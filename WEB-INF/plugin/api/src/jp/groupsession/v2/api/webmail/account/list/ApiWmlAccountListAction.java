package jp.groupsession.v2.api.webmail.account.list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn170.dao.Cmn170Dao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSignDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlDirectoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] WEBメールのアカウント一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlAccountListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlAccountListAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //ResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        int userSid = umodel.getUsrsid();

        List<WmlAccountModel>     accountList = null;
        List<WmlDirectoryModel>   dirList     = null;
        List<WmlAccountSignModel> signList    = null;
        WmlAdmConfModel           confMdl     = null;
        int                       themeId     = 0;


        //アカウント一覧を取得する。
        try {
            // テーマ
            Cmn170Dao cmnDao = new Cmn170Dao(con);
            themeId = cmnDao.select2(userSid);

            //アカウント情報を取得する
            WmlAccountDao accountDao = new WmlAccountDao(con);
            accountList = accountDao.getAccountList(userSid);

            if (accountList != null && accountList.size() > 0) {
                int[] wacSidList = new int[accountList.size()];
                for (int i = 0; i < wacSidList.length; i++) {
                    wacSidList[i] = accountList.get(i).getWacSid();
                }

                // 管理者設定取得
                WmlAdmConfDao confDao = new WmlAdmConfDao(con);
                confMdl = confDao.selectAdmData();

                // ディレクトリ一覧取得
                WmlDirectoryDao dirDao = new WmlDirectoryDao(con);
                dirList = dirDao.getDirectoryList(wacSidList);

                // 署名一覧取得
                WmlAccountSignDao signDao = new WmlAccountSignDao(con);
                signList = signDao.getSignList(wacSidList);
            }
        } catch (SQLException e) {
            log__.error("webmailのアカウント一覧取得に失敗", e);
        }

        if (accountList == null || accountList.isEmpty()) {
            // アカウントがない場合
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("search.data.notfound",
                                                  gsMsg.getMessage("wml.102"));
            StrutsUtil.addMessage(errors, msg, "account");
            addErrors(req, errors);
            return null;
        }

        resultSet.setAttribute("Count", Integer.toString(accountList.size()));

        for (WmlAccountModel acMdl : accountList) {
            //Result
            Element result = new Element("Result");
            resultSet.addContent(result);

            int wacSid = acMdl.getWacSid();
            List<WmlDirectoryModel>   acDirList  = new ArrayList<WmlDirectoryModel>();
            List<WmlAccountSignModel> acSignList = new ArrayList<WmlAccountSignModel>();

            // 該当するディレクトリ一覧抽出
            if (dirList != null && dirList.size() > 0) {
                int idx = dirList.size() - 1;
                for (; idx >= 0; idx--) {
                    WmlDirectoryModel mdl = dirList.get(idx);
                    if (mdl.getWacSid() == wacSid) {
                        acDirList.add(mdl);
                        dirList.remove(idx);
                    }
                }
            }
            // 該当する署名情報一覧抽出
            if (signList != null && signList.size() > 0) {
                int idx = signList.size() - 1;
                for (; idx >= 0; idx--) {
                    WmlAccountSignModel mdl = signList.get(idx);
                    if (mdl.getWacSid() == wacSid) {
                        acSignList.add(mdl);
                        signList.remove(idx);
                    }
                }
            }

            // アカウント情報
            this.setWmlElement(result, acMdl, confMdl, acDirList, acSignList, themeId);
        }
        return doc;
    }

    /**
     * <br>[機  能] webmailアカウント情報をXMLのresult属性にセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param result   エレメント
     * @param acMdl    アカウント情報
     * @param confMdl  管理者設定情報
     * @param dirList  ディレクトリ情報一覧
     * @param signList 署名情報一覧
     * @param gsThemeId GSテーマ(テーマ未設定の場合に必要)
     * @throws Exception 実行例外
     */
    protected void setWmlElement(Element result, WmlAccountModel acMdl,
                                                 WmlAdmConfModel confMdl,
                                                 List<WmlDirectoryModel>   dirList,
                                                 List<WmlAccountSignModel> signList,
                                                 int             gsThemeId)
            throws Exception {

        int proxyUser = GSConstWebmail.WAD_PROXY_USER_YES; // 代理人使用許可フラグ
        int defSignNo = -1;    // 初期選択署名No

        if (confMdl != null) {
            if (confMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN) {
                // 管理者側で設定されている場合(全アカウントで共通設定)

                acMdl.setWacDelreceive(confMdl.getWadDelreceive());     // 受信時削除
                acMdl.setWacAutoreceive(confMdl.getWadAutoreceive());   // 自動受信
                acMdl.setWacAutoReceiveTime(confMdl.getWadAutoReceiveTime()); // 自動受信間隔
                acMdl.setWacCheckAddress(confMdl.getWadCheckAddress()); // 宛先確認
                acMdl.setWacCheckFile(confMdl.getWadCheckFile());       // 添付ファイル確認

                // ディスク容量制限
                acMdl.setWacDisk(confMdl.getWadDisk());          // 区分
                acMdl.setWacDiskSize(confMdl.getWadDiskSize());  // サイズ(MB)

                // 添付ファイル自動圧縮
                acMdl.setWacCompressFile(confMdl.getWadCompressFile());       // 区分
                acMdl.setWacCompressFileDef(confMdl.getWadCompressFileDef()); // 初期値

                // 時間差送信
                acMdl.setWacTimesent(confMdl.getWadTimesent());       // 区分
                acMdl.setWacTimesentDef(confMdl.getWadTimesentDef()); // 初期値
            } else {
                // ディスク容量制限(アカウントの特例設定の場合)
                if (acMdl.getWacDiskSps() == GSConstWebmail.WAC_DISK_SPS_SPSETTINGS) {
                    acMdl.setWacDisk(confMdl.getWadDisk());          // 区分
                    acMdl.setWacDiskSize(confMdl.getWadDiskSize());  // サイズ(MB)
                }
            }
            proxyUser = confMdl.getWadProxyUser();
        }

        // ----------------------------------------------------------
        //  アカウント情報
        // ----------------------------------------------------------
        int diskSize    = 0;  // ディスク容量サイズ(MB)
        int receiveTime = 0;  // 自動受信間隔(分)
        String rcvDate  = ""; // 最終受信日時

        if (acMdl.getWacAutoreceive() == GSConstWebmail.WAC_DISK_LIMIT) {
            // 「制限あり」の場合のみサイズ指定
            diskSize = acMdl.getWacDiskSize();
        }
        if (acMdl.getWacAutoreceive() != GSConstWebmail.AUTO_RECEIVE_FIRST) {
            // 「自動受信する」場合のみ自動受信間隔指定
            receiveTime = acMdl.getWacAutoReceiveTime();
        }
        if (acMdl.getWacReceiveDate() != null) {
            //rcvDate = acMdl.getWacReceiveDate().getTimeStamp2();
            rcvDate =
                UDateUtil.getSlashYYMD(acMdl.getWacReceiveDate())
                + " "
                + UDateUtil.getSeparateHMS(acMdl.getWacReceiveDate());
        }

        // 基本設定
        result.addContent(_createElement("wacSid",   acMdl.getWacSid()));     // アカウントSID
        result.addContent(_createElement("wacName",  acMdl.getWacName()));    // アカウント名
        result.addContent(_createElement("diskSize", diskSize));              // サイズ(MB)
        result.addContent(_createElement("address",  acMdl.getWacAddress())); // メールアドレス
        result.addContent(_createElement("rcvDate",  rcvDate));               // 最終受信日時
        result.addContent(_createElement("biko",     acMdl.getWacBiko()));    // 備考

        // 送受信設定情報
        result.addContent(_createElement("autoTo",   acMdl.getWacAutoto()));     // 自動TO
        result.addContent(_createElement("autoCc",   acMdl.getWacAutocc()));     // 自動CC
        result.addContent(_createElement("autoBcc",  acMdl.getWacAutobcc()));    // 自動BCC
        result.addContent(_createElement("delRcv",   acMdl.getWacDelreceive())); // 受信時削除
        result.addContent(_createElement("reRcv",    acMdl.getWacRereceive()));  // 再受信
        result.addContent(_createElement("apop",     acMdl.getWacApop()));       // APOP
        result.addContent(_createElement("topCmd",   acMdl.getWacTopCmd()));     // TOPコマンド
        result.addContent(_createElement("popbsmtp", acMdl.getWacPopbsmtp()));   // POP認証
        result.addContent(_createElement("encSend",  acMdl.getWacEncodeSend())); // 文字コード
        result.addContent(_createElement("autoRcvTime",  receiveTime));                // 自動受信間隔
        result.addContent(_createElement("mailType",     acMdl.getWacSendMailtype())); // メール形式
        result.addContent(_createElement("checkAddress", acMdl.getWacCheckAddress())); // 宛先確認
        result.addContent(_createElement("checkFile",    acMdl.getWacCheckFile()));    // 添付ファイル確認

        // 添付ファイル自動圧縮
        result.addContent(_createElement("compressFile", acMdl.getWacCompressFile()));
        if (acMdl.getWacCompressFile() == GSConstWebmail.WAC_COMPRESS_FILE_INPUT) {
            // 「作成時に選択」の場合のみ初期値設定
            result.addContent(_createElement("compressFileDef", acMdl.getWacCompressFileDef()));
        }
        // 時間差送信
        result.addContent(_createElement("timesent", acMdl.getWacTimesent())); // 区分
        if (acMdl.getWacTimesent() == GSConstWebmail.WAC_TIMESENT_INPUT) {
            // 「作成時に選択」の場合のみ初期値設定
            result.addContent(_createElement("timesentDef", acMdl.getWacTimesentDef())); // 初期値
        }

        // その他
        String quotesStr = WmlBiz.getMailQuotes(acMdl.getWacQuotes());
        int themeId = acMdl.getWacTheme();
        if (themeId == 0) {
            // テーマが未設定 → GS自体のテーマを反映
            themeId = gsThemeId;
        }
        result.addContent(_createElement("theme",     themeId));              // テーマ
        result.addContent(_createElement("quotes",    quotesStr));            // 引用符
        result.addContent(_createElement("proxyUser", proxyUser));            // 代理人フラグ

        // 署名情報
        result.addContent(_createElement("signOrgan",
                                                acMdl.getWacOrganization())); // 組織名
        result.addContent(_createElement("signPoint",
                                                acMdl.getWacSignPointKbn())); // 署名位置
        result.addContent(_createElement("signDspKbn",
                                                  acMdl.getWacSignDspKbn())); // 表示区分
        result.addContent(_createElement("signAuto",
                                                  acMdl.getWacSignAuto()));   // 自動署名

        Element signSet = new Element("SignSet");
        result.addContent(signSet);
        signSet.setAttribute("Count", Integer.toString(signList.size()));
        for (WmlAccountSignModel mdl : signList) {
            Element signElm = new Element("Sign");
            signElm.addContent(_createElement("wsiNo",    mdl.getWsiNo()));
            signElm.addContent(_createElement("wsiTitle", mdl.getWsiTitle()));
            signElm.addContent(_createElement("wsiSign",  mdl.getWsiSign()));
            if (mdl.getWsiDef() > 0) {
                defSignNo = mdl.getWsiNo(); // 初期選択値取得
            }
            signSet.addContent(signElm);
        }
        if (defSignNo >= 0) {
            result.addContent(_createElement("signDefNo", defSignNo));  // 初期選択署名No
        }

        // ディレクトリ情報
        Element dirSet = new Element("DirectorySet");
        result.addContent(dirSet);
        dirSet.setAttribute("Count", Integer.toString(dirList.size()));
        for (WmlDirectoryModel mdl : dirList) {
            Element dirElm = new Element("Directory");
            dirElm.addContent(_createElement("wdrSid",  mdl.getWdrSid()));
            dirElm.addContent(_createElement("wdrType", mdl.getWdrType()));
            dirElm.addContent(_createElement("wdrName", mdl.getWdrName()));
            dirSet.addContent(dirElm);
        }
    }
}

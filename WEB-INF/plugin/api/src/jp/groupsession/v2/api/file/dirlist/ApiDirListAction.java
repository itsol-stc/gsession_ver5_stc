package jp.groupsession.v2.api.file.dirlist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil040.FileDirectoryDspModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ディレクトリ一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiDirListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiDirListAction.class);

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

        log__.debug("createXml start");

        ApiDirListForm thisForm = (ApiDirListForm) form;
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        RequestModel reqMdl = getRequestModel(req);
        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }
        //入力チェック
        ActionErrors errors = thisForm.validateDirList(con, gsMsg, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        //ソート
        int sort = GSConstFile.SORT_NAME;
        //オーダー
        int order = GSConst.ORDER_KEY_ASC;
        //ディレクトリSID
        int dirSid = NullDefault.getInt(thisForm.getFdrSid(), 0);

        ArrayList<FileDirectoryDspModel> retList = new ArrayList<FileDirectoryDspModel>();
        boolean superUser = false;
        boolean parentEditor = true;
        final int write_kbn = Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE);
        FileCabinetModel fcbMdl = null;
        try {
            //特権ユーザ判定
            FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
            fcbMdl = cmnBiz.getCabinetModel(dirSid);
            int cabSid = fcbMdl.getFcbSid();
            superUser = cmnBiz.isEditCabinetUser(cabSid);

            //カレント情報取得
            if (superUser) {
                retList = dirDao.getNewLowDirectory(
                        dirSid, umodel.getUsrsid(), sort, order, reqMdl);
            } else {
                retList = dirDao.getNewLowDirectoryAccessLimit(
                        dirSid, umodel.getUsrsid(), sort, order, reqMdl);
            }

            //親ディレクトリアクセス権限取得
            if (!superUser) {
                FileDAccessConfDao dao = new FileDAccessConfDao(con);
                parentEditor = dao.isAccessUser(dirSid, reqMdl.getSmodel().getUsrsid(), write_kbn);
            }

        } catch (SQLException e) {
            log__.error("ディレクトリ一覧の取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        if (parentEditor) {
            resultSet.setAttribute("parentWriteKbn", "0");
        } else {
            resultSet.setAttribute("parentWriteKbn", "1");
        }
        //XMLデータ作成
        for (FileDirectoryDspModel dspModel : retList) {

            Element result = new Element("Result");
            resultSet.addContent(result);

            //FdrSid ディレクトリSID
            result.addContent(_createElement("FdrSid", dspModel.getFdrSid()));

            //FdrName ファイル・フォルダ名
            result.addContent(_createElement("FdrName", dspModel.getFdrName()));

            //FdrKbn ディレクトリ区分
            result.addContent(_createElement("FdrKbn", dspModel.getFdrKbn()));

            //BinSid バイナリSID
            result.addContent(_createElement("BinSid", dspModel.getFileBinSid()));

            //FileSize ファイルサイズ(KB)
            Element fileSize = new Element("FileSize");
            fileSize.addContent(Long.toString(dspModel.getFflFileSize()));
            result.addContent(fileSize);

            //FdrEdate 更新日時
            Element fdrEdate = new Element("FdrEdate");
            fdrEdate.addContent(dspModel.getEdateString());
            result.addContent(fdrEdate);

            //LockKbn ロック区分
            Element lockKbn = new Element("LockKbn");
            lockKbn.addContent(dspModel.getLockKbn());
            result.addContent(lockKbn);

            //LockUsrSid ロックユーザSID
            result.addContent(_createElement("LockUsrSid", dspModel.getLockUsrSid()));

            //編集権限
            if (superUser
            || dspModel.getAccessKbn() == write_kbn) {
                result.addContent(_createElement("WriteKbn", 0));
            } else {
                result.addContent(_createElement("WriteKbn", 1));
            }


            int egid = dspModel.getFdrEgid();
            if (egid == 0) {
                //編集ユーザ区分
                result.addContent(_createElement("EditUsrKbn", 0));
                //編集ユーザSID
                result.addContent(_createElement("EditUsrSid", dspModel.getFdrEuid()));
                //編集ユーザ名
                result.addContent(_createElement("EditUsrName", dspModel.getUpUsrName()));
                //編集ユーザ無効フラグ
                result.addContent(_createElement("EditUsrUkoFlg", dspModel.getUpUsrUkoFlg()));
            } else {
                //編集ユーザ区分
                result.addContent(_createElement("EditUsrKbn", 1));
                //編集ユーザSID
                result.addContent(_createElement("EditUsrSid", dspModel.getFdrEgid()));
                //編集ユーザ名
                result.addContent(_createElement("EditUsrName", dspModel.getUpUsrName()));
                //編集ユーザ無効フラグ
                result.addContent(_createElement("EditUsrUkoFlg", dspModel.getUpUsrUkoFlg()));
            }
            if (fcbMdl != null &&
                    fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON
                    && dspModel.getFdrKbn() == GSConstFile.DIRECTORY_KBN_FILE) {
                String tradeDate = "";
                String tradeTarget = "";
                String money = "";
                String moneyName = "";

                if (dspModel.getFdrTradeDate() != null) {
                    tradeDate = dspModel.getFdrTradeDate().getDateString("/");
                }

                if (dspModel.getFdrTradeTarget() != null) {
                    tradeTarget = dspModel.getFdrTradeTarget();
                }
                result.addContent(_createElement("FdrErrlDate", tradeDate));
                result.addContent(_createElement("FdrErrlTarget", tradeTarget));
                if (dspModel.getFdrTradeMoneykbn() == GSConstFile.MONEY_KBN_ON) {
                    money = StringUtil.toCommaUnderZeroTrim(
                            dspModel.getFdrTradeMoney().toPlainString());

                    result.addContent(_createElement("FdrErrlMoney", money));

                    int mtIndex = dspModel.getTradeMoney().indexOf(" ");
                    if (mtIndex >= 0
                          &&  mtIndex < dspModel.getTradeMoney().length()) {
                        moneyName =
                                Optional.ofNullable(dspModel.getTradeMoney())
                                    .map(str -> str.substring(mtIndex))
                                    .filter(str -> str.length() > 1)
                                    .map(str -> str.substring(1))
                                    .orElse("");

                        result.addContent(_createElement("FdrErrlMoneyType", moneyName));

                    }

                }

            }

        }

        log__.debug("createXml end");
        return doc;
    }
}

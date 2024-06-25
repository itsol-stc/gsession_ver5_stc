package jp.groupsession.v2.cir.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir020.Cir020SearchModel;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirAconfModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 回覧板ショートメール通知関連ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirNotifyBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirNotifyBiz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル*/
    private RequestModel reqMdl__ = null;



    /**
     * コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエストモデル
     */
    public CirNotifyBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     * <br>[機  能] 受信者にショートメールで通知を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param sidList 宛先アカウントSIDリスト
     * @param ciMdl 回覧板モデル
     * @param appRootPath アプリケーションのルートパス
     * @throws Exception 実行例外
     */
    public void doNotifiesJusin(
        MlCountMtController cntCon,
        List<Integer>  sidList,
        CirInfModel ciMdl,
        String appRootPath
        ) throws Exception {


        if (!__isSmlSend(sidList, GSConstCircular.NOTIFY_NTF)) {
            return;
        }


        log__.debug("回覧板受信通知メールを送信する");


        //発信日時作成
        String tdate = UDateUtil.getSlashYYMD(ciMdl.getCifAdate()) + " "
        + UDateUtil.getSeparateHMS(ciMdl.getCifAdate());

        //ファイルリスト作成
        CircularDao cirDao = new CircularDao(con__);
        List<CmnBinfModel> cbList = cirDao.getFileInfo(ciMdl.getCifSid());
        StringBuilder fileLine = new StringBuilder();
        for (CmnBinfModel cbMdl : cbList) {
            String fileName = cbMdl.getBinFileName();
            if (fileLine.length() != 0) {
                fileLine.append(" , ");
            }
            fileLine.append(fileName);
        }

        //URL
        CirCommonBiz biz = new CirCommonBiz();
        int infSid = ciMdl.getCifSid();
        int hantei = GSConstCircular.SMAIL_DSP_KAKUNIN;
        String url = biz.createThreadUrl(reqMdl__, infSid, hantei);

        //メール本文作成
        Map<String, String> map = new HashMap<String, String>();
        map.put("TITLE", ciMdl.getCifTitle());
        map.put("DATE", tdate);
        String sendName = "";
        CirAccountDao cacDao = new CirAccountDao(con__);
        CirAccountModel cacMdl = new CirAccountModel();
        cacMdl = cacDao.select(ciMdl.getCifAuid());
        if (cacMdl != null) {
            sendName = cacMdl.getCacName();
        }

        map.put("NAME", sendName);
        map.put("FILES", fileLine.toString());
        map.put("BODY", ciMdl.getCifValue());
        map.put("URL", url);

        //テンプレートファイルパス取得
        String tmpPath = __getTemplatePathJusin(appRootPath);
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
        String bodyml = StringUtil.merge(tmpBody, map);

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String message = gsMsg.getMessage("cmn.mail.omit");

        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            bodyml = message + "\r\n\r\n" + bodyml;
            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
        }

        //ショートメール送信用モデルを作成する。
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);

        //TO アカウントSIDから使用ユーザを取得
        List<Integer> accountList = __smlSendAccSidList(sidList, GSConstCircular.NOTIFY_NTF);
        List<Integer> usrSidList = biz.getUsrSidListFromAccSid(con__, accountList);

        smlModel.setSendToUsrSidArray(usrSidList);

        //タイトル
        String title = gsMsg.getMessage("cir.52");
        String cirTitle = ciMdl.getCifTitle();

        //30文字まで表示
        if (cirTitle.length() > 30) {
            cirTitle = cirTitle.substring(0, 30);
        }

        smlModel.setSendTitle(title + "　" + cirTitle);
        //本文
        smlModel.setSendBody(bodyml);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        boolean commit = false;
        try {
            CommonBiz cmnBiz = new CommonBiz();

            //メール送信処理開始
            SmlSender sender = new SmlSender(con__, cntCon, smlModel,
                    cmnBiz.getPluginConfigForMain(con__, reqMdl__),
                    appRootPath, reqMdl__);
            sender.execute();

            commit = true;
        } catch (Exception e) {
            log__.error("スレッド作成処理エラー", e);
            throw e;
        } finally {
            if (commit) {
                con__.commit();
            } else {
                con__.rollback();
            }
        }
    }
    /**
     *
     * <br>[機  能] 通知設定判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList アカウントSIDリスト
     * @param notifyType 通知種別
     * @return true:通知する false：通知しない
     * @throws SQLException 実行時例外
     */
    private boolean __isSmlSend(List<Integer> sidList,
            int notifyType) throws SQLException {
        CirCommonBiz biz = new CirCommonBiz();
        CirAconfModel admData = biz.getCirAdminData(con__, reqMdl__.getSmodel().getUsrsid());
        boolean smlFlg = false;

        //管理者設定でショートメール通知が「管理者が設定する」の場合
        if (admData.getCafSmailSendKbn() == GSConstCircular.CAF_SML_NTF_ADMIN) {
            switch (notifyType) {
            case GSConstCircular.NOTIFY_NTF:
                //管理者設定で「通知する」の場合
                if (admData.getCafSmailSend() == GSConstCircular.CAF_SML_NTF_KBN_YES) {
                    smlFlg = false;

                //管理者設定で「通知しない」の場合、送信処理終了
                } else {
                    return false;
                }
                break;
            case GSConstCircular.NOTIFY_MEMO:
                //管理者設定で「通知する」の場合
                if (admData.getCafSmailSendMemo() == GSConstCircular.CAF_SML_NTF_KBN_YES) {
                    smlFlg = false;

                //管理者設定で「通知しない」の場合、送信処理終了
                } else {
                    return false;
                }
                break;
            case GSConstCircular.NOTIFY_EDIT:
                //管理者設定で「通知する」の場合
                if (admData.getCafSmailSendEdit() == GSConstCircular.CAF_SML_NTF_KBN_YES) {
                    smlFlg = false;

                //管理者設定で「通知しない」の場合、送信処理終了
                } else {
                    return false;
                }
                break;
            default:
            }



        //管理者設定でショートメール通知が「各ユーザが設定する」の場合
        } else {
            smlFlg = true;
        }

        //指定されたユーザSIDリストの中からショートメール通知対象の受信者情報を取得
        CirAccountDao cacDao = new CirAccountDao(con__);

        String[] sidsStr = new String[sidList.size()];
        for (int i = 0; i < sidsStr.length; i++) {
            sidsStr[i] = String.valueOf(sidList.get(i));
        }

        List<CirAccountModel> cacList =
                cacDao.getMailSendUser(sidsStr,
                        smlFlg, notifyType);

        log__.debug("cuList.size() = " + cacList.size());
        if (cacList.size() <= 0) {
            //送信対象ユーザがいない場合、処理終了
            return false;
        }

        return true;

    }

    /**
    *
    * <br>[機  能] 通知設定判定を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param sidList アカウントSIDリスト
    * @param notifyType 通知種別
    * @return true:通知する false：通知しない
    * @throws SQLException 実行時例外
    */
   private List<Integer> __smlSendAccSidList(List<Integer> sidList,
           int notifyType) throws SQLException {
       CirCommonBiz biz = new CirCommonBiz();
       CirAconfModel admData = biz.getCirAdminData(con__, reqMdl__.getSmodel().getUsrsid());

       List<Integer> accountList = new ArrayList<Integer>();
       //管理者設定でショートメール通知が「各ユーザが設定する」の場合
       if (admData.getCafSmailSendKbn() == GSConstCircular.CAF_SML_NTF_USER) {
           //指定されたアカウントSIDリストの中からショートメール通知対象の受信者情報を取得
           CirAccountDao cacDao = new CirAccountDao(con__);
           accountList = cacDao.getMailSendAccount(sidList, notifyType);
       } else {
           accountList = sidList;
       }
       return accountList;
   }

    /**
     * <br>[機  能] アプリケーションのルートパスから受信通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    private String __getTemplatePathJusin(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/circular/smail/jusin_tsuuchi.txt");
        return ret;
    }


    /**
     * <br>[機  能] 受信者にショートメールで通知を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param sidList 宛先アカウントSIDリスト
     * @param ciMdl 回覧板モデル
     * @param appRootPath アプリケーションのルートパス
     * @return 通知件数
     * @throws Exception 実行例外
     */
    public int doNotifiesEdit(MlCountMtController cntCon,
            List<Integer> sidList,
            CirInfModel ciMdl, String appRootPath) throws Exception {

        int ret = 0;


        if (!__isSmlSend(sidList, GSConstCircular.NOTIFY_EDIT)) {
            return ret;
        }

        //発信日時作成
        String tdate = UDateUtil.getSlashYYMD(ciMdl.getCifEdate()) + " "
        + UDateUtil.getSeparateHMS(ciMdl.getCifEdate());

        //URL
        CirCommonBiz biz = new CirCommonBiz();
        int infSid = ciMdl.getCifSid();
        int hantei = GSConstCircular.SMAIL_DSP_KAKUNIN;
        String url = biz.createThreadUrl(reqMdl__, infSid, hantei);

        //メール本文作成
        Map<String, String> map = new HashMap<String, String>();
        map.put("TITLE", ciMdl.getCifTitle());
        map.put("DATE", tdate);
        String sendName = "";
        CirAccountDao cacDao = new CirAccountDao(con__);
        CirAccountModel cacMdl = new CirAccountModel();
        cacMdl = cacDao.select(ciMdl.getCifAuid());
        if (cacMdl != null) {
            sendName = cacMdl.getCacName();
        }

        map.put("NAME", sendName);
        map.put("URL", url);

        //テンプレートファイルパス取得
        String tmpPath = __getTemplatePathEdit(appRootPath);
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
        String bodyml = StringUtil.merge(tmpBody, map);


        GsMessage gsMsg = new GsMessage(reqMdl__);
        String message = gsMsg.getMessage("cmn.mail.omit");

        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            bodyml = message + "\r\n\r\n" + bodyml;
            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
        }

        //ショートメール送信用モデルを作成する。
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);

        //TO アカウントSIDから使用ユーザを取得
        List<Integer> accountList = __smlSendAccSidList(sidList, GSConstCircular.NOTIFY_EDIT);
        List<Integer> usrSidList = biz.getUsrSidListFromAccSid(con__, accountList);

        smlModel.setSendToUsrSidArray(usrSidList);

        //タイトル
        String title = gsMsg.getMessage("cir.62");
        String cirTitle = ciMdl.getCifTitle();

        //30文字まで表示
        if (cirTitle.length() > 30) {
            cirTitle = cirTitle.substring(0, 30);
        }

        smlModel.setSendTitle(title + "　" + cirTitle);
        //本文
        smlModel.setSendBody(bodyml);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        boolean commit = false;
        try {
            CommonBiz cmnBiz = new CommonBiz();

            //メール送信処理開始
            SmlSender sender = new SmlSender(con__, cntCon, smlModel,
                    cmnBiz.getPluginConfigForMain(con__, reqMdl__),
                    appRootPath, reqMdl__);
            sender.execute();
            ret = usrSidList.size();
            commit = true;
        } catch (Exception e) {
            log__.error("スレッド作成処理エラー", e);
            throw e;
        } finally {
            if (commit) {
                con__.commit();
            } else {
                con__.rollback();
            }
        }
        return ret;

    }
    /**
     * <br>[機  能] ショートメール通知がONの場合、回覧板確認完了通知メールを送信する
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param con コネクション
     * @param cirSid 回覧板SID
     * @param appRootPath アプリケーションのルートパス
     * @param reqMdl リクエスト情報
     * @return 通知件数
     * @throws Exception 実行例外
     */
    public int doNotifyKanryo(
        MlCountMtController cntCon,
        Connection con,
        int cirSid,
        String appRootPath,
        RequestModel reqMdl) throws Exception {
        int ret = 0;
        //回覧板情報を取得する
        CirInfDao ciDao = new CirInfDao(con);
        CirInfModel ciMdl = ciDao.getCirInfo(cirSid);
        if (ciMdl == null) {
            return ret;
        }


        ArrayList<Integer> idList = new ArrayList<>();
        idList.add(ciMdl.getCifAuid());

        if (!__isSmlSend(idList, GSConstCircular.NOTIFY_NTF)) {
            return ret;
        }


        log__.debug("回覧板確認完了通知メールを送信する");
        //発信日時作成
        String tdate = UDateUtil.getSlashYYMD(ciMdl.getCifAdate()) + " "
        + UDateUtil.getSeparateHMS(ciMdl.getCifAdate());

        //ファイルリスト作成
        CircularDao cirDao = new CircularDao(con);
        List<CmnBinfModel> cbList = cirDao.getFileInfo(ciMdl.getCifSid());
        StringBuilder fileLine = new StringBuilder();
        for (CmnBinfModel cbMdl : cbList) {
            String fileName = cbMdl.getBinFileName();
            if (fileLine.length() != 0) {
                fileLine.append(" , ");
            }
            fileLine.append(fileName);
        }

        //URL
        CirCommonBiz biz = new CirCommonBiz();
        int infSid = ciMdl.getCifSid();
        int hantei = GSConstCircular.SMAIL_DSP_JYOUKYOU;
        String url = biz.createThreadUrlPlusAccount(reqMdl, infSid, hantei, ciMdl.getCifAuid());

        //メール本文作成
        Map<String, String> map = new HashMap<String, String>();
        map.put("TITLE", ciMdl.getCifTitle());
        map.put("DATE", tdate);
        map.put("FILES", fileLine.toString());
        map.put("BODY", ciMdl.getCifValue());
        map.put("URL", url);

        //テンプレートファイルパス取得
        String tmpPath = __getSmlTemplateFilePathKanryo(appRootPath);
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
        String bodyml = StringUtil.merge(tmpBody, map);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = gsMsg.getMessage("cmn.mail.omit");

        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            bodyml = message + "\r\n\r\n" + bodyml;
            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
        }

        //TOリストを作成
        List<Integer>  accSidList = new ArrayList<Integer>();
        accSidList.add(ciMdl.getCifAuid());
        List<Integer> accountList = __smlSendAccSidList(accSidList, GSConstCircular.NOTIFY_NTF);
        List<Integer>  sidList = biz.getUsrSidListFromAccSid(con__, accountList);

        if (sidList != null && !sidList.isEmpty()) {

            //sidList.add(new Integer(accounMdl.getUsrSid()));

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);

            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //タイトル
            String title = gsMsg.getMessage("cir.51");
            smlModel.setSendTitle(title);
            //本文
            smlModel.setSendBody(bodyml);
            //メール形式
            smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

            boolean commit = false;
            try {

                //メール送信処理開始
                CommonBiz cmnBiz = new CommonBiz();

                //メール送信処理開始
                SmlSender sender = new SmlSender(con__, cntCon, smlModel,
                        cmnBiz.getPluginConfigForMain(con__, reqMdl__),
                        appRootPath, reqMdl__);
                sender.execute();
                ret = sidList.size();

                commit = true;
            } catch (Exception e) {
                log__.error("スレッド作成処理エラー", e);
                throw e;
            } finally {
                if (commit) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] 確認時メモ、確認時添付編集通知の送信
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param con コネクション
     * @param civMdl 確認情報
     * @param appRootPath アプリケーションのルートパス
     * @param reqMdl リクエスト情報
     * @return 通知件数
     * @throws Exception 実行例外
     */
    public int doNotifyMemo(MlCountMtController cntCon, Connection con,
            CirViewModel civMdl, String appRootPath, RequestModel reqMdl) throws Exception {
        int ret = 0;
        //回覧板情報を取得する
        CirInfDao ciDao = new CirInfDao(con);
        CirInfModel ciMdl = ciDao.getCirInfo(civMdl.getCifSid());
        if (ciMdl == null) {
            return ret;
        }


        ArrayList<Integer> idList = new ArrayList<>();
        idList.add(ciMdl.getCifAuid());

        if (!__isSmlSend(idList, GSConstCircular.NOTIFY_MEMO)) {
            return ret;
        }


        log__.debug("回覧板確認状況編集通知メールを送信する");
        //確認日時作成
        String tdate = UDateUtil.getSlashYYMD(civMdl.getCvwEdate()) + " "
        + UDateUtil.getSeparateHMS(civMdl.getCvwEdate());

        //確認者名
        CirAccountDao accountDao = new CirAccountDao(con);
        CirAccountModel accounMdl = accountDao.select(civMdl.getCacSid());

        //ファイルリスト作成
        CircularDao cirDao = new CircularDao(con);
        Cir020SearchModel searchMdl = new Cir020SearchModel();
        searchMdl.setCirSid(civMdl.getCifSid());
        searchMdl.setCacSid(civMdl.getCacSid());

        List<CmnBinfModel> cbList = cirDao.getUserTempFileInfo(searchMdl);
        StringBuilder fileLine = new StringBuilder();
        for (CmnBinfModel cbMdl : cbList) {
            String fileName = cbMdl.getBinFileName();
            if (fileLine.length() != 0) {
                fileLine.append(" , ");
            }
            fileLine.append(fileName);
        }

        //URL
        CirCommonBiz biz = new CirCommonBiz();
        int infSid = ciMdl.getCifSid();
        int hantei = GSConstCircular.SMAIL_DSP_JYOUKYOU;
        String url = biz.createThreadUrlPlusAccount(reqMdl, infSid, hantei, ciMdl.getCifAuid());

        //メール本文作成
        Map<String, String> map = new HashMap<String, String>();
        map.put("TITLE", ciMdl.getCifTitle());
        map.put("DATE", tdate);
        map.put("NAME", accounMdl.getCacName());
        map.put("FILES", fileLine.toString());
        map.put("BODY", civMdl.getCvwMemo());
        map.put("URL", url);

        //テンプレートファイルパス取得
        String tmpPath = __getTemplatePathMemo(appRootPath);
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
        String bodyml = StringUtil.merge(tmpBody, map);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = gsMsg.getMessage("cmn.mail.omit");

        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            bodyml = message + "\r\n\r\n" + bodyml;
            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
        }

        //TOリストを作成
        List<Integer> accSidList = new ArrayList<Integer>();
        accSidList.add(ciMdl.getCifAuid());
        List<Integer> accountList = __smlSendAccSidList(accSidList, GSConstCircular.NOTIFY_MEMO);
        List<Integer>  sidList = biz.getUsrSidListFromAccSid(con__, accountList);

        if (sidList != null && !sidList.isEmpty()) {

            //sidList.add(new Integer(accounMdl.getUsrSid()));

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);

            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //タイトル
            String title = gsMsg.getMessage("cir.61");
            String cirTitle = ciMdl.getCifTitle();

            //30文字まで表示
            if (cirTitle.length() > 30) {
                cirTitle = cirTitle.substring(0, 30);
            }

            smlModel.setSendTitle(title + "　" + cirTitle);
            //本文
            smlModel.setSendBody(bodyml);
            //メール形式
            smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

            boolean commit = false;
            try {

                //メール送信処理開始
                CommonBiz cmnBiz = new CommonBiz();

                //メール送信処理開始
                SmlSender sender = new SmlSender(con__, cntCon, smlModel,
                        cmnBiz.getPluginConfigForMain(con__, reqMdl__),
                        appRootPath, reqMdl__);
                sender.execute();
                ret = sidList.size();

                commit = true;
            } catch (Exception e) {
                log__.error("スレッド作成処理エラー", e);
                throw e;
            } finally {
                if (commit) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
        return ret;

    }

    /**
     * <br>[機  能] アプリケーションのルートパスから確認完了通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    private String __getSmlTemplateFilePathKanryo(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/circular/smail/kakunin_tsuuchi.txt");
        return ret;
    }
    /**
     * <br>[機  能] アプリケーションのルートパスからメモ編集通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    private String __getTemplatePathMemo(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/circular/smail/memo_tsuuchi.txt");
        return ret;
    }
    /**
     * <br>[機  能] アプリケーションのルートパスから編集通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    private String __getTemplatePathEdit(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/circular/smail/edit_tsuuchi.txt");
        return ret;
    }


}

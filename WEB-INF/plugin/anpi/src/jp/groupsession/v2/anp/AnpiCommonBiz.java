package jp.groupsession.v2.anp;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.co.sjts.util.mail.Sender;
import jp.groupsession.v2.anp.dao.AnpAdmConfDao;
import jp.groupsession.v2.anp.dao.AnpCmnConfDao;
import jp.groupsession.v2.anp.dao.AnpPriConfDao;
import jp.groupsession.v2.anp.model.AnpAdmConfModel;
import jp.groupsession.v2.anp.model.AnpCmnConfModel;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.anp.model.AnpLabelValueModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.OauthMailServerModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSPassword;

/**
 * <br>[機  能] 安否確認共通ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AnpiCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpiCommonBiz.class);

    /** 改行コード */
    private static final String NEW_LINE = "\r\n";

    /** コネクション */
    private Connection con__ = null;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public AnpiCommonBiz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     */
    public AnpiCommonBiz(Connection con) {
        con__ = con;
    }


    /**
     * <br>[機  能] GS管理者か安否確認管理者ならばtrue、管理者でなければfalseを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return boolean 全ネットワーク管理者権限が有り:true, 無し:false
     * @throws SQLException 実行例外
     */
    public static boolean isGsAnpiAdmin(RequestModel reqMdl, Connection con)
            throws SQLException {

        try {
            //セッションユーザ情報を取得する。
            BaseUserModel usModel = reqMdl.getSmodel();

            //GS管理者かどうか判別
            CommonBiz cmnBiz = new CommonBiz();
            boolean isGsAdm = cmnBiz.isPluginAdmin(con, usModel, GSConstAnpi.PLUGIN_ID);
            if (isGsAdm) {
                return true;
            }

            //安否確認の管理者かどうか判別
            AnpAdmConfDao admDao = new AnpAdmConfDao(con);
            List<AnpAdmConfModel>admList = admDao.select();
                for (AnpAdmConfModel mdl : admList) {
                    if ((mdl.getUsrSid() != -1) && (mdl.getUsrSid() == usModel.getUsrsid())) {
                        return true;
                    }
                    GroupBiz grpBiz = new GroupBiz();
                    if (mdl.getGrpSid() != -1) {
                        boolean ret =
                                grpBiz.isBelongGroup(usModel.getUsrsid(), mdl.getGrpSid(), con);
                        if (ret) {
                            return true;
                        }
                    }
                }
            log__.debug("安否確認管理者権限なし");

        } catch (SQLException e) {
            throw e;
        }
        return false;
    }

    /**
     * <br>[機  能] 安否確認個人設定を取得します。
     * <br>[解  説]
     * <br>[備  考] 個人設定が存在しない場合は初期値で個人設定を作成し、返します。
     * @param  con DBコネクション
     * @param usrSid ユーザSID
     * @return 安否確認個人設定
     * @throws SQLException SQL実行エラー
     */
    public AnpPriConfModel getAnpPriConfModel(Connection con, int usrSid) throws SQLException {

        AnpPriConfDao priDao = new AnpPriConfDao(con);
        AnpPriConfModel pconf = priDao.select(usrSid);

        if (pconf == null) {
            //初期値を取得し個人設定をInsert
            pconf = getNewAnpPriConfModel(con, usrSid);
        }

        return pconf;
    }

    /**
     * <br>[機  能] 新規登録者用安否確認個人設定AnpPriConfModelを取得します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return 安否確認個人設定
     * @throws SQLException SQL実行エラー
     */
    public AnpPriConfModel getNewAnpPriConfModel(Connection con, int usrSid) throws SQLException {

        AnpPriConfDao priDao = new AnpPriConfDao(con);
        boolean commitFlg = con.getAutoCommit();

        //個人情報初期値を設定
        AnpPriConfModel pconf = getDefaulPriConfModel(usrSid, con);

        try {
            if (commitFlg) {
                con.setAutoCommit(false);
            }

            priDao.insert(pconf);
            if (commitFlg) {
                con.commit();
            }
        } catch (SQLException e) {
            if (commitFlg) {
                con.rollback();
            }
            log__.error("個人設定の取得に失敗しました。" + e);
            throw e;
        } finally {
        }

        log__.debug("pconf = " + pconf.toCsvString());
        return pconf;
    }

    /**
     * <br>[機  能] 安否確認個人設定のデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param usrSid ユーザSID
     * @param con    DBコネクション
     * @return 安否確認個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public AnpPriConfModel getDefaulPriConfModel(int usrSid, Connection con) throws SQLException {

        AnpPriConfModel confBean = new AnpPriConfModel();

        //ユーザSID
        confBean.setUsrSid(usrSid);
        //メイン表示フラグ
        confBean.setAppMainKbn(GSConstAnpi.DSP_FLG_YES);
        //状況一覧表示件数
        confBean.setAppListCount(GSConstAnpi.MAX_RECORD_COUNT);
        //デフォルト表示グループSID
        GroupBiz gbiz = new GroupBiz();
        String appDspGroup = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
        confBean.setAppDspGroup(Integer.parseInt(appDspGroup));
        //デフォルト表示マイグループSID
        confBean.setAppDspMygroup(0);
        //全て選択フラグ
        confBean.setAppAllGroupFlg(GSConstAnpi.ALL_GROUP_NOT_SELECT);
        //緊急連絡先・メールアドレス
        confBean.setAppMailadr(null);
        //緊急連絡先・電話番号
        confBean.setAppTelno(null);
        //登録者ID
        confBean.setAppAuid(usrSid);
        //登録日時
        confBean.setAppAdate(new UDate());
        //更新者ID
        confBean.setAppEuid(usrSid);
        //更新日時
        confBean.setAppEdate(new UDate());

        return confBean;
    }

    /**
     * <br>[機  能] 安否確認共通設定の初期値を設定します。
     * <br>[解  説] 新規作成時は初期値が設定されます。
     * <br>[備  考]
     * @param con DBコネクション
     * @return 安否確認共通設定 AnpCmnConfModel
     * @throws SQLException SQL実行エラー
     */
    public AnpCmnConfModel getAnpCmnConfModel(
            Connection con) throws SQLException {

        AnpCmnConfDao dao = new AnpCmnConfDao(con);
        List<AnpCmnConfModel> bean = dao.select();

        if (bean == null || bean.isEmpty()) {
            //デフォルト値設定（※特になし）
            AnpCmnConfModel newConf = new AnpCmnConfModel();
            newConf.setApcSendPort(Sender.DEFAULT_PORT);
            newConf.setApcSendSsl(GSConstAnpi.SEND_SSL_NOUSE);
            newConf.setApcSmtpAuth(GSConstAnpi.SMTP_AUTH_NOT);
            return newConf;
        }

        return bean.get(0);
    }

    /**
     * <br>[機  能] 安否確認メッセージ配信時のメール本文を取得します。
     * <br>[解  説] ユーザ入力情報も結合します。
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param anpiSid  安否確認SID
     * @param userSid  ユーザSID
     * @param body1 本文１
     * @param body2 本文２
     * @param isHtml   HTML表示の場合、true
     * @param knrenFlg 訓練モードフラグ
     * @return 入力用URL
     * @throws Exception 実行時例外
     */
    public String getHaisinMessageBody(RequestModel reqMdl, Connection con,
                            String anpiSid, String userSid,
                            String body1, String body2,
                            boolean isHtml, int knrenFlg)
                throws Exception {

        AnpCmnConfModel cmnConf = getAnpCmnConfModel(con);
        return getHaisinMessageBody(reqMdl,
                cmnConf, anpiSid, userSid, body1, body2, isHtml, knrenFlg);
    }

    /**
     * <br>[機  能] 安否確認メッセージ配信時のメール本文を取得します。
     * <br>[解  説] ユーザ入力情報も結合します。
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param cmnConf 共通設定情報
     * @param anpiSid  安否確認SID
     * @param userSid  ユーザSID
     * @param body1 本文１
     * @param body2 本文２
     * @param isHtml   HTML表示の場合、true
     * @param knrenFlg 訓練フラグ
     * @return 入力用URL
     * @throws Exception 実行時例外
     */
    public String getHaisinMessageBody(RequestModel reqMdl, AnpCmnConfModel cmnConf,
                            String anpiSid, String userSid,
                            String body1, String body2,
                            boolean isHtml, int knrenFlg)
                throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl);

        String body = "";
        String newLine = NEW_LINE;
        String dspBody1 = body1;
        String dspBody2 = body2;

        if (isHtml) {
            newLine = GSConst.NEW_LINE_STR;
            dspBody1 = StringUtilHtml.transToHTmlPlusAmparsant(body1);
            dspBody2 = StringUtilHtml.transToHTmlPlusAmparsant(body2);
        }

        //訓練モード用の注意文
        if (knrenFlg == GSConstAnpi.KNREN_MODE_ON) {
            body += "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
            body += newLine;
            body += "※" + gsMsg.getMessage("anp.knmode");
            body += newLine;
            body += gsMsg.getMessage("anp.anp060.05");
            body += newLine;
            body += "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
            body += newLine;
            body += newLine;
        }

        if (!StringUtil.isNullZeroString(body1)) {
            body += dspBody1;
            body += newLine;
            body += newLine;
            body += newLine;
        }

        body += getHaisinMessageBodyFixd(reqMdl, cmnConf, anpiSid, userSid, isHtml);

        if (!StringUtil.isNullZeroString(body2)) {
            body += newLine;
            body += newLine;
            body += dspBody2;
        }

        log__.debug(body);
        return body;
    }

    /**
     * <br>[機  能] 安否確認メッセージ配信時のメール本文（固定部）を取得します。
     * <br>[解  説] ユーザ入力の部分を除いた本文を取得します。
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param anpiSid  安否確認SID
     * @param userSid  ユーザSID
     * @param isHtml   HTML表示の場合、true
     * @return 入力用URL
     * @throws Exception 実行時例外
     */
    public String getHaisinMessageBodyFixd(RequestModel reqMdl, Connection con,
                        String anpiSid, String userSid, boolean isHtml)
                throws Exception {

        AnpCmnConfModel cmnConf = getAnpCmnConfModel(con);
        return getHaisinMessageBodyFixd(reqMdl, cmnConf, anpiSid, userSid, isHtml);
    }

    /**
     * <br>[機  能] 安否確認メッセージ配信時のメール本文（固定部）を取得します。
     * <br>[解  説] ユーザ入力の部分を除いた本文を取得します。
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param cmnConf 共通設定情報
     * @param anpiSid  安否確認SID
     * @param userSid  ユーザSID
     * @param isHtml   HTML表示の場合、true
     * @return 入力用URL
     * @throws Exception 実行時例外
     */
    public String getHaisinMessageBodyFixd(
            RequestModel reqMdl, AnpCmnConfModel cmnConf,
                        String anpiSid, String userSid, boolean isHtml)
                throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl);

        //ユーザ接続アドレスを生成
        String urlPC = "";
        String enc = Encoding.UTF_8;

        //管理者設定基本URL取得
        String baseUrl = NullDefault.getString(cmnConf.getApcUrlBase(), "");
        if (baseUrl.length() > 0 && baseUrl.lastIndexOf("/") != baseUrl.length() - 1) {
            baseUrl += "/";
        }
        urlPC += baseUrl;

        //固定部分を追加
        urlPC += GSConstAnpi.URL_REPLY_PC;

        //パラメータ・安否確認SID
        String paramAnpi = URLEncoder.encode(NullDefault.getStringZeroLength(anpiSid, "x"), enc);
        urlPC += "anpiSid=" + paramAnpi;

        //パラメータ・ユーザSID
        String paramUser = URLEncoder.encode(NullDefault.getStringZeroLength(userSid, "x"), enc);
        urlPC += "&userSid=" + paramUser;

        //パラメータ・リモートモード
        urlPC += "&rmode=" + GSConstAnpi.REMOTE_MODE;

        //本文の固定部分を生成
        String body = "";
        String newLine = NEW_LINE;

        if (isHtml) {
            newLine = GSConst.NEW_LINE_STR;
        }

        body += gsMsg.getMessage("anp.cmn.01");
        body += newLine;
        body += newLine;
        body += urlPC;

        log__.debug(body);
        return body;
    }

    /**
     * <br>[機  能] デフォルト表示で表示するグループSIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @param allGrpFlg 全てを表示する場合：true  表示しない場合：false
     * @return グループSID
     * @throws SQLException SQL実行エラー
     */
    public String getDefaultGroupSid(Connection con, int usrSid, boolean allGrpFlg)
            throws SQLException {

        String gsid = "";

        //個人設定より取得
        AnpPriConfModel pribean = getAnpPriConfModel(con, usrSid);

        if ((pribean.getAppDspMygroup() == 0)
                && (pribean.getAppAllGroupFlg() == GSConstAnpi.ALL_GROUP_SELECT)) {
            if (allGrpFlg) {
                gsid = GSConstAnpi.ALL_GROUP_STRING;
            } else {
                //個人設定が全てでも全てを表示しないコンボボックスだった場合はデフォルトグループを指定
                GroupBiz gpBiz = new GroupBiz();
                gsid = String.valueOf(gpBiz.getDefaultGroupSid(usrSid, con));
            }
        } else  if (pribean.getAppDspMygroup() > 0) {
            gsid = GSConstAnpi.MY_GROUP_STRING + String.valueOf(pribean.getAppDspMygroup());
        } else {
            gsid = String.valueOf(pribean.getAppDspGroup());
        }

        log__.debug("デフォルト表示グループID=>" + gsid);
        return gsid;
    }

    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param allGrpFlg 全て表示フラグ   true：表示   false：非表示
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<AnpLabelValueModel> getGroupLabel(RequestModel reqMdl,
                            Connection con, int usrSid, boolean allGrpFlg)
                    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);

        List<AnpLabelValueModel> ret = new ArrayList<AnpLabelValueModel>();

        //全てを追加
        if (allGrpFlg) {
            String textGrpAll = gsMsg.getMessage("cmn.all");
            ret.add(new AnpLabelValueModel(textGrpAll, GSConstAnpi.ALL_GROUP_STRING, "0"));
        }

        //全員で共有するグループリスト取得
        GroupBiz groupBiz = new GroupBiz();
        ArrayList<GroupModel> gpList = groupBiz.getGroupCombList(con);

        for (GroupModel gmodel : gpList) {
            ret.add(new AnpLabelValueModel(gmodel.getGroupName(),
                    String.valueOf(gmodel.getGroupSid()), "0"));
        }

        //マイグループを追加
        List<AnpLabelValueModel> mylabelList = getMyGroupLabel(usrSid, con);
        ret.addAll(0, mylabelList);

        return ret;
    }

    /**
     * <br>[機  能] ユーザIDを指定しマイグループラベルを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return List
     * @throws SQLException SQL実行時例外
     */
    public List<AnpLabelValueModel> getMyGroupLabel(int userSid, Connection con)
                throws SQLException {

        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(userSid);

        //マイグループリストをセット
        List<AnpLabelValueModel> cmgLabelList = new ArrayList<AnpLabelValueModel>();
        for (CmnMyGroupModel cmgMdl : cmgList) {

            cmgLabelList.add(
                    new AnpLabelValueModel(
                            cmgMdl.getMgpName(),
                            GSConstAnpi.MY_GROUP_STRING + String.valueOf(cmgMdl.getMgpSid()),
                            "1")
                            );
        }
        return cmgLabelList;
    }

    /**
     * <br>[機  能] マイグループ又はグループに所属するユーザ情報一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param sessionUsrSid セッションユーザSID
     * @param myGroupFlg マイグループ選択フラグ
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserList(Connection con, int gpSid,
            ArrayList<Integer> usrSids, int sessionUsrSid, boolean myGroupFlg) throws SQLException {

        UserBiz userBiz = new UserBiz();
        return userBiz.getBelongUserList(con, gpSid, usrSids, sessionUsrSid, myGroupFlg);
    }

    /**
     * <br>[機  能] フォーム情報のグループコンボ値がグループSIDがマイグループSIDかを判定する。
     * <br>[解  説] 先頭文字に"M"が有る場合、マイグループSID
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isMyGroupSidforDisp(String gpSid) {

        if (gpSid == null) {
            return false;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf(GSConstAnpi.MY_GROUP_STRING);

        // 先頭文字に"M"が有る場合はマイグループ
        return (index == 0);
    }

    /**
     * <br>[機  能] フォーム情報のグループコンボ値からグループSID又はマイグループSIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又はマイグループSID
     */
    public static int getGroupSidfromDisp(String gpSid) {
        int ret = 0;
        if (StringUtil.isNullZeroString(gpSid)) {
            return ret;
        }

        if (isMyGroupSidforDisp(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }

    /**
     * <br>[機  能] フォーム情報のグループコンボ値からALL(全て)かどうか判断する。
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return ret  true:全て   false：通常 or Myのグループ
     */
    public static boolean isChackAllGrp(String gpSid) {

        if (StringUtil.isNullZeroString(gpSid)) {
            return false;
        }

        return gpSid.equals(GSConstAnpi.ALL_GROUP_STRING);
    }

    /**
     * <br>[機  能] 送信者一覧取得のSqlBufferにorder句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param  con DBコネクション
     * @return SqlBuffer
     * @throws SQLException SQL例外処理
     */
    public static SqlBuffer setUserOrderSQL(SqlBuffer sql, Connection con)
                            throws SQLException {

        //ソートモデルを取得
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        String order;
        if (sql.toSqlString().toLowerCase().indexOf("order by") < 0) {
            sql.addSql(" order by ");
        }

        order = "asc";
        if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   USI_SORTKEY2 " + order);
                break;
            default:
                sql.addSql("   YAKUSYOKU_EXIST asc,");
                sql.addSql("   YAKUSYOKU_SORT asc,");
                sql.addSql("   USI_SEI_KN asc,");
                sql.addSql("   USI_MEI_KN asc");
                break;
        }

        order = "asc";
        if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey2()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   ,USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   ,case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   ,YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   ,USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   ,USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   ,USI_SORTKEY2 " + order);
                break;
            default:
                break;
        }

        return sql;
    }

    /**
     * <br>[機  能] 送信サーバに接続する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return エラー
     * @throws Exception 実行時例外
     */
    public int connectSendServer(Connection con)
                throws Exception {

        AnpCmnConfModel cmnConf = getAnpCmnConfModel(con);
        return connectSendServer(cmnConf, con);
    }

    /**
     * <br>[機  能] 送信サーバに接続する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmnConf 基本共通設定情報MODEL
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @return 成功、エラーフラグ
     * @throws Exception 実行時例外
     */
    public int connectSendServer(
            AnpCmnConfModel cmnConf, Connection con) throws Exception {

        Sender sender = null;

        try {
            sender = __connectSendServer(cmnConf, con);
            if (sender == null) {
                return GSConstAnpi.SENDMSG_ERROR_CONNECT;
            }

        } catch (MessagingException me) {
            log__.fatal("メールサーバへの接続に失敗しました。", me);
            return GSConstAnpi.SENDMSG_ERROR_SEND;
        } catch (Exception e) {
            log__.fatal("メール送信中に例外が発生しました。", e);
            return GSConstAnpi.SENDMSG_ERROR;
        } finally {
            if (sender != null) {
                sender.disConnect();
            }
        }
        return GSConstAnpi.SENDMSG_SUCCESS;
    }

    /**
     * <br>[機  能] 各配信ユーザにメールを送信する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param cmnConf 基本共通設定情報MODEL
     * @param hModel 安否確認配信データMODEL
     * @param mailAdr 配信対象メールアドレス
     * @param usrSid 配信対象ユーザSID
     * @param con コネクション
     * @return エラー
     * @throws Exception 実行時例外
     */
    public int sendMail(RequestModel reqMdl, AnpCmnConfModel cmnConf,
            AnpHdataModel hModel, String mailAdr, String usrSid,
            Connection con) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl);
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        Sender sender = null;

        //訓練モードフラグ
        int knrenFlg = hModel.getAphKnrenFlg();

        try {
            sender = __connectSendServer(cmnConf, con);
            if (sender == null) {
                return GSConstAnpi.SENDMSG_ERROR_CONNECT;
            }

            log__.info("送信先メールアドレス==>" + mailAdr);

            //件名
            String subject;
            if (knrenFlg == GSConstAnpi.KNREN_MODE_ON) {
                subject = "【 " + gsMsg.getMessage("anp.knmode") + " 】" + hModel.getAphSubject();
            } else {
                subject = hModel.getAphSubject();
            }
            log__.info("mail.subject ==>" + subject);
            //メール本文
            String body = anpiBiz.getHaisinMessageBody(reqMdl,
                                            cmnConf,
                                            String.valueOf(hModel.getAphSid()),
                                            String.valueOf(usrSid),
                                            hModel.getAphText1(),
                                            hModel.getAphText2(),
                                            false,
                                            knrenFlg);
            log__.info("mail.body ==>" + body);
            log__.info("メール送信開始");
            sender.send(subject, cmnConf.getApcAddress(), mailAdr, body);
            log__.info("メール送信終了");

        } catch (MessagingException me) {
            log__.fatal("メール送信に失敗しました。", me);
            return GSConstAnpi.SENDMSG_ERROR_SEND;
        } catch (Exception e) {
            log__.fatal("メール送信中に例外が発生しました。", e);
            return GSConstAnpi.SENDMSG_ERROR;
        } finally {
            if (sender != null) {
                sender.disConnect();
            }
        }
        return GSConstAnpi.SENDMSG_SUCCESS;
    }

    /**
     * <br>[機  能] 送信サーバに接続する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cmnConf 基本共通設定情報MODEL
     * @param con コネクション
     * @return エラー
     * @throws Exception 実行時例外
     */
    private Sender __connectSendServer(
            AnpCmnConfModel cmnConf, Connection con) throws Exception {

        Sender sender = null;

        try {
            if (cmnConf.getApcAuthType() == GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) {
                //SMTPサーバー
                String smtpServer = cmnConf.getApcSendHost();
                log__.info("mail.smtp.server ==>" + smtpServer);
                //ポート番号
                int portNumber = cmnConf.getApcSendPort();
                log__.info("portNumber = " + portNumber);
                //送信元メールアドレス
                String fromMail = cmnConf.getApcAddress();
                log__.info("mail.from ==>" + fromMail);

                //認証ユーザID取得
                String userId = cmnConf.getApcSendUser();
                log__.info("smtp.userID ==>" + userId);
                //パスフレーズ取得
                String pass = GSPassword.getDecryPassword(cmnConf.getApcSendPass());
                log__.info("smtp.passphrase ==>" + pass);

                boolean smtp = cmnConf.getApcSmtpAuth() == GSConstAnpi.SMTP_AUTH_YES;

                log__.info("メールサーバーに接続開始");
                //メールサーバーに接続
                Properties prop = new Properties();
                prop.setProperty("mail.smtp.connectiontimeout", "30000");
                prop.setProperty("mail.smtp.timeout", "30000");
                //SSL_TLS通信の場合
                if (cmnConf.getApcSendSsl() == MailEncryptBiz.ANGO_SSL_TLS) {
                    prop.setProperty("mail.smtp.socketFactory.class",
                                                "javax.net.ssl.SSLSocketFactory");
                    prop.setProperty("mail.smtp.socketFactory.fallback", "false");
                    prop.setProperty("mail.smtp.socketFactory.port",
                                    String.valueOf(portNumber));
                } else if (cmnConf.getApcSendSsl() == MailEncryptBiz.ANGO_STARTTLS) {
                  //STARTTLS通信の場合
                    prop.setProperty("mail.smtp.starttls.enable", "true");
                }

                sender = new Sender(prop, smtp);
                sender.connect(smtpServer, portNumber, userId, pass);
            } else {

                Properties prop = new Properties();
                prop.setProperty("mail.smtp.starttls.enable", "true");
                prop.setProperty("mail.smtp.starttls.required", "true");
                prop.setProperty("mail.smtp.sasl.enable", "true");
                sender = new Sender(prop, false);

                OAuthBiz authBiz = new OAuthBiz();
                OauthMailServerModel serverMdl
                                    = authBiz.getSendServerData(con, cmnConf.getCotSid(),
                                        String.valueOf(GroupSession.getContext().get(GSContext.APP_ROOT_PATH)));

                sender.connectOAuth(
                        serverMdl.getHost(),
                        serverMdl.getPort(),
                        serverMdl.getAccountId(),
                        serverMdl.getAccessToken());
            }

            log__.info("メールサーバーに接続完了");

        } catch (MessagingException me) {
            log__.fatal("メールサーバへの接続に失敗しました。", me);
            if (sender != null) {
                sender.disConnect();
                sender = null;
            }
        } catch (Exception e) {
            throw e;
        }
        return sender;
    }

    /**
     * <br>[機  能] 指定したグループSIDからユーザ一覧(ユーザSID)を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSids グループSID(複数)
     * @return ユーザ情報リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getUsrSidList(ArrayList<Integer> grpSids)
            throws SQLException {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (grpSids == null || (grpSids.size() < 1)) {
            return ret;
        }

        for (Integer grpSid : grpSids) {
            List<CmnUsrmInfModel> belongList =
                    getBelongUserList(con__, grpSid, null, -1, false);
            for (CmnUsrmInfModel usrMdl : belongList) {
                ret.add(usrMdl.getUsrSid());
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 安否全般のログ出力を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }
        GsMessage gsMsg = new GsMessage(reqMdl);
        //メッセージ日報
        String anpi = gsMsg.getMessage("anp.plugin");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstAnpi.PLUGIN_ID);
        logMdl.setLogPluginName(anpi);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), gsMsg));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con__);
        logBiz.outPutLog(logMdl, reqMdl.getDomain());
    }

    /**
     * <br>[機  能] 安否全般のログ出力を行う(セッションの未確立)
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param usrSid ユーザSID
     */
    public void outPutLogNoSec(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            int usrSid) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        //メッセージ安否
        String anpi = gsMsg.getMessage("anp.plugin");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstAnpi.PLUGIN_ID);
        logMdl.setLogPluginName(anpi);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), gsMsg));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con__);
        logBiz.outPutLog(logMdl, reqMdl.getDomain());
    }

    /**
     * <br>[機  能] プログラムIDからプログラム名称を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param id アクションID
     * @param gsMsg GSメッセージ
     * @return String
     */
    public String getPgName(String id, GsMessage gsMsg) {
        String ret = new String();
        if (StringUtil.isNullZeroString(id)) {
            return ret;
        }

        log__.info("プログラムID==>" + id);

        if (id.equals("jp.groupsession.v2.anp.anp010.Anp010Action")) {
            return gsMsg.getMessage("anp.logmsg.anp010");
        }

        if (id.equals("jp.groupsession.v2.anp.anp020.Anp020Action")) {
            return gsMsg.getMessage("anp.logmsg.anp020");
        }

        if (id.equals("jp.groupsession.v2.anp.anp020kn.Anp020knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp020");
        }

        if (id.equals("jp.groupsession.v2.anp.anp021.Anp021Action")) {
            return gsMsg.getMessage("anp.logmsg.anp021");
        }

        if (id.equals("jp.groupsession.v2.anp.anp021kn.Anp021knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp021");
        }

        if (id.equals("jp.groupsession.v2.anp.anp040.Anp040Action")) {
            return gsMsg.getMessage("anp.logmsg.anp040");
        }

        if (id.equals("jp.groupsession.v2.anp.anp040kn.Anp040knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp040");
        }

        if (id.equals("jp.groupsession.v2.anp.anp050.Anp050Action")) {
            return gsMsg.getMessage("cmn.preferences2") + " "
                        + gsMsg.getMessage("anp.logmsg.anp050");
        }

        if (id.equals("jp.groupsession.v2.anp.anp050kn.Anp050knAction")) {
            return gsMsg.getMessage("cmn.preferences.kn");
        }

        if (id.equals("jp.groupsession.v2.anp.anp060.Anp060Action")) {
            return gsMsg.getMessage("anp.logmsg.anp060");
        }

        if (id.equals("jp.groupsession.v2.anp.anp060kn.Anp060knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp060");
        }

        if (id.equals("jp.groupsession.v2.anp.anp080.Anp080Action")) {
            return gsMsg.getMessage("anp.logmsg.anp080");
        }

        if (id.equals("jp.groupsession.v2.anp.anp080kn.Anp080knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp080");
        }

        if (id.equals("jp.groupsession.v2.anp.anp090.Anp090Action")) {
            return gsMsg.getMessage("anp.logmsg.anp090");
        }

        if (id.equals("jp.groupsession.v2.anp.anp100.Anp100Action")) {
            return gsMsg.getMessage("anp.logmsg.anp100");
        }

        if (id.equals("jp.groupsession.v2.anp.anp100kn.Anp100knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp100");
        }

        if (id.equals("jp.groupsession.v2.anp.anp120.Anp120Action")) {
            return gsMsg.getMessage("anp.logmsg.anp120");
        }

        if (id.equals("jp.groupsession.v2.anp.anp120kn.Anp120knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp120");
        }

        if (id.equals("jp.groupsession.v2.anp.anp121.Anp121Action")) {
            return gsMsg.getMessage("anp.logmsg.anp121");
        }

        if (id.equals("jp.groupsession.v2.anp.anp121kn.Anp121knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp121");
        }

        if (id.equals("jp.groupsession.v2.anp.anp130.Anp130Action")) {
            return gsMsg.getMessage("anp.logmsg.anp130");
        }

        if (id.equals("jp.groupsession.v2.anp.anp140.Anp140Action")) {
            return gsMsg.getMessage("anp.logmsg.anp140");
        }

        if (id.equals("jp.groupsession.v2.anp.anp150.Anp150Action")) {
            return gsMsg.getMessage("anp.logmsg.anp150");
        }

        if (id.equals("jp.groupsession.v2.anp.anp150kn.Anp150knAction")) {
            return gsMsg.getMessage("anp.logmsg.anp150");
        }

       return ret;
    }
    /**
    *
    * <br>[機  能] 選択した値がグループコンボ上にない場合に有効な値を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param list ラベルリスト
    * @param nowSel 選択中ラベルID
    * @param defSel 初期ラベルID
    * @return 有効な選択値
    */
   public String getEnableSelectGroup(List<AnpLabelValueModel> list
           , String nowSel
           , String defSel) {
       boolean nowVar = false;
       boolean defVar = false;
       if (list == null || list.size() <= 0) {
           return "";
       }
       for (LabelValueBean label : list) {
           if (label.getValue().equals(nowSel)) {
               nowVar = true;
               break;
           }
           if (label.getValue().equals(defSel)) {
               defVar = true;
           }
       }
       if (nowVar) {
           return nowSel;
       }
       if (defVar) {
           return defSel;
       }
       return list.get(0).getValue();
   }
}

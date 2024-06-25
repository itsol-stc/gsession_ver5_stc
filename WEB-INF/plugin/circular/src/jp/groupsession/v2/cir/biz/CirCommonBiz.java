package jp.groupsession.v2.cir.biz;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirAccountUserDao;
import jp.groupsession.v2.cir.dao.CirAconfDao;
import jp.groupsession.v2.cir.dao.CirArestDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirInfLabelDao;
import jp.groupsession.v2.cir.dao.CirInitDao;
import jp.groupsession.v2.cir.dao.CirLabelDao;
import jp.groupsession.v2.cir.dao.CirLogCountDao;
import jp.groupsession.v2.cir.dao.CirLogCountSumDao;
import jp.groupsession.v2.cir.dao.CirUserDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirAccountUserModel;
import jp.groupsession.v2.cir.model.CirAconfModel;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cir.model.CirLabelModel;
import jp.groupsession.v2.cir.model.CirLogCountModel;
import jp.groupsession.v2.cir.model.CirLogCountSumModel;
import jp.groupsession.v2.cir.model.CirUserModel;
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.cir.util.CirConfigBundle;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板プラグインに関する共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirCommonBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public CirCommonBiz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public CirCommonBiz(Connection con) {
        con__ = con;
    }

    /**
     * 回覧板の管理者設定を取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @return SmlAdminModel 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public CirInitModel getCirInitConf(int userSid, Connection con) throws SQLException {
        CirInitModel ret = null;
        CirInitDao dao = new CirInitDao(con);
        ret = dao.select();
        if (ret == null) {
            ret = new CirInitModel();
            ret.setCinInitSetKen(GSConstCircular.CIR_INIEDIT_STYPE_USER);
            ret.setCinMemoKbn(GSConstCircular.CIR_INIT_MEMO_CHANGE_NO);
            ret.setCinMemoDay(GSConstCircular.CIR_INIT_MEMO_ONEWEEK);
            ret.setCinKouKbn(GSConstCircular.CIR_INIT_SAKI_PUBLIC);
            ret.setCinAutoDelKbn(0);
            ret.setCinAcntMake(0);
            ret.setCinAcntUser(GSConstCircular.CIN_ACNT_USER_NO);
            ret.setCinAuid(userSid);
            ret.setCinAdate(new UDate());
            ret.setCinEuid(userSid);
            ret.setCinEdate(new UDate());
        }
        return ret;
    }

    /**
     * <br>[機  能] 回覧板:未確認の受信回覧板件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usid ユーザSID
     * @param con コネクション
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行時例外
     */
    public int getUnopenedCirCnt(int usid, Connection con) throws SQLException {

        log__.debug("未確認の受信回覧板件数取得");

        int cnt = 0;

        CirViewDao cvDao = new CirViewDao(con);
        cnt = cvDao.getUnopenedCirCnt(usid);

        log__.debug("未確認の受信回覧板件数 = " + cnt);

        return cnt;
    }

    /**
     * <br>[機  能] 回覧板:未確認の受信回覧板件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usid ユーザSID
     * @param con コネクション
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行時例外
     */
    public int getUnopenedGomiCirCnt(int usid, Connection con) throws SQLException {

        log__.debug("未確認のゴミ箱受信回覧板件数取得");

        int cnt = 0;

        CirViewDao cvDao = new CirViewDao(con);
        cnt = cvDao.getUnopenedGomiCirCnt(usid);

        log__.debug("未確認のゴミ箱受信回覧板件数 = " + cnt);

        return cnt;
    }


    /**
     * <br>[機  能] 回覧板個人設定から最大表示件数を取得する
     * <br>[解  説] 取得できない場合は初期値(10件)とする
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con コネクション
     * @return int 最大表示件数
     * @throws SQLException SQL実行時例外
     */
    public int getCountLimit(int usid, Connection con) throws SQLException {

        CirUserDao cuDao = new CirUserDao(con);
        CirUserModel cuMdl = cuDao.getCirUserInfo(usid);
        int limit = GSConst.LIST_COUNT_LIMIT;
        if (cuMdl != null) {
            limit = cuMdl.getCurMaxDsp();
        }
        return limit;
    }

    /**
     * <br>[機  能] 検索対象のデフォルト値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトターゲット配列
     */
    public static String[] getDefultSearchTarget() {
        String[] targets = {
                String.valueOf(GSConstCircular.SEARCH_TARGET_TITLE),
                String.valueOf(GSConstCircular.SEARCH_TARGET_BODY)
              };
        return targets;
    }

    /**
     * <br>[機  能] 回覧板URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param infSid 回覧板SID
     * @param hantei 画面遷移先
     * @return 回覧板URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createThreadUrl(RequestModel reqMdl, int infSid, int hantei)
    throws UnsupportedEncodingException {

        return createThreadUrlPlusAccount(reqMdl, infSid, hantei, -1);
    }

    /**
     * <br>[機  能] 回覧板URLを取得する
     * <br>[解  説] 完了通知メール用にアカウントSIDをパラメータに設定
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param infSid 回覧板SID
     * @param hantei 画面遷移先
     * @param accountSid アカウントSID
     * @return 回覧板URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createThreadUrlPlusAccount(
            RequestModel reqMdl, int infSid, int hantei, int accountSid)
            throws UnsupportedEncodingException {

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl);
            paramUrl += "/" + GSConstCircular.PLUGIN_ID_CIRCULAR;
            //遷移先を指定
            if (hantei == GSConstCircular.SMAIL_DSP_KAKUNIN) {
                paramUrl += "/cir020.do";
            } else if (hantei == GSConstCircular.SMAIL_DSP_JYOUKYOU) {
                paramUrl += "/cir030.do";
            }
            paramUrl += "?CMD=" + GSConstCircular.CMD_SMAIL;
            paramUrl += "&cir010selectInfSid=" + infSid;

            if (hantei == GSConstCircular.SMAIL_DSP_JYOUKYOU) {
                paramUrl += "&cirViewAccount=" + accountSid;
            }

            return urlBiz.getAccessUrl(reqMdl, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }

    /**
     * 回覧板全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
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
        outPutLog(map, reqMdl, opCode, level, value, null, GSConstCircular.CIR_LOG_FLG_NONE);
    }

    /**
     * 回覧板全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            int logFlg) {
        outPutLog(map, reqMdl, opCode, level, value, -1, null, logFlg);
    }

    /**
     * 回覧板全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     *  @param fileId 添付ファイルSID
     *  @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            String fileId,
            int logFlg) {
        outPutLog(map, reqMdl, opCode, level, value, -1, fileId, logFlg);
    }

    /**
     * 回覧板全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param cirId 回覧板SID
     * @param fileId 添付ファイルSID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            int cirId,
            String fileId,
            int logFlg) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textCir = gsMsg.getMessage("cir.5");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstCircular.PLUGIN_ID_CIRCULAR);
        logMdl.setLogPluginName(textCir);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), reqMdl, logFlg));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (logFlg == GSConstCircular.CIR_LOG_FLG_DOWNLOAD) {
            logMdl.setLogCode("binSid：" + fileId);
        } else if (logFlg == GSConstCircular.CIR_LOG_FLG_PDF) {
            logMdl.setLogCode("PDF出力 cirSid：" + cirId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @param reqMdl リクエスト情報
     * @param logFlg ログ出力判別フラグ
     * @return String
     */
    public String getPgName(String id, RequestModel reqMdl, int logFlg) {
        String ret = new String();
        if (id == null) {
            return ret;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);

        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.cir.cir010.Cir010Action")) {
            String textTitle = gsMsg.getMessage("cir.44");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir020.Cir020Action")) {
            String textTitle = gsMsg.getMessage("cir.45");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir030.Cir030Action")) {
            String textTitle = gsMsg.getMessage("cir.46");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir040.Cir040Action")) {
            String textTitle = gsMsg.getMessage("cir.47");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir050.Cir050Action")) {
            String textTitle = gsMsg.getMessage("cir.48");
            String textKojinSettei = gsMsg.getMessage("cmn.preferences");
            return textKojinSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir080kn.Cir080knAction")) {
            String textTitle = gsMsg.getMessage("cir.29");
            String textKojinSettei = gsMsg.getMessage("cmn.preferences");
            return textKojinSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir090kn.Cir090knAction")) {
            String textTitle = gsMsg.getMessage("cir.31");
            String textKojinSettei = gsMsg.getMessage("cmn.preferences");
            return textKojinSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir110kn.Cir110knAction")) {
            String textTitle = gsMsg.getMessage("cir.29");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir120kn.Cir120knAction")) {
            String textTitle = gsMsg.getMessage("cir.31");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir140kn.Cir140knAction")) {
            String textTitle = gsMsg.getMessage("cir.32");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir150.Cir150Action")) {
            String textTitle = gsMsg.getMessage("cir.cir150.1");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir160.Cir160Action")) {
            String textTitle = gsMsg.getMessage("cir.cir160.1");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir160kn.Cir160knAction")) {
            String textTitle = gsMsg.getMessage("cir.cir160kn.1");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir170.Cir170Action")) {
            String textTitle = gsMsg.getMessage("cir.cir170.1");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir170kn.Cir170knAction")) {
            String textTitle = gsMsg.getMessage("cir.cir170kn.1");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir180.Cir180Action")) {
            String textTitle = gsMsg.getMessage("cir.cir180.1");
            String textKanriSettei = gsMsg.getMessage("cmn.preferences");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir190kn.Cir190knAction")) {
            String textTitle = gsMsg.getMessage("cmn.preferences.kn");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir200kn.Cir200knAction")) {
            String textTitle = gsMsg.getMessage("cmn.sml.notification.setting");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir220kn.Cir220knAction")) {
            String textTitle = gsMsg.getMessage("cir.cir220kn.1");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir230.Cir230Action")) {
            String textTitle = gsMsg.getMessage("cmn.label.management");
            String textSettei = __getSetteiKbn(logFlg, gsMsg);
            return textSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.cir.cir240kn.Cir240knAction")) {
            String textTitle = gsMsg.getMessage("cmn.chk.label.for.editing");
            String textSettei = __getSetteiKbn(logFlg, gsMsg);
            return textSettei + " " + textTitle;
        }
        return ret;
    }

    /**
     * ログ出力判別フラグから設定区分を取得する
     * @param logFlg ログ出力判別フラグ
     * @param gsMsg GsMessage
     * @return String
     */
    private String __getSetteiKbn(int logFlg, GsMessage gsMsg) {
        if (logFlg == GSConstCircular.CIR_LOG_FLG_ADMIN) {
            return gsMsg.getMessage("cmn.admin.setting");
        } else {
            return gsMsg.getMessage("cmn.preferences");
        }
    }

    /**
     * <br>[機  能] グループフィルタコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return グループフィルタコンボ
     * @throws SQLException SQL実行時例外
     */
    public List<CmnLabelValueModel> getGrpFilterCombo(Connection con, RequestModel reqMdl)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);

        String textAll = gsMsg.getMessage("cmn.all");

        //発信者グループを取得
        GroupBiz gpBiz = new GroupBiz();

        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(reqMdl.getSmodel().getUsrsid());

        List<CmnLabelValueModel> dspGrpList = new ArrayList<CmnLabelValueModel>();

        //選択して下さい
        dspGrpList.add(
                new CmnLabelValueModel(
                        textAll, String.valueOf(GSConstCircular.GRPFILTER_ALL), "0"));

        //代表アカウント
        dspGrpList.add(
                new CmnLabelValueModel("代表アカウント", GSConstCircular.CIR_ACCOUNT_STR, "2"));

        //マイグループリストをセット
        for (CmnMyGroupModel cmgMdl : cmgList) {
            dspGrpList.add(
                    new CmnLabelValueModel(
                            cmgMdl.getMgpName(), "M" + String.valueOf(cmgMdl.getMgpSid()), "1"));
        }


        List<LabelValueBean> grpLabelList = gpBiz.getGroupCombLabelList(con, false, gsMsg);
        for (LabelValueBean bean : grpLabelList) {
            dspGrpList.add(
                    new CmnLabelValueModel(bean.getLabel(), bean.getValue(), "0"));

        }

        return dspGrpList;
    }

    /**
     * <br>[機  能] 受信回覧板を論理削除する。
     * <br>[解  説] 日次バッチで使用する。
     * <br>[備  考]
     * @param con コネクション
     * @param delMdl CirAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行時例外
     */
    public void deleteView(Connection con,
            CirAdelModel delMdl,
            int kbn) throws SQLException {

        CirViewDao viewDao = new CirViewDao(con);

        //削除する回覧板SIDリストを取得する。
        List<CirViewModel> allDelList = viewDao.getDeleteCir(delMdl, kbn);

        if (allDelList == null || allDelList.size() < 1) {
            return;
        }
        // ラベル情報削除
        CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
        labelViewDao.deleteLabel(allDelList, delMdl, kbn);

        int i = 0;
        int delCount = GSConstCircular.CIR_BATCH_DELETE_COUNT;
        List<CirViewModel> updateList = new ArrayList<CirViewModel>();
        for (CirViewModel model : allDelList) {

            updateList.add(model);
            i++;

            if (i >= delCount) {

                //論理削除する。
                viewDao.delete(updateList);

                updateList = new ArrayList<CirViewModel>();
                i = 0;
            }
        }

        if (updateList != null && updateList.size() > 0) {
            //論理削除する。
            viewDao.delete(updateList);
        }
    }

    /**
     * <br>[機  能] 送信回覧板を論理削除する。
     * <br>[解  説] 日次バッチで使用する。
     * <br>[備  考]
     * @param con コネクション
     * @param delMdl CirAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行時例外
     */
    public void deleteInf(Connection con,
            CirAdelModel delMdl,
            int kbn) throws SQLException {

        CirInfDao infDao = new CirInfDao(con);

        //削除する回覧板SIDリストを取得する。
        List<Integer> allDelList = infDao.getDeleteCir(delMdl, kbn);

        if (allDelList == null || allDelList.size() < 1) {
            return;
        }
        CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
        labelInfDao.deleteLabel(allDelList);

        int i = 0;
        int delCount = GSConstCircular.CIR_BATCH_DELETE_COUNT;
        List<Integer> updateList = new ArrayList<Integer>();
        for (Integer cifSid : allDelList) {

            updateList.add(cifSid);
            i++;

            if (i >= delCount) {

                //論理削除する。
                infDao.delete(updateList);

                updateList = new ArrayList<Integer>();
                i = 0;
            }
        }

        if (updateList != null && updateList.size() > 0) {
            //論理削除する。
            infDao.delete(updateList);
        }
    }

    /**
     * <br>[機  能] 受信回覧板を物理削除する。
     * <br>[解  説] 日次バッチで使用する。
     * <br>[備  考]
     * @param con コネクション
     * @param cirSids 削除回覧板リスト
     * @throws SQLException SQL実行時例外
     */
    public void deleteView(Connection con,
            String[] cirSids) throws SQLException {

        CirViewDao viewDao = new CirViewDao(con);

        List<CirViewModel> allDelList = viewDao.select(cirSids);

        if (allDelList == null || allDelList.size() < 1) {
            return;
        }

        int i = 0;
        int delCount = GSConstCircular.CIR_BATCH_DELETE_COUNT;
        List<CirViewModel> deleteList = new ArrayList<CirViewModel>();
        for (CirViewModel model : allDelList) {

            deleteList.add(model);
            i++;

            if (i >= delCount) {

                //物理削除する。
                viewDao.deleteData(deleteList);

                deleteList = new ArrayList<CirViewModel>();
                i = 0;
            }
        }

        if (deleteList != null && deleteList.size() > 0) {
            //物理削除する。
            viewDao.deleteData(deleteList);
        }
    }

    /**
     * <br>[機  能] 指定したユーザが回覧先の確認状況を確認可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cifSid 回覧板SID
     * @param cacSid アカウントSID
     * @return true:確認可能 false:確認不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canBrowseCirRoute(Connection con, int cifSid, int cacSid)
    throws SQLException {

        //指定された回覧板が"送信済回覧板"かを判定
        CirInfDao infDao = new CirInfDao(con);
        boolean result = infDao.isSendCircular(cifSid, cacSid);
        if (!result) {
            //回覧先確認状況 = "公開" かを判定
            CirInfModel infMdl = infDao.getCirInfo(cifSid, GSConstCircular.DSPKBN_DSP_OK);
            result = (infMdl != null
                    && infMdl.getCifShow() == GSConstCircular.CIR_INIT_SAKI_PUBLIC);
        }

        return result;
    }

    /**
     * デフォルトアカウント情報を設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return sacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public int getDefaultAccount(Connection con, int usrSid) throws SQLException {

        int accountSid = -1;

        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = null;

        //デフォルトのアカウントを取得
        cacMdl = cacDao.selectFromUsrSid(usrSid);

        if (cacMdl != null) {
            accountSid = cacMdl.getCacSid();
        }

        return accountSid;
    }

    /**
     * 使用可能なアカウントかを調べる
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param accountSid アカウントSID
     * @return accountUseFlg
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseAccount(Connection con, int usrSid, int accountSid)
                                                         throws SQLException {

        boolean accountUseFlg = false;
        CircularDao cacDao = new CircularDao(con);

        //使用可能なアカウントか判定
        accountUseFlg = cacDao.canUseCheckAccount(usrSid, accountSid);

        return accountUseFlg;

    }

    /**
     * <br>[機  能] ユーザSIDからアカウントSIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSids ユーザSID
     * @return accountSids アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public String[] getAccountSidFromUsr(Connection con, String[] usrSids)
    throws SQLException {


        List<String> newUserSid = new ArrayList<String>();
        List<String> accountUserSid = new ArrayList<String>();

        for (String usid : usrSids) {
            if (usid.indexOf(GSConstCircular.CIR_ACCOUNT_STR) != -1) {
                //作成アカウント
                accountUserSid.add(usid.substring(GSConstCircular.CIR_ACCOUNT_STR.length()));
            } else {
                //GSユーザ
                newUserSid.add(usid);
            }
        }

        List<String> accountSids = new ArrayList<String>();
        List<String> usrSidsList = null;

        if (newUserSid != null && newUserSid.size() > 0) {
            //GSユーザのアカウントSIDを取得
            CirAccountDao accountDao = new CirAccountDao(con);
            usrSidsList = accountDao.getAccountSidFromUsrSids(
                    (String[]) newUserSid.toArray(new String[newUserSid.size()]));
            if (usrSidsList != null && !usrSidsList.isEmpty()) {
                accountSids.addAll(usrSidsList);
            }
        }

        if (accountUserSid != null && accountUserSid.size() > 0) {
            //アカウントSID
            accountSids.addAll(accountUserSid);
        }

        return (String[]) accountSids.toArray(new String[accountSids.size()]);
    }

    /**
     * <br>[機  能] 選択されたSIDからユーザSIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSids ユーザSID
     * @return accountSids アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public String[] getUsrSidFromSelSid(Connection con, String[] usrSids)
    throws SQLException {


        List<String> userSid = new ArrayList<String>();
        List<String> accountUserSid = new ArrayList<String>();

        for (String usid : usrSids) {
            if (usid.indexOf(GSConstCircular.CIR_ACCOUNT_STR) != -1) {
                //作成アカウント
                accountUserSid.add(usid.substring(GSConstCircular.CIR_ACCOUNT_STR.length()));
            } else {
                //GSユーザ
                userSid.add(usid);
            }
        }

        if (accountUserSid != null && accountUserSid.size() > 0) {
            List<CirAccountUserModel> accountUsrList = new ArrayList<CirAccountUserModel>();
            List<String> grpSidList = new ArrayList<String>();
            CirAccountUserDao accountUsrDao = new CirAccountUserDao(con);

            for (String cacSid : accountUserSid) {

                if (ValidateUtil.isAlphaNum(cacSid) && Integer.valueOf(cacSid) > 0) {
                  //アカウントSIDからユーザSIDを取得
                    accountUsrList = accountUsrDao.getAccountUserList(Integer.valueOf(cacSid));

                    for (CirAccountUserModel cacUsrMdl : accountUsrList) {
                        if (cacUsrMdl.getUsrSid() > 0) {
                            userSid.add(String.valueOf(cacUsrMdl.getUsrSid()));
                        } else if (cacUsrMdl.getGrpSid() > 0) {
                            grpSidList.add(String.valueOf(cacUsrMdl.getGrpSid()));
                        }
                    }

                    if (grpSidList != null && !grpSidList.isEmpty()) {
                        CmnBelongmDao bdao = new CmnBelongmDao(con);
                            userSid.addAll(bdao.select(
                                    (String[]) grpSidList.toArray(new String[grpSidList.size()])));
                    }
                }
            }
        }

        return (String[]) userSid.toArray(new String[userSid.size()]);
    }
    /**
     *
     * <br>[機  能] アカウントSIDからユーザSIDを取得
     * <br>[解  説]
     * <br>[備  考] 重複するユーザは除外する
     * @param con コネクション
     * @param accountUserSid アカウントSID
     * @return usrSidList ユーザSIDリスト
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getUsrSidListFromAccSid(Connection con,
            List<Integer> accountUserSid) throws SQLException {
        //アカウントの使用可能ユーザを取得
        HashSet<Integer> userSid = new HashSet<Integer>();
        if (accountUserSid != null && accountUserSid.size() > 0) {
            List<CirAccountUserModel> accountUsrList = new ArrayList<CirAccountUserModel>();
           ArrayList<Integer> grpSidList = new ArrayList<>();
            CirAccountUserDao accountUsrDao = new CirAccountUserDao(con);

            for (int cacSid : accountUserSid) {

                  //アカウントSIDからユーザSIDを取得
                accountUsrList = accountUsrDao.getAccountUserList(cacSid);

                for (CirAccountUserModel cacUsrMdl : accountUsrList) {
                    if (cacUsrMdl.getUsrSid() > 0) {
                        userSid.add(cacUsrMdl.getUsrSid());
                    } else if (cacUsrMdl.getGrpSid() > 0) {
                        grpSidList.add(cacUsrMdl.getGrpSid());
                    }
                }

                if (grpSidList != null && !grpSidList.isEmpty()) {
                    UserSearchDao usrDao = new UserSearchDao(con);
                    userSid.addAll(usrDao.getBelongUserSids(grpSidList, null));
                }
            }
        }
        return new ArrayList<>(userSid);
    }

    /**
     * <br>[機  能] アカウントの編集が可能なユーザかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param userSid セッションユーザSID
     * @return true:編集可能 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditAccount(Connection con, int cacSid, int userSid) throws SQLException {

        //使用者かを判定する
        CircularDao cirDao = new CircularDao(con);
        return cirDao.canUseCheckAccount(userSid, cacSid);
    }

    /**
     * <br>[機  能] 添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 添付ファイル情報
     * @throws Exception 実行時例外
     */
    public List<Cmn110FileModel> getFileData(String tempDir) throws Exception {

        List<Cmn110FileModel> fileDataList = new ArrayList<Cmn110FileModel>();
        List<String> fileNameList = IOTools.getFileNames(tempDir);

        if (fileNameList != null) {
            for (String fileName : fileNameList) {
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                //表示用リストへ追加
                fileDataList.add((Cmn110FileModel) fObj);
            }
        }

        return fileDataList;
    }

    /**
     * <br>[機  能] ファイルコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return ファイルコンボ
     * @throws Exception 実行時例外
     */
    public List<LabelValueBean> getFileCombo(String tempDir) throws Exception {

        ArrayList<LabelValueBean> fileCombo = new ArrayList<LabelValueBean>();
        List<Cmn110FileModel> fileDataList = getFileData(tempDir);
        if (fileDataList != null && !fileDataList.isEmpty()) {
            for (Cmn110FileModel fileData : fileDataList) {
                fileCombo.add(new LabelValueBean(fileData.getFileName(),
                                                fileData.getSaveFileName()));
            }
        }

        return fileCombo;
    }

    /**
     * <br>[機  能] 回覧板管理者設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUserId セッションユーザSID
     * @return 回覧板管理者設定情報
     * @throws SQLException SQL実行例外
     */
    public CirAconfModel getCirAdminData(Connection con, int sessionUserId) throws SQLException {
        CirAconfDao admDao = new CirAconfDao(con);
        CirAconfModel admMdl = admDao.select();
        if (admMdl == null) {
            //取得できなかった場合は初期値を設定する
            admMdl = new CirAconfModel();

            admMdl.setCafSmailSendKbn(GSConstCircular.CAF_SML_NTF_USER);
            admMdl.setCafSmailSend(GSConstCircular.CAF_SML_NTF_KBN_YES);
            UDate now = new UDate();
            admMdl.setCafAdate(now);
            admMdl.setCafAuid(sessionUserId);
            admMdl.setCafEdate(now);
            admMdl.setCafEuid(sessionUserId);
            admMdl.setCafArestKbn(GSConstCircular.CAF_AREST_KBN_ALL);

            boolean commit = false;
            try {
                admDao.insert(admMdl);
                commit = true;
            } catch (SQLException e) {
                log__.error("回覧板-管理者設定の登録に失敗しました");
                throw e;
            } finally {
                if (commit) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }

        return admMdl;
    }

    /**
     * <br>[機  能] 個人設定でショートメール通知設定が可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canSetSmailConf(Connection con, int userSid) throws SQLException {
        CirAconfModel admData = getCirAdminData(con, userSid);
        return admData.getCafSmailSendKbn() != GSConstCircular.CAF_SML_NTF_ADMIN;
    }

    /**
     * <br>[機  能] 管理者設定でショートメール通知が許可されているが判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return true:通知可能 false:通知不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canSendSmail(Connection con, int userSid) throws SQLException {
        CirAconfModel admData = getCirAdminData(con, userSid);
        return admData.getCafSmailSendKbn() != GSConstCircular.CAF_SML_NTF_ADMIN
                || admData.getCafSmailSend() == GSConstCircular.CAF_SML_NTF_KBN_YES;
    }

    /**
     * <br>[機  能] アカウントが回覧板の添付ファイルを参照可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cifSid 回覧板SID
     * @param binSid バイナリ―SID
     * @param userSid ユーザSID
     * @param accountSid アカウントSID
     * @return true: 参照可能 false: 参照不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canDownloadCirBindata(
            Connection con, int cifSid, long binSid, int userSid, int accountSid)
    throws SQLException {

        //アカウントが使用可能かチェックする
        if (!canUseAccount(con, userSid, accountSid)) {
            return false;
        }

        CircularDao cirDao = new CircularDao(con);

        return cirDao.canViewCirTempfile(cifSid, binSid, accountSid);
    }

    /**
     * <br>[機  能] 送信回覧板の集計データを登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param clcCnt 回覧板送信件数
     * @param sendTime 送信日時
     *
     * @throws SQLException SQL実行時例外
     */
    public void regScirLogCnt(
            Connection con, int cacSid, int clcCnt, UDate sendTime)
                    throws SQLException {

        __registLogCnt(con, cacSid, GSConstCircular.LOG_COUNT_KBN_SCIR, clcCnt, sendTime);
    }

    /**
     * <br>[機  能] 受信回覧板の集計データを登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param sendTime 受信日時
     *
     * @throws SQLException SQL実行時例外
     */
    public void regJcirLogCnt(
            Connection con, int cacSid, UDate sendTime)
                    throws SQLException {

        __registLogCnt(con, cacSid, GSConstCircular.LOG_COUNT_KBN_JCIR, 1, sendTime);
    }

    /**
     * <br>[機  能] 回覧板 集計データを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param clcKbn ログ区分（0:送信 1:受信）
     * @param clcCnt 回覧板送信 or 受信 件数
     * @param sendDate 送信日時
     * @throws SQLException SQL実行時例外
     */
    private void __registLogCnt(
            Connection con, int cacSid, int clcKbn, int clcCnt, UDate sendDate)
                    throws SQLException {
        CirLogCountModel clcMdl = new CirLogCountModel();
        clcMdl.setCacSid(cacSid);
        clcMdl.setClcKbn(clcKbn);
        clcMdl.setClcCnt(clcCnt);
        clcMdl.setClcDate(sendDate);

        CirLogCountDao dao = new CirLogCountDao(con);
        dao.insert(clcMdl);

        //集計
        CirLogCountSumDao logSumDao = new CirLogCountSumDao(con);
        CirLogCountSumModel logSumMdl = logSumDao.getSumLogCount(clcMdl, sendDate);
        if (logSumMdl != null) {
            if (logSumDao.update(logSumMdl) == 0) {
                logSumDao.insert(logSumMdl);
            }
        }

    }
    /**
     * <br>[機  能] 指定したユーザのアカウント情報の一覧を取得する
     * <br>[解  説] 共通設定のソート順が反映される
     * <br>[備  考]
     * @param con コネクション
     * @param usrSids ユーザSID
     * @return List in CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountModel> getAccountList(Connection con,
            String[] usrSids) throws SQLException {
        List<Integer> newUserSid = new ArrayList<Integer>();
        List<String> newUserSidStr = new ArrayList<String>();
        List<String> accountSids = new ArrayList<String>();
        List<CirAccountModel> ret = new ArrayList<CirAccountModel>();
        for (String usid : usrSids) {
            if (usid.indexOf(GSConstCircular.CIR_ACCOUNT_STR) != -1) {
                //代表アカウント
                accountSids.add(usid.substring(GSConstCircular.CIR_ACCOUNT_STR.length()));
            } else {
                //GSユーザ
                newUserSidStr.add(usid);
                newUserSid.add(Integer.parseInt(usid));
            }
        }
        //GSユーザ
        CirAccountDao accDao = new CirAccountDao(con);
        List<CirAccountModel> usrAccList = accDao.selectFromUsrSids(
                newUserSidStr.toArray(new String[newUserSidStr.size()]));
        for (CirAccountModel mdl : usrAccList) {
            ret.add(mdl);
        }

        //代表アカウント取得
        if (accountSids.size() > 0) {
            List<CirAccountModel> accList = accDao.getAccountList(
                    accountSids.toArray(new String[accountSids.size()]));
            ret.addAll(accList);
        }
        return ret;

    }
    /**
     * <br>[機  能] 指定されたユーザが回覧板の管理者ユーザ(システム管理者含む)かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return boolean 管理者権限 有り:true, 無し:false
     * @throws SQLException SQL実行時例外
     */
    public static boolean isGsCirAdmin(RequestModel reqMdl, Connection con) throws SQLException {
        // セッションユーザ情報を取得する
        BaseUserModel usModel = reqMdl.getSmodel();

        // GS管理者かどうか判別
        CommonBiz cmnBiz = new CommonBiz();
        return cmnBiz.isPluginAdmin(con, usModel, GSConstCircular.PLUGIN_ID_CIRCULAR);
    }

    /**
     * <br>[機  能] 指定したユーザが回覧板を作成作成可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return true: 作成可能 false: 作成不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canCreateUserCir(Connection con, RequestModel reqMdl)
    throws SQLException {
        if (isGsCirAdmin(reqMdl, con)) {
            return true;
        }

        int userSid = reqMdl.getSmodel().getUsrsid();
        CirAconfModel aconf = getCirAdminData(con, userSid);
        if (aconf.getCafArestKbn() == GSConstCircular.CAF_AREST_KBN_ALL) {
            return true;
        }

        CirArestDao restDao = new CirArestDao(con);
        return restDao.existUser(userSid);
    }

    /**
     *
     * <br>[機  能]指定された回覧板が表示可能かどうかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cifSid 回覧板SID
     * @param cacSid アカウントSID
     * @return false:論理削除済み
     * @throws SQLException SQL実行例外
     */
    public boolean checkExistence(Connection con, int cifSid, int cacSid)
            throws SQLException {

        //指定された回覧板が表示可能かどうかを判定
        CirViewDao cvwDao = new CirViewDao(con);
        CirViewModel cvwModel = new CirViewModel();
        boolean result = false;

        cvwModel.setCifSid(cifSid);
        cvwModel.setCacSid(cacSid);

        if (cvwDao.select(cvwModel).getCvwDsp() != GSConstCircular.DSPKBN_DSP_DEL) {
            result = true;
        }

        return result;
    }

    /**
    *
    * <br>[機  能]指定されたラベルがアクセス可能かどうかを判定する
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param claSid ラベルSID
    * @param cacSid アカウントSID
    * @return ture:アクセス可能
    * @throws SQLException SQL実行例外
    */
    public boolean checkExistLabel(Connection con, int claSid, int cacSid)
            throws SQLException {
        //指定されたラベルがアクセス可能かどうかを判定
        CirLabelDao cirDao = new CirLabelDao(con);
        boolean result = cirDao.selectExistLabel(cacSid, claSid);
        return result;
    }


    /**
    *
    * <br>[機  能]ラベル名を取得
    * <br>[解  説]
    * <br>[備  考]
    * @param claSid ラベルSID
    * @param cacSid アカウントSID
    * @return ラベル名
    * @throws SQLException SQL実行例外
    */
    public String getLabelName(int claSid, int cacSid)
            throws SQLException {
        CirLabelDao labelDao = new CirLabelDao(con__);
        CirLabelModel labelMdl = labelDao.select(claSid, cacSid);
        String labelName = null;
        if (labelMdl != null) {
            labelName = labelMdl.getClaName();
        }
        return labelName;
    }
    /**
    *
    * <br>[機  能]アカウント名を取得
    * <br>[解  説]
    * <br>[備  考]
    * @param cacSid アカウントSID
    * @return アカウント名
    * @throws SQLException SQL実行例外
    */
    public String getAccountName(int cacSid)
            throws SQLException {
        CirAccountDao acDao = new CirAccountDao(con__);
        String accountName =  acDao.getCirAccountName(cacSid);
        if (accountName == null) {
            return "";
        }
        return accountName;
    }


    /**
     * <br>[機  能] 本文の最大文字数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return メール本文の最大文字数
     */
    public static int getBodyLimitLength() {
        int bodyLimit = getConfValue(
                                    GSConstCircular.CIRCONF_MAX_LENGTH,
                                    -1);

        if (bodyLimit < 0) {
            bodyLimit = GSConstCircular.MAX_LENGTH_VALUE;
        }

        return bodyLimit;
    }

    /**
     * <br>[機  能] WEBメール設定ファイルの設定値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param confKey 設定値のキー
     * @param defValue デフォルト値
     * @return 設定値
     */
    public static synchronized int getConfValue(String confKey, int defValue) {
        String confValue = CirConfigBundle.getValue(confKey);


        if (!StringUtil.isNullZeroString(confValue)) {
            return Integer.parseInt(confValue);
        }

        return defValue;
    }

}

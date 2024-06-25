package jp.groupsession.v2.rsv.biz;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.RsvConfigBundle;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvBinDao;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvScdOperationDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.ReserveRegistSmlModel;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvRegSmailModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.rsv090.model.Rsv090DspModel;
import jp.groupsession.v2.rsv.rsv110.Rsv110SisetuModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 施設予約プラグインで使用される共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvCommonBiz {

    /** Logging インスタンス */
    public static Log log__ = LogFactory.getLog(RsvCommonBiz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvCommonBiz() {
    }

    /**
     * <br>[機  能] 施設予約管理者設定のデフォルトを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 施設予約管理者設定Model
     */
    public static RsvAdmConfModel getDefaultAdmConfModel() {
        RsvAdmConfModel confModel = new RsvAdmConfModel();
        confModel.setRacAdlKbn(0);
        confModel.setRacAdlYear(0);
        confModel.setRacAdlMonth(0);
        confModel.setRacHourDiv(GSConstReserve.DF_HOUR_DIVISION);
        confModel.setRacDtmKbn(GSConstReserve.RAC_DTMKBN_USER);
        confModel.setRacDtmFr(GSConstReserve.YRK_DEFAULT_START_HOUR);
        confModel.setRacDtmTo(GSConstReserve.YRK_DEFAULT_END_HOUR);
        confModel.setRacAmTimeFrH(9);
        confModel.setRacAmTimeFrM(0);
        confModel.setRacAmTimeToH(12);
        confModel.setRacAmTimeToM(0);
        confModel.setRacPmTimeFrH(13);
        confModel.setRacPmTimeFrM(0);
        confModel.setRacPmTimeToH(18);
        confModel.setRacPmTimeToM(0);
        confModel.setRacAllDayTimeFrH(9);
        confModel.setRacAllDayTimeFrM(0);
        confModel.setRacAllDayTimeToH(18);
        confModel.setRacAllDayTimeToM(0);
        confModel.setRacIniPeriodKbn(GSConstReserve.RAC_INIPERIODKBN_USER);
        confModel.setRacIniFrH(9);
        confModel.setRacIniFrM(0);
        confModel.setRacIniToH(18);
        confModel.setRacIniToM(0);
        confModel.setRacIniEditKbn(GSConstReserve.RAC_INIEDITKBN_USER);
        confModel.setRacIniEdit(GSConstReserve.EDIT_AUTH_NONE);
        confModel.setRacSmailSendKbn(GSConstReserve.RAC_SMAIL_SEND_KBN_USER);
        confModel.setRacSmailSend(GSConstReserve.RAC_SMAIL_SEND_YES);
        return confModel;
    }

    /**
     * <br>[機  能] 施設予約初期設定 期間を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canPeriodInitConf(Connection con) throws SQLException {
        RsvAdmConfDao admConfDao = new RsvAdmConfDao(con);
        RsvAdmConfModel confModel = admConfDao.select();
        return confModel == null
                || confModel.getRacIniPeriodKbn() == GSConstReserve.RAC_INIPERIODKBN_USER;
    }

    /**
     * <br>[機  能] 施設予約初期設定 編集権限を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditInitConf(Connection con) throws SQLException {
        RsvAdmConfDao admConfDao = new RsvAdmConfDao(con);
        RsvAdmConfModel confModel = admConfDao.select();
        return confModel == null
                || confModel.getRacIniEditKbn() == GSConstReserve.RAC_INIEDITKBN_USER;
    }

    /**
     * <br>[機  能] 施設予約初期設定 公開区分を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canPublicInitConf(Connection con) throws SQLException {
        RsvAdmConfDao admConfDao = new RsvAdmConfDao(con);
        RsvAdmConfModel confModel = admConfDao.select();
        return confModel == null
                || confModel.getRacIniPublicKbn() == GSConstReserve.RAC_INIPUBLICKBN_USER;
    }


    /**
     * <br>[機  能] 施設予約編集権限の初期値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf 施設予約管理者設定
     * @param uconf 施設予約個人設定
     * @return 施設予約編集権限
     */
    public int getInitEditAuth(RsvAdmConfModel admConf, RsvUserModel uconf) {
        int editAuth = GSConstReserve.EDIT_AUTH_NONE;
        if (admConf != null && admConf.getRacIniEditKbn() == GSConstReserve.RAC_INIEDITKBN_ADM) {
            editAuth = admConf.getRacIniEdit();
        } else if (uconf != null) {
            editAuth = uconf.getRsuIniEdit();
        }

        return editAuth;
    }

    /**
     * <br>[機  能] 施設予約公開区分の初期値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf 施設予約管理者設定
     * @param uconf 施設予約個人設定
     * @return 施設予約編集権限
     */
    public int getInitPublicAuth(RsvAdmConfModel admConf, RsvUserModel uconf) {
        int publicAuth = GSConstReserve.PUBLIC_KBN_ALL;
        if (admConf != null
                && admConf.getRacIniPublicKbn() == GSConstReserve.RAC_INIPUBLICKBN_ADM) {
            publicAuth = admConf.getRacIniPublic();
        } else if (uconf != null) {
            publicAuth = uconf.getRsuIniPublic();
        }

        return publicAuth;
    }

    /**
     * <br>[機  能] 施設予約開始時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf 施設予約管理者設定
     * @param uconf 施設予約個人設定
     * @return 開始
     */
    public UDate getFrDateAuth(RsvAdmConfModel admConf, RsvUserModel uconf) {
        UDate frDateAuth = new UDate();
        if (admConf != null
                && admConf.getRacIniPeriodKbn() == GSConstReserve.RAC_INIPERIODKBN_ADM) {
            frDateAuth.setHour(admConf.getRacIniFrH());
            frDateAuth.setMinute(admConf.getRacIniFrM());

        } else if (uconf != null) {
            frDateAuth = uconf.getRsuIniFrDate();
        }
        return frDateAuth;
    }

    /**
     * <br>[機  能] 施設予約終了時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf 施設予約管理者設定
     * @param uconf 施設予約個人設定
     * @return 施設予約編集権限
     */
    public UDate getToDateAuth(RsvAdmConfModel admConf, RsvUserModel uconf) {
        UDate toDateAuth = new UDate();
        if (admConf != null
                && admConf.getRacIniPeriodKbn() == GSConstReserve.RAC_INIPERIODKBN_ADM) {
            toDateAuth.setHour(admConf.getRacIniToH());
            toDateAuth.setMinute(admConf.getRacIniToM());
        } else if (uconf != null) {
            toDateAuth = uconf.getRsuIniToDate();
        }
        return toDateAuth;
    }

    /**
     * <br>[機  能] 個人設定初期値設定情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 個人設定
     */
    public RsvUserModel getUnconfInitModel() {
        RsvUserModel retUconf = new RsvUserModel();
        UDate frDate = new UDate();
        frDate.setZeroHhMmSs();
        frDate.setHour(GSConstReserve.YRK_DEFAULT_START_HOUR);
        frDate.setMinute(GSConstReserve.YRK_DEFAULT_START_MINUTE);
        retUconf.setRsuIniFrDate(frDate);
        UDate toDate = new UDate();
        toDate.setZeroHhMmSs();
        toDate.setHour(GSConstReserve.YRK_DEFAULT_END_HOUR);
        toDate.setMinute(GSConstReserve.YRK_DEFAULT_END_MINUTE);
        retUconf.setRsuIniToDate(toDate);
        return retUconf;
    }




    /**
     * <br>[機  能] 各ユーザが日間表示時間帯を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditShowHoursDays(Connection con) throws SQLException {
        RsvAdmConfDao admConfDao = new RsvAdmConfDao(con);
        RsvAdmConfModel confModel = admConfDao.select();
        return confModel == null
                || confModel.getRacDtmKbn() == GSConstReserve.RAC_DTMKBN_USER;
    }

    /**
     * <br>[機  能] 承認を行う施設グループかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rsgSid 施設グループSID
     * @return true:承認を行う false:承認を行わない
     * @throws SQLException SQL実行時例外
     */
    public boolean isApprSisGroup(Connection con, int rsgSid)
    throws SQLException {
        RsvSisGrpDao sisGrpDao = new RsvSisGrpDao(con);
        return sisGrpDao.getGrpApprKbn(rsgSid) == GSConstReserve.RSG_APPR_KBN_APPR;
    }

    /**
     * <br>[機  能] 承認を行う施設かを判定する
     * <br>[解  説] ユーザが管理者だった場合もfalse
     * <br>[備  考]
     * @param con コネクション
     * @param rsdSid 施設SID
     * @param userSid ユーザSID
     * @return true:承認を行う false:承認を行わない
     * @throws SQLException SQL実行時例外
     */
    public boolean isApprSis(Connection con, int rsdSid, int userSid)
    throws SQLException {
        boolean result = false;
        RsvSisDataModel sisData = new RsvSisDataModel();
        sisData.setRsdSid(rsdSid);
        RsvSisDataDao sisDataDao = new RsvSisDataDao(con);
        sisData = sisDataDao.select(sisData);

        if (sisData != null) {
            //施設グループに管理者が設定されているかを判定
            RsvSisGrpDao sisGrpDao = new RsvSisGrpDao(con);
            ArrayList<String> grpAdmUsers = sisGrpDao.getDefaultAdmUser(sisData.getRsgSid());
            if (grpAdmUsers == null || grpAdmUsers.isEmpty()) {
                return false;
            }

            //選択しているユーザSIDとグループSIDから全てのユーザSIDを取得する
            ArrayList<Integer> admUserList = getAllUserSids(grpAdmUsers, con);
            //ユーザが管理者かを判定する
            for (int sid : admUserList) {
                if (sid == userSid) {
                   return false;
                }
            }

            //所属する施設グループの区分を判定する
            result = isApprSisGroup(con, sisData.getRsgSid());
            if (!result) {
                result
                    = sisData.getRsdApprKbn() == GSConstReserve.RSD_APPR_KBN_APPR;
            }
        }

        return result;
    }

    /**
     * <br>[機  能] ユーザが指定した施設予約の施設の管理者か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rsySid 施設予約SID
     * @param userSid ユーザSID
     * @return true:施設グループ管理者
     * @throws SQLException SQL実行時例外
     */
    public boolean isSisGrpAdmin(Connection con, int rsySid, int userSid)
            throws SQLException {

        RsvSisYrkDao sisYrkDao = new RsvSisYrkDao(con);
        int rsdSid = sisYrkDao.getSisDataSid(rsySid);

        return isGroupAdmin(con, rsdSid, userSid);
    }

    /**
     * <br>[機  能] ユーザが指定した施設のグループ管理者か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rsdSid 施設SID
     * @param userSid ユーザSID
     * @return true:施設グループ管理者
     * @throws SQLException SQL実行時例外
     */
    public boolean isGroupAdmin(Connection con, int rsdSid, int userSid) throws SQLException {
        RsvSisGrpDao sisGrpDao = new RsvSisGrpDao(con);
        return sisGrpDao.isGroupAdmin(rsdSid, userSid);
    }

    /**
     * <br>[機  能] 施設予約情報登録時の承認状況、承認区分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rsdSid 施設SID
     * @param yrkData 施設予約情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setSisYrkApprData(Connection con, int rsdSid, RsvSisYrkModel yrkData, int userSid)
    throws SQLException {
        if (isApprSis(con, rsdSid, userSid)) {
            yrkData.setRsyApprStatus(GSConstReserve.RSY_APPR_STATUS_NOAPPR);
        } else {
            yrkData.setRsyApprStatus(GSConstReserve.RSY_APPR_STATUS_NORMAL);
        }
        yrkData.setRsyApprKbn(GSConstReserve.RSY_APPR_KBN_NOSET);
    }

    /**
     * <br>[機  能] 画面に表示する施設予約の承認状況に関する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GsMessage
     * @param apprStatus 承認状況
     * @param apprKbn 承認区分
     * @return 利用目的に関する情報 (index0=日付のstyle(css), index1=目的のstyle(css),
     * <br>index2=承認状態( 無し or "(承認待ち)" or "(却下)", index2=目的のstyle(css 一覧用)
     */
    public String[] getMokApprStatus(GsMessage gsMsg, int apprStatus, int apprKbn) {
        String[] result = {"cl_linkDef fs_13", "cl_fontSchTitleBlue ", "", "cl_linkDef"};

        if (apprStatus == GSConstReserve.RSY_APPR_STATUS_NOAPPR) {
            result[0] = "cl_fontSchTitleYellow fs_13";
            result[1] = "cl_fontSchTitleYellow";
            result[2] = "(" + gsMsg.getMessage("reserve.appr.st1") + ")";
            result[3] = "cl_fontSchTitleYellow";
        } else if (apprKbn == GSConstReserve.RSY_APPR_KBN_REJECTION) {
            result[0] = "cl_fontSchTitleBlack fs_13";
            result[1] = "cl_fontSchTitleBlack";
            result[2] = "(" + gsMsg.getMessage("reserve.appr.st2") + ")";
            result[3] = "cl_fontSchTitleBlack";
        }

        return result;
    }

    /**
     * <br>[機  能] 画面に表示する施設予約の承認状況に関する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param locale Locale
     * @param apprStatus 承認状況
     * @param apprKbn 承認区分
     * @return 利用目的に関する情報 (index0=日付のstyle(css), index1=目的のstyle(css),
     * <br>index2=承認状態( 無し or "(承認待ち)" or "(却下)", index2=目的のstyle(css 一覧用)
     */
    public String[] getMokApprStatus(Locale locale, int apprStatus, int apprKbn) {
        GsMessage gsMsg = new GsMessage(locale);
        String[] result = {"", "cl_fontSchTitleBlue", "", "cl_fontSchTitleBlue"};

        if (apprStatus == GSConstReserve.RSY_APPR_STATUS_NOAPPR) {
            result[0] = "";
            result[1] = "cl_fontSchTitleYellow";
            result[2] = "(" + gsMsg.getMessage("reserve.appr.st1") + ")";
            result[3] = "cl_fontSchTitleYellow ";
        } else if (apprKbn == GSConstReserve.RSY_APPR_KBN_REJECTION) {
            result[0] = "";
            result[1] = "cl_fontBody";
            result[2] = "(" + gsMsg.getMessage("reserve.appr.st2") + ")";
            result[3] = "";
        }

        return result;
    }


    /**
     * <br>[機  能] 施設予約情報URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param rsvSid 施設予約情報SID
     * @param procMode 処理モードSID
     * @param date 日付
     * @return スレッドURL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createReserveUrl(RequestModel reqMdl, int rsvSid, int procMode, String date)
    throws UnsupportedEncodingException {
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl);
            paramUrl += "/" + GSConst.PLUGIN_ID_RESERVE;
            paramUrl += "/rsv110.do";

            paramUrl += "?rsv110ProcMode=" + procMode;
            paramUrl += "&rsv110RsySid=" + rsvSid;
            paramUrl += "&rsvDspFrom=" + date;

            return urlBiz.getAccessUrl(reqMdl, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }

    /**
     * <br>[機  能] ショートメールで施設予約 申請通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param regMdl 申請通知情報
     * @throws Exception 実行例外
     */
    public void sendRegSmail(RsvRegSmailModel regMdl) throws Exception {

        //施設予約表示モデル(ショートメール送信用)
        ReserveRegistSmlModel rsvModel = new ReserveRegistSmlModel();
        RsvSisDataDao dao = new RsvSisDataDao(regMdl.getCon());
        Rsv090DspModel mdl = dao.getSisetuData(regMdl.getRsdSid());
        //施設グループデータ取得
        RsvSisGrpDao grpDao = new RsvSisGrpDao(regMdl.getCon());
        //管理者ユーザ一覧取得(ログインユーザ以外)
        ArrayList<Integer> selectUser = new ArrayList<Integer>();

        ArrayList<String> defaultAdmUserGrp = grpDao.getDefaultAdmUser(mdl.getRsgSid());

        ArrayList<Integer> defaultAdmUsers = getAllUserSids(defaultAdmUserGrp, regMdl.getCon());
        for (int adminUsr : defaultAdmUsers) {
            if (adminUsr != regMdl.getUserSid()) {
                selectUser.add(adminUsr);
            }
        }

        UDate now = new UDate();
        String strNow = now.getDateString();
        Rsv110SisetuModel model = __getYoyakuData(regMdl.getCon(), regMdl.getRsySid());
        rsvModel.setRsvSid(model.getRsdSid());
        rsvModel.setRsvMokuteki(model.getRsyMok());
        rsvModel.setRsvNaiyou(model.getRsyBiko());
        rsvModel.setUserSid(model.getRsyAuid());
        rsvModel.setRsvFrDate(model.getRsyFrDate());
        rsvModel.setRsvToDate(model.getRsyToDate());
        rsvModel.setRsvAdate(model.getRsyEdate());
        rsvModel.setRsvUrl(createReserveUrl(regMdl.getReqMdl(),
                                            regMdl.getRsySid(),
                                            Integer.parseInt(GSConstReserve.PROC_MODE_EDIT),
                                            strNow));
        rsvModel.setSidList(selectUser);
        //送信
        sendRegSmail(regMdl.getCon(), regMdl.getCntCon(), rsvModel,
                    regMdl.getAppRootPath(), regMdl.getPluginConfig(),
                    regMdl.getReqMdl());
    }

    /**
     * <br>[機  能] ショートメールで施設予約 申請通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param regMdl 申請通知情報
     * @param rsvUrl 施設予約登録確認URL
     * @throws Exception 実行例外
     */
    public void sendRegSmail(RsvRegSmailModel regMdl, String rsvUrl) throws Exception {

        //施設予約表示モデル(ショートメール送信用)
        ReserveRegistSmlModel rsvModel = new ReserveRegistSmlModel();
        RsvSisDataDao dao = new RsvSisDataDao(regMdl.getCon());
        Rsv090DspModel mdl = dao.getSisetuData(regMdl.getRsdSid());
        //施設グループデータ取得
        RsvSisGrpDao grpDao = new RsvSisGrpDao(regMdl.getCon());
        //管理者ユーザ一覧取得(ログインユーザ以外)
        ArrayList<Integer> selectUser = new ArrayList<Integer>();

        ArrayList<String> defaultAdmUserGrp = grpDao.getDefaultAdmUser(mdl.getRsgSid());

        ArrayList<Integer> defaultAdmUsers = getAllUserSids(defaultAdmUserGrp, regMdl.getCon());

        for (int adminUsr : defaultAdmUsers) {
            if (adminUsr != regMdl.getUserSid()) {
                selectUser.add(adminUsr);
            }
        }


        Rsv110SisetuModel model = __getYoyakuData(regMdl.getCon(), regMdl.getRsySid());
        rsvModel.setRsvSid(model.getRsdSid());
        rsvModel.setRsvMokuteki(model.getRsyMok());
        rsvModel.setRsvNaiyou(model.getRsyBiko());
        rsvModel.setUserSid(model.getRsyAuid());
        rsvModel.setRsvFrDate(model.getRsyFrDate());
        rsvModel.setRsvToDate(model.getRsyToDate());
        rsvModel.setRsvAdate(model.getRsyEdate());
        rsvModel.setRsvUrl(rsvUrl);
        rsvModel.setSidList(selectUser);
        //送信
        sendRegSmail(regMdl.getCon(), regMdl.getCntCon(), rsvModel,
                    regMdl.getAppRootPath(), regMdl.getPluginConfig(),
                    regMdl.getReqMdl());
    }

    /**
     * <br>[機  能] ショートメールで更新通知を行う。
     * <br>[解  説] 承認設定がされている施設予約が登録した際に行う。
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param rsvModel 投稿内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void sendRegSmail(
        Connection con,
        MlCountMtController cntCon,
        ReserveRegistSmlModel rsvModel,
        String appRootPath,
        PluginConfig pluginConfig,
        RequestModel reqMdl) throws Exception {

        //投稿者名
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel umodel = udao.select(rsvModel.getUserSid());
        String tname = umodel.getUsiSei() + " " + umodel.getUsiMei();

        GsMessage gsMsg = new GsMessage(reqMdl);


        String aDate = "";
        String frDate = "";
        String toDate = "";

        String bodyml = "";

            aDate = UDateUtil.getSlashYYMD(rsvModel.getRsvAdate());
            frDate = UDateUtil.getSlashYYMD(rsvModel.getRsvFrDate())
                   + " "
                   + UDateUtil.getSeparateHMS(rsvModel.getRsvFrDate());
            toDate = UDateUtil.getSlashYYMD(rsvModel.getRsvToDate())
                   + " "
                   + UDateUtil.getSeparateHMS(rsvModel.getRsvToDate());

            //本文
            String tmpPath = __getSmlTemplateFilePathRegist(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("RESERVE", rsvModel.getRsvMokuteki());
            map.put("NAIYOU", rsvModel.getRsvNaiyou());
            map.put("ANAME", tname);
            map.put("ADATE", aDate);
            map.put("FRDATE", frDate);
            map.put("TODATE", toDate);
            map.put("URL", rsvModel.getRsvUrl());
            bodyml = StringUtil.merge(tmpBody, map);

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {

                String textMessage = gsMsg.getMessage("cmn.mail.omit");

                bodyml = textMessage + "\r\n\r\n" + bodyml;

                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);

            }

            List<Integer> sidList = rsvModel.getSidList();

            //ショートメール送信用モデルを作成する。
            //
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);

            //タイトル
            String title = gsMsg.getMessage("reserve.171");

            title = StringUtil.trimRengeString(title,
                    GSConstCommon.MAX_LENGTH_SMLTITLE);
            smlModel.setSendTitle(title);

            //本文
            smlModel.setSendBody(bodyml);
            //メール形式
            smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

            //メール送信処理開始
            SmlSender sender = new SmlSender(con, cntCon, smlModel,
                                            pluginConfig, appRootPath, reqMdl);
            sender.execute();
    }

    /**
     * <br>[機  能] 予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param yoyakuSid 施設予約SID
     * @return ret 予約情報
     * @throws SQLException SQL実行時例外
     */
    private Rsv110SisetuModel __getYoyakuData(Connection con, int yoyakuSid)
    throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
        Rsv110SisetuModel ret = yrkDao.selectYoyakuEditData(yoyakuSid);

        return ret;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから登録通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    private String __getSmlTemplateFilePathRegist(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/reserve/smail/touroku_tsuuchi.txt");
        return ret;
    }
    /**
     *
     * <br>[機  能] 管理者権限など強権限が無い場合の施設予約の編集権限取得
     * <br>[解  説]
     * <br>[備  考] 管理者権限の確認や施設グループアクセス権の確認は別途おこなうこと
     * @param con コネクション
     * @param rsySid 施設予約SID
     * @param rsyAuid 施設予約登録者SID
     * @param rsyEuid 施設予約更新者SID
     * @param rsyEdit 施設予約編集区分
     * @param scdRsSid 施設予約スケジュール関連SID
     * @param sessionUsrSid セッションユーザSID
     * @param rsyPublic 施設予約公開区分
     * @throws SQLException SQL実行時例外
     * @return 編集権限の有無
     */
    public boolean isAbleEditLowAuth(Connection con,
            int rsySid,
            int rsyAuid, int rsyEuid, int rsyEdit,
            int scdRsSid, int sessionUsrSid,
            int rsyPublic) throws SQLException {
        boolean auth = false;
        //制限無し
        if (rsyEdit == GSConstReserve.EDIT_AUTH_NONE) {
            auth = true;
            //本人・登録者のみ
        } else if (rsyEdit == GSConstReserve.EDIT_AUTH_PER_AND_ADU) {
            if (sessionUsrSid == rsyAuid) {
                auth = true;
            } else {
                //スケジュールと結びついている場合、使用者はOKとする
                if (scdRsSid > 0) {
                    RsvScdOperationDao scdDao = new RsvScdOperationDao(con);
                    //スケジュール拡張SID取得
                    int sceSid =
                            scdDao.selectSceSid(scdRsSid);

                    //拡張登録時
                    if (sceSid > 0) {
                        if (scdDao.isUsingUserFromSceSid(
                                sceSid, sessionUsrSid)) {
                            auth = true;
                        }
                    } else {
                        if (scdDao.isUsingUserFromRsSid(
                                scdRsSid, sessionUsrSid)) {
                            auth = true;
                        }
                    }
                }
            }
            //所属グループ・登録者のみ
        } else if (rsyEdit == GSConstReserve.EDIT_AUTH_GRP_AND_ADU) {
            if (sessionUsrSid == rsyAuid) {
                auth = true;
            } else {
                //登録者本人ではない場合、同グループのユーザか判定
                GroupDao grpDao = new GroupDao(con);
                if (grpDao.isSameGroupUser(rsyAuid, sessionUsrSid)) {
                    auth = true;
                }
            }
        }

        //公開区分範囲外の場合は編集不可
        if (!(isAbleViewLowAuth(
                con, rsySid, rsyAuid, rsyEuid, rsyPublic, scdRsSid, sessionUsrSid))) {
            auth = false;
        }

        return auth;
    }

    /**
    *
    * <br>[機  能] 管理者権限など強権限が無い場合の施設予約の閲覧権限取得
    * <br>[解  説]
    * <br>[備  考] 管理者権限の確認や施設グループアクセス権の確認は別途おこなうこと
    * @param con コネクション
    * @param rsySid 施設予約SID
    * @param rsyAuid 施設予約登録者SID
    * @param rsyEuid 施設予約更新者SID
    * @param rsyPublic 施設予約編集区分
    * @param scdRsSid 施設予約スケジュール関連SID
    * @param sessionUsrSid セッションユーザSID
    * @throws SQLException SQL実行時例外
    * @return 閲覧権限の有無
    */
    public boolean isAbleViewLowAuth(Connection con,
            int rsySid,
            int rsyAuid, int rsyEuid, int rsyPublic,
            int scdRsSid, int sessionUsrSid) throws SQLException {

        boolean view = false;
        //公開
        if (rsyPublic == GSConstReserve.PUBLIC_KBN_ALL) {
            view = true;
           //予定あり
        } else if (rsyPublic == GSConstReserve.PUBLIC_KBN_PLANS) {
            if (sessionUsrSid == rsyAuid || sessionUsrSid == rsyEuid) {
                view = true;
            } else {
                //スケジュールと結びついている場合、使用者はOKとする
                if (scdRsSid > 0) {
                    RsvScdOperationDao scdDao = new RsvScdOperationDao(con);
                    //スケジュール拡張SID取得
                    int sceSid =
                            scdDao.selectSceSid(scdRsSid);

                    //拡張登録時
                    if (sceSid > 0) {
                        if (scdDao.isUsingUserFromSceSid(
                                sceSid, sessionUsrSid)) {
                            view = true;
                        }
                    } else {
                        if (scdDao.isUsingUserFromRsSid(
                                scdRsSid, sessionUsrSid)) {
                            view = true;
                        }
                    }
                }
            }
            //所属グループ・登録者のみ
        } else if (rsyPublic == GSConstReserve.PUBLIC_KBN_GROUP) {
            if (sessionUsrSid == rsyAuid || sessionUsrSid == rsyEuid) {
                view = true;
            } else {
                //登録者本人ではない場合、同グループのユーザか判定
                GroupDao grpDao = new GroupDao(con);
                if (grpDao.isSameGroupUser(rsyAuid, sessionUsrSid)) {
                    view = true;
                }
            }
            //指定ユーザグループのみ
        } else if (rsyPublic == GSConstReserve.PUBLIC_KBN_USRGRP) {
            if (sessionUsrSid == rsyAuid || sessionUsrSid == rsyEuid) {
                view = true;
            } else {
                RsvDataPubDao rsvPubDao = new RsvDataPubDao(con);
                view = rsvPubDao.select(rsySid, sessionUsrSid);
            }
            //タイトルのみ
        } else if (rsyPublic == GSConstReserve.PUBLIC_KBN_TITLE) {
            if (sessionUsrSid == rsyAuid || sessionUsrSid == rsyEuid) {
                view = true;
            }
        }
        return view;
    }

    /**
     * <br>[機  能] 施設予約個人設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return RsvUserModel
     * @throws SQLException SQL実行時例外
     */
    public RsvUserModel getRevUserModel(int userSid, Connection con) throws SQLException {
        RsvUserDao dao = new RsvUserDao(con);
        RsvUserModel ret = dao.select(userSid);
        return ret;
    }

    /**
     * <br>[機  能] ユーザSID、グループSID(接頭にG文字つき)が混在している配列から
     *                     グループに所属しているユーザを含めた全てのユーザSIDを取得する
     * <br>[解  説] 重複SIDは除去
     * <br>[備  考]
     * @param sids ユーザSID、グループSID配列
     * @param con コネクション
     * @throws SQLException SQL実行時エラー
     * @return ユーザーSIDリスト
     */
    public ArrayList<Integer> getAllUserSids(ArrayList<String> sids, Connection con)
            throws SQLException {
        ArrayList<String> grpSidList = new ArrayList<String>();
        ArrayList<String> usrSidList = new ArrayList<String>();
        for (String id : sids) {
            String str = NullDefault.getString(id, "-1");
            if (str.contains(new String("G").subSequence(0, 1))) {
                //グループ
                grpSidList.add(str.substring(1, str.length()));
            } else {
                //ユーザ
                usrSidList.add(str);
            }
        }

        if (grpSidList != null && !grpSidList.isEmpty()) {
            CmnBelongmDao bdao = new CmnBelongmDao(con);
            usrSidList.addAll(bdao.select(
                    (String[]) grpSidList.toArray(new String[grpSidList.size()])));
        }

        Map<String, String> sidMap = new HashMap<String, String>();
        for (String sid : usrSidList) {
            sidMap.put(sid, sid);
        }

        List<Integer> usrSidInt = new ArrayList<Integer>();

        Set<String> keySet = sidMap.keySet();
        Iterator<String> keyIte = keySet.iterator();
        while (keyIte.hasNext()) {
            String usidkey = (String) keyIte.next();
            usrSidInt.add(Integer.valueOf(sidMap.get(usidkey)));
        }

        return (ArrayList<Integer>) usrSidInt;
    }

    /**
     * <br>[機  能] 施設区分別情報登録対象かチェックする
     * <br>[解  説] 1:部屋 or 3:車 OK
     * <br>[備  考]
     *
     * @param rskSid 施設区分
     * @return true:対象 false:対象外
     */
    public static boolean isRskKbnRegCheck(int rskSid) {
        if (rskSid == GSConstReserve.RSK_KBN_HEYA
                || rskSid == GSConstReserve.RSK_KBN_CAR) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 施設区分別情報の初期値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param rskSid 施設区分
     * @param appRootPath アプリケーションルートパス
     * @return 施設区分別情報初期値モデル
     * @throws SQLException SQL実行時例外
     */
    public RsvSisKyrkModel getSisKbnInitData(
            Connection con, RequestModel reqMdl, int rskSid, String appRootPath)
            throws SQLException {

        RsvSisKyrkModel ret = new RsvSisKyrkModel();
        BaseUserModel usModel = reqMdl.getSmodel();

        //デフォルトグループの情報を取得
        GroupDao dao = new GroupDao(con);
        CmnGroupmModel grpMdl = dao.getDefaultGroup(usModel.getUsrsid());

        //共通項目 担当部署
        ret.setRkyBusyo(grpMdl.getGrpName());
        //共通項目 担当・使用者名
        ret.setRkyName(usModel.getUsisei() + "  " + usModel.getUsimei());
        //連絡先
        CmnUsrmInfDao uInfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel uInfMdl = uInfDao.select(usModel.getUsrsid());
        ret.setRkyContact(NullDefault.getString(uInfMdl.getUsiTelNai1(), ""));

        //施設区分 部屋
        if (rskSid == GSConstReserve.RSK_KBN_HEYA) {
            //利用区分
            ret.setRkyUseKbn(GSConstReserve.RSY_USE_KBN_NOSET);
        } else if (rskSid == GSConstReserve.RSK_KBN_CAR) {
            //施設区分 車
            //印刷区分
            if (RsvCommonBiz.isUsePrintKbn(appRootPath)) {
                ret.setRkyPrintKbn(GSConstReserve.RSY_PRINT_KBN_YES);
            } else {
                ret.setRkyPrintKbn(GSConstReserve.RSY_PRINT_KBN_NO);
            }

        }

        return ret;
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
        RsvAdmConfModel admData = getRsvAdminData(con, userSid);
        return admData.getRacSmailSendKbn() != GSConstReserve.RAC_SMAIL_SEND_KBN_ADMIN;
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
        RsvAdmConfModel admData = getRsvAdminData(con, userSid);
        return admData.getRacSmailSendKbn() != GSConstReserve.RAC_SMAIL_SEND_KBN_ADMIN
                || admData.getRacSmailSend() == GSConstReserve.RAC_SMAIL_SEND_YES;
    }

    /**
     * <br>[機  能] 施設予約管理者設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUserId セッションユーザSID
     * @return 施設予約管理者設定情報
     * @throws SQLException SQL実行例外
     */
    public RsvAdmConfModel getRsvAdminData(Connection con, int sessionUserId) throws SQLException {
        RsvAdmConfDao admDao = new RsvAdmConfDao(con);
        RsvAdmConfModel admData = admDao.select();

        if (admData == null) {
            //取得できなかった場合は初期値を設定する
            admData = getDefaultAdmConfModel();
            UDate now = new UDate();
            admData.setRacAuid(sessionUserId);
            admData.setRacAdate(now);
            admData.setRacEuid(sessionUserId);
            admData.setRacEdate(now);

            boolean commit = false;
            try {
                admDao.insert(admData);
                commit = true;
            } catch (SQLException e) {
                log__.error("施設予約-管理者設定の登録に失敗しました");
                throw e;
            } finally {
                if (commit) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }

        return admData;
    }

    /**
     * <br>[機  能] ショートメール通知の判定を行う。
     * <br>[解  説] 他のユーザに施設予約を変更された場合、登録者にショートメールで通知を行うか。
     *                    true:通知する false:通知しない
     * <br>[備  考] 管理者設定、個人設定より判定
     *
     * @param userSid セッションユーザＳＩＤ
     * @param entryUserSid 登録者ユーザＳＩＤ
     * @param con コネクション
     * @throws Exception 実行例外
     * @return true:通知する false:通知しない
     *
     */
    public boolean checkSendSmail(int userSid, int entryUserSid, Connection con) throws Exception {
        boolean ret = false;
        con.setAutoCommit(true);

        RsvAdmConfModel admMdl = getRsvAdminData(con, userSid);

        //管理者設定 ショートメール通知設定区分「管理者が設定する」の場合
        if (admMdl.getRacSmailSendKbn() == GSConstReserve.RAC_SMAIL_SEND_KBN_ADMIN) {
            if (admMdl.getRacSmailSend() == GSConstReserve.RAC_SMAIL_SEND_YES) {
                //登録者と更新者が違う場合、ショートメール通知
                if (entryUserSid != userSid) {
                    ret = true;
                }
            }

         //管理者設定 ショートメール通知設定区分「各ユーザが設定する」の場合
        } else {
            ret = checkUconfSendSmail(userSid, entryUserSid, con);
        }

        con.setAutoCommit(false);
        return ret;
    }

    /**
     * <br>[機  能] 個人設定よりショートメール通知の判定を行う。
     * <br>[解  説] 他のユーザに施設予約を変更された場合、登録者にショートメールで通知を行うか。
     * <br>[備  考]
     * @param userSid セッションユーザSID
     * @param entryUserSid 予約情報登録ユーザSID
     * @param con コネクション
     * @return boolean true=ショートメール通知有り false=ショートメール通知なし
     * @throws Exception 実行例外
     */
    public boolean checkUconfSendSmail(int userSid, int entryUserSid, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        boolean flg = false;

        //登録者個人設定を取得
        RsvUserDao dao = new RsvUserDao(con);
        RsvUserModel model = dao.select(entryUserSid);
        if (model == null) {
            model = new RsvUserModel();
        }

        //登録者の個人設定にてショートメール通知の設定がされている場合
        if (model.getRsuSmailKbn() == GSConstReserve.RSU_SMAIL_SEND_OK) {

            //登録者と更新者が違う場合、ショートメール通知
            if (entryUserSid != userSid) {
                flg = true;
            }
        }
        con.setAutoCommit(false);
        return flg;
    }

    /**
     * <br>[機  能] 施設予約 印刷区分を使用するか
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return true:使用する  false:使用しない
     */
    public static synchronized boolean isUsePrintKbn(String appRootPath) {

        return getPrintUseKbn(appRootPath) == GSConstReserve.RSV_PRINT_USE_YES;
    }

    /**
     * <br>[機  能] 施設予約 印刷区分の使用区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return 0:使用しない  1:使用する
     */
    public static synchronized int getPrintUseKbn(String appRootPath) {

        return getConfValue(appRootPath,
                GSConstReserve.RSV_PRINT_USE,
                GSConstReserve.RSV_PRINT_USE_NO);
    }

    /**
     * <br>[機  能] 施設予約の設定ファイルの設定値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @param confKey 設定値のキー
     * @param defValue デフォルト値
     * @return 設定値
     */
    public static synchronized int getConfValue(String appRootPath, String confKey, int defValue) {
        String confValue = RsvConfigBundle.getValue(confKey);

        if (StringUtil.isNullZeroString(confValue)) {
            try {
                RsvConfigBundle.readConfig(appRootPath);
                confValue = RsvConfigBundle.getValue(confKey);
            } catch (IOException e) {
                log__.error("施設予約設定ファイルの読み込みに失敗", e);
            }
        }

        if (!StringUtil.isNullZeroString(confValue)) {
            return Integer.parseInt(confValue);
        }

        return defValue;
    }

    /**
     * <br>[機  能] バイナリーSIDが表示・ダウンロード可能なファイルかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSid 施設SID
     * @param binSid バイナリSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return true:可能  false:不可能
     */
    public boolean isCheckRsvImage(int rsdSid, Long binSid, Connection con)
            throws SQLException {
        RsvBinDao dao = new RsvBinDao(con);
        return dao.isCheckRsvImage(rsdSid, binSid);
    }

    /**
     * <br>[機  能] ユーザ一覧からスケジュール閲覧不可のユーザを除外する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userList ユーザ一覧
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void removeNotAccessUser(Connection con, List<CmnUsrmInfModel> userList,
                                                    int sessionUserSid)
    throws SQLException {

        //グループ所属ユーザラベルから閲覧不可ユーザを除外する
        SchDao schDao = new SchDao(con);
        List<Integer> notAccessUsrList = schDao.getNotAccessUserList(sessionUserSid);
        ArrayList<CmnUsrmInfModel> accessUserList = new ArrayList<CmnUsrmInfModel>();
        for (CmnUsrmInfModel userLabel : userList) {
            if (notAccessUsrList.indexOf(userLabel.getUsrSid()) < 0) {
                accessUserList.add(userLabel);
            }
        }
        userList.clear();
        userList.addAll(accessUserList);
    }
    /**
     * <br>[機  能] ユーザ一覧からスケジュール編集不可のユーザを除外する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userList ユーザ一覧
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void removeNotRegistUser(Connection con, List<CmnUsrmInfModel> userList,
                                                    int sessionUserSid)
    throws SQLException {

        //グループ所属ユーザラベルから登録不可ユーザを除外する
        SchDao schDao = new SchDao(con);
        List<Integer> notAccessUsrList = schDao.getNotRegistUserList(sessionUserSid);
        ArrayList<CmnUsrmInfModel> accessUserList = new ArrayList<CmnUsrmInfModel>();
        for (CmnUsrmInfModel userLabel : userList) {
            if (notAccessUsrList.indexOf(userLabel.getUsrSid()) < 0) {
                accessUserList.add(userLabel);
            }
        }
        userList.clear();
        userList.addAll(accessUserList);
    }

    /**
     * <br>[機  能] 管理者設定初期値を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @return confModel confModel
     */
    public RsvAdmConfModel setInitAdminnConfModel() {
        RsvAdmConfModel confModel;
        confModel = new RsvAdmConfModel();
        //時間単位
        confModel.setRacHourDiv(GSConstReserve.DF_HOUR_DIVISION);
        //午前
        confModel.setRacAmTimeFrH(GSConstReserve.DF_FROM_AM_HOUR);
        confModel.setRacAmTimeFrM(GSConstReserve.DF_FROM_AM_MINUTES);
        confModel.setRacAmTimeToH(GSConstReserve.DF_TO_AM_HOUR);
        confModel.setRacAmTimeToM(GSConstReserve.DF_TO_AM_MINUTES);

        //午後
        confModel.setRacPmTimeFrH(GSConstReserve.DF_FROM_PM_HOUR);
        confModel.setRacPmTimeFrM(GSConstReserve.DF_FROM_PM_MINUTES);
        confModel.setRacPmTimeToH(GSConstReserve.DF_TO_PM_HOUR);
        confModel.setRacPmTimeToM(GSConstReserve.DF_TO_PM_MINUTES);

        //終日
        confModel.setRacAllDayTimeFrH(GSConstReserve.DF_FROM_ALL_DAY_HOUR);
        confModel.setRacAllDayTimeFrM(GSConstReserve.DF_FROM_ALL_DAY_MINUTES);
        confModel.setRacAllDayTimeToH(GSConstReserve.DF_TO_ALL_DAY_HOUR);
        confModel.setRacAllDayTimeToM(GSConstReserve.DF_TO_ALL_DAY_MINUTES);

        return confModel;
    }

    /**
     * <br>[機  能] 時コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return labelList 時コンボリスト
     */
    public ArrayList<LabelValueBean> getHourCombo() {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        //0時～23時まででコンボを作成
        for (int i = 0; i <= 23; i++) {
            labelList.add(
                    new LabelValueBean(
                            String.valueOf(i),
                            String.valueOf(i)));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 分コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return labelList 分コンボリスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getMinuteCombo(Connection con)
            throws SQLException {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        //施設予約管理者設定より時間間隔を取得
        RsvAdmConfDao confDao = new RsvAdmConfDao(con);
        RsvAdmConfModel confModel = confDao.select();
        int confMin = GSConstReserve.DF_HOUR_DIVISION;
        if (confModel != null) {
            if (confModel.getRacHourDiv() > 0) {
                confMin = confModel.getRacHourDiv();
            }
        }

        //コンボ作成
        int min = 0;
        for (int i = 1; min < 60; i++) {
            labelList.add(
                    new LabelValueBean(
                            StringUtil.toDecFormat(min, "00"),
                            String.valueOf(min)));
            min = i * confMin;
        }

        return labelList;
    }

    /**
     * <br>[機  能] 施設予約の対象となる施設のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rsdSidList 施設SID
     * @param reqMdl リクエスト情報
     * @return エラーメッセージ | 指定された施設が正常の場合、nullを返す
     * @throws SQLException SQL実行時例外
     */
    public String checkTargetFactory(Connection con, int[] rsdSidList, RequestModel reqMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);

        //施設SIDが指定されていない場合、未選択とする
        if (rsdSidList == null || rsdSidList.length == 0) {
            return gsMsg.getMessage("reserve.173");
        }

        RsvSisDataDao rsvDataDao = new RsvSisDataDao(con);
        for (int rsdSid : rsdSidList) {
            //未選択チェック
            if (rsdSid <= 0) {
                return gsMsg.getMessage("reserve.173");
            } else {
                //施設存在チェック
                if (rsvDataDao.getSisetuData(rsdSid) == null) {
                    return gsMsg.getMessage("reserve.174");
                }
            }

        }

        return null;
    }

    /**
     *
     * <br>[機  能] セッションユーザが編集を許可されているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param rsySid 施設予約SID
     * @param sessionUsrSid セッションユーザSID
     * @return publicFlg GSConstReserve.POWRANGE_IN:権限範囲内 GSConstReserve.POWRANGE_OUT:権限範囲外
     *                      GSConstReserve.POWRANGE_DEL:削除
     * @throws SQLException SQL実行例外
     */
    public int isCanEdit(RequestModel reqMdl,
                                        Connection con,
                                        int rsySid,
                                        int sessionUsrSid) throws SQLException {
        int edit = GSConstReserve.POWRANGE_OUT;
        //施設予約情報を取得
        RsvSisYrkDao rsyDao = new RsvSisYrkDao(con);
        RsvSisYrkModel rsyModel = new RsvSisYrkModel();
        Rsv110SisetuModel sisetuMdl = rsyDao.selectYoyakuEditData(rsySid);
        if (sisetuMdl == null) {
            return GSConstReserve.POWRANGE_DEL;
        }


        int auid = sisetuMdl.getRsyAuid();
        int euid = sisetuMdl.getRsyEuid();
        int rsyEdit = sisetuMdl.getRsyEdit();

        //同時登録スケジュールを取得
        int scdRsSid = sisetuMdl.getScdRsSid();
        //公開区分の取得
        int rsyPublic = rsyModel.getRsyPublic();

        //セッションユーザが更新者あるいは管理者ユーザか判定
        if (isSisGrpAdmin(con, rsySid, sessionUsrSid)) {
            edit = GSConstReserve.POWRANGE_IN;
        //その他編集権限の判定
        } else if (isAbleEditLowAuth(con, rsySid, auid, euid, rsyEdit,
                scdRsSid, sessionUsrSid, rsyPublic)) {
            edit = GSConstReserve.POWRANGE_IN;
        }

        //施設予約の公開区分範囲外の場合は編集権限無し
        if (isWithinPubilicRange(con, rsySid, sessionUsrSid)
                == GSConstReserve.POWRANGE_IN) {
            edit = GSConstReserve.POWRANGE_IN;
        }

        return edit;
    }

    /**
     *
     * <br>[機  能] セッションユーザが施設予約の公開区分範囲内か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rsySid 施設予約SID
     * @param sessionUsrSid セッションユーザSID
     * @return publicFlg GSConstReserve.POWRANGE_IN:公開範囲内 GSConstReserve.POWRANGE_OUT:公開範囲外
     *                      GSConstReserve.POWRANGE_DEL:削除
     * @throws SQLException SQL実行例外
     */
    public int isWithinPubilicRange(Connection con,
            int rsySid,
            int sessionUsrSid) throws SQLException {
        int view = GSConstReserve.POWRANGE_OUT;
        //施設予約情報を取得
        RsvSisYrkDao rsyDao = new RsvSisYrkDao(con);
        Rsv110SisetuModel sisetuMdl = rsyDao.selectYoyakuEditData(rsySid);
        if (sisetuMdl == null) {
            return GSConstReserve.POWRANGE_DEL;
        }
        int auid = sisetuMdl.getRsyAuid();
        int euid = sisetuMdl.getRsyEuid();
        int rsyPublic = sisetuMdl.getRsyPublic();

        //同時登録スケジュールを取得
        int scdRsSid = sisetuMdl.getScdRsSid();

        //セッションユーザが登録者あるいは施設管理者か判定
        if (sessionUsrSid == euid || sessionUsrSid == auid
                || isSisGrpAdmin(con, rsySid, sessionUsrSid)) {
            view = GSConstReserve.POWRANGE_IN;
        //その他閲覧権限の判定
        } else if (isAbleViewLowAuth(
                con, rsySid, auid, euid, rsyPublic, scdRsSid, sessionUsrSid)) {
            view = GSConstReserve.POWRANGE_IN;
        //参照可能な施設予約
        } else if (rsyPublic == GSConstReserve.PUBLIC_KBN_USRGRP) {
            RsvDataPubDao rsdDao = new RsvDataPubDao(con);
            if (rsdDao.select(rsySid, sessionUsrSid)) {
                view = GSConstReserve.POWRANGE_IN;
            }
        }
        return view;
    }

    /**
    *
    * <br>[機  能] セッションユーザが施設予約の公開区分範囲内か判定
    * <br>[解  説] 一覧などの画面表示用
    * <br>[備  考]
    * @param con コネクション
    * @param rsySid 施設予約SID
    * @param sessionUsrSid セッションユーザSID
    * @return publicFlg GSConstReserve.POWRANGE_IN:公開範囲内
    *                    GSConstReserve.POWRANGE_OUT:公開範囲外
    *                    GSConstReserve.RANGE_OUT_TITLE:公開範囲外(公開区分:タイトルのみ)
    *                    GSConstReserve.POWRANGE_DEL:削除
    * @throws SQLException SQL実行例外
    */
   public int isCheckPubRange(Connection con, int rsySid,
           int sessionUsrSid) throws SQLException {
 
       int view = GSConstReserve.POWRANGE_OUT;
       //施設予約情報を取得
       RsvSisYrkDao rsyDao = new RsvSisYrkDao(con);
       Rsv110SisetuModel sisetuMdl = rsyDao.selectYoyakuEditData(rsySid);
       if (sisetuMdl == null) {
           return GSConstReserve.POWRANGE_DEL;
       }
       int auid = sisetuMdl.getRsyAuid();
       int euid = sisetuMdl.getRsyEuid();
       int rsyPublic = sisetuMdl.getRsyPublic();

       //同時登録スケジュールを取得
       int scdRsSid = sisetuMdl.getScdRsSid();

       //セッションユーザが登録者あるいは施設管理者か判定
       if (sessionUsrSid == euid || sessionUsrSid == auid
               || isSisGrpAdmin(con, rsySid, sessionUsrSid)) {
           view = GSConstReserve.POWRANGE_IN;
       //その他閲覧権限の判定
       } else if (isAbleViewLowAuth(
               con, rsySid, auid, euid, rsyPublic, scdRsSid, sessionUsrSid)) {
           view = GSConstReserve.POWRANGE_IN;
       //参照可能な施設予約
       } else if (rsyPublic == GSConstReserve.PUBLIC_KBN_USRGRP) {
           RsvDataPubDao rsdDao = new RsvDataPubDao(con);
           if (rsdDao.select(rsySid, sessionUsrSid)) {
               view = GSConstReserve.POWRANGE_IN;
           }
       } else if (rsyPublic == GSConstReserve.PUBLIC_KBN_TITLE) {
           view = GSConstReserve.RANGE_OUT_TITLE;
       }

       return view;
   }

    /**
     *
     * <br>[機  能] セッションユーザが対象のグループに所属しているかを判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param belongSids 対象とするグループのSID
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return belongFlg true:所属している false:所属していない
     * @throws SQLException SQL実行例外
     */
    public boolean getRsvUsrBelongHnt(ArrayList<Integer> belongSids,
            int sessionUsrSid, Connection con) throws SQLException {
        boolean belongFlg = false;
        //セッションユーザの所属グループを取得
        CmnBelongmDao cmnBelongDao = new CmnBelongmDao(con);
        ArrayList<Integer> belongGpSidList = cmnBelongDao.selectUserBelongGroupSid(sessionUsrSid);

        if (belongSids != null && !belongSids.isEmpty()) {
            for (int gpSid : belongSids) {
                if (belongGpSidList != null) {
                    if (belongGpSidList.indexOf(gpSid) > -1) {
                        belongFlg = true;
                    }
                }
            }
        }
        return belongFlg;
    }

    /**
    *
    * <br>[機  能] 個人設定初期値設定画面アクセス可能確認
    * <br>[解  説] 期間、編集権限、公開区分の全てが管理者が設定するならアクセスができない。
    * <br>[備  考]
    * @param adminMdl 施設予約管理者設定情報
    * @return true: アクセス可能 false: 不可能
    */
   public boolean isAccessUnconfInitSetting(RsvAdmConfModel adminMdl) {
       boolean dspPeriodKbn =
                   (adminMdl.getRacIniPeriodKbn() == GSConstReserve.RAC_INIPERIODKBN_USER);
       boolean dspRacIniEditKbn =
                   (adminMdl.getRacIniEditKbn() == GSConstReserve.RAC_INIEDITKBN_USER);
       boolean dspRacIniPublicKbn =
                   (adminMdl.getRacIniPublicKbn() == GSConstReserve.RAC_INIPUBLICKBN_USER);
       if (!dspPeriodKbn && !dspRacIniEditKbn && !dspRacIniPublicKbn) {
           return false;
       }
       return true;

   }

   /**
   *
   * <br>[機  能] 表示日付に日付以外の文字列が入力された際に今日の日付に変更する
   * <br>[解  説] 8文字の数字以外の文字列しか認めない
   * <br>[備  考]
   * @param dspFrom 表示日付
   * @return dspDate 表示日付
   */
   public String getDspFrom(String dspFrom) {
       String dspDate = new UDate().getDateString();
       if (dspFrom != null
               && dspFrom.length() == 8
               && GSValidateUtil.isNumber(dspFrom)) {
           dspDate = dspFrom;
       }

    return dspDate;

   }

   /**
    * <br>[機  能] 表示グループ用のグループリストを取得する(全て)
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエスト情報
    * @return ArrayList
    * @throws SQLException SQL実行時例外
    */
   public ArrayList <LabelValueBean> getGroupLabelList(
           Connection con, RequestModel reqMdl) throws SQLException {

       ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
       GsMessage gsMsg = new GsMessage(reqMdl);
       LabelValueBean labelBean = new LabelValueBean();
       labelBean.setLabel(gsMsg.getMessage("cmn.grouplist"));
       labelBean.setValue(String.valueOf(GSConst.GROUP_COMBO_VALUE));
       labelList.add(labelBean);

       //グループリスト取得
       GroupBiz gBiz = new GroupBiz();
       ArrayList <GroupModel> gpList = gBiz.getGroupCombList(con);

       GroupModel gpMdl = null;
       for (int i = 0; i < gpList.size(); i++) {
           gpMdl = gpList.get(i);
           labelList.add(
                   new LabelValueBean(gpMdl.getGroupName(), String.valueOf(gpMdl.getGroupSid())));
       }
       log__.debug("labelList.size()=>" + labelList.size());
       return labelList;
   }

   /**
    * <br>[機  能] メンバー一覧を取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param left 取得するユーザSID・グループSID
    * @param con コネクション
    * @return メンバー一覧
    * @throws SQLException SQL実行時例外
    */
   public ArrayList<UsrLabelValueBean> getMemberList(
           String[] left, Connection con) throws SQLException {

       ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

       //
       ArrayList<Integer> grpSids = new ArrayList<Integer>();
       ArrayList<String> usrSids = new ArrayList<String>();

       //ユーザSIDとグループSIDを分離
       if (left != null) {
           for (int i = 0; i < left.length; i++) {
               String str = NullDefault.getString(left[i], "-1");
               if (str.contains(new String("G").subSequence(0, 1))) {
                   //グループ
                   grpSids.add(new Integer(str.substring(1, str.length())));
               } else {
                   //ユーザ
                   usrSids.add(str);
               }
           }
       }
       //グループ情報取得
       UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
       ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
       UsrLabelValueBean labelBean = null;
       for (GroupModel gmodel : glist) {
           labelBean = new UsrLabelValueBean();
           labelBean.setLabel(gmodel.getGroupName());
           labelBean.setValue("G" + String.valueOf(gmodel.getGroupSid()));
           ret.add(labelBean);
       }
       //ユーザ情報取得
       UserBiz userBiz = new UserBiz();
       List<CmnUsrmInfModel> ulist
               = userBiz.getUserList(con,
                                       (String[]) usrSids.toArray(new String[usrSids.size()]));
       for (CmnUsrmInfModel umodel : ulist) {
           labelBean = new UsrLabelValueBean(umodel);
           ret.add(labelBean);
       }
       return ret;
   }

   /**
    * <br>[機  能] 管理者設定 時間表示間隔を取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @return 時間表示間隔
    * @throws SQLException SQL実行時例外
    */
   public int getHourDivision(Connection con) throws SQLException {

       //施設予約管理者設定より時間間隔を取得
       RsvAdmConfDao confDao = new RsvAdmConfDao(con);
       RsvAdmConfModel confModel = confDao.select();
       int hourDiv = GSConstReserve.DF_HOUR_DIVISION;

       if (confModel != null) {
           if (confModel.getRacHourDiv() > 0) {
               hourDiv = confModel.getRacHourDiv();
           }
       }
       return hourDiv;
   }
}
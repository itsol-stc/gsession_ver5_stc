package jp.groupsession.v2.sch.biz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.lang.ClassUtil;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.SchAppendSchData;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.RsvScdOperationDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.RsvScdOperationModel;
import jp.groupsession.v2.cmn.model.SchAppendDataModel;
import jp.groupsession.v2.cmn.model.SchAppendDataParam;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchAdmConfDao;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchExdataBinDao;
import jp.groupsession.v2.sch.dao.SchExdataDao;
import jp.groupsession.v2.sch.dao.SchExdataPubDao;
import jp.groupsession.v2.sch.dao.SchMyviewlistDao;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.dao.SchPushListDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.model.SchMyviewlistModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sch.model.SchPushListModel;
import jp.groupsession.v2.sch.model.SchRepeatKbnModel;
import jp.groupsession.v2.sch.model.ScheduleExSearchModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
//import jp.groupsession.v2.sch.sch040.Sch040Dao;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュールプラグインに関する共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchCommonBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchCommonBiz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SchCommonBiz() {
    }
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public SchCommonBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     */
    public SchCommonBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public SchCommonBiz(Connection con) {
        con__ = con;
    }

    /**
     * <p>マイグループ又はグループに所属するユーザ情報一覧を取得する。
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
     * <br>[機  能] スケジュール個人設定SchPriConfModelを取得します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return スケジュール個人設定SchPriConfModel
     * @throws SQLException SQL実行エラー
     */
    public SchPriConfModel getSchPriConfModel(Connection con, int usrSid) throws SQLException {

        SchPriConfDao dao = new SchPriConfDao(con);
        SchPriConfModel pconf = dao.select(usrSid);
        boolean commitFlg = false;
        if (pconf == null) {
            con.setAutoCommit(false);
            pconf = getDefaulPriConfModel(usrSid, con);
            try {
                dao.insert(pconf);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("個人設定の取得に失敗しました。" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
        log__.debug("pconf = " + pconf.toCsvString());
        return pconf;
    }

    /**
     * <br>[機  能] 新規登録者用スケジュール個人設定SchPriConfModelを取得します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return スケジュール個人設定SchPriConfModel
     * @throws SQLException SQL実行エラー
     */
    public SchPriConfModel getNewSchPriConfModel(Connection con, int usrSid) throws SQLException {
        //
        SchPriConfDao dao = new SchPriConfDao(con);
        SchPriConfModel pconf = dao.select(usrSid);
        //ユーザSIDが0のSchPriConfModelを取得
        SchPriConfModel pribaseModel = getSchPriConfBaseModel(con);

        pconf = getDefaulPriConfModel(usrSid, con);
        //デフォルト表示グループを設定
        GroupBiz gpBiz = new GroupBiz();
        int gsid = gpBiz.getDefaultGroupSid(usrSid, con);
        pconf.setSccDspGroup(gsid);
        pconf.setSccSortKey1(pribaseModel.getSccSortKey1());
        pconf.setSccSortKey2(pribaseModel.getSccSortKey2());
        pconf.setSccSortOrder1(pribaseModel.getSccSortOrder1());
        pconf.setSccSortOrder2(pribaseModel.getSccSortOrder2());

        dao.insert(pconf);
        log__.debug("pconf = " + pconf.toCsvString());
        return pconf;
    }

    /**
     * <br>[機  能] SchPriConfテーブルにユーザSIDが０のデータを作成します。
     * <br>[解  説] 新規作成ユーザはこの
     * <br>[備  考]
     * @param con DBコネクション
     * @return スケジュール個人設定SchPriConfModel
     * @throws SQLException SQL実行エラー
     */

    public SchPriConfModel getSchPriConfBaseModel(Connection con) throws SQLException {
        //
        SchPriConfDao dao = new SchPriConfDao(con);
        SchPriConfModel pconf = new SchPriConfModel();
        SchPriConfModel pribaseModel = dao.select(0);

        if (pribaseModel == null) {
            boolean commitFlg = false;
            con.setAutoCommit(false);

            pconf = getDefaulPriConfModel(0, con);
            try {
                dao.insert(pconf);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("個人設定の取得に失敗しました。" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }

            log__.debug("pconf = " + pconf.toCsvString());
        }
        return pconf;
    }

    /**
     * <br>[機  能] スケジュール個人設定のデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param usrSid ユーザSID
     * @param con DBコネクション
     * @return スケジュール個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public SchPriConfModel getDefaulPriConfModel(int usrSid, Connection con) throws SQLException {
        SchPriConfModel confBean = new SchPriConfModel();

        //ユーザSID
        confBean.setUsrSid(usrSid);
        //開始時間 9時で作成
        UDate frDate = new UDate();
        frDate.setHour(GSConstSchedule.DF_FROM_HOUR);
        frDate.setMinute(GSConstSchedule.DF_FROM_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        confBean.setSccFrDate(frDate);
        //終了時間 18時で作成
        UDate toDate = new UDate();
        toDate.setHour(GSConstSchedule.DF_TO_HOUR);
        toDate.setMinute(GSConstSchedule.DF_TO_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        confBean.setSccToDate(toDate);
        //デフォルト表示グループ
        GroupBiz gpBiz = new GroupBiz();
        int gsid = gpBiz.getDefaultGroupSid(usrSid, con);
        confBean.setSccDspGroup(gsid);
        //一覧表示件数
        confBean.setSccDspList(GSConstSchedule.DEFAULT_LIST_CNT);
        //自動リロード
        confBean.setSccReload(GSConstSchedule.AUTO_RELOAD_10MIN);
        //表示開始曜日
        confBean.setSccIniWeek(GSConstSchedule.CHANGE_WEEK_TODAY);

        confBean.setSccAuid(usrSid);
        confBean.setSccAdate(new UDate());
        confBean.setSccEuid(usrSid);
        confBean.setSccEdate(new UDate());

        //初期値 同時修正フラグ
        confBean.setSccIniSame(GSConstSchedule.SAME_EDIT_ON);
        //初期値 公開フラグ
        confBean.setSccIniPublic(GSConstSchedule.DSP_PUBLIC);
        //初期値 編集権限
        confBean.setSccIniEdit(GSConstSchedule.EDIT_CONF_NONE);
        //初期値 タイトルカラー
        confBean.setSccIniFcolor(GSConstSchedule.DF_BG_COLOR);
        //初期値 開始時刻 9時
        confBean.setSccIniFrDate(frDate);
        //初期値 終了時刻 18時
        confBean.setSccIniToDate(toDate);

        //ソート1
        confBean.setSccSortKey1(GSConstSchedule.SORT_KEY_YKSK);
        confBean.setSccSortOrder1(GSConst.ORDER_KEY_ASC);
        //ソート2
        confBean.setSccSortKey2(GSConstSchedule.SORT_KEY_NAME);
        confBean.setSccSortOrder2(GSConst.ORDER_KEY_ASC);
        //一覧表示件数
        confBean.setSccDspList(GSConstSchedule.DEFAULT_LIST_CNT);
        //マイグループ
        confBean.setSccDspMygroup(0);
        //メンバー表示順編集
        confBean.setSccSortEdit(GSConstSchedule.MEM_EDIT_NOT_EXECUTE);
        //初期表示画面
        confBean.setSccDefDsp(GSConstSchedule.DSP_WEEK);
        //グループスケジュール表示設定
        confBean.setSccGrpShowKbn(GSConstSchedule.GROUP_SCH_SHOW);
        //出欠確認時ショートメール通知
        confBean.setSccSmailAttend(GSConstSchedule.SMAIL_NOT_USE);

        //リマインダー通知時間
        confBean.setSccReminder(GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES);

        //スケジュール管理者設定を取得
        SchAdmConfModel admConfModel = getAdmConfModel(con);

        if (admConfModel.getSadRepeatStype() == GSConstSchedule.SAD_REPEAT_STYPE_USER) {
            //管理者設定の「重複登録区分 設定種別」が「ユーザ単位で設定する」である場合

            //重複登録区分 SCC_REPEAT_KBN
            confBean.setSccRepeatKbn(admConfModel.getSadRepeatKbn());

            //自スケジュール重複登録許可区分 SCC_REPEAT_MY_KBN
            confBean.setSccRepeatMyKbn(admConfModel.getSadRepeatMyKbn());
        }

        return confBean;
    }

    /**
     * <br>[機  能] スケジュール管理者設定を取得し、取得できない場合はデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param con DBコネクション
     * @return スケジュール個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public SchAdmConfModel getAdmConfModel(Connection con) throws SQLException {
        //DBより現在の設定を取得する。
        SchAdmConfDao dao = new SchAdmConfDao(con);
        SchAdmConfModel conf = dao.select();
        if (conf == null) {
            //データがない場合
            conf = new SchAdmConfModel();
            //共有範囲
            conf.setSadCrange(GSConstSchedule.CRANGE_SHARE_ALL);
            //自動削除
            conf.setSadAtdelFlg(GSConstSchedule.AUTO_DELETE_OFF);
            conf.setSadAtdelY(-1);
            conf.setSadAtdelM(-1);
            //時間間隔
            conf.setSadHourDiv(GSConstSchedule.DF_HOUR_DIVISION);

            //登録者・更新者
            UDate now = new UDate();
            conf.setSadAuid(0);
            conf.setSadAdate(now);
            conf.setSadEuid(0);
            conf.setSadEdate(now);

            //メンバ表示順
            conf.setSadSortKbn(GSConstSchedule.MEM_DSP_USR);
            conf.setSadSortKey1(GSConstSchedule.SORT_KEY_YKSK);
            conf.setSadSortOrder1(GSConstSchedule.ORDER_KEY_ASC);
            conf.setSadSortKey2(GSConstSchedule.SORT_KEY_NAME);
            conf.setSadSortOrder2(GSConstSchedule.ORDER_KEY_ASC);

            //初期値 時間設定
            conf.setSadIniTimeStype(GSConstSchedule.SAD_INITIME_STYPE_USER);
            conf.setSadIniFrH(9);
            conf.setSadIniFrM(0);
            conf.setSadIniToH(18);
            conf.setSadIniToM(0);

            //初期値 編集権限設定
            conf.setSadIniEditStype(GSConstSchedule.SAD_INIEDIT_STYPE_USER);
            conf.setSadIniEdit(GSConstSchedule.EDIT_CONF_NONE);

            //初期値 公開区分
            conf.setSadInitPublicStype(GSConstSchedule.SAD_INIPUBLIC_STYPE_USER);
            conf.setSadIniPublic(GSConstSchedule.DSP_PUBLIC);

            //初期値 同時修正設定
            conf.setSadIniSameStype(GSConstSchedule.SAD_INISAME_STYPE_USER);
            conf.setSadIniSame(GSConstSchedule.SAME_EDIT_ON);

            //重複登録区分
            conf.setSadRepeatStype(GSConstSchedule.SAD_REPEAT_STYPE_USER);
            conf.setSadRepeatKbn(GSConstSchedule.SCH_REPEAT_KBN_OK);
            conf.setSadRepeatMyKbn(GSConstSchedule.SCH_REPEAT_MY_KBN_NG);

            //デフォルト表示グループ
            conf.setSadDspGroup(GSConstSchedule.SAD_DSP_GROUP_USER);

            //タイトル色区分
            conf.setSadMsgColorKbn(GSConstSchedule.SAD_MSG_NO_ADD);

            //ショートメール通知設定
            conf.setSadSmailSendKbn(GSConstSchedule.SMAIL_SEND_KBN_USER);
            conf.setSadSmail(GSConstSchedule.SMAIL_NOT_USE);
            conf.setSadSmailGroup(GSConstSchedule.SMAIL_NOT_USE);
            conf.setSadSmailAttend(GSConstSchedule.SMAIL_NOT_USE);

            //時間マスタ
            //午前
            conf.setSadAmFrH(GSConstSchedule.DF_FROM_AM_HOUR);
            conf.setSadAmFrM(GSConstSchedule.DF_FROM_AM_MINUTES);
            conf.setSadAmToH(GSConstSchedule.DF_TO_AM_HOUR);
            conf.setSadAmToM(GSConstSchedule.DF_TO_AM_MINUTES);
            //午後
            conf.setSadPmFrH(GSConstSchedule.DF_FROM_PM_HOUR);
            conf.setSadPmFrM(GSConstSchedule.DF_FROM_PM_MINUTES);
            conf.setSadPmToH(GSConstSchedule.DF_TO_PM_HOUR);
            conf.setSadPmToM(GSConstSchedule.DF_TO_PM_MINUTES);
            //終日
            conf.setSadAllFrH(GSConstSchedule.DF_FROM_ALL_DAY_HOUR);
            conf.setSadAllFrM(GSConstSchedule.DF_FROM_ALL_DAY_MINUTES);
            conf.setSadAllToH(GSConstSchedule.DF_TO_ALL_DAY_HOUR);
            conf.setSadAllToM(GSConstSchedule.DF_TO_ALL_DAY_MINUTES);
        }

        log__.debug(conf.toCsvString());
        return conf;
    }

    /**
     * スケジュールで扱う時間間隔を取得する。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return int 時間間隔
     * @throws SQLException SQL実行時例外
     */
    public int getHourDivision(Connection con) throws SQLException {
        int ret = GSConstSchedule.DF_HOUR_DIVISION;
        SchAdmConfModel conf = getAdmConfModel(con);
        ret = conf.getSadHourDiv();
        return ret;
    }

    /**
     * スケジュールで扱う時間間隔から１時間を何分割するかを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return int 時間の何分割数
     * @throws SQLException SQL実行時例外
     */
    public int getDayScheduleHourMemoriCount(Connection con)
    throws SQLException {
        int ret = GSConstSchedule.HOUR_DIVISION_COUNT_10;
        int hourDiv = getHourDivision(con);
        switch (hourDiv) {
        case 5:
            ret = GSConstSchedule.HOUR_DIVISION_COUNT_5;
            break;
        case 10:
            ret = GSConstSchedule.HOUR_DIVISION_COUNT_10;
            break;
        case 15:
            ret = GSConstSchedule.HOUR_DIVISION_COUNT_15;
            break;
        default:
            break;
        }
        return ret;
    }

    /**
     * 日間画面での1目盛の分を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return int 日間画面での1目盛の分
     * @throws SQLException SQL実行時例外
     */
    public int getDayScheduleHourMemoriMin(Connection con)
    throws SQLException {
        int divMin = getDayScheduleHourMemoriCount(con);
        return 60 / divMin;
    }

    /**
     * <br>[機  能] 基準日を指定し、基準日より古いスケジュールデータを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param bdate 基準日
     * @throws SQLException SQL実行エラー
     */
    public void deleteOldSchedule(Connection con, UDate bdate) throws SQLException {

        SchDataDao dao = new SchDataDao(con);
        SchPushListDao spcDao = new SchPushListDao(con);
        SchBinDao sbinDao = new SchBinDao(con);
        SchDataPubDao pubDao = new SchDataPubDao(con);

        //削除するスケジュールSIDを取得する。
        List<Integer> allDelList = dao.getScdSidList(bdate);

        if (allDelList == null || allDelList.size() < 1) {
            return;
        }

        dao.delete(new ArrayList<>(allDelList));
        spcDao.delete(allDelList);
        sbinDao.deleteTempFile(allDelList);
        pubDao.delete(allDelList);
    }

    /**
     * <br>[機  能] スケジュール情報が存在しないスケジュール拡張情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @throws SQLException SQL実行エラー
     */
    public void deleteSchNoData(Connection con) throws SQLException {

        SchExdataDao dao = new SchExdataDao(con);
        SchExdataBinDao exBinDao = new SchExdataBinDao(con);
        SchExdataPubDao exPubDao = new SchExdataPubDao(con);

        //削除するスケジュール拡張SIDを取得する。
        List<Integer> allDelList = dao.selectSchNoData();

        if (allDelList == null || allDelList.size() < 1) {
            return;
        }

        //スケジュール拡張情報を何度かに分けて削除する。
        int i = 0;
        int delCount = GSConstSchedule.SCH_BATCH_DELETE_COUNT;
        ArrayList<Integer> delList = new ArrayList<Integer>();
        for (Integer scdSid : allDelList) {

            delList.add(scdSid);

            if (i >= delCount) {
                //削除する。
                dao.delete(delList);
                exBinDao.deleteTempFile(delList);
                exPubDao.delete(delList);
                delList = new ArrayList<Integer>();
                i = 0;
            }

        }

        if (delList != null && delList.size() > 0) {
            //削除する。
            dao.delete(delList);
            exBinDao.deleteTempFile(delList);
            exPubDao.delete(delList);
        }
    }

    /**
     * <br>[機  能] スケジュールのデフォルト表示で表示するグループSIDを取得する。
     * <br>[解  説] DBに登録された個人設定情報を取得しその表示グループを返す。
     * <br>グループが削除されていた場合は、デフォルトグループを返す。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return グループSID
     * @throws SQLException SQL実行エラー
     */
    public int getDispDefaultGroupSid(Connection con, int usrSid) throws SQLException {
        //
        SchAdmConfModel aconf = getAdmConfModel(con);
        SchPriConfModel pconf = getSchPriConfModel(con, usrSid);

        if (pconf == null) {
            pconf = getDefaulPriConfModel(usrSid, con);
        }
        int gsid = 0;
        if (aconf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_GROUP) {
            if (pconf.getSccDspMygroup() != 0) {
                gsid = pconf.getSccDspMygroup();
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                if (cmgDao.getMyGroupList(usrSid,
                        String.valueOf(gsid)).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    GroupBiz gbiz = new GroupBiz();
                    gsid = gbiz.getDefaultGroupSid(usrSid, con);
                }
            } else {
                gsid = pconf.getSccDspGroup();
            }
        } else {
            if (pconf.getSccDspMygroup() != 0) {
                gsid = pconf.getSccDspMygroup();
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                if (cmgDao.getMyGroupList(usrSid,
                        String.valueOf(gsid)).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    GroupBiz gbiz = new GroupBiz();
                    gsid = gbiz.getDefaultGroupSid(usrSid, con);
                }
            } else {
                gsid = pconf.getSccDspGroup();
                //グループ存在チェック
                GroupDao gdao = new GroupDao(con);
                CmnGroupmModel group = gdao.getGroup(gsid);
                if (group == null) {
                    //個人設定未設定 or 不正データの場合、ユーザマネージャのデフォルトグループ
                    GroupBiz gbiz = new GroupBiz();
                    gsid = gbiz.getDefaultGroupSid(usrSid, con);
                } else {
                    if (GSConst.JTKBN_DELETE == group.getGrpJkbn()) {
                        //状態区分が削除の場合はデフォルトグループを返す
                        GroupBiz gbiz = new GroupBiz();
                        gsid = gbiz.getDefaultGroupSid(usrSid, con);
                    }
                }
            }
        }
        log__.debug("デフォルト表示グループID=>" + gsid);
        return gsid;
    }
    /**
     * <br>[機  能] スケジュールのデフォルト表示で表示するグループSIDを取得する。
     * <br>[解  説] DBに登録された個人設定情報を取得しその表示グループを返す。
     * <br>グループが削除されていた場合は、デフォルトグループを返す。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return グループSID
     * @throws SQLException SQL実行エラー
     */
    public String getDispDefaultGroupSidStr(Connection con, int usrSid) throws SQLException {
        GroupBiz gbiz = new GroupBiz();

        //管理者設定 デフォルト表示グループ = 1:デフォルトグループに強制 の場合、ユーザのデフォルトグループを返す
        SchAdmConfModel aconf = getAdmConfModel(con);
        if (aconf.getSadDspGroup() == GSConstSchedule.SAD_DSP_GROUP_DEFGROUP) {
            return String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
        }

        SchPriConfModel pconf = getSchPriConfModel(con, usrSid);
        if (pconf == null) {
            pconf = getDefaulPriConfModel(usrSid, con);
        }

        String gsid = null;
        if (aconf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_GROUP) {
            if (pconf.getSccDspMygroup() != 0) {
                gsid = String.valueOf(pconf.getSccDspMygroup());
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                if (cmgDao.getMyGroupList(usrSid,
                        String.valueOf(gsid)).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstSchedule.MY_GROUP_STRING + gsid;
                }
            } else if (pconf.getSccDspViewlist() != 0) {
                gsid = String.valueOf(pconf.getSccDspViewlist());
                //スケジュール表示リストの存在チェック
                SchMyviewlistDao viewListDao = new SchMyviewlistDao(con);
                if (viewListDao.select(pconf.getSccDspViewlist(), usrSid) == null) {
                    //表示リストが存在しない場合はデフォルトグループを返す
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstSchedule.DSP_LIST_STRING + gsid;
                }
            } else {
                gsid = String.valueOf(pconf.getSccDspGroup());
                //個人設定値が閲覧可能なグループで無い場合はデフォルトグループを表示
                if (!isDspOkGroup(pconf.getSccDspGroup(), usrSid, con)) {
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                }
            }
        } else {
            if (pconf.getSccDspMygroup() != 0) {
                gsid = String.valueOf(pconf.getSccDspMygroup());
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                if (cmgDao.getMyGroupList(usrSid,
                        String.valueOf(gsid)).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstSchedule.MY_GROUP_STRING + gsid;
                }
            } else if (pconf.getSccDspViewlist() != 0) {
                gsid = String.valueOf(pconf.getSccDspViewlist());
                //スケジュール表示リストの存在チェック
                SchMyviewlistDao viewListDao = new SchMyviewlistDao(con);
                if (viewListDao.select(pconf.getSccDspViewlist(), usrSid) == null) {
                    //表示リストが存在しない場合はデフォルトグループを返す
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstSchedule.DSP_LIST_STRING + gsid;
                }
            } else {
                //表示グループ = デフォルトグループ
                if (pconf.getSccDspGroup() == GSConstSchedule.SCC_DSP_GROUP_DEF) {
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = String.valueOf(pconf.getSccDspGroup());
                    //グループ存在チェック
                    GroupDao gdao = new GroupDao(con);
                    CmnGroupmModel group = gdao.getGroup(Integer.parseInt(gsid));
                    if (group == null) {
                        //個人設定未設定 or 不正データの場合、ユーザマネージャのデフォルトグループ
                        gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                    } else {
                        if (GSConst.JTKBN_DELETE == group.getGrpJkbn()) {
                            //状態区分が削除の場合はデフォルトグループを返す
                            gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                        }
                    }
                }
            }
        }

        return gsid;
    }
    /**
     * <br>[機  能] スケジュールのデフォルト表示で表示するグループSIDを取得する。
     * <br>[解  説] DBに登録された個人設定情報を取得しその表示グループを返す。
     * <br>グループが削除されていた場合は、デフォルトグループを返す。
     * <br>デフォルト表示グループがスケジュールリストの場合は、ユーザのデフォルトグループを返す。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return グループSID
     * @throws SQLException SQL実行エラー
     */
    public String getCommboDefaultGroupSidStr(Connection con, int usrSid) throws SQLException {
        GroupBiz gbiz = new GroupBiz();

        //管理者設定 デフォルト表示グループ = 1:デフォルトグループに強制 の場合、ユーザのデフォルトグループを返す
        SchAdmConfModel aconf = getAdmConfModel(con);
        if (aconf.getSadDspGroup() == GSConstSchedule.SAD_DSP_GROUP_DEFGROUP) {
            return String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
        }

        SchPriConfModel pconf = getSchPriConfModel(con, usrSid);
        if (pconf == null) {
            pconf = getDefaulPriConfModel(usrSid, con);
        }

        String gsid = null;
        if (aconf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_GROUP) {
            if (pconf.getSccDspMygroup() != 0) {
                gsid = String.valueOf(pconf.getSccDspMygroup());
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                if (cmgDao.getMyGroupList(usrSid,
                        String.valueOf(gsid)).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstSchedule.MY_GROUP_STRING + gsid;
                }
            } else if (pconf.getSccDspViewlist() != 0) {
                //スケジュール表示リストが設定されていた場合はデフォルトグループを表示
                //(ユーザ，グループコンボにスケジュールリストが設定されないことを想定)
                gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
            } else {
                gsid = String.valueOf(pconf.getSccDspGroup());
                //個人設定値が閲覧可能なグループで無い場合はデフォルトグループを表示
                if (!isDspOkGroup(pconf.getSccDspGroup(), usrSid, con)) {
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                }
            }
        } else {
            if (pconf.getSccDspMygroup() != 0) {
                gsid = String.valueOf(pconf.getSccDspMygroup());
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                if (cmgDao.getMyGroupList(usrSid,
                        String.valueOf(gsid)).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstSchedule.MY_GROUP_STRING + gsid;
                }
            } else if (pconf.getSccDspViewlist() != 0) {
                //スケジュール表示リストが設定されていた場合はデフォルトグループを表示
                //(ユーザ，グループコンボにスケジュールリストが設定されないことを想定)
                gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
            } else {
                //表示グループ = デフォルトグループ
                if (pconf.getSccDspGroup() == GSConstSchedule.SCC_DSP_GROUP_DEF) {
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = String.valueOf(pconf.getSccDspGroup());
                    //グループ存在チェック
                    GroupDao gdao = new GroupDao(con);
                    CmnGroupmModel group = gdao.getGroup(Integer.parseInt(gsid));
                    if (group == null) {
                        //個人設定未設定 or 不正データの場合、ユーザマネージャのデフォルトグループ
                        gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                    } else {
                        if (GSConst.JTKBN_DELETE == group.getGrpJkbn()) {
                            //状態区分が削除の場合はデフォルトグループを返す
                            gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                        }
                    }
                }
            }
        }

        return gsid;
    }
    /**
     * フォーム情報のグループコンボ値からグループSID又はマイグループSID、または表示リストSIDを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又はマイグループSID
     */
    public static int getDspGroupSid(String gpSid) {
        int ret = 0;
        if (StringUtil.isNullZeroString(gpSid)) {
            return ret;
        }

        if (isMyGroupSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else if (isDspListSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }
    /**
     * フォーム情報のグループコンボ値からグループSID又は表示リストSIDを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又は表示リストSID
     */
    public static int getDspListSid(String gpSid) {
        int ret = 0;
        if (StringUtil.isNullZeroString(gpSid)) {
            return ret;
        }

        if (isDspListSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }
    /**
     * ユーザが閲覧可能なグループか判定する。
     * 所属グループ、グループ外ユーザとして設定されているグループは閲覧可能
     * @param gpSid グループSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return boolean true=可能 false=不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isDspOkGroup(int gpSid, int usrSid, Connection con) throws SQLException {

        //管理者設定
        SchAdmConfModel admConf = getAdmConfModel(con);
        //閲覧可能なグループリスト取得
        SchGroupBiz schGrpBiz = new SchGroupBiz();
        List<Integer> accessGrpList
            = schGrpBiz.getAccessGrpList(con, usrSid, admConf.getSadCrange());

        return accessGrpList.indexOf(gpSid) >= 0;
    }

    /**
     * フォーム情報のグループコンボ値がグループSIDかマイグループSIDかを判定する
     * <br>[機  能]先頭文字に"M"が有る場合、マイグループSID
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isMyGroupSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf(GSConstSchedule.MY_GROUP_STRING);

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * フォーム情報のグループコンボ値が表示リストSIDかを判定する
     * <br>[機  能]先頭文字に"H"が有る場合、表示リストSID
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isDspListSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf(GSConstSchedule.DSP_LIST_STRING);

        // 先頭文字に"L"が有る場合は表示リスト
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * <br>[機  能] スケジュール拡張登録画面の初期値を取得
     * <br>[解  説] 画面表示用に使用します
     * <br>[備  考] 拡張SIDと登録・更新者、時間は設定されません。
     * @param usrSid ユーザSID
     * @param date 登録・編集日付
     * @param con DBコネクション
     * @param userKbn 0:個人 1:グループスケジュール
     * @return ScheduleExSearchModel 拡張情報Bean
     * @throws SQLException SQL実行エラー
     */
    public ScheduleExSearchModel getDispDefaultExtend(
            int usrSid, UDate date, Connection con, int userKbn)
    throws SQLException {
        //個人設定を取得
        SchPriConfModel priConf = getSchPriConfModel(con, usrSid);
        SchAdmConfModel admConf = getAdmConfModel(con);

        ScheduleExSearchModel extMdl = new ScheduleExSearchModel();

        extMdl.setSceKbn(GSConstSchedule.EXTEND_KBN_DAY);
        extMdl.setSceTranKbn(GSConstSchedule.FURIKAE_KBN_NONE);
        extMdl.setSceWeek(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDay(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDayOfYearly(date.getIntDay());
        extMdl.setSceMonthOfYearly(date.getMonth());
        extMdl.setSceDweek1(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDweek2(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDweek3(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDweek4(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDweek5(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDweek6(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDweek7(GSConstSchedule.SETTING_NONE);
        extMdl.setSceDateFr(date);
        extMdl.setSceDateTo(date);

        if (admConf.getSadIniTimeStype() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
            UDate frTime = new UDate();
            frTime.setZeroHhMmSs();
            frTime.setHour(admConf.getSadIniFrH());
            frTime.setMinute(admConf.getSadIniFrM());
            extMdl.setSceTimeFr(frTime);

            UDate toTime = new UDate();
            toTime.setZeroHhMmSs();
            toTime.setHour(admConf.getSadIniToH());
            toTime.setMinute(admConf.getSadIniToM());
            extMdl.setSceTimeTo(toTime);
        } else {
            extMdl.setSceTimeFr(priConf.getSccIniFrDate());
            extMdl.setSceTimeTo(priConf.getSccIniToDate());
        }

        extMdl.setSceTitle("");
        extMdl.setSceBgcolor(priConf.getSccIniFcolor());
        extMdl.setSceValue("");
        extMdl.setSceBiko("");
        extMdl.setScePeriodKbn(GSConstSchedule.CONF_KBN_ONLY);

        int initPub = getInitPubAuth(con, priConf);
        if (userKbn == GSConstSchedule.USER_KBN_GROUP
            && initPub != GSConstSchedule.DSP_PUBLIC
            && initPub != GSConstSchedule.DSP_NOT_PUBLIC) {
            initPub = GSConstSchedule.DSP_PUBLIC;
        }
        extMdl.setScePublic(initPub);
        extMdl.setSceEdit(getInitEditAuth(con, priConf));

        return extMdl;
    }

    /**
     * 表示グループ用のグループリストを取得する
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<SchLabelValueModel> getGroupLabelForSchedule(int usrSid,
            Connection con, boolean sentakuFlg) throws SQLException {
        return getGroupLabelForSchedule(usrSid, con, sentakuFlg,
                GSConstSchedule.SSP_AUTHFILTER_VIEW);
    }

    /**
     * スケジュールグループ選択用のグループリストを取得する
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @param authFilter 0:すべて 1:閲覧用 2:編集用
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<SchLabelValueModel> getGroupLabelForSchedule(int usrSid,
            Connection con, boolean sentakuFlg, int authFilter) throws SQLException {
        //管理者設定
        SchAdmConfModel admConf = getAdmConfModel(con);

        ArrayList<SchLabelValueModel> labelList = new ArrayList<SchLabelValueModel>();

        //グループリスト取得
        SchGroupBiz schGrpBiz = new SchGroupBiz();
        List<GroupModel> gpList = null;
        if (admConf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
            log__.debug("全員で共有するグループリストを取得");
            //全員で共有
            gpList = schGrpBiz.getGroupCombList(con, -1);
        } else {
            //所属グループのみで共有
            log__.debug("所属グループのみで共有するグループリストを取得");
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

            GroupDao dao = new GroupDao(con);
            ArrayList<GroupModel> gpBaseList = dao.getGroupList(sortMdl);


            SchDao schDao = new SchDao(con);
//          //ユーザが所属するグループを取得
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> belongGroupMdlList = belongDao.selectUserBelongGroupSid(usrSid);
            List<Integer> belongGrpList = new ArrayList<Integer>();
            List<Integer> accessGrpList = new ArrayList<Integer>();
            if (authFilter == GSConstSchedule.SSP_AUTHFILTER_EDIT) {
                //編集可能ユーザが所属するグループを取得
                belongGrpList
                  = schDao.getGroupBelongSpRegistUser(usrSid);
                accessGrpList = schDao.getAccessGrpList(usrSid, GSConstSchedule.SSP_AUTH_EDIT);
            }
            if (authFilter == GSConstSchedule.SSP_AUTHFILTER_VIEW) {
                //閲覧可能ユーザが所属するグループを取得
                belongGrpList
                  = schDao.getGroupBelongSpAccessUser(usrSid);
                accessGrpList = schDao.getAccessGrpList(usrSid);
            }

            gpList = new ArrayList<GroupModel>();
            for (GroupModel gpMdl : gpBaseList) {
                int grpSid = gpMdl.getGroupSid();
                if (belongGroupMdlList.contains(grpSid)
                || accessGrpList.contains(grpSid)
                || belongGrpList.contains(grpSid)) {
                    gpList.add(gpMdl);
                }
            }

        }
        SchLabelValueModel label = null;
        int grpSid = -1;
        for (GroupModel gmodel : gpList) {
            grpSid = gmodel.getGroupSid();
            label = new SchLabelValueModel(gmodel.getGroupName(),
                    String.valueOf(grpSid), "0");
            label.setViewKbn(true);

            labelList.add(label);
        }

        //共有範囲設定が「全て共有」の場合マイグループを追加
        List<SchLabelValueModel> mylabelList = getMyGroupLabel(usrSid, con);
        labelList.addAll(0, mylabelList);
        //グループ
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textHide = gsMsg.getMessage("cmn.hide");
        if (sentakuFlg) {
            labelList.add(new SchLabelValueModel(textHide, String.valueOf(-1), "0"));
        }

        return labelList;
    }
    /**
     * 表示グループ用のグループリストを取得する
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<SchLabelValueModel> getGroupLabelCombo(int usrSid,
            Connection con, boolean sentakuFlg) throws SQLException {
        ArrayList<SchLabelValueModel> labelList = new ArrayList<SchLabelValueModel>();

        //グループリスト取得
        SchGroupBiz schGrpBiz = new SchGroupBiz();
        List<GroupModel> gpList = schGrpBiz.getGroupCombList(con, -1);

        SchLabelValueModel label = null;
        int grpSid = -1;
        for (GroupModel gmodel : gpList) {
            grpSid = gmodel.getGroupSid();
            label = new SchLabelValueModel(gmodel.getGroupName(),
                    String.valueOf(grpSid), "0");
            label.setViewKbn(true);

            labelList.add(label);
        }

        //マイグループを追加
        List<SchLabelValueModel> mylabelList = getMyGroupLabel(usrSid, con);
        labelList.addAll(0, mylabelList);

        //グループ
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textHide = gsMsg.getMessage("cmn.hide");
        if (sentakuFlg) {
            labelList.add(new SchLabelValueModel(textHide, String.valueOf(-1), "0"));
        }

        return labelList;
    }

    /**
     * 表示グループ用のグループリストを取得する(所属グループのみ)
     * 所属メンバーのいないマイグループは取得しない。
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @param defGrpFlg true:グループリストに「デフォルトグループ」を含める, false:含めない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<SchLabelValueModel> getGroupLabelForScheduleMygrpExist(int usrSid,
            Connection con, boolean sentakuFlg, boolean defGrpFlg) throws SQLException {
        //管理者設定
        SchAdmConfModel admConf = getAdmConfModel(con);

        ArrayList<SchLabelValueModel> labelList = new ArrayList<SchLabelValueModel>();

        //表示リストを追加
        List<SchLabelValueModel> dspLabelList = getListLabel(usrSid, con);
        labelList.addAll(dspLabelList);

        //共有範囲設定が「全て共有」の場合マイグループを追加
        List<SchLabelValueModel> mylabelList = getMyGroupLabelExistMember(usrSid, con);
        labelList.addAll(mylabelList);

        //「デフォルトグループ」
        if (defGrpFlg) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            labelList.add(new SchLabelValueModel(gsMsg.getMessage("user.35"),
                            Integer.toString(GSConstSchedule.SCC_DSP_GROUP_DEF), "0"));
        }

        //グループリスト取得
        ArrayList<GroupModel> gpList = null;
        if (admConf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
            //全員で共有
            GroupBiz groupBiz = new GroupBiz();
            gpList = groupBiz.getGroupCombList(con);
        } else {
            //所属グループのみで共有
            UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
            gpList = gpDao.selectGroupNmListOrderbyClass(usrSid);
        }

        for (GroupModel gmodel : gpList) {
            labelList.add(new SchLabelValueModel(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid()), "0"));
        }

        //グループ
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textHide = gsMsg.getMessage("cmn.hide");
        if (sentakuFlg) {
            labelList.add(new SchLabelValueModel(textHide, String.valueOf(-1), "0"));
        }

        return labelList;
    }

    /**
     * ユーザIDを指定しマイグループラベルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return List
     * @throws SQLException SQL実行時例外
     */
    public List<SchLabelValueModel> getMyGroupLabel(int userSid, Connection con)
    throws SQLException {
        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(userSid);

        //マイグループリストをセット
        List<SchLabelValueModel> cmgLabelList = new ArrayList<SchLabelValueModel>();
        for (CmnMyGroupModel cmgMdl : cmgList) {

            cmgLabelList.add(
                    new SchLabelValueModel(
                            cmgMdl.getMgpName(),
                            GSConstSchedule.MY_GROUP_STRING
                            + String.valueOf(cmgMdl.getMgpSid()), "1")
                            );
        }
        return cmgLabelList;
    }

    /**
     * ユーザIDを指定しマイグループラベルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return List
     * @throws SQLException SQL実行時例外
     */
    public List<SchLabelValueModel> getMyGroupLabelExistMember(int userSid, Connection con)
    throws SQLException {
        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupExistMemberList(userSid);

        //マイグループリストをセット
        List<SchLabelValueModel> cmgLabelList = new ArrayList<SchLabelValueModel>();
        for (CmnMyGroupModel cmgMdl : cmgList) {

            cmgLabelList.add(
                    new SchLabelValueModel(
                            cmgMdl.getMgpName(),
                            GSConstSchedule.MY_GROUP_STRING
                            + String.valueOf(cmgMdl.getMgpSid()), "1")
                            );
        }
        return cmgLabelList;
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSids バイナリSID
     * @param scrId 画面ID
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempRoot テンポラリディレクトリ
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setTempFile(
            String[] binSids,
            String scrId,
            Connection con,
            String appRoot,
            String tempRoot,
            String domain) throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(reqMdl__,
                GSConstSchedule.PLUGIN_ID_SCHEDULE, scrId);

        //添付ファイル情報を取得する
        List<CmnBinfModel> cmList = cmnBiz.getBinInfo(con, binSids, domain);
        int fileNum = 1;
        for (CmnBinfModel cbMdl : cmList) {
            if (cbMdl.getBinJkbn() == GSConst.JTKBN_DELETE) {
                continue;
            }

            //添付ファイルをテンポラリディレクトリにコピーする。
            cmnBiz.saveTempFile(dateStr, cbMdl, appRoot, tempDir, fileNum);

            fileNum++;
        }
    }

    /**
     * <br>[機  能] ユーザIDを指定し、表示リストラベルを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return List
     * @throws SQLException SQL実行時例外
     */
    public List<SchLabelValueModel> getListLabel(int userSid, Connection con)
    throws SQLException {

        //ユーザSIDから表示リスト情報を取得する
        SchMyviewlistDao smyDao = new SchMyviewlistDao(con);
        List<SchMyviewlistModel> smyList = smyDao.select(userSid);

        //マイグループリストをセット
        List<SchLabelValueModel> cmgLabelList = new ArrayList<SchLabelValueModel>();
        for (SchMyviewlistModel smyMdl : smyList) {

            cmgLabelList.add(
                    new SchLabelValueModel(
                            smyMdl.getSmyName(),
                            GSConstSchedule.DSP_LIST_STRING
                            + String.valueOf(smyMdl.getSmySid()), "2")
                            );
        }
        return cmgLabelList;
    }


    /**
     * <br>[機  能] モバイルショートメールでスケジュール登録通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param schMdl スケジュール内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void sendSmail(
        Connection con,
        MlCountMtController cntCon,
        SchDataModel schMdl,
        String appRootPath,
        PluginConfig pluginConfig,
        boolean smailPluginUseFlg,
        RequestModel reqMdl
        ) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (!smailPluginUseFlg) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel umodel = null;
        String userName = "";
        // 被登録者
        int userSid = schMdl.getScdUsrSid();
        // 登録者
        int addUserSid = schMdl.getScdEuid();
        SchPriConfModel priConf = null;
        SchAdmConfModel admConf = getAdmConfModel(con);

        //送信するユーザSIDリストを作成
        List<Integer> sidList = new ArrayList<Integer>();
        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
            //グループスケジュールの場合
            //所属しているユーザ一覧を取得
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> belongList
                = userBiz.getBelongUserList(con, userSid, null);

            for (CmnUsrmInfModel usMdl : belongList) {
                priConf = getSchPriConfModel(con, usMdl.getUsrSid());
                if (getSmlSendKbn(admConf, priConf, 0)) {
                    sidList.add(new Integer(usMdl.getUsrSid()));
                }
            }
            GroupDao gpDao = new GroupDao(con);
            CmnGroupmModel gpMdl = gpDao.getGroup(userSid);
            userName = gpMdl.getGrpName();
        } else {
            //ユーザスケジュールの場合
            priConf = getSchPriConfModel(con, userSid);
            //本人以外が登録したスケジュール且つ、ショートメール通知する場合
            if (getSmlSendKbn(admConf, priConf, 1)
                    && schMdl.getScdUsrSid() != schMdl.getScdEuid()) {
                sidList.add(new Integer(userSid));
                umodel = udao.select(userSid);
                userName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            }
        }
        //送信先がない場合は終了
        if (sidList.size() < 1) {
            return;
        }

        priConf.getSccSmailGroup();
        try {

            //登録ユーザ名
            umodel = udao.select(addUserSid);
            String addUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            //タイトル
            String thtitle = schMdl.getScdTitle();
            //内容
            String body = schMdl.getScdValue();
            //備考
            String biko = schMdl.getScdBiko();
            //開始日時
            UDate frDate = schMdl.getScdFrDate();
            String fdate = UDateUtil.getSlashYYMD(frDate) + " "
            + UDateUtil.getSeparateHMS(frDate);
            //終了日時
            UDate toDate = schMdl.getScdToDate();
            String tdate = UDateUtil.getSlashYYMD(toDate) + " "
            + UDateUtil.getSeparateHMS(toDate);
            //登録日時
            UDate aDate = schMdl.getScdEdate();
            String adate = UDateUtil.getSlashYYMD(aDate) + " "
            + UDateUtil.getSeparateHMS(aDate);
            //本文
            String tmpPath = getSmlTemplateFilePath(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("ADATE", adate);
            map.put("UNAME", addUserName);
            map.put("NAME", userName);
            map.put("TITLE", thtitle);
            map.put("FRDATE", fdate);
            map.put("TODATE", tdate);
            map.put("BODY", body);
            map.put("BIKO", biko);

            String bodyml = StringUtil.merge(tmpBody, map);
            String omit = gsMsg.getMessage("cmn.mail.omit");

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                bodyml = omit + "\r\n\r\n" + bodyml;
                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
            }

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //タイトル
            //スケジュール
            String textSchedule = gsMsg.getMessage("schedule.108");
            //登録通知
            String textAdd = gsMsg.getMessage("schedule.130");
            String title = "[GS " + textSchedule + "] " +  textAdd  + " " + thtitle;
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
            SmlSender sender = new SmlSender(
                    con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl);
            sender.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log__.error("ショートメール送信に失敗しました。");
            throw e;
        }
    }

    /**
     * <br>[機  能] ショートメールプラグインでスケジュール登録通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param schMdl スケジュール内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param url スケジュールURL
     * @throws Exception 実行例外
     */
    public void sendPlgSmail(
        Connection con,
        MlCountMtController cntCon,
        SchDataModel schMdl,
        String appRootPath,
        PluginConfig pluginConfig,
        boolean smailPluginUseFlg,
        String url) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (!smailPluginUseFlg) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel umodel = null;
        String userName = "";
        // 被登録者
        int userSid = schMdl.getScdUsrSid();
        // 登録者
        int addUserSid = schMdl.getScdEuid();

        SchAdmConfModel admConf = getAdmConfModel(con);
        SchPriConfModel priConf = null;

        //送信するユーザSIDリストを作成
        List<Integer> sidList = new ArrayList<Integer>();
        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
            //グループスケジュールの場合
            //所属しているユーザ一覧を取得
//            UserSearchDao usDao = new UserSearchDao(con);
//            ArrayList<CmnUsrmInfModel> belongList = usDao.getBelongUserList(userSid, null);
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> belongList
                = userBiz.getBelongUserList(con, userSid, null);
            for (CmnUsrmInfModel usMdl : belongList) {
                priConf = getSchPriConfModel(con, usMdl.getUsrSid());
                if (getSmlSendKbn(admConf, priConf, 0)) {
                    if (usMdl.getUsrSid() != reqMdl__.getSmodel().getUsrsid()) {
                        //登録者以外に通知
                        sidList.add(new Integer(usMdl.getUsrSid()));
                    }
                }
            }
            GroupDao gpDao = new GroupDao(con);
            CmnGroupmModel gpMdl = gpDao.getGroup(userSid);
            userName = gpMdl.getGrpName();
        } else {
            //ユーザスケジュールの場合
            priConf = getSchPriConfModel(con, userSid);
            //本人以外が登録したスケジュール且つ、ショートメール通知する場合
            if (getSmlSendKbn(admConf, priConf, 1)
                    && schMdl.getScdUsrSid() != schMdl.getScdEuid()) {
                sidList.add(new Integer(userSid));
                umodel = udao.select(userSid);
                userName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            }
        }
        //送信先がない場合は終了
        if (sidList.size() < 1) {
            return;
        }

        priConf.getSccSmailGroup();
        try {

            //登録ユーザ名
            umodel = udao.select(addUserSid);
            String addUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            //タイトル
            String thtitle = schMdl.getScdTitle();
            //内容
            String body = schMdl.getScdValue();
            //備考
            String biko = schMdl.getScdBiko();
            //開始日時
            UDate frDate = schMdl.getScdFrDate();
            String fdate = UDateUtil.getSlashYYMD(frDate) + " "
            + UDateUtil.getSeparateHMS(frDate);
            //終了日時
            UDate toDate = schMdl.getScdToDate();
            String tdate = UDateUtil.getSlashYYMD(toDate) + " "
            + UDateUtil.getSeparateHMS(toDate);
            //登録日時
            UDate aDate = schMdl.getScdEdate();
            String adate = UDateUtil.getSlashYYMD(aDate) + " "
            + UDateUtil.getSeparateHMS(aDate);
            //本文
            String tmpPath = getSmlTemplateFilePathPlg(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("ADATE", adate);
            map.put("UNAME", addUserName);
            map.put("NAME", userName);
            map.put("TITLE", thtitle);
            map.put("FRDATE", fdate);
            map.put("TODATE", tdate);
            map.put("BODY", body);
            map.put("BIKO", biko);
            map.put("URL", url);

            String bodyml = StringUtil.merge(tmpBody, map);
            String omit = gsMsg.getMessage("cmn.mail.omit");

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                bodyml = omit + "\r\n\r\n" + bodyml;
                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
            }

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //タイトル
            //スケジュール
            String textSchedule = gsMsg.getMessage("schedule.108");
            //登録通知
            String textAdd = gsMsg.getMessage("schedule.130");
            String title = "[GS " + textSchedule + "] " + textAdd + " " + thtitle;
            title = StringUtil.trimRengeString(title,
                    GSConstCommon.MAX_LENGTH_SMLTITLE);
            smlModel.setSendTitle(title);

            //本文
            smlModel.setSendBody(bodyml);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

            //メール送信処理開始
            SmlSender sender = new SmlSender(
                    con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl__);
            sender.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log__.error("ショートメール送信に失敗しました。");
            throw e;
        }
    }

    /**
     * <br>[機  能] ショートメールプラグインでスケジュール 出欠確認 回答依頼通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param schMdl スケジュール内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param url スケジュールURL
     * @param sendKbn 通知区分 0:回答依頼通知  1:再通知
     * @throws Exception 実行例外
     */
    public void sendAttendSmail(
        Connection con,
        MlCountMtController cntCon,
        SchDataModel schMdl,
        String appRootPath,
        PluginConfig pluginConfig,
        boolean smailPluginUseFlg,
        String url,
        int sendKbn) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (!smailPluginUseFlg) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel umodel = null;
        String userName = "";
        // 被登録者
        int userSid = schMdl.getScdUsrSid();
        // 登録者
        int addUserSid = schMdl.getScdEuid();

        SchAdmConfModel admConf = getAdmConfModel(con);
        SchPriConfModel priConf = null;

        //送信するユーザSIDリストを作成
        List<Integer> sidList = new ArrayList<Integer>();

        //ユーザスケジュールの場合
        priConf = getSchPriConfModel(con, userSid);
        //出欠確認メール通知する場合
        if (getAttendSmlSendKbn(admConf, priConf)) {
            sidList.add(new Integer(userSid));
            umodel = udao.select(userSid);
            userName = umodel.getUsiSei() + " " + umodel.getUsiMei();
        }

        //送信先がない場合は終了
        if (sidList.size() < 1) {
            return;
        }

        try {

            //登録ユーザ名
            umodel = udao.select(addUserSid);
            String addUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            //タイトル
            String thtitle = schMdl.getScdTitle();
            //内容
            String body = schMdl.getScdValue();
            //備考
            String biko = schMdl.getScdBiko();
            //開始日時
            UDate frDate = schMdl.getScdFrDate();
            String fdate = UDateUtil.getSlashYYMD(frDate) + " "
            + UDateUtil.getSeparateHMS(frDate);
            //終了日時
            UDate toDate = schMdl.getScdToDate();
            String tdate = UDateUtil.getSlashYYMD(toDate) + " "
            + UDateUtil.getSeparateHMS(toDate);
            //登録日時
            UDate aDate = schMdl.getScdEdate();
            String adate = UDateUtil.getSlashYYMD(aDate) + " "
            + UDateUtil.getSeparateHMS(aDate);
            //本文
            String tmpPath = null;
          //テンプレートファイルパス取得
            if (sendKbn == 1) {
                tmpPath = getSmlTemplateFilePathAttendEdit(appRootPath);
            } else {
                tmpPath = getSmlTemplateFilePathAttend(appRootPath);
            }
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("ADATE", adate);
            map.put("UNAME", addUserName);
            map.put("NAME", userName);
            map.put("TITLE", thtitle);
            map.put("FRDATE", fdate);
            map.put("TODATE", tdate);
            map.put("BODY", body);
            map.put("BIKO", biko);
            map.put("URL", url);

            String bodyml = StringUtil.merge(tmpBody, map);
            String omit = gsMsg.getMessage("cmn.mail.omit");

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                bodyml = omit + "\r\n\r\n" + bodyml;
                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
            }

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //タイトル
            //スケジュール
            String textSchedule = gsMsg.getMessage("schedule.108");
            //出欠確認
            String textAdd = null;
            if (sendKbn == 1) {
                textAdd = "出欠確認 (再通知)";
            } else {
                textAdd = "出欠確認";
            }
            String title = "[GS " + textSchedule + "] " + textAdd + " " + thtitle;
            title = StringUtil.trimRengeString(title,
                    GSConstCommon.MAX_LENGTH_SMLTITLE);
            smlModel.setSendTitle(title);

            //本文
            smlModel.setSendBody(bodyml);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

            //メール送信処理開始
            SmlSender sender = new SmlSender(
                    con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl__);
            sender.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log__.error("ショートメール送信に失敗しました。");
            throw e;
        }
    }



    /**
     * <br>[機  能] ショートメールプラグインでスケジュール 出欠確認 完了通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param schMdl スケジュール内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param url スケジュールURL
     * @param date 完了日時
     * @throws Exception 実行例外
     */
    public void sendAttendCompSmail(
        Connection con,
        MlCountMtController cntCon,
        SchDataModel schMdl,
        String appRootPath,
        PluginConfig pluginConfig,
        boolean smailPluginUseFlg,
        String url,
        UDate date) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (!smailPluginUseFlg) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }

        SchAdmConfModel admConf = getAdmConfModel(con);
        SchPriConfModel priConf = null;

        //送信するユーザSIDリストを作成
        List<Integer> sidList = new ArrayList<Integer>();

        //ユーザスケジュールの場合
        priConf = getSchPriConfModel(con, schMdl.getScdUsrSid());
        //本人以外が登録したスケジュール且つ、ショートメール通知する場合
        if (getAttendSmlSendKbn(admConf, priConf)) {
            sidList.add(new Integer(schMdl.getScdUsrSid()));
        }

        //送信先がない場合は終了
        if (sidList.size() < 1) {
            return;
        }

        try {
            //タイトル
            String thtitle = schMdl.getScdTitle();
            //内容
            String body = schMdl.getScdValue();
            //備考
            String biko = schMdl.getScdBiko();
            //開始日時
            UDate frDate = schMdl.getScdFrDate();
            String fdate = UDateUtil.getSlashYYMD(frDate) + " "
            + UDateUtil.getSeparateHMS(frDate);
            //終了日時
            UDate toDate = schMdl.getScdToDate();
            String tdate = UDateUtil.getSlashYYMD(toDate) + " "
            + UDateUtil.getSeparateHMS(toDate);
            //登録日時（回答完了日時）
            UDate aDate = date;
            String adate = UDateUtil.getSlashYYMD(aDate) + " "
            + UDateUtil.getSeparateHMS(aDate);
            //本文
            String tmpPath = getSmlTemplateFilePathAttendComp(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("ADATE", adate);
            map.put("TITLE", thtitle);
            map.put("FRDATE", fdate);
            map.put("TODATE", tdate);
            map.put("BODY", body);
            map.put("BIKO", biko);
            map.put("URL", url);

            String bodyml = StringUtil.merge(tmpBody, map);
            String omit = gsMsg.getMessage("cmn.mail.omit");

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                bodyml = omit + "\r\n\r\n" + bodyml;
                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
            }

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //タイトル
            //スケジュール
            String textSchedule = gsMsg.getMessage("schedule.108");
            //出欠確認
            String textAdd = "出欠確認 完了通知";
            String title = "[GS " + textSchedule + "] " + textAdd + " " + thtitle;
            title = StringUtil.trimRengeString(title,
                    GSConstCommon.MAX_LENGTH_SMLTITLE);
            smlModel.setSendTitle(title);

            //本文
            smlModel.setSendBody(bodyml);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

            //メール送信処理開始
            SmlSender sender = new SmlSender(
                    con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl__);
            sender.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log__.error("ショートメール送信に失敗しました。");
            throw e;
        }
    }

    /**
     * <br>[機  能] ショートメールの送信区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf SchAdmConfModel
     * @param priConf SchPriConfModel
     * @param schKbn 0:グループ 1:個人
     * @return sendFlg 送信区分
     * @throws Exception 実行例外
     */
    public boolean getSmlSendKbn(
            SchAdmConfModel admConf,
            SchPriConfModel priConf,
            int schKbn
        ) throws Exception {
        boolean sendFlg = false;

        if (admConf.getSadSmailSendKbn() == GSConstSchedule.SMAIL_SEND_KBN_ADMIN) {
            if (schKbn == 0) {
                sendFlg = admConf.getSadSmailGroup() == GSConstSchedule.SMAIL_USE;
            } else {
                sendFlg = admConf.getSadSmail() == GSConstSchedule.SMAIL_USE;
            }
        } else {
            if (schKbn == 0) {
                sendFlg = priConf.getSccSmailGroup() == GSConstSchedule.SMAIL_USE;
            } else {
                sendFlg = priConf.getSccSmail() == GSConstSchedule.SMAIL_USE;
            }
        }

        return sendFlg;
    }

    /**
     * <br>[機  能] ショートメールの送信区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf SchAdmConfModel
     * @param priConf SchPriConfModel
     * @return sendFlg 送信区分
     * @throws Exception 実行例外
     */
    public boolean getAttendSmlSendKbn(
            SchAdmConfModel admConf,
            SchPriConfModel priConf
        ) throws Exception {
        boolean sendFlg = false;

        if (admConf.getSadSmailSendKbn() == GSConstSchedule.SMAIL_SEND_KBN_ADMIN) {
                sendFlg = admConf.getSadSmailAttend() == GSConstSchedule.SMAIL_USE;
        } else {
                sendFlg = priConf.getSccSmailAttend() == GSConstSchedule.SMAIL_USE;
        }

        return sendFlg;
    }

    /**
     * <br>[機  能] ショートメールでスケジュール登録通知を行う。
     * <br>[解  説] 拡張登録用
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param schMdl スケジュール内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param dateList 登録日付リスト
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @throws Exception 実行例外
     */
    public void sendSmailEx(
        Connection con,
        MlCountMtController cntCon,
        SchDataModel schMdl,
        String appRootPath,
        PluginConfig pluginConfig,
        ArrayList<String> dateList,
        boolean smailPluginUseFlg) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (!smailPluginUseFlg) {
            //ショートメール機能が無効の場合リターン
            return;
        }
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel umodel = null;
        String userName = "";
        // 被登録者
        int userSid = schMdl.getScdUsrSid();
        // 登録者
        int addUserSid = schMdl.getScdEuid();
        SchPriConfModel priConf = null;
        SchAdmConfModel admConf = getAdmConfModel(con);

        //送信するユーザSIDリストを作成
        List<Integer> sidList = new ArrayList<Integer>();
        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
            //グループスケジュールの場合
            //所属しているユーザ一覧を取得
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> belongList
                = userBiz.getBelongUserList(con, userSid, null);
            for (CmnUsrmInfModel usMdl : belongList) {
                priConf = getSchPriConfModel(con, usMdl.getUsrSid());
                if (getSmlSendKbn(admConf, priConf, 0)) {
                    sidList.add(new Integer(usMdl.getUsrSid()));
                }
            }
            GroupDao gpDao = new GroupDao(con);
            CmnGroupmModel gpMdl = gpDao.getGroup(userSid);
            userName = gpMdl.getGrpName();
        } else {
            //ユーザスケジュールの場合
            priConf = getSchPriConfModel(con, userSid);
            //本人以外が登録したスケジュール且つ、ショートメール通知する場合
            if (getSmlSendKbn(admConf, priConf, 1)
                    && schMdl.getScdUsrSid() != schMdl.getScdEuid()) {
                sidList.add(new Integer(userSid));
                umodel = udao.select(userSid);
                userName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            }
        }
        //送信先がない場合は終了
        if (sidList.size() < 1) {
            return;
        }

        priConf.getSccSmailGroup();
        try {

            //登録ユーザ名
            umodel = udao.select(addUserSid);
            String addUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            //タイトル
            String thtitle = schMdl.getScdTitle();
            //内容
            String body = schMdl.getScdValue();
            //備考
            String biko = schMdl.getScdBiko();
            StringBuilder dateBuf = new StringBuilder();
            for (String str : dateList) {
                dateBuf.append(str);
                dateBuf.append("\r\n");
            }
            //開始日時
            UDate frDate = schMdl.getScdFrDate();
            //終了日時
            UDate toDate = schMdl.getScdToDate();
            String time = UDateUtil.getSeparateHMJ(frDate, reqMdl__) + " - "
            + UDateUtil.getSeparateHMJ(toDate, reqMdl__);

            //登録日時
            UDate aDate = schMdl.getScdEdate();
            String adate = UDateUtil.getSlashYYMD(aDate) + " "
            + UDateUtil.getSeparateHMS(aDate);
            //本文
            String tmpPath = getSmlTemplateExFilePath(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("ADATE", adate);
            map.put("UNAME", addUserName);
            map.put("NAME", userName);
            map.put("TITLE", thtitle);
            map.put("SETDATE", dateBuf.toString());
            map.put("SETTIME", time);
            map.put("BODY", body);
            map.put("BIKO", biko);

            String bodyml = StringUtil.merge(tmpBody, map);
            String omit = gsMsg.getMessage("cmn.mail.omit");

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                bodyml = omit + "\r\n\r\n" + bodyml;
                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
            }

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //スケジュール
            String textSchedule = gsMsg.getMessage("schedule.108");
            //登録通知
            String textAdd = gsMsg.getMessage("schedule.130");
            String title = "[GS " + textSchedule + "] " + textAdd  + " " + thtitle;
            title = StringUtil.trimRengeString(title,
                    GSConstCommon.MAX_LENGTH_SMLTITLE);
            smlModel.setSendTitle(title);

            //本文
            smlModel.setSendBody(bodyml);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

            //メール送信処理開始
            SmlSender sender = new SmlSender(
                    con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl__);
            sender.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log__.error("ショートメール送信に失敗しました。");
            throw e;
        }
    }

    /**
     * <br>[機  能] ショートメールでスケジュール登録通知を行う。
     * <br>[解  説] 拡張登録用
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param schMdl スケジュール内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param dateList 登録日付リスト
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param url リンク先URL
     * @throws Exception 実行例外
     */
    public void sendPlgSmailEx(
        MlCountMtController cntCon,
        SchDataModel schMdl,
        String appRootPath,
        PluginConfig pluginConfig,
        ArrayList<String> dateList,
        boolean smailPluginUseFlg,
        String url) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (!smailPluginUseFlg) {
            //ショートメール機能が無効の場合リターン
            return;
        }
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel umodel = null;
        String userName = "";
        // 被登録者
        int userSid = schMdl.getScdUsrSid();
        // 登録者
        int addUserSid = schMdl.getScdEuid();
        SchPriConfModel priConf = null;
        SchAdmConfModel admConf = getAdmConfModel(con__);

        //送信するユーザSIDリストを作成
        List<Integer> sidList = new ArrayList<Integer>();
        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
            //グループスケジュールの場合
            //所属しているユーザ一覧を取得
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> belongList
                = userBiz.getBelongUserList(con__, userSid, null);

            for (CmnUsrmInfModel usMdl : belongList) {
                priConf = getSchPriConfModel(con__, usMdl.getUsrSid());
                if (getSmlSendKbn(admConf, priConf, 0)) {
                    //登録者以外に通知
                    if (usMdl.getUsrSid() != reqMdl__.getSmodel().getUsrsid()) {
                        sidList.add(new Integer(usMdl.getUsrSid()));
                    }
                }
            }
            GroupDao gpDao = new GroupDao(con__);
            CmnGroupmModel gpMdl = gpDao.getGroup(userSid);
            userName = gpMdl.getGrpName();
        } else {
            //ユーザスケジュールの場合
            priConf = getSchPriConfModel(con__, userSid);
            //本人以外が登録したスケジュール且つ、ショートメール通知する場合
            if (getSmlSendKbn(admConf, priConf, 1)
                    && schMdl.getScdUsrSid() != schMdl.getScdEuid()) {
                sidList.add(new Integer(userSid));
                umodel = udao.select(userSid);
                userName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            }
        }
        //送信先がない場合は終了
        if (sidList.size() < 1) {
            return;
        }

        priConf.getSccSmailGroup();
        try {

            //登録ユーザ名
            umodel = udao.select(addUserSid);
            String addUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            //タイトル
            String thtitle = schMdl.getScdTitle();
            //内容
            String body = schMdl.getScdValue();
            //備考
            String biko = schMdl.getScdBiko();
            StringBuilder dateBuf = new StringBuilder();
            for (String str : dateList) {
                dateBuf.append(str);
                dateBuf.append("\r\n");
            }
            //開始日時
            UDate frDate = schMdl.getScdFrDate();
            //終了日時
            UDate toDate = schMdl.getScdToDate();
            String time = UDateUtil.getSeparateHMJ(frDate, reqMdl__) + " - "
            + UDateUtil.getSeparateHMJ(toDate, reqMdl__);

            //登録日時
            UDate aDate = schMdl.getScdEdate();
            String adate = UDateUtil.getSlashYYMD(aDate) + " "
            + UDateUtil.getSeparateHMS(aDate);
            //本文
            String tmpPath = getSmlTemplateExFilePathPlg(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("ADATE", adate);
            map.put("UNAME", addUserName);
            map.put("NAME", userName);
            map.put("TITLE", thtitle);
            map.put("SETDATE", dateBuf.toString());
            map.put("SETTIME", time);
            map.put("BODY", body);
            map.put("BIKO", biko);
            map.put("URL", url);

            String bodyml = StringUtil.merge(tmpBody, map);
            String omit = gsMsg.getMessage("cmn.mail.omit");

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                bodyml = omit + "\r\n\r\n" + bodyml;
                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
            }

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //スケジュール
            String textSchedule = gsMsg.getMessage("schedule.108");
            //登録通知
            String textAdd = gsMsg.getMessage("schedule.130");
            String title = "[GS " + textSchedule + "] " + textAdd + " " + thtitle;
            title = StringUtil.trimRengeString(title,
                    GSConstCommon.MAX_LENGTH_SMLTITLE);
            smlModel.setSendTitle(title);

            //本文
            smlModel.setSendBody(bodyml);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

            //メール送信処理開始
            SmlSender sender = new SmlSender(
                    con__, cntCon, smlModel, pluginConfig, appRootPath, reqMdl__);
            sender.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log__.error("ショートメール送信に失敗しました。");
            throw e;
        }
    }

    /**
     * <br>[機  能] スケジュールモデルから表示用のタイトルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param schModel SchDataModel
     * @param sessionUsrSid ユーザSID
     * @return 表示用スケジュールタイトル
     * @throws SQLException SQL実行例外
     */
    public String getDspTitle(
            SchDataModel schModel,
            int sessionUsrSid) throws SQLException {
        String title = "";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textYoteiari = gsMsg.getMessage("schedule.src.9");

        //自分の予定の場合
        if (schModel.getScdUsrSid() == sessionUsrSid) {
            return schModel.getScdTitle();
        }

        //公開区分
        if (schModel.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
            //予定あり
            title = textYoteiari;
        } else if (schModel.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
            //非公開

        } else if (schModel.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP) {
            //所属グループのみ公開

            //表示グループに所属しているか判定
            GroupBiz gpBiz = new GroupBiz();
            boolean belongFlg
                = gpBiz.isBothBelongGroup(sessionUsrSid, schModel.getScdUsrSid(), con__);
            if (belongFlg) {
                //所属グループのみ公開 所属している
                title = schModel.getScdTitle();
            } else {
                //所属グループのみ公開 未所属の場合は予定ありを表示
                title = textYoteiari;
            }
        } else {
            //公開
            title = schModel.getScdTitle();
        }
        return title;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/schedule/smail/koushin_tsuuchi.txt");
        return ret;
    }
    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メール(拡張用)のテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateExFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/schedule/smail/koushin_tsuuchi_ex.txt");
        return ret;
    }

    /**
     * <br>[機  能] スケジュールプラグインアプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathPlg(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/schedule/smail/koushin_tsuuchi_plg.txt");
        return ret;
    }
    /**
     * <br>[機  能] スケジュールプラグインアプリケーションのルートパスから更新通知メール(拡張用)のテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateExFilePathPlg(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/schedule/smail/koushin_tsuuchi_ex_plg.txt");
        return ret;
    }

    /**
     * <br>[機  能] スケジュールプラグインアプリケーションのルートパスから出欠依頼通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathAttend(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/schedule/smail/shukketsu_irai_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] スケジュールプラグインアプリケーションのルートパスから出欠依頼通知メール（再通知）のテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathAttendEdit(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/schedule/smail/shukketsu_edit_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] スケジュールプラグインアプリケーションのルートパスから出欠確認完了通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathAttendComp(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/schedule/smail/shukketsu_comp_tsuuchi.txt");
        return ret;
    }

    /**
     * スケジュール全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value) {
        outPutLog(map, req, res, opCode, level, value, null);
    }

    /**
     * スケジュール全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param logCode ログコード
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value,
            String logCode) {

        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }
        GsMessage gsMsg = new GsMessage();
        /** メッセージ スケジュール **/
        String schedule = gsMsg.getMessage(req, "schedule.108");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstSchedule.PLUGIN_ID_SCHEDULE);
        logMdl.setLogPluginName(schedule);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType()));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(CommonBiz.getRemoteAddr(req));
        logMdl.setVerVersion(GSConst.VERSION);
        if (logCode != null) {
            logMdl.setLogCode(logCode);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = GroupSession.getResourceManager().getDomain(req);
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * スケジュール全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param logCode ログコード
     * @param dspName 画面名
     */
    public void outPutLogNoDspName(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value,
            String logCode,
            String dspName) {

        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }
        GsMessage gsMsg = new GsMessage();
        /** メッセージ スケジュール **/
        String schedule = gsMsg.getMessage(req, "schedule.108");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstSchedule.PLUGIN_ID_SCHEDULE);
        logMdl.setLogPluginName(schedule);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(dspName);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(CommonBiz.getRemoteAddr(req));
        logMdl.setVerVersion(GSConst.VERSION);
        if (logCode != null) {
            logMdl.setLogCode(logCode);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = GroupSession.getResourceManager().getDomain(req);
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * スケジュールＡＰＩ全般全般のログ出力を行う
     * @param req リクエスト
     * @param con コネクション
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutApiLog(
            RequestModel req,
            Connection con,
            int usid,
            String pgId,
            String opCode,
            String level,
            String value) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ スケジュール **/
        String schedule = gsMsg.getMessage("schedule.108");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstSchedule.PLUGIN_ID_SCHEDULE);
        logMdl.setLogPluginName(schedule);
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(getPgName(pgId));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(req.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl__.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * 想定外エラーの原因調査に必要なログの出力を行う
     * @param dspMod 画面遷移元
     * @param sCmd cmd
     * @param selectUsrSid 選択ユーザSID
     * @param selectUsrKbn 表示区分
     * @param selectDate スケジュール登録日付
     * @param req リクエスト
     */
    public void outPutUnexpectedErrorLog(String dspMod, String sCmd,
            String selectUsrSid, String selectUsrKbn, String selectDate,
            HttpServletRequest req) {

        StringBuffer buf = new StringBuffer();
        buf.append("スケジュール登録画面の表示に失敗しました。");
        buf.append("\n");
        buf.append("        SessionUsrSid : ");
        buf.append(reqMdl__.getSmodel().getUsrsid());
        buf.append("\n");
        buf.append("        Referer : ");
        buf.append(reqMdl__.getReferer());
        buf.append("\n");
        buf.append("        RequestURL : ");
        buf.append(reqMdl__.getRequestURL());
        buf.append("\n");
        buf.append("        dspMod : ");
        buf.append(dspMod);
        buf.append("\n");
        buf.append("        CMD : ");
        buf.append(NullDefault.getString(req.getParameter("CMD"), ""));
        buf.append("\n");
        buf.append("        cmd : ");
        buf.append(sCmd);
        buf.append("\n");
        buf.append("        selectUsrSid : ");
        buf.append(selectUsrSid);
        buf.append("\n");
        buf.append("        selectUsrKbn : ");
        buf.append(selectUsrKbn);
        buf.append("\n");
        buf.append("        selectDate : ");
        buf.append(selectDate);
        buf.append("\n");
        buf.append("        ## リクエストヘッダ : ");
        buf.append("\n");
        Enumeration<String> headerEnum = req.getHeaderNames();
        String headerName = null;
        while (headerEnum.hasMoreElements()) {
            headerName = headerEnum.nextElement();
            buf.append("          " + headerName
                    + " : " + req.getHeader(headerName));
            buf.append("\n");
        }
        buf.append("        ## パラメータ : ");
        buf.append("\n");
        Enumeration<String> paramEnum = req.getParameterNames();
        String paramName = null;
        while (paramEnum.hasMoreElements()) {
            paramName = paramEnum.nextElement();
            buf.append("          " + paramName
                    + " : " + req.getParameter(paramName));
            buf.append("\n");
        }
        log__.error(buf.toString());
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getPgName(String id) {
        String ret = new String();
        if (StringUtil.isNullZeroString(id)) {
            return ret;
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.sch.sch010.Sch010Action")) {
            return gsMsg.getMessage("schedule.sch010.1");
        }
        if (id.equals("jp.groupsession.v2.sch.sch020.Sch020Action")) {
            return gsMsg.getMessage("schedule.sch020.1");
        }
        if (id.equals("jp.groupsession.v2.sch.sch030.Sch030Action")) {
            return gsMsg.getMessage("schedule.sch030.1");
        }
        if (id.equals("jp.groupsession.v2.sch.sch040.Sch040Action")) {
            return gsMsg.getMessage("schedule.151");
        }
        if (id.equals("jp.groupsession.v2.sch.sch040kn.Sch040knAction")) {
            return gsMsg.getMessage("schedule.sch040kn.1");
        }
        if (id.equals("jp.groupsession.v2.sch.sch041kn.Sch041knAction")) {
            return gsMsg.getMessage("schedule.152");
        }

        if (id.equals("jp.groupsession.v2.sch.sch081.Sch081Action")) {
            return gsMsg.getMessage("cmn.admin.setting") + " "
                           + gsMsg.getMessage("cmn.preferences");
        }
        if (id.equals("jp.groupsession.v2.sch.sch082.Sch082Action")) {
            return gsMsg.getMessage("schedule.154");
        }
        if (id.equals("jp.groupsession.v2.sch.sch083.Sch083Action")) {
            return gsMsg.getMessage("schedule.155");
        }
        if (id.equals("jp.groupsession.v2.sch.sch084.Sch084Action")) {
            return gsMsg.getMessage("schedule.156");
        }
        if (id.equals("jp.groupsession.v2.sch.sch084kn.Sch084knAction")) {
            return gsMsg.getMessage("schedule.157");
        }
        if (id.equals("jp.groupsession.v2.sch.sch085.Sch085Action")) {
            return gsMsg.getMessage("cmn.admin.setting") + " "
                        + gsMsg.getMessage("cmn.display.settings");
        }

        if (id.equals("jp.groupsession.v2.sch.sch091.Sch091Action")) {
            return gsMsg.getMessage("cmn.preferences2") + " "
                        + gsMsg.getMessage("cmn.default.setting");
        }
        if (id.equals("jp.groupsession.v2.sch.sch092.Sch092Action")) {
            return gsMsg.getMessage("cmn.preferences2") + " "
                        + gsMsg.getMessage("cmn.display.settings");
        }
        if (id.equals("jp.groupsession.v2.sch.sch095.Sch095Action")) {
            return gsMsg.getMessage("schedule.163");
        }

        if (id.equals("jp.groupsession.v2.sch.sch100.Sch100Action")) {
            return gsMsg.getMessage("schedule.164");
        }
        if (id.equals("jp.groupsession.v2.sch.sch110.Sch110Action")) {
            return gsMsg.getMessage("schedule.165");
        }
        if (id.equals("jp.groupsession.v2.sch.sch110kn.Sch110knAction")) {
            return gsMsg.getMessage("schedule.166");
        }

        if (id.equals("jp.groupsession.v2.api.schedule.search.ApiSchSearchAction")) {
            return gsMsg.getMessage("schedule.167");
        }

        if (id.equals("jp.groupsession.v2.sch.restapi.entities.SchEntitiesAction")) {
            return gsMsg.getMessage("schedule.176");
        }

        return ret;
    }
    /**
     * <br>[機  能] スケジュールの初期表示で表示する画面のURLを取得する。
     * <br>[解  説] DBに登録された個人設定情報を取得しそのURLを返す。
     * <br>
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return 遷移先URL
     * @throws SQLException SQL実行エラー
     */
    public String getDispDefaultUrl(Connection con, int usrSid) throws SQLException {

        String url = null;
        SchPriConfModel pconf = getSchPriConfModel(con, usrSid);

        if (pconf != null) {
            int dsp = pconf.getSccDefDsp();
            if (dsp == GSConstSchedule.DSP_DAY) {
                url = GSConstSchedule.DSP_DAY_URL;
            } else if (dsp == GSConstSchedule.DSP_WEEK) {
                url = GSConstSchedule.DSP_WEEK_URL;
            } else if (dsp == GSConstSchedule.DSP_MONTH) {
                url = GSConstSchedule.DSP_MONTH_URL;
            } else if (dsp == GSConstSchedule.DSP_PRI_WEEK) {
                url = GSConstSchedule.DSP_PRI_WEEK_URL;
            }
        }
        return url;
    }

    /**
     * <br>[機  能] スケジュール 初期値 時間を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canTimeInitConf(Connection con) throws SQLException {
        return canTimeInitConf(getAdmConfModel(con));
    }

    /**
     * <br>[機  能] スケジュール 初期値 時間を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf スケジュール 管理者設定
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canTimeInitConf(SchAdmConfModel admConf) throws SQLException {
        return admConf.getSadIniTimeStype()
                    == GSConstSchedule.SAD_INIEDIT_STYPE_USER;
    }

    /**
     * <br>[機  能] スケジュール 初期値 編集権限を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditInitConf(Connection con) throws SQLException {
        return canEditInitConf(getAdmConfModel(con));
    }

    /**
     * <br>[機  能] スケジュール 初期値 編集権限を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf スケジュール 管理者設定
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditInitConf(SchAdmConfModel admConf) throws SQLException {
        return admConf.getSadIniEditStype()
                    == GSConstSchedule.SAD_INIEDIT_STYPE_USER;
    }

    /**
     * <br>[機  能] スケジュール 初期値 公開区分を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canPubInitConf(Connection con) throws SQLException {
        return canPubInitConf(getAdmConfModel(con));
    }


    /**
     * <br>[機  能] スケジュール 初期値 公開区分を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf スケジュール 管理者設定
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canPubInitConf(SchAdmConfModel admConf) throws SQLException {
        return admConf.getSadInitPublicStype()
                    == GSConstSchedule.SAD_INIPUBLIC_STYPE_USER;
    }

    /**
     * <br>[機  能] スケジュール 初期値 同時編集を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canSameInitConf(Connection con) throws SQLException {
        return canSameInitConf(getAdmConfModel(con));
    }


    /**
     * <br>[機  能] スケジュール 初期値 同時編集を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf スケジュール 管理者設定
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canSameInitConf(SchAdmConfModel admConf) throws SQLException {
        return admConf.getSadIniSameStype()
                    == GSConstSchedule.SAD_INISAME_STYPE_USER;
    }

    /**
     * <br>[機  能] 各ユーザが重複登録設定を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditRepertKbn(Connection con) throws SQLException {
        return canEditRepertKbn(getAdmConfModel(con));
    }

    /**
     * <br>[機  能] 各ユーザが重複登録設定を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf スケジュール 管理者設定
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditRepertKbn(SchAdmConfModel admConf) throws SQLException {
        return admConf.getSadRepeatStype()
                    == GSConstSchedule.SAD_REPEAT_STYPE_USER;
    }

    /**
     * <br>[機  能] スケジュール 開始時間の初期値を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param uconf スケジュール個人設定
     * @return スケジュール 開始時間の初期値
     * @throws SQLException SQL実行時例外
     */
    public UDate getInitFrDateAuth(Connection con, SchPriConfModel uconf) throws SQLException {
        UDate frDateAuth = uconf.getSccIniFrDate();

        SchAdmConfModel admConf = getAdmConfModel(con);
        if (!canTimeInitConf(admConf)) {
            frDateAuth.setHour(admConf.getSadIniFrH());
            frDateAuth.setMinute(admConf.getSadIniFrM());
        }

        return frDateAuth;
    }

    /**
     * <br>[機  能] スケジュール 終了時間の初期値を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param uconf スケジュール個人設定
     * @return スケジュール 終了時間の初期値
     * @throws SQLException SQL実行時例外
     */
    public UDate getInitToDateAuth(Connection con, SchPriConfModel uconf) throws SQLException {
        UDate toDateAuth = uconf.getSccIniToDate();

        SchAdmConfModel admConf = getAdmConfModel(con);
        if (!canTimeInitConf(admConf)) {
            toDateAuth.setHour(admConf.getSadIniToH());
            toDateAuth.setMinute(admConf.getSadIniToM());
        }

        return toDateAuth;
    }

    /**
     * <br>[機  能] スケジュール 編集権限の初期値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return スケジュール 編集権限の初期値
     * @throws SQLException SQL実行時例外
     */
    public int getInitEditAuth(Connection con, int userSid) throws SQLException {
        SchPriConfModel uconf = getSchPriConfModel(con, userSid);
        return getInitEditAuth(con, uconf);
    }

    /**
     * <br>[機  能] スケジュール 編集権限の初期値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param uconf スケジュール個人設定
     * @return スケジュール 編集権限の初期値
     * @throws SQLException SQL実行時例外
     */
    public int getInitEditAuth(Connection con, SchPriConfModel uconf) throws SQLException {
        int editAuth = uconf.getSccIniEdit();

        SchAdmConfModel admConf = getAdmConfModel(con);
        if (!canEditInitConf(admConf)) {
            editAuth = admConf.getSadIniEdit();
        }

        return editAuth;
    }

    /**
     * <br>[機  能] スケジュール 公開区分の初期値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param uconf スケジュール個人設定
     * @return スケジュール 編集権限の初期値
     * @throws SQLException SQL実行時例外
     */
    public int getInitPubAuth(Connection con, SchPriConfModel uconf)
            throws SQLException {

        int pubAuth = uconf.getSccIniPublic();

        SchAdmConfModel admConf = getAdmConfModel(con);
        if (!canPubInitConf(admConf)) {
            pubAuth = admConf.getSadIniPublic();
        }

        return pubAuth;
    }

    /**
     * <br>[機  能] スケジュール 同時修正の初期値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param uconf スケジュール個人設定
     * @return スケジュール 同時修正の初期値
     * @throws SQLException SQL実行時例外
     */
    public int getInitSameAuth(Connection con, SchPriConfModel uconf)
            throws SQLException {

        int sameAuth = uconf.getSccIniSame();

        SchAdmConfModel admConf = getAdmConfModel(con);
        if (!canSameInitConf(admConf)) {
            sameAuth = admConf.getSadIniSame();
        }

        return sameAuth;
    }

    /**
     * <br>[機  能] 重複登録区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param uconf スケジュール個人設定
     * @param userSid ユーザSID
     * @return true:重複登録可能 false:重複登録不可
     * @throws SQLException SQL実行時例外
     */
    public SchRepeatKbnModel getRepertKbn(Connection con, SchPriConfModel uconf, int userSid)
    throws SQLException {
        int repertKbn = 0;
        int repertMyKbn = 0;

        SchAdmConfModel admConf = getAdmConfModel(con);
        if (!canEditRepertKbn(admConf)) {
            repertKbn = admConf.getSadRepeatKbn();
            repertMyKbn = admConf.getSadRepeatMyKbn();
        } else {
            if (uconf != null) {
                repertKbn = uconf.getSccRepeatKbn();
                repertMyKbn = uconf.getSccRepeatMyKbn();
            } else {
                SchPriConfModel usrConf = getDefaulPriConfModel(userSid, con);
                repertKbn = usrConf.getSccRepeatKbn();
                repertMyKbn = usrConf.getSccRepeatMyKbn();
            }
        }

        SchRepeatKbnModel model = new SchRepeatKbnModel();
        model.setRepeatKbn(repertKbn);
        model.setRepeatMyKbn(repertMyKbn);

        return model;
    }


    /**
     * <br>[機  能] 追加情報をセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con DBコネクション
     * @param pconfig プラグイン設定情報
     * @param paramMdl パラメータ
     * @return 他プラグインのスケジュールデータ
     * @throws Exception インフォーメーション取得クラスの設定ミスの場合にスロー
     */
    public ArrayList<SchDataModel> getAppendSchData(RequestModel reqMdl, Connection con,
            PluginConfig pconfig, SchAppendDataParam paramMdl) throws Exception {
        log__.debug("START getAppendSchData");

        String [] pifclss = pconfig.getSchAppendSchData();
        SchAppendSchData[] schData = null;
        try {
            schData = __getAppendSchData(pifclss);
        } catch (ClassNotFoundException e) {
            log__.error("クラスが見つかりません。設定を見直してください。", e);
            throw e;
        } catch (IllegalAccessException e) {
            log__.error("クラスの設定が間違っています。設定を見直してください。", e);
            throw e;
        } catch (InstantiationException e) {
            log__.error("クラスの設定が間違っています。設定を見直してください。", e);
            throw e;
        }

        ArrayList<SchAppendDataModel> schDatas = new ArrayList<SchAppendDataModel>();

        for (SchAppendSchData imsgCls : schData) {
            log__.debug(imsgCls);
            List<SchAppendDataModel> plist
                    = imsgCls.getAppendSchData(paramMdl, reqMdl, con);
            if (plist != null) {
                schDatas.addAll(plist);
            }
        }
        //ArrayList<SchAppendDataModel>をArrayList<SchDataModel>に変換
        ArrayList<SchDataModel> schDatasList = __getSimpleSch(schDatas);

        return schDatasList;
    }

    /**
     * <p>他プラグインのスケジュールデータ情報のリストを取得する
     * @param classNames プラグインクラス名
     * @throws ClassNotFoundException 指定されたクラスが存在しない
     * @throws IllegalAccessException 実装クラスのインスタンス生成に失敗
     * @throws InstantiationException 実装クラスのインスタンス生成に失敗
     * @return リスナー
     */
    private SchAppendSchData[] __getAppendSchData(String[] classNames)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        SchAppendSchData[] iclasses = new SchAppendSchData[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            Object obj = ClassUtil.getObject(classNames[i]);
            iclasses[i] = (SchAppendSchData) obj;
        }
        return iclasses;
    }

    /**
     * <p>他プラグインスケジュール情報の変換を行う
     * @param list プラグインクラス名
     * @return schDataList
     */
    private ArrayList<SchDataModel> __getSimpleSch(ArrayList<SchAppendDataModel> list) {
        ArrayList<SchDataModel> schDataList = new ArrayList<SchDataModel>();
        if (!list.isEmpty()) {
            SchDataModel ssm = null;
            for (SchAppendDataModel sdm : list) {
                ssm = new SchDataModel();
                ssm.setScdUsrSid(sdm.getUsrSid());
                ssm.setScdAppendId(sdm.getSchPlgId());
                ssm.setScdAppendUrl(sdm.getSchPlgUrl());
                ssm.setScdFrDate(sdm.getFromDate());
                ssm.setScdTitle(sdm.getTitle());
                ssm.setScdToDate(sdm.getToDate());
                ssm.setScdDaily(sdm.getTimeKbn());
                ssm.setScdValue(sdm.getValueStr());
                ssm.setScdPublic(sdm.getPublic());
                ssm.setScdBgcolor(ssm.getScdBgcolor());
                schDataList.add(ssm);
            }
        }
        return schDataList;
    }

    /**
     * <p>他プラグインスケジュール情報の変換を行う
     * @param color 色
     * @param con Connection
     * @return color
     * @throws SQLException SQL実行時例外
     */
    public int getUserColor(int color, Connection con) throws SQLException {

        if (color == 0) {
            color = GSConstSchedule.BGCOLOR_BLUE;
        }

        if (getAdmConfModel(con).getSadMsgColorKbn()
                == GSConstSchedule.SAD_MSG_NO_ADD
                && color > GSConstSchedule.BGCOLOR_BLACK) {
            color = GSConstSchedule.BGCOLOR_BLUE;
        }

        return color;
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
    public String getEnableSelectGroup(List<SchLabelValueModel> list,
            String nowSel,
            String defSel) {
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

    /**
     * <br>[機  能] 指定したスケジュールデータを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSid スケジュールSID
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canAccessSchedule(Connection con, int scdSid, int sessionUserSid)
    throws SQLException {
        SchDataDao schDao = new SchDataDao(con);
        SchDataModel schData = schDao.getSchData(scdSid);
        if (schData == null) {
            return true;
        }

        return canAccessSchedule(con, schData, sessionUserSid);
    }

    /**
     * <br>[機  能] 指定したスケジュールデータを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param schData スケジュールデータ
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canAccessSchedule(Connection con, SchDataModel schData, int sessionUserSid)
    throws SQLException {
        RelationBetweenScdAndRsvChkBiz scdRsvBiz
            = new RelationBetweenScdAndRsvChkBiz(reqMdl__, con);

        //対象のスケジュールを閲覧可能かを判定する
        int scdUsrSid = schData.getScdUsrSid();
        int scdUsrKbn = schData.getScdUsrKbn();

        return scdRsvBiz.canAccessSchedule(scdUsrSid, scdUsrKbn, sessionUserSid);
    }
    /**
     * <br>[機  能] 指定したスケジュールデータを編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param schData スケジュールデータ
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canRegistSchedule(Connection con, SchDataModel schData, int sessionUserSid)
    throws SQLException {
        RelationBetweenScdAndRsvChkBiz scdRsvBiz
            = new RelationBetweenScdAndRsvChkBiz(reqMdl__, con);

        //対象のスケジュールを閲覧可能かを判定する
        int scdUsrSid = schData.getScdUsrSid();
        int scdUsrKbn = schData.getScdUsrKbn();
        return scdRsvBiz.canRegistSchedule(scdUsrSid, scdUsrKbn, sessionUserSid);
    }

    /**
     * <br>[機  能] 指定されたユーザSIDからアクセス可能なSIDのみ取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param usrSids ユーザSIDリスト
     * @throws SQLException SQL実行時例外
     * @return アクセス可能ユーザSIDリスト
     */
    public ArrayList<Integer> getAccessUserList(Connection con, int sessionUsrSid,
            ArrayList<Integer> usrSids) throws SQLException {
        SchDao scheduleDao = new SchDao(con);
        List<Integer> notAccessUserList
        = scheduleDao.getNotAccessUserList(sessionUsrSid);

        ArrayList<Integer> ret = new ArrayList<Integer>();

        for (Integer usid : usrSids) {
            if (notAccessUserList.indexOf(usid) < 0) {
                ret.add(usid);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 時コンボを生成します
     * <br>[解  説]「未設定(-1)」が表示されます
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  時コンボ
     */
    public ArrayList<LabelValueBean> getHourLabel() {
        return getHourLabel(true);
    }


    /**
     * <br>[機  能] 時コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param unSetDspFlg 「未設定」表示フラグ  true:表示する  false:表示しない
     * @return ArrayList (in LabelValueBean)  時コンボ
     */
    public ArrayList<LabelValueBean> getHourLabel(boolean unSetDspFlg) {
        int hour = 0;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        if (unSetDspFlg) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            //未設定
            String textNoSet = gsMsg.getMessage("cmn.notset");
            labelList.add(
                    new LabelValueBean(textNoSet, "-1"));
        }
        for (int i = 0; i < 24; i++) {
            labelList.add(
                    new LabelValueBean(String.valueOf(hour), String.valueOf(hour)));
            hour++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 分コンボを生成します
     * <br>[解  説]「未設定(-1)」が表示されます
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList (in LabelValueBean)  分コンボ
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getMinuteLabel(Connection con) throws SQLException {
        return getMinuteLabel(con, true);
    }

    /**
     * <br>[機  能] 分コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param unSetDspFlg 「未設定」表示フラグ  true:表示する  false:表示しない
     * @return ArrayList (in LabelValueBean)  分コンボ
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getMinuteLabel(
            Connection con, boolean unSetDspFlg) throws SQLException {
        int hourDivCount = getDayScheduleHourMemoriCount(con);
        int min = 0;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        if (unSetDspFlg) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            //未設定
            String textNoSet = gsMsg.getMessage("cmn.notset");
            labelList.add(new LabelValueBean(textNoSet, "-1"));
        }
        int hourMemCount = 60 / hourDivCount;
        for (int i = 0; i < hourDivCount; i++) {
            labelList.add(
                    new LabelValueBean(
                            StringUtil.toDecFormat(min, "00"), String.valueOf(min)));
            min = min + hourMemCount;
        }
        return labelList;
    }
    /**
     * <br>[機  能] グループSidから所属ユーザのスケジュール個人設定情報を取得
     * <br>[解  説] <usrSid, 個人設定情報>のMapで返す
     * <br>[備  考]
     * @param grpSid
     * @return priMap
     * @throws SQLException
     */
    public Map<Integer, SchPriPushModel> getGroupPriConf(int grpSid) throws SQLException {
        SchPriConfDao priDao = new SchPriConfDao(con__);
        Map<Integer, SchPriPushModel> priMap = priDao.getPushDataGroup(grpSid);

        return priMap;
    }

    /**
     * <br>[機  能] ユーザSidからスケジュール個人設定情報をか
     * <br>[解  説]　<usrSid, 個人設定情報>のMapで返す
     * <br>[備  考]
     * @param users
     * @return priMap
     * @throws SQLException
     */
    public Map<Integer, SchPriPushModel> getUserPriConf(String[] users) throws SQLException {
        SchPriConfDao priDao = new SchPriConfDao(con__);
        Map<Integer, SchPriPushModel> priMap = priDao.getPushDataUser(users);

        return priMap;
    }

    /**
     * <br>[機  能] 個人設定に設定されているリマインダー通知時間から実際に通知を行う時刻を計算して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate スケジュール開始時間
     * @param reminder リマインダー通知時間
     * @return 実際に通知を行う時刻
     */
    public UDate getReminderDate(UDate frDate, int reminder) {

        switch (reminder) {
            case GSConstSchedule.REMINDER_TIME_FIVE_MINUTES:
                frDate.addMinute(-5);
                break;
            case GSConstSchedule.REMINDER_TIME_TEN_MINUTES:
                frDate.addMinute(-10);
                break;
            case GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES:
                frDate.addMinute(-15);
                break;
            case GSConstSchedule.REMINDER_TIME_THIRTY_MINUTES:
                frDate.addMinute(-30);
                break;
            case GSConstSchedule.REMINDER_TIME_ONE_HOUR:
                frDate.addHour(-1);
                break;
            case GSConstSchedule.REMINDER_TIME_TWO_HOUR:
                frDate.addHour(-2);
                break;
            case GSConstSchedule.REMINDER_TIME_THREE_HOUR:
                frDate.addHour(-3);
                break;
            case GSConstSchedule.REMINDER_TIME_SIX_HOUR:
                frDate.addHour(-6);
                break;
            case GSConstSchedule.REMINDER_TIME_TWELVE_HOUR:
                frDate.addHour(-12);
                break;
            case GSConstSchedule.REMINDER_TIME_ONE_DAY:
                frDate.addDay(-1);
                break;
            case GSConstSchedule.REMINDER_TIME_TWO_DAY:
                frDate.addDay(-2);
                break;
            case GSConstSchedule.REMINDER_TIME_ONE_WEEK:
                frDate.addDay(-7);
                break;
            default:
        }

        return frDate;
    }

    /**
     * <br>[機  能] スケジュール通知リストに設定するかをチェック
     * <br>[解  説] 現在時刻から現在時間 + 8日より前の予約情報である
     *              アプリまたはPCを通知対象にしている
     *              リマインダー通知時間を設定ししている
     *              ユーザSIDがadmin, システムメールではない
     * 上記3点に当てはまる場合のみ登録を行う
     * <br>[備  考]
     * @param frDate
     * @param priMdl
     * @return true:登録する false:登録しない
     */
    public boolean insertPushListCheck(UDate frDate, SchPriPushModel priMdl) {
        return __insertCheck(frDate, priMdl);
    }
    /**
     * <br>[機  能] スケジュール通知リストに設定するかをチェック
     * <br>[解  説] 現在時刻から現在時間 + 8日より前の予約情報である
     *              アプリまたはPCを通知対象にしている
     *              リマインダー通知時間を設定ししている
     *              ユーザSIDがadmin, システムメールではない
     * 上記3点に当てはまる場合のみ登録を行う
     * <br>[備  考]
     * @param frDate
     * @param priMdl
     * @return true:登録する false:登録しない
     */
    private boolean __insertCheck(UDate frDate, SchPriPushModel priMdl) {
        UDate now = new UDate();
        UDate after = now.cloneUDate();
        after.addDay(8);
        //現在時刻から現在時間 + 8日より後の場合
        if (now.compareDateYMDHM(frDate) != UDate.LARGE
                || after.compareDateYMDHM(frDate) != UDate.SMALL) {
            return false;
        }
        //リマインダー通知時間が設定しない場合
        if (priMdl.getSccReminder() == GSConstSchedule.REMINDER_TIME_NO) {
            return false;
        }

        //ユーザSIDが予約済みの場合
        if (priMdl.getUsrSid() <= GSConst.SYSTEM_USER_MAIL) {
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] グループスケジュールプッシュ通知情報の登録
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid
     * @param priMap
     * @param date
     * @param remindGroup
     * @throws SQLException
     */
    public void insertPushInfGroup(
            int scdSid, Map<Integer, SchPriPushModel> priMap, UDate date,
            int remindGroup) throws SQLException {
        if (remindGroup == GSConstSchedule.REMINDER_USE_YES) {
            SchPushListDao pushDao = new SchPushListDao(con__);
            for (Map.Entry<Integer, SchPriPushModel> map : priMap.entrySet()) {
                SchPriPushModel priMdl = map.getValue();
                SchPushListModel pushMdl = new SchPushListModel();
                if (__insertCheck(date, priMdl)) {
                    //通知予定リスト登録
                    pushMdl.setScdSid(scdSid);
                    pushMdl.setUsrSid(priMdl.getUsrSid());
                    UDate remDate = getReminderDate(date.cloneUDate(), priMdl.getSccReminder());
                    pushMdl.setSplReminder(remDate);
                    pushMdl.setSplReminderKbn(priMdl.getSccReminder());
                    pushDao.insert(pushMdl);
                }
            }
        }
    }

    /**
     * <br>[機  能] ユーザスケジュールプッシュ通知情報の登録
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param priMdl リマインダー設定値 SchRemindConfWriterによって設定後の値を反映すること
     * @param frDate スケジュール開始日
     * @throws SQLException SQL実行時例外
     */
    public void insertPushInfUser(int scdSid, SchPriPushModel priMdl, UDate frDate)
            throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        SchPushListModel pushMdl = new SchPushListModel();
        if (priMdl == null) {
            return;
        }

        if (__insertCheck(frDate, priMdl)) {
            //通知予定リスト登録
            pushMdl.setScdSid(scdSid);
            pushMdl.setUsrSid(priMdl.getUsrSid());
            UDate remDate = getReminderDate(frDate.cloneUDate(), priMdl.getSccReminder());
            pushMdl.setSplReminder(remDate);
            pushMdl.setSplReminderKbn(priMdl.getSccReminder());
            pushDao.insert(pushMdl);
        }
    }

    /**
     * <br>[機  能] ユーザスケジュールプッシュ通知情報の登録
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュール SchRemindConfWriterによって設定後の値を反映すること
     * @throws SQLException SQL実行時例外
     */
    public void insertPushInfUser(SchDataModel schMdl) throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        SchPushListModel pushMdl = new SchPushListModel();
        SchPriPushModel priMdl = new SchPriPushModel();
        priMdl.setSccReminder(schMdl.getScdReminder());
        priMdl.setUsrSid(schMdl.getScdUsrSid());
        UDate frDate = schMdl.getScdFrDate().cloneUDate();
        if (__insertCheck(frDate, priMdl)) {
            //通知予定リスト登録
            pushMdl.setScdSid(schMdl.getScdSid());
            pushMdl.setUsrSid(priMdl.getUsrSid());
            UDate remDate = getReminderDate(frDate, priMdl.getSccReminder());
            pushMdl.setSplReminder(remDate);
            pushMdl.setSplReminderKbn(priMdl.getSccReminder());
            pushDao.insert(pushMdl);
        }
    }
    /**
     * <br>[機  能] プッシュ通知情報の削除(個人スケジュール削除時)
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSIDリスト
     * @param delScdUsrSid 削除ユーザリスト
     * @throws SQLException
     */
    public void deleteUserPushList(List<Integer> scdSidList,
            String[] delScdUsrSid) throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        pushDao.deletePushData(scdSidList, delScdUsrSid);
    }
    /**
     * <br>[機  能] プッシュ通知情報の削除(個人スケジュール削除時)
     * <br>[解  説]
     * <br>[備  考]
     * @param scdRsSid 施設予約SID
     * @param delScdUsrSid 削除ユーザリスト
     * @throws SQLException
     */
    public void deleteUserPushList(int scdRsSid,
            String[] delScdUsrSid) throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        pushDao.deletePushData(scdRsSid, delScdUsrSid);
    }


    /**
     * <br>[機  能] プッシュ通知情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @throws SQLException
     */
    public void deletePushList(int scdSid) throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        pushDao.delete(scdSid);
    }

    /**
     * <br>[機  能] プッシュ通知情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSIDリスト
     * @throws SQLException
     */
    public void deletePushList(List<Integer> scdSidList) throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        pushDao.delete(scdSidList);
    }

    /**
     * <br>[機  能] プッシュ通知情報の削除(グループスケジュール削除時)
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSIDリスト
     * @param grpSid グループSID
     * @throws SQLException
     */
    public void deleteGroupPushList(List<Integer> scdSidList,
            int grpSid) throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        pushDao.deletePushGroupData(scdSidList, grpSid);
    }


    /**
     * <br>[機  能] プッシュ通知情報の編集(ユーザスケジュール編集時)
     * <br>[解  説]
     * <br>[備  考]
     * @param splMdl 通知予定リスト
     * @throws SQLException
     */
    public void updateUserPushData(SchPushListModel splMdl) throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        //削除
        pushDao.delete(splMdl.getScdSid(), splMdl.getUsrSid());
        //登録
        if (splMdl.getUsrSid() > GSConst.SYSTEM_USER_MAIL) {
            pushDao.insert(splMdl);
        }

    }

    /**
     * <br>[機  能] 施設予約からプッシュ通知情報の編集(グループスケジュール編集時)
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param grpSid 削除ユーザリスト
     * @param frDate 開始日
     * @throws SQLException
     */
    public void updateGroupPushData(int scdSid,
            int grpSid, UDate frDate) throws SQLException {
        SchPushListDao pushDao = new SchPushListDao(con__);
        List<Integer> sidList = new ArrayList<Integer>();
        sidList.add(scdSid);
        //削除前に情報を取得
        Map<Integer, SchPushListModel> splMap = pushDao.selectGroupData(sidList, grpSid);
        //削除
        deleteGroupPushList(sidList, grpSid);
        //登録
        for (Map.Entry<Integer, SchPushListModel> map : splMap.entrySet()) {
            SchPushListModel priMdl = map.getValue();
            UDate remDate = getReminderDate(frDate,  priMdl.getUsrSid());
            priMdl.setSplReminder(remDate);
        }
    }

    /**
     * <br>[機  能] 施設予約からプッシュ通知情報の編集(グループスケジュール編集時)
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSIDリスト
     * @param grpSid グループSID
     * @param frDate 開始日時
     * @throws SQLException
     */
    public void updateGroupPushList(List<Integer> scdSidList,
            int grpSid, UDate frDate) throws SQLException {

        if (scdSidList.size() == 0) {
            return;
        }
        //削除前に情報を取得
        SchPriConfDao spcDao = new SchPriConfDao(con__);
        Map<Integer, SchPriPushModel> priMap = spcDao.getPushDataGroup(grpSid);

        //削除
        deleteGroupPushList(scdSidList, grpSid);

        //登録
        for (int scdSid : scdSidList) {
            insertPushInfGroup(scdSid, priMap, frDate, GSConstSchedule.REMINDER_USE_YES);
        }
    }


    /**
     * <br>[機  能] スケジュール情報を削除(物理削除)します
     * <br>[解  説]
     * <br>[備  考]
     * @param scds 同時登録スケジュールSID
     * @return 削除レコード件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteSchedule(ArrayList<Integer> scds)
    throws SQLException {

        int cnt = 0;
        SchDataDao scdDao = new SchDataDao(con__);
        cnt = scdDao.delete(scds);

        SchBinDao binDao = new SchBinDao(con__);
        binDao.deleteTempFile(scds);

        deleteSchCompany(scds, 0);
        deletePushList(scds);

        SchDataPubDao sdpDao = new SchDataPubDao(con__);
        sdpDao.delete(scds);

        return cnt;
    }

    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSID
     * @param contactFlg コンタクト履歴変更有無
     * @throws SQLException SQL実行時例外
     */
    public void deleteSchCompany(List<Integer> scdSidList, int contactFlg)
    throws SQLException {

        SchCompanyDao companyDao = new SchCompanyDao(con__);
        companyDao.delete(scdSidList);

        SchAddressDao addressDao = new SchAddressDao(con__);
        if (contactFlg == 1) {
            AdrContactDao adcDao = new AdrContactDao(con__);
            adcDao.delete(
                addressDao.select(scdSidList)
                    .stream()
                    .map(SchAddressModel::getAdcSid)
                    .filter(adcSid -> (adcSid > 0))
                    .collect(Collectors.toSet())
                );
        }
        addressDao.delete(scdSidList);
    }

    /**
     * <br>[機  能] スケジュール情報を削除(物理削除)します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @return 削除レコード件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteSchedule(int scdSid) throws SQLException {

        int cnt = 0;
        SchDataDao scdDao = new SchDataDao(con__);
        cnt = scdDao.delete(scdSid);

        List<Integer> scds = new ArrayList<Integer>();
        scds.add(scdSid);

        SchBinDao binDao = new SchBinDao(con__);
        binDao.deleteTempFile(scds);

//        deleteSchCompany(scds, 0);
        deletePushList(scds);

        SchDataPubDao sdpDao = new SchDataPubDao(con__);
        sdpDao.delete(scds);

        return cnt;
    }

    /**
     * <br>[機  能] 年，月，日からyyyy/mm/dd形式の文字列を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     * @param month 月
     * @param day 日
     * @return hh:mm形式の文字列
     */
    public String getDateSlash(String year, String month, String day) {
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtil.toDecFormat(year, "0000"));
        sb.append("/");
        sb.append(StringUtil.toDecFormat(month, "00"));
        sb.append("/");
        sb.append(StringUtil.toDecFormat(day, "00"));

        return sb.toString();
    }

    /**
     * <br>[機  能] 時，分からhh:mm形式の文字列を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param hour 時間
     * @param minute 分
     * @return hh:mm形式の文字列
     */
    public String getTimeColon(String hour, String minute) {
        int iHour = Integer.parseInt(hour);
        int iMin = Integer.parseInt(minute);

        if (iHour < 0 || iMin < 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        sb.append(StringUtil.toDecFormat(hour, "00"));
        sb.append(":");
        sb.append(StringUtil.toDecFormat(minute, "00"));

        return sb.toString();
    }

    /**
     * <br>[機  能] yyyy/mm/dd形式の文字列から年，月，日を格納したリストを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付モデル
     * @return 年，月，日を格納したリスト
     */
    public List<String> getDateList(String dateStr) {

        String[] dateAry = dateStr.split("/");
        return Arrays.asList(dateAry);
    }

    /**
     * <br>[機  能] スケジュールの添付ファイルがダウンロード可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSid スケジュールSID
     * @param binSid バイナリSID
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLFile(
            Connection con, int scdSid, Long binSid) throws SQLException {

        boolean result = false;
        SchBinDao dao = new SchBinDao(con);
        int count = dao.getFileCount(scdSid, binSid);

        if (count > 0) {
            result = true;
        }

        return result;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl, String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(
                reqMdl, GSConstSchedule.PLUGIN_ID_SCHEDULE, dirId);
        return tempDir;
    }

    /**
     * <br>[機  能] 公開アイコン表示フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュール情報(SCH_DATA Model)
     * @param sessionUsrSid セッションユーザSID
     * @param belongGrpMatch スケジュール登録対象ユーザとセッションユーザが同じグループに所属しているか
     *                        true:所属グループが一致、false:一致しない
     * @param publicUserMatch セッションユーザが公開対象ユーザ・グループに含まれるか
     *                         true:含まれる、false:含まれない
     * @return 公開アイコン表示フラグ
     */
    public boolean getPublicIconFlg(SchDataModel schMdl, int sessionUsrSid,
                                    boolean belongGrpMatch,
                                    boolean publicUserMatch) {

        //グループスケジュール時、公開以外の場合はアイコンを表示する。
        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP
                && schMdl.getScdPublic() != GSConstSchedule.DSP_PUBLIC) {
            return true;
        }


        //対象スケジュール情報、セッションユーザが未指定
        //または公開区分 = 公開 の場合、アイコンを表示しない
        if (schMdl == null || sessionUsrSid == 0
            || schMdl.getScdPublic() == GSConstSchedule.DSP_PUBLIC) {
            return false;
        }

        boolean result = false;

        //スケジュール登録者、更新者がセッションユーザと一致する場合、アイコンを表示する
        if (schMdl.getScdAuid() == sessionUsrSid || schMdl.getScdEuid() == sessionUsrSid) {
            result = true;
        //ユーザスケジュール、かつスケジュール登録対象がセッションユーザの場合、アイコンを表示する
        } else if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER
                    && schMdl.getScdUsrSid() == sessionUsrSid) {
            result = true;
        //公開区分 = 所属グループのみ公開、かつセッションユーザがスケジュール登録対象ユーザと同じグループに所属する場合
        //アイコンを表示する
        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP && belongGrpMatch) {
            result = true;
        //公開区分 = 指定ユーザ・グループのみ公開、かつセッションユーザがスケジュール公開対象ユーザ・グループに含まれる場合
        //アイコンを表示する
        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP && publicUserMatch) {
            result = true;
        }

        return result;
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
     * <br>[機  能] スケジュールSIDからスケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param adminConf 管理者設定
     * @param authFilter 権限フィルター 0:すべて 1:閲覧可能 2:編集可能
     * @param con コネクション
     * @return ScheduleSearchModel
     * @throws SQLException SQL実行時例外
     */
    public ScheduleSearchModel getSchData(
            int scdSid,
            SchAdmConfModel adminConf,
            int authFilter,
            Connection con)
                    throws SQLException {

        ScheduleSearchModel scdMdl = null;
        CmnUsrmInfModel uMdl = null;
        try {
            ScheduleSearchDao scdDao = new ScheduleSearchDao(con);
            scdMdl = scdDao.getScheduleData(scdSid, authFilter,
                    reqMdl__.getSmodel().getUsrsid());
            if (scdMdl == null) {
                return null;
            }
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            //登録者
            uMdl = uDao.getUserInfoJtkb(scdMdl.getScdAuid(), -1);
            if (uMdl != null) {
                scdMdl.setScdAuidSei(uMdl.getUsiSei());
                scdMdl.setScdAuidMei(uMdl.getUsiMei());
                scdMdl.setScdAuidJkbn(cuDao.getUserJkbn(scdMdl.getScdAuid()));
                scdMdl.setRegistUsrUkoFlg(uMdl.getUsrUkoFlg());
            }
            //対象ユーザ
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                uMdl = uDao.getUserInfoJtkb(scdMdl.getScdUsrSid(), -1);
                if (uMdl != null) {
                    scdMdl.setScdUsrSei(uMdl.getUsiSei());
                    scdMdl.setScdUsrMei(uMdl.getUsiMei());
                    scdMdl.setScdUsrJkbn(cuDao.getUserJkbn(scdMdl.getScdUsrSid()));
                    scdMdl.setScdUsrUkoFlg(uMdl.getUsrUkoFlg());
                }
            } else {
                scdMdl.setScdUsrSei(getUsrName(scdMdl.getScdUsrSid(), scdMdl.getScdUsrKbn(), con));
            }
        } catch (SQLException e) {
            log__.error("スケジュール情報の取得に失敗" + e);
            throw e;
        }

        return scdMdl;
    }

    /**
     * <br>[機  能] スケジュールSIDから同時登録しているスケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param adminConf 管理者設定
     * @param con コネクション
     * @return ScheduleSearchModel
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<ScheduleSearchModel> getSchDataList(
            int scdSid,
            SchAdmConfModel adminConf,
            Connection con)
                    throws SQLException {

        ArrayList<ScheduleSearchModel> ret = new ArrayList<ScheduleSearchModel>();
        ScheduleSearchModel scdMdl = null;
        try {
            ScheduleSearchDao scdDao = new ScheduleSearchDao(con);
            //同時登録スケジュールSIDを取得
            ArrayList<Integer> scdSidList = scdDao.getScheduleUsrs(
                    scdSid, reqMdl__.getSmodel().getUsrsid(),
                    GSConstSchedule.CRANGE_SHARE_ALL,
                    GSConstSchedule.SSP_AUTHFILTER_EDIT
                    );

            for (Integer sid : scdSidList) {
                scdMdl = getSchData(sid.intValue(), adminConf,
                                    GSConstSchedule.SSP_AUTHFILTER_EDIT, con);
                if (scdMdl != null) {
                    ret.add(scdMdl);
                }
            }

        } catch (SQLException e) {
            log__.error("スケジュール情報の取得に失敗", e);
            throw e;
        }

        return ret;
    }
    /**
     * <br>[機  能] ユーザSIDとユーザ区分からユーザ氏名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分
     * @param con コネクション
     * @return String ユーザ氏名
     * @throws SQLException SQL実行時例外
     */
    public String getUsrName(int usrSid, int usrKbn, Connection con)
            throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String ret = "";
        if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {
            //グループ
            String textGroup = gsMsg.getMessage("cmn.group");
            if (usrSid == GSConstSchedule.SCHEDULE_GROUP) {
                ret = textGroup;
            } else {
                GroupDao grpDao = new GroupDao(con);
                ret = grpDao.getGroup(usrSid).getGrpName();
            }

        } else {
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmInfModel uMdl = uDao.getUserInfoJtkb(usrSid, GSConstUser.USER_JTKBN_ACTIVE);
            ret = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
        }
        return ret;
    }


    /**
     * <br>同時登録スケジュールも含め編集権限があるか判定する
     * @param scdMdl モデル
     * @param adminConf 管理者設定情報
     * @param sessionUsrSid セッションユーザSID
     * @param isAdmin 管理者権限
     * @param con コネクション
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAllEditOk(
            SchDataModel scdMdl,
            SchAdmConfModel adminConf,
            int sessionUsrSid,
            boolean isAdmin,
            Connection con) throws SQLException {
        boolean baseEdit = false;
        if (isAdmin) {
            return true;
        }

        //自分が登録・編集したもの
        if (scdMdl.getScdAuid() == sessionUsrSid
                || scdMdl.getScdEuid() == sessionUsrSid) {
            baseEdit = true;
        }

        //編集元に対する編集権限チェック
        if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_OWN) {
            //被登録者は編集許可
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                if (scdMdl.getScdUsrSid() == sessionUsrSid) {
                    baseEdit = true;
                }
            }
        } else if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_GROUP) {
            GroupBiz gpBiz = new GroupBiz();
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                //自分も所属しているグループメンバーか
                if (gpBiz.isBothBelongGroup(scdMdl.getScdUsrSid(), sessionUsrSid, con)) {
                    baseEdit = true;
                }
            } else {
                //自分が所属しているグループか
                if (gpBiz.isBelongGroup(sessionUsrSid, scdMdl.getScdUsrSid(), con)) {
                    baseEdit = true;
                }
            }
        } else {
            //編集権限未設定
            baseEdit = true;
        }

        ArrayList<ScheduleSearchModel> schDataList = null;
        if (baseEdit) {
            //同時登録スケジュールに対する編集権限チェック
            schDataList = getSchDataList(scdMdl.getScdSid(), adminConf, con);
            for (ScheduleSearchModel mdl : schDataList) {
                if (!isEditOk(mdl, sessionUsrSid, false, con, true)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }
    /**
     * <br>同時登録スケジュールも含め編集権限があるか判定する
     * @param scdSid スケジュールSID
     * @param con コネクション
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAllEditOk(
            int scdSid,
            Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);
        //管理者権限の有無
        if (isAdmin) {
            return true;
        }
        //管理者設定を取得
        SchCommonBiz adminbiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con);

        ScheduleSearchModel scdMdl = getSchData(scdSid, adminConf,
                                                GSConstSchedule.SSP_AUTHFILTER_EDIT, con);
        if (scdMdl == null) {
            return false;
        }

        return isAllEditOk(scdMdl, adminConf, sessionUsrSid, false, con);
    }

    /**
     * <br>スケジュールに対して編集権限があるか判定する
     * @param scdMdl モデル
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param isAdmin 管理者権限
     * @param sameSchFlg 同時登録スケジュール編集フラグ
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditOk(
            SchDataModel scdMdl,
            int sessionUsrSid,
            Boolean isAdmin,
            Connection con,
            boolean sameSchFlg) throws SQLException {

        if (isAdmin) {
            return true;
        }

        //自分が登録・編集したスケジュールの場合は許可
        if (scdMdl.getScdAuid() == sessionUsrSid
                || scdMdl.getScdEuid() == sessionUsrSid) {
            return true;
        }

        //編集権限「本人」
        if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_OWN) {

            //被登録者は編集許可
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {

                if (scdMdl.getScdUsrSid() == sessionUsrSid) {
                    return true;
                }
            }

            //編集権限「所属グループ」
        } else if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_GROUP) {
            GroupBiz gpBiz = new GroupBiz();

            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                //自分も所属しているグループメンバーか
                if (gpBiz.isBothBelongGroup(scdMdl.getScdUsrSid(), sessionUsrSid, con)) {
                    return true;
                }
            } else {
                //自分が所属しているグループか
                if (gpBiz.isBelongGroup(sessionUsrSid, scdMdl.getScdUsrSid(), con)) {
                    return true;
                }
            }

            //制限なしで同時登録スケジュールの場合
        } else if (sameSchFlg) {
            //編集制限なしで同時登録の場合編集可
            return true;

            //同時登録スケジュール以外
        } else {
            //公開範囲をチェック
            if (checkViewOk(scdMdl, sessionUsrSid, con)) {
                return true;
            }
        }

        //上記以外は編集不可
        return false;
    }
    /**
     * <br>スケジュールに対して編集権限があるか判定する
     * @param scdMdl モデル
     * @param sessionUsrSid セッションユーザSID
     * @param isSameGroup スケジュールユーザ所属グループが同じ
     * @param isPubTarget セッションユーザが指定公開先か
     * @param isAdmin 管理者権限
     * @param sameSchFlg 同時登録スケジュール編集フラグ
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditOk(
            SchDataModel scdMdl,
            int sessionUsrSid,
            boolean isSameGroup,
            boolean isPubTarget,
            Boolean isAdmin,
            boolean sameSchFlg)  {
        if (isAdmin) {
            return true;
        }

        //自分が登録・編集したスケジュールの場合は許可
        if (scdMdl.getScdAuid() == sessionUsrSid
                || scdMdl.getScdEuid() == sessionUsrSid) {
            return true;
        }

        //編集権限「本人」
        if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_OWN) {

            //被登録者は編集許可
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {

                if (scdMdl.getScdUsrSid() == sessionUsrSid) {
                    return true;
                }
            }

            //編集権限「所属グループ」
        } else if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_GROUP) {
            if (isSameGroup) {
                return true;
            }
            //制限なしで同時登録スケジュールの場合
        } else if (sameSchFlg) {
            //編集制限なしで同時登録の場合編集可
            return true;

            //同時登録スケジュール以外
        } else {
            //公開範囲をチェック
            if (checkViewOk(scdMdl, sessionUsrSid, isSameGroup, isPubTarget)) {
                return true;
            }
        }

        //上記以外は編集不可
        return false;
    }
    /**
     *
     * <br>[機  能]公開範囲をチェックして閲覧できるか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param scdMdl スケジュールモデル
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return 閲覧権限(false:閲覧不可 true:閲覧可)
     * @throws SQLException SQL実行時例外
     */
    public boolean checkViewOk(SchDataModel scdMdl, int sessionUsrSid,
            Connection con) throws SQLException {

        GroupBiz gpBiz = new GroupBiz();
        if (scdMdl == null) {
            return false;
        }

        //編集権限未設定の場合閲覧権限チェック
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_PUBLIC) {
            return true;
        }

        //非公開、または予定ありの場合
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                || scdMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI
                || scdMdl.getScdPublic() == GSConstSchedule.DSP_TITLE) {

            //登録者本人ならば閲覧可能
            if (scdMdl.getScdAuid() == sessionUsrSid
                    || scdMdl.getScdEuid() == sessionUsrSid) {
                return true;
            }
            //グループスケジュール時に自分が所属しているなら閲覧可能
            if (scdMdl.getScdUsrKbn() != GSConstSchedule.USER_KBN_USER) {
                if (gpBiz.isBelongGroup(sessionUsrSid, scdMdl.getScdUsrSid(), con)) {
                    return true;
                }
            } else if (scdMdl.getScdUsrSid() == sessionUsrSid) {
                //被登録者ならば閲覧可能
                return true;
            }
        }

        //公開範囲が所属グループのみの場合
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP) {

            //登録者本人ならば閲覧可能
            if (scdMdl.getScdAuid() == sessionUsrSid
                    || scdMdl.getScdEuid() == sessionUsrSid) {
                return true;
            }
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                //自分も所属しているなら編集可能
                if (scdMdl.getScdUsrSid() == sessionUsrSid) {
                    //被登録者ならば閲覧可能
                    return true;
                }
                if (gpBiz.isBothBelongGroup(scdMdl.getScdUsrSid(), sessionUsrSid, con)) {
                    return true;
                }
            } else {
                //所属グループならば編集可能
                if (gpBiz.isBelongGroup(sessionUsrSid, scdMdl.getScdUsrSid(), con)) {
                    return true;
                }
            }
        }

        //公開範囲が指定グループ・ユーザのみの場合
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            //登録者本人ならば閲覧可能
            if (scdMdl.getScdAuid() == sessionUsrSid || scdMdl.getScdEuid() == sessionUsrSid) {
                return true;
            }
            //ユーザスケジュール、かつ対象者本人の場合は閲覧可能
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER
            && scdMdl.getScdUsrSid() == sessionUsrSid) {
                return true;
            }
            SchDataPubDao sdpDao = new SchDataPubDao(con);
            return sdpDao.select(scdMdl.getScdSid(), sessionUsrSid);
        }

        return false;
    }
    /**
    *
    * <br>[機  能]公開範囲をチェックして閲覧できるか判定
    * <br>[解  説]
    * <br>[備  考]
    * @param scdMdl スケジュールモデル
    * @param sessionUsrSid セッションユーザSID
     * @param isSameGroup スケジュールユーザ所属グループが同じ
     * @param isPubTarget セッションユーザが指定公開先か
    * @return 閲覧権限(false:閲覧不可 true:閲覧可)
    */
   public boolean checkViewOk(SchDataModel scdMdl, int sessionUsrSid,
           boolean isSameGroup,
           boolean isPubTarget)  {

       GroupBiz gpBiz = new GroupBiz();
       if (scdMdl == null) {
           return false;
       }

       //編集権限未設定の場合閲覧権限チェック
       if (scdMdl.getScdPublic() == GSConstSchedule.DSP_PUBLIC) {
           return true;
       }

       //非公開、または予定ありの場合
       if (scdMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
               || scdMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI
               || scdMdl.getScdPublic() == GSConstSchedule.DSP_TITLE) {

           //登録者本人ならば閲覧可能
           if (scdMdl.getScdAuid() == sessionUsrSid
                   || scdMdl.getScdEuid() == sessionUsrSid) {
               return true;
           }
           //グループスケジュール時に自分が所属しているなら閲覧可能
           if (scdMdl.getScdUsrKbn() != GSConstSchedule.USER_KBN_USER) {
               if (isSameGroup) {
                   return true;
               }
           } else if (scdMdl.getScdUsrSid() == sessionUsrSid) {
               //被登録者ならば閲覧可能
               return true;
           }
       }

       //公開範囲が所属グループのみの場合
       if (scdMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP) {

           //登録者本人ならば閲覧可能
           if (scdMdl.getScdAuid() == sessionUsrSid
                   || scdMdl.getScdEuid() == sessionUsrSid) {
               return true;
           }
           if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
               //自分も所属しているなら編集可能
               if (scdMdl.getScdUsrSid() == sessionUsrSid) {
                   //被登録者ならば閲覧可能
                   return true;
               }
               if (isSameGroup) {
                   return true;
               }
           } else {
               //所属グループならば編集可能
               if (isSameGroup) {
                   return true;
               }
           }
       }

       //公開範囲が指定グループ・ユーザのみの場合
       if (scdMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
           //登録者本人ならば閲覧可能
           if (scdMdl.getScdAuid() == sessionUsrSid || scdMdl.getScdEuid() == sessionUsrSid) {
               return true;
           }
           //ユーザスケジュール、かつ対象者本人の場合は閲覧可能
           if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER
           && scdMdl.getScdUsrSid() == sessionUsrSid) {
               return true;
           }
           if (isPubTarget) {
               return true;
           }
       }

       return false;
   }
    /**
     * <br>[機  能] 重複するスケジュールを取得する
     * <br>[解  説] 簡易登録用
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param selectSid 対象ユーザ/グループSID
     * @param userKbn ユーザ/グループ区分
     * @param frDate 開始年月日
     * @param frTime 開始時分
     * @param toDate 終了年月日
     * @param toTime 終了時分
     * @param mode 1:NG 2:警告を表示
     * @return 警告スケジュールリスト
     * @throws SQLException SQL実行時例外
     */
    public List<SchDataModel> getSchWarningList(int sessionUsrSid, int selectSid, int userKbn,
            String frDate, String frTime, String toDate, String toTime, int mode)
            throws SQLException {
        List<SchDataModel> rptSchList = new ArrayList<SchDataModel>();

        //個人設定を取得する。
        SchPriConfDao priConfDao = new SchPriConfDao(con__);
        SchPriConfModel priModel = priConfDao.select(sessionUsrSid);

        //自分の予定の場合は編集可能フラグ
        SchRepeatKbnModel repertMdl = getRepertKbn(con__, priModel, sessionUsrSid);
        boolean mySchOkFlg = repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                            && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK;

        //複写フラグ
        String copyFlg = GSConstSchedule.NOT_COPY_FLG;

        //ユーザリストを作成
        List<Integer> usrList = new ArrayList<Integer>();

        //ユーザリストに被登録者を含める
        if (!mySchOkFlg || sessionUsrSid != selectSid) {
            usrList.add(selectSid);
        }

        int frYear = Integer.parseInt(frDate.substring(0, 4));
        int frMonth = Integer.parseInt(frDate.substring(5, 7));
        int frDay = Integer.parseInt(frDate.substring(8, 10));

        int frHour = Integer.parseInt(frTime.substring(0, 2));
        int frMin = Integer.parseInt(frTime.substring(3, 5));

        int toYear = Integer.parseInt(toDate.substring(0, 4));
        int toMonth = Integer.parseInt(toDate.substring(5, 7));
        int toDay = Integer.parseInt(toDate.substring(8, 10));


        int toHour = Integer.parseInt(toTime.substring(0, 2));
        int toMin = Integer.parseInt(toTime.substring(3, 5));

        int toSec = GSConstSchedule.DAY_START_SECOND;
        int toMiliSec = GSConstSchedule.DAY_START_MILLISECOND;


        //予約開始
        UDate chkFrDate = new UDate();
        chkFrDate.setDate(frYear, frMonth, frDay);
        chkFrDate.setHour(frHour);
        chkFrDate.setMinute(frMin);
        chkFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        chkFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);

        //予約終了
        UDate chkToDate = new UDate();
        chkToDate.setDate(toYear, toMonth, toDay);
        chkToDate.setHour(toHour);
        chkToDate.setMinute(toMin);
        chkToDate.setSecond(toSec);
        chkToDate.setMilliSecond(toMiliSec);

        //編集スケジュールSID
        int schSid = 0;

        SchDataDao schDao = new SchDataDao(con__);
        int schGrpSid = -1;

        SchAdmConfModel admConf = getAdmConfModel(con__);
        boolean canEditRepeatKbn = canEditRepertKbn(admConf);
        if (mode == GSConstSchedule.SCH_REPEAT_KBN_NG) {
            List<Integer> ngUsrList = null;
            if (canEditRepeatKbn) {
                //重複登録不可にしているユーザリストを取得
                ngUsrList = priConfDao.getUsrSidListRepeatKbn(usrList,
                        GSConstSchedule.SCH_REPEAT_KBN_NG);
            } else {
                if (admConf.getSadRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG) {
                    ngUsrList = new ArrayList<Integer>();
                    ngUsrList.addAll(usrList);
                }
            }
            if (ngUsrList != null && !ngUsrList.isEmpty()) {
                //重複登録しているスケジュール一覧を取得する。
                rptSchList =
                    schDao.getSchData(ngUsrList, schSid, chkFrDate, chkToDate, schGrpSid, copyFlg);
            }

        } else if (mode == GSConstSchedule.SCH_REPEAT_KBN_WARNING) {

            //重複登録警告にしているユーザリストを取得
            List<Integer> warningUsrList = null;
            if (canEditRepeatKbn) {
                warningUsrList = priConfDao.getUsrSidListRepeatKbn(usrList,
                                                            GSConstSchedule.SCH_REPEAT_KBN_WARNING);
            } else {
                warningUsrList = new ArrayList<Integer>();
                if (admConf.getSadRepeatKbn() != GSConstSchedule.SCH_REPEAT_KBN_OK) {
                    warningUsrList.addAll(usrList);
                }
            }

            if (warningUsrList != null && !warningUsrList.isEmpty()) {
                //重複登録しているスケジュール一覧を取得する。
                rptSchList = schDao.getSchData(
                        warningUsrList, schSid, chkFrDate, chkToDate, schGrpSid, copyFlg);
            }
        }

        return rptSchList;
    }

    /**
     *
     * <br>[機  能] セッションユーザが指定したスケジュールに紐づく施設予約の公開区分範囲内か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSid スケジュールSID
     * @param sessionUsrSid セッションユーザSID
     * @return true:公開範囲内 faise:公開範囲外
     * @throws SQLException SQL実行例外
     */
    public boolean checkRsvPublicRange(Connection con, int scdSid, int sessionUsrSid)
    throws SQLException {
        RsvScdOperationDao scdOpeDao = new RsvScdOperationDao(con);

        //スケジュールに紐づく施設予約が存在しない場合、チェックを終了
        RsvScdOperationModel scdBase = scdOpeDao.selectSchMdl(scdSid);
        if (scdBase == null || scdBase.getScdRsSid() <= 0) {
            return true;
        }

        //施設予約情報を取得
        RsvScdOperationModel scdRet = scdOpeDao.selectSchMdlGrpRssid(scdBase.getScdRsSid());
        ArrayList<Integer> rssidArray =
            new ArrayList<Integer>(scdRet.getRssidMap().values());
        if (rssidArray == null || rssidArray.isEmpty()) {
            return true;
        }
        Collections.sort(rssidArray);
        ArrayList<RsvScdOperationModel> rsvArray =
            scdOpeDao.selectRsvList(rssidArray);

        //公開範囲チェック
        for (RsvScdOperationModel rsvOpeMdl : rsvArray) {
            boolean result =
                    isRsvWithinPubilicRange(
                            con, rsvOpeMdl.getRsySid(), sessionUsrSid);
            if (!result) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * <br>[機  能] セッションユーザが施設予約の公開区分範囲内か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rsySid 施設予約SID
     * @param sessionUsrSid セッションユーザSID
     * @return true:公開範囲内 faise:公開範囲外
     * @throws SQLException SQL実行例外
     */
    public boolean isRsvWithinPubilicRange(Connection con,
            int rsySid,
            int sessionUsrSid) throws SQLException {

        boolean result = false;
        //施設予約情報を取得
        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
        RsvSisYrkModel sisetuMdl = schRsvDao.getRsvYrkData(rsySid);
        if (sisetuMdl == null) {
            return false;
        }
        int auid = sisetuMdl.getRsyAuid();
        int euid = sisetuMdl.getRsyEuid();
        int rsyPublic = sisetuMdl.getRsyPublic();

        //同時登録スケジュールを取得
        int scdRsSid = sisetuMdl.getScdRsSid();
        //セッションユーザが登録者あるいは施設管理者か判定
        if (sessionUsrSid == euid || sessionUsrSid == auid
                || isRsvSisGrpAdmin(con, rsySid, sessionUsrSid)) {
            result = true;
        //その他閲覧権限の判定
        } else if (isRsvAbleViewLowAuth(
                con, rsySid, auid, euid, rsyPublic, scdRsSid, sessionUsrSid)) {
            result = true;
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
    public boolean isRsvSisGrpAdmin(Connection con, int rsySid, int userSid)
            throws SQLException {

        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
        int rsdSid = schRsvDao.getSisDataSid(rsySid);

        return  schRsvDao.isGroupAdmin(rsdSid, userSid);
    }

    /**
     *
     * <br>[機  能] 管理者権限など強権限が無い場合の施設予約の閲覧権限取得
     * <br>[解  説]
     * <br>[備  考]
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
    public boolean isRsvAbleViewLowAuth(Connection con,
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
                    ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
                    //スケジュール拡張SID取得
                    int sceSid =
                            schRsvDao.selectSceSid(scdRsSid);

                    //拡張登録時
                    SchExdataDao schExDao = new SchExdataDao(con);
                    if (sceSid > 0) {
                        if (schExDao.isUsingUserFromSceSid(
                                sceSid, sessionUsrSid)) {
                            view = true;
                        }
                    } else {
                        view = schRsvDao.isUsingUserFromRsSid(
                                scdRsSid, sessionUsrSid);
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
                ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
                view = schRsvDao.checkRsvYrkPubTarget(rsySid, sessionUsrSid);
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
     * <br>[機  能] アクセス権限のない施設数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid ユーザSID
     * @param scdSid スケジュールSID
     * @param rsvAdmin 施設予約管理者
     * @return count 施設数
     * @throws SQLException SQLExceptionm
     */
    public int getCanNotEditRsvCount(
            int sessionUsrSid,
            int scdSid,
            boolean rsvAdmin
            ) throws SQLException {
        int count = 0;

        if (rsvAdmin) {
            return count;
        }

        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con__);

        ArrayList<Integer> allRsdList = schRsvDao.getScheduleReserveData(scdSid);
        if (allRsdList == null || allRsdList.size() == 0) {
            return count;
        }

        //施設SIDリストを取得
        ArrayList<Integer> rsdList
            = schRsvDao.getCanEditScheduleReserveData(scdSid, sessionUsrSid);

        if (rsdList.size() == allRsdList.size()) {
            return count;
        }

        for (Integer rsdSid : allRsdList) {
            if (!rsdList.contains(rsdSid)) {
                count++;
            }
        }

        return count;
    }

}

package jp.groupsession.v2.tcd.tcd060;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardUtil;
import jp.groupsession.v2.tcd.dao.TcdTimezoneDao;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneMeiModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneModel;


/**
 * <br>[機  能] タイムカード 管理者設定 時間帯設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd060Biz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd060Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Tcd060ParamModel paramMdl, Connection con)
    throws SQLException {

        // 時間帯SID
        int ttiSid = paramMdl.getTimezoneSid();

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        //DBより設定情報を取得。なければデフォルト値とする。
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel admMdl = tcdBiz.getTcdAdmConfModel(sessionUsrSid, con);

        int useUserCnt = 0;
        //初期表示の場合
        if (paramMdl.getTcd060initFlg() == GSConstTimecard.DSP_INIT) {
            //編集ならDBから値を取得
            if (paramMdl.getTimezoneCmdMode() == GSConst.CMDMODE_EDIT) {
                // 時間帯情報取得
                TcdTimezoneInfoDao infDao = new TcdTimezoneInfoDao(con);
                TcdTimezoneInfoModel infMdl = infDao.select(ttiSid);
                paramMdl.setTcd060Name(infMdl.getTtiName());
                paramMdl.setTcd060Ryaku(infMdl.getTtiRyaku());
                paramMdl.setTcd060UseFlg(infMdl.getTtiUse());
                paramMdl.setTcd060Holiday(infMdl.getTtiHoliday());
                ArrayList<TcdTimezoneMeiModel> tzmList = __getTimeChartMdl(true, con, ttiSid);
                paramMdl.setTcd060TimezoneMeiList(tzmList);

                //使用ユーザ数を確認
                TcdTimezonePriDao ttpDao = new TcdTimezonePriDao(con);
                useUserCnt = ttpDao.selectUseUserCount(ttiSid);

                //使用可不可の表示設定
                if (admMdl.getTacDefTimezone() == ttiSid) {
                    paramMdl.setTtiUseSetFlg(1);
                } else if (useUserCnt > 0) {
                    paramMdl.setTimezoneSid(ttiSid);
                    paramMdl.setTtiUseSetFlg(2);
                } else {
                    paramMdl.setTtiUseSetFlg(0);
                }
            }

            paramMdl.setTcd060FrH(NullDefault.getString(
                    paramMdl.getTcd060FrH(),
                    String.valueOf(GSConstTimecard.DF_IN_TIME)));
            paramMdl.setTcd060FrM(NullDefault.getString(
                    paramMdl.getTcd060FrM(),
                    String.valueOf(GSConstTimecard.DF_IN_MIN)));
            paramMdl.setTcd060ToH(NullDefault.getString(
                    paramMdl.getTcd060ToH(),
                    String.valueOf(GSConstTimecard.DF_OUT_TIME)));
            paramMdl.setTcd060ToM(NullDefault.getString(
                    paramMdl.getTcd060ToM(),
                    String.valueOf(GSConstTimecard.DF_OUT_MIN)));
            paramMdl.setTcd060initFlg(GSConstTimecard.DSP_RE);
        }
        //ラベル設定
        paramMdl.setTcd060ZoneLabel(tcdBiz.getZoneLabelList());
        paramMdl.setTcd060HourLabel(tcdBiz.getHourLabelList(24));
        paramMdl.setTcd060MinuteLabel(tcdBiz.getMinLabelList(admMdl));
    }

    /**
     * <br>[機  能] 時間帯モデルを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param order ソート有無
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<TcdTimezoneMeiModel> __getTimeChartMdl(
            boolean order,
            Connection con,
            int ttiSid) throws SQLException {

        ArrayList<TcdTimezoneModel> tzList = __getTimeZoneModel(order, con, ttiSid);
        TcdTimezoneModel tzMdl = null;
        ArrayList<TcdTimezoneMeiModel> tzmList = new ArrayList<TcdTimezoneMeiModel>();
        TcdTimezoneMeiModel tzmMdl = null;
        if (tzList != null) {
            for (int i = 0; i < tzList.size(); i++) {
                tzMdl = tzList.get(i);
                tzmMdl = new TcdTimezoneMeiModel(tzMdl);
                String dspTime = TimecardUtil.getTimeString(tzmMdl.getFrTime(), tzmMdl.getToTime());
                tzmMdl.setTimeZoneStr(dspTime);
                tzmMdl.setTimeZoneNo(tzMdl.getTtzSid());
                tzmList.add(tzmMdl);
            }
        }
        return tzmList;
    }

    /**
     * <br>[機  能] タイムカード時間帯設定を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param order ソート有無
     * @param con コネクション
     * @return TcdAdmConfModel
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<TcdTimezoneModel> __getTimeZoneModel(
            boolean order, Connection con, int ttiSid) throws SQLException {
        log__.debug("タイムカード管理者設定取得");
        TcdTimezoneDao dao = new TcdTimezoneDao(con);
        ArrayList<TcdTimezoneModel> list = null;
        list = dao.selectOrderTimezone(order, ttiSid);
        return list;
    }

    /**
     * <br>[機  能] 時間帯情報の編集を行う。また、変更が行われなければ、登録を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @param con コネクション
     * @param mode 0:新規　1:編集
     * @throws SQLException SQL実行時例外
     */
    protected void _updateTcdTimezoneInfo(Tcd060ParamModel paramMdl, int usrSid,
            MlCountMtController cntCon, Connection con, int mode) throws SQLException {

        int sid = paramMdl.getTimezoneSid();
        TcdTimezoneInfoDao infDao = new TcdTimezoneInfoDao(con);
        TcdTimezoneInfoModel infMdl = new TcdTimezoneInfoModel();
        UDate now = new UDate();
        int sort = infMdl.getTtiSort();
        infMdl.setTtiSid(sid);
        infMdl.setTtiName(paramMdl.getTcd060Name());
        infMdl.setTtiRyaku(paramMdl.getTcd060Ryaku());
        infMdl.setTtiUse(paramMdl.getTcd060UseFlg());
        infMdl.setTtiSort(sort);
        infMdl.setTtiHoliday(paramMdl.getTcd060Holiday());
        infMdl.setTtiAuid(usrSid);
        infMdl.setTtiAdate(now);
        infMdl.setTtiEuid(usrSid);
        infMdl.setTtiEdate(now);
        int updateCnt = 0;
        if (mode == GSConstTimecard.CMDMODE_EDIT) {
            updateCnt = infDao.update(infMdl);
        }
        if (updateCnt == 0) {
            sid = __insert(infMdl, usrSid, cntCon, con);
        }
        __insertTimeZone(sid, paramMdl, usrSid, cntCon, con, false);
    }

    /**
     * <br>[機  能] 新規登録
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private int __insert(
            TcdTimezoneInfoModel infMdl,
            int usrSid,
            MlCountMtController cntCon,
            Connection con) throws SQLException {
        //時間帯情報SID採番
        int sid = (int) cntCon.getSaibanNumber(SaibanModel.SBNSID_TIMECARD,
                "tcdZoneInf", usrSid);
        TcdTimezoneInfoDao infDao = new TcdTimezoneInfoDao(con);
        TcdTimezoneInfoModel insertMdl = infMdl;
        int sort = infDao.maxSortNumber() + 1;
        insertMdl.setTtiSid(sid);
        insertMdl.setTtiSort(sort);
        infDao.insert(insertMdl);
        return sid;
    }

    /**
     * <br>[機  能] 時間帯を登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param ttiSid 時間帯情報
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @param con コネクション
     * @param doDelete 削除処理判定
     * @throws SQLException SQL実行時例外
     */
    private void __insertTimeZone(
            int ttiSid,
            Tcd060ParamModel paramMdl,
            int usrSid,
            MlCountMtController cntCon,
            Connection con,
            boolean doDelete) throws SQLException {
        TcdTimezoneDao dao = new TcdTimezoneDao(con);
        ArrayList<TcdTimezoneMeiModel> tzmList = paramMdl.getTcd060TimezoneMeiList();
        if (tzmList == null || tzmList.isEmpty()) {
            return;
        }
        dao.delete(ttiSid);
        for (TcdTimezoneMeiModel mdl : tzmList) {
            //時間帯SID採番
            int ttzSid = (int) cntCon.getSaibanNumber(
                    SaibanModel.SBNSID_TIMECARD,
                    SaibanModel.SBNSID_SUB_TCD,
                    usrSid);
            int kbn = NullDefault.getInt(mdl.getTimeZoneKbn(), 1);

            TcdTimezoneModel tzMdl = new TcdTimezoneModel();
            tzMdl.setTtiSid(ttiSid);
            tzMdl.setTtzSid(ttzSid);
            tzMdl.setTtzKbn(kbn);
            tzMdl.setTtzFrtime(mdl.getFrTime());
            tzMdl.setTtzTotime(mdl.getToTime());
            dao.insert(tzMdl);
        }
    }

    /**
     * <br>[機  能] 非同期：時間帯一覧を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return 処理成功判定
     */
    protected boolean _setTimeZoneList(Tcd060ParamModel paramMdl) {
        try {
            // 時間帯明細リスト設定
            ArrayList<TcdTimezoneMeiModel> tzmList = paramMdl.getTcd060TimezoneMeiList();
            int target = paramMdl.getTcd060ZoneNo();

            if (target > -1 && (tzmList.isEmpty())) {
                return false;
            }
            ArrayList<TcdTimezoneMeiModel> editInfList = __setTimeZoneInfList(
                            tzmList,
                            target,
                            Integer.parseInt(paramMdl.getTcd060FrH()),
                            Integer.parseInt(paramMdl.getTcd060FrM()),
                            Integer.parseInt(paramMdl.getTcd060ToH()),
                            Integer.parseInt(paramMdl.getTcd060ToM()),
                            NullDefault.getInt(paramMdl.getTcd060ZoneKbn(), 0));
            paramMdl.setTcd060TimezoneMeiList(editInfList);

        } catch (Exception e) {
            log__.error("時間帯一覧設定失敗");
            throw e;
        }
        return true;
    }
    /**
     * <br>[機  能] 時間帯一覧を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param timeZoneInfList 時間帯一覧情報
     * @param targetNo 登録:-1 編集：編集対象時間帯情報
     * @param frH 開始時
     * @param frM 開始分
     * @param toH 終了時
     * @param toM 終了分
     * @param kbn 時間帯区分
     * @return ArrayList
     */
    private ArrayList<TcdTimezoneMeiModel> __setTimeZoneInfList(
            ArrayList<TcdTimezoneMeiModel> timeZoneInfList,
            int targetNo,
            int frH,
            int frM,
            int toH,
            int toM,
            int kbn) {
        // 代入する時間帯情報を設定
        TcdTimezoneMeiModel insertMdl = new TcdTimezoneMeiModel();
        UDate frDate = new UDate();
        frDate.setZeroHhMmSs();
        UDate toDate = frDate.cloneUDate();
        frDate.setHour(frH);
        frDate.setMinute(frM);
        toDate.setHour(toH);
        toDate.setMinute(toM);
        Time frTime = new Time(frDate.getTime());
        Time toTime = new Time(toDate.getTime());
        insertMdl.setFrTime(frTime);
        insertMdl.setToTime(toTime);
        insertMdl.setTimeZoneKbn(String.valueOf(kbn));
        String insertDspTime =
        TimecardUtil.getTimeString(
                insertMdl.getFrTime(), insertMdl.getToTime());
        insertMdl.setTimeZoneStr(insertDspTime);
        // 編集の場合、編集対象の時間帯設定を削除
        if (targetNo > -1) {
            for (int i = 0; i < timeZoneInfList.size(); i++) {
                TcdTimezoneMeiModel tzmMdl = timeZoneInfList.get(i);
                if (tzmMdl.getTimeZoneNo() == targetNo) {
                    timeZoneInfList.remove(i);
                    break;
                }
            }
        }
        timeZoneInfList.add(insertMdl);
        ArrayList<TcdTimezoneMeiModel> editInfList = __setSortTimeZoneInfList(timeZoneInfList);
        int index = 0;
        for (TcdTimezoneMeiModel tzmMdl : editInfList) {
            tzmMdl.setTimeZoneNo(index++);
        }
        return editInfList;
    }

    /**
     * <br>[機  能] 時間帯一覧設定をチャートモデル用にソートします。
     * <br>[解  説]
     * <br>[備  考] treeMapに格納させることでkeyを基に自動で昇順にソートしてくれる
     * @param timeZoneInfList 時間帯一覧情報
     * @return ArrayList
     */
    private ArrayList<TcdTimezoneMeiModel> __setSortTimeZoneInfList(
            ArrayList<TcdTimezoneMeiModel> timeZoneInfList) {
        if (timeZoneInfList == null || timeZoneInfList.isEmpty()) {
            return new ArrayList<TcdTimezoneMeiModel>();
        }
        // ソート用に開始時間を秒に変換してキーに設定
        // 深夜時間帯用とは別にする（重複する場合があるため）
        Map<Integer, TcdTimezoneMeiModel> infMap =
                new TreeMap<Integer, TcdTimezoneMeiModel>();
        Map<Integer, TcdTimezoneMeiModel> infSinyaMap =
                new TreeMap<Integer, TcdTimezoneMeiModel>();
        for (TcdTimezoneMeiModel mdl : timeZoneInfList) {
            int kbn = NullDefault.getInt(mdl.getTimeZoneKbn()
                    , GSConstTimecard.TIMEZONE_KBN_NORMAL);
            if (kbn == GSConstTimecard.TIMEZONE_KBN_SINYA) {
                infSinyaMap.put(mdl.getFrTime().toLocalTime().toSecondOfDay(), mdl);
            } else {
                infMap.put(mdl.getFrTime().toLocalTime().toSecondOfDay(), mdl);
            }
        }
        ArrayList<TcdTimezoneMeiModel> infList
            = new ArrayList<TcdTimezoneMeiModel>(infMap.values());
        ArrayList<TcdTimezoneMeiModel> infSinyaList
            = new ArrayList<TcdTimezoneMeiModel>(infSinyaMap.values());
        infList.addAll(infSinyaList);
        return infList;
    }

    /**
     * <br>[機  能] 非同期：時間帯一覧を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return true 処理成功
     */
    protected boolean _deleteTimeZoneList(
            Tcd060ParamModel paramMdl) {
        try {
            int target = paramMdl.getTcd060ZoneNo();
            ArrayList<TcdTimezoneMeiModel> tzmList = __deleteTimeZoneInfList(
                    paramMdl.getTcd060TimezoneMeiList(),
                    target);
            paramMdl.setTcd060TimezoneMeiList(tzmList);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 対象の時間帯を削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param timeZoneInfList 時間帯一覧情報
     * @param targetNo 対象時間帯情報
     * @return ArrayList
     */
    private ArrayList<TcdTimezoneMeiModel> __deleteTimeZoneInfList(
            ArrayList<TcdTimezoneMeiModel> timeZoneInfList,
            int targetNo) {

        if (timeZoneInfList == null || timeZoneInfList.isEmpty()) {
            return  new ArrayList<TcdTimezoneMeiModel>();
        }

        // 削除処理
        for (int i = 0; i < timeZoneInfList.size(); i++) {
            TcdTimezoneMeiModel tzmMdl = timeZoneInfList.get(i);
            if (tzmMdl.getTimeZoneNo() == targetNo) {
                timeZoneInfList.remove(i);
                break;
            }
        }

        return timeZoneInfList;

    }
}

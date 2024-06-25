package jp.groupsession.v2.api.timecard.edit;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdDatausedSumDao;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdTcdataDao;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.dao.TimecardDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;

/**
 * <br>[機  能] タイムカード情報の登録・変更を行うWEBAPIビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiTcdEditBiz {

    /**
     * <br>[機  能] タイムカードを登録・更新
     * <br>[解  説]
     * <br>[備  考] 既存データがある場合はupdate 無い場合はinsertします
     * @param paramMdl パラメータ情報
     * @param usModel ユーザモデル
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return TcdTcdataModel 更新対象
     * @throws SQLException SQL実行時例外
     */
    public TcdTcdataModel updateTcdTcdata(ApiTcdEditParamModel paramMdl,
            BaseUserModel usModel, Connection con,
            RequestModel reqMdl)
                    throws SQLException {

        UDate date = new UDate();
        TcdTcdataModel tcdMdl = new TcdTcdataModel();
        int usrSid = usModel.getUsrsid();

        //基本設定取得
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(usrSid, con);
        CmnUsrmDao cumDao = new CmnUsrmDao(con);
        int targetSid = cumDao.selectLoginId(paramMdl.getUserId());

        //各リクエストパラメータの値を設定
        //登録対象者(ユーザSID)
        tcdMdl.setUsrSid(targetSid);

        //日付
        date = UDate.getInstanceStr(paramMdl.getTargetDate());
        date.setZeroHhMmSs();
        tcdMdl.setTcdDate(date);

        //時間帯区分
        TcdTimezoneInfoDao ttiDao = new TcdTimezoneInfoDao(con);
        ArrayList<TcdTimezoneInfoModel> timezoneList = ttiDao.getCanUseTimeZone(targetSid);

        if (__isLock(usModel, con, admConf.getTacLockTimezone())) {

            for (TcdTimezoneInfoModel  model : timezoneList) {
                if (model.getTtiRyaku().equals(paramMdl.getTimezoneType())) {
                    tcdMdl.setTtiSid(model.getTtiSid());
                }
            }
        }

        //打刻始業終業時間
        if (__isLock(usModel, con, admConf.getTacLockStrike())) {
            tcdMdl.setTcdStrikeIntime(null);
            tcdMdl.setTcdStrikeOuttime(null);
            tcdMdl.setTcdLockStrike(GSConstTimecard.LOCK_FLG);
        } else {
            __setStartEndTimes(paramMdl, tcdMdl, date, true,
                    paramMdl.getStampStartTime(), paramMdl.getStampEndTime());

        }

        //始業終業時間
        if (__isLock(usModel, con, admConf.getTacLockFlg())) {
            tcdMdl.setTcdIntime(null);
            tcdMdl.setTcdOuttime(null);
            tcdMdl.setTcdLockTime(GSConstTimecard.LOCK_FLG);
        } else { 
            __setStartEndTimes(paramMdl, tcdMdl, date, false,
                    paramMdl.getStartTime(), paramMdl.getEndTime());
        }

        //備考
        tcdMdl.setTcdBiko(paramMdl.getRemarksText());

        //遅刻早退区分
        if (__isLock(usModel, con, admConf.getTacLockLate())) {
            tcdMdl.setTcdChkkbn(GSConstTimecard.CHK_KBN_UNSELECT);
            tcdMdl.setTcdSoukbn(GSConstTimecard.SOU_KBN_UNSELECT);
            tcdMdl.setTcdLockLate(GSConstTimecard.LOCK_FLG);
        } else {
            if (StringUtil.isNullZeroString(paramMdl.getChikokuFlg())) {
                tcdMdl.setTcdChkkbn(GSConstTimecard.CHK_KBN_UNSELECT);
            } else {
                tcdMdl.setTcdChkkbn(Integer.parseInt(paramMdl.getChikokuFlg()));
            }
            if (StringUtil.isNullZeroString(paramMdl.getSoutaiFlg())) {
                tcdMdl.setTcdSoukbn(GSConstTimecard.SOU_KBN_UNSELECT);
            } else {
                tcdMdl.setTcdSoukbn(Integer.parseInt(paramMdl.getSoutaiFlg()));
            }
        }

        TcdHolidayInfDao thiDao = new TcdHolidayInfDao(con);
        TcdHolidayInfModel thiMdl = thiDao.getHolidayDataWithName(paramMdl.getHolidayType());

        //休日区分
        if (thiMdl != null) {
            tcdMdl.setThiSid(thiMdl.getThiSid());
        }

        //休日日数、休日内容
        if (__isLock(usModel, con, admConf.getTacLockHoliday())) {
            tcdMdl.setTcdHolother("");
            tcdMdl.setTcdHolcnt(null);
            tcdMdl.setTcdLockHoliday(GSConstTimecard.LOCK_FLG);
        } else {
            tcdMdl.setTcdHolother(NullDefault.getString(paramMdl.getHolidayText(), ""));
            if (!StringUtil.isNullZeroStringSpace(paramMdl.getHolidayType())) {
                tcdMdl.setTcdHolcnt(new BigDecimal(paramMdl.getHolidayNum()));
            }
        }

        UDate editDate = new UDate();
        tcdMdl.setTcdStatus(GSConstTimecard.TCD_STATUS_EDIT);
        tcdMdl.setTcdAuid(usrSid);
        tcdMdl.setTcdAdate(editDate);
        tcdMdl.setTcdEuid(usrSid);
        tcdMdl.setTcdEdate(editDate);

        //タイムカード登録・編集
        TimecardDao dao = new TimecardDao(con);
        TcdTcdataDao tcDao = new TcdTcdataDao(con);
        TcdTcdataModel mdl = tcDao.select(tcdMdl);
        TcdDatausedSumDao tdsDao = new TcdDatausedSumDao(con);
        if (mdl != null) {
            tdsDao.insertDelDiff(Arrays.asList(tcdMdl.getTcdDate()), tcdMdl.getUsrSid());
            dao.updateLock(tcdMdl);    
        } else {
            paramMdl.setCmdMode(GSConstTimecard.CMDMODE_ADD);
            dao.insert(tcdMdl);
        }
        tdsDao.insertAddDiff(Arrays.asList(tcdMdl.getTcdDate()), tcdMdl.getUsrSid());
        return tcdMdl;
    }

    /**
     * <br>[機  能] ロックフラグを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param usModel セッションユーザモデル
     * @param con コネクション
     * @param lockFlg ロックフラグ(管理者設定)
     * @throws SQLException SQL実行時例外
     * @return boolean ロックフラグ
     */
    private boolean __isLock(BaseUserModel usModel, Connection con, int lockFlg)
            throws SQLException {

        if (__getUserKbn(usModel, con) == GSConstTimecard.USER_KBN_ADMIN
                || __getUserKbn(usModel, con) == GSConstTimecard.USER_KBN_GRPADM
                || lockFlg == GSConstTimecard.UNLOCK_FLG) {
            return false;
        }

        return true;
    }

    /**
     *
     * <br>[機  能] ユーザの種別が一般・グループ管理者・管理者かを判定します。
     * <br>[解  説] 管理者権限を持っている場合はグループ管理者権限よりも優先されます。
     * <br>[備  考]
     * @param usModel セッションユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return int ユーザ種別
     */
    private int __getUserKbn(BaseUserModel usModel, Connection con)
            throws SQLException {

        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(con,
                usModel,
                GSConstTimecard.PLUGIN_ID_TIMECARD);

        if (adminUser) {
            return GSConstTimecard.USER_KBN_ADMIN;
        }
        //グループ管理者設定判定
        CmnGroupmDao gpDao = new CmnGroupmDao(con);
        List<CmnGroupmModel> list = gpDao.selectGroupAdmin(usModel.getUsrsid());
        if (list.size() > 0) {
            return GSConstTimecard.USER_KBN_GRPADM;
        }
        return GSConstTimecard.USER_KBN_NORMAL;
    }

    /**
     * <br>[機  能] モデルに開始終了時間を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tcdMdl タイムカード情報
     * @param date 日時
     * @param dakokuFlg 打刻時間かどうか
     * @param startTime 始業時間
     * @param endTime 終業時間
     */
    private void __setStartEndTimes(ApiTcdEditParamModel paramMdl,
            TcdTcdataModel tcdMdl, UDate date, boolean dakokuFlg,
            String startTime, String endTime) {

        //開始時間
        if (!StringUtil.isNullZeroStringSpace(startTime)) {
            ArrayList<String> sList = StringUtil.split(":", startTime);
            int sHour = Integer.parseInt(sList.get(0));
            int sMinute = Integer.parseInt(sList.get(1));
            UDate strInDate = date.cloneUDate();
            strInDate.setZeroHhMmSs();
            strInDate.setHour(sHour);
            strInDate.setMinute(sMinute);
            Time sTime = null;
            sTime = new Time(strInDate.getTime());
            if (dakokuFlg) {
                tcdMdl.setTcdStrikeIntime(sTime);
            } else {
                tcdMdl.setTcdIntime(sTime);
            }
        }

        if (!StringUtil.isNullZeroStringSpace(endTime)) {
          //終了時間
            ArrayList<String> eList = StringUtil.split(":", endTime);
            int eHour = Integer.parseInt(eList.get(0));
            int eMinute = Integer.parseInt(eList.get(1));
            UDate strOutDate = date.cloneUDate();
            strOutDate.setZeroHhMmSs();
            strOutDate.setHour(eHour);
            strOutDate.setMinute(eMinute);
            Time eTime = null;
            eTime = new Time(strOutDate.getTime());
            if (dakokuFlg) {
                tcdMdl.setTcdStrikeOuttime(eTime);
            } else {
                tcdMdl.setTcdOuttime(eTime);
            }
        }
    }
}
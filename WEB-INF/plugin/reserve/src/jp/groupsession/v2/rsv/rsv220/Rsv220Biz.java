package jp.groupsession.v2.rsv.rsv220;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv220Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv220Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Rsv220Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv220ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public void setInitData(Rsv220ParamModel paramMdl, Connection con) throws SQLException,
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        
        log__.debug(" start ");

        //DBより現在の設定を取得する。(なければデフォルト)
        RsvAdmConfDao confDao = new RsvAdmConfDao(con);
        RsvAdmConfModel confModel = confDao.select();
        if (confModel == null) {
            RsvCommonBiz biz = new RsvCommonBiz();
            confModel = biz.setInitAdminnConfModel();
        }

        //施設予約時間単位
        paramMdl.setRsv220HourDiv(
                NullDefault.getString(
                        paramMdl.getRsv220HourDiv(),
                        String.valueOf(confModel.getRacHourDiv())));

        DateTimePickerBiz dateBiz = new DateTimePickerBiz();

        //午前
        int frAmTimeH = confModel.getRacAmTimeFrH();
        int frAmTimeM = confModel.getRacAmTimeFrM();
        paramMdl.setRsv220AmFrHour(
                NullDefault.getString(paramMdl.getRsv220AmFrHour(),
                        String.valueOf(frAmTimeH)));
        paramMdl.setRsv220AmFrMin(
                NullDefault.getString(paramMdl.getRsv220AmFrMin(),
                        String.valueOf(frAmTimeM)));
        if (paramMdl.getRsv220AmFrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv220AmFrTime",
                    "rsv220AmFrHour", "rsv220AmFrMin", null);
        }

        int toAmTimeH = confModel.getRacAmTimeToH();
        int toAmTimeM = confModel.getRacAmTimeToM();
        paramMdl.setRsv220AmToHour(
                NullDefault.getString(paramMdl.getRsv220AmToHour(),
                        String.valueOf(toAmTimeH)));
        paramMdl.setRsv220AmToMin(
                NullDefault.getString(paramMdl.getRsv220AmToMin(),
                        String.valueOf(toAmTimeM)));
        if (paramMdl.getRsv220AmToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv220AmToTime",
                    "rsv220AmToHour", "rsv220AmToMin", null);
        }

        //午後
        int frPmTimeH = confModel.getRacPmTimeFrH();
        int frPmTimeM = confModel.getRacPmTimeFrM();
        paramMdl.setRsv220PmFrHour(
                NullDefault.getString(paramMdl.getRsv220PmFrHour(),
                        String.valueOf(frPmTimeH)));
        paramMdl.setRsv220PmFrMin(
                NullDefault.getString(paramMdl.getRsv220PmFrMin(),
                        String.valueOf(frPmTimeM)));
        if (paramMdl.getRsv220PmFrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv220PmFrTime",
                    "rsv220PmFrHour", "rsv220PmFrMin", null);
        }

        int toPmTimeH = confModel.getRacPmTimeToH();
        int toPmTimeM = confModel.getRacPmTimeToM();
        paramMdl.setRsv220PmToHour(
                NullDefault.getString(paramMdl.getRsv220PmToHour(),
                        String.valueOf(toPmTimeH)));
        paramMdl.setRsv220PmToMin(
                NullDefault.getString(paramMdl.getRsv220PmToMin(),
                        String.valueOf(toPmTimeM)));
        if (paramMdl.getRsv220PmToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv220PmToTime",
                    "rsv220PmToHour", "rsv220PmToMin", null);
        }

        //終日
        int frAllTimeH = confModel.getRacAllDayTimeFrH();
        int frAllTimeM = confModel.getRacAllDayTimeFrM();
        paramMdl.setRsv220AllDayFrHour(
                NullDefault.getString(paramMdl.getRsv220AllDayFrHour(),
                        String.valueOf(frAllTimeH)));
        paramMdl.setRsv220AllDayFrMin(
                NullDefault.getString(paramMdl.getRsv220AllDayFrMin(),
                        String.valueOf(frAllTimeM)));
        if (paramMdl.getRsv220AllDayFrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv220AllDayFrTime",
                    "rsv220AllDayFrHour", "rsv220AllDayFrMin", null);
        }

        int toAllDayTimeH = confModel.getRacAllDayTimeToH();
        int toAllDayTimeM = confModel.getRacAllDayTimeToM();
        paramMdl.setRsv220AllDayToHour(
                NullDefault.getString(paramMdl.getRsv220AllDayToHour(),
                        String.valueOf(toAllDayTimeH)));
        paramMdl.setRsv220AllDayToMin(
                NullDefault.getString(paramMdl.getRsv220AllDayToMin(),
                        String.valueOf(toAllDayTimeM)));
        if (paramMdl.getRsv220AllDayToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv220AllDayToTime",
                    "rsv220AllDayToHour", "rsv220AllDayToMin", null);
        }

        log__.debug(" end ");
    }

}

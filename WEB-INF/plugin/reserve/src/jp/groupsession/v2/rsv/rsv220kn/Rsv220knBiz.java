package jp.groupsession.v2.rsv.rsv220kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約基本設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv220knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv220knBiz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Rsv220knBiz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 表示データを設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     */
    public void setInitData(Rsv220knParamModel paramMdl, Connection con) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        //午前
        UDate frAmTime = new UDate();
        frAmTime.setHour(Integer.parseInt(paramMdl.getRsv220AmFrHour()));
        frAmTime.setMinute(Integer.parseInt(paramMdl.getRsv220AmFrMin()));
        UDate toAmTme = frAmTime.cloneUDate();
        toAmTme.setHour(Integer.parseInt(paramMdl.getRsv220AmToHour()));
        toAmTme.setMinute(Integer.parseInt(paramMdl.getRsv220AmToMin()));
        String startStr = UDateUtil.getSeparateHM(frAmTime);
        String endStr =  UDateUtil.getSeparateHM(toAmTme);
        paramMdl.setRsv220AmTime(gsMsg.getMessage("cmn.am") +  "："
            + startStr + gsMsg.getMessage("tcd.153") + endStr);
        //午後
        UDate frPmTime = new UDate();
        frPmTime.setHour(Integer.parseInt(paramMdl.getRsv220PmFrHour()));
        frPmTime.setMinute(Integer.parseInt(paramMdl.getRsv220PmFrMin()));
        UDate toPmTime = new UDate();
        toPmTime.setHour(Integer.parseInt(paramMdl.getRsv220PmToHour()));
        toPmTime.setMinute(Integer.parseInt(paramMdl.getRsv220PmToMin()));
        startStr = UDateUtil.getSeparateHM(frPmTime);
        endStr =  UDateUtil.getSeparateHM(toPmTime);
        paramMdl.setRsv220PmTime(gsMsg.getMessage("cmn.pm") +  "："
            + startStr + gsMsg.getMessage("tcd.153") + endStr);
        //終日
        UDate frAllTime = new UDate();
        frAllTime.setHour(Integer.parseInt(paramMdl.getRsv220AllDayFrHour()));
        frAllTime.setMinute(Integer.parseInt(paramMdl.getRsv220AllDayFrMin()));
        UDate toAllTime = new UDate();
        toAllTime.setHour(Integer.parseInt(paramMdl.getRsv220AllDayToHour()));
        toAllTime.setMinute(Integer.parseInt(paramMdl.getRsv220AllDayToMin()));
        startStr = UDateUtil.getSeparateHM(frAllTime);
        endStr =  UDateUtil.getSeparateHM(toAllTime);
        paramMdl.setRsv220AllTime(gsMsg.getMessage("cmn.allday") + "："
            + startStr + gsMsg.getMessage("tcd.153") + endStr);
    }

    /**
     * <br>[機  能] 施設予約基本情報をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv220knParamModel
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void updateRsvAdmConf(Rsv220knParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //既存のデータを取得
        //DBより現在の設定を取得する。(なければデフォルト)
        RsvAdmConfModel confModel = RsvCommonBiz.getDefaultAdmConfModel();
        //データを設定
        confModel.setRacHourDiv(
                NullDefault.getInt(paramMdl.getRsv220HourDiv(), GSConstReserve.DF_HOUR_DIVISION));
        confModel.setRacEuid(umodel.getUsrsid());
        UDate now = new UDate();
        confModel.setRacEdate(now);

        //午前開始
        confModel.setRacAmTimeFrH(NullDefault.getInt(paramMdl.getRsv220AmFrHour(),
                GSConstReserve.DF_FROM_AM_HOUR));
        confModel.setRacAmTimeFrM(NullDefault.getInt(paramMdl.getRsv220AmFrMin(),
                GSConstReserve.DF_FROM_AM_MINUTES));

        //午前終了
        confModel.setRacAmTimeToH(NullDefault.getInt(paramMdl.getRsv220AmToHour(),
                GSConstReserve.DF_TO_AM_HOUR));
        confModel.setRacAmTimeToM(NullDefault.getInt(paramMdl.getRsv220AmToMin(),
                GSConstReserve.DF_TO_AM_MINUTES));

        //午後開始
        confModel.setRacPmTimeFrH(NullDefault.getInt(paramMdl.getRsv220PmFrHour(),
                GSConstReserve.DF_FROM_PM_HOUR));
        confModel.setRacPmTimeFrM(NullDefault.getInt(paramMdl.getRsv220PmFrMin(),
                GSConstReserve.DF_FROM_PM_MINUTES));

        //午後終了
        confModel.setRacPmTimeToH(NullDefault.getInt(paramMdl.getRsv220PmToHour(),
                GSConstReserve.DF_TO_PM_HOUR));
        confModel.setRacPmTimeToM(NullDefault.getInt(paramMdl.getRsv220PmToMin(),
                GSConstReserve.DF_TO_PM_MINUTES));

        //終日開始
        confModel.setRacAllDayTimeFrH(NullDefault.getInt(paramMdl.getRsv220AllDayFrHour(),
                GSConstReserve.DF_FROM_ALL_DAY_HOUR));
        confModel.setRacAllDayTimeFrM(NullDefault.getInt(paramMdl.getRsv220AllDayFrMin(),
                GSConstReserve.DF_FROM_ALL_DAY_MINUTES));

        //終日終了
        confModel.setRacAllDayTimeToH(NullDefault.getInt(paramMdl.getRsv220AllDayToHour(),
                GSConstReserve.DF_TO_ALL_DAY_HOUR));
        confModel.setRacAllDayTimeToM(NullDefault.getInt(paramMdl.getRsv220AllDayToMin(),
                GSConstReserve.DF_TO_ALL_DAY_MINUTES));

        //DB更新
        boolean commitFlg = false;
        try {
            //管理者設定を更新
            RsvAdmConfDao dao = new RsvAdmConfDao(con);
            int count = dao.updateHourDiv(confModel);
            if (count <= 0) {
                confModel.setRacAuid(umodel.getUsrsid());
                confModel.setRacAdate(now);
                dao.insert(confModel);
            }

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("施設予約基本設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
}

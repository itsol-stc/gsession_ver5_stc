package jp.groupsession.v2.tcd.main;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;

/**
 * <br>[機  能] タイムカード(メイン画面表示用)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdMainForm extends AbstractGsForm {
    //表示用項目
    /** ステータス */
    private String tcdStatus__;
    /** 始業時刻 */
    private String tcdStartTime__;
    /** 終業時刻 */
    private String tcdStopTime__;
    /** 個人設定(メイン表示設定)**/
    private int dspFlg__;
    /** 在席管理連動**/
    private String zaisekiSts__;
    /** タイムカードトップ画面URL */
    private String  tcdTopUrl__;

    /**
     * @return tcdTopUrl__ を戻します。
     */
    public String getTcdTopUrl() {
        return tcdTopUrl__;
    }
    /**
     * @param tcdTopUrl 設定する tcdTopUrl__。
     */
    public void setTcdTopUrl(String tcdTopUrl) {
        tcdTopUrl__ = tcdTopUrl;
    }

    /**
     * <p>zaisekiSts を取得します。
     * @return zaisekiSts
     */
    public String getZaisekiSts() {
        return zaisekiSts__;
    }

    /**
     * <p>zaisekiSts をセットします。
     * @param zaisekiSts zaisekiSts
     */
    public void setZaisekiSts(String zaisekiSts) {
        zaisekiSts__ = zaisekiSts;
    }

    /**
     * <p>dspFlg を取得します。
     * @return dspFlg
     */
    public int getDspFlg() {
        return dspFlg__;
    }

    /**
     * <p>dspFlg をセットします。
     * @param dspFlg dspFlg
     */
    public void setDspFlg(int dspFlg) {
        dspFlg__ = dspFlg;
    }

    /**
     * <p>tcdStartTime を取得します。
     * @return tcdStartTime
     */
    public String getTcdStartTime() {
        return tcdStartTime__;
    }

    /**
     * <p>tcdStartTime をセットします。
     * @param tcdStartTime tcdStartTime
     */
    public void setTcdStartTime(String tcdStartTime) {
        tcdStartTime__ = tcdStartTime;
    }

    /**
     * <p>tcdStatus を取得します。
     * @return tcdStatus
     */
    public String getTcdStatus() {
        return tcdStatus__;
    }

    /**
     * <p>tcdStatus をセットします。
     * @param tcdStatus tcdStatus
     */
    public void setTcdStatus(String tcdStatus) {
        tcdStatus__ = tcdStatus;
    }

    /**
     * <p>tcdStopTime を取得します。
     * @return tcdStopTime
     */
    public String getTcdStopTime() {
        return tcdStopTime__;
    }

    /**
     * <p>tcdStopTime をセットします。
     * @param tcdStopTime tcdStopTime
     */
    public void setTcdStopTime(String tcdStopTime) {
        tcdStopTime__ = tcdStopTime;
    }

    /**
     * <br>[機  能] 更新時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateChkMan001(int userSid,
            Connection con,
            RequestModel reqMdl) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String timecardInfo = gsMsg.getMessage("tcd.98");

        UDate sysDate = new UDate();
        //当日タイムカード存在チェック
        TimecardBiz tcdBiz = new TimecardBiz();
        TcdTcdataModel tcdMdl = tcdBiz.getTargetTcddata(userSid, sysDate, con);
        if (tcdMdl != null) {
            if (tcdMdl.getTcdIntime() != null && tcdMdl.getTcdOuttime() != null) {
                //登録済みはエラー
                msg = new ActionMessage("error.input.timecard.exist", timecardInfo);
                errors.add("" + "error.input.timecard.exist", msg);
            }
            if (tcdMdl.getTcdIntime() != null
                    && tcdMdl.getTcdOuttime() == null
                    && tcdBiz.isOver24HoursOrTenAM(
                            tcdMdl.getTcdDate(),
                            tcdMdl.getTcdIntime(),
                            tcdMdl.getTcdStrikeIntime())) {
                String startToEnd = gsMsg.getMessage("tcd.214");
                String under24 = gsMsg.getMessage("tcd.161");
                msg = new ActionMessage("error.input.comp.text", startToEnd, under24);
                errors.add("" + "error.input.comp.text", msg);
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 表示時のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @param admConf 管理者設定
     * @param reqMdl リクエスト情報
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public boolean validateMain(int userSid,
            Connection con,
            TcdAdmConfModel admConf,
            RequestModel reqMdl)
    throws SQLException {
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);

        //不正データがある場合はtrue
        return tcBiz.isFailDataExist(userSid, con, admConf);
    }
}

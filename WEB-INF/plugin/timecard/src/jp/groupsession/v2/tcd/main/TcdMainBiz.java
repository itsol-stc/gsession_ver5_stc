package jp.groupsession.v2.tcd.main;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardUtil;
import jp.groupsession.v2.tcd.model.TcdPriConfModel;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;

/**
 * <br>[機  能] タイムカード(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdMainBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdMainBiz.class);

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param umodel ユーザ基本情報
     * @throws Exception SQL実行時例外
     */
    public void setInitData(TcdMainParamModel paramMdl,
            RequestModel reqMdl, Connection con,
            PluginConfig pconfig, BaseUserModel umodel) throws Exception {

        //タイムカードステータスセット
        __setTcdStatus(con, paramMdl, umodel.getUsrsid(), reqMdl);
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        TcdPriConfModel conf = tcBiz.getTcdPriConfModel(umodel.getUsrsid(), con);
        log__.debug("-- form.getTcdStatus() --" + paramMdl.getTcdStatus());
        log__.debug("-- conf.getTpcMainDsp() --" + conf.getTpcMainDsp());
        //表示・非表示設定
        if (paramMdl.getTcdStatus().equals(String.valueOf(GSConstTimecard.STATUS_FIN))
                && conf.getTpcMainDsp() == GSConstCommon.MAIN_NOT_DSP) {
            paramMdl.setDspFlg(GSConstCommon.MAIN_NOT_DSP);
        } else {
            paramMdl.setDspFlg(GSConstCommon.MAIN_DSP);
        }

        String zskStsSwitch = String.valueOf(GSConstTimecard.ZAISEKI_ON);
        if (conf != null) {
            zskStsSwitch = String.valueOf(conf.getTpcZaisekiSts());
        }
        paramMdl.setZaisekiSts(
                NullDefault.getString(
                        paramMdl.getZaisekiSts(),
                        zskStsSwitch));

        log__.debug("-- form.getDspFlg() --" + paramMdl.getDspFlg());
    }

    /**
     * <br>[機  能] 在席ステータスをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid セッションユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setTcdStatus(Connection con, TcdMainParamModel paramMdl, int userSid,
                                RequestModel reqMdl)
        throws SQLException {

        log__.debug("タイムカードステータスセット");
        UDate sysDate = new UDate();
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        TcdTcdataModel tcdMdl = tcdBiz.getTargetTcddata(userSid, sysDate, con);

        //タイムカードのボタン表示チェック
        TimecardBiz checkBtn = new TimecardBiz();
        int btnDspKbn = GSConstTimecard.STATUS_FREE;
        if (tcdMdl != null) {
            btnDspKbn = checkBtn.checkDakokuBtn(tcdMdl.getTcdIntime(), tcdMdl.getTcdOuttime(),
                    null, null);
        }
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = null;
        int interval;
        
        if (btnDspKbn == GSConstTimecard.STATUS_FREE) {
            paramMdl.setTcdStatus(String.valueOf(GSConstTimecard.STATUS_FREE));
        } else if (btnDspKbn == GSConstTimecard.STATUS_HAFE) {
            admConf = tcBiz.getTcdAdmConfModel(userSid, con);
            interval = admConf.getTacInterval();
            paramMdl.setTcdStatus(String.valueOf(GSConstTimecard.STATUS_HAFE));
            //始業打刻時間が設定されていなければ始業時間を設定
            if (tcdMdl.getTcdStrikeIntime() == null) {
                paramMdl.setTcdStartTime(TimecardUtil.getTimeString(
                        TimecardBiz.adjustIntime(tcdMdl.getTcdIntime(), interval)));
            } else {
                paramMdl.setTcdStartTime(TimecardUtil.getTimeString(tcdMdl.getTcdStrikeIntime()));
            }
        } else if (btnDspKbn == GSConstTimecard.STATUS_FIN) {
            admConf = tcBiz.getTcdAdmConfModel(userSid, con);
            interval = admConf.getTacInterval();
            paramMdl.setTcdStatus(String.valueOf(GSConstTimecard.STATUS_FIN));
            //打刻時間が設定されていなければ始業終業時間を設定
            if (tcdMdl.getTcdStrikeIntime() == null) {
                paramMdl.setTcdStartTime(TimecardUtil.getTimeString(
                        TimecardBiz.adjustIntime(tcdMdl.getTcdIntime(), interval)));
            } else {
                paramMdl.setTcdStartTime(TimecardUtil.getTimeString(tcdMdl.getTcdStrikeIntime()));
            }
            if (tcdMdl.getTcdStrikeOuttime() == null) {
                paramMdl.setTcdStopTime(TimecardUtil.getTimeString(
                        TimecardBiz.adjustOuttime(tcdMdl.getTcdOuttime(), interval)));
            } else {
                paramMdl.setTcdStopTime(TimecardUtil.getTimeString(tcdMdl.getTcdStrikeOuttime()));
            }
        } else {
            paramMdl.setTcdStatus(String.valueOf(GSConstTimecard.STATUS_FREE));
        }
    }

    /**
     * <br>[機  能] タイムカードを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return TcdTcdataModel
     * @throws SQLException SQL実行時例外
     */
    public TcdTcdataModel updateTcd(TcdMainParamModel paramMdl, RequestModel reqMdl, Connection con)
        throws SQLException {

        log__.debug("タイムカード更新");

        //セッション情報を取得
        UDate sysDate = new UDate();

        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        TcdTcdataModel tcMdl = tcdBiz.dakokuTcd(reqMdl, con,
                sysDate, GSConstTimecard.DAKOKUKBN_NONE, (paramMdl.getZaisekiSts() != null));

        return tcMdl;
    }



}

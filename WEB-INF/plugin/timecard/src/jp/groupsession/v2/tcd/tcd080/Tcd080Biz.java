package jp.groupsession.v2.tcd.tcd080;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdPriConfDao;
import jp.groupsession.v2.tcd.model.TcdPriConfModel;

/**
 * <br>[機  能] タイムカード 個人設定 基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd080Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd080Biz.class);

    /**
     * <br>[機  能] 個人設定を更新します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return int 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updatePriConf(Tcd080ParamModel paramMdl, int usrSid, Connection con,
            RequestModel reqMdl)
    throws SQLException {
        log__.debug("updatePriConf");

        //基本設定を取得
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = tcdBiz.getTcdAdmConfModel(usrSid, con);
        TcdPriConfModel model = new TcdPriConfModel(usrSid);
        model.setTpcInHour(paramMdl.getTcd080DefFrH());
        model.setTpcInMin(paramMdl.getTcd080DefFrM());
        model.setTpcOutHour(paramMdl.getTcd080DefToH());
        model.setTpcOutMin(paramMdl.getTcd080DefToM());
        model.setTpcMainDsp(paramMdl.getTcd080mainDsp());
        model.setTpcKinmuOut(paramMdl.getTcd080kinmuOutput());
        model.setTpcZaisekiSts(
                NullDefault.getInt(paramMdl.getTcd080zaisekiSts(), GSConstTimecard.ZAISEKI_OFF));
        TcdPriConfDao dao = new TcdPriConfDao(con);
        if (admConf.getTacWorkreportKbn() == 0) {
            dao.update(model);
        } else {
            dao.updateKinmuZyogai(model);
        }
        return admConf.getTacWorkreportKbn();
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Tcd080ParamModel paramMdl,
            RequestModel reqMdl, Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        //DBより設定情報を取得。なければデフォルト値とする。
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        setLabel(paramMdl, reqMdl, con);
        //基本設定を取得
        TcdAdmConfModel admConf = tcdBiz.getTcdAdmConfModel(sessionUsrSid, con);
        //フォーマットが存在する場合は既存データを削除する
        if (admConf.getTacWorkreportKbn() != 0) {
            paramMdl.setTcd080KinpDispFlg(1);
        }
        //個人設定
        TcdPriConfModel priMdl = tcdBiz.getTcdPriConfModel(sessionUsrSid, con);
        int inHour = priMdl.getTpcInHour();
        int inMin = priMdl.getTpcInMin();
        int outHour = priMdl.getTpcOutHour();
        int outMin = priMdl.getTpcOutMin();
        int mainDsp = priMdl.getTpcMainDsp();
        int kinmuOut = priMdl.getTpcKinmuOut();
        int zskSts = priMdl.getTpcZaisekiSts();
        if (paramMdl.getTcd080DefFrH() != -1) {
            inHour = paramMdl.getTcd080DefFrH();
        }
        if (paramMdl.getTcd080DefFrM() != -1) {
            inMin = paramMdl.getTcd080DefFrM();
        }
        if (paramMdl.getTcd080DefToH() != -1) {
            outHour = paramMdl.getTcd080DefToH();
        }
        if (paramMdl.getTcd080DefToM() != -1) {
            outMin = paramMdl.getTcd080DefToM();
        }
        if (paramMdl.getTcd080mainDsp() != -1) {
            mainDsp = paramMdl.getTcd080mainDsp();
        }
        if (paramMdl.getTcd080kinmuOutput() != -1) {
            kinmuOut = paramMdl.getTcd080kinmuOutput();
        }
        //開始 時
        paramMdl.setTcd080DefFrH(inHour);
        //開始 分
        paramMdl.setTcd080DefFrM(inMin);
        //終了 時
        paramMdl.setTcd080DefToH(outHour);
        //終了 分
        paramMdl.setTcd080DefToM(outMin);
        //メイン表示
        paramMdl.setTcd080mainDsp(mainDsp);
        //勤務表出力
        paramMdl.setTcd080kinmuOutput(kinmuOut);
        //在席管理と連動
        paramMdl.setTcd080zaisekiSts(
                NullDefault.getString(
                        paramMdl.getTcd080zaisekiSts(), String.valueOf(zskSts)));
    }

    /**
     * <br>[機  能] 時間コンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setLabel(Tcd080ParamModel paramMdl,
            RequestModel reqMdl, Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        //管理者設定
        TcdAdmConfModel admMdl = tcdBiz.getTcdAdmConfModel(sessionUsrSid, con);
        paramMdl.setTcd080HourLabel(tcdBiz.getHourLabelList());
        paramMdl.setTcd080MinuteLabel(tcdBiz.getMinLabelList(admMdl));
    }
}

package jp.groupsession.v2.ntp.ntp092;

import java.sql.Connection;
import java.sql.SQLException;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpPriConfDao;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 日間表示時間帯設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp092Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp092Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Ntp092Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp092ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Ntp092ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {


//        UDate ifr = pconf.getNprFrDate();
//        UDate ito = pconf.getNprToDate();

//        //開始 時
//        log__.debug("開始 時 = " + ifr.getIntHour());
//        paramMdl.setNtp092FrH(ifr.getIntHour());
//        //終了 時
//        log__.debug("終了 時 = " + ito.getIntHour());
//        paramMdl.setNtp092ToH(ito.getIntHour());

    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp092ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Ntp092ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con, umodel.getUsrsid());

        //開始時刻
        UDate fromUd = new UDate();
        fromUd.setHour(paramMdl.getNtp092FrH());
        //終了時刻
        UDate toUd = new UDate();
        toUd.setHour(paramMdl.getNtp092ToH());

//        pconf.setNprFrDate(fromUd);
//        pconf.setNprToDate(toUd);
        pconf.setNprEuid(umodel.getUsrsid());
        pconf.setNprEdate(new UDate());

        boolean commitFlg = false;
        try {
            NtpPriConfDao dao = new NtpPriConfDao(con);
            int count = dao.updateFromTo(pconf);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(pconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
}

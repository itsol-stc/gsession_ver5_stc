package jp.groupsession.v2.tcd.tcd170;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;


/**
 * <br>[機  能] タイムカード 管理者設定 休日区分登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd170Biz {

    /** 画面ID */
    public static final String SCR_ID = "tcd170";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd170Biz.class);
    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd170Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(Tcd170ParamModel paramMdl, Connection con)
    throws SQLException {

        log__.debug("STRAT");

        //初期表示
        if (paramMdl.getTcd170initFlg() == GSConstTimecard.DSP_INIT) {
            //編集の場合、DBから値を取得、設定
            if (paramMdl.getTcd170Mode() == GSConst.CMDMODE_EDIT) {
                __setHolidayData(paramMdl, con);
                paramMdl.setTcd170initFlg(1);
            }
        }
        log__.debug("end");
    }

    /**
     * <br>[機  能] 編集時の画面情報を取得、設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setHolidayData(Tcd170ParamModel paramMdl, Connection con) throws SQLException {
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        TcdHolidayInfModel mdl = dao.select(paramMdl.getTcd170EditSid());
        paramMdl.setTcd170HolidayName(mdl.getThiName());
        paramMdl.setTcd170HoliTotalKbn(mdl.getThiHoltotalKbn());
        paramMdl.setTcd170Limit(mdl.getThiLimit());
        paramMdl.setTcd170HoliContentKbn(mdl.getThiContentKbn());
    }

    /**
     * <br>[機  能] 編集時の画面情報を取得、設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param usrSid ユーザSid
     * @throws SQLException SQL実行時例外
     */
    protected void _delete(Tcd170ParamModel paramMdl, Connection con) throws SQLException {
        log__.debug("削除");
        boolean commitFlg = false;
        try {
            TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
            dao.delete(paramMdl.getTcd170EditSid());
            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

    }
}

package jp.groupsession.v2.tcd.tcd170kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;


/**
 * <br>[機  能] タイムカード 管理者設定 休日区分登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd170knBiz {

    /** 画面ID */
    public static final String SCR_ID = "tcd170";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd170knBiz.class);
    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd170knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 新規登録
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @throws SQLException SQL実行時例外
     */
    protected int _insert(Tcd170knParamModel paramMdl, Connection con,
            int usrSid, MlCountMtController cntCon) throws SQLException {
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        TcdHolidayInfModel mdl = new TcdHolidayInfModel();
        int sid = (int) cntCon.getSaibanNumber(SaibanModel.SBNSID_TIMECARD,
                "tcdHolInf", usrSid);
        UDate now = new UDate();
        int order = dao.maxSortNumber() + 1;
        mdl.setThiSid(sid);
        mdl.setThiName(paramMdl.getTcd170HolidayName());
        mdl.setThiHoltotalKbn(paramMdl.getTcd170HoliTotalKbn());
        mdl.setThiLimit(paramMdl.getTcd170Limit());
        mdl.setThiContentKbn(paramMdl.getTcd170HoliContentKbn());
        mdl.setThiOrder(order);
        mdl.setThiAuid(usrSid);
        mdl.setThiAdate(now);
        mdl.setThiEuid(usrSid);
        mdl.setThiEdate(now);
        dao.insert(mdl);

        return sid;
    }

    /**
     * <br>[機  能] 編集
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    protected void _update(Tcd170knParamModel paramMdl, Connection con,
            int usrSid) throws SQLException {
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        TcdHolidayInfModel mdl = new TcdHolidayInfModel();
        UDate now = new UDate();
        mdl.setThiSid(paramMdl.getTcd170EditSid());
        mdl.setThiName(paramMdl.getTcd170HolidayName());
        mdl.setThiHoltotalKbn(paramMdl.getTcd170HoliTotalKbn());
        mdl.setThiLimit(paramMdl.getTcd170Limit());
        mdl.setThiContentKbn(paramMdl.getTcd170HoliContentKbn());
        mdl.setThiEuid(usrSid);
        mdl.setThiEdate(now);
        dao.update(mdl);

    }
}

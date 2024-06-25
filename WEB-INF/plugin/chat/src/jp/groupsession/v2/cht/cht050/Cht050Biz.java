package jp.groupsession.v2.cht.cht050;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;

/**
 *
 * <br>[機  能] 手動データ削除のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht050Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht050Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**コネクション*/
    private Connection con__ = null;

    /**コンストラクタ
     * */
    public Cht050Biz() {

    }
    /**コンストラクタ
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * */
    public Cht050Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }


    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht050ParamModel
     * @throws SQLException SQLException
     */
    public void setInitData(Cht050ParamModel paramMdl) throws SQLException {

        log__.debug("init");
        ChtBiz biz = new ChtBiz(con__, reqMdl__);
        //年ラベル
        paramMdl.setCht050YearLabelList(biz.createDelYearCombo());
        //月ラベル
        paramMdl.setCht050MonthLabelList(biz.createDelMonthCombo());

        //バッチ処理実行時間を取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con__);
        CmnBatchJobModel batMdl = batDao.select();
        String batchTime = "";
        if (batMdl != null) {
            batchTime = String.valueOf(batMdl.getBatFrDate());
        }
        paramMdl.setCht050BatchTime(batchTime);

        //管理者設定取得
        if (paramMdl.getCht050InitFlg() == 0) {
            ChtAdmConfModel adminMdl = biz.getChtAconf();
            paramMdl.setCht050ReferenceYear(adminMdl.getCacAtdelY());
            paramMdl.setCht050ReferenceMonth(adminMdl.getCacAtdelM());
            paramMdl.setCht050DoAutoDel(adminMdl.getCacAtdelFlg());
            paramMdl.setCht050InitFlg(1);
        }

    }
}

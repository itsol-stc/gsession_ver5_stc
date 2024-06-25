package jp.groupsession.v2.cht.cht060;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 *
 * <br>[機  能] 手動データ削除のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht060Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht060Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**コンストラクタ
     * */
    public Cht060Biz() {

    }
    /**コンストラクタ
     * @param reqMdl リクエスト情報
     * */
    public Cht060Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     *
     * */
    public void setInitData(Cht060ParamModel paramMdl) {
        log__.debug("init");
        ChtBiz biz = new ChtBiz(reqMdl__);
        //年ラベル
        paramMdl.setCht060YearLabelList(biz.createDelYearCombo());
        //月ラベル
        paramMdl.setCht060MonthLabelList(biz.createDelMonthCombo());
    }
}

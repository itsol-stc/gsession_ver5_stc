package jp.groupsession.v2.enq.enq950;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;

/**
 * <br>[機  能] 管理者設定 アンケート手動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq950Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq950Biz.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq950Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq950Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq950ParamModel enq950Model,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報を取得");

        // 年リストの取得
        enq950Model.setEnq950YearLabel(EnqCommonBiz.getYearLabel(reqMdl));
        // 月リストの取得
        enq950Model.setEnq950MonthLabel(EnqCommonBiz.getMonthLabel(reqMdl));

        // 初期値の設定
        // --発信フォルダ 削除区分
        enq950Model.setEnq950SendDelKbn(NullDefault.getStringZeroLength(
                enq950Model.getEnq950SendDelKbn(),
                String.valueOf(GSConstEnquete.DELETE_KBN_OFF)));

        // --発信フォルダ 年
        enq950Model.setEnq950SelectSendYear(NullDefault.getStringZeroLength(
                StringUtilHtml.transToHTmlPlusAmparsant(enq950Model.getEnq950SelectSendYear()),
                String.valueOf(GSConstEnquete.YEAR_LABEL[3])));

        // --発信フォルダ 月
        enq950Model.setEnq950SelectSendMonth(NullDefault.getStringZeroLength(
                StringUtilHtml.transToHTmlPlusAmparsant(enq950Model.getEnq950SelectSendMonth()),
                String.valueOf(GSConstEnquete.MONTH_LABEL[0])));

        // --草稿フォルダ 削除区分
        enq950Model.setEnq950DraftDelKbn(NullDefault.getStringZeroLength(
                enq950Model.getEnq950DraftDelKbn(),
                String.valueOf(GSConstEnquete.DELETE_KBN_OFF)));

        // --草稿フォルダ 年
        enq950Model.setEnq950SelectDraftYear(NullDefault.getStringZeroLength(
                StringUtilHtml.transToHTmlPlusAmparsant(enq950Model.getEnq950SelectDraftYear()),
                String.valueOf(GSConstEnquete.YEAR_LABEL[3])));

        // --草稿フォルダ 月
        enq950Model.setEnq950SelectDraftMonth(NullDefault.getStringZeroLength(
                StringUtilHtml.transToHTmlPlusAmparsant(enq950Model.getEnq950SelectDraftMonth()),
                String.valueOf(GSConstEnquete.MONTH_LABEL[0])));
    }

}

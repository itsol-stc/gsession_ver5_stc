package jp.groupsession.v2.enq.enq960;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqAutodeleteDao;
import jp.groupsession.v2.enq.model.EnqAutodeleteModel;

/**
 * <br>[機  能] 管理者設定 アンケート自動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq960Biz {

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq960Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq960ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        //年リストの取得
        paramMdl.setEnq960YearLabel(EnqCommonBiz.getYearLabel(reqMdl));
        //月リストの取得
        paramMdl.setEnq960MonthLabel(EnqCommonBiz.getMonthLabel(reqMdl));

        if (paramMdl.getEnq960initFlg() != 1) {
            paramMdl.setEnq960initFlg(1);

            //自動削除設定値取得
            EnqAutodeleteDao delDao = new EnqAutodeleteDao(con);
            EnqAutodeleteModel delMdl = delDao.select();

            if (delMdl == null) {
                //初期値の設定
                delMdl = new EnqAutodeleteModel();
                //発信フォルダ 削除区分
                delMdl.setEadSendKbn(GSConstEnquete.DELETE_KBN_OFF);
                //発信フォルダ 年
                delMdl.setEadSendYear(0);
                //発信フォルダ 月
                delMdl.setEadSendMonth(0);
                //草稿フォルダ 削除区分
                delMdl.setEadDraftKbn(GSConstEnquete.DELETE_KBN_OFF);
                //草稿フォルダ 年
                delMdl.setEadDraftYear(0);
                //草稿フォルダ 月
                delMdl.setEadDraftMonth(0);
            }

            //発信フォルダ 削除区分
            paramMdl.setEnq960SendDelKbn(NullDefault.getStringZeroLength(
                    paramMdl.getEnq960SendDelKbn(),
                    String.valueOf(delMdl.getEadSendKbn())));
            //発信フォルダ 年
                paramMdl.setEnq960SelectSendYear(NullDefault.getStringZeroLength(
                        paramMdl.getEnq960SelectSendYear(),
                        String.valueOf(delMdl.getEadSendYear())));
            //発信フォルダ 月
            paramMdl.setEnq960SelectSendMonth(NullDefault.getStringZeroLength(
                    paramMdl.getEnq960SelectSendMonth(),
                    String.valueOf(delMdl.getEadSendMonth())));

            //草稿フォルダ 削除区分
            paramMdl.setEnq960DraftDelKbn(NullDefault.getStringZeroLength(
                    paramMdl.getEnq960DraftDelKbn(),
                    String.valueOf(delMdl.getEadDraftKbn())));
            //草稿フォルダ 年
            paramMdl.setEnq960SelectDraftYear(NullDefault.getStringZeroLength(
                    paramMdl.getEnq960SelectDraftYear(),
                    String.valueOf(delMdl.getEadDraftYear())));
            //草稿フォルダ 月
            paramMdl.setEnq960SelectDraftMonth(NullDefault.getStringZeroLength(
                    paramMdl.getEnq960SelectDraftMonth(),
                    String.valueOf(delMdl.getEadDraftMonth())));
        }
    }
}

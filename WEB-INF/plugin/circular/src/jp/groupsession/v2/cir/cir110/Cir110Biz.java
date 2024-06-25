package jp.groupsession.v2.cir.cir110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAdelDao;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 管理者設定 自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir110Biz {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Cir110Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Cir110ParamModel paramMdl, Connection con)
        throws SQLException {

       /*********************************************************
        *
        * 回覧板自動削除設定のリスト・選択値を設定
        *
        *********************************************************/

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //年リスト
        for (int nLabel : GSConst.DEL_YEAR_DATE) {
            String label = String.valueOf(nLabel);
            yearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {label}), label));
        }
        paramMdl.setCir110YearLabelList(yearLabel);

        //月リスト
        for (int nLabel : GSConst.DEL_MONTH_DATE) {
            String label = String.valueOf(nLabel);
            monthLabel.add(new LabelValueBean(
                                      gsMsg.getMessage("cmn.months", new String[] {label}),
                                      label));
        }
        paramMdl.setCir110MonthLabelList(monthLabel);

        CirAdelDao delDao = new CirAdelDao(con);
        CirAdelModel delMdl = delDao.select(0);
        if (delMdl == null) {
            delMdl = new CirAdelModel();
            delMdl.setCadDelKbn(GSConstCircular.CIR_DEL_KBN_ADM_SETTING);
            delMdl.setCadJdelKbn(GSConstCircular.CIR_AUTO_DEL_NO);
            delMdl.setCadJdelYear(0);
            delMdl.setCadJdelMonth(0);
            delMdl.setCadSdelKbn(GSConstCircular.CIR_AUTO_DEL_NO);
            delMdl.setCadSdelYear(0);
            delMdl.setCadSdelMonth(0);
            delMdl.setCadDdelKbn(GSConstCircular.CIR_AUTO_DEL_NO);
            delMdl.setCadDdelYear(0);
            delMdl.setCadDdelMonth(0);
        }

        //削除区分
        paramMdl.setCir110DelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110DelKbn(),
                        String.valueOf(delMdl.getCadDelKbn())));

        //受信メール処理 選択値
        paramMdl.setCir110JdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110JdelKbn(),
                        String.valueOf(delMdl.getCadJdelKbn())));

        //受信メール 年
        paramMdl.setCir110JYear(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110JYear(),
                        String.valueOf(delMdl.getCadJdelYear())));

        //受信メール 月
        paramMdl.setCir110JMonth(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110JMonth(),
                        String.valueOf(delMdl.getCadJdelMonth())));

        //送信メール処理 選択値
        paramMdl.setCir110SdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110SdelKbn(),
                        String.valueOf(delMdl.getCadSdelKbn())));

        //送信メール 年
        paramMdl.setCir110SYear(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110SYear(),
                        String.valueOf(delMdl.getCadSdelYear())));

        //送信メール 月
        paramMdl.setCir110SMonth(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110SMonth(),
                        String.valueOf(delMdl.getCadSdelMonth())));

        //ゴミ箱メール処理 選択値
        paramMdl.setCir110DdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110DdelKbn(),
                        String.valueOf(delMdl.getCadDdelKbn())));

        //ゴミ箱メール 年
        paramMdl.setCir110DYear(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110DYear(),
                        String.valueOf(delMdl.getCadDdelYear())));

        //ゴミ箱メール 月
        paramMdl.setCir110DMonth(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir110DMonth(),
                        String.valueOf(delMdl.getCadDdelMonth())));

    }
}
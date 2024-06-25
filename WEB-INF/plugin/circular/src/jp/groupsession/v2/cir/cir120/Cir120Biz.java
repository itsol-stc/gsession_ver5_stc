package jp.groupsession.v2.cir.cir120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 管理者設定 手動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir120Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir120Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Cir120Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(RequestModel reqMdl, Cir120ParamModel paramMdl,
                            Connection con) throws SQLException {

        log__.debug("start");

        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = new CirAccountModel();

        if (paramMdl.getCir120AccountSid() > 0) {
            cacMdl = cacDao.select(paramMdl.getCir120AccountSid());
        } else {
            //デフォルトのアカウントを所得
            cacMdl = cacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        }

        if (cacMdl != null) {
            paramMdl.setCir120AccountSid(cacMdl.getCacSid());
            paramMdl.setCir120AccountName(cacMdl.getCacName());
        }

       /*********************************************************
        *
        * 回覧板手動削除設定のリスト・選択値を設定
        *
        *********************************************************/

        GsMessage gsMsg = new GsMessage(reqMdl);

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();

        //年リスト
        for (int i = 0; i <= 10; i++) {
            yearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setCir120YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setCir120MonthLabelList(monthLabel);

        //受信タブ処理 選択値
        paramMdl.setCir120JdelKbn(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir120JdelKbn()),
                        String.valueOf(GSConstCircular.CIR_AUTO_DEL_NO)));

        //受信タブ 年
        paramMdl.setCir120JYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir120JYear()),
                        String.valueOf(3)));

        //受信タブ 月
        paramMdl.setCir120JMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir120JMonth()),
                        String.valueOf(0)));

        //送信済タブ処理 選択値
        paramMdl.setCir120SdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir120SdelKbn(),
                        String.valueOf(GSConstCircular.CIR_AUTO_DEL_NO)));

        //送信済タブ 年
        paramMdl.setCir120SYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir120SYear()),
                        String.valueOf(3)));

        //送信済タブ 月
        paramMdl.setCir120SMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir120SMonth()),
                        String.valueOf(0)));

        //ゴミ箱タブ処理 選択値
        paramMdl.setCir120DdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir120DdelKbn(),
                        String.valueOf(GSConstCircular.CIR_AUTO_DEL_NO)));

        //ゴミ箱タブ 年
        paramMdl.setCir120DYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir120DYear()),
                        String.valueOf(3)));

        //ゴミ箱タブ 月
        paramMdl.setCir120DMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir120DMonth()),
                        String.valueOf(0)));
    }
}
package jp.groupsession.v2.cir.cir090;

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
 * <br>[機  能] 回覧板 個人設定 手動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir090Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir090Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Cir090Biz() {
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
    public void setInitData(RequestModel reqMdl, Cir090ParamModel paramMdl,
                                                    Connection con) throws SQLException {

       /*********************************************************
        *
        * 回覧板手動削除設定のリスト・選択値を設定
        *
        *********************************************************/

        log__.debug("start");

        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = new CirAccountModel();

        if (paramMdl.getCir090AccountSid() > 0) {
            cacMdl = cacDao.select(paramMdl.getCir090AccountSid());
        } else {
            //デフォルトのアカウントを所得
            cacMdl = cacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        }

        paramMdl.setCir090AccountSid(cacMdl.getCacSid());
        paramMdl.setCir090AccountName(cacMdl.getCacName());

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //年リスト
        for (int i = 0; i <= 10; i++) {
            yearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setCir090YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setCir090MonthLabelList(monthLabel);

        //受信タブ処理 選択値
        paramMdl.setCir090JdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir090JdelKbn(),
                        String.valueOf(GSConstCircular.CIR_AUTO_DEL_LIMIT)));

        //受信タブ 年
        paramMdl.setCir090JYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir090JYear()),
                        String.valueOf(3)));

        //受信タブ 月
        paramMdl.setCir090JMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir090JMonth()),
                        String.valueOf(0)));

        //送信済タブ処理 選択値
        paramMdl.setCir090SdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir090SdelKbn(),
                        String.valueOf(GSConstCircular.CIR_AUTO_DEL_LIMIT)));

        //送信済タブ 年
        paramMdl.setCir090SYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir090SYear()),
                        String.valueOf(3)));

        //送信済タブ 月
        paramMdl.setCir090SMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir090SMonth()),
                        String.valueOf(0)));

        //ゴミ箱タブ処理 選択値
        paramMdl.setCir090DdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getCir090DdelKbn(),
                        String.valueOf(GSConstCircular.CIR_AUTO_DEL_LIMIT)));

        //ゴミ箱タブ 年
        paramMdl.setCir090DYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir090DYear()),
                        String.valueOf(3)));

        //ゴミ箱タブ 月
        paramMdl.setCir090DMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir090DMonth()),
                        String.valueOf(0)));
    }
}
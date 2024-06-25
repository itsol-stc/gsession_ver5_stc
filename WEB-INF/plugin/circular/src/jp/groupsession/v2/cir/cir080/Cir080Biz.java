package jp.groupsession.v2.cir.cir080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAdelDao;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 回覧板 個人設定 自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir080Biz {

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Cir080Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Cir080ParamModel paramMdl, Connection con)
        throws SQLException {

       /*********************************************************
        *
        * 回覧板自動削除設定のリスト・選択値を設定
        *
        *********************************************************/

        GsMessage gsMsg = new GsMessage(reqMdl);

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();

        //年リスト
        for (int i = 0; i <= 5; i++) {
            yearLabel.add(new LabelValueBean(
                                gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                                Integer.toString(i)));
        }
        yearLabel.add(new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {"10"}), "10"));
        paramMdl.setCir080YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(new LabelValueBean(
                            gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                            Integer.toString(i)));
        }
        paramMdl.setCir080MonthLabelList(monthLabel);

        CirAdelDao delDao = new CirAdelDao(con);
        CirAdelModel delMdl = delDao.select(sessionUsrSid);
        if (delMdl == null) {
            delMdl = new CirAdelModel();
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

        //受信タブ処理 選択値
        paramMdl.setCir080JdelKbn(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080JdelKbn()),
                        String.valueOf(delMdl.getCadJdelKbn())));

        //受信タブ 年
        paramMdl.setCir080JYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080JYear()),
                        String.valueOf(delMdl.getCadJdelYear())));

        //受信タブ 月
        paramMdl.setCir080JMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080JMonth()),
                        String.valueOf(delMdl.getCadJdelMonth())));

        //送信済タブ処理 選択値
        paramMdl.setCir080SdelKbn(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080SdelKbn()),
                        String.valueOf(delMdl.getCadSdelKbn())));

        //送信済タブ 年
        paramMdl.setCir080SYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080SYear()),
                        String.valueOf(delMdl.getCadSdelYear())));

        //送信済タブ 月
        paramMdl.setCir080SMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080SMonth()),
                        String.valueOf(delMdl.getCadSdelMonth())));

        //ゴミ箱タブ処理 選択値
        paramMdl.setCir080DdelKbn(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080DdelKbn()),
                        String.valueOf(delMdl.getCadDdelKbn())));

        //ゴミ箱タブ 年
        paramMdl.setCir080DYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080DYear()),
                        String.valueOf(delMdl.getCadDdelYear())));

        //ゴミ箱タブ 月
        paramMdl.setCir080DMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir080DMonth()),
                        String.valueOf(delMdl.getCadDdelMonth())));
    }

    /**
     * <br>[機  能] 自動削除設定(個人)が使用可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret true:使用可能 false:使用不可
     * @throws SQLException SQL実行例外
     */
    public boolean canUseAutoDeleteSetting() throws SQLException {

        boolean ret = true;

        CirAdelDao adelDao = new CirAdelDao(con__);
        //ユーザSID = 0 は管理者設定データ
        CirAdelModel adelMdl = adelDao.select(0);

        if (adelMdl != null) {
            //自動削除 = 管理者が設定する
            if (adelMdl.getCadDelKbn() == GSConstCircular.CIR_DEL_KBN_ADM_SETTING) {
                ret = false;
            }
        }

        return ret;
    }
}
package jp.groupsession.v2.rsv.rsv130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能]施設予約 管理者設定 手動データ削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv130Biz {
    /** コネクション */
    protected Connection con_ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Rsv130Biz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl_ = reqMdl;
    }


    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv130ParamModel
     * @throws SQLException 例外
     */
    public void initDsp(Rsv130ParamModel paramMdl) throws SQLException {

        //コンボセット
        ArrayList<LabelValueBean> rsv130yearLabelList = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> rsv130monthLabelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        //年リスト
        for (int i = 0; i <= 10; i++) {
            rsv130yearLabelList.add(
                new LabelValueBean(
                   gsMsg.getMessage("cmn.year",
                           new String[] {String.valueOf(i)}), Integer.toString(i)));
        }
        //月リスト
        for (int i = 0; i < 12; i++) {
            rsv130monthLabelList.add(
                new LabelValueBean(
                   gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                   Integer.toString(i)));
        }
        paramMdl.setRsv130yearLabelList(rsv130yearLabelList);
        paramMdl.setRsv130monthLabelList(rsv130monthLabelList);
    }
}

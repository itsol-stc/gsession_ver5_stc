package jp.groupsession.v2.rsv.rsv120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 自動データ削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv120Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv120Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Rsv120Biz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv120ParamModel
     * @throws SQLException 例外
     */
    public void initDsp(Rsv120ParamModel paramMdl) throws SQLException {

        //バッチ処理実行時間を取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con_);
        CmnBatchJobModel batMdl = batDao.select();
        String batchTime = "";
        if (batMdl != null) {
            batchTime = String.valueOf(batMdl.getBatFrDate());
        }
        paramMdl.setRsv120BatchTime(batchTime);

        //コンボデータセット
        setRsv120CombData(paramMdl);

        //もし、初期表示フラグがoffならば、
        if (paramMdl.getRsv120initDspFlg() == 0) {
            log__.debug("初期表示デフォルト設定を取得します");
            //& 初期表示フラグをon
            paramMdl.setRsv120initDspFlg(1);
            paramMdl.setRsv120year(GSConstReserve.COMBO_DEFAULT_VALUE);
            paramMdl.setRsv120month(GSConstReserve.COMBO_DEFAULT_VALUE);
            paramMdl.setRsv120batchKbn(GSConstReserve.RSV_RADIO_OFF);
            RsvAdmConfDao dao = new RsvAdmConfDao(con_);
            RsvAdmConfModel model = new RsvAdmConfModel();
            //もし、検索結果が無ければ、
            model = dao.select();
            if (model != null) {
                log__.debug("モデルから表示項目値を取得します");
                //モデルから表示項目値を取得
                paramMdl.setRsv120year(model.getRacAdlYear());
                paramMdl.setRsv120month(model.getRacAdlMonth());
                paramMdl.setRsv120batchKbn(model.getRacAdlKbn());
            }
        }
    }

    /**
     * <br>[機  能] コンボデータを設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv120ParamModel
     */
    public void setRsv120CombData(Rsv120ParamModel paramMdl) {
        ArrayList<LabelValueBean> rsv120yearLabelList = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> rsv120monthLabelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl_);
        //年リスト
        for (int nLabel : GSConst.DEL_YEAR_DATE) {
            String label = String.valueOf(nLabel);
            rsv120yearLabelList.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {label}), label));
        }
        //月リスト
        for (int month : GSConst.DEL_MONTH_DATE) {
            rsv120monthLabelList.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(month)}),
                    Integer.toString(month)));
        }
        paramMdl.setRsv120yearLabelList(rsv120yearLabelList);
        paramMdl.setRsv120monthLabelList(rsv120monthLabelList);

    }
}

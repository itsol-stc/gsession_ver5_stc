package jp.groupsession.v2.mem.mem030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.biz.MemCommonBiz;
import jp.groupsession.v2.mem.dao.MemoAdmConfDao;
import jp.groupsession.v2.mem.model.MemoAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メモ 管理者設定 自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem030Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Mem030Biz.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Mem030Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con Connection
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Mem030ParamModel paramMdl, Connection con) throws SQLException {
        //データベースから現在の設定を取得する。無いときはデフォルト
        MemCommonBiz biz = new MemCommonBiz(reqMdl__);
        MemoAdmConfModel conf = biz.getAdmConfModel(con);
        paramMdl.setMem030Kbn(conf.getMacAtdelKbn());
        paramMdl.setMem030Year(conf.getMacAtdelY());
        paramMdl.setMem030Month(conf.getMacAtdelM());

        //初期表示が終わる
        paramMdl.setMem030InitFlg(GSConstMemo.NOT_INIT_FLG);
    }

    /**
     * <br>[機  能] 表示用データの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行エラー
     */
    protected void _setDisplayData(Mem030ParamModel paramMdl,
            Connection con, RequestModel reqMdl) throws SQLException {

        //バッチ処理実行時間を取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();
        String batchTime = "";
        if (batMdl != null) {
            batchTime = String.valueOf(batMdl.getBatFrDate());
        }
        paramMdl.setBatchTime(batchTime);
        setLabel(reqMdl, paramMdl);
    }

    /**
     * <br>[機  能] 自動削除設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl ParamModel
     * @param umodel ユーザモデル
     * @param con コネクション
     * @return メモ管理者設定モデル
     * @throws SQLException SQL実行エラー
     */
    public MemoAdmConfModel setAutoDelete(Mem030ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        MemoAdmConfModel macMdl = new MemoAdmConfModel();

        //管理者設定自動削除設定登録(更新)Model
        __getAdmConfModel(paramMdl, umodel, macMdl);

        boolean commitFlg = false;
        try {
            MemoAdmConfDao dao = new MemoAdmConfDao(con);
            int count = dao.updateDelConf(macMdl);
            if (count <= 0) {
                dao.insert(macMdl);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("自動削除設定の登録に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
        return macMdl;
    }

    /**
     * <br>[機  能] 新規登録または更新用のMemoLabelModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl ParamModel
     * @param umodel ユーザモデル
     * @param macMdl メモ管理者設定モデル
     * @throws SQLException SQL実行エラー
     */
    private void __getAdmConfModel(
            Mem030ParamModel paramMdl,
            BaseUserModel umodel,
            MemoAdmConfModel macMdl) throws SQLException {

        UDate now = new UDate();

        macMdl.setMacAtdelKbn(paramMdl.getMem030Kbn());
        macMdl.setMacAtdelY(paramMdl.getMem030Year());
        macMdl.setMacAtdelM(paramMdl.getMem030Month());
        macMdl.setMacAuid(umodel.getUsrsid());
        macMdl.setMacAdate(now);
        macMdl.setMacEuid(umodel.getUsrsid());
        macMdl.setMacEdate(now);
    }

    /**
     * <br>[機  能] 年月を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     */
    public void setLabel(RequestModel reqMdl, Mem030ParamModel paramMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);

        //年ラベル作成
        ArrayList<LabelValueBean> mem030AtdelYearLabel__ = new ArrayList<LabelValueBean>();
        for (int nLabel : GSConst.DEL_YEAR_DATE) {
            String label = String.valueOf(nLabel);
            mem030AtdelYearLabel__.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {label}), label));
        }
        paramMdl.setMem030AtdelYearLabel(mem030AtdelYearLabel__);

        // 月ラベル作成
        ArrayList<LabelValueBean> mem030AtdelMonthLabel__ = new ArrayList<LabelValueBean>();
        for (int month : GSConst.DEL_MONTH_DATE) {
            mem030AtdelMonthLabel__.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(month)}),
                    Integer.toString(month)));
        }
        paramMdl.setMem030AtdelMonthLabel(mem030AtdelMonthLabel__);
    }
}
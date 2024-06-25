package jp.groupsession.v2.enq.enq950kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 管理者設定 アンケート手動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq950knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq950knBiz.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq950knBiz() {
    }

    /**
     * <br>[機  能] アンケート手動削除を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq950knModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void doCommit(Enq950knParamModel enq950knModel,
                         RequestModel reqMdl,
                         Connection con) throws SQLException {

        log__.debug("アンケート手動削除実行処理");

        EnqCommonBiz enqBiz = new EnqCommonBiz();
        boolean commitFlg = false;

        // セッションユーザ情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 更新処理
        try {
            con.setAutoCommit(false);

            UDate sendDate = new UDate();
            UDate draftDate = sendDate.cloneUDate();
            UDate now = sendDate.cloneUDate();

            // 発信フォルダ手動削除
            if (enq950knModel.getEnq950SendDelKbn().equals(
                    String.valueOf(GSConstEnquete.DELETE_KBN_ON))) {

                // 公開期限日の閾値取得する
                int year = NullDefault.getInt(
                        enq950knModel.getEnq950SelectSendYear(), GSConstEnquete.YEAR_LABEL[6]);
                int month = NullDefault.getInt(
                        enq950knModel.getEnq950SelectSendMonth(), GSConstEnquete.MONTH_LABEL[11]);
                sendDate = enqBiz.getThresholdUDate(-year, -month, sendDate);

                enqBiz.deleteSendEnq(con, sendDate, now, sessionUsrSid);
            }

            // 草稿フォルダ手動削除
            if (enq950knModel.getEnq950DraftDelKbn().equals(
                    String.valueOf(GSConstEnquete.DELETE_KBN_ON))) {

                // 更新日時の閾値を取得する
                int year = NullDefault.getInt(
                        enq950knModel.getEnq950SelectDraftYear(), GSConstEnquete.YEAR_LABEL[6]);
                int month = NullDefault.getInt(
                        enq950knModel.getEnq950SelectDraftMonth(), GSConstEnquete.MONTH_LABEL[11]);
                draftDate = enqBiz.getThresholdUDate(-year, -month, draftDate);

                enqBiz.deleteDraftEnq(con, draftDate, now, sessionUsrSid);
            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("アンケート手動削除に失敗しました。" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
            con.setAutoCommit(true);
        }
    }
}

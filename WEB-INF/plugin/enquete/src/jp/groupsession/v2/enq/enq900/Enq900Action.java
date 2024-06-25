package jp.groupsession.v2.enq.enq900;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.enq.AbstractEnqueteAdminAction;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq900Action extends AbstractEnqueteAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq900Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        log__.debug("Enq900Action_START");

        ActionForward forward = null;
        Enq900Form enq900Form = (Enq900Form) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("enq900makeUser")) {
            // アンケート作成対象者設定
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq900syurui")) {
            // アンケート種類設定
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq900dsp")) {
            //表示設定
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq900autoDel")) {
            // 自動削除
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq900del")) {
            // 手動削除
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq900mngEnq")) {
            // 発信アンケート管理
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq900back")) {
            // 戻る
            forward = __doBack(map, enq900Form);

        } else {
            //初期表示処理
            forward = __doInit(map, enq900Form, req, res, con);
        }

        log__.debug("Enq900Action_END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq900Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException {

        log__.debug("初期表示処理");

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 後戻処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doBack(ActionMapping map, Enq900Form form) throws Exception {

        log__.debug("後戻処理");

        String fwdName = "";
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            // メインの管理者設定画面
            fwdName = "mainAdmSetting";
        } else {
            fwdName = "enq900backTo010";
        }

        return map.findForward(fwdName);
    }
}

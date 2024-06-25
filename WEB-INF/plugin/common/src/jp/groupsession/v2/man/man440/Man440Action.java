package jp.groupsession.v2.man.man440;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.rsv.rsv050.Rsv050Form;
import jp.groupsession.v2.struts.AdminAction;


/**
 *
 * <br>[機  能]サイボウズLiveデータ取り込み機能ガイド画面
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man440Action extends AdminAction {
    /** Logging インスタンス */
    //private static Log log__ = LogFactory.getLog(Man440Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        Man440Form thisForm = (Man440Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("440_back")) {
            //メイン管理者設定に戻る
            forward = map.findForward("440_back");
        } else if (cmd.equals("grouplist")) {
            //グループマネージャーへ遷移
            forward = map.findForward("grouplist");
        } else if (cmd.equals("sisetuSettei")) {
            //施設設定へ遷移
            forward = __doMoveSisetsuList(map, req);
        } else if (cmd.equals("fileManager")) {
            //ファイル管理へ遷移
            forward = map.findForward("fileManager");
        } else if (cmd.equals("memberImport")) {
            //メンバー名簿インポート画面へ
            forward = map.findForward("memberImport");
        } else if (cmd.equals("eventImport")) {
            //イベント情報インポート画面へ
            forward = map.findForward("eventImport");
        } else if (cmd.equals("boardImport")) {
            //掲示板インポート画面へ
            forward = map.findForward("boardImport");
        } else if (cmd.equals("todoImport")) {
            //ToDoリストインポート画面へ
            forward = map.findForward("todoImport");
        } else {
            //初期表示
            forward = __doDsp(thisForm, map, req, con);
        }

        return forward;
    }

    /**
     * <br>[機  能]表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param thisForm アクションフォーム
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOTools実行例外
     */
    private ActionForward __doDsp(Man440Form thisForm,
            ActionMapping map,
            HttpServletRequest req,
            Connection con) throws SQLException, IOToolsException {

        ActionForward forward = null;
        Man440ParamModel paramMdl = new Man440ParamModel();
        Man440Biz biz = new Man440Biz(getRequestModel(req));

        //初期表示用のデータを取得
        paramMdl.setParam(thisForm);
        biz.setInitData(getRequestModel(req), paramMdl, con, this.getAppRootPath());
        paramMdl.setFormData(thisForm);

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 施設一覧画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doMoveSisetsuList(ActionMapping map,
                                           HttpServletRequest req) throws Exception {

        Rsv050Form nextForm = new Rsv050Form();

        // 施設一覧から戻る際にエラーになる為、ひとまず施設予約メイン画面へ遷移できるよう対応
        UDate now = new UDate();
        nextForm.setRsvDspFrom(now.getStrYear() + now.getStrMonth() + now.getStrDay());
        req.setAttribute("rsv050Form", nextForm);

        return map.findForward("sisetuSettei");
    }
}

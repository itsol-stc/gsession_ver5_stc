package jp.groupsession.v2.zsk.zsk080;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 在席管理 個人設定 初期表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk080Action extends AbstractZaisekiAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk080Action.class);

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Zsk080Form zskForm = (Zsk080Form) form;
        if (cmd.equals("zsk070")) {
            //戻る
            forward = map.findForward("zsk070");
        } else if (cmd.equals("zsk080kn")) {
            //初期表示確認
            forward = __doUpdatePriConf(map, zskForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * 初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, Zsk080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        resetToken(req);
        Zsk080Biz biz = new Zsk080Biz(getRequestModel(req));

        Zsk080ParamModel paramMdl = new Zsk080ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * 入力チェックを行い確認画面へ遷移する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 再表示に失敗
     */
    private ActionForward __doUpdatePriConf(ActionMapping map, Zsk080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //トークンをセーブ
        saveToken(req);
        forward = map.findForward("zsk080kn");
        return forward;
    }
}

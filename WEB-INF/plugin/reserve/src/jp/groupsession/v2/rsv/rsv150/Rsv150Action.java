package jp.groupsession.v2.rsv.rsv150;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.rsv.AbstractReserveAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 施設予約 個人設定 表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv150Action extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv150Action.class);

    /**
    * <br>[機  能] アクションを実行する
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @return ActionForward
    * @throws Exception 実行時例外
    */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Rsv150Form rsvform = (Rsv150Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //OKボタン押下
        if (cmd.equals("hyozi_settei_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, rsvform, req, res, con);
        //個人設定へ戻るボタン押下
        } else if (cmd.equals("back_to_kojn_menu")) {
            log__.debug("個人設定へ戻るボタン押下");
            forward = map.findForward("back_to_kojn_menu");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rsv150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        con.setAutoCommit(true);
        Rsv150Biz biz = new Rsv150Biz(con, getRequestModel(req));

        Rsv150ParamModel paramMdl = new Rsv150ParamModel();
        paramMdl.setParam(form);
        biz.initDsp(paramMdl, getSessionUserSid(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doOk(ActionMapping map,
                                    Rsv150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        saveToken(req);
        return map.findForward("hyozi_settei_kakunin");
    }
}
package jp.groupsession.v2.mem.mem060;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.mem.AbstractMemoAction;

/**
 * <br>[機  能] メモ帳 個人設定 ラベル登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem060Action extends AbstractMemoAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Mem060Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Mem060Form thisForm = (Mem060Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        //ユーザSIDを取得
        int usrSid = getSessionUserSid(req);

        //ラベルの存在チェック
        Mem060ParamModel paramMdl = new Mem060ParamModel();
        paramMdl.setParam(form);
        Mem060Biz biz = new Mem060Biz();
        boolean result = biz._existLabelData(con, paramMdl, usrSid);
        if (!result) {
            return getSubmitErrorPage(map, req);
        }

        if (cmd.equals("mem060Next")) {
            //OKボタンクリック
            forward = __doOK(map, thisForm, req, res, con);

        } else if (cmd.equals("mem060Back")) {
            //戻るボタンクリック
            forward = map.findForward("mem060Back");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Mem060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        con.setAutoCommit(true);
        Mem060ParamModel paramMdl = new Mem060ParamModel();
        paramMdl.setParam(form);
        Mem060Biz biz = new Mem060Biz();
        biz._setInitData(con, paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doOK(ActionMapping map, Mem060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
       // トランザクショントークン設定
        this.saveToken(req);

        return map.findForward("mem060Next");
    }
}
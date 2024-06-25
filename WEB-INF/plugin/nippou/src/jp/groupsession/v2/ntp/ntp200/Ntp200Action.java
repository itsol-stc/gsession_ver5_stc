package jp.groupsession.v2.ntp.ntp200;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.ntp061.Ntp061Form;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 案件検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp200Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp200Action.class);
    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        //ダウンロードフラグ
        String downLoadFlg = NullDefault.getString(req.getParameter("csvOut"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("export")) {
                log__.debug("CSVファイルダウンロード");
                return true;
        }
        return false;
    }
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
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp200Form thisForm = (Ntp200Form) form;
        if (cmd.equals("add") || cmd.equals("edit")) {
            log__.debug("*****登録・編集ボタンクリック");
            Ntp061Form ntpForm = new Ntp061Form();
            ntpForm.setNtp061ReturnPage("ntp060");
            req.setAttribute("ntp061Form", ntpForm);
            forward = map.findForward("ntp061");
        } else if (cmd.equals("search")) {
            log__.debug("*****検索ボタンクリック");
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("prevPage")) {
            log__.debug("*****前ページクリック");
            thisForm.setNtp200PageTop(thisForm.getNtp200PageTop() - 1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            log__.debug("*****次ページクリック");
            thisForm.setNtp200PageTop(thisForm.getNtp200PageTop() + 1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("changePage")) {
            log__.debug("*****ページコンボ変更");
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("changeGyomu")) {
            log__.debug("業務変更");
            Ntp200Biz biz = new Ntp200Biz(con, getRequestModel(req));

            Ntp200ParamModel paramMdl = new Ntp200ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, this.getSessionUserModel(req), con);
            paramMdl.setFormData(form);

            forward = map.getInputForward();
        } else {
            log__.debug("*****初期表示");
            forward = __doSearch(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 検索処理
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
    private ActionForward __doSearch(ActionMapping map,
                                    Ntp200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionMessage msg = null;

        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
        Ntp200Biz biz = new Ntp200Biz(con, getRequestModel(req));

        Ntp200ParamModel paramMdl = new Ntp200ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, this.getSessionUserModel(req), con);
        paramMdl.setFormData(form);

        //入力チェック
        ActionErrors errors = form.validateCheck();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        paramMdl = new Ntp200ParamModel();
        paramMdl.setParam(form);
        biz.doSearch(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        log__.debug("*****商品件数 = " + form.getNtp200AnkenList().size());

        if (form.getNtp200AnkenList().size() == 0) {
            msg = new ActionMessage("search.data.notfound", "案件");
            StrutsUtil.addMessage(errors, msg, "ntp060NanSid__");
            addErrors(req, errors);
        }

        return map.getInputForward();
    }

}

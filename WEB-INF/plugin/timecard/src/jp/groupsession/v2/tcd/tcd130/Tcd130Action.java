package jp.groupsession.v2.tcd.tcd130;

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
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;


/**
 * <br>[機  能] ユーザ別時間帯設定一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd130Action extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd130Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        ActionForward forward = null;
        //管理者権限チェック
        CommonBiz commonBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        boolean isAdmin =
                commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstTimecard.PLUGIN_ID_TIMECARD);
        if (!isAdmin) {
            return getNotAdminSeniPage(map, req);
        }

        //コマンド取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        cmd = cmd.trim();

        Tcd130Form thisForm = (Tcd130Form) form;

        if (cmd.equals("tcd130back")) {
            //戻るボタンクリック
            if (thisForm.getTimezoneSid() != -1) {
                forward = map.findForward("tcd060back");
            } else {
                forward = map.findForward("tcd130back");
            }
        } else if (cmd.equals("search")) {
            //検索
            thisForm.setTcdSelectUserlist(null);
            thisForm.setTcdSvSelectUserlist(null);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("prevPage")) {
            //前のページ
            thisForm.setTcd130pageTop(thisForm.getTcd130pageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次のページ
            thisForm.setTcd130pageTop(thisForm.getTcd130pageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("edits")) {
            //編集
            forward = __doEdits(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd130")) {
            //tcd060からの遷移
            thisForm.setTcd130TimezoneSid(thisForm.getTimezoneSid());
            forward = __doSearch(map, thisForm, req, res, con);
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
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Tcd130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        Tcd130Biz biz = new Tcd130Biz(reqMdl);
        Tcd130ParamModel paramMdl = new Tcd130ParamModel();
        paramMdl.setParam(form);
        con.setAutoCommit(true);
        biz._setInitData(paramMdl, con);
        con.setAutoCommit(false);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);


        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索時
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doSearch(ActionMapping map, Tcd130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        form.setTcd130SearchFlg(1);
        form.saveSearchParm();

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 編集
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doEdits(ActionMapping map, Tcd130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        //エラーチェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        return map.findForward("editTimeZone");
    }

}

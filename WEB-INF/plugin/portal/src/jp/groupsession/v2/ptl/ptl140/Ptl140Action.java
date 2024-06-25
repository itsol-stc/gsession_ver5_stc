package jp.groupsession.v2.ptl.ptl140;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlAdmConfDao;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;
import jp.groupsession.v2.ptl.AbstractPortalAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル 個人設定 ポータル管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl140Action extends AbstractPortalAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl140Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return false;
    }

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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
        log__.debug("START");

        ActionForward forward = null;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        //閲覧権限チェック
        if (!_checkPow(con)) {
            return _notPowForward(map, req);
        }

        Ptl140Form thisForm = (Ptl140Form) form;

        if (cmd.equals("menuBack")) {
            //戻るボタンクリック
            forward = map.findForward("menuBack");

        } else if (cmd.equals("sortUp")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, Ptl140Biz.SORT_UP);

        } else if (cmd.equals("sortDown")) {
            //下へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, Ptl140Biz.SORT_DOWN);

        } else if (cmd.equals("changeKbn")) {
            //ポータルデフォルト表示区分変更
            forward = __doChangeKbn(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
                                    Ptl140Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        Ptl140ParamModel paramMdl = new Ptl140ParamModel();
        paramMdl.setParam(form);
        Ptl140Biz biz = new Ptl140Biz();

        con.setAutoCommit(false);
        int usrSid = getSessionUserSid(req);
        biz.initDsp(con, paramMdl, usrSid);
        paramMdl.setFormData(form);
        con.setAutoCommit(true);
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }
        
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 並び順変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng060Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param sortKbn 処理区分
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSortChange(ActionMapping map, Ptl140Form form,
                                        HttpServletRequest req, HttpServletResponse res,
                                        Connection con, int sortKbn)
            throws Exception {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }
        
        boolean commit = false;
        try {
            Ptl140ParamModel paramMdl = new Ptl140ParamModel();
            paramMdl.setParam(form);
            Ptl140Biz biz = new Ptl140Biz();
            biz.updateSort(con, paramMdl, sortKbn, getSessionUserSid(req));
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.info("ポータルの並び順更新に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String edit = gsMsg.getMessage("cmn.edit");
        String sort = gsMsg.getMessage("change.sort.order");

        //ログ出力処理
        PtlCommonBiz rngBiz = new PtlCommonBiz(con);
        rngBiz.outPutLog(
                map, reqMdl,
                edit, GSConstLog.LEVEL_INFO, sort);

        return __doInit(map, form, req, res, con);
    }


    /**
     * <br>[機  能] ポータルデフォルト表示区分変更
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng060Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doChangeKbn(ActionMapping map, Ptl140Form form,
                                        HttpServletRequest req, HttpServletResponse res,
                                        Connection con)
            throws Exception {

        boolean commit = false;
        try {
            Ptl140ParamModel paramMdl = new Ptl140ParamModel();
            paramMdl.setParam(form);
            Ptl140Biz biz = new Ptl140Biz();
            biz.changeKbn(con, paramMdl, getSessionUserSid(req));
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.info("ポータルデフォルト表示区分更新に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String edit = gsMsg.getMessage("cmn.edit");
        String ptlDefDspKbn = gsMsg.getMessage("ptl.ptl140.1");

        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(
                map, reqMdl,
                edit, GSConstLog.LEVEL_INFO, ptlDefDspKbn);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 閲覧権限のない場合の遷移先
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @return forward エラー画面
     * @throws Exception 実行例外
     */
    protected ActionForward _notPowForward(ActionMapping map,
        HttpServletRequest req)
        throws Exception {

        //権限なしの場合はメッセージページのパラメータを設定する
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("menuBack");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.cant.edit.portal"));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 閲覧権限のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 権限の有無 true:権限あり false:権限無し
     * @throws SQLException SQL実行例外
     */
    protected boolean _checkPow(Connection con) throws SQLException {

        log__.debug("閲覧権限チェック START");

        PtlAdmConfDao dao = new PtlAdmConfDao(con);
        PtlAdmConfModel model = dao.select();
        if (model != null
                && (model.getPacPtlEditkbn() == GSConstPortal.EDIT_KBN_PUBLIC 
                || model.getPacDefKbn() == GSConstPortal.EDIT_KBN_PUBLIC)) {
            return true;
        }

        log__.debug("閲覧権限チェック END");

        return false;
    }
}

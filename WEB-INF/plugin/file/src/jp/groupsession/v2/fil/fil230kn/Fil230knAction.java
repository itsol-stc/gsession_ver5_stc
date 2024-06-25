package jp.groupsession.v2.fil.fil230kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 ファイル一括削除確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil230knAction extends AbstractFileAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil230knAction.class);
    /** リクエストモデルストック*/
    private RequestModel reqMdl__;

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
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

        log__.debug("fil230knAction START");

        ActionForward forward = null;
        Fil230knForm thisForm = (Fil230knForm) form;

        //キャビネットSIDで指定されたキャビネットの存在チェック
        String cabSidStr = thisForm.getFil230SltCabinetSid();
        int cabSid = NullDefault.getInt(cabSidStr, -1);
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        if (cabSidStr == null || filBiz.isExistCabinet(cabSid)) {
            getSubmitErrorPage(map, req);
        }

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil230knback")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil230knok")) {
            //OKボタンクリック
            forward = __DoRegister(map, thisForm, req, res, con);

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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil230knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        Fil230knBiz biz = new Fil230knBiz(con, getRequestModel(req));

        Fil230knParamModel paramMdl = new Fil230knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Fil230knForm form) {

        ActionForward forward = null;

        forward = map.findForward("fil230");
        return forward;
    }

    /**
     * <br>[機  能] 削除処理
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
    private ActionForward __DoRegister(ActionMapping map,
                                    Fil230knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //アプリケーションルートパス
        String appRootPath = getAppRootPath();

        Fil230knBiz biz = new Fil230knBiz(con, getRequestModel(req));
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        boolean commitFlg = false;
        String dirName = "";
        Fil230knParamModel paramMdl = new Fil230knParamModel();
        try {

            //フォルダ名を取得
            dirName = filBiz.getDirctoryPath(
                    NullDefault.getInt(form.getFil230DeleteDirSid(), 0));

            //ファイルの削除を行う。
            paramMdl.setParam(form);
            biz.deleteData(paramMdl, appRootPath);
            paramMdl.setFormData(form);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textDel = gsMsg.getMessage(req, "cmn.delete");

        //ログ出力処理
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "fil.36"));
        sb.append("] ");
        sb.append(dirName);
        int deleteOpt = NullDefault.getInt(paramMdl.getFil230DeleteOpt(), -1);
        if (deleteOpt == GSConstFile.DELETE_OPTION_FOLDER_FILE) {
            sb.append("\r\n");
            sb.append(" ");
            sb.append(gsMsg.getMessage(req, "fil.37"));
        }


        filBiz.outPutLog(textDel,
                GSConstLog.LEVEL_INFO, sb.toString(), map.getType());

        //削除完了画面を設定する。
        return __setCompPageParam(map, req, form);
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward アクションフォワード
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Fil230knForm form) {

        GsMessage gsMsg = new GsMessage();
        String textDelFile = gsMsg.getMessage(req, "fil.27");

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil200");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "cmn.kanryo.object";

        cmn999Form.setMessage(msgRes.getMessage(msgState, textDelFile));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil040SelectDel", form.getFil040SelectDel());
        cmn999Form.addHiddenParam("fil010SelectDelLink", form.getFil010SelectDelLink());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
    /**
    *
    * <br>[機  能] リクエストの情報を返す
    * <br>[解  説]
    * <br>[備  考] 前回のリクエストモデルが再利用される
    * @param req リクエスト
    * @return RequestModel
    */
    public RequestModel getRequestModel(HttpServletRequest req) {
        if (reqMdl__ != null) {
            return reqMdl__;
        }
        return super.getRequestModel(req);
    }
}
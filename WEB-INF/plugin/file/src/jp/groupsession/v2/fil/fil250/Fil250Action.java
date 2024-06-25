package jp.groupsession.v2.fil.fil250;

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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;

/**
 * <br>[機  能] 管理者設定 更新通知一括設定画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil250Action extends AbstractFileAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil250Action.class);

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

        log__.debug("fil250Action START");

        ActionForward forward = null;
        Fil250Form thisForm = (Fil250Form) form;
        
        int cabinetKbn = thisForm.getFil250cabinetKbn();
        if (cabinetKbn != GSConstFile.CABINET_KBN_PUBLIC
                && cabinetKbn != GSConstFile.CABINET_KBN_ERRL) {
            return getSubmitErrorPage(map, req);
        }
        FilCommonBiz biz = new FilCommonBiz(getRequestModel(req), con);
        String cabinetSidStr = thisForm.getFil250SltCabinetSid();
        if (cabinetSidStr != null && !cabinetSidStr.equals("-1")) {
            int cabinetSid = NullDefault.getInt(cabinetSidStr, -1);
            if (!biz.checkCabinetDeleteKbn(cabinetSid)
                    || biz.getPersonalFlg(cabinetSid) == GSConstFile.CABINET_KBN_PRIVATE) {
                return getSubmitErrorPage(map, req);
            }
        }

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil250back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil250ok")) {
            //OKボタンクリック
            forward = __doOK(map, thisForm, req, res, con);
        } else if (cmd.equals("detailDir")) {
            //フォルダ名クリック
            forward = __doChangeFolder(map, thisForm, req, res, con);
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
                                    Fil250Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        Fil250Biz biz = new Fil250Biz(con, getRequestModel(req));

        Fil250ParamModel paramMdl = new Fil250ParamModel();
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
    private ActionForward __doBack(ActionMapping map, Fil250Form form) {

        ActionForward forward = null;

        forward = map.findForward("fil200");
        return forward;
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理
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
    private ActionForward __doOK(ActionMapping map,
                                    Fil250Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateCheck(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);

        return map.findForward("fil250kn");
    }

    /**
     * <br>[機  能] フォルダ名クリック時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doChangeFolder(
        ActionMapping map,
        Fil250Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {
        
        ActionErrors errors = form.validateCheckDir(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        }

        //指定されたフォルダに更新通知設定しているユーザを取得する
        Fil250Biz biz = new Fil250Biz(con, getRequestModel(req));

        Fil250ParamModel paramMdl = new Fil250ParamModel();
        paramMdl.setParam(form);
        form.setFil250SvCallUser(biz.getCallUser(paramMdl));
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con);
    }
}


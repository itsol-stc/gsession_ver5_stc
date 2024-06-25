package jp.groupsession.v2.fil.fil230;

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
 * <br>[機  能] 管理者設定 ファイル一括削除画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil230Action extends AbstractFileAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil230Action.class);

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

        log__.debug("fil230Action START");

        ActionForward forward = null;
        Fil230Form thisForm = (Fil230Form) form;

        //キャビネットSIDで指定されたキャビネットの存在チェック
        String cabSidStr = thisForm.getFil230SltCabinetSid();
        int cabSid = NullDefault.getInt(cabSidStr, -1);
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        if (cabSidStr == null || filBiz.isExistCabinet(cabSid)) {
            getSubmitErrorPage(map, req);
        }

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil230back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil230ChangeCabType")) {
            //キャビネット種別変更
            forward = __doChangeCabinetType(map, thisForm, req, res, con);

        } else if (cmd.equals("changeCabinet")) {
            //キャビネット選択
            forward = __doSelectCabinet(map, thisForm, req, res, con);

        } else if (cmd.equals("fil230selectDir")) {
            //フォルダ選択
            forward = __doSelectFolder(map, thisForm, req, res, con);

        } else if (cmd.equals("fil230delete")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

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
                                    Fil230Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        Fil230Biz biz = new Fil230Biz(con, getRequestModel(req));

        Fil230ParamModel paramMdl = new Fil230ParamModel();
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
    private ActionForward __doBack(ActionMapping map, Fil230Form form) {

        ActionForward forward = null;

        forward = map.findForward("fil200");
        return forward;
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理
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
    private ActionForward __doDelete(ActionMapping map,
                                    Fil230Form form,
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

        return map.findForward("fil230kn");
    }

    /**
     * <br>[機  能] キャビネット選択時の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doSelectCabinet(ActionMapping map,
                                    Fil230Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCabinet(errors, req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setFil230SltCabinetSid(null);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] キャビネット種別変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doChangeCabinetType(ActionMapping map,
                                    Fil230Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCabinetType(errors, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setFil230cabinetKbn(GSConstFile.CABINET_KBN_PRIVATE);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] フォルダ選択時の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doSelectFolder(ActionMapping map,
                                    Fil230Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateFolder(errors, getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setFil230DeleteDirSid(null);
        }

        return __doInit(map, form, req, res, con);
    }
}


package jp.groupsession.v2.fil.fil250kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 更新通知一括設定確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil250knAction extends AbstractFileAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil250knAction.class);

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

        log__.debug("fil250knAction START");

        ActionForward forward = null;
        Fil250knForm thisForm = (Fil250knForm) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil250knback")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil250knok")) {
            //OKボタンクリック
            forward = __doRegister(map, thisForm, req, res, con);

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
                                    Fil250knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        Fil250knBiz biz = new Fil250knBiz(con, getRequestModel(req));

        Fil250knParamModel paramMdl = new Fil250knParamModel();
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
    private ActionForward __doBack(ActionMapping map, Fil250knForm form) {

        ActionForward forward = null;

        forward = map.findForward("fil250");
        return forward;
    }

    /**
     * <br>[機  能] 登録処理
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
    private ActionForward __doRegister(ActionMapping map,
                                    Fil250knForm form,
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

        boolean commitFlg = false;
        Fil250knParamModel paramMdl = new Fil250knParamModel();
        paramMdl.setParam(form);
        try {
            //更新通知設定を行う。
            Fil250knBiz biz = new Fil250knBiz(con, getRequestModel(req));

            biz.entryCallUser(paramMdl);
            paramMdl.setFormData(form);

            con.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        GsMessage gsMsg = new GsMessage();
        String textDel = gsMsg.getMessage(req, "fil.124");

        //フォルダ名を取得
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        String dirName = filBiz.getDirctoryName(
                NullDefault.getInt(form.getFil250DirSid(), 0));

        //ログ出力処理
        String value = "";
        // フォルダ
        value += "[" + gsMsg.getMessage("cmn.folder") + "] " + dirName;
        // 更新通知対象者
        value += "\r\n" + "[" + gsMsg.getMessage("fil.125") + "]\r\n";
        CmnUsrmDao userDao = new CmnUsrmDao(con);
        List<BaseUserModel> userList = userDao.getSelectedUserList(paramMdl.getFil250SvCallUser());
        for (int i = 0; i < userList.size(); i++) {
            if (i > 0) {
                value += "\r\n";
            }
            value += "・" + userList.get(i).getUsiseimei();
        }
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                          + gsMsg.getMessage("fil.fil250kn.1");
        filBiz.outPutLogNoDspName(textDel,
                GSConstLog.LEVEL_INFO, value, map.getType(), null, dspName);

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
        Fil250knForm form) {

        GsMessage gsMsg = new GsMessage();
        String textDelFile = gsMsg.getMessage(req, "fil.124");

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
}

package jp.groupsession.v2.fil.fil210kn;

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
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 基本設定確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil210knAction extends AbstractFileAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil210knAction.class);

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

        log__.debug("fil210knAction START");

        ActionForward forward = null;
        Fil210knForm thisForm = (Fil210knForm) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil210knback")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil210knok")) {
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
                                    Fil210knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        Fil210knBiz biz = new Fil210knBiz(con, getRequestModel(req));

        Fil210knParamModel paramMdl = new Fil210knParamModel();
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
    private ActionForward __doBack(ActionMapping map, Fil210knForm form) {

        ActionForward forward = null;

        forward = map.findForward("fil210");
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __DoRegister(ActionMapping map,
                                    Fil210knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil210knBiz biz = new Fil210knBiz(con, getRequestModel(req));
        boolean commitFlg = false;
        Fil210knParamModel paramMdl = new Fil210knParamModel();
        paramMdl.setParam(form);
        try {

            biz.registerData(paramMdl, buMdl);
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

        GsMessage gsMsg = new GsMessage();
        String textEdit = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        String value = "";
        // キャビネット作成権限
        value += "[" + gsMsg.getMessage("fil.28") + "] ";
        String[] target = {
                gsMsg.getMessage("fil.fil210.1"),
                gsMsg.getMessage("group.designation"),
                gsMsg.getMessage("cmn.user.specified")
        };
        int cabinetKbn = Integer.parseInt(paramMdl.getFil210CrtKbn());
        value += target[cabinetKbn];
        if (cabinetKbn == GSConstFile.CREATE_CABINET_PERMIT_GROUP) {
            value += gsMsg.getMessage("fil.fil210.2") + ":";
            CmnGroupmDao groupDao = new CmnGroupmDao(con);

            int[] sidList = new int[paramMdl.getFil210SvGroups().length];
            int index = 0;
            for (String sid : paramMdl.getFil210SvGroups()) {
                sidList[index] = Integer.parseInt(sid);
                index++;
            }
            List<CmnGroupmModel> groupList
                = groupDao.selectFromSid(sidList, GSConst.JTKBN_TOROKU);
            for (int i = 0; i < groupList.size(); i++) {
                if (i > 0) {
                    value += "\r\n";
                }
                value += "・" + groupList.get(i).getGrpName();
            }
        } else if (cabinetKbn == GSConstFile.CREATE_CABINET_PERMIT_USER) {
            value += gsMsg.getMessage("fil.fil210.2") + ":";
            CmnUsrmDao userDao = new CmnUsrmDao(con);
            List<BaseUserModel> userList = userDao.getSelectedUserList(paramMdl.getFil210SvUsers());
            for (int i = 0; i < userList.size(); i++) {
                if (i > 0) {
                    value += "\r\n";
                }
                value += "・" + userList.get(i).getUsiseimei();
            }
        }
        // ファイルのサイズ制限
        value += "\r\n";
        value += "[" + gsMsg.getMessage("fil.31") + "] ";
        value += paramMdl.getFil210FileSize() + "MB";
        // 削除したファイルを保存する期間
        value += "\r\n[" + gsMsg.getMessage("fil.67") + "] ";
        int saveDays = Integer.parseInt(paramMdl.getFil210SaveDays());
        if (saveDays == GSConstFile.FILE_SAVE_DAYS_NO) {
            value += gsMsg.getMessage("fil.fil210.6");
        } else {
            value += saveDays + gsMsg.getMessage("cmn.days2");
        }
        // ロック機能
        String[] avail = {
                gsMsg.getMessage("fil.107"),
                gsMsg.getMessage("fil.108")
        };
        value += "\r\n";
        value += "[" + gsMsg.getMessage("fil.34") + "] ";
        value += avail[Integer.parseInt(paramMdl.getFil210LockKbn())];
        // バージョン管理機能
        value += "\r\n";
        value += "[" + gsMsg.getMessage("fil.69") + "] ";
        value += avail[Integer.parseInt(paramMdl.getFil210VerKbn())];

        filBiz.outPutLog(textEdit, GSConstLog.LEVEL_INFO, value, map.getType());

        //登録完了画面を設定する。
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
        Fil210knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil200");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";

        GsMessage gsMsg = new GsMessage();
        String textBaseConf = gsMsg.getMessage(req, "cmn.preferences");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textBaseConf));

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


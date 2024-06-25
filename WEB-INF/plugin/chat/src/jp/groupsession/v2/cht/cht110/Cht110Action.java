package jp.groupsession.v2.cht.cht110;

import java.sql.Connection;
import java.util.ArrayList;
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
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtGroupUserDao;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャットグループ追加/編集 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht110Action extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht110Action.class);

    /**
    *
    * <br>[機  能] アクション実行前の事前処理 アクセス制限を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @return ActionForward
    * @throws Exception 実行時例外
    */

   protected ActionForward _immigration(ActionMapping map, Cht110Form form,
           HttpServletRequest req) throws Exception {
       if (form.getCht100ProcMode() < GSConstChat.CHAT_MODE_ADD
               || form.getCht100ProcMode() > GSConstChat.CHAT_MODE_EDIT) {
           return getSubmitErrorPage(map, req);
       }
       return null;
   }

    /**
     * <br>アクション実行
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
        Cht110Form thisForm = (Cht110Form) form;
        // アクセス制限
        ActionForward forward  = _immigration(map, thisForm, req);
        if (forward != null) {
            return forward;
        }
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        if (cmd.equals("ok")) {
            log__.debug("確認画面へ遷移");
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("backToList")) {
            log__.debug("グループ管理へ戻る");
            forward = map.findForward("cht100");
        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタンクリック。");
            forward = __setDeleteDsp(map, req, thisForm, con, res);
        } else if (cmd.equals("delexe")) {
            log__.debug("削除確認OK。");
            forward = __doDelete(map, thisForm, req, res, con);
        }  else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht110Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht110ParamModel paramMdl = new Cht110ParamModel();
        paramMdl.setParam(form);
        Cht110Biz biz = new Cht110Biz(con, getRequestModel(req));
        int usrSid = getRequestModel(req).getSmodel().getUsrsid();
        biz.setInitData(paramMdl, usrSid);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht110Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doOk(ActionMapping map,
                                Cht110Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {

        ChtBiz chtBiz = new ChtBiz(con);
        //　存在チェック
        if (form.getCht100ProcMode() == GSConstChat.CHAT_MODE_EDIT) {
            boolean chk = chtBiz.existChtGroup(form.getCgiSid());
            if (!chk) {
                return getSubmitErrorPage(map, req);
            }
        }
        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCht110(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("cht110kn");
    }

    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param con Connection
     * @param res HttpServletResponse
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __setDeleteDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Cht110Form form,
                                          Connection con,
                                          HttpServletResponse res) throws Exception {

        ChtBiz chtBiz = new ChtBiz(con);
        //削除チェック
        boolean chk = chtBiz.existChtGroup(form.getCgiSid());
        if (!chk) {
            return getSubmitErrorPage(map, req);
        }

        GsMessage gsMsg = new GsMessage(req);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("cht110");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=delexe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("cht110");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        //メッセージ
        MessageResources msgRes = getResources(req);
        String msg = "";
        String chat =  gsMsg.getMessage(req, "cmn.group");
        ChtGroupInfDao infDao = new ChtGroupInfDao(con);
        ChtGroupInfModel infMdl
                = infDao.select(form.getCgiSid());
        msg = msgRes.getMessage("sakujo.kakunin.list", chat,
                StringUtilHtml.transToHTmlPlusAmparsant(infMdl.getCgiName()));
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());
        cmn999Form.addHiddenParam("cgiSid", form.getCgiSid());
        cmn999Form.addHiddenParam("cht110groupId", form.getCht110groupId());
        cmn999Form.addHiddenParam("cht110groupName", form.getCht110groupName());
        cmn999Form.addHiddenParam("cht110category", form.getCht110category());
        cmn999Form.addHiddenParam("cht110createDate", form.getCht110createDate());
        cmn999Form.addHiddenParam("cht110updateDate", form.getCht110updateDate());
        cmn999Form.addHiddenParam("cht110biko", form.getCht110biko());
        cmn999Form.addHiddenParam("cht110InitFlg", form.getCht110InitFlg());
        cmn999Form.addHiddenParam("cht100ProcMode", form.getCht100ProcMode());
        cmn999Form.addHiddenParam("cht100SortKey", form.getCht100SortKey());
        cmn999Form.addHiddenParam("cht100OrderKey", form.getCht100OrderKey());
        cmn999Form.addHiddenParam("cht100PageTop", form.getCht100PageTop());
        cmn999Form.addHiddenParam("cht100PageBottom", form.getCht100PageBottom());
        cmn999Form.addHiddenParam("cht100SearchFlg", form.getCht100SearchFlg());

        cmn999Form.addHiddenParam("cht100InitFlg", form.getCht100InitFlg());
        cmn999Form.addHiddenParam("cht100Keyword", form.getCht100Keyword());
        cmn999Form.addHiddenParam("cht100AndOr", form.getCht100AndOr());
        cmn999Form.addHiddenParam("cht100GroupId", form.getCht100GroupId());
        cmn999Form.addHiddenParam("cht100GroupName", form.getCht100GroupName());
        cmn999Form.addHiddenParam("cht100GroupInfo", form.getCht100GroupInfo());
        cmn999Form.addHiddenParam("cht100Category", form.getCht100Category());
        cmn999Form.addHiddenParam("cht100StatusKbn", form.getCht100StatusKbn());
        cmn999Form.addHiddenParam("cht100SelectGroup", form.getCht100SelectGroup());
        cmn999Form.addHiddenParam("cht100SelectUser", form.getCht100SelectUser());
        cmn999Form.addHiddenParam("cht100AdminMember", form.getCht100AdminMember());
        cmn999Form.addHiddenParam("cht100GeneralMember", form.getCht100GeneralMember());
        cmn999Form.addHiddenParam("cht100CreateYearFr", form.getCht100CreateYearFr());
        cmn999Form.addHiddenParam("cht100CreateMonthFr", form.getCht100CreateMonthFr());
        cmn999Form.addHiddenParam("cht100CreateDayFr", form.getCht100CreateDayFr());
        cmn999Form.addHiddenParam("cht100CreateYearTo", form.getCht100CreateYearTo());
        cmn999Form.addHiddenParam("cht100CreateMonthTo", form.getCht100CreateMonthTo());
        cmn999Form.addHiddenParam("cht100CreateDayTo", form.getCht100CreateDayTo());
        cmn999Form.addHiddenParam("cht100UpdateYearFr", form.getCht100UpdateYearFr());
        cmn999Form.addHiddenParam("cht100UpdateMonthFr", form.getCht100UpdateMonthFr());
        cmn999Form.addHiddenParam("cht100UpdateDayFr", form.getCht100UpdateDayFr());
        cmn999Form.addHiddenParam("cht100UpdateYearTo", form.getCht100UpdateYearTo());
        cmn999Form.addHiddenParam("cht100UpdateMonthTo", form.getCht100UpdateMonthTo());
        cmn999Form.addHiddenParam("cht100UpdateDayTo", form.getCht100UpdateDayTo());

        cmn999Form.addHiddenParam("svCht100Keyword", form.getSvCht100Keyword());
        cmn999Form.addHiddenParam("svCht100AndOr", form.getSvCht100AndOr());
        cmn999Form.addHiddenParam("svCht100GroupId", form.getSvCht100GroupId());
        cmn999Form.addHiddenParam("svCht100GroupName", form.getSvCht100GroupName());
        cmn999Form.addHiddenParam("svCht100GroupInfo", form.getSvCht100GroupInfo());
        cmn999Form.addHiddenParam("svCht100Category", form.getSvCht100Category());
        cmn999Form.addHiddenParam("svCht100StatusKbn", form.getSvCht100StatusKbn());
        cmn999Form.addHiddenParam("svCht100SelectGroup", form.getSvCht100SelectGroup());
        cmn999Form.addHiddenParam("svCht100SelectUser", form.getSvCht100SelectUser());
        cmn999Form.addHiddenParam("svCht100AdminMember", form.getSvCht100AdminMember());
        cmn999Form.addHiddenParam("svCht100GeneralMember", form.getSvCht100GeneralMember());
        cmn999Form.addHiddenParam("svCht100CreateYearFr", form.getSvCht100CreateYearFr());
        cmn999Form.addHiddenParam("svCht100CreateMonthFr", form.getSvCht100CreateMonthFr());
        cmn999Form.addHiddenParam("svCht100CreateDayFr", form.getSvCht100CreateDayFr());
        cmn999Form.addHiddenParam("svCht100CreateYearTo", form.getSvCht100CreateYearTo());
        cmn999Form.addHiddenParam("svCht100CreateMonthTo", form.getSvCht100CreateMonthTo());
        cmn999Form.addHiddenParam("svCht100CreateDayTo", form.getSvCht100CreateDayTo());
        cmn999Form.addHiddenParam("svCht100UpdateYearFr", form.getSvCht100UpdateYearFr());
        cmn999Form.addHiddenParam("svCht100UpdateMonthFr", form.getSvCht100UpdateMonthFr());
        cmn999Form.addHiddenParam("svCht100UpdateDayFr", form.getSvCht100UpdateDayFr());
        cmn999Form.addHiddenParam("svCht100UpdateYearTo", form.getSvCht100UpdateYearTo());
        cmn999Form.addHiddenParam("svCht100UpdateMonthTo", form.getSvCht100UpdateMonthTo());
        cmn999Form.addHiddenParam("svCht100UpdateDayTo", form.getSvCht100UpdateDayTo());
        String[] memberAdminSid = form.getCht110memberAdmin();
        if (memberAdminSid != null) {
            for (String sid:memberAdminSid) {
                cmn999Form.addHiddenParam("cht110memberAdmin", sid);
            }
        }
        String[] memberGeneralSid = form.getCht110memberNormal();
        if (memberGeneralSid != null) {
            for (String sid:memberGeneralSid) {
                cmn999Form.addHiddenParam("cht110memberNormal", sid);
            }
        }
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Cht110Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {


        ChtBiz chtBiz = new ChtBiz(con);
        //削除チェック
        boolean chk = chtBiz.existChtGroup(form.getCgiSid());
        if (!chk) {
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        boolean commit = false;
        // リアルタイム描画用 編集前所属メンバー
        List<Integer> oldSids = new ArrayList<Integer>();
        try {
            Cht110ParamModel paramMdl = new Cht110ParamModel();
            paramMdl.setParam(form);
            // 所属メンバー取得
            ChtGroupUserDao cguDao = new ChtGroupUserDao(con);
            oldSids = cguDao.membersOfChatGroup(paramMdl.getCgiSid());

            // グループチャット論理削除
            chtBiz.logicDeleteChtGroup(paramMdl.getCgiSid(),
                             getSessionUserSid(req));
            paramMdl.setFormData(form);

            forward = __setCompDsp(map, req, form);
            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String msg = gsMsg.getMessage("cmn.delete");
            String opCode = msg;
            ChtGroupInfDao infDao = new ChtGroupInfDao(con);
            ChtGroupInfModel infMdl
                    = infDao.select(paramMdl.getCgiSid());
            //ログ出力処理
            String logGroupName
                = " [" + gsMsg.getMessage("cmn.group.name") + "] " + infMdl.getCgiName();
            chtBiz.outPutLog(
                    map,
                    opCode,
                    GSConstLog.LEVEL_INFO,
                    logGroupName,
                    reqMdl,
                    null);
            commit = true;
        } catch (Exception e) {
            log__.error("チャットグループ情報の論理削除に失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        // リアルタイム通信によるグループの表示
        BaseUserModel smodel = getSessionUserModel(req);
        int usrSid = smodel.getUsrsid();
        Cht110ParamModel paramMdl = new Cht110ParamModel();
        paramMdl.setParam(form);
        Cht110Biz cht110Biz = new Cht110Biz(con, getRequestModel(req));
        cht110Biz.createNewGroupParam(req, paramMdl, usrSid, oldSids);
        paramMdl.setFormData(form);

        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Cht110Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        forwardOk = map.findForward("cht100");
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.group");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", msg));
        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());
        cmn999Form.addHiddenParam("cht100InitFlg", form.getCht100InitFlg());
        cmn999Form.addHiddenParam("cht100SortKey", form.getCht100SortKey());
        cmn999Form.addHiddenParam("cht100OrderKey", form.getCht100OrderKey());
        cmn999Form.addHiddenParam("cht100PageTop", form.getCht100PageTop());
        cmn999Form.addHiddenParam("cht100PageBottom", form.getCht100PageBottom());
        cmn999Form.addHiddenParam("cht100SearchFlg", form.getCht100SearchFlg());

        cmn999Form.addHiddenParam("cht100Keyword", form.getCht100Keyword());
        cmn999Form.addHiddenParam("cht100AndOr", form.getCht100AndOr());
        cmn999Form.addHiddenParam("cht100GroupId", form.getCht100GroupId());
        cmn999Form.addHiddenParam("cht100GroupName", form.getCht100GroupName());
        cmn999Form.addHiddenParam("cht100GroupInfo", form.getCht100GroupInfo());
        cmn999Form.addHiddenParam("cht100Category", form.getCht100Category());
        cmn999Form.addHiddenParam("cht100StatusKbn", form.getCht100StatusKbn());
        cmn999Form.addHiddenParam("cht100SelectGroup", form.getCht100SelectGroup());
        cmn999Form.addHiddenParam("cht100SelectUser", form.getCht100SelectUser());
        cmn999Form.addHiddenParam("cht100AdminMember", form.getCht100AdminMember());
        cmn999Form.addHiddenParam("cht100GeneralMember", form.getCht100GeneralMember());
        cmn999Form.addHiddenParam("cht100CreateYearFr", form.getCht100CreateYearFr());
        cmn999Form.addHiddenParam("cht100CreateMonthFr", form.getCht100CreateMonthFr());
        cmn999Form.addHiddenParam("cht100CreateDayFr", form.getCht100CreateDayFr());
        cmn999Form.addHiddenParam("cht100CreateYearTo", form.getCht100CreateYearTo());
        cmn999Form.addHiddenParam("cht100CreateMonthTo", form.getCht100CreateMonthTo());
        cmn999Form.addHiddenParam("cht100CreateDayTo", form.getCht100CreateDayTo());
        cmn999Form.addHiddenParam("cht100UpdateYearFr", form.getCht100UpdateYearFr());
        cmn999Form.addHiddenParam("cht100UpdateMonthFr", form.getCht100UpdateMonthFr());
        cmn999Form.addHiddenParam("cht100UpdateDayFr", form.getCht100UpdateDayFr());
        cmn999Form.addHiddenParam("cht100UpdateYearTo", form.getCht100UpdateYearTo());
        cmn999Form.addHiddenParam("cht100UpdateMonthTo", form.getCht100UpdateMonthTo());
        cmn999Form.addHiddenParam("cht100UpdateDayTo", form.getCht100UpdateDayTo());

        cmn999Form.addHiddenParam("svCht100Keyword", form.getSvCht100Keyword());
        cmn999Form.addHiddenParam("svCht100AndOr", form.getSvCht100AndOr());
        cmn999Form.addHiddenParam("svCht100GroupId", form.getSvCht100GroupId());
        cmn999Form.addHiddenParam("svCht100GroupName", form.getSvCht100GroupName());
        cmn999Form.addHiddenParam("svCht100GroupInfo", form.getSvCht100GroupInfo());
        cmn999Form.addHiddenParam("svCht100Category", form.getSvCht100Category());
        cmn999Form.addHiddenParam("svCht100StatusKbn", form.getSvCht100StatusKbn());
        cmn999Form.addHiddenParam("svCht100SelectGroup", form.getSvCht100SelectGroup());
        cmn999Form.addHiddenParam("svCht100SelectUser", form.getSvCht100SelectUser());
        cmn999Form.addHiddenParam("svCht100AdminMember", form.getSvCht100AdminMember());
        cmn999Form.addHiddenParam("svCht100GeneralMember", form.getSvCht100GeneralMember());
        cmn999Form.addHiddenParam("svCht100CreateYearFr", form.getSvCht100CreateYearFr());
        cmn999Form.addHiddenParam("svCht100CreateMonthFr", form.getSvCht100CreateMonthFr());
        cmn999Form.addHiddenParam("svCht100CreateDayFr", form.getSvCht100CreateDayFr());
        cmn999Form.addHiddenParam("svCht100CreateYearTo", form.getSvCht100CreateYearTo());
        cmn999Form.addHiddenParam("svCht100CreateMonthTo", form.getSvCht100CreateMonthTo());
        cmn999Form.addHiddenParam("svCht100CreateDayTo", form.getSvCht100CreateDayTo());
        cmn999Form.addHiddenParam("svCht100UpdateYearFr", form.getSvCht100UpdateYearFr());
        cmn999Form.addHiddenParam("svCht100UpdateMonthFr", form.getSvCht100UpdateMonthFr());
        cmn999Form.addHiddenParam("svCht100UpdateDayFr", form.getSvCht100UpdateDayFr());
        cmn999Form.addHiddenParam("svCht100UpdateYearTo", form.getSvCht100UpdateYearTo());
        cmn999Form.addHiddenParam("svCht100UpdateMonthTo", form.getSvCht100UpdateMonthTo());
        cmn999Form.addHiddenParam("svCht100UpdateDayTo", form.getSvCht100UpdateDayTo());
        cmn999Form.addHiddenParam("oldMemberSids", form.getOldMemberSids());
        cmn999Form.addHiddenParam("realTimeFlg", GSConstChat.REAL_TIME_YES);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}

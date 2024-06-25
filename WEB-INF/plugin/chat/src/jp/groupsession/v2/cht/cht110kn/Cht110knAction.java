package jp.groupsession.v2.cht.cht110kn;

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
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.biz.ChtMemberBiz;
import jp.groupsession.v2.cht.cht110.Cht110Biz;
import jp.groupsession.v2.cht.dao.ChtCategoryDao;
import jp.groupsession.v2.cht.dao.ChtGroupUserDao;
import jp.groupsession.v2.cht.model.ChatGroupComboModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] チャットグループ追加/編集確認 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht110knAction extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht110knAction.class);

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

   protected ActionForward _immigration(ActionMapping map, Cht110knForm form,
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
        Cht110knForm thisForm = (Cht110knForm) form;
        // アクセス制限
        ActionForward forward  = _immigration(map, thisForm, req);
        if (forward != null) {
            return forward;
        }
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        if (cmd.equals("kakutei")) {
            log__.debug("確定");
            forward = __doKakutei(map, thisForm, req, res, con);
        } else if (cmd.equals("backInput")) {
            log__.debug("設定画面へ戻る");
            forward = map.findForward("cht110");
        } else {
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
    public ActionForward __doInit(ActionMapping map, Cht110knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht110knParamModel paramMdl = new Cht110knParamModel();
        paramMdl.setParam(form);
        Cht110knBiz biz = new Cht110knBiz(con, getRequestModel(req));
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
     * @param form Cht110knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map,
                                  Cht110knForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ChtBiz chtBiz = new ChtBiz(con);
        //　存在チェック
        if (form.getCht100ProcMode() == GSConstChat.CHAT_MODE_EDIT) {
            boolean chk = chtBiz.existChtGroup(form.getCgiSid());
            if (!chk) {
                return getSubmitErrorPage(map, req);
            }
        }

        //入力チェック
        ActionErrors errors = form.validateCht110(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("cht110");
       }
        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //ログインユーザSID取得
        BaseUserModel smodel = getSessionUserModel(req);
        int usrSid = smodel.getUsrsid();

        //登録または更新処理
        Cht110knBiz biz = new Cht110knBiz(con, getRequestModel(req));

        boolean commit = false;
        // リアルタイム描画用 編集前所属メンバー
        List<Integer> oldSids = new ArrayList<Integer>();
        try {
            Cht110knParamModel paramMdl = new Cht110knParamModel();
            paramMdl.setParam(form);
            if (form.getCht100ProcMode() == GSConstChat.CHAT_MODE_ADD) {
                biz.insert(paramMdl, usrSid, cntCon);
            } else  {
                // 所属メンバー取得
                ChtGroupUserDao cguDao = new ChtGroupUserDao(con);
                oldSids = cguDao.membersOfChatGroup(paramMdl.getCgiSid());
                if (!biz.update(paramMdl, usrSid, cntCon)) {
                    Cmn999Form cmn999Form = new Cmn999Form();
                    ActionForward urlForward = map.findForward("cht110");

                    MessageResources msgRes = getResources(req);
                    cmn999Form.setIcon(Cmn999Form.ICON_WARN);
                    cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
                    cmn999Form.setType(Cmn999Form.TYPE_OK);
                    cmn999Form.setUrlOK(urlForward.getPath());

                    GsMessage gsMsg = new GsMessage();
                    String msg = gsMsg.getMessage(req, "cmn.group");
                    //メッセージセット
                    cmn999Form.setMessage(msgRes.getMessage("search.data.notfound", msg));
                    req.setAttribute("cmn999Form", cmn999Form);

                    return map.findForward("gf_msg");
                }
            }
            paramMdl.setFormData(form);
            __doOutLog(map, form, req, res, con, usrSid);

            commit = true;
        } catch (Exception e) {
            log__.error("グループチャットの登録編集に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            } else {
                con.commit();
            }
        }

        // リアルタイム通信によるグループの表示
        Cht110knParamModel paramMdl = new Cht110knParamModel();
        paramMdl.setParam(form);
        Cht110Biz cht110Biz = new Cht110Biz(con, getRequestModel(req));
        cht110Biz.createNewGroupParam(req, paramMdl, usrSid, oldSids);
        paramMdl.setFormData(form);

        return __setKanryoDsp(map, form, req);
    }



    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(ActionMapping map,
                                         Cht110knForm form,
                                         HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        forwardOk = map.findForward("cht100");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        int procMode = form.getCht100ProcMode();

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.group");

        if (procMode == GSConstChat.CHAT_MODE_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", msg));
        } else  {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", msg));
        }
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());
        cmn999Form.addHiddenParam("cht100Keyword", form.getCht100Keyword());
        cmn999Form.addHiddenParam("cht100SortKey", form.getCht100SortKey());
        cmn999Form.addHiddenParam("cht100OrderKey", form.getCht100OrderKey());
        cmn999Form.addHiddenParam("cht100PageTop", form.getCht100PageTop());
        cmn999Form.addHiddenParam("cht100PageBottom", form.getCht100PageBottom());
        cmn999Form.addHiddenParam("cht100SearchFlg", form.getCht100SearchFlg());

        cmn999Form.addHiddenParam("cht100InitFlg", form.getCht100InitFlg());
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


        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht110knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param usrSid ユーザSID
     * @throws Exception 実行時例外
     */
    private void __doOutLog(ActionMapping map,
            Cht110knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int usrSid)
            throws Exception {
        // ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        ChtBiz biz = new ChtBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.change");
        if (form.getCht100ProcMode() == GSConstChat.CHAT_MODE_ADD) {
            opCode = gsMsg.getMessage("cmn.entry");
        } else {
            opCode = gsMsg.getMessage("cmn.edit");
        }

        StringBuilder sb = new StringBuilder();
        // グループID
        sb.append("[" + gsMsg.getMessage("cht.01")
                + gsMsg.getMessage("main.src.man220.6") + "] ");
            sb.append(form.getCht110groupId());
        sb.append(System.getProperty("line.separator"));

        // カテゴリ
        sb.append("[" + gsMsg.getMessage("cmn.category") + "] ");
        ChtCategoryDao cateDao = new ChtCategoryDao(con);
        String categoryName = cateDao.select(form.getCht110category()).getChcName();
        sb.append(categoryName);
        sb.append(System.getProperty("line.separator"));

        // グループ名
        sb.append("[" + gsMsg.getMessage("cmn.group.name") + "] ");
        sb.append(form.getCht110groupName());
        sb.append(System.getProperty("line.separator"));

        // メンバー
        ChtMemberBiz memberBiz = new ChtMemberBiz(con);
        ChatGroupComboModel targetList = new ChatGroupComboModel();
        targetList.setMemberAdminSid(form.getCht110memberAdmin());
        targetList.setMemberGeneralSid(form.getCht110memberNormal());
        memberBiz.setMember(getRequestModel(req), targetList, false, usrSid);
        // 管理者
        List<UsrLabelValueBean> limitAdminList = targetList.getLeftUserAdminList();
        if (!limitAdminList.isEmpty()) {
            StringBuilder str = new StringBuilder();
            sb.append("[" + gsMsg.getMessage("cht.03") + "]");
            for (UsrLabelValueBean name: limitAdminList) {
                 if (str.length() > 0) {
                     str.append(",");
                 }
                 str.append(name.getLabel());
            }
            sb.append(str.toString());
            sb.append(System.getProperty("line.separator"));
        }
        //　一般
        List<UsrLabelValueBean> limitGeneralList
                = targetList.getLeftUserGeneralList();
        if (!limitGeneralList.isEmpty()) {
            StringBuilder str = new StringBuilder();
            sb.append("[" + gsMsg.getMessage("cht.04") + "]");
            for (UsrLabelValueBean name: limitGeneralList) {
                 if (str.length() > 0) {
                     str.append(",");
                 }
                 str.append(name.getLabel());
            }
            sb.append(str.toString());
            sb.append(System.getProperty("line.separator"));
        }
        // 備考
        sb.append("[" + gsMsg.getMessage("cmn.memo") + "] ");
        sb.append(form.getCht110biko());
        sb.append(System.getProperty("line.separator"));
        // アーカイブ
        if (form.getCht100ProcMode() == GSConstChat.CHAT_MODE_EDIT) {
            sb.append("[" + gsMsg.getMessage("cht.cht110.03") + "] ");
            if (form.getCht110compFlg() == GSConstChat.CHAT_ARCHIVE_MODE) {
                sb.append(gsMsg.getMessage("cht.cht110.01"));
            } else {
                sb.append(gsMsg.getMessage("cht.cht110.02"));
            }
        }
        biz.outPutLog(
                map, opCode, GSConstLog.LEVEL_INFO, sb.toString(),
                reqMdl, null);
    }

}

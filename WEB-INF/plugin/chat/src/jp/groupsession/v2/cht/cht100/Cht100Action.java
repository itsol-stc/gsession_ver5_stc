package jp.groupsession.v2.cht.cht100;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャットグループ管理 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht100Action extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht100Action.class);

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
        ActionForward forward = null;
        Cht100Form thisForm = (Cht100Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        if (cmd.equals("addEditGrp")) {
            log__.debug("チャットグループ追加編集");
            forward = __doClickLink(map, thisForm, req, res, con);
        } else if (cmd.equals("backToAdmin")) {
            log__.debug("管理者設定へ戻る");
            forward = map.findForward("cht020");
        } else if (cmd.equals("search")) {
            log__.debug("検索");
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("prevPage")) {
            log__.debug("前ページクリック");
            thisForm.setCht100PageTop(thisForm.getCht100PageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            log__.debug("次ページクリック");
            thisForm.setCht100PageTop(thisForm.getCht100PageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);
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
     * @param form Cht100Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht100ParamModel paramMdl = new Cht100ParamModel();
        paramMdl.setParam(form);
        Cht100Biz biz = new Cht100Biz(con, getRequestModel(req));
        biz.setInitData(paramMdl);
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getCht100CreateDateFr() == null) {
            dateBiz.setDateParam(paramMdl, "cht100CreateDateFr",
                    "cht100CreateYearFr", "cht100CreateMonthFr",
                    "cht100CreateDayFr", null);
        }
        if (paramMdl.getCht100CreateDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "cht100CreateDateTo",
                    "cht100CreateYearTo", "cht100CreateMonthTo",
                    "cht100CreateDayTo", null);
        }
        
        if (paramMdl.getCht100UpdateDateFr() == null) {
            dateBiz.setDateParam(paramMdl, "cht100UpdateDateFr",
                    "cht100UpdateYearFr", "cht100UpdateMonthFr",
                    "cht100UpdateDayFr", null);
        }
        
        if (paramMdl.getCht100UpdateDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "cht100UpdateDateTo",
                    "cht100UpdateYearTo", "cht100UpdateMonthTo",
                    "cht100UpdateDayTo", null);
        }
        
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht100Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSearch(ActionMapping map, Cht100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //入力チェック
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCht100(req, con, false, reqMdl);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setCht100SearchFlg(0);
            return __doInit(map, form, req, res, con);
        }
        Cht100ParamModel paramMdl = new Cht100ParamModel();
        paramMdl.setParam(form);
        Cht100Biz biz = new Cht100Biz(con, reqMdl);
        int searchCount = biz.getSearchCount(paramMdl);
        paramMdl.setFormData(form);
        if (searchCount <= 0) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String cht = gsMsg.getMessage("cht.01");
            ActionMessage msg = new ActionMessage("search.data.notfound", cht);
            errors.add("search.data.notfound", msg);
            addErrors(req, errors);
            form.setCht100SearchFlg(0);
        } else {
            form.setSvCht100Keyword(paramMdl.getCht100Keyword());
            form.setSvCht100AndOr(paramMdl.getCht100AndOr());
            form.setSvCht100GroupId(paramMdl.getCht100GroupId());
            form.setSvCht100GroupName(paramMdl.getCht100GroupName());
            form.setSvCht100GroupInfo(paramMdl.getCht100GroupInfo());
            form.setSvCht100Category(paramMdl.getCht100Category());
            form.setSvCht100StatusKbn(paramMdl.getCht100StatusKbn());
            form.setSvCht100SelectGroup(paramMdl.getCht100SelectGroup());
            form.setSvCht100SelectUser(paramMdl.getCht100SelectUser());
            form.setSvCht100AdminMember(paramMdl.getCht100AdminMember());
            form.setSvCht100GeneralMember(paramMdl.getCht100GeneralMember());
            form.setSvCht100CreateYearFr(paramMdl.getCht100CreateYearFr());
            form.setSvCht100CreateMonthFr(paramMdl.getCht100CreateMonthFr());
            form.setSvCht100CreateDayFr(paramMdl.getCht100CreateDayFr());
            form.setSvCht100CreateYearTo(paramMdl.getCht100CreateYearTo());
            form.setSvCht100CreateMonthTo(paramMdl.getCht100CreateMonthTo());
            form.setSvCht100CreateDayTo(paramMdl.getCht100CreateDayTo());
            form.setSvCht100UpdateYearFr(paramMdl.getCht100UpdateYearFr());
            form.setSvCht100UpdateMonthFr(paramMdl.getCht100UpdateMonthFr());
            form.setSvCht100UpdateDayFr(paramMdl.getCht100UpdateDayFr());
            form.setSvCht100UpdateYearTo(paramMdl.getCht100UpdateYearTo());
            form.setSvCht100UpdateMonthTo(paramMdl.getCht100UpdateMonthTo());
            form.setSvCht100UpdateDayTo(paramMdl.getCht100UpdateDayTo());
            form.setSvCht100SortKey(paramMdl.getCht100SortKey());
            form.setSvCht100OrderKey(paramMdl.getCht100OrderKey());
            form.setCht100SearchFlg(1);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] リンククリック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht100Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doClickLink(ActionMapping map,
                                Cht100Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {
        // 登録画面遷移
        if (form.getCht100ProcMode() == GSConstChat.CHAT_MODE_ADD) {
            return map.findForward("cht110");
        }

        // 編集画面遷移時時グループ存在チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCht100Edit(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        return map.findForward("cht110");
    }

}

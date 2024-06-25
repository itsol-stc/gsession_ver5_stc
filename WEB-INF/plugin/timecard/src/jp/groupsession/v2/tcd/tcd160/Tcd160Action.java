package jp.groupsession.v2.tcd.tcd160;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;


/**
 * <br>[機  能] タイムカード 管理者設定 休日区分設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd160Action extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd160Action.class);

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

        Tcd160Form thisForm = (Tcd160Form) form;

        if (cmd.equals("tcd160back")) {
            //戻る
            forward = map.findForward("tcd160back");
        } else if (cmd.equals("tcd160add")) {
            thisForm.setTcd170Mode(GSConst.CMDMODE_ADD);
            //追加ボタン
            forward = map.findForward("tcd160add");
        } else if (cmd.equals("tcd160edit")) {
            thisForm.setTcd170Mode(GSConst.CMDMODE_EDIT);
            //編集
            forward = map.findForward("tcd160add");
        } else if (cmd.equals("up")) {
            //上へボタンクリック
            forward = __setAdmConf(map, thisForm, req, res, con, GSConst.SORT_UP);
        } else if (cmd.equals("down")) {
            //下へボタン
            forward = __setAdmConf(map, thisForm, req, res, con, GSConst.SORT_DOWN);
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
    private ActionForward __doInit(ActionMapping map, Tcd160Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        ActionForward forward = null;

        RequestModel reqMdl = getRequestModel(req);
        Tcd160Biz biz = new Tcd160Biz(reqMdl);
        Tcd160ParamModel paramMdl = new Tcd160ParamModel();
        paramMdl.setParam(form);
        con.setAutoCommit(true);
        biz._setInitData(paramMdl, con);
        con.setAutoCommit(false);
        paramMdl.setFormData(form);
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __setAdmConf(
            ActionMapping map,
            Tcd160Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int changeKbn) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        //入力チェックを行う
        ActionErrors ereors = form.validateSortCheck(reqMdl);
        if (!ereors.isEmpty()) {
            addErrors(req, ereors);
            return __doInit(map, form, req, res, con);
        }

        //存在チェック
        ActionErrors errors = null;
        if (form.getTcd170Mode() == GSConst.CMDMODE_EDIT) {
            errors = form.existsHolidayOrder(reqMdl, con);
            if (errors.size() > 0) {
                log__.debug("不正エラー");
                addErrors(req, errors);
                return getSubmitErrorPage(map, req);
            }
        }
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }
        con.setAutoCommit(false);
        boolean commitFlg = false;
        Tcd160Biz biz = new Tcd160Biz(reqMdl);
        boolean updateFlg = false;
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        try {
            Tcd160ParamModel paramMdl = new Tcd160ParamModel();
            paramMdl.setParam(form);
            updateFlg = biz._updateSort(con, paramMdl, changeKbn, userSid);
            paramMdl.setFormData(form);
            commitFlg = true;

        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        //ログ出力
        if (updateFlg) {
            //休日区分名取得
            TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
            int sid = NullDefault.getInt(form.getTcd160Order(), -1);
            TcdHolidayInfModel mdl = dao.select(sid);
            String holName = "";
            if (mdl != null) {
                holName = mdl.getThiName();
            }
            GsMessage gsMsg = new GsMessage(reqMdl);
            String opCode = gsMsg.getMessage("fil.77");
            StringBuilder sb = new StringBuilder();
            //休日区分名称
            sb.append("[" + gsMsg.getMessage("tcd.tcd160.06") + "] ");
            sb.append(holName);
            sb.append(System.getProperty("line.separator"));
            //実行内容
            sb.append("[" + gsMsg.getMessage("wml.282") + "] ");
            if (changeKbn == GSConst.SORT_UP) {
                sb.append(gsMsg.getMessage("tcd.tcd120.06"));
            } else {
                sb.append(gsMsg.getMessage("tcd.tcd120.07"));
            }
            TimecardBiz timBiz = new TimecardBiz();
            timBiz.outPutTimecardLog(
                    map,
                    reqMdl,
                    con,
                    opCode,
                    GSConstLog.LEVEL_TRACE,
                    sb.toString());
        }

        return __doInit(map, form, req, res, con);
    }

}

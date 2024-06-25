package jp.groupsession.v2.bmk.bmk070;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk010.Bmk010Biz;
import jp.groupsession.v2.bmk.bmk010.Bmk010Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <p>コメント・評価画面のアクションクラス
 * @author JTS
 */
public class Bmk070Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk070Action.class);
    /** 遷移元画面 */
    private String returnPage__;

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
        Bmk070Form bmkForm = (Bmk070Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        //遷移元画面セット
        if (!StringUtil.isNullZeroString(bmkForm.getBmk070ReturnPage())) {
            if (bmkForm.getBmk070ReturnPage().equals("bmk150")) {
                returnPage__ = bmkForm.getBmk070ReturnPage();
            }
        } else {
            returnPage__ = bmkForm.getReturnPage();
        }

        if (cmd.equals("prevPage")) {
            //前ページクリック
            bmkForm.setBmk070PageTop(bmkForm.getBmk070PageTop() - 1);
            forward = __doInit(map, bmkForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            bmkForm.setBmk070PageTop(bmkForm.getBmk070PageTop() + 1);
            forward = __doInit(map, bmkForm, req, res, con);
        } else if (cmd.equals("changePage")) {
            //ページコンボ変更
            forward = __doInit(map, bmkForm, req, res, con);
        } else if (cmd.equals("bmk070back")) {
            //戻るボタンクリック
            log__.debug("遷移元：" + returnPage__);
            forward = map.findForward(returnPage__);
        } else if (cmd.equals("bmk010back")) {
            forward = __doBackBmk010(map, bmkForm, req, res, con);
        } else if (cmd.equals("bmkAdd")) {
            forward = map.findForward("bmk020");
        } else if (cmd.equals("bmkEdit")) {
            forward = map.findForward("bmk030");
        } else {
            //初期表示
            forward = __doInit(map, bmkForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Bmk070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        con.setAutoCommit(true);
        Bmk070Biz biz = new Bmk070Biz(getRequestModel(req));

        Bmk070ParamModel paramMdl = new Bmk070ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, userSid, getAppRootPath());
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        GsMessage gsMsg = new GsMessage();
        String msg2 = "";
        if (form.getBmk070NotViewBmk() == Bmk070Biz.BOOKMARK_NOT_PUBLIC) {
            msg2 = gsMsg.getMessage(req, "bmk.69");
            //公開ユーザなし
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("search.notfound.tdfkcode", msg2);
            StrutsUtil.addMessage(errors, msg, "bookmarkCount");
            addErrors(req, errors);
        } else if (form.getBmk070NotViewBmk() == Bmk070Biz.BOOKMARK_NOT_USER) {
            msg2 = gsMsg.getMessage(req, "bmk.70");
            //登録ユーザなし
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("search.notfound.tdfkcode", msg2);
            StrutsUtil.addMessage(errors, msg, "bookmarkCount");
            addErrors(req, errors);
        }

        //新着ブックマークからの遷移フラグ
        if (form.getBmk070ReturnPage() != null) {
            form.setBmk070ToBmk150DspFlg(form.getBmk070ReturnPage().equals("bmk150"));
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ブックマーク画面への戻り処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doBackBmk010(ActionMapping map,
        Bmk070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        //選択ユーザのデフォルトグループの設定
        con.setAutoCommit(true);
        CmnBelongmDao belongmDao = new CmnBelongmDao(con);
        int defGrpSid = belongmDao.selectUserBelongGroupDef(form.getBmk010userSid());
        con.setAutoCommit(false);

        Bmk010Form bmk010Form = (Bmk010Form) form;
        bmk010Form.setBmk010groupSid(defGrpSid);
        bmk010Form.setBmk010orderKey(GSConstBookmark.ORDERKEY_DESC);
        bmk010Form.setBmk010sortKey(GSConstBookmark.SORTKEY_ADATE);
        bmk010Form.setBmk010searchLabel(Bmk010Biz.INIT_VALUE);
        bmk010Form.setBmk010delInfSid(null);
        req.setAttribute("bmk010Form", bmk010Form);

        return map.findForward("bmk010");
    }
}


package jp.groupsession.v2.rss.rss080;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.AbstractRssAdminAction;
import jp.groupsession.v2.rss.RssBiz;


/**
 * <br>[機  能] RSSリーダー メンテナンス画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss080Action extends AbstractRssAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss080Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;
        Rss080Form myForm = (Rss080Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backAconf")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("backAconf");

        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            myForm.setRss080page1(myForm.getRss080page1() - 1);
            forward = __doInit(map, myForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            myForm.setRss080page1(myForm.getRss080page1() + 1);
            forward = __doInit(map, myForm, req, res, con);

        } else if (cmd.equals("changePage")) {
            //ページコンボ変更
            forward = __doInit(map, myForm, req, res, con);

        } else if (cmd.equals("delRss")) {
            log__.debug("削除ボタンクリック");
            forward = __setDelConfirmMsgPageParam(map, req, res, myForm, con);

        } else if (cmd.equals("delRssDecision")) {
            log__.debug("削除確認画面からの遷移");
            forward = __setDelCompleteMsgPageParam(map, req, res, myForm, con);

        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, myForm, req, res, con);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rss080Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Rss080ParamModel paramMdl = new Rss080ParamModel();
        paramMdl.setParam(form);
        Rss080Biz biz = new Rss080Biz();
        biz.setFeedList(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 削除確認の共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外発生
     */
    private ActionForward __setDelConfirmMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        HttpServletResponse res,
        Rss080Form form,
        Connection con) throws Exception {


        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("mine");
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setUrlCancel(urlForward.getPath());

        cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=delRssDecision");

        //メッセージセット
        String msgState = "sakujo.kakunin.once";
        cmn999Form.setMessage(msgRes.getMessage(
                msgState, StringUtilHtml.transToHTmlPlusAmparsant(form.getRssTitle())));

        cmn999Form.addHiddenParam("rssSid", form.getRssSid());
        cmn999Form.addHiddenParam("rssCmdMode", form.getRssCmdMode());
        cmn999Form.addHiddenParam("rssTitle", form.getRssTitle());
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("rss080orderKey", form.getRss080orderKey());
        cmn999Form.addHiddenParam("rss080sortKey", form.getRss080sortKey());
        cmn999Form.addHiddenParam("rss080page1", form.getRss080page1());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 購読完了の共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception RSSの登録に失敗
     */
    private ActionForward __setDelCompleteMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        HttpServletResponse res,
        Rss080Form form,
        Connection con) throws Exception {


        con.setAutoCommit(false);
        boolean commit = false;
        try {
            //RSS情報の削除
            Rss080Biz biz = new Rss080Biz();
            biz.deleteRssData(con, form.getRssSid());

            //完了画面のパラメータ設定
            Cmn999Form cmn999Form = new Cmn999Form();
            ActionForward urlForward = null;

            MessageResources msgRes = getResources(req);
            cmn999Form.setIcon(Cmn999Form.ICON_INFO);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

            String msgState = "sakujo.kanryo.object";
            urlForward = map.findForward("backAconf");
            cmn999Form.setMessage(msgRes.getMessage(
                    msgState, StringUtilHtml.transToHTmlPlusAmparsant(form.getRssTitle())));

            cmn999Form.setUrlOK(urlForward.getPath());

            cmn999Form.addHiddenParam("rssSid", form.getRssSid());
            cmn999Form.addHiddenParam("rssCmdMode", form.getRssCmdMode());
            cmn999Form.addHiddenParam("rssTitle", form.getRssTitle());
            cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
            cmn999Form.addHiddenParam("rss080orderKey", form.getRss080orderKey());
            cmn999Form.addHiddenParam("rss080sortKey", form.getRss080sortKey());
            cmn999Form.addHiddenParam("rss080page1", form.getRss080page1());
            req.setAttribute("cmn999Form", cmn999Form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("RSSの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        RssBiz rssBiz = new RssBiz(con, reqMdl);
        String opCode = getInterMessage(reqMdl, "cmn.delete");

        rssBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "[title]" + form.getRssTitle());

        return map.findForward("gf_msg");
    }
}
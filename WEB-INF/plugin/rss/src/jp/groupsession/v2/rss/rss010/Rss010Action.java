package jp.groupsession.v2.rss.rss010;

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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.AbstractRssAction;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.model.RssDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] RSSリーダー一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss010Action extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss010Action.class);

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
        Rss010Form myForm = (Rss010Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("rssInput")) {
            log__.debug("新規購読ボタンクリック");
            forward = map.findForward("feedUrlInput");

        } else if (cmd.equals("rssEdit")) {
            log__.debug("編集ボタンクリック");
            forward = map.findForward("rssInput");

        } else if (cmd.equals("ranking")) {
            log__.debug("ランキングボタンクリック");
            forward = map.findForward("ranking");

        } else if (cmd.equals("rssAdd")) {
            log__.debug("購読ボタンクリック");
            forward = __setAddConfirmMsgPageParam(map, req, res, myForm, con);

        } else if (cmd.equals("addRssDecision")) {
            log__.debug("購読確認画面からの遷移");
            forward = __setAddCompleteMsgPageParam(map, res, req, myForm, con);

        } else if (cmd.equals("pconf")) {
            log__.debug("個人設定ボタンクリック");
            forward = map.findForward("pconf");

        } else if (cmd.equals("aconf")) {
            log__.debug("管理者設定ボタンクリック");
            forward = map.findForward("aconf");

        } else if (cmd.equals("setPosition")) {
            //RSSフィードの位置移動
            __doSavePosition(map, myForm, req, res, con);

            forward = null;
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
                                    Rss010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(false);
        Rss010ParamModel paramMdl = new Rss010ParamModel();
        paramMdl.setParam(form);
        MlCountMtController cntCon = getCountMtController(req);
        Rss010Biz biz = new Rss010Biz();
        biz.setFeedList(
                paramMdl, con, getRequestModel(req), getSessionUserModel(req), cntCon);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * レイアウト保存ボタンクリック時表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     */
    private void __doSavePosition(ActionMapping map, Rss010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        boolean commit = false;
        con.setAutoCommit(false);
        try {

            Rss010Biz biz = new Rss010Biz();
            biz.saveRssPosition(con, getSessionUserModel(req).getUsrsid(),
                                form.getRss010SidLeft(), form.getRss010SidRight());
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("メイン画面位置情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 購読確認の共通メッセージ画面遷移時の設定
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
    private ActionForward __setAddConfirmMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        HttpServletResponse res,
        Rss010Form form,
        Connection con) throws Exception {

        con.setAutoCommit(true);
        int rssSid = form.getRssSid();
        BaseUserModel userMdl = getSessionUserModel(req);

        //指定されたフィードが登録済みかを確認する
        RequestModel reqMdl = getRequestModel(req);
        Rss010ParamModel paramMdl = new Rss010ParamModel();
        paramMdl.setParam(form);
        RssBiz rssBiz = new RssBiz(reqMdl);
        boolean existData = rssBiz.existRssData(rssSid, userMdl.getUsrsid(), con);
        paramMdl.setFormData(form);

        if (existData) {

            GsMessage gsMsg = new GsMessage(reqMdl);
            String textRssFeed = gsMsg.getMessage("rss.src.3");

            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.input.exist.data",
                                                     textRssFeed);
            errors.add("error.input.exist.data", msg);
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("redraw");
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setUrlCancel(urlForward.getPath());

        cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=addRssDecision");

        //メッセージセット
        String msgState = "add.kakunin.feed";
        cmn999Form.setMessage(msgRes.getMessage(
                msgState, StringUtilHtml.transToHTmlPlusAmparsant(form.getRssTitle())));

        cmn999Form.addHiddenParam("rssSid", form.getRssSid());
        req.setAttribute("cmn999Form", cmn999Form);
        con.setAutoCommit(false);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 購読完了の共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param res レスポンス
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception RSSの登録に失敗
     */
    private ActionForward __setAddCompleteMsgPageParam(
        ActionMapping map,
        HttpServletResponse res,
        HttpServletRequest req,
        Rss010Form form,
        Connection con) throws Exception {

        con.setAutoCommit(false);
        boolean commit = false;
        RssDataModel rssDataMdl = new RssDataModel();

        RequestModel reqMdl = getRequestModel(req);
        try {
            //RSS情報の登録
            RssBiz rssBiz = new RssBiz(reqMdl);
            rssDataMdl = rssBiz.insertRssData(form.getRssSid(),
                                 con,
                                 getCountMtController(req),
                                 getSessionUserSid(req),
                                 GSConstRss.RSS_MAIN_VIEWFLG_SHOW);

            //完了画面のパラメータ設定
            Cmn999Form cmn999Form = new Cmn999Form();
            ActionForward urlForward = null;

            MessageResources msgRes = getResources(req);
            cmn999Form.setIcon(Cmn999Form.ICON_INFO);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

            String msgState = "touroku.kanryo.object";
            urlForward = map.findForward("redraw");
            cmn999Form.setUrlOK(urlForward.getPath());
            cmn999Form.setMessage(msgRes.getMessage(msgState, GSConstRss.RSS_MSG));

            req.setAttribute("cmn999Form", cmn999Form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("RSSの登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力処理
        RssBiz rssBiz = new RssBiz(con, reqMdl);
        String opCode = getInterMessage(reqMdl, "cmn.entry");

        rssBiz.outPutLog(
                map, reqMdl,
                opCode, GSConstLog.LEVEL_TRACE, "[title]" + rssDataMdl.getRsdTitle());
        return map.findForward("gf_msg");
    }
}
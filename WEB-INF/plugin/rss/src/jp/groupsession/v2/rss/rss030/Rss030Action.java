package jp.groupsession.v2.rss.rss030;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import com.rometools.rome.feed.synd.SyndFeed;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.AbstractRssAction;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>RSSリーダー RSS登録画面のアクションクラス
 * @author JTS
 */
public class Rss030Action extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss030Action.class);

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
        Rss030Form rssForm = (Rss030Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);
        if (cmd.equals("rssConfirm")) {
            //ＯＫボタンクリック
            forward = __doConfirm(map, rssForm, req, res, con);
        } else if (cmd.equals("delRss")) {
            //削除ボタンクリック
            forward = __setDeleteConfirmMsgPageParam(map, req, rssForm, con);
        } else if (cmd.equals("delRssConfirm")) {
            //削除ボタンクリック
            forward = __setDeleteConfirmMsgPageParam(map, req, rssForm, con);
        } else if (cmd.equals("delRssDecision")) {
            //RSS削除確認画面からの遷移
            forward = __setDeleteCompleteMsgPageParam(map, req, res, rssForm, con);
        } else if (cmd.equals("backFeedUrl")) {
            //戻るボタン(登録時)クリック
            forward = map.findForward("feedUrlInput");
        } else if (cmd.equals("backRssList")) {
            //戻るボタン(更新時)クリック
            forward = map.findForward("backRssList");
        } else {
            //初期表示
            forward = __doInit(map, rssForm, req, res, con);
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
     * @throws SQLException 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Rss030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException {

        con.setAutoCommit(true);
        Rss030ParamModel paramMdl = new Rss030ParamModel();
        paramMdl.setParam(form);
        Rss030Biz biz = new Rss030Biz();
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        biz.setInitData(cmd, getRequestModel(req), paramMdl, con, getSessionUserSid(req));
        paramMdl.setFormData(form);

        //ヘルプモードを設定する。
        int cmdMode = form.getRssCmdMode();
        if (cmdMode == GSConstRss.RSSCMDMODE_ADD) {
            form.setHelpMode(GSConstRss.RSSHELPMODE_ADD);
        } else if (cmdMode == GSConstRss.RSSCMDMODE_EDIT) {
            form.setHelpMode(GSConstRss.RSSHELPMODE_EDIT);
        }
        
        saveToken(req);
        
        return map.getInputForward();
    }

    /**
     * <br>[機  能] ＯＫボタンクリック時処理を行う
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
    private ActionForward __doConfirm(ActionMapping map,
        Rss030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(true);
        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(con, getSessionUserSid(req), reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        
        //入力されたフィードURLからフィードが取得できるかを判定
        SyndFeed feed = null;
        try {
            HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);
            JDBCUtil.closeConnection(con);

            RssBiz rssBiz = new RssBiz(reqMdl);
            feed = rssBiz.getFeedData(form.getRssFeedUrl(), httpOperation);
            feed.getEntries();
        } catch (Exception e) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textRssFeedUrl = gsMsg.getMessage("rss.feedurl");
            ActionMessage msg = new ActionMessage("nodata.feed.url", textRssFeedUrl);
            StrutsUtil.addMessage(
                    errors, msg, "rssFeedUrl.nodata.feed.url");
            addErrors(req, errors);
            JDBCUtil.closeConnection(con);

            try {
                con = getConnection(req);
                return __doInit(map, form, req, res, con);
            } finally {
                closeConnection(con);
            }
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        Rss030Biz biz = new Rss030Biz();
        boolean commit = false;
        int cmdMode = form.getRssCmdMode();
        con = getConnection(req);
        
        con.setAutoCommit(false);
        try {
            Rss030ParamModel paramMdl = new Rss030ParamModel();
            paramMdl.setParam(form);
            if (cmdMode == GSConstRss.RSSCMDMODE_ADD) {

                //登録処理
                MlCountMtController cntCon = getCountMtController(req);
                biz.insertRssData(paramMdl, con, cntCon, userSid, reqMdl);

            } else if (cmdMode == GSConstRss.RSSCMDMODE_EDIT) {

                //更新処理
                biz.updateRssData(paramMdl, con, getCountMtController(req), userSid, reqMdl);

            }
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("RSS登録処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        
        //ログ出力処理
        RssBiz rssBiz = new RssBiz(con, reqMdl);
        String opCode = null;
        if (cmdMode == GSConstRss.RSSCMDMODE_ADD) {
            //新規登録
            opCode = getInterMessage(reqMdl, "cmn.entry");
        } else if (cmdMode == GSConstRss.RSSCMDMODE_EDIT) {
            //更新
            opCode = getInterMessage(reqMdl, "cmn.change");
        }
        
        rssBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_TRACE, "[title]" + form.getRssTitle());
        JDBCUtil.closeConnection(con);
        
        __setCompPageParam(map, req, form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除確認の共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setDeleteConfirmMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Rss030Form form,
        Connection con) throws SQLException {

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
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                                                GSConstRss.RSS_MSG));


        cmn999Form.addHiddenParam("rssSid", form.getRssSid());
        cmn999Form.addHiddenParam("rssCmdMode", form.getRssCmdMode());
        cmn999Form.addHiddenParam("rssTitle", form.getRssTitle());
        cmn999Form.addHiddenParam("rssBeforeFeedUrl", form.getRssBeforeFeedUrl());
        cmn999Form.addHiddenParam("rssFeedUrl", form.getRssFeedUrl());
        cmn999Form.addHiddenParam("rssUrl", form.getRssUrl());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除完了の共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 
     */
    private ActionForward __setDeleteCompleteMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        HttpServletResponse res,
        Rss030Form form,
        Connection con) throws Exception {

        boolean commit = false;
        try {
            //RSS情報の削除
            Rss030Biz biz = new Rss030Biz();
            biz.deleteRssData(con, form.getRssSid(), getSessionUserSid(req));

            //完了画面のパラメータ設定
            Cmn999Form cmn999Form = new Cmn999Form();
            ActionForward urlForward = null;

            MessageResources msgRes = getResources(req);
            cmn999Form.setIcon(Cmn999Form.ICON_INFO);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

            String msgState = "sakujo.kanryo.object";
            urlForward = map.findForward("backRssList");
            cmn999Form.setMessage(msgRes.getMessage(msgState,
                                GSConstRss.RSS_MSG));

            cmn999Form.setUrlOK(urlForward.getPath());

            cmn999Form.addHiddenParam("rssSid", form.getRssSid());
            cmn999Form.addHiddenParam("rssCmdMode", form.getRssCmdMode());
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
                map, reqMdl, opCode, GSConstLog.LEVEL_TRACE, "[title]" + form.getRssTitle());

        return map.findForward("gf_msg");
    }
    
    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Rss030Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backRssList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        int cmdMode = form.getRssCmdMode();
        if (cmdMode == GSConstRss.RSSCMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (cmdMode == GSConstRss.RSSCMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                            GSConstRss.RSS_MSG));

        req.setAttribute("cmn999Form", cmn999Form);

    }
}

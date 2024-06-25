package jp.groupsession.v2.rss.rss020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.rss.AbstractRssAction;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.rss030.Rss030Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.rometools.rome.feed.synd.SyndFeed;

/**
 * <p>RSSリーダー フィードURL入力画面のアクションクラス
 * @author JTS
 */
public class Rss020Action extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss020Action.class);

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
        Rss020Form rssForm = (Rss020Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);
        if (cmd.equals("moveRssData")) {
            //ＯＫボタンクリック
            forward = __doConfirm(map, rssForm, req, res, con);
        } else if (cmd.equals("rssList")) {
            //戻るボタンクリック
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
        Rss020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException {

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
        Rss020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        con.setAutoCommit(true);
        //入力チェックを行う
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(con, getSessionUserSid(req), errors, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //入力されたフィードURLからフィードが取得できるかを判定
        SyndFeed feed = null;
        try {
            HttpOperation httpOperation = new HttpOperation(con, GSConstRss.TIMEOUT);
            JDBCUtil.closeConnection(con);

            RssBiz rssBiz = new RssBiz();
            feed = rssBiz.getFeedData(form.getRssFeedUrl(), httpOperation);
            feed.getEntries();
        } catch (Exception e) {

            GsMessage gsMsg = new GsMessage();
            String textRssFeedUrl = gsMsg.getMessage(req, "rss.feedurl");

            ActionMessage msg = new ActionMessage("nodata.feed.url", textRssFeedUrl);
            StrutsUtil.addMessage(
                    errors, msg, "rssFeedUrl.nodata.feed.url");
            addErrors(req, errors);
            //
            JDBCUtil.closeConnection(con);
            //
            con = getConnection(req);
            try {
                ActionForward forward = __doInit(map, form, req, res, con);
                return forward;
            } finally {
                closeConnection(con);
            }
        }

        Rss030Form form030 = new Rss030Form();
        form030.setRssSid(form.getRssSid());
        form030.setRssCmdMode(form.getRssCmdMode());
        form030.setRssFeedUrl(form.getRssFeedUrl());
        form030.setRssTitle(feed.getTitle());
        form030.setRssUrl(feed.getLink());

        req.setAttribute("rss030Form", form030);

        return map.findForward("rssData");
    }
}


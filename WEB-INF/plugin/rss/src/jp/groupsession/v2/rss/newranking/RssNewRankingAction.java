package jp.groupsession.v2.rss.newranking;

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
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.AbstractRssAction;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.model.RssDataModel;

/**
 * <br>[機  能] RSSリーダー 新着RSS(メイン画面表示用)のアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssNewRankingAction extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssNewRankingAction.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception, GSException {

        log__.debug("RssNewRankingAction start");
        ActionForward forward = null;

        RssNewRankingForm thisForm = (RssNewRankingForm) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        int sessionUsrSid = getRequestModel(req).getSmodel().getUsrsid();
        //adminユーザのRSSフィード登録を制御
        if ((cmd.equals("rssAdd")
                || cmd.equals("addRssDecision"))
                && sessionUsrSid == 0) {
            return getSubmitErrorPage(map, req);
        }

        if (cmd.equals("rssAdd")) {
            log__.debug("購読ボタンクリック");
            forward = __setAddConfirmMsgPageParam(map, req, thisForm, con);

        } else if (cmd.equals("addRssDecision")) {
            log__.debug("購読確認画面からの遷移");
            forward = __setAddCompleteMsgPageParam(map, res, req, thisForm, con);

        } else {
            //初期表示
            forward = __doInit(map, req, thisForm, con);
        }
        log__.debug("RssNewRankingAction end");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @throws Exception 実行例外
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        HttpServletRequest req,
        RssNewRankingForm form,
        Connection con) throws Exception, GSException {

        //初期表示情報を画面にセットする
        RssNewRankingParamModel paramMdl = new RssNewRankingParamModel();
        paramMdl.setParam(form);
        RssNewRankingBiz biz = new RssNewRankingBiz();
        int userSid = 0;
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }
        userSid = userMdl.getUsrsid();

        biz.setFeedList(paramMdl, con, userSid, getRequestModel(req));
        paramMdl.setFormData(form);
        form.setRssTopUrl(getPluginConfig(req).getPlugin(
                GSConstRss.PLUGIN_ID_RSS).getTopMenuInfo().getUrl());
        return map.getInputForward();

    }

    /**
     * <br>[機  能] 購読確認の共通メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外発生
     */
    private ActionForward __setAddConfirmMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        RssNewRankingForm form,
        Connection con) throws Exception {

        con.setAutoCommit(true);
        int rssSid = NullDefault.getInt(form.getRssSid(), 0);
        BaseUserModel userMdl = getSessionUserModel(req);

        //指定されたフィードが登録済みかを確認する
        RequestModel reqMdl = getRequestModel(req);
        RssBiz rssBiz = new RssBiz(reqMdl);
        if (rssBiz.existRssData(rssSid, userMdl.getUsrsid(), con)) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.input.exist.data",
                                                   getInterMessage(reqMdl, "rss.src.3"));
            errors.add("error.input.exist.data", msg);
            addErrors(req, errors);
            return map.findForward("gf_main");
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("redraw");
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setUrlCancel(urlForward.getPath());

        cmn999Form.setUrlOK("/rss/rssnewranking.do?CMD=addRssDecision");
        //メッセージセット
        String msgState = "add.kakunin.feed";
        cmn999Form.setMessage(msgRes.getMessage(
                msgState, StringUtilHtml.transToHTmlPlusAmparsant(form.getRssTitle())));

        cmn999Form.addHiddenParam("rssSid", form.getRssSid());
        req.setAttribute("cmn999Form", cmn999Form);

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
        RssNewRankingForm form,
        Connection con) throws Exception {

        boolean commit = false;
        con.setAutoCommit(true);
        RssDataModel rssDataMdl = new RssDataModel();
        RequestModel reqMdl = getRequestModel(req);
        try {
            //RSS情報の登録
            RssBiz rssBiz = new RssBiz(reqMdl);
            rssDataMdl = rssBiz.insertRssData(NullDefault.getInt(form.getRssSid(), 0),
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
        con.setAutoCommit(false);
        return map.findForward("gf_msg");
    }
}

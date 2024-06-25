package jp.groupsession.v2.rss.rss040;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.AbstractRssAction;
import jp.groupsession.v2.rss.RssBiz;
import jp.groupsession.v2.rss.model.RssDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>RSSリーダー 登録ランキング画面のアクションクラス
 * @author JTS
 */
public class Rss040Action extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss040Action.class);

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
        Rss040Form rssForm = (Rss040Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("prevPage")) {
            //前ページクリック
            rssForm.setRss040page1(rssForm.getRss040page1() - 1);
            forward = __doInit(map, rssForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            rssForm.setRss040page1(rssForm.getRss040page1() + 1);
            forward = __doInit(map, rssForm, req, res, con);
        } else if (cmd.equals("changePage")) {
            //ページコンボ変更
            forward = __doInit(map, rssForm, req, res, con);
        } else if (cmd.equals("rssAdd")) {
            //追加ボタンクリック
            forward = __setAddConfirmMsgPageParam(map, req, res, rssForm, con);
        } else if (cmd.equals("addRssDecision")) {
            //登録確認画面からの遷移
            forward = __setAddCompleteMsgPageParam(map, req, res, rssForm, con);

        } else if (cmd.equals("backPage")) {
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
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Rss040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        con.setAutoCommit(true);
        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        Rss040ParamModel paramMdl = new Rss040ParamModel();
        paramMdl.setParam(form);
        Rss040Biz biz = new Rss040Biz();
        ITempFileUtil itmp = (ITempFileUtil) GroupSession
        .getContext().get(GSContext.TEMP_FILE_UTIL);
        biz.setInitData(paramMdl, con, itmp, userSid, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録確認の共通メッセージ画面遷移時の設定
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
        Rss040Form form,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        int rssSid = form.getRssSid();
        con.setAutoCommit(true);
        //指定されたフィードが登録済みかを確認する
        RssBiz rssBiz = new RssBiz(reqMdl);
        if (rssBiz.existRssData(rssSid, getSessionUserSid(req), con)) {

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

        urlForward = map.findForward("mine");
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setUrlCancel(urlForward.getPath());

        cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=addRssDecision");

        //メッセージセット
        String msgState = "add.kakunin.feed";
        cmn999Form.setMessage(msgRes.getMessage(
                msgState, StringUtilHtml.transToHTmlPlusAmparsant(form.getRss040feedTitle())));


        cmn999Form.addHiddenParam("rssSid", form.getRssSid());
        cmn999Form.addHiddenParam("rss040page1", form.getRss040page1());
        cmn999Form.addHiddenParam("rss040page2", form.getRss040page2());
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
     * @throws Exception RSSの登録に失敗
     */
    private ActionForward __setAddCompleteMsgPageParam(
        ActionMapping map,
        HttpServletRequest req,
        HttpServletResponse res,
        Rss040Form form,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        RssBiz rssBiz = new RssBiz(con, reqMdl);

        boolean commit = false;
        RssDataModel rssModel = new RssDataModel();
        try {
            //RSS情報の登録
            rssModel = rssBiz.insertRssData(form.getRssSid(),
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
            urlForward = map.findForward("backRssList");
            cmn999Form.setMessage(msgRes.getMessage(msgState,
                                GSConstRss.RSS_MSG));

            cmn999Form.setUrlOK(urlForward.getPath());

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
        String opCode = getInterMessage(reqMdl, "cmn.entry");

        rssBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "[title]" + rssModel.getRsdTitle());

        return map.findForward("gf_msg");
    }

}


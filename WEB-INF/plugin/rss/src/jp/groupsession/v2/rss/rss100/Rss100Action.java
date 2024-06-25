package jp.groupsession.v2.rss.rss100;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
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
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] RSSリーダー 新着RSS表示日数設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss100Action extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss100Action.class);

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
     * @throws GSException GS用汎実行例外
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
        throws Exception, GSException {

        ActionForward forward = null;
        Rss100Form myForm = (Rss100Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("decision")) {
            //追加ボタンクリック
            log__.debug("追加ボタンクリック");
            forward = __doDecision(map, myForm, req, res, con);
        } else if (cmd.equals("backPconf")) {
            //戻る
            log__.debug("戻るボタンクリック");
            forward = map.findForward("backPconf");
        } else {
            //初期表示
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
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rss100Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception, GSException {

        con.setAutoCommit(true);

        //初期表示情報を画面にセットする
        int userSid = getSessionUserSid(req);

        Rss100ParamModel paramMdl = new Rss100ParamModel();
        paramMdl.setParam(form);
        Rss100Biz biz = new Rss100Biz();
        biz.setInitData(paramMdl, con, userSid, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __doDecision(ActionMapping map,
        Rss100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception, GSException {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }
        userSid = userMdl.getUsrsid();

        RequestModel reqMdl = getRequestModel(req);
        Rss100ParamModel paramMdl = new Rss100ParamModel();
        paramMdl.setParam(form);
        Rss100Biz biz = new Rss100Biz(con);

        //個人設定登録
        int addEditFlg =  biz.setRssUconfSetting(paramMdl, reqMdl, userSid);

        //ログ出力処理
        RssBiz rssBiz = new RssBiz(con, reqMdl);
        String opCode = null;
        if (addEditFlg == GSConstRss.RSSCMDMODE_ADD) {
            //新規登録
            opCode = getInterMessage(reqMdl, "cmn.entry");
        } else if (addEditFlg == GSConstRss.RSSCMDMODE_EDIT) {
            //更新
            opCode = getInterMessage(reqMdl, "cmn.change");
        }

        rssBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "");

        __setCompPageParam(map, req, form);

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
        Rss100Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backPconf");
        cmn999Form.setUrlOK(urlForward.getPath());

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.display.settings");

        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("rssSid", form.getRssSid());
        cmn999Form.addHiddenParam("rssCmdMode", form.getRssCmdMode());
        cmn999Form.addHiddenParam("rssTitle", form.getRssTitle());
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        req.setAttribute("cmn999Form", cmn999Form);
    }
}
package jp.groupsession.v2.rss.rss060;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.AbstractRssAction;
import jp.groupsession.v2.rss.RssBiz;


/**
 * <br>[機  能] RSSリーダー メイン画面表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss060Action extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss060Action.class);

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
        Rss060Form myForm = (Rss060Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backpconf")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("backPconf");

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
                                    Rss060Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Rss060ParamModel paramMdl = new Rss060ParamModel();
        paramMdl.setParam(form);
        MlCountMtController cntCon = getCountMtController(req);
        Rss060Biz biz = new Rss060Biz();
        biz.setFeedList(paramMdl, con, getSessionUserSid(req),
                        getRequestModel(req), cntCon);
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
    private void __doSavePosition(ActionMapping map, Rss060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(false);
        boolean commit = false;
        try {

            Rss060ParamModel paramMdl = new Rss060ParamModel();
            paramMdl.setParam(form);
            Rss060Biz biz = new Rss060Biz();
            biz.saveRssPositionMain(con, getSessionUserSid(req),
                                form.getRss060SidLeft(), form.getRss060SidRight());
            paramMdl.setFormData(form);
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
        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        RssBiz rssBiz = new RssBiz(con, reqMdl);
        String opCode = getInterMessage(reqMdl, "cmn.change");

        con.setAutoCommit(true);
        rssBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "");
        con.setAutoCommit(false);
    }
}
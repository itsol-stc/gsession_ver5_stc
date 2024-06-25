package jp.groupsession.v2.bmk.bmk130;

import java.lang.reflect.InvocationTargetException;
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
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 個人設定 表示件数設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk130Action extends AbstractBookmarkAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk130Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;

        Bmk130Form bmkForm = (Bmk130Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("bmk130commit")) {
            //登録
            forward = __doCommit(map, bmkForm, req, res, con);
        } else if (cmd.equals("bmk130back")) {
            //戻るボタンクリック
            if (bmkForm.getBmkBackMainFlg() == 1) {
                forward = map.findForward("gf_main");
            } else {
                forward = map.findForward("bmk120");
            }
        } else if (cmd.equals("changeViewBmk")) {
            log__.debug("メイン表示設定 表示ブックマークの変更");
            forward = __doChangeViewBmk(map, bmkForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, bmkForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Bmk130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("登録処理");

        //セッションユーザSIDを取得する。
        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();

        //DB更新
        Bmk130Biz biz = new Bmk130Biz(getRequestModel(req));

        Bmk130ParamModel paramMdl = new Bmk130ParamModel();
        paramMdl.setParam(form);
        int addEditFlg = biz.setBmkUconfSetting(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);


        //ログ出力処理
        BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
        String opCode = "";

        GsMessage gsMsg = new GsMessage();
        String msg = "";
        //登録
        if (addEditFlg == GSConstBookmark.BMK_MODE_TOUROKU) {
            msg = gsMsg.getMessage(req, "cmn.entry");
            opCode = msg;
        //編集
        } else {
            msg = gsMsg.getMessage(req, "cmn.change");
            opCode = msg;
        }

        bmkBiz.outPutLog(opCode, GSConstLog.LEVEL_INFO, "", map.getType());

        //共通メッセージ画面(OK)を表示
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
        Bmk130Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        if (form.getBmkBackMainFlg() == 1) {
            urlForward = map.findForward("gf_main");
        } else {
            urlForward = map.findForward("bmk120");
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.display.settings");

        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Bmk130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        //セッションユーザSIDを取得する。
        int sessionUserSid = getSessionUserSid(req);

        con.setAutoCommit(true);
        Bmk130Biz biz = new Bmk130Biz(getRequestModel(req));

        Bmk130ParamModel paramMdl = new Bmk130ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }



    /**
     * <br>[機  能] メイン表示設定 表示ブックマークの変更時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     * @return ActionForward
     */
    private ActionForward __doChangeViewBmk(
        ActionMapping map,
        Bmk130Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException,
                        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //個人ブックマークのメイン表示区分を更新する
        Bmk130Biz biz = new Bmk130Biz(getRequestModel(req));
        Bmk130ParamModel paramMdl = new Bmk130ParamModel();
        paramMdl.setParam(form);
        biz.updateMaindisp(con, paramMdl);
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con);
    }
}

package jp.groupsession.v2.anp.anp999;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] 安否確認モバイル版のメッセージ画面のAction
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp999Action extends AbstractAnpiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp999Action.class);


    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                     org.apache.struts.action.ActionForm,
     *                     javax.servlet.http.HttpServletRequest,
     *                     javax.servlet.http.HttpServletResponse,
     *                     java.sql.Connection)
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        ActionForward forward = null;
        Anp999Form thisForm = (Anp999Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        String okBtn = NullDefault.getString(req.getParameter(Anp999Form.TYPE_OK_VALUE), "");

        FirewallBiz fwBiz = FirewallBiz.getInstance();
        if (fwBiz.additionalCheckForAnpAncer(
                __getAccessUsrModel(thisForm, req, con), con) == false) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        log__.debug("cmd = " + cmd);
        if (cmd.equals("BACKUP_PROG")) {
            log__.debug("バックアップ実行中エラーメッセージ");
            __setBackupProgressDispParam(map, thisForm, req, res);
            forward = map.getInputForward();
        } else if (!StringUtil.isNullZeroString(okBtn)) {
            log__.debug("OKボタンクリック");
            forward = map.findForward("anpinput");
        } else {
            log__.debug("デフォルト");
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] バックアップ実行中エラー画面の設定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     */
    private void __setBackupProgressDispParam(
            ActionMapping map,
            Anp999Form form,
            HttpServletRequest req,
            HttpServletResponse res) {


        //ドメインエラー警告画面パラメータの設定
        form.setType(Anp999Form.TYPE_OK);
        form.setIcon(Anp999Form.ICON_INFO);
        MessageResources msgRes = getResources(req);
        form.setMessage(msgRes.getMessage("error.backing.up"));

        req.setAttribute("anp999Form", form);
    }

    /**
     * <p>データベースへのコネクションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoConnection(HttpServletRequest req, ActionForm form) {
        return true;
    }
    /**
    *
    * <br>[機  能] 指定ユーザモデルを取得
    * <br>[解  説]
    * <br>[備  考]
    * @param form
    * @param req
    * @param con
    * @return 指定ユーザモデル
    * @throws SQLException
    * @throws NumberFormatException
    */
   private BaseUserModel __getAccessUsrModel(Anp999Form form,
           HttpServletRequest req,
           Connection con) throws NumberFormatException, SQLException {

       if (canNoSessionAccess(req, form)) {
           //セッション管理なしの場合は、パラメータユーザSID
           AuthDao adao = new AuthDao(con);
           BaseUserModel smodel = null;
           smodel = adao.selectLogin(Integer.valueOf(form.getUserSid()));

           return smodel;

       } else {
           //セッション管理ありの場合は、セッションユーザSID
           HttpSession session = req.getSession();
           BaseUserModel usModel = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
           return usModel;
       }
   }

}
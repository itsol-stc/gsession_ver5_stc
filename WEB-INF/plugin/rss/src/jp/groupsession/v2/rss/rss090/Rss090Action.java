package jp.groupsession.v2.rss.rss090;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.rss.AbstractRssAdminAction;


/**
 * <br>[機  能] RSSリーダー RSS購読メンバー一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss090Action extends AbstractRssAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss090Action.class);

    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {
        if (_isSystemAdmin(req, con)) {
            return true; //システム管理者の場合はプラグインチェックをしない
        }
        return super._isAccessPlugin(req, form, con);
    }

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
        Rss090Form myForm = (Rss090Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("arrorw_right")) {
            //次のページ
            log__.debug("次のページクリック");
            myForm.setRss090page1(myForm.getRss090page1() + 1);
            forward = __doInit(map, myForm, req, res, con);
        } else if (cmd.equals("arrorw_left")) {
            //前のページ
            log__.debug("前のページクリック");
            myForm.setRss090page1(myForm.getRss090page1() - 1);
            forward = __doInit(map, myForm, req, res, con);
        } else if (cmd.equals("pageChange")) {
            log__.debug("ページコンボチェンジ");
            forward = __doInit(map, myForm, req, res, con);
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
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rss090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Rss090ParamModel paramMdl = new Rss090ParamModel();
        paramMdl.setParam(form);
        Rss090Biz biz = new Rss090Biz();
        biz.setUsrList(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }
}
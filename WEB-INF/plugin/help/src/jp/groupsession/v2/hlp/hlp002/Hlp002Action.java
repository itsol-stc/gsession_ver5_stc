package jp.groupsession.v2.hlp.hlp002;

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
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ヘルプ ヘッダーのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp002Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Hlp002Action.class);

    /** CMD :検索 */
    private static final String CMD_SEARCH = "search";

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
     * <p>
     * セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>
     * サブクラスでこのメソッドをオーバーライドして使用する
     *
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        log__.debug("start");
        ActionForward forward = null;
        Hlp002Form myForm = (Hlp002Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();


        if (CMD_SEARCH.equals(cmd)) {
            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, myForm, req, res, con);

        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, myForm, req, res, con);

        }

        log__.debug("end");
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Hlp002Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {

        log__.debug("初期表示");
        ActionForward forward = null;

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSearch(ActionMapping map,
                                    Hlp002Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {

        log__.debug("*** 検索ワード: " + form.getHlp002SearchText());

        if (StringUtil.isNullZeroString(form.getHlp002SearchText())) {
            return map.findForward("hlp010");
        }

        return map.findForward("hlp020");
    }

}


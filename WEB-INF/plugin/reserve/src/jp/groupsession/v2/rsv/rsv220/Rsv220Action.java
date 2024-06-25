package jp.groupsession.v2.rsv.rsv220;

import java.lang.reflect.InvocationTargetException;
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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.rsv.AbstractReserveAdminAction;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約基本設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv220Action extends AbstractReserveAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv220Action.class);

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
        Rsv220Form rsvForm = (Rsv220Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("rsv220ok")) {
            //登録ボタンクリック
            forward = __doKakunin(map, rsvForm, req, res, con);
        } else if (cmd.equals("rsv220back")) {
            //戻るボタンクリック
            forward = __doBack(map, rsvForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, rsvForm, req, res, con);
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
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Rsv220Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);
        return map.findForward("kanrisya_basic_kakunin");
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
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    private ActionForward __doInit(ActionMapping map, Rsv220Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        
        log__.debug("初期表示");
        con.setAutoCommit(true);
        Rsv220Biz biz = new Rsv220Biz(getRequestModel(req), con);

        Rsv220ParamModel paramMdl = new Rsv220ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Rsv220Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("kanrisya_settei");
    }
}

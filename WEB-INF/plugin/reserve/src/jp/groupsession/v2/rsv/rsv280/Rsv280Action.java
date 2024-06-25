package jp.groupsession.v2.rsv.rsv280;

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
 * <br>[機  能] 施設予約 管理者設定 施設予約初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv280Action extends AbstractReserveAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv280Action.class);

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
        Rsv280Form rsvForm = (Rsv280Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("rsv280ok")) {
            //OKボタンクリック
            forward = __doKakunin(map, rsvForm, req, res, con);
        } else if (cmd.equals("rsv280back")) {
            //戻るボタンクリック
            forward = map.findForward("backAdminMenu");
        } else {
            //初期表示
            forward = __doInit(map, rsvForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>初期表示
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
    private ActionForward __doInit(ActionMapping map, Rsv280Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        Rsv280Biz biz = new Rsv280Biz();

        Rsv280ParamModel paramMdl = new Rsv280ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>確認処理
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
    private ActionForward __doKakunin(ActionMapping map, Rsv280Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {

        //入力チェック
        ActionErrors errors = form.validateTimeRsv280(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        
        errors = form.validateRsv280(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        return map.findForward("moveKakunin");
    }
}

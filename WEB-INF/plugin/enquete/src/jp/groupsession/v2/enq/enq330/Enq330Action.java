package jp.groupsession.v2.enq.enq330;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq310.Enq310Action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] アンケート 結果確認（詳細）画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq330Action extends Enq310Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq330Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = null;
        Enq330Form thisForm = (Enq330Form) form;

        // 結果公開の閲覧権限チェック
        con.setAutoCommit(true);
        try {
            EnqCommonBiz enqBiz = new EnqCommonBiz();
            if (!enqBiz.canViewResultEnquete(getRequestModel(req), con, thisForm.getAnsEnqSid())) {
                return getSubmitErrorPage(map, req);
            }
        } finally {
            con.setAutoCommit(false);
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        if (cmd.equals("enq330back")) {
            //戻る
            log__.debug("戻る");
            forward = map.findForward("enq330back");

        } else if (cmd.equals("enq330search")) {
            //検索
            log__.debug("検索");
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setEnq330pageTop(thisForm.getEnq330pageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setEnq330pageTop(thisForm.getEnq330pageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else {
            //初期表示処理
            log__.debug("初期表示処理");

            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq330Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        try {
            Enq330ParamModel paramMdl = new Enq330ParamModel();
            paramMdl.setParam(form);
            Enq330Biz biz = new Enq330Biz();
            biz.setInitData(paramMdl, getRequestModel(req), con);
            paramMdl.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doSearch(ActionMapping map,
                                   Enq330Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        log__.debug("検索処理");

        // 入力チェック
        ActionErrors errors = form.validateEnq330(getRequestModel(req), form.getEnq330queKbn());
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //検索条件の保持を行う
        form.setEnq330pageTop(1);
        form.setEnq330pageBottom(1);
        form.setEnq330svGroup(form.getEnq330group());
        form.setEnq330svUser(form.getEnq330user());
        form.setEnq330svAnsText(form.getEnq330ansText());
        form.setEnq330svAnsNumKbn(form.getEnq330ansNumKbn());
        form.setEnq330svAnsNumFrom(form.getEnq330ansNumFrom());
        form.setEnq330svAnsNumTo(form.getEnq330ansNumTo());
        form.setEnq330svAnsDateFromYear(form.getEnq330ansDateFromYear());
        form.setEnq330svAnsDateFromMonth(form.getEnq330ansDateFromMonth());
        form.setEnq330svAnsDateFromDay(form.getEnq330ansDateFromDay());
        form.setEnq330svAnsDateToYear(form.getEnq330ansDateToYear());
        form.setEnq330svAnsDateToMonth(form.getEnq330ansDateToMonth());
        form.setEnq330svAnsDateToDay(form.getEnq330ansDateToDay());
        form.setEnq330svAns(form.getEnq330ans());

        return __doInit(map, form, req, res, con);
    }
}

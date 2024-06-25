package jp.groupsession.v2.bmk.bmk150;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 新着ブックマーク一覧画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk150Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk150Action.class);

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

        log__.debug("bmk150Action START");

        ActionForward forward = null;
        Bmk150Form thisForm = (Bmk150Form) form;
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("--- cmd :" + cmd);

        if (cmd.equals("changePage")) {
            //ページコンボ変更
            forward = __doChangeMode(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //左矢印押下
            forward = __doPageMinus(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //右矢印押下
            forward = __doPagePlus(map, thisForm, req, res, con);

        } else if (cmd.equals("bmkAdd")) {
            //追加
            forward = map.findForward("bmk020");

        } else if (cmd.equals("commentList")) {
            //コメント・評価
            forward = map.findForward("bmk070");

        } else if (cmd.equals("backPage")) {
            //戻る
            log__.debug("遷移元：" + thisForm.getReturnPage());
            forward = map.findForward(thisForm.getReturnPage());

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
                                    Bmk150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(true);
        Bmk150Biz biz = new Bmk150Biz(getRequestModel(req));

        Bmk150ParamModel paramMdl = new Bmk150ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getSessionUserModel(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ページコンボ変更処理
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
    private ActionForward __doChangeMode(ActionMapping map,
                                          Bmk150Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws Exception {
        __doInit(map, form, req, res, con);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 左矢印押下処理
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
    private ActionForward __doPageMinus(ActionMapping map,
            Bmk150Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws Exception {

        //ページ数取得
        int page = form.getBmk150PageNum();
        page -= 1;
        if (page < 1) {
            page = 1;
        }

        //調整後ページ数セット
        form.setBmk150PageNum(page);
        __doInit(map, form, req, res, con);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 右矢印押下処理
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
    private ActionForward __doPagePlus(ActionMapping map,
            Bmk150Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        //ページ数取得
        int page = form.getBmk150PageNum();
        page += 1;

        //調整後ページ数セット
        form.setBmk150PageNum(page);
        __doInit(map, form, req, res, con);
        return map.getInputForward();
    }
}
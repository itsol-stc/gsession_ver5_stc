package jp.groupsession.v2.ip.ipk070;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.AbstractIpAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] IP管理 詳細検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk070Action extends AbstractIpAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk070Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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
        Ipk070Form ipkForm = (Ipk070Form) form;
        log__.debug("START");

        //コマンド
        String cmd = NullDefault.getString(ipkForm.getCMD(), "");
        log__.debug("=== cmd === " + cmd);

        //戻るボタンクリック
        if (cmd.equals("ipk070Return")) {
            if (StringUtil.isNullZeroString(ipkForm.getNetSid())) {
                forward = map.findForward("ipk010Return");
            } else {
                forward = map.findForward("ipk040Return");
            }

        //タイトルクリック
        } else if (cmd.equals("pageSort")) {
            forward = __doInitTitleClick(map, ipkForm, req, con);

        //グループコンボ変更
        } else if (cmd.equals("changeGroup")) {
            forward = __doChangeGrpCmb(map, ipkForm, con, req);

        //一覧画面の検索ボタンクリック
        } else if (cmd.equals("search")) {
            forward = __doInitSearch(map, ipkForm, req, con);

        //検索ボタンクリック
        } else if (cmd.equals("doSearch")) {
            forward = __doSearch(map, ipkForm, req, con);

        //詳細ボタンクリック
        } else if (cmd.equals("ipk070detail")) {
            forward = map.findForward("ipk070detail");

        //詳細画面から遷移してきた場合
        } else if (cmd.equals("ipReturn")) {
            forward = __doReturnDsp(map, ipkForm, con, req);

        //右矢印クリック
        } else if (cmd.equals("arrow_right")) {
            forward = __setNextPage(map, ipkForm, con, req);

        //左矢印クリック
        } else if (cmd.equals("arrow_left")) {
            forward = __setBeforePage(map, ipkForm, con, req);

        //ページコンボ変更
        } else if (cmd.equals("ipk070PageSelect")) {
            forward = __doChangePageCmb(map, ipkForm, con, req);

        //初期表示
        } else {
            forward = __doInit(map, ipkForm, con, req);
        }
        log__.info("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ipk070Form form,
        Connection con,
        HttpServletRequest req)
        throws SQLException {

        try {
            con.setAutoCommit(true);
            RequestModel reqMdl = getRequestModel(req);
            Ipk070ParamModel paramMdl = new Ipk070ParamModel();
            paramMdl.setParam(form);
            Ipk070Biz biz = new Ipk070Biz();
            //検索結果を設定する。
            biz.setSearchData(paramMdl, con, reqMdl);
            biz.setInitData(paramMdl, con, reqMdl);
            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doInitAg(ActionMapping map,
        Ipk070Form form,
        Connection con,
        HttpServletRequest req)
        throws SQLException {

        try {
            con.setAutoCommit(true);
            Ipk070ParamModel paramMdl = new Ipk070ParamModel();
            paramMdl.setParam(form);
            Ipk070Biz biz = new Ipk070Biz();
            biz.setInitDataAg(paramMdl, con, getRequestModel(req));
            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return map.getInputForward();
    }

    /**
     * <br>[機  能] タイトルクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doInitTitleClick(ActionMapping map,
        Ipk070Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        //検索結果を設定する。
        Ipk070ParamModel paramMdl = new Ipk070ParamModel();
        paramMdl.setParam(form);
        Ipk070Biz biz = new Ipk070Biz();
        biz.setSearchData(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);
        return __doInitAg(map, form, con, req);
    }


    /**
     * <br>[機  能] グループコンボ変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doChangeGrpCmb(ActionMapping map,
        Ipk070Form form, Connection con, HttpServletRequest req)
    throws SQLException {

        con.setAutoCommit(true);
        //検索結果を設定する。
        Ipk070ParamModel paramMdl = new Ipk070ParamModel();
        paramMdl.setParam(form);
        Ipk070Biz biz = new Ipk070Biz();
        biz.setSearchData(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);
        return __doInitAg(map, form, con, req);
    }

    /**
     * <br>[機  能] 検索を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doSearch(ActionMapping map,
        Ipk070Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException {

        try {
            //saveパラメータを設定する。
            __setParam(form);
            //入力チェックを行う
            ActionErrors errors = null;
            errors = __doValidateCheck(form, con, req);
            if (errors != null && !errors.isEmpty()) {
                log__.debug("===========入力チェック===========");
                addErrors(req, errors);
                return __doInitAg(map, form, con, req);
            }
            //検索結果を設定する。
            Ipk070ParamModel paramMdl = new Ipk070ParamModel();
            paramMdl.setParam(form);
            Ipk070Biz biz = new Ipk070Biz();
            biz.setSearchData(paramMdl, con, getRequestModel(req));
            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return __doInitAg(map, form, con, req);
    }

    /**
     * <br>[機  能] 検索時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param con コネクション
     * @param req リクエスト
     * @return errors ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __doValidateCheck(
            Ipk070Form form, Connection con, HttpServletRequest req)
    throws SQLException {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textIpAdInfo = gsMsg.getMessage("ipk.ipk070.3");
        String textSortOrder = gsMsg.getMessage("cmn.sort.order");

        ActionErrors errors = null;
        con.setAutoCommit(true);
        //入力チェック
        errors = form.ipk070ValidateCheck(reqMdl);
        if (!errors.isEmpty()) {
            return errors;
        }

        Ipk070ParamModel paramMdl = new Ipk070ParamModel();
        paramMdl.setParam(form);
        Ipk070Biz biz = new Ipk070Biz();
        int count = biz.getSearchResult(paramMdl, con, reqMdl);
        paramMdl.setFormData(form);

        if (count < 1) {
            ActionMessage msg = new ActionMessage("search.data.notfound", textIpAdInfo,
                    "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            return errors;
        }

        if (form.getIpk070SearchSortKey1().equals(form.getIpk070SearchSortKey2())) {
            ActionMessage msg = new ActionMessage("error.select.dup.list", textSortOrder);
            errors.add("error.select.dup.list", msg);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] 次のページを表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setNextPage(ActionMapping map,
        Ipk070Form form,
        Connection con,
        HttpServletRequest req)
    throws SQLException {

        try {
            con.setAutoCommit(true);
            Ipk070ParamModel paramMdl = new Ipk070ParamModel();
            paramMdl.setParam(form);
            Ipk070Biz biz = new Ipk070Biz();

            //IPアドレス一覧情報を取得
            biz.setNextPage(paramMdl);
            //検索結果を設定する。
            biz.setSearchData(paramMdl, con, getRequestModel(req));

            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return __doInitAg(map, form, con, req);
    }

    /**
     * <br>[機  能] 前のページを表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setBeforePage(ActionMapping map,
        Ipk070Form form,
        Connection con,
        HttpServletRequest req)
    throws SQLException {

        try {
            con.setAutoCommit(true);

            Ipk070ParamModel paramMdl = new Ipk070ParamModel();
            paramMdl.setParam(form);
            Ipk070Biz biz = new Ipk070Biz();

            //IPアドレス一覧情報を取得
            biz.setBeforePage(paramMdl);
            //検索結果を設定する。
            biz.setSearchData(paramMdl, con, getRequestModel(req));

            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return __doInitAg(map, form, con, req);
    }

    /**
     * <br>[機  能] 詳細画面から遷移してきた時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doReturnDsp(ActionMapping map,
        Ipk070Form form,
        Connection con,
        HttpServletRequest req)
    throws SQLException {

        try {
            con.setAutoCommit(true);
            Ipk070ParamModel paramMdl = new Ipk070ParamModel();
            paramMdl.setParam(form);
            Ipk070Biz biz = new Ipk070Biz();

            //検索結果を設定する。
            biz.setSearchData(paramMdl, con, getRequestModel(req));
            paramMdl.setFormData(form);

            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw e;
        }
        return __doInitAg(map, form, con, req);
    }

    /**
     * <br>[機  能] ページコンボ変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doChangePageCmb(ActionMapping map,
        Ipk070Form form,
        Connection con,
        HttpServletRequest req)
        throws SQLException {

        try {
            con.setAutoCommit(true);

            Ipk070ParamModel paramMdl = new Ipk070ParamModel();
            paramMdl.setParam(form);
            Ipk070Biz biz = new Ipk070Biz();

            //ページ数をセット
            biz.setPageCmb(paramMdl);
            //検索結果を設定する。
            biz.setSearchData(paramMdl, con, getRequestModel(req));

            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return __doInitAg(map, form, con, req);
    }

    /**
     * <br>[機  能] 一覧画面の検索ボタン押下で画面遷移してきた時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doInitSearch(ActionMapping map,
        Ipk070Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException {

        try {
            con.setAutoCommit(true);
            String[] searchTarget = {"1", "2", "3"};
            form.setIpk070SearchTarget(searchTarget);
            form.setIpk070SltNet(form.getNetSid());
            //saveパラメータをセットする。
            __setParam(form);
            //入力チェックを行う
            ActionErrors errors = null;
            errors = __doValidateCheck(form, con, req);
            if (errors != null && !errors.isEmpty()) {
                log__.debug("===========入力チェック===========");
                addErrors(req, errors);
                return __doInitAg(map, form, con, req);
            }

            Ipk070ParamModel paramMdl = new Ipk070ParamModel();
            paramMdl.setParam(form);
            Ipk070Biz biz = new Ipk070Biz();

            if (!StringUtil.isNullZeroString(form.getNetSid())) {
                biz.setIpk040Search(paramMdl);
            }
            //検索結果を設定する。
            biz.setSearchData(paramMdl, con, getRequestModel(req));

            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return __doInit(map, form, con, req);
    }
    /**
     * <br>[機  能] ｓｖパラメータを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    private void __setParam(Ipk070Form form) {

        form.setIpk070SvSltNet(form.getIpk070SltNet());
        form.setIpk070SvGrpSid(form.getIpk070SltGroup());
        form.setIpk070SvUsrSid(form.getIpk070SltUser());
        form.setIpk070SvKeyWord(form.getIpk070KeyWord());
        form.setIpk070SvKeyWordkbn(form.getIpk070KeyWordkbn());
        form.setIpk070SvSearchSortKey1(form.getIpk070SearchSortKey1());
        form.setIpk070SvSearchSortKey2(form.getIpk070SearchSortKey2());
        form.setIpk070SvSearchOrderKey1(form.getIpk070SearchOrderKey1());
        form.setIpk070SvSearchOrderKey2(form.getIpk070SearchOrderKey2());
        form.setIpk070SvSearchTarget(form.getIpk070SearchTarget());
    }
}
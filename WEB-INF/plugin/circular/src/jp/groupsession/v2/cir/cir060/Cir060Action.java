package jp.groupsession.v2.cir.cir060;

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
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 回覧板 詳細検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir060Action extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir060Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Cir060");
        ActionForward forward = null;

        Cir060Form thisForm = (Cir060Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backList")) {
            log__.debug("戻る");
            forward = map.findForward("backList");

        } else if (cmd.equals("searchCir")) {
            log__.debug("検索");
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("prev")) {
            log__.debug("前ページ");
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("next")) {
            log__.debug("次ページ");
            forward = __doNext(map, thisForm, req, res, con);

        } else if (cmd.equals("view")) {
            log__.debug("確認(詳細へ)");
            forward = __doDspView(map, thisForm, req, res, con);

        } else if (cmd.equals("searchAgain") || cmd.equals("back")) {
            log__.debug("回覧先選択から戻る、又は画面再表示");
            forward = __doDsp(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Cir060");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Cir060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期値をセット
        //回覧板種別
        if (form.getCir010cmdMode().equals(GSConstCircular.MODE_LABEL)) {
            form.setCir060syubetsu(Integer.parseInt(GSConstCircular.MODE_JUSIN));
        } else {
            form.setCir060syubetsu(NullDefault.getInt(form.getCir010cmdMode(), -1));
        }

        //検索対象
        form.setCir060searchTarget(CirCommonBiz.getDefultSearchTarget());

//        String searchWord = NullDefault.getString(form.getCir010searchWord(), "");
//        if (!searchWord.equals("")) {
//            //検索ワードが入力済みの場合

            //検索ワードが未入力でも検索実行
            //検索実行
        return __doSearch(map, form, req, res, con);
//        }

//        //画面に常に表示する値をセットする
//        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 画面に常に表示する値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Cir060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        con.setAutoCommit(true);
        try {
            RequestModel reqMdl = getRequestModel(req);
            Cir060ParamModel paramMdl = new Cir060ParamModel();
            paramMdl.setParam(form);

            //初期表示情報を画面にセットする
            Cir060Biz biz = new Cir060Biz();
            biz.setInitData(paramMdl, con, reqMdl);

            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
            paramMdl.setCir060searchUse(CommonBiz.getWebSearchUse(pconfig));

            //検索結果を画面にセットする
            ActionErrors errors = biz.setSearchData(paramMdl, con, userSid, reqMdl);
            paramMdl.setFormData(form);

            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setCir060searchFlg(false);
                return __doDsp(map, form, req, res, con);
            }
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doSearch(
        ActionMapping map,
        Cir060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateSearchCir(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setCir060searchFlg(false);
            return __doDsp(map, form, req, res, con);
        }

        form.setCir060pageNum1(1);

        //検索条件パラメータをセーブフィールドへ移行
        form.saveSearchParm();

        //検索フラグON
        form.setCir060searchFlg(true);

        //画面を再表示
        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doPrev(
        ActionMapping map,
        Cir060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getCir060pageNum1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setCir060pageNum1(page);
        form.setCir060pageNum2(page);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doNext(
        ActionMapping map,
        Cir060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getCir060pageNum1();
        page += 1;
        form.setCir060pageNum1(page);
        form.setCir060pageNum2(page);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 回覧板タイトルクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDspView(
        ActionMapping map,
        Cir060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;

        //回覧板種別(セーブ)で処理を分岐
        int svSyubetsu = form.getCir060svSyubetsu();

        if (svSyubetsu == Integer.parseInt(GSConstCircular.MODE_JUSIN)) {
            //受信回覧板確認画面へ
            forward = map.findForward("jusin");

        } else if (svSyubetsu == Integer.parseInt(GSConstCircular.MODE_SOUSIN)) {
            //送信回覧板状況確認画面へ
            forward = map.findForward("sousin");

        } else if (svSyubetsu == Integer.parseInt(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱の中身を参照
            String sojuKbn = NullDefault.getString(form.getCir010sojuKbn(), "");

            if (sojuKbn.equals(GSConstCircular.MODE_JUSIN)) {
                //受信回覧板確認画面へ
                forward = map.findForward("jusin");
            } else if (sojuKbn.equals(GSConstCircular.MODE_SOUSIN)) {
                //送信回覧板状況確認画面へ
                forward = map.findForward("sousin");
            }
        }

        return forward;
    }


}

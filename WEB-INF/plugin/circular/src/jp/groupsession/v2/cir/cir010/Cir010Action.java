package jp.groupsession.v2.cir.cir010;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirLabelDao;
import jp.groupsession.v2.cir.model.CirLabelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir010Action extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir010Action.class);

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

        log__.debug("START_Cir010");
        ActionForward forward = null;

        Cir010Form thisForm = (Cir010Form) form;
        CirCommonBiz biz = new CirCommonBiz();

        //アカウントが未選択の場合、デフォルトアカウントを設定する
        if (thisForm.getCirViewAccount() <= 0) {
            thisForm.setCirViewAccount(
                    biz.getDefaultAccount(con, getSessionUserSid(req)));
        }

        //選択されているアカウントが使用可能かを判定する
        if (!biz.canUseAccount(
                con, getSessionUserSid(req), thisForm.getCirViewAccount())) {
            return getAuthErrorPage(map, req);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        String cmdMode = NullDefault.getString(thisForm.getCir010cmdMode(), "").trim();
        if (cmd.equals("admConf")) {
            log__.debug("管理者設定");
            forward = map.findForward("aset");
        } else if (cmd.equals("pset")) {
            log__.debug("個人設定");
            forward = map.findForward("pset");

        } else if (cmd.equals("add")) {
            log__.debug("新規回覧板");

            //回覧板作成可能ユーザかを判定する
            RequestModel reqMdl = getRequestModel(req);
            if (!biz.canCreateUserCir(con, reqMdl)) {
                return getSubmitErrorPage(map, req);
            }

            forward = map.findForward("add");

        } else if (cmd.equals("delete")) {
            log__.debug("削除");

            //回覧板作成可能ユーザかを判定する
            if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
                RequestModel reqMdl = getRequestModel(req);
                if (!biz.canCreateUserCir(con, reqMdl)) {
                    return getSubmitErrorPage(map, req);
                }
            }
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");

            //回覧板作成可能ユーザかを判定する
            if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
                RequestModel reqMdl = getRequestModel(req);
                if (!biz.canCreateUserCir(con, reqMdl)) {
                    return getSubmitErrorPage(map, req);
                }
            }

            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (cmd.equals("comeback")) {
            log__.debug("元に戻す");
            forward = __doComeBack(map, thisForm, req, res, con);

        } else if (cmd.equals("comebackExe")) {
            log__.debug("元に戻す実行");
            forward = __doComeBackExe(map, thisForm, req, res, con);

        } else if (cmd.equals("prev")) {
            log__.debug("前ページ");
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("next")) {
            log__.debug("次ページ");
            forward = __doNext(map, thisForm, req, res, con);

        } else if (cmd.equals("jusin")) {
            log__.debug("受信タブクリック");
            forward = __doJusin(map, thisForm, req, res, con);

        } else if (cmd.equals("sousin")) {
            log__.debug("送信済みタブクリック");
            forward = __doSousin(map, thisForm, req, res, con);

        } else if (cmd.equals("gomi")) {
            log__.debug("ゴミ箱タブクリック");
            forward = __doGomi(map, thisForm, req, res, con);

        } else if (cmd.equals("label")) {
            log__.debug("ラベルフォルダクリック");
            forward = __doLabel(map, thisForm, req, res, con);

        }  else if (cmd.equals("view")) {
            log__.debug("確認(詳細へ)");
            forward = __doDspView(map, thisForm, req, res, con);
        } else if (cmd.equals("gomibakoClear")) {
            log__.debug("ゴミ箱を空にするクリック");
            __doClearConfirmationData(map, thisForm, req, res, con);
        } else if (cmd.equals("gomibakoClearExe")) {
            log__.debug("ゴミ箱を空にする実行");
            __doAllDeleteExe(map, thisForm, req, res, con);
        } else if (cmd.equals("search")) {
            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("accountConf")) {
            log__.debug("アカウント管理ボタン押下");
            forward = map.findForward("accountConf");
        } else if (cmd.equals("addLabel")) {
            log__.debug("ラベル追加ボタン押下");
            __doDialogLabel(map, thisForm, req, res, con);
        } else if (cmd.equals("delLabel")) {
            log__.debug("ラベルの削除");
            __doDialogLabel(map, thisForm, req, res, con);
        } else if (cmd.equals("addCircularLabel")) {
            log__.debug("ラベル登録");
            __doAddDelLabel(map, thisForm, req, res, con, GSConstCircular.CIR_LABEL_ADD);
        } else if (cmd.equals("delCircularLabel")) {
            log__.debug("ラベル削除");
            __doAddDelLabel(map, thisForm, req, res, con, GSConstCircular.CIR_LABEL_DEL);
        }  else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Cir010");
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
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        Cir010ParamModel paramMdl = new Cir010ParamModel();
        paramMdl.setParam(form);
        Cir010Biz biz = new Cir010Biz();
        biz.setInitData(paramMdl, con, userSid, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
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
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getCir010pageNum1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setCir010pageNum1(page);
        form.setCir010pageNum2(page);

        return __doInit(map, form, req, res, con);
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
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getCir010pageNum1();
        page += 1;
        form.setCir010pageNum1(page);
        form.setCir010pageNum2(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 受信タブクリック時の処理
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
    private ActionForward __doJusin(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        form.setCir010cmdMode(GSConstCircular.MODE_JUSIN);
        form.setCir010pageNum1(1);
        form.setCir010pageNum2(1);
        form.setCir010delInfSid(null);
        form.setCir010orderKey(GSConst.ORDER_KEY_DESC);
        form.setCir010sortKey(GSConstCircular.SORT_DATE);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 送信済みタブクリック時の処理
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
    private ActionForward __doSousin(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        form.setCir010cmdMode(GSConstCircular.MODE_SOUSIN);
        form.setCir010pageNum1(1);
        form.setCir010pageNum2(1);
        form.setCir010delInfSid(null);
        form.setCir010orderKey(GSConst.ORDER_KEY_DESC);
        form.setCir010sortKey(GSConstCircular.SORT_DATE);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] ゴミ箱タブクリック時の処理
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
    private ActionForward __doGomi(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        form.setCir010cmdMode(GSConstCircular.MODE_GOMI);
        form.setCir010pageNum1(1);
        form.setCir010pageNum2(1);
        form.setCir010delInfSid(null);
        form.setCir010orderKey(GSConst.ORDER_KEY_DESC);
        form.setCir010sortKey(GSConstCircular.SORT_DATE);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] ラベルフォルダクリック時の処理
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
    private ActionForward __doLabel(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ラベルチェック
        ActionErrors errors
            = form.validateLabelAccessCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setCir010cmdMode(GSConstCircular.MODE_JUSIN);
        } else {
            form.setCir010cmdMode(GSConstCircular.MODE_LABEL);
        }
        form.setCir010pageNum1(1);
        form.setCir010pageNum2(1);
        form.setCir010delInfSid(null);
        form.setCir010orderKey(GSConst.ORDER_KEY_DESC);
        form.setCir010sortKey(GSConstCircular.SORT_DATE);

        return __doInit(map, form, req, res, con);
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
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;

        //処理モードで処理を分岐
        String cmdMode = NullDefault.getString(form.getCir010cmdMode(), "");
        if (cmdMode.equals(GSConstCircular.MODE_JUSIN)) {
            //受信回覧板確認画面へ
            forward = map.findForward("jusin");

        } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
            //送信回覧板状況確認画面へ
            forward = map.findForward("sousin");

        } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱の中身を参照
            String sojuKbn = NullDefault.getString(form.getCir010sojuKbn(), "");

            if (sojuKbn.equals(GSConstCircular.MODE_JUSIN)) {
                //受信回覧板確認画面へ
                forward = map.findForward("jusin");
            } else if (sojuKbn.equals(GSConstCircular.MODE_SOUSIN)) {
                //送信回覧板状況確認画面へ
                forward = map.findForward("sousin");
            }
        } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {
            //中身を参照
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

    /**
     * <br>[機  能] 削除時の処理
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
    private ActionForward __doDelete(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        ActionErrors errors = form.validateSelectCheck(reqMdl, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            // チェック初期化
            form.setCir010delInfSid(null);
            return __doInit(map, form, req, res, con);
        }

        //削除する回覧板のタイトルを取得する
        con.setAutoCommit(true);
        Cir010ParamModel paramMdl = new Cir010ParamModel();
        paramMdl.setParam(form);
        Cir010Biz biz = new Cir010Biz();
        String deleteCir = biz.getDeleteCirName(
                paramMdl, paramMdl.getCirViewAccount(), con, reqMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, deleteCir);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param deleteCir 削除する回覧板
     * @return ActionForward
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        String deleteCir) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("init");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("init");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textCir = gsMsg.getMessage("cir.5");
        String cmdMode = NullDefault.getString(form.getCir010cmdMode(), "");
        if (cmdMode.equals(GSConstCircular.MODE_JUSIN)) {
            //受信
            if (form.getMikakkuCount() > 0) {
                //未確認データ有り
                cmn999Form.setMessage(msgRes.getMessage(
                        "move.gomibako.cmn", textCir, deleteCir));
            } else {
                //未確認データ無し
                cmn999Form.setMessage(msgRes.getMessage(
                        "move.gomibako.cmn", textCir, deleteCir));
            }

        } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
            //送信済み
            cmn999Form.setMessage(msgRes.getMessage(
                    "move.gomibako.mail3",
                    new String[]{textCir, gsMsg.getMessage("cir.57"), deleteCir}));

        } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱
            cmn999Form.setMessage(msgRes.getMessage(
                    "sakujo.kakunin.list", textCir, deleteCir));
        } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {
            //ラベル
            cmn999Form.setMessage(msgRes.getMessage(
                    "sakujo.kakunin.list", textCir, deleteCir));
            if (form.getMikakkuCount() > 0) {
                //未確認データ有り
                cmn999Form.setMessage(msgRes.getMessage(
                        "move.gomibako.folder", textCir, deleteCir));
            } else {
                //未確認データ無し
                cmn999Form.setMessage(msgRes.getMessage(
                        "move.gomibako.folder2", textCir, deleteCir));
            }

        }

        //画面パラメータをセット
        cmn999Form.addHiddenParam("cir010delInfSid", form.getCir010delInfSid());
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
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
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //選択された回覧板を削除する
        RequestModel reqMdl = getRequestModel(req);
        Cir010ParamModel paramMdl = new Cir010ParamModel();
        paramMdl.setParam(form);
        Cir010Biz biz = new Cir010Biz();
        String deleteCir = biz.getLogSelectCirName(
                paramMdl, paramMdl.getCirViewAccount(), con, reqMdl);
        biz.deleteCir(paramMdl, con, paramMdl.getCirViewAccount());
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        //ログ出力処理
        StringBuilder sb = new StringBuilder();
        //アカウント名取得
        int accountSid = form.getCirViewAccount();
        CirAccountDao acDao = new CirAccountDao(con);
        String accountName =  acDao.getCirAccountName(accountSid);
        sb.append("[" + gsMsg.getMessage("wml.96") + "] ");
        sb.append(accountName);
        sb.append(System.getProperty("line.separator"));
        //回覧板名
        sb.append("[" + gsMsg.getMessage("cir.5") + "] ");
        sb.append(System.getProperty("line.separator"));
        sb.append(deleteCir);
        sb.append(System.getProperty("line.separator"));

        CirCommonBiz cirBiz = new CirCommonBiz(con);
        cirBiz.outPutLog(map, reqMdl, textDel, GSConstLog.LEVEL_TRACE, sb.toString());

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req, cmd);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param cmd コマンドパラメータ
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        String cmd) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("init");
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String textCir = gsMsg.getMessage(req, "cir.5");

        MessageResources msgRes = getResources(req);
        if (cmd.equals("deleteExe")) {
            //削除完了

            //処理モードで処理を分岐
            String cmdMode = NullDefault.getString(form.getCir010cmdMode(), "");
            if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
                //ゴミ箱から削除
                cmn999Form.setMessage(
                        msgRes.getMessage("sakujo.kanryo.object", textCir));
            } else {
                //上記以外
                cmn999Form.setMessage(
                        msgRes.getMessage("move.gomibako.object", textCir));
            }

        } else if (cmd.equals("comebackExe")) {
            //復帰(元に戻す)完了
            cmn999Form.setMessage(
                    msgRes.getMessage("move.former.object", textCir));

        } else {
            //ゴミ箱を空にする
            cmn999Form.setMessage(msgRes.getMessage("conf.clear.comp.gomibako"));
        }

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 元に戻す時の処理
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
    private ActionForward __doComeBack(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        ActionErrors errors = form.validateSelectCheck(reqMdl, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            // チェック初期化
            form.setCir010delInfSid(null);
            return __doInit(map, form, req, res, con);
        }

        //元に戻す回覧板のタイトルを取得する
        con.setAutoCommit(true);
        Cir010ParamModel paramMdl = new Cir010ParamModel();
        paramMdl.setParam(form);
        Cir010Biz biz = new Cir010Biz();
        String deleteCir = biz.getDeleteCirName(
                paramMdl, paramMdl.getCirViewAccount(), con, reqMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //元に戻す確認画面を表示
        return __setComeBackKakuninDsp(map, form, req, deleteCir);
    }

    /**
     * [機  能] 元に戻す確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param cirStr 元に戻す回覧板
     * @return ActionForward
     */
    private ActionForward __setComeBackKakuninDsp(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        String cirStr) {

        GsMessage gsMsg = new GsMessage();
        String textCir = gsMsg.getMessage(req, "cir.5");

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("init");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=comebackExe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("init");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(
                "move.former.mail", textCir, cirStr));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("cir010delInfSid", form.getCir010delInfSid());
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 元に戻す処理を行う(元に戻す実行)
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
    private ActionForward __doComeBackExe(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //選択された回覧板を元に戻す
        Cir010ParamModel paramMdl = new Cir010ParamModel();
        paramMdl.setParam(form);
        Cir010Biz biz = new Cir010Biz();
        biz.comeBackCir(paramMdl, con, paramMdl.getCirViewAccount());
        paramMdl.setFormData(form);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textReturn = gsMsg.getMessage("cmn.undo");
        String textEdit = gsMsg.getMessage("cmn.change");

        //ログ出力処理
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        cirBiz.outPutLog(map, reqMdl,
                textEdit, GSConstLog.LEVEL_TRACE, textReturn);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //元に戻す完了画面を表示
        return __setKanryoDsp(map, form, req, cmd);
    }



    /**
     * <br>[機  能] 検索ボタンクリック時の処理を行う
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
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateSearch(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //回覧板詳細検索画面へ遷移する
        return map.findForward("search");
    }




    /**
     * <br>[機  能] ラベル追加・削除ダイアログ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doDialogLabel(ActionMapping map,
                                 Cir010Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con)
        throws Exception {

        con.setAutoCommit(true);
        try {
            JSONObject jsonData = new JSONObject();
            boolean successFlg = false;
            Cir010ParamModel paramMdl = new Cir010ParamModel();
            paramMdl.setParam(form);
            // 回覧板選択チェック
            ActionErrors errors =
                form.validateSelectLabelCheck(getRequestModel(req), con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            } else {
                successFlg = true;
                Cir010Biz biz = new Cir010Biz();
                paramMdl.setCir010LabelList(
                        biz.getJsonLabelList(con, paramMdl.getCirViewAccount()));
            }
            paramMdl.setFormData(form);
            jsonData = JSONObject.fromObject(form);
            if (successFlg) {
                jsonData.element("success", true);
            } else {
                jsonData.element("error", true);
            }

            PrintWriter out = null;
            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData);
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(削除確認データ)");
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
    }

    /**
     * <br>jsonエラーメッセージ作成
     * @param req リクエスト
     * @param errors エラーメッセージ
     * @throws Exception 実行例外
     * @return errorResult jsonエラーメッセージ
     */
    private List<String> __getJsonErrorMsg(
        HttpServletRequest req, ActionErrors errors) throws Exception {

        @SuppressWarnings("all")
        Iterator iterator = errors.get();

        List<String> errorList = new ArrayList<String>();
        while (iterator.hasNext()) {
            ActionMessage error = (ActionMessage) iterator.next();
            errorList.add(getResources(req).getMessage(error.getKey(), error.getValues()));
        }
        return errorList;
    }


    /**
     * <br>[機  能] ラベル追加・削除処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param type 処理種別 0:ラベル追加 1:ラベル削除
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __doAddDelLabel(ActionMapping map,
                                            Cir010Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            int type) throws SQLException, Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter out = null;
        boolean commit = false;
        boolean logFlg = false;
        String logCirName = "";
        RequestModel reqMdl = getRequestModel(req);
        // 回覧板選択チェック
        ActionErrors errors =
            form.validateSelectLabelCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setErrorsList(__getJsonErrorMsg(req, errors));
        } else {
            // 回覧板選択チェック
            errors = form.validateLabelCheck(getRequestModel(req), con, type);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                form.setErrorsList(__getJsonErrorMsg(req, errors));
            }
        }
        try {
            con.setAutoCommit(false);
            Cir010ParamModel paramMdl = new Cir010ParamModel();
            paramMdl.setParam(form);
            JSONObject jsonData = new JSONObject();
            boolean chk = false;
            if (errors.isEmpty()) {
                Cir010Biz biz = new Cir010Biz();
                //回覧板名
                Cir010Biz cir010Biz = new Cir010Biz();
                logCirName = cir010Biz.getLogSelectCirName(
                        paramMdl, paramMdl.getCirViewAccount(), con, reqMdl);
                if (type == GSConstCircular.CIR_LABEL_ADD) {
                    chk = biz.setLabelForCircular(map, reqMdl, res, con, paramMdl,
                                                    getCountMtController(req),
                                                    getSessionUserSid(req));
                } else if (type == GSConstCircular.CIR_LABEL_DEL) {
                    chk = biz.deleteLabelForCircular(con, paramMdl, getRequestModel(req));
                }

            }
            jsonData = JSONObject.fromObject(form);
            if (!errors.isEmpty() || !chk) {
                jsonData.element("error", true);
            } else {
                jsonData.element("success", true);
                logFlg = true;
            }
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();

            con.commit();
            commit = true;
        } finally {
            if (out != null) {
                out.close();
            }

            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        if (logFlg) {
            StringBuilder sb = new StringBuilder();
            // ログ出力
            String opCode = "";
            int labelSid = -1;
            GsMessage gsMsg = new GsMessage(reqMdl);
            if (type == GSConstCircular.CIR_LABEL_ADD) {
                opCode = gsMsg.getMessage("cmn.add.label");
                labelSid = form.getCir010addLabel();
            } else if (type == GSConstCircular.CIR_LABEL_DEL) {
                opCode = gsMsg.getMessage("wml.js.108");
                labelSid = form.getCir010delLabel();
            }
            //アカウント名取得
            int accountSid = form.getCirViewAccount();
            CirAccountDao acDao = new CirAccountDao(con);
            String accountName =  acDao.getCirAccountName(accountSid);
            sb.append("[" + gsMsg.getMessage("wml.96") + "] ");
            sb.append(accountName);
            sb.append(System.getProperty("line.separator"));
            //ラベル名
            String labelName = "";
            if (form.getCir010addLabelType() == GSConstCircular.ADDLABEL_NEW) {
                sb.append("【" + gsMsg.getMessage("wml.wml010.09") + "】");
                labelName = form.getCir010addLabelName();
            } else {
                CirLabelDao labelDao = new CirLabelDao(con);
                CirLabelModel labelData = labelDao.select(labelSid, accountSid);
                labelName = labelData.getClaName();
            }
            sb.append("[" + gsMsg.getMessage("cmn.label.name") + "] ");
            sb.append(labelName);
            sb.append(System.getProperty("line.separator"));
            //回覧板名
            sb.append("[" + gsMsg.getMessage("cir.5") + "] ");
            sb.append(System.getProperty("line.separator"));
            sb.append(logCirName);
            sb.append(System.getProperty("line.separator"));
            //ログ出力処理
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            cirBiz.outPutLog(map, reqMdl,
                    opCode, GSConstLog.LEVEL_INFO, sb.toString());
        }

    }



    /**
     * <br>[機  能] ゴミ箱を空にする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doClearConfirmationData(ActionMapping map,
                                 Cir010Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con)
        throws Exception {


        con.setAutoCommit(true);
        try {
            JSONObject jsonData = new JSONObject();

            Cir010ParamModel paramMdl = new Cir010ParamModel();
            paramMdl.setParam(form);
            // ゴミ箱内回覧板存在チェック
            Cir010Biz biz = new Cir010Biz();
            int cnt = biz.getGomibakoCnt(
                    paramMdl, paramMdl.getCirViewAccount(), con);

            paramMdl.setFormData(form);
            jsonData = JSONObject.fromObject(form);

            if (cnt < 1) {
                jsonData.element("error", true);
            } else {
                jsonData.element("success", true);
            }
            PrintWriter out = null;
            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData);
                out.flush();
            } catch (Exception e) {
                log__.error("jsonデータ送信失敗(削除確認データ)");
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
    }

    /**
     * <br>[機  能] 全削除処理を行う(削除実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     */
    private void __doAllDeleteExe(
        ActionMapping map,
        Cir010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //選択された回覧板を削除する
        Cir010ParamModel paramMdl = new Cir010ParamModel();
        paramMdl.setParam(form);
        Cir010Biz biz = new Cir010Biz();
        biz.deleteAllCir(paramMdl, con, paramMdl.getCirViewAccount());
        paramMdl.setFormData(form);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textGomi = gsMsg.getMessage("cmn.empty.trash");
        String textDel = gsMsg.getMessage("cmn.delete");

        //ログ出力処理
        StringBuilder sb = new StringBuilder();
        //アカウント名取得
        int accountSid = form.getCirViewAccount();
        CirAccountDao acDao = new CirAccountDao(con);
        String accountName =  acDao.getCirAccountName(accountSid);
        sb.append("[" + gsMsg.getMessage("wml.96") + "] ");
        sb.append(accountName);
        sb.append(System.getProperty("line.separator"));
        sb.append(textGomi);

        CirCommonBiz cirBiz = new CirCommonBiz(con);
        cirBiz.outPutLog(map, reqMdl,
                textDel, GSConstLog.LEVEL_INFO, sb.toString());

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(復旧データ)");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }





}

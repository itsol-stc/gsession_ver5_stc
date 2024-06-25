package jp.groupsession.v2.mem.mem030;

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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.mem.AbstractMemoAdminAction;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.biz.MemCommonBiz;
import jp.groupsession.v2.mem.model.MemoAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メモ 管理者設定 自動削除設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem030Action extends AbstractMemoAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Mem030Action.class);

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
     */
    public ActionForward executeAction(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //オブジェクトの生成
        Mem030Form thisForm = (Mem030Form) form;

        //画面遷移を表す
        // コマンドパラメータの取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        ActionForward forward = null;
        con.setAutoCommit(true);

        if (cmd.equals("mem030Next")) {
            //設定画面でOKボタンを押下したとき
            forward = __doKakunin(map, thisForm, req, res, con);

        } else if (cmd.equals("mem030commit")) {
            //設定確認画面でOKボタンを押下したとき
            forward = __doCommit(map, thisForm, req, res, con);

        } else if (cmd.equals("mem030cancel")) {
            //設定確認画面でキャンセルボタンを押下したとき
            forward = __doSetDisplayData(map, thisForm, req, res, con);

        } else if (cmd.equals("mem030Back")) {
            //設定画面で戻るボタンを押下したとき
            forward = __doBack(map, thisForm, req, res, con);

        } else {
            //初期表示の場合
            if (thisForm.getMem030InitFlg() == GSConstMemo.INIT_FLG) {
                log__.debug("初期表示");
                forward = __doInit(map, thisForm, req, res, con);

                //初期表示ではないとき
            } else {
                log__.debug("初期表示完了");
                forward = map.getInputForward();
            }
        }
        return forward;
    }

    /**
     * <br>[機  能] 表示用データを取得、表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSetDisplayData(ActionMapping map, Mem030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

        //表示用データを取得する
        Mem030Biz biz = new Mem030Biz(getRequestModel(req));
        Mem030ParamModel paramMdl = new Mem030ParamModel();
        paramMdl.setParam(form);
        biz._setDisplayData(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
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
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Mem030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

        log__.debug("初期表示");

        //初期表示をセット
        Mem030Biz biz = new Mem030Biz(getRequestModel(req));
        Mem030ParamModel paramMdl = new Mem030ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        return __doSetDisplayData(map, form, req, res, con);
    }

    /**
     * <br>[機  能] OKボタン(設定画面)押下時の確認処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection 
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Mem030Form form,
            HttpServletRequest req, HttpServletResponse res,  Connection con) throws SQLException {

        log__.debug("確認処理");

        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doSetDisplayData(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面(OK キャンセル)を表示
        __setKakuninScreen(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認画面遷移時のパラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req HttpServletRequest
     * @param form ActionForm
     */
    private void __setKakuninScreen(ActionMapping map, HttpServletRequest req, Mem030Form form) {

        GsMessage gsMsg = new GsMessage();

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //年
        String textYear = gsMsg.getMessage(req, "cmn.year", String.valueOf(form.getMem030Year()));
        //設定しない
        String textNoset = gsMsg.getMessage(req, "cmn.noset");
        // 自動で削除する
        String textAutomatically = gsMsg.getMessage(req, "cmn.automatically.delete");
        //メッセージセット
        String msgState = "setting.kakunin.data";
        String mkey1 = gsMsg.getMessage(req, "memo.01")
                + " " + gsMsg.getMessage(req, "cmn.autodelete");
        String mkey2 = null;

        if (form.getMem030Kbn() == GSConstMemo.AUTO_DELETE_KBN_OFF) {
            mkey2 = textNoset;
        } else {
            mkey2 = textAutomatically + "(" + textYear + gsMsg.getMessage(
                    req, "cmn.months", String.valueOf(form.getMem030Month())) + ")";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1, mkey2));

        cmn999Form.addHiddenParam("cmd", "ok");
        cmn999Form.addHiddenParam("mem030Kbn", form.getMem030Kbn());
        cmn999Form.addHiddenParam("mem030Year", form.getMem030Year());
        cmn999Form.addHiddenParam("mem030Month", form.getMem030Month());

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Mem030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {
        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel umodel = getSessionUserModel(req);
        Mem030Biz biz = new Mem030Biz(reqMdl);
        Mem030ParamModel paramMdl = new Mem030ParamModel();
        paramMdl.setParam(form);
        MemoAdmConfModel conf = biz.setAutoDelete(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        //ログ出力
        __outPutUpdateLog(map, req, con, reqMdl, conf);

        //共通メッセージ画面(設定完了)を表示
        __setCompParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 管理者設定自動削除設定のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param conf MemoAdmConfModel
     */
    private void __outPutUpdateLog(ActionMapping map, HttpServletRequest req,
            Connection con, RequestModel reqMdl, MemoAdmConfModel conf) {

        //ログ出力処理
        MemCommonBiz memBiz = new MemCommonBiz(con, reqMdl);
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage();

        //内容セット
        String change = gsMsg.getMessage(req, "cmn.change");
        String atdel = "[" + gsMsg.getMessage("cmn.autodelete.setting") + "]:";
        String keikaYM = "[" + gsMsg.getMessage("cmn.passage.year.month") + "]:";
        String delKbn = null;
        String year = null;
        String month = null;

        if (conf.getMacAtdelKbn() == 0) {
            delKbn = gsMsg.getMessage(req, "cmn.noset");
            values.append(atdel + delKbn);
        } else {
            delKbn = gsMsg.getMessage(req, "cmn.automatically.delete");
            year = gsMsg.getMessage(req, "cmn.year", String.valueOf(conf.getMacAtdelY()));
            month = gsMsg.getMessage(req, "cmn.months", String.valueOf(conf.getMacAtdelM()));
            values.append(atdel + delKbn + "\n" + keikaYM + year + month);
        }
        memBiz.outPutLog(map, req, change, GSConstLog.LEVEL_INFO, values.toString());
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompParam(ActionMapping map, HttpServletRequest req, Mem030Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("mem020");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();
        //自動データ削除設定
        String textAutoDelete = gsMsg.getMessage(req, "cmn.automatic.delete.setting");
        String key1 = textAutoDelete;
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] 戻るボタン(設定画面)押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Mem030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {
        log__.debug("戻る");
        return map.findForward("mem030Back");
    }
}
package jp.groupsession.v2.anp.anp060;


import java.sql.Connection;

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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.dao.AnpiCommonDao;
import jp.groupsession.v2.anp.model.AnpCmnConfModel;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.anp.model.AnpStateModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>安否確認メッセージ配信画面のアクション
 * @author JTS
 */
/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 */
public class Anp060Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp060Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp060Form uform = (Anp060Form) form;

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        uform.setAnp060ScrollFlg("0");
        if (cmd.equals("anp060back")) {
            //「戻る」ボタンクリック時
            forward = map.findForward(__getBackForward(uform));

        } else if (cmd.equals("anp060haisin")) {
            //「配信」ボタンクリック時
            forward = __dohaisin(map, uform, req, res, con);

        } else if (cmd.equals("anp060selectTemp")) {
            //「(定型メッセージ)選択」ボタンクリック時
            forward = __dspMessageTemplate(map, uform, req, res, con);

        } else if (cmd.equals("anp060group")) {
            //グループコンボボックス選択時、または選択画面からの戻り
            uform.setAnp060ScrollFlg("1");
            forward = __refresh(map, uform, req, res, con);

        } else if (cmd.equals("anp060sendTest")) {
            //「(テスト)送信」ボタンクリック時
            forward = __sendTestMail(map, uform, req, res, con);

        } else if (cmd.equals("anp060knback")) {
            //確認画面からの戻り時
            forward = __refresh(map, uform, req, res, con);

        } else if (cmd.equals("haisin_ok")) {
            //既存配信を完了に更新
            forward = __doFinishExec(map, uform, req, res, con);

        } else {
            //初期化（配信中のデータがあるかどうか確認する）
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __doInit(ActionMapping map, Anp060Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //メッセージ送信に必要な情報が設定済みかどうか確認する
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        AnpCmnConfModel bean = anpiBiz.getAnpCmnConfModel(con);
        if (StringUtil.isNullZeroString(bean.getApcUrlBase())
         || StringUtil.isNullZeroString(bean.getApcSendHost())) {
            return __setCmnReadErrorDsp(map, form, req);
        }

        //既に配信中のデータがあるかどうか確認する
        AnpHdataDao hDao = new AnpHdataDao(con);
        AnpHdataModel hdata = hDao.selectInHaisin();

        if (hdata != null) {
            log__.debug("配信中データあり");
            //トランザクショントークン設定
            this.saveToken(req);
            //配信中のデータを完了していいかどうか確認
            return __setFinishHaisinConfDsp(map, form, req, res, con, hdata);
        }

        //「複写して新規作成」の既存データ取得
        if (form.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_COPY)) {
            Anp060Biz biz = new Anp060Biz();
            Anp060ParamModel paramModel = new Anp060ParamModel();
            paramModel.setParam(form);
            biz.setDatafromCopy(paramModel, con);
            paramModel.setFormData(form);
        }

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form アクションフォーム
     * @return アクションフォーワード名
     * @throws Exception 実行時例外
     */
    private String __getBackForward(Anp060Form form)
            throws Exception {
        log__.debug("戻る");

        if (form.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_COPY)) {
            //履歴詳細画面へ遷移する。
            return "back_his";
        }

        return "back";
    }

    /**
     * <br>[機  能] 配信処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __dohaisin(ActionMapping map, Anp060Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("配信");

        //入力チェック
        ActionErrors errors = form.validateAnp060(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __refresh(map, form, req, res, con);
        }

        // トランザクショントークン設定
        this.saveToken(req);

        return map.findForward("excute");
    }

    /**
     * <br>[機  能] 定型メッセージ選択処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __dspMessageTemplate(ActionMapping map, Anp060Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("定型メッセージ選択");

        //定型メッセージの内容をセット
        Anp060Biz biz = new Anp060Biz();
        Anp060ParamModel paramModel = new Anp060ParamModel();
        paramModel.setParam(form);
        biz.setMailTemplate(paramModel, con);
        paramModel.setFormData(form);

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] テストメール送信処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __sendTestMail(ActionMapping map, Anp060Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug(" テストメール送信");

        //メールアドレスチェック
        ActionErrors errors = form.validateAnp060_testSend(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __refresh(map, form, req, res, con);
        }

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("anp.anp060.06");
        String value;

        //テストメール送信
        Anp060Biz biz = new Anp060Biz();
        Anp060ParamModel paramModel = new Anp060ParamModel();
        paramModel.setParam(form);
        int sendfg = biz.sendTestMail(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);
        if (sendfg == GSConstAnpi.SENDMSG_SUCCESS) {
            value = gsMsg.getMessage("anp.logmsg.test.ok");
            anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_TRACE, value);
            return __refresh(map, form, req, res, con);
        }

        errors = new ActionErrors();
        String msgKey = "error.connect.failed.mailserver";
        ActionMessage msg = new ActionMessage(
                msgKey, gsMsg.getMessage("anp.smtp.server"), gsMsg.getMessage("anp.anp060.07"));
        StrutsUtil.addMessage(errors, msg, "testmail." + msgKey);
        addErrors(req, errors);
        value = gsMsg.getMessage("anp.logmsg.test.no");
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_TRACE, value);

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 基本設定未登録警告画面のパラメータセット
     * <br>[解  説] OKボタンのみのメッセージ画面を表示する
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setCmnReadErrorDsp(ActionMapping map, Anp060Form form,
                                        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("cmnConf");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("error.touroku.required.data",
                        gsMsg.getMessage("anp.anp060.08")));

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp130(cmn999Form);
        form.setHiddenParamAnp140(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 配信中データ破棄確認
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param hdata 配信データMODEL
     * @return ActionForward 画面遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __setFinishHaisinConfDsp(ActionMapping map, Anp060Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con,
                    AnpHdataModel hdata)
            throws Exception {

        log__.debug("配信中データあり");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //確認メッセージ画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //遷移先（OK→本画面、CANCEL→遷移元画面へ）
        urlForward = map.findForward("haisin");
        cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=haisin_ok");
        urlForward = map.findForward(__getBackForward(form));
        cmn999Form.setUrlCancel(urlForward.getPath());

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonDao adao = new AnpiCommonDao(con, getRequestModel(req));
        AnpStateModel state = adao.getStateInfo(hdata.getAphSid());
        String msg = gsMsg.getMessage("anp.date.send") + "："
                + NullDefault.getString(state.getHaisinDate(), "") + "<br>"
                + gsMsg.getMessage("anp.reply.state") + "："
                + NullDefault.getString(state.getReplyState(), "-");
        cmn999Form.setMessage(
                gsMsg.getMessage("anp.anp060.09",
                        new String []{StringUtilHtml.transToHTmlPlusAmparsant(
                                hdata.getAphSubject())})
                + "<br><br>" + msg);

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp060(cmn999Form);
        form.setHiddenParamAnp130(cmn999Form);
        form.setHiddenParamAnp140(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 配信中データを破棄して新しい配信を開始する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doFinishExec(ActionMapping map, Anp060Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("配信中データ破棄");

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //配信中データ破棄更新
        con.setAutoCommit(true);
        Anp060Biz biz = new Anp060Biz();
        Anp060ParamModel paramModel = new Anp060ParamModel();
        paramModel.setParam(form);
        String finishTitle = biz.finishHaisin(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("anp.end");
        String value = finishTitle + "    ※" + gsMsg.getMessage("anp.logmsg.endn");
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, value);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __refresh(ActionMapping map, Anp060Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //初期データ取得
        Anp060Biz biz = new Anp060Biz();
        Anp060ParamModel paramModel = new Anp060ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }
}

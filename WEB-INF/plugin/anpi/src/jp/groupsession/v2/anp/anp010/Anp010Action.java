package jp.groupsession.v2.anp.anp010;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp060kn.Anp060knForm;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 安否状況一覧画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Anp010Action extends AbstractAnpiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp010Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp010Form uform = (Anp010Form) form;

        if (cmd.equals("anp010admtool")) {
            //「管理者設定」ボタンクリック時
            forward = map.findForward("admtool");

        } else if (cmd.equals("anp010pritool")) {
            //「個人設定」ボタンクリック時
            forward = map.findForward("pritool");

        } else if (cmd.equals("anp010haisin")) {
            //「新規配信」ボタンクリック時
            forward = map.findForward("haisin");

        } else if (cmd.equals("anp010saiso")) {
            //「再送信」ボタンクリック時
            forward = __reSend(map, uform, req, res, con, GSConstAnpi.MSG_HAISIN_MODE_MISAISOU);

        } else if (cmd.equals("anp010finish")) {
            //「受付終了」ボタンクリック時
            forward = __doFinishConf(map, uform, req, res, con);

        } else if (cmd.equals("finish_ok")) {
            //確認完了実行
            forward = __doFinishExec(map, uform, req, res, con);

        } else if (cmd.equals("anp010InfoConf")) {
            //「配信内容」ボタンクリック時
            forward = __reSend(map, uform, req, res, con, GSConstAnpi.MSG_HAISIN_MODE_INFOCONF);

        } else if (cmd.equals("anp010group")) {
            //グループコンボボックス選択時、または選択画面からの戻り
            uform.setAnp010NowPage(1);  //ページを1ページ目へ
            forward = __refresh(map, uform, req, res, con, false);

        } else if (cmd.equals("anp010pageChange")) {
            //ページコンボボックス選択時
            forward = __movePage(map, uform, req, res, con, 0);

        } else if (cmd.equals("anp010pageLast")) {
            //「前ページ」ボタンクリック時
            forward = __movePage(map, uform, req, res, con, -1);

        } else if (cmd.equals("anp010pageNext")) {
            //「次ページ」ボタンクリック時
            forward = __movePage(map, uform, req, res, con, 1);

        } else if (cmd.equals("anp010sortList")) {
            //ソート順変更（一覧列タイトルクリック時）
            forward = __sortList(map, uform, req, res, con);

        } else if (cmd.equals("anp010syosai")) {
            //安否状況一覧の氏名クリック時
            forward = map.findForward("syosai");

        } else if (cmd.equals("detailSearch")) {
            //検索ボタンクリック時
            forward = __refresh(map, uform, req, res, con, true);

        } else if (cmd.equals("reload")) {
            //再読込
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
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
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __doInit(ActionMapping map, Anp010Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return __refresh(map, form, req, res, con, false);
    }

    /**
     * <br>[機  能] 再送信処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 全員へ最送信 or 未送信者へ最送信
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __reSend(ActionMapping map, Anp010Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con, String mode)
            throws Exception {

        //次画面パラメータ設定
        Anp060knForm sform = new Anp060knForm();
        BeanUtils.copyProperties(sform, form);
        sform.setAnp060ProcMode(mode);

        req.setAttribute("anp060knForm", sform);

        // トランザクショントークン設定
        if (!mode.equals(GSConstAnpi.MSG_HAISIN_MODE_INFOCONF)) {
            this.saveToken(req);
        }

        return map.findForward("saisou");
    }

    /**
     * <br>[機  能] 確認完了ボタンクリック時の処理を行う
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
    private ActionForward __doFinishConf(ActionMapping map, Anp010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {



        //メッセージ（対象データからメッセージを作成）
        Anp010Biz biz = new Anp010Biz();
        String msg = biz.getFinishMessage(getRequestModel(req), con, form.getAnpiSid());
        msg = NullDefault.getStringZeroLength(msg, "");

        //トランザクショントークン設定
        this.saveToken(req);

        //削除確認画面のパラメータセット
        return __setFinishConfDsp(map, form, req, con, msg);
    }

    /**
     * <br>[機  能] 完了確認メッセージ確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param msg 削除確認メッセージ文字列
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __setFinishConfDsp(ActionMapping map, Anp010Form form,
                                  HttpServletRequest req, Connection con, String msg)
            throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //確認メッセージ画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //遷移先（OK→本画面、CANCEL→遷移元画面へ）
        urlForward = map.findForward("anpimain");
        cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=finish_ok");
        cmn999Form.setUrlCancel(urlForward.getPath() + "?CMD=finish_cancel");

        //メッセージ
        GsMessage gsmsg = new GsMessage(getRequestModel(req));
        cmn999Form.setMessage(gsmsg.getMessage("anp.anp010.07") + "<br><br>" + msg
                            + "<br><br>" + gsmsg.getMessage("anp.anp010.08"));

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 安否確認完了処理を行う(完了フラグの更新)
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
    private ActionForward __doFinishExec(ActionMapping map, Anp010Form form,
                         HttpServletRequest req, HttpServletResponse res, Connection con)
             throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(true);
        Anp010Biz biz = new Anp010Biz();
        String finishTitle = biz.finishHaisin(getRequestModel(req), con);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("anp.end");
        String value = finishTitle;
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_TRACE, value);

        //確認完了画面を表示
        return __setFinishExecDsp(map, form, req);
    }

    /**
     * <br>[機  能] 確認完了メッセージ画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setFinishExecDsp(ActionMapping map, Anp010Form form,
                                          HttpServletRequest req) {

        //メッセージ
        GsMessage gsmsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        String msg = msgRes.getMessage("cmn.kanryo.object", gsmsg.getMessage("anp.anp010.09"));

        return __setMsgDsp(map, req, msg);
    }

    /**
     * <br>[機  能] ページ移動処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param pageNo ページ数
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __movePage(ActionMapping map, Anp010Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con, int pageNo)
            throws Exception {

        //ページ数調整
        int page = form.getAnp010NowPage();
        page += pageNo;
        if (page < 1) {
            page = 1;
        }
        form.setAnp010NowPage(page);

        return __refresh(map, form, req, res, con, false);
    }

    /**
     * <br>[機  能] ソート順変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __sortList(ActionMapping map, Anp010Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //ソート設定
        int order = form.getAnp010OrderKey();
        if (order == GSConst.ORDER_KEY_ASC) {
            order = GSConst.ORDER_KEY_DESC;
        } else {
            order = GSConst.ORDER_KEY_ASC;
        }
        form.setAnp010OrderKey(order);

        return __refresh(map, form, req, res, con, false);
    }

    /**
     * <br>[機  能] 表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param save パラメータをセーブする場合true
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __refresh(ActionMapping map, Anp010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, boolean save)
            throws Exception {

        if (save) {
            //ページの初期化
            form.setAnp010NowPage(1);
            //検索パラメータをセーブ
            form.saveSearchParm();
        }

        //初期データ取得
        Anp010Biz biz = new Anp010Biz();
        Anp010ParamModel paramModel = new Anp010ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] メッセージ画面のパラメータセット
     * <br>[解  説] OKボタンのみのメッセージ画面を表示する
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param msg メッセージ文字列
     * @return ActionForward
     */
    private ActionForward __setMsgDsp(ActionMapping map, HttpServletRequest req, String msg) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("anpimain");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        cmn999Form.setMessage(msg);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}

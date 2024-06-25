package jp.groupsession.v2.anp.anp060kn;


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
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 安否確認メッセージ配信確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp060knAction extends AbstractAnpiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp060knAction.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {
        if (!((Anp060knForm) form).isViewMode()) {
            // 閲覧モード以外の場合のみチェック
            if (_isSystemAdmin(req, con)) {
                return true; //システム管理者の場合はプラグインチェックをしない
            }
        }
        return super._isAccessPlugin(req, form, con);
    }

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
        Anp060knForm uform = (Anp060knForm) form;

        if (!uform.validateParamAnp060kn()) {
            log__.info("パラメータエラー");
            return getSubmitErrorPage(map, req);
        }

        //管理者権限確認
        if (!uform.isViewMode()) {
            if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
                return getNotAdminSeniPage(map, req);
            }
        }

        uform.setAnp060knScrollFlg("0");
        if (cmd.equals("anp060knback")) {
            //「戻る」ボタンクリック時
            forward = __doBack(map, uform, req, res, con);

        } else if (cmd.equals("anp060knhaisin")) {
            //「配信」ボタンクリック時（登録→完了メッセージ→安否メイン画面）
            forward =  __doHaisin(map, uform, req, res, con);

        } else if (cmd.equals("anp060knpageChange")) {
            //ページコンボボックス選択時
            uform.setAnp060knScrollFlg("1");
            forward = __movePage(map, uform, req, res, con, 0);

        } else if (cmd.equals("anp060knpageLast")) {
            //「前ページ」ボタンクリック時
            uform.setAnp060knScrollFlg("1");
            forward = __movePage(map, uform, req, res, con, -1);

        } else if (cmd.equals("anp060knpageNext")) {
            //「次ページ」ボタンクリック時
            uform.setAnp060knScrollFlg("1");
            forward = __movePage(map, uform, req, res, con, 1);

        } else {
            if (cmd.equals("anp060knreload")) {
                uform.setAnp060ProcMode(uform.getAnp060knProcMode());
            } else {
                uform.setAnp060knProcMode(uform.getAnp060ProcMode());
            }
            //初期化
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
    private ActionForward __doInit(ActionMapping map, Anp060knForm form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //再送信データ取得
        if (form.isReadMode()) {
            log__.debug("再送信 SID = " + form.getAnpiSid());
            //既存の配信データを確認
            ActionForward forward = __checkReadData(map, form, req, res, con);
            if (forward != null) {
                return forward;
            }

            Anp060knBiz biz = new Anp060knBiz();
            Anp060knParamModel paramModel = new Anp060knParamModel();
            paramModel.setParam(form);
            boolean dataflg = biz.setReplyData(paramModel, con);
            paramModel.setFormData(form);
            if (!dataflg) {
                return __setErrorDspNoData(map, form, req, con);
            }
        }

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 戻る処理
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
    private ActionForward __doBack(ActionMapping map, Anp060knForm form,
                HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("戻る");

        if (form.isReadMode()) {
            //安否確認トップへ遷移する。
            return map.findForward("anpimain");
        }

        return map.findForward("back");
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
    private ActionForward __doHaisin(ActionMapping map, Anp060knForm form,
                HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("配信");

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateAnp060(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __refresh(map, form, req, res, con);
        }

        //送信サーバに接続可能かチェック
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        //SMTPサーバー接続フラグ
        boolean smtpFlg = false;
        if (anpBiz.connectSendServer(con) == GSConstAnpi.SENDMSG_SUCCESS) {
            smtpFlg = true;
        }

        //既存の配信データを確認
        ActionForward forward = __checkReadData(map, form, req, res, con);
        if (forward != null) {
            return forward;
        }

        //登録＆mail送信
        MlCountMtController cntCon = getCountMtController(req);
        Anp060knBiz biz = new Anp060knBiz();
        Anp060knParamModel paramModel = new Anp060knParamModel();
        paramModel.setParam(form);
        boolean sendflg = biz.doHaisin(paramModel, getRequestModel(req), con, cntCon, smtpFlg);
        paramModel.setFormData(form);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String opCode = gsMsg.getMessage("cmn.change");
        String value = gsMsg.getMessage("change.sort.order");
        if (form.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_MISAISOU)) {
            opCode = gsMsg.getMessage("anp.send.re");
            value = gsMsg.getMessage("anp.anp060kn.09");
        } else if (form.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_ZENSAISOU)) {
            opCode = gsMsg.getMessage("anp.send.re");
            value = gsMsg.getMessage("anp.anp060kn.10");
        } else {
            //新規配信
            opCode = gsMsg.getMessage("anp.anp060kn.11");

            if (form.getAnp010KnrenFlg() == GSConstAnpi.KNREN_MODE_ON) {
                value = "【 " + gsMsg.getMessage("anp.knmode") + " 】" +  form.getAnp060Subject();
            } else {
                value = form.getAnp060Subject();
            }
        }
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_TRACE, value);

        //完了画面を表示
        return __setHaisinDsp(map, form, req, smtpFlg, sendflg);
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param smtpFlg true:接続可  false:接続不可
     * @param sendflg SMTPサーバー接続フラグ true:正常終了 false:一部ユーザにメール送信失敗
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __setHaisinDsp(ActionMapping map, Anp060knForm form,
                                    HttpServletRequest req, boolean smtpFlg, boolean sendflg)
                        throws Exception {

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        String haisinMode = "";
        if (form.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_MISAISOU)) {
            haisinMode = gsMsg.getMessage("anp.anp060kn.09");
        } else if (form.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_ZENSAISOU)) {
            haisinMode = gsMsg.getMessage("anp.anp060kn.10");
        } else {
            haisinMode = gsMsg.getMessage("anp.anp060kn.11");
        }
        String msg = msgRes.getMessage("cmn.kanryo.object", haisinMode);

        if (!smtpFlg) {
            msg += "<br><br>";
            msg += gsMsg.getMessage("anp.anp060kn.12");
        } else if (!sendflg) {
            msg += "<br><br>";
            msg += gsMsg.getMessage("anp.anp060kn.13");
        }

        return __setMsgDsp(map, form, req, msg, Cmn999Form.ICON_INFO);
    }

    /**
     * <br>[機  能] 配信、再送信を行う前に既存データの確認を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __checkReadData(ActionMapping map, Anp060knForm form,
                HttpServletRequest req, HttpServletResponse res, Connection con)
                        throws Exception {

        //表示のみなので、スルー
        if (form.isViewMode()) {
            return null;
        }

        //既存配信データを確認
        AnpHdataDao hDao = new AnpHdataDao(con);
        AnpHdataModel hdata = hDao.selectInHaisin();

        if (form.isReadMode()) {
            if (hdata == null) {
                //配信中のデータなし
                return __setErrorDspNoData(map, form, req, con);

            } else if (!form.getAnpiSid().equals(String.valueOf(hdata.getAphSid()))) {
                //配信中のデータとパラメータのSIDが違う場合、エラー
                return __setErrorDspNoData(map, form, req, con);
            }

        } else {
            if (hdata != null) {
                //配信中のデータあり
                return __setErrorDspIsData(map, form, req, con, hdata);
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 読込エラー画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __setErrorDspNoData(ActionMapping map, Anp060knForm form,
                                                HttpServletRequest req, Connection con)
            throws Exception {

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        String msg = msgRes.getMessage("search.data.notfound", gsMsg.getMessage("anp.plugin"));

        return __setMsgDsp(map, form, req, msg, Cmn999Form.ICON_WARN);
    }

    /**
     * <br>[機  能] 読込エラー画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param hdata 配信中データ
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __setErrorDspIsData(ActionMapping map, Anp060knForm form,
                          HttpServletRequest req, Connection con, AnpHdataModel hdata)
            throws Exception {

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        String msg = msgRes.getMessage(
                "error.input.timecard.exist", gsMsg.getMessage("anp.plugin"));

        return __setMsgDsp(map, form, req, msg, Cmn999Form.ICON_WARN);
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
    private ActionForward __refresh(ActionMapping map, Anp060knForm form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //初期データ取得
        Anp060knBiz biz = new Anp060knBiz();
        Anp060knParamModel paramModel = new Anp060knParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ページ移動実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @param  res レスポンス
     * @param  con コネクション
     * @param  pageNo ページ番号
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __movePage(ActionMapping map,
                                     Anp060knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con,
                                     int pageNo)
                              throws Exception {

        //ページ数調整
        int page = form.getAnp060knNowPage();
        page += pageNo;
        if (page < 1) {
            page = 1;
        }
        form.setAnp060knNowPage(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] メッセージ画面のパラメータセット
     * <br>[解  説] OKボタンのみのメッセージ画面を表示する
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param msg メッセージ文字列
     * @param iconType アイコン種類
     * @return ActionForward
     */
    private ActionForward __setMsgDsp(ActionMapping map, Anp060knForm form,
                                        HttpServletRequest req, String msg, int iconType) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(iconType);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        int usrSid = getSessionUserSid(req);
        ActionForward forwardOk;
        if (usrSid == GSConst.SYSTEM_USER_ADMIN) {
            forwardOk = map.findForward("history");
        } else {
            forwardOk = map.findForward("anpimain");
        }
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        cmn999Form.setMessage(msg);

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}

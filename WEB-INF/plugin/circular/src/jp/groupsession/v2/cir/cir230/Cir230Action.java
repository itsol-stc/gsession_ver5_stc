package jp.groupsession.v2.cir.cir230;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.AbstractCircularSubAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ラベル管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir230Action extends AbstractCircularSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir230Action.class);

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
        Cir230Form thisForm = (Cir230Form) form;

        // アクセス権限チェック
        CommonBiz cmnBiz = new CommonBiz();
        CirCommonBiz cirBiz = new CirCommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(
                con,
                getSessionUserModel(req),
                getPluginId());
        if (adminFlg) {
            CirAccountDao dao = new CirAccountDao(con);
            if (!dao.selectExistAccount(thisForm.getCirAccountSid())) {
                return getSubmitErrorPage(map, req);
            }
        } else {
            if (!cirBiz.canUseAccount(
                    con, getSessionUserSid(req), thisForm.getCirAccountSid())) {
                return getSubmitErrorPage(map, req);
            }
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        if (cmd.equals("configLabel")) {
            //追加ボタン、修正ボタンクリック
            forward = __doConfig(map, thisForm, req, res, con);
        } else if (cmd.equals("backAccount")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteLabel")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteLabelComp")) {
            //削除確認画面からの遷移
            forward = __doDeleteComp(map, thisForm, req, res, con);
        } else if (cmd.equals("upLabelData")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_UP);
        } else if (cmd.equals("downLabelData")) {
            //下へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_DOWN);
        } else {
            //初期表示
             forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;

    }



    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Cir230Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        Cir230ParamModel paramMdl = new Cir230ParamModel();
        paramMdl.setParam(form);
        Cir230Biz biz = new Cir230Biz(con);
        biz.setInitData(paramMdl, userSid);
        paramMdl.setFormData(form);
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }


    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説] アカウントマネージャー画面orアカウントの管理画面に遷移する
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Cir230Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        if (form.getCir230DspKbn() == GSConstCircular.DSPKBN_ADMIN) {
            // アカウントマネージャー
            return map.findForward("backAcManager");
        } else {
            // アカウントの管理
            return map.findForward("backAcConf");
        }
    }


    /**
     * <br>[機  能] 追加ボタン、修正ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doConfig(ActionMapping map, Cir230Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //編集チェック
        if (form.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_EDIT) {
            ActionErrors errors = form.validateLabelAccessCheck(getRequestModel(req), con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
        }
        return map.findForward("addEditLabel");
    }


    /**
    * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map アクションマッピング
    * @param form アクションフォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
    * @throws Exception 実行例外
    * @return ActionForward
    */
   private ActionForward __doSortChange(
       ActionMapping map,
       Cir230Form form,
       HttpServletRequest req,
       HttpServletResponse res,
       Connection con,
       int changeKbn) throws Exception {

       con.setAutoCommit(false);
       boolean commitFlg = false;
       boolean chk = false;
       //ログインユーザSIDを取得
       int userSid = 0;
       BaseUserModel buMdl = getSessionUserModel(req);
       if (buMdl != null) {
           userSid = buMdl.getUsrsid();
       }
       //入力チェック
       ActionErrors errors
           = form.validateLabelSortAccessCheck(getRequestModel(req), con);
       if (!errors.isEmpty()) {
           addErrors(req, errors);
           return __doInit(map, form, req, res, con);
       }
       //トークンチェック
       if (!isTokenValid(req, false)) {
           return getSubmitErrorPage(map, req);
       }
       try {
           Cir230ParamModel paramMdl = new Cir230ParamModel();
           paramMdl.setParam(form);
           Cir230Biz biz = new Cir230Biz(con);
           chk = biz.updateSort(paramMdl, changeKbn, userSid);
           paramMdl.setFormData(form);
           commitFlg = true;

       } catch (SQLException e) {
           log__.error("SQLException", e);
           throw e;
       } finally {
           if (commitFlg) {
               con.commit();
           } else {
               JDBCUtil.rollback(con);
           }
       }

       //ログ出力処理
       CirCommonBiz cirBiz = new CirCommonBiz(con);
       RequestModel reqMdl = getRequestModel(req);
       GsMessage gsMsg = new GsMessage(reqMdl);
       String opCode = gsMsg.getMessage("fil.77");
       StringBuilder sb = new StringBuilder();
       //アカウント名
       int cacSid = form.getCirAccountSid();
       String accountName =  cirBiz.getAccountName(cacSid);
       sb.append("[" + gsMsg.getMessage("wml.96") + "] ");
       sb.append(accountName);
       sb.append(System.getProperty("line.separator"));
       if (chk) {
           //ラベル名
           int sid = NullDefault.getInt(form.getCir230SortRadio(), -1);
           String labelName = cirBiz.getLabelName(sid, cacSid);
           sb.append("[" + gsMsg.getMessage("cmn.label.name") + "] ");
           sb.append(labelName);
           sb.append(System.getProperty("line.separator"));
           //実行内容
           sb.append("[" + gsMsg.getMessage("wml.282") + "] ");
           if (changeKbn == GSConst.SORT_UP) {
               sb.append(gsMsg.getMessage("cir.cir230.1"));
           } else {
               sb.append(gsMsg.getMessage("cir.cir230.2"));
           }
       } else {
           sb.append(gsMsg.getMessage("cir.cir230.3"));
       }
       //設定区分
       int logFlg = GSConstCircular.CIR_LOG_FLG_PREF;
       if (form.getCir230DspKbn() == GSConstCircular.DSPKBN_ADMIN) {
           logFlg = GSConstCircular.CIR_LOG_FLG_ADMIN;
       }
       cirBiz.outPutLog(map, reqMdl, opCode,
               GSConstLog.LEVEL_INFO, sb.toString(), logFlg);

       return __doInit(map, form, req, res, con);
   }


   /**
    * <br>[機  能] 削除ボタンクリック時の処理を行う
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
   private ActionForward __doDelete(
       ActionMapping map,
       Cir230Form form,
       HttpServletRequest req,
       HttpServletResponse res,
       Connection con) throws Exception {

       // トランザクショントークン設定
       saveToken(req);
       //入力チェック
       ActionErrors errors
           = form.validateLabelAccessCheck(getRequestModel(req), con);
       if (!errors.isEmpty()) {
           addErrors(req, errors);
           return __doInit(map, form, req, res, con);
       }
       //削除確認画面を表示
       return __setKakuninDsp(map, form, req, con);
   }

   /**
    * [機  能] 削除確認画面のパラメータセット<br>
    * [解  説] <br>
    * [備  考] <br>
    * @param map マッピング
    * @param form アクションフォーム
    * @param req リクエスト
    * @param con コネクション
    * @return ActionForward
    * @throws SQLException SQL実行例外
    */
   private ActionForward __setKakuninDsp(
       ActionMapping map,
       Cir230Form form,
       HttpServletRequest req,
       Connection con) throws SQLException {

       Cmn999Form cmn999Form = new Cmn999Form();
       cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
       cmn999Form.setIcon(Cmn999Form.ICON_INFO);
       cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

       //キャンセルボタンクリック時遷移先
       ActionForward forward = map.findForward("init");
       cmn999Form.setUrlCancel(forward.getPath());

       //OKボタンクリック時遷移先
       cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteLabelComp");

       String msg = getInterMessage(req, "wml.164");
       cmn999Form.setMessage(msg);

       //画面パラメータをセット
       cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
       form.setHiddenParam(cmn999Form);
       form.setHiddenParamCir230(cmn999Form);

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
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Cir230Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        // ２重投稿チェック
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        // ラベル存在チェック
        ActionErrors errors
            = form.validateLabelAccessCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            return getSubmitErrorPage(map, req);
        }
        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        Cir230ParamModel paramMdl = new Cir230ParamModel();
        paramMdl.setParam(form);
        Cir230Biz biz = new Cir230Biz(con);
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        //ログ出力用アカウント名取得
        int cacSid = paramMdl.getCirAccountSid();
        String accountName =  cirBiz.getAccountName(cacSid);
        //ログ出力用ラベル名取得
        String labelName = cirBiz.getLabelName(paramMdl.getCir230EditLabelId(), cacSid);
        //ラベルを削除する
        biz.deleteLabel(paramMdl, userSid);
        paramMdl.setFormData(form);

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.delete");
        StringBuilder sb = new StringBuilder();
        //アカウント名
        sb.append("[" + gsMsg.getMessage("wml.96") + "] ");
        sb.append(accountName);
        sb.append(System.getProperty("line.separator"));
        //ラベル名
        sb.append("[" + gsMsg.getMessage("cmn.label.name") + "] ");
        sb.append(labelName);
        //設定区分
        int logFlg = GSConstCircular.CIR_LOG_FLG_PREF;
        if (form.getCir230DspKbn() == GSConstCircular.DSPKBN_ADMIN) {
            logFlg = GSConstCircular.CIR_LOG_FLG_ADMIN;
        }
        cirBiz.outPutLog(map, reqMdl, opCode,
                GSConstLog.LEVEL_INFO, sb.toString(), logFlg);
        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Cir230Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("init");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", getInterMessage(req, "cmn.label")));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setHiddenParam(cmn999Form);
        form.setHiddenParamCir230(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }



}

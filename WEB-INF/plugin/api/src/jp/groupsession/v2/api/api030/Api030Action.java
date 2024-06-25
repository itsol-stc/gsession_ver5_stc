package jp.groupsession.v2.api.api030;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.api.biz.ApiCommonBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] トークン管理画面 アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api030Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Api030Action.class);

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

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;
        Api030Form thisForm = (Api030Form) form;

        con.setAutoCommit(true);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("api010Token")) {
            //初期化
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //ページ移動
            thisForm.setApi030page(thisForm.getApi030page() + 1);
            forward = __doDsp(map, thisForm, req, res, con);
        } else if (cmd.equals("prevPage")) {
            //ページ移動
            thisForm.setApi030page(thisForm.getApi030page() - 1);
            forward = __doDsp(map, thisForm, req, res, con);
        } else if (cmd.equals("api030Search")) {
            //検索
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("api030Muko")) {
            //無効化
            forward = __doMuko(map, thisForm, req, res, con);
        } else if (cmd.equals("api030MukoComp")) {
            //無効化確定
            forward = __doMukoComp(map, thisForm, req, res, con);
        } else if (cmd.equals("api030Back")) {
            //戻る
            forward = map.findForward("back");
        } else if (cmd.equals("tokenGroupChange")) {
            //トークングループ変更
            forward = __doTokenGrpChange(map, thisForm, req, res, con);
        } else if (cmd.equals("createToken")) {
            //トークン生成
            forward = __doCreateToken(map, thisForm, req, res, con);
        } else {
            //表示
            forward = __doDsp(map, thisForm, req, res, con);
        }
        log__.debug("END");
        return forward;
    }
    /**
     *
     * <br>[機  能] 無効化ボタン 確定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doMukoComp(ActionMapping map, Api030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateDelete(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }
        con.setAutoCommit(false);
        boolean commit = false;
        Api030ParamModel param = new Api030ParamModel();
        param.setParam(form);
        Api030Biz biz = new Api030Biz(getRequestModel(req), con);
        try {
            biz.doInvalidToken(param);

            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ 変更 **/
            String change = gsMsg.getMessage("cmn.invalid");

            //ログ出力
            ApiCommonBiz cmnBiz = new ApiCommonBiz();
            cmnBiz.outPutApiLog(map, getRequestModel(req), gsMsg, con,
                    change, GSConstLog.LEVEL_INFO,
                    biz.outputLogMessage(param)
                    );
            con.commit();
            commit = true;


        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        param.setFormData(form);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        ActionForward forwardOK = map.findForward("dsp");
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        cmn999Form.setUrlOK(forwardOK.getPath());

        GsMessage gsMsg = new GsMessage(reqMdl);
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("cmn.kanryo.object",
                     gsMsg.getMessage("cmn.invalid")));

        cmn999Form.addHiddenAll(form, form.getClass(), "");


        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     *
     * <br>[機  能] 無効化ボタン クリック処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doMuko(ActionMapping map, Api030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateDelete(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);


        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mukoComp");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCanc = map.findForward("dsp");
        cmn999Form.setUrlCancel(forwardCanc.getPath());
        GsMessage gsMsg = new GsMessage(reqMdl);
        int cnt = 0;
        if (StringUtil.isNullZeroString(form.getApi030delete())) {
            if (form.getApi030delMulti() != null) {
                cnt = form.getApi030delMulti().length;
            }
        } else {
            cnt = 1;
        }

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("setting.kakunin.data",
                     gsMsg.getMessage("api.api030.8",
                         new String[] {
                             String.valueOf(cnt),
                             gsMsg.getMessage("cmn.token")
                         }),
                     gsMsg.getMessage("cmn.invalid")));

        cmn999Form.addHiddenAll(form, form.getClass(), "");

        req.setAttribute("cmn999Form", cmn999Form);
        saveToken(req);
        return map.findForward("gf_msg");
    }

    /**
     *
     * <br>[機  能] 検索処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doSearch(ActionMapping map, Api030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception  {
        Api030ParamModel param = new Api030ParamModel();
        ActionErrors  errors = form.validateSearch(getRequestModel(req), con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        param.setParam(form);
        Api030Biz biz = new Api030Biz(getRequestModel(req), con);
        biz.doSaveSerch(param);
        biz.doDsp(param);
        param.setFormData(form);
        return map.getInputForward();
    }
    /**
     *
     * <br>[機  能] 表示前処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doDsp(ActionMapping map, Api030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        Api030ParamModel param = new Api030ParamModel();
        param.setParam(form);
        Api030Biz biz = new Api030Biz(getRequestModel(req), con);
        biz.doDsp(param);

        param.setFormData(form);
        return map.getInputForward();
    }

    /**
     *
     * <br>[機  能] 初期化処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Api030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        Api030ParamModel param = new Api030ParamModel();
        param.setParam(form);
        Api030Biz biz = new Api030Biz(getRequestModel(req), con);
        biz.doInit(param);
        biz.doDsp(param);
        param.setFormData(form);
        return map.getInputForward();
    }

    /**
    *
    * <br>[機  能] グループ変更時のユーザリストを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param form フォーム
    * @param map map マップ
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return 遷移先
    * @throws Exception 実行時例外
    */
   private ActionForward __doTokenGrpChange(ActionMapping map, Api030Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

       Api030ParamModel param = new Api030ParamModel();
       param.setParam(form);
       Api030Biz biz = new Api030Biz(getRequestModel(req), con);
       biz.getUserLabelList(param);

       JSONObject jsonData = new JSONObject();
       jsonData.element("success", true);
       jsonData.element("size", param.getApi030tokenUserLabel().size());
       for (int nIdx = 0; nIdx < param.getApi030tokenUserLabel().size(); nIdx++) {
           jsonData.element("usrSid_" + nIdx, param.getApi030tokenUserLabel().get(nIdx).getValue());
           jsonData.element("usrName_"
           + nIdx, param.getApi030tokenUserLabel().get(nIdx).getLabel());
       }
       PrintWriter out = null;
       try {
           res.setHeader("Cache-Control", "no-cache");
           res.setContentType("application/json;charset=UTF-8");
           out = res.getWriter();
           out.print(jsonData);
           out.flush();
       } catch (IOException e) {
       } finally {
           if (out != null) {
               out.close();
           }
       }
       return map.getInputForward();
   }

    /**
    *
    * <br>[機  能] トークン発行を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param form フォーム
    * @param map map マップ
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return 遷移先
    * @throws Exception 実行時例外
    */
   private ActionForward __doCreateToken(ActionMapping map, Api030Form form,
           HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

       Api030ParamModel param = new Api030ParamModel();
       param.setParam(form);

       Api030Biz biz = new Api030Biz(getRequestModel(req), con);
       String token = biz.getToken(param, req);

       GsMessage gsMsg = new GsMessage(req);
       /** メッセージ 変更 **/
       String change = gsMsg.getMessage("api.api030.10");

       //ログ出力
       ApiCommonBiz cmnBiz = new ApiCommonBiz();
       cmnBiz.outPutApiLog(map, getRequestModel(req), gsMsg, con,
                change, GSConstLog.LEVEL_INFO,
                String.valueOf(1) + gsMsg.getMessage("cmn.number")
                );

       JSONObject jsonData = new JSONObject();
       jsonData.element("success", true);
       jsonData.element("token", token);
       PrintWriter out = null;
       try {
           res.setHeader("Cache-Control", "no-cache");
           res.setContentType("application/json;charset=UTF-8");
           out = res.getWriter();
           out.print(jsonData);
           out.flush();
       } catch (IOException e) {
       } finally {
           if (out != null) {
               out.close();
           }
       }
       return map.getInputForward();
   }

   @Override
   public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
       return false;
   }
   @Override
   public String getPluginId() {
       return GSConst.PLUGINID_API;
   }
}

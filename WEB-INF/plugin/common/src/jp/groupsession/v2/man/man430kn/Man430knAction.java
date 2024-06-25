package jp.groupsession.v2.man.man430kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能]ユーザ連携自動インポート機能設定画面
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man430knAction extends AdminAction {

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
        Man430knForm thisForm = (Man430knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("back")) {
            //前画面に戻る
            forward = map.findForward("back");
        } else if (cmd.equals("confirm")) {
           //確定ボタン押下
            forward = __doConfirm(thisForm, map, req, con);
        } else {
            // トランザクショントークン設定
            saveToken(req);
            //初期表示
            forward = __doInit(thisForm, map, req, con);
        }

        return forward;
    }

    /**
     *
     * <br>[機  能] 初期表示時に必要なデータを画面にセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォワード
     */
    private ActionForward __doInit(Man430knForm form,
            ActionMapping map,
            HttpServletRequest req,
            Connection con) {

        Man430knParamModel paramMdl = new Man430knParamModel();
        Man430knBiz biz = new Man430knBiz();
        paramMdl.setParam(form);
        biz.setCmnExtDomain(paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     *
     * <br>[機  能]確定ボタン押下時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doConfirm(Man430knForm form,
            ActionMapping map,
            HttpServletRequest req,
            Connection con) throws SQLException {

        ActionErrors errors = form.validateCheck(map, req, con);
        //入力チェック
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(form, map, req, con);
        }

        Man430knParamModel paramMdl = new Man430knParamModel();
        Man430knBiz biz = new Man430knBiz();
        RequestModel reqMdl = getRequestModel(req);
        paramMdl.setParam(form);
        biz.insertDomains(con, paramMdl, reqMdl);
        paramMdl.setFormData(form);

        //オペレーションログを作成
        __outPutLog(req, con, map, form);

        return __doneDsp(req, con, map, form);
    }

    /**
    *
    * <br>[機  能]設定完了画面を表示する
    * <br>[解  説]
    * <br>[備  考]
    * @param req リクエスト
    * @param con コネクション
    * @param map アクションマッピング
    * @param thisForm アクションフォーム
    * @return アクションフォワード
    */
   private ActionForward __doneDsp(
           HttpServletRequest req,
           Connection con,
           ActionMapping map,
           Man430knForm thisForm) {

       Cmn999Form cmn999Form = new Cmn999Form();
       ActionForward urlForward = null;

       //表示件数設定完了画面パラメータの設定
       MessageResources msgRes = getResources(req);
       GsMessage gsMsg = new GsMessage(getRequestModel(req));

       cmn999Form.setType(Cmn999Form.TYPE_OK);
       cmn999Form.setIcon(Cmn999Form.ICON_INFO);
       cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
       cmn999Form.setMessage(
               msgRes.getMessage("settei.kanryo.object", gsMsg.getMessage("main.man430.2")));

       urlForward = map.findForward("mainAdminMenu");
       cmn999Form.setUrlOK(urlForward.getPath());

       req.setAttribute("cmn999Form", cmn999Form);

       return map.findForward("gf_msg");

   }

   /**
   *
   * <br>[機  能]オペレーションログを出力する
   * <br>[解  説]
   * <br>[備  考]
   * @param req リクエスト
   * @param con コネクション
   * @param map アクションマッピング
   * @param form アクションフォーム
   */
   private void __outPutLog(
           HttpServletRequest req,
           Connection con,
           ActionMapping map,
           Man430knForm form) {

           //ログ出力
           RequestModel reqMdl = getRequestModel(req);
           CommonBiz cmnBiz = new CommonBiz();
           GsMessage gsMsg = new GsMessage(reqMdl);

           //ログの本文を作成
           Man430knParamModel paramMdl = new Man430knParamModel();
           paramMdl.setParam(form);
           Man430knBiz biz = new Man430knBiz();
           String logValue = biz.setLogValue(paramMdl, gsMsg);

           String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                   + gsMsg.getMessage("main.man430.9");
           cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                   gsMsg.getMessage("cmn.change"),
                   GSConstLog.LEVEL_INFO, logValue, dspName);

   }

}

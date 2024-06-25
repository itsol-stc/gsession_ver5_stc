package jp.groupsession.v2.ntp.ntp210;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.ntp061.Ntp061Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 日報 案件情報ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp210Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp210Action.class);

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
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
     public ActionForward executeAction(ActionMapping map,
                                         ActionForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con) throws Exception {

         ActionForward forward = null;
         Ntp210Form thisForm = (Ntp210Form) form;
         Ntp061Biz biz = new Ntp061Biz(
                 con, getCountMtController(req), getRequestModel(req));
         if (!biz.isViewable(thisForm.getNtp210NanSid())) {
             //閲覧権限エラー
             GsMessage gsMsg = new GsMessage(req);
             Cmn999Form cmn999Form = new Cmn999Form();
             cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
             return getPermissionError(map, cmn999Form,
                     map.findForward("ntp060"),
                     gsMsg.getMessage("cmn.reading"),
                     req, res);
         }

         log__.debug("初期表示");
         forward = __doInit(map, thisForm, req, res, con);

         return forward;
     }

     /**
      * <br>[機  能] 初期表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws Exception 実行時例外
      * @return ActionForward
      */
     private ActionForward __doInit(ActionMapping map,
                                     Ntp210Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {

         BaseUserModel buMdl = getSessionUserModel(req);

         con.setAutoCommit(true);
         Ntp210Biz biz = new Ntp210Biz(con, getRequestModel(req));

         Ntp210ParamModel paramMdl = new Ntp210ParamModel();
         paramMdl.setParam(form);
         biz.setInitData(paramMdl, buMdl, con);
         paramMdl.setFormData(form);

         con.setAutoCommit(false);

         return map.getInputForward();
     }
}
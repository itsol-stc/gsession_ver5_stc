package jp.groupsession.v2.anp.anp160;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.anp.GSConstAnpi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] メッセージ配信確認 送信者一覧(ポップアップ画面)のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp160Action extends AbstractAnpiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp160Action.class);

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
     * @return アクションフォアード
     */
     public ActionForward executeAction(ActionMapping map,
                                         ActionForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con) throws Exception {

         ActionForward forward = null;
         Anp160Form thisForm = (Anp160Form) form;

         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         if (cmd.equals("anp160pageChange")) {
             //ページコンボボックス選択時
             forward = __movePage(map, thisForm, req, res, con, 0);

         } else if (cmd.equals("anp160pageLast")) {
             //「前ページ」ボタンクリック時
             forward = __movePage(map, thisForm, req, res, con, -1);

         } else if (cmd.equals("anp160pageNext")) {
             //「次ページ」ボタンクリック時
             forward = __movePage(map, thisForm, req, res, con, 1);

         } else {
             log__.debug("初期表示");
             forward = __doInit(map, thisForm, req, res, con);
         }

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
             Anp160Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {


         //初期データセット
         String anpSid = "";
         String grpSid = NullDefault.getString(req.getParameter("grpSid"), "-1");
         String mode = NullDefault.getString(req.getParameter("procMode"), "");
         if (!mode.equals(GSConstAnpi.MSG_HAISIN_MODE_NEW)
                 && !mode.equals(GSConstAnpi.MSG_HAISIN_MODE_COPY)) {
             anpSid = NullDefault.getString(req.getParameter("anpiSid"), "0");
             form.setAnpiSid(anpSid);
         }
         form.setAnp160GrpSid(grpSid);
         form.setAnp160ProcMode(mode);

         return __refresh(map, form, req, res, con);
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
      * @throws Exception 実行時例外
      * @return ActionForward
      */
     private ActionForward __refresh(ActionMapping map,
             Anp160Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {

       int anpSid = 0;
       int grpSid = Integer.valueOf(form.getAnp160GrpSid());
       String mode = form.getAnp160ProcMode();
       if (!mode.equals(GSConstAnpi.MSG_HAISIN_MODE_NEW)
               && !mode.equals(GSConstAnpi.MSG_HAISIN_MODE_COPY)) {
           anpSid = Integer.valueOf(form.getAnpiSid());
       }

         con.setAutoCommit(true);
         Anp160Biz biz = new Anp160Biz();
         Anp160ParamModel paramModel = new Anp160ParamModel();
         paramModel.setParam(form);
         biz.setInitData(paramModel, getRequestModel(req), con, anpSid, grpSid, mode);
         paramModel.setFormData(form);
         con.setAutoCommit(false);

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
                                      Anp160Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      int pageNo)
                               throws Exception {

         //ページ数調整
         int page = form.getAnp160NowPage();
         page += pageNo;
         if (page < 1) {
             page = 1;
         }
         form.setAnp160NowPage(page);

         return __refresh(map, form, req, res, con);
     }

}

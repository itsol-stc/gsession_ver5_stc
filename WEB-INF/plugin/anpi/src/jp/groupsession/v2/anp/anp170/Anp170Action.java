package jp.groupsession.v2.anp.anp170;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 状況内容確認 結果状況ポップアップ画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp170Action extends AbstractAnpiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp170Action.class);

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
         Anp170Form thisForm = (Anp170Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         log__.debug("CMD = " + cmd);

         if (cmd.equals("anp170pageChange")) {
             //ページコンボボックス選択時
             forward = __movePage(map, thisForm, req, res, con, 0);

         } else if (cmd.equals("anp170pageLast")) {
             //「前ページ」ボタンクリック時
             forward = __movePage(map, thisForm, req, res, con, -1);

         } else if (cmd.equals("anp170pageNext")) {
             //「次ページ」ボタンクリック時
             forward = __movePage(map, thisForm, req, res, con, 1);

         } else if (cmd.equals("anp170sortList")) {
             //ソート順変更（一覧列タイトルクリック時）
             forward = __sortList(map, thisForm, req, res, con);
         }  else {
             //初期化
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
             Anp170Form form,
             HttpServletRequest req,
             HttpServletResponse res,
             Connection con)
         throws Exception {


         String anpSid = NullDefault.getString(req.getParameter("anpSid"), "-1");
         String grpSid = NullDefault.getString(req.getParameter("grpSid"), "-1");

         form.setAnp170AnpSid(Integer.valueOf(anpSid));
         form.setAnp170GrpSid(Integer.valueOf(grpSid));


         return __doDisp(map, form, req, res, con);
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
     private ActionForward __doDisp(ActionMapping map,
             Anp170Form form,
             HttpServletRequest req,
             HttpServletResponse res,
             Connection con)
         throws Exception {


         con.setAutoCommit(true);
         Anp170Biz biz = new Anp170Biz();
         Anp170ParamModel paramModel = new Anp170ParamModel();
         paramModel.setParam(form);
         biz.setInitData(paramModel, con, getSessionUserSid(req));
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
             Anp170Form form,
             HttpServletRequest req,
             HttpServletResponse res,
             Connection con,
             int pageNo)
                     throws Exception {

         //ページ数調整
         int page = form.getAnp170NowPage();
         page += pageNo;
         if (page < 1) {
             page = 1;
         }
         form.setAnp170NowPage(page);

         return __doDisp(map, form, req, res, con);
     }

     /**
      * <br>[機  能] ソート順変更処理
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
     private ActionForward __sortList(ActionMapping map, Anp170Form form,
                     HttpServletRequest req, HttpServletResponse res, Connection con)
             throws Exception {

         //ソート設定
         int order = form.getAnp170OrderKey();
         if (order == GSConst.ORDER_KEY_ASC) {
             order = GSConst.ORDER_KEY_DESC;
         } else {
             order = GSConst.ORDER_KEY_ASC;
         }
         form.setAnp170OrderKey(order);

         return __doDisp(map, form, req, res, con);
     }
}
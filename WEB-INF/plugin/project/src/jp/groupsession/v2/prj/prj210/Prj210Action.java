package jp.groupsession.v2.prj.prj210;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.prj010.Prj010Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] プロジェクト管理 プロジェクト選択画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj210Action extends AbstractProjectAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj210Action.class);

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
         Prj210Form thisForm = (Prj210Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         if (cmd.equals("prevPage")) {
             //前ページクリック
             log__.debug("前ページ");
             thisForm.setPrj210page(thisForm.getPrj210page() - 1);
             forward = __doInit(map, thisForm, req, res, con);

         } else if (cmd.equals("nextPage")) {
             //次ページクリック
             log__.debug("次ページ");
             thisForm.setPrj210page(thisForm.getPrj210page() + 1);
             forward = __doInit(map, thisForm, req, res, con);
         } else if (cmd.equals("changePageTop")) {
             //上ページコンボ変更
             thisForm.setPrj210page(thisForm.getPrj210pageTop());
             forward = __doInit(map, thisForm, req, res, con);
         } else if (cmd.equals("changePageBottom")) {
             //下ページコンボ変更
             thisForm.setPrj210page(thisForm.getPrj210pageBottom());
             forward = __doInit(map, thisForm, req, res, con);
         } else if (cmd.equals("getImageFile")) {
             log__.debug("画像ダウンロード");
             forward = __doGetImageFile(map, thisForm, req, res, con);
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
                                     Prj210Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {

         con.setAutoCommit(true);
         BaseUserModel bumdl = getSessionUserModel(req);
         Prj210Biz biz = new Prj210Biz(con, getRequestModel(req));

         Prj210ParamModel paramMdl = new Prj210ParamModel();
         paramMdl.setParam(form);
         biz.setInitData(paramMdl, bumdl);
         paramMdl.setFormData(form);

         return map.getInputForward();
     }

     /**
      * <br>[機  能] tempディレクトリの画像を読み込む
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map マップ
      * @param form フォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con Connection
      * @return ActionForward フォワード
      * @throws Exception 実行時例外
      */
     private ActionForward __doGetImageFile(ActionMapping map,
             Prj210Form form,
                                             HttpServletRequest req,
                                             HttpServletResponse res,
                                             Connection con)
         throws Exception {

         CommonBiz cmnBiz = new CommonBiz();
         CmnBinfModel cbMdl = null;
         //画像バイナリSIDとプロジェクトSIDの照合チェック
         Prj010Biz prj010Biz = new Prj010Biz(con, getRequestModel(req));
         boolean icoHnt = prj010Biz.cheIcoHnt(form.getPrj010PrjSid(), form.getPrj010PrjBinSid());

         if (!icoHnt) {
             return null;

         } else {
             cbMdl = cmnBiz.getBinInfo(con, form.getPrj010PrjBinSid(),
                     GroupSession.getResourceManager().getDomain(req));
         }

         if (cbMdl != null) {
             JDBCUtil.closeConnectionAndNull(con);

             //ファイルをダウンロードする
             TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                         Encoding.UTF_8);
         }
         return null;
     }
}
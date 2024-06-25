package jp.groupsession.v2.ntp.ntp220;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 日報分析画面アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Ntp220Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp220Action.class);

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

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         log__.debug("CMD = " + cmd);

         ActionForward forward = null;
         Ntp220Form thisForm = (Ntp220Form) form;

         if (cmd.equals("nippou")) {
             forward = map.findForward("ntp010");
         } else if (cmd.equals("anken")) {
             forward = map.findForward("anken");
         } else if (cmd.equals("target")) {
             //目標設定画面へ
             forward = map.findForward("target");
         } else if (cmd.equals("masta")) {
             //マスタメンテ
             forward = map.findForward("masta");
         } else if (cmd.equals("ktool")) {
             //管理者ツール
             forward = map.findForward("ktool");
         } else if (cmd.equals("pset")) {
             //個人設定
             forward = map.findForward("pset");
         } else if (cmd.equals("showContent")) {
             //メニュー表示
             __getMenuContent(req, res, thisForm, con);
         } else if (cmd.equals("getSearchBtnResultList")) {
             //メニュー項目選択表示
             __searchMenuContent(map, thisForm, req, res, con);
         } else if (cmd.equals("getPeriodAnkenData")) {
             //期間案件データ取得
             __getPeriodAnkenData(req, res, thisForm, con);
         } else if (cmd.equals("getPeriodKadouData")) {
             //稼働時間データ取得
             __getPeriodKadouData(req, res, thisForm, con);
         } else if (cmd.equals("getContentData")) {
             //項目データ取得
             __getContentData(map, thisForm, req, res, con);
         } else if (cmd.equals("getAnkenStateCnt")) {
             //案件状態件数取得
             __getAnkenStateCnt(map, thisForm, req, res, con);
         } else if (cmd.equals("getKadouAnkenCnt")) {
             //稼働時間案件取得
             __getKadouAnkenCnt(map, thisForm, req, res, con);
         } else if (cmd.equals("getKadouKigyouCnt")) {
             //稼働時間企業取得
             __getKadouKigyouCnt(map, thisForm, req, res, con);
         } else if (cmd.equals("getKadouKbunruiCnt")) {
             //稼働時間活動分類取得
             __getKadouKbunruiCnt(map, thisForm, req, res, con);
         } else if (cmd.equals("getKadouKhouhouCnt")) {
             //稼働時間活動方法取得
             __getKadouKhouhouCnt(map, thisForm, req, res, con);
         } else if (cmd.equals("getKadouContentData")) {
             //稼働時間項目データ取得
             __getKadouContentData(map, thisForm, req, res, con);
         } else if (cmd.equals("getKadouContentDetail")) {
             //稼働時間項目データ取得
             __getKadouContentDetail(map, thisForm, req, res, con);
         } else if (cmd.equals("getAnkenContentStateCnt")) {
             //案件項目別状態件数取得
             __getAnkenContentStateCnt(map, thisForm, req, res, con);
         } else if (cmd.equals("getAnkenData")) {
             //案件データ取得
             __getAnkenData(req, res, thisForm, con);
         } else if (cmd.equals("getShohinData")) {
             //商品データ取得
             __getShohinData(req, res, thisForm, con);
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
                                     Ntp220Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {

         BaseUserModel buMdl = getSessionUserModel(req);

         con.setAutoCommit(true);
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         CommonBiz commonBiz = new CommonBiz();
         if (commonBiz.isPluginAdmin(con, buMdl, GSConstNippou.PLUGIN_ID_NIPPOU)) {
             form.setAdminKbn(GSConst.USER_ADMIN);
         } else {
             form.setAdminKbn(GSConst.USER_NOT_ADMIN);
         }

         Ntp220ParamModel paramMdl = new Ntp220ParamModel();
         paramMdl.setParam(form);
         biz.setInitData(paramMdl, buMdl, con);
         paramMdl.setFormData(form);

         con.setAutoCommit(false);

         return map.getInputForward();
     }

     /**
      * <br>メニュー項目取得
      * @param req リクエスト
      * @param res レスポンス
      * @param form アクションフォーム
      * @param con Connection
      * @throws Exception 実行例外
      */
     private void __getMenuContent(HttpServletRequest req,
                                 HttpServletResponse res,
                                 Ntp220Form form,
                                 Connection con) throws Exception {

         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         String selGrpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String contentName = NullDefault.getString(req.getParameter("contentName"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String offset = NullDefault.getString(req.getParameter("offset"), "");
         String alreadySelStr = NullDefault.getString(req.getParameter("alreadySelStr"), "");
         String gyoushuSid = NullDefault.getString(req.getParameter("gyoushuSid"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(gyoushuSid)) {
             gyoushuSid = "-1";
         }

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         boolean exitAnkenFlg = false;
         if (contentName.equals(GSConstNippou.STR_KADOU)) {
             exitAnkenFlg = true;
         } else {
             exitAnkenFlg = biz.existGrpUsrAnken(selGrpSid, selUsrSid, frDate, toDate,
                            Integer.valueOf(shohinCategory));
         }

         if (exitAnkenFlg && !StringUtil.isNullZeroStringSpace(contentName)
                          && !StringUtil.isNullZeroStringSpace(frdate)
                          && !StringUtil.isNullZeroStringSpace(todate)
                          && !StringUtil.isNullZeroStringSpace(offset)
                          && ValidateUtil.isNumber(offset)) {

             try {

                 //メニュー項目取得
                 JSONArray jsonData = biz.getMenuContent(
                                             selGrpSid,
                                             selUsrSid,
                                             contentName,
                                             frDate,
                                             toDate,
                                             Integer.valueOf(offset),
                                             alreadySelStr,
                                             Integer.valueOf(gyoushuSid),
                                             Integer.valueOf(shohinCategory));

                 if (jsonData != null && !jsonData.isEmpty()) {

                     try {
                         res.setHeader("Cache-Control", "no-cache");
                         res.setContentType("application/json;charset=UTF-8");
                         PrintWriter out = res.getWriter();
                         out.print(jsonData);
                         out.flush();
                     } catch (Exception e) {
                         log__.error("jsonデータ送信失敗(" + contentName + ")");
                     }
                 }

             } catch (SQLException e) {
                 log__.error("メニュー項目(" + contentName + ")の取得に失敗しました" + e);
                 throw e;
             }
         }
     }

     /**
      * <br>メニュー項目検索
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __searchMenuContent(ActionMapping map,
                                   Ntp220Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws SQLException, Exception {

         JSONObject jsonData = null;
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //選択グループSID
         String selGrpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         //ユーザSID
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         //メニュー項目取得
         String contentSid = NullDefault.getString(req.getParameter("contentSid"), "");
         //ページ番号取得
         String pageNum = NullDefault.getString(req.getParameter("pageNum"), "");
         //検索キーワード
         String searchWord = NullDefault.getString(req.getParameter("searchWord"), "");
         //選択開始日
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         //選択終了日
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         //カテゴリ
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");


         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         if (biz.existGrpUsrAnken(selGrpSid, selUsrSid, frDate, toDate,
                                                     Integer.valueOf(shohinCategory))
                 && !StringUtil.isNullZeroStringSpace(contentSid)) {
             //データ取得
             jsonData = biz.getJsonMenuContentList(
                     selGrpSid,
                     selUsrSid,
                     contentSid,
                     frDate,
                     toDate,
                     Integer.valueOf(pageNum),
                     searchWord,
                     Integer.valueOf(shohinCategory));
         }

         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData.toString());
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(メニュー項目検索)");
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }

     /**
      * <br>項目のデータを取得する
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getContentData(ActionMapping map,
                                      Ntp220Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException, Exception {

         JSONObject jsonData = new JSONObject();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String selGrpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String contentName = NullDefault.getString(req.getParameter("contentName"), "");
         String state = NullDefault.getString(req.getParameter("state"), "");
         String ankenState = NullDefault.getString(req.getParameter("ankenState"), "");
         String pageNum = NullDefault.getString(req.getParameter("pageNum"), "1");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String gyoushuSid = NullDefault.getString(req.getParameter("gyoushuSid"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(gyoushuSid)) {
             gyoushuSid = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         if (biz.existGrpUsrAnken(selGrpSid, selUsrSid, frDate, toDate,
                                                               Integer.valueOf(shohinCategory))
                 && !StringUtil.isNullZeroStringSpace(contentName)
                 && !StringUtil.isNullZeroStringSpace(state)
                 && !StringUtil.isNullZeroStringSpace(ankenState)) {

             //データ取得
             jsonData = biz.getContentData(
                     selGrpSid,
                     selUsrSid,
                     contentName,
                     Integer.valueOf(state),
                     Integer.valueOf(ankenState),
                     Integer.valueOf(pageNum),
                     frDate,
                     toDate,
                     Integer.valueOf(gyoushuSid),
                     Integer.valueOf(shohinCategory));
         }

         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData.toString());
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(メニュー項目検索)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }


     /**
      * <br>案件の状態の件数を取得する
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getAnkenStateCnt(ActionMapping map,
                                      Ntp220Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException, Exception {

         JSONObject jsonData = new JSONObject();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String contentName = NullDefault.getString(req.getParameter("contentName"), "");
         String state = NullDefault.getString(req.getParameter("state"), "");
         String childVal = NullDefault.getString(req.getParameter("childVal"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         if (biz.existGrpUsrAnken(grpSid, selUsrSid, frDate, toDate,
                                                                 Integer.valueOf(shohinCategory))
                 && !StringUtil.isNullZeroStringSpace(contentName)
                 && !StringUtil.isNullZeroStringSpace(state)) {

             //データ取得
             jsonData = biz.getStateData(
                     grpSid,
                     selUsrSid,
                     Integer.valueOf(state),
                     childVal,
                     frDate,
                     toDate,
                     Integer.valueOf(shohinCategory));
         }

         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData.toString());
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(案件状態)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }

     /**
      * <br>稼働時間に対する案件を取得する
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getKadouAnkenCnt(ActionMapping map,
                                      Ntp220Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException, Exception {

         JSONArray jsonData = new JSONArray();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String pageNum = NullDefault.getString(req.getParameter("pageNum"), "1");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         //データ取得
         jsonData = biz.getKadouAnkenData(
                 grpSid,
                 selUsrSid,
                 frDate,
                 toDate,
                 Integer.valueOf(pageNum));


         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData);
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(稼働時間      案件)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }

     /**
      * <br>稼働時間に対する企業を取得する
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getKadouKigyouCnt(ActionMapping map,
                                      Ntp220Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException, Exception {

         JSONArray jsonData = new JSONArray();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String pageNum = NullDefault.getString(req.getParameter("pageNum"), "1");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         //データ取得
         jsonData = biz.getKadouKigyouData(
                 grpSid,
                 selUsrSid,
                 frDate,
                 toDate,
                 Integer.valueOf(pageNum));


         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData);
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(稼働時間      案件)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }

     /**
      * <br>稼働時間に対する活動分類を取得する
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getKadouKbunruiCnt(ActionMapping map,
                                      Ntp220Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException, Exception {

         JSONArray jsonData = new JSONArray();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String pageNum = NullDefault.getString(req.getParameter("pageNum"), "1");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         //データ取得
         jsonData = biz.getKadouKbunruiData(
                 grpSid,
                 selUsrSid,
                 frDate,
                 toDate,
                 Integer.valueOf(pageNum));


         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData);
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(稼働時間      案件)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }

     /**
      * <br>稼働時間に対する活動方法を取得する
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getKadouKhouhouCnt(ActionMapping map,
                                      Ntp220Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException, Exception {

         JSONArray jsonData = new JSONArray();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String pageNum = NullDefault.getString(req.getParameter("pageNum"), "1");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         //データ取得
         jsonData = biz.getKadouKhouhouData(
                 grpSid,
                 selUsrSid,
                 frDate,
                 toDate,
                 Integer.valueOf(pageNum));


         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData);
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(稼働時間      案件)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }



     /**
      * <br>稼働時間に対する指定項目のデータを取得する
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getKadouContentData(ActionMapping map,
                                        Ntp220Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws SQLException, Exception {

         JSONArray jsonData = new JSONArray();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String pageNum = NullDefault.getString(req.getParameter("pageNum"), "1");
         String childVal = NullDefault.getString(req.getParameter("childVal"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         //データ取得
         jsonData = biz.getKadouContentData(
                 grpSid,
                 selUsrSid,
                 childVal,
                 frDate,
                 toDate,
                 Integer.valueOf(pageNum));


         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData);
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(稼働時間      案件)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }



     /**
      * <br>稼働時間に対する指定項目のデータの詳細取得する
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getKadouContentDetail(ActionMapping map,
                                        Ntp220Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws SQLException, Exception {

         JSONArray jsonData = new JSONArray();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String pageNum = NullDefault.getString(req.getParameter("pageNum"), "1");
         String childVal = NullDefault.getString(req.getParameter("childVal"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         //データ取得
         jsonData = biz.getKadouDetailData(
                 grpSid,
                 selUsrSid,
                 childVal,
                 frDate,
                 toDate,
                 Integer.valueOf(pageNum));


         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData);
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(稼働時間      案件)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }











     /**
      * <br>案件の状態の件数を取得する(項目別)
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      */
     private void __getAnkenContentStateCnt(ActionMapping map,
                                      Ntp220Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException, Exception {

         JSONArray jsonData = new JSONArray();
         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         //メニュー項目取得
         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String contentName = NullDefault.getString(req.getParameter("contentName"), "");
         String state = NullDefault.getString(req.getParameter("state"), "");
         String ankenState = NullDefault.getString(req.getParameter("ankenState"), "");
         String childVal = NullDefault.getString(req.getParameter("childVal"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String gyoushuSid = NullDefault.getString(req.getParameter("gyoushuSid"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         if (!ValidateUtil.isNumber(gyoushuSid)) {
             gyoushuSid = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         if (biz.existGrpUsrAnken(grpSid, selUsrSid, frDate, toDate,
                                                              Integer.valueOf(shohinCategory))
                 && !StringUtil.isNullZeroStringSpace(contentName)
                 && !StringUtil.isNullZeroStringSpace(state)) {

             //データ取得
             jsonData = biz.getContentStateData(
                     grpSid,
                     selUsrSid,
                     contentName,
                     Integer.valueOf(state),
                     Integer.valueOf(ankenState),
                     childVal,
                     frDate,
                     toDate,
                     Integer.valueOf(gyoushuSid),
                     Integer.valueOf(shohinCategory));
         }

         PrintWriter out = null;

         try {
             res.setHeader("Cache-Control", "no-cache");
             res.setContentType("application/json;charset=UTF-8");
             out = res.getWriter();
             out.print(jsonData);
             out.flush();
         } catch (Exception e) {
             log__.error("jsonデータ送信失敗(案件状態)");
             throw e;
         } finally {
             if (out != null) {
                 out.close();
             }
         }
     }


     /**
      * <br>案件情報取得
      * @param req リクエスト
      * @param res レスポンス
      * @param form アクションフォーム
      * @param con Connection
      * @throws Exception 実行例外
      */
     private void __getAnkenData(HttpServletRequest req,
                                 HttpServletResponse res,
                                 Ntp220Form form,
                                 Connection con) throws Exception {

         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         String selGrpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String contentName = NullDefault.getString(req.getParameter("contentName"), "");
         String state = NullDefault.getString(req.getParameter("state"), "");
         String ankenState = NullDefault.getString(req.getParameter("ankenState"), "");
         String ankenDataPageNum = NullDefault.getString(req.getParameter("ankenDataPageNum"), "");
         String childVal = NullDefault.getString(req.getParameter("childVal"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String gyoushuSid = NullDefault.getString(req.getParameter("gyoushuSid"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         if (!ValidateUtil.isNumber(gyoushuSid)) {
             gyoushuSid = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         if (biz.existGrpUsrAnken(selGrpSid, selUsrSid, frDate, toDate,
                                                                 Integer.valueOf(shohinCategory))
                && !StringUtil.isNullZeroStringSpace(contentName)
                && !StringUtil.isNullZeroStringSpace(state)
                && !StringUtil.isNullZeroStringSpace(ankenState)
                && !StringUtil.isNullZeroStringSpace(ankenDataPageNum)) {

             try {
                 //案件情報取得
                 JSONArray jsonData = biz.getAnkenData(
                         selGrpSid,
                         selUsrSid,
                         Integer.valueOf(ankenState),
                         Integer.valueOf(state),
                         Integer.valueOf(ankenDataPageNum),
                         childVal,
                         frDate,
                         toDate,
                         Integer.valueOf(gyoushuSid),
                         Integer.valueOf(shohinCategory));

                 if (jsonData != null && !jsonData.isEmpty()) {
                     try {
                         res.setHeader("Cache-Control", "no-cache");
                         res.setContentType("application/json;charset=UTF-8");
                         PrintWriter out = res.getWriter();
                         out.print(jsonData);
                         out.flush();
                     } catch (Exception e) {
                         log__.error("jsonデータ送信失敗(案件)");
                     }
                 }

             } catch (SQLException e) {
                 log__.error("メニュー項目(案件)の取得に失敗しました" + e);
                 throw e;
             }
         }
     }

     /**
      * <br>商品情報取得
      * @param req リクエスト
      * @param res レスポンス
      * @param form アクションフォーム
      * @param con Connection
      * @throws Exception 実行例外
      */
     private void __getShohinData(HttpServletRequest req,
                                 HttpServletResponse res,
                                 Ntp220Form form,
                                 Connection con) throws Exception {

         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         String grpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String contentName = NullDefault.getString(req.getParameter("contentName"), "");
         String state = NullDefault.getString(req.getParameter("state"), "");
         String ankenState = NullDefault.getString(req.getParameter("ankenState"), "");
         String childVal = NullDefault.getString(req.getParameter("childVal"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         if (biz.existGrpUsrAnken(grpSid, selUsrSid, frDate, toDate,
                                                               Integer.valueOf(shohinCategory))
                && !StringUtil.isNullZeroStringSpace(contentName)
                && !StringUtil.isNullZeroStringSpace(state)
                && !StringUtil.isNullZeroStringSpace(ankenState)) {

             try {
                 //案件情報取得
                 JSONArray jsonData = biz.getShohinData(
                         grpSid,
                         selUsrSid,
                         Integer.valueOf(ankenState),
                         Integer.valueOf(state),
                         childVal,
                         frDate,
                         toDate,
                         Integer.valueOf(shohinCategory));

                 if (jsonData != null && !jsonData.isEmpty()) {
                     try {
                         res.setHeader("Cache-Control", "no-cache");
                         res.setContentType("application/json;charset=UTF-8");
                         PrintWriter out = res.getWriter();
                         out.print(jsonData);
                         out.flush();
                     } catch (Exception e) {
                         log__.error("jsonデータ送信失敗(商品)");
                     }
                 }

             } catch (SQLException e) {
                 log__.error("メニュー項目(商品)の取得に失敗しました" + e);
                 throw e;
             }
         }
     }

     /**
      * <br>期間案件情報取得
      * @param req リクエスト
      * @param res レスポンス
      * @param form アクションフォーム
      * @param con Connection
      * @throws Exception 実行例外
      */
     private void __getPeriodAnkenData(HttpServletRequest req,
                                 HttpServletResponse res,
                                 Ntp220Form form,
                                 Connection con) throws Exception {

         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         String selGrpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String state = NullDefault.getString(req.getParameter("state"), "");
         String ankenState = NullDefault.getString(req.getParameter("ankenState"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String defGraphMode = NullDefault.getString(req.getParameter("defGraphMode"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         if (biz.existGrpUsrAnken(selGrpSid, selUsrSid, frDate, toDate,
                                                                  Integer.valueOf(shohinCategory))
                && !StringUtil.isNullZeroStringSpace(state)
                && !StringUtil.isNullZeroStringSpace(ankenState)) {

             try {
                 //案件情報取得
                 JSONArray jsonData = biz.getPeriodAnkenData(
                         selGrpSid,
                         selUsrSid,
                         Integer.valueOf(ankenState),
                         Integer.valueOf(state),
                         frDate,
                         toDate,
                         defGraphMode,
                         Integer.valueOf(shohinCategory));

                 if (jsonData != null && !jsonData.isEmpty()) {
                     try {
                         res.setHeader("Cache-Control", "no-cache");
                         res.setContentType("application/json;charset=UTF-8");
                         PrintWriter out = res.getWriter();
                         out.print(jsonData);
                         out.flush();
                     } catch (Exception e) {
                         log__.error("jsonデータ送信失敗(案件)");
                     }
                 }

             } catch (SQLException e) {
                 log__.error("メニュー項目(案件)の取得に失敗しました" + e);
                 throw e;
             }
         }
     }

     /**
      * <br>期間案件情報取得
      * @param req リクエスト
      * @param res レスポンス
      * @param form アクションフォーム
      * @param con Connection
      * @throws Exception 実行例外
      */
     private void __getPeriodKadouData(HttpServletRequest req,
                                 HttpServletResponse res,
                                 Ntp220Form form,
                                 Connection con) throws Exception {

         Ntp220Biz biz = new Ntp220Biz(con, getRequestModel(req));

         String selGrpSid = NullDefault.getString(req.getParameter("selGrpSid"), "");
         String selUsrSid = NullDefault.getString(req.getParameter("selUsrSid"), "");
         String frdate = NullDefault.getString(req.getParameter("frdate"), "");
         String todate = NullDefault.getString(req.getParameter("todate"), "");
         String shohinCategory = NullDefault.getString(req.getParameter("shohinCategory"), "-1");

         if (!ValidateUtil.isNumber(shohinCategory)) {
             shohinCategory = "-1";
         }

         UDate frDate = biz.getDateFromString(frdate);
         UDate toDate = biz.getDateFromString(todate);

         try {
             //稼働時間情報取得
             JSONArray jsonData = biz.getPeriodKadouData(
                     selGrpSid,
                     selUsrSid,
                     frDate,
                     toDate);

             if (jsonData != null && !jsonData.isEmpty()) {
                 try {
                     res.setHeader("Cache-Control", "no-cache");
                     res.setContentType("application/json;charset=UTF-8");
                     PrintWriter out = res.getWriter();
                     out.print(jsonData);
                     out.flush();
                 } catch (Exception e) {
                     log__.error("jsonデータ送信失敗(案件)");
                 }
             }

         } catch (SQLException e) {
             log__.error("メニュー項目(案件)の取得に失敗しました" + e);
             throw e;
         }
     }
}
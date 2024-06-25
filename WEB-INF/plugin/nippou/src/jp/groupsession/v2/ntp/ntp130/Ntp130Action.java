package jp.groupsession.v2.ntp.ntp130;

import java.io.PrintWriter;
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
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.AbstractNippouMstmntAction;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.ntp061.Ntp061Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] 日報 商品一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp130Action extends AbstractNippouMstmntAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp130Action.class);

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
     *                      org.apache.struts.action.ActionFsorm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp130Form thisForm = (Ntp130Form) form;

        if (cmd.equals("backNtp130")) {
            forward = __doBack(map, req, thisForm);
        } else if (cmd.equals("add") || cmd.equals("edit")) {
            forward = map.findForward("ntp131");
        } else if (cmd.equals("search")) {
            log__.debug("検索");
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setNtp130PageTop(thisForm.getNtp130PageTop() - 1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setNtp130PageTop(thisForm.getNtp130PageTop() + 1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("changePage")) {
            //ページコンボ変更
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("ok")) {
            forward = __doOk(map, req, thisForm);
        } else if (cmd.equals("import")) {
            //インポートボタン
            forward = map.findForward("ntp132");
        } else if (cmd.equals("export")) {
            log__.debug("CSVファイルダウンロード");
            forward =  __doExportOk(map, thisForm, req, res, con);
        } else if (cmd.equals("addCategory")) {
            //カテゴリ追加
            forward = map.findForward("ntp133");
        } else if (cmd.equals("categoryEdit")) {
            //カテゴリ追加
            forward = map.findForward("ntp133");
        } else if (cmd.equals("getShohinList")) {
            //ユーザ一覧取得
            __getShohinList(map, thisForm, req, res, con);
        } else if (cmd.equals("ntp130up")) {
            log__.debug("上へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_UP);

        } else if (cmd.equals("ntp130down")) {
            log__.debug("下へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_DOWN);
        } else {
            log__.debug("初期表示");
            forward = __doSearch(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doSearch(ActionMapping map,
                                    Ntp130Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionMessage msg = null;

        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
        Ntp130Biz biz = new Ntp130Biz(getRequestModel(req));

        //入力チェック
        ActionErrors errors = form.validateCheck();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            Ntp130ParamModel paramMdl = new Ntp130ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, sessionUserSid, con);
            paramMdl.setFormData(form);
            return map.getInputForward();
        }

        Ntp130ParamModel paramMdl = new Ntp130ParamModel();
        paramMdl.setParam(form);
        biz.doSearch(paramMdl, sessionUserSid, con);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        log__.debug("*****商品件数 = " + form.getNtp130ShohinList().size());

        if (form.getNtp130ShohinList().size() == 0) {
            msg = new ActionMessage("search.data.notfound", "商品");
            StrutsUtil.addMessage(errors, msg, "ntp130NhnSid__");
            addErrors(req, errors);
        }
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }
    /**
     * <br>[機  能] 戻るボタンをクリック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, HttpServletRequest req,
                                    Ntp130Form form) {

        if (form.getNtp130ReturnPage().equals("ntp061")) {
            Ntp061Form ntp061Form = new Ntp061Form();
            ntp061Form.setNtp061ChkShohinSidList(form.getNtp130SvChkShohinSidList());
            req.setAttribute("ntp061Form", ntp061Form);
        }
        return map.findForward(form.getNtp130ReturnPage());
    }
    /**
     * <br>[機  能] OKボタンをクリック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doOk(ActionMapping map, HttpServletRequest req,
                                    Ntp130Form form) {

        if (form.getNtp130ReturnPage().equals("ntp061")) {
            Ntp061Form ntp061Form = new Ntp061Form();
            ntp061Form.setNtp061ChkShohinSidList(form.getNtp130ChkShohinSidList());
            req.setAttribute("ntp061Form", ntp061Form);
        }
        return map.findForward(form.getNtp130ReturnPage());
    }

    /**
     * エクスポート処理実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doExportOk(ActionMapping map,
                                        Ntp130Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        //検索条件をサーチモデルにセット
        Ntp130SearchModel searchMdl = setSearchData(form);
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage();

        log__.debug("エクスポート処理実行");
        Ntp130Biz biz = new Ntp130Biz(reqMdl);
        String tempDir = biz.getTempDir();

        //CSVファイルを作成
        String fileName = ShohinCsvWriter.FILE_NAME;
        ShohinCsvWriter write = new ShohinCsvWriter(req, searchMdl);
        write.outputCsv(con, tempDir);

        //ダウンロード
        String fullPath = tempDir + fileName;
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        biz.deleteTempDir();

        /** メッセージ エクスポート **/
        String export = gsMsg.getMessage(req, "cmn.export");

        //ログ出力
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, reqMdl);
        ntpBiz.outPutLog(
                map,
                export, GSConstLog.LEVEL_INFO, fileName);
        return null;
    }

    /**
     * <br>[機  能] フォームからわたってくる検索条件をサーチモデルにセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param form Ntp130Form
     * @return serchMdl サーチモデル
     */
    private Ntp130SearchModel setSearchData(Ntp130Form form) {
        Ntp130SearchModel searchMdl = new Ntp130SearchModel();
        searchMdl.setNhnCode(form.getNtp130NhnCode());
        searchMdl.setNhnName(form.getNtp130NhnName());

        String price = NullDefault.getStringZeroLength(form.getNtp130NhnPriceSale(), "-1");
        searchMdl.setNhnPriceSale(Integer.parseInt(price));
        searchMdl.setNhnPriceSaleKbn(form.getNtp130NhnPriceSaleKbn());

        String cost = NullDefault.getStringZeroLength(form.getNtp130NhnPriceCost(), "-1");
        searchMdl.setNhnPriceCost(Integer.parseInt(cost));
        searchMdl.setNhnPriceCostKbn(form.getNtp130NhnPriceCostKbn());

        searchMdl.setSortKey1(form.getNtp130SortKey1());
        searchMdl.setSortKey2(form.getNtp130SortKey2());
        searchMdl.setOrderKey1(form.getNtp130OrderKey1());
        searchMdl.setOrderKey2(form.getNtp130OrderKey2());
        return searchMdl;
    }

    /**
     * <br>商品一覧取得
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __getShohinList(
            ActionMapping map,
            Ntp130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        JSONObject jsonData = null;
        Ntp130Biz biz = new Ntp130Biz(getRequestModel(req));

        //カテゴリSID取得
        String categorySid = NullDefault.getString(req.getParameter("categorySid"), "");
        //ページ番号取得
        String pageNum = NullDefault.getString(req.getParameter("pageNum"), "");

        if (!StringUtil.isNullZeroStringSpace(categorySid)) {
            //データ取得
            jsonData = biz.getJsonShohinList(
                    con, Integer.valueOf(categorySid),  Integer.valueOf(pageNum));
        }

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(商品一覧取得)");
        } finally {
            if (out != null) {
                out.close();
            }
        }
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
     * @return ActionForward
     * @throws Exception 例外
     */
    private ActionForward __doSortChange(
        ActionMapping map,
        Ntp130Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {
        
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;


        try {
            Ntp130Biz biz = new Ntp130Biz(getRequestModel(req));

            Ntp130ParamModel paramMdl = new Ntp130ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(con, paramMdl, changeKbn);
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

        return __doSearch(map, form, req, res, con);
    }
}

package jp.groupsession.v2.mem.mem010;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.mem.AbstractMemoAction;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.GSValidateMemo;
import jp.groupsession.v2.mem.biz.MemCommonBiz;
import jp.groupsession.v2.mem.model.MemoLabelModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メモ メモ画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem010Action extends AbstractMemoAction {

    /**
     * <br>[機能]アクションを実行する
     * <br>[解説]
     * <br>[備考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォワード
     */
    public ActionForward executeAction(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Mem010Form thisForm = (Mem010Form) form;

        if (cmd.equals("scrollRead")) {
            //スクロールによるメモの読み込み処理
            __doSearchDetail(thisForm, req, res, con, false);
            return null;
        } else if (cmd.equals("insertMemo")) {
            //メモ登録
            __doInsertMemo(map, thisForm, req, res, con);
            return null;
        } else if (cmd.equals("detailSearch")) {
            //メモ検索
            __doSearchDetail(thisForm, req, res, con, true);
            return null;
        } else if (cmd.equals("addLabel")) {
            //ラベル登録
            __doInsertLabel(map, thisForm, req, res, con);
            return null;
        } else if (cmd.equals("deleteTmpData")) {
            //テンポラリディレクトリにあるファイルの削除
            __doDeleteTemporaryFile(thisForm, req, res);
            return null;
        } else if (cmd.equals("showMemoData")) {
            //メモ詳細表示
            __doShowDetail(thisForm, req, res, con);
            return null;
        } else if (cmd.equals("deleteMemo")) {
            //メモ削除
            __doDeleteMemo(map, thisForm, req, res, con);
            return null;
        } else if (cmd.equals("updateMemo")) {
            //メモ変更
            __doUpdateMemo(map, thisForm, req, res, con);
            return null;
        } else if (cmd.equals("fileDownload")) {
            //ファイルダウンロード
            return __doDownLoad(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteTmpAll")) {
            //テンポラリディレクトリの初期化
            __doClearTemporary(thisForm, req, res);
            return null;
        } else if (cmd.equals("saveDisplay")) {
            //画面を閉じた際の状態の保存
            __doSaveMode(thisForm, req, con);
        } else {
            //初期表示
            __doInit(thisForm, req, res, con);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doInit(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        con.setAutoCommit(true);

        try {
            RequestModel reqMdl = getRequestModel(req);
            Mem010Biz biz = new Mem010Biz(con, reqMdl);
            Mem010ParamModel paramMdl = new Mem010ParamModel();
            paramMdl.setParam(form);

            //添付ファイルの有無を指定なしに変更
            paramMdl.setMem010SvSearchTenpu(GSConstMemo.TENPU_KBN_NONE);

            //初期表示データの設定
            biz._setInitData(paramMdl);
            paramMdl.setFormData(form);

            //メモ画面の表示状態の取得
            int mode = biz._getDisplayMode(con);
            form.setMem010DisplayMode(mode);

            //テンポラリディレクトリパスの取得
            GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();

            //テンポラリディレクトリの作成
            tempUtil.deleteTempPath(reqMdl, GSConstMemo.PLUGIN_ID_MEMO, GSConstMemo.DIRID_MEM010);
            tempUtil.createTempDir(reqMdl, GSConstMemo.PLUGIN_ID_MEMO, GSConstMemo.DIRID_MEM010);

            //トークンの生成
            saveToken(req);
        } finally {
            con.setAutoCommit(false);
        }

    }

    /**
     * <br>[機  能] メモを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doInsertMemo(
            ActionMapping map,
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //保持用検索検索条件チェック
        List<String> errors = form.validateSearch(req, con, reqMdl);
        if (!errors.isEmpty()) {
            return;
        }

        //入力チェック
        errors = form.validateMemo(req, con, reqMdl);
        if (!errors.isEmpty()) {
            __doRedrawValidateError(form, req, res, errors);
            return;
        }

        //アプリケーションのルートパス
        String appRootPath = getAppRootPath();

        //メモの登録
        Mem010Biz biz = new Mem010Biz(con, reqMdl);
        Mem010ParamModel paramMdl = new Mem010ParamModel();
        paramMdl.setParam(form);
        MlCountMtController cntCon = getCountMtController(req);
        boolean commitFlg = false;
        try {
            biz._insertMemoData(paramMdl, cntCon, appRootPath);
            con.commit();
            commitFlg = true;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        //テンポラリディレクトリにあるファイルの削除
        MemCommonBiz memCmnBiz = new MemCommonBiz();
        memCmnBiz.clearTempDir(reqMdl, GSConstMemo.DIRID_MEM010);

        paramMdl.setFormData(form);

        //画面描画処理
        __doRedrawData(form, req, res, Mem010Form.MODE_INSERT_MEMO);
    }

    /**
     *
     * <br>[機  能] ラベルの追加
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doInsertLabel(
            ActionMapping map,
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        Mem010Biz biz = new Mem010Biz(con, reqMdl);
        Mem010ParamModel paramMdl = new Mem010ParamModel();
        paramMdl.setParam(form);

        List<String> strErrors = new ArrayList<String>();
        ActionErrors errors = new ActionErrors();

        if (form.getMem010addLabelMode() == GSConstMemo.MODE_ADD) {
            //ラベルの登録
            String label = form.getMem010addLabelName();
            errors = GSValidateMemo.validateLabel(errors, label, reqMdl);
            
        } else if (paramMdl.getMem010addLabelMode() == GSConstMemo.MODE_SELECT) {
            //ラベルの選択
            int labelSid = form.getMem010targetLabelSid();
            errors = GSValidateMemo.existLabel(con, errors, labelSid, reqMdl);
        }
        
        MessageResources resources = getResources(req);
        Iterator<ActionMessage> iterator = (Iterator<ActionMessage>) errors.get();
        while (iterator.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) iterator.next();
            String key = actionMessage.getKey();
            Object[] obj = actionMessage.getValues();
            strErrors.add(resources.getMessage(key, obj));
        }

        if (!errors.isEmpty()) {
            __doRedrawValidateError(form, req, res, strErrors);
            return;
        }
        
        //ラベルの追加
        if (paramMdl.getMem010addLabelMode() == GSConstMemo.MODE_ADD) {
            MlCountMtController cntCon = getCountMtController(req);
            boolean commitFlg = false;
            try {
                biz._insertLabelData(paramMdl, cntCon);
                con.commit();
                commitFlg = true;
            } finally {
                if (!commitFlg) {
                    JDBCUtil.rollback(con);
                }
            }
        }

        //ラベルの選択
        if (paramMdl.getMem010addLabelMode() == GSConstMemo.MODE_SELECT) {
            biz._setLabelData(paramMdl);
        }
        paramMdl.setFormData(form);

        //画面描画処理
        __doRedrawData(form, req, res, Mem010Form.MODE_ADD_LABEL);
    }

    /**
     * <br>[機  能] メモ詳細検索の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param delFlg テンポラリディレクトリの初期化フラグ
     * @throws Exception 実行例外
     */
    private void __doSearchDetail(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            boolean delFlg) throws Exception {

        //メモの取得
        con.setAutoCommit(true);

        try {
            RequestModel reqMdl = getRequestModel(req);

            //入力チェック
            List<String> errors = form.validateSearch(req, con, reqMdl);
            if (!errors.isEmpty()) {
                __doRedrawSearchError(form, req, res, errors);
                return;
            }

            //入力された検索内容を保持用に保存
            __setSearchSv(form);

            Mem010Biz biz = new Mem010Biz(con, reqMdl);
            Mem010ParamModel paramMdl = new Mem010ParamModel();

            paramMdl.setParam(form);
            int usrSid = getSessionUserSid(req);

            //メモ表示件数チェック 
            int memoCount = form.getMem010MemoCount();
            int dbCount = biz._getMemoCount(paramMdl, usrSid);
            if (memoCount > dbCount) {
                return; 
            }

            if (delFlg) {
                //テンポラリディレクトリの初期化
                MemCommonBiz memCmnBiz = new MemCommonBiz();
                memCmnBiz.clearTempDir(reqMdl, GSConstMemo.DIRID_MEM010);    
            }

            biz._setMemoData(paramMdl);

            paramMdl.setFormData(form);    
        } finally {
            con.setAutoCommit(false);
        }

        //画面描画処理
        __doRedrawData(form, req, res, Mem010Form.MODE_SEARCH);
    }

    /**
     * <br>[機  能] メモ詳細表示の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doShowDetail(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        con.setAutoCommit(true);

        try {
            RequestModel reqMdl = getRequestModel(req);

            //入力チェック
            List<String> errors = form.validateDetail(req, con, reqMdl);
            if (!errors.isEmpty()) {
                __doRedrawSearchError(form, req, res, errors);
                return;
            }

            Mem010ParamModel paramMdl = new Mem010ParamModel();
            paramMdl.setParam(form);
            Mem010Biz biz = new Mem010Biz(con, reqMdl);

            //アプリケーションのルートパス
            String appRootPath = getAppRootPath();

            //メモ明細情報の設定
            biz._setMemoDetail(paramMdl, appRootPath,
                    GroupSession.getResourceManager().getDomain(req), reqMdl);
            paramMdl.setFormData(form);    
        } finally {
            con.setAutoCommit(false);
        }

        //画面描画処理
        __doRedrawData(form, req, res, Mem010Form.MODE_SHOW_DETAIL);
    }

    /**
     * <br>[機  能] メモの削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doDeleteMemo(
            ActionMapping map,
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        int usrSid = getSessionUserSid(req);
        Mem010Biz biz = new Mem010Biz(con, reqMdl);
        Mem010ParamModel paramMdl = new Mem010ParamModel();
        paramMdl.setParam(form);

        boolean flg = false;

        //保持用検索検索条件チェック
        List<String> errors = form.validateSearch(req, con, reqMdl);
        if (!errors.isEmpty()) {
            return;
        }

        //入力チェック
        errors = form.validateDelete(reqMdl);
        if (!errors.isEmpty()) {
            __doRedrawValidateError(form, req, res, errors);
            return;
        }

        //存在するメモSIDのリストを取得
        long[] delAry = paramMdl.getMem010DeleteMemoSidArray();
        List<Long> existMemoSidList = biz._getExistSidList(usrSid, delAry);

        MemCommonBiz memCmnBiz = new MemCommonBiz();
        delAry = memCmnBiz.listToArray(existMemoSidList);
        paramMdl.setMem010DeleteMemoSidArray(delAry);

        //変更中のメモを含んでいるかの判定
        if (existMemoSidList.contains(form.getMem010TargetMemoSid())) {
            memCmnBiz.clearTempDir(reqMdl, GSConstMemo.DIRID_MEM010);
            flg = true;
        }

        boolean commitFlg = false;
        try {
            //メモの削除
            memCmnBiz.deleteMemo(con, existMemoSidList);
            con.commit();
            commitFlg = true;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con); 
            }
        }

        //メモの取得
        biz._setMemoData(paramMdl);
        paramMdl.setFormData(form);

        //変更中のメモの有無によって表示の切り替え
        if (flg) {
            __doRedrawData(form, req, res, Mem010Form.MODE_DELETE_HENKOU);
        } else {
            __doRedrawData(form, req, res, Mem010Form.MODE_DELETE);
        }
    }

    /**
     * <br>[機  能] メモの更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doUpdateMemo(
            ActionMapping map,
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //保持用検索検索条件チェック
        List<String> errors = form.validateSearch(req, con, reqMdl);
        if (!errors.isEmpty()) {
            return;
        }

        //メモ内容入力チェック
        errors = form.validateMemo(req, con, reqMdl);
        if (!errors.isEmpty()) {
            __doRedrawValidateError(form, req, res, errors);
            return;
        }

        Mem010Biz biz = new Mem010Biz(con, reqMdl);
        Mem010ParamModel paramMdl = new Mem010ParamModel();
        paramMdl.setParam(form);
        int usrSid = getSessionUserSid(req);

        //存在するメモのSIDリストを取得
        long[] targetSidAry = {paramMdl.getMem010TargetMemoSid()};
        List<Long> existMemoSidList = biz._getExistSidList(usrSid, targetSidAry);

        //変更対象のメモが存在しない時
        if (existMemoSidList.isEmpty()) {
            errors.clear();
            String msg = null;
            GsMessage gsMsg = new GsMessage(reqMdl);
            msg = gsMsg.getMessage("memo.mem010.24");
            errors.add(msg);
            __doRedrawValidateError(form, req, res, errors);
            return;
        }

        String appRootPath = getAppRootPath();
        MlCountMtController cntCon = getCountMtController(req);

        boolean commitFlg = false;
        try {
            //メモの変更
            biz._updateMemoData(paramMdl, usrSid, con, cntCon, appRootPath);
            con.commit();
            commitFlg = true;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        paramMdl.setFormData(form);

        //画面描画処理
        __doRedrawData(form, req, res, Mem010Form.MODE_UPDATE);
    }

    /**
     * <br>[機  能] 詳細検索，スクロール読み込み時の再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param errors エラーメッセージ
     * @throws Exception 実行時例外
     */
    private void __doRedrawSearchError(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            List<String> errors) throws Exception {

        JSONObject jsonData = new JSONObject();
        Mem010ParamModel paramMdl = new Mem010ParamModel();
        paramMdl.setParam(form);
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", false);
        jsonData.element("error", false);
        jsonData.element("size", errors.size());
        for (int i = 0; i < errors.size(); i++) {
            jsonData.element("errorMsg_" + i, errors.get(i));
        }
        paramMdl.setFormData(form);

        //レスポンスの出力
        __writeResp(res, jsonData);
    }

    /**
     * <br>[機  能] テンポラリディレクトリから指定されたファイルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @throws Exception 実行例外
     */
    private void __doDeleteTemporaryFile(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        //選択された添付ファイルを削除する
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteFile(form.getMem010selectFile(), getRequestModel(req),
                GSConstMemo.PLUGIN_ID_MEMO, GSConstMemo.DIRID_MEM010);

        //画面描画処理
        __doRedrawData(form, req, res, Mem010Form.MODE_DELETE_FILE);
    }

    /**
     * <br>[機  能] テンポラリディレクトリからファイルを全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @throws Exception 実行例外
     */
    private void __doClearTemporary(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリの削除
        MemCommonBiz biz = new MemCommonBiz();
        biz.clearTempDir(reqMdl, GSConstMemo.DIRID_MEM010);

        //画面描画処理
        __doRedrawData(form, req, res, Mem010Form.MODE_CLEAR_TEMP);
    }

    /**
     * <br>[機  能] 指定されたファイルをダウンロードする
     * <br>[解  説]
     * <br>[備  考]
     * @param map map
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDownLoad(
            ActionMapping map,
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        String fileId = form.getMem010selectFile()[0];
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);
        Mem010Biz biz = new Mem010Biz(con, reqMdl);       
        String selectFile = fileId + "file";
        String fullPath = biz._getTempDir() + selectFile;

        try {
            //オブジェクトファイルを取得
            ObjectFile objFile =
                    new ObjectFile(biz._getTempDir(), fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
            Object fObj = objFile.load();
            Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
            String fileName = fMdl.getFileName();
            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);
            //ファイルのダウンロード
            TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * <br>[機  能] メモ画面の表示状態を保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __doSaveMode(Mem010Form form, HttpServletRequest req, Connection con)
            throws SQLException {

        int mode = form.getMem010DisplayMode();

        //modeに想定外の値が入っていた場合、処理を中断する
        if (!(mode == 0 || mode == 1)) {
            return;
        }

        int usrSid = getSessionUserSid(req);
        RequestModel reqMdl = getRequestModel(req);
        Mem010Biz biz = new Mem010Biz(con, reqMdl);

        boolean commitFlg = false;
        try {
            biz._saveDisplayMode(usrSid, mode);
            con.commit();
            commitFlg = true;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 検索条件を検索条件保持用に保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     */
    private void __setSearchSv(Mem010Form form) {
        form.setMem010SvSearchNaiyo(form.getMem010SearchNaiyo());
        form.setMem010SvSearchDateFr(form.getMem010SearchDateFr());
        form.setMem010SvSearchDateTo(form.getMem010SearchDateTo());
        form.setMem010SvSearchLabel(form.getMem010SearchLabel());
        form.setMem010SvSearchTenpu(form.getMem010SearchTenpu());
        form.setMem010SvSort(form.getMem010Sort());
    }

    /**
     * <br>[機  能] 入力エラー時の再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param errors エラーメッセージ
     * @throws Exception 実行時例外
     */
    private void __doRedrawValidateError(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            List<String> errors) throws Exception {

        JSONObject jsonData = new JSONObject();
        Mem010ParamModel paramMdl = new Mem010ParamModel();
        paramMdl.setParam(form);
        jsonData = JSONObject.fromObject(form);

        //トランザクショントークン設定
        saveToken(req);
        String saved = (String) req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        jsonData.element("token", saved);

        //エラーメッセージ設定
        jsonData.element("success", false);
        jsonData.element("error", false);
        jsonData.element("size", errors.size());
        for (int i = 0; i < errors.size(); i++) {
            jsonData.element("errorMsg_" + i, errors.get(i));
        }
        paramMdl.setFormData(form);

        //レスポンスの出力
        __writeResp(res, jsonData);
    }

    /**
     * <br>[機  能] 2重投稿エラー時の再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req  リクエスト
     * @param res  レスポンス
     * @throws Exception 実行時例外
     */
    private void __doRedrawTokenError(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        String msg = null;
        GsMessage gsMsg = new GsMessage(req);
        msg = gsMsg.getMessage("memo.mem010.26");

        //トランザクショントークン設定
        saveToken(req);
        String saved = (String) req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        jsonData.element("token", saved);

        //エラーメッセージ設定
        jsonData.element("success", false);
        jsonData.element("size", 1);
        jsonData.element("errorMsg_0", msg);

        //レスポンスの出力
        __writeResp(res, jsonData);
    }

    /**
     * <br>[機  能] レスポンスデータを作成し、レスポンスを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param mode 作成モード
     * @throws Exception 実行時例外
     */
    private void __doRedrawData(
            Mem010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            int mode) throws Exception {

        JSONObject jsonData = new JSONObject();
        Mem010ParamModel paramMdl = new Mem010ParamModel();
        paramMdl.setParam(form);

        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);

        switch (mode) {
        //テンポラリディレクトリ削除時
        case Mem010Form.MODE_CLEAR_TEMP:
            break;
            //メモ検索，スクロール読み込み時
        case Mem010Form.MODE_SEARCH:
            __doSearch(jsonData, paramMdl);
            break;
            //ラベル登録時
        case Mem010Form.MODE_ADD_LABEL:
            __doAddLabel(jsonData, paramMdl, req);
            break;
            //メモ登録時
        case Mem010Form.MODE_INSERT_MEMO:
            __doInsert(jsonData, paramMdl, req);
            break;
            //メモ明細表示時
        case Mem010Form.MODE_SHOW_DETAIL:
            __doDetail(jsonData, paramMdl);
            break;
            //メモ削除時(削除対象に変更中のメモが入っている場合)
        case Mem010Form.MODE_DELETE_HENKOU:
            __doDeleteYokei(jsonData);
            __doDelete(jsonData, paramMdl, req);
            __doSearch(jsonData, paramMdl);
            break;
            //メモ削除時
        case Mem010Form.MODE_DELETE:
            __doDelete(jsonData, paramMdl, req);
            __doSearch(jsonData, paramMdl);
            break;
            //メモ変更時
        case Mem010Form.MODE_UPDATE:
            __doInsert(jsonData, paramMdl, req);
            break;
            //添付ファイル削除時
        case Mem010Form.MODE_DELETE_FILE:
            __doDeleteFile(jsonData, paramMdl, req);
            break;
        default:
        }
        paramMdl.setFormData(form);

        //レスポンスの出力
        __writeResp(res, jsonData);
    }

    /**
     * <br>[機  能] 検索時のjsonデータの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Mem010ParamModel
     * @throws Exception 実行時例外
     */
    private void __doSearch(JSONObject jsonData, Mem010ParamModel paramMdl) throws Exception {

        List<Mem010DisplayModel> memList = paramMdl.getMemList();
        jsonData.element("size", String.valueOf(memList.size()));

        for (int nIdx = 0; nIdx < memList.size(); nIdx++) {
            jsonData.element("memSid_" + nIdx, String.valueOf(memList.get(nIdx).getMemSid()));
            jsonData.element(
                    "mmdContent_" + nIdx, 
                    StringUtilHtml.transToHTmlPlusAmparsant(memList.get(nIdx).getMmdContent()));
            jsonData.element("mmdEdate_" + nIdx, memList.get(nIdx).getMmdEdate());
            jsonData.element("mmlName_" + nIdx,
                    StringUtilHtml.transToHTmlPlusAmparsant(memList.get(nIdx).getMmlName()));
            jsonData.element("memBin_" + nIdx, String.valueOf(memList.get(nIdx).getMemBin()));
        }
        jsonData.element("svSearchNaiyo", 
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getMem010SvSearchNaiyo()));
        jsonData.element("svSearchDateFr", paramMdl.getMem010SvSearchDateFr());
        jsonData.element("svSearchDateTo", paramMdl.getMem010SvSearchDateTo());
        jsonData.element("svSearchLabel", paramMdl.getMem010SvSearchLabel());
        jsonData.element("svSearchTenpu", paramMdl.getMem010SvSearchTenpu());
        jsonData.element("svSort", paramMdl.getMem010SvSort());
    }

    /**
     * <br>[機  能] ラベル追加時のjsonデータの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Mem010ParamModel
     * @param req リクエスト
     * @throws Exception 実行時例外
     */
    private void __doAddLabel(
            JSONObject jsonData,
            Mem010ParamModel paramMdl,
            HttpServletRequest req) throws Exception {

        //トランザクショントークン設定
        this.saveToken(req);
        MemoLabelModel model = paramMdl.getAddLabelModel();
        String saved = (String) req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        jsonData.element("token", saved);
        jsonData.element("mmlName", StringUtilHtml.transToHTmlPlusAmparsant(model.getMmlName()));
        jsonData.element("mmlSid", model.getMmlSid());
    }

    /**
     * <br>[機  能] メモ登録時のjsonデータの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Mem010ParamModel
     * @param req リクエスト
     * @throws Exception 実行時例外
     */
    private void __doInsert(
            JSONObject jsonData,
            Mem010ParamModel paramMdl,
            HttpServletRequest req) throws Exception {

        List<Mem010DisplayModel> disList = paramMdl.getMemList();
        //トランザクショントークン設定
        saveToken(req);
        String saved = (String) req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        jsonData.element("token", saved);
        if (disList != null && !disList.isEmpty()) {
            Mem010DisplayModel disMdl = disList.get(0);
            jsonData.element("size", disList.size());
            jsonData.element("memSid_0", String.valueOf(disMdl.getMemSid()));
            jsonData.element("mmdContent_0", 
                    StringUtilHtml.transToHTmlPlusAmparsant(disMdl.getMmdContent()));
            jsonData.element("mmdEdate_0", disMdl.getMmdEdate());
            jsonData.element("mmlName_0",
                    StringUtilHtml.transToHTmlPlusAmparsant(disMdl.getMmlName()));
            jsonData.element("memBin_0", String.valueOf(disMdl.getMemBin()));
        }
    }

    /**
     * <br>[機  能] 明細表示時のjsonデータの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Mem010ParamModel
     * @throws Exception 実行時例外
     */
    private void __doDetail(
            JSONObject jsonData,
            Mem010ParamModel paramMdl) throws Exception {

        //メモ情報の設定
        List<Mem010DisplayModel> dspList = paramMdl.getMemList();
        if (dspList != null && !dspList.isEmpty()) {
            Mem010DisplayModel dspMdl = dspList.get(0);
            jsonData.element("memSid", String.valueOf(dspMdl.getMemSid()));
            jsonData.element("mmdContent", dspMdl.getMmdContent());
            jsonData.element("atdelDate", dspMdl.getAdtelDate());
        }

        //ラベル情報の設定
        List<MemoLabelModel> mmlList = paramMdl.getLabelList();
        if (mmlList != null && !mmlList.isEmpty()) {
            jsonData.element("mmlSize", mmlList.size());
            for (int nIdx = 0; nIdx < mmlList.size(); nIdx++) {
                jsonData.element("mmlSid_" + nIdx, mmlList.get(nIdx).getMmlSid());
                jsonData.element("mmlName_" + nIdx, 
                        StringUtilHtml.transToHTmlPlusAmparsant(mmlList.get(nIdx).getMmlName()));
            }
        }

        //ファイル情報の設定
        List<LabelValueBean> fileList = paramMdl.getFileList();
        if (fileList != null && !fileList.isEmpty()) {
            jsonData.element("fileSize", fileList.size());
            for (int nIdx = 0; nIdx < fileList.size(); nIdx++) {
                jsonData.element("value_" + nIdx, fileList.get(nIdx).getValue());
                jsonData.element("fileName_" + nIdx, 
                        StringUtilHtml.transToHTmlPlusAmparsant(fileList.get(nIdx).getLabel()));
            }
        }
    }

    /**
     * <br>[機  能] メモ削除対象に変更中のメモがあった場合の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     */
    private void __doDeleteYokei(JSONObject jsonData) {
        //メモ情報削除を設定
        jsonData.element("delete", true);
    }

    /**
     * <br>[機  能] メモ削除時のjsonデータの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Mem010ParamModel
     * @param req リクエスト
     * @throws Exception 実行時例外
     */
    private void __doDelete(
            JSONObject jsonData,
            Mem010ParamModel paramMdl,
            HttpServletRequest req) throws Exception {

        //削除されたメモ件数を設定
        long[] sidArray = paramMdl.getMem010DeleteMemoSidArray();
        jsonData.element("delSize", sidArray.length);

        //トランザクショントークン設定
        saveToken(req);
        String saved = (String) req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        jsonData.element("token", saved);
    }

    /**
     * <br>[機  能] ファイル削除時のjsonデータの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Mem010ParamModel
     * @param req リクエスト
     * @throws Exception 実行時例外
     */
    private void __doDeleteFile(
            JSONObject jsonData,
            Mem010ParamModel paramMdl,
            HttpServletRequest req) throws Exception {

        //トランザクショントークン設定
        saveToken(req);
        String saved = (String) req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        jsonData.element("token", saved);

        //削除されたファイル情報を設定
        String[] selectFile = paramMdl.getMem010selectFile();
        if (selectFile != null) {
            jsonData.element("size", selectFile.length);
            for (int i = 0; i < selectFile.length; i++) {
                jsonData.element("value_" + i, selectFile[i]);
            }
        }
    }

    /**
     *
     * <br>[機  能] jsonレスポンスの書き込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param json jsonオブジェクト
     */
    private void __writeResp(HttpServletResponse res, JSONObject json) {
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}

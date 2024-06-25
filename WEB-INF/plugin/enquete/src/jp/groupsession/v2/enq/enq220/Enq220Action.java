package jp.groupsession.v2.enq.enq220;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq210.Enq210Action;
import jp.groupsession.v2.enq.enq210.Enq210Biz;
import jp.groupsession.v2.enq.enq210.Enq210Form;
import jp.groupsession.v2.enq.enq210.Enq210ParamModel;

/**
 * <br>[機  能] アンケート 設問詳細登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq220Action extends Enq210Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq220Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "enq210";

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "").trim();
        return cmd.equals("enq220getImageFile") || cmd.equals("enq220download");
    }

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

        log__.debug("START_Enq220");

        ActionForward forward = null;
        Enq220Form thisForm = (Enq220Form) form;

        con.setAutoCommit(true);
        try {
            //アンケート作成可能者以外はアクセス不可
            if (!_checkEnqCrtUser(con, req)) {
                return getSubmitErrorPage(map, req);
            }

            if (thisForm.getEnqEditMode() == GSConstEnquete.EDITMODE_EDIT) {
                EnqCommonBiz enqBiz = new EnqCommonBiz();
                long emnSid = thisForm.getEditEnqSid();

                //ユーザが編集対象アンケートを編集可能かを判定
                if (thisForm.getEnq210editMode() != Enq210Form.EDITMODE_TEMPLATE) {
                    if (!enqBiz.canEditEnquete(getRequestModel(req), con, emnSid)) {
                        return getSubmitErrorPage(map, req);
                    }
                } else {
                    if (!enqBiz.checkExistEnquete(
                            getRequestModel(req), con, emnSid, GSConstEnquete.DATA_KBN_TEMPLATE)) {
                        return getSubmitErrorPage(map, req);
                    }
                }

                //編集対象アンケートが存在するかを確認
                Enq210Biz biz = new Enq210Biz();
                Enq210ParamModel paramMdl = new Enq210ParamModel();
                paramMdl.setParam(thisForm);
                if (!biz.canEditEnquete(con, paramMdl)) {
                    return __doNoneDataError(map, req, thisForm, OPERATION_EDIT_);
                }
            }
        } finally {
            con.setAutoCommit(false);
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("enq220back")) {
            //戻る
            //テンポラリディレクトリを削除
            Enq220Biz biz = new Enq220Biz();
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(biz.getQueEditTempPath(getRequestModel(req)));
            log__.debug("戻る");
            forward = map.findForward("enq220back");

        } else if (cmd.equals("enq220ok")) {
            //OK
            log__.debug("OK");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("enq220addRow")) {
            //選択肢追加
            forward = __doAddRow(map, thisForm, req, res, con);

        } else if (cmd.equals("enq220delRow")) {
            //選択肢削除
            forward = __doDelRow(map, thisForm, req, res, con);

        } else if (cmd.equals("enq220upRow")) {
            //選択肢順序 上へ
            forward = __doSortChoice(map, thisForm, req, res, con, Enq220Biz.SORTTYPE_UP_);

        } else if (cmd.equals("enq220downRow")) {
            //選択肢順序 下へ
            forward = __doSortChoice(map, thisForm, req, res, con, Enq220Biz.SORTTYPE_DOWN_);

        } else if (cmd.equals("enq220download")) {
            //添付ファイルダウンロード
            forward = __doDownload(map, thisForm, req, res, con, Enq220Biz.SORTTYPE_DOWN_);

        } else if (cmd.equals("enq220getImageFile")) {
            //画像表示
            forward = __doGetImageFile(map, thisForm, req, res, con, Enq220Biz.SORTTYPE_DOWN_);

        } else {
            //初期表示処理
            log__.debug("初期表示処理");

            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Enq220");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 設問情報の読み込みに失敗
     * @throws InvocationTargetException 設問 選択肢の読み込みに失敗
     * @throws IllegalAccessException 設問 選択肢の読み込みに失敗
     * @throws IOException 設問情報の読み込みに失敗
     * @throws NoSuchMethodException 日時設定時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq220Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, IOToolsException,
        InvocationTargetException, IllegalAccessException, IOException, NoSuchMethodException {

        int seq = NullDefault.getInt(req.getParameter("enq210Seq"), -1);
        form.setEnq220Seq(seq);

        con.setAutoCommit(true);
        try {
            // 初期表示情報を取得
            Enq220Biz biz = new Enq220Biz();
            Enq220ParamModel paramModel = new Enq220ParamModel();
            paramModel.setParam(form);
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            biz.setInitData(paramModel, getRequestModel(req), con,
                    temp.getTempPath(getRequestModel(req),
                            GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID));
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 選択肢追加処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 設問情報の読み込みに失敗
     * @throws InvocationTargetException 設問 選択肢の読み込みに失敗
     * @throws IllegalAccessException 設問 選択肢の読み込みに失敗
     * @throws IOException 設問情報の読み込みに失敗
     * @throws NoSuchMethodException 日時設定時例外
     */
    private ActionForward __doAddRow(ActionMapping map,
                                   Enq220Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, IOToolsException,
        InvocationTargetException, IllegalAccessException, IOException, NoSuchMethodException {

        Enq220SubForm subForm = new Enq220SubForm();
        List<Enq220SubForm> subList = form.getSubListToList();
        subForm.setEnqIndex(subList.size());
        subList.add(subForm);
        form.setSubListForm(subList);

        form.setEnq220scrollQuestonFlg(1);
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 選択肢削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 設問情報の読み込みに失敗
     * @throws InvocationTargetException 設問 選択肢の読み込みに失敗
     * @throws IllegalAccessException 設問 選択肢の読み込みに失敗
     * @throws IOException 設問情報の読み込みに失敗
     * @throws NoSuchMethodException 日時設定時例外
     */
    private ActionForward __doDelRow(ActionMapping map,
                                   Enq220Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, IOToolsException,
        InvocationTargetException, IllegalAccessException, IOException, NoSuchMethodException {

        Enq220ParamModel paramModel = new Enq220ParamModel();
        paramModel.setParam(form);
        Enq220Biz biz = new Enq220Biz();
        biz.deleteChoice(paramModel);
        paramModel.setFormData(form);

        form.setEnq220scrollQuestonFlg(1);
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 選択肢順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param sortType 上へ or 下へ
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doSortChoice(ActionMapping map,
                                   Enq220Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con,
                                   int sortType) throws SQLException, Exception {

        Enq220Biz biz = new Enq220Biz();
        Enq220ParamModel paramModel = new Enq220ParamModel();
        paramModel.setParam(form);
        biz.sortChoice(paramModel, sortType);
        paramModel.setFormData(form);

        form.setEnq220scrollQuestonFlg(1);
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 画像表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param sortType 上へ or 下へ
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                   Enq220Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con,
                                   int sortType) throws SQLException, Exception {

        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        Enq210Biz biz210 = new Enq210Biz();
        String queTempDir = biz210.getEnqTempDir(getRequestModel(req));
        CommonBiz cmnBiz = new CommonBiz();
        List<TempFileModel> fileList = cmnBiz.getTempFiles(queTempDir);
        if (fileList != null && !fileList.isEmpty()) {
            TempFileUtil.downloadInline(req, res,
                    fileList.get(0).getFile(),
                    fileList.get(0).getFileName(),
                    Encoding.UTF_8);
        }

        return null;
    }

    /**
     * <br>[機  能] 添付ファイルダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param sortType 上へ or 下へ
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDownload(ActionMapping map,
                                   Enq220Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con,
                                   int sortType) throws SQLException, Exception {

        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        Enq210Biz biz210 = new Enq210Biz();
        String queTempDir = biz210.getEnqTempDir(getRequestModel(req));
        CommonBiz cmnBiz = new CommonBiz();
        List<TempFileModel> fileList = cmnBiz.getTempFiles(queTempDir);
        if (fileList != null && !fileList.isEmpty()) {
            TempFileUtil.downloadAtachment(req, res,
                    fileList.get(0).getFile(),
                    fileList.get(0).getFileName(),
                    Encoding.UTF_8);
        }

        return null;
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws IOToolsException 設問情報の設定に失敗
     * @throws InvocationTargetException 設問 選択肢の読み込みに失敗
     * @throws IllegalAccessException 設問 選択肢の読み込みに失敗
     * @throws SQLException SQL実行例外
     * @throws IOException 設問情報の読み込みに失敗
     * @throws NoSuchMethodException 日時設定時例外
     */
    private ActionForward __doOk(ActionMapping map,
                                 Enq220Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con)
        throws IOToolsException, InvocationTargetException,
            IllegalAccessException, SQLException, IOException, NoSuchMethodException {

        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        //入力チェック
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        ActionErrors errors = form.validateCheck(
                getRequestModel(req), temp.getTempPath(reqMdl,
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Enq220Biz biz = new Enq220Biz();
        Enq220ParamModel paramModel = new Enq220ParamModel();
        paramModel.setParam(form);
        biz.setQuestionData(paramModel, reqMdl);
        paramModel.setFormData(form);

        temp.deleteTempPath(biz.getQueEditTempPath(reqMdl));

        forward = map.findForward("enq220ok");
        return forward;
    }
}

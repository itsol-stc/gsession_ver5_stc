package jp.groupsession.v2.prj.prj040;

import java.lang.reflect.InvocationTargetException;
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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj010.Prj010Biz;
import jp.groupsession.v2.prj.prj240.Prj240Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト詳細検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj040Action extends AbstractProjectAction {

    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = "backClick040";

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj040";

    /** CMD:プロジェクト登録ボタンクリック */
    public static final String CMD_PRJ_ADD_CLICK = "prjAddClick";
    /** CMD:プロジェクト名称クリック */
    public static final String CMD_PRJ_NAME_CLICK = "prjNameClick";
    /** CMD:選択ボタンクリック(メンバー) */
    public static final String CMD_SCT_MEM = "prjSelectMember";
    /** CMD:検索ボタンクリック */
    public static final String CMD_SEARCH = "searchClick";
    /** CMD:エクスポートボタンクリック */
    public static final String CMD_EXPORT = "export";
    /** CMD:前ページ */
    public static final String CMD_PAGE_PREVEW = "prev";
    /** CMD:次ページ */
    public static final String CMD_PAGE_NEXT = "next";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj040Action.class);

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (CMD_EXPORT.equals(cmd)) {
            log__.debug("エクスポート");
            return true;

        }
        return false;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("start");
        ActionForward forward = null;

        Prj040Form thisForm = (Prj040Form) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_PRJ_ADD_CLICK.equals(cmd)) {
            log__.debug("プロジェクト登録ボタンクリック");
            forward = map.findForward(CMD_PRJ_ADD_CLICK);

        } else if (CMD_PRJ_NAME_CLICK.equals(cmd)) {
            log__.debug("プロジェクト編集ボタンクリック");
            forward = map.findForward(CMD_PRJ_NAME_CLICK);

        } else if (CMD_SCT_MEM.equals(cmd)) {
            log__.debug("選択ボタンクリック(メンバー)");
            forward = __doUserSelect(map, thisForm, req, res, con);

        } else if (CMD_SEARCH.equals(cmd)) {
            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (CMD_EXPORT.equals(cmd)) {
            log__.debug("エクスポートボタンクリック");
            forward = __doExport(map, thisForm, req, res, con);

        } else if (CMD_PAGE_PREVEW.equals(cmd)) {
            log__.debug("前ページ");
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (CMD_PAGE_NEXT.equals(cmd)) {
            log__.debug("次ページ");
            forward = __doNext(map, thisForm, req, res, con);

        } else if (CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward(CMD_BACK_CLICK);

        } else if (cmd.equals("getImageFile")) {
            log__.debug("画像ダウンロード");
            forward = __doGetImageFile(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("end");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doInit(ActionMapping map, Prj040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);
        //初期表示情報を画面にセットする
        Prj040Biz biz = new Prj040Biz(con, getRequestModel(req));
        Prj040ParamModel paramMdl = new Prj040ParamModel();
        paramMdl.setParam(form);
        ActionErrors errors = biz.setInitData(paramMdl, getSessionUserModel(req));
        paramMdl.setFormData(form);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setPrj040searchFlg(GSConstProject.SEARCH_FLG_NO);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 選択(メンバー)クリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doUserSelect(
        ActionMapping map,
        Prj040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) {

        Prj240Form prj240Form = new Prj240Form();
        GsMessage gsMsg = new GsMessage();
        //所属メンバー
        String textSquad = gsMsg.getMessage(req, "cmn.squad");
        //「戻る」ボタンURLセット
        ActionForward forward = map.findForward("redraw");
        prj240Form.setPrj240BackUrl(forward.getPath());
        //機能名称セット
        prj240Form.setPrj240FunctionName(textSquad);
        //フォーム識別子
        prj240Form.setPrj240FormKey("prj040Form");
        //パラメータ名
        prj240Form.setPrj240paramName("prj040scMemberSid");
        //メンバーSID
        prj240Form.setPrj240userSid(form.getPrj040scMemberSid());

        req.setAttribute("prj240Form", prj240Form);
        return map.findForward("selectuser");
    }

    /**
     * <br>[機  能] 検索ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doSearch(
            ActionMapping map, Prj040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //入力チェック
        ActionErrors errors = form.validate040(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setPrj040searchFlg(GSConstProject.SEARCH_FLG_NO);
            return __doInit(map, form, req, res, con);
        }

        form.setPrj040page1(1);

        //検索条件パラメータをセーブフィールドへ移行
        form.saveSearchParm040();

        //検索フラグON
        form.setPrj040searchFlg(GSConstProject.SEARCH_FLG_OK);

        //画面を再表示
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] エクスポート処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(
        ActionMapping map,
        Prj040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
        String fileName = Prj040CsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        Prj040Biz biz = new Prj040Biz(con, getRequestModel(req));
        Prj040ParamModel paramMdl = new Prj040ParamModel();
        paramMdl.setParam(form);
        biz.export(paramMdl, getSessionUserModel(req), tempDir);
        paramMdl.setFormData(form);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        GsMessage gsMsg = new GsMessage(req);

        //ログ出力処理
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        prjBiz.outPutLog(
                map, req, res,
                getInterMessage(req, "cmn.export"), GSConstLog.LEVEL_INFO, fileName);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        return null;
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doPrev(ActionMapping map, Prj040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //ページ設定
        int page = form.getPrj040page1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setPrj040page1(page);
        form.setPrj040page2(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doNext(ActionMapping map, Prj040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //ページ設定
        int page = form.getPrj040page1();
        page += 1;
        form.setPrj040page1(page);
        form.setPrj040page2(page);

        return __doInit(map, form, req, res, con);
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
                                            Prj040Form form,
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

package jp.groupsession.v2.fil.main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil010.Fil010Action;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル管理メイン画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilMainAction extends AbstractFileAction {

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

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

        if (cmd.equals("fileKanriFileDownload")) {
            log__.debug("添付ファイルダウンロード");
            return true;
        }
        return false;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil010Action.class);

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        log__.debug("filMainAction START");

        ActionForward forward = null;
        FilMainForm thisForm = (FilMainForm) form;
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        int sessionUsrSid = getRequestModel(req).getSmodel().getUsrsid();
        //adminユーザの簡易登録を制御
        if (cmd.equals("fileKanriFileDownload") && sessionUsrSid == 0) {
            return getSubmitErrorPage(map, req);
        }

        if (cmd.equals("filMainFolderDetail")) {
            //フォルダ詳細へ遷移
            forward = map.findForward("fil050");

        } else if (cmd.equals("filMainFileDetail")) {
            //ファイル詳細へ遷移
            forward = map.findForward("fil070");

        } else if (cmd.equals("filMainFolderList")) {
            //ファイル詳細へ遷移
            forward = map.findForward("fil040");

        } else if (cmd.equals("filMainCallList")) {
            //ファイル詳細へ遷移
            forward = map.findForward("fil240");

        } else if (cmd.equals("filMainPconf")) {
            //ファイル個人設定へ遷移
            forward = map.findForward("fil120");

        } else if (cmd.equals("fileKanriFileDownload")) {
            //添付ファイル名クリック
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD);

        } else if (cmd.equals("fileDownloadInline")) {
            //プレビュー用
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD_INLINE);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    FilMainForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        FilMainBiz biz = new FilMainBiz(getRequestModel(req));

        FilMainParamModel paramMdl = new FilMainParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        form.setFilTopUrl(getPluginConfig(req).getPlugin(
                GSConstFile.PLUGIN_ID_FILE).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param downloadKbn ダウンロード区分
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(ActionMapping map,
                                        FilMainForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con,
                                        int downloadKbn) throws SQLException, Exception {

        Long binSid = NullDefault.getLong(form.getFileSid(), -1);
        CommonBiz cmnBiz = new CommonBiz();
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));

        //権限チェック
        if (!filBiz.isDownloadAuthUser(binSid)) {
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("cmn.download"));
        }

        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));

        if (cbMdl == null) {
            return __doInit(map, form, req, res, con);
        }

        String textDownload = gsMsg.getMessage("cmn.download");

        //ログ出力処理
        String dspName = gsMsg.getMessage("cmn.main");
        filBiz.outPutLogNoDspName(textDownload,
                GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), map.getType(), null, dspName);

        //集計用データを登録する
        filBiz.regFileDownloadLogCnt(getSessionUserSid(req), binSid, new UDate());

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        if (downloadKbn == GSConstCommon.FILE_DOWNLOAD_INLINE) {
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        } else {
            TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        }

        cbMdl.removeTempFile();
        return null;
    }
}

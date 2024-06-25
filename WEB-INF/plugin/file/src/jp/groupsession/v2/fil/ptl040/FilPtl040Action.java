package jp.groupsession.v2.fil.ptl040;

import java.io.IOException;
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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *<p>ポータル ファイル一覧画面Action
 *
 * @author JTS
 */
public class FilPtl040Action extends AbstractFileAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl040Action.class);

    /** 画面データ */
    private FilPtl040Form filptl040Form__;

    /**
     * <p>filptl040Form を取得します。
     * @return filptl040Form
     */
    public FilPtl040Form getFilptl040Form() {
        return filptl040Form__;
    }
    /**
     * <p>filptl040Form をセットします。
     * @param filptl040Form filptl040Form
     */
    public void setFilptl040Form(FilPtl040Form filptl040Form) {
        filptl040Form__ = filptl040Form;
    }

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

        if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            return true;

        }
        return false;
    }


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

        log__.debug("FilPtl040Action START");

        ActionForward forward = null;
        FilPtl040Form thisForm = (FilPtl040Form) form;
        RequestModel reqMdl = getRequestModel(req);

        //論理削除済みのキャビネットの場合はエラーを表示
        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        String cabSid = thisForm.getFil010SelectCabinet();
        if (!StringUtil.isNullZeroString(cabSid)
                && cmnBiz.isExistCabinet(Integer.parseInt(cabSid))) {
            return getSubmitErrorPage(map, req);
        }

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        //adminユーザの簡易登録を制御
        if (cmd.equals("fileDownload") && sessionUsrSid == 0) {
            return getSubmitErrorPage(map, req);
        }

        if (cmd.equals("filptlSelectFolder")) {
            //ファイル管理 フォルダ一覧に遷移
            forward = map.findForward("fil040");

        } else if (cmd.equals("fileDownload")) {
            //ファイルダウンロード
            log__.debug("ファイルダウンロード");

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
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     */
    private ActionForward __doInit(ActionMapping map,
                                    FilPtl040Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOToolsException, IOException  {

        con.setAutoCommit(true);

        FilPtl040Biz biz = new FilPtl040Biz(getRequestModel(req));
        FilPtl040ParamModel paramMdl = new FilPtl040ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

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
                                        FilPtl040Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con,
                                        int downloadKbn) throws SQLException, Exception {

        //バイナリSID
        Long binSid = NullDefault.getLong(form.getFileSid(), -1);

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textDownload = gsMsg.getMessage("cmn.download");
        //権限チェック
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        if (!filBiz.isDownloadAuthUser(binSid)) {
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    textDownload);
        }

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));
        if (cbMdl == null) {
            return __doInit(map, form, req, res, con);
        }

        //ログ出力処理
        filBiz.outPutLog(
                textDownload, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), map.getType(),
                String.valueOf(binSid));

        //集計用データを登録する
        filBiz.regFileDownloadLogCnt(getSessionUserSid(req), binSid, new UDate());

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        cbMdl.setBinFilekbn(GSConst.FILEKBN_FILE);
        if (downloadKbn == GSConstCommon.FILE_DOWNLOAD_INLINE) {
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        } else {
            TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        }

        cbMdl.removeTempFile();
        return null;
    }


}
package jp.groupsession.v2.fil.fil240;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 更新通知一覧画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil240Action extends AbstractFileAction {

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

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil240Action.class);

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

        //個人設定が無い場合作成する
        BaseUserModel buMdl = getSessionUserModel(req);
        FilCommonBiz biz = new FilCommonBiz(getRequestModel(req), con);
        biz.getFileUconfModel(buMdl.getUsrsid());
        //管理者設定が無い場合は作成する
        biz.getFileAconfModel();

        log__.debug("fil240Action START");

        ActionForward forward = null;
        Fil240Form thisForm = (Fil240Form) form;
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil240fileDetail")) {
            //ファイル詳細へ遷移
            forward = map.findForward("fil070");

        } else if (cmd.equals("fileDownload")) {
            //添付ファイル名クリック
            forward = __doDownLoad(map, thisForm, req, res, con);

        } else if (cmd.equals("changeMode")) {
            //ページコンボ変更
            forward = __doChangeMode(map, thisForm, req, res, con);

        } else if (cmd.equals("arrorw_left")) {
            //左矢印押下
            forward = __doPageMinus(map, thisForm, req, res, con);

        } else if (cmd.equals("arrorw_right")) {
            //右矢印押下
            forward = __doPagePlus(map, thisForm, req, res, con);

        } else if (cmd.equals("fil240back")) {
            //戻る
            forward = __doBack(map, thisForm, req, res, con);

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
                                    Fil240Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        Fil240Biz biz = new Fil240Biz(con, getRequestModel(req));

        Fil240ParamModel paramMdl = new Fil240ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

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
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(ActionMapping map,
                                        Fil240Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws SQLException, Exception {

        Long binSid = NullDefault.getLong(form.getFileSid(), -1);
        CommonBiz cmnBiz = new CommonBiz();

        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));

        if (cbMdl == null) {
            return __doInit(map, form, req, res, con);
        }
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        //ファイルがダウンロード可能かチェックする
        if (!filBiz.isDownloadAuthUser(binSid)) {
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("cmn.download"));
        }

        String textDownload = gsMsg.getMessage("cmn.download");

        //ログ出力処理
        String dspName = gsMsg.getMessage("fil.fil240.1");
        filBiz.outPutLogNoDspName(
                textDownload, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), map.getType(),
                String.valueOf(binSid), dspName);

        //集計用データを登録する
        filBiz.regFileDownloadLogCnt(getSessionUserSid(req), binSid, new UDate());

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        cbMdl.setBinFilekbn(GSConst.FILEKBN_FILE);
        TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        cbMdl.removeTempFile();

        return null;
    }

    /**
     * <br>[機  能] ページコンボ変更処理
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
    private ActionForward __doChangeMode(ActionMapping map,
                                          Fil240Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws SQLException {
        __doInit(map, form, req, res, con);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 左矢印押下処理
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
    private ActionForward __doPageMinus(ActionMapping map,
            Fil240Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws SQLException {

        //ページ数取得
        int page = form.getFil240PageNum();
        page -= 1;
        if (page < 1) {
            page = 1;
        }

        //調整後ページ数セット
        form.setFil240PageNum(page);
        __doInit(map, form, req, res, con);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 右矢印押下処理
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
    private ActionForward __doPagePlus(ActionMapping map,
            Fil240Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        //ページ数取得
        int page = form.getFil240PageNum();
        page += 1;

        //調整後ページ数セット
        form.setFil240PageNum(page);
        __doInit(map, form, req, res, con);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻る処理
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
    private ActionForward __doBack(ActionMapping map,
                                          Fil240Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws SQLException {
        ActionForward forward = null;

        if (form.getBackDspCall().equals(GSConstFile.MOVE_TO_MAIN)) {
            forward = map.findForward("main");
        } else {
            forward = map.findForward("cabinetMain");
        }

        return forward;
    }

}

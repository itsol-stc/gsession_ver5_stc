package jp.groupsession.v2.fil.fil330;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.fil300.Fil300Biz;
import jp.groupsession.v2.fil.fil300.FilErrlAdddataException;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル管理 外貨登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil330Action extends AbstractFileAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil330Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Fil330Form thisForm = (Fil330Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        try {
            if (cmd.equals("fil330del")) {
                //削除ボタンクリック
                forward = __doDeleteCheck(map, thisForm, req, res, con);

            } else if (cmd.equals("fil330back")) {
                //戻るボタンクリック
                forward = __doBack(map, thisForm, req, res, con);

            } else if (cmd.equals("fil330folderSel")) {
                //フォルダ選択
                forward = __doRedrowList(map, thisForm, req, res, con);
            } else if (cmd.equals("fil330to300")) {
                //取引情報登録画面への遷移
                thisForm.setFil300SelectCabinet(thisForm.getFil330SelectCabinet());
                thisForm.setFil300SelectDir(NullDefault.getInt(thisForm.getFil330SelectDsp(), -1));
                thisForm.setFil300InitFlg(GSConstFile.INIT_FLG_OK);
                req.setAttribute("fil300Form", thisForm);

                forward = map.findForward("fil300");
            } else if (cmd.equals("fil330knComp")) {
                //削除確認からの戻り時
                forward = __backDelComp(map, thisForm, req, res, con);

            } else if (cmd.equals("fil330downloadImage")) {

                forward = __doDownLoadImage(map, thisForm, req, res, con);
            } else {
                //初期表示
                forward = __doInit(map, thisForm, req, res, con);
            }
        } catch (FilErrlAdddataException e) {
            //一括削除画面実行時例外ハンドリング
            forward = __doException(map, thisForm, req, res, con, e);
        }

        return forward;
    }
    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException
     * @throws Exception 実行時例外
     */
    private ActionForward __doRedrowList(ActionMapping map, Fil330Form thisForm,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            ) throws SQLException, FilErrlAdddataException {
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        con.setAutoCommit(true);

        Fil330Biz biz = new Fil330Biz(reqMdl, con);
        Fil330ParamModel paramMdl = new Fil330ParamModel();


        paramMdl.setParam(thisForm);

        paramMdl.setFil330SelectDel(null);
        try {
            biz.setInitDsp(paramMdl);
        } catch (FilErrlAdddataException e) {
            if (e.getMessage() == FilErrlAdddataException.INFO_NOFILE_SELDIR) {
                ActionErrors errors = new ActionErrors();
                StrutsUtil.addMessage(errors,
                        new ActionMessage(
                                "error.edit.power.notfound",
                                gsMsg.getMessage("cmn.folder"),
                                gsMsg.getMessage("cmn.show")
                                ),
                        "error.edit.power.notfound");
                addErrors(req, errors);

            }
            throw e;
        }
        paramMdl.setFormData(thisForm);


        return map.findForward("fil330_dellist");
    }

    /**
     * <br>[機  能] 戻るボタン動作処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Fil330Form thisForm,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = map.findForward("fil040");
        if (thisForm.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL010) {
            if (thisForm.getBackDsp().equals(GSConstFile.MOVE_TO_MAIN)) {
                forward = map.findForward("main");
            } else if (thisForm.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
                forward = map.findForward("fil040");
            } else {
                forward = map.findForward("cabinetMain");
            }
        }

        return forward;
    }

    /**
     * <br>[機  能] 画面独自例外処理をハンドリングする
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param e ハンドリングする例外
     * @return ActionForward
     * @throws SQLException 実行時例外
     */
    private ActionForward __doException(ActionMapping map, Fil330Form thisForm,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            FilErrlAdddataException e) throws SQLException {
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //削除対象がない
        if (Objects.equals(e.getMessage(), FilErrlAdddataException.INFO_NOFILE_ALL)) {
            return getTargetNotfoundPage(
                    map,
                    thisForm,
                    req,
                    __doBack(map, thisForm, req, res, con),
                    gsMsg.getMessage("fil.187"),
                    gsMsg.getMessage("cmn.delete"));
        }

        //選択対象の再選択が必要
        if (Objects.equals(e.getMessage(), FilErrlAdddataException.INFO_NOFILE_SELDIR)) {
            thisForm.setFil330SelectDsp(null);
            thisForm.setFil330SelectCabinet(null);
            Fil330ParamModel paramMdl = new Fil330ParamModel();
            paramMdl.setParam(thisForm);

            //フォルダツリー設定
            Fil330Biz fil330Biz = new Fil330Biz(reqMdl, con);
            try {
                fil330Biz.setInitDsp(paramMdl);
            } catch (FilErrlAdddataException re) {
                if (Objects.equals(re.getMessage(), FilErrlAdddataException.INFO_NOFILE_SELDIR)
                        == false) {
                    return getSubmitErrorPage(map, req);
                }
            }

            paramMdl.setFormData(thisForm);

            return map.getInputForward();

        }



        return getSubmitErrorPage(map, req);
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws FilErrlAdddataException
     * @throws SqlException 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Fil330Form thisForm,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, FilErrlAdddataException {

        RequestModel reqMdl = getRequestModel(req);
        con.setAutoCommit(true);

        //フォルダツリー設定
        Fil300Biz fil300Biz = new Fil300Biz(reqMdl, con);
        if (!fil300Biz.isErrlFile(con)) {
            throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_ALL);
        }

        Fil330Biz biz = new Fil330Biz(reqMdl, con);
        Fil330ParamModel paramMdl = new Fil330ParamModel();


        paramMdl.setParam(thisForm);
        biz.setInitDsp(paramMdl);
        paramMdl.setFormData(thisForm);


        return map.getInputForward();
    }
    /**
     *
     * <br>[機  能] 削除完了からの戻り時の遷移処理を実行する
     * <br>[解  説] 他に削除可能な仮登録ファイルがない場合、遷移元画面へ遷移する
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException
     * @throws FilErrlAdddataException
     */
    private ActionForward __backDelComp(ActionMapping map, Fil330Form thisForm,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, FilErrlAdddataException {
        try {
            return __doInit(map, thisForm, req, res, con);
        } catch (FilErrlAdddataException e) {
            //削除可能な仮登録ファイルがない
            if (Objects.equals(e.getMessage(), FilErrlAdddataException.INFO_NOFILE_ALL)) {
                return __doBack(map, thisForm, req, res, con);
            }
            throw e;
        }
    }
    /**
     * <br>[機  能] 削除確認表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException 実行時例外
     */
    private ActionForward __doDeleteCheck(ActionMapping map,
            Fil330Form thisForm, HttpServletRequest req,
            HttpServletResponse res, Connection con) throws SQLException, FilErrlAdddataException {
        con.setAutoCommit(true);
        ActionErrors errors = thisForm.validateDelete(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, thisForm, req, res, con);
        }
        //削除ボタンクリック
        return map.findForward("fil330del");
    }

    /**
     * <br>[機  能] ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDownLoadImage(ActionMapping map, Fil330Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        //仮登録ファイルの権限チェック
        int feaSid = form.getFil300SelectFile();
        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con);
        FileErrlAdddataModel feaMdl = feaDao.select(feaSid);
        if (feaMdl == null
                || !filBiz.checkEditErrlData(feaSid)) {
            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("fil.187"),
                            gsMsg.getMessage("cmn.download")
                            ),
                    "error.edit.power.notfound");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        CommonBiz cmnBiz = new CommonBiz();
        long binSid = feaMdl.getBinSid();
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));
        if (cbMdl == null) {
            return __doInit(map, form, req, res, con);
        }

        //ログ出力処理
        String textDownload = gsMsg.getMessage("cmn.download");
        filBiz.outPutLog(
                textDownload, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), map.getType(),
                String.valueOf(binSid));

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


}
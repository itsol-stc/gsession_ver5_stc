package jp.groupsession.v2.rsv.rsv090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.AbstractReserveSubAction;
import jp.groupsession.v2.rsv.rsv090.model.Rsv090DspModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv090Action extends AbstractReserveSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv090Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "rsv090";
    /** 施設/設備画像 画像名 */
    public String sisetuFileName__ = "";
    /** 施設/設備画像 保存名 */
    public String sisetuFileSaveName__ = "";

    /** 場所/地図画像 画像名 */
    public String basyoImageFileName__ = "";
    /** 場所/地図画像 保存名 */
    public String basyoImageFileSaveName__ = "";


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

        if (cmd.equals("getImageFilePlace") || cmd.equals("getImageFileSisetu")) {
            return true;
        }

        return false;
    }

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
        Rsv090Form rsvform = (Rsv090Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        //OKボタン押下
        if (cmd.equals("sisetu_toroku_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doConfirmation(map, rsvform, req, res, con);
            //削除ボタン押下
        } else if (cmd.equals("sisetu_sakuzyo_kakunin")) {
            log__.debug("削除ボタン押下");
            forward = __doGrpDeleteCheck(map, rsvform, req, res, con);
            //削除確認画面でOKボタン押下
        } else if (cmd.equals("deleteOk")) {
            log__.debug("削除確認画面でOKボタン押下");
            forward = __doSisetuDeleteOk(map, rsvform, req, res, con);
            //戻るボタン押下
        } else if (cmd.equals("back_to_sisetu_zyoho_settei")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, rsvform, req, res, con);
            //施設画像登録画面
        } else if (cmd.equals("sisetu_img_toroku")) {
            log__.debug("施設画像登録画面遷移");
            __doTemp(map, rsvform, req, res, con, GSConstReserve.TEMP_IMG_KBN_SISETU);
            forward = __doInit(map, rsvform, req, res, con, cmd);
            //施設画像登録画面
        } else if (cmd.equals("place_img_toroku")) {
            log__.debug("場所画像登録画面遷移");
            __doTemp(map, rsvform, req, res, con, GSConstReserve.TEMP_IMG_KBN_PLACE);
            forward = __doInit(map, rsvform, req, res, con, cmd);
            //施設・設備画像ダウンロード
        } else if (cmd.equals("getImageFileSisetu")) {
            log__.debug("施設・設備画像ダウンロード");
            forward = __doGetImageFile(map, rsvform, req, res, con, 0);
            //場所・地図画像ダウンロード
        } else if (cmd.equals("getImageFilePlace")) {
            log__.debug("場所・地図画像ダウンロード");
            forward = __doGetImageFile(map, rsvform, req, res, con, 1);
            //施設グループコンボ変更
        } else if (cmd.equals("chGrpComb")) {
            log__.debug("施設グループコンボ変更");
            forward = __doChGrpComb(map, rsvform, req, res, con, cmd);
            //画像削除処理
        } else if (cmd.equals("place_img_delete")) {
            forward = __doDelete(map, rsvform, req, res, con);
            //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con, cmd);
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
     * @param cmd コマンドパラメータ
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            String cmd) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        try {
            con.setAutoCommit(true);
            Rsv090Biz biz = new Rsv090Biz(reqMdl, con);

            boolean processFlg = false;

            Rsv090ParamModel paramMdl = new Rsv090ParamModel();
            paramMdl.setParam(form);
            processFlg = biz.isPossibleToProcess(paramMdl);
            paramMdl.setFormData(form);

            //処理権限判定
            if (!processFlg) {
                //処理権限無し
                return getSubmitErrorPage(map, req);
            }

            //テンポラリディレクトリパスを取得
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();

            //初期表示設定
            paramMdl = new Rsv090ParamModel();
            paramMdl.setParam(form);
            Rsv090DspModel rsv090DspModel = biz.setSisetuData(paramMdl,
                    getAppRootPath(), cmd,
                    GroupSession.getResourceManager().getDomain(req));
            paramMdl.setFormData(form);

            //場所/地図画像データファイルパス
            String placeImgDir = temp.getTempPath(getRequestModel(req),
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_PLACE_UPLOAD);
            
            boolean initFlg = form.isRsv090InitFlg();

            //初期表示時かつ編集時
            if (initFlg && rsv090DspModel != null) {

                //場所/地図画像データ保存用ファイルパス
                String placeImgDataDir = temp.getTempPath(getRequestModel(req),
                        GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                        GSConstReserve.TEMP_IMG_PLACE_DATA);

                //DBから取得した場所・地図画像データを保存する
                paramMdl = new Rsv090ParamModel();
                paramMdl.setParam(form);
                biz.setPlaceImgData(paramMdl, placeImgDir, placeImgDataDir, rsv090DspModel);
                paramMdl.setFormData(form);

            }

            //初期表示フラグOFF
            form.setRsv090InitFlg(false);

            con.setAutoCommit(false);
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
        
        return __doRedraw(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 再描画処理
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
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doRedraw(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOToolsException {

        RequestModel reqMdl = getRequestModel(req);

        con.setAutoCommit(true);
        Rsv090Biz biz = new Rsv090Biz(reqMdl, con);

        //施設/設備画像ファイルパス
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDirSisetu = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_SISETU_UPLOAD);

        //場所/地図画像データ保存用ファイルパス
        String tempDirPlaceData = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);

        Rsv090ParamModel paramMdl = new Rsv090ParamModel();
        paramMdl.setParam(form);

        //施設グループ情報セット
        biz.setSisetuGroup(paramMdl);

        //添付ファイル情報セット
        paramMdl.setPlaceFormList(new ArrayList<Rsv090PlaceForm>());
        biz.setTempFiles(paramMdl, tempDirSisetu, tempDirPlaceData);

        //施設グループコンボをセット
        biz.setSisetuGrpCombo(paramMdl);

        paramMdl.setFormData(form);
        
        saveToken(req);

        return map.getInputForward();
    }
    
    /**
     * <br>[機  能] 場所画像設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws IOToolsException
     */
    private void __doSetBasyoImage(ActionMapping map,
                                            Rsv090Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con) throws IOToolsException {

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();

        //コピー先ディレクトリパスを取得
        String copyPlaImgDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_UPLOAD);

        String copyPlaImgDataDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);
        
        Rsv090Biz biz = new Rsv090Biz(reqMdl, con);
        biz.setSavePlaImgData(copyPlaImgDataDir, copyPlaImgDataDir, copyPlaImgDir);
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doBack(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws IOToolsException {

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        return map.findForward("back_to_sisetu_zyoho_settei");
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
     * @param fileKbn ファイル区分 0:施設/設備画像 1:場所/地図画像
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doGetImageFile(
            ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int fileKbn) throws SQLException, Exception {

        String fileId = form.getRsv090BinSid();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        String endDir = "";
        if (fileKbn == 0) {
            endDir = GSConstReserve.TEMP_IMG_SISETU_UPLOAD;
        } else {
            endDir = GSConstReserve.TEMP_IMG_PLACE_UPLOAD;
        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID, endDir);

        Cmn110FileModel fMdl = __getCmn110FileModel(tempDir, fileId);

        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);
        //ファイルをダウンロードする
        TempFileUtil.downloadInline(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] 添付ファイルモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリディレクトリ
     * @param fileId ファイルID
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private Cmn110FileModel __getCmn110FileModel(String tempDir,
            String fileId)
                    throws Exception {

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

        return fMdl;
    }

    /**
     * <br>[機  能] OKボタン押下処理
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
    private ActionForward __doConfirmation(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_SISETU_UPLOAD);

        //テンポラリディレクトリパスを取得
        String tmpPlaceDataDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);

        //フォームから取得した場所画像データを保存する
        Rsv090Biz biz = new Rsv090Biz(reqMdl, con);
        Rsv090ParamModel paramMdl = new Rsv090ParamModel();
        paramMdl.setParam(form);
        biz.savePlaceTempFilesData(paramMdl, tmpPlaceDataDir);
        paramMdl.setFormData(form);

        boolean commitFlg = false;

        try {

            //2重投稿
            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            GSTemporaryPathModel sisetuTempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_UPLOAD);
            String sisetuTempDir = sisetuTempMdl.getTempPath();
            //入力チェック
            ActionErrors errors = form.validateCheck(con, tempDir, req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doRedraw(map, form, req, res, con);
            }

            String placeTempDir = GSTemporaryPathModel.getInstance(getRequestModel(req),
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_PLACE_UPLOAD).getTempPath();

            String plaTempDataDir = GSTemporaryPathModel.getInstance(getRequestModel(req),
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_PLACE_DATA).getTempPath();

            GSTemporaryPathModel weekDayDsptempDir = GSTemporaryPathModel.getInstance(getRequestModel(req),
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_DSP);

            //テンポラリディレクトリから施設/設備画像データを取得
            CommonBiz commonBiz = new CommonBiz();
            List<LabelValueBean> sisetuFileList = commonBiz.getTempFileLabelList(sisetuTempDir);

            if (sisetuFileList != null && sisetuFileList.size() > 0) {
                //週間・日間で表示する画像を別テンポラリディレクトリに移動
                paramMdl = new Rsv090ParamModel();
                paramMdl.setParam(form);
                biz.setWeekDayImgOthTemp(paramMdl, sisetuTempMdl, weekDayDsptempDir);
                paramMdl.setFormData(form);
            }

            //DB更新
            MlCountMtController cntCon = getCountMtController(req);
            paramMdl.setParam(form);
            biz.updateSisetuData(paramMdl, cntCon, getAppRootPath(),
                    sisetuTempDir, weekDayDsptempDir.getTempPath(), placeTempDir, plaTempDataDir);
            paramMdl.setFormData(form);

            //ログ出力処理
            AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
            GsMessage gsMsg = new GsMessage();
            String opCode = "";
            if (form.getRsv090ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)) {
                opCode = gsMsg.getMessage(req, "cmn.entry");
            } else if (form.getRsv090ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
                opCode = gsMsg.getMessage(req, "cmn.change");
            }
            rsvBiz.outPutLog(
                    map, req, res, opCode, GSConstLog.LEVEL_TRACE,
                    "[name]" + form.getRsv090SisetuName());

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

        //テンポラリディレクトリ内のファイルを削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        return __doUpdateComp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 添付ボタン押下処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param tempType 添付タイプ 0:施設・設備画像 1:場所・地図画像
     * @throws IOToolsException ファイルアクセス例外
     */
    private void __doTemp(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int tempType) throws IOToolsException {

        RequestModel reqMdl = getRequestModel(req);

        Rsv090Biz biz = new Rsv090Biz(reqMdl, con);

        //場所画像データパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String exPlaImgDataDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);

        //フォームに入力したデータを保存する
        Rsv090ParamModel paramMdl = new Rsv090ParamModel();
        paramMdl.setParam(form);
        biz.savePlaceTempFilesData(paramMdl, exPlaImgDataDir);
        paramMdl.setFormData(form);

        if (tempType == GSConstReserve.TEMP_IMG_KBN_PLACE) {
            __doSetBasyoImage(map, form, req, res, con);
        }
    }

    /**
     * <br>[機  能] 削除確認画面でOKボタン押下時処理
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
    private ActionForward __doSisetuDeleteOk(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            RequestModel reqMdl = getRequestModel(req);
            Rsv090Biz biz = new Rsv090Biz(reqMdl, con);

            //削除処理実行
            Rsv090ParamModel paramMdl = new Rsv090ParamModel();
            paramMdl.setParam(form);
            biz.doSisetuDelete(paramMdl);
            paramMdl.setFormData(form);

            //ログ出力処理
            GsMessage gsMsg = new GsMessage(reqMdl);
            AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
            rsvBiz.outPutLog(
                    map, req, res, gsMsg.getMessage("cmn.delete"),
                    GSConstLog.LEVEL_TRACE, "[name]" + form.getRsv090SisetuName());

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

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);


        return __doDeleteComp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 施設グループコンボ変更処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param cmd コマンドパラメータ
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doChGrpComb(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            String cmd)
                    throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tmpPlaceDataDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);

        //フォームから取得した場所画像データを保存する
        Rsv090Biz biz = new Rsv090Biz(reqMdl, con);
        Rsv090ParamModel paramMdl = new Rsv090ParamModel();
        paramMdl.setParam(form);
        biz.savePlaceTempFilesData(paramMdl, tmpPlaceDataDir);
        paramMdl.setFormData(form);


        return __doInit(map, form, req, res, con, cmd);
    }
    
    /**
     * <br>[機  能] 削除ボタンクリック時の処理
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
    private ActionForward __doDelete(ActionMapping map,
                                      Rsv090Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws Exception {
        
        RequestModel reqMdl = getRequestModel(req);

        //画像が保存されているテンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();

        //場所画像データのパス(データ参照用)
        String tempPlaDataDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);

        Rsv090Biz biz = new Rsv090Biz(reqMdl, con);
        Rsv090ParamModel paramMdl = new Rsv090ParamModel();
        paramMdl.setParam(form);
        biz.detPlaceData(paramMdl, tempPlaDataDir);
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con, "delete");
    }

    /**
     * <br>[機  能] 削除ボタン押下時確認画面表示処理
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
    private ActionForward __doGrpDeleteCheck(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //場所画像データを保存する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tmpPlaceDataDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);

        Rsv090Biz biz = new Rsv090Biz(reqMdl, con);
        //フォームに入力したデータを保存する
        Rsv090ParamModel paramMdl = new Rsv090ParamModel();
        paramMdl.setParam(form);
        biz.savePlaceTempFilesData(paramMdl, tmpPlaceDataDir);
        paramMdl.setFormData(form);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteOk");

        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(forwardOk.getPath()
                + "?" + GSConst.P_CMD + "=back_to_sisetu_settei_input");

        paramMdl = new Rsv090ParamModel();
        paramMdl.setParam(form);
        String sisetuName = biz.getSisetuName(paramMdl);
        paramMdl.setFormData(form);


        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("delete.kakunin.sisetu",
                        StringUtilHtml.transToHTmlPlusAmparsant(sisetuName)));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("rsvBackPgId", form.getRsvBackPgId());
        cmn999Form.addHiddenParam("rsvBackToAdminSetting", form.getRsvBackToAdminSetting());
        cmn999Form.addHiddenParam("rsvDspFrom", form.getRsvDspFrom());
        cmn999Form.addHiddenParam("rsvSelectedGrpSid", form.getRsvSelectedGrpSid());
        cmn999Form.addHiddenParam("rsvSelectedSisetuSid", form.getRsvSelectedSisetuSid());
        cmn999Form.addHiddenParam("rsv050SortRadio", form.getRsv050SortRadio());
        cmn999Form.addHiddenParam("rsv080EditGrpSid", form.getRsv080EditGrpSid());
        cmn999Form.addHiddenParam("rsv080SortRadio", form.getRsv080SortRadio());
        cmn999Form.addHiddenParam("rsv090InitFlg", String.valueOf(form.isRsv090InitFlg()));
        cmn999Form.addHiddenParam("rsv090ProcMode", form.getRsv090ProcMode());
        cmn999Form.addHiddenParam("rsv090EditGrpSid", form.getRsv090EditGrpSid());
        cmn999Form.addHiddenParam("rsv090EditSisetuSid", form.getRsv090EditSisetuSid());
        cmn999Form.addHiddenParam("rsv090SisanKanri", form.getRsv090SisanKanri());
        cmn999Form.addHiddenParam("rsv090SisetuId", form.getRsv090SisetuId());
        cmn999Form.addHiddenParam("rsv090SisetuName", form.getRsv090SisetuName());
        cmn999Form.addHiddenParam("rsv090Prop1Value", form.getRsv090Prop1Value());
        cmn999Form.addHiddenParam("rsv090Prop2Value", form.getRsv090Prop2Value());
        cmn999Form.addHiddenParam("rsv090Prop3Value", form.getRsv090Prop3Value());
        cmn999Form.addHiddenParam("rsv090Prop4Value", form.getRsv090Prop4Value());
        cmn999Form.addHiddenParam("rsv090Prop5Value", form.getRsv090Prop5Value());
        cmn999Form.addHiddenParam("rsv090Prop6Value", form.getRsv090Prop6Value());
        cmn999Form.addHiddenParam("rsv090Prop7Value", form.getRsv090Prop7Value());
        cmn999Form.addHiddenParam("rsv090Biko", form.getRsv090Biko());
        cmn999Form.addHiddenParam("rsv090PlaceComment", form.getRsv090PlaceComment());
        cmn999Form.addHiddenParam("rsv090SisetuImgDefoValue", form.getRsv090SisetuImgDefoValue());

        cmn999Form.addHiddenParam("rsv090SisetuIdDspKbn", form.getRsv090SisetuIdDspKbn());
        cmn999Form.addHiddenParam("rsv090SisanKanriDspKbn", form.getRsv090SisanKanriDspKbn());
        cmn999Form.addHiddenParam("rsv090Prop1ValueDspKbn", form.getRsv090Prop1ValueDspKbn());
        cmn999Form.addHiddenParam("rsv090Prop2ValueDspKbn", form.getRsv090Prop2ValueDspKbn());
        cmn999Form.addHiddenParam("rsv090Prop3ValueDspKbn", form.getRsv090Prop3ValueDspKbn());
        cmn999Form.addHiddenParam("rsv090Prop4ValueDspKbn", form.getRsv090Prop4ValueDspKbn());
        cmn999Form.addHiddenParam("rsv090Prop5ValueDspKbn", form.getRsv090Prop5ValueDspKbn());
        cmn999Form.addHiddenParam("rsv090Prop6ValueDspKbn", form.getRsv090Prop6ValueDspKbn());
        cmn999Form.addHiddenParam("rsv090Prop7ValueDspKbn", form.getRsv090Prop7ValueDspKbn());
        cmn999Form.addHiddenParam("rsv090BikoDspKbn", form.getRsv090BikoDspKbn());
        cmn999Form.addHiddenParam("rsv090PlaceCommentDspKbn", form.getRsv090PlaceCommentDspKbn());
        cmn999Form.addHiddenParam("rsv090SisetuImgDefoDspKbn", form.getRsv090SisetuImgDefoDspKbn());

        cmn999Form.addHiddenParam("rsv100InitFlg",
                String.valueOf(form.isRsv100InitFlg()));
        cmn999Form.addHiddenParam("rsv100SearchFlg",
                String.valueOf(form.isRsv100SearchFlg()));
        cmn999Form.addHiddenParam("rsv100SortKey", form.getRsv100SortKey());
        cmn999Form.addHiddenParam("rsv100OrderKey", form.getRsv100OrderKey());
        cmn999Form.addHiddenParam("rsv100PageTop", form.getRsv100PageTop());
        cmn999Form.addHiddenParam("rsv100PageBottom", form.getRsv100PageBottom());
        cmn999Form.addHiddenParam("rsv100selectedToYear", form.getRsv100selectedToYear());
        cmn999Form.addHiddenParam("rsv100selectedToMonth", form.getRsv100selectedToMonth());
        cmn999Form.addHiddenParam("rsv100selectedToDay", form.getRsv100selectedToDay());
        cmn999Form.addHiddenParam("rsv100KeyWord", form.getRsv100KeyWord());
        cmn999Form.addHiddenParam("rsv100SearchCondition", form.getRsv100SearchCondition());
        cmn999Form.addHiddenParam("rsv100TargetMok", form.getRsv100TargetMok());
        cmn999Form.addHiddenParam("rsv100TargetNiyo", form.getRsv100TargetNiyo());
        cmn999Form.addHiddenParam("rsv100CsvOutField", form.getRsv100CsvOutField());
        cmn999Form.addHiddenParam("rsv100SelectedKey1", form.getRsv100SelectedKey1());
        cmn999Form.addHiddenParam("rsv100SelectedKey2", form.getRsv100SelectedKey2());
        cmn999Form.addHiddenParam("rsv100SelectedKey1Sort", form.getRsv100SelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100SelectedKey2Sort", form.getRsv100SelectedKey2Sort());
        cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", form.getRsvIkkatuTorokuKey());
        cmn999Form.addHiddenParam("rsv100svFromYear", form.getRsv100svFromYear());
        cmn999Form.addHiddenParam("rsv100svFromMonth", form.getRsv100svFromMonth());
        cmn999Form.addHiddenParam("rsv100svFromDay", form.getRsv100svFromDay());
        cmn999Form.addHiddenParam("rsv100svToYear", form.getRsv100svToYear());
        cmn999Form.addHiddenParam("rsv100svToMonth", form.getRsv100svToMonth());
        cmn999Form.addHiddenParam("rsv100svToDay", form.getRsv100svToDay());
        cmn999Form.addHiddenParam("rsv100svGrp1", form.getRsv100svGrp1());
        cmn999Form.addHiddenParam("rsv100svGrp2", form.getRsv100svGrp2());
        cmn999Form.addHiddenParam("rsv100svKeyWord", form.getRsv100svKeyWord());
        cmn999Form.addHiddenParam("rsv100svSearchCondition", form.getRsv100svSearchCondition());
        cmn999Form.addHiddenParam("rsv100svTargetMok", form.getRsv100svTargetMok());
        cmn999Form.addHiddenParam("rsv100svTargetNiyo", form.getRsv100svTargetNiyo());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1", form.getRsv100svSelectedKey1());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2", form.getRsv100svSelectedKey2());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1Sort", form.getRsv100svSelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2Sort", form.getRsv100svSelectedKey2Sort());
        cmn999Form.addHiddenParam("rsv100SearchSvFlg",
                String.valueOf(form.isRsv100SearchSvFlg()));

        cmn999Form.addHiddenParam("rsv100dateKbn", form.getRsv100dateKbn());
        cmn999Form.addHiddenParam("rsv100apprStatus", form.getRsv100apprStatus());
        cmn999Form.addHiddenParam("rsv100svDateKbn", form.getRsv100svDateKbn());
        cmn999Form.addHiddenParam("rsv100svApprStatus", form.getRsv100svApprStatus());
        cmn999Form.addHiddenParam("rsv010LearnMoreFlg", form.getRsv010LearnMoreFlg());
        cmn999Form.addHiddenParam("rsv010sisetuKeyword", form.getRsv010sisetuKeyword());
        cmn999Form.addHiddenParam("rsv010KeyWordkbn", form.getRsv010KeyWordkbn());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", form.getRsv010sisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu", form.getRsv010sisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordBiko", form.getRsv010sisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordNo", form.getRsv010sisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordIsbn", form.getRsv010sisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010sisetuFree", form.getRsv010sisetuFree());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromY", form.getRsv010sisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMo", form.getRsv010sisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromD", form.getRsv010sisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromH", form.getRsv010sisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMi", form.getRsv010sisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToY", form.getRsv010sisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMo", form.getRsv010sisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToD", form.getRsv010sisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToH", form.getRsv010sisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMi", form.getRsv010sisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010sisetuKbn", form.getRsv010sisetuKbn());
        cmn999Form.addHiddenParam("rsv010grpNarrowDown", form.getRsv010grpNarrowDown());
        cmn999Form.addHiddenParam("rsv010sisetuSmoky", form.getRsv010sisetuSmoky());
        cmn999Form.addHiddenParam("rsv010sisetuChere", form.getRsv010sisetuChere());
        cmn999Form.addHiddenParam("rsv010sisetuTakeout", form.getRsv010sisetuTakeout());
        cmn999Form.addHiddenParam("rsv010svSisetuKeyword", form.getRsv010svSisetuKeyword());
        cmn999Form.addHiddenParam("rsv010svKeyWordkbn", form.getRsv010svKeyWordkbn());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisan",
                form.getRsv010svSisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisetu",
                form.getRsv010svSisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko", form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn", form.getRsv010svSisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010svSisetuFree", form.getRsv010svSisetuFree());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromY", form.getRsv010svSisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMo", form.getRsv010svSisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromD", form.getRsv010svSisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromH", form.getRsv010svSisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMi", form.getRsv010svSisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToY", form.getRsv010svSisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMo", form.getRsv010svSisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToD", form.getRsv010svSisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToH", form.getRsv010svSisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMi", form.getRsv010svSisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010svSisetuKbn", form.getRsv010svSisetuKbn());
        cmn999Form.addHiddenParam("rsv010svGrpNarrowDown", form.getRsv010svGrpNarrowDown());
        cmn999Form.addHiddenParam("rsv010svSisetuSmoky", form.getRsv010svSisetuSmoky());
        cmn999Form.addHiddenParam("rsv010svSisetuChere", form.getRsv010svSisetuChere());
        cmn999Form.addHiddenParam("rsv010svSisetuTakeout", form.getRsv010svSisetuTakeout());
        cmn999Form.addHiddenParam("rsv010InitFlg", form.getRsv010InitFlg());
        cmn999Form.addHiddenParam("rsv010SiborikomiFlg", form.getRsv010SiborikomiFlg());
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理完了後の画面遷移設定
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
    private ActionForward __doDeleteComp(ActionMapping map,
            Rsv090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("back_to_sisetu_zyoho_settei");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "reserve.rsv070.1")));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("rsvBackPgId", form.getRsvBackPgId());
        cmn999Form.addHiddenParam("rsvBackToAdminSetting", form.getRsvBackToAdminSetting());
        cmn999Form.addHiddenParam("rsvDspFrom", form.getRsvDspFrom());
        cmn999Form.addHiddenParam("rsvSelectedGrpSid", form.getRsvSelectedGrpSid());
        cmn999Form.addHiddenParam("rsvSelectedSisetuSid", form.getRsvSelectedSisetuSid());
        cmn999Form.addHiddenParam("rsv050SortRadio", form.getRsv050SortRadio());
        cmn999Form.addHiddenParam("rsv080EditGrpSid", form.getRsv080EditGrpSid());
        cmn999Form.addHiddenParam("rsv080SortRadio", form.getRsv080SortRadio());
        cmn999Form.addHiddenParam("Rsv090SisetuName", form.getRsv090SisetuName());
        cmn999Form.addHiddenParam("rsv100InitFlg",
                String.valueOf(form.isRsv100InitFlg()));
        cmn999Form.addHiddenParam("rsv100SearchFlg",
                String.valueOf(form.isRsv100SearchFlg()));
        cmn999Form.addHiddenParam("rsv100SortKey", form.getRsv100SortKey());
        cmn999Form.addHiddenParam("rsv100OrderKey", form.getRsv100OrderKey());
        cmn999Form.addHiddenParam("rsv100PageTop", form.getRsv100PageTop());
        cmn999Form.addHiddenParam("rsv100PageBottom", form.getRsv100PageBottom());
        cmn999Form.addHiddenParam("rsv100selectedToYear", form.getRsv100selectedToYear());
        cmn999Form.addHiddenParam("rsv100selectedToMonth", form.getRsv100selectedToMonth());
        cmn999Form.addHiddenParam("rsv100selectedToDay", form.getRsv100selectedToDay());
        cmn999Form.addHiddenParam("rsv100KeyWord", form.getRsv100KeyWord());
        cmn999Form.addHiddenParam("rsv100SearchCondition", form.getRsv100SearchCondition());
        cmn999Form.addHiddenParam("rsv100TargetMok", form.getRsv100TargetMok());
        cmn999Form.addHiddenParam("rsv100TargetNiyo", form.getRsv100TargetNiyo());
        cmn999Form.addHiddenParam("rsv100CsvOutField", form.getRsv100CsvOutField());
        cmn999Form.addHiddenParam("rsv100SelectedKey1", form.getRsv100SelectedKey1());
        cmn999Form.addHiddenParam("rsv100SelectedKey2", form.getRsv100SelectedKey2());
        cmn999Form.addHiddenParam("rsv100SelectedKey1Sort", form.getRsv100SelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100SelectedKey2Sort", form.getRsv100SelectedKey2Sort());
        cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", form.getRsvIkkatuTorokuKey());
        cmn999Form.addHiddenParam("rsv100svFromYear", form.getRsv100svFromYear());
        cmn999Form.addHiddenParam("rsv100svFromMonth", form.getRsv100svFromMonth());
        cmn999Form.addHiddenParam("rsv100svFromDay", form.getRsv100svFromDay());
        cmn999Form.addHiddenParam("rsv100svToYear", form.getRsv100svToYear());
        cmn999Form.addHiddenParam("rsv100svToMonth", form.getRsv100svToMonth());
        cmn999Form.addHiddenParam("rsv100svToDay", form.getRsv100svToDay());
        cmn999Form.addHiddenParam("rsv100svGrp1", form.getRsv100svGrp1());
        cmn999Form.addHiddenParam("rsv100svGrp2", form.getRsv100svGrp2());
        cmn999Form.addHiddenParam("rsv100svKeyWord", form.getRsv100svKeyWord());
        cmn999Form.addHiddenParam("rsv100svSearchCondition", form.getRsv100svSearchCondition());
        cmn999Form.addHiddenParam("rsv100svTargetMok", form.getRsv100svTargetMok());
        cmn999Form.addHiddenParam("rsv100svTargetNiyo", form.getRsv100svTargetNiyo());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1", form.getRsv100svSelectedKey1());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2", form.getRsv100svSelectedKey2());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1Sort", form.getRsv100svSelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2Sort", form.getRsv100svSelectedKey2Sort());
        cmn999Form.addHiddenParam("rsv100SearchSvFlg",
                String.valueOf(form.isRsv100SearchSvFlg()));

        cmn999Form.addHiddenParam("rsv100dateKbn", form.getRsv100dateKbn());
        cmn999Form.addHiddenParam("rsv100apprStatus", form.getRsv100apprStatus());
        cmn999Form.addHiddenParam("rsv100svDateKbn", form.getRsv100svDateKbn());
        cmn999Form.addHiddenParam("rsv100svApprStatus", form.getRsv100svApprStatus());

        cmn999Form.addHiddenParam("rsv010LearnMoreFlg", form.getRsv010LearnMoreFlg());
        cmn999Form.addHiddenParam("rsv010sisetuKeyword", form.getRsv010sisetuKeyword());
        cmn999Form.addHiddenParam("rsv010KeyWordkbn", form.getRsv010KeyWordkbn());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", form.getRsv010sisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu", form.getRsv010sisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordBiko", form.getRsv010sisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordNo", form.getRsv010sisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordIsbn", form.getRsv010sisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010sisetuFree", form.getRsv010sisetuFree());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromY", form.getRsv010sisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMo", form.getRsv010sisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromD", form.getRsv010sisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromH", form.getRsv010sisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMi", form.getRsv010sisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToY", form.getRsv010sisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMo", form.getRsv010sisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToD", form.getRsv010sisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToH", form.getRsv010sisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMi", form.getRsv010sisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010sisetuKbn", form.getRsv010sisetuKbn());
        cmn999Form.addHiddenParam("rsv010grpNarrowDown", form.getRsv010grpNarrowDown());
        cmn999Form.addHiddenParam("rsv010sisetuSmoky", form.getRsv010sisetuSmoky());
        cmn999Form.addHiddenParam("rsv010sisetuChere", form.getRsv010sisetuChere());
        cmn999Form.addHiddenParam("rsv010sisetuTakeout", form.getRsv010sisetuTakeout());
        cmn999Form.addHiddenParam("rsv010svSisetuKeyword", form.getRsv010svSisetuKeyword());
        cmn999Form.addHiddenParam("rsv010svKeyWordkbn", form.getRsv010svKeyWordkbn());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisan",
                form.getRsv010svSisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisetu",
                form.getRsv010svSisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko", form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn", form.getRsv010svSisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010svSisetuFree", form.getRsv010svSisetuFree());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromY", form.getRsv010svSisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMo", form.getRsv010svSisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromD", form.getRsv010svSisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromH", form.getRsv010svSisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMi", form.getRsv010svSisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToY", form.getRsv010svSisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMo", form.getRsv010svSisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToD", form.getRsv010svSisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToH", form.getRsv010svSisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMi", form.getRsv010svSisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010svSisetuKbn", form.getRsv010svSisetuKbn());
        cmn999Form.addHiddenParam("rsv010svGrpNarrowDown", form.getRsv010svGrpNarrowDown());
        cmn999Form.addHiddenParam("rsv010svSisetuSmoky", form.getRsv010svSisetuSmoky());
        cmn999Form.addHiddenParam("rsv010svSisetuChere", form.getRsv010svSisetuChere());
        cmn999Form.addHiddenParam("rsv010svSisetuTakeout", form.getRsv010svSisetuTakeout());
        cmn999Form.addHiddenParam("rsv010InitFlg", form.getRsv010InitFlg());
        cmn999Form.addHiddenParam("rsv010SiborikomiFlg", form.getRsv010SiborikomiFlg());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    
    /**
     * <br>[機  能] 施設更新完了後の画面遷移設定
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
    private ActionForward __doUpdateComp(ActionMapping map,
                                          Rsv090Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("back_to_sisetu_zyoho_settei");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        String msgId = "";
        if (form.getRsv090ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)) {
            msgId = "touroku.kanryo.object";
        } else if (form.getRsv090ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
            msgId = "hensyu.kanryo.object";
        }

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(msgRes.getMessage(msgId, gsMsg.getMessage(req, "reserve.rsv070.1")));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("rsvBackPgId", form.getRsvBackPgId());
        cmn999Form.addHiddenParam("rsvBackToAdminSetting", form.getRsvBackToAdminSetting());
        cmn999Form.addHiddenParam("rsvDspFrom", form.getRsvDspFrom());
        cmn999Form.addHiddenParam("rsvSelectedGrpSid", form.getRsvSelectedGrpSid());
        cmn999Form.addHiddenParam("rsvSelectedSisetuSid", form.getRsvSelectedSisetuSid());
        cmn999Form.addHiddenParam("rsv050SortRadio", form.getRsv050SortRadio());
        cmn999Form.addHiddenParam("rsv080EditGrpSid", form.getRsv080EditGrpSid());
        cmn999Form.addHiddenParam("rsv080SortRadio", form.getRsv080SortRadio());
        cmn999Form.addHiddenParam("rsv100InitFlg",
                String.valueOf(form.isRsv100InitFlg()));
        cmn999Form.addHiddenParam("rsv100SearchFlg",
                String.valueOf(form.isRsv100SearchFlg()));
        cmn999Form.addHiddenParam("rsv100SortKey", form.getRsv100SortKey());
        cmn999Form.addHiddenParam("rsv100OrderKey", form.getRsv100OrderKey());
        cmn999Form.addHiddenParam("rsv100PageTop", form.getRsv100PageTop());
        cmn999Form.addHiddenParam("rsv100PageBottom", form.getRsv100PageBottom());
        cmn999Form.addHiddenParam("rsv100selectedFromYear", form.getRsv100selectedFromYear());
        cmn999Form.addHiddenParam("rsv100selectedFromMonth", form.getRsv100selectedFromMonth());
        cmn999Form.addHiddenParam("rsv100selectedFromDay", form.getRsv100selectedFromDay());
        cmn999Form.addHiddenParam("rsv100selectedToYear", form.getRsv100selectedToYear());
        cmn999Form.addHiddenParam("rsv100selectedToMonth", form.getRsv100selectedToMonth());
        cmn999Form.addHiddenParam("rsv100selectedToDay", form.getRsv100selectedToDay());
        cmn999Form.addHiddenParam("rsv100KeyWord", form.getRsv100KeyWord());
        cmn999Form.addHiddenParam("rsv100SearchCondition", form.getRsv100SearchCondition());
        cmn999Form.addHiddenParam("rsv100TargetMok", form.getRsv100TargetMok());
        cmn999Form.addHiddenParam("rsv100TargetNiyo", form.getRsv100TargetNiyo());
        cmn999Form.addHiddenParam("rsv100CsvOutField", form.getRsv100CsvOutField());
        cmn999Form.addHiddenParam("rsv100SelectedKey1", form.getRsv100SelectedKey1());
        cmn999Form.addHiddenParam("rsv100SelectedKey2", form.getRsv100SelectedKey2());
        cmn999Form.addHiddenParam("rsv100SelectedKey1Sort", form.getRsv100SelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100SelectedKey2Sort", form.getRsv100SelectedKey2Sort());
        cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", form.getRsvIkkatuTorokuKey());
        cmn999Form.addHiddenParam("rsv100svFromYear", form.getRsv100svFromYear());
        cmn999Form.addHiddenParam("rsv100svFromMonth", form.getRsv100svFromMonth());
        cmn999Form.addHiddenParam("rsv100svFromDay", form.getRsv100svFromDay());
        cmn999Form.addHiddenParam("rsv100svToYear", form.getRsv100svToYear());
        cmn999Form.addHiddenParam("rsv100svToMonth", form.getRsv100svToMonth());
        cmn999Form.addHiddenParam("rsv100svToDay", form.getRsv100svToDay());
        cmn999Form.addHiddenParam("rsv100svGrp1", form.getRsv100svGrp1());
        cmn999Form.addHiddenParam("rsv100svGrp2", form.getRsv100svGrp2());
        cmn999Form.addHiddenParam("rsv100svKeyWord", form.getRsv100svKeyWord());
        cmn999Form.addHiddenParam("rsv100svSearchCondition", form.getRsv100svSearchCondition());
        cmn999Form.addHiddenParam("rsv100svTargetMok", form.getRsv100svTargetMok());
        cmn999Form.addHiddenParam("rsv100svTargetNiyo", form.getRsv100svTargetNiyo());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1", form.getRsv100svSelectedKey1());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2", form.getRsv100svSelectedKey2());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1Sort", form.getRsv100svSelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2Sort", form.getRsv100svSelectedKey2Sort());
        cmn999Form.addHiddenParam("rsv100SearchSvFlg",
                String.valueOf(form.isRsv100SearchSvFlg()));

        cmn999Form.addHiddenParam("rsv100dateKbn", form.getRsv100dateKbn());
        cmn999Form.addHiddenParam("rsv100apprStatus", form.getRsv100apprStatus());
        cmn999Form.addHiddenParam("rsv100svDateKbn", form.getRsv100svDateKbn());
        cmn999Form.addHiddenParam("rsv100svApprStatus", form.getRsv100svApprStatus());
        cmn999Form.addHiddenParam("rsv010LearnMoreFlg", form.getRsv010LearnMoreFlg());
        cmn999Form.addHiddenParam("rsv010sisetuKeyword", form.getRsv010sisetuKeyword());
        cmn999Form.addHiddenParam("rsv010KeyWordkbn", form.getRsv010KeyWordkbn());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", form.getRsv010sisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu", form.getRsv010sisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordBiko", form.getRsv010sisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordNo", form.getRsv010sisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordIsbn", form.getRsv010sisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010sisetuFree", form.getRsv010sisetuFree());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromY", form.getRsv010sisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMo", form.getRsv010sisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromD", form.getRsv010sisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromH", form.getRsv010sisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMi", form.getRsv010sisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToY", form.getRsv010sisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMo", form.getRsv010sisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToD", form.getRsv010sisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToH", form.getRsv010sisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMi", form.getRsv010sisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010sisetuKbn", form.getRsv010sisetuKbn());
        cmn999Form.addHiddenParam("rsv010grpNarrowDown", form.getRsv010grpNarrowDown());
        cmn999Form.addHiddenParam("rsv010sisetuSmoky", form.getRsv010sisetuSmoky());
        cmn999Form.addHiddenParam("rsv010sisetuChere", form.getRsv010sisetuChere());
        cmn999Form.addHiddenParam("rsv010sisetuTakeout", form.getRsv010sisetuTakeout());
        cmn999Form.addHiddenParam("rsv010svSisetuKeyword", form.getRsv010svSisetuKeyword());
        cmn999Form.addHiddenParam("rsv010svKeyWordkbn", form.getRsv010svKeyWordkbn());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisan",
                form.getRsv010svSisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisetu",
                form.getRsv010svSisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko", form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn", form.getRsv010svSisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010svSisetuFree", form.getRsv010svSisetuFree());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromY", form.getRsv010svSisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMo", form.getRsv010svSisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromD", form.getRsv010svSisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromH", form.getRsv010svSisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMi", form.getRsv010svSisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToY", form.getRsv010svSisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMo", form.getRsv010svSisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToD", form.getRsv010svSisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToH", form.getRsv010svSisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMi", form.getRsv010svSisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010svSisetuKbn", form.getRsv010svSisetuKbn());
        cmn999Form.addHiddenParam("rsv010svGrpNarrowDown", form.getRsv010svGrpNarrowDown());
        cmn999Form.addHiddenParam("rsv010svSisetuSmoky", form.getRsv010svSisetuSmoky());
        cmn999Form.addHiddenParam("rsv010svSisetuChere", form.getRsv010svSisetuChere());
        cmn999Form.addHiddenParam("rsv010svSisetuTakeout", form.getRsv010svSisetuTakeout());
        cmn999Form.addHiddenParam("rsv010InitFlg", form.getRsv010InitFlg());
        cmn999Form.addHiddenParam("rsv010SiborikomiFlg", form.getRsv010SiborikomiFlg());
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
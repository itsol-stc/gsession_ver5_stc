package jp.groupsession.v2.rsv.rsv100;

import java.io.IOException;
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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.pdf.RsvListPdfModel;
import jp.groupsession.v2.rsv.rsv010.Rsv010Form;
import jp.groupsession.v2.rsv.rsv020.Rsv020Form;
import jp.groupsession.v2.rsv.rsv040.Rsv040Form;
import jp.groupsession.v2.rsv.rsv050.Rsv050Form;
import jp.groupsession.v2.rsv.rsv140.Rsv140Form;
import jp.groupsession.v2.rsv.rsv210.Rsv210Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設利用状況照会画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv100Action extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv100Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "rsv100";

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

        if (cmd.equals("export")) {
            return true;
        } else if (cmd.equals("pdf")) {
            log__.debug("PDFファイルダウンロード");
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
        Rsv100Form rsvform = (Rsv100Form) form;
        rsvform.setRsv100userSid(getSessionUserModel(req).getUsrsid());

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //施設設定ボタン押下
        if (cmd.equals("sisetu_settei")) {
            log__.debug("施設設定ボタン押下");
            forward = __doMoveSisetuSettei(map, rsvform, req, res, con);
        //他画面から遷移してきた場合
        } else if (cmd.equals("riyo_zyokyo_syokai")) {
            log__.debug("他画面から遷移してきた");
            forward = __doMoved(map, rsvform, req, res, con, cmd);
        //エクスポートボタン押下
        } else if (cmd.equals("pdf")) {
            log__.debug("PDFボタン押下");
            forward = __doDownLoadPdf(map, rsvform, req, res, con);
        //管理者設定ボタン押下
        } else if (cmd.equals("kanrisya_settei")) {
            log__.debug("管理者設定ボタン押下");
            forward = __doMoveKanri(map, rsvform, req, res, con);
        //個人設定ボタン押下
        } else if (cmd.equals("kozin_settei")) {
            log__.debug("個人設定ボタン押下");
            forward = __doMoveKojin(map, rsvform, req, res, con);
        //日間ボタン押下
        } else if (cmd.equals("nikkan")) {
            log__.debug("日間ボタン押下");
            forward = __doMoveNikkan(map, rsvform, req, res, con);
        //週間ボタン押下
        } else if (cmd.equals("syukan")) {
            log__.debug("週間ボタン押下");
            forward = __doMoveSyukan(map, rsvform, req, res, con);
        //検索ボタン押下
        } else if (cmd.equals("kensaku")) {
            log__.debug("検索ボタン押下");
            forward = __doSearch(map, rsvform, req, res, con);
        //エクスポートボタン押下
        } else if (cmd.equals("export")) {
            log__.debug("エクスポートボタン押下");
            forward = __doExport(map, rsvform, req, res, con);
        //左矢印ボタン押下
        } else if (cmd.equals("pageleft")) {
            log__.debug("左矢印ボタン押下");
            rsvform.setRsv100PageTop(rsvform.getRsv100PageTop() - 1);
            forward = __doRedraw(map, rsvform, req, res, con);
        //右矢印ボタン押下
        } else if (cmd.equals("pageright")) {
            log__.debug("右矢印ボタン押下");
            rsvform.setRsv100PageTop(rsvform.getRsv100PageTop() + 1);
            forward = __doRedraw(map, rsvform, req, res, con);
        //ページコンボ変更
        } else if (cmd.equals("pageChange")) {
            log__.debug("ページコンボ変更");
            forward = __doRedraw(map, rsvform, req, res, con);
        //グループコンボ変更
        } else if (cmd.equals("comboChange")) {
            log__.debug("グループコンボ変更");
            forward = __doRedraw(map, rsvform, req, res, con);
        //一括予約ボタン押下
        } else if (cmd.equals("ikkatu_yoyaku")) {
            log__.debug("一括予約ボタン押下");
            forward = __doMoveIkkatu(map, rsvform, req, res, con);
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doMoved(map, rsvform, req, res, con, cmd);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rsv100Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        form.setRsv100SearchFlg(true);

        return __doSearch(map, form, req, res, con);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doDsp(ActionMapping map,
                                   Rsv100Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws Exception {
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Rsv100Biz biz = new Rsv100Biz(con, reqMdl);

        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        biz.initDsp(paramMdl, reqMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
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
                                    Rsv100Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionErrors errors = form.validateCheck(req);
        con.setAutoCommit(true);
        //入力チェック
        if (!errors.isEmpty()) {
            form.setRsv100SearchFlg(false);
            form.setRsv100SearchSvFlg(false);
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        RequestModel reqMdl = getRequestModel(req);
        Rsv100Biz biz = new Rsv100Biz(con, reqMdl);

        form.setRsv100PageTop(1);
        form.setRsv100SearchFlg(true);

        //パラメータをコピー
        form.setParameter();
        //施設グループSIDより施設区分を取得
        int sisKbn = biz.getSisetsuKbn(con, form.getRsvSelectedGrpSid());

        //既存の施設区分と検索ボタン押下後の施設区分に違いがあった場合
        if (form.getRsv100svSelectSisKbn() != sisKbn) {
            form.setRsv100CsvOutField(biz.getCsvOut(sisKbn));
        }
        form.setRsv100svSelectSisKbn(sisKbn);


        //検索結果の有無チェック
        GsMessage gsMsg = new GsMessage(reqMdl);

        int searchCnt = 0;
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        searchCnt = biz.searchCount(paramMdl);
        paramMdl.setFormData(form);

        if (searchCnt <= 0) {
            ActionMessage msg = new ActionMessage(
                    "search.data.notfound",
                    gsMsg.getMessage("reserve.149"));
            StrutsUtil.addMessage(errors, msg, "");
            addErrors(req, errors);
            form.setRsv100SearchFlg(false);
            form.setRsv100SearchSvFlg(false);
        }

        //検索結果セット


        paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        biz.setSearchList(paramMdl);
        paramMdl.setFormData(form);

        return __doDsp(map, form, req, res, con);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doRedraw(ActionMapping map,
                                      Rsv100Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws Exception {

        con.setAutoCommit(true);
        ActionErrors errors = new ActionErrors();
        form.setRsv100SearchFlg(true);

        //検索結果の有無チェック
        RequestModel reqMdl = getRequestModel(req);
        Rsv100Biz biz = new Rsv100Biz(con, reqMdl);
        GsMessage gsMsg = new GsMessage(reqMdl);

        int searchCnt = 0;
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        searchCnt = biz.searchCount(paramMdl);
        paramMdl.setFormData(form);

        if (searchCnt <= 0) {
            ActionMessage msg = new ActionMessage(
                    "search.data.notfound",
                    gsMsg.getMessage("reserve.149"));
            StrutsUtil.addMessage(errors, msg, "");
            addErrors(req, errors);
            form.setRsv100SearchFlg(false);
            form.setRsv100SearchSvFlg(false);
        }

        //検索結果セット
        paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        biz.setSearchList(paramMdl);
        paramMdl.setFormData(form);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 他画面から遷移してきた場合
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param cmd コマンド
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doMoved(ActionMapping map,
                                    Rsv100Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con,
                                    String cmd) throws Exception {

        ActionForward forward = null;
        GsMessage gsMsg = new GsMessage();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String frDateNameJp = gsMsg.getMessage("reserve.157");
        String toDateNameJp = gsMsg.getMessage("reserve.158");

        if (form.isRsv100InitFlg() || cmd.equals("gotosearch")) {
            form.setRsv100InitFlg(false);

            //コンボに日付をセット
            RsvCommonBiz cBiz = new RsvCommonBiz();
            String rsvDspDate = cBiz.getDspFrom(form.getRsvDspFrom());
            UDate dspDate = new UDate();
            dspDate.setDate(rsvDspDate);

            UDate toDate = dspDate.cloneUDate();
            toDate.addYear(1);

            form.setRsv100selectedFromYear(dspDate.getYear());
            form.setRsv100selectedFromMonth(dspDate.getMonth());
            form.setRsv100selectedFromDay(dspDate.getIntDay());
            form.setRsv100selectedToYear(toDate.getYear());
            form.setRsv100selectedToMonth(toDate.getMonth());
            form.setRsv100selectedToDay(toDate.getIntDay());
            picker.setDateParam(
                    form, 
                    "rsv100selectedFromDate",
                    "rsv100selectedFromYear",
                    "rsv100selectedFromMonth",
                    "rsv100selectedFromDay",
                    frDateNameJp);
            picker.setDateParam(
                    form, 
                    "rsv100selectedToDate",
                    "rsv100selectedToYear",
                    "rsv100selectedToMonth",
                    "rsv100selectedToDay",
                    toDateNameJp);

            form.setRsv100TargetMok(1);
            form.setRsv100TargetNiyo(1);


            Rsv100Biz biz = new Rsv100Biz(con, getRequestModel(req));
            //施設区分
            int sisKbn = biz.getSisetsuKbn(con, form.getRsvSelectedGrpSid());
            form.setRsv100svSelectSisKbn(sisKbn);
            form.setRsv100CsvOutField(biz.getCsvOut(sisKbn));
            form.setRsv100SearchFlg(true);

            //パラメータをコピー
            form.setParameter();

            forward = __doSearch(map, form, req, res, con);

        } else {
            picker.setDateParam(
                    form, 
                    "rsv100selectedFromDate",
                    "rsv100selectedFromYear",
                    "rsv100selectedFromMonth",
                    "rsv100selectedFromDay",
                    frDateNameJp);
            picker.setDateParam(
                    form, 
                    "rsv100selectedToDate",
                    "rsv100selectedToYear",
                    "rsv100selectedToMonth",
                    "rsv100selectedToDay",
                    toDateNameJp);

            forward = __doRedraw(map, form, req, res, con);

        }

        return forward;
    }

    /**
     * <br>[機  能] エクスポート
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
    private ActionForward __doExport(ActionMapping map,
                                    Rsv100Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionErrors errors = form.validateCsvOutCheck(req);
        //CSV出力チェック
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setRsv100SearchFlg(false);
            form.setRsv100SearchSvFlg(false);
            return __doDsp(map, form, req, res, con);
        }

        //管理者かどうか
        RequestModel reqMdl = getRequestModel(req);
        Rsv100Biz biz = new Rsv100Biz(con, reqMdl);

        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        biz.setAdmFlg(paramMdl);
        paramMdl.setFormData(form);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);
        //CSV作成
        String fileName = GSConstReserve.RSV_CSV_NAME;


        paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        Rsv100CsvWriter writer = new Rsv100CsvWriter(con, paramMdl, reqMdl);
        paramMdl.setFormData(form);

        writer.outputCsv(tempDir);

        String fullPath = tempDir + fileName;
        //ダウンロード
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        rsvBiz.outPutLog(map, req, res,
                gsMsg.getMessage(req, "cmn.export"),
                GSConstLog.LEVEL_INFO, fileName);

        return null;
    }

    /**
     * <br>[機  能] 日間画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doMoveNikkan(ActionMapping map,
                                          Rsv100Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) {

        Rsv020Form nextForm = new Rsv020Form();
        Rsv100Biz biz = new Rsv100Biz(con, getRequestModel(req));

        String convDspDate = "";
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        convDspDate = biz.convDspDate(paramMdl);
        paramMdl.setFormData(form);

        nextForm.setRsvDspFrom(convDspDate);
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsv100selectedFromYear(form.getRsv100selectedFromYear());
        nextForm.setRsv100selectedFromMonth(form.getRsv100selectedFromMonth());
        nextForm.setRsv100selectedFromDay(form.getRsv100selectedFromDay());
        nextForm.setRsv100selectedToYear(form.getRsv100selectedToYear());
        nextForm.setRsv100selectedToMonth(form.getRsv100selectedToMonth());
        nextForm.setRsv100selectedToDay(form.getRsv100selectedToDay());

        //追加
        nextForm.setRsv100KeyWord(form.getRsv100KeyWord());
        nextForm.setRsv100SearchCondition(form.getRsv100SearchCondition());
        nextForm.setRsv100TargetMok(form.getRsv100TargetMok());
        nextForm.setRsv100TargetNiyo(form.getRsv100TargetNiyo());
        nextForm.setRsv100CsvOutField(form.getRsv100CsvOutField());
        nextForm.setRsv100SelectedKey1(form.getRsv100SelectedKey1());
        nextForm.setRsv100SelectedKey2(form.getRsv100SelectedKey2());
        nextForm.setRsv100SelectedKey1Sort(form.getRsv100SelectedKey1Sort());
        nextForm.setRsv100SelectedKey2Sort(form.getRsv100SelectedKey2Sort());
        req.setAttribute("rsv020Form", nextForm);

        return map.findForward("nikkan");
    }

    /**
     * <br>[機  能] 週間画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doMoveSyukan(ActionMapping map,
                                          Rsv100Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) {

        Rsv010Form nextForm = new Rsv010Form();
        Rsv100Biz biz = new Rsv100Biz(con, getRequestModel(req));

        String convDspDate = "";
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        convDspDate = biz.convDspDate(paramMdl);
        paramMdl.setFormData(form);

        nextForm.setRsvDspFrom(convDspDate);
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsv100selectedFromYear(form.getRsv100selectedFromYear());
        nextForm.setRsv100selectedFromMonth(form.getRsv100selectedFromMonth());
        nextForm.setRsv100selectedFromDay(form.getRsv100selectedFromDay());
        nextForm.setRsv100selectedToYear(form.getRsv100selectedToYear());
        nextForm.setRsv100selectedToMonth(form.getRsv100selectedToMonth());
        nextForm.setRsv100selectedToDay(form.getRsv100selectedToDay());
        //追加
        nextForm.setRsv100KeyWord(form.getRsv100KeyWord());
        nextForm.setRsv100SearchCondition(form.getRsv100SearchCondition());
        nextForm.setRsv100TargetMok(form.getRsv100TargetMok());
        nextForm.setRsv100TargetNiyo(form.getRsv100TargetNiyo());
        nextForm.setRsv100CsvOutField(form.getRsv100CsvOutField());
        nextForm.setRsv100SelectedKey1(form.getRsv100SelectedKey1());
        nextForm.setRsv100SelectedKey2(form.getRsv100SelectedKey2());
        nextForm.setRsv100SelectedKey1Sort(form.getRsv100SelectedKey1Sort());
        nextForm.setRsv100SelectedKey2Sort(form.getRsv100SelectedKey2Sort());
        req.setAttribute("rsv010Form", nextForm);

        return map.findForward("syukan");
    }

    /**
     * <br>[機  能] 施設設定画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doMoveSisetuSettei(ActionMapping map,
                                                Rsv100Form form,
                                                HttpServletRequest req,
                                                HttpServletResponse res,
                                                Connection con) {


        Rsv050Form nextForm = new Rsv050Form();
        Rsv100Biz biz = new Rsv100Biz(con, getRequestModel(req));

        String convDspDate = "";
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        convDspDate = biz.convDspDate(paramMdl);
        paramMdl.setFormData(form);

        nextForm.setRsvDspFrom(convDspDate);
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV100);
        nextForm.setRsv100selectedFromYear(form.getRsv100selectedFromYear());
        nextForm.setRsv100selectedFromMonth(form.getRsv100selectedFromMonth());
        nextForm.setRsv100selectedFromDay(form.getRsv100selectedFromDay());
        nextForm.setRsv100selectedToYear(form.getRsv100selectedToYear());
        nextForm.setRsv100selectedToMonth(form.getRsv100selectedToMonth());
        nextForm.setRsv100selectedToDay(form.getRsv100selectedToDay());
        //追加
        nextForm.setRsv100KeyWord(form.getRsv100KeyWord());
        nextForm.setRsv100SearchCondition(form.getRsv100SearchCondition());
        nextForm.setRsv100TargetMok(form.getRsv100TargetMok());
        nextForm.setRsv100TargetNiyo(form.getRsv100TargetNiyo());
        nextForm.setRsv100CsvOutField(form.getRsv100CsvOutField());
        nextForm.setRsv100SelectedKey1(form.getRsv100SelectedKey1());
        nextForm.setRsv100SelectedKey2(form.getRsv100SelectedKey2());
        nextForm.setRsv100SelectedKey1Sort(form.getRsv100SelectedKey1Sort());
        nextForm.setRsv100SelectedKey2Sort(form.getRsv100SelectedKey2Sort());
        req.setAttribute("rsv050Form", nextForm);

        return map.findForward("sisetu_settei");
    }

    /**
     * <br>[機  能] 管理者設定画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doMoveKanri(ActionMapping map,
                                         Rsv100Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con) {

        Rsv040Form nextForm = new Rsv040Form();
        Rsv100Biz biz = new Rsv100Biz(con, getRequestModel(req));

        String convDspDate = "";
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        convDspDate = biz.convDspDate(paramMdl);
        paramMdl.setFormData(form);

        nextForm.setRsvDspFrom(convDspDate);
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV100);
        nextForm.setRsv100selectedFromYear(form.getRsv100selectedFromYear());
        nextForm.setRsv100selectedFromMonth(form.getRsv100selectedFromMonth());
        nextForm.setRsv100selectedFromDay(form.getRsv100selectedFromDay());
        nextForm.setRsv100selectedToYear(form.getRsv100selectedToYear());
        nextForm.setRsv100selectedToMonth(form.getRsv100selectedToMonth());
        nextForm.setRsv100selectedToDay(form.getRsv100selectedToDay());

        //追加
        nextForm.setRsv100KeyWord(form.getRsv100KeyWord());
        nextForm.setRsv100SearchCondition(form.getRsv100SearchCondition());
        nextForm.setRsv100TargetMok(form.getRsv100TargetMok());
        nextForm.setRsv100TargetNiyo(form.getRsv100TargetNiyo());
        nextForm.setRsv100CsvOutField(form.getRsv100CsvOutField());
        nextForm.setRsv100SelectedKey1(form.getRsv100SelectedKey1());
        nextForm.setRsv100SelectedKey2(form.getRsv100SelectedKey2());
        nextForm.setRsv100SelectedKey1Sort(form.getRsv100SelectedKey1Sort());
        nextForm.setRsv100SelectedKey2Sort(form.getRsv100SelectedKey2Sort());

        req.setAttribute("rsv040Form", nextForm);

        return map.findForward("kanrisya_settei");
    }

    /**
     * <br>[機  能] 個人設定画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doMoveKojin(ActionMapping map,
                                         Rsv100Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con) {

        Rsv140Form nextForm = new Rsv140Form();
        Rsv100Biz biz = new Rsv100Biz(con, getRequestModel(req));

        String convDspDate = "";
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        convDspDate = biz.convDspDate(paramMdl);
        paramMdl.setFormData(form);

        nextForm.setRsvDspFrom(convDspDate);
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV100);
        nextForm.setRsv100selectedFromYear(form.getRsv100selectedFromYear());
        nextForm.setRsv100selectedFromMonth(form.getRsv100selectedFromMonth());
        nextForm.setRsv100selectedFromDay(form.getRsv100selectedFromDay());
        nextForm.setRsv100selectedToYear(form.getRsv100selectedToYear());
        nextForm.setRsv100selectedToMonth(form.getRsv100selectedToMonth());
        nextForm.setRsv100selectedToDay(form.getRsv100selectedToDay());

        //追加
        nextForm.setRsv100KeyWord(form.getRsv100KeyWord());
        nextForm.setRsv100SearchCondition(form.getRsv100SearchCondition());
        nextForm.setRsv100TargetMok(form.getRsv100TargetMok());
        nextForm.setRsv100TargetNiyo(form.getRsv100TargetNiyo());
        nextForm.setRsv100CsvOutField(form.getRsv100CsvOutField());
        nextForm.setRsv100SelectedKey1(form.getRsv100SelectedKey1());
        nextForm.setRsv100SelectedKey2(form.getRsv100SelectedKey2());
        nextForm.setRsv100SelectedKey1Sort(form.getRsv100SelectedKey1Sort());
        nextForm.setRsv100SelectedKey2Sort(form.getRsv100SelectedKey2Sort());
        req.setAttribute("rsv140Form", nextForm);

        return map.findForward("kozin_settei");
    }

    /**
     * <br>[機  能] 施設設定画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doMoveIkkatu(ActionMapping map,
                                          Rsv100Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        //削除対象選択チェック
        ActionErrors errors = form.validateSelectCheck(req);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Rsv210Form nextForm = new Rsv210Form();
        Rsv100Biz biz = new Rsv100Biz(con, getRequestModel(req));

        String convDspDate = "";
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        convDspDate = biz.convDspDate(paramMdl);
        paramMdl.setFormData(form);

        nextForm.setRsvDspFrom(convDspDate);
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV100);
        nextForm.setRsv100selectedFromYear(form.getRsv100selectedFromYear());
        nextForm.setRsv100selectedFromMonth(form.getRsv100selectedFromMonth());
        nextForm.setRsv100selectedFromDay(form.getRsv100selectedFromDay());
        nextForm.setRsv100selectedToYear(form.getRsv100selectedToYear());
        nextForm.setRsv100selectedToMonth(form.getRsv100selectedToMonth());
        nextForm.setRsv100selectedToDay(form.getRsv100selectedToDay());
        //追加
        nextForm.setRsv100KeyWord(form.getRsv100KeyWord());
        nextForm.setRsv100SearchCondition(form.getRsv100SearchCondition());
        nextForm.setRsv100TargetMok(form.getRsv100TargetMok());
        nextForm.setRsv100TargetNiyo(form.getRsv100TargetNiyo());
        nextForm.setRsv100CsvOutField(form.getRsv100CsvOutField());
        nextForm.setRsv100SelectedKey1(form.getRsv100SelectedKey1());
        nextForm.setRsv100SelectedKey2(form.getRsv100SelectedKey2());
        nextForm.setRsv100SelectedKey1Sort(form.getRsv100SelectedKey1Sort());
        nextForm.setRsv100SelectedKey2Sort(form.getRsv100SelectedKey2Sort());

        req.setAttribute("rsv210Form", nextForm);

        return map.findForward("ikkatu_yoyaku");
    }


    /**
     * <br>[機  能] PDFファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイルの書き出しに失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws TempFileException 添付ファイル情報の取得に失敗
     * @throws Exception 実行例外
     */
    private ActionForward __doDownLoadPdf(ActionMapping map, Rsv100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException, TempFileException, Exception {

        log__.debug("ファイルダウンロード処理(施設予約一覧PDF)");

        RequestModel reqMdl = getRequestModel(req);

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String outTempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        Rsv100Biz biz = new Rsv100Biz(con, reqMdl);
        //PDF生成
        Rsv100ParamModel paramMdl = new Rsv100ParamModel();
        paramMdl.setParam(form);
        RsvListPdfModel pdfMdl = biz.createRsvListPdf(paramMdl, appRootPath, outTempDir);
        paramMdl.setFormData(form);

        String outBookName = pdfMdl.getFileName();
        String saveFileName = pdfMdl.getSaveFileName();
        String outFilePath = IOTools.setEndPathChar(outTempDir) + saveFileName;
        //ログ出力処理
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        GsMessage gsMsg = new GsMessage(req);
        String logCode =  "施設予約利用状況 PDF出力 " + form.getRsvSelectedGrpSid();
        rsvBiz.outPutLog(map, req, res, gsMsg.getMessage("cmn.pdf"), GSConstLog.LEVEL_TRACE,
                outBookName, logCode);
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        return null;
    }
}
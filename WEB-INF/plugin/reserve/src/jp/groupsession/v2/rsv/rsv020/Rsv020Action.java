package jp.groupsession.v2.rsv.rsv020;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.pdf.RsvNikPdfModel;
import jp.groupsession.v2.rsv.rsv010.Rsv010ParamModel;
import jp.groupsession.v2.rsv.rsv030.Rsv030Form;
import jp.groupsession.v2.rsv.rsv040.Rsv040Form;
import jp.groupsession.v2.rsv.rsv050.Rsv050Form;
import jp.groupsession.v2.rsv.rsv100.Rsv100Form;
import jp.groupsession.v2.rsv.rsv110.Rsv110Form;
import jp.groupsession.v2.rsv.rsv140.Rsv140Form;
import jp.groupsession.v2.rsv.rsv210.Rsv210Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約一覧 日間画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv020Action extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv020Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "rsv020";

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

        if (cmd.equals("pdf_nik")) {
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
        Rsv020Form rsvform = (Rsv020Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //施設設定ボタン押下
        if (cmd.equals("sisetu_settei")) {
            log__.debug("施設設定ボタン押下");
            forward = __doMoveSisetuSettei(map, rsvform, req, res, con);
        //施設利用状況照会ボタン押下
        } else if (cmd.equals("riyo_zyokyo_syokai")) {
            log__.debug("施設利用状況照会ボタン押下");
            forward = __doMoveSyokai(map, rsvform, req, res, con);
        //管理者設定ボタン押下
        } else if (cmd.equals("kanrisya_settei")) {
            log__.debug("管理者設定ボタン押下");
            forward = __doMoveKanri(map, rsvform, req, res, con);
        //個人設定ボタン押下
        } else if (cmd.equals("kozin_settei")) {
            log__.debug("個人設定ボタン押下");
            forward = __doMoveKojin(map, rsvform, req, res, con);
        //週間ボタン押下
        } else if (cmd.equals("syukan")) {
            log__.debug("週間ボタン押下");
            forward = map.findForward("syukan");
        //月間ボタン押下
        } else if (cmd.equals("gekkan")) {
            log__.debug("月間ボタン押下");
            forward = __doMoveGekkan(map, rsvform, req, res, con);
        //前日移動ボタン押下
        } else if (cmd.equals("zenzitu_ido")) {
            log__.debug("前日移動ボタン押下");
            __doMoveDays(rsvform, req, con, -1, false);
            forward = __doInit(map, rsvform, req, res, con);
        //今日ボタン押下
        } else if (cmd.equals("kyo")) {
            log__.debug("今日ボタン押下");
            __doMoveDays(rsvform, req, con, 0, true);
            forward = __doInit(map, rsvform, req, res, con);
        //翌日移動ボタン押下
        } else if (cmd.equals("yokuzitu_ido")) {
            log__.debug("翌日移動ボタン押下");
            __doMoveDays(rsvform, req, con, 1, false);
            forward = __doInit(map, rsvform, req, res, con);
        //施設予約登録ボタン押下
        } else if (cmd.equals("sisetu_add")) {
            log__.debug("施設予約登録ボタン押下");
            forward =
                __doMoveYoyaku(
                        map,
                        rsvform,
                        req,
                        res,
                        con,
                        GSConstReserve.PROC_MODE_SINKI);
        //施設予約編集リンククリック
        } else if (cmd.equals("sisetu_edit")) {
            log__.debug("施設予約編集リンククリック");
            forward =
                __doMoveYoyaku(
                        map,
                        rsvform,
                        req,
                        res,
                        con,
                        GSConstReserve.PROC_MODE_EDIT);
        //全て取消ボタン押下
        } else if (cmd.equals("allClear")) {
            log__.debug("全て取消ボタン押下");
            forward = __doAllClear(map, rsvform, req, res, con);
        //取消ボタン押下
        } else if (cmd.equals("clearHidSisetu")) {
            log__.debug("取消ボタン押下");
            forward = __doClearHidSisetu(map, rsvform, req, res, con);
        //一括予約ボタン押下
        } else if (cmd.equals("ikkatu_yoyaku")) {
            log__.debug("一括予約ボタン押下");
            forward = __doMoveIkkatu(map, rsvform, req, res, con);
        //一括予約完了時表示処理
        } else if (cmd.equals("rsv210finish")) {
            log__.debug("一括予約完了時表示処理");
            rsvform.setRsvIkkatuTorokuKey(null);
            forward = __doInit(map, rsvform, req, res, con);
        //検索ボタン押下
        } else if (cmd.equals("gotosearch")) {
            log__.debug("検索ボタン押下");
            forward = __doGoToSearch(map, rsvform, req, res, con);
        //PDF出力ボタン押下
        } else if (cmd.equals("pdf_nik")) {
            log__.debug("施設予約[日間] PDF出力ボタン押下");
            forward = __doDownLoadPdf(map, rsvform, req, res, con);
        //再読込
        } else if (cmd.equals("reload")) {
            log__.debug("再読込");
            forward = __doInit(map, rsvform, req, res, con);
          //絞り込み時
        } else if (cmd.equals("siborikomi")) {
            log__.debug("絞り込み時");
            forward = __doSiborikomi(map, rsvform, req, res, con);
        //絞り込み解除時
        } else if (cmd.equals("unfiltered")) {
            log__.debug("絞り込み解除時");
            forward = __doUnfiltered(map, rsvform, req, res, con);
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
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
                                    Rsv020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {

            con.setAutoCommit(true);
            RequestModel reqMdl = getRequestModel(req);
            Rsv020Biz biz = new Rsv020Biz(reqMdl, con);

            Rsv020ParamModel paramMdl = new Rsv020ParamModel();
            paramMdl.setParam(form);

            //管理者フラグを設定する
            biz.setAdmFlg(paramMdl);

            //施設グループの編集が可能か判定しフラグをセットする。
            biz.setGroupEditFlg(paramMdl);

            //絞り込みの初期設定
            if (paramMdl.getRsv010InitFlg() == 1) {
                biz.setNarrowDownData(paramMdl);
            }
            
            //コンボリストを設定する
            biz.setComboList(paramMdl);

            //施設予約情報一覧を取得する
            biz.setYoyakuDay(paramMdl);

            //自動リロード時間を設定する
            biz.set020Reloadtime(con, paramMdl, getSessionUserSid(req));

            paramMdl.setFormData(form);

            con.setAutoCommit(false);

            //施設データが存在する場合のみ画像データを取得
            if (form.getRsv020DaylyList() != null
                    && form.getRsv020DaylyList().size() > 0) {
                //テンポラリディレクトリパスを取得
                GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
                String tempDir = temp.getTempPath(getRequestModel(req),
                        GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

                paramMdl = new Rsv020ParamModel();
                paramMdl.setParam(form);
                biz.get020SisetuImgData(paramMdl, tempDir,
                        getAppRootPath(), getSessionUserSid(req));
                paramMdl.setFormData(form);

            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 絞り込みを行います
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
    private ActionForward __doSiborikomi(ActionMapping map,
            Rsv020Form form, HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {


        ActionErrors errors = form.validateCheckSiborikomi(req);
        //入力チェック
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        form.setParameter();
        form.setRsv010SiborikomiFlg(1);
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 絞り込み解除を行います
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
    private ActionForward __doUnfiltered(ActionMapping map,
            Rsv020Form form, HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {


        Rsv010ParamModel paramMdl = new Rsv010ParamModel();
        paramMdl.setParam(form);
        paramMdl.setRsvSelectedGrpSid(paramMdl.getRsv010grpNarrowDown());
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 表示日付の移動を行います
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param moveDay 移動日数
     * @param today 今日へ移動

     */
    private void __doMoveDays(Rsv020Form form,
                                HttpServletRequest req,
                                Connection con,
                                int moveDay,
                                boolean today) {

        Rsv020Biz biz = new Rsv020Biz(getRequestModel(req), con);

        Rsv020ParamModel paramMdl = new Rsv020ParamModel();
        paramMdl.setParam(form);
        biz.setMoveDspData(paramMdl, moveDay, today);
        paramMdl.setFormData(form);

    }

    /**
     * <br>[機  能] 選択施設を全て取り消す
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
    private ActionForward __doAllClear(ActionMapping map,
                                        Rsv020Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        form.setRsvIkkatuTorokuFlg(false);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 選択施設から指定された施設チェックを取り消す
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
    private ActionForward __doClearHidSisetu(ActionMapping map,
                                               Rsv020Form form,
                                               HttpServletRequest req,
                                               HttpServletResponse res,
                                               Connection con) throws Exception {

        //予約チェックがあれば除外
        String delKey = NullDefault.getString(form.getRsv020ClearTargetKey(), "");
        ArrayList<String> convKeyArray = new ArrayList<String>();
        String[] ikkatuKey = form.getRsvIkkatuTorokuKey();

        if (ikkatuKey != null && ikkatuKey.length > 0) {
            for (String key : ikkatuKey) {
                if (!key.equals(delKey)) {
                    convKeyArray.add(key);
                }
            }
            String[] convKeyStr = null;
            if (convKeyArray.isEmpty()) {
                convKeyStr = new String[0];
            } else {
                convKeyStr =
                    (String[]) convKeyArray.toArray(
                            new String[convKeyArray.size()]);
            }
            form.setRsvIkkatuTorokuKey(convKeyStr);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 検索ボタン押下時処理
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
    private ActionForward __doGoToSearch(ActionMapping map,
                                          Rsv020Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        ActionErrors errors = form.validateSearchCheck(req);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return __doMoveSyokai(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 施設利用状況照会画面へ移動
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
    private ActionForward __doMoveSyokai(ActionMapping map,
                                          Rsv020Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) {

        Rsv100Form nextForm = new Rsv100Form();
        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
//        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvSelectedSisetuSid(0);
        nextForm.setRsv100InitFlg(form.isRsv100InitFlg());
        nextForm.setRsv100SearchFlg(form.isRsv100SearchFlg());
        nextForm.setRsv100SortKey(form.getRsv100SortKey());
        nextForm.setRsv100OrderKey(form.getRsv100OrderKey());
        nextForm.setRsv100PageTop(form.getRsv100PageTop());
        nextForm.setRsv100PageBottom(form.getRsv100PageBottom());
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
        req.setAttribute("rsv100Form", nextForm);

        return map.findForward("riyo_zyokyo_syokai");
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
                                         Rsv020Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con) {

        Rsv040Form nextForm = new Rsv040Form();
        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV020);
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
                                         Rsv020Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con) {

        Rsv140Form nextForm = new Rsv140Form();
        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV020);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doMoveSisetuSettei(ActionMapping map,
                                                Rsv020Form form,
                                                HttpServletRequest req,
                                                HttpServletResponse res,
                                                Connection con) throws Exception {

        Rsv050Form nextForm = new Rsv050Form();
        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV020);
        req.setAttribute("rsv050Form", nextForm);

        return map.findForward("sisetu_settei");
    }

    /**
     * <br>[機  能] 月間画面へ移動
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
    private ActionForward __doMoveGekkan(ActionMapping map,
                                          Rsv020Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Rsv030Form nextForm = new Rsv030Form();
        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        req.setAttribute("rsv030Form", nextForm);

        return map.findForward("gekkan");
    }

    /**
     * <br>[機  能] 施設予約画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 処理区分
     * @return ActionForward フォワード
     */
    private ActionForward __doMoveYoyaku(ActionMapping map,
                                          Rsv020Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con,
                                          String mode) {

        Rsv110Form nextForm = new Rsv110Form();
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV020);
        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsv110ProcMode(mode);
        nextForm.setRsv110SinkiDefaultDate(form.getRsvSelectedDate());
        nextForm.setRsv110RsySid(form.getRsvSelectedYoyakuSid());
        nextForm.setRsv110RsdSid(form.getRsvSelectedSisetuSid());
        req.setAttribute("rsv110Form", nextForm);

        return map.findForward("yoyaku");
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
                                          Rsv020Form form,
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
        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSV020);
        req.setAttribute("rsv210Form", nextForm);

        return map.findForward("ikkatu_yoyaku");
    }

    /**
     * <br>[機  能] PDFファイルダウンロード処理を実行
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
    private ActionForward __doDownLoadPdf(ActionMapping map, Rsv020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException, TempFileException, Exception {

        //データ取得
        __doInit(map, form, req, res, con);

        RequestModel reqMdl = getRequestModel(req);

        log__.debug("ファイルダウンロード処理(PDF)");
        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String outTempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        Rsv020Biz biz = new Rsv020Biz(reqMdl, con);
        //PDF生成
        Rsv020ParamModel paramMdl = new Rsv020ParamModel();
        paramMdl.setParam(form);
        RsvNikPdfModel pdfMdl = biz.createRsvNikPdf(paramMdl, con, appRootPath, outTempDir);
        paramMdl.setFormData(form);

        String outBookName = pdfMdl.getRsvNikFileName();
        String saveFilneName = pdfMdl.getSaveNikFileName();
        String outFilePath = IOTools.setEndPathChar(outTempDir) + saveFilneName;
        //ログ出力処理
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        GsMessage gsMsg = new GsMessage(req);
        String logCode =  "日間 施設グループ PDF出力 " + form.getRsvSelectedGrpSid();
        rsvBiz.outPutLog(map, req, res, gsMsg.getMessage("cmn.pdf"), GSConstLog.LEVEL_TRACE,
                outBookName, logCode);
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        return null;
    }
}
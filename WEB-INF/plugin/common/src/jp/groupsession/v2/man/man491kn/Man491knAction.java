package jp.groupsession.v2.man.man491kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man440.Man440Biz;
import jp.groupsession.v2.man.man491.CybMycalCsvImport;
import jp.groupsession.v2.man.man491.Man491Biz;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] マイカレンダーインポート確認画面 Action
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man491knAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man491knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man491";


    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        //cmd = cmd.trim();

        if (cmd.equals("Man491kn_Back")) {
            // 戻るボタン押下
            forward = map.findForward("491kn_back");
        } else if (cmd.equals("Man491kn_Import")) {
            // 確定ボタン押下
            forward = __doCompImport(map, (Man491knForm) form, req, con);
        } else {
            forward = __doDsp(map, (Man491knForm) form, req, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     *
     * <br>[機  能] 描画処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doDsp(ActionMapping map, Man491knForm form,
           HttpServletRequest req, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        Man491knParamModel param = new Man491knParamModel();
        param.setParam(form);

        Man491Biz man491Biz = new Man491Biz(reqMdl, con);
        man491Biz.doDsp(param, tempDir);

        // -------------------------------------------------------
        //データ件数取得
        ActionErrors errors = new ActionErrors();
        CybMycalCsvImport csvImport = new CybMycalCsvImport(errors,
                                                            reqMdl,
                                                            con);
        String csvFile = csvImport.getCsvFilePath(tempDir);
        csvImport.isCsvDataOk(csvFile); // データ件数取得のために実行
        param.setMan491knImpCount(csvImport.getCount());
        // -------------------------------------------------------

        param.setFormData(form);

        ActionForward forward = map.getInputForward();

        return forward;
    }

    /**
     *
     * <br>[機  能] インポート確定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doCompImport(ActionMapping map, Man491knForm form,
            HttpServletRequest req, Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        CommonBiz cmnBiz = new CommonBiz();
        String appRootPath = this.getAppRootPath();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("491kn_back");
        }

        //取込み処理
        log__.debug("サイボウズLive - マイカレンダー取り込み開始");
        boolean commit = false;
        long num = 0;

        try {
            con.setAutoCommit(false);

            // インポートデータ解析 → スケジュール一覧データ作成
            CybMycalCsvImport csvImport = new CybMycalCsvImport(errors,
                                                                reqMdl,
                                                                con);
            num = csvImport.importCsv(tempDir);

            Man491Biz biz = new Man491Biz(reqMdl, con);
            biz.importScheduleList(csvImport.getSchList(), appRootPath);

            commit = true;

            //ログ出力
            GsMessage gsMsg = new GsMessage(reqMdl);
            String value = "";
            value += "[" + gsMsg.getMessage("cmn.capture.item.count") + "] " + num;
            String dspName = gsMsg.getMessage("cmn.preferences2") + " "
                    + gsMsg.getMessage("main.man491kn.1");
            cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                    getInterMessage(reqMdl, "cmn.import"),
                    GSConstLog.LEVEL_INFO, value, dspName);

        } catch (Exception e) {
            log__.error("マイカレンダーCSVの取り込みに失敗しました。", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }

            //テンポラリディレクトリのファイル削除を行う
            GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                    GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
            Man440Biz biz = new Man440Biz(getRequestModel(req));
            biz.doDeleteFile(tempMdl);
        }

        MessageResources msgRes = getResources(req);
        String msgStr = msgRes.getMessage("touroku.kanryo.object",
                getInterMessage(req, "schedule.108"));

        if (!commit) {
            // インポート失敗
            msgStr = msgRes.getMessage("touroku.kanryo.object",
                    getInterMessage(req, "main.man491.1"));
        }

        //完了画面遷移
        return __doImportComp(map, req, msgStr);
    }

    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param msgStr メッセージ
     * @return アクションフォーワード
     */
    private ActionForward __doImportComp(ActionMapping map,
                                         HttpServletRequest req,
                                         String msgStr) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("menu");
        cmn999Form.setUrlOK(urlForward.getPath());

        cmn999Form.setMessage(msgStr);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}

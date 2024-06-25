package jp.groupsession.v2.man.man450kn;

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
import jp.groupsession.v2.man.man450.CybMemberCsvImport;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] サイボウズLiveデータ移行 メンバー名簿インポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man450knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man450knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man450";

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
                                        Connection con)
        throws Exception {

        ActionForward forward = null;
        Man450knForm mform = (Man450knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("Man450kn_Back")) {
            //戻るボタン押下
            log__.debug("戻るボタン押下");
            forward = map.findForward("450kn_back");
        } else if (cmd.equals("Man450kn_Import")) {
            //実行ボタン押下
            log__.debug("実行ボタン押下");
            forward = __doImport(map, mform, req, res, con);
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, mform, req, res, con);
        }

        return forward;
    }


    /**
     * <br>[機  能] 確認画面の表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Man450knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        Man440Biz biz = new Man440Biz(reqMdl);
        form.setMan450knFileName(biz.getFileName(tempDir));

        //取込み予定情報を取得
        ActionErrors errors = new ActionErrors();
        CybMemberCsvImport csvImport = new CybMemberCsvImport(errors,
                                                              reqMdl,
                                                              con,
                                                              form.getMan440GrpSid(),
                                                              form.getMan450password(),
                                                              form.getMan450passUpdateFlg());
        //テンポラリディレクトリにあるファイル名称を取得
        String csvFile = csvImport.getCsvFilePath(tempDir);
        csvImport.isCsvDataOk(csvFile); // データ件数取得のために実行
        form.setMan450knImpCount(csvImport.getCount());

        return map.getInputForward();
    }

    /**
     * <br>[機  能] CSVインポート処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doImport(ActionMapping map,
                                      Man450knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        CommonBiz cmnBiz = new CommonBiz();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        //再入力チェック
        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
        if (errors.size() > 0) {
            log__.debug("再チェックNG");
            addErrors(req, errors);
            return map.getInputForward();
        }

        //取込み処理
        log__.debug("サイボウズLive - メンバー名簿取り込み開始");
        boolean commit = false;
        long num = 0;

        try {
            con.setAutoCommit(false);
            CybMemberCsvImport csvImport = new CybMemberCsvImport(errors,
                                                                  reqMdl,
                                                                  con,
                                                                  form.getMan440GrpSid(),
                                                                  form.getMan450password(),
                                                                  form.getMan450passUpdateFlg());

            num = csvImport.importCsv(tempDir, getPluginConfig(req));

            commit = true;

            //ログ出力
            GsMessage gsMsg = new GsMessage(reqMdl);
            String value = "";
            value += "[" + gsMsg.getMessage("cmn.capture.item.count") + "] " + num;
            String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("main.man450kn.1");
            cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                    getInterMessage(reqMdl, "cmn.import"),
                    GSConstLog.LEVEL_INFO, value, dspName);

        } catch (Exception e) {
            log__.error("メンバー名簿CSVの取り込みに失敗しました。", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }

            //テンポラリディレクトリのファイル削除を行う
            Man440Biz biz = new Man440Biz(reqMdl);
            GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                    GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
            biz.doDeleteFile(tempMdl);
        }

        MessageResources msgRes = getResources(req);
        String msgStr = msgRes.getMessage("touroku.kanryo.object",
                getInterMessage(req, "cmn.user"));

        if (!commit) {
            // インポート失敗
            msgStr = msgRes.getMessage("touroku.kanryo.object",
                    getInterMessage(req, "main.man450.1"));
        }

        //完了画面遷移
        return __doImportComp(map, req, form, msgStr);
    }

    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param form フォーム
     * @param msgStr メッセージ
     * @return アクションフォーワード
     * @throws Exception Exception
     */
    private ActionForward __doImportComp(ActionMapping map,
                                         HttpServletRequest req,
                                         Man450knForm form,
                                         String msgStr) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("cybozuConvertMenu");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgStr);
        cmn999Form.addHiddenAll(form, form.getClass(), "");

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}

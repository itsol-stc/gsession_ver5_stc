package jp.groupsession.v2.tcd.tcd210kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardSubAction;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード 有休日数インポート確認画面のアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210knAction extends AbstractTimecardSubAction {

    /** テンポラリディレクトリID */
    private static final String TEMP_DIRECTORY_ID = "tcd210";

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        
        Tcd210knForm thisForm = (Tcd210knForm) form;
        TimecardBiz tcBiz = new TimecardBiz();
        //権限チェック
        if (!tcBiz.isAccessOk(req, con)) {
            __setAccessErrorParam(map, req, thisForm);
            return map.findForward("gf_msg");
        }
        
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        ActionForward forward = null;
        
        if (cmd.equals("tcd210knCommit")) {
            //確認ボタン押下時処理
            forward = __doImport(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd210knBack")) {
            //戻るボタン押下時処理
            forward = map.findForward("tcd210knback");
        } else {
            //画面表示処理
            forward = __doDsp(map, thisForm, req, res, con);
        }
        
        return forward;
    }
    
    /**
     * <br>[機  能] 画面表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDsp(
            ActionMapping map,
            Tcd210knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        
        //テンポラリディレクトリファイルの初期化
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        
        RequestModel reqMdl = getRequestModel(req);
        //入力チェック
        ActionErrors errors = form.validateCheck(map, reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }
        
        //表示項目の設定
        Tcd210knParamModel paramMdl = new Tcd210knParamModel();
        paramMdl.setParam(form);
        Tcd210knBiz biz = new Tcd210knBiz();
        biz._setInitData(paramMdl, tempDir, con);
        paramMdl.setFormData(form);
        
        //画面の再表示
        return map.getInputForward();
    }
    
    /**
     * <br>[機  能] CSVファイルのインポートを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doImport(
            ActionMapping map,
            Tcd210knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        
        //2重投稿チェック
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }
        
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        
        RequestModel reqMdl = getRequestModel(req);
        //入力チェック
        ActionErrors errors = form.validateCheck(map, reqMdl, tempDir, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.getInputForward();
        }
        
        boolean commit = false;
        try {
            //インポート
            Tcd210knImportCsv imp = new Tcd210knImportCsv(con, reqMdl);
            long num = imp._importCsv(tempDir);
            //タイトル分減算
            num--;

            GsMessage gsMsg = new GsMessage();
            /** メッセージ インポート **/
            String strImport = gsMsg.getMessage(req, "cmn.import");

            //ログ出力処理
            TimecardBiz tcdBiz = new TimecardBiz();
            tcdBiz.outPutTimecardLog(
                    map, reqMdl, con, strImport, GSConstLog.LEVEL_INFO, "[count]" + num);

            commit = true;

            //完了画面遷移
            return __doImportComp(map, form, req, res, con);

        } catch (Exception e) {
            throw e;
        } finally {

            //テンポラリディレクトリのファイル削除を行う
            temp.deleteTempPath(getRequestModel(req),
                    GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }
    
    /**
     * <br>[機  能] タイムカードインポート完了後の画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doImportComp(ActionMapping map,
                                          Tcd210knForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("importComp");
        cmn999Form.setUrlOK(forwardOk.getPath());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //有休日数
        String textYukyuDays = gsMsg.getMessage(req, "tcd.210");
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", textYukyuDays));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("tcdBackScreen", form.getTcdBackScreen());
        cmn999Form.addHiddenParam("tcd190group", form.getTcd190group());
        cmn999Form.addHiddenParam("tcd190nendo", form.getTcd190nendo());
        cmn999Form.addHiddenParam("tcd190sortKey", form.getTcd190sortKey());
        cmn999Form.addHiddenParam("tcd190order", form.getTcd190order());
        cmn999Form.addHiddenParam("tcd190page", form.getTcd190page());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 権限エラー画面遷移時
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setAccessErrorParam(
        ActionMapping map,
        HttpServletRequest req,
        Tcd210knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        urlForward = map.findForward("gf_menu");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "tcd.143");
        String msg2 = gsMsg.getMessage(req, "tcd.144");

        //メッセージセット
        String msgState = "error.edit.power.user";
        String key1 = msg;
        String key2 = msg2;
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1, key2));

        req.setAttribute("cmn999Form", cmn999Form);
    }
}

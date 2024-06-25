package jp.groupsession.v2.tcd.tcd210;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardSubAction;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード 有休日数インポート画面のアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210Action extends AbstractTimecardSubAction {
    
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
        
        Tcd210Form thisForm = (Tcd210Form) form;
        TimecardBiz tcBiz = new TimecardBiz();
        //権限チェック
        if (!tcBiz.isAccessOk(req, con)) {
            __setAccessErrorParam(map, req, thisForm);
            return map.findForward("gf_msg");
        }
        
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        ActionForward forward = null;
        
        if (cmd.equals("tcd210import")) {
            //確定ボタン押下時
            forward = __doCommit(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd210back")) {
            //戻るボタン押下時
            forward = __doBack(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd210delete")) {
            //削除ボタン押下時
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd210_sample")) {
            //サンプルCSVリンク押下時処理
            __doSampleDownLoad(req, res);
            forward = __doDsp(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd210knBack")) {
            //確認画面戻るボタン押下時処理
            forward = __doDsp(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
     * @throws IOToolsException 読取ファイル操作実行時例外
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInit(
            ActionMapping map,
            Tcd210Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws IOToolsException, SQLException {
        
        //テンポラリディレクトリファイルの初期化
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        temp.createTempDir(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        
        //画面の再表示
        return __doDsp(map, form, req, res, con);
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
     * @throws IOToolsExcetption 取込みファイル操作時例外
     */
    private ActionForward __doDsp(
            ActionMapping map,
            Tcd210Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws IOToolsException {
        
        Tcd210ParamModel paramMdl = new Tcd210ParamModel();
        paramMdl.setParam(form);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        Tcd210Biz biz = new Tcd210Biz();
        biz._setInitData(paramMdl, tempDir);
        paramMdl.setFormData(form);
        
        return map.getInputForward();
    }
    
    /**
     * <br>[機  能] インポートボタン押下時処理
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
    private ActionForward __doCommit(
            ActionMapping map,
            Tcd210Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        
        RequestModel reqMdl = getRequestModel(req);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        
        //入力チェック
        ActionErrors errors = form.validateCheck(map, reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        
        //トランザクショントークンの保存
        saveToken(req);
        
        return map.findForward("tcd210commit");
    }
    
    /**
     * <br>[機  能] インポートボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doBack(
            ActionMapping map,
            Tcd210Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) {
        
        //テンポラリディレクトリの削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        
        //有休日数一覧画面へ遷移
        return map.findForward("tcd210back");
    }
    
    /**
     * <br>[機  能] サンプルCSVをダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @throws Exception ダウンロード時例外
     */
    private void __doSampleDownLoad(HttpServletRequest req, HttpServletResponse res)
        throws Exception {

        String fileName = GSConstTimecard.SAMPLE_YUKYU_CSV_NAME;
        
        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstTimecard.PLUGIN_ID_TIMECARD);
        buf.append(File.separator);
        buf.append("doc");
        buf.append(File.separator);
        buf.append(fileName);
        String fullPath = buf.toString();
        TempFileUtil.downloadAtachment(req, res, fullPath,
                                        "有休インポートサンプル.xlsx", Encoding.UTF_8);
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
        Tcd210Form form) {

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

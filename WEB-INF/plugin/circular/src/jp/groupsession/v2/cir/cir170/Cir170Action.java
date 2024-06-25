package jp.groupsession.v2.cir.cir170;

import java.io.File;
import java.sql.Connection;

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
import jp.groupsession.v2.cir.AbstractCircularSubAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;

/**
 * <br>[機  能] 回覧板 アカウントインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir170Action extends AbstractCircularSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir170Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "cir170";

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //ダウンロードフラグ
        if (cmd.equals("import_account")) {
                return true;
        }
        return false;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Cir170Form thisForm = (Cir170Form) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();

        if (cmd.equals("importAccountConfirm")) {
            // トランザクショントークン設定
            saveToken(req);
            //インポートボタンクリック
            forward = __doImport(map, thisForm, req, res, con);

        } else if (cmd.equals("beforePage")) {
            //戻るボタンクリック
            temp.deleteTempPath(getRequestModel(req),
                    GSConstCircular.PLUGIN_ID_CIRCULAR, TEMP_DIRECTORY_ID);
            forward = map.findForward("beforePage");

        } else if (cmd.equals("import_account")) {
            //import_account.xlsリンククリック
            //ログ出力
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            cirBiz.outPutLog(map, getRequestModel(req),
                    getInterMessage(req, "cmn.download"), GSConstLog.LEVEL_INFO,
                    GSConstCircular.SAMPLE_CSV_FILE_NAME);
            __doSampleDownLoad(req, res);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
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
    private ActionForward __doInit(
        ActionMapping map,
        Cir170Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        if (form.getCir170InitFlg() == 0) {
            temp.deleteTempPath(getRequestModel(req), GSConstCircular.PLUGIN_ID_CIRCULAR,
                    TEMP_DIRECTORY_ID);
            temp.createTempDir(getRequestModel(req), GSConstCircular.PLUGIN_ID_CIRCULAR,
                    TEMP_DIRECTORY_ID);
        }
        //テンポラリディレクトリパスを取得
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstCircular.PLUGIN_ID_CIRCULAR, TEMP_DIRECTORY_ID);

        //初期表示情報を取得する
        Cir170ParamModel paramMdl = new Cir170ParamModel();
        paramMdl.setParam(form);
        Cir170Biz biz = new Cir170Biz();
        biz.getInitData(paramMdl, tempDir);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] インポートボタンクリック時の処理
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
    private ActionForward __doImport(
        ActionMapping map,
        Cir170Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        con.setAutoCommit(true);

        //テンポラリディレクトリ作成
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstCircular.PLUGIN_ID_CIRCULAR, TEMP_DIRECTORY_ID);
        //入力チェック
        ActionErrors errors = form.validateCheck(con, req, tempDir);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);
        return map.findForward("importAccountConfirm");
    }

    /**
     * <br>[機  能] サンプルCSVをダウンロードします。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @throws Exception ダウンロード時の例外
     */
    private void __doSampleDownLoad(HttpServletRequest req, HttpServletResponse res)
    throws Exception {

        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstCircular.PLUGIN_ID_CIRCULAR);
        buf.append(File.separator);
        buf.append("templete");
        buf.append(File.separator);
        buf.append(GSConstCircular.SAMPLE_CSV_FILE_NAME);
        String fullPath = buf.toString();
        log__.debug("FULLPATH=" + fullPath);
        TempFileUtil.downloadAtachment(req, res, fullPath,
                                    GSConstCircular.SAMPLE_CSV_FILE_NAME,
                                    Encoding.UTF_8);
    }
}

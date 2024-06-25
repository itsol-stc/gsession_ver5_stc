package jp.groupsession.v2.anp.anp121;

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
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 管理者設定・緊急連絡先インポート画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp121Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp121Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "anp121";

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
        String downLoadFlg = NullDefault.getString(req.getParameter("sample"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("anp121downloadCsv")) {
            if (downLoadFlg.equals("1")) {
                log__.debug("サンプルCSVファイルダウンロード");
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォアード
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
                                throws Exception {

        ActionForward forward = null;
        Anp121Form uform = (Anp121Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp121back")) {
            //戻る
            forward = map.findForward("back");
            __doTenpDelete(map, uform, req, res, con);

        } else if (cmd.equals("anp121knback")) {
            //確認画面からの戻り
            forward = __doDisp(map, uform, req, res, con);

        } else if (cmd.equals("anp121import")) {
            //インポート（確認画面→完了メッセージ→一覧）
            forward = __doExcute(map, uform, req, res, con);

        } else if (cmd.equals("anp121downloadCsv")) {
            //緊急連絡先取込み用csvファイルクリック
            __doDownLoadCsv(map, uform, req, res, con);
        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期化処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Anp121Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
                            throws Exception {

        //テンポラリディレクトリを削除
        __doTenpDelete(map, form, req, res, con);

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.createTempDir(getRequestModel(req),
                GSConstAnpi.PLUGIN_ID, TEMP_DIRECTORY_ID);

        return __doDisp(map, form, req, res, con);
    }

    /**
    * <br>[機  能] 再表示を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map アクションマッピング
    * @param form アクションフォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @throws Exception 実行例外
    * @throws IOToolsException ファイルアクセス時例外
    * @return ActionForward
    */
   private ActionForward __doDisp(ActionMapping map,
                                  Anp121Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                           throws Exception, IOToolsException {

       //テンポラリディレクトリパスを取得
       GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
       String tempDir = temp.getTempPath(getRequestModel(req),
               GSConstAnpi.PLUGIN_ID, TEMP_DIRECTORY_ID);
       log__.debug("テンポラリディレクトリ = " + tempDir);

       //取込みファイルコンボを設定
       AddressBiz addressBiz = new AddressBiz(getRequestModel(req));
       form.setAnp121fileCombo(addressBiz.getFileCombo(tempDir));

       //トランザクショントークン設定
       saveToken(req);

       return map.getInputForward();
   }

    /**
     * <br>[機  能] 確認画面へ遷移
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doExcute(ActionMapping map,
                                      Anp121Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
                               throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();

        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAnpi.PLUGIN_ID, TEMP_DIRECTORY_ID);
        log__.debug("テンポラリディレクトリパスを取得" + tempDir);

        //チェック処理
        ActionErrors errors = form.validateAnp121(map, req, getRequestModel(req), tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDisp(map, form, req, res, con);
        }

        //確認画面
        return map.findForward("excute");
    }

    /**
     * <br>[機  能] テンポラリディレクトリ以下ファイルの削除
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doTenpDelete(ActionMapping map,
                                Anp121Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                         throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstAnpi.PLUGIN_ID, TEMP_DIRECTORY_ID);
    }

    /**
     * <br>[機  能] インポート用ファイルをダウンロードします。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception ダウンロード時の例外
     */
    private void __doDownLoadCsv(
            ActionMapping map,
            Anp121Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //インポートファイルのフルパスを作成
        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstAnpi.PLUGIN_ID);
        buf.append(File.separator);
        buf.append(GSConstAnpi.IMPORT_CSV_SAVEDIR);
        buf.append(File.separator);
        buf.append(GSConstAnpi.IMPORT_CSV_SAMPLE);
        String fullPath = buf.toString();

        log__.debug("FULLPATH=" + fullPath);
        TempFileUtil.downloadAtachment(req, res, fullPath,
                                       GSConstAnpi.IMPORT_CSV_SAMPLE, Encoding.UTF_8);

        //ログ出力
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        GsMessage gsMsg = new GsMessage();
        String opCode = gsMsg.getMessage("cmn.download");
        String value = GSConstAnpi.IMPORT_CSV_SAMPLE;
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, value);

    }
}

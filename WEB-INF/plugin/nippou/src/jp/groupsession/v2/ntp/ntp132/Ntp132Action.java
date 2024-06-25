package jp.groupsession.v2.ntp.ntp132;

import java.io.File;
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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.AbstractNippouMstmntAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 商品インポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp132Action extends AbstractNippouMstmntAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp132Action.class);

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     *
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("ntp132_sample")) {
            log__.debug("サンプルCSVファイルダウンロード");
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
                                        Connection con)
        throws Exception {
        log__.debug("START 132Action");

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp132Form thisForm = (Ntp132Form) form;

        //戻るボタン
        if (cmd.equals("backNtp132")) {
            log__.debug("戻るボタン");
            forward = map.findForward("ntp130");

            //テンポラリディレクトリを削除
            Ntp132Biz biz = new Ntp132Biz(getRequestModel(req));
            biz.deleteTempDir();

        //サンプルダウンロードリンククリック
        } else if (cmd.equals("ntp132_sample")) {
            log__.debug("サンプルダウンロードリンククリック");
            __doSampleDownLoad(map, req, res, con);
        //インポートボタン
        } else if (cmd.equals("importFile")) {
            forward = __doKakunin(map, thisForm, req, res, con);
        //確認画面からの再表示(テンポラリディレクトリ削除処理無し)
        } else if (cmd.equals("reDisp")) {
            forward = __doDsp(map, thisForm, req, res, con);
        //初期表示
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END 132Action");
        return forward;
    }

    /**
     * <br>[機  能] 確認画面へ遷移Action
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
    private ActionForward __doKakunin(ActionMapping map,
                                       Ntp132Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = map.findForward("ntp132kn");

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Ntp132Biz biz = new Ntp132Biz(reqMdl);
        String tempDir = biz.getTempDir();

        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(map, req, tempDir, con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        return forward;
    }



    /**
     * <br>[機  能] 初期パラメータ設定
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
                                    Ntp132Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //テンポラリディレクトリを初期化
        RequestModel reqMdl = getRequestModel(req);
        Ntp132Biz biz = new Ntp132Biz(reqMdl);
        biz.clearTempDir();

        return __doDsp(map, form, req, res, con);
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
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @return ActionForward
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Ntp132Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //初期表示情報を画面にセットする
        Ntp132Biz biz = new Ntp132Biz(getRequestModel(req));

        Ntp132ParamModel paramMdl = new Ntp132ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);


        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] サンプルCSVをダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception ダウンロード時例外
     */
    private void __doSampleDownLoad(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws Exception {

        String fileName = GSConstNippou.SAMPLE_NTP_CSV_SHOHIN;

        StringBuffer buf = new StringBuffer();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstNippou.PLUGIN_ID_NIPPOU);
        buf.append(File.separator);
        buf.append("doc");
        buf.append(File.separator);
        buf.append(fileName);
        String fullPath = buf.toString();

        GsMessage gsMsg = new GsMessage();
        /** メッセージ ダウンロード **/
        String download = gsMsg.getMessage("cmn.download");
        //ログ出力
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        ntpBiz.outPutLog(
                map, req, res,
                download, GSConstLog.LEVEL_INFO, fileName);
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);
    }
}

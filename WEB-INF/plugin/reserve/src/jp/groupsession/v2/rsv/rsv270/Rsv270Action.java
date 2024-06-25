package jp.groupsession.v2.rsv.rsv270;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.AbstractReserveSubAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 グループ・施設一括出力画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv270Action extends AbstractReserveSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv270Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "rsv270";

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
        Rsv270Form rsvform = (Rsv270Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //エクスポートボタン押下
        if (cmd.equals("export")) {
            log__.debug("エクスポートボタン押下");
            forward = __doExport(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_sisetu_group_settei")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_to_sisetu_group_settei");
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
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 取込みファイル操作時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rsv270Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException, IOToolsException {

        con.setAutoCommit(true);
        Rsv270Biz biz = new Rsv270Biz(getRequestModel(req), con);

        //処理権限判定
        if (!biz.isPossibleToProcess()) {
            //処理権限無し
            return getSubmitErrorPage(map, req);
        }

        //施設区分コンボセット
        Rsv270ParamModel paramMdl = new Rsv270ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] エクスポートボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
    * @throws Exception インポート処理時例外
     */
    private ActionForward __doExport(ActionMapping map,
                                           Rsv270Form form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con)
        throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        //CSV作成
        String fileName = GSConstReserve.RSV_CSV_NAME_GROUP_ALL;
        Rsv270CsvWriter writer = new Rsv270CsvWriter(con, form, req);
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
}
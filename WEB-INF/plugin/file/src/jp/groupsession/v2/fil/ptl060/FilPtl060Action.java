package jp.groupsession.v2.fil.ptl060;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.fil.AbstractFileSubAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>ポータル ファイル一覧管理 フォルダ情報画面Action
 *
 *  @author JTS
 */
public class FilPtl060Action extends AbstractFileSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl060Action.class);

    /**
     * プラグインIDを取得します
     *
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return GSConstPortal.PLUGIN_ID;
    }

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        log__.debug("filptl060Action START");

        ActionForward forward = null;
        FilPtl060Form thisForm = (FilPtl060Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("filchangeCombo")) {
        //プラグインポートレットコンボ変更
        forward = __changeCombo(map, thisForm, req, res, con);

        } else if (cmd.equals("filptl060back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("backList")) {
            // 閉じるボタンクリック
            forward = map.findForward("ptlList");

        } else if (cmd.equals("insertPtl")) {
            // ポートレット登録
            forward = __insert(map, thisForm, req, res, con);

        } else if (cmd.equals("detailDir")) {
            //表示ディレクトリ変更
            forward = __doInit(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     */
    private ActionForward __doInit(ActionMapping map,
                                    FilPtl060Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOToolsException, IOException  {

        con.setAutoCommit(true);

        FilPtl060Biz biz = new FilPtl060Biz(con, getRequestModel(req));
        FilPtl060ParamModel paramMdl = new FilPtl060ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }


    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, FilPtl060Form form) {

        ActionForward forward = null;

        if (form.getBackDsp().equals(GSConstFile.MOVE_TO_MAIN)) {
            forward = map.findForward(GSConstFile.MOVE_TO_MAIN);
        } else {
            forward = map.findForward("filptl050");
        }

        return forward;
    }


    /**
     * <br>[機  能] ポートレット登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException 実行例外
     * @throws GSException 実行例外
     * @return ActionForward フォワード
     */
    private ActionForward __insert(ActionMapping map,
            FilPtl060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)

            throws SQLException, GSException {

            FilPtl060Biz biz = new FilPtl060Biz(con, getRequestModel(req));
            boolean commit = false;

            BaseUserModel usModel = getSessionUserModel(req);
            if (usModel == null) {
                throw new GSAuthenticateException("ユーザ情報の取得に失敗");
            }

            PluginConfig pconfig = getPluginConfig(req);

            try {

                //登録処理
                FilPtl060ParamModel paramMdl = new FilPtl060ParamModel();
                paramMdl.setParam(form);
                biz.insertData(paramMdl, pconfig);
                paramMdl.setFormData(form);

                con.commit();
                commit = true;
            } catch (SQLException e) {
                log__.error("プラグイン追加処理エラー", e);
                throw e;
            } finally {
                if (!commit) {
                    con.rollback();
                }
            }

            GsMessage gsMsg = new GsMessage();

            //ログ出力処理
            FilCommonBiz filCmnBiz = new FilCommonBiz(getRequestModel(req), con);

            String opCode = gsMsg.getMessage(req, "cmn.entry");
            String value = gsMsg.getMessage("plugin.portlet");
            String dspName = gsMsg.getMessage("ptl.ptl040.1")
                    + " " + gsMsg.getMessage("fil.ptl050.1");

            filCmnBiz.outPutLogNoDspName(
                    opCode, GSConstLog.LEVEL_INFO, value, map.getType(), null, dspName);

            return map.getInputForward();
        }


    /**
     * <br>
     * [機 能] プラグインポートレットコンボ変更時の処理 <br>
     * [解 説] <br>
     * [備 考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __changeCombo(ActionMapping map, FilPtl060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        String screenId = form.getPtl080PluginPortlet();
        if (StringUtil.isNullZeroString(screenId)) {
            return map.getInputForward();
        }

        return map.findForward(screenId);

    }

  }

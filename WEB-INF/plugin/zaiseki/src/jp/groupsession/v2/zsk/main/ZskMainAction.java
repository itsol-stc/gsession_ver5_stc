package jp.groupsession.v2.zsk.main;

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

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] 在席管理(メイン画面表示用 本人)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskMainAction extends AbstractZaisekiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZskMainAction.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <p>
     * アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        int sessionUsrSid = getRequestModel(req).getSmodel().getUsrsid();
        //adminユーザの在席ステータス変更を制御
        if (cmd.equals("zskEdit")
                && sessionUsrSid == 0) {
            return getSubmitErrorPage(map, req);
        }

        ZskMainForm zskForm = (ZskMainForm) form;
        if (cmd.equals("zskEdit")) {
            //在席状況修正
            forward = __doZskEdit(map, zskForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * 初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, ZskMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        BaseUserModel umodel = getSessionUserModel(req);
        ZskMainBiz biz = new ZskMainBiz(getRequestModel(req));
        PluginConfig pconfig = getPluginConfig(req);

        ZskMainParamModel paramMdl = new ZskMainParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con, pconfig, umodel);
        paramMdl.setFormData(form);

        form.setZskTopUrl(getPluginConfig(req).getPlugin(
                GSConstZaiseki.PLUGIN_ID_ZAISEKI).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 在席管理 設定ボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    public ActionForward __doZskEdit(ActionMapping map,
                                      ZskMainForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            //入力チェック
            ActionErrors errors = form.validateChkMan001(req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return map.findForward("gf_main");
            }

            //在席情報更新
            ZskMainBiz biz = new ZskMainBiz(getRequestModel(req));

            ZskMainParamModel paramMdl = new ZskMainParamModel();
            paramMdl.setParam(form);
            biz.updateZskStatus(paramMdl, con);
            paramMdl.setFormData(form);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        GsMessage gsMsg = new GsMessage(req);
        String msg = gsMsg.getMessage("cmn.change");

        int status = form.getZskUioStatus();

        //ログ出力
        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        String opLog = cmnBiz.opLogContent(getRequestModel(req).getSmodel().getUsrsid(),
                status, con);
        cmnBiz.outPutLog(con,
                msg, GSConstLog.LEVEL_TRACE,
                opLog, map.getType());

        //メイン画面リロード(Enterキー押下時)
        if (form.getMainReDspFlg() == GSConstZaiseki.MAIN_RELOAD_ON) {
            return map.findForward("redraw");
        } else {
            return __doInit(map, form, req, res, con);
        }
    }
}

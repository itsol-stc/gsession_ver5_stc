package jp.groupsession.v2.man.man480kn;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man440.Man440Biz;
import jp.groupsession.v2.man.man480.Man480Biz;
import jp.groupsession.v2.man.man480.Man480Form;
import jp.groupsession.v2.man.man480.Man480ParamModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] CybozuLive Todoリストインポート確認画面 Action
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man480knAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man480knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man480";

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("Man480kn_Back")) {
            // 戻るボタン押下
            forward = map.findForward("480kn_back");
        } else if (cmd.equals("Man480kn_Import")) {
            // 確定ボタン押下
            forward = __doCompImport(map, (Man480knForm) form, req, con);
        } else {
            forward = __doDsp(map, (Man480Form) form, req, con);
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
   private ActionForward __doDsp(ActionMapping map, Man480Form form,
           HttpServletRequest req, Connection con) throws Exception {
       RequestModel reqMdl = getRequestModel(req);
       //テンポラリディレクトリパスを取得
       GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
       String tempDir = temp.getTempPath(getRequestModel(req),
               GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

       form.setMan480fileOk(true);

       Man480ParamModel param = new Man480ParamModel();
       param.setParam(form);

       Man480Biz man480Biz = new Man480Biz(reqMdl, con);

       man480Biz.doDsp(param, tempDir);
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
    private ActionForward __doCompImport(ActionMapping map, Man480knForm form,
            HttpServletRequest req, Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        //テンポラリディレクトリパスを取得
        CommonBiz cmnBiz = new CommonBiz();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("480kn_back");
        }
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean success = false;
        try {
            Man480ParamModel param = new Man480ParamModel();
            param.setParam(form);
            Man480Biz man480Biz = new Man480Biz(reqMdl, con);
            //採番コントローラ
            MlCountMtController cntCon = getCountMtController(req);

            long num = man480Biz.doInsert(param, cntCon, tempDir);
            success = true;

            //ログ出力
            GsMessage gsMsg = new GsMessage(reqMdl);
            String value = "";
            value += "[" + gsMsg.getMessage("cmn.capture.item.count") + "] " + num;

            value += "\r\n[" + gsMsg.getMessage("main.man480.2") + "]";
            if (form.getMan480mode() == GSConst.TARGET_NEW_PROJECT) {
                value += gsMsg.getMessage("main.man480.3");
                value += "\r\n" + gsMsg.getMessage("project.31")
                              + " : " + form.getMan480projectID();
                value += "\r\n" + gsMsg.getMessage("project.40")
                              + " : " + form.getMan480projectName();
                value += "\r\n" + gsMsg.getMessage("project.41")
                              + " : " + form.getMan480projectShortName();
            } else if (form.getMan480mode() == GSConst.TARGET_OLD_PROJECT) {
                value += gsMsg.getMessage("main.man480.4");
                String projectName = man480Biz.getProjectName(form.getMan480selectProject(), con);
                value += "\r\n" + gsMsg.getMessage("project.40") + " : " + projectName;
            }
            String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("main.man480kn.1");
            cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                    getInterMessage(reqMdl, "cmn.import"),
                    GSConstLog.LEVEL_INFO, value, dspName);

            //テンポラリディレクトリのファイル削除を行う
            GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                    GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
            Man440Biz biz = new Man440Biz(getRequestModel(req));
            biz.doDeleteFile(tempMdl);

            con.commit();
        } finally {
            if (!success) {
                JDBCUtil.rollback(con);
            }
        }
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("cybozuConvertMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(
                msgRes.getMessage(msgState,
                        getInterMessage(req, "project.src.86")));
        cmn999Form.addHiddenAll(form, form.getClass(), "");

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

}

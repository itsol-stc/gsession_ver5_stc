package jp.groupsession.v2.man.man480;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man440.Man440Biz;
import jp.groupsession.v2.struts.AbstractGsAction;
/**
 *
 * <br>[機  能] CybozuLive Todoリストインポート画面 Action
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man480Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man480Action.class);

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
        //初期表示
        if (cmd.equals("todoImport")) {
            forward = __doInit(map, (Man480Form) form, req, con);
        } else if (cmd.equals("Man480_Back")) {
            // 戻るボタン押下
            forward = __doBack(map, (Man480Form) form, req, con);
        } else if (cmd.equals("Man480_Import")) {
            // インポートボタン押下
            forward = __doCheckImport(map, (Man480Form) form, req, con);
        } else if (cmd.equals("delete")) {
            // 削除ボタン押下
            forward = __doInit(map, (Man480Form) form, req, con);
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
        ActionErrors errors = form.validateFileCheck(reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setMan480fileOk(false);
        } else {
            form.setMan480fileOk(true);
        }

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
     * <br>[機  能] インポートボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doCheckImport(ActionMapping map, Man480Form form,
            HttpServletRequest req, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, con);
        }


        saveToken(req);

        ActionForward forward = map.findForward("480_Import");

        return forward;
    }
    /**
     *
     * <br>[機  能] 戻る時の遷移処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Man480Form form,
            HttpServletRequest req, Connection con) throws Exception {

        GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        Man440Biz biz = new Man440Biz(getRequestModel(req));
        biz.doDeleteFile(tempMdl);
        ActionForward forward = map.findForward("480_back");
        return forward;
    }
    /**
     *
     * <br>[機  能] 初期表示時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Man480Form form,
            HttpServletRequest req, Connection con) throws Exception {

        GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        Man440Biz biz = new Man440Biz(getRequestModel(req));
        biz.doDeleteFile(tempMdl);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.createTempDir(tempMdl);

        ActionForward forward = __doDsp(map, form, req, con);
        return forward;
    }
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

}

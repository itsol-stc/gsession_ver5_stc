package jp.groupsession.v2.man.man470;

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
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man440.Man440Biz;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] サイボウズLiveデータ移行 掲示板インポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man470Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man470Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man470";

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

        ActionForward forward = null;
        Man470Form man470Form = (Man470Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("Man470_Import")) {
            //インポートボタン押下
            log__.debug("インポートボタン押下");
            forward = __doKakunin(map, man470Form, req, res, con);
        } else if (cmd.equals("Man470_Back")) {
            //戻るボタン押下
            log__.debug("戻るボタン押下");
            GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                    GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
            Man440Biz biz = new Man440Biz(getRequestModel(req));
            biz.doDeleteFile(tempMdl);
            forward = map.findForward("470_back");
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, man470Form, req, res, con);
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
                                    Man470Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathModel tempMdl = GSTemporaryPathModel.getInstance(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        Man440Biz biz = new Man440Biz(getRequestModel(req));
        biz.doDeleteFile(tempMdl);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.createTempDir(tempMdl);

        // 初期状態ではグループ名が入力される
        String grpName = biz.getGroupName(form.getMan440GrpSid(), con);
        form.setMan470forumName(grpName);

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
        Man470Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        log__.debug("テンポラリディレクトリ = " + tempDir);

        //初期表示情報を画面にセットする
        Man470ParamModel paramMdl = new Man470ParamModel();
        paramMdl.setParam(form);
        Man470Biz biz = new Man470Biz(getRequestModel(req));
        biz.setInitData(paramMdl, con, tempDir);
        paramMdl.setFormData(form);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
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
                                       Man470Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = map.findForward("470_Import");
        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        return forward;
    }

}
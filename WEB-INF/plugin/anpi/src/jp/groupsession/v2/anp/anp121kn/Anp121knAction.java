package jp.groupsession.v2.anp.anp121kn;

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
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定・緊急連絡先インポート確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp121knAction extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp121knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "anp121";

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
        Anp121knForm uform = (Anp121knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp121knback")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp121knimport")) {
            //実行（更新処理→完了画面→一覧）
            forward = __doImport(map, uform, req, res, con);

        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
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
                                   Anp121knForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
                            throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAnpi.PLUGIN_ID, TEMP_DIRECTORY_ID);
        log__.debug("テンポラリディレクトリ = " + tempDir);

        //再入力チェック
        ActionErrors errors = form.validateAnp121(map, req, getRequestModel(req), tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //取り込みファイル名を設定
        Anp121knBiz biz = new Anp121knBiz();
        String fileName = biz.getFileName(tempDir, 1);
        form.setAnp121knFileName(fileName);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 緊急連絡先インポート処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doImport(ActionMapping map,
                                     Anp121knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
                              throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstAnpi.PLUGIN_ID, TEMP_DIRECTORY_ID);
        log__.debug("テンポラリディレクトリパス = " + tempDir);

        //再入力チェック
        ActionErrors errors = form.validateAnp121(map, req, getRequestModel(req), tempDir, con);
        if (errors.size() > 0) {
            log__.debug("再チェックNG");
            addErrors(req, errors);
            return map.getInputForward();
        }

        Anp121knBiz biz = new Anp121knBiz();
        Anp121knParamModel paramModel = new Anp121knParamModel();
        paramModel.setParam(form);
        long impCount = biz.doInport(paramModel,
                getRequestModel(req), con, tempDir, TEMP_DIRECTORY_ID);
        paramModel.setFormData(form);

        /** インポートメッセージ **/
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String strImport = gsMsg.getMessage("cmn.import");

        //ログ出力
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);

        anpBiz.outPutLog(map, getRequestModel(req), strImport,
                GSConstLog.LEVEL_INFO, impCount + gsMsg.getMessage("anp.count.people"));

        //完了画面パラメータセット
        return __setKakuninDisp(map, form, req);
    }

    /**
     * <br>[機  能] 確認メッセージ表示：パラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @return アクションフォーム
     */
    private ActionForward __setKakuninDisp(ActionMapping map,
                                           Anp121knForm form,
                                           HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("list");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("cmn.kanryo.object", gsMsg.getMessage("anp.anp121kn.02")));

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp110(cmn999Form);
        form.setHiddenParamAnp121(cmn999Form);

        log__.debug("メッセージ表示");
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");

    }
}

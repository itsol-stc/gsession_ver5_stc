package jp.groupsession.v2.prj;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] プロジェクト管理プラグインで共通的に使用するアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractProjectAction extends AbstractGsAction {
    /** プラグインID */
    private static final String PLUGIN_ID = "project";

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    /**
     * <br>[機  能] プロジェクト権限エラー画面を表示する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param cmdMode 処理モード(登録・更新)
     * @return ActionForward
     */
    public ActionForward setPrjKengenError(
        ActionMapping map,
        HttpServletRequest req,
        String cmdMode) {
        GsMessage gsMsg = new GsMessage();
        //登録
        String textEntry = gsMsg.getMessage(req, "cmn.entry");
        //編集
        String textEdit = gsMsg.getMessage(req, "cmn.edit");

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //表示件数設定完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward(GSConstProject.SCR_INDEX);
        cmn999Form.setUrlOK(urlForward.getPath());

        String mode = textEntry;
        if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            mode = textEdit;
        }

        //プロジェクト
        String textProject = gsMsg.getMessage(req, "cmn.project");
        cmn999Form.setMessage(msgRes.getMessage(
                "error.edit.power.user", textProject + mode, mode));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] TODO権限エラー画面を表示する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return ActionForward
     */
    public ActionForward setTodoKengenError(
        ActionMapping map,
        HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        //編集
        String textEdit = gsMsg.getMessage(req, "cmn.edit");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        //ご利用
        String textRiyou = gsMsg.getMessage(req, "project.src.88");
        //表示件数設定完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward(GSConstProject.SCR_INDEX);
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage(
                "error.edit.power.user", GSConstProject.MSG_TODO + textEdit, textRiyou));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] テンプレート登録権限エラー画面を表示する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return ActionForward
     */
    public ActionForward setPrjTemplateError(
        ActionMapping map,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //登録
        String textEntry = gsMsg.getMessage(req, "cmn.entry");
        //表示件数設定完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward(GSConstProject.SCR_INDEX);
        cmn999Form.setUrlOK(urlForward.getPath());

        String mode = textEntry;

        //プロジェクトテンプレート
        String textProjectTmp = gsMsg.getMessage(req, "project.prj130.1");
        cmn999Form.setMessage(msgRes.getMessage(
                "error.edit.power.user", textProjectTmp + mode, mode));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
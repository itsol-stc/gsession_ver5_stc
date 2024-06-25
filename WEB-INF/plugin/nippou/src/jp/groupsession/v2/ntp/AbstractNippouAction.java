package jp.groupsession.v2.ntp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] 日報プラグインで共通的に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractNippouAction extends AbstractGsAction {
    /** プラグインID */
    private static final String PLUGIN_ID = GSConstNippou.PLUGIN_ID_NIPPOU;

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }
    /**
     * <br>権限に関するエラー画面設定
     * @param map アクションマッピング
     * @param cmn999Form hiddenパラメータ設定済みアクションフォーム
     * @param urlForward OKボタンクリック時遷移
     * @param errActStr 権限のない動作
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    public ActionForward getPermissionError(ActionMapping map,
            Cmn999Form cmn999Form,
            ActionForward urlForward,
            String errActStr,
            HttpServletRequest req, HttpServletResponse res
            ) {
        ActionForward forward = null;
        if (cmn999Form == null) {
            cmn999Form = new Cmn999Form();
        }

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        cmn999Form.setMessage(msgRes.getMessage("error.edit.power.user",
                errActStr, errActStr));

        cmn999Form.setUrlOK(urlForward.getPath());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>権限に関するエラー画面設定
     * @param map アクションマッピング
     * @param cmn999Form hiddenパラメータ設定済みアクションフォーム
     * @param urlForward OKボタンクリック時遷移
     * @param authStr 与えられていない権限の種類
     * @param errActStr 権限のない動作
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    public ActionForward getAuthError(ActionMapping map,
            Cmn999Form cmn999Form,
            ActionForward urlForward,
            String authStr,
            String errActStr,
            HttpServletRequest req, HttpServletResponse res
            ) {
        ActionForward forward = null;
        if (cmn999Form == null) {
            cmn999Form = new Cmn999Form();
        }

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        cmn999Form.setMessage(msgRes.getMessage("error.edit.power.user",
                authStr, errActStr));

        cmn999Form.setUrlOK(urlForward.getPath());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
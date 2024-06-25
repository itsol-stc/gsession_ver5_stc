package jp.groupsession.v2.cir;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板プラグインで共通的に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractCircularAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractCircularAction.class);

    /** プラグインID */
    private static final String PLUGIN_ID = "circular";

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    /**
     * <br>[機  能] 回覧板が存在しない場合のエラーメッセージ画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @return ActionForward アクションフォワード
     */
    public ActionForward getNotExistErrorPage(
        ActionMapping map, HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("gf_main");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textCircular = gsMsg.getMessage(req, "cir.5");

        //メッセージセット
        String msgState = "search.data.notfound";

        cmn999Form.setMessage(msgRes.getMessage(msgState, textCircular));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 管理者権限のチェックを行う
     * <br>[解  説]
     * <br>[備  考] 権限無しの場合はエラーメッセージページへのパラメータを設定
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @return 権限の有無 true:権限あり false:権限無し
     * @throws Exception 実行例外
     */
    protected boolean _checkAuth(ActionMapping map,
        HttpServletRequest req, Connection con)
        throws Exception {

        log__.debug("閲覧権限チェック START");

        BaseUserModel buMdl = getSessionUserModel(req);

        //管理者の場合は権限あり
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstCircular.PLUGIN_ID_CIRCULAR);

        if (adminUser) {
            return true;
        }

        //権限なしの場合はメッセージページのパラメータを設定する
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("gf_power");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.access.power.user";
        cmn999Form.setMessage(msgRes.getMessage(msgState));

        req.setAttribute("cmn999Form", cmn999Form);

        log__.debug("閲覧権限チェック END");

        return false;
    }

}

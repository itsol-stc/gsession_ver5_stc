package jp.groupsession.v2.rsv.rsv900;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.rsv.RsvConfigBundle;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] 施設予約設定ファイル再読み込みのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv900Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv900Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                     org.apache.struts.action.ActionForm,
     *                     javax.servlet.http.HttpServletRequest,
     *                     javax.servlet.http.HttpServletResponse,
     *                     java.sql.Connection)
    */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        log__.debug("-- 施設予約設定ファイルの再読み込み開始 --");

        String appRootPath = getAppRootPath();
        RsvConfigBundle.readConfig(appRootPath);

        ActionForward forward = map.findForward("gf_msg");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        //URL
        urlForward = map.findForward("gf_menu");
        cmn999Form.setUrlOK(urlForward.getPath());
        //メッセージ
        //印刷区分使用設定 0:使用しない 1:使用する
        int priKbn = RsvCommonBiz.getConfValue(appRootPath,
                GSConstReserve.RSV_PRINT_USE,
                GSConstReserve.RSV_PRINT_USE_NO);

        String priMsg;
        if (priKbn == GSConstReserve.RSV_PRINT_USE_YES) {
            priMsg = "使用する";
        } else {
            priMsg = "使用しない";
        }

        cmn999Form.setMessage(
                "施設予約設定ファイルの再読み込みが完了しました。<br>"
                + "印刷区分使用設定 : "
                + priMsg + "(" + priKbn + ")"
                );

        req.setAttribute("cmn999Form", cmn999Form);
        log__.debug("-- 施設予約設定ファイルの再読み込み完了 --");

        return forward;
    }
}

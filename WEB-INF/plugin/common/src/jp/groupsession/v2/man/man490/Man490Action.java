package jp.groupsession.v2.man.man490;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.struts.AbstractGsAction;
/**
 *
 * <br>[機  能] 個人設定 CybozuLive取り込み
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man490Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man490Action.class);

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("man491")) {
            forward = map.findForward("man491");
        } else if (cmd.equals("man490Back")) {
            // 戻るボタン押下
            forward = map.findForward("490_back");
        } else {
            //初期表示
            forward = map.getInputForward();
        }
        log__.debug("END");
        return forward;
    }

}

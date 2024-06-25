package jp.groupsession.v2.zsk.zsk030;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.zsk.AbstractZaisekiAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 在席管理 座席表設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk030Action extends AbstractZaisekiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk030Action.class);

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Zsk030Form zskForm = (Zsk030Form) form;
        if (cmd.equals("zsk020")) {
            //戻る
            forward = map.findForward("zsk020");
        } else if (cmd.equals("zsk040")) {
            //座席表追加
            forward = map.findForward("zsk040");
        } else if (cmd.equals("zsk050")) {
            //編集
            forward = map.findForward("zsk050");
        } else {
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * 初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, Zsk030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Zsk030Biz biz = new Zsk030Biz();

        Zsk030ParamModel paramMdl = new Zsk030ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }
}

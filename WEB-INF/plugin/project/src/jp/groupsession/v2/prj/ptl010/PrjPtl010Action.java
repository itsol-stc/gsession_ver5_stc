package jp.groupsession.v2.prj.ptl010;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.prj.AbstractProjectAction;

/**
 * <br>[機  能] プロジェクト管理 ポートレット TODO一覧のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjPtl010Action extends AbstractProjectAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjPtl010Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("start");
        ActionForward forward = null;
        PrjPtl010Biz biz = new PrjPtl010Biz(con, getRequestModel(req));
        PrjPtl010Form thisForm = (PrjPtl010Form) form;
        BaseUserModel buMdl = getSessionUserModel(req);

        PrjPtl010ParamModel paramMdl = new PrjPtl010ParamModel();
        paramMdl.setParam(thisForm);
        biz.setInitData(paramMdl, buMdl);
        paramMdl.setFormData(thisForm);

        forward = map.getInputForward();
        log__.debug("end");
        return forward;
    }

}
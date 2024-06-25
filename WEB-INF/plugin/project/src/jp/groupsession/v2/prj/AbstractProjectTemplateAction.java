package jp.groupsession.v2.prj;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.prj.prj010.Prj010Form;

/**
 * <br>[機  能] プロジェクト管理プラグインで共通的に使用するアクションクラスです。(テンプレート用)
 * <br>[解  説] 管理者設定から遷移可能な為、共有テンプレート＋システム管理者の場合にプラグインチェックしない判定付き
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractProjectTemplateAction extends AbstractProjectAction {

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

    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {

        //テンプレート表示モード = 共有テンプレート
        int tmpMode = ((Prj010Form) form).getPrjTmpMode();
        if (tmpMode == GSConstProject.MODE_TMP_KYOYU) {
            // 共有テンプレートの場合のみチェック
            if (_isSystemAdmin(req, con)) {
                return true; //システム管理者の場合はプラグインチェックをしない
            }
        }
        return super._isAccessPlugin(req, form, con);
    }

    /**
    *
    * <br>[機  能] アクション実行前の事前処理 アクセス制限を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @return ActionForward
    * @throws Exception 実行時例外
    */
   protected ActionForward _immigration(ActionMapping map, ActionForm form,
           HttpServletRequest req, HttpServletResponse res, Connection con)
           throws Exception {

       int tmpMode = ((Prj010Form) form).getPrjTmpMode();

       //テンプレート表示モード = 共有テンプレート
       if (tmpMode == GSConstProject.MODE_TMP_KYOYU) {
           BaseUserModel buMdl = getSessionUserModel(req);
           CommonBiz cmnBiz = new CommonBiz();
           boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

           if (!adminUser) {
               return setPrjTemplateError(map, req);
           }
       } else if (tmpMode < 1) {
           return setPrjTemplateError(map, req);
       }

       return null;
   }
}
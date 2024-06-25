package jp.groupsession.v2.man.man420kn;

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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.dao.base.CmnImpTimeDao;
import jp.groupsession.v2.man.man420.Man420ParamModel;
import jp.groupsession.v2.man.model.CmnImpTimeModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能]ユーザ連携自動インポート機能設定確認画面
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man420knAction extends AdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man420knAction.class);

    /**
     * <br>[機  能] アクションを実行する
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
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        Man420knForm thisForm = (Man420knForm) form;

        //クラウド版の場合は不正なアクセス
        if (!GroupSession.getResourceManager().getDomain(req).equals(GSConst.GS_DOMAIN)) {
            forward = map.findForward("gf_submit");
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("back")) {
            //自動インポート設定画面へ戻る
            forward = map.findForward("back");
        } else if (cmd.equals("doSetting")) {
            //データの登録を行う
            forward = __doSetting(thisForm, req, map, con);
        } else {
            // トランザクショントークン設定
            saveToken(req);
            //初期表示
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     *
     * <br>[機  能]データ登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param thisForm アクションフォーム
     * @param req リクエスト
     * @param map アクションマッピング
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     */
    public ActionForward __doSetting(Man420knForm thisForm, HttpServletRequest req,
            ActionMapping map, Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //登録データのチェック
        ActionErrors errors = thisForm.validateForm(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        Man420knBiz knBiz = new Man420knBiz();
        Man420knParamModel paramMdl = new Man420knParamModel();
        paramMdl.setParam(thisForm);

        //DBに値が設定されていなかった場合は追加する
        CmnImpTimeDao citDao = new CmnImpTimeDao(con);
        CmnImpTimeModel cmiMdl = citDao.select();

        if (cmiMdl == null) {
             knBiz.insertImpTime(con, citDao, paramMdl);
        } else {
            knBiz.updateImpTime(con, citDao, paramMdl);
        }
        paramMdl.setFormData(thisForm);

        //オペレーションログを作成
        __outPutLog(req, con, map, thisForm);

        return __doneDsp(req, con, map, thisForm);
    }

    /**
     *
     * <br>[機  能]設定完了画面を表示する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param map アクションマッピング
     * @param thisForm アクションフォーム
     * @return アクションフォワード
     */
    private ActionForward __doneDsp(
            HttpServletRequest req,
            Connection con,
            ActionMapping map,
            Man420knForm thisForm) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //表示件数設定完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object", gsMsg.getMessage("main.man420.2")));

        urlForward = map.findForward("mainAdminMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");

    }

    /**
    *
    * <br>[機  能]オペレーションログを出力する
    * <br>[解  説]
    * <br>[備  考]
    * @param req リクエスト
    * @param con コネクション
    * @param map アクションマッピング
    * @param thisForm アクションフォーム
    */
    private void __outPutLog(
            HttpServletRequest req,
            Connection con,
            ActionMapping map,
            Man420knForm thisForm) {

            //ログ出力
            RequestModel reqMdl = getRequestModel(req);
            CommonBiz cmnBiz = new CommonBiz();
            GsMessage gsMsg = new GsMessage(reqMdl);

            //ログの本文を作成
            Man420knBiz knBiz = new Man420knBiz();
            Man420ParamModel paramMdl = new Man420ParamModel();
            paramMdl.setParam(thisForm);
            String logValue = knBiz.settingLogValue(paramMdl, gsMsg);

            String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                             + gsMsg.getMessage("main.man420kn.1");
            cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                    gsMsg.getMessage("cmn.change"),
                    GSConstLog.LEVEL_INFO, logValue, dspName);

    }

}

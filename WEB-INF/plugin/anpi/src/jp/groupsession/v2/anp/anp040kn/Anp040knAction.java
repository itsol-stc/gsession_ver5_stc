package jp.groupsession.v2.anp.anp040kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 個人設定・表示設定確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Anp040knAction extends AbstractAnpiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp040knAction.class);

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
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
                                       throws Exception {

        ActionForward forward = null;
        Anp040knForm uform = (Anp040knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("anp040knback")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp040knexcute")) {
            //確定（完了メッセージ→個人設定メニュー）
            forward = __doExcute(map, uform, req, res, con);

        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期化実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @param  res レスポンス
     * @param  con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doInit(ActionMapping map,
                                   Anp040knForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
                            throws Exception {

        log__.debug("///初期化開始///");
        Anp040knBiz biz = new Anp040knBiz();
        Anp040knParamModel paramModel = new Anp040knParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定（更新）実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @param  res レスポンス
     * @param  con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doExcute(ActionMapping map,
                                    Anp040knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
                             throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        Anp040knBiz biz = new Anp040knBiz();
        Anp040knParamModel paramModel = new Anp040knParamModel();
        paramModel.setParam(form);
        biz.doUpdate(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = __getLogMessage(form, req, con);
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("cmn.edit");
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, msg);

        //メッセージ画面に設定する処理の後↓にリターン
        return __setFinishDisp(map, form, req);
    }

    /**
     * <br>[機  能] 完了画面表示：パラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @return アクションフォーム
     */
    private ActionForward __setFinishDisp(ActionMapping map,
                                          Anp040knForm form,
                                          HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("pritool");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("cmn.kanryo.object", gsMsg.getMessage("anp.anp040kn.01")));

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");

    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws Exception エラー
     */
    private String __getLogMessage(
        Anp040knForm form,
        HttpServletRequest req,
        Connection con) throws Exception {

        Anp040knBiz biz = new Anp040knBiz();
        GsMessage gsMsg = new GsMessage(req);
        String groupName = gsMsg.getMessage("cmn.all");
        if (!form.getAnp040SelectGroupSid().equals(GSConstAnpi.ALL_GROUP_STRING)) {
            groupName = biz.getGroupName(con, form.getAnp040SelectGroupSid());
        }

        String msg = "";
        //メイン画面表示
        msg += "[" + gsMsg.getMessage("cmn.main.view2") + "]";
        if (form.getAnp040MainDispFlg() == GSConstAnpi.MAIN_DSP_HIDE) {
            msg += gsMsg.getMessage("cmn.dont.show");
        } else if (form.getAnp040MainDispFlg() == GSConstAnpi.MAIN_DSP_SHOW) {
            msg += gsMsg.getMessage("cmn.display.ok");
        }
        //一覧表示件数
        msg += "\r\n[" + gsMsg.getMessage("enq.enq810.01") + "]";
        msg += form.getAnp040SelectDispCnt();
        //デフォルト表示グループ
        msg += "\r\n[" + gsMsg.getMessage("anp.anp040.02") + "]";
        msg += groupName;
        return msg;
    }
}

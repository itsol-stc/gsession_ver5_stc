package jp.groupsession.v2.anp.anp050kn;

import java.sql.Connection;

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
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 個人設定・連絡先設定確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp050knAction extends AbstractAnpiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp050knAction.class);

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
        Anp050knForm uform = (Anp050knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("anp050knback")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp050knexcute")) {
            //確定（完了メッセージ→個人設定メニュー）
            forward = __doExcute(map, uform, req, res, con);

        } else {
            //初期化
            forward = map.getInputForward();
        }

        return forward;
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
                                    Anp050knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
                                    throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateAnp050(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        Anp050knBiz biz = new Anp050knBiz();
        Anp050knParamModel paramModel = new Anp050knParamModel();
        RequestModel reqMdl = getRequestModel(req);
        paramModel.setParam(form);
        biz.doUpdate(paramModel, reqMdl, con);
        paramModel.setFormData(form);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("cmn.edit");
        String msg = __getLogMessage(form, req);
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
                                          Anp050knForm form,
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
                msgRes.getMessage("cmn.kanryo.object", gsMsg.getMessage("cmn.preferences")));

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
     * @return ActionForward
     * @throws Exception エラー
     */
    private String __getLogMessage(
        Anp050knForm form,
        HttpServletRequest req) throws Exception {

        GsMessage gsMsg = new GsMessage(req);

        String msg = "";
        //メールアドレス
        msg += "[" + gsMsg.getMessage("cmn.mailaddress") + "]";
        msg += form.getAnp050Mail();
        //電話番号
        msg += "\r\n[" + gsMsg.getMessage("cmn.tel") + "]";
        msg += form.getAnp050TelNo();
        return msg;
    }
}

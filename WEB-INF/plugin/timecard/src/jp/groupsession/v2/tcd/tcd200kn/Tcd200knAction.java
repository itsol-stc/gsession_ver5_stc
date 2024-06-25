package jp.groupsession.v2.tcd.tcd200kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardSubAction;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード 管理者設定有休日数登録,編集確認画面のアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd200knAction extends AbstractTimecardSubAction {

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        
        Tcd200knForm thisForm = (Tcd200knForm) form;
        TimecardBiz tcBiz = new TimecardBiz();
        String group = String.valueOf(thisForm.getTcd200Group());
        //権限チェック
        if (!tcBiz.isAccessOk(req, con, group, getRequestModel(req))) {
            __setAccessErrorParam(map, req, thisForm);
            return map.findForward("gf_msg");
        }
        
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        ActionForward forward = null;
        
        if (cmd.equals("decision")) {
            //確定ボタン押下時
            forward = __doSubmit(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd200knback")) {
            //戻るボタン押下時
            forward = map.findForward("tcd200knback");
        } else {
            //初期表示
            forward = __doDsp(map, thisForm, req, res, con);
        }
        return forward; 
    }
    
    /**
     * <br>[機  能] 画面表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doDsp(
            ActionMapping map,
            Tcd200knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        Tcd200knParamModel paramMdl = new Tcd200knParamModel();
        paramMdl.setParam(form);
        Tcd200knBiz biz = new Tcd200knBiz();
        biz._setMode(paramMdl, con);
        biz._setDisplayData(paramMdl, con);
        paramMdl.setFormData(form);
        
        return map.getInputForward();
    }
    
    /**
     * <br>[機  能] 確定ボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doSubmit(
            ActionMapping map,
            Tcd200knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        //モードの設定
        Tcd200knParamModel paramMdl = new Tcd200knParamModel();
        paramMdl.setParam(form);
        Tcd200knBiz biz = new Tcd200knBiz();
        biz._setMode(paramMdl, con);
        
        //2重投稿チェック
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }
        
        RequestModel reqMdl = getRequestModel(req);
        
        //入力チェック
        ActionErrors errors = form.validateSubmit(reqMdl, con); 
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        //有休日数情報の登録,編集の実行
        biz._upsertYukyu(map, reqMdl, paramMdl, con);
        
        //有休日数登録完了画面へ遷移
        return __doUpsertKanryo(map, form, req, res, con);
        
    }
    
    /**
     * <br>[機  能] タイムカード有休日数登録,編集後の画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doUpsertKanryo(ActionMapping map,
                                          Tcd200knForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) {
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("upsertComp");
        cmn999Form.setUrlOK(forwardOk.getPath());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //有休日数
        String textYukyuDays = gsMsg.getMessage(req, "tcd.210");
        if (form.getTcd200mode() == GSConstTimecard.YUKYU_MODE_INSERT) {
            cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", textYukyuDays));
        } else if (form.getTcd200mode() == GSConstTimecard.YUKYU_MODE_UPDATE) {
            cmn999Form.setMessage(msgRes.getMessage("hensyu.kanryo.object", textYukyuDays));
        }

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        cmn999Form.addHiddenParam("tcdBackScreen", form.getTcdBackScreen());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("tcd190nendo", form.getTcd190nendo());
        cmn999Form.addHiddenParam("tcd190group", form.getTcd190group());
        cmn999Form.addHiddenParam("tcd190sortKey", form.getTcd190sortKey());
        cmn999Form.addHiddenParam("tcd190order", form.getTcd190order());
        cmn999Form.addHiddenParam("tcd190page", form.getTcd190page());
        
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 権限エラー画面遷移時
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setAccessErrorParam(
        ActionMapping map,
        HttpServletRequest req,
        Tcd200knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        urlForward = map.findForward("gf_menu");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "tcd.143");
        String msg2 = gsMsg.getMessage(req, "tcd.144");

        //メッセージセット
        String msgState = "error.edit.power.user";
        String key1 = msg;
        String key2 = msg2;
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1, key2));

        req.setAttribute("cmn999Form", cmn999Form);
    }
}

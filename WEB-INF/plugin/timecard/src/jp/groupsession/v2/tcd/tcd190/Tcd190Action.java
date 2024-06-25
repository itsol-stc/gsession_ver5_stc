package jp.groupsession.v2.tcd.tcd190;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardSubAction;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード管理者設定 有休日数一覧画面のアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd190Action extends AbstractTimecardSubAction {

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
        
        Tcd190Form thisForm = (Tcd190Form) form;
        TimecardBiz tcBiz = new TimecardBiz();
        //権限チェック
        if (!tcBiz.isAccessOk(req, con, thisForm.getTcd190group(), getRequestModel(req))) {
            __setAccessErrorParam(map, req, thisForm);
            return map.findForward("gf_msg");
        }
        
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        ActionForward forward = null;
        
        if (cmd.equals("import")) {
            //インポートボタン押下時処理
            forward = map.findForward("tcd190import");
        } else if (cmd.equals("add")) {
            //追加ボタン押下時処理
            forward = __doInsert(map, thisForm, req, res, con);
        } else if (cmd.equals("page_right")) {
            //ページコンボ次へボタン押下時
            forward = __doNext(map, thisForm, req, res, con);
        } else if (cmd.equals("page_left")) {
            //ページコンボ前へボタン押下時
            forward = __doPrev(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd190back")) {
            //戻るボタン押下時処理
            if (thisForm.getTcdBackScreen() == GSConstTimecard.TCD_BACKSCREEN_ADMIN) {
                forward = map.findForward("backAdmin");
            } else {
                forward = map.findForward("back");
            }
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward; 
    }
    
    /**
     * <br>[機  能] 表示処理を行う
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
    private ActionForward __doInit(
            ActionMapping map,
            Tcd190Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        RequestModel reqMdl = getRequestModel(req);
        Tcd190Biz biz = new Tcd190Biz(reqMdl);
        Tcd190ParamModel paramMdl = new Tcd190ParamModel();
        paramMdl.setParam(form);
        biz._setInitData(paramMdl, con);  
        paramMdl.setFormData(form);
        
        return map.getInputForward();
    }
    
    /**
     * <br>[機  能] 有休日数登録画面へ遷移する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward
     */
    private ActionForward __doInsert(
            ActionMapping map,
            Tcd190Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) {
        
        //パラメータの設定
        form.setTcd200Nendo(form.getTcd190nendo());
        form.setTcd200Group(NullDefault.getInt(form.getTcd190group(), -1));

        return map.findForward("tcd190insert");
    }
    
    /**
     * <br>[機  能] 次へボタン押下時処理
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
    private ActionForward __doNext(
            ActionMapping map,
            Tcd190Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        //ページ番号を増加させる
        form.setTcd190page(form.getTcd190page() + 1);
        
        return __doInit(map, form, req, res, con);
    }
    
    /**
     * <br>[機  能] 前へボタン押下時処理
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
    private ActionForward __doPrev(
            ActionMapping map,
            Tcd190Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        //ページ番号を減少させる
        form.setTcd190page(form.getTcd190page() - 1);
        
        return __doInit(map, form, req, res, con);
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
        Tcd190Form form) {

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

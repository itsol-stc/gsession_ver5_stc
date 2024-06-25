package jp.groupsession.v2.tcd.tcd200;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardSubAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;

/**
 * <br>[機  能] タイムカード管理者設定 有休日数登録画面のアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd200Action extends AbstractTimecardSubAction {

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
        
        Tcd200Form thisForm = (Tcd200Form) form;
        TimecardBiz tcBiz = new TimecardBiz();
        String group = String.valueOf(thisForm.getTcd200Group());
        //権限チェック
        if (!tcBiz.isAccessOk(req, con, group, getRequestModel(req))) {
            __setAccessErrorParam(map, req, thisForm);
            return map.findForward("gf_msg");
        }
        
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        ActionForward forward = null;

        if (cmd.equals("tcd200confirm")) {
            //OKボタン押下時
            forward = __doSubmit(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd200delete")) {
            //削除ボタン押下時
            forward = __doDeleteKakunin(map, thisForm, req, res, con);
        } else if (cmd.equals("doDelete")) {
            //削除確認画面OKボタン押下時
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd200back")) {
            //戻るボタン押下時
            return map.findForward("tcd200back");
        } else if (cmd.equals("changeGroup")) {
            //グループコンボ変更時処理
            forward = __doChangeGroup(map, thisForm, req, res, con);
        } else if (cmd.equals("changeUserNendo")) {
            //ユーザコンボ,年度コンボ変更時処理
            forward = __doChangeUserNendo(map, thisForm, req, res, con);
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
            Tcd200Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        RequestModel reqMdl = getRequestModel(req);
        Tcd200ParamModel paramMdl = new Tcd200ParamModel();
        paramMdl.setParam(form);
        
        Tcd200Biz biz = new Tcd200Biz();
        biz._setInitData(paramMdl, reqMdl, con);
        paramMdl.setFormData(form);
        
        return map.getInputForward();
    }
    
    /**
     * <br>[機  能] 入力値をチェックし、確認画面へ遷移する
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
            Tcd200Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        RequestModel reqMdl = getRequestModel(req);
        
        //入力チェック
        ActionErrors errors = form.validateSubmit(reqMdl, con); 
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        
        //トランザクショントークンの保存
        saveToken(req);
        
        return map.findForward("tcd200commit");
    }
    
    /**
     * <br>[機  能] グループコンボ変更時処理
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
    private ActionForward __doChangeGroup(
            ActionMapping map,
            Tcd200Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        //パラメータの設定
        form.setTcd200initFlg(GSConstTimecard.DSP_INIT);
        form.setTcd200Name(-1);
        
        return __doDsp(map, form, req, res, con);
    }
    
    /**
     * <br>[機  能] ユーザコンボ,年度コンボ変更時処理
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
    private ActionForward __doChangeUserNendo(
            ActionMapping map,
            Tcd200Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        //パラメータの設定
        form.setTcd200initFlg(GSConstTimecard.DSP_INIT);
        
        return __doDsp(map, form, req, res, con);
    }
    
    /**
     * <br>[機  能] 削除ボタン押下時処理
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
    private ActionForward __doDeleteKakunin(
            ActionMapping map,
            Tcd200Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        //パラメータチェック
        if (!form._validateDelete(con)) {
            return getSubmitErrorPage(map, req);
        }
        
        //トークンの保存
        saveToken(req);
        
        return __doDeleteConfirm(map, form, req, res, con);
    }
    
    /**
     * <br>[機  能] タイムカード有休日数削除後の画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doDeleteConfirm(ActionMapping map,
                                          Tcd200Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forward = map.findForward("tcd200delete");
        cmn999Form.setUrlOK(forward.getPath());
        //キャンセルクリック時遷移先
        forward = map.findForward("tcd200cancel");
        cmn999Form.setUrlCancel(forward.getPath());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        
        StringBuilder strBld = new StringBuilder();
        CmnUsrmInfDao cmnDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel cmnMdl = cmnDao.select(form.getTcd200Name());
        strBld.append(cmnMdl.getUsiSei());
        strBld.append(" ");
        strBld.append(cmnMdl.getUsiMei());
        strBld.append("　");
        strBld.append(form.getTcd200Nendo());
        strBld.append(gsMsg.getMessage("cmn.year2"));
        String userNendo = strBld.toString();
        String text = gsMsg.getMessageVal0("tcd.tcd200.04", userNendo);
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once", text));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("tcdBackScreen", form.getTcdBackScreen());
        cmn999Form.addHiddenParam("tcd200Name", form.getTcd200Name());
        cmn999Form.addHiddenParam("tcd200Nendo", form.getTcd200Nendo());
        cmn999Form.addHiddenParam("tcd200Group", form.getTcd200Group());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("tcd190group", form.getTcd190group());
        cmn999Form.addHiddenParam("tcd190nendo", form.getTcd190nendo());
        cmn999Form.addHiddenParam("tcd190sortKey", form.getTcd190sortKey());
        cmn999Form.addHiddenParam("tcd190order", form.getTcd190order());
        cmn999Form.addHiddenParam("tcd190page", form.getTcd190page());
        
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    
    /**
     * <br>[機  能] 削除確認画面OKボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doDelete(
            ActionMapping map,
            Tcd200Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {
        
        //2重投稿チェック
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }
        
        //入力チェック
        if (!form._validateDelete(con)) {
            return getSubmitErrorPage(map, req);
        }
        
        //削除処理の実行
        boolean commit = false;
        try {
            TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
            tydDao.delete(form.getTcd200Name(), form.getTcd200Nendo());
            commit = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        
        RequestModel reqMdl = getRequestModel(req);
        TimecardBiz tcdBiz = new TimecardBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDelete = gsMsg.getMessage("cmn.delete");
        StringBuilder strBld = new StringBuilder();
        CmnUsrmInfDao cmnDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel cmnMdl = cmnDao.select(form.getTcd200Name());
        strBld.append("[");
        strBld.append(gsMsg.getMessage("cmn.user.name"));
        strBld.append("]");
        strBld.append(cmnMdl.getUsiName());
        strBld.append("\r\n");
        strBld.append("[");
        strBld.append(gsMsg.getMessage("tcd.209"));
        strBld.append("]");
        strBld.append(form.getTcd200Nendo());
        
        String value = strBld.toString();
        tcdBiz.outPutTimecardLog(map, reqMdl, con,
                textDelete, GSConstLog.LEVEL_INFO, value);
        
        return __doDeleteKanryo(map, form, req, res, con);
    }
    
    /**
     * <br>[機  能] タイムカード有休日数削除後の画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteKanryo(ActionMapping map,
                                          Tcd200Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) {
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("tcd200back");
        cmn999Form.setUrlOK(forwardOk.getPath());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //有休日数
        String textYukyuDays = gsMsg.getMessage(req, "tcd.210");
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object", textYukyuDays));

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        cmn999Form.addHiddenParam("tcdBackScreen", form.getTcdBackScreen());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("tcd190group", form.getTcd190group());
        cmn999Form.addHiddenParam("tcd190nendo", form.getTcd190nendo());
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
        Tcd200Form form) {

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

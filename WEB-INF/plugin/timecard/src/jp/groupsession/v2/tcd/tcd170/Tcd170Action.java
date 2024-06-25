package jp.groupsession.v2.tcd.tcd170;

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
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;


/**
 * <br>[機  能] タイムカード 管理者設定 休日区分登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd170Action extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd170Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {
        ActionForward forward = null;
        //管理者権限チェック
        CommonBiz commonBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        boolean isAdmin =
                commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstTimecard.PLUGIN_ID_TIMECARD);
        if (!isAdmin) {
            return getNotAdminSeniPage(map, req);
        }

        //コマンド取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        cmd = cmd.trim();

        Tcd170Form thisForm = (Tcd170Form) form;

        if (cmd.equals("tcd170back")) {
            thisForm.setTcd170Mode(GSConst.CMDMODE_ADD);
            //戻る
            forward = map.findForward("tcd170back");
        } else if (cmd.equals("ok")) {
            //ok
            forward = __doKakunin(map, thisForm, req, res, con);
        } else if (cmd.equals("delete")) {
            //削除ボタン押下
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteHoliKbn")) {
            //削除確認画面からの遷移
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }



    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Tcd170Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = null;
        if (form.getTcd170Mode() == GSConst.CMDMODE_EDIT) {
            //存在チェック
            errors = form.existsHoliday(reqMdl, con);
            if (errors.size() > 0) {
                log__.debug("不正エラー");
                addErrors(req, errors);
                return getSubmitErrorPage(map, req);
            }
        }

        con.setAutoCommit(true);
        Tcd170ParamModel paramMdl = new Tcd170ParamModel();
        paramMdl.setParam(form);
        Tcd170Biz biz = new Tcd170Biz(reqMdl);
        biz._setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        //使用チェック
        errors = form.outputCheck(con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            form.setTcd170useHolFlg(1);
        }
        con.setAutoCommit(false);
        return map.getInputForward();
    }


    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 例外
     */
    private ActionForward __doKakunin(ActionMapping map, Tcd170Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("確認処理");
        //入力チェック
        ActionErrors errors = form.validateCheck(req, con);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        log__.debug("入力エラーなし");
        //トランザクショントークン設定
        saveToken(req);
        return map.findForward("tcd170confirm");
    }

    /**
     * <br>削除ボタンクリック時の処理を行う
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 例外
     */
    private ActionForward __doDelete(
            ActionMapping map,
            Tcd170Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = null;
        if (form.getTcd170Mode() == GSConst.CMDMODE_EDIT) {
            errors = form.existsHoliday(reqMdl, con);
            if (errors.size() > 0) {
                log__.debug("不正エラー");
                addErrors(req, errors);
                return getSubmitErrorPage(map, req);
            }
            //デフォルト休日チェック
            errors = form.defaultHoliday(reqMdl);
            if (errors.size() > 0) {
                log__.debug("不正エラー");
                addErrors(req, errors);
                return getSubmitErrorPage(map, req);
            }
        }
        //使用チェック
        errors = form.outputCheck(con);
        if (errors.size() > 0) {
            log__.debug("使用中エラー");
            addErrors(req, errors);
            form.setTcd170useHolFlg(1);
            return __doInit(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Tcd170Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage(req);

        //キャンセルボタンクリック時遷移先
        ActionForward forward = map.findForward("mine");
        cmn999Form.setUrlCancel(forward.getPath());

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteHoliKbn");
        //メッセージ
        String msg = "sakujo.kakunin.once";
        String mkey1 = gsMsg.getMessage("tcd.tcd160.06");
        cmn999Form.setMessage(msgRes.getMessage(msg, mkey1));

        //画面パラメータをセット
        form.setHiddenParamTcd170(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Tcd170Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        if (form.getTcd170Mode() == GSConst.CMDMODE_EDIT) {
            errors = form.existsHoliday(reqMdl, con);
            if (errors.size() > 0) {
                log__.debug("不正エラー");
                addErrors(req, errors);
                return getSubmitErrorPage(map, req);
            }
        }
        //使用チェック
        errors = form.outputCheck(con);
        if (errors.size() > 0) {
            log__.debug("使用中エラー");
            addErrors(req, errors);
            return getSubmitErrorPage(map, req);
        }

        //ログ出力用
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        TcdHolidayInfModel mdl = dao.select(form.getTcd170EditSid());

        //削除実行
        Tcd170ParamModel paramMdl = new Tcd170ParamModel();
        paramMdl.setParam(form);
        Tcd170Biz biz = new Tcd170Biz(reqMdl);
        biz._delete(paramMdl, con);
        paramMdl.setFormData(form);

        //ログ作成
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgState = gsMsg.getMessage("cmn.delete");

        StringBuilder sb = new StringBuilder();
        //休日区分名
        sb.append("[" + gsMsg.getMessage("tcd.tcd160.02") + "] ");
        sb.append(mdl.getThiName());
        sb.append(System.getProperty("line.separator"));
        TimecardBiz cBiz = new TimecardBiz(reqMdl);
        cBiz.outPutTimecardLog(
                map,
                reqMdl,
                con,
                msgState,
                GSConstLog.LEVEL_INFO,
                sb.toString());


        //削除完了画面を表示
        return __setKanryoDsp(map, req, form);
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        HttpServletRequest req,
        Tcd170Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("tcd170back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage(req);
        String mkey1 = gsMsg.getMessage("tcd.tcd160.06");
        String msgState = "sakujo.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}

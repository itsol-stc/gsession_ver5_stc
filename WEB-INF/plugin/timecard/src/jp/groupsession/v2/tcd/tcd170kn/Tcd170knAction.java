package jp.groupsession.v2.tcd.tcd170kn;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;


/**
 * <br>[機  能] タイムカード 管理者設定 休日区分登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd170knAction extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd170knAction.class);

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

        Tcd170knForm thisForm = (Tcd170knForm) form;

        if (cmd.equals("tcd170knBack")) {
            //戻る
            forward = map.findForward("tcd170knBack");
        } else if (cmd.equals("kakutei")) {
            //確定
            forward = __doKakutei(map, thisForm, req, res, con);
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
    private ActionForward __doInit(ActionMapping map, Tcd170knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        //存在チェック
        ActionErrors errors = null;
        if (form.getTcd170Mode() == GSConst.CMDMODE_EDIT) {
            errors = form.existsHoliday(reqMdl, con);
            if (errors.size() > 0) {
                log__.debug("不正エラー");
                addErrors(req, errors);
                return getSubmitErrorPage(map, req);
            }
        }

        return map.getInputForward();
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 例外
     */
    private ActionForward __doKakutei(
            ActionMapping map,
            Tcd170knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
            throws Exception {

        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCheck(req, con);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //DB更新
        BaseUserModel umodel = getSessionUserModel(req);
        Tcd170knParamModel paramMdl = new Tcd170knParamModel();
        paramMdl.setParam(form);
        Tcd170knBiz biz = new Tcd170knBiz(reqMdl);
        int sid = 0;
        if (form.getTcd170Mode() == GSConst.CMDMODE_ADD) {
            MlCountMtController cntCon = getCountMtController(req);
            sid = biz._insert(paramMdl, con, umodel.getUsrsid(), cntCon);
        } else {
            biz._update(paramMdl, con, umodel.getUsrsid());
            sid = form.getTcd170EditSid();
        }
        paramMdl.setFormData(form);

        //ログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = "";
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        TcdHolidayInfModel mdl = dao.select(sid);
        if (form.getTcd170Mode() == GSConst.CMDMODE_ADD) {
            opCode = gsMsg.getMessage("cmn.edit");
        } else {
            opCode = gsMsg.getMessage("cmn.entry");
        }
        StringBuilder sb = new StringBuilder();
        //選択ユーザ
        sb.append("[" + gsMsg.getMessage("tcd.tcd160.02") + "] ");
        sb.append(mdl.getThiName());
        sb.append(System.getProperty("line.separator"));
        TimecardBiz cBiz = new TimecardBiz(reqMdl);
        cBiz.outPutTimecardLog(map, reqMdl, con,
                opCode, GSConstLog.LEVEL_INFO, sb.toString());

        //共通メッセージ画面(OK キャンセル)を表示
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Tcd170knForm form) {

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
        urlForward = map.findForward("tcd160list");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage(req);
        String mkey1 = gsMsg.getMessage("tcd.203");
        String msgState = "";
        if (form.getTcd170Mode() == GSConst.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        req.setAttribute("cmn999Form", cmn999Form);

    }

}

package jp.groupsession.v2.ntp.ntp094;

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
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 日報一覧表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp094Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp094Action.class);

    /**
     * <br>アクション実行
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

        Ntp094Form ntpForm = (Ntp094Form) form;
        if (form == null) {
            log__.debug(" form is null ");
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ntp094kakunin")) {
            //確認
            forward = __doKakunin(map, ntpForm, req, res, con);
        } else if (cmd.equals("ntp094commit")) {
            //登録
            forward = __doCommit(map, ntpForm, req, res, con);
        } else if (cmd.equals("ntp094back")) {
            //戻る
            forward = __doBack(map, ntpForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, ntpForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(
        ActionMapping map,
        Ntp094Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

//        ActionForward forward = null;

        ActionErrors errors = form.validateCheck();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ntp094Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
//        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msgState = "edit.kakunin.once";
        String mkey1 = gsMsg.getMessage("cmn.display.settings");
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("ntp094DefLine", form.getNtp094DefLine());
        cmn999Form.addHiddenParam("ntp094ReloadTime", form.getNtp094ReloadTime());
        cmn999Form.addHiddenParam("ntp094Position", form.getNtp094Position());
        cmn999Form.addHiddenParam("ntp094InitFlg", form.getNtp094InitFlg());
        cmn999Form.addHiddenParam("ntp094SortKey1", form.getNtp094SortKey1());
        cmn999Form.addHiddenParam("ntp094SortOrder1", form.getNtp094SortOrder1());
        cmn999Form.addHiddenParam("ntp094SortKey2", form.getNtp094SortKey2());
        cmn999Form.addHiddenParam("ntp094SortOrder2", form.getNtp094SortOrder2());
        cmn999Form.addHiddenParam("ntp094DefGroup", form.getNtp094DefGroup());
        cmn999Form.addHiddenParam("ntp094SchKbn", form.getNtp094SchKbn());

        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Ntp094Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("登録");
        ActionForward forward = null;

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionErrors errors = form.validateCheck();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //DB更新
        Ntp094Biz biz = new Ntp094Biz(con, getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Ntp094ParamModel paramMdl = new Ntp094ParamModel();
        paramMdl.setParam(form);
        biz.setPconfSetting(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        /** メッセージ 変更 **/
        String change = gsMsg.getMessage("cmn.change");

        /** ログメッセージ */
        String msg = "";
        //表示件数
        msg += "[" + gsMsg.getMessage("cmn.number.display") + "]";
        msg += form.getNtp094DefLine();
        msg += gsMsg.getMessage("cmn.number");
        //表示位置設定
        msg += "\r\n[" + gsMsg.getMessage("ntp.110") + "]";
        if (form.getNtp094Position() == GSConstNippou.DAY_POSITION_LEFT) {
            msg += gsMsg.getMessage("ntp.112");
        } else {
            msg += gsMsg.getMessage("ntp.113");
        }
        //メンバー表示順
        msg += "\r\n[" + gsMsg.getMessage("cmn.sortby.members") + "]";
        msg += gsMsg.getMessage("cmn.first.key");
        msg += "：";
        if (form.getNtp094SortKey1() == GSConstNippou.SORT_KEY_ALL[0]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[0];
        } else if (form.getNtp094SortKey1() == GSConstNippou.SORT_KEY_ALL[1]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[1];
        } else if (form.getNtp094SortKey1() == GSConstNippou.SORT_KEY_ALL[2]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[2];
        } else if (form.getNtp094SortKey1() == GSConstNippou.SORT_KEY_ALL[3]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[3];
        } else if (form.getNtp094SortKey1() == GSConstNippou.SORT_KEY_ALL[4]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[4];
        } else if (form.getNtp094SortKey1() == GSConstNippou.SORT_KEY_ALL[5]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[5];
        }
        msg += " ";
        if (form.getNtp094SortOrder1() == GSConst.ORDER_KEY_ASC) {
            msg += gsMsg.getMessage("cmn.order.asc");
        } else {
            msg += gsMsg.getMessage("cmn.order.desc");
        }
        msg += "\r\n";
        msg += gsMsg.getMessage("cmn.second.key");
        msg += "：";
        if (form.getNtp094SortKey2() == GSConstNippou.SORT_KEY_ALL[0]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[0];
        } else if (form.getNtp094SortKey2() == GSConstNippou.SORT_KEY_ALL[1]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[1];
        } else if (form.getNtp094SortKey2() == GSConstNippou.SORT_KEY_ALL[2]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[2];
        } else if (form.getNtp094SortKey2() == GSConstNippou.SORT_KEY_ALL[3]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[3];
        } else if (form.getNtp094SortKey2() == GSConstNippou.SORT_KEY_ALL[4]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[4];
        } else if (form.getNtp094SortKey2() == GSConstNippou.SORT_KEY_ALL[5]) {
            msg += GSConstNippou.SORT_KEY_ALL_TEXT[5];
        }
        msg += " ";
        if (form.getNtp094SortOrder2() == GSConst.ORDER_KEY_ASC) {
            msg += gsMsg.getMessage("cmn.order.asc");
        } else {
            msg += gsMsg.getMessage("cmn.order.desc");
        }

        //デフォルト表示グループ
        msg += "\r\n[" + gsMsg.getMessage("schedule.sch093.2") + "]";
        if (form.getNtp094DefGroup().startsWith("M")) {
            CmnMyGroupDao myGroupDao = new CmnMyGroupDao(con);
            int myGrpSid = Integer.parseInt(form.getNtp094DefGroup().substring(1));
            CmnMyGroupModel myGroupMdl = myGroupDao.getMyGroupInfo(myGrpSid);
            if (myGroupMdl != null) {
                msg += myGroupMdl.getMgpName();
            }
        } else {
            CmnGroupmDao groupDao = new CmnGroupmDao(con);
            CmnGroupmModel groupMdl = groupDao.select(
                    Integer.parseInt(form.getNtp094DefGroup()));
            msg += groupMdl.getGrpName();
        }

        //スケジュール表示
        msg += "\r\n[" + gsMsg.getMessage("ntp.115") + "]";
        if (form.getNtp094SchKbn() == GSConstNippou.SCH_DSP_YES) {
            msg += gsMsg.getMessage("reserve.show.ok");
        } else {
            msg += gsMsg.getMessage("fil.65");
        }

        //ログ出力処理
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        ntpBiz.outPutLog(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, msg);

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);
        forward = map.findForward("gf_msg");
        return forward;
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
        Ntp094Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ntp094back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msgState = "touroku.kanryo.object";
        String key1 = gsMsg.getMessage("cmn.display.settings");
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Ntp094Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("ntp094back");
        return forward;
    }

    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Ntp094Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");
        log__.debug("form = " + form);
        ActionForward forward = null;

        Ntp094Biz biz = new Ntp094Biz(con, getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Ntp094ParamModel paramMdl = new Ntp094ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        return forward;
    }
}

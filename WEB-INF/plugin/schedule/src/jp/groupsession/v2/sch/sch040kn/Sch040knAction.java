package jp.groupsession.v2.sch.sch040kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040knAction extends AbstractScheduleAction {

    /** エラーの種類 閲覧不可データ */
    private static final int ERRORTYPE_VIEW__ = 0;
    /** エラーの種類 データなし */
    private static final int ERRORTYPE_NONE__ = 1;
    /** エラーの種類 想定外エラー */
    private static final int ERRORTYPE_UNEXPECTED__ = 9;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch040knAction.class);

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

        log__.debug("START_SCH040kn");
        ActionForward forward = null;
        Sch040knForm uform = (Sch040knForm) form;

        //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
        int selectUserSid = NullDefault.getInt(uform.getSch010SelectUsrSid(), -1);
        if (selectUserSid >= 0) {
            //公開範囲についてのチェック
            int scdSid = NullDefault.getInt(uform.getSch010SchSid(), -1);
            if (scdSid != -1) {
                SchDataDao scdDao = new SchDataDao(con);
                SchDataModel scdMdl = scdDao.getEditCheckData(scdSid);
                int sessionUsrSid = getRequestModel(req).getSmodel().getUsrsid();

                Sch010Biz biz = new Sch010Biz(getRequestModel(req));
                if (!biz.checkViewOk(scdMdl, sessionUsrSid, con)) {
                    return getSubmitErrorPage(map, req);
                }
            }
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("040kn_week")) {
            //日間スケジュール
            forward = map.findForward("040kn_week");
        } else if (cmd.equals("040kn_month")) {
            //月間スケジュール
            forward = map.findForward("040kn_month");
        } else if (cmd.equals("040kn_day")) {
            //日間スケジュール
            forward = map.findForward("040kn_day");
        } else if (cmd.equals("040kn_back")) {
            //画面遷移先を取得
            forward = __doBack(map, uform, req, res, con);
        } else if (cmd.equals("040kn_fileDownload")) {
            //ファイルダウンロード
            forward = __doDownLoadTemp(map, uform, req, res, con);
        } else {
            //スケジュール詳細・確認
            forward = __doInit(map, uform, req, res, con);
        }
        log__.debug("END_SCH040kn");
        return forward;
    }

    /**
     * <br>リクエストを解析し画面遷移先を取得する
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sch040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        
        if (!NullDefault.getString(form.getCmd(), "").equals(GSConstSchedule.CMD_ADD)
                && !NullDefault.getString(form.getCmd(), "").equals(GSConstSchedule.CMD_EDIT)) {
            //想定外エラー
            return __doDataError(map, form, req, res, con, ERRORTYPE_UNEXPECTED__);
        }
        
        con.setAutoCommit(true);
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        Sch040knBiz biz = new Sch040knBiz(reqMdl);

        Sch040knParamModel paramMdl = new Sch040knParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);

        if (form.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
            if (!form.isSch040ViewFlg()) {
                //編集データ閲覧権限チェック
                forward = __doDataError(map, form, req, res, con, ERRORTYPE_VIEW__);
            } else if (!form.isSch040DataFlg()) {
                //編集データ有無チェック
                forward = __doDataError(map, form, req, res, con, ERRORTYPE_NONE__);
            } else {
                forward = map.getInputForward();
            }
        } else {
            forward = map.getInputForward();
        }
        con.setAutoCommit(false);
        return forward;
    }
    
    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoadTemp(
            ActionMapping map,
            Sch040knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        //スケジュールSID
        int scdSid = NullDefault.getInt(form.getSch010SchSid(), -1);
        //バイナリSID
        Long binSid = NullDefault.getLong(form.getSch040knBinSid(), 0);
        SchCommonBiz schBiz = new SchCommonBiz(con);
        //添付ファイルがダウンロード可能かチェックする
        if (!schBiz.isCheckDLFile(con, scdSid, binSid)) {
            return getSubmitErrorPage(map, req);
        }
        
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textDownload = gsMsg.getMessage("cmn.download");
        
        CommonBiz cmnBiz = new CommonBiz();

        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));
        
        SchDataDao scdDao = new SchDataDao(con);
        SchDataModel scdMdl = scdDao.getSchData(scdSid);
        
        StringBuilder builder = new StringBuilder();
        builder.append("[" + gsMsg.getMessage("cmn.title") + "]");
        builder.append(scdMdl.getScdTitle());
        builder.append("\r\n");
        builder.append("[" + gsMsg.getMessage("cmn.file.name") + "]");
        builder.append(cbMdl.getBinFileName());
        //ログ出力処理
        schBiz.outPutLog(
                map, req, res, textDownload,
                GSConstLog.LEVEL_INFO, builder.toString(), String.valueOf(binSid));

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        cbMdl.removeTempFile();

        return null;
    }

    /**
     * <br>リクエストを解析し画面遷移先を取得する
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Sch040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        ActionForward forward = null;
        String dspMod = form.getDspMod();
        String listMod = form.getListMod();
        if (listMod.equals(GSConstSchedule.DSP_MOD_LIST)) {
            forward = map.findForward("040kn_list");
            return forward;
        }
        if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
            forward = map.findForward("040kn_week");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
            forward = map.findForward("040kn_month");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
            forward = map.findForward("040kn_day");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MAIN)) {
            forward = map.findForward("040kn_main");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
            forward = map.findForward("040kn_kojin");
        } else {
            //デフォルト遷移先(例外処理)
            forward = map.findForward("040kn_week");
        }
        return forward;
    }

    /**
     * <br>[機  能] スケジュールデータに関するエラーページ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param errorType エラーの種類
     * @return ActionForward
     */
    private ActionForward __doDataError(ActionMapping map, Sch040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            int errorType) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        String listMod = NullDefault.getString(form.getListMod(), "");
        if (listMod.equals(GSConstSchedule.DSP_MOD_LIST)) {
            urlForward = map.findForward("040kn_list");
        } else {
            String dspMod = NullDefault.getString(form.getDspMod(), "");
            if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
                urlForward = map.findForward("040kn_week");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {

                urlForward = map.findForward("040kn_month");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
                urlForward = map.findForward("040kn_day");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MAIN)) {
                urlForward = map.findForward("040kn_main");
            } else if (dspMod.equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
                urlForward = map.findForward("040kn_kojin");
            } else {
                urlForward = map.findForward("040kn_week");
            }
        }

        //エラーメッセージ
        if (errorType == ERRORTYPE_NONE__) {
            //変更
            String textSchedule = gsMsg.getMessage(req, "schedule.108");
            String textChange = gsMsg.getMessage(req, "cmn.change");
            cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                    textSchedule, textChange));
        } else if (errorType == ERRORTYPE_VIEW__) {
            cmn999Form.setMessage(msgRes.getMessage("error.notaccess.scd"));
        } else if (errorType == ERRORTYPE_UNEXPECTED__) {
            urlForward = map.findForward("040kn_week");
            cmn999Form.setMessage(msgRes.getMessage("error.schedule.unexpected"));
            //エラーログ出力
            SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
            schBiz.outPutUnexpectedErrorLog(form.getDspMod(), form.getCmd(),
                    form.getSch010SelectUsrSid(), form.getSch010SelectUsrKbn(),
                    form.getSch010SelectDate(),
                    req);
        }

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("schDailyDate", form.getSchDailyDate());
        cmn999Form.addHiddenParam("sch100SelectUsrSid", form.getSch100SelectUsrSid());
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}

package jp.groupsession.v2.sch.sch210;

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
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール確認ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch210Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch210Action.class);

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

        log__.debug("START_SCH210");
        ActionForward forward = null;
        Sch210Form uform = (Sch210Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);

        if (cmd.equals("210_fileDownload")) {
            //ファイルダウンロード
            forward = __doDownLoadTemp(map, uform, req, res, con);
        } else {
            //スケジュール詳細・確認
            forward = __doInit(map, uform, req, res, con);
            log__.debug("forward==>" + forward.getPath());
        }

        log__.debug("END_SCH210");
        return forward;
    }

    /**
     * <br>初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sch210Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        con.setAutoCommit(true);
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        Sch210Biz biz = new Sch210Biz(reqMdl);

        Sch210ParamModel paramMdl = new Sch210ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);

        //編集データ有無チェック
        if (form.isSch040DataFlg() == false) {
            forward = __doNoneDataError(map, form, req, res, con);
        } else {
            forward = map.getInputForward();
        }
        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>登録・更新完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNoneDataError(ActionMapping map, Sch210Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        GsMessage gsMsg = new GsMessage();

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);

        //スケジュール
        String textSchedule = gsMsg.getMessage(req, "schedule.108");
        cmn999Form.setMessage(msgRes.getMessage("search.data.notfound",
                textSchedule));

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
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
            Sch210Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        //スケジュールSID
        int scdSid = NullDefault.getInt(form.getSch010SchSid(), -1);
        //バイナリSID
        Long binSid = NullDefault.getLong(form.getSch040knBinSid(), 0);
        SchCommonBiz schBiz = new SchCommonBiz(con);
        //ネットワーク詳細情報の添付ファイルがダウンロード可能かチェックする
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
                map, req, res, textDownload, GSConstLog.LEVEL_INFO, builder.toString(),
                String.valueOf(cbMdl.getBinSid()));

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        cbMdl.removeTempFile();

        return null;
    }
}

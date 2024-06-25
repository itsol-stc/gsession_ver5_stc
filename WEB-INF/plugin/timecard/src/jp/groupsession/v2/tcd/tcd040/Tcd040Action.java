package jp.groupsession.v2.tcd.tcd040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardSubAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdManagerModel;


/**
 * <br>[機  能] タイムカード勤怠集計画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd040Action extends AbstractTimecardSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd040Action.class);

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        //ダウンロードフラグ
        String downLoadFlg = NullDefault.getString(req.getParameter("csvOut"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("tcd040_export")) {
                log__.debug("CSVファイルダウンロード");
                return true;
        }
        return false;
    }

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
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

        log__.debug("tcd040 start");
        ActionForward forward = null;
        Tcd040Form myForm = (Tcd040Form) form;
        TimecardBiz tcdBiz = new TimecardBiz();
        RequestModel reqMdl = getRequestModel(req);
        //権限チェック
        if (!((tcdBiz.isAccessOk(req, con, myForm.getTcd040SltGroup(), reqMdl))
                && (tcdBiz.isAccessOk(req, con, myForm.getTcd040SltGroupSv(), reqMdl)))) {
            __setAccessErrorParam(map, req, myForm);
            return map.findForward("gf_msg");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("tcd040_back")) {
            //戻る
            if (myForm.getTcdBackScreen() == GSConstTimecard.TCD_BACKSCREEN_ADMIN) {
                forward = map.findForward("backAdmin");
            } else {
                forward = map.findForward("back");
            }
        } else if (cmd.equals("tcd040_export")) {
            //エクスポート
            forward = __doDownLoad(map, myForm, req, res, con);
        } else if (cmd.equals("tcd040_search")) {
            //検索
            __doSearch(map, myForm, req, res, con);
            forward = __doInit(map, myForm, req, res, con);
        } else {
            //初期表示処理
            forward = __doInit(map, myForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Tcd040Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        log__.debug("初期表示");
        con.setAutoCommit(true);

        //Formの初期値を設定
        RequestModel reqMdl = getRequestModel(req);
        Tcd040ParamModel paramMdl = new Tcd040ParamModel();
        paramMdl.setParam(form);
        Tcd040Biz biz = new Tcd040Biz(reqMdl);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }
    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doSearch(ActionMapping map,
                                    Tcd040Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        log__.debug("検索処理");

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req));
        if (errors.isEmpty()) {
            Tcd040ParamModel paramMdl = new Tcd040ParamModel();
            paramMdl.setParam(form);

            paramMdl.setTcd040SearchFlg("1");
            //Formの検索条件をsaveへ設定
            paramMdl.save();

            paramMdl.setFormData(form);
        } else {
            addErrors(req, errors);
        }
    }

    /**
     * <br>[機  能] 戻るボタンクリック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map,
                                    Tcd040Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        ActionForward forward = null;
        forward = map.findForward("back");
        return forward;
    }

    /**
     * <br>[機  能] エクスポートボタンクリック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception CSV作成時例外
     */
    private ActionForward __doDownLoad(ActionMapping map,
                                    Tcd040Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        log__.debug("エクスポート処理");

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        Tcd040Biz biz = new Tcd040Biz(reqMdl);
        String tempDir = biz.getTempDir();

        String fileName = Tcd040CsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        __doExport(form, reqMdl, con, tempDir);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        biz.deleteTempDir();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.export");

        //ログ出力
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        tcdBiz.outPutTimecardLog(map, reqMdl, con, msg, GSConstLog.LEVEL_INFO,
                fileName);

        return null;
    }
    /**
     * <br>[機  能] エクスポート処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @throws Exception 実行例外
     */
    private void __doExport(Tcd040Form form,
            RequestModel reqMdl, Connection con, String outDir)
            throws Exception {

        log__.debug("エクスポート処理(CSV)");

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = tcdBiz.getTcdAdmConfModel(sessionUsrSid, con);

        //検索結果を取得
        log__.debug("<==CSV出力条件==>");
        log__.debug("年==>" + form.getTcd040SltYearSv());
        log__.debug("月==>" + form.getTcd040SltMonthSv());
        log__.debug("年(To)==>" + form.getTcd040SltYearToSv());
        log__.debug("月(To)==>" + form.getTcd040SltMonthToSv());
        log__.debug("グループ==>" + form.getTcd040SltGroupSv());

        Tcd040ParamModel paramMdl = new Tcd040ParamModel();
        paramMdl.setParam(form);

        Tcd040Biz biz = new Tcd040Biz(reqMdl);
        ArrayList<TcdManagerModel> tcdMngList = biz.getSearchResultList(
                paramMdl, admConf, sessionUsrSid, con);
        List<TcdHolidayInfModel> tcdHolDataList = biz._getHolData(con);
        paramMdl.setFormData(form);

        //CSVファイルを作成
        Tcd040CsvWriter write = new Tcd040CsvWriter(reqMdl);
        write.setTcdMngList(tcdMngList);
        write.setSessionUserSid(sessionUsrSid);
        write.setTcdAverageData(form.getAverageData());
        write.setTcdTotalData(form.getTotalData());
        write.setHolDataList(tcdHolDataList);
        write.outputCsv(con, outDir);

    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setAccessErrorParam(
        ActionMapping map,
        HttpServletRequest req,
        Tcd040Form form) {

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
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("usrKbn", form.getUsrKbn());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());

        req.setAttribute("cmn999Form", cmn999Form);

    }
}


package jp.groupsession.v2.ntp.ntp062;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.adr.adr150.Adr150Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpAnMemberDao;
import jp.groupsession.v2.ntp.dao.NtpAnMemberHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenPermitDao;
import jp.groupsession.v2.ntp.excel.NtpSampleAnkenExcelWriter;
import jp.groupsession.v2.ntp.excel.NtpSampleExcelModel;
import jp.groupsession.v2.ntp.model.NtpAnMemberHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnMemberModel;
import jp.groupsession.v2.ntp.model.NtpAnShohinHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnShohinModel;
import jp.groupsession.v2.ntp.model.NtpAnkenHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.ntp061.Ntp061Biz;
import jp.groupsession.v2.ntp.ntp130.Ntp130Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 案件インポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp062Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp062Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp062Form thisForm = (Ntp062Form) form;
        log__.debug("*****ntp061ReturnPage = " + thisForm.getNtp061ReturnPage());

        //ユーザ選択コマンド処理
        UserGroupSelectModel usrSel = thisForm.getNtp061NanPermitUsrGrp();
        usrSel.executeCmd(cmd, "ntp061NanPermitUsrGrp");


        if (cmd.equals("backNtp062")) {
            forward = map.findForward("ntp060");
            //テンポラリディレクトリの削除
            Ntp062Biz biz = new Ntp062Biz(con, getRequestModel(req));
            biz.deleteTempDir();

        //インポートボタン押下
        } else if (cmd.equals("062_import")) {
            log__.debug("インポートボタン押下");
            forward = __doImportCheck(map, thisForm, req, res, con);
        //サンプルダウンロードリンククリック
        } else if (cmd.equals("ntp062_sample")) {
            log__.debug("サンプルダウンロードリンククリック");
            __doSampleDownLoad(req, res, con);

            GsMessage gsMsg = new GsMessage();
            /** メッセージ ダウンロード **/
            String download = gsMsg.getMessage(req, "cmn.download");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, reqMdl);
            ntpBiz.outPutLog(
                    map,
                    download, GSConstLog.LEVEL_INFO, "sample.xls");
        } else if (cmd.equals("delete")) {
            forward = __doDeleteTmp(map, thisForm, req, res, con);
        } else if (cmd.equals("backNtp200")) {
            forward = map.findForward("ntp200");
        } else if (cmd.equals("okNtp061")) {
            forward = __doRegistConfirmation(map, thisForm, req, res, con);
        } else if (cmd.equals("okNtp061pop")) {
            forward = __doRegistConfPop(map, thisForm, req, res, con);
        } else if (cmd.equals("addOk")) {
            forward = __doRegistOk(map, thisForm, req, res, con);
        } else if (cmd.equals("addOkPop")) {
            forward = __doRegistOkPop(map, thisForm, req, res, con, 0);
        } else if (cmd.equals("addOkPopNtp")) {
            forward = __doRegistOkPop(map, thisForm, req, res, con, 1);
        } else if (cmd.equals("del")) {
            forward = __doDeleteConfirmation(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteOk")) {
            forward = __doDeleteOk(map, thisForm, req, res, con);
        } else if (cmd.equals("searchCompany")) {
            Adr150Form adrForm = new Adr150Form();
            adrForm.setAdr150ReturnPage("ntp061");
            req.setAttribute("adr150Form", adrForm);
            forward = map.findForward("adr150");
        } else if (cmd.equals("searchShohin")) {
            Ntp130Form ntpForm = new Ntp130Form();
            ntpForm.setNtp130ReturnPage("ntp061");
            ntpForm.setNtp130DspMode("check");
            req.setAttribute("ntp130Form", ntpForm);
            forward = map.findForward("ntp130");
        } else if (cmd.equals("delShohin")) {
            forward = __doDelShohin(map, thisForm, req, res, con);
        } else if (cmd.equals("prevPage")) {
            //アドレス帳 前ページクリック
            thisForm.setNtp061Adrpage(thisForm.getNtp061Adrpage() - 1);
            thisForm.setNtp061AdrKbn(1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //アドレス帳 次ページクリック
            thisForm.setNtp061Adrpage(thisForm.getNtp061Adrpage() + 1);
            thisForm.setNtp061AdrKbn(1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("changePageTop")) {
            //アドレス帳 上ページコンボ変更
            thisForm.setNtp061Adrpage(thisForm.getNtp061AdrpageTop());
            thisForm.setNtp061AdrKbn(1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("changePageBottom")) {
            //アドレス帳 下ページコンボ変更
            thisForm.setNtp061Adrpage(thisForm.getNtp061AdrpageBottom());
            thisForm.setNtp061AdrKbn(1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("init")) {
            //アドレス帳 五十音タブ選択
            thisForm.setNtp061AdrKbn(1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("changeTab")) {
            //タブ変更
            thisForm.setNtp061AdrKbn(1);
            __resetSvData(thisForm);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("search")) {
            log__.debug("検索ボタンクリック");
            thisForm.setNtp061AdrKbn(1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("itmsearch")) {
            //商品 検索
            thisForm.setNtp061ItmKbn(1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("itmprevPage")) {
            //商品 前ページクリック
            thisForm.setNtp061ItmKbn(1);
            thisForm.setNtp061ItmPageTop(thisForm.getNtp061ItmPageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("itmnextPage")) {
            //商品 次ページクリック
            thisForm.setNtp061ItmKbn(1);
            thisForm.setNtp061ItmPageTop(thisForm.getNtp061ItmPageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("itmchangePage")) {
            //商品 ページコンボ変更
            thisForm.setNtp061ItmKbn(1);
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("itmok")) {
            //商品 選択ボタン
            forward = __doItmOk(map, thisForm, req, res, con);
        } else {
            log__.debug("*****初期表示");
            forward = __doInit(map, thisForm, req, res, con);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Ntp062Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        BaseUserModel buMdl = getSessionUserModel(req);

        // トランザクショントークン設定
        saveToken(req);

        if (NullDefault.getString(req.getParameter("CMD"), "").equals(GSConstNippou.POP_UP)) {
            //ポップアップ区分
            form.setNtp061PopKbn(1);
        }

        Ntp062Biz biz = new Ntp062Biz(
                con, getRequestModel(req));

        Ntp062ParamModel paramMdl = new Ntp062ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, buMdl, con, getCountMtController(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 添付ファイル削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDeleteTmp(ActionMapping map,
                                    Ntp062Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //テンポラリディレクトリの初期化を行う
        Ntp062Biz biz = new Ntp062Biz(
                con, getRequestModel(req));
        biz.clearTempDir();

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除(商品)ボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doDelShohin(
        ActionMapping map,
        Ntp062Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        form.setNtp061ChkShohinSidList(
            cmnBiz.getDeleteMember(form.getNtp061SelectShohin(), form.getNtp061ChkShohinSidList()));

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 登録確認処理
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doRegistConfirmation(ActionMapping map,
        Ntp062Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception, SQLException {

        ActionForward forward = null;

        //入力チェック
        ActionErrors errors = form.validateCheck(con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //登録確認画面を設定
        forward = __setConfirmationDsp(map, req, form,
            "=addOk", "touroku.kakunin.once", StringUtilHtml.transToHTml(form.getNtp061NanName()));
        return forward;
    }

    /**
     * <br>[機  能] 登録確認処理(popup時)
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doRegistConfPop(ActionMapping map,
        Ntp062Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception, SQLException {

        //入力チェック
        ActionErrors errors = form.validateCheck(con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {
            form.setNtp061AddFlg(1);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 登録確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doRegistOk(ActionMapping map,
        Ntp062Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            GsMessage gsMsg = new GsMessage(req);
            String entry = gsMsg.getMessage("cmn.entry");
            String change = gsMsg.getMessage("cmn.change");

            int usrSid = this.getSessionUserModel(req).getUsrsid();
            MlCountMtController cntCon = getCountMtController(req);

            Ntp061Biz biz = new Ntp061Biz(con, cntCon, getRequestModel(req));
            Ntp062ParamModel param = new Ntp062ParamModel();
            param.setParam(form);
            biz.doResistAnken(usrSid, param);
            param.setFormData(form);


            commitFlg = true;

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            String opCode = "";
            if (form.getNtp060ProcMode().equals(GSConstNippou.CMD_ADD)) {
                opCode = entry;
            } else {
                opCode = change;
            }

            ntpBiz.outPutLog(
             map, req, res, opCode, GSConstLog.LEVEL_TRACE, form.getNtp061NanName());

            //完了画面設定
            return __setCompDsp(map, req, form,
                    "touroku.kanryo.object", StringUtilHtml.transToHTml(form.getNtp061NanName()));

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 登録確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param backKbn 戻り先 0:案件選択画面 1:日報登録画面
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doRegistOkPop(ActionMapping map,
        Ntp062Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int backKbn)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            int usrSid = this.getSessionUserModel(req).getUsrsid();
            NtpAnkenDao ankenDao = new NtpAnkenDao(con);
            NtpAnkenModel ankenMdl = __createNtpAnken(usrSid);

            String mitumori = NullDefault.getStringZeroLength(
                    form.getNtp061NanKinMitumori().replaceAll(",", ""), "0");
            String jutyu = NullDefault.getStringZeroLength(
                    form.getNtp061NanKinJutyu().replaceAll(",", ""), "0");
            String acoSid
                = NullDefault.getStringZeroLength(form.getNtp061CompanySid(), "-1");
            String abaSid
                = NullDefault.getStringZeroLength(form.getNtp061CompanyBaseSid(), "-1");

            UDate date = new UDate();
            date.setZeroHhMmSs();

            ankenMdl.setNanDate(date);
            ankenMdl.setNanCode(form.getNtp061NanCode());
            ankenMdl.setNanName(form.getNtp061NanName());
            ankenMdl.setNanDetial(form.getNtp061NanSyosai());
            ankenMdl.setAcoSid(Integer.parseInt(acoSid));
            ankenMdl.setAbaSid(Integer.parseInt(abaSid));
            ankenMdl.setNgpSid(form.getNtp061NgpSid());
            ankenMdl.setNanMikomi(form.getNtp061NanMikomi());
            ankenMdl.setNanKinMitumori(Integer.parseInt(mitumori));
            ankenMdl.setNanKinJutyu(Integer.parseInt(jutyu));
            ankenMdl.setNanSyodan(form.getNtp061NanSyodan());
            ankenMdl.setNanState(form.getNtp061NanState());
            ankenMdl.setNcnSid(form.getNtp061NcnSid());

            UDate mitumoriDate = new UDate();
            mitumoriDate.setDate(
                    Integer.parseInt(form.getNtp061MitumoriYear()),
                    Integer.parseInt(form.getNtp061MitumoriMonth()),
                    Integer.parseInt(form.getNtp061MitumoriDay()));
            mitumoriDate.setZeroHhMmSs();
            UDate jutyuDate = new UDate();
            jutyuDate.setDate(
                    Integer.parseInt(form.getNtp061JutyuYear()),
                    Integer.parseInt(form.getNtp061JutyuMonth()),
                    Integer.parseInt(form.getNtp061JutyuDay()));
            jutyuDate.setZeroHhMmSs();
            ankenMdl.setNanMitumoriDate(mitumoriDate);
            ankenMdl.setNanJutyuDate(jutyuDate);

            int nanSid = -1;

            //追加モード
            MlCountMtController cntCon = getCountMtController(req);
            //SID採番
            nanSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_ANKEN, usrSid);
            ankenMdl.setNanSid(nanSid);
            ankenDao.insert(ankenMdl);


            //案件商品情報の登録
            NtpAnShohinModel  anShohinMdl = null;
            NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con);
            if (form.getNtp061ChkShohinSidList() != null
                && form.getNtp061ChkShohinSidList().length > 0) {
                for (String shohinSid : form.getNtp061ChkShohinSidList()) {
                    anShohinMdl = __createNtpAnShohin(usrSid);
                    anShohinMdl.setNhnSid(Integer.parseInt(shohinSid));
                    anShohinMdl.setNanSid(nanSid);
                    anShohinDao.insert(anShohinMdl);
                }
            }

            //担当者
            NtpAnMemberDao anMemberDao = new NtpAnMemberDao(con);
            NtpAnMemberModel anMemberMdl = null;
            String[] svUsers = form.getSv_users();
            if (svUsers != null) {
                for (int i = 0; i < svUsers.length; i++) {
                    if (GSValidateUtil.isNumber(svUsers[i])) {
                        anMemberMdl = __createNtpAnMember(usrSid);
                        anMemberMdl.setUsrSid(Integer.parseInt(svUsers[i]));
                        anMemberMdl.setNanSid(nanSid);
                        anMemberDao.insert(anMemberMdl);
                    }
                }
            }


            //履歴の新規登録
            NtpAnkenHistoryDao hisDao = new NtpAnkenHistoryDao(con);
            NtpAnShohinHistoryDao shohinHisDao = new NtpAnShohinHistoryDao(con);
            NtpAnMemberHistoryDao memberHisDao = new NtpAnMemberHistoryDao(con);

            //履歴は見積もり金額、受注金額を0に設定
            NtpAnkenHistoryModel hisMdl = new NtpAnkenHistoryModel();
            BeanUtils.copyProperties(hisMdl, ankenMdl);
            UDate nanMonth = new UDate();
            nanMonth.setYear(hisMdl.getNanDate().getYear());
            nanMonth.setMonth(hisMdl.getNanDate().getMonth());
            nanMonth.setDay(nanMonth.getMaxDayOfMonth());
            nanMonth.setZeroHhMmSs();
            hisMdl.setNanMonth(nanMonth);

            //履歴は見積もり金額、受注金額を0に設定
            hisMdl.setNanKinJutyu(0);
            hisMdl.setNanKinMitumori(0);

            //履歴SID採番
            int nahSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
            hisMdl.setNahSid(nahSid);
            hisDao.insert(hisMdl);

            if (anShohinMdl != null) {
                NtpAnShohinHistoryModel shohinHisMdl = new NtpAnShohinHistoryModel();
                if (form.getNtp061ChkShohinSidList() != null
                        && form.getNtp061ChkShohinSidList().length > 0) {
                    for (String shohinSid : form.getNtp061ChkShohinSidList()) {
                        shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                        shohinHisMdl.setNahSid(nahSid);
                        shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                        shohinHisMdl.setNanSid(nanSid);
                        shohinHisDao.insert(shohinHisMdl);
                    }
                }
            }

            if (anMemberMdl != null) {
                NtpAnMemberHistoryModel memberHisMdl = new NtpAnMemberHistoryModel();
                if (form.getSv_users() != null
                        && form.getSv_users().length > 0) {
                    for (String uSid : form.getSv_users()) {
                        if (GSValidateUtil.isNumber(uSid)) {
                            memberHisMdl = __createNtpAnMemberHistory(usrSid);
                            memberHisMdl.setNahSid(nahSid);
                            memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                            memberHisMdl.setNanSid(nanSid);
                            memberHisDao.insert(memberHisMdl);
                        }
                    }
                }
            }


            commitFlg = true;

            //完了
            if (backKbn == 0) {
                //案件選択画面へ遷移
                form.setNtp061AddCompFlg(1);
            } else {
                //日報登録画面へ遷移
                form.setNtp061AnkenSid(nanSid);
                form.setNtp061AddCompFlg(2);
            }

            return __doInit(map, form, req, res, con);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 削除確認処理
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteConfirmation(ActionMapping map,
        Ntp062Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception, SQLException {

        // トランザクショントークン設定
        saveToken(req);

        ActionForward forward = null;

        NtpAnkenDao ankenDao = new NtpAnkenDao(con);
        NtpAnkenModel ankenMdl = ankenDao.select(form.getNtp060NanSid());

        //削除確認画面を設定
        forward = __setConfirmationDsp(map, req, form,
            "=deleteOk", "sakujo.kakunin.once",
            StringUtilHtml.transToHTml(ankenMdl.getNanName()));
        return forward;
    }

    /**
     * <br>[機  能] 削除確認画面でOKボタン押下
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
    private ActionForward __doDeleteOk(ActionMapping map,
        Ntp062Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {

            //削除処理実行
            NtpAnkenDao ankenDao = new NtpAnkenDao(con);
            NtpAnkenHistoryDao ankenHisDao = new NtpAnkenHistoryDao(con);
            NtpAnkenModel ankenMdl = ankenDao.select(form.getNtp060NanSid());
            ankenDao.delete(form.getNtp060NanSid());
            ankenHisDao.deleteAnken(form.getNtp060NanSid());

            //案件商品情報の削除
            NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con);
            NtpAnShohinHistoryDao anShohinHisDao = new NtpAnShohinHistoryDao(con);
            anShohinDao.delete(form.getNtp060NanSid());
            anShohinHisDao.deleteAnken(form.getNtp060NanSid());

            //メンバー削除
            NtpAnMemberDao anMemberDao = new NtpAnMemberDao(con);
            NtpAnMemberHistoryDao anMemberHisDao = new NtpAnMemberHistoryDao(con);
            anMemberDao.delete(form.getNtp060NanSid());
            anMemberHisDao.deleteAnken(form.getNtp060NanSid());
            //案件権限情報削除
            NtpAnkenPermitDao napDao = new NtpAnkenPermitDao(con);
            napDao.delete(form.getNtp060NanSid());

            commitFlg = true;

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            ntpBiz.outPutLog(
                    map, req, res,
                    delete,
                    GSConstLog.LEVEL_TRACE, ankenMdl.getNanName());

            //完了画面設定
            return __setCompDsp(map, req, form, "sakujo.kanryo.object",
                    StringUtilHtml.transToHTml(ankenMdl.getNanName()));

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param cmd リクエストコマンドパラメータ
     * @param mesParam メッセージパラメータ
     * @param mesValue メッセージ値
     * @return ActionForward フォワード
     */
    private ActionForward __setConfirmationDsp(ActionMapping map,
        HttpServletRequest req,
        Ntp062Form form,
        String cmd,
        String mesParam,
        String mesValue) {

        MessageResources msgRes = getResources(req);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forwardOk = map.findForward("redraw");
        ActionForward forwardCancel = map.findForward("redraw");

        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + cmd);
        cmn999Form.setUrlCancel(forwardCancel.getPath() + "?" + GSConst.P_CMD + "=redraw");

        cmn999Form.setMessage(msgRes.getMessage(mesParam, mesValue));
        cmn999Form.addHiddenParam("ntp060NanSid", form.getNtp060NanSid());
        cmn999Form.addHiddenParam("ntp060ProcMode", form.getNtp060ProcMode());
        cmn999Form.addHiddenParam("ntp061CompanySid", form.getNtp061CompanySid());
        cmn999Form.addHiddenParam("ntp061CompanyBaseSid", form.getNtp061CompanyBaseSid());
        form.setNtp061HiddenParam(cmn999Form, form);
        form.setNtp060HiddenParam(cmn999Form, form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param mesParam メッセージパラメータ
     * @param mesValue メッセージ値
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
        HttpServletRequest req,
        Ntp062Form form,
        String mesParam,
        String mesValue) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("ntp060");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(mesParam, mesValue));

        form.setNtp060HiddenParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 案件情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnkenModel __createNtpAnken(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnkenModel mdl = new NtpAnkenModel();
        mdl.setNanAuid(usrSid);
        mdl.setNanAdate(nowDate);
        mdl.setNanEuid(usrSid);
        mdl.setNanEdate(nowDate);
        return mdl;
    }
    /**
     * <br>[機  能] 案件商品情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnShohinModel __createNtpAnShohin(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnShohinModel mdl = new NtpAnShohinModel();
        mdl.setNasAuid(usrSid);
        mdl.setNasAdate(nowDate);
        mdl.setNasEuid(usrSid);
        mdl.setNasEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 案件商品情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnShohinHistoryModel __createNtpAnShohinHistory(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnShohinHistoryModel mdl = new NtpAnShohinHistoryModel();
        mdl.setNasAuid(usrSid);
        mdl.setNasAdate(nowDate);
        mdl.setNasEuid(usrSid);
        mdl.setNasEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 担当者情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnMemberModel __createNtpAnMember(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnMemberModel mdl = new NtpAnMemberModel();
        mdl.setNamAuid(usrSid);
        mdl.setNamAdate(nowDate);
        mdl.setNamEuid(usrSid);
        mdl.setNamEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 担当者履歴情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnMemberHistoryModel __createNtpAnMemberHistory(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnMemberHistoryModel mdl = new NtpAnMemberHistoryModel();
        mdl.setNamAuid(usrSid);
        mdl.setNamAdate(nowDate);
        mdl.setNamEuid(usrSid);
        mdl.setNamEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] OKボタンをクリック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doItmOk(ActionMapping map,
                                      Ntp062Form form,
                               HttpServletRequest req,
                              HttpServletResponse res,
                                       Connection con) throws Exception {

        form.setNtp061ChkShohinSidList(form.getNtp061ItmChkShohinSidList());

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 保持データリセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form フォーム
     * @throws Exception 実行時例外
     */
    private void __resetSvData(Ntp062Form form) throws Exception {
        form.setNtp061Adrpage(1);
        form.setNtp061searchFlg(1);
        form.setNtp061svAdrCode(null);
        form.setNtp061svAdrCoName(null);
        form.setNtp061svAdrCoNameKn(null);
        form.setNtp061svAdrCoBaseName(null);
        form.setNtp061svAdrAtiSid(0);
        form.setNtp061svAdrTdfk(0);
        form.setNtp061svAdrBiko(null);
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doSearch(ActionMapping map,
                                    Ntp062Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {


        //入力チェック
        ActionErrors errors = form.validateCheckAdr(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);

        } else {
            //企業コード
            form.setNtp061svAdrCode(form.getNtp061code());
            //会社名
            form.setNtp061svAdrCoName(form.getNtp061coName());
            //会社名カナ
            form.setNtp061svAdrCoNameKn(form.getNtp061coNameKn());
            //支店・営業所名
            form.setNtp061svAdrCoBaseName(form.getNtp061coBaseName());
            //業種
            form.setNtp061svAdrAtiSid(form.getNtp061atiSid());
            //都道府県
            form.setNtp061svAdrTdfk(form.getNtp061tdfk());
            //備考
            form.setNtp061svAdrBiko(form.getNtp061biko());

            //ページ
            form.setNtp061Adrpage(1);
            form.setNtp061searchFlg(1);

        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] サンプルCSVをダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception ダウンロード時例外
     */
    private void __doSampleDownLoad(HttpServletRequest req, HttpServletResponse res,
                                   Connection con)
    throws Exception {

        String fileName = GSConstNippou.SAMPLE_ANKEN_NTP_CSV_NAME;

        //パラメータセット
        NtpSampleExcelModel excelParam = new NtpSampleExcelModel();
        excelParam.setAppRootPath(getAppRootPath());
        excelParam.setFormatFile(fileName);
        excelParam.setOutPutFile(fileName);

        //Excel出力処理
        NtpSampleAnkenExcelWriter excelWriter = new NtpSampleAnkenExcelWriter();
        excelWriter.downloadExcel(req, res, con, excelParam, getRequestModel(req));
    }

    /**
     * <br>[機  能] インポートボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception インポート処理時例外
     */
    private ActionForward __doImportCheck(ActionMapping map,
                                        Ntp062Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
    throws Exception {

        //テンポラリディレクトリパスを取得
        Ntp062Biz biz = new Ntp062Biz(con, getRequestModel(req));
        String tempDir = biz.getTempDir();

        ActionErrors errors = form.validateCheck(getRequestModel(req), tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("doImport");
    }
}

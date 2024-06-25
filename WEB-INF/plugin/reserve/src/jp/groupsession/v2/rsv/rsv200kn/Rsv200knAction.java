package jp.groupsession.v2.rsv.rsv200kn;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.AbstractReserveSubAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設一括設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv200knAction extends AbstractReserveSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv200knAction.class);

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
                                    Connection con) throws Exception {

        ActionForward forward = null;
        Rsv200knForm rsvform = (Rsv200knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("ikkatu_settei_kakutei")) {
            log__.debug("確定ボタン押下");
            forward = __doUpdate(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_ikkatu_settei")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_to_ikkatu_settei");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
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
                                    Rsv200knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {
            con.setAutoCommit(true);
            Rsv200knBiz biz = new Rsv200knBiz(getRequestModel(req), con);

            boolean processFlg = false;

            Rsv200knParamModel paramMdl = new Rsv200knParamModel();
            paramMdl.setParam(form);
            processFlg = biz.isPossibleToProcess(paramMdl);
            paramMdl.setFormData(form);

            //処理権限判定
            if (!processFlg) {
                //処理権限無し
                return getSubmitErrorPage(map, req);
            }

            //初期表示データセット
            paramMdl = new Rsv200knParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl);
            paramMdl.setFormData(form);

            con.setAutoCommit(false);
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタン押下時処理
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
    private ActionForward __doUpdate(ActionMapping map,
                                      Rsv200knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            //2重投稿
            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //入力チェック
            ActionErrors errors = form.validateCheck(con, req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            //DB更新
            Rsv200knBiz biz = new Rsv200knBiz(getRequestModel(req), con);

            Rsv200knParamModel paramMdl = new Rsv200knParamModel();
            paramMdl.setParam(form);
            biz.updateSisetuData(paramMdl);
            paramMdl.setFormData(form);

            commitFlg = true;

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

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        String logMsg = __getLogMessage(form, req, con);
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        rsvBiz.outPutLog(map, req, res,
                gsMsg.getMessage(req, "cmn.change"), GSConstLog.LEVEL_TRACE, logMsg);

        return __doUpdateComp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 施設更新完了後の画面遷移設定
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
    private ActionForward __doUpdateComp(ActionMapping map,
                                          Rsv200knForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("updateComp");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(msgRes.getMessage("hensyu.kanryo.object",
                gsMsg.getMessage(req, "reserve.rsv070.1")));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("rsvBackPgId", form.getRsvBackPgId());
        cmn999Form.addHiddenParam("rsvBackToAdminSetting", form.getRsvBackToAdminSetting());
        cmn999Form.addHiddenParam("rsvDspFrom", form.getRsvDspFrom());
        cmn999Form.addHiddenParam("rsvSelectedGrpSid", form.getRsvSelectedGrpSid());
        cmn999Form.addHiddenParam("rsvSelectedSisetuSid", form.getRsvSelectedSisetuSid());
        cmn999Form.addHiddenParam("rsv050SortRadio", form.getRsv050SortRadio());
        cmn999Form.addHiddenParam("rsv080EditGrpSid", form.getRsv080EditGrpSid());
        cmn999Form.addHiddenParam("rsv080SortRadio", form.getRsv080SortRadio());
        cmn999Form.addHiddenParam("rsv100InitFlg",
                String.valueOf(form.isRsv100InitFlg()));
        cmn999Form.addHiddenParam("rsv100SearchFlg",
                String.valueOf(form.isRsv100SearchFlg()));
        cmn999Form.addHiddenParam("rsv100SortKey", form.getRsv100SortKey());
        cmn999Form.addHiddenParam("rsv100OrderKey", form.getRsv100OrderKey());
        cmn999Form.addHiddenParam("rsv100PageTop", form.getRsv100PageTop());
        cmn999Form.addHiddenParam("rsv100PageBottom", form.getRsv100PageBottom());
        cmn999Form.addHiddenParam("rsv100selectedFromYear", form.getRsv100selectedFromYear());
        cmn999Form.addHiddenParam("rsv100selectedFromMonth", form.getRsv100selectedFromMonth());
        cmn999Form.addHiddenParam("rsv100selectedFromDay", form.getRsv100selectedFromDay());
        cmn999Form.addHiddenParam("rsv100selectedToYear", form.getRsv100selectedToYear());
        cmn999Form.addHiddenParam("rsv100selectedToMonth", form.getRsv100selectedToMonth());
        cmn999Form.addHiddenParam("rsv100selectedToDay", form.getRsv100selectedToDay());
        cmn999Form.addHiddenParam("rsv100KeyWord", form.getRsv100KeyWord());
        cmn999Form.addHiddenParam("rsv100SearchCondition", form.getRsv100SearchCondition());
        cmn999Form.addHiddenParam("rsv100TargetMok", form.getRsv100TargetMok());
        cmn999Form.addHiddenParam("rsv100TargetNiyo", form.getRsv100TargetNiyo());
        cmn999Form.addHiddenParam("rsv100CsvOutField", form.getRsv100CsvOutField());
        cmn999Form.addHiddenParam("rsv100SelectedKey1", form.getRsv100SelectedKey1());
        cmn999Form.addHiddenParam("rsv100SelectedKey2", form.getRsv100SelectedKey2());
        cmn999Form.addHiddenParam("rsv100SelectedKey1Sort", form.getRsv100SelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100SelectedKey2Sort", form.getRsv100SelectedKey2Sort());
        cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", form.getRsvIkkatuTorokuKey());
        cmn999Form.addHiddenParam("rsv100svFromYear", form.getRsv100svFromYear());
        cmn999Form.addHiddenParam("rsv100svFromMonth", form.getRsv100svFromMonth());
        cmn999Form.addHiddenParam("rsv100svFromDay", form.getRsv100svFromDay());
        cmn999Form.addHiddenParam("rsv100svToYear", form.getRsv100svToYear());
        cmn999Form.addHiddenParam("rsv100svToMonth", form.getRsv100svToMonth());
        cmn999Form.addHiddenParam("rsv100svToDay", form.getRsv100svToDay());
        cmn999Form.addHiddenParam("rsv100svGrp1", form.getRsv100svGrp1());
        cmn999Form.addHiddenParam("rsv100svGrp2", form.getRsv100svGrp2());
        cmn999Form.addHiddenParam("rsv100svKeyWord", form.getRsv100svKeyWord());
        cmn999Form.addHiddenParam("rsv100svSearchCondition", form.getRsv100svSearchCondition());
        cmn999Form.addHiddenParam("rsv100svTargetMok", form.getRsv100svTargetMok());
        cmn999Form.addHiddenParam("rsv100svTargetNiyo", form.getRsv100svTargetNiyo());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1", form.getRsv100svSelectedKey1());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2", form.getRsv100svSelectedKey2());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1Sort", form.getRsv100svSelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2Sort", form.getRsv100svSelectedKey2Sort());
        cmn999Form.addHiddenParam("rsv100SearchSvFlg",
                String.valueOf(form.isRsv100SearchSvFlg()));

        cmn999Form.addHiddenParam("rsv100dateKbn", form.getRsv100dateKbn());
        cmn999Form.addHiddenParam("rsv100apprStatus", form.getRsv100apprStatus());
        cmn999Form.addHiddenParam("rsv100svDateKbn", form.getRsv100svDateKbn());
        cmn999Form.addHiddenParam("rsv100svApprStatus", form.getRsv100svApprStatus());

        cmn999Form.addHiddenParam("rsv010LearnMoreFlg", form.getRsv010LearnMoreFlg());
        cmn999Form.addHiddenParam("rsv010sisetuKeyword", form.getRsv010sisetuKeyword());
        cmn999Form.addHiddenParam("rsv010KeyWordkbn", form.getRsv010KeyWordkbn());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", form.getRsv010sisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu",
                form.getRsv010sisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordBiko", form.getRsv010sisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordNo", form.getRsv010sisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordIsbn", form.getRsv010sisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010sisetuFree", form.getRsv010sisetuFree());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromY", form.getRsv010sisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMo", form.getRsv010sisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromD", form.getRsv010sisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromH", form.getRsv010sisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMi", form.getRsv010sisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToY", form.getRsv010sisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMo", form.getRsv010sisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToD", form.getRsv010sisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToH", form.getRsv010sisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMi", form.getRsv010sisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010sisetuKbn", form.getRsv010sisetuKbn());
        cmn999Form.addHiddenParam("rsv010grpNarrowDown", form.getRsv010grpNarrowDown());
        cmn999Form.addHiddenParam("rsv010sisetuSmoky", form.getRsv010sisetuSmoky());
        cmn999Form.addHiddenParam("rsv010sisetuChere", form.getRsv010sisetuChere());
        cmn999Form.addHiddenParam("rsv010sisetuTakeout", form.getRsv010sisetuTakeout());
        cmn999Form.addHiddenParam("rsv010svSisetuKeyword", form.getRsv010svSisetuKeyword());
        cmn999Form.addHiddenParam("rsv010svKeyWordkbn", form.getRsv010svKeyWordkbn());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisan",
                form.getRsv010svSisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisetu",
                form.getRsv010svSisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko",
                form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn",
                form.getRsv010svSisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010svSisetuFree", form.getRsv010svSisetuFree());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromY", form.getRsv010svSisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMo", form.getRsv010svSisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromD", form.getRsv010svSisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromH", form.getRsv010svSisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMi", form.getRsv010svSisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToY", form.getRsv010svSisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMo", form.getRsv010svSisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToD", form.getRsv010svSisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToH", form.getRsv010svSisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMi", form.getRsv010svSisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010svSisetuKbn", form.getRsv010svSisetuKbn());
        cmn999Form.addHiddenParam("rsv010svGrpNarrowDown", form.getRsv010svGrpNarrowDown());
        cmn999Form.addHiddenParam("rsv010svSisetuSmoky", form.getRsv010svSisetuSmoky());
        cmn999Form.addHiddenParam("rsv010svSisetuChere", form.getRsv010svSisetuChere());
        cmn999Form.addHiddenParam("rsv010svSisetuTakeout", form.getRsv010svSisetuTakeout());
        cmn999Form.addHiddenParam("rsv010InitFlg", form.getRsv010InitFlg());
        cmn999Form.addHiddenParam("rsv010SiborikomiFlg", form.getRsv010SiborikomiFlg());
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQLエラー
     */
    private String __getLogMessage(
        Rsv200knForm form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        //施設名
        msg += "[" + gsMsg.getMessage("cmn.facility.name") + "]";
        Rsv200knBiz biz = new Rsv200knBiz(getRequestModel(req), con);
        String logMsg = biz.getLogMessage(form.getRsv200TargetSisetu(), con);
        msg += logMsg;
        return msg;
    }
}

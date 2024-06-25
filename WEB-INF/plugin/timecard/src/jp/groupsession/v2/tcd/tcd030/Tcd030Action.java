package jp.groupsession.v2.tcd.tcd030;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd030Action extends AbstractTimecardAdminAction {
    /** ロギング */
    private static Log log__ = LogFactory.getLog(Tcd030Action.class);

    /**
     *<br>[機  能] 印刷プレビューへの遷移
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

        log__.debug("START:Tcd030Action");
        ActionForward forward = null;
        Tcd030Form uform = (Tcd030Form) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("base_conf")) {
            //基本設定
            forward = __doBaseConf(map, uform, req, res, con);
        } else if (cmd.equals("timezone")) {
            //時間帯設定
            forward = __doTimeZone(map, uform, req, res, con);
        } else if (cmd.equals("editAuth")) {
            //編集権限設定
            forward = __doEditAuth(map, uform, req, res, con);
        } else if (cmd.equals("outputKinmu")) {
            //勤務表一括出力
            forward = __doOutputKinmu(map);
        } else if (cmd.equals("userTimezone")) {
            //時間帯設定
            forward = __doUserTimeZone(map);
        } else if (cmd.equals("holidayKbn")) {
            //休日区分設定
            forward = __doHolidayKbn(map);
        } else if (cmd.equals("formatInf")) {
            //勤務フォーマット登録
            forward = __doFormatInf(map);
        } else if (cmd.equals("yukyuKeikoku")) {
            //有休警告設定
            forward = __doYukyuKeikoku(map);
        } else if (cmd.equals("back")) {
            //戻る
            forward = __doBack(map, uform);
        } else if (cmd.equals("mng")) {
            //勤怠集計
            forward = map.findForward("mng");
        } else if (cmd.equals("yuukyuList")) {
            //有休日数一覧
            forward = map.findForward("yukyu");
        } else {
            //初期表示処理
            forward = __doInit(map, uform, req, res, con);
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
                                    Tcd030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        ActionForward forward = null;

        //ユーザ情報
        BaseUserModel umodel = getSessionUserModel(req);
        TimecardBiz tcdBiz = new TimecardBiz(getRequestModel(req));
        int usKbn = tcdBiz.getUserKbn(con, umodel);
        form.setMenuLevel(String.valueOf(usKbn));
        form.setTcdBackScreen(GSConstTimecard.TCD_BACKSCREEN_ADMIN);
        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>[機  能] 基本設定画面へ
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
    private ActionForward __doBaseConf(ActionMapping map,
                                    Tcd030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        ActionForward forward = null;

        forward = map.findForward("base_conf");
        return forward;
    }

    /**
     * <br>[機  能] 時間帯設定画面へ
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
    private ActionForward __doTimeZone(ActionMapping map,
                                    Tcd030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        ActionForward forward = null;

        forward = map.findForward("timezone");
        return forward;
    }

    /**
     * <br>[機  能] 編集権限設定画面へ
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
    private ActionForward __doEditAuth(ActionMapping map,
                                    Tcd030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        ActionForward forward = null;

        forward = map.findForward("editAuth");
        return forward;
    }

    /**
     * <br>[機  能] 勤務表一括出力画面へ
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @return ActionForward フォワード
     */
    private ActionForward __doOutputKinmu(
            ActionMapping map) {

        ActionForward forward = null;

        forward = map.findForward("outputKinmu");
        return forward;
    }

    /**
     * <br>[機  能] ユーザ別使用時間帯設定一覧
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @return ActionForward フォワード
     */
    private ActionForward __doUserTimeZone(ActionMapping map) {
        ActionForward forward = null;

        forward = map.findForward("userTimezone");
        return forward;
    }

    /**
     * <br>[機  能] 休日区分設定一覧
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @return ActionForward フォワード
     */
    private ActionForward __doHolidayKbn(ActionMapping map) {
        ActionForward forward = null;

        forward = map.findForward("holidayKbn");
        return forward;
    }
    
    /**
     * <br>[機  能] 勤務表フォーマット登録
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @return ActionForward フォワード
     */
    private ActionForward __doFormatInf(ActionMapping map) {
        ActionForward forward = null;
        forward = map.findForward("formatInf");
        return forward;
    }
    
    /**
     * <br>[機  能] 有休警告設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @return ActionForward フォワード
     */
    private ActionForward __doYukyuKeikoku(ActionMapping map) {
        ActionForward forward = null;
        
        forward = map.findForward("yukyuKeikoku");
        return forward;
    }
    

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Tcd030Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            return map.findForward("mainAdmSetting");
        }
        return map.findForward("back");
    }
}

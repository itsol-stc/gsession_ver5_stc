package jp.groupsession.v2.rsv.rsv190;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.rsv010.Rsv010Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 施設予約 週間ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv190Action extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv190Action.class);

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
        Rsv190Form rsvform = (Rsv190Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("zensyu_ido")) {
            log__.debug("前週移動ボタン押下");
            __doMoveDays(rsvform, req, con, -7, false);
            forward = __doInit(map, rsvform, req, res, con);
        //前日移動ボタン押下
        } else if (cmd.equals("zenzitu_ido")) {
            log__.debug("前日移動ボタン押下");
            __doMoveDays(rsvform, req, con, -1, false);
            forward = __doInit(map, rsvform, req, res, con);
        //今日ボタン押下
        } else if (cmd.equals("kyo")) {
            log__.debug("今日ボタン押下");
            __doMoveDays(rsvform, req, con, 0, true);
            forward = __doInit(map, rsvform, req, res, con);
        //翌日移動ボタン押下
        } else if (cmd.equals("yokuzitu_ido")) {
            log__.debug("翌日移動ボタン押下");
            __doMoveDays(rsvform, req, con, 1, false);
            forward = __doInit(map, rsvform, req, res, con);
        //翌週移動ボタン押下
        } else if (cmd.equals("yokusyu_ido")) {
            log__.debug("翌週移動ボタン押下");
            __doMoveDays(rsvform, req, con, 7, false);
            forward = __doInit(map, rsvform, req, res, con);
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
                                    Rsv190Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {
            con.setAutoCommit(true);
            //表示開始日
            int selectGroup = NullDefault.getInt(
                    req.getParameter(
                            "rsvSelectedGrpSid"),
                            GSConstReserve.COMBO_DEFAULT_VALUE);
            form.setRsvSelectedGrpSid(selectGroup);

            Rsv010Biz biz = new Rsv010Biz(getRequestModel(req), con);


            Rsv190ParamModel paramMdl = new Rsv190ParamModel();
            paramMdl.setParam(form);

            //施設グループコンボリストを設定する
            biz.setGroupComboList(paramMdl);

            //施設予約情報一覧を取得する
            biz.setYoyakuWeek(paramMdl, getSessionUserSid(req));

            paramMdl.setFormData(form);


            con.setAutoCommit(false);
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 表示日付の移動を行います
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param moveDay 移動日数
     * @param today 今日へ移動

     */
    private void __doMoveDays(Rsv190Form form,
                                HttpServletRequest req,
                                Connection con,
                                int moveDay,
                                boolean today) {

        Rsv010Biz biz = new Rsv010Biz(getRequestModel(req), con);

        Rsv190ParamModel paramMdl = new Rsv190ParamModel();
        paramMdl.setParam(form);
        biz.setMoveDspData(paramMdl, moveDay, today);
        paramMdl.setFormData(form);

    }
}
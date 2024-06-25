package jp.groupsession.v2.prj.prj025;

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

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj023.Prj023Action;
import jp.groupsession.v2.prj.prj023.Prj023Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] TODO状態削除画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj025Action extends AbstractProjectAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj025Action.class);

    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = Prj023Action.CMD_BACK_REDRAW;
    /** CMD:設定ボタンクリック */
    public static final String CMD_EDIT_CLICK = Prj023Action.CMD_DEL_EDIT_CLICK;

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("Prj025Action start");
        ActionForward forward = null;

        GsMessage gsMsg = new GsMessage(req);

        Prj025Form thisForm = (Prj025Form) form;
        con.setAutoCommit(true);
        //登録・編集権限チェック
        PrjCommonBiz pcBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        if (!pcBiz.getProjectKengen(thisForm.getPrj020cmdMode(),
                                    thisForm.getPrj020prjSid(),
                                    getSessionUserModel(req))) {
            //権限なし
            return setPrjKengenError(map, req, thisForm.getPrj020cmdMode());
        }
        con.setAutoCommit(false);
        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        //テンポラリディレクトリ確認
        if (!CMD_BACK_CLICK.equals(cmd)
                && !PrjCommonBiz.existStatusFile(
                        GSConstProject.MODE_TMP_OTHER, getRequestModel(req))) {
            return getSubmitErrorPage(map, req);
        }

        if (CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward(CMD_EDIT_CLICK);

        } else if (CMD_EDIT_CLICK.equals(cmd)) {
            log__.debug("設定ボタンクリック");
            forward = __doEdit(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Prj025Action end");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doInit(
        ActionMapping map,
        Prj025Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //初期表示情報を画面にセットする
        Prj025Biz biz = new Prj025Biz(getRequestModel(req));

        Prj025ParamModel paramMdl = new Prj025ParamModel();
        paramMdl.setParam(form);
        biz.getDspData(paramMdl, getTempPath(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doEdit(
        ActionMapping map,
        Prj025Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        ActionErrors errors = form.validate025(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //削除対象の更新先SIDをファイルに保存
        Prj025Biz biz = new Prj025Biz(getRequestModel(req));
        Prj025ParamModel paramMdl = new Prj025ParamModel();
        paramMdl.setParam(form);
        biz.updateSave(paramMdl, getTempPath(req));
        paramMdl.setFormData(form);


        //削除対象のTODO状態をファイルから削除
        Prj023Biz biz023 = new Prj023Biz(con, getRequestModel(req));

        paramMdl = new Prj025ParamModel();
        paramMdl.setParam(form);
        biz023.removeStatus(paramMdl, getTempPath(req));
        paramMdl.setFormData(form);

        return map.findForward(CMD_EDIT_CLICK);
    }

}

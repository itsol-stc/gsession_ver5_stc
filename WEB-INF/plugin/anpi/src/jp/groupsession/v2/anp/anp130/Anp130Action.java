package jp.groupsession.v2.anp.anp130;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 * <br>管理者設定・配信履歴画面のアクション
 * @author JTS
 */
public class Anp130Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp130Action.class);

    /**
     * <p>
     * アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
                                throws Exception {

        ActionForward forward = null;
        Anp130Form uform = (Anp130Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp130back")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp130delete")) {
            //削除（確認メッセージ→完了メッセージ→再描画）
            forward = __doDeleteConf(map, uform, req, res, con);

        } else if (cmd.equals("deleteOk")) {
            //削除実行
            forward = __doDeleteExe(map, uform, req, res, con);

        } else if (cmd.equals("anp130syokai")) {
            //照会
            forward = map.findForward("syokai");

        } else if (cmd.equals("anp130pageChange")) {
            //ページコンボボックス選択時
            forward = __movePage(map, uform, req, res, con, 0);

        } else if (cmd.equals("anp130pageLast")) {
            //「前ページ」ボタンクリック時
            forward = __movePage(map, uform, req, res, con, -1);

        } else if (cmd.equals("anp130pageNext")) {
            //「次ページ」ボタンクリック時
            forward = __movePage(map, uform, req, res, con, 1);

        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <p>
     * 初期化実行
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @param  res レスポンス
     * @param  con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doInit(ActionMapping map,
                                   Anp130Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
                            throws Exception {

        Anp130Biz biz = new Anp130Biz();
        Anp130ParamModel paramModel = new Anp130ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <p>
     * ページ移動実行
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @param  res レスポンス
     * @param  con コネクション
     * @param  pageNo ページ番号
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __movePage(ActionMapping map,
                                     Anp130Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con,
                                     int pageNo)
                              throws Exception {

        //ページ数調整
        int page = form.getAnp130NowPage();
        page += pageNo;
        if (page < 1) {
            page = 1;
        }
        form.setAnp130NowPage(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <p>
     * 削除ボタンクリック時の処理を行う
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteConf(ActionMapping map,
                                         Anp130Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
                                  throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheckAnp130(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トランザクショントークン設定
        this.saveToken(req);

        //削除確認画面のパラメータセット
        return __setDeleteConfDsp(map, form, req);
    }

    /**
     * <p>
     * 削除確認画面のパラメータセット
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __setDeleteConfDsp(ActionMapping map,
                                             Anp130Form form,
                                             HttpServletRequest req)
                                      throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forward = map.findForward("return");
        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(forward.getPath() + "?CMD=deleteOk");
        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(forward.getPath() + "?CMD=deleteCancel");

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        cmn999Form.setMessage(gsMsg.getMessage("anp.anp130.01"));

        //画面パラメータ設定
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp130(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <p>
     * 削除実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteExe(ActionMapping map,
                                        Anp130Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
                                 throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //削除処理
        Anp130Biz biz = new Anp130Biz();
        Anp130ParamModel paramModel = new Anp130ParamModel();
        paramModel.setParam(form);
        biz.doDelete(paramModel, con);
        paramModel.setFormData(form);

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("cmn.delete");
        String value = gsMsg.getMessage("man.purge");
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, value);


        //削除完了画面を表示
        return __setDeleteExeDsp(map, form, req);
    }

    /**
     * <p>
     * 削除完了画面のパラメータセット
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __setDeleteExeDsp(ActionMapping map,
                                            Anp130Form form,
                                            HttpServletRequest req)
                                     throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("return");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("cmn.kanryo.object", gsMsg.getMessage("anp.anp130.02")));

        //画面パラメータ設定
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp130(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}

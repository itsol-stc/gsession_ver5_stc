package jp.groupsession.v2.anp.anp140;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp060.Anp060Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 * <br>[機  能] 管理者設定・配信履歴 状況内容確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp140Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp140Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォアード
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
                                throws Exception {

        ActionForward forward = null;
        Anp140Form uform = (Anp140Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        uform.setAnp140ScrollFlg("0");
        if (cmd.equals("anp140back")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp140delete")) {
            //削除（確認メッセージ→完了メッセージ→一覧）
            forward = __doDeleteConf(map, uform, req, res, con);

        } else if (cmd.equals("deleteOk")) {
            //削除実行
            forward = __doDeleteExe(map, uform, req, res, con);

        } else if (cmd.equals("anp140copyNew")) {
            //複写して新規作成
            forward = __copyNewMsg(map, uform, req, res, con);

        } else if (cmd.equals("anp140pageChange")) {
            //ページコンボボックス選択時
            uform.setAnp140ScrollFlg("1");
            forward = __movePage(map, uform, req, res, con, 0);

        } else if (cmd.equals("anp140pageLast")) {
            //「前ページ」ボタンクリック時
            uform.setAnp140ScrollFlg("1");
            forward = __movePage(map, uform, req, res, con, -1);

        } else if (cmd.equals("anp140pageNext")) {
            //「次ページ」ボタンクリック時
            uform.setAnp140ScrollFlg("1");
            forward = __movePage(map, uform, req, res, con, 1);

        } else if (cmd.equals("anp140sortList")) {
            uform.setAnp140ScrollFlg("1");
            //ソート順変更（一覧列タイトルクリック時）
            forward = __sortList(map, uform, req, res, con);
        }  else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doInit(ActionMapping map,
                                   Anp140Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
                            throws Exception {

        Anp140Biz biz = new Anp140Biz();
        Anp140ParamModel paramModel = new Anp140ParamModel();
        paramModel.setParam(form);
        boolean dataExist = biz.setInitData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);
        if (!dataExist) {
            //データなし
            return __setDataNotExistDisp(map, form, req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ページ移動実行
     * <br>[解  説]
     * <br>[備  考]
     *
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
                                     Anp140Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con,
                                     int pageNo)
                              throws Exception {

        //ページ数調整
        int page = form.getAnp140NowPage();
        page += pageNo;
        if (page < 1) {
            page = 1;
        }
        form.setAnp140NowPage(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除確認
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @param  res レスポンス
     * @param  con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doDeleteConf(ActionMapping map,
                                         Anp140Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
                                  throws Exception {
        //トランザクショントークン設定
        this.saveToken(req);

        //削除確認画面のパラメータセット
        return __setDeleteConfDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __setDeleteConfDsp(ActionMapping map,
                                             Anp140Form form,
                                             HttpServletRequest req)
                                      throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forward = map.findForward("return");
        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(forward.getPath() + "?CMD=deleteOk");
        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(forward.getPath() + "?CMD=deleteCancel");

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        //メッセージ
        cmn999Form.setMessage(gsMsg.getMessage("anp.anp140.06"));

        //画面パラメータ設定
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp130(cmn999Form);
        form.setHiddenParamAnp140(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteExe(ActionMapping map,
                                        Anp140Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
                                 throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //削除処理
        Anp140Biz biz = new Anp140Biz();
        //タイトル取得
        String value = biz.getLogValue(con, getRequestModel(req), form.getAnp130SelectAphSid());

        Anp140ParamModel paramModel = new Anp140ParamModel();
        paramModel.setParam(form);
        biz.doDelete(paramModel, con);
        paramModel.setFormData(form);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("cmn.delete");
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, value);


        //削除完了画面を表示
        return __setDeleteExeDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除完了画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __setDeleteExeDsp(ActionMapping map,
                                            Anp140Form form,
                                            HttpServletRequest req)
                                     throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("list");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("cmn.kanryo.object", gsMsg.getMessage("anp.anp130.02")));

        //画面パラメータ設定
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp130(cmn999Form);
        form.setHiddenParamAnp140(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] メッセージ配信画面へ配信データ流用
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  map アクションマッピング
     * @param  form アクションフォーム
     * @param  req リクエスト
     * @param  res レスポンス
     * @param  con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __copyNewMsg(ActionMapping map,
                                       Anp140Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
                                throws Exception {

        //次画面パラメータ設定
        Anp060Form anp060Form = new Anp060Form();

        BeanUtils.copyProperties(anp060Form, form);
        anp060Form.setAnp060CopyAnpiSid(String.valueOf(form.getAnp130SelectAphSid()));
        anp060Form.setAnp060ProcMode(GSConstAnpi.MSG_HAISIN_MODE_COPY);

        req.setAttribute("anp060Form", anp060Form);

        return map.findForward("copyNew");
    }

    /**
     * <br>[機  能] ソート順変更処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __sortList(ActionMapping map, Anp140Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //ソート設定
        int order = form.getAnp140OrderKey();
        if (order == GSConst.ORDER_KEY_ASC) {
            order = GSConst.ORDER_KEY_DESC;
        } else {
            order = GSConst.ORDER_KEY_ASC;
        }
        form.setAnp140OrderKey(order);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 詳細データ不在時、メッセージ画面へのパラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __setDataNotExistDisp(ActionMapping map,
                                                Anp140Form form,
                                                HttpServletRequest req)
                                         throws Exception {

        log__.debug("///配信データなし、メッセージ出力");
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("list");
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        //メッセージ
        cmn999Form.setMessage(gsMsg.getMessage("anp.anp140.07"));

        //画面パラメータ設定
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp130(cmn999Form);
        form.setHiddenParamAnp140(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}

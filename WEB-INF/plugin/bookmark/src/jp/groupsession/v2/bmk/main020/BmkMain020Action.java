package jp.groupsession.v2.bmk.main020;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] 新着ブックマーク（メイン）画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BmkMain020Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkMain020Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        BmkMain020Form thisForm = (BmkMain020Form) form;

        //メイン画面表示設定の判定
        thisForm.setBmkmain020dspFlg(__checkMainDsp(thisForm, req, con));
        if (thisForm.getBmkmain020dspFlg() == GSConstBookmark.DSP_NO) {
            return map.getInputForward();
        }

        if (cmd.equals("bmkmain020settei")) {
            log__.debug("設定ボタン押下");
            forward = map.findForward("bmk130");

        } else if (cmd.equals("bmkmain020add")) {
            log__.debug("追加ボタン押下");
            forward = map.findForward("bmk020");

        } else if (cmd.equals("bmkmain020cmt")) {
            log__.debug("コメント・評価");
            forward = map.findForward("bmk070");

        } else if (cmd.equals("bmkmain020newbookmark")) {
            log__.debug("一覧");
            forward = map.findForward("bmk150");

        } else {
            log__.debug("初期表示");
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
                                    BmkMain020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        BmkMain020Biz biz = new BmkMain020Biz(getRequestModel(req));

        BmkMain020ParamModel paramMdl = new BmkMain020ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getSessionUserModel(req));
        paramMdl.setFormData(form);

        form.setBmkTopUrl(getPluginConfig(req).getPlugin(
                GSConstBookmark.PLUGIN_ID_BOOKMARK).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);

        return map.getInputForward();
    }


    /**
     * メイン画面表示の有無を判定する。
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 例外
     * @return 0:非表示 1:表示
     */
    private int __checkMainDsp(BmkMain020Form form, HttpServletRequest req, Connection con)
    throws Exception {

        con.setAutoCommit(true);
        try {
            //ブックマーク個人設定のメイン表示区分で判定
            BaseUserModel umodel = getSessionUserModel(req);
            BmkMain020Biz biz = new BmkMain020Biz(getRequestModel(req));

            int mainDapNum = 0;
            BmkMain020ParamModel paramMdl = new BmkMain020ParamModel();
            paramMdl.setParam(form);
            mainDapNum = biz.isMainDsp(paramMdl, con, umodel.getUsrsid());
            paramMdl.setFormData(form);

            if (mainDapNum == GSConstBookmark.DSP_NO) {
                return GSConstBookmark.DSP_NO;
            }

            //プラグイン設定を取得する
            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
            CommonBiz cmnBiz = new CommonBiz();
            //ブックマークプラグインは利用可能か判定
            if (!cmnBiz.isCanUsePlugin(GSConstBookmark.PLUGIN_ID_BOOKMARK, pconfig)) {
                return GSConstBookmark.DSP_NO;
            }
        } finally {
            con.setAutoCommit(false);
        }

        return GSConstBookmark.DSP_YES;
    }
}
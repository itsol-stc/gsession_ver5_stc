package jp.groupsession.v2.anp.anp110;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * <br>[機  能] 管理者設定・緊急連絡先設定状況画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp110Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp110Action.class);

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
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        Anp110Form uform = (Anp110Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp110back")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp110import")) {
            //インポート
            forward = map.findForward("import");

        } else if (cmd.equals("anp110edit")) {
            //編集画面へ（氏名リンククリック時）
            forward = map.findForward("edit");

        } else if (cmd.equals("anp110sortList")) {
            //リスト氏名列クリックによるソート
            forward = __sortList(map, uform, req, res, con);

        } else if (cmd.equals("anp110pageChange")) {
            //ページコンボボックス選択時
            forward = __movePage(map, uform, req, res, con, 0);

        } else if (cmd.equals("anp110pageLast")) {
            //「前ページ」ボタンクリック時
            forward = __movePage(map, uform, req, res, con, -1);

        } else if (cmd.equals("anp110pageNext")) {
            //「次ページ」ボタンクリック時
            forward = __movePage(map, uform, req, res, con, 1);

        } else if (cmd.equals("anp110group")) {
            //グループコンボボックス選択時、または選択画面からの戻り
            uform.setAnp110NowPage(1);  //ページを1ページ目へ
            forward = __refresh(map, uform, req, res, con);

        } else if (cmd.equals("anp110filter")) {
            //グループコンボボックス選択時、または選択画面からの戻り
            uform.setAnp110NowPage(1);  //ページを1ページ目へ
            forward = __refresh(map, uform, req, res, con);
        } else {
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
                                   Anp110Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
                            throws Exception {

        //初期表示のソート情報をセット
        Anp110Biz biz = new Anp110Biz();
        Anp110ParamModel paramModel = new Anp110ParamModel();
        paramModel.setParam(form);
        biz.setInitSortData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 更新処理
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
    private ActionForward __refresh(ActionMapping map,
                                   Anp110Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
                            throws Exception {

        Anp110Biz biz = new Anp110Biz();
        Anp110ParamModel paramModel = new Anp110ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, getRequestModel(req), con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 一覧並べ替え実行
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
    private ActionForward __sortList(ActionMapping map,
                                     Anp110Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
                               throws Exception {

        //ソート設定
        int sortKey = form.getAnp110OrderKey();
        if (sortKey == GSConst.ORDER_KEY_ASC) {
            sortKey = GSConst.ORDER_KEY_DESC;
        } else {
            sortKey = GSConst.ORDER_KEY_ASC;
        }
        form.setAnp110OrderKey(sortKey);

        return __refresh(map, form, req, res, con);
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
                                     Anp110Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con,
                                     int pageNo)
                              throws Exception {

        //ページ数調整
        int page = form.getAnp110NowPage();
        page += pageNo;
        if (page < 1) {
            page = 1;
        }
        form.setAnp110NowPage(page);

        return __refresh(map, form, req, res, con);
    }

}

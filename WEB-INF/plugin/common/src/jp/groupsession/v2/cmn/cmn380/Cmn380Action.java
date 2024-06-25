package jp.groupsession.v2.cmn.cmn380;

import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ファイルプレビュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn380Action extends AbstractGsAction {

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Cmn380Form thisForm = (Cmn380Form) form;

        String extension = NullDefault.getString(thisForm.getCmn380PreviewFileExtension(), "");
        String previewUrl = thisForm.getCmn380PreviewURL();
        String checkUrl = NullDefault.getString(previewUrl, "").toLowerCase();

        //想定外ファイル、または外部の画像は表示不可
        if (!Arrays.asList(GSConstCommon.FILEPREVIEW_EXTENSION).contains(extension.toLowerCase())
        || checkUrl.startsWith("http://") || checkUrl.startsWith("https://")) {
            __setErrorPage(map, req);
            return map.findForward("gf_msg");
        }

        //各機能のダウンロードURL以外(ファイルの直接指定)は不可
        int lastIdx = checkUrl.indexOf("?");
        if (lastIdx <= 0 || !checkUrl.substring(0, lastIdx).endsWith(".do")) {
            __setErrorPage(map, req);
            return map.findForward("gf_msg");
        }

        if (!StringUtil.isNullZeroString(previewUrl)) {
            AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();

            if (previewUrl.startsWith("../")) {
                previewUrl = previewUrl.substring(3);
            }

            previewUrl = "/" + urlBiz.getContextPath(getRequestModel(req)) + "/"
                        + previewUrl;
            thisForm.setCmn380PreviewPdfURL(
                    URLEncoder.encode(previewUrl, Encoding.UTF_8));
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] エラーメッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setErrorPage(
            ActionMapping map,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);

        //メッセージセット
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.access.window.colse"));

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
    }

}

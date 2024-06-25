package jp.groupsession.v2.ptl.ptl990;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.ptl.AbstractPortalAction;
import jp.groupsession.v2.ptl.ptl100.Ptl100Biz;

/**
 * <br>[機  能] ポータル ポートレット画像表示のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl990Action extends AbstractPortalAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl990Action.class);

    /** NO IMAGE画像ファイル名 */
    private static final String NOIMAGE_FILENAME = "ptl_noimage.gif";

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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

        try {
            Ptl990Form thisForm = (Ptl990Form) form;
            String pltSidStr = thisForm.getPtlPortletSid();
            long imgSid = thisForm.getImgId();

            con.setAutoCommit(true);

            //ユーザモデル
            BaseUserModel usModel = getSessionUserModel(req);
            int userSid = usModel.getUsrsid();

            PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, userSid);
            Ptl990Biz biz = new Ptl990Biz();

            boolean ptl100Image = false;

            long binSid = 0;
            if (!StringUtil.isNullZeroString(pltSidStr)) {

                ptl100Image = pltSidStr.equals("$PORTLET_SID");
                if (!ptl100Image && ValidateUtil.isNumber(pltSidStr)) {
                    int pltSid = Integer.parseInt(pltSidStr);
                    if (biz.canViewPortlet(con, pconfig, pltSid, usModel)) {
                        binSid = biz.getPortletBinSid(con, pltSid, imgSid);
                    }
                }
            }

            con.setAutoCommit(false);

            //画像のダウンロードを行う
            if (binSid > 0) {
                CommonBiz cmnBiz = new CommonBiz();
                CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                        GroupSession.getResourceManager().getDomain(req));

                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
            } else if (ptl100Image) {
                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                Ptl100Biz biz100 = new Ptl100Biz();
                String imgTempDir
                    = biz100.getPortletImgTempDir(getRequestModel(req),
                                                String.valueOf(thisForm.getImgId()));
                CommonBiz cmnBiz = new CommonBiz();

                List<TempFileModel> fileList = cmnBiz.getTempFiles(imgTempDir);
                if (fileList != null && !fileList.isEmpty()) {
                    TempFileUtil.downloadInline(req, res, fileList.get(0).getFile(),
                            fileList.get(0).getFileName(), Encoding.UTF_8);
                } else {
                    String noImagePath = getAppRootPath();
                    noImagePath += "/portal/images/" + NOIMAGE_FILENAME;
                    TempFileUtil.downloadInline(req, res, noImagePath,
                            NOIMAGE_FILENAME, Encoding.UTF_8);
                }

            } else {

                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                String noImagePath = getAppRootPath();
                noImagePath += "/portal/images/" + NOIMAGE_FILENAME;
                TempFileUtil.downloadInline(req, res, noImagePath,
                        NOIMAGE_FILENAME, Encoding.UTF_8);
            }

        } catch (Throwable e) {
            log__.error("ポートレット画像取得時にエラー発生", e);
        } finally {
            JDBCUtil.autoCommitOff(con);
        }
        return null;
    }
}

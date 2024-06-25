package jp.groupsession.v2.api.ntp.nippou.comment.edit;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ntp.nippou.edit.ApiNippouEditBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
/**
 * <br>[機  能] WEBAPI 日報コメント投稿アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouCommentAddAction extends AbstractApiAction {

    /** ログ */
    private static Log log__ = LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        ApiNippouCommentAddForm thisForm = (ApiNippouCommentAddForm) form;
        ActionErrors errors = thisForm.validateCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }
        Document doc = new Document();

        BaseUserModel usModel = umodel;

        String ntpSid = NullDefault.getString(thisForm.getNipSid(), "");
        String commentStr = NullDefault.getString(thisForm.getComment(), "");

        int retSid = -1;
        if (usModel != null && !StringUtil.isNullZeroStringSpace(ntpSid)
                                   && !StringUtil.isNullZeroStringSpace(commentStr)) {

            //セッションユーザSID
            int sessionUsrSid = usModel.getUsrsid();
            //コメントをデコード
            commentStr = URLDecoder.decode(commentStr, "utf-8");

            PluginConfig pconfig = getPluginConfig(req);

            MlCountMtController cntCon = getCountMtController(req);
            ApiNippouEditBiz biz = new ApiNippouEditBiz(con, getRequestModel(req), cntCon);

            boolean commitFlg = false;
            con.setAutoCommit(false);
            try {

                //コメント新規登録
                retSid = biz.insertComment(Integer.valueOf(ntpSid), commentStr,
                        sessionUsrSid, getAppRootPath(), pconfig, getRequestModel(req));
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("コメントの登録に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
        doc.addContent(_createElement("NpcSid", retSid));
        return doc;
    }


}

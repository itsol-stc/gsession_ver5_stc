package jp.groupsession.v2.api.ntp.nippou.goodjob.cancel;

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
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.dao.NtpGoodDao;
/**
 * <br>[機  能] WEBAPI 日報いいね！除去アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouRemoveGoodJobAction extends AbstractApiAction {
    /**ロガークラス*/
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        Document doc = new Document();

        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        ApiNtpRemoveGoodJobForm thisForm = (ApiNtpRemoveGoodJobForm) form;
        ActionErrors err = thisForm.validateCheck(con, getRequestModel(req));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        //セッション情報を取得
        BaseUserModel usModel = umodel;

        int nipSid = NullDefault.getInt(thisForm.getNipSid(), -1);

        if (usModel != null && nipSid > 0) {
            //セッションユーザSID
            int sessionUsrSid = usModel.getUsrsid();
            NtpGoodDao gDao = new NtpGoodDao(con);
            boolean commitFlg = false;
            con.setAutoCommit(false);
            try {
                //いいね削除
                gDao.delete(nipSid, sessionUsrSid);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("いいねの登録に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
            //最新のいいねの件数を取得

            int cnt = 0;
            cnt = gDao.count(nipSid);
            doc.addContent(_createElement("Result", cnt));
        }
        return doc;


    }

}

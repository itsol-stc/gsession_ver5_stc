package jp.groupsession.v2.api.ntp.target.edit;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.ntp240.Ntp240Biz;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
/**
 * <br>[機  能] WEBAPI 日報目標登録アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNtpTargetEditAction extends AbstractApiAction {
    /**ロガーオブジェクト*/
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
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
        ApiNtpTargetEditForm thisForm = (ApiNtpTargetEditForm) form;
        ActionErrors err = thisForm.validateCheck(con, getRequestModel(req));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        Document doc = new Document();

        //目標登録
        boolean commitFlg = false;
        con.setAutoCommit(false);

        Ntp240Biz ntp240Biz = new Ntp240Biz(con, getRequestModel(req));
        if (thisForm.getTarget() != null) {
            String[] target = thisForm.getTarget();
            try {

                for (int i = 0; i < target.length; i++) {
                    String json = target[i];
                    JSONObject receipt = JSONObject.fromObject(json);
                    ntp240Biz.setTarget(con,
                            //目標年度    year
                            receipt.optInt("year"),
                            //目標月 month
                            receipt.optInt("month"),
                            //ユーザSID   usrSid
                            receipt.optInt("usrSid"),
                            //目標SID   ntgSid
                            receipt.optInt("ntgSid"),
                            //目標実績値   record
                            receipt.optLong("record"),
                            //目標値 value
                            receipt.optLong("value"),
                            umodel);
                }
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("目標の登録に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }


        return doc.addContent(_createElement("Result", "OK"));
    }

}

package jp.groupsession.v2.api.ntp.nippou.tempFile.check;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 * <br>[機  能] WEBAPI 日報 添付ファイルの事前確認アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouTempFileValidateAction extends AbstractApiAction {
    /** ロガーオブジェクト*/
    private static Log log__ = LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        ActionErrors errors =
            ((ApiNippouTempFileValidateForm) form).validateCheck(getRequestModel(req), req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }
        return new Document(_createElement("Result", "OK"));
    }

}

package jp.groupsession.v2.api.schedule.prefarence.worktime;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.api.schedule.AbstractApiSchAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 *
 * <br>[機  能] スケジュール日間表示時間帯設定取得WEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchPrefWorkTimeAction extends AbstractApiSchAction {

    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());

    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        ApiSchPrefWorkTimeForm form = (ApiSchPrefWorkTimeForm) aForm;
        RequestModel reqMdl = getRequestModel(req);

        ActionErrors err = form.validateCheck(new GsMessage(reqMdl));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        int usrSid = NullDefault.getInt(form.getUsrSid(), umodel.getUsrsid());

        SchCommonBiz biz = new SchCommonBiz(reqMdl);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, usrSid);
        UDate ifr = pconf.getSccFrDate();
        UDate ito = pconf.getSccToDate();
        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Element result = new Element("Result");
        resultSet.addContent(result);

        //開始 時
        result.addContent(_createElement("From", ifr.getIntHour()));
        //終了 時
        result.addContent(_createElement("To", ito.getIntHour()));
        log__.debug("createXml end");

        return doc;
    }

}

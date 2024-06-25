package jp.groupsession.v2.api.cmn.getholiday;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ApiDataTypeUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能]休日一覧を取得するWebAPIAction
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCmnGetHolidayAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCmnGetHolidayAction.class);

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        //入力チェック
        ApiCmnGetHolidayForm acghForm = (ApiCmnGetHolidayForm) form;
        ActionErrors errors = acghForm.validateFromTo();
        if (!errors.isEmpty()) {
            log__.debug("エラーあり");
            addErrors(req, errors);
            return null;
        }

        //休日情報を取得する
        UDate frDate = new UDate();
        if (acghForm.getFrom() != null) {
            String from = acghForm.getFrom();
            frDate.setDate(Integer.parseInt(from.substring(0, 4)),
                    Integer.parseInt(from.substring(5, 7)),
                    Integer.parseInt(from.substring(8, 10)));
        }
        frDate.setHour(0);
        frDate.setMinute(0);
        frDate.setSecond(0);
        UDate toDate = frDate.cloneUDate();
        if (acghForm.getTo() != null) {
            String to = acghForm.getTo();
            toDate.setDate(Integer.parseInt(to.substring(0, 4)),
                    Integer.parseInt(to.substring(5, 7)),
                    Integer.parseInt(to.substring(8, 10)));
        } else {
            toDate.addYear(1);
        }
        toDate.setHour(0);
        toDate.setMinute(0);
        toDate.setSecond(0);

        Attribute atStart = new Attribute("start", frDate.getDateString());
        Attribute atEnd = new Attribute("end", toDate.getDateString());
        resultSet.setAttribute(atStart);
        resultSet.setAttribute(atEnd);

        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(frDate, toDate);

        if (holMap == null) {
            holMap = new HashMap<String, CmnHolidayModel>();
        }
        Attribute atCount = new Attribute("TotalCount", Integer.toString(holMap.size()));
        resultSet.setAttribute(atCount);


        Set<Entry<String, CmnHolidayModel>> set = holMap.entrySet();
        Iterator<Entry<String, CmnHolidayModel>> it = set.iterator();
        TreeMap<String, CmnHolidayModel> holTreeMap = new TreeMap<String, CmnHolidayModel>();
        while (it.hasNext()) {
            Entry<String, CmnHolidayModel> entry = (Entry<String, CmnHolidayModel>) it.next();
            holTreeMap.put(entry.getKey(), entry.getValue());
        }
        Collection<CmnHolidayModel> colections = holTreeMap.values();

        for (CmnHolidayModel data : colections) {
            Element result = new Element("Result");
            resultSet.addContent(result);

            String holdate = ApiDataTypeUtil.getDate(data.getHolDate());

            result.addContent(_createElement("HolDate", holdate));
            result.addContent(_createElement("HolYear", data.getHolYear()));
            result.addContent(_createElement("HolName", data.getHolName()));
            result.addContent(_createElement("HolKbn", data.getHolKbn()));
            result.addContent(_createElement("HolTcd", data.getHolTcd()));
            log__.debug("休日名称＝" + data.getHolName());
        }
        return doc;
    }

}

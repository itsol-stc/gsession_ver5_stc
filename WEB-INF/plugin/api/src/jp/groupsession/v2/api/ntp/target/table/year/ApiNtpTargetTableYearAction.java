package jp.groupsession.v2.api.ntp.target.table.year;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.ntp240.Ntp240Biz;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 * <br>[機  能] WEBAPI 日報年間目標取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNtpTargetTableYearAction extends AbstractApiAction {

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

        ApiNtpTargetTableYearForm thisForm = (ApiNtpTargetTableYearForm) form;
        ActionErrors err = thisForm.validateCheck(con, req);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }


        Element resultSet = new Element("ResultSet");
        Element result = new Element("Result");
        resultSet.addContent(result);
        Document doc = new Document(resultSet);

        Ntp240Biz biz = new Ntp240Biz(con, getRequestModel(req));
        UDate defDate = new UDate();
        String ymStr = NullDefault.getString(thisForm.getMonth(), UDateUtil.getSlashYYMD(defDate));
        int usrSid = NullDefault.getInt(thisForm.getUsrSid(), umodel.getUsrsid());
        int ntgSid = Integer.valueOf(thisForm.getNtgSid());
        JSONObject json = biz.getYearData(con
                , Integer.valueOf(ymStr.substring(0, 4))
                , Integer.valueOf(ymStr.substring(5, 7))
                , usrSid
                , ntgSid);
        result.addContent(_createElement("Year", json.optInt("year")));
        result.addContent(_createElement("UsrSid", json.optInt("usrSid")));
        result.addContent(_createElement("UsrName", json.optString("usrName")));
        result.addContent(_createElement("UsrUkoFlg", json.optString("usrUkoFlg")));
        result.addContent(_createElement("NtgSid", json.optInt("targetSid")));
        result.addContent(_createElement("NtgName", json.optString("targetName")));
        result.addContent(_createElement("AvrRatio", json.optString("targetRatio")));

        JSONArray list = json.optJSONArray("ntgList");
        Element targetSet = new Element("TargetSet");
        result.addContent(targetSet);
        targetSet.setAttribute("Count", String.valueOf(list.size()));
        for (int i = 0; i < list.size(); i++) {
            JSONObject data = list.optJSONObject(i);
            Element target = new Element("Target");
            targetSet.addContent(target);
            //目標SID
            //目標SID
            //Year    目標年度
            target.addContent(_createElement("Year", data.optInt("npgYear")));
            //Month   目標月
            target.addContent(_createElement("Month", data.optInt("npgMonth")));
            //Ratio   目標達成率
            target.addContent(_createElement("Ratio", data.optString("npgTargetRatio")));
            //Value   目標値
            target.addContent(_createElement("Value", data.optInt("npgTarget")));
            //Record  目標実績値
            target.addContent(_createElement("Record", data.optInt("npgRecord")));

        }

        return doc;
    }

}

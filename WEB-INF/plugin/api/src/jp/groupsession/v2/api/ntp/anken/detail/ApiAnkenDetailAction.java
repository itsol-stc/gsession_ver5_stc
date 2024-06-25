package jp.groupsession.v2.api.ntp.anken.detail;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpContactDao;
import jp.groupsession.v2.ntp.dao.NtpGyomuDao;
import jp.groupsession.v2.ntp.dao.NtpProcessDao;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpContactModel;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;
import jp.groupsession.v2.ntp.model.NtpProcessModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import jp.groupsession.v2.ntp.ntp061.Ntp061AnkenDao;
import jp.groupsession.v2.ntp.ntp061.Ntp061AnkenModel;
import jp.groupsession.v2.ntp.ntp061.Ntp061Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] 日報 案件詳細取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAnkenDetailAction extends AbstractApiAction {
    /** ロガーオブジェクト*/
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
        ApiAnkenDetailForm thisForm = (ApiAnkenDetailForm) form;
        ActionErrors err = thisForm.validateCheck();
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        Ntp061Biz biz = new Ntp061Biz(
                con, getCountMtController(req), getRequestModel(req));
        if (!biz.isViewable(Integer.valueOf(thisForm.getNanSid()))) {
            GsMessage gsMsg = new GsMessage(req);
            ActionMessage msg = new ActionMessage("error.edit.power.user",
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("cmn.reading"));
            StrutsUtil.addMessage(err, msg, "error.edit.power.user");
            addErrors(req, err);
            return null;
        }



        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        Element result = new Element("Result");
        resultSet.addContent(result);
        //sidから案件情報の取得
        Ntp061AnkenDao ankenDao = new Ntp061AnkenDao(con);
        Ntp061AnkenModel ankenModel = ankenDao.select(Integer.parseInt(thisForm.getNanSid()),
                getRequestModel(req));
        result.addContent(_createElement("Date", UDateUtil.getSlashYYMD(ankenModel.getNanDate())));
        result.addContent(_createElement("NanSid", ankenModel.getNanSid()));
        result.addContent(_createElement("NanCode", ankenModel.getNanCode()));
        result.addContent(_createElement("NanName", ankenModel.getNanName()));
        result.addContent(_createElement("AcoSid", ankenModel.getAcoSid()));
        result.addContent(_createElement("AcoName", ankenModel.getNtp061CompanyName()));
        result.addContent(_createElement("AbaSid", ankenModel.getAbaSid()));
        result.addContent(_createElement("AbaName", ankenModel.getNtp061BaseName()));
        result.addContent(_createElement("NanDetail", ankenModel.getNanDetial()));
        result.addContent(_createElement("NanMikomi", ankenModel.getNanMikomi()));
        result.addContent(_createElement("NanKinMitumori", ankenModel.getNanKinMitumori()));
        result.addContent(_createElement("NanDateMitumori"
                , UDateUtil.getSlashYYMD(ankenModel.getNanMitumoriDate())));
        result.addContent(_createElement("NanKinJutyu", ankenModel.getNanKinJutyu()));
        result.addContent(_createElement("NanDateJutyu"
                , UDateUtil.getSlashYYMD(ankenModel.getNanJutyuDate())));
        result.addContent(_createElement("NanSyodan", ankenModel.getNanSyodan()));
        result.addContent(_createElement("NgpSid", ankenModel.getNgpSid()));
        if (ankenModel.getNgpSid() > 0) {
            NtpProcessDao ngpDao = new NtpProcessDao(con);
            NtpProcessModel ngpModel = ngpDao.select(ankenModel.getNgpSid());
            result.addContent(_createElement("NgpName", ngpModel.getNgpName()));
        } else {
            result.addContent(_createElement("NgpName", ""));
        }
        result.addContent(_createElement("NgySid", ankenModel.getNtp061NgySid()));
        if (ankenModel.getNtp061NgySid() > 0) {
            NtpGyomuDao ngyDao = new NtpGyomuDao(con);
            NtpGyomuModel ngyModel = ngyDao.select(ankenModel.getNtp061NgySid());
            result.addContent(_createElement("NgyName", ngyModel.getNgyName()));
        } else {
            result.addContent(_createElement("NgyName", ""));
        }
        result.addContent(_createElement("NcnSid", ankenModel.getNcnSid()));
        if (ankenModel.getNcnSid() > 0) {
            NtpContactDao ncnDao = new NtpContactDao(con);
            NtpContactModel ncnModel = ncnDao.select(ankenModel.getNcnSid());
            result.addContent(_createElement("NcnName", ncnModel.getNcnName()));
        } else {
            result.addContent(_createElement("NcnName", ""));
        }
        //商品一覧設定
        Element nhnSet = new Element("NhnSet");
        result.addContent(nhnSet);
        NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con);
        String[] shohinSidList = anShohinDao.select(Integer.parseInt(thisForm.getNanSid()));
        NtpShohinDao shohinDao = new NtpShohinDao(con);
        ArrayList<NtpShohinModel> shohinList
            = (ArrayList<NtpShohinModel>) shohinDao.select(shohinSidList);
        for (NtpShohinModel mdl : shohinList) {
            Element shohin = new Element("Shohin");
            nhnSet.addContent(shohin);
            shohin.addContent(_createElement("NhnSid", mdl.getNhnSid()));
            shohin.addContent(_createElement("NhnCode", mdl.getNhnCode()));
            shohin.addContent(_createElement("NhnName", mdl.getNhnName()));
        }
        nhnSet.setAttribute("TotalCount", Integer.toString(shohinList.size()));

        return doc;
    }

}

package jp.groupsession.v2.api.ntp.nippou.edit.template;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ntp.nippou.edit.ApiNippouEditBiz;
import jp.groupsession.v2.api.ntp.nippou.edit.model.ApiNippouEditTemplateModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.model.NtpPriTargetModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
/**
 * <br>[機  能] WEBAPI 日報テンプレート取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouEditTemplateAction extends AbstractApiAction {

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
        ApiNippouEditTemplateForm thisForm = (ApiNippouEditTemplateForm) form;
        ActionErrors err = thisForm.validateCheck(con, req);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        Element resultSet = new Element("ResultSet");
        Element result = new Element("Result");
        resultSet.addContent(result);
        Document doc = new Document(resultSet);

        BaseUserModel usModel = umodel;
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        ApiNippouEditBiz biz = new ApiNippouEditBiz(con, getRequestModel(req));
        UDate ntpDate = new UDate();
        String dateStr = NullDefault.getString(thisForm.getDate(), UDateUtil.getSlashYYMD(ntpDate));
        ntpDate = UDateUtil.getUDate(dateStr.substring(0, 4),
                dateStr.substring(5, 7),
                dateStr.substring(8, 10));

        ApiNippouEditTemplateModel apiTemplateMdl =
                biz.userTemplate(con,
                                NullDefault.getInt(thisForm.getUsrSid()
                                , sessionUsrSid), ntpDate);
        NtpTemplateModel templateMdl = apiTemplateMdl.getTemplate();
        if (templateMdl != null) {
            //AnkenUse    案件利用フラグ
            result.addContent(_createElement("AnkenUse", templateMdl.getNttAnken()));
            //CompanyUse  企業利用フラグ
            result.addContent(_createElement("CompanyUse", templateMdl.getNttComp()));
            //KtBriHhuUse 活動分類利用フラグ
            result.addContent(_createElement("KtBriHhuUse", templateMdl.getNttKatudo()));
            //MikomidoUse 見込み度利用フラグ
            result.addContent(_createElement("MikomidoUse", templateMdl.getNttMikomi()));
            //TmpFileUse  添付ファイル利用フラグ
            result.addContent(_createElement("TmpFileUse", templateMdl.getNttTemp()));
            //DetailDefault   内容入力初期値
            result.addContent(_createElement("DetailDefault", templateMdl.getNttDetail()));
        } else {
            //AnkenUse    案件利用フラグ
            result.addContent(_createElement("AnkenUse", 0));
            //CompanyUse  企業利用フラグ
            result.addContent(_createElement("CompanyUse", 0));
            //KtBriHhuUse 活動分類利用フラグ
            result.addContent(_createElement("KtBriHhuUse", 0));
            //MikomidoUse 見込み度利用フラグ
            result.addContent(_createElement("MikomidoUse", 0));
            //TmpFileUse  添付ファイル利用フラグ
            result.addContent(_createElement("TmpFileUse", 0));
            //DetailDefault   内容入力初期値
            result.addContent(_createElement("DetailDefault", ""));

        }
        List<NtpPriTargetModel> ntgList =  apiTemplateMdl.getNtgList();
        //TargetSet   目標配列　次の属性を持つ
        //    Count:目標件数
        Element targetSet = new Element("TargetSet");
        result.addContent(targetSet);
        targetSet.setAttribute("Count", String.valueOf(ntgList.size()));
        for (NtpPriTargetModel ntgModel : ntgList) {
            //Target  目標情報
            Element target = new Element("Target");
            targetSet.addContent(target);
            //目標SID
            target.addContent(_createElement("NtgSid", ntgModel.getNtgSid()));
            //目標SID
            target.addContent(_createElement("UsrSid", ntgModel.getUsrSid()));
            //Year    目標年度
            target.addContent(_createElement("Year", ntgModel.getNpgYear()));
            //Month   目標月
            target.addContent(_createElement("Month", ntgModel.getNpgMonth()));
            //Name    目標タイトル
            target.addContent(_createElement("Name", ntgModel.getNpgTargetName()));
            //Unit    目標単位
            target.addContent(_createElement("Unit", ntgModel.getNpgTargetUnit()));
            //Ratio   目標達成率
            target.addContent(_createElement("Ratio", (int) (100 * ntgModel.getNpgTargetRatio())));
            //Value   目標値
            target.addContent(_createElement("Value", ntgModel.getNpgTarget()));
            //Record  目標実績値
            target.addContent(_createElement("Record", ntgModel.getNpgRecord()));

        }

        return doc;
    }

}

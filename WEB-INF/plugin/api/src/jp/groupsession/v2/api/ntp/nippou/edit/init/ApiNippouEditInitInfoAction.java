package jp.groupsession.v2.api.ntp.nippou.edit.init;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ntp.nippou.edit.ApiNippouEditBiz;
import jp.groupsession.v2.api.ntp.nippou.edit.model.ApiNippouEditTemplateModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;
import jp.groupsession.v2.ntp.ntp040.Ntp040Biz;
/**
 * <br>[機  能] WEBAPI 日報編集初期基本情報取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouEditInitInfoAction extends AbstractApiAction {
    /** ファイルサイズ 1MB */
    private static final int FILE_SIZE_1MB = 1048576;

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
        con.setAutoCommit(true);
        Document doc = new Document();

        Element result = new Element("Result");
        doc.addContent(result);
        Ntp040Biz ntp040biz = new Ntp040Biz(con, getRequestModel(req));
        //セッション情報を取得
        BaseUserModel usModel = umodel;
        Ntp010Biz ntp010biz = new Ntp010Biz(con, getRequestModel(req));
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //個人設定を取得
        NtpPriConfModel confMdl = ntp010biz.getPrivateConf(sessionUsrSid, con);

        //リクエストパラメータを取得
        //表示開始日
        String strDspDate = new UDate().getDateString();
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);

        //活動分類取得
        List<LabelValueBean> ktBunruiList = ntp040biz.getKtbunruiLabelList(con);
        Element ktBunruiSet = new Element("KtBunruiSet");
        result.addContent(ktBunruiSet);
        ktBunruiSet.setAttribute("TotalCount", Integer.toString(ktBunruiList.size()));
        String defKtBunruiSidStr = null;
        String defKtBunruiName = null;
        for (LabelValueBean labelValueBean__ : ktBunruiList) {
            Element child = new Element("KtBunrui");
            ktBunruiSet.addContent(child);
            child.addContent(_createElement("NkbSid", labelValueBean__.getValue()));
            child.addContent(_createElement("NkbName", labelValueBean__.getLabel()));
            if (defKtBunruiSidStr == null) {
                defKtBunruiSidStr = labelValueBean__.getValue();
            }
            if (defKtBunruiName == null) {
                defKtBunruiName = labelValueBean__.getLabel();
            }
        }
        defKtBunruiSidStr = NullDefault.getString(defKtBunruiSidStr, "-1");
        defKtBunruiName = NullDefault.getString(defKtBunruiName, "未指定");
        ktBunruiSet.setAttribute("DefaultNkbSid", defKtBunruiSidStr);
        ktBunruiSet.setAttribute("DefaultNkbName", defKtBunruiName);

        //活動方法取得
        List<LabelValueBean> ktHouhouList = ntp040biz.getKthouhouLabelList(con);
        Element ktHouhouSet = new Element("KtHouhouSet");
        result.addContent(ktHouhouSet);
        ktHouhouSet.setAttribute("TotalCount", Integer.toString(ktHouhouList.size()));
        String defKtHouhouSidStr = null;
        String defKtHouhouName = null;
        for (LabelValueBean labelValueBean__ : ktHouhouList) {
            Element child = new Element("KtHouhou");
            ktHouhouSet.addContent(child);
            child.addContent(_createElement("NkhSid", labelValueBean__.getValue()));
            child.addContent(_createElement("NkhName", labelValueBean__.getLabel()));
            if (defKtHouhouSidStr == null) {
                defKtHouhouSidStr = labelValueBean__.getValue();
            }
            if (defKtHouhouName == null) {
                defKtHouhouName = labelValueBean__.getLabel();
            }
        }
        defKtHouhouSidStr = NullDefault.getString(defKtHouhouSidStr, "-1");
        defKtHouhouName = NullDefault.getString(defKtHouhouName, "未指定");
        ktHouhouSet.setAttribute("DefaultNkhSid", defKtHouhouSidStr);
        ktHouhouSet.setAttribute("DefaultNkhName", defKtHouhouName);




        //表示項目設定
//        int iniPub = 0;
//        int iniMikomido = GSConstNippou.DF_MIKOMIDO;
        int iniFcolor = GSConstNippou.DF_BG_COLOR;
//        int iniEdit = GSConstNippou.EDIT_CONF_NONE;
        if (confMdl != null) {
//                iniPub = confMdl.getNprIniPublic();
            iniFcolor = confMdl.getNprIniFcolor();
//                iniEdit = biz.getInitEditAuth(con, confMdl);
        }
        if (iniFcolor < 1 || 5 < iniFcolor) {
            iniFcolor = GSConstNippou.DF_BG_COLOR;
        }


        //デフォルト表示グループ
        NtpCommonBiz scBiz = new NtpCommonBiz(con, getRequestModel(req));
        String dspGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);

        //表示グループ
        String dspGpName = "";
        List<NtpLabelValueModel> grpLabelList = ntp010biz.getGroupLabelList(con, sessionUsrSid);
        for (NtpLabelValueModel ntpLabelValueModel__ : grpLabelList) {
            if (ntpLabelValueModel__.getValue().equals(dspGpSidStr)) {
                dspGpName = ntpLabelValueModel__.getLabel();
            }
        }
        result.addContent(_createElement("DefaultGrpSid", dspGpSidStr));
        result.addContent(_createElement("DefaultGrpName", dspGpName));



        //共通項目
        //背景色
        result.addContent(_createElement("DefaultColor", String.valueOf(iniFcolor)));

        //見込み度
        result.addContent(_createElement("DefaultMikomi", GSConstNippou.DF_MIKOMIDO));

        //年コンボを作成
//        form.setNtp040YearLavel(getYearLavel(dspDate.getYear()));
        //月コンボを作成
//        form.setNtp040MonthLavel(getMonthLavel());
        //日コンボを作成
//        form.setNtp040DayLavel(getDayLavel());
        //時コンボを作成
        List<LabelValueBean> labelList = ntp040biz.getHourLavel();
        Element hourSet = new Element("HourSet");
        result.addContent(hourSet);
        hourSet.setAttribute("TotalCount", Integer.toString(labelList.size()));
        for (LabelValueBean labelValueBean__ : labelList) {
            Element hour = new Element("Hour");
            hourSet.addContent(hour);
            hour.addContent(_createElement("Value", labelValueBean__.getValue()));
            hour.addContent(_createElement("Label", labelValueBean__.getLabel()));
        }

        //分コンボを作成
        labelList = ntp040biz.getMinuteLavel(con);
        Element minuteSet = new Element("MinuteSet");
        result.addContent(minuteSet);
        minuteSet.setAttribute("TotalCount", Integer.toString(labelList.size()));
        for (LabelValueBean labelValueBean__ : labelList) {
            Element minute = new Element("Minute");
            minuteSet.addContent(minute);
            minute.addContent(_createElement("Value", labelValueBean__.getValue()));
            minute.addContent(_createElement("Label", labelValueBean__.getLabel()));
        }

        //時間
        UDate frDate = null;
        UDate toDate = null;
        if (confMdl != null) {
            frDate = confMdl.getNprIniFrDate();
            toDate = confMdl.getNprIniToDate();
        } else {
            frDate = new UDate();
            toDate = new UDate();
            frDate.setHour(GSConstNippou.DF_FROM_HOUR);
            frDate.setMinute(GSConstNippou.DF_FROM_MINUTES);
            toDate.setHour(GSConstNippou.DF_TO_HOUR);
            toDate.setMinute(GSConstNippou.DF_TO_MINUTES);
        }

        result.addContent(_createElement("DefaultFrHour", frDate.getIntHour()));
        result.addContent(_createElement("DefaultFrMinute", frDate.getIntMinute()));

        result.addContent(_createElement("DefaultToHour", toDate.getIntHour()));
        result.addContent(_createElement("DefaultToMinute", toDate.getIntMinute()));

        //管理者権限
        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(
                con, usModel, GSConstNippou.PLUGIN_ID_NIPPOU);
        if (adminUser) {
            result.addContent(_createElement("AdmKubun", GSConst.USER_ADMIN));

        } else {
            result.addContent(_createElement("AdmKubun", GSConst.USER_NOT_ADMIN));
        }

        //ユーザ情報取得
        CmnUsrmInfModel model = new CmnUsrmInfModel();
        CmnUsrmInfModel usrModel =  ntp040biz.getUsrInfo(String.valueOf(sessionUsrSid));
        if (model != null) {
            result.addContent(_createElement("UsrSid", usrModel.getUsrSid()));
            result.addContent(_createElement("UsrName",
                    usrModel.getUsiSei() + " " + usrModel.getUsiMei()));
            if (usrModel.getBinSid() == 0) {
                //写真なし
                result.addContent(_createElement("UsrImgPath", ""));
            } else {
                    //写真あり 公開
                result.addContent(_createElement("UsrImgPath"
                        , "./common/cmn100.do?CMD=getImageFile&cmn100binSid="
                                + usrModel.getBinSid()));

            }
        }
        int maxSize = 0;

        CmnFileConfDao cfcDao = new CmnFileConfDao(con);
        //添付ファイル最大容量取得

        CmnFileConfModel cfcMdl = cfcDao.select();
        maxSize = cfcMdl.getFicMaxSize() * FILE_SIZE_1MB;

        result.addContent(_createElement("TempMaxSize", maxSize));

        ApiNippouEditBiz biz = new ApiNippouEditBiz(con, getRequestModel(req));
        UDate ntpDate = new UDate();
        String dateStr = UDateUtil.getSlashYYMD(ntpDate);
        ntpDate = UDateUtil.getUDate(dateStr.substring(0, 4),
                dateStr.substring(5, 7),
                dateStr.substring(8, 10));
        ApiNippouEditTemplateModel apiTemplateMdl =
                biz.userTemplate(con, sessionUsrSid, ntpDate);
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
        return doc;
    }

}

package jp.groupsession.v2.api.schedule.otherplugin.schlist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.api.ApiDataTypeUtil;
import jp.groupsession.v2.api.schedule.AbstractApiSchAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchAppendDataParam;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 *
 * <br>[機  能] 他のプラグインのスケジュールを取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchOtherPlginSchListAction extends AbstractApiSchAction {

    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        ApiSchOtherPlginSchListForm form = (ApiSchOtherPlginSchListForm) aForm;
        RequestModel reqMdl = getRequestModel(req);

        ActionErrors err = form.validateCheck(new GsMessage(reqMdl));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        List<SchDataModel> schDataList  = __getAppendPlgData(req, con, form);

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Attribute atCount = new Attribute("TotalCount", Integer.toString(schDataList.size()));
        resultSet.setAttribute(atCount);
        log__.debug("外部プラグインスケジュールカウント = " + schDataList.size());
        //XMLデータ作成
        for (SchDataModel scData : schDataList) {
            //Result
            Element result = new Element("Result");
            resultSet.addContent(result);

            //SchKf スケジュール公開フラグ
            result.addContent(_createElement("SchKf", scData.getScdPublic()));
            //Title
            result.addContent(_createElement("Title", scData.getScdTitle()));
            //Naiyo
            result.addContent(_createElement("Naiyo", scData.getScdValue()));
            //Biko
            result.addContent(_createElement("Biko", scData.getScdBiko()));
            //StartDateTime
            Element startDateTime = new Element("StartDateTime");
            startDateTime.addContent(ApiDataTypeUtil.getDateTime(scData.getScdFrDate()));
            result.addContent(startDateTime);
            //EndDateTime
            Element endDateTime = new Element("EndDateTime");
            endDateTime.addContent(ApiDataTypeUtil.getDateTime(scData.getScdToDate()));
            result.addContent(endDateTime);
            //TimeKbn
            result.addContent(_createElement("TimeKbn", scData.getScdDaily()));
            //UserKbn
            result.addContent(_createElement("UserKbn", scData.getScdUsrKbn()));
            //UserSid
            result.addContent(_createElement("UserSid", scData.getScdUsrSid()));
            //UserName
            result.addContent(_createElement("UserName", scData.getScdUserName()));
            //UserUkoFlg
            result.addContent(_createElement("UserUkoFlg", scData.getScdUserUkoFlg()));
            //ColorKbn
            result.addContent(_createElement("ColorKbn", scData.getScdBgcolor()));
            //AppendUrl
            result.addContent(_createElement("AppendUrl", scData.getScdAppendUrl()));
            //AppendId
            result.addContent(_createElement("AppendId", scData.getScdAppendId()));
            //AppendDspName
            result.addContent(_createElement("AppendDspName", scData.getScdAppendDspName()));

        }
        log__.debug("createXml end");

        return doc;
    }


    /**
     * <br>[機  能] 他プラグインデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param form form
     * @throws SQLException SQL実行時例外
     * @return 他のプラグインスケジュール
     */
    private ArrayList <SchDataModel> __getAppendPlgData(
            HttpServletRequest req,
            Connection con,
            ApiSchOtherPlginSchListForm form
            ) throws SQLException {
        RequestModel reqMdl = getRequestModel(req);
        //他プラグイン情報を取得
        int sessionUsrSid = getSessionUserSid(req);

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, sessionUsrSid);

        SchCommonBiz scBiz = new SchCommonBiz(reqMdl);
        ArrayList<SchDataModel> apdSchList = new ArrayList<SchDataModel>();
        int usrSid = NullDefault.getInt(form.getUsid(), sessionUsrSid);
        //From
        String from = form.getFrom();
        UDate frDate = null;
        if (StringUtil.isNullZeroString(from)) {
            //未入力の場合現在日
            frDate = new UDate();
            frDate.resetTime();
        } else {
            frDate = UDate.getInstanceStr(from.substring(0, 10));
        }
        //To
        String to = form.getTo();
        UDate toDate = null;
        if (StringUtil.isNullZeroString(to)) {
            //未入力の場合現在日 + 1月
            toDate = new UDate();
            toDate.resetTime();
            toDate.addMonth(1);
        } else {
            toDate = UDate.getInstanceStr(to.substring(0, 10));
        }
        //デフォルト表示グループ
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);

        if (pconfig != null) {
            UDate prmFrDate = frDate.cloneUDate();
            UDate prmToDate = toDate.cloneUDate();
            SchAppendDataParam paramMdl = new SchAppendDataParam();
            paramMdl.setUsrSid(usrSid);
            paramMdl.setFrDate(prmFrDate);
            paramMdl.setToDate(prmToDate);
            paramMdl.setSrcId(GSConstSchedule.DSP_ID_SCH010);

            paramMdl.setGrpSid(NullDefault.getString(form.getGsid(), dfGpSidStr));

            GsMessage gsMsg = new GsMessage(reqMdl);
            //年
            String textYear = gsMsg.getMessage("cmn.year", new String[] {frDate.getStrYear()});
            //月
            String textMonth = gsMsg.getMessage("cmn.month");
            String dspDate = textYear + frDate.getStrMonth() + textMonth;
            paramMdl.setDspDate(dspDate);
            List<SchDataModel> tmpSchList = new ArrayList<SchDataModel>();
            try {
                tmpSchList = scBiz.getAppendSchData(reqMdl, con, pconfig, paramMdl);
            } catch (Exception e) {
                log__.error("他プラグインのスケジュールデータ取得に失敗");
            }
            //公開区分反映

            //予定あり
            String textYoteiari = gsMsg.getMessage("schedule.src.9");

            for (SchDataModel schDataModel__ : tmpSchList) {

                if (GSConstCommon.PLUGIN_ID_NIPPOU.equals(schDataModel__.getScdAppendId())) {
                    schDataModel__.setScdAppendDspName(gsMsg.getMessage("cmn.action"));
                } else if (GSConstCommon.PLUGIN_ID_PROJECT
                        .equals(schDataModel__.getScdAppendId())) {
                    schDataModel__.setScdAppendDspName("TODO");
                } else {
                    schDataModel__.setScdAppendDspName("");
                }


                usrSid = schDataModel__.getScdUsrSid();
                int usrKbn = schDataModel__.getScdUsrKbn();
                if (usrKbn == GSConstSchedule.USER_KBN_USER
                        && usrSid == sessionUsrSid) {
                    //本人
                } else if (usrKbn == GSConstSchedule.USER_KBN_USER
                        && usrSid != sessionUsrSid) {
                    //他ユーザ
                    if (schDataModel__.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
                        //予定あり
                        schDataModel__.setScdTitle(textYoteiari);
                        schDataModel__.setScdValue("");
                        schDataModel__.setScdBiko("");
                    } else if (schDataModel__.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
                        //非公開
                        continue;
                    }
                }

                // ユーザ名、無効フラグ取得
                String usrName   = "";
                int    usrUkoFlg = GSConst.YUKOMUKO_YUKO;
                if (usrKbn == GSConstSchedule.USER_KBN_USER) {
                    CmnUsrmInfDao uDao = new CmnUsrmInfDao(con);
                    CmnUsrmInfModel uMdl = uDao.selectUserNameAndJtkbn(usrSid);
                    if (uMdl != null) {
                        usrName   = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
                        usrUkoFlg = uMdl.getUsrUkoFlg();
                    }
                } else if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {
                    GroupDao gDao = new GroupDao(con);
                    CmnGroupmModel gMdl = gDao.getGroup(usrSid);
                    if (gMdl != null) {
                        usrName = gMdl.getGrpName();
                    }
                }
                //schDataModel__.setScdUserName(__getUserName(usrSid, usrKbn, con));
                schDataModel__.setScdUserName(usrName);
                schDataModel__.setScdUserUkoFlg(usrUkoFlg);

                apdSchList.add(schDataModel__);
            }

        }
        return apdSchList;
    }
}

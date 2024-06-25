package jp.groupsession.v2.api.ntp.nippou.detail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ntp.nippou.biz.ApiNippouSearchBiz;
import jp.groupsession.v2.api.ntp.nippou.model.ApiNippouDataModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.dao.NippouSearchDao;
import jp.groupsession.v2.ntp.model.AnkenPermitCheckModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.ntp040.Ntp040Biz;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040CommentModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] WEBAPI 日報詳細情報取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouDetailAction extends AbstractApiAction {
    /** ロガーオブジェクト */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        ApiNippouDetailForm thisForm = (ApiNippouDetailForm) form;
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }

        BaseUserModel usModel = umodel;
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        int usrSid = NullDefault.getInt(thisForm.getUsrSid(), sessionUsrSid);

        UDate date = new UDate();
        date.setZeroHhMmSs();
        if (!StringUtil.isNullZeroString(thisForm.getDate())) {
            date.setYear(Integer.parseInt(thisForm.getDate().substring(0, 4)));
            date.setMonth(Integer.parseInt(thisForm.getDate().substring(5, 7)));
            date.setDay(Integer.parseInt(thisForm.getDate().substring(8, 10)));
        }


        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        resultSet.setAttribute("usrSid", Integer.toString(usrSid));
        UserSearchDao uDao = new UserSearchDao(con);

        UserSearchModel uMdl = uDao.getUserInfoJtkb(usrSid, -1);
        String usrName = "";
        if (uMdl != null) {
            usrName = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
        }
        resultSet.setAttribute("usrName", usrName);
        resultSet.setAttribute("date", UDateUtil.getSlashYYMD(date));

        //特例アクセス閲覧不可ユーザの取得
        //特例アクセスで閲覧権限がないユーザは表示しない
        NippouDao ntpDao = new NippouDao(con);
        List<Integer> notAccessUserList = null;
        notAccessUserList = ntpDao.getSpAccessUserList(-1, sessionUsrSid, false, -1);

        boolean spAccessFlg = true;
        for (int notAccessUser:notAccessUserList) {
            if (usrSid == notAccessUser) {
                spAccessFlg = false;
            }
        }
        //特例でアクセス許可がされておらず共有範囲=所属グループで所属グループ外だったらエラー
        if (!spAccessFlg) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = null;
            GsMessage gsmsg = new GsMessage(req);
            String view = gsmsg.getMessage("cmn.reading");
            msg = new ActionMessage(
                    "error.edit.power.user", view, view);
            StrutsUtil.addMessage(errors, msg, "usrSid");

            addErrors(req, errors);
            return null;

        }

        ApiNippouSearchBiz searchBiz = new ApiNippouSearchBiz(getRequestModel(req), con);
        List<ApiNippouDataModel> dataList =
                searchBiz.getReports(__getReportsBase(con, usrSid, date,
                        notAccessUserList, getRequestModel(req)));

        resultSet.setAttribute("TotalCount", Integer.toString(dataList.size()));
        ArrayList<Integer> ntpSids = new ArrayList<Integer>();

        for (ApiNippouDataModel dataModel__ : dataList) {
            ntpSids.add(new Integer(dataModel__.getNipSid()));
            Element result = new Element("Result");
            resultSet.addContent(result);
            result.addContent(_createElement("NipSid", dataModel__.getNipSid())); //日報SID
            CmnUsrmInfModel usrMdl = dataModel__.getUsrMdl();

            result.addContent(_createElement("UsrSid", dataModel__.getUsrSid())); //ユーザSID
            result.addContent(_createElement("UsrName",
                    usrMdl.getUsiSei() + " " + usrMdl.getUsiMei())); //ユーザ名
            result.addContent(_createElement("UsrUkoFlg", usrMdl.getUsrUkoFlg())); //ユーザ無効フラグ
            if (usrMdl.getBinSid() == 0) {
                //写真なし
                result.addContent(_createElement("UsrImgPath", ""));
            } else {
                if (usrMdl.getUsiPictKf() == 0) {
                    //写真あり 公開
                    result.addContent(_createElement("UsrImgPath",
                            "./common/cmn100.do?CMD=getImageFile&cmn100binSid="
                                 + usrMdl.getBinSid()));

                } else {
                    //写真あり 非公開
                    result.addContent(_createElement("UsrImgPath", "secret"));
                }
            }

            result.addContent(_createElement("AddUsrSid", dataModel__.getNipEuid())); //登録ユーザSID
            result.addContent(_createElement("AddUsrName", dataModel__.getAddUsrName())); //登録ユーザ名
            result.addContent(_createElement("AddUsrUkoFlg",
                    dataModel__.getAddUsrUkoFlg())); //登録ユーザ無効フラグ
            result.addContent(_createElement("NipDate",
                    UDateUtil.getSlashYYMD(dataModel__.getNipDate()))); //登録日
            int hour = dataModel__.getNipFrTime().getIntHour();
            if (!dataModel__.getNipFrTime().equalsDate(dataModel__.getNipDate())) {
                hour += 24;
            }
            result.addContent(_createElement("NipFrTime",
                    (StringUtil.toDecFormat(hour, "00")
                            + ":" + StringUtil.toDecFormat(
                                    dataModel__.getNipFrTime().getIntMinute(), "00"
                                    )))); //開始時間
            hour = dataModel__.getNipToTime().getIntHour();
            if (!dataModel__.getNipToTime().equalsDate(dataModel__.getNipDate())) {
                hour += 24;
            }
            result.addContent(_createElement("NipToTime",
                    (StringUtil.toDecFormat(hour, "00")
                            + ":" + StringUtil.toDecFormat(
                                    dataModel__.getNipToTime().getIntMinute(), "00"
                                    )))); //終了時間

            result.addContent(_createElement("NipTitle", dataModel__.getNipTitle())); //日報タイトル
            result.addContent(_createElement("NipTitleClo",
                    dataModel__.getNipTitleClo())); //タイトルカラー
            result.addContent(_createElement("NipMikomi", dataModel__.getNipMikomi())); //見込み度
            result.addContent(_createElement("NipSyokan", dataModel__.getNipDetail())); //所感
            result.addContent(_createElement("NanSid", dataModel__.getNanSid())); //案件SID
            result.addContent(_createElement("NanName", dataModel__.getNanName())); //案件名
            result.addContent(_createElement("AcoSid", dataModel__.getAcoSid())); //会社SID
            result.addContent(_createElement("AcoName", dataModel__.getAcoName())); //会社名
            result.addContent(_createElement("AbaSid", dataModel__.getAbaSid())); //拠点SID
            result.addContent(_createElement("AbaName", dataModel__.getAbaName())); //拠点名
            result.addContent(_createElement("MkbSid", dataModel__.getMkbSid())); //活動区分SID
            result.addContent(_createElement("MkbName", dataModel__.getKtBunrui())); //活動区分名
            result.addContent(_createElement("MkhSid", dataModel__.getMkhSid())); //活動方法SID
            result.addContent(_createElement("MkhName", dataModel__.getKtHouhou())); //活動方法名
            result.addContent(_createElement("IineCount", dataModel__.getIineCount())); //いいね回数
            result.addContent(_createElement("IineFlg", dataModel__.getIineFlg())); //いいねフラグ
            if (dataModel__.isAnkenViewable()) {
                result.addContent(_createElement("AnkenViewable", 1)); //案件公開
            } else {
                result.addContent(_createElement("AnkenViewable", 0)); //案件非公開
            }

            Element tempFileSet = new Element("TempFileSet");
            result.addContent(tempFileSet);
            tempFileSet.setAttribute("TotalCount",
                    Integer.toString(dataModel__.getClips().size()));
            for (CmnBinfModel binModel : dataModel__.getClips()) {
                Element binFile = new Element("File");
                tempFileSet.addContent(binFile);
                binFile.addContent(_createElement("BinSid", binModel.getBinSid())); //バイナリーSID
                binFile.addContent(_createElement("BinFileName",
                        binModel.getBinFileName())); //ファイル名
            }



            Element commentSet = new Element("CommentSet");
            result.addContent(commentSet);
            commentSet.setAttribute("TotalCount",
                    Integer.toString(dataModel__.getComments().size()));

            for (Ntp040CommentModel commentModel : dataModel__.getComments()) {
                Element comment = new Element("Comment");
                commentSet.addContent(comment);
                comment.addContent(_createElement("AdmFlg", commentModel.getNtp040CommentDelFlg()));
                comment.addContent(_createElement("NpcSid",
                        commentModel.getNtp040CommentMdl().getNpcSid()));
                comment.addContent(_createElement("UsrSid",
                        commentModel.getNtp040CommentMdl().getUsrSid()));
                comment.addContent(_createElement("UsrName",
                        (commentModel.getNtp040UsrInfMdl().getUsiSei() + " "
                                + commentModel.getNtp040UsrInfMdl().getUsiMei())));
                comment.addContent(_createElement("UsrUkoFlg",
                        commentModel.getNtp040UsrInfMdl().getUsrUkoFlg()));
                comment.addContent(_createElement("NpcComment",
                        StringUtilHtml.transToText(
                                commentModel.getNtp040CommentMdl().getNpcComment())));
                if (commentModel.getNtp040UsrInfMdl().getBinSid() == 0) {
                    //写真なし
                    comment.addContent(_createElement("UsrImgPath", ""));
                } else {
                    if (commentModel.getNtp040UsrInfMdl().getUsiPictKf() == 0) {
                        //写真あり 公開
                        comment.addContent(_createElement("UsrImgPath",
                                "./common/cmn100.do?CMD=getImageFile&cmn100binSid="
                                       + commentModel.getNtp040UsrInfMdl().getBinSid()));

                    } else {
                        //写真あり 非公開
                        comment.addContent(_createElement("UsrImgPath", "secret"));
                    }
                }
            }

        }
        Ntp040Biz ntp040Biz = new Ntp040Biz(con, getRequestModel(req));

        //表示する日報をすべて確認済にする
        if (!ntpSids.isEmpty()) {
            ntp040Biz.setCheck(ntpSids, sessionUsrSid);
        }

        return doc;
    }
    /**
     * 日報ベースモデル取得
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid usrSid
     * @param date 取得日
     * @param notAccessUserList 特例閲覧不可ユーザ
     * @param reqMdl リクエストモデル
     * @return list 一覧
     * @throws SQLException SQL実行例外
     */
    List<NtpDataModel> __getReportsBase(
            Connection con,
            int usrSid,
            UDate date,
            List<Integer> notAccessUserList,
            RequestModel reqMdl) throws SQLException {
        //選択された日付の日報データをすべて取得する

        //日報データ取得
        ArrayList<NtpDataModel> ntpMdlList = new ArrayList<NtpDataModel>();
        NippouSearchDao dao = new NippouSearchDao(con);

        UDate fromDate = date.cloneUDate();
        fromDate.setZeroHhMmSs();
        UDate toDate = date.cloneUDate();
        toDate.setMaxHhMmSs();
        //非公開案件日報を秘匿するのに必要なモデルをセット
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl,
                ntpCmnBiz.isNippouAdmin(con, reqMdl));

        try {
            ntpMdlList = dao.select(usrSid,
                    0,
                    -1,
                    fromDate,
                    toDate,
                    GSConstNippou.DSP_MOD_DAY, napMdl);
        } catch (SQLException e) {
            log__.error("日報情報の取得に失敗" + e);
            throw e;
        }
        return ntpMdlList;
    }
}

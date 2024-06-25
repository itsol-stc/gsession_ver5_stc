package jp.groupsession.v2.api.ntp.nippou.edit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.ntp.nippou.edit.model.ApiNippouEditModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.model.NippouSearchModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;
import jp.groupsession.v2.ntp.ntp040.Ntp040Biz;
import jp.groupsession.v2.ntp.ntp060.Ntp060AnkenDao;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] WEBAPI 日報編集フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouEdit",
plugin = "nippou", name = "日報登録",
url = "/api/ntp/nippouEdit.do", reqtype = "POST")
public class ApiNippouEditForm extends AbstractApiForm {
    /** ロガーオブジェクト */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    /** 展開済み日報一覧 */
    private ApiNippouEditModel report__;
    /** 日報SID */
    @ApiParam(name = "nipSid", viewName = "日報SID")
    private String nipSid__;
    /** コマンド */
    @ApiParam(name = "cmd", viewName = "コマンド")
    private String cmd__;
    /** ユーザSID */
    @ApiParam(name = "usrSid", viewName = "ユーザSID")
    private String usrSid__;
    /** 日報日付 */
    @ApiParam(name = "nipDate", viewName = "報告日")
    private String nipDate__;
    /** 開始時間 */
    @ApiParam(name = "nipFrTime", viewName = "開始時間")
    private String nipFrTime__;
    /** 終了時間 */
    @ApiParam(name = "nipToTime", viewName = "終了時間")
    private String nipToTime__;
    /** 案件SID */
    private String nanSid__;
    /** 会社SID */
    private String acoSid__;
    /** 拠点SID */
    private String abaSid__;
    /** タイトル */
    @ApiParam(name = "nipTitle", viewName = "タイトル")
    private String nipTitle__;
    /** タイトルカラー */
    private String nipTitleClo__;
    /** 活動分類SID */
    private String mkbSid__;
    /** 活動方法SID */
    private String mkhSid__;
    /** 内容 */
    @ApiParam(name = "nipDetail", viewName = "活動内容詳細", required = false, confRequired = true)
    private String nipDetail__;
    /** 見込み度 */
    private String nipMikomi__;
    /** 公開フラグ */
    private String nipPublic__;
    /** 編集権減フラグ */
    private String nipEdit__;
    /** 添付ファイル　*/
    private FormFile tempFile1__;
    /** 添付ファイル　*/
    private FormFile tempFile2__;
    /** 添付ファイル　*/
    private FormFile tempFile3__;
    /** 添付ファイル　*/
    private FormFile tempFile4__;
    /** 添付ファイル　*/
    private FormFile tempFile5__;
    /** 削除する添付ファイルのbinSid　*/
    private String delBinSid__;
    /** 削除する添付ファイルのbinSid配列　*/
    private String[] delBinSidArr__;

    /**
     * <p>reports を取得します。
     * @return reports
     */
    public ApiNippouEditModel getReport() {
        return report__;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
            Connection con, RequestModel reqMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        BaseUserModel usModel = reqMdl.getSmodel();
        Ntp010Biz ntp010biz = new Ntp010Biz(con, reqMdl);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        boolean isAdd = true;

        //個人設定を取得
        NtpPriConfModel confMdl = ntp010biz.getPrivateConf(sessionUsrSid, con);

            ApiNippouEditModel model = new ApiNippouEditModel();
            /** NIP_SID mapping */
            String nipSid = nipSid__;
            if (StringUtil.isNullZeroString(nipSid)) {
                msg = new ActionMessage("error.input.required.text", GSConstNippou.TEXT_NIPPOU_SID);
                StrutsUtil.addMessage(errors, msg, "nipSid");
                return errors;
            }
            if (!GSValidateUtil.isNumberHaifun(nipSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_NIPPOU_SID);
                StrutsUtil.addMessage(errors, msg, "nipSid");
                return errors;

            }
            model.setNipSid(Integer.parseInt(nipSid));

            String cmd = cmd__;


            model.setCmd(cmd);
            if (cmd.equals("deleteNtp")) {
                return errors;
            }

            /** USR_SID mapping */
            String usrSid = usrSid__;
            if (StringUtil.isNullZeroString(usrSid)) {
                msg = new ActionMessage("error.input.required.text", GSConstNippou.TEXT_USER_SID);
                StrutsUtil.addMessage(errors, msg, "usrSid");
                return errors;
            }
            if (!GSValidateUtil.isNumberHaifun(usrSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_USER_SID);
                StrutsUtil.addMessage(errors, msg, "usrSid");
                return errors;

            }
            int selUsrSid = Integer.parseInt(usrSid);
            model.setSelectUsrSid(selUsrSid);

            NippouSearchModel ntpMdl = null;
            if (getCmd().equals("editNtp")) {
                Ntp040Biz ntp040biz = new Ntp040Biz(con, reqMdl);
                isAdd = false;
                int changeNtpSid = NullDefault.getInt(
                        nipSid, -1);
                List<NippouSearchModel> ntpList = ntp040biz.getNtpDataSingle(changeNtpSid, con);
                if (ntpList.size() > 0) {
                    ntpMdl = ntpList.get(0);
                }
            }

            //日報閲覧権限チェック
            NtpCommonBiz ncbiz = new NtpCommonBiz(con, reqMdl);
            CommonBiz commonBiz = new CommonBiz();
            if (!ncbiz.isCanInspection(sessionUsrSid, selUsrSid, con)
                    || (selUsrSid != sessionUsrSid
                       && !commonBiz.isPluginAdmin(con, usModel, GSConstNippou.PLUGIN_ID_NIPPOU))) {
                //閲覧
                String edit = gsMsg.getMessage("cmn.edit");
                msg = new ActionMessage(
                        "error.edit.power.user", edit, edit);

                StrutsUtil.addMessage(errors, msg, "admFlg");
                return errors;
            }


            /** NIP_DATE mapping */
            String nipDate = nipDate__;
            GSValidateCommon.validateDateFieldText(errors,
                    nipDate, "nipDate", GSConstNippou.TEXT_REPORT_DATE, false);
            if (!errors.isEmpty()) {
                return errors;
            }
            if (StringUtil.isNullZeroString(nipDate)) {
                UDate now = new UDate();
                nipDate = UDateUtil.getSlashYYMD(now);
            }
            UDate udate = UDateUtil.getUDate(nipDate.substring(0, 4),
                    nipDate.substring(5, 7), nipDate.substring(8, 10));
            udate.setZeroHhMmSs();
            model.setNtpYear(udate.getYear());
            model.setNtpMonth(udate.getMonth());
            model.setNtpDay(udate.getIntDay());

            //開始年月日チェックフラグ(true=入力OK、false=NG)
            boolean fromOk = false;

            int iSYear = udate.getYear();
            int iSMonth = udate.getMonth();
            int iSDay = udate.getIntDay();

            UDate frDate = new UDate();
            frDate.setDate(iSYear, iSMonth, iSDay);
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            if (frDate.getYear() != iSYear
            || frDate.getMonth() != iSMonth
            || frDate.getIntDay() != iSDay) {
                msg = new ActionMessage("error.input.notfound.date",
                        gsMsg.getMessage("schedule.sch100.10"));
                errors.add("error.input.notfound.date", msg);
                log__.debug("error:1");
            } else {
                fromOk = true;
            }

            //終了年月日チェックフラグ(true=入力OK、false=NG)
            boolean toOk = false;

            int iEYear = udate.getYear();
            int iEMonth = udate.getMonth();
            int iEDay = udate.getIntDay();

            UDate toDate = new UDate();
            toDate.setDate(iEYear, iEMonth, iEDay);
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            if (toDate.getYear() != iEYear
            || toDate.getMonth() != iEMonth
            || toDate.getIntDay() != iEDay) {
                msg = new ActionMessage("error.input.notfound.date",
                        gsMsg.getMessage("schedule.sch100.15"));
                errors.add("error.input.notfound.date", msg);
                log__.debug("error:2");
            } else {
                toOk = true;
            }
            /** NIP_FR_TIME mapping */
            String frHour, frMin, toHour, toMin;
            String nipFrTime = NullDefault.getString(nipFrTime__, "");
            if (nipFrTime.length() != 5
                    || !ValidateUtil.isNumber(nipFrTime.substring(0, 2))
                    || !ValidateUtil.isNumber(nipFrTime.substring(3, 5))) {
                msg = new ActionMessage("error.input.format.text", "nipFrTime");
                StrutsUtil.addMessage(
                        errors, msg, "nipFrTime.開始時間");
                nipFrTime = "00:00";
            }
            frHour = nipFrTime.substring(0, 2);
            frMin = nipFrTime.substring(3, 5);



            /** NIP_TO_TIME mapping */
            String nipToTime = nipToTime__;
            if (nipToTime.length() != 5
                    || !ValidateUtil.isNumber(nipToTime.substring(0, 2))
                    || !ValidateUtil.isNumber(nipToTime.substring(3, 5))) {
                msg = new ActionMessage("error.input.format.text", "nipFrTime");
                StrutsUtil.addMessage(
                        errors, msg, "nipToTime.終了時間");
                nipToTime = "00:00";
            }
            toHour = nipToTime.substring(0, 2);
            toMin = nipToTime.substring(3, 5);

            frDate.setHour(Integer.valueOf(frHour));
            frDate.setMinute(Integer.valueOf(frMin));
            toDate.setHour(Integer.parseInt(toHour));
            toDate.setMinute(Integer.parseInt(toMin));

            //個別チェックOKの場合
            if (fromOk && toOk) {

                //from～to大小チェック
                if (frDate.compare(frDate, toDate) != UDate.LARGE) {
                    //開始 < 終了
                    String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
                    //開始・終了
                    String textStartEnd = gsMsg.getMessage("cmn.start.end");
                    msg = new ActionMessage("error.input.comp.text",
                            textStartEnd, textStartLessEnd);
                    errors.add("" + "error.input.comp.text", msg);
                    log__.debug("error:5");
                }

            }

            model.setFrHour(Integer.parseInt(frHour));
            model.setFrMin(Integer.parseInt(frMin));

            model.setToHour(Integer.parseInt(toHour));
            model.setToMin(Integer.parseInt(toMin));

            /** NAN_SID mapping */
            String nanSid = NullDefault.getString(nanSid__, "-1");
            if (!GSValidateUtil.isNumberHaifun(usrSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_ANKEN_SID);
                StrutsUtil.addMessage(errors, msg, "nanSid");
            }
            int intNanSid = NullDefault.getInt(nanSid__, -1);
            if (intNanSid > 0
                    && (isAdd
                            || (ntpMdl != null
                            && ntpMdl.getNanSid() != intNanSid))) {
                //新規登録時と編集時の案件SIDが違う場合は案件の権限チェック
                Ntp060AnkenDao ankenDao = new Ntp060AnkenDao(con);
                NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl);

                List<Integer> nanSidList = ankenDao.getNoPermissionAnkenSids(
                        Arrays.asList(Integer.valueOf(intNanSid)),
                        GSConst.SP_AUTH_EDIT, sessionUsrSid,
                        ntpCmnBiz.isNippouAdmin(con, reqMdl));
                if (nanSidList.size() > 0) {
                    String action;
                    if (isAdd) {
                        action = gsMsg.getMessage("cmn.entry");
                    } else {
                        action = gsMsg.getMessage("cmn.edit");
                    }
                    msg = new ActionMessage("error.edit.power.notfound",
                            gsMsg.getMessage("ntp.11"),
                            action);
                    errors.add("" + "error.edit.power.notfound.anken", msg);

                }
            }

            model.setAnkenSid(NullDefault.getInt(nanSid, -1));
            /** ACO_SID mapping */
            String acoSid = NullDefault.getString(acoSid__, "-1");
            if (!GSValidateUtil.isNumberHaifun(usrSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_COMPANY_SID);
                StrutsUtil.addMessage(errors, msg, "acoSid");
            }
            model.setCompanySid(NullDefault.getInt(acoSid, -1));
            /** ABA_SID mapping */
            String abaSid = NullDefault.getString(abaSid__, "-1");
            if (!GSValidateUtil.isNumberHaifun(usrSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_COMPANY_SID);
                StrutsUtil.addMessage(errors, msg, "abaSid");
            }
            model.setCompanyBaseSid(NullDefault.getInt(abaSid, -1));
            /** NIP_TITLE mapping */
            String nipTitle = nipTitle__;
            GSValidateCommon.validateTextField(errors,
                    nipTitle, "nipTitle",
                    GSConstNippou.TEXT_TITLE, GSConstNippou.MAX_LENGTH_TITLE, true);

            model.setTitle(nipTitle);
            /** NIP_TITLE_CLO mapping */
            int iniFcolor = GSConstNippou.DF_BG_COLOR;
            if (confMdl != null) {
                iniFcolor = confMdl.getNprIniFcolor();
            }
            if (iniFcolor < 1 || 5 < iniFcolor) {
                iniFcolor = GSConstNippou.DF_BG_COLOR;
            }

            String nipTitleClo = NullDefault.getString(nipTitleClo__, String.valueOf(iniFcolor));
            if (!GSValidateUtil.isNumber(nipTitleClo)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_TITLE_COLOR);
                StrutsUtil.addMessage(errors, msg, "nipTitleClo");

            }
            model.setBgcolor(Integer.parseInt(nipTitleClo));
            /** MKB_SID mapping */
            String mkbSid = NullDefault.getString(mkbSid__, "-1");
            if (!GSValidateUtil.isNumberHaifun(mkbSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_COMPANY_SID);
                StrutsUtil.addMessage(errors, msg, "mkbSid");

            }
            model.setKtbunruiSid(NullDefault.getInt(mkbSid__, -1));
            /** MKH_SID mapping */
            String mkhSid = NullDefault.getString(mkhSid__, "-1");
            if (!GSValidateUtil.isNumberHaifun(mkhSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_COMPANY_SID);
                StrutsUtil.addMessage(errors, msg, "mkhSid");

            }
            model.setKthouhouSid(NullDefault.getInt(mkhSid__, -1));
            /** NIP_DETAIL mapping */
            String nipDetail = NullDefault.getString(nipDetail__, "");
            GSValidateCommon.validateTextAreaField(errors, nipDetail,
                    "nipDetail", GSConstNippou.TEXT_NAIYO,
                    GSConstNippou.MAX_LENGTH_NAIYO, false);

            model.setValueStr(nipDetail);
            /** NIP_MIKOMI mapping */
            String nipMikomi = NullDefault.getString(nipMikomi__, "0");
            if (!GSValidateUtil.isNumber(nipMikomi)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_MIKOMI);
                StrutsUtil.addMessage(errors, msg, "nipMikomi");
            }
            model.setMikomido(NullDefault.getInt(nipMikomi, 0));
            if (tempFile1__ != null) {
                ApiNippouEditBiz biz = new ApiNippouEditBiz(con, reqMdl);
                errors = biz.validateTempFile(tempFile1__.getFileName(), tempFile1__.getFileSize());
                if (!errors.isEmpty()) {
                    return errors;
                }
            }
            if (tempFile2__ != null) {
                ApiNippouEditBiz biz = new ApiNippouEditBiz(con, reqMdl);
                errors = biz.validateTempFile(tempFile2__.getFileName(), tempFile2__.getFileSize());
                if (!errors.isEmpty()) {
                    return errors;
                }
            }
            if (tempFile3__ != null) {
                ApiNippouEditBiz biz = new ApiNippouEditBiz(con, reqMdl);
                errors = biz.validateTempFile(tempFile3__.getFileName(), tempFile3__.getFileSize());
                if (!errors.isEmpty()) {
                    return errors;
                }
            }
            if (tempFile4__ != null) {
                ApiNippouEditBiz biz = new ApiNippouEditBiz(con, reqMdl);
                errors = biz.validateTempFile(tempFile4__.getFileName(), tempFile4__.getFileSize());
                if (!errors.isEmpty()) {
                    return errors;
                }
            }
            if (tempFile5__ != null) {
                ApiNippouEditBiz biz = new ApiNippouEditBiz(con, reqMdl);
                errors = biz.validateTempFile(tempFile5__.getFileName(), tempFile5__.getFileSize());
                if (!errors.isEmpty()) {
                    return errors;
                }
            }
            if (delBinSid__ == null || delBinSid__.length() == 0) {
                delBinSidArr__ = new String[0];
            } else {
                delBinSidArr__ = delBinSid__.split(",");
            }
            for (String binSid : delBinSidArr__) {
                if (StringUtil.isNullZeroString(binSid)) {
                    msg = new ActionMessage("error.input.required.text", "バイナリSID");
                    StrutsUtil.addMessage(errors, msg, "binSid");
                    return errors;
                }
                if (!GSValidateUtil.isNumber(binSid)) {
                    msg = new ActionMessage(
                            "error.input.number.hankaku", "バイナリSID");
                    StrutsUtil.addMessage(errors, msg, "binSid");
                    return errors;
                }
            }
            report__ = model;

            //編集権限チェック
            if (cmd__.equals("editNtp")) {
                ApiNippouEditBiz editBiz = new ApiNippouEditBiz(con, reqMdl);
                int ecode = editBiz.validateNippouAccsess(Integer.parseInt(nipSid));
                if (ecode == ApiNippouEditBiz.ECODE_NIPPOU_NONE) {
                    String textNippou = gsMsg.getMessage("ntp.1");
                    //閲覧
                    String edit = gsMsg.getMessage("cmn.edit");

                    msg = new ActionMessage(
                            "error.none.edit.data", textNippou, edit);
                    StrutsUtil.addMessage(errors, msg, "admFlg");
                    return errors;
                }
                if (ecode != 0) {
                    String edit = gsMsg.getMessage("cmn.edit");
                    msg = new ActionMessage(
                            "error.edit.power.user", edit, edit);
                    StrutsUtil.addMessage(errors, msg, "admFlg");
                    return errors;
                }
            }
        return errors;
    }
    /**
     * <p>nipSid を取得します。
     * @return nipSid
     */
    public String getNipSid() {
        return nipSid__;
    }
    /**
     * <p>nipSid をセットします。
     * @param nipSid nipSid
     */
    public void setNipSid(String nipSid) {
        nipSid__ = nipSid;
    }
    /**
     * <p>cmd を取得します。
     * @return cmd
     */
    public String getCmd() {
        return cmd__;
    }
    /**
     * <p>cmd をセットします。
     * @param cmd cmd
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>nipDate を取得します。
     * @return nipDate
     */
    public String getNipDate() {
        return nipDate__;
    }
    /**
     * <p>nipDate をセットします。
     * @param nipDate nipDate
     */
    public void setNipDate(String nipDate) {
        nipDate__ = nipDate;
    }
    /**
     * <p>nipFrTime を取得します。
     * @return nipFrTime
     */
    public String getNipFrTime() {
        return nipFrTime__;
    }
    /**
     * <p>nipFrTime をセットします。
     * @param nipFrTime nipFrTime
     */
    public void setNipFrTime(String nipFrTime) {
        nipFrTime__ = nipFrTime;
    }
    /**
     * <p>nipToTime を取得します。
     * @return nipToTime
     */
    public String getNipToTime() {
        return nipToTime__;
    }
    /**
     * <p>nipToTime をセットします。
     * @param nipToTime nipToTime
     */
    public void setNipToTime(String nipToTime) {
        nipToTime__ = nipToTime;
    }
    /**
     * <p>nanSid を取得します。
     * @return nanSid
     */
    public String getNanSid() {
        return nanSid__;
    }
    /**
     * <p>nanSid をセットします。
     * @param nanSid nanSid
     */
    public void setNanSid(String nanSid) {
        nanSid__ = nanSid;
    }
    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public String getAcoSid() {
        return acoSid__;
    }
    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(String acoSid) {
        acoSid__ = acoSid;
    }
    /**
     * <p>abaSid を取得します。
     * @return abaSid
     */
    public String getAbaSid() {
        return abaSid__;
    }
    /**
     * <p>abaSid をセットします。
     * @param abaSid abaSid
     */
    public void setAbaSid(String abaSid) {
        abaSid__ = abaSid;
    }
    /**
     * <p>nipTitle を取得します。
     * @return nipTitle
     */
    public String getNipTitle() {
        return nipTitle__;
    }
    /**
     * <p>nipTitle をセットします。
     * @param nipTitle nipTitle
     */
    public void setNipTitle(String nipTitle) {
        nipTitle__ = nipTitle;
    }
    /**
     * <p>nipTitleClo を取得します。
     * @return nipTitleClo
     */
    public String getNipTitleClo() {
        return nipTitleClo__;
    }
    /**
     * <p>nipTitleClo をセットします。
     * @param nipTitleClo nipTitleClo
     */
    public void setNipTitleClo(String nipTitleClo) {
        nipTitleClo__ = nipTitleClo;
    }
    /**
     * <p>mkbSid を取得します。
     * @return mkbSid
     */
    public String getMkbSid() {
        return mkbSid__;
    }
    /**
     * <p>mkbSid をセットします。
     * @param mkbSid mkbSid
     */
    public void setMkbSid(String mkbSid) {
        mkbSid__ = mkbSid;
    }
    /**
     * <p>mkhSid を取得します。
     * @return mkhSid
     */
    public String getMkhSid() {
        return mkhSid__;
    }
    /**
     * <p>mkhSid をセットします。
     * @param mkhSid mkhSid
     */
    public void setMkhSid(String mkhSid) {
        mkhSid__ = mkhSid;
    }
    /**
     * <p>nipDetail を取得します。
     * @return nipDetail
     */
    public String getNipDetail() {
        return nipDetail__;
    }
    /**
     * <p>nipDetail をセットします。
     * @param nipDetail nipDetail
     */
    public void setNipDetail(String nipDetail) {
        nipDetail__ = nipDetail;
    }
    /**
     * <p>nipMikomi を取得します。
     * @return nipMikomi
     */
    public String getNipMikomi() {
        return nipMikomi__;
    }
    /**
     * <p>nipMikomi をセットします。
     * @param nipMikomi nipMikomi
     */
    public void setNipMikomi(String nipMikomi) {
        nipMikomi__ = nipMikomi;
    }
    /**
     * <p>nipPublic を取得します。
     * @return nipPublic
     */
    public String getNipPublic() {
        return nipPublic__;
    }
    /**
     * <p>nipPublic をセットします。
     * @param nipPublic nipPublic
     */
    public void setNipPublic(String nipPublic) {
        nipPublic__ = nipPublic;
    }
    /**
     * <p>nipEdit を取得します。
     * @return nipEdit
     */
    public String getNipEdit() {
        return nipEdit__;
    }
    /**
     * <p>nipEdit をセットします。
     * @param nipEdit nipEdit
     */
    public void setNipEdit(String nipEdit) {
        nipEdit__ = nipEdit;
    }
    /**
     * <p>delBinSid を取得します。
     * @return delBinSid
     */
    public String getDelBinSid() {
        return delBinSid__;
    }
    /**
     * <p>delBinSid をセットします。
     * @param delBinSid delBinSid
     */
    public void setDelBinSid(String delBinSid) {
        delBinSid__ = delBinSid;
    }
    /**
     * <p>tempFile1 を取得します。
     * @return tempFile1
     */
    public FormFile getTempFile1() {
        return tempFile1__;
    }
    /**
     * <p>tempFile1 をセットします。
     * @param tempFile1 tempFile1
     */
    public void setTempFile1(FormFile tempFile1) {
        tempFile1__ = tempFile1;
    }
    /**
     * <p>tempFile2 を取得します。
     * @return tempFile2
     */
    public FormFile getTempFile2() {
        return tempFile2__;
    }
    /**
     * <p>tempFile2 をセットします。
     * @param tempFile2 tempFile2
     */
    public void setTempFile2(FormFile tempFile2) {
        tempFile2__ = tempFile2;
    }
    /**
     * <p>tempFile3 を取得します。
     * @return tempFile3
     */
    public FormFile getTempFile3() {
        return tempFile3__;
    }
    /**
     * <p>tempFile3 をセットします。
     * @param tempFile3 tempFile3
     */
    public void setTempFile3(FormFile tempFile3) {
        tempFile3__ = tempFile3;
    }
    /**
     * <p>tempFile4 を取得します。
     * @return tempFile4
     */
    public FormFile getTempFile4() {
        return tempFile4__;
    }
    /**
     * <p>tempFile4 をセットします。
     * @param tempFile4 tempFile4
     */
    public void setTempFile4(FormFile tempFile4) {
        tempFile4__ = tempFile4;
    }
    /**
     * <p>tempFile5 を取得します。
     * @return tempFile5
     */
    public FormFile getTempFile5() {
        return tempFile5__;
    }
    /**
     * <p>tempFile5 をセットします。
     * @param tempFile5 tempFile5
     */
    public void setTempFile5(FormFile tempFile5) {
        tempFile5__ = tempFile5;
    }
    /**
     * <p>delBinSidArr を取得します。
     * @return delBinSidArr
     */
    public String[] getDelBinSidArr() {
        return delBinSidArr__;
    }
}

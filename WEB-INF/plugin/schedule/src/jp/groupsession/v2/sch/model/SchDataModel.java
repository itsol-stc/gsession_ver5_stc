package jp.groupsession.v2.sch.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <p>SCH_DATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchDataModel extends AbstractModel implements ISchDataPushInf {

    /** SCD_SID mapping */
    private int scdSid__;
    /** SCD_USR_SID mapping */
    private int scdUsrSid__;
    /** SCD_GRP_SID mapping */
    private int scdGrpSid__;
    /** SCD_USR_KBN mapping */
    private int scdUsrKbn__;
    /** SCD_FR_DATE mapping */
    private UDate scdFrDate__;
    /** SCD_TO_DATE mapping */
    private UDate scdToDate__;
    /** SCD_DAILY mapping */
    private int scdDaily__;
    /** SCD_BGCOLOR mapping */
    private int scdBgcolor__;
    /** SCD_TITLE mapping */
    private String scdTitle__;
    /** SCD_VALUE mapping */
    private String scdValue__;
    /** SCD_BIKO mapping */
    private String scdBiko__;
    /** SCD_PUBLIC mapping */
    private int scdPublic__;
    /** SCD_AUID mapping */
    private int scdAuid__;
    /** SCD_ADATE mapping */
    private UDate scdAdate__;
    /** SCD_EUID mapping */
    private int scdEuid__;
    /** SCD_EDATE mapping */
    private UDate scdEdate__;
    /** SCE_SID mapping */
    private int sceSid__;
    /** SCD_EDIT mapping */
    private int scdEdit__;
    /** SCD_RSSID mapping */
    private int scdRsSid__;
    /** SCD_ATTEND_KBN mapping */
    private int scdAttendKbn__;
    /** SCD_ATTEND_ANS mapping */
    private int scdAttendAns__;
    /** SCD_ATTEND_AU_KBN mapping */
    private int scdAttendAuKbn__;
    /** SCD_ATTEND_COMMENT mapping */
    private String scdAttendComment__;
    /** SCD_TARGET_GRP mapping */
    private int scdTargetGrp__;
    /** SCD_REMINDER mapping */
    private int scdReminder__;

    /** スケジュールユーザグループSID格納LIST */
    private ArrayList<Integer> scdUserBlongGpList__;

    /** 被登録者名 月間で使用 */
    private String scdUserName__;
    /** 被登録者有効フラグ  月間で使用  */
    private int scdUserUkoFlg__;
    /** 他プラグインスId */
    private String scdAppendId__;
    /** 他プラグインスケジュール表示名 */
    private String scdAppendDspName__;
    /** 他プラグイン遷移先URL */
    private String scdAppendUrl__;


    /**
     * <p>scdAppendUrl を取得します。
     * @return scdAppendUrl
     */
    public String getScdAppendUrl() {
        return scdAppendUrl__;
    }

    /**
     * <p>scdAppendUrl をセットします。
     * @param scdAppendUrl scdAppendUrl
     */
    public void setScdAppendUrl(String scdAppendUrl) {
        scdAppendUrl__ = scdAppendUrl;
    }

    /**
     * <p>scdAppendId を取得します。
     * @return scdAppendId
     */
    public String getScdAppendId() {
        return scdAppendId__;
    }

    /**
     * <p>scdAppendId をセットします。
     * @param scdAppendId scdAppendId
     */
    public void setScdAppendId(String scdAppendId) {
        scdAppendId__ = scdAppendId;
    }

    /**
     * <p>scdUserName を取得します。
     * @return scdUserName
     */
    public String getScdUserName() {
        return scdUserName__;
    }

    /**
     * <p>scdUserName をセットします。
     * @param scdUserName scdUserName
     */
    public void setScdUserName(String scdUserName) {
        scdUserName__ = scdUserName;
    }

    /**
     * <p>scdRsSid を取得します。
     * @return scdRsSid
     */
    public int getScdRsSid() {
        return scdRsSid__;
    }

    /**
     * <p>scdRsSid をセットします。
     * @param scdRsSid scdRsSid
     */
    public void setScdRsSid(int scdRsSid) {
        scdRsSid__ = scdRsSid;
    }

    /**
     * <p>scdEdit を取得します。
     * @return scdEdit
     */
    public int getScdEdit() {
        return scdEdit__;
    }

    /**
     * <p>scdEdit をセットします。
     * @param scdEdit scdEdit
     */
    public void setScdEdit(int scdEdit) {
        scdEdit__ = scdEdit;
    }

    /**
     * <p>sceSid を取得します。
     * @return sceSid
     */
    public int getSceSid() {
        return sceSid__;
    }

    /**
     * <p>sceSid をセットします。
     * @param sceSid sceSid
     */
    public void setSceSid(int sceSid) {
        sceSid__ = sceSid;
    }

    /**
     * <p>Default Constructor
     */
    public SchDataModel() {
    }

    /**
     * <p>get SCD_SID value
     * @return SCD_SID value
     */
    public int getScdSid() {
        return scdSid__;
    }

    /**
     * <p>set SCD_SID value
     * @param scdSid SCD_SID value
     */
    public void setScdSid(int scdSid) {
        scdSid__ = scdSid;
    }

    /**
     * <p>get SCD_USR_SID value
     * @return SCD_USR_SID value
     */
    public int getScdUsrSid() {
        return scdUsrSid__;
    }

    /**
     * <p>set SCD_USR_SID value
     * @param scdUsrSid SCD_USR_SID value
     */
    public void setScdUsrSid(int scdUsrSid) {
        scdUsrSid__ = scdUsrSid;
    }

    /**
     * <p>get SCD_GRP_SID value
     * @return SCD_GRP_SID value
     */
    public int getScdGrpSid() {
        return scdGrpSid__;
    }

    /**
     * <p>set SCD_GRP_SID value
     * @param scdGrpSid SCD_GRP_SID value
     */
    public void setScdGrpSid(int scdGrpSid) {
        scdGrpSid__ = scdGrpSid;
    }

    /**
     * <p>get SCD_USR_KBN value
     * @return SCD_USR_KBN value
     */
    public int getScdUsrKbn() {
        return scdUsrKbn__;
    }

    /**
     * <p>set SCD_USR_KBN value
     * @param scdUsrKbn SCD_USR_KBN value
     */
    public void setScdUsrKbn(int scdUsrKbn) {
        scdUsrKbn__ = scdUsrKbn;
    }

    /**
     * <p>get SCD_FR_DATE value
     * @return SCD_FR_DATE value
     */
    public UDate getScdFrDate() {
        return scdFrDate__;
    }

    /**
     * <p>set SCD_FR_DATE value
     * @param scdFrDate SCD_FR_DATE value
     */
    public void setScdFrDate(UDate scdFrDate) {
        scdFrDate__ = scdFrDate;
    }

    /**
     * <p>get SCD_TO_DATE value
     * @return SCD_TO_DATE value
     */
    public UDate getScdToDate() {
        return scdToDate__;
    }

    /**
     * <p>set SCD_TO_DATE value
     * @param scdToDate SCD_TO_DATE value
     */
    public void setScdToDate(UDate scdToDate) {
        scdToDate__ = scdToDate;
    }

    /**
     * <p>get SCD_DAILY value
     * @return SCD_DAILY value
     */
    public int getScdDaily() {
        return scdDaily__;
    }

    /**
     * <p>set SCD_DAILY value
     * @param scdDaily SCD_DAILY value
     */
    public void setScdDaily(int scdDaily) {
        scdDaily__ = scdDaily;
    }

    /**
     * <p>get SCD_BGCOLOR value
     * @return SCD_BGCOLOR value
     */
    public int getScdBgcolor() {
        return scdBgcolor__;
    }

    /**
     * <p>set SCD_BGCOLOR value
     * @param scdBgcolor SCD_BGCOLOR value
     */
    public void setScdBgcolor(int scdBgcolor) {
        scdBgcolor__ = scdBgcolor;
    }

    /**
     * <p>get SCD_TITLE value
     * @return SCD_TITLE value
     */
    public String getScdTitle() {
        return scdTitle__;
    }

    /**
     * <p>set SCD_TITLE value
     * @param scdTitle SCD_TITLE value
     */
    public void setScdTitle(String scdTitle) {
        scdTitle__ = scdTitle;
    }

    /**
     * <p>get SCD_VALUE value
     * @return SCD_VALUE value
     */
    public String getScdValue() {
        return scdValue__;
    }

    /**
     * <p>set SCD_VALUE value
     * @param scdValue SCD_VALUE value
     */
    public void setScdValue(String scdValue) {
        scdValue__ = scdValue;
    }

    /**
     * <p>get SCD_BIKO value
     * @return SCD_BIKO value
     */
    public String getScdBiko() {
        return scdBiko__;
    }

    /**
     * <p>set SCD_BIKO value
     * @param scdBiko SCD_BIKO value
     */
    public void setScdBiko(String scdBiko) {
        scdBiko__ = scdBiko;
    }

    /**
     * <p>get SCD_PUBLIC value
     * @return SCD_PUBLIC value
     */
    public int getScdPublic() {
        return scdPublic__;
    }

    /**
     * <p>set SCD_PUBLIC value
     * @param scdPublic SCD_PUBLIC value
     */
    public void setScdPublic(int scdPublic) {
        scdPublic__ = scdPublic;
    }

    /**
     * <p>get SCD_AUID value
     * @return SCD_AUID value
     */
    public int getScdAuid() {
        return scdAuid__;
    }

    /**
     * <p>set SCD_AUID value
     * @param scdAuid SCD_AUID value
     */
    public void setScdAuid(int scdAuid) {
        scdAuid__ = scdAuid;
    }

    /**
     * <p>get SCD_ADATE value
     * @return SCD_ADATE value
     */
    public UDate getScdAdate() {
        return scdAdate__;
    }

    /**
     * <p>set SCD_ADATE value
     * @param scdAdate SCD_ADATE value
     */
    public void setScdAdate(UDate scdAdate) {
        scdAdate__ = scdAdate;
    }

    /**
     * <p>get SCD_EUID value
     * @return SCD_EUID value
     */
    public int getScdEuid() {
        return scdEuid__;
    }

    /**
     * <p>set SCD_EUID value
     * @param scdEuid SCD_EUID value
     */
    public void setScdEuid(int scdEuid) {
        scdEuid__ = scdEuid;
    }

    /**
     * <p>get SCD_EDATE value
     * @return SCD_EDATE value
     */
    public UDate getScdEdate() {
        return scdEdate__;
    }

    /**
     * <p>set SCD_EDATE value
     * @param scdEdate SCD_EDATE value
     */
    public void setScdEdate(UDate scdEdate) {
        scdEdate__ = scdEdate;
    }

    /**
     * <br>[機  能] SchDataModelのクローンを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @return SchDataModelのクローン
     * @throws IllegalAccessException クローンの作成に失敗
     * @throws InvocationTargetException クローンの作成に失敗
     */
    public SchDataModel cloneSchData() throws IllegalAccessException, InvocationTargetException {
        SchDataModel newData = new SchDataModel();
        BeanUtils.copyProperties(newData, this);
        return newData;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(scdSid__);
        buf.append(",");
        buf.append(scdUsrSid__);
        buf.append(",");
        buf.append(scdGrpSid__);
        buf.append(",");
        buf.append(scdUsrKbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(scdFrDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(scdToDate__, ""));
        buf.append(",");
        buf.append(scdDaily__);
        buf.append(",");
        buf.append(scdBgcolor__);
        buf.append(",");
        buf.append(NullDefault.getString(scdTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(scdValue__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(scdBiko__, ""));
        buf.append(",");
        buf.append(scdPublic__);
        buf.append(",");
        buf.append(scdAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(scdAdate__, ""));
        buf.append(",");
        buf.append(scdEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(scdEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>scdUserBlongGpList を取得します。
     * @return scdUserBlongGpList
     */
    public ArrayList<Integer> getScdUserBlongGpList() {
        return scdUserBlongGpList__;
    }

    /**
     * <p>scdUserBlongGpList をセットします。
     * @param scdUserBlongGpList scdUserBlongGpList
     */
    public void setScdUserBlongGpList(ArrayList<Integer> scdUserBlongGpList) {
        scdUserBlongGpList__ = scdUserBlongGpList;
    }

    /**
     * <p>scdAttendKbn を取得します。
     * @return scdAttendKbn
     */
    public int getScdAttendKbn() {
        return scdAttendKbn__;
    }

    /**
     * <p>scdAttendKbn をセットします。
     * @param scdAttendKbn scdAttendKbn
     */
    public void setScdAttendKbn(int scdAttendKbn) {
        scdAttendKbn__ = scdAttendKbn;
    }

    /**
     * <p>scdAttendAns を取得します。
     * @return scdAttendAns
     */
    public int getScdAttendAns() {
        return scdAttendAns__;
    }

    /**
     * <p>scdAttendAns をセットします。
     * @param scdAttendAns scdAttendAns
     */
    public void setScdAttendAns(int scdAttendAns) {
        scdAttendAns__ = scdAttendAns;
    }

    /**
     * <p>scdAttendAuKbn を取得します。
     * @return scdAttendAuKbn
     */
    public int getScdAttendAuKbn() {
        return scdAttendAuKbn__;
    }

    /**
     * <p>scdAttendAuKbn をセットします。
     * @param scdAttendAuKbn scdAttendAuKbn
     */
    public void setScdAttendAuKbn(int scdAttendAuKbn) {
        scdAttendAuKbn__ = scdAttendAuKbn;
    }

    /**
     * <p>scdAppendDspName を取得します。
     * @return scdAppendDspName
     */
    public String getScdAppendDspName() {
        return scdAppendDspName__;
    }

    /**
     * <p>scdAppendDspName をセットします。
     * @param scdAppendDspName scdAppendDspName
     */
    public void setScdAppendDspName(String scdAppendDspName) {
        scdAppendDspName__ = scdAppendDspName;
    }

    /**
     * <p>scdUserUkoFlg を取得します。
     * @return scdUserUkoFlg
     */
    public int getScdUserUkoFlg() {
        return scdUserUkoFlg__;
    }

    /**
     * <p>scdUserUkoFlg をセットします。
     * @param scdUserUkoFlg scdUserUkoFlg
     */
    public void setScdUserUkoFlg(int scdUserUkoFlg) {
        scdUserUkoFlg__ = scdUserUkoFlg;
    }

    /**
     * <p>scdAttendComment を取得します。
     * @return scdAttendComment
     * @see jp.groupsession.v2.sch.model.SchDataModel#scdAttendComment__
     */
    public String getScdAttendComment() {
        return scdAttendComment__;
    }

    /**
     * <p>scdAttendComment をセットします。
     * @param scdAttendComment scdAttendComment
     * @see jp.groupsession.v2.sch.model.SchDataModel#scdAttendComment__
     */
    public void setScdAttendComment(String scdAttendComment) {
        scdAttendComment__ = scdAttendComment;
    }

    /**
     * <p>scdTargetGrp を取得します。
     * @return scdTargetGrp
     * @see jp.groupsession.v2.sch.model.SchDataModel#scdTargetGrp__
     */
    public int getScdTargetGrp() {
        return scdTargetGrp__;
    }

    /**
     * <p>scdTargetGrp をセットします。
     * @param scdTargetGrp scdTargetGrp
     * @see jp.groupsession.v2.sch.model.SchDataModel#scdTargetGrp__
     */
    public void setScdTargetGrp(int scdTargetGrp) {
        scdTargetGrp__ = scdTargetGrp;
    }

    /**
     * <p>scdReminder を取得します。
     * @return scdReminder
     * @see jp.groupsession.v2.sch.model.SchDataModel#scdReminder__
     */
    public int getScdReminder() {
        return scdReminder__;
    }

    /**
     * <p>scdReminder をセットします。
     * @param scdReminder scdReminder
     * @see jp.groupsession.v2.sch.model.SchDataModel#scdReminder__
     */
    public void setScdReminder(int scdReminder) {
        scdReminder__ = scdReminder;
    }


}

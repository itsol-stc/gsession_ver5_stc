package jp.groupsession.v2.rsv.model.other;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;

import java.io.Serializable;

/**
 * <p>SCH_PRI_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSchPriConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** SCC_FR_DATE mapping */
    private UDate sccFrDate__;
    /** SCC_TO_DATE mapping */
    private UDate sccToDate__;
    /** SCC_DSP_GROUP mapping */
    private int sccDspGroup__;
    /** SCC_AUID mapping */
    private int sccAuid__;
    /** SCC_ADATE mapping */
    private UDate sccAdate__;
    /** SCC_EUID mapping */
    private int sccEuid__;
    /** SCC_EDATE mapping */
    private UDate sccEdate__;
    /** SCC_SORT_KEY1 mapping */
    private int sccSortKey1__;
    /** SCC_SORT_ORDER1 mapping */
    private int sccSortOrder1__;
    /** SCC_SORT_KEY2 mapping */
    private int sccSortKey2__;
    /** SCC_SORT_ORDER2 mapping */
    private int sccSortOrder2__;
    /** SCC_INI_FR_DATE mapping */
    private UDate sccIniFrDate__;
    /** SCC_INI_TO_DATE mapping */
    private UDate sccIniToDate__;
    /** SCC_INI_PUBLIC mapping */
    private int sccIniPublic__;
    /** SCC_DSP_LIST mapping */
    private int sccDspList__;
    /** SCC_INI_FCOLOR mapping */
    private int sccIniFcolor__;
    /** SCC_INI_EDIT mapping */
    private int sccIniEdit__;
    /** SCC_INI_EDIT mapping */
    private int sccDspMygroup__;
    /** SCC_SMAIL mapping */
    private int sccSmail__;
    /** SCC_MAIL_GROUP mapping */
    private int sccSmailGroup__;
    /** SCC_RELOAD mapping */
    private int sccReload__;
    /**SCC_INI_WEEK mapping */
    private int sccIniWeek__;
    /**SCC_SORT_EDIT mapping */
    private int sccSortEdit__;
    /**SCC_REPEAT_KBN mapping */
    private int sccRepeatKbn__;
    /**SCC_REPEAT_MY_KBN mapping */
    private int sccRepeatMyKbn__;
    /**SCC_DEF_DSP mapping */
    private int sccDefDsp__;

    /**
     * <p>sccDefDsp を取得します。
     * @return sccDefDsp
     */
    public int getSccDefDsp() {
        return sccDefDsp__;
    }

    /**
     * <p>sccDefDsp をセットします。
     * @param sccDefDsp sccDefDsp
     */
    public void setSccDefDsp(int sccDefDsp) {
        sccDefDsp__ = sccDefDsp;
    }

    /**
     * <p>sccSmail を取得します。
     * @return sccSmail
     */
    public int getSccSmail() {
        return sccSmail__;
    }

    /**
     * <p>sccSmail をセットします。
     * @param sccSmail sccSmail
     */
    public void setSccSmail(int sccSmail) {
        sccSmail__ = sccSmail;
    }

    /**
     * <p>sccSmailGroup を取得します。
     * @return sccSmailGroup
     */
    public int getSccSmailGroup() {
        return sccSmailGroup__;
    }

    /**
     * <p>sccSmailGroup をセットします。
     * @param sccSmailGroup sccSmailGroup
     */
    public void setSccSmailGroup(int sccSmailGroup) {
        sccSmailGroup__ = sccSmailGroup;
    }

    /**
     * <p>sccDspMygroup を取得します。
     * @return sccDspMygroup
     */
    public int getSccDspMygroup() {
        return sccDspMygroup__;
    }

    /**
     * <p>sccDspMygroup をセットします。
     * @param sccDspMygroup sccDspMygroup
     */
    public void setSccDspMygroup(int sccDspMygroup) {
        sccDspMygroup__ = sccDspMygroup;
    }

    /**
     * <p>sccIniEdit を取得します。
     * @return sccIniEdit
     */
    public int getSccIniEdit() {
        return sccIniEdit__;
    }

    /**
     * <p>sccIniEdit をセットします。
     * @param sccIniEdit sccIniEdit
     */
    public void setSccIniEdit(int sccIniEdit) {
        sccIniEdit__ = sccIniEdit;
    }

    /**
     * <p>sccIniFcolor を取得します。
     * @return sccIniFcolor
     */
    public int getSccIniFcolor() {
        return sccIniFcolor__;
    }

    /**
     * <p>sccIniFcolor をセットします。
     * @param sccIniFcolor sccIniFcolor
     */
    public void setSccIniFcolor(int sccIniFcolor) {
        sccIniFcolor__ = sccIniFcolor;
    }

    /**
     * <p>sccDspList を取得します。
     * @return sccDspList
     */
    public int getSccDspList() {
        return sccDspList__;
    }

    /**
     * <p>sccDspList をセットします。
     * @param sccDspList sccDspList
     */
    public void setSccDspList(int sccDspList) {
        sccDspList__ = sccDspList;
    }

    /**
     * <p>Default Constructor
     */
    public RsvSchPriConfModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get SCC_FR_DATE value
     * @return SCC_FR_DATE value
     */
    public UDate getSccFrDate() {
        return sccFrDate__;
    }

    /**
     * <p>set SCC_FR_DATE value
     * @param sccFrDate SCC_FR_DATE value
     */
    public void setSccFrDate(UDate sccFrDate) {
        sccFrDate__ = sccFrDate;
    }

    /**
     * <p>get SCC_TO_DATE value
     * @return SCC_TO_DATE value
     */
    public UDate getSccToDate() {
        return sccToDate__;
    }

    /**
     * <p>set SCC_TO_DATE value
     * @param sccToDate SCC_TO_DATE value
     */
    public void setSccToDate(UDate sccToDate) {
        sccToDate__ = sccToDate;
    }

    /**
     * <p>get SCC_DSP_GROUP value
     * @return SCC_DSP_GROUP value
     */
    public int getSccDspGroup() {
        return sccDspGroup__;
    }

    /**
     * <p>set SCC_DSP_GROUP value
     * @param sccDspGroup SCC_DSP_GROUP value
     */
    public void setSccDspGroup(int sccDspGroup) {
        sccDspGroup__ = sccDspGroup;
    }

    /**
     * <p>get SCC_AUID value
     * @return SCC_AUID value
     */
    public int getSccAuid() {
        return sccAuid__;
    }

    /**
     * <p>set SCC_AUID value
     * @param sccAuid SCC_AUID value
     */
    public void setSccAuid(int sccAuid) {
        sccAuid__ = sccAuid;
    }

    /**
     * <p>get SCC_ADATE value
     * @return SCC_ADATE value
     */
    public UDate getSccAdate() {
        return sccAdate__;
    }

    /**
     * <p>set SCC_ADATE value
     * @param sccAdate SCC_ADATE value
     */
    public void setSccAdate(UDate sccAdate) {
        sccAdate__ = sccAdate;
    }

    /**
     * <p>get SCC_EUID value
     * @return SCC_EUID value
     */
    public int getSccEuid() {
        return sccEuid__;
    }

    /**
     * <p>set SCC_EUID value
     * @param sccEuid SCC_EUID value
     */
    public void setSccEuid(int sccEuid) {
        sccEuid__ = sccEuid;
    }

    /**
     * <p>get SCC_EDATE value
     * @return SCC_EDATE value
     */
    public UDate getSccEdate() {
        return sccEdate__;
    }

    /**
     * <p>set SCC_EDATE value
     * @param sccEdate SCC_EDATE value
     */
    public void setSccEdate(UDate sccEdate) {
        sccEdate__ = sccEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sccFrDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sccToDate__, ""));
        buf.append(",");
        buf.append(sccDspGroup__);
        buf.append(",");
        buf.append(sccSortKey1__);
        buf.append(",");
        buf.append(sccSortOrder1__);
        buf.append(",");
        buf.append(sccSortKey2__);
        buf.append(",");
        buf.append(sccSortOrder2__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sccIniFrDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sccIniToDate__, ""));
        buf.append(",");
        buf.append(sccIniPublic__);
        buf.append(",");
        buf.append(sccAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sccAdate__, ""));
        buf.append(",");
        buf.append(sccEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sccEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>sccIniFrDate を取得します。
     * @return sccIniFrDate
     */
    public UDate getSccIniFrDate() {
        return sccIniFrDate__;
    }

    /**
     * <p>sccIniFrDate をセットします。
     * @param sccIniFrDate sccIniFrDate
     */
    public void setSccIniFrDate(UDate sccIniFrDate) {
        sccIniFrDate__ = sccIniFrDate;
    }

    /**
     * <p>sccIniPublic を取得します。
     * @return sccIniPublic
     */
    public int getSccIniPublic() {
        return sccIniPublic__;
    }

    /**
     * <p>sccIniPublic をセットします。
     * @param sccIniPublic sccIniPublic
     */
    public void setSccIniPublic(int sccIniPublic) {
        sccIniPublic__ = sccIniPublic;
    }

    /**
     * <p>sccIniToDate を取得します。
     * @return sccIniToDate
     */
    public UDate getSccIniToDate() {
        return sccIniToDate__;
    }

    /**
     * <p>sccIniToDate をセットします。
     * @param sccIniToDate sccIniToDate
     */
    public void setSccIniToDate(UDate sccIniToDate) {
        sccIniToDate__ = sccIniToDate;
    }

    /**
     * <p>sccSortKey1 を取得します。
     * @return sccSortKey1
     */
    public int getSccSortKey1() {
        return sccSortKey1__;
    }

    /**
     * <p>sccSortKey1 をセットします。
     * @param sccSortKey1 sccSortKey1
     */
    public void setSccSortKey1(int sccSortKey1) {
        sccSortKey1__ = sccSortKey1;
    }

    /**
     * <p>sccSortKey2 を取得します。
     * @return sccSortKey2
     */
    public int getSccSortKey2() {
        return sccSortKey2__;
    }

    /**
     * <p>sccSortKey2 をセットします。
     * @param sccSortKey2 sccSortKey2
     */
    public void setSccSortKey2(int sccSortKey2) {
        sccSortKey2__ = sccSortKey2;
    }

    /**
     * <p>sccSortOrder1 を取得します。
     * @return sccSortOrder1
     */
    public int getSccSortOrder1() {
        return sccSortOrder1__;
    }

    /**
     * <p>sccSortOrder1 をセットします。
     * @param sccSortOrder1 sccSortOrder1
     */
    public void setSccSortOrder1(int sccSortOrder1) {
        sccSortOrder1__ = sccSortOrder1;
    }

    /**
     * <p>sccSortOrder2 を取得します。
     * @return sccSortOrder2
     */
    public int getSccSortOrder2() {
        return sccSortOrder2__;
    }

    /**
     * <p>sccSortOrder2 をセットします。
     * @param sccSortOrder2 sccSortOrder2
     */
    public void setSccSortOrder2(int sccSortOrder2) {
        sccSortOrder2__ = sccSortOrder2;
    }

    /**
     * <p>sccReload を取得します。
     * @return sccReload
     */
    public int getSccReload() {
        return sccReload__;
    }

    /**
     * <p>sccReload をセットします。
     * @param sccReload sccReload
     */
    public void setSccReload(int sccReload) {
        sccReload__ = sccReload;
    }

    /**
     * @return sccIniWeek
     */
    public int getSccIniWeek() {
        return sccIniWeek__;
    }

    /**
     * @param sccIniWeek 設定する sccIniWeek
     */
    public void setSccIniWeek(int sccIniWeek) {
        sccIniWeek__ = sccIniWeek;
    }

    /**
     * @return sccSortEdit
     */
    public int getSccSortEdit() {
        return sccSortEdit__;
    }

    /**
     * @param sccSortEdit 設定する sccSortEdit
     */
    public void setSccSortEdit(int sccSortEdit) {
        sccSortEdit__ = sccSortEdit;
    }

    /**
     * <p>sccRepeatKbn を取得します。
     * @return sccRepeatKbn
     */
    public int getSccRepeatKbn() {
        return sccRepeatKbn__;
    }

    /**
     * <p>sccRepeatKbn をセットします。
     * @param sccRepeatKbn sccRepeatKbn
     */
    public void setSccRepeatKbn(int sccRepeatKbn) {
        sccRepeatKbn__ = sccRepeatKbn;
    }

    /**
     * <p>sccRepeatMyKbn を取得します。
     * @return sccRepeatMyKbn
     */
    public int getSccRepeatMyKbn() {
        return sccRepeatMyKbn__;
    }

    /**
     * <p>sccRepeatMyKbn をセットします。
     * @param sccRepeatMyKbn sccRepeatMyKbn
     */
    public void setSccRepeatMyKbn(int sccRepeatMyKbn) {
        sccRepeatMyKbn__ = sccRepeatMyKbn;
    }
}

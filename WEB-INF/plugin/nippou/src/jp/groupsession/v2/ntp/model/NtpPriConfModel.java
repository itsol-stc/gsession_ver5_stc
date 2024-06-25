package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_PRI_CONFI Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpPriConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** NPR_DSP_GROUP mapping */
    private int nprDspGroup__;
    /** NPR_SORT_KEY1 mapping */
    private int nprSortKey1__;
    /** NPR_SORT_ORDER1 mapping */
    private int nprSortOrder1__;
    /** NPR_SORT_KEY2 mapping */
    private int nprSortKey2__;
    /** NPR_SORT_ORDER2 mapping */
    private int nprSortOrder2__;
    /** NPR_INI_FR_DATE mapping */
    private UDate nprIniFrDate__;
    /** NPR_INI_TO_DATE mapping */
    private UDate nprIniToDate__;
    /** NPR_INI_FCOLOR mapping */
    private int nprIniFcolor__;
    /** NPR_DSP_LIST mapping */
    private int nprDspList__;
    /** NPR_AUTO_RELOAD mapping */
    private int nprAutoReload__;
    /** NPR_DSP_MYGROUP mapping */
    private int nprDspMygroup__;
    /** NPR_SMAIL mapping */
    private int nprSmail__;
    /** NPR_CMT_SMAIL mapping */
    private int nprCmtSmail__;
    /** NPR_GOOD_SMAIL mapping */
    private int nprGoodSmail__;
    /** NPR_DSP_POSITION mapping */
    private int nprDspPosition__;
    /** NPR_SCH_KBN mapping */
    private int nprSchKbn__;
    /** NPR_AUID mapping */
    private int nprAuid__;
    /** NPR_ADATE mapping */
    private UDate nprAdate__;
    /** NPR_EUID mapping */
    private int nprEuid__;
    /** NPR_EDATE mapping */
    private UDate nprEdate__;


    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(nprDspGroup__);
        buf.append(",");
        buf.append(nprSortKey1__);
        buf.append(",");
        buf.append(nprSortOrder1__);
        buf.append(",");
        buf.append(nprSortKey2__);
        buf.append(",");
        buf.append(nprSortOrder2__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nprIniFrDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nprIniToDate__, ""));
        buf.append(",");
        buf.append(nprIniFcolor__);
        buf.append(",");
        buf.append(nprDspList__);
        buf.append(",");
        buf.append(nprAutoReload__);
        buf.append(",");
        buf.append(nprDspMygroup__);
        buf.append(",");
        buf.append(nprSmail__);
        buf.append(",");
        buf.append(nprAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nprAdate__, ""));
        buf.append(",");
        buf.append(nprEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nprEdate__, ""));
        return buf.toString();
    }


    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }


    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }


    /**
     * <p>nprDspGroup を取得します。
     * @return nprDspGroup
     */
    public int getNprDspGroup() {
        return nprDspGroup__;
    }


    /**
     * <p>nprDspGroup をセットします。
     * @param nprDspGroup nprDspGroup
     */
    public void setNprDspGroup(int nprDspGroup) {
        nprDspGroup__ = nprDspGroup;
    }


    /**
     * <p>nprSortKey1 を取得します。
     * @return nprSortKey1
     */
    public int getNprSortKey1() {
        return nprSortKey1__;
    }


    /**
     * <p>nprSortKey1 をセットします。
     * @param nprSortKey1 nprSortKey1
     */
    public void setNprSortKey1(int nprSortKey1) {
        nprSortKey1__ = nprSortKey1;
    }


    /**
     * <p>nprSortOrder1 を取得します。
     * @return nprSortOrder1
     */
    public int getNprSortOrder1() {
        return nprSortOrder1__;
    }


    /**
     * <p>nprSortOrder1 をセットします。
     * @param nprSortOrder1 nprSortOrder1
     */
    public void setNprSortOrder1(int nprSortOrder1) {
        nprSortOrder1__ = nprSortOrder1;
    }


    /**
     * <p>nprSortKey2 を取得します。
     * @return nprSortKey2
     */
    public int getNprSortKey2() {
        return nprSortKey2__;
    }


    /**
     * <p>nprSortKey2 をセットします。
     * @param nprSortKey2 nprSortKey2
     */
    public void setNprSortKey2(int nprSortKey2) {
        nprSortKey2__ = nprSortKey2;
    }


    /**
     * <p>nprSortOrder2 を取得します。
     * @return nprSortOrder2
     */
    public int getNprSortOrder2() {
        return nprSortOrder2__;
    }


    /**
     * <p>nprSortOrder2 をセットします。
     * @param nprSortOrder2 nprSortOrder2
     */
    public void setNprSortOrder2(int nprSortOrder2) {
        nprSortOrder2__ = nprSortOrder2;
    }


    /**
     * <p>nprIniFrDate を取得します。
     * @return nprIniFrDate
     */
    public UDate getNprIniFrDate() {
        return nprIniFrDate__;
    }


    /**
     * <p>nprIniFrDate をセットします。
     * @param nprIniFrDate nprIniFrDate
     */
    public void setNprIniFrDate(UDate nprIniFrDate) {
        nprIniFrDate__ = nprIniFrDate;
    }


    /**
     * <p>nprIniToDate を取得します。
     * @return nprIniToDate
     */
    public UDate getNprIniToDate() {
        return nprIniToDate__;
    }


    /**
     * <p>nprIniToDate をセットします。
     * @param nprIniToDate nprIniToDate
     */
    public void setNprIniToDate(UDate nprIniToDate) {
        nprIniToDate__ = nprIniToDate;
    }


    /**
     * <p>nprIniFcolor を取得します。
     * @return nprIniFcolor
     */
    public int getNprIniFcolor() {
        return nprIniFcolor__;
    }


    /**
     * <p>nprIniFcolor をセットします。
     * @param nprIniFcolor nprIniFcolor
     */
    public void setNprIniFcolor(int nprIniFcolor) {
        nprIniFcolor__ = nprIniFcolor;
    }


    /**
     * <p>nprDspList を取得します。
     * @return nprDspList
     */
    public int getNprDspList() {
        return nprDspList__;
    }


    /**
     * <p>nprDspList をセットします。
     * @param nprDspList nprDspList
     */
    public void setNprDspList(int nprDspList) {
        nprDspList__ = nprDspList;
    }


    /**
     * <p>nprAutoReload を取得します。
     * @return nprAutoReload
     */
    public int getNprAutoReload() {
        return nprAutoReload__;
    }


    /**
     * <p>nprAutoReload をセットします。
     * @param nprAutoReload nprAutoReload
     */
    public void setNprAutoReload(int nprAutoReload) {
        nprAutoReload__ = nprAutoReload;
    }


    /**
     * <p>nprSmail を取得します。
     * @return nprSmail
     */
    public int getNprSmail() {
        return nprSmail__;
    }


    /**
     * <p>nprSmail をセットします。
     * @param nprSmail nprSmail
     */
    public void setNprSmail(int nprSmail) {
        nprSmail__ = nprSmail;
    }


    /**
     * <p>nprAuid を取得します。
     * @return nprAuid
     */
    public int getNprAuid() {
        return nprAuid__;
    }


    /**
     * <p>nprAuid をセットします。
     * @param nprAuid nprAuid
     */
    public void setNprAuid(int nprAuid) {
        nprAuid__ = nprAuid;
    }


    /**
     * <p>nprAdate を取得します。
     * @return nprAdate
     */
    public UDate getNprAdate() {
        return nprAdate__;
    }


    /**
     * <p>nprAdate をセットします。
     * @param nprAdate nprAdate
     */
    public void setNprAdate(UDate nprAdate) {
        nprAdate__ = nprAdate;
    }


    /**
     * <p>nprEuid を取得します。
     * @return nprEuid
     */
    public int getNprEuid() {
        return nprEuid__;
    }


    /**
     * <p>nprEuid をセットします。
     * @param nprEuid nprEuid
     */
    public void setNprEuid(int nprEuid) {
        nprEuid__ = nprEuid;
    }


    /**
     * <p>nprEdate を取得します。
     * @return nprEdate
     */
    public UDate getNprEdate() {
        return nprEdate__;
    }


    /**
     * <p>nprEdate をセットします。
     * @param nprEdate nprEdate
     */
    public void setNprEdate(UDate nprEdate) {
        nprEdate__ = nprEdate;
    }


    /**
     * <p>nprDspMygroup を取得します。
     * @return nprDspMygroup
     */
    public int getNprDspMygroup() {
        return nprDspMygroup__;
    }


    /**
     * <p>nprDspMygroup をセットします。
     * @param nprDspMygroup nprDspMygroup
     */
    public void setNprDspMygroup(int nprDspMygroup) {
        nprDspMygroup__ = nprDspMygroup;
    }


    /**
     * <p>nprDspPosition を取得します。
     * @return nprDspPosition
     */
    public int getNprDspPosition() {
        return nprDspPosition__;
    }


    /**
     * <p>nprDspPosition をセットします。
     * @param nprDspPosition nprDspPosition
     */
    public void setNprDspPosition(int nprDspPosition) {
        nprDspPosition__ = nprDspPosition;
    }


    /**
     * <p>nprCmtSmail を取得します。
     * @return nprCmtSmail
     */
    public int getNprCmtSmail() {
        return nprCmtSmail__;
    }


    /**
     * <p>nprCmtSmail をセットします。
     * @param nprCmtSmail nprCmtSmail
     */
    public void setNprCmtSmail(int nprCmtSmail) {
        nprCmtSmail__ = nprCmtSmail;
    }


    /**
     * <p>nprGoodSmail を取得します。
     * @return nprGoodSmail
     */
    public int getNprGoodSmail() {
        return nprGoodSmail__;
    }


    /**
     * <p>nprGoodSmail をセットします。
     * @param nprGoodSmail nprGoodSmail
     */
    public void setNprGoodSmail(int nprGoodSmail) {
        nprGoodSmail__ = nprGoodSmail;
    }


    /**
     * <p>nprSchKbn を取得します。
     * @return nprSchKbn
     */
    public int getNprSchKbn() {
        return nprSchKbn__;
    }


    /**
     * <p>nprSchKbn をセットします。
     * @param nprSchKbn nprSchKbn
     */
    public void setNprSchKbn(int nprSchKbn) {
        nprSchKbn__ = nprSchKbn;
    }

}

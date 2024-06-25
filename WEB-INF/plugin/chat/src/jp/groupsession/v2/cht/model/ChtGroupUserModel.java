package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_GROUP_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupUserModel implements Serializable {

    /** CGI_SID mapping */
    private int cgiSid__;
    /** CGU_KBN mapping */
    private int cguKbn__;
    /** CGU_SELECT_SID mapping */
    private int cguSelectSid__;
    /** CGU_ADMIN_FLG mapping */
    private int cguAdminFlg__;
    /** CGU_AUID mapping */
    private int cguAuid__;
    /** CGU_ADATE mapping */
    private UDate cguAdate__;
    /** CGU_EUID mapping */
    private int cguEuid__;
    /** CGU_EDATE mapping */
    private UDate cguEdate__;

    /**
     * <p>Default Constructor
     */
    public ChtGroupUserModel() {
    }

    /**
     * <p>get CGI_SID value
     * @return CGI_SID value
     */
    public int getCgiSid() {
        return cgiSid__;
    }

    /**
     * <p>set CGI_SID value
     * @param cgiSid CGI_SID value
     */
    public void setCgiSid(int cgiSid) {
        cgiSid__ = cgiSid;
    }


    /**
     * <p>get CGU_ADMIN_FLG value
     * @return CGU_ADMIN_FLG value
     */
    public int getCguAdminFlg() {
        return cguAdminFlg__;
    }

    /**
     * <p>set CGU_ADMIN_FLG value
     * @param cguAdminFlg CGU_ADMIN_FLG value
     */
    public void setCguAdminFlg(int cguAdminFlg) {
        cguAdminFlg__ = cguAdminFlg;
    }

    /**
     * <p>get CGU_AUID value
     * @return CGU_AUID value
     */
    public int getCguAuid() {
        return cguAuid__;
    }

    /**
     * <p>set CGU_AUID value
     * @param cguAuid CGU_AUID value
     */
    public void setCguAuid(int cguAuid) {
        cguAuid__ = cguAuid;
    }

    /**
     * <p>get CGU_ADATE value
     * @return CGU_ADATE value
     */
    public UDate getCguAdate() {
        return cguAdate__;
    }

    /**
     * <p>set CGU_ADATE value
     * @param cguAdate CGU_ADATE value
     */
    public void setCguAdate(UDate cguAdate) {
        cguAdate__ = cguAdate;
    }

    /**
     * <p>get CGU_EUID value
     * @return CGU_EUID value
     */
    public int getCguEuid() {
        return cguEuid__;
    }

    /**
     * <p>set CGU_EUID value
     * @param cguEuid CGU_EUID value
     */
    public void setCguEuid(int cguEuid) {
        cguEuid__ = cguEuid;
    }

    /**
     * <p>get CGU_EDATE value
     * @return CGU_EDATE value
     */
    public UDate getCguEdate() {
        return cguEdate__;
    }

    /**
     * <p>set CGU_EDATE value
     * @param cguEdate CGU_EDATE value
     */
    public void setCguEdate(UDate cguEdate) {
        cguEdate__ = cguEdate;
    }

    /**
     * <p>cguKbn を取得します。
     * @return cguKbn
     * @see jp.groupsession.v2.cht.model.ChtGroupUserModel#cguKbn__
     */
    public int getCguKbn() {
        return cguKbn__;
    }

    /**
     * <p>cguKbn をセットします。
     * @param cguKbn cguKbn
     * @see jp.groupsession.v2.cht.model.ChtGroupUserModel#cguKbn__
     */
    public void setCguKbn(int cguKbn) {
        cguKbn__ = cguKbn;
    }

    /**
     * <p>cguSelectSid を取得します。
     * @return cguSelectSid
     * @see jp.groupsession.v2.cht.model.ChtGroupUserModel#cguSelectSid__
     */
    public int getCguSelectSid() {
        return cguSelectSid__;
    }

    /**
     * <p>cguSelectSid をセットします。
     * @param cguSelectSid cguSelectSid
     * @see jp.groupsession.v2.cht.model.ChtGroupUserModel#cguSelectSid__
     */
    public void setCguSelectSid(int cguSelectSid) {
        cguSelectSid__ = cguSelectSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cgiSid__);
        buf.append(",");
        buf.append(cguKbn__);
        buf.append(",");
        buf.append(cguSelectSid__);
        buf.append(",");
        buf.append(cguAdminFlg__);
        buf.append(",");
        buf.append(cguAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cguAdate__, ""));
        buf.append(",");
        buf.append(cguEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cguEdate__, ""));
        return buf.toString();
    }

}

package jp.groupsession.v2.cir.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CIR_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirInfModel implements Serializable {

    /** CIF_SID mapping */
    private int cifSid__;
    /** CIF_TITLE mapping */
    private String cifTitle__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** CIF_VALUE mapping */
    private String cifValue__;
    /** CIF_AUID mapping */
    private int cifAuid__;
    /** CIF_ADATE mapping */
    private UDate cifAdate__;
    /** CIF_EUID mapping */
    private int cifEuid__;
    /** CIF_EDATE mapping */
    private UDate cifEdate__;
    /** CIF_JKBN mapping */
    private int cifEkbn__;
    /** CIF_JKBN mapping */
    private int cifJkbn__;
    /** CIF_SHOW mapping */
    private int cifShow__;
    /** CIF_MEMO_FLG mapping */
    private int cifMemoFlg__;
    /** CIF_MEMO_DATE mapping */
    private UDate cifMemoDate__;
    /** CIF_EDIT_DATE mapping */
    private UDate cifEditDate__;


    /**
     * <p>Default Constructor
     */
    public CirInfModel() {
    }

    /**
     * <p>get CIF_SID value
     * @return CIF_SID value
     */
    public int getCifSid() {
        return cifSid__;
    }

    /**
     * <p>set CIF_SID value
     * @param cifSid CIF_SID value
     */
    public void setCifSid(int cifSid) {
        cifSid__ = cifSid;
    }

    /**
     * <p>get CIF_TITLE value
     * @return CIF_TITLE value
     */
    public String getCifTitle() {
        return cifTitle__;
    }

    /**
     * <p>set CIF_TITLE value
     * @param cifTitle CIF_TITLE value
     */
    public void setCifTitle(String cifTitle) {
        cifTitle__ = cifTitle;
    }

    /**
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get CIF_VALUE value
     * @return CIF_VALUE value
     */
    public String getCifValue() {
        return cifValue__;
    }

    /**
     * <p>set CIF_VALUE value
     * @param cifValue CIF_VALUE value
     */
    public void setCifValue(String cifValue) {
        cifValue__ = cifValue;
    }

    /**
     * <p>get CIF_AUID value
     * @return CIF_AUID value
     */
    public int getCifAuid() {
        return cifAuid__;
    }

    /**
     * <p>set CIF_AUID value
     * @param cifAuid CIF_AUID value
     */
    public void setCifAuid(int cifAuid) {
        cifAuid__ = cifAuid;
    }

    /**
     * <p>get CIF_ADATE value
     * @return CIF_ADATE value
     */
    public UDate getCifAdate() {
        return cifAdate__;
    }

    /**
     * <p>set CIF_ADATE value
     * @param cifAdate CIF_ADATE value
     */
    public void setCifAdate(UDate cifAdate) {
        cifAdate__ = cifAdate;
    }

    /**
     * <p>get CIF_EUID value
     * @return CIF_EUID value
     */
    public int getCifEuid() {
        return cifEuid__;
    }

    /**
     * <p>set CIF_EUID value
     * @param cifEuid CIF_EUID value
     */
    public void setCifEuid(int cifEuid) {
        cifEuid__ = cifEuid;
    }

    /**
     * <p>get CIF_EDATE value
     * @return CIF_EDATE value
     */
    public UDate getCifEdate() {
        return cifEdate__;
    }

    /**
     * <p>set CIF_EDATE value
     * @param cifEdate CIF_EDATE value
     */
    public void setCifEdate(UDate cifEdate) {
        cifEdate__ = cifEdate;
    }

    /**
     * <p>cifJkbn を取得します。
     * @return cifJkbn
     */
    public int getCifJkbn() {
        return cifJkbn__;
    }

    /**
     * <p>cifJkbn をセットします。
     * @param cifJkbn cifJkbn
     */
    public void setCifJkbn(int cifJkbn) {
        cifJkbn__ = cifJkbn;
    }

    /**
     * <p>cifShow を取得します。
     * @return cifShow
     */
    public int getCifShow() {
        return cifShow__;
    }

    /**
     * <p>cifShow をセットします。
     * @param cifShow cifShow
     */
    public void setCifShow(int cifShow) {
        cifShow__ = cifShow;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(cifSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cifTitle__, ""));
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cifValue__, ""));
        buf.append(",");
        buf.append(cifAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cifAdate__, ""));
        buf.append(",");
        buf.append(cifEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cifEdate__, ""));
        return buf.toString();
    }

    /**
     * @return cifMemoFlg を取得します。
     */
    public int getCifMemoFlg() {
        return cifMemoFlg__;
    }

    /**
     * @param cifMemoFlg をセットします。
     */
    public void setCifMemoFlg(int cifMemoFlg) {
        cifMemoFlg__ = cifMemoFlg;
    }

    /**
     * @return cifMEmoDate を取得します。
     */
    public UDate getCifMemoDate() {
        return cifMemoDate__;
    }

    /**
     * @param cifMemoDate をセットします。
     */
    public void setCifMemoDate(UDate cifMemoDate) {
        cifMemoDate__ = cifMemoDate;
    }

    /**
     * <p>cifEkbn を取得します。
     * @return cifEkbn
     */
    public int getCifEkbn() {
        return cifEkbn__;
    }

    /**
     * <p>cifEkbn をセットします。
     * @param cifEkbn cifEkbn
     */
    public void setCifEkbn(int cifEkbn) {
        cifEkbn__ = cifEkbn;
    }

    /**
     * <p>cifEditDate を取得します。
     * @return cifEditDate
     */
    public UDate getCifEditDate() {
        return cifEditDate__;
    }

    /**
     * <p>cifEditDate をセットします。
     * @param cifEditDate cifEditDate
     */
    public void setCifEditDate(UDate cifEditDate) {
        cifEditDate__ = cifEditDate;
    }

}

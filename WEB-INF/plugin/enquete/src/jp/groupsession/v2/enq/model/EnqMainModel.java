package jp.groupsession.v2.enq.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_MAIN Data Binding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqMainModel implements Serializable {

    /** EMN_SID mapping */
    private long emnSid__;
    /** EMN_DATA_KBN mapping */
    private int emnDataKbn__;
    /** ETP_SID mapping */
    private int etpSid__;
    /** EMN_TITLE mapping */
    private String emnTitle__;
    /** EMN_PRI_KBN mapping */
    private int emnPriKbn__;
    /** EMN_DESC mapping */
    private String emnDesc__;
    /** EMN_DESC_PLAIN mapping */
    private String emnDescPlain__;
    /** EMN_ATTACH_KBN mapping */
    private int emnAttachKbn__;
    /** EMN_ATTACH_ID mapping */
    private String emnAttachId__;
    /** EMN_ATTACH_NAME mapping */
    private String emnAttachName__;
    /** EMN_ATTACH_POS mapping */
    private int emnAttachPos__;
    /** EMN_OPEN_STR mapping */
    private UDate emnOpenStr__;
    /** EMN_OPEN_END mapping */
    private UDate emnOpenEnd__;
    /** EMN_OPEN_END_KBN mapping */
    private int emnOpenEndKbn__;
    /** EMN_RES_END mapping */
    private UDate emnResEnd__;
    /** EMN_ANS_PUB_STR mapping */
    private UDate emnAnsPubStr__;
    /** EMN_ANONY mapping */
    private int emnAnony__;
    /** EMN_ANS_OPEN mapping */
    private int emnAnsOpen__;
    /** EMN_SEND_GRP mapping */
    private long emnSendGrp__;
    /** EMN_SEND_USR mapping */
    private long emnSendUsr__;
    /** EMN_SEND_NAME mapping */
    private String emnSendName__;
    /** EMN_TARGET mapping */
    private int emnTarget__;
    /** EMN_QUESEC_TYPE mapping */
    private int emnQuesecType__;
    /** EMN_AUID mapping */
    private int emnAuid__;
    /** EMN_ADATE mapping */
    private UDate emnAdate__;
    /** EMN_EUID mapping */
    private int emnEuid__;
    /** EMN_EDATE mapping */
    private UDate emnEdate__;

    /**
     * <p>Default Constructor
     */
    public EnqMainModel() {
    }

    /**
     * <p>get EMN_SID value
     * @return EMN_SID value
     */
    public long getEmnSid() {
        return emnSid__;
    }

    /**
     * <p>set EMN_SID value
     * @param emnSid EMN_SID value
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
    }

    /**
     * <p>get EMN_DATA_KBN value
     * @return EMN_DATA_KBN value
     */
    public int getEmnDataKbn() {
        return emnDataKbn__;
    }

    /**
     * <p>set EMN_DATA_KBN value
     * @param emnDataKbn EMN_DATA_KBN value
     */
    public void setEmnDataKbn(int emnDataKbn) {
        emnDataKbn__ = emnDataKbn;
    }

    /**
     * <p>get ETP_SID value
     * @return ETP_SID value
     */
    public int getEtpSid() {
        return etpSid__;
    }

    /**
     * <p>set ETP_SID value
     * @param etpSid ETP_SID value
     */
    public void setEtpSid(int etpSid) {
        etpSid__ = etpSid;
    }

    /**
     * <p>get EMN_TITLE value
     * @return EMN_TITLE value
     */
    public String getEmnTitle() {
        return emnTitle__;
    }

    /**
     * <p>set EMN_TITLE value
     * @param emnTitle EMN_TITLE value
     */
    public void setEmnTitle(String emnTitle) {
        emnTitle__ = emnTitle;
    }

    /**
     * <p>get EMN_PRI_KBN value
     * @return EMN_PRI_KBN value
     */
    public int getEmnPriKbn() {
        return emnPriKbn__;
    }

    /**
     * <p>set EMN_PRI_KBN value
     * @param emnPriKbn EMN_PRI_KBN value
     */
    public void setEmnPriKbn(int emnPriKbn) {
        emnPriKbn__ = emnPriKbn;
    }

    /**
     * <p>get EMN_DESC value
     * @return EMN_DESC value
     */
    public String getEmnDesc() {
        return emnDesc__;
    }

    /**
     * <p>set EMN_DESC value
     * @param emnDesc EMN_DESC value
     */
    public void setEmnDesc(String emnDesc) {
        emnDesc__ = emnDesc;
    }

    /**
     * <p>get EMN_DESC_PLAIN value
     * @return EMN_DESC_PLAIN value
     */
    public String getEmnDescPlain() {
        return emnDescPlain__;
    }

    /**
     * <p>set EMN_DESC_PLAIN value
     * @param emnDescPlain EMN_DESC_PLAIN value
     */
    public void setEmnDescPlain(String emnDescPlain) {
        emnDescPlain__ = emnDescPlain;
    }

    /**
     * <p>get EMN_ATTACH_KBN value
     * @return EMN_ATTACH_KBN value
     */
    public int getEmnAttachKbn() {
        return emnAttachKbn__;
    }

    /**
     * <p>set EMN_ATTACH_KBN value
     * @param emnAttachKbn EMN_ATTACH_KBN value
     */
    public void setEmnAttachKbn(int emnAttachKbn) {
        emnAttachKbn__ = emnAttachKbn;
    }

    /**
     * <p>get EMN_ATTACH_ID value
     * @return EMN_ATTACH_ID value
     */
    public String getEmnAttachId() {
        return emnAttachId__;
    }

    /**
     * <p>set EMN_ATTACH_ID value
     * @param emnAttachId EMN_ATTACH_ID value
     */
    public void setEmnAttachId(String emnAttachId) {
        emnAttachId__ = emnAttachId;
    }

    /**
     * <p>get EMN_ATTACH_NAME value
     * @return EMN_ATTACH_NAME value
     */
    public String getEmnAttachName() {
        return emnAttachName__;
    }

    /**
     * <p>set EMN_ATTACH_NAME value
     * @param emnAttachName EMN_ATTACH_NAME value
     */
    public void setEmnAttachName(String emnAttachName) {
        emnAttachName__ = emnAttachName;
    }

    /**
     * <p>get EMN_ATTACH_POS value
     * @return EMN_ATTACH_POS value
     */
    public int getEmnAttachPos() {
        return emnAttachPos__;
    }

    /**
     * <p>set EMN_ATTACH_POS value
     * @param emnAttachPos EMN_ATTACH_POS value
     */
    public void setEmnAttachPos(int emnAttachPos) {
        emnAttachPos__ = emnAttachPos;
    }

    /**
     * <p>get EMN_OPEN_STR value
     * @return EMN_OPEN_STR value
     */
    public UDate getEmnOpenStr() {
        return emnOpenStr__;
    }

    /**
     * <p>set EMN_OPEN_STR value
     * @param emnOpenStr EMN_OPEN_STR value
     */
    public void setEmnOpenStr(UDate emnOpenStr) {
        emnOpenStr__ = emnOpenStr;
    }

    /**
     * <p>get EMN_OPEN_END value
     * @return EMN_OPEN_END value
     */
    public UDate getEmnOpenEnd() {
        return emnOpenEnd__;
    }

    /**
     * <p>set EMN_OPEN_END value
     * @param emnOpenEnd EMN_OPEN_END value
     */
    public void setEmnOpenEnd(UDate emnOpenEnd) {
        emnOpenEnd__ = emnOpenEnd;
    }

    /**
     * <p>get EMN_OPEN_END_KBN value
     * @return EMN_OPEN_END_KBN value
     */
    public int getEmnOpenEndKbn() {
        return emnOpenEndKbn__;
    }

    /**
     * <p>set EMN_OPEN_END_KBN value
     * @param emnOpenEndKbn EMN_OPEN_END_KBN value
     */
    public void setEmnOpenEndKbn(int emnOpenEndKbn) {
        emnOpenEndKbn__ = emnOpenEndKbn;
    }

    /**
     * <p>get EMN_RES_END value
     * @return EMN_RES_END value
     */
    public UDate getEmnResEnd() {
        return emnResEnd__;
    }

    /**
     * <p>set EMN_RES_END value
     * @param emnResEnd EMN_RES_END value
     */
    public void setEmnResEnd(UDate emnResEnd) {
        emnResEnd__ = emnResEnd;
    }

    /**
     * <p>get EMN_ANS_PUB_STR value
     * @return EMN_ANS_PUB_STR value
     */
    public UDate getEmnAnsPubStr() {
        return emnAnsPubStr__;
    }

    /**
     * <p>set EMN_ANS_PUB_STR value
     * @param emnAnsPubStr EMN_ANS_PUB_STR value
     */
    public void setEmnAnsPubStr(UDate emnAnsPubStr) {
        emnAnsPubStr__ = emnAnsPubStr;
    }

    /**
     * <p>get EMN_ANONY value
     * @return EMN_ANONY value
     */
    public int getEmnAnony() {
        return emnAnony__;
    }

    /**
     * <p>set EMN_ANONY value
     * @param emnAnony EMN_ANONY value
     */
    public void setEmnAnony(int emnAnony) {
        emnAnony__ = emnAnony;
    }

    /**
     * <p>get EMN_ANS_OPEN value
     * @return EMN_ANS_OPEN value
     */
    public int getEmnAnsOpen() {
        return emnAnsOpen__;
    }

    /**
     * <p>set EMN_ANS_OPEN value
     * @param emnAnsOpen EMN_ANS_OPEN value
     */
    public void setEmnAnsOpen(int emnAnsOpen) {
        emnAnsOpen__ = emnAnsOpen;
    }

    /**
     * <p>get EMN_SEND_GRP value
     * @return EMN_SEND_GRP value
     */
    public long getEmnSendGrp() {
        return emnSendGrp__;
    }

    /**
     * <p>set EMN_SEND_GRP value
     * @param emnSendGrp EMN_SEND_GRP value
     */
    public void setEmnSendGrp(long emnSendGrp) {
        emnSendGrp__ = emnSendGrp;
    }

    /**
     * <p>get EMN_SEND_USR value
     * @return EMN_SEND_USR value
     */
    public long getEmnSendUsr() {
        return emnSendUsr__;
    }

    /**
     * <p>set EMN_SEND_USR value
     * @param emnSendUsr EMN_SEND_USR value
     */
    public void setEmnSendUsr(long emnSendUsr) {
        emnSendUsr__ = emnSendUsr;
    }

    /**
     * <p>get EMN_SEND_NAME value
     * @return EMN_SEND_NAME value
     */
    public String getEmnSendName() {
        return emnSendName__;
    }

    /**
     * <p>set EMN_SEND_NAME value
     * @param emnSendName EMN_SEND_NAME value
     */
    public void setEmnSendName(String emnSendName) {
        emnSendName__ = emnSendName;
    }

    /**
     * <p>get EMN_TARGET value
     * @return EMN_TARGET value
     */
    public int getEmnTarget() {
        return emnTarget__;
    }

    /**
     * <p>set EMN_TARGET value
     * @param emnTarget EMN_TARGET value
     */
    public void setEmnTarget(int emnTarget) {
        emnTarget__ = emnTarget;
    }

    /**
     * <p>get EMN_QUESEC_TYPE value
     * @return EMN_QUESEC_TYPE value
     */
    public int getEmnQuesecType() {
        return emnQuesecType__;
    }

    /**
     * <p>set EMN_QUESEC_TYPE value
     * @param emnQuesecType EMN_QUESEC_TYPE value
     */
    public void setEmnQuesecType(int emnQuesecType) {
        emnQuesecType__ = emnQuesecType;
    }

    /**
     * <p>get EMN_AUID value
     * @return EMN_AUID value
     */
    public int getEmnAuid() {
        return emnAuid__;
    }

    /**
     * <p>set EMN_AUID value
     * @param emnAuid EMN_AUID value
     */
    public void setEmnAuid(int emnAuid) {
        emnAuid__ = emnAuid;
    }

    /**
     * <p>get EMN_ADATE value
     * @return EMN_ADATE value
     */
    public UDate getEmnAdate() {
        return emnAdate__;
    }

    /**
     * <p>set EMN_ADATE value
     * @param emnAdate EMN_ADATE value
     */
    public void setEmnAdate(UDate emnAdate) {
        emnAdate__ = emnAdate;
    }

    /**
     * <p>get EMN_EUID value
     * @return EMN_EUID value
     */
    public int getEmnEuid() {
        return emnEuid__;
    }

    /**
     * <p>set EMN_EUID value
     * @param emnEuid EMN_EUID value
     */
    public void setEmnEuid(int emnEuid) {
        emnEuid__ = emnEuid;
    }

    /**
     * <p>get EMN_EDATE value
     * @return EMN_EDATE value
     */
    public UDate getEmnEdate() {
        return emnEdate__;
    }

    /**
     * <p>set EMN_EDATE value
     * @param emnEdate EMN_EDATE value
     */
    public void setEmnEdate(UDate emnEdate) {
        emnEdate__ = emnEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(emnSid__);
        buf.append(",");
        buf.append(emnDataKbn__);
        buf.append(",");
        buf.append(etpSid__);
        buf.append(",");
        buf.append(NullDefault.getString(emnTitle__, ""));
        buf.append(",");
        buf.append(emnPriKbn__);
        buf.append(",");
        buf.append(NullDefault.getString(emnDesc__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(emnDescPlain__, ""));
        buf.append(",");
        buf.append(emnAttachKbn__);
        buf.append(",");
        buf.append(NullDefault.getString(emnAttachId__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(emnAttachName__, ""));
        buf.append(",");
        buf.append(emnAttachPos__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(emnOpenStr__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(emnOpenEnd__, ""));
        buf.append(",");
        buf.append(emnOpenEndKbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(emnResEnd__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(emnAnsPubStr__, ""));
        buf.append(",");
        buf.append(emnAnony__);
        buf.append(",");
        buf.append(emnAnsOpen__);
        buf.append(",");
        buf.append(emnSendGrp__);
        buf.append(",");
        buf.append(emnSendUsr__);
        buf.append(",");
        buf.append(NullDefault.getString(emnSendName__, ""));
        buf.append(",");
        buf.append(emnTarget__);
        buf.append(",");
        buf.append(emnQuesecType__);
        buf.append(",");
        buf.append(emnAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(emnAdate__, ""));
        buf.append(",");
        buf.append(emnEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(emnEdate__, ""));
        return buf.toString();
    }

}

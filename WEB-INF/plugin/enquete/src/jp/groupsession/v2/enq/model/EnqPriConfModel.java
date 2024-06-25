package jp.groupsession.v2.enq.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_PRI_CONF Data Binding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqPriConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** EPC_MAIN_DSP mapping */
    private int epcMainDsp__;
    /** EPC_LIST_CNT mapping */
    private int epcListCnt__;
    /** EPC_LIST_FOLDER mapping */
    private int epcListFolder__;
    /** EPC_LIST_FOLDER mapping */
    private int epcCanAnswer__;
    /** EPC_AUID mapping */
    private int epcAuid__;
    /** EPC_ADATE mapping */
    private UDate epcAdate__;
    /** EPC_EUID mapping */
    private int epcEuid__;
    /** EPC_EDATE mapping */
    private UDate epcEdate__;
    /** EPC_DSPCNT_MAIN */
    private int epcDspcntMain__;
    /** EPC_DSP_CHECKED */
    private int epcDspChecked__;

    /**
     * <p>Default Constructor
     */
    public EnqPriConfModel() {
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
     * <p>get EPC_MAIN_DSP value
     * @return EPC_MAIN_DSP value
     */
    public int getEpcMainDsp() {
        return epcMainDsp__;
    }

    /**
     * <p>set EPC_MAIN_DSP value
     * @param epcMainDsp EPC_MAIN_DSP value
     */
    public void setEpcMainDsp(int epcMainDsp) {
        epcMainDsp__ = epcMainDsp;
    }

    /**
     * <p>get EPC_LIST_CNT value
     * @return EPC_LIST_CNT value
     */
    public int getEpcListCnt() {
        return epcListCnt__;
    }

    /**
     * <p>set EPC_LIST_CNT value
     * @param epcListCnt EPC_LIST_CNT value
     */
    public void setEpcListCnt(int epcListCnt) {
        epcListCnt__ = epcListCnt;
    }

    /**
     * <p>get EPC_AUID value
     * @return EPC_AUID value
     */
    public int getEpcAuid() {
        return epcAuid__;
    }

    /**
     * <p>set EPC_AUID value
     * @param epcAuid EPC_AUID value
     */
    public void setEpcAuid(int epcAuid) {
        epcAuid__ = epcAuid;
    }

    /**
     * <p>get EPC_ADATE value
     * @return EPC_ADATE value
     */
    public UDate getEpcAdate() {
        return epcAdate__;
    }

    /**
     * <p>set EPC_ADATE value
     * @param epcAdate EPC_ADATE value
     */
    public void setEpcAdate(UDate epcAdate) {
        epcAdate__ = epcAdate;
    }

    /**
     * <p>get EPC_EUID value
     * @return EPC_EUID value
     */
    public int getEpcEuid() {
        return epcEuid__;
    }

    /**
     * <p>set EPC_EUID value
     * @param epcEuid EPC_EUID value
     */
    public void setEpcEuid(int epcEuid) {
        epcEuid__ = epcEuid;
    }

    /**
     * <p>get EPC_EDATE value
     * @return EPC_EDATE value
     */
    public UDate getEpcEdate() {
        return epcEdate__;
    }

    /**
     * <p>set EPC_EDATE value
     * @param epcEdate EPC_EDATE value
     */
    public void setEpcEdate(UDate epcEdate) {
        epcEdate__ = epcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(epcMainDsp__);
        buf.append(",");
        buf.append(epcListCnt__);
        buf.append(",");
        buf.append(epcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(epcAdate__, ""));
        buf.append(",");
        buf.append(epcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(epcEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>epcListFolder を取得します。
     * @return epcListFolder
     */
    public int getEpcListFolder() {
        return epcListFolder__;
    }

    /**
     * <p>epcListFolder をセットします。
     * @param epcListFolder epcListFolder
     */
    public void setEpcListFolder(int epcListFolder) {
        epcListFolder__ = epcListFolder;
    }

    /**
     * <p>epcCanAnswer を取得します。
     * @return epcCanAnswer
     */
    public int getEpcCanAnswer() {
        return epcCanAnswer__;
    }

    /**
     * <p>epcCanAnswer をセットします。
     * @param epcCanAnswer epcCanAnswer
     */
    public void setEpcCanAnswer(int epcCanAnswer) {
        epcCanAnswer__ = epcCanAnswer;
    }

    /**
     * <p>epcDspcntMain を取得します。
     * @return epcDspcntMain
     * @see jp.groupsession.v2.enq.model.EnqPriConfModel#epcDspcntMain__
     */
    public int getEpcDspcntMain() {
        return epcDspcntMain__;
    }

    /**
     * <p>epcDspcntMain をセットします。
     * @param epcDspcntMain epcDspcntMain
     * @see jp.groupsession.v2.enq.model.EnqPriConfModel#epcDspcntMain__
     */
    public void setEpcDspcntMain(int epcDspcntMain) {
        epcDspcntMain__ = epcDspcntMain;
    }

    /**
     * <p>epcDspChecked を取得します。
     * @return epcDspChecked
     * @see jp.groupsession.v2.enq.model.EnqPriConfModel#epcDspChecked__
     */
    public int getEpcDspChecked() {
        return epcDspChecked__;
    }

    /**
     * <p>epcDspChecked をセットします。
     * @param epcDspChecked epcDspChecked
     * @see jp.groupsession.v2.enq.model.EnqPriConfModel#epcDspChecked__
     */
    public void setEpcDspChecked(int epcDspChecked) {
        epcDspChecked__ = epcDspChecked;
    }

}

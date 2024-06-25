package jp.groupsession.v2.enq.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_ADM_CONF Data Binding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqAdmConfModel implements Serializable {

    /** EAC_KBN_TAISYO mapping */
    private int eacKbnTaisyo__;
    /** EAC_MAIN_DSP_USE mapping */
    private int eacMainDspUse__;
    /** EAC_MAIN_DSP mapping */
    private int eacMainDsp__;
    /** EAC_LIST_CNT_USE mapping */
    private int eacListCntUse__;
    /** EAC_LIST_CNT mapping */
    private int eacListCnt__;
    /** EAC_FOLDER_USE mapping*/
    private int eacFolderUse__;
    /** EAC_FOLDER_SELECT mapping*/
    private int eacFolderSelect__;
    /** EAC_CAN_ANSWER*/
    private int eacCanAnswer__;
    /** EAC_AUID mapping */
    private int eacAuid__;
    /** EAC_ADATE mapping */
    private UDate eacAdate__;
    /** EAC_EUID mapping */
    private int eacEuid__;
    /** EAC_EDATE mapping */
    private UDate eacEdate__;
    /** EAC_DSPCNT_MAIN */
    private int eacDspcntMain__;
    /** EAC_DSP_CHECKED */
    private int eacDspChecked__;

    /**
     * <p>Default Constructor
     */
    public EnqAdmConfModel() {
    }

    /**
     * <p>get EAC_KBN_TAISYO value
     * @return EAC_KBN_TAISYO value
     */
    public int getEacKbnTaisyo() {
        return eacKbnTaisyo__;
    }

    /**
     * <p>set EAC_KBN_TAISYO value
     * @param eacKbnTaisyo EAC_KBN_TAISYO value
     */
    public void setEacKbnTaisyo(int eacKbnTaisyo) {
        eacKbnTaisyo__ = eacKbnTaisyo;
    }

    /**
     * <p>get EAC_MAIN_DSP_USE value
     * @return EAC_MAIN_DSP_USE value
     */
    public int getEacMainDspUse() {
        return eacMainDspUse__;
    }

    /**
     * <p>set EAC_MAIN_DSP_USE value
     * @param eacMainDspUse EAC_MAIN_DSP_USE value
     */
    public void setEacMainDspUse(int eacMainDspUse) {
        eacMainDspUse__ = eacMainDspUse;
    }

    /**
     * <p>get EAC_MAIN_DSP value
     * @return EAC_MAIN_DSP value
     */
    public int getEacMainDsp() {
        return eacMainDsp__;
    }

    /**
     * <p>set EAC_MAIN_DSP value
     * @param eacMainDsp EAC_MAIN_DSP value
     */
    public void setEacMainDsp(int eacMainDsp) {
        eacMainDsp__ = eacMainDsp;
    }

    /**
     * <p>get EAC_LIST_CNT_USE value
     * @return EAC_LIST_CNT_USE value
     */
    public int getEacListCntUse() {
        return eacListCntUse__;
    }

    /**
     * <p>set EAC_LIST_CNT_USE value
     * @param eacListCntUse EAC_LIST_CNT_USE value
     */
    public void setEacListCntUse(int eacListCntUse) {
        eacListCntUse__ = eacListCntUse;
    }

    /**
     * <p>get EAC_LIST_CNT value
     * @return EAC_LIST_CNT value
     */
    public int getEacListCnt() {
        return eacListCnt__;
    }

    /**
     * <p>set EAC_LIST_CNT value
     * @param eacListCnt EAC_LIST_CNT value
     */
    public void setEacListCnt(int eacListCnt) {
        eacListCnt__ = eacListCnt;
    }

    /**
     * <p>get EAC_AUID value
     * @return EAC_AUID value
     */
    public int getEacAuid() {
        return eacAuid__;
    }

    /**
     * <p>set EAC_AUID value
     * @param eacAuid EAC_AUID value
     */
    public void setEacAuid(int eacAuid) {
        eacAuid__ = eacAuid;
    }

    /**
     * <p>get EAC_ADATE value
     * @return EAC_ADATE value
     */
    public UDate getEacAdate() {
        return eacAdate__;
    }

    /**
     * <p>set EAC_ADATE value
     * @param eacAdate EAC_ADATE value
     */
    public void setEacAdate(UDate eacAdate) {
        eacAdate__ = eacAdate;
    }

    /**
     * <p>get EAC_EUID value
     * @return EAC_EUID value
     */
    public int getEacEuid() {
        return eacEuid__;
    }

    /**
     * <p>set EAC_EUID value
     * @param eacEuid EAC_EUID value
     */
    public void setEacEuid(int eacEuid) {
        eacEuid__ = eacEuid;
    }

    /**
     * <p>get EAC_EDATE value
     * @return EAC_EDATE value
     */
    public UDate getEacEdate() {
        return eacEdate__;
    }

    /**
     * <p>set EAC_EDATE value
     * @param eacEdate EAC_EDATE value
     */
    public void setEacEdate(UDate eacEdate) {
        eacEdate__ = eacEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(eacKbnTaisyo__);
        buf.append(",");
        buf.append(eacMainDspUse__);
        buf.append(",");
        buf.append(eacMainDsp__);
        buf.append(",");
        buf.append(eacListCntUse__);
        buf.append(",");
        buf.append(eacListCnt__);
        buf.append(",");
        buf.append(eacAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eacAdate__, ""));
        buf.append(",");
        buf.append(eacEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eacEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>eacFolderUse を取得します。
     * @return eacFolderUse
     */
    public int getEacFolderUse() {
        return eacFolderUse__;
    }

    /**
     * <p>eacFolderUse をセットします。
     * @param eacFolderUse eacFolderUse
     */
    public void setEacFolderUse(int eacFolderUse) {
        eacFolderUse__ = eacFolderUse;
    }

    /**
     * <p>eacFolderSelect を取得します。
     * @return eacFolderSelect
     */
    public int getEacFolderSelect() {
        return eacFolderSelect__;
    }

    /**
     * <p>eacFolderSelect をセットします。
     * @param eacFolderSelect eacFolderSelect
     */
    public void setEacFolderSelect(int eacFolderSelect) {
        eacFolderSelect__ = eacFolderSelect;
    }

    /**
     * <p>eacCanAnswer を取得します。
     * @return eacCanAnswer
     */
    public int getEacCanAnswer() {
        return eacCanAnswer__;
    }

    /**
     * <p>eacCanAnswer をセットします。
     * @param eacCanAnswer eacCanAnswer
     */
    public void setEacCanAnswer(int eacCanAnswer) {
        eacCanAnswer__ = eacCanAnswer;
    }

    /**
     * <p>eacDspcntMain を取得します。
     * @return eacDspcntMain
     * @see jp.groupsession.v2.enq.model.EnqAdmConfModel#eacDspcntMain__
     */
    public int getEacDspcntMain() {
        return eacDspcntMain__;
    }

    /**
     * <p>eacDspcntMain をセットします。
     * @param eacDspcntMain eacDspcntMain
     * @see jp.groupsession.v2.enq.model.EnqAdmConfModel#eacDspcntMain__
     */
    public void setEacDspcntMain(int eacDspcntMain) {
        eacDspcntMain__ = eacDspcntMain;
    }

    /**
     * <p>eacDspChecked を取得します。
     * @return eacDspChecked
     * @see jp.groupsession.v2.enq.model.EnqAdmConfModel#eacDspChecked__
     */
    public int getEacDspChecked() {
        return eacDspChecked__;
    }

    /**
     * <p>eacDspChecked をセットします。
     * @param eacDspChecked eacDspChecked
     * @see jp.groupsession.v2.enq.model.EnqAdmConfModel#eacDspChecked__
     */
    public void setEacDspChecked(int eacDspChecked) {
        eacDspChecked__ = eacDspChecked;
    }

}

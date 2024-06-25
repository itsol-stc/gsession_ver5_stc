package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;

/**
 * <p>CHT_PRI_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtPriConfModel implements Serializable {

    /** CPC_PRI_UID mapping */
    private int cpcPriUid__;
    /** CPC_PUSH_FLG mapping */
    private int cpcPushFlg__;
    /** CPC_PUSH_TIME mapping */
    private int cpcPushTime__;
    /** CPC_DEF_FLG mapping */
    private int cpcDefFlg__;
    /** CPC_CHAT_KBN mapping */
    private int cpcChatKbn__;
    /** CPC_SEL_SID mapping */
    private int cpcSelSid__;
    /** CPC_ENTER_FLG mapping */
    private int cpcEnterFlg__;
    /** CPC_AUID mapping */
    private int cpcAuid__;
    /** CPC_ADATE mapping */
    private UDate cpcAdate__;
    /** CPC_EUID mapping */
    private int cpcEuid__;
    /** CPC_EDATE mapping */
    private UDate cpcEdate__;
    /** CPC_SEL_TAB mapping */
    private int cpcSelTab__;

    /**
     * init
     * @param usrSid ユーザSID
     */
    public void initData(int usrSid) {
        this.setCpcPriUid(usrSid);
        this.setCpcPushFlg(GSConstChat.CHAT_PUSH_USE);
        this.setCpcPushTime(GSConstChat.CHAT_PUSH_DEFAULT_TIME);
        this.setCpcDefFlg(GSConstChat.PRI_DEF_FLG_USER);
        this.setCpcChatKbn(GSConstChat.CHAT_KBN_USER);
        this.setCpcSelSid(usrSid);
        this.setCpcEnterFlg(GSConstChat.CHAT_ENTER_NOT_SEND);
        UDate now = new UDate();
        this.setCpcAuid(usrSid);
        this.setCpcAdate(now);
        this.setCpcEuid(usrSid);
        this.setCpcEdate(now);
    }


    /**
     * <p>Default Constructor
     */
    public ChtPriConfModel() {
    }

    /**
     * <p>get CPC_PRI_UID value
     * @return CPC_PRI_UID value
     */
    public int getCpcPriUid() {
        return cpcPriUid__;
    }

    /**
     * <p>set CPC_PRI_UID value
     * @param cpcPriUid CPC_PRI_UID value
     */
    public void setCpcPriUid(int cpcPriUid) {
        cpcPriUid__ = cpcPriUid;
    }

    /**
     * <p>get CPC_PUSH_FLG value
     * @return CPC_PUSH_FLG value
     */
    public int getCpcPushFlg() {
        return cpcPushFlg__;
    }

    /**
     * <p>set CPC_PUSH_FLG value
     * @param cpcPushFlg CPC_PUSH_FLG value
     */
    public void setCpcPushFlg(int cpcPushFlg) {
        cpcPushFlg__ = cpcPushFlg;
    }

    /**
     * <p>get CPC_PUSH_TIME value
     * @return CPC_PUSH_TIME value
     */
    public int getCpcPushTime() {
        return cpcPushTime__;
    }

    /**
     * <p>set CPC_PUSH_TIME value
     * @param cpcPushTime CPC_PUSH_TIME value
     */
    public void setCpcPushTime(int cpcPushTime) {
        cpcPushTime__ = cpcPushTime;
    }

    /**
     * <p>get CPC_DEF_FLG value
     * @return CPC_DEF_FLG value
     */
    public int getCpcDefFlg() {
        return cpcDefFlg__;
    }

    /**
     * <p>set CPC_DEF_FLG value
     * @param cpcDefFlg CPC_DEF_FLG value
     */
    public void setCpcDefFlg(int cpcDefFlg) {
        cpcDefFlg__ = cpcDefFlg;
    }

    /**
     * <p>get CPC_CHAT_KBN value
     * @return CPC_CHAT_KBN value
     */
    public int getCpcChatKbn() {
        return cpcChatKbn__;
    }

    /**
     * <p>set CPC_CHAT_KBN value
     * @param cpcChatKbn CPC_CHAT_KBN value
     */
    public void setCpcChatKbn(int cpcChatKbn) {
        cpcChatKbn__ = cpcChatKbn;
    }

    /**
     * <p>get CPC_SEL_SID value
     * @return CPC_SEL_SID value
     */
    public int getCpcSelSid() {
        return cpcSelSid__;
    }

    /**
     * <p>set CPC_SEL_SID value
     * @param cpcSelSid CPC_SEL_SID value
     */
    public void setCpcSelSid(int cpcSelSid) {
        cpcSelSid__ = cpcSelSid;
    }

    /**
     * <p>get CPC_ENTER_FLG value
     * @return CPC_ENTER_FLG value
     */
    public int getCpcEnterFlg() {
        return cpcEnterFlg__;
    }

    /**
     * <p>set CPC_ENTER_FLG value
     * @param cpcEnterFlg CPC_ENTER_FLG value
     */
    public void setCpcEnterFlg(int cpcEnterFlg) {
        cpcEnterFlg__ = cpcEnterFlg;
    }

    /**
     * <p>get CPC_AUID value
     * @return CPC_AUID value
     */
    public int getCpcAuid() {
        return cpcAuid__;
    }

    /**
     * <p>set CPC_AUID value
     * @param cpcAuid CPC_AUID value
     */
    public void setCpcAuid(int cpcAuid) {
        cpcAuid__ = cpcAuid;
    }

    /**
     * <p>get CPC_ADATE value
     * @return CPC_ADATE value
     */
    public UDate getCpcAdate() {
        return cpcAdate__;
    }

    /**
     * <p>set CPC_ADATE value
     * @param cpcAdate CPC_ADATE value
     */
    public void setCpcAdate(UDate cpcAdate) {
        cpcAdate__ = cpcAdate;
    }

    /**
     * <p>get CPC_EUID value
     * @return CPC_EUID value
     */
    public int getCpcEuid() {
        return cpcEuid__;
    }

    /**
     * <p>set CPC_EUID value
     * @param cpcEuid CPC_EUID value
     */
    public void setCpcEuid(int cpcEuid) {
        cpcEuid__ = cpcEuid;
    }

    /**
     * <p>get CPC_EDATE value
     * @return CPC_EDATE value
     */
    public UDate getCpcEdate() {
        return cpcEdate__;
    }

    /**
     * <p>set CPC_EDATE value
     * @param cpcEdate CPC_EDATE value
     */
    public void setCpcEdate(UDate cpcEdate) {
        cpcEdate__ = cpcEdate;
    }

    /**
     * <p>cpcSelTab を取得します。
     * @return cpcSelTab
     * @see jp.groupsession.v2.cht.model.ChtPriConfModel#cpcSelTab__
     */
    public int getCpcSelTab() {
        return cpcSelTab__;
    }


    /**
     * <p>cpcSelTab をセットします。
     * @param cpcSelTab cpcSelTab
     * @see jp.groupsession.v2.cht.model.ChtPriConfModel#cpcSelTab__
     */
    public void setCpcSelTab(int cpcSelTab) {
        cpcSelTab__ = cpcSelTab;
    }


    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cpcPriUid__);
        buf.append(",");
        buf.append(cpcPushFlg__);
        buf.append(",");
        buf.append(cpcPushTime__);
        buf.append(",");
        buf.append(cpcDefFlg__);
        buf.append(",");
        buf.append(cpcChatKbn__);
        buf.append(",");
        buf.append(cpcSelSid__);
        buf.append(",");
        buf.append(cpcEnterFlg__);
        buf.append(",");
        buf.append(cpcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cpcAdate__, ""));
        buf.append(",");
        buf.append(cpcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cpcEdate__, ""));
        buf.append(",");
        buf.append(cpcSelTab__);
        return buf.toString();
    }

}

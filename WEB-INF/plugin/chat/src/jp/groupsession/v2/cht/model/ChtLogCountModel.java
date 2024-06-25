package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_LOG_COUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtLogCountModel implements Serializable {

    /** CLC_USR_SID mapping */
    private int clcUsrSid__;
    /** CLC_CHAT_KBN mapping */
    private int clcChatKbn__;
    /** CLC_DATE mapping */
    private UDate clcDate__;

    /**
     * <p>Default Constructor
     */
    public ChtLogCountModel() {
    }

    /**
     * <p>get CLC_USR_SID value
     * @return CLC_USR_SID value
     */
    public int getClcUsrSid() {
        return clcUsrSid__;
    }

    /**
     * <p>set CLC_USR_SID value
     * @param clcUsrSid CLC_USR_SID value
     */
    public void setClcUsrSid(int clcUsrSid) {
        clcUsrSid__ = clcUsrSid;
    }

    /**
     * <p>get CLC_CHAT_KBN value
     * @return CLC_CHAT_KBN value
     */
    public int getClcChatKbn() {
        return clcChatKbn__;
    }

    /**
     * <p>set CLC_CHAT_KBN value
     * @param clcChatKbn CLC_CHAT_KBN value
     */
    public void setClcChatKbn(int clcChatKbn) {
        clcChatKbn__ = clcChatKbn;
    }

    /**
     * <p>get CLC_DATE value
     * @return CLC_DATE value
     */
    public UDate getClcDate() {
        return clcDate__;
    }

    /**
     * <p>set CLC_DATE value
     * @param clcDate CLC_DATE value
     */
    public void setClcDate(UDate clcDate) {
        clcDate__ = clcDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(clcUsrSid__);
        buf.append(",");
        buf.append(clcChatKbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(clcDate__, ""));
        return buf.toString();
    }

}

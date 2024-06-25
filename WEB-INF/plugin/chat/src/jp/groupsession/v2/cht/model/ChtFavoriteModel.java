package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <p>CHT_FAVORITE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtFavoriteModel implements Serializable {

    /** CHF_UID mapping */
    private int chfUid__;
    /** CHF_CHAT_KBN mapping */
    private int chfChatKbn__;
    /** CHF_SID mapping */
    private int chfSid__;

    /**
     * <p>Default Constructor
     */
    public ChtFavoriteModel() {
    }

    /**
     * <p>get CHF_UID value
     * @return CHF_UID value
     */
    public int getChfUid() {
        return chfUid__;
    }

    /**
     * <p>set CHF_UID value
     * @param chfUid CHF_UID value
     */
    public void setChfUid(int chfUid) {
        chfUid__ = chfUid;
    }

    /**
     * <p>get CHF_CHAT_KBN value
     * @return CHF_CHAT_KBN value
     */
    public int getChfChatKbn() {
        return chfChatKbn__;
    }

    /**
     * <p>set CHF_CHAT_KBN value
     * @param chfChatKbn CHF_CHAT_KBN value
     */
    public void setChfChatKbn(int chfChatKbn) {
        chfChatKbn__ = chfChatKbn;
    }

    /**
     * <p>get CHF_SID value
     * @return CHF_SID value
     */
    public int getChfSid() {
        return chfSid__;
    }

    /**
     * <p>set CHF_SID value
     * @param chfSid CHF_SID value
     */
    public void setChfSid(int chfSid) {
        chfSid__ = chfSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(chfUid__);
        buf.append(",");
        buf.append(chfChatKbn__);
        buf.append(",");
        buf.append(chfSid__);
        return buf.toString();
    }

}

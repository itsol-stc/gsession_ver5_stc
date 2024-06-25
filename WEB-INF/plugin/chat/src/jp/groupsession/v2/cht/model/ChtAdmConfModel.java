package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;

/**
 * <p>CHT_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtAdmConfModel implements Serializable {

    /** CAC_CHAT_FLG mapping */
    private int cacChatFlg__;
    /** CAC_GROUP_FLG mapping */
    private int cacGroupFlg__;
    /** CAC_GROUP_KBN mapping */
    private int cacGroupKbn__;
    /** CAC_READ_FLG mapping */
    private int cacReadFlg__;
    /** CAC_ATDEL_FLG mapping */
    private int cacAtdelFlg__;
    /** CAC_ATDEL_Y mapping */
    private int cacAtdelY__;
    /** CAC_ATDEL_M mapping */
    private int cacAtdelM__;
    /** CAC_AUID mapping */
    private int cacAuid__;
    /** CAC_ADATE mapping */
    private UDate cacAdate__;
    /** CAC_EUID mapping */
    private int cacEuid__;
    /** CAC_EDATE mapping */
    private UDate cacEdate__;

    /**
     * <p>Default Constructor
     */
    public ChtAdmConfModel() {
    }

    /**
     * init
     */
    public void initData() {
        UDate now = new UDate();
        this.setCacChatFlg(GSConstChat.PERMIT_BETWEEN_USERS);
        this.setCacGroupFlg(GSConstChat.PERMIT_CREATE_GROUP);
        this.setCacGroupKbn(GSConstChat.TARGET_LIMIT);
        this.setCacReadFlg(GSConstChat.KIDOKU_DISP);
        this.setCacAtdelFlg(GSConstChat.AUTO_DELETE_NO);
        this.setCacAtdelY(GSConstChat.AUTO_DELETE_DEFAULT_YEAR);
        this.setCacAtdelM(GSConstChat.AUTO_DELETE_DEFAULT_MONTH);
        this.setCacAuid(-1);
        this.setCacAdate(now);
        this.setCacEuid(-1);
        this.setCacEdate(now);
    }

    /**
     * init
     * @param usrSid ユーザSID
     * @param now 日時
     */
    public void initData(int usrSid, UDate now) {
        this.setCacChatFlg(GSConstChat.PERMIT_BETWEEN_USERS);
        this.setCacGroupFlg(GSConstChat.PERMIT_CREATE_GROUP);
        this.setCacGroupKbn(GSConstChat.TARGET_LIMIT);
        this.setCacReadFlg(GSConstChat.KIDOKU_DISP);
        this.setCacAtdelFlg(GSConstChat.AUTO_DELETE_NO);
        this.setCacAtdelY(GSConstChat.AUTO_DELETE_DEFAULT_YEAR);
        this.setCacAtdelM(GSConstChat.AUTO_DELETE_DEFAULT_MONTH);
        this.setCacAuid(usrSid);
        this.setCacAdate(now);
        this.setCacEuid(usrSid);
        this.setCacEdate(now);
    }


    /**
     * <p>get CAC_CHAT_FLG value
     * @return CAC_CHAT_FLG value
     */
    public int getCacChatFlg() {
        return cacChatFlg__;
    }

    /**
     * <p>set CAC_CHAT_FLG value
     * @param cacChatFlg CAC_CHAT_FLG value
     */
    public void setCacChatFlg(int cacChatFlg) {
        cacChatFlg__ = cacChatFlg;
    }

    /**
     * <p>get CAC_GROUP_FLG value
     * @return CAC_GROUP_FLG value
     */
    public int getCacGroupFlg() {
        return cacGroupFlg__;
    }

    /**
     * <p>set CAC_GROUP_FLG value
     * @param cacGroupFlg CAC_GROUP_FLG value
     */
    public void setCacGroupFlg(int cacGroupFlg) {
        cacGroupFlg__ = cacGroupFlg;
    }

    /**
     * <p>get CAC_GROUP_KBN value
     * @return CAC_GROUP_KBN value
     */
    public int getCacGroupKbn() {
        return cacGroupKbn__;
    }

    /**
     * <p>set CAC_GROUP_KBN value
     * @param cacGroupKbn CAC_GROUP_KBN value
     */
    public void setCacGroupKbn(int cacGroupKbn) {
        cacGroupKbn__ = cacGroupKbn;
    }

    /**
     * <p>get CAC_READ_FLG value
     * @return CAC_READ_FLG value
     */
    public int getCacReadFlg() {
        return cacReadFlg__;
    }

    /**
     * <p>set CAC_READ_FLG value
     * @param cacReadFlg CAC_READ_FLG value
     */
    public void setCacReadFlg(int cacReadFlg) {
        cacReadFlg__ = cacReadFlg;
    }

    /**
     * <p>get CAC_ATDEL_FLG value
     * @return CAC_ATDEL_FLG value
     */
    public int getCacAtdelFlg() {
        return cacAtdelFlg__;
    }

    /**
     * <p>set CAC_ATDEL_FLG value
     * @param cacAtdelFlg CAC_ATDEL_FLG value
     */
    public void setCacAtdelFlg(int cacAtdelFlg) {
        cacAtdelFlg__ = cacAtdelFlg;
    }

    /**
     * <p>get CAC_ATDEL_Y value
     * @return CAC_ATDEL_Y value
     */
    public int getCacAtdelY() {
        return cacAtdelY__;
    }

    /**
     * <p>set CAC_ATDEL_Y value
     * @param cacAtdelY CAC_ATDEL_Y value
     */
    public void setCacAtdelY(int cacAtdelY) {
        cacAtdelY__ = cacAtdelY;
    }

    /**
     * <p>get CAC_ATDEL_M value
     * @return CAC_ATDEL_M value
     */
    public int getCacAtdelM() {
        return cacAtdelM__;
    }

    /**
     * <p>set CAC_ATDEL_M value
     * @param cacAtdelM CAC_ATDEL_M value
     */
    public void setCacAtdelM(int cacAtdelM) {
        cacAtdelM__ = cacAtdelM;
    }

    /**
     * <p>get CAC_AUID value
     * @return CAC_AUID value
     */
    public int getCacAuid() {
        return cacAuid__;
    }

    /**
     * <p>set CAC_AUID value
     * @param cacAuid CAC_AUID value
     */
    public void setCacAuid(int cacAuid) {
        cacAuid__ = cacAuid;
    }

    /**
     * <p>get CAC_ADATE value
     * @return CAC_ADATE value
     */
    public UDate getCacAdate() {
        return cacAdate__;
    }

    /**
     * <p>set CAC_ADATE value
     * @param cacAdate CAC_ADATE value
     */
    public void setCacAdate(UDate cacAdate) {
        cacAdate__ = cacAdate;
    }

    /**
     * <p>get CAC_EUID value
     * @return CAC_EUID value
     */
    public int getCacEuid() {
        return cacEuid__;
    }

    /**
     * <p>set CAC_EUID value
     * @param cacEuid CAC_EUID value
     */
    public void setCacEuid(int cacEuid) {
        cacEuid__ = cacEuid;
    }

    /**
     * <p>get CAC_EDATE value
     * @return CAC_EDATE value
     */
    public UDate getCacEdate() {
        return cacEdate__;
    }

    /**
     * <p>set CAC_EDATE value
     * @param cacEdate CAC_EDATE value
     */
    public void setCacEdate(UDate cacEdate) {
        cacEdate__ = cacEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cacChatFlg__);
        buf.append(",");
        buf.append(cacGroupFlg__);
        buf.append(",");
        buf.append(cacGroupKbn__);
        buf.append(",");
        buf.append(cacReadFlg__);
        buf.append(",");
        buf.append(cacAtdelFlg__);
        buf.append(",");
        buf.append(cacAtdelY__);
        buf.append(",");
        buf.append(cacAtdelM__);
        buf.append(",");
        buf.append(cacAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cacAdate__, ""));
        buf.append(",");
        buf.append(cacEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cacEdate__, ""));
        return buf.toString();
    }

}

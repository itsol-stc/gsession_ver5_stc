package jp.groupsession.v2.sch.model;

import java.io.Serializable;

/**
 * <p>SCH_PRI_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchPriPushModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** SCC_REMINDER mapping */
    private int sccReminder__ = 4;

    /**
     * <p>Default Constructor
     */
    public SchPriPushModel() {
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
     * <p>get SCC_REMINDER value
     * @return SCC_REMINDER value
     */
    public int getSccReminder() {
        return sccReminder__;
    }

    /**
     * <p>set SCC_REMINDER value
     * @param sccReminder SCC_REMINDER value
     */
    public void setSccReminder(int sccReminder) {
        sccReminder__ = sccReminder;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(sccReminder__);
        return buf.toString();
    }
    /**
     *
     * <br>[機  能] 個人設定からインスタンスを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param spcMdl 個人設定
     * @return インスタンス
     */
    public static SchPriPushModel getInstance(SchPriConfModel spcMdl) {
        SchPriPushModel ret = new SchPriPushModel();
        if (spcMdl == null) {
            return null;
        }
        ret.usrSid__ = spcMdl.getUsrSid();
        ret.sccReminder__ = spcMdl.getSccReminder();
        return ret;
    }

    /**
     *
     * <br>[機  能] スケジュールからインスタンスを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl 個人設定
     * @return インスタンス
     */
    public static SchPriPushModel getInstance(SchDataModel schMdl) {
        SchPriPushModel ret = new SchPriPushModel();
        if (schMdl == null) {
            return null;
        }
        ret.usrSid__ = schMdl.getScdUsrSid();
        ret.sccReminder__ = schMdl.getScdReminder();
        return ret;
    }

}

package jp.groupsession.v2.mem.mem010;

import java.io.Serializable;

/**
 * <br>[機  能] メモ情報欄に表示する情報を格納するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem010DisplayModel implements Serializable {

    /** メモSID */
    private long memSid__;
    /** セッションユーザSID */
    private int usrSid__;
    /** 内容 */
    private String mmdContent__;
    /** 更新日 */
    private String mmdEdate__;
    /** ラベル名 */
    private String mmlName__;
    /** 自動削除日付 */
    private String adtelDate__;
    /** 添付ファイルの有無 */
    private int memBin__;
    
    /**
     * <p>get MEM_SID value
     * @return MEM_SID value
     */
    public long getMemSid() {
        return memSid__;
    }

    /**
     * <p>set MEM_SID value
     * @param memSid MEM_SID value
     */
    public void setMemSid(long memSid) {
        memSid__ = memSid;
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
     * <p>get MMD_CONTENT value
     * @return MMD_CONTENT value
     */
    public String getMmdContent() {
        return mmdContent__;
    }

    /**
     * <p>set MMD_CONTENT value
     * @param mmdContent MMD_CONTENT value
     */
    public void setMmdContent(String mmdContent) {
        mmdContent__ = mmdContent;
    }
    
    /**
     * <p>get MMD_EDATE value
     * @return MMD_EDATE value
     */
    public String getMmdEdate() {
        return mmdEdate__;
    }

    /**
     * <p>set MMD_EDATE value
     * @param mmdEdate MMD_EDATE value
     */
    public void setMmdEdate(String mmdEdate) {
        mmdEdate__ = mmdEdate;
    }
    
    /**
     * <p>get MML_NAME value
     * @return MML_NAME value
     */
    public String getMmlName() {
        return mmlName__;
    }

    /**
     * <p>set MML_NAME value
     * @param mmlName MML_NAME value
     */
    public void setMmlName(String mmlName) {
        mmlName__ = mmlName;
    }

    /**
     * <p>adtelDate を取得します。
     * @return adtelDate
     * @see jp.groupsession.v2.mem.mem010.Mem010DisplayModel#adtelDate__
     */
    public String getAdtelDate() {
        return adtelDate__;
    }

    /**
     * <p>adtelDate をセットします。
     * @param adtelDate adtelDate
     * @see jp.groupsession.v2.mem.mem010.Mem010DisplayModel#adtelDate__
     */
    public void setAdtelDate(String adtelDate) {
        adtelDate__ = adtelDate;
    }

    /**
     * <p>memBin を取得します。
     * @return memBin
     * @see jp.groupsession.v2.mem.mem010.Mem010DisplayModel#memBin__
     */
    public int getMemBin() {
        return memBin__;
    }

    /**
     * <p>memBin をセットします。
     * @param memBin memBin
     * @see jp.groupsession.v2.mem.mem010.Mem010DisplayModel#memBin__
     */
    public void setMemBin(int memBin) {
        memBin__ = memBin;
    }

}

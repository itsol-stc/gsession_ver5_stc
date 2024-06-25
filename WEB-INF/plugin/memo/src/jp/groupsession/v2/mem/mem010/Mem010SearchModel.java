package jp.groupsession.v2.mem.mem010;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] メモ検索条件を格納するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem010SearchModel implements Serializable {

    /** MEM_SID mapping */
    private long memSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** MMD_CONTENT mapping */
    private String mmdContent__ = null;
    
    /** 並び順 */
    private int sortOrder__;
    
    /** 現在のメモ一覧の表示件数 */
    private int hyouziKensu__ = 0;
    
    /** 登録日From */
    private UDate dateFr__ = null;
    
    /** 登録日To */
    private UDate dateTo__ = null;
    
    /** ラベルSID */
    private int mmlSid__ = -1;
    
    /** 添付の有無 */
    private int tenpu__ = -1;
    
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
     * フィールドsortOrder__の値を取得します。
     * @return sortOrder__
     */
    public int getSortOrder() {
        return sortOrder__;
    }

    /**
     * フィールドsortOrder__の値を設定します。
     * @param sortOrderを フィールドsortOrder__に設定します。
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder__ = sortOrder;
    }

    /**
     * フィールドhyouziKensu__の値を取得します。
     * @return hyouziKensu__
     */
    public int getHyouziKensu() {
        return hyouziKensu__;
    }

    /**
     * フィールドhyouziKensu__の値を設定します。
     * @param hyouziKensu を フィールドhyouziKensu__に設定します。
     */
    public void setHyouziKensu(int hyouziKensu) {
        this.hyouziKensu__ = hyouziKensu;
    }

    /**
     * フィールドdateFr__の値を取得します。
     * @return dateFr__
     */
    public UDate getDateFr() {
        return dateFr__;
    }

    /**
     * フィールドdateFr__の値を設定します。
     * @param dateFr__ を フィールドdateFr__に設定します。
     */
    public void setDateFr(UDate dateFr) {
        this.dateFr__ = dateFr;
    }

    /**
     * フィールドdateTo__の値を取得します。
     * @return dateTo__
     */
    public UDate getDateTo() {
        return dateTo__;
    }

    /**
     * フィールドdateTo__の値を設定します。
     * @param dateTo__ を フィールドdateTo__に設定します。
     */
    public void setDateTo(UDate dateTo) {
        this.dateTo__ = dateTo;
    }

    /**
     * フィールドmmlSid__の値を取得します。
     * @return mmlSid__
     */
    public int getMmlSid() {
        return mmlSid__;
    }

    /**
     * フィールドmmlSid__の値を設定します。
     * @param mmlSid__ を フィールドmmlSid__に設定します。
     */
    public void setMmlSid(int mmlSid) {
        this.mmlSid__ = mmlSid;
    }

    /**
     * フィールドtenpu__の値を取得します。
     * @return tenpu__
     */
    public int getTenpu() {
        return tenpu__;
    }

    /**
     * フィールドtenpu__の値を設定します。
     * @param tenpu__ を フィールドtenpu__に設定します。
     */
    public void setTenpu(int tenpu) {
        this.tenpu__ = tenpu;
    }

}

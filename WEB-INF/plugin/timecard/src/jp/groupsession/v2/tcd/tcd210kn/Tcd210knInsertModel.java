package jp.groupsession.v2.tcd.tcd210kn;

import java.math.BigDecimal;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210knInsertModel {

    /** 年度 */
    private int nendo__;
    
    /** ユーザ名 */
    private String userName__ = null;
    
    /** 有休付与日数 */
    private BigDecimal yukyuDays__;
    
    /** ユーザSID */
    private int userSid__;

    /**
     * <p>nendo を取得します。
     * @return nendo
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knInsertModel#nendo__
     */
    public int getNendo() {
        return nendo__;
    }

    /**
     * <p>nendo をセットします。
     * @param nendo nendo
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knInsertModel#nendo__
     */
    public void setNendo(int nendo) {
        nendo__ = nendo;
    }

    /**
     * <p>userName を取得します。
     * @return userName
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knInsertModel#userName__
     */
    public String getUserName() {
        return userName__;
    }

    /**
     * <p>userName をセットします。
     * @param userName userName
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knInsertModel#userName__
     */
    public void setUserName(String userName) {
        userName__ = userName;
    }

    /**
     * <p>yukyuDays を取得します。
     * @return yukyuDays
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knInsertModel#yukyuDays__
     */
    public BigDecimal getYukyuDays() {
        return yukyuDays__;
    }

    /**
     * <p>yukyuDays をセットします。
     * @param yukyuDays yukyuDays
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knInsertModel#yukyuDays__
     */
    public void setYukyuDays(BigDecimal yukyuDays) {
        yukyuDays__ = yukyuDays;
    }

    /**
     * <p>userSid を取得します。
     * @return userSid
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knInsertModel#userSid__
     */
    public int getUserSid() {
        return userSid__;
    }

    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knInsertModel#userSid__
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
}

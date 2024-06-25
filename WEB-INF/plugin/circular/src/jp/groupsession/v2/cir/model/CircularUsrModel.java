package jp.groupsession.v2.cir.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <p>CMN_USRM_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CircularUsrModel extends CmnUsrmInfModel {

    /** アカウントSID */
    private String cacSid__ = null;
    /** アカウント名 */
    private String cacName__ = null;
    /** アカウント状態区分 */
    private int cacJkbn__;
    /**
     * <p>cacSid を取得します。
     * @return cacSid
     */
    public String getCacSid() {
        return cacSid__;
    }
    /**
     * <p>cacSid をセットします。
     * @param cacSid cacSid
     */
    public void setCacSid(String cacSid) {
        cacSid__ = cacSid;
    }
    /**
     * <p>cacName を取得します。
     * @return cacName
     */
    public String getCacName() {
        return cacName__;
    }
    /**
     * <p>cacName をセットします。
     * @param cacName cacName
     */
    public void setCacName(String cacName) {
        cacName__ = cacName;
    }
    /**
     * <p>cacJkbn を取得します。
     * @return cacJkbn
     */
    public int getCacJkbn() {
        return cacJkbn__;
    }
    /**
     * <p>cacJkbn をセットします。
     * @param cacJkbn cacJkbn
     */
    public void setCacJkbn(int cacJkbn) {
        cacJkbn__ = cacJkbn;
    }
}
package jp.groupsession.v2.api.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.model.base.IApiConfModel;

/**
 * <p>API_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiConfModel implements Serializable, IApiConfModel {

    /** APC_TOAKEN_USE mapping */
    private int apcToakenUse__ = GSConstApi.USEKBN_AUTH_USE;
    /** APC_TOAKEN_IP mapping */
    private String apcToakenIp__;
    /** APC_TOAKEN_LIFE mapping */
    private int apcToakenLife__ = GSConstApi.TOKEN_LIMIT_FREE;
    /** APC_BASIC_USE mapping */
    private int apcBasicUse__  = GSConstApi.USEKBN_AUTH_NOUSE;
    /** APC_BASIC_IP mapping */
    private String apcBasicIp__;
    /** APC_AUTO_DEL*/
    private int apcAutoDel__;


    /**
     * <p>Default Constructor
     */
    public ApiConfModel() {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#getApcToakenUse()
     */
    @Override
    public int getApcToakenUse() {
        return apcToakenUse__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#setApcToakenUse(int)
     */
    @Override
    public void setApcToakenUse(int apcToakenUse) {
        apcToakenUse__ = apcToakenUse;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#getApcToakenIp()
     */
    @Override
    public String getApcToakenIp() {
        return apcToakenIp__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#setApcToakenIp(java.lang.String)
     */
    @Override
    public void setApcToakenIp(String apcToakenIp) {
        apcToakenIp__ = apcToakenIp;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#getApcToakenLife()
     */
    @Override
    public int getApcToakenLife() {
        return apcToakenLife__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#setApcToakenLife(int)
     */
    @Override
    public void setApcToakenLife(int apcToakenLife) {
        apcToakenLife__ = apcToakenLife;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#getApcBasicUse()
     */
    @Override
    public int getApcBasicUse() {
        return apcBasicUse__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#setApcBasicUse(int)
     */
    @Override
    public void setApcBasicUse(int apcBasicUse) {
        apcBasicUse__ = apcBasicUse;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#getApcBasicIp()
     */
    @Override
    public String getApcBasicIp() {
        return apcBasicIp__;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.model.IApiConfModel#setApcBasicIp(java.lang.String)
     */
    @Override
    public void setApcBasicIp(String apcBasicIp) {
        apcBasicIp__ = apcBasicIp;
    }

    /**
     * <p>apcAutoDel を取得します。
     * @return apcAutoDel
     * @see jp.groupsession.v2.api.model.ApiConfModel#apcAutoDel__
     */
    public int getApcAutoDel() {
        return apcAutoDel__;
    }

    /**
     * <p>apcAutoDel をセットします。
     * @param apcAutoDel apcAutoDel
     * @see jp.groupsession.v2.api.model.ApiConfModel#apcAutoDel__
     */
    public void setApcAutoDel(int apcAutoDel) {
        apcAutoDel__ = apcAutoDel;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(apcToakenUse__);
        buf.append(",");
        buf.append(NullDefault.getString(apcToakenIp__, ""));
        buf.append(",");
        buf.append(apcToakenLife__);
        buf.append(",");
        buf.append(apcBasicUse__);
        buf.append(",");
        buf.append(NullDefault.getString(apcBasicIp__, ""));
        buf.append(",");
        buf.append(apcAutoDel__);
        return buf.toString();
    }

}

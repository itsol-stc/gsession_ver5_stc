package jp.groupsession.v2.bmk.model;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>BMK_SCHEME Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BmkSchemeModel implements Serializable {

    /** BSC_SCHEME mapping */
    private String bscScheme__;

    /**
     * <p>Default Constructor
     */
    public BmkSchemeModel() {
    }

    /**
     * <p>get BSC_SCHEME value
     * @return BSC_SCHEME value
     */
    public String getBscScheme() {
        return bscScheme__;
    }

    /**
     * <p>set BSC_SCHEME value
     * @param bscScheme BSC_SCHEME value
     */
    public void setBscScheme(String bscScheme) {
        bscScheme__ = bscScheme;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(NullDefault.getString(bscScheme__, ""));
        return buf.toString();
    }

}

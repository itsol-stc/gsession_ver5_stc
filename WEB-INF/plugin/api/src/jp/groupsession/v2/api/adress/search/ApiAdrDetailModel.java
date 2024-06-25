package jp.groupsession.v2.api.adress.search;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel;
/**
 *
 * <br>[機  能] API アドレス検索結果モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAdrDetailModel extends Adr010CsvDetailModel {
    /** 姓 */
    private String sei__;
    /** 名 */
    private String mei__;
    /** 姓 カナ*/
    private String seiKn__;
    /** 名 カナ*/
    private String meiKn__;
    /** ADR_AUID mapping */
    private int auid__;
    /** ADR_ADATE mapping */
    private UDate adate__ = null;
    /** ADR_EUID mapping */
    private int euid__;
    /** ADR_EDATE mapping */
    private UDate edate__ = null;


    /**
     * <p>auid を取得します。
     * @return auid
     */
    public int getAuid() {
        return auid__;
    }
    /**
     * <p>auid をセットします。
     * @param auid auid
     */
    public void setAuid(int auid) {
        auid__ = auid;
    }
    /**
     * <p>adate を取得します。
     * @return adate
     */
    public UDate getAdate() {
        return adate__;
    }
    /**
     * <p>adate をセットします。
     * @param adate adate
     */
    public void setAdate(UDate adate) {
        adate__ = adate;
    }
    /**
     * <p>euid を取得します。
     * @return euid
     */
    public int getEuid() {
        return euid__;
    }
    /**
     * <p>euid をセットします。
     * @param euid euid
     */
    public void setEuid(int euid) {
        euid__ = euid;
    }
    /**
     * <p>edate を取得します。
     * @return edate
     */
    public UDate getEdate() {
        return edate__;
    }
    /**
     * <p>edate をセットします。
     * @param edate edate
     */
    public void setEdate(UDate edate) {
        edate__ = edate;
    }
    /**
     * <p>sei を取得します。
     * @return sei
     */
    public String getSei() {
        return sei__;
    }
    /**
     * <p>sei をセットします。
     * @param sei sei
     */
    public void setSei(String sei) {
        sei__ = sei;
    }
    /**
     * <p>mei を取得します。
     * @return mei
     */
    public String getMei() {
        return mei__;
    }
    /**
     * <p>mei をセットします。
     * @param mei mei
     */
    public void setMei(String mei) {
        mei__ = mei;
    }
    /**
     * <p>seiKn を取得します。
     * @return seiKn
     */
    public String getSeiKn() {
        return seiKn__;
    }
    /**
     * <p>seiKn をセットします。
     * @param seiKn seiKn
     */
    public void setSeiKn(String seiKn) {
        seiKn__ = seiKn;
    }
    /**
     * <p>meiKn を取得します。
     * @return meiKn
     */
    public String getMeiKn() {
        return meiKn__;
    }
    /**
     * <p>meiKn をセットします。
     * @param meiKn meiKn
     */
    public void setMeiKn(String meiKn) {
        meiKn__ = meiKn;
    }

}

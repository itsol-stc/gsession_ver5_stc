package jp.groupsession.v2.cir.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 回覧板初期値管理者設定を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirInitModel implements Serializable {

    /** CIN_INITSET_KEN mapping */
    private int cinInitSetKen__;
    /** CIN_MEMO_KBN mapping */
    private int cinMemoKbn__;
    /** CIN_MEMO_DAY mapping */
    private int cinMemoDay__;
    /** CIN_KOU_KBN mapping */
    private int cinKouKbn__;
    /** CIN_AUID mapping */
    private int cinAuid__;
    /** CIN_ADATE mapping */
    private UDate cinAdate__;
    /** CIN_EUID mapping */
    private int cinEuid__;
    /** CIN_EDATE mapping */
    private UDate cinEdate__;
    /** CIN_ACNT_MAKE mapping */
    private int cinAcntMake__;
    /** CIN_AUTO_DEL_KBN mapping */
    private int cinAutoDelKbn__;
    /** CIN_ACNT_USER mapping */
    private int cinAcntUser__;

    /**
     * <p>cinInitSetKen を取得します。
     * @return cinInitSetKen
     */
    public int getCinInitSetKen() {
        return cinInitSetKen__;
    }
    /**
     * <p>cinInitSetKen をセットします。
     * @param cinInitSetKen cinInitSetKen
     */
    public void setCinInitSetKen(int cinInitSetKen) {
        cinInitSetKen__ = cinInitSetKen;
    }
    /**
     * <p>cinMemoKbn を取得します。
     * @return cinMemoKbn
     */
    public int getCinMemoKbn() {
        return cinMemoKbn__;
    }
    /**
     * <p>cinMemoKbn をセットします。
     * @param cinMemoKbn cinMemoKbn
     */
    public void setCinMemoKbn(int cinMemoKbn) {
        cinMemoKbn__ = cinMemoKbn;
    }
    /**
     * <p>cinMemoDay を取得します。
     * @return cinMemoDay
     */
    public int getCinMemoDay() {
        return cinMemoDay__;
    }
    /**
     * <p>cinMemoDay をセットします。
     * @param cinMemoDay cinMemoDay
     */
    public void setCinMemoDay(int cinMemoDay) {
        cinMemoDay__ = cinMemoDay;
    }
    /**
     * <p>cinKouKbn を取得します。
     * @return cinKouKbn
     */
    public int getCinKouKbn() {
        return cinKouKbn__;
    }
    /**
     * <p>cinKouKbn をセットします。
     * @param cinKouKbn cinKouKbn
     */
    public void setCinKouKbn(int cinKouKbn) {
        cinKouKbn__ = cinKouKbn;
    }
    /**
     * <p>cinAuid を取得します。
     * @return cinAuid
     */
    public int getCinAuid() {
        return cinAuid__;
    }
    /**
     * <p>cinAuid をセットします。
     * @param cinAuid cinAuid
     */
    public void setCinAuid(int cinAuid) {
        cinAuid__ = cinAuid;
    }
    /**
     * <p>cinAdate を取得します。
     * @return cinAdate
     */
    public UDate getCinAdate() {
        return cinAdate__;
    }
    /**
     * <p>cinAdate をセットします。
     * @param cinAdate cinAdate
     */
    public void setCinAdate(UDate cinAdate) {
        cinAdate__ = cinAdate;
    }
    /**
     * <p>cinEuid を取得します。
     * @return cinEuid
     */
    public int getCinEuid() {
        return cinEuid__;
    }
    /**
     * <p>cinEuid をセットします。
     * @param cinEuid cinEuid
     */
    public void setCinEuid(int cinEuid) {
        cinEuid__ = cinEuid;
    }
    /**
     * <p>cinEdate を取得します。
     * @return cinEdate
     */
    public UDate getCinEdate() {
        return cinEdate__;
    }
    /**
     * <p>cinEdate をセットします。
     * @param cinEdate cinEdate
     */
    public void setCinEdate(UDate cinEdate) {
        cinEdate__ = cinEdate;
    }
    /**
     * <p>cinAcntMake を取得します。
     * @return cinAcntMake
     */
    public int getCinAcntMake() {
        return cinAcntMake__;
    }
    /**
     * <p>cinAcntMake をセットします。
     * @param cinAcntMake cinAcntMake
     */
    public void setCinAcntMake(int cinAcntMake) {
        cinAcntMake__ = cinAcntMake;
    }
    /**
     * <p>cinAutoDelKbn を取得します。
     * @return cinAutoDelKbn
     */
    public int getCinAutoDelKbn() {
        return cinAutoDelKbn__;
    }
    /**
     * <p>cinAutoDelKbn をセットします。
     * @param cinAutoDelKbn cinAutoDelKbn
     */
    public void setCinAutoDelKbn(int cinAutoDelKbn) {
        cinAutoDelKbn__ = cinAutoDelKbn;
    }

    /**
     * <p>get CIN_ACNT_USER value
     * @return CIN_ACNT_USER value
     */
    public int getCinAcntUser() {
        return cinAcntUser__;
    }

    /**
     * <p>set CIN_ACNT_USER value
     * @param cinAcntUser CIN_ACNT_USER value
     */
    public void setCinAcntUser(int cinAcntUser) {
        cinAcntUser__ = cinAcntUser;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cinInitSetKen__);
        buf.append(",");
        buf.append(cinMemoKbn__);
        buf.append(",");
        buf.append(cinMemoDay__);
        buf.append(",");
        buf.append(cinKouKbn__);
        buf.append(",");
        buf.append(cinAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cinAdate__, ""));
        buf.append(",");
        buf.append(cinEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cinEdate__, ""));
        buf.append(",");
        buf.append(cinAcntMake__);
        buf.append(",");
        buf.append(cinAutoDelKbn__);
        buf.append(",");
        buf.append(cinAcntUser__);
        return buf.toString();
    }
}

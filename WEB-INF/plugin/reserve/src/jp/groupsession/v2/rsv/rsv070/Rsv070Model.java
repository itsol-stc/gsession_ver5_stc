package jp.groupsession.v2.rsv.rsv070;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 施設情報画面で使用するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv070Model extends AbstractModel {

    /** 施設グループSID */
    private int rsgSid__ = -1;
    /** 施設グループ名称 */
    private String rsgName__ = null;
    /** 施設区分 */
    private int rskSid__ = -1;
    /** 施設区分名称 */
    private String rskName__ = null;
    /** 施設ID */
    private String rsdId__ = null;
    /** 施設名称 */
    private String rsdName__ = null;
    /** 施設資産管理番号 */
    private String rsdSnum__ = null;
    /** 施設設定属性1 */
    private String rsdProp1Value__ = null;
    /** 施設設定属性2 */
    private String rsdProp2Value__ = null;
    /** 施設設定属性3 */
    private String rsdProp3Value__ = null;
    /** 施設設定属性4 */
    private String rsdProp4Value__ = null;
    /** 施設設定属性5 */
    private String rsdProp5Value__ = null;
    /** 施設設定属性6 */
    private String rsdProp6Value__ = null;
    /** 施設設定属性7 */
    private String rsdProp7Value__ = null;
    /** 備考 */
    private String rsdBiko__ = null;
    /** 場所コメント */
    private String rsdPlaCmt__;
    /** 場所/地図画像 コメント１ */
    private String rsdImgCmt1__;
    /** 場所/地図画像 コメント２ */
    private String rsdImgCmt2__;
    /** 場所/地図画像 コメント３ */
    private String rsdImgCmt3__;
    /** 場所/地図画像 コメント４ */
    private String rsdImgCmt4__;
    /** 場所/地図画像 コメント５ */
    private String rsdImgCmt5__;
    /** 場所/地図画像 コメント６ */
    private String rsdImgCmt6__;
    /** 場所/地図画像 コメント７ */
    private String rsdImgCmt7__;
    /** 場所/地図画像 コメント８ */
    private String rsdImgCmt8__;
    /** 場所/地図画像 コメント９ */
    private String rsdImgCmt9__;
    /** 場所/地図画像 コメント１０ */
    private String rsdImgCmt10__;
    /** 施設予約の承認 */
    private int rsdApprKbn__;
    /** 施設予約の承認表示フラグ */
    private int rsdApprKbnDf__;

    /**
     * <p>rsgSid__ を取得します。
     * @return rsgSid
     */
    public int getRsgSid() {
        return rsgSid__;
    }
    /**
     * <p>rsgSid__ をセットします。
     * @param rsgSid rsgSid__
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
    }
    /**
     * <p>rsdBiko__ を取得します。
     * @return rsdBiko
     */
    public String getRsdBiko() {
        return rsdBiko__;
    }
    /**
     * <p>rsdBiko__ をセットします。
     * @param rsdBiko rsdBiko__
     */
    public void setRsdBiko(String rsdBiko) {
        rsdBiko__ = rsdBiko;
    }
    /**
     * <p>rsdName__ を取得します。
     * @return rsdName
     */
    public String getRsdName() {
        return rsdName__;
    }
    /**
     * <p>rsdName__ をセットします。
     * @param rsdName rsdName__
     */
    public void setRsdName(String rsdName) {
        rsdName__ = rsdName;
    }
    /**
     * <p>rsdProp1Value__ を取得します。
     * @return rsdProp1Value
     */
    public String getRsdProp1Value() {
        return rsdProp1Value__;
    }
    /**
     * <p>rsdProp1Value__ をセットします。
     * @param rsdProp1Value rsdProp1Value__
     */
    public void setRsdProp1Value(String rsdProp1Value) {
        rsdProp1Value__ = rsdProp1Value;
    }
    /**
     * <p>rsdProp2Value__ を取得します。
     * @return rsdProp2Value
     */
    public String getRsdProp2Value() {
        return rsdProp2Value__;
    }
    /**
     * <p>rsdProp2Value__ をセットします。
     * @param rsdProp2Value rsdProp2Value__
     */
    public void setRsdProp2Value(String rsdProp2Value) {
        rsdProp2Value__ = rsdProp2Value;
    }
    /**
     * <p>rsdProp3Value__ を取得します。
     * @return rsdProp3Value
     */
    public String getRsdProp3Value() {
        return rsdProp3Value__;
    }
    /**
     * <p>rsdProp3Value__ をセットします。
     * @param rsdProp3Value rsdProp3Value__
     */
    public void setRsdProp3Value(String rsdProp3Value) {
        rsdProp3Value__ = rsdProp3Value;
    }
    /**
     * <p>rsdSnum__ を取得します。
     * @return rsdSnum
     */
    public String getRsdSnum() {
        return rsdSnum__;
    }
    /**
     * <p>rsdSnum__ をセットします。
     * @param rsdSnum rsdSnum__
     */
    public void setRsdSnum(String rsdSnum) {
        rsdSnum__ = rsdSnum;
    }
    /**
     * <p>rsgName__ を取得します。
     * @return rsgName
     */
    public String getRsgName() {
        return rsgName__;
    }
    /**
     * <p>rsgName__ をセットします。
     * @param rsgName rsgName__
     */
    public void setRsgName(String rsgName) {
        rsgName__ = rsgName;
    }
    /**
     * <p>rskName__ を取得します。
     * @return rskName
     */
    public String getRskName() {
        return rskName__;
    }
    /**
     * <p>rskName__ をセットします。
     * @param rskName rskName__
     */
    public void setRskName(String rskName) {
        rskName__ = rskName;
    }
    /**
     * <p>rskSid__ を取得します。
     * @return rskSid
     */
    public int getRskSid() {
        return rskSid__;
    }
    /**
     * <p>rskSid__ をセットします。
     * @param rskSid rskSid__
     */
    public void setRskSid(int rskSid) {
        rskSid__ = rskSid;
    }
    /**
     * <p>rsdProp4Value__ を取得します。
     * @return rsdProp4Value
     */
    public String getRsdProp4Value() {
        return rsdProp4Value__;
    }
    /**
     * <p>rsdProp4Value__ をセットします。
     * @param rsdProp4Value rsdProp4Value__
     */
    public void setRsdProp4Value(String rsdProp4Value) {
        rsdProp4Value__ = rsdProp4Value;
    }
    /**
     * <p>rsdProp5Value__ を取得します。
     * @return rsdProp5Value
     */
    public String getRsdProp5Value() {
        return rsdProp5Value__;
    }
    /**
     * <p>rsdProp5Value__ をセットします。
     * @param rsdProp5Value rsdProp5Value__
     */
    public void setRsdProp5Value(String rsdProp5Value) {
        rsdProp5Value__ = rsdProp5Value;
    }
    /**
     * <p>rsdProp6Value__ を取得します。
     * @return rsdProp6Value
     */
    public String getRsdProp6Value() {
        return rsdProp6Value__;
    }
    /**
     * <p>rsdProp6Value__ をセットします。
     * @param rsdProp6Value rsdProp6Value__
     */
    public void setRsdProp6Value(String rsdProp6Value) {
        rsdProp6Value__ = rsdProp6Value;
    }
    /**
     * <p>rsdProp7Value__ を取得します。
     * @return rsdProp7Value
     */
    public String getRsdProp7Value() {
        return rsdProp7Value__;
    }
    /**
     * <p>rsdProp7Value__ をセットします。
     * @param rsdProp7Value rsdProp7Value__
     */
    public void setRsdProp7Value(String rsdProp7Value) {
        rsdProp7Value__ = rsdProp7Value;
    }
    /**
     * @return rsdId
     */
    public String getRsdId() {
        return rsdId__;
    }
    /**
     * @param rsdId 設定する rsdId
     */
    public void setRsdId(String rsdId) {
        rsdId__ = rsdId;
    }
    /**
     * <p>rsdImgCmt1 を取得します。
     * @return rsdImgCmt1
     */
    public String getRsdImgCmt1() {
        return rsdImgCmt1__;
    }
    /**
     * <p>rsdImgCmt1 をセットします。
     * @param rsdImgCmt1 rsdImgCmt1
     */
    public void setRsdImgCmt1(String rsdImgCmt1) {
        rsdImgCmt1__ = rsdImgCmt1;
    }
    /**
     * <p>rsdImgCmt10 を取得します。
     * @return rsdImgCmt10
     */
    public String getRsdImgCmt10() {
        return rsdImgCmt10__;
    }
    /**
     * <p>rsdImgCmt10 をセットします。
     * @param rsdImgCmt10 rsdImgCmt10
     */
    public void setRsdImgCmt10(String rsdImgCmt10) {
        rsdImgCmt10__ = rsdImgCmt10;
    }
    /**
     * <p>rsdImgCmt2 を取得します。
     * @return rsdImgCmt2
     */
    public String getRsdImgCmt2() {
        return rsdImgCmt2__;
    }
    /**
     * <p>rsdImgCmt2 をセットします。
     * @param rsdImgCmt2 rsdImgCmt2
     */
    public void setRsdImgCmt2(String rsdImgCmt2) {
        rsdImgCmt2__ = rsdImgCmt2;
    }
    /**
     * <p>rsdImgCmt3 を取得します。
     * @return rsdImgCmt3
     */
    public String getRsdImgCmt3() {
        return rsdImgCmt3__;
    }
    /**
     * <p>rsdImgCmt3 をセットします。
     * @param rsdImgCmt3 rsdImgCmt3
     */
    public void setRsdImgCmt3(String rsdImgCmt3) {
        rsdImgCmt3__ = rsdImgCmt3;
    }
    /**
     * <p>rsdImgCmt4 を取得します。
     * @return rsdImgCmt4
     */
    public String getRsdImgCmt4() {
        return rsdImgCmt4__;
    }
    /**
     * <p>rsdImgCmt4 をセットします。
     * @param rsdImgCmt4 rsdImgCmt4
     */
    public void setRsdImgCmt4(String rsdImgCmt4) {
        rsdImgCmt4__ = rsdImgCmt4;
    }
    /**
     * <p>rsdImgCmt5 を取得します。
     * @return rsdImgCmt5
     */
    public String getRsdImgCmt5() {
        return rsdImgCmt5__;
    }
    /**
     * <p>rsdImgCmt5 をセットします。
     * @param rsdImgCmt5 rsdImgCmt5
     */
    public void setRsdImgCmt5(String rsdImgCmt5) {
        rsdImgCmt5__ = rsdImgCmt5;
    }
    /**
     * <p>rsdImgCmt6 を取得します。
     * @return rsdImgCmt6
     */
    public String getRsdImgCmt6() {
        return rsdImgCmt6__;
    }
    /**
     * <p>rsdImgCmt6 をセットします。
     * @param rsdImgCmt6 rsdImgCmt6
     */
    public void setRsdImgCmt6(String rsdImgCmt6) {
        rsdImgCmt6__ = rsdImgCmt6;
    }
    /**
     * <p>rsdImgCmt7 を取得します。
     * @return rsdImgCmt7
     */
    public String getRsdImgCmt7() {
        return rsdImgCmt7__;
    }
    /**
     * <p>rsdImgCmt7 をセットします。
     * @param rsdImgCmt7 rsdImgCmt7
     */
    public void setRsdImgCmt7(String rsdImgCmt7) {
        rsdImgCmt7__ = rsdImgCmt7;
    }
    /**
     * <p>rsdImgCmt8 を取得します。
     * @return rsdImgCmt8
     */
    public String getRsdImgCmt8() {
        return rsdImgCmt8__;
    }
    /**
     * <p>rsdImgCmt8 をセットします。
     * @param rsdImgCmt8 rsdImgCmt8
     */
    public void setRsdImgCmt8(String rsdImgCmt8) {
        rsdImgCmt8__ = rsdImgCmt8;
    }
    /**
     * <p>rsdImgCmt9 を取得します。
     * @return rsdImgCmt9
     */
    public String getRsdImgCmt9() {
        return rsdImgCmt9__;
    }
    /**
     * <p>rsdImgCmt9 をセットします。
     * @param rsdImgCmt9 rsdImgCmt9
     */
    public void setRsdImgCmt9(String rsdImgCmt9) {
        rsdImgCmt9__ = rsdImgCmt9;
    }
    /**
     * <p>rsdPlaCmt を取得します。
     * @return rsdPlaCmt
     */
    public String getRsdPlaCmt() {
        return rsdPlaCmt__;
    }
    /**
     * <p>rsdPlaCmt をセットします。
     * @param rsdPlaCmt rsdPlaCmt
     */
    public void setRsdPlaCmt(String rsdPlaCmt) {
        rsdPlaCmt__ = rsdPlaCmt;
    }
    /**
     * <p>rsdApprKbn を取得します。
     * @return rsdApprKbn
     */
    public int getRsdApprKbn() {
        return rsdApprKbn__;
    }
    /**
     * <p>rsdApprKbn をセットします。
     * @param rsdApprKbn rsdApprKbn
     */
    public void setRsdApprKbn(int rsdApprKbn) {
        rsdApprKbn__ = rsdApprKbn;
    }
    /**
     * <p>rsdApprKbnDf を取得します。
     * @return rsdApprKbnDf
     */
    public int getRsdApprKbnDf() {
        return rsdApprKbnDf__;
    }
    /**
     * <p>rsdApprKbnDf をセットします。
     * @param rsdApprKbnDf rsdApprKbnDf
     */
    public void setRsdApprKbnDf(int rsdApprKbnDf) {
        rsdApprKbnDf__ = rsdApprKbnDf;
    }
}
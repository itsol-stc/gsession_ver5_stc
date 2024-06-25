package jp.groupsession.v2.rsv;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 施設情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvSisetuModel extends AbstractModel {

    /** 施設グループSID */
    private int rsgSid__;
    /** 施設SID */
    private int rsdSid__;
    /** 施設名称 */
    private String rsdName__;
    /** 予約情報(1週間分)リスト */
    private ArrayList<RsvWeekModel> rsvWeekList__ = null;
    /** 施設画像バイナリSID */
    private Long sisetuImgBinSid__ = new Long(0);
    /** 施設ID */
    private String rsdSisetuId__ = null;
    /** 資産管理番号 */
    private String rsdSisanKanri__ = null;
    /** 表示項目1項目入力欄 座席数、個数、乗員数、冊数などの数値 */
    private String rsdProp1Value__ = null;
    /** 表示項目2項目入力欄 喫煙の可不可 */
    private String rsdProp2Value__ = null;
    /** 表示項目3項目入力欄 社外持出しの可不可 */
    private String rsdProp3Value__ = null;
    /** 表示項目4項目入力欄 車のナンバー */
    private String rsdProp4Value__ = null;
    /** 表示項目5項目入力欄 書籍のISBN */
    private String rsdProp5Value__ = null;
    /** 表示項目6項目入力欄 全施設共通 予約可能期限 */
    private String rsdProp6Value__ = null;
    /** 表示項目7項目入力欄 全施設共通 重複登録 */
    private String rsdProp7Value__ = null;
    /** 備考 */
    private String rsdBiko__ = null;
    /** 場所コメント */
    private String rsdInfoPlaCom__ = null;
    /** デフォルト施設画像 */
    private String rsdSisetuImgDefo__ = null;
    /** 場所・地図画像コメント1 */
    private String rsdPlaceImgCom1__ = null;
    /** 場所・地図画像コメント2 */
    private String rsdPlaceImgCom2__ = null;
    /** 場所・地図画像コメント3 */
    private String rsdPlaceImgCom3__ = null;
    /** 場所・地図画像コメント4 */
    private String rsdPlaceImgCom4__ = null;
    /** 場所・地図画像コメント5 */
    private String rsdPlaceImgCom5__ = null;
    /** 場所・地図画像コメント6 */
    private String rsdPlaceImgCom6__ = null;
    /** 場所・地図画像コメント7 */
    private String rsdPlaceImgCom7__ = null;
    /** 場所・地図画像コメント8 */
    private String rsdPlaceImgCom8__ = null;
    /** 場所・地図画像コメント9 */
    private String rsdPlaceImgCom9__ = null;
    /** 場所・地図画像コメント10 */
    private String rsdPlaceImgCom10__ = null;
    /** 施設区分SID */
    private int rsdInfoSisetuKbnSid__;

    /** 施設ID表示区分 */
    private int rsdInfoSisetuIdDspKbn__;
    /** 資産管理番号表示区分 */
    private int rsdInfoSisanKanriDspKbn__;
    /** 表示項目1項目入力欄表示区分 座席数、個数、乗員数、冊数などの数値 */
    private int rsdInfoProp1ValueDspKbn__;
    /** 表示項目2項目入力欄表示区分 喫煙の可不可 */
    private int rsdInfoProp2ValueDspKbn__;
    /** 表示項目3項目入力欄表示区分 社外持出しの可不可 */
    private int rsdInfoProp3ValueDspKbn__;
    /** 表示項目4項目入力欄表示区分 車のナンバー */
    private int rsdInfoProp4ValueDspKbn__;
    /** 表示項目5項目入力欄表示区分 書籍のISBN */
    private int rsdInfoProp5ValueDspKbn__;
    /** 表示項目6項目入力欄表示区分 全施設共通 予約可能期限 */
    private int rsdInfoProp6ValueDspKbn__;
    /** 表示項目7項目入力欄表示区分 全施設共通 重複登録 */
    private int rsdInfoProp7ValueDspKbn__;
    /** 備考表示区分 */
    private int rsdInfoBikoDspKbn__;
    /** 場所コメント表示区分 */
    private int rsdInfoPlaComDspKbn__;
    /** 場所・地図画像コメント1表示区分 */
    private int rsdInfoPlaceImgCom1DspKbn__;
    /** 場所・地図画像コメント2表示区分 */
    private int rsdInfoPlaceImgCom2DspKbn__;
    /** 場所・地図画像コメント3表示区分 */
    private int rsdInfoPlaceImgCom3DspKbn__;
    /** 場所・地図画像コメント4表示区分 */
    private int rsdInfoPlaceImgCom4DspKbn__;
    /** 場所・地図画像コメント5表示区分 */
    private int rsdInfoPlaceImgCom5DspKbn__;
    /** 場所・地図画像コメント6表示区分 */
    private int rsdInfoPlaceImgCom6DspKbn__;
    /** 場所・地図画像コメント7表示区分 */
    private int rsdInfoPlaceImgCom7DspKbn__;
    /** 場所・地図画像コメント8表示区分 */
    private int rsdInfoPlaceImgCom8DspKbn__;
    /** 場所・地図画像コメント9表示区分 */
    private int rsdInfoPlaceImgCom9DspKbn__;
    /** 場所・地図画像コメント10表示区分 */
    private int rsdInfoPlaceImgCom10DspKbn__;
    /** デフォルト施設画像表示区分 */
    private int rsdInfoSisetuImgDspKbn__;

    /** 表示項目1項目名称 */
    private String rsvPropHeaderName1__ = null;
    /** 表示項目2項目名称 */
    private String rsvPropHeaderName2__ = null;
    /** 表示項目3項目名称 */
    private String rsvPropHeaderName3__ = null;
    /** 表示項目4項目名称 */
    private String rsvPropHeaderName4__ = null;
    /** 表示項目5項目名称 */
    private String rsvPropHeaderName5__ = null;
    /** 表示項目6項目名称 */
    private String rsvPropHeaderName6__ = null;
    /** 表示項目7項目名称 */
    private String rsvPropHeaderName7__ = null;

    /** 施設予約の承認 */
    private boolean rsdApprKbnFlg__ = false;
    /** 施設予約の承認 表示フラグ */
    private boolean rsdApprDspFlg__ = false;

    /** 施設アクセス区分 */
    private int racAuth__;

    /** 施設 座席数 乗員数 個数 冊数 */
    private String rsvPropHeaderName8__ = null;

    /**
     * <p>sisetuImgBinSid を取得します。
     * @return sisetuImgBinSid
     */
    public Long getSisetuImgBinSid() {
        return sisetuImgBinSid__;
    }
    /**
     * <p>sisetuImgBinSid をセットします。
     * @param sisetuImgBinSid sisetuImgBinSid
     */
    public void setSisetuImgBinSid(Long sisetuImgBinSid) {
        sisetuImgBinSid__ = sisetuImgBinSid;
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
     * <p>rsdSid__ を取得します。
     * @return rsdSid
     */
    public int getRsdSid() {
        return rsdSid__;
    }
    /**
     * <p>rsdSid__ をセットします。
     * @param rsdSid rsdSid__
     */
    public void setRsdSid(int rsdSid) {
        rsdSid__ = rsdSid;
    }
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
     * <p>rsvWeekList__ を取得します。
     * @return rsvWeekList
     */
    public ArrayList<RsvWeekModel> getRsvWeekList() {
        return rsvWeekList__;
    }
    /**
     * <p>rsvWeekList__ をセットします。
     * @param rsvWeekList rsvWeekList__
     */
    public void setRsvWeekList(ArrayList<RsvWeekModel> rsvWeekList) {
        rsvWeekList__ = rsvWeekList;
    }
    /**
     * <p>rsdBiko を取得します。
     * @return rsdBiko
     */
    public String getRsdBiko() {
        return rsdBiko__;
    }
    /**
     * <p>rsdBiko をセットします。
     * @param rsdBiko rsdBiko
     */
    public void setRsdBiko(String rsdBiko) {
        rsdBiko__ = rsdBiko;
    }
    /**
     * <p>rsdPlaceImgCom1 を取得します。
     * @return rsdPlaceImgCom1
     */
    public String getRsdPlaceImgCom1() {
        return rsdPlaceImgCom1__;
    }
    /**
     * <p>rsdPlaceImgCom1 をセットします。
     * @param rsdPlaceImgCom1 rsdPlaceImgCom1
     */
    public void setRsdPlaceImgCom1(String rsdPlaceImgCom1) {
        rsdPlaceImgCom1__ = rsdPlaceImgCom1;
    }
    /**
     * <p>rsdPlaceImgCom10 を取得します。
     * @return rsdPlaceImgCom10
     */
    public String getRsdPlaceImgCom10() {
        return rsdPlaceImgCom10__;
    }
    /**
     * <p>rsdPlaceImgCom10 をセットします。
     * @param rsdPlaceImgCom10 rsdPlaceImgCom10
     */
    public void setRsdPlaceImgCom10(String rsdPlaceImgCom10) {
        rsdPlaceImgCom10__ = rsdPlaceImgCom10;
    }
    /**
     * <p>rsdPlaceImgCom2 を取得します。
     * @return rsdPlaceImgCom2
     */
    public String getRsdPlaceImgCom2() {
        return rsdPlaceImgCom2__;
    }
    /**
     * <p>rsdPlaceImgCom2 をセットします。
     * @param rsdPlaceImgCom2 rsdPlaceImgCom2
     */
    public void setRsdPlaceImgCom2(String rsdPlaceImgCom2) {
        rsdPlaceImgCom2__ = rsdPlaceImgCom2;
    }
    /**
     * <p>rsdPlaceImgCom3 を取得します。
     * @return rsdPlaceImgCom3
     */
    public String getRsdPlaceImgCom3() {
        return rsdPlaceImgCom3__;
    }
    /**
     * <p>rsdPlaceImgCom3 をセットします。
     * @param rsdPlaceImgCom3 rsdPlaceImgCom3
     */
    public void setRsdPlaceImgCom3(String rsdPlaceImgCom3) {
        rsdPlaceImgCom3__ = rsdPlaceImgCom3;
    }
    /**
     * <p>rsdPlaceImgCom4 を取得します。
     * @return rsdPlaceImgCom4
     */
    public String getRsdPlaceImgCom4() {
        return rsdPlaceImgCom4__;
    }
    /**
     * <p>rsdPlaceImgCom4 をセットします。
     * @param rsdPlaceImgCom4 rsdPlaceImgCom4
     */
    public void setRsdPlaceImgCom4(String rsdPlaceImgCom4) {
        rsdPlaceImgCom4__ = rsdPlaceImgCom4;
    }
    /**
     * <p>rsdPlaceImgCom5 を取得します。
     * @return rsdPlaceImgCom5
     */
    public String getRsdPlaceImgCom5() {
        return rsdPlaceImgCom5__;
    }
    /**
     * <p>rsdPlaceImgCom5 をセットします。
     * @param rsdPlaceImgCom5 rsdPlaceImgCom5
     */
    public void setRsdPlaceImgCom5(String rsdPlaceImgCom5) {
        rsdPlaceImgCom5__ = rsdPlaceImgCom5;
    }
    /**
     * <p>rsdPlaceImgCom6 を取得します。
     * @return rsdPlaceImgCom6
     */
    public String getRsdPlaceImgCom6() {
        return rsdPlaceImgCom6__;
    }
    /**
     * <p>rsdPlaceImgCom6 をセットします。
     * @param rsdPlaceImgCom6 rsdPlaceImgCom6
     */
    public void setRsdPlaceImgCom6(String rsdPlaceImgCom6) {
        rsdPlaceImgCom6__ = rsdPlaceImgCom6;
    }
    /**
     * <p>rsdPlaceImgCom7 を取得します。
     * @return rsdPlaceImgCom7
     */
    public String getRsdPlaceImgCom7() {
        return rsdPlaceImgCom7__;
    }
    /**
     * <p>rsdPlaceImgCom7 をセットします。
     * @param rsdPlaceImgCom7 rsdPlaceImgCom7
     */
    public void setRsdPlaceImgCom7(String rsdPlaceImgCom7) {
        rsdPlaceImgCom7__ = rsdPlaceImgCom7;
    }
    /**
     * <p>rsdPlaceImgCom8 を取得します。
     * @return rsdPlaceImgCom8
     */
    public String getRsdPlaceImgCom8() {
        return rsdPlaceImgCom8__;
    }
    /**
     * <p>rsdPlaceImgCom8 をセットします。
     * @param rsdPlaceImgCom8 rsdPlaceImgCom8
     */
    public void setRsdPlaceImgCom8(String rsdPlaceImgCom8) {
        rsdPlaceImgCom8__ = rsdPlaceImgCom8;
    }
    /**
     * <p>rsdPlaceImgCom9 を取得します。
     * @return rsdPlaceImgCom9
     */
    public String getRsdPlaceImgCom9() {
        return rsdPlaceImgCom9__;
    }
    /**
     * <p>rsdPlaceImgCom9 をセットします。
     * @param rsdPlaceImgCom9 rsdPlaceImgCom9
     */
    public void setRsdPlaceImgCom9(String rsdPlaceImgCom9) {
        rsdPlaceImgCom9__ = rsdPlaceImgCom9;
    }
    /**
     * <p>rsdProp1Value を取得します。
     * @return rsdProp1Value
     */
    public String getRsdProp1Value() {
        return rsdProp1Value__;
    }
    /**
     * <p>rsdProp1Value をセットします。
     * @param rsdProp1Value rsdProp1Value
     */
    public void setRsdProp1Value(String rsdProp1Value) {
        rsdProp1Value__ = rsdProp1Value;
    }
    /**
     * <p>rsdProp2Value を取得します。
     * @return rsdProp2Value
     */
    public String getRsdProp2Value() {
        return rsdProp2Value__;
    }
    /**
     * <p>rsdProp2Value をセットします。
     * @param rsdProp2Value rsdProp2Value
     */
    public void setRsdProp2Value(String rsdProp2Value) {
        rsdProp2Value__ = rsdProp2Value;
    }
    /**
     * <p>rsdProp3Value を取得します。
     * @return rsdProp3Value
     */
    public String getRsdProp3Value() {
        return rsdProp3Value__;
    }
    /**
     * <p>rsdProp3Value をセットします。
     * @param rsdProp3Value rsdProp3Value
     */
    public void setRsdProp3Value(String rsdProp3Value) {
        rsdProp3Value__ = rsdProp3Value;
    }
    /**
     * <p>rsdProp4Value を取得します。
     * @return rsdProp4Value
     */
    public String getRsdProp4Value() {
        return rsdProp4Value__;
    }
    /**
     * <p>rsdProp4Value をセットします。
     * @param rsdProp4Value rsdProp4Value
     */
    public void setRsdProp4Value(String rsdProp4Value) {
        rsdProp4Value__ = rsdProp4Value;
    }
    /**
     * <p>rsdProp5Value を取得します。
     * @return rsdProp5Value
     */
    public String getRsdProp5Value() {
        return rsdProp5Value__;
    }
    /**
     * <p>rsdProp5Value をセットします。
     * @param rsdProp5Value rsdProp5Value
     */
    public void setRsdProp5Value(String rsdProp5Value) {
        rsdProp5Value__ = rsdProp5Value;
    }
    /**
     * <p>rsdProp6Value を取得します。
     * @return rsdProp6Value
     */
    public String getRsdProp6Value() {
        return rsdProp6Value__;
    }
    /**
     * <p>rsdProp6Value をセットします。
     * @param rsdProp6Value rsdProp6Value
     */
    public void setRsdProp6Value(String rsdProp6Value) {
        rsdProp6Value__ = rsdProp6Value;
    }
    /**
     * <p>rsdProp7Value を取得します。
     * @return rsdProp7Value
     */
    public String getRsdProp7Value() {
        return rsdProp7Value__;
    }
    /**
     * <p>rsdProp7Value をセットします。
     * @param rsdProp7Value rsdProp7Value
     */
    public void setRsdProp7Value(String rsdProp7Value) {
        rsdProp7Value__ = rsdProp7Value;
    }
    /**
     * <p>rsdSisanKanri を取得します。
     * @return rsdSisanKanri
     */
    public String getRsdSisanKanri() {
        return rsdSisanKanri__;
    }
    /**
     * <p>rsdSisanKanri をセットします。
     * @param rsdSisanKanri rsdSisanKanri
     */
    public void setRsdSisanKanri(String rsdSisanKanri) {
        rsdSisanKanri__ = rsdSisanKanri;
    }
    /**
     * <p>rsdSisetuId を取得します。
     * @return rsdSisetuId
     */
    public String getRsdSisetuId() {
        return rsdSisetuId__;
    }
    /**
     * <p>rsdSisetuId をセットします。
     * @param rsdSisetuId rsdSisetuId
     */
    public void setRsdSisetuId(String rsdSisetuId) {
        rsdSisetuId__ = rsdSisetuId;
    }
    /**
     * <p>rsdSisetuImgDefo を取得します。
     * @return rsdSisetuImgDefo
     */
    public String getRsdSisetuImgDefo() {
        return rsdSisetuImgDefo__;
    }
    /**
     * <p>rsdSisetuImgDefo をセットします。
     * @param rsdSisetuImgDefo rsdSisetuImgDefo
     */
    public void setRsdSisetuImgDefo(String rsdSisetuImgDefo) {
        rsdSisetuImgDefo__ = rsdSisetuImgDefo;
    }
    /**
     * <p>rsdInfoBikoDspKbn を取得します。
     * @return rsdInfoBikoDspKbn
     */
    public int getRsdInfoBikoDspKbn() {
        return rsdInfoBikoDspKbn__;
    }
    /**
     * <p>rsdInfoBikoDspKbn をセットします。
     * @param rsdInfoBikoDspKbn rsdInfoBikoDspKbn
     */
    public void setRsdInfoBikoDspKbn(int rsdInfoBikoDspKbn) {
        rsdInfoBikoDspKbn__ = rsdInfoBikoDspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom10DspKbn を取得します。
     * @return rsdInfoPlaceImgCom10DspKbn
     */
    public int getRsdInfoPlaceImgCom10DspKbn() {
        return rsdInfoPlaceImgCom10DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom10DspKbn をセットします。
     * @param rsdInfoPlaceImgCom10DspKbn rsdInfoPlaceImgCom10DspKbn
     */
    public void setRsdInfoPlaceImgCom10DspKbn(int rsdInfoPlaceImgCom10DspKbn) {
        rsdInfoPlaceImgCom10DspKbn__ = rsdInfoPlaceImgCom10DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom1DspKbn を取得します。
     * @return rsdInfoPlaceImgCom1DspKbn
     */
    public int getRsdInfoPlaceImgCom1DspKbn() {
        return rsdInfoPlaceImgCom1DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom1DspKbn をセットします。
     * @param rsdInfoPlaceImgCom1DspKbn rsdInfoPlaceImgCom1DspKbn
     */
    public void setRsdInfoPlaceImgCom1DspKbn(int rsdInfoPlaceImgCom1DspKbn) {
        rsdInfoPlaceImgCom1DspKbn__ = rsdInfoPlaceImgCom1DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom2DspKbn を取得します。
     * @return rsdInfoPlaceImgCom2DspKbn
     */
    public int getRsdInfoPlaceImgCom2DspKbn() {
        return rsdInfoPlaceImgCom2DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom2DspKbn をセットします。
     * @param rsdInfoPlaceImgCom2DspKbn rsdInfoPlaceImgCom2DspKbn
     */
    public void setRsdInfoPlaceImgCom2DspKbn(int rsdInfoPlaceImgCom2DspKbn) {
        rsdInfoPlaceImgCom2DspKbn__ = rsdInfoPlaceImgCom2DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom3DspKbn を取得します。
     * @return rsdInfoPlaceImgCom3DspKbn
     */
    public int getRsdInfoPlaceImgCom3DspKbn() {
        return rsdInfoPlaceImgCom3DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom3DspKbn をセットします。
     * @param rsdInfoPlaceImgCom3DspKbn rsdInfoPlaceImgCom3DspKbn
     */
    public void setRsdInfoPlaceImgCom3DspKbn(int rsdInfoPlaceImgCom3DspKbn) {
        rsdInfoPlaceImgCom3DspKbn__ = rsdInfoPlaceImgCom3DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom4DspKbn を取得します。
     * @return rsdInfoPlaceImgCom4DspKbn
     */
    public int getRsdInfoPlaceImgCom4DspKbn() {
        return rsdInfoPlaceImgCom4DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom4DspKbn をセットします。
     * @param rsdInfoPlaceImgCom4DspKbn rsdInfoPlaceImgCom4DspKbn
     */
    public void setRsdInfoPlaceImgCom4DspKbn(int rsdInfoPlaceImgCom4DspKbn) {
        rsdInfoPlaceImgCom4DspKbn__ = rsdInfoPlaceImgCom4DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom5DspKbn を取得します。
     * @return rsdInfoPlaceImgCom5DspKbn
     */
    public int getRsdInfoPlaceImgCom5DspKbn() {
        return rsdInfoPlaceImgCom5DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom5DspKbn をセットします。
     * @param rsdInfoPlaceImgCom5DspKbn rsdInfoPlaceImgCom5DspKbn
     */
    public void setRsdInfoPlaceImgCom5DspKbn(int rsdInfoPlaceImgCom5DspKbn) {
        rsdInfoPlaceImgCom5DspKbn__ = rsdInfoPlaceImgCom5DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom6DspKbn を取得します。
     * @return rsdInfoPlaceImgCom6DspKbn
     */
    public int getRsdInfoPlaceImgCom6DspKbn() {
        return rsdInfoPlaceImgCom6DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom6DspKbn をセットします。
     * @param rsdInfoPlaceImgCom6DspKbn rsdInfoPlaceImgCom6DspKbn
     */
    public void setRsdInfoPlaceImgCom6DspKbn(int rsdInfoPlaceImgCom6DspKbn) {
        rsdInfoPlaceImgCom6DspKbn__ = rsdInfoPlaceImgCom6DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom7DspKbn を取得します。
     * @return rsdInfoPlaceImgCom7DspKbn
     */
    public int getRsdInfoPlaceImgCom7DspKbn() {
        return rsdInfoPlaceImgCom7DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom7DspKbn をセットします。
     * @param rsdInfoPlaceImgCom7DspKbn rsdInfoPlaceImgCom7DspKbn
     */
    public void setRsdInfoPlaceImgCom7DspKbn(int rsdInfoPlaceImgCom7DspKbn) {
        rsdInfoPlaceImgCom7DspKbn__ = rsdInfoPlaceImgCom7DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom8DspKbn を取得します。
     * @return rsdInfoPlaceImgCom8DspKbn
     */
    public int getRsdInfoPlaceImgCom8DspKbn() {
        return rsdInfoPlaceImgCom8DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom8DspKbn をセットします。
     * @param rsdInfoPlaceImgCom8DspKbn rsdInfoPlaceImgCom8DspKbn
     */
    public void setRsdInfoPlaceImgCom8DspKbn(int rsdInfoPlaceImgCom8DspKbn) {
        rsdInfoPlaceImgCom8DspKbn__ = rsdInfoPlaceImgCom8DspKbn;
    }
    /**
     * <p>rsdInfoPlaceImgCom9DspKbn を取得します。
     * @return rsdInfoPlaceImgCom9DspKbn
     */
    public int getRsdInfoPlaceImgCom9DspKbn() {
        return rsdInfoPlaceImgCom9DspKbn__;
    }
    /**
     * <p>rsdInfoPlaceImgCom9DspKbn をセットします。
     * @param rsdInfoPlaceImgCom9DspKbn rsdInfoPlaceImgCom9DspKbn
     */
    public void setRsdInfoPlaceImgCom9DspKbn(int rsdInfoPlaceImgCom9DspKbn) {
        rsdInfoPlaceImgCom9DspKbn__ = rsdInfoPlaceImgCom9DspKbn;
    }
    /**
     * <p>rsdInfoProp1ValueDspKbn を取得します。
     * @return rsdInfoProp1ValueDspKbn
     */
    public int getRsdInfoProp1ValueDspKbn() {
        return rsdInfoProp1ValueDspKbn__;
    }
    /**
     * <p>rsdInfoProp1ValueDspKbn をセットします。
     * @param rsdInfoProp1ValueDspKbn rsdInfoProp1ValueDspKbn
     */
    public void setRsdInfoProp1ValueDspKbn(int rsdInfoProp1ValueDspKbn) {
        rsdInfoProp1ValueDspKbn__ = rsdInfoProp1ValueDspKbn;
    }
    /**
     * <p>rsdInfoProp2ValueDspKbn を取得します。
     * @return rsdInfoProp2ValueDspKbn
     */
    public int getRsdInfoProp2ValueDspKbn() {
        return rsdInfoProp2ValueDspKbn__;
    }
    /**
     * <p>rsdInfoProp2ValueDspKbn をセットします。
     * @param rsdInfoProp2ValueDspKbn rsdInfoProp2ValueDspKbn
     */
    public void setRsdInfoProp2ValueDspKbn(int rsdInfoProp2ValueDspKbn) {
        rsdInfoProp2ValueDspKbn__ = rsdInfoProp2ValueDspKbn;
    }
    /**
     * <p>rsdInfoProp3ValueDspKbn を取得します。
     * @return rsdInfoProp3ValueDspKbn
     */
    public int getRsdInfoProp3ValueDspKbn() {
        return rsdInfoProp3ValueDspKbn__;
    }
    /**
     * <p>rsdInfoProp3ValueDspKbn をセットします。
     * @param rsdInfoProp3ValueDspKbn rsdInfoProp3ValueDspKbn
     */
    public void setRsdInfoProp3ValueDspKbn(int rsdInfoProp3ValueDspKbn) {
        rsdInfoProp3ValueDspKbn__ = rsdInfoProp3ValueDspKbn;
    }
    /**
     * <p>rsdInfoProp4ValueDspKbn を取得します。
     * @return rsdInfoProp4ValueDspKbn
     */
    public int getRsdInfoProp4ValueDspKbn() {
        return rsdInfoProp4ValueDspKbn__;
    }
    /**
     * <p>rsdInfoProp4ValueDspKbn をセットします。
     * @param rsdInfoProp4ValueDspKbn rsdInfoProp4ValueDspKbn
     */
    public void setRsdInfoProp4ValueDspKbn(int rsdInfoProp4ValueDspKbn) {
        rsdInfoProp4ValueDspKbn__ = rsdInfoProp4ValueDspKbn;
    }
    /**
     * <p>rsdInfoProp5ValueDspKbn を取得します。
     * @return rsdInfoProp5ValueDspKbn
     */
    public int getRsdInfoProp5ValueDspKbn() {
        return rsdInfoProp5ValueDspKbn__;
    }
    /**
     * <p>rsdInfoProp5ValueDspKbn をセットします。
     * @param rsdInfoProp5ValueDspKbn rsdInfoProp5ValueDspKbn
     */
    public void setRsdInfoProp5ValueDspKbn(int rsdInfoProp5ValueDspKbn) {
        rsdInfoProp5ValueDspKbn__ = rsdInfoProp5ValueDspKbn;
    }
    /**
     * <p>rsdInfoProp6ValueDspKbn を取得します。
     * @return rsdInfoProp6ValueDspKbn
     */
    public int getRsdInfoProp6ValueDspKbn() {
        return rsdInfoProp6ValueDspKbn__;
    }
    /**
     * <p>rsdInfoProp6ValueDspKbn をセットします。
     * @param rsdInfoProp6ValueDspKbn rsdInfoProp6ValueDspKbn
     */
    public void setRsdInfoProp6ValueDspKbn(int rsdInfoProp6ValueDspKbn) {
        rsdInfoProp6ValueDspKbn__ = rsdInfoProp6ValueDspKbn;
    }
    /**
     * <p>rsdInfoProp7ValueDspKbn を取得します。
     * @return rsdInfoProp7ValueDspKbn
     */
    public int getRsdInfoProp7ValueDspKbn() {
        return rsdInfoProp7ValueDspKbn__;
    }
    /**
     * <p>rsdInfoProp7ValueDspKbn をセットします。
     * @param rsdInfoProp7ValueDspKbn rsdInfoProp7ValueDspKbn
     */
    public void setRsdInfoProp7ValueDspKbn(int rsdInfoProp7ValueDspKbn) {
        rsdInfoProp7ValueDspKbn__ = rsdInfoProp7ValueDspKbn;
    }
    /**
     * <p>rsdInfoSisanKanriDspKbn を取得します。
     * @return rsdInfoSisanKanriDspKbn
     */
    public int getRsdInfoSisanKanriDspKbn() {
        return rsdInfoSisanKanriDspKbn__;
    }
    /**
     * <p>rsdInfoSisanKanriDspKbn をセットします。
     * @param rsdInfoSisanKanriDspKbn rsdInfoSisanKanriDspKbn
     */
    public void setRsdInfoSisanKanriDspKbn(int rsdInfoSisanKanriDspKbn) {
        rsdInfoSisanKanriDspKbn__ = rsdInfoSisanKanriDspKbn;
    }
    /**
     * <p>rsdInfoSisetuIdDspKbn を取得します。
     * @return rsdInfoSisetuIdDspKbn
     */
    public int getRsdInfoSisetuIdDspKbn() {
        return rsdInfoSisetuIdDspKbn__;
    }
    /**
     * <p>rsdInfoSisetuIdDspKbn をセットします。
     * @param rsdInfoSisetuIdDspKbn rsdInfoSisetuIdDspKbn
     */
    public void setRsdInfoSisetuIdDspKbn(int rsdInfoSisetuIdDspKbn) {
        rsdInfoSisetuIdDspKbn__ = rsdInfoSisetuIdDspKbn;
    }
    /**
     * <p>rsdInfoSisetuImgDspKbn を取得します。
     * @return rsdInfoSisetuImgDspKbn
     */
    public int getRsdInfoSisetuImgDspKbn() {
        return rsdInfoSisetuImgDspKbn__;
    }
    /**
     * <p>rsdInfoSisetuImgDspKbn をセットします。
     * @param rsdInfoSisetuImgDspKbn rsdInfoSisetuImgDspKbn
     */
    public void setRsdInfoSisetuImgDspKbn(int rsdInfoSisetuImgDspKbn) {
        rsdInfoSisetuImgDspKbn__ = rsdInfoSisetuImgDspKbn;
    }
    /**
     * <p>rsdInfoSisetuKbnSid を取得します。
     * @return rsdInfoSisetuKbnSid
     */
    public int getRsdInfoSisetuKbnSid() {
        return rsdInfoSisetuKbnSid__;
    }
    /**
     * <p>rsdInfoSisetuKbnSid をセットします。
     * @param rsdInfoSisetuKbnSid rsdInfoSisetuKbnSid
     */
    public void setRsdInfoSisetuKbnSid(int rsdInfoSisetuKbnSid) {
        rsdInfoSisetuKbnSid__ = rsdInfoSisetuKbnSid;
    }
    /**
     * <p>rsvPropHeaderName1 を取得します。
     * @return rsvPropHeaderName1
     */
    public String getRsvPropHeaderName1() {
        return rsvPropHeaderName1__;
    }
    /**
     * <p>rsvPropHeaderName1 をセットします。
     * @param rsvPropHeaderName1 rsvPropHeaderName1
     */
    public void setRsvPropHeaderName1(String rsvPropHeaderName1) {
        rsvPropHeaderName1__ = rsvPropHeaderName1;
    }
    /**
     * <p>rsvPropHeaderName2 を取得します。
     * @return rsvPropHeaderName2
     */
    public String getRsvPropHeaderName2() {
        return rsvPropHeaderName2__;
    }
    /**
     * <p>rsvPropHeaderName2 をセットします。
     * @param rsvPropHeaderName2 rsvPropHeaderName2
     */
    public void setRsvPropHeaderName2(String rsvPropHeaderName2) {
        rsvPropHeaderName2__ = rsvPropHeaderName2;
    }
    /**
     * <p>rsvPropHeaderName3 を取得します。
     * @return rsvPropHeaderName3
     */
    public String getRsvPropHeaderName3() {
        return rsvPropHeaderName3__;
    }
    /**
     * <p>rsvPropHeaderName3 をセットします。
     * @param rsvPropHeaderName3 rsvPropHeaderName3
     */
    public void setRsvPropHeaderName3(String rsvPropHeaderName3) {
        rsvPropHeaderName3__ = rsvPropHeaderName3;
    }
    /**
     * <p>rsvPropHeaderName4 を取得します。
     * @return rsvPropHeaderName4
     */
    public String getRsvPropHeaderName4() {
        return rsvPropHeaderName4__;
    }
    /**
     * <p>rsvPropHeaderName4 をセットします。
     * @param rsvPropHeaderName4 rsvPropHeaderName4
     */
    public void setRsvPropHeaderName4(String rsvPropHeaderName4) {
        rsvPropHeaderName4__ = rsvPropHeaderName4;
    }
    /**
     * <p>rsvPropHeaderName5 を取得します。
     * @return rsvPropHeaderName5
     */
    public String getRsvPropHeaderName5() {
        return rsvPropHeaderName5__;
    }
    /**
     * <p>rsvPropHeaderName5 をセットします。
     * @param rsvPropHeaderName5 rsvPropHeaderName5
     */
    public void setRsvPropHeaderName5(String rsvPropHeaderName5) {
        rsvPropHeaderName5__ = rsvPropHeaderName5;
    }
    /**
     * <p>rsvPropHeaderName6 を取得します。
     * @return rsvPropHeaderName6
     */
    public String getRsvPropHeaderName6() {
        return rsvPropHeaderName6__;
    }
    /**
     * <p>rsvPropHeaderName6 をセットします。
     * @param rsvPropHeaderName6 rsvPropHeaderName6
     */
    public void setRsvPropHeaderName6(String rsvPropHeaderName6) {
        rsvPropHeaderName6__ = rsvPropHeaderName6;
    }
    /**
     * <p>rsvPropHeaderName7 を取得します。
     * @return rsvPropHeaderName7
     */
    public String getRsvPropHeaderName7() {
        return rsvPropHeaderName7__;
    }
    /**
     * <p>rsvPropHeaderName7 をセットします。
     * @param rsvPropHeaderName7 rsvPropHeaderName7
     */
    public void setRsvPropHeaderName7(String rsvPropHeaderName7) {
        rsvPropHeaderName7__ = rsvPropHeaderName7;
    }
    /**
     * <p>rsdInfoPlaCom を取得します。
     * @return rsdInfoPlaCom
     */
    public String getRsdInfoPlaCom() {
        return rsdInfoPlaCom__;
    }
    /**
     * <p>rsdInfoPlaCom をセットします。
     * @param rsdInfoPlaCom rsdInfoPlaCom
     */
    public void setRsdInfoPlaCom(String rsdInfoPlaCom) {
        rsdInfoPlaCom__ = rsdInfoPlaCom;
    }
    /**
     * <p>rsdInfoPlaComDspKbn を取得します。
     * @return rsdInfoPlaComDspKbn
     */
    public int getRsdInfoPlaComDspKbn() {
        return rsdInfoPlaComDspKbn__;
    }
    /**
     * <p>rsdInfoPlaComDspKbn をセットします。
     * @param rsdInfoPlaComDspKbn rsdInfoPlaComDspKbn
     */
    public void setRsdInfoPlaComDspKbn(int rsdInfoPlaComDspKbn) {
        rsdInfoPlaComDspKbn__ = rsdInfoPlaComDspKbn;
    }
    /**
     * <p>racAuth を取得します。
     * @return racAuth
     */
    public int getRacAuth() {
        return racAuth__;
    }
    /**
     * <p>racAuth をセットします。
     * @param racAuth racAuth
     */
    public void setRacAuth(int racAuth) {
        racAuth__ = racAuth;
    }
    /**
     * @return rsvPropHeaderName8
     */
    public String getRsvPropHeaderName8() {
        return rsvPropHeaderName8__;
    }
    /**
     * @param rsvPropHeaderName8 セットする rsvPropHeaderName8
     */
    public void setRsvPropHeaderName8(String rsvPropHeaderName8) {
        rsvPropHeaderName8__ = rsvPropHeaderName8;
    }
    /**
     * <p>rsdApprDspFlg を取得します。
     * @return rsdApprDspFlg
     */
    public boolean isRsdApprDspFlg() {
        return rsdApprDspFlg__;
    }
    /**
     * <p>rsdApprDspFlg をセットします。
     * @param rsdApprDspFlg rsdApprDspFlg
     */
    public void setRsdApprDspFlg(boolean rsdApprDspFlg) {
        rsdApprDspFlg__ = rsdApprDspFlg;
    }
    /**
     * <p>rsdApprKbnFlg を取得します。
     * @return rsdApprKbnFlg
     */
    public boolean isRsdApprKbnFlg() {
        return rsdApprKbnFlg__;
    }
    /**
     * <p>rsdApprKbnFlg をセットします。
     * @param rsdApprKbnFlg rsdApprKbnFlg
     */
    public void setRsdApprKbnFlg(boolean rsdApprKbnFlg) {
        rsdApprKbnFlg__ = rsdApprKbnFlg;
    }
}
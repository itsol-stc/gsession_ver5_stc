package jp.groupsession.v2.rsv;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 コンバート前施設情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvWeekModelBeforConv extends AbstractModel {

    /** 施設グループSID */
    private int rsgSid__;
    /** 施設SID */
    private int rsdSid__;
    /** 施設名称 */
    private String rsdName__;
    /** 予約SID */
    private int rsySid__;
    /** 利用目的 */
    private String rsyMok__;
    /** 内容 */
    private String rsyNaiyo__;
    /** 利用開始 */
    private UDate rsyFrDate__;
    /** 利用終了 */
    private UDate rsyToDate__;
    /** 承認状況 */
    private int rsyApprStatus__ = GSConstReserve.RSY_APPR_STATUS_NORMAL;
    /** 承認区分 */
    private int rsyApprKbn__ = GSConstReserve.RSY_APPR_KBN_NOSET;
    /** 公開区分 */
    private int rsyPublic__ = GSConstReserve.PUBLIC_KBN_ALL;
    /** 登録者 姓 */
    private String usiSei__;
    /** 登録者 名 */
    private String usiMei__;
    /** 登録者 有効フラグ */
    private int usrUkoFlg__;
    /** 登録者 状態区分 */
    private int usrJkbn__;

    //**************************施設情報表示****************
    /** 施設区分SID */
    private int rsdInfoSisetuKbnSid__;
    /** 施設ID */
    private String rsdInfoSisetuId__ = null;
    /** 資産管理番号 */
    private String rsdInfoSisanKanri__ = null;
    /** 表示項目1項目入力欄 座席数、個数、乗員数、冊数などの数値 */
    private String rsdInfoProp1Value__ = null;
    /** 表示項目2項目入力欄 喫煙の可不可 */
    private String rsdInfoProp2Value__ = null;
    /** 表示項目3項目入力欄 社外持出しの可不可 */
    private String rsdInfoProp3Value__ = null;
    /** 表示項目4項目入力欄 車のナンバー */
    private String rsdInfoProp4Value__ = null;
    /** 表示項目5項目入力欄 書籍のISBN */
    private String rsdInfoProp5Value__ = null;
    /** 表示項目6項目入力欄 全施設共通 予約可能期限 */
    private String rsdInfoProp6Value__ = null;
    /** 表示項目7項目入力欄 全施設共通 重複登録 */
    private String rsdInfoProp7Value__ = null;
    /** 備考 */
    private String rsdInfoBiko__ = null;
    /** 場所コメント */
    private String rsdInfoPlaCom__ = null;
    /** 場所・地図画像コメント1 */
    private String rsdInfoPlaceImgCom1__ = null;
    /** 場所・地図画像コメント2 */
    private String rsdInfoPlaceImgCom2__ = null;
    /** 場所・地図画像コメント3 */
    private String rsdInfoPlaceImgCom3__ = null;
    /** 場所・地図画像コメント4 */
    private String rsdInfoPlaceImgCom4__ = null;
    /** 場所・地図画像コメント5 */
    private String rsdInfoPlaceImgCom5__ = null;
    /** 場所・地図画像コメント6 */
    private String rsdInfoPlaceImgCom6__ = null;
    /** 場所・地図画像コメント7 */
    private String rsdInfoPlaceImgCom7__ = null;
    /** 場所・地図画像コメント8 */
    private String rsdInfoPlaceImgCom8__ = null;
    /** 場所・地図画像コメント9 */
    private String rsdInfoPlaceImgCom9__ = null;
    /** 場所・地図画像コメント10 */
    private String rsdInfoPlaceImgCom10__ = null;
    /** 施設予約の承認 */
    private int rsdApprKbn__ = GSConstReserve.RSD_APPR_KBN_NOSET;

    //*********************表示区分****************
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
    /** 施設予約の承認表示区分 */
    private int rsdApprDspKbn__;

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
     * <p>rsyFrDate__ を取得します。
     * @return rsyFrDate
     */
    public UDate getRsyFrDate() {
        return rsyFrDate__;
    }
    /**
     * <p>rsyFrDate__ をセットします。
     * @param rsyFrDate rsyFrDate__
     */
    public void setRsyFrDate(UDate rsyFrDate) {
        rsyFrDate__ = rsyFrDate;
    }
    /**
     * <p>rsyMok__ を取得します。
     * @return rsyMok
     */
    public String getRsyMok() {
        return rsyMok__;
    }
    /**
     * <p>rsyMok__ をセットします。
     * @param rsyMok rsyMok__
     */
    public void setRsyMok(String rsyMok) {
        rsyMok__ = rsyMok;
    }
    /**
     * <p>rsyToDate__ を取得します。
     * @return rsyToDate
     */
    public UDate getRsyToDate() {
        return rsyToDate__;
    }
    /**
     * <p>rsyToDate__ をセットします。
     * @param rsyToDate rsyToDate__
     */
    public void setRsyToDate(UDate rsyToDate) {
        rsyToDate__ = rsyToDate;
    }
    /**
     * <p>usiMei__ を取得します。
     * @return usiMei
     */
    public String getUsiMei() {
        return usiMei__;
    }
    /**
     * <p>usiMei__ をセットします。
     * @param usiMei usiMei__
     */
    public void setUsiMei(String usiMei) {
        usiMei__ = usiMei;
    }
    /**
     * <p>usiSei__ を取得します。
     * @return usiSei
     */
    public String getUsiSei() {
        return usiSei__;
    }
    /**
     * <p>usiSei__ をセットします。
     * @param usiSei usiSei__
     */
    public void setUsiSei(String usiSei) {
        usiSei__ = usiSei;
    }
    /**
     * <p>rsySid__ を取得します。
     * @return rsySid
     */
    public int getRsySid() {
        return rsySid__;
    }
    /**
     * <p>rsySid__ をセットします。
     * @param rsySid rsySid__
     */
    public void setRsySid(int rsySid) {
        rsySid__ = rsySid;
    }
    /**
     * <p>rsyNaiyo を取得します。
     * @return rsyNaiyo
     */
    public String getRsyNaiyo() {
        return rsyNaiyo__;
    }
    /**
     * <p>rsyNaiyo をセットします。
     * @param rsyNaiyo rsyNaiyo
     */
    public void setRsyNaiyo(String rsyNaiyo) {
        rsyNaiyo__ = rsyNaiyo;
    }
    /**
     * <p>rsdInfoBiko を取得します。
     * @return rsdInfoBiko
     */
    public String getRsdInfoBiko() {
        return rsdInfoBiko__;
    }
    /**
     * <p>rsdInfoBiko をセットします。
     * @param rsdInfoBiko rsdInfoBiko
     */
    public void setRsdInfoBiko(String rsdInfoBiko) {
        rsdInfoBiko__ = rsdInfoBiko;
    }
    /**
     * <p>rsdInfoPlaceImgCom1 を取得します。
     * @return rsdInfoPlaceImgCom1
     */
    public String getRsdInfoPlaceImgCom1() {
        return rsdInfoPlaceImgCom1__;
    }
    /**
     * <p>rsdInfoPlaceImgCom1 をセットします。
     * @param rsdInfoPlaceImgCom1 rsdInfoPlaceImgCom1
     */
    public void setRsdInfoPlaceImgCom1(String rsdInfoPlaceImgCom1) {
        rsdInfoPlaceImgCom1__ = rsdInfoPlaceImgCom1;
    }
    /**
     * <p>rsdInfoPlaceImgCom10 を取得します。
     * @return rsdInfoPlaceImgCom10
     */
    public String getRsdInfoPlaceImgCom10() {
        return rsdInfoPlaceImgCom10__;
    }
    /**
     * <p>rsdInfoPlaceImgCom10 をセットします。
     * @param rsdInfoPlaceImgCom10 rsdInfoPlaceImgCom10
     */
    public void setRsdInfoPlaceImgCom10(String rsdInfoPlaceImgCom10) {
        rsdInfoPlaceImgCom10__ = rsdInfoPlaceImgCom10;
    }
    /**
     * <p>rsdInfoPlaceImgCom2 を取得します。
     * @return rsdInfoPlaceImgCom2
     */
    public String getRsdInfoPlaceImgCom2() {
        return rsdInfoPlaceImgCom2__;
    }
    /**
     * <p>rsdInfoPlaceImgCom2 をセットします。
     * @param rsdInfoPlaceImgCom2 rsdInfoPlaceImgCom2
     */
    public void setRsdInfoPlaceImgCom2(String rsdInfoPlaceImgCom2) {
        rsdInfoPlaceImgCom2__ = rsdInfoPlaceImgCom2;
    }
    /**
     * <p>rsdInfoPlaceImgCom3 を取得します。
     * @return rsdInfoPlaceImgCom3
     */
    public String getRsdInfoPlaceImgCom3() {
        return rsdInfoPlaceImgCom3__;
    }
    /**
     * <p>rsdInfoPlaceImgCom3 をセットします。
     * @param rsdInfoPlaceImgCom3 rsdInfoPlaceImgCom3
     */
    public void setRsdInfoPlaceImgCom3(String rsdInfoPlaceImgCom3) {
        rsdInfoPlaceImgCom3__ = rsdInfoPlaceImgCom3;
    }
    /**
     * <p>rsdInfoPlaceImgCom4 を取得します。
     * @return rsdInfoPlaceImgCom4
     */
    public String getRsdInfoPlaceImgCom4() {
        return rsdInfoPlaceImgCom4__;
    }
    /**
     * <p>rsdInfoPlaceImgCom4 をセットします。
     * @param rsdInfoPlaceImgCom4 rsdInfoPlaceImgCom4
     */
    public void setRsdInfoPlaceImgCom4(String rsdInfoPlaceImgCom4) {
        rsdInfoPlaceImgCom4__ = rsdInfoPlaceImgCom4;
    }
    /**
     * <p>rsdInfoPlaceImgCom5 を取得します。
     * @return rsdInfoPlaceImgCom5
     */
    public String getRsdInfoPlaceImgCom5() {
        return rsdInfoPlaceImgCom5__;
    }
    /**
     * <p>rsdInfoPlaceImgCom5 をセットします。
     * @param rsdInfoPlaceImgCom5 rsdInfoPlaceImgCom5
     */
    public void setRsdInfoPlaceImgCom5(String rsdInfoPlaceImgCom5) {
        rsdInfoPlaceImgCom5__ = rsdInfoPlaceImgCom5;
    }
    /**
     * <p>rsdInfoPlaceImgCom6 を取得します。
     * @return rsdInfoPlaceImgCom6
     */
    public String getRsdInfoPlaceImgCom6() {
        return rsdInfoPlaceImgCom6__;
    }
    /**
     * <p>rsdInfoPlaceImgCom6 をセットします。
     * @param rsdInfoPlaceImgCom6 rsdInfoPlaceImgCom6
     */
    public void setRsdInfoPlaceImgCom6(String rsdInfoPlaceImgCom6) {
        rsdInfoPlaceImgCom6__ = rsdInfoPlaceImgCom6;
    }
    /**
     * <p>rsdInfoPlaceImgCom7 を取得します。
     * @return rsdInfoPlaceImgCom7
     */
    public String getRsdInfoPlaceImgCom7() {
        return rsdInfoPlaceImgCom7__;
    }
    /**
     * <p>rsdInfoPlaceImgCom7 をセットします。
     * @param rsdInfoPlaceImgCom7 rsdInfoPlaceImgCom7
     */
    public void setRsdInfoPlaceImgCom7(String rsdInfoPlaceImgCom7) {
        rsdInfoPlaceImgCom7__ = rsdInfoPlaceImgCom7;
    }
    /**
     * <p>rsdInfoPlaceImgCom8 を取得します。
     * @return rsdInfoPlaceImgCom8
     */
    public String getRsdInfoPlaceImgCom8() {
        return rsdInfoPlaceImgCom8__;
    }
    /**
     * <p>rsdInfoPlaceImgCom8 をセットします。
     * @param rsdInfoPlaceImgCom8 rsdInfoPlaceImgCom8
     */
    public void setRsdInfoPlaceImgCom8(String rsdInfoPlaceImgCom8) {
        rsdInfoPlaceImgCom8__ = rsdInfoPlaceImgCom8;
    }
    /**
     * <p>rsdInfoPlaceImgCom9 を取得します。
     * @return rsdInfoPlaceImgCom9
     */
    public String getRsdInfoPlaceImgCom9() {
        return rsdInfoPlaceImgCom9__;
    }
    /**
     * <p>rsdInfoPlaceImgCom9 をセットします。
     * @param rsdInfoPlaceImgCom9 rsdInfoPlaceImgCom9
     */
    public void setRsdInfoPlaceImgCom9(String rsdInfoPlaceImgCom9) {
        rsdInfoPlaceImgCom9__ = rsdInfoPlaceImgCom9;
    }
    /**
     * <p>rsdInfoProp1Value を取得します。
     * @return rsdInfoProp1Value
     */
    public String getRsdInfoProp1Value() {
        return rsdInfoProp1Value__;
    }
    /**
     * <p>rsdInfoProp1Value をセットします。
     * @param rsdInfoProp1Value rsdInfoProp1Value
     */
    public void setRsdInfoProp1Value(String rsdInfoProp1Value) {
        rsdInfoProp1Value__ = rsdInfoProp1Value;
    }
    /**
     * <p>rsdInfoProp2Value を取得します。
     * @return rsdInfoProp2Value
     */
    public String getRsdInfoProp2Value() {
        return rsdInfoProp2Value__;
    }
    /**
     * <p>rsdInfoProp2Value をセットします。
     * @param rsdInfoProp2Value rsdInfoProp2Value
     */
    public void setRsdInfoProp2Value(String rsdInfoProp2Value) {
        rsdInfoProp2Value__ = rsdInfoProp2Value;
    }
    /**
     * <p>rsdInfoProp3Value を取得します。
     * @return rsdInfoProp3Value
     */
    public String getRsdInfoProp3Value() {
        return rsdInfoProp3Value__;
    }
    /**
     * <p>rsdInfoProp3Value をセットします。
     * @param rsdInfoProp3Value rsdInfoProp3Value
     */
    public void setRsdInfoProp3Value(String rsdInfoProp3Value) {
        rsdInfoProp3Value__ = rsdInfoProp3Value;
    }
    /**
     * <p>rsdInfoProp4Value を取得します。
     * @return rsdInfoProp4Value
     */
    public String getRsdInfoProp4Value() {
        return rsdInfoProp4Value__;
    }
    /**
     * <p>rsdInfoProp4Value をセットします。
     * @param rsdInfoProp4Value rsdInfoProp4Value
     */
    public void setRsdInfoProp4Value(String rsdInfoProp4Value) {
        rsdInfoProp4Value__ = rsdInfoProp4Value;
    }
    /**
     * <p>rsdInfoProp5Value を取得します。
     * @return rsdInfoProp5Value
     */
    public String getRsdInfoProp5Value() {
        return rsdInfoProp5Value__;
    }
    /**
     * <p>rsdInfoProp5Value をセットします。
     * @param rsdInfoProp5Value rsdInfoProp5Value
     */
    public void setRsdInfoProp5Value(String rsdInfoProp5Value) {
        rsdInfoProp5Value__ = rsdInfoProp5Value;
    }
    /**
     * <p>rsdInfoProp6Value を取得します。
     * @return rsdInfoProp6Value
     */
    public String getRsdInfoProp6Value() {
        return rsdInfoProp6Value__;
    }
    /**
     * <p>rsdInfoProp6Value をセットします。
     * @param rsdInfoProp6Value rsdInfoProp6Value
     */
    public void setRsdInfoProp6Value(String rsdInfoProp6Value) {
        rsdInfoProp6Value__ = rsdInfoProp6Value;
    }
    /**
     * <p>rsdInfoProp7Value を取得します。
     * @return rsdInfoProp7Value
     */
    public String getRsdInfoProp7Value() {
        return rsdInfoProp7Value__;
    }
    /**
     * <p>rsdInfoProp7Value をセットします。
     * @param rsdInfoProp7Value rsdInfoProp7Value
     */
    public void setRsdInfoProp7Value(String rsdInfoProp7Value) {
        rsdInfoProp7Value__ = rsdInfoProp7Value;
    }
    /**
     * <p>rsdInfoSisanKanri を取得します。
     * @return rsdInfoSisanKanri
     */
    public String getRsdInfoSisanKanri() {
        return rsdInfoSisanKanri__;
    }
    /**
     * <p>rsdInfoSisanKanri をセットします。
     * @param rsdInfoSisanKanri rsdInfoSisanKanri
     */
    public void setRsdInfoSisanKanri(String rsdInfoSisanKanri) {
        rsdInfoSisanKanri__ = rsdInfoSisanKanri;
    }
    /**
     * <p>rsdInfoSisetuId を取得します。
     * @return rsdInfoSisetuId
     */
    public String getRsdInfoSisetuId() {
        return rsdInfoSisetuId__;
    }
    /**
     * <p>rsdInfoSisetuId をセットします。
     * @param rsdInfoSisetuId rsdInfoSisetuId
     */
    public void setRsdInfoSisetuId(String rsdInfoSisetuId) {
        rsdInfoSisetuId__ = rsdInfoSisetuId;
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
     * <p>rsyApprStatus を取得します。
     * @return rsyApprStatus
     */
    public int getRsyApprStatus() {
        return rsyApprStatus__;
    }
    /**
     * <p>rsyApprStatus をセットします。
     * @param rsyApprStatus rsyApprStatus
     */
    public void setRsyApprStatus(int rsyApprStatus) {
        rsyApprStatus__ = rsyApprStatus;
    }
    /**
     * <p>rsyApprKbn を取得します。
     * @return rsyApprKbn
     */
    public int getRsyApprKbn() {
        return rsyApprKbn__;
    }
    /**
     * <p>rsyApprKbn をセットします。
     * @param rsyApprKbn rsyApprKbn
     */
    public void setRsyApprKbn(int rsyApprKbn) {
        rsyApprKbn__ = rsyApprKbn;
    }
    /**
     * <p>rsyPublic を取得します。
     * @return rsyPublic
     * @see jp.groupsession.v2.rsv.RsvWeekModelBeforConv#rsyPublic__
     */
    public int getRsyPublic() {
        return rsyPublic__;
    }
    /**
     * <p>rsyPublic をセットします。
     * @param rsyPublic rsyPublic
     * @see jp.groupsession.v2.rsv.RsvWeekModelBeforConv#rsyPublic__
     */
    public void setRsyPublic(int rsyPublic) {
        rsyPublic__ = rsyPublic;
    }
    /**
     * <p>rsdApprDspKbn を取得します。
     * @return rsdApprDspKbn
     */
    public int getRsdApprDspKbn() {
        return rsdApprDspKbn__;
    }
    /**
     * <p>rsdApprDspKbn をセットします。
     * @param rsdApprDspKbn rsdApprDspKbn
     */
    public void setRsdApprDspKbn(int rsdApprDspKbn) {
        rsdApprDspKbn__ = rsdApprDspKbn;
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
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
    /**
     * <p>usrJkbn を取得します。
     * @return usrJkbn
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }
    /**
     * <p>usrJkbn をセットします。
     * @param usrJkbn usrJkbn
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }
}
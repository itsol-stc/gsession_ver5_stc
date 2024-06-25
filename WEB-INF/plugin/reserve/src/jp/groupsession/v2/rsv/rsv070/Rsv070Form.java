package jp.groupsession.v2.rsv.rsv070;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.rsv.AbstractReserveForm;
import jp.groupsession.v2.rsv.rsv090.model.Rsv090BinfModel;

/**
 * <br>[機  能] 施設予約 施設情報画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv070Form extends AbstractReserveForm {

    /** 処理対象施設SID */
    private int rsv070RsdSid__ = -1;
    /** 処理モード */
    private int rsv070RsdMode__ = 0;

    /** 表示項目1項目名称 */
    private String rsv070PropHeaderName1__ = null;
    /** 表示項目2項目名称 */
    private String rsv070PropHeaderName2__ = null;
    /** 表示項目3項目名称 */
    private String rsv070PropHeaderName3__ = null;
    /** 表示項目4項目名称 */
    private String rsv070PropHeaderName4__ = null;
    /** 表示項目5項目名称 */
    private String rsv070PropHeaderName5__ = null;
    /** 表示項目6項目名称 */
    private String rsv070PropHeaderName6__ = null;
    /** 表示項目7項目名称 */
    private String rsv070PropHeaderName7__ = null;
    /** 所属グループ */
    private String rsv070GrpName__ = null;
    /** 施設区分名 */
    private String rsv070SisetuKbnName__ = null;
    /** 施設ID */
    private String rsv070SisetuId__ = null;
    /** 施設名 */
    private String rsv070SisetuName__ = null;
    /** 資産管理番号 */
    private String rsv070SisanKanri__ = null;
    /** 可変項目1 */
    private String rsv070Prop1Value__ = null;
    /** 可変項目2 */
    private String rsv070Prop2Value__ = null;
    /** 可変項目3 */
    private String rsv070Prop3Value__ = null;
    /** 可変項目4 */
    private String rsv070Prop4Value__ = null;
    /** 可変項目5 */
    private String rsv070Prop5Value__ = null;
    /** 可変項目6 */
    private String rsv070Prop6Value__ = null;
    /** 可変項目7 */
    private String rsv070Prop7Value__ = null;
    /** 備考 */
    private String rsv070Biko__ = null;

    /** 場所コメント */
    private String rsv070PlaComment__ = null;
    /** 場所/地図コメント１ */
    private String rsv070PlaceFileComment1__ = null;
    /** 場所/地図コメント２ */
    private String rsv070PlaceFileComment2__ = null;
    /** 場所/地図コメント３ */
    private String rsv070PlaceFileComment3__ = null;
    /** 場所/地図コメント４ */
    private String rsv070PlaceFileComment4__ = null;
    /** 場所/地図コメント５ */
    private String rsv070PlaceFileComment5__ = null;
    /** 場所/地図コメント６ */
    private String rsv070PlaceFileComment6__ = null;
    /** 場所/地図コメント７ */
    private String rsv070PlaceFileComment7__ = null;
    /** 場所/地図コメント８ */
    private String rsv070PlaceFileComment8__ = null;
    /** 場所/地図コメント９ */
    private String rsv070PlaceFileComment9__ = null;
    /** 場所/地図コメント１０ */
    private String rsv070PlaceFileComment10__ = null;

    /** 施設予約の承認 */
    private boolean rsv070apprKbnFlg__ = false;
    /** 施設予約の承認 表示フラグ */
    private boolean rsv070apprDspFlg__ = false;

    /** 予約情報一覧 */
    private ArrayList<String> rsv070RsvList__ = null;

    /** 施設/設備画像バイナリデータ */
    private List<Rsv090BinfModel> rsv070SisetuBinDataList__ = null;
    /** 場所/地図画像バイナリデータ */
    private List<Rsv090BinfModel> rsv070PlaceBinDataList__ = null;

    /** 管理者フラグ */
    private boolean rsv070AdmFlg__;

    /**
     * <p>rsv070AdmFlg を取得します。
     * @return rsv070AdmFlg
     */
    public boolean isRsv070AdmFlg() {
        return rsv070AdmFlg__;
    }
    /**
     * <p>rsv070AdmFlg をセットします。
     * @param rsv070AdmFlg rsv070AdmFlg
     */
    public void setRsv070AdmFlg(boolean rsv070AdmFlg) {
        rsv070AdmFlg__ = rsv070AdmFlg;
    }
    /**
     * <p>rsv070PlaceBinDataList を取得します。
     * @return rsv070PlaceBinDataList
     */
    public List<Rsv090BinfModel> getRsv070PlaceBinDataList() {
        return rsv070PlaceBinDataList__;
    }
    /**
     * <p>rsv070PlaceBinDataList をセットします。
     * @param rsv070PlaceBinDataList rsv070PlaceBinDataList
     */
    public void setRsv070PlaceBinDataList(List<Rsv090BinfModel> rsv070PlaceBinDataList) {
        rsv070PlaceBinDataList__ = rsv070PlaceBinDataList;
    }
    /**
     * <p>rsv070SisetuBinDataList を取得します。
     * @return rsv070SisetuBinDataList
     */
    public List<Rsv090BinfModel> getRsv070SisetuBinDataList() {
        return rsv070SisetuBinDataList__;
    }
    /**
     * <p>rsv070SisetuBinDataList をセットします。
     * @param rsv070SisetuBinDataList rsv070SisetuBinDataList
     */
    public void setRsv070SisetuBinDataList(
            List<Rsv090BinfModel> rsv070SisetuBinDataList) {
        rsv070SisetuBinDataList__ = rsv070SisetuBinDataList;
    }
    /**
     * <p>rsv070RsvList を取得します。
     * @return rsv070RsvList
     */
    public ArrayList<String> getRsv070RsvList() {
        return rsv070RsvList__;
    }
    /**
     * <p>rsv070RsvList をセットします。
     * @param rsv070RsvList rsv070RsvList
     */
    public void setRsv070RsvList(ArrayList<String> rsv070RsvList) {
        rsv070RsvList__ = rsv070RsvList;
    }
    /**
     * <p>rsv070RsdMode を取得します。
     * @return rsv070RsdMode
     */
    public int getRsv070RsdMode() {
        return rsv070RsdMode__;
    }
    /**
     * <p>rsv070RsdMode をセットします。
     * @param rsv070RsdMode rsv070RsdMode
     */
    public void setRsv070RsdMode(int rsv070RsdMode) {
        rsv070RsdMode__ = rsv070RsdMode;
    }
    /**
     * <p>rsv070RsdSid__ を取得します。
     * @return rsv070RsdSid
     */
    public int getRsv070RsdSid() {
        return rsv070RsdSid__;
    }
    /**
     * <p>rsv070RsdSid__ をセットします。
     * @param rsv070RsdSid rsv070RsdSid__
     */
    public void setRsv070RsdSid(int rsv070RsdSid) {
        rsv070RsdSid__ = rsv070RsdSid;
    }
    /**
     * <p>rsv070PropHeaderName1__ を取得します。
     * @return rsv070PropHeaderName1
     */
    public String getRsv070PropHeaderName1() {
        return rsv070PropHeaderName1__;
    }
    /**
     * <p>rsv070PropHeaderName1__ をセットします。
     * @param rsv070PropHeaderName1 rsv070PropHeaderName1__
     */
    public void setRsv070PropHeaderName1(String rsv070PropHeaderName1) {
        rsv070PropHeaderName1__ = rsv070PropHeaderName1;
    }
    /**
     * <p>rsv070PropHeaderName2__ を取得します。
     * @return rsv070PropHeaderName2
     */
    public String getRsv070PropHeaderName2() {
        return rsv070PropHeaderName2__;
    }
    /**
     * <p>rsv070PropHeaderName2__ をセットします。
     * @param rsv070PropHeaderName2 rsv070PropHeaderName2__
     */
    public void setRsv070PropHeaderName2(String rsv070PropHeaderName2) {
        rsv070PropHeaderName2__ = rsv070PropHeaderName2;
    }
    /**
     * <p>rsv070PropHeaderName3__ を取得します。
     * @return rsv070PropHeaderName3
     */
    public String getRsv070PropHeaderName3() {
        return rsv070PropHeaderName3__;
    }
    /**
     * <p>rsv070PropHeaderName3__ をセットします。
     * @param rsv070PropHeaderName3 rsv070PropHeaderName3__
     */
    public void setRsv070PropHeaderName3(String rsv070PropHeaderName3) {
        rsv070PropHeaderName3__ = rsv070PropHeaderName3;
    }
    /**
     * <p>rsv070Biko__ を取得します。
     * @return rsv070Biko
     */
    public String getRsv070Biko() {
        return rsv070Biko__;
    }
    /**
     * <p>rsv070Biko__ をセットします。
     * @param rsv070Biko rsv070Biko__
     */
    public void setRsv070Biko(String rsv070Biko) {
        rsv070Biko__ = rsv070Biko;
    }
    /**
     * <p>rsv070GrpName__ を取得します。
     * @return rsv070GrpName
     */
    public String getRsv070GrpName() {
        return rsv070GrpName__;
    }
    /**
     * <p>rsv070GrpName__ をセットします。
     * @param rsv070GrpName rsv070GrpName__
     */
    public void setRsv070GrpName(String rsv070GrpName) {
        rsv070GrpName__ = rsv070GrpName;
    }
    /**
     * <p>rsv070Prop1Value__ を取得します。
     * @return rsv070Prop1Value
     */
    public String getRsv070Prop1Value() {
        return rsv070Prop1Value__;
    }
    /**
     * <p>rsv070Prop1Value__ をセットします。
     * @param rsv070Prop1Value rsv070Prop1Value__
     */
    public void setRsv070Prop1Value(String rsv070Prop1Value) {
        rsv070Prop1Value__ = rsv070Prop1Value;
    }
    /**
     * <p>rsv070Prop2Value__ を取得します。
     * @return rsv070Prop2Value
     */
    public String getRsv070Prop2Value() {
        return rsv070Prop2Value__;
    }
    /**
     * <p>rsv070Prop2Value__ をセットします。
     * @param rsv070Prop2Value rsv070Prop2Value__
     */
    public void setRsv070Prop2Value(String rsv070Prop2Value) {
        rsv070Prop2Value__ = rsv070Prop2Value;
    }
    /**
     * <p>rsv070Prop3Value__ を取得します。
     * @return rsv070Prop3Value
     */
    public String getRsv070Prop3Value() {
        return rsv070Prop3Value__;
    }
    /**
     * <p>rsv070Prop3Value__ をセットします。
     * @param rsv070Prop3Value rsv070Prop3Value__
     */
    public void setRsv070Prop3Value(String rsv070Prop3Value) {
        rsv070Prop3Value__ = rsv070Prop3Value;
    }
    /**
     * <p>rsv070SisanKanri__ を取得します。
     * @return rsv070SisanKanri
     */
    public String getRsv070SisanKanri() {
        return rsv070SisanKanri__;
    }
    /**
     * <p>rsv070SisanKanri__ をセットします。
     * @param rsv070SisanKanri rsv070SisanKanri__
     */
    public void setRsv070SisanKanri(String rsv070SisanKanri) {
        rsv070SisanKanri__ = rsv070SisanKanri;
    }
    /**
     * <p>rsv070SisetuKbnName__ を取得します。
     * @return rsv070SisetuKbnName
     */
    public String getRsv070SisetuKbnName() {
        return rsv070SisetuKbnName__;
    }
    /**
     * <p>rsv070SisetuKbnName__ をセットします。
     * @param rsv070SisetuKbnName rsv070SisetuKbnName__
     */
    public void setRsv070SisetuKbnName(String rsv070SisetuKbnName) {
        rsv070SisetuKbnName__ = rsv070SisetuKbnName;
    }
    /**
     * <p>rsv070SisetuName__ を取得します。
     * @return rsv070SisetuName
     */
    public String getRsv070SisetuName() {
        return rsv070SisetuName__;
    }
    /**
     * <p>rsv070SisetuName__ をセットします。
     * @param rsv070SisetuName rsv070SisetuName__
     */
    public void setRsv070SisetuName(String rsv070SisetuName) {
        rsv070SisetuName__ = rsv070SisetuName;
    }
    /**
     * <p>rsv070Prop4Value__ を取得します。
     * @return rsv070Prop4Value
     */
    public String getRsv070Prop4Value() {
        return rsv070Prop4Value__;
    }
    /**
     * <p>rsv070Prop4Value__ をセットします。
     * @param rsv070Prop4Value rsv070Prop4Value__
     */
    public void setRsv070Prop4Value(String rsv070Prop4Value) {
        rsv070Prop4Value__ = rsv070Prop4Value;
    }
    /**
     * <p>rsv070Prop5Value__ を取得します。
     * @return rsv070Prop5Value
     */
    public String getRsv070Prop5Value() {
        return rsv070Prop5Value__;
    }
    /**
     * <p>rsv070Prop5Value__ をセットします。
     * @param rsv070Prop5Value rsv070Prop5Value__
     */
    public void setRsv070Prop5Value(String rsv070Prop5Value) {
        rsv070Prop5Value__ = rsv070Prop5Value;
    }
    /**
     * <p>rsv070PropHeaderName4__ を取得します。
     * @return rsv070PropHeaderName4
     */
    public String getRsv070PropHeaderName4() {
        return rsv070PropHeaderName4__;
    }
    /**
     * <p>rsv070PropHeaderName4__ をセットします。
     * @param rsv070PropHeaderName4 rsv070PropHeaderName4__
     */
    public void setRsv070PropHeaderName4(String rsv070PropHeaderName4) {
        rsv070PropHeaderName4__ = rsv070PropHeaderName4;
    }
    /**
     * <p>rsv070PropHeaderName5__ を取得します。
     * @return rsv070PropHeaderName5
     */
    public String getRsv070PropHeaderName5() {
        return rsv070PropHeaderName5__;
    }
    /**
     * <p>rsv070PropHeaderName5__ をセットします。
     * @param rsv070PropHeaderName5 rsv070PropHeaderName5__
     */
    public void setRsv070PropHeaderName5(String rsv070PropHeaderName5) {
        rsv070PropHeaderName5__ = rsv070PropHeaderName5;
    }
    /**
     * <p>rsv070Prop6Value__ を取得します。
     * @return rsv070Prop6Value
     */
    public String getRsv070Prop6Value() {
        return rsv070Prop6Value__;
    }
    /**
     * <p>rsv070Prop6Value__ をセットします。
     * @param rsv070Prop6Value rsv070Prop6Value__
     */
    public void setRsv070Prop6Value(String rsv070Prop6Value) {
        rsv070Prop6Value__ = rsv070Prop6Value;
    }
    /**
     * <p>rsv070PropHeaderName6__ を取得します。
     * @return rsv070PropHeaderName6
     */
    public String getRsv070PropHeaderName6() {
        return rsv070PropHeaderName6__;
    }
    /**
     * <p>rsv070PropHeaderName6__ をセットします。
     * @param rsv070PropHeaderName6 rsv070PropHeaderName6__
     */
    public void setRsv070PropHeaderName6(String rsv070PropHeaderName6) {
        rsv070PropHeaderName6__ = rsv070PropHeaderName6;
    }
    /**
     * <p>rsv070Prop7Value__ を取得します。
     * @return rsv070Prop7Value
     */
    public String getRsv070Prop7Value() {
        return rsv070Prop7Value__;
    }
    /**
     * <p>rsv070Prop7Value__ をセットします。
     * @param rsv070Prop7Value rsv070Prop7Value__
     */
    public void setRsv070Prop7Value(String rsv070Prop7Value) {
        rsv070Prop7Value__ = rsv070Prop7Value;
    }
    /**
     * <p>rsv070PropHeaderName7__ を取得します。
     * @return rsv070PropHeaderName7
     */
    public String getRsv070PropHeaderName7() {
        return rsv070PropHeaderName7__;
    }
    /**
     * <p>rsv070PropHeaderName7__ をセットします。
     * @param rsv070PropHeaderName7 rsv070PropHeaderName7__
     */
    public void setRsv070PropHeaderName7(String rsv070PropHeaderName7) {
        rsv070PropHeaderName7__ = rsv070PropHeaderName7;
    }
    /**
     * @return rsv070SisetuId
     */
    public String getRsv070SisetuId() {
        return rsv070SisetuId__;
    }
    /**
     * @param rsv070SisetuId 設定する rsv070SisetuId
     */
    public void setRsv070SisetuId(String rsv070SisetuId) {
        rsv070SisetuId__ = rsv070SisetuId;
    }
    /**
     * <p>rsv070PlaceFileComment1 を取得します。
     * @return rsv070PlaceFileComment1
     */
    public String getRsv070PlaceFileComment1() {
        return rsv070PlaceFileComment1__;
    }
    /**
     * <p>rsv070PlaceFileComment1 をセットします。
     * @param rsv070PlaceFileComment1 rsv070PlaceFileComment1
     */
    public void setRsv070PlaceFileComment1(String rsv070PlaceFileComment1) {
        rsv070PlaceFileComment1__ = rsv070PlaceFileComment1;
    }
    /**
     * <p>rsv070PlaceFileComment10 を取得します。
     * @return rsv070PlaceFileComment10
     */
    public String getRsv070PlaceFileComment10() {
        return rsv070PlaceFileComment10__;
    }
    /**
     * <p>rsv070PlaceFileComment10 をセットします。
     * @param rsv070PlaceFileComment10 rsv070PlaceFileComment10
     */
    public void setRsv070PlaceFileComment10(String rsv070PlaceFileComment10) {
        rsv070PlaceFileComment10__ = rsv070PlaceFileComment10;
    }
    /**
     * <p>rsv070PlaceFileComment2 を取得します。
     * @return rsv070PlaceFileComment2
     */
    public String getRsv070PlaceFileComment2() {
        return rsv070PlaceFileComment2__;
    }
    /**
     * <p>rsv070PlaceFileComment2 をセットします。
     * @param rsv070PlaceFileComment2 rsv070PlaceFileComment2
     */
    public void setRsv070PlaceFileComment2(String rsv070PlaceFileComment2) {
        rsv070PlaceFileComment2__ = rsv070PlaceFileComment2;
    }
    /**
     * <p>rsv070PlaceFileComment3 を取得します。
     * @return rsv070PlaceFileComment3
     */
    public String getRsv070PlaceFileComment3() {
        return rsv070PlaceFileComment3__;
    }
    /**
     * <p>rsv070PlaceFileComment3 をセットします。
     * @param rsv070PlaceFileComment3 rsv070PlaceFileComment3
     */
    public void setRsv070PlaceFileComment3(String rsv070PlaceFileComment3) {
        rsv070PlaceFileComment3__ = rsv070PlaceFileComment3;
    }
    /**
     * <p>rsv070PlaceFileComment4 を取得します。
     * @return rsv070PlaceFileComment4
     */
    public String getRsv070PlaceFileComment4() {
        return rsv070PlaceFileComment4__;
    }
    /**
     * <p>rsv070PlaceFileComment4 をセットします。
     * @param rsv070PlaceFileComment4 rsv070PlaceFileComment4
     */
    public void setRsv070PlaceFileComment4(String rsv070PlaceFileComment4) {
        rsv070PlaceFileComment4__ = rsv070PlaceFileComment4;
    }
    /**
     * <p>rsv070PlaceFileComment5 を取得します。
     * @return rsv070PlaceFileComment5
     */
    public String getRsv070PlaceFileComment5() {
        return rsv070PlaceFileComment5__;
    }
    /**
     * <p>rsv070PlaceFileComment5 をセットします。
     * @param rsv070PlaceFileComment5 rsv070PlaceFileComment5
     */
    public void setRsv070PlaceFileComment5(String rsv070PlaceFileComment5) {
        rsv070PlaceFileComment5__ = rsv070PlaceFileComment5;
    }
    /**
     * <p>rsv070PlaceFileComment6 を取得します。
     * @return rsv070PlaceFileComment6
     */
    public String getRsv070PlaceFileComment6() {
        return rsv070PlaceFileComment6__;
    }
    /**
     * <p>rsv070PlaceFileComment6 をセットします。
     * @param rsv070PlaceFileComment6 rsv070PlaceFileComment6
     */
    public void setRsv070PlaceFileComment6(String rsv070PlaceFileComment6) {
        rsv070PlaceFileComment6__ = rsv070PlaceFileComment6;
    }
    /**
     * <p>rsv070PlaceFileComment7 を取得します。
     * @return rsv070PlaceFileComment7
     */
    public String getRsv070PlaceFileComment7() {
        return rsv070PlaceFileComment7__;
    }
    /**
     * <p>rsv070PlaceFileComment7 をセットします。
     * @param rsv070PlaceFileComment7 rsv070PlaceFileComment7
     */
    public void setRsv070PlaceFileComment7(String rsv070PlaceFileComment7) {
        rsv070PlaceFileComment7__ = rsv070PlaceFileComment7;
    }
    /**
     * <p>rsv070PlaceFileComment8 を取得します。
     * @return rsv070PlaceFileComment8
     */
    public String getRsv070PlaceFileComment8() {
        return rsv070PlaceFileComment8__;
    }
    /**
     * <p>rsv070PlaceFileComment8 をセットします。
     * @param rsv070PlaceFileComment8 rsv070PlaceFileComment8
     */
    public void setRsv070PlaceFileComment8(String rsv070PlaceFileComment8) {
        rsv070PlaceFileComment8__ = rsv070PlaceFileComment8;
    }
    /**
     * <p>rsv070PlaceFileComment9 を取得します。
     * @return rsv070PlaceFileComment9
     */
    public String getRsv070PlaceFileComment9() {
        return rsv070PlaceFileComment9__;
    }
    /**
     * <p>rsv070PlaceFileComment9 をセットします。
     * @param rsv070PlaceFileComment9 rsv070PlaceFileComment9
     */
    public void setRsv070PlaceFileComment9(String rsv070PlaceFileComment9) {
        rsv070PlaceFileComment9__ = rsv070PlaceFileComment9;
    }
    /**
     * <p>rsv070PlaComment を取得します。
     * @return rsv070PlaComment
     */
    public String getRsv070PlaComment() {
        return rsv070PlaComment__;
    }
    /**
     * <p>rsv070PlaComment をセットします。
     * @param rsv070PlaComment rsv070PlaComment
     */
    public void setRsv070PlaComment(String rsv070PlaComment) {
        rsv070PlaComment__ = rsv070PlaComment;
    }
    /**
     * <p>rsv070apprDspFlg を取得します。
     * @return rsv070apprDspFlg
     */
    public boolean isRsv070apprDspFlg() {
        return rsv070apprDspFlg__;
    }
    /**
     * <p>rsv070apprDspFlg をセットします。
     * @param rsv070apprDspFlg rsv070apprDspFlg
     */
    public void setRsv070apprDspFlg(boolean rsv070apprDspFlg) {
        rsv070apprDspFlg__ = rsv070apprDspFlg;
    }
    /**
     * <p>rsv070apprKbnFlg を取得します。
     * @return rsv070apprKbnFlg
     */
    public boolean isRsv070apprKbnFlg() {
        return rsv070apprKbnFlg__;
    }
    /**
     * <p>rsv070apprKbnFlg をセットします。
     * @param rsv070apprKbnFlg rsv070apprKbnFlg
     */
    public void setRsv070apprKbnFlg(boolean rsv070apprKbnFlg) {
        rsv070apprKbnFlg__ = rsv070apprKbnFlg;
    }
}
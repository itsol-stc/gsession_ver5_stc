package jp.groupsession.v2.enq.enq210;

import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アンケート 設問情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq210QueModel extends AbstractModel {
    /** 初期値 選択 */
    public static final int INITFLG_SELECT = 1;
    /** 範囲 選択 */
    public static final int RANGEFLG_SELECT = 1;

    /** アンケートSID */
    private long enq210Sid__ = -1;
    /** 設問連番 */
    private int enq210Seq__ = 0;
    /** 表示順 */
    private int enq210DspOrder__ = 0;
    /** 設問番号 */
    private String enq210QueNo__ = null;
    /** 設問番号(自動) */
    private int enq210AutoQueNo__ = 0;
    /** 設問 */
    private String enq210Question__ = null;
    /** 設問種類区分 */
    private int enq210QueKbn__ = -1;
    /** 必須フラグ */
    private String enq210Require__ = null;
    /** その他入力有無 */
    private int enq210OtherFlg__ = 0;
    /** 説明 */
    private String enq210QueDesc__ = null;
    /** 説明(テキストのみ) */
    private String enq210QueDescText__ = null;
    /** 添付区分 */
    private int enq210AttachKbn__ = 0;
    /** 添付ファイルSID */
    private String enq210AttachSid__ = null;
    /** 添付名 */
    private String enq210AttachName__ = null;
    /** 添付位置 */
    private int enq210AttachPos__ = 0;
    /** URL */
    private String enq210Url__ = null;
    /** 表示名 */
    private String enq210DspName__ = null;
    /** 初期値フラグ */
    private int enq210initFlg__ = 0;
    /** 初期値 テキスト */
    private String enq210initTxt__ = null;
    /** 初期値 日付 */
    private UDate enq210initDate__ = null;
    /** 初期値 日付 表示用 */
    private String enq210initDspDate__ = null;
    /** 範囲フラグ */
    private int enq210rangeFlg__ = 0;
    /** 範囲From テキスト */
    private String enq210rangeTxtFr__ = null;
    /** 範囲To テキスト */
    private String enq210rangeTxtTo__ = null;
    /** 範囲From 日付 */
    private UDate enq210rangeDateFr__ = null;
    /** 範囲To 日付 */
    private UDate enq210rangeDateTo__ = null;
    /** 範囲From 日付 表示用 */
    private String enq210rangeTxtFrDsp__ = null;
    /** 範囲To 日付 表示用 */
    private String enq210rangeTxtToDsp__ = null;
    /** 横線位置 */
    private int enq210LinePos__ = 0;
    /** 単位 */
    private String enq210unitNum__ = null;

    /** 設問ID */
    private String enq210Id__ = null;
    /** 設問Index */
    private int enq210queIndex__ = 0;
    /** 設問種類名称 */
    private String enq210SyuruiName__ = null;

    /** 設問情報(選択肢)一覧 */
    private List<Enq210QueSubModel> queSubList__ = null;

    /**
     * <p>enq210Sid を取得します。
     * @return enq210Sid
     */
    public long getEnq210Sid() {
        return enq210Sid__;
    }
    /**
     * <p>enq210Sid をセットします。
     * @param enq210Sid enq210Sid
     */
    public void setEnq210Sid(long enq210Sid) {
        enq210Sid__ = enq210Sid;
    }
    /**
     * <p>enq210Seq を取得します。
     * @return enq210Seq
     */
    public int getEnq210Seq() {
        return enq210Seq__;
    }
    /**
     * <p>enq210Seq をセットします。
     * @param enq210Seq enq210Seq
     */
    public void setEnq210Seq(int enq210Seq) {
        enq210Seq__ = enq210Seq;
    }
    /**
     * <p>enq210DspOrder を取得します。
     * @return enq210DspOrder
     */
    public int getEnq210DspOrder() {
        return enq210DspOrder__;
    }
    /**
     * <p>enq210DspOrder をセットします。
     * @param enq210DspOrder enq210DspOrder
     */
    public void setEnq210DspOrder(int enq210DspOrder) {
        enq210DspOrder__ = enq210DspOrder;
    }
    /**
     * <p>enq210AutoQueNo を取得します。
     * @return enq210AutoQueNo
     */
    public int getEnq210AutoQueNo() {
        return enq210AutoQueNo__;
    }
    /**
     * <p>enq210AutoQueNo をセットします。
     * @param enq210AutoQueNo enq210AutoQueNo
     */
    public void setEnq210AutoQueNo(int enq210AutoQueNo) {
        enq210AutoQueNo__ = enq210AutoQueNo;
    }
    /**
     * <p>enq210QueNo を取得します。
     * @return enq210QueNo
     */
    public String getEnq210QueNo() {
        return enq210QueNo__;
    }
    /**
     * <p>enq210QueNo をセットします。
     * @param enq210QueNo enq210QueNo
     */
    public void setEnq210QueNo(String enq210QueNo) {
        enq210QueNo__ = enq210QueNo;
    }
    /**
     * <p>enq210Question を取得します。
     * @return enq210Question
     */
    public String getEnq210Question() {
        return enq210Question__;
    }
    /**
     * <p>enq210Question をセットします。
     * @param enq210Question enq210Question
     */
    public void setEnq210Question(String enq210Question) {
        enq210Question__ = enq210Question;
    }
    /**
     * <p>enq210QueKbn を取得します。
     * @return enq210QueKbn
     */
    public int getEnq210QueKbn() {
        return enq210QueKbn__;
    }
    /**
     * <p>enq210QueKbn をセットします。
     * @param enq210QueKbn enq210QueKbn
     */
    public void setEnq210QueKbn(int enq210QueKbn) {
        enq210QueKbn__ = enq210QueKbn;
    }
    /**
     * <p>enq210Require を取得します。
     * @return enq210Require
     */
    public String getEnq210Require() {
        return enq210Require__;
    }
    /**
     * <p>enq210Require をセットします。
     * @param enq210Require enq210Require
     */
    public void setEnq210Require(String enq210Require) {
        enq210Require__ = enq210Require;
    }
    /**
     * <p>enq210OtherFlg を取得します。
     * @return enq210OtherFlg
     */
    public int getEnq210OtherFlg() {
        return enq210OtherFlg__;
    }
    /**
     * <p>enq210OtherFlg をセットします。
     * @param enq210OtherFlg enq210OtherFlg
     */
    public void setEnq210OtherFlg(int enq210OtherFlg) {
        enq210OtherFlg__ = enq210OtherFlg;
    }
    /**
     * <p>enq210QueDesc を取得します。
     * @return enq210QueDesc
     */
    public String getEnq210QueDesc() {
        return enq210QueDesc__;
    }
    /**
     * <p>enq210QueDesc をセットします。
     * @param enq210QueDesc enq210QueDesc
     */
    public void setEnq210QueDesc(String enq210QueDesc) {
        enq210QueDesc__ = enq210QueDesc;
    }
    /**
     * <p>enq210QueDescText を取得します。
     * @return enq210QueDescText
     */
    public String getEnq210QueDescText() {
        return enq210QueDescText__;
    }
    /**
     * <p>enq210QueDescText をセットします。
     * @param enq210QueDescText enq210QueDescText
     */
    public void setEnq210QueDescText(String enq210QueDescText) {
        enq210QueDescText__ = enq210QueDescText;
    }
    /**
     * <p>enq210AttachKbn を取得します。
     * @return enq210AttachKbn
     */
    public int getEnq210AttachKbn() {
        return enq210AttachKbn__;
    }
    /**
     * <p>enq210AttachKbn をセットします。
     * @param enq210AttachKbn enq210AttachKbn
     */
    public void setEnq210AttachKbn(int enq210AttachKbn) {
        enq210AttachKbn__ = enq210AttachKbn;
    }
    /**
     * <p>enq210AttachSid を取得します。
     * @return enq210AttachSid
     */
    public String getEnq210AttachSid() {
        return enq210AttachSid__;
    }
    /**
     * <p>enq210AttachSid をセットします。
     * @param enq210AttachSid enq210AttachSid
     */
    public void setEnq210AttachSid(String enq210AttachSid) {
        enq210AttachSid__ = enq210AttachSid;
    }
    /**
     * <p>enq210AttachName を取得します。
     * @return enq210AttachName
     */
    public String getEnq210AttachName() {
        return enq210AttachName__;
    }
    /**
     * <p>enq210AttachName をセットします。
     * @param enq210AttachName enq210AttachName
     */
    public void setEnq210AttachName(String enq210AttachName) {
        enq210AttachName__ = enq210AttachName;
    }
    /**
     * <p>enq210AttachPos を取得します。
     * @return enq210AttachPos
     */
    public int getEnq210AttachPos() {
        return enq210AttachPos__;
    }
    /**
     * <p>enq210AttachPos をセットします。
     * @param enq210AttachPos enq210AttachPos
     */
    public void setEnq210AttachPos(int enq210AttachPos) {
        enq210AttachPos__ = enq210AttachPos;
    }
    /**
     * <p>enq210Url を取得します。
     * @return enq210Url
     */
    public String getEnq210Url() {
        return enq210Url__;
    }
    /**
     * <p>enq210Url をセットします。
     * @param enq210Url enq210Url
     */
    public void setEnq210Url(String enq210Url) {
        enq210Url__ = enq210Url;
    }
    /**
     * <p>enq210DspName を取得します。
     * @return enq210DspName
     */
    public String getEnq210DspName() {
        return enq210DspName__;
    }
    /**
     * <p>enq210DspName をセットします。
     * @param enq210DspName enq210DspName
     */
    public void setEnq210DspName(String enq210DspName) {
        enq210DspName__ = enq210DspName;
    }
    /**
     * <p>enq210initFlg を取得します。
     * @return enq210initFlg
     */
    public int getEnq210initFlg() {
        return enq210initFlg__;
    }
    /**
     * <p>enq210initFlg をセットします。
     * @param enq210initFlg enq210initFlg
     */
    public void setEnq210initFlg(int enq210initFlg) {
        enq210initFlg__ = enq210initFlg;
    }
    /**
     * <p>enq210initTxt を取得します。
     * @return enq210initTxt
     */
    public String getEnq210initTxt() {
        return enq210initTxt__;
    }
    /**
     * <p>enq210initTxt をセットします。
     * @param enq210initTxt enq210initTxt
     */
    public void setEnq210initTxt(String enq210initTxt) {
        enq210initTxt__ = enq210initTxt;
    }
    /**
     * <p>enq210initDate を取得します。
     * @return enq210initDate
     */
    public UDate getEnq210initDate() {
        return enq210initDate__;
    }
    /**
     * <p>enq210initDate をセットします。
     * @param enq210initDate enq210initDate
     */
    public void setEnq210initDate(UDate enq210initDate) {
        enq210initDate__ = enq210initDate;
    }
    /**
     * <p>enq210rangeFlg を取得します。
     * @return enq210rangeFlg
     */
    public int getEnq210rangeFlg() {
        return enq210rangeFlg__;
    }
    /**
     * <p>enq210rangeFlg をセットします。
     * @param enq210rangeFlg enq210rangeFlg
     */
    public void setEnq210rangeFlg(int enq210rangeFlg) {
        enq210rangeFlg__ = enq210rangeFlg;
    }
    /**
     * <p>enq210rangeTxtFr を取得します。
     * @return enq210rangeTxtFr
     */
    public String getEnq210rangeTxtFr() {
        return enq210rangeTxtFr__;
    }
    /**
     * <p>enq210rangeTxtFr をセットします。
     * @param enq210rangeTxtFr enq210rangeTxtFr
     */
    public void setEnq210rangeTxtFr(String enq210rangeTxtFr) {
        enq210rangeTxtFr__ = enq210rangeTxtFr;
    }
    /**
     * <p>enq210rangeTxtTo を取得します。
     * @return enq210rangeTxtTo
     */
    public String getEnq210rangeTxtTo() {
        return enq210rangeTxtTo__;
    }
    /**
     * <p>enq210rangeTxtTo をセットします。
     * @param enq210rangeTxtTo enq210rangeTxtTo
     */
    public void setEnq210rangeTxtTo(String enq210rangeTxtTo) {
        enq210rangeTxtTo__ = enq210rangeTxtTo;
    }
    /**
     * <p>enq210rangeDateFr を取得します。
     * @return enq210rangeDateFr
     */
    public UDate getEnq210rangeDateFr() {
        return enq210rangeDateFr__;
    }
    /**
     * <p>enq210rangeDateFr をセットします。
     * @param enq210rangeDateFr enq210rangeDateFr
     */
    public void setEnq210rangeDateFr(UDate enq210rangeDateFr) {
        enq210rangeDateFr__ = enq210rangeDateFr;
    }
    /**
     * <p>enq210rangeDateTo を取得します。
     * @return enq210rangeDateTo
     */
    public UDate getEnq210rangeDateTo() {
        return enq210rangeDateTo__;
    }
    /**
     * <p>enq210rangeDateTo をセットします。
     * @param enq210rangeDateTo enq210rangeDateTo
     */
    public void setEnq210rangeDateTo(UDate enq210rangeDateTo) {
        enq210rangeDateTo__ = enq210rangeDateTo;
    }
    /**
     * <p>enq210SyuruiName を取得します。
     * @return enq210SyuruiName
     */
    public String getEnq210SyuruiName() {
        return enq210SyuruiName__;
    }
    /**
     * <p>enq210SyuruiName をセットします。
     * @param enq210SyuruiName enq210SyuruiName
     */
    public void setEnq210SyuruiName(String enq210SyuruiName) {
        enq210SyuruiName__ = enq210SyuruiName;
    }
    /**
     * <p>enq210LinePos を取得します。
     * @return enq210LinePos
     */
    public int getEnq210LinePos() {
        return enq210LinePos__;
    }
    /**
     * <p>enq210LinePos をセットします。
     * @param enq210LinePos enq210LinePos
     */
    public void setEnq210LinePos(int enq210LinePos) {
        enq210LinePos__ = enq210LinePos;
    }
    /**
     * <p>enq210unitNum を取得します。
     * @return enq210unitNum
     */
    public String getEnq210unitNum() {
        return enq210unitNum__;
    }
    /**
     * <p>enq210unitNum をセットします。
     * @param enq210unitNum enq210unitNum
     */
    public void setEnq210unitNum(String enq210unitNum) {
        enq210unitNum__ = enq210unitNum;
    }
    /**
     * <p>enq210queIndex を取得します。
     * @return enq210queIndex
     */
    public int getEnq210queIndex() {
        return enq210queIndex__;
    }
    /**
     * <p>enq210queIndex をセットします。
     * @param enq210queIndex enq210queIndex
     */
    public void setEnq210queIndex(int enq210queIndex) {
        enq210queIndex__ = enq210queIndex;
    }
    /**
     * <p>enq210Id を取得します。
     * @return enq210Id
     */
    public String getEnq210Id() {
        return enq210Id__;
    }
    /**
     * <p>enq210Id をセットします。
     * @param enq210Id enq210Id
     */
    public void setEnq210Id(String enq210Id) {
        enq210Id__ = enq210Id;
    }
    /**
     * <p>queSubList を取得します。
     * @return queSubList
     */
    public List<Enq210QueSubModel> getQueSubList() {
        return queSubList__;
    }
    /**
     * <p>queSubList をセットします。
     * @param queSubList queSubList
     */
    public void setQueSubList(List<Enq210QueSubModel> queSubList) {
        queSubList__ = queSubList;
    }
    /**
     * <p>enq210initDspDate を取得します。
     * @return enq210initDspDate
     */
    public String getEnq210initDspDate() {
        return enq210initDspDate__;
    }
    /**
     * <p>enq210initDspDate をセットします。
     * @param enq210initDspDate enq210initDspDate
     */
    public void setEnq210initDspDate(String enq210initDspDate) {
        enq210initDspDate__ = enq210initDspDate;
    }
    /**
     * <p>enq210rangeTxtFrDsp を取得します。
     * @return enq210rangeTxtFrDsp
     */
    public String getEnq210rangeTxtFrDsp() {
        return enq210rangeTxtFrDsp__;
    }
    /**
     * <p>enq210rangeTxtFrDsp をセットします。
     * @param enq210rangeTxtFrDsp enq210rangeTxtFrDsp
     */
    public void setEnq210rangeTxtFrDsp(String enq210rangeTxtFrDsp) {
        enq210rangeTxtFrDsp__ = enq210rangeTxtFrDsp;
    }
    /**
     * <p>enq210rangeTxtToDsp を取得します。
     * @return enq210rangeTxtToDsp
     */
    public String getEnq210rangeTxtToDsp() {
        return enq210rangeTxtToDsp__;
    }
    /**
     * <p>enq210rangeTxtToDsp をセットします。
     * @param enq210rangeTxtToDsp enq210rangeTxtToDsp
     */
    public void setEnq210rangeTxtToDsp(String enq210rangeTxtToDsp) {
        enq210rangeTxtToDsp__ = enq210rangeTxtToDsp;
    }

    /**
     * <br>[機  能] 初期値(テキスト)の画面表示用文字列を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return 初期値(テキスト)の画面表示用文字列
     */
    public String getEnq210viewInitTxt() {
        return StringUtilHtml.transToHTmlPlusAmparsant(
                    NullDefault.getString(enq210initTxt__, ""));
    }
}

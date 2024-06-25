package jp.groupsession.v2.enq.enq220;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.EnqValidateUtil;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq210.Enq210Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート 設問詳細登録画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq220Form extends Enq210Form {

    /** 編集モード */
    private int enq220editMode__ = 0;
    /** 初期表示フラグ */
    private int enq220initFlg__ = 0;
    /** アンケート詳細情報表示フラグ */
    private int enq220viewDetailFlg__ = 0;

    /** 設問ID */
    private String enq220queId__ = null;
    /** ファイル保存先 */
    private String enq220fileDir__ = null;
    /** 発信者(表示用) */
    private String enq220ViewSender__ = null;
    /** アンケート内容(表示用) */
    private String enq220ViewContent__ = null;
    /** 回答期限(表示用) */
    private String enq220ViewAnsDate__ = null;
    /** 公開期限 開始(表示用) */
    private String enq220ViewPubDateFrom__ = null;
    /** 公開期限 終了(表示用) */
    private String enq220ViewPubDateTo__ = null;
    /** 回答公開期限 開始(表示用) */
    private String enq220ViewAnsPubDateFrom__ = null;
    /** 注意事項(表示用) */
    private String enq220ViewDesc__ = null;

    /** 表示モード */
    private String enq220DspMode__ = null;
    /** 表示種類名称 */
    private String enq220SyuruiName__ = null;
    /** 表示位置移動フラグ */
    private int enq220scrollQuestonFlg__ = 0;

    /** 設問連番 */
    private int enq220Seq__ = 0;
    /** 表示順 */
    private int enq220DspOrder__ = 0;
    /** 設問番号 */
    private String enq220QueNo__ = null;
    /** 設問番号(自動) */
    private String enq220autoQueNo__ = null;
    /** 設問 */
    private String enq220Question__ = null;
    /** 設問種類 */
    private int enq220QueKbn__ = -1;
    /** 必須フラグ */
    private String enq220Require__ = null;
    /** その他入力有無区分 */
    private String enq220Other__ = null;
    /** 説明 */
    private String enq220QueDesc__ = null;
    /** 説明(テキストのみ) */
    private String enq220QueDescText__ = null;
    /** 添付区分 */
    private String enq220AttachKbn__ = null;
    /** 添付ファイルSID */
    private String enq220AttachSid__ = null;
    /** 添付名 */
    private String enq220AttachName__ = null;
    /** 添付位置 */
    private String enq220AttachPos__ = null;
    /** URL */
    private String enq220Url__ = null;
    /** 表示名 */
    private String enq220TempDspName__ = null;

    /** 初期値 区分 */
    private int enq220DefKbn__ = 0;
    /** 初期値 テキスト入力 */
    private String enq220DefTxt__ = null;
    /** 初期値 数値入力 */
    private String enq220DefNum__ = null;
    /** 初期値 日付入力 年 */
    private int enq220DefDateYear__ = 0;
    /** 初期値 日付入力 月 */
    private int enq220DefDateMonth__ = 0;
    /** 初期値 日付入力 日 */
    private int enq220DefDateDay__ = 0;
    /** 初期値 日付入力 年月日 */
    private String enq220DefDate__ = null;
    /** 範囲 区分 */
    private int enq220RngKbn__ = 0;
    /** 範囲 数値入力 開始 */
    private String enq220RngStrNum__ = null;
    /** 範囲 数値入力 終了 */
    private String enq220RngEndNum__ = null;
    /** 範囲 日付入力 開始 年 */
    private int enq220RngStrDateYear__ = 0;
    /** 範囲 日付入力 開始 月 */
    private int enq220RngStrDateMonth__ = 0;
    /** 範囲 日付入力 開始 日 */
    private int enq220RngStrDateDay__ = 0;
    /** 範囲 日付入力 終了 年 */
    private int enq220RngEndDateYear__ = 0;
    /** 範囲 日付入力 終了 月 */
    private int enq220RngEndDateMonth__ = 0;
    /** 範囲 日付入力 終了 日 */
    private int enq220RngEndDateDay__ = 0;
    /** 範囲 日付入力 開始 年月日 */
    private String enq220RngStrDate__ = null;
    /** 範囲 日付入力 終了 年月日 */
    private String enq220RngEndDate__ = null;
    
    /** 単位 */
    private String enq220UnitNum__ = null;
    /** 横線位置 */
    private int enq220LinePos__ = 0;

    /** 添付ファイル */
    private String enq220files__ = null;
    /** 添付ファイル名 */
    private String enq220fileName__ = null;

    /** 選択 編集対象 */
    private int enq220selectRow__ = -1;
    /** 選択 削除対象 */
    private int enq220deleteRow__ = -1;
    /** 選択のサブフォーム */
    private List<Enq220SubForm> subList__ = null;

    /**
     * <p>enq220editMode を取得します。
     * @return enq220editMode
     */
    public int getEnq220editMode() {
        return enq220editMode__;
    }

    /**
     * <p>enq220editMode をセットします。
     * @param enq220editMode enq220editMode
     */
    public void setEnq220editMode(int enq220editMode) {
        enq220editMode__ = enq220editMode;
    }

    /**
     * <p>enq220initFlg を取得します。
     * @return enq220initFlg
     */
    public int getEnq220initFlg() {
        return enq220initFlg__;
    }

    /**
     * <p>enq220initFlg をセットします。
     * @param enq220initFlg enq220initFlg
     */
    public void setEnq220initFlg(int enq220initFlg) {
        enq220initFlg__ = enq220initFlg;
    }

    /**
     * <p>enq220viewDetailFlg を取得します。
     * @return enq220viewDetailFlg
     */
    public int getEnq220viewDetailFlg() {
        return enq220viewDetailFlg__;
    }

    /**
     * <p>enq220viewDetailFlg をセットします。
     * @param enq220viewDetailFlg enq220viewDetailFlg
     */
    public void setEnq220viewDetailFlg(int enq220viewDetailFlg) {
        enq220viewDetailFlg__ = enq220viewDetailFlg;
    }

    /**
     * <p>enq220DspMode を取得します。
     * @return enq220DspMode
     */
    public String getEnq220DspMode() {
        return enq220DspMode__;
    }


    /**
     * <p>enq220DspMode をセットします。
     * @param enq220DspMode enq220DspMode
     */
    public void setEnq220DspMode(String enq220DspMode) {
        enq220DspMode__ = enq220DspMode;
    }


    /**
     * <p>enq220SyuruiName を取得します。
     * @return enq220SyuruiName
     */
    public String getEnq220SyuruiName() {
        return enq220SyuruiName__;
    }


    /**
     * <p>enq220SyuruiName をセットします。
     * @param enq220SyuruiName enq220SyuruiName
     */
    public void setEnq220SyuruiName(String enq220SyuruiName) {
        enq220SyuruiName__ = enq220SyuruiName;
    }


    /**
     * <p>enq220queId を取得します。
     * @return enq220queId
     */
    public String getEnq220queId() {
        return enq220queId__;
    }

    /**
     * <p>enq220queId をセットします。
     * @param enq220queId enq220queId
     */
    public void setEnq220queId(String enq220queId) {
        enq220queId__ = enq220queId;
    }

    /**
     * <p>enq220fileDir を取得します。
     * @return enq220fileDir
     */
    public String getEnq220fileDir() {
        return enq220fileDir__;
    }

    /**
     * <p>enq220fileDir をセットします。
     * @param enq220fileDir enq220fileDir
     */
    public void setEnq220fileDir(String enq220fileDir) {
        enq220fileDir__ = enq220fileDir;
    }

    /**
     * <p>enq220Seq を取得します。
     * @return enq220Seq
     */
    public int getEnq220Seq() {
        return enq220Seq__;
    }


    /**
     * <p>enq220Seq をセットします。
     * @param enq220Seq enq220Seq
     */
    public void setEnq220Seq(int enq220Seq) {
        enq220Seq__ = enq220Seq;
    }


    /**
     * <p>enq220DspOrder を取得します。
     * @return enq220DspOrder
     */
    public int getEnq220DspOrder() {
        return enq220DspOrder__;
    }


    /**
     * <p>enq220DspOrder をセットします。
     * @param enq220DspOrder enq220DspOrder
     */
    public void setEnq220DspOrder(int enq220DspOrder) {
        enq220DspOrder__ = enq220DspOrder;
    }


    /**
     * <p>enq220autoQueNo を取得します。
     * @return enq220autoQueNo
     */
    public String getEnq220autoQueNo() {
        return enq220autoQueNo__;
    }

    /**
     * <p>enq220autoQueNo をセットします。
     * @param enq220autoQueNo enq220autoQueNo
     */
    public void setEnq220autoQueNo(String enq220autoQueNo) {
        enq220autoQueNo__ = enq220autoQueNo;
    }

    /**
     * <p>enq220QueNo を取得します。
     * @return enq220QueNo
     */
    public String getEnq220QueNo() {
        return enq220QueNo__;
    }


    /**
     * <p>enq220QueNo をセットします。
     * @param enq220QueNo enq220QueNo
     */
    public void setEnq220QueNo(String enq220QueNo) {
        enq220QueNo__ = enq220QueNo;
    }


    /**
     * <p>enq220Question を取得します。
     * @return enq220Question
     */
    public String getEnq220Question() {
        return enq220Question__;
    }


    /**
     * <p>enq220Question をセットします。
     * @param enq220Question enq220Question
     */
    public void setEnq220Question(String enq220Question) {
        enq220Question__ = enq220Question;
    }


    /**
     * <p>enq220QueKbn を取得します。
     * @return enq220QueKbn
     */
    public int getEnq220QueKbn() {
        return enq220QueKbn__;
    }


    /**
     * <p>enq220QueKbn をセットします。
     * @param enq220QueKbn enq220QueKbn
     */
    public void setEnq220QueKbn(int enq220QueKbn) {
        enq220QueKbn__ = enq220QueKbn;
    }


    /**
     * <p>enq220Require を取得します。
     * @return enq220Require
     */
    public String getEnq220Require() {
        return enq220Require__;
    }


    /**
     * <p>enq220Require をセットします。
     * @param enq220Require enq220Require
     */
    public void setEnq220Require(String enq220Require) {
        enq220Require__ = enq220Require;
    }


    /**
     * <p>enq220Other を取得します。
     * @return enq220Other
     */
    public String getEnq220Other() {
        return enq220Other__;
    }


    /**
     * <p>enq220Other をセットします。
     * @param enq220Other enq220Other
     */
    public void setEnq220Other(String enq220Other) {
        enq220Other__ = enq220Other;
    }


    /**
     * <p>enq220QueDesc を取得します。
     * @return enq220QueDesc
     */
    public String getEnq220QueDesc() {
        return enq220QueDesc__;
    }
    /**
     * <p>enq220QueDesc をセットします。
     * @param enq220QueDesc enq220QueDesc
     */
    public void setEnq220QueDesc(String enq220QueDesc) {
        enq220QueDesc__ = enq220QueDesc;
    }
    /**
     * <p>enq220QueDescText を取得します。
     * @return enq220QueDescText
     */
    public String getEnq220QueDescText() {
        return enq220QueDescText__;
    }

    /**
     * <p>enq220QueDescText をセットします。
     * @param enq220QueDescText enq220QueDescText
     */
    public void setEnq220QueDescText(String enq220QueDescText) {
        enq220QueDescText__ = enq220QueDescText;
    }

    /**
     * <p>enq220AttachKbn を取得します。
     * @return enq220AttachKbn
     */
    public String getEnq220AttachKbn() {
        return enq220AttachKbn__;
    }


    /**
     * <p>enq220AttachKbn をセットします。
     * @param enq220AttachKbn enq220AttachKbn
     */
    public void setEnq220AttachKbn(String enq220AttachKbn) {
        enq220AttachKbn__ = enq220AttachKbn;
    }


    /**
     * <p>enq220AttachSid を取得します。
     * @return enq220AttachSid
     */
    public String getEnq220AttachSid() {
        return enq220AttachSid__;
    }


    /**
     * <p>enq220AttachSid をセットします。
     * @param enq220AttachSid enq220AttachSid
     */
    public void setEnq220AttachSid(String enq220AttachSid) {
        enq220AttachSid__ = enq220AttachSid;
    }


    /**
     * <p>enq220AttachName を取得します。
     * @return enq220AttachName
     */
    public String getEnq220AttachName() {
        return enq220AttachName__;
    }


    /**
     * <p>enq220AttachName をセットします。
     * @param enq220AttachName enq220AttachName
     */
    public void setEnq220AttachName(String enq220AttachName) {
        enq220AttachName__ = enq220AttachName;
    }


    /**
     * <p>enq220AttachPos を取得します。
     * @return enq220AttachPos
     */
    public String getEnq220AttachPos() {
        return enq220AttachPos__;
    }


    /**
     * <p>enq220AttachPos をセットします。
     * @param enq220AttachPos enq220AttachPos
     */
    public void setEnq220AttachPos(String enq220AttachPos) {
        enq220AttachPos__ = enq220AttachPos;
    }


    /**
     * <p>enq220Url を取得します。
     * @return enq220Url
     */
    public String getEnq220Url() {
        return enq220Url__;
    }


    /**
     * <p>enq220Url をセットします。
     * @param enq220Url enq220Url
     */
    public void setEnq220Url(String enq220Url) {
        enq220Url__ = enq220Url;
    }


    /**
     * <p>enq220TempDspName を取得します。
     * @return enq220TempDspName
     */
    public String getEnq220TempDspName() {
        return enq220TempDspName__;
    }


    /**
     * <p>enq220TempDspName をセットします。
     * @param enq220TempDspName enq220TempDspName
     */
    public void setEnq220TempDspName(String enq220TempDspName) {
        enq220TempDspName__ = enq220TempDspName;
    }


    /**
     * <p>enq220DefTxt を取得します。
     * @return enq220DefTxt
     */
    public String getEnq220DefTxt() {
        return enq220DefTxt__;
    }


    /**
     * <p>enq220DefTxt をセットします。
     * @param enq220DefTxt enq220DefTxt
     */
    public void setEnq220DefTxt(String enq220DefTxt) {
        enq220DefTxt__ = enq220DefTxt;
    }

    /**
     * <p>enq220UnitNum を取得します。
     * @return enq220UnitNum
     */
    public String getEnq220UnitNum() {
        return enq220UnitNum__;
    }


    /**
     * <p>enq220UnitNum をセットします。
     * @param enq220UnitNum enq220UnitNum
     */
    public void setEnq220UnitNum(String enq220UnitNum) {
        this.enq220UnitNum__ = enq220UnitNum;
    }

    /**
     * <p>enq220DefKbn を取得します。
     * @return enq220DefKbn
     */
    public int getEnq220DefKbn() {
        return enq220DefKbn__;
    }

    /**
     * <p>enq220DefKbn をセットします。
     * @param enq220DefKbn enq220DefKbn
     */
    public void setEnq220DefKbn(int enq220DefKbn) {
        enq220DefKbn__ = enq220DefKbn;
    }

    /**
     * <p>enq220DefNum を取得します。
     * @return enq220DefNum
     */
    public String getEnq220DefNum() {
        return enq220DefNum__;
    }

    /**
     * <p>enq220DefNum をセットします。
     * @param enq220DefNum enq220DefNum
     */
    public void setEnq220DefNum(String enq220DefNum) {
        enq220DefNum__ = enq220DefNum;
    }

    /**
     * <p>enq220DefDateYear を取得します。
     * @return enq220DefDateYear
     */
    public int getEnq220DefDateYear() {
        return enq220DefDateYear__;
    }

    /**
     * <p>enq220DefDateYear をセットします。
     * @param enq220DefDateYear enq220DefDateYear
     */
    public void setEnq220DefDateYear(int enq220DefDateYear) {
        enq220DefDateYear__ = enq220DefDateYear;
    }

    /**
     * <p>enq220DefDateMonth を取得します。
     * @return enq220DefDateMonth
     */
    public int getEnq220DefDateMonth() {
        return enq220DefDateMonth__;
    }

    /**
     * <p>enq220DefDateMonth をセットします。
     * @param enq220DefDateMonth enq220DefDateMonth
     */
    public void setEnq220DefDateMonth(int enq220DefDateMonth) {
        enq220DefDateMonth__ = enq220DefDateMonth;
    }

    /**
     * <p>enq220DefDateDay を取得します。
     * @return enq220DefDateDay
     */
    public int getEnq220DefDateDay() {
        return enq220DefDateDay__;
    }

    /**
     * <p>enq220DefDateDay をセットします。
     * @param enq220DefDateDay enq220DefDateDay
     */
    public void setEnq220DefDateDay(int enq220DefDateDay) {
        enq220DefDateDay__ = enq220DefDateDay;
    }
    
    /**
     * <p>enq220DefDate を取得します。
     * @return enq220DefDate
     * @see jp.groupsession.v2.enq.enq220.Enq220Form#enq220DefDate__
     */
    public String getEnq220DefDate() {
        return enq220DefDate__;
    }

    /**
     * <p>enq220DefDate をセットします。
     * @param enq220DefDate enq220DefDate
     * @see jp.groupsession.v2.enq.enq220.Enq220Form#enq220DefDate__
     */
    public void setEnq220DefDate(String enq220DefDate) {
        enq220DefDate__ = enq220DefDate;
    }


    /**
     * <p>enq220RngKbn を取得します。
     * @return enq220RngKbn
     */
    public int getEnq220RngKbn() {
        return enq220RngKbn__;
    }

    /**
     * <p>enq220RngKbn をセットします。
     * @param enq220RngKbn enq220RngKbn
     */
    public void setEnq220RngKbn(int enq220RngKbn) {
        enq220RngKbn__ = enq220RngKbn;
    }

    /**
     * <p>enq220RngStrNum を取得します。
     * @return enq220RngStrNum
     */
    public String getEnq220RngStrNum() {
        return enq220RngStrNum__;
    }

    /**
     * <p>enq220RngStrNum をセットします。
     * @param enq220RngStrNum enq220RngStrNum
     */
    public void setEnq220RngStrNum(String enq220RngStrNum) {
        enq220RngStrNum__ = enq220RngStrNum;
    }

    /**
     * <p>enq220RngEndNum を取得します。
     * @return enq220RngEndNum
     */
    public String getEnq220RngEndNum() {
        return enq220RngEndNum__;
    }

    /**
     * <p>enq220RngEndNum をセットします。
     * @param enq220RngEndNum enq220RngEndNum
     */
    public void setEnq220RngEndNum(String enq220RngEndNum) {
        enq220RngEndNum__ = enq220RngEndNum;
    }

    /**
     * <p>enq220RngStrDateYear を取得します。
     * @return enq220RngStrDateYear
     */
    public int getEnq220RngStrDateYear() {
        return enq220RngStrDateYear__;
    }

    /**
     * <p>enq220RngStrDateYear をセットします。
     * @param enq220RngStrDateYear enq220RngStrDateYear
     */
    public void setEnq220RngStrDateYear(int enq220RngStrDateYear) {
        enq220RngStrDateYear__ = enq220RngStrDateYear;
    }

    /**
     * <p>enq220RngStrDateMonth を取得します。
     * @return enq220RngStrDateMonth
     */
    public int getEnq220RngStrDateMonth() {
        return enq220RngStrDateMonth__;
    }

    /**
     * <p>enq220RngStrDateMonth をセットします。
     * @param enq220RngStrDateMonth enq220RngStrDateMonth
     */
    public void setEnq220RngStrDateMonth(int enq220RngStrDateMonth) {
        enq220RngStrDateMonth__ = enq220RngStrDateMonth;
    }

    /**
     * <p>enq220RngStrDateDay を取得します。
     * @return enq220RngStrDateDay
     */
    public int getEnq220RngStrDateDay() {
        return enq220RngStrDateDay__;
    }

    /**
     * <p>enq220RngStrDateDay をセットします。
     * @param enq220RngStrDateDay enq220RngStrDateDay
     */
    public void setEnq220RngStrDateDay(int enq220RngStrDateDay) {
        enq220RngStrDateDay__ = enq220RngStrDateDay;
    }

    /**
     * <p>enq220RngEndDateYear を取得します。
     * @return enq220RngEndDateYear
     */
    public int getEnq220RngEndDateYear() {
        return enq220RngEndDateYear__;
    }

    /**
     * <p>enq220RngEndDateYear をセットします。
     * @param enq220RngEndDateYear enq220RngEndDateYear
     */
    public void setEnq220RngEndDateYear(int enq220RngEndDateYear) {
        enq220RngEndDateYear__ = enq220RngEndDateYear;
    }

    /**
     * <p>enq220RngEndDateMonth を取得します。
     * @return enq220RngEndDateMonth
     */
    public int getEnq220RngEndDateMonth() {
        return enq220RngEndDateMonth__;
    }

    /**
     * <p>enq220RngEndDateMonth をセットします。
     * @param enq220RngEndDateMonth enq220RngEndDateMonth
     */
    public void setEnq220RngEndDateMonth(int enq220RngEndDateMonth) {
        enq220RngEndDateMonth__ = enq220RngEndDateMonth;
    }

    /**
     * <p>enq220RngEndDateDay を取得します。
     * @return enq220RngEndDateDay
     */
    public int getEnq220RngEndDateDay() {
        return enq220RngEndDateDay__;
    }

    /**
     * <p>enq220RngEndDateDay をセットします。
     * @param enq220RngEndDateDay enq220RngEndDateDay
     */
    public void setEnq220RngEndDateDay(int enq220RngEndDateDay) {
        enq220RngEndDateDay__ = enq220RngEndDateDay;
    }
    
    /**
     * <p>enq220RngStrDate を取得します。
     * @return enq220RngStrDate
     * @see jp.groupsession.v2.enq.enq220.Enq220Form#enq220RngStrDate__
     */
    public String getEnq220RngStrDate() {
        return enq220RngStrDate__;
    }

    /**
     * <p>enq220RngStrDate をセットします。
     * @param enq220RngStrDate enq220RngStrDate
     * @see jp.groupsession.v2.enq.enq220.Enq220Form#enq220RngStrDate__
     */
    public void setEnq220RngStrDate(String enq220RngStrDate) {
        enq220RngStrDate__ = enq220RngStrDate;
    }
    
    /**
     * <p>enq220RngEndDate を取得します。
     * @return enq220RngEndDate
     * @see jp.groupsession.v2.enq.enq220.Enq220Form#enq220RngEndDate__
     */
    public String getEnq220RngEndDate() {
        return enq220RngEndDate__;
    }

    /**
     * <p>enq220RngEndDate をセットします。
     * @param enq220RngEndDate enq220RngEndDate
     * @see jp.groupsession.v2.enq.enq220.Enq220Form#enq220RngEndDate__
     */
    public void setEnq220RngEndDate(String enq220RngEndDate) {
        enq220RngEndDate__ = enq220RngEndDate;
    }

    /**
     * <p>enq220LinePos を取得します。
     * @return enq220LinePos
     */
    public int getEnq220LinePos() {
        return enq220LinePos__;
    }

    /**
     * <p>enq220LinePos をセットします。
     * @param enq220LinePos enq220LinePos
     */
    public void setEnq220LinePos(int enq220LinePos) {
        enq220LinePos__ = enq220LinePos;
    }

    /**
     * <p>enq220ViewSender を取得します。
     * @return enq220ViewSender
     */
    public String getEnq220ViewSender() {
        return enq220ViewSender__;
    }


    /**
     * <p>enq220ViewSender をセットします。
     * @param enq220ViewSender enq220ViewSender
     */
    public void setEnq220ViewSender(String enq220ViewSender) {
        enq220ViewSender__ = enq220ViewSender;
    }


    /**
     * <p>enq220ViewContent を取得します。
     * @return enq220ViewContent
     */
    public String getEnq220ViewContent() {
        return enq220ViewContent__;
    }


    /**
     * <p>enq220ViewContent をセットします。
     * @param enq220ViewContent enq220ViewContent
     */
    public void setEnq220ViewContent(String enq220ViewContent) {
        enq220ViewContent__ = enq220ViewContent;
    }
    /**
     * <p>enq220ViewAnsDate を取得します。
     * @return enq220ViewAnsDate
     */
    public String getEnq220ViewAnsDate() {
        return enq220ViewAnsDate__;
    }

    /**
     * <p>enq220ViewAnsDate をセットします。
     * @param enq220ViewAnsDate enq220ViewAnsDate
     */
    public void setEnq220ViewAnsDate(String enq220ViewAnsDate) {
        enq220ViewAnsDate__ = enq220ViewAnsDate;
    }

    /**
     * <p>enq220ViewAnsPubDateFrom を取得します。
     * @return enq220ViewAnsPubDateFrom
     */
    public String getEnq220ViewAnsPubDateFrom() {
        return enq220ViewAnsPubDateFrom__;
    }

    /**
     * <p>enq220ViewAnsPubDateFrom をセットします。
     * @param enq220ViewAnsPubDateFrom enq220ViewAnsPubDateFrom
     */
    public void setEnq220ViewAnsPubDateFrom(String enq220ViewAnsPubDateFrom) {
        enq220ViewAnsPubDateFrom__ = enq220ViewAnsPubDateFrom;
    }

    /**
     * <p>enq220ViewPubDateFrom を取得します。
     * @return enq220ViewPubDateFrom
     */
    public String getEnq220ViewPubDateFrom() {
        return enq220ViewPubDateFrom__;
    }


    /**
     * <p>enq220ViewPubDateFrom をセットします。
     * @param enq220ViewPubDateFrom enq220ViewPubDateFrom
     */
    public void setEnq220ViewPubDateFrom(String enq220ViewPubDateFrom) {
        enq220ViewPubDateFrom__ = enq220ViewPubDateFrom;
    }


    /**
     * <p>enq220files を取得します。
     * @return enq220files
     */
    public String getEnq220files() {
        return enq220files__;
    }

    /**
     * <p>enq220files をセットします。
     * @param enq220files enq220files
     */
    public void setEnq220files(String enq220files) {
        enq220files__ = enq220files;
    }

    /**
     * <p>enq220fileName を取得します。
     * @return enq220fileName
     */
    public String getEnq220fileName() {
        return enq220fileName__;
    }

    /**
     * <p>enq220fileName をセットします。
     * @param enq220fileName enq220fileName
     */
    public void setEnq220fileName(String enq220fileName) {
        enq220fileName__ = enq220fileName;
    }

    /**
     * <p>enq220ViewPubDateTo を取得します。
     * @return enq220ViewPubDateTo
     */
    public String getEnq220ViewPubDateTo() {
        return enq220ViewPubDateTo__;
    }


    /**
     * <p>enq220ViewPubDateTo をセットします。
     * @param enq220ViewPubDateTo enq220ViewPubDateTo
     */
    public void setEnq220ViewPubDateTo(String enq220ViewPubDateTo) {
        enq220ViewPubDateTo__ = enq220ViewPubDateTo;
    }
    /**
     * <p>enq220ViewDesc を取得します。
     * @return enq220ViewDesc
     */
    public String getEnq220ViewDesc() {
        return enq220ViewDesc__;
    }

    /**
     * <p>enq220ViewDesc をセットします。
     * @param enq220ViewDesc enq220ViewDesc
     */
    public void setEnq220ViewDesc(String enq220ViewDesc) {
        enq220ViewDesc__ = enq220ViewDesc;
    }

    /**
     * <p>enq220deleteRow を取得します。
     * @return enq220deleteRow
     */
    public int getEnq220deleteRow() {
        return enq220deleteRow__;
    }

    /**
     * <p>enq220deleteRow をセットします。
     * @param enq220deleteRow enq220deleteRow
     */
    public void setEnq220deleteRow(int enq220deleteRow) {
        enq220deleteRow__ = enq220deleteRow;
    }

    /**
     * <p>enq220selectRow を取得します。
     * @return enq220selectRow
     */
    public int getEnq220selectRow() {
        return enq220selectRow__;
    }

    /**
     * <p>enq220selectRow をセットします。
     * @param enq220selectRow enq220selectRow
     */
    public void setEnq220selectRow(int enq220selectRow) {
        enq220selectRow__ = enq220selectRow;
    }

    /**
     * <p>subList を取得します。
     * @return subList
     */
    public List<Enq220SubForm> getSubListToList() {
        return subList__;
    }
    /**
     * <p>subList をセットします。
     * @param subList subList
     */
    public void setSubListForm(List<Enq220SubForm> subList) {
        subList__ = subList;
    }

    /**
     * <p>Enq220SubForm を取得します。
     * @param iIndex インデックス番号
     * @return Enq220SubForm を戻す
     */
    public Enq220SubForm getSubList(int iIndex) {
        while (subList__.size() <= iIndex) {
            subList__.add(new Enq220SubForm());
        }
        return subList__.get(iIndex);
    }
    /**
     * <p>Enq220SubForm を取得します
     * @return Enq220SubForm[]
     */
    public Enq220SubForm[] getSubList() {
        int size = 0;
        if (subList__ != null) {
            size = subList__.size();
        }
        return (Enq220SubForm[]) subList__.toArray(new Enq220SubForm[size]);
    }

    /**
     * <p>enq220scrollQuestonFlg を取得します。
     * @return enq220scrollQuestonFlg
     */
    public int getEnq220scrollQuestonFlg() {
        return enq220scrollQuestonFlg__;
    }

    /**
     * <p>enq220scrollQuestonFlg をセットします。
     * @param enq220scrollQuestonFlg enq220scrollQuestonFlg
     */
    public void setEnq220scrollQuestonFlg(int enq220scrollQuestonFlg) {
        enq220scrollQuestonFlg__ = enq220scrollQuestonFlg;
    }

    /**
     * コンストラクタ
     */
    public Enq220Form() {
        subList__ = new ArrayList<Enq220SubForm>();
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリパス
     * @return エラー
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl, String tempDir)
    throws IOToolsException, IllegalAccessException,
        InvocationTargetException, NoSuchMethodException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String paramNameJpn = null;

        //設問番号(手動時)
        if (getEnq210queSeqType() == QUE_SEQTYPE_MANUAL
        && getEnq210queType() != GSConstEnquete.SYURUI_COMMENT) {
            EnqValidateUtil.validateTextBoxInput(errors, enq220QueNo__,
                    "enq220QueNo",
                    gsMsg.getMessage("enq.09"),
                    GSConstEnquete.MAX_LEN_EQM_QUE_SEC, true);
        }

        //設問
        if (getEnq210queType() != GSConstEnquete.SYURUI_COMMENT) {
            EnqValidateUtil.validateTextBoxInput(errors, enq220Question__,
                    "enq220Question",
                    gsMsg.getMessage("enq.12"),
                    GSConstEnquete.MAX_LEN_EQM_QUESTION, true);
        }

        //添付ファイル
        int attachKbn = NullDefault.getInt(getEnq220AttachKbn(), 0);
        if (attachKbn == GSConstEnquete.EQM_ATTACH_KBN_IMAGE
        || attachKbn == GSConstEnquete.EQM_ATTACH_KBN_FILE) {
            Enq220Biz biz = new Enq220Biz();
            String fileName = biz.getQueEditTempFileName(reqMdl);
            String fieldfix = "enq220attachFile.";
            String msgKey = null;
            if (StringUtil.isNullZeroString(fileName)) {
                msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey,
                                                    gsMsg.getMessage("cmn.attach.file"));
                StrutsUtil.addMessage(
                        errors, msg, fieldfix + msgKey);
            } else if (attachKbn == GSConstEnquete.EQM_ATTACH_KBN_IMAGE
            && !Cmn110Biz.isExtensionOk(fileName)) {
                msgKey = "error.select2.required.extent";
                ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("cmn.attach.file"));
                StrutsUtil.addMessage(
                        errors, msg, fieldfix + msgKey);
            }
        }

        //設問種類
        switch (getEnq210queType()) {

            case GSConstEnquete.SYURUI_SINGLE:
                //単一選択

                //表示名が一つも作成されていない時
                if (getSubListToList().size() < 2) {
                    ActionMessage msg = new ActionMessage(
                            "error.input.range0over.data", gsMsg.getMessage("cmn.choice"), 2 + "つ");
                    StrutsUtil.addMessage(
                            errors, msg, "cmn.display.name");
                }

                int enqDefFlgSum = 0;
                ArrayList<String> singleNames = new ArrayList<String>();
                for (int idx = 0; idx < getSubListToList().size(); idx++) {
                    paramNameJpn = gsMsg.getMessage("cmn.choice") + " "
                            + gsMsg.getMessageVal0("cmn.line2", String.valueOf(idx + 1))
                            + gsMsg.getMessage("cmn.display.name");

                    //表示名入力チェック
                    EnqValidateUtil.validateTypeName(errors,
                                                     getSubListToList().get(idx).getEnqDspName(),
                                                     "enqDspName" + idx,
                                                     paramNameJpn,
                                                     GSConstEnquete.MAX_LEN_EQS_DSP_NAME,
                                                     singleNames);

                    singleNames.add(getSubListToList().get(idx).getEnqDspName());

                    enqDefFlgSum += getSubList(idx).getEnqDefFlg();
                }

                //初期値入力チェック
                EnqValidateUtil.validateEnqDefFlg(errors,
                        gsMsg.getMessage("ntp.10"),
                        gsMsg.getMessage("ntp.10"),
                        enqDefFlgSum);

                break;

            case GSConstEnquete.SYURUI_MULTIPLE:
                //複数選択

                //表示名が一つも作成されていない時
                if (getSubListToList().size() < 2) {
                    ActionMessage msg = new ActionMessage(
                            "error.input.range0over.data", gsMsg.getMessage("cmn.choice"), 2 + "つ");
                    StrutsUtil.addMessage(
                            errors, msg, "cmn.display.name");
                }

                ArrayList<String> multiNames = new ArrayList<String>();
                //表示名入力チェック
                for (int idx = 0; idx < getSubListToList().size(); idx++) {
                    paramNameJpn = gsMsg.getMessage("cmn.choice") + " "
                            + gsMsg.getMessageVal0("cmn.line2", String.valueOf(idx + 1))
                            + gsMsg.getMessage("cmn.display.name");

                    EnqValidateUtil.validateTypeName(errors,
                                                     getSubListToList().get(idx).getEnqDspName(),
                                                     "enqDspName" + idx,
                                                     paramNameJpn,
                                                     GSConstEnquete.MAX_LEN_EQS_DSP_NAME,
                                                     multiNames);
                    multiNames.add(getSubListToList().get(idx).getEnqDspName());
                }


                break;

            case GSConstEnquete.SYURUI_TEXT:
                //テキスト入力

                //初期値設定時
                if (enq220DefKbn__ == GSConstEnquete.INIT_ON) {

                    //テキストボックス入力チェック
                    EnqValidateUtil.validateTextBoxInput(errors, enq220DefTxt__,
                            "enq220DefTxt",
                            gsMsg.getMessage("ntp.10"),
                            GSConstEnquete.MAX_LEN_EQM_QUESTION, true);
                }
                break;

            case GSConstEnquete.SYURUI_TEXTAREA:
                //テキスト入力(複数)

                if (enq220DefKbn__ == GSConstEnquete.INIT_ON) {

                    //テキストエリア入力チェック
                    EnqValidateUtil.validateTextarea(errors, enq220DefTxt__,
                            "enq220DefTxt",
                            gsMsg.getMessage("ntp.10"),
                            GSConstEnquete.MAX_LEN_EQM_DESC, true);
                }
                break;

            case GSConstEnquete.SYURUI_INTEGER:
                //数値入力

                /** 初期値有り かつ 入力範囲有り */
                if (enq220DefKbn__ == GSConstEnquete.INIT_ON
                        && enq220RngKbn__ == GSConstEnquete.RNG_ON) {

                    // 初期値入力チェック
                    EnqValidateUtil.validateIntTextBox(errors,
                            enq220DefNum__,
                            "enq220DefNum",
                            gsMsg.getMessage("ntp.10"),
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                            true);

                    // 入力範囲 開始値
                    EnqValidateUtil.validateIntTextBox(errors,
                            enq220RngStrNum__,
                            "enq220RngStrNum",
                            gsMsg.getMessage("enq.enq220.01"),
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                            true);

                    // 入力範囲 終了値
                    EnqValidateUtil.validateIntTextBox(errors,
                            enq220RngEndNum__,
                            "enq220RngEndNum",
                            gsMsg.getMessage("enq.enq220.02"),
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                            true);

                    // 入力範囲のフォーマット
                    EnqValidateUtil.validateDoubleRange(reqMdl, errors,
                            enq220DefNum__, enq220RngStrNum__, enq220RngEndNum__, true);

                }

                /** 初期値あり かつ 入力範囲無し */
                if (enq220DefKbn__ == GSConstEnquete.INIT_ON
                        && enq220RngKbn__ == GSConstEnquete.RNG_OFF) {

                    // 初期値 入力チェック
                    EnqValidateUtil.validateIntTextBox(errors,
                            enq220DefNum__,
                            "enq220DefNum",
                            gsMsg.getMessage("ntp.10"),
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                            true);

                }

                /** 初期値無し かつ 入力範囲有り */
                if (enq220DefKbn__ == GSConstEnquete.INIT_OFF
                        && enq220RngKbn__ == GSConstEnquete.RNG_ON) {

                    // 入力範囲 開始値
                    EnqValidateUtil.validateIntTextBox(errors,
                            enq220RngStrNum__,
                            "enq220RngStrNum",
                            gsMsg.getMessage("enq.enq220.01"),
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                            true);

                    // 入力範囲 終了値
                    EnqValidateUtil.validateIntTextBox(errors,
                            enq220RngEndNum__,
                            "enq220RngEndNum",
                            gsMsg.getMessage("enq.enq220.02"),
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                            true);

                    // 入力範囲のフォーマット
                    EnqValidateUtil.validateDoubleRange(reqMdl, errors, enq220RngStrNum__,
                            enq220RngStrNum__, enq220RngEndNum__, false);

                }

                //単位入力チェック
                EnqValidateUtil.validateTextBoxInput(errors, enq220UnitNum__,
                        "enq220UnitNum",
                        gsMsg.getMessage("ntp.102"),
                        GSConstEnquete.MAX_LEN_EQM_QUE_SEC, true);
                break;

            case GSConstEnquete.SYURUI_DAY:
                //日付入力

                DateTimePickerBiz dateBiz = new DateTimePickerBiz();
                
                /** 初期値有り かつ 入力範囲有り */
                if (enq220DefKbn__ == GSConstEnquete.INIT_ON
                        && enq220RngKbn__ == GSConstEnquete.RNG_ON) {
                    
                    int errorCnt = errors.size();
                    //初期値 日付論理チェック
                    errors.add(dateBiz.setYmdParam(this, "enq220DefDate", "enq220DefDateYear",
                            "enq220DefDateMonth", "enq220DefDateDay", gsMsg.getMessage("ntp.10")));
                    
                    String rangeStart = gsMsg.getMessage("cmn.input.range")
                            + " " + gsMsg.getMessage("cmn.start");
                    errors.add(dateBiz.setYmdParam(this, "enq220RngStrDate", "enq220RngStrDateYear",
                            "enq220RngStrDateMonth", "enq220RngStrDateDay", rangeStart));
                    
                    String rangeEnd =
                            gsMsg.getMessage("cmn.input.range") + " " + gsMsg.getMessage("cmn.end");
                    errors.add(dateBiz.setYmdParam(this, "enq220RngEndDate", "enq220RngEndDateYear",
                            "enq220RngEndDateMonth", "enq220RngEndDateDay", rangeEnd));
                    
                    if (errorCnt == errors.size()) {
                      //入力範囲開始範囲チェック <= 初期値
                        EnqValidateUtil.validateDateRange(reqMdl, errors,
                                gsMsg.getMessage("cmn.input.range"),
                                gsMsg.getMessage("cmn.input.range")
                                + " " + gsMsg.getMessage("cmn.start"),
                                gsMsg.getMessage("ntp.10"),
                                enq220RngStrDateYear__,
                                enq220RngStrDateMonth__,
                                enq220RngStrDateDay__,
                                enq220DefDateYear__,
                                enq220DefDateMonth__,
                                enq220DefDateDay__,
                                1);

                        //初期値 <= 入力範囲終了範囲チェック
                        EnqValidateUtil.validateDateRange(reqMdl, errors,
                                gsMsg.getMessage("cmn.input.range"),
                                gsMsg.getMessage("ntp.10"),
                                gsMsg.getMessage("cmn.input.range")
                                + " " + gsMsg.getMessage("cmn.end"),
                                enq220DefDateYear__,
                                enq220DefDateMonth__,
                                enq220DefDateDay__,
                                enq220RngEndDateYear__,
                                enq220RngEndDateMonth__,
                                enq220RngEndDateDay__,
                                1);

                        //入力範囲論理チェック
                        EnqValidateUtil.validateDateRange(reqMdl, errors,
                                gsMsg.getMessage("cmn.input.range"),
                                gsMsg.getMessage("cmn.input.range")
                                    + " " + gsMsg.getMessage("cmn.start"),
                                gsMsg.getMessage("cmn.input.range")
                                + " " + gsMsg.getMessage("cmn.end"),
                                enq220RngStrDateYear__,
                                enq220RngStrDateMonth__,
                                enq220RngStrDateDay__,
                                enq220RngEndDateYear__,
                                enq220RngEndDateMonth__,
                                enq220RngEndDateDay__,
                                2);

                    }
                }

                /** 初期値有り かつ 入力範囲無し */
                if (enq220DefKbn__ == GSConstEnquete.INIT_ON
                        && enq220RngKbn__ == GSConstEnquete.RNG_OFF) {
                    //初期値日付論理チェック
                    errors.add(dateBiz.setYmdParam(this, "enq220DefDate", "enq220DefDateYear",
                            "enq220DefDateMonth", "enq220DefDateDay", gsMsg.getMessage("ntp.10")));
                }

                /** 初期値無し かつ 入力範囲有り */
                if (enq220DefKbn__ == GSConstEnquete.INIT_OFF
                        && enq220RngKbn__ == GSConstEnquete.RNG_ON) {
                    
                    int errorCnt = errors.size();
                    

                    String rangeStart = gsMsg.getMessage("cmn.input.range")
                            + " " + gsMsg.getMessage("cmn.start");
                    errors.add(dateBiz.setYmdParam(this, "enq220RngStrDate",
                            "enq220RngStrDateYear", "enq220RngStrDateMonth",
                            "enq220RngStrDateDay", rangeStart));

                    String rangeEnd =
                            gsMsg.getMessage("cmn.input.range") + " " + gsMsg.getMessage("cmn.end");
                    errors.add(dateBiz.setYmdParam(this, "enq220RngEndDate", "enq220RngEndDateYear",
                            "enq220RngEndDateMonth", "enq220RngEndDateDay", rangeEnd));


                        if (errorCnt == errors.size()) {
                            //入力範囲論理チェック
                            EnqValidateUtil.validateDateRange(reqMdl, errors,
                                    gsMsg.getMessage("cmn.input.range"),
                                    gsMsg.getMessage("cmn.input.range")
                                    + " " + gsMsg.getMessage("cmn.start"),
                                    gsMsg.getMessage("cmn.input.range")
                                    + " " + gsMsg.getMessage("cmn.end"),
                                    enq220RngStrDateYear__,
                                    enq220RngStrDateMonth__,
                                    enq220RngStrDateDay__,
                                    enq220RngEndDateYear__,
                                    enq220RngEndDateMonth__,
                                    enq220RngEndDateDay__,
                                    2);
                        }
                }
                break;

            default:
                break;
        }

        return errors;
    }
}

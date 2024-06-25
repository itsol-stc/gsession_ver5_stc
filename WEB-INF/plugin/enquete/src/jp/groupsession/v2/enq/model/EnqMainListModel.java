package jp.groupsession.v2.enq.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 設問情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqMainListModel extends AbstractModel {

    /** アンケートSID */
    private long emnSid__ = -1;
    /** 設問連番 */
    private int eqmSeq__ = -1;
    /** 表示順 */
    private int eqmDspSec__ = -1;
    /** 設問番号 */
    private String eqmQueSec__ = null;
    /** 設問 */
    private String eqmQuestion__ = null;
    /** 設問種類区分 */
    private int eqmQueKbn__ = -1;
    /** 必須フラグ */
    private String eqmRequire__ = null;
    /** その他入力有無 */
    private int eqmOther__ = 0;
    /** 説明 */
    private String eqmDesc__ = null;
    /** 添付区分 */
    private int eqmAttachKbn__ = 0;
    /** 添付ファイルSID */
    private String eqmAttachId__ = null;
    /** 添付名 */
    private String eqmAttachName__ = null;
    /** 添付位置 */
    private int eqmAttachPos__ = 0;
    /** 添付ファイルサイズ */
    private String eqmAttachSize__ = null;
    /** URL */
    private String eqmUrl__ = null;
    /** 表示名 */
    private String eqmDspName__ = null;
    /** テンポラリディレクトリ */
    private String eqmAttachDir__ = null;

    /** 初期値_文字 */
    private String eqmDefTxt__ = null;
    /** 初期値_数値 */
    private String eqmDefNum__ = null;
    /** 初期値_日付 */
    private UDate eqmDefDat__ = null;
    /** 初期値_日付_年 */
    private String eqmDefDatYear__ = null;
    /** 初期値_日付_月 */
    private String eqmDefDatMonth__ = null;
    /** 初期値_日付_日 */
    private String eqmDefDatDay__ = null;
    /** 初期値 */
    private String eqmDef__ = null;
    /** 範囲_数値_開始 */
    private String eqmRngStrNum__ = null;
    /** 範囲_数値_終了 */
    private String eqmRngEndNum__ = null;
    /** 範囲_日付_開始 */
    private String eqmRngStrDat__ = null;
    /** 範囲_日付_終了 */
    private String eqmRngEndDat__ = null;
    /** 単位 */
    private String eqmUnitNum__ = null;
    /** 横線位置（コメント行） */
    private int eqmLineKbn__ = 0;
    /** グラフ種類（初期値） */
    private int eqmGrfKbn__ = 0;

    /** 回答欄 テキスト */
    private String eqmAnsText__ = null;
    /** 回答欄 テキストエリア */
    private String eqmAnsTextarea__ = null;
    /** 回答欄 数値 */
    private String eqmAnsNum__ = null;
    /** 回答欄 年 */
    private int eqmSelectAnsYear__ = -1;
    /** 回答欄 月 */
    private int eqmSelectAnsMonth__ = -1;
    /** 回答欄 年 */
    private int eqmSelectAnsDay__ = -1;
    /** 回答欄 表示用年月日 */
    private String eqmSelectAnsDate__ = null;
    /** 回答欄 入力用年月日 */
    private String eqmSelectAnsDateInput__ = null;

    /** 回答 選択ラジオボタン */
    private String eqmSelRbn__ = null;
    /** 回答 選択ラジオボタン 表示名 */
    private String eqmSelRbnName__ = null;
    /** 回答 選択チェックボックス */
    private String[] eqmSelChk__ = null;
    /** 回答 選択チェックボックス 表示名 */
    private ArrayList<LabelValueBean> eqmSelChkName__ = null;
    /** 回答 その他 テキスト */
    private String eqmSelOther__ = null;

    /** 設問サブリスト */
    private List<EnqSubListModel> eqmSubList__ = null;

    /**
     * <p>emnSid を取得します。
     * @return emnSid
     */
    public long getEmnSid() {
        return emnSid__;
    }

    /**
     * <p>emnSid をセットします。
     * @param emnSid emnSid
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
    }

    /**
     * <p>eqmSeq を取得します。
     * @return eqmSeq
     */
    public int getEqmSeq() {
        return eqmSeq__;
    }

    /**
     * <p>eqmSeq をセットします。
     * @param eqmSeq eqmSeq
     */
    public void setEqmSeq(int eqmSeq) {
        eqmSeq__ = eqmSeq;
    }

    /**
     * <p>eqmDspSec を取得します。
     * @return eqmDspSec
     */
    public int getEqmDspSec() {
        return eqmDspSec__;
    }

    /**
     * <p>eqmDspSec をセットします。
     * @param eqmDspSec eqmDspSec
     */
    public void setEqmDspSec(int eqmDspSec) {
        eqmDspSec__ = eqmDspSec;
    }

    /**
     * <p>eqmQueSec を取得します。
     * @return eqmQueSec
     */
    public String getEqmQueSec() {
        return eqmQueSec__;
    }

    /**
     * <p>eqmQueSec をセットします。
     * @param eqmQueSec eqmQueSec
     */
    public void setEqmQueSec(String eqmQueSec) {
        eqmQueSec__ = eqmQueSec;
    }

    /**
     * <p>eqmQuestion を取得します。
     * @return eqmQuestion
     */
    public String getEqmQuestion() {
        return eqmQuestion__;
    }

    /**
     * <p>eqmQuestion をセットします。
     * @param eqmQuestion eqmQuestion
     */
    public void setEqmQuestion(String eqmQuestion) {
        eqmQuestion__ = eqmQuestion;
    }

    /**
     * <p>eqmQueKbn を取得します。
     * @return eqmQueKbn
     */
    public int getEqmQueKbn() {
        return eqmQueKbn__;
    }

    /**
     * <p>eqmQueKbn をセットします。
     * @param eqmQueKbn eqmQueKbn
     */
    public void setEqmQueKbn(int eqmQueKbn) {
        eqmQueKbn__ = eqmQueKbn;
    }

    /**
     * <p>eqmRequire を取得します。
     * @return eqmRequire
     */
    public String getEqmRequire() {
        return eqmRequire__;
    }

    /**
     * <p>eqmRequire をセットします。
     * @param eqmRequire eqmRequire
     */
    public void setEqmRequire(String eqmRequire) {
        eqmRequire__ = eqmRequire;
    }

    /**
     * <p>eqmOther を取得します。
     * @return eqmOther
     */
    public int getEqmOther() {
        return eqmOther__;
    }

    /**
     * <p>eqmOther をセットします。
     * @param eqmOther eqmOther
     */
    public void setEqmOther(int eqmOther) {
        eqmOther__ = eqmOther;
    }

    /**
     * <p>eqmDesc を取得します。
     * @return eqmDesc
     */
    public String getEqmDesc() {
        return eqmDesc__;
    }

    /**
     * <p>eqmDesc をセットします。
     * @param eqmDesc eqmDesc
     */
    public void setEqmDesc(String eqmDesc) {
        eqmDesc__ = eqmDesc;
    }

    /**
     * <p>eqmAttachKbn を取得します。
     * @return eqmAttachKbn
     */
    public int getEqmAttachKbn() {
        return eqmAttachKbn__;
    }

    /**
     * <p>eqmAttachKbn をセットします。
     * @param eqmAttachKbn eqmAttachKbn
     */
    public void setEqmAttachKbn(int eqmAttachKbn) {
        eqmAttachKbn__ = eqmAttachKbn;
    }

    /**
     * <p>eqmAttachId を取得します。
     * @return eqmAttachId
     */
    public String getEqmAttachId() {
        return eqmAttachId__;
    }

    /**
     * <p>eqmAttachId をセットします。
     * @param eqmAttachId eqmAttachId
     */
    public void setEqmAttachId(String eqmAttachId) {
        eqmAttachId__ = eqmAttachId;
    }

    /**
     * <p>eqmAttachName を取得します。
     * @return eqmAttachName
     */
    public String getEqmAttachName() {
        return eqmAttachName__;
    }

    /**
     * <p>eqmAttachName をセットします。
     * @param eqmAttachName eqmAttachName
     */
    public void setEqmAttachName(String eqmAttachName) {
        eqmAttachName__ = eqmAttachName;
    }

    /**
     * <p>eqmAttachPos を取得します。
     * @return eqmAttachPos
     */
    public int getEqmAttachPos() {
        return eqmAttachPos__;
    }

    /**
     * <p>eqmAttachPos をセットします。
     * @param eqmAttachPos eqmAttachPos
     */
    public void setEqmAttachPos(int eqmAttachPos) {
        eqmAttachPos__ = eqmAttachPos;
    }

    /**
     * <p>eqmAttachSize を取得します。
     * @return eqmAttachSize
     */
    public String getEqmAttachSize() {
        return eqmAttachSize__;
    }

    /**
     * <p>eqmAttachSize をセットします。
     * @param eqmAttachSize eqmAttachSize
     */
    public void setEqmAttachSize(String eqmAttachSize) {
        eqmAttachSize__ = eqmAttachSize;
    }

    /**
     * <p>eqmUrl を取得します。
     * @return eqmUrl
     */
    public String getEqmUrl() {
        return eqmUrl__;
    }

    /**
     * <p>eqmUrl をセットします。
     * @param eqmUrl eqmUrl
     */
    public void setEqmUrl(String eqmUrl) {
        eqmUrl__ = eqmUrl;
    }

    /**
     * <p>eqmDspName を取得します。
     * @return eqmDspName
     */
    public String getEqmDspName() {
        return eqmDspName__;
    }

    /**
     * <p>eqmDspName をセットします。
     * @param eqmDspName eqmDspName
     */
    public void setEqmDspName(String eqmDspName) {
        eqmDspName__ = eqmDspName;
    }

    /**
     * <p>eqmAttachDir を取得します。
     * @return eqmAttachDir
     */
    public String getEqmAttachDir() {
        return eqmAttachDir__;
    }

    /**
     * <p>eqmAttachDir をセットします。
     * @param eqmAttachDir eqmAttachDir
     */
    public void setEqmAttachDir(String eqmAttachDir) {
        eqmAttachDir__ = eqmAttachDir;
    }

    /**
     * <p>eqmDefTxt を取得します。
     * @return eqmDefTxt
     */
    public String getEqmDefTxt() {
        return eqmDefTxt__;
    }

    /**
     * <p>eqmDefTxt をセットします。
     * @param eqmDefTxt eqmDefTxt
     */
    public void setEqmDefTxt(String eqmDefTxt) {
        eqmDefTxt__ = eqmDefTxt;
    }

    /**
     * <p>eqmDefNum を取得します。
     * @return eqmDefNum
     */
    public String getEqmDefNum() {
        return eqmDefNum__;
    }

    /**
     * <p>eqmDefNum をセットします。
     * @param eqmDefNum eqmDefNum
     */
    public void setEqmDefNum(String eqmDefNum) {
        eqmDefNum__ = eqmDefNum;
    }

    /**
     * <p>eqmDefDat を取得します。
     * @return eqmDefDat
     */
    public UDate getEqmDefDat() {
        return eqmDefDat__;
    }

    /**
     * <p>eqmDefDat をセットします。
     * @param eqmDefDat eqmDefDat
     */
    public void setEqmDefDat(UDate eqmDefDat) {
        eqmDefDat__ = eqmDefDat;
    }

    /**
     * <p>eqmDefDatYear を取得します。
     * @return eqmDefDatYear
     */
    public String getEqmDefDatYear() {
        return eqmDefDatYear__;
    }

    /**
     * <p>eqmDefDatYear をセットします。
     * @param eqmDefDatYear eqmDefDatYear
     */
    public void setEqmDefDatYear(String eqmDefDatYear) {
        eqmDefDatYear__ = eqmDefDatYear;
    }

    /**
     * <p>eqmDefDatMonth を取得します。
     * @return eqmDefDatMonth
     */
    public String getEqmDefDatMonth() {
        return eqmDefDatMonth__;
    }

    /**
     * <p>eqmDefDatMonth をセットします。
     * @param eqmDefDatMonth eqmDefDatMonth
     */
    public void setEqmDefDatMonth(String eqmDefDatMonth) {
        eqmDefDatMonth__ = eqmDefDatMonth;
    }

    /**
     * <p>eqmDefDatDay を取得します。
     * @return eqmDefDatDay
     */
    public String getEqmDefDatDay() {
        return eqmDefDatDay__;
    }

    /**
     * <p>eqmDefDatDay をセットします。
     * @param eqmDefDatDay eqmDefDatDay
     */
    public void setEqmDefDatDay(String eqmDefDatDay) {
        eqmDefDatDay__ = eqmDefDatDay;
    }

    /**
     * <p>eqmDef を取得します。
     * @return eqmDef
     */
    public String getEqmDef() {
        return eqmDef__;
    }

    /**
     * <p>eqmDef をセットします。
     * @param eqmDef eqmDef
     */
    public void setEqmDef(String eqmDef) {
        eqmDef__ = eqmDef;
    }

    /**
     * <p>eqmRngStrNum を取得します。
     * @return eqmRngStrNum
     */
    public String getEqmRngStrNum() {
        return eqmRngStrNum__;
    }

    /**
     * <p>eqmRngStrNum をセットします。
     * @param eqmRngStrNum eqmRngStrNum
     */
    public void setEqmRngStrNum(String eqmRngStrNum) {
        eqmRngStrNum__ = eqmRngStrNum;
    }

    /**
     * <p>eqmRngEndNum を取得します。
     * @return eqmRngEndNum
     */
    public String getEqmRngEndNum() {
        return eqmRngEndNum__;
    }

    /**
     * <p>eqmRngEndNum をセットします。
     * @param eqmRngEndNum eqmRngEndNum
     */
    public void setEqmRngEndNum(String eqmRngEndNum) {
        eqmRngEndNum__ = eqmRngEndNum;
    }

    /**
     * <p>eqmRngStrDat を取得します。
     * @return eqmRngStrDat
     */
    public String getEqmRngStrDat() {
        return eqmRngStrDat__;
    }

    /**
     * <p>eqmRngStrDat をセットします。
     * @param eqmRngStrDat eqmRngStrDat
     */
    public void setEqmRngStrDat(String eqmRngStrDat) {
        eqmRngStrDat__ = eqmRngStrDat;
    }

    /**
     * <p>eqmRngEndDat を取得します。
     * @return eqmRngEndDat
     */
    public String getEqmRngEndDat() {
        return eqmRngEndDat__;
    }

    /**
     * <p>eqmRngEndDat をセットします。
     * @param eqmRngEndDat eqmRngEndDat
     */
    public void setEqmRngEndDat(String eqmRngEndDat) {
        eqmRngEndDat__ = eqmRngEndDat;
    }

    /**
     * <p>eqmUnitNum を取得します。
     * @return eqmUnitNum
     */
    public String getEqmUnitNum() {
        return eqmUnitNum__;
    }

    /**
     * <p>eqmUnitNum をセットします。
     * @param eqmUnitNum eqmUnitNum
     */
    public void setEqmUnitNum(String eqmUnitNum) {
        eqmUnitNum__ = eqmUnitNum;
    }

    /**
     * <p>eqmLineKbn を取得します。
     * @return eqmLineKbn
     */
    public int getEqmLineKbn() {
        return eqmLineKbn__;
    }

    /**
     * <p>eqmLineKbn をセットします。
     * @param eqmLineKbn eqmLineKbn
     */
    public void setEqmLineKbn(int eqmLineKbn) {
        eqmLineKbn__ = eqmLineKbn;
    }

    /**
     * <p>eqmGrfKbn を取得します。
     * @return eqmGrfKbn
     */
    public int getEqmGrfKbn() {
        return eqmGrfKbn__;
    }

    /**
     * <p>eqmGrfKbn をセットします。
     * @param eqmGrfKbn eqmGrfKbn
     */
    public void setEqmGrfKbn(int eqmGrfKbn) {
        eqmGrfKbn__ = eqmGrfKbn;
    }

    /**
     * <p>eqmAnsText を取得します。
     * @return eqmAnsText
     */
    public String getEqmAnsText() {
        return eqmAnsText__;
    }

    /**
     * <p>eqmAnsText をセットします。
     * @param eqmAnsText eqmAnsText
     */
    public void setEqmAnsText(String eqmAnsText) {
        eqmAnsText__ = eqmAnsText;
    }

    /**
     * <p>eqmAnsTextarea を取得します。
     * @return eqmAnsTextarea
     */
    public String getEqmAnsTextarea() {
        return eqmAnsTextarea__;
    }

    /**
     * <p>eqmAnsTextarea をセットします。
     * @param eqmAnsTextarea eqmAnsTextarea
     */
    public void setEqmAnsTextarea(String eqmAnsTextarea) {
        eqmAnsTextarea__ = eqmAnsTextarea;
    }

    /**
     * <p>eqmAnsNum を取得します。
     * @return eqmAnsNum
     */
    public String getEqmAnsNum() {
        return eqmAnsNum__;
    }

    /**
     * <p>eqmAnsNum をセットします。
     * @param eqmAnsNum eqmAnsNum
     */
    public void setEqmAnsNum(String eqmAnsNum) {
        eqmAnsNum__ = eqmAnsNum;
    }

    /**
     * <p>eqmSelectAnsYear を取得します。
     * @return eqmSelectAnsYear
     */
    public int getEqmSelectAnsYear() {
        return eqmSelectAnsYear__;
    }

    /**
     * <p>eqmSelectAnsYear をセットします。
     * @param eqmSelectAnsYear eqmSelectAnsYear
     */
    public void setEqmSelectAnsYear(int eqmSelectAnsYear) {
        eqmSelectAnsYear__ = eqmSelectAnsYear;
    }

    /**
     * <p>eqmSelectAnsMonth を取得します。
     * @return eqmSelectAnsMonth
     */
    public int getEqmSelectAnsMonth() {
        return eqmSelectAnsMonth__;
    }

    /**
     * <p>eqmSelectAnsMonth をセットします。
     * @param eqmSelectAnsMonth eqmSelectAnsMonth
     */
    public void setEqmSelectAnsMonth(int eqmSelectAnsMonth) {
        eqmSelectAnsMonth__ = eqmSelectAnsMonth;
    }

    /**
     * <p>eqmSelectAnsDay を取得します。
     * @return eqmSelectAnsDay
     */
    public int getEqmSelectAnsDay() {
        return eqmSelectAnsDay__;
    }

    /**
     * <p>eqmSelectAnsDay をセットします。
     * @param eqmSelectAnsDay eqmSelectAnsDay
     */
    public void setEqmSelectAnsDay(int eqmSelectAnsDay) {
        eqmSelectAnsDay__ = eqmSelectAnsDay;
    }

    /**
     * <p>eqmSelectAnsDate を取得します。
     * @return eqmSelectAnsDate
     */
    public String getEqmSelectAnsDate() {
        return eqmSelectAnsDate__;
    }

    /**
     * <p>eqmSelectAnsDate をセットします。
     * @param eqmSelectAnsDate eqmSelectAnsDate
     */
    public void setEqmSelectAnsDate(String eqmSelectAnsDate) {
        eqmSelectAnsDate__ = eqmSelectAnsDate;
    }
    
    /**
     * <p>eqmSelectAnsDateInput を取得します。
     * @return eqmSelectAnsDateInput
     * @see jp.groupsession.v2.enq.model.EnqMainListModel#eqmSelectAnsDateInput__
     */
    public String getEqmSelectAnsDateInput() {
        return eqmSelectAnsDateInput__;
    }

    /**
     * <p>eqmSelectAnsDateInput をセットします。
     * @param eqmSelectAnsDateInput eqmSelectAnsDateInput
     * @see jp.groupsession.v2.enq.model.EnqMainListModel#eqmSelectAnsDateInput__
     */
    public void setEqmSelectAnsDateInput(String eqmSelectAnsDateInput) {
        eqmSelectAnsDateInput__ = eqmSelectAnsDateInput;
    }

    /**
     * <p>eqmSelRbn を取得します。
     * @return eqmSelRbn
     */
    public String getEqmSelRbn() {
        return eqmSelRbn__;
    }

    /**
     * <p>eqmSelRbn をセットします。
     * @param eqmSelRbn eqmSelRbn
     */
    public void setEqmSelRbn(String eqmSelRbn) {
        eqmSelRbn__ = eqmSelRbn;
    }

    /**
     * <p>eqmSelRbnName を取得します。
     * @return eqmSelRbnName
     */
    public String getEqmSelRbnName() {
        return eqmSelRbnName__;
    }

    /**
     * <p>eqmSelRbnName をセットします。
     * @param eqmSelRbnName eqmSelRbnName
     */
    public void setEqmSelRbnName(String eqmSelRbnName) {
        eqmSelRbnName__ = eqmSelRbnName;
    }

    /**
     * <p>eqmSelChk を取得します。
     * @return eqmSelChk
     */
    public String[] getEqmSelChk() {
        return eqmSelChk__;
    }

    /**
     * <p>eqmSelChk をセットします。
     * @param eqmSelChk eqmSelChk
     */
    public void setEqmSelChk(String[] eqmSelChk) {
        eqmSelChk__ = eqmSelChk;
    }

    /**
     * <p>eqmSelChkName を取得します。
     * @return eqmSelChkName
     */
    public ArrayList<LabelValueBean> getEqmSelChkName() {
        return eqmSelChkName__;
    }

    /**
     * <p>eqmSelChkName をセットします。
     * @param eqmSelChkName eqmSelChkName
     */
    public void setEqmSelChkName(ArrayList<LabelValueBean> eqmSelChkName) {
        eqmSelChkName__ = eqmSelChkName;
    }

    /**
     * <p>eqmSelOther を取得します。
     * @return eqmSelOther
     */
    public String getEqmSelOther() {
        return eqmSelOther__;
    }

    /**
     * <p>eqmSelOther をセットします。
     * @param eqmSelOther eqmSelOther
     */
    public void setEqmSelOther(String eqmSelOther) {
        eqmSelOther__ = eqmSelOther;
    }

    /**
     * <p>eqmSubList を取得します。
     * @return eqmSubList
     */
    public List<EnqSubListModel> getEqmSubListToList() {
        return eqmSubList__;
    }

    /**
     * <p>eqmSubList をセットします。
     * @param eqmSubList eqmSubList
     */
    public void setEqmSubListForm(List<EnqSubListModel> eqmSubList) {
        eqmSubList__ = eqmSubList;
    }

    /**
     * <br>[機  能] 設問_選択肢リストを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param index インデックス番号
     * @return 設問_選択肢リスト
     */
    public EnqSubListModel getEqmSubList(int index) {
        while (eqmSubList__.size() <= index) {
            eqmSubList__.add(new EnqSubListModel());
        }
        return eqmSubList__.get(index);
    }

    /**
     * <br>[機  能] 設問_選択肢リストの配列を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return 設問_選択肢[]
     */
    public EnqSubListModel[] getEqmSubList() {
        int size = 0;
        if (eqmSubList__ != null) {
            size = eqmSubList__.size();
        }
        return (EnqSubListModel[]) eqmSubList__.toArray(new EnqSubListModel[size]);
    }

    /**
     * <br>[機  能] 設問_選択肢リストの件数を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 件数
     */
    public int getEqmSubListSize() {
        return eqmSubList__.size();
    }

    /**
     * コンストラクタ
     */
    public EnqMainListModel() {
        eqmSubList__ = new ArrayList<EnqSubListModel>();
    }
}

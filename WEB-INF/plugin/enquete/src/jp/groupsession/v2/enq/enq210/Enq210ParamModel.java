package jp.groupsession.v2.enq.enq210;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.enq.enq230.Enq230ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アンケート 設問登録画面のパラメータ情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq210ParamModel extends Enq230ParamModel {

    /** 編集モード */
    private int enq210editMode__ = 0;
    /** データ区分 */
    private int enq210DataKbn__ = -1;
    /** 重要度 */
    private int enq210Juuyou__ = 0;
    /** アンケート種類 */
    private int enq210Syurui__ = 0;
    /** 発信者 */
    private String enq210Send__ = null;
    /** タイトル */
    private String enq210Title__ = null;
    /** 説明 */
    private String enq210Desc__ = null;
    /** 説明(テキストのみ) */
    private String enq210DescText__ = null;
    /** 添付区分 */
    private int enq210AttachKbn__ = 0;
    /** URL */
    private String enq210Url__ = null;
    /** 表示名 */
    private String enq210TempDspName__ = null;

    /** ファイル保存先 */
    private String enq210fileDir__ = null;
    /** 添付ファイル */
    private String[] enq210file__ = null;
    /** 添付ファイル名 */
    private String enq210fileName__ = null;

    /** 添付位置 */
    private int enq210AttachPos__ = 0;
    /** 公開期間_開始年 */
    private int enq210FrYear__ = 0;
    /** 公開期間_開始月 */
    private int enq210FrMonth__ = 0;
    /** 公開期間_開始日 */
    private int enq210FrDay__ = 0;
    /** 公開期間_終了年 */
    private int enq210ToYear__ = 0;
    /** 公開期間_終了月 */
    private int enq210ToMonth__ = 0;
    /** 公開期間_終了日 */
    private int enq210ToDay__ = 0;
    /** 公開期間_終了日 指定なし */
    private int enq210ToKbn__ = 0;
    /** 公開期間_開始 年月日 */
    private String enq210FrDate__ = null;
    /** 公開期間_終了 年月日 */
    private String enq210ToDate__ = null;
    /** 回答期限_年 */
    private int enq210AnsYear__ = 0;
    /** 回答期限_月 */
    private int enq210AnsMonth__ = 0;
    /** 回答期限_日 */
    private int enq210AnsDay__ = 0;
    /** 回答期限_年月日 */
    private String enq210AnsDate__ = null;
    
    /** 結果公開期間_開始年 */
    private int enq210AnsPubFrYear__ = 0;
    /** 結果公開期間_開始月 */
    private int enq210AnsPubFrMonth__ = 0;
    /** 結果公開期間_開始日 */
    private int enq210AnsPubFrDay__ = 0;
    /** 結果公開期間_開始 年月日 */
    private String enq210AnsPubFrDate__ = null;
    
    /** 匿名フラグ */
    private int enq210Anony__ = 0;
    /** 回答公開フラグ */
    private int enq210AnsOpen__ = 0;
    /** 設問番号(自動/手動) */
    private int enq210queSeqType__ = 0;
    /** ショートメール通知 */
    private int enq210smailInfo__ = 0;
    /** ショートメールプラグイン使用可能フラグ */
    private int enq210pluginSmailUse__ = GSConst.PLUGIN_NOT_USE;

    /** SEQ */
    private int enq210Seq__ = -1;
    /** 初期表示フラグ */
    private int enq210initFlg__ = 0;
    /** テンプレートID */
    private long enq210templateId__ = 0;
    /** 表示位置移動フラグ */
    private int enq210scrollQuestonFlg__ = 0;

    /** 設問種類 */
    private int enq210queType__ = 0;
    /** 設問Index */
    private int enq210queIndex__ = -1;
    /** 設問Index(編集) */
    private int enq210editQueIndex__ = -1;
    /** 設問リスト */
    private List<Enq210QueModel> ebaList__ = null;

    /** サブリスト */
    //private List<Enq210SubForm> subList__ = null;

    /** アンケート種類リスト */
    private List<LabelValueBean> enqTypeList__ = null;
    /** 発信者リスト */
    private List<UsrLabelValueBean> enqSenderList__ = null;
    /** 年リスト */
    private ArrayList <LabelValueBean> enq210YearLabel__ = null;
    /** 月リスト */
    private ArrayList <LabelValueBean> enq210MonthLabel__ = null;
    /** 日リスト */
    private ArrayList <LabelValueBean> enq210DayLabel__ = null;
    /** ファイルコンボ */
    private List <LabelValueBean> enq210FileLabelList__ = null;
    /** テンプレート一覧 */
    private List <LabelValueBean> enq210TemplatelList__ = null;

    /** 対象者グループ */
    private int enq210answerGroup__ = -2;
    /** 対象者 */
    private String[] enq210answerList__ = null;
    /** 対象者コンボ */
    private List<UsrLabelValueBean> selectAnswerCombo__ = null;
    /** 対象者 UI */
    private UserGroupSelector enq210answerListUI__ = null;

    /** 警告フラグ */
    private int enq210Alert__ = 0;
    /** 回答情報削除フラグ */
    private boolean enq210DelAnsFlg__ = false;

    /** 添付ファイル 添付ボタン又は削除ボタンクリック判断フラグ */
    private int tempClickBtn__ = 0;

    /** 回答情報リセットフラグ リセットしない:0 リセットする:1 */
    private int answerDataReset__ = 0;
    
    /** 初期表示 日付文字列設定フラグ */
    private boolean enq210DateInitFlg__ = false;

    /** 説明内ファイルの一時保存ディレクトリID */
    private String enq210TempSaveId__ = null;

    /**
     * <p>enq210editMode を取得します。
     * @return enq210editMode
     */
    public int getEnq210editMode() {
        return enq210editMode__;
    }

    /**
     * <p>enq210editMode をセットします。
     * @param enq210editMode enq210editMode
     */
    public void setEnq210editMode(int enq210editMode) {
        enq210editMode__ = enq210editMode;
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
     * <p>enq210templateId を取得します。
     * @return enq210templateId
     */
    public long getEnq210templateId() {
        return enq210templateId__;
    }

    /**
     * <p>enq210templateId をセットします。
     * @param enq210templateId enq210templateId
     */
    public void setEnq210templateId(long enq210templateId) {
        enq210templateId__ = enq210templateId;
    }

    /**
     * <p>enq210scrollQuestonFlg を取得します。
     * @return enq210scrollQuestonFlg
     */
    public int getEnq210scrollQuestonFlg() {
        return enq210scrollQuestonFlg__;
    }

    /**
     * <p>enq210scrollQuestonFlg をセットします。
     * @param enq210scrollQuestonFlg enq210scrollQuestonFlg
     */
    public void setEnq210scrollQuestonFlg(int enq210scrollQuestonFlg) {
        enq210scrollQuestonFlg__ = enq210scrollQuestonFlg;
    }

    /**
     * <p>enq210queType を取得します。
     * @return enq210queType
     */
    public int getEnq210queType() {
        return enq210queType__;
    }

    /**
     * <p>enq210queType をセットします。
     * @param enq210queType enq210queType
     */
    public void setEnq210queType(int enq210queType) {
        enq210queType__ = enq210queType;
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
     * <p>enq210editQueIndex を取得します。
     * @return enq210editQueIndex
     */
    public int getEnq210editQueIndex() {
        return enq210editQueIndex__;
    }

    /**
     * <p>enq210editQueIndex をセットします。
     * @param enq210editQueIndex enq210editQueIndex
     */
    public void setEnq210editQueIndex(int enq210editQueIndex) {
        enq210editQueIndex__ = enq210editQueIndex;
    }

    /**
     * <p>enq210queSeqType を取得します。
     * @return enq210queSeqType
     */
    public int getEnq210queSeqType() {
        return enq210queSeqType__;
    }

    /**
     * <p>enq210queSeqType をセットします。
     * @param enq210queSeqType enq210queSeqType
     */
    public void setEnq210queSeqType(int enq210queSeqType) {
        enq210queSeqType__ = enq210queSeqType;
    }

    /**
     * <p>enq210DataKbn を取得します。
     * @return enq210DataKbn
     */
    public int getEnq210DataKbn() {
        return enq210DataKbn__;
    }
    /**
     * <p>enq210DataKbn をセットします。
     * @param enq210DataKbn enq210DataKbn
     */
    public void setEnq210DataKbn(int enq210DataKbn) {
        enq210DataKbn__ = enq210DataKbn;
    }
    /**
     * <p>enq210Syurui を取得します。
     * @return enq210Syurui
     */
    public int getEnq210Syurui() {
        return enq210Syurui__;
    }
    /**
     * <p>enq210Syurui をセットします。
     * @param enq210Syurui enq210Syurui
     */
    public void setEnq210Syurui(int enq210Syurui) {
        enq210Syurui__ = enq210Syurui;
    }
    /**
     * <p>enq210Title を取得します。
     * @return enq210Title
     */
    public String getEnq210Title() {
        return enq210Title__;
    }
    /**
     * <p>enq210Title をセットします。
     * @param enq210Title enq210Title
     */
    public void setEnq210Title(String enq210Title) {
        enq210Title__ = enq210Title;
    }
    /**
     * <p>enq210Juuyou を取得します。
     * @return enq210Juuyou
     */
    public int getEnq210Juuyou() {
        return enq210Juuyou__;
    }
    /**
     * <p>enq210Juuyou をセットします。
     * @param enq210Juuyou enq210Juuyou
     */
    public void setEnq210Juuyou(int enq210Juuyou) {
        enq210Juuyou__ = enq210Juuyou;
    }
    /**
     * <p>enq210Desc を取得します。
     * @return enq210Desc
     */
    public String getEnq210Desc() {
        return enq210Desc__;
    }
    /**
     * <p>enq210Desc をセットします。
     * @param enq210Desc enq210Desc
     */
    public void setEnq210Desc(String enq210Desc) {
        enq210Desc__ = enq210Desc;
    }
    /**
     * <p>enq210DescText を取得します。
     * @return enq210DescText
     */
    public String getEnq210DescText() {
        return enq210DescText__;
    }

    /**
     * <p>enq210DescText をセットします。
     * @param enq210DescText enq210DescText
     */
    public void setEnq210DescText(String enq210DescText) {
        enq210DescText__ = enq210DescText;
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
     * <p>enq210fileDir を取得します。
     * @return enq210fileDir
     */
    public String getEnq210fileDir() {
        return enq210fileDir__;
    }

    /**
     * <p>enq210fileDir をセットします。
     * @param enq210fileDir enq210fileDir
     */
    public void setEnq210fileDir(String enq210fileDir) {
        enq210fileDir__ = enq210fileDir;
    }

    /**
     * <p>enq210file を取得します。
     * @return enq210file
     */
    public String[] getEnq210file() {
        return enq210file__;
    }
    /**
     * <p>enq210file をセットします。
     * @param enq210file enq210file
     */
    public void setEnq210file(String[] enq210file) {
        enq210file__ = enq210file;
    }
    /**
     * <p>enq210fileName を取得します。
     * @return enq210fileName
     */
    public String getEnq210fileName() {
        return enq210fileName__;
    }

    /**
     * <p>enq210fileName をセットします。
     * @param enq210fileName enq210fileName
     */
    public void setEnq210fileName(String enq210fileName) {
        enq210fileName__ = enq210fileName;
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
     * <p>enq210FrYear を取得します。
     * @return enq210FrYear
     */
    public int getEnq210FrYear() {
        return enq210FrYear__;
    }

    /**
     * <p>enq210FrYear をセットします。
     * @param enq210FrYear enq210FrYear
     */
    public void setEnq210FrYear(int enq210FrYear) {
        enq210FrYear__ = enq210FrYear;
    }

    /**
     * <p>enq210FrMonth を取得します。
     * @return enq210FrMonth
     */
    public int getEnq210FrMonth() {
        return enq210FrMonth__;
    }

    /**
     * <p>enq210FrMonth をセットします。
     * @param enq210FrMonth enq210FrMonth
     */
    public void setEnq210FrMonth(int enq210FrMonth) {
        enq210FrMonth__ = enq210FrMonth;
    }

    /**
     * <p>enq210FrDay を取得します。
     * @return enq210FrDay
     */
    public int getEnq210FrDay() {
        return enq210FrDay__;
    }

    /**
     * <p>enq210FrDay をセットします。
     * @param enq210FrDay enq210FrDay
     */
    public void setEnq210FrDay(int enq210FrDay) {
        enq210FrDay__ = enq210FrDay;
    }

    /**
     * <p>enq210ToYear を取得します。
     * @return enq210ToYear
     */
    public int getEnq210ToYear() {
        return enq210ToYear__;
    }

    /**
     * <p>enq210ToYear をセットします。
     * @param enq210ToYear enq210ToYear
     */
    public void setEnq210ToYear(int enq210ToYear) {
        enq210ToYear__ = enq210ToYear;
    }

    /**
     * <p>enq210ToMonth を取得します。
     * @return enq210ToMonth
     */
    public int getEnq210ToMonth() {
        return enq210ToMonth__;
    }

    /**
     * <p>enq210ToMonth をセットします。
     * @param enq210ToMonth enq210ToMonth
     */
    public void setEnq210ToMonth(int enq210ToMonth) {
        enq210ToMonth__ = enq210ToMonth;
    }

    /**
     * <p>enq210ToDay を取得します。
     * @return enq210ToDay
     */
    public int getEnq210ToDay() {
        return enq210ToDay__;
    }

    /**
     * <p>enq210ToDay をセットします。
     * @param enq210ToDay enq210ToDay
     */
    public void setEnq210ToDay(int enq210ToDay) {
        enq210ToDay__ = enq210ToDay;
    }
    
    /**
     * <p>enq210FrDate を取得します。
     * @return enq210FrDate
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210FrDate__
     */
    public String getEnq210FrDate() {
        return enq210FrDate__;
    }

    /**
     * <p>enq210FrDate をセットします。
     * @param enq210FrDate enq210FrDate
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210FrDate__
     */
    public void setEnq210FrDate(String enq210FrDate) {
        enq210FrDate__ = enq210FrDate;
    }

    /**
     * <p>enq210ToDate を取得します。
     * @return enq210ToDate
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210ToDate__
     */
    public String getEnq210ToDate() {
        return enq210ToDate__;
    }

    /**
     * <p>enq210ToDate をセットします。
     * @param enq210ToDate enq210ToDate
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210ToDate__
     */
    public void setEnq210ToDate(String enq210ToDate) {
        enq210ToDate__ = enq210ToDate;
    }

    /**
     * <p>enq210ToKbn を取得します。
     * @return enq210ToKbn
     */
    public int getEnq210ToKbn() {
        return enq210ToKbn__;
    }

    /**
     * <p>enq210ToKbn をセットします。
     * @param enq210ToKbn enq210ToKbn
     */
    public void setEnq210ToKbn(int enq210ToKbn) {
        enq210ToKbn__ = enq210ToKbn;
    }

    /**
     * <p>enq210AnsYear を取得します。
     * @return enq210AnsYear
     */
    public int getEnq210AnsYear() {
        return enq210AnsYear__;
    }

    /**
     * <p>enq210AnsYear をセットします。
     * @param enq210AnsYear enq210AnsYear
     */
    public void setEnq210AnsYear(int enq210AnsYear) {
        enq210AnsYear__ = enq210AnsYear;
    }

    /**
     * <p>enq210AnsMonth を取得します。
     * @return enq210AnsMonth
     */
    public int getEnq210AnsMonth() {
        return enq210AnsMonth__;
    }

    /**
     * <p>enq210AnsMonth をセットします。
     * @param enq210AnsMonth enq210AnsMonth
     */
    public void setEnq210AnsMonth(int enq210AnsMonth) {
        enq210AnsMonth__ = enq210AnsMonth;
    }

    /**
     * <p>enq210AnsDay を取得します。
     * @return enq210AnsDay
     */
    public int getEnq210AnsDay() {
        return enq210AnsDay__;
    }

    /**
     * <p>enq210AnsDay をセットします。
     * @param enq210AnsDay enq210AnsDay
     */
    public void setEnq210AnsDay(int enq210AnsDay) {
        enq210AnsDay__ = enq210AnsDay;
    }
    
    /**
     * <p>enq210AnsDate を取得します。
     * @return enq210AnsDate
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210AnsDate__
     */
    public String getEnq210AnsDate() {
        return enq210AnsDate__;
    }

    /**
     * <p>enq210AnsDate をセットします。
     * @param enq210AnsDate enq210AnsDate
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210AnsDate__
     */
    public void setEnq210AnsDate(String enq210AnsDate) {
        enq210AnsDate__ = enq210AnsDate;
    }

    /**
     * <p>enq210AnsPubFrYear を取得します。
     * @return enq210AnsPubFrYear
     */
    public int getEnq210AnsPubFrYear() {
        return enq210AnsPubFrYear__;
    }

    /**
     * <p>enq210AnsPubFrYear をセットします。
     * @param enq210AnsPubFrYear enq210AnsPubFrYear
     */
    public void setEnq210AnsPubFrYear(int enq210AnsPubFrYear) {
        enq210AnsPubFrYear__ = enq210AnsPubFrYear;
    }

    /**
     * <p>enq210AnsPubFrMonth を取得します。
     * @return enq210AnsPubFrMonth
     */
    public int getEnq210AnsPubFrMonth() {
        return enq210AnsPubFrMonth__;
    }

    /**
     * <p>enq210AnsPubFrMonth をセットします。
     * @param enq210AnsPubFrMonth enq210AnsPubFrMonth
     */
    public void setEnq210AnsPubFrMonth(int enq210AnsPubFrMonth) {
        enq210AnsPubFrMonth__ = enq210AnsPubFrMonth;
    }

    /**
     * <p>enq210AnsPubFrDay を取得します。
     * @return enq210AnsPubFrDay
     */
    public int getEnq210AnsPubFrDay() {
        return enq210AnsPubFrDay__;
    }

    /**
     * <p>enq210AnsPubFrDay をセットします。
     * @param enq210AnsPubFrDay enq210AnsPubFrDay
     */
    public void setEnq210AnsPubFrDay(int enq210AnsPubFrDay) {
        enq210AnsPubFrDay__ = enq210AnsPubFrDay;
    }
    
    /**
     * <p>enq210AnsPubFrDate を取得します。
     * @return enq210AnsPubFrDate
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210AnsPubFrDate__
     */
    public String getEnq210AnsPubFrDate() {
        return enq210AnsPubFrDate__;
    }

    /**
     * <p>enq210AnsPubFrDate をセットします。
     * @param enq210AnsPubFrDate enq210AnsPubFrDate
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210AnsPubFrDate__
     */
    public void setEnq210AnsPubFrDate(String enq210AnsPubFrDate) {
        enq210AnsPubFrDate__ = enq210AnsPubFrDate;
    }

    /**
     * <p>enq210Anony を取得します。
     * @return enq210Anony
     */
    public int getEnq210Anony() {
        return enq210Anony__;
    }
    /**
     * <p>enq210Anony をセットします。
     * @param enq210Anony enq210Anony
     */
    public void setEnq210Anony(int enq210Anony) {
        enq210Anony__ = enq210Anony;
    }
    /**
     * <p>enq210AnsOpen を取得します。
     * @return enq210AnsOpen
     */
    public int getEnq210AnsOpen() {
        return enq210AnsOpen__;
    }
    /**
     * <p>enq210AnsOpen をセットします。
     * @param enq210AnsOpen enq210AnsOpen
     */
    public void setEnq210AnsOpen(int enq210AnsOpen) {
        enq210AnsOpen__ = enq210AnsOpen;
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
     * <p>enq210TempDspName を取得します。
     * @return enq210TempDspName
     */
    public String getEnq210TempDspName() {
        return enq210TempDspName__;
    }

    /**
     * <p>enq210TempDspName をセットします。
     * @param enq210TempDspName enq210TempDspName
     */
    public void setEnq210TempDspName(String enq210TempDspName) {
        enq210TempDspName__ = enq210TempDspName;
    }

    /**
     * <p>enq210Send を取得します。
     * @return enq210Send
     */
    public String getEnq210Send() {
        return enq210Send__;
    }

    /**
     * <p>enq210Send をセットします。
     * @param enq210Send enq210Send
     */
    public void setEnq210Send(String enq210Send) {
        enq210Send__ = enq210Send;
    }
    /**
     * <p>enq210smailInfo を取得します。
     * @return enq210smailInfo
     */
    public int getEnq210smailInfo() {
        return enq210smailInfo__;
    }

    /**
     * <p>enq210smailInfo をセットします。
     * @param enq210smailInfo enq210smailInfo
     */
    public void setEnq210smailInfo(int enq210smailInfo) {
        enq210smailInfo__ = enq210smailInfo;
    }

    /**
     * <p>enq210pluginSmailUse を取得します。
     * @return enq210pluginSmailUse
     */
    public int getEnq210pluginSmailUse() {
        return enq210pluginSmailUse__;
    }

    /**
     * <p>enqSenderList を取得します。
     * @return enqSenderList
     */
    public List<UsrLabelValueBean> getEnqSenderList() {
        return enqSenderList__;
    }

    /**
     * <p>enqSenderList をセットします。
     * @param enqSenderList enqSenderList
     */
    public void setEnqSenderList(List<UsrLabelValueBean> enqSenderList) {
        enqSenderList__ = enqSenderList;
    }

    /**
     * <p>enq210YearLabel を取得します。
     * @return enq210YearLabel
     */
    public ArrayList<LabelValueBean> getEnq210YearLabel() {
        return enq210YearLabel__;
    }
    /**
     * <p>enq210YearLabel をセットします。
     * @param enq210YearLabel enq210YearLabel
     */
    public void setEnq210YearLabel(ArrayList<LabelValueBean> enq210YearLabel) {
        enq210YearLabel__ = enq210YearLabel;
    }
    /**
     * <p>enq210MonthLabel を取得します。
     * @return enq210MonthLabel
     */
    public ArrayList<LabelValueBean> getEnq210MonthLabel() {
        return enq210MonthLabel__;
    }
    /**
     * <p>enq210MonthLabel をセットします。
     * @param enq210MonthLabel enq210MonthLabel
     */
    public void setEnq210MonthLabel(ArrayList<LabelValueBean> enq210MonthLabel) {
        enq210MonthLabel__ = enq210MonthLabel;
    }
    /**
     * <p>enq210DayLabel を取得します。
     * @return enq210DayLabel
     */
    public ArrayList<LabelValueBean> getEnq210DayLabel() {
        return enq210DayLabel__;
    }
    /**
     * <p>enq210DayLabel をセットします。
     * @param enq210DayLabel enq210DayLabel
     */
    public void setEnq210DayLabel(ArrayList<LabelValueBean> enq210DayLabel) {
        enq210DayLabel__ = enq210DayLabel;
    }
    /**
     * <p>enq210FileLabelList を取得します。
     * @return enq210FileLabelList
     */
    public List<LabelValueBean> getEnq210FileLabelList() {
        return enq210FileLabelList__;
    }
    /**
     * <p>enq210FileLabelList をセットします。
     * @param enq210FileLabelList enq210FileLabelList
     */
    public void setEnq210FileLabelList(List<LabelValueBean> enq210FileLabelList) {
        enq210FileLabelList__ = enq210FileLabelList;
    }
    /**
     * <p>enq210TemplatelList を取得します。
     * @return enq210TemplatelList
     */
    public List<LabelValueBean> getEnq210TemplatelList() {
        return enq210TemplatelList__;
    }

    /**
     * <p>enq210TemplatelList をセットします。
     * @param enq210TemplatelList enq210TemplatelList
     */
    public void setEnq210TemplatelList(List<LabelValueBean> enq210TemplatelList) {
        enq210TemplatelList__ = enq210TemplatelList;
    }

    /**
     * <p>enqTypeList を取得します。
     * @return enqTypeList
     */
    public List<LabelValueBean> getEnqTypeList() {
        return enqTypeList__;
    }

    /**
     * <p>enqTypeList をセットします。
     * @param enqTypeList enqTypeList
     */
    public void setEnqTypeList(List<LabelValueBean> enqTypeList) {
        enqTypeList__ = enqTypeList;
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
     * <p>ebaList を取得します。
     * @return ebaList
     */
    public List<Enq210QueModel> getEbaList() {
        return ebaList__;
    }

    /**
     * <p>ebaList をセットします。
     * @param ebaList ebaList
     */
    public void setEbaList(List<Enq210QueModel> ebaList) {
        ebaList__ = ebaList;
    }

    /**
     * <p>enq210answerGroup を取得します。
     * @return enq210answerGroup
     */
    public int getEnq210answerGroup() {
        return enq210answerGroup__;
    }

    /**
     * <p>enq210answerGroup をセットします。
     * @param enq210answerGroup enq210answerGroup
     */
    public void setEnq210answerGroup(int enq210answerGroup) {
        enq210answerGroup__ = enq210answerGroup;
    }

    /**
     * <p>enq210answerList を取得します。
     * @return enq210answerList
     */
    public String[] getEnq210answerList() {
        return enq210answerList__;
    }

    /**
     * <p>enq210answerList をセットします。
     * @param enq210answerList enq210answerList
     */
    public void setEnq210answerList(String[] enq210answerList) {
        enq210answerList__ = enq210answerList;
    }

    /**
     * <p>enq210answerListUI を取得します。
     * @return enq210answerListUI
     */
    public UserGroupSelector getEnq210answerListUI() {
        return enq210answerListUI__;
    }

    /**
     * <p>enq210answerListUI をセットします。
     * @param enq210answerListUI enq210answerListUI
     */
    public void setEnq210answerListUI(UserGroupSelector enq210answerListUI) {
        enq210answerListUI__ = enq210answerListUI;
    }

    /**
     * <p>selectAnswerCombo を取得します。
     * @return selectAnswerCombo
     */
    public List<UsrLabelValueBean> getSelectAnswerCombo() {
        return selectAnswerCombo__;
    }

    /**
     * <p>selectAnswerCombo をセットします。
     * @param selectAnswerCombo selectAnswerCombo
     */
    public void setSelectAnswerCombo(List<UsrLabelValueBean> selectAnswerCombo) {
        selectAnswerCombo__ = selectAnswerCombo;
    }

    /**
     * <p>enq210Alert を取得します。
     * @return enq210Alert
     */
    public int getEnq210Alert() {
        return enq210Alert__;
    }

    /**
     * <p>enq210Alert をセットします。
     * @param enq210Alert enq210Alert
     */
    public void setEnq210Alert(int enq210Alert) {
        enq210Alert__ = enq210Alert;
    }

    /**
     * <p>tempClickBtn を取得します。
     * @return tempClickBtn
     */
    public int getTempClickBtn() {
        return tempClickBtn__;
    }

    /**
     * <p>tempClickBtn をセットします。
     * @param tempClickBtn tempClickBtn
     */
    public void setTempClickBtn(int tempClickBtn) {
        tempClickBtn__ = tempClickBtn;
    }

    /**
     * <p>answerDataReset を取得します。
     * @return answerDataReset
     */
    public int getAnswerDataReset() {
        return answerDataReset__;
    }

    /**
     * <p>answerDataReset をセットします。
     * @param answerDataReset answerDataReset
     */
    public void setAnswerDataReset(int answerDataReset) {
        answerDataReset__ = answerDataReset;
    }

    /**
     * <p>enq210DelAnsFlg を取得します。
     * @return enq210DelAnsFlg
     */
    public boolean isEnq210DelAnsFlg() {
        return enq210DelAnsFlg__;
    }

    /**
     * <p>enq210DelAnsFlg をセットします。
     * @param enq210DelAnsFlg enq210DelAnsFlg
     */
    public void setEnq210DelAnsFlg(boolean enq210DelAnsFlg) {
        enq210DelAnsFlg__ = enq210DelAnsFlg;
    }
    
    /**
     * <p>enq210DateInitFlg を取得します。
     * @return enq210DateInitFlg
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210DateInitFlg__
     */
    public boolean isEnq210DateInitFlg() {
        return enq210DateInitFlg__;
    }

    /**
     * <p>enq210DateInitFlg をセットします。
     * @param enq210DateInitFlg enq210DateInitFlg
     * @see jp.groupsession.v2.enq.enq210.Enq210Form#enq210DateInitFlg__
     */
    public void setEnq210DateInitFlg(boolean enq210DateInitFlg) {
        enq210DateInitFlg__ = enq210DateInitFlg;
    }

    /**
     * <p>enq210TempSaveId を取得します。
     * @return enq210TempSaveId
     * @see jp.groupsession.v2.enq.enq210.Enq210ParamModel#enq210TempSaveId__
     */
    public String getEnq210TempSaveId() {
        return enq210TempSaveId__;
    }

    /**
     * <p>enq210TempSaveId をセットします。
     * @param enq210TempSaveId enq210TempSaveId
     * @see jp.groupsession.v2.enq.enq210.Enq210ParamModel#enq210TempSaveId__
     */
    public void setEnq210TempSaveId(String enq210TempSaveId) {
        enq210TempSaveId__ = enq210TempSaveId;
    }

}

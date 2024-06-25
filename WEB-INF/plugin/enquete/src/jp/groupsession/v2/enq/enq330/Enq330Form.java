package jp.groupsession.v2.enq.enq330;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.EnqValidateUtil;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq310.Enq310Form;
import jp.groupsession.v2.enq.enq310.Enq310QuestionSubModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート 結果確認（詳細）画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq330Form extends Enq310Form {
    /** 設問番号 */
    private String enq330queNo__ = null;
    /** 必須 */
    private int enq330queRequire__ = 0;
    /** 設問種別 */
    private int enq330queKbn__ = 0;
    /** 設問種別名称 */
    private String enq330queKbnName__ = null;
    /** 設問 */
    private String enq330question__ = null;
    /** 回答結果 回答人数 */
    private String enq330answerCountAr__ = null;
    /** 回答結果 未回答人数 */
    private String enq330answerCountUn__ = null;
    /** 回答結果 最小値 */
    private String enq330answerMinValue__ = null;
    /** 回答結果 最大値 */
    private String enq330answerMaxValue__ = null;
    /** 回答結果 平均値 */
    private String enq330answerAvgValue__ = null;
    /** 検索結果件数 */
    private int enq330searchCount__ = 0;
    /** グラフ */
    private int enq330graph__ = 0;

    /** グループ */
    private int enq330group__ = -1;
    /** ユーザ */
    private int enq330user__ = -1;
    /** 回答(テキスト) */
    private String enq330ansText__ = null;
    /** 回答(数値入力・日付入力) 区分 */
    private int enq330ansNumKbn__ = 0;
    /** 回答(数値入力) From */
    private String enq330ansNumFrom__ = null;
    /** 回答(数値入力) To */
    private String enq330ansNumTo__ = null;
    /** 回答(日付入力) From 年 */
    private int enq330ansDateFromYear__ = 0;
    /** 回答(日付入力) From 月 */
    private int enq330ansDateFromMonth__ = 0;
    /** 回答(日付入力) From 日 */
    private int enq330ansDateFromDay__ = 0;
    /** 回答(日付入力) To 年 */
    private int enq330ansDateToYear__ = 0;
    /** 回答(日付入力) To 月 */
    private int enq330ansDateToMonth__ = 0;
    /** 回答(日付入力) To 日 */
    private int enq330ansDateToDay__ = 0;
    /** 回答(日付入力) From 年月日 */
    private String enq330ansDateFrom__ = null;
    /** 回答(日付入力) To 年月日 */
    private String enq330ansDateTo__ = null;
    /** 回答(選択肢) */
    private int[] enq330ans__ = null;
    /** グループ(検索条件保持) */
    private int enq330svGroup__ = -1;
    /** ユーザ(検索条件保持) */
    private int enq330svUser__ = -1;
    /** 回答(テキスト)(検索条件保持) */
    private String enq330svAnsText__ = null;
    /** 回答(数値入力・日付入力) 区分(検索条件保持) */
    private int enq330svAnsNumKbn__ = 0;
    /** 回答(数値入力) From(検索条件保持) */
    private String enq330svAnsNumFrom__ = null;
    /** 回答(数値入力) To(検索条件保持) */
    private String enq330svAnsNumTo__ = null;
    /** 回答(日付入力) From 年(検索条件保持) */
    private int enq330svAnsDateFromYear__ = 0;
    /** 回答(日付入力) From 月(検索条件保持) */
    private int enq330svAnsDateFromMonth__ = 0;
    /** 回答(日付入力) From 日(検索条件保持) */
    private int enq330svAnsDateFromDay__ = 0;
    /** 回答(日付入力) To 年(検索条件保持) */
    private int enq330svAnsDateToYear__ = 0;
    /** 回答(日付入力) To 月(検索条件保持) */
    private int enq330svAnsDateToMonth__ = 0;
    /** 回答(日付入力) To 日(検索条件保持) */
    private int enq330svAnsDateToDay__ = 0;
    /** 回答(日付入力) From 年月日(検索条件保持) */
    private String enq330svAnsDateFrom__ = null;
    /** 回答(日付入力) To 年月日(検索条件保持) */
    private String enq330svAnsDateTo__ = null;
    /** 回答(選択肢)(検索条件保持) */
    private int[] enq330svAns__ = null;

    /** アンケート詳細情報表示フラグ */
    private int enq330viewDetailFlg__ = 0;
    /** 表示位置移動フラグ */
    private int enq330scrollQuestonFlg__ = 0;
    /** ソートキー */
    private int enq330sortKey__ = Enq330Const.SORTKEY_ANSDATE;
    /** 並び順 */
    private int enq330order__ = Enq330Const.ORDER_DESC;
    /** ページ(上段) */
    private int enq330pageTop__ = 0;
    /** ページ(下段) */
    private int enq330pageBottom__ = 0;

    /** グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** ユーザコンボ */
    private List<LabelValueBean> userCombo__ = null;
    /** 回答一覧 */
    private List<Enq330AnswerModel> ansList__ = null;
    /** 設問結果 選択肢 */
    private List<Enq310QuestionSubModel> queSubList__ = null;
    /**
     * <p>enq330queNo を取得します。
     * @return enq330queNo
     */
    public String getEnq330queNo() {
        return enq330queNo__;
    }
    /**
     * <p>enq330queNo をセットします。
     * @param enq330queNo enq330queNo
     */
    public void setEnq330queNo(String enq330queNo) {
        enq330queNo__ = enq330queNo;
    }
    /**
     * <p>enq330queRequire を取得します。
     * @return enq330queRequire
     */
    public int getEnq330queRequire() {
        return enq330queRequire__;
    }
    /**
     * <p>enq330queRequire をセットします。
     * @param enq330queRequire enq330queRequire
     */
    public void setEnq330queRequire(int enq330queRequire) {
        enq330queRequire__ = enq330queRequire;
    }
    /**
     * <p>enq330queKbn を取得します。
     * @return enq330queKbn
     */
    public int getEnq330queKbn() {
        return enq330queKbn__;
    }
    /**
     * <p>enq330queKbn をセットします。
     * @param enq330queKbn enq330queKbn
     */
    public void setEnq330queKbn(int enq330queKbn) {
        enq330queKbn__ = enq330queKbn;
    }
    /**
     * <p>enq330queKbnName を取得します。
     * @return enq330queKbnName
     */
    public String getEnq330queKbnName() {
        return enq330queKbnName__;
    }
    /**
     * <p>enq330queKbnName をセットします。
     * @param enq330queKbnName enq330queKbnName
     */
    public void setEnq330queKbnName(String enq330queKbnName) {
        enq330queKbnName__ = enq330queKbnName;
    }
    /**
     * <p>enq330question を取得します。
     * @return enq330question
     */
    public String getEnq330question() {
        return enq330question__;
    }
    /**
     * <p>enq330question をセットします。
     * @param enq330question enq330question
     */
    public void setEnq330question(String enq330question) {
        enq330question__ = enq330question;
    }
    /**
     * <p>enq330answerCountAr を取得します。
     * @return enq330answerCountAr
     */
    public String getEnq330answerCountAr() {
        return enq330answerCountAr__;
    }
    /**
     * <p>enq330answerCountAr をセットします。
     * @param enq330answerCountAr enq330answerCountAr
     */
    public void setEnq330answerCountAr(String enq330answerCountAr) {
        enq330answerCountAr__ = enq330answerCountAr;
    }
    /**
     * <p>enq330answerCountUn を取得します。
     * @return enq330answerCountUn
     */
    public String getEnq330answerCountUn() {
        return enq330answerCountUn__;
    }
    /**
     * <p>enq330answerCountUn をセットします。
     * @param enq330answerCountUn enq330answerCountUn
     */
    public void setEnq330answerCountUn(String enq330answerCountUn) {
        enq330answerCountUn__ = enq330answerCountUn;
    }
    /**
     * <p>enq330answerMinValue を取得します。
     * @return enq330answerMinValue
     */
    public String getEnq330answerMinValue() {
        return enq330answerMinValue__;
    }
    /**
     * <p>enq330answerMinValue をセットします。
     * @param enq330answerMinValue enq330answerMinValue
     */
    public void setEnq330answerMinValue(String enq330answerMinValue) {
        enq330answerMinValue__ = enq330answerMinValue;
    }
    /**
     * <p>enq330answerMaxValue を取得します。
     * @return enq330answerMaxValue
     */
    public String getEnq330answerMaxValue() {
        return enq330answerMaxValue__;
    }
    /**
     * <p>enq330answerMaxValue をセットします。
     * @param enq330answerMaxValue enq330answerMaxValue
     */
    public void setEnq330answerMaxValue(String enq330answerMaxValue) {
        enq330answerMaxValue__ = enq330answerMaxValue;
    }
    /**
     * <p>enq330answerAvgValue を取得します。
     * @return enq330answerAvgValue
     */
    public String getEnq330answerAvgValue() {
        return enq330answerAvgValue__;
    }
    /**
     * <p>enq330answerAvgValue をセットします。
     * @param enq330answerAvgValue enq330answerAvgValue
     */
    public void setEnq330answerAvgValue(String enq330answerAvgValue) {
        enq330answerAvgValue__ = enq330answerAvgValue;
    }
    /**
     * <p>enq330searchCount を取得します。
     * @return enq330searchCount
     */
    public int getEnq330searchCount() {
        return enq330searchCount__;
    }
    /**
     * <p>enq330searchCount をセットします。
     * @param enq330searchCount enq330searchCount
     */
    public void setEnq330searchCount(int enq330searchCount) {
        enq330searchCount__ = enq330searchCount;
    }
    /**
     * <p>enq330graph を取得します。
     * @return enq330graph
     */
    public int getEnq330graph() {
        return enq330graph__;
    }
    /**
     * <p>enq330graph をセットします。
     * @param enq330graph enq330graph
     */
    public void setEnq330graph(int enq330graph) {
        enq330graph__ = enq330graph;
    }
    /**
     * <p>enq330group を取得します。
     * @return enq330group
     */
    public int getEnq330group() {
        return enq330group__;
    }
    /**
     * <p>enq330group をセットします。
     * @param enq330group enq330group
     */
    public void setEnq330group(int enq330group) {
        enq330group__ = enq330group;
    }
    /**
     * <p>enq330user を取得します。
     * @return enq330user
     */
    public int getEnq330user() {
        return enq330user__;
    }
    /**
     * <p>enq330user をセットします。
     * @param enq330user enq330user
     */
    public void setEnq330user(int enq330user) {
        enq330user__ = enq330user;
    }
    /**
     * <p>enq330ans を取得します。
     * @return enq330ans
     */
    public int[] getEnq330ans() {
        return enq330ans__;
    }
    /**
     * <p>enq330ans をセットします。
     * @param enq330ans enq330ans
     */
    public void setEnq330ans(int[] enq330ans) {
        enq330ans__ = enq330ans;
    }
    /**
     * <p>enq330svGroup を取得します。
     * @return enq330svGroup
     */
    public int getEnq330svGroup() {
        return enq330svGroup__;
    }
    /**
     * <p>enq330svGroup をセットします。
     * @param enq330svGroup enq330svGroup
     */
    public void setEnq330svGroup(int enq330svGroup) {
        enq330svGroup__ = enq330svGroup;
    }
    /**
     * <p>enq330svUser を取得します。
     * @return enq330svUser
     */
    public int getEnq330svUser() {
        return enq330svUser__;
    }
    /**
     * <p>enq330svUser をセットします。
     * @param enq330svUser enq330svUser
     */
    public void setEnq330svUser(int enq330svUser) {
        enq330svUser__ = enq330svUser;
    }
    /**
     * <p>enq330svAns を取得します。
     * @return enq330svAns
     */
    public int[] getEnq330svAns() {
        return enq330svAns__;
    }
    /**
     * <p>enq330svAns をセットします。
     * @param enq330svAns enq330svAns
     */
    public void setEnq330svAns(int[] enq330svAns) {
        enq330svAns__ = enq330svAns;
    }
    /**
     * <p>enq330ansText を取得します。
     * @return enq330ansText
     */
    public String getEnq330ansText() {
        return enq330ansText__;
    }
    /**
     * <p>enq330ansText をセットします。
     * @param enq330ansText enq330ansText
     */
    public void setEnq330ansText(String enq330ansText) {
        enq330ansText__ = enq330ansText;
    }
    /**
     * <p>enq330ansNumKbn を取得します。
     * @return enq330ansNumKbn
     */
    public int getEnq330ansNumKbn() {
        return enq330ansNumKbn__;
    }
    /**
     * <p>enq330ansNumKbn をセットします。
     * @param enq330ansNumKbn enq330ansNumKbn
     */
    public void setEnq330ansNumKbn(int enq330ansNumKbn) {
        enq330ansNumKbn__ = enq330ansNumKbn;
    }
    /**
     * <p>enq330ansNumFrom を取得します。
     * @return enq330ansNumFrom
     */
    public String getEnq330ansNumFrom() {
        return enq330ansNumFrom__;
    }
    /**
     * <p>enq330ansNumFrom をセットします。
     * @param enq330ansNumFrom enq330ansNumFrom
     */
    public void setEnq330ansNumFrom(String enq330ansNumFrom) {
        enq330ansNumFrom__ = enq330ansNumFrom;
    }
    /**
     * <p>enq330ansNumTo を取得します。
     * @return enq330ansNumTo
     */
    public String getEnq330ansNumTo() {
        return enq330ansNumTo__;
    }
    /**
     * <p>enq330ansNumTo をセットします。
     * @param enq330ansNumTo enq330ansNumTo
     */
    public void setEnq330ansNumTo(String enq330ansNumTo) {
        enq330ansNumTo__ = enq330ansNumTo;
    }
    /**
     * <p>enq330ansDateFromYear を取得します。
     * @return enq330ansDateFromYear
     */
    public int getEnq330ansDateFromYear() {
        return enq330ansDateFromYear__;
    }
    /**
     * <p>enq330ansDateFromYear をセットします。
     * @param enq330ansDateFromYear enq330ansDateFromYear
     */
    public void setEnq330ansDateFromYear(int enq330ansDateFromYear) {
        enq330ansDateFromYear__ = enq330ansDateFromYear;
    }
    /**
     * <p>enq330ansDateFromMonth を取得します。
     * @return enq330ansDateFromMonth
     */
    public int getEnq330ansDateFromMonth() {
        return enq330ansDateFromMonth__;
    }
    /**
     * <p>enq330ansDateFromMonth をセットします。
     * @param enq330ansDateFromMonth enq330ansDateFromMonth
     */
    public void setEnq330ansDateFromMonth(int enq330ansDateFromMonth) {
        enq330ansDateFromMonth__ = enq330ansDateFromMonth;
    }
    /**
     * <p>enq330ansDateFromDay を取得します。
     * @return enq330ansDateFromDay
     */
    public int getEnq330ansDateFromDay() {
        return enq330ansDateFromDay__;
    }
    /**
     * <p>enq330ansDateFromDay をセットします。
     * @param enq330ansDateFromDay enq330ansDateFromDay
     */
    public void setEnq330ansDateFromDay(int enq330ansDateFromDay) {
        enq330ansDateFromDay__ = enq330ansDateFromDay;
    }
    /**
     * <p>enq330ansDateToYear を取得します。
     * @return enq330ansDateToYear
     */
    public int getEnq330ansDateToYear() {
        return enq330ansDateToYear__;
    }
    /**
     * <p>enq330ansDateToYear をセットします。
     * @param enq330ansDateToYear enq330ansDateToYear
     */
    public void setEnq330ansDateToYear(int enq330ansDateToYear) {
        enq330ansDateToYear__ = enq330ansDateToYear;
    }
    /**
     * <p>enq330ansDateToMonth を取得します。
     * @return enq330ansDateToMonth
     */
    public int getEnq330ansDateToMonth() {
        return enq330ansDateToMonth__;
    }
    /**
     * <p>enq330ansDateToMonth をセットします。
     * @param enq330ansDateToMonth enq330ansDateToMonth
     */
    public void setEnq330ansDateToMonth(int enq330ansDateToMonth) {
        enq330ansDateToMonth__ = enq330ansDateToMonth;
    }
    /**
     * <p>enq330ansDateToDay を取得します。
     * @return enq330ansDateToDay
     */
    public int getEnq330ansDateToDay() {
        return enq330ansDateToDay__;
    }
    /**
     * <p>enq330ansDateToDay をセットします。
     * @param enq330ansDateToDay enq330ansDateToDay
     */
    public void setEnq330ansDateToDay(int enq330ansDateToDay) {
        enq330ansDateToDay__ = enq330ansDateToDay;
    }
    /**
     * <p>enq330ansDateFrom を取得します。
     * @return enq330ansDateFrom
     * @see jp.groupsession.v2.enq.enq330.Enq330Form#enq330ansDateFrom__
     */
    public String getEnq330ansDateFrom() {
        return enq330ansDateFrom__;
    }
    /**
     * <p>enq330ansDateFrom をセットします。
     * @param enq330ansDateFrom enq330ansDateFrom
     * @see jp.groupsession.v2.enq.enq330.Enq330Form#enq330ansDateFrom__
     */
    public void setEnq330ansDateFrom(String enq330ansDateFrom) {
        enq330ansDateFrom__ = enq330ansDateFrom;
    }
    /**
     * <p>enq330ansDateTo を取得します。
     * @return enq330ansDateTo
     * @see jp.groupsession.v2.enq.enq330.Enq330Form#enq330ansDateTo__
     */
    public String getEnq330ansDateTo() {
        return enq330ansDateTo__;
    }
    /**
     * <p>enq330ansDateTo をセットします。
     * @param enq330ansDateTo enq330ansDateTo
     * @see jp.groupsession.v2.enq.enq330.Enq330Form#enq330ansDateTo__
     */
    public void setEnq330ansDateTo(String enq330ansDateTo) {
        enq330ansDateTo__ = enq330ansDateTo;
    }
    /**
     * <p>enq330svAnsText を取得します。
     * @return enq330svAnsText
     */
    public String getEnq330svAnsText() {
        return enq330svAnsText__;
    }
    /**
     * <p>enq330svAnsText をセットします。
     * @param enq330svAnsText enq330svAnsText
     */
    public void setEnq330svAnsText(String enq330svAnsText) {
        enq330svAnsText__ = enq330svAnsText;
    }
    /**
     * <p>enq330svAnsNumKbn を取得します。
     * @return enq330svAnsNumKbn
     */
    public int getEnq330svAnsNumKbn() {
        return enq330svAnsNumKbn__;
    }
    /**
     * <p>enq330svAnsNumKbn をセットします。
     * @param enq330svAnsNumKbn enq330svAnsNumKbn
     */
    public void setEnq330svAnsNumKbn(int enq330svAnsNumKbn) {
        enq330svAnsNumKbn__ = enq330svAnsNumKbn;
    }
    /**
     * <p>enq330svAnsNumFrom を取得します。
     * @return enq330svAnsNumFrom
     */
    public String getEnq330svAnsNumFrom() {
        return enq330svAnsNumFrom__;
    }
    /**
     * <p>enq330svAnsNumFrom をセットします。
     * @param enq330svAnsNumFrom enq330svAnsNumFrom
     */
    public void setEnq330svAnsNumFrom(String enq330svAnsNumFrom) {
        enq330svAnsNumFrom__ = enq330svAnsNumFrom;
    }
    /**
     * <p>enq330svAnsNumTo を取得します。
     * @return enq330svAnsNumTo
     */
    public String getEnq330svAnsNumTo() {
        return enq330svAnsNumTo__;
    }
    /**
     * <p>enq330svAnsNumTo をセットします。
     * @param enq330svAnsNumTo enq330svAnsNumTo
     */
    public void setEnq330svAnsNumTo(String enq330svAnsNumTo) {
        enq330svAnsNumTo__ = enq330svAnsNumTo;
    }
    /**
     * <p>enq330svAnsDateFromYear を取得します。
     * @return enq330svAnsDateFromYear
     */
    public int getEnq330svAnsDateFromYear() {
        return enq330svAnsDateFromYear__;
    }
    /**
     * <p>enq330svAnsDateFromYear をセットします。
     * @param enq330svAnsDateFromYear enq330svAnsDateFromYear
     */
    public void setEnq330svAnsDateFromYear(int enq330svAnsDateFromYear) {
        enq330svAnsDateFromYear__ = enq330svAnsDateFromYear;
    }
    /**
     * <p>enq330svAnsDateFromMonth を取得します。
     * @return enq330svAnsDateFromMonth
     */
    public int getEnq330svAnsDateFromMonth() {
        return enq330svAnsDateFromMonth__;
    }
    /**
     * <p>enq330svAnsDateFromMonth をセットします。
     * @param enq330svAnsDateFromMonth enq330svAnsDateFromMonth
     */
    public void setEnq330svAnsDateFromMonth(int enq330svAnsDateFromMonth) {
        enq330svAnsDateFromMonth__ = enq330svAnsDateFromMonth;
    }
    /**
     * <p>enq330svAnsDateFromDay を取得します。
     * @return enq330svAnsDateFromDay
     */
    public int getEnq330svAnsDateFromDay() {
        return enq330svAnsDateFromDay__;
    }
    /**
     * <p>enq330svAnsDateFromDay をセットします。
     * @param enq330svAnsDateFromDay enq330svAnsDateFromDay
     */
    public void setEnq330svAnsDateFromDay(int enq330svAnsDateFromDay) {
        enq330svAnsDateFromDay__ = enq330svAnsDateFromDay;
    }
    /**
     * <p>enq330svAnsDateToYear を取得します。
     * @return enq330svAnsDateToYear
     */
    public int getEnq330svAnsDateToYear() {
        return enq330svAnsDateToYear__;
    }
    /**
     * <p>enq330svAnsDateToYear をセットします。
     * @param enq330svAnsDateToYear enq330svAnsDateToYear
     */
    public void setEnq330svAnsDateToYear(int enq330svAnsDateToYear) {
        enq330svAnsDateToYear__ = enq330svAnsDateToYear;
    }
    /**
     * <p>enq330svAnsDateToMonth を取得します。
     * @return enq330svAnsDateToMonth
     */
    public int getEnq330svAnsDateToMonth() {
        return enq330svAnsDateToMonth__;
    }
    /**
     * <p>enq330svAnsDateToMonth をセットします。
     * @param enq330svAnsDateToMonth enq330svAnsDateToMonth
     */
    public void setEnq330svAnsDateToMonth(int enq330svAnsDateToMonth) {
        enq330svAnsDateToMonth__ = enq330svAnsDateToMonth;
    }
    /**
     * <p>enq330svAnsDateToDay を取得します。
     * @return enq330svAnsDateToDay
     */
    public int getEnq330svAnsDateToDay() {
        return enq330svAnsDateToDay__;
    }
    /**
     * <p>enq330svAnsDateToDay をセットします。
     * @param enq330svAnsDateToDay enq330svAnsDateToDay
     */
    public void setEnq330svAnsDateToDay(int enq330svAnsDateToDay) {
        enq330svAnsDateToDay__ = enq330svAnsDateToDay;
    }
    /**
     * <p>enq330svAnsDateFrom を取得します。
     * @return enq330svAnsDateFrom
     * @see jp.groupsession.v2.enq.enq330.Enq330Form#enq330svAnsDateFrom__
     */
    public String getEnq330svAnsDateFrom() {
        return enq330svAnsDateFrom__;
    }
    /**
     * <p>enq330svAnsDateFrom をセットします。
     * @param enq330svAnsDateFrom enq330svAnsDateFrom
     * @see jp.groupsession.v2.enq.enq330.Enq330Form#enq330svAnsDateFrom__
     */
    public void setEnq330svAnsDateFrom(String enq330svAnsDateFrom) {
        enq330svAnsDateFrom__ = enq330svAnsDateFrom;
    }
    /**
     * <p>enq330svAnsDateTo を取得します。
     * @return enq330svAnsDateTo
     * @see jp.groupsession.v2.enq.enq330.Enq330Form#enq330svAnsDateTo__
     */
    public String getEnq330svAnsDateTo() {
        return enq330svAnsDateTo__;
    }
    /**
     * <p>enq330svAnsDateTo をセットします。
     * @param enq330svAnsDateTo enq330svAnsDateTo
     * @see jp.groupsession.v2.enq.enq330.Enq330Form#enq330svAnsDateTo__
     */
    public void setEnq330svAnsDateTo(String enq330svAnsDateTo) {
        enq330svAnsDateTo__ = enq330svAnsDateTo;
    }
    /**
     * <p>groupCombo を取得します。
     * @return groupCombo
     */
    public List<LabelValueBean> getGroupCombo() {
        return groupCombo__;
    }
    /**
     * <p>groupCombo をセットします。
     * @param groupCombo groupCombo
     */
    public void setGroupCombo(List<LabelValueBean> groupCombo) {
        groupCombo__ = groupCombo;
    }
    /**
     * <p>userCombo を取得します。
     * @return userCombo
     */
    public List<LabelValueBean> getUserCombo() {
        return userCombo__;
    }
    /**
     * <p>userCombo をセットします。
     * @param userCombo userCombo
     */
    public void setUserCombo(List<LabelValueBean> userCombo) {
        userCombo__ = userCombo;
    }
    /**
     * <p>ansList を取得します。
     * @return ansList
     */
    public List<Enq330AnswerModel> getAnsList() {
        return ansList__;
    }
    /**
     * <p>ansList をセットします。
     * @param ansList ansList
     */
    public void setAnsList(List<Enq330AnswerModel> ansList) {
        ansList__ = ansList;
    }
    /**
     * <p>queSubList を取得します。
     * @return queSubList
     */
    public List<Enq310QuestionSubModel> getQueSubList() {
        return queSubList__;
    }
    /**
     * <p>queSubList をセットします。
     * @param queSubList queSubList
     */
    public void setQueSubList(List<Enq310QuestionSubModel> queSubList) {
        queSubList__ = queSubList;
    }

    /**
     * <p>enq330sortKey を取得します。
     * @return enq330sortKey
     */
    public int getEnq330sortKey() {
        return enq330sortKey__;
    }
    /**
     * <p>enq330sortKey をセットします。
     * @param enq330sortKey enq330sortKey
     */
    public void setEnq330sortKey(int enq330sortKey) {
        enq330sortKey__ = enq330sortKey;
    }
    /**
     * <p>enq330order を取得します。
     * @return enq330order
     */
    public int getEnq330order() {
        return enq330order__;
    }
    /**
     * <p>enq330order をセットします。
     * @param enq330order enq330order
     */
    public void setEnq330order(int enq330order) {
        enq330order__ = enq330order;
    }
    /**
     * <p>enq330pageTop を取得します。
     * @return enq330pageTop
     */
    public int getEnq330pageTop() {
        return enq330pageTop__;
    }
    /**
     * <p>enq330pageTop をセットします。
     * @param enq330pageTop enq330pageTop
     */
    public void setEnq330pageTop(int enq330pageTop) {
        enq330pageTop__ = enq330pageTop;
    }
    /**
     * <p>enq330pageBottom を取得します。
     * @return enq330pageBottom
     */
    public int getEnq330pageBottom() {
        return enq330pageBottom__;
    }
    /**
     * <p>enq330pageBottom をセットします。
     * @param enq330pageBottom enq330pageBottom
     */
    public void setEnq330pageBottom(int enq330pageBottom) {
        enq330pageBottom__ = enq330pageBottom;
    }
    /**
     * <p>enq330viewDetailFlg を取得します。
     * @return enq330viewDetailFlg
     */
    public int getEnq330viewDetailFlg() {
        return enq330viewDetailFlg__;
    }
    /**
     * <p>enq330viewDetailFlg をセットします。
     * @param enq330viewDetailFlg enq330viewDetailFlg
     */
    public void setEnq330viewDetailFlg(int enq330viewDetailFlg) {
        enq330viewDetailFlg__ = enq330viewDetailFlg;
    }
    /**
     * <p>enq330scrollQuestonFlg を取得します。
     * @return enq330scrollQuestonFlg
     */
    public int getEnq330scrollQuestonFlg() {
        return enq330scrollQuestonFlg__;
    }
    /**
     * <p>enq330scrollQuestonFlg をセットします。
     * @param enq330scrollQuestonFlg enq330scrollQuestonFlg
     */
    public void setEnq330scrollQuestonFlg(int enq330scrollQuestonFlg) {
        enq330scrollQuestonFlg__ = enq330scrollQuestonFlg;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param queKbn 設問種類
     * @return エラー
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外 
     * @throws IllegalAccessException 日時設定時例外
     */
    public ActionErrors validateEnq330(RequestModel reqMdl, int queKbn) 
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        // テキスト
        if (queKbn == GSConstEnquete.SYURUI_TEXT
         || queKbn == GSConstEnquete.SYURUI_TEXTAREA) {
            EnqValidateUtil.validateTextBoxInput(errors, enq330ansText__, "enq330ansText",
                                                 gsMsg.getMessage("enq.22"),
                                                 GSConstEnquete.MAX_LEN_EAS_ANS_TEXT,
                                                 false);

        // 数値入力
        } else if (queKbn == GSConstEnquete.SYURUI_INTEGER) {

            // 検索条件が「単一」
            if (enq330ansNumKbn__ == Enq330Const.ANS_NUM_KBN_SINGLE) {
                EnqValidateUtil.validateIntTextBox(errors,
                        enq330ansNumFrom__,
                        "enq330ansNumFrom",
                        gsMsg.getMessage("enq.22"),
                        GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                        GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                        GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                        false);

            // 検索条件が「範囲」
            } else {
                // from
                String targetJp = gsMsg.getMessage("enq.41") + " ";
                EnqValidateUtil.validateIntTextBox(errors,
                        enq330ansNumFrom__,
                        "enq330ansNumFrom",
                        targetJp + gsMsg.getMessage("cmn.start"),
                        GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                        GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                        GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                        false);

                // to
                if (errors != null) {
                    EnqValidateUtil.validateIntTextBox(errors,
                            enq330ansNumTo__,
                            "enq330ansNumTo",
                            targetJp + gsMsg.getMessage("cmn.end"),
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_INT,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM_DEC,
                            GSConstEnquete.MAX_LEN_EAS_ANS_NUM,
                            false);
                }

                // 範囲の論理チェック
                if (errors != null && enq330ansNumFrom__ != null && enq330ansNumTo__ != null) {
                    EnqValidateUtil.validateNumRange(reqMdl, errors, "ansNumRange",
                            "ansNumRange", enq330ansNumFrom__, enq330ansNumTo__);
                }
            }

        // 日付
        } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
            
            DateTimePickerBiz dateBiz = new DateTimePickerBiz();
            if (enq330ansNumKbn__ == Enq330Const.ANS_NUM_KBN_SINGLE
                    && !StringUtil.isNullZeroString(enq330ansDateFrom__)) {
                String targetJp = gsMsg.getMessage("enq.22");
                
                errors.add(dateBiz.setYmdParam(this, "enq330ansDateFrom",
                        "enq330ansDateFromYear", "enq330ansDateFromMonth",
                        "enq330ansDateFromDay", targetJp));
            } else {
                String targetJp = gsMsg.getMessage("enq.41");
                
                int errorCnt = errors.size();
                
                if (!StringUtil.isNullZeroString(enq330ansDateFrom__)) {
                    errors.add(dateBiz.setYmdParam(this, "enq330ansDateFrom",
                            "enq330ansDateFromYear", "enq330ansDateFromMonth",
                            "enq330ansDateFromDay", targetJp + gsMsg.getMessage("cmn.start")));
                }
                
                if (!StringUtil.isNullZeroString(enq330ansDateTo__)) {
                    errors.add(dateBiz.setYmdParam(this, "enq330ansDateTo",
                            "enq330ansDateToYear", "enq330ansDateToMonth",
                            "enq330ansDateToDay", targetJp + gsMsg.getMessage("cmn.end")));
                }
                
                if (errorCnt == errors.size()
                        && !StringUtil.isNullZeroString(enq330ansDateFrom__)
                        && !StringUtil.isNullZeroString(enq330ansDateTo__)) {
                    
                    EnqValidateUtil.validateDate(reqMdl, errors, "dateRange", targetJp,
                            enq330ansDateFromYear__, enq330ansDateFromMonth__,
                            enq330ansDateFromDay__, enq330ansDateToYear__,
                            enq330ansDateToMonth__, enq330ansDateToDay__);
                }
            }
        }

        return errors;
    }
}

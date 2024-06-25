package jp.groupsession.v2.enq.enq970;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.enq.enq010.Enq010Const;

/**
 * <br>[機  能] アンケート情報(一覧表示)を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq970EnqueteModel extends AbstractModel {
    /** アンケートSID */
    private long enqSid__ = 0;
    /** 重要度 */
    private int priority__ = 0;
    /** 状態 */
    private int status__ = 0;
    /** 発信者 */
    private String sender__ = null;
    /** 作成日 */
    private String makeDate__ = null;
    /** 種類 */
    private String typeName__ = null;
    /** タイトル */
    private String title__ = null;
    /** 回答期限 */
    private String ansLimitDate__ = null;
    /** 回答期限 曜日 */
    private String ansLimitDayOfWeek__ = null;
    /** 対象人数 */
    private long subjects__ = 0;
    /** 公開 */
    private int publicKbn__ = Enq010Const.PUBLIC_NO;
    /** 公開期限 開始 */
    private String pubStartDateStr__ = null;
    /** 公開期限 開始 曜日 */
    private String pubStartDayOfWeek__ = null;
    /** 公開期限 終了 */
    private String pubEndDateStr__ = null;
    /** 公開期限 終了 曜日 */
    private String pubEndDayOfWeek__ = null;
    /** 公開期限 開始 */
    private UDate pubStartDate__ = null;
    /** 公開期限 終了 */
    private UDate pubEndDate__ = null;
    /** 匿名 */
    private int annoy__ = 0;
    /** 回答公開 */
    private int ansOpen__ = 0;
    /**
     * <p>enqSid を取得します。
     * @return enqSid
     */
    public long getEnqSid() {
        return enqSid__;
    }
    /**
     * <p>enqSid をセットします。
     * @param enqSid enqSid
     */
    public void setEnqSid(long enqSid) {
        enqSid__ = enqSid;
    }
    /**
     * <p>priority を取得します。
     * @return priority
     */
    public int getPriority() {
        return priority__;
    }
    /**
     * <p>priority をセットします。
     * @param priority priority
     */
    public void setPriority(int priority) {
        priority__ = priority;
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public int getStatus() {
        return status__;
    }
    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setStatus(int status) {
        status__ = status;
    }
    /**
     * <p>sender を取得します。
     * @return sender
     */
    public String getSender() {
        return sender__;
    }
    /**
     * <p>sender をセットします。
     * @param sender sender
     */
    public void setSender(String sender) {
        sender__ = sender;
    }
    /**
     * <p>makeDate を取得します。
     * @return makeDate
     */
    public String getMakeDate() {
        return makeDate__;
    }
    /**
     * <p>makeDate をセットします。
     * @param makeDate makeDate
     */
    public void setMakeDate(String makeDate) {
        makeDate__ = makeDate;
    }
    /**
     * <p>typeName を取得します。
     * @return typeName
     */
    public String getTypeName() {
        return typeName__;
    }
    /**
     * <p>typeName をセットします。
     * @param typeName typeName
     */
    public void setTypeName(String typeName) {
        typeName__ = typeName;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>ansLimitDate を取得します。
     * @return ansLimitDate
     */
    public String getAnsLimitDate() {
        return ansLimitDate__;
    }
    /**
     * <p>ansLimitDate をセットします。
     * @param ansLimitDate ansLimitDate
     */
    public void setAnsLimitDate(String ansLimitDate) {
        ansLimitDate__ = ansLimitDate;
    }
    /**
     * <p>ansLimitDayOfWeek を取得します。
     * @return ansLimitDayOfWeek
     */
    public String getAnsLimitDayOfWeek() {
        return ansLimitDayOfWeek__;
    }
    /**
     * <p>ansLimitDayOfWeek をセットします。
     * @param ansLimitDayOfWeek ansLimitDayOfWeek
     */
    public void setAnsLimitDayOfWeek(String ansLimitDayOfWeek) {
        ansLimitDayOfWeek__ = ansLimitDayOfWeek;
    }
    /**
     * <p>subjects を取得します。
     * @return subjects
     */
    public long getSubjects() {
        return subjects__;
    }
    /**
     * <p>subjects をセットします。
     * @param subjects subjects
     */
    public void setSubjects(long subjects) {
        subjects__ = subjects;
    }
    /**
     * <p>publicKbn を取得します。
     * @return publicKbn
     */
    public int getPublicKbn() {
        return publicKbn__;
    }
    /**
     * <p>publicKbn をセットします。
     * @param publicKbn publicKbn
     */
    public void setPublicKbn(int publicKbn) {
        publicKbn__ = publicKbn;
    }
    /**
     * <p>pubStartDateStr を取得します。
     * @return pubStartDateStr
     */
    public String getPubStartDateStr() {
        return pubStartDateStr__;
    }
    /**
     * <p>pubStartDateStr をセットします。
     * @param pubStartDateStr pubStartDateStr
     */
    public void setPubStartDateStr(String pubStartDateStr) {
        pubStartDateStr__ = pubStartDateStr;
    }
    /**
     * <p>pubStartDayOfWeek を取得します。
     * @return pubStartDayOfWeek
     */
    public String getPubStartDayOfWeek() {
        return pubStartDayOfWeek__;
    }
    /**
     * <p>pubStartDayOfWeek をセットします。
     * @param pubStartDayOfWeek pubStartDayOfWeek
     */
    public void setPubStartDayOfWeek(String pubStartDayOfWeek) {
        pubStartDayOfWeek__ = pubStartDayOfWeek;
    }
    /**
     * <p>pubEndDateStr を取得します。
     * @return pubEndDateStr
     */
    public String getPubEndDateStr() {
        return pubEndDateStr__;
    }
    /**
     * <p>pubEndDateStr をセットします。
     * @param pubEndDateStr pubEndDateStr
     */
    public void setPubEndDateStr(String pubEndDateStr) {
        pubEndDateStr__ = pubEndDateStr;
    }
    /**
     * <p>pubEndDayOfWeek を取得します。
     * @return pubEndDayOfWeek
     */
    public String getPubEndDayOfWeek() {
        return pubEndDayOfWeek__;
    }
    /**
     * <p>pubEndDayOfWeek をセットします。
     * @param pubEndDayOfWeek pubEndDayOfWeek
     */
    public void setPubEndDayOfWeek(String pubEndDayOfWeek) {
        pubEndDayOfWeek__ = pubEndDayOfWeek;
    }
    /**
     * <p>pubStartDate を取得します。
     * @return pubStartDate
     */
    public UDate getPubStartDate() {
        return pubStartDate__;
    }
    /**
     * <p>pubStartDate をセットします。
     * @param pubStartDate pubStartDate
     */
    public void setPubStartDate(UDate pubStartDate) {
        pubStartDate__ = pubStartDate;
    }
    /**
     * <p>pubEndDate を取得します。
     * @return pubEndDate
     */
    public UDate getPubEndDate() {
        return pubEndDate__;
    }
    /**
     * <p>pubEndDate をセットします。
     * @param pubEndDate pubEndDate
     */
    public void setPubEndDate(UDate pubEndDate) {
        pubEndDate__ = pubEndDate;
    }
    /**
     * <p>annoy を取得します。
     * @return annoy
     */
    public int getAnnoy() {
        return annoy__;
    }
    /**
     * <p>annoy をセットします。
     * @param annoy annoy
     */
    public void setAnnoy(int annoy) {
        annoy__ = annoy;
    }
    /**
     * <p>ansOpen を取得します。
     * @return ansOpen
     */
    public int getAnsOpen() {
        return ansOpen__;
    }
    /**
     * <p>ansOpen をセットします。
     * @param ansOpen ansOpen
     */
    public void setAnsOpen(int ansOpen) {
        ansOpen__ = ansOpen;
    }
    /**
     * <br>[機  能] 公開中かを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 非公開 or 公開中
     */
    public int getEnqPublic() {
        int enqPublic = Enq010Const.PUBLIC_NO;
        if (getPubStartDate() != null && getPubEndDate() != null) {
            UDate now = new UDate();
            now.setZeroDdHhMmSs();
            getPubStartDate().setZeroDdHhMmSs();
            if (getPubStartDate().compareDateYMD(now) != UDate.SMALL
            && (getPubEndDate().compareDateYMD(now) != UDate.LARGE)) {
                enqPublic = Enq010Const.PUBLIC_YES;
            }
        }

        return enqPublic;
    }
}

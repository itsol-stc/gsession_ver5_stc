package jp.groupsession.v2.api.ntp.nippou.model;


/**
 * <br>[機  能] WEBAPI 日報検索条件モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouSearchModel {
    /** usrSid*/
    private Integer usrSid__;
    /** grpSID */
    private Integer grpSid__;
    /** nanSid */
    private Integer nanSid__;
    /** ubaSid */
    private Integer ubaSid__;
    /** mkbSid */
    private Integer mkbSid__;
    /** mkhSid */
    private Integer mkhSid__;
    /** mikomi */
    private Integer mikomi__;
    /** keyWord */
    private String keyWord__;
    /** kewRule */
    private Integer kewRule__;
    /** keyFlgTitle */
    private Boolean keyFlgTitle__;
    /** keyFlgSyokan */
    private Boolean keyFlgSyokan__;
    /** titleClrBlue */
    private Boolean titleClrBlue__;
    /** titleClrRed */
    private Boolean titleClrRed__;
    /** titleClrGleen */
    private Boolean titleClrGleen__;
    /** titleClrYellow */
    private Boolean titleClrYellow__;
    /** titleClrBlack */
    private Boolean titleClrBlack__;
    /** startFrom */
    private String startFrom__;
    /** startTo */
    private String startTo__;
    /** endFrom */
    private String endFrom__;
    /** endTo */
    private String endTo__;
    /** upFrom */
    private String upFrom__;
    /** upTo */
    private String upTo__;
    /** 最大取得件数*/
    private int searchCnt__ = 50;
    /** 開始位置*/
    private int start__ = 0;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public Integer getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(Integer usrSid) {
        this.usrSid__ = usrSid;
    }
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public Integer getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(Integer grpSid) {
        this.grpSid__ = grpSid;
    }
    /**
     * <p>nanSid を取得します。
     * @return nanSid
     */
    public Integer getNanSid() {
        return nanSid__;
    }
    /**
     * <p>nanSid をセットします。
     * @param nanSid nanSid
     */
    public void setNanSid(Integer nanSid) {
        this.nanSid__ = nanSid;
    }
    /**
     * <p>ubaSid を取得します。
     * @return ubaSid
     */
    public Integer getUbaSid() {
        return ubaSid__;
    }
    /**
     * <p>ubaSid をセットします。
     * @param ubaSid ubaSid
     */
    public void setUbaSid(Integer ubaSid) {
        this.ubaSid__ = ubaSid;
    }
    /**
     * <p>mkbSid を取得します。
     * @return mkbSid
     */
    public Integer getMkbSid() {
        return mkbSid__;
    }
    /**
     * <p>mkbSid をセットします。
     * @param mkbSid mkbSid
     */
    public void setMkbSid(Integer mkbSid) {
        this.mkbSid__ = mkbSid;
    }
    /**
     * <p>mkhSid を取得します。
     * @return mkhSid
     */
    public Integer getMkhSid() {
        return mkhSid__;
    }
    /**
     * <p>mkhSid をセットします。
     * @param mkhSid mkhSid
     */
    public void setMkhSid(Integer mkhSid) {
        this.mkhSid__ = mkhSid;
    }
    /**
     * <p>mikomi を取得します。
     * @return mikomi
     */
    public Integer getMikomi() {
        return mikomi__;
    }
    /**
     * <p>mikomi をセットします。
     * @param mikomi mikomi
     */
    public void setMikomi(Integer mikomi) {
        this.mikomi__ = mikomi;
    }
    /**
     * <p>keyWord を取得します。
     * @return keyWord
     */
    public String getKeyWord() {
        return keyWord__;
    }
    /**
     * <p>keyWord をセットします。
     * @param keyWord keyWord
     */
    public void setKeyWord(String keyWord) {
        this.keyWord__ = keyWord;
    }
    /**
     * <p>kewRule を取得します。
     * @return kewRule
     */
    public Integer getKewRule() {
        return kewRule__;
    }
    /**
     * <p>kewRule をセットします。
     * @param kewRule kewRule
     */
    public void setKewRule(Integer kewRule) {
        this.kewRule__ = kewRule;
    }
    /**
     * <p>keyFlgTitle を取得します。
     * @return keyFlgTitle
     */
    public boolean isKeyFlgTitle() {
        return keyFlgTitle__;
    }
    /**
     * <p>keyFlgTitle をセットします。
     * @param keyFlgTitle keyFlgTitle
     */
    public void setKeyFlgTitle(boolean keyFlgTitle) {
        this.keyFlgTitle__ = keyFlgTitle;
    }
    /**
     * <p>keyFlgSyokan を取得します。
     * @return keyFlgSyokan
     */
    public boolean isKeyFlgSyokan() {
        return keyFlgSyokan__;
    }
    /**
     * <p>keyFlgSyokan をセットします。
     * @param keyFlgSyokan keyFlgSyokan
     */
    public void setKeyFlgSyokan(boolean keyFlgSyokan) {
        this.keyFlgSyokan__ = keyFlgSyokan;
    }
    /**
     * <p>titleClrBlue を取得します。
     * @return titleClrBlue
     */
    public boolean isTitleClrBlue() {
        return titleClrBlue__;
    }
    /**
     * <p>titleClrBlue をセットします。
     * @param titleClrBlue titleClrBlue
     */
    public void setTitleClrBlue(boolean titleClrBlue) {
        this.titleClrBlue__ = titleClrBlue;
    }
    /**
     * <p>titleClrRed を取得します。
     * @return titleClrRed
     */
    public boolean isTitleClrRed() {
        return titleClrRed__;
    }
    /**
     * <p>titleClrRed をセットします。
     * @param titleClrRed titleClrRed
     */
    public void setTitleClrRed(boolean titleClrRed) {
        this.titleClrRed__ = titleClrRed;
    }
    /**
     * <p>titleClrGleen を取得します。
     * @return titleClrGleen
     */
    public boolean isTitleClrGleen() {
        return titleClrGleen__;
    }
    /**
     * <p>titleClrGleen をセットします。
     * @param titleClrGleen titleClrGleen
     */
    public void setTitleClrGleen(boolean titleClrGleen) {
        this.titleClrGleen__ = titleClrGleen;
    }
    /**
     * <p>titleClrYellow を取得します。
     * @return titleClrYellow
     */
    public boolean isTitleClrYellow() {
        return titleClrYellow__;
    }
    /**
     * <p>titleClrYellow をセットします。
     * @param titleClrYellow titleClrYellow
     */
    public void setTitleClrYellow(boolean titleClrYellow) {
        this.titleClrYellow__ = titleClrYellow;
    }
    /**
     * <p>titleClrBlack を取得します。
     * @return titleClrBlack
     */
    public boolean isTitleClrBlack() {
        return titleClrBlack__;
    }
    /**
     * <p>titleClrBlack をセットします。
     * @param titleClrBlack titleClrBlack
     */
    public void setTitleClrBlack(boolean titleClrBlack) {
        this.titleClrBlack__ = titleClrBlack;
    }
    /**
     * <p>startFrom を取得します。
     * @return startFrom
     */
    public String getStartFrom() {
        return startFrom__;
    }
    /**
     * <p>startFrom をセットします。
     * @param startFrom startFrom
     */
    public void setStartFrom(String startFrom) {
        this.startFrom__ = startFrom;
    }
    /**
     * <p>startTo を取得します。
     * @return startTo
     */
    public String getStartTo() {
        return startTo__;
    }
    /**
     * <p>startTo をセットします。
     * @param startTo startTo
     */
    public void setStartTo(String startTo) {
        this.startTo__ = startTo;
    }
    /**
     * <p>endFrom を取得します。
     * @return endFrom
     */
    public String getEndFrom() {
        return endFrom__;
    }
    /**
     * <p>endFrom をセットします。
     * @param endFrom endFrom
     */
    public void setEndFrom(String endFrom) {
        this.endFrom__ = endFrom;
    }
    /**
     * <p>endTo を取得します。
     * @return endTo
     */
    public String getEndTo() {
        return endTo__;
    }
    /**
     * <p>endTo をセットします。
     * @param endTo endTo
     */
    public void setEndTo(String endTo) {
        this.endTo__ = endTo;
    }
    /**
     * <p>upFrom を取得します。
     * @return upFrom
     */
    public String getUpFrom() {
        return upFrom__;
    }
    /**
     * <p>upFrom をセットします。
     * @param upFrom upFrom
     */
    public void setUpFrom(String upFrom) {
        this.upFrom__ = upFrom;
    }
    /**
     * <p>upTo を取得します。
     * @return upTo
     */
    public String getUpTo() {
        return upTo__;
    }
    /**
     * <p>upTo をセットします。
     * @param upTo upTo
     */
    public void setUpTo(String upTo) {
        this.upTo__ = upTo;
    }
    /**
     * <p>searchCnt を取得します。
     * @return searchCnt
     */
    public int getSearchCnt() {
        return searchCnt__;
    }
    /**
     * <p>searchCnt をセットします。
     * @param searchCnt searchCnt
     */
    public void setSearchCnt(int searchCnt) {
        this.searchCnt__ = searchCnt;
    }
    /**
     * <p>start を取得します。
     * @return start
     */
    public int getStart() {
        return start__;
    }
    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(int start) {
        this.start__ = start;
    }
}

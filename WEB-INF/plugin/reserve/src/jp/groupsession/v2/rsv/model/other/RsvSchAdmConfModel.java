package jp.groupsession.v2.rsv.model.other;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SCH_ADM_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSchAdmConfModel implements Serializable {

    /** SAD_CRANGE mapping */
    private int sadCrange__;
    /** SAD_ATDEL_FLG mapping */
    private int sadAtdelFlg__;
    /** SAD_ATDEL_Y mapping */
    private int sadAtdelY__;
    /** SAD_ATDEL_M mapping */
    private int sadAtdelM__;
    /** SAD_AUID mapping */
    private int sadAuid__;
    /** SAD_ADATE mapping */
    private UDate sadAdate__;
    /** SAD_EUID mapping */
    private int sadEuid__;
    /** SAD_EDATE mapping */
    private UDate sadEdate__;

    //2007/11/27追加
    /** SAD_HOUR_DIV mapping */
    private int sadHourDiv__;
    /** SAD_COLOR_MSG1 mapping */
    private String sadColorMsg1__;
    /** SAD_COLOR_MSG2 mapping */
    private String sadColorMsg2__;
    /** SAD_COLOR_MSG3 mapping */
    private String sadColorMsg3__;
    /** SAD_COLOR_MSG4 mapping */
    private String sadColorMsg4__;
    /** SAD_COLOR_MSG5 mapping */
    private String sadColorMsg5__;

    //2010/3/23追加
    /** SAD_SORT_KBN mapping */
    private int sadSortKbn__;
    /** SAD_SORT_KEY1 mapping */
    private int sadSortKey1__;
    /** SAD_SORT_ORDER1 mapping */
    private int sadSortOrder1__;
    /** SAD_SORT_KEY2 mapping */
    private int sadSortKey2__;
    /** SAD_SORT_ORDER2 mapping */
    private int sadSortOrder2__;

    //2011/10/28 追加
    /** SAD_INI_EDIT_STYPE mapping */
    private int sadIniEditStype__;
    /** SAD_INI_EDIT mapping */
    private int sadIniEdit__;
    /** SAD_REPEAT_STYPE mapping */
    private int sadRepeatStype__;
    /** SAD_REPEAT_KBN mapping */
    private int sadRepeatKbn__;
    /** SAD_REPEAT_MY_KBN mapping */
    private int sadRepeatMyKbn__;

    /**
     * <p>Default Constructor
     */
    public RsvSchAdmConfModel() {
    }

    /**
     * <p>sadColorMsg1 を取得します。
     * @return sadColorMsg1
     */
    public String getSadColorMsg1() {
        return sadColorMsg1__;
    }

    /**
     * <p>sadColorMsg1 をセットします。
     * @param sadColorMsg1 sadColorMsg1
     */
    public void setSadColorMsg1(String sadColorMsg1) {
        sadColorMsg1__ = sadColorMsg1;
    }

    /**
     * <p>sadColorMsg2 を取得します。
     * @return sadColorMsg2
     */
    public String getSadColorMsg2() {
        return sadColorMsg2__;
    }

    /**
     * <p>sadColorMsg2 をセットします。
     * @param sadColorMsg2 sadColorMsg2
     */
    public void setSadColorMsg2(String sadColorMsg2) {
        sadColorMsg2__ = sadColorMsg2;
    }

    /**
     * <p>sadColorMsg3 を取得します。
     * @return sadColorMsg3
     */
    public String getSadColorMsg3() {
        return sadColorMsg3__;
    }

    /**
     * <p>sadColorMsg3 をセットします。
     * @param sadColorMsg3 sadColorMsg3
     */
    public void setSadColorMsg3(String sadColorMsg3) {
        sadColorMsg3__ = sadColorMsg3;
    }

    /**
     * <p>sadColorMsg4 を取得します。
     * @return sadColorMsg4
     */
    public String getSadColorMsg4() {
        return sadColorMsg4__;
    }

    /**
     * <p>sadColorMsg4 をセットします。
     * @param sadColorMsg4 sadColorMsg4
     */
    public void setSadColorMsg4(String sadColorMsg4) {
        sadColorMsg4__ = sadColorMsg4;
    }

    /**
     * <p>sadColorMsg5 を取得します。
     * @return sadColorMsg5
     */
    public String getSadColorMsg5() {
        return sadColorMsg5__;
    }

    /**
     * <p>sadColorMsg5 をセットします。
     * @param sadColorMsg5 sadColorMsg5
     */
    public void setSadColorMsg5(String sadColorMsg5) {
        sadColorMsg5__ = sadColorMsg5;
    }

    /**
     * <p>sadHourDiv を取得します。
     * @return sadHourDiv
     */
    public int getSadHourDiv() {
        return sadHourDiv__;
    }

    /**
     * <p>sadHourDiv をセットします。
     * @param sadHourDiv sadHourDiv
     */
    public void setSadHourDiv(int sadHourDiv) {
        sadHourDiv__ = sadHourDiv;
    }

    /**
     * <p>get SAD_CRANGE value
     * @return SAD_CRANGE value
     */
    public int getSadCrange() {
        return sadCrange__;
    }

    /**
     * <p>set SAD_CRANGE value
     * @param sadCrange SAD_CRANGE value
     */
    public void setSadCrange(int sadCrange) {
        sadCrange__ = sadCrange;
    }

    /**
     * <p>get SAD_ATDEL_FLG value
     * @return SAD_ATDEL_FLG value
     */
    public int getSadAtdelFlg() {
        return sadAtdelFlg__;
    }

    /**
     * <p>set SAD_ATDEL_FLG value
     * @param sadAtdelFlg SAD_ATDEL_FLG value
     */
    public void setSadAtdelFlg(int sadAtdelFlg) {
        sadAtdelFlg__ = sadAtdelFlg;
    }

    /**
     * <p>get SAD_ATDEL_Y value
     * @return SAD_ATDEL_Y value
     */
    public int getSadAtdelY() {
        return sadAtdelY__;
    }

    /**
     * <p>set SAD_ATDEL_Y value
     * @param sadAtdelY SAD_ATDEL_Y value
     */
    public void setSadAtdelY(int sadAtdelY) {
        sadAtdelY__ = sadAtdelY;
    }

    /**
     * <p>get SAD_ATDEL_M value
     * @return SAD_ATDEL_M value
     */
    public int getSadAtdelM() {
        return sadAtdelM__;
    }

    /**
     * <p>set SAD_ATDEL_M value
     * @param sadAtdelM SAD_ATDEL_M value
     */
    public void setSadAtdelM(int sadAtdelM) {
        sadAtdelM__ = sadAtdelM;
    }

    /**
     * <p>get SAD_AUID value
     * @return SAD_AUID value
     */
    public int getSadAuid() {
        return sadAuid__;
    }

    /**
     * <p>set SAD_AUID value
     * @param sadAuid SAD_AUID value
     */
    public void setSadAuid(int sadAuid) {
        sadAuid__ = sadAuid;
    }

    /**
     * <p>get SAD_ADATE value
     * @return SAD_ADATE value
     */
    public UDate getSadAdate() {
        return sadAdate__;
    }

    /**
     * <p>set SAD_ADATE value
     * @param sadAdate SAD_ADATE value
     */
    public void setSadAdate(UDate sadAdate) {
        sadAdate__ = sadAdate;
    }

    /**
     * <p>get SAD_EUID value
     * @return SAD_EUID value
     */
    public int getSadEuid() {
        return sadEuid__;
    }

    /**
     * <p>set SAD_EUID value
     * @param sadEuid SAD_EUID value
     */
    public void setSadEuid(int sadEuid) {
        sadEuid__ = sadEuid;
    }

    /**
     * <p>get SAD_EDATE value
     * @return SAD_EDATE value
     */
    public UDate getSadEdate() {
        return sadEdate__;
    }

    /**
     * <p>set SAD_EDATE value
     * @param sadEdate SAD_EDATE value
     */
    public void setSadEdate(UDate sadEdate) {
        sadEdate__ = sadEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sadCrange__);
        buf.append(",");
        buf.append(sadAtdelFlg__);
        buf.append(",");
        buf.append(sadAtdelY__);
        buf.append(",");
        buf.append(sadAtdelM__);
        buf.append(",");
        buf.append(sadAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sadAdate__, ""));
        buf.append(",");
        buf.append(sadEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sadEdate__, ""));
        return buf.toString();
    }

    /**
     * @return sadSortKbn
     */
    public int getSadSortKbn() {
        return sadSortKbn__;
    }

    /**
     * @param sadSortKbn 設定する sadSortKbn
     */
    public void setSadSortKbn(int sadSortKbn) {
        sadSortKbn__ = sadSortKbn;
    }

    /**
     * @return sadSortKey1
     */
    public int getSadSortKey1() {
        return sadSortKey1__;
    }

    /**
     * @param sadSortKey1 設定する sadSortKey1
     */
    public void setSadSortKey1(int sadSortKey1) {
        sadSortKey1__ = sadSortKey1;
    }

    /**
     * @return sadSortKey2
     */
    public int getSadSortKey2() {
        return sadSortKey2__;
    }

    /**
     * @param sadSortKey2 設定する sadSortKey2
     */
    public void setSadSortKey2(int sadSortKey2) {
        sadSortKey2__ = sadSortKey2;
    }

    /**
     * @return sadSortOrder1
     */
    public int getSadSortOrder1() {
        return sadSortOrder1__;
    }

    /**
     * @param sadSortOrder1 設定する sadSortOrder1
     */
    public void setSadSortOrder1(int sadSortOrder1) {
        sadSortOrder1__ = sadSortOrder1;
    }

    /**
     * @return sadSortOrder2
     */
    public int getSadSortOrder2() {
        return sadSortOrder2__;
    }

    /**
     * @param sadSortOrder2 設定する sadSortOrder2
     */
    public void setSadSortOrder2(int sadSortOrder2) {
        sadSortOrder2__ = sadSortOrder2;
    }

    /**
     * <p>get SAD_INI_EDIT_STYPE value
     * @return SAD_INI_EDIT_STYPE value
     */
    public int getSadIniEditStype() {
        return sadIniEditStype__;
    }

    /**
     * <p>set SAD_INI_EDIT_STYPE value
     * @param sadIniEditStype SAD_INI_EDIT_STYPE value
     */
    public void setSadIniEditStype(int sadIniEditStype) {
        sadIniEditStype__ = sadIniEditStype;
    }

    /**
     * <p>get SAD_INI_EDIT value
     * @return SAD_INI_EDIT value
     */
    public int getSadIniEdit() {
        return sadIniEdit__;
    }

    /**
     * <p>set SAD_INI_EDIT value
     * @param sadIniEdit SAD_INI_EDIT value
     */
    public void setSadIniEdit(int sadIniEdit) {
        sadIniEdit__ = sadIniEdit;
    }

    /**
     * <p>get SAD_REPEAT_STYPE value
     * @return SAD_REPEAT_STYPE value
     */
    public int getSadRepeatStype() {
        return sadRepeatStype__;
    }

    /**
     * <p>set SAD_REPEAT_STYPE value
     * @param sadRepeatStype SAD_REPEAT_STYPE value
     */
    public void setSadRepeatStype(int sadRepeatStype) {
        sadRepeatStype__ = sadRepeatStype;
    }

    /**
     * <p>get SAD_REPEAT_KBN value
     * @return SAD_REPEAT_KBN value
     */
    public int getSadRepeatKbn() {
        return sadRepeatKbn__;
    }

    /**
     * <p>set SAD_REPEAT_KBN value
     * @param sadRepeatKbn SAD_REPEAT_KBN value
     */
    public void setSadRepeatKbn(int sadRepeatKbn) {
        sadRepeatKbn__ = sadRepeatKbn;
    }

    /**
     * <p>get SAD_REPEAT_MY_KBN value
     * @return SAD_REPEAT_MY_KBN value
     */
    public int getSadRepeatMyKbn() {
        return sadRepeatMyKbn__;
    }

    /**
     * <p>set SAD_REPEAT_MY_KBN value
     * @param sadRepeatMyKbn SAD_REPEAT_MY_KBN value
     */
    public void setSadRepeatMyKbn(int sadRepeatMyKbn) {
        sadRepeatMyKbn__ = sadRepeatMyKbn;
    }
}

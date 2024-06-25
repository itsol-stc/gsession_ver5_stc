package jp.groupsession.v2.ntp.ntp240;

import java.util.List;

import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.ntp090.Ntp090Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 目標設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp240Form extends Ntp090Form {

    /** 表示開始日付*/
    private String ntp240DspDate__ = null;
    /** 表示グループSID */
    private String ntp240DspGpSid__;
    /** ユーザSID */
    private String ntp240SelectUsrSid__;
    /** グループコンボ */
    private List<NtpLabelValueModel> ntp240GpLabelList__ = null;
    /** ユーザラベル */
    private List<UsrLabelValueBean> ntp240UserLabel__;

    /** ユーザ名*/
    private String ntp240UserName__ = null;

    /** 目標日付 年 */
    private String ntp240Year__ = null;
    /** 目標日付 月 */
    private String ntp240Month__ = null;

    /** 目標日付 年ラベル */
    private List<LabelValueBean> ntp240YearLabel__;
    /** 目標日付 月ラベル */
    private List<LabelValueBean> ntp240MonthLabel__;

    /** 適用テンプレート名 */
    private String ntp240Template__ = "";
    /** 目標リスト */
    private List<NtpTargetModel> ntp240TargetList__;

    /** 目標画面リスト */
    private List<Ntp240DspTargetModel> ntp240DspTargetList__;

    /**
     * <p>ntp240DspDate を取得します。
     * @return ntp240DspDate
     */
    public String getNtp240DspDate() {
        return ntp240DspDate__;
    }
    /**
     * <p>ntp240DspDate をセットします。
     * @param ntp240DspDate ntp240DspDate
     */
    public void setNtp240DspDate(String ntp240DspDate) {
        ntp240DspDate__ = ntp240DspDate;
    }
    /**
     * <p>ntp240DspGpSid を取得します。
     * @return ntp240DspGpSid
     */
    public String getNtp240DspGpSid() {
        return ntp240DspGpSid__;
    }
    /**
     * <p>ntp240DspGpSid をセットします。
     * @param ntp240DspGpSid ntp240DspGpSid
     */
    public void setNtp240DspGpSid(String ntp240DspGpSid) {
        ntp240DspGpSid__ = ntp240DspGpSid;
    }
    /**
     * <p>ntp240SelectUsrSid を取得します。
     * @return ntp240SelectUsrSid
     */
    public String getNtp240SelectUsrSid() {
        return ntp240SelectUsrSid__;
    }
    /**
     * <p>ntp240SelectUsrSid をセットします。
     * @param ntp240SelectUsrSid ntp240SelectUsrSid
     */
    public void setNtp240SelectUsrSid(String ntp240SelectUsrSid) {
        ntp240SelectUsrSid__ = ntp240SelectUsrSid;
    }
    /**
     * <p>ntp240GpLabelList を取得します。
     * @return ntp240GpLabelList
     */
    public List<NtpLabelValueModel> getNtp240GpLabelList() {
        return ntp240GpLabelList__;
    }
    /**
     * <p>ntp240GpLabelList をセットします。
     * @param ntp240GpLabelList ntp240GpLabelList
     */
    public void setNtp240GpLabelList(List<NtpLabelValueModel> ntp240GpLabelList) {
        ntp240GpLabelList__ = ntp240GpLabelList;
    }
    /**
     * <p>ntp240Year を取得します。
     * @return ntp240Year
     */
    public String getNtp240Year() {
        return ntp240Year__;
    }
    /**
     * <p>ntp240Year をセットします。
     * @param ntp240Year ntp240Year
     */
    public void setNtp240Year(String ntp240Year) {
        ntp240Year__ = ntp240Year;
    }
    /**
     * <p>ntp240Month を取得します。
     * @return ntp240Month
     */
    public String getNtp240Month() {
        return ntp240Month__;
    }
    /**
     * <p>ntp240Month をセットします。
     * @param ntp240Month ntp240Month
     */
    public void setNtp240Month(String ntp240Month) {
        ntp240Month__ = ntp240Month;
    }
    /**
     * <p>ntp240YearLabel を取得します。
     * @return ntp240YearLabel
     */
    public List<LabelValueBean> getNtp240YearLabel() {
        return ntp240YearLabel__;
    }
    /**
     * <p>ntp240YearLabel をセットします。
     * @param ntp240YearLabel ntp240YearLabel
     */
    public void setNtp240YearLabel(List<LabelValueBean> ntp240YearLabel) {
        ntp240YearLabel__ = ntp240YearLabel;
    }
    /**
     * <p>ntp240MonthLabel を取得します。
     * @return ntp240MonthLabel
     */
    public List<LabelValueBean> getNtp240MonthLabel() {
        return ntp240MonthLabel__;
    }
    /**
     * <p>ntp240MonthLabel をセットします。
     * @param ntp240MonthLabel ntp240MonthLabel
     */
    public void setNtp240MonthLabel(List<LabelValueBean> ntp240MonthLabel) {
        ntp240MonthLabel__ = ntp240MonthLabel;
    }
    /**
     * <p>ntp240UserLabel を取得します。
     * @return ntp240UserLabel
     */
    public List<UsrLabelValueBean> getNtp240UserLabel() {
        return ntp240UserLabel__;
    }
    /**
     * <p>ntp240UserLabel をセットします。
     * @param ntp240UserLabel ntp240UserLabel
     */
    public void setNtp240UserLabel(List<UsrLabelValueBean> ntp240UserLabel) {
        ntp240UserLabel__ = ntp240UserLabel;
    }
    /**
     * <p>ntp240UserName を取得します。
     * @return ntp240UserName
     */
    public String getNtp240UserName() {
        return ntp240UserName__;
    }
    /**
     * <p>ntp240UserName をセットします。
     * @param ntp240UserName ntp240UserName
     */
    public void setNtp240UserName(String ntp240UserName) {
        ntp240UserName__ = ntp240UserName;
    }
    /**
     * <p>ntp240TargetList を取得します。
     * @return ntp240TargetList
     */
    public List<NtpTargetModel> getNtp240TargetList() {
        return ntp240TargetList__;
    }
    /**
     * <p>ntp240TargetList をセットします。
     * @param ntp240TargetList ntp240TargetList
     */
    public void setNtp240TargetList(List<NtpTargetModel> ntp240TargetList) {
        ntp240TargetList__ = ntp240TargetList;
    }
    /**
     * <p>ntp240Template を取得します。
     * @return ntp240Template
     */
    public String getNtp240Template() {
        return ntp240Template__;
    }
    /**
     * <p>ntp240Template をセットします。
     * @param ntp240Template ntp240Template
     */
    public void setNtp240Template(String ntp240Template) {
        ntp240Template__ = ntp240Template;
    }
    /**
     * <p>ntp240DspTargetList を取得します。
     * @return ntp240DspTargetList
     */
    public List<Ntp240DspTargetModel> getNtp240DspTargetList() {
        return ntp240DspTargetList__;
    }
    /**
     * <p>ntp240DspTargetList をセットします。
     * @param ntp240DspTargetList ntp240DspTargetList
     */
    public void setNtp240DspTargetList(
            List<Ntp240DspTargetModel> ntp240DspTargetList) {
        ntp240DspTargetList__ = ntp240DspTargetList;
    }
}

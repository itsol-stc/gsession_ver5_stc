package jp.groupsession.v2.ntp.ntp220;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 日報分析画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Ntp220Form extends Ntp010Form {
    /** モード 0:集計 1:個人*/
    private int ntp220mode__ = 1;
    /** グループSID */
    private String ntp220GroupSid__ = null;
    /** グループリスト */
    private List<NtpLabelValueModel> ntp220GroupLavel__ = null;
    /** ユーザラベル */
    private List<UsrLabelValueBean> ntp220UserLabel__;
    /** ユーザSID */
    private String ntp220SelectUsrSid__;
    /** ユーザ名 */
    private String ntp220DspUsrName__;


    /** 日付範囲 開始 */
    private String ntp220DateFrStr__;
    /** 日付範囲 終了 */
    private String ntp220DateToStr__;

    /** 状態  0:すべて 1:進行中 2:完了*/
    private int ntp220State__ = -1;
    /** 案件状態  0:商談中 1:受注 2:失注 */
    private int ntp220AnkenState__ = -1;

    /** 選択中の親要素 */
    private String ntp220NowSelParentId__ = "main";
    /** 選択中の子要素 */
    private String ntp220NowSelChildId__;

    /** 業種 */
    private int ntp220GyoushuSid__ = 0;
    /** 業種コンボ */
    private List<LabelValueBean> ntp220GyoushuList__ = null;

    /** カテゴリSID */
    private int ntp220CatSid__ = -1;
    /** カテゴリリスト一覧 */
    private List<LabelValueBean> ntp220CategoryList__ = null;

    /**
     * <p>ntp220mode を取得します。
     * @return ntp220mode
     */
    public int getNtp220mode() {
        return ntp220mode__;
    }

    /**
     * <p>ntp220mode をセットします。
     * @param ntp220mode ntp220mode
     */
    public void setNtp220mode(int ntp220mode) {
        ntp220mode__ = ntp220mode;
    }

    /**
     * <p>ntp220GroupSid を取得します。
     * @return ntp220GroupSid
     */
    public String getNtp220GroupSid() {
        return ntp220GroupSid__;
    }

    /**
     * <p>ntp220GroupSid をセットします。
     * @param ntp220GroupSid ntp220GroupSid
     */
    public void setNtp220GroupSid(String ntp220GroupSid) {
        ntp220GroupSid__ = ntp220GroupSid;
    }

    /**
     * <p>ntp220GroupLavel を取得します。
     * @return ntp220GroupLavel
     */
    public List<NtpLabelValueModel> getNtp220GroupLavel() {
        return ntp220GroupLavel__;
    }

    /**
     * <p>ntp220GroupLavel をセットします。
     * @param ntp220GroupLavel ntp220GroupLavel
     */
    public void setNtp220GroupLavel(List<NtpLabelValueModel> ntp220GroupLavel) {
        ntp220GroupLavel__ = ntp220GroupLavel;
    }


    /**
     * <p>ntp220SelectUsrSid を取得します。
     * @return ntp220SelectUsrSid
     */
    public String getNtp220SelectUsrSid() {
        return ntp220SelectUsrSid__;
    }

    /**
     * <p>ntp220SelectUsrSid をセットします。
     * @param ntp220SelectUsrSid ntp220SelectUsrSid
     */
    public void setNtp220SelectUsrSid(String ntp220SelectUsrSid) {
        ntp220SelectUsrSid__ = ntp220SelectUsrSid;
    }

    /**
     * <p>ntp220DateFrStr を取得します。
     * @return ntp220DateFrStr
     */
    public String getNtp220DateFrStr() {
        return ntp220DateFrStr__;
    }

    /**
     * <p>ntp220DateFrStr をセットします。
     * @param ntp220DateFrStr ntp220DateFrStr
     */
    public void setNtp220DateFrStr(String ntp220DateFrStr) {
        ntp220DateFrStr__ = ntp220DateFrStr;
    }

    /**
     * <p>ntp220DateToStr を取得します。
     * @return ntp220DateToStr
     */
    public String getNtp220DateToStr() {
        return ntp220DateToStr__;
    }

    /**
     * <p>ntp220DateToStr をセットします。
     * @param ntp220DateToStr ntp220DateToStr
     */
    public void setNtp220DateToStr(String ntp220DateToStr) {
        ntp220DateToStr__ = ntp220DateToStr;
    }

    /**
     * <p>ntp220NowSelParentId を取得します。
     * @return ntp220NowSelParentId
     */
    public String getNtp220NowSelParentId() {
        return ntp220NowSelParentId__;
    }

    /**
     * <p>ntp220NowSelParentId をセットします。
     * @param ntp220NowSelParentId ntp220NowSelParentId
     */
    public void setNtp220NowSelParentId(String ntp220NowSelParentId) {
        ntp220NowSelParentId__ = ntp220NowSelParentId;
    }

    /**
     * <p>ntp220NowSelChildId を取得します。
     * @return ntp220NowSelChildId
     */
    public String getNtp220NowSelChildId() {
        return ntp220NowSelChildId__;
    }

    /**
     * <p>ntp220NowSelChildId をセットします。
     * @param ntp220NowSelChildId ntp220NowSelChildId
     */
    public void setNtp220NowSelChildId(String ntp220NowSelChildId) {
        ntp220NowSelChildId__ = ntp220NowSelChildId;
    }

    /**
     * <p>ntp220State を取得します。
     * @return ntp220State
     */
    public int getNtp220State() {
        return ntp220State__;
    }

    /**
     * <p>ntp220State をセットします。
     * @param ntp220State ntp220State
     */
    public void setNtp220State(int ntp220State) {
        ntp220State__ = ntp220State;
    }

    /**
     * <p>ntp220AnkenState を取得します。
     * @return ntp220AnkenState
     */
    public int getNtp220AnkenState() {
        return ntp220AnkenState__;
    }

    /**
     * <p>ntp220AnkenState をセットします。
     * @param ntp220AnkenState ntp220AnkenState
     */
    public void setNtp220AnkenState(int ntp220AnkenState) {
        ntp220AnkenState__ = ntp220AnkenState;
    }

    /**
     * <p>ntp220GyoushuSid を取得します。
     * @return ntp220GyoushuSid
     */
    public int getNtp220GyoushuSid() {
        return ntp220GyoushuSid__;
    }

    /**
     * <p>ntp220GyoushuSid をセットします。
     * @param ntp220GyoushuSid ntp220GyoushuSid
     */
    public void setNtp220GyoushuSid(int ntp220GyoushuSid) {
        ntp220GyoushuSid__ = ntp220GyoushuSid;
    }

    /**
     * <p>ntp220GyoushuList を取得します。
     * @return ntp220GyoushuList
     */
    public List<LabelValueBean> getNtp220GyoushuList() {
        return ntp220GyoushuList__;
    }

    /**
     * <p>ntp220GyoushuList をセットします。
     * @param ntp220GyoushuList ntp220GyoushuList
     */
    public void setNtp220GyoushuList(List<LabelValueBean> ntp220GyoushuList) {
        ntp220GyoushuList__ = ntp220GyoushuList;
    }

    /**
     * <p>ntp220UserLabel を取得します。
     * @return ntp220UserLabel
     */
    public List<UsrLabelValueBean> getNtp220UserLabel() {
        return ntp220UserLabel__;
    }

    /**
     * <p>ntp220UserLabel をセットします。
     * @param ntp220UserLabel ntp220UserLabel
     */
    public void setNtp220UserLabel(List<UsrLabelValueBean> ntp220UserLabel) {
        ntp220UserLabel__ = ntp220UserLabel;
    }

    /**
     * <p>ntp220DspUsrName を取得します。
     * @return ntp220DspUsrName
     */
    public String getNtp220DspUsrName() {
        return ntp220DspUsrName__;
    }

    /**
     * <p>ntp220DspUsrName をセットします。
     * @param ntp220DspUsrName ntp220DspUsrName
     */
    public void setNtp220DspUsrName(String ntp220DspUsrName) {
        ntp220DspUsrName__ = ntp220DspUsrName;
    }

    /**
     * <p>ntp220CategoryList を取得します。
     * @return ntp220CategoryList
     */
    public List<LabelValueBean> getNtp220CategoryList() {
        return ntp220CategoryList__;
    }

    /**
     * <p>ntp220CategoryList をセットします。
     * @param ntp220CategoryList ntp220CategoryList
     */
    public void setNtp220CategoryList(List<LabelValueBean> ntp220CategoryList) {
        ntp220CategoryList__ = ntp220CategoryList;
    }

    /**
     * <p>ntp220CatSid を取得します。
     * @return ntp220CatSid
     */
    public int getNtp220CatSid() {
        return ntp220CatSid__;
    }

    /**
     * <p>ntp220CatSid をセットします。
     * @param ntp220CatSid ntp220CatSid
     */
    public void setNtp220CatSid(int ntp220CatSid) {
        ntp220CatSid__ = ntp220CatSid;
    }
}
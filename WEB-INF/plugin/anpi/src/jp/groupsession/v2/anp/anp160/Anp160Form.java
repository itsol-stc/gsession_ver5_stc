package jp.groupsession.v2.anp.anp160;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.anp.anp060kn.Anp060knForm;


/**
 * <br>[機  能] メッセージ配信確認 送信者一覧(ポップアップ画面)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp160Form extends Anp060knForm {

    /** 緊急連絡先設定有無フラグ 未設定ユーザ 無し */
    public static final int MAIL_NOSET_USER_NO = 0;
    /** 緊急連絡先設定有無フラグ 未設定ユーザ 有り */
    public static final int MAIL_NOSET_USER_YES = 1;
    /** 緊急連絡先設定フラグ 未設定 */
    public static final int MAIL_SET_NO = 0;
    /** 緊急連絡先設定フラグ 設定 */
    public static final int MAIL_SET_YES = 1;

    /** プロセスモード */
    private String anp160ProcMode__;
    /** グループSID */
    private String anp160GrpSid__;
    /** グループ名称 */
    private String anp160DispGrpName__;
    /** 送信者リスト */
    private List<Anp160DspModel> anp160DspSenderList__;
    /** 緊急連絡先未設定ユーザ有無フラグ */
    private int anp160NosetMailFlg__;

    /** 現在ページ */
    private int anp160NowPage__ = 1;
    /** 表示ページ（上） */
    private int anp160DspPage1__;
    /** 表示ページ（下） */
    private int anp160DspPage2__;
    /** ページラベルリスト */
    private List<LabelValueBean> anp160PageLabel__;

    /**
     * <p>anp160DispGrpName を取得します。
     * @return anp160DispGrpName
     */
    public String getAnp160DispGrpName() {
        return anp160DispGrpName__;
    }
    /**
     * <p>anp160DispGrpName をセットします。
     * @param anp160DispGrpName anp160DispGrpName
     */
    public void setAnp160DispGrpName(String anp160DispGrpName) {
        anp160DispGrpName__ = anp160DispGrpName;
    }

    /**
     * <p>anp160NosetMailFlg を取得します。
     * @return anp160NosetMailFlg
     */
    public int getAnp160NosetMailFlg() {
        return anp160NosetMailFlg__;
    }
    /**
     * <p>anp160NosetMailFlg をセットします。
     * @param anp160NosetMailFlg anp160NosetMailFlg
     */
    public void setAnp160NosetMailFlg(int anp160NosetMailFlg) {
        anp160NosetMailFlg__ = anp160NosetMailFlg;
    }
    /**
     * <p>anp160DspSenderList を取得します。
     * @return anp160DspSenderList
     */
    public List<Anp160DspModel> getAnp160DspSenderList() {
        return anp160DspSenderList__;
    }
    /**
     * <p>anp160DspSenderList をセットします。
     * @param anp160DspSenderList anp160DspSenderList
     */
    public void setAnp160DspSenderList(List<Anp160DspModel> anp160DspSenderList) {
        anp160DspSenderList__ = anp160DspSenderList;
    }
    /**
     * <p>anp160NowPage を取得します。
     * @return anp160NowPage
     */
    public int getAnp160NowPage() {
        return anp160NowPage__;
    }
    /**
     * <p>anp160NowPage をセットします。
     * @param anp160NowPage anp160NowPage
     */
    public void setAnp160NowPage(int anp160NowPage) {
        anp160NowPage__ = anp160NowPage;
    }
    /**
     * <p>anp160DspPage1 を取得します。
     * @return anp160DspPage1
     */
    public int getAnp160DspPage1() {
        return anp160DspPage1__;
    }
    /**
     * <p>anp160DspPage1 をセットします。
     * @param anp160DspPage1 anp160DspPage1
     */
    public void setAnp160DspPage1(int anp160DspPage1) {
        anp160DspPage1__ = anp160DspPage1;
    }
    /**
     * <p>anp160DspPage2 を取得します。
     * @return anp160DspPage2
     */
    public int getAnp160DspPage2() {
        return anp160DspPage2__;
    }
    /**
     * <p>anp160DspPage2 をセットします。
     * @param anp160DspPage2 anp160DspPage2
     */
    public void setAnp160DspPage2(int anp160DspPage2) {
        anp160DspPage2__ = anp160DspPage2;
    }
    /**
     * <p>anp160PageLabel を取得します。
     * @return anp160PageLabel
     */
    public List<LabelValueBean> getAnp160PageLabel() {
        return anp160PageLabel__;
    }
    /**
     * <p>anp160PageLabel をセットします。
     * @param anp160PageLabel anp160PageLabel
     */
    public void setAnp160PageLabel(List<LabelValueBean> anp160PageLabel) {
        anp160PageLabel__ = anp160PageLabel;
    }
    /**
     * <p>anp160GrpSid を取得します。
     * @return anp160GrpSid
     */
    public String getAnp160GrpSid() {
        return anp160GrpSid__;
    }
    /**
     * <p>anp160GrpSid をセットします。
     * @param anp160GrpSid anp160GrpSid
     */
    public void setAnp160GrpSid(String anp160GrpSid) {
        anp160GrpSid__ = anp160GrpSid;
    }
    /**
     * <p>anp160ProcMode を取得します。
     * @return anp160ProcMode
     */
    public String getAnp160ProcMode() {
        return anp160ProcMode__;
    }
    /**
     * <p>anp160ProcMode をセットします。
     * @param anp160ProcMode anp160ProcMode
     */
    public void setAnp160ProcMode(String anp160ProcMode) {
        anp160ProcMode__ = anp160ProcMode;
    }
}

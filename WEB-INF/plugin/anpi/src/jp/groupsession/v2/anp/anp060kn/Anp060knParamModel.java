package jp.groupsession.v2.anp.anp060kn;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp060.Anp060ParamModel;
import jp.groupsession.v2.anp.anp060kn.model.Anp060knSenderModel;

/**
 * <br>[機  能] 安否確認メッセージ配信確認画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp060knParamModel extends Anp060ParamModel {

    /** 送信先 グループ選択フラグ(選択) */
    public static final int GROUP_SELECT_YES = 1;
    /** 送信先 グループ選択フラグ(未選択) */
    public static final int GROUP_SELECT_NO = 0;
    /** 緊急連絡先設定フラグ (設定) */
    public static final int MAIL_SET_YES = 1;
    /** 緊急連絡先設定フラグ(未設定) */
    public static final int MAIL_SET_NO = 0;


    /** 件名（表示用） */
    private String anp060knDispSubject__;
    /** メッセージ本文（表示用） */
    private String anp060knDispMessageBody__;


    /** 画面モード（再配信選択用） */
    private String anp060knProcMode__;
    /** 未回答者フラグ（1：未回答者あり） */
    private int anp060knNoreplyCount__ = 0;

    /** 現在ページ */
    private int anp060knNowPage__ = 1;
    /** 表示ページ（上） */
    private int anp060knDspPage1__;
    /** 表示ページ（下） */
    private int anp060knDspPage2__;
    /** ページラベルリスト */
    private List<LabelValueBean> anp060knPageLabel__;

    /** 送信者リスト */
    private List<Anp060knSenderModel> anp060knSenderList__;
    /** 緊急連絡先設定数 */
    private int anp060knSetConCount__ = 0;

    /** 表示位置フラグ（1:送信先） */
    private String anp060knScrollFlg__;

    /**
     * <p>anp060knProcMode を取得します。
     * @return anp060knProcMode
     */
    public String getAnp060knProcMode() {
        return anp060knProcMode__;
    }
    /**
     * <p>anp060knProcMode をセットします。
     * @param anp060knProcMode anp060knProcMode
     */
    public void setAnp060knProcMode(String anp060knProcMode) {
        anp060knProcMode__ = anp060knProcMode;
    }
    /**
     * <p>anp060knNoreplyCount を取得します。
     * @return anp060knNoreplyCount
     */
    public int getAnp060knNoreplyCount() {
        return anp060knNoreplyCount__;
    }
    /**
     * <p>anp060knNoreplyCount をセットします。
     * @param anp060knNoreplyCount anp060knNoreplyCount
     */
    public void setAnp060knNoreplyCount(int anp060knNoreplyCount) {
        anp060knNoreplyCount__ = anp060knNoreplyCount;
    }
    /**
     * <p>anp060knSenderList を取得します。
     * @return anp060knSenderList
     */
    public List<Anp060knSenderModel> getAnp060knSenderList() {
        return anp060knSenderList__;
    }
    /**
     * <p>anp060knSenderList をセットします。
     * @param anp060knSenderList anp060knSenderList
     */
    public void setAnp060knSenderList(List<Anp060knSenderModel> anp060knSenderList) {
        anp060knSenderList__ = anp060knSenderList;
    }
    /**
     * <p>anp060knSetConCount を取得します。
     * @return anp060knSetConCount
     */
    public int getAnp060knSetConCount() {
        return anp060knSetConCount__;
    }
    /**
     * <p>anp060knSetConCount をセットします。
     * @param anp060knSetConCount anp060knSetConCount
     */
    public void setAnp060knSetConCount(int anp060knSetConCount) {
        anp060knSetConCount__ = anp060knSetConCount;
    }


    /**
     * <p>閲覧モードの場合、true;
     * @return 既存データを読み込む場合、true
     */
    public boolean isViewMode() {

        if (this.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_INFOCONF)) {
            return true;
        }
        return false;
    }
    /**
     * <p>anp060knDispSubject を取得します。
     * @return anp060knDispSubject
     */
    public String getAnp060knDispSubject() {
        return anp060knDispSubject__;
    }
    /**
     * <p>anp060knDispSubject をセットします。
     * @param anp060knDispSubject anp060knDispSubject
     */
    public void setAnp060knDispSubject(String anp060knDispSubject) {
        anp060knDispSubject__ = anp060knDispSubject;
    }
    /**
     * <p>anp060knDispMessageBody を取得します。
     * @return anp060knDispMessageBody
     */
    public String getAnp060knDispMessageBody() {
        return anp060knDispMessageBody__;
    }
    /**
     * <p>anp060knDispMessageBody をセットします。
     * @param anp060knDispMessageBody anp060knDispMessageBody
     */
    public void setAnp060knDispMessageBody(String anp060knDispMessageBody) {
        anp060knDispMessageBody__ = anp060knDispMessageBody;
    }
    /**
     * <p>anp060knNowPage を取得します。
     * @return anp060knNowPage
     */
    public int getAnp060knNowPage() {
        return anp060knNowPage__;
    }
    /**
     * <p>anp060knNowPage をセットします。
     * @param anp060knNowPage anp060knNowPage
     */
    public void setAnp060knNowPage(int anp060knNowPage) {
        anp060knNowPage__ = anp060knNowPage;
    }
    /**
     * <p>anp060knDspPage1 を取得します。
     * @return anp060knDspPage1
     */
    public int getAnp060knDspPage1() {
        return anp060knDspPage1__;
    }
    /**
     * <p>anp060knDspPage1 をセットします。
     * @param anp060knDspPage1 anp060knDspPage1
     */
    public void setAnp060knDspPage1(int anp060knDspPage1) {
        anp060knDspPage1__ = anp060knDspPage1;
    }
    /**
     * <p>anp060knDspPage2 を取得します。
     * @return anp060knDspPage2
     */
    public int getAnp060knDspPage2() {
        return anp060knDspPage2__;
    }
    /**
     * <p>anp060knDspPage2 をセットします。
     * @param anp060knDspPage2 anp060knDspPage2
     */
    public void setAnp060knDspPage2(int anp060knDspPage2) {
        anp060knDspPage2__ = anp060knDspPage2;
    }
    /**
     * <p>anp060knPageLabel を取得します。
     * @return anp060knPageLabel
     */
    public List<LabelValueBean> getAnp060knPageLabel() {
        return anp060knPageLabel__;
    }
    /**
     * <p>anp060knPageLabel をセットします。
     * @param anp060knPageLabel anp060knPageLabel
     */
    public void setAnp060knPageLabel(List<LabelValueBean> anp060knPageLabel) {
        anp060knPageLabel__ = anp060knPageLabel;
    }
    /**
     * <p>anp060knScrollFlg を取得します。
     * @return anp060knScrollFlg
     */
    public String getAnp060knScrollFlg() {
        return anp060knScrollFlg__;
    }
    /**
     * <p>anp060knScrollFlg をセットします。
     * @param anp060knScrollFlg anp060knScrollFlg
     */
    public void setAnp060knScrollFlg(String anp060knScrollFlg) {
        anp060knScrollFlg__ = anp060knScrollFlg;
    }
}
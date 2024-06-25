package jp.groupsession.v2.anp.anp130;

import java.util.List;

import jp.groupsession.v2.anp.anp070.Anp070ParamModel;
import jp.groupsession.v2.anp.anp130.model.Anp130SenderModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 管理者設定・配信履歴画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp130ParamModel extends Anp070ParamModel {

    /** 配信履歴一覧 */
    private List<Anp130SenderModel> anp130HaisinList__ = null;
    /** 選択された配信データSID */
    private int anp130SelectAphSid__;

    /** 全選択削除チェックボックス */
    private int anp130allCheck__ = 0;
    /** 一覧削除チェックボックス */
    private String[] anp130DelSidList__;

    /** 現在ページ */
    private int anp130NowPage__ = 1;
    /** 表示ページ（上） */
    private int anp130DspPage1__;
    /** 表示ページ（下） */
    private int anp130DspPage2__;
    /** ページラベルリスト */
    private List<LabelValueBean> anp130PageLabel__;
    /**
     * <p>anp130HaisinList を取得します。
     * @return anp130HaisinList
     */
    public List<Anp130SenderModel> getAnp130HaisinList() {
        return anp130HaisinList__;
    }
    /**
     * <p>anp130HaisinList をセットします。
     * @param anp130HaisinList anp130HaisinList
     */
    public void setAnp130HaisinList(List<Anp130SenderModel> anp130HaisinList) {
        anp130HaisinList__ = anp130HaisinList;
    }
    /**
     * <p>anp130SelectAphSid を取得します。
     * @return anp130SelectAphSid
     */
    public int getAnp130SelectAphSid() {
        return anp130SelectAphSid__;
    }
    /**
     * <p>anp130SelectAphSid をセットします。
     * @param anp130SelectAphSid anp130SelectAphSid
     */
    public void setAnp130SelectAphSid(int anp130SelectAphSid) {
        anp130SelectAphSid__ = anp130SelectAphSid;
    }
    /**
     * <p>anp130allCheck を取得します。
     * @return anp130allCheck
     */
    public int getAnp130allCheck() {
        return anp130allCheck__;
    }
    /**
     * <p>anp130allCheck をセットします。
     * @param anp130allCheck anp130allCheck
     */
    public void setAnp130allCheck(int anp130allCheck) {
        anp130allCheck__ = anp130allCheck;
    }
    /**
     * <p>anp130DelSidList を取得します。
     * @return anp130DelSidList
     */
    public String[] getAnp130DelSidList() {
        return anp130DelSidList__;
    }
    /**
     * <p>anp130DelSidList をセットします。
     * @param anp130DelSidList anp130DelSidList
     */
    public void setAnp130DelSidList(String[] anp130DelSidList) {
        anp130DelSidList__ = anp130DelSidList;
    }
    /**
     * <p>anp130NowPage を取得します。
     * @return anp130NowPage
     */
    public int getAnp130NowPage() {
        return anp130NowPage__;
    }
    /**
     * <p>anp130NowPage をセットします。
     * @param anp130NowPage anp130NowPage
     */
    public void setAnp130NowPage(int anp130NowPage) {
        anp130NowPage__ = anp130NowPage;
    }
    /**
     * <p>anp130DspPage1 を取得します。
     * @return anp130DspPage1
     */
    public int getAnp130DspPage1() {
        return anp130DspPage1__;
    }
    /**
     * <p>anp130DspPage1 をセットします。
     * @param anp130DspPage1 anp130DspPage1
     */
    public void setAnp130DspPage1(int anp130DspPage1) {
        anp130DspPage1__ = anp130DspPage1;
    }
    /**
     * <p>anp130DspPage2 を取得します。
     * @return anp130DspPage2
     */
    public int getAnp130DspPage2() {
        return anp130DspPage2__;
    }
    /**
     * <p>anp130DspPage2 をセットします。
     * @param anp130DspPage2 anp130DspPage2
     */
    public void setAnp130DspPage2(int anp130DspPage2) {
        anp130DspPage2__ = anp130DspPage2;
    }
    /**
     * <p>anp130PageLabel を取得します。
     * @return anp130PageLabel
     */
    public List<LabelValueBean> getAnp130PageLabel() {
        return anp130PageLabel__;
    }
    /**
     * <p>anp130PageLabel をセットします。
     * @param anp130PageLabel anp130PageLabel
     */
    public void setAnp130PageLabel(List<LabelValueBean> anp130PageLabel) {
        anp130PageLabel__ = anp130PageLabel;
    }
}
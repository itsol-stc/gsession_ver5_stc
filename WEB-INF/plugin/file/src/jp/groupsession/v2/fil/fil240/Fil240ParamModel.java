package jp.groupsession.v2.fil.fil240;

import java.util.ArrayList;

import jp.groupsession.v2.fil.fil010.FileLinkSimpleModel;
import jp.groupsession.v2.fil.fil070.Fil070ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 更新通知一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil240ParamModel extends Fil070ParamModel {

    /** 更新通知一覧 */
    private ArrayList<FileLinkSimpleModel> callList__;

    /** ページ1 */
    private int fil240Slt_page1__;
    /** ページ2 */
    private int fil240Slt_page2__;
    /** ページラベル */
    private ArrayList<LabelValueBean> fil240PageLabel__;

    /**
     * @return callList
     */
    public ArrayList<FileLinkSimpleModel> getCallList() {
        return callList__;
    }
    /**
     * @param callList 設定する callList
     */
    public void setCallList(ArrayList<FileLinkSimpleModel> callList) {
        callList__ = callList;
    }
    /**
     * <p>fil240Slt_page1 を取得します。
     * @return fil240Slt_page1
     */
    public int getFil240Slt_page1() {
        return fil240Slt_page1__;
    }
    /**
     * <p>fil240Slt_page1 をセットします。
     * @param fil240SltPage1 fil240Slt_page1
     */
    public void setFil240Slt_page1(int fil240SltPage1) {
        fil240Slt_page1__ = fil240SltPage1;
    }
    /**
     * <p>fil240Slt_page2 を取得します。
     * @return fil240Slt_page2
     */
    public int getFil240Slt_page2() {
        return fil240Slt_page2__;
    }
    /**
     * <p>fil240Slt_page2 をセットします。
     * @param fil240SltPage2 fil240Slt_page2
     */
    public void setFil240Slt_page2(int fil240SltPage2) {
        fil240Slt_page2__ = fil240SltPage2;
    }
    /**
     * <p>fil240PageLabel を取得します。
     * @return fil240PageLabel
     */
    public ArrayList<LabelValueBean> getFil240PageLabel() {
        return fil240PageLabel__;
    }
    /**
     * <p>fil240PageLabel をセットします。
     * @param fil240PageLabel fil240PageLabel
     */
    public void setFil240PageLabel(ArrayList<LabelValueBean> fil240PageLabel) {
        fil240PageLabel__ = fil240PageLabel;
    }
}
package jp.groupsession.v2.bmk.bmk150;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.bmk.bmk010.Bmk010Form;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010InfoModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 新着ブックマーク一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk150Form extends Bmk010Form {

    /** 新着ブックマーク一覧 */
    private List<Bmk010InfoModel> bmk150NewList__;
    /** ページ1 */
    private int bmk150Slt_page1__;
    /** ページ2 */
    private int bmk150Slt_page2__;
    /** ページラベル */
    private ArrayList<LabelValueBean> bmk150PageLabel__;
    /** 遷移元 */
    private String bmk070ReturnPage__;

    /**
     * <p>bmk070ReturnPage を取得します。
     * @return bmk070ReturnPage
     */
    public String getBmk070ReturnPage() {
        return bmk070ReturnPage__;
    }
    /**
     * <p>bmk070ReturnPage をセットします。
     * @param bmk070ReturnPage bmk070ReturnPage
     */
    public void setBmk070ReturnPage(String bmk070ReturnPage) {
        bmk070ReturnPage__ = bmk070ReturnPage;
    }
    /**
     * <p>bmk150NewList を取得します。
     * @return bmk150NewList
     */
    public List<Bmk010InfoModel> getBmk150NewList() {
        return bmk150NewList__;
    }
    /**
     * <p>bmk150NewList をセットします。
     * @param bmk150NewList bmk150NewList
     */
    public void setBmk150NewList(List<Bmk010InfoModel> bmk150NewList) {
        bmk150NewList__ = bmk150NewList;
    }
    /**
     * <p>bmk150PageLabel を取得します。
     * @return bmk150PageLabel
     */
    public ArrayList<LabelValueBean> getBmk150PageLabel() {
        return bmk150PageLabel__;
    }
    /**
     * <p>bmk150PageLabel をセットします。
     * @param bmk150PageLabel bmk150PageLabel
     */
    public void setBmk150PageLabel(ArrayList<LabelValueBean> bmk150PageLabel) {
        bmk150PageLabel__ = bmk150PageLabel;
    }
    /**
     * <p>bmk150Slt_page1 を取得します。
     * @return bmk150Slt_page1
     */
    public int getBmk150Slt_page1() {
        return bmk150Slt_page1__;
    }
    /**
     * <p>bmk150Slt_page1 をセットします。
     * @param bmk150Sltpage1 bmk150Slt_page1
     */
    public void setBmk150Slt_page1(int bmk150Sltpage1) {
        bmk150Slt_page1__ = bmk150Sltpage1;
    }
    /**
     * <p>bmk150Slt_page2 を取得します。
     * @return bmk150Slt_page2
     */
    public int getBmk150Slt_page2() {
        return bmk150Slt_page2__;
    }
    /**
     * <p>bmk150Slt_page2 をセットします。
     * @param bmk150Sltpage2 bmk150Slt_page2
     */
    public void setBmk150Slt_page2(int bmk150Sltpage2) {
        bmk150Slt_page2__ = bmk150Sltpage2;
    }
}
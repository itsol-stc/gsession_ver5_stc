package jp.groupsession.v2.tcd.tcd210;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.tcd.tcd190.Tcd190ParamModel;

/**
 * <br>[機  能] タイムカード 有休日数インポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210ParamModel extends Tcd190ParamModel {

    /** 添付用セレクトボックス */
    private String tcd210TenpuSelect__ = null;
    
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> tcd210FileLabelList__ = null;

    /**
     * <p>tcd210TenpuSelect を取得します。
     * @return tcd210TenpuSelect
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210Form#tcd210TenpuSelect__
     */
    public String getTcd210TenpuSelect() {
        return tcd210TenpuSelect__;
    }

    /**
     * <p>tcd210TenpuSelect をセットします。
     * @param tcd210TenpuSelect tcd210TenpuSelect
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210Form#tcd210TenpuSelect__
     */
    public void setTcd210TenpuSelect(String tcd210TenpuSelect) {
        tcd210TenpuSelect__ = tcd210TenpuSelect;
    }

    /**
     * <p>tcd210FileLabelList を取得します。
     * @return tcd210FileLabelList
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210ParamModel#tcd210FileLabelList__
     */
    public ArrayList<LabelValueBean> getTcd210FileLabelList() {
        return tcd210FileLabelList__;
    }

    /**
     * <p>tcd210FileLabelList をセットします。
     * @param tcd210FileLabelList tcd210FileLabelList
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210ParamModel#tcd210FileLabelList__
     */
    public void setTcd210FileLabelList(
            ArrayList<LabelValueBean> tcd210FileLabelList) {
        tcd210FileLabelList__ = tcd210FileLabelList;
    }
}

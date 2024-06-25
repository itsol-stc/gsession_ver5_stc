package jp.groupsession.v2.rsv.rsv270;

import java.util.ArrayList;

import jp.groupsession.v2.rsv.rsv050.Rsv050Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 グループ・施設一括出力画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv270Form extends Rsv050Form {

    /** 施設区分選択値 */
    private int rsv270SelectedSisetuKbn__ = -1;
    /** 施設区分コンボリスト */
    private ArrayList<LabelValueBean> rsv270SisetuLabelList__ = null;

    /**
     * <p>rsv270SelectedSisetuKbn を取得します。
     * @return rsv270SelectedSisetuKbn
     */
    public int getRsv270SelectedSisetuKbn() {
        return rsv270SelectedSisetuKbn__;
    }
    /**
     * <p>rsv270SelectedSisetuKbn をセットします。
     * @param rsv270SelectedSisetuKbn rsv270SelectedSisetuKbn
     */
    public void setRsv270SelectedSisetuKbn(int rsv270SelectedSisetuKbn) {
        rsv270SelectedSisetuKbn__ = rsv270SelectedSisetuKbn;
    }
    /**
     * <p>rsv270SisetuLabelList を取得します。
     * @return rsv270SisetuLabelList
     */
    public ArrayList<LabelValueBean> getRsv270SisetuLabelList() {
        return rsv270SisetuLabelList__;
    }
    /**
     * <p>rsv270SisetuLabelList をセットします。
     * @param rsv270SisetuLabelList rsv270SisetuLabelList
     */
    public void setRsv270SisetuLabelList(
            ArrayList<LabelValueBean> rsv270SisetuLabelList) {
        rsv270SisetuLabelList__ = rsv270SisetuLabelList;
    }

}
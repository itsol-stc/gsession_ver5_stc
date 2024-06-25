package jp.groupsession.v2.cir.cir050;

import java.util.List;
import jp.groupsession.v2.cir.cir010.Cir010Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 回覧板 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir050Form extends Cir010Form {

    //入力項目
    /** 表示件数 */
    private int cir050ViewCnt__ = 10;
    /** 自動リロード時間コンボ */
    private List < LabelValueBean > cir050TimeLabelList__ = null;
    /** 自動リロード時間の選択値 */
    private String cir050ReloadTime__ = null;

    //表示項目
    /** 表示件数コンボ */
    private List < LabelValueBean > cir050DspCntList__ = null;

    /**
     * @return cir050DspCntList を戻します。
     */
    public List < LabelValueBean > getCir050DspCntList() {
        return cir050DspCntList__;
    }

    /**
     * @param cir050DspCntList 設定する cir050DspCntList。
     */
    public void setCir050DspCntList(List < LabelValueBean > cir050DspCntList) {
        cir050DspCntList__ = cir050DspCntList;
    }

    /**
     * @return cir050ViewCnt を戻します。
     */
    public int getCir050ViewCnt() {
        return cir050ViewCnt__;
    }

    /**
     * @param cir050ViewCnt 設定する cir050ViewCnt。
     */
    public void setCir050ViewCnt(int cir050ViewCnt) {
        cir050ViewCnt__ = cir050ViewCnt;
    }

    /**
     * <p>cir050ReloadTime をセットします。
     * @param cir050ReloadTime cir050ReloadTime
     */
    public void setCir050ReloadTime(String cir050ReloadTime) {
        cir050ReloadTime__ = cir050ReloadTime;
    }

    /**
     * <p>cir050TimeLabelList を取得します。
     * @return cir050TimeLabelList
     */
    public List<LabelValueBean> getCir050TimeLabelList() {
        return cir050TimeLabelList__;
    }

    /**
     * <p>cir050TimeLabelList をセットします。
     * @param cir050TimeLabelList cir050TimeLabelList
     */
    public void setCir050TimeLabelList(List<LabelValueBean> cir050TimeLabelList) {
        cir050TimeLabelList__ = cir050TimeLabelList;
    }

    /**
     * <p>cir050ReloadTime を取得します。
     * @return cir050ReloadTime
     */
    public String getCir050ReloadTime() {
        return cir050ReloadTime__;
    }

}

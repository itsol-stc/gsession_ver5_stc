package jp.groupsession.v2.cir.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ラベル情報を格納したModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class LabelDataModel extends AbstractModel {

    /** ラベルSID */
    private int labelSid__ = -1;
    /** ラベル名 */
    private String labelName__ = null;
    /** 未読件数 */
    private int midokuCount__ = -1;

    /**
     * <p>labelSid を取得します。
     * @return labelSid
     * @see jp.groupsession.v2.cir.model.LabelDataModel#labelSid__
     */
    public int getLabelSid() {
        return labelSid__;
    }
    /**
     * <p>labelSid をセットします。
     * @param labelSid labelSid
     * @see jp.groupsession.v2.cir.model.LabelDataModel#labelSid__
     */
    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }
    /**
     * <p>labelName を取得します。
     * @return labelName
     * @see jp.groupsession.v2.cir.model.LabelDataModel#labelName__
     */
    public String getLabelName() {
        return labelName__;
    }
    /**
     * <p>labelName をセットします。
     * @param labelName labelName
     * @see jp.groupsession.v2.cir.model.LabelDataModel#labelName__
     */
    public void setLabelName(String labelName) {
        labelName__ = labelName;
    }
    /**
     * <p>midokuCount を取得します。
     * @return midokuCount
     * @see jp.groupsession.v2.cir.model.LabelDataModel#midokuCount__
     */
    public int getMidokuCount() {
        return midokuCount__;
    }
    /**
     * <p>midokuCount をセットします。
     * @param midokuCount midokuCount
     * @see jp.groupsession.v2.cir.model.LabelDataModel#midokuCount__
     */
    public void setMidokuCount(int midokuCount) {
        midokuCount__ = midokuCount;
    }

}

package jp.groupsession.v2.bmk.bmk040;

import java.util.List;

import jp.groupsession.v2.bmk.bmk010.model.Bmk010LabelModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;


/**
 * <br>[機  能] ラベル選択画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk040ParamModel extends AbstractParamModel {

    /** 呼び出し元画面のラベルパラメータ名 */
    private String bmk040parentLabelName__ = null;
    /** 呼び出し元画面のブックマーク区分 */
    private int bmk040mode__ = 0;
    /** 呼び出し元画面のグループSID */
    private int bmk040groupSid__ = -1;

    /** 選択ラベル */
    private String[] bmk040selectLabel__ = null;

    /** ラベル情報一覧 */
    public List<Bmk010LabelModel> labelList__ = null;

    /**
     * <p>bmk040selectLabel を取得します。
     * @return bmk040selectLabel
     */
    public String[] getBmk040selectLabel() {
        return bmk040selectLabel__;
    }
    /**
     * <p>bmk040selectLabel をセットします。
     * @param bmk040selectLabel bmk040selectLabel
     */
    public void setBmk040selectLabel(String[] bmk040selectLabel) {
        bmk040selectLabel__ = bmk040selectLabel;
    }
    /**
     * <p>labelList を取得します。
     * @return labelList
     */
    public List<Bmk010LabelModel> getLabelList() {
        return labelList__;
    }
    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     */
    public void setLabelList(List<Bmk010LabelModel> labelList) {
        labelList__ = labelList;
    }
    /**
     * <p>bmk040parentLabelName を取得します。
     * @return bmk040parentLabelName
     */
    public String getBmk040parentLabelName() {
        return bmk040parentLabelName__;
    }
    /**
     * <p>bmk040parentLabelName をセットします。
     * @param bmk040parentLabelName bmk040parentLabelName
     */
    public void setBmk040parentLabelName(String bmk040parentLabelName) {
        bmk040parentLabelName__ = bmk040parentLabelName;
    }
    /**
     * <p>bmk040groupSid を取得します。
     * @return bmk040groupSid
     */
    public int getBmk040groupSid() {
        return bmk040groupSid__;
    }
    /**
     * <p>bmk040groupSid をセットします。
     * @param bmk040groupSid bmk040groupSid
     */
    public void setBmk040groupSid(int bmk040groupSid) {
        bmk040groupSid__ = bmk040groupSid;
    }
    /**
     * <p>bmk040mode を取得します。
     * @return bmk040mode
     */
    public int getBmk040mode() {
        return bmk040mode__;
    }
    /**
     * <p>bmk040mode をセットします。
     * @param bmk040mode bmk040mode
     */
    public void setBmk040mode(int bmk040mode) {
        bmk040mode__ = bmk040mode;
    }
}
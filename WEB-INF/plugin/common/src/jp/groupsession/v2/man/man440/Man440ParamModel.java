package jp.groupsession.v2.man.man440;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 *
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man440ParamModel extends AbstractParamModel {
    /** 初回判定 */
    private int man440InitFlg__ = 0;
    /** 選択グループSID */
    private int man440GrpSid__ = -1;
    /** グループラベル */
    private ArrayList<LabelValueBean> grpLabelList__ = null;

    /**
     * <p>man440InitFlg を取得します。
     * @return man440InitFlg
     */
    public int getMan440InitFlg() {
        return man440InitFlg__;
    }

    /**
     * <p>man440InitFlg__ をセットします。
     * @param man440InitFlg man440InitFlg
     */
    public void setMan440InitFlg(int man440InitFlg) {
        man440InitFlg__ = man440InitFlg;
    }

    /**
     * <p>man440GrpSid を取得します。
     * @return man440GrpSid
     */
    public int getMan440GrpSid() {
        return man440GrpSid__;
    }

    /**
     * <p>man440GrpSid__ をセットします。
     * @param man440GrpSid man440GrpSid
     */
    public void setMan440GrpSid(int man440GrpSid) {
        man440GrpSid__ = man440GrpSid;
    }

    /**
     * <p>grpLabelList を取得します。
     * @return grpLabelList
     */
    public ArrayList<LabelValueBean> getGrpLabelList() {
        return grpLabelList__;
    }
    /**
     * <p>grpLabelList をセットします。
     * @param grpLabelList grpLabelList
     */
    public void setGrpLabelList(ArrayList<LabelValueBean> grpLabelList) {
        grpLabelList__ = grpLabelList;
    }
}

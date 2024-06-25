package jp.groupsession.v2.bmk.ptl010;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010BodyModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;


/**
 * <p>ポータル グループブックマーク表示用のフォーム
 * @author JTS
 */
public class BmkPtl010ParamModel extends AbstractParamModel {
    /** グループブックマーク設定 0=公開 1=所属グループのみ公開 **/
    private int dspFlg__ = -1;
    /** ブックマーク表示グループSID */
    private int bmkGrpSid__;
    /** グループブックマーク一覧 */
    private List<Bmk010BodyModel> bmkPtl010List__ = null;
    /** ブックマークトップ画面URL */
    private String bmkTopUrl__;
    /** グループ名 */
    private String groupName__;
    /** ページ */
    private int bmkptl010page__ = 0;
    /** ポートレットitemId */
    private String bmkptl010ItemId__ = "";
    /** ページコンボ */
    private ArrayList<LabelValueBean> bmkptl010pageCmb__ = null;
    /**
     * @return dspFlg
     */
    public int getDspFlg() {
        return dspFlg__;
    }
    /**
     * @param dspFlg セットする dspFlg
     */
    public void setDspFlg(int dspFlg) {
        dspFlg__ = dspFlg;
    }
    /**
     * @return bmkGrpSid
     */
    public int getBmkGrpSid() {
        return bmkGrpSid__;
    }
    /**
     * @param bmkGrpSid セットする bmkGrpSid
     */
    public void setBmkGrpSid(int bmkGrpSid) {
        bmkGrpSid__ = bmkGrpSid;
    }
    /**
     * @return bmkTopUrl
     */
    public String getBmkTopUrl() {
        return bmkTopUrl__;
    }
    /**
     * @param bmkTopUrl セットする bmkTopUrl
     */
    public void setBmkTopUrl(String bmkTopUrl) {
        bmkTopUrl__ = bmkTopUrl;
    }
    /**
     * @return groupName
     */
    public String getGroupName() {
        return groupName__;
    }
    /**
     * @param groupName セットする groupName
     */
    public void setGroupName(String groupName) {
        groupName__ = groupName;
    }
    /**
     * @return bmkPtl010List
     */
    public List<Bmk010BodyModel> getBmkPtl010List() {
        return bmkPtl010List__;
    }
    /**
     * @param bmkPtl010List セットする bmkPtl010List
     */
    public void setBmkPtl010List(List<Bmk010BodyModel> bmkPtl010List) {
        bmkPtl010List__ = bmkPtl010List;
    }
    /**
     * @return bmkptl010page
     */
    public int getBmkptl010page() {
        return bmkptl010page__;
    }
    /**
     * @param bmkptl010page セットする bmkptl010page
     */
    public void setBmkptl010page(int bmkptl010page) {
        bmkptl010page__ = bmkptl010page;
    }
    /**
     * @return bmkptl010ItemId
     */
    public String getBmkptl010ItemId() {
        return bmkptl010ItemId__;
    }
    /**
     * @param bmkptl010ItemId セットする bmkptl010ItemId
     */
    public void setBmkptl010ItemId(String bmkptl010ItemId) {
        bmkptl010ItemId__ = bmkptl010ItemId;
    }
    /**
     * @return bmkptl010pageCmb
     */
    public ArrayList<LabelValueBean> getBmkptl010pageCmb() {
        return bmkptl010pageCmb__;
    }
    /**
     * @param bmkptl010pageCmb セットする bmkptl010pageCmb
     */
    public void setBmkptl010pageCmb(ArrayList<LabelValueBean> bmkptl010pageCmb) {
        bmkptl010pageCmb__ = bmkptl010pageCmb;
    }
}

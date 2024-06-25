package jp.groupsession.v2.ip.model;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] IP管理 ネットワーク管理者情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */

public class IpkGrpModel extends AbstractModel {

    /** ネットワーク管理者ユーザ一覧 */
    private ArrayList<UsrLabelValueBean> leftUserList__ = null;
    /** 追加用ユーザ一覧 */
    private List<UsrLabelValueBean> rightUserList__ = null;
    /** グループ一覧 */
    private List<LabelValueBean> groupList__ = null;
    /** 選択ているグループ */
    private String groupSelect__ = "1";
    /** ネットワーク管理者SID */
    private String[] adminSidList__ = null;
    /**
     * <p>adminSidList を取得します。
     * @return adminSidList
     */
    public String[] getAdminSidList() {
        return adminSidList__;
    }
    /**
     * <p>adminSidList をセットします。
     * @param adminSidList adminSidList
     */
    public void setAdminSidList(String[] adminSidList) {
        adminSidList__ = adminSidList;
    }
    /**
     * <p>groupList を取得します。
     * @return groupList
     */
    public List<LabelValueBean> getGroupList() {
        return groupList__;
    }
    /**
     * <p>groupList をセットします。
     * @param groupList groupList
     */
    public void setGroupList(List<LabelValueBean> groupList) {
        groupList__ = groupList;
    }
    /**
     * <p>groupSelect を取得します。
     * @return groupSelect
     */
    public String getGroupSelect() {
        return groupSelect__;
    }
    /**
     * <p>groupSelect をセットします。
     * @param groupSelect groupSelect
     */
    public void setGroupSelect(String groupSelect) {
        groupSelect__ = groupSelect;
    }
    /**
     * <p>leftUserList を取得します。
     * @return leftUserList
     */
    public ArrayList<UsrLabelValueBean> getLeftUserList() {
        return leftUserList__;
    }
    /**
     * <p>leftUserList をセットします。
     * @param leftUserList leftUserList
     */
    public void setLeftUserList(ArrayList<UsrLabelValueBean> leftUserList) {
        leftUserList__ = leftUserList;
    }
    /**
     * <p>rightUserList を取得します。
     * @return rightUserList
     */
    public List<UsrLabelValueBean> getRightUserList() {
        return rightUserList__;
    }
    /**
     * <p>rightUserList をセットします。
     * @param rightUserList rightUserList
     */
    public void setRightUserList(List<UsrLabelValueBean> rightUserList) {
        rightUserList__ = rightUserList;
    }
}
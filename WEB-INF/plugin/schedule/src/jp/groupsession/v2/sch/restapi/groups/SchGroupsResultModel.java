package jp.groupsession.v2.sch.restapi.groups;

public class SchGroupsResultModel {
    /**グループ区分                                       */
    private String groupType__;
    /**グループSID                                       */
    private int groupSid__;
    /**グループID                                        */
    private String groupId__;
    /**グループ名                                         */
    private String groupName__;
    /**デフォルトグループフラグ                                         1：デフォルトグループ 0: その他 */
    private int defaultGrpFlg__;
    /**アクセス可否                                       0::閲覧・登録不可 1:閲覧・登録可 2:閲覧のみ可 */
    private int permissionType__;
    /**階層レベル                                         */
    private int classLevelNum__;
    /**
     * <p>groupType を取得します。
     * @return groupType
     * @see SchGroupsResultModel#groupType__
     */
    public String getGroupType() {
        return groupType__;
    }
    /**
     * <p>groupType をセットします。
     * @param groupType groupType
     * @see SchGroupsResultModel#groupType__
     */
    public void setGroupType(String groupType) {
        groupType__ = groupType;
    }
    /**
     * <p>groupSid を取得します。
     * @return groupSid
     * @see SchGroupsResultModel#groupSid__
     */
    public int getGroupSid() {
        return groupSid__;
    }
    /**
     * <p>groupSid をセットします。
     * @param groupSid groupSid
     * @see SchGroupsResultModel#groupSid__
     */
    public void setGroupSid(int groupSid) {
        groupSid__ = groupSid;
    }
    /**
     * <p>groupId を取得します。
     * @return groupId
     * @see SchGroupsResultModel#groupId__
     */
    public String getGroupId() {
        return groupId__;
    }
    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     * @see SchGroupsResultModel#groupId__
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
    /**
     * <p>groupName を取得します。
     * @return groupName
     * @see SchGroupsResultModel#groupName__
     */
    public String getGroupName() {
        return groupName__;
    }
    /**
     * <p>groupName をセットします。
     * @param groupName groupName
     * @see SchGroupsResultModel#groupName__
     */
    public void setGroupName(String groupName) {
        groupName__ = groupName;
    }
    /**
     * <p>defaultGrpFlg を取得します。
     * @return defaultGrpFlg
     * @see SchGroupsResultModel#defaultGrpFlg__
     */
    public int getDefaultGrpFlg() {
        return defaultGrpFlg__;
    }
    /**
     * <p>defaultGrpFlg をセットします。
     * @param defaultGrpFlg defaultGrpFlg
     * @see SchGroupsResultModel#defaultGrpFlg__
     */
    public void setDefaultGrpFlg(int defaultGrpFlg) {
        defaultGrpFlg__ = defaultGrpFlg;
    }
    /**
     * <p>permissionType を取得します。
     * @return permissionType
     * @see SchGroupsResultModel#permissionType__
     */
    public int getPermissionType() {
        return permissionType__;
    }
    /**
     * <p>permissionType をセットします。
     * @param permissionType permissionType
     * @see SchGroupsResultModel#permissionType__
     */
    public void setPermissionType(int permissionType) {
        permissionType__ = permissionType;
    }
    /**
     * <p>classLevelNum を取得します。
     * @return classLevelNum
     * @see SchGroupsResultModel#classLevelNum__
     */
    public int getClassLevelNum() {
        return classLevelNum__;
    }
    /**
     * <p>classLevelNum をセットします。
     * @param classLevelNum classLevelNum
     * @see SchGroupsResultModel#classLevelNum__
     */
    public void setClassLevelNum(int classLevelNum) {
        classLevelNum__ = classLevelNum;
    }

}

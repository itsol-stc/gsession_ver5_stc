package jp.groupsession.v2.rsv.restapi.facility_groups;
/**
 *
 * <br>[機  能] 施設グループ情報API リザルトモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvFacilityGroupsGetResultModel {

    /** 施設グループSID */
    private int sid__ = -1;
    /** 施設グループID */
    private String id__ = "";
    /** 施設グループ名 */
    private String name__ = "";
    /** 施設グループ区分 */
    private int type__ = -1;
    /** 施設区分名 */
    private String typeName__ = "";
    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.RsvFacilityGroupsGetResultModel#sid__
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.RsvFacilityGroupsGetResultModel#sid__
     */
    public void setSid(int sid) {
        sid__ = sid;
    }
    /**
     * <p>id を取得します。
     * @return id
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.RsvFacilityGroupsGetResultModel#id__
     */
    public String getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.RsvFacilityGroupsGetResultModel#id__
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.RsvFacilityGroupsGetResultModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.RsvFacilityGroupsGetResultModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>type を取得します。
     * @return type
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.RsvFacilityGroupsGetResultModel#type__
     */
    public int getType() {
        return type__;
    }
    /**
     * <p>type をセットします。
     * @param type type
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.RsvFacilityGroupsGetResultModel#type__
     */
    public void setType(int type) {
        type__ = type;
    }
    /**
     * <p>typeName を取得します。
     * @return typeName
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.
     *       RsvFacilityGroupsGetResultModel#typeName__
     */
    public String getTypeName() {
        return typeName__;
    }
    /**
     * <p>typeName をセットします。
     * @param typeName typeName
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.
     *       RsvFacilityGroupsGetResultModel#typeName__
     */
    public void setTypeName(String typeName) {
        typeName__ = typeName;
    }
}

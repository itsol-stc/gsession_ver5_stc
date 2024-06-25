package jp.groupsession.v2.sch.restapi.groups;
/**
 *
 * <br>[機  能] 列挙型 グループ区分
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumGroupType {
    /** グループ*/
    GROUP,
    /** マイグループ*/
    MYGROUP,
    /** スケジュールリスト*/
    SCHEDULELIST;
    /** 文字列値 グループ*/
    public static final String GROUP_VALUE_STRING = "group";
    /** 文字列値 マイグループ*/
    public static final String MYGROUP_VALUE_STRING = "mygroup";
    /** 文字列値 スケジュールリスト*/
    public static final String SCHEDULELIST_VALUE_STRING = "schedulelist";
    /** 値 */
    private String value__ = null;

    EnumGroupType() {
        try {
            value__ = (String) getClass()
                        .getField(name() + "_VALUE_STRING")
                        .get(this);
        } catch (IllegalArgumentException | IllegalAccessException
                | NoSuchFieldException | SecurityException e) {
        }

    }

    public String getValue() {
        return value__;
    }



}

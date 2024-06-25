package jp.groupsession.v2.sch.restapi.entities;

import jp.co.sjts.util.IEnumMsgkeyValue;

/**
 * <br>[機  能] 列挙型 選択タイプ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumScheduleSelectType implements IEnumMsgkeyValue {
    /** 0: ユーザスケジュール */
    @MsgkeyValue(msgKey = "", value = 0)
    USER,
    /** 1: ユーザスケジュール
       + 所属グループスケジュール */
    @MsgkeyValue(msgKey = "", value = 1)
    USER_WITH_BELONGGROUP,
    /** 2: グループスケジュール */
    @MsgkeyValue(msgKey = "", value = 2)
    GROUP,
    /** 3: グループスケジュール
       + グループメンバースケジュール */
    @MsgkeyValue(msgKey = "", value = 3)
    GROUP_WITH_MENBER,
    /** 4: マイグループメンバースケジュール */
    @MsgkeyValue(msgKey = "", value = 4)
    MYGROUPMEMBER,
    /** 5: スケジュールリストスケジュール */
    @MsgkeyValue(msgKey = "", value = 5)
    SCHEDULELIST;
    @Override
    public int getValue() {
        return IEnumMsgkeyValue.getValue(this);
    }

    @Override
    public String getMsgKey() {
        return IEnumMsgkeyValue.getMsgKey(this);
    }

}

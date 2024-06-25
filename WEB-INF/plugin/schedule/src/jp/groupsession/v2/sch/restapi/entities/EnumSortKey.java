package jp.groupsession.v2.sch.restapi.entities;

import jp.co.sjts.util.IEnumMsgkeyValue;
/**
 *
 * <br>[機  能] ソートキー列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumSortKey implements IEnumMsgkeyValue {
    /** 1:名前 */
    @MsgkeyValue(msgKey = "", value = 1)
    NAME,
    /** 2:開始日時 */
    @MsgkeyValue(msgKey = "", value = 2)
    DATEFROM,
    /** 3:終了日時 */
    @MsgkeyValue(msgKey = "", value = 3)
    DATETO,
    /** 4:タイトル/内容 */
    @MsgkeyValue(msgKey = "", value = 4)
    TITLE;
    @Override
    public int getValue() {
        return IEnumMsgkeyValue.getValue(this);
    }

    @Override
    public String getMsgKey() {
        return IEnumMsgkeyValue.getMsgKey(this);
    }

}

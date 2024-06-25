package jp.groupsession.v2.sch.restapi.entities;

import jp.co.sjts.util.IEnumMsgkeyValue;

/**
 * <br>[機  能] 列挙型 キーワード検索対象
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumKeywordTarget implements IEnumMsgkeyValue {
    /** 1:タイトル  */
    @MsgkeyValue(msgKey = "", value = 1)
    TITLE_ONLY,
    /** 2:内容  */
    @MsgkeyValue(msgKey = "", value = 2)
    VALUE_ONLY,
    /** 3:タイトル＋内容 */
    @MsgkeyValue(msgKey = "", value = 3)
    TITLE_WITH_VALUE;
    @Override
    public int getValue() {
        return IEnumMsgkeyValue.getValue(this);
    }

    @Override
    public String getMsgKey() {
        return IEnumMsgkeyValue.getMsgKey(this);
    }

}

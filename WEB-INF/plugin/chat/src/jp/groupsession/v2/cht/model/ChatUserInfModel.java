package jp.groupsession.v2.cht.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <p>CHT_GROUP_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChatUserInfModel extends CmnUsrmInfModel {

    /** チャットユーザカウント*/
    private int chtUserCount__;

    /**
     * <p>Default Constructor
     */
    public ChatUserInfModel() {
    }

    /**
     * <p>chtuserCount を取得します。
     * @return chtuserCount
     * @see jp.groupsession.v2.cht.model.ChatUserInfModel#chtUserCount__
     */
    public int getChtUserCount() {
        return chtUserCount__;
    }

    /**
     * <p>chtuserCount をセットします。
     * @param chtuserCount chtuserCount
     * @see jp.groupsession.v2.cht.model.ChatUserInfModel#chtUserCount__
     */
    public void setChtUserCount(int chtuserCount) {
        chtUserCount__ = chtuserCount;
    }


}
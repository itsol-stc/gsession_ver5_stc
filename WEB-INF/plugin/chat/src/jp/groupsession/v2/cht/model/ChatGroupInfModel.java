package jp.groupsession.v2.cht.model;

/**
 * <p>CHT_GROUP_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChatGroupInfModel extends ChtGroupInfModel {

    /** チャットグループカウント*/
    private int chtGroupCount__;

    /**
     * <p>Default Constructor
     */
    public ChatGroupInfModel() {
    }

    /**
     * <p>chtGroupCount を取得します。
     * @return chtGroupCount
     * @see jp.groupsession.v2.cht.model.ChatGroupInfModel#chtGroupCount__
     */
    public int getChtGroupCount() {
        return chtGroupCount__;
    }

    /**
     * <p>chtGroupCount をセットします。
     * @param chtGroupCount chtGroupCount
     * @see jp.groupsession.v2.cht.model.ChatGroupInfModel#chtGroupCount__
     */
    public void setChtGroupCount(int chtGroupCount) {
        chtGroupCount__ = chtGroupCount;
    }
}
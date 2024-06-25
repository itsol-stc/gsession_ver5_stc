package jp.groupsession.v2.cht.model;

/**
 * チャットグループのデータ
 * */
public class ChtGroupModel {

    /** チャットグループSID */
    private String chtGrpSid__ = null;
    /** グループ名 */
    private String chtGrpName__ = null;

    /**
     * <p>chtGrpSid を取得します。
     * @return chtGrpSid
     * @see jp.groupsession.v2.cht.model.ChtGroupModel#chtGrpSid__
     */
    public String getChtGrpSid() {
        return chtGrpSid__;
    }

    /**
     * <p>chtGrpSid をセットします。
     * @param chtGrpSid chtGrpSid
     * @see jp.groupsession.v2.cht.model.ChtGroupModel#chtGrpSid__
     */
    public void setChtGrpSid(String chtGrpSid) {
        chtGrpSid__ = chtGrpSid;
    }

    /**
     * <p>chtGrpName を取得します。
     * @return chtGrpName
     * @see jp.groupsession.v2.cht.model.ChtGroupModel#chtGrpName__
     */
    public String getChtGrpName() {
        return chtGrpName__;
    }

    /**
     * <p>chtGrpName をセットします。
     * @param chtGrpName chtGrpName
     * @see jp.groupsession.v2.cht.model.ChtGroupModel#chtGrpName__
     */
    public void setChtGrpName(String chtGrpName) {
        chtGrpName__ = chtGrpName;
    }

}

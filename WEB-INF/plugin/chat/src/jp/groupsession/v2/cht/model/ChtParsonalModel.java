package jp.groupsession.v2.cht.model;

import java.util.List;

/**
 * チャット相手のデータ
 * */
public class ChtParsonalModel {

    /** ユーザSID */
    private int usrSid__ = -1;
    /** ユーザ氏名 */
    private String usrName__ = null;
    /** 所属チャットグループSID (グループSIDは"G"から始まるためString型で保持) */
    private List<String> chtGroupSid__ = null;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>usrName を取得します。
     * @return usrName
     */
    public String getUsrName() {
        return usrName__;
    }
    /**
     * <p>usrName をセットします。
     * @param usrName usrName
     */
    public void setUsrName(String usrName) {
        usrName__ = usrName;
    }
    /**
     * <p>chtGroupSid を取得します。
     * @return chtGroupSid
     */
    public List<String> getChtGroupSid() {
        return chtGroupSid__;
    }
    /**
     * <p>chtGroupSid をセットします。
     * @param chtGroupSid chtGroupSid
     */
    public void setChtGroupSid(List<String> chtGroupSid) {
        chtGroupSid__ = chtGroupSid;
    }

}

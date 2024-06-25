package jp.groupsession.v2.sch.restapi.groups.member;
/**
 *
 * <br>[機  能] レスポンス用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchGroupMemberResultModel {
    /** メンバー区分 */
    private int memberFlg__;
    /** ユーザID or グループID */
    private String id__;
    /** ユーザSID or グループSID */
    private Integer sid__;
    /** 名称               */
    private String name__;
    /** 姓                 */
    private String seiText__;
    /** 名                 */
    private String meiText__;
    /** 姓（カナ）         */
    private String seiKanaText__;
    /** 名（カナ）         */
    private String meiKanaText__;
    /** ログイン停止フラグ */
    private Integer loginStopFlg__;

    /**
     * <p>memberFlg を取得します。
     * @return memberFlg
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#memberFlg__
     */
    public int getMemberFlg() {
        return memberFlg__;
    }
    /**
     * <p>memberFlg をセットします。
     * @param memberFlg memberFlg
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#memberFlg__
     */
    public void setMemberFlg(int memberFlg) {
        memberFlg__ = memberFlg;
    }
    /**
     * <p>id を取得します。
     * @return id
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#id__
     */
    public String getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#id__
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#sid__
     */
    public Integer getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#sid__
     */
    public void setSid(Integer sid) {
        sid__ = sid;
    }

    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>seiText を取得します。
     * @return seiText
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#seiText__
     */
    public String getSeiText() {
        return seiText__;
    }
    /**
     * <p>seiText をセットします。
     * @param seiText seiText
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#seiText__
     */
    public void setSeiText(String seiText) {
        seiText__ = seiText;
    }
    /**
     * <p>meiText を取得します。
     * @return meiText
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#meiText__
     */
    public String getMeiText() {
        return meiText__;
    }
    /**
     * <p>meiText をセットします。
     * @param meiText meiText
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#meiText__
     */
    public void setMeiText(String meiText) {
        meiText__ = meiText;
    }
    /**
     * <p>seiKanaText を取得します。
     * @return seiKanaText
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#seiKanaText__
     */
    public String getSeiKanaText() {
        return seiKanaText__;
    }
    /**
     * <p>seiKanaText をセットします。
     * @param seiKanaText seiKanaText
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#seiKanaText__
     */
    public void setSeiKanaText(String seiKanaText) {
        seiKanaText__ = seiKanaText;
    }
    /**
     * <p>meiKanaText を取得します。
     * @return meiKanaText
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#meiKanaText__
     */
    public String getMeiKanaText() {
        return meiKanaText__;
    }
    /**
     * <p>meiKanaText をセットします。
     * @param meiKanaText meiKanaText
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#meiKanaText__
     */
    public void setMeiKanaText(String meiKanaText) {
        meiKanaText__ = meiKanaText;
    }
    /**
     * <p>loginStopFlg を取得します。
     * @return loginStopFlg
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#loginStopFlg__
     */
    public Integer getLoginStopFlg() {
        return loginStopFlg__;
    }
    /**
     * <p>loginStopFlg をセットします。
     * @param loginStopFlg loginStopFlg
     * @see jp.groupsession.v2.sch.restapi.groups.member.SchGroupMemberResultModel#loginStopFlg__
     */
    public void setLoginStopFlg(Integer loginStopFlg) {
        loginStopFlg__ = loginStopFlg;
    }



}

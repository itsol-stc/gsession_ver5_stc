package jp.groupsession.v2.prj.prj150;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトメンバー設定画面のメンバー情報(1行ごと)を格納するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj150MemberForm extends AbstractGsForm {

    /** 行番号 */
    private int rowNumber__;
    /** ユーザSID */
    private int usrSid__;
    /** ユーザ氏名 */
    private String usrName__;
    /** プロジェクトメンバーID */
    private String projectMemberKey__;
    /** プロジェクトメンバーID_save */
    private String projectMemberKeySv__;
    /** ソート順 */
    private String sort__;
    /** ログイン有効フラグ*/
    private int usrUkoFlg__;

    /**
     * <p>projectMemberKeySv を取得します。
     * @return projectMemberKeySv
     */
    public String getProjectMemberKeySv() {
        return projectMemberKeySv__;
    }
    /**
     * <p>projectMemberKeySv をセットします。
     * @param projectMemberKeySv projectMemberKeySv
     */
    public void setProjectMemberKeySv(String projectMemberKeySv) {
        projectMemberKeySv__ = projectMemberKeySv;
    }
    /**
     * <p>projectMemberKey を取得します。
     * @return projectMemberKey
     */
    public String getProjectMemberKey() {
        return projectMemberKey__;
    }
    /**
     * <p>projectMemberKey をセットします。
     * @param projectMemberKey projectMemberKey
     */
    public void setProjectMemberKey(String projectMemberKey) {
        projectMemberKey__ = projectMemberKey;
    }
    /**
     * <p>rowNumber を取得します。
     * @return rowNumber
     */
    public int getRowNumber() {
        return rowNumber__;
    }
    /**
     * <p>rowNumber をセットします。
     * @param rowNumber rowNumber
     */
    public void setRowNumber(int rowNumber) {
        rowNumber__ = rowNumber;
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
     * <p>sort を取得します。
     * @return sort
     */
    public String getSort() {
        return sort__;
    }
    /**
     * <p>sort をセットします。
     * @param sort sort
     */
    public void setSort(String sort) {
        sort__ = sort;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
}
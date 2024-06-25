package jp.groupsession.v2.sch.sch100;

import jp.groupsession.v2.sch.model.SchDataModel;


/**
 * <br>[機  能] スケジュール一覧からCSV出力する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ScheduleCsvModel extends SchDataModel {

    /** ログインID */
    private String loginId__;
    /** グループID */
    private String groupId__;
    /** 氏名 */
    private String usrName__;
    /** 登録者氏名 */
    private String addUsrName__;
    /** 更新者氏名 */
    private String edtUsrName__;
    /** 開始日付String */
    private String scdFrDateStr__;
    /** 開始時刻String */
    private String scdFrTimeStr__;
    /** 終了日付String */
    private String scdToDateStr__;
    /** 終了時刻String */
    private String scdToTimeStr__;
    /** マイグループフラグ*/
    private boolean myGrpFlg__ = false;
    /** 表示リストフラグ */
    private boolean dspListFlg__ = false;

    /**
     * @return myGrpFlg
     */
    public boolean isMyGrpFlg() {
        return myGrpFlg__;
    }
    /**
     * @param myGrpFlg 設定する myGrpFlg
     */
    public void setMyGrpFlg(boolean myGrpFlg) {
        myGrpFlg__ = myGrpFlg;
    }
    /**
     * <p>addUsrName を取得します。
     * @return addUsrName
     */
    public String getAddUsrName() {
        return addUsrName__;
    }
    /**
     * <p>addUsrName をセットします。
     * @param addUsrName addUsrName
     */
    public void setAddUsrName(String addUsrName) {
        addUsrName__ = addUsrName;
    }
    /**
     * <p>edtUsrName を取得します。
     * @return edtUsrName
     */
    public String getEdtUsrName() {
        return edtUsrName__;
    }
    /**
     * <p>edtUsrName をセットします。
     * @param edtUsrName edtUsrName
     */
    public void setEdtUsrName(String edtUsrName) {
        edtUsrName__ = edtUsrName;
    }
    /**
     * <p>scdFrDateStr を取得します。
     * @return scdFrDateStr
     */
    public String getScdFrDateStr() {
        return scdFrDateStr__;
    }
    /**
     * <p>scdFrDateStr をセットします。
     * @param scdFrDateStr scdFrDateStr
     */
    public void setScdFrDateStr(String scdFrDateStr) {
        scdFrDateStr__ = scdFrDateStr;
    }
    /**
     * <p>scdToDateStr を取得します。
     * @return scdToDateStr
     */
    public String getScdToDateStr() {
        return scdToDateStr__;
    }
    /**
     * <p>scdToDateStr をセットします。
     * @param scdToDateStr scdToDateStr
     */
    public void setScdToDateStr(String scdToDateStr) {
        scdToDateStr__ = scdToDateStr;
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
     * <p>scdFrTimeStr を取得します。
     * @return scdFrTimeStr
     */
    public String getScdFrTimeStr() {
        return scdFrTimeStr__;
    }
    /**
     * <p>scdFrTimeStr をセットします。
     * @param scdFrTimeStr scdFrTimeStr
     */
    public void setScdFrTimeStr(String scdFrTimeStr) {
        scdFrTimeStr__ = scdFrTimeStr;
    }
    /**
     * <p>scdToTimeStr を取得します。
     * @return scdToTimeStr
     */
    public String getScdToTimeStr() {
        return scdToTimeStr__;
    }
    /**
     * <p>scdToTimeStr をセットします。
     * @param scdToTimeStr scdToTimeStr
     */
    public void setScdToTimeStr(String scdToTimeStr) {
        scdToTimeStr__ = scdToTimeStr;
    }
    /**
     * <p>groupId を取得します。
     * @return groupId
     */
    public String getGroupId() {
        return groupId__;
    }
    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
    /**
     * <p>loginId を取得します。
     * @return loginId
     */
    public String getLoginId() {
        return loginId__;
    }
    /**
     * <p>loginId をセットします。
     * @param loginId loginId
     */
    public void setLoginId(String loginId) {
        loginId__ = loginId;
    }
    /**
     * <p>dspListFlg を取得します。
     * @return dspListFlg
     * @see jp.groupsession.v2.sch.sch100.ScheduleListSearchModel#dspListFlg__
     */
    public boolean isDspListFlg() {
        return dspListFlg__;
    }
    /**
     * <p>dspListFlg をセットします。
     * @param dspListFlg dspListFlg
     * @see jp.groupsession.v2.sch.sch100.ScheduleListSearchModel#dspListFlg__
     */
    public void setDspListFlg(boolean dspListFlg) {
        dspListFlg__ = dspListFlg;
    }
}
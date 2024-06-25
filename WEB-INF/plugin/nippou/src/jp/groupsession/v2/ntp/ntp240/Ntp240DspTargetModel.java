package jp.groupsession.v2.ntp.ntp240;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.model.NtpPriTargetModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;

/**
 * <br>[機  能] 日報 目標設定画面の画面表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp240DspTargetModel extends NtpTargetModel {

    /** ユーザSID */
    private int usrSid__;
    /** 年 */
    private int year__;
    /** 月 */
    private int month__;
    /** 目標リスト */
    private List<NtpPriTargetModel> ntgList__;

    /** 目標達成度ポップアップ用パラメータ */

    /** ユーザ名 */
    private String usrName__;
    /** ユーザ無効フラグ */
    private int usrUkoFlg__ = GSConst.YUKOMUKO_YUKO;
    /** 目標SID */
    private int targetSid__;
    /** 目標名 */
    private String targetName__;
    /** 目標名 */
    private double targetRatio__;
    /** 目標単位 */
    private String targetUnit__;

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
     * <p>year を取得します。
     * @return year
     */
    public int getYear() {
        return year__;
    }
    /**
     * <p>year をセットします。
     * @param year year
     */
    public void setYear(int year) {
        year__ = year;
    }
    /**
     * <p>month を取得します。
     * @return month
     */
    public int getMonth() {
        return month__;
    }
    /**
     * <p>month をセットします。
     * @param month month
     */
    public void setMonth(int month) {
        month__ = month;
    }
    /**
     * <p>ntgList を取得します。
     * @return ntgList
     */
    public List<NtpPriTargetModel> getNtgList() {
        return ntgList__;
    }
    /**
     * <p>ntgList をセットします。
     * @param ntgList ntgList
     */
    public void setNtgList(List<NtpPriTargetModel> ntgList) {
        ntgList__ = ntgList;
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
    /**
     * <p>targetName を取得します。
     * @return targetName
     */
    public String getTargetName() {
        return targetName__;
    }
    /**
     * <p>targetName をセットします。
     * @param targetName targetName
     */
    public void setTargetName(String targetName) {
        targetName__ = targetName;
    }
    /**
     * <p>targetRatio を取得します。
     * @return targetRatio
     */
    public double getTargetRatio() {
        return targetRatio__;
    }
    /**
     * <p>targetRatio をセットします。
     * @param targetRatio targetRatio
     */
    public void setTargetRatio(double targetRatio) {
        targetRatio__ = targetRatio;
    }
    /**
     * <p>targetSid を取得します。
     * @return targetSid
     */
    public int getTargetSid() {
        return targetSid__;
    }
    /**
     * <p>targetSid をセットします。
     * @param targetSid targetSid
     */
    public void setTargetSid(int targetSid) {
        targetSid__ = targetSid;
    }
    /**
     * <p>targetUnit を取得します。
     * @return targetUnit
     */
    public String getTargetUnit() {
        return targetUnit__;
    }
    /**
     * <p>targetUnit をセットします。
     * @param targetUnit targetUnit
     */
    public void setTargetUnit(String targetUnit) {
        targetUnit__ = targetUnit;
    }
}

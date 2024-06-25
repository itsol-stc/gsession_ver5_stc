package jp.groupsession.v2.ntp.ntp040;

import java.util.List;

import jp.groupsession.v2.ntp.model.NtpPriTargetModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;

/**
 * <br>[機  能] 日報 日報登録画面の表示用情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp040DspTargetModel extends NtpTargetModel {

    /** ユーザSID */
    private int usrSid__;
    /** 年 */
    private int year__;
    /** 月 */
    private int month__;
    /** テンプレートSID */
    private int nttSid__;
    /** 目標リスト */
    private List<NtpPriTargetModel> ntgList__;


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
     * <p>nttSid を取得します。
     * @return nttSid
     */
    public int getNttSid() {
        return nttSid__;
    }
    /**
     * <p>nttSid をセットします。
     * @param nttSid nttSid
     */
    public void setNttSid(int nttSid) {
        nttSid__ = nttSid;
    }
}

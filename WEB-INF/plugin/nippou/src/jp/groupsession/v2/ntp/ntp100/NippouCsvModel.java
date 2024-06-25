package jp.groupsession.v2.ntp.ntp100;

import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 日報一覧からCSV出力する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NippouCsvModel extends NtpDataModel {

    /** ユーザログインID */
    private String usrLgId__;
    /** 氏名 */
    private String usrName__;
    /** 登録者氏名 */
    private String addUsrName__;
    /** 更新者氏名 */
    private String edtUsrName__;
    /** 日報日付 */
    private String ntpDateStr__;
    /** 開始日付String */
    private String ntpFrDateStr__;
    /** 開始時刻String */
    private String ntpFrTimeStr__;
    /** 終了日付String */
    private String ntpToDateStr__;
    /** 終了時刻String */
    private String ntpToTimeStr__;
    /** 案件コード */
    private String ankenCode__;
    /** 企業コード */
    private String acoCode__;
    /** 活動分類コード */
    private String kbunruiCode__;
    /** 活動方法コード */
    private String khouhouCode__;
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
     * <p>ntpFrDateStr を取得します。
     * @return ntpFrDateStr
     */
    public String getNtpFrDateStr() {
        return ntpFrDateStr__;
    }
    /**
     * <p>ntpFrDateStr をセットします。
     * @param ntpFrDateStr ntpFrDateStr
     */
    public void setNtpFrDateStr(String ntpFrDateStr) {
        ntpFrDateStr__ = ntpFrDateStr;
    }
    /**
     * <p>ntpFrTimeStr を取得します。
     * @return ntpFrTimeStr
     */
    public String getNtpFrTimeStr() {
        return ntpFrTimeStr__;
    }
    /**
     * <p>ntpFrTimeStr をセットします。
     * @param ntpFrTimeStr ntpFrTimeStr
     */
    public void setNtpFrTimeStr(String ntpFrTimeStr) {
        ntpFrTimeStr__ = ntpFrTimeStr;
    }
    /**
     * <p>ntpToDateStr を取得します。
     * @return ntpToDateStr
     */
    public String getNtpToDateStr() {
        return ntpToDateStr__;
    }
    /**
     * <p>ntpToDateStr をセットします。
     * @param ntpToDateStr ntpToDateStr
     */
    public void setNtpToDateStr(String ntpToDateStr) {
        ntpToDateStr__ = ntpToDateStr;
    }
    /**
     * <p>ntpToTimeStr を取得します。
     * @return ntpToTimeStr
     */
    public String getNtpToTimeStr() {
        return ntpToTimeStr__;
    }
    /**
     * <p>ntpToTimeStr をセットします。
     * @param ntpToTimeStr ntpToTimeStr
     */
    public void setNtpToTimeStr(String ntpToTimeStr) {
        ntpToTimeStr__ = ntpToTimeStr;
    }
    /**
     * <p>ntpDateStr を取得します。
     * @return ntpDateStr
     */
    public String getNtpDateStr() {
        return ntpDateStr__;
    }
    /**
     * <p>ntpDateStr をセットします。
     * @param ntpDateStr ntpDateStr
     */
    public void setNtpDateStr(String ntpDateStr) {
        ntpDateStr__ = ntpDateStr;
    }
    /**
     * <p>ankenCode を取得します。
     * @return ankenCode
     */
    public String getAnkenCode() {
        return ankenCode__;
    }
    /**
     * <p>ankenCode をセットします。
     * @param ankenCode ankenCode
     */
    public void setAnkenCode(String ankenCode) {
        ankenCode__ = ankenCode;
    }
    /**
     * <p>acoCode を取得します。
     * @return acoCode
     */
    public String getAcoCode() {
        return acoCode__;
    }
    /**
     * <p>acoCode をセットします。
     * @param acoCode acoCode
     */
    public void setAcoCode(String acoCode) {
        acoCode__ = acoCode;
    }
    /**
     * <p>kbunruiCode を取得します。
     * @return kbunruiCode
     */
    public String getKbunruiCode() {
        return kbunruiCode__;
    }
    /**
     * <p>kbunruiCode をセットします。
     * @param kbunruiCode kbunruiCode
     */
    public void setKbunruiCode(String kbunruiCode) {
        kbunruiCode__ = kbunruiCode;
    }
    /**
     * <p>khouhouCode を取得します。
     * @return khouhouCode
     */
    public String getKhouhouCode() {
        return khouhouCode__;
    }
    /**
     * <p>khouhouCode をセットします。
     * @param khouhouCode khouhouCode
     */
    public void setKhouhouCode(String khouhouCode) {
        khouhouCode__ = khouhouCode;
    }
    /**
     * <p>usrLgId を取得します。
     * @return usrLgId
     */
    public String getUsrLgId() {
        return usrLgId__;
    }
    /**
     * <p>usrLgId をセットします。
     * @param usrLgId usrLgId
     */
    public void setUsrLgId(String usrLgId) {
        usrLgId__ = usrLgId;
    }
    @Override
    public void resetNoPermitAnkenView(GsMessage gsMsg) {
        super.resetNoPermitAnkenView(gsMsg);
        /** 案件コード */
        ankenCode__ = "";
        /** 企業コード */
        acoCode__ = "";
        /** 活動分類コード */
        kbunruiCode__ = "";
        /** 活動方法コード */
        khouhouCode__ = "";
    }

}
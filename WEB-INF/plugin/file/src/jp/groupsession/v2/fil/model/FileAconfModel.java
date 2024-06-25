package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.fil.GSConstFile;

/**
 * <p>FILE_ACONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileAconfModel implements Serializable {

    /** FAC_CRT_KBN mapping */
    private int facCrtKbn__;
    /** FAC_FILE_SIZE mapping */
    private int facFileSize__;
    /** FAC_SAVE_DAYS mapping */
    private int facSaveDays__;
    /** FAC_LOCK_KBN mapping */
    private int facLockKbn__;
    /** FAC_VER_KBN mapping */
    private int facVerKbn__;
    /** FAC_AUID mapping */
    private int facAuid__;
    /** FAC_ADATE mapping */
    private UDate facAdate__;
    /** FAC_EUID mapping */
    private int facEuid__;
    /** FAC_EDATE mapping */
    private UDate facEdate__;
    /** FAC_SMAIL_SEND_KBN mapping */
    private int facSmailSendKbn__;
    /** FAC_SMAIL_SEND mapping */
    private int facSmailSend__;
    /** FAC_WARN_CNT mapping */
    private int facWarnCnt__;
    /** FAC_PERSONAL_KBN mapping */
    private int facPersonalKbn__;
    /** FAC_USE_KBN mapping */
    private int facUseKbn__;
    /** FAC_PERSONAL_CAPA */
    private int facPersonalCapa__;
    /** FAC_PERSONAL_SIZE  */
    private int facPersonalSize__;
    /** FAC_PERSONAL_WARN  */
    private int facPersonalWarn__;
    /** FAC_PERSONAL_VER  */
    private int facPersonalVer__;
    /** FAC_PERSONAL_EDATE mapping */
    private UDate facPersonalEdate__;

    /**
     * <p>Default Constructor
     */
    public FileAconfModel() {
    }
    /**
     * 初期設定値を設定します
     */
    public void init() {
        UDate now = new UDate();
        facCrtKbn__ = GSConstFile.CREATE_CABINET_PERMIT_ADMIN;
        facFileSize__ = GSConstFile.FILE_SIZE_DEFAULT;
        facSaveDays__ = GSConstFile.FILE_SAVE_DAYS_NO;
        facLockKbn__ = GSConstFile.LOCK_KBN_OFF;
        facVerKbn__ = GSConstFile.VERSION_KBN_OFF;
        facAuid__ = 0;
        facAdate__ = now;
        facEuid__ = 0;
        facEdate__ = now;
        facSmailSendKbn__ = GSConstFile.FAC_SMAIL_SEND_KBN_USER;
        facSmailSend__ = GSConstFile.FAC_SMAIL_SEND_YES;
        facWarnCnt__ = 0;

        facPersonalKbn__   = GSConstFile.CABINET_PRIVATE_NOT_USE;
        facUseKbn__        = GSConstFile.CABINET_AUTH_ALL;
        facPersonalCapa__  = GSConstFile.CAPA_KBN_OFF;
        facPersonalSize__  = 0;
        facPersonalWarn__  = 0;
        facPersonalVer__   = 0;
        facPersonalEdate__ = null;
    }
    /**
     * <p>get FAC_CRT_KBN value
     * @return FAC_CRT_KBN value
     */
    public int getFacCrtKbn() {
        return facCrtKbn__;
    }

    /**
     * <p>set FAC_CRT_KBN value
     * @param facCrtKbn FAC_CRT_KBN value
     */
    public void setFacCrtKbn(int facCrtKbn) {
        facCrtKbn__ = facCrtKbn;
    }

    /**
     * <p>get FAC_FILE_SIZE value
     * @return FAC_FILE_SIZE value
     */
    public int getFacFileSize() {
        return facFileSize__;
    }

    /**
     * <p>set FAC_FILE_SIZE value
     * @param facFileSize FAC_FILE_SIZE value
     */
    public void setFacFileSize(int facFileSize) {
        facFileSize__ = facFileSize;
    }

    /**
     * <p>get FAC_SAVE_DAYS value
     * @return FAC_SAVE_DAYS value
     */
    public int getFacSaveDays() {
        return facSaveDays__;
    }

    /**
     * <p>set FAC_SAVE_DAYS value
     * @param facSaveDays FAC_SAVE_DAYS value
     */
    public void setFacSaveDays(int facSaveDays) {
        facSaveDays__ = facSaveDays;
    }

    /**
     * <p>get FAC_LOCK_KBN value
     * @return FAC_LOCK_KBN value
     */
    public int getFacLockKbn() {
        return facLockKbn__;
    }

    /**
     * <p>set FAC_LOCK_KBN value
     * @param facLockKbn FAC_LOCK_KBN value
     */
    public void setFacLockKbn(int facLockKbn) {
        facLockKbn__ = facLockKbn;
    }

    /**
     * <p>get FAC_VER_KBN value
     * @return FAC_VER_KBN value
     */
    public int getFacVerKbn() {
        return facVerKbn__;
    }

    /**
     * <p>set FAC_VER_KBN value
     * @param facVerKbn FAC_VER_KBN value
     */
    public void setFacVerKbn(int facVerKbn) {
        facVerKbn__ = facVerKbn;
    }

    /**
     * <p>get FAC_AUID value
     * @return FAC_AUID value
     */
    public int getFacAuid() {
        return facAuid__;
    }

    /**
     * <p>set FAC_AUID value
     * @param facAuid FAC_AUID value
     */
    public void setFacAuid(int facAuid) {
        facAuid__ = facAuid;
    }

    /**
     * <p>get FAC_ADATE value
     * @return FAC_ADATE value
     */
    public UDate getFacAdate() {
        return facAdate__;
    }

    /**
     * <p>set FAC_ADATE value
     * @param facAdate FAC_ADATE value
     */
    public void setFacAdate(UDate facAdate) {
        facAdate__ = facAdate;
    }

    /**
     * <p>get FAC_EUID value
     * @return FAC_EUID value
     */
    public int getFacEuid() {
        return facEuid__;
    }

    /**
     * <p>set FAC_EUID value
     * @param facEuid FAC_EUID value
     */
    public void setFacEuid(int facEuid) {
        facEuid__ = facEuid;
    }

    /**
     * <p>get FAC_EDATE value
     * @return FAC_EDATE value
     */
    public UDate getFacEdate() {
        return facEdate__;
    }

    /**
     * <p>set FAC_EDATE value
     * @param facEdate FAC_EDATE value
     */
    public void setFacEdate(UDate facEdate) {
        facEdate__ = facEdate;
    }

    /**
     * <p>get FAC_SMAIL_SEND_KBN value
     * @return FAC_SMAIL_SEND_KBN value
     */
    public int getFacSmailSendKbn() {
        return facSmailSendKbn__;
    }

    /**
     * <p>set FAC_SMAIL_SEND_KBN value
     * @param facSmailSendKbn FAC_SMAIL_SEND_KBN value
     */
    public void setFacSmailSendKbn(int facSmailSendKbn) {
        facSmailSendKbn__ = facSmailSendKbn;
    }

    /**
     * <p>get FAC_SMAIL_SEND value
     * @return FAC_SMAIL_SEND value
     */
    public int getFacSmailSend() {
        return facSmailSend__;
    }

    /**
     * <p>set FAC_SMAIL_SEND value
     * @param facSmailSend FAC_SMAIL_SEND value
     */
    public void setFacSmailSend(int facSmailSend) {
        facSmailSend__ = facSmailSend;
    }

    /**
     * <p>get FAC_WARN_CNT value
     * @return FAC_WARN_CNT value
     */
    public int getFacWarnCnt() {
        return facWarnCnt__;
    }

    /**
     * <p>set FAC_WARN_CNT value
     * @param facWarnCnt FAC_WARN_CNT value
     */
    public void setFacWarnCnt(int facWarnCnt) {
        this.facWarnCnt__ = facWarnCnt;
    }

    /**
     * <p>get FAC_PERSONAL_KBN value
     * @return FAC_PERSONAL_KBN value
     */
    public int getFacPersonalKbn() {
        return facPersonalKbn__;
    }

    /**
     * <p>set FAC_PERSONAL_KBN value
     * @param facPersonalKbn FAC_PERSONAL_KBN value
     */
    public void setFacPersonalKbn(int facPersonalKbn) {
        this.facPersonalKbn__ = facPersonalKbn;
    }

    /**
     * <p>get FAC_USE_KBN value
     * @return FAC_USE_KBN value
     */
    public int getFacUseKbn() {
        return facUseKbn__;
    }

    /**
     * <p>set FAC_USE_KBN value
     * @param facUseKbn FAC_USE_KBN value
     */
    public void setFacUseKbn(int facUseKbn) {
        this.facUseKbn__ = facUseKbn;
    }

    /**
     * <p> FAC_PERSONAL_CAPAの値を取得します。
     * @return FAC_PERSONAL_CAPAの値
     * */
    public int getFacPersonalCapa() {
        return facPersonalCapa__;
    }

    /**
     * <p> FAC_PERSONAL_CAPAに値をセットします。
     * @param facPersonalCapa FAC_PERSONAL_CAPAへセットする値
     * */
    public void setFacPersonalCapa(int facPersonalCapa) {
        facPersonalCapa__ = facPersonalCapa;
    }

    /**
     * <p> FAC_PERSONAL_SIZEの値を取得します。
     * @return FAC_PERSONAL_SIZEの値
     * */
    public int getFacPersonalSize() {
        return facPersonalSize__;
    }

    /**
     * <p> FAC_PERSONAL_SIZEに値をセットします。
     * @param facPersonalSize FAC_PERSONAL_SIZEへセットする値
     * */
    public void setFacPersonalSize(int facPersonalSize) {
        facPersonalSize__ = facPersonalSize;
    }

    /**
     * <p> FAC_PERSONAL_WARNの値を取得します。
     * @return FAC_PERSONAL_WARNの値
     * */
    public int getFacPersonalWarn() {
        return facPersonalWarn__;
    }

    /**
     * <p> FAC_PERSONAL_WARNに値をセットします。
     * @param facPersonalWarn FAC_PERSONAL_WARNの値
     * */
    public void setFacPersonalWarn(int facPersonalWarn) {
        facPersonalWarn__ = facPersonalWarn;
    }

    /**
     * <p> FAC_PERSONAL_VERの値を取得します。
     * @return FAC_PERSONAL_VERの値
     * */
    public int getFacPersonalVer() {
        return facPersonalVer__;
    }

    /**
     * <p> FAC_PERSONAL_VERに値をセットします。
     * @param facPersonalVer FAC_PERSONAL_VERの値
     * */
    public void setFacPersonalVer(int facPersonalVer) {
        facPersonalVer__ = facPersonalVer;
    }

    /**
     * <p>get FAC_PERSONAL_EDATE value
     * @return FAC_PERSONAL_EDATE value
     */
    public UDate getFacPersonalEdate() {
        return facPersonalEdate__;
    }

    /**
     * <p>set FAC_PERSONAL_EDATE value
     * @param facPersonalEdate FAC_PERSONAL_EDATE value
     */
    public void setFacPersonalEdate(UDate facPersonalEdate) {
        facPersonalEdate__ = facPersonalEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(facCrtKbn__);
        buf.append(",");
        buf.append(facFileSize__);
        buf.append(",");
        buf.append(facSaveDays__);
        buf.append(",");
        buf.append(facLockKbn__);
        buf.append(",");
        buf.append(facVerKbn__);
        buf.append(",");
        buf.append(facAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(facAdate__, ""));
        buf.append(",");
        buf.append(facEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(facEdate__, ""));
        buf.append(",");
        buf.append(facSmailSendKbn__);
        buf.append(",");
        buf.append(facSmailSend__);
        buf.append(",");
        buf.append(facPersonalCapa__);
        buf.append(",");
        buf.append(facPersonalSize__);
        buf.append(",");
        buf.append(facPersonalWarn__);
        buf.append(",");
        buf.append(facPersonalVer__);

        return buf.toString();
    }

}

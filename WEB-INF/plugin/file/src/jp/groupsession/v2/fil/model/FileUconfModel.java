package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.groupsession.v2.fil.GSConstFile;

/**
 * <p>FILE_UCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileUconfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** FUC_MAIN_OKINI mapping */
    private int fucMainOkini__;
    /** FUC_MAIN_CALL mapping */
    private int fucMainCall__;
    /** FUC_RIREKI_CNT mapping */
    private int fucRirekiCnt__;
    /** FUC_SMAIL_SEND mapping */
    private int fucSmailSend__;
    /** FUC_CALL mapping */
    private int fucCall__;

    /**
     * 初期設定値を設定します
     * @param usrSid ユーザSID
     */
    public void init(int usrSid) {
        usrSid__ = usrSid;
        fucMainOkini__ = GSConstFile.MAIN_OKINI_DSP_ON;
        fucMainCall__ = GSConstFile.MAIN_CALL_DSP_CNT;
        fucRirekiCnt__ = GSConstFile.RIREKI_COUNT_DEFAULT;
        fucCall__ = GSConstFile.CALL_DSP_CNT;
        fucSmailSend__ = GSConstFile.SMAIL_SEND_ON;
    }
    /**
     * <p>Default Constructor
     */
    public FileUconfModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get FUC_MAIN_OKINI value
     * @return FUC_MAIN_OKINI value
     */
    public int getFucMainOkini() {
        return fucMainOkini__;
    }

    /**
     * <p>set FUC_MAIN_OKINI value
     * @param fucMainOkini FUC_MAIN_OKINI value
     */
    public void setFucMainOkini(int fucMainOkini) {
        fucMainOkini__ = fucMainOkini;
    }

    /**
     * <p>get FUC_MAIN_CALL value
     * @return FUC_MAIN_CALL value
     */
    public int getFucMainCall() {
        return fucMainCall__;
    }

    /**
     * <p>set FUC_MAIN_CALL value
     * @param fucMainCall FUC_MAIN_CALL value
     */
    public void setFucMainCall(int fucMainCall) {
        fucMainCall__ = fucMainCall;
    }

    /**
     * <p>get FUC_RIREKI_CNT value
     * @return FUC_RIREKI_CNT value
     */
    public int getFucRirekiCnt() {
        return fucRirekiCnt__;
    }

    /**
     * <p>set FUC_RIREKI_CNT value
     * @param fucRirekiCnt FUC_RIREKI_CNT value
     */
    public void setFucRirekiCnt(int fucRirekiCnt) {
        fucRirekiCnt__ = fucRirekiCnt;
    }

    /**
     * <p>get FUC_SMAIL_SEND value
     * @return FUC_SMAIL_SEND value
     */
    public int getFucSmailSend() {
        return fucSmailSend__;
    }

    /**
     * <p>set FUC_SMAIL_SEND value
     * @param fucSmailSend FUC_SMAIL_SEND value
     */
    public void setFucSmailSend(int fucSmailSend) {
        fucSmailSend__ = fucSmailSend;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(fucMainOkini__);
        buf.append(",");
        buf.append(fucMainCall__);
        buf.append(",");
        buf.append(fucRirekiCnt__);
        buf.append(",");
        buf.append(fucSmailSend__);
        return buf.toString();
    }
    /**
     * <p>fucCall を取得します。
     * @return fucCall
     */
    public int getFucCall() {
        return fucCall__;
    }
    /**
     * <p>fucCall をセットします。
     * @param fucCall fucCall
     */
    public void setFucCall(int fucCall) {
        fucCall__ = fucCall;
    }

}

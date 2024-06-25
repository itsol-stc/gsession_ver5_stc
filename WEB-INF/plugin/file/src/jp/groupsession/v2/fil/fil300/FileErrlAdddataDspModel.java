package jp.groupsession.v2.fil.fil300;

import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;

/**
 * <br>[機  能] CSVで入力された取引情報モデルです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileErrlAdddataDspModel extends FileErrlAdddataModel {

    /** 表示用 SID */
    private String dspId__ = null;
    /** 表示用 ファイル名 */
    private String fileName__ = null;
    
    /** 表示用 取引年月日 */
    private String tradeDate__ = null;
    /** 取引先 */
    private String tradeTarget__ = null;
    /** 表示用取引金額 */
    private String tradeMoey__ = null;
    /** 取引金額(外貨) */
    private String tradeGaika__ = null;
    
    /** 入力値保持用 取引年月日 */
    private String tradeDateInput__ = null;
    /** 入力値保持用 取引金額 */
    private String tradeMoeyInput__ = null;
    
    /** インポートエラー内容 */
    private String errorText__ = null;
    /** インポートエラー 存在フラグ */
    private int errorFlg__ = GSConstFile.READ_KBN_SUCCESSFUL;
    /** 保存先フォルダ 存在フラグ */
    private boolean parentExistFlg__ = true;
    
    /**
     * <p>dspId を取得します。
     * @return dspId
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#dspId__
     */
    public String getDspId() {
        return dspId__;
    }
    /**
     * <p>dspId をセットします。
     * @param dspId dspId
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#dspId__
     */
    public void setDspId(String dspId) {
        dspId__ = dspId;
    }
    /**
     * <p>fileName を取得します。
     * @return fileName
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#fileName__
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#fileName__
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    /**
     * <p>tradeDate を取得します。
     * @return tradeDate
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeDate__
     */
    public String getTradeDate() {
        return tradeDate__;
    }
    /**
     * <p>tradeDate をセットします。
     * @param tradeDate tradeDate
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeDate__
     */
    public void setTradeDate(String tradeDate) {
        tradeDate__ = tradeDate;
    }
    /**
     * <p>tradeTarget を取得します。
     * @return tradeTarget
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeTarget__
     */
    public String getTradeTarget() {
        return tradeTarget__;
    }
    /**
     * <p>tradeTarget をセットします。
     * @param tradeTarget tradeTarget
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeTarget__
     */
    public void setTradeTarget(String tradeTarget) {
        tradeTarget__ = tradeTarget;
    }
    /**
     * <p>tradeMoey を取得します。
     * @return tradeMoey
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeMoey__
     */
    public String getTradeMoey() {
        return tradeMoey__;
    }
    /**
     * <p>tradeMoey をセットします。
     * @param tradeMoey tradeMoey
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeMoey__
     */
    public void setTradeMoey(String tradeMoey) {
        tradeMoey__ = tradeMoey;
    }
    /**
     * <p>tradeGaika を取得します。
     * @return tradeGaika
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeGaika__
     */
    public String getTradeGaika() {
        return tradeGaika__;
    }
    /**
     * <p>tradeGaika をセットします。
     * @param tradeGaika tradeGaika
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeGaika__
     */
    public void setTradeGaika(String tradeGaika) {
        tradeGaika__ = tradeGaika;
    }
    /**
     * <p>tradeDateInput を取得します。
     * @return tradeDateInput
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeDateInput__
     */
    public String getTradeDateInput() {
        return tradeDateInput__;
    }
    /**
     * <p>tradeDateInput をセットします。
     * @param tradeDateInput tradeDateInput
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeDateInput__
     */
    public void setTradeDateInput(String tradeDateInput) {
        tradeDateInput__ = tradeDateInput;
    }
    /**
     * <p>tradeMoeyInput を取得します。
     * @return tradeMoeyInput
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeMoeyInput__
     */
    public String getTradeMoeyInput() {
        return tradeMoeyInput__;
    }
    /**
     * <p>tradeMoeyInput をセットします。
     * @param tradeMoeyInput tradeMoeyInput
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#tradeMoeyInput__
     */
    public void setTradeMoeyInput(String tradeMoeyInput) {
        tradeMoeyInput__ = tradeMoeyInput;
    }
    /**
     * <p>errorText を取得します。
     * @return errorText
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#errorText__
     */
    public String getErrorText() {
        return errorText__;
    }
    /**
     * <p>errorText をセットします。
     * @param errorText errorText
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#errorText__
     */
    public void setErrorText(String errorText) {
        errorText__ = errorText;
    }
    /**
     * <p>errorFlg を取得します。
     * @return errorFlg
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#errorFlg__
     */
    public int getErrorFlg() {
        return errorFlg__;
    }
    /**
     * <p>errorFlg をセットします。
     * @param errorFlg errorFlg
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#errorFlg__
     */
    public void setErrorFlg(int errorFlg) {
        this.errorFlg__ = errorFlg;
    }
    /**
     * <p>parentExistFlg を取得します。
     * @return parentExistFlg
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#parentExistFlg__
     */
    public boolean isParentExistFlg() {
        return parentExistFlg__;
    }
    /**
     * <p>parentExistFlg をセットします。
     * @param parentExistFlg parentExistFlg
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataDspModel#parentExistFlg__
     */
    public void setParentExistFlg(boolean parentExistFlg) {
        parentExistFlg__ = parentExistFlg;
    }
    
}

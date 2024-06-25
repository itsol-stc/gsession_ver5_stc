package jp.groupsession.v2.fil.fil020.model;

import java.io.Serializable;

/**
 * キャビネット詳細更新履歴一覧表示用のモデル
 * @author JTS
 *
 */
public class FileHistoryModel implements Serializable {

    /** ユーザSID */
    private int usrSid__;
    /** ユーザ状態区分 */
    private int usrJkbn__;
    /** ユーザログイン停止フラグ */
    private int usrUkoFlg__;
    /** ユーザ姓名 */
    private String usrSeiMei__;
    /** ディレクトリSID */
    private int fdrSid__;
    /** バージョン */
    private int ffrVersion__;
    /** ファイル名 */
    private String ffrName__;
    /** 履歴区分 */
    private int ffrKbn__;
    /** 更新者ID */
    private int ffrEuid__;
    /** 更新グループID */
    private int ffrEgid__;
    /** 更新日時 */
    private String ffrEdate__;
    /** 添付SID */
    private Long binSid__ = Long.valueOf(0);
    /** 復旧ボタン表示フラグ */
    private boolean repairBtnDspFlg__ = true;
    /** ディレクトリ状態区分 */
    private int fdrJtkbn__;
    /** 更新コメント */
    private String ffrUpCmt__;
    /** 取引年月日 */
    private String fdrTradeDate__;
    /** 取引先 */
    private String fdrTradeTarget__;
    /** 取引金額 存在区分 */
    private int fdrTradeMoneykbn__;
    /** 取引金額 */
    private String fdrTradeMoney__;
    /** 外貨名 */
    private String fmmName__;
    /**
     * <p>repairBtnDspFlg を取得します。
     * @return repairBtnDspFlg
     */
    public boolean isRepairBtnDspFlg() {
        return repairBtnDspFlg__;
    }
    /**
     * <p>repairBtnDspFlg をセットします。
     * @param repairBtnDspFlg repairBtnDspFlg
     */
    public void setRepairBtnDspFlg(boolean repairBtnDspFlg) {
        repairBtnDspFlg__ = repairBtnDspFlg;
    }
    /**
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * @param binSid 設定する binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }
    /**
     * @return fdrSid
     */
    public int getFdrSid() {
        return fdrSid__;
    }
    /**
     * @param fdrSid 設定する fdrSid
     */
    public void setFdrSid(int fdrSid) {
        fdrSid__ = fdrSid;
    }
    /**
     * @return ffrEdate
     */
    public String getFfrEdate() {
        return ffrEdate__;
    }
    /**
     * @param ffrEdate 設定する ffrEdate
     */
    public void setFfrEdate(String ffrEdate) {
        ffrEdate__ = ffrEdate;
    }
    /**
     * @return ffrEuid
     */
    public int getFfrEuid() {
        return ffrEuid__;
    }
    /**
     * @param ffrEuid 設定する ffrEuid
     */
    public void setFfrEuid(int ffrEuid) {
        ffrEuid__ = ffrEuid;
    }
    /**
     * @return ffrKbn
     */
    public int getFfrKbn() {
        return ffrKbn__;
    }
    /**
     * @param ffrKbn 設定する ffrKbn
     */
    public void setFfrKbn(int ffrKbn) {
        ffrKbn__ = ffrKbn;
    }
    /**
     * @return ffrName
     */
    public String getFfrName() {
        return ffrName__;
    }
    /**
     * @param ffrName 設定する ffrName
     */
    public void setFfrName(String ffrName) {
        ffrName__ = ffrName;
    }
    /**
     * @return ffrVersion
     */
    public int getFfrVersion() {
        return ffrVersion__;
    }
    /**
     * @param ffrVersion 設定する ffrVersion
     */
    public void setFfrVersion(int ffrVersion) {
        ffrVersion__ = ffrVersion;
    }
    /**
     * @return usrJkbn
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }
    /**
     * @param usrJkbn 設定する usrJkbn
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }
    /**
     * @return usrSeiMei
     */
    public String getUsrSeiMei() {
        return usrSeiMei__;
    }
    /**
     * @param usrSeiMei 設定する usrSeiMei
     */
    public void setUsrSeiMei(String usrSeiMei) {
        usrSeiMei__ = usrSeiMei;
    }
    /**
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * @param usrSid 設定する usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>fdrJtkbn を取得します。
     * @return fdrJtkbn
     */
    public int getFdrJtkbn() {
        return fdrJtkbn__;
    }
    /**
     * <p>fdrJtkbn をセットします。
     * @param fdrJtkbn fdrJtkbn
     */
    public void setFdrJtkbn(int fdrJtkbn) {
        fdrJtkbn__ = fdrJtkbn;
    }
    /**
     * <p>ffrUpCmt を取得します。
     * @return ffrUpCmt
     */
    public String getFfrUpCmt() {
        return ffrUpCmt__;
    }
    /**
     * <p>ffrUpCmt をセットします。
     * @param ffrUpCmt ffrUpCmt
     */
    public void setFfrUpCmt(String ffrUpCmt) {
        ffrUpCmt__ = ffrUpCmt;
    }
    /**
     * <p>ffrEgid を取得します。
     * @return ffrEgid
     */
    public int getFfrEgid() {
        return ffrEgid__;
    }
    /**
     * <p>ffrEgid をセットします。
     * @param ffrEgid ffrEgid
     */
    public void setFfrEgid(int ffrEgid) {
        ffrEgid__ = ffrEgid;
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
     * <p>fdrTradeDate を取得します。
     * @return fdrTradeDate
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fdrTradeDate__
     */
    public String getFdrTradeDate() {
        return fdrTradeDate__;
    }
    /**
     * <p>fdrTradeDate をセットします。
     * @param fdrTradeDate fdrTradeDate
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fdrTradeDate__
     */
    public void setFdrTradeDate(String fdrTradeDate) {
        fdrTradeDate__ = fdrTradeDate;
    }
    /**
     * <p>fdrTradeTarget を取得します。
     * @return fdrTradeTarget
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fdrTradeTarget__
     */
    public String getFdrTradeTarget() {
        return fdrTradeTarget__;
    }
    /**
     * <p>fdrTradeTarget をセットします。
     * @param fdrTradeTarget fdrTradeTarget
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fdrTradeTarget__
     */
    public void setFdrTradeTarget(String fdrTradeTarget) {
        fdrTradeTarget__ = fdrTradeTarget;
    }
    /**
     * <p>fdrTradeMoneykbn を取得します。
     * @return fdrTradeMoneykbn
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fdrTradeMoneykbn__
     */
    public int getFdrTradeMoneykbn() {
        return fdrTradeMoneykbn__;
    }
    /**
     * <p>fdrTradeMoneykbn をセットします。
     * @param fdrTradeMoneykbn fdrTradeMoneykbn
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fdrTradeMoneykbn__
     */
    public void setFdrTradeMoneykbn(int fdrTradeMoneykbn) {
        fdrTradeMoneykbn__ = fdrTradeMoneykbn;
    }
    /**
     * <p>fdrTradeMoney を取得します。
     * @return fdrTradeMoney
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fdrTradeMoney__
     */
    public String getFdrTradeMoney() {
        return fdrTradeMoney__;
    }
    /**
     * <p>fdrTradeMoney をセットします。
     * @param fdrTradeMoney fdrTradeMoney
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fdrTradeMoney__
     */
    public void setFdrTradeMoney(String fdrTradeMoney) {
        fdrTradeMoney__ = fdrTradeMoney;
    }
    /**
     * <p>fmmName を取得します。
     * @return fmmName
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fmmName__
     */
    public String getFmmName() {
        return fmmName__;
    }
    /**
     * <p>fmmName をセットします。
     * @param fmmName fmmName
     * @see jp.groupsession.v2.fil.fil020.model.FileHistoryModel#fmmName__
     */
    public void setFmmName(String fmmName) {
        fmmName__ = fmmName;
    }
}

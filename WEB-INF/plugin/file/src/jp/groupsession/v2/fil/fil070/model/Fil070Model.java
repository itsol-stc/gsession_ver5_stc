package jp.groupsession.v2.fil.fil070.model;

import java.io.Serializable;

import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] ファイル詳細画面のモデルクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil070Model implements Serializable {

    /** ユーザSID */
    private int usrSid__;
    /** ユーザ状態区分 */
    private int usrJkbn__;
    /** ユーザログイン停止フラグ */
    private int usrUkoFlg__ = GSConst.YUKOMUKO_YUKO;
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
    /** 更新コメント */
    private String ffrUpCmt__;
    /** 添付SID */
    private Long binSid__ = 0L;
    /** 添付ファイル保存名 */
    private String saveFileName__;
    /** 最新バージョンフラグ */
    private boolean newVersionFlg__ = false;
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
     * <p>newVersionFlg を取得します。
     * @return newVersionFlg
     */
    public boolean isNewVersionFlg() {
        return newVersionFlg__;
    }
    /**
     * <p>newVersionFlg をセットします。
     * @param newVersionFlg newVersionFlg
     */
    public void setNewVersionFlg(boolean newVersionFlg) {
        newVersionFlg__ = newVersionFlg;
    }
    /**
     * <p>saveFileName を取得します。
     * @return saveFileName
     */
    public String getSaveFileName() {
        return saveFileName__;
    }
    /**
     * <p>saveFileName をセットします。
     * @param saveFileName saveFileName
     */
    public void setSaveFileName(String saveFileName) {
        saveFileName__ = saveFileName;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }
    /**
     * <p>fdrSid を取得します。
     * @return fdrSid
     */
    public int getFdrSid() {
        return fdrSid__;
    }
    /**
     * <p>fdrSid をセットします。
     * @param fdrSid fdrSid
     */
    public void setFdrSid(int fdrSid) {
        fdrSid__ = fdrSid;
    }
    /**
     * <p>ffrEdate を取得します。
     * @return ffrEdate
     */
    public String getFfrEdate() {
        return ffrEdate__;
    }
    /**
     * <p>ffrEdate をセットします。
     * @param ffrEdate ffrEdate
     */
    public void setFfrEdate(String ffrEdate) {
        ffrEdate__ = ffrEdate;
    }
    /**
     * <p>ffrEuid を取得します。
     * @return ffrEuid
     */
    public int getFfrEuid() {
        return ffrEuid__;
    }
    /**
     * <p>ffrEuid をセットします。
     * @param ffrEuid ffrEuid
     */
    public void setFfrEuid(int ffrEuid) {
        ffrEuid__ = ffrEuid;
    }
    /**
     * <p>ffrKbn を取得します。
     * @return ffrKbn
     */
    public int getFfrKbn() {
        return ffrKbn__;
    }
    /**
     * <p>ffrKbn をセットします。
     * @param ffrKbn ffrKbn
     */
    public void setFfrKbn(int ffrKbn) {
        ffrKbn__ = ffrKbn;
    }
    /**
     * <p>ffrName を取得します。
     * @return ffrName
     */
    public String getFfrName() {
        return ffrName__;
    }
    /**
     * <p>ffrName をセットします。
     * @param ffrName ffrName
     */
    public void setFfrName(String ffrName) {
        ffrName__ = ffrName;
    }
    /**
     * <p>ffrVersion を取得します。
     * @return ffrVersion
     */
    public int getFfrVersion() {
        return ffrVersion__;
    }
    /**
     * <p>ffrVersion をセットします。
     * @param ffrVersion ffrVersion
     */
    public void setFfrVersion(int ffrVersion) {
        ffrVersion__ = ffrVersion;
    }
    /**
     * <p>usrJkbn を取得します。
     * @return usrJkbn
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }
    /**
     * <p>usrJkbn をセットします。
     * @param usrJkbn usrJkbn
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }
    /**
     * <p>usrSeiMei を取得します。
     * @return usrSeiMei
     */
    public String getUsrSeiMei() {
        return usrSeiMei__;
    }
    /**
     * <p>usrSeiMei をセットします。
     * @param usrSeiMei usrSeiMei
     */
    public void setUsrSeiMei(String usrSeiMei) {
        usrSeiMei__ = usrSeiMei;
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

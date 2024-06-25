package jp.groupsession.v2.fil.fil010;

import java.io.Serializable;

import jp.groupsession.v2.cmn.GSConst;

/**
 * キャビネット一覧で使用する各フォルダ、ファイルへのリンク情報を保持するJavaBeans
 * @author ohba
 *
 */
public class FileLinkSimpleModel implements Serializable {

    /** キャビネットSID */
    private int cabinetSid__;
    /** ディレクトリSID */
    private int directorySid__;
    /** ディレクトリ区分 0=フォルダ 1=ファイル*/
    private int directoryKbn__;
    /** ディレクトリ名称*/
    private String directoryName__;
    /** ディレクトリフルパス名称*/
    private String directoryFullPathName__;
    /** ディレクトリ更新日時文字列*/
    private String directoryUpdateStr__;
    /** バイナリSID*/
    private Long binSid__ = 0L;
    /** ファイル更新者名 */
    private String editUsrName__;
    /** キャビネットのアイコン */
    private long fcbMark__;

    /** 更新コメント */
    private String ffrUpCmt__;
    /** 更新者状態区分 */
    private String editUsrJkbn__;
    /** 更新者ログイン停止フラグ */
    private int editUsrUkoFlg__ = GSConst.YUKOMUKO_YUKO;

    /**
     * @return cabinetSid
     */
    public int getCabinetSid() {
        return cabinetSid__;
    }
    /**
     * @param cabinetSid 設定する cabinetSid
     */
    public void setCabinetSid(int cabinetSid) {
        cabinetSid__ = cabinetSid;
    }
    /**
     * @return directoryFullPathName
     */
    public String getDirectoryFullPathName() {
        return directoryFullPathName__;
    }
    /**
     * @param directoryFullPathName 設定する directoryFullPathName
     */
    public void setDirectoryFullPathName(String directoryFullPathName) {
        directoryFullPathName__ = directoryFullPathName;
    }
    /**
     * @return directoryKbn
     */
    public int getDirectoryKbn() {
        return directoryKbn__;
    }
    /**
     * @param directoryKbn 設定する directoryKbn
     */
    public void setDirectoryKbn(int directoryKbn) {
        directoryKbn__ = directoryKbn;
    }
    /**
     * @return directoryName
     */
    public String getDirectoryName() {
        return directoryName__;
    }
    /**
     * @param directoryName 設定する directoryName
     */
    public void setDirectoryName(String directoryName) {
        directoryName__ = directoryName;
    }
    /**
     * @return directorySid
     */
    public int getDirectorySid() {
        return directorySid__;
    }
    /**
     * @param directorySid 設定する directorySid
     */
    public void setDirectorySid(int directorySid) {
        directorySid__ = directorySid;
    }
    /**
     * @return directoryUpdateStr
     */
    public String getDirectoryUpdateStr() {
        return directoryUpdateStr__;
    }
    /**
     * @param directoryUpdateStr 設定する directoryUpdateStr
     */
    public void setDirectoryUpdateStr(String directoryUpdateStr) {
        directoryUpdateStr__ = directoryUpdateStr;
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
     * <p>editUsrName を取得します。
     * @return editUsrName
     */
    public String getEditUsrName() {
        return editUsrName__;
    }
    /**
     * <p>editUsrName をセットします。
     * @param editUsrName editUsrName
     */
    public void setEditUsrName(String editUsrName) {
        editUsrName__ = editUsrName;
    }
    /**
     * <p>fcbMark を取得します。
     * @return fcbMark
     */
    public long getFcbMark() {
        return fcbMark__;
    }
    /**
     * <p>fcbMark をセットします。
     * @param fcbMark fcbMark
     */
    public void setFcbMark(long fcbMark) {
        fcbMark__ = fcbMark;
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
     * <p>editUsrJkbn を取得します。
     * @return editUsrJkbn
     */
    public String getEditUsrJkbn() {
        return editUsrJkbn__;
    }
    /**
     * <p>editUsrJkbn をセットします。
     * @param editUsrJkbn editUsrJkbn
     */
    public void setEditUsrJkbn(String editUsrJkbn) {
        editUsrJkbn__ = editUsrJkbn;
    }
    /**
     * <p>editUsrUkoFlg を取得します。
     * @return editUsrUkoFlg
     */
    public int getEditUsrUkoFlg() {
        return editUsrUkoFlg__;
    }
    /**
     * <p>editUsrUkoFlg をセットします。
     * @param editUsrUkoFlg editUsrUkoFlg
     */
    public void setEditUsrUkoFlg(int editUsrUkoFlg) {
        editUsrUkoFlg__ = editUsrUkoFlg;
    }


}

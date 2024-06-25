package jp.groupsession.v2.api.file.shortcutlist;

import jp.groupsession.v2.fil.model.FileModel;

/**
 * <p>WEBAPIショートカット一覧取得用モデル
 *
 * @author JTS
 */
public class ApiFileShortcutListModel extends FileModel {

    /** グループ名 */
    private String grpName__;
    /** 編集者名 */
    private String editUsrName__;
    /** 編集権限 */
    private int writeKbn__;
    /** 編集者区分 */
    private int editUsrKbn__;
    /** 編集者SID */
    private int editUsrSid__;
    /** 編集者ユーザ無効フラグ */
    private int editUsrUkoFlg__;
    /** 更新日時 */
    private String editDate__;
    /** ファイルサイズ */
    private String fileSize__;
    /** ディレクトリパス */
    private String fdrPath__;
    /** 電帳法対応区分 */
    private int cabinetKbn__;
    

    /**
     * <p>grpName を取得します。
     * @return grpName
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#grpName__
     */
    public String getGrpName() {
        return grpName__;
    }
    /**
     * <p>grpName をセットします。
     * @param grpName grpName
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#grpName__
     */
    public void setGrpName(String grpName) {
        grpName__ = grpName;
    }
    /**
     * <p>editUsrName を取得します。
     * @return editUsrName
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editUsrName__
     */
    public String getEditUsrName() {
        return editUsrName__;
    }
    /**
     * <p>editUsrName をセットします。
     * @param editUsrName editUsrName
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editUsrName__
     */
    public void setEditUsrName(String editUsrName) {
        editUsrName__ = editUsrName;
    }
    /**
     * <p>writeKbn を取得します。
     * @return writeKbn
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#writeKbn__
     */
    public int getWriteKbn() {
        return writeKbn__;
    }
    /**
     * <p>writeKbn をセットします。
     * @param writeKbn writeKbn
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#writeKbn__
     */
    public void setWriteKbn(int writeKbn) {
        writeKbn__ = writeKbn;
    }
    /**
     * <p>editUsrKbn を取得します。
     * @return editUsrKbn
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editUsrKbn__
     */
    public int getEditUsrKbn() {
        return editUsrKbn__;
    }
    /**
     * <p>editUsrKbn をセットします。
     * @param editUsrKbn editUsrKbn
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editUsrKbn__
     */
    public void setEditUsrKbn(int editUsrKbn) {
        editUsrKbn__ = editUsrKbn;
    }
    /**
     * <p>editUsrSid を取得します。
     * @return editUsrSid
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editUsrSid__
     */
    public int getEditUsrSid() {
        return editUsrSid__;
    }
    /**
     * <p>editUsrSid をセットします。
     * @param editUsrSid editUsrSid
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editUsrSid__
     */
    public void setEditUsrSid(int editUsrSid) {
        editUsrSid__ = editUsrSid;
    }
    /**
     * <p>editUsrUkoFlg を取得します。
     * @return editUsrUkoFlg
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editUsrUkoFlg__
     */
    public int getEditUsrUkoFlg() {
        return editUsrUkoFlg__;
    }
    /**
     * <p>editUsrUkoFlg をセットします。
     * @param editUsrUkoFlg editUsrUkoFlg
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editUsrUkoFlg__
     */
    public void setEditUsrUkoFlg(int editUsrUkoFlg) {
        editUsrUkoFlg__ = editUsrUkoFlg;
    }
    /**
     * <p>editDate を取得します。
     * @return editDate
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editDate__
     */
    public String getEditDate() {
        return editDate__;
    }
    /**
     * <p>editDate をセットします。
     * @param editDate editDate
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#editDate__
     */
    public void setEditDate(String editDate) {
        editDate__ = editDate;
    }
    /**
     * <p>fileSize を取得します。
     * @return fileSize
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#fileSize__
     */
    public String getFileSize() {
        return fileSize__;
    }
    /**
     * <p>fileSize をセットします。
     * @param fileSize fileSize
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchModel#fileSize__
     */
    public void setFileSize(String fileSize) {
        fileSize__ = fileSize;
    }
    /**
     * <p>fdrPath を取得します。
     * @return fdrPath
     * @see jp.groupsession.v2.api.file.shortcutlist.ApiFileShortcutListModel#fdrPath__
     */
    public String getFdrPath() {
        return fdrPath__;
    }
    /**
     * <p>fdrPath をセットします。
     * @param fdrPath fdrPath
     * @see jp.groupsession.v2.api.file.shortcutlist.ApiFileShortcutListModel#fdrPath__
     */
    public void setFdrPath(String fdrPath) {
        fdrPath__ = fdrPath;
    }
    /**
     * <p>cabinetKbn を取得します。
     * @return cabinetKbn
     * @see jp.groupsession.v2.api.file.shortcutlist.ApiFileShortcutListModel#cabinetKbn__
     */
    public int getCabinetKbn() {
        return cabinetKbn__;
    }
    /**
     * <p>cabinetKbn をセットします。
     * @param cabinetKbn cabinetKbn
     * @see jp.groupsession.v2.api.file.shortcutlist.ApiFileShortcutListModel#cabinetKbn__
     */
    public void setCabinetKbn(int cabinetKbn) {
        cabinetKbn__ = cabinetKbn;
    }
}

package jp.groupsession.v2.fil.fil240;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.fil.model.FileDirectoryModel;


/**
 * <p>更新通知一覧画面で使用するデータモデル
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Fil240Model extends FileDirectoryModel {

    /** 更新者名 */
    private String editUsrName__;
    /** 更新者状態区分 */
    private String editUsrJkbn__;
    /** 更新コメント */
    private String ffrUpCmt__;
    /** 更新者ログイン停止フラグ */
    private int editUsrUkoFlg__ = GSConst.YUKOMUKO_YUKO;

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

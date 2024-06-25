package jp.groupsession.v2.tcd.tcd150kn;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.tcd.tcd150.Tcd150Form;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150knForm extends Tcd150Form{

    /** 登録対象 ユーザ名 */
    private String tcd150knSltUserName__ = null;
    
    /** 取込みファイル名 */
    private String fileName__ = null;
    
    /** ユーザ有効フラグ */
    private int userUkoFLg__ = GSConst.YUKOMUKO_YUKO;

    /**
     * <p>tcd150knSltUserName を取得します。
     * @return tcd150knSltUserName
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knForm#tcd150knSltUserName__
     */
    public String getTcd150knSltUserName() {
        return tcd150knSltUserName__;
    }

    /**
     * <p>tcd150knSltUserName をセットします。
     * @param tcd150knSltUserName tcd150knSltUserName
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knForm#tcd150knSltUserName__
     */
    public void setTcd150knSltUserName(String tcd150knSltUserName) {
        tcd150knSltUserName__ = tcd150knSltUserName;
    }

    /**
     * <p>fileName を取得します。
     * @return fileName
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knForm#fileName__
     */
    public String getFileName() {
        return fileName__;
    }

    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knForm#fileName__
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }

    /**
     * <p>userUkoFLg を取得します。
     * @return userUkoFLg
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knForm#userUkoFLg__
     */
    public int getUserUkoFLg() {
        return userUkoFLg__;
    }

    /**
     * <p>userUkoFLg をセットします。
     * @param userUkoFLg userUkoFLg
     * @see jp.groupsession.v2.tcd.tcd150kn.Tcd150knForm#userUkoFLg__
     */
    public void setUserUkoFLg(int userUkoFLg) {
        userUkoFLg__ = userUkoFLg;
    }
}

package jp.groupsession.v2.tcd.tcd200kn;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.tcd.tcd200.Tcd200Form;

/**
 * <br>[機  能] タイムカード 管理者設定有休日数登録,編集確認画面のフォームクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd200knForm extends Tcd200Form {

    /** 編集対象ユーザ */
    private int tcd200editUser__;

    /** 編集対象年度 */
    private int tcd200editNendo__;

    /** 編集対象ユーザ名 */
    private String tcd200editUserName__ = null;
    
    /** ユーザ有効フラグ */
    private int tcd200editUserUkoFlg__ = GSConst.YUKOMUKO_YUKO;

    /**
     * <p>tcd200editUser を取得します。
     * @return tcd200editUser
     * @see jp.groupsession.v2.tcd.tcd200kn.Tcd200knForm#tcd200editUser__
     */
    public int getTcd200editUser() {
        return tcd200editUser__;
    }

    /**
     * <p>tcd200editUser をセットします。
     * @param tcd200editUser tcd200editUser
     * @see jp.groupsession.v2.tcd.tcd200kn.Tcd200knForm#tcd200editUser__
     */
    public void setTcd200editUser(int tcd200editUser) {
        tcd200editUser__ = tcd200editUser;
    }

    /**
     * <p>tcd200editNendo を取得します。
     * @return tcd200editNendo
     * @see jp.groupsession.v2.tcd.tcd200kn.Tcd200knForm#tcd200editNendo__
     */
    public int getTcd200editNendo() {
        return tcd200editNendo__;
    }

    /**
     * <p>tcd200editNendo をセットします。
     * @param tcd200editNendo tcd200editNendo
     * @see jp.groupsession.v2.tcd.tcd200kn.Tcd200knForm#tcd200editNendo__
     */
    public void setTcd200editNendo(int tcd200editNendo) {
        tcd200editNendo__ = tcd200editNendo;
    }

    /**
     * <p>tcd200editUserName を取得します。
     * @return tcd200editUserName
     * @see jp.groupsession.v2.tcd.tcd200kn.Tcd200knForm#tcd200editUserName__
     */
    public String getTcd200editUserName() {
        return tcd200editUserName__;
    }

    /**
     * <p>tcd200editUserName をセットします。
     * @param tcd200editUserName tcd200editUserName
     * @see jp.groupsession.v2.tcd.tcd200kn.Tcd200knForm#tcd200editUserName__
     */
    public void setTcd200editUserName(String tcd200editUserName) {
        tcd200editUserName__ = tcd200editUserName;
    }

    /**
     * <p>tcd200editUserUkoFlg を取得します。
     * @return tcd200editUserUkoFlg
     * @see jp.groupsession.v2.tcd.tcd200kn.Tcd200knForm#tcd200editUserUkoFlg__
     */
    public int getTcd200editUserUkoFlg() {
        return tcd200editUserUkoFlg__;
    }

    /**
     * <p>tcd200editUserUkoFlg をセットします。
     * @param tcd200editUserUkoFlg tcd200editUserUkoFlg
     * @see jp.groupsession.v2.tcd.tcd200kn.Tcd200knForm#tcd200editUserUkoFlg__
     */
    public void setTcd200editUserUkoFlg(int tcd200editUserUkoFlg) {
        tcd200editUserUkoFlg__ = tcd200editUserUkoFlg;
    }
}

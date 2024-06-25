package jp.groupsession.v2.prj.model;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.usr.UserUtil;


/**
 * <br>[機  能] TODOの担当者を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TodoTantoModel extends AbstractModel {

    /** 姓 */
    private String sei__ = null;
    /** 名 */
    private String mei__ = null;
    /** 削除ユーザか */
    private boolean delUser__ = false;
    /** ログイン停止フラグ */
    private int usrUkoFlg__ = GSConst.YUKOMUKO_YUKO;

    /**
     * <p>delUser を取得します。
     * @return delUser
     */
    public boolean isDelUser() {
        return delUser__;
    }
    /**
     * <p>delUser をセットします。
     * @param delUser delUser
     */
    public void setDelUser(boolean delUser) {
        delUser__ = delUser;
    }
    /**
     * <p>mei を取得します。
     * @return mei
     */
    public String getMei() {
        return mei__;
    }
    /**
     * <p>mei をセットします。
     * @param mei mei
     */
    public void setMei(String mei) {
        mei__ = mei;
    }
    /**
     * <p>sei を取得します。
     * @return sei
     */
    public String getSei() {
        return sei__;
    }
    /**
     * <p>sei をセットします。
     * @param sei sei
     */
    public void setSei(String sei) {
        sei__ = sei;
    };

    /**
     * <br>[機  能] 担当者名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 担当者名
     */
    public String getUserName() {
        return UserUtil.makeName(sei__, mei__);
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
}

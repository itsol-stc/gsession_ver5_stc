package jp.groupsession.v2.fil.fil280;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] ファイル管理 個人キャビネット管理画面 検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil280SearchModel extends AbstractModel {

    /** キーワード */
    private String keyword__ = null;
    /** グループSID */
    private int grpSid__ = 0;
    /** ユーザSID */
    private int userSid__ = 0;
    /** 許可ユーザSID一覧 */
    private ArrayList<Integer> permitUsrSids__ = null;

    /**
     * <p>keyword を取得します。
     * @return keyword
     */
    public String getKeyword() {
        return keyword__;
    }
    /**
     * <p>keyword をセットします。
     * @param keyword keyword
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>permitUsrSids を取得します。
     * @return permitUsrSids
     */
    public ArrayList<Integer> getPermitUsrSids() {
        return permitUsrSids__;
    }
    /**
     * <p>permitUsrSids をセットします。
     * @param permitUsrSids permitUsrSids
     */
    public void setPermitUsrSids(List<Integer> permitUsrSids) {
        if (permitUsrSids != null) {
            permitUsrSids__ = new ArrayList<Integer>(permitUsrSids);
        } else {
            permitUsrSids__ = null;
        }
    }
}

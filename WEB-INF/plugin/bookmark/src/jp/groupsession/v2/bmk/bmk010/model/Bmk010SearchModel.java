package jp.groupsession.v2.bmk.bmk010.model;

import jp.groupsession.v2.bmk.bmk010.Bmk010Biz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ブックマーク画面の検索条件を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk010SearchModel extends AbstractModel {

    /** セッションユーザ情報 */
    private BaseUserModel userMdl__;

    /** ブックマーク区分 */
    private int bmkMode__ = 0;
    /** ソート項目 */
    private int sortKey__ = 0;
    /** 並び順 */
    private int orderKey__ = 0;

    /** グループ */
    private int group__ = Bmk010Biz.INIT_VALUE;
    /** ユーザ */
    private int user__ = Bmk010Biz.INIT_VALUE;

    /** ラベルSID */
    private int label__ = Bmk010Biz.INIT_VALUE;

    /** ページ */
    private int page__ = 0;
    /** 1ページの最大表示件数 */
    private int maxViewCount__ = 0;

    /**
     * <p>bmkMode を取得します。
     * @return bmkMode
     */
    public int getBmkMode() {
        return bmkMode__;
    }
    /**
     * <p>bmkMode をセットします。
     * @param bmkMode bmkMode
     */
    public void setBmkMode(int bmkMode) {
        bmkMode__ = bmkMode;
    }
    /**
     * <p>group を取得します。
     * @return group
     */
    public int getGroup() {
        return group__;
    }
    /**
     * <p>group をセットします。
     * @param group group
     */
    public void setGroup(int group) {
        group__ = group;
    }
    /**
     * <p>label を取得します。
     * @return label
     */
    public int getLabel() {
        return label__;
    }
    /**
     * <p>label をセットします。
     * @param label label
     */
    public void setLabel(int label) {
        label__ = label;
    }
    /**
     * <p>maxViewCount を取得します。
     * @return maxViewCount
     */
    public int getMaxViewCount() {
        return maxViewCount__;
    }
    /**
     * <p>maxViewCount をセットします。
     * @param maxViewCount maxViewCount
     */
    public void setMaxViewCount(int maxViewCount) {
        maxViewCount__ = maxViewCount;
    }
    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public int getOrderKey() {
        return orderKey__;
    }
    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
    }
    /**
     * <p>page を取得します。
     * @return page
     */
    public int getPage() {
        return page__;
    }
    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(int page) {
        page__ = page;
    }

    /**
     * <p>userMdl を取得します。
     * @return userMdl
     */
    public BaseUserModel getUserMdl() {
        return userMdl__;
    }
    /**
     * <p>userMdl をセットします。
     * @param userMdl sUserMdl
     */
    public void setUserMdl(BaseUserModel userMdl) {
        userMdl__ = userMdl;
    }
    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>user を取得します。
     * @return user
     */
    public int getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(int user) {
        user__ = user;
    }
}
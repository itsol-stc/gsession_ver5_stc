package jp.groupsession.v2.prj;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] ファイルツリーフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjTreeParamModel extends AbstractParamModel {

    /** ツリーフォーム 1階層 */
    private String[] treeFormLv1__;
    /** ツリーフォーム 2階層 */
    private String[] treeFormLv2__;
    /** ツリーフォーム 3階層 */
    private String[] treeFormLv3__;
    /** ツリーフォーム 4階層 */
    private String[] treeFormLv4__;
    /** ツリーフォーム 5階層 */
    private String[] treeFormLv5__;
    /** ツリーフォーム 6階層 */
    private String[] treeFormLv6__;
    /** ディレクトリ選択値 */
    private String selectDir__ = "";
    /** ディレクトリ選択値(移動先) */
    private String selectMoveDir__ = "";
    /** セパレートキー */
    private String sepKey__ = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;

    /**
     * <p>selectMoveDir を取得します。
     * @return selectMoveDir
     */
    public String getSelectMoveDir() {
        return selectMoveDir__;
    }
    /**
     * <p>selectMoveDir をセットします。
     * @param selectMoveDir selectMoveDir
     */
    public void setSelectMoveDir(String selectMoveDir) {
        selectMoveDir__ = selectMoveDir;
    }
    /**
     * <p>selectDir を取得します。
     * @return selectDir
     */
    public String getSelectDir() {
        return selectDir__;
    }
    /**
     * <p>selectDir をセットします。
     * @param selectDir selectDir
     */
    public void setSelectDir(String selectDir) {
        selectDir__ = selectDir;
    }
    /**
     * <p>sepKey を取得します。
     * @return sepKey
     */
    public String getSepKey() {
        return sepKey__;
    }
    /**
     * <p>sepKey をセットします。
     * @param sepKey sepKey
     */
    public void setSepKey(String sepKey) {
        sepKey__ = sepKey;
    }
    /**
     * <p>treeFormLv1 を取得します。
     * @return treeFormLv1
     */
    public String[] getTreeFormLv1() {
        return treeFormLv1__;
    }
    /**
     * <p>treeFormLv1 をセットします。
     * @param treeFormLv1 treeFormLv1
     */
    public void setTreeFormLv1(String[] treeFormLv1) {
        treeFormLv1__ = treeFormLv1;
    }
    /**
     * <p>treeFormLv2 を取得します。
     * @return treeFormLv2
     */
    public String[] getTreeFormLv2() {
        return treeFormLv2__;
    }
    /**
     * <p>treeFormLv2 をセットします。
     * @param treeFormLv2 treeFormLv2
     */
    public void setTreeFormLv2(String[] treeFormLv2) {
        treeFormLv2__ = treeFormLv2;
    }
    /**
     * <p>treeFormLv3 を取得します。
     * @return treeFormLv3
     */
    public String[] getTreeFormLv3() {
        return treeFormLv3__;
    }
    /**
     * <p>treeFormLv3 をセットします。
     * @param treeFormLv3 treeFormLv3
     */
    public void setTreeFormLv3(String[] treeFormLv3) {
        treeFormLv3__ = treeFormLv3;
    }
    /**
     * <p>treeFormLv4 を取得します。
     * @return treeFormLv4
     */
    public String[] getTreeFormLv4() {
        return treeFormLv4__;
    }
    /**
     * <p>treeFormLv4 をセットします。
     * @param treeFormLv4 treeFormLv4
     */
    public void setTreeFormLv4(String[] treeFormLv4) {
        treeFormLv4__ = treeFormLv4;
    }
    /**
     * <p>treeFormLv5 を取得します。
     * @return treeFormLv5
     */
    public String[] getTreeFormLv5() {
        return treeFormLv5__;
    }
    /**
     * <p>treeFormLv5 をセットします。
     * @param treeFormLv5 treeFormLv5
     */
    public void setTreeFormLv5(String[] treeFormLv5) {
        treeFormLv5__ = treeFormLv5;
    }
    /**
     * <p>treeFormLv6 を取得します。
     * @return treeFormLv6
     */
    public String[] getTreeFormLv6() {
        return treeFormLv6__;
    }
    /**
     * <p>treeFormLv6 をセットします。
     * @param treeFormLv6 treeFormLv6
     */
    public void setTreeFormLv6(String[] treeFormLv6) {
        treeFormLv6__ = treeFormLv6;
    }
}
package jp.groupsession.v2.fil;

import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] ファイルツリーフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilTreeParamModel extends AbstractFileParamModel {

    /** ツリーフォーム root階層 */
    private String[] treeFormLv0__;
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
    /** ツリーフォーム 7階層 */
    private String[] treeFormLv7__;
    /** ツリーフォーム 8階層 */
    private String[] treeFormLv8__;
    /** ツリーフォーム 9階層 */
    private String[] treeFormLv9__;
    /** ツリーフォーム 10階層 */
    private String[] treeFormLv10__;
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
     * @return treeFormLv0
     */
    public String[] getTreeFormLv0() {
        return treeFormLv0__;
    }
    /**
     * @param treeFormLv0 設定する treeFormLv0
     */
    public void setTreeFormLv0(String[] treeFormLv0) {
        treeFormLv0__ = treeFormLv0;
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
    /**
     * <p>treeFormLv10 を取得します。
     * @return treeFormLv10
     */
    public String[] getTreeFormLv10() {
        return treeFormLv10__;
    }
    /**
     * <p>treeFormLv10 をセットします。
     * @param treeFormLv10 treeFormLv10
     */
    public void setTreeFormLv10(String[] treeFormLv10) {
        treeFormLv10__ = treeFormLv10;
    }
    /**
     * <p>treeFormLv7 を取得します。
     * @return treeFormLv7
     */
    public String[] getTreeFormLv7() {
        return treeFormLv7__;
    }
    /**
     * <p>treeFormLv7 をセットします。
     * @param treeFormLv7 treeFormLv7
     */
    public void setTreeFormLv7(String[] treeFormLv7) {
        treeFormLv7__ = treeFormLv7;
    }
    /**
     * <p>treeFormLv8 を取得します。
     * @return treeFormLv8
     */
    public String[] getTreeFormLv8() {
        return treeFormLv8__;
    }
    /**
     * <p>treeFormLv8 をセットします。
     * @param treeFormLv8 treeFormLv8
     */
    public void setTreeFormLv8(String[] treeFormLv8) {
        treeFormLv8__ = treeFormLv8;
    }
    /**
     * <p>treeFormLv9 を取得します。
     * @return treeFormLv9
     */
    public String[] getTreeFormLv9() {
        return treeFormLv9__;
    }
    /**
     * <p>treeFormLv9 をセットします。
     * @param treeFormLv9 treeFormLv9
     */
    public void setTreeFormLv9(String[] treeFormLv9) {
        treeFormLv9__ = treeFormLv9;
    }
}
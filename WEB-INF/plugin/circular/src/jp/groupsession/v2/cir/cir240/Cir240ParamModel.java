package jp.groupsession.v2.cir.cir240;

import jp.groupsession.v2.cir.cir230.Cir230ParamModel;

/**
 * <br>[機  能] ラベル登録編集画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir240ParamModel extends Cir230ParamModel {

    /** ラベル名 */
    private String cir240LabelName__ = null;
    /** 初期表示区分 */
    private int cir240initKbn__ = 0;
    /**
     * <p>cir240LabelName を取得します。
     * @return cir240LabelName
     * @see jp.groupsession.v2.cir.cir240.Cir240ParamModel#cir240LabelName__
     */
    public String getCir240LabelName() {
        return cir240LabelName__;
    }
    /**
     * <p>cir240LabelName をセットします。
     * @param cir240LabelName cir240LabelName
     * @see jp.groupsession.v2.cir.cir240.Cir240ParamModel#cir240LabelName__
     */
    public void setCir240LabelName(String cir240LabelName) {
        cir240LabelName__ = cir240LabelName;
    }
    /**
     * <p>cir240initKbn を取得します。
     * @return cir240initKbn
     * @see jp.groupsession.v2.cir.cir240.Cir240ParamModel#cir240initKbn__
     */
    public int getCir240initKbn() {
        return cir240initKbn__;
    }
    /**
     * <p>cir240initKbn をセットします。
     * @param cir240initKbn cir240initKbn
     * @see jp.groupsession.v2.cir.cir240.Cir240ParamModel#cir240initKbn__
     */
    public void setCir240initKbn(int cir240initKbn) {
        cir240initKbn__ = cir240initKbn;
    }

}

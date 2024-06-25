package jp.groupsession.v2.cir.cir230;

import java.util.List;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir150.Cir150ParamModel;
import jp.groupsession.v2.cir.model.LabelDataModel;

/**
 * <br>[機  能] ラベル管理画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir230ParamModel extends Cir150ParamModel {

    /** ラベル編集モード */
    private int cir230LabelCmdMode__ = GSConstCircular.CMDMODE_ADD;
    /** 編集対象ラベルSID */
    private int cir230EditLabelId__ = 0;
    /** ラベルリスト */
    private List<LabelDataModel> cir230LabelList__ = null;
    /** アカウント名 */
    private String cir230accountName__ = null;
    /** 選択ラベル */
    private String cir230SortRadio__ = null;
    /** ページ表示区分 */
    private int cir230DspKbn__ = GSConstCircular.DSPKBN_PREF;

    /**
     * <p>cir230LabelCmdMode を取得します。
     * @return cir230LabelCmdMode
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230LabelCmdMode__
     */
    public int getCir230LabelCmdMode() {
        return cir230LabelCmdMode__;
    }
    /**
     * <p>cir230LabelCmdMode をセットします。
     * @param cir230LabelCmdMode cir230LabelCmdMode
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230LabelCmdMode__
     */
    public void setCir230LabelCmdMode(int cir230LabelCmdMode) {
        cir230LabelCmdMode__ = cir230LabelCmdMode;
    }
    /**
     * <p>cir230EditLabelId を取得します。
     * @return cir230EditLabelId
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230EditLabelId__
     */
    public int getCir230EditLabelId() {
        return cir230EditLabelId__;
    }
    /**
     * <p>cir230EditLabelId をセットします。
     * @param cir230EditLabelId cir230EditLabelId
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230EditLabelId__
     */
    public void setCir230EditLabelId(int cir230EditLabelId) {
        cir230EditLabelId__ = cir230EditLabelId;
    }
    /**
     * <p>cir230LabelList を取得します。
     * @return cir230LabelList
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230LabelList__
     */
    public List<LabelDataModel> getCir230LabelList() {
        return cir230LabelList__;
    }
    /**
     * <p>cir230LabelList をセットします。
     * @param cir230LabelList cir230LabelList
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230LabelList__
     */
    public void setCir230LabelList(List<LabelDataModel> cir230LabelList) {
        cir230LabelList__ = cir230LabelList;
    }
    /**
     * <p>cir230accountName を取得します。
     * @return cir230accountName
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230accountName__
     */
    public String getCir230accountName() {
        return cir230accountName__;
    }
    /**
     * <p>cir230accountName をセットします。
     * @param cir230accountName cir230accountName
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230accountName__
     */
    public void setCir230accountName(String cir230accountName) {
        cir230accountName__ = cir230accountName;
    }
    /**
     * <p>cir230SortRadio を取得します。
     * @return cir230SortRadio
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230SortRadio__
     */
    public String getCir230SortRadio() {
        return cir230SortRadio__;
    }
    /**
     * <p>cir230SortRadio をセットします。
     * @param cir230SortRadio cir230SortRadio
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230SortRadio__
     */
    public void setCir230SortRadio(String cir230SortRadio) {
        cir230SortRadio__ = cir230SortRadio;
    }
    /**
     * <p>cir230DspKbn を取得します。
     * @return cir230DspKbn
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230DspKbn__
     */
    public int getCir230DspKbn() {
        return cir230DspKbn__;
    }
    /**
     * <p>cir230DspKbn をセットします。
     * @param cir230DspKbn cir230DspKbn
     * @see jp.groupsession.v2.cir.cir230.Cir230ParamModel#cir230DspKbn__
     */
    public void setCir230DspKbn(int cir230DspKbn) {
        cir230DspKbn__ = cir230DspKbn;
    }


}

package jp.groupsession.v2.fil.fil310;

import java.util.List;

import jp.groupsession.v2.fil.fil200.Fil200ParamModel;

/**
 * <br>[機  能] ファイル管理 外貨マスタ画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil310ParamModel extends Fil200ParamModel {
    /** 削除対象外貨SID */
    private int filDelGaikaId__ = 0;
    /** 外貨リスト */
    private List<Fil310DisplayModel> gaikaList__ = null;
    /** 並び替え対象の外貨 */
    private String[] fil310SortGaika__ = null;
    /** チェックされている並び順 */
    private String fil310SortRadio__ = null;

    /**
     * <p>filDelGaikaId を取得します。
     * @return filDelGaikaId
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#filDelGaikaId__
     */
    public int getFilDelGaikaId() {
        return filDelGaikaId__;
    }
    /**
     * <p>filDelGaikaId をセットします。
     * @param filDelGaikaId filDelGaikaId
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#filDelGaikaId__
     */
    public void setFilDelGaikaId(int filDelGaikaId) {
        filDelGaikaId__ = filDelGaikaId;
    }
    /**
     * <p>gaikaList を取得します。
     * @return gaikaList
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#gaikaList__
     */
    public List<Fil310DisplayModel> getGaikaList() {
        return gaikaList__;
    }
    /**
     * <p>gaikaList をセットします。
     * @param gaikaList gaikaList
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#gaikaList__
     */
    public void setGaikaList(List<Fil310DisplayModel> gaikaList) {
        gaikaList__ = gaikaList;
    }
    /**
     * <p>fil310SortGaika を取得します。
     * @return fil310SortGaika
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#fil310SortGaika__
     */
    public String[] getFil310SortGaika() {
        return fil310SortGaika__;
    }
    /**
     * <p>fil310SortGaika をセットします。
     * @param fil310SortGaika fil310SortGaika
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#fil310SortGaika__
     */
    public void setFil310SortGaika(String[] fil310SortGaika) {
        fil310SortGaika__ = fil310SortGaika;
    }
    /**
     * <p>fil310SortRadio を取得します。
     * @return fil310SortRadio
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#fil310SortRadio__
     */
    public String getFil310SortRadio() {
        return fil310SortRadio__;
    }
    /**
     * <p>fil310SortRadio をセットします。
     * @param fil310SortRadio fil310SortRadio
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#fil310SortRadio__
     */
    public void setFil310SortRadio(String fil310SortRadio) {
        fil310SortRadio__ = fil310SortRadio;
    }
}
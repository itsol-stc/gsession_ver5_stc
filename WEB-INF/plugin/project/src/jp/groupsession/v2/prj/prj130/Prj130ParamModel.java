package jp.groupsession.v2.prj.prj130;

import java.util.ArrayList;

import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;
import jp.groupsession.v2.prj.prj020.Prj020ParamModel;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトテンプレート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj130ParamModel extends Prj020ParamModel {

    /** プロジェクトテンプレート一覧(個人) */
    private ArrayList<PrjPrjdataTmpModel> prj130TmpKojinList__ = null;
    /** プロジェクトテンプレート一覧(共有) */
    private ArrayList<PrjPrjdataTmpModel> prj130TmpKyoyuList__ = null;
    /** 処理モード */
    private String prj130cmdMode__ = null;

    /**
     * <p>prj130TmpKojinList を取得します。
     * @return prj130TmpKojinList
     */
    public ArrayList<PrjPrjdataTmpModel> getPrj130TmpKojinList() {
        return prj130TmpKojinList__;
    }
    /**
     * <p>prj130TmpKojinList をセットします。
     * @param prj130TmpKojinList prj130TmpKojinList
     */
    public void setPrj130TmpKojinList(
            ArrayList<PrjPrjdataTmpModel> prj130TmpKojinList) {
        prj130TmpKojinList__ = prj130TmpKojinList;
    }
    /**
     * <p>prj130TmpKyoyuList を取得します。
     * @return prj130TmpKyoyuList
     */
    public ArrayList<PrjPrjdataTmpModel> getPrj130TmpKyoyuList() {
        return prj130TmpKyoyuList__;
    }
    /**
     * <p>prj130TmpKyoyuList をセットします。
     * @param prj130TmpKyoyuList prj130TmpKyoyuList
     */
    public void setPrj130TmpKyoyuList(
            ArrayList<PrjPrjdataTmpModel> prj130TmpKyoyuList) {
        prj130TmpKyoyuList__ = prj130TmpKyoyuList;
    }
    /**
     * <p>prj130cmdMode を取得します。
     * @return prj130cmdMode
     */
    public String getPrj130cmdMode() {
        return prj130cmdMode__;
    }
    /**
     * <p>prj130cmdMode をセットします。
     * @param prj130cmdMode prj130cmdMode
     */
    public void setPrj130cmdMode(String prj130cmdMode) {
        prj130cmdMode__ = prj130cmdMode;
    }
}
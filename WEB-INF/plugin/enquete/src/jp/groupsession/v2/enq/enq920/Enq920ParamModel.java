package jp.groupsession.v2.enq.enq920;

import java.util.ArrayList;

import jp.groupsession.v2.enq.enq900.Enq900ParamModel;
import jp.groupsession.v2.enq.enq920.model.Enq920DelListModel;
import jp.groupsession.v2.enq.model.EnqTypeListModel;

/**
 * <br>[機  能] 管理者設定 アンケート種類設定画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq920ParamModel extends Enq900ParamModel {

    /** アンケート種類リスト */
    private ArrayList<EnqTypeListModel> enq920TypeList__ = null;
    /** 削除したSIDリスト */
    private ArrayList<Enq920DelListModel> enq920DelList__ = null;

    /**
     * <p>アンケート種類リスト を取得します。
     * @return アンケート種類リスト
     */
    public ArrayList<EnqTypeListModel> getEnq920TypeListToList() {
        return enq920TypeList__;
    }
    /**
     * <p>アンケート種類リスト をセットします。
     * @param enq920TypeList アンケート種類リスト
     */
    public void setEnq920TypeListForm(ArrayList<EnqTypeListModel> enq920TypeList) {
        enq920TypeList__ = enq920TypeList;
    }


    /**
     * <p>削除したSIDリスト を取得します。
     * @return 削除したSIDリスト
     */
    public ArrayList<Enq920DelListModel> getEnq920DelListToList() {
        return enq920DelList__;
    }

    /**
     * <p>削除したSIDリスト をセットします。
     * @param enq920DelList 削除したSIDリスト
     */
    public void setEnq920DelListForm(ArrayList<Enq920DelListModel> enq920DelList) {
        enq920DelList__ = enq920DelList;
    }

    /**
     * <br>[機  能] アンケート種類リスト を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param iIndex インデックス番号
     * @return アンケート種類リスト を戻す
     */
    public EnqTypeListModel getEnq920TypeList(int iIndex) {
        while (enq920TypeList__.size() <= iIndex) {
            enq920TypeList__.add(new EnqTypeListModel());
        }
        return enq920TypeList__.get(iIndex);
    }

    /**
     * <br>[機  能] アンケート種類リストの配列を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return アンケート種類[]
     */
    public EnqTypeListModel[] getEnq920TypeList() {
        int size = 0;
        if (enq920TypeList__ != null) {
            size = enq920TypeList__.size();
        }
        return (EnqTypeListModel[]) enq920TypeList__.toArray(new EnqTypeListModel[size]);
    }

    /**
     * <br>[機  能] アンケート種類の件数を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 件数
     */
    public int getEnq920TypeListSize() {
        return enq920TypeList__.size();
    }


    /**
     * <br>[機  能] アンケート種類削除リストを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param iIndex インデックス番号
     * @return アンケート種類削除リスト
     */
    public Enq920DelListModel getEnq920DelList(int iIndex) {
        while (enq920DelList__.size() <= iIndex) {
            enq920DelList__.add(new Enq920DelListModel());
        }
        return enq920DelList__.get(iIndex);
    }

    /**
     * <br>[機  能] アンケート種類削除リストの配列を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return アンケート種類削除[]
     */
    public Enq920DelListModel[] getEnq920DelList() {
        int size = 0;
        if (enq920DelList__ != null) {
            size = enq920DelList__.size();
        }
        return (Enq920DelListModel[]) enq920DelList__.toArray(new Enq920DelListModel[size]);
    }

    /**
     * <br>[機  能] フォームパラメータをコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setParam(Object form) {
        super.setParam(form);
        setEnq920TypeListForm(((Enq920Form) form).getEnq920TypeListToList());
        setEnq920DelListForm(((Enq920Form) form).getEnq920DelListToList());
    }

    /**
     * <br>[機  能] Modelの出力値をフォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setFormData(Object form) {
        super.setFormData(form);
        ((Enq920Form) form).setEnq920TypeListForm(getEnq920TypeListToList());
        ((Enq920Form) form).setEnq920DelListForm(getEnq920DelListToList());
    }

    /**
     * コンストラクタ
     */
    public Enq920ParamModel() {
        enq920TypeList__ = new ArrayList<EnqTypeListModel>();
        enq920DelList__ = new ArrayList<Enq920DelListModel>();
    }

}

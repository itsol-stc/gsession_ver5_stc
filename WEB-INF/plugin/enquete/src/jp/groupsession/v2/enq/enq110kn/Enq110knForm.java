package jp.groupsession.v2.enq.enq110kn;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.enq.enq110.Enq110Form;
import jp.groupsession.v2.enq.model.EnqMainListModel;

/**
 * <br>[機  能] アンケート 回答確認画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq110knForm extends Enq110Form {

    /** 遷移元画面 */
    private String enq110knSvBackScreen__ = null;

    /** 設問一覧リスト */
    private List<EnqMainListModel> enq110knQueList__ = null;

    /**
     * <p>遷移元画面 を取得します。
     * @return 遷移元画面
     */
    public String getEnq110knSvBackScreen() {
        return enq110knSvBackScreen__;
    }
    /**
     * <p>遷移元画面 をセットします。
     * @param enq110knSvBackScreen 遷移元画面
     */
    public void setEnq110knSvBackScreen(String enq110knSvBackScreen) {
        enq110knSvBackScreen__ = enq110knSvBackScreen;
    }

    /**
     * <p>設問一覧リスト  を取得します。
     * @return 設問一覧リスト
     */
    public List<EnqMainListModel> getEnq110knQueListToList() {
        return enq110knQueList__;
    }
    /**
     * <p>設問一覧リスト  をセットします。
     * @param enq110knQueList 設問一覧リスト
     */
    public void setEnq110knQueListForm(List<EnqMainListModel> enq110knQueList) {
        enq110knQueList__ = enq110knQueList;
    }

    /**
     * <br>[機  能] アンケート設問リストを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param index インデックス番号
     * @return 設問リスト を戻す
     */
    public EnqMainListModel getEnq110knQueList(int index) {
        while (enq110knQueList__.size() <= index) {
            enq110knQueList__.add(new EnqMainListModel());
        }
        return enq110knQueList__.get(index);
    }

    /**
     * <br>[機  能] アンケート設問リストの配列を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return アンケート設問[]
     */
    public EnqMainListModel[] getEnq110knQueList() {
        int size = 0;
        if (enq110knQueList__ != null) {
            size = enq110knQueList__.size();
        }
        return (EnqMainListModel[]) enq110knQueList__.toArray(new EnqMainListModel[size]);
    }

    /**
     * <br>[機  能] アンケート設問リストの件数を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 件数
     */
    public int getEnq110knQueListSize() {
        return enq110knQueList__.size();
    }

    /**
     * コンストラクタ
     */
    public Enq110knForm() {
        enq110knQueList__ = new ArrayList<EnqMainListModel>();
    }

}

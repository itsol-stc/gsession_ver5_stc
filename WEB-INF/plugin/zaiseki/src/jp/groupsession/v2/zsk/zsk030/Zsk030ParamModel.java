package jp.groupsession.v2.zsk.zsk030;

import java.util.ArrayList;

import jp.groupsession.v2.zsk.zsk020.Zsk020ParamModel;

/**
 * <br>[機  能] 在席管理 座席表設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk030ParamModel extends Zsk020ParamModel {
    /** 編集対象の座席表SID */
    private String editZifSid__;
    /** 座席表一覧リスト */
    private ArrayList<Zsk030Model> zasekiList__;

    /**
     * <p>zasekiList を取得します。
     * @return zasekiList
     */
    public ArrayList<Zsk030Model> getZasekiList() {
        return zasekiList__;
    }

    /**
     * <p>zasekiList をセットします。
     * @param zasekiList zasekiList
     */
    public void setZasekiList(ArrayList<Zsk030Model> zasekiList) {
        zasekiList__ = zasekiList;
    }

    /**
     * <p>editZifSid を取得します。
     * @return editZifSid
     */
    public String getEditZifSid() {
        return editZifSid__;
    }

    /**
     * <p>editZifSid をセットします。
     * @param editZifSid editZifSid
     */
    public void setEditZifSid(String editZifSid) {
        editZifSid__ = editZifSid;
    }

}

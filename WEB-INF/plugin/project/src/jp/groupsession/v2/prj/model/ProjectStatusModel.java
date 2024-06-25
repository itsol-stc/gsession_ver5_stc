package jp.groupsession.v2.prj.model;

import java.util.HashMap;
import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] プロジェクトに対するプロジェクト状態、TODOカテゴリ、TODO状態を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ProjectStatusModel extends AbstractModel {

    /** プロジェクト状態 */
    private List<PrjPrjstatusModel> prjStatusList__;
    /** TODOカテゴリ */
    private List<PrjTodocategoryModel> todoCateList__;
    /** TODO状態 */
    private List<PrjTodostatusModel> todoStatusList__;
    /** TODOカテゴリ更新SID <削除対象SID, 削除するカテゴリの更新先SID> */
    private HashMap<String, String> updateCate__;
    /** TODO状態更新SID <削除対象SID, 削除する状態の更新先SID> */
    private HashMap<String, String> updateStatus__;

    /**
     * <p>prjStatusList を取得します。
     * @return prjStatusList
     */
    public List<PrjPrjstatusModel> getPrjStatusList() {
        return prjStatusList__;
    }
    /**
     * <p>prjStatusList をセットします。
     * @param prjStatusList prjStatusList
     */
    public void setPrjStatusList(List<PrjPrjstatusModel> prjStatusList) {
        prjStatusList__ = prjStatusList;
    }
    /**
     * <p>todoCateList を取得します。
     * @return todoCateList
     */
    public List<PrjTodocategoryModel> getTodoCateList() {
        return todoCateList__;
    }
    /**
     * <p>todoCateList をセットします。
     * @param todoCateList todoCateList
     */
    public void setTodoCateList(List<PrjTodocategoryModel> todoCateList) {
        todoCateList__ = todoCateList;
    }
    /**
     * <p>todoStatusList を取得します。
     * @return todoStatusList
     */
    public List<PrjTodostatusModel> getTodoStatusList() {
        return todoStatusList__;
    }
    /**
     * <p>todoStatusList をセットします。
     * @param todoStatusList todoStatusList
     */
    public void setTodoStatusList(List<PrjTodostatusModel> todoStatusList) {
        todoStatusList__ = todoStatusList;
    }
    /**
     * <p>updateCate を取得します。
     * @return updateCate
     */
    public HashMap<String, String> getUpdateCate() {
        return updateCate__;
    }
    /**
     * <p>updateCate をセットします。
     * @param updateCate updateCate
     */
    public void setUpdateCate(HashMap<String, String> updateCate) {
        updateCate__ = updateCate;
    }
    /**
     * <p>updateStatus を取得します。
     * @return updateStatus
     */
    public HashMap<String, String> getUpdateStatus() {
        return updateStatus__;
    }
    /**
     * <p>updateStatus をセットします。
     * @param updateStatus updateStatus
     */
    public void setUpdateStatus(HashMap<String, String> updateStatus) {
        updateStatus__ = updateStatus;
    }

}

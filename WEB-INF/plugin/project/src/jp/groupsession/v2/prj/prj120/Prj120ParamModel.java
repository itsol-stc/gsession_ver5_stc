package jp.groupsession.v2.prj.prj120;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;

/**
 * <br>[機  能] プロジェクト管理 プロジェクト選択ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj120ParamModel extends AbstractParamModel {

    /** プロジェクトリスト */
    private List<ProjectItemModel> projectList__;

    /**
     * <p>projectList を取得します。
     * @return projectList
     */
    public List<ProjectItemModel> getProjectList() {
        return projectList__;
    }

    /**
     * <p>projectList をセットします。
     * @param projectList projectList
     */
    public void setProjectList(List<ProjectItemModel> projectList) {
        projectList__ = projectList;
    }

}

package jp.groupsession.v2.prj.main;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] プロジェクト(メイン画面表示用)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjMainForm extends AbstractGsForm {

    /** プロジェクト一覧 */
    private List<ProjectItemModel> projectList__;
    /** プロジェクトトップ画面URL */
    private String  prjTopUrl__;

    /** プロジェクトSID */
    private int prjMainPrjSid__;
    /** プロジェクトバイナリSID */
    private Long prjMainPrjBinSid__ = new Long(0);

    /** ソートキー */
    private int prjMainSort__ = GSConstProject.SORT_TODO_LIMIT_PLAN;
    /** オーダーキー */
    private int prjMainOrder__ = GSConst.ORDER_KEY_ASC;


    /**
     * @return prjTopUrl__ を戻します。
     */
    public String getPrjTopUrl() {
        return prjTopUrl__;
    }
    /**
     * @param prjTopUrl 設定する prjTopUrl__。
     */
    public void setPrjTopUrl(String prjTopUrl) {
        prjTopUrl__ = prjTopUrl;
    }
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
    /**
     * <p>prjMainOrder を取得します。
     * @return prjMainOrder
     */
    public int getPrjMainOrder() {
        return prjMainOrder__;
    }
    /**
     * <p>prjMainOrder をセットします。
     * @param prjMainOrder prjMainOrder
     */
    public void setPrjMainOrder(int prjMainOrder) {
        prjMainOrder__ = prjMainOrder;
    }
    /**
     * <p>prjMainSort を取得します。
     * @return prjMainSort
     */
    public int getPrjMainSort() {
        return prjMainSort__;
    }
    /**
     * <p>prjMainSort をセットします。
     * @param prjMainSort prjMainSort
     */
    public void setPrjMainSort(int prjMainSort) {
        prjMainSort__ = prjMainSort;
    }
    /**
     * <p>prjMainPrjBinSid を取得します。
     * @return prjMainPrjBinSid
     */
    public Long getPrjMainPrjBinSid() {
        return prjMainPrjBinSid__;
    }
    /**
     * <p>prjMainPrjBinSid をセットします。
     * @param prjMainPrjBinSid prjMainPrjBinSid
     */
    public void setPrjMainPrjBinSid(Long prjMainPrjBinSid) {
        prjMainPrjBinSid__ = prjMainPrjBinSid;
    }
    /**
     * <p>prjMainPrjSid を取得します。
     * @return prjMainPrjSid
     */
    public int getPrjMainPrjSid() {
        return prjMainPrjSid__;
    }
    /**
     * <p>prjMainPrjSid をセットします。
     * @param prjMainPrjSid prjMainPrjSid
     */
    public void setPrjMainPrjSid(int prjMainPrjSid) {
        prjMainPrjSid__ = prjMainPrjSid;
    }

}

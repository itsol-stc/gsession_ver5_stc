package jp.groupsession.v2.prj.prj210;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.prj010.Prj010ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] プロジェクト管理 プロジェクト選択画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj210ParamModel extends Prj010ParamModel {

    /** 参加プロジェクト公開プロジェクト */
    private String prj210KoukaiKbn__ = "-1";
    /** 未完プロジェクト 完了プロジェクト */
    private String prj210Progress__ = "-1";
    /** 親画面ID */
    private String prj210parentPageId__ = null;

    /** プロジェクトリスト **/
    private List<ProjectItemModel> prj210ProjectList__ = null;

    /** プロジェクトSID */
    private String[] prj210ProjectSid__ = null;
    /** プロジェクト名 */
    private String prj210ProjectName__ = null;

    /** 選択された会社 */
    private String prj210selectProject__ = null;

    /** ページ */
    private int prj210page__ = 0;
    /** ページ(画面上部) */
    private int prj210pageTop__ = 0;
    /** ページ(画面下部) */
    private int prj210pageBottom__ = 0;
    /** ページコンボ */
    private List<LabelValueBean> pageCmbList__ = null;

    /** ソートキー */
    private int prj210sort__ = GSConstProject.SORT_PRJECT_START;
    /** オーダーキー */
    private int prj210order__ = GSConst.ORDER_KEY_DESC;

    /**
     * @return prj210page
     */
    public int getPrj210page() {
        return prj210page__;
    }
    /**
     * @param prj210page 設定する prj210page
     */
    public void setPrj210page(int prj210page) {
        prj210page__ = prj210page;
    }
    /**
     * @return prj210pageBottom
     */
    public int getPrj210pageBottom() {
        return prj210pageBottom__;
    }
    /**
     * @param prj210pageBottom 設定する prj210pageBottom
     */
    public void setPrj210pageBottom(int prj210pageBottom) {
        prj210pageBottom__ = prj210pageBottom;
    }
    /**
     * @return prj210pageTop
     */
    public int getPrj210pageTop() {
        return prj210pageTop__;
    }
    /**
     * @param prj210pageTop 設定する prj210pageTop
     */
    public void setPrj210pageTop(int prj210pageTop) {
        prj210pageTop__ = prj210pageTop;
    }
    /**
     * @return prj210parentPageId
     */
    public String getPrj210parentPageId() {
        return prj210parentPageId__;
    }
    /**
     * @param prj210parentPageId 設定する prj210parentPageId
     */
    public void setPrj210parentPageId(String prj210parentPageId) {
        prj210parentPageId__ = prj210parentPageId;
    }
    /**
     * @return prj210ProjectList
     */
    public List<ProjectItemModel> getPrj210ProjectList() {
        return prj210ProjectList__;
    }
    /**
     * @param prj210ProjectList 設定する prj210ProjectList
     */
    public void setPrj210ProjectList(List<ProjectItemModel> prj210ProjectList) {
        prj210ProjectList__ = prj210ProjectList;
    }
    /**
     * @return prj210ProjectName
     */
    public String getPrj210ProjectName() {
        return prj210ProjectName__;
    }
    /**
     * @param prj210ProjectName 設定する prj210ProjectName
     */
    public void setPrj210ProjectName(String prj210ProjectName) {
        prj210ProjectName__ = prj210ProjectName;
    }
    /**
     * @return prj210selectProject
     */
    public String getPrj210selectProject() {
        return prj210selectProject__;
    }
    /**
     * @param prj210selectProject 設定する prj210selectProject
     */
    public void setPrj210selectProject(String prj210selectProject) {
        prj210selectProject__ = prj210selectProject;
    }
    /**
     * @return prj210KoukaiKbn
     */
    public String getPrj210KoukaiKbn() {
        return prj210KoukaiKbn__;
    }
    /**
     * @param prj210KoukaiKbn 設定する prj210KoukaiKbn
     */
    public void setPrj210KoukaiKbn(String prj210KoukaiKbn) {
        prj210KoukaiKbn__ = prj210KoukaiKbn;
    }
    /**
     * @return prj210Progress
     */
    public String getPrj210Progress() {
        return prj210Progress__;
    }
    /**
     * @param prj210Progress 設定する prj210Progress
     */
    public void setPrj210Progress(String prj210Progress) {
        prj210Progress__ = prj210Progress;
    }
    /**
     * @return prj210order
     */
    public int getPrj210order() {
        return prj210order__;
    }
    /**
     * @param prj210order 設定する prj210order
     */
    public void setPrj210order(int prj210order) {
        prj210order__ = prj210order;
    }
    /**
     * @return prj210sort
     */
    public int getPrj210sort() {
        return prj210sort__;
    }
    /**
     * @param prj210sort 設定する prj210sort
     */
    public void setPrj210sort(int prj210sort) {
        prj210sort__ = prj210sort;
    }
    /**
     * @return prj210ProjectSid
     */
    public String[] getPrj210ProjectSid() {
        return prj210ProjectSid__;
    }
    /**
     * @param prj210ProjectSid 設定する prj210ProjectSid
     */
    public void setPrj210ProjectSid(String[] prj210ProjectSid) {
        prj210ProjectSid__ = prj210ProjectSid;
    }
    /**
     * @return pageCmbList
     */
    public List<LabelValueBean> getPageCmbList() {
        return pageCmbList__;
    }
    /**
     * @param pageCmbList 設定する pageCmbList
     */
    public void setPageCmbList(List<LabelValueBean> pageCmbList) {
        pageCmbList__ = pageCmbList;
    }
}
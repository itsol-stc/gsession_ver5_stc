package jp.groupsession.v2.prj.prj090;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.prj080.Prj080Form;

/**
 * <br>[機  能] プロジェクト管理 個人設定 表示件数設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj090Form extends Prj080Form {

    //入力項目
    /** プロジェクト表示件数 */
    private int prj090prjViewCnt__ = 10;
    /** TODO表示件数 */
    private int prj090todoViewCnt__ = 10;
    /**スケジュール表示区分 0:表示する 1:表示しない */
    private int prj090SchDsp__ = GSConstProject.DSP_TODO_SHOW;

    //表示項目
    /** 表示件数コンボ */
    private List<LabelValueBean> cntList__;

    /**
     * <p>prj090prjViewCnt を取得します。
     * @return prj090prjViewCnt
     */
    public int getPrj090prjViewCnt() {
        return prj090prjViewCnt__;
    }
    /**
     * <p>prj090prjViewCnt をセットします。
     * @param prj090prjViewCnt prj090prjViewCnt
     */
    public void setPrj090prjViewCnt(int prj090prjViewCnt) {
        prj090prjViewCnt__ = prj090prjViewCnt;
    }
    /**
     * <p>prj090todoViewCnt を取得します。
     * @return prj090todoViewCnt
     */
    public int getPrj090todoViewCnt() {
        return prj090todoViewCnt__;
    }
    /**
     * <p>prj090todoViewCnt をセットします。
     * @param prj090todoViewCnt prj090todoViewCnt
     */
    public void setPrj090todoViewCnt(int prj090todoViewCnt) {
        prj090todoViewCnt__ = prj090todoViewCnt;
    }
    /**
     * <p>cntList を取得します。
     * @return cntList
     */
    public List<LabelValueBean> getCntList() {
        return cntList__;
    }
    /**
     * <p>cntList をセットします。
     * @param cntList cntList
     */
    public void setCntList(List<LabelValueBean> cntList) {
        cntList__ = cntList;
    }

    /**
     * <p>prj090SchDsp を取得します。
     * @return prj090SchDsp
     */
    public int getPrj090SchDsp() {
        return prj090SchDsp__;
    }

    /**
     * <p>prj090SchDsp をセットします。
     * @param prj090SchDsp prj090SchDsp
     */
    public void setPrj090SchDsp(int prj090SchDsp) {
        prj090SchDsp__ = prj090SchDsp;
    }

}

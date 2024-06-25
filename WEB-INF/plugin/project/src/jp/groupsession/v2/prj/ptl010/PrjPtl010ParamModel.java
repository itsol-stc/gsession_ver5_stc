package jp.groupsession.v2.prj.ptl010;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] プロジェクト管理 ポートレット TODO一覧のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjPtl010ParamModel extends AbstractParamModel {

    /** 表示プロジェクトSID */
    private int prjPtl010PrjSid__ = 0;

    /** ＴＯＤＯ[内訳] 完了件数 */
    private int todoKanryoCnt__ = 0;
    /** ＴＯＤＯ[内訳] 進行中件数 */
    private int todoSinkotyuCnt__ = 0;
    /** ＴＯＤＯ[内訳] 未完了件数 */
    private int todoMikanryoCnt__ = 0;

    /** プロジェクト名 */
    private String prjPtl010prjName__ = "";

    /**
     * @return todoKanryoCnt
     */
    public int getTodoKanryoCnt() {
        return todoKanryoCnt__;
    }
    /**
     * @param todoKanryoCnt セットする todoKanryoCnt
     */
    public void setTodoKanryoCnt(int todoKanryoCnt) {
        todoKanryoCnt__ = todoKanryoCnt;
    }
    /**
     * @return todoSinkotyuCnt
     */
    public int getTodoSinkotyuCnt() {
        return todoSinkotyuCnt__;
    }
    /**
     * @param todoSinkotyuCnt セットする todoSinkotyuCnt
     */
    public void setTodoSinkotyuCnt(int todoSinkotyuCnt) {
        todoSinkotyuCnt__ = todoSinkotyuCnt;
    }
    /**
     * @return todoMikanryoCnt
     */
    public int getTodoMikanryoCnt() {
        return todoMikanryoCnt__;
    }
    /**
     * @param todoMikanryoCnt セットする todoMikanryoCnt
     */
    public void setTodoMikanryoCnt(int todoMikanryoCnt) {
        todoMikanryoCnt__ = todoMikanryoCnt;
    }
    /**
     * <p>prjPtl010PrjSid を取得します。
     * @return prjPtl010PrjSid
     */
    public int getPrjPtl010PrjSid() {
        return prjPtl010PrjSid__;
    }
    /**
     * <p>prjPtl010PrjSid をセットします。
     * @param prjPtl010PrjSid prjPtl010PrjSid
     */
    public void setPrjPtl010PrjSid(int prjPtl010PrjSid) {
        prjPtl010PrjSid__ = prjPtl010PrjSid;
    }
    /**
     * <p>prjPtl010prjName を取得します。
     * @return prjPtl010prjName
     */
    public String getPrjPtl010prjName() {
        return prjPtl010prjName__;
    }
    /**
     * <p>prjPtl010prjName をセットします。
     * @param prjPtl010prjName prjPtl010prjName
     */
    public void setPrjPtl010prjName(String prjPtl010prjName) {
        prjPtl010prjName__ = prjPtl010prjName;
    }

}
package jp.groupsession.v2.prj.prj160kn;

import java.util.HashMap;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;

/**
 * <br>[機  能] プロジェクト管理 TODOインポート確認画面 プロジェクト情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class TodoCsvImportConstModel extends AbstractModel {

    /** プロジェクトSID */
    private int prjSid__ = 0;
    /** マイプロジェクト区分 */
    private int prjMyKbn__ = 0;
    /** ユーザSID */
    private int usrSid__ = 0;
    /** カテゴリマッピング */
    private HashMap<String, PrjTodocategoryModel> categoryMap__ = null;
    /** 状態マッピング */
    private HashMap<String, PrjTodostatusModel> statusMap__ = null;
    /** メンバーマッピング */
    private HashMap<String, PrjMembersModel> memberMap__ = null;

    /**
     * <p>prjSid を取得します。
     * @return prjSid
     */
    public int getPrjSid() {
        return prjSid__;
    }
    /**
     * <p>prjSid をセットします。
     * @param prjSid prjSid
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }
    /**
     * <p>prjMyKbn を取得します。
     * @return prjMyKbn
     */
    public int getPrjMyKbn() {
        return prjMyKbn__;
    }
    /**
     * <p>prjMyKbn をセットします。
     * @param prjMyKbn prjMyKbn
     */
    public void setPrjMyKbn(int prjMyKbn) {
        prjMyKbn__ = prjMyKbn;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>categoryMap を取得します。
     * @return categoryMap
     */
    public HashMap<String, PrjTodocategoryModel> getCategoryMap() {
        return categoryMap__;
    }
    /**
     * <p>categoryMap をセットします。
     * @param categoryMap categoryMap
     */
    public void setCategoryMap(HashMap<String, PrjTodocategoryModel> categoryMap) {
        categoryMap__ = categoryMap;
    }
    /**
     * <p>statusMap を取得します。
     * @return statusMap
     */
    public HashMap<String, PrjTodostatusModel> getStatusMap() {
        return statusMap__;
    }
    /**
     * <p>statusMap をセットします。
     * @param statusMap statusMap
     */
    public void setStatusMap(HashMap<String, PrjTodostatusModel> statusMap) {
        statusMap__ = statusMap;
    }
    /**
     * <p>memberMap を取得します。
     * @return memberMap
     */
    public HashMap<String, PrjMembersModel> getMemberMap() {
        return memberMap__;
    }
    /**
     * <p>memberMap をセットします。
     * @param memberMap memberMap
     */
    public void setMemberMap(HashMap<String, PrjMembersModel> memberMap) {
        memberMap__ = memberMap;
    }

}
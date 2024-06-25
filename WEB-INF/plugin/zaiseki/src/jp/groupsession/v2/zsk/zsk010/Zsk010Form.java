package jp.groupsession.v2.zsk.zsk010;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.zsk.GSConstZaiseki;

/**
 * <br>[機  能] 在席管理 在席状況画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk010Form extends AbstractGsForm {
    /** 処理モード */
    private String cmd__ = null;
    /** 処理モード */
    private String adminKbn__ = null;

    /** 自動リロード時間 */
    private int zsk010Reload__ = GSConstZaiseki.AUTO_RELOAD_10MIN;

    /** 表示する座席表SID*/
    private String selectZifSid__;
    /** 在席状況*/
    private String uioStatus__;
    /** 在席状況コメント*/
    private String uioStatusBiko__;
    /** 在席状況DB登録値*/
    private String uioStatusDb__;

    /** 在席コンボ */
    private List<LabelValueBean> statusLabelList__ = null;
    /** 座席表コンボ */
    private List<LabelValueBean> zifLabelList__ = null;
    /** 座席表所属ユーザ一覧*/
    private ArrayList<UserSearchModel> userList__;
    /** 在席状況を更新するユーザSID */
    private String uioUpdateUsrSid__;
    /** 更新する在席状況 */
    private String uioUpdateStatus__;

    /** ソート項目*/
    private String sortKey__;
    /** ソート順*/
    private String orderKey__;
    /** 表示項目KEYパラメータ*/
    private ArrayList<String> elementKeyList__;
    /** 座席表イメージファイルSID */
    private Long zsk010binSid__ = new Long(-1);
    /** 座席表イメージ名*/
    private String imageFileName__ = null;

    //スケジュール月間へ遷移用
    /** ユーザSID */
    private String sch010SelectUsrSid__;
    /** ユーザ区分 */
    private String sch010SelectUsrKbn__;

    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** ショートメールプラグイン利用可:0・不可:1*/
    private int smailUseOk__ = GSConst.PLUGIN_USE;


    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }
    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
    /**
     * <p>uioUpdateStatus を取得します。
     * @return uioUpdateStatus
     */
    public String getUioUpdateStatus() {
        return uioUpdateStatus__;
    }
    /**
     * <p>uioUpdateStatus をセットします。
     * @param uioUpdateStatus uioUpdateStatus
     */
    public void setUioUpdateStatus(String uioUpdateStatus) {
        uioUpdateStatus__ = uioUpdateStatus;
    }
    /**
     * <p>uioUpdateUsrSid を取得します。
     * @return uioUpdateUsrSid
     */
    public String getUioUpdateUsrSid() {
        return uioUpdateUsrSid__;
    }
    /**
     * <p>uioUpdateUsrSid をセットします。
     * @param uioUpdateUsrSid uioUpdateUsrSid
     */
    public void setUioUpdateUsrSid(String uioUpdateUsrSid) {
        uioUpdateUsrSid__ = uioUpdateUsrSid;
    }
    /**
     * <p>adminKbn を取得します。
     * @return adminKbn
     */
    public String getAdminKbn() {
        return adminKbn__;
    }
    /**
     * <p>adminKbn をセットします。
     * @param adminKbn adminKbn
     */
    public void setAdminKbn(String adminKbn) {
        adminKbn__ = adminKbn;
    }
    /**
     * <p>elementKeyList を取得します。
     * @return elementKeyList
     */
    public ArrayList<String> getElementKeyList() {
        return elementKeyList__;
    }
    /**
     * <p>elementKeyList をセットします。
     * @param elementKeyList elementKeyList
     */
    public void setElementKeyList(ArrayList<String> elementKeyList) {
        elementKeyList__ = elementKeyList;
    }
    /**
     * <p>sch010SelectUsrKbn を取得します。
     * @return sch010SelectUsrKbn
     */
    public String getSch010SelectUsrKbn() {
        return sch010SelectUsrKbn__;
    }
    /**
     * <p>sch010SelectUsrKbn をセットします。
     * @param sch010SelectUsrKbn sch010SelectUsrKbn
     */
    public void setSch010SelectUsrKbn(String sch010SelectUsrKbn) {
        sch010SelectUsrKbn__ = sch010SelectUsrKbn;
    }
    /**
     * <p>sch010SelectUsrSid を取得します。
     * @return sch010SelectUsrSid
     */
    public String getSch010SelectUsrSid() {
        return sch010SelectUsrSid__;
    }
    /**
     * <p>sch010SelectUsrSid をセットします。
     * @param sch010SelectUsrSid sch010SelectUsrSid
     */
    public void setSch010SelectUsrSid(String sch010SelectUsrSid) {
        sch010SelectUsrSid__ = sch010SelectUsrSid;
    }
    /**
     * <p>cmd を取得します。
     * @return cmd
     */
    public String getCmd() {
        return cmd__;
    }
    /**
     * <p>cmd をセットします。
     * @param cmd cmd
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }
    /**
     * <p>uioStatusDb を取得します。
     * @return uioStatusDb
     */
    public String getUioStatusDb() {
        return uioStatusDb__;
    }
    /**
     * <p>uioStatusDb をセットします。
     * @param uioStatusDb uioStatusDb
     */
    public void setUioStatusDb(String uioStatusDb) {
        uioStatusDb__ = uioStatusDb;
    }
    /**
     * <p>imageFileName を取得します。
     * @return imageFileName
     */
    public String getImageFileName() {
        return imageFileName__;
    }
    /**
     * <p>imageFileName をセットします。
     * @param imageFileName imageFileName
     */
    public void setImageFileName(String imageFileName) {
        imageFileName__ = imageFileName;
    }
    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public String getOrderKey() {
        return orderKey__;
    }
    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(String orderKey) {
        orderKey__ = orderKey;
    }
    /**
     * <p>selectZifSid を取得します。
     * @return selectZifSid
     */
    public String getSelectZifSid() {
        return selectZifSid__;
    }
    /**
     * <p>selectZifSid をセットします。
     * @param selectZifSid selectZifSid
     */
    public void setSelectZifSid(String selectZifSid) {
        selectZifSid__ = selectZifSid;
    }
    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public String getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(String sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>statusLabelList を取得します。
     * @return statusLabelList
     */
    public List<LabelValueBean> getStatusLabelList() {
        return statusLabelList__;
    }
    /**
     * <p>statusLabelList をセットします。
     * @param statusLabelList statusLabelList
     */
    public void setStatusLabelList(List<LabelValueBean> statusLabelList) {
        statusLabelList__ = statusLabelList;
    }
    /**
     * <p>uioStatus を取得します。
     * @return uioStatus
     */
    public String getUioStatus() {
        return uioStatus__;
    }
    /**
     * <p>uioStatus をセットします。
     * @param uioStatus uioStatus
     */
    public void setUioStatus(String uioStatus) {
        uioStatus__ = uioStatus;
    }
    /**
     * <p>uioStatusBiko を取得します。
     * @return uioStatusBiko
     */
    public String getUioStatusBiko() {
        return uioStatusBiko__;
    }
    /**
     * <p>uioStatusBiko をセットします。
     * @param uioStatusBiko uioStatusBiko
     */
    public void setUioStatusBiko(String uioStatusBiko) {
        uioStatusBiko__ = uioStatusBiko;
    }
    /**
     * <p>userList を取得します。
     * @return userList
     */
    public ArrayList<UserSearchModel> getUserList() {
        return userList__;
    }
    /**
     * <p>userList をセットします。
     * @param userList userList
     */
    public void setUserList(ArrayList<UserSearchModel> userList) {
        userList__ = userList;
    }
    /**
     * <p>zifLabelList を取得します。
     * @return zifLabelList
     */
    public List<LabelValueBean> getZifLabelList() {
        return zifLabelList__;
    }
    /**
     * <p>zifLabelList をセットします。
     * @param zifLabelList zifLabelList
     */
    public void setZifLabelList(List<LabelValueBean> zifLabelList) {
        zifLabelList__ = zifLabelList;
    }
    /**
     * <p>zsk010Reload を取得します。
     * @return zsk010Reload
     */
    public int getZsk010Reload() {
        return zsk010Reload__;
    }
    /**
     * <p>zsk010Reload をセットします。
     * @param zsk010Reload zsk010Reload
     */
    public void setZsk010Reload(int zsk010Reload) {
        zsk010Reload__ = zsk010Reload;
    }
    /**
     * <p>zsk010binSid を取得します。
     * @return zsk010binSid
     */
    public Long getZsk010binSid() {
        return zsk010binSid__;
    }
    /**
     * <p>zsk010binSid をセットします。
     * @param zsk010binSid zsk010binSid
     */
    public void setZsk010binSid(Long zsk010binSid) {
        zsk010binSid__ = zsk010binSid;
    }
    /**
     * <p>smailUseOk を取得します。
     * @return smailUseOk
     */
    public int getSmailUseOk() {
        return smailUseOk__;
    }
    /**
     * <p>smailUseOk をセットします。
     * @param smailUseOk smailUseOk
     */
    public void setSmailUseOk(int smailUseOk) {
        smailUseOk__ = smailUseOk;
    }

}

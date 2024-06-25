package jp.groupsession.v2.prj.model;

import java.util.HashMap;
import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] プロジェクト管理に関するメール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjSmailModel extends AbstractModel {

    /** 処理モード */
    private String cmdMode__;
    /** プロジェクト名称 */
    private String projectName__;
    /** 管理番号 */
    private String kanriNo__;
    /** タイトル */
    private String title__;
    /** 重要度 */
    private String priority__;
    /** 担当者 */
    private List<UsrLabelValueBean> tanto__;
    /** 担当者マッピング */
    private HashMap<Integer, PrjTodomemberModel> tantoMap__;
    /** 状態 */
    private String status__;
    /** 登録(更新)者 */
    private String updUserName__;
    /** 内容 */
    private String naiyo__;
    /** 添付ファイル */
    private List<LabelValueBean> tmpFiles__;
    /** 予定 日付 */
    private String yoteiDate__;
    /** 予定 工数 */
    private String yoteiKosu__;
    /** 実績 日付 */
    private String zissekiDate__;
    /** 実績 工数 */
    private String zissekiKosu__;
    /** 送信先 */
    private List<Integer> userSids__;
    /** 状態変更理由 */
    private String history__;
    /** URL */
    private String todoUrl__;

    /**
     * <p>todoUrl を取得します。
     * @return todoUrl
     */
    public String getTodoUrl() {
        return todoUrl__;
    }
    /**
     * <p>todoUrl をセットします。
     * @param todoUrl todoUrl
     */
    public void setTodoUrl(String todoUrl) {
        todoUrl__ = todoUrl;
    }
    /**
     * <p>histry を取得します。
     * @return histry
     */
    public String getHistory() {
        return history__;
    }
    /**
     * <p>histry をセットします。
     * @param histry histry
     */
    public void setHistory(String histry) {
        history__ = histry;
    }
    /**
     * <p>projectName を取得します。
     * @return projectName
     */
    public String getProjectName() {
        return projectName__;
    }
    /**
     * <p>projectName をセットします。
     * @param projectName projectName
     */
    public void setProjectName(String projectName) {
        projectName__ = projectName;
    }
    /**
     * <p>tantoMap を取得します。
     * @return tantoMap
     */
    public HashMap<Integer, PrjTodomemberModel> getTantoMap() {
        return tantoMap__;
    }
    /**
     * <p>tantoMap をセットします。
     * @param tantoMap tantoMap
     */
    public void setTantoMap(HashMap<Integer, PrjTodomemberModel> tantoMap) {
        tantoMap__ = tantoMap;
    }
    /**
     * <p>userSids を取得します。
     * @return userSids
     */
    public List<Integer> getUserSids() {
        return userSids__;
    }
    /**
     * <p>userSids をセットします。
     * @param userSids userSids
     */
    public void setUserSids(List<Integer> userSids) {
        userSids__ = userSids;
    }
    /**
     * <p>yoteiKosu を取得します。
     * @return yoteiKosu
     */
    public String getYoteiKosu() {
        return yoteiKosu__;
    }
    /**
     * <p>yoteiKosu をセットします。
     * @param yoteiKosu yoteiKosu
     */
    public void setYoteiKosu(String yoteiKosu) {
        yoteiKosu__ = yoteiKosu;
    }
    /**
     * <p>zissekiKosu を取得します。
     * @return zissekiKosu
     */
    public String getZissekiKosu() {
        return zissekiKosu__;
    }
    /**
     * <p>zissekiKosu をセットします。
     * @param zissekiKosu zissekiKosu
     */
    public void setZissekiKosu(String zissekiKosu) {
        zissekiKosu__ = zissekiKosu;
    }
    /**
     * <p>cmdMode を取得します。
     * @return cmdMode
     */
    public String getCmdMode() {
        return cmdMode__;
    }
    /**
     * <p>cmdMode をセットします。
     * @param cmdMode cmdMode
     */
    public void setCmdMode(String cmdMode) {
        cmdMode__ = cmdMode;
    }
    /**
     * <p>naiyo を取得します。
     * @return naiyo
     */
    public String getNaiyo() {
        return naiyo__;
    }
    /**
     * <p>naiyo をセットします。
     * @param naiyo naiyo
     */
    public void setNaiyo(String naiyo) {
        naiyo__ = naiyo;
    }
    /**
     * <p>priority を取得します。
     * @return priority
     */
    public String getPriority() {
        return priority__;
    }
    /**
     * <p>priority をセットします。
     * @param priority priority
     */
    public void setPriority(String priority) {
        priority__ = priority;
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public String getStatus() {
        return status__;
    }
    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setStatus(String status) {
        status__ = status;
    }
    /**
     * <p>tanto を取得します。
     * @return tanto
     */
    public List<UsrLabelValueBean> getTanto() {
        return tanto__;
    }
    /**
     * <p>tanto をセットします。
     * @param tanto tanto
     */
    public void setTanto(List<UsrLabelValueBean> tanto) {
        tanto__ = tanto;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>tmpFiles を取得します。
     * @return tmpFiles
     */
    public List<LabelValueBean> getTmpFiles() {
        return tmpFiles__;
    }
    /**
     * <p>tmpFiles をセットします。
     * @param tmpFiles tmpFiles
     */
    public void setTmpFiles(List<LabelValueBean> tmpFiles) {
        tmpFiles__ = tmpFiles;
    }
    /**
     * <p>updUserName を取得します。
     * @return updUserName
     */
    public String getUpdUserName() {
        return updUserName__;
    }
    /**
     * <p>updUserName をセットします。
     * @param updUserName updUserName
     */
    public void setUpdUserName(String updUserName) {
        updUserName__ = updUserName;
    }
    /**
     * <p>kanriNo を取得します。
     * @return kanriNo
     */
    public String getKanriNo() {
        return kanriNo__;
    }
    /**
     * <p>kanriNo をセットします。
     * @param kanriNo kanriNo
     */
    public void setKanriNo(String kanriNo) {
        kanriNo__ = kanriNo;
    }
    /**
     * <p>yoteiDate を取得します。
     * @return yoteiDate
     */
    public String getYoteiDate() {
        return yoteiDate__;
    }
    /**
     * <p>yoteiDate をセットします。
     * @param yoteiDate yoteiDate
     */
    public void setYoteiDate(String yoteiDate) {
        yoteiDate__ = yoteiDate;
    }
    /**
     * <p>zissekiDate を取得します。
     * @return zissekiDate
     */
    public String getZissekiDate() {
        return zissekiDate__;
    }
    /**
     * <p>zissekiDate をセットします。
     * @param zissekiDate zissekiDate
     */
    public void setZissekiDate(String zissekiDate) {
        zissekiDate__ = zissekiDate;
    }
}
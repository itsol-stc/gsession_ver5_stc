package jp.groupsession.v2.prj.model;

import java.io.Serializable;

/**
 * <p>PRJ_ADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PrjSmailParamModel implements Serializable {

    /** 処理モード */
    private String cmdMode__;
    /** プロジェクトSID */
    private int prjSid__;
    /** TODO_SID */
    private int todoSid__;
    /** 登録(更新)者ユーザSID */
    private int usrSid__;
    /** 送信先 */
    private int target__;
    /** 状態変更理由 */
    private String history__;
    /** ルートパス */
    private String appRoot__;
    /** テンポラリパス */
    private String tempDir__;
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
     * <p>todoSid を取得します。
     * @return todoSid
     */
    public int getTodoSid() {
        return todoSid__;
    }
    /**
     * <p>todoSid をセットします。
     * @param todoSid todoSid
     */
    public void setTodoSid(int todoSid) {
        todoSid__ = todoSid;
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
     * <p>target を取得します。
     * @return target
     */
    public int getTarget() {
        return target__;
    }
    /**
     * <p>target をセットします。
     * @param target target
     */
    public void setTarget(int target) {
        target__ = target;
    }
    /**
     * <p>history を取得します。
     * @return history
     */
    public String getHistory() {
        return history__;
    }
    /**
     * <p>history をセットします。
     * @param history history
     */
    public void setHistory(String history) {
        history__ = history;
    }
    /**
     * <p>appRoot を取得します。
     * @return appRoot
     */
    public String getAppRoot() {
        return appRoot__;
    }
    /**
     * <p>appRoot をセットします。
     * @param appRoot appRoot
     */
    public void setAppRoot(String appRoot) {
        appRoot__ = appRoot;
    }
    /**
     * <p>tempDir を取得します。
     * @return tempDir
     */
    public String getTempDir() {
        return tempDir__;
    }
    /**
     * <p>tempDir をセットします。
     * @param tempDir tempDir
     */
    public void setTempDir(String tempDir) {
        tempDir__ = tempDir;
    }

}

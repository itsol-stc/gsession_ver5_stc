package jp.groupsession.v2.rsv.rsv110;

import java.sql.Connection;

import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] ショートメール送信に必要な情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv110SMailModel extends AbstractModel {

    /** コネクション */
    private Connection con__;
    /** リクエスト情報 */
    private RequestModel reqMdl__;
    /** 施設予約SID */
    private int rsySid__;
    /** MlCountMtController */
    private MlCountMtController cntCon__;
    /** セッションユーザSID */
    private int userSid__;
    /** アプリケーションのルートパス */
    private String appRootPath__;
    /** テンポラリディレクトリ */
    private String tempDir__;
    /** PluginConfig */
    private PluginConfig pluginConfig__;
    /**e 処理モード */
    private int procMode__;
    /** 削除モード */
    private int delMode__;
    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>rsySid を取得します。
     * @return rsySid
     */
    public int getRsySid() {
        return rsySid__;
    }
    /**
     * <p>rsySid をセットします。
     * @param rsySid rsySid
     */
    public void setRsySid(int rsySid) {
        rsySid__ = rsySid;
    }
    /**
     * <p>cntCon を取得します。
     * @return cntCon
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }
    /**
     * <p>cntCon をセットします。
     * @param cntCon cntCon
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>appRootPath を取得します。
     * @return appRootPath
     */
    public String getAppRootPath() {
        return appRootPath__;
    }
    /**
     * <p>appRootPath をセットします。
     * @param appRootPath appRootPath
     */
    public void setAppRootPath(String appRootPath) {
        appRootPath__ = appRootPath;
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
    /**
     * <p>pluginConfig を取得します。
     * @return pluginConfig
     */
    public PluginConfig getPluginConfig() {
        return pluginConfig__;
    }
    /**
     * <p>pluginConfig をセットします。
     * @param pluginConfig pluginConfig
     */
    public void setPluginConfig(PluginConfig pluginConfig) {
        pluginConfig__ = pluginConfig;
    }
    /**
     * <p>procMode を取得します。
     * @return procMode
     */
    public int getProcMode() {
        return procMode__;
    }
    /**
     * <p>procMode をセットします。
     * @param procMode procMode
     */
    public void setProcMode(int procMode) {
        procMode__ = procMode;
    }
    /**
     * <p>delMode を取得します。
     * @return delMode
     */
    public int getDelMode() {
        return delMode__;
    }
    /**
     * <p>delMode をセットします。
     * @param delMode delMode
     */
    public void setDelMode(int delMode) {
        delMode__ = delMode;
    }
}
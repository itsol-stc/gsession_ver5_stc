package jp.groupsession.v2.api.webmail.importmail;

import java.io.File;
import java.sql.Connection;

import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] WEBメールインポート処理に必要な情報を格納するModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiImportMailModel {
    /** コネクション */
    Connection con__;
    /** リクエストモデル */
    RequestModel reqMdl__;
    /** MessageResources */
    MessageResources msgResource__;
    /** MlCountMtController */
    MlCountMtController mtCon__;
    /** フォーム */
    ApiImportMailForm form__;
    /** ユーザSID */
    int userSid__;
    /** アプリケーションルートパス */
    String appRootPath__;
    /** テンポラリディレクトリ */
    String tempDir__;
    /** インポートファイルのパス */
    File impFilePath__;
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
     * <p>msgResource を取得します。
     * @return msgResource
     */
    public MessageResources getMsgResource() {
        return msgResource__;
    }
    /**
     * <p>msgResource をセットします。
     * @param msgResource msgResource
     */
    public void setMsgResource(MessageResources msgResource) {
        msgResource__ = msgResource;
    }
    /**
     * <p>mtCon を取得します。
     * @return mtCon
     */
    public MlCountMtController getMtCon() {
        return mtCon__;
    }
    /**
     * <p>mtCon をセットします。
     * @param mtCon mtCon
     */
    public void setMtCon(MlCountMtController mtCon) {
        mtCon__ = mtCon;
    }
    /**
     * <p>form を取得します。
     * @return form
     */
    public ApiImportMailForm getForm() {
        return form__;
    }
    /**
     * <p>form をセットします。
     * @param form form
     */
    public void setForm(ApiImportMailForm form) {
        form__ = form;
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
     * <p>impFilePath を取得します。
     * @return impFilePath
     */
    public File getImpFilePath() {
        return impFilePath__;
    }
    /**
     * <p>impFilePath をセットします。
     * @param impFilePath impFilePath
     */
    public void setImpFilePath(File impFilePath) {
        impFilePath__ = impFilePath;
    }
}

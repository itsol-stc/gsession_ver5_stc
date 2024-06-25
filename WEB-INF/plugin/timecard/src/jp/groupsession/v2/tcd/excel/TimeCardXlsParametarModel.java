package jp.groupsession.v2.tcd.excel;

import java.sql.Connection;
import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.tcd.tcd010.Tcd010Model;

/**
 * <br>[機  能] 勤務表作成時のパラメータを格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考] 出力を行うには全てのパラメータ設定を必須とします。
 *
 * @author JTS
 */
public class TimeCardXlsParametarModel extends AbstractModel {

    /** 出力Book名称 */
    private String outBookName__ = null;
    /** 出力Book名称（テンポラリ上） */
    private String outBookTmpName__ = null;
    /** 出力対象年 */
    private int targetYear__ = -1;
    /** 出力対象月 */
    private int targetMonth__ = -1;
    /** 出力対象ユーザSID */
    private int targetUserSid__ = -1;
    /** 1ヶ月分のタイムカード情報 */
    List<Tcd010Model> timeCardInfoList__ = null;
    /** アプリケーションルートパス */
    private String appRootPath__ = null;
    /** テンポラリディレクトリパス */
    private String outTempDir__ = null;
    /** コネクション */
    Connection con__ = null;

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
     * <p>outTempDir を取得します。
     * @return outTempDir
     */
    public String getOutTempDir() {
        return outTempDir__;
    }
    /**
     * <p>outTempDir をセットします。
     * @param outTempDir outTempDir
     */
    public void setOutTempDir(String outTempDir) {
        outTempDir__ = outTempDir;
    }
    /**
     * <p>targetMonth を取得します。
     * @return targetMonth
     */
    public int getTargetMonth() {
        return targetMonth__;
    }
    /**
     * <p>targetMonth をセットします。
     * @param targetMonth targetMonth
     */
    public void setTargetMonth(int targetMonth) {
        targetMonth__ = targetMonth;
    }
    /**
     * <p>targetUserSid を取得します。
     * @return targetUserSid
     */
    public int getTargetUserSid() {
        return targetUserSid__;
    }
    /**
     * <p>targetUserSid をセットします。
     * @param targetUserSid targetUserSid
     */
    public void setTargetUserSid(int targetUserSid) {
        targetUserSid__ = targetUserSid;
    }
    /**
     * <p>targetYear を取得します。
     * @return targetYear
     */
    public int getTargetYear() {
        return targetYear__;
    }
    /**
     * <p>targetYear をセットします。
     * @param targetYear targetYear
     */
    public void setTargetYear(int targetYear) {
        targetYear__ = targetYear;
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
     * <p>timeCardInfoList を取得します。
     * @return timeCardInfoList
     */
    public List<Tcd010Model> getTimeCardInfoList() {
        return timeCardInfoList__;
    }
    /**
     * <p>timeCardInfoList をセットします。
     * @param timeCardInfoList timeCardInfoList
     */
    public void setTimeCardInfoList(List<Tcd010Model> timeCardInfoList) {
        timeCardInfoList__ = timeCardInfoList;
    }
    /**
     * <p>outBookName を取得します。
     * @return outBookName
     */
    public String getOutBookName() {
        return outBookName__;
    }
    /**
     * <p>outBookName をセットします。
     * @param outBookName outBookName
     */
    public void setOutBookName(String outBookName) {
        outBookName__ = outBookName;
    }
    /**
     * <p>outBookTmpName を取得します。
     * @return outBookTmpName
     */
    public String getOutBookTmpName() {
        return outBookTmpName__;
    }
    /**
     * <p>outBookTmpName をセットします。
     * @param outBookTmpName outBookTmpName
     */
    public void setOutBookTmpName(String outBookTmpName) {
        outBookTmpName__ = outBookTmpName;
    }

}

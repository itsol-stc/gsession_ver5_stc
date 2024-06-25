package jp.groupsession.v2.fil.ptl040;

import java.util.List;

import jp.groupsession.v2.fil.fil010.Fil010Form;
import jp.groupsession.v2.fil.fil040.FileDirectoryDspModel;

/**
 *<p>ポータル ファイル一覧画面Form
 *
 * @author JTS
 */
public class FilPtl040Form extends Fil010Form {

    /** ディレクトリ情報一覧 */
    private List<FileDirectoryDspModel> directoryList__ = null;

    /** ファイル管理トップ画面URL */
    private String  filTopUrl__;

    /** 表示キャビネットSID */
    private int dspFcbSid__ = 0;
    /** 表示ディレクトリSID */
    private int dspDirSid__ = 0;
    /** ディレクトリ名 */
    private String filPtlDirName__ = "";

    /**
     * <p>directoryList を取得します。
     * @return directoryList
     */
    public List<FileDirectoryDspModel> getDirectoryList() {
        return directoryList__;
    }
    /**
     * <p>directoryList をセットします。
     * @param directoryList directoryList
     */
    public void setDirectoryList(List<FileDirectoryDspModel> directoryList) {
        directoryList__ = directoryList;
    }
    /**
     * <p>filTopUrl を取得します。
     * @return filTopUrl
     */
    public String getFilTopUrl() {
        return filTopUrl__;
    }
    /**
     * <p>filTopUrl をセットします。
     * @param filTopUrl filTopUrl
     */
    public void setFilTopUrl(String filTopUrl) {
        filTopUrl__ = filTopUrl;
    }

    /**
     * <p>dspDirSid を取得します。
     * @return dspDirSid
     */
    public int getDspDirSid() {
        return dspDirSid__;
    }
    /**
     * <p>dspDirSid をセットします。
     * @param dspDirSid dspDirSid
     */
    public void setDspDirSid(int dspDirSid) {
        dspDirSid__ = dspDirSid;
    }

    /**
     * <p>dspFcbSid を取得します。
     * @return dspFcbSid
     */
    public int getDspFcbSid() {
        return dspFcbSid__;
    }
    /**
     * <p>dspFcbSid をセットします。
     * @param dspFcbSid dspFcbSid
     */
    public void setDspFcbSid(int dspFcbSid) {
        dspFcbSid__ = dspFcbSid;
    }
    /**
     * <p>filPtlDirName を取得します。
     * @return filPtlDirName
     */
    public String getFilPtlDirName() {
        return filPtlDirName__;
    }
    /**
     * <p>filPtlDirName をセットします。
     * @param filPtlDirName filPtlDirName
     */
    public void setFilPtlDirName(String filPtlDirName) {
        filPtlDirName__ = filPtlDirName;
    }

}
package jp.groupsession.v2.fil.ptl050;

import jp.groupsession.v2.fil.model.FileCabinetModel;

/**
 * キャビネット情報モデル
 * @author JTS
 *
 */
public class FileCabinetInfoModel extends FileCabinetModel {

    /** 画面表示用 キャビネット名称 */
    private String dspfcbName__;
    /** RootディレクトリSID */
    private int rootDirSid__;

    /**
     * @return rootDirSid
     */
    public int getRootDirSid() {
        return rootDirSid__;
    }
    /**
     * @param rootDirSid 設定する rootDirSid
     */
    public void setRootDirSid(int rootDirSid) {
        rootDirSid__ = rootDirSid;
    }

    /**
     * @return dspfcbName
     */
    public String getDspfcbName() {
        return dspfcbName__;
    }
    /**
     * @param dspfcbName 設定する dspfcbName
     */
    public void setDspfcbName(String dspfcbName) {
        dspfcbName__ = dspfcbName;
    }


}

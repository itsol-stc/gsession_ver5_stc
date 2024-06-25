package jp.groupsession.v2.api.ntp.nippou.edit.model;

import java.util.List;

import jp.groupsession.v2.ntp.ntp040.Ntp040Param;
/**
 * <br>[機  能] WEBAPI 編集時の日報１件のデータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouEditModel extends Ntp040Param {
    /** 日報SID */
    private int nipSid__;

    /** 編集コマンド （0:新規,1:更新,2:削除）*/
    private String cmd__;

    /** 添付ファイル情報 */
    private List<ApiNippouEditTempModel> tempFiles__;
    /**
     * <p>nipSid を取得します。
     * @return nipSid
     */
    public int getNipSid() {
        return nipSid__;
    }

    /**
     * <p>nipSid をセットします。
     * @param nipSid nipSid
     */
    public void setNipSid(int nipSid) {
        nipSid__ = nipSid;
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
     * <p>tempFiles を取得します。
     * @return tempFiles
     */
    public List<ApiNippouEditTempModel> getTempFiles() {
        return tempFiles__;
    }

    /**
     * <p>tempFiles をセットします。
     * @param tempFiles tempFiles
     */
    public void setTempFiles(List<ApiNippouEditTempModel> tempFiles) {
        tempFiles__ = tempFiles;
    }

}

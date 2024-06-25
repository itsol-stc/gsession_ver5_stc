package jp.groupsession.v2.ntp.ntp110kn;

import jp.groupsession.v2.ntp.ntp110.Ntp110Form;

/**
 * <br>[機  能] 日報 インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp110knForm extends Ntp110Form {

    /** 取込みファイル名称 */
    private String ntp110knFileName__ = null;
    /** 登録対象名称 */
    private String impTargetName__ = null;

    /**
     * <p>impTargetName を取得します。
     * @return impTargetName
     */
    public String getImpTargetName() {
        return impTargetName__;
    }

    /**
     * <p>impTargetName をセットします。
     * @param impTargetName impTargetName
     */
    public void setImpTargetName(String impTargetName) {
        impTargetName__ = impTargetName;
    }

    /**
     * <p>ntp110knFileName を取得します。
     * @return ntp110knFileName
     */
    public String getNtp110knFileName() {
        return ntp110knFileName__;
    }

    /**
     * <p>ntp110knFileName をセットします。
     * @param ntp110knFileName ntp110knFileName
     */
    public void setNtp110knFileName(String ntp110knFileName) {
        ntp110knFileName__ = ntp110knFileName;
    }



}
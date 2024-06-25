package jp.groupsession.v2.sch.sch110kn;

import jp.groupsession.v2.sch.sch110.Sch110Form;

/**
 * <br>[機  能] スケジュールインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch110knForm extends Sch110Form {

    /** 取込みファイル名称 */
    private String sch110knFileName__ = null;
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
     * <p>sch110knFileName を取得します。
     * @return sch110knFileName
     */
    public String getSch110knFileName() {
        return sch110knFileName__;
    }

    /**
     * <p>sch110knFileName をセットします。
     * @param sch110knFileName sch110knFileName
     */
    public void setSch110knFileName(String sch110knFileName) {
        sch110knFileName__ = sch110knFileName;
    }



}

package jp.groupsession.v2.prj.prj160kn;

import jp.groupsession.v2.prj.prj160.Prj160ParamModel;

/**
 * <br>[機  能] プロジェクト管理 TODOインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj160knParamModel extends Prj160ParamModel {

    /** 取り込みファイル名称 */
    private String prj160knImportFileName__;
    /** 取り込み対象プロジェクト名称 */
    private String prj160knTargetProjectName__;
    /** 取り込み実行ユーザ名 */
    private String prj160knImportUserName__;
    /** 取り込み件数 */
    private String prj160knImportDataCnt__;

    /**
     * <p>prj160knImportDataCnt を取得します。
     * @return prj160knImportDataCnt
     */
    public String getPrj160knImportDataCnt() {
        return prj160knImportDataCnt__;
    }
    /**
     * <p>prj160knImportDataCnt をセットします。
     * @param prj160knImportDataCnt prj160knImportDataCnt
     */
    public void setPrj160knImportDataCnt(String prj160knImportDataCnt) {
        prj160knImportDataCnt__ = prj160knImportDataCnt;
    }
    /**
     * <p>prj160knImportFileName を取得します。
     * @return prj160knImportFileName
     */
    public String getPrj160knImportFileName() {
        return prj160knImportFileName__;
    }
    /**
     * <p>prj160knImportFileName をセットします。
     * @param prj160knImportFileName prj160knImportFileName
     */
    public void setPrj160knImportFileName(String prj160knImportFileName) {
        prj160knImportFileName__ = prj160knImportFileName;
    }
    /**
     * <p>prj160knImportUserName を取得します。
     * @return prj160knImportUserName
     */
    public String getPrj160knImportUserName() {
        return prj160knImportUserName__;
    }
    /**
     * <p>prj160knImportUserName をセットします。
     * @param prj160knImportUserName prj160knImportUserName
     */
    public void setPrj160knImportUserName(String prj160knImportUserName) {
        prj160knImportUserName__ = prj160knImportUserName;
    }
    /**
     * <p>prj160knTargetProjectName を取得します。
     * @return prj160knTargetProjectName
     */
    public String getPrj160knTargetProjectName() {
        return prj160knTargetProjectName__;
    }
    /**
     * <p>prj160knTargetProjectName をセットします。
     * @param prj160knTargetProjectName prj160knTargetProjectName
     */
    public void setPrj160knTargetProjectName(String prj160knTargetProjectName) {
        prj160knTargetProjectName__ = prj160knTargetProjectName;
    }
}
package jp.groupsession.v2.cir.cir020.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 回覧板一括ダウンロード用の回覧先一覧(CSV)エクスポート用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirCsvKnExpModel  extends AbstractModel {

    /** 社員/職員番号 */
    private String cirCsvKnSyainNum__;
    /** 氏名 */
    private String cirCsvKnName__;
    /** 役職 */
    private String cirCsvKnPost__;
    /** 確認 */
    private String cirCsvKnDate__;
    /** メモ */
    private String cirCsvKnMemo__;
    /** 添付ファイル */
    private String cirCsvKnTmpFile__;
    /** 最終更新日時 */
    private String cirCsvKnEditDate__;

    /**
     * <p>cirCsvKnSyainNum を取得します。
     * @return cirCsvKnSyainNum
     */
    public String getCirCsvKnSyainNum() {
        return cirCsvKnSyainNum__;
    }
    /**
     * <p>cirCsvKnSyainNum をセットします。
     * @param cirCsvKnSyainNum cirCsvKnSyainNum
     */
    public void setCirCsvKnSyainNum(String cirCsvKnSyainNum) {
        cirCsvKnSyainNum__ = cirCsvKnSyainNum;
    }
    /**
     * <p>cirCsvKnName を取得します。
     * @return cirCsvKnName
     */
    public String getCirCsvKnName() {
        return cirCsvKnName__;
    }
    /**
     * <p>cirCsvKnName をセットします。
     * @param cirCsvKnName cirCsvKnName
     */
    public void setCirCsvKnName(String cirCsvKnName) {
        cirCsvKnName__ = cirCsvKnName;
    }
    /**
     * <p>cirCsvKnPost を取得します。
     * @return cirCsvKnPost
     */
    public String getCirCsvKnPost() {
        return cirCsvKnPost__;
    }
    /**
     * <p>cirCsvKnPost をセットします。
     * @param cirCsvKnPost cirCsvKnPost
     */
    public void setCirCsvKnPost(String cirCsvKnPost) {
        cirCsvKnPost__ = cirCsvKnPost;
    }
    /**
     * <p>cirCsvKnDate を取得します。
     * @return cirCsvKnDate
     */
    public String getCirCsvKnDate() {
        return cirCsvKnDate__;
    }
    /**
     * <p>cirCsvKnDate をセットします。
     * @param cirCsvKnDate cirCsvKnDate
     */
    public void setCirCsvKnDate(String cirCsvKnDate) {
        cirCsvKnDate__ = cirCsvKnDate;
    }
    /**
     * <p>cirCsvKnMemo を取得します。
     * @return cirCsvKnMemo
     */
    public String getCirCsvKnMemo() {
        return cirCsvKnMemo__;
    }
    /**
     * <p>cirCsvKnMemo をセットします。
     * @param cirCsvKnMemo cirCsvKnMemo
     */
    public void setCirCsvKnMemo(String cirCsvKnMemo) {
        cirCsvKnMemo__ = cirCsvKnMemo;
    }
    /**
     * <p>cirCsvKnTmpFile を取得します。
     * @return cirCsvKnTmpFile
     */
    public String getCirCsvKnTmpFile() {
        return cirCsvKnTmpFile__;
    }
    /**
     * <p>cirCsvKnTmpFile をセットします。
     * @param cirCsvKnTmpFile cirCsvKnTmpFile
     */
    public void setCirCsvKnTmpFile(String cirCsvKnTmpFile) {
        cirCsvKnTmpFile__ = cirCsvKnTmpFile;
    }
    /**
     * <p>cirCsvKnEditDate を取得します。
     * @return cirCsvKnEditDate
     */
    public String getCirCsvKnEditDate() {
        return cirCsvKnEditDate__;
    }
    /**
     * <p>cirCsvKnEditDate をセットします。
     * @param cirCsvKnEditDate cirCsvKnEditDate
     */
    public void setCirCsvKnEditDate(String cirCsvKnEditDate) {
        cirCsvKnEditDate__ = cirCsvKnEditDate;
    }
}

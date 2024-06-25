package jp.groupsession.v2.enq.pdf;

import jp.groupsession.v2.enq.enq320.Enq320ParamModel;



/**
 * <br>[機  能] アンケート結果確認画面のPDF用Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqPdfModel extends Enq320ParamModel {

    //PDF用
    /** アンケートSID */
    private long enqPdfSid__ = -1;
    /** ファイル名 */
    private String fileName__ = null;
    /** セーブ用ファイル名 */
    private String svFileName__ = null;

    //検索条件
    /**対象  */

    /** 状態(回答、未回答) */
    private String stsAnswer__ = null;

    /**
     * <p>enqPdfSid を取得します。
     * @return enqPdfSid
     */
    public long getEnqPdfSid() {
        return enqPdfSid__;
    }
    /**
     * <p>enqPdfSid をセットします。
     * @param enqPdfSid enqPdfSid
     */
    public void setEnqPdfSid(long enqPdfSid) {
        enqPdfSid__ = enqPdfSid;
    }
    /**
     * <p>fileName を取得します。
     * @return fileName
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    /**
     * <p>svFileName を取得します。
     * @return svFileName
     */
    public String getSvFileName() {
        return svFileName__;
    }
    /**
     * <p>svFileName をセットします。
     * @param svFileName svFileName
     */
    public void setSvFileName(String svFileName) {
        svFileName__ = svFileName;
    }
    /**
     * <p>stsAnswer を取得します。
     * @return stsAnswer
     */
    public String getStsAnswer() {
        return stsAnswer__;
    }
    /**
     * <p>stsAnswer をセットします。
     * @param stsAnswer stsAnswer
     */
    public void setStsAnswer(String stsAnswer) {
        stsAnswer__ = stsAnswer;
    }

}

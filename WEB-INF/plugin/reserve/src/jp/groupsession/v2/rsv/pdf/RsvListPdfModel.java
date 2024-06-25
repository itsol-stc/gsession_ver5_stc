package jp.groupsession.v2.rsv.pdf;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel;

/**
 * <br>[機  能] 施設利用状況照会画面のPDF出力用Modelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvListPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** セーブ用ファイル名 */
    private String saveFileName__ = null;

    /** 期間指定区分 */
    private int pdfDateKbn__ = 0;
    /** 期間From */
    private String pdfFromDate__ = null;
    /** 期間To */
    private String pdfToDate__ = null;
    /** キーワード */
    private String pdfKeyWord__ = null;
    /** キーワード区分 */
    private int pdfKeyWordKbn__ = 0;
    /** 検索対象1 利用目的 */
    private int pdfSearchTarget1__ = 0;
    /** 検索対象2 内容 */
    private int pdfSearchTarget2__ = 0;
    /** グループ */
    private String pdfSelectGrp__ = null;
    /** 施設 */
    private String pdfSelectSisetsu__ = null;
    /** 承認状況 */
    private int pdfSyoninKbn__ = 0;
    /** ソートキー１ */
    private String pdfSortKey1__ = null;
    /** オーダーキー1  */
    private int pdfOrderKey1__ = 0;
    /** ソートキー2 */
    private String pdfSortKey2__ = null;
    /** オーダーキー2  */
    private int pdfOrderKey2__ = 0;
    /** 施設予約一覧リスト */
    private List <Rsv100SisYrkModel> rsvDataList__ = null;

    /**
     * コンストラクタ
     * */
    public RsvListPdfModel() {
        rsvDataList__ = new ArrayList<Rsv100SisYrkModel>();
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
     * <p>pdfDateKbn を取得します。
     * @return pdfDateKbn
     */
    public int getPdfDateKbn() {
        return pdfDateKbn__;
    }

    /**
     * <p>pdfDateKbn をセットします。
     * @param pdfDateKbn pdfDateKbn
     */
    public void setPdfDateKbn(int pdfDateKbn) {
        pdfDateKbn__ = pdfDateKbn;
    }

    /**
     * <p>pdfFromDate を取得します。
     * @return pdfFromDate
     */
    public String getPdfFromDate() {
        return pdfFromDate__;
    }

    /**
     * <p>pdfFromDate をセットします。
     * @param pdfFromDate pdfFromDate
     */
    public void setPdfFromDate(String pdfFromDate) {
        pdfFromDate__ = pdfFromDate;
    }

    /**
     * <p>pdfToDate を取得します。
     * @return pdfToDate
     */
    public String getPdfToDate() {
        return pdfToDate__;
    }

    /**
     * <p>pdfToDate をセットします。
     * @param pdfToDate pdfToDate
     */
    public void setPdfToDate(String pdfToDate) {
        pdfToDate__ = pdfToDate;
    }

    /**
     * <p>pdfKeyWord を取得します。
     * @return pdfKeyWord
     */
    public String getPdfKeyWord() {
        return pdfKeyWord__;
    }

    /**
     * <p>pdfKeyWord をセットします。
     * @param pdfKeyWord pdfKeyWord
     */
    public void setPdfKeyWord(String pdfKeyWord) {
        pdfKeyWord__ = pdfKeyWord;
    }

    /**
     * <p>pdfKeyWordKbn を取得します。
     * @return pdfKeyWordKbn
     */
    public int getPdfKeyWordKbn() {
        return pdfKeyWordKbn__;
    }

    /**
     * <p>pdfKeyWordKbn をセットします。
     * @param pdfKeyWordKbn pdfKeyWordKbn
     */
    public void setPdfKeyWordKbn(int pdfKeyWordKbn) {
        pdfKeyWordKbn__ = pdfKeyWordKbn;
    }

    /**
     * <p>pdfSearchTarget1 を取得します。
     * @return pdfSearchTarget1
     */
    public int getPdfSearchTarget1() {
        return pdfSearchTarget1__;
    }

    /**
     * <p>pdfSearchTarget1 をセットします。
     * @param pdfSearchTarget1 pdfSearchTarget1
     */
    public void setPdfSearchTarget1(int pdfSearchTarget1) {
        pdfSearchTarget1__ = pdfSearchTarget1;
    }

    /**
     * <p>pdfSearchTarget2 を取得します。
     * @return pdfSearchTarget2
     */
    public int getPdfSearchTarget2() {
        return pdfSearchTarget2__;
    }

    /**
     * <p>pdfSearchTarget2 をセットします。
     * @param pdfSearchTarget2 pdfSearchTarget2
     */
    public void setPdfSearchTarget2(int pdfSearchTarget2) {
        pdfSearchTarget2__ = pdfSearchTarget2;
    }

    /**
     * <p>pdfSelectGrp を取得します。
     * @return pdfSelectGrp
     */
    public String getPdfSelectGrp() {
        return pdfSelectGrp__;
    }

    /**
     * <p>pdfSelectGrp をセットします。
     * @param pdfSelectGrp pdfSelectGrp
     */
    public void setPdfSelectGrp(String pdfSelectGrp) {
        pdfSelectGrp__ = pdfSelectGrp;
    }

    /**
     * <p>pdfSelectSisetsu を取得します。
     * @return pdfSelectSisetsu
     */
    public String getPdfSelectSisetsu() {
        return pdfSelectSisetsu__;
    }

    /**
     * <p>pdfSelectSisetsu をセットします。
     * @param pdfSelectSisetsu pdfSelectSisetsu
     */
    public void setPdfSelectSisetsu(String pdfSelectSisetsu) {
        pdfSelectSisetsu__ = pdfSelectSisetsu;
    }

    /**
     * <p>pdfSyoninKbn を取得します。
     * @return pdfSyoninKbn
     */
    public int getPdfSyoninKbn() {
        return pdfSyoninKbn__;
    }

    /**
     * <p>pdfSyoninKbn をセットします。
     * @param pdfSyoninKbn pdfSyoninKbn
     */
    public void setPdfSyoninKbn(int pdfSyoninKbn) {
        pdfSyoninKbn__ = pdfSyoninKbn;
    }

    /**
     * <p>pdfSortKey1 を取得します。
     * @return pdfSortKey1
     */
    public String getPdfSortKey1() {
        return pdfSortKey1__;
    }

    /**
     * <p>pdfSortKey1 をセットします。
     * @param pdfSortKey1 pdfSortKey1
     */
    public void setPdfSortKey1(String pdfSortKey1) {
        pdfSortKey1__ = pdfSortKey1;
    }

    /**
     * <p>pdfOrderKey1 を取得します。
     * @return pdfOrderKey1
     */
    public int getPdfOrderKey1() {
        return pdfOrderKey1__;
    }

    /**
     * <p>pdfOrderKey1 をセットします。
     * @param pdfOrderKey1 pdfOrderKey1
     */
    public void setPdfOrderKey1(int pdfOrderKey1) {
        pdfOrderKey1__ = pdfOrderKey1;
    }

    /**
     * <p>pdfSortKey2 を取得します。
     * @return pdfSortKey2
     */
    public String getPdfSortKey2() {
        return pdfSortKey2__;
    }

    /**
     * <p>pdfSortKey2 をセットします。
     * @param pdfSortKey2 pdfSortKey2
     */
    public void setPdfSortKey2(String pdfSortKey2) {
        pdfSortKey2__ = pdfSortKey2;
    }

    /**
     * <p>pdfOrderKey2 を取得します。
     * @return pdfOrderKey2
     */
    public int getPdfOrderKey2() {
        return pdfOrderKey2__;
    }

    /**
     * <p>pdfOrderKey2 をセットします。
     * @param pdfOrderKey2 pdfOrderKey2
     */
    public void setPdfOrderKey2(int pdfOrderKey2) {
        pdfOrderKey2__ = pdfOrderKey2;
    }

    /**
     * <p>rsvDataList を取得します。
     * @return rsvDataList
     */
    public List<Rsv100SisYrkModel> getRsvDataList() {
        return rsvDataList__;
    }

    /**
     * <p>rsvDataList をセットします。
     * @param rsvDataList rsvDataList
     */
    public void setRsvDataList(List<Rsv100SisYrkModel> rsvDataList) {
        rsvDataList__ = rsvDataList;
    }

    /**
     * <p>saveFileName を取得します。
     * @return saveFileName
     */
    public String getSaveFileName() {
        return saveFileName__;
    }

    /**
     * <p>saveFileName をセットします。
     * @param saveFileName saveFileName
     */
    public void setSaveFileName(String saveFileName) {
        saveFileName__ = saveFileName;
    }

}

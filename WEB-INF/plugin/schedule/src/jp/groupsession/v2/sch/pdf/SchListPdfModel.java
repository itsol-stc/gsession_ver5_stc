package jp.groupsession.v2.sch.pdf;

import java.util.ArrayList;

import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;

/**
 * <br>[機  能] スケジュール一覧画面のPDF出力用Modelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchListPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** セーブ用ファイル名 */
    private String saveFileName__ = null;

    /** 検索条件 グループ名 */
    private String pdfGroup__ = null;
    /** 検索条件 ユーザ名 */
    private String pdfUser__ = null;
    /** 検索条件 開始日 */
    private String pdfStDate__ = null;
    /** 検索条件 終了日 */
    private String pdfEdDate__ = null;
    /** 検索条件 キーワード */
    private String pdfKeyWord__ = null;
    /** 検索条件 キーワード条件区分 */
    private int pdfKeyWordKbn__ = 0;
    /** 検索条件 検索対象 件名 */
    private boolean pdfTarKen__ = false;
    /** 検索条件 検索対象 本文 */
    private boolean pdfTarHon__ = false;
    /** 検索条件 タイトル色 選択 */
    private String [] pdfColor__ = null;
    /** 検索条件 タイトル色 コメント */
    private String [] pdfColorMsg__ = null;
    /** 検索条件 ソートキー１ */
    private String pdfSortKey1__ = null;
    /** 検索条件 オーダーキー１ */
    private int pdfOrderKey1__ = 0;
    /** 検索条件 ソートキー２ */
    private String pdfSortKey2__ = null;
    /** 検索条件 オーダーキー２ */
    private int pdfOrderKey2__ = 0;
    /** スケジュールリスト */
    private ArrayList<SimpleScheduleModel> pdfScheduleList__ = null;

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
    /**
     * <p>pdfGroup を取得します。
     * @return pdfGroup
     */
    public String getPdfGroup() {
        return pdfGroup__;
    }
    /**
     * <p>pdfGroup をセットします。
     * @param pdfGroup pdfGroup
     */
    public void setPdfGroup(String pdfGroup) {
        pdfGroup__ = pdfGroup;
    }
    /**
     * <p>pdfUser を取得します。
     * @return pdfUser
     */
    public String getPdfUser() {
        return pdfUser__;
    }
    /**
     * <p>pdfUser をセットします。
     * @param pdfUser pdfUser
     */
    public void setPdfUser(String pdfUser) {
        pdfUser__ = pdfUser;
    }
    /**
     * <p>pdfStDate を取得します。
     * @return pdfStDate
     */
    public String getPdfStDate() {
        return pdfStDate__;
    }
    /**
     * <p>pdfStDate をセットします。
     * @param pdfStDate pdfStDate
     */
    public void setPdfStDate(String pdfStDate) {
        pdfStDate__ = pdfStDate;
    }
    /**
     * <p>pdfEdDate を取得します。
     * @return pdfEdDate
     */
    public String getPdfEdDate() {
        return pdfEdDate__;
    }
    /**
     * <p>pdfEdDate をセットします。
     * @param pdfEdDate pdfEdDate
     */
    public void setPdfEdDate(String pdfEdDate) {
        pdfEdDate__ = pdfEdDate;
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
     * <p>pdfTarKen を取得します。
     * @return pdfTarKen
     */
    public boolean isPdfTarKen() {
        return pdfTarKen__;
    }
    /**
     * <p>pdfTarKen をセットします。
     * @param pdfTarKen pdfTarKen
     */
    public void setPdfTarKen(boolean pdfTarKen) {
        pdfTarKen__ = pdfTarKen;
    }
    /**
     * <p>pdfTarHon を取得します。
     * @return pdfTarHon
     */
    public boolean isPdfTarHon() {
        return pdfTarHon__;
    }
    /**
     * <p>pdfTarHon をセットします。
     * @param pdfTarHon pdfTarHon
     */
    public void setPdfTarHon(boolean pdfTarHon) {
        pdfTarHon__ = pdfTarHon;
    }
    /**
     * <p>pdfColor を取得します。
     * @return pdfColor
     */
    public String[] getPdfColor() {
        return pdfColor__;
    }
    /**
     * <p>pdfColor をセットします。
     * @param pdfColor pdfColor
     */
    public void setPdfColor(String[] pdfColor) {
        pdfColor__ = pdfColor;
    }
    /**
     * <p>pdfColorMsg を取得します。
     * @return pdfColorMsg
     */
    public String[] getPdfColorMsg() {
        return pdfColorMsg__;
    }
    /**
     * <p>pdfColorMsg をセットします。
     * @param pdfColorMsg pdfColorMsg
     */
    public void setPdfColorMsg(String[] pdfColorMsg) {
        pdfColorMsg__ = pdfColorMsg;
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
     * <p>pdfScheduleList を取得します。
     * @return pdfScheduleList
     */
    public ArrayList<SimpleScheduleModel> getPdfScheduleList() {
        return pdfScheduleList__;
    }
    /**
     * <p>pdfScheduleList をセットします。
     * @param pdfScheduleList pdfScheduleList
     */
    public void setPdfScheduleList(ArrayList<SimpleScheduleModel> pdfScheduleList) {
        pdfScheduleList__ = pdfScheduleList;
    }
}
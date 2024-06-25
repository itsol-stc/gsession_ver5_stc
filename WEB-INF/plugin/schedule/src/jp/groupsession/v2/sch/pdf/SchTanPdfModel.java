package jp.groupsession.v2.sch.pdf;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sch.sch040.model.Sch040AttendModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;



/**
 * <br>[機  能] スケジュール編集画面のPDF出力用Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchTanPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** セーブ用ファイル名 */
    private String saveFileName__ = null;

    /** 施設予約プラグイン使用有無 0=使用 1=未使用*/
    private int reservePluginKbn__;
    /** アドレス帳プラグイン使用有無 0=使用 1=未使用*/
    private int addressPluginKbn__;

    /** 名前 */
    private String pdfName__ = null;
    /** 開始 */
    private String pdfFrDate__ = null;
    /** 終了 */
    private String pdfToDate__ = null;
    /** 時間指定区分 */
    private int pdfTimeKbn__ = 0;
    /** 期間 */
    private int pdfKikan__ = 0;
    /** 会社・担当者 */
    private List<Sch040CompanyModel> pdfCompanyList__ = null;
    /** タイトル */
    private String pdfTitle__ = null;
    /** タイトル色 選択値 */
    private int pdfColor__ = 0;
    /** タイトル色 コメント */
    private ArrayList <String> pdfColorMsg__ = null;
    /** 内容 */
    private String pdfValue__ = null;
    /** 内容 */
    private List<CmnBinfModel> pdfBinList__ = null;
    /** 備考 */
    private String pdfBiko__ = null;
    /** 編集権限 */
    private int pdfEditKbn__ = 0;
    /** 公開 */
    private int pdfPublicKbn__ = 0;
    /** 公開対象 */
    private List<UsrLabelValueBean> pdfPublicTarget__ = null;
    /** 同時登録 */
    private ArrayList<CmnUsrmInfModel> pdfUserList__ = null;
    /** 施設予約 */
    private ArrayList<String> pdfSisList__ = null;
    /** 登録者 */
    private String pdfRegistUser__ = null;
    /** 登録日時 */
    private String pdfRegistDate__ = null;

    /** 出欠確認区分 0:非表示  1:表示 */
    private int pdfAttendKbn__ = 0;
    /** 出欠確認登録者区分 */
    private int pdfAttendAuKbn__ = 0;
    /** 出欠確認回答区分 */
    private int pdfAttendAnsKbn__ = 0;
    /** 出欠確認回答コメント*/
    private String pdfAttendAnsComment__ = null;
    /** 出欠確認回答一覧 */
    private ArrayList<Sch040AttendModel> pdfAttendAnsList__ = null;
    /** スケジュール編集画面 表示モード（0：通常スケジュール  1:出欠依頼者  2:出欠回答者） */
    private int pdfEditDspMode__ = 0;

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
     * <p>reservePluginKbn を取得します。
     * @return reservePluginKbn
     */
    public int getReservePluginKbn() {
        return reservePluginKbn__;
    }
    /**
     * <p>reservePluginKbn をセットします。
     * @param reservePluginKbn reservePluginKbn
     */
    public void setReservePluginKbn(int reservePluginKbn) {
        reservePluginKbn__ = reservePluginKbn;
    }
    /**
     * <p>addressPluginKbn を取得します。
     * @return addressPluginKbn
     */
    public int getAddressPluginKbn() {
        return addressPluginKbn__;
    }
    /**
     * <p>addressPluginKbn をセットします。
     * @param addressPluginKbn addressPluginKbn
     */
    public void setAddressPluginKbn(int addressPluginKbn) {
        addressPluginKbn__ = addressPluginKbn;
    }
    /**
     * <p>pdfName を取得します。
     * @return pdfName
     */
    public String getPdfName() {
        return pdfName__;
    }
    /**
     * <p>pdfName をセットします。
     * @param pdfName pdfName
     */
    public void setPdfName(String pdfName) {
        pdfName__ = pdfName;
    }
    /**
     * <p>pdfFrDate を取得します。
     * @return pdfFrDate
     */
    public String getPdfFrDate() {
        return pdfFrDate__;
    }
    /**
     * <p>pdfFrDate をセットします。
     * @param pdfFrDate pdfFrDate
     */
    public void setPdfFrDate(String pdfFrDate) {
        pdfFrDate__ = pdfFrDate;
    }
    /**
     * <p>pdfTimeKbn を取得します。
     * @return pdfTimeKbn
     */
    public int getPdfTimeKbn() {
        return pdfTimeKbn__;
    }
    /**
     * <p>pdfTimeKbn をセットします。
     * @param pdfTimeKbn pdfTimeKbn
     */
    public void setPdfTimeKbn(int pdfTimeKbn) {
        pdfTimeKbn__ = pdfTimeKbn;
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
     * <p>pdfKikan を取得します。
     * @return pdfKikan
     */
    public int getPdfKikan() {
        return pdfKikan__;
    }
    /**
     * <p>pdfKikan をセットします。
     * @param pdfKikan pdfKikan
     */
    public void setPdfKikan(int pdfKikan) {
        pdfKikan__ = pdfKikan;
    }
    /**
     * <p>pdfTitle を取得します。
     * @return pdfTitle
     */
    public String getPdfTitle() {
        return pdfTitle__;
    }
    /**
     * <p>pdfTitle をセットします。
     * @param pdfTitle pdfTitle
     */
    public void setPdfTitle(String pdfTitle) {
        pdfTitle__ = pdfTitle;
    }
    /**
     * <p>pdfColor を取得します。
     * @return pdfColor
     */
    public int getPdfColor() {
        return pdfColor__;
    }
    /**
     * <p>pdfColor をセットします。
     * @param pdfColor pdfColor
     */
    public void setPdfColor(int pdfColor) {
        pdfColor__ = pdfColor;
    }
    /**
     * <p>pdfColorMsg を取得します。
     * @return pdfColorMsg
     */
    public ArrayList<String> getPdfColorMsg() {
        return pdfColorMsg__;
    }
    /**
     * <p>pdfColorMsg をセットします。
     * @param pdfColorMsg pdfColorMsg
     */
    public void setPdfColorMsg(ArrayList<String> pdfColorMsg) {
        pdfColorMsg__ = pdfColorMsg;
    }
    /**
     * <p>pdfValue を取得します。
     * @return pdfValue
     */
    public String getPdfValue() {
        return pdfValue__;
    }
    /**
     * <p>pdfValue をセットします。
     * @param pdfValue pdfValue
     */
    public void setPdfValue(String pdfValue) {
        pdfValue__ = pdfValue;
    }
    /**
     * <p>pdfBinList を取得します。
     * @return pdfBinList
     * @see jp.groupsession.v2.sch.pdf.SchTanPdfModel#pdfBinList__
     */
    public List<CmnBinfModel> getPdfBinList() {
        return pdfBinList__;
    }
    /**
     * <p>pdfBinList をセットします。
     * @param pdfBinList pdfBinList
     * @see jp.groupsession.v2.sch.pdf.SchTanPdfModel#pdfBinList__
     */
    public void setPdfBinList(List<CmnBinfModel> pdfBinList) {
        pdfBinList__ = pdfBinList;
    }
    /**
     * <p>pdfBiko を取得します。
     * @return pdfBiko
     */
    public String getPdfBiko() {
        return pdfBiko__;
    }
    /**
     * <p>pdfBiko をセットします。
     * @param pdfBiko pdfBiko
     */
    public void setPdfBiko(String pdfBiko) {
        pdfBiko__ = pdfBiko;
    }
    /**
     * <p>pdfEditKbn を取得します。
     * @return pdfEditKbn
     */
    public int getPdfEditKbn() {
        return pdfEditKbn__;
    }
    /**
     * <p>pdfEditKbn をセットします。
     * @param pdfEditKbn pdfEditKbn
     */
    public void setPdfEditKbn(int pdfEditKbn) {
        pdfEditKbn__ = pdfEditKbn;
    }
    /**
     * <p>pdfPublicKbn を取得します。
     * @return pdfPublicKbn
     */
    public int getPdfPublicKbn() {
        return pdfPublicKbn__;
    }
    /**
     * <p>pdfPublicKbn をセットします。
     * @param pdfPublicKbn pdfPublicKbn
     */
    public void setPdfPublicKbn(int pdfPublicKbn) {
        pdfPublicKbn__ = pdfPublicKbn;
    }
    /**
     * <p>pdfPublicTarget を取得します。
     * @return pdfPublicTarget
     * @see jp.groupsession.v2.sch.pdf.SchTanPdfModel#pdfPublicTarget__
     */
    public List<UsrLabelValueBean> getPdfPublicTarget() {
        return pdfPublicTarget__;
    }
    /**
     * <p>pdfPublicTarget をセットします。
     * @param pdfPublicTarget pdfPublicTarget
     * @see jp.groupsession.v2.sch.pdf.SchTanPdfModel#pdfPublicTarget__
     */
    public void setPdfPublicTarget(List<UsrLabelValueBean> pdfPublicTarget) {
        pdfPublicTarget__ = pdfPublicTarget;
    }
    /**
     * <p>pdfUserList を取得します。
     * @return pdfUserList
     */
    public ArrayList<CmnUsrmInfModel> getPdfUserList() {
        return pdfUserList__;
    }
    /**
     * <p>pdfUserList をセットします。
     * @param pdfUserList pdfUserList
     */
    public void setPdfUserList(ArrayList<CmnUsrmInfModel> pdfUserList) {
        pdfUserList__ = pdfUserList;
    }
    /**
     * <p>pdfSisList を取得します。
     * @return pdfSisList
     */
    public ArrayList<String> getPdfSisList() {
        return pdfSisList__;
    }
    /**
     * <p>pdfSisList をセットします。
     * @param pdfSisList pdfSisList
     */
    public void setPdfSisList(ArrayList<String> pdfSisList) {
        pdfSisList__ = pdfSisList;
    }
    /**
     * <p>pdfRegistUser を取得します。
     * @return pdfRegistUser
     */
    public String getPdfRegistUser() {
        return pdfRegistUser__;
    }
    /**
     * <p>pdfRegistUser をセットします。
     * @param pdfRegistUser pdfRegistUser
     */
    public void setPdfRegistUser(String pdfRegistUser) {
        pdfRegistUser__ = pdfRegistUser;
    }
    /**
     * <p>pdfRegistDate を取得します。
     * @return pdfRegistDate
     */
    public String getPdfRegistDate() {
        return pdfRegistDate__;
    }
    /**
     * <p>pdfRegistDate をセットします。
     * @param pdfRegistDate pdfRegistDate
     */
    public void setPdfRegistDate(String pdfRegistDate) {
        pdfRegistDate__ = pdfRegistDate;
    }
    /**
     * <p>pdfCompanyList を取得します。
     * @return pdfCompanyList
     */
    public List<Sch040CompanyModel> getPdfCompanyList() {
        return pdfCompanyList__;
    }
    /**
     * <p>pdfCompanyList をセットします。
     * @param pdfCompanyList pdfCompanyList
     */
    public void setPdfCompanyList(List<Sch040CompanyModel> pdfCompanyList) {
        pdfCompanyList__ = pdfCompanyList;
    }
    /**
     * <p>pdfAttendKbn を取得します。
     * @return pdfAttendKbn
     */
    public int getPdfAttendKbn() {
        return pdfAttendKbn__;
    }
    /**
     * <p>pdfAttendKbn をセットします。
     * @param pdfAttendKbn pdfAttendKbn
     */
    public void setPdfAttendKbn(int pdfAttendKbn) {
        pdfAttendKbn__ = pdfAttendKbn;
    }
    /**
     * <p>pdfAttendAuKbn を取得します。
     * @return pdfAttendAuKbn
     */
    public int getPdfAttendAuKbn() {
        return pdfAttendAuKbn__;
    }
    /**
     * <p>pdfAttendAuKbn をセットします。
     * @param pdfAttendAuKbn pdfAttendAuKbn
     */
    public void setPdfAttendAuKbn(int pdfAttendAuKbn) {
        pdfAttendAuKbn__ = pdfAttendAuKbn;
    }
    /**
     * <p>pdfAttendAnsKbn を取得します。
     * @return pdfAttendAnsKbn
     */
    public int getPdfAttendAnsKbn() {
        return pdfAttendAnsKbn__;
    }
    /**
     * <p>pdfAttendAnsKbn をセットします。
     * @param pdfAttendAnsKbn pdfAttendAnsKbn
     */
    public void setPdfAttendAnsKbn(int pdfAttendAnsKbn) {
        pdfAttendAnsKbn__ = pdfAttendAnsKbn;
    }
    /**
     * <p>pdfAttendAnsList を取得します。
     * @return pdfAttendAnsList
     */
    public ArrayList<Sch040AttendModel> getPdfAttendAnsList() {
        return pdfAttendAnsList__;
    }
    /**
     * <p>pdfAttendAnsList をセットします。
     * @param pdfAttendAnsList pdfAttendAnsList
     */
    public void setPdfAttendAnsList(ArrayList<Sch040AttendModel> pdfAttendAnsList) {
        pdfAttendAnsList__ = pdfAttendAnsList;
    }
    /**
     * <p>pdfEditDspMode を取得します。
     * @return pdfEditDspMode
     */
    public int getPdfEditDspMode() {
        return pdfEditDspMode__;
    }
    /**
     * <p>pdfEditDspMode をセットします。
     * @param pdfEditDspMode pdfEditDspMode
     */
    public void setPdfEditDspMode(int pdfEditDspMode) {
        pdfEditDspMode__ = pdfEditDspMode;
    }
    /**
     * <p>pdfAttendAnsComment を取得します。
     * @return pdfAttendAnsComment
     * @see jp.groupsession.v2.sch.pdf.SchTanPdfModel#pdfAttendAnsComment__
     */
    public String getPdfAttendAnsComment() {
        return pdfAttendAnsComment__;
    }
    /**
     * <p>pdfAttendAnsComment をセットします。
     * @param pdfAttendAnsComment pdfAttendAnsComment
     * @see jp.groupsession.v2.sch.pdf.SchTanPdfModel#pdfAttendAnsComment__
     */
    public void setPdfAttendAnsComment(String pdfAttendAnsComment) {
        pdfAttendAnsComment__ = pdfAttendAnsComment;
    }

}

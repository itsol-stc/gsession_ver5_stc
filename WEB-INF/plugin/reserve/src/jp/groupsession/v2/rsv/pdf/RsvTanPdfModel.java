package jp.groupsession.v2.rsv.pdf;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] 施設予約 施設予約編集画面の単票PDF出力用Modelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvTanPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** セーブ用ファイル名 */
    private String saveFileName__ = null;

    /************** 施設情報 **************/
    /***** 共通項目 *****/
    /** 施設グループ */
    private String pdfRsvGrpName__ = null;
    /** 施設区分 */
    private int pdfRsvKbn__ = 0;
    /** 施設区分(表示用) */
    private String pdfRsvDspKbn__ = null;

    /** 施設名 */
    private String pdfRsvName__ = null;
    /** 資産管理番号 */
    private String pdfRsvSisNum__ = null;
    /** 重複登録 */
    private String pdfRsvOverRegKbn__ = null;
    /** 予約可能期限 */
    private String pdfRsvKikan__ = null;
    /** 備考 */
    private String pdfRsvBiko__ = null;

    /***** 可変項目 *****/
    /***** 部屋系 *****/
    /** 座席数 */
    private String pdfRsvSeatNum__ = null;
    /** 喫煙 かぶり */
    private String pdfRsvSmoking__ = null;

    /***** 物品系 *****/
    /** 個数 */
    private String pdfRsvNum__ = null;
    /** 社外持出し かぶり */
    private String pdfRsvTakeOut__ = null;

    /***** 車 *****/
    /** ナンバー */
    private String pdfRsvCarNumber__ = null;
    /** 乗員数 */
    private String pdfRsvCarSeatNum__ = null;
    /** 喫煙 かぶり */

    /***** 書籍 *****/
    /** ISBN */
    private String pdfRsvBookIsbn__ = null;
    /** 冊数 */
    private String pdfRsvBookNum__ = null;
    /** 社外持出し かぶり */

    /************** 予約情報 **************/

    /** 登録者 */
    private String pdfRsvAddName__ = null;
    /** 登録日時 */
    private String pdfRsvAddDate__ = null;
    /**更新者 */
    private String pdfRsvEditName__ = null;
    /** 更新日時 */
    private String pdfRsvEditDate__ = null;
    /** 利用目的 */
    private String pdfRsvObj__ = null;
    /** 期間 開始 */
    private String pdfRsvFromDate__ = null;
    /** 期間 終了 */
    private String pdfRsvToDate__ = null;
    /** 内容 */
    private String pdfRsvSubject__ = null;
    /** 編集権限 */
    private int pdfRsvEditKbn__ = 0;
    /** 公開区分 */
    private int pdfRsvPublicKbn__ = 0;
    /** スケジュールプラグイン使用可能フラグ */
    private int pdfRsvSchUseFlg__ = 0;
    /** スケジュール登録区分 */
    private int pdfRsvSchKbn__ = 0;
    /** スケジュール登録 グループ名 */
    private String pdfRsvSchGrpName__ = null;
    /** スケジュール登録 ユーザリスト */
    private List<String> pdfRsvSchUserList__ = null;
    /** 公開対象ユーザ・グループリスト */
    private List <UsrLabelValueBean> pdfRsvPubUsrGrpList__ = null;

    /************** 施設予約区分別情報 **************/
    /** 担当部署 */
    private String pdfRsvBusyo__ = null;
    /** 担当・使用者名 */
    private String pdfRsvUseName__ = null;
    /** 人数 */
    private String pdfRsvUseNum__ = null;
    /** 区分 (0:未設定 1:会議 2:研修 3:その他) */
    private String pdfRsvUseKbn__ = null;
    /** 連絡先 */
    private String pdfRsvContact__ = null;
    /** 会議名案内 */
    private String pdfRsvGuide__ = null;
    /** 駐車場見込み台数 */
    private String pdfRsvParkNum__ = null;
    /** 印刷区分 */
    private String pdfRsvPrintKbn__ = null;
    /** 行き先 */
    private String pdfRsvDest__ = null;

    /**
     * コンストラクタ
     * */
    public RsvTanPdfModel() {
        pdfRsvSchUserList__ = new ArrayList<String>();
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
     * <p>pdfRsvGrpName を取得します。
     * @return pdfRsvGrpName
     */
    public String getPdfRsvGrpName() {
        return pdfRsvGrpName__;
    }

    /**
     * <p>pdfRsvGrpName をセットします。
     * @param pdfRsvGrpName pdfRsvGrpName
     */
    public void setPdfRsvGrpName(String pdfRsvGrpName) {
        pdfRsvGrpName__ = pdfRsvGrpName;
    }

    /**
     * <p>pdfRsvKbn を取得します。
     * @return pdfRsvKbn
     */
    public int getPdfRsvKbn() {
        return pdfRsvKbn__;
    }

    /**
     * <p>pdfRsvKbn をセットします。
     * @param pdfRsvKbn pdfRsvKbn
     */
    public void setPdfRsvKbn(int pdfRsvKbn) {
        pdfRsvKbn__ = pdfRsvKbn;
    }

    /**
     * <p>pdfRsvDspKbn を取得します。
     * @return pdfRsvDspKbn
     */
    public String getPdfRsvDspKbn() {
        return pdfRsvDspKbn__;
    }

    /**
     * <p>pdfRsvKbn をセットします。
     * @param pdfRsvDspKbn pdfRsvDspKbn
     */
    public void setPdfRsvDspKbn(String pdfRsvDspKbn) {
        pdfRsvDspKbn__ = pdfRsvDspKbn;
    }

    /**
     * <p>pdfRsvName を取得します。
     * @return pdfRsvName
     */
    public String getPdfRsvName() {
        return pdfRsvName__;
    }

    /**
     * <p>pdfRsvName をセットします。
     * @param pdfRsvName pdfRsvName
     */
    public void setPdfRsvName(String pdfRsvName) {
        pdfRsvName__ = pdfRsvName;
    }

    /**
     * <p>pdfRsvSisNum を取得します。
     * @return pdfRsvSisNum
     */
    public String getPdfRsvSisNum() {
        return pdfRsvSisNum__;
    }

    /**
     * <p>pdfRsvSisNum をセットします。
     * @param pdfRsvSisNum pdfRsvSisNum
     */
    public void setPdfRsvSisNum(String pdfRsvSisNum) {
        pdfRsvSisNum__ = pdfRsvSisNum;
    }

    /**
     * <p>pdfRsvOverRegKbn を取得します。
     * @return pdfRsvOverRegKbn
     */
    public String getPdfRsvOverRegKbn() {
        return pdfRsvOverRegKbn__;
    }

    /**
     * <p>pdfRsvOverRegKbn をセットします。
     * @param pdfRsvOverRegKbn pdfRsvOverRegKbn
     */
    public void setPdfRsvOverRegKbn(String pdfRsvOverRegKbn) {
        pdfRsvOverRegKbn__ = pdfRsvOverRegKbn;
    }

    /**
     * <p>pdfRsvKikan を取得します。
     * @return pdfRsvKikan
     */
    public String getPdfRsvKikan() {
        return pdfRsvKikan__;
    }

    /**
     * <p>pdfRsvKikan をセットします。
     * @param pdfRsvKikan pdfRsvKikan
     */
    public void setPdfRsvKikan(String pdfRsvKikan) {
        pdfRsvKikan__ = pdfRsvKikan;
    }

    /**
     * <p>pdfRsvBiko を取得します。
     * @return pdfRsvBiko
     */
    public String getPdfRsvBiko() {
        return pdfRsvBiko__;
    }

    /**
     * <p>pdfRsvBiko をセットします。
     * @param pdfRsvBiko pdfRsvBiko
     */
    public void setPdfRsvBiko(String pdfRsvBiko) {
        pdfRsvBiko__ = pdfRsvBiko;
    }

    /**
     * <p>pdfRsvSeatNum を取得します。
     * @return pdfRsvSeatNum
     */
    public String getPdfRsvSeatNum() {
        return pdfRsvSeatNum__;
    }

    /**
     * <p>pdfRsvSeatNum をセットします。
     * @param pdfRsvSeatNum pdfRsvSeatNum
     */
    public void setPdfRsvSeatNum(String pdfRsvSeatNum) {
        pdfRsvSeatNum__ = pdfRsvSeatNum;
    }

    /**
     * <p>pdfRsvSmoking を取得します。
     * @return pdfRsvSmoking
     */
    public String getPdfRsvSmoking() {
        return pdfRsvSmoking__;
    }

    /**
     * <p>pdfRsvSmoking をセットします。
     * @param pdfRsvSmoking pdfRsvSmoking
     */
    public void setPdfRsvSmoking(String pdfRsvSmoking) {
        pdfRsvSmoking__ = pdfRsvSmoking;
    }

    /**
     * <p>pdfRsvNum を取得します。
     * @return pdfRsvNum
     */
    public String getPdfRsvNum() {
        return pdfRsvNum__;
    }

    /**
     * <p>pdfRsvNum をセットします。
     * @param pdfRsvNum pdfRsvNum
     */
    public void setPdfRsvNum(String pdfRsvNum) {
        pdfRsvNum__ = pdfRsvNum;
    }

    /**
     * <p>pdfRsvTakeOut を取得します。
     * @return pdfRsvTakeOut
     */
    public String getPdfRsvTakeOut() {
        return pdfRsvTakeOut__;
    }

    /**
     * <p>pdfRsvTakeOut をセットします。
     * @param pdfRsvTakeOut pdfRsvTakeOut
     */
    public void setPdfRsvTakeOut(String pdfRsvTakeOut) {
        pdfRsvTakeOut__ = pdfRsvTakeOut;
    }

    /**
     * <p>pdfRsvCarNumber を取得します。
     * @return pdfRsvCarNumber
     */
    public String getPdfRsvCarNumber() {
        return pdfRsvCarNumber__;
    }

    /**
     * <p>pdfRsvCarNumber をセットします。
     * @param pdfRsvCarNumber pdfRsvCarNumber
     */
    public void setPdfRsvCarNumber(String pdfRsvCarNumber) {
        pdfRsvCarNumber__ = pdfRsvCarNumber;
    }

    /**
     * <p>pdfRsvCarSeatNum を取得します。
     * @return pdfRsvCarSeatNum
     */
    public String getPdfRsvCarSeatNum() {
        return pdfRsvCarSeatNum__;
    }

    /**
     * <p>pdfRsvCarSeatNum をセットします。
     * @param pdfRsvCarSeatNum pdfRsvCarSeatNum
     */
    public void setPdfRsvCarSeatNum(String pdfRsvCarSeatNum) {
        pdfRsvCarSeatNum__ = pdfRsvCarSeatNum;
    }

    /**
     * <p>pdfRsvBookIsbn を取得します。
     * @return pdfRsvBookIsbn
     */
    public String getPdfRsvBookIsbn() {
        return pdfRsvBookIsbn__;
    }

    /**
     * <p>pdfRsvBookIsbn をセットします。
     * @param pdfRsvBookIsbn pdfRsvBookIsbn
     */
    public void setPdfRsvBookIsbn(String pdfRsvBookIsbn) {
        pdfRsvBookIsbn__ = pdfRsvBookIsbn;
    }

    /**
     * <p>pdfRsvBookNum を取得します。
     * @return pdfRsvBookNum
     */
    public String getPdfRsvBookNum() {
        return pdfRsvBookNum__;
    }

    /**
     * <p>pdfRsvBookNum をセットします。
     * @param pdfRsvBookNum pdfRsvBookNum
     */
    public void setPdfRsvBookNum(String pdfRsvBookNum) {
        pdfRsvBookNum__ = pdfRsvBookNum;
    }

    /**
     * <p>pdfRsvAddName を取得します。
     * @return pdfRsvAddName
     */
    public String getPdfRsvAddName() {
        return pdfRsvAddName__;
    }

    /**
     * <p>pdfRsvAddName をセットします。
     * @param pdfRsvAddName pdfRsvAddName
     */
    public void setPdfRsvAddName(String pdfRsvAddName) {
        pdfRsvAddName__ = pdfRsvAddName;
    }

    /**
     * <p>pdfRsvAddDate を取得します。
     * @return pdfRsvAddDate
     */
    public String getPdfRsvAddDate() {
        return pdfRsvAddDate__;
    }

    /**
     * <p>pdfRsvAddDate をセットします。
     * @param pdfRsvAddDate pdfRsvAddDate
     */
    public void setPdfRsvAddDate(String pdfRsvAddDate) {
        pdfRsvAddDate__ = pdfRsvAddDate;
    }

    /**
     * <p>pdfRsvEditName を取得します。
     * @return pdfRsvEditName
     */
    public String getPdfRsvEditName() {
        return pdfRsvEditName__;
    }

    /**
     * <p>pdfRsvEditName をセットします。
     * @param pdfRsvEditName pdfRsvEditName
     */
    public void setPdfRsvEditName(String pdfRsvEditName) {
        pdfRsvEditName__ = pdfRsvEditName;
    }

    /**
     * <p>pdfRsvEditDate を取得します。
     * @return pdfRsvEditDate
     */
    public String getPdfRsvEditDate() {
        return pdfRsvEditDate__;
    }

    /**
     * <p>pdfRsvEditDate をセットします。
     * @param pdfRsvEditDate pdfRsvEditDate
     */
    public void setPdfRsvEditDate(String pdfRsvEditDate) {
        pdfRsvEditDate__ = pdfRsvEditDate;
    }

    /**
     * <p>pdfRsvObj を取得します。
     * @return pdfRsvObj
     */
    public String getPdfRsvObj() {
        return pdfRsvObj__;
    }

    /**
     * <p>pdfRsvObj をセットします。
     * @param pdfRsvObj pdfRsvObj
     */
    public void setPdfRsvObj(String pdfRsvObj) {
        pdfRsvObj__ = pdfRsvObj;
    }

    /**
     * <p>pdfRsvFromDate を取得します。
     * @return pdfRsvFromDate
     */
    public String getPdfRsvFromDate() {
        return pdfRsvFromDate__;
    }

    /**
     * <p>pdfRsvFromDate をセットします。
     * @param pdfRsvFromDate pdfRsvFromDate
     */
    public void setPdfRsvFromDate(String pdfRsvFromDate) {
        pdfRsvFromDate__ = pdfRsvFromDate;
    }

    /**
     * <p>pdfRsvToDate を取得します。
     * @return pdfRsvToDate
     */
    public String getPdfRsvToDate() {
        return pdfRsvToDate__;
    }

    /**
     * <p>pdfRsvToDate をセットします。
     * @param pdfRsvToDate pdfRsvToDate
     */
    public void setPdfRsvToDate(String pdfRsvToDate) {
        pdfRsvToDate__ = pdfRsvToDate;
    }

    /**
     * <p>pdfRsvSubject を取得します。
     * @return pdfRsvSubject
     */
    public String getPdfRsvSubject() {
        return pdfRsvSubject__;
    }

    /**
     * <p>pdfRsvSubject をセットします。
     * @param pdfRsvSubject pdfRsvSubject
     */
    public void setPdfRsvSubject(String pdfRsvSubject) {
        pdfRsvSubject__ = pdfRsvSubject;
    }

    /**
     * <p>pdfRsvEditKbn を取得します。
     * @return pdfRsvEditKbn
     */
    public int getPdfRsvEditKbn() {
        return pdfRsvEditKbn__;
    }

    /**
     * <p>pdfRsvEditKbn をセットします。
     * @param pdfRsvEditKbn pdfRsvEditKbn
     */
    public void setPdfRsvEditKbn(int pdfRsvEditKbn) {
        pdfRsvEditKbn__ = pdfRsvEditKbn;
    }

    /**
     * <p> pdfRsvPublicKbnを取得します。
     * @return pdfRsvPublicKbn
     * */
    public int getPdfRsvPublicKbn() {
        return pdfRsvPublicKbn__;
    }

    /**
     * <p> pdfRsvPublicKbnをセットします。
     * @param pdfRsvPublicKbn pdfRsvPublicKbn
     * */
    public void setPdfRsvPublicKbn(int pdfRsvPublicKbn) {
        pdfRsvPublicKbn__ = pdfRsvPublicKbn;
    }

    /**
     * <p>pdfRsvSchKbn を取得します。
     * @return pdfRsvSchKbn
     */
    public int getPdfRsvSchKbn() {
        return pdfRsvSchKbn__;
    }

    /**
     * <p>pdfRsvSchKbn をセットします。
     * @param pdfRsvSchKbn pdfRsvSchKbn
     */
    public void setPdfRsvSchKbn(int pdfRsvSchKbn) {
        pdfRsvSchKbn__ = pdfRsvSchKbn;
    }

    /**
     * <p>pdfRsvSchGrpName を取得します。
     * @return pdfRsvSchGrpName
     */
    public String getPdfRsvSchGrpName() {
        return pdfRsvSchGrpName__;
    }

    /**
     * <p>pdfRsvSchGrpName をセットします。
     * @param pdfRsvSchGrpName pdfRsvSchGrpName
     */
    public void setPdfRsvSchGrpName(String pdfRsvSchGrpName) {
        pdfRsvSchGrpName__ = pdfRsvSchGrpName;
    }

    /**
     * <p>pdfRsvSchUserList を取得します。
     * @return pdfRsvSchUserList
     */
    public List<String> getPdfRsvSchUserList() {
        return pdfRsvSchUserList__;
    }

    /**
     * <p>pdfRsvSchUserList をセットします。
     * @param pdfRsvSchUserList pdfRsvSchUserList
     */
    public void setPdfRsvSchUserList(List<String> pdfRsvSchUserList) {
        pdfRsvSchUserList__ = pdfRsvSchUserList;
    }

    /**
     * <p>pdfRsvSchUseFlg を取得します。
     * @return pdfRsvSchUseFlg
     */
    public int getPdfRsvSchUseFlg() {
        return pdfRsvSchUseFlg__;
    }

    /**
     * <p>pdfRsvSchUseFlg をセットします。
     * @param pdfRsvSchUseFlg pdfRsvSchUseFlg
     */
    public void setPdfRsvSchUseFlg(int pdfRsvSchUseFlg) {
        pdfRsvSchUseFlg__ = pdfRsvSchUseFlg;
    }

    /**
     * <p>pdfRsvBusyo を取得します。
     * @return pdfRsvBusyo
     */
    public String getPdfRsvBusyo() {
        return pdfRsvBusyo__;
    }

    /**
     * <p>pdfRsvBusyo をセットします。
     * @param pdfRsvBusyo pdfRsvBusyo
     */
    public void setPdfRsvBusyo(String pdfRsvBusyo) {
        pdfRsvBusyo__ = pdfRsvBusyo;
    }

    /**
     * <p>pdfRsvUseName を取得します。
     * @return pdfRsvUseName
     */
    public String getPdfRsvUseName() {
        return pdfRsvUseName__;
    }

    /**
     * <p>pdfRsvUseName をセットします。
     * @param pdfRsvUseName pdfRsvUseName
     */
    public void setPdfRsvUseName(String pdfRsvUseName) {
        pdfRsvUseName__ = pdfRsvUseName;
    }

    /**
     * <p>pdfRsvUseNum を取得します。
     * @return pdfRsvUseNum
     */
    public String getPdfRsvUseNum() {
        return pdfRsvUseNum__;
    }

    /**
     * <p>pdfRsvUseNum をセットします。
     * @param pdfRsvUseNum pdfRsvUseNum
     */
    public void setPdfRsvUseNum(String pdfRsvUseNum) {
        pdfRsvUseNum__ = pdfRsvUseNum;
    }

    /**
     * <p>pdfRsvUseKbn を取得します。
     * @return pdfRsvUseKbn
     */
    public String getPdfRsvUseKbn() {
        return pdfRsvUseKbn__;
    }

    /**
     * <p>pdfRsvUseKbn をセットします。
     * @param pdfRsvUseKbn pdfRsvUseKbn
     */
    public void setPdfRsvUseKbn(String pdfRsvUseKbn) {
        pdfRsvUseKbn__ = pdfRsvUseKbn;
    }

    /**
     * <p>pdfRsvContact を取得します。
     * @return pdfRsvContact
     */
    public String getPdfRsvContact() {
        return pdfRsvContact__;
    }

    /**
     * <p>pdfRsvContact をセットします。
     * @param pdfRsvContact pdfRsvContact
     */
    public void setPdfRsvContact(String pdfRsvContact) {
        pdfRsvContact__ = pdfRsvContact;
    }

    /**
     * <p>pdfRsvGuide を取得します。
     * @return pdfRsvGuide
     */
    public String getPdfRsvGuide() {
        return pdfRsvGuide__;
    }

    /**
     * <p>pdfRsvGuide をセットします。
     * @param pdfRsvGuide pdfRsvGuide
     */
    public void setPdfRsvGuide(String pdfRsvGuide) {
        pdfRsvGuide__ = pdfRsvGuide;
    }

    /**
     * <p>pdfRsvParkNum を取得します。
     * @return pdfRsvParkNum
     */
    public String getPdfRsvParkNum() {
        return pdfRsvParkNum__;
    }

    /**
     * <p>pdfRsvParkNum をセットします。
     * @param pdfRsvParkNum pdfRsvParkNum
     */
    public void setPdfRsvParkNum(String pdfRsvParkNum) {
        pdfRsvParkNum__ = pdfRsvParkNum;
    }

    /**
     * <p>pdfRsvPrintKbn を取得します。
     * @return pdfRsvPrintKbn
     */
    public String getPdfRsvPrintKbn() {
        return pdfRsvPrintKbn__;
    }

    /**
     * <p>pdfRsvPrintKbn をセットします。
     * @param pdfRsvPrintKbn pdfRsvPrintKbn
     */
    public void setPdfRsvPrintKbn(String pdfRsvPrintKbn) {
        pdfRsvPrintKbn__ = pdfRsvPrintKbn;
    }

    /**
     * <p>pdfRsvDest を取得します。
     * @return pdfRsvDest
     */
    public String getPdfRsvDest() {
        return pdfRsvDest__;
    }

    /**
     * <p>pdfRsvDest をセットします。
     * @param pdfRsvDest pdfRsvDest
     */
    public void setPdfRsvDest(String pdfRsvDest) {
        pdfRsvDest__ = pdfRsvDest;
    }

    /**
     * <p>pdfRsvPubUsrGrpList を取得します。
     * @return pdfRsvPubUsrGrpList
     * @see jp.groupsession.v2.rsv.pdf.RsvTanPdfModel#pdfRsvPubUsrGrpList__
     */
    public List<UsrLabelValueBean> getPdfRsvPubUsrGrpList() {
        return pdfRsvPubUsrGrpList__;
    }

    /**
     * <p>pdfRsvPubUsrGrpList をセットします。
     * @param pdfRsvPubUsrGrpList pdfRsvPubUsrGrpList
     * @see jp.groupsession.v2.rsv.pdf.RsvTanPdfModel#pdfRsvPubUsrGrpList__
     */
    public void setPdfRsvPubUsrGrpList(
            List<UsrLabelValueBean> pdfRsvPubUsrGrpList) {
        pdfRsvPubUsrGrpList__ = pdfRsvPubUsrGrpList;
    }
}

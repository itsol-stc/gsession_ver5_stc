package jp.groupsession.v2.cir.pdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 回覧板確認画面のPDF用Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirDtlPdfModel {

    /** 回覧板SID */
    private int cifSid__ = -1;
    /** ファイル名 */
    private String fileName__ = null;
    /** セーブ用ファイル名 */
    private String saveFileName__ = null;
    /** 回覧板モード(受信/送信済) */
    private String cirMode__ = null;
    /** 処理モード */
    private String cmdMode__ = null;

    /** 確認状態 */
    private int cvwConf__ = GSConstCircular.CONF_UNOPEN;
    /** タイトル */
    private String cifTitle__ = null;
    /** アカウント名*/
    private String cirViewAccountName__;
    /** 発信者 */
    private String cacName__ = null;
    /** 発信日時(文字列) */
    private String dspCifAdate__ = null;
    /** 修正区分 */
    private int cifEkbn__ = GSConstCircular.CIR_NO_EDIT;
    /** 修正日時(文字列) */
    private String dspCifEditDate__ = null;
    /** 内容 */
    private String cifValue__ = null;
    /** 回覧板添付ファイル情報 */
    private List<CmnBinfModel> binfMdlList__ = null;

    /** 回覧先確認状況 公開状態 */
    private int privateLevel__ = GSConstCircular.CIR_INIT_SAKI_PUBLIC;
    /** グループフィルタ グループ名 */
    private String cirMemListGroupName__ = "";
    /** ソートキー */
    private int pdfSortKey__ = GSConstCircular.SAKI_SORT_KAKU;
    /** オーダーキー */
    private int pdfOrderKey__ = GSConst.ORDER_KEY_DESC;

    /** 回覧先確認状況 */
    private List<CirDtlPdfMemModel> pdfMemList__ = null;
    /** 回覧先添付ファイル情報 */
    private HashMap<Integer, ArrayList<CmnBinfModel>> memFileNameList__ = null;

    /**
     * <p>cifSid を取得します。
     * @return cifSid
     */
    public int getCifSid() {
        return cifSid__;
    }

    /**
     * <p>cifSid をセットします。
     * @param cifSid cifSid
     */
    public void setCifSid(int cifSid) {
        cifSid__ = cifSid;
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
     * <p>cirMode を取得します。
     * @return cirMode
     */
    public String getCirMode() {
        return cirMode__;
    }

    /**
     * <p>cirMode をセットします。
     * @param cirMode cirMode
     */
    public void setCirMode(String cirMode) {
        cirMode__ = cirMode;
    }

    /**
     * <p>cmdMode を取得します。
     * @return cmdMode
     */
    public String getCmdMode() {
        return cmdMode__;
    }

    /**
     * <p>cmdMode をセットします。
     * @param cmdMode cmdMode
     */
    public void setCmdMode(String cmdMode) {
        cmdMode__ = cmdMode;
    }

    /**
     * <p>cvwConf を取得します。
     * @return cvwConf
     */
    public int getCvwConf() {
        return cvwConf__;
    }

    /**
     * <p>cvwConf をセットします。
     * @param cvwConf cvwConf
     */
    public void setCvwConf(int cvwConf) {
        cvwConf__ = cvwConf;
    }

    /**
     * <p>cifTitle を取得します。
     * @return cifTitle
     */
    public String getCifTitle() {
        return cifTitle__;
    }

    /**
     * <p>cifTitle をセットします。
     * @param cifTitle cifTitle
     */
    public void setCifTitle(String cifTitle) {
        cifTitle__ = cifTitle;
    }

    /**
     * <p>cirViewAccountName を取得します。
     * @return cirViewAccountName
     */
    public String getCirViewAccountName() {
        return cirViewAccountName__;
    }

    /**
     * <p>cirViewAccountName をセットします。
     * @param cirViewAccountName cirViewAccountName
     */
    public void setCirViewAccountName(String cirViewAccountName) {
        cirViewAccountName__ = cirViewAccountName;
    }

    /**
     * <p>cacName を取得します。
     * @return cacName
     */
    public String getCacName() {
        return cacName__;
    }

    /**
     * <p>cacName をセットします。
     * @param cacName cacName
     */
    public void setCacName(String cacName) {
        cacName__ = cacName;
    }

    /**
     * <p>dspCifAdate を取得します。
     * @return dspCifAdate
     */
    public String getDspCifAdate() {
        return dspCifAdate__;
    }

    /**
     * <p>dspCifAdate をセットします。
     * @param dspCifAdate dspCifAdate
     */
    public void setDspCifAdate(String dspCifAdate) {
        dspCifAdate__ = dspCifAdate;
    }

    /**
     * <p>cifEkbn を取得します。
     * @return cifEkbn
     */
    public int getCifEkbn() {
        return cifEkbn__;
    }

    /**
     * <p>cifEkbn をセットします。
     * @param cifEkbn cifEkbn
     */
    public void setCifEkbn(int cifEkbn) {
        cifEkbn__ = cifEkbn;
    }

    /**
     * <p>dspCifEditDate を取得します。
     * @return dspCifEditDate
     */
    public String getDspCifEditDate() {
        return dspCifEditDate__;
    }

    /**
     * <p>dspCifEditDate をセットします。
     * @param dspCifEditDate dspCifEditDate
     */
    public void setDspCifEditDate(String dspCifEditDate) {
        dspCifEditDate__ = dspCifEditDate;
    }

    /**
     * <p>cifValue を取得します。
     * @return cifValue
     */
    public String getCifValue() {
        return cifValue__;
    }

    /**
     * <p>cifValue をセットします。
     * @param cifValue cifValue
     */
    public void setCifValue(String cifValue) {
        cifValue__ = cifValue;
    }

    /**
     * <p>binfMdlList を取得します。
     * @return binfMdlList
     */
    public List<CmnBinfModel> getBinfMdlList() {
        return binfMdlList__;
    }

    /**
     * <p>binfMdlList をセットします。
     * @param binfMdlList binfMdlList
     */
    public void setBinfMdlList(List<CmnBinfModel> binfMdlList) {
        this.binfMdlList__ = binfMdlList;
    }

    /**
     * <p>privateLevel を取得します。
     * @return privateLevel
     */
    public int getPrivateLevel() {
        return privateLevel__;
    }

    /**
     * <p>privateLevel をセットします。
     * @param privateLevel privateLevel
     */
    public void setPrivateLevel(int privateLevel) {
        privateLevel__ = privateLevel;
    }

    /**
     * <p>cirMemListGroupName を取得します。
     * @return cirMemListGroupName
     */
    public String getCirMemListGroupName() {
        return cirMemListGroupName__;
    }

    /**
     * <p>cirMemListGroupName をセットします。
     * @param cirMemListGroupName cirMemListGroupName
     */
    public void setCirMemListGroupName(String cirMemListGroupName) {
        cirMemListGroupName__ = cirMemListGroupName;
    }

    /**
     * <p>pdfSortKey を取得します。
     * @return pdfSortKey
     */
    public int getPdfSortKey() {
        return pdfSortKey__;
    }

    /**
     * <p>pdfSortKey をセットします。
     * @param pdfSortKey pdfSortKey
     */
    public void setPdfSortKey(int pdfSortKey) {
        pdfSortKey__ = pdfSortKey;
    }

    /**
     * <p>pdfOrderKey を取得します。
     * @return pdfOrderKey
     */
    public int getPdfOrderKey() {
        return pdfOrderKey__;
    }

    /**
     * <p>pdfOrderKey をセットします。
     * @param pdfOrderKey pdfOrderKey
     */
    public void setPdfOrderKey(int pdfOrderKey) {
        pdfOrderKey__ = pdfOrderKey;
    }

    /**
     * <p>pdfMemList を取得します。
     * @return pdfMemList
     */
    public List<CirDtlPdfMemModel> getPdfMemList() {
        return pdfMemList__;
    }

    /**
     * <p>pdfMemList をセットします。
     * @param pdfMemList pdfMemList
     */
    public void setPdfMemList(List<CirDtlPdfMemModel> pdfMemList) {
        pdfMemList__ = pdfMemList;
    }

    /**
     * <p>memFileNameList を取得します。
     * @return memFileNameList
     */
    public HashMap<Integer, ArrayList<CmnBinfModel>> getMemFileNameList() {
        return memFileNameList__;
    }

    /**
     * <p>memFileNameList をセットします。
     * @param memFileNameList memFileNameList
     */
    public void setMemFileNameList(
            HashMap<Integer, ArrayList<CmnBinfModel>> memFileNameList) {
        memFileNameList__ = memFileNameList;
    }

}

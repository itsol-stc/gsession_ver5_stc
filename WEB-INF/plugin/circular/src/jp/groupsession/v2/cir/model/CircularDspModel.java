package jp.groupsession.v2.cir.model;

import java.io.Serializable;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 回覧板の画面表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CircularDspModel implements Serializable {

    /** CVW_MEMO mapping */
    private String cvwMemo__;
    /** CVW_CONF mapping */
    private int cvwConf__;
    /** CVW_DSP mapping*/
    private int cvwDsp__;
    /** CVW_CONF_DATE mapping */
    private UDate cvwConfDate__;
    /** CVW_EDATE mapping */
    private UDate cvwEdate__;
    /** CIF_SID mapping */
    private int cifSid__;
    /** CIF_TITLE mapping */
    private String cifTitle__;
    /** CIF_ADATE mapping */
    private UDate cifAdate__;
    /** CIF_ADATE mapping */
    private UDate cifEdate__;
    /** CIF_EKBN mapping */
    private int cifEkbn__;
    /** CIF_EDIT_DATE mapping */
    private UDate cifEditDate__;
    /** CIF_VALUE mapping */
    private String cifValue__;
    /** CIF_SHOW mapping */
    private int cifShow__;
    /** CIF_MEMO_FLG mapping */
    private int cifMemoFlg__;
    /** CIF_MEMO_DATE mapping */
    private UDate cifMemoDate__;
    /** GRP_NAME mapping */
    private String grpName__;
    /** GRP_JKBN mapping */
    private int grpJkbn__;
//    /** USR_JKBN mapping */
//    private int usrJkbn__;
    /** CAC_JKBN mapping */
    private int cacJkbn__;
//    /** USR_SID mapping */
//    private int usrSid__;
    /** CAC_SID mapping */
    private int cacSid__;
    /** CAC_NAME mapping */
    private String cacName__;
    /** USR_UKO_FLG*/
    private int usrUkoFlg__ = 0;
//    /** USI_SEI mapping */
//    private String usiSei__;
//    /** USI_MEI mapping */
//    private String usiMei__;
    /** VIEW_UKO_FLG*/
    private int viewUkoFlg__ = 0;
    /** USI_SYAIN_NO mapping */
    private String syainNo__;
    /** POS_NAME mapping */
    private String posName__;
    /** CVW_EDATE (表示用) */
    private String dspCvwEdate__;
    /** CVW_EDATE (表示用 日付) */
    private String dspCvwEdateDate__;
    /** CVW_EDATE (表示用 時間) */
    private String dspCvwEdateTime__;
    /** CIF_ADATE(表示用) */
    private String dspCifAdate__;
    /** CIF_EDIT_DATE(表示用) */
    private String dspCifEditDate__;
    /** CIF_MEMO_DATE(表示用) */
    private String dspCifMemoDate__;
    /** JS_FLG 受信・送信フラグ mapping */
    private String jsFlg__;
    /** 受信者数 */
    private int allCount__;
    /** 確認済み数 */
    private int openCount__;
    /** 確認時間 */
    private String openTime__;
    /** 確認時間 日付 */
    private String openTimeDate__;
    /** 確認時間 時間 */
    private String openTimeTime__;
    /** 回覧板プラグイン使用可/不可 */
    private boolean circularUse__ = true;
    /** 新規回覧先フラグ */
    private boolean newCirUser__ = false;
    /** ユーザ添付情報 */
    private List<CmnBinfModel> dspUserTmpFileList__ = null;
    /** ラベル情報 */
    private String labelName__;

    /**
     * <p>cifAdate を取得します。
     * @return cifAdate
     */
    public UDate getCifAdate() {
        return cifAdate__;
    }
    /**
     * <p>cifAdate をセットします。
     * @param cifAdate cifAdate
     */
    public void setCifAdate(UDate cifAdate) {
        cifAdate__ = cifAdate;
    }
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
     * <p>cvwDspを取得します。
     * @return cvwDsp
     */
    public int getCvwDsp() {
        return cvwDsp__;
    }
    /**
     * <p>cvwDspをセットします。
     * @param cvwDsp cvwDsp
     */
    public void setCvwDsp(int cvwDsp) {
        cvwDsp__ = cvwDsp;
    }
    /**
     * <p>grpJkbn を取得します。
     * @return grpJkbn
     */
    public int getGrpJkbn() {
        return grpJkbn__;
    }
    /**
     * <p>grpJkbn をセットします。
     * @param grpJkbn grpJkbn
     */
    public void setGrpJkbn(int grpJkbn) {
        grpJkbn__ = grpJkbn;
    }
    /**
     * <p>grpName を取得します。
     * @return grpName
     */
    public String getGrpName() {
        return grpName__;
    }
    /**
     * <p>grpName をセットします。
     * @param grpName grpName
     */
    public void setGrpName(String grpName) {
        grpName__ = grpName;
    }
//    /**
//     * <p>usiMei を取得します。
//     * @return usiMei
//     */
//    public String getUsiMei() {
//        return usiMei__;
//    }
//    /**
//     * <p>usiMei をセットします。
//     * @param usiMei usiMei
//     */
//    public void setUsiMei(String usiMei) {
//        usiMei__ = usiMei;
//    }
//    /**
//     * <p>usiSei を取得します。
//     * @return usiSei
//     */
//    public String getUsiSei() {
//        return usiSei__;
//    }
//    /**
//     * <p>usiSei をセットします。
//     * @param usiSei usiSei
//     */
//    public void setUsiSei(String usiSei) {
//        usiSei__ = usiSei;
//    }

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
     * <p>cifShow を取得します。
     * @return cifShow
     */
    public int getCifShow() {
        return cifShow__;
    }
    /**
     * <p>cifShow をセットします。
     * @param cifShow cifShow
     */
    public void setCifShow(int cifShow) {
        cifShow__ = cifShow;
    }
    /**
     * <p>cvwMemo を取得します。
     * @return cvwMemo
     */
    public String getCvwMemo() {
        return cvwMemo__;
    }
    /**
     * <p>cvwMemo をセットします。
     * @param cvwMemo cvwMemo
     */
    public void setCvwMemo(String cvwMemo) {
        cvwMemo__ = cvwMemo;
    }
    /**
     * <p>jsFlg を取得します。
     * @return jsFlg
     */
    public String getJsFlg() {
        return jsFlg__;
    }
    /**
     * <p>jsFlg をセットします。
     * @param jsFlg jsFlg
     */
    public void setJsFlg(String jsFlg) {
        jsFlg__ = jsFlg;
    }
    /**
     * <p>allCount を取得します。
     * @return allCount
     */
    public int getAllCount() {
        return allCount__;
    }
    /**
     * <p>allCount をセットします。
     * @param allCount allCount
     */
    public void setAllCount(int allCount) {
        allCount__ = allCount;
    }
    /**
     * <p>openCount を取得します。
     * @return openCount
     */
    public int getOpenCount() {
        return openCount__;
    }
    /**
     * <p>openCount をセットします。
     * @param openCount openCount
     */
    public void setOpenCount(int openCount) {
        openCount__ = openCount;
    }
    /**
     * <p>openTime を取得します。
     * @return openTime
     */
    public String getOpenTime() {
        return openTime__;
    }
    /**
     * <p>openTime をセットします。
     * @param openTime openTime
     */
    public void setOpenTime(String openTime) {
        openTime__ = openTime;
    }
    /**
     * <p>cvwEdate を取得します。
     * @return cvwEdate
     */
    public UDate getCvwEdate() {
        return cvwEdate__;
    }
    /**
     * <p>cvwEdate をセットします。
     * @param cvwEdate cvwEdate
     */
    public void setCvwEdate(UDate cvwEdate) {
        cvwEdate__ = cvwEdate;
    }
    /**
     * <p>cvwConfDate を取得します。
     * @return cvwConfDate
     */
    public UDate getCvwConfDate() {
        return cvwConfDate__;
    }
    /**
     * <p>cvwConfDate をセットします。
     * @param cvwConfDate cvwConfDate
     */
    public void setCvwConfDate(UDate cvwConfDate) {
        cvwConfDate__ = cvwConfDate;
    }
    /**
     * <p>circularUse を取得します。
     * @return circularUse
     */
    public boolean isCircularUse() {
        return circularUse__;
    }
    /**
     * <p>circularUse をセットします。
     * @param circularUse circularUse
     */
    public void setCircularUse(boolean circularUse) {
        circularUse__ = circularUse;
    }
    /**
     * @return cifMemoFlg
     */
    public int getCifMemoFlg() {
        return cifMemoFlg__;
    }
    /**
     * @param cifMemoFlg セットする cifMemoFlg
     */
    public void setCifMemoFlg(int cifMemoFlg) {
        cifMemoFlg__ = cifMemoFlg;
    }
    /**
     * @return cifMemoDate
     */
    public UDate getCifMemoDate() {
        return cifMemoDate__;
    }
    /**
     * @param cifMemoDate セットする cifMemoDate
     */
    public void setCifMemoDate(UDate cifMemoDate) {
        cifMemoDate__ = cifMemoDate;
    }
    /**
     * @return dspCifMemoDate
     */
    public String getDspCifMemoDate() {
        return dspCifMemoDate__;
    }
    /**
     * @param dspCifMemoDate セットする dspCifMemoDate
     */
    public void setDspCifMemoDate(String dspCifMemoDate) {
        dspCifMemoDate__ = dspCifMemoDate;
    }
    /**
     * @return dspCvwEdate
     */
    public String getDspCvwEdate() {
        return dspCvwEdate__;
    }
    /**
     * @param dspCvwEdate セットする dspCvwEdate
     */
    public void setDspCvwEdate(String dspCvwEdate) {
        dspCvwEdate__ = dspCvwEdate;
    }
    /**
     * <p>newCirUser を取得します。
     * @return newCirUser
     */
    public boolean isNewCirUser() {
        return newCirUser__;
    }
    /**
     * <p>newCirUser をセットします。
     * @param newCirUser newCirUser
     */
    public void setNewCirUser(boolean newCirUser) {
        newCirUser__ = newCirUser;
    }
    /**
     * <p>syainNo を取得します。
     * @return syainNo
     */
    public String getSyainNo() {
        return syainNo__;
    }
    /**
     * <p>syainNo をセットします。
     * @param syainNo syainNo
     */
    public void setSyainNo(String syainNo) {
        syainNo__ = syainNo;
    }
    /**
     * <p>posName を取得します。
     * @return posName
     */
    public String getPosName() {
        return posName__;
    }
    /**
     * <p>posName をセットします。
     * @param posName posName
     */
    public void setPosName(String posName) {
        posName__ = posName;
    }
    /**
     * <p>dspUserTmpFileList を取得します。
     * @return dspUserTmpFileList
     */
    public List<CmnBinfModel> getDspUserTmpFileList() {
        return dspUserTmpFileList__;
    }
    /**
     * <p>dspUserTmpFileList をセットします。
     * @param dspUserTmpFileList dspUserTmpFileList
     */
    public void setDspUserTmpFileList(List<CmnBinfModel> dspUserTmpFileList) {
        dspUserTmpFileList__ = dspUserTmpFileList;
    }

    /**
     * <p>cacJkbn を取得します。
     * @return cacJkbn
     */
    public int getCacJkbn() {
        return cacJkbn__;
    }
    /**
     * <p>cacJkbn をセットします。
     * @param cacJkbn cacJkbn
     */
    public void setCacJkbn(int cacJkbn) {
        cacJkbn__ = cacJkbn;
    }
    /**
     * <p>cacSid を取得します。
     * @return cacSid
     */
    public int getCacSid() {
        return cacSid__;
    }
    /**
     * <p>cacSid をセットします。
     * @param cacSid cacSid
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
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
     * <p>cifEdate を取得します。
     * @return cifEdate
     */
    public UDate getCifEdate() {
        return cifEdate__;
    }
    /**
     * <p>cifEdate をセットします。
     * @param cifEdate cifEdate
     */
    public void setCifEdate(UDate cifEdate) {
        cifEdate__ = cifEdate;
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
     * <p>cifEditDate を取得します。
     * @return cifEditDate
     */
    public UDate getCifEditDate() {
        return cifEditDate__;
    }
    /**
     * <p>cifEditDate をセットします。
     * @param cifEditDate cifEditDate
     */
    public void setCifEditDate(UDate cifEditDate) {
        cifEditDate__ = cifEditDate;
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
     * <p>dspCvwEdateDate を取得します。
     * @return dspCvwEdateDate
     */
    public String getDspCvwEdateDate() {
        return dspCvwEdateDate__;
    }
    /**
     * <p>dspCvwEdateDate をセットします。
     * @param dspCvwEdateDate dspCvwEdateDate
     */
    public void setDspCvwEdateDate(String dspCvwEdateDate) {
        dspCvwEdateDate__ = dspCvwEdateDate;
    }
    /**
     * <p>dspCvwEdateTime を取得します。
     * @return dspCvwEdateTime
     */
    public String getDspCvwEdateTime() {
        return dspCvwEdateTime__;
    }
    /**
     * <p>dspCvwEdateTime をセットします。
     * @param dspCvwEdateTime dspCvwEdateTime
     */
    public void setDspCvwEdateTime(String dspCvwEdateTime) {
        dspCvwEdateTime__ = dspCvwEdateTime;
    }
    /**
     * <p>openTimeDate を取得します。
     * @return openTimeDate
     */
    public String getOpenTimeDate() {
        return openTimeDate__;
    }
    /**
     * <p>openTimeDate をセットします。
     * @param openTimeDate openTimeDate
     */
    public void setOpenTimeDate(String openTimeDate) {
        openTimeDate__ = openTimeDate;
    }
    /**
     * <p>openTimeTime を取得します。
     * @return openTimeTime
     */
    public String getOpenTimeTime() {
        return openTimeTime__;
    }
    /**
     * <p>openTimeTime をセットします。
     * @param openTimeTime openTimeTime
     */
    public void setOpenTimeTime(String openTimeTime) {
        openTimeTime__ = openTimeTime;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
    /**
     * <p>viewUkoFlg を取得します。
     * @return viewUkoFlg
     */
    public int getViewUkoFlg() {
        return viewUkoFlg__;
    }
    /**
     * <p>viewUkoFlg をセットします。
     * @param viewUkoFlg viewUkoFlg
     */
    public void setViewUkoFlg(int viewUkoFlg) {
        viewUkoFlg__ = viewUkoFlg;
    }
    /**
     * <p>labelName を取得します。
     * @return labelName
     * @see jp.groupsession.v2.cir.model.CircularDspModel#labelName__
     */
    public String getLabelName() {
        return labelName__;
    }
    /**
     * <p>labelName をセットします。
     * @param labelName labelName
     * @see jp.groupsession.v2.cir.model.CircularDspModel#labelName__
     */
    public void setLabelName(String labelName) {
        labelName__ = labelName;
    }
}

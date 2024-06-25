package jp.groupsession.v2.rsv.rsv150;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.rsv140.Rsv140Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 個人設定 表示設定画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv150Form extends Rsv140Form {

    /** 施設コンボリスト */
    private ArrayList<LabelValueBean> rsv150sisetuLabelList__ = null;
    /** 施設コンボリスト 選択SID */
    private int rsv150SelectedGrpSid__ = GSConstReserve.COMBO_PLEASE_CHOICE;
    /** 表示項目1 利用目的 */
    private String rsv150DispItem1__;
    /** 表示項目2 登録者名 */
    private String rsv150DispItem2__;
    /** 初期表示フラグ */
    private int rsv150initDspFlg__ = 0;
    /** 自動リロード時間コンボ */
    private List < LabelValueBean > rsv150TimeLabelList__ = null;
    /** 自動リロード時間の選択値 */
    private String rsv150ReloadTime__ = null;
    /** 施設画像表示区分 */
    private int rsv150ImgDspKbn__ = GSConstReserve.SISETU_IMG_ON;
    /** デフォルト表示画面 */
    private int rsv150DefDsp__ = 0;

    /** 日間表示時間帯設定 表示区分 */
    private int rsv150DateKbn__ = 0;
    /** 時間コンボリスト */
    private ArrayList<LabelValueBean> rsv150ourLabelList__ = null;
    /** 時間コンボ 選択 */
    private String rsv150SelectedFromSid__ = "";
    /** 時間コンボ 選択 */
    private String rsv150SelectedToSid__ = "";

    /** 表示件数 */
    private int rsv150ViewCnt__ = -1;
    /** 表示件数コンボ */
    private ArrayList<LabelValueBean> rsv150DspCntList__ = null;
    /**
     * <br>[機  能] 入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String eprefix = "reserve";
        GsMessage gsMsg = new GsMessage();

        if ((rsv150DispItem1__ == null || rsv150DispItem1__.equals("0"))
                && (rsv150DispItem2__ == null || rsv150DispItem2__.equals("0"))) {
                msg = new ActionMessage("error.select.required.text",
                        gsMsg.getMessage(req, "reserve.100"));
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "rsv150DispItem");
        }

        //時間帯設定整合性チェック
        if (rsv150DateKbn__ == GSConstReserve.AUTH_ALL_USER) {
            if (Integer.parseInt(rsv150SelectedFromSid__) > Integer.parseInt(rsv150SelectedToSid__)) {
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage(req, "cmn.show.timezone.days.setting"),
                        gsMsg.getMessage(req, "cmn.start.or.end"));
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "rsv160SelectedFromSid__");
            }
        }
        return errors;
    }
    /**
     * <p>rsv150ImgDspKbn を取得します。
     * @return rsv150ImgDspKbn
     */
    public int getRsv150ImgDspKbn() {
        return rsv150ImgDspKbn__;
    }
    /**
     * <p>rsv150ImgDspKbn をセットします。
     * @param rsv150ImgDspKbn rsv150ImgDspKbn
     */
    public void setRsv150ImgDspKbn(int rsv150ImgDspKbn) {
        rsv150ImgDspKbn__ = rsv150ImgDspKbn;
    }
    /**
     * <p>rsv150initDspFlg を取得します。
     * @return rsv150initDspFlg
     */
    public int getRsv150initDspFlg() {
        return rsv150initDspFlg__;
    }
    /**
     * <p>rsv150initDspFlg をセットします。
     * @param rsv150initDspFlg rsv150initDspFlg
     */
    public void setRsv150initDspFlg(int rsv150initDspFlg) {
        rsv150initDspFlg__ = rsv150initDspFlg;
    }
    /**
     * <p>rsv150DispItem1 を取得します。
     * @return rsv150DispItem1
     */
    public String getRsv150DispItem1() {
        return rsv150DispItem1__;
    }
    /**
     * <p>rsv150DispItem1 をセットします。
     * @param rsv150DispItem1 rsv150DispItem1
     */
    public void setRsv150DispItem1(String rsv150DispItem1) {
        rsv150DispItem1__ = rsv150DispItem1;
    }
    /**
     * <p>rsv150DispItem2 を取得します。
     * @return rsv150DispItem2
     */
    public String getRsv150DispItem2() {
        return rsv150DispItem2__;
    }
    /**
     * <p>rsv150DispItem2 をセットします。
     * @param rsv150DispItem2 rsv150DispItem2
     */
    public void setRsv150DispItem2(String rsv150DispItem2) {
        rsv150DispItem2__ = rsv150DispItem2;
    }
    /**
     * <p>rsv150SelectedGrpSid をセットします。
     * @param rsv150SelectedGrpSid rsv150SelectedGrpSid
     */
    public void setRsv150SelectedGrpSid(int rsv150SelectedGrpSid) {
        rsv150SelectedGrpSid__ = rsv150SelectedGrpSid;
    }
    /**
     * <p>rsv150sisetuLabelList を取得します。
     * @return rsv150sisetuLabelList
     */
    public ArrayList<LabelValueBean> getRsv150sisetuLabelList() {
        return rsv150sisetuLabelList__;
    }
    /**
     * <p>rsv150sisetuLabelList をセットします。
     * @param rsv150sisetuLabelList rsv150sisetuLabelList
     */
    public void setRsv150sisetuLabelList(
            ArrayList<LabelValueBean> rsv150sisetuLabelList) {
        rsv150sisetuLabelList__ = rsv150sisetuLabelList;
    }
    /**
     * <p>rsv150SelectedGrpSid を取得します。
     * @return rsv150SelectedGrpSid
     */
    public int getRsv150SelectedGrpSid() {
        return rsv150SelectedGrpSid__;
    }
    /**
     * <p>rsv150ReloadTime を取得します。
     * @return rsv150ReloadTime
     */
    public String getRsv150ReloadTime() {
        return rsv150ReloadTime__;
    }
    /**
     * <p>rsv150ReloadTime をセットします。
     * @param rsv150ReloadTime rsv150ReloadTime
     */
    public void setRsv150ReloadTime(String rsv150ReloadTime) {
        rsv150ReloadTime__ = rsv150ReloadTime;
    }
    /**
     * <p>rsv150TimeLabelList を取得します。
     * @return rsv150TimeLabelList
     */
    public List<LabelValueBean> getRsv150TimeLabelList() {
        return rsv150TimeLabelList__;
    }
    /**
     * <p>rsv150TimeLabelList をセットします。
     * @param rsv150TimeLabelList rsv150TimeLabelList
     */
    public void setRsv150TimeLabelList(List<LabelValueBean> rsv150TimeLabelList) {
        rsv150TimeLabelList__ = rsv150TimeLabelList;
    }
    /**
     * <p>rsv150DefDsp を取得します。
     * @return rsv150DefDsp
     */
    public int getRsv150DefDsp() {
        return rsv150DefDsp__;
    }
    /**
     * <p>rsv150DefDsp をセットします。
     * @param rsv150DefDsp rsv150DefDsp
     */
    public void setRsv150DefDsp(int rsv150DefDsp) {
        rsv150DefDsp__ = rsv150DefDsp;
    }
    /**
     * <p>rsv150DateKbn を取得します。
     * @return rsv150DateKbn
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150DateKbn__
     */
    public int getRsv150DateKbn() {
        return rsv150DateKbn__;
    }
    /**
     * <p>rsv150DateKbn をセットします。
     * @param rsv150DateKbn rsv150DateKbn
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150DateKbn__
     */
    public void setRsv150DateKbn(int rsv150DateKbn) {
        rsv150DateKbn__ = rsv150DateKbn;
    }
    /**
     * <p>rsv150ourLabelList を取得します。
     * @return rsv150ourLabelList
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150ourLabelList__
     */
    public ArrayList<LabelValueBean> getRsv150ourLabelList() {
        return rsv150ourLabelList__;
    }
    /**
     * <p>rsv150ourLabelList をセットします。
     * @param rsv150ourLabelList rsv150ourLabelList
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150ourLabelList__
     */
    public void setRsv150ourLabelList(
            ArrayList<LabelValueBean> rsv150ourLabelList) {
        rsv150ourLabelList__ = rsv150ourLabelList;
    }
    /**
     * <p>rsv150SelectedFromSid を取得します。
     * @return rsv150SelectedFromSid
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150SelectedFromSid__
     */
    public String getRsv150SelectedFromSid() {
        return rsv150SelectedFromSid__;
    }
    /**
     * <p>rsv150SelectedFromSid をセットします。
     * @param rsv150SelectedFromSid rsv150SelectedFromSid
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150SelectedFromSid__
     */
    public void setRsv150SelectedFromSid(String rsv150SelectedFromSid) {
        rsv150SelectedFromSid__ = rsv150SelectedFromSid;
    }
    /**
     * <p>rsv150SelectedToSid を取得します。
     * @return rsv150SelectedToSid
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150SelectedToSid__
     */
    public String getRsv150SelectedToSid() {
        return rsv150SelectedToSid__;
    }
    /**
     * <p>rsv150SelectedToSid をセットします。
     * @param rsv150SelectedToSid rsv150SelectedToSid
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150SelectedToSid__
     */
    public void setRsv150SelectedToSid(String rsv150SelectedToSid) {
        rsv150SelectedToSid__ = rsv150SelectedToSid;
    }
    /**
     * <p>rsv150ViewCnt を取得します。
     * @return rsv150ViewCnt
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150ViewCnt__
     */
    public int getRsv150ViewCnt() {
        return rsv150ViewCnt__;
    }
    /**
     * <p>rsv150ViewCnt をセットします。
     * @param rsv150ViewCnt rsv150ViewCnt
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150ViewCnt__
     */
    public void setRsv150ViewCnt(int rsv150ViewCnt) {
        rsv150ViewCnt__ = rsv150ViewCnt;
    }
    /**
     * <p>rsv150DspCntList を取得します。
     * @return rsv150DspCntList
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150DspCntList__
     */
    public ArrayList<LabelValueBean> getRsv150DspCntList() {
        return rsv150DspCntList__;
    }
    /**
     * <p>rsv150DspCntList をセットします。
     * @param rsv150DspCntList rsv150DspCntList
     * @see jp.groupsession.v2.rsv.rsv150.Rsv150Form#rsv150DspCntList__
     */
    public void setRsv150DspCntList(ArrayList<LabelValueBean> rsv150DspCntList) {
        rsv150DspCntList__ = rsv150DspCntList;
    }
}
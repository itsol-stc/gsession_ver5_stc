package jp.groupsession.v2.enq.enq810;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq800.Enq800ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート 個人設定 表示設定画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq810ParamModel extends Enq800ParamModel {

    /** 表示件数選択値 */
    private int enq810SelectCnt__ = 0;
    /** 表示件数一覧 */
    private ArrayList<LabelValueBean> enq810CntListLabel__ = null;

    /** 初期表示フォルダ選択値 */
    private String enq810SelectFolder__ = null;
    /** 初期表示フォルダ一覧 */
    private ArrayList<LabelValueBean> enq810FolderListLabel__ = null;

    /** 回答可能のみ表示 */
    private boolean enq810CanAnswer__ = false;

    /** メイン表示 */
    private int enq810MainDsp__ = 0;
    /** メイン表示件数 */
    private int enq810DspCntMain__ = GSConstEnquete.MAIN_DSPCNT_5;
    /** 回答済みアンケート表示の有無 */
    private int enq810DspChecked__ = GSConstEnquete.DSP_CHECKED_NONE;
    /** メイン表示件数 コンボボックス */
    private List<LabelValueBean> dspCntComb__ = null;

    /** 表示設定 使用可能フラグ */
    private int enq810DspUse__ = 0;
    /** メイン表示設定 使用可能フラグ */
    private int enq810DspMainUse__ = 0;

    /**
     * <p>enq810SelectCnt を取得します。
     * @return enq810SelectCnt
     */
    public int getEnq810SelectCnt() {
        return enq810SelectCnt__;
    }
    /**
     * <p>enq810SelectCnt をセットします。
     * @param enq810SelectCnt enq810SelectCnt
     */
    public void setEnq810SelectCnt(int enq810SelectCnt) {
        enq810SelectCnt__ = enq810SelectCnt;
    }
    /**
     * <p>enq810CntListLabel を取得します。
     * @return enq810CntListLabel
     */
    public ArrayList<LabelValueBean> getEnq810CntListLabel() {
        return enq810CntListLabel__;
    }
    /**
     * <p>enq810CntListLabel をセットします。
     * @param enq810CntListLabel enq810CntListLabel
     */
    public void setEnq810CntListLabel(ArrayList<LabelValueBean> enq810CntListLabel) {
        enq810CntListLabel__ = enq810CntListLabel;
    }
    /**
     * <p>enq810SelectFolder を取得します。
     * @return enq810SelectFolder
     */
    public String getEnq810SelectFolder() {
        return enq810SelectFolder__;
    }
    /**
     * <p>enq810SelectFolder をセットします。
     * @param enq810SelectFolder enq810SelectFolder
     */
    public void setEnq810SelectFolder(String enq810SelectFolder) {
        enq810SelectFolder__ = enq810SelectFolder;
    }
    /**
     * <p>enq810FolderListLabel を取得します。
     * @return enq810FolderListLabel
     */
    public ArrayList<LabelValueBean> getEnq810FolderListLabel() {
        return enq810FolderListLabel__;
    }
    /**
     * <p>enq810FolderListLabel をセットします。
     * @param enq810FolderListLabel enq810FolderListLabel
     */
    public void setEnq810FolderListLabel(
            ArrayList<LabelValueBean> enq810FolderListLabel) {
        enq810FolderListLabel__ = enq810FolderListLabel;
    }
    /**
     * <p>enq810CanAnswer を取得します。
     * @return enq810CanAnswer
     */
    public boolean getEnq810CanAnswer() {
        return enq810CanAnswer__;
    }
    /**
     * <p>enq810CanAnswer をセットします。
     * @param enq810CanAnswer enq810CanAnswer
     */
    public void setEnq810CanAnswer(boolean enq810CanAnswer) {
        enq810CanAnswer__ = enq810CanAnswer;
    }
    /**
     * <p>メイン表示 を取得します。
     * @return メイン表示
     */
    public int getEnq810MainDsp() {
        return enq810MainDsp__;
    }
    /**
     * <p>メイン表示 をセットします。
     * @param enq810MainDsp メイン表示
     */
    public void setEnq810MainDsp(int enq810MainDsp) {
        enq810MainDsp__ = enq810MainDsp;
    }

    /**
     * <br>[機  能] メイン表示設定更新時のオペレーションログ内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return [メイン表示区分]管理者が設定する or 各ユーザが設定する
     *         [メイン表示]表示する or 表示しない
     */
    public String getLogText(RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String ret = "[" + gsMsg.getMessage("enq.enq810.01") + "]";
        if (getEnq810MainDsp() == GSConstEnquete.CONF_MAIN_DSP_OFF) {
            ret += gsMsg.getMessage("cmn.dont.show");
        } else {
            ret += gsMsg.getMessage("cmn.display.ok");
        }

        return ret;
    }
    /**
     * <p>enq810DspCntMain を取得します。
     * @return enq810DspCntMain
     * @see jp.groupsession.v2.enq.enq810.Enq810Form#enq810DspCntMain__
     */
    public int getEnq810DspCntMain() {
        return enq810DspCntMain__;
    }
    /**
     * <p>enq810DspCntMain をセットします。
     * @param enq810DspCntMain enq810DspCntMain
     * @see jp.groupsession.v2.enq.enq810.Enq810Form#enq810DspCntMain__
     */
    public void setEnq810DspCntMain(int enq810DspCntMain) {
        enq810DspCntMain__ = enq810DspCntMain;
    }
    /**
     * <p>enq810DspChecked を取得します。
     * @return enq810DspChecked
     * @see jp.groupsession.v2.enq.enq810.Enq810Form#enq810DspChecked__
     */
    public int getEnq810DspChecked() {
        return enq810DspChecked__;
    }
    /**
     * <p>enq810DspChecked をセットします。
     * @param enq810DspChecked enq810DspChecked
     * @see jp.groupsession.v2.enq.enq810.Enq810Form#enq810DspChecked__
     */
    public void setEnq810DspChecked(int enq810DspChecked) {
        enq810DspChecked__ = enq810DspChecked;
    }
    /**
     * <p>dspCntComb を取得します。
     * @return dspCntComb
     * @see jp.groupsession.v2.enq.enq810.Enq810Form#dspCntComb__
     */
    public List<LabelValueBean> getDspCntComb() {
        return dspCntComb__;
    }
    /**
     * <p>dspCntComb をセットします。
     * @param dspCntComb dspCntComb
     * @see jp.groupsession.v2.enq.enq810.Enq810Form#dspCntComb__
     */
    public void setDspCntComb(List<LabelValueBean> dspCntComb) {
        dspCntComb__ = dspCntComb;
    }
    /**
     * <p>enq810DspUse を取得します。
     * @return enq810DspUse
     */
    public int getEnq810DspUse() {
        return enq810DspUse__;
    }
    /**
     * <p>enq810DspUse をセットします。
     * @param enq810DspUse enq810DspUse
     */
    public void setEnq810DspUse(int enq810DspUse) {
        enq810DspUse__ = enq810DspUse;
    }
    /**
     * <p>enq810DspMainUse を取得します。
     * @return enq810DspMainUse
     */
    public int getEnq810DspMainUse() {
        return enq810DspMainUse__;
    }
    /**
     * <p>enq810DspMainUse をセットします。
     * @param enq810DspMainUse enq810DspMainUse
     */
    public void setEnq810DspMainUse(int enq810DspMainUse) {
        enq810DspMainUse__ = enq810DspMainUse;
    }
}

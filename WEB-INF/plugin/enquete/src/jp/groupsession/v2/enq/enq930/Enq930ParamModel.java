package jp.groupsession.v2.enq.enq930;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq900.Enq900ParamModel;

/**
 * <br>[機  能] 管理者設定 表示設定画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq930ParamModel extends Enq900ParamModel {

    /** 表示区分 */
    private int enq930DspKbn__ = 0;
    /** 表示件数選択値 */
    private int enq930SelectCnt__ = 0;
    /** 表示件数一覧 */
    private ArrayList<LabelValueBean> enq930CntListLabel__ = null;
    /** 初期表示フォルダ選択値 */
    private String enq930SelectFolder__ = null;
    /** 初期表示フォルダ一覧 */
    private ArrayList<LabelValueBean> enq930FolderListLabel__ = null;
    /** 回答可能のみ表示 */
    private boolean enq930CanAnswer__ = false;
    /** メイン表示区分 */
    private int enq930MainDspKbn__ = 0;
    /** メイン表示 */
    private int enq930MainDsp__ = 0;
    /** メイン表示件数 */
    private int enq930DspCntMain__ = GSConstEnquete.MAIN_DSPCNT_5;
    /** 回答済みアンケート表示の有無 */
    private int enq930DspChecked__ = GSConstEnquete.DSP_CHECKED_NONE;
    /** メイン表示件数 コンボボックス */
    private List<LabelValueBean> dspCntComb__ = null;

    /**
     * <p>表示区分 を取得します。
     * @return 表示区分
     */
    public int getEnq930DspKbn() {
        return enq930DspKbn__;
    }
    /**
     * <p>表示区分 をセットします。
     * @param enq930DspKbn 表示区分
     */
    public void setEnq930DspKbn(int enq930DspKbn) {
        enq930DspKbn__ = enq930DspKbn;
    }
    /**
     * <p>表示件数選択値 を取得します。
     * @return 表示件数選択値
     */
    public int getEnq930SelectCnt() {
        return enq930SelectCnt__;
    }
    /**
     * <p>表示件数選択値 をセットします。
     * @param enq930SelectCnt 表示件数選択値
     */
    public void setEnq930SelectCnt(int enq930SelectCnt) {
        enq930SelectCnt__ = enq930SelectCnt;
    }
    /**
     * <p>表示件数一覧 を取得します。
     * @return 表示件数一覧
     */
    public ArrayList<LabelValueBean> getEnq930CntListLabel() {
        return enq930CntListLabel__;
    }
    /**
     * <p>表示件数一覧 をセットします。
     * @param enq930CntList 表示件数一覧
     */
    public void setEnq930CntListLabel(ArrayList<LabelValueBean> enq930CntList) {
        enq930CntListLabel__ = enq930CntList;
    }
    /**
     * <p>enq930SelectFolder を取得します。
     * @return enq930SelectFolder
     */
    public String getEnq930SelectFolder() {
        return enq930SelectFolder__;
    }
    /**
     * <p>enq930SelectFolder をセットします。
     * @param enq930SelectFolder enq930SelectFolder
     */
    public void setEnq930SelectFolder(String enq930SelectFolder) {
        enq930SelectFolder__ = enq930SelectFolder;
    }
    /**
     * <p>enq930FolderListLabel を取得します。
     * @return enq930FolderListLabel
     */
    public ArrayList<LabelValueBean> getEnq930FolderListLabel() {
        return enq930FolderListLabel__;
    }
    /**
     * <p>enq930FolderListLabel をセットします。
     * @param enq930FolderListLabel enq930FolderListLabel
     */
    public void setEnq930FolderListLabel(
            ArrayList<LabelValueBean> enq930FolderListLabel) {
        enq930FolderListLabel__ = enq930FolderListLabel;
    }
    /**
     * <p>enq930CanAnswer を取得します。
     * @return enq930CanAnswer
     */
    public boolean getEnq930CanAnswer() {
        return enq930CanAnswer__;
    }
    /**
     * <p>enq930CanAnswer をセットします。
     * @param enq930CanAnswer enq930CanAnswer
     */
    public void setEnq930CanAnswer(boolean enq930CanAnswer) {
        enq930CanAnswer__ = enq930CanAnswer;
    }
    /**
     * <p>enq930MainDspKbn を取得します。
     * @return enq930MainDspKbn
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#enq930MainDspKbn__
     */
    public int getEnq930MainDspKbn() {
        return enq930MainDspKbn__;
    }
    /**
     * <p>enq930MainDspKbn をセットします。
     * @param enq930MainDspKbn enq930MainDspKbn
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#enq930MainDspKbn__
     */
    public void setEnq930MainDspKbn(int enq930MainDspKbn) {
        enq930MainDspKbn__ = enq930MainDspKbn;
    }
    /**
     * <p>enq930MainDsp を取得します。
     * @return enq930MainDsp
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#enq930MainDsp__
     */
    public int getEnq930MainDsp() {
        return enq930MainDsp__;
    }
    /**
     * <p>enq930MainDsp をセットします。
     * @param enq930MainDsp enq930MainDsp
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#enq930MainDsp__
     */
    public void setEnq930MainDsp(int enq930MainDsp) {
        enq930MainDsp__ = enq930MainDsp;
    }
    /**
     * <p>enq930DspCntMain を取得します。
     * @return enq930DspCntMain
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#enq930DspCntMain__
     */
    public int getEnq930DspCntMain() {
        return enq930DspCntMain__;
    }
    /**
     * <p>enq930DspCntMain をセットします。
     * @param enq930DspCntMain enq930DspCntMain
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#enq930DspCntMain__
     */
    public void setEnq930DspCntMain(int enq930DspCntMain) {
        enq930DspCntMain__ = enq930DspCntMain;
    }
    /**
     * <p>enq930DspChecked を取得します。
     * @return enq930DspChecked
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#enq930DspChecked__
     */
    public int getEnq930DspChecked() {
        return enq930DspChecked__;
    }
    /**
     * <p>enq930DspChecked をセットします。
     * @param enq930DspChecked enq930DspChecked
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#enq930DspChecked__
     */
    public void setEnq930DspChecked(int enq930DspChecked) {
        enq930DspChecked__ = enq930DspChecked;
    }
    /**
     * <p>dspCntComb を取得します。
     * @return dspCntComb
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#dspCntComb__
     */
    public List<LabelValueBean> getDspCntComb() {
        return dspCntComb__;
    }
    /**
     * <p>dspCntComb をセットします。
     * @param dspCntComb dspCntComb
     * @see jp.groupsession.v2.enq.enq930.Enq930ParamModel#dspCntComb__
     */
    public void setDspCntComb(List<LabelValueBean> dspCntComb) {
        dspCntComb__ = dspCntComb;
    }
}

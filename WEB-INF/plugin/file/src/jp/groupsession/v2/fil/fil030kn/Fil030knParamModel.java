package jp.groupsession.v2.fil.fil030kn;

import java.util.ArrayList;

import jp.groupsession.v2.fil.fil030.Fil030ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] キャビネット登録・編集確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil030knParamModel extends Fil030ParamModel {

    /** 表示用 キャビネットの容量上限*/
    private String fil030knDspCapaSize__;
    /** 表示用 キャビネットの備考*/
    private String fil030knDspBiko__;
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String fil030knBinSid__ = null;
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String fil030knBinSidMark__ = null;

    /** アクセス制限 フルアクセスユーザ・グループリスト */
    private ArrayList<UsrLabelValueBean> fil030knAcFullList__ = null;
    /** アクセス制限 閲覧ユーザ・グループリスト */
    private ArrayList< UsrLabelValueBean > fil030knAcReadList__ = null;
    /** キャビネット管理者 ユーザリスト */
    private ArrayList<UsrLabelValueBean> fil030knAdmList__ = null;

    /**
     * @return fil030knBinSid
     */
    public String getFil030knBinSid() {
        return fil030knBinSid__;
    }
    /**
     * @param fil030knBinSid 設定する fil030knBinSid
     */
    public void setFil030knBinSid(String fil030knBinSid) {
        fil030knBinSid__ = fil030knBinSid;
    }
    /**
     * @return fil030knDspBiko
     */
    public String getFil030knDspBiko() {
        return fil030knDspBiko__;
    }
    /**
     * @param fil030knDspBiko 設定する fil030knDspBiko
     */
    public void setFil030knDspBiko(String fil030knDspBiko) {
        fil030knDspBiko__ = fil030knDspBiko;
    }
    /**
     * @return fil030knDspCapaSize
     */
    public String getFil030knDspCapaSize() {
        return fil030knDspCapaSize__;
    }
    /**
     * @param fil030knDspCapaSize 設定する fil030knDspCapaSize
     */
    public void setFil030knDspCapaSize(String fil030knDspCapaSize) {
        fil030knDspCapaSize__ = fil030knDspCapaSize;
    }
    /**
     * <p>fil030knBinSidMark を取得します。
     * @return fil030knBinSidMark
     */
    public String getFil030knBinSidMark() {
        return fil030knBinSidMark__;
    }
    /**
     * <p>fil030knBinSidMark をセットします。
     * @param fil030knBinSidMark fil030knBinSidMark
     */
    public void setFil030knBinSidMark(String fil030knBinSidMark) {
        fil030knBinSidMark__ = fil030knBinSidMark;
    }
    /**
     * <p>fil030knAcFullList を取得します。
     * @return fil030knAcFullList
     * @see jp.groupsession.v2.fil.fil030kn.Fil030knParamModel#fil030knAcFullList__
     */
    public ArrayList<UsrLabelValueBean> getFil030knAcFullList() {
        return fil030knAcFullList__;
    }
    /**
     * <p>fil030knAcFullList をセットします。
     * @param fil030knAcFullList fil030knAcFullList
     * @see jp.groupsession.v2.fil.fil030kn.Fil030knParamModel#fil030knAcFullList__
     */
    public void setFil030knAcFullList(
            ArrayList<UsrLabelValueBean> fil030knAcFullList) {
        fil030knAcFullList__ = fil030knAcFullList;
    }
    /**
     * <p>fil030knAcReadList を取得します。
     * @return fil030knAcReadList
     * @see jp.groupsession.v2.fil.fil030kn.Fil030knParamModel#fil030knAcReadList__
     */
    public ArrayList<UsrLabelValueBean> getFil030knAcReadList() {
        return fil030knAcReadList__;
    }
    /**
     * <p>fil030knAcReadList をセットします。
     * @param fil030knAcReadList fil030knAcReadList
     * @see jp.groupsession.v2.fil.fil030kn.Fil030knParamModel#fil030knAcReadList__
     */
    public void setFil030knAcReadList(
            ArrayList<UsrLabelValueBean> fil030knAcReadList) {
        fil030knAcReadList__ = fil030knAcReadList;
    }
    /**
     * <p>fil030knAdmList を取得します。
     * @return fil030knAdmList
     * @see jp.groupsession.v2.fil.fil030kn.Fil030knParamModel#fil030knAdmList__
     */
    public ArrayList<UsrLabelValueBean> getFil030knAdmList() {
        return fil030knAdmList__;
    }
    /**
     * <p>fil030knAdmList をセットします。
     * @param fil030knAdmList fil030knAdmList
     * @see jp.groupsession.v2.fil.fil030kn.Fil030knParamModel#fil030knAdmList__
     */
    public void setFil030knAdmList(ArrayList<UsrLabelValueBean> fil030knAdmList) {
        fil030knAdmList__ = fil030knAdmList;
    }
}
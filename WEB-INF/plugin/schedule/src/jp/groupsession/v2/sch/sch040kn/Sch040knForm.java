package jp.groupsession.v2.sch.sch040kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sch.sch040.Sch040Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040knForm extends Sch040Form {

    /** 開始日付文字列 */
    private String sch040knFromDate__ = null;
    /** 終了日付文字列 */
    private String sch040knToDate__ = null;
    /** 修正権限フラグ */
    private int sch040knIsEdit__;
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String sch040knBinSid__;
    /** 添付ファイルリスト */
    private List<CmnBinfModel> sch040knFileList__ = null;
    /** 表示用公開対象 */
    private List<UsrLabelValueBean> sch040knDspTarget__ = null;

    /**
     * @return sch040knIsEdit を戻します。
     */
    public int getSch040knIsEdit() {
        return sch040knIsEdit__;
    }
    /**
     * @param sch040knIsEdit 設定する sch040knIsEdit。
     */
    public void setSch040knIsEdit(int sch040knIsEdit) {
        sch040knIsEdit__ = sch040knIsEdit;
    }
    /**
     * @return sch040knFromDate を戻します。
     */
    public String getSch040knFromDate() {
        return sch040knFromDate__;
    }
    /**
     * @param sch040knFromDate 設定する sch040knFromDate。
     */
    public void setSch040knFromDate(String sch040knFromDate) {
        sch040knFromDate__ = sch040knFromDate;
    }
    /**
     * @return sch040knToDate を戻します。
     */
    public String getSch040knToDate() {
        return sch040knToDate__;
    }
    /**
     * @param sch040knToDate 設定する sch040knToDate。
     */
    public void setSch040knToDate(String sch040knToDate) {
        sch040knToDate__ = sch040knToDate;
    }
    /**
     * <p>sch040knBinSid を取得します。
     * @return sch040knBinSid
     * @see jp.groupsession.v2.sch.sch040kn.Sch040knForm#sch040knBinSid__
     */
    public String getSch040knBinSid() {
        return sch040knBinSid__;
    }
    /**
     * <p>sch040knBinSid をセットします。
     * @param sch040knBinSid sch040knBinSid
     * @see jp.groupsession.v2.sch.sch040kn.Sch040knForm#sch040knBinSid__
     */
    public void setSch040knBinSid(String sch040knBinSid) {
        sch040knBinSid__ = sch040knBinSid;
    }
    /**
     * <p>sch040knFileList を取得します。
     * @return sch040knFileList
     * @see jp.groupsession.v2.sch.sch040kn.Sch040knForm#sch040knFileList__
     */
    public List<CmnBinfModel> getSch040knFileList() {
        return sch040knFileList__;
    }
    /**
     * <p>sch040knFileList をセットします。
     * @param sch040knFileList sch040knFileList
     * @see jp.groupsession.v2.sch.sch040kn.Sch040knForm#sch040knFileList__
     */
    public void setSch040knFileList(List<CmnBinfModel> sch040knFileList) {
        sch040knFileList__ = sch040knFileList;
    }
    /**
     * <p>sch040knDspTarget を取得します。
     * @return sch040knDspTarget
     * @see jp.groupsession.v2.sch.sch040kn.Sch040knForm#sch040knDspTarget__
     */
    public List<UsrLabelValueBean> getSch040knDspTarget() {
        return sch040knDspTarget__;
    }
    /**
     * <p>sch040knDspTarget をセットします。
     * @param sch040knDspTarget sch040knDspTarget
     * @see jp.groupsession.v2.sch.sch040kn.Sch040knForm#sch040knDspTarget__
     */
    public void setSch040knDspTarget(List<UsrLabelValueBean> sch040knDspTarget) {
        sch040knDspTarget__ = sch040knDspTarget;
    }

}

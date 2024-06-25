package jp.groupsession.v2.rsv.rsv230kn;

import java.util.List;

import jp.groupsession.v2.rsv.rsv230.Rsv230Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 施設予約 初期値設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv230knForm extends Rsv230Form {

    /** 表示用 開始 時 */
    private String rsv230knDefFrHDsp__ = null;
    /** 表示用 開始 分 */
    private String rsv230knDefFrMDsp__ = null;
    /** 表示用 終了 時 */
    private String rsv230knDefToHDsp__ = null;
    /** 表示用 終了 分 */
    private String rsv230knDefToMDsp__ = null;
    /** 公開対象ユーザ・グループリスト */
    private List <UsrLabelValueBean> rsv230knPubUsrGrpList__ = null;

    /**
     * <p>rsv230knDefFrHDsp を取得します。
     * @return rsv230knDefFrHDsp
     */
    public String getRsv230knDefFrHDsp() {
        return rsv230knDefFrHDsp__;
    }
    /**
     * <p>rsv230knDefFrHDsp をセットします。
     * @param rsv230knDefFrHDsp rsv230knDefFrHDsp
     */
    public void setRsv230knDefFrHDsp(String rsv230knDefFrHDsp) {
        rsv230knDefFrHDsp__ = rsv230knDefFrHDsp;
    }
    /**
     * <p>rsv230knDefFrMDsp を取得します。
     * @return rsv230knDefFrMDsp
     */
    public String getRsv230knDefFrMDsp() {
        return rsv230knDefFrMDsp__;
    }
    /**
     * <p>rsv230knDefFrMDsp をセットします。
     * @param rsv230knDefFrMDsp rsv230knDefFrMDsp
     */
    public void setRsv230knDefFrMDsp(String rsv230knDefFrMDsp) {
        rsv230knDefFrMDsp__ = rsv230knDefFrMDsp;
    }
    /**
     * <p>rsv230knDefToHDsp を取得します。
     * @return rsv230knDefToHDsp
     */
    public String getRsv230knDefToHDsp() {
        return rsv230knDefToHDsp__;
    }
    /**
     * <p>rsv230knDefToHDsp をセットします。
     * @param rsv230knDefToHDsp rsv230knDefToHDsp
     */
    public void setRsv230knDefToHDsp(String rsv230knDefToHDsp) {
        rsv230knDefToHDsp__ = rsv230knDefToHDsp;
    }
    /**
     * <p>rsv230knDefToMDsp を取得します。
     * @return rsv230knDefToMDsp
     */
    public String getRsv230knDefToMDsp() {
        return rsv230knDefToMDsp__;
    }
    /**
     * <p>rsv230knDefToMDsp をセットします。
     * @param rsv230knDefToMDsp rsv230knDefToMDsp
     */
    public void setRsv230knDefToMDsp(String rsv230knDefToMDsp) {
        rsv230knDefToMDsp__ = rsv230knDefToMDsp;
    }
    /**
     * <p>rsv230knPubUsrGrpList を取得します。
     * @return rsv230knPubUsrGrpList
     * @see jp.groupsession.v2.rsv.rsv230kn.Rsv230knForm#rsv230knPubUsrGrpList__
     */
    public List<UsrLabelValueBean> getRsv230knPubUsrGrpList() {
        return rsv230knPubUsrGrpList__;
    }
    /**
     * <p>rsv230knPubUsrGrpList をセットします。
     * @param rsv230knPubUsrGrpList rsv230knPubUsrGrpList
     * @see jp.groupsession.v2.rsv.rsv230kn.Rsv230knForm#rsv230knPubUsrGrpList__
     */
    public void setRsv230knPubUsrGrpList(
            List<UsrLabelValueBean> rsv230knPubUsrGrpList) {
        rsv230knPubUsrGrpList__ = rsv230knPubUsrGrpList;
    }
}
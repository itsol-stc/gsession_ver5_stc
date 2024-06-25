package jp.groupsession.v2.rsv.rsv280kn;

import java.util.List;

import jp.groupsession.v2.rsv.rsv280.Rsv280Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能]施設予約 管理者設定 施設予約初期値設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv280knForm extends Rsv280Form {

    /** 表示用 開始 時 */
    private String rsv280knDefFrHDsp__ = null;
    /** 表示用 開始 分 */
    private String rsv280knDefFrMDsp__ = null;
    /** 表示用 終了 時 */
    private String rsv280knDefToHDsp__ = null;
    /** 表示用 終了 分 */
    private String rsv280knDefToMDsp__ = null;
    /** 公開対象ユーザ・グループリスト */
    private List <UsrLabelValueBean> rsv280knPubUsrGrpList__ = null;

    /**
     * <p>rsv280knDefFrHDsp を取得します。
     * @return rsv280knDefFrHDsp
     */
    public String getRsv280knDefFrHDsp() {
        return rsv280knDefFrHDsp__;
    }
    /**
     * <p>rsv280knDefFrHDsp をセットします。
     * @param rsv280knDefFrHDsp rsv280knDefFrHDsp
     */
    public void setRsv280knDefFrHDsp(String rsv280knDefFrHDsp) {
        rsv280knDefFrHDsp__ = rsv280knDefFrHDsp;
    }
    /**
     * <p>rsv280knDefFrMDsp を取得します。
     * @return rsv280knDefFrMDsp
     */
    public String getRsv280knDefFrMDsp() {
        return rsv280knDefFrMDsp__;
    }
    /**
     * <p>rsv280knDefFrMDsp をセットします。
     * @param rsv280knDefFrMDsp rsv280knDefFrMDsp
     */
    public void setRsv280knDefFrMDsp(String rsv280knDefFrMDsp) {
        rsv280knDefFrMDsp__ = rsv280knDefFrMDsp;
    }
    /**
     * <p>rsv280knDefToHDsp を取得します。
     * @return rsv280knDefToHDsp
     */
    public String getRsv280knDefToHDsp() {
        return rsv280knDefToHDsp__;
    }
    /**
     * <p>rsv280knDefToHDsp をセットします。
     * @param rsv280knDefToHDsp rsv280knDefToHDsp
     */
    public void setRsv280knDefToHDsp(String rsv280knDefToHDsp) {
        rsv280knDefToHDsp__ = rsv280knDefToHDsp;
    }
    /**
     * <p>rsv280knDefToMDsp を取得します。
     * @return rsv280knDefToMDsp
     */
    public String getRsv280knDefToMDsp() {
        return rsv280knDefToMDsp__;
    }
    /**
     * <p>rsv280knDefToMDsp をセットします。
     * @param rsv280knDefToMDsp rsv280knDefToMDsp
     */
    public void setRsv280knDefToMDsp(String rsv280knDefToMDsp) {
        rsv280knDefToMDsp__ = rsv280knDefToMDsp;
    }
    /**
     * <p>rsv280knPubUsrGrpList を取得します。
     * @return rsv280knPubUsrGrpList
     * @see jp.groupsession.v2.rsv.rsv280kn.Rsv280knForm#rsv280knPubUsrGrpList__
     */
    public List<UsrLabelValueBean> getRsv280knPubUsrGrpList() {
        return rsv280knPubUsrGrpList__;
    }
    /**
     * <p>rsv280knPubUsrGrpList をセットします。
     * @param rsv280knPubUsrGrpList rsv280knPubUsrGrpList
     * @see jp.groupsession.v2.rsv.rsv280kn.Rsv280knForm#rsv280knPubUsrGrpList__
     */
    public void setRsv280knPubUsrGrpList(
            List<UsrLabelValueBean> rsv280knPubUsrGrpList) {
        rsv280knPubUsrGrpList__ = rsv280knPubUsrGrpList;
    }
}
package jp.groupsession.v2.rsv.rsv240;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.rsv140.Rsv140Form;

/**
 * <br>[機  能] 施設予約 メイン表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv240Form extends Rsv140Form {

    /** ラベル 時 */
    private List<RsvSisGrpModel> rsv240DspList__ = null;
    /** 施設グループチェックボックス */
    private String[] rsv240RsgSids__ = null;
    /** 施設グループ全選択チェックボックス */
    private String rsv240AllCheck__ = null;
    /** 初期表示フラグ */
    private boolean rsv240InitFlg__ = true;
    /** 予約時間経過表示区分 */
    private int rsv240overTimeDspKbn__ = GSConstReserve.RSV_OVERTIME_DSP_ON;

    /**
     * <p>rsv240DspList を取得します。
     * @return rsv240DspList
     */
    public List<RsvSisGrpModel> getRsv240DspList() {
        return rsv240DspList__;
    }
    /**
     * <p>rsv240DspList をセットします。
     * @param rsv240DspList rsv240DspList
     */
    public void setRsv240DspList(List<RsvSisGrpModel> rsv240DspList) {
        rsv240DspList__ = rsv240DspList;
    }
    /**
     * <p>rsv240RsgSids を取得します。
     * @return rsv240RsgSids
     */
    public String[] getRsv240RsgSids() {
        return rsv240RsgSids__;
    }
    /**
     * <p>rsv240RsgSids をセットします。
     * @param rsv240RsgSids rsv240RsgSids
     */
    public void setRsv240RsgSids(String[] rsv240RsgSids) {
        rsv240RsgSids__ = rsv240RsgSids;
    }
    /**
     * <p>rsv240AllCheck を取得します。
     * @return rsv240AllCheck
     */
    public String getRsv240AllCheck() {
        return rsv240AllCheck__;
    }
    /**
     * <p>rsv240AllCheck をセットします。
     * @param rsv240AllCheck rsv240AllCheck
     */
    public void setRsv240AllCheck(String rsv240AllCheck) {
        rsv240AllCheck__ = rsv240AllCheck;
    }
    /**
     * <p>rsv240InitFlg を取得します。
     * @return rsv240InitFlg
     */
    public boolean isRsv240InitFlg() {
        return rsv240InitFlg__;
    }
    /**
     * <p>rsv240InitFlg をセットします。
     * @param rsv240InitFlg rsv240InitFlg
     */
    public void setRsv240InitFlg(boolean rsv240InitFlg) {
        rsv240InitFlg__ = rsv240InitFlg;
    }
    /**
     * <p>rsv240overTimeDspKbn を取得します。
     * @return rsv240overTimeDspKbn
     */
    public int getRsv240overTimeDspKbn() {
        return rsv240overTimeDspKbn__;
    }
    /**
     * <p>rsv240overTimeDspKbn をセットします。
     * @param rsv240overTimeDspKbn rsv240overTimeDspKbn
     */
    public void setRsv240overTimeDspKbn(int rsv240overTimeDspKbn) {
        rsv240overTimeDspKbn__ = rsv240overTimeDspKbn;
    }

}

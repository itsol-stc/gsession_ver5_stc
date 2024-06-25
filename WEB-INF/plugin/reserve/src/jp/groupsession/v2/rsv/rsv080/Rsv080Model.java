package jp.groupsession.v2.rsv.rsv080;

import jp.groupsession.v2.rsv.model.RsvSisDataModel;

/**
 * <br>[機  能] 施設予約 施設情報設定画面で使用するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv080Model extends RsvSisDataModel {

    /** 施設グループ名称 */
    private String rsgName__ = null;
    /** 施設区分SID */
    private int rskSid__ = -1;
    /** 施設区分名称 */
    private String rskName__ = null;
    /** ラジオキー */
    private String radioKey__ = null;

    /**
     * <p>rsgName__ を取得します。
     * @return rsgName
     */
    public String getRsgName() {
        return rsgName__;
    }
    /**
     * <p>rsgName__ をセットします。
     * @param rsgName rsgName__
     */
    public void setRsgName(String rsgName) {
        rsgName__ = rsgName;
    }
    /**
     * <p>rskName__ を取得します。
     * @return rskName
     */
    public String getRskName() {
        return rskName__;
    }
    /**
     * <p>rskName__ をセットします。
     * @param rskName rskName__
     */
    public void setRskName(String rskName) {
        rskName__ = rskName;
    }
    /**
     * <p>rskSid__ を取得します。
     * @return rskSid
     */
    public int getRskSid() {
        return rskSid__;
    }
    /**
     * <p>rskSid__ をセットします。
     * @param rskSid rskSid__
     */
    public void setRskSid(int rskSid) {
        rskSid__ = rskSid;
    }
    /**
     * <p>radioKey__ を取得します。
     * @return radioKey
     */
    public String getRadioKey() {
        return radioKey__;
    }
    /**
     * <p>radioKey__ をセットします。
     * @param radioKey radioKey__
     */
    public void setRadioKey(String radioKey) {
        radioKey__ = radioKey;
    }
}
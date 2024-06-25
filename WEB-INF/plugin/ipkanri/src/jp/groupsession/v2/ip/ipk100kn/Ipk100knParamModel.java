package jp.groupsession.v2.ip.ipk100kn;

import jp.groupsession.v2.ip.ipk100.Ipk100ParamModel;

/**
 * <br>[機  能] IP管理 スペックマスタメンテナンス 登録確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk100knParamModel extends Ipk100ParamModel {
    /** 名前 */
    private String ipk100knName__;
    /** 備考 */
    private String ipk100knBiko__;
    /** スクロール位置t */
    private String ipk100knScroll__;

    /**
     * <p>ipk100knBiko を取得します。
     * @return ipk100knBiko
     */
    public String getIpk100knBiko() {
        return ipk100knBiko__;
    }

    /**
     * <p>ipk100knBiko をセットします。
     * @param ipk100knBiko ipk100knBiko
     */
    public void setIpk100knBiko(String ipk100knBiko) {
        ipk100knBiko__ = ipk100knBiko;
    }

    /**
     * <p>ipk100knName を取得します。
     * @return ipk100knName
     */
    public String getIpk100knName() {
        return ipk100knName__;
    }

    /**
     * <p>ipk100knName をセットします。
     * @param ipk100knName ipk100knName
     */
    public void setIpk100knName(String ipk100knName) {
        ipk100knName__ = ipk100knName;
    }

    /**
     * <p>ipk100knScroll を取得します。
     * @return ipk100knScroll
     */
    public String getIpk100knScroll() {
        return ipk100knScroll__;
    }

    /**
     * <p>ipk100knScroll をセットします。
     * @param ipk100knScroll ipk100knScroll
     */
    public void setIpk100knScroll(String ipk100knScroll) {
        ipk100knScroll__ = ipk100knScroll;
    }
}
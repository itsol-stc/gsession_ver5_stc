package jp.groupsession.v2.cmn.cmn390;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 *
 * <br>[機  能] GSファイアウォール設定画面 ParamModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn390ParamModel extends AbstractParamModel {
    /** IPアドレス制限 0：使用しない 1:使用する*/
    private int cmn390ipAddrSeigenUseFlg__;
    /** 許可IPアドレス*/
    private String cmn390arrowIpAddrText__;
    /** IPアドレス制限 0：使用しない 1:使用する*/
    private int cmn390arrowMblFlg__;
    /** 安否確認制限 0：使用しない 1:使用する*/
    private int cmn390arrowAnpFlg__;
    /**
     * <p>cmn390ipAddrSeigenUseFlg を取得します。
     * @return cmn390ipAddrSeigenUseFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390ipAddrSeigenUseFlg__
     */
    public int getCmn390ipAddrSeigenUseFlg() {
        return cmn390ipAddrSeigenUseFlg__;
    }
    /**
     * <p>cmn390ipAddrSeigenUseFlg をセットします。
     * @param cmn390ipAddrSeigenUseFlg cmn390ipAddrSeigenUseFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390ipAddrSeigenUseFlg__
     */
    public void setCmn390ipAddrSeigenUseFlg(int cmn390ipAddrSeigenUseFlg) {
        cmn390ipAddrSeigenUseFlg__ = cmn390ipAddrSeigenUseFlg;
    }
    /**
     * <p>cmn390arrowIpAddrText を取得します。
     * @return cmn390arrowIpAddrText
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowIpAddrText__
     */
    public String getCmn390arrowIpAddrText() {
        return cmn390arrowIpAddrText__;
    }
    /**
     * <p>cmn390arrowIpAddrText をセットします。
     * @param cmn390arrowIpAddrText cmn390arrowIpAddrText
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowIpAddrText__
     */
    public void setCmn390arrowIpAddrText(String cmn390arrowIpAddrText) {
        cmn390arrowIpAddrText__ = cmn390arrowIpAddrText;
    }
    /**
     * <p>cmn390arrowMblFlg を取得します。
     * @return cmn390arrowMblFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowMblFlg__
     */
    public int getCmn390arrowMblFlg() {
        return cmn390arrowMblFlg__;
    }
    /**
     * <p>cmn390arrowMblFlg をセットします。
     * @param cmn390arrowMblFlg cmn390arrowMblFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowMblFlg__
     */
    public void setCmn390arrowMblFlg(int cmn390arrowMblFlg) {
        cmn390arrowMblFlg__ = cmn390arrowMblFlg;
    }
    /**
     * <p>cmn390arrowAnpFlg を取得します。
     * @return cmn390arrowAnpFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowAnpFlg__
     */
    public int getCmn390arrowAnpFlg() {
        return cmn390arrowAnpFlg__;
    }
    /**
     * <p>cmn390arrowAnpFlg をセットします。
     * @param cmn390arrowAnpFlg cmn390arrowAnpFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowAnpFlg__
     */
    public void setCmn390arrowAnpFlg(int cmn390arrowAnpFlg) {
        cmn390arrowAnpFlg__ = cmn390arrowAnpFlg;
    }


}

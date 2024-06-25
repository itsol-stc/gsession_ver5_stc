package jp.groupsession.v2.api.api020;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.api.api010.Api010ParamModel;
/**
 *
 * <br>[機  能] API基本設定 パラムモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api020ParamModel extends Api010ParamModel {
    //前提設定
    /** ワンタイムパスワード使用フラグ*/
    private boolean api020useOtp__;

    //input
    /** トークン認証 使用*/
    private int api020useToken__;
    /** トークン 使用IP*/
    private String api020tokenIpArea__;
    /** トークン 有効期限*/
    private int api020tokenLimit__;
    /** トークン 自動削除フラグ*/
    private int api020autoDel__;
    /** ベーシック認証 使用*/
    private int api020useBasic__;
    /** ベーシック 使用IP*/
    private String api020basicIpArea__;

    //表示用
    /** トークン 有効期限 選択値*/
    private String api020tokenLimitDsp__;
    /** トークン 有効期限 コンボリスト*/
    private List<LabelValueBean> api020tokenLimitOption__;
    /** トークン 使用IP*/
    private String api020tokenIpAreaDsp__;
    /** ベーシック 使用IP*/
    private String api020basicIpAreaDsp__;
    /**
     * <p>api020useOtp を取得します。
     * @return api020useOtp
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020useOtp__
     */
    public boolean isApi020useOtp() {
        return api020useOtp__;
    }
    /**
     * <p>api020useOtp をセットします。
     * @param api020useOtp api020useOtp
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020useOtp__
     */
    public void setApi020useOtp(boolean api020useOtp) {
        api020useOtp__ = api020useOtp;
    }
    /**
     * <p>api020useToken を取得します。
     * @return api020useToken
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020useToken__
     */
    public int getApi020useToken() {
        return api020useToken__;
    }
    /**
     * <p>api020useToken をセットします。
     * @param api020useToken api020useToken
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020useToken__
     */
    public void setApi020useToken(int api020useToken) {
        api020useToken__ = api020useToken;
    }
    /**
     * <p>api020tokenIpArea を取得します。
     * @return api020tokenIpArea
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenIpArea__
     */
    public String getApi020tokenIpArea() {
        return api020tokenIpArea__;
    }
    /**
     * <p>api020tokenIpArea をセットします。
     * @param api020tokenIpArea api020tokenIpArea
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenIpArea__
     */
    public void setApi020tokenIpArea(String api020tokenIpArea) {
        api020tokenIpArea__ = api020tokenIpArea;
    }
    /**
     * <p>api020tokenLimit を取得します。
     * @return api020tokenLimit
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenLimit__
     */
    public int getApi020tokenLimit() {
        return api020tokenLimit__;
    }
    /**
     * <p>api020tokenLimit をセットします。
     * @param api020tokenLimit api020tokenLimit
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenLimit__
     */
    public void setApi020tokenLimit(int api020tokenLimit) {
        api020tokenLimit__ = api020tokenLimit;
    }
    /**
     * <p>api020useBasic を取得します。
     * @return api020useBasic
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020useBasic__
     */
    public int getApi020useBasic() {
        return api020useBasic__;
    }
    /**
     * <p>api020useBasic をセットします。
     * @param api020useBasic api020useBasic
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020useBasic__
     */
    public void setApi020useBasic(int api020useBasic) {
        api020useBasic__ = api020useBasic;
    }
    /**
     * <p>api020basicIpArea を取得します。
     * @return api020basicIpArea
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020basicIpArea__
     */
    public String getApi020basicIpArea() {
        return api020basicIpArea__;
    }
    /**
     * <p>api020basicIpArea をセットします。
     * @param api020basicIpArea api020basicIpArea
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020basicIpArea__
     */
    public void setApi020basicIpArea(String api020basicIpArea) {
        api020basicIpArea__ = api020basicIpArea;
    }
    /**
     * <p>api020tokenLimitDsp を取得します。
     * @return api020tokenLimitDsp
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenLimitDsp__
     */
    public String getApi020tokenLimitDsp() {
        return api020tokenLimitDsp__;
    }
    /**
     * <p>api020tokenLimitDsp をセットします。
     * @param api020tokenLimitDsp api020tokenLimitDsp
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenLimitDsp__
     */
    public void setApi020tokenLimitDsp(String api020tokenLimitDsp) {
        api020tokenLimitDsp__ = api020tokenLimitDsp;
    }
    /**
     * <p>api020tokenLimitOption を取得します。
     * @return api020tokenLimitOption
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenLimitOption__
     */
    public List<LabelValueBean> getApi020tokenLimitOption() {
        return api020tokenLimitOption__;
    }
    /**
     * <p>api020tokenLimitOption をセットします。
     * @param api020tokenLimitOption api020tokenLimitOption
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenLimitOption__
     */
    public void setApi020tokenLimitOption(
            List<LabelValueBean> api020tokenLimitOption) {
        api020tokenLimitOption__ = api020tokenLimitOption;
    }
    /**
     * <p>api020tokenIpAreaDsp を取得します。
     * @return api020tokenIpAreaDsp
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenIpAreaDsp__
     */
    public String getApi020tokenIpAreaDsp() {
        return api020tokenIpAreaDsp__;
    }
    /**
     * <p>api020tokenIpAreaDsp をセットします。
     * @param api020tokenIpAreaDsp api020tokenIpAreaDsp
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020tokenIpAreaDsp__
     */
    public void setApi020tokenIpAreaDsp(String api020tokenIpAreaDsp) {
        api020tokenIpAreaDsp__ = api020tokenIpAreaDsp;
    }
    /**
     * <p>api020basicIpAreaDsp を取得します。
     * @return api020basicIpAreaDsp
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020basicIpAreaDsp__
     */
    public String getApi020basicIpAreaDsp() {
        return api020basicIpAreaDsp__;
    }
    /**
     * <p>api020basicIpAreaDsp をセットします。
     * @param api020basicIpAreaDsp api020basicIpAreaDsp
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020basicIpAreaDsp__
     */
    public void setApi020basicIpAreaDsp(String api020basicIpAreaDsp) {
        api020basicIpAreaDsp__ = api020basicIpAreaDsp;
    }
    /**
     * <p>api020autoDel を取得します。
     * @return api020autoDel
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020autoDel__
     */
    public int getApi020autoDel() {
        return api020autoDel__;
    }
    /**
     * <p>api020autoDel をセットします。
     * @param api020autoDel api020autoDel
     * @see jp.groupsession.v2.api.api020.Api020ParamModel#api020autoDel__
     */
    public void setApi020autoDel(int api020autoDel) {
        api020autoDel__ = api020autoDel;
    }


}

package jp.groupsession.v2.ntp.ntp080;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.ParameterUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.ntp.ntp100.Ntp100Form;

/**
 * <br>[機  能] 日報 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp080Form extends Ntp100Form {
    /** ショートメール使用可否*/
    private int canUseSml__;

    /**
     * <p>canUseSml を取得します。
     * @return canUseSml
     */
    public int getCanUseSml() {
        return canUseSml__;
    }

    /**
     * <p>canUseSml をセットします。
     * @param canUseSml canUseSml
     */
    public void setCanUseSml(int canUseSml) {
        canUseSml__ = canUseSml;
    }
    /**
     *
     * <br>[機  能] 設定画面系 共通 画面引継ぎ用hiddenパラメータを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return hiddenで設定されるパラメータ
     */
    public List<LabelValueBean> getConfHiddenParamList() {
        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>(
                ParameterUtil.getParameterLabels(this,
                new String[] {
                        "ntp010DspDate",
                        "ntp010DspGpSid",
                        "ntp010SelectUsrSid",
                        "ntp010SelectUsrKbn",
                        "ntp010SelectDate",
                        "ntp020SelectUsrSid",
                        "ntp030SelectUsrSid",
                        "dspMod",
                        "listMod",
                        "backScreen"
                }));

        return ret;

    }
    /**
    *
    * <br>[機  能] 画面引継ぎ用hiddenパラメータを返す
    * <br>[解  説]
    * <br>[備  考]
    * @return hiddenで設定されるパラメータ
    */
    public List<LabelValueBean> getHiddenParamList() {
        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        ret.addAll(
                getConfHiddenParamList()
                );
        ret.addAll(
                getSearchHiddenParamList()
                );

        return ret;

     }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
//      super.setHiddenParam(msgForm);
        for (LabelValueBean label : getHiddenParamList()) {
            msgForm.addHiddenParam(label.getLabel(), label.getValue());
        }

    }



}

package jp.groupsession.v2.cmn.cmn390;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] GSファイアウォール設定画面 Formクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn390Form extends AbstractGsForm {
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
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390ipAddrSeigenUseFlg__
     */
    public int getCmn390ipAddrSeigenUseFlg() {
        return cmn390ipAddrSeigenUseFlg__;
    }
    /**
     * <p>cmn390ipAddrSeigenUseFlg をセットします。
     * @param cmn390ipAddrSeigenUseFlg cmn390ipAddrSeigenUseFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390ipAddrSeigenUseFlg__
     */
    public void setCmn390ipAddrSeigenUseFlg(int cmn390ipAddrSeigenUseFlg) {
        cmn390ipAddrSeigenUseFlg__ = cmn390ipAddrSeigenUseFlg;
    }
    /**
     * <p>cmn390arrowIpAddrText を取得します。
     * @return cmn390arrowIpAddrText
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowIpAddrText__
     */
    public String getCmn390arrowIpAddrText() {
        return cmn390arrowIpAddrText__;
    }
    /**
     * <p>cmn390arrowIpAddrText をセットします。
     * @param cmn390arrowIpAddrText cmn390arrowIpAddrText
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowIpAddrText__
     */
    public void setCmn390arrowIpAddrText(String cmn390arrowIpAddrText) {
        cmn390arrowIpAddrText__ = cmn390arrowIpAddrText;
    }
    /**
     * <p>cmn390arrowMblFlg を取得します。
     * @return cmn390arrowMblFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowMblFlg__
     */
    public int getCmn390arrowMblFlg() {
        return cmn390arrowMblFlg__;
    }
    /**
     * <p>cmn390arrowMblFlg をセットします。
     * @param cmn390arrowMblFlg cmn390arrowMblFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowMblFlg__
     */
    public void setCmn390arrowMblFlg(int cmn390arrowMblFlg) {
        cmn390arrowMblFlg__ = cmn390arrowMblFlg;
    }
    /**
     * <p>cmn390arrowAnpFlg を取得します。
     * @return cmn390arrowAnpFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowAnpFlg__
     */
    public int getCmn390arrowAnpFlg() {
        return cmn390arrowAnpFlg__;
    }
    /**
     * <p>cmn390arrowAnpFlg をセットします。
     * @param cmn390arrowAnpFlg cmn390arrowAnpFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowAnpFlg__
     */
    public void setCmn390arrowAnpFlg(int cmn390arrowAnpFlg) {
        cmn390arrowAnpFlg__ = cmn390arrowAnpFlg;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl,
            Connection con
            ) throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (Stream.of(0, 1)
                .anyMatch(v -> v == cmn390ipAddrSeigenUseFlg__) == false) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("cmn.cmn390.03")),
                    "cmn390ipAddrSeigenUseFlg");

        }

        if (getCmn390ipAddrSeigenUseFlg() == 1) {
            GSValidateCommon.validateTextField(errors,
                    cmn390arrowIpAddrText__,
                    "cmn390arrowIpAddrText",
                    gsMsg.getMessage("cmn.cmn390.04"),
                    Cmn390Biz.MAXLENGTH_IPADDR,
                    true);

            if (Stream.of(0, 1)
                    .anyMatch(v -> v == cmn390arrowMblFlg__) == false) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.input.notvalidate.data",
                                gsMsg.getMessage("cmn.cmn390.05")),
                        "cmn390arrowMblFlg");

            }

            if (Stream.of(0, 1)
                    .anyMatch(v -> v == cmn390arrowAnpFlg__) == false) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.input.notvalidate.data",
                                gsMsg.getMessage("cmn.cmn390.05")),
                        "cmn390arrowAnpFlg");
            }

            FirewallBiz biz = FirewallBiz.getInstance();
            if (biz.isArrowAccessForValidate(
                    reqMdl.getRemoteAddr(),
                    cmn390arrowIpAddrText__) == false) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("errors.free.msg",
                                gsMsg.getMessage("cmn.cmn390.08")),
                        "cmn390arrowAnpFlg");

            }

        }


        return errors;
    }



}

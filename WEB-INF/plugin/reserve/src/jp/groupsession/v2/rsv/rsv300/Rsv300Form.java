package jp.groupsession.v2.rsv.rsv300;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.rsv140.Rsv140Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv300Form extends Rsv140Form {

    /** 通知設定 0=通知しない 1=通知する */
    private int rsv300smailKbn__ = -1;

    /**
     * @return rsv300smailKbn
     */
    public int getRsv300smailKbn() {
        return rsv300smailKbn__;
    }

    /**
     * @param rsv300smailKbn セットする rsv300smailKbn
     */
    public void setRsv300smailKbn(int rsv300smailKbn) {
        rsv300smailKbn__ = rsv300smailKbn;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return アクションエラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        
        String reserve = gsMsg.getMessage("cmn.reserve");
        String msgKey = "error.input.sml.setting";
        String eprefix = "rsv300.";
        
        // 数値範囲チェック
        if (rsv300smailKbn__ != GSConstReserve.RAC_SMAIL_SEND_YES
                && rsv300smailKbn__ !=  GSConstReserve.RAC_SMAIL_SEND_NO) {
            // 他ユーザ変更時
            String checkObj = gsMsg.getMessage("reserve.rsv300.2");
            msg = new ActionMessage(msgKey, reserve, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        
        return errors;
    }
}
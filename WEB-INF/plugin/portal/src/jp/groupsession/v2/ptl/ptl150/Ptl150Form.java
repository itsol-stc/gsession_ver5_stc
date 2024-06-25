package jp.groupsession.v2.ptl.ptl150;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.ptl020.Ptl020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] ポータル 管理者設定 初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl150Form extends Ptl020Form {

    /** ポータルの初期表示区分 */
    private String ptl150ptlInitKbn__ = null;
    /** ポータルの初期表示 設定方法 */
    private String ptl150ptlInitType__ = null;
    /** ポータルの初期表示フラグ */
    private int ptl150init__ = 0;


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String initSettig = gsMsg.getMessage("schedule.145");
        String settig = gsMsg.getMessage("ptl.ptl150.3");

        //ラジオチェック
        if (ptl150ptlInitKbn__ == null) {
            msg = new ActionMessage("error.select.required.text", initSettig);
            StrutsUtil.addMessage(errors, msg, "ptl150ptlInitKbn");
        }
        if (ptl150ptlInitType__ == null) {
            msg = new ActionMessage("error.select.required.text", settig);
            StrutsUtil.addMessage(errors, msg, "ptl150ptlInitType");
        }

        return errors;
    }


    /**
     * @return ptl150init
     */
    public int getPtl150init() {
        return ptl150init__;
    }
    /**
     * @param ptl150init セットする ptl150init
     */
    public void setPtl150init(int ptl150init) {
        ptl150init__ = ptl150init;
    }
    /**
     * @return ptl150ptlInitKbn
     */
    public String getPtl150ptlInitKbn() {
        return ptl150ptlInitKbn__;
    }

    /**
     * @param ptl150ptlInitKbn セットする ptl150ptlInitKbn
     */
    public void setPtl150ptlInitKbn(String ptl150ptlInitKbn) {
        ptl150ptlInitKbn__ = ptl150ptlInitKbn;
    }

    /**
     * @return ptl150ptlInitType
     */
    public String getPtl150ptlInitType() {
        return ptl150ptlInitType__;
    }

    /**
     * @param ptl150ptlInitType セットする ptl150ptlInitType
     */
    public void setPtl150ptlInitType(String ptl150ptlInitType) {
        ptl150ptlInitType__ = ptl150ptlInitType;
    }

}
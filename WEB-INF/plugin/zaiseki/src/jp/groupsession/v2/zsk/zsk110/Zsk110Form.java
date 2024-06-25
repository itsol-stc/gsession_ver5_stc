package jp.groupsession.v2.zsk.zsk110;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.GSValidateZaiseki;
import jp.groupsession.v2.zsk.zsk020.Zsk020Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理 管理者設定 定時一括更新画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk110Form extends Zsk020Form {

    /** 定時一括更新 区分 */
    private int zsk110UpdateKbn__ = GSConstZaiseki.FIXED_UPDATE_ON;
    /** 定時一括更新 開始時刻 */
    private int zsk110StartTime__ = -1;
    /** 定時一括更新 在席状況 */
    private int zsk110Status__ = GSConst.UIOSTS_LEAVE;
    /** 定時一括更新 在席コメント */
    private String zsk110Msg__;

    /** 開始時刻コンボ */
    private List <LabelValueBean> zsk110TimeList__ = null;

    /**
     * <p>zsk110StartTime を取得します。
     * @return zsk110StartTime
     */
    public int getZsk110StartTime() {
        return zsk110StartTime__;
    }
    /**
     * <p>zsk110StartTime をセットします。
     * @param zsk110StartTime zsk110StartTime
     */
    public void setZsk110StartTime(int zsk110StartTime) {
        zsk110StartTime__ = zsk110StartTime;
    }
    /**
     * <p>zsk110Status を取得します。
     * @return zsk110Status
     */
    public int getZsk110Status() {
        return zsk110Status__;
    }
    /**
     * <p>zsk110Status をセットします。
     * @param zsk110Status zsk110Status
     */
    public void setZsk110Status(int zsk110Status) {
        zsk110Status__ = zsk110Status;
    }
    /**
     * <p>zsk110UpdateKbn を取得します。
     * @return zsk110UpdateKbn
     */
    public int getZsk110UpdateKbn() {
        return zsk110UpdateKbn__;
    }
    /**
     * <p>zsk110UpdateKbn をセットします。
     * @param zsk110UpdateKbn zsk110UpdateKbn
     */
    public void setZsk110UpdateKbn(int zsk110UpdateKbn) {
        zsk110UpdateKbn__ = zsk110UpdateKbn;
    }
    /**
     * <p>zsk110Msg を取得します。
     * @return zsk110Msg
     */
    public String getZsk110Msg() {
        return zsk110Msg__;
    }
    /**
     * <p>zsk110ZskMsg をセットします。
     * @param zsk110Msg zsk110Msgs
     */
    public void setZsk110Msg(String zsk110Msg) {
        zsk110Msg__ = zsk110Msg;
    }
    /**
     * <p>zsk110TimeList を取得します。
     * @return zsk110TimeList
     */
    public List<LabelValueBean> getZsk110TimeList() {
        return zsk110TimeList__;
    }
    /**
     * <p>zsk110TimeList をセットします。
     * @param zsk110TimeList zsk110TimeList
     */
    public void setZsk110TimeList(List<LabelValueBean> zsk110TimeList) {
        zsk110TimeList__ = zsk110TimeList;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @return エラー
     * @param req リクエスト
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        String message = gsMsg.getMessage(req, "cmn.starttime");

        //開始時刻
        if (zsk110StartTime__ == -1 && zsk110UpdateKbn__ == GSConstZaiseki.FIXED_UPDATE_ON) {
            msg = new ActionMessage("error.select.required.text", message);
            StrutsUtil.addMessage(errors, msg, "zsk110StartTime");
        }

        //コメント
        if (zsk110UpdateKbn__ == GSConstZaiseki.FIXED_UPDATE_ON) {
            GSValidateZaiseki.validateZskBiko(errors, zsk110Msg__, req);
        }

        return errors;
    }
}

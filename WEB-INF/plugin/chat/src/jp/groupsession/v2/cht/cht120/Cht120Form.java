package jp.groupsession.v2.cht.cht120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht030.Cht030Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャット 通知設定のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht120Form extends Cht030Form {
    /** 受信時プッシュ通知 */
    private int cht120Push__ = GSConstChat.CHAT_PUSH_FLG_YES;
    /** 通知表示時間 */
    private int cht120DspSecond__ = GSConstChat.CHAT_PUSH_DEFAULT_TIME;
    /** 時間リスト */
    private List<LabelValueBean> cht120TimeList__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form フォーム
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCht120(
            RequestModel reqMdl, Connection con, Cht120Form form) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // 受信時プッシュ通知
        if (cht120Push__ != GSConstChat.CHAT_PUSH_FLG_YES
                && cht120Push__ != GSConstChat.CHAT_PUSH_FLG_NO) {
                   ActionMessage msg =  new ActionMessage("error.input.format.file",
                           gsMsg.getMessage("cht.cht120.02"),
                           gsMsg.getMessage("main.man340.10"));
                   String eprefix = "pushKbn";
                   StrutsUtil.addMessage(errors, msg, eprefix);
        }
        // 通知表示時間
        if (cht120DspSecond__ <= 0
                || cht120DspSecond__ > GSConstChat.CHAT_PUSH_MAX_TIME) {
                   ActionMessage msg =  new ActionMessage("error.input.format.file",
                           gsMsg.getMessage("cht.cht120.03"),
                           gsMsg.getMessage("main.man340.10"));
                   String eprefix = "dspTime";
                   StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }

    /**
     * <p>cht120Push を取得します。
     * @return cht120Push
     * @see jp.groupsession.v2.cht.cht120.Cht120Form#cht120Push__
     */
    public int getCht120Push() {
        return cht120Push__;
    }
    /**
     * <p>cht120Push をセットします。
     * @param cht120Push cht120Push
     * @see jp.groupsession.v2.cht.cht120.Cht120Form#cht120Push__
     */
    public void setCht120Push(int cht120Push) {
        cht120Push__ = cht120Push;
    }
    /**
     * <p>cht120DspSecond を取得します。
     * @return cht120DspSecond
     * @see jp.groupsession.v2.cht.cht120.Cht120Form#cht120DspSecond__
     */
    public int getCht120DspSecond() {
        return cht120DspSecond__;
    }
    /**
     * <p>cht120DspSecond をセットします。
     * @param cht120DspSecond cht120DspSecond
     * @see jp.groupsession.v2.cht.cht120.Cht120Form#cht120DspSecond__
     */
    public void setCht120DspSecond(int cht120DspSecond) {
        cht120DspSecond__ = cht120DspSecond;
    }
    /**
     * <p>cht120TimeList を取得します。
     * @return cht120TimeList
     * @see jp.groupsession.v2.cht.cht120.Cht120Form#cht120TimeList__
     */
    public List<LabelValueBean> getCht120TimeList() {
        return cht120TimeList__;
    }
    /**
     * <p>cht120TimeList をセットします。
     * @param cht120TimeList cht120TimeList
     * @see jp.groupsession.v2.cht.cht120.Cht120Form#cht120TimeList__
     */
    public void setCht120TimeList(List<LabelValueBean> cht120TimeList) {
        cht120TimeList__ = cht120TimeList;
    }

}

package jp.groupsession.v2.zsk.zsk080;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.zsk030.Zsk030Model;
import jp.groupsession.v2.zsk.zsk070.Zsk070ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 在席管理 個人設定 初期表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk080ParamModel extends Zsk070ParamModel {
    /** 初期表示対象の座席表SID */
    private String dfZifSid__;
    /** 座席表一覧リスト */
    private ArrayList<Zsk030Model> zasekiList__;

    /**
     * <p>dfZifSid を取得します。
     * @return dfZifSid
     */
    public String getDfZifSid() {
        return dfZifSid__;
    }
    /**
     * <p>dfZifSid をセットします。
     * @param dfZifSid dfZifSid
     */
    public void setDfZifSid(String dfZifSid) {
        dfZifSid__ = dfZifSid;
    }
    /**
     * <p>zasekiList を取得します。
     * @return zasekiList
     */
    public ArrayList<Zsk030Model> getZasekiList() {
        return zasekiList__;
    }
    /**
     * <p>zasekiList をセットします。
     * @param zasekiList zasekiList
     */
    public void setZasekiList(ArrayList<Zsk030Model> zasekiList) {
        zasekiList__ = zasekiList;
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
        String msg2 = gsMsg.getMessage(req, "zsk.29");

        //初期表示座席表
        //未選択はNG
        if (StringUtil.isNullZeroString(dfZifSid__)) {
            msg = new ActionMessage("error.select.required.text", msg2);
            StrutsUtil.addMessage(errors, msg, msg2);
        }

        return errors;
    }
}

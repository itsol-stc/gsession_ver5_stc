package jp.groupsession.v2.prj.prj025;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.prj.prj023.Prj023Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] TODO状態削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj025Form extends Prj023Form {

    //入力項目
    /** TODO状態 */
    private String prj025stateSlc__;

    //表示項目
    /** 状態名称 */
    private String statusName__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate025(HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //TODO状態
        String textTodoState = gsMsg.getMessage(req, "project.src.46");
        if (NullDefault.getInt(prj025stateSlc__, -1) < 0) {
            msg = new ActionMessage("error.select.required.text", textTodoState);
            StrutsUtil.addMessage(errors, msg, "prj020status.error.select.required.text");
        }

        return errors;
    }

    /**
     * <p>prj025stateSlc を取得します。
     * @return prj025stateSlc
     */
    public String getPrj025stateSlc() {
        return prj025stateSlc__;
    }

    /**
     * <p>prj025stateSlc をセットします。
     * @param prj025stateSlc prj025stateSlc
     */
    public void setPrj025stateSlc(String prj025stateSlc) {
        prj025stateSlc__ = prj025stateSlc;
    }

    /**
     * <p>statusName を取得します。
     * @return statusName
     */
    public String getStatusName() {
        return statusName__;
    }

    /**
     * <p>statusName をセットします。
     * @param statusName statusName
     */
    public void setStatusName(String statusName) {
        statusName__ = statusName;
    }

}

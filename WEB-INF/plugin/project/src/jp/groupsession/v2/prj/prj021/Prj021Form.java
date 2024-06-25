package jp.groupsession.v2.prj.prj021;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.GSValidateProject;
import jp.groupsession.v2.prj.prj020.Prj020Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] プロジェクト管理 状態設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj021Form extends Prj020Form {

    //入力項目
    /** 進捗率 */
    private String prj021rate__;
    /** 状態名称 */
    private String prj021name__;
    /** 状態 */
    private String prj021state__;
    /** 0%状態名称 */
    private String prj021name0__;
    /** 100%状態名称 */
    private String prj021name100__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate021(HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        int errSize = 0;
        errSize = errors.size();
        GsMessage gsMsg = new GsMessage();
        //進捗率
        String textPrjRate = gsMsg.getMessage(req, "project.src.34");
        //状態名称
        String textStatusName = gsMsg.getMessage(req, "project.src.42");
        //進捗率
        GSValidateProject.validateTextBoxInputNum(
                errors,
                prj021rate__,
                textPrjRate,
                GSConstProject.MAX_LENGTH_RATE,
                true);
        if (errSize == errors.size()) {
            if (NullDefault.getInt(prj021rate__, 0) < 1) {
                msg = new ActionMessage("error.input.lenge", textPrjRate, 1, 99);
                StrutsUtil.addMessage(errors, msg, "prj021rate.error.input.lenge");
            }
        }

        //状態名称
        GSValidateProject.validateTextBoxInput(
                errors,
                prj021name__,
                textStatusName,
                GSConstProject.MAX_LENGTH_STATUS_NAME,
                true);

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validate021edit(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //状態名称0％
        String textStatusName0 = gsMsg.getMessage(req, "project.src.43");
        //状態名称100％
        String textStatusName100 = gsMsg.getMessage(req, "project.src.44");
        //0%の状態名称
        GSValidateProject.validateTextBoxInput(
                errors,
                prj021name0__,
                textStatusName0,
                GSConstProject.MAX_LENGTH_STATUS_NAME,
                true);

        //100%の状態名称
        GSValidateProject.validateTextBoxInput(
                errors,
                prj021name100__,
                textStatusName100,
                GSConstProject.MAX_LENGTH_STATUS_NAME,
                true);

        return errors;
    }

    /**
     * <p>prj021rate を取得します。
     * @return prj021rate
     */
    public String getPrj021rate() {
        return prj021rate__;
    }
    /**
     * <p>prj021rate をセットします。
     * @param prj021rate prj021rate
     */
    public void setPrj021rate(String prj021rate) {
        prj021rate__ = prj021rate;
    }
    /**
     * <p>prj021name を取得します。
     * @return prj021name
     */
    public String getPrj021name() {
        return prj021name__;
    }
    /**
     * <p>prj021name をセットします。
     * @param prj021name prj021name
     */
    public void setPrj021name(String prj021name) {
        prj021name__ = prj021name;
    }
    /**
     * <p>prj021state を取得します。
     * @return prj021state
     */
    public String getPrj021state() {
        return prj021state__;
    }
    /**
     * <p>prj021state をセットします。
     * @param prj021state prj021state
     */
    public void setPrj021state(String prj021state) {
        prj021state__ = prj021state;
    }

    /**
     * <p>prj021name0 を取得します。
     * @return prj021name0
     */
    public String getPrj021name0() {
        return prj021name0__;
    }

    /**
     * <p>prj021name0 をセットします。
     * @param prj021name0 prj021name0
     */
    public void setPrj021name0(String prj021name0) {
        prj021name0__ = prj021name0;
    }

    /**
     * <p>prj021name100 を取得します。
     * @return prj021name100
     */
    public String getPrj021name100() {
        return prj021name100__;
    }

    /**
     * <p>prj021name100 をセットします。
     * @param prj021name100 prj021name100
     */
    public void setPrj021name100(String prj021name100) {
        prj021name100__ = prj021name100;
    }

}

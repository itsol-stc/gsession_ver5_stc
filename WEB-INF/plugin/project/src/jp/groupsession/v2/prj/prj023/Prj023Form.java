package jp.groupsession.v2.prj.prj023;

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
 * <br>[機  能] プロジェクト管理 TODO状態設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj023Form extends Prj020Form {

    //入力項目
    /** 進捗率 */
    private String prj023rate__;
    /** 状態名称 */
    private String prj023name__;
    /** 状態 */
    private String prj023state__;
    /** 0%状態名称 */
    private String prj023name0__;
    /** 100%状態名称 */
    private String prj023name100__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate023(HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        int errSize = 0;
        errSize = errors.size();
        GsMessage gsMsg = new GsMessage();
        //進捗率
        String textPrjRate = gsMsg.getMessage(req, "project.src.34");
        //進捗率
        GSValidateProject.validateTextBoxInputNum(
                errors,
                prj023rate__,
                textPrjRate,
                GSConstProject.MAX_LENGTH_RATE,
                true);
        if (errSize == errors.size()) {
            if (NullDefault.getInt(prj023rate__, 0) < 1) {
                msg = new ActionMessage("error.input.lenge", textPrjRate, 1, 99);
                StrutsUtil.addMessage(errors, msg, "prj023rate.error.input.lenge");
            }
        }
        //状態名称
        String textStatusName = gsMsg.getMessage(req, "project.src.42");

        //状態名称
        GSValidateProject.validateTextBoxInput(
                errors,
                prj023name__,
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
    public ActionErrors validate023edit(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //状態名称0％
        String textStatusName0 = gsMsg.getMessage(req, "project.src.43");
        //状態名称100％
        String textStatusName100 = gsMsg.getMessage(req, "project.src.44");
        //0%の状態名称
        GSValidateProject.validateTextBoxInput(
                errors,
                prj023name0__,
                textStatusName0,
                GSConstProject.MAX_LENGTH_STATUS_NAME,
                true);

        //100%の状態名称
        GSValidateProject.validateTextBoxInput(
                errors,
                prj023name100__,
                textStatusName100,
                GSConstProject.MAX_LENGTH_STATUS_NAME,
                true);

        return errors;
    }

    /**
     * <p>prj023rate を取得します。
     * @return prj023rate
     */
    public String getPrj023rate() {
        return prj023rate__;
    }
    /**
     * <p>prj023rate をセットします。
     * @param prj023rate prj023rate
     */
    public void setPrj023rate(String prj023rate) {
        prj023rate__ = prj023rate;
    }
    /**
     * <p>prj023name を取得します。
     * @return prj023name
     */
    public String getPrj023name() {
        return prj023name__;
    }
    /**
     * <p>prj023name をセットします。
     * @param prj023name prj023name
     */
    public void setPrj023name(String prj023name) {
        prj023name__ = prj023name;
    }
    /**
     * <p>prj023state を取得します。
     * @return prj023state
     */
    public String getPrj023state() {
        return prj023state__;
    }
    /**
     * <p>prj023state をセットします。
     * @param prj023state prj023state
     */
    public void setPrj023state(String prj023state) {
        prj023state__ = prj023state;
    }

    /**
     * <p>prj023name0 を取得します。
     * @return prj023name0
     */
    public String getPrj023name0() {
        return prj023name0__;
    }

    /**
     * <p>prj023name0 をセットします。
     * @param prj023name0 prj023name0
     */
    public void setPrj023name0(String prj023name0) {
        prj023name0__ = prj023name0;
    }

    /**
     * <p>prj023name100 を取得します。
     * @return prj023name100
     */
    public String getPrj023name100() {
        return prj023name100__;
    }

    /**
     * <p>prj023name100 をセットします。
     * @param prj023name100 prj023name100
     */
    public void setPrj023name100(String prj023name100) {
        prj023name100__ = prj023name100;
    }

}

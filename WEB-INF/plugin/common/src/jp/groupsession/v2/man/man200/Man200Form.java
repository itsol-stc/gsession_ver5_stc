package jp.groupsession.v2.man.man200;

import java.util.ArrayList;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.AbstractMainForm;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 パスワードルール設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man200Form extends AbstractMainForm {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man200Form.class);

    /** 英数混在区分 */
    private int man200CoeKbn__ = 0;
    /** 同一パスワード期限区分 */
    private int man200LimitKbn__ = 0;
    /** ユーザID同一パスワード設定区分 */
    private int man200UidPswdKbn__ = 0;
    /** 旧パスワード設定区分 */
    private int man200OldPswdKbn__ = 0;
    /** 同一パスワード期限(日) */
    private String man200LimitDay__ = null;
    /** 年 */
    private int man200Digit__;
    /** 桁数リスト */
    private ArrayList<LabelValueBean> man200DigitLabelList__ = null;
    /**
     * <p>man200CoeKbn を取得します。
     * @return man200CoeKbn
     */
    public int getMan200CoeKbn() {
        return man200CoeKbn__;
    }
    /**
     * <p>man200CoeKbn をセットします。
     * @param man200CoeKbn man200CoeKbn
     */
    public void setMan200CoeKbn(int man200CoeKbn) {
        man200CoeKbn__ = man200CoeKbn;
    }
    /**
     * <p>man200Digit を取得します。
     * @return man200Digit
     */
    public int getMan200Digit() {
        return man200Digit__;
    }
    /**
     * <p>man200Digit をセットします。
     * @param man200Digit man200Digit
     */
    public void setMan200Digit(int man200Digit) {
        man200Digit__ = man200Digit;
    }
    /**
     * <p>man200DigitLabelList を取得します。
     * @return man200DigitLabelList
     */
    public ArrayList<LabelValueBean> getMan200DigitLabelList() {
        return man200DigitLabelList__;
    }
    /**
     * <p>man200DigitLabelList をセットします。
     * @param man200DigitLabelList man200DigitLabelList
     */
    public void setMan200DigitLabelList(
            ArrayList<LabelValueBean> man200DigitLabelList) {
        man200DigitLabelList__ = man200DigitLabelList;
    }
    /**
     * <p>man200LimitKbn を取得します。
     * @return man200LimitKbn
     */
    public int getMan200LimitKbn() {
        return man200LimitKbn__;
    }
    /**
     * <p>man200LimitKbn をセットします。
     * @param man200LimitKbn man200LimitKbn
     */
    public void setMan200LimitKbn(int man200LimitKbn) {
        man200LimitKbn__ = man200LimitKbn;
    }
    /**
     * <p>man200OldPswdKbn を取得します。
     * @return man200OldPswdKbn
     */
    public int getMan200OldPswdKbn() {
        return man200OldPswdKbn__;
    }
    /**
     * <p>man200OldPswdKbn をセットします。
     * @param man200OldPswdKbn man200OldPswdKbn
     */
    public void setMan200OldPswdKbn(int man200OldPswdKbn) {
        man200OldPswdKbn__ = man200OldPswdKbn;
    }
    /**
     * <p>man200UidPswdKbn を取得します。
     * @return man200UidPswdKbn
     */
    public int getMan200UidPswdKbn() {
        return man200UidPswdKbn__;
    }
    /**
     * <p>man200UidPswdKbn をセットします。
     * @param man200UidPswdKbn man200UidPswdKbn
     */
    public void setMan200UidPswdKbn(int man200UidPswdKbn) {
        man200UidPswdKbn__ = man200UidPswdKbn;
    }
    /**
     * <p>man200LimitDay を取得します。
     * @return man200LimitDay
     */
    public String getMan200LimitDay() {
        return man200LimitDay__;
    }
    /**
     * <p>man200LimitDay をセットします。
     * @param man200LimitDay man200LimitDay
     */
    public void setMan200LimitDay(String man200LimitDay) {
        man200LimitDay__ = man200LimitDay;
    }
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
        log__.debug("入力チェック開始");

        GsMessage gsMsg = new GsMessage(reqMdl);

        String eprefix = "nds040LimitDay.";
        if (StringUtil.isNullZeroString(man200LimitDay__)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text"
                    , gsMsg.getMessage("main.src.man200.2"),
                    GSConstMain.MAX_LENGTH_PSWD_LIMIT_DAY);

            StrutsUtil.addMessage(errors, msg, eprefix
                    +  "error.input.required.text");
            return errors;
        }

        if (man200LimitDay__.length() > GSConstMain.MAX_LENGTH_PSWD_LIMIT_DAY) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text"
                    , gsMsg.getMessage("main.src.man200.2"),
                    GSConstMain.MAX_LENGTH_PSWD_LIMIT_DAY);

            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.length.text");
            return errors;
        }
        if (!ValidateUtil.isNumber(man200LimitDay__)) {

            // 数字以外の文字を入力した場合
            msg = new ActionMessage("error.input.comp.text",
                    gsMsg.getMessage("main.src.man200.2"),
                    gsMsg.getMessage("cmn.numbers"));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.comp.text");
            return errors;
        }
        if (Integer.parseInt(man200LimitDay__) <= 0 || Integer.parseInt(man200LimitDay__) > 999) {

            // 数字以外の文字を入力した場合
            msg = new ActionMessage("error.input.lenge",
                    gsMsg.getMessage("main.src.man200.2"), "1", "999");
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.lenge");
            return errors;
        }
        log__.debug("入力チェック終了");
        return errors;
    }
}
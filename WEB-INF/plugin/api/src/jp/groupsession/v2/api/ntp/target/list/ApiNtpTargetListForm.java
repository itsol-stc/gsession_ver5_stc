package jp.groupsession.v2.api.ntp.target.list;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.ntp.GSValidateNippou;
/**
 * <br>[機  能] WEBAPI 日報目標一覧取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-targetList",
plugin = "nippou", name = "目標一覧取得",
url = "/api/ntp/targetList.do", reqtype = "GET")
public class ApiNtpTargetListForm extends AbstractApiForm {
    /** ユーザSID*/
    private String usrSid__;
    /** 表示月*/
    private String month__;
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        this.usrSid__ = usrSid;
    }
    /**
     * <p>startMonth を取得します。
     * @return startMonth
     */
    public String getMonth() {
        return month__;
    }
    /**
     * <p>startMonth をセットします。
     * @param startMonth startMonth
     */
    public void setMonth(String startMonth) {
        this.month__ = startMonth;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        //未入力チェック
        //目標SID   usrSid
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                "usrSid", usrSid__, "usrSid", 15, false);
//        startMonth =
        if (StringUtil.isNullZeroString(month__)) {
            UDate date = new UDate();
            String dateF = date.toString();
            month__ = dateF.substring(0, 4) + "/" + dateF.substring(4, 6);
        }
        //日付フォーマットチェック
        if (month__.length() < 7 || !GSValidateUtil.isNumber(month__.substring(0, 4))
                || !GSValidateUtil.isNumber(month__.substring(5, 7))
                || !month__.substring(4, 5).equals("/")) {
            msg = new ActionMessage("error.input.format.text", "取得月");
            StrutsUtil.addMessage(
                    errors, msg, "取得月.month");
        } else {
            //日付存在チェック
            UDate date = new UDate();
            int month = Integer.parseInt(month__.substring(5, 7));
            date.setDate(Integer.parseInt(month__.substring(0, 4)),
                        month,
                        1);

            if (date.getMonth() != month) {
                msg = new ActionMessage("error.input.notfound.date", "取得月");
                StrutsUtil.addMessage(
                        errors, msg, "取得月.month");
            }
        }

        return errors;
    }

}

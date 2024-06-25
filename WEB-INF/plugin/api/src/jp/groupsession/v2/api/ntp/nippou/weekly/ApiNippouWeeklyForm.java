package jp.groupsession.v2.api.ntp.nippou.weekly;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
/**
 * <br>[機  能] WEBAPI 日報週間データ取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouWeekly",
plugin = "nippou", name = "日報一覧取得(グループ指定、週間表示)",
url = "/api/ntp/nippouWeekly.do", reqtype = "GET")
public class ApiNippouWeeklyForm extends AbstractApiForm {
    /** グループSID*/
    @ApiParam(name = "grpSid", viewName = "グループSID")
    private String grpSid__ = null;
    /** 取得基準日*/
    private String baseDay__ = null;
    /** 自分のデータを先頭につけるかどうか */
    private String selfDataOnTop__ = null;
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public String getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(String grpSid) {
        this.grpSid__ = grpSid;
    }
    /**
     * <p>startDay を取得します。
     * @return startDay
     */
    public String getBaseDay() {
        return baseDay__;
    }
    /**
     * <p>startDay をセットします。
     * @param startDay startDay
     */
    public void setBaseDay(String startDay) {
        this.baseDay__ = startDay;
    }


    /**
     * <p>selfDataOnTop を取得します。
     * @return selfDataOnTop
     */
    public String getSelfDataOnTop() {
        return selfDataOnTop__;
    }
    /**
     * <p>selfDataOnTop をセットします。
     * @param selfDataOnTop selfDataOnTop
     */
    public void setSelfDataOnTop(String selfDataOnTop) {
        selfDataOnTop__ = selfDataOnTop;
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
        errors = GSValidateCommon.validateDateFieldText(
                errors, baseDay__, "baseDay", "取得開始日", false);
        if (!errors.isEmpty()) {
            return errors;
        }
        selfDataOnTop__ = NullDefault.getString(selfDataOnTop__, "1");
        if (!GSValidateUtil.isNumber(selfDataOnTop__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "自己データ表示フラグ");
            StrutsUtil.addMessage(errors, msg, "selfDataOnTop");
            return errors;
        }

        return errors;
    }
}

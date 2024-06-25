package jp.groupsession.v2.api.ntp.nippou.timeline;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.ntp.GSConstNippou;
/**
 * <br>[機  能] WEBAPI 日報タイムラインフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouTimeLine",
plugin = "nippou", name = "日報タイムライン取得",
url = "/api/ntp/nippouTimeLine.do", reqtype = "GET")
public class ApiNippouTimeLineForm extends AbstractApiForm {
    /** 取得件数 */
    private String maxCnt__ = null;
    /** 取得位置 */
    private String page__ = null;
    /** ユーザSID */
    private String usrSid__;
    /** グループSID */
    private String grpSid__;
    /** ソートフラグ */
    private String sort__;
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
        usrSid__ = usrSid;
    }
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
        grpSid__ = grpSid;
    }
    /**
     * <p>maxCnt を取得します。
     * @return maxCnt
     */
    public String getMaxCnt() {
        return maxCnt__;
    }


    /**
     * <p>maxCnt をセットします。
     * @param maxCnt maxCnt
     */
    public void setMaxCnt(String maxCnt) {
        maxCnt__ = maxCnt;
    }


    /**
     * <p>page を取得します。
     * @return page
     */
    public String getPage() {
        return page__;
    }


    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(String page) {
        page__ = page;
    }


    /**
     * <p>sort を取得します。
     * @return sort
     */
    public String getSort() {
        return sort__;
    }
    /**
     * <p>sort をセットします。
     * @param sort sort
     */
    public void setSort(String sort) {
        sort__ = sort;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @param sessionUsrSid セッションユーザSID
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
            Connection con, HttpServletRequest req, int sessionUsrSid) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        usrSid__ = NullDefault.getString(usrSid__, "-1");
        if (!GSValidateUtil.isNumberHaifun(usrSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_USER_SID);
            StrutsUtil.addMessage(errors, msg, "usrSid");
            return errors;

        }

        page__
        = NullDefault.getStringZeroLength(page__, "1");
        if (!GSValidateUtil.isNumber(page__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "ページ");
            StrutsUtil.addMessage(errors, msg, "page");
            return errors;
        }
        maxCnt__
        = NullDefault.getStringZeroLength(maxCnt__, "10");
        if (!GSValidateUtil.isNumber(maxCnt__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "取得件数");
            StrutsUtil.addMessage(errors, msg, "maxCnt");
            return errors;
        }
        sort__ = NullDefault.getStringZeroLength(sort__,
                String.valueOf(GSConstNippou.DATE_DESC_EDATE_DESC));
        if (!GSValidateUtil.isNumber(sort__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "sort");
            StrutsUtil.addMessage(errors, msg, "sort");
            return errors;
        }
        return errors;
    }
}

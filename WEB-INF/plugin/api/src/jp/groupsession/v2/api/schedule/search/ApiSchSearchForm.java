package jp.groupsession.v2.api.schedule.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール検索WEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-search",
plugin = "schedule", name = "スケジュール検索",
url = "/api/schedule/search.do", reqtype = "GET")
public class ApiSchSearchForm extends AbstractApiForm {

    /** ソートキー一覧 */
    private static final int[] SCH_SORTKEY__ = {GSConstSchedule.SORT_KEY_NAME,
                                                GSConstSchedule.SORT_KEY_FRDATE,
                                                GSConstSchedule.SORT_KEY_TODATE,
                                                GSConstSchedule.SORT_KEY_TITLE};

    //検索条件
    /** ターゲット */
    @ApiParam(name = "target", viewName = "検索対象(0:ユーザ、1:グループ)")
    private String target__ = null;
    /** グループ */
    @ApiParam(name = "gsid", viewName = "グループSID")
    private String gsid__ = null;
    /** ユーザ */
    @ApiParam(name = "usid", viewName = "ユーザSID")
    private String usid__ = null;
    /** 開始日from */
    private String startFrom__ = null;
    /** 開始日to */
    private String startTo__ = null;
    /** 終了日FROM endFrom */
    private String endFrom__ = null;
    /** 終了日TO   endTo */
    private String endTo__ = null;
    /** キーワード   keyWord */
    private String keyWord__ = null;
    /** キーワードand or     keyWordKbn */
    private String keyWordKbn__ = null;
    /** キーワード対象 件名  keytitle */
    private String keytitle__ = null;
    /** キーワード対象 本文  keybody */
    private String keybody__ = null;
    /** ソート1キー  sort1 */
    private String sort1__ = null;
    /** ソート1昇順降順    order1 */
    private String order1__ = null;
    /** ソート2キー  sort2 */
    private String sort2__ = null;
    /** ソート2昇順降順    order2 */
    private String order2__ = null;
    /** 結果を取得する件数   results */
    private String results__ = null;
    /** 取得開始位置  start */
    private String start__ = null;
    /** 所属グループも検索フラグ*/
    private String grpShowKbn__ = null;
    /** 同時登録情報取得フラグ*/
    private String sameInputFlg__ = null;
    /** 内容エスケープフラグ*/
    private String escapeFlg__ = null;


    /**
     * <p>endFrom を取得します。
     * @return endFrom
     */
    public String getEndFrom() {
        return endFrom__;
    }
    /**
     * <p>endFrom をセットします。
     * @param endFrom endFrom
     */
    public void setEndFrom(String endFrom) {
        endFrom__ = endFrom;
    }
    /**
     * <p>endTo を取得します。
     * @return endTo
     */
    public String getEndTo() {
        return endTo__;
    }
    /**
     * <p>endTo をセットします。
     * @param endTo endTo
     */
    public void setEndTo(String endTo) {
        endTo__ = endTo;
    }
    /**
     * <p>gsid を取得します。
     * @return gsid
     */
    public String getGsid() {
        return gsid__;
    }
    /**
     * <p>gsid をセットします。
     * @param gsid gsid
     */
    public void setGsid(String gsid) {
        gsid__ = gsid;
    }
    /**
     * <p>keybody を取得します。
     * @return keybody
     */
    public String getKeybody() {
        return keybody__;
    }
    /**
     * <p>keybody をセットします。
     * @param keybody keybody
     */
    public void setKeybody(String keybody) {
        keybody__ = keybody;
    }
    /**
     * <p>keytitle を取得します。
     * @return keytitle
     */
    public String getKeytitle() {
        return keytitle__;
    }
    /**
     * <p>keytitle をセットします。
     * @param keytitle keytitle
     */
    public void setKeytitle(String keytitle) {
        keytitle__ = keytitle;
    }
    /**
     * <p>keyWord を取得します。
     * @return keyWord
     */
    public String getKeyWord() {
        return keyWord__;
    }
    /**
     * <p>keyWord をセットします。
     * @param keyWord keyWord
     */
    public void setKeyWord(String keyWord) {
        keyWord__ = keyWord;
    }
    /**
     * <p>keyWordKbn を取得します。
     * @return keyWordKbn
     */
    public String getKeyWordKbn() {
        return keyWordKbn__;
    }
    /**
     * <p>keyWordKbn をセットします。
     * @param keyWordKbn keyWordKbn
     */
    public void setKeyWordKbn(String keyWordKbn) {
        keyWordKbn__ = keyWordKbn;
    }
    /**
     * <p>order1 を取得します。
     * @return order1
     */
    public String getOrder1() {
        return order1__;
    }
    /**
     * <p>order1 をセットします。
     * @param order1 order1
     */
    public void setOrder1(String order1) {
        order1__ = order1;
    }
    /**
     * <p>order2 を取得します。
     * @return order2
     */
    public String getOrder2() {
        return order2__;
    }
    /**
     * <p>order2 をセットします。
     * @param order2 order2
     */
    public void setOrder2(String order2) {
        order2__ = order2;
    }
    /**
     * <p>results を取得します。
     * @return results
     */
    public String getResults() {
        return results__;
    }
    /**
     * <p>results をセットします。
     * @param results results
     */
    public void setResults(String results) {
        results__ = results;
    }
    /**
     * <p>sort1 を取得します。
     * @return sort1
     */
    public String getSort1() {
        return sort1__;
    }
    /**
     * <p>sort1 をセットします。
     * @param sort1 sort1
     */
    public void setSort1(String sort1) {
        sort1__ = sort1;
    }
    /**
     * <p>sort2 を取得します。
     * @return sort2
     */
    public String getSort2() {
        return sort2__;
    }
    /**
     * <p>sort2 をセットします。
     * @param sort2 sort2
     */
    public void setSort2(String sort2) {
        sort2__ = sort2;
    }
    /**
     * <p>start を取得します。
     * @return start
     */
    public String getStart() {
        return start__;
    }
    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(String start) {
        start__ = start;
    }
    /**
     * <p>startFrom を取得します。
     * @return startFrom
     */
    public String getStartFrom() {
        return startFrom__;
    }
    /**
     * <p>startFrom をセットします。
     * @param startFrom startFrom
     */
    public void setStartFrom(String startFrom) {
        startFrom__ = startFrom;
    }
    /**
     * <p>startTo を取得します。
     * @return startTo
     */
    public String getStartTo() {
        return startTo__;
    }
    /**
     * <p>startTo をセットします。
     * @param startTo startTo
     */
    public void setStartTo(String startTo) {
        startTo__ = startTo;
    }
    /**
     * <p>target を取得します。
     * @return target
     */
    public String getTarget() {
        return target__;
    }
    /**
     * <p>target をセットします。
     * @param target target
     */
    public void setTarget(String target) {
        target__ = target;
    }
    /**
     * <p>usid を取得します。
     * @return usid
     */
    public String getUsid() {
        return usid__;
    }
    /**
     * <p>usid をセットします。
     * @param usid usid
     */
    public void setUsid(String usid) {
        usid__ = usid;
    }

    /**
     * <p>grpShowKbn を取得します。
     * @return grpShowKbn
     */
    public String getGrpShowKbn() {
        return grpShowKbn__;
    }
    /**
     * <p>grpShowKbn をセットします。
     * @param grpShowKbn grpShowKbn
     */
    public void setGrpShowKbn(String grpShowKbn) {
        grpShowKbn__ = grpShowKbn;
    }
    /**
     * <p>sameInputFlg を取得します。
     * @return sameInputFlg
     */
    public String getSameInputFlg() {
        return sameInputFlg__;
    }
    /**
     * <p>sameInputFlg をセットします。
     * @param sameInputFlg sameInputFlg
     */
    public void setSameInputFlg(String sameInputFlg) {
        sameInputFlg__ = sameInputFlg;
    }
    /**
     * <p>escapeFlg を取得します。
     * @return escapeFlg
     */
    public String getEscapeFlg() {
        return escapeFlg__;
    }
    /**
     * <p>escapeFlg をセットします。
     * @param escapeFlg escapeFlg
     */
    public void setEscapeFlg(String escapeFlg) {
        escapeFlg__ = escapeFlg;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param gsMsg GsMessage
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSchSearch(Connection con,
            RequestModel reqMdl,
            GsMessage gsMsg) throws SQLException {
        ActionErrors errors = new ActionErrors();
        String prefix = "validateSchSearch.";
        ActionMessage msg = null;

        //対象 target
        if (!StringUtil.isNullZeroString(target__)) {
            if (!target__.equals("person") && !target__.equals("group")) {
                //person groupのどちらでもない場合
                msg = new ActionMessage("error.input.comp.text", "target", "person or group");
                StrutsUtil.addMessage(errors, msg, prefix + "target");
            }
        }
        //グループSID gsid
        if (!StringUtil.isNullZeroString(gsid__)) {
            if (!ValidateUtil.isNumber(gsid__)) {
                msg = new ActionMessage("error.input.comp.text", "gsid",
                        gsMsg.getMessage("main.src.man200.3"));
                StrutsUtil.addMessage(errors, msg, prefix + "gsid");
            }
        }
        if (!StringUtil.isNullZeroString(target__) && target__.equals("group")) {
            //group指定時にgsid未入力はエラー
            if (StringUtil.isNullZeroString(gsid__)) {
                msg = new ActionMessage("error.input.required.text", "gsid");
                StrutsUtil.addMessage(errors, msg, prefix + "gsid");
            }
        }

        //ユーザSID  usid
        if (!StringUtil.isNullZeroString(usid__)) {
            if (!ValidateUtil.isNumber(usid__)) {
                msg = new ActionMessage("error.input.comp.text", "usid",
                        gsMsg.getMessage("main.src.man200.3"));
                StrutsUtil.addMessage(errors, msg, prefix + "usid");
            }
        }
        if (!StringUtil.isNullZeroString(target__) && target__.equals("person")) {
            //person指定時にusid未入力はエラー
            if (StringUtil.isNullZeroString(usid__)) {
                msg = new ActionMessage("error.input.required.text", "usid");
                StrutsUtil.addMessage(errors, msg, prefix + "usid");
            }
        }



//        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl);
//        SchAdmConfModel adminConf = schCmnBiz.getAdmConfModel(con);
//        if (adminConf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_GROUP) {
//
//            GroupBiz gpBiz = new GroupBiz();
//            String target = "";
//            boolean isCrange = false;
//            if (!StringUtil.isNullZeroString(target__) && target__.equals("group")) {
//                target = gsMsg.getMessage("cmn.group");
//                int usrSid = NullDefault.getInt(gsid__, -1);
//                isCrange = gpBiz.isBelongGroup(reqMdl.getSmodel().getUsrsid(), usrSid, con);
//            }
//            if (StringUtil.isNullZeroString(target__) || target__.equals("person")) {
//                target = gsMsg.getMessage("cmn.user");
//                int usrSid = NullDefault.getInt(usid__, -1);
//                isCrange = gpBiz.isBothBelongGroup(reqMdl.getSmodel().getUsrsid(), usrSid, con);
//            }
//
//            if (!isCrange) {
//                msg = new ActionMessage("cant.execute.out.crange",
//                        target,
//                        gsMsg.getMessage("ntp.115"));
//                errors.add("" + "cant.execute.out.crange", msg);
//                return errors;
//            }
//
//        }


        //開始日FROM startFrom
        GSValidateCommon.validateDateFieldText(errors, startFrom__,
                "startFrom", "startFrom", false);
        //開始日TO   startTo
        GSValidateCommon.validateDateFieldText(errors, startTo__, "startTo", "startTo", false);
        //終了日FROM endFrom
        GSValidateCommon.validateDateFieldText(errors, endFrom__, "endFrom", "endFrom", false);
        //終了日TO   endTo
        GSValidateCommon.validateDateFieldText(errors, endTo__, "endTo", "endTo", false);
        //キーワード   keyWord
        //キーワードand or     keyWordKbn
        __validateZeroIchi(errors, prefix, keyWordKbn__, "keyWordKbn", gsMsg);
        //キーワード対象 件名  keytitle
        __validateZeroIchi(errors, prefix, keytitle__, "keytitle", gsMsg);
        //キーワード対象 本文  keybody
        __validateZeroIchi(errors, prefix, keybody__, "keybody", gsMsg);
        //ソート1キー  sort1
        __validateSortKey(errors, prefix, sort1__, "sort1", gsMsg);
        //ソート1昇順降順    order1
        __validateZeroIchi(errors, prefix, order1__, "order1", gsMsg);
        //ソート2キー  sort2
        __validateSortKey(errors, prefix, sort2__, "sort2", gsMsg);
        //ソート2昇順降順    order2
        __validateZeroIchi(errors, prefix, order2__, "order2", gsMsg);
        //結果を取得する件数   results
        if (!StringUtil.isNullZeroString(results__)) {
            if (!ValidateUtil.isNumber(results__)) {
                msg = new ActionMessage("error.input.comp.text", "results",
                        gsMsg.getMessage("main.src.man200.3"));
                StrutsUtil.addMessage(errors, msg, prefix + "results");
            }
        }
        //取得開始位置  start
        if (!StringUtil.isNullZeroString(start__)) {
            if (!ValidateUtil.isNumber(start__)) {
                msg = new ActionMessage("error.input.comp.text", "start",
                        gsMsg.getMessage("main.src.man200.3"));
                StrutsUtil.addMessage(errors, msg, prefix + "start");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 0 or 1のValidateを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param prefix エラーキー保存時のPrefix
     * @param value チェック対象の値
     * @param fieldName パラメータ名
     * @param gsMsg GsMessage
     */
    private void __validateZeroIchi(ActionErrors errors, String prefix,
            String value, String fieldName, GsMessage gsMsg) {
        ActionMessage msg = null;
        if (!StringUtil.isNullZeroString(value)) {
            if (!ValidateUtil.isNumber(value)) {
                msg = new ActionMessage("error.input.comp.text", fieldName,
                        gsMsg.getMessage("main.src.man200.3"));
                StrutsUtil.addMessage(errors, msg, prefix + "keybody");
            } else {
                int kkbn = NullDefault.getInt(value, -1);
                if (kkbn != 0 && kkbn != 1) {
                    msg = new ActionMessage("error.input.comp.text", fieldName, "0 or 1");
                    StrutsUtil.addMessage(errors, msg, prefix + fieldName);
                }
            }
        }
    }

    /**
     * <br>[機  能] ソートキーのValidateを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param prefix エラーキー保存時のPrefix
     * @param value チェック対象の値
     * @param fieldName パラメータ名
     * @param gsMsg GsMessage
     */
    public void __validateSortKey(ActionErrors errors, String prefix,
                                    String value, String fieldName,  GsMessage gsMsg) {
        ActionMessage msg = null;
        if (!StringUtil.isNullZeroString(value)) {
            if (!ValidateUtil.isNumber(value)) {
                msg = new ActionMessage("error.input.comp.text", fieldName,
                        gsMsg.getMessage("main.src.man200.3"));
                StrutsUtil.addMessage(errors, msg, prefix + "keybody");
            } else {
                int sortKey = NullDefault.getInt(value, -1);
                if (Arrays.binarySearch(SCH_SORTKEY__, sortKey) < 0) {
                    msg = new ActionMessage("error.input.comp.text", fieldName, "1 or 2 or 3 or 4");
                    StrutsUtil.addMessage(errors, msg, prefix + fieldName);
                }
            }
        }
    }
}

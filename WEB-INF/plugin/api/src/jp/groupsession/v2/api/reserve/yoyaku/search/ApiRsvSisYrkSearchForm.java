package jp.groupsession.v2.api.reserve.yoyaku.search;

import java.util.Arrays;

import org.apache.oro.text.perl.Perl5Util;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "reserve-yoyaku/search",
plugin = "reserve", name = "施設利用状況照会",
url = "/api/reserve/yoyaku/search.do", reqtype = "GET")
public class ApiRsvSisYrkSearchForm extends AbstractApiForm {
    /** ソートキー一覧 */
    private static final int[] RSV_SORTKEY__ = {
        1, //利用者
        2, //施設
        3, //利用開始日
        4, //利用終了日
        5, //利用目的
        };

    /** ソートキー一覧 */
    private static final int[] RSV_STATUS__ = {
        0, //すべて
        GSConstReserve.SRH_APPRSTATUS_NORMAL, 
        GSConstReserve.SRH_APPRSTATUS_NOAPPR,
        GSConstReserve.SRH_APPRSTATUS_APPRONLY,
        };
    /** エラーメッセージIDプレフィックス*/
    private static final String VALIDATE_ERRID_PREFIX = "validateRsvSearch.";
    /** 施設グループSID */
    private String rsgSid__;
    /** 施設SID */
    private String rsdSid__;
    /** 開始日 */
    private String startTime__;
    /** 終了日 */
    private String endTime__;
    /** 登録日FROM */
    private String addTimeFrom__;
    /** 登録日TO */
    private String addTimeTo__;
    /** 編集日FROM */
    private String editTimeFrom__;
    /** 編集日TO */
    private String editTimeTo__;
    /** 承認状況 */
    private String statusKbn__;

    /** キーワード */
    private String keyWord__;
    /** キーワードand or */
    private String keyWordKbn__;
    /** キーワード対象 利用目的 */
    private String keytitle__;
    /** キーワード対象 内容・備考 */
    private String keybody__;
    /** ソート1キー */
    private String sort1__ = "1";

    /** ソート1昇順降順 */
    private String order1__;
    /** ソート2キー */
    private String sort2__ = "2";

    /** ソート2昇順降順 */
    private String order2__ = "0";
    /** 結果を取得する件数 */
    private String results__ = "50";
    /** 取得開始位置 */
    private String start__ = "0";
    /**
     * <p>rsgSid を取得します。
     * @return rsgSid
     */
    public String getRsgSid() {
        return rsgSid__;
    }
    /**
     * <p>rsgSid をセットします。
     * @param rsgSid rsgSid
     */
    public void setRsgSid(String rsgSid) {
        rsgSid__ = rsgSid;
    }
    /**
     * <p>rsdSid を取得します。
     * @return rsdSid
     */
    public String getRsdSid() {
        return rsdSid__;
    }
    /**
     * <p>rsdSid をセットします。
     * @param rsdSid rsdSid
     */
    public void setRsdSid(String rsdSid) {
        rsdSid__ = rsdSid;
    }
    /**
     * <p>startTime を取得します。
     * @return startTime
     */
    public String getStartTime() {
        return startTime__;
    }
    /**
     * <p>startTime をセットします。
     * @param startTime startTime
     */
    public void setStartTime(String startTime) {
        startTime__ = __dateLengeUnit(startTime, false);
    }
    /**
     * <p>endTime を取得します。
     * @return endTime
     */
    public String getEndTime() {
        return endTime__;
    }
    /**
     * <p>endTime をセットします。
     * @param endTime endTime
     */
    public void setEndTime(String endTime) {
        endTime__ = __dateLengeUnit(endTime, true);
    }
    /**
     * <p>addTimeFrom を取得します。
     * @return addTimeFrom
     */
    public String getAddTimeFrom() {
        return addTimeFrom__;
    }
    /**
     * <p>addTimeFrom をセットします。
     * @param addTimeFrom addTimeFrom
     */
    public void setAddTimeFrom(String addTimeFrom) {
        addTimeFrom__ = __dateLengeUnit(addTimeFrom, false);
    }
    /**
     * <p>addTimeTo を取得します。
     * @return addTimeTo
     */
    public String getAddTimeTo() {
        return addTimeTo__;
    }
    /**
     * <p>addTimeTo をセットします。
     * @param addTimeTo addTimeTo
     */
    public void setAddTimeTo(String addTimeTo) {
        addTimeTo__ = __dateLengeUnit(addTimeTo, true);
    }
    /**
     * <p>editTimeFrom を取得します。
     * @return editTimeFrom
     */
    public String getEditTimeFrom() {
        return editTimeFrom__;
    }
    /**
     * <p>editTimeFrom をセットします。
     * @param editTimeFrom editTimeFrom
     */
    public void setEditTimeFrom(String editTimeFrom) {
        editTimeFrom__ = __dateLengeUnit(editTimeFrom, false);
    }
    /**
     * <p>editTimeTo を取得します。
     * @return editTimeTo
     */
    public String getEditTimeTo() {
        return editTimeTo__;
    }
    /**
     * <p>editTimeTo をセットします。
     * @param editTimeTo editTimeTo
     */
    public void setEditTimeTo(String editTimeTo) {
        editTimeTo__ = __dateLengeUnit(editTimeTo, true);
        
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public String getStatusKbn() {
        return statusKbn__;
    }
    /**
     * <p>status をセットします。
     * @param statusKbn status
     */
    public void setStatusKbn(String statusKbn) {
        statusKbn__ = statusKbn;
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
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GsMessage
     * @return エラー
     */
    public ActionErrors validate(GsMessage gsMsg) {
        ActionErrors errors = new ActionErrors();
        
        //取得期間 開始・終了
        __valdateDateToDate(errors,
                startTime__, endTime__,
                gsMsg.getMessage("reserve.157"),
                gsMsg.getMessage("reserve.158"),
                gsMsg.getMessage("reserve.156"),                
                "startTime", "endTime"
                );
        //登録日 開始・終了
        __valdateDateToDate(errors,
                addTimeFrom__, addTimeTo__,
                gsMsg.getMessage("api.rsv.yrk.search.1"),
                gsMsg.getMessage("api.rsv.yrk.search.2"),
                gsMsg.getMessage("api.rsv.yrk.search.3"),                
                "addTimeFrom", "addTimeTo"
                );
        //編集日 開始・終了
        __valdateDateToDate(errors,
                editTimeFrom__, editTimeTo__,
                gsMsg.getMessage("api.rsv.yrk.search.4"),
                gsMsg.getMessage("api.rsv.yrk.search.5"),
                gsMsg.getMessage("api.rsv.yrk.search.6"),                
                "editTimeFrom", "editTimeTo"
                );
        //施設SID
        GSValidateApi.validateSid(errors, rsdSid__, "rsdSid",
                gsMsg.getMessage("api.rsv.rsd.sid"), false);
        
        //施設グループSID
        GSValidateApi.validateSid(errors, rsgSid__, "rsgSid",
                gsMsg.getMessage("api.rsv.rsg.sid"), false);
        
        //承認状況
        __validateFlags(errors, statusKbn__, 
                gsMsg.getMessage("api.rsv.yrk.search.7"),
                "statusKbn", gsMsg, RSV_STATUS__);
        
//        キーワード
//        キーワードand or 
        __validateZeroIchi(errors, keyWordKbn__, 
                gsMsg.getMessage("api.rsv.yrk.search.8"),
                "keyWordKbn", gsMsg);
//        キーワード対象 利用目的
        __validateZeroIchi(errors, keytitle__, 
                gsMsg.getMessage("api.rsv.yrk.search.9"),
                "keytitle", gsMsg);
//        キーワード対象 内容・備考
        __validateZeroIchi(errors, keybody__,  
                gsMsg.getMessage("api.rsv.yrk.search.10"),
                "keybody", gsMsg);

        //ソートキー１
        __validateFlags(errors, sort1__,  
                gsMsg.getMessage("api.rsv.yrk.search.11"),
                "sort1", gsMsg, RSV_SORTKEY__);
//        ソート1昇順降順
        __validateZeroIchi(errors, order1__,  
                gsMsg.getMessage("api.rsv.yrk.search.12"),
                "order1", gsMsg);
        //ソートキー2
        __validateFlags(errors, sort2__,  
                gsMsg.getMessage("api.rsv.yrk.search.13"),
                "sort2", gsMsg, RSV_SORTKEY__);
//        ソート2昇順降順
        __validateZeroIchi(errors, order2__,  
                gsMsg.getMessage("api.rsv.yrk.search.14"),
                "order2", gsMsg);
        
        
        return errors;
    }    
    /**
     * 
     * <br>[機  能] 日付間入力チェック
     * <br>[解  説] 渡された二つの日付の入力文字列のフォーマットを確認
     * 　　　　　　　　日付が二つ渡された場合はそれぞれの大小が正しいことを確認
     * <br>[備  考]
     * @param errors エラーメッセージ格納先
     * @param start 開始日文字列
     * @param end 終了日文字列
     * @param startDspName 開始日表示名
     * @param endDspName 終了日表示名
     * @param s2eDspName 開始・終了 表示名
     * @param startParamName 開始日パラメータ名
     * @param endParamName 終了日パラメータ名
     * 
     */
    public void __valdateDateToDate(ActionErrors errors,
            String start, String end,
            String startDspName, String endDspName, String s2eDspName,
            String startParamName, String endParamName) {
        ActionMessage msg = null;
        __validateDateTimeFieldText(errors,
                start,
                startDspName,
                VALIDATE_ERRID_PREFIX + startParamName,
                false);
        __validateDateTimeFieldText(errors,
                end,
                endDspName,
                VALIDATE_ERRID_PREFIX + endParamName,
                false);
        if (errors.isEmpty() && start != null && end != null) {
            UDate frdate = UDateUtil.getUDate(start.substring(0, 4),
                    start.substring(5, 7),
                    start.substring(8, 10));
            frdate.setZeroHhMmSs();            
//            if (start.length() > 10) {
                frdate.setHour(Integer.valueOf(start.substring(11, 13)));
                frdate.setMinute(Integer.valueOf(start.substring(14, 16)));
                frdate.setSecond(Integer.valueOf(start.substring(17, 19)));
//            }
            UDate todate = UDateUtil.getUDate(end.substring(0, 4),
                    end.substring(5, 7),
                    end.substring(8, 10));
            todate.setMaxHhMmSs();            
//            if (end.length() > 10) {
                todate.setHour(Integer.valueOf(end.substring(11, 13)));
                todate.setMinute(Integer.valueOf(end.substring(14, 16)));
                frdate.setSecond(Integer.valueOf(end.substring(17, 19)));
//            }
            
            if (frdate.compareDateYMD(todate) == UDate.SMALL) {
                //開始 < 終了
                String textStartLessEnd = startDspName + " <= " + endDspName;
                //開始・終了
                msg = new ActionMessage("error.input.comp.text", s2eDspName, textStartLessEnd);
                errors.add(VALIDATE_ERRID_PREFIX + "error.input.comp.text", msg);
            }
        }
        
    }
    /**
     * <br>[機  能] 0 or 1のValidateを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value チェック対象の値
     * @param fieldDspName 表示パラメータ名
     * @param fieldName パラメータ名
     * @param gsMsg GsMessage
     */
    private void __validateZeroIchi(ActionErrors errors, 
            String value, String fieldDspName, String fieldName, GsMessage gsMsg) {
        ActionMessage msg = null;
        if (!StringUtil.isNullZeroString(value)) {
            if (!ValidateUtil.isNumber(value)) {
                msg = new ActionMessage("error.input.comp.text", fieldDspName,
                        gsMsg.getMessage("main.src.man200.3"));
                StrutsUtil.addMessage(errors, msg, VALIDATE_ERRID_PREFIX + fieldName);
            } else {
                int kkbn = NullDefault.getInt(value, -1);
                if (kkbn != 0 && kkbn != 1) {
                    msg = new ActionMessage("error.input.comp.text", fieldDspName, "0 or 1");
                    StrutsUtil.addMessage(errors, msg, VALIDATE_ERRID_PREFIX + fieldName);
                }
            }
        }
    }

    /**
     * <br>[機  能] 数字指定フラグのValidateを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value チェック対象の値
     * @param fieldDspName 表示パラメータ名
     * @param fieldName パラメータ名
     * @param gsMsg GsMessage
     * @param arrays 入力可能数字配列
     */
    public void __validateFlags(ActionErrors errors, 
                                    String value, String fieldDspName, String fieldName,
                                    GsMessage gsMsg,
                                    int[] arrays) {
        ActionMessage msg = null;
        if (!StringUtil.isNullZeroString(value)) {
            if (!ValidateUtil.isNumber(value)) {
                msg = new ActionMessage("error.input.comp.text", fieldDspName,
                        gsMsg.getMessage("main.src.man200.3"));
                StrutsUtil.addMessage(errors, msg, VALIDATE_ERRID_PREFIX + fieldName);
            } else {
                int sortKey = NullDefault.getInt(value, -1);
                if (Arrays.binarySearch(arrays, sortKey) < 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i__ : arrays) {
                        sb.append(i__);
                        sb.append(" or ");
                    }
                    sb.delete(sb.length() - 4, sb.length());
                    msg = new ActionMessage("error.input.comp.text", fieldDspName, sb.toString());
                    StrutsUtil.addMessage(errors, msg, VALIDATE_ERRID_PREFIX + fieldName);
                }
            }
        }
    }   
    /**
     * <br>[機  能] 日時テキストフィールド(yyyy/mm/dd hh:mm:ss)の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックするフィールドの文字列
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param fieldName チェックするフィールド
     * @param indispensable 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors __validateDateTimeFieldText(
                                        ActionErrors errors,
                                        String value,
                                        String checkObject,
                                        String fieldName,
                                        boolean indispensable) {

        ActionMessage msg = null;

        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            if (indispensable) {
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, fieldfix + fieldName);
            }
            return errors;
        } else {
            //日時フォーマットチェック
            if (!__isSlashDateTimeFormat(value)) {
                msg = new ActionMessage("error.input.format.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, fieldfix + fieldName);
            } else {
                //日時存在チェック
                UDate date = new UDate();

                int month = Integer.parseInt(value.substring(5, 7));
                date.setDate(Integer.parseInt(value.substring(0, 4)),
                            month,
                            Integer.parseInt(value.substring(8, 10)));

                if (date.getMonth() != month) {
                    msg = new ActionMessage("error.input.notfound.date", checkObject);
                    StrutsUtil.addMessage(
                            errors, msg, fieldfix + fieldName);
                } else {

                    int hour = Integer.parseInt(value.substring(11, 13));
                    date.setHour(hour);
                    date.setMinute(Integer.parseInt(value.substring(14, 16)));
                    date.setSecond(Integer.parseInt(value.substring(17, 19)));
                    if (date.getIntHour() != hour) {
                        msg = new ActionMessage("error.input.notfound.time", checkObject);
                        StrutsUtil.addMessage(
                                errors, msg, fieldfix + fieldName);
                    }
                }
            }
        }


        return errors;
    }    
    
    /**
     * <br>[機  能] 指定された文字列がyyyy/mm/dd hh:mm:ss形式かチェックする。
     * <br>[解  説]
     * <br>[備  考] 
     *
     * @param date チェック対象の文字列
     * @return true:yyyy/mm/dd hh:mm:ss型である false:yyyy/mm/dd hh:mm:ss型ではない
     */
    public static boolean __isSlashDateTimeFormat(String date) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$/", date)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 
     * <br>[機  能] 日付文字列の桁合わせ
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付文字列
     * @param totime 最大時間
     * @return 桁合わせ後 時間文字列
     */
    private String __dateLengeUnit(String dateStr, boolean totime) {
       if (dateStr.length() <= 10) {
           if (totime) {
               return dateStr + " 23:59:59";               
           }
           return dateStr + " 00:00:00";
       }
       if (dateStr.length() <= 16) {
           if (totime) {
               return dateStr + ":59";               
           }
           return dateStr + ":00";
       }
       return dateStr;
    }
}

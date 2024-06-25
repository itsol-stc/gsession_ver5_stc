package jp.groupsession.v2.api;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;

/**
 * <br>[機  能] Apiのデータ型に関するユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiDataTypeUtil {

    /**
     * <br>[機  能] UDateをyy/MM/dd形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY/MM/DD形式の文字列
     */
    public static String getDate(UDate date) {
        if (date == null) {
            return "";
        }
        return date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay();
    }

    /**
     * <br>[機  能] UDateをyy/MM/dd hh:mm:ss形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY/MM/DD形式の文字列
     */
    public static String getDateTime(UDate date) {
        if (date == null) {
            return "";
        }
        return getDate(date) + " " + UDateUtil.getSeparateHMS(date);
    }
}

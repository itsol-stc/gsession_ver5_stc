package jp.groupsession.v2.tcd;

import java.sql.Time;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] タイムカードプラグイン関連のユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TimecardUtil {

    /**
     * <br>[機  能]Timeオブジェクトをhh:mm:ss形式へ変換後、hh:mm形式に変換
     * <br>[解  説]
     * <br>[備  考]
     * @param  t Time
     * @return hh:mm形式の文字列 形式が不正な場合はnullを返す
     */
    public static String getTimeString(Time t) {
        String ret = "";
        if (t == null) {
            return ret;
        }
        String hhmmss = t.toString();

        String[] time;
        time = hhmmss.split(":");

        if (time.length == 3) {
            return time[0] + ":" + time[1];
        } else {
            return ret;
        }
    }
    /**
     * <br>[機  能]Timeオブジェクトをhh:mm:ss形式へ変換後、hh:mm形式に変換
     * <br>[解  説]
     * <br>[備  考]
     * @param  t Time
     * @param addHour 加算する時間
     * @return hh:mm形式の文字列 形式が不正な場合はnullを返す
     */
    public static String getTimeString(Time t, int addHour) {
        String ret = "";
        if (t == null) {
            return ret;
        }
        String hhmmss = t.toString();

        String[] time;
        time = hhmmss.split(":");

        if (time.length == 3) {
            int hour = Integer.parseInt(time[0]) + addHour;
            return hour + ":" + time[1];
        } else {
            return ret;
        }
    }
    /**
     * <br>[機  能]Timeオブジェクトをhh:mm:ss形式へ変換後、hh:mm形式に変換
     * <br>[解  説]t1～t2の形式
     * <br>[備  考]
     * @param  t1 Time from
     * @param  t2 Time to
     * @return hh:mm形式の文字列 形式が不正な場合はnullを返す
     */
    public static String getTimeString(Time t1, Time t2) {
        String ret = "";
        if (t1 == null || t2 == null) {
            return ret;
        }

        StringBuilder buf = new StringBuilder();
        String hhmmss1 = t1.toString();
        String hhmmss2 = t2.toString();
        String[] time1;
        String[] time2;
        time1 = hhmmss1.split(":");
        time2 = hhmmss2.split(":");

        if (time1.length == 3) {
            buf.append(time1[0] + ":" + time1[1]);
        }
        buf.append("～");
        if (time2.length == 3) {

            int timeFr = (TimecardUtil.getTimeHour(t1) * 100) + TimecardUtil.getTimeMin(t1);
            int timeTo = (TimecardUtil.getTimeHour(t2) * 100) + TimecardUtil.getTimeMin(t2);

            if (timeTo <= timeFr) {
                int toHour = Integer.parseInt(time2[0]);
                toHour = toHour + 24;
                buf.append(toHour + ":" + time2[1]);
            } else {
                buf.append(time2[0] + ":" + time2[1]);
            }

        }
        return buf.toString();
    }
    /**
     * <br>[機  能]Timeオブジェクトから時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param  t Time
     * @return int hour
     */
    public static int getTimeHour(Time t) {
        int ret = 0;
        if (t == null) {
            return ret;
        }
        String hhmmss = t.toString();

        String[] time;
        time = hhmmss.split(":");

        if (time.length == 3) {
            return Integer.parseInt(time[0]);
        } else {
            return ret;
        }
    }
    /**
     * <br>[機  能]Timeオブジェクトから分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param  t Time
     * @return int hour
     */
    public static int getTimeMin(Time t) {
        int ret = 0;
        if (t == null) {
            return ret;
        }
        String hhmmss = t.toString();

        String[] time;
        time = hhmmss.split(":");

        if (time.length == 3) {
            return Integer.parseInt(time[1]);
        } else {
            return ret;
        }
    }

    /**
     * <br>[機  能] 指定されたUDateに時刻を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @param t 時刻
     */
    public static void setTime(UDate date, Time t) {
        date.setHour(getTimeHour(t));
        date.setMinute(getTimeMin(t));
    }

    /**
     * <br>[機  能] int形式の曜日から漢字一字の曜日に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param week UDateで定めるint形式の曜日
     * @param reqMdl リクエスト情報
     * @return 漢字一字の曜日("月"など)
     */
    public static String toStrWeek(int week, RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String sunday = gsMsg.getMessage("cmn.day");
        String monday = gsMsg.getMessage("cmn.Monday");
        String tuesday = gsMsg.getMessage("cmn.tuesday");
        String wednesday = gsMsg.getMessage("cmn.wednesday");
        String thursday = gsMsg.getMessage("cmn.thursday");
        String friday = gsMsg.getMessage("cmn.friday");
        String saturday = gsMsg.getMessage("cmn.saturday");

        switch (week) {
        case UDate.MONDAY:
            return monday;

        case UDate.TUESDAY:
            return tuesday;

        case UDate.WEDNESDAY:
            return wednesday;

        case UDate.THURSDAY:
            return thursday;

        case UDate.FRIDAY:
            return friday;

        case UDate.SATURDAY:
            return saturday;

        case UDate.SUNDAY:
            return sunday;

        default:
            return "";
        }
    }

    /**
     * <br>[機  能]ストリングの配列をカンマ区切りの文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param strs ストリングの配列
     * @return カンマ区切りの文字列 配列がnullだった場合はnullを返す
     */
    public static String toCSV(String[] strs) {
        if (null == strs) {
            return null;
        }

        StringBuilder strBuf = new StringBuilder();

        for (int i = 0; i < strs.length; i++) {
            strBuf.append(strs[i]);

            if (i != (strs.length - 1)) {
                strBuf.append(",");
            }
        }

        return strBuf.toString();
    }
}

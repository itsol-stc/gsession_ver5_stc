package jp.groupsession.v2.tcd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.tcd.dao.TcdTcdataDao;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;
import jp.groupsession.v2.tcd.dao.TimecardDao;

/**
 * <br>[機  能] タイムカード有休日数情報のビジネスロジッククラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TimecardYukyuConfBiz {
    
    
    /**
     * <br>[機  能] 有休情報の最低年度から現在の年度+1までの年度リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 年度リスト
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getNendoList(Connection con) throws SQLException {
        
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        
        //表示年度の設定
        int minNendo = getMinYear(con);
        int maxNendo = getMaxYear(con);
        while (minNendo <= maxNendo) {
            LabelValueBean nendoBean = new LabelValueBean();
            nendoBean.setLabel(String.valueOf(minNendo));
            nendoBean.setValue(String.valueOf(minNendo));
            ret.add(nendoBean);
            minNendo++;
        }
        return ret;
    }
    
    /**
     * <br>[機  能] 有休情報を取得できる最小年度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 有休情報を取得できる最大年度
     * @throws SQLException SQL実行例外
     */
    public int getMinYear(Connection con) throws SQLException {
        
        //表示年度の設定
        int nowYear = getThisNendo(con);
        TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
        TcdTcdataDao ttDao = new TcdTcdataDao(con);
        int tyMinYear = NullDefault.getInt(tydDao.getMinNendo(), nowYear);
        UDate minYearDate = ttDao.getMinNendo();
        int ttMinYear = getNendo(con, minYearDate);
        
        int minNendo = 0;
        if (nowYear < tyMinYear && nowYear < ttMinYear) {
            minNendo = nowYear;
        } else {
            if (ttMinYear < tyMinYear) {
                minNendo = ttMinYear;    
            } else {
                minNendo = tyMinYear;
            }
        }
        
        return minNendo;
    }
    
    /**
     * <br>[機  能] 有休情報を取得できる最大年度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 有休情報を取得できる最大年度
     * @throws SQLException SQL実行例外
     */
    public int getMaxYear(Connection con) throws SQLException {
        
        //表示年度の設定
        int nowYear = getThisNendo(con);
        TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
        String maxNendoStr = tydDao.getMaxNendo();
        int maxNendo;
        
        if (NullDefault.getInt(maxNendoStr, 0) > nowYear + 5) {
            maxNendo = Integer.parseInt(maxNendoStr);
        } else {
            maxNendo = nowYear + 5;
        }
        return maxNendo;
    }
    
    /**
     * <br>[機  能] うるう年かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     * @return true:うるう年 false:うるう年ではない
     */
    public boolean isUruYear(int year) {
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * <br>[機  能] 期首月を考慮し、今年度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 今年度
     * @throws SQLException SQL実行例外
     */
    public int getThisNendo(Connection con) throws SQLException {

        UDate now = new UDate();
        int ret = now.getYear();
        //期首月を取得
        TimecardDao tcdDao = new TimecardDao(con);
        String kishuStr = NullDefault.getString(String.valueOf(tcdDao.getKishuMonth()), "4");
        int month = now.getMonth();
        
        if (1 <= month && month < Integer.parseInt(kishuStr)) {
            ret = ret - 1;
        }
        return ret;
    }
    
    /**
     * <br>[機  能] 期首月を考慮し、今年度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 今年度
     * @throws SQLException SQL実行例外
     */
    public int getNendo(Connection con, UDate nendo) throws SQLException {

        int ret = nendo.getYear();
        //期首月を取得
        TimecardDao tcdDao = new TimecardDao(con);
        String kishuStr = NullDefault.getString(String.valueOf(tcdDao.getKishuMonth()), "4");
        int month = nendo.getMonth();
        
        if (1 <= month && month < Integer.parseInt(kishuStr)) {
            ret = ret - 1;
        }
        return ret;
    }
}

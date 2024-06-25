package jp.groupsession.v2.fil.util;

import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;

/**
 *
 * <br>[機  能] ファイル管理特有の表示文字列変換メソッド ユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilStringUtil {
    /**
     * <br>[機  能] ファイルの物理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param money 取引金額(BigDecimal)
     * @return 整形後の取引金額(整数部:3桁毎にカンマ、小数部:不要な桁の除外
     * @throws SQLException SQL実行時例外
     */
    public static String getDspErrlTradeMoney(String money) {

        if (money.indexOf(".") != -1) {
            String money_top = StringUtil.toCommaFormat(money.substring(0, money.indexOf(".")));
            String money_bottom = money.substring(money.indexOf(".") + 1);
            int money_bottom_length = money_bottom.length();
            for (int i = 0; i < money_bottom_length; i++) {
                String check = money_bottom.substring(money_bottom_length - i - 1);
                if (check.equals("0")) {
                    money_bottom = money_bottom.substring(0, (money_bottom_length - i - 1));
                }
            }
            if (money_bottom.length() > 0) {
                money_bottom = "." + money_bottom;
            }
            money = money_top + money_bottom;
        } else {
            money = StringUtil.toCommaFormat(money);
        }
        return money;
    }

}

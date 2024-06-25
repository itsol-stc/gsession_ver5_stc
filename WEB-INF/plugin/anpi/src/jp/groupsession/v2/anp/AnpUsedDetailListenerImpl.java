package jp.groupsession.v2.anp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.groupsession.v2.anp.dao.AnpDatausedSumDao;
import jp.groupsession.v2.anp.model.AnpDatausedSumModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.usedsize.IUsedDetailListener;

/**
 * <br>[機  能] データ使用量取得時に実行されるリスナーを実装
 * <br>[解  説] タイムカードについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class AnpUsedDetailListenerImpl implements IUsedDetailListener {

    /**
     * <br>[機  能] データ使用量の詳細を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return データ使用量の詳細
     * @throws SQLException SQL実行時例外
     */
    public Map<String, Long> getDatails(Connection con) throws SQLException {
        Map<String, Long> detailMap = new LinkedHashMap<String, Long>();

        AnpDatausedSumDao dataUsedDao = new AnpDatausedSumDao(con);
        AnpDatausedSumModel totalDataMdl = dataUsedDao.getTotalData();
        if (totalDataMdl != null) {
            detailMap.put("main.useddisk.anp.hdata", totalDataMdl.getAnpHdataSize());
            detailMap.put("main.useddisk.anp.jdata", totalDataMdl.getAnpJdataSize());
            detailMap.put("main.useddisk.anp.mail", totalDataMdl.getAnpMtepSize());
        }
        return detailMap;
    }

    /**
     * <br>[機  能] プラグイン独自の集計マップを使用するかを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true: プラグイン独自の集計マップを使用する, false: 共通の集計マップを使用する
     * @throws SQLException SQL実行時例外
     */
    public boolean isUseSenyoDetails(Connection con) throws SQLException {
        return false;
    }

    /**
     * <br>[機  能] プラグイン独自の集計マップHTMLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return プラグイン独自の集計マップHTML
     * @throws SQLException SQL実行時例外
     */
    public String getUseSenyoHtml(Connection con, RequestModel reqMdl) throws SQLException {
        return "";
    }
}
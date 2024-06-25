package jp.groupsession.v2.mem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.usedsize.IUsedDetailListener;
import jp.groupsession.v2.mem.dao.MemoDatausedSumDao;
import jp.groupsession.v2.mem.model.MemoDatausedSumModel;
/**
 * <br>[機  能] データ使用量取得時に実行されるリスナーを実装
 * <br>[解  説] メモについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class MemoUsedDetailListenerImpl implements IUsedDetailListener {

    @Override
    public Map<String, Long> getDatails(Connection con) throws SQLException {
        Map<String, Long> detailMap = new LinkedHashMap<String, Long>();

        MemoDatausedSumDao dataUsedDao = new MemoDatausedSumDao(con);
        MemoDatausedSumModel totalDataMdl = dataUsedDao.getTotalData();
        if (totalDataMdl != null) {
            detailMap.put("main.useddisk.memo.memodata", totalDataMdl.getMemoDataSize());
        }

        return detailMap;
    }

    @Override
    public boolean isUseSenyoDetails(Connection con) throws SQLException {
        return false;
    }

    @Override
    public String getUseSenyoHtml(Connection con, RequestModel reqMdl)
            throws SQLException {
        return null;
    }

}

package jp.groupsession.v2.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.api.biz.ApiConfBiz;
import jp.groupsession.v2.api.dao.ApiTokenDao;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
/**
 *
 * <br>[機  能] 日次バッチ起動時に実行されるリスナー
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiBatchListenerImpl implements IBatchListener {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ApiBatchListenerImpl.class);

    @Override
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {
        String pluginName = "WEBAPI";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;
        boolean success = false;
        try {
            log__.debug("有効期限切れのトークンを削除する");
            ApiConfBiz confBiz = new ApiConfBiz();
            UDate delDate = new UDate();
            ApiTokenDao tokenDao = new ApiTokenDao(con);
            //無期限かどうか判定する            
            if (confBiz.isTokenLifeDayBatchFree(con) && confBiz.isTokenAutoDelete(con)) {
                ArrayList<String> delList = new ArrayList<String>();
                //個体識別番号が同じ かつ ユーザSIDが同じの日時が古いトークン情報を削除
                //個体識別番号が空 かつ IPアドレスが同じの日時が古いトークン情報を削除
                delList = tokenDao.selectDelTarget();
                tokenDao.deleteAuto(delList);
                //無効化済みトークンの削除
                tokenDao.deleteMuko();
            }
            //トークン期限が1日以上の場合は現在時＋1日の間のトークンを削除することで
            //24時間ではなく日時バッチをまたぐ毎にトークンが無効化しているようにする
            if (confBiz.isTokenLifeDayBatch(con)) {
                delDate.addDay(1);
            }
            tokenDao.deleteLimitOver(delDate);
            con.commit();
            success = true;
        } catch (SQLException e) {
            JDBCUtil.rollback(con);
            log__.error("有効期限切れのトークン削除に失敗", e);
            throw e;
        } finally {
            if (success) {
                DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);
            } else {
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }
    }

    @Override
    public void doOneHourBatch(Connection con, IBatchModel param)
            throws Exception {

    }

    @Override
    public void do5mBatch(Connection con, IBatchModel param) throws Exception {

    }

}

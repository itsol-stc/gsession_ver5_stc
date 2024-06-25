package jp.groupsession.v2.enq.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.enq.dao.EnqAnsSubDao;
import jp.groupsession.v2.enq.dao.EnqDatausedSumDao;
import jp.groupsession.v2.enq.dao.EnqDescBinDao;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.enq.dao.EnqQueMainDao;
import jp.groupsession.v2.enq.model.EnqDatausedSumModel;

/**
 * <br>[機  能] メイン 各種ログデータの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public EnqUsedDataBiz(Connection con) {
        con__ = con;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] アンケート情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSidList アンケートSID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertEnqDataSize(List<Long> emnSidList, boolean entryType)
    throws SQLException {

        if (emnSidList == null || emnSidList.isEmpty()) {
            return;
        }

        //アンケート基本情報のサイズを取得
        EnqMainDao enqMainDao = new EnqMainDao(con__);
        long enqDataSize = enqMainDao.getTotalDescSize(emnSidList);

        //アンケート基本情報の添付ファイルサイズを取得
        enqDataSize += enqMainDao.getTotalFileSize(emnSidList);

        //設問_基本情報のサイズを取得
        EnqQueMainDao queMainDao = new EnqQueMainDao(con__);
        enqDataSize += queMainDao.getTotalDescSize(emnSidList);

        //設問_基本情報の添付ファイルサイズを取得
        enqDataSize += queMainDao.getTotalFileSize(emnSidList);

        //回答_サブ情報のサイズを取得
        EnqAnsSubDao ansSubDao = new EnqAnsSubDao(con__);
        enqDataSize += ansSubDao.getTotalDescSize(emnSidList);

        //アンケート説明添付情報の添付ファイルサイズを取得
        EnqDescBinDao descBinDao = new EnqDescBinDao(con__);
        enqDataSize += descBinDao.getTotalFileSize(emnSidList);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            enqDataSize *= -1;
        }

        //使用データサイズを登録
        EnqDatausedSumModel dataUsedMdl = new EnqDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setEnqDataSize(enqDataSize);
        EnqDatausedSumDao dataUsedDao = new EnqDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}

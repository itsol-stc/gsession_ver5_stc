package jp.groupsession.v2.rsv.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.rsv.dao.RsvBinDao;
import jp.groupsession.v2.rsv.dao.RsvDatausedSumDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.model.RsvDatausedSumModel;

/**
 * <br>[機  能] 施設予約プラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvUsedDataBiz {


    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public RsvUsedDataBiz(Connection con) {
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
     * <br>[機  能] 施設情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSid 施設SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertRsvDataSize(int rsdSid, boolean entryType)
    throws SQLException {
        if (rsdSid <= 0) {
            return;
        }

        insertRsvDataSize(Arrays.asList(rsdSid), entryType);
    }

    /**
     * <br>[機  能] 施設情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSidList 施設SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertRsvDataSize(List<Integer> rsdSidList, boolean entryType)
    throws SQLException {

        if (rsdSidList == null || rsdSidList.isEmpty()) {
            return;
        }

        //施設情報のサイズを取得
        RsvSisDataDao rsvSisDataDao = new RsvSisDataDao(con__);
        long rsvDataSize = rsvSisDataDao.getDataSize(rsdSidList);

        //施設情報の添付ファイルサイズを取得
        RsvBinDao rsvBinDao = new RsvBinDao(con__);
        rsvDataSize += rsvBinDao.getTotalFileSize(rsdSidList);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            rsvDataSize *= -1;
        }

        //使用データサイズを登録
        RsvDatausedSumModel dataUsedMdl = new RsvDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setRsvSyrkSumSize(0);
        dataUsedMdl.setRsvDataSumSize(rsvDataSize);
        RsvDatausedSumDao dataUsedDao = new RsvDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}

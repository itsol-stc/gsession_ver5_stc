package jp.groupsession.v2.cir.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cir.dao.CirBinDao;
import jp.groupsession.v2.cir.dao.CirDatausedSumDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirUserBinDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.model.CirDatausedSumModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] 回覧板プラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public CirUsedDataBiz(Connection con) {
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
     * <br>[機  能] 回覧板情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSid 回覧板SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertCirDataSize(int cifSid, boolean entryType)
    throws SQLException {
        insertCirDataSize(Arrays.asList(cifSid), entryType);
    }

    /**
     * <br>[機  能] 回覧板情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSidList 回覧板SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertCirDataSize(List<Integer> cifSidList, boolean entryType)
    throws SQLException {

        if (cifSidList == null || cifSidList.isEmpty()) {
            return;
        }

        //回覧板情報のサイズを取得
        CirInfDao cirDao = new CirInfDao(con__);
        long cirDataSize = cirDao.getTotalDataSize(cifSidList);

        //回覧板情報の添付ファイルサイズを取得
        CirBinDao cirBinDao = new CirBinDao(con__);
        cirDataSize += cirBinDao.getTotalFileSize(cifSidList);

        //回覧先情報のサイズを取得
        CirViewDao viewDao = new CirViewDao(con__);
        cirDataSize += viewDao.getTotalDataSize(cifSidList);

        //回覧先情報の添付ファイルサイズを取得
        CirUserBinDao cirUserBinDao = new CirUserBinDao(con__);
        cirDataSize += cirUserBinDao.getTotalFileSize(cifSidList);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            cirDataSize *= -1;
        }

        //使用データサイズを登録
        CirDatausedSumModel dataUsedMdl = new CirDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setCirDataSize(cirDataSize);
        CirDatausedSumDao dataUsedDao = new CirDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}

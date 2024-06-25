package jp.groupsession.v2.ip;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ip.dao.IpkBinDao;
import jp.groupsession.v2.ip.dao.IpkDatausedSumDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.model.IpkDatausedSumModel;

/**
 * <br>[機  能] IP管理プラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public IpkUsedDataBiz(Connection con) {
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
     * <br>[機  能] ネットワーク情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param intSid ネットワークSID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertNetworkSize(int intSid, boolean entryType)
    throws SQLException {

        if (intSid <= 0) {
            return;
        }

        //ネットワーク情報のサイズを取得
        IpkNetDao ipkNetDao = new IpkNetDao(con__);
        long networkSize = ipkNetDao.getNetworkDataSize(intSid);

        //ネットワーク情報の添付ファイルサイズを取得
        List<Integer> intSidList = Arrays.asList(new Integer[] {intSid});
        IpkBinDao ipkBinDao = new IpkBinDao(con__);
        networkSize += ipkBinDao.getTotalNetworkFileSize(intSidList);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            networkSize *= -1;
        }

        //使用データサイズを登録
        IpkDatausedSumModel dataUsedMdl = new IpkDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setIpkDataSize(networkSize);
        IpkDatausedSumDao dataUsedDao = new IpkDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}

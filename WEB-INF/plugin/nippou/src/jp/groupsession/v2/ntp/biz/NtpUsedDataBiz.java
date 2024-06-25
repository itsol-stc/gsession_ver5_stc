package jp.groupsession.v2.ntp.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.dao.NtpBinDao;
import jp.groupsession.v2.ntp.dao.NtpCommentDao;
import jp.groupsession.v2.ntp.dao.NtpDataDao;
import jp.groupsession.v2.ntp.dao.NtpDatausedSumDao;
import jp.groupsession.v2.ntp.model.NtpDatausedSumModel;

/**
 * <br>[機  能] 日報プラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public NtpUsedDataBiz(Connection con) {
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
     * <br>[機  能] 日報情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSid 日報SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertNtpDataSize(int nipSid, boolean entryType)
    throws SQLException {
        insertNtpDataSize(Arrays.asList(nipSid), entryType);
    }

    /**
     * <br>[機  能] 日報情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSidList 日報SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertNtpDataSize(List<Integer> nipSidList, boolean entryType)
    throws SQLException {

        if (nipSidList == null || nipSidList.isEmpty()) {
            return;
        }

        //日報情報のサイズを取得
        NtpDataDao ndDao = new NtpDataDao(con__);
        long ntpDataSize = ndDao.getDiskSize(nipSidList);

        NtpBinDao binDao = new NtpBinDao(con__);
        NtpCommentDao commentDao = new NtpCommentDao(con__);

        int fromIdx = 0;
        List<Integer> subList = new ArrayList<Integer>();
        for (int idx = 0; idx < nipSidList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == nipSidList.size()) {
                subList = nipSidList.subList(fromIdx, idx + 1);

                //日報情報の添付ファイルサイズを取得
                ntpDataSize += binDao.getTotalNtpFileSize(subList);
                
                //日報コメント情報のサイズを取得
                ntpDataSize += commentDao.getDiskSize(subList);

                fromIdx = idx + 1;
            }
        }

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            ntpDataSize *= -1;
        }

        //使用データサイズを登録
        NtpDatausedSumModel dataUsedMdl = new NtpDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setNtpDataSize(ntpDataSize);
        NtpDatausedSumDao dataUsedDao = new NtpDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }

    /**
     * <br>[機  能] 日報コメント情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param npcSid 日報コメントSID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertNtpCommentSize(int npcSid, boolean entryType)
    throws SQLException {

        if (npcSid <= 0) {
            return;
        }

        //日報コメント情報のサイズを取得
        NtpCommentDao ncDao = new NtpCommentDao(con__);
        long ntpCommentSize = ncDao.getDiskSize(npcSid);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            ntpCommentSize *= -1;
        }

        //使用データサイズを登録
        NtpDatausedSumModel dataUsedMdl = new NtpDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setNtpDataSize(ntpCommentSize);
        NtpDatausedSumDao dataUsedDao = new NtpDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}

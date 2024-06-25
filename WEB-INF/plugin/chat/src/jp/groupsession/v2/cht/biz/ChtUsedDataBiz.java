package jp.groupsession.v2.cht.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cht.dao.ChtDatausedSumDao;
import jp.groupsession.v2.cht.dao.ChtGroupDataDao;
import jp.groupsession.v2.cht.dao.ChtUserDataDao;
import jp.groupsession.v2.cht.model.ChtDatausedSumModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] チャットプラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtUsedDataBiz {

    /** 登録対象 チャットグループ投稿情報 */
    public static final int TYPE_GROUP = 0;
    /** 登録対象 チャットユーザ投稿情報 */
    public static final int TYPE_USER = 1;

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public ChtUsedDataBiz(Connection con) {
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
     * <br>[機  能] チャット投稿情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param chatSid 投稿SID
     * @param type 登録対象
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertChtDataSize(long chatSid, int type, boolean entryType)
    throws SQLException {
        if (chatSid <= 0) {
            return;
        }

        insertChtDataSize(Arrays.asList(chatSid), type, entryType);
    }

    /**
     * <br>[機  能] チャット投稿情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param chatSidList 投稿SID
     * @param type 登録対象
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertChtDataSize(List<Long> chatSidList, int type, boolean entryType)
    throws SQLException {

        if (chatSidList == null || chatSidList.isEmpty()) {
            return;
        }

        long chatDataSize = 0;

        int fromIdx = 0;
        List<Long> subList = new ArrayList<Long>();
        for (int idx = 0; idx < chatSidList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == chatSidList.size()) {
                subList = chatSidList.subList(fromIdx, idx + 1);

                if (type == TYPE_GROUP) {
                    ChtGroupDataDao groupDataDao = new ChtGroupDataDao(con__);
                    //チャットグループ投稿情報
                    chatDataSize += groupDataDao.getDiskSize(subList);
                    //添付ファイルサイズ取得
                    chatDataSize += groupDataDao.getTotalFileSize(subList);

                } else if (type == TYPE_USER) {
                    ChtUserDataDao userDataDao = new ChtUserDataDao(con__);
                    //チャットユーザ投稿情報
                    chatDataSize += userDataDao.getDiskSize(subList);
                    //添付ファイルサイズ取得
                    chatDataSize += userDataDao.getTotalFileSize(subList);
                }

                fromIdx = idx + 1;
            }
        }

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            chatDataSize *= -1;
        }

        //使用データサイズを登録
        ChtDatausedSumModel dataUsedMdl = new ChtDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setChtDiskSize(chatDataSize);
        ChtDatausedSumDao dataUsedDao = new ChtDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}

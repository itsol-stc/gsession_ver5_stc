package jp.groupsession.v2.prj;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.prj.dao.PrjDatausedSumDao;
import jp.groupsession.v2.prj.dao.PrjTodoBinDao;
import jp.groupsession.v2.prj.dao.PrjTodocommentDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.model.PrjDatausedSumModel;

/**
 * <br>[機  能] プロジェクトプラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public PrjUsedDataBiz(Connection con) {
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
     * <br>[機  能] TODO情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptdSidList TODO情報SIDリスト
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertTodoSize(List<Integer> ptdSidList, boolean entryType)
    throws SQLException {

        if (ptdSidList == null || ptdSidList.isEmpty()) {
            return;
        }

        long todoSize = 0;

        PrjTododataDao todoDao = new PrjTododataDao(con__);
        PrjTodoBinDao todoBinDao = new PrjTodoBinDao(con__);
        PrjTodocommentDao todoCmtDao = new PrjTodocommentDao(con__);

        int fromIdx = 0;
        List<Integer> subList = null;
        for (int idx = 0; idx < ptdSidList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == ptdSidList.size()) {
                subList = ptdSidList.subList(fromIdx, idx + 1);

                //指定されたTODO情報のサイズを取得
                todoSize += todoDao.getTodoDataSize(subList);

                //TODO情報の添付ファイルサイズを取得
                todoSize += todoBinDao.getSumFileSize(subList);

                //TODO情報に関連するTODOコメント情報のサイズを取得する
                todoSize += todoCmtDao.getCmtDataSizeInTodo(subList);

                fromIdx = idx + 1;
            }
        }

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            todoSize *= -1;
        }

        //使用データサイズを登録
        __insertDatausedSum(todoSize);
    }

    /**
     * <br>[機  能] TODOコメント情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptdSid TODO情報SID
     * @param pcmSidList TODOコメントSIDリスト
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertTodoCommentSize(int ptdSid, List<Integer> pcmSidList, boolean entryType)
    throws SQLException {

        if (pcmSidList == null || pcmSidList.isEmpty()) {
            return;
        }

        PrjTodocommentDao todoCmtDao = new PrjTodocommentDao(con__);
        long todoCommentSize = todoCmtDao.getCmtDataSize(ptdSid, pcmSidList);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            todoCommentSize *= -1;
        }

        //使用データサイズを登録
        __insertDatausedSum(todoCommentSize);
    }

    /**
     * <br>[機  能] 使用データサイズ集計データの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSize TODO情報のサイズ
     * @throws SQLException SQL実行例外
     */
    private void __insertDatausedSum(long todoSize)
    throws SQLException {

        //使用データサイズを登録
        PrjDatausedSumModel dataUsedMdl = new PrjDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setPrjTodoSize(todoSize);
        PrjDatausedSumDao dataUsedDao = new PrjDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}

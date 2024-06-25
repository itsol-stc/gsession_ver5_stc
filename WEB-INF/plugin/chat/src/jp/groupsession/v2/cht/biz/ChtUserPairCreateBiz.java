package jp.groupsession.v2.cht.biz;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.dao.ChtUserDataSumDao;
import jp.groupsession.v2.cht.dao.ChtUserPairDao;
import jp.groupsession.v2.cht.model.ChtUserDataSumModel;
import jp.groupsession.v2.cht.model.ChtUserPairModel;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 *
 * <br>[機  能] ChtUserPairの新規作成ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtUserPairCreateBiz {
    /** 同期キー*/
    private static final Object LOCK__ = new Object();

    /**
     *
     * コンストラクタ
     */
    public ChtUserPairCreateBiz() {
        super();
    }


    /**
     *
     * <br>[機  能] ユーザSIDのペアから存在するChtUserPairのCUP_SIDを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid1 ペアを組むユーザSID１
     * @param usrSid2 ペアを組むユーザSID２
     * @return ChtUserPairのCUP_SID 存在しなければ 0
     * @throws SQLException SQL実行時例外
     */
    private int __getCupSidIfExists(Connection con, int usrSid1, int usrSid2) throws SQLException {
        int nPair = 0;
        ChtUserPairDao cupDao = new ChtUserPairDao(con);
        nPair = cupDao.select(usrSid1, usrSid2);
        return nPair;
    }

    /**
     *
     * <br>[機  能] ユーザSIDのペアからChtUserPairのCUP_SIDを返す
     * <br>[解  説] 存在しなければペア情報を新規作成する
     * <br>[備  考] 新規作成は引数のコネクションとは別に作成し行われる
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param usrSid1 ペアを組むユーザSID１
     * @param usrSid2 ペアを組むユーザSID２
     * @return ChtUserPairのCUP_SID;
     * @throws Exception ペア情報取得に失敗
     */
    public int getCupSidAutoCreate(RequestModel reqMdl, Connection con,
            int usrSid1, int usrSid2) throws Exception {
        int cupSid = __getCupSidIfExists(con, usrSid1, usrSid2);
        if (cupSid != 0) {
            return cupSid;
        }
//        CHT_USER_PAIRテーブルの新規登録部分(下記処理)をsynchronizeブロック化
//         ・ユーザSIDをキーとしたレコード存在チェック
//         ・新規データinsert
        synchronized (LOCK__) {
            cupSid = __getCupSidIfExists(con, usrSid1, usrSid2);
            if (cupSid != 0) {
                return cupSid;
            }
            //ペアは重複登録を防ぐ必要があるため別コネクションを利用する
            Connection insCon = GroupSession.getConnection(reqMdl.getDomain());
            JDBCUtil.autoCommitOff(insCon);
            boolean succsess = false;
            try {
                cupSid = __createPair(reqMdl, insCon, usrSid1, usrSid2);
                insCon.commit();
                succsess = true;
            } catch (Exception e) {
                throw e;
            } finally {
                if (!succsess) {
                    JDBCUtil.rollback(insCon);
                }
                JDBCUtil.closeConnection(insCon);
            }
        }
        return cupSid;
    }

    /**
     *
     * <br>[機  能] ペア情報を新規作成
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param usrSid1 ペアを組むユーザSID１
     * @param usrSid2 ペアを組むユーザSID２
     * @return 新規作成されたペアSID
     * @throws Exception ペア情報作成に失敗
     */
    private int __createPair(RequestModel reqMdl, Connection con, int usrSid1,
            int usrSid2) throws Exception {
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        MlCountMtController cntCon
        = GroupSession.getResourceManager().getCountController(reqMdl);
        int nPair = (int) cntCon.getSaibanNumber("chat",
                GSConstChat.SBSID_SUB_CHAT_PAIR, sessionUsrSid);
        ChtUserPairDao cupDao = new ChtUserPairDao(con);
        ChtUserPairModel cupMdl = new ChtUserPairModel();

        cupMdl.setCupSid(nPair);
        cupMdl.setCupUidF(usrSid1);
        cupMdl.setCupUidS(usrSid2);
        cupMdl.setCupCompFlg(GSConstChat.CHAT_ARCHIVE_NOT_MODE);
        cupDao.insert(cupMdl);

        ChtUserDataSumModel cusMdl = new ChtUserDataSumModel();
        cusMdl.setCupSid(nPair);
        ChtUserDataSumDao cusDao = new ChtUserDataSumDao(con);
        cusDao.insert(cusMdl);
        return nPair;
    }


}

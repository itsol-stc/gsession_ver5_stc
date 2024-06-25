package jp.groupsession.v2.zsk.biz;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.zsk.dao.ZaiDatausedSumDao;
import jp.groupsession.v2.zsk.dao.ZaiIndexDao;
import jp.groupsession.v2.zsk.dao.ZaiInfoDao;
import jp.groupsession.v2.zsk.model.ZaiDatausedSumModel;

/**
 * <br>[機  能] 在席管理プラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public ZskUsedDataBiz(Connection con) {
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
     * <br>[機  能] 座席表の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param zifSid 座席表SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertZasekiSize(int zifSid, boolean entryType)
    throws SQLException {
        if (zifSid <= 0) {
            return;
        }

        //座席表情報サイズを取得
        ZaiInfoDao infoDao = new ZaiInfoDao(con__);
        long zasekiSize = infoDao.getDataSize(zifSid);

        //座席表情報の添付ファイルサイズを取得
        zasekiSize += infoDao.getSumFileSize(zifSid);

        //座席表項目配置情報のサイズを取得
        ZaiIndexDao indexDao = new ZaiIndexDao(con__);
        zasekiSize += indexDao.getZaiIndexDataSize(zifSid);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            zasekiSize *= -1;
        }

        //使用データサイズを登録
        ZaiDatausedSumModel dataUsedMdl = new ZaiDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setZaiDataSize(zasekiSize);
        ZaiDatausedSumDao dataUsedDao = new ZaiDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}

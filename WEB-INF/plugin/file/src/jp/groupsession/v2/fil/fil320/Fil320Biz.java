package jp.groupsession.v2.fil.fil320;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.model.FileMoneyMasterModel;

/**
 * <br>[機  能] ファイル管理 外貨登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil320Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil320Biz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil320Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 外貨の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    protected void _doInsert(
            Fil320ParamModel paramMdl,
            MlCountMtController cntCon) throws SQLException {

        boolean commitFlg = false;

        try {
            //外貨SID採番
            int gaikaSaiSid = (int) cntCon.getSaibanNumber(
                    GSConstFile.PLUGIN_ID_FILE,
                    GSConstFile.SBNSID_SUB_MONEY,
                    -1);

            FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con__);
            int maxNum = fmmDao.getMaxSort();

            //外貨登録Model
            FileMoneyMasterModel fmmMdl  = new FileMoneyMasterModel();
            fmmMdl.setFmmSid(gaikaSaiSid);
            fmmMdl.setFmmName(paramMdl.getFil320GaikaName());
            fmmMdl.setFmmSort(maxNum + 1);

            //外貨を登録する
            fmmDao.insert(fmmMdl);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("新規登録失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }
}
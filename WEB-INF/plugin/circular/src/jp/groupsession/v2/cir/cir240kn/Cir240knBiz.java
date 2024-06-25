package jp.groupsession.v2.cir.cir240kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirLabelDao;
import jp.groupsession.v2.cir.model.CirLabelModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] ラベル登録編集確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir240knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir240knBiz.class);
    /** コネクション */
    private Connection con__ = null;
    /**
     * コンストラクタ
     * @param con コネクション
     */
    public Cir240knBiz(Connection con) {
        con__ = con;
    }


    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Cir240knParamModel paramMdl) throws SQLException {
        //アカウント名取得
        int accountSid = paramMdl.getCirAccountSid();
        CirAccountDao acDao = new CirAccountDao(con__);
        String accountName =  acDao.getCirAccountName(accountSid);
        paramMdl.setCir230accountName(accountName);
    }

    /**
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void doAddEdit(
        Cir240knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException, IOToolsException {
        //登録
        if (paramMdl.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_ADD) {
            log__.debug("登録");
            doInsert(paramMdl, userSid, cntCon);

        //編集
        } else if (paramMdl.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_EDIT) {
            log__.debug("編集");
            doUpdate(paramMdl, userSid);
        }
    }

    /**
     * <br>[機  能] 新規登録画面処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    public void doInsert(
        Cir240knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException {
        boolean commitFlg = false;
        try {
            con__.setAutoCommit(false);
            //ラベルSID採番
            int claSaiSid = (int) cntCon.getSaibanNumber(
                    GSConstCircular.SBNSID_CIRCULAR,
                    GSConstCircular.SBNSID_SUB_LABEL,
                    userSid);
            //ラベル登録Model
            CirLabelModel cirMdl  = __getLabelInsertMdl(paramMdl, userSid, claSaiSid);
            //ラベルを登録する
            CirLabelDao cirDao = new CirLabelDao(con__);
            cirDao.insert(cirMdl);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
        throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 編集画面登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doUpdate(
        Cir240knParamModel paramMdl,
        int userSid) throws SQLException {
        boolean commitFlg = false;
        try {
            con__.setAutoCommit(false);
            //ラベル更新Model
            CirLabelModel cirMdl  = __getLabelUpdateMdl(paramMdl, userSid);
            //ラベルを更新する
            CirLabelDao cirDao = new CirLabelDao(con__);
            cirDao.update(cirMdl);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 新規登録用のラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @param claSaiSid 採番SID
     * @return CirLabelModel
     * @throws SQLException SQL実行例外
     */
    private CirLabelModel __getLabelInsertMdl(
        Cir240knParamModel paramMdl,
        int userSid,
        int claSaiSid) throws SQLException {
        UDate now = new UDate();
        CirLabelModel cirMdl = new CirLabelModel();
        CirLabelDao cirDao = new CirLabelDao(con__);
        cirMdl.setClaSid(claSaiSid);
        cirMdl.setCacSid(paramMdl.getCirAccountSid());
        cirMdl.setClaName(paramMdl.getCir240LabelName());
        cirMdl.setClaOrder(cirDao.maxSortNumber(cirMdl.getCacSid()) + 1);
        cirMdl.setClaAuid(userSid);
        cirMdl.setClaAdate(now);
        cirMdl.setClaEuid(userSid);
        cirMdl.setClaEdate(now);
        return cirMdl;
    }

    /**
     * <br>[機  能] 更新用のラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @return CirLabelModel
     * @throws SQLException SQL実行例外
     */
    private CirLabelModel __getLabelUpdateMdl(
        Cir240knParamModel paramMdl,
        int userSid) throws SQLException {
        UDate now = new UDate();
        CirLabelModel cirMdl = new CirLabelModel();
        cirMdl.setClaSid(paramMdl.getCir230EditLabelId());
        cirMdl.setCacSid(paramMdl.getCirAccountSid());
        cirMdl.setClaName(paramMdl.getCir240LabelName());
        cirMdl.setClaEuid(userSid);
        cirMdl.setClaEdate(now);
        return cirMdl;
    }

}

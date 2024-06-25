package jp.groupsession.v2.mem.mem060kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.dao.MemoLabelDao;
import jp.groupsession.v2.mem.model.MemoLabelModel;

/**
 * <br>[機  能] メモ帳 個人設定 ラベル登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem060knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Mem060knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Mem060knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Mem060knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] ラベルの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @return 存在チェック判定
     * @throws SQLException SQL実行時例外
     */
    protected boolean _existLabelData(
            Connection con,
            Mem060knParamModel paramMdl,
            int usrSid) throws SQLException {

        //編集モードチェック
        if  (paramMdl.getMemLabelCmdMode() == GSConstMemo.CMDMODE_EDIT) {
            MemoLabelDao mlDao = new MemoLabelDao(con);
            int mmlSid = paramMdl.getMemEditLabelId();
            boolean result = mlDao.existLabel(mmlSid, usrSid);

            return result;
        }
        return true;
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
    protected void _doInsert(
            Mem060knParamModel paramMdl,
            int userSid,
            MlCountMtController cntCon) throws SQLException {

        boolean commitFlg = false;

        try {
            //ラベルSID採番
            int lbSaiSid = (int) cntCon.getSaibanNumber(
                    GSConstMemo.SBN_SID,
                    GSConstMemo.SBN_SID_SUB,
                    userSid);

            //ラベル登録Model
            MemoLabelModel mlMdl  = __getLabelMdl(paramMdl, userSid, lbSaiSid);
            //ラベルを登録する
            MemoLabelDao mlDao = new MemoLabelDao(con__);
            mlDao.insert(mlMdl);
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

    /**
     * <br>[機  能] 編集画面登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    protected void _doUpdate(
            Mem060knParamModel paramMdl,
            int userSid) throws SQLException {

        boolean commitFlg = false;

        try {
            //ラベル更新Model
            MemoLabelModel mlMdl  = __getLabelMdl(paramMdl, userSid, paramMdl.getMemEditLabelId());
            //ラベルを更新する
            MemoLabelDao mldao = new MemoLabelDao(con__);
            mldao.updateLabel(mlMdl);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("編集失敗", e);
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
     * <br>[機  能] 新規登録または更新用のMemoLabelModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @param mmlSid メモラベルSID
     * @return MemoLabelModel
     * @throws SQLException SQL実行例外
     */
    private MemoLabelModel __getLabelMdl(
            Mem060knParamModel paramMdl,
            int userSid,
            int mmlSid) throws SQLException {

        MemoLabelModel mlMdl = new MemoLabelModel();
        MemoLabelDao mlDao = new MemoLabelDao(con__);
        UDate now = new UDate();

        if (paramMdl.getMemLabelCmdMode() == GSConstMemo.CMDMODE_ADD) {
            //並び順
            mlMdl.setMmlSort(mlDao.getMaxSort(userSid) + 1);
            mlMdl.setMmlAuid(userSid);
            mlMdl.setMmlAdate(now);
        } 
        mlMdl.setMmlSid(mmlSid);
        mlMdl.setMmlName(paramMdl.getMem060LabelName());
        mlMdl.setUsrSid(userSid);
        mlMdl.setMmlEuid(userSid);
        mlMdl.setMmlEdate(now);

        return mlMdl;
    }
}
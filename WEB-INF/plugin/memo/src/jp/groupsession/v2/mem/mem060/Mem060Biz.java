package jp.groupsession.v2.mem.mem060;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.dao.MemoLabelDao;
import jp.groupsession.v2.mem.model.MemoLabelModel;

/**
 * <br>[機  能]メモ帳 個人設定 ラベル登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem060Biz {

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
            Mem060ParamModel paramMdl,
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
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(Connection con, Mem060ParamModel paramMdl)
            throws SQLException {

        MemoLabelDao mlDao = new MemoLabelDao(con);
        int mmlSid = paramMdl.getMemEditLabelId();

        //初期表示 編集
        if (paramMdl.getMem060initKbn() == GSConstMemo.INIT_FLG
                && paramMdl.getMemLabelCmdMode() == GSConstMemo.CMDMODE_EDIT) {

            //ラベル名設定
            MemoLabelModel mlData = mlDao.select(mmlSid);
            paramMdl.setMem060LabelName(mlData.getMmlName());

            //初期表示完了
            paramMdl.setMem060initKbn(GSConstMemo.NOT_INIT_FLG);
        }
    }
}
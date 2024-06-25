package jp.groupsession.v2.bmk.bmk160;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.bmk.dao.BmkAconfDao;
import jp.groupsession.v2.bmk.model.BmkAconfModel;

/**
 * <br>[機  能] 管理者設定 セキュリティ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk160Biz {
    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Bmk160ParamModel paramMdl,
            Connection con) throws SQLException {

        if (paramMdl.getBmk160initFlg() != 1) {
            BmkAconfDao dao = new BmkAconfDao(con);
            BmkAconfModel model = dao.selectAConf();

            if (model != null) {
                paramMdl.setBmk160LimitKbn(model.getBacLimit());
            }

            paramMdl.setBmk160initFlg(1);
        }
    }
}

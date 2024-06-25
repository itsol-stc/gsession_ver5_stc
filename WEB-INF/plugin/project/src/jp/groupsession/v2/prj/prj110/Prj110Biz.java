package jp.groupsession.v2.prj.prj110;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.prj.dao.PrjAdmConfDao;
import jp.groupsession.v2.prj.model.PrjAdmConfModel;

/**
 * <br>[機  能] プロジェクト管理 管理者設定 登録権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj110Biz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Prj110Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj110ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Prj110ParamModel paramMdl) throws SQLException {

        //管理者設定情報を取得する
        PrjAdmConfDao pacDao = new PrjAdmConfDao(con__);
        PrjAdmConfModel pacMdl = pacDao.select();
        paramMdl.setPrj110edit(pacMdl.getPacPrjEdit());
    }

}

package jp.groupsession.v2.ptl.ptl130kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.man.dao.base.PtlAdmConfDao;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;

/**
 * <br>[機  能] ポータル 権限設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl130knBiz {
    /**
     * <br>[機  能] 権限設定をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return int 更新件数
     */
    public int setPtlAconf(Ptl130knParamModel paramMdl, Connection con) throws SQLException {

        //更新件数
        int count = 0;
        PtlAdmConfDao dao = new PtlAdmConfDao(con);
        //画面値セット
        PtlAdmConfModel ptlMdl = createPtlAconfData(paramMdl);

        count = dao.updateEditKbn(ptlMdl);

        //レコードがなければ新規登録
        if (count < 1) {
            dao.insert(ptlMdl);
        }
        return count;
    }


    /**
     * <br>[機  能] 権限設定情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return PtlAdmConfModel 権限設定情報
     */
    public PtlAdmConfModel createPtlAconfData(Ptl130knParamModel paramMdl) {

        PtlAdmConfModel mdl = new PtlAdmConfModel();

        mdl.setPacPtlEditkbn(paramMdl.getPtl130editKbn());
        mdl.setPacDefKbn(0);
        mdl.setPacDefType(0);

        return mdl;
    }

}

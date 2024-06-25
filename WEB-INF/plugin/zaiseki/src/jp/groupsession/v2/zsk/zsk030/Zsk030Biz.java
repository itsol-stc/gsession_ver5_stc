package jp.groupsession.v2.zsk.zsk030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.zsk.dao.ZaiInfoDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 在席管理 座席表設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk030Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk030Biz.class);

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Zsk030ParamModel
     * @param con コネクション
     * @return Sch030Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Zsk030ParamModel getInitData(
            Zsk030ParamModel paramMdl,
            Connection con)
    throws SQLException {
        log__.debug("START_Zsk030Biz.getInitData");

        //表示座席表コンボ
        paramMdl.setZasekiList(__getZasekiList(con));

        return paramMdl;
    }

    /**
     * 在席一覧を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList 在席一覧
     * @throws SQLException SQL実行時エラー
     */
    private ArrayList<Zsk030Model> __getZasekiList(Connection con) throws SQLException {
        ArrayList<Zsk030Model> ret = null;
        ZaiInfoDao infoDao = new ZaiInfoDao(con);
        ret = infoDao.getZsk030ModelList();
        return ret;
    }
}

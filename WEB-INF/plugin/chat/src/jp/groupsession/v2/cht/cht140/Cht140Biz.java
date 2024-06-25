package jp.groupsession.v2.cht.cht140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cht.dao.ChtCategoryDao;
import jp.groupsession.v2.cht.model.ChtCategoryModel;

/**
 *
 * <br>[機  能] チャット カテゴリ管理のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht140Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht140Biz.class);
    /** コネクション */
    private Connection con__ = null;

    /**コンストラクタ
     * @param con コネクション
     * */
    public Cht140Biz(Connection con) {
        con__ = con;
    }

    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行例外
     *
     * */
    public void setInitData(Cht140ParamModel paramMdl)
            throws SQLException {
        log__.debug("init");
        ChtCategoryDao dao = new ChtCategoryDao(con__);
        List<ChtCategoryModel> categoryList = dao.selectCategory();
        paramMdl.setCht140CategoryList(categoryList);
    }

}

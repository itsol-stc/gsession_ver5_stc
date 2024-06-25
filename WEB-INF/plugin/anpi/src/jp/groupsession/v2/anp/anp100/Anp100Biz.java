package jp.groupsession.v2.anp.anp100;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.anp.dao.AnpDatausedSumDao;
import jp.groupsession.v2.anp.dao.AnpMtempDao;
import jp.groupsession.v2.anp.model.AnpMtempModel;


/**
 * <br>[機  能] 管理者設定・メールテンプレート編集画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp100Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp100Biz.class);

    /**
     * <br>[機  能] テンプレート情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param anp100Model パラメータモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setTempData(Anp100ParamModel anp100Model, Connection con)
                throws Exception {
        String mSid = anp100Model.getAnp090SelectSid();

        if (StringUtil.isNullZeroStringSpace(mSid)) {
            log__.debug("新規作成");
            return;
        }

        log__.debug("修正 SID = " + mSid);

        AnpMtempDao dao = new AnpMtempDao(con);
        AnpMtempModel bean = dao.select(Integer.valueOf(mSid));
        if (bean == null) {
            anp100Model.setAnp090SelectSid("");
            return;
        }

        anp100Model.setAnp100Title(bean.getApmTitle());
        anp100Model.setAnp100Subject(bean.getApmSubject());
        anp100Model.setAnp100Text1(bean.getApmText1());
        anp100Model.setAnp100Text2(bean.getApmText2());
    }

    /**
     * <br>[機  能] 削除処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param anp100Model パラメータモデル
     * @param con DBコネクション
     * @throws SQLException SQL実行例外
     */
    public void doDelete(Anp100ParamModel anp100Model, Connection con)
                throws SQLException {

        String mSid = anp100Model.getAnp090SelectSid();
        AnpDatausedSumDao adsDao = new AnpDatausedSumDao(con);
        adsDao.insertMailDelDiff(Arrays.asList(Integer.valueOf(mSid)));
        AnpMtempDao dao = new AnpMtempDao(con);
        dao.delete(Integer.valueOf(mSid));
    }
}
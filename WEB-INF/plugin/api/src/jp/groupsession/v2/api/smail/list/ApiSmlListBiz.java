package jp.groupsession.v2.api.smail.list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.model.SmailModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 受信ショートメールリストを取得するWEBAPIビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlListBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlListBiz.class);

    /**
     * <br>[機  能] 受信ショートメールデータを取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param sacSid アカウントSID
     * @param mode モード
     * @return 受信ショートメールリスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<SmailModel> getSmailJusinList(Connection con, int sacSid, int mode)
        throws SQLException {

        log__.debug("初期表示データセット");

        SmailDao sDao = new SmailDao(con);

        //一覧最大取得件数
        int limit = GSConstApi.SMAIL_MAX_RECORD_COUNT;
        int start = 0;

       //受信ショートメール一覧を取得する

        ArrayList<SmailModel> resultList = null;

        //該当データ取得(受信モード)
        resultList =
            sDao.selectJmeisListPlusBody(
                sacSid,
                start,
                limit,
                GSConstSmail.MSG_SORT_KEY_DATE,
                GSConstSmail.ORDER_KEY_DESC,
                mode);

        return resultList;
    }
}
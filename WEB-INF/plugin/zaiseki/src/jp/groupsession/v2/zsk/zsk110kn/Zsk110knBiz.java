package jp.groupsession.v2.zsk.zsk110kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.zsk.dao.ZaiFixUpdateDao;
import jp.groupsession.v2.zsk.model.ZaiFixUpdateModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 在席管理 管理者設定 定時一括更新確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk110knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk110knBiz.class);

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk110knParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Zsk110knParamModel paramMdl, Connection con)
            throws SQLException {

        log__.debug("setInitData START");

    }

    /**
     * <br>[機  能] 登録処理を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk110knParamModel
     * @param con コネクション
     * @param sessionUserSid ユーザSID
     * @throws SQLException SQL実行エラー
     */
    public void dataSetting(Zsk110knParamModel paramMdl, Connection con, int sessionUserSid)
            throws SQLException {

        UDate now = new UDate();
        //更新用モデル作成する。
        ZaiFixUpdateModel model = new ZaiFixUpdateModel();
        model.setZfuUpdateKbn(paramMdl.getZsk110UpdateKbn());
        model.setZfuFixUpdateTime(paramMdl.getZsk110StartTime());
        model.setZfuStatus(paramMdl.getZsk110Status());
        model.setZfuMsg(paramMdl.getZsk110Msg());
        model.setZfuAuid(sessionUserSid);
        model.setZfuAdate(now);
        model.setZfuEuid(sessionUserSid);
        model.setZfuEdate(now);

        updateData(con, model);
    }

    /**
     * <br>[機  能] DB更新処理を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param model ZaiFixUpdateModel
     * @throws SQLException SQL実行エラー
     */
    public void updateData(Connection con, ZaiFixUpdateModel model)
            throws SQLException {

        ZaiFixUpdateDao dao = new ZaiFixUpdateDao(con);
        boolean commitFlg = false;
        try {
            dao.delete();
            dao.insert(model);

            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
}

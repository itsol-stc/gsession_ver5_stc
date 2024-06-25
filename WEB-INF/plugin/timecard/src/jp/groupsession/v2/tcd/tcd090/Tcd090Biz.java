package jp.groupsession.v2.tcd.tcd090;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;


/**
 * <br>[機  能] タイムカード 管理者設定 編集権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd090Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd090Biz.class);

    /**
     * <br>[機  能] 初期表示画面情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Tcd090ParamModel paramMdl, RequestModel reqMdl, Connection con)
    throws SQLException {

        log__.debug("setInitData START");
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //タイムカード管理者設定を取得
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConfMdl = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);

        paramMdl.setTcd090LockFlg(String.valueOf(admConfMdl.getTacLockFlg()));
        paramMdl.setTcd090LockStrike(String.valueOf(admConfMdl.getTacLockStrike()));
        paramMdl.setTcd090LockBiko(String.valueOf(admConfMdl.getTacLockBiko()));
        paramMdl.setTcd090LockLate(String.valueOf(admConfMdl.getTacLockLate()));
        paramMdl.setTcd090LockHoliday(String.valueOf(admConfMdl.getTacLockHoliday()));
        paramMdl.setTcd090LockTimezone(String.valueOf(admConfMdl.getTacLockTimezone()));
    }
}

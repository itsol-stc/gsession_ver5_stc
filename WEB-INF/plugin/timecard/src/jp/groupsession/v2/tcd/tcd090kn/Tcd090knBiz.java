package jp.groupsession.v2.tcd.tcd090kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;


/**
 * <br>[機  能] タイムカード 管理者設定 編集権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd090knBiz {
    /**
     * <br>[機  能] タイムカード編集権限設定を更新します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateTcdAdmConf(Tcd090knParamModel paramMdl, int usrSid, Connection con)
    throws SQLException {

        TcdAdmConfModel admMdl = new TcdAdmConfModel();

        //更新内容を設定
        admMdl.setTacLockFlg(
                NullDefault.getInt(paramMdl.getTcd090LockFlg(), GSConstTimecard.UNLOCK_FLG));
        admMdl.setTacLockStrike(
                NullDefault.getInt(paramMdl.getTcd090LockStrike(), GSConstTimecard.UNLOCK_FLG));
        admMdl.setTacLockBiko(
                NullDefault.getInt(paramMdl.getTcd090LockBiko(), GSConstTimecard.UNLOCK_FLG));
        admMdl.setTacLockLate(
                NullDefault.getInt(paramMdl.getTcd090LockLate(), GSConstTimecard.UNLOCK_FLG));
        admMdl.setTacLockHoliday(
                NullDefault.getInt(paramMdl.getTcd090LockHoliday(), GSConstTimecard.UNLOCK_FLG));
        admMdl.setTacLockTimezone(
                NullDefault.getInt(paramMdl.getTcd090LockTimezone(), GSConstTimecard.UNLOCK_FLG));
        admMdl.setTacEuid(usrSid);
        admMdl.setTacEdate(new UDate());

        TcdAdmConfDao dao = new TcdAdmConfDao(con);
        dao.updateLockInf(admMdl);
    }
}

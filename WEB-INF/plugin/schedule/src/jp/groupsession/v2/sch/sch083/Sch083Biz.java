package jp.groupsession.v2.sch.sch083;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;

/**
 * <br>[機  能] スケジュール 手動データ削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch083Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch083Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch083Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] スケジュールデータを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch083ParamModel
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void deleteSchedule(Sch083ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {
        log__.debug("スケジュールデータ 削除開始");

        //削除基準日の作成
        UDate bdate = new UDate();
        int year = paramMdl.getSch083DelYear();
        int month = paramMdl.getSch083DelMonth();
        bdate.addYear(-year);
        bdate.addMonth(-month);

        //削除実行
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        biz.deleteOldSchedule(con, bdate);

        //スケジュール拡張情報を削除する。
        biz.deleteSchNoData(con);

        log__.debug("スケジュールデータ 削除完了");
    }
}

package jp.groupsession.v2.enq.enq800;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] アンケート 個人設定メニュー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq800Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq800Biz.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq800Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq800Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq800ParamModel enq800Model,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報取得処理");


    }


}

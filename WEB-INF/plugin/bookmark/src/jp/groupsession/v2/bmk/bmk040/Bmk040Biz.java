package jp.groupsession.v2.bmk.bmk040;

import java.sql.Connection;
import jp.groupsession.v2.bmk.bmk010.dao.Bmk010Dao;
import jp.groupsession.v2.bmk.bmk030.Bmk030Biz;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ラベル選択画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk040Biz extends Bmk030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk040Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Bmk040Biz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl paramMdl
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(Bmk040ParamModel paramMdl, Connection con, int sessionUserSid)
    throws Exception {
        log__.debug("初期表示情報を設定");

        //ラベル一覧を設定
        Bmk010Dao labelDao = new Bmk010Dao(con, reqMdl__);
        paramMdl.setLabelList(labelDao.selectLabelList(paramMdl.getBmk040mode(),
                                                   sessionUserSid,
                                                   paramMdl.getBmk040groupSid(),
                                                   0));
    }
}

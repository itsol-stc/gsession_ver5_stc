package jp.groupsession.v2.cir.cir190;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板 アカウント基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir190Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir190Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                              Cir190ParamModel paramMdl,
                              RequestModel reqMdl) throws SQLException {
        log__.debug("START");


        //初期表示かどうか
        if (paramMdl.getCir190initFlg() == GSConstCircular.DSP_FIRST) {

            CirCommonBiz cirBiz = new CirCommonBiz();
            CirInitModel cacMdl = new CirInitModel();
            cacMdl = cirBiz.getCirInitConf(0, con);

            //データをセットする
            paramMdl.setCir190acntMakeKbn(cacMdl.getCinAcntMake());
            paramMdl.setCir190autoDelKbn(cacMdl.getCinAutoDelKbn());
            paramMdl.setCir190acntUser(cacMdl.getCinAcntUser());

            paramMdl.setCir190initFlg(GSConstCircular.DSP_ALREADY);
        }

        log__.debug("END");
    }

}

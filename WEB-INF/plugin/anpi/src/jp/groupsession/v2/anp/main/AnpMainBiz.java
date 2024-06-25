package jp.groupsession.v2.anp.main;

import java.sql.Connection;

import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.dao.AnpJdataDao;
import jp.groupsession.v2.anp.dao.AnpiCommonDao;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.anp.model.AnpJdataModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.anp.model.AnpStateModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] メイン画面に表示する安否状況画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AnpMainBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpMainBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anpMainModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(AnpMainParamModel anpMainModel, RequestModel reqMdl, Connection con)
                throws Exception {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //メイン表示フラグを確認
        AnpiCommonBiz anpibiz = new AnpiCommonBiz();
        AnpPriConfModel pConf = anpibiz.getAnpPriConfModel(con, sessionUsrSid);
        if (pConf.getAppMainKbn() == GSConstAnpi.DSP_FLG_NOT) {
            log__.debug("個人設定でメイン非表示");
            return;
        }

        //配信中の安否データを取得
        AnpHdataDao hDao = new AnpHdataDao(con);
        AnpHdataModel hdata = hDao.selectInHaisin();
        if (hdata == null) {
            log__.debug("配信中データなし");
            return;
        }

        //配信データ.メイン表示 = 送信先ユーザのみ の場合
        //送信先ユーザに含まれているかを判定
        if (hdata.getAphViewMain() == GSConstAnpi.APH_VIEW_MAIN_SENDTO) {
            AnpJdataDao jdataDao = new AnpJdataDao(con);
            AnpJdataModel jdataMdl = jdataDao.select(hdata.getAphSid(), sessionUsrSid);
            if (jdataMdl == null) {
                log__.debug("送信先ユーザに含まれていない");
                return;
            }
        }

        //現在の状況を取得
        AnpiCommonDao anpidao = new AnpiCommonDao(con, reqMdl);
        AnpStateModel state = anpidao.getStateInfo(hdata.getAphSid());

        anpMainModel.setAnpMainAnpiSid(String.valueOf(hdata.getAphSid()));
        anpMainModel.setAnpMainState(state);
    }
}
package jp.groupsession.v2.anp.anp050;

import java.sql.Connection;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 個人設定・連絡先設定ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp050Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param  anp050Model パラメータモデル
     * @param  reqMdl リクエストモデル
     * @param  con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp050ParamModel anp050Model,
            RequestModel reqMdl,
                            Connection con)
                     throws Exception {

        log__.debug("///Anp050Biz * 初期表示データセット///");
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        if (anp050Model.getAnp050UserSid() == 0) {
            //個人設定情報を取得（未登録の場合データを作成）
            AnpPriConfModel pribean = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

            anp050Model.setAnp050UserSid(sessionUsrSid);
            anp050Model.setAnp050Mail(NullDefault.getString(pribean.getAppMailadr(), ""));
            anp050Model.setAnp050TelNo(NullDefault.getString(pribean.getAppTelno(), ""));
        }
    }
}

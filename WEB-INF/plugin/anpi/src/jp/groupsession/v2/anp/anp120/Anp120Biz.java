package jp.groupsession.v2.anp.anp120;

import java.sql.Connection;

import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.anp110.Anp110Biz;
import jp.groupsession.v2.anp.model.AnpPriConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 緊急連絡先編集画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp120Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp110Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp120Model パラメータモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp120ParamModel anp120Model,
                            Connection con)
                            throws Exception {

        log__.debug("///Anp120Biz * 初期表示データ取得///");
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        int sessionUsrSid = anp120Model.getAnp110SelectUserSid();

        AnpPriConfModel pribean = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        anp120Model.setAnp120MailAdr(pribean.getAppMailadr());
        anp120Model.setAnp120TelNo(pribean.getAppTelno());
    }
}

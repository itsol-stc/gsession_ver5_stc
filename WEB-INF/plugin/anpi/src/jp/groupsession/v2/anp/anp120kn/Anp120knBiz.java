package jp.groupsession.v2.anp.anp120kn;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.dao.AnpPriConfDao;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.model.RequestModel;


/**
 * <br>[機  能] 個人設定・連絡先設定確認画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp120knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp120knBiz.class);

    /**
     * <br>[機  能] 連絡先を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param  anp120knModel パラメータモデル
     * @param  con DBコネクション
     * @throws Exception 実行例外
     */
    public void doUpdate(Anp120knParamModel anp120knModel,
            RequestModel reqMdl, Connection con) throws Exception {

        log__.debug("///個人設定更新START///");
        AnpPriConfDao dao = new AnpPriConfDao(con);

        //DBに個人設定が無い場合登録
        AnpiCommonBiz biz = new AnpiCommonBiz();
        biz.getAnpPriConfModel(con, anp120knModel.getAnp110SelectUserSid());
        //更新内容をモデルに設定
        AnpPriConfModel pribean = __setUpdateModel(anp120knModel);
        dao.doUpdateAnp050kn(pribean);

    }

    /**
     * <br>[機  能] データ設定用モデルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param  anp120knModel パラメータモデル
     * @return Anp050knSenderModel 更新用データ
     */
    private AnpPriConfModel __setUpdateModel(Anp120knParamModel anp120knModel) {

        AnpPriConfModel pribean = new AnpPriConfModel();
        pribean.setUsrSid(anp120knModel.getAnp110SelectUserSid());
        pribean.setAppMailadr(anp120knModel.getAnp120MailAdr());
        pribean.setAppTelno(anp120knModel.getAnp120TelNo());
        pribean.setAppEuid(anp120knModel.getAnp110SelectUserSid());

        return pribean;
    }
}

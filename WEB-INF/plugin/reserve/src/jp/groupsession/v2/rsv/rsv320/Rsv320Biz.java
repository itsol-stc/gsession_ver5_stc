package jp.groupsession.v2.rsv.rsv320;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 施設予約 管理者設定 ショートメール通知設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv320Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv320Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv320Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示データセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv320ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Rsv320ParamModel paramMdl) throws SQLException {

        log__.debug("初期表示データセット");
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();

        RsvCommonBiz biz = new RsvCommonBiz();
        RsvAdmConfModel mdl = biz.getRsvAdminData(con_, usrSid);

        paramMdl.setRsv320SmailSendKbn(mdl.getRacSmailSendKbn());
        paramMdl.setRsv320SmailSend(mdl.getRacSmailSend());
    }

    /**
     * <br>[機  能] 通知設定更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv320ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void updateSmailKbn(Rsv320ParamModel paramMdl) throws SQLException {
        log__.debug("START");

        //セッションユーザモデル取得
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();

        RsvCommonBiz biz = new RsvCommonBiz();
        RsvAdmConfModel mdl = biz.getRsvAdminData(con_, usrSid);
        UDate now = new UDate();

        mdl.setRacEuid(usrSid);
        mdl.setRacEdate(now);
        mdl.setRacSmailSendKbn(paramMdl.getRsv320SmailSendKbn());
        mdl.setRacSmailSend(paramMdl.getRsv320SmailSend());
        if (mdl.getRacSmailSendKbn() == GSConstReserve.RAC_SMAIL_SEND_KBN_USER) {
            mdl.setRacSmailSend(GSConstReserve.RAC_SMAIL_SEND_YES);
        }

        //更新
        RsvAdmConfDao dao = new RsvAdmConfDao(con_);
        dao.updateSmailSendConf(mdl);

        log__.debug("End");
    }
}
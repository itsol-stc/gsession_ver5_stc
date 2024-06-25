package jp.groupsession.v2.rsv.rsv300;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 施設予約 ショートメール通知設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv300Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv300Biz.class);
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
    public Rsv300Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示データセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv300ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Rsv300ParamModel paramMdl) throws SQLException {

        log__.debug("初期表示データセット");

        //セッションユーザモデル取得
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);

        int kbn = paramMdl.getRsv300smailKbn();
        if (kbn < 0) {

            //個人設定取得
            RsvUserDao dao = new RsvUserDao(con_);
            RsvUserModel ret = dao.select(usrMdl.getUsrsid());

            if (ret != null) {
                kbn = ret.getRsuSmailKbn();
            } else {
                kbn = GSConstReserve.RSU_SMAIL_SEND_NO;
            }
        }
        paramMdl.setRsv300smailKbn(kbn);
    }

    /**
     * <br>[機  能] 通知設定更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv300ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void updateSmailKbn(Rsv300ParamModel paramMdl) throws SQLException {

        //セッションユーザモデル取得
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();
        UDate now = new UDate();

        RsvUserModel param = new RsvUserModel();
        param.setUsrSid(usrSid);
        param.setRsuSmailKbn(paramMdl.getRsv300smailKbn());
        param.setRsuEuid(usrSid);
        param.setRsuEdate(now);

        RsvUserDao dao = new RsvUserDao(con_);
        if (dao.updateSmailKbn(param) < 1) {
            param.setRsgSid(0);
            param.setRsuDit1(GSConstReserve.KOJN_SETTEI_DSP_OK);
            param.setRsuDit2(GSConstReserve.KOJN_SETTEI_DSP_OK);
            param.setRsuDtmFr(GSConstReserve.YRK_DEFAULT_START_HOUR);
            param.setRsuDtmTo(GSConstReserve.YRK_DEFAULT_END_HOUR);
            param.setRsuReload(GSConstReserve.AUTO_RELOAD_10MIN);
            UDate frDate = new UDate();
            frDate.setZeroHhMmSs();
            frDate.setHour(GSConstReserve.YRK_DEFAULT_START_HOUR);
            frDate.setMinute(GSConstReserve.YRK_DEFAULT_START_MINUTE);
            param.setRsuIniFrDate(frDate);
            UDate toDate = new UDate();
            toDate.setZeroHhMmSs();
            toDate.setHour(GSConstReserve.YRK_DEFAULT_END_HOUR);
            toDate.setMinute(GSConstReserve.YRK_DEFAULT_END_MINUTE);
            param.setRsuIniToDate(toDate);
            param.setRsuIniEdit(GSConstReserve.EDIT_AUTH_NONE);
            param.setRsuAuid(usrSid);
            param.setRsuAdate(now);
            param.setRsuMaxDsp(GSConstReserve.RSV_DEFAULT_DSP);
            dao.insert(param);
        }
    }
}
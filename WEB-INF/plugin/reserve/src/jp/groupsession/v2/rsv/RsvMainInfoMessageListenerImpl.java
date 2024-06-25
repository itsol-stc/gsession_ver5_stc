package jp.groupsession.v2.rsv;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] 施設予約に関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvMainInfoMessageListenerImpl implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvMainInfoMessageListenerImpl.class);

    /** 施設利用状況照会画面URL */
    public static final String RSV_SEARCH_URL = "../reserve/rsv100.do";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvMainInfoMessageListenerImpl() {
    }

    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説] メインへは未開封のメッセージ件数を表示します。
     * <br>未開封のメッセージがない場合は表示しません。
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param gsMsg Gsメッセージ
     * @param reqMdl リクエストモデル
     * @return メッセージのリスト
     */
    public List<MainInfoMessageModel> getMessage(Map<String, Object> paramMap,
                                                int usid, Connection con,
                                         GsMessage gsMsg, RequestModel reqMdl) {
        //承認待ちの施設予約情報件数を取得する
        List<MainInfoMessageModel> msgList = new ArrayList<MainInfoMessageModel>();

        boolean autoCommit = false;
        try {
            try {
                autoCommit = con.getAutoCommit();
                if (!autoCommit) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log__.info("auto commitの設定に失敗", e);
            }

            RsvSisYrkDao sisYrkDao = new RsvSisYrkDao(con);
            int count = sisYrkDao.getNotApprDataCount(usid);
            if (count > 0) {
                MainInfoMessageModel model = new MainInfoMessageModel();
                String textRsv = gsMsg.getMessage("cmn.reserve");
                model.setPluginId(GSConst.PLUGIN_ID_RESERVE);
                model.setPluginName(textRsv);
                String rsvMsg = gsMsg.getMessage("reserve.170",
                        new String[] {Integer.toString(count)});

                StringBuilder msgBuf = new StringBuilder();
                msgBuf.append("[ " + textRsv + " ] ");
                msgBuf.append(rsvMsg);
                model.setMessage(msgBuf.toString());
                model.setOriginalMessage(rsvMsg);

                String url = RSV_SEARCH_URL;
                url += "?rsv100dateKbn=" + AbstractReserveForm.DATEKBN_SELECT;
                url += "&rsv100svDateKbn=" + AbstractReserveForm.DATEKBN_SELECT;
                url += "&rsv100apprStatus=" + GSConstReserve.SRH_APPRSTATUS_APPRONLY;
                url += "&rsv100svApprStatus=" + GSConstReserve.SRH_APPRSTATUS_APPRONLY;
                model.setLinkUrl(url);

                CommonBiz cmnBiz = new CommonBiz();
                model.setIcon(cmnBiz.getPluginIconUrl(GSConstReserve.PLUGIN_ID_RESERVE,
                        reqMdl.getDomain()));

                msgList.add(model);
            }
        } catch (SQLException e) {
            log__.error("承認待ちの施設予約情報件数の取得に失敗", e);
        } finally {
            if (!autoCommit) {
                try {
                    con.setAutoCommit(false);
                } catch (SQLException e) {
                    log__.info("auto commitの設定に失敗", e);
                }
            }
        }

        return msgList;
    }

}

package jp.groupsession.v2.cht;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cht.dao.ChatDao;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションにメッセージを表示するクラス
 * <br>[解  説] チャット未読件数を表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtMainInfoMessage.class);

    /** アンケートメインURL */
    public static final String CHAT_MAIN_URL = "../chat/cht010.do?CMD=fromMain";

    /**
     * <p>デフォルトコンストラクタ
     */
    public ChtMainInfoMessage() {
    }

    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説] メインへは未読件数を表示します。
     * <br>      未読チャットが無い場合は表示しません。
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param gsMsg GSメッセージ
     * @param reqMdl リクエストモデル
     * @return メッセージのリスト
     */
    @Override
    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説]
     * <br>
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param gsMsg Gsメッセージ
     * @param reqMdl リクエストモデル
     * @return メッセージのリスト
     */
    public List<MainInfoMessageModel> getMessage(Map<String, Object> paramMap,
                       int usid, Connection con, GsMessage gsMsg, RequestModel reqMdl) {
        ArrayList<MainInfoMessageModel> msgList = null;
        String linkUrl = CHAT_MAIN_URL;

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

            //未読の件数を取得する。
            ChatDao dao = new ChatDao(con);
            int count = 0;
            try {
                count = dao.getMidokuCount(usid);
            } catch (SQLException e) {
                log__.error("未読メッセージ件数の取得に失敗", e);
            }

            if (count <= 0) {
                return null;
            }
            String chat = "[" + gsMsg.getMessage("cht.01") + "]";
            String msg = gsMsg.getMessage("cht.05", new String[] {String.valueOf(count)});

            //メッセージを作成する。
            MainInfoMessageModel model = new MainInfoMessageModel();
            model.setPluginId(GSConst.PLUGIN_ID_CHAT);
            model.setPluginName(gsMsg.getMessage("cht.01"));
            model.setLinkUrl(linkUrl);
            StringBuilder msgBuf = new StringBuilder();
            msgBuf.append(chat + " ");
            msgBuf.append(msg);
            model.setMessage(msgBuf.toString());
            model.setOriginalMessage(msg);
            CommonBiz cmnBiz = new CommonBiz();
            model.setIcon(cmnBiz.getPluginIconUrl(GSConstChat.PLUGIN_ID_CHAT,
                    reqMdl.getDomain()));

            msgList = new ArrayList<MainInfoMessageModel>();
            msgList.add(model);
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


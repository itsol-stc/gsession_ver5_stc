package jp.groupsession.v2.anp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.dao.AnpJdataDao;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.anp.model.AnpJdataModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] 安否確認に関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class AnpMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpMainInfoMessage.class);

    /** 安否確認メインURL */
    public static final String ANPI_MAIN_URL = "../anpi/anp010.do";

    /**
     * <p>コンストラクタ
     */
    public AnpMainInfoMessage() {
    }

    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説] メインへは未開封のメッセージ件数を表示します。
     * <br>           未開封のメッセージがない場合は表示しません。
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
        String linkUrl = ANPI_MAIN_URL;

        AnpHdataModel hdata = null;
        boolean autoCommit = false;
        try {
            try {
                autoCommit = con.getAutoCommit();
                if (!autoCommit) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log__.error("auto commitの設定に失敗", e);
            }

            //現在確認中の安否確認データがあるかどうか確認
            try {
                AnpHdataDao hDao = new AnpHdataDao(con);
                hdata = hDao.selectInHaisin();
            } catch (SQLException e) {
                log__.info("安否確認データ取得に失敗しました。");
            }

            if (hdata == null) {
                return null;
            }

            //配信データ.メイン表示 = 送信先ユーザのみ の場合
            //送信先ユーザに含まれているかを判定
            if (hdata.getAphViewMain() == GSConstAnpi.APH_VIEW_MAIN_SENDTO) {
                try {
                    AnpJdataDao jdataDao = new AnpJdataDao(con);
                    AnpJdataModel jdataMdl = jdataDao.select(hdata.getAphSid(), usid);
                    if (jdataMdl == null) {
                        log__.debug("送信先ユーザに含まれていない");
                        return null;
                    }
                } catch (SQLException e) {
                    log__.info("安否状況データ取得に失敗しました。");
                }
            }
        } finally {
            if (!autoCommit) {
                try {
                    con.setAutoCommit(false);
                } catch (SQLException e) {
                    log__.error("auto commitの設定に失敗", e);
                }
            }
        }

        String cap;
        if (hdata.getAphKnrenFlg() == GSConstAnpi.KNREN_MODE_ON) {
            cap = "[ " + gsMsg.getMessage("anp.plugin")
                    + " " + gsMsg.getMessage("anp.knmode") + " ]";
        } else {
            cap = "[ " + gsMsg.getMessage("anp.plugin") + " ]";
        }

        String msg = gsMsg.getMessage("anp.main.info");

        //メッセージを作成する。
        MainInfoMessageModel model = new MainInfoMessageModel();
        model.setPluginId(GSConst.PLUGIN_ID_ANPI);
        model.setPluginName(gsMsg.getMessage("anp.plugin"));
        model.setLinkUrl(linkUrl);
        StringBuilder msgBuf = new StringBuilder();
        msgBuf.append(cap + " ");
        msgBuf.append(msg);
        model.setMessage(msgBuf.toString());
        model.setOriginalMessage(msg);
        CommonBiz cmnBiz = new CommonBiz();
        model.setIcon(cmnBiz.getPluginIconUrl(GSConstAnpi.PLUGIN_ID,
                reqMdl.getDomain()));

        msgList = new ArrayList<MainInfoMessageModel>();
        msgList.add(model);
        return msgList;
    }

}

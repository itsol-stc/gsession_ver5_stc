package jp.groupsession.v2.cir;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] 回覧板に関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirMainInfoMessage.class);

    /** 回覧板メインURL */
    public static final String CIRCULAR_MAIN_URL = "../circular/cir010.do";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public CirMainInfoMessage() {
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
                int usid, Connection con, GsMessage gsMsg, RequestModel reqMdl) {
        ArrayList<MainInfoMessageModel> msgList = null;
        String linkUrl = CIRCULAR_MAIN_URL;

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

            CirAccountDao cacDao = new CirAccountDao(con);
            List<CirAccountModel> cacMdlList = null;

            //使用可能なアカウントを取得
            try {
                cacMdlList = cacDao.getAccountList(usid);
            } catch (SQLException e1) {
                log__.error("アカウントの取得に失敗", e1);
            }

            if (cacMdlList != null && !cacMdlList.isEmpty()) {

                boolean headMsgFlg = false;
                msgList = new ArrayList<MainInfoMessageModel>();

                for (CirAccountModel cacMdl : cacMdlList) {

                    //未確認の件数を取得する。
                    CirCommonBiz biz = new CirCommonBiz();
                    int count = 0;
                    try {
                        count = biz.getUnopenedCirCnt(cacMdl.getCacSid(), con);
                    } catch (SQLException e) {
                        log__.error("未開封回覧板カウントの取得に失敗", e);
                    }

                    if (count > 0) {

                        if (!headMsgFlg) {
                            MainInfoMessageModel model = new MainInfoMessageModel();
                            model.setPluginId(GSConst.PLUGINID_CIR);
                            model.setPluginName(gsMsg.getMessage("cir.5"));
                            model.setMessage(gsMsg.getMessage("cir.60"));
                            model.setOriginalMessage(gsMsg.getMessage("cir.info.msg"));
                            CommonBiz cmnBiz = new CommonBiz();
                            model.setIcon(
                                    cmnBiz.getPluginIconUrl(GSConstCircular.PLUGIN_ID_CIRCULAR,
                                                                        reqMdl.getDomain()));
                            model.setLinkUrl(linkUrl);
                            msgList.add(model);
                            headMsgFlg = true;
                        }

                        //メッセージを作成する。
                        MainInfoMessageModel model = new MainInfoMessageModel();
                        model.setPluginId(GSConst.PLUGINID_CIR);
                        StringBuilder msgBuf = new StringBuilder();

                        msgBuf.append("  ・ ");
                        msgBuf.append(cacMdl.getCacName());
                        msgBuf.append(": ");
                        msgBuf.append(String.valueOf(count));
                        msgBuf.append(gsMsg.getMessage("cmn.number") + " ");
                        model.setLinkUrl(linkUrl + "?cirViewAccount="
                                                 + cacMdl.getCacSid());
                        model.setMessage(msgBuf.toString());
                        model.setOriginalMessage(msgBuf.toString());

                        msgList.add(model);
                    }
                }
            }
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

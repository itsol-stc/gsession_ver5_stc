package jp.groupsession.v2.fil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
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
public class FilMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilMainInfoMessage.class);

    /** 回覧板メインURL */
    public static final String FILE_TORIHIKI_URL = "../file/fil300.do?backDsp=main";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public FilMainInfoMessage() {
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
        String linkUrl = FILE_TORIHIKI_URL;

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

            //ログインユーザが編集権限を持つキャビネット内に
            //取引先情報未登録のファイルが存在するかを確認する
            boolean msgFlg = true;
            try {
                FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con);
                List<Integer> fcbSidList = feaDao.getFcbSidList(reqMdl.getSmodel());
                msgFlg = (fcbSidList != null && !fcbSidList.isEmpty());
            } catch (SQLException e) {
                log__.info("取引先未登録ファイルの取得に失敗しました。");
            }
            
            if (msgFlg) {
                MainInfoMessageModel model = new MainInfoMessageModel();
                model.setPluginId(GSConst.PLUGIN_ID_FILE);
                String plgName = gsMsg.getMessage("cmn.filekanri");
                String message = gsMsg.getMessage("fil.990"); 
                model.setPluginName(plgName);
                model.setOriginalMessage(message);
                model.setMessage("[ " + plgName + " ] " + message);
                CommonBiz cmnBiz = new CommonBiz();
                model.setIcon(
                        cmnBiz.getPluginIconUrl(GSConstFile.PLUGIN_ID_FILE,
                                                            reqMdl.getDomain()));
                model.setLinkUrl(linkUrl);
                msgList = new ArrayList<MainInfoMessageModel>();
                msgList.add(model);
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

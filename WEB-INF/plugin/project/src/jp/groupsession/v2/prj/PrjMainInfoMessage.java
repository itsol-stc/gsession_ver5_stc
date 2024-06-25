package jp.groupsession.v2.prj;

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
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] プロジェクト管理に関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjMainInfoMessage.class);

    /** プロジェクト ダッシュボードURL */
    public static final String PROJECT_INDEX_URL = "../project/prj010.do";

    /**
     * <p>Set HttpServletRequest
     */
    public PrjMainInfoMessage() {

    }

    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説] メインへは警告中のTODOの件数を表示します。
     * <br>         警告中のTODOがない場合は表示しません。
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
        String linkUrl = PROJECT_INDEX_URL;

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

            //警告中のTODOの件数を取得する。
            int count = 0;
            try {
                ProjectSearchDao projectDao = new ProjectSearchDao(con);
                count = projectDao.getKeikokuTodoCount(usid);
            } catch (SQLException e) {
                log__.error("警告中のTODOカウントの取得に失敗", e);
            }

            if (count <= 0) {
                return null;
            }
            //プロジェクト
            String textPrj = gsMsg.getMessage("cmn.project");

            //警告中のTODOがcount件あります。
            String textWarningTodo =
                gsMsg.getMessage("project.126", new String[] {String.valueOf(count)});

            //メッセージを作成する。
            MainInfoMessageModel model = new MainInfoMessageModel();
            model.setPluginId(GSConst.PLUGINID_PRJ);
            model.setPluginName(textPrj);
            model.setLinkUrl(linkUrl);
            StringBuilder msgBuf = new StringBuilder();
            msgBuf.append("[ " +  textPrj + " ] ");
            msgBuf.append(textWarningTodo);
            model.setMessage(msgBuf.toString());
            model.setOriginalMessage(textWarningTodo);
            CommonBiz cmnBiz = new CommonBiz();
            model.setIcon(cmnBiz.getPluginIconUrl(GSConstProject.PLUGIN_ID_PROJECT,
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

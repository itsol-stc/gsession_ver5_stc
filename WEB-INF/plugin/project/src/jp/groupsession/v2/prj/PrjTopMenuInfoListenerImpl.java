package jp.groupsession.v2.prj;

import java.sql.Connection;
import javax.servlet.http.HttpSession;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.ITopMenuInfo;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.model.PrjUserConfModel;


/**
 * <br>[機  能] トップメニュー表示に実行されるリスナーを実装
 * <br>[解  説] プロジェクトについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjTopMenuInfoListenerImpl implements ITopMenuInfo {
    /**
     * <br>[機  能] トップメニューのプラグインIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return id プラグインID
     */
    public String getTopMenuId() {
        return GSConstProject.PLUGIN_ID_PROJECT;
    }
    /**
     * <br>[機  能] トップメニューのURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return URL　トップメニューのURL
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws Exception URL取得失敗時に例外発生
     */
    public String getTopMenuUrl(Connection con, RequestModel reqMdl) throws Exception {
        String url = null;

        int usrSid = -1;

        if (reqMdl != null && reqMdl.getSmodel() != null) {
            usrSid = reqMdl.getSmodel().getUsrsid();
        }

        //DBより設定情報を取得。なければデフォルト値とする。
        PrjCommonBiz biz = new PrjCommonBiz(con, reqMdl);
        PrjUserConfModel pconf = biz.getPrjUserConfModel(con, usrSid);
        if (pconf != null) {
            switch (pconf.getPucDefDsp()) {
            case GSConstProject.DSP_TODO : //TODO(プロジェクト)
            default:
                url = GSConstProject.DSP_TODO_URL;
                break;
            case GSConstProject.DSP_PROJECT : //プロジェクト
                url = GSConstProject.DSP_PROJECT_URL;
                break;
            }
        }
        return url;
    }
    /**
     * <p>セッションからユーザモデルを取得する。
     * @param reqMdl リクエストモデル
     * @return SessionUserModel
     */
    public BaseUserModel getSessionUserModel(RequestModel reqMdl) {
        HttpSession session = reqMdl.getSession();
        if (session == null) {
            return null;
        }
        Object tmp = session.getAttribute(GSConst.SESSION_KEY);
        if (tmp == null) {
            return null;
        }
        BaseUserModel smodel = (BaseUserModel) tmp;
        return smodel;
    }
}

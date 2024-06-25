package jp.groupsession.v2.rsv;

import java.sql.Connection;

import javax.servlet.http.HttpSession;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.ITopMenuInfo;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvUserModel;

/**
 * <br>[機  能] トップメニュー表示に実行されるリスナーを実装
 * <br>[解  説] 施設予約についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvTopMenuInfoListenerImpl implements ITopMenuInfo {
    /**
     * <br>[機  能] トップメニューのプラグインIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return id プラグインID
     */
    public String getTopMenuId() {
        return GSConstReserve.PLUGIN_ID_RESERVE;
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
        RsvUserDao dao = new RsvUserDao(con);
        RsvUserModel model = dao.select(usrSid);
        if (model != null) {
            switch (model.getRsuIniDsp()) {
            case GSConstReserve.DSP_DAY : //日間
                url = GSConstReserve.DSP_DAY_URL;
                break;
            case GSConstReserve.DSP_WEEK : //週間
            default:
                url = GSConstReserve.DSP_WEEK_URL;
                break;
            }
        } else {
            url  = GSConstReserve.DSP_WEEK_URL;
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

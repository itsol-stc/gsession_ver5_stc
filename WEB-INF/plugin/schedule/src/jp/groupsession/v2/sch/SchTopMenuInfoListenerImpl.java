package jp.groupsession.v2.sch;

import java.sql.Connection;
import javax.servlet.http.HttpSession;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.ITopMenuInfo;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchPriConfModel;


/**
 * <br>[機  能] トップメニュー表示に実行されるリスナーを実装
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchTopMenuInfoListenerImpl implements ITopMenuInfo {
    /**
     * <br>[機  能] トップメニューのプラグインIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return id プラグインID
     */
    public String getTopMenuId() {
        return GSConstSchedule.PLUGIN_ID_SCHEDULE;
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
        SchCommonBiz biz = new SchCommonBiz(reqMdl);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, usrSid);
        if (pconf != null) {
            switch (pconf.getSccDefDsp()) {
            case GSConstSchedule.DSP_DAY : //日間
                url = GSConstSchedule.DSP_DAY_URL;
                break;
            case GSConstSchedule.DSP_WEEK : //週間
            default:
                url = GSConstSchedule.DSP_WEEK_URL;
                break;
            case GSConstSchedule.DSP_MONTH : //月間
                url = GSConstSchedule.DSP_MONTH_URL;
                break;
            case GSConstSchedule.DSP_PRI_WEEK : //個人週間
                url = GSConstSchedule.DSP_PRI_WEEK_URL;
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

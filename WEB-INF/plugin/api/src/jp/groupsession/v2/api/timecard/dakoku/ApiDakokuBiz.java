package jp.groupsession.v2.api.timecard.dakoku;

import java.sql.Connection;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;

/**
 * <br>[機  能] タイムカードの始業終業時間の打刻を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiDakokuBiz {

    /**
     * <br>[機  能] タイムカードを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return ret 0:OK 1:更新済み 2:エラー
     * @throws Exception コネクション取得時例外
     */
    public int updateTcd(RequestModel reqMdl, Connection con)
        throws Exception {


        BaseUserModel usModel = reqMdl.getSmodel();
        int userSid = usModel.getUsrsid();

        int ret = 2;
        //不正データチェック
        TimecardBiz tcBiz = new TimecardBiz();
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(userSid, con);
        if (tcBiz.isFailDataExist(userSid, con, admConf)) {
            return ret;
        }

        UDate sysDate = new UDate();
        TcdTcdataModel tcMdl = tcBiz.dakokuTcd(reqMdl, con,
                sysDate, GSConstTimecard.DAKOKUKBN_NONE, false);
        if (tcMdl == null) {
            return 1;
        }
        return 0;

    }

}

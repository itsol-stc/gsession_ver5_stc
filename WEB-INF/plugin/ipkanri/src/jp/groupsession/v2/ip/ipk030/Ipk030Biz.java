package jp.groupsession.v2.ip.ipk030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.dao.IpkNetAdmDao;
import jp.groupsession.v2.ip.model.IpkGrpModel;
import jp.groupsession.v2.ip.model.IpkNetAdmModel;

/**
 * <br>[機  能] IP管理 全ネットワーク管理者設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 */
public class Ipk030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk030Biz.class);

    /**
     * <br>[機  能] 全ネットワーク管理者情報を設定する(初期表示)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitAdminData(Ipk030ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        try {
            IpkNetAdmDao ipknetAdmDao = new IpkNetAdmDao(con);
            //全ネットワーク管理者のユーザSIDのリストを取得する。
            ArrayList<String> admSidList = ipknetAdmDao.selectAdminUsrSid();
            IpkBiz ipkBiz = new IpkBiz();

            //全ネットワーク管理者情報を取得する。
            IpkGrpModel model = ipkBiz.setAdminData(con, reqMdl, admSidList, IpkConst.IPK_HENSYU);

            //フォームにネットワーク管理者情報をセットする。
            paramMdl.setAdminSidList(model.getAdminSidList());
            paramMdl.setGroupSelect(model.getGroupSelect());

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] 全ネットワーク管理者情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param adminSidList IP管理ユーザSIDリスト
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setNetworkAdmin(String[] adminSidList, Connection con, int sessionUsrSid)
    throws SQLException {

        IpkNetAdmDao ipkAdminDao = new IpkNetAdmDao(con);
        //コミットフラグの定義
        boolean commitFlg = false;
        //オートコミットしないようにする。
        con.setAutoCommit(false);

        try {
            //既存管理者情報を削除
            ipkAdminDao.deleteAdmin(-1);
            //現在日時を取得する。
            UDate now = new UDate();
            //追加する管理者のユーザSidリストを作成する。
            if (adminSidList == null) {
                commitFlg = true;
                return;
            }

            //削除区分ユーザを除外する。
            ArrayList<Integer> userArray = new ArrayList<Integer>();
            for (String strUsrSid : adminSidList) {
                userArray.add(Integer.parseInt(strUsrSid));
            }
            CmnUsrmDao usrDao = new CmnUsrmDao(con);
            CmnUsrmModel usrModel = null;
            int cnt = usrDao.getCountDeleteUser(userArray);

            IpkNetAdmModel model = new IpkNetAdmModel();
            for (String admSid : adminSidList) {
                int sid = NullDefault.getInt(admSid, 0);

                if (cnt > 0) {
                    usrModel = usrDao.select(sid);
                    if (usrModel.getUsrJkbn() == GSConst.JTKBN_DELETE) {
                        continue;
                    }
                }

                model.setNetSid(-1);
                model.setNetAuid(sessionUsrSid);
                model.setNetEuid(sessionUsrSid);
                model.setNetAdate(now);
                model.setNetEdate(now);
                model.setUsrSid(sid);
                ipkAdminDao.insertAdmin(model);
            }
            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
                log__.debug("コミット完了");
            } else {
                con.rollback();
            }
        }
    }
}
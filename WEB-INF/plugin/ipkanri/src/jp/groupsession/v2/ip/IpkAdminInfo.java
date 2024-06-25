package jp.groupsession.v2.ip;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.dao.IpkAddAdmDao;
import jp.groupsession.v2.ip.dao.IpkNetAdmDao;

/**
 * <br>[機  能] IP管理 管理者設定で使用する情報を取得する。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkAdminInfo {

    /**
     * <br>[機  能] GS管理者か全ネットワーク管理者ならばtrue、管理者でなければfalseを返す。　　
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return boolean 全ネットワーク管理者権限が有り:true, 無し:false
     * @throws SQLException 実行例外
     */
    public boolean isGsIpAdm(
        RequestModel reqMdl,
        Connection con)
        throws SQLException {

        try {
            //セッションユーザSIDを取得する。
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();
            CommonBiz cmnBiz = new CommonBiz();
            boolean admFlg = cmnBiz.isPluginAdmin(con, usModel, IpkConst.PLUGIN_ID_IPKANRI);
            //許可しない場合、管理者か判定
            if (admFlg) {
                return IpkConst.KANRI_KENGEN_ARI;
            }
            //セッションユーザが管理者になっているネットワークSID リストを取得する。
            IpkNetAdmDao ipkAdminDao = new IpkNetAdmDao(con);
            int allNetworkAdmCount = ipkAdminDao.countUsrAdmAllNet(sessionUsrSid);
            //セッションユーザが全ネットワーク管理者ならばtrue
            if (allNetworkAdmCount > 0) {
                        return IpkConst.KANRI_KENGEN_ARI;
            }
        } catch (SQLException e) {
            throw e;
        }
        return IpkConst.KANRI_KENGEN_NASI;
    }

    /**
     * <br>[機  能] セッションユーザの管理ネットワークリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return admList セッションユーザの管理ネットワークSIDリスト
     * @throws SQLException 実行例外
     */
    public ArrayList<String>  getUsrAdminNetList(
        int sessionUsrSid,
        Connection con)
        throws SQLException {

        try {
            //セッションユーザが管理者になっているネットワークSIDを取得する。
            IpkNetAdmDao ipkAdmDao = new IpkNetAdmDao(con);
            ArrayList<String> admList = ipkAdmDao.getUsrAdminNetworkSidData(sessionUsrSid);
            return admList;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] セッションユーザの使用者のIPアドレスSIDリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return admList セッションユーザのIPアドレスSIDリスト
     * @throws SQLException 実行例外
     */
    public ArrayList<String>  getUsrAdminIpadSid(
        RequestModel reqMdl,
        Connection con)
        throws SQLException {

        //セッションユーザSIDを取得する。
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        //セッションユーザが使用者になっているIPアドレスSIDを取得する。
        IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
        ArrayList<String> admList = ipkAddAdmDao.selectUsrAdminIadSid(sessionUsrSid);
        return admList;
    }
}
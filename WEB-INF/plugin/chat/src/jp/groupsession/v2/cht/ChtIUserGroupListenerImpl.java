package jp.groupsession.v2.cht;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cht.dao.ChtUserPairDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] チャットについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtIUserGroupListenerImpl implements IUserGroupListener {

    /**
     * <br>[機  能] ユーザ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid 追加されるユーザSID
     * @param con DBコネクション
     * @param cntCon MlCountMtController
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void addUser(MlCountMtController cntCon,
                        Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

    }

    /**
     * <br>[機  能] ユーザ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUser(Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {
        try {

            ChtUserPairDao cupDao = new ChtUserPairDao(con);
            ArrayList<Integer> cupList = cupDao.selectCupSidForUserDel(usid);
            for (int cupSid : cupList) {
                cupDao.update(cupSid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <br>[機  能] グループ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void addGroup(Connection con, int gsid, int eusid) throws SQLException {
    }

    /**
     * <br>[機  能] グループ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void deleteGroup(
            Connection con, int gsid, int eusid, RequestModel reqMdl) throws SQLException {

    }

    /**
     * <br>[機  能] ユーザの所属グループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param pastGsids 変更前のグループSID配列
     * @param gsids 変更後のグループSID配列
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeBelong(Connection con, int usid, int[] pastGsids, int[] gsids, int eusid)
    throws SQLException {
    }

    /**
     * <br>[機  能] ユーザのデフォルトグループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param gsid 変更後のデフォルトグループ
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeDefaultBelong(Connection con, int usid, int gsid, int eusid)
    throws SQLException {
    }
}

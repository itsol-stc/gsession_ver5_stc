package jp.groupsession.v2.bmk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.bmk.bmk010.Bmk010Biz;
import jp.groupsession.v2.bmk.dao.BmkBookmarkDao;
import jp.groupsession.v2.bmk.dao.BmkGconfDao;
import jp.groupsession.v2.bmk.dao.BmkGroupEditDao;
import jp.groupsession.v2.bmk.dao.BmkLabelDao;
import jp.groupsession.v2.bmk.dao.BmkPublicEditDao;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.model.BmkBookmarkModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] 掲示板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class BmkIUserGroupListenerImpl implements IUserGroupListener {

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
        //個人設定情報の削除
        BmkUconfDao uconfDao = new BmkUconfDao(con);
        uconfDao.delete(usid);

        //共有ブックマーク編集権限の削除
        BmkPublicEditDao pubDao = new BmkPublicEditDao(con);
        pubDao.delete(-1, usid);

        //ブックマーク情報、URLマスタ、ラベル付与情報の削除
        BmkBookmarkDao bmkDao = new BmkBookmarkDao(con);
        List<BmkBookmarkModel> bmkList = bmkDao.getUsrBookmark(usid);
        for (BmkBookmarkModel bmkModel : bmkList) {
            Bmk010Biz biz = new Bmk010Biz(reqMdl);
            biz.deleteBookmark(bmkModel.getBmkSid(), -1, con);
        }

        //グループブックマーク編集権限の削除
        BmkGroupEditDao grpDao = new BmkGroupEditDao(con);
        grpDao.deleteUser(usid);

        //ラベル情報の削除
        BmkLabelDao lblDao = new BmkLabelDao(con);
        lblDao.delete(-1, usid);
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
        //グループ設定情報の削除
        BmkGconfDao gconfDao = new BmkGconfDao(con);
        gconfDao.delete(gsid);

        //グループブックマーク編集権限の削除
        BmkGroupEditDao grpDao = new BmkGroupEditDao(con);
        grpDao.deleteGroup(gsid);

        //共有ブックマーク編集権限の削除
        BmkPublicEditDao pubDao = new BmkPublicEditDao(con);
        pubDao.delete(gsid, -1);

        //ブックマーク情報、URLマスタ、ラベル付与情報の削除
        BmkBookmarkDao bmkDao = new BmkBookmarkDao(con);
        List<BmkBookmarkModel> bmkList = bmkDao.getGroupBookmark(gsid);
        for (BmkBookmarkModel bmkModel : bmkList) {
            Bmk010Biz biz = new Bmk010Biz(reqMdl);
            biz.deleteBookmark(bmkModel.getBmkSid(), -1, con);
        }

        //ラベル情報の削除
        BmkLabelDao lblDao = new BmkLabelDao(con);
        lblDao.delete(gsid, -1);
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

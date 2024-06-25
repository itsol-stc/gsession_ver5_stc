package jp.groupsession.v2.zsk;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrInoutDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.zsk.dao.ZaiGpriConfDao;
import jp.groupsession.v2.zsk.dao.ZaiIndexDao;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] 在席管理に関する処理を実行します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskIUserGroupListenerImpl implements IUserGroupListener {

    /**
     * <br>[機  能] ユーザ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid 追加されるユーザSID
     * @param cntCon MlCountMtController
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void addUser(MlCountMtController cntCon,
            Connection con, int usid, int eusid, RequestModel reqMdl)
    throws SQLException {

        UDate now = new UDate();

        CmnUsrInoutModel bean = new CmnUsrInoutModel();
        bean.setUioSid(usid);
        bean.setUioStatus(GSConst.UIOSTS_IN);
        bean.setUioAuid(eusid);
        bean.setUioAdate(now);
        bean.setUioEuid(eusid);
        bean.setUioEdate(now);

        //在席の状態で在席ステータスを登録
        CmnUsrInoutDao dao = new CmnUsrInoutDao(con);
        dao.insert(bean);
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

        //在席ステータス削除
        CmnUsrInoutDao inOutDao = new CmnUsrInoutDao(con);
        inOutDao.delete(usid);

        //座席表項目配置情報削除
        ZaiIndexDao zaiIdxDao = new ZaiIndexDao(con);
        zaiIdxDao.deleteUsrData(usid);

        //表示グループ設定削除
        ZaiGpriConfDao gpriConfDao = new ZaiGpriConfDao(con);
        gpriConfDao.delete(usid);
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
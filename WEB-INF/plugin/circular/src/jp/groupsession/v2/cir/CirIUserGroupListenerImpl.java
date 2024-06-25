package jp.groupsession.v2.cir;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirAccountSortDao;
import jp.groupsession.v2.cir.dao.CirAccountUserDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirInfLabelDao;
import jp.groupsession.v2.cir.dao.CirLabelDao;
import jp.groupsession.v2.cir.dao.CirUserDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CirUserModel;
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] 回覧板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirIUserGroupListenerImpl implements IUserGroupListener {

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

        UDate now = new UDate();

        CirUserModel cuMdl = new CirUserModel();
        cuMdl.setUsrSid(usid);
        cuMdl.setCurMaxDsp(GSConst.LIST_COUNT_LIMIT);
        //cuMdl.setCurSmlNtf(GSConstCircular.SMAIL_TSUUCHI);
        cuMdl.setCurAuid(eusid);
        cuMdl.setCurAdate(now);
        cuMdl.setCurEuid(eusid);
        cuMdl.setCurEdate(now);

//        cuMdl.setCurMemoKbn(GSConstCircular.CIR_INIT_MEMO_CHANGE_NO);
//        cuMdl.setCurMemoDay(GSConstCircular.CIR_INIT_MEMO_ONEWEEK);
//        cuMdl.setCurKouKbn(GSConstCircular.CIR_INIT_SAKI_PUBLIC);
//        cuMdl.setCurInitKbn(GSConstCircular.CIR_INIT_KBN_NOSET);

        //回覧板個人設定を登録する
        CirUserDao cuDao = new CirUserDao(con);
        cuDao.insertCirUser(cuMdl);

        //ユーザ情報取得
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel usrMdl = null;
        usrMdl = udao.select(usid);

        if (usrMdl != null) {
            //アカウント登録
            __insertCirAccount(cntCon, con, usid, usrMdl, reqMdl);
        }
    }

    /**
     * <br>[機  能] ユーザ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param uSid ユーザSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUser(Connection con, int uSid, int eusid, RequestModel reqMdl)
    throws SQLException {

        //回覧板個人設定を削除する
        CirUserDao cuDao = new CirUserDao(con);
        cuDao.deleteCirUser(uSid);

        //回覧板情報論理削除
        UDate now = new UDate();

        CirAccountDao accountDao = new CirAccountDao(con);
        CirAccountModel accounMdl = accountDao.selectFromUsrSid(uSid);

        CirInfModel ciMdl = new CirInfModel();
        ciMdl.setCifJkbn(GSConstCircular.DSPKBN_DSP_DEL);
        ciMdl.setCifEuid(eusid);
        ciMdl.setCifEdate(now);
        ciMdl.setCifAuid(accounMdl.getCacSid());

        CirInfDao ciDao = new CirInfDao(con);
        ciDao.updateUserCir(ciMdl);

        //回覧先情報論理削除
        CirViewModel cvMdl = new CirViewModel();
        cvMdl.setCvwDsp(GSConstCircular.DSPKBN_DSP_DEL);
        cvMdl.setCvwEuid(eusid);
        cvMdl.setCvwEdate(now);
        cvMdl.setCacSid(accounMdl.getCacSid());

        CirViewDao cvDao = new CirViewDao(con);
        cvDao.updateUserCir(cvMdl);

        //アカウントを論理削除
        accountDao.updateJkbn(accounMdl.getCacSid(), GSConstCircular.CAC_JKBN_DELETE);

        //アカウント使用者を削除する
        CirAccountUserDao acctUserDao = new CirAccountUserDao(con);
        acctUserDao.deleteOfUser(uSid);

        // ラベル情報削除
        CirLabelDao labelDao = new CirLabelDao(con);
        labelDao.deleteAccountLabel(accounMdl.getCacSid());
        // 回覧板送信ラベル削除
        CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
        labelInfDao.deleteAccountLabel(accounMdl.getCacSid());
        // 回覧板受信ラベル削除
        CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
        labelViewDao.deleteAccountLabel(accounMdl.getCacSid());
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


    /**
     * <br>[機  能] 回覧板のアカウントを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param cntCon MlCountMtController
     * @param usrMdl CmnUsrmInfModel
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void __insertCirAccount(MlCountMtController cntCon,
                                   Connection con,
                                   int usid,
                                   CmnUsrmInfModel usrMdl,
                                   RequestModel reqMdl) throws SQLException {

        //アカウント情報の登録
        CirAccountModel accountModel = new CirAccountModel();

        accountModel.setUsrSid(usid);
        accountModel.setCacType(GSConstCircular.CAC_TYPE_USER);
        accountModel.setCacName(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
        accountModel.setCacBiko("");
        accountModel.setCacTheme(0);
        accountModel.setCacSmlNtf(0);
        accountModel.setCacMemoKbn(0);
        accountModel.setCacMemoDay(0);
        accountModel.setCacKouKbn(0);
        accountModel.setCacInitKbn(0);

        //アカウント採番取得
        int cacSaiSid = (int) cntCon.getSaibanNumber("circular", "account", -1);

        CirAccountDao accountDao = new CirAccountDao(con);
        CirAccountUserDao accountUserDao = new CirAccountUserDao(con);
        CirAccountSortDao accountSortDao = new CirAccountSortDao(con);

        accountModel.setCacSid(cacSaiSid);
        accountDao.insertAccountDef(accountModel);

        //アカウントの並び順を登録する
        accountSortDao.insertAccountSort(cacSaiSid, usid);
        accountUserDao.insert(cacSaiSid, accountModel.getCacType(),
                   new String[] {String.valueOf(usid)});

    }
}

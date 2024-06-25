package jp.groupsession.v2.cir.cir130kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir150.Cir150AccountModel;
import jp.groupsession.v2.cir.cir150.Cir150Dao;
import jp.groupsession.v2.cir.cir150.Cir150SearchModel;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 回覧板 個人設定 初期値設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir130knBiz {


    /**
     * <br>[機  能] 初期処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void getInitData(RequestModel reqMdl,
                                       Cir130knParamModel paramMdl,
                                       Connection con)
        throws SQLException {


        //アカウントSID
        List<Cir150AccountModel> accountList = new ArrayList<Cir150AccountModel>();

        if (paramMdl.getCir130SelKbn() == GSConstCircular.ACCOUNT_SEL) {
            //指定アカウント
            Cir150AccountModel cirMdl = new Cir150AccountModel(reqMdl);
            cirMdl.setAccountSid(paramMdl.getCir130AccountSid());
            cirMdl.setAccountName(paramMdl.getCir130AccountName());
            accountList.add(cirMdl);
            paramMdl.setCir130knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setCir130knAccountList(accountList);

        }
    }

    /**
     * <br>[機  能] 設定値をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setInitDataDB(
            Connection con,
            Cir130knParamModel paramMdl,
            int userSid
            ) throws Exception {

        CirAccountModel userMdl = new CirAccountModel();
        CirAccountDao userDao = new CirAccountDao(con);

        userMdl.setCacSid(paramMdl.getCirViewAccount());
        userMdl.setCacMemoKbn(paramMdl.getCir130memoKbn());
        userMdl.setCacMemoDay(paramMdl.getCir130memoPeriod());
        userMdl.setCacKouKbn(paramMdl.getCir130show());
        userMdl.setCacInitKbn(GSConstCircular.CIR_INIT_KBN_SET);

        userDao.updateInitSet(userMdl);

    }

    /**
     * <br>[機  能] 指定ユーザの利用可能な全アカウントを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return accountList
     */
    public List<Cir150AccountModel> getAllUseAccount(
                                 RequestModel reqMdl,
                                 int usrSid,
                                 Connection con)
        throws SQLException {

        Cir150SearchModel searchMdl = new Cir150SearchModel();
        searchMdl.setUserSid(usrSid);
        searchMdl.setMaxCount(-1);
        Cir150Dao dao = new Cir150Dao(con);
        List<Cir150AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);

        return accountList;
    }

}

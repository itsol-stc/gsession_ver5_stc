package jp.groupsession.v2.cir.cir120kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir150.Cir150AccountModel;
import jp.groupsession.v2.cir.cir150.Cir150Dao;
import jp.groupsession.v2.cir.cir150.Cir150SearchModel;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirInfLabelDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 回覧板 管理者設定 手動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir120knBiz {

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Cir120knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void getInitData(RequestModel reqMdl,
                                       Cir120knParamModel paramMdl)
        throws SQLException {


        //アカウントSID
        List<Cir150AccountModel> accountList = new ArrayList<Cir150AccountModel>();

        if (paramMdl.getCir120SelKbn() == GSConstCircular.ACCOUNT_SEL) {
            //指定アカウント
            Cir150AccountModel cirMdl = new Cir150AccountModel(reqMdl);
            cirMdl.setAccountSid(paramMdl.getCir120AccountSid());
            cirMdl.setAccountName(paramMdl.getCir120AccountName());
            accountList.add(cirMdl);
            paramMdl.setCir120knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con__);
            paramMdl.setCir120knAccountList(accountList);

        }
    }

    /**
     * <br>[機  能] 回覧板手動削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public void updateSyudoDelSetting(Cir120knParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {


        //アカウントSID
        List<Cir150AccountModel> accountList = new ArrayList<Cir150AccountModel>();

        if (paramMdl.getCir120SelKbn() == GSConstCircular.ACCOUNT_SEL) {
            //指定アカウント
            Cir150AccountModel smlMdl = new Cir150AccountModel(reqMdl);
            smlMdl.setAccountSid(paramMdl.getCir120AccountSid());
            smlMdl.setAccountName(paramMdl.getCir120AccountName());
            accountList.add(smlMdl);
            paramMdl.setCir120knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con__);

            paramMdl.setCir120knAccountList(accountList);

        }

        ArrayList<CirAdelModel> jdelList = new ArrayList<CirAdelModel>();
        ArrayList<CirAdelModel> sdelList = new ArrayList<CirAdelModel>();
        ArrayList<CirAdelModel> ddelList = new ArrayList<CirAdelModel>();

        CirInfDao cirInfDao = new CirInfDao(con__);
        CirViewDao cirViewDao = new CirViewDao(con__);
        CirAdelModel adelModel = null;
        //受信
        int jdelKbn = NullDefault.getInt(paramMdl.getCir120JdelKbn(),
                                        GSConstCircular.CIR_AUTO_DEL_NO);

        if (jdelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            for (Cir150AccountModel mdl : paramMdl.getCir120knAccountList()) {
                adelModel = new CirAdelModel();
                adelModel.setCadJdelYear(Integer.parseInt(paramMdl.getCir120JYear()));
                adelModel.setCadJdelMonth(Integer.parseInt(paramMdl.getCir120JMonth()));
                adelModel.setCacSid(mdl.getAccountSid());
                jdelList.add(adelModel);
            }
        }

        //送信
        int sdelKbn = NullDefault.getInt(paramMdl.getCir120SdelKbn(),
                                        GSConstCircular.CIR_AUTO_DEL_NO);
        if (sdelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            for (Cir150AccountModel mdl : paramMdl.getCir120knAccountList()) {
                adelModel = new CirAdelModel();
                adelModel.setCadSdelYear(Integer.parseInt(paramMdl.getCir120SYear()));
                adelModel.setCadSdelMonth(Integer.parseInt(paramMdl.getCir120SMonth()));
                adelModel.setCacSid(mdl.getAccountSid());
                sdelList.add(adelModel);
            }
        }

        int ddelKbn = NullDefault.getInt(paramMdl.getCir120DdelKbn(),
                                        GSConstCircular.CIR_AUTO_DEL_NO);

        if (ddelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            for (Cir150AccountModel mdl : paramMdl.getCir120knAccountList()) {
                adelModel = new CirAdelModel();
                adelModel.setCadDdelYear(Integer.parseInt(paramMdl.getCir120DYear()));
                adelModel.setCadDdelMonth(Integer.parseInt(paramMdl.getCir120DMonth()));
                adelModel.setCacSid(mdl.getAccountSid());
                ddelList.add(adelModel);
            }
        }


        CirInfLabelDao labelInfDao = new CirInfLabelDao(con__);
        CirViewLabelDao labelViewDao = new CirViewLabelDao(con__);

        //ゴミ箱データ削除
        if (!ddelList.isEmpty()) {
            //ラベル物理削除
            labelInfDao.deleteLabel(ddelList, 2);
            labelViewDao.deleteLabel(ddelList, 2);
            //回覧板論理削除
            cirViewDao.delete(ddelList, 2);
            cirInfDao.delete(ddelList, 2);
        }

        if (!jdelList.isEmpty()) {
            //ラベル物理削除
            labelViewDao.deleteLabel(jdelList, 1);
            //受信データ論理削除
            cirViewDao.delete(jdelList, 1);
        }

        if (!sdelList.isEmpty()) {
            //ラベル物理削除
            labelInfDao.deleteLabel(sdelList, 1);
            //送信データ論理削除
            cirInfDao.delete(sdelList, 1);
        }


        //削除対象の回覧板SIDを取得
        CircularDao cDao = new CircularDao(con__);
        String[] delLst = cDao.getDelList();

        //回覧板情報を物理削除、バイナリ情報を論理削除
        cDao.deleteCir(delLst, GSConstUser.SID_ADMIN);

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
        searchMdl.setMaxCount(-1);
        searchMdl.setGrpSid(-1);
        searchMdl.setUserSid(-1);
        Cir150Dao dao = new Cir150Dao(con);

        List<Cir150AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);

        return accountList;
    }

    /**
     * <br>[機  能] 選択した年月日に不正な値が入った場合に空白文字に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cir120knParamModel
     */
    public void updateIgnoreYearMonth(Cir120knParamModel paramMdl) {
        if (!ValidateUtil.isNumber(paramMdl.getCir120JYear())) {
            paramMdl.setCir120JYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir120JMonth())) {
            paramMdl.setCir120JMonth("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir120SYear())) {
            paramMdl.setCir120SYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir120SMonth())) {
            paramMdl.setCir120SMonth("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir120DYear())) {
            paramMdl.setCir120DYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir120DMonth())) {
            paramMdl.setCir120DMonth("");
        }
    }
}
package jp.groupsession.v2.cir.cir090kn;

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
 * <br>[機  能] 回覧板 個人設定 手動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir090knBiz {

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Cir090knBiz(Connection con) {
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
                                       Cir090knParamModel paramMdl)
        throws SQLException {


        //アカウントSID
        List<Cir150AccountModel> accountList = new ArrayList<Cir150AccountModel>();

        if (paramMdl.getCir090SelKbn() == GSConstCircular.ACCOUNT_SEL) {
            //指定アカウント
            Cir150AccountModel cirMdl = new Cir150AccountModel(reqMdl);
            cirMdl.setAccountSid(paramMdl.getCir090AccountSid());
            cirMdl.setAccountName(paramMdl.getCir090AccountName());
            accountList.add(cirMdl);
            paramMdl.setCir090knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con__);
            paramMdl.setCir090knAccountList(accountList);

        }
    }

    /**
     * <br>[機  能] 回覧板手動削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void updateSyudoDelSetting(RequestModel reqMdl, Cir090knParamModel paramMdl)
        throws SQLException {

        //アカウントSID
        List<Cir150AccountModel> accountList = new ArrayList<Cir150AccountModel>();

        if (paramMdl.getCir090SelKbn() == GSConstCircular.ACCOUNT_SEL) {
            //指定アカウント
            Cir150AccountModel smlMdl = new Cir150AccountModel(reqMdl);
            smlMdl.setAccountSid(paramMdl.getCir090AccountSid());
            smlMdl.setAccountName(paramMdl.getCir090AccountName());
            accountList.add(smlMdl);
            paramMdl.setCir090knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con__);

            paramMdl.setCir090knAccountList(accountList);

        }

        ArrayList<CirAdelModel> jdelList = new ArrayList<CirAdelModel>();
        ArrayList<CirAdelModel> sdelList = new ArrayList<CirAdelModel>();
        ArrayList<CirAdelModel> ddelList = new ArrayList<CirAdelModel>();

        CirInfDao cirInfDao = new CirInfDao(con__);
        CirViewDao cirViewDao = new CirViewDao(con__);
        CirAdelModel adelModel = null;
        //受信
        int jdelKbn = NullDefault.getInt(paramMdl.getCir090JdelKbn(),
                                        GSConstCircular.CIR_AUTO_DEL_NO);

        if (jdelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            for (Cir150AccountModel mdl : paramMdl.getCir090knAccountList()) {
                adelModel = new CirAdelModel();
                adelModel.setCadJdelYear(Integer.parseInt(paramMdl.getCir090JYear()));
                adelModel.setCadJdelMonth(Integer.parseInt(paramMdl.getCir090JMonth()));
                adelModel.setCacSid(mdl.getAccountSid());
                jdelList.add(adelModel);
            }
        }

        //送信
        int sdelKbn = NullDefault.getInt(paramMdl.getCir090SdelKbn(),
                                        GSConstCircular.CIR_AUTO_DEL_NO);
        if (sdelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            for (Cir150AccountModel mdl : paramMdl.getCir090knAccountList()) {
                adelModel = new CirAdelModel();
                adelModel.setCadSdelYear(Integer.parseInt(paramMdl.getCir090SYear()));
                adelModel.setCadSdelMonth(Integer.parseInt(paramMdl.getCir090SMonth()));
                adelModel.setCacSid(mdl.getAccountSid());
                sdelList.add(adelModel);
            }
        }

        int ddelKbn = NullDefault.getInt(paramMdl.getCir090DdelKbn(),
                                        GSConstCircular.CIR_AUTO_DEL_NO);

        if (ddelKbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
            for (Cir150AccountModel mdl : paramMdl.getCir090knAccountList()) {
                adelModel = new CirAdelModel();
                adelModel.setCadDdelYear(Integer.parseInt(paramMdl.getCir090DYear()));
                adelModel.setCadDdelMonth(Integer.parseInt(paramMdl.getCir090DMonth()));
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
        searchMdl.setUserSid(usrSid);
        searchMdl.setMaxCount(-1);
        Cir150Dao dao = new Cir150Dao(con);
        List<Cir150AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);

        return accountList;
    }

    /**
     * <br>[機  能] 選択した年月日に不正な値が入った場合に空白文字に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cir090knParamModel
     */
    public void updateIgnoreYearMonth(Cir090knParamModel paramMdl) {
        if (!ValidateUtil.isNumber(paramMdl.getCir090JYear())) {
            paramMdl.setCir090JYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir090JMonth())) {
            paramMdl.setCir090JMonth("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir090SYear())) {
            paramMdl.setCir090SYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir090SMonth())) {
            paramMdl.setCir090SMonth("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir090DYear())) {
            paramMdl.setCir090DYear("");
        }
        if (!ValidateUtil.isNumber(paramMdl.getCir090DMonth())) {
            paramMdl.setCir090DMonth("");
        }
    }
}
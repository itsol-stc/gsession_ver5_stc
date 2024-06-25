package jp.groupsession.v2.cir.cir160kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir160.Cir160Biz;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirAccountSortDao;
import jp.groupsession.v2.cir.dao.CirAccountUserDao;
import jp.groupsession.v2.cir.dao.CirAdelDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 アカウント登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir160knBiz extends Cir160Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir160knBiz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Cir160knParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {
        paramMdl.setCir160knBiko(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getCir160biko()), ""));

        _setUserCombo(con, paramMdl, reqMdl);

        int cacSid = paramMdl.getCirAccountSid();
        boolean acntUserFlg = getAcntUserFlg(con, paramMdl, cacSid, null);
        paramMdl.setCir160acntUserFlg(acntUserFlg);

        //テーマ(表示用)を設定
        CmnThemeDao themeDao = new CmnThemeDao(con);
        CmnThemeModel themeData = themeDao.select(paramMdl.getCir160theme());
        GsMessage gsMsg = new GsMessage(reqMdl);
        String themeName = gsMsg.getMessage("cmn.notset");
        if (themeData != null) {
            themeName = themeData.getCtmName();
        }
        paramMdl.setCir160knTheme(themeName);

        // ショートメール通知 プラグイン判定
        CommonBiz cmnBiz = new CommonBiz();
        PluginConfig pconfig = cmnBiz.getPluginConfigForMain(con, reqMdl);
        pconfig = cmnBiz.getPluginConfigForMain(pconfig, con);
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);
        if (smailPluginUseFlg) {
            paramMdl.setCanSmlUse(GSConst.PLUGIN_USE);
        } else {
            paramMdl.setCanSmlUse(GSConst.PLUGIN_NOT_USE);
        }
    }

    /**
     * <br>[機  能] アカウント情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     * @return CirAccountModel
     */
    public CirAccountModel entryAddressData(Connection con, Cir160knParamModel paramMdl,
            MlCountMtController mtCon, int sessionUserSid, RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        //管理者設定取得
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        CirInitModel admMdl = new CirInitModel();
        admMdl = cirBiz.getCirInitConf(sessionUserSid, con);

        boolean newData = paramMdl.getCirCmdMode() == GSConstCircular.CMDMODE_ADD;
        int accountMode = paramMdl.getCirAccountMode();

        if (paramMdl.isCir010adminUser()) {
            accountMode = GSConstCircular.ACCOUNTMODE_COMMON;
        }

        //int userKbn = paramMdl.getCir160userKbn();

        //アカウント情報の登録
        CirAccountModel accountModel = new CirAccountModel();

        accountModel.setUsrSid(sessionUserSid);
        accountModel.setCacType(GSConstCircular.CAC_TYPE_NORMAL);
//        if (accountMode == GSConstCircular.ACCOUNTMODE_COMMON
//        && userKbn == Cir160knForm.USERKBN_GROUP) {
//            accountModel.setCacType(GSConstCircular.CAC_TYPE_GROUP);
//        } else {
            accountModel.setCacType(GSConstCircular.CAC_TYPE_USER);
//        }

        accountModel.setCacName(paramMdl.getCir160name());
        accountModel.setCacTheme(paramMdl.getCir160theme());
        accountModel.setCacBiko(paramMdl.getCir160biko());
        accountModel.setCacSmlNtf(paramMdl.getCir160smlNtf());
        accountModel.setCacSmlMemo(paramMdl.getCir160smlMemo());
        accountModel.setCacSmlEdit(paramMdl.getCir160smlEdt());
        accountModel.setCacMemoKbn(paramMdl.getCir160memoKbn());
        accountModel.setCacMemoDay(paramMdl.getCir160memoPeriod());
        accountModel.setCacKouKbn(paramMdl.getCir160show());

        if (paramMdl.getCir160cirInitKbn() == GSConstCircular.CIR_INIEDIT_STYPE_ADM) {
            accountModel.setCacInitKbn(GSConstCircular.CIR_INIT_KBN_NOSET);
        } else {
            accountModel.setCacInitKbn(GSConstCircular.CIR_INIT_KBN_SET);
        }


        CirAccountDao accountDao = new CirAccountDao(con);
        CirAccountUserDao accountUserDao = new CirAccountUserDao(con);
        CirAccountSortDao accountSortDao = new CirAccountSortDao(con);
//        CirAccountDiskDao wadDao = new CirAccountDiskDao(con);

        int accountSid = -1;

        int cacSid = paramMdl.getCirAccountSid();
        boolean acntUserFlg = getAcntUserFlg(con, paramMdl, cacSid, null);

        //新規登録
        if (newData) {

            //アカウント採番取得
            int cacSaiSid = (int) mtCon.getSaibanNumber(GSConstCircular.SBNSID_CIRCULAR,
                                                      GSConstCircular.SBNSID_SUB_ACCOUNT,
                                                      sessionUserSid);

            accountModel.setCacSid(cacSaiSid);
            accountDao.insertAccount(accountModel, accountMode);

            //アカウントの並び順を登録する
//            if (accountMode == GSConstCircular.ACCOUNTMODE_NORMAL
//                    || accountMode == GSConstCircular.ACCOUNTMODE_PSNLSETTING) {
            if (!acntUserFlg) {
                accountSortDao.insertAccountSort(cacSaiSid, sessionUserSid);
                accountUserDao.insert(cacSaiSid, accountModel.getCacType(),
                                        new String[] {String.valueOf(sessionUserSid)});

            } else {
                //アカウントユーザ情報の登録

                ArrayList<String> grpSidList = new ArrayList<String>();
                ArrayList<String> usrSidList = new ArrayList<String>();
                for (String id : paramMdl.getCir160userKbnUser()) {
                    String str = NullDefault.getString(id, "-1");
                    if (str.contains(new String("G").subSequence(0, 1))) {
                        //グループ
                        grpSidList.add(str.substring(1, str.length()));
                    } else {
                        //ユーザ
                        usrSidList.add(str);
                    }
                }

                if (grpSidList != null && !grpSidList.isEmpty()) {
                    accountUserDao.insert(cacSaiSid, GSConstCircular.CAC_TYPE_GROUP,
                            (String[]) grpSidList.toArray(new String[grpSidList.size()]));

                }

                if (usrSidList != null && !usrSidList.isEmpty()) {
                    accountUserDao.insert(cacSaiSid, GSConstCircular.CAC_TYPE_USER,
                        (String[]) usrSidList.toArray(new String[usrSidList.size()]));
                    accountSortDao.insertAccountSortUsr(cacSaiSid,
                            (String[]) usrSidList.toArray(new String[usrSidList.size()]));
                }

            }

//            //ディスク使用量の新規登録
//            CirAccountDiskModel useDiskMdl = new CirAccountDiskModel();
//            useDiskMdl.setCacSid(cacSaiSid);
//            //新規登録時は使用サイズを0にセット
//            useDiskMdl.setSdsSize(0);
//            wadDao.insert(useDiskMdl);

            accountSid = cacSaiSid;

        //編集登録
        } else {

            int wacSid = paramMdl.getCirAccountSid();

            accountModel.setCacSid(wacSid);

            accountDao.updateAccount(accountModel, accountMode);

//            if (paramMdl.isCir010adminUser()) {
            if (acntUserFlg) {
                //アカウント使用者の削除
                accountUserDao.delete(wacSid);

                ArrayList<String> grpSidList = new ArrayList<String>();
                ArrayList<String> usrSidList = new ArrayList<String>();
                for (String id : paramMdl.getCir160userKbnUser()) {
                    String str = NullDefault.getString(id, "-1");
                    if (str.contains(new String("G").subSequence(0, 1))) {
                        //グループ
                        grpSidList.add(str.substring(1, str.length()));
                    } else {
                        //ユーザ
                        usrSidList.add(str);
                    }
                }

                //アカウントユーザ情報の登録
//                if (accountModel.getCacType() == GSConstCircular.CAC_TYPE_GROUP) {
//                    accountUserDao.insert(wacSid, accountModel.getCacType(),
//                            paramMdl.getCir160userKbnGroup());
//                    CmnBelongmDao belongmDao = new CmnBelongmDao(con);
//                    List<String> usrSidList = null;
//                    usrSidList = belongmDao.select(paramMdl.getCir160userKbnGroup());
//                    if (usrSidList != null && !usrSidList.isEmpty()) {
//                        usrSids = (String[]) usrSidList.toArray(new String[usrSidList.size()]);
//                    }
//
//                } else if (accountModel.getCacType() == GSConstCircular.CAC_TYPE_USER) {
//                    accountUserDao.insert(wacSid, GSConstCircular.CAC_TYPE_USER,
//                            paramMdl.getCir160userKbnUser());
//                    usrSids = paramMdl.getCir160userKbnUser();
//                } else {
//                    accountUserDao.insert(wacSid, accountModel.getCacType(),
//                                        new String[]{String.valueOf(sessionUserSid)});
//                    usrSids = new String[]{String.valueOf(sessionUserSid)};
//                }

                if (grpSidList != null && !grpSidList.isEmpty()) {
                    accountUserDao.insert(wacSid, GSConstCircular.CAC_TYPE_GROUP,
                            (String[]) grpSidList.toArray(new String[grpSidList.size()]));
                }

                if (usrSidList != null && !usrSidList.isEmpty()) {
                    accountUserDao.insert(wacSid, GSConstCircular.CAC_TYPE_USER,
                        (String[]) usrSidList.toArray(new String[usrSidList.size()]));
                    accountSortDao.delAccountSortUsr(wacSid, paramMdl.getCir160userKbnUser());
                    accountSortDao.updateAccountSortUsr(wacSid, paramMdl.getCir160userKbnUser(),
                            wacSid);
                }

            }
            accountSid = wacSid;
        }

        /************************  自動削除設定  *********************************/
        if (admMdl.getCinAutoDelKbn() == GSConstCircular.AUTO_DEL_ACCOUNT
                && accountSid != -1) {
            UDate nowDate = new UDate();

            //回覧板自動削除設定
            CirAdelModel delMdl = new CirAdelModel();

            delMdl.setCacSid(accountSid);
            delMdl.setCadUsrKbn(GSConstCircular.CIR_ADEL_USR_KBN_USER);
            delMdl.setCadDelKbn(GSConstCircular.CIR_DEL_KBN_USER_SETTING);

            int jdelkbn =
                NullDefault.getInt(
                        paramMdl.getCir160JdelKbn(),
                        GSConstCircular.CIR_AUTO_DEL_NO);

            delMdl.setCadJdelKbn(jdelkbn);

            int jdelYear = 0;
            int jdelMonth = 0;

            if (jdelkbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
                jdelYear = NullDefault.getInt(paramMdl.getCir160JYear(), 0);
                jdelMonth = NullDefault.getInt(paramMdl.getCir160JMonth(), 0);
            }

            delMdl.setCadJdelYear(jdelYear);
            delMdl.setCadJdelMonth(jdelMonth);

            int sdelkbn =
                NullDefault.getInt(
                        paramMdl.getCir160SdelKbn(),
                        GSConstCircular.CIR_AUTO_DEL_NO);

            delMdl.setCadSdelKbn(sdelkbn);

            int sdelYear = 0;
            int sdelMonth = 0;

            if (sdelkbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
                sdelYear = NullDefault.getInt(paramMdl.getCir160SYear(), 0);
                sdelMonth = NullDefault.getInt(paramMdl.getCir160SMonth(), 0);
            }

            delMdl.setCadSdelYear(sdelYear);
            delMdl.setCadSdelMonth(sdelMonth);

            int ddelkbn =
                NullDefault.getInt(
                        paramMdl.getCir160DdelKbn(),
                        GSConstCircular.CIR_AUTO_DEL_NO);

            delMdl.setCadDdelKbn(ddelkbn);

            int ddelYear = 0;
            int ddelMonth = 0;

            if (ddelkbn == GSConstCircular.CIR_AUTO_DEL_LIMIT) {
                ddelYear = NullDefault.getInt(paramMdl.getCir160DYear(), 0);
                ddelMonth = NullDefault.getInt(paramMdl.getCir160DMonth(), 0);
            }

            delMdl.setCadDdelYear(ddelYear);
            delMdl.setCadDdelMonth(ddelMonth);

            delMdl.setCadAuid(sessionUserSid);
            delMdl.setCadAdate(nowDate);
            delMdl.setCadEuid(sessionUserSid);
            delMdl.setCadEdate(nowDate);

            CirAdelDao delDao = new CirAdelDao(con);
            if (delDao.update(delMdl) == 0) {
                delDao.insert(delMdl);
            }
        }
        return accountModel;
    }



}

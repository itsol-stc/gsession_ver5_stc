package jp.groupsession.v2.api.webmail.importmail;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDiskDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.model.WmlDirectoryCountModel;
import jp.groupsession.v2.wml.model.WmlReceiveServerModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.pop3.Pop3Receive;
import jp.groupsession.v2.wml.pop3.model.Pop3ReceiveModel;
import jp.groupsession.v2.wml.util.WmlUtil;

/**
 * <br>[機  能] WEBメールのインポートを行うWEBAPIビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiImportMailBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiImportMailBiz.class);

    /**
     * <br>[機  能] インポート処理を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param impModel WEBメールインポート時に使用する情報
     * @throws Throwable インポート処理時に例外発生
     * @throws IOException 入出力時例外
     */
    public void importMsgFile(ApiImportMailModel impModel) throws Throwable, IOException {

        log__.debug("メール情報 インポート開始");
        int wacSid = Integer.parseInt(impModel.getForm().getAccountSid());
        WmlAccountDao accountDao = new WmlAccountDao(impModel.getCon());
        WmlAccountModel accountData = accountDao.select(wacSid);
        accountDao = null;

        String accountString = WmlUtil.getAccountString(accountData.getWacReceiveHost(),
                                                        accountData.getWacReceivePort(),
                                                        accountData.getWacReceiveUser());

        WmlDirectoryDao dirDao = new WmlDirectoryDao(impModel.getCon());
        long saveWdrSid = Long.parseLong(impModel.getForm().getDirectorySid());
        long dustWdrSid = dirDao.getDirSid(wacSid, GSConstWebmail.DIR_TYPE_DUST);
        long strageWdrSid = dirDao.getDirSid(wacSid,
                GSConstWebmail.DIR_TYPE_STORAGE);
        dirDao = null;

        Pop3ReceiveModel receiveMdl = new Pop3ReceiveModel();
        receiveMdl.setCon(impModel.getCon());
        receiveMdl.setMtCon(impModel.getMtCon());
        receiveMdl.setWacSid(wacSid);
        receiveMdl.setAccountString(accountString);
        receiveMdl.setAccountMailAddress(accountData.getWacAddress());
        receiveMdl.setSaveWdrSid(saveWdrSid);
        receiveMdl.setDustWdrSid(dustWdrSid);
        receiveMdl.setStrageWdrSid(strageWdrSid);

        receiveMdl.setAppRootPath(impModel.getAppRootPath());
        receiveMdl.setFileSaveDir(impModel.getTempDir());
        receiveMdl.setUserSid(impModel.getUserSid());

        WmlBiz wmlBiz = new WmlBiz();
        WmlReceiveServerModel receiveServerModel
            = wmlBiz.createReceiveServerData(impModel.getCon(),
                                            impModel.getAppRootPath(),
                                            accountData);

        MimeMessage msg = null;
        FileInputStream fis = null;
        boolean commit = false;
        try {
            fis = new FileInputStream(impModel.getImpFilePath());
            msg = new MimeMessage(Session.getDefaultInstance(new Properties()) , fis);
            String domain = impModel.getReqMdl().getDomain();

            Pop3Receive receive = new Pop3Receive();
            receive.insertMailData(domain, receiveMdl, receiveServerModel,
                                msg, impModel.getMsgResource());
            impModel.getCon().commit();
            commit = true;

        } catch (IOException e) {
            log__.error("インポートファイルの読み込みに失敗", e);
            throw e;

        } catch (Throwable e) {
            log__.error("メール情報 インポート失敗", e);
            throw e;

        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }

            if (!commit) {
                impModel.getCon().rollback();
            }
        }

        log__.debug("メール情報 インポート終了");

    }

    /**
     * <br>[機  能] アカウントのディスク使用量を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @return アカウントのディスク容量上限
     * @throws SQLException SQL実行時例外
     */
    public long getAccountUseDiskSize(Connection con, int wacSid) throws SQLException {
        WmlAccountDiskDao accountDiskDao = new WmlAccountDiskDao(con);
        return accountDiskDao.getUseDiskSize(wacSid);
    }

    /**
     * <br>[機  能] 指定したアカウントをユーザが使用可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param userSid ユーザSID
     * @return true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseAccount(Connection con, int wacSid, int userSid)
    throws SQLException {
        boolean result = false;

        WmlAccountDao accountDao = new WmlAccountDao(con);
        List<WmlAccountModel> accountList = accountDao.getAccountList(userSid);

        for (WmlAccountModel accountMdl : accountList) {
            if (accountMdl.getWacSid() == wacSid) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * <br>[機  能] 指定したディレクトリが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param directorySid ディレクトリSID
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existDirectory(RequestModel reqMdl,
                                  Connection con,
                                  int wacSid, String directorySid)
    throws SQLException {
        boolean result = false;
        long wdrSid = Long.parseLong(directorySid);

        WmlDirectoryDao dao = new WmlDirectoryDao(con);
        List<WmlDirectoryCountModel> dirList = dao.getDirectoryList(reqMdl, wacSid);
        for (WmlDirectoryCountModel dirMdl : dirList) {
            if (dirMdl.getId() == wdrSid) {
                result = true;
                break;
            }
        }

        return result;
    }
}
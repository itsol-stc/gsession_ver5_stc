package jp.groupsession.v2.api.file.addfolder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCallConfDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileCallConfModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <br>[機  能] フォルダの登録を行うWEBAPIビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileAddFolderBiz {

    /**
     * <br>[機  能] フォルダを登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param params ParamModel
     * @param userSid ユーザSID
     * @return 登録したフォルダのディレクトリSID
     * @throws Exception 実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     */
    public int registerData(
            Connection con,
            MlCountMtController cntCon,
            ApiFileAddFolderParamModel params,
            int userSid) throws Exception, IOToolsException, IOException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        int parentDirSid = NullDefault.getInt(params.getFdrParentSid(), -1);
        UDate now = new UDate();

        //親ディレクトリ情報を取得する。
        FileDirectoryModel parDirModel = dirDao.getNewDirectory(parentDirSid);

        //ディレクトリSIDを採番する。
        int fdrSid = (int) cntCon.getSaibanNumber(GSConstFile.PLUGIN_ID_FILE,
                                                GSConstFile.SBNSID_SUB_DIRECTORY,
                                                userSid);

        FileDirectoryModel newDirModel = new FileDirectoryModel();
        int parDirSid = NullDefault.getInt(params.getFdrParentSid(), 0);
        newDirModel.setFdrSid(fdrSid);
        newDirModel.setFcbSid(parDirModel.getFcbSid());
        newDirModel.setFdrLevel(parDirModel.getFdrLevel() + 1);
        newDirModel.setFdrParentSid(parDirSid);
        newDirModel.setFdrBiko(params.getFdrNote());
        newDirModel.setFdrName(params.getFdrName());
        newDirModel.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
        newDirModel.setFdrKbn(GSConstFile.DIRECTORY_FOLDER);
        newDirModel.setFdrVerKbn(GSConstFile.VERSION_KBN_OFF);
        newDirModel.setFdrVersion(GSConstFile.VERSION_KBN_OFF);
        newDirModel.setFdrAuid(userSid);
        newDirModel.setFdrAdate(now);
        newDirModel.setFdrEuid(userSid);
        newDirModel.setFdrEdate(now);
        dirDao.insert(newDirModel);

        //親ディレクトリに更新通知が設定されている場合、登録する。
        insertCallConf(parDirSid, fdrSid, con);

        FileDirectoryDao fdDao = new FileDirectoryDao(con);
        fdDao.updateAccessSid(fdrSid);
        return fdrSid;
    }

    /**
     * <br>[機  能] 親ディレクトリに更新通知が設定されている場合、登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param parDirSid 親ディレクトリSID
     * @param dirSid ディレクトリSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void insertCallConf(int parDirSid, int dirSid, Connection con)
    throws SQLException, IOToolsException {


        FileCallConfDao confDao = new FileCallConfDao(con);

        //親ディレクトリに更新通知設定しているユーザ取得
        ArrayList<FileCallConfModel> confModelList = confDao.select(parDirSid);

        FileCallConfModel confModel = null;

        if (confModelList != null && confModelList.size() > 0) {
            //更新通知設定を登録
            for (FileCallConfModel model : confModelList) {
                confModel = new FileCallConfModel();
                confModel.setFdrSid(dirSid);
                confModel.setUsrSid(model.getUsrSid());
                confDao.insert(confModel);
            }
        }

    }
}
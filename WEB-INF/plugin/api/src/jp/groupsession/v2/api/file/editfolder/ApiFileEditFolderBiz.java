package jp.groupsession.v2.api.file.editfolder;

import java.io.IOException;
import java.sql.Connection;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <br>[機  能] フォルダの登録を行うWEBAPIビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileEditFolderBiz {

    /**
     * <br>[機  能] フォルダを更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param params アクションフォーム
     * @param userSid ユーザSID
     * @return 更新件数
     * @throws Exception 実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     */
    public int registerData(
            Connection con,
            ApiFileEditFolderParamModel params,
            int userSid) throws Exception, IOToolsException, IOException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        int editDirSid = NullDefault.getInt(params.getFdrSid(), -1);
        UDate now = new UDate();

        //編集するディレクトリ情報を取得する。
        FileDirectoryModel editDirModel = dirDao.getNewDirectory(editDirSid);
        editDirModel.setFdrBiko(params.getFdrNote());
        editDirModel.setFdrName(params.getFdrName());
        editDirModel.setFdrEuid(userSid);
        editDirModel.setFdrEdate(now);
        int upCnt = dirDao.updateFolder(editDirModel);
        return upCnt;
    }
}
package jp.groupsession.v2.api.file.delete;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.model.FileFileBinModel;

/**
 * <br>[機  能] ファイルの削除を行うWEBAPIビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileDeleteBiz {

    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** DBコネクション */
    private Connection con__ = null;


    /**
     * コンストラクタ
     * @param con
     * @param reqMdl
     */
    public ApiFileDeleteBiz(RequestModel reqMdl, Connection con) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid 削除対象SID
     * @param usrSid ユーザSID
     * @param comment 操作コメント
     * @return fileName ファイル名
     * @throws SQLException SQL実行時例外
     */
    public String deleteFile(
            int fdrSid,
            int usrSid,
            String comment)
    throws SQLException {


        String[] delSids = new String[1];
        delSids[0] = String.valueOf(fdrSid);


        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        List<String> retList = null;

        comment = NullDefault.getString(comment, "");
        retList = filBiz.deleteDirectoryLogical(delSids, comment)
                .stream()
                .map(mdl -> mdl.getFdrName())
                .collect(Collectors.toList());

        String ret = "";
        if (retList.size() > 0) {
            ret = retList.get(0);
        }
        return ret;
    }

    /**
     * <br>[機  能] ファイルがロックされているか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @throws SQLException 実行例外
     * @return rockFlg ロックフラグ true:ロックされている false:ロックされていない
     */
    public boolean checkFileLock(
        int fdrSid) throws SQLException {

        boolean lockFlg = true;
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        //管理者設定ロック区分を取得する。
        int admLockKbn = filBiz.getLockKbnAdmin();
        if (admLockKbn == GSConstFile.LOCK_KBN_OFF) {
            //ロック機能が無効の場合
            return false;
        }

        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        FileFileBinModel fileBinModel = fileBinDao.getNewFile(fdrSid);

        if (fileBinModel == null) {
            //ディレクトリ情報が存在しない場合
            return lockFlg;
        }

        //ログインユーザ以外がロックしている場合
        if (fileBinModel.getFflLockKbn() == GSConstFile.LOCK_KBN_ON
                && fileBinModel.getFflLockUser() != reqMdl__.getSmodel().getUsrsid()) {
            //編集ユーザがログインユーザと異なった場合
            return lockFlg;
        }

        return false;
    }
}
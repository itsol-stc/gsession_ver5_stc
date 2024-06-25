package jp.groupsession.v2.rsv.rsv260kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvSisKbnDao;
import jp.groupsession.v2.rsv.model.RsvSisKbnModel;
import jp.groupsession.v2.rsv.rsv260.Rsv260Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 施設予約 グループ・施設一括登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv260knBiz extends Rsv260Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv260knBiz.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv260knBiz(RequestModel reqMdl, Connection con) {
        super(reqMdl, con);
    }

    /**
     * <br>[機  能] 初期表示データセット
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv260knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Rsv260knParamModel paramMdl) throws SQLException {

        String sKbnName = "";

        //施設区分名称取得
        RsvSisKbnDao kbnDao = new RsvSisKbnDao(con_);
        RsvSisKbnModel kbnParam = new RsvSisKbnModel();
        kbnParam.setRskSid(paramMdl.getRsv260SelectedSisetuKbn());
        RsvSisKbnModel ret = kbnDao.select(kbnParam);
        if (ret != null) {
            sKbnName = ret.getRskName();
        }
        paramMdl.setRsv260knSelectedSisetuName(sKbnName);

        //【権限設定を行う】の場合は管理者ユーザ一覧取得
        if (paramMdl.getRsv260GrpAdmKbn() == GSConstReserve.RSG_ADM_KBN_OK) {

            //管理者ユーザ一覧取得
            String[] saveUser = paramMdl.getSaveUser();
            if (saveUser != null && saveUser.length > 0) {
                UserGroupSelectBiz selBiz = new UserGroupSelectBiz();
                paramMdl.setRsv260knKanriUser(selBiz.getSelectedLabel(saveUser, con_));
            }
        }
    }

    /**
     * <br>[機  能] 取込みファイル名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv260knParamModel
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     */
    public void setImportFileName(Rsv260knParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //取込みCSVファイル名称セット
        String fileName = __getFileName(tempDir);
        paramMdl.setRsv260knFileName(fileName);
    }

    /**
     * <br>[機  能] 添付ファイルの名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @return String ファイル名
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    private String __getFileName(String tempDir) throws IOToolsException {
        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                ret = fMdl.getFileName();
                if (ret != null) {
                    return ret;
                }
            }
        }
        log__.debug("添付ファイルの名称 = " + ret);
        return ret;
    }
}
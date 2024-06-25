package jp.groupsession.v2.rsv.rsv180kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.rsv080.Rsv080Model;
import jp.groupsession.v2.rsv.rsv180.Rsv180Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 施設予約 施設インポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv180knBiz extends Rsv180Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv180knBiz.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv180knBiz(RequestModel reqMdl, Connection con) {
        super(reqMdl, con);
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

    /**
     * <br>[機  能] グループ情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv180knParamModel
     * @throws SQLException SQL実行例外
     */
    public void setGroupData(Rsv180knParamModel paramMdl) throws SQLException {

        //施設グループ情報を取得
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
        Rsv080Model grpMdl = grpDao.getGrpBaseData(paramMdl.getRsv080EditGrpSid());
        paramMdl.setRsv080RsgName(grpMdl.getRsgName());
        paramMdl.setRsv080RskName(grpMdl.getRskName());
    }

    /**
     * <br>[機  能] 取込みファイル名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv180knParamModel
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     */
    public void setImportFileName(Rsv180knParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //取込みCSVファイル名称セット
        String fileName = __getFileName(tempDir);
        paramMdl.setRsv180knFileName(fileName);
    }

    /**
     * <br>[機  能] 取込みユーザ名をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv180knParamModel
     * @throws SQLException SQL実行例外
     */
    public void setImportUserName(Rsv180knParamModel paramMdl)
        throws SQLException {

        //取込みユーザ名セット
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        paramMdl.setRsv180knUserName(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());
    }

    /**
     * <br>[機  能] セッションユーザSID取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return userSid セッションユーザSID
     */
    public int getSessionUserSId() {

        //セッションユーザSID取得
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        return usrMdl.getUsrsid();
    }

    /**
     * <br>[機  能] 取込み先グループの施設区分取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv180knParamModel
     * @return userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public int getRskSid(Rsv180knParamModel paramMdl) throws SQLException {

        int rskSid = -1;
        //取込み先グループの施設区分取得
        RsvSisGrpDao dao = new RsvSisGrpDao(con_);
        RsvSisGrpModel ret = dao.select(paramMdl.getRsv080EditGrpSid());
        if (ret != null) {
            rskSid = ret.getRskSid();
        }
        return rskSid;
    }
}
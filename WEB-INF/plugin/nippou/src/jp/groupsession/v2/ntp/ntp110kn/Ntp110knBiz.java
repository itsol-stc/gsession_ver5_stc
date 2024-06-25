package jp.groupsession.v2.ntp.ntp110kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 インポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp110knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp110knBiz.class);
    /** リクエスモデル */
    public RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Ntp110knBiz(Connection con, RequestModel reqMdl) {
        reqMdl_ = reqMdl;
        con_ = con;
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
     * <br>[機  能] 取込みファイル名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp110knParamModel
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     */
    public void setImportFileName(Ntp110knParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //取込みCSVファイル名称セット
        String fileName = __getFileName(tempDir);
        paramMdl.setNtp110knFileName(fileName);
    }
    /**
     * <br>[機  能] 取込みファイル名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     * @return String 保存しているファイル名
     */
    public String getImportFileName(String tempDir)
        throws SQLException, IOToolsException {

        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_SAVEFILE)) {
                    continue;
                }
                ret = fileName.substring(0, 11);
            }
        }
        return ret;
    }
    /**
     * <br>[機  能] 登録対象の名称を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Ntp110knParamModel
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     */
    public void setImportTargetName(Ntp110knParamModel paramMdl,
                                    String userSid)
        throws SQLException, IOToolsException {

        String impTargetName = "";
        String userId = NullDefault.getString(userSid, "-1");
        log__.debug("userId==>" + userId);
        if (userId.equals(GSConstNippou.USER_NOT_SELECT)) {
            //グループ名取得
            CmnGroupmDao dao = new CmnGroupmDao(con_);
            CmnGroupmModel model = dao.select(NullDefault.getInt(userSid, -1));
            if (model != null) {
                impTargetName = model.getGrpName();
            }
        } else {
            //ユーザ名取得
            CmnUsrmInfDao dao = new CmnUsrmInfDao(con_);
            CmnUsrmInfModel model = dao.select(NullDefault.getInt(userSid, -1));
            if (model != null) {
                impTargetName = model.getUsiSei() + " " + model.getUsiMei();
            }
        }
        paramMdl.setImpTargetName(impTargetName);
    }
}
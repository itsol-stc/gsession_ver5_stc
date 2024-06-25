package jp.groupsession.v2.tcd.tcd150kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード CSVインポート確認画面で使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150knBiz {

    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param dirId テンポラリディレクトリID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 取込みファイル操作時例外
     */
    protected void _setInitData(
            Tcd150knParamModel paramMdl,
            RequestModel reqMdl,
            Connection con,
            String dirId) throws SQLException, IOToolsException {
        
        BaseUserModel usModel = reqMdl.getSmodel();
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        int usKbn = tcBiz.getUserKbn(con, usModel);
        paramMdl.setUsrKbn(String.valueOf(usKbn));
        if (usKbn == GSConstTimecard.USER_KBN_ADMIN || usKbn == GSConstTimecard.USER_KBN_GRPADM) {
            //対象ユーザ名の設定
            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
            int usrSid = NullDefault.getInt(paramMdl.getTcd150SltUser(), -1);
            CmnUsrmInfModel usrInfMdl = cuiDao.select(usrSid);
            paramMdl.setTcd150knSltUserName(usrInfMdl.getUsiName());
            //ユーザの有効無効を設定
            CmnUsrmDao usrDao = new CmnUsrmDao(con);
            CmnUsrmModel usrMdl = usrDao.select(usrSid);  
            paramMdl.setUserUkoFLg(usrMdl.getUsrUkoFlg());
        }
                
        //テンポラリディレクトリパスを取得
        String tempDir = _getTempDir(reqMdl, dirId);
        //ファイル名の取得
        String fileName = __getFileName(tempDir);
        paramMdl.setFileName(fileName);
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
        return ret;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param dirId テンポラリディレクトリID
     * @return テンポラリディレクトリ
     */
    protected String _getTempDir(RequestModel reqMdl, String dirId) {
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(reqMdl,
                GSConstTimecard.PLUGIN_ID_TIMECARD, dirId);
        
        return tempDir;
    }
    
    /**
     * <br>[機  能] 取込みファイル名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリファイルパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException CSVファイル取扱い時例外
     * @return String 保存しているファイル名
     */
    protected String _getImportFileName(String tempDir)
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
}

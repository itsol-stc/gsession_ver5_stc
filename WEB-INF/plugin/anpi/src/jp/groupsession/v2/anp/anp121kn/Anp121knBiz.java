package jp.groupsession.v2.anp.anp121kn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;


/**
 * <br>[機  能] 管理者設定・緊急連絡先インポート確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp121knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp121knBiz.class);


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Anp121knBiz() {

    }

    /**
     * <br>[機  能] 連絡先を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param  anp121knModel    パラメータモデル
     * @param  reqMdl リクエストモデル
     * @param  con     DBコネクション
     * @param  tempDir テンポラリディレクトリパス
     * @param  dirId   ディレクトリＩＤ
     * @return long    取り込み件数
     * @throws Exception 実行例外
     */
    public long doInport(Anp121knParamModel anp121knModel,
            RequestModel reqMdl,
                         Connection con,
                         String tempDir,
                         String dirId)
                  throws Exception {

        log__.debug("///緊急連絡先取り込みSTART///");

        //セッションユーザSIDを取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = usModel.getUsrsid();
        long num = 0;

        try {
            Anp121knCsvImport csvImport = new Anp121knCsvImport(con, usrSid);
            log__.debug("取り込みファイル名" + tempDir + getFileName(tempDir, 2));
            ArrayList<AnpPriConfModel> impList
                = csvImport.importCsv(tempDir + getFileName(tempDir, 2));
            if (impList != null) {
                num = impList.size();
            }

        } catch (Exception e) {
            log__.error("ユーザCSVの取り込みに失敗しました。" + e);
            throw e;

        } finally {
            //テンポラリディレクトリのファイル削除を行う
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(reqMdl,
                    GSConstAnpi.PLUGIN_ID, dirId);
        }

        return num;
    }

    /**
     * <br>[機  能] 添付ファイルの名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @param mode    添付ファイル名取得モード（1：ファイル名　2：保存ファイル名）
     * @return String ファイル名
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    public String getFileName(String tempDir, int mode) throws IOToolsException {

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
                if (mode == 1) {
                    //ファイル名
                    ret = fMdl.getFileName();
                } else if (mode == 2) {
                    //テンポラリディレクトリ保存ファイル名
                    ret = fMdl.getSaveFileName();
                }

                if (ret != null) {
                    return ret;
                }
            }
        }
        return ret;
    }

}

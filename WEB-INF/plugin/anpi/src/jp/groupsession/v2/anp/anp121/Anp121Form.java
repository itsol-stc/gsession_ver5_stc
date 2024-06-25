package jp.groupsession.v2.anp.anp121;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.anp.anp110.Anp110Form;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 管理者設定・緊急連絡先インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp121Form extends Anp110Form {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp121Form.class);

    /** 添付ファイル(コンボで選択中) */
    private String[] anp121selectFile__ = null;

    /** 取り込みファイルコンボ */
    private List<LabelValueBean> anp121fileCombo__ = null;

    /**
     * <p>添付ファイルを取得します
     * @return anp121selectFile
     */
    public String[] getAnp121selectFile() {
        return anp121selectFile__;
    }

    /**
     * <p>添付ファイルを設定します
     * @param anp121selectFile セットする anp121selectFile
     */
    public void setAnp121selectFile(String[] anp121selectFile) {
        anp121selectFile__ = anp121selectFile;
    }

    /**
     * <p>取り込みファイルコンボを取得します
     * @return anp121fileCombo
     */
    public List<LabelValueBean> getAnp121fileCombo() {
        return anp121fileCombo__;
    }

    /**
     * <p>取り込みファイルコンボを設定します
     * @param anp121fileCombo セットする anp121fileCombo
     */
    public void setAnp121fileCombo(List<LabelValueBean> anp121fileCombo) {
        anp121fileCombo__ = anp121fileCombo;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト TODO各プラグインリファクタリング時に削除
     * @param reqMdl リクエストモデル
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateAnp121(ActionMapping map,
                                       HttpServletRequest req,
                                       RequestModel reqMdl,
                                       String tempDir,
                                       Connection con)
                                throws IOToolsException, SQLException, Exception {

        log__.debug("///ファイルチェックSTART///");
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";

        //インポートファイルチェック
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //取り込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        //ファイル存在チェック
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text", textCaptureFile);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");

        } else {

            for (int i = 0; i < fileList.size(); i++) {
                String fileName = fileList.get(i);
                //ファイル形式チェック
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    //オブジェクトファイル以外
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                saveFileName = fMdl.getSaveFileName();
                baseFileName = fMdl.getFileName();
            }

            //CSV形式のファイル
            String textCsvFile = gsMsg.getMessage("cmn.csv.file.format");
            boolean csvError = false;
            //選択ファイル数チェック
            if (fileList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage("error.input.notfound.file", textCaptureFile);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage("error.select.required.text", textCsvFile);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }

            if (!csvError) {
                String fullPath = tempDir + saveFileName;
                log__.debug("保存ファイルFULLPATH==" + fullPath);
                Anp121CsvCheck csvCheck = new Anp121CsvCheck(errors, con, req, reqMdl);

                //CSV有効データ存在チェック
                csvCheck.isCsvDataOk(fullPath);
            }
        }
        log__.debug("///ファイルチェックEND///");
        return errors;
    }

}
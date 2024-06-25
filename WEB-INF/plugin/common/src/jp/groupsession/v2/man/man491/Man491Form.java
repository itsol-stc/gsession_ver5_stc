package jp.groupsession.v2.man.man491;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.AbstractForm;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] マイカレンダーインポート画面 Form
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man491Form extends AbstractForm {
    /** テンポラリファイル選択*/
    private String[] man491selectFiles__;

    /** 表示用ファイル名リスト*/
    private List<LabelValueBean> man491FileLabelList__;

    /**
     * <p>man491selectFiles を取得します。
     * @return man491selectFiles
     * @see jp.groupsession.v2.man.man491.Man491Form#man491selectFiles__
     */
    public String[] getMan491selectFiles() {
        return man491selectFiles__;
    }

    /**
     * <p>man491selectFiles をセットします。
     * @param man491selectFiles man491selectFiles
     * @see jp.groupsession.v2.man.man491.Man491Form#man491selectFiles__
     */
    public void setMan491selectFiles(String[] man491selectFiles) {
        man491selectFiles__ = man491selectFiles;
    }

    /**
     * <p>man491FileLabelList を取得します。
     * @return man491FileLabelList
     * @see jp.groupsession.v2.man.man491.Man491Form#man491FileLabelList__
     */
    public List<LabelValueBean> getMan491FileLabelList() {
        return man491FileLabelList__;
    }

    /**
     * <p>man491FileLabelList をセットします。
     * @param man491FileLabelList man491FileLabelList
     * @see jp.groupsession.v2.man.man491.Man491Form#man491FileLabelList__
     */
    public void setMan491FileLabelList(List<LabelValueBean> man491FileLabelList) {
        man491FileLabelList__ = man491FileLabelList;
    }
    /**
     *
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param tempDir テンポラリディレクトリ
     * @param con コネクション
     * @return 入力チェックエラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
            String tempDir, Connection con) throws IOToolsException, SQLException, Exception {

        // TODO 自動生成されたメソッド・スタブ
        ActionErrors errors = new ActionErrors();

        //インポートファイルチェック
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text", textCaptureFile);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        } else {
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
                saveFileName = fMdl.getSaveFileName();
                baseFileName = fMdl.getFileName();
            }

            //CSV形式のファイル
            String textCsvFile = gsMsg.getMessage("cmn.csv.file.format");
            boolean csvError = false;
            //複数選択エラー
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

                CybMycalCsvImport csvCheck = new CybMycalCsvImport(errors,
                                                                   reqMdl,
                                                                   con);
                //CSVチェック
                csvCheck.isCsvDataOk(fullPath);
            }
        }

        return errors;
    }


}

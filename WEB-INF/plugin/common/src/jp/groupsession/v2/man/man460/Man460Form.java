package jp.groupsession.v2.man.man460;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.man440.Man440Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] サイボウズLiveデータ移行 イベントインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man460Form extends Man440Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man460Form.class);

    //非表示項目
    /** プラグインID */
    private String man460pluginId__ = GSConst.PLUGINID_MAIN;

    /** 添付ファイル(コンボで選択中) */
    private String[] man460selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man460FileLabelList__ = null;
    /** グループ名 */
    private String man460grpName__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
            String tempDir, Connection con) throws IOToolsException, SQLException, Exception {

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
                log__.debug("FULLPATH==" + fullPath);

                CybEventCsvImport csvCheck = new CybEventCsvImport(errors,
                                                                   reqMdl,
                                                                   con,
                                                                   this.getMan440GrpSid());
                //CSVチェック
                csvCheck.isCsvDataOk(fullPath);
            }
        }

        return errors;
    }

    /**
     * <p>man460FileLabelList を取得します。
     * @return man460FileLabelList
     */
    public ArrayList<LabelValueBean> getMan460FileLabelList() {
        return man460FileLabelList__;
    }

    /**
     * <p>man460FileLabelList をセットします。
     * @param man460FileLabelList man460FileLabelList
     */
    public void setMan460FileLabelList(ArrayList<LabelValueBean> man460FileLabelList) {
        man460FileLabelList__ = man460FileLabelList;
    }

    /**
     * <p>man460pluginId を取得します。
     * @return man460pluginId
     */
    public String getMan460pluginId() {
        return man460pluginId__;
    }

    /**
     * <p>man460pluginId をセットします。
     * @param man460pluginId man460pluginId
     */
    public void setMan460pluginId(String man460pluginId) {
        man460pluginId__ = man460pluginId;
    }

    /**
     * <p>man460selectFiles を取得します。
     * @return man460selectFiles
     */
    public String[] getMan460selectFiles() {
        return man460selectFiles__;
    }

    /**
     * <p>man460selectFiles をセットします。
     * @param man460selectFiles man460selectFiles
     */
    public void setMan460selectFiles(String[] man460selectFiles) {
        man460selectFiles__ = man460selectFiles;
    }

    /**
     * <p>man460grpName を取得します。
     * @return man460grpName
     */
    public String getMan460grpName() {
        return man460grpName__;
    }

    /**
     * <p>man460grpName をセットします。
     * @param man460grpName man460grpName
     */
    public void setMan460grpName(String man460grpName) {
        man460grpName__ = man460grpName;
    }
}

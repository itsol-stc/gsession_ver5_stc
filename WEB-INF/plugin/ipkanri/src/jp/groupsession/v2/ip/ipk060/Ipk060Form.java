package jp.groupsession.v2.ip.ipk060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.ipk040.Ipk040Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] IP管理 IPアドレスインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk060Form extends Ipk040Form {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk060Form.class);
    /** 添付ファイル */
    private String[] ipk060Files__ = null;
    /** ファイルコンボ */
    private List < LabelValueBean > ipk060FileLabelList__ = null;
    /** インポートモード */
    private String importMode__ = IpkConst.IMPORT_MODE_TUIKA;

    /**
     * <p>importMode を取得します。
     * @return importMode
     */
    public String getImportMode() {
        return importMode__;
    }
    /**
     * <p>importMode をセットします。
     * @param importMode importMode
     */
    public void setImportMode(String importMode) {
        importMode__ = importMode;
    }
    /**
     * <p>ipk060FileLabelList を取得します。
     * @return ipk060FileLabelList
     */
    public List<LabelValueBean> getIpk060FileLabelList() {
        return ipk060FileLabelList__;
    }
    /**
     * <p>ipk060FileLabelList をセットします。
     * @param ipk060FileLabelList ipk060FileLabelList
     */
    public void setIpk060FileLabelList(List<LabelValueBean> ipk060FileLabelList) {
        ipk060FileLabelList__ = ipk060FileLabelList;
    }
    /**
     * <p>ipk060Files を取得します。
     * @return ipk060Files
     */
    public String[] getIpk060Files() {
        return ipk060Files__;
    }
    /**
     * <p>ipk060Files をセットします。
     * @param ipk060Files ipk060Files
     */
    public void setIpk060Files(String[] ipk060Files) {
        ipk060Files__ = ipk060Files;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @param netSid ネットワークSID
     * @param mode インポートモード
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
            String tempDir, Connection con, int netSid, String mode)
    throws IOToolsException, SQLException, Exception {

        log__.debug("=== 入力チェック ===");
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textSelectFile = gsMsg.getMessage("cmn.capture.file");
        String textCsvFile = gsMsg.getMessage("cmn.csv.file.format");

        //インポートファイルチェック
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text", textSelectFile);
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
            boolean csvError = false;
            //複数選択エラー
            if (fileList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage("error.input.notfound.file", textSelectFile);
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
                ArrayList<String> ipadListCsv = new ArrayList<String>();
                IpkCsvCheck csvCheck
                    = new IpkCsvCheck(errors, con, netSid, mode, ipadListCsv, reqMdl);
                //CSVチェック
                csvCheck.isCsvDataOk(fullPath);
            }
        }
        return errors;
    }
}
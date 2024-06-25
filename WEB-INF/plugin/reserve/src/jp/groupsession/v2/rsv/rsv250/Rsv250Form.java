package jp.groupsession.v2.rsv.rsv250;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.rsv040.Rsv040Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv250Form extends Rsv040Form {

    /** 添付ファイル(コンボで選択中) */
    private String[] rsv250SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> rsv250FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエストモデル
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(ActionMapping map,
                                       RequestModel reqMdl,
                                       String tempDir,
                                       Connection con)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        gsMsg.getMessage("cmn.capture.file"));
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
                    new ActionMessage(
                            "error.input.notfound.file",
                            gsMsg.getMessage("cmn.capture.file"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.select.required.text",
                                gsMsg.getMessage("cmn.csv.file.format"));
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }


            String fullPath = tempDir + saveFileName;
            RsvImportCheck csvCheck = new RsvImportCheck(errors, con, reqMdl);
            //CSVチェック
            if (errors.isEmpty() && csvCheck.isCsvDataOk(fullPath)) {
                ActionMessage msg =
                    new ActionMessage("error.format.impfile");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.format.impfile");
                csvError = true;

            }

            //有効データ数
            setImpDataCnt(csvCheck.getCount());
            if (!csvError && getImpDataCnt() <= 0) {
                ActionMessage msg =
                    new ActionMessage("error.nodata.impfile");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.nodata.impfile");
            }

        }

        return errors;
    }

    /**
     * @return impDataCnt
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }

    /**
     * @param impDataCnt 設定する impDataCnt
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }

    /**
     * @return rsv250FileLabelList
     */
    public ArrayList<LabelValueBean> getRsv250FileLabelList() {
        return rsv250FileLabelList__;
    }

    /**
     * @param rsv250FileLabelList 設定する rsv250FileLabelList
     */
    public void setRsv250FileLabelList(ArrayList<LabelValueBean> rsv250FileLabelList) {
        rsv250FileLabelList__ = rsv250FileLabelList;
    }

    /**
     * @return rsv250SelectFiles
     */
    public String[] getRsv250SelectFiles() {
        return rsv250SelectFiles__;
    }

    /**
     * @param rsv250SelectFiles 設定する rsv250SelectFiles
     */
    public void setRsv250SelectFiles(String[] rsv250SelectFiles) {
        rsv250SelectFiles__ = rsv250SelectFiles;
    }
}
package jp.groupsession.v2.tcd.tcd210;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.tcd190.Tcd190Form;

/**
 * <br>[機  能] タイムカード 有休日数インポート画面のフォームクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210Form extends Tcd190Form {

    /** 添付用セレクトボックス */
    private String tcd210TenpuSelect__ = null;

    /** 取込みファイル件数 */
    private int impDataCnt__;
    
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> tcd210FileLabelList__ = null;

    /**
     * <p>tcd210TenpuSelect を取得します。
     * @return tcd210TenpuSelect
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210Form#tcd210TenpuSelect__
     */
    public String getTcd210TenpuSelect() {
        return tcd210TenpuSelect__;
    }

    /**
     * <p>tcd210TenpuSelect をセットします。
     * @param tcd210TenpuSelect tcd210TenpuSelect
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210Form#tcd210TenpuSelect__
     */
    public void setTcd210TenpuSelect(String tcd210TenpuSelect) {
        tcd210TenpuSelect__ = tcd210TenpuSelect;
    }
    
    /**
     * <p>impDataCnt を取得します。
     * @return impDataCnt
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210Form#impDataCnt__
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }

    /**
     * <p>impDataCnt をセットします。
     * @param impDataCnt impDataCnt
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210Form#impDataCnt__
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }
    
    /**
     * <p>tcd210FileLabelList を取得します。
     * @return tcd210FileLabelList
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210Form#tcd210FileLabelList__
     */
    public ArrayList<LabelValueBean> getTcd210FileLabelList() {
        return tcd210FileLabelList__;
    }

    /**
     * <p>tcd210FileLabelList をセットします。
     * @param tcd210FileLabelList tcd210FileLabelList
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210Form#tcd210FileLabelList__
     */
    public void setTcd210FileLabelList(
            ArrayList<LabelValueBean> tcd210FileLabelList) {
        tcd210FileLabelList__ = tcd210FileLabelList;
    }
    
    /**
     * <br>[機  能] CSVファイルのフォーマットが正しいかをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(
            ActionMapping map,
            RequestModel reqMdl,
            String tempDir,
            Connection con) throws Exception {
        
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        //CSV形式のファイル
        String textCsvFileFormat = gsMsg.getMessage("cmn.csv.file.format");
        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage(
                        "error.select.required.text",
                        textCaptureFile);
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
                            textCaptureFile);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.select.required.text",
                                textCsvFileFormat);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }

            int userSid = reqMdl.getSmodel().getUsrsid();

            String fullPath = tempDir + saveFileName;
            Tcd210ImportCheck csvCheck = new Tcd210ImportCheck(
                    errors, con, reqMdl, userSid);
            
            //CSVチェック
            if (errors.isEmpty() && csvCheck._isCsvDataOk(fullPath)) {
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
}

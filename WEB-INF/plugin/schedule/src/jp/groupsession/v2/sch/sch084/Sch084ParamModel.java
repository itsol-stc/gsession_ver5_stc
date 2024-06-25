package jp.groupsession.v2.sch.sch084;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.sch100.Sch100ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] スケジュール 管理者設定 スケジュールインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch084ParamModel extends Sch100ParamModel {

    /** 添付ファイル(コンボで選択中) */
    private String[] sch084SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> sch084FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;
    /** 重複登録チェックフラグ */
    private String sch084repeatCheckFlg__ = "0";



    /**
     * <p>impDataCnt を取得します。
     * @return impDataCnt
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }



    /**
     * <p>impDataCnt をセットします。
     * @param impDataCnt impDataCnt
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }



    /**
     * <p>sch084FileLabelList を取得します。
     * @return sch084FileLabelList
     */
    public ArrayList<LabelValueBean> getSch084FileLabelList() {
        return sch084FileLabelList__;
    }



    /**
     * <p>sch084FileLabelList をセットします。
     * @param sch084FileLabelList sch084FileLabelList
     */
    public void setSch084FileLabelList(ArrayList<LabelValueBean> sch084FileLabelList) {
        sch084FileLabelList__ = sch084FileLabelList;
    }



    /**
     * <p>sch084SelectFiles を取得します。
     * @return sch084SelectFiles
     */
    public String[] getSch084SelectFiles() {
        return sch084SelectFiles__;
    }



    /**
     * <p>sch084SelectFiles をセットします。
     * @param sch084SelectFiles sch084SelectFiles
     */
    public void setSch084SelectFiles(String[] sch084SelectFiles) {
        sch084SelectFiles__ = sch084SelectFiles;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
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
        //CSV形式のファイル
        String textCsvFileFormat = gsMsg.getMessage("cmn.csv.file.format");
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

            boolean checkFlg = isRepeatCheck();

            String fullPath = tempDir + saveFileName;
            SchImportCheck csvCheck = new SchImportCheck(errors, con, reqMdl, checkFlg);
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
     * <br>[機  能] 重複登録チェックを行うか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return 重複登録チェックフラグ
     */
    public boolean isRepeatCheck() {

        boolean checkFlg = false;
        String check = NullDefault.getString(sch084repeatCheckFlg__, "0");

        if (check.equals("0")) {
            checkFlg = true;
        }

        return checkFlg;
    }


    /**
     * <p>sch084repeatCheckFlg__ を取得します。
     * @return sch084repeatCheckFlg__
     */
    public String getSch084repeatCheckFlg() {
        return sch084repeatCheckFlg__;
    }



    /**
     * <p>sch084repeatCheckFlg__ をセットします。
     * @param sch084repeatCheckFlg sch084repeatCheckFlg__
     */
    public void setSch084repeatCheckFlg(String sch084repeatCheckFlg) {
        sch084repeatCheckFlg__ = sch084repeatCheckFlg;
    }
}
package jp.groupsession.v2.tcd.tcd180;

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
import jp.groupsession.v2.tcd.tcd030.Tcd030Form;

/**
 * <br>[機  能] タイムカード 勤務表フォーマット登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd180Form extends Tcd030Form {

    /** ファイルコンボ */
    private ArrayList<LabelValueBean> tcd180FileLabelList__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] tcd180TenpSelect__ = null;
    /** フォーマット登録フラグ*/
    private int tcd180FormatExistFlg__ = 0;
    /** ファイル名 */
    private String tcd180FileName__;
    /** ファイル保存名 */
    private String tcd180FileSaveName__;
    /** バイナリSID */
    private Long tcd180BinSid__ = Long.valueOf(0);
    /** フォーマット使用*/
    private int tcd180Use__ = 0;
    /** フォーマット表示フラグ*/
    private int tcd180FormatDisp__ = 0;

    /** 初期表示*/
    private boolean tcd180InitFlg__ = false;

    /** TempDirId*/
    private String tempDirId__ = null;

    /**
     * <br>[機  能] ファイルのフォーマットが正しいかをチェックする
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

        if (tcd180Use__ != 0) {
          //テンポラリディレクトリにあるファイル名称を取得
            List<String> fileList = IOTools.getFileNames(tempDir);
            //CSV形式のファイル
            String textCsvFileFormat = gsMsg.getMessage("tcd.205");
            String baseFileName = "";
            String eprefix = "inputFile.";
            //取込みファイル
            String textCaptureFile = gsMsg.getMessage("tcd.204");
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
                    baseFileName = fMdl.getFileName();
                }

                //複数選択エラー
                if (fileList.size() > 2) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.input.notfound.file",
                                textCaptureFile);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                } else {
                    //拡張子チェック
                    String strExt = StringUtil.getExtension(baseFileName);
                    if (strExt == null || !strExt.toUpperCase().equals(".XLSX")) {
                        ActionMessage msg =
                            new ActionMessage(
                                    "error.select.required.text",
                                    textCsvFileFormat);
                        StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    }
                }
            }
        }
        return errors;
    }

    /**
     * <p>tcd180FileLabelList を取得します。
     * @return tcd180FileLabelList
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180FileLabelList__
     */
    public ArrayList<LabelValueBean> getTcd180FileLabelList() {
        return tcd180FileLabelList__;
    }

    /**
     * <p>tcd180FileLabelList をセットします。
     * @param tcd180FileLabelList tcd180FileLabelList
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180FileLabelList__
     */
    public void setTcd180FileLabelList(
            ArrayList<LabelValueBean> tcd180FileLabelList) {
        tcd180FileLabelList__ = tcd180FileLabelList;
    }

    /**
     * <p>tcd180TenpSelect を取得します。
     * @return tcd180TenpSelect
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180TenpSelect__
     */
    public String[] getTcd180TenpSelect() {
        return tcd180TenpSelect__;
    }

    /**
     * <p>tcd180TenpSelect をセットします。
     * @param tcd180TenpSelect tcd180TenpSelect
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180TenpSelect__
     */
    public void setTcd180TenpSelect(String[] tcd180TenpSelect) {
        tcd180TenpSelect__ = tcd180TenpSelect;
    }

    /**
     * <p>tcd180FormatExistFlg を取得します。
     * @return tcd180FormatExistFlg
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180FormatExistFlg__
     */
    public int getTcd180FormatExistFlg() {
        return tcd180FormatExistFlg__;
    }

    /**
     * <p>tcd180FormatExistFlg をセットします。
     * @param tcd180FormatExistFlg tcd180FormatExistFlg
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180FormatExistFlg__
     */
    public void setTcd180FormatExistFlg(int tcd180FormatExistFlg) {
        tcd180FormatExistFlg__ = tcd180FormatExistFlg;
    }

    /**
     * <p>tcd180FileName を取得します。
     * @return tcd180FileName
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180FileName__
     */
    public String getTcd180FileName() {
        return tcd180FileName__;
    }

    /**
     * <p>tcd180FileName をセットします。
     * @param tcd180FileName tcd180FileName
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180FileName__
     */
    public void setTcd180FileName(String tcd180FileName) {
        tcd180FileName__ = tcd180FileName;
    }

    /**
     * <p>tcd180FileSaveName を取得します。
     * @return tcd180FileSaveName
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180FileSaveName__
     */
    public String getTcd180FileSaveName() {
        return tcd180FileSaveName__;
    }

    /**
     * <p>tcd180FileSaveName をセットします。
     * @param tcd180FileSaveName tcd180FileSaveName
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180FileSaveName__
     */
    public void setTcd180FileSaveName(String tcd180FileSaveName) {
        tcd180FileSaveName__ = tcd180FileSaveName;
    }

    /**
     * <p>tcd180BinSid を取得します。
     * @return tcd180BinSid
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180BinSid__
     */
    public Long getTcd180BinSid() {
        return tcd180BinSid__;
    }

    /**
     * <p>tcd180BinSid をセットします。
     * @param tcd180BinSid tcd180BinSid
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180BinSid__
     */
    public void setTcd180BinSid(Long tcd180BinSid) {
        tcd180BinSid__ = tcd180BinSid;
    }

    /**
     * <p>tempDirId を取得します。
     * @return tempDirId
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tempDirId__
     */
    public String getTempDirId() {
        return tempDirId__;
    }

    /**
     * <p>tempDirId をセットします。
     * @param tempDirId tempDirId
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tempDirId__
     */
    public void setTempDirId(String tempDirId) {
        tempDirId__ = tempDirId;
    }

    /**
     * <p>tcd180Use を取得します。
     * @return tcd180Use
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180Use__
     */
    public int getTcd180Use() {
        return tcd180Use__;
    }

    /**
     * <p>tcd180Use をセットします。
     * @param tcd180Use tcd180Use
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180Use__
     */
    public void setTcd180Use(int tcd180Use) {
        tcd180Use__ = tcd180Use;
    }

    /**
     * <p>tcd180InitFlg を取得します。
     * @return tcd180InitFlg
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180InitFlg__
     */
    public boolean isTcd180InitFlg() {
        return tcd180InitFlg__;
    }

    /**
     * <p>tcd180InitFlg をセットします。
     * @param tcd180InitFlg tcd180InitFlg
     * @see jp.groupsession.v2.tcd.tcd180.Tcd180Form#tcd180InitFlg__
     */
    public void setTcd180InitFlg(boolean tcd180InitFlg) {
        tcd180InitFlg__ = tcd180InitFlg;
    }

    public int getTcd180FormatDisp() {
        return tcd180FormatDisp__;
    }

    public void setTcd180FormatDisp(int tcd180FormatDisp) {
        tcd180FormatDisp__ = tcd180FormatDisp;
    }



}

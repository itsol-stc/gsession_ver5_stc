package jp.groupsession.v2.ntp.ntp132;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp130.Ntp130ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 商品インポート画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp132ParamModel extends Ntp130ParamModel {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp132ParamModel.class);

    //非表示項目
    /** プラグインID */
    private String ntp132pluginId__ = GSConstNippou.PLUGIN_ID_NIPPOU;
    /** 添付ファイル(コンボで選択中) */
    private String[] ntp132selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> ntp132FileLabelList__ = null;
    /** カテゴリSID */
    private int ntp132CatSid__ = 1;
    /** カテゴリリスト一覧 */
    private List<LabelValueBean> ntp132CategoryList__ = null;
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(ActionMapping map, HttpServletRequest req,
            String tempDir, Connection con) throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();
        //インポートファイルチェック
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        GsMessage gsMsg = new GsMessage();
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage(req, "cmn.capture.file");
        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
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
            String textCsvFile = gsMsg.getMessage(req, "cmn.csv.file.format");
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
                ShohinCsvCheck csvCheck = new ShohinCsvCheck(errors, con, req);
                //CSVチェック
                csvCheck.isCsvDataOk(fullPath, req);
            }
        }

        return errors;
    }

    /**
     * <p>ntp132pluginId を取得します。
     * @return ntp132pluginId
     */
    public String getNtp132pluginId() {
        return ntp132pluginId__;
    }

    /**
     * <p>ntp132pluginId をセットします。
     * @param ntp132pluginId ntp132pluginId
     */
    public void setNtp132pluginId(String ntp132pluginId) {
        ntp132pluginId__ = ntp132pluginId;
    }

    /**
     * <p>ntp132selectFiles を取得します。
     * @return ntp132selectFiles
     */
    public String[] getNtp132selectFiles() {
        return ntp132selectFiles__;
    }

    /**
     * <p>ntp132selectFiles をセットします。
     * @param ntp132selectFiles ntp132selectFiles
     */
    public void setNtp132selectFiles(String[] ntp132selectFiles) {
        ntp132selectFiles__ = ntp132selectFiles;
    }

    /**
     * <p>ntp132FileLabelList を取得します。
     * @return ntp132FileLabelList
     */
    public ArrayList<LabelValueBean> getNtp132FileLabelList() {
        return ntp132FileLabelList__;
    }

    /**
     * <p>ntp132FileLabelList をセットします。
     * @param ntp132FileLabelList ntp132FileLabelList
     */
    public void setNtp132FileLabelList(ArrayList<LabelValueBean> ntp132FileLabelList) {
        ntp132FileLabelList__ = ntp132FileLabelList;
    }

    /**
     * <p>ntp132CategoryList を取得します。
     * @return ntp132CategoryList
     */
    public List<LabelValueBean> getNtp132CategoryList() {
        return ntp132CategoryList__;
    }

    /**
     * <p>ntp132CategoryList をセットします。
     * @param ntp132CategoryList ntp132CategoryList
     */
    public void setNtp132CategoryList(List<LabelValueBean> ntp132CategoryList) {
        ntp132CategoryList__ = ntp132CategoryList;
    }

    /**
     * <p>ntp132CatSid を取得します。
     * @return ntp132CatSid
     */
    public int getNtp132CatSid() {
        return ntp132CatSid__;
    }

    /**
     * <p>ntp132CatSid をセットします。
     * @param ntp132CatSid ntp132CatSid
     */
    public void setNtp132CatSid(int ntp132CatSid) {
        ntp132CatSid__ = ntp132CatSid;
    }
}

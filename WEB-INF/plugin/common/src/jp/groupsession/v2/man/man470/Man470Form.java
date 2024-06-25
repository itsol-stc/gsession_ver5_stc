package jp.groupsession.v2.man.man470;

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
 * <br>[機  能] サイボウズLiveデータ移行 掲示板インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man470Form extends Man440Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man470Form.class);

    //非表示項目
    /** プラグインID */
    private String man470pluginId__ = GSConst.PLUGINID_MAIN;

    /** 添付ファイル(コンボで選択中) */
    private String[] man470selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man470FileLabelList__ = null;

    /** 新規フォーラム作成 フォーラム名 */
    private int man470mode__ = GSConst.CMDMODE_ADD;
    /** 新規フォーラム作成 フォーラム名 */
    private String man470forumName__ = null;
    /** グループ名 */
    private String man470grpName__ = null;
    /** 登録済みフォーラム フォーラム(コンボで選択中) */
    private String[] man470selectForum__ = null;
    /** 登録済みフォーラム フォーラムコンボ */
    private ArrayList<LabelValueBean> man470forumLabelList__ = null;
    /** 登録済みフォーラム フォーラムSID */
    private int man470forumSid__ = -1;

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

                CybBulletinCsvImport csvCheck = new CybBulletinCsvImport(errors,
                        reqMdl,
                        con,
                        this);

                //CSVチェック
                csvCheck.isCsvDataOk(fullPath);
            }
        }

        return errors;
    }

    /**
     * <p>man470FileLabelList を取得します。
     * @return man470FileLabelList
     */
    public ArrayList<LabelValueBean> getMan470FileLabelList() {
        return man470FileLabelList__;
    }

    /**
     * <p>man470FileLabelList をセットします。
     * @param man470FileLabelList man470FileLabelList
     */
    public void setMan470FileLabelList(ArrayList<LabelValueBean> man470FileLabelList) {
        man470FileLabelList__ = man470FileLabelList;
    }

    /**
     * <p>man470pluginId を取得します。
     * @return man470pluginId
     */
    public String getMan470pluginId() {
        return man470pluginId__;
    }

    /**
     * <p>man470pluginId をセットします。
     * @param man470pluginId man470pluginId
     */
    public void setMan470pluginId(String man470pluginId) {
        man470pluginId__ = man470pluginId;
    }

    /**
     * <p>man470selectFiles を取得します。
     * @return man470selectFiles
     */
    public String[] getMan470selectFiles() {
        return man470selectFiles__;
    }

    /**
     * <p>man470selectFiles をセットします。
     * @param man470selectFiles man470selectFiles
     */
    public void setMan470selectFiles(String[] man470selectFiles) {
        man470selectFiles__ = man470selectFiles;
    }

    /**
     * <p>man470mode を取得します。
     * @return man470mode
     */
    public int getMan470mode() {
        return man470mode__;
    }

    /**
     * <p>man470mode をセットします。
     * @param man470mode man470mode
     */
    public void setMan470mode(int man470mode) {
        man470mode__ = man470mode;
    }

    /**
     * <p>man470forumName を取得します。
     * @return man470forumName
     */
    public String getMan470forumName() {
        return man470forumName__;
    }

    /**
     * <p>man470forumName をセットします。
     * @param man470forumName man470forumName
     */
    public void setMan470forumName(String man470forumName) {
        man470forumName__ = man470forumName;
    }

    /**
     * <p>man470grpName を取得します。
     * @return man470grpName
     */
    public String getMan470grpName() {
        return man470grpName__;
    }

    /**
     * <p>man470grpName をセットします。
     * @param man470grpName man470grpName
     */
    public void setMan470grpName(String man470grpName) {
        man470grpName__ = man470grpName;
    }

    /**
     * <p>man470selectForum を取得します。
     * @return man470selectForum
     */
    public String[] getMan470selectForum() {
        return man470selectForum__;
    }

    /**
     * <p>man470selectForum をセットします。
     * @param man470selectForum man470selectForum
     */
    public void setMan470selectForum(String[] man470selectForum) {
        man470selectForum__ = man470selectForum;
    }

    /**
     * <p>man470forumSid を取得します。
     * @return man470forumSid
     */
    public int getMan470forumSid() {
        return man470forumSid__;
    }

    /**
     * <p>man470forumSid をセットします。
     * @param man470forumSid man470forumSid
     */
    public void setMan470forumSid(int man470forumSid) {
        man470forumSid__ = man470forumSid;
    }

    /**
     * <p>man470forumLabelList を取得します。
     * @return man470forumLabelList
     */
    public ArrayList<LabelValueBean> getMan470forumLabelList() {
        return man470forumLabelList__;
    }

    /**
     * <p>man470forumLabelList をセットします。
     * @param man470forumLabelList man470forumLabelList
     */
    public void setMan470forumLabelList(ArrayList<LabelValueBean> man470forumLabelList) {
        man470forumLabelList__ = man470forumLabelList;
    }
}

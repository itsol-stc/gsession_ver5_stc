package jp.groupsession.v2.man.man450;

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
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.man440.Man440Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 役職インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man450Form extends Man440Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man450Form.class);

    //非表示項目
    /** プラグインID */
    private String man450pluginId__ = GSConst.PLUGINID_MAIN;

    /** 添付ファイル(コンボで選択中) */
    private String[] man450selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man450FileLabelList__ = null;
    /** グループ名 */
    private String man450grpName__ = null;
    /** 仮パスワード */
    private String man450password__ = "";
    /** パスワード次回更新フラグ */
    private int man450passUpdateFlg__ = 0;


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

                CybMemberCsvImport csvCheck = new CybMemberCsvImport(errors,
                                                                   reqMdl,
                                                                   con,
                                                                   this.getMan440GrpSid(),
                                                                   this.getMan450password(),
                                                                   this.getMan450passUpdateFlg());
                //CSVチェック
                if (!csvCheck.isCsvDataOk(fullPath)) {
                    int dataCnt = csvCheck.getCount(); // 抽出データ件数取得
                    UsrCommonBiz ucBiz = new UsrCommonBiz(con, reqMdl);
                    if (ucBiz.isUserCountOver(reqMdl, con, dataCnt)) {
                        //ユーザ数制限エラー
                        IGsResourceManager resourceManager = GroupSession.getResourceManager();
                        ActionMessage msg = new ActionMessage("error.usercount.limit.over",
                                resourceManager.getUserCountLimit(reqMdl.getDomain()));
                        StrutsUtil.addMessage(errors, msg,
                                              eprefix + "error.usercount.limit.over");
                    }
                }
            }
        }
        return errors;
    }

    /**
     * <p>man450FileLabelList を取得します。
     * @return man450FileLabelList
     */
    public ArrayList<LabelValueBean> getMan450FileLabelList() {
        return man450FileLabelList__;
    }

    /**
     * <p>man450FileLabelList をセットします。
     * @param man450FileLabelList man450FileLabelList
     */
    public void setMan450FileLabelList(ArrayList<LabelValueBean> man450FileLabelList) {
        man450FileLabelList__ = man450FileLabelList;
    }

    /**
     * <p>man450pluginId を取得します。
     * @return man450pluginId
     */
    public String getMan450pluginId() {
        return man450pluginId__;
    }

    /**
     * <p>man450pluginId をセットします。
     * @param man450pluginId man450pluginId
     */
    public void setMan450pluginId(String man450pluginId) {
        man450pluginId__ = man450pluginId;
    }

    /**
     * <p>man450selectFiles を取得します。
     * @return man450selectFiles
     */
    public String[] getMan450selectFiles() {
        return man450selectFiles__;
    }

    /**
     * <p>man450selectFiles をセットします。
     * @param man450selectFiles man450selectFiles
     */
    public void setMan450selectFiles(String[] man450selectFiles) {
        man450selectFiles__ = man450selectFiles;
    }

    /**
     * <p>man450grpName を取得します。
     * @return man450grpName
     */
    public String getMan450grpName() {
        return man450grpName__;
    }

    /**
     * <p>man450grpName をセットします。
     * @param man450grpName man450grpName
     */
    public void setMan450grpName(String man450grpName) {
        man450grpName__ = man450grpName;
    }

    /**
     * <p>man450password を取得します。
     * @return man450password
     */
    public String getMan450password() {
        return man450password__;
    }

    /**
     * <p>man450password をセットします。
     * @param man450password man450password
     */
    public void setMan450password(String man450password) {
        man450password__ = man450password;
    }

    /**
     * <p>man450passUpdateFlg を取得します。
     * @return man450passUpdateFlg
     */
    public int getMan450passUpdateFlg() {
        return man450passUpdateFlg__;
    }

    /**
     * <p>man450passUpdateFlg をセットします。
     * @param man450passUpdateFlg man450passUpdateFlg
     */
    public void setMan450passUpdateFlg(int man450passUpdateFlg) {
        man450passUpdateFlg__ = man450passUpdateFlg;
    }
}

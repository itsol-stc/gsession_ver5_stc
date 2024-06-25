package jp.groupsession.v2.prj.prj160;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.UserModel;
import jp.groupsession.v2.prj.prj030.Prj030ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] プロジェクト管理 TODOインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj160ParamModel extends Prj030ParamModel {

    /** 添付ファイル(コンボで選択中) */
    private String[] prj160SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> prj160FileLabelList__ = null;
    /** 選択グループ(CSVが格納される) */
    private String prj160Selectgroup__ = null;
    /** 選択プロジェクトSID */
    private int prj160PrjSid__ = -1;
    /** プロジェクトラベル */
    private List<LabelValueBean> prj160ProjectLabel__;
    /** カテゴリリスト */
    private List<PrjTodocategoryModel> prj160CategoryList__;
    /** 状態リスト */
    private List<PrjTodostatusModel> prj160StatusList__;
    /** プロジェクトメンバリスト */
    private List<UserModel> prj160MemberList__;
    /** 取り込み件数 */
    private String prj160ImportCnt__;
    /** インポート方法 */
    private int prj160ImportMeans__ = GSConstProject.PRJ_IMP_MEANS_ADD;
    /** マイプロジェクト区分 */
    private int prj160PrjMyKbn__;

    /**
     * <p>prj160ImportMeans を取得します。
     * @return prj160ImportMeans
     */
    public int getPrj160ImportMeans() {
        return prj160ImportMeans__;
    }
    /**
     * <p>prj160ImportMeans をセットします。
     * @param prj160ImportMeans prj160ImportMeans
     */
    public void setPrj160ImportMeans(int prj160ImportMeans) {
        prj160ImportMeans__ = prj160ImportMeans;
    }
    /**
     * <p>prj160ImportCnt を取得します。
     * @return prj160ImportCnt
     */
    public String getPrj160ImportCnt() {
        return prj160ImportCnt__;
    }
    /**
     * <p>prj160ImportCnt をセットします。
     * @param prj160ImportCnt prj160ImportCnt
     */
    public void setPrj160ImportCnt(String prj160ImportCnt) {
        prj160ImportCnt__ = prj160ImportCnt;
    }
    /**
     * <p>prj160MemberList を取得します。
     * @return prj160MemberList
     */
    public List<UserModel> getPrj160MemberList() {
        return prj160MemberList__;
    }
    /**
     * <p>prj160MemberList をセットします。
     * @param prj160MemberList prj160MemberList
     */
    public void setPrj160MemberList(List<UserModel> prj160MemberList) {
        prj160MemberList__ = prj160MemberList;
    }
    /**
     * <p>prj160StatusList を取得します。
     * @return prj160StatusList
     */
    public List<PrjTodostatusModel> getPrj160StatusList() {
        return prj160StatusList__;
    }
    /**
     * <p>prj160StatusList をセットします。
     * @param prj160StatusList prj160StatusList
     */
    public void setPrj160StatusList(List<PrjTodostatusModel> prj160StatusList) {
        prj160StatusList__ = prj160StatusList;
    }
    /**
     * <p>prj160CategoryList を取得します。
     * @return prj160CategoryList
     */
    public List<PrjTodocategoryModel> getPrj160CategoryList() {
        return prj160CategoryList__;
    }
    /**
     * <p>prj160CategoryList をセットします。
     * @param prj160CategoryList prj160CategoryList
     */
    public void setPrj160CategoryList(
            List<PrjTodocategoryModel> prj160CategoryList) {
        prj160CategoryList__ = prj160CategoryList;
    }
    /**
     * <p>prj160PrjSid を取得します。
     * @return prj160PrjSid
     */
    public int getPrj160PrjSid() {
        return prj160PrjSid__;
    }
    /**
     * <p>prj160PrjSid をセットします。
     * @param prj160PrjSid prj160PrjSid
     */
    public void setPrj160PrjSid(int prj160PrjSid) {
        prj160PrjSid__ = prj160PrjSid;
    }
    /**
     * <p>prj160ProjectLabel を取得します。
     * @return prj160ProjectLabel
     */
    public List<LabelValueBean> getPrj160ProjectLabel() {
        return prj160ProjectLabel__;
    }
    /**
     * <p>prj160ProjectLabel をセットします。
     * @param prj160ProjectLabel prj160ProjectLabel
     */
    public void setPrj160ProjectLabel(List<LabelValueBean> prj160ProjectLabel) {
        prj160ProjectLabel__ = prj160ProjectLabel;
    }
    /**
     * <p>prj160FileLabelList を取得します。
     * @return prj160FileLabelList
     */
    public ArrayList<LabelValueBean> getPrj160FileLabelList() {
        return prj160FileLabelList__;
    }
    /**
     * <p>prj160FileLabelList をセットします。
     * @param prj160FileLabelList prj160FileLabelList
     */
    public void setPrj160FileLabelList(ArrayList<LabelValueBean> prj160FileLabelList) {
        prj160FileLabelList__ = prj160FileLabelList;
    }
    /**
     * <p>prj160SelectFiles を取得します。
     * @return prj160SelectFiles
     */
    public String[] getPrj160SelectFiles() {
        return prj160SelectFiles__;
    }
    /**
     * <p>prj160SelectFiles をセットします。
     * @param prj160SelectFiles prj160SelectFiles
     */
    public void setPrj160SelectFiles(String[] prj160SelectFiles) {
        prj160SelectFiles__ = prj160SelectFiles;
    }
    /**
     * <p>prj160Selectgroup を取得します。
     * @return prj160Selectgroup
     */
    public String getPrj160Selectgroup() {
        return prj160Selectgroup__;
    }
    /**
     * <p>prj160Selectgroup をセットします。
     * @param prj160Selectgroup prj160Selectgroup
     */
    public void setPrj160Selectgroup(String prj160Selectgroup) {
        prj160Selectgroup__ = prj160Selectgroup;
    }

    /**
     * <br>[機  能] Cmn999Formに画面パラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     */
    public void setcmn999FormParam(Cmn999Form cmn999Form) {
        super.setcmn999FormParam(cmn999Form);
    }

    /**
     * <p>prj160PrjMyKbn を取得します。
     * @return prj160PrjMyKbn
     */
    public int getPrj160PrjMyKbn() {
        return prj160PrjMyKbn__;
    }
    /**
     * <p>prj160PrjMyKbn をセットします。
     * @param prj160PrjMyKbn prj160PrjMyKbn
     */
    public void setPrj160PrjMyKbn(int prj160PrjMyKbn) {
        prj160PrjMyKbn__ = prj160PrjMyKbn;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param req リクエスト
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(ActionMapping map,
                                       HttpServletRequest req,
                                       String tempDir,
                                       Connection con)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //CSV形式のファイル
        String textCsvFileFormat = gsMsg.getMessage(req, "cmn.csv.file.format");
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage(req, "cmn.capture.file");
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

            //CSVファイルの内容をチェック
            if (!csvError) {

                //プロジェクトメンバーマッピング作成
                PrjMembersDao memberDao = new PrjMembersDao(con);
                HashMap<String, PrjMembersModel> memberMap =
                    memberDao.selectProjectsMap(prj160PrjSid__);

                TodoImportCheck impCheck =
                    new TodoImportCheck(errors, con, memberMap, prj160PrjMyKbn__, req);

                //CSV内容チェック
                String fullPath = tempDir + saveFileName;
                impCheck.isCsvDataOk(fullPath);

                //取り込み件数
                setPrj160ImportCnt(String.valueOf(impCheck.getCount()));
            }
        }
        return errors;
    }
}
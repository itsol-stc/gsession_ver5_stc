package jp.groupsession.v2.man.man480;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.man.man440.Man440Form;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] CybozuLive Todoリストインポート画面 Form
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man480Form extends Man440Form {
    /** テンポラリファイル選択*/
    private String[] man480selectFiles__;

    /** 表示用ファイル名リスト*/
    private List<LabelValueBean> man480FileLabelList__;

    /** 対象プロジェクトモード  */
    private int man480mode__ = GSConst.CMDMODE_ADD;
    /** 新規プロジェクト作成 プロジェクトID */
    private String man480projectID__ = null;
    /** 新規プロジェクト作成 プロジェクト名 */
    private String man480projectName__ = null;
    /** 新規プロジェクト作成 プロジェクト略称 */
    private String man480projectShortName__ = null;

    /** グループ名 */
    private String man480grpName__ = null;
    /** 登録済みプロジェクト選択 */
    private int man480selectProject__ = -1;
    /** 登録済みプロジェクト コンボ */
    private ArrayList<LabelValueBean> man480projectLabelList__ = null;
    /** 登録済みプロジェクト 進捗リスト */
    private ArrayList<LabelValueBean> man480svStatusLabelList__ = null;

    /** ファイル読み込み済み */
    private boolean man480fileOk__ = false;
    /** 追加進捗名 リスト */
    private String[] man480statusNames__ = null;
    /** 追加進捗状態 Map */
    private Map<String, String> man480statusValueMap__ = new HashMap<>();

    /** インポート行数*/
    private int man480impCount__;


    /**
     * <p>man480selectFiles を取得します。
     * @return man480selectFiles
     * @see jp.groupsession.v2.man.man480.Man480Form#man480selectFiles__
     */
    public String[] getMan480selectFiles() {
        return man480selectFiles__;
    }

    /**
     * <p>man480selectFiles をセットします。
     * @param man480selectFiles man480selectFiles
     * @see jp.groupsession.v2.man.man480.Man480Form#man480selectFiles__
     */
    public void setMan480selectFiles(String[] man480selectFiles) {
        man480selectFiles__ = man480selectFiles;
    }

    /**
     * <p>man480FileLabelList を取得します。
     * @return man480FileLabelList
     * @see jp.groupsession.v2.man.man480.Man480Form#man480FileLabelList__
     */
    public List<LabelValueBean> getMan480FileLabelList() {
        return man480FileLabelList__;
    }

    /**
     * <p>man480FileLabelList をセットします。
     * @param man480FileLabelList man480FileLabelList
     * @see jp.groupsession.v2.man.man480.Man480Form#man480FileLabelList__
     */
    public void setMan480FileLabelList(List<LabelValueBean> man480FileLabelList) {
        man480FileLabelList__ = man480FileLabelList;
    }
    /**
     * <p>man480mode を取得します。
     * @return man480mode
     * @see jp.groupsession.v2.man.man480.Man480Form#man480mode__
     */
    public int getMan480mode() {
        return man480mode__;
    }

    /**
     * <p>man480mode をセットします。
     * @param man480mode man480mode
     * @see jp.groupsession.v2.man.man480.Man480Form#man480mode__
     */
    public void setMan480mode(int man480mode) {
        man480mode__ = man480mode;
    }

    /**
     * <p>man480projectID を取得します。
     * @return man480projectID
     * @see jp.groupsession.v2.man.man480.Man480Form#man480projectID__
     */
    public String getMan480projectID() {
        return man480projectID__;
    }

    /**
     * <p>man480projectID をセットします。
     * @param man480projectID man480projectID
     * @see jp.groupsession.v2.man.man480.Man480Form#man480projectID__
     */
    public void setMan480projectID(String man480projectID) {
        man480projectID__ = man480projectID;
    }

    /**
     * <p>man480projectName を取得します。
     * @return man480projectName
     * @see jp.groupsession.v2.man.man480.Man480Form#man480projectName__
     */
    public String getMan480projectName() {
        return man480projectName__;
    }

    /**
     * <p>man480projectName をセットします。
     * @param man480projectName man480projectName
     * @see jp.groupsession.v2.man.man480.Man480Form#man480projectName__
     */
    public void setMan480projectName(String man480projectName) {
        man480projectName__ = man480projectName;
    }

    /**
     * <p>man480projectShortName を取得します。
     * @return man480projectShortName
     * @see jp.groupsession.v2.man.man480.Man480Form#man480projectShortName__
     */
    public String getMan480projectShortName() {
        return man480projectShortName__;
    }

    /**
     * <p>man480projectShortName をセットします。
     * @param man480projectShortName man480projectShortName
     * @see jp.groupsession.v2.man.man480.Man480Form#man480projectShortName__
     */
    public void setMan480projectShortName(String man480projectShortName) {
        man480projectShortName__ = man480projectShortName;
    }

    /**
     * <p>man480grpName を取得します。
     * @return man480grpName
     * @see jp.groupsession.v2.man.man480.Man480Form#man480grpName__
     */
    public String getMan480grpName() {
        return man480grpName__;
    }

    /**
     * <p>man480grpName をセットします。
     * @param man480grpName man480grpName
     * @see jp.groupsession.v2.man.man480.Man480Form#man480grpName__
     */
    public void setMan480grpName(String man480grpName) {
        man480grpName__ = man480grpName;
    }

    /**
     * <p>man480selectProject を取得します。
     * @return man480selectProject
     * @see jp.groupsession.v2.man.man480.Man480Form#man480selectProject__
     */
    public int getMan480selectProject() {
        return man480selectProject__;
    }

    /**
     * <p>man480selectProject をセットします。
     * @param man480selectProject man480selectProject
     * @see jp.groupsession.v2.man.man480.Man480Form#man480selectProject__
     */
    public void setMan480selectProject(int man480selectProject) {
        man480selectProject__ = man480selectProject;
    }

    /**
     * <p>man480projectLabelList を取得します。
     * @return man480projectLabelList
     * @see jp.groupsession.v2.man.man480.Man480Form#man480projectLabelList__
     */
    public ArrayList<LabelValueBean> getMan480projectLabelList() {
        return man480projectLabelList__;
    }

    /**
     * <p>man480projectLabelList をセットします。
     * @param man480projectLabelList man480projectLabelList
     * @see jp.groupsession.v2.man.man480.Man480Form#man480projectLabelList__
     */
    public void setMan480projectLabelList(
            ArrayList<LabelValueBean> man480projectLabelList) {
        man480projectLabelList__ = man480projectLabelList;
    }

    /**
     * <p>man480svStatusLabelList を取得します。
     * @return man480svStatusLabelList
     * @see jp.groupsession.v2.man.man480.Man480Form#man480svStatusLabelList__
     */
    public ArrayList<LabelValueBean> getMan480svStatusLabelList() {
        return man480svStatusLabelList__;
    }

    /**
     * <p>man480svStatusLabelList をセットします。
     * @param man480svStatusLabelList man480svStatusLabelList
     * @see jp.groupsession.v2.man.man480.Man480Form#man480svStatusLabelList__
     */
    public void setMan480svStatusLabelList(ArrayList<LabelValueBean> man480svStatusLabelList) {
        man480svStatusLabelList__ = man480svStatusLabelList;
    }

    /**
     * <p>man480fileOk を取得します。
     * @return man480fileOk
     * @see jp.groupsession.v2.man.man480.Man480Form#man480fileOk__
     */
    public boolean isMan480fileOk() {
        return man480fileOk__;
    }

    /**
     * <p>man480fileOk をセットします。
     * @param man480fileOk man480fileOk
     * @see jp.groupsession.v2.man.man480.Man480Form#man480fileOk__
     */
    public void setMan480fileOk(boolean man480fileOk) {
        man480fileOk__ = man480fileOk;
    }

    /**
     * <p>man480statusNames を取得します。
     * @return man480statusNames
     * @see jp.groupsession.v2.man.man480.Man480Form#man480statusNames__
     */
    public String[] getMan480statusNames() {
        return man480statusNames__;
    }

    /**
     * <p>man480statusNames をセットします。
     * @param man480statusNames man480statusNames
     * @see jp.groupsession.v2.man.man480.Man480Form#man480statusNames__
     */
    public void setMan480statusNames(String[] man480statusNames) {
        man480statusNames__ = man480statusNames;
    }

    /**
     * <p>man480statusValueMap を取得します。
     * @return man480statusValueMap
     * @see jp.groupsession.v2.man.man480.Man480Form#man480statusValueMap__
     */
    public Map<String, String> getMan480statusValueMap() {
        return man480statusValueMap__;
    }

    /**
     * <p>man480statusValueMap をセットします。
     * @param man480statusValueMap man480statusValueMap
     * @see jp.groupsession.v2.man.man480.Man480Form#man480statusValueMap__
     */
    public void setMan480statusValueMap(Map<String, String> man480statusValueMap) {
        man480statusValueMap__ = man480statusValueMap;
    }
    /**
     * <p>man480statusValue を取得します。
     * @param key キー
     * @return man480statusValue
     * @see jp.groupsession.v2.man.man480.Man480Form#man480statusValueMap__
     */
    public String getMan480statusValue(String key) {
        return man480statusValueMap__.get(key);
    }

    /**
     * <p>man480statusValue をセットします。
     * @param key キー
     * @param man480statusValue man480statusValueMap
     * @see jp.groupsession.v2.man.man480.Man480Form#man480statusValueMap__
     */
    public void setMan480statusValue(String key, String man480statusValue) {
        man480statusValueMap__.put(key, man480statusValue);
    }


    /**
     * <p>man480impCount を取得します。
     * @return man480impCount
     * @see jp.groupsession.v2.man.man480.Man480Form#man480impCount__
     */
    public int getMan480impCount() {
        return man480impCount__;
    }

    /**
     * <p>man480impCount をセットします。
     * @param man480impCount man480impCount
     * @see jp.groupsession.v2.man.man480.Man480Form#man480impCount__
     */
    public void setMan480impCount(int man480impCount) {
        man480impCount__ = man480impCount;
    }

    /**
     *
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param tempDir テンポラリディレクトリ
     * @param con コネクション
     * @return 入力チェックエラー
     * @throws Exception 実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl, String tempDir,
            Connection con) throws Exception {
        ActionErrors errors = new ActionErrors();

        boolean fileOk = false;
        String eprefix = "inputFile.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");

        //インポートファイルチェック
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList == null || fileList.size() == 0) {
            ActionMessage msg =
                    new ActionMessage("error.select.required.text", textCaptureFile);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        } else {
            errors = validateFileCheck(reqMdl, tempDir, con);
        }
        if (errors.size() == 0) {
            fileOk = true;
        }

        //プロジェクト登録対象 モード
        if (man480mode__ != GSConst.CMDMODE_ADD
                && man480mode__ != GSConst.CMDMODE_EDIT) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("main.man480.2")),
                    "man480mode");
        }

        //プロジェクト登録対象 新規時
        if (man480mode__ == GSConst.CMDMODE_ADD) {
            //プロジェクトID
            String textProjectId = gsMsg.getMessage("project.31");
            GSValidateCommon.validateTextField(errors, man480projectID__,
                    "man480projectID",
                    textProjectId,
                    GSConstProject.MAX_LENGTH_PRJ_ID, true);

            //プロジェクト名称
            String textProjectName = gsMsg.getMessage("project.40");
            GSValidateCommon.validateTextField(errors, man480projectName__,
                    "man480projectName",
                    textProjectName,
                    GSConstProject.MAX_LENGTH_PRJ_NAME, true);
            //プロジェクト略称
            String textProjectSName = gsMsg.getMessage("project.41");
            GSValidateCommon.validateTextField(errors, man480projectShortName__,
                    "man480projectShortName",
                    textProjectSName,
                    GSConstProject.MAX_LENGTH_PRJ_SHORT_NAME, true);

            //所属メンバー グループSIDチェック
            String textGrpSid = gsMsg.getMessage("main.man480.5");
            GroupDao grpDao = new GroupDao(con);
            CmnGroupmModel grpMdl = grpDao.getGroup(getMan440GrpSid());
            if (grpMdl == null) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("search.data.notfound",
                                textGrpSid),
                        "man440groSid." + "search.data.notfound");
                return errors;
            }
        }

        if (man480mode__ == GSConst.CMDMODE_EDIT) {
            //登録済みのプロジェクト
            String textPrj = gsMsg.getMessage("main.man480.4");
            PrjPrjdataDao prjDao = new PrjPrjdataDao(con);
            PrjPrjdataModel prjMdl = prjDao.getProjectData(man480selectProject__);
            if (prjMdl == null) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("search.data.notfound",
                                textPrj),
                        "man480selectProject." + "search.data.notfound");
                return errors;

            }
        }

        //ステータスの進捗
        if (man480statusValueMap__.size() > 0 && fileOk) {
            String textStatusValue = gsMsg.getMessage("main.man480.6");

            PrjTodostatusDao pts = new PrjTodostatusDao(con);
            List<PrjTodostatusModel> todoStatusList =
                    pts.selectProjects(man480selectProject__);

            LinkedHashMap<String, Integer> svStatusMap = new LinkedHashMap<>();
            ArrayList<LabelValueBean> svStatusList = new ArrayList<>();

            for (PrjTodostatusModel mdl : todoStatusList) {
                if (mdl.getPtsSid() == GSConstProject.STATUS_0
                        || mdl.getPtsSid() == GSConstProject.STATUS_100) {
                    continue;
                }
                if (!svStatusMap.containsKey(mdl.getPtsName())) {
                    svStatusMap.put(mdl.getPtsName(), mdl.getPtsRate());
                    svStatusList.add(
                            new LabelValueBean(mdl.getPtsName(),
                                    String.valueOf(mdl.getPtsRate())));
                }
            }


            CybTodoCsvImport csvCheck = new CybTodoCsvImport(errors,
                                                               reqMdl,
                                                               con,
                                                               null, -1);
            Set<String> readStatusSet = csvCheck.readStatusSet(tempDir);

            //既存ステータスとの重複を除去
            Set<String> statusSet = new HashSet<String>();
            for (String status : readStatusSet) {
                if (!svStatusMap.containsKey(status)) {
                    statusSet.add(status);
                }
            }

            int setIdx = 0;
            int errCnt = errors.size();
            for (@SuppressWarnings("unused") String statusName : statusSet) {
                String rate = null;
                if (man480statusValueMap__.containsKey(String.valueOf(setIdx))) {
                    rate = man480statusValueMap__.get(String.valueOf(setIdx));
                }
                GSValidateCommon.validateNumberInt(errors,
                        rate,
                        textStatusValue,
                        GSConstProject.MAX_LENGTH_RATE);
                if (errors.size() > errCnt) {
                    break;
                }
                setIdx++;
            }
        }

        return errors;
    }
    /**
    *
    * <br>[機  能] ファイル入力チェックを行う
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl リクエストモデル
    * @param tempDir テンポラリディレクトリ
    * @param con コネクション
    * @return 入力チェックエラー
     * @throws Exception 実行時例外
    */
   public ActionErrors validateFileCheck(RequestModel reqMdl, String tempDir,
           Connection con) throws Exception {
       ActionErrors errors = new ActionErrors();

       //インポートファイルチェック
       //テンポラリディレクトリにあるファイル名称を取得
       List<String> fileList = IOTools.getFileNames(tempDir);
       //ファイルなしはここではエラーとしない
       if (fileList == null || fileList.size() == 0) {
           return errors;
       }

       String saveFileName = "";
       String baseFileName = "";
       String eprefix = "inputFile.";
       GsMessage gsMsg = new GsMessage(reqMdl);
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
       //拡張子チェック
       String strExt = StringUtil.getExtension(baseFileName);
       if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
           ActionMessage msg =
               new ActionMessage("error.select.required.text", textCsvFile);
           StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
           return errors;
       }

       CybTodoCsvImport csvCheck = new CybTodoCsvImport(errors,
                                                          reqMdl,
                                                          con,
                                                          null, -1);
       String fullPath = tempDir + saveFileName;

       //CSVチェック
       csvCheck.isCsvDataOk(fullPath);

       return errors;
   }


}

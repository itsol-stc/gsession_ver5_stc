package jp.groupsession.v2.prj.prj023;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjStatusHistoryDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.PrjTodostatusTmpModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusTmpModel;

/**
 * <br>[機  能] プロジェクト管理 TODO状態設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj023Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj023Biz.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID_TEMP = "prj140";
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID_DEF = "prj020";

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエストモデル
     */
    public Prj023Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    public Prj023Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj023ParamModel
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void getDspData(Prj023ParamModel paramMdl, String rootDir)
    throws SQLException, IOToolsException {

        List<LabelValueBean> statusLabel = new ArrayList<LabelValueBean>();
        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //プロジェクト状態
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodostatusTmpModel> todoStatusList =
                projectStatus.getTodoStatusList();

            int index = -1;
            for (PrjTodostatusTmpModel ppsMdl : todoStatusList) {

                index++;
                //0%、100%はコンボに含めない
                if (ppsMdl.getPstRate() == GSConstProject.RATE_MIN
                || ppsMdl.getPstRate() == GSConstProject.RATE_MAX) {
                    continue;
                }

                String state = ppsMdl.getPstRate() + "% " + ppsMdl.getPstName();
                statusLabel.add(new LabelValueBean(state, String.valueOf(index)));
            }

        //通常登録画面からの呼び出し
        } else {

            //プロジェクト状態
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodostatusModel> todoStatusList =
                projectStatus.getTodoStatusList();

            int index = -1;
            for (PrjTodostatusModel ppsMdl : todoStatusList) {

                index++;
                //0%、100%はコンボに含めない
                if (ppsMdl.getPtsRate() == GSConstProject.RATE_MIN
                || ppsMdl.getPtsRate() == GSConstProject.RATE_MAX) {
                    continue;
                }

                String state = ppsMdl.getPtsRate() + "% " + ppsMdl.getPtsName();
                statusLabel.add(new LabelValueBean(state, String.valueOf(index)));
            }
        }

        paramMdl.setStatusLabel(statusLabel);
    }

    /**
     * <br>[機  能] 入力した項目を状態に追加
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj023ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void addStatus(Prj023ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodostatusTmpModel> todoStatusList =
                projectStatus.getTodoStatusList();

            List<PrjTodostatusTmpModel> newTodoStatusList =
                new ArrayList<PrjTodostatusTmpModel>();

            //最後尾の状態情報を取得(100%)
            PrjTodostatusTmpModel maxMdl = todoStatusList.get(todoStatusList.size() - 1);
            int maxSort = maxMdl.getPstSort();

            int rate = NullDefault.getInt(paramMdl.getPrj023rate(), 0);
            int changeSort = 0;
            boolean insertExist = false;

            //既存のリスト項目から挿入する箇所を探す
            for (PrjTodostatusTmpModel mdl : todoStatusList) {

                if (!insertExist) {

                    if (mdl.getPstRate() > rate) {

                        //この位置に挿入
                        PrjTodostatusTmpModel ppsMdl = new PrjTodostatusTmpModel();
                        ppsMdl.setPstSid(maxSort + 1);
                        ppsMdl.setPstSort(mdl.getPstSort());
                        ppsMdl.setPstName(paramMdl.getPrj023name());
                        ppsMdl.setPstRate(rate);
                        newTodoStatusList.add(ppsMdl);

                        changeSort = mdl.getPstSort() + 1;
                        insertExist = true;

                        PrjTodostatusTmpModel oldMdl = new PrjTodostatusTmpModel();
                        oldMdl.setPstSid(mdl.getPstSid());
                        oldMdl.setPstSort(changeSort);
                        oldMdl.setPstName(mdl.getPstName());
                        oldMdl.setPstRate(mdl.getPstRate());
                        newTodoStatusList.add(oldMdl);

                        changeSort += 1;

                    } else {
                        newTodoStatusList.add(mdl);
                    }

                } else {

                    PrjTodostatusTmpModel oldMdl = new PrjTodostatusTmpModel();
                    oldMdl.setPstSid(mdl.getPstSid());
                    oldMdl.setPstSort(changeSort);
                    oldMdl.setPstName(mdl.getPstName());
                    oldMdl.setPstRate(mdl.getPstRate());
                    newTodoStatusList.add(oldMdl);

                    changeSort += 1;
                }
            }

            projectStatus.setTodoStatusList(newTodoStatusList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodostatusModel> todoStatusList =
                projectStatus.getTodoStatusList();

            List<PrjTodostatusModel> newTodoStatusList =
                new ArrayList<PrjTodostatusModel>();

            //最後尾の状態情報を取得(100%)
            PrjTodostatusModel maxMdl = todoStatusList.get(todoStatusList.size() - 1);
            int maxSort = maxMdl.getPtsSort();

            int rate = NullDefault.getInt(paramMdl.getPrj023rate(), 0);
            int changeSort = 0;
            boolean insertExist = false;

            //既存のリスト項目から挿入する箇所を探す
            for (PrjTodostatusModel mdl : todoStatusList) {

                if (!insertExist) {

                    if (mdl.getPtsRate() > rate) {

                        //この位置に挿入
                        PrjTodostatusModel ppsMdl = new PrjTodostatusModel();
                        ppsMdl.setPtsSid(maxSort + 1);
                        ppsMdl.setPtsSort(mdl.getPtsSort());
                        ppsMdl.setPtsName(paramMdl.getPrj023name());
                        ppsMdl.setPtsRate(rate);
                        newTodoStatusList.add(ppsMdl);

                        changeSort = mdl.getPtsSort() + 1;
                        insertExist = true;

                        PrjTodostatusModel oldMdl = new PrjTodostatusModel();
                        oldMdl.setPtsSid(mdl.getPtsSid());
                        oldMdl.setPtsSort(changeSort);
                        oldMdl.setPtsName(mdl.getPtsName());
                        oldMdl.setPtsRate(mdl.getPtsRate());
                        newTodoStatusList.add(oldMdl);

                        changeSort += 1;

                    } else {
                        newTodoStatusList.add(mdl);
                    }

                } else {

                    PrjTodostatusModel oldMdl = new PrjTodostatusModel();
                    oldMdl.setPtsSid(mdl.getPtsSid());
                    oldMdl.setPtsSort(changeSort);
                    oldMdl.setPtsName(mdl.getPtsName());
                    oldMdl.setPtsRate(mdl.getPtsRate());
                    newTodoStatusList.add(oldMdl);

                    changeSort += 1;
                }
            }

            projectStatus.setTodoStatusList(newTodoStatusList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }

    /**
     * <br>[機  能] 選択した項目を状態から削除
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj023ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void removeStatus(Prj023ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodostatusTmpModel> todoStatusList =
                projectStatus.getTodoStatusList();

            int slcState = NullDefault.getInt(paramMdl.getPrj023state(), -1);
            if (slcState == -1) {
                return;
            }

            todoStatusList.remove(slcState);
            projectStatus.setTodoStatusList(todoStatusList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodostatusModel> todoStatusList =
                projectStatus.getTodoStatusList();

            int slcState = NullDefault.getInt(paramMdl.getPrj023state(), -1);
            if (slcState == -1) {
                return;
            }

            todoStatusList.remove(slcState);
            projectStatus.setTodoStatusList(todoStatusList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }

//    /**
//     * <br>[機  能] 選択した項目を対象と入れ替える
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param paramMdl Prj023ParamModel
//     * @param rootDir ルートディレクトリ
//     * @param req リクエスト
//     * @param sortKbn ソート区分
//     * @throws IOToolsException IOエラー
//     */
//    public void chengeRate(
//        Prj023ParamModel paramMdl,
//        String rootDir,
//        HttpServletRequest req,
//        String sortKbn) throws IOToolsException {
//
//        int tmpMode = paramMdl.getPrjTmpMode();
//
//        //テンプレート作成モードからの呼び出し
//        if (tmpMode > 0) {
//
//            //オブジェクトファイルからProjectStatusTmpModelを取得する
//            ProjectStatusTmpModel projectStatus =
//                PrjCommonBiz.getProjectStatusTmpModel(rootDir, req);
//            List<PrjTodostatusTmpModel> todoStatusList =
//                projectStatus.getTodoStatusList();
//
//            int slcState = NullDefault.getInt(paramMdl.getPrj023state(), -1);
//            int targetIndex = 0;
//
//            log__.debug("選択した項目 = " + slcState);
//
//            if (sortKbn.equals(Prj021Action.CMD_SORT_UP_CLICK)) {
//                //一つ上と入れ替える
//                //リストの最初の項目(0%)は移動させないので、
//                //3番目のindexを、移動できるindexの最小値とする
//                if (slcState < 2) {
//                    return;
//                }
//                targetIndex = slcState - 1;
//
//            } else {
//                //一つ下と入れ替える
//                //リストの最後の項目(100%)は移動させないので、
//                //最後のindex(size - 1)から2を引いた数を、移動できるindexの最大値とする
//                if (slcState == -1 || slcState >= todoStatusList.size() - 2) {
//                    return;
//                }
//                targetIndex = slcState + 1;
//            }
//
//            log__.debug("入れ替える対象 = " + targetIndex);
//
//            List<PrjTodostatusTmpModel> newList = new ArrayList<PrjTodostatusTmpModel>();
//            PrjTodostatusTmpModel baseMdl = todoStatusList.get(slcState);
//            PrjTodostatusTmpModel targetMdl = todoStatusList.get(targetIndex);
//
//            int baseSort = baseMdl.getPstSort();
//            int targetSort = targetMdl.getPstSort();
//            baseMdl.setPstSort(targetSort);
//            targetMdl.setPstSort(baseSort);
//
//            int index = 0;
//            for (PrjTodostatusTmpModel ppMdl : todoStatusList) {
//
//                if (index == targetIndex) {
//                    newList.add(baseMdl);
//                } else if (index == slcState) {
//                    newList.add(targetMdl);
//                } else {
//                    newList.add(ppMdl);
//                }
//                index++;
//            }
//
//            projectStatus.setTodoStatusList(newList);
//
//            //ProjectStatusModelをオブジェクトファイルに保存する
//            PrjCommonBiz.saveObjFile(projectStatus, rootDir, req);
//
//        //通常登録画面からの呼び出し
//        } else {
//
//            //オブジェクトファイルからProjectStatusModelを取得する
//            ProjectStatusModel projectStatus =
//                PrjCommonBiz.getProjectStatusModel(rootDir, req);
//            List<PrjTodostatusModel> todoStatusList =
//                projectStatus.getTodoStatusList();
//
//            int slcState = NullDefault.getInt(paramMdl.getPrj023state(), -1);
//            int targetIndex = 0;
//
//            log__.debug("選択した項目 = " + slcState);
//
//            if (sortKbn.equals(Prj021Action.CMD_SORT_UP_CLICK)) {
//                //一つ上と入れ替える
//                //リストの最初の項目(0%)は移動させないので、
//                //3番目のindexを、移動できるindexの最小値とする
//                if (slcState < 2) {
//                    return;
//                }
//                targetIndex = slcState - 1;
//
//            } else {
//                //一つ下と入れ替える
//                //リストの最後の項目(100%)は移動させないので、
//                //最後のindex(size - 1)から2を引いた数を、移動できるindexの最大値とする
//                if (slcState == -1 || slcState >= todoStatusList.size() - 2) {
//                    return;
//                }
//                targetIndex = slcState + 1;
//            }
//
//            log__.debug("入れ替える対象 = " + targetIndex);
//
//            List<PrjTodostatusModel> newList = new ArrayList<PrjTodostatusModel>();
//            PrjTodostatusModel baseMdl = todoStatusList.get(slcState);
//            PrjTodostatusModel targetMdl = todoStatusList.get(targetIndex);
//
//            int baseSort = baseMdl.getPtsSort();
//            int targetSort = targetMdl.getPtsSort();
//            baseMdl.setPtsSort(targetSort);
//            targetMdl.setPtsSort(baseSort);
//
//            int index = 0;
//            for (PrjTodostatusModel ppMdl : todoStatusList) {
//
//                if (index == targetIndex) {
//                    newList.add(baseMdl);
//                } else if (index == slcState) {
//                    newList.add(targetMdl);
//                } else {
//                    newList.add(ppMdl);
//                }
//                index++;
//            }
//
//            projectStatus.setTodoStatusList(newList);
//
//            //ProjectStatusModelをオブジェクトファイルに保存する
//            PrjCommonBiz.saveObjFile(projectStatus, rootDir, req);
//        }
//    }

    /**
     * <br>[機  能] 選択した状態を登録しているTODOがあるかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj023ParamModel
     * @param rootDir ルートディレクトリ
     * @return boolean true=TODOあり、false=TODOなし
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public boolean isExistTodo(Prj023ParamModel paramMdl, String rootDir)
    throws SQLException, IOToolsException {

        //処理モード
        String cmdMode = paramMdl.getPrj020cmdMode();
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録モード
            return false;
        }

        //プロジェクトSID
        int projectSid = paramMdl.getPrj020prjSid();
        int status = NullDefault.getInt(paramMdl.getPrj023state(), -1);
        log__.debug("削除状態index = " + status);

        if (status < 0) {
            return false;
        }

        //オブジェクトファイルからProjectStatusModelを取得する
        ProjectStatusModel projectStatus = PrjCommonBiz.getProjectStatusModel(
                reqMdl__, TEMP_DIRECTORY_ID_DEF);
        List<PrjTodostatusModel> todoStatusList = projectStatus.getTodoStatusList();
        PrjTodostatusModel statusMdl = todoStatusList.get(status);
        int ptsSid = statusMdl.getPtsSid();
        log__.debug("削除状態SID = " + ptsSid);

        PrjTododataDao ptDao = new PrjTododataDao(con__);
        int count = ptDao.getTodoStatesCount(projectSid, ptsSid);
        log__.debug("TODOの該当件数 = " + count);
        if (count > 0) {
            return true;
        }

        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(con__);
        count = pshDao.getTodoStatesCount(projectSid, ptsSid);
        log__.debug("TODO変更履歴の該当件数 = " + count);
        if (count > 0) {
            return true;
        }

        //オブジェクトファイルをチェック
        //削除対象の更新先SIDとして指定されていないかチェック
        HashMap<String, String> statusMap = projectStatus.getUpdateStatus();
        log__.debug("statusMap = " + statusMap);

        if (statusMap != null) {
            if (statusMap.containsValue(String.valueOf(ptsSid))) {
                return true;
            }
        }

        return false;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj023ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void setInitData(Prj023ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {
            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__, TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodostatusTmpModel> todoStatusList =
                projectStatus.getTodoStatusList();

            //0%
            PrjTodostatusTmpModel pts0Mdl = todoStatusList.get(0);
            paramMdl.setPrj023name0(pts0Mdl.getPstName());
            //100%
            PrjTodostatusTmpModel pts100Mdl = todoStatusList.get(todoStatusList.size() - 1);
            paramMdl.setPrj023name100(pts100Mdl.getPstName());

        //通常登録画面からの呼び出し
        } else {
            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodostatusModel> todoStatusList =
                projectStatus.getTodoStatusList();

            //0%
            PrjTodostatusModel pts0Mdl = todoStatusList.get(0);
            paramMdl.setPrj023name0(pts0Mdl.getPtsName());
            //100%
            PrjTodostatusModel pts100Mdl = todoStatusList.get(todoStatusList.size() - 1);
            paramMdl.setPrj023name100(pts100Mdl.getPtsName());
        }
    }

    /**
     * <br>[機  能] 0%、100%の状態名称を入力値に設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj023ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void editStatusName(Prj023ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodostatusTmpModel> todoStatusList =
                projectStatus.getTodoStatusList();

            //0%
            PrjTodostatusTmpModel pts0Mdl = todoStatusList.get(0);
            pts0Mdl.setPstName(paramMdl.getPrj023name0());
            //100%
            PrjTodostatusTmpModel pts100Mdl = todoStatusList.get(todoStatusList.size() - 1);
            pts100Mdl.setPstName(paramMdl.getPrj023name100());

            //ProjectStatusTmpModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodostatusModel> todoStatusList =
                projectStatus.getTodoStatusList();

            //0%
            PrjTodostatusModel pts0Mdl = todoStatusList.get(0);
            pts0Mdl.setPtsName(paramMdl.getPrj023name0());
            //100%
            PrjTodostatusModel pts100Mdl = todoStatusList.get(todoStatusList.size() - 1);
            pts100Mdl.setPtsName(paramMdl.getPrj023name100());

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }
}
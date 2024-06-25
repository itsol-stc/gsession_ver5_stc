package jp.groupsession.v2.prj.prj022;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryTmpModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusTmpModel;
import jp.groupsession.v2.prj.prj021.Prj021Action;

/**
 * <br>[機  能] TODOラベル設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj022Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj022Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID_TEMP = "prj140";
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID_DEF = "prj020";

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエストモデル
     */
    public Prj022Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    public Prj022Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj022ParamModel
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void getDspData(Prj022ParamModel paramMdl, String rootDir)
    throws SQLException, IOToolsException {

        List<LabelValueBean> cateLabel = new ArrayList<LabelValueBean>();

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {
            //プロジェクト状態
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodocategoryTmpModel> todoCateList =
                projectStatus.getTodoCateList();

            if (todoCateList != null) {
                int index = -1;
                for (PrjTodocategoryTmpModel ptcMdl : todoCateList) {
                    index++;
                    String state = ptcMdl.getPctName();
                    cateLabel.add(new LabelValueBean(state, String.valueOf(index)));
                }
            }

        //通常登録画面からの呼び出し
        } else {
            //プロジェクト状態
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodocategoryModel> todoCateList = projectStatus.getTodoCateList();

            if (todoCateList != null) {
                int index = -1;
                for (PrjTodocategoryModel ptcMdl : todoCateList) {
                    index++;
                    String state = ptcMdl.getPtcName();
                    cateLabel.add(new LabelValueBean(state, String.valueOf(index)));
                }
            }
        }

        paramMdl.setStatusLabel(cateLabel);
    }

    /**
     * <br>[機  能] 入力した項目を状態に追加
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj022ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void addStatus(Prj022ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodocategoryTmpModel> todoCateList =
                projectStatus.getTodoCateList();

            if (todoCateList == null) {
                todoCateList = new ArrayList<PrjTodocategoryTmpModel>();
            }

            int maxSort = 0;
            if (todoCateList.size() > 0) {
                //最後尾のカテゴリ情報を取得
                PrjTodocategoryTmpModel maxMdl = todoCateList.get(todoCateList.size() - 1);
                maxSort = maxMdl.getPctSort();
            }

            //追加するカテゴリ情報を作成
            PrjTodocategoryTmpModel ppsMdl = new PrjTodocategoryTmpModel();
            ppsMdl.setPctSort(maxSort + 1);
            ppsMdl.setPctCategorySid(maxSort + 1);
            ppsMdl.setPctName(paramMdl.getPrj022cateAdd());

            todoCateList.add(ppsMdl);
            projectStatus.setTodoCateList(todoCateList);

            //ProjectStatusTmpModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodocategoryModel> todoCateList =
                projectStatus.getTodoCateList();

            if (todoCateList == null) {
                todoCateList = new ArrayList<PrjTodocategoryModel>();
            }

            int maxSort = 0;
            if (todoCateList.size() > 0) {
                //最後尾のカテゴリ情報を取得
                PrjTodocategoryModel maxMdl = todoCateList.get(todoCateList.size() - 1);
                maxSort = maxMdl.getPtcSort();
            }

            //追加するカテゴリ情報を作成
            PrjTodocategoryModel ppsMdl = new PrjTodocategoryModel();
            ppsMdl.setPtcSort(maxSort + 1);
            ppsMdl.setPtcCategorySid(maxSort + 1);
            ppsMdl.setPtcName(paramMdl.getPrj022cateAdd());

            todoCateList.add(ppsMdl);
            projectStatus.setTodoCateList(todoCateList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }

    /**
     * <br>[機  能] 選択した項目を状態から削除
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj022ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void removeStatus(Prj022ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodocategoryTmpModel> todoCateList =
                projectStatus.getTodoCateList();

            int slcState = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);
            if (slcState == -1) {
                return;
            }

            todoCateList.remove(slcState);
            projectStatus.setTodoCateList(todoCateList);

            //ProjectStatusTmpModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodocategoryModel> todoCateList =
                projectStatus.getTodoCateList();

            int slcState = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);
            if (slcState == -1) {
                return;
            }

            todoCateList.remove(slcState);
            projectStatus.setTodoCateList(todoCateList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }

    /**
     * <br>[機  能] 選択した項目を対象と入れ替える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj022ParamModel
     * @param rootDir ルートディレクトリ
     * @param sortKbn ソート区分
     * @throws IOToolsException IOエラー
     */
    public void chengeRate(
        Prj022ParamModel paramMdl,
        String rootDir,
        String sortKbn) throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodocategoryTmpModel> todoCateList =
                projectStatus.getTodoCateList();

            int slcState = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);
            int targetIndex = 0;

            if (sortKbn.equals(Prj021Action.CMD_SORT_UP_CLICK)) {
                //一つ上と入れ替える
                if (slcState < 1) {
                    return;
                }
                targetIndex = slcState - 1;

            } else {
                //一つ下と入れ替える
                if (slcState == -1 || slcState >= todoCateList.size() - 1) {
                    return;
                }
                targetIndex = slcState + 1;
            }

            List<PrjTodocategoryTmpModel> newList = new ArrayList<PrjTodocategoryTmpModel>();
            PrjTodocategoryTmpModel baseMdl = todoCateList.get(slcState);
            PrjTodocategoryTmpModel targetMdl = todoCateList.get(targetIndex);

            int baseSort = baseMdl.getPctSort();
            int targetSort = targetMdl.getPctSort();
            baseMdl.setPctSort(targetSort);
            targetMdl.setPctSort(baseSort);

            int index = 0;
            for (PrjTodocategoryTmpModel ppMdl : todoCateList) {

                if (index == targetIndex) {
                    newList.add(baseMdl);
                } else if (index == slcState) {
                    newList.add(targetMdl);
                } else {
                    newList.add(ppMdl);
                }
                index++;
            }

            projectStatus.setTodoCateList(newList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodocategoryModel> todoCateList =
                projectStatus.getTodoCateList();

            int slcState = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);
            int targetIndex = 0;

            if (sortKbn.equals(Prj021Action.CMD_SORT_UP_CLICK)) {
                //一つ上と入れ替える
                if (slcState < 1) {
                    return;
                }
                targetIndex = slcState - 1;

            } else {
                //一つ下と入れ替える
                if (slcState == -1 || slcState >= todoCateList.size() - 1) {
                    return;
                }
                targetIndex = slcState + 1;
            }

            List<PrjTodocategoryModel> newList = new ArrayList<PrjTodocategoryModel>();
            PrjTodocategoryModel baseMdl = todoCateList.get(slcState);
            PrjTodocategoryModel targetMdl = todoCateList.get(targetIndex);

            int baseSort = baseMdl.getPtcSort();
            int targetSort = targetMdl.getPtcSort();
            baseMdl.setPtcSort(targetSort);
            targetMdl.setPtcSort(baseSort);

            int index = 0;
            for (PrjTodocategoryModel ppMdl : todoCateList) {

                if (index == targetIndex) {
                    newList.add(baseMdl);
                } else if (index == slcState) {
                    newList.add(targetMdl);
                } else {
                    newList.add(ppMdl);
                }
                index++;
            }

            projectStatus.setTodoCateList(newList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }

    /**
     * <br>[機  能] 選択したカテゴリを登録しているTODOがあるかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj022ParamModel
     * @param rootDir ルートディレクトリ
     * @return boolean true=TODOあり、false=TODOなし
     * @throws IOToolsException IOエラー
     * @throws SQLException SQL実行例外
     */
    public boolean isExistTodo(Prj022ParamModel paramMdl, String rootDir)
    throws SQLException, IOToolsException {

        //処理モード
        String cmdMode = paramMdl.getPrj020cmdMode();
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録モード
            return false;
        }

        //プロジェクトSID
        int projectSid = paramMdl.getPrj020prjSid();
        int cate = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);
        log__.debug("削除カテゴリindex = " + cate);

        if (cate == -1) {
            return false;
        }

        //オブジェクトファイルからProjectStatusModelを取得する
        ProjectStatusModel projectStatus = PrjCommonBiz.getProjectStatusModel(
                reqMdl__, TEMP_DIRECTORY_ID_DEF);
        List<PrjTodocategoryModel> todoCateList = projectStatus.getTodoCateList();
        PrjTodocategoryModel cateMdl = todoCateList.get(cate);
        log__.debug("削除カテゴリSID = " + cateMdl.getPtcCategorySid());

        //DBをチェック
        PrjTododataDao ptDao = new PrjTododataDao(con__);
        int count = ptDao.getTodoCateCount(projectSid, cateMdl.getPtcCategorySid());
        log__.debug("DBの該当件数 = " + count);

        if (count > 0) {
            return true;
        }

        //オブジェクトファイルをチェック
        //削除対象の更新先SIDとして指定されていないかチェック
        HashMap<String, String> cateMap = projectStatus.getUpdateCate();
        log__.debug("cateMap = " + cateMap);

        if (cateMap != null) {
            if (cateMap.containsValue(String.valueOf(cateMdl.getPtcCategorySid()))) {
                return true;
            }
        }

        return false;
    }

    /**
     * <br>[機  能] 編集するカテゴリ名を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj022ParamModel
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void setEditCategoryName(Prj022ParamModel paramMdl, String rootDir)
    throws SQLException, IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();
        String editName = "";
        int slcState = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus
                = PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodocategoryTmpModel> todoCateList = projectStatus.getTodoCateList();


            int index = 0;
            for (PrjTodocategoryTmpModel ppMdl : todoCateList) {

                if (index == slcState) {
                    editName = ppMdl.getPctName();
                }
                index++;
            }


        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodocategoryModel> todoCateList = projectStatus.getTodoCateList();

            int index = 0;
            for (PrjTodocategoryModel ppMdl : todoCateList) {

                if (index == slcState) {
                    editName = ppMdl.getPtcName();
                }
                index++;
            }

        }

        paramMdl.setPrj022selectCategory(paramMdl.getPrj022cateSlc());
        paramMdl.setPrj022editCategoryName(editName);

    }

    /**
     * <br>[機  能] 選択した項目名を変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj022ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void editCategoryName(Prj022ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus
                = PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjTodocategoryTmpModel> todoCateList = projectStatus.getTodoCateList();

            int slcState = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);

            String editName = paramMdl.getPrj022editCategoryName();
            if (StringUtil.isNullZeroString(editName) || todoCateList == null) {
                return;
            }

            int index = 0;
            for (PrjTodocategoryTmpModel ppMdl : todoCateList) {

                if (index == slcState) {
                    ppMdl.setPctName(editName);
                }
                index++;
            }

            projectStatus.setTodoCateList(todoCateList);

            //ProjectStatusTmpModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus
            = PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjTodocategoryModel> todoCateList = projectStatus.getTodoCateList();

            int slcState = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);

            String editName = paramMdl.getPrj022editCategoryName();
            if (StringUtil.isNullZeroString(editName) || todoCateList == null) {
                return;
            }

            int index = 0;
            for (PrjTodocategoryModel ppMdl : todoCateList) {

                if (index == slcState) {
                    ppMdl.setPtcName(editName);
                }
                index++;
            }

            projectStatus.setTodoCateList(todoCateList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }

    }
}


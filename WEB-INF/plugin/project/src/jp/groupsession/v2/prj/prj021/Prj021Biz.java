package jp.groupsession.v2.prj.prj021;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.model.PrjPrjstatusModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusTmpModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusTmpModel;

/**
 * <br>[機  能] プロジェクト管理 状態設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj021Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj021Biz.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID_TEMP = "prj140";
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID_DEF = "prj020";

    /**
     * <p>Set Connection
     * @param reqMdl リクエストモデル
     */
    public Prj021Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj021ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void setInitData(Prj021ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjPrjstatusTmpModel> prjStatusList =
                projectStatus.getPrjStatusList();

            //0%
            PrjPrjstatusTmpModel pps0Mdl = prjStatusList.get(0);
            paramMdl.setPrj021name0(pps0Mdl.getPttName());
            //100%
            PrjPrjstatusTmpModel pps100Mdl = prjStatusList.get(prjStatusList.size() - 1);
            paramMdl.setPrj021name100(pps100Mdl.getPttName());

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID_DEF);
            List<PrjPrjstatusModel> prjStatusList =
                projectStatus.getPrjStatusList();

            //0%
            PrjPrjstatusModel pps0Mdl = prjStatusList.get(0);
            paramMdl.setPrj021name0(pps0Mdl.getPrsName());
            //100%
            PrjPrjstatusModel pps100Mdl = prjStatusList.get(prjStatusList.size() - 1);
            paramMdl.setPrj021name100(pps100Mdl.getPrsName());
        }
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj021ParamModel
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void getDspData(Prj021ParamModel paramMdl, String rootDir)
    throws SQLException, IOToolsException {

        List<LabelValueBean> statusLabel = new ArrayList<LabelValueBean>();

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //プロジェクト状態
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjPrjstatusTmpModel> prjStatusList =
                projectStatus.getPrjStatusList();

            int index = -1;
            for (PrjPrjstatusTmpModel ppsMdl : prjStatusList) {

                index++;
                //0%、100%はコンボに含めない
                if (ppsMdl.getPttRate() == GSConstProject.RATE_MIN
                || ppsMdl.getPttRate() == GSConstProject.RATE_MAX) {
                    continue;
                }

                String state = ppsMdl.getPttRate() + "% " + ppsMdl.getPttName();
                statusLabel.add(new LabelValueBean(state, String.valueOf(index)));
            }

        //通常登録画面からの呼び出し
        } else {

            //プロジェクト状態
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__,
                        TEMP_DIRECTORY_ID_DEF);
            List<PrjPrjstatusModel> prjStatusList =
                projectStatus.getPrjStatusList();

            int index = -1;
            for (PrjPrjstatusModel ppsMdl : prjStatusList) {

                index++;
                //0%、100%はコンボに含めない
                if (ppsMdl.getPrsRate() == GSConstProject.RATE_MIN
                || ppsMdl.getPrsRate() == GSConstProject.RATE_MAX) {
                    continue;
                }

                String state = ppsMdl.getPrsRate() + "% " + ppsMdl.getPrsName();
                statusLabel.add(new LabelValueBean(state, String.valueOf(index)));
            }
        }

        paramMdl.setStatusLabel(statusLabel);
    }

    /**
     * <br>[機  能] 入力した項目を状態に追加
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj021ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void addStatus(Prj021ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjPrjstatusTmpModel> prjStatusList =
                projectStatus.getPrjStatusList();

            List<PrjPrjstatusTmpModel> newProjectStatus =
                new ArrayList<PrjPrjstatusTmpModel>();

            //最後尾の状態情報を取得(100%)
            PrjPrjstatusTmpModel maxMdl = prjStatusList.get(prjStatusList.size() - 1);
            int maxSort = maxMdl.getPttSort();

            int rate = NullDefault.getInt(paramMdl.getPrj021rate(), 0);
            int changeSort = 0;
            boolean insertExist = false;

            //既存のリスト項目から挿入する箇所を探す
            for (PrjPrjstatusTmpModel mdl : prjStatusList) {

                if (!insertExist) {

                    if (mdl.getPttRate() > rate) {

                        //この位置に挿入
                        PrjPrjstatusTmpModel ppsMdl = new PrjPrjstatusTmpModel();
                        ppsMdl.setPttSid(maxSort + 1);
                        ppsMdl.setPttSort(mdl.getPttSort());
                        ppsMdl.setPttName(paramMdl.getPrj021name());
                        ppsMdl.setPttRate(rate);
                        newProjectStatus.add(ppsMdl);

                        changeSort = mdl.getPttSort() + 1;
                        insertExist = true;

                        PrjPrjstatusTmpModel oldMdl = new PrjPrjstatusTmpModel();
                        oldMdl.setPttSid(mdl.getPttSid());
                        oldMdl.setPttSort(changeSort);
                        oldMdl.setPttName(mdl.getPttName());
                        oldMdl.setPttRate(mdl.getPttRate());
                        newProjectStatus.add(oldMdl);

                        changeSort += 1;

                    } else {
                        newProjectStatus.add(mdl);
                    }

                } else {

                    PrjPrjstatusTmpModel oldMdl = new PrjPrjstatusTmpModel();
                    oldMdl.setPttSid(mdl.getPttSid());
                    oldMdl.setPttSort(changeSort);
                    oldMdl.setPttName(mdl.getPttName());
                    oldMdl.setPttRate(mdl.getPttRate());
                    newProjectStatus.add(oldMdl);

                    changeSort += 1;
                }
            }

            projectStatus.setPrjStatusList(newProjectStatus);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__,
                        TEMP_DIRECTORY_ID_DEF);
            List<PrjPrjstatusModel> prjStatusList =
                projectStatus.getPrjStatusList();

            List<PrjPrjstatusModel> newProjectStatus =
                new ArrayList<PrjPrjstatusModel>();

            //最後尾の状態情報を取得(100%)
            PrjPrjstatusModel maxMdl = prjStatusList.get(prjStatusList.size() - 1);
            int maxSort = maxMdl.getPrsSort();

            int rate = NullDefault.getInt(paramMdl.getPrj021rate(), 0);
            int changeSort = 0;
            boolean insertExist = false;

            //既存のリスト項目から挿入する箇所を探す
            for (PrjPrjstatusModel mdl : prjStatusList) {

                if (!insertExist) {

                    if (mdl.getPrsRate() > rate) {

                        //この位置に挿入
                        PrjPrjstatusModel ppsMdl = new PrjPrjstatusModel();
                        ppsMdl.setPrsSid(maxSort + 1);
                        ppsMdl.setPrsSort(mdl.getPrsSort());
                        ppsMdl.setPrsName(paramMdl.getPrj021name());
                        ppsMdl.setPrsRate(rate);
                        newProjectStatus.add(ppsMdl);

                        changeSort = mdl.getPrsSort() + 1;
                        insertExist = true;

                        PrjPrjstatusModel oldMdl = new PrjPrjstatusModel();
                        oldMdl.setPrsSid(mdl.getPrsSid());
                        oldMdl.setPrsSort(changeSort);
                        oldMdl.setPrsName(mdl.getPrsName());
                        oldMdl.setPrsRate(mdl.getPrsRate());
                        newProjectStatus.add(oldMdl);

                        changeSort += 1;

                    } else {
                        newProjectStatus.add(mdl);
                    }

                } else {

                    PrjPrjstatusModel oldMdl = new PrjPrjstatusModel();
                    oldMdl.setPrsSid(mdl.getPrsSid());
                    oldMdl.setPrsSort(changeSort);
                    oldMdl.setPrsName(mdl.getPrsName());
                    oldMdl.setPrsRate(mdl.getPrsRate());
                    newProjectStatus.add(oldMdl);

                    changeSort += 1;
                }
            }

            projectStatus.setPrjStatusList(newProjectStatus);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }

    /**
     * <br>[機  能] 選択した項目を状態から削除
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj021ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void removeStatus(Prj021ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjPrjstatusTmpModel> prjStatusList =
                projectStatus.getPrjStatusList();

            int slcState = NullDefault.getInt(paramMdl.getPrj021state(), -1);
            if (slcState == -1) {
                return;
            }

            prjStatusList.remove(slcState);
            projectStatus.setPrjStatusList(prjStatusList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__,
                        TEMP_DIRECTORY_ID_DEF);
            List<PrjPrjstatusModel> prjStatusList =
                projectStatus.getPrjStatusList();

            int slcState = NullDefault.getInt(paramMdl.getPrj021state(), -1);
            if (slcState == -1) {
                return;
            }

            prjStatusList.remove(slcState);
            projectStatus.setPrjStatusList(prjStatusList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }

    /**
     * <br>[機  能] 選択した項目を対象と入れ替える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj021ParamModel
     * @param rootDir ルートディレクトリ
     * @param sortKbn ソート区分
     * @throws IOToolsException IOエラー
     */
    public void chengeRate(
        Prj021ParamModel paramMdl,
        String rootDir,
        String sortKbn) throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjPrjstatusTmpModel> prjStatusList =
                projectStatus.getPrjStatusList();

            int slcState = NullDefault.getInt(paramMdl.getPrj021state(), -1);
            int targetIndex = 0;

            log__.debug("選択した項目 = " + slcState);

            if (sortKbn.equals(Prj021Action.CMD_SORT_UP_CLICK)) {
                //一つ上と入れ替える
                //リストの最初の項目(0%)は移動させないので、
                //3番目のindexを、移動できるindexの最小値とする
                if (slcState < 2) {
                    return;
                }
                targetIndex = slcState - 1;

            } else {
                //一つ下と入れ替える
                //リストの最後の項目(100%)は移動させないので、
                //最後のindex(size - 1)から2を引いた数を、移動できるindexの最大値とする
                if (slcState == -1 || slcState >= prjStatusList.size() - 2) {
                    return;
                }
                targetIndex = slcState + 1;
            }

            log__.debug("入れ替える対象 = " + targetIndex);

            List<PrjPrjstatusTmpModel> newList = new ArrayList<PrjPrjstatusTmpModel>();
            PrjPrjstatusTmpModel baseMdl = prjStatusList.get(slcState);
            PrjPrjstatusTmpModel targetMdl = prjStatusList.get(targetIndex);

            int baseSort = baseMdl.getPttSort();
            int targetSort = targetMdl.getPttSort();
            baseMdl.setPttSort(targetSort);
            targetMdl.setPttSort(baseSort);

            int index = 0;
            for (PrjPrjstatusTmpModel ppMdl : prjStatusList) {

                if (index == targetIndex) {
                    newList.add(baseMdl);
                } else if (index == slcState) {
                    newList.add(targetMdl);
                } else {
                    newList.add(ppMdl);
                }
                index++;
            }

            projectStatus.setPrjStatusList(newList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__,
                        TEMP_DIRECTORY_ID_DEF);
            List<PrjPrjstatusModel> prjStatusList =
                projectStatus.getPrjStatusList();

            int slcState = NullDefault.getInt(paramMdl.getPrj021state(), -1);
            int targetIndex = 0;

            log__.debug("選択した項目 = " + slcState);

            if (sortKbn.equals(Prj021Action.CMD_SORT_UP_CLICK)) {
                //一つ上と入れ替える
                //リストの最初の項目(0%)は移動させないので、
                //3番目のindexを、移動できるindexの最小値とする
                if (slcState < 2) {
                    return;
                }
                targetIndex = slcState - 1;

            } else {
                //一つ下と入れ替える
                //リストの最後の項目(100%)は移動させないので、
                //最後のindex(size - 1)から2を引いた数を、移動できるindexの最大値とする
                if (slcState == -1 || slcState >= prjStatusList.size() - 2) {
                    return;
                }
                targetIndex = slcState + 1;
            }

            log__.debug("入れ替える対象 = " + targetIndex);

            List<PrjPrjstatusModel> newList = new ArrayList<PrjPrjstatusModel>();
            PrjPrjstatusModel baseMdl = prjStatusList.get(slcState);
            PrjPrjstatusModel targetMdl = prjStatusList.get(targetIndex);

            int baseSort = baseMdl.getPrsSort();
            int targetSort = targetMdl.getPrsSort();
            baseMdl.setPrsSort(targetSort);
            targetMdl.setPrsSort(baseSort);

            int index = 0;
            for (PrjPrjstatusModel ppMdl : prjStatusList) {

                if (index == targetIndex) {
                    newList.add(baseMdl);
                } else if (index == slcState) {
                    newList.add(targetMdl);
                } else {
                    newList.add(ppMdl);
                }
                index++;
            }

            projectStatus.setPrjStatusList(newList);

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }

    /**
     * <br>[機  能] 0%、100%の状態名称を入力値に設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj021ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void editStatusName(Prj021ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        int tmpMode = paramMdl.getPrjTmpMode();

        //テンプレート作成モードからの呼び出し
        if (tmpMode > 0) {

            //オブジェクトファイルからProjectStatusTmpModelを取得する
            ProjectStatusTmpModel projectStatus =
                PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                        TEMP_DIRECTORY_ID_TEMP);
            List<PrjPrjstatusTmpModel> prjStatusList =
                projectStatus.getPrjStatusList();

            //0%
            PrjPrjstatusTmpModel pps0Mdl = prjStatusList.get(0);
            pps0Mdl.setPttName(paramMdl.getPrj021name0());
            //100%
            PrjPrjstatusTmpModel pps100Mdl = prjStatusList.get(prjStatusList.size() - 1);
            pps100Mdl.setPttName(paramMdl.getPrj021name100());

            //ProjectStatusTmpModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_TEMP);

        //通常登録画面からの呼び出し
        } else {

            //オブジェクトファイルからProjectStatusModelを取得する
            ProjectStatusModel projectStatus =
                PrjCommonBiz.getProjectStatusModel(reqMdl__,
                        TEMP_DIRECTORY_ID_DEF);
            List<PrjPrjstatusModel> prjStatusList =
                projectStatus.getPrjStatusList();

            //0%
            PrjPrjstatusModel pps0Mdl = prjStatusList.get(0);
            pps0Mdl.setPrsName(paramMdl.getPrj021name0());
            //100%
            PrjPrjstatusModel pps100Mdl = prjStatusList.get(prjStatusList.size() - 1);
            pps100Mdl.setPrsName(paramMdl.getPrj021name100());

            //ProjectStatusModelをオブジェクトファイルに保存する
            PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                    TEMP_DIRECTORY_ID_DEF);
        }
    }
}

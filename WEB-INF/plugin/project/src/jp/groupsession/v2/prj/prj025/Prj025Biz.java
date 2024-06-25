package jp.groupsession.v2.prj.prj025;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;

/**
 * <br>[機  能] TODO状態削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj025Biz {

    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj020";

    /**
     * <p>Set Connection
     * @param reqMdl リクエストモデル
     */
    public Prj025Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj025ParamModel
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void getDspData(Prj025ParamModel paramMdl, String rootDir)
    throws SQLException, IOToolsException {

        //プロジェクト状態
        ProjectStatusModel projectStatus = PrjCommonBiz.getProjectStatusModel(
                reqMdl__, TEMP_DIRECTORY_ID);
        List<PrjTodostatusModel> todoStatusList = projectStatus.getTodoStatusList();

        int status = NullDefault.getInt(paramMdl.getPrj023state(), -1);
        PrjTodostatusModel statusMdl = todoStatusList.get(status);

        List<LabelValueBean> statusLabel = new ArrayList<LabelValueBean>();
        int index = -1;
        for (PrjTodostatusModel ppsMdl : todoStatusList) {
            index++;
            if (statusMdl.getPtsSid() == ppsMdl.getPtsSid()) {
                paramMdl.setStatusName(ppsMdl.getPtsName());
                continue;
            }

            String state = ppsMdl.getPtsRate() + "% " + ppsMdl.getPtsName();
            statusLabel.add(new LabelValueBean(state, String.valueOf(index)));
        }

        paramMdl.setStatusLabel(statusLabel);
    }

    /**
     * <br>[機  能] 選択した項目を更新用としてファイルに保存
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj025ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void updateSave(Prj025ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        //オブジェクトファイルからProjectStatusModelを取得する
        ProjectStatusModel projectStatus = PrjCommonBiz.getProjectStatusModel(
                reqMdl__, TEMP_DIRECTORY_ID);
        List<PrjTodostatusModel> todoStatusList = projectStatus.getTodoStatusList();
        HashMap<String, String> saveMap = projectStatus.getUpdateStatus();
        if (saveMap == null) {
            saveMap = new HashMap<String, String>();
        }

        //削除対象
        PrjTodostatusModel delStatesMdl =
            todoStatusList.get(NullDefault.getInt(paramMdl.getPrj023state(), -1));
        int delStatus = delStatesMdl.getPtsSid();
        //削除対象の更新先
        int updStatus = -1;
        if (NullDefault.getInt(paramMdl.getPrj025stateSlc(), -1) != -1) {
            PrjTodostatusModel updStatusMdl =
                todoStatusList.get(NullDefault.getInt(paramMdl.getPrj025stateSlc(), -1));
            updStatus = updStatusMdl.getPtsSid();
        }

        HashMap<String, String> newMap = new HashMap<String, String>();
        Iterator<Entry<String, String>> itr = (saveMap.entrySet()).iterator();
        while (itr.hasNext()) {
            Entry<String, String> map = (Entry<String, String>) itr.next();
            if (((String) map.getValue()).equals(String.valueOf(delStatus))) {
                newMap.put((String) map.getKey(), String.valueOf(updStatus));
            } else {
                newMap.put((String) map.getKey(), (String) map.getValue());
            }
        }
        newMap.put(String.valueOf(delStatus), String.valueOf(updStatus));

        projectStatus.setUpdateStatus(newMap);

        //ProjectStatusModelをオブジェクトファイルに保存する
        PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                TEMP_DIRECTORY_ID);
    }

}

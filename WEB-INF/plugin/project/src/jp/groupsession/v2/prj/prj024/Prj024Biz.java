package jp.groupsession.v2.prj.prj024;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 TODOカテゴリ設定削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj024Biz {

    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj020";

    /**
     * <p>Set Connection
     * @param reqMdl リクエストモデル
     */
    public Prj024Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj024ParamModel
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void getDspData(Prj024ParamModel paramMdl, String rootDir)
    throws SQLException, IOToolsException {

        //プロジェクト状態
        ProjectStatusModel projectStatus =
            PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID);
        List<PrjTodocategoryModel> todoCateList = projectStatus.getTodoCateList();

        List<LabelValueBean> cateLabel = new ArrayList<LabelValueBean>();
        //未選択
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textUnSelection = gsMsg.getMessage("project.src.1");
        cateLabel.add(new LabelValueBean(textUnSelection, String.valueOf(GSConstCommon.NUM_INIT)));

        //オブジェクトファイルからProjectStatusModelを取得する
        int cate = NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1);
        PrjTodocategoryModel cateMdl = todoCateList.get(cate);

        if (todoCateList != null) {
            int index = -1;
            for (PrjTodocategoryModel ptcMdl : todoCateList) {
                index++;
                if (cateMdl.getPtcCategorySid() == ptcMdl.getPtcCategorySid()) {
                    paramMdl.setCateName(ptcMdl.getPtcName());
                    continue;
                }
                String state = ptcMdl.getPtcName();
                cateLabel.add(new LabelValueBean(state, String.valueOf(index)));
            }
        }

        paramMdl.setStatusLabel(cateLabel);
    }

    /**
     * <br>[機  能] 選択した項目を更新用としてファイルに保存
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj024ParamModel
     * @param rootDir ルートディレクトリ
     * @throws IOToolsException IOエラー
     */
    public void updateSave(Prj024ParamModel paramMdl, String rootDir)
    throws IOToolsException {

        //オブジェクトファイルからProjectStatusModelを取得する
        ProjectStatusModel projectStatus =
            PrjCommonBiz.getProjectStatusModel(reqMdl__, TEMP_DIRECTORY_ID);
        List<PrjTodocategoryModel> todoCateList = projectStatus.getTodoCateList();
        HashMap<String, String> saveMap = projectStatus.getUpdateCate();
        if (saveMap == null) {
            saveMap = new HashMap<String, String>();
        }

        //削除対象
        PrjTodocategoryModel delCateMdl =
            todoCateList.get(NullDefault.getInt(paramMdl.getPrj022cateSlc(), -1));
        int delCate = delCateMdl.getPtcCategorySid();
        //削除対象の更新先
        int updCate = -1;
        if (NullDefault.getInt(paramMdl.getPrj024cateSlc(), -1) != -1) {
            PrjTodocategoryModel updCateMdl =
                todoCateList.get(NullDefault.getInt(paramMdl.getPrj024cateSlc(), -1));
            updCate = updCateMdl.getPtcCategorySid();
        }

        HashMap<String, String> newMap = new HashMap<String, String>();
        Iterator<Entry<String, String>> itr = (saveMap.entrySet()).iterator();
        while (itr.hasNext()) {
            Entry<String, String> map = (Entry<String, String>) itr.next();
            if (((String) map.getValue()).equals(String.valueOf(delCate))) {
                newMap.put((String) map.getKey(), String.valueOf(updCate));
            } else {
                newMap.put((String) map.getKey(), (String) map.getValue());
            }
        }
        newMap.put(String.valueOf(delCate), String.valueOf(updCate));

        projectStatus.setUpdateCate(newMap);

        //ProjectStatusModelをオブジェクトファイルに保存する
        PrjCommonBiz.saveObjFile(projectStatus, rootDir, reqMdl__,
                TEMP_DIRECTORY_ID);
    }

}

package jp.groupsession.v2.prj.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjMainBiz {

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public PrjMainBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl PrjMainParamModel
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setInitData(PrjMainParamModel paramMdl, int userSid) throws SQLException {

        ProjectSearchModel search = new ProjectSearchModel();
        search.setEndPrjFlg(false);
        search.setGetKbn(ProjectSearchModel.GET_BELONG);
        search.setUserSid(userSid);
//        search.setMirai("1");
        search.setSelectingMember(String.valueOf(userSid));
        search.setOrder(paramMdl.getPrjMainOrder());
        search.setSort(paramMdl.getPrjMainSort());
        search.setTodayFlg(true);

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ProjectSearchDao projectDao = new ProjectSearchDao(con__, gsMsg);
        List<ProjectItemModel> piList = projectDao.getAllTodoList(search);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        BaseUserModel buMdl = new BaseUserModel();
        buMdl.setUsrsid(userSid);
        for (ProjectItemModel piMdl : piList) {
            piMdl.setStrStartDate(UDateUtil.getSlashYYMD(piMdl.getStartDate()));
            piMdl.setStrEndDate(UDateUtil.getSlashYYMD(piMdl.getEndDate()));
            piMdl.setPrjTodoEdit(prjBiz.getTodoEditKengen(piMdl.getProjectSid(), buMdl));
        }
        paramMdl.setProjectList(piList);
    }

}

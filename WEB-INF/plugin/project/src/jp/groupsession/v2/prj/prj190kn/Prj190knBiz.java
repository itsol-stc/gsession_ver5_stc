package jp.groupsession.v2.prj.prj190kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjUserConfDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjUserConfModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 個人設定 ダッシュボード初期値設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj190knBiz {

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Prj190knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示値セット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj190knParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Prj190knParamModel paramMdl, BaseUserModel buMdl)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        PrjCommonBiz biz = new PrjCommonBiz(con__, gsMsg, reqMdl__);

        //ＴＯＤＯ[ダッシュボード] 日付
        paramMdl.setPrj190knTodoDateStr(
                biz.getTargetDateString(
                        Integer.parseInt(paramMdl.getPrj190TodoDay())));
        //全て
        String textAll = gsMsg.getMessage("cmn.all");
        //ＴＯＤＯ[ダッシュボード] プロジェクト
        String todoPrjStr = "";
        int todoPrjSid = Integer.parseInt(paramMdl.getPrj190TodoPrj());
        if (todoPrjSid == GSConstProject.PROJECT_ALL) {
            todoPrjStr = textAll;
        } else {
            ProjectSearchModel bean = new ProjectSearchModel();
            bean.setUserSid(buMdl.getUsrsid());
            bean.setOrder(GSConst.ORDER_KEY_ASC);
            bean.setSort(GSConstProject.SORT_PRJECT_START);
            bean.setEndPrjFlg(true);
            bean.setGetKbn(ProjectSearchModel.GET_BELONG);
            bean.setProjectSid(todoPrjSid);

            ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
            List<ProjectItemModel> prjlist = psDao.getAllProjectList(bean);

            if (!prjlist.isEmpty()) {
                ProjectItemModel prjMdl = (ProjectItemModel) prjlist.get(0);
                todoPrjStr = prjMdl.getProjectName();
            }
        }

        paramMdl.setPrj190knTodoProjectStr(todoPrjStr);

        //ＴＯＤＯ[ダッシュボード] 状態
        String todoStsStr =
            biz.getStatusString(
                    todoPrjSid,
                    Integer.parseInt(paramMdl.getPrj190TodoSts()),
                    con__);

        paramMdl.setPrj190knTodoStatusStr(todoStsStr);

        //プロジェクト[ダッシュボード] プロジェクト
        String prjStr =
            biz.getTargetProjectString(
                    buMdl, Integer.parseInt(paramMdl.getPrj190Project()));

        paramMdl.setPrj190knProjectStr(prjStr);

        //ダッシュボード初期表示画面
        if (paramMdl.getPrj190DefDsp() == 0) {
            paramMdl.setPrj190knDefDsp("TODO");
        } else {
            paramMdl.setPrj190knDefDsp(gsMsg.getMessage("cmn.project"));
        }


        //日付
        paramMdl.setPrj190knMainDspDateStr(
                biz.getTargetDateString(
                        Integer.parseInt(paramMdl.getPrj190mainDspDate())));

        //状態
        paramMdl.setPrj190knMainDspStatusStr(
                biz.getStatusString(
                        Integer.parseInt(paramMdl.getPrj190mainDspStatus())));

        //メンバ
        paramMdl.setPrj190knMainDspMemberStr(
                biz.getMemberString(
                        Integer.parseInt(paramMdl.getPrj190mainDspMember())));

    }

    /**
     * <br>[機  能] 個人設定更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj190knParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行時例外
     * @return addEditFlg 登録モード(0:登録 1:編集)
     */
    public int updateUserConf(Prj190knParamModel paramMdl, BaseUserModel buMdl)
        throws SQLException {

        int usrSid = buMdl.getUsrsid();
        UDate now = new UDate();
        int addEditFlg = 1;

        PrjUserConfModel pucMdl = new PrjUserConfModel();
        pucMdl.setUsrSid(usrSid);
        pucMdl.setPucEuid(usrSid);
        pucMdl.setPucEdate(now);
        pucMdl.setPucTodoDate(Integer.parseInt(paramMdl.getPrj190TodoDay()));
        pucMdl.setPucTodoProject(Integer.parseInt(paramMdl.getPrj190TodoPrj()));
        pucMdl.setPucTodoStatus(Integer.parseInt(paramMdl.getPrj190TodoSts()));
        pucMdl.setPucPrjProject(Integer.parseInt(paramMdl.getPrj190Project()));
        pucMdl.setPucDefDsp(paramMdl.getPrj190DefDsp());

        PrjUserConfDao pucDao = new PrjUserConfDao(con__);
        int updateCnt = pucDao.updateDashBoard(pucMdl);

        //更新件数が0件の場合、プロジェクト個人設定の新規登録を行う
        if (updateCnt == 0) {
            pucMdl.setPucPrjCnt(10);
            pucMdl.setPucTodoCnt(10);
            pucMdl.setPucAuid(usrSid);
            pucMdl.setPucAdate(now);
            pucMdl.setPucMainDate(GSConstProject.DATE_THE_PAST);
            pucMdl.setPucMainStatus(GSConstProject.STATUS_YOTEI_AND_MIKAN);
            pucMdl.setPucMainMember(GSConstProject.MEMBER_ALL);
            pucMdl.setPucSchKbn(GSConstProject.DSP_TODO_SHOW);
            pucMdl.setPucTodoDsp(GSConstProject.DSP_TODO_EASY);
            pucDao.insert(pucMdl);
            addEditFlg = 0;
        }

        pucMdl.setPucMainDate(Integer.parseInt(paramMdl.getPrj190mainDspDate()));
        pucMdl.setPucMainStatus(Integer.parseInt(paramMdl.getPrj190mainDspStatus()));
        pucMdl.setPucMainMember(Integer.parseInt(paramMdl.getPrj190mainDspMember()));

        pucDao.updatePrjMain(pucMdl);


        pucMdl.setPucTodoDsp(paramMdl.getPrj190todoInputKbn());
        pucDao.updatePrjTodoDsp(pucMdl);

        return addEditFlg;
    }

    /**
     * <br>[機  能]プロジェクト名を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param prjSid プロジェクトSID
     * @return プロジェクト名
     * @throws SQLException SQLException
     * @throws NumberFormatException NumberFormatException
     */
    public String getTargetName(Connection con, String prjSid)
            throws NumberFormatException, SQLException {

        String name = "";
        PrjPrjdataDao pDao = new PrjPrjdataDao(con);
        PrjPrjdataModel mdl = pDao.getProjectData(Integer.parseInt(prjSid));
        if (mdl != null) {
            name = mdl.getPrjName();
        }
        return name;
    }
}
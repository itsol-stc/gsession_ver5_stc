package jp.groupsession.v2.prj;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.dao.PrjMembersTmpDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataTmpDao;
import jp.groupsession.v2.prj.dao.PrjPrjstatusDao;
import jp.groupsession.v2.prj.dao.PrjPrjstatusTmpDao;
import jp.groupsession.v2.prj.dao.PrjStatusHistoryDao;
import jp.groupsession.v2.prj.dao.PrjTodoBinDao;
import jp.groupsession.v2.prj.dao.PrjTodocategoryDao;
import jp.groupsession.v2.prj.dao.PrjTodocategoryTmpDao;
import jp.groupsession.v2.prj.dao.PrjTodocommentDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.dao.PrjTodomemberDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusTmpDao;
import jp.groupsession.v2.prj.dao.PrjUserConfDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.dao.ProjectUpdateDao;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusModel;
import jp.groupsession.v2.prj.model.PrjTodoBinModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.PrjUserConfModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] ユーザ・グループに変更があった場合に実行されるリスナーを実装
 * <br>[解  説] プロジェクト管理についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjIUserGroupListenerImpl implements IUserGroupListener {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjIUserGroupListenerImpl.class);

    /**
     * <br>[機  能] ユーザ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid 追加されるユーザSID
     * @param cntCon MlCountMtController
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void addUser(MlCountMtController cntCon, Connection con, int usid,
            int eusid, RequestModel reqMdl) throws SQLException {

        /** マイプロジェクト登録 **********************************************/
        //採番コントローラー取得
        MlCountMtController mlCnt = null;
        try {
            mlCnt = GroupSession.getResourceManager().getCountController(reqMdl);
        } catch (Exception e) {
            log__.error("採番コントラーラの取得に失敗", e);
        }
        UDate now = new UDate();

        //プロジェクトSID採番
        int projectSid = (int) mlCnt.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                                                       GSConstProject.SBNSID_SUB_PROJECT,
                                                       eusid);
        //プロジェクト更新Model
        PrjPrjdataModel ppMdl = __getPrjUpdateMdl(projectSid, eusid, now, reqMdl);
        //プロジェクト状態
        ProjectStatusModel prjStatusMdl = __getProjectStatusModel(reqMdl);
        //プロジェクトメンバー更新Modelリスト
        List<PrjMembersModel> memberList = __getMemberUpdateList(projectSid, eusid, now, usid);

        //プロジェクト情報を更新する
        ProjectUpdateDao projectDao = new ProjectUpdateDao(con);
        projectDao.insertProject(ppMdl, prjStatusMdl, memberList);


        /** プロジェクト個人設定登録 *******************************************/
        PrjUserConfDao pucDao = new PrjUserConfDao(con);
        if (pucDao.select(usid) == null) {
            PrjUserConfModel pucMdl = new PrjUserConfModel();
            pucMdl.setUsrSid(usid);
            pucMdl.setPucPrjCnt(GSConst.LIST_COUNT_LIMIT);
            pucMdl.setPucTodoCnt(GSConst.LIST_COUNT_LIMIT);
            pucMdl.setPucEuid(eusid);
            pucMdl.setPucEdate(now);
            pucMdl.setPucAuid(eusid);
            pucMdl.setPucAdate(now);
            pucMdl.setPucTodoDate(GSConstProject.DATE_THE_PAST);
            pucMdl.setPucTodoProject(GSConstProject.PROJECT_ALL);
            pucMdl.setPucTodoStatus(GSConstProject.STATUS_YOTEI_AND_MIKAN);
            pucMdl.setPucPrjProject(GSConstProject.PRJ_MEMBER_NOT_END);
            pucMdl.setPucMainDate(GSConstProject.DATE_THE_PAST);
            pucMdl.setPucMainStatus(GSConstProject.STATUS_YOTEI_AND_MIKAN);
            pucMdl.setPucMainMember(GSConstProject.MEMBER_ALL);
            pucMdl.setPucDefDsp(GSConstProject.DSP_TODO);
            pucMdl.setPucSchKbn(GSConstProject.DSP_TODO_SHOW);
            pucMdl.setPucTodoDsp(GSConstProject.DSP_TODO_EASY);
            pucDao.insert(pucMdl);
        }
    }

    /**
     * <br>[機  能] ユーザ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUser(Connection con, int usid,
            int eusid, RequestModel reqMdl) throws SQLException {

        //プロジェクト個人設定を削除する
        PrjUserConfDao pucDao = new PrjUserConfDao(con);
        pucDao.delete(usid);

        //マイプロジェクトのSIDを取得する
        ProjectSearchDao searchDao = new ProjectSearchDao(con);
        int myPrjSid = searchDao.getMyPrjSid(usid);
        log__.debug("*** マイプロジェクトSID = " + myPrjSid);

        if (myPrjSid == -1) {
            return;
        }

        //TODO情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        PrjTododataDao prjTodoDao = new PrjTododataDao(con);
        List<PrjTododataModel> todoDataList = prjTodoDao.getTodoList(myPrjSid);
        List<Integer> ptdSidList = new ArrayList<Integer>();
        for (PrjTododataModel model : todoDataList) {
            ptdSidList.add(model.getPtdSid());
        }
        PrjUsedDataBiz usedDataBiz = new PrjUsedDataBiz(con);
        usedDataBiz.insertTodoSize(ptdSidList, false);

        log__.debug("*** マイプロジェクト削除");
        PrjPrjdataDao prjDataDao = new PrjPrjdataDao(con);
        prjDataDao.delete(myPrjSid);

        log__.debug("*** マイプロジェクト状態削除");
        PrjPrjstatusDao prjStsDao = new PrjPrjstatusDao(con);
        prjStsDao.deletePrjAll(myPrjSid);

        log__.debug("*** マイプロジェクトメンバー削除");
        PrjMembersDao prjMemDao = new PrjMembersDao(con);
        prjMemDao.delete(myPrjSid);

        log__.debug("*** マイプロジェクトTODOカテゴリ削除");
        PrjTodocategoryDao prjTodoCatDao = new PrjTodocategoryDao(con);
        prjTodoCatDao.deletePrjAll(myPrjSid);

        log__.debug("*** マイプロジェクトTODO状態削除");
        PrjTodostatusDao prjTodoStsDao = new PrjTodostatusDao(con);
        prjTodoStsDao.deletePrjAll(myPrjSid);

        log__.debug("*** マイプロジェクトTODO削除");
        prjTodoDao.delete(myPrjSid);

        log__.debug("*** マイプロジェクトTODO変更履歴削除");
        PrjStatusHistoryDao prjTodoHisDao = new PrjStatusHistoryDao(con);
        prjTodoHisDao.delete(myPrjSid);

        log__.debug("*** マイプロジェクトTODOメンバー削除");
        PrjTodomemberDao prjTodoMemDao = new PrjTodomemberDao(con);
        prjTodoMemDao.delete(myPrjSid);

        log__.debug("*** マイプロジェクトTODOバイナリSID取得");
        PrjTodoBinDao prjTodoBinDao = new PrjTodoBinDao(con);
        List<PrjTodoBinModel> prjTodoBinList = prjTodoBinDao.getBinList(myPrjSid);

        if (!prjTodoBinList.isEmpty()) {

            log__.debug("*** マイプロジェクトTODOバイナリ削除");
            prjTodoBinDao.delete(myPrjSid);

            log__.debug("*** バイナリ(論理)削除");
            CmnBinfDao binfDao = new CmnBinfDao(con);

            CmnBinfModel binfParam = new CmnBinfModel();
            binfParam.setBinJkbn(GSConst.JTKBN_DELETE);
            binfParam.setBinUpuser(usid);
            binfParam.setBinUpdate(new UDate());

            List<Long> binDelList = new ArrayList<Long>();
            for (PrjTodoBinModel delMdl : prjTodoBinList) {
                binDelList.add(delMdl.getBinSid());
            }

            binfDao.updateJKbn(binfParam, binDelList);
        }

        log__.debug("*** マイプロジェクトTODOコメント削除");
        PrjTodocommentDao prjTodoComDao = new PrjTodocommentDao(con);
        prjTodoComDao.delete(myPrjSid);

        log__.debug("*** プロジェクトテンプレート(個人)SID取得");
        PrjPrjdataTmpDao prjDataTmpDao = new PrjPrjdataTmpDao(con);
        ArrayList<PrjPrjdataTmpModel> prjTmpSidList =
            prjDataTmpDao.selectTemlateList(GSConstProject.PRT_KBN_KOJIN, usid);

        if (!prjTmpSidList.isEmpty()) {

            log__.debug("*** プロジェクトテンプレート(個人)削除");
            prjDataTmpDao.deletePrjTemplate(prjTmpSidList);

            log__.debug("*** プロジェクトテンプレート状態削除");
            PrjPrjstatusTmpDao prjStsTmpDao = new PrjPrjstatusTmpDao(con);
            prjStsTmpDao.deletePrjTemplateStatus(prjTmpSidList);

            log__.debug("*** プロジェクトテンプレートメンバー削除");
            PrjMembersTmpDao prjMemTempDao = new PrjMembersTmpDao(con);
            prjMemTempDao.deletePrjTemplateMember(prjTmpSidList);

            log__.debug("*** プロジェクトテンプレートTODOカテゴリ削除");
            PrjTodocategoryTmpDao prjTodoCatTmpDao = new PrjTodocategoryTmpDao(con);
            prjTodoCatTmpDao.deletePrjTemplateTodoCat(prjTmpSidList);

            log__.debug("*** プロジェクトテンプレートTODO状態削除");
            PrjTodostatusTmpDao prjTodoStsTmpDao = new PrjTodostatusTmpDao(con);
            prjTodoStsTmpDao.deletePrjTemplateTodoSts(prjTmpSidList);
        }
    }

    /**
     * <br>[機  能] グループ追加時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void addGroup(Connection con, int gsid, int eusid) throws SQLException {
    }

    /**
     * <br>[機  能] グループ削除時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     */
    public void deleteGroup(
            Connection con, int gsid, int eusid, RequestModel reqMdl) throws SQLException {
    }

    /**
     * <br>[機  能] ユーザの所属グループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param pastGsids 変更前のグループSID配列
     * @param gsids 変更後のグループSID配列
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeBelong(Connection con, int usid, int[] pastGsids, int[] gsids, int eusid)
    throws SQLException {
    }

    /**
     * <br>[機  能] ユーザのデフォルトグループが変更になった場合に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param gsid 変更後のデフォルトグループ
     * @param con DBコネクション
     * @param eusid 更新者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeDefaultBelong(Connection con, int usid, int gsid, int eusid)
    throws SQLException {
    }

    /**
     * <br>[機  能] 更新用のPrjPrjdataModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param reqMdl リクエスト情報
     * @return PrjPrjdataModel
     */
    private PrjPrjdataModel __getPrjUpdateMdl(int projectSid, int userSid,
            UDate now, RequestModel reqMdl) {

        UDate startDate = null;
        UDate andDate = null;
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        //マイプロジェクト
        String textMyPrj = gsMsg.getMessage("project.src.73");

        PrjPrjdataModel ppMdl = new PrjPrjdataModel();
        ppMdl.setPrjSid(projectSid);
        ppMdl.setPrjMyKbn(GSConstProject.KBN_MY_PRJ_MY);
        ppMdl.setPrjId("MY_PROJECT");
        ppMdl.setPrjName(textMyPrj);
        ppMdl.setPrjNameShort(textMyPrj);
        ppMdl.setPrjYosan(GSConstCommon.NUM_INIT);
        ppMdl.setPrjKoukaiKbn(GSConstProject.KBN_KOUKAI_DISABLED);
        ppMdl.setPrjDateStart(startDate);
        ppMdl.setPrjDateEnd(andDate);
        ppMdl.setPrjStatusSid(GSConstProject.STATUS_MYPRJ);
        ppMdl.setPrjTarget(null);
        ppMdl.setPrjContent(null);
        ppMdl.setPrjEdit(GSConstProject.TODO_EDIT_KENGEN_MEM);
        ppMdl.setPrjAuid(userSid);
        ppMdl.setPrjAdate(now);
        ppMdl.setPrjEuid(userSid);
        ppMdl.setPrjEdate(now);
        return ppMdl;
    }

    /**
     * <br>[機  能] 更新用のPrjMembersModelのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param usid 追加されるユーザSID
     * @return List in PrjMembersModel
     */
    private List<PrjMembersModel> __getMemberUpdateList(
        int projectSid,
        int userSid,
        UDate now,
        int usid) {

        List<PrjMembersModel> memberList = new ArrayList<PrjMembersModel>();
        PrjMembersModel memberMdl = new PrjMembersModel();
        memberMdl.setPrjSid(projectSid);
        memberMdl.setUsrSid(usid);
        memberMdl.setPrmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
        memberMdl.setPrmAdminKbn(GSConstProject.KBN_POWER_ADMIN);
        memberMdl.setPrmAuid(userSid);
        memberMdl.setPrmAdate(now);
        memberMdl.setPrmEuid(userSid);
        memberMdl.setPrmEdate(now);
        memberMdl.setPrmSort(1);
        memberList.add(memberMdl);
        return memberList;
    }

    /**
     * <br>[機  能] ProjectStatusModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ProjectStatusModel
     */
    private ProjectStatusModel __getProjectStatusModel(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        //マイプロジェクト
        String textMyPrj = gsMsg.getMessage("project.src.73");
        //予定
        String textYotei = gsMsg.getMessage("project.prj010.8");
        //プロジェクト状態
        ProjectStatusModel prjStatusMdl = new ProjectStatusModel();
        //完了
        String textCmoplete = gsMsg.getMessage("cmn.complete");

        //プロジェクト状態
        List<PrjPrjstatusModel> prjStatusList = new ArrayList<PrjPrjstatusModel>();
        PrjPrjstatusModel ppsMdl = new PrjPrjstatusModel();
        ppsMdl.setPrsSid(GSConstProject.STATUS_MYPRJ);
        ppsMdl.setPrsSort(0);
        ppsMdl.setPrsName(textMyPrj);
        ppsMdl.setPrsRate(GSConstProject.RATE_MIN);
        prjStatusList.add(ppsMdl);

        //TODO状態
        List<PrjTodostatusModel> todoStatusList = new ArrayList<PrjTodostatusModel>();
        PrjTodostatusModel ptsMdl = new PrjTodostatusModel();
        ptsMdl.setPtsSid(1);
        ptsMdl.setPtsSort(1);
        ptsMdl.setPtsName(textYotei);
        ptsMdl.setPtsRate(GSConstProject.RATE_MIN);
        todoStatusList.add(ptsMdl);

        ptsMdl = new PrjTodostatusModel();
        ptsMdl.setPtsSid(2);
        ptsMdl.setPtsSort(2);
        ptsMdl.setPtsName(textCmoplete);
        ptsMdl.setPtsRate(GSConstProject.RATE_MAX);
        todoStatusList.add(ptsMdl);

        //プロジェクト状態を取得
        prjStatusMdl.setPrjStatusList(prjStatusList);
        //TODO状態を取得
        prjStatusMdl.setTodoStatusList(todoStatusList);

        return prjStatusMdl;
    }
}
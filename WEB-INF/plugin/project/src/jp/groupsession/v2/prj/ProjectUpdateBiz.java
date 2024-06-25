package jp.groupsession.v2.prj;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
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
import jp.groupsession.v2.prj.dao.ProjectUpdateDao;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjMembersTmpModel;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusTmpModel;
import jp.groupsession.v2.prj.model.PrjTodoBinModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryTmpModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.PrjTodostatusTmpModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusTmpModel;

/**
 * <br>[機  能] プロジェクト情報やTODOの登録、更新を行うビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ProjectUpdateBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public ProjectUpdateBiz(Connection con) {
        con__ = con;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] プロジェクト情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cbMdl CmnBinfModel
     * @param binList バイナリSIDリスト
     * @param projectSid プロジェクトSID
     * @throws SQLException SQL実行例外
     */
    public void deleteProject(CmnBinfModel cbMdl, List<Long> binList, int projectSid)
    throws SQLException {

        //TODO情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        PrjTododataDao ptDao = new PrjTododataDao(getCon());
        List<Integer> todoSidList = ptDao.getTodoSidList(projectSid);
        PrjUsedDataBiz usedDataBiz = new PrjUsedDataBiz(con__);
        usedDataBiz.insertTodoSize(todoSidList, false);

        //バイナリー情報を論理削除する
        CmnBinfDao cbDao = new CmnBinfDao(getCon());
        cbDao.updateJKbn(cbMdl, binList);

        //TODO情報とTODOに紐付くデータを物理削除する
        //TODO情報削除
        ptDao.delete(projectSid);
        //TODO変更履歴情報
        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(getCon());
        pshDao.delete(projectSid);
        //TODO担当者情報
        PrjTodomemberDao ptmDao = new PrjTodomemberDao(getCon());
        ptmDao.delete(projectSid);
        //TODO添付情報
        PrjTodoBinDao ptbDao = new PrjTodoBinDao(getCon());
        ptbDao.delete(projectSid);
        //TODOコメント情報
        PrjTodocommentDao ptcDao = new PrjTodocommentDao(getCon());
        ptcDao.delete(projectSid);

        //プロジェクト情報とプロジェクトに紐付くデータを物理削除する
        //プロジェクト情報
        PrjPrjdataDao ppdDao = new PrjPrjdataDao(getCon());
        ppdDao.delete(projectSid);
        //プロジェクト状態マスタ
        PrjPrjstatusDao ppsDao = new PrjPrjstatusDao(getCon());
        ppsDao.deletePrjAll(projectSid);
        //プロジェクトメンバー情報
        PrjMembersDao pmDao = new PrjMembersDao(getCon());
        pmDao.delete(projectSid);
        //TODOカテゴリマスタ
        PrjTodocategoryDao ptcaDao = new PrjTodocategoryDao(getCon());
        ptcaDao.deletePrjAll(projectSid);
        //TODO状態マスタ
        PrjTodostatusDao ptsDao = new PrjTodostatusDao(getCon());
        ptsDao.deletePrjAll(projectSid);
    }

    /**
     * <br>[機  能] プロジェクトテンプレート情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid プロジェクトSID
     * @throws SQLException SQL実行例外
     */
    public void deleteProjectTmp(int prtSid) throws SQLException {

        //プロジェクトテンプレート情報を物理削除する
        //プロジェクトテンプレート情報
        PrjPrjdataTmpDao ppdDao = new PrjPrjdataTmpDao(getCon());
        ppdDao.delete(prtSid);

        //プロジェクト状態_テンプレートマスタ
        PrjPrjstatusTmpDao ppsDao = new PrjPrjstatusTmpDao(getCon());
        ppsDao.deleteAll(prtSid);

        //プロジェクトメンバー情報
        PrjMembersTmpDao pmDao = new PrjMembersTmpDao(getCon());
        pmDao.deleteAll(prtSid);

        //TODOカテゴリマスタ
        PrjTodocategoryTmpDao ptcaDao = new PrjTodocategoryTmpDao(getCon());
        ptcaDao.deleteAll(prtSid);

        //TODO状態マスタ
        PrjTodostatusTmpDao ptsDao = new PrjTodostatusTmpDao(getCon());
        ptsDao.deleteAll(prtSid);
    }

    /**
     * <br>[機  能] TODO情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSidList TODOSID
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteTodo(String[] todoSidList, int userSid) throws SQLException {

        //TODO情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        List<Integer> intTodoSidList = new ArrayList<Integer>();
        for (String todoSid : todoSidList) {
            intTodoSidList.add(Integer.parseInt(todoSid));
        }
        PrjUsedDataBiz usedDataBiz = new PrjUsedDataBiz(con__);
        usedDataBiz.insertTodoSize(intTodoSidList, false);


        //TODO情報に紐付く添付ファイル情報を取得する
        PrjTodoBinDao ptbDao = new PrjTodoBinDao(getCon());
        List<PrjTodoBinModel> ptbList = ptbDao.getBinList(todoSidList);

        List<Long> binList = new ArrayList<Long>();
        for (PrjTodoBinModel ptbMdl : ptbList) {
            binList.add(ptbMdl.getBinSid());
        }

        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(userSid);
        cbMdl.setBinUpdate(new UDate());

        //バイナリー情報を論理削除する
        CmnBinfDao cbDao = new CmnBinfDao(getCon());
        cbDao.updateJKbn(cbMdl, binList);

        //TODO情報とTODOに紐付くデータを物理削除する
        int todoSid = 0;
        PrjTododataDao ptDao = new PrjTododataDao(getCon());
        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(getCon());
        PrjTodomemberDao ptmDao = new PrjTodomemberDao(getCon());
        PrjTodocommentDao ptcDao = new PrjTodocommentDao(getCon());
        for (String strTodoSid : todoSidList) {
            todoSid = Integer.parseInt(strTodoSid);

            //TODO情報削除
            ptDao.deleteFromTodoSid(todoSid);
            //TODO変更履歴情報
            pshDao.deleteFromTodoSid(todoSid);
            //TODO担当者情報
            ptmDao.deleteFromTodoSid(todoSid);
            //TODO添付情報
            ptbDao.deleteFromTodoSid(todoSid);
            //TODOコメント情報
            ptcDao.deleteFromTodoSid(todoSid);
        }
    }

    /**
     * <br>[機  能] プロジェクト単位でTODO情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param cbMdl CmnBinfModel
     * @param binList バイナリSIDリスト
     * @param prjSid プロジェクトSID
     * @throws SQLException SQL実行例外
     */
    public void deleteTodoPrj(CmnBinfModel cbMdl, List<Long> binList, int prjSid)
    throws SQLException {

        //TODO情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        PrjTododataDao ptDao = new PrjTododataDao(getCon());
        List<Integer> todoSidList = ptDao.getTodoSidList(prjSid);
        PrjUsedDataBiz usedDataBiz = new PrjUsedDataBiz(con__);
        usedDataBiz.insertTodoSize(todoSidList, false);

        //バイナリー情報を論理削除する
        CmnBinfDao cbDao = new CmnBinfDao(getCon());
        cbDao.updateJKbn(cbMdl, binList);

        //TODO情報とTODOに紐付くデータを物理削除する
        //TODO情報削除
        ptDao.delete(prjSid);
        //TODO変更履歴情報
        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(getCon());
        pshDao.delete(prjSid);
        //TODO担当者情報
        PrjTodomemberDao ptmDao = new PrjTodomemberDao(getCon());
        ptmDao.delete(prjSid);
        //TODO添付情報
        PrjTodoBinDao ptbDao = new PrjTodoBinDao(getCon());
        ptbDao.delete(prjSid);
        //TODOコメント情報
        PrjTodocommentDao ptcDao = new PrjTodocommentDao(getCon());
        ptcDao.delete(prjSid);

    }

    /**
     * <br>[機  能] プロジェクト情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param prjStatus プロジェクト状態
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void updateProject(
        PrjPrjdataModel ppMdl,
        ProjectStatusModel prjStatus,
        List<PrjMembersModel> memberList) throws SQLException {

        List<PrjPrjstatusModel> prjStatusList = prjStatus.getPrjStatusList();
        List<PrjTodocategoryModel> todoCateList = prjStatus.getTodoCateList();
        List<PrjTodostatusModel> todoStatusList = prjStatus.getTodoStatusList();
        HashMap<String, String> updateCate = prjStatus.getUpdateCate();
        HashMap<String, String> updateStatus = prjStatus.getUpdateStatus();

        //プロジェクト情報を更新する
        PrjPrjdataDao ppdDao = new PrjPrjdataDao(getCon());
        ppdDao.update(ppMdl);

        //プロジェクト情報を更新する
        ProjectUpdateDao prjUpDao = new ProjectUpdateDao(getCon());


        if (ppMdl.getPrjMyKbn() == GSConstProject.KBN_MY_PRJ_DEF) {
            //不要になったプロジェクト状態を削除する
            prjUpDao.deleteStatus(ppMdl.getPrjSid(), prjStatusList);
            //プロジェクト状態を登録・更新する
            prjUpDao.updateProjectStatus(ppMdl, prjStatusList);
        }

        //不要になったプロジェクトメンバーを削除する
        prjUpDao.deleteProjectMember(ppMdl.getPrjSid(), memberList);
        //プロジェクトメンバーを登録・更新する
        prjUpDao.updateProjectMember(memberList);

        //TODO情報のカテゴリを更新する
        prjUpDao.updateCate(updateCate, ppMdl);
        //不要になったTODOカテゴリを削除する
        prjUpDao.deleteProjectCate(ppMdl.getPrjSid(), todoCateList);
        //TODOカテゴリを登録・更新する
        prjUpDao.updateTodoCate(ppMdl, todoCateList);
        //カテゴリがマスタに存在しない場合、TODO情報のカテゴリを「未選択」に更新する
        prjUpDao.updateCateNotExists(ppMdl);

        //TODO情報のTODO状態を更新する
        prjUpDao.updateStatus(updateStatus, ppMdl);
        //TODO変更履歴のTODO状態を更新する
        prjUpDao.updateTodoHisStatus(updateStatus, ppMdl);
        //不要になったTODO状態を削除する
        prjUpDao.deleteTodoStatus(ppMdl.getPrjSid(), todoStatusList);
        //TODO状態を登録・更新する
        prjUpDao.updateTodoStatus(ppMdl, todoStatusList);
        //TODO状態がマスタに存在しない場合、TODO情報のTODO状態を0%に更新する
        prjUpDao.updateStatusNotExists(ppMdl.getPrjEuid(), ppMdl.getPrjEdate(), ppMdl.getPrjSid());
        //TODO状態がマスタに存在しない場合、TODO変更履歴のTODO状態を0%に更新する
        prjUpDao.updateTodoHisStatusNotExists(
                ppMdl.getPrjEuid(), ppMdl.getPrjEdate(), ppMdl.getPrjSid());
    }

    /**
     * <br>[機  能] プロジェクトテンプレート情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param prjStatus プロジェクト状態
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void updateProjectTmp(PrjPrjdataTmpModel ppMdl,
                                  ProjectStatusTmpModel prjStatus,
                                  List<PrjMembersTmpModel> memberList)
        throws SQLException {

        List<PrjPrjstatusTmpModel> prjStatusList = prjStatus.getPrjStatusList();
        List<PrjTodocategoryTmpModel> todoCateList = prjStatus.getTodoCateList();
        List<PrjTodostatusTmpModel> todoStatusList = prjStatus.getTodoStatusList();

        //プロジェクトテンプレート情報を更新する
        ProjectUpdateDao prjUpDao = new ProjectUpdateDao(getCon());
        PrjPrjdataTmpDao ppdDao = new PrjPrjdataTmpDao(getCon());
        ppdDao.updateTemplate(ppMdl);

        //不要になったプロジェクトテンプレート状態を削除する
        prjUpDao.deleteStatusTmp(ppMdl.getPrtSid(), prjStatusList);
        //プロジェクトテンプレート状態を登録・更新する
        prjUpDao.updateProjectStatusTmp(ppMdl, prjStatusList);

        //不要になったプロジェクトテンプレートメンバーを削除する
        prjUpDao.deleteProjectMemberTmp(ppMdl.getPrtSid(), memberList);
        //プロジェクトテンプレートメンバーを登録・更新する
        prjUpDao.updateProjectMemberTmp(memberList);

        //不要になったTODOカテゴリ_テンプレートを削除する
        prjUpDao.deleteProjectCateTmp(ppMdl.getPrtSid(), todoCateList);
        //TODOカテゴリ_テンプレートを登録・更新する
        prjUpDao.updateTodoCateTmp(ppMdl, todoCateList);

        //不要になったTODO状態を削除する
        prjUpDao.deleteTodoStatusTmp(ppMdl.getPrtSid(), todoStatusList);
        //TODO状態を登録・更新する
        prjUpDao.updateTodoStatusTmp(ppMdl, todoStatusList);

    }

}

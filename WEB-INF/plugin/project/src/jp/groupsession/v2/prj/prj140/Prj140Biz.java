package jp.groupsession.v2.prj.prj140;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.ProjectUpdateBiz;
import jp.groupsession.v2.prj.dao.PrjMembersTmpDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataTmpDao;
import jp.groupsession.v2.prj.dao.PrjPrjstatusTmpDao;
import jp.groupsession.v2.prj.dao.PrjTodocategoryTmpDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusTmpDao;
import jp.groupsession.v2.prj.dao.ProjectUpdateDao;
import jp.groupsession.v2.prj.model.PrjMembersTmpModel;
import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusTmpModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryTmpModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.PrjTodostatusTmpModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusTmpModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトテンプレート登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj140Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj140Biz.class);

    /** ヘルプモード 個人テンプレート登録*/
    public static final String HELP_MODE_TMP_KOJIN_ADD = "0";
    /** ヘルプモード 個人テンプレート編集*/
    public static final String HELP_MODE_TMP_KOJIN_EDIT = "1";
    /** ヘルプモード 共有テンプレート登録*/
    public static final String HELP_MODE_TMP_KYOUYU_ADD = "2";
    /** ヘルプモード 共有テンプレート編集*/
    public static final String HELP_MODE_TMP_KYOUYU_EDIT = "3";
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj140";
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID_DEF = "prj020";

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    public Prj140Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl アクションフォーム
     * @param userSid ユーザSID
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void setInitData(
            Prj140ParamModel paramMdl, int userSid, String rootDir)
                    throws SQLException, IOToolsException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //プロジェクトテンプレートSID取得
        int prtSid = paramMdl.getPrtSid();

        //プロジェクト状態
        ProjectStatusTmpModel prjStatusMdl = null;

        if (prtSid < 1) {

            log__.debug("テンプレート新規登録");

            //初期設定を行い、プロジェクト状態Modelを返す
            prjStatusMdl = __setAddInit(paramMdl, userSid);

            //ヘルプパラメータを設定する
            if (paramMdl.getPrjTmpMode() == GSConstProject.MODE_TMP_KOJIN) {
                paramMdl.setPrj140cmdMode(HELP_MODE_TMP_KOJIN_ADD);
            } else {
                paramMdl.setPrj140cmdMode(HELP_MODE_TMP_KYOUYU_ADD);
            }

        } else {

            log__.debug("テンプレート編集");

            //初期設定を行い、プロジェクト状態Modelを返す
            prjStatusMdl = __setEditInit(paramMdl, userSid, paramMdl.getPrtSid());

            //ヘルプパラメータを設定する
            if (paramMdl.getPrjTmpMode() == GSConstProject.MODE_TMP_KOJIN) {
                paramMdl.setPrj140cmdMode(HELP_MODE_TMP_KOJIN_EDIT);
            } else {
                paramMdl.setPrj140cmdMode(HELP_MODE_TMP_KYOUYU_EDIT);
            }
        }

        //プロジェクト状態をオブジェクトファイルに保存
        __saveFile(rootDir, prjStatusMdl);

        //グループにデフォルトグループを設定
        GroupBiz grpBiz = new GroupBiz();
        paramMdl.setPrj140group(grpBiz.getDefaultGroupSid(userSid, con__));
    }

    /**
     * <br>[機  能] 登録時の初期設定を行い、プロジェクト状態Modelを返す
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl アクションフォーム
     * @param userSid ユーザSID
     * @return ProjectStatusModel
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ProjectStatusTmpModel __setAddInit(Prj140ParamModel paramMdl, int userSid)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //予定
        String textYotei = gsMsg.getMessage("project.prj010.8");
        //プロジェクト状態
        ProjectStatusTmpModel prjStatusMdl = new ProjectStatusTmpModel();
        //完了
        String textCmoplete = gsMsg.getMessage("cmn.complete");

        //プロジェクト状態に初期値をセットする
        paramMdl.setPrj140status(1);

        //プロジェクトメンバー・管理者に初期値をセットする
        String[] member = {String.valueOf(userSid) + GSConst.GSESSION2_ID};
        String[] admin = {String.valueOf(userSid)};

        //所属メンバー
        paramMdl.setPrj140hdnMember(member);
        paramMdl.setPrj140hdnMemberSid(new String[]{String.valueOf(userSid)});
        //プロジェクト管理者
        paramMdl.setPrj140hdnAdmin(admin);

        //プロジェクトテンプレート状態
        List<PrjPrjstatusTmpModel> prjStatusTmpList = new ArrayList<PrjPrjstatusTmpModel>();
        PrjPrjstatusTmpModel pttMdl = new PrjPrjstatusTmpModel();
        pttMdl.setPttSid(GSConstProject.STATUS_0);
        pttMdl.setPttSort(1);
        pttMdl.setPttName(textYotei);
        pttMdl.setPttRate(GSConstProject.RATE_MIN);
        prjStatusTmpList.add(pttMdl);

        pttMdl = new PrjPrjstatusTmpModel();
        pttMdl.setPttSid(GSConstProject.STATUS_100);
        pttMdl.setPttSort(2);
        pttMdl.setPttName(textCmoplete);
        pttMdl.setPttRate(GSConstProject.RATE_MAX);
        prjStatusTmpList.add(pttMdl);

        //TODO状態
        List<PrjTodostatusTmpModel> todoStatusTmpList = new ArrayList<PrjTodostatusTmpModel>();
        PrjTodostatusTmpModel pstMdl = new PrjTodostatusTmpModel();
        pstMdl.setPstSid(1);
        pstMdl.setPstSort(1);
        pstMdl.setPstName(textYotei);
        pstMdl.setPstRate(GSConstProject.RATE_MIN);
        todoStatusTmpList.add(pstMdl);

        pstMdl = new PrjTodostatusTmpModel();
        pstMdl.setPstSid(2);
        pstMdl.setPstSort(2);
        pstMdl.setPstName(textCmoplete);
        pstMdl.setPstRate(GSConstProject.RATE_MAX);
        todoStatusTmpList.add(pstMdl);

        //プロジェクト状態を取得
        prjStatusMdl.setPrjStatusList(prjStatusTmpList);

        //TODO状態を取得
        prjStatusMdl.setTodoStatusList(todoStatusTmpList);

        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strDateNameJp = gsMsg.getMessage("cmn.start");
        String endDateNameJp = gsMsg.getMessage("cmn.end");

        //初期表示時は開始と終了にシステム年月日を設定
        UDate systemUd = new UDate();
        String year = String.valueOf(systemUd.getYear());
        String month = String.valueOf(systemUd.getMonth());
        String day = String.valueOf(systemUd.getIntDay());

        paramMdl.setPrj140startYear(year);
        paramMdl.setPrj140startMonth(month);
        paramMdl.setPrj140startDay(day);
        paramMdl.setPrj140endYear(year);
        paramMdl.setPrj140endMonth(month);
        paramMdl.setPrj140endDay(day);
        picker.setDateParam(
                paramMdl,
                "prj140startDate",
                "prj140startYear",
                "prj140startMonth",
                "prj140startDay",
                strDateNameJp);
        picker.setDateParam(
                paramMdl,
                "prj140endDate",
                "prj140endYear",
                "prj140endMonth",
                "prj140endDay",
                endDateNameJp);

        return prjStatusMdl;
    }

    /**
     * <br>[機  能] 編集時の初期設定を行い、プロジェクト状態Modelを返す
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl アクションフォーム
     * @param userSid ユーザSID
     * @param prtSid プロジェクトテンプレートSID
     * @return ProjectStatusModel
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ProjectStatusTmpModel __setEditInit(
            Prj140ParamModel paramMdl, int userSid, int prtSid)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //プロジェクト状態
        ProjectStatusTmpModel prjStatusMdl = new ProjectStatusTmpModel();

        //プロジェクト情報を取得する
        PrjPrjdataTmpDao ppDao = new PrjPrjdataTmpDao(con__);
        ProjectItemModel piMdl = ppDao.getProjectTmpInfo(prtSid);

        //テンプレート名称
        paramMdl.setPrj140prtTmpName(NullDefault.getString(piMdl.getPrtTmpName(), ""));
        //プロジェクトID
        paramMdl.setPrj140prtId(piMdl.getProjectId());
        //プロジェクト名称
        paramMdl.setPrj140prtName(piMdl.getProjectName());
        //プロジェクト略称
        paramMdl.setPrj140prtNameS(piMdl.getProjectRyakuName());
        //予算
        long yosan = piMdl.getYosan();
        if (yosan > GSConstCommon.NUM_INIT) {
            paramMdl.setPrj140yosan(String.valueOf(yosan));
        }
        //公開・非公開
        paramMdl.setPrj140koukai(piMdl.getKoukaiKbn());

        GsMessage gsMsg = new GsMessage(reqMdl__);
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strDateNameJp = gsMsg.getMessage("cmn.start");
        String endDateNameJp = gsMsg.getMessage("cmn.end");

        //開始年月日
        UDate start = piMdl.getStartDate();
        if (start != null) {
            paramMdl.setPrj140startYear(String.valueOf(start.getYear()));
            paramMdl.setPrj140startMonth(String.valueOf(start.getMonth()));
            paramMdl.setPrj140startDay(String.valueOf(start.getIntDay()));
            picker.setDateParam(
                    paramMdl,
                    "prj140startDate",
                    "prj140startYear",
                    "prj140startMonth",
                    "prj140startDay",
                    strDateNameJp);
        }
        //終了年月日
        UDate end = piMdl.getEndDate();
        if (end != null) {
            paramMdl.setPrj140endYear(String.valueOf(end.getYear()));
            paramMdl.setPrj140endMonth(String.valueOf(end.getMonth()));
            paramMdl.setPrj140endDay(String.valueOf(end.getIntDay()));
            picker.setDateParam(
                    paramMdl,
                    "prj140endDate",
                    "prj140endYear",
                    "prj140endMonth",
                    "prj140endDay",
                    endDateNameJp);
        }
        //状態
        paramMdl.setPrj140status(piMdl.getStatus());
        //目標・目的
        paramMdl.setPrj140mokuhyou(piMdl.getMokuhyou());
        //内容
        paramMdl.setPrj140naiyou(piMdl.getNaiyou());
        //編集権限
        paramMdl.setPrj140kengen(piMdl.getEditKengen());
        //ショートメール通知先
        paramMdl.setPrj140smailKbn(piMdl.getPrjMailKbn());

        //所属メンバーを取得
        PrjMembersTmpDao pmDao = new PrjMembersTmpDao(con__);
        List<PrjMembersTmpModel> pmList = pmDao.getPrjMembersTmp(prtSid);

        List<PrjMembersTmpModel> adminList = new ArrayList<PrjMembersTmpModel>();
        String[] member = new String[pmList.size()];
        String[] memberSid = new String[pmList.size()];

        int index = 0;
        for (PrjMembersTmpModel pmMdl : pmList) {
            if (pmMdl.getPmtAdminKbn() == GSConstProject.KBN_POWER_ADMIN) {
                adminList.add(pmMdl);
            }
            memberSid[index] = String.valueOf(pmMdl.getUsrSid());
            member[index] = String.valueOf(pmMdl.getUsrSid())
                            + GSConst.GSESSION2_ID
                + NullDefault.getString(pmMdl.getPmtMemKey(), "");;
            index++;
        }

        String[] admin = new String[adminList.size()];
        index = 0;
        for (PrjMembersTmpModel pmMdl : adminList) {
            admin[index] = String.valueOf(pmMdl.getUsrSid());
            index++;
        }
        //所属メンバー
        paramMdl.setPrj140hdnMember(member);
        //所属メンバーSID
        paramMdl.setPrj140hdnMemberSid(memberSid);
        //プロジェクト管理者
        paramMdl.setPrj140hdnAdmin(admin);

        //プロジェクト状態を取得
        PrjPrjstatusTmpDao ppsDao = new PrjPrjstatusTmpDao(con__);
        prjStatusMdl.setPrjStatusList(ppsDao.selectTmpProjects(prtSid));

        //TODOカテゴリを取得
        PrjTodocategoryTmpDao ptcDao = new PrjTodocategoryTmpDao(con__);
        prjStatusMdl.setTodoCateList(ptcDao.selectTmpProjects(prtSid));

        //TODO状態を取得
        PrjTodostatusTmpDao pts = new PrjTodostatusTmpDao(con__);
        prjStatusMdl.setTodoStatusList(pts.selectTmpProjects(prtSid));

        return prjStatusMdl;
    }

    /**
     * <br>[機  能] プロジェクト状態をオブジェクトファイルに保存
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param prjStatusMdl プロジェクト状態
     * @throws IOToolsException IOエラー
     */
    private void __saveFile(String rootDir,
                             ProjectStatusTmpModel prjStatusMdl) throws IOToolsException {

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl__,
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                GSConstProject.TEMP_STATUS_PRJ);

        log__.debug("ディレクトリ = " + filePath);
        log__.debug("ファイル名 = " + GSConstProject.SAVE_FILENAME);

        //ディレクトリ存在チェック(なければ作成)
        IOTools.isDirCheck(filePath, true);

        //ファイル保存
        ObjectFile objFile = new ObjectFile(filePath, GSConstProject.SAVE_FILENAME);
        objFile.save(prjStatusMdl);
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl アクションフォーム
     * @param pconfig プラグイン設定情報
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void getDspData(Prj140ParamModel paramMdl, PluginConfig pconfig,
                            String rootDir)
        throws SQLException, IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();

        //所属メンバー
        String[] member = paramMdl.getPrj140hdnMember();
        String[] convMember = __getUserSidList(paramMdl);

        //プロジェクト管理者
        String[] admin = paramMdl.getPrj140hdnAdmin();
        //管理者以外メンバー
        String[] notAdmin = cmnBiz.getDeleteMember(admin, convMember);
        //グループSID
        int groupSid = paramMdl.getPrj140group();

        //グループラベルを設定する。
        GsMessage gsMsg = new GsMessage(reqMdl__);
        GroupBiz grpBiz = new GroupBiz();
        List<LabelValueBean> groupLabel = grpBiz.getGroupCombLabelList(con__, true, gsMsg);
        groupLabel.remove(0);
        paramMdl.setGroupLabel(groupLabel);

        //所属メンバーラベル
        UserBiz userBiz = new UserBiz();
        paramMdl.setSyozokuMemberLabel(userBiz.getUserPrjLabelList(con__, member));

        //未所属ユーザラベル
        paramMdl.setUserLabel(userBiz.getNormalUserLabelList(
                                   con__, groupSid, convMember, false, gsMsg));

        //管理者メンバーラベル
        paramMdl.setAdminMemberLabel(userBiz.getUserLabelList(con__, admin));

        //管理者以外メンバー
        paramMdl.setMemberLabel(userBiz.getUserLabelList(con__, notAdmin));

        //プロジェクト状態
        paramMdl.setPrjStatusTmpMdl(PrjCommonBiz.getProjectStatusTmpModel(
                reqMdl__, TEMP_DIRECTORY_ID));

        //ショートメール使用有無
        paramMdl.setUseSmail(pconfig.getPlugin(GSConstProject.PLUGIN_ID_SMAIL) != null);
    }

    /**
     * <br>[機  能] ユーザSID配列からユーザSID部分を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @return ret ユーザ情報Model
     * @throws SQLException SQL実行時例外
     */
    private String[] __getUserSidList(Prj140ParamModel paramMdl) throws SQLException {

        String[] hdnMember = paramMdl.getPrj140hdnMember();
        String[] spUsrSidList = null;

        if (hdnMember != null && hdnMember.length > 0) {

            int idx = 0;
            spUsrSidList = new String[hdnMember.length];

            for (String hdn : hdnMember) {

                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                spUsrSidList[idx] = String.valueOf(splitStr[0]);

                idx += 1;
            }
        }

        return spUsrSidList;
    }

    /**
     * <br>[機  能] プロジェクトを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteProject(Prj140ParamModel paramMdl, int userSid) throws SQLException {

        //プロジェクトテンプレートSID
        int prtSid = paramMdl.getPrtSid();
        boolean commitFlg = false;

        try {

            con__.setAutoCommit(false);

            //プロジェクト情報を削除する
            ProjectUpdateBiz projectBiz = new ProjectUpdateBiz(con__);
            projectBiz.deleteProjectTmp(prtSid);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj140ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void doAddEdit(Prj140ParamModel paramMdl,
                           MlCountMtController cntCon,
                           int userSid,
                           String rootDir)
        throws SQLException, IOToolsException {

        //プロジェクト状態
        ProjectStatusTmpModel prjStatus =
            PrjCommonBiz.getProjectStatusTmpModel(reqMdl__,
                    TEMP_DIRECTORY_ID);

        if (paramMdl.getPrtSid() < 1) {

            //登録
            __doInsert(paramMdl, cntCon, userSid, prjStatus);

        } else {

            //更新
            __doUpdate(paramMdl, userSid, prjStatus);
        }
    }

    /**
     * <br>[機  能] 登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj140ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param prjStatus プロジェクト状態
     * @throws SQLException SQL実行例外
     */
    private void __doInsert(Prj140ParamModel paramMdl,
                             MlCountMtController cntCon,
                             int userSid,
                             ProjectStatusTmpModel prjStatus) throws SQLException {

        boolean commitFlg = false;
        UDate now = new UDate();

        try {

            con__.setAutoCommit(false);

            //プロジェクトテンプレートSID採番
            int prtSid =
                (int) cntCon.getSaibanNumber(
                        GSConstProject.SBNSID_PROJECT,
                        GSConstProject.SBNSID_SUB_PROJECT_TMP,
                        userSid);

            //プロジェクトテンプレート更新Model
            PrjPrjdataTmpModel ppMdl = __getPrjUpdateMdl(prtSid, paramMdl, userSid, now);

            //プロジェクトテンプレートメンバー更新Modelリスト
            List<PrjMembersTmpModel> memberList =
                __getMemberUpdateList(prtSid, paramMdl, userSid, now);

            //プロジェクトテンプレート情報を登録する
            ProjectUpdateDao projectDao = new ProjectUpdateDao(con__);
            projectDao.insertProjectTmp(ppMdl, prjStatus, memberList);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj140ParamModel
     * @param userSid ログインユーザSID
     * @param prjStatus プロジェクト状態
     * @throws SQLException SQL実行例外
     */
    private void __doUpdate(
            Prj140ParamModel paramMdl, int userSid, ProjectStatusTmpModel prjStatus)
        throws SQLException {

        boolean commitFlg = false;
        UDate now = new UDate();

        try {

            con__.setAutoCommit(false);

            //プロジェクトテンプレートSID
            int prtSid = paramMdl.getPrtSid();

            //プロジェクトテンプレート更新Model
            PrjPrjdataTmpModel ppMdl = __getPrjUpdateMdl(prtSid, paramMdl, userSid, now);

            //プロジェクトテンプレートメンバー更新Modelリスト
            List<PrjMembersTmpModel> memberList =
                __getMemberUpdateList(prtSid, paramMdl, userSid, now);

            //プロジェクト情報を更新する
            ProjectUpdateBiz projectBiz = new ProjectUpdateBiz(con__);
            projectBiz.updateProjectTmp(ppMdl, prjStatus, memberList);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 更新用のPrjPrjdataTmpModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid プロジェクトテンプレートSID
     * @param paramMdl Prj140ParamModel
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return PrjPrjdataModel
     */
    private PrjPrjdataTmpModel __getPrjUpdateMdl(int prtSid,
                                                  Prj140ParamModel paramMdl,
                                                  int userSid,
                                                  UDate now) {

        UDate startDate = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj140startYear(), -1),
                NullDefault.getInt(paramMdl.getPrj140startMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj140startDay(), -1));

        UDate andDate = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj140endYear(), -1),
                NullDefault.getInt(paramMdl.getPrj140endMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj140endDay(), -1));

        PrjPrjdataTmpModel ppMdl = new PrjPrjdataTmpModel();
        ppMdl.setPrtSid(prtSid);

        int prtKbn = -1;
        int prtUsrSid = 0;
        if (paramMdl.getPrjTmpMode() == GSConstProject.MODE_TMP_KOJIN) {
            prtKbn = GSConstProject.PRT_KBN_KOJIN;
            prtUsrSid = userSid;
        } else if (paramMdl.getPrjTmpMode() == GSConstProject.MODE_TMP_KYOYU) {
            prtKbn = GSConstProject.PRT_KBN_KYOYU;
        }
        ppMdl.setPrtKbn(prtKbn);
        ppMdl.setPrtTmpName(NullDefault.getString(paramMdl.getPrj140prtTmpName(), ""));
        ppMdl.setPrtUsrSid(prtUsrSid);
        ppMdl.setPrtId(NullDefault.getString(paramMdl.getPrj140prtId(), ""));
        ppMdl.setPrtName(NullDefault.getString(paramMdl.getPrj140prtName(), ""));
        ppMdl.setPrtNameShort(NullDefault.getString(paramMdl.getPrj140prtNameS(), ""));
        ppMdl.setPrtYosan(NullDefault.getInt(paramMdl.getPrj140yosan(), GSConstCommon.NUM_INIT));
        ppMdl.setPrtKoukaiKbn(paramMdl.getPrj140koukai());
        ppMdl.setPrtDateStart(startDate);
        ppMdl.setPrtDateEnd(andDate);
        ppMdl.setPrtStatusSid(paramMdl.getPrj140status());
        ppMdl.setPrtTarget(NullDefault.getString(paramMdl.getPrj140mokuhyou(), ""));
        ppMdl.setPrtContent(NullDefault.getString(paramMdl.getPrj140naiyou(), ""));
        ppMdl.setPrtEdit(paramMdl.getPrj140kengen());
        ppMdl.setPrtMailKbn(paramMdl.getPrj140smailKbn());
        ppMdl.setPrtAuid(userSid);
        ppMdl.setPrtAdate(now);
        ppMdl.setPrtEuid(userSid);
        ppMdl.setPrtEdate(now);
        return ppMdl;
    }

    /**
     * <br>[機  能] 更新用のPrjMembersTmpModelのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid プロジェクトテンプレートSID
     * @param paramMdl Prj140ParamModel
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return List in PrjMembersModel
     */
    private List<PrjMembersTmpModel> __getMemberUpdateList(int prtSid,
                                                            Prj140ParamModel paramMdl,
                                                            int userSid,
                                                            UDate now) {

        List<PrjMembersTmpModel> memberList = new ArrayList<PrjMembersTmpModel>();
        String[] members = paramMdl.getPrj140hdnMember();
        String[] spUsrSidList = null;
        HashMap<String, String> spUsrKeyMap = null;

        if (members != null && members.length > 0) {

            int idx = 0;
            spUsrSidList = new String[members.length];
            spUsrKeyMap = new HashMap<String, String>();

            for (String hdn : members) {

                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                spUsrSidList[idx] = String.valueOf(splitStr[0]);

                if (splitStr.length > 1) {
                    spUsrKeyMap.put(spUsrSidList[idx], splitStr[1]);
                } else {
                    spUsrKeyMap.put(spUsrSidList[idx], "");
                }
                idx += 1;
            }
        }

        String[] admins = paramMdl.getPrj140hdnAdmin();
        PrjMembersTmpModel memberMdl = null;

        if (spUsrSidList != null) {
            for (String member : spUsrSidList) {
                int adminKbn = GSConstProject.KBN_POWER_NORMAL;
                if (admins != null) {
                    for (String admin : admins) {
                        if (member.equals(admin)) {
                            adminKbn = GSConstProject.KBN_POWER_ADMIN;
                            break;
                        }
                    }
                }
                memberMdl = new PrjMembersTmpModel();
                memberMdl.setPrtSid(prtSid);
                memberMdl.setUsrSid(NullDefault.getInt(member, -1));
                memberMdl.setPmtEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
                memberMdl.setPmtAdminKbn(adminKbn);
                memberMdl.setPmtAuid(userSid);
                memberMdl.setPmtAdate(now);
                memberMdl.setPmtEuid(userSid);
                memberMdl.setPmtEdate(now);
                memberMdl.setPmtMemKey(spUsrKeyMap.get(member));
                memberList.add(memberMdl);
            }
        }
        return memberList;
    }

    /**
     * <br>[機  能] 表示しているテンプレートを適用する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj140ParamModel
     * @return ProjectStatusModel
     * @throws SQLException SQL実行例外
     */
    private ProjectStatusModel __getSelectData(Prj140ParamModel paramMdl) throws SQLException {

        int prtSid = paramMdl.getPrtSid();

        //プロジェクト状態
        ProjectStatusModel prjStatusMdl = new ProjectStatusModel();

        //プロジェクト状態_テンプレートを取得
        PrjPrjstatusTmpDao pptDao = new PrjPrjstatusTmpDao(con__);
        List<PrjPrjstatusTmpModel> prjStsTmpList = pptDao.selectTmpProjects(prtSid);

        //プロジェクト状態を設定
        List<PrjPrjstatusModel> prjStsList = new ArrayList<PrjPrjstatusModel>();
        for (PrjPrjstatusTmpModel pptMdl : prjStsTmpList) {
            PrjPrjstatusModel bean = new PrjPrjstatusModel();
            bean.setPrjSid(0);
            bean.setPrsSid(pptMdl.getPttSid());
            bean.setPrsSort(pptMdl.getPttSort());
            bean.setPrsName(NullDefault.getString(pptMdl.getPttName(), ""));
            bean.setPrsRate(pptMdl.getPttRate());
            bean.setPrsAuid(pptMdl.getPttAuid());
            bean.setPrsAdate(NullDefault.getUDate(pptMdl.getPttAdate(), null));
            bean.setPrsEuid(pptMdl.getPttEuid());
            bean.setPrsEdate(NullDefault.getUDate(pptMdl.getPttEdate(), null));
            prjStsList.add(bean);
        }
        prjStatusMdl.setPrjStatusList(prjStsList);

        //TODOカテゴリ_テンプレートを取得
        PrjTodocategoryTmpDao pctDao = new PrjTodocategoryTmpDao(con__);
        List<PrjTodocategoryTmpModel> prjTodoCateList = pctDao.selectTmpProjects(prtSid);

        //TODOカテゴリを設定
        List<PrjTodocategoryModel> prjCateList = new ArrayList<PrjTodocategoryModel>();
        for (PrjTodocategoryTmpModel pctMdl : prjTodoCateList) {
            PrjTodocategoryModel bean = new PrjTodocategoryModel();
            bean.setPrjSid(0);
            bean.setPtcCategorySid(pctMdl.getPctCategorySid());
            bean.setPtcSort(pctMdl.getPctSort());
            bean.setPtcName(NullDefault.getString(pctMdl.getPctName(), ""));
            bean.setPtcAuid(pctMdl.getPctAuid());
            bean.setPtcAdate(NullDefault.getUDate(pctMdl.getPctAdate(), null));
            bean.setPtcEuid(pctMdl.getPctEuid());
            bean.setPtcEdate(NullDefault.getUDate(pctMdl.getPctEdate(), null));
            prjCateList.add(bean);
        }
        prjStatusMdl.setTodoCateList(prjCateList);

        //TODO状態_テンプレートを取得
        PrjTodostatusTmpDao prtDao = new PrjTodostatusTmpDao(con__);
        List<PrjTodostatusTmpModel> prjTodoStsList = prtDao.selectTmpProjects(prtSid);

        //TODOテンプレートを設定
        List<PrjTodostatusModel> todoStsList = new ArrayList<PrjTodostatusModel>();
        for (PrjTodostatusTmpModel pstMdl : prjTodoStsList) {
            PrjTodostatusModel bean = new PrjTodostatusModel();
            bean.setPrjSid(0);
            bean.setPtsSid(pstMdl.getPstSid());
            bean.setPtsName(NullDefault.getString(pstMdl.getPstName(), ""));
            bean.setPtsRate(pstMdl.getPstRate());
            bean.setPtsSort(pstMdl.getPstSort());
            bean.setPtsAuid(pstMdl.getPstAuid());
            bean.setPtsAdate(NullDefault.getUDate(pstMdl.getPstAdate(), null));
            bean.setPtsEuid(pstMdl.getPstEuid());
            bean.setPtsEdate(NullDefault.getUDate(pstMdl.getPstAdate(), null));
            todoStsList.add(bean);
        }
        prjStatusMdl.setTodoStatusList(todoStsList);

        return prjStatusMdl;
    }

    /**
     * <br>[機  能] プロジェクト状態をオブジェクトファイルに保存
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param prjStatusMdl プロジェクト状態
     * @throws IOToolsException IOエラー
     */
    private void __saveFile(String rootDir,
                              ProjectStatusModel prjStatusMdl)
        throws IOToolsException {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl__,
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID_DEF,
                GSConstProject.TEMP_STATUS_PRJ);

        log__.debug("ディレクトリ = " + filePath);
        log__.debug("ファイル名 = " + GSConstProject.SAVE_FILENAME);

        //ディレクトリ存在チェック(なければ作成)
        IOTools.isDirCheck(filePath, true);
        //ファイル保存
        ObjectFile objFile = new ObjectFile(filePath, GSConstProject.SAVE_FILENAME);
        objFile.save(prjStatusMdl);
    }

    /**
     * <br>[機  能] 選択されたテンプレートを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj140ParamModel
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void setTemplateData(Prj140ParamModel paramMdl,
                                 String rootDir)
        throws SQLException, IOToolsException {

        ProjectStatusModel prjStatusMdl = __getSelectData(paramMdl);

        //プロジェクト状態をオブジェクトファイルに保存
        __saveFile(rootDir, prjStatusMdl);

//        //プロジェクト登録画面へのコピー値をセット
//        __setPrjAddDspParam(paramMdl);
    }
}
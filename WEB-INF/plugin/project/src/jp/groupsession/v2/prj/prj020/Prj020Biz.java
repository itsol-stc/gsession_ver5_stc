package jp.groupsession.v2.prj.prj020;

import java.io.File;
import java.io.IOException;
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
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.PrjMemberEditModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.ProjectUpdateBiz;
import jp.groupsession.v2.prj.dao.PrjAddressDao;
import jp.groupsession.v2.prj.dao.PrjCompanyDao;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjPrjstatusDao;
import jp.groupsession.v2.prj.dao.PrjTodoBinDao;
import jp.groupsession.v2.prj.dao.PrjTodocategoryDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.ProjectUpdateDao;
import jp.groupsession.v2.prj.model.PrjAddressModel;
import jp.groupsession.v2.prj.model.PrjCompanyModel;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusModel;
import jp.groupsession.v2.prj.model.PrjTodoBinModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.prj150.Prj150Biz;
import jp.groupsession.v2.prj.prj150.Prj150Dao;
import jp.groupsession.v2.prj.prj150.model.Prj150CompanyModel;
import jp.groupsession.v2.prj.prj150.model.Prj150DspMdl;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj020Biz.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj020";

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    protected RequestModel reqMdl__ = null;
    /**
     * <p>コンストラクタ
     * @param reqMdl リクエストモデル
     */
    public Prj020Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    public Prj020Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid ユーザSID
     * @param rootDir ルートディレクトリ
     * @param appRootPath アプリケーションルートパス
     * @param domain ドメイン
     * @param cmd 画面パラメータ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void setInitData(
            Prj020ParamModel paramMdl, int userSid, String rootDir,
            String appRootPath, String domain, String cmd)
                throws SQLException, IOToolsException, TempFileException, IOException,
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //初期表示フラグ
        paramMdl.setPrj020initFlg(false);

        //処理モード
        String cmdMode = paramMdl.getPrj020cmdMode();
        //プロジェクト状態
        ProjectStatusModel prjStatusMdl = null;
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録モード
            //初期設定を行い、プロジェクト状態Modelを返す
            prjStatusMdl = __setAddInit(paramMdl, userSid);

        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新モード
            //初期設定を行い、プロジェクト状態Modelを返す
            prjStatusMdl = __setEditInit(paramMdl, userSid, paramMdl.getPrj020prjSid());
            getCompanyData(paramMdl, userSid, paramMdl.getPrj020prjSid());

            //バイナリSIDが取得できていたら画像を取得
            if (paramMdl.getPrj020BinSid() > 0
                    && !(cmd.equals("prj020tempdeleteMark"))
                    && paramMdl.getPrj020IcoSetFlg() != 1) {

                CommonBiz cmnBiz = new CommonBiz();
                CmnBinfModel binMdl = cmnBiz.getBinInfo(con__, paramMdl.getPrj020BinSid(), domain);
                if (binMdl != null) {

                    //テンポラリディレクトリパスを取得
                    GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
                    String tempDir = temp.getTempPath(reqMdl__,
                            GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                            GSConstProject.TEMP_ICN_PRJ);

                    //添付ファイルをテンポラリディレクトリに格納する。
                    String imageSaveName = cmnBiz.saveSingleTempFile(binMdl, appRootPath, tempDir);
                    paramMdl.setPrj020IcoName(binMdl.getBinFileName());
                    paramMdl.setPrj020IcoSaveName(imageSaveName);
                }
            }
        }

        //プロジェクト状態をオブジェクトファイルに保存
        __saveFile(rootDir, prjStatusMdl);

        //グループにデフォルトグループを設定
        GroupBiz grpBiz = new GroupBiz();
        paramMdl.setPrj020group(grpBiz.getDefaultGroupSid(userSid, con__));
    }

    /**
     * <br>[機  能] コピー元のプロジェクト情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid ユーザSID
     * @param rootDir ルートディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void setCopyData(
        Prj020ParamModel paramMdl, int userSid, String rootDir)
                throws SQLException, IOToolsException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {

        //プロジェクト状態
        ProjectStatusModel prjStatusMdl = null;
        //初期設定を行い、プロジェクト状態Modelを返す
        prjStatusMdl = __setEditInit(paramMdl, userSid, paramMdl.getCopyProjectSid(), true);

        //プロジェクト状態をオブジェクトファイルに保存
        __saveFile(rootDir, prjStatusMdl);

        //グループにデフォルトグループを設定
        GroupBiz grpBiz = new GroupBiz();
        paramMdl.setPrj020group(grpBiz.getDefaultGroupSid(userSid, con__));
    }

    /**
     * <br>[機  能] プロジェクト状態をオブジェクトファイルに保存
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param prjStatusMdl プロジェクト状態
     * @throws IOToolsException IOエラー
     */
    private void __saveFile(
        String rootDir,
        ProjectStatusModel prjStatusMdl) throws IOToolsException {

        //テンポラリディレクトリパスを取得
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
     * <br>[機  能] 更新時の初期設定を行い、プロジェクト状態Modelを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid ユーザSID
     * @param projectSid プロジェクトSID
     * @return ProjectStatusModel
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ProjectStatusModel __setEditInit(
            Prj020ParamModel paramMdl, int userSid, int projectSid)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        return __setEditInit(paramMdl, userSid, projectSid, false);
    }

    /**
     * <br>[機  能] 更新時の初期設定を行い、プロジェクト状態Modelを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid ユーザSID
     * @param projectSid プロジェクトSID
     * @param copyFlg true:コピーモード false:通常読込みモード
     * @return ProjectStatusModel
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ProjectStatusModel __setEditInit(
            Prj020ParamModel paramMdl, int userSid, int projectSid, boolean copyFlg)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //プロジェクト状態
        ProjectStatusModel prjStatusMdl = new ProjectStatusModel();

        //プロジェクト情報を取得する
        PrjPrjdataDao ppDao = new PrjPrjdataDao(con__);
        ProjectItemModel piMdl = ppDao.getProjectInfo(projectSid);

        if (copyFlg) {
            //マイプロジェクト区分
            paramMdl.setPrj020prjMyKbn(GSConstProject.KBN_MY_PRJ_DEF);
        } else {
            //マイプロジェクト区分
            paramMdl.setPrj020prjMyKbn(piMdl.getPrjMyKbn());
        }

        //プロジェクトID
        paramMdl.setPrj020prjId(piMdl.getProjectId());
        //プロジェクト名称
        paramMdl.setPrj020prjName(piMdl.getProjectName());
        //プロジェクト略称
        paramMdl.setPrj020prjNameS(piMdl.getProjectRyakuName());
        //予算
        long yosan = piMdl.getYosan();
        if (yosan > GSConstCommon.NUM_INIT) {
            paramMdl.setPrj020yosan(String.valueOf(yosan));
        }
        //公開・非公開
        paramMdl.setPrj020koukai(piMdl.getKoukaiKbn());

        GsMessage gsMsg = new GsMessage();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strDateNameJp = gsMsg.getMessage("cmn.start");
        String endDateNameJp = gsMsg.getMessage("cmn.end");

        //開始年月日
        UDate start = piMdl.getStartDate();
        if (start != null) {
            paramMdl.setPrj020startYear(String.valueOf(start.getYear()));
            paramMdl.setPrj020startMonth(String.valueOf(start.getMonth()));
            paramMdl.setPrj020startDay(String.valueOf(start.getIntDay()));
            picker.setDateParam(
                    paramMdl,
                    "prj020startDate",
                    "prj020startYear",
                    "prj020startMonth",
                    "prj020startDay",
                    strDateNameJp);
        }
        //終了年月日
        UDate end = piMdl.getEndDate();
        if (end != null) {
            paramMdl.setPrj020endYear(String.valueOf(end.getYear()));
            paramMdl.setPrj020endMonth(String.valueOf(end.getMonth()));
            paramMdl.setPrj020endDay(String.valueOf(end.getIntDay()));
            picker.setDateParam(
                    paramMdl,
                    "prj020endDate",
                    "prj020endYear",
                    "prj020endMonth",
                    "prj020endDay",
                    endDateNameJp);
        }
        //状態
        paramMdl.setPrj020status(piMdl.getStatus());
        //目標・目的
        paramMdl.setPrj020mokuhyou(piMdl.getMokuhyou());
        //内容
        paramMdl.setPrj020naiyou(piMdl.getNaiyou());
        //編集権限
        paramMdl.setPrj020kengen(piMdl.getEditKengen());
        //ショートメール通知先
        paramMdl.setPrj020smailKbn(piMdl.getPrjMailKbn());

        //プロジェクトアイコンバイナリSID
        paramMdl.setPrj020BinSid(piMdl.getPrjBinSid());

        //所属メンバーを取得
        PrjMembersDao pmDao = new PrjMembersDao(con__);
        List<PrjMemberEditModel> usrModelList = pmDao.getMemberList(projectSid);

        List<PrjMemberEditModel> adminList = new ArrayList<PrjMemberEditModel>();
        String[] member = new String[usrModelList.size()];
        String[] memberSid = new String[usrModelList.size()];

        int index = 0;
        for (PrjMemberEditModel pmMdl : usrModelList) {
            if (pmMdl.getPrmAdminKbn() == GSConstProject.KBN_POWER_ADMIN) {
                adminList.add(pmMdl);
            }
            memberSid[index] = String.valueOf(pmMdl.getUsrSid());
            member[index] =
                String.valueOf(pmMdl.getUsrSid())
                + GSConst.GSESSION2_ID
                + NullDefault.getString(pmMdl.getMemberKey(), "");
            index++;
        }

        String[] admin = new String[adminList.size()];
        index = 0;
        for (PrjMemberEditModel pmMdl : adminList) {
            admin[index] = String.valueOf(pmMdl.getUsrSid());
            index++;
        }
        //所属メンバー
        paramMdl.setPrj020hdnMember(member);
        //所属メンバーSID
        paramMdl.setPrj020hdnMemberSid(memberSid);
        //プロジェクト管理者
        paramMdl.setPrj020hdnAdmin(admin);

        //プロジェクト状態を取得
        PrjPrjstatusDao ppsDao = new PrjPrjstatusDao(con__);
        prjStatusMdl.setPrjStatusList(ppsDao.selectProjects(projectSid));

        //TODOカテゴリを取得
        PrjTodocategoryDao ptcDao = new PrjTodocategoryDao(con__);
        prjStatusMdl.setTodoCateList(ptcDao.selectProjects(projectSid));

        //TODO状態を取得
        PrjTodostatusDao pts = new PrjTodostatusDao(con__);
        prjStatusMdl.setTodoStatusList(pts.selectProjects(projectSid));

        return prjStatusMdl;
    }

    /**
     * <br>[機  能] 登録時の初期設定を行い、プロジェクト状態Modelを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid ユーザSID
     * @return ProjectStatusModel
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ProjectStatusModel __setAddInit(Prj020ParamModel paramMdl, int userSid)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //予定
        String textYotei = gsMsg.getMessage("project.prj010.8");
        //プロジェクト状態
        ProjectStatusModel prjStatusMdl = new ProjectStatusModel();
        //完了
        String textCmoplete = gsMsg.getMessage("cmn.complete");

        //プロジェクト状態に初期値をセットする
        paramMdl.setPrj020status(1);

        //プロジェクトメンバー・管理者に初期値をセットする
        String[] member =
            {String.valueOf(userSid) + GSConst.GSESSION2_ID};
        String[] admin = {String.valueOf(userSid)};

        //所属メンバー
        paramMdl.setPrj020hdnMember(member);
        paramMdl.setPrj020hdnMemberSid(new String[]{String.valueOf(userSid)});
        //プロジェクト管理者
        paramMdl.setPrj020hdnAdmin(admin);

        //プロジェクト状態
        List<PrjPrjstatusModel> prjStatusList = new ArrayList<PrjPrjstatusModel>();
        PrjPrjstatusModel ppsMdl = new PrjPrjstatusModel();
        ppsMdl.setPrsSid(GSConstProject.STATUS_0);
        ppsMdl.setPrsSort(1);
        ppsMdl.setPrsName(textYotei);
        ppsMdl.setPrsRate(GSConstProject.RATE_MIN);
        prjStatusList.add(ppsMdl);

        ppsMdl = new PrjPrjstatusModel();
        ppsMdl.setPrsSid(GSConstProject.STATUS_100);
        ppsMdl.setPrsSort(2);
        ppsMdl.setPrsName(textCmoplete);
        ppsMdl.setPrsRate(GSConstProject.RATE_MAX);
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

        //初期表示時は開始と終了にシステム年月日を設定
        UDate systemUd = new UDate();
        String year = String.valueOf(systemUd.getYear());
        String month = String.valueOf(systemUd.getMonth());
        String day = String.valueOf(systemUd.getIntDay());

        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strDateNameJp = gsMsg.getMessage("cmn.start");
        String endDateNameJp = gsMsg.getMessage("cmn.end");

        paramMdl.setPrj020startYear(year);
        paramMdl.setPrj020startMonth(month);
        paramMdl.setPrj020startDay(day);
        paramMdl.setPrj020endYear(year);
        paramMdl.setPrj020endMonth(month);
        paramMdl.setPrj020endDay(day);
        picker.setDateParam(
                paramMdl,
                "prj020startDate",
                "prj020startYear",
                "prj020startMonth",
                "prj020startDay",
                strDateNameJp);
        picker.setDateParam(
                paramMdl,
                "prj020endDate",
                "prj020endYear",
                "prj020endMonth",
                "prj020endDay",
                endDateNameJp);

        return prjStatusMdl;
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj020ParamModel
     * @param pconfig プラグイン設定情報
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void getDspData(Prj020ParamModel paramMdl, PluginConfig pconfig)
        throws SQLException, IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();

        //テンプレート呼び出し時モード
        paramMdl.setPrjTmpMode(0);
        //所属メンバー
        String[] member = paramMdl.getPrj020hdnMember();
        String[] convMember = __getUserSidList(paramMdl);

        //プロジェクト管理者
        String[] admin = paramMdl.getPrj020hdnAdmin();
        //管理者以外メンバー
        String[] notAdmin = cmnBiz.getDeleteMember(admin, convMember);

        //グループSID
        int groupSid = paramMdl.getPrj020group();

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //グループラベルを設定する。
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
        paramMdl.setPrjStatusMdl(PrjCommonBiz.getProjectStatusModel(
                reqMdl__, TEMP_DIRECTORY_ID));

        //ショートメール使用有無
        paramMdl.setUseSmail(pconfig.getPlugin(GSConstProject.PLUGIN_ID_SMAIL) != null);
    }

    /**
     * <br>[機  能] ユーザSID配列からユーザSID部分を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj020ParamModel
     * @return ret ユーザ情報Model
     * @throws SQLException SQL実行時例外
     */
    private String[] __getUserSidList(Prj020ParamModel paramMdl) throws SQLException {

        String[] hdnMember = paramMdl.getPrj020hdnMember();
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
     * <br>[機  能] プロジェクトに紐付くTODO情報があるかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @return boolean true=TODO情報あり、false=TODO情報無し
     * @throws SQLException SQL実行例外
     */
    public boolean checkTodoExist(Prj020ParamModel paramMdl) throws SQLException {

        //プロジェクトSID
        int projectSid = paramMdl.getPrj020prjSid();

        PrjTododataDao ptDao = new PrjTododataDao(con__);
        int count = ptDao.getTodoCount(projectSid);

        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] プロジェクトを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteProject(Prj020ParamModel paramMdl, int userSid) throws SQLException {

        //プロジェクトSID
        int projectSid = paramMdl.getPrj020prjSid();


        //プロジェクトに紐付くTODO情報を取得する
        PrjTododataDao ptDao = new PrjTododataDao(con__);
        List<PrjTododataModel> ptList = ptDao.getTodoList(projectSid);

        String[] todoSid = new String[ptList.size()];
        List<Integer> todoSidList = new ArrayList<Integer>();
        int index = 0;
        for (PrjTododataModel ptMdl : ptList) {
            todoSid[index] = String.valueOf(ptMdl.getPtdSid());
            todoSidList.add(ptMdl.getPtdSid());
            index += 1;
        }

        //TODO情報に紐付く添付ファイル情報を取得する
        PrjTodoBinDao ptbDao = new PrjTodoBinDao(con__);
        List<PrjTodoBinModel> ptbList = ptbDao.getBinList(todoSid);

        List<Long> binList = new ArrayList<Long>();
        for (PrjTodoBinModel ptbMdl : ptbList) {
            binList.add(ptbMdl.getBinSid());
        }

        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(userSid);
        cbMdl.setBinUpdate(new UDate());

        boolean commitFlg = false;

        try {

            //プロジェクトアイコンを削除
            PrjPrjdataDao ppDao = new PrjPrjdataDao(con__);
            ppDao.deleteBinfProject(projectSid);

            //プロジェクト情報を削除する
            ProjectUpdateBiz projectBiz = new ProjectUpdateBiz(con__);
            projectBiz.deleteProject(cbMdl, binList, projectSid);

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
     * <br>[機  能] Saveパラメータを元に会社情報、アドレス帳情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void getCompanyDataSv(Prj020ParamModel paramMdl,
                                int userSid)
    throws SQLException {

        //アドレス情報を取得
        Prj150Dao prj150Dao = new Prj150Dao(con__);
        List<Prj150DspMdl> pri150DspList = new ArrayList<Prj150DspMdl>();

        PrjAddressDao paDao = new PrjAddressDao(con__);

        List<PrjAddressModel> addDbSidList = new ArrayList<PrjAddressModel>();
        String [] addId = paramMdl.getPrj150AddressIdSv();

        List<String> addSidList = new ArrayList<String>();
        if (addId == null) {
            addDbSidList = paDao.getAddSidList(paramMdl.getPrj030prjSid());
            for (int m = 0; m < addDbSidList.size(); m++) {
                addSidList.add(String.valueOf(addDbSidList.get(m).getAdrSid()));
            }
            paramMdl.setPrj150AddressIdSv(addSidList.toArray(new String[addSidList.size()]));

        } else {
            for (int i = 0; i < paramMdl.getPrj150AddressIdSv().length; i++) {
                addSidList.add(addId[i]);
            }
            paramMdl.setPrj150AddressIdSv(addSidList.toArray(new String[addSidList.size()]));
        }

        List<Prj150DspMdl> addressList
                    = prj150Dao.getAddressList2(con__, paramMdl.getPrj150AddressIdSv(),
                            userSid, paramMdl.getPrj020prjSid());

        //会社Sid
        List<String> adrComSidList = new ArrayList<String>();
        //会社拠点Sid
        List<String> adrComBaseSidList = new ArrayList<String>();

        if (addressList != null) {
            for (Prj150DspMdl adrData : addressList) {
                Prj150DspMdl addressData = new Prj150DspMdl();
                Prj150CompanyModel companyData = new Prj150CompanyModel();
                addressData.setAdrName(adrData.getAdrName());
                addressData.setAdrTel(adrData.getAdrTel());
                addressData.setAdrMail(adrData.getAdrMail());
                addressData.setAdrSid(adrData.getAdrSid());

                Prj150Biz prj150Biz = new Prj150Biz(con__);
                companyData = prj150Biz.createCompanyData(prj150Dao,
                        adrData.getCompanySid(),
                        adrData.getCompanyBaseSid());

                if (companyData != null) {
                    addressData.setCompanyName(companyData.getCompanyName());
                    addressData.setCompanyBaseSid(companyData.getCompanyBaseSid());
                    addressData.setCompanySid(companyData.getCompanySid());
                }

                pri150DspList.add(addressData);

                if (companyData != null) {
                    adrComSidList.add(String.valueOf(
                            companyData.getCompanySid() + paramMdl.getSepKey()));
                    adrComBaseSidList.add(String.valueOf(
                            companyData.getCompanyBaseSid() + paramMdl.getSepKey()));
                }
            }
        }
        paramMdl.setPrj020AddDataList(pri150DspList);

        paramMdl.setPrj150CompanySid(
                adrComSidList.toArray(new String[adrComSidList.size()]));
        paramMdl.setPrj150CompanyBaseSid(
                adrComBaseSidList.toArray(new String[adrComBaseSidList.size()]));
    }

    /**
     * <br>[機  能] 会社情報、アドレス帳情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid セッションユーザSID
     * @param projectSid プロジェクトSID
     * @throws SQLException SQL実行時例外
     */
    public void getCompanyData(Prj020ParamModel paramMdl,
                                int userSid,
                                int projectSid)
    throws SQLException {

        PrjAddressDao paDao = new PrjAddressDao(con__);
        List<String> addSidList = new ArrayList<String>();
        List<PrjAddressModel> addDbSidList = new ArrayList<PrjAddressModel>();
        addDbSidList = paDao.getAddSidList(projectSid);
        for (int m = 0; m < addDbSidList.size(); m++) {
            addSidList.add(String.valueOf(addDbSidList.get(m).getAdrSid()));
        }
        paramMdl.setPrj150AddressIdSv(addSidList.toArray(new String[addSidList.size()]));
        getCompanyDataSv(paramMdl, userSid);
        paramMdl.setPrj020EditDspFlg(1);
    }

    /**
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param rootDir ルートディレクトリ
     * @param appRoot アプリケーションルートパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void doAddEdit(
        Prj020ParamModel paramMdl,
        MlCountMtController cntCon,
        int userSid,
        String rootDir,
        String appRoot) throws SQLException, IOToolsException, TempFileException {

        //プロジェクト状態
        ProjectStatusModel prjStatus = PrjCommonBiz.getProjectStatusModel(
                reqMdl__, TEMP_DIRECTORY_ID);

        //テンポラリディレクトリパス
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl__,
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                GSConstProject.TEMP_ICN_PRJ);

        //処理モード
        String cmdMode = paramMdl.getPrj020cmdMode();
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録
            doInsert(paramMdl, cntCon, userSid, prjStatus, appRoot, filePath);

        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新
            doUpdate(paramMdl, userSid, prjStatus, appRoot, filePath, cntCon);
        }
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param userSid ログインユーザSID
     * @param prjStatus プロジェクト状態
     * @param appRoot アプリケーションパス
     * @param filePath ファイルパス
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void doUpdate(
            Prj020ParamModel paramMdl,
            int userSid,
            ProjectStatusModel prjStatus,
            String appRoot,
            String filePath,
            MlCountMtController cntCon)
    throws SQLException, TempFileException {

        boolean commitFlg = false;
        UDate now = new UDate();

        try {

            con__.setAutoCommit(false);

            //プロジェクトSID
            int projectSid = paramMdl.getPrj020prjSid();

            //プロジェクト更新Model
            PrjPrjdataModel ppMdl =
                __getPrjUpdateMdl(projectSid, paramMdl, userSid, now, appRoot, filePath, cntCon);

            //プロジェクトメンバー更新Modelリスト
            List<PrjMembersModel> memberList =
                __getMemberUpdateList(projectSid, paramMdl, userSid, now);

            //プロジェクト情報を更新する
            ProjectUpdateBiz projectBiz = new ProjectUpdateBiz(con__);
            projectBiz.updateProject(ppMdl, prjStatus, memberList);

            addPrjAddDB(paramMdl, userSid, projectSid);
            addPrjComDB(paramMdl, userSid, projectSid);

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
     * <br>[機  能] 登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj020ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param prjStatus プロジェクト状態
     * @param appRoot アプリケーションパス
     * @param filePath ファイルパス
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void doInsert(
        Prj020ParamModel paramMdl,
        MlCountMtController cntCon,
        int userSid,
        ProjectStatusModel prjStatus,
        String appRoot,
        String filePath) throws SQLException, TempFileException {

        boolean commitFlg = false;
        UDate now = new UDate();

        try {

            con__.setAutoCommit(false);

            //プロジェクトSID採番
            int projectSid = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                                                           GSConstProject.SBNSID_SUB_PROJECT,
                                                           userSid);
            //プロジェクト更新Model
            PrjPrjdataModel ppMdl = __getPrjUpdateMdl(projectSid, paramMdl, userSid,
                                                      now, appRoot, filePath, cntCon);

            //プロジェクトメンバー更新Modelリスト
            List<PrjMembersModel> memberList =
                __getMemberUpdateList(projectSid, paramMdl, userSid, now);

            //プロジェクト情報を登録する
            ProjectUpdateDao projectDao = new ProjectUpdateDao(con__);
            projectDao.insertProject(ppMdl, prjStatus, memberList);

            addPrjAddDB(paramMdl, userSid, projectSid);
            addPrjComDB(paramMdl, userSid, projectSid);

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
     * <br>[機  能] 更新用のPrjMembersModelのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param paramMdl Prj020ParamModel
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return List in PrjMembersModel
     */
    private List<PrjMembersModel> __getMemberUpdateList(
        int projectSid,
        Prj020ParamModel paramMdl,
        int userSid,
        UDate now) {

        List<PrjMembersModel> memberList = new ArrayList<PrjMembersModel>();
        String[] members = paramMdl.getPrj020hdnMember();

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

        String[] admins = paramMdl.getPrj020hdnAdmin();
        PrjMembersModel memberMdl = null;

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
                memberMdl = new PrjMembersModel();
                memberMdl.setPrjSid(projectSid);
                memberMdl.setUsrSid(NullDefault.getInt(member, -1));
                memberMdl.setPrmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
                memberMdl.setPrmAdminKbn(adminKbn);
                memberMdl.setPrmAuid(userSid);
                memberMdl.setPrmAdate(now);
                memberMdl.setPrmEuid(userSid);
                memberMdl.setPrmEdate(now);
                memberMdl.setPrmMemKey(spUsrKeyMap.get(member));

                memberList.add(memberMdl);
            }
        }
        return memberList;
    }

    /**
     * <br>[機  能] 更新用のPrjPrjdataModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param paramMdl Prj020ParamModel
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param appRoot アプリケーションルートパス
     * @param tempDir Tempパス
     * @param cntCon 採番コントローラ
     * @return PrjPrjdataModel
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws SQLException SQL実行例外
     */
    private PrjPrjdataModel __getPrjUpdateMdl(
        int projectSid,
        Prj020ParamModel paramMdl,
        int userSid,
        UDate now,
        String appRoot,
        String tempDir,
        MlCountMtController cntCon
    ) throws TempFileException, SQLException {

        UDate startDate = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj020startYear(), -1),
                NullDefault.getInt(paramMdl.getPrj020startMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj020startDay(), -1));
        UDate andDate = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj020endYear(), -1),
                NullDefault.getInt(paramMdl.getPrj020endMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj020endDay(), -1));

        PrjPrjdataModel ppMdl = new PrjPrjdataModel();
        ppMdl.setPrjSid(projectSid);
        ppMdl.setPrjMyKbn(paramMdl.getPrj020prjMyKbn());
        ppMdl.setPrjId(paramMdl.getPrj020prjId());
        ppMdl.setPrjName(paramMdl.getPrj020prjName());
        ppMdl.setPrjNameShort(paramMdl.getPrj020prjNameS());
        ppMdl.setPrjYosan(NullDefault.getLong(paramMdl.getPrj020yosan(), GSConstCommon.NUM_INIT));
        ppMdl.setPrjKoukaiKbn(paramMdl.getPrj020koukai());
        ppMdl.setPrjDateStart(startDate);
        ppMdl.setPrjDateEnd(andDate);
        if (paramMdl.getPrj020prjMyKbn() == GSConstProject.KBN_MY_PRJ_MY) {
            ppMdl.setPrjStatusSid(GSConstProject.STATUS_MYPRJ);
        } else {
            ppMdl.setPrjStatusSid(paramMdl.getPrj020status());
        }
        ppMdl.setPrjTarget(paramMdl.getPrj020mokuhyou());
        ppMdl.setPrjContent(paramMdl.getPrj020naiyou());
        ppMdl.setPrjEdit(paramMdl.getPrj020kengen());
        ppMdl.setPrjMailKbn(paramMdl.getPrj020smailKbn());
        ppMdl.setPrjAuid(userSid);
        ppMdl.setPrjAdate(now);
        ppMdl.setPrjEuid(userSid);
        ppMdl.setPrjEdate(now);

        //アイコン情報を登録
        //バイナリー情報を更新する
        if (paramMdl.getPrj020cmdMode().equals(GSConstProject.CMD_MODE_EDIT)) {
            //編集時
            PrjPrjdataDao ppDao = new PrjPrjdataDao(con__);
            ppDao.deleteBinfProject(projectSid);
        }

        Long binSid = new Long(0);
        if (!NullDefault.getStringZeroLength(
                paramMdl.getPrj020IcoName(), "").equals("")
                && !NullDefault.getStringZeroLength(
                        paramMdl.getPrj020IcoSaveName(), "").equals("")) {
            CommonBiz cmnBiz = new CommonBiz();

            String filePath = tempDir
                             + File.separator
                             + paramMdl.getPrj020IcoSaveName() + GSConstCommon.ENDSTR_SAVEFILE;

            binSid = cmnBiz.insertBinInfo(
                    con__, appRoot, cntCon, userSid, now, filePath, paramMdl.getPrj020IcoName());
        }

        ppMdl.setBinSid(binSid);
        return ppMdl;
    }

    /**
     * <br>[機  能] プロジェクトアドレス情報をDBに更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj020ParamModel
     * @param usrSid セッションユーザSID
     * @param newPrjSid 新規登録用プロジェクトSID
     * @throws SQLException SQL実行時例外
     */
    public void addPrjAddDB(Prj020ParamModel paramMdl, int usrSid, int newPrjSid)
        throws SQLException {

        UDate now = new UDate();
        PrjAddressDao addDao = new PrjAddressDao(con__);
        addDao.deletePrjAdd(paramMdl.getPrj030prjSid());

        String[] addressIdList = paramMdl.getPrj150AddressIdSv();

        if (addressIdList != null) {
            for (int i = 0; i < addressIdList.length; i++) {
                PrjAddressModel addressModel = new PrjAddressModel();
                String addSid = addressIdList[i];
                addressModel.setAdrSid(Integer.parseInt(addSid));
                addressModel.setPrjSid(newPrjSid);
                addressModel.setPraAuid(usrSid);
                addressModel.setPraAdate(now);
                addressModel.setPraEuid(usrSid);
                addressModel.setPraEdate(now);
                addressModel.setPraSort(0);
                addDao.insert(addressModel);
            }
        }
    }

    /**
     * <br>[機  能] プロジェクト会社情報をDBに更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj020ParamModel
     * @param usrSid セッションユーザSID
     * @param newPrjSid 新規登録用プロジェクトSID
     * @throws SQLException SQL実行時例外
     * @return 更新実行
     */
    public boolean addPrjComDB(Prj020ParamModel paramMdl, int usrSid, int newPrjSid)
        throws SQLException {

        UDate now = new UDate();
        PrjCompanyDao comDao = new PrjCompanyDao(con__);
        comDao.deletePrjCom(paramMdl.getPrj030prjSid());

        String[] companyIdList = paramMdl.getPrj150CompanySid();
        String[] companyBaseIdList
               = paramMdl.getPrj150CompanyBaseSid();

        if (companyIdList == null) {
            return false;
        }

        for (int i = 0; i < companyIdList.length; i++) {

            //会社SID、拠点SIDを取得
            int cntCom = companyIdList[i].toString().indexOf("d");
            int cntBaseCom = companyBaseIdList[i].toString().indexOf("d");
            String comSid = companyIdList[i].toString().substring(0, cntCom);
            String comBaseSid = companyBaseIdList[i].toString().substring(0, cntBaseCom);

            int comCnt = comDao.getprjComCount(paramMdl.getPrj030prjSid(),
                    Integer.parseInt(comSid),
                    Integer.parseInt(comBaseSid));

            if (comCnt != 0) {
                continue;
            }

            PrjCompanyModel companyModel = new PrjCompanyModel();

            companyModel.setAcoSid(Integer.parseInt(comSid));
            companyModel.setAbaSid(Integer.parseInt(comBaseSid));
            companyModel.setPrjSid(newPrjSid);
            companyModel.setPrcAuid(usrSid);
            companyModel.setPrcAdate(now);
            companyModel.setPrcEuid(usrSid);
            companyModel.setPrcEdate(now);

            if (comDao.update(companyModel) == 0) {
                comDao.insert(companyModel);
            }
        }
        return true;
    }
}
package jp.groupsession.v2.prj.prj050;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.PrjIUserGroupListenerImpl;
import jp.groupsession.v2.prj.ProjectUpdateBiz;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjTodoBinDao;
import jp.groupsession.v2.prj.dao.PrjTodocategoryDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.dao.PrjTodomemberDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.dao.ProjectUpdateDao;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjSmailModel;
import jp.groupsession.v2.prj.model.PrjSmailParamModel;
import jp.groupsession.v2.prj.model.PrjStatusHistoryModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjTodomemberModel;
import jp.groupsession.v2.prj.model.PrjUserConfModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.UserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] TODO登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj050Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj050";

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエストモデル
     */
    public Prj050Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    public Prj050Biz(
            Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param buMdl セッションユーザModel
     * @param appRoot アプリケーションのルートパス
     * @param tempRoot テンポラリディレクトリ
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void setInitData(
            Prj050ParamModel paramMdl, BaseUserModel buMdl,
            String appRoot, String tempRoot, int userSid,
            RequestModel reqMdl, String domain)
                    throws SQLException, IOToolsException, IOException, TempFileException,
                    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //処理モード
        String cmdMode = paramMdl.getPrj050cmdMode();
        if (cmdMode == null) {
            cmdMode = GSConstProject.CMD_MODE_ADD;
            paramMdl.setPrj050cmdMode(cmdMode);
        }

        //個人設定を取得
        PrjCommonBiz prjBiz = new PrjCommonBiz(con__, reqMdl__);
        PrjUserConfModel prjUserMdl = null;
        prjUserMdl = prjBiz.getPrjUserConfModel(con__, reqMdl__.getSmodel().getUsrsid());
        //画面の表示区分を取得
        if (paramMdl.getPrj050dspKbn() == GSConstProject.DSP_FIRST) {
            paramMdl.setPrj050elementKbn(prjUserMdl.getPucTodoDsp());
        }

        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録モード
            __setNewInit(paramMdl, buMdl, reqMdl);

        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新モード
            //更新時の初期設定を行う
            __setEditInit(paramMdl, appRoot, tempRoot, buMdl, domain);

            //更新モード時初期表示
            if (paramMdl.getPrj050dspKbn() == GSConstProject.DSP_FIRST) {

                //タイトル
                paramMdl.setPrj050titleEasy(paramMdl.getPrj050title());
                //重要度
                paramMdl.setPrj050juyouEasy(paramMdl.getPrj050juyou());
                //内容
                paramMdl.setPrj050naiyoEasy(paramMdl.getPrj050naiyo());
            }
        }
        paramMdl.setPrj050dspKbn(GSConstProject.DSP_ALREADY);
    }

    /**
     * <br>[機  能] 新規時の初期設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param buMdl セッションユーザModel
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    private void __setNewInit(Prj050ParamModel paramMdl, BaseUserModel buMdl, RequestModel reqMdl)
    throws SQLException {

        int projectSid = -1;

        //プロジェクト未選択の場合、初期値をセット
        if (paramMdl.getPrj050prjSid() < 1) {

            String prj050scrId =
                NullDefault.getString(paramMdl.getPrj050scrId(), "");

            //メイン画面 or ダッシュボードから遷移
            if (prj050scrId.equals(GSConstProject.SCR_ID_PRJ010)
                    || prj050scrId.equals(GSConstProject.SCR_ID_MAIN)) {

                //マイプロジェクトをデフォルトとする
                GsMessage gsMsg = new GsMessage(reqMdl__);
                ProjectSearchDao dao = new ProjectSearchDao(con__, gsMsg);
                projectSid = dao.getMyPrjSid(buMdl.getUsrsid());

                if (projectSid == -1) {

                    log__.debug("***マイプロジェクト無し → 作成");
                    //マイプロジェクトが登録されていない場合、作成する。
                    PrjIUserGroupListenerImpl uglistener = new PrjIUserGroupListenerImpl();
                    uglistener.addUser(null, con__, buMdl.getUsrsid(), GSConst.USER_ADMIN, reqMdl);
                    con__.commit();
                }

            } else {
                //簡易、詳細でTODO作成区分を判別する
                int param = 0;
                if (paramMdl.getPrj050elementKbn() == GSConstProject.DSP_TODO_EASY) {
                    param = GSConstProject.PRJ_KBN_PARTICIPATION;
                } else if (paramMdl.getPrj050elementKbn() == GSConstProject.DSP_TODO_DETAIL) {
                    param = paramMdl.getPrj050PrjListKbn();
                }

                GsMessage gsMsg = new GsMessage(reqMdl__);
                PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
                List<LabelValueBean> projectLabel =
                    pcBiz.getCanCreateTodoProjectLabel(buMdl, param);
                projectSid =
                    NullDefault.getInt(
                            projectLabel.get(0).getValue(), GSConstCommon.NUM_INIT);
            }

            paramMdl.setPrj050prjSid(projectSid);
        }

        //マイプロジェクトの場合、担当者に自分を設定する
        setMyPrjKbn(paramMdl, buMdl);
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setPrj050statusCmt(gsMsg.getMessage("cmn.new.registration"));
    }

    /**
     * <br>[機  能] 更新時の初期設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param appRoot アプリケーションのルートパス
     * @param tempRoot テンポラリディレクトリ
     * @param buMdl セッションユーザModel
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private void __setEditInit(
            Prj050ParamModel paramMdl,  String appRoot,
            String tempRoot, BaseUserModel buMdl, String domain)
                    throws SQLException, IOToolsException, IOException, TempFileException,
                    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        if (paramMdl.getPrj050dspKbn() == GSConstProject.DSP_FIRST) {
            int todoSid = paramMdl.getPrj050todoSid();

            //TODO情報を取得
            PrjTododataDao ptdDao = new PrjTododataDao(con__);
            ProjectItemModel piMdl = ptdDao.getTodoData(todoSid);

            //プロジェクトSID
            paramMdl.setPrj050prjSid(piMdl.getProjectSid());
            //カテゴリ
            paramMdl.setPrj050cate(piMdl.getCategorySid());
            //TODOタイトル
            paramMdl.setPrj050title(piMdl.getTodoTitle());

            GsMessage gsMsg = new GsMessage();
            DateTimePickerBiz picker = new DateTimePickerBiz();
            String strPlanDateJp = gsMsg.getMessage("project.prj070.2");
            String endPlanDateJp = gsMsg.getMessage("project.prj070.4");
            String strResultDateJp = gsMsg.getMessage("project.prj070.5");
            String endResultDateJp = gsMsg.getMessage("project.prj070.6");

            //予定開始年月日
            UDate start = piMdl.getStartDate();
            if (start != null) {
                paramMdl.setPrj050kaisiYoteiYear(String.valueOf(start.getYear()));
                paramMdl.setPrj050kaisiYoteiMonth(String.valueOf(start.getMonth()));
                paramMdl.setPrj050kaisiYoteiDay(String.valueOf(start.getIntDay()));
                picker.setDateParam(
                        paramMdl, 
                        "prj050strPlanDate",
                        "prj050kaisiYoteiYear",
                        "prj050kaisiYoteiMonth",
                        "prj050kaisiYoteiDay",
                        strPlanDateJp);
            }
            //予定終了年月日
            UDate end = piMdl.getEndDate();
            if (end != null) {
                paramMdl.setPrj050kigenYear(String.valueOf(end.getYear()));
                paramMdl.setPrj050kigenMonth(String.valueOf(end.getMonth()));
                paramMdl.setPrj050kigenDay(String.valueOf(end.getIntDay()));
                picker.setDateParam(
                        paramMdl, 
                        "prj050endPlanDate",
                        "prj050kigenYear",
                        "prj050kigenMonth",
                        "prj050kigenDay",
                        endPlanDateJp);
            }
            //実績開始年月日
            UDate jissekiStart = piMdl.getStartJissekiDate();
            if (jissekiStart != null) {
                paramMdl.setPrj050kaisiJissekiYear(String.valueOf(jissekiStart.getYear()));
                paramMdl.setPrj050kaisiJissekiMonth(String.valueOf(jissekiStart.getMonth()));
                paramMdl.setPrj050kaisiJissekiDay(String.valueOf(jissekiStart.getIntDay()));
                picker.setDateParam(
                        paramMdl, 
                        "prj050strResultDate",
                        "prj050kaisiJissekiYear",
                        "prj050kaisiJissekiMonth",
                        "prj050kaisiJissekiDay",
                        strResultDateJp);
            }
            //実績終了年月日
            UDate jissekiEnd = piMdl.getEndJissekiDate();
            if (jissekiEnd != null) {
                paramMdl.setPrj050syuryoJissekiYear(String.valueOf(jissekiEnd.getYear()));
                paramMdl.setPrj050syuryoJissekiMonth(String.valueOf(jissekiEnd.getMonth()));
                paramMdl.setPrj050syuryoJissekiDay(String.valueOf(jissekiEnd.getIntDay()));
                picker.setDateParam(
                        paramMdl, 
                        "prj050endResultDate",
                        "prj050syuryoJissekiYear",
                        "prj050syuryoJissekiMonth",
                        "prj050syuryoJissekiDay",
                        endResultDateJp);
            }
            //予定工数
            BigDecimal yoteiKosu = piMdl.getYoteiKosu();
            if (yoteiKosu != null) {
                paramMdl.setPrj050yoteiKosu(String.valueOf(yoteiKosu));
            }
            //実績工数
            BigDecimal jissekiKosu = piMdl.getJissekiKosu();
            if (jissekiKosu != null) {
                paramMdl.setPrj050jissekiKosu(String.valueOf(jissekiKosu));
            }
            //警告開始
            paramMdl.setPrj050keikoku(piMdl.getKeikoku());
            //重要度
            paramMdl.setPrj050juyou(piMdl.getJuyo());
            //状態
            paramMdl.setPrj050status(piMdl.getStatus());
            //内容
            paramMdl.setPrj050naiyo(piMdl.getNaiyou());

            //担当者情報を取得する
            PrjTodomemberDao ptmDao = new PrjTodomemberDao(con__);
            CmnUsrmDao usrmDao = new CmnUsrmDao(con__);
            List<PrjTodomemberModel> ptmList = ptmDao.getTantoBaseList(todoSid);
            List<String> tantoList = new ArrayList<String>();

            int cnt = 0;
            for (PrjTodomemberModel ptmMdl : ptmList) {

                //削除ユーザチェック
                List<String> userSidList = new ArrayList<String>();
                userSidList.add(String.valueOf(ptmMdl.getUsrSid()));
                cnt = usrmDao.getCountDeleteUser(userSidList);

                if (cnt == 0) {
                    tantoList.add(String.valueOf(ptmMdl.getUsrSid()));
                }
            }
            String[] tanto = (String[]) tantoList.toArray(new String[tantoList.size()]);
            paramMdl.setPrj050hdnTanto(tanto);

          //添付ファイルをテンポラリディレクトリへ設定する
            __setTempFile(paramMdl, appRoot, tempRoot, domain);
        }
        //マイプロジェクトの場合、担当者に自分を設定する
        setMyPrjKbn(paramMdl, buMdl);
    }

    /**
     * <br>[機  能] マイプロジェクト区分・担当者の設定を行うを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param buMdl セッションユーザModel
     * @throws SQLException SQL実行例外
     */
    public void setMyPrjKbn(Prj050ParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        //簡易、詳細でTODO作成区分を判別する
        int param = 0;
        if (paramMdl.getPrj050elementKbn() == GSConstProject.DSP_TODO_EASY) {
            param = GSConstProject.PRJ_KBN_PARTICIPATION;
        } else if (paramMdl.getPrj050elementKbn() == GSConstProject.DSP_TODO_DETAIL) {
            param = paramMdl.getPrj050PrjListKbn();
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
        List<ProjectItemModel> prjlist =
            psDao.getCanCreateTodoProjectList(buMdl, param);

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        boolean projectExistFlg = false;
        int projectSid = paramMdl.getPrj050prjSid();

        for (ProjectItemModel piMdl : prjlist) {

            labelList.add(new LabelValueBean(
                    piMdl.getProjectName(), String.valueOf(piMdl.getProjectSid())));

            if (projectSid == piMdl.getProjectSid()) {
                projectExistFlg = true;
                break;
            }
        }

        //プロジェクト未選択の場合、初期値をセット(コンボの一番上の値)
        if (paramMdl.getPrj050prjSid() < 1
                || !projectExistFlg) {
            paramMdl.setPrj050prjSid(
                    NullDefault.getInt(labelList.get(0).getValue(), GSConstCommon.NUM_INIT));
        }

        //プロジェクト情報を取得
        PrjPrjdataDao prjDao = new PrjPrjdataDao(con__);
        PrjPrjdataModel prjMdl = prjDao.getProjectData(paramMdl.getPrj050prjSid());

        if (prjMdl != null) {
            int prjMyKbn = prjMdl.getPrjMyKbn();
            //プロジェクトがマイプロジェクトの場合は担当に自分をセット
            if (prjMyKbn == GSConstProject.KBN_MY_PRJ_MY) {
                //担当者に初期値をセットする
                String[] tanto = {String.valueOf(buMdl.getUsrsid())};
                paramMdl.setPrj050hdnTanto(tanto);
            }
            paramMdl.setPrj050prjMyKbn(prjMyKbn);
        }
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param appRoot アプリケーションのルートパス
     * @param tempRoot テンポラリディレクトリ
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __setTempFile(
        Prj050ParamModel paramMdl,
        String appRoot,
        String tempRoot,
        String domain)
    throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        int projectSid = paramMdl.getPrj050prjSid();
        int todoSid = paramMdl.getPrj050todoSid();
        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(reqMdl__,
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        //添付ファイル情報を取得する
        PrjTodoBinDao ptbDao = new PrjTodoBinDao(con__);
        String[] binSids = ptbDao.getBinSids(projectSid, todoSid);
        if (binSids == null || binSids.length < 1) {
            return;
        }
        List<CmnBinfModel> cmList = cmnBiz.getBinInfo(con__, binSids, domain);

        int fileNum = 1;
        for (CmnBinfModel cbMdl : cmList) {
            if (cbMdl.getBinJkbn() == GSConst.JTKBN_DELETE) {
                continue;
            }

            //添付ファイルをテンポラリディレクトリにコピーする。
            cmnBiz.saveTempFile(dateStr, cbMdl, appRoot, tempDir, fileNum);

            fileNum++;
        }
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param buMdl セッションユーザModel
     * @param tempRoot テンポラリディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void getDspData(
            Prj050ParamModel paramMdl, BaseUserModel buMdl, String tempRoot)
            throws SQLException, IOToolsException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        //管理者区分
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);
        paramMdl.setAdmin(adminUser);

        //処理モード
        String cmdMode = paramMdl.getPrj050cmdMode();
        int todoSid = paramMdl.getPrj050todoSid();

        if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新モード
            //TODO情報を取得
            PrjTododataDao ptdDao = new PrjTododataDao(con__);
            ProjectItemModel piMdl = ptdDao.getTodoData(todoSid);
            piMdl.setStrKanriNo(
                    StringUtil.toDecFormat(piMdl.getKanriNo(), GSConstProject.KANRI_NO_FORMAT));
            paramMdl.setProjectItem(piMdl);
        }
        int param = 0;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strPlanDateJp = gsMsg.getMessage("project.prj070.2");
        String endPlanDateJp = gsMsg.getMessage("project.prj070.4");
        String strResultDateJp = gsMsg.getMessage("project.prj070.5");
        String endResultDateJp = gsMsg.getMessage("project.prj070.6");

        if (paramMdl.getPrj050elementKbn() == GSConstProject.DSP_TODO_EASY) {
            param = GSConstProject.PRJ_KBN_PARTICIPATION;

            picker.setDateParam(
                    paramMdl, 
                    "prj050endPlanDate",
                    "prj050kigenYear",
                    "prj050kigenMonth",
                    "prj050kigenDay",
                    endPlanDateJp);
        } else if (paramMdl.getPrj050elementKbn() == GSConstProject.DSP_TODO_DETAIL) {
            param = paramMdl.getPrj050PrjListKbn();

            picker.setDateParam(
                    paramMdl, 
                    "prj050strPlanDate",
                    "prj050kaisiYoteiYear",
                    "prj050kaisiYoteiMonth",
                    "prj050kaisiYoteiDay",
                    strPlanDateJp);
            picker.setDateParam(
                    paramMdl, 
                    "prj050endPlanDate",
                    "prj050kigenYear",
                    "prj050kigenMonth",
                    "prj050kigenDay",
                    endPlanDateJp);
            picker.setDateParam(
                    paramMdl, 
                    "prj050strResultDate",
                    "prj050kaisiJissekiYear",
                    "prj050kaisiJissekiMonth",
                    "prj050kaisiJissekiDay",
                    strResultDateJp);
            picker.setDateParam(
                    paramMdl, 
                    "prj050endResultDate",
                    "prj050syuryoJissekiYear",
                    "prj050syuryoJissekiMonth",
                    "prj050syuryoJissekiDay",
                    endResultDateJp);
        }

        //プロジェクトコンボをセットする
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
        List<ProjectItemModel> prjlist =
            psDao.getCanCreateTodoProjectList(buMdl, param);

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        boolean projectExistFlg = false;
        int projectSid = paramMdl.getPrj050prjSid();

        for (ProjectItemModel piMdl : prjlist) {
            labelList.add(new LabelValueBean(
                    piMdl.getProjectName(), String.valueOf(piMdl.getProjectSid())));

            if (projectSid == piMdl.getProjectSid()) {
                projectExistFlg = true;
            }
        }

        paramMdl.setProjectLabel(labelList);

        //プロジェクト未選択の場合、初期値をセット(コンボの一番上の値)
        if (paramMdl.getPrj050prjSid() < 1
                || !projectExistFlg) {
            paramMdl.setPrj050prjSid(
                    NullDefault.getInt(labelList.get(0).getValue(), GSConstCommon.NUM_INIT));
        }

        //TODOカテゴリリストをセットする
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        paramMdl.setPrj050CategoryList(pcBiz.getTodoCategoryList(projectSid));

        //担当者
        String[] tanto = paramMdl.getPrj050hdnTanto();

        //担当者コンボをセットする
        UserBiz userBiz = new UserBiz();
        paramMdl.setAdminMemberLabel(userBiz.getUserLabelList(con__, tanto));

        //メンバーコンボをセットする
        PrjMembersDao pmDao = new PrjMembersDao(con__);
        List<UserModel> usrAllList = pmDao.getMemberList(projectSid, tanto, GSConst.JTKBN_TOROKU);
        List<UsrLabelValueBean> userLabel = new ArrayList<UsrLabelValueBean>();

        for (UserModel userMdl : usrAllList) {
            userLabel.add(new UsrLabelValueBean(
                    UserUtil.makeName(userMdl.getSei(), userMdl.getMei()),
                                             String.valueOf(userMdl.getUserSid()),
                                             userMdl.getUsrUkoFlg()));
        }
        paramMdl.setMemberLabel(userLabel);

        PrjCommonBiz prjComBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        //警告開始コンボの値をセット
        paramMdl.setKeikokuLabel(prjComBiz.getKeikokuLabel());

        //TODO状態を取得
        PrjTodostatusDao pts = new PrjTodostatusDao(con__);
        ProjectStatusModel prjStatusMdl = new ProjectStatusModel();
        prjStatusMdl.setTodoStatusList(pts.selectProjects(projectSid));
        paramMdl.setPrjStatusMdl(prjStatusMdl);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(reqMdl__,
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
        paramMdl.setFileLabel(cmnBiz.getTempFileLabelList(tempDir));

        //ショートメール通知区分
        PrjPrjdataDao prjDao = new PrjPrjdataDao(con__);
        PrjPrjdataModel prjMdl = prjDao.getProjectData(projectSid);

        int send = paramMdl.getPrj050MailSend();

        //ショートメール通知区分
        paramMdl.setPrj050smailKbn(prjMdl.getPrjMailKbn());
        if (send == -1) {
            if (prjMdl.getPrjMailKbn() == GSConstProject.TODO_MAIL_FREE) {
                paramMdl.setPrj050MailSend(GSConstProject.NOT_SEND);
            } else if (prjMdl.getPrjMailKbn() == GSConstProject.TODO_MAIL_SEND_ADMIN) {
                paramMdl.setPrj050MailSend(GSConstProject.SEND_LEADER);
            }
        }

        if (prjMdl != null) {
            int prjMyKbn = prjMdl.getPrjMyKbn();
            paramMdl.setPrj050prjMyKbn(prjMyKbn);
        }

        //ラベル不正書き換え時ラジオのチェックを行う
        PrjTodocategoryDao cDao = new PrjTodocategoryDao(con__);
        PrjTodocategoryModel cMdl = cDao.select(paramMdl.getPrj050prjSid(),
                paramMdl.getPrj050cate());
        if (cMdl == null) {
            paramMdl.setPrj050cate(GSConstCommon.NUM_INIT);
        }
    }

    /**
     * <br>[機  能] TODOを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteTodo(Prj050ParamModel paramMdl, int userSid) throws SQLException {

        boolean commitFlg = false;

        try {

            //TODO情報を削除する
            int todoSid = paramMdl.getPrj050todoSid();
            String[] todoSids = {String.valueOf(todoSid)};

            ProjectUpdateBiz projectBiz = new ProjectUpdateBiz(con__);
            projectBiz.deleteTodo(todoSids, userSid);

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
     * @param paramMdl Prj050ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param appRoot ルートディレクトリ
     * @param tempRoot テンポラリディレクトリ
     * @param pconfig プラグインコンフィグ
     * @return boolean 画面遷移時、対象データがあるか(削除、プロジェクトSID変更済でないか)
     *                  true=データが存在する、false=存在しない
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException 入出力時例外
     * @throws Exception その他例外
     */
    public boolean doAddEdit(Prj050ParamModel paramMdl,
                              MlCountMtController cntCon,
                              int userSid,
                              String appRoot,
                              String tempRoot,
                              PluginConfig pconfig)
        throws SQLException, IOToolsException, IOException, Exception {

        boolean dataexist = true;

        //処理モード
        String cmdMode = paramMdl.getPrj050cmdMode();
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録
            doInsert(paramMdl, cntCon, userSid, appRoot, tempRoot, pconfig);

        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新
            dataexist = doUpdate(paramMdl, cntCon, userSid, appRoot, tempRoot, pconfig);
        }
        return dataexist;
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param appRoot ルートディレクトリ
     * @param tempRoot テンポラリディレクトリ
     * @param pconfig プラグインコンフィグ
     * @return boolean 画面遷移時、対象データがあるか(削除、プロジェクトSID変更済でないか)
     *                  true=データが存在する、false=存在しない
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws Exception その他例外
     */
    public boolean doUpdate(Prj050ParamModel paramMdl,
                              MlCountMtController cntCon,
                              int userSid,
                              String appRoot,
                              String tempRoot,
                              PluginConfig pconfig)
        throws SQLException, IOToolsException, IOException, Exception {

        boolean commitFlg = false;
        UDate now = new UDate();
        boolean dataexist = true;

        //プロジェクトSID
        int projectSid = paramMdl.getPrj050prjSid();
        //TODOSID
        int todoSid = paramMdl.getPrj050todoSid();
        //状態区分
        int status = paramMdl.getPrj050status();
        //テンポラリディレクトリ
        String tempDir = null;
        //状態変更理由
        String statusCmt = NullDefault.getString(paramMdl.getPrj050statusCmt(), "");

        try {

            con__.setAutoCommit(false);

            //TODO情報を取得
            PrjTododataDao ptdDao = new PrjTododataDao(con__);
            ProjectItemModel piMdl = ptdDao.getTodoData(todoSid);

            ProjectUpdateDao projectDao = new ProjectUpdateDao(con__);

            //管理番号
            int kanriNo = piMdl.getKanriNo();
            if (projectSid != piMdl.getProjectSid()) {
                //プロジェクトSIDが変更された場合、管理番号を採番する
                kanriNo = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                          projectSid + GSConstProject.SBNSID_SUB_KANRI,
                          userSid);
                dataexist = false;
            }

            //変更履歴SID
            int hisSid = piMdl.getHisSid();
            PrjStatusHistoryModel pshMdl = null;
            if (status != piMdl.getStatus() || !statusCmt.equals("")) {
                //状態が変更された or 状態変更時コメントが入力された場合
                //変更履歴SIDを採番する
                hisSid = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                         todoSid + GSConstProject.SBNSID_SUB_HISTORY,
                         userSid);

                //更新用のPrjStatusHistoryModelを取得する
                pshMdl = __getPrjStatusHistoryModel(paramMdl, todoSid, hisSid, userSid, now);
            }

            //更新用のPrjTododataModelを取得する
            PrjTododataModel ptMdl =
                __getPrjTododataModel(paramMdl, todoSid, kanriNo, hisSid, userSid, now);

            //更新用のPrjTodomemberModelのリストを取得する
            List<PrjTodomemberModel> memberList =
                __getPrjTodomemberList(paramMdl, todoSid, userSid, now);

            //テンポラリディレクトリパスを取得
            CommonBiz cmnBiz = new CommonBiz();
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            tempDir = temp.getTempPath(reqMdl__,
                    GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
            log__.debug("テンポラリディレクトリ = " + tempDir);

            //テンポラリディレクトリパスにある添付ファイルを全て登録
            List<String> binList =
                cmnBiz.insertBinInfo(con__, tempDir, appRoot, cntCon, userSid, now);

            //TODO情報を更新する
            projectDao.updateTodo(ptMdl, memberList, pshMdl, binList);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {

                con__.commit();

                if (paramMdl.getPrj050MailSend() != GSConstProject.NOT_SEND) {
                    GsMessage gsMsg = new GsMessage(reqMdl__);
                    PrjCommonBiz cmnBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
                    PrjSmailParamModel smlParamModel = new PrjSmailParamModel();
                    smlParamModel.setCmdMode(paramMdl.getPrj050cmdMode());
                    smlParamModel.setPrjSid(projectSid);
                    smlParamModel.setTodoSid(todoSid);
                    smlParamModel.setUsrSid(userSid);
                    smlParamModel.setTarget(paramMdl.getPrj050MailSend());
                    smlParamModel.setHistory(statusCmt);
                    smlParamModel.setAppRoot(appRoot);
                    smlParamModel.setTempDir(tempDir);

                    PrjSmailModel param = cmnBiz.getSmailParamMdl(smlParamModel);

                    cmnBiz.sendTodoEditMail(
                            con__, cntCon, param, appRoot, pconfig);
                }

            } else {
                JDBCUtil.rollback(con__);
            }
        }
        return dataexist;
    }

    /**
     * <br>[機  能] 登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param appRoot ルートディレクトリ
     * @param tempRoot テンポラリディレクトリ
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws Exception その他例外
     */
    public void doInsert(Prj050ParamModel paramMdl,
                          MlCountMtController cntCon,
                          int userSid,
                          String appRoot,
                          String tempRoot,
                          PluginConfig pconfig)
        throws SQLException, IOToolsException, IOException, Exception {

        boolean commitFlg = false;
        UDate now = new UDate();

        //プロジェクトSID
        int projectSid = paramMdl.getPrj050prjSid();
        int todoSid = -1;
        String tempDir = null;
        String statusCmt = paramMdl.getPrj050statusCmt();

        try {

            con__.setAutoCommit(false);

            //TODOSID採番
            todoSid = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                                                           GSConstProject.SBNSID_SUB_TODO,
                                                           userSid);
            //管理番号を採番
            ProjectUpdateDao projectDao = new ProjectUpdateDao(con__);
            int kanriNo = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                           projectSid + GSConstProject.SBNSID_SUB_KANRI,
                           userSid);
            //変更履歴SIDを採番
            int hisSid = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                          todoSid + GSConstProject.SBNSID_SUB_HISTORY,
                          userSid);

            //更新用のPrjTododataModelを取得する
            PrjTododataModel ptMdl =
                __getPrjTododataModel(paramMdl, todoSid, kanriNo, hisSid, userSid, now);

            //更新用のPrjTodomemberModelのリストを取得する
            List<PrjTodomemberModel> memberList =
                __getPrjTodomemberList(paramMdl, todoSid, userSid, now);

            //更新用のPrjStatusHistoryModelを取得する
            PrjStatusHistoryModel pshMdl =
                __getPrjStatusHistoryModel(paramMdl, todoSid, hisSid, userSid, now);

            //テンポラリディレクトリパスを取得
            CommonBiz cmnBiz = new CommonBiz();
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            tempDir = temp.getTempPath(reqMdl__,
                    GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
            log__.debug("テンポラリディレクトリ = " + tempDir);

            //テンポラリディレクトリパスにある添付ファイルを全て登録
            List<String> binList =
                cmnBiz.insertBinInfo(con__, tempDir, appRoot, cntCon, userSid, now);

            //TODO情報を登録する
            projectDao.insertTodo(ptMdl, memberList, pshMdl, binList);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {

                con__.commit();

                if (paramMdl.getPrj050MailSend() != GSConstProject.NOT_SEND) {
                    GsMessage gsMsg = new GsMessage(reqMdl__);
                    PrjCommonBiz cmnBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
                    PrjSmailParamModel smlParamModel = new PrjSmailParamModel();
                    smlParamModel.setCmdMode(paramMdl.getPrj050cmdMode());
                    smlParamModel.setPrjSid(projectSid);
                    smlParamModel.setTodoSid(todoSid);
                    smlParamModel.setUsrSid(userSid);
                    smlParamModel.setTarget(paramMdl.getPrj050MailSend());
                    smlParamModel.setHistory(statusCmt);
                    smlParamModel.setAppRoot(appRoot);
                    smlParamModel.setTempDir(tempDir);
                    PrjSmailModel param = cmnBiz.getSmailParamMdl(smlParamModel);

                    cmnBiz.sendTodoEditMail(
                            con__, cntCon, param, appRoot, pconfig);
                }

            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 更新用のPrjStatusHistoryModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param todoSid TODOSID
     * @param hisSid 変更履歴SID
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return PrjStatusHistoryModel
     */
    private PrjStatusHistoryModel __getPrjStatusHistoryModel(
        Prj050ParamModel paramMdl,
        int todoSid,
        int hisSid,
        int userSid,
        UDate now) {

        //プロジェクトSID
        int projectSid = paramMdl.getPrj050prjSid();

        PrjStatusHistoryModel pshMdl = new PrjStatusHistoryModel();
        pshMdl.setPrjSid(projectSid);
        pshMdl.setPtdSid(todoSid);
        pshMdl.setPshSid(hisSid);

        pshMdl.setPtsSid(paramMdl.getPrj050status());
        pshMdl.setPshReason(paramMdl.getPrj050statusCmt());

        pshMdl.setPshAuid(userSid);
        pshMdl.setPshAdate(now);
        pshMdl.setPshEuid(userSid);
        pshMdl.setPshEdate(now);
        return pshMdl;
    }

    /**
     * <br>[機  能] 更新用のPrjTodomemberModelのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param todoSid TODOSID
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return List in PrjTodomemberModel
     */
    private List<PrjTodomemberModel> __getPrjTodomemberList(
        Prj050ParamModel paramMdl,
        int todoSid,
        int userSid,
        UDate now) {

        //プロジェクトSID
        int projectSid = paramMdl.getPrj050prjSid();

        List<PrjTodomemberModel> memberList = new ArrayList<PrjTodomemberModel>();
        String[] tantos = paramMdl.getPrj050hdnTanto();
        PrjTodomemberModel memberMdl = null;

        if (tantos != null) {
            for (String member : tantos) {
                memberMdl = new PrjTodomemberModel();
                memberMdl.setPrjSid(projectSid);
                memberMdl.setPtdSid(todoSid);
                memberMdl.setUsrSid(NullDefault.getInt(member, -1));
                memberMdl.setPtmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
                memberMdl.setPtmAuid(userSid);
                memberMdl.setPtmAdate(now);
                memberMdl.setPtmEuid(userSid);
                memberMdl.setPtmEdate(now);
                memberList.add(memberMdl);
            }
        }
        return memberList;
    }

    /**
     * <br>[機  能] 更新用のPrjTododataModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj050ParamModel
     * @param todoSid TODOSID
     * @param kanriNo 管理番号
     * @param hisSid 変更履歴SID
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return PrjTododataModel 更新用のPrjTododataModel
     */
    private PrjTododataModel __getPrjTododataModel(
        Prj050ParamModel paramMdl,
        int todoSid,
        int kanriNo,
        int hisSid,
        int userSid,
        UDate now) {

        //プロジェクトSID
        int projectSid = paramMdl.getPrj050prjSid();
        UDate kaisiYotei = new UDate();
        UDate kigen = new UDate();
        UDate kaisiJisseki = new UDate();
        UDate syuryoJisseki = new UDate();

        //開始予定年月日
        kaisiYotei = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj050kaisiYoteiYear(), -1),
                NullDefault.getInt(paramMdl.getPrj050kaisiYoteiMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj050kaisiYoteiDay(), -1));
        //期限年月日
        kigen = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj050kigenYear(), -1),
                NullDefault.getInt(paramMdl.getPrj050kigenMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj050kigenDay(), -1));
        //開始実績年月日
        kaisiJisseki = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj050kaisiJissekiYear(), -1),
                NullDefault.getInt(paramMdl.getPrj050kaisiJissekiMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj050kaisiJissekiDay(), -1));
        //終了実績年月日
        syuryoJisseki = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj050syuryoJissekiYear(), -1),
                NullDefault.getInt(paramMdl.getPrj050syuryoJissekiMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj050syuryoJissekiDay(), -1));

        PrjTododataModel ptMdl = new PrjTododataModel();

        ptMdl.setPrjSid(projectSid);
        ptMdl.setPtdSid(todoSid);
        ptMdl.setPtdNo(kanriNo);
        ptMdl.setPtdCategory(paramMdl.getPrj050cate());
        ptMdl.setPtdTitle(paramMdl.getPrj050title());
        ptMdl.setPtdDatePlan(kaisiYotei);
        ptMdl.setPrjDateLimit(kigen);
        ptMdl.setPtdDateStart(kaisiJisseki);
        ptMdl.setPtdDateEnd(syuryoJisseki);
        ptMdl.setPtdPlanKosu(NullDefault.getBigDecimal(paramMdl.getPrj050yoteiKosu(), null));
        ptMdl.setPtdResultsKosu(NullDefault.getBigDecimal(
                paramMdl.getPrj050jissekiKosu(), null));
        ptMdl.setPtdAlarmKbn(paramMdl.getPrj050keikoku());
        ptMdl.setPtdImportance(paramMdl.getPrj050juyou());
        ptMdl.setPshSid(hisSid);
        ptMdl.setPtsSid(paramMdl.getPrj050status());
        ptMdl.setPtdContent(paramMdl.getPrj050naiyo());
        ptMdl.setPtdAuid(userSid);
        ptMdl.setPtdAdate(now);
        ptMdl.setPtdEuid(userSid);
        ptMdl.setPtdEdate(now);

        return ptMdl;
    }
}

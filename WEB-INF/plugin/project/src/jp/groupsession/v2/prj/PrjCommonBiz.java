package jp.groupsession.v2.prj;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.prj.dao.PrjAdmConfDao;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataTmpDao;
import jp.groupsession.v2.prj.dao.PrjStatusHistoryDao;
import jp.groupsession.v2.prj.dao.PrjTodoBinDao;
import jp.groupsession.v2.prj.dao.PrjTodocategoryDao;
import jp.groupsession.v2.prj.dao.PrjTodocommentDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.dao.PrjTodomemberDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.PrjUserConfDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjAdmConfModel;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;
import jp.groupsession.v2.prj.model.PrjSmailModel;
import jp.groupsession.v2.prj.model.PrjSmailParamModel;
import jp.groupsession.v2.prj.model.PrjStatusHistoryModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjTodomemberModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.PrjUserConfModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusTmpModel;
import jp.groupsession.v2.prj.model.UserModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] プロジェクト管理の共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjCommonBiz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** GSメッセージ */
    private GsMessage gsMsg__ = new GsMessage();
    /** リクエス */
    private RequestModel reqMdl__ = null;

    //TODO リファクタリング
    /**
     * <p>Set Connection
     * @param con Connection
     * @param gsMsg GSメッセージ
     * @param reqMdl RequestModel
     */
    public PrjCommonBiz(Connection con, GsMessage gsMsg, RequestModel reqMdl) {
        con__ = con;
        gsMsg__ = gsMsg;
        reqMdl__ = reqMdl;
    }

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    public PrjCommonBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] リクエストよりコマンドパラメータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return CMDパラメータ
     */
    public static String getCmdProperty(HttpServletRequest req) {
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("--- cmd :" + cmd);
        return cmd;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/project/smail/koushin_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスからTODOコメント登録通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getTodoCmtSmlTemplateFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/project/smail/todocomment_add_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] プロジェクトの登録権限があるかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param buMdl セッションユーザModel
     * @return boolean true=登録権限あり、false=登録権限なし
     * @throws SQLException SQL実行時例外
     */
    public boolean getPrjAddKengen(BaseUserModel buMdl) throws SQLException {

        //登録権限があるかチェックを行う
        PrjAdmConfDao pacDao = new PrjAdmConfDao(con__);
        PrjAdmConfModel pacMdl = pacDao.select();

        boolean prjAdd = false;
        if (pacMdl.getPacPrjEdit() == GSConstProject.PRJ_EDIT_KENGEN_ADM) {
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser =
                cmnBiz.isPluginAdmin(con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

            if (adminUser) {
                prjAdd = true;
            }
        } else {
            prjAdd = true;
        }

        return prjAdd;
    }

    /**
     * <br>[機  能] プロジェクトの編集権限があるかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param buMdl セッションユーザModel
     * @return boolean true=登録権限あり、false=登録権限なし
     * @throws SQLException SQL実行時例外
     */
    public boolean getPrjEditKengen(int projectSid, BaseUserModel buMdl) throws SQLException {

        boolean prjEdit = false;
        //プロジェクトのメンバーで、管理者として登録されているか
        PrjMembersDao pmDao = new PrjMembersDao(con__);
        int count = pmDao.getMemberCount(projectSid, buMdl.getUsrsid(), true);
        if (count > 0) {
            prjEdit = true;
        }

        PrjPrjdataDao ppDao = new PrjPrjdataDao(con__);
        int userSid = ppDao.getEditMyProjectUsrSid(projectSid);
        if (userSid == buMdl.getUsrsid()) {
            prjEdit = true;
        }
        if (userSid == -1) {
            //又は管理者権限を持ったユーザか
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(
                    con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

            if (adminUser) {
                prjEdit = true;
            }
        }

        return prjEdit;
    }

    /**
     * <br>[機  能] プロジェクトの登録・編集権限があるかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmdMode 処理モード
     * @param projectSid プロジェクトSID
     * @param buMdl セッションユーザModel
     * @return boolean true=権限あり、false=権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean getProjectKengen(String cmdMode, int projectSid, BaseUserModel buMdl)
    throws SQLException {

        boolean kengen = false;

        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録モード
            //プロジェクト登録権限があるかチェックを行う
            kengen = getPrjAddKengen(buMdl);

        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新モード
            //プロジェクト編集権限があるかチェックを行う
            kengen = getPrjEditKengen(projectSid, buMdl);
        }
        return kengen;
    }

    /**
     * <br>[機  能] TODOの編集権限があるかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param buMdl セッションユーザModel
     * @return boolean true=権限あり、false=権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean getTodoEditKengen(int projectSid, BaseUserModel buMdl) throws SQLException {

        //プロジェクト情報を取得
        PrjPrjdataDao ppdDao = new PrjPrjdataDao(con__);
        ProjectItemModel piMdl = ppdDao.getProjectInfo(projectSid);
        if (piMdl == null) {
            return false;
        }

        //管理者権限を持ったユーザか
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

        if (adminUser) {
            return true;
        }

        if (piMdl.getEditKengen() == GSConstProject.TODO_EDIT_KENGEN_ALL) {
            //権限設定なし
            return true;
        }

        boolean adminFlg = false;
        if (piMdl.getEditKengen() == GSConstProject.TODO_EDIT_KENGEN_ADM) {
            adminFlg = true;
        }

        PrjMembersDao pmDao = new PrjMembersDao(con__);
        int count = pmDao.getMemberCount(projectSid, buMdl.getUsrsid(), adminFlg);
        if (count > 0) {
            //指定された権限あり
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] TODOの削除権限があるかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param buMdl セッションユーザModel
     * @param admin システム管理者フラグ
     * @return boolean true=権限あり、false=権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean getTodoDeleteKengen(int projectSid, BaseUserModel buMdl, boolean admin)
            throws SQLException {

        //管理者権限を持ったユーザか
        if (admin) {
            return true;
        }

        PrjMembersDao pmDao = new PrjMembersDao(con__);
        int count = pmDao.getMemberCount(projectSid, buMdl.getUsrsid(), true);
        if (count > 0) {
            //プロジェクト管理者権限あり
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] プロジェクト管理個人設定から最大表示件数を取得する
     * <br>[解  説] 取得できない場合は初期値(10件)とする
     * <br>[備  考]
     * @param usid ユーザSID
     * @param mode モード(TODO・プロジェクト)
     * @return int 最大表示件数
     * @throws SQLException SQL実行時例外
     */
    public int getCountLimit(int usid, String mode) throws SQLException {

        PrjUserConfDao pucDao = new PrjUserConfDao(con__);
        PrjUserConfModel pucMdl = pucDao.select(usid);

        int limit = GSConst.LIST_COUNT_LIMIT;
        if (pucMdl != null) {
            if (NullDefault.getString(mode, "").equals(GSConstProject.MODE_PROJECT)) {
                //プロジェクトの表示件数
                limit = pucMdl.getPucPrjCnt();
            } else {
                //TODOの表示件数
                limit = pucMdl.getPucTodoCnt();
            }
        }
        return limit;
    }

    /**
     * <br>[機  能] 警告開始コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return List (in LabelValueBean)  表示件数コンボ
     */
    public List<LabelValueBean> getKeikokuLabel() {

        //1ヶ月前
        String textKeikokuNameBef30 = gsMsg__.getMessage("project.src.8");
        //10日前
        String textKeikokuNameBef10 = gsMsg__.getMessage("project.src.6");
        //5日前
        String textKeikokuNameBef5 = gsMsg__.getMessage("project.src.9");
        //3日前
        String textKeikokuNameBef3 = gsMsg__.getMessage("project.src.7");
        //1日前
        String textKeikokuNameBef1 = gsMsg__.getMessage("project.src.5");
        //無し
        String textNo = gsMsg__.getMessage("cmn.no3");
        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(
                textNo, String.valueOf(GSConstProject.KEIKOKU_NO)));
        labelList.add(new LabelValueBean(
                textKeikokuNameBef1, String.valueOf(GSConstProject.KEIKOKU_BEF1)));
        labelList.add(new LabelValueBean(
                textKeikokuNameBef3, String.valueOf(GSConstProject.KEIKOKU_BEF3)));
        labelList.add(new LabelValueBean(
                textKeikokuNameBef5, String.valueOf(GSConstProject.KEIKOKU_BEF5)));
        labelList.add(new LabelValueBean(
                textKeikokuNameBef10, String.valueOf(GSConstProject.KEIKOKU_BEF10)));
        labelList.add(new LabelValueBean(
                textKeikokuNameBef30, String.valueOf(GSConstProject.KEIKOKU_BEF30)));



        return labelList;
    }

    /**
     * 表示件数コンボを生成します
     * @return List (in LabelValueBean)  表示件数コンボ
     */
    public static List<LabelValueBean> getDspCntLavel() {
        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        for (int cnt = 10; cnt <= 50; cnt += 10) {
            labelList.add(
                    new LabelValueBean(String.valueOf(cnt), String.valueOf(cnt)));
        }
        return labelList;
    }

    /**
     * <br>表示開始日から前後10年のコンボを生成します
     * @param year 基準年
     * @return ArrayList (in LabelValueBean)  年コンボ
     */
    public List<LabelValueBean> getYearList(int year) {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", ""));
        for (int i = year - 10; i <= year + 10; i++) {
            labelList.add(
                    new LabelValueBean(gsMsg__.getMessage(
                            "cmn.year", new String[] {String.valueOf(i)}), String.valueOf(i)));
        }
        return labelList;
    }

    /**
     * <br>月コンボを生成します
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    public List<LabelValueBean> getMonthList() {
        //月
        String textMonth = gsMsg__.getMessage("cmn.month");

        int month = 1;
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", ""));
        for (int i = 0; i < 12; i++) {
            labelList.add(
                    new LabelValueBean(month + textMonth, String.valueOf(month)));
            month++;
        }
        return labelList;
    }

    /**
     * <br>日コンボを生成します
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public List<LabelValueBean> getDayList() {
        //日
        String textDay = gsMsg__.getMessage("cmn.day");

        int day = 1;
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", ""));
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day + textDay, String.valueOf(day)));
            day++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] プロジェクトコンボの値を取得する(検索用)
     * <br>[解  説]
     * <br>[備  考]
     * @param buMdl セッションユーザModel
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getProjectSearchLabel(BaseUserModel buMdl) throws SQLException {

        //全て
        String textAll = gsMsg__.getMessage("cmn.all");

        List<LabelValueBean> labelList = getProjectLabel(buMdl, true);
        labelList.add(0, new LabelValueBean(textAll, String.valueOf(GSConstCommon.NUM_INIT)));

        return labelList;
    }

    /**
     * <br>[機  能] プロジェクトコンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param buMdl セッションユーザModel
     * @param getKbn
     *   0:全てのTODO作成権限があるプロジェクト
     *   1:自分が参加していてTODO作成権限があるプロジュクト
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getCanCreateTodoProjectLabel(BaseUserModel buMdl, int getKbn)
        throws SQLException {

        List<ProjectItemModel> prjList = getCanCreateTodoProjectList(buMdl, getKbn);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        for (ProjectItemModel piMdl : prjList) {
            labelList.add(new LabelValueBean(
                    piMdl.getProjectName(), String.valueOf(piMdl.getProjectSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] プロジェクトコンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param buMdl セッションユーザModel
     * @param openGetFlg true=所属、公開プロジェクト以外も取得、false=所属、公開プロジェクトのみ取得
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getProjectLabel(BaseUserModel buMdl, boolean openGetFlg)
    throws SQLException {

        List<ProjectItemModel> prjList = getAllProjectList(buMdl, openGetFlg);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        for (ProjectItemModel piMdl : prjList) {
            labelList.add(new LabelValueBean(
                    piMdl.getProjectName(), String.valueOf(piMdl.getProjectSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] プロジェクトリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param buMdl BaseUserModel
     * @param getKbn
     *   0:全てのTODO作成権限があるプロジェクト
     *   1:自分が参加していてTODO作成権限があるプロジュクト
     * @return List in ProjectItemModel
     * @throws SQLException SQL実行時例外
     */
    public List<ProjectItemModel> getCanCreateTodoProjectList(BaseUserModel buMdl, int getKbn)
        throws SQLException {

        //プロジェクトを取得
        ProjectSearchDao projectDao = new ProjectSearchDao(con__);
        return projectDao.getCanCreateTodoProjectList(buMdl, getKbn);
    }

    /**
     * <br>[機  能] プロジェクトリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param buMdl セッションユーザModel
     * @param openGetFlg true=所属、公開プロジェクト以外も取得、false=所属、公開プロジェクトのみ取得
     * @return List in ProjectItemModel
     * @throws SQLException SQL実行時例外
     */
    public List<ProjectItemModel> getAllProjectList(BaseUserModel buMdl, boolean openGetFlg)
    throws SQLException {

        //プロジェクトの検索条件をセットする
        ProjectSearchModel search = new ProjectSearchModel();
        search.setUserSid(buMdl.getUsrsid());
        search.setOrder(GSConstProject.SORT_PRJECT_START);
        search.setSort(GSConst.ORDER_KEY_ASC);
//        search.setMirai("1");
        //完了プロジェクト表示フラグ true=表示
        search.setEndPrjFlg(true);

        //取得区分
        int getKbn = ProjectSearchModel.GET_OPEN;
        if (openGetFlg) {
            //所属、公開プロジェクト以外も取得
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser =
                cmnBiz.isPluginAdmin(con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

            if (adminUser) {
                //管理者は全てのプロジェクトを取得
                getKbn = ProjectSearchModel.GET_ALL;
            }
        }
        search.setGetKbn(getKbn);

        //プロジェクトを取得
        ProjectSearchDao projectDao = new ProjectSearchDao(con__);
        return projectDao.getAllProjectList(search);
    }

    /**
     * <br>[機  能] TODOカテゴリコンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getTodoCategoryLabel(int projectSid) throws SQLException {
        //全て
        String textAll = gsMsg__.getMessage("cmn.all");
        String textNo = gsMsg__.getMessage("cmn.no");
        PrjTodocategoryDao ptcDao = new PrjTodocategoryDao(con__);
        List<PrjTodocategoryModel> ptcList = ptcDao.selectProjects(projectSid);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        labelList.add(new LabelValueBean(textAll, GSConstProject.TODO_CATEGORY_ALL));
        labelList.add(new LabelValueBean(textNo, GSConstProject.TODO_CATEGORY_NO));

        for (PrjTodocategoryModel ptcMdl : ptcList) {
            labelList.add(new LabelValueBean(
                    ptcMdl.getPtcName(), String.valueOf(ptcMdl.getPtcCategorySid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] TODOカテゴリリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param projectSid プロジェクトSID
     * @return ArrayList in PrjTodocategoryModel
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<PrjTodocategoryModel> getTodoCategoryList(int projectSid)
        throws SQLException {
        //無し
        String textNo = gsMsg__.getMessage("cmn.no3");
        ArrayList<PrjTodocategoryModel> ret = new ArrayList<PrjTodocategoryModel>();
        PrjTodocategoryModel ptcMdl = new PrjTodocategoryModel();
        ptcMdl.setPtcCategorySid(GSConstCommon.NUM_INIT);
        ptcMdl.setPtcName(textNo);
        ret.add(ptcMdl);

        PrjTodocategoryDao ptcDao = new PrjTodocategoryDao(con__);
        List<PrjTodocategoryModel> ptcList = ptcDao.selectProjects(projectSid);

        for (PrjTodocategoryModel dbMdl : ptcList) {
            ptcMdl = new PrjTodocategoryModel();
            ptcMdl.setPtcCategorySid(dbMdl.getPtcCategorySid());
            ptcMdl.setPtcName(NullDefault.getString(dbMdl.getPtcName(), ""));
            ret.add(ptcMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] オブジェクトファイルからProjectStatusModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param dirId ディレクトリID
     * @return ProjectStatusModel
     * @throws IOToolsException IOエラー
     */
    public static ProjectStatusModel getProjectStatusModel(
        RequestModel reqMdl, String dirId) throws IOToolsException {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl,
                GSConstProject.PLUGIN_ID_PROJECT, dirId,
                GSConstProject.TEMP_STATUS_PRJ);

        //オブジェクトファイルを取得
        if (!IOTools.isFileCheck(filePath, GSConstProject.SAVE_FILENAME, false)) {
            return null;
        }

        ObjectFile objFile = new ObjectFile(filePath, GSConstProject.SAVE_FILENAME);
        Object formData = objFile.load();
        if (formData == null) {
            return null;
        }

        //プロジェクト状態
        return (ProjectStatusModel) formData;
    }

    /**
     * <br>[機  能] オブジェクトファイルからProjectStatusModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param dirId ディレクトリID
     * @return ProjectStatusModel
     * @throws IOToolsException IOエラー
     */
    public static ProjectStatusTmpModel getProjectStatusTmpModel(
            RequestModel reqMdl, String dirId) throws IOToolsException {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl,
                GSConstProject.PLUGIN_ID_PROJECT, dirId,
                GSConstProject.TEMP_STATUS_PRJ);

        if (!IOTools.isFileCheck(filePath, GSConstProject.SAVE_FILENAME, false)) {
            return null;
        }

        ObjectFile objFile = new ObjectFile(filePath, GSConstProject.SAVE_FILENAME);
        Object formData = objFile.load();
        if (formData == null) {
            return null;
        }

        //プロジェクト状態
        return (ProjectStatusTmpModel) formData;
    }

    /**
     * <br>[機  能] ProjectStatusModelをオブジェクトファイルに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param projectStatus ProjectStatusModel
     * @param rootDir ルートディレクトリ
     * @param reqMdl リクエストモデル
     * @param dirId ディレクトリID
     * @throws IOToolsException IOエラー
     */
    public static void saveObjFile(
        ProjectStatusModel projectStatus,
        String rootDir,
        RequestModel reqMdl, String dirId) throws IOToolsException {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl,
                GSConstProject.PLUGIN_ID_PROJECT, dirId,
                GSConstProject.TEMP_STATUS_PRJ);

        //ファイル保存
        ObjectFile objFile = new ObjectFile(filePath, GSConstProject.SAVE_FILENAME);
        objFile.save(projectStatus);
    }

    /**
     * <br>[機  能] ProjectStatusTmpModelをオブジェクトファイルに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param projectStatus ProjectStatusModel
     * @param rootDir ルートディレクトリ
     * @param reqMdl リクエストモデル
     * @param dirId ディレクトリID
     * @throws IOToolsException IOエラー
     */
    public static void saveObjFile(
        ProjectStatusTmpModel projectStatus,
        String rootDir,
        RequestModel reqMdl, String dirId) throws IOToolsException {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl,
                GSConstProject.PLUGIN_ID_PROJECT, dirId,
                GSConstProject.TEMP_STATUS_PRJ);

        //ファイル保存
        ObjectFile objFile = new ObjectFile(filePath, GSConstProject.SAVE_FILENAME);
        objFile.save(projectStatus);
    }

    /**
     * <br>[機  能] オブジェクトファイルを指定したパスで別名保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param reqMdl リクエストモデル
     * @param fileName オブジェクトファイル名
     * @param saveFileName 別名保存するファイル名
     * @param dirId ディレクトリID
     * @throws IOToolsException IOエラー
     */
    public static void saveNewFile(
        String rootDir,
        RequestModel reqMdl,
        String fileName,
        String saveFileName, String dirId) throws IOToolsException {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl,
                GSConstProject.PLUGIN_ID_PROJECT, dirId,
                GSConstProject.TEMP_STATUS_PRJ);
        if (!IOTools.isFileCheck(filePath, fileName, false)) {
            return;
        }

        ObjectFile objFile = new ObjectFile(filePath, fileName);
        Object formData = objFile.load();
        if (formData == null) {
            return;
        }

        //プロジェクト状態
        ProjectStatusModel projectStatus = (ProjectStatusModel) formData;

        //画面遷移前の状態のファイル保存
        objFile = new ObjectFile(filePath, saveFileName);
        objFile.save(projectStatus);
    }

    /**
     * <br>[機  能] オブジェクトファイルを指定したパスで別名保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param reqMdl リクエストモデル
     * @param fileName オブジェクトファイル名
     * @param saveFileName 別名保存するファイル名
     * @param dirId ディレクトリID
     * @throws IOToolsException IOエラー
     */
    public static void saveNewTmpFile(
        String rootDir,
        RequestModel reqMdl,
        String fileName,
        String saveFileName, String dirId) throws IOToolsException {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(reqMdl,
                GSConstProject.PLUGIN_ID_PROJECT, dirId,
                GSConstProject.TEMP_STATUS_PRJ);
        if (!IOTools.isFileCheck(filePath, fileName, false)) {
            return;
        }

        ObjectFile objFile = new ObjectFile(filePath, fileName);
        Object formData = objFile.load();
        if (formData == null) {
            return;
        }

        //プロジェクト状態
        ProjectStatusTmpModel projectStatus = (ProjectStatusTmpModel) formData;

        //画面遷移前の状態のファイル保存
        objFile = new ObjectFile(filePath, saveFileName);
        objFile.save(projectStatus);
    }

    /**
     * <br>[機  能] UDateのインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @return UDate
     */
    public static UDate createUDate(int year, int month, int day) {
        if (year <= -1 || month <= -1 || day <= -1) {
            return null;
        }

        UDate date = UDate.getInstance(0);
        date.setDate(year, month, day);
        return date;
    }

    /**
     * <br>[機  能] 重要度(区分)から重要度の表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param weightKbn 重要度(区分)
     * @return String 重要度の表示文字列
     */
    public String getWeightName(int weightKbn) {

        String weightName = "";

        //低
        String textLow = gsMsg__.getMessage("project.58");
        //中
        String textMiddle = gsMsg__.getMessage("project.59");
        //高
        String textHigh = gsMsg__.getMessage("project.60");

        if (weightKbn == GSConstProject.WEIGHT_KBN_LOW) {
            weightName = textLow;

        } else if (weightKbn == GSConstProject.WEIGHT_KBN_MIDDLE) {
            weightName = textMiddle;

        } else if (weightKbn == GSConstProject.WEIGHT_KBN_HIGH) {
            weightName = textHigh;

        }
        return weightName;
    }

    /**
     * <br>[機  能] 予算(int)を表示用に加工して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param yosan 予算(int)
     * @return String
     */
    public String getYosanStr(long yosan) {

        //円
        String textEn = gsMsg__.getMessage("project.103");
        String yosanStr = "";

        if (yosan > -1) {
            yosanStr =
                StringUtil.toCommaFormat(
                        String.valueOf(yosan)) + textEn;
        }
        return yosanStr;
    }

    /**
     * <br>[機  能] 公開区分から表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param koukaiKbn 公開区分
     * @return String 表示文字列
     */
    public String getKoukaiKbnName(int koukaiKbn) {

        String koukaiKbnName = "";

        //公開
        String textPublic = gsMsg__.getMessage("cmn.public");
        //非公開
        String textPrivate = gsMsg__.getMessage("cmn.private");

        if (koukaiKbn == GSConstProject.KBN_KOUKAI_ENABLED) {
            koukaiKbnName = textPublic;

        } else if (koukaiKbn == GSConstProject.KBN_KOUKAI_DISABLED) {
            koukaiKbnName = textPrivate;

        }
        return koukaiKbnName;

    }

    /**
     * <br>[機  能] TODO編集権限(区分)からTODO編集権限の表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoKengenKbn 編集権限(区分)
     * @return String 編集権限の表示文字列
     */
    public String getTodoKengenName(int todoKengenKbn) {

        //所属メンバーのみ
        String textEditKengenMem = gsMsg__.getMessage("project.prj020.8");
        //プロジェクト管理者のみ
        String textEditKengenAdm = gsMsg__.getMessage("project.13");
        //プロジェクト管理者のみ
        String textEditKengenAll = gsMsg__.getMessage("cmn.no.setting.permission");
        String todoKengenName = "";

        if (todoKengenKbn == GSConstProject.TODO_EDIT_KENGEN_MEM) {
            todoKengenName = textEditKengenMem;
        } else if (todoKengenKbn == GSConstProject.TODO_EDIT_KENGEN_ADM) {
            todoKengenName = textEditKengenAdm;

        } else if (todoKengenKbn == GSConstProject.TODO_EDIT_KENGEN_ALL) {
            todoKengenName = textEditKengenAll;

        }
        return todoKengenName;
    }

    /**
     * <br>[機  能] ショートメール通知先区分からショートメール通知先表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smailKbn ショートメール通知先
     * @return smailKbnStr ショートメール通知先表示文字列
     */
    public String getSmailKbnStr(int smailKbn) {

        String smailKbnStr = "";
        //自由に選択可能
        String textMailFree = gsMsg__.getMessage("project.16");
        //必ずプロジェクト管理者を含める
        String textMailSendAdmin = gsMsg__.getMessage("project.17");
        if (smailKbn == GSConstProject.TODO_MAIL_FREE) {
            smailKbnStr = textMailFree;

        } else if (smailKbn == GSConstProject.TODO_MAIL_SEND_ADMIN) {
            smailKbnStr = textMailSendAdmin;
        }
        return smailKbnStr;
    }

    /**
     * <br>[機  能] 警告開始(区分)から警告開始の表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoKengenKbn 警告開始(区分)
     * @return String 警告開始の表示文字列
     */
    public String getKeikokuName(int todoKengenKbn) {

        //無し
        String textNo = gsMsg__.getMessage("cmn.no3");
        //1ヶ月前
        String textKeikokuNameBef30 = gsMsg__.getMessage("project.src.8");
        //10日前
        String textKeikokuNameBef10 = gsMsg__.getMessage("project.src.6");
        //5日前
        String textKeikokuNameBef5 = gsMsg__.getMessage("project.src.9");
        //3日前
        String textKeikokuNameBef3 = gsMsg__.getMessage("project.src.7");
        //1日前
        String textKeikokuNameBef1 = gsMsg__.getMessage("project.src.5");

        String keikokuName = "";

        if (todoKengenKbn == GSConstProject.KEIKOKU_NO) {
            keikokuName = textNo;

        } else if (todoKengenKbn == GSConstProject.KEIKOKU_BEF30) {
            keikokuName = textKeikokuNameBef30;

        } else if (todoKengenKbn == GSConstProject.KEIKOKU_BEF10) {
            keikokuName = textKeikokuNameBef10;

        } else if (todoKengenKbn == GSConstProject.KEIKOKU_BEF5) {
            keikokuName = textKeikokuNameBef5;

        } else if (todoKengenKbn == GSConstProject.KEIKOKU_BEF3) {
            keikokuName = textKeikokuNameBef3;

        } else if (todoKengenKbn == GSConstProject.KEIKOKU_BEF1) {
            keikokuName = textKeikokuNameBef1;

        }
        return keikokuName;
    }

    /**
     * <br>[機  能] 日付コンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getTargetDateLabel() throws SQLException {

        //未入力
        String textNoinput = gsMsg__.getMessage("project.src.2");
        //今日+未来
        String textDateTheFuture = gsMsg__.getMessage("project.src.3");
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        //今日+過去
        String textDatePast = gsMsg__.getMessage("project.src.4");
        //全て
        String textAll = gsMsg__.getMessage("cmn.all");
        //全て
        labelList.add(
                new LabelValueBean(
                        textAll,
                        String.valueOf(GSConstProject.DATE_ALL)));
        //今日
        String textToday = gsMsg__.getMessage("cmn.today");
        //今日
        labelList.add(
                new LabelValueBean(
                        textToday,
                        String.valueOf(GSConstProject.DATE_TODAY)));

        //今日以前
        labelList.add(
                new LabelValueBean(
                        textDatePast,
                        String.valueOf(GSConstProject.DATE_THE_PAST)));

        //今日以降
        labelList.add(
                new LabelValueBean(
                        textDateTheFuture,
                        String.valueOf(GSConstProject.DATE_THE_FUTURE)));

        //未入力
        labelList.add(
                new LabelValueBean(
                        textNoinput,
                        String.valueOf(GSConstProject.DATE_NOT_INPUT)));

        return labelList;
    }

    /**
     * <br>[機  能] 日付コンボの表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param kbn 日付区分
     * @return ret 日付区分の表示文字列
     */
    public String getTargetDateString(int kbn) {

        //未入力
        String textNoinput = gsMsg__.getMessage("project.src.2");
        //今日+未来
        String textDateTheFuture = gsMsg__.getMessage("project.src.3");
        //今日+過去
        String textDatePast = gsMsg__.getMessage("project.src.4");
        String ret = "";

        switch (kbn) {
           //全て
            case GSConstProject.DATE_ALL:
                ret = gsMsg__.getMessage("cmn.all");
                break;

            //今日
            case GSConstProject.DATE_TODAY:
                //今日
                String textToday = gsMsg__.getMessage("cmn.today");
                ret = textToday;
                break;
            //今日以前
            case GSConstProject.DATE_THE_PAST:
                ret = textDatePast;
                break;
            //今日以降
            case GSConstProject.DATE_THE_FUTURE:
                ret = textDateTheFuture;
                break;
            //未入力
            case GSConstProject.DATE_NOT_INPUT:
                ret = textNoinput;
                break;
            default:
                break;
        }

        return ret;
    }

    /**
     * <br>[機  能] 状態コンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param projectSid プロジェクトSID
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getStatusLabel(int projectSid) throws SQLException {
        return getStatusLabel(projectSid, true);
    }

    /**
     * <br>[機  能] 状態コンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param projectSid プロジェクトSID
     * @param addDefault trueの場合はコンボに"全て"、"未完了を設定する
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getStatusLabel(int projectSid, boolean addDefault)
    throws SQLException {
        //100%　完了

        String textKanryo = gsMsg__.getMessage("project.src.70");
        //0%　予定
        String textMikan = gsMsg__.getMessage("project.src.71");
        //未完了
        String textYoteiAndMikan = gsMsg__.getMessage("project.src.72");
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        //全て
        String textAll = gsMsg__.getMessage("cmn.all");
        if (addDefault) {
            //全て
            labelList.add(
                    new LabelValueBean(
                            textAll,
                            String.valueOf(GSConstProject.STATUS_ALL)));

            //未完了
            labelList.add(
                    new LabelValueBean(
                            textYoteiAndMikan,
                            String.valueOf(GSConstProject.STATUS_YOTEI_AND_MIKAN)));
        }

        if (projectSid > 0) {

            PrjTodostatusDao ptdDao = new PrjTodostatusDao(con__);
            List<PrjTodostatusModel> stsList = ptdDao.selectProjects(projectSid);

            for (PrjTodostatusModel mdl : stsList) {

                labelList.add(
                        new LabelValueBean(
                                String.valueOf(mdl.getPtsRate())
                                + "%"
                                + "　"
                                + NullDefault.getString(mdl.getPtsName(), ""),
                                String.valueOf(mdl.getPtsSid())));
            }

        } else {

            //0% 予定
            labelList.add(
                    new LabelValueBean(
                            textMikan,
                            String.valueOf(GSConstProject.STATUS_MIKAN)));
            //進行中
            String textOnGoing = gsMsg__.getMessage("rng.application.ongoing");
            //進行中
            labelList.add(
                    new LabelValueBean(
                            textOnGoing,
                            String.valueOf(GSConstProject.STATUS_SINKO)));

            //100% 完了
            labelList.add(
                    new LabelValueBean(
                            textKanryo,
                            String.valueOf(GSConstProject.STATUS_KANRYO)));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 状態コンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return List in LabelValueBean
     */
    public List<LabelValueBean> getStatusLabel() {
        return getStatusLabel(true);
    }

    /**
     * <br>[機  能] 状態コンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param addSearch 状態コンボに「全て」、「未完了」を追加する
     * @return List in LabelValueBean
     */
    public List<LabelValueBean> getStatusLabel(boolean addSearch) {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        //100%　完了

        String textKanryo = gsMsg__.getMessage("project.src.70");
        //0%　予定
        String textMikan = gsMsg__.getMessage("project.src.71");
        //未完了
        String textKeikokuNameBef30 = gsMsg__.getMessage("project.src.72");
        //全て
        String textAll = gsMsg__.getMessage("cmn.all");
        if (addSearch) {
            //全て
            labelList.add(
                    new LabelValueBean(
                            textAll,
                            String.valueOf(GSConstProject.STATUS_ALL)));

            //未完了
            labelList.add(
                    new LabelValueBean(
                            textKeikokuNameBef30,
                            String.valueOf(GSConstProject.STATUS_YOTEI_AND_MIKAN)));
        }

        //0% 予定
        labelList.add(
                new LabelValueBean(
                        textMikan, "1"));

        //100% 完了
        labelList.add(
                new LabelValueBean(
                        textKanryo, "2"));

        return labelList;
    }

    /**
     * <br>[機  能] 状態表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param projectSid プロジェクトSID
     * @param statusSid 状態区分
     * @param con コネクション
     * @return ret 状態表示文字列
     * @throws SQLException SQL実行時例外
     */
    public String getStatusString(int projectSid, int statusSid, Connection con)
        throws SQLException {
        //100%　完了

        //未完了
        String textYoteiAndMikan = gsMsg__.getMessage("project.src.72");
        String textKanryo = gsMsg__.getMessage("project.src.70");
        //0%　予定
        String textMikan = gsMsg__.getMessage("project.src.71");
        String ret = "";
        //全て
        String textAll = gsMsg__.getMessage("cmn.all");
        switch (statusSid) {
            //全て
            case GSConstProject.STATUS_ALL:
                ret = textAll;
                break;
            //未完了
            case GSConstProject.STATUS_YOTEI_AND_MIKAN:
                ret = textYoteiAndMikan;
                break;
            default:
                break;
        }

        if (projectSid > 0) {
            PrjTodostatusDao dao = new PrjTodostatusDao(con);
            PrjTodostatusModel mdl = dao.select(projectSid, statusSid);
            if (mdl != null) {
                ret =
                    String.valueOf(mdl.getPtsRate())
                    + "%"
                    + "　"
                    + NullDefault.getString(mdl.getPtsName(), "");
            }
        } else {
            switch (statusSid) {
                //0% 予定
                case GSConstProject.STATUS_MIKAN:
                    ret = textMikan;
                    break;
                //進行中
                case GSConstProject.STATUS_SINKO:
                    //進行中
                    String textOnGoing = gsMsg__.getMessage("rng.application.ongoing");
                    ret = textOnGoing;
                    break;
                //100% 完了
                case GSConstProject.STATUS_KANRYO:
                    ret = textKanryo;
                    break;
                default:
                    break;
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 状態表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param statusSid 状態区分
     * @return ret 状態表示文字列
     */
    public String getStatusString(int statusSid) {

        String ret = "";
        //100%　完了

        String textKanryo = gsMsg__.getMessage("project.src.70");
        //0%　予定
        String textMikan = gsMsg__.getMessage("project.src.71");
        //未完了
        String textYoteiAndMikan = gsMsg__.getMessage("project.src.72");
        //全て
        String textAll = gsMsg__.getMessage("cmn.all");
        switch (statusSid) {
            //全て
            case GSConstProject.STATUS_ALL:
                ret = textAll;
                break;
            //未完了
            case GSConstProject.STATUS_YOTEI_AND_MIKAN:
                ret = textYoteiAndMikan;
                break;
            //0% 予定
            case 1:
                ret = textMikan;
                break;
            //100% 完了
            case 2:
                ret = textKanryo;
                break;
            default:
                break;
        }

        return ret;
    }

    /**
     * <br>[機  能] メンバコンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param projectSid プロジェクトSID
     * @param usrSid セッションユーザSID
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getMemberLabel(int projectSid, int usrSid)
        throws SQLException {

        List<UsrLabelValueBean> labelList = new ArrayList<UsrLabelValueBean>();

        //全員
        String textAll = gsMsg__.getMessage("project.src.11");
        //自分
        String textMine = gsMsg__.getMessage("project.src.12");
        //担当なし
        String textNoMember = gsMsg__.getMessage("project.src.13");
        //全員
        labelList.add(
                new UsrLabelValueBean(
                        textAll,
                        String.valueOf(GSConstProject.MEMBER_ALL)));
        //担当無し
        labelList.add(
                new UsrLabelValueBean(
                        textNoMember,
                        String.valueOf(GSConstProject.MEMBER_NOTHING)));

        CmnUsrmInfDao infDao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel infMdl = infDao.select(usrSid);

        if (infMdl != null) {
            //自分
            labelList.add(
                    new UsrLabelValueBean(
                            textMine,
                            String.valueOf(usrSid)));
        }

        PrjMembersDao prmDao = new PrjMembersDao(con__);
        List<UserModel> memberList = prmDao.getMemberList(projectSid, usrSid);

        //その他メンバー
        for (UserModel mdl : memberList) {

            labelList.add(
                    new UsrLabelValueBean(
                            NullDefault.getString(mdl.getSei(), "")
                            + "　"
                            + NullDefault.getString(mdl.getMei(), ""),
                            String.valueOf(mdl.getUserSid()),
                            mdl.getUsrUkoFlg()));
        }

        return labelList;
    }

    /**
     * <br>[機  能] メンバコンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid セッションユーザSID
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getMemberLabel(int usrSid) throws SQLException {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        //全員
        String textAll = gsMsg__.getMessage("project.src.11");
        //自分
        String textMine = gsMsg__.getMessage("project.src.12");
        //全員
        labelList.add(
                new LabelValueBean(
                        textAll,
                        String.valueOf(GSConstProject.MEMBER_ALL)));

        CmnUsrmInfDao infDao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel infMdl = infDao.select(usrSid);

        if (infMdl != null) {
            //自分
            labelList.add(
                    new LabelValueBean(
                            textMine,
                            String.valueOf(usrSid)));
        }

        return labelList;
    }

    /**
     * <br>[機  能] メンバ文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid セッションユーザSID
     * @return ret メンバ文字列
     * @throws SQLException SQL実行時例外
     */
    public String getMemberString(int usrSid) throws SQLException {

        String ret = "";

        //全員
        String textAll = gsMsg__.getMessage("project.src.11");
        //自分
        String textMine = gsMsg__.getMessage("project.src.12");
        if (usrSid > 0) {

            CmnUsrmInfDao infDao = new CmnUsrmInfDao(con__);
            CmnUsrmInfModel infMdl = infDao.select(usrSid);

            if (infMdl != null) {
                ret = textMine;
            } else {
                ret = textAll;
            }
        } else {
            ret = textAll;
        }

        return ret;
    }

    /**
     * <br>[機  能] プロジェクトコンボの値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param buMdl セッションユーザModel
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getTargetProjectLabel(BaseUserModel buMdl)
        throws SQLException {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        //参加プロジェクト　全て
        String textAll = gsMsg__.getMessage("project.src.57");
        //全てのプロジェクト
        String textAllProject = gsMsg__.getMessage("cmn.allprojects");
        //全ての完了プロジェクト
        String textEndAllProject = gsMsg__.getMessage("project.src.53");
        //参加プロジェクト　完了
        String textMemberEnd = gsMsg__.getMessage("project.src.58");
        //参加プロジェクト　未完
        String textMemberNotEnd = gsMsg__.getMessage("project.src.59");
        //全ての未完プロジェクト
        String textNotEndAll = gsMsg__.getMessage("project.src.60");
        //公開プロジェクト　全て
        String textOpenAll = gsMsg__.getMessage("project.src.61");
        //公開プロジェクト　完了
        String textOpenEnd = gsMsg__.getMessage("project.src.62");
        //参加プロジェクト 未完
        labelList.add(
                new LabelValueBean(
                        textMemberNotEnd,
                        String.valueOf(GSConstProject.PRJ_MEMBER_NOT_END)));

        //参加プロジェクト 完了
        labelList.add(
                new LabelValueBean(
                        textMemberEnd,
                        String.valueOf(GSConstProject.PRJ_MEMBER_END)));

        //参加プロジェクト 全て
        labelList.add(
                new LabelValueBean(
                        textAll,
                        String.valueOf(GSConstProject.PRJ_MEMBER_ALL)));
        //公開プロジェクト　未完
        String textOpenNotEnd = gsMsg__.getMessage("project.src.63");
        //公開プロジェクト 未完
        labelList.add(
                new LabelValueBean(
                        textOpenNotEnd,
                        String.valueOf(GSConstProject.PRJ_OPEN_NOT_END)));

        //公開プロジェクト 完了
        labelList.add(
                new LabelValueBean(
                        textOpenEnd,
                        String.valueOf(GSConstProject.PRJ_OPEN_END)));

        //公開プロジェクト 全て
        labelList.add(
                new LabelValueBean(
                        textOpenAll,
                        String.valueOf(GSConstProject.PRJ_OPEN_ALL)));

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

        if (adminUser) {

            //全ての未完プロジェクト
            labelList.add(
                    new LabelValueBean(
                            textNotEndAll,
                            String.valueOf(GSConstProject.PRJ_NOT_END_ALL)));

            //全ての完了プロジェクト
            labelList.add(
                    new LabelValueBean(
                            textEndAllProject,
                            String.valueOf(GSConstProject.PRJ_END_ALL)));

            //全てのプロジェクト
            labelList.add(
                    new LabelValueBean(
                            textAllProject,
                            String.valueOf(GSConstProject.PRJ_ALL)));
        }

        return labelList;
    }

    /**
     * <br>[機  能] プロジェクトコンボの表示文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param buMdl セッションユーザModel
     * @param projectKbn プロジェクト区分
     * @return ret プロジェクト表示文字列
     * @throws SQLException SQL実行例外
     */
    public String getTargetProjectString(BaseUserModel buMdl, int projectKbn)
    throws SQLException {

        String ret = "";

        //全員
        String textAll = gsMsg__.getMessage("project.src.11");
        //全てのプロジェクト
        String textAllProject = gsMsg__.getMessage("cmn.allprojects");
        //全ての完了プロジェクト
        String textEndAllProject = gsMsg__.getMessage("project.src.53");
        //参加プロジェクト　完了
        String textMemberEnd = gsMsg__.getMessage("project.src.58");
        //参加プロジェクト　未完
        String textMemberNotEnd = gsMsg__.getMessage("project.src.59");
        //全ての未完プロジェクト
        String textNotEndAll = gsMsg__.getMessage("project.src.60");
        //公開プロジェクト　全て
        String textOpenAll = gsMsg__.getMessage("project.src.61");
        //公開プロジェクト　完了
        String textOpenEnd = gsMsg__.getMessage("project.src.62");
        //公開プロジェクト　未完
        String textOpenNotEnd = gsMsg__.getMessage("project.src.63");
        switch (projectKbn) {
            //参加プロジェクト 未完
            case GSConstProject.PRJ_MEMBER_NOT_END:
                ret = textMemberNotEnd;
                break;
            //参加プロジェクト 完了
            case GSConstProject.PRJ_MEMBER_END:
                ret = textMemberEnd;
                break;
            //参加プロジェクト 全て
            case GSConstProject.PRJ_MEMBER_ALL:
                ret = textAll;
                break;
            //公開プロジェクト 未完
            case GSConstProject.PRJ_OPEN_NOT_END:
                ret = textOpenNotEnd;
                break;
            //公開プロジェクト 完了
            case GSConstProject.PRJ_OPEN_END:
                ret = textOpenEnd;
                break;
            //公開プロジェクト 全て
            case GSConstProject.PRJ_OPEN_ALL:
                ret = textOpenAll;
                break;
            default:
                break;
        }

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

        if (StringUtil.isNullZeroStringSpace(ret) && adminUser) {

            switch (projectKbn) {
                //全ての未完プロジェクト
                case GSConstProject.PRJ_NOT_END_ALL:
                    ret = textNotEndAll;
                    break;
                //全ての完了プロジェクト
                case GSConstProject.PRJ_END_ALL:
                    ret = textEndAllProject;
                    break;
                //全ての完了プロジェクト
                case GSConstProject.PRJ_ALL:
                    ret = textAllProject;
                    break;
                default:
                    break;
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] ショートメールプラグインが使用可能か取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param pluginId プラグインID
     * @param pconfig プラグイン設定情報
     * @return ret true:使用可能 false:使用不可能
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseSmailPlugin(String pluginId, PluginConfig pconfig)
        throws SQLException {

        boolean ret = false;

        CommonBiz cmnBiz = new CommonBiz();

        //ショートメールが利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstProject.PLUGIN_ID_SMAIL, pconfig)) {
            ret = true;
        }

        return ret;
    }

    /**
     * <br>[機  能] 回覧板プラグインが使用可能か取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param pluginId プラグインID
     * @param pconfig プラグイン設定情報
     * @return ret true:使用可能 false:使用不可能
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseCircularPlugin(String pluginId, PluginConfig pconfig)
        throws SQLException {

        boolean ret = false;

        CommonBiz cmnBiz = new CommonBiz();

        //回覧板が利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstProject.PLUGIN_ID_CIRCULAR, pconfig)) {
            ret = true;
        }

        return ret;
    }

    /**
     * <br>[機  能] ショートメール通知用データモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramModel PrjSmailParamModel
     * @return ret ショートメール通知用データモデル
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IO例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public PrjSmailModel getSmailParamMdl(PrjSmailParamModel paramModel)
        throws SQLException, IOToolsException, IOException, TempFileException {

        //プロジェクト情報を取得
        PrjPrjdataDao prjDao = new PrjPrjdataDao(con__);
        PrjPrjdataModel prjMdl = prjDao.getProjectData(paramModel.getPrjSid());

        //TODO情報を取得
        PrjTododataDao ptdDao = new PrjTododataDao(con__);
        ProjectItemModel piMdl = ptdDao.getTodoData(paramModel.getTodoSid());

        PrjSmailModel ret = new PrjSmailModel();

        //処理モード
        ret.setCmdMode(paramModel.getCmdMode());
        //プロジェクト名称
        ret.setProjectName(prjMdl.getPrjName());
        //管理番号
        ret.setKanriNo(
                StringUtil.toDecFormat(
                        piMdl.getKanriNo(),
                        GSConstProject.KANRI_NO_FORMAT));
        //タイトル
        ret.setTitle(NullDefault.getString(piMdl.getTodoTitle(), ""));
        //予定 日付
        UDate yoteiFr = piMdl.getStartDate();
        UDate yoteiTo = piMdl.getEndDate();
        String yoteiString = "";
        if (yoteiFr != null) {
            yoteiString = UDateUtil.getSlashYYMD(yoteiFr);
        }
        if (yoteiTo != null) {
            yoteiString = yoteiString + " ～ ";
            yoteiString = yoteiString + UDateUtil.getSlashYYMD(yoteiTo);
        }

        ret.setYoteiDate(yoteiString);

        //予定 工数
        String yoteiKosu = "";
        if (piMdl.getYoteiKosu() != null) {
            yoteiKosu = piMdl.getYoteiKosu().toString();
        }
        ret.setYoteiKosu(yoteiKosu);

        //実績 日付
        UDate zissekiFr = piMdl.getStartJissekiDate();
        UDate zissekiTo = piMdl.getEndJissekiDate();
        String zissekiString = "";
        if (zissekiFr != null) {
            zissekiString = UDateUtil.getSlashYYMD(zissekiFr);
        }
        if (zissekiTo != null) {
            zissekiString = zissekiString + " ～ ";
            zissekiString = zissekiString + UDateUtil.getSlashYYMD(zissekiTo);
        }

        //実績 日付
        ret.setZissekiDate(zissekiString);

        //実績 工数
        String zissekiKosu = "";
        if (piMdl.getJissekiKosu() != null) {
            zissekiKosu = piMdl.getJissekiKosu().toString();
        }
        ret.setZissekiKosu(zissekiKosu);

        //担当者
        PrjTodomemberDao ptmDao = new PrjTodomemberDao(con__);
        List<PrjTodomemberModel> ptmList = ptmDao.getTantoBaseList(paramModel.getTodoSid());
        HashMap<Integer, PrjTodomemberModel> tantoMap =
            new HashMap<Integer, PrjTodomemberModel>();
        String[] tanto = new String[ptmList.size()];

        int index = 0;
        for (PrjTodomemberModel ptmMdl : ptmList) {
            tanto[index] = String.valueOf(ptmMdl.getUsrSid());
            tantoMap.put(ptmMdl.getUsrSid(), ptmMdl);
            index++;
        }

        List<UsrLabelValueBean> tantoUser = new ArrayList<UsrLabelValueBean>();
        if (tanto != null && tanto.length > 0) {
            UserBiz userBiz = new UserBiz();
            tantoUser = userBiz.getUserLabelList(con__, tanto);
        }

        ret.setTantoMap(tantoMap);
        ret.setTanto(tantoUser);
        //重要度
        ret.setPriority(getWeightName(piMdl.getJuyo()));

        //状態
        PrjTodostatusDao ptsDao = new PrjTodostatusDao(con__);
        PrjTodostatusModel ptsMdl = ptsDao.select(paramModel.getPrjSid(), piMdl.getStatus());
        String statusString = "";
        if (ptsMdl != null) {
            statusString = String.valueOf(ptsMdl.getPtsRate());
            statusString = statusString + "%（";
            statusString = statusString + ptsMdl.getPtsName();
            statusString = statusString + "）";
        }
        ret.setStatus(statusString);

        //登録(更新)者
        CmnUsrmInfDao infDao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel infMdl = infDao.select(paramModel.getUsrSid());
        String editUser = "";
        if (infMdl != null) {
            editUser =
                infMdl.getUsiSei()
                + "  "
                + infMdl.getUsiMei();
        }
        ret.setUpdUserName(editUser);

        //内容
        String naiyo = NullDefault.getString(piMdl.getNaiyou(), "");
        ret.setNaiyo(naiyo);

        //状態変更理由
        ret.setHistory(NullDefault.getString(paramModel.getHistory(), ""));

        //添付ファイル
        CommonBiz cmnBiz = new CommonBiz();
        List<LabelValueBean> fileName = new ArrayList<LabelValueBean>();
        if (paramModel.getTempDir() != null) {
            fileName = cmnBiz.getTempFileLabelList(paramModel.getTempDir());
        } else {
            __setTempFile(paramModel.getPrjSid(),
                    paramModel.getTodoSid(),
                    paramModel.getAppRoot(),
                    paramModel.getTempDir());
            fileName = cmnBiz.getTempFileLabelList(paramModel.getTempDir());
        }
        ret.setTmpFiles(fileName);

        //プロジェクトメンバー情報を取得する
        PrjMembersDao pmDao = new PrjMembersDao(con__);
        List<UserModel> userList = pmDao.getMemberList(paramModel.getPrjSid(), false);
        List<Integer> userSids = new ArrayList<Integer>();

        if (!userList.isEmpty()) {

            //送信先 全メンバー
            if (paramModel.getTarget() == GSConstProject.SEND_ALL_MEMBER) {
                for (UserModel mdl : userList) {
                    userSids.add(mdl.getUserSid());
                }
            //担当者
            } else if (paramModel.getTarget() == GSConstProject.SEND_TANTO) {
                for (UserModel mdl : userList) {
                    if (tantoMap.containsKey(mdl.getUserSid())) {
                        userSids.add(mdl.getUserSid());
                    }
                }
            //プロジェクトリーダー
            } else if (paramModel.getTarget() == GSConstProject.SEND_LEADER) {
                for (UserModel mdl : userList) {
                    if (mdl.getAdminKbn() == GSConstProject.KBN_POWER_ADMIN) {
                        userSids.add(mdl.getUserSid());
                    }
                }
            //プロジェクトリーダー + 担当者
            } else if (paramModel.getTarget() == GSConstProject.SEND_LEADER_AND_TANTO) {
                for (UserModel mdl : userList) {
                    if (tantoMap.containsKey(mdl.getUserSid())) {
                        userSids.add(mdl.getUserSid());
                    } else if (mdl.getAdminKbn() == GSConstProject.KBN_POWER_ADMIN) {
                        userSids.add(mdl.getUserSid());
                    }
                }
            }
        }

        //本人はメール送信対象外とする
        if (userSids.contains(paramModel.getUsrSid())) {
            userSids.remove(userSids.indexOf(paramModel.getUsrSid()));
        }

        ret.setUserSids(userSids);

        //TODOのURL作成
        ret.setTodoUrl(createTodoUrl(paramModel.getPrjSid(), paramModel.getTodoSid()));

        return ret;
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @param todoSid TODO_SID
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __setTempFile(int prjSid,
                                 int todoSid,
                                 String appRoot,
                                 String tempDir)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

        //添付ファイル情報を取得する
        PrjTodoBinDao ptbDao = new PrjTodoBinDao(con__);
        String[] binSids = ptbDao.getBinSids(prjSid, todoSid);
        if (binSids == null || binSids.length < 1) {
            return;
        }

        List<CmnBinfModel> cmList = cmnBiz.getBinInfo(con__, binSids,
                reqMdl__.getDomain());

        int fileNum = 1;
        for (CmnBinfModel cbMdl : cmList) {
            if (cbMdl.getBinJkbn() == GSConst.JTKBN_DELETE) {
                continue;
            }

            cmnBiz.saveTempFile(dateStr, cbMdl, appRoot, tempDir, fileNum);

            fileNum++;
        }
    }

    /**
     * <br>[機  能] プロジェクト管理でTODO登録・更新メールを送信する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param model メールパラメータモデル
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @throws Exception 実行例外
     */
    public void sendTodoEditMail(Connection con,
                                  MlCountMtController cntCon,
                                  PrjSmailModel model,
                                  String appRootPath,
                                  PluginConfig pluginConfig)
        throws Exception {

        //登録
        String textEntry = gsMsg__.getMessage("cmn.entry");
        //更新
        String textUpdate = gsMsg__.getMessage("cmn.update");
        //通知
        String textNotification = gsMsg__.getMessage("rng.88");
        //プロジェクト管理
        String textPrj = gsMsg__.getMessage("project.107");
        //登　録
        String textEntry2 = gsMsg__.getMessage("project.124");
        //更　新
        String textUpdate2 = gsMsg__.getMessage("cmn.update2");
        //テンプレートファイルパス
        String tmpPath = getSmlTemplateFilePath(appRootPath);
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);

        Map<String, String> map = new HashMap<String, String>();
        String mode = "";
        String mode2 = "";
        if (model.getCmdMode().equals(GSConstProject.CMD_MODE_ADD)) {
            mode = textEntry;
            mode2 = textEntry2 + " ";
        } else if (model.getCmdMode().equals(GSConstProject.CMD_MODE_EDIT)) {
            mode = textUpdate;
            mode2 = textUpdate2 + " ";
        }
        map.put("MODE", mode);
        map.put("PROJECT", model.getProjectName());
        map.put("KNUMBER", model.getKanriNo());
        map.put("TITLE", model.getTitle());
        map.put("YOTEI", model.getYoteiDate());
        map.put("YOTEI_KOSU", model.getYoteiKosu());
        map.put("ZISSEKI", model.getZissekiDate());
        map.put("ZISSEKI_KOSU", model.getZissekiKosu());

        //担当者
        List<UsrLabelValueBean> tantoLabel = model.getTanto();
        String tantoString = "";
        for (LabelValueBean tanto : tantoLabel) {
            String tantoName = tanto.getLabel();
            if (tantoString.length() != 0) {
                tantoString = tantoString + ", ";
            }
            tantoString = tantoString + tantoName;
        }
        map.put("TANTO", tantoString);

        map.put("PRIORITY", model.getPriority());
        map.put("STATUS", model.getStatus());
        map.put("MODE2", mode2);
        map.put("NAME", model.getUpdUserName());
        map.put("BODY", model.getNaiyo());
        map.put("BODY2", model.getHistory());

        //添付ファイル
        List<LabelValueBean> fileLabel = model.getTmpFiles();
        String fileNameString = "";
        for (LabelValueBean file : fileLabel) {
            String fileName = file.getLabel();
            if (fileNameString.length() != 0) {
                fileNameString = fileNameString + ", ";
            }
            fileNameString = fileNameString + fileName;
        }
        map.put("FILES", fileNameString);
        map.put("URL", model.getTodoUrl());

        String bodyml = StringUtil.merge(tmpBody, map);

        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            bodyml = gsMsg__.getMessage("cmn.mail.omit") + "\r\n\r\n" + bodyml;
            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
        }

        //ショートメール送信用モデルを作成する。
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
        //TO
        smlModel.setSendToUsrSidArray(model.getUserSids());
        //タイトル
        String title =
            "[GS " + textPrj + "] TODO"
            + mode
            + textNotification
            + model.getKanriNo()
            + " "
            + model.getTitle();
        title = StringUtil.trimRengeString(title,
                GSConstCommon.MAX_LENGTH_SMLTITLE);
        smlModel.setSendTitle(title);

        //本文
        smlModel.setSendBody(bodyml);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);

        //メール送信処理開始
        SmlSender sender = new SmlSender(con, cntCon, smlModel, pluginConfig,
                                        appRootPath, reqMdl__);
        sender.execute();
    }

    /**
     * <br>[機  能] プロジェクト管理でTODOコメント登録メールを送信する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param model メールパラメータモデル
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @throws Exception 実行例外
     */
    public void sendTodoCommentMail(Connection con,
                                  MlCountMtController cntCon,
                                  PrjSmailModel model,
                                  String appRootPath,
                                  PluginConfig pluginConfig)
        throws Exception {

        //プロジェクト管理
        String textPrj = gsMsg__.getMessage("project.107");
        //TODOコメント登録通知
        String textTodoComment = gsMsg__.getMessage("project.125");

        //テンプレートファイルパス
        String tmpPath = getTodoCmtSmlTemplateFilePath(appRootPath);
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);

        Map<String, String> map = new HashMap<String, String>();

        map.put("PROJECT", model.getProjectName());
        map.put("KNUMBER", model.getKanriNo());
        map.put("NAME", model.getUpdUserName());
        map.put("BODY", model.getNaiyo());
        map.put("URL", model.getTodoUrl());

        //ショートメール送信用モデルを作成する。
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
        //TO
        smlModel.setSendToUsrSidArray(model.getUserSids());
        //タイトル
        String title =
            "[GS " + textPrj + "] " + textTodoComment
            + model.getKanriNo()
            + " "
            + model.getTitle();
        title = StringUtil.trimRengeString(title,
                GSConstCommon.MAX_LENGTH_SMLTITLE);
        smlModel.setSendTitle(title);

        String bodyml = StringUtil.merge(tmpBody, map);

        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            bodyml = gsMsg__.getMessage("cmn.mail.omit") + "\r\n\r\n" + bodyml;
            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
        }

        //本文
        smlModel.setSendBody(bodyml);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        //メール送信処理開始
        SmlSender sender = new SmlSender(con, cntCon, smlModel, pluginConfig, appRootPath,
                                        reqMdl__);
        sender.execute();
    }

    /**
     * <br>[機  能] テンポラリディレクトリのファイル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリディレクトリパス
     * @return 画面表示用添付ファイル一覧
     * @throws IOToolsException 添付ファイル読込み失敗
     */
    public ArrayList<Cmn110FileModel> getTempFileLabelList(String tempDir)
        throws IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<Cmn110FileModel> fileLblList = new ArrayList<Cmn110FileModel>();

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);

                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

                if (fMdl.getUpdateKbn() > 0) {
                    log__.debug("*** 添付オブジェクト = " + fMdl.getSplitObjName());
                    log__.debug("*** fileName = " + fMdl.getFileName());
                    log__.debug("*** updateKbn = " + fMdl.getUpdateKbn());
                    fileLblList.add(fMdl);
                }
            }
        }

        return fileLblList;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     *
     * <br>[解  説] ファイル本体は保存用ディレクトリにコピー、
     *              ファイル情報はDBに登録する
     * <br>[備  考]
     *
     * @param binMap 既存バイナリマッピング
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録・更新バイナリーSIDのリスト
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public ArrayList<Cmn110FileModel> updateBinInfo(HashMap<Long, Cmn110FileModel> binMap,
                                                     Connection con,
                                                     String tempDir,
                                                     String appRootPath,
                                                     MlCountMtController cntCon,
                                                     int userSid,
                                                     UDate now)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz biz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);

        ArrayList<Cmn110FileModel> binList = new ArrayList<Cmn110FileModel>();

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //テンポラリにファイル無し & 既存バイナリ有り → 既存バイナリ削除
        if (fileList == null && !binMap.isEmpty()) {

            CmnBinfModel binfMdl = new CmnBinfModel();
            binfMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            binfMdl.setBinUpuser(userSid);
            binfMdl.setBinUpdate(now);

            List<Long> binSidList = new ArrayList<Long>();

            List<Cmn110FileModel> delList =
                new ArrayList<Cmn110FileModel>(binMap.values());

            for (Cmn110FileModel mdl : delList) {
                binSidList.add(mdl.getBinSid());
                mdl.setProcKbn(3);
                binList.add(mdl);
            }

            if (!binSidList.isEmpty()) {
                cbDao.updateJKbn(binfMdl, binSidList);
            }

            return binList;
        }

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());

                Long binSid = fMdl.getBinSid();
                log__.debug("binSid = " + binSid);

                //テンポラリファイルのフルパス
                String tempFullPath = tempDir + "/" + fMdl.getSaveFileName();
                tempFullPath = IOTools.replaceFileSep(tempFullPath);

                if (binSid < 1) {

                    log__.debug("ファイル登録");

                    //添付ファイルを登録
                    binSid = biz.insertBinInfo(
                            con, appRootPath,
                            cntCon, userSid, now, tempFullPath, fMdl.getFileName());

                    //バイナリーモデルをリストに追加
                    fMdl.setBinSid(binSid);
                    fMdl.setProcKbn(1);
                    binList.add(fMdl);

                } else {

                    //既存ファイル
                    if (fMdl.getUpdateKbn() == 2) {

                        fMdl.setProcKbn(0);
                        binMap.remove(fMdl.getBinSid());

                    //更新ファイル
                    } else if (fMdl.getUpdateKbn() == 3) {

                        fMdl.setProcKbn(2);
                        binMap.remove(fMdl.getBinSid());

                        //添付ファイルを更新する。
                        biz.updateBinInfo(con, appRootPath, userSid, now,
                                tempFullPath, binSid, fMdl.getFileName(), cntCon);
                    }

                    //バイナリーモデルをリストに追加
                    binList.add(fMdl);
                }
            }

            if (!binMap.isEmpty()) {

                List<Cmn110FileModel> delList =
                    new ArrayList<Cmn110FileModel>(binMap.values());

                for (Cmn110FileModel mdl : delList) {
                    mdl.setProcKbn(3);
                    binList.add(mdl);
                }
            }
        }
        return binList;
    }

    /**
     * <br>[機  能] TODOのURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @return todoUrl
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createTodoUrl(int prjSid, int todoSid)
        throws UnsupportedEncodingException {

        String todoUrl = null;
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();

        try {
            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);

            paramUrl += "/project/prj060" + ".do";
            paramUrl += "?prj060prjSid=" + prjSid;
            paramUrl += "&prj060todoSid=" + todoSid;
            paramUrl += "&prj060scrId=" + GSConstProject.SCR_ID_PRJ030;
            paramUrl += "&prj030prjSid=" + prjSid;

            todoUrl = urlBiz.getAccessUrl(reqMdl__, paramUrl);
        }  catch (URISyntaxException e) {
            return null;
        }

        return todoUrl;
    }

    /**
     * プロジェクト全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value) {
        outPutLog(map, req, res, opCode, level, value, null);
    }

    /**
     * プロジェクト全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルID
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value,
            String fileId) {

        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();

        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstProject.PLUGIN_ID_PROJECT);
        logMdl.setLogPluginName(gsMsg__.getMessage(req, "project.107"));
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), req));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(CommonBiz.getRemoteAddr(req));
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = GroupSession.getResourceManager().getDomain(req);
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @param req リクエスト
     * @return String
     */
    public String getPgName(String id, HttpServletRequest req) {
        String ret = new String();
        if (id == null) {
            return ret;
        }
        log__.info("プログラムID==>" + id);

        if (id.equals("jp.groupsession.v2.prj.prj010.Prj010Action")) {
            //プロジェクトダッシュボード
            return gsMsg__.getMessage(req, "project.prj010.2");
        }
        if (id.equals("jp.groupsession.v2.prj.prj020.Prj020Action")) {
            //プロジェクト登録・編集
            return gsMsg__.getMessage(req, "project.add");
        }
        if (id.equals("jp.groupsession.v2.prj.prj030.Prj030Action")) {
            //プロジェクトメイン
            return gsMsg__.getMessage(req, "project.prj030.1");
        }
        if (id.equals("jp.groupsession.v2.prj.prj040.Prj040Action")) {
            //プロジェクト詳細検索
            return gsMsg__.getMessage(req, "project.prj040.1");
        }
        if (id.equals("jp.groupsession.v2.prj.prj050.Prj050Action")) {
            //TODO登録・編集
            return gsMsg__.getMessage(req, "project.todo.add");
        }
        if (id.equals("jp.groupsession.v2.prj.prj060.Prj060Action")) {
            //TODO参照
            return gsMsg__.getMessage(req, "project.prj060.1");
        }
        if (id.equals("jp.groupsession.v2.prj.prj070.Prj070Action")) {
            //TODO詳細検索
            return gsMsg__.getMessage(req, "project.123");
        }
        if (id.equals("jp.groupsession.v2.prj.prj090.Prj090Action")) {
            //個人設定 表示件数設定
            return gsMsg__.getMessage(req, "cmn.display.settings");
        }
        if (id.equals("jp.groupsession.v2.prj.prj110kn.Prj110knAction")) {
            //管理者設定 登録権限設定確認
            return gsMsg__.getMessage(req, "project.add.permit.set.kn");
        }
        if (id.equals("jp.groupsession.v2.prj.prj140.Prj140Action")) {
            //テンプレート登録・編集
            return gsMsg__.getMessage(req, "project.add.temp");
        }
        if (id.equals("jp.groupsession.v2.prj.prj150.Prj150Action")) {
            //プロジェクトメンバー設定
            return gsMsg__.getMessage(req, "project.prj150.8");
        }
        if (id.equals("jp.groupsession.v2.prj.prj160.Prj160Action")) {
            //TODOインポート
            return gsMsg__.getMessage(req, "project.prj160.1");
        }
        if (id.equals("jp.groupsession.v2.prj.prj160kn.Prj160knAction")) {
            //TODOインポート確認
            return gsMsg__.getMessage(req, "project.prj160.7");
        }
        if (id.equals("jp.groupsession.v2.prj.prj170.Prj170Action")) {
            //ディレクトリ情報
            return gsMsg__.getMessage(req, "project.prj170.2");
        }
        if (id.equals("jp.groupsession.v2.prj.prj180.Prj180Action")) {
            //フォルダ・ファイル作成
            return gsMsg__.getMessage(req, "project.file.add");
        }
        if (id.equals("jp.groupsession.v2.prj.prj180kn.Prj180knAction")) {
            //フォルダ・ファイル作成確認
            return gsMsg__.getMessage(req, "project.file.add.kn");
        }
        if (id.equals("jp.groupsession.v2.prj.prj190kn.Prj190knAction")) {
            //個人設定 ダッシュボード初期値設定確認
            return String.format("%s %s%s",
                    gsMsg__.getMessage("cmn.project"),
                    gsMsg__.getMessage("cmn.default.setting"),
                    gsMsg__.getMessage("cmn.check"));
        }
        if (id.equals("jp.groupsession.v2.prj.ptl020.PrjPtl020Action")) {
            //ポータル プラグインポートレット
            return gsMsg__.getMessage(req, "ptl.ptl040.1");
        }

        return ret;
    }

    /**
     * <br>[機  能] TODOの開始予定日、終了予定日を変更する
     * <br>[解  説] TODOの状態だけではなく変更履歴情報も登録する
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @param frDate 開始予定日付
     * @param fnDate 終了予定日付
     * @param riyu 変更理由
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeTodoDate(int prjSid, int todoSid, UDate frDate, UDate fnDate, String riyu,
                        MlCountMtController cntCon, int userSid)
    throws SQLException {

        UDate now = new UDate();

        //変更履歴SIDを採番
        int hisSid = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                      todoSid + GSConstProject.SBNSID_SUB_HISTORY,
                      userSid);

        PrjStatusHistoryModel pshMdl = new PrjStatusHistoryModel();
        pshMdl.setPrjSid(prjSid);
        pshMdl.setPtdSid(todoSid);
        pshMdl.setPshSid(hisSid);
        pshMdl.setPshReason(riyu);
        pshMdl.setPshAuid(userSid);
        pshMdl.setPshAdate(now);
        pshMdl.setPshEuid(userSid);
        pshMdl.setPshEdate(now);

        //状態変更履歴を登録する
        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(con__);
        pshDao.insert(pshMdl);

        PrjTododataModel ptdMdl = new PrjTododataModel();
        ptdMdl.setPshSid(hisSid);
        ptdMdl.setPtdDatePlan(frDate);
        ptdMdl.setPrjDateLimit(fnDate);
        ptdMdl.setPtdEuid(userSid);
        ptdMdl.setPtdEdate(now);
        ptdMdl.setPrjSid(prjSid);
        ptdMdl.setPtdSid(todoSid);

        //TODO情報の状態を更新する
        PrjTododataDao ptdDao = new PrjTododataDao(con__);
        ptdDao.updateDate(ptdMdl);
    }

    /**
     * <br>[機  能] TODOの状態を変更する
     * <br>[解  説] TODOの状態だけではなく変更履歴情報も登録する
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @param status 状態
     * @param riyu 変更理由
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void changeTodoStatus(int prjSid, int todoSid, int status, String riyu,
                        MlCountMtController cntCon, int userSid)
    throws SQLException {

        UDate now = new UDate();

        //変更履歴SIDを採番
        int hisSid = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                      todoSid + GSConstProject.SBNSID_SUB_HISTORY,
                      userSid);

        PrjStatusHistoryModel pshMdl = new PrjStatusHistoryModel();
        pshMdl.setPrjSid(prjSid);
        pshMdl.setPtdSid(todoSid);
        pshMdl.setPshSid(hisSid);
        pshMdl.setPtsSid(status);
        pshMdl.setPshReason(riyu);
        pshMdl.setPshAuid(userSid);
        pshMdl.setPshAdate(now);
        pshMdl.setPshEuid(userSid);
        pshMdl.setPshEdate(now);

        //状態変更履歴を登録する
        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(con__);
        pshDao.insert(pshMdl);

        PrjTododataModel ptdMdl = new PrjTododataModel();
        ptdMdl.setPshSid(hisSid);
        ptdMdl.setPtsSid(status);
        ptdMdl.setPtdEuid(userSid);
        ptdMdl.setPtdEdate(now);
        ptdMdl.setPrjSid(prjSid);
        ptdMdl.setPtdSid(todoSid);

        //TODO情報の状態を更新する
        PrjTododataDao ptdDao = new PrjTododataDao(con__);
        ptdDao.updateStatus(ptdMdl);
    }


    /**
     * <br>[機  能] メッセージに表示するTODOのタイトルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param todoSidList TODOSID
     * @return メッセージ表示用のTODOタイトル
     * @throws SQLException SQL実行時例外
     */
    public String getMsgTodoTitle(Connection con, String[] todoSidList)
    throws SQLException {

        PrjTododataDao todoDao = new PrjTododataDao(con);
        List<String> titleList = todoDao.getTodoTitleList(todoSidList);

        String msgTitle = "";
        for (int idx = 0; idx < titleList.size(); idx++) {

            //最初の要素以外は改行を挿入
            if (idx > 0) {
                msgTitle += "<br>";
            }

            msgTitle += "・ " + StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(titleList.get(idx), ""));
        }

        return msgTitle;
    }

    /**
     * <br>[機  能] TODOに紐付くデータがあるかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSidList TODOSID
     * @return boolean true=TODO情報あり、false=TODO情報無し
     * @throws SQLException SQL実行例外
     */
    public boolean checkDataExist(String[] todoSidList) throws SQLException {

        //TODO変更履歴の件数をカウントする
        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(con__);
        int count = pshDao.getHisCount(todoSidList);

        if (count > 0) {
            return true;
        }

        //TODOコメントの件数をカウントする
        PrjTodocommentDao ptcDao = new PrjTodocommentDao(con__);
        count = ptcDao.getCmtCount(todoSidList);

        if (count > 0) {
            return true;
        }

        return false;
    }
    /**
     * <br>[機  能] プロジェクト個人設定PrjPriConfModelを取得します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return スケジュール個人設定SchPriConfModel
     * @throws SQLException SQL実行エラー
     */
    public PrjUserConfModel getPrjUserConfModel(Connection con, int usrSid) throws SQLException {
        //
        PrjUserConfDao dao = new PrjUserConfDao(con);
        PrjUserConfModel pconf = dao.select(usrSid);
        boolean commitFlg = false;
        if (pconf == null) {
            con.setAutoCommit(false);
            pconf = getDefaulPriConfModel(usrSid);
            try {
                dao.insert(pconf);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("個人設定の取得に失敗しました。" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
        return pconf;
    }
    /**
     * <br>[機  能] プロジェクト個人設定のデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param usrSid ユーザSID
     * @return スケジュール個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public PrjUserConfModel getDefaulPriConfModel(int usrSid) throws SQLException {
        PrjUserConfModel confBean = new PrjUserConfModel();
        confBean.setUsrSid(usrSid);
        confBean.setPucPrjCnt(10);
        confBean.setPucTodoCnt(10);
        confBean.setPucAuid(usrSid);
        confBean.setPucAdate(new UDate());
        confBean.setPucEuid(usrSid);
        confBean.setPucEdate(confBean.getPucAdate());
        confBean.setPucPrjProject(GSConstProject.PRJ_MEMBER_NOT_END);
        confBean.setPucMainDate(GSConstProject.DATE_THE_PAST);
        confBean.setPucMainStatus(GSConstProject.STATUS_YOTEI_AND_MIKAN);
        confBean.setPucMainMember(GSConstProject.MEMBER_ALL);
        confBean.setPucDefDsp(GSConstProject.DSP_TODO);
        return confBean;
    }

    /**
     * <br>[機  能] TODOの添付ファイルをダウンロード可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param prjSid プロジェクトSID
     * @param ptdSid TODOSID
     * @param binSid バイナリSID
     * @param usrSid ユーザSID
     * @param adminFlg 管理者フラグ
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLPrjTemp(
            Connection con, int prjSid, int ptdSid, Long binSid, int usrSid, boolean adminFlg)
                    throws SQLException {
        PrjTodoBinDao binDao = new PrjTodoBinDao(con);
        //プロジェクトにアクセス権限がある 且つ バイナリSIDはTODOの添付ファイルのものである。
        if (isAcsessPrj(con, prjSid, usrSid, adminFlg)
                && binDao.isCheckPrjTemp(prjSid, ptdSid, binSid)) {
            return true;
        }
        return false;
    }


    /**
     * <br>[機  能] プロジェクトへアクセス権限があるかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param prjSid プロジェクトSID
     * @param usrSid ユーザSID
     * @param adminFlg 管理者フラグ
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isAcsessPrj(Connection con, int prjSid, int usrSid, boolean adminFlg)
            throws SQLException {

        PrjPrjdataDao prjDao = new PrjPrjdataDao(con);
        //通常プロジェクトかマイプロジェクトか判定する
        int prjUsrSid = prjDao.getEditMyProjectUsrSid(prjSid);

        //システム・プラグイン管理者は許可
        if (adminFlg && prjUsrSid == -1) {
            return true;
        }

        //通常プロジェクト
        if (prjUsrSid == -1) {
            PrjPrjdataModel prjData = prjDao.getProjectData(prjSid);
            if (prjData != null) {
                //プロジェクトが公開
                if (prjData.getPrjKoukaiKbn() == GSConstProject.KBN_KOUKAI_ENABLED) {
                    return true;

                //プロジェクトが非公開
                } else {
                    //プロジェクトのメンバー
                    PrjMembersDao memDao = new PrjMembersDao(con);
                    if (memDao.isCheckPrjMember(prjSid, usrSid)) {
                        return true;
                    }
                }
            }

        //マイプロジェクト
        } else {
            //自分のプロジェクト
            if (prjUsrSid == usrSid) {
                return true;
            }
        }

        return false;
    }

    /**
     * <br>[機  能] オブジェクトファイルから情報を取得できるか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tmpMode
     * @param reqMdl リクエストモデル
     * @return true:取得成功  false:取得失敗
     * @throws IOToolsException
     */
    public static boolean existStatusFile(int tmpMode, RequestModel reqMdl)
            throws IOToolsException {
        if (tmpMode > GSConstProject.MODE_TMP_OTHER) {
            ProjectStatusTmpModel statusTmpModel = getProjectStatusTmpModel(
                    reqMdl, GSConstProject.SCR_ID_PRJ140);
            if (statusTmpModel == null) {
                return false;
            }
        } else {
            ProjectStatusModel statusModel = getProjectStatusModel(
                    reqMdl, GSConstProject.SCR_ID_PRJ020);
            if (statusModel == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 編集対象への権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param prtSid テンプレートSID
     * @param prtKbn テンプレート区分
     * @param umodel セッションユーザ情報
     * @return true=権限あり、false=権限なし
     * @throws SQLException SQL実行例外
     */
    public boolean validateCheckPower(Connection con, int prtSid, int prtKbn, BaseUserModel umodel)
            throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(
                con, umodel, GSConstProject.PLUGIN_ID_PROJECT);

        if (prtKbn == GSConstProject.PRT_KBN_KYOYU && !adminFlg) {
            return false;
        }
        PrjPrjdataTmpModel prjTmpMdl = new PrjPrjdataTmpModel();
        PrjPrjdataTmpDao prjTmpDao = new PrjPrjdataTmpDao(con);
        prjTmpMdl = prjTmpDao.getPrjTmpMdl(prtSid);

        if (prjTmpMdl == null) {
            return false;
        }
        if (prtKbn == GSConstProject.PRT_KBN_KOJIN
                && prjTmpMdl.getPrtUsrSid() != umodel.getUsrsid()) {
            return false;
        }
        if (prtKbn == GSConstProject.PRT_KBN_KYOYU
                && prjTmpMdl.getPrtKbn() != prtKbn) {
            return false;
        }
        if (prtKbn == GSConstProject.MODE_TMP_SELECT
                && prjTmpMdl.getPrtUsrSid() != umodel.getUsrsid()
                && prjTmpMdl.getPrtKbn() == GSConstProject.PRT_KBN_KOJIN) {
            return false;
        }
        return true;
    }
}
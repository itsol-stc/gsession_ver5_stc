package jp.groupsession.v2.prj.prj070;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] TODO詳細検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj070Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Prj070Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj070ParamModel
     * @param buMdl セッションユーザModel
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors setInitData(
            Prj070ParamModel paramMdl, BaseUserModel buMdl)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //プロジェクトコンボをセットする
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        paramMdl.setProjectLabel(pcBiz.getProjectSearchLabel(buMdl));

        //プロジェクトコンボの選択値 != 「全て」の場合
        //TODOカテゴリコンボを選択する
        if (paramMdl.getPrj070scPrjSid() != GSConstCommon.NUM_INIT) {
            paramMdl.setCategoryLabel(pcBiz.getTodoCategoryLabel(paramMdl.getPrj070scPrjSid()));
        }

        //担当メンバーの名称を取得
        UserBiz userBiz = new UserBiz();
        paramMdl.setMemberList(userBiz.getUserList(con__, paramMdl.getPrj070scTantou()));

        //登録者の名称を取得
        paramMdl.setAddUserList(userBiz.getUserList(con__, paramMdl.getPrj070scTourokusya()));

        DateTimePickerBiz picker = new DateTimePickerBiz();
        //開始From
        String textStartFrom = gsMsg.getMessage("project.src.36");
        //開始To
        String textStartTo = gsMsg.getMessage("project.src.38");
        //終了From
        String textEndForm = gsMsg.getMessage("project.src.19");
        //終了To
        String textEndTo = gsMsg.getMessage("project.src.21");

        //年月日の値をセット
        picker.setDateParam(paramMdl, "prj070strPlanDate",
                "prj070scKaisiYoteiYear", "prj070scKaisiYoteiMonth",
                "prj070scKaisiYoteiDay", textStartFrom);
        picker.setDateParam(paramMdl, "prj070endPlanDate",
                "prj070scKigenYear", "prj070scKigenMonth",
                "prj070scKigenDay", textStartTo);
        picker.setDateParam(paramMdl, "prj070strResultDater",
                "prj070scKaisiJissekiYear", "prj070scKaisiJissekiMonth",
                "prj070scKaisiJissekiDay", textEndForm);
        picker.setDateParam(paramMdl, "prj070endResultDate",
                "prj070scSyuryoJissekiYear", "prj070scSyuryoJissekiMonth",
                "prj070scSyuryoJissekiDay", textEndTo);

        //検索フラグが立っている場合のみ検索を行う
        if (paramMdl.getPrj070searchFlg() != GSConstProject.SEARCH_FLG_OK) {
            return new ActionErrors();
        }

        //プロジェクトリストを取得する(検索を行う)
        return __getProjectList(paramMdl, buMdl);
    }

    /**
     * <br>[機  能] プロジェクトリストを取得する(検索を行う)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj070ParamModel
     * @param buMdl セッションユーザModel
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    private ActionErrors __getProjectList(Prj070ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //ログインユーザSID
        int userSid = buMdl.getUsrsid();

        //プロジェクト個人設定から最大表示件数を取得する
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        int limit = pcBiz.getCountLimit(userSid, GSConstProject.MODE_TODO);

        //検索用モデルを設定する
        ProjectSearchModel bean = __getSearchModel(paramMdl, buMdl);
        bean.setLimit(limit);

        //件数カウント
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
        long maxCount = psDao.getTodoCount(bean);
        log__.debug("件数 = " + maxCount);

        int nowPage = paramMdl.getPrj070page1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setPrj070page1(nowPage);
        paramMdl.setPrj070page2(nowPage);
        paramMdl.setPageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            ActionMessage msg = new ActionMessage("search.data.notfound", GSConstProject.MSG_TODO);
            StrutsUtil.addMessage(errors, msg, "search.data.notfound");
            return errors;
        }

        List<ProjectItemModel> prjList = psDao.getTodoList(bean);

        for (ProjectItemModel piMdl : prjList) {
            piMdl.setStrKanriNo(
                    StringUtil.toDecFormat(piMdl.getKanriNo(), GSConstProject.KANRI_NO_FORMAT));
            piMdl.setStrJuyo(pcBiz.getWeightName(piMdl.getJuyo()));
            piMdl.setStrStartDate(UDateUtil.getSlashYYMD(piMdl.getStartDate()));
            piMdl.setStrEndDate(UDateUtil.getSlashYYMD(piMdl.getEndDate()));
        }
        paramMdl.setProjectList(prjList);

        return errors;
    }

    /**
     * <br>[機  能] 検索用モデルを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj070ParamModel
     * @param buMdl セッションユーザModel
     * @return ProjectSearchModel
     * @throws SQLException SQL実行例外
     */
    private ProjectSearchModel __getSearchModel(Prj070ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException {

        //ログインユーザSID
        int userSid = buMdl.getUsrsid();

        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(userSid);
        bean.setOrder(paramMdl.getPrj070order());
        bean.setSort(paramMdl.getPrj070sort());
        //完了プロジェクト表示フラグ true=表示
        bean.setEndPrjFlg(true);

        //管理者権限がある場合は全て、ない場合は公開プロジェクトのみ取得
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);
        int getKbn = ProjectSearchModel.GET_OPEN;
        if (adminUser) {
            getKbn = ProjectSearchModel.GET_ALL;
        }
        bean.setGetKbn(getKbn);

        //選択プロジェクトが「全て」の場合、参加プロジェクトのみ表示フラグをセット
        if (paramMdl.getPrj070scPrjSid() == GSConstProject.PROJECT_ALL_SELECT) {
            bean.setJoinOnlyFlg(paramMdl.getPrj070JoinOnlyFlg());
        }

        //プロジェクトSID
        bean.setProjectSid(paramMdl.getPrj070svScPrjSid());
        //カテゴリSID
        bean.setSelectingCategory(paramMdl.getPrj070svScCategorySid());
        //担当メンバー
        bean.setMemberSid(paramMdl.getPrj070svScTantou());
        //重要度
        bean.setJuyo(paramMdl.getPrj070svScJuuyou());
        //状態From
        bean.setStatusFrom(NullDefault.getInt(paramMdl.getPrj070svScStatusFrom(), -1));
        //状態To
        bean.setStatusTo(NullDefault.getInt(paramMdl.getPrj070svScStatusTo(), -1));
        //開始予定
        bean.setStartFrom(PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj070svScKaisiYoteiYear(), -1),
                NullDefault.getInt(paramMdl.getPrj070svScKaisiYoteiMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj070svScKaisiYoteiDay(), -1)));
        //期限
        bean.setStartTo(PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj070svScKigenYear(), -1),
                NullDefault.getInt(paramMdl.getPrj070svScKigenMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj070svScKigenDay(), -1)));
        //開始実績
        bean.setEndFrom(PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj070svScKaisiJissekiYear(), -1),
                NullDefault.getInt(paramMdl.getPrj070svScKaisiJissekiMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj070svScKaisiJissekiDay(), -1)));
        //終了実績
        bean.setEndTo(PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj070svScSyuryoJissekiYear(), -1),
                NullDefault.getInt(paramMdl.getPrj070svScSyuryoJissekiMonth(), -1),
                NullDefault.getInt(paramMdl.getPrj070svScSyuryoJissekiDay(), -1)));
        //タイトル
        bean.setTodoTitle(paramMdl.getPrj070svScTitle());
        CommonBiz cBiz = new CommonBiz();
        String keyWord = NullDefault.getString(bean.getTodoTitle(), "");
        bean.setPrjKeyValue(cBiz.setKeyword(keyWord));

        //タイトル（キーワード区分）
        bean.setKeyWordkbn(Integer.parseInt(paramMdl.getPrj070SvKeyWordkbn()));

        //検索対象
        String[] targets = paramMdl.getPrj070SvSearchTarget();
        boolean targetTitle = false;
        boolean targetBody = false;
        if (targets != null && targets.length > 0) {
            for (String target : targets) {
                if (String.valueOf(GSConstProject.SEARCH_TARGET_TITLE).equals(target)) {
                    targetTitle = true;
                }
                if (String.valueOf(GSConstProject.SEARCH_TARGET_NAIYOU).equals(target)) {
                    targetBody = true;
                }
            }
        }
        bean.setTargetTitle(targetTitle);
        bean.setTargetValue(targetBody);
        bean.setKanriNumber(NullDefault.getInt(paramMdl.getPrj070SvKanriNumber(), -1));

        //登録者
        bean.setAddUserSid(paramMdl.getPrj070svScTourokusya());
        return bean;
    }

    /**
     * <br>[機  能] プロジェクト一覧のCSV出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj070ParamModel
     * @param buMdl セッションユーザModel
     * @param outDir 出力先ディレクトリ
     * @throws CSVException CSV出力時例外
     * @throws SQLException SQL実行例外
     */
    public void export(Prj070ParamModel paramMdl, BaseUserModel buMdl, String outDir)
    throws SQLException, CSVException {

        //検索用モデルを設定する
        ProjectSearchModel bean = __getSearchModel(paramMdl, buMdl);

        //CSVファイルを作成
        Prj070CsvWriter write = new Prj070CsvWriter(reqMdl__);
        write.setSearchModel(bean);
        write.outputCsv(con__, outDir);

    }

    /**
     * <br>[機  能] 検索対象がNULLの場合、検索対象のデフォルト値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj070ParamModel
     */
    public void setDefultSearchTarget(Prj070ParamModel paramMdl) {
        //検索対象
        if (paramMdl.getPrj070SearchTarget() == null) {
            paramMdl.setPrj070SearchTarget(getDefultSearchTarget());
        }
    }

    /**
     * <br>[機  能] 検索対象のデフォルト値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトターゲット配列
     */
    public static String[] getDefultSearchTarget() {
        String[] targets = {
                String.valueOf(GSConstProject.SEARCH_TARGET_TITLE),
                String.valueOf(GSConstProject.SEARCH_TARGET_NAIYOU)
              };
        return targets;
    }
}

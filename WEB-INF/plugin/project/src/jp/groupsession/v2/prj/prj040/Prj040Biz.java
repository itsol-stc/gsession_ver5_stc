
package jp.groupsession.v2.prj.prj040;

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
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
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
 * <br>[機  能] プロジェクト詳細検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj040Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Prj040Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj040ParamModel
     * @param buMdl セッションユーザModel
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors setInitData(Prj040ParamModel paramMdl, BaseUserModel buMdl)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //プロジェクト登録権限があるかチェックを行う
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        paramMdl.setPrjAdd(pcBiz.getPrjAddKengen(buMdl));

        //メンバーの名称を取得
        UserBiz userBiz = new UserBiz();
        paramMdl.setMemberList(userBiz.getUserList(con__, paramMdl.getPrj040scMemberSid()));

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
        picker.setDateParam(paramMdl, "prj040StartDateFr",
                "prj040scStartYearFrom", "prj040scStartMonthFrom",
                "prj040scStartDayFrom", textStartFrom);
        picker.setDateParam(paramMdl, "prj040StartDateTo",
                "prj040scStartYearTo", "prj040scStartMonthTo",
                "prj040scStartDayTo", textStartTo);
        picker.setDateParam(paramMdl, "prj040EndDateFr",
                "prj040scEndYearFrom", "prj040scEndMonthFrom",
                "prj040scEndDayFrom", textEndForm);
        picker.setDateParam(paramMdl, "prj040EndDateTo",
                "prj040scEndYearTo", "prj040scEndMonthTo",
                "prj040scEndDayTo", textEndTo);

        //検索フラグが立っている場合のみ検索を行う
        if (paramMdl.getPrj040searchFlg() != GSConstProject.SEARCH_FLG_OK) {
            return new ActionErrors();
        }

        //プロジェクトリストを取得する(検索を行う)
        return __getProjectList(paramMdl, buMdl);
    }

    /**
     * <br>[機  能] プロジェクトリストを取得する(検索を行う)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj040ParamModel
     * @param buMdl セッションユーザModel
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    private ActionErrors __getProjectList(Prj040ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();

        //ログインユーザSID
        int userSid = buMdl.getUsrsid();

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //プロジェクト個人設定から最大表示件数を取得する
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        int limit = pcBiz.getCountLimit(userSid, GSConstProject.MODE_PROJECT);

        //検索用モデルを設定する
        ProjectSearchModel bean = __getSearchModel(paramMdl, buMdl);
        bean.setLimit(limit);

        //件数カウント
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
        long maxCount = psDao.getProjectCount(bean);
        log__.debug("件数 = " + maxCount);

        int nowPage = paramMdl.getPrj040page1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setPrj040page1(nowPage);
        paramMdl.setPrj040page2(nowPage);
        paramMdl.setPageLabel(PageUtil.createPageOptions(maxCount, limit));

        //プロジェクト
        String textProject = gsMsg.getMessage("cmn.project");
        if (maxCount < 1) {
            ActionMessage msg =
                new ActionMessage("search.data.notfound", textProject);
            StrutsUtil.addMessage(errors, msg, "search.data.notfound");
            return errors;
        }

        List<ProjectItemModel> prjList = psDao.getProjectList(bean);

        for (ProjectItemModel piMdl : prjList) {
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
     * @param paramMdl Prj040ParamModel
     * @param buMdl セッションユーザModel
     * @return ProjectSearchModel
     * @throws SQLException SQL実行例外
     */
    private ProjectSearchModel __getSearchModel(Prj040ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException {

        //ログインユーザSID
        int userSid = buMdl.getUsrsid();

        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(userSid);
        bean.setOrder(paramMdl.getPrj040order());
        bean.setSort(paramMdl.getPrj040sort());
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

        //プロジェクトID
        bean.setPrjId(paramMdl.getPrj040svScPrjId());
        //状態From
        bean.setStatusFrom(NullDefault.getInt(paramMdl.getPrj040svScStatusFrom(), -1));
        //状態To
        bean.setStatusTo(NullDefault.getInt(paramMdl.getPrj040svScStatusTo(), -1));
        //プロジェクト名
        bean.setPrjName(paramMdl.getPrj040svScPrjName());
        //メンバーSID
        bean.setMemberSid(paramMdl.getPrj040svScMemberSid());
        //予算From
        bean.setYosanFrom(NullDefault.getLong(paramMdl.getPrj040svScYosanFr(), -1));
        //予算From
        bean.setYosanTo(NullDefault.getLong(paramMdl.getPrj040svScYosanTo(), -1));

        //開始From
        bean.setStartFrom(PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj040svScStartYearFrom(), -1),
                NullDefault.getInt(paramMdl.getPrj040svScStartMonthFrom(), -1),
                NullDefault.getInt(paramMdl.getPrj040svScStartDayFrom(), -1)));
        //開始To
        bean.setStartTo(PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj040svScStartYearTo(), -1),
                NullDefault.getInt(paramMdl.getPrj040svScStartMonthTo(), -1),
                NullDefault.getInt(paramMdl.getPrj040svScStartDayTo(), -1)));
        //終了From
        bean.setEndFrom(PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj040svScEndYearFrom(), -1),
                NullDefault.getInt(paramMdl.getPrj040svScEndMonthFrom(), -1),
                NullDefault.getInt(paramMdl.getPrj040svScEndDayFrom(), -1)));
        //終了To
        bean.setEndTo(PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj040svScEndYearTo(), -1),
                NullDefault.getInt(paramMdl.getPrj040svScEndMonthTo(), -1),
                NullDefault.getInt(paramMdl.getPrj040svScEndDayTo(), -1)));

        return bean;
    }

    /**
     * <br>[機  能] プロジェクト一覧のCSV出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj040ParamModel
     * @param buMdl セッションユーザModel
     * @param outDir 出力先ディレクトリ
     * @throws CSVException CSV出力時例外
     * @throws SQLException SQL実行例外
     */
    public void export(Prj040ParamModel paramMdl, BaseUserModel buMdl, String outDir)
    throws SQLException, CSVException {

        //検索用モデルを設定する
        ProjectSearchModel bean = __getSearchModel(paramMdl, buMdl);

        //CSVファイルを作成
        Prj040CsvWriter write = new Prj040CsvWriter(reqMdl__);
        write.setSearchModel(bean);
        write.outputCsv(con__, outDir);

    }
}

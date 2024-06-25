package jp.groupsession.v2.prj.prj030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjAddressDao;
import jp.groupsession.v2.prj.dao.PrjMembersDao;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.dao.PrjTodomemberDao;
import jp.groupsession.v2.prj.dao.PrjUserConfDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjAddressModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjUserConfModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.prj.model.TodoTantoModel;
import jp.groupsession.v2.prj.prj150.Prj150Dao;
import jp.groupsession.v2.prj.prj150.model.Prj150DspMdl;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクトメイン画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj030Biz {

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj030Biz.class);

    /**
     * <p>Set Connection
     * @param reqMdl RequestModel
     */
    public Prj030Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Prj030Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] ショートメール・回覧板プラグインが使用可能か判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj030ParamModel
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行例外
     */
    public void setUsePlugin(Prj030ParamModel paramMdl, PluginConfig pconfig) throws SQLException {

        //ショートメール使用有無
        paramMdl.setUseSmail(pconfig.getPlugin(GSConstProject.PLUGIN_ID_SMAIL) != null);

        //回覧板使用有無
        paramMdl.setUseCircular(pconfig.getPlugin(GSConstProject.PLUGIN_ID_CIRCULAR) != null);

        //アドレス帳使用有無
        paramMdl.setUseAddress(pconfig.getPlugin(GSConstProject.PLUGIN_ID_ADDRESS) != null);

        //在席管理使用有無
        paramMdl.setUseZaiseki(pconfig.getPlugin(
                                           GSConstProject.PLUGIN_ID_ZAISEKI) != null);
    }

    /**
     * <br>[機  能] 初期処理情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj030ParamModel
     * @param buMdl セッションユーザModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Prj030ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException {

        if (paramMdl.isPrj030Init()) {

            paramMdl.setPrj030Init(false);

            PrjUserConfDao confDao = new PrjUserConfDao(con__);
            PrjUserConfModel confMdl = confDao.select(buMdl.getUsrsid());

            if (confMdl != null) {
                paramMdl.setSelectingDate(String.valueOf(confMdl.getPucMainDate()));
                paramMdl.setSelectingStatus(String.valueOf(confMdl.getPucMainStatus()));
                paramMdl.setSelectingMember(String.valueOf(confMdl.getPucMainMember()));
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //ログインユーザSID
        int userSid = buMdl.getUsrsid();
        //プロジェクトSID
        int projectSid = paramMdl.getPrj030prjSid();
        paramMdl.setPrj010PrjSid(projectSid);

        //プロジェクト編集権限があるかチェックを行う
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        paramMdl.setPrjEdit(pcBiz.getPrjEditKengen(projectSid, buMdl));

        //検索条件 日付コンボ選択値設定
        paramMdl.setSelectingDate(
                NullDefault.getStringZeroLength(
                        paramMdl.getSelectingDate(),
                        String.valueOf(GSConstProject.DATE_THE_PAST)));

        //検索条件 カテゴリコンボ
        paramMdl.setSelectingCategory(
                NullDefault.getStringZeroLength(
                        paramMdl.getSelectingCategory(),
                        String.valueOf(GSConstProject.TODO_CATEGORY_ALL)));

        //検索条件 状態コンボ選択値設定
        paramMdl.setSelectingStatus(
                NullDefault.getStringZeroLength(
                        paramMdl.getSelectingStatus(),
                        String.valueOf(GSConstProject.STATUS_YOTEI_AND_MIKAN)));

        //検索条件 メンバコンボ選択値設定
        if (StringUtil.isNullZeroString(paramMdl.getSelectingMember())) {

            //プロジェクト管理者、またはシステム管理者か
            PrjMembersDao prmDao = new PrjMembersDao(con__);
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser =
                cmnBiz.isPluginAdmin(con__, buMdl, GSConstProject.PLUGIN_ID_PROJECT);

            if (adminUser || prmDao.getMemberCount(projectSid, buMdl.getUsrsid(), true) > 0) {
                //全員
                paramMdl.setSelectingMember(String.valueOf(GSConstProject.MEMBER_ALL));
            } else {
                //自分
                paramMdl.setSelectingMember(String.valueOf(buMdl.getUsrsid()));
            }
        }

        //検索条件 日付コンボ設定
        paramMdl.setTargetDateLabel(pcBiz.getTargetDateLabel());
        //検索条件 状態コンボ設定
        paramMdl.setTargetStatusLabel(pcBiz.getStatusLabel(projectSid));
        //検索条件 カテゴリコンボ
        paramMdl.setTargetCategoryLabel(pcBiz.getTodoCategoryLabel(projectSid));
        //検索条件 メンバコンボ設定
        paramMdl.setTargetMemberLabel(pcBiz.getMemberLabel(projectSid, buMdl.getUsrsid()));

        //状態コンボ(状態変更用)設定
        paramMdl.setEditStatusLabel(pcBiz.getStatusLabel(projectSid, false));

        //プロジェクト個人設定から最大表示件数を取得する
        int limit = pcBiz.getCountLimit(userSid, GSConstProject.MODE_TODO);
        PrjCommonBiz prjCmnBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        //TODO情報を取得
        List<ProjectItemModel> prjList = __getTodoList(paramMdl, limit, userSid);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        for (ProjectItemModel piMdl : prjList) {
            piMdl.setStrKanriNo(
                    StringUtil.toDecFormat(piMdl.getKanriNo(), GSConstProject.KANRI_NO_FORMAT));
            piMdl.setStrJuyo(prjCmnBiz.getWeightName(piMdl.getJuyo()));
            piMdl.setStrStartDate(UDateUtil.getSlashYYMD(piMdl.getStartDate()));
            piMdl.setStrEndDate(UDateUtil.getSlashYYMD(piMdl.getEndDate()));
            piMdl.setPrjTodoEdit(prjBiz.getTodoEditKengen(piMdl.getProjectSid(), buMdl));
        }
        paramMdl.setProjectList(prjList);

        //プロジェクト情報を取得
        PrjPrjdataDao ppDao = new PrjPrjdataDao(con__);
        ProjectItemModel ppMdl = ppDao.getProjectInfo(projectSid);
        ppMdl.setStrYosan(prjCmnBiz.getYosanStr(ppMdl.getYosan()));
        ppMdl.setStrStartDate(UDateUtil.getSlashYYMD(ppMdl.getStartDate()));
        ppMdl.setStrEndDate(UDateUtil.getSlashYYMD(ppMdl.getEndDate()));
        ppMdl.setMokuhyou(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(ppMdl.getMokuhyou()), ""));
        ppMdl.setNaiyou(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(ppMdl.getNaiyou()), ""));
        int notEndRate = 100 - ppMdl.getRate();
        ppMdl.setNotEndRate(notEndRate);

        paramMdl.setPrj010PrjBinSid(ppMdl.getPrjBinSid());
        paramMdl.setProjectItem(ppMdl);

        //プロジェクトメンバー情報を取得する
        if (ppMdl.getPrjMyKbn() == GSConstProject.KBN_MY_PRJ_DEF) {
            PrjMembersDao pmDao = new PrjMembersDao(con__);
            paramMdl.setUserList(pmDao.getMemberList(projectSid, false));
        }
        paramMdl.setPrjTodoEdit(pcBiz.getTodoEditKengen(projectSid, buMdl));

        //選択ディレクトリ
        paramMdl.setSelectDir(
                NullDefault.getStringZeroLength(
                        paramMdl.getSelectDir(), GSConstProject.DIRECTORY_NOT_SELECT));

        //外部メンバー取得
        __getOutMember(userSid, paramMdl);
    }

    /**
     * <br>[機  能] 遷移元画面IDから、ActionForwardを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param scrId 遷移元画面ID
     * @param map アクションマッピング
     * @return ActionForward
     */
    public ActionForward getActionForward(String scrId, ActionMapping map) {

        ActionForward forward = null;

        if (scrId.equals(GSConstProject.SCR_ID_PRJ010)) {
            //ダッシュボードへ遷移する
            forward = map.findForward(GSConstProject.SCR_INDEX);

        } else if (scrId.equals(GSConstProject.SCR_ID_PRJ040)) {
            //プロジェクト詳細検索へ遷移する
            forward = map.findForward(GSConstProject.SCR_PRJ_SEARCH);

        //メイン画面へ遷移する
        } else if (scrId.equals(GSConstProject.SCR_ID_MAIN)) {
            forward = map.findForward(GSConstProject.SCR_MENU);
        }

        return forward;
    }

    /**
     * <br>[機  能] TODOの状態を変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj030ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void editTodoStatus(Prj030ParamModel paramMdl, MlCountMtController cntCon, int userSid)
    throws SQLException {
        int projectSid = paramMdl.getPrj030prjSid();
        int status = paramMdl.getPrj030selectEditStatus();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //一括更新
        String textMultiEditRiku = gsMsg.getMessage("project.src.74");
        PrjCommonBiz commonBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        String[] todoSidList = paramMdl.getPrj030selectTodo();

        for (String todoSid : todoSidList) {
            commonBiz.changeTodoStatus(projectSid, Integer.parseInt(todoSid), status,
                    textMultiEditRiku,
                                    cntCon, userSid);
        }
    }

    /**
     * <br>[機  能] TODO情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj030ParamModel
     * @param limit 1ページの表示件数
     * @param userSid ログインユーザSID
     * @return List in ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    private List<ProjectItemModel> __getTodoList(Prj030ParamModel paramMdl, int limit, int userSid)
        throws SQLException {

        int nowPage = paramMdl.getPrj030page1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //検索用Modelを作成
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setProjectSid(paramMdl.getPrj030prjSid());
        bean.setUserSid(userSid);
        bean.setLimit(limit);
        bean.setOrder(paramMdl.getPrj030order());
        bean.setSort(paramMdl.getPrj030sort());
        bean.setEndPrjFlg(true);
        bean.setGetKbn(ProjectSearchModel.GET_ALL);
        bean.setSelectingDate(paramMdl.getSelectingDate());
        bean.setSelectingStatus(paramMdl.getSelectingStatus());
        bean.setSelectingCategory(paramMdl.getSelectingCategory());
        bean.setSelectingMember(paramMdl.getSelectingMember());

        //件数カウント
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
        long maxCount = psDao.getTodoCount(bean);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setPrj030page1(nowPage);
        paramMdl.setPrj030page2(nowPage);
        paramMdl.setPageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<ProjectItemModel>();
        }

        List<ProjectItemModel> todoList = psDao.getTodoList(bean);
        if (todoList != null && !todoList.isEmpty()) {
            //担当者情報を設定
            int prjSid = paramMdl.getPrj030prjSid();
            PrjTodomemberDao ptmDao = new PrjTodomemberDao(con__);
            for (int idx = 0; idx < todoList.size(); idx++) {
                List<TodoTantoModel> todoTantoList
                    = ptmDao.getTodoTantoList(prjSid, todoList.get(idx).getTodoSid());

                todoList.get(idx).setTodoTantoList(todoTantoList);
            }
        }

        return todoList;
    }

    /**
     * <br>[機  能] 外部メンバー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param paramMdl Prj030ParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __getOutMember(
            int userSid,
            Prj030ParamModel paramMdl)
    throws SQLException {

        //アドレス情報を取得
        Prj150Dao prj150Dao = new Prj150Dao(con__);

        PrjAddressDao paDao = new PrjAddressDao(con__);

        //プロジェクトSIDからアドレス情報取得
        List<PrjAddressModel> addDbSidList = paDao.getAddSidList(paramMdl.getPrj030prjSid());
        List<String> addSidList = new ArrayList<String>();

        for (int m = 0; m < addDbSidList.size(); m++) {
            addSidList.add(String.valueOf(addDbSidList.get(m).getAdrSid()));
        }
        String[] addId =  addSidList.toArray(new String[addSidList.size()]);

        //外部メンバー取得
        List<Prj150DspMdl> addressList
                    = prj150Dao.getAddressList2(con__, addId, userSid, paramMdl.getPrj030prjSid());

        paramMdl.setOutMemberList(addressList);

    }
    /**
     * <br>[機  能] TODOの日付を変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj030ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void editTodoDate(Prj030ParamModel paramMdl, MlCountMtController cntCon, int userSid)
    throws SQLException {

        String[] sidList = paramMdl.getPrj030selectTodo();
        //移動月
        int mvMonth = paramMdl.getPrj030mvMonth();
        //移動日
        int mvDay = paramMdl.getPrj030mvDay();
        //移動区分
        int mvKbn = paramMdl.getPrj030chDateKbn();
        //休日区分
        int holKbn = paramMdl.getPrj030chDateHol();

        HashMap< String, CmnHolidayModel > holMap  = new HashMap< String, CmnHolidayModel >();
        TcdAdmConfModel acMdl = new TcdAdmConfModel();
        CmnHolidayDao holDao = new CmnHolidayDao(con__);
        if (holKbn == 0) {
            //休日設定を反映させる場合はタイムカードの設定から休日を取得
            holMap = holDao.getHoliDayListFotTcd();
            //タイムカードの管理者設定を取得
            acMdl = getTcdAdmConfModel(userSid, con__);
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //一括更新
        String textMultiEditRiku = gsMsg.getMessage("project.src.74");
        PrjCommonBiz commonBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        for (String sid : sidList) {

            //選択されたTODOの情報を取得
            PrjTododataDao todoDao = new PrjTododataDao(con__);

            PrjTododataModel todoMdl = todoDao.select(
                    paramMdl.getPrj030prjSid(), Integer.valueOf(sid));
            if (todoMdl != null) {

                //開始予定日
                UDate frDate = todoMdl.getPtdDatePlan();
                if (frDate != null) {
                    frDate.setHour(0);
                    frDate.setMinute(0);
                }
                //終了予定日
                UDate fnDate = todoMdl.getPrjDateLimit();
                if (fnDate != null) {
                    fnDate.setHour(0);
                    fnDate.setMinute(0);
                }

                //開始予定と終了予定が設定されている場合の休日設定の反映
                if (holKbn == 0 && frDate != null && fnDate != null) {
                    //日数
                    int dayCnt = UDateUtil.diffDay(frDate, fnDate) + 1;
                    log__.debug("日数＝" + dayCnt);
                    HashMap< String, CmnHolidayModel > holMap2
                                            = new HashMap< String, CmnHolidayModel >();
                    //指定期間内に何日間休日があるかを取得
                    holMap2 = holDao.getHoliDayListFotTcd(frDate, fnDate);
                    int holCnt = __getHolCnt(frDate, fnDate, holMap2, acMdl);
                    //何日間(営業日)の予定か
                    int todoDayCnt = dayCnt - holCnt;
                    log__.debug("実質日数＝" + todoDayCnt);
                    //開始予定日を変更
                    getChDate(frDate, mvMonth, mvDay, mvKbn);
                    //変更した開始日予定日が休日かを判定
                    __getConvertDate(frDate, holMap, acMdl);
                    //終了予定日を設定
                    if (todoDayCnt == 0 || todoDayCnt == 1) {
                        fnDate = frDate;
                    } else {
                        fnDate = __getFnDate(frDate, holMap, acMdl, todoDayCnt);
                    }
                } else if (holKbn == 1 && frDate != null && fnDate != null) {
                    //休日設定なし
                    getChDate(frDate, mvMonth, mvDay, mvKbn);
                    getChDate(fnDate, mvMonth, mvDay, mvKbn);
                }

                if (frDate != null && fnDate == null) {
                    //変更後の日付を取得
                    getChDate(frDate, mvMonth, mvDay, mvKbn);
                    if (holKbn == 0) {
                        frDate = __getFnDate2(frDate, holMap, acMdl, 1, mvKbn);
                    }
                }
                if (frDate == null && fnDate != null) {
                    //変更後の日付を取得
                    getChDate(fnDate, mvMonth, mvDay, mvKbn);
                    if (holKbn == 0) {
                      fnDate = __getFnDate2(fnDate, holMap, acMdl, 1, mvKbn);
                    }
                }
                if (frDate != null || fnDate != null) {
                    commonBiz.changeTodoDate(paramMdl.getPrj030prjSid(), Integer.valueOf(sid),
                                            frDate, fnDate, textMultiEditRiku, cntCon, userSid);
                }
            }
        }
    }

    /**
    *
    * <br>[機  能] タイムカード管理者設定を取得する。
    * <br>[解  説] レコードが存在しない場合、デフォルト値で作成します。
    * <br>[備  考]
    * @param usrSid ユーザSID
    * @param con コネクション
    * @return TcdAdmConfModel
    * @throws SQLException SQL実行時例外
    */
   public TcdAdmConfModel getTcdAdmConfModel(int usrSid, Connection con) throws SQLException {
       log__.debug("タイムカード管理者設定取得");
       TcdAdmConfDao dao = new TcdAdmConfDao(con);
       TcdAdmConfModel model = dao.select();
       if (model == null) {
           boolean commit = false;
           try {
               model = new TcdAdmConfModel(usrSid);
               dao.insert(model);
               commit = true;
           } catch (SQLException e) {
               log__.error("タイムカード管理者設定の登録に失敗しました。");
               throw e;
           } finally {
               if (commit) {
                   con.commit();
               }

           }
       }
       return model;
   }
    /**
     * <br>[機  能] 日付変更後の日時を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param chDate 変更する日付
     * @param mvMonth 移動月数
     * @param mvDay 移動日数
     * @param mvKbn 移動区分
     */
    public static void getChDate(UDate chDate, int mvMonth,
                                  int mvDay, int mvKbn) {

        if (mvKbn == 0) {
            //日付を先に延ばす
            if (mvMonth > 0) {
                chDate.addMonth(mvMonth);
            }
            if (mvDay > 0) {
                chDate.addDay(mvDay);
            }
        } else {
            //日付を戻す
            if (mvMonth > 0) {
                chDate.addMonth(-mvMonth);
            }
            if (mvDay > 0) {
                chDate.addDay(-mvDay);
            }
        }
    }

    /**
     * <br>[機  能] 指定期間内の休日の日数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 開始予定日付
     * @param fnDate 終了予定日付
     * @param holMap 休日情報
     * @param acMdl タイムカード管理設定
     * @return 休日の日数
     */
    private int __getHolCnt(
            UDate frDate,
            UDate fnDate,
            HashMap<String, CmnHolidayModel> holMap,
            TcdAdmConfModel acMdl) {

        int holCnt = 0;
        UDate fr = frDate.cloneUDate();
        int addDay = 1;
        //休日として扱う曜日か判定
        while (fr.compareDateYMD(fnDate) > 0) {
            if (__isMatchWeek(fr.getWeek(), acMdl)
             || holMap.containsKey(fr.getDateString())) {
                log__.debug("休日として認識==>" + fr.getDateString());
                holCnt = holCnt + 1;
            }
            fr.addDay(addDay);
        }
        log__.debug("休日数＝" + holCnt);
        return holCnt;
    }

    /**
     * <br>[機  能] 営業日判定を行い非営業日の場合、振替設定によって日付をコンバートします。
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @param holMap 休日情報
     * @param acMdl タイムカード管理設定
     * @return UDate コンバート結果
     */
    private UDate __getConvertDate(
            UDate date,
            HashMap<String, CmnHolidayModel> holMap,
            TcdAdmConfModel acMdl) {

        UDate ret = date.cloneUDate();
        int addDay = 1;

        //休日として扱う曜日か判定
        boolean fin = true;
        while (fin) {
            if (__isMatchWeek(ret.getWeek(), acMdl)
             || holMap.containsKey(ret.getDateString())) {
                log__.debug("休日として認識==>" + ret.getDateString());
                ret.addDay(addDay);
            } else {
                fin = false;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定された営業日数後の日付を返します
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 日付
     * @param holMap 休日情報
     * @param acMdl タイムカード管理設定
     * @param todoDayCnt 営業日数
     * @return UDate コンバート結果
     *
     */
    private UDate __getFnDate(
            UDate frDate,
            HashMap<String, CmnHolidayModel> holMap,
            TcdAdmConfModel acMdl,
            int todoDayCnt) {

        UDate fr = frDate.cloneUDate();
        int dayCnt = 0;
        int addDay = 1;

        //休日として扱う曜日か判定
        while (dayCnt < todoDayCnt) {
            if (__isMatchWeek(fr.getWeek(), acMdl)
             || holMap.containsKey(fr.getDateString())) {
                log__.debug("休日として認識==>" + fr.getDateString());
            } else {
                dayCnt = dayCnt + 1;
            }
            if (dayCnt != todoDayCnt) {
                fr.addDay(addDay);
            }
        }
        return fr;
    }

    /**
     * <br>[機  能] 指定された営業日数後の日付を返します
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 日付
     * @param holMap 休日情報
     * @param acMdl タイムカード管理設定
     * @param todoDayCnt 営業日数
     * @param mvKbn 移動区分ｎ
     * @return UDate コンバート結果
     *
     */
    private UDate __getFnDate2(
            UDate frDate,
            HashMap<String, CmnHolidayModel> holMap,
            TcdAdmConfModel acMdl,
            int todoDayCnt,
            int mvKbn) {

        UDate fr = frDate.cloneUDate();
        int dayCnt = 0;
        int addDay = 1;
        if (mvKbn == 1) {
            addDay = -1;
        }

        //休日として扱う曜日か判定
        while (dayCnt < todoDayCnt) {
            if (__isMatchWeek(fr.getWeek(), acMdl)
             || holMap.containsKey(fr.getDateString())) {
                log__.debug("休日として認識==>" + fr.getDateString());
            } else {
                dayCnt = dayCnt + 1;
            }
            if (dayCnt != todoDayCnt) {
                fr.addDay(addDay);
            }
        }
        return fr;
    }

    /**
     * <br>[機  能] 指定した曜日が指定されているか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週
     * @param acMdl 管理設定
     * @return true:休日曜日　false:休日曜日以外
     */
    private boolean __isMatchWeek(int week, TcdAdmConfModel acMdl) {
        switch (week) {
        case UDate.SUNDAY:
            if (acMdl.getTacHolSun() == 0) {
                return false;
            }
            break;
        case UDate.MONDAY:
            if (acMdl.getTacHolMon() == 0) {
                return false;
            }
            break;
        case UDate.TUESDAY:
            if (acMdl.getTacHolTue() == 0) {
                return false;
            }
            break;
        case UDate.WEDNESDAY:
            if (acMdl.getTacHolWed() == 0) {
                return false;
            }
            break;
        case UDate.THURSDAY:
            if (acMdl.getTacHolThu() == 0) {
                return false;
            }
            break;
        case UDate.FRIDAY:
            if (acMdl.getTacHolFri() == 0) {
                return false;
            }
            break;
        case UDate.SATURDAY:
            if (acMdl.getTacHolSat() == 0) {
                return false;
            }
            break;
        default:
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] TODOSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj030ParamModel
     * @return TODOSID
     */
    public static String[] getTodoSid(Prj030ParamModel paramMdl) {
        String todoSid = null;
        String[] selectTodo = paramMdl.getPrj030selectTodo();
        String[] todoSids = new String[selectTodo.length];
        for (int idx = 0; idx < selectTodo.length; idx++) {
            todoSid = selectTodo[idx];
            todoSid = todoSid.substring(todoSid.indexOf(":") + 1, todoSid.length());
            todoSids[idx] = todoSid;
        }

        return todoSids;
    }

    /**
     * <br>[機  能] 選択したTODO名を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList todoSidリスト
     * @return タイトルリスト
     * @throws SQLException SQLException
     */
    public List<String> getTargetName(Connection con, String[] sidList)
            throws SQLException {

        PrjTododataDao dao = new PrjTododataDao(con);
        List<String> titleList = dao.getTodoTitleList(sidList);
        return titleList;
    }
}
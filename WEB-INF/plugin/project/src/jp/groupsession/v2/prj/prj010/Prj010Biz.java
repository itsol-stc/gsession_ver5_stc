package jp.groupsession.v2.prj.prj010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.PrjIUserGroupListenerImpl;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.PrjUserConfDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.PrjUserConfModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 ダッシュボード画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj010Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Prj010Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 常に描画する情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj010ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Prj010ParamModel paramMdl)
    throws SQLException {

        BaseUserModel buMdl = reqMdl__.getSmodel();
        if (paramMdl.isPrj010Init()) {

            paramMdl.setPrj010Init(false);

            PrjUserConfDao confDao = new PrjUserConfDao(con__);
            PrjUserConfModel confMdl = confDao.select(buMdl.getUsrsid());

            if (confMdl != null) {

                paramMdl.setSelectingTodoDay(String.valueOf(confMdl.getPucTodoDate()));

                boolean todoProjectEffective = false;
                ProjectSearchModel bean = new ProjectSearchModel();
                bean.setUserSid(buMdl.getUsrsid());
                bean.setOrder(GSConst.ORDER_KEY_ASC);
                bean.setSort(GSConstProject.SORT_PRJECT_START);
                bean.setEndPrjFlg(true);
                bean.setGetKbn(ProjectSearchModel.GET_BELONG);
                bean.setProjectSid(confMdl.getPucTodoProject());

                ProjectSearchDao psDao = new ProjectSearchDao(con__);
                List<ProjectItemModel> prjlist = psDao.getAllProjectList(bean);
                if (!prjlist.isEmpty()) {
                    todoProjectEffective = true;
                }

                if (todoProjectEffective) {
                    paramMdl.setSelectingTodoPrj(String.valueOf(confMdl.getPucTodoProject()));
                } else {
                    paramMdl.setSelectingTodoPrj(String.valueOf(GSConstProject.PROJECT_ALL));
                }

                if (todoProjectEffective) {
                    PrjTodostatusDao todoStsDao = new PrjTodostatusDao(con__);
                    PrjTodostatusModel todoStsMdl =
                        todoStsDao.select(
                                confMdl.getPucTodoProject(),
                                confMdl.getPucTodoStatus());

                    if (todoStsMdl != null
                            || confMdl.getPucTodoStatus() == GSConstProject.STATUS_ALL
                            || confMdl.getPucTodoStatus() == GSConstProject.STATUS_YOTEI_AND_MIKAN
                            || confMdl.getPucTodoStatus() == GSConstProject.STATUS_MIKAN
                            || confMdl.getPucTodoStatus() == GSConstProject.STATUS_SINKO
                            || confMdl.getPucTodoStatus() == GSConstProject.STATUS_KANRYO) {
                        paramMdl.setSelectingTodoSts(
                                String.valueOf(confMdl.getPucTodoStatus()));
                    } else {
                        paramMdl.setSelectingTodoSts(
                                String.valueOf(GSConstProject.STATUS_YOTEI_AND_MIKAN));
                    }
                }

                CommonBiz cmnBiz = new CommonBiz();
                boolean adminUser = cmnBiz.isPluginAdmin(con__,
                                     buMdl, GSConstProject.PLUGIN_ID_PROJECT);

                int prjKbn = confMdl.getPucPrjProject();
                if ((prjKbn == GSConstProject.PRJ_NOT_END_ALL
                        || prjKbn == GSConstProject.PRJ_END_ALL
                        || prjKbn == GSConstProject.PRJ_ALL)
                        && !adminUser) {
                    paramMdl.setSelectingProject(String.valueOf(GSConstProject.PRJ_MEMBER_NOT_END));
                } else {
                    paramMdl.setSelectingProject(String.valueOf(confMdl.getPucPrjProject()));
                }
            }
        }

        //ログインユーザSID
        int userSid = buMdl.getUsrsid();
        //処理モード
        String cmdMode = NullDefault.getString(paramMdl.getPrj010cmdMode(), "");

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //プロジェクト登録権限があるかチェックを行う
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        paramMdl.setPrjAdd(pcBiz.getPrjAddKengen(buMdl));

        //プロジェクト個人設定から最大表示件数を取得する
        int limit = pcBiz.getCountLimit(userSid, cmdMode);

        //コンボ設定
        __setSearchCombo(paramMdl, buMdl);

        //プロジェクト情報を取得する
        __setProjectList(paramMdl, limit, buMdl);

        //参加プロジェクト一覧を取得する
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(userSid);
        bean.setOrder(GSConst.ORDER_KEY_ASC);
        bean.setSort(GSConstProject.SORT_PRJECT_START);
        bean.setEndPrjFlg(false);
        bean.setGetKbn(ProjectSearchModel.GET_BELONG);

        ProjectSearchDao psDao = new ProjectSearchDao(con__);
        List<ProjectItemModel> prjlist = psDao.getAllProjectList(bean);
        PrjPrjdataDao pdDao = new PrjPrjdataDao(con__);
        int myPrjCount = pdDao.getMyPrjCount(userSid);
        if (myPrjCount <= 0) {

            log__.debug("***マイプロジェクト無し → 作成");
            //マイプロジェクトが登録されていない場合、作成する。
            PrjIUserGroupListenerImpl uglistener = new PrjIUserGroupListenerImpl();
            uglistener.addUser(null, con__, userSid, GSConst.USER_ADMIN, reqMdl__);
            con__.commit();
            //データ再取得
            prjlist = psDao.getAllProjectList(bean);
        }
        paramMdl.setAllProjectList(prjlist);
    }

    /**
     * <br>[機  能] TODOの状態を変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj010ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void editTodoStatus(Prj010ParamModel paramMdl, MlCountMtController cntCon, int userSid)
    throws SQLException {
        String[] sidList = paramMdl.getPrj010selectTodo();
        int status = paramMdl.getPrj010selectEditStatus();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //一括更新
        String textMultiEditRiku = gsMsg.getMessage("project.src.74");
        PrjCommonBiz commonBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        for (String sid : sidList) {

            int[] sids = formatSid(sid);

            commonBiz.changeTodoStatus(sids[0], sids[1], status,
                    textMultiEditRiku,
                                    cntCon, userSid);
        }
    }


    /**
     * <br>[機  能] TODOの日付を変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj010ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void editTodoDate(Prj010ParamModel paramMdl, MlCountMtController cntCon, int userSid)
    throws SQLException {

        String[] sidList = paramMdl.getPrj010selectTodo();
        //移動月
        int mvMonth = paramMdl.getPrj010mvMonth();
        //移動日
        int mvDay = paramMdl.getPrj010mvDay();
        //移動区分
        int mvKbn = paramMdl.getPrj010chDateKbn();
        //休日区分
        int holKbn = paramMdl.getPrj010chDateHol();

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

            int[] sids = formatSid(sid);

            //選択されたTODOの情報を取得
            PrjTododataDao todoDao = new PrjTododataDao(con__);

            PrjTododataModel todoMdl = todoDao.select(sids[0], sids[1]);
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
                    commonBiz.changeTodoDate(sids[0], sids[1], frDate, fnDate,
                            textMultiEditRiku,
                                            cntCon, userSid);
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
     * <br>[機  能] 選択されたTODOSIDの解析を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sid 選択されたTODOSID
     * @return プロジェクトSID(index=0)、TODOSID(index=1)を設定した配列
     */
    public static int[] formatSid(String sid) {
        int[] sids = null;
        int index = sid.indexOf(":");

        if (index > 0) {
            sids = new int[2];
            sids[0] = Integer.parseInt(sid.substring(0, index));
            sids[1] = Integer.parseInt(sid.substring(index + 1, sid.length()));
        }

        return sids;
    }

    /**
     * <br>[機  能] TODOSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj010ParamModel
     * @return TODOSID
     */
    public static String[] getTodoSid(Prj010ParamModel paramMdl) {
        String todoSid = null;
        String[] selectTodo = paramMdl.getPrj010selectTodo();
        String[] todoSids = new String[selectTodo.length];
        for (int idx = 0; idx < selectTodo.length; idx++) {
            todoSid = selectTodo[idx];
            todoSid = todoSid.substring(todoSid.indexOf(":") + 1, todoSid.length());
            todoSids[idx] = todoSid;
        }

        return todoSids;
    }

    /**
     * <br>[機  能] ＴＯＤＯ、プロジェクトの検索条件コンボを設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj010ParamModel
     * @param buMdl セッションユーザModel
     * @throws SQLException SQL実行時例外
     */
    private void __setSearchCombo(Prj010ParamModel paramMdl, BaseUserModel buMdl)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);

        //ＴＯＤＯ用 日付コンボ
        paramMdl.setTargetTodoDayLabel(pcBiz.getTargetDateLabel());

        //ＴＯＤＯ用 日付選択値
        paramMdl.setSelectingTodoDay(
                NullDefault.getStringZeroLength(
                        paramMdl.getSelectingTodoDay(),
                        String.valueOf(GSConstProject.DATE_THE_PAST)));

        //ＴＯＤＯ用 プロジェクトコンボ
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(buMdl.getUsrsid());
        bean.setOrder(GSConst.ORDER_KEY_ASC);
        bean.setSort(GSConstProject.SORT_PRJECT_START);
        bean.setEndPrjFlg(true);
        bean.setGetKbn(ProjectSearchModel.GET_BELONG);

        ProjectSearchDao psDao = new ProjectSearchDao(con__);
        List<ProjectItemModel> prjlist = psDao.getAllProjectList(bean);

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        //全て
        String textAll = gsMsg.getMessage("cmn.all");
        //全て
        labelList.add(new LabelValueBean(
                textAll,
                String.valueOf(GSConstProject.PROJECT_ALL)));

        for (ProjectItemModel piMdl : prjlist) {
            labelList.add(new LabelValueBean(
                    piMdl.getProjectName(), String.valueOf(piMdl.getProjectSid())));
        }
        paramMdl.setTargetTodoProjectLabel(labelList);

        //ＴＯＤＯ用 プロジェクト選択値
        paramMdl.setSelectingTodoPrj(
                NullDefault.getStringZeroLength(
                        paramMdl.getSelectingTodoPrj(),
                        String.valueOf(GSConstProject.PROJECT_ALL)));

        //ＴＯＤＯ用 状態コンボ
        paramMdl.setTargetTodoStsLabel(
                pcBiz.getStatusLabel(
                        NullDefault.getInt(
                                paramMdl.getSelectingTodoPrj(), -1)));

        //ＴＯＤＯ用 状態選択値
        paramMdl.setSelectingTodoSts(
                NullDefault.getStringZeroLength(
                        paramMdl.getSelectingTodoSts(),
                        String.valueOf(GSConstProject.STATUS_YOTEI_AND_MIKAN)));

        //プロジェクト用 プロジェクトコンボ
        paramMdl.setTargetProjectLabel(pcBiz.getTargetProjectLabel(buMdl));

        //プロジェクト用 プロジェクト選択値
        paramMdl.setSelectingProject(
                NullDefault.getStringZeroLength(
                        paramMdl.getSelectingProject(),
                        String.valueOf(GSConstProject.PRJ_MEMBER_NOT_END)));

        //状態変更用 状態コンボ
        paramMdl.setEditStatusLabel(pcBiz.getStatusLabel(false));
    }

    /**
     * <br>[機  能] プロジェクト情報、TODO情報を取得、paramMdlにセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj010ParamModel
     * @param limit 1ページの表示件数
     * @param buMdl ログインユーザ情報
     * @throws SQLException SQL実行例外
     */
    private void __setProjectList(Prj010ParamModel paramMdl, int limit, BaseUserModel buMdl)
    throws SQLException {

        //処理モード
        String cmdMode = NullDefault.getString(paramMdl.getPrj010cmdMode(), "");

        List<ProjectItemModel> prjList = null;

        int userSid = buMdl.getUsrsid();
        if (cmdMode.equals(GSConstProject.MODE_TODO)) {
            //TODO情報
            prjList = __getTodoList(paramMdl, limit, userSid);
            ProjectItemModel cntMdl = __getTodoCnt(paramMdl, userSid);

            paramMdl.setTodoKanryoCnt(cntMdl.getPrjTodoKanryoCnt());
            paramMdl.setTodoMikanryoCnt(cntMdl.getPrjTodoMikanryoCnt());
            paramMdl.setTodoSinkotyuCnt(cntMdl.getPrjTodoSinkotyuCnt());

        } else if (cmdMode.equals(GSConstProject.MODE_PROJECT)) {
            //プロジェクト
            prjList = __getProjectList(paramMdl, limit, userSid);
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);

        PrjCommonBiz prjBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        for (ProjectItemModel piMdl : prjList) {
            if (cmdMode.equals(GSConstProject.MODE_TODO)) {
                piMdl.setStrKanriNo(
                        StringUtil.toDecFormat(piMdl.getKanriNo(),
                                GSConstProject.KANRI_NO_FORMAT));
                piMdl.setStrJuyo(prjBiz.getWeightName(piMdl.getJuyo()));
            }
            piMdl.setStrStartDate(UDateUtil.getSlashYYMD(piMdl.getStartDate()));
            piMdl.setStrEndDate(UDateUtil.getSlashYYMD(piMdl.getEndDate()));

            piMdl.setPrjTodoEdit(prjBiz.getTodoEditKengen(piMdl.getProjectSid(), buMdl));
        }
        paramMdl.setProjectList(prjList);
    }

    /**
     * <br>[機  能] TODO情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj010ParamModel
     * @param limit 1ページの表示件数
     * @param userSid ログインユーザSID
     * @return List in ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    private List<ProjectItemModel> __getTodoList(Prj010ParamModel paramMdl, int limit, int userSid)
    throws SQLException {

        int nowPage = paramMdl.getPrj010page1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //検索用Modelを作成
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(userSid);
        bean.setLimit(limit);
        bean.setOrder(paramMdl.getPrj010order());
        bean.setSort(paramMdl.getPrj010sort());
        bean.setEndPrjFlg(true);
        bean.setGetKbn(ProjectSearchModel.GET_BELONG);
        bean.setSelectingDate(paramMdl.getSelectingTodoDay());
        bean.setProjectSid(Integer.parseInt(paramMdl.getSelectingTodoPrj()));
        bean.setSelectingStatus(paramMdl.getSelectingTodoSts());
        bean.setSelectingMember(String.valueOf(userSid));

        //件数カウント
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);
        long maxCount = psDao.getTodoCount(bean);
        log__.debug("件数 = " + maxCount);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setPrj010page1(nowPage);
        paramMdl.setPrj010page2(nowPage);
        paramMdl.setPageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<ProjectItemModel>();
        }

        return psDao.getTodoList(bean);
    }

    /**
     * <br>[機  能] TODO情報の完了・未完了件数を取得する
     * <br>[解  説]
     * <br>[備  考] 全件数をカウント(ページ毎のカウントではない)
     *
     * @param paramMdl Prj010ParamModel
     * @param userSid ログインユーザSID
     * @return ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    private ProjectItemModel __getTodoCnt(Prj010ParamModel paramMdl, int userSid)
    throws SQLException {

        //検索用Modelを作成
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(userSid);
        bean.setEndPrjFlg(true);
        bean.setGetKbn(ProjectSearchModel.GET_BELONG);
        bean.setSelectingDate(paramMdl.getSelectingTodoDay());
        bean.setProjectSid(Integer.parseInt(paramMdl.getSelectingTodoPrj()));
        bean.setSelectingStatus(paramMdl.getSelectingTodoSts());
        bean.setSelectingMember(String.valueOf(userSid));
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ProjectSearchDao psDao = new ProjectSearchDao(con__, gsMsg);

        return psDao.getTodoCnt(bean);
    }

    /**
     * <br>[機  能] プロジェクト情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj010ParamModel
     * @param limit 1ページの表示件数
     * @param userSid ログインユーザSID
     * @return List in ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    private List<ProjectItemModel>
             __getProjectList(Prj010ParamModel paramMdl, int limit, int userSid)
    throws SQLException {

        int nowPage = paramMdl.getPrj010page1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //検索用Modelを作成
        ProjectSearchModel bean = new ProjectSearchModel();
        bean.setUserSid(userSid);
        bean.setLimit(limit);
        bean.setOrder(paramMdl.getPrj010order());
        bean.setSort(paramMdl.getPrj010sort());
        bean.setEndPrjFlg(true);
        bean.setGetKbn(ProjectSearchModel.GET_SELECTED);
        bean.setPrjStatus(Integer.parseInt(paramMdl.getSelectingProject()));

        //件数カウント
        ProjectSearchDao psDao = new ProjectSearchDao(con__);
        long maxCount = psDao.getDashBoardProjectCount(bean);
        log__.debug("件数 = " + maxCount);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setPrj010page1(nowPage);
        paramMdl.setPrj010page2(nowPage);
        paramMdl.setPageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<ProjectItemModel>();
        }

        return psDao.getDashBoardProjectList(bean);
    }

    /**
     * <br>[機  能] プロジェクトSIDとアイコンバイナリSIDを照合する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param imgBinSid 画像バイナリSID
     * @return true:照合OK false:照合NG
     * @throws SQLException SQL実行例外
     */
    public boolean cheIcoHnt(int prjSid, Long imgBinSid)
    throws SQLException {

        boolean icoCheckFlg = false;

        //フォーラムSIDとアイコンバイナリSIDの組み合わせチェック
        PrjPrjdataDao ppDao = new PrjPrjdataDao(con__);
        boolean existForIcoFlg = ppDao.existPrjIco(prjSid, imgBinSid);

        if (existForIcoFlg) {
            icoCheckFlg = true;
        }

        return icoCheckFlg;
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
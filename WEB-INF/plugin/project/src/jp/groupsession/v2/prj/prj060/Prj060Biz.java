package jp.groupsession.v2.prj.prj060;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.PrjUsedDataBiz;
import jp.groupsession.v2.prj.ProjectUpdateBiz;
import jp.groupsession.v2.prj.dao.PrjStatusHistoryDao;
import jp.groupsession.v2.prj.dao.PrjTodoBinDao;
import jp.groupsession.v2.prj.dao.PrjTodocommentDao;
import jp.groupsession.v2.prj.dao.PrjTododataDao;
import jp.groupsession.v2.prj.dao.PrjTodomemberDao;
import jp.groupsession.v2.prj.dao.PrjTodostatusDao;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.PrjTodocommentModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.StatusHistoryModel;
import jp.groupsession.v2.prj.model.TodocommentModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] TODO参照画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj060Biz.class);

    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Prj060Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     * <p>Set Connection
     * @param reqMdl RequestModel
     */
    public Prj060Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj060ParamModel
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void setInitData(Prj060ParamModel paramMdl)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        int projectSid = paramMdl.getPrj060prjSid();
        int todoSid = paramMdl.getPrj060todoSid();

        //TODO状態を取得
        PrjTododataDao ptdDao = new PrjTododataDao(con__);
        PrjTododataModel ptdMdl = ptdDao.select(projectSid, todoSid);
        paramMdl.setPrj060status(ptdMdl.getPtsSid());

        //追加-------------------------------------------

        GsMessage gsMsg = new GsMessage();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strResultDateJp = gsMsg.getMessage("project.prj070.5");
        String endResultDateJp = gsMsg.getMessage("project.prj070.6");

        UDate jsStart = ptdMdl.getPtdDateStart();
        if (jsStart != null) {
            //実績開始 年 選択値
            paramMdl.setPrj060SelectYearFr(String.valueOf(jsStart.getYear()));
            //実績開始 月 選択値
            paramMdl.setPrj060SelectMonthFr(String.valueOf(jsStart.getMonth()));
            //実績開始 日 選択値
            paramMdl.setPrj060SelectDayFr(String.valueOf(jsStart.getIntDay()));
            picker.setDateParam(
                    paramMdl, 
                    "prj060SelectDateFr",
                    "prj060SelectYearFr",
                    "prj060SelectMonthFr",
                    "prj060SelectDayFr",
                    strResultDateJp);
        }

        UDate jsEnd = ptdMdl.getPtdDateEnd();
        if (jsEnd != null) {
            //実績終了 年 選択値
            paramMdl.setPrj060SelectYearTo(String.valueOf(jsEnd.getYear()));
            //実績終了 月 選択値
            paramMdl.setPrj060SelectMonthTo(String.valueOf(jsEnd.getMonth()));
            //実績終了 日 選択値
            paramMdl.setPrj060SelectDayTo(String.valueOf(jsEnd.getIntDay()));
            picker.setDateParam(
                    paramMdl, 
                    "prj060SelectDateTo",
                    "prj060SelectYearTo",
                    "prj060SelectMonthTo",
                    "prj060SelectDayTo",
                    endResultDateJp);
        }

        BigDecimal jissekiKosu = ptdMdl.getPtdResultsKosu();
        if (jissekiKosu != null) {
            //実績工数
            paramMdl.setPrj060ResultKosu(String.valueOf(jissekiKosu));
        }

        //追加ここまで-----------------------------------
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj060ParamModel
     * @param buMdl セッションユーザModel
     * @param admin システム管理者フラグ
     * @throws SQLException SQL実行例外
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void getDspData(Prj060ParamModel paramMdl, BaseUserModel buMdl, boolean admin)
            throws SQLException, UnsupportedEncodingException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strResultDateJp = gsMsg.getMessage("project.prj070.5");
        String endResultDateJp = gsMsg.getMessage("project.prj070.6");
        picker.setDateParam(
                paramMdl, 
                "prj060SelectDateFr",
                "prj060SelectYearFr",
                "prj060SelectMonthFr",
                "prj060SelectDayFr",
                strResultDateJp);
        picker.setDateParam(
                paramMdl, 
                "prj060SelectDateTo",
                "prj060SelectYearTo",
                "prj060SelectMonthTo",
                "prj060SelectDayTo",
                endResultDateJp);

        int projectSid = paramMdl.getPrj060prjSid();
        int todoSid = paramMdl.getPrj060todoSid();

        //TODO編集権限をセット
        PrjCommonBiz pcBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        paramMdl.setTodoEdit(pcBiz.getTodoEditKengen(projectSid, buMdl));

        //TODOの削除権限があるかチェックを行う
        paramMdl.setTodoDelete(pcBiz.getTodoDeleteKengen(projectSid, buMdl, admin));

        //TODO情報を取得する
        ProjectSearchModel bean = new ProjectSearchModel();
        //完了プロジェクト表示フラグ true=表示
        bean.setEndPrjFlg(true);
        //全て取得
        bean.setGetKbn(ProjectSearchModel.GET_ALL);
        //TODOSID
        bean.setTodoSid(todoSid);
        //未来の予定も表示する
//        bean.setMirai("1");
        bean.setOrder(GSConst.ORDER_KEY_DESC);
        bean.setSort(GSConstProject.SORT_TODO_WEIGHT);
        bean.setUserSid(buMdl.getUsrsid());

        ProjectSearchDao projectDao = new ProjectSearchDao(con__, gsMsg);
        ProjectItemModel piMdl = projectDao.getTodoData(bean);
        piMdl.setStrKanriNo(
                StringUtil.toDecFormat(piMdl.getKanriNo(), GSConstProject.KANRI_NO_FORMAT));
        piMdl.setStrJuyo(pcBiz.getWeightName(piMdl.getJuyo()));
        piMdl.setStrStartDate(UDateUtil.getSlashYYMD(piMdl.getStartDate()));
        piMdl.setStrEndDate(UDateUtil.getSlashYYMD(piMdl.getEndDate()));
        piMdl.setStrStartJissekiDate(UDateUtil.getSlashYYMD(piMdl.getStartJissekiDate()));
        piMdl.setStrEndJissekiDate(UDateUtil.getSlashYYMD(piMdl.getEndJissekiDate()));
        BigDecimal yoteiKosu = piMdl.getYoteiKosu();
        if (yoteiKosu != null) {
            piMdl.setStrYoteiKosu(String.valueOf(yoteiKosu));
        }
        BigDecimal jissekiKosu = piMdl.getJissekiKosu();
        if (jissekiKosu != null) {
            piMdl.setStrJissekiKosu(String.valueOf(jissekiKosu));
        }
//        piMdl.setNaiyou(NullDefault.getString(
//                StringUtilHtml.transToHTmlPlusAmparsant(piMdl.getNaiyou()), ""));
        piMdl.setNaiyou(StringUtilHtml.transToHTmlPlusAmparsantAndLink(piMdl.getNaiyou()));
        paramMdl.setProjectItem(piMdl);
        //登録者情報を取得
        CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel cuiMdl = cuiDao.getUsersInf(piMdl.getAddUserSid());
        paramMdl.setAddUserName(cuiMdl.getUsiSei() + " " + cuiMdl.getUsiMei());
        paramMdl.setAddUserStatus(cuiMdl.getUsrJkbn());
        paramMdl.setAddUsrUkoFlg(cuiMdl.getUsrUkoFlg());

        //TODOタイトルをparamMdlにセット
        paramMdl.setPrj060TodoTitle(piMdl.getTodoTitle());

        //担当者情報を取得する
        PrjTodomemberDao ptmDao = new PrjTodomemberDao(con__);
        paramMdl.setUserList(ptmDao.getTantoMemberList(projectSid, todoSid));

        //添付ファイル情報を取得する
        PrjTodoBinDao ptbDao = new PrjTodoBinDao(con__);
        paramMdl.setBinfList(ptbDao.getBinList(projectSid, todoSid));

        //TODOコメント情報を取得する
        PrjTodocommentDao ptcDao = new PrjTodocommentDao(con__);
        List<TodocommentModel> tcList =
                ptcDao.getTodoCommentList(projectSid, todoSid, buMdl.getUsrsid());
        for (TodocommentModel tcMdl : tcList) {
            tcMdl.setStrPcmAdate(UDateUtil.getSlashYYMD(tcMdl.getPcmAdate()));
            tcMdl.setPcmComment(StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                    NullDefault.getString(tcMdl.getPcmComment(), "")));
        }
        paramMdl.setTodoComList(tcList);

        //TODO状態を取得
        if (paramMdl.isTodoEdit()) {
            //編集権限がある時のみ
            PrjTodostatusDao ptsDao = new PrjTodostatusDao(con__);
            ProjectStatusModel prjStatusMdl = new ProjectStatusModel();
            prjStatusMdl.setTodoStatusList(ptsDao.selectProjects(projectSid));
            paramMdl.setPrjStatusMdl(prjStatusMdl);
        }

        //変更履歴を取得する
        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(con__);
        List<StatusHistoryModel> shList = pshDao.getStatusHistoryList(projectSid, todoSid);
        for (StatusHistoryModel shMdl : shList) {
            shMdl.setStrAddDate(UDateUtil.getSlashYYMD(shMdl.getAddDate())
                        + " " + UDateUtil.getSeparateHM(shMdl.getAddDate()));
            shMdl.setReason(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(shMdl.getReason()), ""));
        }
        paramMdl.setTodoHisList(shList);

        PrjCommonBiz prjComBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);

        //URLをセット
        paramMdl.setPrj060TodoUrl(prjComBiz.createTodoUrl(projectSid, todoSid));

        int send = paramMdl.getPrj060MailSend();

        //ショートメール通知区分
        paramMdl.setPrj060smailKbn(piMdl.getPrjMailKbn());
        if (send == -1) {
            if (piMdl.getPrjMailKbn() == GSConstProject.TODO_MAIL_FREE) {
                paramMdl.setPrj060MailSend(GSConstProject.NOT_SEND);
            } else if (piMdl.getPrjMailKbn() == GSConstProject.TODO_MAIL_SEND_ADMIN) {
                paramMdl.setPrj060MailSend(GSConstProject.SEND_LEADER);
            }
        }

        //ショートメール通知区分(コメント)
        if (paramMdl.getPrj060CommentMailSend() == -1) {
            if (piMdl.getPrjMailKbn() == GSConstProject.TODO_MAIL_FREE) {
                paramMdl.setPrj060CommentMailSend(GSConstProject.NOT_SEND);
            } else if (piMdl.getPrjMailKbn() == GSConstProject.TODO_MAIL_SEND_ADMIN) {
                paramMdl.setPrj060CommentMailSend(GSConstProject.SEND_LEADER);
            }
        }
    }

    /**
     * <br>[機  能] TODOを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj060ParamModel
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteTodo(Prj060ParamModel paramMdl, int userSid) throws SQLException {

        boolean commitFlg = false;

        try {

            //TODO情報を削除する
            int todoSid = paramMdl.getPrj060todoSid();
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
     * <br>[機  能] TODOコメント情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj060ParamModel
     * @throws SQLException SQL実行例外
     */
    public void deleteCmt(Prj060ParamModel paramMdl) throws SQLException {

        int projectSid = paramMdl.getPrj060prjSid();
        int todoSid = paramMdl.getPrj060todoSid();
        int cmtSid = paramMdl.getCommentSid();

        boolean commitFlg = false;

        try {
            //TODOコメントのデータ使用量を登録(削除対象のデータ使用量を減算)
            PrjUsedDataBiz usedDataBiz = new PrjUsedDataBiz(con__);
            usedDataBiz.insertTodoCommentSize(todoSid, Arrays.asList(cmtSid), false);

            //TODOコメント情報を削除する
            PrjTodocommentDao ptcDao = new PrjTodocommentDao(con__);
            ptcDao.deleteCommemt(projectSid, todoSid, cmtSid);

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
     * <br>[機  能] コメント登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj060ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doAddCmt(Prj060ParamModel paramMdl, MlCountMtController cntCon, int userSid)
    throws SQLException {

        int projectSid = paramMdl.getPrj060prjSid();
        int todoSid = paramMdl.getPrj060todoSid();

        boolean commitFlg = false;
        UDate now = new UDate();

        try {

            con__.setAutoCommit(false);

            //TODOコメントSIDを採番
            int todoCmtSid = (int) cntCon.getSaibanNumber(GSConstProject.SBNSID_PROJECT,
                              todoSid + GSConstProject.SBNSID_SUB_COMMENT,
                              userSid);

            PrjTodocommentModel ptcMdl = new PrjTodocommentModel();
            ptcMdl.setPrjSid(projectSid);
            ptcMdl.setPtdSid(todoSid);
            ptcMdl.setPcmSid(todoCmtSid);
            ptcMdl.setPcmComment(paramMdl.getPrj060comment());
            ptcMdl.setPcmAuid(userSid);
            ptcMdl.setPcmAdate(now);
            ptcMdl.setPcmEuid(userSid);
            ptcMdl.setPcmEdate(now);

            //TODOコメントを登録する
            PrjTodocommentDao ptcDao = new PrjTodocommentDao(con__);
            ptcDao.insert(ptcMdl);

            //TODOコメントのデータ使用量を登録
            PrjUsedDataBiz usedDataBiz = new PrjUsedDataBiz(con__);
            usedDataBiz.insertTodoCommentSize(todoSid, Arrays.asList(todoCmtSid), true);

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
     * <br>[機  能] 状態変更履歴情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj060ParamModel
     * @throws SQLException SQL実行例外
     */
    public void deleteStatus(Prj060ParamModel paramMdl) throws SQLException {

        int projectSid = paramMdl.getPrj060prjSid();
        int todoSid = paramMdl.getPrj060todoSid();
        int hisSid = paramMdl.getHistorySid();

        boolean commitFlg = false;

        try {

            //状態変更履歴情報を削除する
            PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(con__);
            pshDao.deleteHis(projectSid, todoSid, hisSid);

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
     * <br>[機  能] 状態変更履歴情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj060ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doAddHis(Prj060ParamModel paramMdl, MlCountMtController cntCon, int userSid)
    throws SQLException {
        int projectSid = paramMdl.getPrj060prjSid();
        int todoSid = paramMdl.getPrj060todoSid();
        int status = paramMdl.getPrj060status();
        String riyu = paramMdl.getPrj060riyu();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        PrjCommonBiz commonBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        commonBiz.changeTodoStatus(projectSid, todoSid, status, riyu, cntCon, userSid);
    }

    /**
     * <br>[機  能] 実績情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj060ParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doUpdateZisseki(Prj060ParamModel paramMdl,
                                 int userSid)
    throws SQLException {

        int projectSid = paramMdl.getPrj060prjSid();
        int todoSid = paramMdl.getPrj060todoSid();
        UDate now = new UDate();

        PrjTododataModel ptdMdl = new PrjTododataModel();
        ptdMdl.setPrjSid(projectSid);
        ptdMdl.setPtdSid(todoSid);

        //開始実績年月日
        UDate kaisiJisseki = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj060SelectYearFr(), -1),
                NullDefault.getInt(paramMdl.getPrj060SelectMonthFr(), -1),
                NullDefault.getInt(paramMdl.getPrj060SelectDayFr(), -1));

        ptdMdl.setPtdDateStart(kaisiJisseki);

        //開始実績年月日
        UDate syuryoJisseki = PrjCommonBiz.createUDate(
                NullDefault.getInt(paramMdl.getPrj060SelectYearTo(), -1),
                NullDefault.getInt(paramMdl.getPrj060SelectMonthTo(), -1),
                NullDefault.getInt(paramMdl.getPrj060SelectDayTo(), -1));

        ptdMdl.setPtdDateEnd(syuryoJisseki);

        //実績工数
        BigDecimal bdZissekiKosu = null;
        if (!StringUtil.isNullZeroString(paramMdl.getPrj060ResultKosu())) {
            bdZissekiKosu = new BigDecimal(paramMdl.getPrj060ResultKosu());
        }
        ptdMdl.setPtdResultsKosu(bdZissekiKosu);

        ptdMdl.setPtdEuid(userSid);
        ptdMdl.setPtdEdate(now);

        //TODO情報の状態を更新する
        PrjTododataDao ptdDao = new PrjTododataDao(con__);
        ptdDao.updateZisseki(ptdMdl);
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

        } else if (scrId.equals(GSConstProject.SCR_ID_PRJ030)) {
            //プロジェクトメインへ遷移する
            forward = map.findForward(GSConstProject.SCR_PRJ_MAIN);

        } else if (scrId.equals(GSConstProject.SCR_ID_PRJ070)) {
            //TODO詳細検索へ遷移する
            forward = map.findForward(GSConstProject.SCR_TODO_SEARCH);

        } else if (scrId.equals(GSConstProject.SCR_ID_MAIN)) {
            //メインメニューへ遷移する
            forward = map.findForward(GSConstProject.SCR_MENU);

        }



        return forward;
    }

    /**
     * <br>[機  能] TODOが存在するかチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj060ParamModel
     * @return ret:存在する  false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean isExistTodoData(Prj060ParamModel paramMdl) throws SQLException {

        boolean ret = false;

        int projectSid = paramMdl.getPrj060prjSid();
        int todoSid = paramMdl.getPrj060todoSid();

        //TODO状態を取得
        PrjTododataDao ptdDao = new PrjTododataDao(con__);
        PrjTododataModel ptdMdl = ptdDao.select(projectSid, todoSid);
        if (ptdMdl != null) {
            ret = true;
        }

        return ret;
    }

    /**
     * <br>[機  能] TODOタイトルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sid TODOSID
     * @return TODOタイトル
     * @throws SQLException
     */
    public String getTargetName(Connection con, int sid)
            throws SQLException {

        PrjTododataDao ptdDao = new PrjTododataDao(con__);
        ProjectItemModel mdl = ptdDao.getTodoData(sid);
        if (mdl == null) {
            return "";
        }
        return mdl.getTodoTitle();
    }
}
package jp.groupsession.v2.prj.prj060;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.GSValidateProject;
import jp.groupsession.v2.prj.model.StatusHistoryModel;
import jp.groupsession.v2.prj.model.TodocommentModel;
import jp.groupsession.v2.prj.prj070.Prj070Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] TODO参照画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj060Form extends Prj070Form {

    /** コメント */
    private String prj060comment__;
    /** 状態 */
    private int prj060status__;
    /** 状態変更理由 */
    private String prj060riyu__;
    /** TODOコメントSID */
    private int commentSid__;
    /** TODOタイトル */
    private String prj060TodoTitle__;
    /** 変更履歴SID */
    private int historySid__;

    //表示情報
    /** 登録者名 */
    private String addUserName__;
    /** 登録者状態 */
    private int addUserStatus__;
    /** 登録者ログイン停止フラグ */
    private int addUsrUkoFlg__;
    /** 添付ファイル情報 */
    private List<CmnBinfModel> binfList__;
    /** TODOコメント情報 */
    private List<TodocommentModel> todoComList__;
    /** TODO履歴情報 */
    private List<StatusHistoryModel> todoHisList__;

    //フラグ
    /** TODO編集権限 */
    private boolean todoEdit__;
    /** TODO削除権限 */
    private boolean todoDelete__;

    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private Long binSid__ = new Long(GSConstCommon.NUM_INIT);

    /** 実績開始 年 選択値 */
    private String prj060SelectYearFr__ = "";
    /** 実績開始 月 選択値 */
    private String prj060SelectMonthFr__ = "";
    /** 実績開始 日 選択値 */
    private String prj060SelectDayFr__ = "";

    /** 実績終了 年 選択値 */
    private String prj060SelectYearTo__ = "";
    /** 実績終了 月 選択値 */
    private String prj060SelectMonthTo__ = "";
    /** 実績終了 日 選択値 */
    private String prj060SelectDayTo__ = "";

    /** 実績開始 年月日 選択値 */
    private String prj060SelectDateFr__ = "";
    /** 実績終了 年月日 選択値 */
    private String prj060SelectDateTo__ = "";

    /** 実績工数 */
    private String prj060ResultKosu__ = "";

    /** ショートメール通知 */
    private int prj060MailSend__ = -1;
    /** ショートメール通知(コメント) */
    private int prj060CommentMailSend__ = -1;
    /** TODO_URL */
    private String prj060TodoUrl__ = "";
    /** ショートメール通知区分 */
    private int prj060smailKbn__;

    /** スケジュール画面遷移URL*/
    private String prj060schUrl__;

    /**
     * <p>prj060schUrl を取得します。
     * @return prj060schUrl
     */
    public String getPrj060schUrl() {
        return prj060schUrl__;
    }

    /**
     * <p>prj060schUrl をセットします。
     * @param prj060schUrl prj060schUrl
     */
    public void setPrj060schUrl(String prj060schUrl) {
        prj060schUrl__ = prj060schUrl;
    }

    /**
     * <br>[機  能] 入力チェックを行う(コメント追加時)
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエス
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate060Cmt(HttpServletRequest req) throws SQLException {
        GsMessage gsMsg = new GsMessage();
        ActionErrors errors = new ActionErrors();
        //コメント
        String textComment = gsMsg.getMessage(req, "cmn.comment");
        //コメント
        GSValidateProject.validateTextarea(
                errors,
                prj060comment__,
                textComment,
                GSConstProject.MAX_LENGTH_TODO_CMT,
                true);

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う(実績更新時)
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validate060Zisseki(HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        int errSize = 0;
        GsMessage gsMsg = new GsMessage();
        //開始実績
        String textZissekiFr = gsMsg.getMessage(req, "project.src.50");
        //終了実績
        String textZissekiTo = gsMsg.getMessage(req, "project.src.51");
        //正数部3桁小数部1桁以内
        String textDigitSeisu3Shosu1 = gsMsg.getMessage(req, "project.src.83");
        errSize = errors.size();
        GSValidateProject gsValidatePrj = new GSValidateProject(req);
        //開始実績年月日
        gsValidatePrj.validateYMD(
                errors,
                textZissekiFr,
                prj060SelectYearFr__,
                prj060SelectMonthFr__,
                prj060SelectDayFr__,
                false);

        //終了実績年月日
        gsValidatePrj.validateYMD(
                errors,
                textZissekiTo,
                prj060SelectYearTo__,
                prj060SelectMonthTo__,
                prj060SelectDayTo__,
                false);

        if (errSize == errors.size()
            && !NullDefault.getString(prj060SelectYearFr__, "").equals("")
            && !NullDefault.getString(prj060SelectMonthFr__, "").equals("")
            && !NullDefault.getString(prj060SelectDayFr__, "").equals("")
            && !NullDefault.getString(prj060SelectYearTo__, "").equals("")
            && !NullDefault.getString(prj060SelectMonthTo__, "").equals("")
            && !NullDefault.getString(prj060SelectDayTo__, "").equals("")) {

            //大小チェック
            UDate dateStart = new UDate();
            dateStart.setDate(NullDefault.getInt(prj060SelectYearFr__, -1),
                              NullDefault.getInt(prj060SelectMonthFr__, -1),
                              NullDefault.getInt(prj060SelectDayFr__, -1));
            UDate dateEnd = new UDate();
            dateEnd.setDate(NullDefault.getInt(prj060SelectYearTo__, -1),
                            NullDefault.getInt(prj060SelectMonthTo__, -1),
                            NullDefault.getInt(prj060SelectDayTo__, -1));
            GSValidateProject.validateDataRange(
                    errors,
                    textZissekiFr,
                    textZissekiTo,
                    dateStart,
                    dateEnd);
        }
        //実績工数
        String textJissekiKosu = gsMsg.getMessage(req, "project.prj060.5");
        //実績工数
        if (!StringUtil.isNullZeroString(prj060ResultKosu__)) {
            if (!ValidateUtil.isNumberDot(prj060ResultKosu__,
                                          GSConstProject.MAX_LENGTH_KOSU_SEISU,
                                          GSConstProject.MAX_LENGTH_KOSU_SYOSU)) {
                msg = new ActionMessage("error.input.comp.text",
                        textJissekiKosu,
                                        textDigitSeisu3Shosu1);
                StrutsUtil.addMessage(errors, msg, "prj060ResultKosu__");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う(状態履歴追加時)
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors validate060Status(HttpServletRequest req)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        GSValidateProject gsValidatePrj = new GSValidateProject(req);
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //開始実績
        String textZissekiFr = gsMsg.getMessage(req, "project.src.50");
        //終了実績
        String textZissekiTo = gsMsg.getMessage(req, "project.src.51");
        //正数部3桁小数部1桁以内
        String textSei3Shou1 = gsMsg.getMessage(req, "project.src.83");
        //状態
        String textStatus = gsMsg.getMessage(req, "cmn.status");
        //状態
        if (prj060status__ <= 0) {
            msg = new ActionMessage("error.select.required.text", textStatus);
            StrutsUtil.addMessage(errors, msg, "prj060status.error.select.required.text");
        }

        //状態 = 100%時
        if (prj060status__ == GSConstProject.STATUS_100) {

            int errSize = errors.size();
            DateTimePickerBiz picker = new DateTimePickerBiz();

            //開始実績年月日
            if (!StringUtil.isNullZeroString(prj060SelectDateFr__)) {
                errors.add(picker.setYmdParam(this, "prj060SelectDateFr",
                        "prj060SelectYearFr", "prj060SelectMonthFr",
                        "prj060SelectDayFr", textZissekiFr));
            } else {
                picker.setYmdParam(this, "prj060SelectDateFr",
                        "prj060SelectYearFr", "prj060SelectMonthFr",
                        "prj060SelectDayFr", textZissekiFr);
            }
            if (errSize == errors.size()) {
                gsValidatePrj.validateYMD(
                        errors,
                        textZissekiFr,
                        prj060SelectYearFr__,
                        prj060SelectMonthFr__,
                        prj060SelectDayFr__,
                        false);
            }

            //終了実績年月日
            if (!StringUtil.isNullZeroString(prj060SelectDateTo__)) {
                errors.add(picker.setYmdParam(this, "prj060SelectDateTo",
                        "prj060SelectYearTo", "prj060SelectMonthTo",
                        "prj060SelectDayTo", textZissekiTo));
            } else {
                picker.setYmdParam(this, "prj060SelectDateTo",
                        "prj060SelectYearTo", "prj060SelectMonthTo",
                        "prj060SelectDayTo", textZissekiTo);
            }
            if (errSize == errors.size()) {
                gsValidatePrj.validateYMD(
                        errors,
                        textZissekiTo,
                        prj060SelectYearTo__,
                        prj060SelectMonthTo__,
                        prj060SelectDayTo__,
                        false);
            }

            if (errSize == errors.size()
                && !NullDefault.getString(prj060SelectYearFr__, "").equals("")
                && !NullDefault.getString(prj060SelectMonthFr__, "").equals("")
                && !NullDefault.getString(prj060SelectDayFr__, "").equals("")
                && !NullDefault.getString(prj060SelectYearTo__, "").equals("")
                && !NullDefault.getString(prj060SelectMonthTo__, "").equals("")
                && !NullDefault.getString(prj060SelectDayTo__, "").equals("")) {

                //大小チェック
                UDate dateStart = new UDate();
                dateStart.setDate(NullDefault.getInt(prj060SelectYearFr__, -1),
                                  NullDefault.getInt(prj060SelectMonthFr__, -1),
                                  NullDefault.getInt(prj060SelectDayFr__, -1));
                UDate dateEnd = new UDate();
                dateEnd.setDate(NullDefault.getInt(prj060SelectYearTo__, -1),
                                NullDefault.getInt(prj060SelectMonthTo__, -1),
                                NullDefault.getInt(prj060SelectDayTo__, -1));
                GSValidateProject.validateDataRange(
                        errors,
                        textZissekiFr,
                        textZissekiTo,
                        dateStart,
                        dateEnd);
            }

            //実績工数
            if (!StringUtil.isNullZeroString(prj060ResultKosu__)) {
                if (!ValidateUtil.isNumberDot(prj060ResultKosu__,
                                              GSConstProject.MAX_LENGTH_KOSU_SEISU,
                                              GSConstProject.MAX_LENGTH_KOSU_SYOSU)) {
                    msg = new ActionMessage("error.input.comp.text",
                                            gsMsg.getMessage(req, "project.prj060.5"),
                                            textSei3Shou1);
                    StrutsUtil.addMessage(errors, msg, "prj060ResultKosu__");
                }
            }
        }
        //状態変更理由
        String textComment = gsMsg.getMessage(req, "project.36");
        //状態変更理由
        GSValidateProject.validateTextarea(
                errors,
                prj060riyu__,
                textComment,
                GSConstProject.MAX_LENGTH_STATUS_REASON,
                false);

        return errors;
    }

    /**
     * <br>[機  能] Cmn999Formに画面パラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     */
    public void setcmn999FormParam(Cmn999Form cmn999Form) {

        super.setcmn999FormParam(cmn999Form);

        cmn999Form.addHiddenParam("prj060scrId", getPrj060scrId());
        cmn999Form.addHiddenParam("prj060prjSid", getPrj060prjSid());
        cmn999Form.addHiddenParam("prj060todoSid", getPrj060todoSid());
        cmn999Form.addHiddenParam("prj060TodoTitle", getPrj060TodoTitle());
        cmn999Form.addHiddenParam("prj060comment", prj060comment__);
        cmn999Form.addHiddenParam("prj060status", prj060status__);
        cmn999Form.addHiddenParam("prj060riyu", prj060riyu__);
        cmn999Form.addHiddenParam("prj060SelectYearFr", prj060SelectYearFr__);
        cmn999Form.addHiddenParam("prj060SelectMonthFr", prj060SelectMonthFr__);
        cmn999Form.addHiddenParam("prj060SelectDayFr", prj060SelectDayFr__);
        cmn999Form.addHiddenParam("prj060SelectYearTo", prj060SelectYearTo__);
        cmn999Form.addHiddenParam("prj060SelectMonthTo", prj060SelectMonthTo__);
        cmn999Form.addHiddenParam("prj060SelectDayTo", prj060SelectDayTo__);
        cmn999Form.addHiddenParam("prj060ResultKosu", prj060ResultKosu__);
        cmn999Form.addHiddenParam("prj060smailKbn", prj060smailKbn__);
        cmn999Form.addHiddenParam("prj060schUrl", prj060schUrl__);
        cmn999Form.addHiddenParam("commentSid", commentSid__);
        cmn999Form.addHiddenParam("historySid", historySid__);
    }

    /**
     * <p>prj060comment を取得します。
     * @return prj060comment
     */
    public String getPrj060comment() {
        return prj060comment__;
    }
    /**
     * <p>prj060comment をセットします。
     * @param prj060comment prj060comment
     */
    public void setPrj060comment(String prj060comment) {
        prj060comment__ = prj060comment;
    }
    /**
     * <p>prj060status を取得します。
     * @return prj060status
     */
    public int getPrj060status() {
        return prj060status__;
    }
    /**
     * <p>prj060status をセットします。
     * @param prj060status prj060status
     */
    public void setPrj060status(int prj060status) {
        prj060status__ = prj060status;
    }
    /**
     * <p>prj060riyu を取得します。
     * @return prj060riyu
     */
    public String getPrj060riyu() {
        return prj060riyu__;
    }
    /**
     * <p>prj060riyu をセットします。
     * @param prj060riyu prj060riyu
     */
    public void setPrj060riyu(String prj060riyu) {
        prj060riyu__ = prj060riyu;
    }
    /**
     * <p>todoEdit を取得します。
     * @return todoEdit
     */
    public boolean isTodoEdit() {
        return todoEdit__;
    }
    /**
     * <p>todoEdit をセットします。
     * @param todoEdit todoEdit
     */
    public void setTodoEdit(boolean todoEdit) {
        todoEdit__ = todoEdit;
    }
    /**
     * <p>todoDelete を取得します。
     * @return todoDelete
     */
    public boolean isTodoDelete() {
        return todoDelete__;
    }
    /**
     * <p>todoDelete をセットします。
     * @param todoDelete todoDelete
     */
    public void setTodoDelete(boolean todoDelete) {
        todoDelete__ = todoDelete;
    }
    /**
     * <p>addUserName を取得します。
     * @return addUserName
     */
    public String getAddUserName() {
        return addUserName__;
    }
    /**
     * <p>addUserName をセットします。
     * @param addUserName addUserName
     */
    public void setAddUserName(String addUserName) {
        addUserName__ = addUserName;
    }
    /**
     * <p>addUserStatus を取得します。
     * @return addUserStatus
     */
    public int getAddUserStatus() {
        return addUserStatus__;
    }
    /**
     * <p>addUserStatus をセットします。
     * @param addUserStatus addUserStatus
     */
    public void setAddUserStatus(int addUserStatus) {
        addUserStatus__ = addUserStatus;
    }
    /**
     * <p>binfList を取得します。
     * @return binfList
     */
    public List<CmnBinfModel> getBinfList() {
        return binfList__;
    }
    /**
     * <p>binfList をセットします。
     * @param binfList binfList
     */
    public void setBinfList(List<CmnBinfModel> binfList) {
        binfList__ = binfList;
    }
    /**
     * <p>todoComList を取得します。
     * @return todoComList
     */
    public List<TodocommentModel> getTodoComList() {
        return todoComList__;
    }
    /**
     * <p>todoComList をセットします。
     * @param todoComList todoComList
     */
    public void setTodoComList(List<TodocommentModel> todoComList) {
        todoComList__ = todoComList;
    }
    /**
     * <p>todoHisList を取得します。
     * @return todoHisList
     */
    public List<StatusHistoryModel> getTodoHisList() {
        return todoHisList__;
    }
    /**
     * <p>todoHisList をセットします。
     * @param todoHisList todoHisList
     */
    public void setTodoHisList(List<StatusHistoryModel> todoHisList) {
        todoHisList__ = todoHisList;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>commentSid を取得します。
     * @return commentSid
     */
    public int getCommentSid() {
        return commentSid__;
    }

    /**
     * <p>commentSid をセットします。
     * @param commentSid commentSid
     */
    public void setCommentSid(int commentSid) {
        commentSid__ = commentSid;
    }

    /**
     * <p>historySid を取得します。
     * @return historySid
     */
    public int getHistorySid() {
        return historySid__;
    }

    /**
     * <p>historySid をセットします。
     * @param historySid historySid
     */
    public void setHistorySid(int historySid) {
        historySid__ = historySid;
    }

    /**
     * <p>prj060SelectDayFr を取得します。
     * @return prj060SelectDayFr
     */
    public String getPrj060SelectDayFr() {
        return prj060SelectDayFr__;
    }

    /**
     * <p>prj060SelectDayFr をセットします。
     * @param prj060SelectDayFr prj060SelectDayFr
     */
    public void setPrj060SelectDayFr(String prj060SelectDayFr) {
        prj060SelectDayFr__ = prj060SelectDayFr;
    }

    /**
     * <p>prj060SelectDayTo を取得します。
     * @return prj060SelectDayTo
     */
    public String getPrj060SelectDayTo() {
        return prj060SelectDayTo__;
    }

    /**
     * <p>prj060SelectDayTo をセットします。
     * @param prj060SelectDayTo prj060SelectDayTo
     */
    public void setPrj060SelectDayTo(String prj060SelectDayTo) {
        prj060SelectDayTo__ = prj060SelectDayTo;
    }

    /**
     * <p>prj060SelectMonthFr を取得します。
     * @return prj060SelectMonthFr
     */
    public String getPrj060SelectMonthFr() {
        return prj060SelectMonthFr__;
    }

    /**
     * <p>prj060SelectMonthFr をセットします。
     * @param prj060SelectMonthFr prj060SelectMonthFr
     */
    public void setPrj060SelectMonthFr(String prj060SelectMonthFr) {
        prj060SelectMonthFr__ = prj060SelectMonthFr;
    }

    /**
     * <p>prj060SelectMonthTo を取得します。
     * @return prj060SelectMonthTo
     */
    public String getPrj060SelectMonthTo() {
        return prj060SelectMonthTo__;
    }

    /**
     * <p>prj060SelectMonthTo をセットします。
     * @param prj060SelectMonthTo prj060SelectMonthTo
     */
    public void setPrj060SelectMonthTo(String prj060SelectMonthTo) {
        prj060SelectMonthTo__ = prj060SelectMonthTo;
    }

    /**
     * <p>prj060SelectYearFr を取得します。
     * @return prj060SelectYearFr
     */
    public String getPrj060SelectYearFr() {
        return prj060SelectYearFr__;
    }

    /**
     * <p>prj060SelectYearFr をセットします。
     * @param prj060SelectYearFr prj060SelectYearFr
     */
    public void setPrj060SelectYearFr(String prj060SelectYearFr) {
        prj060SelectYearFr__ = prj060SelectYearFr;
    }

    /**
     * <p>prj060SelectYearTo を取得します。
     * @return prj060SelectYearTo
     */
    public String getPrj060SelectYearTo() {
        return prj060SelectYearTo__;
    }

    /**
     * <p>prj060SelectYearTo をセットします。
     * @param prj060SelectYearTo prj060SelectYearTo
     */
    public void setPrj060SelectYearTo(String prj060SelectYearTo) {
        prj060SelectYearTo__ = prj060SelectYearTo;
    }

    /**
     * <p>prj060ResultKosu を取得します。
     * @return prj060ResultKosu
     */
    public String getPrj060ResultKosu() {
        return prj060ResultKosu__;
    }

    /**
     * <p>prj060ResultKosu をセットします。
     * @param prj060ResultKosu prj060ResultKosu
     */
    public void setPrj060ResultKosu(String prj060ResultKosu) {
        prj060ResultKosu__ = prj060ResultKosu;
    }
    /**
     * <p>prj060MailSend を取得します。
     * @return prj060MailSend
     */
    public int getPrj060MailSend() {
        return prj060MailSend__;
    }
    /**
     * <p>prj060MailSend をセットします。
     * @param prj060MailSend prj060MailSend
     */
    public void setPrj060MailSend(int prj060MailSend) {
        prj060MailSend__ = prj060MailSend;
    }
    /**
     * <p>prj060CommentMailSend を取得します。
     * @return prj060CommentMailSend
     */
    public int getPrj060CommentMailSend() {
        return prj060CommentMailSend__;
    }
    /**
     * <p>prj060CommentMailSend をセットします。
     * @param prj060CommentMailSend prj060CommentMailSend
     */
    public void setPrj060CommentMailSend(int prj060CommentMailSend) {
        prj060CommentMailSend__ = prj060CommentMailSend;
    }
    /**
     * <p>prj060TodoUrl を取得します。
     * @return prj060TodoUrl
     */
    public String getPrj060TodoUrl() {
        return prj060TodoUrl__;
    }
    /**
     * <p>prj060TodoUrl をセットします。
     * @param prj060TodoUrl prj060TodoUrl
     */
    public void setPrj060TodoUrl(String prj060TodoUrl) {
        prj060TodoUrl__ = prj060TodoUrl;
    }
    /**
     * <p>prj060smailKbn を取得します。
     * @return prj060smailKbn
     */
    public int getPrj060smailKbn() {
        return prj060smailKbn__;
    }
    /**
     * <p>prj060smailKbn をセットします。
     * @param prj060smailKbn prj060smailKbn
     */
    public void setPrj060smailKbn(int prj060smailKbn) {
        prj060smailKbn__ = prj060smailKbn;
    }
    /**
     * <p>prj060TodoTitle を取得します。
     * @return prj060TodoTitle
     */
    public String getPrj060TodoTitle() {
        return prj060TodoTitle__;
    }
    /**
     * <p>prj060TodoTitle をセットします。
     * @param prj060TodoTitle prj060TodoTitle
     */
    public void setPrj060TodoTitle(String prj060TodoTitle) {
        prj060TodoTitle__ = prj060TodoTitle;
    }

    /**
     * <p>addUsrUkoFlg を取得します。
     * @return addUsrUkoFlg
     */
    public int getAddUsrUkoFlg() {
        return addUsrUkoFlg__;
    }

    /**
     * <p>addUsrUkoFlg をセットします。
     * @param addUsrUkoFlg addUsrUkoFlg
     */
    public void setAddUsrUkoFlg(int addUsrUkoFlg) {
        addUsrUkoFlg__ = addUsrUkoFlg;
    }

    /**
     * <p>prj060SelectDateFr を取得します。
     * @return prj060SelectDateFr
     * @see jp.groupsession.v2.prj.prj060.Prj060Form#prj060SelectDateFr__
     */
    public String getPrj060SelectDateFr() {
        return prj060SelectDateFr__;
    }

    /**
     * <p>prj060SelectDateFr をセットします。
     * @param prj060SelectDateFr prj060SelectDateFr
     * @see jp.groupsession.v2.prj.prj060.Prj060Form#prj060SelectDateFr__
     */
    public void setPrj060SelectDateFr(String prj060SelectDateFr) {
        prj060SelectDateFr__ = prj060SelectDateFr;
    }

    /**
     * <p>prj060SelectDateTo を取得します。
     * @return prj060SelectDateTo
     * @see jp.groupsession.v2.prj.prj060.Prj060Form#prj060SelectDateTo__
     */
    public String getPrj060SelectDateTo() {
        return prj060SelectDateTo__;
    }

    /**
     * <p>prj060SelectDateTo をセットします。
     * @param prj060SelectDateTo prj060SelectDateTo
     * @see jp.groupsession.v2.prj.prj060.Prj060Form#prj060SelectDateTo__
     */
    public void setPrj060SelectDateTo(String prj060SelectDateTo) {
        prj060SelectDateTo__ = prj060SelectDateTo;
    }
}
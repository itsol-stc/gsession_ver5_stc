package jp.groupsession.v2.cht;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cht.dao.ChtCategoryDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChatValidate {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChatValidate.class);

    /** epefix 稟議*/
    private static final String CHT_E_CHAT__ = "chat";


    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateCmnFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = CHT_E_CHAT__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isTab(field)) {
            //タブスペースチェック
            msg = new ActionMessage("error.input.tab.text", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] 文章分割時の先頭スペースのみチェックしない
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateChtSecondFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = CHT_E_CHAT__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isTab(field)) {
            //タブスペースチェック
            msg = new ActionMessage("error.input.tab.text", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] 文字列の長さをチェックしない
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateChtAllFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = CHT_E_CHAT__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isTab(field)) {
            //タブスペースチェック
            msg = new ActionMessage("error.input.tab.text", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] グループIDの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param cgiSid cgiSid
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param con Connection
     * @return ActionErrors
     * @throws SQLException SQLエラー
     */
    public static ActionErrors validateIsExitGroupId(
        ActionErrors errors,
        int cgiSid,
        String checkObject,
        String field,
        String strField,
        Connection con) throws SQLException {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        String eprefix = CHT_E_CHAT__;
        String fieldfix = checkObject + ".";
        String catMsg = gsMsg.getMessage("cht.02");
        ChtGroupInfDao dao = new ChtGroupInfDao(con);
        boolean isGrpId = dao.isExitGroupId(field, cgiSid);

        if (isGrpId) {
             msg
            =  new ActionMessage("error.select.dup.list", catMsg);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
        }

        return errors;
    }


    /**
     * <br>[機  能] カテゴリの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param con Connection
     * @param defCategory カテゴリ無、一般カテゴリを判別するか
     * @return ActionErrors
     * @throws SQLException SQLエラー
     */
    public static ActionErrors validateIsExitCategory(
        ActionErrors errors,
        String checkObject,
        int field,
        String strField,
        Connection con,
        boolean defCategory) throws SQLException {
        // 『全て』はチェックしない
        if (field == GSConstChat.CHAT_CHC_SID_ALL) {
            return errors;
        }
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;
        String eprefix = CHT_E_CHAT__;
        String fieldfix = checkObject + ".";
        ChtCategoryDao dao = new ChtCategoryDao(con);
        boolean isCategory = dao.isExitCategory(field, defCategory);

        if (!isCategory) {
             msg
            =  new ActionMessage("search.data.notfound", checkObject);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
        }

        return errors;
    }

    /**
     * <br>[機  能] 日付の入力チェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors エラー
     * @param name 項目名
     * @param year 年
     * @param month 月
     * @param day 日
     * @param req リクエスト
     * @return エラー
     */
    public static ActionErrors checkDate(ActionErrors errors, String name,
            int year, int month, int day, HttpServletRequest req) {
        int errCount = 0;

        GsMessage gsMsg = new GsMessage();
        String msgYear = gsMsg.getMessage(req, "cmn.year2");
        String msgMonth = gsMsg.getMessage(req, "cmn.month");
        String msgDay = gsMsg.getMessage(req, "cmn.day");
        if (year <= 0 && (month > 0 || day > 0)) {
            ActionMessage msg =
                new ActionMessage("error.input.required.text", name + " " + msgYear);
            errors.add("error.input.required.text", msg);
            errCount++;
        }
        if (month <= 0 && (year > 0 || day > 0)) {
            ActionMessage msg =
                new ActionMessage("error.input.required.text", name + " " + msgMonth);
            errors.add("error.input.required.text", msg);
            errCount++;
        }
        if (day <= 0 && (year > 0 || month > 0)) {
            ActionMessage msg =
                new ActionMessage("error.input.required.text", name + " " + msgDay);
            errors.add("error.input.required.text", msg);
            errCount++;
        }

        if (errCount > 0) {
            return errors;
        }

        UDate date = new UDate();
        date.setDate(year, month, day);
        if (date.getYear() != year
        || date.getMonth() != month
        || date.getIntDay() != day) {
            ActionMessage msg = new ActionMessage("error.input.notfound.date", name);
            errors.add("error.input.notfound.date", msg);
        }

        return errors;
    }


    /**
     * <br>[機  能] 日付の範囲チェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors エラー
     * @param nameFrom 項目名From
     * @param nameTo 項目名To
     * @param frDate 開始日
     * @param toDate 終了日
     * @return エラー
     */
    public static ActionErrors checkDateRange(ActionErrors errors, String nameFrom, String nameTo,
            UDate frDate, UDate toDate) {

        if (frDate.compare(frDate, toDate) == UDate.SMALL) {
            ActionMessage msg = new ActionMessage("error.input.range.date",
                    nameFrom, nameTo);
            errors.add("error.input.range.date", msg);
        }

        return errors;
    }


    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return errors
     */
    public static ActionErrors validateTextarea(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return errors;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return errors;
        }
        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            msg = new ActionMessage("error.input.spase.cl.only", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
            return errors;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(target);
            msg = new ActionMessage("error.input.njapan.text", targetName, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return errors;
        }

        if (target.length() > maxLength) {
           //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return errors;
        }

        //入力エラー無し
        return errors;
    }

    /**
     * <br>[機  能] APIのテキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return errors
     */
    public static ActionErrors validateApiTextarea(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return errors;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return errors;
        }
        if (ValidateUtil.isSpaceStart(target)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", targetName);
            StrutsUtil.addMessage(errors, msg,
                    fieldFix + "error.input.spase.start");
            return errors;
        }

        if (ValidateUtil.isTab(target)) {
            //タブスペースチェック
            msg = new ActionMessage("error.input.tab.text", targetName);
            StrutsUtil.addMessage(errors, msg,
                    fieldFix + "error.input.tab.text");
            return errors;
        }

        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            msg = new ActionMessage("error.input.spase.cl.only", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
            return errors;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            msg = new ActionMessage("error.chat.not.send.unavailable");
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return errors;
        }

        if (target.length() > maxLength) {
           //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return errors;
        }

        //入力エラー無し
        return errors;
    }


    /**
     * <br>[機  能] 所属チャットグループチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 区分
     * @param sid 選択SID
     * @param usrSid ユーザSID
     * @param con Connection
     * @return boolean
     * @throws SQLException SQLエラー
     */
    public static boolean validateUserChtGrp(
        int kbn,
        int sid,
        int usrSid,
        Connection con) throws SQLException {

        if (kbn == GSConstChat.CHAT_KBN_USER) {
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            if (!cuDao.isExistUser(sid)) {
                return false;
            }
        } else {
            ChtGroupInfDao infDao = new ChtGroupInfDao(con);
            if (!infDao.isJoinGroup(sid, usrSid)) {
                return false;
            }
        }

        return true;
    }

    /**
     * <br>[機  能] チャットグループに所属しているか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param sid グループ
     * @param usrSid ユーザSID
     * @param con Connection
     * @return true:所属している false:していない
     * @throws SQLException SQLエラー
     */
    public static boolean validateJoinChtGrp(
        int sid,
        int usrSid,
        Connection con) throws SQLException {
        ChtGroupInfDao infDao = new ChtGroupInfDao(con);
        if (!infDao.isJoinGroup(sid, usrSid)) {
            return false;
        }
        return true;
    }


    /**
     * <br>[機  能] グループ編集制限判定
     * <br>[解  説] グループ作成編集権限を持っているか
     * <br>[備  考]
     * @param sid 選択SID
     * @param usrSid ユーザSID
     * @param con Connection
     * @return boolean
     * @throws SQLException SQLエラー
     */
    public static boolean validateLimitChtGrp(
        int sid,
        int usrSid,
        Connection con) throws SQLException {
        ChtGroupInfDao infDao = new ChtGroupInfDao(con);
        if (!infDao.isLimitEditGroup(sid, usrSid)) {
            return false;
        }
        return true;
    }


    /**
     * <br>[機  能] チャットグループの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sid sid
     * @param con Connection
     * @return boolean
     * @throws SQLException SQLエラー
     */
    public static boolean validateIsExitChatGroup(
        int sid,
        Connection con) throws SQLException {
        ChtGroupInfDao infDao = new ChtGroupInfDao(con);
        return infDao.isExitGroup(sid);
    }

    /**
     * <br>[機  能] 添付ファイル入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con      コネクション
     * @param reqMdl   リクエストモデル
     * @param file     ファイル
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateTempFile(Connection con, RequestModel reqMdl, FormFile file)
            throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        String fileName =  file.getFileName();
        int    fileSize = file.getFileSize();
        if (fileName.length() > GSConst.MAX_LENGTH_FILE) {
            //ファイル名
            String textFileName = gsMsg.getMessage("cmn.file.name");

            //ファイル名桁数チェック
            msg = new ActionMessage(
                    "error.input.length.text", textFileName, GSConst.MAX_LENGTH_FILE);
            errors.add("error.input.length.text", msg);
        } else if (!GSValidateUtil.isGsJapaneaseString(fileName)) {
            //ファイル名 使用文字チェック
            String textFileName = gsMsg.getMessage("cmn.file.name");  //ファイル名

            //利用不可能な文字を入力した場合
            String nstr =
                GSValidateUtil.getNotGsJapaneaseString(
                        fileName);
            msg =
                new ActionMessage("error.input.njapan.text",
                        textFileName,
                        nstr);
            errors.add("error.file.name.char", msg);
        } else {
            int maxSize = 0;
            CmnFileConfDao cfcDao = new CmnFileConfDao(con);

            //添付ファイル最大容量取得
            CmnFileConfModel cfcMdl = cfcDao.select();
            maxSize = cfcMdl.getFicMaxSize() * 1048576;
            if (fileSize > maxSize) {
                //指定されたファイルの容量が最大値を超えていた場合はエラーメッセージを表示
                msg = new ActionMessage("error.input.capacity.over", cfcMdl.getFicMaxSize() + "MB");
                errors.add("cmn110file.error.input.capacity.over", msg);
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 未選択チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param params チェック対象
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @return ActionErrors
     */
    public static ActionErrors validateNoSelect(
            ActionErrors errors,
            String[] params,
            String targetName,
            String targetJp) {

        if (params == null || params.length <= 0) {
            ActionMessage msg = new ActionMessage("error.select.required.text",
                    targetJp);
            StrutsUtil.addMessage(errors, msg, targetName + "error.select.required.text");
        }
        return errors;
    }


}

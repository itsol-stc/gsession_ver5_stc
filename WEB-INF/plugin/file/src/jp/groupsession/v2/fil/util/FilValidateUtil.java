package jp.groupsession.v2.fil.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * ファイル管理 入力チェックに関係する機能
 *
 * @author JTS
 */
public class FilValidateUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilValidateUtil.class);

    /**
     * <br>[機  能] ディレクトリアクセス権限の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors エラー
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param auth 権限区分
     * @param acArray 制限ユーザリスト
     * @param fdrSid ディレクトリSID
     * @param gsMsg GS共通メッセージ管理
     * @return 入力チェック結果 true : 正常 false : 不正
     * @throws SQLException 実行例外
     */
    public static boolean validateSltDaccess(ActionErrors errors,
                                             RequestModel reqMdl,
                                             Connection con,
                                             int auth,
                                             String[] acArray,
                                             int fdrSid,
                                             GsMessage gsMsg)
                                             throws SQLException {
        return validateSltDaccess(errors, reqMdl, con, auth, acArray, fdrSid, gsMsg, false);
    }
    /**
     * <br>[機  能] ディレクトリアクセス権限の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors エラー
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param auth 権限区分
     * @param acArray 制限ユーザリスト
     * @param fdrSid ディレクトリSID
     * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
     * @param gsMsg GS共通メッセージ管理
     * @return 入力チェック結果 true : 正常 false : 不正
     * @throws SQLException 実行例外
     */
    public static boolean validateSltDaccess(ActionErrors errors,
                                             RequestModel reqMdl,
                                             Connection con,
                                             int auth,
                                             String[] acArray,
                                             int fdrSid,
                                             GsMessage gsMsg,
                                             boolean ignoreJkbnFlg)
                                             throws SQLException {

        int startErrCount = errors.size();
        ActionMessage msg = null;

        if (acArray != null && acArray.length > 0) {
            ArrayList<Integer> grpSids = new ArrayList<Integer>();
            ArrayList<String> usrSids = new ArrayList<String>();

            //ユーザSIDとグループSIDを分離
            if (acArray != null) {
                for (int i = 0; i < acArray.length; i++) {
                    String str = NullDefault.getString(acArray[i], "-1");
                    if (str.contains(new String("G").subSequence(0, 1))) {
                        //グループ
                        grpSids.add(Integer.valueOf(str.substring(1, str.length())));
                    } else {
                        //ユーザ
                        usrSids.add(str);
                    }
                }
            }

            //グループ情報取得
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
            ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyClass(grpSids);
            //ユーザ情報取得
            UserBiz userBiz = new UserBiz();
            ArrayList<BaseUserModel> ulist
                    = userBiz.getBaseUserList(con,
                                            (String[]) usrSids.toArray(new String[usrSids.size()]));

            //個人キャビネット内か判定
            FileDirectoryDao fdrDao = new FileDirectoryDao(con);
            FileDirectoryModel fdrMdl = fdrDao.getNewDirectory(fdrSid);
            FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
            int personalFlg = cmnBiz.getPersonalFlg(fdrMdl.getFcbSid(), ignoreJkbnFlg);

            //共有キャビネット内の場合、親ディレクトリのアクセス権限を判定
            if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PUBLIC)) {
                //親ディレクトリのアクセス権限を取得
                List<String> parentSids = new ArrayList<String>();
                String[] prtGrpSids = null;
                String[] prtUsrSids = null;
                FileDAccessConfDao daConfDao = new FileDAccessConfDao(con);
                prtGrpSids = daConfDao.getAccessParentGroup(
                        fdrSid, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
                prtUsrSids = daConfDao.getAccessParentUser(
                        fdrSid, -1, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
                if (prtGrpSids != null) {
                    parentSids.addAll(Arrays.asList(prtGrpSids));
                }
                if (prtUsrSids != null) {
                    parentSids.addAll(Arrays.asList(prtUsrSids));
                }
                if (auth == Integer.parseInt(GSConstFile.ACCESS_KBN_READ)) {
                    prtGrpSids = daConfDao.getAccessParentGroup(
                            fdrSid, Integer.parseInt(GSConstFile.ACCESS_KBN_READ));
                    prtUsrSids = daConfDao.getAccessParentUser(
                            fdrSid, -1, Integer.parseInt(GSConstFile.ACCESS_KBN_READ));
                    if (prtGrpSids != null) {
                        parentSids.addAll(Arrays.asList(prtGrpSids));
                    }
                    if (prtUsrSids != null) {
                        parentSids.addAll(Arrays.asList(prtUsrSids));
                    }
                }
                List<String> notPAA = new ArrayList<String>();
                //グループ判定
                for (GroupModel gmodel : glist) {
                    if (!parentSids.contains("G" + String.valueOf(gmodel.getGroupSid()))) {
                        notPAA.add(gmodel.getGroupName());
                    }
                }
                //ユーザ判定
                for (BaseUserModel umodel : ulist) {
                    if (!parentSids.contains(String.valueOf(umodel.getUsrsid()))) {
                        notPAA.add(umodel.getUsiseimei());
                    }
                }
                if (notPAA.size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (String aa : notPAA) {
                        sb.append("・");
                        sb.append(aa);
                        sb.append("<br>");
                    }
                    msg = new ActionMessage("error.cant.list",
                            gsMsg.getMessage("fil.102"),
                            gsMsg.getMessage("fil.126"),
                            gsMsg.getMessage("cmn.setting"),
                            sb.toString());
                    StrutsUtil.addMessage(errors, msg, "error.no.daccess");

                }
            }
        }

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextField(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary) {

        log__.debug(paramNameJpn + " のチェックを行います。");

        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペースのみチェック
        if (!validateSpaceOnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //カンマチェック
        if (value.indexOf(",") > 0) {
            ActionMessage msg =
                new ActionMessage("error.cantinput.connma", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramName);
        }

        //JIS第二水準チェック
        validateGsJapanese(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] 数字フィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextFieldOfNumber(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary) {

        log__.debug(paramNameJpn + " のチェックを行います。");

        int startErrCount = errors.size();
        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //半角数字チェック
        validateNumber(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] 半角英数字フィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextFieldOfAlphaNum(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary) {

        log__.debug(paramNameJpn + " のチェックを行います。");

        int startErrCount = errors.size();
        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //半角英数字チェック
        validateAlphaNum(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <p>電話番号の入力チェックを行う
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTel(
                                    ActionErrors errors,
                                    String value,
                                    String paramName,
                                    String paramNameJpn,
                                    int maxLength,
                                    boolean necessary) {

        int startErrCnt = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);


        //電話番号フォーマットチェック
        ActionMessage msg = null;
        if (!GSValidateUtil.isTel(value)) {
            msg = new ActionMessage("error.input.format.text", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramName
                    + ".error.input.format.text");
        }

        return startErrCnt == errors.size();
    }

    /**
     * <br>[機  能] 全角カナフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextFieldKana(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary) {

        log__.debug(paramNameJpn + " のチェックを行います。");

        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペースのみチェック
        if (!validateSpaceOnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //全角カナチェック
        validateKana(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextAreaField(
                                                ActionErrors errors,
                                                String value,
                                                String paramName,
                                                String paramNameJpn,
                                                int maxLength,
                                                boolean necessary) {

        log__.debug(paramNameJpn + " のチェックを行います。");


        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペース、改行のみチェック
        if (!validateSpaceOrBROnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //JIS第二水準チェック
        validateGsJapanese(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }


    /**
     * <br>[機  能] 未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNecessary(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            String msgKey = "error.input.required.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] スペースのみチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateSpaceOnly(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpace(value)) {
            String msgKey = "error.input.spase.only";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] スペース、改行のみのみチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateSpaceOrBROnly(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpaceOrKaigyou(value)) {
            String msgKey = "error.input.spase.cl.only";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 先頭スペースチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateInitialSpace(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpaceStart(value)) {
            String msgKey = "error.input.spase.start";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 最大長を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大長
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateMaxLength(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                int maxLength) {

        String fieldfix = paramName + ".";

        if (value.length() > maxLength) {
            String msgKey = "error.input.length.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 半角数字チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNumber(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";
        if (!GSValidateUtil.isNumber(value)) {
            String msgKey = "error.input.number.hankaku";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] ユーザIDのフォーマットチェックを行う
     * <br>[解  説]
     * <br>[備  考] 半角英数、「.」、「_」、「-」、「@」が使用可能
     * @param input 文字列
     * @return true: 正常なユーザＩＤフォーマット, false: ユーザＩＤのフォーマットではない
     */
    public static boolean isUseridFormat(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Z0-9a-z@._\\-]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 半角英数字チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateAlphaNum(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";
        if (!GSValidateUtil.isAlphaNum(value)) {
            String msgKey = "error.input.text.hankaku";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 全角カナチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateKana(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (!GSValidateUtil.isGsWideKana(value)) {
            //全角カナチェック
            String msgKey = "error.input.kana.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 利用可能文字(JIS第二水準)チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateGsJapanese(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);

            String msgKey = "error.input.njapan.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn, nstr);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 日付の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param targetName チェック対象
     * @param year 年
     * @param month 月
     * @param day 日
     * @param req リクエスト
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateDate(
            ActionErrors errors,
            String targetName,
            String year,
            String month,
            String day,
            HttpServletRequest req) {

        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String textYear = gsMsg.getMessage(req, "cmn.year", year);
        String textMonth = gsMsg.getMessage(req, "cmn.month");
        String textDay = gsMsg.getMessage(req, "cmn.day");

        String eprefix = "date";
        String fieldFix = targetName + ".";
        String inputedDate = textYear + month + textMonth + day + textDay;
        int iBYear = Integer.parseInt(year);
        int iBMonth = Integer.parseInt(month);
        int iBDay = Integer.parseInt(day);

        UDate date = new UDate();
        date.setDate(iBYear, iBMonth, iBDay);
        if (date.getYear() != iBYear
        || date.getMonth() != iBMonth
        || date.getIntDay() != iBDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    targetName + "（" + inputedDate + ")");
            StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                    + "error.input.notfound.date");
            return true;
        }
        //エラー無し
        return false;
    }

    /**
     * <br>[機  能] 年月日大小チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param targetNameS チェック対象(開始日付)
     * @param targetNameE チェック対象(終了日付)
     * @param startDate 開始日付
     * @param endDate 終了日付
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateDataRange(ActionErrors errors,
                                                   String targetNameS,
                                                   String targetNameE,
                                                   UDate startDate,
                                                   UDate endDate) {
        ActionMessage msg = null;
        String eprefix = "dateRange.";
        String fieldFix = targetNameS + "." + targetNameE + ".";

        if (endDate.compareDateYMD(startDate) == UDate.LARGE) {
            msg = new ActionMessage(
                    "error.input.comp.text",
                    targetNameS + "・" + targetNameE, targetNameS + "<" + targetNameE);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldFix + "error.input.comp.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 年月日コンボ選択チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param paramName パラメータ名
     * @param year チェック対象(年)
     * @param month チェック対象(月)
     * @param date チェック対象(日)
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @param req リクエスト
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateDataListSel(ActionErrors errors,
                                                   HttpServletRequest req,
                                                   String paramName,
                                                   String year,
                                                   String month,
                                                   String date,
                                                   boolean necessary) {
        log__.debug(paramName + " のチェックを行います。");

        year = NullDefault.getString(year, "-1");
        month = NullDefault.getString(month, "-1");
        date = NullDefault.getString(date, "-1");
        String fieldfix = paramName + ".";

        //選択チェック
        if (!year.equals("-1") || !month.equals("-1") || !date.equals("-1")) {
            if (year.equals("-1") || month.equals("-1") || date.equals("-1")) {
                String msgKey = "error.input.notvalidate.data";
                ActionMessage msg = new ActionMessage(msgKey, paramName);
                StrutsUtil.addMessage(
                        errors, msg, fieldfix + msgKey);
                return true;
            }
        }

        //入力必須チェック
        if (year.equals("-1") && month.equals("-1") && date.equals("-1")) {
            if (necessary == true) {
                String msgKey = "error.input.required.text";
                ActionMessage msg = new ActionMessage(msgKey, paramName);
                StrutsUtil.addMessage(
                        errors, msg, fieldfix + msgKey);
                return true;
            }
        //論理チェック
        } else if (FilValidateUtil.validateDate(errors, paramName, year, month, date, req)) {
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] CSV項目の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @param rowNum 行番号
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateCsvText(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary,
        int rowNum) {

        log__.debug(paramNameJpn + " のチェックを行います。");

        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペースのみチェック
        if (!validateSpaceOnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //JIS第二水準チェック
        validateGsJapanese(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

}
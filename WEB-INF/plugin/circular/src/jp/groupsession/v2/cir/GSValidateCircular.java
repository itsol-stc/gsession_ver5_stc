package jp.groupsession.v2.cir;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir020.Cir020SearchModel;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 回覧板の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateCircular {

    /**
     * <br>[機  能] 回覧板の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param errors ActionErrors
     * @param cirSids 削除するマイグループSID
     * @param cacSid アカウントSID
     * @param con Connection
     * @return ActionErrors
     * @throws SQLException SQL例外
     */
    public static ActionErrors validateDeleteCir(
        RequestModel reqMdl,
        Connection con,
        ActionErrors errors,
        String[] cirSids,
        int cacSid) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //未選択チェック
        if (cirSids == null || cirSids.length < 1) {
            msg = new ActionMessage("error.select.required.text", gsMsg.getMessage("cir.5"));
            StrutsUtil.addMessage(errors, msg, "cirSid");
            return errors;
        }
        //回覧板アクセスチェック
        boolean accessFlg = true;
        for (String strCirSid: cirSids) {
            String[] splitSid = strCirSid.split("-");
            int cirSid = NullDefault.getInt(splitSid[0], -1);

            if (splitSid == null || splitSid.length != 2 || cirSid == -1) {
                accessFlg = false;
                break;
            }
            if (splitSid[1].equals(GSConstCircular.MODE_JUSIN)) {
                //受信
                //検索用Modelを作成
                Cir020SearchModel bean = new Cir020SearchModel();
                bean.setCirSid(cirSid);
                bean.setCacSid(cacSid);
                //回覧板情報(受信)を取得する
                CircularDao cDao = new CircularDao(con);
                CircularDspModel cdMdl = cDao.getJusinView(bean);
                if (cdMdl == null) {
                    accessFlg = false;
                    break;
                } else if (cdMdl.getCvwDsp() == GSConstCircular.DSPKBN_DSP_DEL) {
                    accessFlg = false;
                    break;
                }

            } else if (splitSid[1].equals(GSConstCircular.MODE_SOUSIN)) {
                //送信
                CircularDao cDao = new CircularDao(con);
                CirInfModel infMdl = cDao.checkSendCirExist(cirSid, cacSid);
                if (infMdl == null) {
                    accessFlg = false;
                    break;
                } else if (infMdl.getCifJkbn() == GSConstCircular.DSPKBN_DSP_DEL) {
                    accessFlg = false;
                    break;
                }
            } else {
                accessFlg = false;
                break;
            }

        }

        if (!accessFlg) {
            msg = new ActionMessage("error.select.required.text", gsMsg.getMessage("cir.5"));
            StrutsUtil.addMessage(errors, msg, "groupSid");
        }

        return errors;
    }

    /**
     * <br>[機  能] タイトルの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param title タイトル
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     */
    public static ActionErrors validateTitle(ActionErrors errors, String title,
            RequestModel reqMdl) {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = gsMsg.getMessage("cmn.title");

        //未入力チェック
        if (StringUtil.isNullZeroString(title)) {
            msg = new ActionMessage("error.input.required.text", message);
            StrutsUtil.addMessage(errors, msg, "title");
            return errors;
        }

        //MAX桁チェック
        if (title.length() > GSConstCircular.MAX_LENGTH_TITLE) {
            msg = new ActionMessage("error.input.length.text",
                                    message,
                                    GSConstCircular.MAX_LENGTH_TITLE);
            StrutsUtil.addMessage(errors, msg, "title");
        }
        //スペースのみチェック
        if (ValidateUtil.isSpace(title)) {
            msg = new ActionMessage("error.input.spase.only", message);
            StrutsUtil.addMessage(errors, msg, "title");
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(title)) {
            msg = new ActionMessage("error.input.spase.start", message);
            StrutsUtil.addMessage(errors, msg, "title");
        }
        //タブ文字が含まれている
        if (ValidateUtil.isTab(title)) {
            msg = new ActionMessage("error.input.tab.text", message);
            StrutsUtil.addMessage(errors, msg, "title");
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(title)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(title);
            msg = new ActionMessage("error.input.njapan.text",
                                    message,
                                    nstr);
            StrutsUtil.addMessage(errors, msg, "title");
        }

        return errors;
    }

    /**
     * <br>[機  能] 内容の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 内容
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     */
    public static ActionErrors validateValue(ActionErrors errors, String value,
            RequestModel reqMdl) {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = gsMsg.getMessage("cmn.content");

        //未入力チェック
        if (StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("error.input.required.text", message);
            StrutsUtil.addMessage(errors, msg, "value");
            return errors;
        }

        //MAX桁チェック
        int max_length = CirCommonBiz.getBodyLimitLength();
        if (max_length != 0 && value.length() > max_length) {
            msg = new ActionMessage("error.input.length.textarea",
                                    message,
                                    max_length);
            StrutsUtil.addMessage(errors, msg, "value");
        }
        //スペース(改行)のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(value)) {
            msg = new ActionMessage("error.input.spase.cl.only", message);
            StrutsUtil.addMessage(errors, msg, "value");
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text",
                                    message,
                                    nstr);
            StrutsUtil.addMessage(errors, msg, "value");
        }
        return errors;
    }

    /**
     * <br>[機  能] 回覧先の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param userSid 回覧先
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateMember(ActionErrors errors, String[] userSid,
            RequestModel reqMdl, Connection con) throws SQLException {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textMember = gsMsg.getMessage("cir.20");

        //未選択チェック
        if (userSid == null) {
            msg = new ActionMessage("error.select.required.text", textMember);
            StrutsUtil.addMessage(errors, msg, "userSid");
            return errors;
        } else {
            if (userSid.length < 1) {
                msg = new ActionMessage("error.select.required.text", textMember);
                StrutsUtil.addMessage(errors, msg, "userSid");
                return errors;
            }
        }
        //不正回覧先チェック
        //削除済ユーザのチェック
        CirAccountDao cacDao = new CirAccountDao(con);
        CmnUsrmDao udao = new CmnUsrmDao(con);

        ArrayList<Integer> newUserSid = new ArrayList<Integer>();
        List<String> accountUserSid = new ArrayList<String>();

        for (String usid : userSid) {
            if (usid.indexOf(GSConstCircular.CIR_ACCOUNT_STR) != -1) {
                //作成アカウント
                accountUserSid.add(usid.substring(GSConstCircular.CIR_ACCOUNT_STR.length()));
            } else {
                //GSユーザ
                newUserSid.add(Integer.valueOf(usid));
            }
        }

        int count = 0;
        int usrCount = 0;
        int cacCount = 0;

        if (!newUserSid.isEmpty()) {
            usrCount = udao.getCountDeleteUser(newUserSid);
        }

        if (!accountUserSid.isEmpty()) {
            cacCount = cacDao.getCountDeleteAccount(accountUserSid);
        }

        count = usrCount + cacCount;

        if (count > 0) {
            msg =
                new ActionMessage("error.select.has.deleteuser.list", textMember);
            StrutsUtil.addMessage(errors, msg,
                    "userSid." + "error.select.has.deleteuser.list");
        }
        //送信制限送信先のチェック
        CommonBiz cmnBiz = new CommonBiz();
        List<Integer> okUserSid
        = cmnBiz.getCanUsePluginUser(con,
                GSConstCircular.PLUGIN_ID_CIRCULAR,
                newUserSid);
        @SuppressWarnings("unchecked")
        List<Integer> noUserSid = ListUtils.subtract(newUserSid, okUserSid);
        if (noUserSid.size() > 0 || noUserSid.size() > 0) {
            StringBuilder sb = new StringBuilder();
            String[] usrSidsStr = new String[noUserSid.size()];
            for (int i = 0; i < noUserSid.size(); i++) {
                usrSidsStr[i] = String.valueOf(noUserSid.get(i));
            }
            List<BaseUserModel> usrMdlList = udao.getSelectedUserList(usrSidsStr);
            for (BaseUserModel buMdl : usrMdlList) {
                sb.append("<br />・");
                sb.append(buMdl.getUsisei());
                sb.append(" ");
                sb.append(buMdl.getUsimei());
            }
            msg =
                    new ActionMessage("error.dest.banned", textMember, sb.toString());
                StrutsUtil.addMessage(errors, msg, "userSid." + "error.dest.banned");


        }



        return errors;
    }

    /**
     * <br>[機  能] メモの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param memo 内容
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     */
    public static ActionErrors validateMemo(ActionErrors errors, String memo,
            RequestModel reqMdl) {
        ActionMessage msg = null;

        //未入力チェック
        if (StringUtil.isNullZeroString(memo)) {
            return errors;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textMemo = gsMsg.getMessage("cir.11");

        //MAX桁チェック
        if (memo.length() > GSConstCircular.MAX_LENGTH_MEMO) {
            msg = new ActionMessage("error.input.length.text",
                                    textMemo,
                                    GSConstCircular.MAX_LENGTH_MEMO);
            StrutsUtil.addMessage(errors, msg, "memo");
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(memo)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(memo);
            msg = new ActionMessage("error.input.njapan.text",
                                    textMemo,
                                    nstr);
            StrutsUtil.addMessage(errors, msg, "memo");
        }
        return errors;
    }
    
    /**
     * <p>メモの編集の可否を判定する
     * @param errors ActionErrors
     * @param cirSid 回覧板SID
     * @param cacSid 対象アカウントSID
     * @param reqMdl リクエスト情報
     * @param con コネクション 
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateMemoEdit(
            ActionErrors errors,
            String cirSid,
            int cacSid,
            RequestModel reqMdl,
            Connection con) throws SQLException {
        
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        int iCirSid = NullDefault.getInt(cirSid, -1);
        //対象の回覧板情報を取得する
        CirInfDao dao = new CirInfDao(con);
        CirInfModel cifMdl = dao.getCirInfo(iCirSid, -1);
        if (cifMdl == null) {
            String circular = gsMsg.getMessage("cir.5");
            msg = new ActionMessage("error.nothing.selected", circular);
            StrutsUtil.addMessage(errors, msg, "error.nothing.selected");
            return errors;
        }
        int memoEditFlg = cifMdl.getCifMemoFlg();
        UDate now = new UDate();
        UDate editLimitDate = cifMdl.getCifMemoDate();
        //対象の回覧板のメモが編集可能かを判定する
        if (memoEditFlg == GSConstCircular.CIR_INIT_MEMO_CHANGE_NO
                || (memoEditFlg == GSConstCircular.CIR_INIT_MEMO_CHANGE_YES
                && editLimitDate != null
                && editLimitDate.compareDateYMD(now) == UDate.LARGE)) {
            //メモが編集不可能の回覧板が確認済みかを取得する
            CirViewDao cvDao = new CirViewDao(con);
            CirViewModel searchMdl = new CirViewModel();
            searchMdl.setCifSid(iCirSid);
            searchMdl.setCacSid(cacSid);
            CirViewModel resultMdl = cvDao.select(searchMdl);
            //確認済みの場合、エラーメッセージを追加する
            if (resultMdl.getCvwConf() == GSConstCircular.CONF_OPEN) {
                msg = new ActionMessage("error.cant.edit.memo");
                StrutsUtil.addMessage(errors, msg, "error.cant.edit.memo");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 検索キーワードの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 検査値
     * @param reqMdl リクエスト情報
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchKeyword(ActionErrors errors, String value,
            RequestModel reqMdl) {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textSearchKey = gsMsg.getMessage("cmn.search.keyword");

        //未入力はOK
        if (StringUtil.isNullZeroString(value)) {
            return false;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(value)) {
            msg = new ActionMessage(
                    "error.input.spase.only", textSearchKey);
            StrutsUtil.addMessage(errors, msg, "keyWord");
            return true;
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(value)) {
            msg = new ActionMessage(
                    "error.input.spase.start", textSearchKey);
            StrutsUtil.addMessage(errors, msg, "keyWord");
            return true;
        }

        if (value.length() > GSConstCircular.MAX_LENGTH_KEYWORD) {
            //MAX桁チェック
            msg = new ActionMessage(
                    "error.input.length.text",
                    textSearchKey,
                    GSConstCircular.MAX_LENGTH_KEYWORD);
            StrutsUtil.addMessage(errors, msg, "keyWord__");
            return true;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(value);
            msg = new ActionMessage(
                    "error.input.njapan.text", textSearchKey, nstr);
            StrutsUtil.addMessage(errors, msg, "keyWord");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 検索対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param keyWord キーワード
     * @param searchTarget 検索対象
     * @param reqMdl リクエスト情報
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchTarget(
        ActionErrors errors,
        RequestModel reqMdl,
        String keyWord,
        String[] searchTarget) {

        ActionMessage msg = null;

        //キーワード未入力時はチェックなし
        if (NullDefault.getString(keyWord, "").length() < 1) {
            return false;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textTarget = gsMsg.getMessage("cmn.search2");

        if (searchTarget == null || searchTarget.length < 1) {
            //未選択の場合エラー
            msg = new ActionMessage(
                    "error.select.required.text", textTarget);
            StrutsUtil.addMessage(errors, msg, "target");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 検索ソート順の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param sortKey1 検査ソートKey1
     * @param sortKey2 検査ソートKey2
     * @param reqMdl リクエスト情報
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchSortOrder(
            ActionErrors errors,
            RequestModel reqMdl,
            int sortKey1,
            int sortKey2) {
        ActionMessage msg = null;

        if (sortKey1 == sortKey2) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textSortKey = gsMsg.getMessage("cmn.sortkey");

            //同一キー指定チェック
            msg = new ActionMessage("error.select.dup.list", textSortKey);
            StrutsUtil.addMessage(errors, msg, "sortKey");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] テキストボックスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            msg = new ActionMessage("error.input.njapan.text",
                    targetJp, nstr);
            StrutsUtil.addMessage(errors, msg,
                                     fieldFix + "error.input.njapan.text");
            return true;
        }

        if (maxLength > 0 && target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        if (ValidateUtil.isSpace(target)) {
            //スペースのみ
            String msgKey = "error.input.spase.only";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        if (ValidateUtil.isSpaceStart(target)) {
            //先頭スペース
            String msgKey = "error.input.spase.start";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        //タブ文字が含まれている
        if (ValidateUtil.isTab(target)) {
            String msgKey = "error.input.tab.text";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix + msgKey);
            return true;
        }

        //入力エラー無し
        return false;
    }


    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextarea(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            msg = new ActionMessage("error.input.spase.cl.only", targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
            return true;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(target);
            msg = new ActionMessage("error.input.njapan.text", targetJp, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }


    /**
     * <p>ユーザＩＤの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param index 番号
     * @return ActionErrors
     */
    public static ActionErrors validateCsvUserId(
            ActionErrors errors,
            String userid,
            String targetName,
            String targetJp,
            int index) {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";

        if (!(StringUtil.isNullZeroString(userid))) {
            if (userid.length() < GSConstCircular.MINLEN_USERID
                    || userid.length() > GSConstCircular.MAXLEN_USERID) {
                //MIN,MAX桁チェック
                msg = new ActionMessage("error.input.length2.text",
                        targetJp + index,
                        GSConstCircular.MINLEN_USERID, GSConstCircular.MAXLEN_USERID);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
            } else if (!GSValidateUtil.isUseridFormat(userid)) {
                //ユーザＩＤフォーマットチェック
                msg = new ActionMessage("error.input.format.text",
                        targetJp + index);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
            }
        }
        return errors;
    }
    /**
     * <p>グループＩＤの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param grpid ユーザＩＤ
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param index 番号
     * @return ActionErrors
     */
    public static ActionErrors validateCsvGroupId(
            ActionErrors errors,
            String grpid,
            String targetName,
            String targetJp,
            int index) {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";

        if (!(StringUtil.isNullZeroString(grpid))) {
            if (grpid.length() < GSConstCircular.MINLEN_USERID
                    || grpid.length() > GSConstCircular.MAXLEN_USERID) {
                //MIN,MAX桁チェック
                msg = new ActionMessage("error.input.length2.text",
                        targetJp + index,
                        GSConstCircular.MINLEN_USERID, GSConstCircular.MAXLEN_USERID);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
            } else if (!GSValidateUtil.isOtheridFormat(grpid)) {
                //ユーザＩＤフォーマットチェック
                msg = new ActionMessage("error.input.format.text",
                        targetJp + index);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
            }
        }
        return errors;
    }

    /**
     * <p>ユーザＩＤの存在チェックを行う(CSV取込み時)
     * <p>自分のユーザIDは除く
     * @param errors ActionErrors
     * @param userid ユーザＩＤ
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param con DBコネクション
     * @param index 番号
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateCsvUserIdExist(
            ActionErrors errors,
            String userid,
            String targetName,
            String targetJp,
            Connection con,
            int index) throws SQLException {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";
        if (!(StringUtil.isNullZeroString(userid))) {
            //存在しない、またはシステムユーザである場合のエラーチェックする
            CmnUsrmDao dao = new CmnUsrmDao(con);
            CmnUsrmModel mdl = dao.getUserSid(userid);
            if (mdl == null || mdl.getUsrSid() <= GSConstUser.USER_RESERV_SID) {
                msg = new ActionMessage("error.not.exist.userid",
                        targetJp + index);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.not.exist.userid");
                return errors;
            }
        }
        return errors;
    }

    /**
     * <p>グループＩＤの存在チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param groupid グループＩＤ
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param con DBコネクション
     * @param index 番号
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateCsvGroupIdExist(
            ActionErrors errors,
            String groupid,
            String targetName,
            String targetJp,
            Connection con,
            int index) throws SQLException {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";
        if (!(StringUtil.isNullZeroString(groupid))) {
            CmnGroupmDao dao = new CmnGroupmDao(con);
            boolean ret = dao.existGroupid(groupid);
            if (!ret) {
                //存在しない場合のエラー
                msg = new ActionMessage("error.not.exist.userid",
                        targetJp + index);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.not.exist.userid");
            }
        }
        return errors;
    }
}

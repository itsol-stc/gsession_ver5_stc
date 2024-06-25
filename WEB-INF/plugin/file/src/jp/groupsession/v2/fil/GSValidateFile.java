package jp.groupsession.v2.fil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.model.FilChildTreeModel;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル管理の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateFile {
    /**
     * <br>[機  能] テキストボックス（オールタイプ）の入力チェックを行う（汎用）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(target)) {
            msg = new ActionMessage("error.input.spase.only", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.only");
            return true;
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(target)) {
            msg = new ActionMessage("error.input.spase.start", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.start");
            return true;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            msg = new ActionMessage("error.input.njapan.text", targetName, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（数字タイプ）の入力チェックを行う（汎用）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInputNum(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isNumber(target)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.number.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（数字タイプ）の入力チェックを行う（汎用）
     * <br>[解  説] 最大値、最小値の判定も行う
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param min 最小値
     * @param max 最大値
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInputNumLenge(
            ActionErrors errors,
            String target,
            String targetName,
            int min,
            int max,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isNumber(target)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.number.text");
            return true;
        }

        if (NullDefault.getInt(target, -1) < min || NullDefault.getInt(target, -1) > max) {
            //入力範囲チェック
            msg = new ActionMessage("error.input.lenge", targetName, min, max);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.lenge");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（英数字タイプ）の入力チェックを行う（汎用）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param req リクエスト
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInputArphaNum(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput,
            HttpServletRequest req) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";
        GsMessage gsMsg = new GsMessage();
        String textHankakuEiji = gsMsg.getMessage(req, "cmn.english");

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isAlpha(target)) {
            //半角英字チェック
            msg = new ActionMessage("error.input.comp.text", targetName, textHankakuEiji);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.comp.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う（汎用）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextarea(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }
        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            msg = new ActionMessage("error.input.spase.cl.only", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
            return true;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(target);
            msg = new ActionMessage("error.input.njapan.text", targetName, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 検索ソート順の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param sortKey1 検査ソートKey1
     * @param sortKey2 検査ソートKey2
     * @param sortKeyName ソートキー名称
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchSortOrder(
            ActionErrors errors,
            String sortKey1,
            String sortKey2,
            String sortKeyName) {
        ActionMessage msg = null;

        String fieldFix = sortKeyName + ".";

        if (sortKey1.equals(sortKey2)) {
            //同一キー指定チェック
            msg = new ActionMessage("error.select.dup.list", sortKeyName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.select.dup.list");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param req リクエスト
     * @param searchTarget1 対象
     * @param searchTarget2 対象
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateTarget(
        ActionErrors errors,
        HttpServletRequest req,
        String searchTarget1,
        String searchTarget2) {

        ActionMessage msg = null;

        if (!(searchTarget1.equals(String.valueOf(GSConstFile.GET_TARGET_FOLDER)))
                && !(searchTarget2.equals(String.valueOf(GSConstFile.GET_TARGET_FILE)))) {
            //未選択の場合エラー
            GsMessage gsMsg = new GsMessage();
            String textTaisyou = gsMsg.getMessage(req, "cmn.target");
            msg = new ActionMessage(
                    "error.select.required.text", textTaisyou);
            StrutsUtil.addMessage(errors, msg, "target1");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param req リクエスト
     * @param searchTarget1 対象
     * @param searchTarget2 対象
     * @param searchTarget3 対象
     * @param searchTarget4 対象
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateTargetErrl(
        ActionErrors errors,
        HttpServletRequest req,
        String searchTarget1,
        String searchTarget2,
        String searchTarget3,
        String searchTarget4) {

        ActionMessage msg = null;

        if (__isNotCheck(searchTarget1, GSConstFile.GET_TARGET_FOLDER)
        && __isNotCheck(searchTarget2, GSConstFile.GET_TARGET_FILE)
        && __isNotCheck(searchTarget3, GSConstFile.GET_TARGET_DELETED)
        && __isNotCheck(searchTarget4, GSConstFile.GET_TARGET_DELETED)) {
            //未選択の場合エラー
            GsMessage gsMsg = new GsMessage();
            String textTaisyou = gsMsg.getMessage(req, "cmn.target");
            msg = new ActionMessage(
                    "error.select.required.text", textTaisyou);
            StrutsUtil.addMessage(errors, msg, "target1");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 指定されたパラメータ(チェックボックス)が未選択かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param target パラメータ
     * @param selectValue チェック時の値
     * @return true: 未選択, false: 選択
     */
    private static boolean __isNotCheck(String target, int selectValue) {
        return NullDefault.getInt(target, 9999) != selectValue;
    }

    /**
     * <br>[機  能] 検索対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param searchTarget1 検索対象
     * @param searchTarget2 検索対象
     * @param searchTarget3 検索対象
     * @param req リクエスト
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchTarget(
        ActionErrors errors,
        HttpServletRequest req,
        String searchTarget1,
        String searchTarget2,
        String searchTarget3) {

        ActionMessage msg = null;

        if (!(searchTarget1.equals(String.valueOf(GSConstFile.KEYWORD_TARGET_NAME)))
                && !(searchTarget2.equals(String.valueOf(GSConstFile.KEYWORD_TARGET_BIKO)))
                && !(searchTarget3.equals(String.valueOf(GSConstFile.KEYWORD_TARGET_TEXT)))) {
            //未選択の場合エラー

            GsMessage gsMsg = new GsMessage();
            String textSerachTarget = gsMsg.getMessage(req, "cmn.search2");

            msg = new ActionMessage(
                    "error.select.required.text", textSerachTarget);
            StrutsUtil.addMessage(errors, msg, "target2");
            return true;
        }

        return false;
    }

   /**
    *
    *<br>[機  能]チェックボックスが一つ以上選択されているか
    *<br>[解  説]
    *<br>[備  考]
    * @param errors ActionErrors
    * @param searchTarget 検索対象
    * @param req リクエスト
    * @return true: エラーあり false: エラーなし
    */
   public static boolean validateCheckSelected(ActionErrors errors,
           HttpServletRequest req, String[] searchTarget) {
       ActionMessage msg = null;

       if ((searchTarget != null)) {

          if (!(searchTarget.length > 0)) {
              //未選択の場合エラー

              GsMessage gsMsg = new GsMessage();
              String textDelShortcut = gsMsg.getMessage(req, "fil.76");

              msg = new ActionMessage(
                      "error.select.required.text", textDelShortcut);
              StrutsUtil.addMessage(errors, msg, "target2");
              return true;
          }
      }
      return false;
  }

   /**
    * <br>[機  能] キャビネットの最大容量設定を超えていないか判定する。
    * <br>[解  説]
    * <br>[備  考]
    * @param errors ActionErrors
    * @param fcbSid キャビネットSID
    * @param con コネクション
    * @param tempDir テンポラリディレクトリ
    * @param reqMdl RequestModel
    * @return チェック結果 true :エラー有り false :エラー無し
    * @throws SQLException SQL実行時例外
    * @throws IOToolsException ファイル操作時例外
    */
   public static boolean validateUsedDiskSizeOver(
           ActionErrors errors,
           int fcbSid,
           Connection con,
           String tempDir,
           RequestModel reqMdl) throws SQLException, IOToolsException {

       FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);

       //添付ファイルサイズを取得する。
       BigDecimal tempFileSize = filBiz.getSumTempFileSize(tempDir);

       return validateUsedDiskSizeOverValue(
               errors,
               fcbSid,
               con,
               tempFileSize);
   }

   /**
    * <br>[機  能] キャビネットの最大容量設定を超えていないか判定する。
    * <br>[解  説]
    * <br>[備  考]
    * @param errors ActionErrors
    * @param fcbSid キャビネットSID
    * @param con コネクション
    * @param tempFileSize 添付ファイルサイズ
    * @return チェック結果 true :エラー有り false :エラー無し
    * @throws SQLException SQL実行時例外
    * @throws IOToolsException ファイル操作時例外
    */
   public static boolean validateUsedDiskSizeOverValue(
           ActionErrors errors,
           int fcbSid,
           Connection con,
           BigDecimal tempFileSize) throws SQLException, IOToolsException {
       return validateUsedDiskSizeOverValue(errors, fcbSid, con, tempFileSize, false);
   }
   /**
    * <br>[機  能] キャビネットの最大容量設定を超えていないか判定する。
    * <br>[解  説]
    * <br>[備  考]
    * @param errors ActionErrors
    * @param fcbSid キャビネットSID
    * @param con コネクション
    * @param tempFileSize 添付ファイルサイズ
    * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
    * @return チェック結果 true :エラー有り false :エラー無し
    * @throws SQLException SQL実行時例外
    * @throws IOToolsException ファイル操作時例外
    */
   public static boolean validateUsedDiskSizeOverValue(
           ActionErrors errors,
           int fcbSid,
           Connection con,
           BigDecimal tempFileSize,
           boolean ignoreJkbnFlg) throws SQLException, IOToolsException {

       ActionMessage msg = null;

       //最大ディスク使用容量を取得する。
       FileCabinetDao cabDao = new FileCabinetDao(con);
       FileCabinetModel cabMdl = cabDao .select(fcbSid);

       if (cabMdl == null
               || (cabMdl.getFcbJkbn() != GSConst.JTKBN_TOROKU
                   && ignoreJkbnFlg == false)
               ) {
           //キャビネット存在なし
           GsMessage gsMsg = new GsMessage();
           String textSelectedFile = gsMsg.getMessage("fil.23");
           msg = new ActionMessage(
                   "search.notfound.tdfkcode", textSelectedFile);
           StrutsUtil.addMessage(errors, msg, "fcbSid");
           return false;
       }

       if (cabMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
           FileAconfDao   aconfDao = new FileAconfDao(con);
           FileAconfModel aconfMdl = aconfDao.select();
           if (aconfMdl == null) {
               aconfMdl = new FileAconfModel();
               aconfMdl.init();
           }
           cabMdl.setFileAconf(aconfMdl);
       }

       if (cabMdl.getFcbCapaKbn() == GSConstFile.CAPA_KBN_OFF) {
           return false;
       }
       BigDecimal capaSizeMB = BigDecimal.valueOf(cabMdl.getFcbCapaSize());
       BigDecimal capaSize = capaSizeMB.multiply(new BigDecimal(1000000));

       //現在の使用容量を取得する。
       FileCabinetDao dao = new FileCabinetDao(con);
       BigDecimal usedSize = dao.getCabinetUsedSize(fcbSid);

       //登録後の合計サイズ
       BigDecimal useSize = usedSize.add(tempFileSize);

       if (useSize.compareTo(capaSize) == 1) {
           msg = new ActionMessage("error.input.cabinet.capacity.over", capaSizeMB + "MB");
           StrutsUtil.addMessage(errors, msg, "error.input.cabinet.capacity.over");
       }

       //入力エラー無し
       return false;
   }

   /**
    * <br>[機  能] 添付ファイルの最大容量設定を超えていないか判定する。
    * <br>[解  説]
    * <br>[備  考]
    * @param errors ActionErrors
    * @param con コネクション
    * @param tempFileSize 添付ファイルサイズ
    * @param reqMdl RequestModel
    * @return チェック結果 true :エラー有り false :エラー無し
    * @throws SQLException SQL実行時例外
    * @throws IOToolsException ファイル操作時例外
    */
   public static boolean validateFileSizeOver(
           ActionErrors errors,
           Connection con,
           BigDecimal tempFileSize,
           RequestModel reqMdl) throws SQLException, IOToolsException {

       ActionMessage msg = null;
       FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);

       //最大添付ファイルサイズを取得する。
       FileAconfModel aconfModel = fileBiz.getFileAconfModel();
       BigDecimal capaSizeMB = new BigDecimal(aconfModel.getFacFileSize());
       BigDecimal capaSize = capaSizeMB.multiply(new BigDecimal(1000000));

       if (tempFileSize.compareTo(capaSize) == 1) {
           msg = new ActionMessage("error.input.capacity.over", capaSizeMB + "MB");
           StrutsUtil.addMessage(errors, msg, "error.input.capacity.over");
           //入力エラー無し
           return true;
       }

       //入力エラー無し
       return false;
   }
   /**
    * <br>[機  能] ロック中のファイルが存在するか確認する
    * <br>[解  説]
    * <br>[備  考]
    * @param errors ActionErrors
    * @param con コネクション
    * @param reqMdl RequestModel
    * @param dirSid ディレクトリSID
    * @param fcbSid キャビネットSID
    * @param usrSid ユーザSID
    * @param myDir 選択ディレクトリ情報
    * @return チェック結果 true :エラー有り false :エラー無し
    * @throws SQLException SQL実行時例外
    * @throws IOToolsException ファイル操作時例外
    */
   public static boolean validateLockFile(
           ActionErrors errors,
           Connection con,
           RequestModel reqMdl,
           int dirSid,
           int fcbSid,
           int usrSid,
           FileDirectoryModel myDir
           ) throws SQLException, IOToolsException {
       return validateLockFile(errors, con, reqMdl, dirSid, fcbSid, usrSid, myDir, false);

   }

   /**
    * <br>[機  能] ロック中のファイルが存在するか確認する
    * <br>[解  説]
    * <br>[備  考]
    * @param errors ActionErrors
    * @param con コネクション
    * @param reqMdl RequestModel
    * @param dirSid ディレクトリSID
    * @param fcbSid キャビネットSID
    * @param usrSid ユーザSID
    * @param myDir 選択ディレクトリ情報
    * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
    * @return チェック結果 true :エラー有り false :エラー無し
    * @throws SQLException SQL実行時例外
    * @throws IOToolsException ファイル操作時例外
    */
   public static boolean validateLockFile(
           ActionErrors errors,
           Connection con,
           RequestModel reqMdl,
           int dirSid,
           int fcbSid,
           int usrSid,
           FileDirectoryModel myDir,
           boolean ignoreJkbnFlg
           ) throws SQLException, IOToolsException {
       ActionMessage msg = null;
       FilCommonBiz biz = new FilCommonBiz(reqMdl, con);
       GsMessage gsMsg = new GsMessage();
       // ディレクトリ確認
       if (myDir == null) {
           msg = new ActionMessage("search.data.notfound", gsMsg.getMessage("fil.118"));
           StrutsUtil.addMessage(errors, msg, "fdrSid");
           return true;
       }
       // ディレクトリ:ロックファイル確認
       FileFileBinDao fileBinDao = new FileFileBinDao(con);
       if (myDir.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
           // 選択対象がファイル
           FileFileBinModel fileBinModel = fileBinDao.getNewFile(dirSid);
           if (fileBinModel != null && fileBinModel.getFflLockKbn() == GSConstFile.LOCK_KBN_ON) {
               msg = new ActionMessage("error.file.lock.move", gsMsg.getMessage("cmn.file"));
               StrutsUtil.addMessage(errors, msg, "fdrSid");
               return true;
           }
       } else {
           // 選択対象がフォルダ
           FilTreeBiz treeBiz = new FilTreeBiz(con);
           // 子階層のファイル情報一覧を全て取得
           FilChildTreeModel childMdl = treeBiz.getChildOfTarget(myDir);
           if (childMdl != null) {
               ArrayList<FileDirectoryModel> fileDirSids = childMdl.getListOfFile();
               if (fileDirSids == null || fileDirSids.isEmpty()) {
                   // ファイルが存在しない
                   return false;
               }
               // ロック中のファイル情報一覧を取得
               List<Integer> errList = fileBinDao.getLockNewFile(fileDirSids);
               if (errList != null && !errList.isEmpty()) {
                   boolean existNotAccess = false;
                   for (Integer errDirSid:errList) {
                       if (!biz.isDirAccessAuthUser(fcbSid,
                               errDirSid,
                               Integer.parseInt(GSConstFile.ACCESS_KBN_READ))) {
                           // アクセス権限が存在しない
                           existNotAccess = true;
                       } else {
                           String path = biz.getDirctoryPath(errDirSid, ignoreJkbnFlg);
                           msg = new ActionMessage("error.file.lock.move",
                                   path);
                           StrutsUtil.addMessage(errors, msg,
                                   "fdrSid_" + String.valueOf(errDirSid));
                       }
                   }
                   if (existNotAccess) {
                       msg = new ActionMessage("error.file.lock.move.unknown");
                       StrutsUtil.addMessage(errors, msg, "AccessUnknown");
                   }
                   return true;
               }
           }
       }
       //エラー無し
       return false;
   }

   /**
     * <br>[機  能] テキストボックス（数字タイプ）の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param targetNameTop 整数部
     * @param targetNameBottom 小数部
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTradeMoney(
            ActionErrors errors,
            String target,
            String targetName,
            String targetNameTop,
            String targetNameBottom) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        String money = String.valueOf(target);
        String money_top = "";
        String money_bottom = "";

        if (StringUtil.isNullZeroString(money)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //マイナス対応
        if (money.substring(0, 1).equals("-")) {
            money = money.substring(1);
        }

        if (money.indexOf(".") != -1) {
            money_top = money.substring(0, money.indexOf(".")).replaceAll(",", "");
            money_bottom = money.substring(money.indexOf(".") + 1);
        } else {
            money_top = money.replaceAll(",", "");
        }

        if (!ValidateUtil.isNumber(money_top)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.number.text");
            return true;
        }
        if (money_top.length() > GSConstFile.MAX_LENGTH_TRADE_MONEY_TOP) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", (targetName + " " + targetNameTop),
                    GSConstFile.MAX_LENGTH_TRADE_MONEY_TOP);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.length.text");
            return true;
        }
        if (money_bottom != "") {
            if (!ValidateUtil.isNumber(money_bottom)) {
                //半角数字チェック
                msg = new ActionMessage(
                        "error.input.number.text", targetName);
                StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.number.text");
                return true;
            }
            if (money_bottom.length() > GSConstFile.MAX_LENGTH_TRADE_MONEY_BOTTOM) {
                //MAX桁チェック
                msg = new ActionMessage(
                        "error.input.length.text", (targetName + " " + targetNameBottom),
                        GSConstFile.MAX_LENGTH_TRADE_MONEY_BOTTOM);
                StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.length.text");
                return true;
            }
        }

        //入力エラー無し
        return false;
    }

}

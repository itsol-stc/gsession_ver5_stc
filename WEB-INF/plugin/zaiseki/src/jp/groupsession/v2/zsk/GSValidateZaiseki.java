package jp.groupsession.v2.zsk;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 在席管理の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateZaiseki {

    /**
     * <br>[機  能] 在席ステータスの区分値チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param status 在席ステータス
     * @param req リクエスト
     * @return ActionErrors
     */
    public static ActionErrors validateZskStatus(ActionErrors errors,
            int status,
            HttpServletRequest req) {

        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String msg2 = gsMsg.getMessage(req, "zsk.73");
        //選択区分値チェック
        if (status != GSConst.UIOSTS_ETC
            && status != GSConst.UIOSTS_IN
            && status != GSConst.UIOSTS_LEAVE) {
            msg = new ActionMessage("error.select.required.text", msg2);
            StrutsUtil.addMessage(errors, msg, "status");
        }

        return errors;
    }

    /**
     * <br>[機  能] 在席備考のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param biko 在席備考
     * @param req リクエスト
     * @return ActionErrors
     */
    public static ActionErrors validateZskBiko(ActionErrors errors,
            String biko,
            HttpServletRequest req) {

        ActionMessage msg = null;

        //未入力はOK
        if (StringUtil.isNullZeroString(biko)) {
            return errors;
        }

        GsMessage gsMsg = new GsMessage();
        String msg2 = gsMsg.getMessage(req, "cmn.comment");
        //MAX桁チェック
        if (biko.length() > GSConst.MAX_LENGTH_ZSKBIKO) {
            msg = new ActionMessage("error.input.length.text",
                                    msg2,
                                    GSConst.MAX_LENGTH_ZSKBIKO);
            StrutsUtil.addMessage(errors, msg, "biko");
            return errors;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(biko)) {
            msg = new ActionMessage("error.input.spase.only", msg2);
            StrutsUtil.addMessage(errors, msg, "biko");
            return errors;
        }

        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(biko)) {
            msg =
                new ActionMessage("error.input.spase.start", msg2);
            StrutsUtil.addMessage(errors, msg, "biko");
            return errors;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(biko)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(biko);
            msg =
                new ActionMessage(
                        "error.input.njapan.text", msg2, nstr);
            StrutsUtil.addMessage(errors, msg, "biko");
            return errors;
        }
        return errors;
    }
    /**
     * <br>[機  能] 座席表名称の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param name タイトル
     * @param checkObject チェック対象
     * @param maxLength 最大文字数
     * @return ActionErrors
     */
    public static ActionErrors validateName(
        ActionErrors errors,
        String name,
        String checkObject,
        int maxLength) {

        ActionMessage msg = null;

        //未入力チェック
        if (StringUtil.isNullZeroString(name)) {
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }

        //MAX桁チェック
        if (name.length() > maxLength) {
            msg = new ActionMessage(
                    "error.input.length.text", checkObject, String.valueOf(maxLength));
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //スペースのみチェック
        if (ValidateUtil.isSpace(name)) {
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(name)) {
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(name)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(name);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        return errors;
    }
    /**
     * <br>[機  能] 座席表名称の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param name タイトル
     * @param checkObject チェック対象
     * @param maxLength 最大文字数
     * @param req リクエスト
     * @return ActionErrors
     */
    public static ActionErrors validateSortNumber(
        ActionErrors errors,
        String name,
        String checkObject,
        int maxLength,
        HttpServletRequest req) {

        ActionMessage msg = null;

        //未入力チェック
        if (StringUtil.isNullZeroString(name)) {
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }

        //MAX桁チェック
        if (name.length() > maxLength) {
            msg = new ActionMessage(
                    "error.input.length.text", checkObject, String.valueOf(maxLength));
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        GsMessage gsMsg = new GsMessage();
        String message = gsMsg.getMessage(req, "zsk.71");

        //数値のみチェック
        if (!ValidateUtil.isNumber(name)) {
            msg = new ActionMessage("error.input.comp.text", checkObject, message);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        return errors;
    }
    /**
     * <br>[機  能] 座席表ファイルの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param fileName 座席表ファイル
     * @param checkObject チェック対象
     * @return ActionErrors
     */
    public static ActionErrors validateFile(
        ActionErrors errors,
        String fileName,
        String checkObject) {

        ActionMessage msg = null;

        //未入力チェック
        if (fileName == null) {
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }
        String imageFileName = fileName;
        if (StringUtil.isNullZeroString(imageFileName)) {
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }

        if (!Cmn110Biz.isExtensionOk(imageFileName)) {
            //BMP,JPG,JPEG,GIF,PNG以外のファイルならばエラー
            msg = new ActionMessage("error.select2.required.extent");
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        return errors;
    }

    /**
     * <br>[機  能] 画面に表示する添付ファイル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @return 画面表示用添付ファイル一覧
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public ArrayList<String> getTempFileName(String tempDir)
    throws IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List < String > fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<String> fileLblList = new ArrayList<String>();

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);

                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(fMdl.getFileName());
            }
        }
        return fileLblList;
    }

}
package jp.groupsession.v2.api.file.add;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.fil080.Fil080Form;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/addのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-add",
plugin = "file", name = "ファイル登録",
url = "/api/file/add.do",
reqtype = "POST")
public class ApiFileAddForm extends AbstractApiForm {

    /** 親ディレクトリSID */
    @ApiParam(name = "fdrParentSid", viewName = "親ディレクトリSID")
    private String fdrParentSid__ = null;
    /** ファイル */
    @ApiParam(name = "uploadFile", viewName = "添付ファイル情報")
    private FormFile uploadFile__ = null;
    /** キャビネットSID */
    @ApiParam(name = "fcbSid", viewName = "キャビネットSID")
    private String fcbSid__ = null;
    /** 取引年月日 */
    @ApiParam(name = "fdrErrlDate", viewName = "取引年月日")
    private String fdrErrlDate__ = null;
    /** 取引先 */
    @ApiParam(name = "fdrErrlTarget", viewName = "取引先")
    private String fdrErrlTarget__ = null;
    /** 取引金額 */
    @ApiParam(name = "fdrErrlMoney", viewName = "取引金額")
    private String fdrErrlMoney__ = null;
    /** 外貨名 */
    @ApiParam(name = "fdrErrlMoneyType", viewName = "外貨名")
    private String fdrErrlMoneyType__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param umodel ユーザ情報モデル
     * @param gsMsg GsMessage
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    public ActionErrors validateFileUpload(
            Connection con, BaseUserModel umodel, GsMessage gsMsg,
            RequestModel reqMdl)
    throws SQLException, IOToolsException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //親ディレクトリSIDとキャビネットSIDの両方は未入力は禁止
        if (StringUtil.isNullZeroString(fdrParentSid__) && StringUtil.isNullZeroString(fcbSid__)) {
            msg = new ActionMessage("error.file.input.sid");
            StrutsUtil.addMessage(errors, msg, "error.file.input.sid");
            return errors;
        } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(uploadFile__.getFileName())) {
            //利用不可能な文字を入力した場合
            String elmName = gsMsg.getMessage("cmn.form.temp") + gsMsg.getMessage("cmn.name3");
            String str = GSValidateUtil.getNotGsJapaneaseStringTextArea(uploadFile__.getFileName());
            msg = new ActionMessage("error.input.njapan.text", elmName, str);
            StrutsUtil.addMessage(errors, msg, "fdrParentSid");
        }
        if (errors.size() > 0) {
            return errors;
        }

        //親ディレクトリSID，キャビネットSIDのチェック
        errors = __checkDirectory(con, reqMdl);
        if (errors.size() > 0) {
            return errors;
        }

        return errors;
    }

    /**
     * <br>[機  能] 親ディレクトリSID, キャビネットSIDの確認を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    private ActionErrors __checkDirectory(
            Connection con, RequestModel reqMdl) throws SQLException, IOToolsException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        FileCabinetModel fcbMdl = null;
        boolean autoSortCabFlg = false;
        if (!StringUtil.isNullZeroString(fdrParentSid__)
                && !StringUtil.isNullZeroString(fcbSid__)) {
            msg = new ActionMessage(
                    "error.both.input.user.group",
                    gsMsg.getMessage("api.cmn.parameter"),
                    "fdrParentSid",
                    "fcbSid");
            StrutsUtil.addMessage(errors, msg, "error.both.input.user.group");
            return errors;
        }
        if (StringUtil.isNullZeroString(fdrParentSid__)
                 && StringUtil.isNullZeroString(fcbSid__)) {
            msg = new ActionMessage(
                     "error.both.input.user.group",
                     gsMsg.getMessage("api.cmn.parameter"),
                     "fdrParentSid",
                     "fcbSid");
            StrutsUtil.addMessage(errors, msg, "error.both.input.user.group");
            return errors;
        }
        //親ディレクトリが入力されている場合
        if (!StringUtil.isNullZeroString(fdrParentSid__)) {

            fcbMdl = fileBiz.getCabinetModel(NullDefault.getInt(fdrParentSid__, 0));

            //指定したディレクトリを格納しているキャビネットが削除 or 論理削除済みになっている
            if (fcbMdl == null) {
                String textEditCabinet = gsMsg.getMessage("cmn.edit.folder");
                String textAdd = gsMsg.getMessage("cmn.add");
                msg = new ActionMessage("error.edit.power.user",
                        textEditCabinet, textAdd);
                StrutsUtil.addMessage(errors, msg, "fdrParentSid");
                return errors;
            }
            //親ディレクトリが振り分け機能有効の電帳法キャビネットに対して通常登録を実行した
            if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON
                    && fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.file.add.notsort"),
                        "error.file.add.notsort");
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.input.required.text",
                                "fcbSid"),
                        "error.input.required.text");

                return errors;
            }
        }
        //キャビネットSIDが入力されている場合
        if (!StringUtil.isNullZeroString(fcbSid__)) {
            autoSortCabFlg = true;
            FileCabinetDao fcbDao = new FileCabinetDao(con);
            fcbMdl = fcbDao.select(NullDefault.getInt(fcbSid__, -1));

            //指定したディレクトリを格納しているキャビネットが削除 or 論理削除済みになっている
            if (fcbMdl == null
                    || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
                String textEditCabinet = gsMsg.getMessage("cmn.edit.folder");
                String textAdd = gsMsg.getMessage("cmn.add");
                msg = new ActionMessage("error.edit.power.user",
                        textEditCabinet, textAdd);
                StrutsUtil.addMessage(errors, msg, "fdrParentSid");
                return errors;
            }
            //fcbSidで指定したキャビネットが振り分け機能を有効にした電帳法キャビネットではない
            if (fcbMdl.getFcbSortFolder() != GSConstFile.SORT_FOLDER_USE) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.file.add.autosort"),
                        "error.file.add.autosort");
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.input.required.text",
                                "fdrParentSid"),
                        "error.input.required.text");

                return errors;
            }
        }


        int errorSize = errors.size();

        //電帳法対応しているときはファイル形式の判定
        if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
            String fileName = uploadFile__.getFileName();
             //ファイル名がNULLまたは空の場合は処理しない
            if (StringUtil.isNullZeroString(fileName) == false) {
                String strExt = StringUtil.getExtension(fileName);
                //拡張子が想定したものと違った場合はエラー
                if (strExt == null || (
                        !strExt.toUpperCase().equals(".PNG")
                        && !strExt.toUpperCase().equals(".JPG")
                        && !strExt.toUpperCase().equals(".JPEG")
                        && !strExt.toUpperCase().equals(".PDF"))) {
                    msg = new ActionMessage(
                            "error.select.required.text",
                            gsMsg.getMessage("cmn.errl.file.format"));
                    StrutsUtil.addMessage(errors, msg, "inputFile.error.select.required.text");


                    return errors;
                }
            }

            //取引年月日
            if (StringUtil.isNullZeroString(fdrErrlDate__)) {
                msg = new ActionMessage(
                        "error.input.required.text", gsMsg.getMessage("fil.fil080.8"));
                StrutsUtil.addMessage(errors, msg, "error.input.required.date");
            } else if (!ValidateUtil.isSlashDateFormat(fdrErrlDate__)
                    || !ValidateUtil.isExistDateYyyyMMdd(fdrErrlDate__.replaceAll("/", ""))) {
                msg = new ActionMessage("error.input.notfound.date",
                        gsMsg.getMessage("fil.fil080.8"));
                errors.add("error.input.notfound.date", msg);
            }

            //取引先
            String targetName = gsMsg.getMessage("fil.fil030.18");
            GSValidateCommon.validateTextField(errors,
                    fdrErrlTarget__,
                    targetName, targetName, 50, true);

            //取引金額
            if (StringUtil.isNullZeroString(fdrErrlMoney__) == false) {
                GSValidateFile.validateTradeMoney(
                    errors,
                    fdrErrlMoney__,
                    gsMsg.getMessage("fil.fil080.5"),
                    gsMsg.getMessage("fil.fil300.7"),
                    gsMsg.getMessage("fil.fil300.8"));

                //外貨名
                FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con);
                String textGaika = gsMsg.getMessage("fil.fil310.2");
                if (StringUtil.isNullZeroString(fdrErrlMoneyType__)) {
                    msg = new ActionMessage(
                            "error.input.required.text",
                            textGaika);
                    StrutsUtil.addMessage(errors, msg,
                                        "fdrErrlMoneyType.error.input.required.texte");
                } else if (!StringUtil.isNullZeroString(fdrErrlMoneyType__)) {
                    if (!fmmDao.existGaikaName(fdrErrlMoneyType__)) {
                        msg = new ActionMessage("search.data.notfound", textGaika);
                        StrutsUtil.addMessage(errors, msg, "search.data.notfound.moneytype");
                    }
                }

            }
        }

        //ファイルサイズ
        BigDecimal fileSize = new BigDecimal(uploadFile__.getFileSize());

        //ファイルサイズのチェック
        GSValidateFile.validateFileSizeOver(errors, con, fileSize, reqMdl);

        if (errors.size() != errorSize) {
            return errors;
        }
        //キャビネット添付ファイルサイズチェック
        GSValidateFile.validateUsedDiskSizeOverValue(errors, fcbMdl.getFcbSid(), con, fileSize);
        if (errors.size() != errorSize) {
            return errors;
        }

        //アクセス権限チェック
        boolean canAccessFlg;
        //自動振り分け機能が有効なキャビネットはルートディレクトリの編集権限でチェックする
        if (autoSortCabFlg) {
            canAccessFlg =
                    (fileBiz.checkPowEditRootDir(fcbMdl.getFcbSid())
                        == FilCommonBiz.ERR_NONE);
        //自動振り分け機能が無効なキャビネットはparentDirSidで指定されたディレクトリの編集権限でチェックする
        } else {
            canAccessFlg =
                    (fileBiz.checkPowEditDir(NullDefault.getInt(fdrParentSid__, -1),
                            GSConstFile.DIRECTORY_KBN_FOLDER)
                            == FilCommonBiz.ERR_NONE);
        }
        if (canAccessFlg == false) {
            //キャビネットへの書込み権限なし
            String textEditCabinet = gsMsg.getMessage("cmn.edit.folder");
            String textAdd = gsMsg.getMessage("cmn.add");
            msg = new ActionMessage("error.edit.power.user",
                    textEditCabinet, textAdd);
            StrutsUtil.addMessage(errors, msg, "fdrParentSid");
        }

        return errors;
    }

    /**
     * @return fdrParentSid
     */
    public String getFdrParentSid() {
        return fdrParentSid__;
    }

    /**
     * @param fdrParentSid 設定する fdrParentSid
     */
    public void setFdrParentSid(String fdrParentSid) {
        fdrParentSid__ = fdrParentSid;
    }

    /**
     * @return uploadFile
     */
    public FormFile getUploadFile() {
        return uploadFile__;
    }

    /**
     * @param uploadFile 設定する uploadFile
     */
    public void setUploadFile(FormFile uploadFile) {
        uploadFile__ = uploadFile;
    }

    /**
     * <p>fcbSid を取得します。
     * @return fcbSid
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fcbSid__
     */
    public String getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>fcbSid をセットします。
     * @param fcbSid fcbSid
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fcbSid__
     */
    public void setFcbSid(String fcbSid) {
        fcbSid__ = fcbSid;
    }

    /**
     * <p>fdrErrlDate を取得します。
     * @return fdrErrlDate
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fdrErrlDate__
     */
    public String getFdrErrlDate() {
        return fdrErrlDate__;
    }

    /**
     * <p>fdrErrlDate をセットします。
     * @param fdrErrlDate fdrErrlDate
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fdrErrlDate__
     */
    public void setFdrErrlDate(String fdrErrlDate) {
        fdrErrlDate__ = fdrErrlDate;
    }

    /**
     * <p>fdrErrlTarget を取得します。
     * @return fdrErrlTarget
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fdrErrlTarget__
     */
    public String getFdrErrlTarget() {
        return fdrErrlTarget__;
    }

    /**
     * <p>fdrErrlTarget をセットします。
     * @param fdrErrlTarget fdrErrlTarget
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fdrErrlTarget__
     */
    public void setFdrErrlTarget(String fdrErrlTarget) {
        fdrErrlTarget__ = fdrErrlTarget;
    }

    /**
     * <p>fdrErrlMoney を取得します。
     * @return fdrErrlMoney
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fdrErrlMoney__
     */
    public String getFdrErrlMoney() {
        return fdrErrlMoney__;
    }

    /**
     * <p>fdrErrlMoney をセットします。
     * @param fdrErrlMoney fdrErrlMoney
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fdrErrlMoney__
     */
    public void setFdrErrlMoney(String fdrErrlMoney) {
        fdrErrlMoney__ = fdrErrlMoney;
    }

    /**
     * <p>fdrErrlMoneyType を取得します。
     * @return fdrErrlMoneyType
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fdrErrlMoneyType__
     */
    public String getFdrErrlMoneyType() {
        return fdrErrlMoneyType__;
    }

    /**
     * <p>fdrErrlMoneyType をセットします。
     * @param fdrErrlMoneyType fdrErrlMoneyType
     * @see jp.groupsession.v2.api.file.add.ApiFileAddForm#fdrErrlMoneyType__
     */
    public void setFdrErrlMoneyType(String fdrErrlMoneyType) {
        fdrErrlMoneyType__ = fdrErrlMoneyType;
    }

}

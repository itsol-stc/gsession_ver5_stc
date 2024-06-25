package jp.groupsession.v2.api.file.update;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.file.delete.ApiFileDeleteBiz;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.fil080.Fil080Form;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/updateのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-update",
plugin = "file", name = "ファイル更新",
url = "/api/file/update.do",
reqtype = "POST")
public class ApiFileUpdateForm extends AbstractApiForm {

    /** 取引金額区分 金額なし */
    public static final int MONEYKBN_NO = 0;
    /** 取引金額区分 金額あり */
    public static final int MONEYKBN_YES = 1;

    /** ディレクトリSID */
    @ApiParam(name = "fdrSid", viewName = "ディレクトリSID")
    private String fdrSid__ = null;
    /** ファイル */
    @ApiParam(name = "uploadFile", viewName = "添付ファイル情報")
    private FormFile uploadFile__ = null;
    /** 取引年月日 */
    @ApiParam(name = "fdrErrlDate", viewName = "取引年月日")
    private String fdrErrlDate__ = null;
    /** 取引先 */
    @ApiParam(name = "fdrErrlTarget", viewName = "取引先")
    private String fdrErrlTarget__ = null;
    /** 取引金額 */
    @ApiParam(name = "fdrErrlMoney", viewName = "取引金額")
    private String fdrErrlMoney__;
    /** 外貨名 */
    @ApiParam(name = "fdrErrlMoneyType", viewName = "外貨名")
    private String fdrErrlMoneyType__ = null;

    /** キャビネットSID(入力チェック用) */
    private int fcbSid__ = 0;
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    public ActionErrors validateFileUpdate(Connection con, RequestModel reqMdl)
    throws SQLException, IOToolsException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg  = new GsMessage(reqMdl);
        ActionMessage msg = null;
        ApiFileDeleteBiz fileDelBiz = new ApiFileDeleteBiz(reqMdl, con);

        ApiFileUpdateBiz updateBiz = new ApiFileUpdateBiz(reqMdl, con);
        if (StringUtil.isNullZeroString(fdrSid__)) {
            //未入力
            String textDirSid = gsMsg.getMessage("fil.111");
            msg = new ActionMessage("error.input.required.text", textDirSid);
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (!ValidateUtil.isNumber(fdrSid__)) {
            //数字チェック
            String textDirSid = gsMsg.getMessage("fil.111");
            msg = new ActionMessage(
                    "error.input.number.hankaku", textDirSid);
            StrutsUtil.addMessage(errors, msg, "fdrSid");
        }
        if (!errors.isEmpty()) {
            return errors;
        }

        int fdrSid = NullDefault.getInt(fdrSid__, -1);
        if (!__isExistFile(con)) {
            //ファイル存在チェック
            String textFile = gsMsg.getMessage("fil.92");
            msg = new ActionMessage(
                    "search.notfound.tdfkcode", textFile);
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (!__validateWrite(con, reqMdl)) {
            //キャビネットへの書込み権限なし
            String textEdit = gsMsg.getMessage("cmn.edit");
            String textCabinet = gsMsg.getMessage("fil.51");

            msg = new ActionMessage("error.edit.power.user",
                    textCabinet, textEdit);
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (fileDelBiz.checkFileLock(fdrSid)) {
            //ファイルロック
            msg = new ActionMessage("error.file.lock");
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        }
        if (!errors.isEmpty()) {
            return errors;
        }

        boolean errlFlg = updateBiz.isErrlCabint(fdrSid);

        //ファイル選択を必須とする
        if (uploadFile__ == null || StringUtil.isNullZeroString(uploadFile__.getFileName())) {
            msg = new ActionMessage("error.select.required.text", gsMsg.getMessage("cmn.file"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
            return errors;

        } else {
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(uploadFile__.getFileName())) {
                //利用不可能な文字を入力した場合
                String elmName = gsMsg.getMessage("cmn.form.temp") + gsMsg.getMessage("cmn.name3");
                String str =
                        GSValidateUtil.getNotGsJapaneaseStringTextArea(uploadFile__.getFileName());
                msg = new ActionMessage("error.input.njapan.text", elmName, str);
                StrutsUtil.addMessage(errors, msg, "fdrSid");

            } else if (errlFlg && uploadFile__.getFileName().length() > 100) {
                //ファイル名が100文字を超えていた時
                String elmName = gsMsg.getMessage("cmn.form.temp") + gsMsg.getMessage("cmn.name3");
                msg = new ActionMessage("error.input.length.text", elmName);
                StrutsUtil.addMessage(errors, msg, "error.input.length.text");
            } else {
                //ファイルサイズ
                BigDecimal fileSize = new BigDecimal(uploadFile__.getFileSize());

                //ファイルサイズのチェック
                boolean errorFlg = GSValidateFile.validateFileSizeOver(
                                                    errors, con, fileSize, reqMdl);

                if (!errorFlg) {
                    //キャビネット容量チェック
                    GSValidateFile.validateUsedDiskSizeOverValue(errors, fcbSid__, con, fileSize);
                }
            }
        }

        //電帳法対応しているときはファイル形式の判定
        if (errlFlg) {
            if (uploadFile__ != null && uploadFile__.getFileName() != null) {
                String fileName = uploadFile__.getFileName();
                List<String> extension =
                        new ArrayList<String>(Arrays.asList(".PNG", ".JPG", ".JPEG", ".PDF"));
                String strExt = StringUtil.getExtension(fileName);
                //拡張子が想定したものと違った場合はエラー
                if (strExt == null || !extension.contains(strExt.toUpperCase())) {
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
                        "error.input.required.text",
                        gsMsg.getMessage("fil.fil080.8"));
                StrutsUtil.addMessage(errors, msg, "fdrErrlDate.error.input.required.texte");

            } else if (!ValidateUtil.isSlashDateFormat(fdrErrlDate__)
                || !ValidateUtil.isExistDateYyyyMMdd(fdrErrlDate__.replaceAll("/", ""))) {

                msg = new ActionMessage(
                        "error.input.comp.text",
                        gsMsg.getMessage("fil.fil080.8"), gsMsg.getMessage("cmn.format.date2"));
                StrutsUtil.addMessage(errors, msg, "error.input.required.date");
            }

            //取引先
            String targetName = gsMsg.getMessage("fil.fil030.18");
            GSValidateFile.validateTextBoxInput(
                    errors, fdrErrlTarget__, targetName, 50, true);
            if (!StringUtil.isNullZeroString(fdrErrlTarget__)
                    && ValidateUtil.isTab(fdrErrlTarget__)) {
                msg = new ActionMessage("error.input.tab.text", gsMsg.getMessage("fil.fil080.8"));
                StrutsUtil.addMessage(errors, msg, "error.input.tab.targetName");
            }

            //取引金額
            if (!StringUtil.isNullZeroString(fdrErrlMoney__)) {
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

        return errors;
    }

    /**
     * <br>[機  能] ユーザがディレクトリへの書込み権限を持っているか判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    private boolean __validateWrite(Connection con, RequestModel reqMdl)
    throws SQLException {

        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        int fdrSid = NullDefault.getInt(fdrSid__, -1);
        fcbSid__ = fileBiz.getCabinetSid(fdrSid);
        if (fcbSid__ <= 0) {
            return false;
        }

        return fileBiz.isDirAccessAuthUser(fcbSid__,
                fdrSid,
                Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
    }

    /**
     * <br>[機  能] ファイル存在するか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    private boolean __isExistFile(Connection con) throws SQLException {

        FileDirectoryDao dao = new FileDirectoryDao(con);
        FileDirectoryModel model = dao.getNewDirectory(NullDefault.getInt(fdrSid__, 0));
        if (model == null || model.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        }
        if (model.getFdrKbn() == GSConstFile.DIRECTORY_FOLDER) {
            return false;
        }

        return true;
    }

    /**
     * @return fdrSid
     */
    public String getFdrSid() {
        return fdrSid__;
    }

    /**
     * @param fdrSid 設定する fdrSid
     */
    public void setFdrSid(String fdrSid) {
        fdrSid__ = fdrSid;
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
     * <p>fdrErrlDate を取得します。
     * @return fdrErrlDate
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateForm#fdrErrlDate__
     */
    public String getFdrErrlDate() {
        return fdrErrlDate__;
    }

    /**
     * <p>fdrErrlDate をセットします。
     * @param fdrErrlDate fdrErrlDate
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateForm#fdrErrlDate__
     */
    public void setFdrErrlDate(String fdrErrlDate) {
        fdrErrlDate__ = fdrErrlDate;
    }

    /**
     * <p>fdrErrlTarget を取得します。
     * @return fdrErrlTarget
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateForm#fdrErrlTarget__
     */
    public String getFdrErrlTarget() {
        return fdrErrlTarget__;
    }

    /**
     * <p>fdrErrlTarget をセットします。
     * @param fdrErrlTarget fdrErrlTarget
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateForm#fdrErrlTarget__
     */
    public void setFdrErrlTarget(String fdrErrlTarget) {
        fdrErrlTarget__ = fdrErrlTarget;
    }

    /**
     * <p>fdrErrlMoney を取得します。
     * @return fdrErrlMoney
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateForm#fdrErrlMoney__
     */
    public String getFdrErrlMoney() {
        return fdrErrlMoney__;
    }

    /**
     * <p>fdrErrlMoney をセットします。
     * @param fdrErrlMoney fdrErrlMoney
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateForm#fdrErrlMoney__
     */
    public void setFdrErrlMoney(String fdrErrlMoney) {
        fdrErrlMoney__ = fdrErrlMoney;
    }

    /**
     * <p>fdrErrlMoneyType を取得します。
     * @return fdrErrlMoneyType
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateForm#fdrErrlMoneyType__
     */
    public String getFdrErrlMoneyType() {
        return fdrErrlMoneyType__;
    }

    /**
     * <p>fdrErrlMoneyType をセットします。
     * @param fdrErrlMoneyType fdrErrlMoneyType
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateForm#fdrErrlMoneyType__
     */
    public void setFdrErrlMoneyType(String fdrErrlMoneyType) {
        fdrErrlMoneyType__ = fdrErrlMoneyType;
    }

}

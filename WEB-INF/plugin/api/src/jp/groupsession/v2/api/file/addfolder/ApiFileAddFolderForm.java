package jp.groupsession.v2.api.file.addfolder;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/addfolderのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-addfolder",
plugin = "file", name = "フォルダ登録",
url = "/api/file/addfolder.do",
reqtype = "POST")
public class ApiFileAddFolderForm extends AbstractApiForm {

    /** 親ディレクトリSID */
    @ApiParam(name = "fdrParentSid", viewName = "親ディレクトリSID")
    private String fdrParentSid__ = null;
    /** フォルダ名 */
    @ApiParam(name = "fdrName", viewName = "フォルダ名")
    private String fdrName__ = null;
    /** 備考 */
    private String fdrNote__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GsMessage
     * @param reqMdl リクエストモデル
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    public ActionErrors validateFileUpload(Connection con, GsMessage gsMsg,
                                            RequestModel reqMdl)
    throws SQLException, IOToolsException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        if (StringUtil.isNullZeroString(fdrParentSid__)) {
            //未入力
            msg = new ActionMessage(
                    "error.input.required.text",
                    gsMsg.getMessage(GSConstApi.TEXT_PARENT_DIRECTORY_SID));
            StrutsUtil.addMessage(errors, msg, "fdrParentSid");

        } else if (!ValidateUtil.isNumber(fdrParentSid__)) {
            //数字チェック
            msg = new ActionMessage(
                    "error.input.number.hankaku",
                    gsMsg.getMessage(GSConstApi.TEXT_PARENT_DIRECTORY_SID));
            StrutsUtil.addMessage(errors, msg, "fdrParentSid.error.input.number.hankaku");

        } else if (__checkDirData(con, gsMsg, errors)) {

            //キャビネットSIDを取得
            int parentFdrSid = NullDefault.getInt(fdrParentSid__, -1);
            int fcbSid = fileBiz.getCabinetSid(parentFdrSid);

            if (fcbSid < 1 || !fileBiz.isDirAccessAuthUser(fcbSid,
                    parentFdrSid,
                    Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE))) {
                //キャビネットへの書込み権限なし
                msg = new ActionMessage("error.edit.power.user",
                                        gsMsg.getMessage("cmn.edit.folder"),
                                        gsMsg.getMessage("fil.123"));
                StrutsUtil.addMessage(errors, msg, "fdrParentSid.error.edit.power.user");
            }

        }

        //フォルダ名
        GSValidateFile.validateTextBoxInput(errors,
                                         fdrName__,
                                         gsMsg.getMessage("fil.21"),
                                         GSConstFile.MAX_LENGTH_FOLDER_NAME,
                                         true);

        //備考
        GSValidateFile.validateTextarea(errors,
                                         fdrNote__,
                                         gsMsg.getMessage("cmn.memo"),
                                         GSConstFile.MAX_LENGTH_FOLDER_BIKO,
                                         false);

        return errors;
    }

    /**
     * <br>[機  能] フォルダの存在チェック、階層チェックを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GsMessage
     * @param errors ActionErrors
     * @return true:正常 false:エラーあり
     * @throws SQLException SQL実行時例外
     */
    private boolean __checkDirData(Connection con, GsMessage gsMsg,
                                    ActionErrors errors)
    throws SQLException {

        ActionMessage msg = null;
        FileDirectoryDao dao = new FileDirectoryDao(con);
        FileDirectoryModel model = dao.getNewDirectory(NullDefault.getInt(fdrParentSid__, 0));

        int errorCnt = errors.size();
        if (model == null || model.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
            //ディレクトリ存在チェック
            msg = new ActionMessage(
                    "search.notfound.tdfkcode",
                    gsMsg.getMessage(GSConstApi.TEXT_SELECT_FOLDER));
            StrutsUtil.addMessage(errors, msg, "fdrParentSid.search.notfound.tdfkcode");

        } else if (model.getFdrLevel() >= GSConstFile.DIRECTORY_LEVEL_10) {
            //ディレクトリ階層チェック
            msg = new ActionMessage(
                    "error.over.level.create.dir", GSConstFile.DIRECTORY_LEVEL_10);
            StrutsUtil.addMessage(errors, msg, "fdrParentSid.error.over.level.create.dir");
        }

        return errorCnt == errors.size();
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
     * <p>fdrName を取得します。
     * @return fdrName
     */
    public String getFdrName() {
        return fdrName__;
    }

    /**
     * <p>fdrName をセットします。
     * @param fdrName fdrName
     */
    public void setFdrName(String fdrName) {
        fdrName__ = fdrName;
    }

    /**
     * <p>fdrNote を取得します。
     * @return fdrNote
     */
    public String getFdrNote() {
        return fdrNote__;
    }

    /**
     * <p>fdrNote をセットします。
     * @param fdrNote fdrNote
     */
    public void setFdrNote(String fdrNote) {
        fdrNote__ = fdrNote;
    }


}

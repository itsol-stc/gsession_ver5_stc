package jp.groupsession.v2.api.file.delete;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/deleteのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-delete",
plugin = "file", name = "ファイル削除",
url = "/api/file/delete.do",
reqtype = "DELET")
public class ApiFileDeleteForm extends AbstractApiForm {

    /** ディレクトリSID（ファイル） */
    @ApiParam(name = "fdrSid", viewName = "ディレクトリSID")
    private String fdrSid__ = null;
    /** 操作コメント */
    @ApiParam(name = "comment", viewName = "操作コメント")
    private String comment__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GsMessage
     * @param reqMdl リクエストモデル
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateFileUpload(Connection con, GsMessage gsMsg,
                                            RequestModel reqMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        ApiFileDeleteBiz biz = new ApiFileDeleteBiz(reqMdl, con);


        if (StringUtil.isNullZeroString(fdrSid__)) {
            //未入力
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(GSConstApi.TEXT_DIRECTORY_SID));
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (!ValidateUtil.isNumber(fdrSid__)) {
            //数字チェック
            msg = new ActionMessage(
                    "error.input.number.hankaku",
                    gsMsg.getMessage(GSConstApi.TEXT_DIRECTORY_SID));
                StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (!__isExistFile(con)) {
            //ファイル存在チェック
            msg = new ActionMessage(
                    "search.notfound.tdfkcode",
                    gsMsg.getMessage(GSConstApi.TEXT_SELECT_FILE));
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (biz.checkFileLock(NullDefault.getInt(fdrSid__, 0))) {
            //ファイルロック
            msg = new ActionMessage(
                    "error.file.lock.name",
                    gsMsg.getMessage(GSConstApi.TEXT_SELECT_FILE));
                StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else {
            int parentFdrSid = NullDefault.getInt(fdrSid__, -1);
            int cabinetSid = fileBiz.getCabinetSid(parentFdrSid);
            if (cabinetSid < 1 || !fileBiz.isDirAccessAuthUser(cabinetSid,
                    parentFdrSid,
                    Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE))) {
                //ファイルへの書込み権限なし
                msg = new ActionMessage("error.cant.delete.filekanri.file");
                StrutsUtil.addMessage(errors, msg, "fdrSid.error.cant.delete.filekanri.file");
            }
        }

        //操作コメント 入力チェック
        if (!StringUtil.isNullZeroString(comment__)) {
            //MAX桁チェック
            if (comment__.length() > GSConstFile.MAX_LENGTH_FILE_UP_CMT) {
                msg = new ActionMessage("error.input.length.textarea",
                                        gsMsg.getMessage("fil.11"),
                                        GSConstFile.MAX_LENGTH_FILE_UP_CMT);
                StrutsUtil.addMessage(errors, msg, "error.input.length.textarea." + "comment");
            }
            //スペース(改行)のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(comment__)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                                    gsMsg.getMessage("fil.11"));
                StrutsUtil.addMessage(errors, msg, "error.input.spase.cl.only." + "comment");
            }
            //JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(comment__)) {
                //利用不可能な文字を入力した場合
                msg = new ActionMessage("error.input.njapan.text3",
                                        gsMsg.getMessage("fil.11"));
                StrutsUtil.addMessage(errors, msg, "error.input.njapan.text3." + "comment");
            }
        }

        return errors;
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
        if (model == null) {
            return false;
        }
        if (model.getFdrKbn() == GSConstFile.DIRECTORY_FOLDER) {
            return false;
        }

        if (model.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
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
     * @return comment
     */
    public String getComment() {
        return comment__;
    }

    /**
     * @param comment 設定する comment
     */
    public void setComment(String comment) {
        comment__ = comment;
    }
}

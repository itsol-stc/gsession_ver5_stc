package jp.groupsession.v2.api.file.dirlist;

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
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/dirlistのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-dirlist",
plugin = "file", name = "ディレクトリ一覧取得",
url = "/api/file/dirlist.do",
reqtype = "GET")
public class ApiDirListForm extends AbstractApiForm {

    /** ディレクトリSID */
    @ApiParam(name = "fdrSid", viewName = "ディレクトリSID")
    private String fdrSid__ = null;

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
    public ActionErrors validateDirList(Connection con, GsMessage gsMsg,
                                        RequestModel reqMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);

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

        } else if (!__isExistDir(con)) {
            //ディレクトリ存在チェック
            msg = new ActionMessage(
                    "search.notfound.tdfkcode",
                    gsMsg.getMessage(GSConstApi.TEXT_SELECT_FOLDER));
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else {
            int fdrSid = NullDefault.getInt(fdrSid__, -1);
            int cabinetSid = fileBiz.getCabinetSid(fdrSid);
            if (!fileBiz.isDirAccessAuthUser(cabinetSid, fdrSid,
                    -1)) {
                //ディレクトリへの閲覧権限なし
                msg = new ActionMessage("error.edit.power.user",
                        gsMsg.getMessage("cmn.reading"),
                        gsMsg.getMessage("cmn.reading"));
                StrutsUtil.addMessage(errors, msg, "fcbSid");
            }

        }
        return errors;
    }

    /**
     * <br>[機  能] フォルダ存在するか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    private boolean __isExistDir(Connection con) throws SQLException {

        FileDirectoryDao dao = new FileDirectoryDao(con);
        FileDirectoryModel model = dao.getNewDirectory(NullDefault.getInt(fdrSid__, 0));
        if (model == null) {
            return false;
        }
        if (model.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
            return false;
        }

        return true;
    }

    /**
     * <p>fdrSid を取得します。
     * @return fdrSid
     */
    public String getFdrSid() {
        return fdrSid__;
    }

    /**
     * <p>fdrSid をセットします。
     * @param fdrSid fdrSid
     */
    public void setFdrSid(String fdrSid) {
        fdrSid__ = fdrSid;
    }
}

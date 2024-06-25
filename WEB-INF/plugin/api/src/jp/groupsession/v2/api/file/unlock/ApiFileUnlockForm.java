package jp.groupsession.v2.api.file.unlock;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/unlockのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-unlock",
plugin = "file", name = "ファイルロック解除",
url = "/api/file/unlock.do",
reqtype = "POST")
public class ApiFileUnlockForm extends AbstractApiForm {

    /** ディレクトリSID */
    @ApiParam(name = "fdrSid", viewName = "ディレクトリSID")
    private String fdrSid__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param gsMsg GsMessage
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateFileLock(
            Connection con, RequestModel reqMdl, GsMessage gsMsg)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(fdrSid__)) {
            String textDirSid = gsMsg.getMessage("fil.111");
            //未入力
            msg = new ActionMessage("error.input.required.text", textDirSid);
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (!ValidateUtil.isNumber(fdrSid__)) {
            String textDirSid = gsMsg.getMessage("fil.111");
            //数字チェック
            msg = new ActionMessage(
                    "error.input.number.hankaku", textDirSid);
                StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (!__isExistFile(con)) {
            String textFile = gsMsg.getMessage("fil.92");
            //ファイル存在チェック
            msg = new ActionMessage(
                    "search.notfound.tdfkcode", textFile);
            StrutsUtil.addMessage(errors, msg, "fdrSid");

        } else if (!__isCanFileUnlockUser(con, reqMdl)) {
            //ファイルロック解除権限無し
            String textFile = gsMsg.getMessage("fil.48");
            msg = new ActionMessage("error.edit.power.user", textFile, textFile);
                StrutsUtil.addMessage(errors, msg, "fdrSid");
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

        return true;
    }

    /**
     * ユーザがキャビネット内のファイルロック解除が可能か判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    private boolean __isCanFileUnlockUser(Connection con, RequestModel reqMdl)
    throws SQLException {
        int fdrSid = NullDefault.getInt(fdrSid__, -1);
        if (fdrSid <= 0) {
            return false;
        }

        //認証ユーザの管理者判定
        BaseUserModel umodel = reqMdl.getSmodel();
        GroupDao gdao = new GroupDao(con);
        umodel.setAdminFlg(gdao.isBelongAdmin(umodel.getUsrsid()));
        reqMdl.setSmodel(umodel);

        //指定ディレクトリの保存先キャビネットを取得
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        int fcbSid = fileBiz.getCabinetSid(fdrSid);
        if (fcbSid <= 0) {
            return false;
        }

        //指定ファイルの編集権限、及びロック解除可能チェック
        if (!fileBiz.isCanFileUnlockUser(fcbSid)
                && !fileBiz.checkFileLock(fdrSid)) {
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

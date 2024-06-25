package jp.groupsession.v2.api.file.shortcut;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileShortcutConfDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileShortcutConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/shortcutのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-shortcut",
plugin = "file", name = "ショートカット設定",
url = "/api/file/shortcut.do",
reqtype = "POST")
public class ApiFileShortcutForm extends AbstractApiForm {

    /** ディレクトリSID */
    @ApiParam(name = "fdrSid", viewName = "ディレクトリSID")
    private String fdrSid__ = null;
    /** ショートカット区分 */
    @ApiParam(name = "shcKbn", viewName = "ショートカット区分")
    private String shcKbn__ = null;


    /**
     * <br>[機  能] チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @param usrSid ユーザSID
     * @return errors エラー
     * @throws NumberFormatException 実行例外
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateAccessCheck(Connection con, RequestModel reqMdl, int usrSid)
            throws NumberFormatException, SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        FilCommonBiz biz = new FilCommonBiz(reqMdl, con);
        // キャビネットSID取得
        int drSid = NullDefault.getInt(fdrSid__, -1);
        int fcbSid = biz.getCabinetSid(drSid);
        // 存在確認
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel myDir = dirDao.getNewDirectory(drSid);
        if (myDir == null || myDir.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            msg = new ActionMessage("search.data.notfound", gsMsg.getMessage("fil.118"));
            StrutsUtil.addMessage(errors, msg, "fdrSid__");
            return errors;
        }
        //権限チェック
        if (!biz.isDirAccessAuthUser(fcbSid,
                                     drSid,
                                     Integer.parseInt(GSConstFile.ACCESS_KBN_READ))) {
            msg = new ActionMessage("error.edit.power.user", gsMsg.getMessage("cmn.access"),
                    gsMsg.getMessage("cmn.edit"));
            StrutsUtil.addMessage(errors, msg, "fdrSid__");
            return errors;
        }
        // ショートカット登録・削除チェック
        FileShortcutConfDao dao = new FileShortcutConfDao(con);
        FileShortcutConfModel chkIsExistMdl = dao.select(drSid, usrSid);
        int addDeleteKbn = NullDefault.getInt(shcKbn__, -1);
        if (addDeleteKbn == GSConstFile.SHORTCUT_ADD) {
            if (chkIsExistMdl != null) {
                msg = new ActionMessage("error.shortcut.exist");
                StrutsUtil.addMessage(errors, msg, "fdrSid__");
            }
        } else if (addDeleteKbn == GSConstFile.SHORTCUT_DEL) {
            if (chkIsExistMdl == null) {
                msg = new ActionMessage("search.data.notfound", gsMsg.getMessage("fil.2"));
                StrutsUtil.addMessage(errors, msg, "fdrSid__");
            }
        } else {
            msg = new ActionMessage("error.input.notvalidate.data",
                    gsMsg.getMessage("cmn.entry") + gsMsg.getMessage("cmn.delete"));
            StrutsUtil.addMessage(errors, msg, "fdrSid__");
        }
        return errors;
    }

    /**
     * <p>fdrSid を取得します。
     * @return fdrSid
     * @see jp.groupsession.v2.api.file.shortcut.ApiFileShortcutForm#fdrSid__
     */
    public String getFdrSid() {
        return fdrSid__;
    }
    /**
     * <p>fdrSid をセットします。
     * @param fdrSid fdrSid
     * @see jp.groupsession.v2.api.file.shortcut.ApiFileShortcutForm#fdrSid__
     */
    public void setFdrSid(String fdrSid) {
        fdrSid__ = fdrSid;
    }

    /**
     * <p>shcKbn を取得します。
     * @return shcKbn
     * @see jp.groupsession.v2.api.file.shortcut.ApiFileShortcutForm#shcKbn__
     */
    public String getShcKbn() {
        return shcKbn__;
    }
    /**
     * <p>shcKbn をセットします。
     * @param shcKbn shcKbn
     * @see jp.groupsession.v2.api.file.shortcut.ApiFileShortcutForm#shcKbn__
     */
    public void setShcKbn(String shcKbn) {
        shcKbn__ = shcKbn;
    }



}

package jp.groupsession.v2.api.file.move;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil090.Fil090Biz;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/moveのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-move",
plugin = "file", name = "フォルダ・ファイル移動",
url = "/api/file/move.do",
reqtype = "POST")
public class ApiFileMoveForm extends AbstractApiForm {

    /** 移動ディレクトリSID */
    @ApiParam(name = "fdrSid", viewName = "ディレクトリSID")
    private String fdrSid__ = null;
    /** 移動先ディレクトリSID */
    @ApiParam(name = "fdrParentSid", viewName = "親ディレクトリSID", required = false, confRequired = true)
    private String fdrParentSid__ = null;
    /** 移動先キャビネットSID */
    @ApiParam(name = "fcbSid", viewName = "キャビネットSID", required = false, confRequired = true)
    private String fcbSid__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GsMessage
     * @param usrSid ユーザSID
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    public ActionErrors validateFileMove(Connection con, GsMessage gsMsg,
                                        int usrSid, RequestModel reqMdl)
    throws SQLException, IOToolsException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        FilCommonBiz biz = new FilCommonBiz(reqMdl, con);
        // 移動元ディレクトリSID確認
        int dirSid = NullDefault.getInt(fdrSid__, -1);
        if (dirSid == -1) {
            msg = new ActionMessage("error.select3.required.text",
                    gsMsg.getMessage("fil.120"));
            StrutsUtil.addMessage(errors, msg, "fdrSid");
            return errors;
        }
        // 移動元ディレクトリの情報取得
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel fromDirMdl = dirDao.getNewDirectory(dirSid);
        if (fromDirMdl == null || fromDirMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            msg = new ActionMessage("search.data.notfound", gsMsg.getMessage("fil.118"));
            StrutsUtil.addMessage(errors, msg, "fdrSid");
            return errors;
        }
        // 移動元ディレクトリキャビネット確認
        if (fromDirMdl.getFdrParentSid() == GSConstFile.DIRECTORY_ROOT) {
            msg = new ActionMessage("error.file.move.cabinet");
            StrutsUtil.addMessage(errors, msg, "fdrSid");
            return errors;
        }
        // 移動元ディレクトリSID編集権限確認
        int fcbSid = biz.getCabinetSid(dirSid);
        if (!biz.isDirAccessAuthUser(fcbSid,
                dirSid,
                NullDefault.getInt(GSConstFile.ACCESS_KBN_WRITE, -1))) {
            msg = new ActionMessage("error.edit.power.user", gsMsg.getMessage("cmn.edit"),
                    gsMsg.getMessage("cmn.move"));
            StrutsUtil.addMessage(errors, msg, "fdrSid");
            return errors;
        }
        // 移動元ディレクトリ:ロックファイル確認
        boolean fileErr = GSValidateFile.validateLockFile(errors,
                con,
                reqMdl,
                dirSid,
                fcbSid,
                usrSid,
                fromDirMdl);
        if (fileErr) {
            return errors;
        }
        // 移動先ディレクトリSID
        int parentDirSid = NullDefault.getInt(fdrParentSid__, -1);
        int parentFcbSid = NullDefault.getInt(fcbSid__, -1);
        if (parentDirSid == -1 && parentFcbSid == -1) {
            msg = new ActionMessage("error.select3.required.text",
                    gsMsg.getMessage("fil.120"));
            StrutsUtil.addMessage(errors, msg, "fdrParentSid");
            return errors;
        }

        // キャビネットSIDのみ取得できた場合はキャビネットSIDからディレクトリSIDを取得
        if (parentDirSid == -1) {

            FileDirectoryModel dirModel
                = dirDao.getRootDirectory(parentFcbSid);
            if (dirModel != null && dirModel.getFdrJtkbn() != GSConstFile.JTKBN_DELETE) {
                parentDirSid = dirModel.getFdrSid();
                this.setFdrParentSid(String.valueOf(parentDirSid));
            } else {
                msg = new ActionMessage("error.select3.required.text",
                        gsMsg.getMessage("fil.75"));
                StrutsUtil.addMessage(errors, msg, "fdrParentSid");
                return errors;
            }
        // ディレクトリSIDを取得できた場合は、ディレクトリSIDからキャビネットSIDを取得
        } else {
            parentFcbSid = biz.getCabinetSid(parentDirSid);
        }
        // 移動先ディレクトリの情報取得
        FileDirectoryModel toDirMdl = dirDao.getNewDirectory(parentDirSid);
        if (toDirMdl == null
                || toDirMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE
                || toDirMdl.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
            msg = new ActionMessage("search.data.notfound", gsMsg.getMessage("cmn.folder"));
            StrutsUtil.addMessage(errors, msg, "fdrSid");
            return errors;
        }
         // 移動先ディレクトリSID編集権限確認
        if (!biz.isDirAccessAuthUser(parentFcbSid,
                parentDirSid,
                NullDefault.getInt(GSConstFile.ACCESS_KBN_WRITE, -1))) {
            msg = new ActionMessage("error.edit.power.user", gsMsg.getMessage("cmn.edit"),
                    gsMsg.getMessage("cmn.move"));
                   StrutsUtil.addMessage(errors, msg, "parentDirSid");
                   return errors;
        }

        //通常キャビネットと電帳法対応キャビネット間での移動を禁止
        FileCabinetDao cabDao = new FileCabinetDao(con);
        FileCabinetModel fromCabMdl = cabDao.select(fromDirMdl.getFcbSid());
        FileCabinetModel toCabMdl = cabDao.select(toDirMdl.getFcbSid());
        if (fromCabMdl.getFcbErrl() != toCabMdl.getFcbErrl()) {
            msg = new ActionMessage("error.move.errl.file");
            StrutsUtil.addMessage(errors, msg, "error.move.errl.file");
            return errors;
        }

        // 移動後ディレクトリの構造確認
        __setValidateTree(con, reqMdl, errors);

        return errors;
    }

    /**
     * <br>[機  能] ディレクトリ移動後の構造をチェックする。
     * <br>[解  説] 以下はエラーとする。
     * <br>        移動後ディレクトリ11階層以上
     * <br>        移動先ディレクトリが自分
     * <br>        移動先ディレクトリの親ディレクトリ
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @param errors アクションエラー
     * @throws SQLException SQL実行例外
     */
    private void __setValidateTree(Connection con, RequestModel reqMdl,
            ActionErrors errors) throws SQLException {
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        Fil090Biz biz = new Fil090Biz(reqMdl, con);
        ActionMessage msg = null;

        FileDirectoryModel dirModel
        = dirDao.getNewDirectory(NullDefault.getInt(fdrParentSid__, -1));

        int moveDirSid = NullDefault.getInt(fdrSid__, -1);
        int ecode = Fil090Biz.ECODE_NONE;
        ecode = biz.validateTree(dirModel, moveDirSid);

        switch (ecode) {
        case Fil090Biz.ECODE_OVER_LEVEL:
            msg = new ActionMessage("error.over.level.dir", GSConstFile.MAX_LEVEL);
            StrutsUtil.addMessage(errors, msg, "fil090DirSid");
            break;
        case Fil090Biz.ECODE_MOVETOSELF:
            msg = new ActionMessage("error.dir.move3");
            StrutsUtil.addMessage(errors, msg, "itSelf");
            break;

        case Fil090Biz.ECODE_MOVETOCHILD:
            msg = new ActionMessage("error.dir.move1");
            StrutsUtil.addMessage(errors, msg, "loopDir");
        default:
            break;
        }
        return;
    }


    /**
     * <p>fdrSid を取得します。
     * @return fdrSid
     * @see jp.groupsession.v2.api.file.move.ApiFileMoveForm#fdrSid__
     */
    public String getFdrSid() {
        return fdrSid__;
    }

    /**
     * <p>fdrSid をセットします。
     * @param fdrSid fdrSid
     * @see jp.groupsession.v2.api.file.move.ApiFileMoveForm#fdrSid__
     */
    public void setFdrSid(String fdrSid) {
        fdrSid__ = fdrSid;
    }

    /**
     * <p>fdrParentSid を取得します。
     * @return fdrParentSid
     * @see jp.groupsession.v2.api.file.move.ApiFileMoveForm#fdrParentSid__
     */
    public String getFdrParentSid() {
        return fdrParentSid__;
    }

    /**
     * <p>fdrParentSid をセットします。
     * @param fdrParentSid fdrParentSid
     * @see jp.groupsession.v2.api.file.move.ApiFileMoveForm#fdrParentSid__
     */
    public void setFdrParentSid(String fdrParentSid) {
        fdrParentSid__ = fdrParentSid;
    }

    /**
     * <p>fcbSid を取得します。
     * @return fcbSid
     * @see jp.groupsession.v2.api.file.move.ApiFileMoveForm#fcbSid__
     */
    public String getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>fcbSid をセットします。
     * @param fcbSid fcbSid
     * @see jp.groupsession.v2.api.file.move.ApiFileMoveForm#fcbSid__
     */
    public void setFcbSid(String fcbSid) {
        fcbSid__ = fcbSid;
    }


}

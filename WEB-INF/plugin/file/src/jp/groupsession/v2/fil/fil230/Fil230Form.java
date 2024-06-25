package jp.groupsession.v2.fil.fil230;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil200.Fil200Form;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 ファイル一括削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil230Form extends Fil200Form {

    /** 選択キャビネットSID */
    private String fil230SltCabinetSid__ = null;
    /** 削除ディレクトリSID */
    private String fil230DeleteDirSid__ = null;
    /** 削除オプション */
    private String fil230DeleteOpt__ = null;
    /** 削除ディレクトリパス */
    private String fil230DeleteDir__ = null;
    /** ルートディレクトリパス */
    private String fil230RootDirSid__ = null;
    /** ルートディレクトリ名 */
    private String fil230RootDirName__ = null;
    /** キャビネットリスト */
    private List<LabelValueBean> fil230cabinetList__ = new ArrayList<LabelValueBean>();
    /** キャビネット区分 */
    private int fil230cabinetKbn__ = GSConstFile.CABINET_KBN_PRIVATE;
    /** 操作コメント */
    private String fil230Comment__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return エラー
     * @throws SQLException
     */
    public ActionErrors validateCheck(HttpServletRequest req, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();

        //キャビネット種別
        errors = validateCabinetType(errors, req);

        //キャビネット
        if (fil230SltCabinetSid__ == null || fil230SltCabinetSid__.equals("-1")) {
            String textCabinet = gsMsg.getMessage(req, "fil.23");
            msg = new ActionMessage("error.select.required.text", textCabinet);
            StrutsUtil.addMessage(errors, msg, "fil230SltCabinetSid");
        } else {
            errors = validateCabinet(errors, req, con);
        }

        //ディレクトリ
        if (StringUtil.isNullZeroString(fil230DeleteDirSid__)) {
            String textFolder = gsMsg.getMessage(req, "cmn.folder");
            msg = new ActionMessage("error.select.required.text", textFolder);
            StrutsUtil.addMessage(errors, msg, "fil230DeleteDirSid");
        } else {
            FileDirectoryDao fdrDao = new FileDirectoryDao(con);
            FileDirectoryModel fdrMdl = fdrDao.getNewDirectory(
                    Integer.parseInt(fil230DeleteDirSid__));
            if (fdrMdl == null || fdrMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                String textFolder = gsMsg.getMessage(req, "cmn.folder");
                msg = new ActionMessage("error.input.notvalidate.data", textFolder);
                StrutsUtil.addMessage(errors, msg, "fil230DeleteDirSid");
            }
        }

        //削除オプション
        if (fil230DeleteOpt__ == null) {
            String textDeleteOption = gsMsg.getMessage(req, "fil.35");
            msg = new ActionMessage("error.select.required.text", textDeleteOption);
            StrutsUtil.addMessage(errors, msg, "fil230DeleteOpt");
        }


        //操作コメント
        GSValidateFile.validateTextarea(errors,
                                        fil230Comment__,
                                        gsMsg.getMessage("fil.11"),
                                        GSConstFile.MAX_LENGTH_FILE_UP_CMT,
                                        false);

        return errors;
    }

    /**
     * <br>[機  能] キャビネット種別の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCabinetType(ActionErrors errors, HttpServletRequest req) {
        ActionMessage msg = null;

        if (fil230cabinetKbn__ != GSConstFile.CABINET_KBN_PRIVATE
        && fil230cabinetKbn__ != GSConstFile.CABINET_KBN_PUBLIC
        && fil230cabinetKbn__ != GSConstFile.CABINET_KBN_ERRL) {

            GsMessage gsMsg = new GsMessage(req);
            msg = new ActionMessage("error.select3.required.text", gsMsg.getMessage("fil.991"));
            StrutsUtil.addMessage(errors, msg, "fil230cabinetKbn");
        }

        return errors;
    }

    /**
     * <br>[機  能] キャビネットの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param req リクエスト
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCabinet(ActionErrors errors, HttpServletRequest req, Connection con)
    throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(req);

        FileCabinetDao fcbDao = new FileCabinetDao(con);
        FileCabinetModel fcbMdl = fcbDao.select(NullDefault.getInt(fil230SltCabinetSid__, -1));
        if (fcbMdl == null || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            String textCabinet = gsMsg.getMessage(req, "fil.23");
            msg = new ActionMessage("error.input.notvalidate.data", textCabinet);
            StrutsUtil.addMessage(errors, msg, "fil230SltCabinetSid");
        } else {
            boolean errorCabType = false;

            if (fil230cabinetKbn__ == GSConstFile.CABINET_KBN_PRIVATE) {
                errorCabType = fcbMdl.getFcbPersonalFlg() != GSConstFile.CABINET_PRIVATE_USE;
            } else if (fil230cabinetKbn__ == GSConstFile.CABINET_KBN_PUBLIC) {
                errorCabType = fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON;
            } else if (fil230cabinetKbn__ == GSConstFile.CABINET_KBN_ERRL) {
                errorCabType = fcbMdl.getFcbErrl() != GSConstFile.ERRL_KBN_ON;
            }

            if (errorCabType) {
                msg = new ActionMessage("error.select3.required.text", gsMsg.getMessage("fil.23"));
                StrutsUtil.addMessage(errors, msg, "fil230SltCabinetSid");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] フォルダの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateFolder(ActionErrors errors, RequestModel reqMdl, Connection con)
    throws SQLException {

        //フォルダの存在チェック
        FileDirectoryDao fdrDao = new FileDirectoryDao(con);
        FileDirectoryModel fdrMdl
            = fdrDao.getNewDirectory(NullDefault.getInt(fil230DeleteDirSid__, -1));
        boolean folderError = (fdrMdl == null || fdrMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE);

        //キャビネットの存在チェック
        if (!folderError) {
            FileCabinetDao fcbDao = new FileCabinetDao(con);
            FileCabinetModel fcbMdl = fcbDao.select(fdrMdl.getFcbSid());
            folderError = (fcbMdl == null || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE);
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (folderError) {
            ActionMessage msg = new ActionMessage("error.select3.required.text",
                                                gsMsg.getMessage("fil.36"));
            StrutsUtil.addMessage(errors, msg, "fil230DeleteDirSid");
        }

        return errors;
    }

    /**
     * <p>fil230cabinetList を取得します。
     * @return fil230cabinetList
     */
    public List<LabelValueBean> getFil230cabinetList() {
        return fil230cabinetList__;
    }

    /**
     * <p>fil230cabinetList をセットします。
     * @param fil230cabinetList fil230cabinetList
     */
    public void setFil230cabinetList(List<LabelValueBean> fil230cabinetList) {
        fil230cabinetList__ = fil230cabinetList;
    }

    /**
     * <p>fil230DeleteDirSid を取得します。
     * @return fil230DeleteDirSid
     */
    public String getFil230DeleteDirSid() {
        return fil230DeleteDirSid__;
    }

    /**
     * <p>fil230DeleteDirSid をセットします。
     * @param fil230DeleteDirSid fil230DeleteDirSid
     */
    public void setFil230DeleteDirSid(String fil230DeleteDirSid) {
        fil230DeleteDirSid__ = fil230DeleteDirSid;
    }

    /**
     * <p>fil230DeleteOpt を取得します。
     * @return fil230DeleteOpt
     */
    public String getFil230DeleteOpt() {
        return fil230DeleteOpt__;
    }

    /**
     * <p>fil230DeleteOpt をセットします。
     * @param fil230DeleteOpt fil230DeleteOpt
     */
    public void setFil230DeleteOpt(String fil230DeleteOpt) {
        fil230DeleteOpt__ = fil230DeleteOpt;
    }

    /**
     * <p>fil230SltCabinetSid を取得します。
     * @return fil230SltCabinetSid
     */
    public String getFil230SltCabinetSid() {
        return fil230SltCabinetSid__;
    }

    /**
     * <p>fil230SltCabinetSid をセットします。
     * @param fil230SltCabinetSid fil230SltCabinetSid
     */
    public void setFil230SltCabinetSid(String fil230SltCabinetSid) {
        fil230SltCabinetSid__ = fil230SltCabinetSid;
    }

    /**
     * <p>fil230DeleteDir を取得します。
     * @return fil230DeleteDir
     */
    public String getFil230DeleteDir() {
        return fil230DeleteDir__;
    }

    /**
     * <p>fil230DeleteDir をセットします。
     * @param fil230DeleteDir fil230DeleteDir
     */
    public void setFil230DeleteDir(String fil230DeleteDir) {
        fil230DeleteDir__ = fil230DeleteDir;
    }

    /**
     * <p>fil230RootDirSid を取得します。
     * @return fil230RootDirSid
     */
    public String getFil230RootDirSid() {
        return fil230RootDirSid__;
    }

    /**
     * <p>fil230RootDirSid をセットします。
     * @param fil230RootDirSid fil230RootDirSid
     */
    public void setFil230RootDirSid(String fil230RootDirSid) {
        fil230RootDirSid__ = fil230RootDirSid;
    }

    /**
     * <p>fil230RootDirName を取得します。
     * @return fil230RootDirName
     */
    public String getFil230RootDirName() {
        return fil230RootDirName__;
    }

    /**
     * <p>fil230RootDirName をセットします。
     * @param fil230RootDirName fil230RootDirName
     */
    public void setFil230RootDirName(String fil230RootDirName) {
        fil230RootDirName__ = fil230RootDirName;
    }

    /**
     * <p>fil230cabinetKbn を取得します。
     * @return fil230cabinetKbn
     * @see jp.groupsession.v2.fil.fil230.Fil230Form#fil230cabinetKbn__
     */
    public int getFil230cabinetKbn() {
        return fil230cabinetKbn__;
    }

    /**
     * <p>fil230cabinetKbn をセットします。
     * @param fil230cabinetKbn fil230cabinetKbn
     * @see jp.groupsession.v2.fil.fil230.Fil230Form#fil230cabinetKbn__
     */
    public void setFil230cabinetKbn(int fil230cabinetKbn) {
        fil230cabinetKbn__ = fil230cabinetKbn;
    }

    /**
     * <p>fil230Comment を取得します。
     * @return fil230Comment
     */
    public String getFil230Comment() {
        return fil230Comment__;
    }

    /**
     * <p>fil230Comment をセットします。
     * @param fil230Comment fil230Comment
     */
    public void setFil230Comment(String fil230Comment) {
        fil230Comment__ = fil230Comment;
    }

}

package jp.groupsession.v2.api.file.searchlist;

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
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/searchlistのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-searchlist",
plugin = "file", name = "ファイル管理詳細検索",
url = "/api/file/searchlist.do",
reqtype = "GET")
public class ApiFileSearchListForm extends AbstractApiForm {

    /** 検索キーワード */
    @ApiParam(name = "keyword", viewName = "検索キーワード")
    private String keyword__ = null;

    /** キャビネットSID */
    @ApiParam(name = "fcbSid", viewName = "キャビネットSID")
    private String fcbSid__ = null;

    /** ディレクトリSID */
    @ApiParam(name = "fdrSid", viewName = "ディレクトリSID")
    private String fdrSid__ = null;
    
    /** キャビネット区分 */
    @ApiParam(name = "fcbKbn", viewName = "キャビネット区分")
    private String fcbKbn__ = String.valueOf(GSConstFile.SELECT_CABINET_PUBLIC);

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQLエラー
     */
    public ActionErrors validateSearchList(Connection con, RequestModel reqMdl)
            throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);

        // 検索キーワード
        GSValidateFile.validateTextBoxInput(errors,
                                         keyword__,
                                         gsMsg.getMessage("cmn.keyword"),
                                         GSConstFile.MAX_LENGTH_KEYWORD,
                                         false);
        
        if (!fcbKbn__.equals(String.valueOf(GSConstFile.SELECT_CABINET_PUBLIC))
                && !fcbKbn__.equals(String.valueOf(GSConstFile.SELECT_CABINET_ERRL))) {
            //キャビネット区分
            msg = new ActionMessage(
                    "error.input.notvalidate.data",
                    gsMsg.getMessage(GSConstApi.TEXT_CABINET_KBN));
            StrutsUtil.addMessage(errors, msg, "fcbKbn");
            
        } else if (!StringUtil.isNullZeroString(fdrSid__)) {
            // ディレクトリSID
            if (!ValidateUtil.isNumber(fdrSid__)) {
                //数字チェック
                msg = new ActionMessage(
                        "error.input.number.hankaku",
                        gsMsg.getMessage(GSConstApi.TEXT_DIRECTORY_SID));
                StrutsUtil.addMessage(errors, msg, "fdrSid");

            } else if (!__isExistFolder(con)) {
                //存在チェック
                msg = new ActionMessage(
                        "search.notfound.tdfkcode",
                        gsMsg.getMessage(GSConstApi.TEXT_SELECT_FOLDER));
                StrutsUtil.addMessage(errors, msg, "fdrSid");

            } else {
                int fdrSid = NullDefault.getInt(fdrSid__, -1);
                int cabinetSid = fileBiz.getCabinetSid(fdrSid);
                FileCabinetDao cabDao = new FileCabinetDao(con);
                FileCabinetModel fcbMdl = cabDao.select(cabinetSid);
                if (!fileBiz.isDirAccessAuthUser(cabinetSid, fdrSid,
                        -1)) {
                    //アクセス権限チェック
                    msg = new ActionMessage("error.edit.power.user",
                            gsMsg.getMessage("cmn.reading"),
                            gsMsg.getMessage("cmn.reading"));
                    StrutsUtil.addMessage(errors, msg, "fcbSid");
                }
                
                if (!__isCorrectCabinetKbn(fcbMdl)) {
                    //キャビネット区分チェック
                    msg = new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage(GSConstApi.TEXT_SELECT_FOLDER));
                    StrutsUtil.addMessage(errors, msg, "fcbSid");
                }
            }
        } else if (!StringUtil.isNullZeroString(fcbSid__)) {
            // キャビネットSID
            if (!ValidateUtil.isNumber(fcbSid__)) {
                //数字チェック
                msg = new ActionMessage(
                        "error.input.number.hankaku",
                        gsMsg.getMessage(GSConstApi.TEXT_CABINET_SID));
                StrutsUtil.addMessage(errors, msg, "fcbSid");

            } else if (!__isExistCabinet(con)) {
                //存在チェック
                msg = new ActionMessage(
                        "search.notfound.tdfkcode",
                        gsMsg.getMessage(GSConstApi.TEXT_SELECT_CABINET));
                StrutsUtil.addMessage(errors, msg, "fcbSid");

            } else if (!__isCorrectCabinetKbn(con)) {
                //キャビネット区分チェック
                msg = new ActionMessage("error.input.notvalidate.data",
                        gsMsg.getMessage(GSConstApi.TEXT_SELECT_CABINET));
                StrutsUtil.addMessage(errors, msg, "fcbSid");
                
            } else if (!fileBiz.isAccessAuthUser(NullDefault.getInt(fcbSid__, -1))) {
                //アクセス権限チェック
                msg = new ActionMessage("error.edit.power.user",
                        gsMsg.getMessage("cmn.reading"),
                        gsMsg.getMessage("cmn.reading"));
                StrutsUtil.addMessage(errors, msg, "fcbSid");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] フォルダが存在するか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    private boolean __isExistFolder(Connection con) throws SQLException {

        FileDirectoryDao dao = new FileDirectoryDao(con);
        FileDirectoryModel model = dao.getNewDirectory(NullDefault.getInt(fdrSid__, 0));
        if (model == null || model.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        }
        if (model.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] キャビネットが存在するか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    private boolean __isExistCabinet(Connection con) throws SQLException {
        
        int fcbSid = NullDefault.getInt(fcbSid__, 0);
        FileCabinetDao fcbDao = new FileCabinetDao(con);
        FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
        if (fcbMdl == null || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        }

        FileDirectoryDao dao = new FileDirectoryDao(con);
        FileDirectoryModel fdrMdl = dao.getRootDirectory(fcbSid);
        if (fdrMdl == null || fdrMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        }
        
        return true;
    }
    
    /**
     * <br>[機  能] キャビネット区分が指定されたものと同じか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbMdl キャビネット情報
     * @return true:キャビネット区分が指定されたものと同じ false:キャビネット区分が指定されたものと異なる
     * @throws SQLException SQL実行時例外
     */
    private boolean __isCorrectCabinetKbn(FileCabinetModel fcbMdl) throws SQLException {
        
        if (fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            return false;
        }
        
        String cabKbn = String.valueOf(GSConstFile.SELECT_CABINET_PUBLIC);
        if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
            cabKbn = String.valueOf(GSConstFile.SELECT_CABINET_ERRL);
        }
        if (!cabKbn.equals(fcbKbn__)) {
            return false;
        }
        return true;
    }
    
    /**
     * <br>[機  能] キャビネット区分が指定されたものと同じか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:キャビネット区分が指定されたものと同じ false:キャビネット区分が指定されたものと異なる
     * @throws SQLException SQL実行時例外
     */
    private boolean __isCorrectCabinetKbn(Connection con) throws SQLException {
        FileCabinetDao dao = new FileCabinetDao(con);
        //事前にキャビネットの存在チェックが行われているため、ここでは行わない
        FileCabinetModel fcbMdl = dao.select(NullDefault.getInt(fcbSid__, -1));
        return __isCorrectCabinetKbn(fcbMdl);
    }

    /**
     * <p>keyword を取得します。
     * @return keyword
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchListForm#keyword__
     */
    public String getKeyword() {
        return keyword__;
    }

    /**
     * <p>keyword をセットします。
     * @param keyword keyword
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchListForm#keyword__
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }

    /**
     * <p>fcbSid を取得します。
     * @return fcbSid
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchListForm#fcbSid__
     */
    public String getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>fcbSid をセットします。
     * @param fcbSid fcbSid
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchListForm#fcbSid__
     */
    public void setFcbSid(String fcbSid) {
        fcbSid__ = fcbSid;
    }

    /**
     * <p>fdrSid を取得します。
     * @return fdrSid
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchListForm#fdrSid__
     */
    public String getFdrSid() {
        return fdrSid__;
    }

    /**
     * <p>fdrSid をセットします。
     * @param fdrSid fdrSid
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchListForm#fdrSid__
     */
    public void setFdrSid(String fdrSid) {
        fdrSid__ = fdrSid;
    }
    
    /**
     * <p>fcbKbn を取得します。
     * @return fcbKbn
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchListForm#fcbKbn__
     */
    public String getFcbKbn() {
        return fcbKbn__;
    }

    /**
     * <p>fcbKbn をセットします。
     * @param fcbKbn fcbKbn
     * @see jp.groupsession.v2.api.file.searchlist.ApiFileSearchListForm#fcbKbn__
     */
    public void setFcbKbn(String fcbKbn) {
        fcbKbn__ = fcbKbn;
    }

}

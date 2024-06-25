package jp.groupsession.v2.ptl.ptl160;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.ptl.AbstractPtlForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 * <br>[機  能] ポータル ポートレット画像選択ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl160Form extends AbstractPtlForm {

    /** ポートレットSID */
    private int ptlPortletSid__ = 0;
    /** 選択画像SID */
    private long ptlPortletImageSid__ = 0;

    /** 添付ファイル名 */
    private String ptl160tempName__ = null;
    /** 添付ファイル名(本体) */
    private String ptl160tempSaveName__ = null;
    /** 添付ファイル */
    private FormFile ptl160file__ = null;

    /** ファイル添付完了フラグ */
    private int ptl160Decision__ = 0;
    /** ファイル最大容量 (表示用) */
    private String strMaxSize__;

    /** ファイルサイズ */
    private static final int FILESIZE_NODATA = 0;
    /** ファイルサイズ 1MB */
    private static final int FILE_SIZE_1MB = 1048576;
    /** スプリット文字列 */
    private String splitStr__ = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con Connection
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 添付ファイル操作時例外
     */
    public ActionErrors validate110(Connection con, HttpServletRequest req)
        throws SQLException, IOToolsException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        if (ptl160file__ == null || ptl160file__.getFileSize() == FILESIZE_NODATA) {
            //指定されたファイルが存在しない場合はエラーメッセージを表示
            msg = new ActionMessage("error.input.notfound.file");
            errors.add("ptl160file.error.input.notfound.file", msg);
            return errors;
        }

        if (ptl160file__.getFileName().length() > GSConst.MAX_LENGTH_FILE) {
            //ファイル名
            String textFileName = gsMsg.getMessage(req, "cmn.file.name");

            //ファイル名桁数チェック
            msg = new ActionMessage(
                    "error.input.length.text", textFileName, GSConst.MAX_LENGTH_FILE);
            errors.add("ptl160file.error.input.length.text", msg);
            return errors;
        }

        //ファイル名 使用文字チェック
        if (!GSValidateUtil.isGsJapaneaseString(ptl160file__.getFileName())) {
            //ファイル名
            String textFileName = gsMsg.getMessage(req, "cmn.file.name");

            //利用不可能な文字を入力した場合
            String nstr =
                GSValidateUtil.getNotGsJapaneaseString(
                        ptl160file__.getFileName());
            msg =
                new ActionMessage("error.input.njapan.text",
                        textFileName,
                        nstr);
            errors.add("ptl160file.error.file.name.char", msg);
        }

        int maxSize = 0;
        CmnFileConfDao cfcDao = new CmnFileConfDao(con);
        //添付ファイル最大容量取得

        CmnFileConfModel cfcMdl = cfcDao.select();
        maxSize = cfcMdl.getFicMaxSize() * FILE_SIZE_1MB;
        if (ptl160file__.getFileSize() > maxSize) {
            //指定されたファイルの容量が最大値を超えていた場合はエラーメッセージを表示
            msg = new ActionMessage("error.input.capacity.over", cfcMdl.getFicMaxSize() + "MB");
            errors.add("ptl160file.error.input.capacity.over", msg);
            return errors;
        }

        return errors;
    }

    /**
     * <p>ptl160Decision を取得します。
     * @return ptl160Decision
     */
    public int getPtl160Decision() {
        return ptl160Decision__;
    }

    /**
     * <p>ptl160Decision をセットします。
     * @param ptl160Decision ptl160Decision
     */
    public void setPtl160Decision(int ptl160Decision) {
        ptl160Decision__ = ptl160Decision;
    }

    /**
     * <p>ptl160file を取得します。
     * @return ptl160file
     */
    public FormFile getPtl160file() {
        return ptl160file__;
    }

    /**
     * <p>ptl160file をセットします。
     * @param ptl160file ptl160file
     */
    public void setPtl160file(FormFile ptl160file) {
        ptl160file__ = ptl160file;
    }

    /**
     * <p>ptl160tempName を取得します。
     * @return ptl160tempName
     */
    public String getPtl160tempName() {
        return ptl160tempName__;
    }

    /**
     * <p>ptl160tempName をセットします。
     * @param ptl160tempName ptl160tempName
     */
    public void setPtl160tempName(String ptl160tempName) {
        ptl160tempName__ = ptl160tempName;
    }

    /**
     * <p>ptl160tempSaveName を取得します。
     * @return ptl160tempSaveName
     */
    public String getPtl160tempSaveName() {
        return ptl160tempSaveName__;
    }

    /**
     * <p>ptl160tempSaveName をセットします。
     * @param ptl160tempSaveName ptl160tempSaveName
     */
    public void setPtl160tempSaveName(String ptl160tempSaveName) {
        ptl160tempSaveName__ = ptl160tempSaveName;
    }

    /**
     * <p>ptlPortletImageSid を取得します。
     * @return ptlPortletImageSid
     */
    public long getPtlPortletImageSid() {
        return ptlPortletImageSid__;
    }

    /**
     * <p>ptlPortletImageSid をセットします。
     * @param ptlPortletImageSid ptlPortletImageSid
     */
    public void setPtlPortletImageSid(long ptlPortletImageSid) {
        ptlPortletImageSid__ = ptlPortletImageSid;
    }

    /**
     * <p>ptlPortletSid を取得します。
     * @return ptlPortletSid
     */
    public int getPtlPortletSid() {
        return ptlPortletSid__;
    }

    /**
     * <p>ptlPortletSid をセットします。
     * @param ptlPortletSid ptlPortletSid
     */
    public void setPtlPortletSid(int ptlPortletSid) {
        ptlPortletSid__ = ptlPortletSid;
    }

    /**
     * <p>splitStr を取得します。
     * @return splitStr
     */
    public String getSplitStr() {
        return splitStr__;
    }

    /**
     * <p>splitStr をセットします。
     * @param splitStr splitStr
     */
    public void setSplitStr(String splitStr) {
        splitStr__ = splitStr;
    }

    /**
     * <p>strMaxSize を取得します。
     * @return strMaxSize
     */
    public String getStrMaxSize() {
        return strMaxSize__;
    }

    /**
     * <p>strMaxSize をセットします。
     * @param strMaxSize strMaxSize
     */
    public void setStrMaxSize(String strMaxSize) {
        strMaxSize__ = strMaxSize;
    }
}
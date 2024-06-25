package jp.groupsession.v2.api.webmail.mail.send;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.webmail.mail.ApiWmlMailBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.GSValidateWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateFileDao;
import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.model.WmlValidateMailAddressResultModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.wml010.Wml010Const;
import jp.groupsession.v2.wml.wml010.Wml010Form;

/**
 * <br>[機  能] WEBメールを送信するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-mail/send",
plugin = "webmail", name = "メール送信",
url = "/api/webmail/mail/send.do", reqtype = "POST")
public class ApiWmlMailSendForm extends AbstractApiForm {

    /** アカウントSID */
    @ApiParam(name = "wacSid", viewName = "アカウントSID")
    private int wacSid__    = -1;
    /** 宛先メールアドレス */
    @ApiParam(name = "sendAdrTo", viewName = "宛先メールアドレス", required = false, confRequired = true)
    private String[] sendAdrTo__  = null;
    /** CCメールアドレス */
    @ApiParam(name = "sendAdrCc", viewName = "CCメールアドレス", required = false, confRequired = true)
    private String[] sendAdrCc__  = null;
    /** BCCメールアドレス */
    @ApiParam(name = "sendAdrBcc", viewName = "BCCメールアドレス", required = false, confRequired = true)
    private String[] sendAdrBcc__ = null;
    /** 件名 */
    @ApiParam(name = "title", viewName = "タイトル", required = false, confRequired = true)
    private String title__ = null;
    /** 本文 */
    @ApiParam(name = "body", viewName = "内容", required = false, confRequired = true)
    private String body__ = null;
    /** 送信タイプ(0:新規作成 / 1:返信 / 2:全返信 / 3:転送 / 4:編集) */
    private int sendType__ = GSConstWebmail.SEND_TYPE_NORMAL;
    /** 送信方法(0:即時送信 / 1:時間差送信 / 2:予約送信) */
    private int sendPlan__ = Wml010Const.TIMESENT_NORMAL;
    /** 予約送信日付(予約送信時のみ) */
    private String sendPlanDate__ = null;
    /** 参照メールSID(新規作成以外) */
    private long wmlSid__ = -1;

    ///** 添付ファイル バイナリデータ一覧 */
    //private FormFile[] tmpFiles__ = null;
    /** 添付ファイル1 */
    private FormFile tmpFile1__  = null;
    /** 添付ファイル2 */
    private FormFile tmpFile2__  = null;
    /** 添付ファイル3 */
    private FormFile tmpFile3__  = null;
    /** 添付ファイル4 */
    private FormFile tmpFile4__  = null;
    /** 添付ファイル5 */
    private FormFile tmpFile5__  = null;
    /** 添付ファイル6 */
    private FormFile tmpFile6__  = null;
    /** 添付ファイル7 */
    private FormFile tmpFile7__  = null;
    /** 添付ファイル8 */
    private FormFile tmpFile8__  = null;
    /** 添付ファイル9 */
    private FormFile tmpFile9__  = null;
    /** 添付ファイル10 */
    private FormFile tmpFile10__ = null;

    /** 添付ファイル バイナリSID一覧(テンプレート用) */
    private long[] binSids__ = null;
    /** 添付ファイル バイナリSID一覧(転送・編集用) */
    private long[] wtfSids__ = null;
    /** 添付ファイル テンプレートSID */
    private int wtpSid__ = -1;
    /** 添付ファイル 自動圧縮フラグ */
    private int fileCompress__ = 0;

    /** 添付ファイル バイナリデータ一覧(テンプレート用) */
    private List<CmnBinfModel> binFiles__ = null;
    /** 添付ファイル バイナリデータ一覧(転送・編集用) */
    private List<WmlTempfileModel> wtfFiles__ = null;

    /**
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * @param wacSid アカウントSID
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }
    /**
     * @return body
     */
    public String getBody() {
        return body__;
    }
    /**
     * @param body 設定する body
     */
    public void setBody(String body) {
        body__ = body;
    }
    /**
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * @param title 設定する title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    ///**
    // * @return tmpFiles
    // */
    //public FormFile[] getTmpFiles() {
    //    return tmpFiles__;
    //}
    ///**
    // * @param tmpFiles 設定する tmpFiles
    // */
    //public void setTmpFiles(FormFile[] tmpFiles) {
    //    tmpFiles__ = tmpFiles;
    //}
    /**
     * @return tmpFile1
     */
    public FormFile getTmpFile1() {
        return tmpFile1__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile1(FormFile tmpFile) {
        tmpFile1__ = tmpFile;
    }
    /**
     * @return tmpFile2
     */
    public FormFile getTmpFile2() {
        return tmpFile2__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile2(FormFile tmpFile) {
        tmpFile2__ = tmpFile;
    }
    /**
     * @return tmpFile3
     */
    public FormFile getTmpFile3() {
        return tmpFile3__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile3(FormFile tmpFile) {
        tmpFile3__ = tmpFile;
    }
    /**
     * @return tmpFile4
     */
    public FormFile getTmpFile4() {
        return tmpFile4__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile4(FormFile tmpFile) {
        tmpFile4__ = tmpFile;
    }
    /**
     * @return tmpFile5
     */
    public FormFile getTmpFile5() {
        return tmpFile5__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile5(FormFile tmpFile) {
        tmpFile5__ = tmpFile;
    }
    /**
     * @return tmpFile6
     */
    public FormFile getTmpFile6() {
        return tmpFile6__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile6(FormFile tmpFile) {
        tmpFile6__ = tmpFile;
    }
    /**
     * @return tmpFile7
     */
    public FormFile getTmpFile7() {
        return tmpFile7__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile7(FormFile tmpFile) {
        tmpFile7__ = tmpFile;
    }
    /**
     * @return tmpFile8
     */
    public FormFile getTmpFile8() {
        return tmpFile8__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile8(FormFile tmpFile) {
        tmpFile8__ = tmpFile;
    }
    /**
     * @return tmpFile9
     */
    public FormFile getTmpFile9() {
        return tmpFile9__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile9(FormFile tmpFile) {
        tmpFile9__ = tmpFile;
    }
    /**
     * @return tmpFile10
     */
    public FormFile getTmpFile10() {
        return tmpFile10__;
    }
    /**
     * @param tmpFile 設定する tmpFile
     */
    public void setTmpFile10(FormFile tmpFile) {
        tmpFile10__ = tmpFile;
    }
    /**
     * @return tmpFiles
     */
    public FormFile[] getTmpFiles() {
        List<FormFile> tmpFiles = new ArrayList<FormFile>();
        if (this.getTmpFile1() != null) {
            tmpFiles.add(this.getTmpFile1());
        }
        if (this.getTmpFile2() != null) {
            tmpFiles.add(this.getTmpFile2());
        }
        if (this.getTmpFile3() != null) {
            tmpFiles.add(this.getTmpFile3());
        }
        if (this.getTmpFile4() != null) {
            tmpFiles.add(this.getTmpFile4());
        }
        if (this.getTmpFile5() != null) {
            tmpFiles.add(this.getTmpFile5());
        }
        if (this.getTmpFile6() != null) {
            tmpFiles.add(this.getTmpFile6());
        }
        if (this.getTmpFile7() != null) {
            tmpFiles.add(this.getTmpFile7());
        }
        if (this.getTmpFile8() != null) {
            tmpFiles.add(this.getTmpFile8());
        }
        if (this.getTmpFile9() != null) {
            tmpFiles.add(this.getTmpFile9());
        }
        if (this.getTmpFile10() != null) {
            tmpFiles.add(this.getTmpFile10());
        }
        if (tmpFiles.size() > 0) {
            return tmpFiles.toArray(new FormFile[tmpFiles.size()]);
        }
        return null;
    }

    /**
     * @return binSids
     */
    public long[] getBinSids() {
        return binSids__;
    }
    /**
     * @param binSids 設定する binSids
     */
    public void setBinSids(long[] binSids) {
        binSids__ = binSids;
    }

    /**
     * <br>[機  能] 添付ファイルデータ一覧をテンプレートから取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con         コネクション
     * @param reqMdl      リクエストモデル
     * @return 添付ファイルデータ一覧
     */
    public List<CmnBinfModel> getBinFiles(Connection con, RequestModel reqMdl) {
        if (binFiles__ == null) {
            binFiles__ = new ArrayList<CmnBinfModel>();

            try {
                // 添付ファイルをテンプレートから取得
                List<Long> checkBinList = new ArrayList<Long>();
                if (this.getBinSids() != null) {
                    for (long binSid : this.getBinSids()) {
                        checkBinList.add(Long.valueOf(binSid));
                    }
                }

                String[]   binSidList   = null;
                if (this.getWacSid() >= 0 && checkBinList.size() > 0) {
                    // 使用可能なテンプレートの添付ファイル バイナリSID一覧取得
                    WmlMailTemplateFileDao templateFileDao = new WmlMailTemplateFileDao(con);
                    List<Long> tmpBinSidList =
                            templateFileDao.getTemplateBinSidList(this.getWacSid());
                    if (tmpBinSidList != null) {
                        for (int i = checkBinList.size() - 1; i >= 0; i--) {
                            Long val = checkBinList.get(i);
                            if (!tmpBinSidList.contains(val)) {
                                checkBinList.remove(val);
                            }
                        }
                        binSidList = new String[checkBinList.size()];
                        for (int i = 0; i < checkBinList.size(); i++) {
                            binSidList[i] = checkBinList.get(i).toString();
                        }
                    }
                }

                if (binSidList != null && binSidList.length > 0) {
                    CommonBiz cmnBiz = new CommonBiz();
                    // テンプレートから添付ファイルをコピー
                    List<CmnBinfModel> cmList = cmnBiz.getBinInfo(con, binSidList,
                                                                  reqMdl.getDomain());
                    if (cmList != null) {
                        for (CmnBinfModel cbMdl : cmList) {
                            if (cbMdl.getBinJkbn() != GSConst.JTKBN_DELETE) {
                                binFiles__.add(cbMdl);
                            }
                        }
                    }
                }
            } catch (TempFileException e) {
            } catch (SQLException e) {
            }
        }
        return binFiles__;
    }

    /**
     * @return wtfSids
     */
    public long[] getWtfSids() {
        return wtfSids__;
    }
    /**
     * @param wtfSids 設定する wtfSids
     */
    public void setWtfSids(long[] wtfSids) {
        wtfSids__ = wtfSids;
    }

    /**
     * <br>[機  能] 添付ファイルデータ一覧を参照メールから取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con         コネクション
     * @param reqMdl      リクエストモデル
     * @return 添付ファイルデータ一覧
     */
    public List<WmlTempfileModel> getWtfFiles(Connection con, RequestModel reqMdl) {

        if (wtfFiles__ == null) {
            wtfFiles__ = new ArrayList<WmlTempfileModel>();

            try {
                // 添付ファイルを参照メールから取得
                List<Long> checkWtfList = new ArrayList<Long>();
                if (this.getWtfSids() != null) {
                    for (long binSid : this.getWtfSids()) {
                        checkWtfList.add(Long.valueOf(binSid));
                    }
                }

                List<MailTempFileModel> binMdlList = null;
                if (this.getWmlSid() >= 0 && checkWtfList.size() > 0) {
                    // 参照WEBメールを指定されている場合(転送・編集時のみ)
                    if (this.getSendType() == GSConstWebmail.SEND_TYPE_FORWARD
                     || this.getSendType() == GSConstWebmail.SEND_TYPE_EDIT) {
                        // 既存のファイル情報を取得
                        WebmailDao wmlDao = new WebmailDao(con);
                        binMdlList = wmlDao.getTempFileList(this.getWmlSid());
                    }
                }

                if (binMdlList != null && binMdlList.size() > 0) {
                    // 参照メールから添付ファイルをコピー
                    WmlBiz wmlBiz = new WmlBiz();
                    for (MailTempFileModel binMdl : binMdlList) {
                        if (checkWtfList.contains(Long.valueOf(binMdl.getBinSid()))) {
                            WmlTempfileModel tmpMdl = wmlBiz.getTempFileData(con,
                                                                             this.getWmlSid(),
                                                                             binMdl.getBinSid(),
                                                                             reqMdl);
                            if (tmpMdl != null && tmpMdl.getWtfJkbn() != GSConst.JTKBN_DELETE) {
                                wtfFiles__.add(tmpMdl);
                            }
                        }
                    }
                }
            } catch (TempFileException e) {
            } catch (SQLException e) {
            }
        }
        return wtfFiles__;
    }

    /**
     * @return wtpSid
     */
    public int getWtpSid() {
        return wtpSid__;
    }
    /**
     * @param wtpSid 設定する wtpSid
     */
    public void setWtpSid(int wtpSid) {
        wtpSid__ = wtpSid;
    }
    /**
     * @return sendSidTo
     */
    public String[] getSendAdrTo() {
        return sendAdrTo__;
    }
    /**
     * @param sendAdrTo 設定する sendAdrTo
     */
    public void setsendAdrTo(String[] sendAdrTo) {
        sendAdrTo__ = sendAdrTo;
    }
    /**
     * @return sendAdrTo
     */
    public String getSendAdrToStr() {
        return ApiWmlMailBiz.appendAddress(sendAdrTo__);
    }
    /**
     * @return sendAdrCc
     */
    public String[] getSendAdrCc() {
        return sendAdrCc__;
    }
    /**
     * @param sendAdrCc 設定する sendAdrCc
     */
    public void setSendAdrCc(String[] sendAdrCc) {
        sendAdrCc__ = sendAdrCc;
    }
    /**
     * @return sendAdrCc
     */
    public String getSendAdrCcStr() {
        return ApiWmlMailBiz.appendAddress(sendAdrCc__);
    }
    /**
     * @return sendAdrBcc
     */
    public String[] getSendAdrBcc() {
        return sendAdrBcc__;
    }
    /**
     * @param sendAdrBcc 設定する sendAdrBcc
     */
    public void setSendAdrBcc(String[] sendAdrBcc) {
        sendAdrBcc__ = sendAdrBcc;
    }
    /**
     * @return sendAdrBcc
     */
    public String getSendAdrBccStr() {
        return ApiWmlMailBiz.appendAddress(sendAdrBcc__);
    }
    /**
     * @return sendType
     */
    public int getSendType() {
        return sendType__;
    }
    /**
     * @param sendType メール送信タイプ
     */
    public void setSendType(int sendType) {
        sendType__ = sendType;
    }
    /**
     * @return sendPlan
     */
    public int getSendPlan() {
        return sendPlan__;
    }
    /**
     * @param sendPlan メール送信方法
     */
    public void setSendPlan(int sendPlan) {
        sendPlan__ = sendPlan;
    }
    /**
     * @return sendPlanDate
     */
    public String getSendPlanDate() {
        return sendPlanDate__;
    }
    /**
     * @param sendPlanDate 予約送信日時
     */
    public void setSendPlanDate(String sendPlanDate) {
        sendPlanDate__ = sendPlanDate;
    }
    /**
     * @return wmlSid
     */
    public long getWmlSid() {
        return wmlSid__;
    }
    /**
     * @param wmlSid メールSID(返信 or 転送元メッセージ番号)
     */
    public void setWmlSid(long wmlSid) {
        wmlSid__ = wmlSid;
    }
    /**
     * @return fileCompress
     */
    public int getFileCompress() {
        return fileCompress__;
    }
    /**
     * @param fileCompress 添付ファイルの自動圧縮設定
     */
    public void setFileCompress(int fileCompress) {
        fileCompress__ = fileCompress;
    }

    /**
     * <br>[機  能] 添付ファイル入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con      コネクション
     * @param reqMdl   リクエストモデル
     * @param file     ファイル
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateTempFile(Connection con, RequestModel reqMdl, FormFile file)
            throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        String fileName = null;
        int    fileSize = 0;
        if (file != null) {
            fileName = file.getFileName();
            fileSize = file.getFileSize();
        }

        if (fileName == null || fileName.length() == 0 || fileSize == 0) {
            //指定されたファイルが存在しない場合はエラーメッセージを表示
            msg = new ActionMessage("error.input.notfound.file");
            errors.add("error.input.notfound.file", msg);
        } else if (fileName.length() > GSConst.MAX_LENGTH_FILE) {
            //ファイル名
            String textFileName = gsMsg.getMessage("cmn.file.name");

            //ファイル名桁数チェック
            msg = new ActionMessage(
                    "error.input.length.text", textFileName, GSConst.MAX_LENGTH_FILE);
            errors.add("error.input.length.text", msg);
        } else if (!GSValidateUtil.isGsJapaneaseString(fileName)) {
            //ファイル名 使用文字チェック
            String textFileName = gsMsg.getMessage("cmn.file.name");  //ファイル名

            //利用不可能な文字を入力した場合
            String nstr =
                GSValidateUtil.getNotGsJapaneaseString(
                        fileName);
            msg =
                new ActionMessage("error.input.njapan.text",
                        textFileName,
                        nstr);
            errors.add("error.file.name.char", msg);
        } else {
            int maxSize = 0;
            CmnFileConfDao cfcDao = new CmnFileConfDao(con);

            //添付ファイル最大容量取得
            CmnFileConfModel cfcMdl = cfcDao.select();
            maxSize = cfcMdl.getFicMaxSize() * 1048576;
            if (fileSize > maxSize) {
                //指定されたファイルの容量が最大値を超えていた場合はエラーメッセージを表示
                msg = new ActionMessage("error.input.capacity.over", cfcMdl.getFicMaxSize() + "MB");
                errors.add("cmn110file.error.input.capacity.over", msg);
            }
        }
        StrutsUtil.addMessage(errors, msg, "error.cant.use.plugin");

        return errors;
    }

    public String[] validateSendMail(
        Connection con, RequestModel reqMdl, String appRootPath) throws SQLException {
        
        GsMessage gsMsg = new GsMessage();
        String[] message = new String[]{};


        //送信先未入力
        if (StringUtils.isAllEmpty(sendAdrTo__)
            && StringUtils.isAllEmpty(sendAdrCc__)
            && StringUtils.isAllEmpty(sendAdrBcc__)) {
            message = new String[]{
                gsMsg.getMessage("cmn.plz.input",
                        new String[] {
                                gsMsg.getMessage("wml.send.dest")})};
        } else {
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountData = accountDao.select(wacSid__);
            String encode = WmlBiz.getSendEncode(accountData);

            WmlBiz wBiz = new WmlBiz();
            WmlAdmConfModel wadMdl = wBiz.getAdminConf(con); // 管理者設定
            String[] tldList = wBiz.getTldList(wadMdl);
            
            //送信先のチェック
            message = __addMessage(message,
                __validateSendMailAddress(getSendAdrToStr(),
                gsMsg.getMessage("cmn.from"),
                false,
                reqMdl, encode, tldList));

            //CCのチェック
            message = __addMessage(message,
                __validateSendMailAddress(getSendAdrCcStr(),
                "CC",
                false,
                reqMdl, encode, tldList));

            //BCCのチェック
            message = __addMessage(message,
                __validateSendMailAddress(getSendAdrBccStr(),
                "BCC",
                false,
                reqMdl, encode, tldList));
        }

        //予約送信日付
        if (sendPlan__ == GSConstWebmail.TIMESENT_FUTURE) {
            UDate planDate = ApiWmlMailBiz.convertSlashDateTimeFormat(sendPlanDate__, false);
            UDate now = new UDate();
            if (now.compareDateYMDHM(planDate) != UDate.LARGE) {
                message = __addMessage(message, new String[]{gsMsg.getMessage("wml.wml010.33")});
            }
        }

        return message;
    }

    /**
     * <br>[機  能] メッセージの追加を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param message メッセージ
     * @param newMessage 追加メッセージ
     * @return メッセージ
     */
    private String[] __addMessage(String[] message, String[] newMessage) {

        if (newMessage != null) {
            if (message != null) {
                String[] msg = new String[message.length + newMessage.length];
                System.arraycopy(message, 0, msg, 0, message.length);
                System.arraycopy(newMessage, 0, msg, message.length, newMessage.length);
                return msg;
            } else {
                return newMessage;
            }
        }

        return message;
    }

    /**
     * <br>[機  能] 送信先メールアドレスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @param nameJp パラメータ名(日本語名)
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param reqMdl リクエスト情報
     * @param encode 文字コード
     * @param tldList TLD制限許可リスト
     * @return エラーメッセージ
     */
    private String[] __validateSendMailAddress(String address, String nameJp,
            boolean checkNoInput, RequestModel reqMdl,
            String encode, String[] tldList) {

        WmlValidateMailAddressResultModel resultMdl
            = GSValidateWebmail.validateMailAddress(
                address, nameJp,
                reqMdl, encode, tldList);
        String[] message = resultMdl.getErrMessage();

        return message;
    }

    /**
     * <br>[機  能] 送信チェック用フォームデータへ変換
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con    コネクション
     * @return 送信チェック用フォームデータ
     */
    public Wml010Form getWml010Form(Connection con) {
        // 初期状態は即時送信設定
        int   sendPlanType = Wml010Const.TIMESENT_NORMAL;
        int   sendPlanImm  = Wml010Const.SENDPLAN_IMM;
        int   sendWacSid   = this.getWacSid();


        // 送信方法チェック
        if (this.getSendPlan() == GSConstWebmail.TIMESENT_FUTURE) {
            // 予約送信
            sendPlanType = Wml010Const.TIMESENT_AFTER;
        } else if (this.getSendPlan() == 1) {
            // 時間差送信
            sendPlanImm = 0;
        }

        // 入力情報の整合性チェック(PC版と同じ判定処理を使用)
        Wml010Form ret = new Wml010Form();
        ret.setWml010sendAccount(sendWacSid);
        ret.setWml010sendMailHtml(GSConstWebmail.SEND_HTMLMAIL_TEXT);
        ret.setWml010sendAddressTo(this.getSendAdrToStr());
        ret.setWml010sendAddressCc(this.getSendAdrCcStr());
        ret.setWml010sendAddressBcc(this.getSendAdrBccStr());
        ret.setWml010sendSubject(this.getTitle());
        ret.setWml010svSendContent(this.getBody());
        ret.setWml010sendMailType(this.getSendType());
        ret.setSendMailPlanType(sendPlanType);
        ret.setWml010sendMailPlanImm(sendPlanImm);
        if (this.getWmlSid() >= 0) {
            ret.setWml010sendMessageNum(this.getWmlSid());
        }

        // 予約送信時のみ送信時刻指定
        if (sendPlanType == Wml010Const.TIMESENT_AFTER) {
            UDate sendPlanDate =
                    ApiWmlMailBiz.convertSlashDateTimeFormat(this.getSendPlanDate(), false);
            //if (this.getSendPlanDate() != null && this.getSendPlanDate().length() > 0) {
            //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            //    sendPlanDate = UDate.getInstanceDate(sdf.parse(this.getSendPlanDate()));
            //}
            if (sendPlanDate != null) {
                ret.setWml010sendMailPlanDateYear(sendPlanDate.getStrYear());
                ret.setWml010sendMailPlanDateMonth(sendPlanDate.getStrMonth());
                ret.setWml010sendMailPlanDateDay(sendPlanDate.getStrDay());
                ret.setWml010sendMailPlanDateHour(sendPlanDate.getStrHour());
                ret.setWml010sendMailPlanDateMinute(sendPlanDate.getStrMinute());
            }
        }

        // 添付ファイル自動圧縮
        ret.setWml010sendTempfileCompress(this.getFileCompress());

        // 添付ファイル
        ret.setWml010sendMailFile(this.getTmpFiles());

        return ret;
    }

    /**
     * <br>[機  能] 送信メール情報を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl リクエスト情報
     * @return 送信メール情報
     * @throws SQLException SQL実行時例外
     * @throws IOException 
     * @throws IOToolsException 
     */
    public WmlMailSendBiz createMailSendBiz(Connection con,
        GSContext gsContext, String appRootPath, String tempDir,
        RequestModel reqMdl) throws SQLException, IOToolsException, IOException {

        UDate sendPlanDate = ApiWmlMailBiz.convertSlashDateTimeFormat(sendPlanDate__, false);
        int timeSent = GSConstWebmail.TIMESENT_NORMAL;
        if (sendPlan__ == GSConstWebmail.TIMESENT_FUTURE) {
            timeSent = GSConstWebmail.TIMESENT_FUTURE;
        } else if (sendPlan__ == GSConstWebmail.TIMESENT_AFTER) {
            timeSent = GSConstWebmail.TIMESENT_AFTER;
        }

        int userSid = reqMdl.getSmodel().getUsrsid();
        long messageNum = wmlSid__;
        if (messageNum < 0) {
            messageNum = 0;
        }

        int compressFileFlg = GSConstWebmail.WSP_COMPRESS_FILE_NOTCOMPRESS;
        if (fileCompress__ == 1) {
            compressFileFlg = GSConstWebmail.WSP_COMPRESS_FILE_COMPRESS;
        }

        WmlMailSendBiz ret = WmlMailSendBiz.builder()
            .setTo(getSendAdrToStr())
            .setCc(getSendAdrCcStr())
            .setBcc(getSendAdrBccStr())
            .setSubject(title__)
            .setContent(body__)
            .setWacSid(wacSid__)
            .setSendMessageNum(messageNum)
            .setSendMailType(sendType__)
            .setHtmlMail(false)
            .setTimeSentType(timeSent)
            .setSendPlanDate(sendPlanDate)
            .setCompressFileType(compressFileFlg)
            .build(con, gsContext, appRootPath, tempDir, userSid);

        return ret;
    }
}

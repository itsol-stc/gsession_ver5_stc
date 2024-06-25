package jp.groupsession.v2.api.smail.mail.send;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 * <br>[機  能] ショートメールを送信するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-mail/send",
plugin = "smail", name = "ショートメール送信",
url = "/api/smail/mail/send.do", reqtype = "POST")
public class ApiSmlMailSendForm extends AbstractApiForm {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailSendForm.class);


    /** 登録アカウントSID */
    @ApiParam(name = "sacSid", viewName = "アカウントSID")
    private int sacSid__    = -1;
    /** 宛先ユーザSID */
    @ApiParam(name = "sendTo", viewName = "宛先ユーザSID")
    private Integer[] sendTo__ = null;
    /** CCユーザSID */
    @ApiParam(name = "sendCc", viewName = "CCユーザSID", required = false, confRequired = true)
    private Integer[] sendCc__  = null;
    /** BCCユーザSID */
    @ApiParam(name = "sendBcc", viewName = "BCCユーザSID", required = false, confRequired = true)
    private Integer[] sendBcc__ = null;
    /** 件名 */
    @ApiParam(name = "title", viewName = "件名")
    private String title__ = null;
    /** マーク */
    private int    mark__ = GSConstSmail.MARK_KBN_NONE;
    /** 本文 */
    @ApiParam(name = "body", viewName = "本文")
    private String body__ = null;
    ///** 添付ファイル */
    //private List<FormFile> tmpFiles__ = null;
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
    /** 添付ファイル(既存) */
    private long[] binSids__ = null;
    /** メッセージ作成モード */
    private String procMode__ = GSConstSmail.MSG_CREATE_MODE_NEW;;
    /** 参照メールSID */
    private int smlSid__  = -1;

    /**
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }
    /**
     * @param sacSid 登録者アカウントSID
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
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
     * @return mark
     */
    public int getMark() {
        return mark__;
    }
    /**
     * @param mark 設定する mark
     */
    public void setMark(int mark) {
        mark__ = mark;
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
    //    if (tmpFiles__ == null) {
    //        return null;
    //    }
    //    return tmpFiles__.toArray(new FormFile[tmpFiles__.size()]);
    //}
    ///**
    // * @param tmpFiles 設定する tmpFiles
    // */
    //public void setTmpFiles(FormFile[] tmpFiles) {
    //    if (tmpFiles == null) {
    //        return;
    //    }
    //    tmpFiles__ = new ArrayList<FormFile>();
    //    for (FormFile formFile : tmpFiles) {
    //        tmpFiles__.add(formFile);
    //    }
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
        log__.info("FORM FILE CHECK");
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
        log__.info("FORM FILE COUNT: " + tmpFiles.size());
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
     * @return sendTo
     */
    public Integer[] getSendTo() {
        return sendTo__;
    }
    /**
     * @return sendToList
     */
    public List<Integer> getSendToList() {
        List<Integer> ret = new ArrayList<Integer>();
        if (sendTo__ != null) {
            for (Integer sid : sendTo__) {
                ret.add(sid);
            }
        }
        return ret;
    }
    /**
     * @param sendTo 設定する usrSid
     */
    public void setSendTo(Integer[] sendTo) {
        sendTo__ = sendTo;
    }
    /**
     * @return sendCc
     */
    public Integer[] getSendCc() {
        return sendCc__;
    }
    /**
     * @return sendToList
     */
    public List<Integer> getSendCcList() {
        List<Integer> ret = new ArrayList<Integer>();
        if (sendCc__ != null) {
            for (Integer sid : sendCc__) {
                ret.add(sid);
            }
        }
        return ret;
    }
    /**
     * @param sendCc 設定する sendCc
     */
    public void setSendCc(Integer[] sendCc) {
        sendCc__ = sendCc;
    }
    /**
     * @return sendBcc
     */
    public Integer[] getSendBcc() {
        return sendBcc__;
    }
    /**
     * @return sendToList
     */
    public List<Integer> getSendBccList() {
        List<Integer> ret = new ArrayList<Integer>();
        if (sendBcc__ != null) {
            for (Integer sid : sendBcc__) {
                ret.add(sid);
            }
        }
        return ret;
    }
    /**
     * @param sendBcc 設定する sendBcc
     */
    public void setSendBcc(Integer[] sendBcc) {
        sendBcc__ = sendBcc;
    }
    /**
     * @return procMode
     */
    public String getProcMode() {
        return procMode__;
    }
    /**
     * @param procMode メッセージ作成モード
     */
    public void setProcMode(String procMode) {
        procMode__ = procMode;
    }
    /**
     * @return smlSid
     */
    public int getSmlSid() {
        return smlSid__;
    }
    /**
     * @param smlSid 引用元メールSID
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }
}

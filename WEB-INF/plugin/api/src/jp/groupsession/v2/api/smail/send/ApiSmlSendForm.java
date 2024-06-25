package jp.groupsession.v2.api.smail.send;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlUserSearchDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメールを送信するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlSendForm extends AbstractApiForm {

    /** 宛先ユーザSID */
    private String usrSid__ = null;
    /** 件名 */
    private String title__ = null;
    /** マーク */
    private String mark__ = String.valueOf(GSConstSmail.MARK_KBN_NONE);
    /** 本文 */
    private String body__ = null;
    /** 添付ファイル */
    private FormFile tmpFile1__ = null;
    /** 添付ファイル */
    private FormFile tmpFile2__ = null;
    /** 添付ファイル */
    private FormFile tmpFile3__ = null;
    /** 添付ファイル */
    private FormFile tmpFile4__ = null;
    /** 添付ファイル */
    private FormFile tmpFile5__ = null;
    /** 添付ファイル */
    private FormFile tmpFile6__ = null;
    /** 添付ファイル */
    private FormFile tmpFile7__ = null;
    /** 添付ファイル */
    private FormFile tmpFile8__ = null;
    /** 添付ファイル */
    private FormFile tmpFile9__ = null;
    /** 添付ファイル */
    private FormFile tmpFile10__ = null;

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
    public String getMark() {
        return mark__;
    }
    /**
     * @param mark 設定する mark
     */
    public void setMark(String mark) {
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
    /**
     * @return tmpFile1
     */
    public FormFile getTmpFile1() {
        return tmpFile1__;
    }
    /**
     * @param tmpFile1 設定する tmpFile1
     */
    public void setTmpFile1(FormFile tmpFile1) {
        tmpFile1__ = tmpFile1;
    }
    /**
     * @return tmpFile10
     */
    public FormFile getTmpFile10() {
        return tmpFile10__;
    }
    /**
     * @param tmpFile10 設定する tmpFile10
     */
    public void setTmpFile10(FormFile tmpFile10) {
        tmpFile10__ = tmpFile10;
    }
    /**
     * @return tmpFile2
     */
    public FormFile getTmpFile2() {
        return tmpFile2__;
    }
    /**
     * @param tmpFile2 設定する tmpFile2
     */
    public void setTmpFile2(FormFile tmpFile2) {
        tmpFile2__ = tmpFile2;
    }
    /**
     * @return tmpFile3
     */
    public FormFile getTmpFile3() {
        return tmpFile3__;
    }
    /**
     * @param tmpFile3 設定する tmpFile3
     */
    public void setTmpFile3(FormFile tmpFile3) {
        tmpFile3__ = tmpFile3;
    }
    /**
     * @return tmpFile4
     */
    public FormFile getTmpFile4() {
        return tmpFile4__;
    }
    /**
     * @param tmpFile4 設定する tmpFile4
     */
    public void setTmpFile4(FormFile tmpFile4) {
        tmpFile4__ = tmpFile4;
    }
    /**
     * @return tmpFile5
     */
    public FormFile getTmpFile5() {
        return tmpFile5__;
    }
    /**
     * @param tmpFile5 設定する tmpFile5
     */
    public void setTmpFile5(FormFile tmpFile5) {
        tmpFile5__ = tmpFile5;
    }
    /**
     * @return tmpFile6
     */
    public FormFile getTmpFile6() {
        return tmpFile6__;
    }
    /**
     * @param tmpFile6 設定する tmpFile6
     */
    public void setTmpFile6(FormFile tmpFile6) {
        tmpFile6__ = tmpFile6;
    }
    /**
     * @return tmpFile7
     */
    public FormFile getTmpFile7() {
        return tmpFile7__;
    }
    /**
     * @param tmpFile7 設定する tmpFile7
     */
    public void setTmpFile7(FormFile tmpFile7) {
        tmpFile7__ = tmpFile7;
    }
    /**
     * @return tmpFile8
     */
    public FormFile getTmpFile8() {
        return tmpFile8__;
    }
    /**
     * @param tmpFile8 設定する tmpFile8
     */
    public void setTmpFile8(FormFile tmpFile8) {
        tmpFile8__ = tmpFile8;
    }
    /**
     * @return tmpFile9
     */
    public FormFile getTmpFile9() {
        return tmpFile9__;
    }
    /**
     * @param tmpFile9 設定する tmpFile9
     */
    public void setTmpFile9(FormFile tmpFile9) {
        tmpFile9__ = tmpFile9;
    }
    /**
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }
    /**
     * @param usrSid 設定する usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param gsMsg GsMessage
     * @param sessionUsrSid セッションユーザSID
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheckSmlSend(Connection con, GsMessage gsMsg, int sessionUsrSid)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //宛先
        if (StringUtil.isNullZeroString(usrSid__)) {
            //未入力
            msg = new ActionMessage(
                    "error.input.required.text",
                    gsMsg.getMessage("cmn.from"));
            StrutsUtil.addMessage(errors, msg, "usrSid");

            return errors;
        }

        //宛先を配列に格納
        String[] userSidList = usrSid__.split(",");
        CmnUsrmDao usrmDao = new CmnUsrmDao(con);
        for (int i = 0; userSidList.length > i; i++) {
            if (!ValidateUtil.isNumber(userSidList[i])) {
                //数字チェック
                msg = new ActionMessage(
                        "error.input.number.hankaku", gsMsg.getMessage("cmn.from"));
                StrutsUtil.addMessage(errors, msg, "usrSid");
                return errors;
            }

            if (!usrmDao.isExistUser(NullDefault.getInt(userSidList[i], -1))) {
                //存在チェック
                msg = new ActionMessage(
                        "error.select.has.not.exist.list");
                StrutsUtil.addMessage(errors, msg, "usrSid");
                return errors;
            }

        }
        SmlBanDestDao sbdDao = new SmlBanDestDao(con);
        List<Integer> intUserSidList = new ArrayList<Integer>();
        for (String id : userSidList) {
            intUserSidList.add(Integer.parseInt(id));
        }
        List<Integer> banedUsrSid = sbdDao.getBanDestUsrSidList(
                sessionUsrSid, intUserSidList);
        if (banedUsrSid.size() > 0) {
            StringBuilder sb = new StringBuilder();
            SmlUserSearchDao udao = new SmlUserSearchDao(con);
            String[] sids = new String[banedUsrSid.size()];
            for (int i = 0; i < banedUsrSid.size(); i++) {
                sids[i] = String.valueOf(banedUsrSid.get(i));
            }
            List<AtesakiModel> atkList = udao.getUserDataFromSidList(sids);
            for (AtesakiModel atk :atkList) {
                sb.append("<br />・");
                sb.append(atk.getUsiSei());
                sb.append(" ");
                sb.append(atk.getUsiMei());
            }
            msg =
                    new ActionMessage("error.dest.banned", "usrSid", sb.toString());
                StrutsUtil.addMessage(errors, msg, "usrSid." + "error.dest.banned");
        }


        /** メッセージ 件名 **/
        String subject = gsMsg.getMessage("cmn.subject");
        /** メッセージ 本文 **/
        String body = gsMsg.getMessage("cmn.body");

        //件名
        if (__validateSmlTitle(title__)) {
            msg = new ActionMessage(
                    "error.input.notvalidate.data", subject);
            StrutsUtil.addMessage(errors, msg, "title");
            return errors;
        }

        //マーク
        if (StringUtil.isNullZeroString(mark__)) {
        } else if (!ValidateUtil.isNumber(mark__)) {
            //数字チェック
            msg = new ActionMessage(
                    "error.input.number.hankaku", gsMsg.getMessage("cmn.mark"));
            StrutsUtil.addMessage(errors, msg, "mark");
            return errors;
        }

        //本文
        if (__validateSmlBody(body__)) {
            msg = new ActionMessage(
                    "error.input.notvalidate.data", body);
            StrutsUtil.addMessage(errors, msg, "body");

        }

        ArrayList<FormFile> tempList = new ArrayList<FormFile>();
        tempList.add(tmpFile1__);
        tempList.add(tmpFile2__);
        tempList.add(tmpFile3__);
        tempList.add(tmpFile4__);
        tempList.add(tmpFile5__);
        tempList.add(tmpFile6__);
        tempList.add(tmpFile7__);
        tempList.add(tmpFile8__);
        tempList.add(tmpFile9__);
        tempList.add(tmpFile10__);

        //添付ファイル
        for (FormFile tempFile : tempList) {
            __validateTemp(errors, con, gsMsg, tempFile);
        }
        return errors;
    }

    /**
     * <br>[機  能] 件名の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param title 件名
     * @return エラー true:有 false:無
     */
    private boolean __validateSmlTitle(String title) {
        boolean error = false;

        //未入力チェック
        if (StringUtil.isNullZeroString(title)) {
            error = true;
        }
        //MAX桁チェック
        if (!error && title.length() > GSConstCommon.MAX_LENGTH_SMLTITLE) {
            error = true;
        }
        //スペースのみチェック
        if (!error && ValidateUtil.isSpace(title)) {
            error = true;
        }
        //先頭スペースチェック
        if (!error && ValidateUtil.isSpaceStart(title)) {
            error = true;
        }
        //JIS第2水準チェック
        if (!error && !GSValidateUtil.isGsJapaneaseString(title)) {
            error = true;
        }
        return error;
    }

    /**
     * <br>[機  能] 本文の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param body 本文
     * @return エラー true:有 false:無
     */
    private boolean __validateSmlBody(String body) {

        boolean error = false;

        //未入力チェック
        if (StringUtil.isNullZeroString(body)) {
            error = true;
        }
        //MAX桁チェック
        if (!error && body.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            error = true;
        }

        //スペース、改行のみチェック
        if (!error && ValidateUtil.isSpaceOrKaigyou(body)) {
            error = true;
        }

        //JIS第2水準チェック
        if (!error && !GSValidateUtil.isGsJapaneaseStringTextArea(body)) {
            error = true;
        }

        return error;
    }

    /**
     * <br>[機  能] 添付ファイル入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors エラー
     * @param con コネクション
     * @param gsMsg GsMessage
     * @param file ファイル
     * @throws SQLException SQL実行時例外
     */
    private void __validateTemp(ActionErrors errors, Connection con,
                                 GsMessage gsMsg, FormFile file) throws SQLException {

        ActionMessage msg = null;
        String fileName = null;
        int    fileSize = 0;

        if (file == null) {
            return;
        }
        fileName = file.getFileName();
        fileSize = file.getFileSize();

        if (fileName.length() > GSConst.MAX_LENGTH_FILE) {
            //ファイル名
            String textFileName = gsMsg.getMessage("cmn.file.name");
            //ファイル名桁数チェック
            msg = new ActionMessage(
                    "error.input.length.text", textFileName, GSConst.MAX_LENGTH_FILE);
            StrutsUtil.addMessage(errors, msg, "error.input.length.text");
        } else if (!GSValidateUtil.isGsJapaneaseString(fileName)) {
            //ファイル名 使用文字チェック
            String textFileName = gsMsg.getMessage("cmn.file.name");
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(fileName);
            msg = new ActionMessage("error.input.njapan.text",
                        textFileName, nstr);
            StrutsUtil.addMessage(errors, msg, "error.file.name.char");
        } else {
            int maxSize = 0;
            CmnFileConfDao cfcDao = new CmnFileConfDao(con);
            //添付ファイル最大容量取得
            CmnFileConfModel cfcMdl = cfcDao.select();
            maxSize = cfcMdl.getFicMaxSize() * 1048576;
            if (fileSize > maxSize) {
                //指定されたファイルの容量が最大値を超えていた場合はエラーメッセージを表示
                msg = new ActionMessage("error.input.capacity.over", cfcMdl.getFicMaxSize() + "MB");
                StrutsUtil.addMessage(errors, msg, "cmn110file.error.input.capacity.over");
            }
        }
    }
}
package jp.groupsession.v2.api.chat.send;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cht.ChatValidate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessPermitDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessTargetDao;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] チャット投稿APIのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "chat-send",
plugin = "chat", name = "メッセージ送信",
url = "/api/chat/send.do", reqtype = "POST")
public class ApiChatSendForm extends AbstractApiForm {

    /** チャット区分 */
    @ApiParam(name = "sendKbn", viewName = "チャット区分")
    private String sendKbn__ = null;
    /** 区分ID */
    @ApiParam(name = "selectId", viewName = "区分ID")
    private String selectId__ = null;
    /** 投稿内容 */
    @ApiParam(name = "body", viewName = "投稿内容", required = false, confRequired = true)
    private String body__ = null;
    /** 添付ファイル */
    private FormFile tmpFile__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param umodel ユーザ情報モデル
     * @param gsMsg GsMessage
     * @param reqMdl RequestModel
     * @param fileFlg ファイル存在フラグ
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    public ActionErrors validateChatApi(
            Connection con, BaseUserModel umodel, GsMessage gsMsg,
            RequestModel reqMdl, boolean fileFlg)
    throws SQLException, IOToolsException {
        ActionErrors errors = new ActionErrors();
        int sendKbn = NullDefault.getInt(sendKbn__, -1);

        // チャット区分
        if (sendKbn != GSConstChat.CHAT_KBN_USER
                && sendKbn != GSConstChat.CHAT_KBN_GROUP) {
                   ActionMessage msg =  new ActionMessage("error.input.format.file",
                           gsMsg.getMessage("cht.01") + gsMsg.getMessage("tcd.tcd070.03"),
                           gsMsg.getMessage("main.man340.10"));
                   String eprefix = "sendKbn__";
                   StrutsUtil.addMessage(errors, msg, eprefix);
        }
        // 区分ID
        if (sendKbn == GSConstChat.CHAT_KBN_USER) {
            int selectSid = NullDefault.getInt(selectId__, -1);
            // ユーザ削除チェック
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            if (!cuDao.isExistUser(selectSid)) {
                ActionMessage msg = new ActionMessage("error.input.format.file",
                        gsMsg.getMessage("cmn.target.user"),
                        gsMsg.getMessage("cmn.user.sid"));
                errors.add("error.input.format.file", msg);
            }
            // 特例アクセス対象か
            ChtSpaccessTargetDao cstDao = new ChtSpaccessTargetDao(con);
            ArrayList<Integer> spList = cstDao.selectSid(selectSid);
            if (spList.size() != 0) {
                ChtSpaccessPermitDao cspDao = new ChtSpaccessPermitDao(con);
                int cntSid = cspDao.selectCount(spList,  umodel.getUsrsid());
                if (cntSid == 0) {
                    ActionMessage msg =  new ActionMessage("error.chat.not.send.permit.tokurei");
                    String eprefix = "tokurei__";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            }
            //ユーザチャット制限チェック
            ChtBiz cBiz = new ChtBiz(con, reqMdl);
            ChtAdmConfModel aMdl = cBiz.getChtAconf();
            if (aMdl.getCacChatFlg() == GSConstChat.LIMIT_BETWEEN_USERS) {
                ActionMessage msg =  new ActionMessage("error.chat.not.send.limit.user");
                String eprefix = "limitUserAcess__";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

            //プラグイン使用権限
            CommonBiz cmnBiz = new CommonBiz();
            boolean bAccess = false;
            List<String> menuPluginIdList = cmnBiz.getCanUsePluginIdList(con, selectSid);
            if (!menuPluginIdList.isEmpty()) {
                for (String pId : menuPluginIdList) {
                    if (pId.equals(GSConstChat.PLUGIN_ID_CHAT)) {
                        bAccess = true;
                        break;
                    }
                }
            }
            if (!bAccess) {
                ActionMessage msg =  new ActionMessage("error.chat.not.send.limit.plugin");
                String eprefix = "limitPluginUse__";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

        } else if (sendKbn == GSConstChat.CHAT_KBN_GROUP) {
            ChtGroupInfDao infDao = new ChtGroupInfDao(con);
            ChtGroupInfModel infMdl = infDao.selectWhereCgiId(selectId__);
            int grpSid = infMdl.getCgiSid();
            boolean chk = ChatValidate.validateIsExitChatGroup(grpSid, con);

            if (chk) {
               chk = ChatValidate.validateUserChtGrp(
                       GSConstChat.CHAT_KBN_GROUP, grpSid, umodel.getUsrsid(), con);
            }

            if (!chk) {
                ActionMessage msg
                =  new ActionMessage("search.data.notfound", gsMsg.getMessage("cmn.group"));
                String eprefix = "group__";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

            if (infMdl.getCgiCompFlg() == GSConstChat.CHAT_ARCHIVE_MODE) {
                ActionMessage msg =  new ActionMessage("error.chat.not.send.archive");
                String eprefix = "archive__";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

        }
        // 投稿内容
        errors = ChatValidate.validateApiTextarea(
                errors,
                body__,
                gsMsg.getMessage("cmn.message"),
                GSConstChat.MAX_LENGTH_MESSAGE,
                false);
        // 添付ファイル
        if (fileFlg) {
            errors = ChatValidate.validateTempFile(con, reqMdl, tmpFile__);
        }
        // 投稿添付存在確認
        if (StringUtil.isNullZeroStringSpace(body__) && !fileFlg) {
            ActionMessage msg
                = new ActionMessage("error.input.required.text", gsMsg.getMessage("cmn.message"));
            String eprefix = "message__";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        // 投稿同時送信確認
        if (!StringUtil.isNullZeroStringSpace(body__) && fileFlg) {
            ActionMessage msg
            = new ActionMessage("error.chat.not.send.limit.same");
        String eprefix = "same__";
        StrutsUtil.addMessage(errors, msg, eprefix);
        }

        return errors;
    }



    /**
     * <p>body を取得します。
     * @return body
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendForm#body__
     */
    public String getBody() {
        return body__;
    }

    /**
     * <p>body をセットします。
     * @param body body
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendForm#body__
     */
    public void setBody(String body) {
        body__ = body;
    }

    /**
     * <p>tmpFile を取得します。
     * @return tmpFile
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendForm#tmpFile__
     */
    public FormFile getTmpFile() {
        return tmpFile__;
    }

    /**
     * <p>tmpFile をセットします。
     * @param tmpFile tmpFile
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendForm#tmpFile__
     */
    public void setTmpFile(FormFile tmpFile) {
        tmpFile__ = tmpFile;
    }



    /**
     * <p>sendKbn を取得します。
     * @return sendKbn
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendForm#sendKbn__
     */
    public String getSendKbn() {
        return sendKbn__;
    }



    /**
     * <p>sendKbn をセットします。
     * @param sendKbn sendKbn
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendForm#sendKbn__
     */
    public void setSendKbn(String sendKbn) {
        sendKbn__ = sendKbn;
    }



    /**
     * <p>selectId を取得します。
     * @return selectId
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendForm#selectId__
     */
    public String getSelectId() {
        return selectId__;
    }



    /**
     * <p>selectId をセットします。
     * @param selectId selectId
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendForm#selectId__
     */
    public void setSelectId(String selectId) {
        selectId__ = selectId;
    }
}

package jp.groupsession.v2.api.chat.send;

import org.apache.struts.upload.FormFile;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] チャット投稿APIパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiChatSendParamModel extends AbstractParamModel {

    /** チャット区分 */
    private String sendKbn__ = null;
    /** 区分ID */
    private String selectId__ = null;
    /** 投稿内容 */
    private String body__ = null;
    /** 添付ファイル */
    private FormFile tmpFile__ = null;



    /**
     * <p>body を取得します。
     * @return body
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendParamModel#body__
     */
    public String getBody() {
        return body__;
    }
    /**
     * <p>body をセットします。
     * @param body body
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendParamModel#body__
     */
    public void setBody(String body) {
        body__ = body;
    }
    /**
     * <p>tmpFile を取得します。
     * @return tmpFile
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendParamModel#tmpFile__
     */
    public FormFile getTmpFile() {
        return tmpFile__;
    }
    /**
     * <p>tmpFile をセットします。
     * @param tmpFile tmpFile
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendParamModel#tmpFile__
     */
    public void setTmpFile(FormFile tmpFile) {
        tmpFile__ = tmpFile;
    }
    /**
     * <p>sendKbn を取得します。
     * @return sendKbn
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendParamModel#sendKbn__
     */
    public String getSendKbn() {
        return sendKbn__;
    }
    /**
     * <p>sendKbn をセットします。
     * @param sendKbn sendKbn
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendParamModel#sendKbn__
     */
    public void setSendKbn(String sendKbn) {
        sendKbn__ = sendKbn;
    }
    /**
     * <p>selectId を取得します。
     * @return selectId
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendParamModel#selectId__
     */
    public String getSelectId() {
        return selectId__;
    }
    /**
     * <p>selectId をセットします。
     * @param selectId selectId
     * @see jp.groupsession.v2.api.chat.send.ApiChatSendParamModel#selectId__
     */
    public void setSelectId(String selectId) {
        selectId__ = selectId;
    }

}

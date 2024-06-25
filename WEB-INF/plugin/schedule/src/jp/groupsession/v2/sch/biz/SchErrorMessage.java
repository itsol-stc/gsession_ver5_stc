package jp.groupsession.v2.sch.biz;

import org.apache.struts.action.ActionMessage;

/**
 *
 * <br>[機  能] 処理中に発生したエラーメッセージを格納
 * <br>[解  説] ActionMessageのキーとパラメータで構成
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchErrorMessage {
    /** 実行後エラーメッセージキー*/
    private String emsgKey__;

    /** 実行後エラーコードパラメータ*/
    private String[] emsgParam__;
    /**
     *
     * コンストラクタ 2つめ以降の引数はプレースホルダー用パラメータになります
     * @param msgKey
     * @param errorParams メッセージのプレースホルダー用パラメータ
     */
    public SchErrorMessage(String msgKey, String... errorParams) {
        emsgKey__ = msgKey;
        emsgParam__ = errorParams;
    }
    /**
     * <p>emsgKey を取得します。
     * @return emsgKey
     * @see jp.groupsession.v2.sch.biz.SchErrorMessage#emsgKey__
     */
    public synchronized String getEmsgKey() {
        return emsgKey__;
    }

    /**
     *
     * <br>[機  能] ActionMessageを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @return
     */
    public ActionMessage createMessage() {
        ActionMessage ret = null;
        if (emsgKey__ == null) {
            return null;
        }
        if (emsgParam__ != null && emsgParam__.length > 0) {
            ret = new ActionMessage(emsgKey__, emsgParam__);
        } else {
            ret = new ActionMessage(emsgKey__);
        }
        return ret;
    }
}
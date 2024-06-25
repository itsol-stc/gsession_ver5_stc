package jp.groupsession.v2.api.schedule.attend;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] スケジュール 出欠確認応答編集 WEBAPI パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchChangeAttendParamModel extends AbstractParamModel {
    /**Schsid スケジュールSID*/
    private String schSid__;

    /**
     * anser 出欠回答
     */
    private String anser__;

    /**
     * <p>schSid を取得します。
     * @return schSid
     */
    public String getSchSid() {
        return schSid__;
    }

    /**
     * <p>schSid をセットします。
     * @param schSid schSid
     */
    public void setSchSid(String schSid) {
        schSid__ = schSid;
    }

    /**
     * <p>anser を取得します。
     * @return anser
     */
    public String getAnser() {
        return anser__;
    }

    /**
     * <p>anser をセットします。
     * @param anser anser
     */
    public void setAnser(String anser) {
        anser__ = anser;
    }

}

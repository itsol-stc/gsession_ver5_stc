package jp.groupsession.v2.sch.restapi.entities;

import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;

/**  個別公開対象   ユーザ                                                                 */
@ResponceModel(targetField = {
        "id",
        "name",
        "seiText",
        "meiText",
        "seiKanaText",
        "meiKanaText",
        "loginStopFlg"
})
public class PublicTargetUserInf {
    /** ユーザ情報*/
    private CmnUserModel base__;
    /**
     *
     * コンストラクタ
     * @param cmnUserModel
     */
    public PublicTargetUserInf(CmnUserModel cmnUserModel) {
        base__ = cmnUserModel;
    }
    /** @return ID    */
    public String getId() {
        return base__.getUsrLgid();
    }
    /** @return 名前                                       */
    public String getName() {
        return base__.getUsiName();
    }
    /** @return 姓                                        */
    public String getSeiText() {
        return  base__.getUsiSei();
    }
    /** @return 名                                        */
    public String getMeiText() {
        return base__.getUsiMei();
    }
    /** @return 姓（カナ）                                        */
    public String getSeiKanaText() {
        return base__.getUsiSeiKn();
    }
    /** @return 名（カナ）                                        */
    public String getMeiKanaText() {
        return base__.getUsiMeiKn();
    }
    /** @return ログイン停止フラグ                                        */
    public int getLoginStopFlg() {
        return base__.getUsrUkoFlg();
    }
}
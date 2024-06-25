package jp.groupsession.v2.ntp.ntp061kn;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel;
/**
 * <br>[機  能] 日報 案件登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp061knParamModel extends Ntp061ParamModel {
    /** 案件詳細 表示用*/
    private String ntp061knNanSyosai__ = null;

    /** 権限区分 ユーザグループ選択 */
    private UserGroupSelectModel ntp061NanPermitUsrGrp__ = new UserGroupSelectModel();
    /** 担当者リスト */
    private ArrayList<CmnUsrmInfModel> ntp061knTantoList__ = null;

    /**
     * <p>ntp061knNanSyosai を取得します。
     * @return ntp061knNanSyosai
     */
    public String getNtp061knNanSyosai() {
        return ntp061knNanSyosai__;
    }

    /**
     * <p>ntp061knNanSyosai をセットします。
     * @param ntp061knNanSyosai ntp061knNanSyosai
     */
    public void setNtp061knNanSyosai(String ntp061knNanSyosai) {
        ntp061knNanSyosai__ = ntp061knNanSyosai;
    }

    public UserGroupSelectModel getNtp061NanPermitUsrGrp() {
        return ntp061NanPermitUsrGrp__;
    }

    public void setNtp061NanPermitUsrGrp(
            UserGroupSelectModel ntp061NanPermitUsrGrp) {
        ntp061NanPermitUsrGrp__ = ntp061NanPermitUsrGrp;
    }

    public ArrayList<CmnUsrmInfModel> getNtp061knTantoList() {
        return ntp061knTantoList__;
    }

    public void setNtp061knTantoList(ArrayList<CmnUsrmInfModel> ntp061knTantoList) {
        ntp061knTantoList__ = ntp061knTantoList;
    }
}
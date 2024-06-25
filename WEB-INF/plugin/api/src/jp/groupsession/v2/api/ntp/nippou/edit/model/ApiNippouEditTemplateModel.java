package jp.groupsession.v2.api.ntp.nippou.edit.model;

import java.util.List;

import jp.groupsession.v2.ntp.model.NtpPriTargetModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;

/**
 * <br>[機  能] WEBAPI 日報入力テンプレートモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouEditTemplateModel {
    /** テンプレート*/
    private NtpTemplateModel template__;
    /** 目標リスト */
    private List<NtpPriTargetModel> ntgList__;
    /**
     * <p>template を取得します。
     * @return template
     */
    public NtpTemplateModel getTemplate() {
        return template__;
    }
    /**
     * <p>template をセットします。
     * @param template template
     */
    public void setTemplate(NtpTemplateModel template) {
        template__ = template;
    }
    /**
     * <p>ntgList を取得します。
     * @return ntgList
     */
    public List<NtpPriTargetModel> getNtgList() {
        return ntgList__;
    }
    /**
     * <p>ntgList をセットします。
     * @param ntgList ntgList
     */
    public void setNtgList(List<NtpPriTargetModel> ntgList) {
        ntgList__ = ntgList;
    }

}

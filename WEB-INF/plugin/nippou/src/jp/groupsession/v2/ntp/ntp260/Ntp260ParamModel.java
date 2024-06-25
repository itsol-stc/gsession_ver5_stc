package jp.groupsession.v2.ntp.ntp260;

import java.util.List;

import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010ParamModel;

/**
 * <br>[機  能] 日報 テンプレート情報ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Ntp260ParamModel extends Ntp010ParamModel {

    /** テンプレートSID */
    private int ntp260NttSid__ = -1;
    /** テンプレート名 */
    private String ntp260TemplateName__ = null;
    /** テンプレート内容 */
    private String ntp260TemplateDetail__ = null;
    /** 項目名 */
    private List<String> ntp260ItemList__ = null;
    /** 目標 */
    private List<NtpTargetModel> ntp260TargetList__ = null;
    /** 適用グループ・ユーザ */
    private List<NtpLabelValueModel> ntp260GpUsrList__ = null;
    /**
     * <p>ntp260NttSid を取得します。
     * @return ntp260NttSid
     */
    public int getNtp260NttSid() {
        return ntp260NttSid__;
    }
    /**
     * <p>ntp260NttSid をセットします。
     * @param ntp260NttSid ntp260NttSid
     */
    public void setNtp260NttSid(int ntp260NttSid) {
        ntp260NttSid__ = ntp260NttSid;
    }
    /**
     * <p>ntp260TemplateName を取得します。
     * @return ntp260TemplateName
     */
    public String getNtp260TemplateName() {
        return ntp260TemplateName__;
    }
    /**
     * <p>ntp260TemplateName をセットします。
     * @param ntp260TemplateName ntp260TemplateName
     */
    public void setNtp260TemplateName(String ntp260TemplateName) {
        ntp260TemplateName__ = ntp260TemplateName;
    }
    /**
     * <p>ntp260TemplateDetail を取得します。
     * @return ntp260TemplateDetail
     */
    public String getNtp260TemplateDetail() {
        return ntp260TemplateDetail__;
    }
    /**
     * <p>ntp260TemplateDetail をセットします。
     * @param ntp260TemplateDetail ntp260TemplateDetail
     */
    public void setNtp260TemplateDetail(String ntp260TemplateDetail) {
        ntp260TemplateDetail__ = ntp260TemplateDetail;
    }
    /**
     * <p>ntp260ItemList を取得します。
     * @return ntp260ItemList
     */
    public List<String> getNtp260ItemList() {
        return ntp260ItemList__;
    }
    /**
     * <p>ntp260ItemList をセットします。
     * @param ntp260ItemList ntp260ItemList
     */
    public void setNtp260ItemList(List<String> ntp260ItemList) {
        ntp260ItemList__ = ntp260ItemList;
    }
    /**
     * <p>ntp260TargetList を取得します。
     * @return ntp260TargetList
     */
    public List<NtpTargetModel> getNtp260TargetList() {
        return ntp260TargetList__;
    }
    /**
     * <p>ntp260TargetList をセットします。
     * @param ntp260TargetList ntp260TargetList
     */
    public void setNtp260TargetList(List<NtpTargetModel> ntp260TargetList) {
        ntp260TargetList__ = ntp260TargetList;
    }
    /**
     * <p>ntp260GpUsrList を取得します。
     * @return ntp260GpUsrList
     */
    public List<NtpLabelValueModel> getNtp260GpUsrList() {
        return ntp260GpUsrList__;
    }
    /**
     * <p>ntp260GpUsrList をセットします。
     * @param ntp260GpUsrList ntp260GpUsrList
     */
    public void setNtp260GpUsrList(List<NtpLabelValueModel> ntp260GpUsrList) {
        ntp260GpUsrList__ = ntp260GpUsrList;
    }

}
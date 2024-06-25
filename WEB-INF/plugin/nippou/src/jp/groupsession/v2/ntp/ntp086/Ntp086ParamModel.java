package jp.groupsession.v2.ntp.ntp086;

import java.util.List;

import jp.groupsession.v2.ntp.ntp080.Ntp080ParamModel;

/**
 * <br>[機  能] 日報 日報テンプレート一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp086ParamModel extends Ntp080ParamModel {

    /** テンプレート一覧 */
    private List<Ntp086TemplateDspModel> ntp086TemplateList__ = null;
    /** テンプレートSID */
    private int ntp086NttSid__ = -1;
    /** チェックされている並び順 */
    private String ntp086sortTemplate__ = null;
    /** 並び替え対象のラベル */
    private String[] ntp086sortLabel__ = null;
    /** ページ番号 */
    private int ntp086pageNum__ = 1;


    /**
     * <p>ntp086sortLabel を取得します。
     * @return ntp086sortLabel
     */
    public String[] getNtp086sortLabel() {
        return ntp086sortLabel__;
    }
    /**
     * <p>ntp086sortLabel をセットします。
     * @param ntp086sortLabel ntp086sortLabel
     */
    public void setNtp086sortLabel(String[] ntp086sortLabel) {
        ntp086sortLabel__ = ntp086sortLabel;
    }
    /**
     * <p>ntp086TemplateList を取得します。
     * @return ntp086TemplateList
     */
    public List<Ntp086TemplateDspModel> getNtp086TemplateList() {
        return ntp086TemplateList__;
    }
    /**
     * <p>ntp086TemplateList をセットします。
     * @param ntp086TemplateList ntp086TemplateList
     */
    public void setNtp086TemplateList(
            List<Ntp086TemplateDspModel> ntp086TemplateList) {
        ntp086TemplateList__ = ntp086TemplateList;
    }
    /**
     * <p>ntp086NttSid を取得します。
     * @return ntp086NttSid
     */
    public int getNtp086NttSid() {
        return ntp086NttSid__;
    }
    /**
     * <p>ntp086NttSid をセットします。
     * @param ntp086NttSid ntp086NttSid
     */
    public void setNtp086NttSid(int ntp086NttSid) {
        ntp086NttSid__ = ntp086NttSid;
    }
    /**
     * <p>ntp086sortTemplate を取得します。
     * @return ntp086sortTemplate
     */
    public String getNtp086sortTemplate() {
        return ntp086sortTemplate__;
    }
    /**
     * <p>ntp086sortTemplate をセットします。
     * @param ntp086sortTemplate ntp086sortTemplate
     */
    public void setNtp086sortTemplate(String ntp086sortTemplate) {
        ntp086sortTemplate__ = ntp086sortTemplate;
    }
    /**
     * <p>ntp086pageNum を取得します。
     * @return ntp086pageNum
     */
    public int getNtp086pageNum() {
        return ntp086pageNum__;
    }
    /**
     * <p>ntp086pageNum をセットします。
     * @param ntp086pageNum ntp086pageNum
     */
    public void setNtp086pageNum(int ntp086pageNum) {
        ntp086pageNum__ = ntp086pageNum;
    }
}

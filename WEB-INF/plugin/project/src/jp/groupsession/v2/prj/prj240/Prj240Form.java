package jp.groupsession.v2.prj.prj240;

import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ユーザ選択画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj240Form extends AbstractGsForm {

    /** 呼び出し元画面URL */
    private String prj240BackUrl__ = null;
    /** 機能名称 */
    private String prj240FunctionName__ = null;
    /** 呼び出し元画面のフォーム識別子 */
    private String prj240FormKey__ = null;
    /** 呼び出し元画面に返すパラメータ名 */
    private String prj240paramName__ = "prj240userSid";
    /** 選択済みSID */
    private String[] prj240userSid__ = null;
    /** 選択済みSID(初期) */
    private String[] prj240userSidOld__ = null;
    /** ユーザ選択 グループ */
    private String prj240group__ = "-1";
    /** ユーザ選択 UI*/
    private UserGroupSelector prj240userUI__ =
            UserGroupSelector.builder()
                //ユーザ選択 日本語名（入力チェック時に使用）
                .chainLabel(new GsMessageReq("cmn.target", null))
                //ユーザ選択タイプ
                .chainType(EnumSelectType.USER)
                //グループ選択タイプ
                .chainGrpType(EnumGroupSelectType.WITHMYGROUP)
                //選択対象設定
                .chainSelect(Select.builder()
                        //ユーザSID保管パラメータ名
                        .chainParameterName(
                                "prj240userSid")
                        )
                //グループ選択保管パラメータ名
                .chainGroupSelectionParamName("prj240group")
                .build();
    /**
     * <p>prj240BackUrl を取得します。
     * @return prj240BackUrl
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240BackUrl__
     */
    public String getPrj240BackUrl() {
        return prj240BackUrl__;
    }
    /**
     * <p>prj240BackUrl をセットします。
     * @param prj240BackUrl prj240BackUrl
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240BackUrl__
     */
    public void setPrj240BackUrl(String prj240BackUrl) {
        prj240BackUrl__ = prj240BackUrl;
    }
    /**
     * <p>prj240FunctionName を取得します。
     * @return prj240FunctionName
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240FunctionName__
     */
    public String getPrj240FunctionName() {
        return prj240FunctionName__;
    }
    /**
     * <p>prj240FunctionName をセットします。
     * @param prj240FunctionName prj240FunctionName
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240FunctionName__
     */
    public void setPrj240FunctionName(String prj240FunctionName) {
        prj240FunctionName__ = prj240FunctionName;
    }
    /**
     * <p>prj240FormKey を取得します。
     * @return prj240FormKey
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240FormKey__
     */
    public String getPrj240FormKey() {
        return prj240FormKey__;
    }
    /**
     * <p>prj240FormKey をセットします。
     * @param prj240FormKey prj240FormKey
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240FormKey__
     */
    public void setPrj240FormKey(String prj240FormKey) {
        prj240FormKey__ = prj240FormKey;
    }
    /**
     * <p>prj240paramName を取得します。
     * @return prj240paramName
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240paramName__
     */
    public String getPrj240paramName() {
        return prj240paramName__;
    }
    /**
     * <p>prj240paramName をセットします。
     * @param prj240paramName prj240paramName
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240paramName__
     */
    public void setPrj240paramName(String prj240paramName) {
        prj240paramName__ = prj240paramName;
    }
    /**
     * <p>prj240userSid を取得します。
     * @return prj240userSid
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240userSid__
     */
    public String[] getPrj240userSid() {
        return prj240userSid__;
    }
    /**
     * <p>prj240userSid をセットします。
     * @param prj240userSid prj240userSid
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240userSid__
     */
    public void setPrj240userSid(String[] prj240userSid) {
        prj240userSid__ = prj240userSid;
    }
    /**
     * <p>prj240userSidOld を取得します。
     * @return prj240userSidOld
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240userSidOld__
     */
    public String[] getPrj240userSidOld() {
        return prj240userSidOld__;
    }
    /**
     * <p>prj240userSidOld をセットします。
     * @param prj240userSidOld prj240userSidOld
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240userSidOld__
     */
    public void setPrj240userSidOld(String[] prj240userSidOld) {
        prj240userSidOld__ = prj240userSidOld;
    }
    /**
     * <p>prj240group を取得します。
     * @return prj240group
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240group__
     */
    public String getPrj240group() {
        return prj240group__;
    }
    /**
     * <p>prj240group をセットします。
     * @param prj240group prj240group
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240group__
     */
    public void setPrj240group(String prj240group) {
        prj240group__ = prj240group;
    }
    /**
     * <p>prj240userUI を取得します。
     * @return prj240userUI
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240userUI__
     */
    public UserGroupSelector getPrj240userUI() {
        return prj240userUI__;
    }
    /**
     * <p>prj240userUI をセットします。
     * @param prj240userUI prj240userUI
     * @see jp.groupsession.v2.prj.prj240.Prj240Form#prj240userUI__
     */
    public void setPrj240userUI(UserGroupSelector prj240userUI) {
        prj240userUI__ = prj240userUI;
    }
}
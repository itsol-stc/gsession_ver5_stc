package jp.groupsession.v2.anp.anp080;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp070.Anp070ParamModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 管理者設定・基本設定画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp080ParamModel extends Anp070ParamModel {

    /** 遷移元(1:メッセージ配信) */
    private String anp080BackScreen__;

    /** 返信基本URL */
    private String anp080BaseUrl__;
    /** 返信基本URL(自動取得値保持用) */
    private String anp080SvBaseUrlAuto__;

    /** 基本URL設定区分 */
    private int anp080UrlSetKbn__;
    /** 送信メールアドレス */
    private String anp080SendMail__;
    /** メール送信サーバ */
    private String anp080SendHost__;
    /** メール送信サーバ ポート番号 */
    private String anp080SendPort__;
    /** メール送信サーバ 暗号化 */
    private int anp080SendEncrypt__;
    /** メール送信サーバ SMTP認証 */
    private String anp080SmtpAuth__;
    /** メール送信サーバ ユーザ */
    private String anp080SendUser__;
    /** メール送信サーバ パスワード */
    private String anp080SendPass__;

    /** 安否確認管理者グループコンボボックス選択SID */
    private String anp080SelectGroupSid__ = null;
    /** 安否確認管理者ユーザSIDリスト */
    private String[] anp080AdmUserList__ = null;

    /** 安否確認管理者ユーザリスト */
    private List <UsrLabelValueBean> anp080AdmUserLabel__ = null;
    /** 安否確認管理者 UI */
    private UserGroupSelector anp080AdmUserListUI__ = null;

    /** 暗号化プロトコル 一覧 */
    private List<LabelValueBean> anp080AngoProtocolCombo__ = null;

    /** 認証形式 */
    private int anp080authMethod = GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL;
    /** プロバイダ一覧 */
    private List<LabelValueBean> anp080providerList__ = null;
    /** プロバイダ */
    private int anp080provider__ = 0;
    /** OAuth認証済みフラグ */
    private int anp080oauthCompFlg__ = GSConstAnpi.AUTH_YET;
    /** OAuth認証情報トークンSID */
    private int anp080cotSid__ = 0;
    
    /**
     * <p>anp080BackScreen を取得します。
     * @return anp080BackScreen
     */
    public String getAnp080BackScreen() {
        return anp080BackScreen__;
    }
    /**
     * <p>anp080BackScreen をセットします。
     * @param anp080BackScreen anp080BackScreen
     */
    public void setAnp080BackScreen(String anp080BackScreen) {
        anp080BackScreen__ = anp080BackScreen;
    }
    /**
     * <p>anp080BaseUrl を取得します。
     * @return anp080BaseUrl
     */
    public String getAnp080BaseUrl() {
        return anp080BaseUrl__;
    }
    /**
     * <p>anp080BaseUrl をセットします。
     * @param anp080BaseUrl anp080BaseUrl
     */
    public void setAnp080BaseUrl(String anp080BaseUrl) {
        anp080BaseUrl__ = anp080BaseUrl;
    }
    /**
     * <p>anp080SvBaseUrlAuto を取得します。
     * @return anp080SvBaseUrlAuto
     */
    public String getAnp080SvBaseUrlAuto() {
        return anp080SvBaseUrlAuto__;
    }
    /**
     * <p>anp080SvBaseUrlAuto をセットします。
     * @param anp080SvBaseUrlAuto anp080SvBaseUrlAuto
     */
    public void setAnp080SvBaseUrlAuto(String anp080SvBaseUrlAuto) {
        anp080SvBaseUrlAuto__ = anp080SvBaseUrlAuto;
    }
    /**
     * <p>anp080UrlSetKbn を取得します。
     * @return anp080UrlSetKbn
     */
    public int getAnp080UrlSetKbn() {
        return anp080UrlSetKbn__;
    }
    /**
     * <p>anp080UrlSetKbn をセットします。
     * @param anp080UrlSetKbn anp080UrlSetKbn
     */
    public void setAnp080UrlSetKbn(int anp080UrlSetKbn) {
        anp080UrlSetKbn__ = anp080UrlSetKbn;
    }
    /**
     * <p>anp080SendMail を取得します。
     * @return anp080SendMail
     */
    public String getAnp080SendMail() {
        return anp080SendMail__;
    }
    /**
     * <p>anp080SendMail をセットします。
     * @param anp080SendMail anp080SendMail
     */
    public void setAnp080SendMail(String anp080SendMail) {
        anp080SendMail__ = anp080SendMail;
    }
    /**
     * <p>anp080SendHost を取得します。
     * @return anp080SendHost
     */
    public String getAnp080SendHost() {
        return anp080SendHost__;
    }
    /**
     * <p>anp080SendHost をセットします。
     * @param anp080SendHost anp080SendHost
     */
    public void setAnp080SendHost(String anp080SendHost) {
        anp080SendHost__ = anp080SendHost;
    }
    /**
     * <p>anp080SendPort を取得します。
     * @return anp080SendPort
     */
    public String getAnp080SendPort() {
        return anp080SendPort__;
    }
    /**
     * <p>anp080SendPort をセットします。
     * @param anp080SendPort anp080SendPort
     */
    public void setAnp080SendPort(String anp080SendPort) {
        anp080SendPort__ = anp080SendPort;
    }

    /**
     * <p>anp080SmtpAuth を取得します。
     * @return anp080SmtpAuth
     */
    public String getAnp080SmtpAuth() {
        return anp080SmtpAuth__;
    }
    /**
     * <p>anp080SmtpAuth をセットします。
     * @param anp080SmtpAuth anp080SmtpAuth
     */
    public void setAnp080SmtpAuth(String anp080SmtpAuth) {
        anp080SmtpAuth__ = anp080SmtpAuth;
    }
    /**
     * <p>anp080SendUser を取得します。
     * @return anp080SendUser
     */
    public String getAnp080SendUser() {
        return anp080SendUser__;
    }
    /**
     * <p>anp080SendUser をセットします。
     * @param anp080SendUser anp080SendUser
     */
    public void setAnp080SendUser(String anp080SendUser) {
        anp080SendUser__ = anp080SendUser;
    }
    /**
     * <p>anp080SendPass を取得します。
     * @return anp080SendPass
     */
    public String getAnp080SendPass() {
        return anp080SendPass__;
    }
    /**
     * <p>anp080SendPass をセットします。
     * @param anp080SendPass anp080SendPass
     */
    public void setAnp080SendPass(String anp080SendPass) {
        anp080SendPass__ = anp080SendPass;
    }
    /**
     * <p>anp080SelectGroupSid を取得します。
     * @return anp080SelectGroupSid
     */
    public String getAnp080SelectGroupSid() {
        return anp080SelectGroupSid__;
    }
    /**
     * <p>anp080SelectGroupSid をセットします。
     * @param anp080SelectGroupSid anp080SelectGroupSid
     */
    public void setAnp080SelectGroupSid(String anp080SelectGroupSid) {
        anp080SelectGroupSid__ = anp080SelectGroupSid;
    }
    /**
     * <p>anp080AdmUserList を取得します。
     * @return anp080AdmUserList
     */
    public String[] getAnp080AdmUserList() {
        return anp080AdmUserList__;
    }
    /**
     * <p>anp080AdmUserList をセットします。
     * @param anp080AdmUserList anp080AdmUserList
     */
    public void setAnp080AdmUserList(String[] anp080AdmUserList) {
        anp080AdmUserList__ = anp080AdmUserList;
    }
    /**
     * <p>anp080AdmUserLabel を取得します。
     * @return anp080AdmUserLabel
     */
    public List<UsrLabelValueBean> getAnp080AdmUserLabel() {
        return anp080AdmUserLabel__;
    }
    /**
     * <p>anp080AdmUserLabel をセットします。
     * @param anp080AdmUserLabel anp080AdmUserLabel
     */
    public void setAnp080AdmUserLabel(List<UsrLabelValueBean> anp080AdmUserLabel) {
        anp080AdmUserLabel__ = anp080AdmUserLabel;
    }
    /**
     * <p>anp080AdmUserListUI を取得します。
     * @return anp080AdmUserListUI
     */
    public UserGroupSelector getAnp080AdmUserListUI() {
        return anp080AdmUserListUI__;
    }
    /**
     * <p>anp080AdmUserListUI をセットします。
     * @param anp080AdmUserListUI anp080AdmUserListUI
     */
    public void setAnp080AdmUserListUI(UserGroupSelector anp080AdmUserListUI) {
        anp080AdmUserListUI__ = anp080AdmUserListUI;
    }
    /**
     * <p>anp080SendEncrypt を取得します。
     * @return anp080SendEncrypt
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080SendEncrypt__
     */
    public int getAnp080SendEncrypt() {
        return anp080SendEncrypt__;
    }
    /**
     * <p>anp080SendEncrypt をセットします。
     * @param anp080SendEncrypt anp080SendEncrypt
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080SendEncrypt__
     */
    public void setAnp080SendEncrypt(int anp080SendEncrypt) {
        anp080SendEncrypt__ = anp080SendEncrypt;
    }
    /**
     * <p>anp080AngoProtocolCombo を取得します。
     * @return anp080AngoProtocolCombo
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080AngoProtocolCombo__
     */
    public List<LabelValueBean> getAnp080AngoProtocolCombo() {
        return anp080AngoProtocolCombo__;
    }
    /**
     * <p>anp080AngoProtocolCombo をセットします。
     * @param anp080AngoProtocolCombo anp080AngoProtocolCombo
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080AngoProtocolCombo__
     */
    public void setAnp080AngoProtocolCombo(
            List<LabelValueBean> anp080AngoProtocolCombo) {
        anp080AngoProtocolCombo__ = anp080AngoProtocolCombo;
    }
    /**
     * <p>anp080authMethod を取得します。
     * @return anp080authMethod
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080authMethod
     */
    public int getAnp080authMethod() {
        return anp080authMethod;
    }
    /**
     * <p>anp080authMethod をセットします。
     * @param anp080authMethod anp080authMethod
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080authMethod
     */
    public void setAnp080authMethod(int anp080authMethod) {
        this.anp080authMethod = anp080authMethod;
    }
    /**
     * <p>anp080providerList を取得します。
     * @return anp080providerList
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080providerList__
     */
    public List<LabelValueBean> getAnp080providerList() {
        return anp080providerList__;
    }
    /**
     * <p>anp080providerList をセットします。
     * @param anp080providerList anp080providerList
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080providerList__
     */
    public void setAnp080providerList(List<LabelValueBean> anp080providerList) {
        anp080providerList__ = anp080providerList;
    }
    /**
     * <p>anp080provider を取得します。
     * @return anp080provider
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080provider__
     */
    public int getAnp080provider() {
        return anp080provider__;
    }
    /**
     * <p>anp080provider をセットします。
     * @param anp080provider anp080provider
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080provider__
     */
    public void setAnp080provider(int anp080provider) {
        anp080provider__ = anp080provider;
    }
    /**
     * <p>anp080oauthCompFlg を取得します。
     * @return anp080oauthCompFlg
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080oauthCompFlg__
     */
    public int getAnp080oauthCompFlg() {
        return anp080oauthCompFlg__;
    }
    /**
     * <p>anp080oauthCompFlg をセットします。
     * @param anp080oauthCompFlg anp080oauthCompFlg
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080oauthCompFlg__
     */
    public void setAnp080oauthCompFlg(int anp080oauthCompFlg) {
        anp080oauthCompFlg__ = anp080oauthCompFlg;
    }
    /**
     * <p>anp080cotSid を取得します。
     * @return anp080cotSid
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080cotSid__
     */
    public int getAnp080cotSid() {
        return anp080cotSid__;
    }
    /**
     * <p>anp080cotSid をセットします。
     * @param anp080cotSid anp080cotSid
     * @see jp.groupsession.v2.anp.anp080.Anp080ParamModel#anp080cotSid__
     */
    public void setAnp080cotSid(int anp080cotSid) {
        anp080cotSid__ = anp080cotSid;
    }
}
package jp.groupsession.v2.api.api030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.api010.Api010Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] トークン管理 フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api030Form extends Api010Form {
    //検索保管
    /** 検索保管 クライアント CrossRide*/
    private int api030cliantCRSv__ = UserAgent.CLIENT_TYPE_NOSEL;
    /** 検索保管 クライアント GSモバイル*/
    private int api030cliantAppSv__ = UserAgent.CLIENT_TYPE_NOSEL;
    /** 検索保管 クライアント その他*/
    private int api030cliantOtherSv__ = UserAgent.CLIENT_TYPE_NOSEL;
    /** 検索保管 ユーザ ユーザ*/
    private int api030userSv__ = -1;
    /** 検索保管 ユーザ グループ*/
    private int api030groupSv__ = -1;
    /** 検索保管 ソートキー*/
    private int api030sortKeySv__ = Api030Biz.TOKEN_SORTKEY_ADATE;
    /** 検索保管 オーダーキー*/
    private int api030orderKeySv__;
    /** 検索補完 対象 無効化を除く */
    private int api030targetDisabledSv__ = Api030Biz.SEARCH_TARGET_DISABLED_OFF;
    //検索
    /** 検索 クライアント CrossRide*/
    private int api030cliantCR__ = UserAgent.CLIENT_TYPE_NOSEL;
    /** 検索 クライアント GSモバイル*/
    private int api030cliantApp__ = UserAgent.CLIENT_TYPE_NOSEL;
    /** 検索 クライアント その他*/
    private int api030cliantOther__ = UserAgent.CLIENT_TYPE_NOSEL;
    /** 検索 ユーザ*/
    private int api030user__ = -1;
    /** 検索 ユーザ*/
    private int api030group__ = -1;
    /** 検索 ソートキー*/
    private int api030sortKey__ = Api030Biz.TOKEN_SORTKEY_ADATE;
    /** 検索 オーダーキー*/
    private int api030orderKey__;
    /** 検索 対象 無効化を除く */
    private int api030targetDisabled__ = Api030Biz.SEARCH_TARGET_DISABLED_OFF;
    /** 検索 ページ*/
    private int api030page__;
    /** 検索 ページ選択*/
    private int api030pageSel__;
    //input
    /** 削除対象単体*/
    private String api030delete__;
    /** 削除対象複数*/
    private String[] api030delMulti__;
    //表示
    /** ソートキーラベル */
    private List<LabelValueBean> sortLabel__;
    /** ページラベル */
    private List<LabelValueBean> api030PageLabel__;
    /** グループラベル */
    private List<LabelValueBean> api030groupLabel__;
    /** ユーザラベル */
    private List<UsrLabelValueBean> api030usrLabel__;
    /** 検索結果 */
    private List<Api030TokenModel> api030DspList__;

    //トークン発行
    /** トークン発行 ユーザ*/
    private int api030createTokenUser__ = -1;
    /** トークン発行 グループ*/
    private int api030createTokenGroup__ = -1;
    /** グループラベル */
    private List<LabelValueBean> api030tokenGroupLabel__;
    /** ユーザラベル */
    private List<UsrLabelValueBean> api030tokenUserLabel__;

    /** api連携呼び出しフラグ */
    private int api030cmnCallFlg__ = 0;


    /**
     * <p>api030cliantCRSv を取得します。
     * @return api030cliantCRSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantCRSv__
     */
    public int getApi030cliantCRSv() {
        return api030cliantCRSv__;
    }
    /**
     * <p>api030cliantCRSv をセットします。
     * @param api030cliantCRSv api030cliantCRSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantCRSv__
     */
    public void setApi030cliantCRSv(int api030cliantCRSv) {
        api030cliantCRSv__ = api030cliantCRSv;
    }
    /**
     * <p>api030cliantAppSv を取得します。
     * @return api030cliantAppSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantAppSv__
     */
    public int getApi030cliantAppSv() {
        return api030cliantAppSv__;
    }
    /**
     * <p>api030cliantAppSv をセットします。
     * @param api030cliantAppSv api030cliantAppSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantAppSv__
     */
    public void setApi030cliantAppSv(int api030cliantAppSv) {
        api030cliantAppSv__ = api030cliantAppSv;
    }
    /**
     * <p>api030cliantOtherSv を取得します。
     * @return api030cliantOtherSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantOtherSv__
     */
    public int getApi030cliantOtherSv() {
        return api030cliantOtherSv__;
    }
    /**
     * <p>api030cliantOtherSv をセットします。
     * @param api030cliantOtherSv api030cliantOtherSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantOtherSv__
     */
    public void setApi030cliantOtherSv(int api030cliantOtherSv) {
        api030cliantOtherSv__ = api030cliantOtherSv;
    }
    /**
     * <p>api030userSv を取得します。
     * @return api030userSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030userSv__
     */
    public int getApi030userSv() {
        return api030userSv__;
    }
    /**
     * <p>api030userSv をセットします。
     * @param api030userSv api030userSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030userSv__
     */
    public void setApi030userSv(int api030userSv) {
        api030userSv__ = api030userSv;
    }
    /**
     * <p>api030groupSv を取得します。
     * @return api030groupSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030groupSv__
     */
    public int getApi030groupSv() {
        return api030groupSv__;
    }
    /**
     * <p>api030groupSv をセットします。
     * @param api030groupSv api030groupSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030groupSv__
     */
    public void setApi030groupSv(int api030groupSv) {
        api030groupSv__ = api030groupSv;
    }
    /**
     * <p>api030sortKeySv を取得します。
     * @return api030sortKeySv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030sortKeySv__
     */
    public int getApi030sortKeySv() {
        return api030sortKeySv__;
    }
    /**
     * <p>api030sortKeySv をセットします。
     * @param api030sortKeySv api030sortKeySv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030sortKeySv__
     */
    public void setApi030sortKeySv(int api030sortKeySv) {
        api030sortKeySv__ = api030sortKeySv;
    }
    /**
     * <p>api030orderKeySv を取得します。
     * @return api030orderKeySv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030orderKeySv__
     */
    public int getApi030orderKeySv() {
        return api030orderKeySv__;
    }
    /**
     * <p>api030orderKeySv をセットします。
     * @param api030orderKeySv api030orderKeySv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030orderKeySv__
     */
    public void setApi030orderKeySv(int api030orderKeySv) {
        api030orderKeySv__ = api030orderKeySv;
    }
    /**
     * <p>api030targetDisabledSv を取得します。
     * @return api030targetDisabledSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030targetDisabledSv__
     */
    public int getApi030targetDisabledSv() {
        return api030targetDisabledSv__;
    }
    /**
     * <p>api030targetDisabledSv をセットします。
     * @param api030targetDisabledSv api030targetDisabledSv
     * @see jp.groupsession.v2.api.api030.Api030Form#api030targetDisabledSv__
     */
    public void setApi030targetDisabledSv(int api030targetDisabledSv) {
        api030targetDisabledSv__ = api030targetDisabledSv;
    }
    /**
     * <p>api030cliantCR を取得します。
     * @return api030cliantCR
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantCR__
     */
    public int getApi030cliantCR() {
        return api030cliantCR__;
    }
    /**
     * <p>api030cliantCR をセットします。
     * @param api030cliantCR api030cliantCR
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantCR__
     */
    public void setApi030cliantCR(int api030cliantCR) {
        api030cliantCR__ = api030cliantCR;
    }
    /**
     * <p>api030cliantApp を取得します。
     * @return api030cliantApp
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantApp__
     */
    public int getApi030cliantApp() {
        return api030cliantApp__;
    }
    /**
     * <p>api030cliantApp をセットします。
     * @param api030cliantApp api030cliantApp
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantApp__
     */
    public void setApi030cliantApp(int api030cliantApp) {
        api030cliantApp__ = api030cliantApp;
    }
    /**
     * <p>api030cliantOther を取得します。
     * @return api030cliantOther
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantOther__
     */
    public int getApi030cliantOther() {
        return api030cliantOther__;
    }
    /**
     * <p>api030cliantOther をセットします。
     * @param api030cliantOther api030cliantOther
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cliantOther__
     */
    public void setApi030cliantOther(int api030cliantOther) {
        api030cliantOther__ = api030cliantOther;
    }
    /**
     * <p>api030sortKey を取得します。
     * @return api030sortKey
     * @see jp.groupsession.v2.api.api030.Api030Form#api030sortKey__
     */
    public int getApi030sortKey() {
        return api030sortKey__;
    }
    /**
     * <p>api030sortKey をセットします。
     * @param api030sortKey api030sortKey
     * @see jp.groupsession.v2.api.api030.Api030Form#api030sortKey__
     */
    public void setApi030sortKey(int api030sortKey) {
        api030sortKey__ = api030sortKey;
    }
    /**
     * <p>api030orderKey を取得します。
     * @return api030orderKey
     * @see jp.groupsession.v2.api.api030.Api030Form#api030orderKey__
     */
    public int getApi030orderKey() {
        return api030orderKey__;
    }
    /**
     * <p>api030orderKey をセットします。
     * @param api030orderKey api030orderKey
     * @see jp.groupsession.v2.api.api030.Api030Form#api030orderKey__
     */
    public void setApi030orderKey(int api030orderKey) {
        api030orderKey__ = api030orderKey;
    }
    /**
     * <p>api030page を取得します。
     * @return api030page
     * @see jp.groupsession.v2.api.api030.Api030Form#api030page__
     */
    public int getApi030page() {
        return api030page__;
    }
    /**
     * <p>api030page をセットします。
     * @param api030page api030page
     * @see jp.groupsession.v2.api.api030.Api030Form#api030page__
     */
    public void setApi030page(int api030page) {
        api030page__ = api030page;
    }
    /**
     * <p>api030targetDisabled を取得します。
     * @return api030targetDisabled
     * @see jp.groupsession.v2.api.api030.Api030ParamModel#api030targetDisabled__
     */
    public int getApi030targetDisabled() {
        return api030targetDisabled__;
    }
    /**
     * <p>api030targetDisabled をセットします。
     * @param api030targetDisabled api030targetDisabled
     * @see jp.groupsession.v2.api.api030.Api030ParamModel#api030targetDisabled__
     */
    public void setApi030targetDisabled(int api030targetDisabled) {
        api030targetDisabled__ = api030targetDisabled;
    }
    /**
     * <p>api030delete を取得します。
     * @return api030delete
     * @see jp.groupsession.v2.api.api030.Api030Form#api030delete__
     */
    public String getApi030delete() {
        return api030delete__;
    }
    /**
     * <p>api030delete をセットします。
     * @param api030delete api030delete
     * @see jp.groupsession.v2.api.api030.Api030Form#api030delete__
     */
    public void setApi030delete(String api030delete) {
        api030delete__ = api030delete;
    }
    /**
     * <p>api030delMulti を取得します。
     * @return api030delMulti
     * @see jp.groupsession.v2.api.api030.Api030Form#api030delMulti__
     */
    public String[] getApi030delMulti() {
        return api030delMulti__;
    }
    /**
     * <p>api030delMulti をセットします。
     * @param api030delMulti api030delMulti
     * @see jp.groupsession.v2.api.api030.Api030Form#api030delMulti__
     */
    public void setApi030delMulti(String[] api030delMulti) {
        api030delMulti__ = api030delMulti;
    }
    /**
     * <p>sortLabel を取得します。
     * @return sortLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#sortLabel__
     */
    public List<LabelValueBean> getSortLabel() {
        return sortLabel__;
    }
    /**
     * <p>sortLabel をセットします。
     * @param sortLabel sortLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#sortLabel__
     */
    public void setSortLabel(List<LabelValueBean> sortLabel) {
        sortLabel__ = sortLabel;
    }
    /**
     * <p>api030PageLabel を取得します。
     * @return api030PageLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030PageLabel__
     */
    public List<LabelValueBean> getApi030PageLabel() {
        return api030PageLabel__;
    }
    /**
     * <p>api030PageLabel をセットします。
     * @param api030PageLabel api030PageLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030PageLabel__
     */
    public void setApi030PageLabel(List<LabelValueBean> api030PageLabel) {
        api030PageLabel__ = api030PageLabel;
    }
    /**
     * <p>api030DspList を取得します。
     * @return api030DspList
     * @see jp.groupsession.v2.api.api030.Api030Form#api030DspList__
     */
    public List<Api030TokenModel> getApi030DspList() {
        return api030DspList__;
    }
    /**
     * <p>api030DspList をセットします。
     * @param api030DspList api030DspList
     * @see jp.groupsession.v2.api.api030.Api030Form#api030DspList__
     */
    public void setApi030DspList(List<Api030TokenModel> api030DspList) {
        api030DspList__ = api030DspList;
    }
    /**
    *
    * <br>[機  能] 入力チェックを行う
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl リクエストモデル
    * @param con コネクション
    * @return エラー
     * @throws SQLException SQL実行時例外
    */
    public ActionErrors validateSearch(RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        //クライアント
        boolean check = false;
        switch (api030cliantOther__) {
        case UserAgent.CLIENT_TYPE_CROSSRIDE:
        case UserAgent.CLIENT_TYPE_GSMOBILE:
        case UserAgent.CLIENT_TYPE_OTHER:
        case UserAgent.CLIENT_TYPE_NOSEL:
            break;
        default:
            check = true;
        }
        switch (api030cliantCR__) {
        case UserAgent.CLIENT_TYPE_CROSSRIDE:
        case UserAgent.CLIENT_TYPE_GSMOBILE:
        case UserAgent.CLIENT_TYPE_OTHER:
        case UserAgent.CLIENT_TYPE_NOSEL:
            break;
        default:
            check = true;
        }
        switch (api030cliantApp__) {
        case UserAgent.CLIENT_TYPE_CROSSRIDE:
        case UserAgent.CLIENT_TYPE_GSMOBILE:
        case UserAgent.CLIENT_TYPE_OTHER:
        case UserAgent.CLIENT_TYPE_NOSEL:
            break;
        default:
            check = true;
        }
        if (check) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.input.notvalidate.data",
                            gsMsg.getMessage("cmn.client")
                            ),
                    "api030sortKey");

        }
        //ユーザ
        GSValidateUser.validateSelectGroup(errors, api030group__, con, reqMdl);
        if (api030user__ != -1) {
            UserGroupSelectBiz usrBiz = new UserGroupSelectBiz();
            if (!usrBiz.checkUser(con, api030user__)) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage(
                                "error.input.notvalidate.data",
                                gsMsg.getMessage("cmn.user")
                                ),
                        "api030sortKey");
            }
        }

        //ソート順
        check = false;
        switch (api030sortKey__) {
         case Api030Biz.TOKEN_SORTKEY_USER:
         case Api030Biz.TOKEN_SORTKEY_CLIENT:
         case Api030Biz.TOKEN_SORTKEY_ADATE:
         case Api030Biz.TOKEN_SORTKEY_LDATE:
             break;
         default:
             check = true;

        }
        switch (api030orderKey__) {
        case GSConst.ORDER_KEY_ASC:
        case GSConst.ORDER_KEY_DESC:
            break;
        default:
            check = true;

        }
        if (check) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.input.notvalidate.data",
                            gsMsg.getMessage("cmn.sort.order")
                            ),
                    "api030sortKey");

        }

        //検索対象
        if (api030targetDisabled__ != Api030Biz.SEARCH_TARGET_DISABLED_OFF
                && api030targetDisabled__ != Api030Biz.SEARCH_TARGET_DISABLED_ON) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.input.required.text",
                            gsMsg.getMessage("cmn.search.criteria")
                            ),
                    "api030targetDisabled");
        }
        return errors;
    }
    /**
     *
     * <br>[機  能] 入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラー
     */
    public ActionErrors validateDelete(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (StringUtil.isNullZeroString(api030delete__)) {
            if (api030delMulti__ == null
                    || api030delMulti__.length <= 0) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.input.selectoen.check",
                                gsMsg.getMessage("cmn.token")
                                ),
                        "api030delMulti__");
            }
        }
        return errors;
    }
    /**
     * <p>api030pageSel を取得します。
     * @return api030pageSel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030pageSel__
     */
    public int getApi030pageSel() {
        return api030pageSel__;
    }
    /**
     * <p>api030pageSel をセットします。
     * @param api030pageSel api030pageSel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030pageSel__
     */
    public void setApi030pageSel(int api030pageSel) {
        api030pageSel__ = api030pageSel;
    }
    /**
     * <p>api030user を取得します。
     * @return api030user
     * @see jp.groupsession.v2.api.api030.Api030Form#api030user__
     */
    public int getApi030user() {
        return api030user__;
    }
    /**
     * <p>api030user をセットします。
     * @param api030user api030user
     * @see jp.groupsession.v2.api.api030.Api030Form#api030user__
     */
    public void setApi030user(int api030user) {
        api030user__ = api030user;
    }
    /**
     * <p>api030group を取得します。
     * @return api030group
     * @see jp.groupsession.v2.api.api030.Api030Form#api030group__
     */
    public int getApi030group() {
        return api030group__;
    }
    /**
     * <p>api030group をセットします。
     * @param api030group api030group
     * @see jp.groupsession.v2.api.api030.Api030Form#api030group__
     */
    public void setApi030group(int api030group) {
        api030group__ = api030group;
    }
    /**
     * <p>api030groupLabel を取得します。
     * @return api030groupLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030groupLabel__
     */
    public List<LabelValueBean> getApi030groupLabel() {
        return api030groupLabel__;
    }
    /**
     * <p>api030groupLabel をセットします。
     * @param api030groupLabel api030groupLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030groupLabel__
     */
    public void setApi030groupLabel(List<LabelValueBean> api030groupLabel) {
        api030groupLabel__ = api030groupLabel;
    }
    /**
     * <p>api030usrLabel を取得します。
     * @return api030usrLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030usrLabel__
     */
    public List<UsrLabelValueBean> getApi030usrLabel() {
        return api030usrLabel__;
    }
    /**
     * <p>api030usrLabel をセットします。
     * @param api030usrLabel api030usrLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030usrLabel__
     */
    public void setApi030usrLabel(List<UsrLabelValueBean> api030usrLabel) {
        api030usrLabel__ = api030usrLabel;
    }
    /**
     * <p>api030createTokenUser を取得します。
     * @return api030createTokenUser
     * @see jp.groupsession.v2.api.api030.Api030Form#api030createTokenUser__
     */
    public int getApi030createTokenUser() {
        return api030createTokenUser__;
    }
    /**
     * <p>api030createTokenUser をセットします。
     * @param api030createTokenUser api030createTokenUser
     * @see jp.groupsession.v2.api.api030.Api030Form#api030createTokenUser__
     */
    public void setApi030createTokenUser(int api030createTokenUser) {
        api030createTokenUser__ = api030createTokenUser;
    }
    /**
     * <p>api030createTokenGroup を取得します。
     * @return api030createTokenGroup
     * @see jp.groupsession.v2.api.api030.Api030Form#api030createTokenGroup__
     */
    public int getApi030createTokenGroup() {
        return api030createTokenGroup__;
    }
    /**
     * <p>api030createTokenGroup をセットします。
     * @param api030createTokenGroup api030createTokenGroup
     * @see jp.groupsession.v2.api.api030.Api030Form#api030createTokenGroup__
     */
    public void setApi030createTokenGroup(int api030createTokenGroup) {
        api030createTokenGroup__ = api030createTokenGroup;
    }
    /**
     * <p>api030tokenGroupLabel を取得します。
     * @return api030tokenGroupLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030tokenGroupLabel__
     */
    public List<LabelValueBean> getApi030tokenGroupLabel() {
        return api030tokenGroupLabel__;
    }
    /**
     * <p>api030tokenGroupLabel をセットします。
     * @param api030tokenGroupLabel api030tokenGroupLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030tokenGroupLabel__
     */
    public void setApi030tokenGroupLabel(
            List<LabelValueBean> api030tokenGroupLabel) {
        api030tokenGroupLabel__ = api030tokenGroupLabel;
    }
    /**
     * <p>api030tokenUserLabel を取得します。
     * @return api030tokenUserLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030tokenUserLabel__
     */
    public List<UsrLabelValueBean> getApi030tokenUserLabel() {
        return api030tokenUserLabel__;
    }
    /**
     * <p>api030tokenUserLabel をセットします。
     * @param api030tokenUserLabel api030tokenUserLabel
     * @see jp.groupsession.v2.api.api030.Api030Form#api030tokenUserLabel__
     */
    public void setApi030tokenUserLabel(
            List<UsrLabelValueBean> api030tokenUserLabel) {
        api030tokenUserLabel__ = api030tokenUserLabel;
    }
    /**
     * <p>api030cmnCallFlg を取得します。
     * @return api030cmnCallFlg
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cmnCallFlg__
     */
    public int getApi030cmnCallFlg() {
        return api030cmnCallFlg__;
    }
    /**
     * <p>api030cmnCallFlg をセットします。
     * @param api030cmnCallFlg api030cmnCallFlg
     * @see jp.groupsession.v2.api.api030.Api030Form#api030cmnCallFlg__
     */
    public void setApi030cmnCallFlg(int api030cmnCallFlg) {
        api030cmnCallFlg__ = api030cmnCallFlg;
    }



}

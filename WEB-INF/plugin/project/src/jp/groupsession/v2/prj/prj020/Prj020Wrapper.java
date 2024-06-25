package jp.groupsession.v2.prj.prj020;

import java.io.IOException;
import java.io.Serializable;

import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class Prj020Wrapper implements Serializable {
    private void writeObject(java.io.ObjectOutputStream out)
        throws IOException {
    }
    private void readObject(java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException {
    }

    public Prj020Wrapper() {

    }

    /** 所属メンバー UI*/
    private UserGroupSelector prj020hdnMemberUI__ =
            UserGroupSelector.builder()
                //ユーザ選択 日本語名（入力チェック時に使用）
                .chainLabel(new GsMessageReq("cmn.squad", null))
                //ユーザ選択タイプ
                .chainType(EnumSelectType.USER)
                //選択対象設定
                .chainSelect(Select.builder()
                        //ユーザSID保管パラメータ名
                        .chainParameterName(
                                "prj020hdnMemberSid")
                        )
                //グループ選択保管パラメータ名
                .chainGroupSelectionParamName("prj020group")
                .build();

    /** プロジェクト管理者 UI*/
    private Prj020AdminMemberSelector prj020adminSelectUI__ =
            Prj020AdminMemberSelector.builder()
                .chainLabel(new GsMessageReq("cmn.member", null))
                .chainGroupSelectionParamName("prj020groupSidAdm")
                .chainSelect(
                        Select.builder()
                            .chainParameterName(
                                    "prj020hdnAdmin")
                            )
                .build();

    /**
     * <p>prj020hdnMemberUI を取得します。
     * @return prj020hdnMemberUI
     * @see jp.groupsession.v2.prj.prj020.Prj020AdminMemberSelectorWrapper#prj020hdnMemberUI__
     */
    public UserGroupSelector getPrj020hdnMemberUI() {
        return prj020hdnMemberUI__;
    }
    /**
     * <p>prj020hdnMemberUI をセットします。
     * @param prj020hdnMemberUI prj020hdnMemberUI
     * @see jp.groupsession.v2.prj.prj020.Prj020AdminMemberSelectorWrapper#prj020hdnMemberUI__
     */
    public void setPrj020hdnMemberUI(UserGroupSelector prj020hdnMemberUI) {
        prj020hdnMemberUI__ = prj020hdnMemberUI;
    }
    /**
     * <p>prj020adminSelectUI を取得します。
     * @return prj020adminSelectUI
     * @see jp.groupsession.v2.prj.prj020.Prj020AdminMemberSelectorWrapper#prj020adminSelectUI__
     */
    public Prj020AdminMemberSelector getPrj020adminSelectUI() {
        return prj020adminSelectUI__;
    }
    /**
     * <p>prj020adminSelectUI をセットします。
     * @param prj020adminSelectUI prj020adminSelectUI
     * @see jp.groupsession.v2.prj.prj020.Prj020AdminMemberSelectorWrapper#prj020adminSelectUI__
     */
    public void setPrj020adminSelectUI(
            Prj020AdminMemberSelector prj020adminSelectUI) {
        prj020adminSelectUI__ = prj020adminSelectUI;
    }
}
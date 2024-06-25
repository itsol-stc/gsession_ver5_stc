package jp.groupsession.v2.bmk.bmk110;

import jp.groupsession.v2.bmk.bmk100.Bmk100ParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

/**
 * <br>[機  能] 管理者設定 権限設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk110ParamModel extends Bmk100ParamModel {
    /** 共有ブックマーク編集区分 */
    private int bmk110PubEditKbn__ = -1;
    /** グループブックマーク編集区分 */
    private int bmk110GrpEditKbn__ = -1;    /** 初期表示フラグ */
    private int bmk110initFlg__ = 0;


    //共有ブックマーク編集区分、グループ指定時
    /** 追加済みグループSID */
    private String[] bmk110GrpSid__ = null;
    /** 編集グループ UI */
    private UserGroupSelector bmk110GrpSidUI__ = null;

    //共有ブックマーク編集区分、ユーザ指定時
    /** 追加済みユーザSID */
    private String[] bmk110UserSid__ = null;

    /** グループSID */
    private int bmk110GroupSid__ = 0;
    /** 編集ユーザ UI */
    private UserGroupSelector bmk110UserSidUI__ = null;

    /**
     * <p>bmk110GrpEditKbn を取得します。
     * @return bmk110GrpEditKbn
     */
    public int getBmk110GrpEditKbn() {
        return bmk110GrpEditKbn__;
    }
    /**
     * <p>bmk110GrpEditKbn をセットします。
     * @param bmk110GrpEditKbn bmk110GrpEditKbn
     */
    public void setBmk110GrpEditKbn(int bmk110GrpEditKbn) {
        bmk110GrpEditKbn__ = bmk110GrpEditKbn;
    }
    /**
     * <p>bmk110PubEditKbn を取得します。
     * @return bmk110PubEditKbn
     */
    public int getBmk110PubEditKbn() {
        return bmk110PubEditKbn__;
    }
    /**
     * <p>bmk110PubEditKbn をセットします。
     * @param bmk110PubEditKbn bmk110PubEditKbn
     */
    public void setBmk110PubEditKbn(int bmk110PubEditKbn) {
        bmk110PubEditKbn__ = bmk110PubEditKbn;
    }
    /**
     * <p>bmk110initFlg を取得します。
     * @return bmk110initFlg
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110ParamModel#bmk110initFlg__
     */
    public int getBmk110initFlg() {
        return bmk110initFlg__;
    }
    /**
     * <p>bmk110initFlg をセットします。
     * @param bmk110initFlg bmk110initFlg
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110ParamModel#bmk110initFlg__
     */
    public void setBmk110initFlg(int bmk110initFlg) {
        bmk110initFlg__ = bmk110initFlg;
    }
    /**
     * <p>bmk110GrpSid を取得します。
     * @return bmk110GrpSid
     */
    public String[] getBmk110GrpSid() {
        return bmk110GrpSid__;
    }
    /**
     * <p>bmk110GrpSid をセットします。
     * @param bmk110GrpSid bmk110GrpSid
     */
    public void setBmk110GrpSid(String[] bmk110GrpSid) {
        bmk110GrpSid__ = bmk110GrpSid;
    }
    /**
     * <p>bmk110GrpSidUI を取得します。
     * @return bmk110GrpSidUI
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110ParamModel#bmk110GrpSidUI__
     */
    public UserGroupSelector getBmk110GrpSidUI() {
        return bmk110GrpSidUI__;
    }
    /**
     * <p>bmk110GrpSidUI をセットします。
     * @param bmk110GrpSidUI bmk110GrpSidUI
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110ParamModel#bmk110GrpSidUI__
     */
    public void setBmk110GrpSidUI(UserGroupSelector bmk110GrpSidUI) {
        bmk110GrpSidUI__ = bmk110GrpSidUI;
    }
    /**
     * <p>bmk110UserSid を取得します。
     * @return bmk110UserSid
     */
    public String[] getBmk110UserSid() {
        return bmk110UserSid__;
    }
    /**
     * <p>bmk110UserSid をセットします。
     * @param bmk110UserSid bmk110UserSid
     */
    public void setBmk110UserSid(String[] bmk110UserSid) {
        bmk110UserSid__ = bmk110UserSid;
    }
    /**
     * <p>bmk110GroupSid を取得します。
     * @return bmk110GroupSid
     */
    public int getBmk110GroupSid() {
        return bmk110GroupSid__;
    }
    /**
     * <p>bmk110GroupSid をセットします。
     * @param bmk110GroupSid bmk110GroupSid
     */
    public void setBmk110GroupSid(int bmk110GroupSid) {
        bmk110GroupSid__ = bmk110GroupSid;
    }
    /**
     * <p>bmk110UserSidUI を取得します。
     * @return bmk110UserSidUI
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110ParamModel#bmk110UserSidUI__
     */
    public UserGroupSelector getBmk110UserSidUI() {
        return bmk110UserSidUI__;
    }
    /**
     * <p>bmk110UserSidUI をセットします。
     * @param bmk110UserSidUI bmk110UserSidUI
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110ParamModel#bmk110UserSidUI__
     */
    public void setBmk110UserSidUI(UserGroupSelector bmk110UserSidUI) {
        bmk110UserSidUI__ = bmk110UserSidUI;
    }
}

package jp.groupsession.v2.ip.ipk090;

import java.util.ArrayList;

import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.ipk080.Ipk080ParamModel;
import jp.groupsession.v2.ip.model.IpkSpecMstModel;

/**
 * <br>[機  能] IP管理 スペックマスタメンテナンス一覧画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk090ParamModel extends Ipk080ParamModel {
    /** ページ遷移コマンド */
    private String CMD__;
    /** 追加編集モード */
    private String editMode__;
    /** スペックSID */
    private String ismSid__;
    /** スペック区分 */
    private String specKbn__ = String.valueOf(IpkConst.IPK_SPECKBN_CPU);
    /** スペック一覧リスト */
    private ArrayList<IpkSpecMstModel> specInfList__ = null;
    /** ヘルプモード */
    private String ipk090helpMode__;

    /**
     * <p>specInfList を取得します。
     * @return specInfList
     */
    public ArrayList<IpkSpecMstModel> getSpecInfList() {
        return specInfList__;
    }

    /**
     * <p>specInfList をセットします。
     * @param specInfList specInfList
     */
    public void setSpecInfList(ArrayList<IpkSpecMstModel> specInfList) {
        specInfList__ = specInfList;
    }

    /**
     * <p>CMD を取得します。
     * @return CMD
     */
    public String getCMD() {
        return CMD__;
    }

    /**
     * <p>CMD をセットします。
     * @param CMD CMD
     */
    public void setCMD(String CMD) {
        CMD__ = CMD;
    }

    /**
     * <p>editMode を取得します。
     * @return editMode
     */
    public String getEditMode() {
        return editMode__;
    }

    /**
     * <p>editMode をセットします。
     * @param editMode editMode
     */
    public void setEditMode(String editMode) {
        editMode__ = editMode;
    }

    /**
     * <p>specKbn を取得します。
     * @return specKbn
     */
    public String getSpecKbn() {
        return specKbn__;
    }

    /**
     * <p>specKbn をセットします。
     * @param specKbn specKbn
     */
    public void setSpecKbn(String specKbn) {
        specKbn__ = specKbn;
    }

    /**
     * <p>ismSid を取得します。
     * @return ismSid
     */
    public String getIsmSid() {
        return ismSid__;
    }

    /**
     * <p>ismSid をセットします。
     * @param ismSid ismSid
     */
    public void setIsmSid(String ismSid) {
        ismSid__ = ismSid;
    }

    /**
     * <p>ipk090helpMode を取得します。
     * @return ipk090helpMode
     */
    public String getIpk090helpMode() {
        return ipk090helpMode__;
    }

    /**
     * <p>ipk090helpMode をセットします。
     * @param ipk090helpMode ipk090helpMode
     */
    public void setIpk090helpMode(String ipk090helpMode) {
        ipk090helpMode__ = ipk090helpMode;
    }
}
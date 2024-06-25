package jp.groupsession.v2.enq.enq910;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.enq.enq900.Enq900ParamModel;

/**
 * <br>[機  能] 管理者設定 アンケート発信対象者設定画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq910ParamModel extends Enq900ParamModel {

    /** 初期表示フラグ */
    private int enq910initFlg__ = 0;
    /** アンケート作成対象者区分 */
    private int enq910TaisyoKbn__ = 0;

    /** アンケート作成可能ユーザリスト */
    private String[] enq910MakeSenderList__ = null;

    /** グループSID */
    private String enq910GroupSid__ = null;
    /** グループ一覧 */
    private ArrayList<LabelValueBean> enq910GroupLabel__ = null;

    /** アンケート作成可能ユーザ UI */
    private UserGroupSelector enq910MakeSenderListUI__ = null;

    /**
     * <p>enq910initFlg を取得します。
     * @return enq910initFlg
     */
    public int getEnq910initFlg() {
        return enq910initFlg__;
    }
    /**
     * <p>enq910initFlg をセットします。
     * @param enq910initFlg enq910initFlg
     */
    public void setEnq910initFlg(int enq910initFlg) {
        enq910initFlg__ = enq910initFlg;
    }
    /**
     * <p>enq910TaisyoKbn を取得します。
     * @return enq910TaisyoKbn
     */
    public int getEnq910TaisyoKbn() {
        return enq910TaisyoKbn__;
    }
    /**
     * <p>enq910TaisyoKbn をセットします。
     * @param enq910TaisyoKbn enq910TaisyoKbn
     */
    public void setEnq910TaisyoKbn(int enq910TaisyoKbn) {
        enq910TaisyoKbn__ = enq910TaisyoKbn;
    }
    /**
     * <p>アンケート作成可能ユーザリスト を取得します。
     * @return enq910MakeSenderList
     */
    public String[] getEnq910MakeSenderList() {
        return enq910MakeSenderList__;
    }
    /**
     * <p>アンケート作成可能ユーザリスト をセットします。
     * @param enq910MakeSenderList アンケート作成可能ユーザリスト
     */
    public void setEnq910MakeSenderList(String[] enq910MakeSenderList) {
        enq910MakeSenderList__ = enq910MakeSenderList;
    }
    /**
     * <p>グループSID を取得します。
     * @return enq910GroupSid
     */
    public String getEnq910GroupSid() {
        return enq910GroupSid__;
    }
    /**
     * <p>グループSID をセットします。
     * @param enq910GroupSid グループSID
     */
    public void setEnq910GroupSid(String enq910GroupSid) {
        enq910GroupSid__ = enq910GroupSid;
    }
    /**
     * <p>グループ一覧 を取得します。
     * @return enq910GroupLabel
     */
    public ArrayList<LabelValueBean> getEnq910GroupLabel() {
        return enq910GroupLabel__;
    }
    /**
     * <p>グループ一覧 をセットします。
     * @param enq910GroupLabel グループ一覧
     */
    public void setEnq910GroupLabel(ArrayList<LabelValueBean> enq910GroupLabel) {
        enq910GroupLabel__ = enq910GroupLabel;
    }
    /**
     * <p>enq910MakeSenderListUI を取得します。
     * @return enq910MakeSenderListUI
     */
    public UserGroupSelector getEnq910MakeSenderListUI() {
        return enq910MakeSenderListUI__;
    }
    /**
     * <p>enq910MakeSenderListUI をセットします。
     * @param enq910MakeSenderListUI enq910MakeSenderListUI
     */
    public void setEnq910MakeSenderListUI(
            UserGroupSelector enq910MakeSenderListUI) {
        enq910MakeSenderListUI__ = enq910MakeSenderListUI;
    }
}

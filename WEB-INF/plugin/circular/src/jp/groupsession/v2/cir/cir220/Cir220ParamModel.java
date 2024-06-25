package jp.groupsession.v2.cir.cir220;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.cir100.Cir100ParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

/**
 * <br>[機  能] 管理者設定 回覧板登録制限設定画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir220ParamModel extends Cir100ParamModel {

    /** 初期表示フラグ */
    private int cir220initFlg__ = 0;
    /** 回覧板作成対象者区分 */
    private int cir220TaisyoKbn__ = 0;

    /** 回覧板作成可能ユーザリスト */
    private String[] cir220MakeSenderList__ = null;

    /** グループSID */
    private String cir220GroupSid__ = null;

    /** 回覧板送信対象者リスト（左） */
    private ArrayList<LabelValueBean> cir220AddSenderLabel__ = null;

    /** 回覧板送信対象者 選択UI*/
    private UserGroupSelector cir220MakeSenderSelector__
        = null;

    /**
     * <p>cir220initFlg を取得します。
     * @return cir220initFlg
     */
    public int getCir220initFlg() {
        return cir220initFlg__;
    }
    /**
     * <p>cir220initFlg をセットします。
     * @param cir220initFlg cir220initFlg
     */
    public void setCir220initFlg(int cir220initFlg) {
        cir220initFlg__ = cir220initFlg;
    }
    /**
     * <p>cir220TaisyoKbn を取得します。
     * @return cir220TaisyoKbn
     */
    public int getCir220TaisyoKbn() {
        return cir220TaisyoKbn__;
    }
    /**
     * <p>cir220TaisyoKbn をセットします。
     * @param cir220TaisyoKbn cir220TaisyoKbn
     */
    public void setCir220TaisyoKbn(int cir220TaisyoKbn) {
        cir220TaisyoKbn__ = cir220TaisyoKbn;
    }
    /**
     * <p>回覧板作成可能ユーザリスト を取得します。
     * @return cir220MakeSenderList
     */
    public String[] getCir220MakeSenderList() {
        return cir220MakeSenderList__;
    }
    /**
     * <p>回覧板作成可能ユーザリスト をセットします。
     * @param cir220MakeSenderList 回覧板作成可能ユーザリスト
     */
    public void setCir220MakeSenderList(String[] cir220MakeSenderList) {
        cir220MakeSenderList__ = cir220MakeSenderList;
    }
    /**
     * <p>グループSID を取得します。
     * @return cir220GroupSid
     */
    public String getCir220GroupSid() {
        return cir220GroupSid__;
    }
    /**
     * <p>グループSID をセットします。
     * @param cir220GroupSid グループSID
     */
    public void setCir220GroupSid(String cir220GroupSid) {
        cir220GroupSid__ = cir220GroupSid;
    }
    /**
     * <p>回覧板送信対象者リスト（左） を取得します。
     * @return 回覧板送信対象者リスト（左）
     */
    public ArrayList<LabelValueBean> getCir220AddSenderLabel() {
        return cir220AddSenderLabel__;
    }
    /**
     * <p>回覧板送信対象者リスト（左） をセットします。
     * @param cir220AddSenderLabel 回覧板送信対象者リスト（左）
     */
    public void setCir220AddSenderLabel(
            ArrayList<LabelValueBean> cir220AddSenderLabel) {
        cir220AddSenderLabel__ = cir220AddSenderLabel;
    }
    /**
     * <p>cir220MakeSenderSelector を取得します。
     * @return cir220MakeSenderSelector
     * @see jp.groupsession.v2.cir.cir220.Cir220ParamModel#cir220MakeSenderSelector__
     */
    public UserGroupSelector getCir220MakeSenderSelector() {
        return cir220MakeSenderSelector__;
    }
    /**
     * <p>cir220MakeSenderSelector をセットします。
     * @param cir220MakeSenderSelector cir220MakeSenderSelector
     * @see jp.groupsession.v2.cir.cir220.Cir220ParamModel#cir220MakeSenderSelector__
     */
    public void setCir220MakeSenderSelector(UserGroupSelector cir220MakeSenderSelector) {
        cir220MakeSenderSelector__ = cir220MakeSenderSelector;
    }

}

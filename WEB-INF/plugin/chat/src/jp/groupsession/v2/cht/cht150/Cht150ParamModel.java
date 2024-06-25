package jp.groupsession.v2.cht.cht150;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht140.Cht140ParamModel;
import jp.groupsession.v2.cht.cht150.ui.Cht150Selector;

/**
 *
 * <br>[機  能] チャット カテゴリ作成編集のパラメータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht150ParamModel extends Cht140ParamModel {

    /** 初期表示区分 */
    private int cht150InitFlg__ = GSConstChat.DSP_FIRST;
    /** グループフォーム表示判定 */
    private boolean cht150DspGroupForm__ = true;
    /** カテゴリ名 */
    private String cht150CategoryName__ = null;

    /** 所属チャットグループ カテゴリ選択 */
    private int cht150CategorySid__ = -1;
    /** 所属チャットグループ 選択中チャットグループ */
    private String[] cht150ChtGroupSid__ = new String[0];
    /** 所属チャットグループ UI */
    private Cht150Selector cht150ChtGroupUI__ = null;

    /**
     * <p>cht150InitFlg を取得します。
     * @return cht150InitFlg
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150InitFlg__
     */
    public int getCht150InitFlg() {
        return cht150InitFlg__;
    }
    /**
     * <p>cht150InitFlg をセットします。
     * @param cht150InitFlg cht150InitFlg
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150InitFlg__
     */
    public void setCht150InitFlg(int cht150InitFlg) {
        cht150InitFlg__ = cht150InitFlg;
    }
    /**
     * <p>cht150DspGroupForm を取得します。
     * @return cht150DspGroupForm
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150DspGroupForm__
     */
    public boolean isCht150DspGroupForm() {
        return cht150DspGroupForm__;
    }
    /**
     * <p>cht150DspGroupForm をセットします。
     * @param cht150DspGroupForm cht150DspGroupForm
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150DspGroupForm__
     */
    public void setCht150DspGroupForm(boolean cht150DspGroupForm) {
        cht150DspGroupForm__ = cht150DspGroupForm;
    }
    /**
     * <p>cht150CategoryName を取得します。
     * @return cht150CategoryName
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150CategoryName__
     */
    public String getCht150CategoryName() {
        return cht150CategoryName__;
    }
    /**
     * <p>cht150CategoryName をセットします。
     * @param cht150CategoryName cht150CategoryName
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150CategoryName__
     */
    public void setCht150CategoryName(String cht150CategoryName) {
        cht150CategoryName__ = cht150CategoryName;
    }
    /**
     * <p>cht150CategorySid を取得します。
     * @return cht150CategorySid
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150CategorySid__
     */
    public int getCht150CategorySid() {
        return cht150CategorySid__;
    }
    /**
     * <p>cht150CategorySid をセットします。
     * @param cht150CategorySid cht150CategorySid
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150CategorySid__
     */
    public void setCht150CategorySid(int cht150CategorySid) {
        cht150CategorySid__ = cht150CategorySid;
    }
    /**
     * <p>cht150ChtGroupSid を取得します。
     * @return cht150ChtGroupSid
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150ChtGroupSid__
     */
    public String[] getCht150ChtGroupSid() {
        return cht150ChtGroupSid__;
    }
    /**
     * <p>cht150ChtGroupSid をセットします。
     * @param cht150ChtGroupSid cht150ChtGroupSid
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150ChtGroupSid__
     */
    public void setCht150ChtGroupSid(String[] cht150ChtGroupSid) {
        cht150ChtGroupSid__ = cht150ChtGroupSid;
    }
    /**
     * <p>cht150ChtGroupUI を取得します。
     * @return cht150ChtGroupUI
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150ChtGroupUI__
     */
    public Cht150Selector getCht150ChtGroupUI() {
        return cht150ChtGroupUI__;
    }
    /**
     * <p>cht150ChtGroupUI をセットします。
     * @param cht150ChtGroupUI cht150ChtGroupUI
     * @see jp.groupsession.v2.cht.cht150.Cht150ParamModel#cht150ChtGroupUI__
     */
    public void setCht150ChtGroupUI(Cht150Selector cht150ChtGroupUI) {
        cht150ChtGroupUI__ = cht150ChtGroupUI;
    }
}

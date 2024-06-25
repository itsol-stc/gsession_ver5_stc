package jp.groupsession.v2.anp.anp040;

import java.util.List;

import jp.groupsession.v2.anp.anp030.Anp030ParamModel;
import jp.groupsession.v2.anp.model.AnpLabelValueModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 安否確認個人設定・表示設定画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp040ParamModel extends Anp030ParamModel {

    /** 表示中のユーザSID */
    private int anp040UserSid__ = 0;
    /** メイン表示フラグ選択値 */
    private int anp040MainDispFlg__ = 0;
    /** 状況一覧表示件数選択値  */
    private int anp040SelectDispCnt__ = 0;
    /** デフォルト表示グループ選択SID */
    private String anp040SelectGroupSid__ = null;

    /** デフォルト表示グループコンボボックスリスト */
    private List<AnpLabelValueModel> anp040GroupLabel__ = null;
    /** 表示件数コンボボックスリスト */
    private List<LabelValueBean> anp040DsipCntList__ = null;


    /**
     * <p>ユーザSIDを取得します
     * @return anp040UserSid
     */
    public int getAnp040UserSid() {
        return anp040UserSid__;
    }

    /**
     * <p>ユーザSIDを設定します
     * @param anp040UserSid セットする anp040UserSid
     */
    public void setAnp040UserSid(int anp040UserSid) {
        anp040UserSid__ = anp040UserSid;
    }

    /**
     * <p>メイン表示フラグを取得します
     * @return anp040MainDispFlg
     */
    public int getAnp040MainDispFlg() {
        return anp040MainDispFlg__;
    }

    /**
     * <p>メイン表示フラグを設定します
     * @param anp040MainDispFlg セットする anp040MainDispFlg
     */
    public void setAnp040MainDispFlg(int anp040MainDispFlg) {
        anp040MainDispFlg__ = anp040MainDispFlg;
    }

    /**
     * <p>状況一覧表示件数選択値を取得します
     * @return anp040SelectDispCnt
     */
    public int getAnp040SelectDispCnt() {
        return anp040SelectDispCnt__;
    }

    /**
     * <p>状況一覧表示件数選択値を設定します
     * @param anp040SelectDispCnt セットする anp040SelectDispCnt
     */
    public void setAnp040SelectDispCnt(int anp040SelectDispCnt) {
        anp040SelectDispCnt__ = anp040SelectDispCnt;
    }

    /**
     * <p>デフォルト表示グループ選択SIDを取得します
     * @return anp040SelectGroupSid
     */
    public String getAnp040SelectGroupSid() {
        return anp040SelectGroupSid__;
    }

    /**
     * <p>デフォルト表示グループ選択SIDを設定します
     * @param anp040SelectGroupSid セットする anp040SelectGroupSid
     */
    public void setAnp040SelectGroupSid(String anp040SelectGroupSid) {
        anp040SelectGroupSid__ = anp040SelectGroupSid;
    }

    /**
     * <p>デフォルト表示グループコンボボックスリストを取得します
     * @return anp040GroupLabel
     */
    public List<AnpLabelValueModel> getAnp040GroupLabel() {
        return anp040GroupLabel__;
    }

    /**
     * <p>デフォルト表示グループコンボボックスリストを設定します
     * @param anp040GroupLabel セットする anp040GroupLabel
     */
    public void setAnp040GroupLabel(List<AnpLabelValueModel> anp040GroupLabel) {
        anp040GroupLabel__ = anp040GroupLabel;
    }

    /**
     * <p>表示件数コンボボックスリストを取得します
     * @return anp040DsipCntList
     */
    public List<LabelValueBean> getAnp040DsipCntList() {
        return anp040DsipCntList__;
    }

    /**
     * <p>表示件数コンボボックスリストを設定します
     * @param anp040DsipCntList セットする anp040DsipCntList
     */
    public void setAnp040DsipCntList(List<LabelValueBean> anp040DsipCntList) {
        anp040DsipCntList__ = anp040DsipCntList;
    }


}
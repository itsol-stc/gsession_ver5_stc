package jp.groupsession.v2.anp.anp170;

import java.util.List;

import jp.groupsession.v2.anp.anp140.Anp140ParamModel;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 状況内容確認 結果状況ポップアップ画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp170ParamModel extends Anp140ParamModel {


    /** ソートキー列 (送信先) */
    public static final int SORT_KEY_NAME = 0;
    /** ソートキー列 (連絡先) */
    public static final int SORT_KEY_ADDRESS = 1;
    /** ソートキー列 (返信日時) */
    public static final int SORT_KEY_DATE = 2;
    /** ソートキー列 (状態) */
    public static final int SORT_KEY_JOKYO = 3;

    /** 安否SID */
    private int anp170AnpSid__;
    /** 選択グループSID */
    private int anp170GrpSid__;
    /** 表示グループ名 */
    private String anp170GrpName__;


    /** 結果状況リスト */
    private List<Anp170DspModel> anp170JyokyoList__ = null;

    /** 現在ページ */
    private int anp170NowPage__ = 1;
    /** 表示ページ（上） */
    private int anp170DspPage1__;
    /** 表示ページ（下） */
    private int anp170DspPage2__;
    /** ページラベルリスト */
    private List<LabelValueBean> anp170PageLabel__;

    /** ソートキー列Index */
    private int anp170SortKeyIndex__ = -1;
    /** オーダーキー */
    private int anp170OrderKey__ = GSConst.ORDER_KEY_DESC;

    /**
     * <p>anp170AnpSid を取得します。
     * @return anp170AnpSid
     */
    public int getAnp170AnpSid() {
        return anp170AnpSid__;
    }

    /**
     * <p>anp170AnpSid をセットします。
     * @param anp170AnpSid anp170AnpSid
     */
    public void setAnp170AnpSid(int anp170AnpSid) {
        anp170AnpSid__ = anp170AnpSid;
    }

    /**
     * <p>anp170GrpSid を取得します。
     * @return anp170GrpSid
     */
    public int getAnp170GrpSid() {
        return anp170GrpSid__;
    }

    /**
     * <p>anp170GrpSid をセットします。
     * @param anp170GrpSid anp170GrpSid
     */
    public void setAnp170GrpSid(int anp170GrpSid) {
        anp170GrpSid__ = anp170GrpSid;
    }

    /**
     * <p>anp170GrpName を取得します。
     * @return anp170GrpName
     */
    public String getAnp170GrpName() {
        return anp170GrpName__;
    }

    /**
     * <p>anp170GrpName をセットします。
     * @param anp170GrpName anp170GrpName
     */
    public void setAnp170GrpName(String anp170GrpName) {
        anp170GrpName__ = anp170GrpName;
    }

    /**
     * <p>anp170JyokyoList を取得します。
     * @return anp170JyokyoList
     */
    public List<Anp170DspModel> getAnp170JyokyoList() {
        return anp170JyokyoList__;
    }

    /**
     * <p>anp170JyokyoList をセットします。
     * @param anp170JyokyoList anp170JyokyoList
     */
    public void setAnp170JyokyoList(List<Anp170DspModel> anp170JyokyoList) {
        anp170JyokyoList__ = anp170JyokyoList;
    }

    /**
     * <p>anp170NowPage を取得します。
     * @return anp170NowPage
     */
    public int getAnp170NowPage() {
        return anp170NowPage__;
    }

    /**
     * <p>anp170NowPage をセットします。
     * @param anp170NowPage anp170NowPage
     */
    public void setAnp170NowPage(int anp170NowPage) {
        anp170NowPage__ = anp170NowPage;
    }

    /**
     * <p>anp170DspPage1 を取得します。
     * @return anp170DspPage1
     */
    public int getAnp170DspPage1() {
        return anp170DspPage1__;
    }

    /**
     * <p>anp170DspPage1 をセットします。
     * @param anp170DspPage1 anp170DspPage1
     */
    public void setAnp170DspPage1(int anp170DspPage1) {
        anp170DspPage1__ = anp170DspPage1;
    }

    /**
     * <p>anp170DspPage2 を取得します。
     * @return anp170DspPage2
     */
    public int getAnp170DspPage2() {
        return anp170DspPage2__;
    }

    /**
     * <p>anp170DspPage2 をセットします。
     * @param anp170DspPage2 anp170DspPage2
     */
    public void setAnp170DspPage2(int anp170DspPage2) {
        anp170DspPage2__ = anp170DspPage2;
    }

    /**
     * <p>anp170PageLabel を取得します。
     * @return anp170PageLabel
     */
    public List<LabelValueBean> getAnp170PageLabel() {
        return anp170PageLabel__;
    }

    /**
     * <p>anp170PageLabel をセットします。
     * @param anp170PageLabel anp170PageLabel
     */
    public void setAnp170PageLabel(List<LabelValueBean> anp170PageLabel) {
        anp170PageLabel__ = anp170PageLabel;
    }

    /**
     * <p>anp170SortKeyIndex を取得します。
     * @return anp170SortKeyIndex
     */
    public int getAnp170SortKeyIndex() {
        return anp170SortKeyIndex__;
    }

    /**
     * <p>anp170SortKeyIndex をセットします。
     * @param anp170SortKeyIndex anp170SortKeyIndex
     */
    public void setAnp170SortKeyIndex(int anp170SortKeyIndex) {
        anp170SortKeyIndex__ = anp170SortKeyIndex;
    }

    /**
     * <p>anp170OrderKey を取得します。
     * @return anp170OrderKey
     */
    public int getAnp170OrderKey() {
        return anp170OrderKey__;
    }

    /**
     * <p>anp170OrderKey をセットします。
     * @param anp170OrderKey anp170OrderKey
     */
    public void setAnp170OrderKey(int anp170OrderKey) {
        anp170OrderKey__ = anp170OrderKey;
    }
}

package jp.groupsession.v2.anp.anp110;

import java.util.List;

import jp.groupsession.v2.anp.anp070.Anp070Form;
import jp.groupsession.v2.anp.anp110.model.Anp110SenderModel;
import jp.groupsession.v2.anp.model.AnpLabelValueModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 管理者設定・緊急連絡先設定状況画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp110Form extends Anp070Form {

    /** ソートキー列 (社員/職員番号) */
    public static final int SORT_KEY_SYAIN = 0;
    /** ソートキー列 (氏名) */
    public static final int SORT_KEY_NAME = 1;
    /** ソートキー列 (役職) */
    public static final int SORT_KEY_POST = 2;
    /** ソートキー列 (メールアドレス) */
    public static final int SORT_KEY_MAIL = 3;
    /** ソートキー列 (電話番号) */
    public static final int SORT_KEY_TEL = 4;

    /** 選択されたユーザSID */
    private int anp110SelectUserSid__;
    /** 選択されたユーザ氏名 */
    private String anp110SelectUserNm__;
    /** 緊急連絡先一覧 */
    private List<Anp110SenderModel> anp110List__ = null;

    /** フィルター メールアドレス */
    private int anp110SelectMailFilter__;
    /** フィルター 電話番号 */
    private int anp110SelectTellFilter__;

    /** 表示グループコンボボックス選択SID */
    private String anp110SelectGroupSid__ = null;
    /** デフォルト表示グループコンボボックスリスト */
    private List<AnpLabelValueModel> anp110GroupLabel__ = null;

    /** 現在ページ */
    private int anp110NowPage__ = 1;
    /** 表示ページ（上） */
    private int anp110DspPage1__;
    /** 表示ページ（下） */
    private int anp110DspPage2__;
    /** ページラベルリスト */
    private List<LabelValueBean> anp110PageLabel__;

    /** ソートキー */
    private int anp110SortKeyIndex__  = -1;
    /** オーダーキー */
    private int anp110OrderKey__ = GSConst.ORDER_KEY_ASC;


    /**
     * <p>選択されたユーザSIDを取得します
     * @return anp110SelectUserSid
     */
    public int getAnp110SelectUserSid() {
        return anp110SelectUserSid__;
    }

    /**
     * <p>選択されたユーザSIDを設定します
     * @param anp110SelectUserSid セットする anp110SelectUserSid
     */
    public void setAnp110SelectUserSid(int anp110SelectUserSid) {
        anp110SelectUserSid__ = anp110SelectUserSid;
    }

    /**
     * <p>選択されたユーザ氏名を取得します
     * @return anp110SelectUserNm
     */
    public String getAnp110SelectUserNm() {
        return anp110SelectUserNm__;
    }

    /**
     * <p>選択されたユーザ氏名を設定します
     * @param anp110SelectUserNm セットする anp110SelectUserNm
     */
    public void setAnp110SelectUserNm(String anp110SelectUserNm) {
        anp110SelectUserNm__ = anp110SelectUserNm;
    }

    /**
     * <p>緊急連絡先一覧を取得します
     * @return anp110List
     */
    public List<Anp110SenderModel> getAnp110List() {
        return anp110List__;
    }

    /**
     * <p>緊急連絡先一覧を設定します
     * @param anp110List セットする anp110List
     */
    public void setAnp110List(List<Anp110SenderModel> anp110List) {
        anp110List__ = anp110List;
    }

    /**
     * <p>表示グループコンボボックス選択SIDを取得します
     * @return anp110SelectGroupSid
     */
    public String getAnp110SelectGroupSid() {
        return anp110SelectGroupSid__;
    }

    /**
     * <p>表示グループコンボボックス選択SIDを設定します
     * @param anp110SelectGroupSid セットする anp110SelectGroupSid
     */
    public void setAnp110SelectGroupSid(String anp110SelectGroupSid) {
        this.anp110SelectGroupSid__ = anp110SelectGroupSid;
    }

    /**
     * <p>デフォルト表示グループコンボボックスリストを取得します
     * @return anp110GroupLabel
     */
    public List<AnpLabelValueModel> getAnp110GroupLabel() {
        return anp110GroupLabel__;
    }

    /**
     * <p>デフォルト表示グループコンボボックスリストを設定します
     * @param anp110GroupLabel セットする anp110GroupLabel
     */
    public void setAnp110GroupLabel(List<AnpLabelValueModel> anp110GroupLabel) {
        anp110GroupLabel__ = anp110GroupLabel;
    }

    /**
     * <p>現在ページを取得します
     * @return anp110NowPage
     */
    public int getAnp110NowPage() {
        return anp110NowPage__;
    }

    /**
     * <p>現在ページを設定します
     * @param anp110NowPage セットする anp110NowPage
     */
    public void setAnp110NowPage(int anp110NowPage) {
        anp110NowPage__ = anp110NowPage;
    }

    /**
     * <p>表示ページ（上）を取得します
     * @return anp110DspPage1
     */
    public int getAnp110DspPage1() {
        return anp110DspPage1__;
    }

    /**
     * <p>表示ページ（上）を設定します
     * @param anp110DspPage1 セットする anp110DspPage1
     */
    public void setAnp110DspPage1(int anp110DspPage1) {
        anp110DspPage1__ = anp110DspPage1;
    }

    /**
     * <p>表示ページ（下）を取得します
     * @return anp110DspPage2
     */
    public int getAnp110DspPage2() {
        return anp110DspPage2__;
    }

    /**
     * <p>表示ページ（下）を設定します
     * @param anp110DspPage2 セットする anp110DspPage2
     */
    public void setAnp110DspPage2(int anp110DspPage2) {
        anp110DspPage2__ = anp110DspPage2;
    }

    /**
     * <p>ページラベルリストを取得します
     * @return anp110PageLabel
     */
    public List<LabelValueBean> getAnp110PageLabel() {
        return anp110PageLabel__;
    }

    /**
     * <p>ページラベルリストを設定します
     * @param anp110PageLabel セットする anp110PageLabel
     */
    public void setAnp110PageLabel(List<LabelValueBean> anp110PageLabel) {
        anp110PageLabel__ = anp110PageLabel;
    }

    /**
     * <p>ソートキーを取得します
     * @return anp110SortKeyIndex
     */
    public int getAnp110SortKeyIndex() {
        return anp110SortKeyIndex__;
    }

    /**
     * <p>ソートキーを設定します
     * @param anp110SortKeyIndex セットする anp110SortKeyIndex
     */
    public void setAnp110SortKeyIndex(int anp110SortKeyIndex) {
        anp110SortKeyIndex__ = anp110SortKeyIndex;
    }

    /**
     * <p>オーダーキーを取得します
     * @return anp011OrderKey
     */
    public int getAnp110OrderKey() {
        return anp110OrderKey__;
    }

    /**
     * <p>オーダーキーを設定します
     * @param anp110OrderKey セットする anp110OrderKey
     */
    public void setAnp110OrderKey(int anp110OrderKey) {
        anp110OrderKey__ = anp110OrderKey;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     */
    public void setHiddenParamAnp110(Cmn999Form cmn999Form) {

        cmn999Form.addHiddenParam("anp110SelectUserSid", anp110SelectUserSid__);
        cmn999Form.addHiddenParam("anp110SelectUserNm", anp110SelectUserNm__);
        cmn999Form.addHiddenParam("anp110SortKeyIndex", anp110SortKeyIndex__);
        cmn999Form.addHiddenParam("anp110OrderKey", anp110OrderKey__);
        cmn999Form.addHiddenParam("anp110NowPage", anp110NowPage__);
        cmn999Form.addHiddenParam("anp110DspPage1", anp110DspPage1__);
        cmn999Form.addHiddenParam("anp110NowPage2", anp110DspPage2__);

        cmn999Form.addHiddenParam("anp110SelectGroupSid", anp110SelectGroupSid__);
        cmn999Form.addHiddenParam("anp110SelectMailFilter", anp110SelectMailFilter__);
        cmn999Form.addHiddenParam("anp110SelectTellFilter", anp110SelectTellFilter__);
    }

    /**
     * <p>anp110SelectMailFilter を取得します。
     * @return anp110SelectMailFilter
     */
    public int getAnp110SelectMailFilter() {
        return anp110SelectMailFilter__;
    }

    /**
     * <p>anp110SelectMailFilter をセットします。
     * @param anp110SelectMailFilter anp110SelectMailFilter
     */
    public void setAnp110SelectMailFilter(int anp110SelectMailFilter) {
        anp110SelectMailFilter__ = anp110SelectMailFilter;
    }

    /**
     * <p>anp110SelectTellFilter を取得します。
     * @return anp110SelectTellFilter
     */
    public int getAnp110SelectTellFilter() {
        return anp110SelectTellFilter__;
    }

    /**
     * <p>anp110SelectTellFilter をセットします。
     * @param anp110SelectTellFilter anp110SelectTellFilter
     */
    public void setAnp110SelectTellFilter(int anp110SelectTellFilter) {
        anp110SelectTellFilter__ = anp110SelectTellFilter;
    }
}
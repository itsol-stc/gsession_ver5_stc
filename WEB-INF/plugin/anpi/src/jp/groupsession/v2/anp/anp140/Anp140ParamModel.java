package jp.groupsession.v2.anp.anp140;

import java.util.List;

import jp.groupsession.v2.anp.anp130.Anp130ParamModel;
import jp.groupsession.v2.anp.anp140.model.Anp140JyokyoListModel;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 管理者設定・配信履歴 状況内容確認画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp140ParamModel extends Anp130ParamModel {

    /** ソートキー列 (送信先) */
    public static final int SORT_KEY_NAME = 0;
    /** ソートキー列 (連絡先) */
    public static final int SORT_KEY_ADDRESS = 1;
    /** ソートキー列 (返信日時) */
    public static final int SORT_KEY_DATE = 2;
    /** ソートキー列 (状態) */
    public static final int SORT_KEY_JOKYO = 3;

    /** 配信日 */
    private String anp140HaisinDate__ = null;
    /** 最終日時 */
    private String anp140EndDate__ = null;
    /** 配信者 */
    private String anp140Name__ = null;
    /** 配信者 ログイン停止*/
    private int anp140usrUkoFlg__ = 0;
    /** 配信者 状態区分*/
    private int anp140usrJkbn__ = 0;
    /** 件名 */
    private String anp140Subject__ = null;

    /** 本文 */
    private String anp140Body__ = null;
    /** 返信状況 */
    private String anp140ReplyState__ = null;
    /** 無事数 */
    private String anp140Buji__ = null;
    /** 軽症者数 */
    private String anp140Keisyo__ = null;
    /** 重症者数 */
    private String anp140Jyusyo__ = null;

    /** 出社可 人数 */
    private String anp140SyusyaOk__ = null;
    /** 出社不可 人数 */
    private String anp140SyusyaNo__ = null;

    /** 結果状況リスト */
    private List<Anp140JyokyoListModel> anp140JyokyoList__ = null;

    /** 現在ページ */
    private int anp140NowPage__ = 1;
    /** 表示ページ（上） */
    private int anp140DspPage1__;
    /** 表示ページ（下） */
    private int anp140DspPage2__;
    /** ページラベルリスト */
    private List<LabelValueBean> anp140PageLabel__;

    /** ソートキー列Index */
    private int anp140SortKeyIndex__ = -1;
    /** オーダーキー */
    private int anp140OrderKey__ = GSConst.ORDER_KEY_DESC;

    /** 表示位置フラグ（1:送信先） */
    private String anp140ScrollFlg__;

    /**
     * <p>配信日を取得する
     * @return anp140HaisinDate
     */
    public String getAnp140HaisinDate() {
        return anp140HaisinDate__;
    }

    /**
     * <p>配信日を設定する
     * @param anp140HaisinDate セットする anp140HaisinDate
     */
    public void setAnp140HaisinDate(String anp140HaisinDate) {
        anp140HaisinDate__ = anp140HaisinDate;
    }

    /**
     * <p>配信者を取得する
     * @return anp140Name
     */
    public String getAnp140Name() {
        return anp140Name__;
    }

    /**
     * <p>配信者を設定する
     * @param anp140Name セットする anp140Name
     */
    public void setAnp140Name(String anp140Name) {
        anp140Name__ = anp140Name;
    }

    /**
     * <p>件名を取得する
     * @return anp140Subject
     */
    public String getAnp140Subject() {
        return anp140Subject__;
    }

    /**
     * <p>件名を設定する
     * @param anp140Subject セットする anp140Subject
     */
    public void setAnp140Subject(String anp140Subject) {
        anp140Subject__ = anp140Subject;
    }

    /**
     * <p>返信状況を取得する
     * @return anp140ReplyState
     */
    public String getAnp140ReplyState() {
        return anp140ReplyState__;
    }

    /**
     * <p>返信状況を設定する
     * @param anp140ReplyState セットする anp140ReplyState
     */
    public void setAnp140ReplyState(String anp140ReplyState) {
        anp140ReplyState__ = anp140ReplyState;
    }

    /**
     * <p>軽症者数を取得する
     * @return anp140Keisyo
     */
    public String getAnp140Keisyo() {
        return anp140Keisyo__;
    }

    /**
     * <p>軽症者数を設定する
     * @param anp140Keisyo セットする anp140Keisyo
     */
    public void setAnp140Keisyo(String anp140Keisyo) {
        anp140Keisyo__ = anp140Keisyo;
    }

    /**
     * <p>重症者数を取得する
     * @return anp140Jyusyo
     */
    public String getAnp140Jyusyo() {
        return anp140Jyusyo__;
    }

    /**
     * <p>重症者数を設定する
     * @param anp140Jyusyo セットする anp140Jyusyo
     */
    public void setAnp140Jyusyo(String anp140Jyusyo) {
        anp140Jyusyo__ = anp140Jyusyo;
    }

    /**
     * <p>結果状況リストを取得する
     * @return anp140JyokyoList
     */
    public List<Anp140JyokyoListModel> getAnp140JyokyoList() {
        return anp140JyokyoList__;
    }

    /**
     * <p>結果状況リストを設定する
     * @param anp140JyokyoList セットする anp140JyokyoList
     */
    public void setAnp140JyokyoList(List<Anp140JyokyoListModel> anp140JyokyoList) {
        anp140JyokyoList__ = anp140JyokyoList;
    }

    /**
     * <p>ページ番号を取得する
     * @return anp140NowPage
     */
    public int getAnp140NowPage() {
        return anp140NowPage__;
    }

    /**
     * <p>ページ番号を設定する
     * @param anp140NowPage セットする anp140NowPage
     */
    public void setAnp140NowPage(int anp140NowPage) {
        anp140NowPage__ = anp140NowPage;
    }

    /**
     * <p>ページ番号（上）を取得する
     * @return anp140DspPage1
     */
    public int getAnp140DspPage1() {
        return anp140DspPage1__;
    }

    /**
     * <p>ページ番号（上）を設定する
     * @param anp140DspPage1 セットする anp140DspPage1
     */
    public void setAnp140DspPage1(int anp140DspPage1) {
        anp140DspPage1__ = anp140DspPage1;
    }

    /**
     * <p>ページ番号（下）を取得する
     * @return anp140DspPage2
     */
    public int getAnp140DspPage2() {
        return anp140DspPage2__;
    }

    /**
     * <p>ページ番号（下）を設定する
     * @param anp140DspPage2 セットする anp140DspPage2
     */
    public void setAnp140DspPage2(int anp140DspPage2) {
        anp140DspPage2__ = anp140DspPage2;
    }

    /**
     * <p>ページラベルリストを取得する
     * @return anp140PageLabel
     */
    public List<LabelValueBean> getAnp140PageLabel() {
        return anp140PageLabel__;
    }

    /**
     * <p>ページラベルリストを設定する
     * @param anp140PageLabel セットする anp140PageLabel
     */
    public void setAnp140PageLabel(List<LabelValueBean> anp140PageLabel) {
        anp140PageLabel__ = anp140PageLabel;
    }

    /**
     * <p>スクロールフラグを取得する
     * @return anp140ScrollFlg
     */
    public String getAnp140ScrollFlg() {
        return anp140ScrollFlg__;
    }

    /**
     * <p>スクロールフラグを設定する
     * @param anp140ScrollFlg セットする anp140ScrollFlg
     */
    public void setAnp140ScrollFlg(String anp140ScrollFlg) {
        anp140ScrollFlg__ = anp140ScrollFlg;
    }

    /**
     * <p>anp140Body を取得します。
     * @return anp140Body
     */
    public String getAnp140Body() {
        return anp140Body__;
    }

    /**
     * <p>anp140Body をセットします。
     * @param anp140Body anp140Body
     */
    public void setAnp140Body(String anp140Body) {
        anp140Body__ = anp140Body;
    }

    /**
     * <p>anp140SortKeyIndex を取得します。
     * @return anp140SortKeyIndex
     */
    public int getAnp140SortKeyIndex() {
        return anp140SortKeyIndex__;
    }

    /**
     * <p>anp140SortKeyIndex をセットします。
     * @param anp140SortKeyIndex anp140SortKeyIndex
     */
    public void setAnp140SortKeyIndex(int anp140SortKeyIndex) {
        anp140SortKeyIndex__ = anp140SortKeyIndex;
    }

    /**
     * <p>anp140OrderKey を取得します。
     * @return anp140OrderKey
     */
    public int getAnp140OrderKey() {
        return anp140OrderKey__;
    }

    /**
     * <p>anp140OrderKey をセットします。
     * @param anp140OrderKey anp140OrderKey
     */
    public void setAnp140OrderKey(int anp140OrderKey) {
        anp140OrderKey__ = anp140OrderKey;
    }

    /**
     * <p>anp140Buji を取得します。
     * @return anp140Buji
     */
    public String getAnp140Buji() {
        return anp140Buji__;
    }

    /**
     * <p>anp140Buji をセットします。
     * @param anp140Buji anp140Buji
     */
    public void setAnp140Buji(String anp140Buji) {
        anp140Buji__ = anp140Buji;
    }

    /**
     * <p>anp140SyusyaOk を取得します。
     * @return anp140SyusyaOk
     */
    public String getAnp140SyusyaOk() {
        return anp140SyusyaOk__;
    }

    /**
     * <p>anp140SyusyaOk をセットします。
     * @param anp140SyusyaOk anp140SyusyaOk
     */
    public void setAnp140SyusyaOk(String anp140SyusyaOk) {
        anp140SyusyaOk__ = anp140SyusyaOk;
    }

    /**
     * <p>anp140SyusyaNo を取得します。
     * @return anp140SyusyaNo
     */
    public String getAnp140SyusyaNo() {
        return anp140SyusyaNo__;
    }

    /**
     * <p>anp140SyusyaNo をセットします。
     * @param anp140SyusyaNo anp140SyusyaNo
     */
    public void setAnp140SyusyaNo(String anp140SyusyaNo) {
        anp140SyusyaNo__ = anp140SyusyaNo;
    }

    /**
     * <p>anp140EndDate を取得します。
     * @return anp140EndDate
     */
    public String getAnp140EndDate() {
        return anp140EndDate__;
    }

    /**
     * <p>anp140EndDate をセットします。
     * @param anp140EndDate anp140EndDate
     */
    public void setAnp140EndDate(String anp140EndDate) {
        anp140EndDate__ = anp140EndDate;
    }

    /**
     * <p>anp140usrUkoFlg を取得します。
     * @return anp140usrUkoFlg
     */
    public int getAnp140usrUkoFlg() {
        return anp140usrUkoFlg__;
    }

    /**
     * <p>anp140usrUkoFlg をセットします。
     * @param anp140usrUkoFlg anp140usrUkoFlg
     */
    public void setAnp140usrUkoFlg(int anp140usrUkoFlg) {
        anp140usrUkoFlg__ = anp140usrUkoFlg;
    }

    /**
     * <p>anp140usrJkbn を取得します。
     * @return anp140usrJkbn
     */
    public int getAnp140usrJkbn() {
        return anp140usrJkbn__;
    }

    /**
     * <p>anp140usrJkbn をセットします。
     * @param anp140usrJkbn anp140usrJkbn
     */
    public void setAnp140usrJkbn(int anp140usrJkbn) {
        this.anp140usrJkbn__ = anp140usrJkbn;
    }

}
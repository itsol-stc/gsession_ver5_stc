package jp.groupsession.v2.anp.anp010;

import java.util.List;

import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp010.model.Anp010SenderModel;
import jp.groupsession.v2.anp.model.AnpLabelValueModel;
import jp.groupsession.v2.anp.model.AnpStateModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 安否状況一覧画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp010ParamModel extends AbstractParamModel {

    /** ソートキー列 (送信日時) */
    public static final int SORT_KEY_HAISIN = 0;
    /** ソートキー列 (回答日時) */
    public static final int SORT_KEY_REPLY = SORT_KEY_HAISIN + 1;
    /** ソートキー列 (氏名) */
    public static final int SORT_KEY_NAME = SORT_KEY_REPLY + 1;
    /** ソートキー列 (状態) */
    public static final int SORT_KEY_JOKYO = SORT_KEY_NAME + 1;
    /** ソートキー列 (現在地) */
    public static final int SORT_KEY_PLACE = SORT_KEY_JOKYO + 1;
    /** ソートキー列 (出社) */
    public static final int SORT_KEY_SYUSYA = SORT_KEY_PLACE + 1;

    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** 現在表示中の安否確認SID */
    private String anpiSid__;
    /** 選択されたユーザSID */
    private String userSid__;

    /** 訓練モードフラグ */
    private int anp010KnrenFlg__ = 0;

    /** 管理者権限フラグ（1:管理者権限あり） */
    private int anp010KanriFlg__ = 0;

    /** 現在の状況 */
    private AnpStateModel anp010State__ = null;
    /** 送信者リスト */
    private List<Anp010SenderModel> anp010List__ = null;
    /** 送信者全削除フラグ 1:全ユーザ削除*/
    private int anp010AllDeleteFlg__ = 0;

    /** 表示グループコンボボックス選択SID */
    private String anp010SelectGroupSid__;
    /** 表示グループコンボボックスリスト */
    private List<AnpLabelValueModel> anp010GroupLabel__ = null;

    /** 安否確認管理者一覧 */
    private List<UsrLabelValueBean> anp010AdmUsrList__ = null;

    /** 現在ページ */
    private int anp010NowPage__ = 1;
    /** 表示ページ（上） */
    private int anp010DspPage1__;
    /** 表示ページ（下） */
    private int anp010DspPage2__;
    /** ページラベル */
    private List<LabelValueBean> anp010PageLabel__;

    /** ソートキー列Index */
    private int anp010SortKeyIndex__ = -1;
    /** オーダーキー */
    private int anp010OrderKey__ = GSConst.ORDER_KEY_DESC;

    /** セッションユーザーの安否確認 配信対象フラグ  true:配信対象 false:対象外*/
    private boolean anp010SendFlg__ = false;
    /** セッションユーザーの回答区分 */
    private int anp010AnsKbn__ = GSConstAnpi.ANP_ANS_NO;
    /** セッションユーザーの安否状況 */
    private Anp010SenderModel anp010SessionUserInfo__ = null;

    /** 検索区分 0:通常  1:詳細 */
    private int anp010SearchKbn__;
    /** 検索条件 配信状況 */
    private int anp010SearchSndKbn__;
    /** 検索条件 回答状況 */
    private int anp010SearchAnsKbn__;
    /** 検索条件 安否状況 */
    private int anp010SearchAnpKbn__;
    /** 検索条件 現在地 */
    private int anp010SearchPlcKbn__;
    /** 検索条件 出社状況 */
    private int anp010SearchSyuKbn__;

    /** 検索条件 配信状況(save) */
    private int anp010SvSearchSndKbn__;
    /** 検索条件 回答状況(save) */
    private int anp010SvSearchAnsKbn__;
    /** 検索条件 安否状況(save) */
    private int anp010SvSearchAnpKbn__;
    /** 検索条件 現在地(save) */
    private int anp010SvSearchPlcKbn__;
    /** 検索条件 出社状況(save) */
    private int anp010SvSearchSyuKbn__;
    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }
    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
    /**
     * <p>anpiSid を取得します。
     * @return anpiSid
     */
    public String getAnpiSid() {
        return anpiSid__;
    }
    /**
     * <p>anpiSid をセットします。
     * @param anpiSid anpiSid
     */
    public void setAnpiSid(String anpiSid) {
        anpiSid__ = anpiSid;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public String getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(String userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>anp010KnrenFlg を取得します。
     * @return anp010KnrenFlg
     */
    public int getAnp010KnrenFlg() {
        return anp010KnrenFlg__;
    }
    /**
     * <p>anp010KnrenFlg をセットします。
     * @param anp010KnrenFlg anp010KnrenFlg
     */
    public void setAnp010KnrenFlg(int anp010KnrenFlg) {
        anp010KnrenFlg__ = anp010KnrenFlg;
    }
    /**
     * <p>anp010KanriFlg を取得します。
     * @return anp010KanriFlg
     */
    public int getAnp010KanriFlg() {
        return anp010KanriFlg__;
    }
    /**
     * <p>anp010KanriFlg をセットします。
     * @param anp010KanriFlg anp010KanriFlg
     */
    public void setAnp010KanriFlg(int anp010KanriFlg) {
        anp010KanriFlg__ = anp010KanriFlg;
    }
    /**
     * <p>anp010State を取得します。
     * @return anp010State
     */
    public AnpStateModel getAnp010State() {
        return anp010State__;
    }
    /**
     * <p>anp010State をセットします。
     * @param anp010State anp010State
     */
    public void setAnp010State(AnpStateModel anp010State) {
        anp010State__ = anp010State;
    }
    /**
     * <p>anp010List を取得します。
     * @return anp010List
     */
    public List<Anp010SenderModel> getAnp010List() {
        return anp010List__;
    }
    /**
     * <p>anp010List をセットします。
     * @param anp010List anp010List
     */
    public void setAnp010List(List<Anp010SenderModel> anp010List) {
        anp010List__ = anp010List;
    }
    /**
     * <p>anp010SelectGroupSid を取得します。
     * @return anp010SelectGroupSid
     */
    public String getAnp010SelectGroupSid() {
        return anp010SelectGroupSid__;
    }
    /**
     * <p>anp010SelectGroupSid をセットします。
     * @param anp010SelectGroupSid anp010SelectGroupSid
     */
    public void setAnp010SelectGroupSid(String anp010SelectGroupSid) {
        anp010SelectGroupSid__ = anp010SelectGroupSid;
    }
    /**
     * <p>anp010GroupLabel を取得します。
     * @return anp010GroupLabel
     */
    public List<AnpLabelValueModel> getAnp010GroupLabel() {
        return anp010GroupLabel__;
    }
    /**
     * <p>anp010GroupLabel をセットします。
     * @param anp010GroupLabel anp010GroupLabel
     */
    public void setAnp010GroupLabel(List<AnpLabelValueModel> anp010GroupLabel) {
        anp010GroupLabel__ = anp010GroupLabel;
    }
    /**
     * <p>anp010AdmUsrList を取得します。
     * @return anp010AdmUsrList
     */
    public List<UsrLabelValueBean> getAnp010AdmUsrList() {
        return anp010AdmUsrList__;
    }
    /**
     * <p>anp010AdmUsrList をセットします。
     * @param anp010AdmUsrList anp010AdmUsrList
     */
    public void setAnp010AdmUsrList(List<UsrLabelValueBean> anp010AdmUsrList) {
        anp010AdmUsrList__ = anp010AdmUsrList;
    }
    /**
     * <p>anp010NowPage を取得します。
     * @return anp010NowPage
     */
    public int getAnp010NowPage() {
        return anp010NowPage__;
    }
    /**
     * <p>anp010NowPage をセットします。
     * @param anp010NowPage anp010NowPage
     */
    public void setAnp010NowPage(int anp010NowPage) {
        anp010NowPage__ = anp010NowPage;
    }
    /**
     * <p>anp010DspPage1 を取得します。
     * @return anp010DspPage1
     */
    public int getAnp010DspPage1() {
        return anp010DspPage1__;
    }
    /**
     * <p>anp010DspPage1 をセットします。
     * @param anp010DspPage1 anp010DspPage1
     */
    public void setAnp010DspPage1(int anp010DspPage1) {
        anp010DspPage1__ = anp010DspPage1;
    }
    /**
     * <p>anp010DspPage2 を取得します。
     * @return anp010DspPage2
     */
    public int getAnp010DspPage2() {
        return anp010DspPage2__;
    }
    /**
     * <p>anp010DspPage2 をセットします。
     * @param anp010DspPage2 anp010DspPage2
     */
    public void setAnp010DspPage2(int anp010DspPage2) {
        anp010DspPage2__ = anp010DspPage2;
    }
    /**
     * <p>anp010PageLabel を取得します。
     * @return anp010PageLabel
     */
    public List<LabelValueBean> getAnp010PageLabel() {
        return anp010PageLabel__;
    }
    /**
     * <p>anp010PageLabel をセットします。
     * @param anp010PageLabel anp010PageLabel
     */
    public void setAnp010PageLabel(List<LabelValueBean> anp010PageLabel) {
        anp010PageLabel__ = anp010PageLabel;
    }
    /**
     * <p>anp010SortKeyIndex を取得します。
     * @return anp010SortKeyIndex
     */
    public int getAnp010SortKeyIndex() {
        return anp010SortKeyIndex__;
    }
    /**
     * <p>anp010SortKeyIndex をセットします。
     * @param anp010SortKeyIndex anp010SortKeyIndex
     */
    public void setAnp010SortKeyIndex(int anp010SortKeyIndex) {
        anp010SortKeyIndex__ = anp010SortKeyIndex;
    }
    /**
     * <p>anp010OrderKey を取得します。
     * @return anp010OrderKey
     */
    public int getAnp010OrderKey() {
        return anp010OrderKey__;
    }
    /**
     * <p>anp010OrderKey をセットします。
     * @param anp010OrderKey anp010OrderKey
     */
    public void setAnp010OrderKey(int anp010OrderKey) {
        anp010OrderKey__ = anp010OrderKey;
    }
    /**
     * <p>anp010SendFlg を取得します。
     * @return anp010SendFlg
     */
    public boolean isAnp010SendFlg() {
        return anp010SendFlg__;
    }
    /**
     * <p>anp010SendFlg をセットします。
     * @param anp010SendFlg anp010SendFlg
     */
    public void setAnp010SendFlg(boolean anp010SendFlg) {
        anp010SendFlg__ = anp010SendFlg;
    }
    /**
     * <p>anp010AnsKbn を取得します。
     * @return anp010AnsKbn
     */
    public int getAnp010AnsKbn() {
        return anp010AnsKbn__;
    }
    /**
     * <p>anp010AnsKbn をセットします。
     * @param anp010AnsKbn anp010AnsKbn
     */
    public void setAnp010AnsKbn(int anp010AnsKbn) {
        anp010AnsKbn__ = anp010AnsKbn;
    }
    /**
     * <p>anp010SessionUserInfo を取得します。
     * @return anp010SessionUserInfo
     */
    public Anp010SenderModel getAnp010SessionUserInfo() {
        return anp010SessionUserInfo__;
    }
    /**
     * <p>anp010SessionUserInfo をセットします。
     * @param anp010SessionUserInfo anp010SessionUserInfo
     */
    public void setAnp010SessionUserInfo(Anp010SenderModel anp010SessionUserInfo) {
        anp010SessionUserInfo__ = anp010SessionUserInfo;
    }
    /**
     * <p>anp010SearchKbn を取得します。
     * @return anp010SearchKbn
     */
    public int getAnp010SearchKbn() {
        return anp010SearchKbn__;
    }
    /**
     * <p>anp010SearchKbn をセットします。
     * @param anp010SearchKbn anp010SearchKbn
     */
    public void setAnp010SearchKbn(int anp010SearchKbn) {
        anp010SearchKbn__ = anp010SearchKbn;
    }
    /**
     * <p>anp010SearchSndKbn を取得します。
     * @return anp010SearchSndKbn
     */
    public int getAnp010SearchSndKbn() {
        return anp010SearchSndKbn__;
    }
    /**
     * <p>anp010SearchSndKbn をセットします。
     * @param anp010SearchSndKbn anp010SearchSndKbn
     */
    public void setAnp010SearchSndKbn(int anp010SearchSndKbn) {
        anp010SearchSndKbn__ = anp010SearchSndKbn;
    }
    /**
     * <p>anp010SearchAnsKbn を取得します。
     * @return anp010SearchAnsKbn
     */
    public int getAnp010SearchAnsKbn() {
        return anp010SearchAnsKbn__;
    }
    /**
     * <p>anp010SearchAnsKbn をセットします。
     * @param anp010SearchAnsKbn anp010SearchAnsKbn
     */
    public void setAnp010SearchAnsKbn(int anp010SearchAnsKbn) {
        anp010SearchAnsKbn__ = anp010SearchAnsKbn;
    }
    /**
     * <p>anp010SearchAnpKbn を取得します。
     * @return anp010SearchAnpKbn
     */
    public int getAnp010SearchAnpKbn() {
        return anp010SearchAnpKbn__;
    }
    /**
     * <p>anp010SearchAnpKbn をセットします。
     * @param anp010SearchAnpKbn anp010SearchAnpKbn
     */
    public void setAnp010SearchAnpKbn(int anp010SearchAnpKbn) {
        anp010SearchAnpKbn__ = anp010SearchAnpKbn;
    }
    /**
     * <p>anp010SearchPlcKbn を取得します。
     * @return anp010SearchPlcKbn
     */
    public int getAnp010SearchPlcKbn() {
        return anp010SearchPlcKbn__;
    }
    /**
     * <p>anp010SearchPlcKbn をセットします。
     * @param anp010SearchPlcKbn anp010SearchPlcKbn
     */
    public void setAnp010SearchPlcKbn(int anp010SearchPlcKbn) {
        anp010SearchPlcKbn__ = anp010SearchPlcKbn;
    }
    /**
     * <p>anp010SearchSyuKbn を取得します。
     * @return anp010SearchSyuKbn
     */
    public int getAnp010SearchSyuKbn() {
        return anp010SearchSyuKbn__;
    }
    /**
     * <p>anp010SearchSyuKbn をセットします。
     * @param anp010SearchSyuKbn anp010SearchSyuKbn
     */
    public void setAnp010SearchSyuKbn(int anp010SearchSyuKbn) {
        anp010SearchSyuKbn__ = anp010SearchSyuKbn;
    }
    /**
     * <p>anp010SvSearchSndKbn を取得します。
     * @return anp010SvSearchSndKbn
     */
    public int getAnp010SvSearchSndKbn() {
        return anp010SvSearchSndKbn__;
    }
    /**
     * <p>anp010SvSearchSndKbn をセットします。
     * @param anp010SvSearchSndKbn anp010SvSearchSndKbn
     */
    public void setAnp010SvSearchSndKbn(int anp010SvSearchSndKbn) {
        anp010SvSearchSndKbn__ = anp010SvSearchSndKbn;
    }
    /**
     * <p>anp010SvSearchAnsKbn を取得します。
     * @return anp010SvSearchAnsKbn
     */
    public int getAnp010SvSearchAnsKbn() {
        return anp010SvSearchAnsKbn__;
    }
    /**
     * <p>anp010SvSearchAnsKbn をセットします。
     * @param anp010SvSearchAnsKbn anp010SvSearchAnsKbn
     */
    public void setAnp010SvSearchAnsKbn(int anp010SvSearchAnsKbn) {
        anp010SvSearchAnsKbn__ = anp010SvSearchAnsKbn;
    }
    /**
     * <p>anp010SvSearchAnpKbn を取得します。
     * @return anp010SvSearchAnpKbn
     */
    public int getAnp010SvSearchAnpKbn() {
        return anp010SvSearchAnpKbn__;
    }
    /**
     * <p>anp010SvSearchAnpKbn をセットします。
     * @param anp010SvSearchAnpKbn anp010SvSearchAnpKbn
     */
    public void setAnp010SvSearchAnpKbn(int anp010SvSearchAnpKbn) {
        anp010SvSearchAnpKbn__ = anp010SvSearchAnpKbn;
    }
    /**
     * <p>anp010SvSearchPlcKbn を取得します。
     * @return anp010SvSearchPlcKbn
     */
    public int getAnp010SvSearchPlcKbn() {
        return anp010SvSearchPlcKbn__;
    }
    /**
     * <p>anp010SvSearchPlcKbn をセットします。
     * @param anp010SvSearchPlcKbn anp010SvSearchPlcKbn
     */
    public void setAnp010SvSearchPlcKbn(int anp010SvSearchPlcKbn) {
        anp010SvSearchPlcKbn__ = anp010SvSearchPlcKbn;
    }
    /**
     * <p>anp010SvSearchSyuKbn を取得します。
     * @return anp010SvSearchSyuKbn
     */
    public int getAnp010SvSearchSyuKbn() {
        return anp010SvSearchSyuKbn__;
    }
    /**
     * <p>anp010SvSearchSyuKbn をセットします。
     * @param anp010SvSearchSyuKbn anp010SvSearchSyuKbn
     */
    public void setAnp010SvSearchSyuKbn(int anp010SvSearchSyuKbn) {
        anp010SvSearchSyuKbn__ = anp010SvSearchSyuKbn;
    }
    /**
     * <p>anp010AllDeleteFlg を取得します。
     * @return anp010AllDeleteFlg
     */
    public int getAnp010AllDeleteFlg() {
        return anp010AllDeleteFlg__;
    }
    /**
     * <p>anp010AllDeleteFlg をセットします。
     * @param anp010AllDeleteFlg anp010AllDeleteFlg
     */
    public void setAnp010AllDeleteFlg(int anp010AllDeleteFlg) {
        anp010AllDeleteFlg__ = anp010AllDeleteFlg;
    }


}
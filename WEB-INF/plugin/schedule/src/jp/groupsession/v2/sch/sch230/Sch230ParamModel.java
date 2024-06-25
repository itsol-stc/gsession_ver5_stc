package jp.groupsession.v2.sch.sch230;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.sch.sch081.Sch081ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] スケジュール 特例アクセス管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch230ParamModel extends Sch081ParamModel {

    /** 検索キーワード */
    private String sch230keyword__ = null;
    /** ページ上段 */
    private int sch230pageTop__ = 0;
    /** ページ下段 */
    private int sch230pageBottom__ = 0;
    /** ページ表示フラグ */
    private boolean sch230pageDspFlg__ = false;

    /** 検索キーワード(検索条件保持) */
    private String sch230svKeyword__ = null;

    /** ソートキー */
    private int sch230sortKey__ = GSConstWebmail.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int sch230order__ = GSConstWebmail.ORDER_ASC;

    /** 送信先リスト(編集対象) */
    private int sch230editData__ = 0;
    /** 検索実行フラグ */
    private int sch230searchFlg__ = 0;
    /** 編集モード */
    private int sch230editMode__ = 0;

    /** 検索結果一覧 */
    private List<Sch230SpAccessModel> spAccessList__ = null;
    /** ページコンボ */
    private List<LabelValueBean> pageCombo__ = null;

    /** 選択された送信先リスト */
    private String[] sch230selectSpAccessList__;

    /**
     * <p>sch230keyword を取得します。
     * @return sch230keyword
     */
    public String getSch230keyword() {
        return sch230keyword__;
    }

    /**
     * <p>sch230keyword をセットします。
     * @param sch230keyword sch230keyword
     */
    public void setSch230keyword(String sch230keyword) {
        sch230keyword__ = sch230keyword;
    }

    /**
     * <p>sch230pageTop を取得します。
     * @return sch230pageTop
     */
    public int getSch230pageTop() {
        return sch230pageTop__;
    }

    /**
     * <p>sch230pageTop をセットします。
     * @param sch230pageTop sch230pageTop
     */
    public void setSch230pageTop(int sch230pageTop) {
        sch230pageTop__ = sch230pageTop;
    }

    /**
     * <p>sch230pageBottom を取得します。
     * @return sch230pageBottom
     */
    public int getSch230pageBottom() {
        return sch230pageBottom__;
    }

    /**
     * <p>sch230pageBottom をセットします。
     * @param sch230pageBottom sch230pageBottom
     */
    public void setSch230pageBottom(int sch230pageBottom) {
        sch230pageBottom__ = sch230pageBottom;
    }

    /**
     * <p>sch230pageDspFlg を取得します。
     * @return sch230pageDspFlg
     */
    public boolean isSch230pageDspFlg() {
        return sch230pageDspFlg__;
    }

    /**
     * <p>sch230pageDspFlg をセットします。
     * @param sch230pageDspFlg sch230pageDspFlg
     */
    public void setSch230pageDspFlg(boolean sch230pageDspFlg) {
        sch230pageDspFlg__ = sch230pageDspFlg;
    }

    /**
     * <p>sch230svKeyword を取得します。
     * @return sch230svKeyword
     */
    public String getSch230svKeyword() {
        return sch230svKeyword__;
    }

    /**
     * <p>sch230svKeyword をセットします。
     * @param sch230svKeyword sch230svKeyword
     */
    public void setSch230svKeyword(String sch230svKeyword) {
        sch230svKeyword__ = sch230svKeyword;
    }

    /**
     * <p>sch230sortKey を取得します。
     * @return sch230sortKey
     */
    public int getSch230sortKey() {
        return sch230sortKey__;
    }

    /**
     * <p>sch230sortKey をセットします。
     * @param sch230sortKey sch230sortKey
     */
    public void setSch230sortKey(int sch230sortKey) {
        sch230sortKey__ = sch230sortKey;
    }

    /**
     * <p>sch230order を取得します。
     * @return sch230order
     */
    public int getSch230order() {
        return sch230order__;
    }

    /**
     * <p>sch230order をセットします。
     * @param sch230order sch230order
     */
    public void setSch230order(int sch230order) {
        sch230order__ = sch230order;
    }

    /**
     * <p>sch230editData を取得します。
     * @return sch230editData
     */
    public int getSch230editData() {
        return sch230editData__;
    }

    /**
     * <p>sch230editData をセットします。
     * @param sch230editData sch230editData
     */
    public void setSch230editData(int sch230editData) {
        sch230editData__ = sch230editData;
    }

    /**
     * <p>sch230searchFlg を取得します。
     * @return sch230searchFlg
     */
    public int getSch230searchFlg() {
        return sch230searchFlg__;
    }

    /**
     * <p>sch230searchFlg をセットします。
     * @param sch230searchFlg sch230searchFlg
     */
    public void setSch230searchFlg(int sch230searchFlg) {
        sch230searchFlg__ = sch230searchFlg;
    }

    /**
     * <p>sch230editMode を取得します。
     * @return sch230editMode
     */
    public int getSch230editMode() {
        return sch230editMode__;
    }

    /**
     * <p>sch230editMode をセットします。
     * @param sch230editMode sch230editMode
     */
    public void setSch230editMode(int sch230editMode) {
        sch230editMode__ = sch230editMode;
    }

    /**
     * <p>spAccessList を取得します。
     * @return spAccessList
     */
    public List<Sch230SpAccessModel> getSpAccessList() {
        return spAccessList__;
    }

    /**
     * <p>spAccessList をセットします。
     * @param spAccessList spAccessList
     */
    public void setSpAccessList(List<Sch230SpAccessModel> spAccessList) {
        spAccessList__ = spAccessList;
    }

    /**
     * <p>pageCombo を取得します。
     * @return pageCombo
     */
    public List<LabelValueBean> getPageCombo() {
        return pageCombo__;
    }

    /**
     * <p>pageCombo をセットします。
     * @param pageCombo pageCombo
     */
    public void setPageCombo(List<LabelValueBean> pageCombo) {
        pageCombo__ = pageCombo;
    }

    /**
     * <p>sch230selectSpAccessList を取得します。
     * @return sch230selectSpAccessList
     */
    public String[] getSch230selectSpAccessList() {
        return sch230selectSpAccessList__;
    }

    /**
     * <p>sch230selectSpAccessList をセットします。
     * @param sch230selectSpAccessList sch230selectSpAccessList
     */
    public void setSch230selectSpAccessList(String[] sch230selectSpAccessList) {
        sch230selectSpAccessList__ = sch230selectSpAccessList;
    }
}
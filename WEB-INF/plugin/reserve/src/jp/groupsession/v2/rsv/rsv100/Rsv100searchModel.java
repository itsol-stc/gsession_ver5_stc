package jp.groupsession.v2.rsv.rsv100;

import java.io.Serializable;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;

/**
 * <br>[機  能] 施設予約 施設利用状況照会画面の検索結果を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv100searchModel implements Serializable {

    /** 選択グループ */
    private int rsvGrp1__;
    /** 選択施設 */
    private int rsvGrp2__;
    /** 開始日付 */
    private UDate rsvFrom__;
    /** 終了日付 */
    private UDate rsvTo__;
    /** 日付区分 */
    private boolean rsvDateKbn__ = true;
    /** オーダーキー */
    private int rsvOrderKey__;
    /** ソートキー */
    private int rsvSortKey__;
    /** ページ */
    private int rsvPageTop__;
    /** ページ */
    private int rsvMaxPage__;
    /** キーワード */
    private List<String> rsvKeyWord__;
    /** 検索条件 */
    private int rsvSearchCondition__;
    /** 検索対象(利用目的) */
    private int rsvTargetMok__;
    /** 検索対象(内容) */
    private int rsvTargetNiyo__;
    /** 承認状況 */
    private int rsvApprStatus__ = GSConstReserve.SRH_APPRSTATUS_ALL;
    /** 第一キー選択値 */
    private int rsvSelectedKey1__;
    /** 第二キー選択値 */
    private int rsvSelectedKey2__;
    /** 第一キー ソート */
    private int rsvSelectedKey1Sort__;
    /** 第二キー ソート */
    private int rsvSelectedKey2Sort__;

    /** セッションユーザSID */
    private int usrSid__;
    
    /** 登録日From*/
    private UDate rsvADateFrom__;
    /** 登録日To*/
    private UDate rsvADateTo__;
    /** 編集日From*/
    private UDate rsvEDateFrom__;
    /** 編集日To*/
    private UDate rsvEDateTo__;

    /**
     * <p>rsvKeyWord__ を取得します。
     * @return rsvKeyWord
     */
    public List<String> getRsvKeyWord() {
        return rsvKeyWord__;
    }
    /**
     * <p>rsvKeyWord__ をセットします。
     * @param rsvKeyWord rsvKeyWord__
     */
    public void setRsvKeyWord(List<String> rsvKeyWord) {
        rsvKeyWord__ = rsvKeyWord;
    }
    /**
     * <p>rsvSearchCondition__ を取得します。
     * @return rsvSearchCondition
     */
    public int getRsvSearchCondition() {
        return rsvSearchCondition__;
    }
    /**
     * <p>rsvSearchCondition__ をセットします。
     * @param rsvSearchCondition rsvSearchCondition__
     */
    public void setRsvSearchCondition(int rsvSearchCondition) {
        rsvSearchCondition__ = rsvSearchCondition;
    }
    /**
     * <p>rsvSelectedKey1__ を取得します。
     * @return rsvSelectedKey1
     */
    public int getRsvSelectedKey1() {
        return rsvSelectedKey1__;
    }
    /**
     * <p>rsvSelectedKey1__ をセットします。
     * @param rsvSelectedKey1 rsvSelectedKey1__
     */
    public void setRsvSelectedKey1(int rsvSelectedKey1) {
        rsvSelectedKey1__ = rsvSelectedKey1;
    }
    /**
     * <p>rsvSelectedKey1Sort__ を取得します。
     * @return rsvSelectedKey1Sort
     */
    public int getRsvSelectedKey1Sort() {
        return rsvSelectedKey1Sort__;
    }
    /**
     * <p>rsvSelectedKey1Sort__ をセットします。
     * @param rsvSelectedKey1Sort rsvSelectedKey1Sort__
     */
    public void setRsvSelectedKey1Sort(int rsvSelectedKey1Sort) {
        rsvSelectedKey1Sort__ = rsvSelectedKey1Sort;
    }
    /**
     * <p>rsvSelectedKey2__ を取得します。
     * @return rsvSelectedKey2
     */
    public int getRsvSelectedKey2() {
        return rsvSelectedKey2__;
    }
    /**
     * <p>rsvSelectedKey2__ をセットします。
     * @param rsvSelectedKey2 rsvSelectedKey2__
     */
    public void setRsvSelectedKey2(int rsvSelectedKey2) {
        rsvSelectedKey2__ = rsvSelectedKey2;
    }
    /**
     * <p>rsvSelectedKey2Sort__ を取得します。
     * @return rsvSelectedKey2Sort
     */
    public int getRsvSelectedKey2Sort() {
        return rsvSelectedKey2Sort__;
    }
    /**
     * <p>rsvSelectedKey2Sort__ をセットします。
     * @param rsvSelectedKey2Sort rsvSelectedKey2Sort__
     */
    public void setRsvSelectedKey2Sort(int rsvSelectedKey2Sort) {
        rsvSelectedKey2Sort__ = rsvSelectedKey2Sort;
    }
    /**
     * <p>rsvTargetMok__ を取得します。
     * @return rsvTargetMok
     */
    public int getRsvTargetMok() {
        return rsvTargetMok__;
    }
    /**
     * <p>rsvTargetMok__ をセットします。
     * @param rsvTargetMok rsvTargetMok__
     */
    public void setRsvTargetMok(int rsvTargetMok) {
        rsvTargetMok__ = rsvTargetMok;
    }
    /**
     * <p>rsvTargetNiyo__ を取得します。
     * @return rsvTargetNiyo
     */
    public int getRsvTargetNiyo() {
        return rsvTargetNiyo__;
    }
    /**
     * <p>rsvTargetNiyo__ をセットします。
     * @param rsvTargetNiyo rsvTargetNiyo__
     */
    public void setRsvTargetNiyo(int rsvTargetNiyo) {
        rsvTargetNiyo__ = rsvTargetNiyo;
    }
    /**
     * <p>rsvGrp1 を取得します。
     * @return rsvGrp1
     */
    public int getRsvGrp1() {
        return rsvGrp1__;
    }
    /**
     * <p>rsvGrp1 をセットします。
     * @param rsvGrp1 rsvGrp1
     */
    public void setRsvGrp1(int rsvGrp1) {
        rsvGrp1__ = rsvGrp1;
    }
    /**
     * <p>rsvGrp2 を取得します。
     * @return rsvGrp2
     */
    public int getRsvGrp2() {
        return rsvGrp2__;
    }
    /**
     * <p>rsvGrp2 をセットします。
     * @param rsvGrp2 rsvGrp2
     */
    public void setRsvGrp2(int rsvGrp2) {
        rsvGrp2__ = rsvGrp2;
    }
    /**
     * <p>rsvMaxPage を取得します。
     * @return rsvMaxPage
     */
    public int getRsvMaxPage() {
        return rsvMaxPage__;
    }
    /**
     * <p>rsvMaxPage をセットします。
     * @param rsvMaxPage rsvMaxPage
     */
    public void setRsvMaxPage(int rsvMaxPage) {
        rsvMaxPage__ = rsvMaxPage;
    }
    /**
     * <p>rsvOrderKey を取得します。
     * @return rsvOrderKey
     */
    public int getRsvOrderKey() {
        return rsvOrderKey__;
    }
    /**
     * <p>rsvOrderKey をセットします。
     * @param rsvOrderKey rsvOrderKey
     */
    public void setRsvOrderKey(int rsvOrderKey) {
        rsvOrderKey__ = rsvOrderKey;
    }
    /**
     * <p>rsvPageTop を取得します。
     * @return rsvPageTop
     */
    public int getRsvPageTop() {
        return rsvPageTop__;
    }
    /**
     * <p>rsvPageTop をセットします。
     * @param rsvPageTop rsvPageTop
     */
    public void setRsvPageTop(int rsvPageTop) {
        rsvPageTop__ = rsvPageTop;
    }
    /**
     * <p>rsvSortKey を取得します。
     * @return rsvSortKey
     */
    public int getRsvSortKey() {
        return rsvSortKey__;
    }
    /**
     * <p>rsvSortKey をセットします。
     * @param rsvSortKey rsvSortKey
     */
    public void setRsvSortKey(int rsvSortKey) {
        rsvSortKey__ = rsvSortKey;
    }
     /**
     * <p>rsvFrom を取得します。
     * @return rsvFrom
     */
    public UDate getRsvFrom() {
        return rsvFrom__;
    }
    /**
     * <p>rsvFrom をセットします。
     * @param rsvFrom rsvFrom
     */
    public void setRsvFrom(UDate rsvFrom) {
        rsvFrom__ = rsvFrom;
    }
    /**
     * <p>rsvTo を取得します。
     * @return rsvTo
     */
    public UDate getRsvTo() {
        return rsvTo__;
    }
    /**
     * <p>rsvTo をセットします。
     * @param rsvTo rsvTo
     */
    public void setRsvTo(UDate rsvTo) {
        rsvTo__ = rsvTo;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * @return rsvDateKbn
     */
    public boolean isRsvDateKbn() {
        return rsvDateKbn__;
    }
    /**
     * @param rsvDateKbn 設定する rsvDateKbn
     */
    public void setRsvDateKbn(boolean rsvDateKbn) {
        rsvDateKbn__ = rsvDateKbn;
    }
    /**
     * <p>rsvApprStatus を取得します。
     * @return rsvApprStatus
     */
    public int getRsvApprStatus() {
        return rsvApprStatus__;
    }
    /**
     * <p>rsvApprStatus をセットします。
     * @param rsvApprStatus rsvApprStatus
     */
    public void setRsvApprStatus(int rsvApprStatus) {
        rsvApprStatus__ = rsvApprStatus;
    }
    /**
     * <p>rsvADateFrom を取得します。
     * @return rsvADateFrom
     */
    public UDate getRsvADateFrom() {
        return rsvADateFrom__;
    }
    /**
     * <p>rsvADateFrom をセットします。
     * @param rsvADateFrom rsvADateFrom
     */
    public void setRsvADateFrom(UDate rsvADateFrom) {
        rsvADateFrom__ = rsvADateFrom;
    }
    /**
     * <p>rsvADateTo を取得します。
     * @return rsvADateTo
     */
    public UDate getRsvADateTo() {
        return rsvADateTo__;
    }
    /**
     * <p>rsvADateTo をセットします。
     * @param rsvADateTo rsvADateTo
     */
    public void setRsvADateTo(UDate rsvADateTo) {
        rsvADateTo__ = rsvADateTo;
    }
    /**
     * <p>rsvEDateFrom を取得します。
     * @return rsvEDateFrom
     */
    public UDate getRsvEDateFrom() {
        return rsvEDateFrom__;
    }
    /**
     * <p>rsvEDateFrom をセットします。
     * @param rsvEDateFrom rsvEDateFrom
     */
    public void setRsvEDateFrom(UDate rsvEDateFrom) {
        rsvEDateFrom__ = rsvEDateFrom;
    }
    /**
     * <p>rsvEDateTo を取得します。
     * @return rsvEDateTo
     */
    public UDate getRsvEDateTo() {
        return rsvEDateTo__;
    }
    /**
     * <p>rsvEDateTo をセットします。
     * @param rsvEDateTo rsvEDateTo
     */
    public void setRsvEDateTo(UDate rsvEDateTo) {
        rsvEDateTo__ = rsvEDateTo;
    }
}

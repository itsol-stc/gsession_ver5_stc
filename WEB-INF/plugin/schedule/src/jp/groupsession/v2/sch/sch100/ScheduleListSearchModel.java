package jp.groupsession.v2.sch.sch100;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュール一覧の検索結果をを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ScheduleListSearchModel extends AbstractModel {

    /** ユーザSID */
    private int schUsrSid__;
    /** ユーザ区分 */
    private int schUsrKbn__;
    /** 公開区分 */
    private int schPublic__;
    /** セッションユーザSID */
    private int sessionUserSid__;

    /** 昇順降順 */
    private int schOrder__;
    /** ソートKEY */
    private int schSortKey__;
    /** 昇順降順 */
    private int schOrder2__;
    /** ソートKEY */
    private int schSortKey2__;
    /** オフセット */
    private int schOffset__;
    /** 最大表示件数 */
    private int schLimit__;

    //検索条件
    /** グループSID */
    private String schSltGroupSid__;
    /** ユーザSID */
    private int schSltUserSid__;
    /** 検索ユーザの所属グループを検索に含める*/
    private boolean schSltUserBelongGrpFlg__ = false;
    /** 開始日from */
    private UDate schStartDateFr__;
    /** 開始日to */
    private UDate schStartDateTo__;
    /** 終了日from */
    private UDate schEndDateFr__;
    /** 終了日to */
    private UDate schEndDateTo__;

    /** キーワード */
    private List<String> schKeyValue__;
    /** キーワード区分(AND・OR) */
    private int keyWordkbn__;
    /** 検索対象 タイトル*/
    private boolean targetTitle__ = false;
    /** 検索対象 内容*/
    private boolean targetValue__ = false;
    /** タイトルカラー*/
    private String[] bgcolor__ = null;
    /** グループ所属区分*/
    private int gpBelongKbn__ = 0;
    /** マイグループフラグ*/
    private boolean myGrpFlg__ = false;
    /** 表示リストフラグ */
    private boolean dspListFlg__ = false;

    /**
     * @return gpBelongKbn
     */
    public int getGpBelongKbn() {
        return gpBelongKbn__;
    }
    /**
     * @param gpBelongKbn 設定する gpBelongKbn
     */
    public void setGpBelongKbn(int gpBelongKbn) {
        gpBelongKbn__ = gpBelongKbn;
    }
    /**
     * <p>schOrder2 を取得します。
     * @return schOrder2
     */
    public int getSchOrder2() {
        return schOrder2__;
    }
    /**
     * <p>schOrder2 をセットします。
     * @param schOrder2 schOrder2
     */
    public void setSchOrder2(int schOrder2) {
        schOrder2__ = schOrder2;
    }
    /**
     * <p>schSortKey2 を取得します。
     * @return schSortKey2
     */
    public int getSchSortKey2() {
        return schSortKey2__;
    }
    /**
     * <p>schSortKey2 をセットします。
     * @param schSortKey2 schSortKey2
     */
    public void setSchSortKey2(int schSortKey2) {
        schSortKey2__ = schSortKey2;
    }
    /**
     * <p>schSltGroupSid を取得します。
     * @return schSltGroupSid
     */
    public String getSchSltGroupSid() {
        return schSltGroupSid__;
    }
    /**
     * <p>schSltGroupSid をセットします。
     * @param schSltGroupSid schSltGroupSid
     */
    public void setSchSltGroupSid(String schSltGroupSid) {
        schSltGroupSid__ = schSltGroupSid;
    }
    /**
     * <p>schSltUserSid を取得します。
     * @return schSltUserSid
     */
    public int getSchSltUserSid() {
        return schSltUserSid__;
    }
    /**
     * <p>schSltUserSid をセットします。
     * @param schSltUserSid schSltUserSid
     */
    public void setSchSltUserSid(int schSltUserSid) {
        schSltUserSid__ = schSltUserSid;
    }
    /**
     * <p>schEndDateFr を取得します。
     * @return schEndDateFr
     */
    public UDate getSchEndDateFr() {
        return schEndDateFr__;
    }
    /**
     * <p>schEndDateFr をセットします。
     * @param schEndDateFr schEndDateFr
     */
    public void setSchEndDateFr(UDate schEndDateFr) {
        schEndDateFr__ = schEndDateFr;
    }
    /**
     * <p>schEndDateTo を取得します。
     * @return schEndDateTo
     */
    public UDate getSchEndDateTo() {
        return schEndDateTo__;
    }
    /**
     * <p>schEndDateTo をセットします。
     * @param schEndDateTo schEndDateTo
     */
    public void setSchEndDateTo(UDate schEndDateTo) {
        schEndDateTo__ = schEndDateTo;
    }

    /**
     * <p>schKeyValue を取得します。
     * @return schKeyValue
     */
    public List<String> getSchKeyValue() {
        return schKeyValue__;
    }
    /**
     * <p>schKeyValue をセットします。
     * @param schKeyValue schKeyValue
     */
    public void setSchKeyValue(List<String> schKeyValue) {
        schKeyValue__ = schKeyValue;
    }
    /**
     * <p>schStartDateFr を取得します。
     * @return schStartDateFr
     */
    public UDate getSchStartDateFr() {
        return schStartDateFr__;
    }
    /**
     * <p>schStartDateFr をセットします。
     * @param schStartDateFr schStartDateFr
     */
    public void setSchStartDateFr(UDate schStartDateFr) {
        schStartDateFr__ = schStartDateFr;
    }
    /**
     * <p>schStartDateTo を取得します。
     * @return schStartDateTo
     */
    public UDate getSchStartDateTo() {
        return schStartDateTo__;
    }
    /**
     * <p>schStartDateTo をセットします。
     * @param schStartDateTo schStartDateTo
     */
    public void setSchStartDateTo(UDate schStartDateTo) {
        schStartDateTo__ = schStartDateTo;
    }
    /**
     * <p>schLimit を取得します。
     * @return schLimit
     */
    public int getSchLimit() {
        return schLimit__;
    }
    /**
     * <p>schLimit をセットします。
     * @param schLimit schLimit
     */
    public void setSchLimit(int schLimit) {
        schLimit__ = schLimit;
    }
    /**
     * <p>schOffset を取得します。
     * @return schOffset
     */
    public int getSchOffset() {
        return schOffset__;
    }
    /**
     * <p>schOffset をセットします。
     * @param schOffset schOffset
     */
    public void setSchOffset(int schOffset) {
        schOffset__ = schOffset;
    }
    /**
     * <p>schOrder を取得します。
     * @return schOrder
     */
    public int getSchOrder() {
        return schOrder__;
    }
    /**
     * <p>schOrder をセットします。
     * @param schOrder schOrder
     */
    public void setSchOrder(int schOrder) {
        schOrder__ = schOrder;
    }
    /**
     * <p>schPublic を取得します。
     * @return schPublic
     */
    public int getSchPublic() {
        return schPublic__;
    }
    /**
     * <p>schPublic をセットします。
     * @param schPublic schPublic
     */
    public void setSchPublic(int schPublic) {
        schPublic__ = schPublic;
    }
    /**
     * <p>schSortKey を取得します。
     * @return schSortKey
     */
    public int getSchSortKey() {
        return schSortKey__;
    }
    /**
     * <p>schSortKey をセットします。
     * @param schSortKey schSortKey
     */
    public void setSchSortKey(int schSortKey) {
        schSortKey__ = schSortKey;
    }
    /**
     * <p>schUsrKbn を取得します。
     * @return schUsrKbn
     */
    public int getSchUsrKbn() {
        return schUsrKbn__;
    }
    /**
     * <p>schUsrKbn をセットします。
     * @param schUsrKbn schUsrKbn
     */
    public void setSchUsrKbn(int schUsrKbn) {
        schUsrKbn__ = schUsrKbn;
    }
    /**
     * <p>schUsrSid を取得します。
     * @return schUsrSid
     */
    public int getSchUsrSid() {
        return schUsrSid__;
    }
    /**
     * <p>schUsrSid をセットします。
     * @param schUsrSid schUsrSid
     */
    public void setSchUsrSid(int schUsrSid) {
        schUsrSid__ = schUsrSid;
    }
    /**
     * <p>keyWordkbn を取得します。
     * @return keyWordkbn
     */
    public int getKeyWordkbn() {
        return keyWordkbn__;
    }
    /**
     * <p>keyWordkbn をセットします。
     * @param keyWordkbn keyWordkbn
     */
    public void setKeyWordkbn(int keyWordkbn) {
        keyWordkbn__ = keyWordkbn;
    }
    /**
     * <p>targetTitle を取得します。
     * @return targetTitle
     */
    public boolean isTargetTitle() {
        return targetTitle__;
    }
    /**
     * <p>targetTitle をセットします。
     * @param targetTitle targetTitle
     */
    public void setTargetTitle(boolean targetTitle) {
        targetTitle__ = targetTitle;
    }
    /**
     * <p>targetValue を取得します。
     * @return targetValue
     */
    public boolean isTargetValue() {
        return targetValue__;
    }
    /**
     * <p>targetValue をセットします。
     * @param targetValue targetValue
     */
    public void setTargetValue(boolean targetValue) {
        targetValue__ = targetValue;
    }
    /**
     * <p>bgcolor を取得します。
     * @return bgcolor
     */
    public String[] getBgcolor() {
        return bgcolor__;
    }
    /**
     * <p>bgcolor をセットします。
     * @param bgcolor bgcolor
     */
    public void setBgcolor(String[] bgcolor) {
        bgcolor__ = bgcolor;
    }
    /**
     * @return myGrpFlg
     */
    public boolean isMyGrpFlg() {
        return myGrpFlg__;
    }
    /**
     * @param myGrpFlg 設定する myGrpFlg
     */
    public void setMyGrpFlg(boolean myGrpFlg) {
        myGrpFlg__ = myGrpFlg;
    }
    /**
     * <p>dspListFlg を取得します。
     * @return dspListFlg
     * @see jp.groupsession.v2.sch.sch100.ScheduleListSearchModel#dspListFlg__
     */
    public boolean isDspListFlg() {
        return dspListFlg__;
    }
    /**
     * <p>dspListFlg をセットします。
     * @param dspListFlg dspListFlg
     * @see jp.groupsession.v2.sch.sch100.ScheduleListSearchModel#dspListFlg__
     */
    public void setDspListFlg(boolean dspListFlg) {
        dspListFlg__ = dspListFlg;
    }

    /**
     * <p>schSltUserBelongGrpKbn を取得します。
     * @return schSltUserBelongGrpFlg
     */
    public boolean isSchSltUserBelongGrpFlg() {
        return schSltUserBelongGrpFlg__;
    }
    /**
     * <p>schSltUserBelongGrpFlg をセットします。
     * @param schSltUserBelongGrpFlg schSltUserBelongGrpFlg
     */
    public void setSchSltUserBelongGrpFlg(boolean schSltUserBelongGrpFlg) {
        schSltUserBelongGrpFlg__ = schSltUserBelongGrpFlg;
    }
    /**
     * <p>sessionUserSid を取得します。
     * @return sessionUserSid
     */
    public int getSessionUserSid() {
        return sessionUserSid__;
    }
    /**
     * <p>sessionUserSid をセットします。
     * @param sessionUserSid sessionUserSid
     */
    public void setSessionUserSid(int sessionUserSid) {
        sessionUserSid__ = sessionUserSid;
    }
}

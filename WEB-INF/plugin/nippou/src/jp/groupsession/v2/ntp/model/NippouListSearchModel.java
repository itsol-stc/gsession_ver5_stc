package jp.groupsession.v2.ntp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.ntp.GSConstNippou;

/**
 * <br>[機  能] 日報一覧の検索条件を格納Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NippouListSearchModel extends AbstractModel {
    /** ソート区分 グループ週間表示でのソート順 */
    public static final int SORTKBN_NIPPO_GROUPSYUKAN = 0;
    /** ソート区分 一覧検索でのソート順 */
    public static final int SORTKBN_NIPPO_SEARCH = 1;
    /** ソート区分 タイムラインでのソート順 */
    public static final int SORTKBN_NIPPO_TIMELINE = 2;
    /**
     *
     * <br>[機  能] 検索ソートキー列挙子
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static enum ENUM_SORT {
        /** グループ週間表示でのソート順 */
        SORT_KEY_NIPPO_GROUPSYUKAN(SORTKBN_NIPPO_GROUPSYUKAN, 0),
        /** ソート 名前 */
        SORT_KEY_NAME(SORTKBN_NIPPO_SEARCH, GSConstNippou.SORT_KEY_NAME),
        /** ソート 開始日 */
        SORT_KEY_FRDATE(SORTKBN_NIPPO_SEARCH, GSConstNippou.SORT_KEY_FRDATE),
        /** ソート 終了日 */
        SORT_KEY_TODATE(SORTKBN_NIPPO_SEARCH, GSConstNippou.SORT_KEY_TODATE),
        /** ソート タイトル */
        SORT_KEY_TITLE(SORTKBN_NIPPO_SEARCH, GSConstNippou.SORT_KEY_TITLE),

        /** タイムラインソート順  登録日時▲更新日時▲ */
        DATE_DESC_EDATE_DESC(SORTKBN_NIPPO_TIMELINE, GSConstNippou.DATE_DESC_EDATE_DESC),
        /** タイムラインソート順   登録日時▲更新日時▼ */
        DATE_DESC_EDATE_ASC(SORTKBN_NIPPO_TIMELINE, GSConstNippou.DATE_DESC_EDATE_ASC),
        /** タイムラインソート順   登録日時▼更新日時▲ */
        DATE_ASC_EDATE_DESC(SORTKBN_NIPPO_TIMELINE, GSConstNippou.DATE_ASC_EDATE_DESC),
        /** タイムラインソート順   登録日時▼更新日時▼ */
        DATE_ASC_EDATE_ASC(SORTKBN_NIPPO_TIMELINE, GSConstNippou.DATE_ASC_EDATE_ASC);
        /** ソートキー */
        private final int sortKbn__;
        /**
         * <p>sortKbn を取得します。
         * @return sortKbn
         */
        public int getSortKbn() {
            return sortKbn__;
        }
        /**
         * <p>sortKey を取得します。
         * @return sortKey
         */
        public int getSortKey() {
            return sortKey__;
        }
        /** ソート区分 タイムラインソートか一覧検索ソートか */
        private final int sortKey__;

        /**
         * コンストラクタ
         * @param sortKbn id値
         * @param sortKey key値
         */
        ENUM_SORT(int sortKbn, int sortKey) {
            sortKbn__ = sortKbn;
            sortKey__ = sortKey;
        }
        /**
         *
         * <br>[機  能] ソートクラスの逆引き
         * <br>[解  説]
         * <br>[備  考]
         * @param sortKbn 区分
         * @param sortKey キー値
         * @return ソートENUMクラス(該当データが無い場合は null)
         */
        public static ENUM_SORT getSortEnum(int sortKbn, int sortKey) {
            Map<Integer, Map<Integer, ENUM_SORT>> map = ENUM_SORT_MAP;
            if (map != null && map.containsKey(sortKbn)) {
                return map.get(sortKbn).get(sortKey);
            }
            return null;
        }
        /**ソートクラスの逆引き用マップ*/
        private static final Map<Integer, Map<Integer, ENUM_SORT>> ENUM_SORT_MAP =
                new HashMap<Integer, Map<Integer, ENUM_SORT>>() {
            {
                for (ENUM_SORT sort : ENUM_SORT.values()) {
                    Map<Integer, ENUM_SORT> map;
                    map = get(sort.getSortKbn());
                    if (map == null) {
                        map = new HashMap<Integer, ENUM_SORT>();
                        put(sort.getSortKbn(), map);
                    }
                    map.put(sort.getSortKey(), sort);
                }
            }
        };
    }

    /** ユーザSID */
    private int usrSid__ = -1;
    /** 昇順降順 */
    private int ntpOrder__;
    /** ソートKEY */
    private ENUM_SORT ntpSortKey__;
    /** 昇順降順 */
    private int ntpOrder2__;
    /** ソートKEY */
    private ENUM_SORT ntpSortKey2__;
    /** オフセット */
    private int ntpOffset__;
    /** 最大表示件数 */
    private int ntpLimit__;

    //検索条件
    /** グループSID */
    private String ntpSltGroupSid__;
    /** 報告日付from */
    private UDate ntpDateFrom__;
    /** 報告日付to */
    private UDate ntpDateTo__;

    /** キーワード */
    private List<String> ntpKeyValue__;
    /** キーワード区分(AND・OR) */
    private int keyWordkbn__;
    /** 検索対象 (件名・本文) */
    private String[] targetKbn__ = null;
    /** 検索対象 タイトル*/
    private boolean targetTitle__ = false;
    /** 検索対象 内容*/
    private boolean targetValue__ = false;
    /** タイトルカラー*/
    private String[] bgcolor__ = null;
    /** 見込み度*/
    private String[] mikomido__ = null;
    /** 活動分類*/
    private int katudouBunrui__;
    /** 活動方法*/
    private int katudouHouhou__;

    /** 案件SID*/
    private int ankenSid__;
    /** 企業SID*/
    private int companySid__;
    /** 拠点SID*/
    private int companyBaseSid__;

    /** マイグループフラグ*/
    private boolean myGrpFlg__ = false;

    /** 日報SID*/
    private Integer nipSid__ = null;

    /** 案件権限チェック確認用モデル*/
    private AnkenPermitCheckModel napMdl__ = null;

    /** 特例アクセス 閲覧不可ユーザ*/
    private List<Integer> notAccessUserList__ = null;

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
     * <p>ntpOrder を取得します。
     * @return ntpOrder
     */
    public int getNtpOrder() {
        return ntpOrder__;
    }
    /**
     * <p>ntpOrder をセットします。
     * @param ntpOrder ntpOrder
     */
    public void setNtpOrder(int ntpOrder) {
        ntpOrder__ = ntpOrder;
    }
    /**
     * <p>ntpSortKey を取得します。
     * @return ntpSortKey
     */
    public ENUM_SORT getNtpSortKey() {
        return ntpSortKey__;
    }
    /**
     * <p>ntpSortKey をセットします。
     * @param ntpSortKey ntpSortKey
     */
    public void setNtpSortKey(ENUM_SORT ntpSortKey) {
        ntpSortKey__ = ntpSortKey;
    }
    /**
     * <p>ntpOrder2 を取得します。
     * @return ntpOrder2
     */
    public int getNtpOrder2() {
        return ntpOrder2__;
    }
    /**
     * <p>ntpOrder2 をセットします。
     * @param ntpOrder2 ntpOrder2
     */
    public void setNtpOrder2(int ntpOrder2) {
        ntpOrder2__ = ntpOrder2;
    }
    /**
     * <p>ntpSortKey2 を取得します。
     * @return ntpSortKey2
     */
    public ENUM_SORT getNtpSortKey2() {
        return ntpSortKey2__;
    }
    /**
     * <p>ntpSortKey2 をセットします。
     * @param ntpSortKey2 ntpSortKey2
     */
    public void setNtpSortKey2(ENUM_SORT ntpSortKey2) {
        ntpSortKey2__ = ntpSortKey2;
    }
    /**
     * <p>ntpOffset を取得します。
     * @return ntpOffset
     */
    public int getNtpOffset() {
        return ntpOffset__;
    }
    /**
     * <p>ntpOffset をセットします。
     * @param ntpOffset ntpOffset
     */
    public void setNtpOffset(int ntpOffset) {
        ntpOffset__ = ntpOffset;
    }
    /**
     * <p>ntpLimit を取得します。
     * @return ntpLimit
     */
    public int getNtpLimit() {
        return ntpLimit__;
    }
    /**
     * <p>ntpLimit をセットします。
     * @param ntpLimit ntpLimit
     */
    public void setNtpLimit(int ntpLimit) {
        ntpLimit__ = ntpLimit;
    }
    /**
     * <p>ntpKeyValue を取得します。
     * @return ntpKeyValue
     */
    public List<String> getNtpKeyValue() {
        return ntpKeyValue__;
    }
    /**
     * <p>ntpKeyValue をセットします。
     * @param ntpKeyValue ntpKeyValue
     */
    public void setNtpKeyValue(List<String> ntpKeyValue) {
        ntpKeyValue__ = ntpKeyValue;
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
     * <p>targetKbn を取得します。
     * @return targetKbn
     */
    public String[] getTargetKbn() {
        return targetKbn__;
    }
    /**
     * <p>targetKbn をセットします。
     * @param targetKbn targetKbn
     */
    public void setTargetKbn(String[] targetKbn) {
        targetKbn__ = targetKbn;
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
     * <p>mikomido を取得します。
     * @return mikomido
     */
    public String[] getMikomido() {
        return mikomido__;
    }
    /**
     * <p>mikomido をセットします。
     * @param mikomido mikomido
     */
    public void setMikomido(String[] mikomido) {
        mikomido__ = mikomido;
    }
    /**
     * <p>katudouBunrui を取得します。
     * @return katudouBunrui
     */
    public int getKatudouBunrui() {
        return katudouBunrui__;
    }
    /**
     * <p>katudouBunrui をセットします。
     * @param katudouBunrui katudouBunrui
     */
    public void setKatudouBunrui(int katudouBunrui) {
        katudouBunrui__ = katudouBunrui;
    }
    /**
     * <p>katudouHouhou を取得します。
     * @return katudouHouhou
     */
    public int getKatudouHouhou() {
        return katudouHouhou__;
    }
    /**
     * <p>katudouHouhou をセットします。
     * @param katudouHouhou katudouHouhou
     */
    public void setKatudouHouhou(int katudouHouhou) {
        katudouHouhou__ = katudouHouhou;
    }
    /**
     * <p>ntpDateFrom を取得します。
     * @return ntpDateFrom
     */
    public UDate getNtpDateFrom() {
        return ntpDateFrom__;
    }
    /**
     * <p>ntpDateFrom をセットします。
     * @param ntpDateFrom ntpDateFrom
     */
    public void setNtpDateFrom(UDate ntpDateFrom) {
        ntpDateFrom__ = ntpDateFrom;
    }
    /**
     * <p>ntpDateTo を取得します。
     * @return ntpDateTo
     */
    public UDate getNtpDateTo() {
        return ntpDateTo__;
    }
    /**
     * <p>ntpDateTo をセットします。
     * @param ntpDateTo ntpDateTo
     */
    public void setNtpDateTo(UDate ntpDateTo) {
        ntpDateTo__ = ntpDateTo;
    }
    /**
     * <p>ntpDateFr、ntpDateToを指定日付の範囲でセットします。
     * @param ntpDateFr 開始日
     * @param length 日数
     */
    public void setNtpDateDayLenge(UDate ntpDateFr, int length) {
        ntpDateFrom__ = ntpDateFr.cloneUDate();
        ntpDateFrom__.setZeroHhMmSs();
        ntpDateTo__ = ntpDateFr.cloneUDate();
        ntpDateTo__.addDay(length - 1);
        ntpDateTo__.setMaxHhMmSs();
    }
    /**
     * <p>myGrpFlg を取得します。
     * @return myGrpFlg
     */
    public boolean isMyGrpFlg() {
        return myGrpFlg__;
    }
    /**
     * <p>myGrpFlg をセットします。
     * @param myGrpFlg myGrpFlg
     */
    public void setMyGrpFlg(boolean myGrpFlg) {
        myGrpFlg__ = myGrpFlg;
    }
    /**
     * <p>ntpSltGroupSid を取得します。
     * @return ntpSltGroupSid
     */
    public String getNtpSltGroupSid() {
        return ntpSltGroupSid__;
    }
    /**
     * <p>ntpSltGroupSid をセットします。
     * @param ntpSltGroupSid ntpSltGroupSid
     */
    public void setNtpSltGroupSid(String ntpSltGroupSid) {
        ntpSltGroupSid__ = ntpSltGroupSid;
    }
    /**
     * <p>ankenSid を取得します。
     * @return ankenSid
     */
    public int getAnkenSid() {
        return ankenSid__;
    }
    /**
     * <p>ankenSid をセットします。
     * @param ankenSid ankenSid
     */
    public void setAnkenSid(int ankenSid) {
        ankenSid__ = ankenSid;
    }
    /**
     * <p>companySid を取得します。
     * @return companySid
     */
    public int getCompanySid() {
        return companySid__;
    }
    /**
     * <p>companySid をセットします。
     * @param companySid companySid
     */
    public void setCompanySid(int companySid) {
        companySid__ = companySid;
    }
    /**
     * <p>companyBaseSid を取得します。
     * @return companyBaseSid
     */
    public int getCompanyBaseSid() {
        return companyBaseSid__;
    }
    /**
     * <p>companyBaseSid をセットします。
     * @param companyBaseSid companyBaseSid
     */
    public void setCompanyBaseSid(int companyBaseSid) {
        companyBaseSid__ = companyBaseSid;
    }
    /**
     * <p>nipSid を取得します。
     * @return nipSid
     */
    public Integer getNipSid() {
        return nipSid__;
    }
    /**
     * <p>nipSid をセットします。
     * @param nipSid nipSid
     */
    public void setNipSid(Integer nipSid) {
        nipSid__ = nipSid;
    }
    /**
     * <p>napMdl を取得します。
     * @return napMdl
     */
    public AnkenPermitCheckModel getNapMdl() {
        return napMdl__;
    }
    /**
     * <p>napMdl をセットします。
     * @param napMdl napMdl
     */
    public void setNapMdl(AnkenPermitCheckModel napMdl) {
        napMdl__ = napMdl;
    }
    /**
     * <p>notAccessUserList を取得します。
     * @return notAccessUserList
     */
    public List<Integer> getNotAccessUserList() {
        return notAccessUserList__;
    }
    /**
     * <p>notAccessUserList をセットします。
     * @param notAccessUserList notAccessUserList
     */
    public void setNotAccessUserList(List<Integer> notAccessUserList) {
        notAccessUserList__ = notAccessUserList;
    }
}

package jp.groupsession.v2.prj.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.prj.GSConstProject;

/**
 * <br>[機  能] プロジェクト管理で使用する検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ProjectSearchModel {

    /** 取得区分:所属プロジェクトのみ取得 */
    public static final int GET_BELONG = 0;
    /** 取得区分:公開プロジェクトのみ取得（参加プロジェクトを含む） */
    public static final int GET_OPEN = 1;
    /** 取得区分:全て取得 */
    public static final int GET_ALL = 2;
    /** 取得区分:編集可能なプロジェクトのみ取得 */
    public static final int GET_EDIT = 3;
    /** 取得区分:コンボ選択値に応じて取得 */
    public static final int GET_SELECTED = 4;
    /** 取得区分:公開プロジェクトのみ取得（参加プロジェクトを除く） */
    public static final int GET_OPEN_NOT_BELONG = 5;

    /** プロジェクトSID */
    private int projectSid__ = -1;
    /** マイプロジェクト区分 */
    private int prjMyKbn__ = GSConstProject.KBN_MY_PRJ_DEF;
    /** TODOSID */
    private int todoSid__ = -1;
    /** ユーザSID */
    private int userSid__;
    /** カーソルスタート位置 */
    private int start__;
    /** 1ページの件数 */
    private int limit__;
    /** オーダーキー */
    private int order__;
    /** ソートキー */
    private int sort__;
    /** 未来の予定も表示する */
    private String mirai__;
    /** 完了プロジェクト・TODO表示フラグ true=表示、false=非表示 */
    private boolean endPrjFlg__;
    /** 今日のTODO表示フラグ true=今日の分のみ表示、false=今日以外も表示 */
    private boolean todayFlg__ = false;
    /** 取得区分 */
    private int getKbn__;
    /** 取得プロジェクト区分(プロジェクト「全て」選択時) */
    private boolean joinOnlyFlg__ = false;
    /** プロジェクトID */
    private String prjId__ = null;
    /** 状態From */
    private int statusFrom__ = -1;
    /** 状態To */
    private int statusTo__ = -1;
    /** プロジェクト名 */
    private String prjName__ = null;
    /** TODOタイトル */
    private String todoTitle__ = null;
    /** キーワード */
    private List<String> prjKeyValue__;
    /** キーワード区分(AND・OR) */
    private int keyWordkbn__;
    /** 検索対象 タイトル*/
    private boolean targetTitle__ = false;
    /** 検索対象 内容*/
    private boolean targetValue__ = false;
    /** メンバーSID */
    private String[] memberSid__ = null;
    /** 登録者SID */
    private String[] addUserSid__ = null;
    /** 重要度 */
    private String[] juyo__ = null;
    /** 開始From */
    private UDate startFrom__ = null;
    /** 開始To */
    private UDate startTo__ = null;
    /** 終了From */
    private UDate endFrom__ = null;
    /** 終了To */
    private UDate endTo__ = null;
    /** 管理番号 */
    private int kanriNumber__ = -1;

    /** 日付(0:全日付 1:今日 2:過去 3:未来 4:未入力) */
    private String selectingDate__ = null;
    /** 状態(0:全状態、それ以外:状態SID) */
    private String selectingStatus__ = null;
    /** カテゴリ(-1:全て、それ以外:カテゴリSID */
    private String selectingCategory__ = null;
    /** 対象メンバSID(0:全ユーザ それ以外:ユーザSID) */
    private String selectingMember__ = null;

    /** システム管理権限 true:有り false:無し */
    private boolean admin__ = false;
    /** プロジェクトステータス */
    private int prjStatus__ = -1;
    /** メンバーごとにTODOを取得*/
    private boolean memberBaseTodo__ = false;

    /** 予算From */
    private long yosanFrom__ = -1;
    /** 予算To */
    private long yosanTo__ = -1;

    /** 検索区分 0:プロジェクト  1:スケジュール*/
    private int prjSearchKbn__ = 0;

    /**
     * <p>yosanFrom を取得します。
     * @return yosanFrom
     */
    public long getYosanFrom() {
        return yosanFrom__;
    }
    /**
     * <p>prjSearchKbn を取得します。
     * @return prjSearchKbn
     */
    public int getPrjSearchKbn() {
        return prjSearchKbn__;
    }
    /**
     * <p>prjSearchKbn をセットします。
     * @param prjSearchKbn prjSearchKbn
     */
    public void setPrjSearchKbn(int prjSearchKbn) {
        prjSearchKbn__ = prjSearchKbn;
    }
    /**
     * <p>yosanFrom をセットします。
     * @param yosanFrom yosanFrom
     */
    public void setYosanFrom(long yosanFrom) {
        yosanFrom__ = yosanFrom;
    }
    /**
     * <p>yosanTo を取得します。
     * @return yosanTo
     */
    public long getYosanTo() {
        return yosanTo__;
    }
    /**
     * <p>yosanTo をセットします。
     * @param yosanTo yosanTo
     */
    public void setYosanTo(long yosanTo) {
        yosanTo__ = yosanTo;
    }
    /**
     * <p>prjStatus を取得します。
     * @return prjStatus
     */
    public int getPrjStatus() {
        return prjStatus__;
    }
    /**
     * <p>prjStatus をセットします。
     * @param prjStatus prjStatus
     */
    public void setPrjStatus(int prjStatus) {
        prjStatus__ = prjStatus;
    }
    /**
     * <p>admin を取得します。
     * @return admin
     */
    public boolean isAdmin() {
        return admin__;
    }
    /**
     * <p>admin をセットします。
     * @param admin admin
     */
    public void setAdmin(boolean admin) {
        admin__ = admin;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>start を取得します。
     * @return start
     */
    public int getStart() {
        return start__;
    }
    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(int start) {
        start__ = start;
    }
    /**
     * <p>limit を取得します。
     * @return limit
     */
    public int getLimit() {
        return limit__;
    }
    /**
     * <p>limit をセットします。
     * @param limit limit
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }
    /**
     * <p>order を取得します。
     * @return order
     */
    public int getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(int order) {
        order__ = order;
    }
    /**
     * <p>sort を取得します。
     * @return sort
     */
    public int getSort() {
        return sort__;
    }
    /**
     * <p>sort をセットします。
     * @param sort sort
     */
    public void setSort(int sort) {
        sort__ = sort;
    }
    /**
     * <p>mirai を取得します。
     * @return mirai
     */
    public String getMirai() {
        return mirai__;
    }
    /**
     * <p>mirai をセットします。
     * @param mirai mirai
     */
    public void setMirai(String mirai) {
        mirai__ = mirai;
    }
    /**
     * <p>endPrjFlg を取得します。
     * @return endPrjFlg
     */
    public boolean isEndPrjFlg() {
        return endPrjFlg__;
    }
    /**
     * <p>endPrjFlg をセットします。
     * @param endPrjFlg endPrjFlg
     */
    public void setEndPrjFlg(boolean endPrjFlg) {
        endPrjFlg__ = endPrjFlg;
    }
    /**
     * <p>getKbn を取得します。
     * @return getKbn
     */
    public int getGetKbn() {
        return getKbn__;
    }
    /**
     * <p>getKbn をセットします。
     * @param getKbn getKbn
     */
    public void setGetKbn(int getKbn) {
        getKbn__ = getKbn;
    }
    /**
     * <p>joinOnlyFlg を取得します。
     * @return joinOnlyFlg
     */
    public boolean getJoinOnlyFlg() {
        return joinOnlyFlg__;
    }
    /**
     * <p>joinOnlyFlg をセットします。
     * @param joinOnlyFlg joinOnlyFlg
     */
    public void setJoinOnlyFlg(boolean joinOnlyFlg) {
        joinOnlyFlg__ = joinOnlyFlg;
    }
    /**
     * <p>prjId を取得します。
     * @return prjId
     */
    public String getPrjId() {
        return prjId__;
    }
    /**
     * <p>prjId をセットします。
     * @param prjId prjId
     */
    public void setPrjId(String prjId) {
        prjId__ = prjId;
    }
    /**
     * <p>statusFrom を取得します。
     * @return statusFrom
     */
    public int getStatusFrom() {
        return statusFrom__;
    }
    /**
     * <p>statusFrom をセットします。
     * @param statusFrom statusFrom
     */
    public void setStatusFrom(int statusFrom) {
        statusFrom__ = statusFrom;
    }
    /**
     * <p>statusTo を取得します。
     * @return statusTo
     */
    public int getStatusTo() {
        return statusTo__;
    }
    /**
     * <p>statusTo をセットします。
     * @param statusTo statusTo
     */
    public void setStatusTo(int statusTo) {
        statusTo__ = statusTo;
    }
    /**
     * <p>prjName を取得します。
     * @return prjName
     */
    public String getPrjName() {
        return prjName__;
    }
    /**
     * <p>prjName をセットします。
     * @param prjName prjName
     */
    public void setPrjName(String prjName) {
        prjName__ = prjName;
    }
    /**
     * <p>memberSid を取得します。
     * @return memberSid
     */
    public String[] getMemberSid() {
        return memberSid__;
    }
    /**
     * <p>memberSid をセットします。
     * @param memberSid memberSid
     */
    public void setMemberSid(String[] memberSid) {
        memberSid__ = memberSid;
    }
    /**
     * <p>startFrom を取得します。
     * @return startFrom
     */
    public UDate getStartFrom() {
        return startFrom__;
    }
    /**
     * <p>startFrom をセットします。
     * @param startFrom startFrom
     */
    public void setStartFrom(UDate startFrom) {
        startFrom__ = startFrom;
    }
    /**
     * <p>startTo を取得します。
     * @return startTo
     */
    public UDate getStartTo() {
        return startTo__;
    }
    /**
     * <p>startTo をセットします。
     * @param startTo startTo
     */
    public void setStartTo(UDate startTo) {
        startTo__ = startTo;
    }
    /**
     * <p>endFrom を取得します。
     * @return endFrom
     */
    public UDate getEndFrom() {
        return endFrom__;
    }
    /**
     * <p>endFrom をセットします。
     * @param endFrom endFrom
     */
    public void setEndFrom(UDate endFrom) {
        endFrom__ = endFrom;
    }
    /**
     * <p>endTo を取得します。
     * @return endTo
     */
    public UDate getEndTo() {
        return endTo__;
    }
    /**
     * <p>endTo をセットします。
     * @param endTo endTo
     */
    public void setEndTo(UDate endTo) {
        endTo__ = endTo;
    }
    /**
     * <p>projectSid を取得します。
     * @return projectSid
     */
    public int getProjectSid() {
        return projectSid__;
    }
    /**
     * <p>projectSid をセットします。
     * @param projectSid projectSid
     */
    public void setProjectSid(int projectSid) {
        projectSid__ = projectSid;
    }
    /**
     * <p>addUserSid を取得します。
     * @return addUserSid
     */
    public String[] getAddUserSid() {
        return addUserSid__;
    }
    /**
     * <p>addUserSid をセットします。
     * @param addUserSid addUserSid
     */
    public void setAddUserSid(String[] addUserSid) {
        addUserSid__ = addUserSid;
    }
    /**
     * <p>juyo を取得します。
     * @return juyo
     */
    public String[] getJuyo() {
        return juyo__;
    }
    /**
     * <p>juyo をセットします。
     * @param juyo juyo
     */
    public void setJuyo(String[] juyo) {
        juyo__ = juyo;
    }
    /**
     * <p>todoTitle を取得します。
     * @return todoTitle
     */
    public String getTodoTitle() {
        return todoTitle__;
    }
    /**
     * <p>todoTitle をセットします。
     * @param todoTitle todoTitle
     */
    public void setTodoTitle(String todoTitle) {
        todoTitle__ = todoTitle;
    }
    /**
     * <p>todoSid を取得します。
     * @return todoSid
     */
    public int getTodoSid() {
        return todoSid__;
    }
    /**
     * <p>todoSid をセットします。
     * @param todoSid todoSid
     */
    public void setTodoSid(int todoSid) {
        todoSid__ = todoSid;
    }
    /**
     * <p>todayFlg を取得します。
     * @return todayFlg
     */
    public boolean isTodayFlg() {
        return todayFlg__;
    }
    /**
     * <p>todayFlg をセットします。
     * @param todayFlg todayFlg
     */
    public void setTodayFlg(boolean todayFlg) {
        todayFlg__ = todayFlg;
    }
    /**
     * <p>selectingCategory を取得します。
     * @return selectingCategory
     */
    public String getSelectingCategory() {
        return selectingCategory__;
    }
    /**
     * <p>selectingCategory をセットします。
     * @param selectingCategory selectingCategory
     */
    public void setSelectingCategory(String selectingCategory) {
        selectingCategory__ = selectingCategory;
    }
    /**
     * <p>selectingDate を取得します。
     * @return selectingDate
     */
    public String getSelectingDate() {
        return selectingDate__;
    }
    /**
     * <p>selectingDate をセットします。
     * @param selectingDate selectingDate
     */
    public void setSelectingDate(String selectingDate) {
        selectingDate__ = selectingDate;
    }
    /**
     * <p>selectingMember を取得します。
     * @return selectingMember
     */
    public String getSelectingMember() {
        return selectingMember__;
    }
    /**
     * <p>selectingMember をセットします。
     * @param selectingMember selectingMember
     */
    public void setSelectingMember(String selectingMember) {
        selectingMember__ = selectingMember;
    }
    /**
     * <p>selectingStatus を取得します。
     * @return selectingStatus
     */
    public String getSelectingStatus() {
        return selectingStatus__;
    }
    /**
     * <p>selectingStatus をセットします。
     * @param selectingStatus selectingStatus
     */
    public void setSelectingStatus(String selectingStatus) {
        selectingStatus__ = selectingStatus;
    }
    /**
     * <p>prjMyKbn を取得します。
     * @return prjMyKbn
     */
    public int getPrjMyKbn() {
        return prjMyKbn__;
    }
    /**
     * <p>prjMyKbn をセットします。
     * @param prjMyKbn prjMyKbn
     */
    public void setPrjMyKbn(int prjMyKbn) {
        prjMyKbn__ = prjMyKbn;
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
     * <p>prjKeyValue を取得します。
     * @return prjKeyValue
     */
    public List<String> getPrjKeyValue() {
        return prjKeyValue__;
    }
    /**
     * <p>prjKeyValue をセットします。
     * @param prjKeyValue prjKeyValue
     */
    public void setPrjKeyValue(List<String> prjKeyValue) {
        prjKeyValue__ = prjKeyValue;
    }
    /**
     * @return kanriNumber
     */
    public int getKanriNumber() {
        return kanriNumber__;
    }
    /**
     * @param kanriNumber セットする kanriNumber
     */
    public void setKanriNumber(int kanriNumber) {
        kanriNumber__ = kanriNumber;
    }
    /**
     * <p>memberBaseTodo を取得します。
     * @return memberBaseTodo
     * @see jp.groupsession.v2.prj.model.ProjectSearchModel#memberBaseTodo__
     */
    public boolean isMemberBaseTodo() {
        return memberBaseTodo__;
    }
    /**
     * <p>memberBaseTodo をセットします。
     * @param memberBaseTodo memberBaseTodo
     * @see jp.groupsession.v2.prj.model.ProjectSearchModel#memberBaseTodo__
     */
    public void setMemberBaseTodo(boolean memberBaseTodo) {
        memberBaseTodo__ = memberBaseTodo;
    }
}
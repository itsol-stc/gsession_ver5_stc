package jp.groupsession.v2.rng.rng130;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RngValidate;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngDairiUserDao;
import jp.groupsession.v2.rng.model.AccountDataModel;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.rng010.Rng010Form;
import jp.groupsession.v2.rng.rng050.Rng050Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議詳細検索画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng130Form extends Rng010Form {

    /** 種別 */
    private int rng130Type__ = RngConst.RNG_MODE_JYUSIN;
    /** 状態 */
    private int rng130Status__ = -1;
    /** 申請者 グループ */
    private int sltGroupSid__ = -1;
    /** 申請者 ユーザ */
    private int sltUserSid__ = -1;
    /** キーワード 条件 */
    private int rng130keyKbn__ = 0;
    /** 検索対象 件名 */
    private int rng130searchSubject1__ = 0;
    /** 検索対象 内容 */
    private int rng130searchSubject2__ = 0;
    /** 検索対象 申請ID */
    private int rng130searchSubject3__ = 0;
    /** 第１ソートキー */
    private int sltSortKey1__ = RngConst.RNG_SORT_TITLE;
    /** 第１オーダーキー */
    private int rng130orderKey1__ = RngConst.RNG_ORDER_ASC;
    /** 第２ソートキー */
    private int sltSortKey2__ = -1;
    /** 第２オーダーキー */
    private int rng130orderKey2__ = RngConst.RNG_ORDER_ASC;
    /** 選択申請日時 年 From */
    private int sltApplYearFr__ = -1;
    /** 選択申請日時 月 From */
    private int sltApplMonthFr__ = -1;
    /** 選択申請日時 日 From */
    private int sltApplDayFr__ = -1;
    /** 選択申請日時 年 To */
    private int sltApplYearTo__ = -1;
    /** 選択申請日時 月 To */
    private int sltApplMonthTo__ = -1;
    /** 選択申請日時 日 To */
    private int sltApplDayTo__ = -1;
    /** 選択最終処理日時 年 From */
    private int sltLastManageYearFr__ = -1;
    /** 選択最終処理日時 月 From */
    private int sltLastManageMonthFr__ = -1;
    /** 選択最終処理日時 日 From */
    private int sltLastManageDayFr__ = -1;
    /** 選択最終処理日時 年 To */
    private int sltLastManageYearTo__ = -1;
    /** 選択最終処理日時 月 To */
    private int sltLastManageMonthTo__ = -1;
    /** 選択最終処理日時 日 To */
    private int sltLastManageDayTo__ = -1;
    /** ページコンボ上段 */
    private int rng130pageTop__ = 1;
    /** ページコンボ下段 */
    private int rng130pageBottom__ = 1;

    /** 申請日時 年月日 From */
    private String rng130ApplDateFr__;
    /** 申請日時 年月日 To */
    private String rng130ApplDateTo__;
    /** 最終確認日時 年月日 From */
    private String rng130LastManageDateFr__;
    /** 最終確認日時 年月日 To */
    private String rng130LastManageDateTo__;
    /** 稟議 日付選択 年(現在から2000年までの数値) */
    private int rng130MinYear__ = 0;


    /** 申請者(検索条件保持用) */
    private int svRngViewAccount__ = 0;
    /** キーワード(検索条件保持用) */
    private String svRngKeyword__ = null;
    /** 種別(検索条件保持用) */
    private int svRng130Type__ = RngConst.RNG_SORT_JYUSIN;
    /** 状態(検索条件保持用) */
    private int svRng130Status__ = -1;
    /** カテゴリ(検索条件保持用)*/
    private int svRng130Category__ = -1;
    /** 申請者 グループ(検索条件保持用) */
    private int svGroupSid__ = -1;
    /** 申請者 ユーザ(検索条件保持用) */
    private int svUserSid__ = -1;
    /** キーワード 条件(検索条件保持用) */
    private int svRng130keyKbn__ = 0;
    /** 検索対象 件名(検索条件保持用) */
    private int svRng130searchSubject1__ = 0;
    /** 検索対象 内容(検索条件保持用) */
    private int svRng130searchSubject2__ = 0;
    /** 検索対象 申請ID(検索条件保持用) */
    private int svRng130searchSubject3__ = 0;
    /** 第１ソートキー(検索条件保持用) */
    private int svSortKey1__ = -1;
    /** 第１オーダーキー(検索条件保持用) */
    private int svRng130orderKey1__ = RngConst.RNG_ORDER_ASC;
    /** 第２ソートキー(検索条件保持用) */
    private int svSortKey2__ = -1;
    /** 第２オーダーキー(検索条件保持用) */
    private int svRng130orderKey2__ = RngConst.RNG_ORDER_ASC;
    /** 選択申請日時 年 From(検索条件保持用) */
    private int svApplYearFr__ = -1;
    /** 選択申請日時 月 From(検索条件保持用) */
    private int svApplMonthFr__ = -1;
    /** 選択申請日時 日 From(検索条件保持用) */
    private int svApplDayFr__ = -1;
    /** 選択申請日時 年 To(検索条件保持用) */
    private int svApplYearTo__ = -1;
    /** 選択申請日時 月 To(検索条件保持用) */
    private int svApplMonthTo__ = -1;
    /** 選択申請日時 日 To(検索条件保持用) */
    private int svApplDayTo__ = -1;
    /** 選択最終処理日時 年 From(検索条件保持用) */
    private int svLastManageYearFr__ = -1;
    /** 選択最終処理日時 月 From(検索条件保持用) */
    private int svLastManageMonthFr__ = -1;
    /** 選択最終処理日時 日 From(検索条件保持用) */
    private int svLastManageDayFr__ = -1;
    /** 選択最終処理日時 年 To(検索条件保持用) */
    private int svLastManageYearTo__ = -1;
    /** 選択最終処理日時 月 To(検索条件保持用) */
    private int svLastManageMonthTo__ = -1;
    /** 選択最終処理日時 日 To(検索条件保持用) */
    private int svLastManageDayTo__ = -1;

    /** 表示用グループリスト */
    private List<LabelValueBean> rng130groupList__ = null;
    /** 表示用ユーザーリスト */
    private List<UsrLabelValueBean> rng130userList__ = null;
    /** ソートキーリスト */
    private List<LabelValueBean> sortKeyList__ = null;
    /** ページコンボリスト */
    private List < LabelValueBean > pageList__ = null;
    /** 稟議情報一覧 */
    private List<RingiDataModel> rng130rngDataList__ = null;

    /** 検索フラグ */
    private int rng130searchFlg__ = 0;
    /** 年月日 */
    private String rng130rngNowDate__ = "";
    /** 出力ディレクトリ用ハッシュ値 */
    private String rng130outPutDirHash__ = "";
    /** 出力件数 */
    private int rng130pdfOutputCnt__ = 0;
    /** 出力全件数 */
    private int rng130pdfOutputMax__ = 0;

    /** セッションユーザSID */
    private int rng130sessionSid__ = 0;


    /**
     * <p>rng130sessionSid を取得します。
     * @return rng130sessionSid
     */
    public int getRng130sessionSid() {
        return rng130sessionSid__;
    }
    /**
     * <p>rng130sessionSid をセットします。
     * @param rng130sessionSid rng130sessionSid
     */
    public void setRng130sessionSid(int rng130sessionSid) {
        rng130sessionSid__ = rng130sessionSid;
    }

    /**
     * <p>rng130Type を取得します。
     * @return rng130Type
     */
    public int getRng130Type() {
        return rng130Type__;
    }
    /**
     * <p>rng130Type をセットします。
     * @param rng130Type rng130Type
     */
    public void setRng130Type(int rng130Type) {
        rng130Type__ = rng130Type;
    }
    /**
     * <p>rng130groupList を取得します。
     * @return rng130groupList
     */
    public List<LabelValueBean> getRng130groupList() {
        return rng130groupList__;
    }
    /**
     * <p>rng130groupList をセットします。
     * @param rng130groupList rng130groupList
     */
    public void setRng130groupList(List<LabelValueBean> rng130groupList) {
        rng130groupList__ = rng130groupList;
    }
    /**
     * <p>rng130keyKbn を取得します。
     * @return rng130keyKbn
     */
    public int getRng130keyKbn() {
        return rng130keyKbn__;
    }
    /**
     * <p>rng130keyKbn をセットします。
     * @param rng130keyKbn rng130keyKbn
     */
    public void setRng130keyKbn(int rng130keyKbn) {
        rng130keyKbn__ = rng130keyKbn;
    }
    /**
     * <p>rng130orderKey1 を取得します。
     * @return rng130orderKey1
     */
    public int getRng130orderKey1() {
        return rng130orderKey1__;
    }
    /**
     * <p>rng130orderKey1 をセットします。
     * @param rng130orderKey1 rng130orderKey1
     */
    public void setRng130orderKey1(int rng130orderKey1) {
        rng130orderKey1__ = rng130orderKey1;
    }
    /**
     * <p>rng130orderKey2 を取得します。
     * @return rng130orderKey2
     */
    public int getRng130orderKey2() {
        return rng130orderKey2__;
    }
    /**
     * <p>rng130orderKey2 をセットします。
     * @param rng130orderKey2 rng130orderKey2
     */
    public void setRng130orderKey2(int rng130orderKey2) {
        rng130orderKey2__ = rng130orderKey2;
    }
    /**
     * <p>rng130rngDataList を取得します。
     * @return rng130rngDataList
     */
    public List<RingiDataModel> getRng130rngDataList() {
        return rng130rngDataList__;
    }
    /**
     * <p>rng130rngDataList をセットします。
     * @param rng130rngDataList rng130rngDataList
     */
    public void setRng130rngDataList(List<RingiDataModel> rng130rngDataList) {
        rng130rngDataList__ = rng130rngDataList;
    }

    /**
     * <p>rng130searchSubject1 を取得します。
     * @return rng130searchSubject1
     */
    public int getRng130searchSubject1() {
        return rng130searchSubject1__;
    }
    /**
     * <p>rng130searchSubject1 をセットします。
     * @param rng130searchSubject1 rng130searchSubject1
     */
    public void setRng130searchSubject1(int rng130searchSubject1) {
        rng130searchSubject1__ = rng130searchSubject1;
    }
    /**
     * <p>rng130searchSubject2 を取得します。
     * @return rng130searchSubject2
     */
    public int getRng130searchSubject2() {
        return rng130searchSubject2__;
    }
    /**
     * <p>rng130searchSubject2 をセットします。
     * @param rng130searchSubject2 rng130searchSubject2
     */
    public void setRng130searchSubject2(int rng130searchSubject2) {
        rng130searchSubject2__ = rng130searchSubject2;
    }
    /**
     * <p>rng130userList を取得します。
     * @return rng130userList
     */
    public List<UsrLabelValueBean> getRng130userList() {
        return rng130userList__;
    }
    /**
     * <p>rng130userList をセットします。
     * @param rng130userList rng130userList
     */
    public void setRng130userList(List<UsrLabelValueBean> rng130userList) {
        rng130userList__ = rng130userList;
    }
    /**
     * <p>sltGroupSid を取得します。
     * @return sltGroupSid
     */
    public int getSltGroupSid() {
        return sltGroupSid__;
    }
    /**
     * <p>sltGroupSid をセットします。
     * @param sltGroupSid sltGroupSid
     */
    public void setSltGroupSid(int sltGroupSid) {
        sltGroupSid__ = sltGroupSid;
    }
    /**
     * <p>sltSortKey1 を取得します。
     * @return sltSortKey1
     */
    public int getSltSortKey1() {
        return sltSortKey1__;
    }
    /**
     * <p>sltSortKey1 をセットします。
     * @param sltSortKey1 sltSortKey1
     */
    public void setSltSortKey1(int sltSortKey1) {
        sltSortKey1__ = sltSortKey1;
    }
    /**
     * <p>sltSortKey2 を取得します。
     * @return sltSortKey2
     */
    public int getSltSortKey2() {
        return sltSortKey2__;
    }
    /**
     * <p>sltSortKey2 をセットします。
     * @param sltSortKey2 sltSortKey2
     */
    public void setSltSortKey2(int sltSortKey2) {
        sltSortKey2__ = sltSortKey2;
    }
    /**
     * <p>sltUserSid を取得します。
     * @return sltUserSid
     */
    public int getSltUserSid() {
        return sltUserSid__;
    }
    /**
     * <p>sltUserSid をセットします。
     * @param sltUserSid sltUserSid
     */
    public void setSltUserSid(int sltUserSid) {
        sltUserSid__ = sltUserSid;
    }
    /**
     * <p>sortKeyList を取得します。
     * @return sortKeyList
     */
    public List<LabelValueBean> getSortKeyList() {
        return sortKeyList__;
    }
    /**
     * <p>sortKeyList をセットします。
     * @param sortKeyList sortKeyList
     */
    public void setSortKeyList(List<LabelValueBean> sortKeyList) {
        sortKeyList__ = sortKeyList;
    }
    /**
     * <p>rng130searchFlg を取得します。
     * @return rng130searchFlg
     */
    public int getRng130searchFlg() {
        return rng130searchFlg__;
    }
    /**
     * <p>rng130searchFlg をセットします。
     * @param rng130searchFlg rng130searchFlg
     */
    public void setRng130searchFlg(int rng130searchFlg) {
        rng130searchFlg__ = rng130searchFlg;
    }
    /**
     * <p>rng130pageBottom を取得します。
     * @return rng130pageBottom
     */
    public int getRng130pageBottom() {
        return rng130pageBottom__;
    }
    /**
     * <p>rng130pageBottom をセットします。
     * @param rng130pageBottom rng130pageBottom
     */
    public void setRng130pageBottom(int rng130pageBottom) {
        rng130pageBottom__ = rng130pageBottom;
    }
    /**
     * <p>rng130pageTop を取得します。
     * @return rng130pageTop
     */
    public int getRng130pageTop() {
        return rng130pageTop__;
    }
    /**
     * <p>rng130pageTop をセットします。
     * @param rng130pageTop rng130pageTop
     */
    public void setRng130pageTop(int rng130pageTop) {
        rng130pageTop__ = rng130pageTop;
    }
    /**
     * <p>pageList を取得します。
     * @return pageList
     */
    public List<LabelValueBean> getPageList() {
        return pageList__;
    }
    /**
     * <p>pageList をセットします。
     * @param pageList pageList
     */
    public void setPageList(List<LabelValueBean> pageList) {
        pageList__ = pageList;
    }
    /**
     * <p>svRngViewAccount を取得します。
     * @return svRngViewAccount
     */
    public int getSvRngViewAccount() {
        return svRngViewAccount__;
    }
    /**
     * <p>svRngViewAccount をセットします。
     * @param svRngViewAccount svRngViewAccount
     */
    public void setSvRngViewAccount(int svRngViewAccount) {
        svRngViewAccount__ = svRngViewAccount;
    }
    /**
     * <p>svRngKeyword を取得します。
     * @return svRngKeyword
     */
    public String getSvRngKeyword() {
        return svRngKeyword__;
    }
    /**
     * <p>svRngKeyword をセットします。
     * @param svRngKeyword svRngKeyword
     */
    public void setSvRngKeyword(String svRngKeyword) {
        svRngKeyword__ = svRngKeyword;
    }
    /**
     * <p>svGroupSid を取得します。
     * @return svGroupSid
     */
    public int getSvGroupSid() {
        return svGroupSid__;
    }
    /**
     * <p>svGroupSid をセットします。
     * @param svGroupSid svGroupSid
     */
    public void setSvGroupSid(int svGroupSid) {
        svGroupSid__ = svGroupSid;
    }
    /**
     * <p>svRng130keyKbn を取得します。
     * @return svRng130keyKbn
     */
    public int getSvRng130keyKbn() {
        return svRng130keyKbn__;
    }
    /**
     * <p>svRng130keyKbn をセットします。
     * @param svRng130keyKbn svRng130keyKbn
     */
    public void setSvRng130keyKbn(int svRng130keyKbn) {
        svRng130keyKbn__ = svRng130keyKbn;
    }
    /**
     * <p>svRng130orderKey1 を取得します。
     * @return svRng130orderKey1
     */
    public int getSvRng130orderKey1() {
        return svRng130orderKey1__;
    }
    /**
     * <p>svRng130orderKey1 をセットします。
     * @param svRng130orderKey1 svRng130orderKey1
     */
    public void setSvRng130orderKey1(int svRng130orderKey1) {
        svRng130orderKey1__ = svRng130orderKey1;
    }
    /**
     * <p>svRng130orderKey2 を取得します。
     * @return svRng130orderKey2
     */
    public int getSvRng130orderKey2() {
        return svRng130orderKey2__;
    }
    /**
     * <p>svRng130orderKey2 をセットします。
     * @param svRng130orderKey2 svRng130orderKey2
     */
    public void setSvRng130orderKey2(int svRng130orderKey2) {
        svRng130orderKey2__ = svRng130orderKey2;
    }

    /**
     * <p>svRng130searchSubject1 を取得します。
     * @return svRng130searchSubject1
     */
    public int getSvRng130searchSubject1() {
        return svRng130searchSubject1__;
    }
    /**
     * <p>svRng130searchSubject1 をセットします。
     * @param svRng130searchSubject1 svRng130searchSubject1
     */
    public void setSvRng130searchSubject1(int svRng130searchSubject1) {
        svRng130searchSubject1__ = svRng130searchSubject1;
    }
    /**
     * <p>svRng130searchSubject2 を取得します。
     * @return svRng130searchSubject2
     */
    public int getSvRng130searchSubject2() {
        return svRng130searchSubject2__;
    }
    /**
     * <p>svRng130searchSubject2 をセットします。
     * @param svRng130searchSubject2 svRng130searchSubject2
     */
    public void setSvRng130searchSubject2(int svRng130searchSubject2) {
        svRng130searchSubject2__ = svRng130searchSubject2;
    }
    /**
     * <p>svRng130Type を取得します。
     * @return svRng130Type
     */
    public int getSvRng130Type() {
        return svRng130Type__;
    }
    /**
     * <p>svRng130Type をセットします。
     * @param svRng130Type svRng130Type
     */
    public void setSvRng130Type(int svRng130Type) {
        svRng130Type__ = svRng130Type;
    }
    /**
     * <p>svSortKey1 を取得します。
     * @return svSortKey1
     */
    public int getSvSortKey1() {
        return svSortKey1__;
    }
    /**
     * <p>svSortKey1 をセットします。
     * @param svSortKey1 svSortKey1
     */
    public void setSvSortKey1(int svSortKey1) {
        svSortKey1__ = svSortKey1;
    }
    /**
     * <p>svSortKey2 を取得します。
     * @return svSortKey2
     */
    public int getSvSortKey2() {
        return svSortKey2__;
    }
    /**
     * <p>svSortKey2 をセットします。
     * @param svSortKey2 svSortKey2
     */
    public void setSvSortKey2(int svSortKey2) {
        svSortKey2__ = svSortKey2;
    }
    /**
     * <p>svUserSid を取得します。
     * @return svUserSid
     */
    public int getSvUserSid() {
        return svUserSid__;
    }
    /**
     * <p>svUserSid をセットします。
     * @param svUserSid svUserSid
     */
    public void setSvUserSid(int svUserSid) {
        svUserSid__ = svUserSid;
    }
    /**
     * <p>sltApplDayFr を取得します。
     * @return sltApplDayFr
     */
    public int getSltApplDayFr() {
        return sltApplDayFr__;
    }
    /**
     * <p>sltApplDayFr をセットします。
     * @param sltApplDayFr sltApplDayFr
     */
    public void setSltApplDayFr(int sltApplDayFr) {
        sltApplDayFr__ = sltApplDayFr;
    }
    /**
     * <p>sltApplDayTo を取得します。
     * @return sltApplDayTo
     */
    public int getSltApplDayTo() {
        return sltApplDayTo__;
    }
    /**
     * <p>sltApplDayTo をセットします。
     * @param sltApplDayTo sltApplDayTo
     */
    public void setSltApplDayTo(int sltApplDayTo) {
        sltApplDayTo__ = sltApplDayTo;
    }
    /**
     * <p>sltApplMonthFr を取得します。
     * @return sltApplMonthFr
     */
    public int getSltApplMonthFr() {
        return sltApplMonthFr__;
    }
    /**
     * <p>sltApplMonthFr をセットします。
     * @param sltApplMonthFr sltApplMonthFr
     */
    public void setSltApplMonthFr(int sltApplMonthFr) {
        sltApplMonthFr__ = sltApplMonthFr;
    }
    /**
     * <p>sltApplMonthTo を取得します。
     * @return sltApplMonthTo
     */
    public int getSltApplMonthTo() {
        return sltApplMonthTo__;
    }
    /**
     * <p>sltApplMonthTo をセットします。
     * @param sltApplMonthTo sltApplMonthTo
     */
    public void setSltApplMonthTo(int sltApplMonthTo) {
        sltApplMonthTo__ = sltApplMonthTo;
    }
    /**
     * <p>sltApplYearFr を取得します。
     * @return sltApplYearFr
     */
    public int getSltApplYearFr() {
        return sltApplYearFr__;
    }
    /**
     * <p>sltApplYearFr をセットします。
     * @param sltApplYearFr sltApplYearFr
     */
    public void setSltApplYearFr(int sltApplYearFr) {
        sltApplYearFr__ = sltApplYearFr;
    }
    /**
     * <p>sltApplYearTo を取得します。
     * @return sltApplYearTo
     */
    public int getSltApplYearTo() {
        return sltApplYearTo__;
    }
    /**
     * <p>sltApplYearTo をセットします。
     * @param sltApplYearTo sltApplYearTo
     */
    public void setSltApplYearTo(int sltApplYearTo) {
        sltApplYearTo__ = sltApplYearTo;
    }
    /**
     * <p>sltLastManageDayFr を取得します。
     * @return sltLastManageDayFr
     */
    public int getSltLastManageDayFr() {
        return sltLastManageDayFr__;
    }
    /**
     * <p>sltLastManageDayFr をセットします。
     * @param sltLastManageDayFr sltLastManageDayFr
     */
    public void setSltLastManageDayFr(int sltLastManageDayFr) {
        sltLastManageDayFr__ = sltLastManageDayFr;
    }
    /**
     * <p>sltLastManageDayTo を取得します。
     * @return sltLastManageDayTo
     */
    public int getSltLastManageDayTo() {
        return sltLastManageDayTo__;
    }
    /**
     * <p>sltLastManageDayTo をセットします。
     * @param sltLastManageDayTo sltLastManageDayTo
     */
    public void setSltLastManageDayTo(int sltLastManageDayTo) {
        sltLastManageDayTo__ = sltLastManageDayTo;
    }
    /**
     * <p>sltLastManageMonthFr を取得します。
     * @return sltLastManageMonthFr
     */
    public int getSltLastManageMonthFr() {
        return sltLastManageMonthFr__;
    }
    /**
     * <p>sltLastManageMonthFr をセットします。
     * @param sltLastManageMonthFr sltLastManageMonthFr
     */
    public void setSltLastManageMonthFr(int sltLastManageMonthFr) {
        sltLastManageMonthFr__ = sltLastManageMonthFr;
    }
    /**
     * <p>sltLastManageMonthTo を取得します。
     * @return sltLastManageMonthTo
     */
    public int getSltLastManageMonthTo() {
        return sltLastManageMonthTo__;
    }
    /**
     * <p>sltLastManageMonthTo をセットします。
     * @param sltLastManageMonthTo sltLastManageMonthTo
     */
    public void setSltLastManageMonthTo(int sltLastManageMonthTo) {
        sltLastManageMonthTo__ = sltLastManageMonthTo;
    }
    /**
     * <p>sltLastManageYearFr を取得します。
     * @return sltLastManageYearFr
     */
    public int getSltLastManageYearFr() {
        return sltLastManageYearFr__;
    }
    /**
     * <p>sltLastManageYearFr をセットします。
     * @param sltLastManageYearFr sltLastManageYearFr
     */
    public void setSltLastManageYearFr(int sltLastManageYearFr) {
        sltLastManageYearFr__ = sltLastManageYearFr;
    }
    /**
     * <p>sltLastManageYearTo を取得します。
     * @return sltLastManageYearTo
     */
    public int getSltLastManageYearTo() {
        return sltLastManageYearTo__;
    }
    /**
     * <p>sltLastManageYearTo をセットします。
     * @param sltLastManageYearTo sltLastManageYearTo
     */
    public void setSltLastManageYearTo(int sltLastManageYearTo) {
        sltLastManageYearTo__ = sltLastManageYearTo;
    }
    /**
     * <p>svApplDayFr を取得します。
     * @return svApplDayFr
     */
    public int getSvApplDayFr() {
        return svApplDayFr__;
    }
    /**
     * <p>svApplDayFr をセットします。
     * @param svApplDayFr svApplDayFr
     */
    public void setSvApplDayFr(int svApplDayFr) {
        svApplDayFr__ = svApplDayFr;
    }
    /**
     * <p>svApplDayTo を取得します。
     * @return svApplDayTo
     */
    public int getSvApplDayTo() {
        return svApplDayTo__;
    }
    /**
     * <p>svApplDayTo をセットします。
     * @param svApplDayTo svApplDayTo
     */
    public void setSvApplDayTo(int svApplDayTo) {
        svApplDayTo__ = svApplDayTo;
    }
    /**
     * <p>svApplMonthFr を取得します。
     * @return svApplMonthFr
     */
    public int getSvApplMonthFr() {
        return svApplMonthFr__;
    }
    /**
     * <p>svApplMonthFr をセットします。
     * @param svApplMonthFr svApplMonthFr
     */
    public void setSvApplMonthFr(int svApplMonthFr) {
        svApplMonthFr__ = svApplMonthFr;
    }
    /**
     * <p>svApplMonthTo を取得します。
     * @return svApplMonthTo
     */
    public int getSvApplMonthTo() {
        return svApplMonthTo__;
    }
    /**
     * <p>svApplMonthTo をセットします。
     * @param svApplMonthTo svApplMonthTo
     */
    public void setSvApplMonthTo(int svApplMonthTo) {
        svApplMonthTo__ = svApplMonthTo;
    }
    /**
     * <p>svApplYearFr を取得します。
     * @return svApplYearFr
     */
    public int getSvApplYearFr() {
        return svApplYearFr__;
    }
    /**
     * <p>svApplYearFr をセットします。
     * @param svApplYearFr svApplYearFr
     */
    public void setSvApplYearFr(int svApplYearFr) {
        svApplYearFr__ = svApplYearFr;
    }
    /**
     * <p>svApplYearTo を取得します。
     * @return svApplYearTo
     */
    public int getSvApplYearTo() {
        return svApplYearTo__;
    }
    /**
     * <p>svApplYearTo をセットします。
     * @param svApplYearTo svApplYearTo
     */
    public void setSvApplYearTo(int svApplYearTo) {
        svApplYearTo__ = svApplYearTo;
    }
    /**
     * <p>svLastManageDayFr を取得します。
     * @return svLastManageDayFr
     */
    public int getSvLastManageDayFr() {
        return svLastManageDayFr__;
    }
    /**
     * <p>svLastManageDayFr をセットします。
     * @param svLastManageDayFr svLastManageDayFr
     */
    public void setSvLastManageDayFr(int svLastManageDayFr) {
        svLastManageDayFr__ = svLastManageDayFr;
    }
    /**
     * <p>svLastManageDayTo を取得します。
     * @return svLastManageDayTo
     */
    public int getSvLastManageDayTo() {
        return svLastManageDayTo__;
    }
    /**
     * <p>svLastManageDayTo をセットします。
     * @param svLastManageDayTo svLastManageDayTo
     */
    public void setSvLastManageDayTo(int svLastManageDayTo) {
        svLastManageDayTo__ = svLastManageDayTo;
    }
    /**
     * <p>svLastManageMonthFr を取得します。
     * @return svLastManageMonthFr
     */
    public int getSvLastManageMonthFr() {
        return svLastManageMonthFr__;
    }
    /**
     * <p>svLastManageMonthFr をセットします。
     * @param svLastManageMonthFr svLastManageMonthFr
     */
    public void setSvLastManageMonthFr(int svLastManageMonthFr) {
        svLastManageMonthFr__ = svLastManageMonthFr;
    }
    /**
     * <p>svLastManageMonthTo を取得します。
     * @return svLastManageMonthTo
     */
    public int getSvLastManageMonthTo() {
        return svLastManageMonthTo__;
    }
    /**
     * <p>svLastManageMonthTo をセットします。
     * @param svLastManageMonthTo svLastManageMonthTo
     */
    public void setSvLastManageMonthTo(int svLastManageMonthTo) {
        svLastManageMonthTo__ = svLastManageMonthTo;
    }
    /**
     * <p>svLastManageYearFr を取得します。
     * @return svLastManageYearFr
     */
    public int getSvLastManageYearFr() {
        return svLastManageYearFr__;
    }
    /**
     * <p>svLastManageYearFr をセットします。
     * @param svLastManageYearFr svLastManageYearFr
     */
    public void setSvLastManageYearFr(int svLastManageYearFr) {
        svLastManageYearFr__ = svLastManageYearFr;
    }
    /**
     * <p>svLastManageYearTo を取得します。
     * @return svLastManageYearTo
     */
    public int getSvLastManageYearTo() {
        return svLastManageYearTo__;
    }
    /**
     * <p>svLastManageYearTo をセットします。
     * @param svLastManageYearTo svLastManageYearTo
     */
    public void setSvLastManageYearTo(int svLastManageYearTo) {
        svLastManageYearTo__ = svLastManageYearTo;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param userSid ユーザSID
     * @param isSv 保存用パラメータ使用判定
     * @param reqMdl リクエストモデル
     * @param isAdmin 管理者フラグ(システム管理者 or プラグイン管理者)
     * @throws SQLException SQLエラー
     * @return errors エラー
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors validateCheck(HttpServletRequest req, Connection con,
            int userSid, boolean isSv, RequestModel reqMdl, boolean isAdmin) 
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String msgKeyword   = gsMsg.getMessage(req, "cmn.keyword");
        String msgSearchSub = gsMsg.getMessage(req, "cmn.search2");

        String shinFrom = gsMsg.getMessage(req, "rng.83");
        String shinTo = gsMsg.getMessage(req, "rng.84");
        String lastFrom = gsMsg.getMessage(req, "rng.78");
        String lastTo = gsMsg.getMessage(req, "rng.79");

        if (!StringUtil.isNullZeroString(rng130ApplDateFr__)) {
            errors.add(picker.setYmdParam(this, "rng130ApplDateFr",
                    "sltApplYearFr", "sltApplMonthFr",
                    "sltApplDayFr", shinFrom));
        }
        if (!StringUtil.isNullZeroString(rng130ApplDateTo__)) {
            errors.add(picker.setYmdParam(this, "rng130ApplDateTo",
                    "sltApplYearTo", "sltApplMonthTo",
                    "sltApplDayTo", shinTo));
        }
        if (!StringUtil.isNullZeroString(rng130LastManageDateFr__)) {
            errors.add(picker.setYmdParam(this, "rng130LastManageDateFr",
                    "sltLastManageYearFr", "sltLastManageMonthFr",
                    "sltLastManageDayFr", lastFrom));
        }
        if (!StringUtil.isNullZeroString(rng130LastManageDateTo__)) {
            errors.add(picker.setYmdParam(this, "rng130LastManageDateTo",
                    "sltLastManageYearTo", "sltLastManageMonthTo",
                    "sltLastManageDayTo", lastTo));
        }
        // 検索パラメータ
        int    viewSid      = this.getRng010ViewAccount();
        int    viewType     = this.getRng130Type();
        String keyword      = this.getRngKeyword();
        int    subject1     = this.getRng130searchSubject1();
        int    subject2     = this.getRng130searchSubject2();
        int    subject3     = this.getRng130searchSubject3();
        int    applFrYear   = this.getSltApplYearFr();
        int    applFrMonth  = this.getSltApplMonthFr();
        int    applFrDay    = this.getSltApplDayFr();
        int    applToYear   = this.getSltApplYearTo();
        int    applToMonth  = this.getSltApplMonthTo();
        int    applToDay    = this.getSltApplDayTo();
        int    lastFrYear   = this.getSltLastManageYearFr();
        int    lastFrMonth  = this.getSltLastManageMonthFr();
        int    lastFrDay    = this.getSltLastManageDayFr();
        int    lastToYear   = this.getSltLastManageYearTo();
        int    lastToMonth  = this.getSltLastManageMonthTo();
        int    lastToDay    = this.getSltLastManageDayTo();
        int    sortKey1     = this.getSltSortKey1();
        int    sortKey2     = this.getSltSortKey2();
        int    applUserSid  = this.getSltUserSid();
        int    applGroupSid = this.getSltGroupSid();
        int    category      = this.getRng010SearchCategory();

        // 保存用パラメータ使用の場合
        if (isSv) {
            viewSid      = this.getSvRngViewAccount();
            viewType     = this.getSvRng130Type();
            keyword      = this.getSvRngKeyword();
            subject1     = this.getSvRng130searchSubject1();
            subject2     = this.getSvRng130searchSubject2();
            subject3     = this.getSvRng130searchSubject3();
            applFrYear   = this.getSvApplYearFr();
            applFrMonth  = this.getSvApplMonthFr();
            applFrDay    = this.getSvApplDayFr();
            applToYear   = this.getSvApplYearTo();
            applToMonth  = this.getSvApplMonthTo();
            applToDay    = this.getSvApplDayTo();
            lastFrYear   = this.getSvLastManageYearFr();
            lastFrMonth  = this.getSvLastManageMonthFr();
            lastFrDay    = this.getSvLastManageDayFr();
            lastToYear   = this.getSvLastManageYearTo();
            lastToMonth  = this.getSvLastManageMonthTo();
            lastToDay    = this.getSvLastManageDayTo();
            sortKey1     = this.getSvSortKey1();
            sortKey2     = this.getSvSortKey2();
            applUserSid  = this.getSvUserSid();
            applGroupSid = this.getSvGroupSid();
            category     = this.getSvRng130Category();
        }

        // アカウント
        if (!this.validateCheckAccount(req, con, userSid, viewSid)) {
            // アカウントの使用権限がないのでエラー
            ActionMessage msg = new ActionMessage("error.input.format.file",
                                                  gsMsg.getMessage(req, "wml.99"),
                                                  gsMsg.getMessage(req, "cmn.user.sid"));
            errors.add("error.input.format.file", msg);
        }

        // 検索種別（草稿は代理人で確認不可）
        if (userSid != viewSid && viewType == RngConst.RNG_MODE_SOUKOU) {
            // アカウントの使用権限がないのでエラー
            ActionMessage msg = new ActionMessage("error.input.format.file",
                                                  gsMsg.getMessage(req, "cmn.type"),
                                                  gsMsg.getMessage(req, "main.man340.10"));
            errors.add("error.input.format.file", msg);

        }

        // カテゴリ
        if (category > 0) {
            ArrayList<RngTemplateCategoryModel> categoryList; // テンプレートカテゴリ一覧
            RngBiz biz = new RngBiz(con);
            //共有のカテゴリを取得する
            categoryList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                               userSid, isAdmin, RngConst.RTPLIST_MOKUTEKI_USE);
            boolean isCategory = false;
            for (RngTemplateCategoryModel mdl : categoryList) {
                if (category == mdl.getRtcSid()) {
                    isCategory = true;
                }
            }
            if (!isCategory) {
                ActionMessage msg = new ActionMessage("error.input.format.file",
                        gsMsg.getMessage(req, "cmn.category"),
                        gsMsg.getMessage(req, "cmn.category.select"));
                errors.add("error.input.format.file", msg);
            }
        }
        // 申請者 グループ
        boolean isGroup = applGroupSid > -1;
        if (isGroup && !GSValidateUser.existGroup(applGroupSid, con)) {
                ActionMessage msg = new ActionMessage("error.input.format.file",
                        gsMsg.getMessage(req, "rng.47"),
                        gsMsg.getMessage(req, "cmn.group.sid"));
                errors.add("error.input.format.file", msg);
                isGroup = false;
        }

        // 申請者 ユーザ
        if (isGroup && applUserSid > -1) {
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            if (!cuDao.isExistUser(applUserSid)) {
                ActionMessage msg = new ActionMessage("error.input.format.file",
                        gsMsg.getMessage(req, "rng.47"),
                        gsMsg.getMessage(req, "cmn.user.sid"));
                errors.add("error.input.format.file", msg);
            }
        }

        //キーワード
        errors = RngValidate.validateCmnFieldText(
                errors,                 //errors
                msgKeyword,             //エラーメッセージ表示テキスト
                keyword,                //チェックするフィールド
                "rngAdminKeyword__",    //チェックするフィールドの文字列
                100,                    //最大桁数
                false);                 //入力必須か

        //検索対象
        if (!StringUtil.isNullZeroString(keyword)) {
            if (viewType == RngConst.RNG_MODE_SOUKOU) {
                subject3 = 0;
            }
            if (subject1 + subject2 + subject3 == 0) {
                ActionMessage msg = new ActionMessage("error.input.required.text", msgSearchSub);
                errors.add("error.input.required.text", msg);
            }
        }

        Rng050Form form050 = new Rng050Form();
        int errCount = errors.size();
        boolean inputFrom = false;
        boolean inputTo = false;
        if (applFrYear > 0 || applFrMonth > 0 || applFrDay > 0) {

            //申請日時From
            errors = form050.checkDate(errors, shinFrom, applFrYear, applFrMonth, applFrDay, req);
            inputFrom = true;
        }

        if (applToYear > 0 || applToMonth > 0 || applToDay > 0) {
            //申請日時To
            errors = form050.checkDate(errors, shinTo, applToYear, applToMonth, applToDay, req);
            inputTo = true;
        }

        if (inputFrom && inputTo && errCount == errors.size()) {
            //申請日時範囲チェック
            long time = System.currentTimeMillis();
            UDate frDate = UDate.getInstance(time);
            UDate toDate = UDate.getInstance(time);
            frDate.setDate(applFrYear, applFrMonth, applFrDay);
            toDate.setDate(applToYear, applToMonth, applToDay);

            errors = form050.checkDateRange(errors, shinFrom, shinTo, frDate, toDate);
        }

        errCount = errors.size();
        inputFrom = false;
        inputTo = false;
        if (lastFrYear > 0 || lastFrMonth > 0 || lastFrDay > 0) {
            //最終確認日時From
            errors = form050.checkDate(errors, lastFrom,
                    lastFrYear,
                    lastFrMonth,
                    lastFrDay,
                                       req);
            inputFrom = true;
        }

        if (lastToYear > 0 || lastToMonth > 0 || lastToDay > 0) {
            //最終確認日時To
            errors = form050.checkDate(errors, lastTo, lastToYear, lastToMonth, lastToDay, req);
            inputTo = true;
        }

        if (inputFrom && inputTo && errCount == errors.size()) {
            //最終確認日時範囲チェック
            //申請日時範囲チェック
            long time = System.currentTimeMillis();
            UDate frDate = UDate.getInstance(time);
            UDate toDate = UDate.getInstance(time);
            frDate.setDate(lastFrYear, lastFrMonth, lastFrDay);
            toDate.setDate(lastToYear, lastToMonth, lastToDay);

            errors = form050.checkDateRange(errors, lastFrom, lastTo, frDate, toDate);
        }

        String sort = gsMsg.getMessage(req, "cmn.sort.order");

        //ソート順
        if (sortKey1 == sortKey2) {
            ActionMessage msg = new ActionMessage("error.select.dup.list", sort);
            errors.add("error.select.dup.list", msg);
        }

        return errors;
    }

    /**
     * <br>[機  能] アカウントチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @param viewSid アカウントユーザSID
     * @return エラー
     */
    public boolean validateCheckAccount(HttpServletRequest req, Connection con,
                                        int userSid, int viewSid) {
        if (viewSid != userSid) {
            boolean ret = false;
            // 使用アカウントがログインユーザではない場合、代理人チェック
            RngDairiUserDao dairiDao = new RngDairiUserDao(con);
            try {
                UDate now = new UDate();
                List<AccountDataModel> dairiList = dairiDao.select(userSid, now, now);
                for (AccountDataModel mdl : dairiList) {
                    if (mdl.getAccountSid() == viewSid) {
                        ret = true;
                        break;
                    }
                }
            } catch (SQLException e) {
            }
            return ret;
        }
        return true;
    }
    /**
     * <p>rng130rngNowDate を取得します。
     * @return rng130rngNowDate
     * @see jp.groupsession.v2.rng.rng130.Rng130ParamModel#rng130rngNowDate__
     */
    public String getRng130rngNowDate() {
        return rng130rngNowDate__;
    }
    /**
     * <p>rng130rngNowDate をセットします。
     * @param rng130rngNowDate rng130rngNowDate
     * @see jp.groupsession.v2.rng.rng130.Rng130ParamModel#rng130rngNowDate__
     */
    public void setRng130rngNowDate(String rng130rngNowDate) {
        rng130rngNowDate__ = rng130rngNowDate;
    }
    /**
     * <p>rng130outPutDirHash を取得します。
     * @return rng130outPutDirHash
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130outPutDirHash__
     */
    public String getRng130outPutDirHash() {
        return rng130outPutDirHash__;
    }
    /**
     * <p>rng130outPutDirHash をセットします。
     * @param rng130outPutDirHash rng130outPutDirHash
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130outPutDirHash__
     */
    public void setRng130outPutDirHash(String rng130outPutDirHash) {
        rng130outPutDirHash__ = rng130outPutDirHash;
    }
    /**
     * <p>svRng130Category を取得します。
     * @return svRng130Category
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#svRng130Category__
     */
    public int getSvRng130Category() {
        return svRng130Category__;
    }
    /**
     * <p>svRng130Category をセットします。
     * @param svRng130Category svRng130Category
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#svRng130Category__
     */
    public void setSvRng130Category(int svRng130Category) {
        svRng130Category__ = svRng130Category;
    }
    /**
     * <p>rng130pdfOutputCnt を取得します。
     * @return rng130pdfOutputCnt
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130pdfOutputCnt__
     */
    public int getRng130pdfOutputCnt() {
        return rng130pdfOutputCnt__;
    }
    /**
     * <p>rng130pdfOutputCnt をセットします。
     * @param rng130pdfOutputCnt rng130pdfOutputCnt
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130pdfOutputCnt__
     */
    public void setRng130pdfOutputCnt(int rng130pdfOutputCnt) {
        rng130pdfOutputCnt__ = rng130pdfOutputCnt;
    }
    /**
     * <p>rng130pdfOutputMax を取得します。
     * @return rng130pdfOutputMax
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130pdfOutputMax__
     */
    public int getRng130pdfOutputMax() {
        return rng130pdfOutputMax__;
    }
    /**
     * <p>rng130pdfOutputMax をセットします。
     * @param rng130pdfOutputMax rng130pdfOutputMax
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130pdfOutputMax__
     */
    public void setRng130pdfOutputMax(int rng130pdfOutputMax) {
        rng130pdfOutputMax__ = rng130pdfOutputMax;
    }
    /**
     * <p>rng130searchSubject3 を取得します。
     * @return rng130searchSubject3
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130searchSubject3__
     */
    public int getRng130searchSubject3() {
        return rng130searchSubject3__;
    }
    /**
     * <p>rng130searchSubject3 をセットします。
     * @param rng130searchSubject3 rng130searchSubject3
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130searchSubject3__
     */
    public void setRng130searchSubject3(int rng130searchSubject3) {
        rng130searchSubject3__ = rng130searchSubject3;
    }
    /**
     * <p>svRng130searchSubject3 を取得します。
     * @return svRng130searchSubject3
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#svRng130searchSubject3__
     */
    public int getSvRng130searchSubject3() {
        return svRng130searchSubject3__;
    }
    /**
     * <p>svRng130searchSubject3 をセットします。
     * @param svRng130searchSubject3 svRng130searchSubject3
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#svRng130searchSubject3__
     */
    public void setSvRng130searchSubject3(int svRng130searchSubject3) {
        svRng130searchSubject3__ = svRng130searchSubject3;
    }
    /**
     * <p>rng130Status を取得します。
     * @return rng130Status
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130Status__
     */
    public int getRng130Status() {
        return rng130Status__;
    }
    /**
     * <p>rng130Status をセットします。
     * @param rng130Status rng130Status
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130Status__
     */
    public void setRng130Status(int rng130Status) {
        rng130Status__ = rng130Status;
    }
    /**
     * <p>svRng130Status を取得します。
     * @return svRng130Status
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#svRng130Status__
     */
    public int getSvRng130Status() {
        return svRng130Status__;
    }
    /**
     * <p>svRng130Status をセットします。
     * @param svRng130Status svRng130Status
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#svRng130Status__
     */
    public void setSvRng130Status(int svRng130Status) {
        svRng130Status__ = svRng130Status;
    }
    /**
     * <p>rng130ApplDateFr を取得します。
     * @return rng130ApplDateFr
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130ApplDateFr__
     */
    public String getRng130ApplDateFr() {
        return rng130ApplDateFr__;
    }
    /**
     * <p>rng130ApplDateFr をセットします。
     * @param rng130ApplDateFr rng130ApplDateFr
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130ApplDateFr__
     */
    public void setRng130ApplDateFr(String rng130ApplDateFr) {
        rng130ApplDateFr__ = rng130ApplDateFr;
    }
    /**
     * <p>rng130ApplDateTo を取得します。
     * @return rng130ApplDateTo
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130ApplDateTo__
     */
    public String getRng130ApplDateTo() {
        return rng130ApplDateTo__;
    }
    /**
     * <p>rng130ApplDateTo をセットします。
     * @param rng130ApplDateTo rng130ApplDateTo
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130ApplDateTo__
     */
    public void setRng130ApplDateTo(String rng130ApplDateTo) {
        rng130ApplDateTo__ = rng130ApplDateTo;
    }
    /**
     * <p>rng130LastManageDateFr を取得します。
     * @return rng130LastManageDateFr
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130LastManageDateFr__
     */
    public String getRng130LastManageDateFr() {
        return rng130LastManageDateFr__;
    }
    /**
     * <p>rng130LastManageDateFr をセットします。
     * @param rng130LastManageDateFr rng130LastManageDateFr
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130LastManageDateFr__
     */
    public void setRng130LastManageDateFr(String rng130LastManageDateFr) {
        rng130LastManageDateFr__ = rng130LastManageDateFr;
    }
    /**
     * <p>rng130LastManageDateTo を取得します。
     * @return rng130LastManageDateTo
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130LastManageDateTo__
     */
    public String getRng130LastManageDateTo() {
        return rng130LastManageDateTo__;
    }
    /**
     * <p>rng130LastManageDateTo をセットします。
     * @param rng130LastManageDateTo rng130LastManageDateTo
     * @see jp.groupsession.v2.rng.rng130.Rng130Form#rng130LastManageDateTo__
     */
    public void setRng130LastManageDateTo(String rng130LastManageDateTo) {
        rng130LastManageDateTo__ = rng130LastManageDateTo;
    }

    /**
     * <p>rng130MinYear を取得します。
     * @return rng130MinYear
     * @see jp.groupsession.v2.rng.rng130.Rng130ParamModel#rng130MinYear__
     */
    public int getRng130MinYear() {
        return rng130MinYear__;
    }
    /**
     * <p>rng130MinYear をセットします。
     * @param rng130MinYear rng130MinYear
     * @see jp.groupsession.v2.rng.rng130.Rng130ParamModel#rng130MinYear__
     */
    public void setRng130MinYear(int rng130MinYear) {
        rng130MinYear__ = rng130MinYear;
    }
}
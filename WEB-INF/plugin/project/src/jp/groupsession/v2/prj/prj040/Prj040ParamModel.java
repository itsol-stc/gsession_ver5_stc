package jp.groupsession.v2.prj.prj040;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.prj010.Prj010ParamModel;

/**
 * <br>[機  能] プロジェクト詳細検索画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj040ParamModel extends Prj010ParamModel {

    //検索条件
    /** プロジェクトID */
    private String prj040scPrjId__;
    /** 状態From */
    private String prj040scStatusFrom__;
    /** 状態To */
    private String prj040scStatusTo__;
    /** プロジェクト名 */
    private String prj040scPrjName__;
    /** メンバーSID */
    private String[] prj040scMemberSid__;
    /** 開始年From */
    private String prj040scStartYearFrom__;
    /** 開始月From */
    private String prj040scStartMonthFrom__;
    /** 開始日From */
    private String prj040scStartDayFrom__;
    /** 開始年To */
    private String prj040scStartYearTo__;
    /** 開始月To */
    private String prj040scStartMonthTo__;
    /** 開始日To */
    private String prj040scStartDayTo__;
    /** 終了年From */
    private String prj040scEndYearFrom__;
    /** 終了月From */
    private String prj040scEndMonthFrom__;
    /** 終了日From */
    private String prj040scEndDayFrom__;
    /** 終了年To */
    private String prj040scEndYearTo__;
    /** 終了月To */
    private String prj040scEndMonthTo__;
    /** 終了日To */
    private String prj040scEndDayTo__;
    /** 予算From */
    private String prj040scYosanFr__;
    /** 予算To */
    private String prj040scYosanTo__;

    //save項目
    /** (save)プロジェクトID */
    private String prj040svScPrjId__;
    /** (save)状態From */
    private String prj040svScStatusFrom__;
    /** (save)状態To */
    private String prj040svScStatusTo__;
    /** (save)プロジェクト名 */
    private String prj040svScPrjName__;
    /** (save)メンバーSID */
    private String[] prj040svScMemberSid__;
    /** (save)開始年From */
    private String prj040svScStartYearFrom__;
    /** (save)開始月From */
    private String prj040svScStartMonthFrom__;
    /** (save)開始日From */
    private String prj040svScStartDayFrom__;
    /** (save)開始年To */
    private String prj040svScStartYearTo__;
    /** (save)開始月To */
    private String prj040svScStartMonthTo__;
    /** (save)開始日To */
    private String prj040svScStartDayTo__;
    /** (save)終了年From */
    private String prj040svScEndYearFrom__;
    /** (save)終了月From */
    private String prj040svScEndMonthFrom__;
    /** (save)終了日From */
    private String prj040svScEndDayFrom__;
    /** (save)終了年To */
    private String prj040svScEndYearTo__;
    /** (save)終了月To */
    private String prj040svScEndMonthTo__;
    /** (save)終了日To */
    private String prj040svScEndDayTo__;
    /** (save)予算From */
    private String prj040svScYosanFr__;
    /** (save)予算To */
    private String prj040svScYosanTo__;

    /** 開始年月日From */
    private String prj040StartDateFr__ = null;
    /** 開始年月日To */
    private String prj040StartDateTo__ = null;
    /** 終了年月日From */
    private String prj040EndDateFr__ = null;
    /** 終了年月日To */
    private String prj040EndDateTo__ = null;

    /** ページ1 */
    private int prj040page1__;
    /** ページ2 */
    private int prj040page2__;
    /** ソートキー */
    private int prj040sort__ = GSConst.ORDER_KEY_ASC;
    /** オーダーキー */
    private int prj040order__ = GSConstProject.SORT_PRJECT_START;

    //表示項目
    /** メンバーリスト */
    private List<CmnUsrmInfModel> memberList__ = null;
    /** 登録者リスト */
    private List<CmnUsrmInfModel> addUserList__ = null;

    /**
     * <p>prj040scPrjId を取得します。
     * @return prj040scPrjId
     */
    public String getPrj040scPrjId() {
        return prj040scPrjId__;
    }

    /**
     * <p>prj040scPrjId をセットします。
     * @param prj040scPrjId prj040scPrjId
     */
    public void setPrj040scPrjId(String prj040scPrjId) {
        prj040scPrjId__ = prj040scPrjId;
    }

    /**
     * <p>prj040scStatusFrom を取得します。
     * @return prj040scStatusFrom
     */
    public String getPrj040scStatusFrom() {
        return prj040scStatusFrom__;
    }

    /**
     * <p>prj040scStatusFrom をセットします。
     * @param prj040scStatusFrom prj040scStatusFrom
     */
    public void setPrj040scStatusFrom(String prj040scStatusFrom) {
        prj040scStatusFrom__ = prj040scStatusFrom;
    }

    /**
     * <p>prj040scStatusTo を取得します。
     * @return prj040scStatusTo
     */
    public String getPrj040scStatusTo() {
        return prj040scStatusTo__;
    }

    /**
     * <p>prj040scStatusTo をセットします。
     * @param prj040scStatusTo prj040scStatusTo
     */
    public void setPrj040scStatusTo(String prj040scStatusTo) {
        prj040scStatusTo__ = prj040scStatusTo;
    }

    /**
     * <p>prj040scPrjName を取得します。
     * @return prj040scPrjName
     */
    public String getPrj040scPrjName() {
        return prj040scPrjName__;
    }

    /**
     * <p>prj040scPrjName をセットします。
     * @param prj040scPrjName prj040scPrjName
     */
    public void setPrj040scPrjName(String prj040scPrjName) {
        prj040scPrjName__ = prj040scPrjName;
    }

    /**
     * <p>prj040scStartYearFrom を取得します。
     * @return prj040scStartYearFrom
     */
    public String getPrj040scStartYearFrom() {
        return prj040scStartYearFrom__;
    }

    /**
     * <p>prj040scStartYearFrom をセットします。
     * @param prj040scStartYearFrom prj040scStartYearFrom
     */
    public void setPrj040scStartYearFrom(String prj040scStartYearFrom) {
        prj040scStartYearFrom__ = prj040scStartYearFrom;
    }

    /**
     * <p>prj040scStartMonthFrom を取得します。
     * @return prj040scStartMonthFrom
     */
    public String getPrj040scStartMonthFrom() {
        return prj040scStartMonthFrom__;
    }

    /**
     * <p>prj040scStartMonthFrom をセットします。
     * @param prj040scStartMonthFrom prj040scStartMonthFrom
     */
    public void setPrj040scStartMonthFrom(String prj040scStartMonthFrom) {
        prj040scStartMonthFrom__ = prj040scStartMonthFrom;
    }

    /**
     * <p>prj040scStartDayFrom を取得します。
     * @return prj040scStartDayFrom
     */
    public String getPrj040scStartDayFrom() {
        return prj040scStartDayFrom__;
    }

    /**
     * <p>prj040scStartDayFrom をセットします。
     * @param prj040scStartDayFrom prj040scStartDayFrom
     */
    public void setPrj040scStartDayFrom(String prj040scStartDayFrom) {
        prj040scStartDayFrom__ = prj040scStartDayFrom;
    }

    /**
     * <p>prj040scStartYearTo を取得します。
     * @return prj040scStartYearTo
     */
    public String getPrj040scStartYearTo() {
        return prj040scStartYearTo__;
    }

    /**
     * <p>prj040scStartYearTo をセットします。
     * @param prj040scStartYearTo prj040scStartYearTo
     */
    public void setPrj040scStartYearTo(String prj040scStartYearTo) {
        prj040scStartYearTo__ = prj040scStartYearTo;
    }

    /**
     * <p>prj040scStartMonthTo を取得します。
     * @return prj040scStartMonthTo
     */
    public String getPrj040scStartMonthTo() {
        return prj040scStartMonthTo__;
    }

    /**
     * <p>prj040scStartMonthTo をセットします。
     * @param prj040scStartMonthTo prj040scStartMonthTo
     */
    public void setPrj040scStartMonthTo(String prj040scStartMonthTo) {
        prj040scStartMonthTo__ = prj040scStartMonthTo;
    }

    /**
     * <p>prj040scStartDayTo を取得します。
     * @return prj040scStartDayTo
     */
    public String getPrj040scStartDayTo() {
        return prj040scStartDayTo__;
    }

    /**
     * <p>prj040scStartDayTo をセットします。
     * @param prj040scStartDayTo prj040scStartDayTo
     */
    public void setPrj040scStartDayTo(String prj040scStartDayTo) {
        prj040scStartDayTo__ = prj040scStartDayTo;
    }

    /**
     * <p>prj040scEndYearFrom を取得します。
     * @return prj040scEndYearFrom
     */
    public String getPrj040scEndYearFrom() {
        return prj040scEndYearFrom__;
    }

    /**
     * <p>prj040scEndYearFrom をセットします。
     * @param prj040scEndYearFrom prj040scEndYearFrom
     */
    public void setPrj040scEndYearFrom(String prj040scEndYearFrom) {
        prj040scEndYearFrom__ = prj040scEndYearFrom;
    }

    /**
     * <p>prj040scEndMonthFrom を取得します。
     * @return prj040scEndMonthFrom
     */
    public String getPrj040scEndMonthFrom() {
        return prj040scEndMonthFrom__;
    }

    /**
     * <p>prj040scEndMonthFrom をセットします。
     * @param prj040scEndMonthFrom prj040scEndMonthFrom
     */
    public void setPrj040scEndMonthFrom(String prj040scEndMonthFrom) {
        prj040scEndMonthFrom__ = prj040scEndMonthFrom;
    }

    /**
     * <p>prj040scEndDayFrom を取得します。
     * @return prj040scEndDayFrom
     */
    public String getPrj040scEndDayFrom() {
        return prj040scEndDayFrom__;
    }

    /**
     * <p>prj040scEndDayFrom をセットします。
     * @param prj040scEndDayFrom prj040scEndDayFrom
     */
    public void setPrj040scEndDayFrom(String prj040scEndDayFrom) {
        prj040scEndDayFrom__ = prj040scEndDayFrom;
    }

    /**
     * <p>prj040scEndYearTo を取得します。
     * @return prj040scEndYearTo
     */
    public String getPrj040scEndYearTo() {
        return prj040scEndYearTo__;
    }

    /**
     * <p>prj040scEndYearTo をセットします。
     * @param prj040scEndYearTo prj040scEndYearTo
     */
    public void setPrj040scEndYearTo(String prj040scEndYearTo) {
        prj040scEndYearTo__ = prj040scEndYearTo;
    }

    /**
     * <p>prj040scEndMonthTo を取得します。
     * @return prj040scEndMonthTo
     */
    public String getPrj040scEndMonthTo() {
        return prj040scEndMonthTo__;
    }

    /**
     * <p>prj040scEndMonthTo をセットします。
     * @param prj040scEndMonthTo prj040scEndMonthTo
     */
    public void setPrj040scEndMonthTo(String prj040scEndMonthTo) {
        prj040scEndMonthTo__ = prj040scEndMonthTo;
    }

    /**
     * <p>prj040scEndDayTo を取得します。
     * @return prj040scEndDayTo
     */
    public String getPrj040scEndDayTo() {
        return prj040scEndDayTo__;
    }

    /**
     * <p>prj040scEndDayTo をセットします。
     * @param prj040scEndDayTo prj040scEndDayTo
     */
    public void setPrj040scEndDayTo(String prj040scEndDayTo) {
        prj040scEndDayTo__ = prj040scEndDayTo;
    }

    /**
     * <p>prj040svScPrjId を取得します。
     * @return prj040svScPrjId
     */
    public String getPrj040svScPrjId() {
        return prj040svScPrjId__;
    }

    /**
     * <p>prj040svScPrjId をセットします。
     * @param prj040svScPrjId prj040svScPrjId
     */
    public void setPrj040svScPrjId(String prj040svScPrjId) {
        prj040svScPrjId__ = prj040svScPrjId;
    }

    /**
     * <p>prj040svScStatusFrom を取得します。
     * @return prj040svScStatusFrom
     */
    public String getPrj040svScStatusFrom() {
        return prj040svScStatusFrom__;
    }

    /**
     * <p>prj040svScStatusFrom をセットします。
     * @param prj040svScStatusFrom prj040svScStatusFrom
     */
    public void setPrj040svScStatusFrom(String prj040svScStatusFrom) {
        prj040svScStatusFrom__ = prj040svScStatusFrom;
    }

    /**
     * <p>prj040svScStatusTo を取得します。
     * @return prj040svScStatusTo
     */
    public String getPrj040svScStatusTo() {
        return prj040svScStatusTo__;
    }

    /**
     * <p>prj040svScStatusTo をセットします。
     * @param prj040svScStatusTo prj040svScStatusTo
     */
    public void setPrj040svScStatusTo(String prj040svScStatusTo) {
        prj040svScStatusTo__ = prj040svScStatusTo;
    }

    /**
     * <p>prj040svScPrjName を取得します。
     * @return prj040svScPrjName
     */
    public String getPrj040svScPrjName() {
        return prj040svScPrjName__;
    }

    /**
     * <p>prj040svScPrjName をセットします。
     * @param prj040svScPrjName prj040svScPrjName
     */
    public void setPrj040svScPrjName(String prj040svScPrjName) {
        prj040svScPrjName__ = prj040svScPrjName;
    }

    /**
     * <p>prj040svScStartYearFrom を取得します。
     * @return prj040svScStartYearFrom
     */
    public String getPrj040svScStartYearFrom() {
        return prj040svScStartYearFrom__;
    }

    /**
     * <p>prj040svScStartYearFrom をセットします。
     * @param prj040svScStartYearFrom prj040svScStartYearFrom
     */
    public void setPrj040svScStartYearFrom(String prj040svScStartYearFrom) {
        prj040svScStartYearFrom__ = prj040svScStartYearFrom;
    }

    /**
     * <p>prj040svScStartMonthFrom を取得します。
     * @return prj040svScStartMonthFrom
     */
    public String getPrj040svScStartMonthFrom() {
        return prj040svScStartMonthFrom__;
    }

    /**
     * <p>prj040svScStartMonthFrom をセットします。
     * @param prj040svScStartMonthFrom prj040svScStartMonthFrom
     */
    public void setPrj040svScStartMonthFrom(String prj040svScStartMonthFrom) {
        prj040svScStartMonthFrom__ = prj040svScStartMonthFrom;
    }

    /**
     * <p>prj040svScStartDayFrom を取得します。
     * @return prj040svScStartDayFrom
     */
    public String getPrj040svScStartDayFrom() {
        return prj040svScStartDayFrom__;
    }

    /**
     * <p>prj040svScStartDayFrom をセットします。
     * @param prj040svScStartDayFrom prj040svScStartDayFrom
     */
    public void setPrj040svScStartDayFrom(String prj040svScStartDayFrom) {
        prj040svScStartDayFrom__ = prj040svScStartDayFrom;
    }

    /**
     * <p>prj040svScStartYearTo を取得します。
     * @return prj040svScStartYearTo
     */
    public String getPrj040svScStartYearTo() {
        return prj040svScStartYearTo__;
    }

    /**
     * <p>prj040svScStartYearTo をセットします。
     * @param prj040svScStartYearTo prj040svScStartYearTo
     */
    public void setPrj040svScStartYearTo(String prj040svScStartYearTo) {
        prj040svScStartYearTo__ = prj040svScStartYearTo;
    }

    /**
     * <p>prj040svScStartMonthTo を取得します。
     * @return prj040svScStartMonthTo
     */
    public String getPrj040svScStartMonthTo() {
        return prj040svScStartMonthTo__;
    }

    /**
     * <p>prj040svScStartMonthTo をセットします。
     * @param prj040svScStartMonthTo prj040svScStartMonthTo
     */
    public void setPrj040svScStartMonthTo(String prj040svScStartMonthTo) {
        prj040svScStartMonthTo__ = prj040svScStartMonthTo;
    }

    /**
     * <p>prj040svScStartDayTo を取得します。
     * @return prj040svScStartDayTo
     */
    public String getPrj040svScStartDayTo() {
        return prj040svScStartDayTo__;
    }

    /**
     * <p>prj040svScStartDayTo をセットします。
     * @param prj040svScStartDayTo prj040svScStartDayTo
     */
    public void setPrj040svScStartDayTo(String prj040svScStartDayTo) {
        prj040svScStartDayTo__ = prj040svScStartDayTo;
    }

    /**
     * <p>prj040svScEndYearFrom を取得します。
     * @return prj040svScEndYearFrom
     */
    public String getPrj040svScEndYearFrom() {
        return prj040svScEndYearFrom__;
    }

    /**
     * <p>prj040svScEndYearFrom をセットします。
     * @param prj040svScEndYearFrom prj040svScEndYearFrom
     */
    public void setPrj040svScEndYearFrom(String prj040svScEndYearFrom) {
        prj040svScEndYearFrom__ = prj040svScEndYearFrom;
    }

    /**
     * <p>prj040svScEndMonthFrom を取得します。
     * @return prj040svScEndMonthFrom
     */
    public String getPrj040svScEndMonthFrom() {
        return prj040svScEndMonthFrom__;
    }

    /**
     * <p>prj040svScEndMonthFrom をセットします。
     * @param prj040svScEndMonthFrom prj040svScEndMonthFrom
     */
    public void setPrj040svScEndMonthFrom(String prj040svScEndMonthFrom) {
        prj040svScEndMonthFrom__ = prj040svScEndMonthFrom;
    }

    /**
     * <p>prj040svScEndDayFrom を取得します。
     * @return prj040svScEndDayFrom
     */
    public String getPrj040svScEndDayFrom() {
        return prj040svScEndDayFrom__;
    }

    /**
     * <p>prj040svScEndDayFrom をセットします。
     * @param prj040svScEndDayFrom prj040svScEndDayFrom
     */
    public void setPrj040svScEndDayFrom(String prj040svScEndDayFrom) {
        prj040svScEndDayFrom__ = prj040svScEndDayFrom;
    }

    /**
     * <p>prj040svScEndYearTo を取得します。
     * @return prj040svScEndYearTo
     */
    public String getPrj040svScEndYearTo() {
        return prj040svScEndYearTo__;
    }

    /**
     * <p>prj040svScEndYearTo をセットします。
     * @param prj040svScEndYearTo prj040svScEndYearTo
     */
    public void setPrj040svScEndYearTo(String prj040svScEndYearTo) {
        prj040svScEndYearTo__ = prj040svScEndYearTo;
    }

    /**
     * <p>prj040svScEndMonthTo を取得します。
     * @return prj040svScEndMonthTo
     */
    public String getPrj040svScEndMonthTo() {
        return prj040svScEndMonthTo__;
    }

    /**
     * <p>prj040svScEndMonthTo をセットします。
     * @param prj040svScEndMonthTo prj040svScEndMonthTo
     */
    public void setPrj040svScEndMonthTo(String prj040svScEndMonthTo) {
        prj040svScEndMonthTo__ = prj040svScEndMonthTo;
    }

    /**
     * <p>prj040svScEndDayTo を取得します。
     * @return prj040svScEndDayTo
     */
    public String getPrj040svScEndDayTo() {
        return prj040svScEndDayTo__;
    }

    /**
     * <p>prj040svScEndDayTo をセットします。
     * @param prj040svScEndDayTo prj040svScEndDayTo
     */
    public void setPrj040svScEndDayTo(String prj040svScEndDayTo) {
        prj040svScEndDayTo__ = prj040svScEndDayTo;
    }

    /**
     * <p>prj040page1 を取得します。
     * @return prj040page1
     */
    public int getPrj040page1() {
        return prj040page1__;
    }

    /**
     * <p>prj040page1 をセットします。
     * @param prj040page1 prj040page1
     */
    public void setPrj040page1(int prj040page1) {
        prj040page1__ = prj040page1;
    }

    /**
     * <p>prj040page2 を取得します。
     * @return prj040page2
     */
    public int getPrj040page2() {
        return prj040page2__;
    }

    /**
     * <p>prj040page2 をセットします。
     * @param prj040page2 prj040page2
     */
    public void setPrj040page2(int prj040page2) {
        prj040page2__ = prj040page2;
    }

    /**
     * <p>prj040sort を取得します。
     * @return prj040sort
     */
    public int getPrj040sort() {
        return prj040sort__;
    }

    /**
     * <p>prj040sort をセットします。
     * @param prj040sort prj040sort
     */
    public void setPrj040sort(int prj040sort) {
        prj040sort__ = prj040sort;
    }

    /**
     * <p>prj040order を取得します。
     * @return prj040order
     */
    public int getPrj040order() {
        return prj040order__;
    }

    /**
     * <p>prj040order をセットします。
     * @param prj040order prj040order
     */
    public void setPrj040order(int prj040order) {
        prj040order__ = prj040order;
    }

    /**
     * <p>prj040scMemberSid を取得します。
     * @return prj040scMemberSid
     */
    public String[] getPrj040scMemberSid() {
        return prj040scMemberSid__;
    }

    /**
     * <p>prj040scMemberSid をセットします。
     * @param prj040scMemberSid prj040scMemberSid
     */
    public void setPrj040scMemberSid(String[] prj040scMemberSid) {
        prj040scMemberSid__ = prj040scMemberSid;
    }

    /**
     * <p>prj040svScMemberSid を取得します。
     * @return prj040svScMemberSid
     */
    public String[] getPrj040svScMemberSid() {
        return prj040svScMemberSid__;
    }

    /**
     * <p>prj040svScMemberSid をセットします。
     * @param prj040svScMemberSid prj040svScMemberSid
     */
    public void setPrj040svScMemberSid(String[] prj040svScMemberSid) {
        prj040svScMemberSid__ = prj040svScMemberSid;
    }

    /**
     * <p>memberList を取得します。
     * @return memberList
     */
    public List<CmnUsrmInfModel> getMemberList() {
        return memberList__;
    }

    /**
     * <p>memberList をセットします。
     * @param memberList memberList
     */
    public void setMemberList(List<CmnUsrmInfModel> memberList) {
        memberList__ = memberList;
    }

    /**
     * <p>addUserList を取得します。
     * @return addUserList
     */
    public List<CmnUsrmInfModel> getAddUserList() {
        return addUserList__;
    }

    /**
     * <p>addUserList をセットします。
     * @param addUserList addUserList
     */
    public void setAddUserList(List<CmnUsrmInfModel> addUserList) {
        addUserList__ = addUserList;
    }

    /**
     * <p>prj040scYosanFr を取得します。
     * @return prj040scYosanFr
     */
    public String getPrj040scYosanFr() {
        return prj040scYosanFr__;
    }

    /**
     * <p>prj040scYosanFr をセットします。
     * @param prj040scYosanFr prj040scYosanFr
     */
    public void setPrj040scYosanFr(String prj040scYosanFr) {
        prj040scYosanFr__ = prj040scYosanFr;
    }

    /**
     * <p>prj040scYosanTo を取得します。
     * @return prj040scYosanTo
     */
    public String getPrj040scYosanTo() {
        return prj040scYosanTo__;
    }

    /**
     * <p>prj040scYosanTo をセットします。
     * @param prj040scYosanTo prj040scYosanTo
     */
    public void setPrj040scYosanTo(String prj040scYosanTo) {
        prj040scYosanTo__ = prj040scYosanTo;
    }

    /**
     * <p>prj040svScYosanFr を取得します。
     * @return prj040svScYosanFr
     */
    public String getPrj040svScYosanFr() {
        return prj040svScYosanFr__;
    }

    /**
     * <p>prj040svScYosanFr をセットします。
     * @param prj040svScYosanFr prj040svScYosanFr
     */
    public void setPrj040svScYosanFr(String prj040svScYosanFr) {
        prj040svScYosanFr__ = prj040svScYosanFr;
    }

    /**
     * <p>prj040svScYosanTo を取得します。
     * @return prj040svScYosanTo
     */
    public String getPrj040svScYosanTo() {
        return prj040svScYosanTo__;
    }

    /**
     * <p>prj040svScYosanTo をセットします。
     * @param prj040svScYosanTo prj040svScYosanTo
     */
    public void setPrj040svScYosanTo(String prj040svScYosanTo) {
        prj040svScYosanTo__ = prj040svScYosanTo;
    }

    /**
     * <p>prj040StartDateFr を取得します。
     * @return prj040StartDateFr
     * @see jp.groupsession.v2.prj.prj040.Prj040Form#prj040StartDateFr__
     */
    public String getPrj040StartDateFr() {
        return prj040StartDateFr__;
    }

    /**
     * <p>prj040StartDateFr をセットします。
     * @param prj040StartDateFr prj040StartDateFr
     * @see jp.groupsession.v2.prj.prj040.Prj040Form#prj040StartDateFr__
     */
    public void setPrj040StartDateFr(String prj040StartDateFr) {
        prj040StartDateFr__ = prj040StartDateFr;
    }

    /**
     * <p>prj040StartDateTo を取得します。
     * @return prj040StartDateTo
     * @see jp.groupsession.v2.prj.prj040.Prj040Form#prj040StartDateTo__
     */
    public String getPrj040StartDateTo() {
        return prj040StartDateTo__;
    }

    /**
     * <p>prj040StartDateTo をセットします。
     * @param prj040StartDateTo prj040StartDateTo
     * @see jp.groupsession.v2.prj.prj040.Prj040Form#prj040StartDateTo__
     */
    public void setPrj040StartDateTo(String prj040StartDateTo) {
        prj040StartDateTo__ = prj040StartDateTo;
    }

    /**
     * <p>prj040EndDateFr を取得します。
     * @return prj040EndDateFr
     * @see jp.groupsession.v2.prj.prj040.Prj040Form#prj040EndDateFr__
     */
    public String getPrj040EndDateFr() {
        return prj040EndDateFr__;
    }

    /**
     * <p>prj040EndDateFr をセットします。
     * @param prj040EndDateFr prj040EndDateFr
     * @see jp.groupsession.v2.prj.prj040.Prj040Form#prj040EndDateFr__
     */
    public void setPrj040EndDateFr(String prj040EndDateFr) {
        prj040EndDateFr__ = prj040EndDateFr;
    }

    /**
     * <p>prj040EndDateTo を取得します。
     * @return prj040EndDateTo
     * @see jp.groupsession.v2.prj.prj040.Prj040Form#prj040EndDateTo__
     */
    public String getPrj040EndDateTo() {
        return prj040EndDateTo__;
    }

    /**
     * <p>prj040EndDateTo をセットします。
     * @param prj040EndDateTo prj040EndDateTo
     * @see jp.groupsession.v2.prj.prj040.Prj040Form#prj040EndDateTo__
     */
    public void setPrj040EndDateTo(String prj040EndDateTo) {
        prj040EndDateTo__ = prj040EndDateTo;
    }
}
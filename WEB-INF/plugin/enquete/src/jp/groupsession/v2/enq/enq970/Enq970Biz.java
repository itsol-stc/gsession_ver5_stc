package jp.groupsession.v2.enq.enq970;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqMenuListDao;
import jp.groupsession.v2.enq.dao.EnqTypeDao;
import jp.groupsession.v2.enq.enq010.Enq010Const;
import jp.groupsession.v2.enq.enq010.Enq010Dao;
import jp.groupsession.v2.enq.enq010.Enq010SearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 管理者設定 発信アンケート管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq970Biz {

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException 実行例外
     * @throws Exception 実行例外
     */
    public void setInitData(Enq970ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con)
    throws SQLException, Exception {

        if (paramMdl.getEnq970initFlg() != 1) {
            paramMdl.setEnq970type(-1);
            paramMdl.setEnq970keyword(null);
            paramMdl.setEnq970keywordType(0);
            paramMdl.setEnq970sendGroup(-1);
            paramMdl.setEnq970sendUser(-1);
            paramMdl.setEnq970sendInput(0);
            paramMdl.setEnq970sendInputText(null);
            paramMdl.setEnq970sortKey(Enq010Const.SORTKEY_OPEN);
            paramMdl.setEnq970order(Enq010Const.ORDER_DESC);

            UDate now = new UDate();
            paramMdl.setEnq970makeDateKbn(Enq010Const.DATE_NON);
            paramMdl.setEnq970makeDateFromYear(now.getYear());
            paramMdl.setEnq970makeDateFromMonth(now.getMonth());
            paramMdl.setEnq970makeDateFromDay(now.getIntDay());
            paramMdl.setEnq970makeDateToYear(now.getYear());
            paramMdl.setEnq970makeDateToMonth(now.getMonth());
            paramMdl.setEnq970makeDateToDay(now.getIntDay());
            paramMdl.setEnq970pubDateKbn(Enq010Const.DATE_NON);
            paramMdl.setEnq970pubDateFromYear(now.getYear());
            paramMdl.setEnq970pubDateFromMonth(now.getMonth());
            paramMdl.setEnq970pubDateFromDay(now.getIntDay());
            paramMdl.setEnq970pubDateToYear(now.getYear());
            paramMdl.setEnq970pubDateToMonth(now.getMonth());
            paramMdl.setEnq970pubDateToDay(now.getIntDay());
            paramMdl.setEnq970resPubDateKbn(Enq010Const.DATE_NON);
            paramMdl.setEnq970resPubDateFromYear(now.getYear());
            paramMdl.setEnq970resPubDateFromMonth(now.getMonth());
            paramMdl.setEnq970resPubDateFromDay(now.getIntDay());
            paramMdl.setEnq970resPubDateToYear(now.getYear());
            paramMdl.setEnq970resPubDateToMonth(now.getMonth());
            paramMdl.setEnq970resPubDateToDay(now.getIntDay());
            paramMdl.setEnq970ansDateKbn(Enq010Const.DATE_NON);
            paramMdl.setEnq970ansDateFromYear(now.getYear());
            paramMdl.setEnq970ansDateFromMonth(now.getMonth());
            paramMdl.setEnq970ansDateFromDay(now.getIntDay());
            paramMdl.setEnq970ansDateToYear(now.getYear());
            paramMdl.setEnq970ansDateToMonth(now.getMonth());
            paramMdl.setEnq970ansDateToDay(now.getIntDay());

            paramMdl.setEnq970priority(
                    new int[] {GSConstEnquete.JUUYOU_0,
                               GSConstEnquete.JUUYOU_1,
                               GSConstEnquete.JUUYOU_2});

            int[] status = new int[] {Enq010Const.STATUS_NOTPUB,
                                      Enq010Const.STATUS_PUB,
                                      Enq010Const.STATUS_ANSEXIT,
                                      Enq010Const.STATUS_PUBEXIT};
            paramMdl.setEnq970status(status);

            _setSearchParam(paramMdl);

            paramMdl.setEnq970initFlg(1);
        }
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getEnq970makeDateFrom() == null) {
            dateBiz.setDateParam(paramMdl, "enq970makeDateFrom", "enq970makeDateFromYear",
                    "enq970makeDateFromMonth", "enq970makeDateFromDay", null);
        }
        
        if (paramMdl.getEnq970makeDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "enq970makeDateTo", "enq970makeDateToYear",
                    "enq970makeDateToMonth", "enq970makeDateToDay", null);
        }

        if (paramMdl.getEnq970ansDateFrom() == null) {
            dateBiz.setDateParam(paramMdl, "enq970ansDateFrom",
                    "enq970ansDateFromYear", "enq970ansDateFromMonth",
                    "enq970ansDateFromDay", null);
        }
        
        if (paramMdl.getEnq970ansDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "enq970ansDateTo",
                    "enq970ansDateToYear", "enq970ansDateToMonth",
                    "enq970ansDateToDay", null);
        }
        
        if (paramMdl.getEnq970resPubDateFrom() == null) {
            dateBiz.setDateParam(paramMdl, "enq970resPubDateFrom",
                    "enq970resPubDateFromYear", "enq970resPubDateFromMonth",
                    "enq970resPubDateFromDay", null);
        }
        
        if (paramMdl.getEnq970resPubDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "enq970resPubDateTo",
                    "enq970resPubDateToYear", "enq970resPubDateToMonth",
                    "enq970resPubDateToDay", null);
        }

        EnqCommonBiz enqBiz = new EnqCommonBiz();

        //アンケート種類を設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> enqTypeList = new ArrayList<LabelValueBean>();
        enqTypeList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        EnqTypeDao enqTypeDao = new EnqTypeDao(con);
        enqTypeList.addAll(enqTypeDao.getEnqTypeList());
        paramMdl.setEnqTypeList(enqTypeList);

        //発信者 グループを設定
        GroupBiz grpBiz = new GroupBiz();
        paramMdl.setEnqSendGroupList(
                grpBiz.getGroupCombLabelList(con, true, gsMsg));

        //発信者 ユーザを設定
        UserBiz usrBiz = new UserBiz();
        paramMdl.setEnqSendUserList(
                usrBiz.getNormalUserLabelList(con, paramMdl.getEnq970sendGroup(),
                                                            null, true, gsMsg));

        //アンケート情報一覧を取得する
        Enq010SearchModel searchMdl = __createSearchModel(con, paramMdl, reqMdl);

        Enq010Dao dao010 = new Enq010Dao(con);
        int searchCnt = dao010.getEnqueteCount(searchMdl, reqMdl);

        //ページ調整
        int pageMaxCnt = enqBiz.getMaxListCnt(con, reqMdl.getSmodel().getUsrsid());
        searchMdl.setMaxCount(pageMaxCnt);
        int maxPage = searchCnt / pageMaxCnt;
        if ((searchCnt % pageMaxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getEnq970pageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setEnq970pageTop(page);
        paramMdl.setEnq970pageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageList(PageUtil.createPageOptions(searchCnt, pageMaxCnt));
        }

        searchMdl.setPage(paramMdl.getEnq970pageTop());
        paramMdl.setEnq010EnqueteList(dao010.getEnqueteList(searchMdl, reqMdl));

        // 左メニューのテンプレート一覧取得
        EnqMenuListDao menuDao = new EnqMenuListDao(con);
        paramMdl.setEnq010TemplateList(menuDao.selectMenuList(GSConstEnquete.DATA_KBN_TEMPLATE));
    }

    /**
     * <br>[機  能] 入力された検索条件を検索条件保持パラメータへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    protected void _setSearchParam(Enq970ParamModel paramMdl) {
        /** 種類(検索条件保持) */
        paramMdl.setEnq970svType(paramMdl.getEnq970type());
        /** キーワード(検索条件保持) */
        paramMdl.setEnq970svKeyword(paramMdl.getEnq970keyword());
        /** キーワード 種別(検索条件保持) */
        paramMdl.setEnq970svKeywordType(paramMdl.getEnq970keywordType());
        /** 発信者 グループ(検索条件保持) */
        paramMdl.setEnq970svSendGroup(paramMdl.getEnq970sendGroup());
        /** 発信者 ユーザ(検索条件保持) */
        paramMdl.setEnq970svSendUser(paramMdl.getEnq970sendUser());
        /** 発信者 入力(検索条件保持) */
        paramMdl.setEnq970svSendInput(paramMdl.getEnq970sendInput());
        /** 発信者 テキスト(検索条件保持) */
        paramMdl.setEnq970svSendInputText(paramMdl.getEnq970sendInputText());
        /** 作成日 指定なし(検索条件保持) */
        paramMdl.setEnq970svMakeDateKbn(paramMdl.getEnq970makeDateKbn());
        /** 作成日 開始 年(検索条件保持) */
        paramMdl.setEnq970svMakeDateFromYear(paramMdl.getEnq970makeDateFromYear());
        /** 作成日 開始 月(検索条件保持) */
        paramMdl.setEnq970svMakeDateFromMonth(paramMdl.getEnq970makeDateFromMonth());
        /** 作成日 開始 日(検索条件保持) */
        paramMdl.setEnq970svMakeDateFromDay(paramMdl.getEnq970makeDateFromDay());
        /** 作成日 終了 年(検索条件保持) */
        paramMdl.setEnq970svMakeDateToYear(paramMdl.getEnq970makeDateToYear());
        /** 作成日 終了 月(検索条件保持) */
        paramMdl.setEnq970svMakeDateToMonth(paramMdl.getEnq970makeDateToMonth());
        /** 作成日 終了 日(検索条件保持) */
        paramMdl.setEnq970svMakeDateToDay(paramMdl.getEnq970makeDateToDay());
        /** 作成日 開始 年月日(検索条件保持) */
        paramMdl.setEnq970svMakeDateFrom(paramMdl.getEnq970makeDateFrom());
        /** 作成日 終了 年月日(検索条件保持) */
        paramMdl.setEnq970svMakeDateTo(paramMdl.getEnq970makeDateTo());
        /** 公開期間 指定なし(検索条件保持) */
        paramMdl.setEnq970svPubDateKbn(paramMdl.getEnq970pubDateKbn());
        /** 公開期間 開始 年(検索条件保持) */
        paramMdl.setEnq970svPubDateFromYear(paramMdl.getEnq970pubDateFromYear());
        /** 公開期間 開始 月(検索条件保持) */
        paramMdl.setEnq970svPubDateFromMonth(paramMdl.getEnq970pubDateFromMonth());
        /** 公開期間 開始 日(検索条件保持) */
        paramMdl.setEnq970svPubDateFromDay(paramMdl.getEnq970pubDateFromDay());
        /** 公開期間 終了 年(検索条件保持) */
        paramMdl.setEnq970svPubDateToYear(paramMdl.getEnq970pubDateToYear());
        /** 公開期間 終了 月(検索条件保持) */
        paramMdl.setEnq970svPubDateToMonth(paramMdl.getEnq970pubDateToMonth());
        /** 公開期間 終了 日(検索条件保持) */
        paramMdl.setEnq970svPubDateToDay(paramMdl.getEnq970pubDateToDay());

        /** 結果公開期間 指定なし(検索条件保持) */
        paramMdl.setEnq970svResPubDateKbn(paramMdl.getEnq970resPubDateKbn());
        /** 結果公開期間 開始 年(検索条件保持) */
        paramMdl.setEnq970svResPubDateFromYear(paramMdl.getEnq970resPubDateFromYear());
        /** 結果公開期間 開始 月(検索条件保持) */
        paramMdl.setEnq970svResPubDateFromMonth(paramMdl.getEnq970resPubDateFromMonth());
        /** 結果公開期間 開始 日(検索条件保持) */
        paramMdl.setEnq970svResPubDateFromDay(paramMdl.getEnq970resPubDateFromDay());
        /** 結果公開期間 終了 年(検索条件保持) */
        paramMdl.setEnq970svResPubDateToYear(paramMdl.getEnq970resPubDateToYear());
        /** 結果公開期間 終了 月(検索条件保持) */
        paramMdl.setEnq970svResPubDateToMonth(paramMdl.getEnq970resPubDateToMonth());
        /** 結果公開期間 終了 日(検索条件保持) */
        paramMdl.setEnq970svResPubDateToDay(paramMdl.getEnq970resPubDateToDay());
        /** 結果公開期間 開始 年月日(検索条件保持) */
        paramMdl.setEnq970svResPubDateFrom(paramMdl.getEnq970resPubDateFrom());
        /** 結果公開期間 終了 年月日(検索条件保持) */
        paramMdl.setEnq970svResPubDateTo(paramMdl.getEnq970resPubDateTo());

        /** 回答期限 指定なし(検索条件保持) */
        paramMdl.setEnq970svAnsDateKbn(paramMdl.getEnq970ansDateKbn());
        /** 回答期限 開始 年(検索条件保持) */
        paramMdl.setEnq970svAnsDateFromYear(paramMdl.getEnq970ansDateFromYear());
        /** 回答期限 開始 月(検索条件保持) */
        paramMdl.setEnq970svAnsDateFromMonth(paramMdl.getEnq970ansDateFromMonth());
        /** 回答期限 開始 日(検索条件保持) */
        paramMdl.setEnq970svAnsDateFromDay(paramMdl.getEnq970ansDateFromDay());
        /** 回答期限 終了 年(検索条件保持) */
        paramMdl.setEnq970svAnsDateToYear(paramMdl.getEnq970ansDateToYear());
        /** 回答期限 終了 月(検索条件保持) */
        paramMdl.setEnq970svAnsDateToMonth(paramMdl.getEnq970ansDateToMonth());
        /** 回答期限 終了 日(検索条件保持) */
        paramMdl.setEnq970svAnsDateToDay(paramMdl.getEnq970ansDateToDay());
        /** 回答期限 開始 年月日(検索条件保持) */
        paramMdl.setEnq970svAnsDateFrom(paramMdl.getEnq970ansDateFrom());
        /** 回答期限 終了 年月日(検索条件保持) */
        paramMdl.setEnq970svAnsDateTo(paramMdl.getEnq970ansDateTo());
        /** 重要度(検索条件保持) */
        paramMdl.setEnq970svPriority(paramMdl.getEnq970priority());
        /** 状態(検索条件保持) */
        paramMdl.setEnq970svStatus(paramMdl.getEnq970status());
        /** 匿名 匿名(検索条件保持) */
        paramMdl.setEnq970svAnony(paramMdl.getEnq970anony());
    }

    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @return 検索条件Model
     * @throws SQLException SQL実行時例外
     */
    private Enq010SearchModel __createSearchModel(Connection con, Enq970ParamModel paramMdl,
                                                                            RequestModel reqMdl)
    throws SQLException {
        Enq010SearchModel searchMdl = new Enq010SearchModel();
        searchMdl.setPage(paramMdl.getEnq970pageTop());

        searchMdl.setFolder(Enq010Const.FOLDER_SEND);
        searchMdl.setSessionUserSid(reqMdl.getSmodel().getUsrsid());

        //ソートキー
        searchMdl.setSortKey(paramMdl.getEnq970sortKey());
        //並び順
        searchMdl.setOrder(paramMdl.getEnq970order());

        //種類
        searchMdl.setEnqType(paramMdl.getEnq970svType());

        //キーワード
        //キーワード 種別
        String[] keywordList = null;
        if (!StringUtil.isNullZeroString(paramMdl.getEnq970svKeyword())) {
            keywordList = paramMdl.getEnq970svKeyword().split(" ");
        }
        searchMdl.setKeyword(keywordList);
        searchMdl.setKeywordType(paramMdl.getEnq970svKeywordType());

        //キーワード 種別
        searchMdl.setKeywordType(paramMdl.getEnq970svKeywordType());
        //発信者 グループ
        searchMdl.setSenderGroup(paramMdl.getEnq970svSendGroup());
        //発信者 ユーザ
        searchMdl.setSenderUser(paramMdl.getEnq970svSendUser());
        //発信者 入力
        searchMdl.setSenderInput(paramMdl.getEnq970svSendInputText());

        //作成日 開始
        searchMdl.setMakeDateFrom(
                __createSearchDate(paramMdl.getEnq970svMakeDateKbn(),
                        paramMdl.getEnq970svMakeDateFromYear(),
                        paramMdl.getEnq970svMakeDateFromMonth(),
                        paramMdl.getEnq970svMakeDateFromDay()));
        //作成日 終了
        searchMdl.setMakeDateTo(
                __createSearchDate(paramMdl.getEnq970svMakeDateKbn(),
                        paramMdl.getEnq970svMakeDateToYear(),
                        paramMdl.getEnq970svMakeDateToMonth(),
                        paramMdl.getEnq970svMakeDateToDay()));

        //公開期間 開始
        searchMdl.setPubLimitDateFrom(
                __createSearchDate(paramMdl.getEnq970svPubDateKbn(),
                        paramMdl.getEnq970svPubDateFromYear(),
                        paramMdl.getEnq970svPubDateFromMonth(),
                        paramMdl.getEnq970svPubDateFromDay()));

        //公開期間 終了
        searchMdl.setPubLimitDateTo(
                __createSearchDate(paramMdl.getEnq970svPubDateKbn(),
                        paramMdl.getEnq970svPubDateToYear(),
                        paramMdl.getEnq970svPubDateToMonth(),
                        paramMdl.getEnq970svPubDateToDay()));

        //回答期限 開始
        searchMdl.setAnsLimitDateFrom(
                __createSearchDate(paramMdl.getEnq970svAnsDateKbn(),
                        paramMdl.getEnq970svAnsDateFromYear(),
                        paramMdl.getEnq970svAnsDateFromMonth(),
                        paramMdl.getEnq970svAnsDateFromDay()));
        //回答期限 終了
        searchMdl.setAnsLimitDateTo(
                __createSearchDate(paramMdl.getEnq970svAnsDateKbn(),
                        paramMdl.getEnq970svAnsDateToYear(),
                        paramMdl.getEnq970svAnsDateToMonth(),
                        paramMdl.getEnq970svAnsDateToDay()));

        //結果公開期間 開始
        searchMdl.setPubLimitDateFrom(
                __createSearchDate(paramMdl.getEnq970svPubDateKbn(),
                        paramMdl.getEnq970svPubDateFromYear(),
                        paramMdl.getEnq970svPubDateFromMonth(),
                        paramMdl.getEnq970svPubDateFromDay()));

        //結果公開期間 終了
        searchMdl.setResPubLimitDateTo(
                __createSearchDate(paramMdl.getEnq970svResPubDateKbn(),
                        paramMdl.getEnq970svResPubDateToYear(),
                        paramMdl.getEnq970svResPubDateToMonth(),
                        paramMdl.getEnq970svResPubDateToDay()));


        //重要度
        searchMdl.setPriority(paramMdl.getEnq970svPriority());
        //状態
        searchMdl.setStatus(paramMdl.getEnq970svStatus());
        //匿名
        searchMdl.setAnony(0);

        //管理者フラグ
        searchMdl.setEnqAdminFlg(true);

        return searchMdl;
    }

    /**
     * <br>[機  能] 検索条件の日付をUDateへ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 指定なし
     * @param year 年
     * @param month 月
     * @param day 日
     * @return UDate
     */
    private UDate __createSearchDate(int kbn, int year, int month, int day) {
        if (kbn != Enq010Const.DATE_USE) {
            return null;
        }

        UDate date = new UDate();
        date.setDate(year, month, day);
        date.setZeroHhMmSs();
        return date;
    }
}

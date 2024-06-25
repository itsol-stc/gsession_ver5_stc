package jp.groupsession.v2.enq.enq330;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqQueMainDao;
import jp.groupsession.v2.enq.enq210.Enq210Biz;
import jp.groupsession.v2.enq.enq310.Enq310Biz;
import jp.groupsession.v2.enq.enq310.Enq310Dao;
import jp.groupsession.v2.enq.enq310.Enq310QuestionSubModel;
import jp.groupsession.v2.enq.enq320.Enq320Biz;
import jp.groupsession.v2.enq.model.EnqQueMainModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アンケート 結果確認（詳細）画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq330Biz {
    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq330ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con)
    throws SQLException {

        //グループコンボ、ユーザコンボを設定
        Enq320Biz biz320 = new Enq320Biz();
        paramMdl.setGroupCombo(biz320.getGroupCombo(con, reqMdl));
        paramMdl.setUserCombo(biz320.getUserCombo(con, reqMdl, paramMdl.getEnq330group()));

        // 年、月、日コンボの作成
        EnqCommonBiz enqBiz = new EnqCommonBiz();

        //アンケート情報を設定する
        Enq310Biz biz310 = new Enq310Biz();
        biz310.setEnqueteData(reqMdl, con, paramMdl);

        //設問情報を設定
        long emnSid = paramMdl.getAnsEnqSid();
        int eqmSeq = paramMdl.getEnq310selectQue();
        EnqQueMainDao queMainDao = new EnqQueMainDao(con);
        EnqQueMainModel queMainModel
            = queMainDao.select(emnSid, eqmSeq);
        //設問番号
        paramMdl.setEnq330queNo(queMainModel.getEqmQueSec());
        //必須
        paramMdl.setEnq330queRequire(queMainModel.getEqmRequire());
        //設問種別
        int queKbn = queMainModel.getEqmQueKbn();
        paramMdl.setEnq330queKbn(queKbn);
        //設問種別名称
        paramMdl.setEnq330queKbnName(
                Enq210Biz.getDspQueType(reqMdl, paramMdl.getEnq330queKbn()));
        //設問
        paramMdl.setEnq330question(queMainModel.getEqmQuestion());

        Enq310Dao dao310 = new Enq310Dao(con);
        int[] ansCount = dao310.getAnswerCount(emnSid, eqmSeq);
        //対象人数
//        int ansAllCnt = ansCount[0];
        //対象人数 回答人数
        paramMdl.setEnq330answerCountAr(
                EnqCommonBiz.toCommaFormat(ansCount[1]));
        //対象人数 未回答人数
        paramMdl.setEnq330answerCountUn(
                EnqCommonBiz.toCommaFormat(ansCount[2]));
//        /** 検索結果件数 */
//        private int enq330searchCount__ = 0;

        //設問 選択肢
        if (queKbn == GSConstEnquete.SYURUI_SINGLE
        || queKbn == GSConstEnquete.SYURUI_MULTIPLE) {
            List<Enq310QuestionSubModel> queSubList
                = dao310.getAnswerSubList(reqMdl, emnSid, eqmSeq, queKbn);
            paramMdl.setQueSubList(queSubList);
        } else if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
            //数値入力
            Enq330Dao dao330 = new Enq330Dao(con);
            long[] numCount = dao330.getNumAnsCount(emnSid, eqmSeq);
            //回答結果 最小値
            paramMdl.setEnq330answerMinValue(
                    EnqCommonBiz.toCommaFormat(numCount[0]));
            //回答結果 最大値
            paramMdl.setEnq330answerMaxValue(
                    EnqCommonBiz.toCommaFormat(numCount[1]));
            //回答結果 平均値
            paramMdl.setEnq330answerAvgValue(
                    EnqCommonBiz.toCommaFormat(numCount[2]));

        } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
            //日付入力
            Enq330Dao dao330 = new Enq330Dao(con);
            String[] dateCount = dao330.getDateAnsCount(reqMdl, emnSid, eqmSeq);
            //回答結果 最小値
            paramMdl.setEnq330answerMinValue(dateCount[0]);
            //回答結果 最大値
            paramMdl.setEnq330answerMaxValue(dateCount[1]);
        }

        //回答情報
        Enq330SearchModel searchMdl = new Enq330SearchModel();
        searchMdl.setEmnSid(emnSid);
        searchMdl.setEqmSeq(eqmSeq);
        searchMdl.setQueKbn(queKbn);
        searchMdl.setQueKbn(paramMdl.getEnq330queKbn());

        if (paramMdl.getEnq310anony() == GSConstEnquete.EMN_ANONNY_NON) {
            searchMdl.setGroup(paramMdl.getEnq330svGroup());
            searchMdl.setUser(paramMdl.getEnq330svUser());
        }

        searchMdl.setAns(paramMdl.getEnq330svAns());
        searchMdl.setAnsText(paramMdl.getEnq330svAnsText());
        searchMdl.setAnsNumKbn(paramMdl.getEnq330svAnsNumKbn());
        if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
            searchMdl.setAnsNumFrom(paramMdl.getEnq330svAnsNumFrom());
            searchMdl.setAnsNumTo(paramMdl.getEnq330svAnsNumTo());
        }
        if (queKbn == GSConstEnquete.SYURUI_DAY) {
            if (paramMdl.getEnq330svAnsDateFromYear() > 0) {
                UDate ansDateFrom = new UDate();
                ansDateFrom.setDate(paramMdl.getEnq330svAnsDateFromYear(),
                                            paramMdl.getEnq330svAnsDateFromMonth(),
                                            paramMdl.getEnq330svAnsDateFromDay());
                ansDateFrom.setZeroHhMmSs();
                searchMdl.setAnsDateFrom(ansDateFrom);
            }
            if (searchMdl.getAnsNumKbn() == Enq330Const.ANS_NUM_KBN_RANGE
            && paramMdl.getEnq330svAnsDateToYear() > 0) {
                UDate ansDateTo = new UDate();
                ansDateTo.setDate(paramMdl.getEnq330svAnsDateToYear(),
                                            paramMdl.getEnq330svAnsDateToMonth(),
                                            paramMdl.getEnq330svAnsDateToDay());
                ansDateTo.setZeroHhMmSs();
                searchMdl.setAnsDateTo(ansDateTo);
            }
        }

        Enq330Dao dao330 = new Enq330Dao(con);
        int searchCnt = dao330.getAnswerCount(searchMdl, reqMdl);
        paramMdl.setEnq330searchCount(searchCnt);
        //ページ調整
        int pageMaxCnt = enqBiz.getMaxListCnt(con, reqMdl.getSmodel().getUsrsid());
        searchMdl.setMaxCount(pageMaxCnt);
        int maxPage = searchCnt / pageMaxCnt;
        if ((searchCnt % pageMaxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getEnq330pageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setEnq330pageTop(page);
        paramMdl.setEnq330pageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageList(PageUtil.createPageOptions(searchCnt, pageMaxCnt));
        }

        if (paramMdl.getEnq310anony() == GSConstEnquete.EMN_ANONNY_ANONNY) {
            searchMdl.setSortKey(Enq330Const.SORTKEY_ANSDATE);
        } else {
            searchMdl.setSortKey(paramMdl.getEnq330sortKey());
        }
        searchMdl.setOrder(paramMdl.getEnq330order());
        searchMdl.setPage(paramMdl.getEnq330pageTop());

        List<Enq330AnswerModel> ansList = dao330.getAnswerList(searchMdl, reqMdl);
        paramMdl.setAnsList(ansList);
    }
}

package jp.groupsession.v2.enq.enq320;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq310.Enq310Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アンケート 結果確認（一覧）画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq320Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq320Biz.class);

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq320ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con)
    throws SQLException {

        //アンケート情報を設定する
        Enq310Biz biz310 = new Enq310Biz();
        biz310.setEnqueteData(reqMdl, con, paramMdl);

        //グループコンボ、ユーザコンボを設定
        paramMdl.setGroupCombo(getGroupCombo(con, reqMdl));
        paramMdl.setUserCombo(getUserCombo(con, reqMdl, paramMdl.getEnq320group()));

        //回答情報
        long emnSid = paramMdl.getAnsEnqSid();
        Enq320SearchModel searchMdl = new Enq320SearchModel();
        searchMdl.setEmnSid(emnSid);
        if (paramMdl.getEnq310anony() == GSConstEnquete.EMN_ANONNY_NON) {
            searchMdl.setGroup(paramMdl.getEnq320svGroup());
            searchMdl.setUser(paramMdl.getEnq320svUser());
        }
        searchMdl.setStsAns(paramMdl.getEnq320svStsAns());
        searchMdl.setStsNon(paramMdl.getEnq320svStsNon());
        searchMdl.setOrder(paramMdl.getEnq320order());
        if (paramMdl.getEnq310anony() == GSConstEnquete.ANONYMUS_ON) {
            searchMdl.setSortKey(Enq320Const.SORTKEY_ANSDATE);
        } else {
            searchMdl.setSortKey(paramMdl.getEnq320sortKey());
        }

        // 対象者情報件数を取得
        Enq320Dao dao320 = new Enq320Dao(con);
        int searchCnt = dao320.getAnswerCount(searchMdl, reqMdl);

        //ページ調整
        EnqCommonBiz enqBiz = new EnqCommonBiz();
        int pageMaxCnt = enqBiz.getMaxListCnt(con, reqMdl.getSmodel().getUsrsid());

        searchMdl.setMaxCount(pageMaxCnt);
        int maxPage = searchCnt / pageMaxCnt;
        if ((searchCnt % pageMaxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getEnq320pageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setEnq320pageTop(page);
        paramMdl.setEnq320pageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageList(PageUtil.createPageOptions(searchCnt, pageMaxCnt));
        }
        searchMdl.setPage(paramMdl.getEnq320pageTop());

        List<Enq320AnswerModel> ansList = dao320.getAnswerList(searchMdl, reqMdl);
        paramMdl.setAnsList(ansList);
    }

    /**
     * <br>[機  能] グループコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return グループコンボ
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getGroupCombo(Connection con, RequestModel reqMdl)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        GroupBiz grpBiz = new GroupBiz();
        List<LabelValueBean> grpCombo
            = grpBiz.getGroupCombLabelList(con, false, gsMsg);
        grpCombo.add(0, new LabelValueBean(gsMsg.getMessage("cmn.all"), "-1"));
        return grpCombo;
    }

    /**
     * <br>[機  能] ユーザコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param grpSid グループSID
     * @return ユーザコンボ
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getUserCombo(Connection con, RequestModel reqMdl, int grpSid)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        UserBiz userBiz = new UserBiz();
        List<UsrLabelValueBean> userCombo
            = userBiz.getNormalUserLabelList(con, grpSid, null, false, gsMsg);
        userCombo.add(0, new UsrLabelValueBean(gsMsg.getMessage("cmn.all"), "-1"));
        return userCombo;
    }
}

package jp.groupsession.v2.enq.enq230;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqTypeDao;
import jp.groupsession.v2.enq.enq010.Enq010Const;
import jp.groupsession.v2.enq.enq010.Enq010Dao;
import jp.groupsession.v2.enq.enq010.Enq010SearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] テンプレート一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq230Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq230Biz.class);

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
    public void setInitData(Enq230ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con) throws SQLException, Exception {

        log__.debug("初期表示情報取得処理");

        if (paramMdl.getEnq230initFlg() != 1) {
            paramMdl.setEnq230type(-1);
            paramMdl.setEnq230keyword(null);
            paramMdl.setEnq230keywordType(0);
            paramMdl.setEnq230priority(
                    new int[] {GSConstEnquete.JUUYOU_0,
                                    GSConstEnquete.JUUYOU_1,
                                    GSConstEnquete.JUUYOU_2});
            paramMdl.setEnq230initFlg(1);
            _setSearchParam(paramMdl);
        }

        // セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 一覧表示最大件数取得
        EnqCommonBiz enqBiz = new EnqCommonBiz();
        int listCnt = enqBiz.getMaxListCnt(con, sessionUsrSid);

        // アンケート種類を設定
        paramMdl.setEnq230TypeList(__getEnqTypeList(con, reqMdl));

        //アンケート情報一覧を取得する
        Enq010SearchModel searchMdl = __createSearchModel(paramMdl, sessionUsrSid, listCnt);

        Enq010Dao dao010 = new Enq010Dao(con);
        int searchCnt = dao010.getEnqueteCount(searchMdl, reqMdl);

        //ページ調整
        int maxPage = searchCnt / listCnt;
        if ((searchCnt % listCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getEnq230pageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setEnq230pageTop(page);
        paramMdl.setEnq230pageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setEnq230pageList(PageUtil.createPageOptions(searchCnt, listCnt));
        }

        searchMdl.setPage(paramMdl.getEnq230pageTop());
        paramMdl.setEnq230EnqueteList(dao010.getEnqueteList(searchMdl, reqMdl));
    }

    /**
     * <br>[機  能] テンプレートを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return 削除したアンケート名
     * @throws SQLException SQL実行例外
     */
    public String doDelete(Enq230ParamModel paramMdl, RequestModel reqMdl, Connection con)
            throws SQLException {

        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        boolean commitFlg = false;
        String delEnqName = "";

        // セッションユーザ情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        try {
            con.setAutoCommit(false);

            for (String selectEnqSid : paramMdl.getEnq230selectEnqSid()) {
                if (delEnqName.length() > 0) {
                    delEnqName += ",";
                }
                delEnqName += enqBiz.deleteEnquete(
                        Long.parseLong(selectEnqSid), sessionUsrSid, con);
            }
            con.commit();
            commitFlg = true;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        return delEnqName;
    }

    /**
     * <br>[機  能] 入力された検索条件を検索条件保持パラメータへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    protected void _setSearchParam(Enq230ParamModel paramMdl) {

        /** 種類(検索条件保持) */
        paramMdl.setEnq230svType(paramMdl.getEnq230type());
        /** キーワード(検索条件保持) */
        paramMdl.setEnq230svKeyword(paramMdl.getEnq230keyword());
        /** キーワード 種別(検索条件保持) */
        paramMdl.setEnq230svKeywordType(paramMdl.getEnq230keywordType());
        /** 重要度(検索条件保持) */
        paramMdl.setEnq230svPriority(paramMdl.getEnq230priority());
        /** 匿名 匿名(検索条件保持) */
        paramMdl.setEnq230svAnony(paramMdl.getEnq230anony());
    }

    /**
     * <br>[機  能] アンケート種類取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return 最大表示件数
     * @throws SQLException SQL実行例外
     */
    private List<LabelValueBean> __getEnqTypeList(Connection con, RequestModel reqMdl)
            throws SQLException {

        log__.debug("アンケート種類取得処理");
        List<LabelValueBean> enqTypeList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);

        enqTypeList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz") , "-1"));
        EnqTypeDao enqTypeDao = new EnqTypeDao(con);
        enqTypeList.addAll(enqTypeDao.getEnqTypeList());

        return enqTypeList;
    }

    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param listCnt 一覧表示最大件数
     * @return 検索条件Model
     */
    private Enq010SearchModel __createSearchModel(Enq230ParamModel paramMdl,
                                                  int usrSid,
                                                  int listCnt) {

        Enq010SearchModel searchMdl = new Enq010SearchModel();

        searchMdl.setPage(paramMdl.getEnq230pageTop());
        searchMdl.setMaxCount(listCnt);
        searchMdl.setSessionUserSid(usrSid);
        searchMdl.setFolder(Enq010Const.FOLDER_TEMPLATE);

        // 種類
        searchMdl.setEnqType(paramMdl.getEnq230svType());

        //キーワード
        //キーワード 種別
        String[] keywordList = null;
        if (!StringUtil.isNullZeroString(paramMdl.getEnq230svKeyword())) {
            keywordList = paramMdl.getEnq230svKeyword().split(" ");
        }
        searchMdl.setKeyword(keywordList);
        searchMdl.setKeywordType(paramMdl.getEnq230svKeywordType());

        // 重要度
        searchMdl.setPriority(paramMdl.getEnq230svPriority());
        // 状態
        searchMdl.setStatus(paramMdl.getEnq230svStatus());
        // 匿名
        searchMdl.setAnony(paramMdl.getEnq230svAnony());

        return searchMdl;
    }
}

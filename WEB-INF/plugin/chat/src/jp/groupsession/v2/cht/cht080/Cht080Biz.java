package jp.groupsession.v2.cht.cht080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cht.dao.ChtSpaccessDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessPermitDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessTargetDao;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] チャット 特例アクセス一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht080Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht080Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Cht080ParamModel paramMdl,
                            RequestModel reqMdl) throws SQLException {

        if (paramMdl.getCht080searchFlg() != 1) {
            //検索条件Model生成
            Cht080SearchModel searchMdl = __createSearchModel(paramMdl, reqMdl);

            //検索結果件数を取得する
            int maxCount = getRecordCount(con, paramMdl, reqMdl);

            //件数カウント
            int limit = searchMdl.getLimit();
            int maxPage = 1;
            if (maxCount > 0) {
                maxPage = PageUtil.getPageCount(maxCount, limit);
            }
            log__.debug("表示件数 = " + maxCount);

            //現在ページ（ページコンボのvalueを設定）
            int nowPage = paramMdl.getCht080pageTop();
            if (nowPage == 0) {
                nowPage = 1;
            } else if (nowPage > maxPage) {
                nowPage = maxPage;
            }
            //結果取得開始カーソル位置
            int start = PageUtil.getRowNumber(nowPage, limit);
            searchMdl.setStart(start);

            //ページング
            paramMdl.setCht080pageTop(nowPage);
            paramMdl.setCht080pageBottom(nowPage);
            if (maxPage > 1) {
                paramMdl.setPageCombo(PageUtil.createPageOptions(maxCount, limit));
            }

            Cht080Dao dao = new Cht080Dao(con);
            List<Cht080SpAccessModel> spAccessList = dao.getAccessList(searchMdl, reqMdl);
            paramMdl.setSpAccessList(spAccessList);
        }
    }


    /**
     * <br>[機  能] 検索結果件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @return 検索結果件数
     * @throws SQLException SQL実行時例外
     */
    public int getRecordCount(Connection con, Cht080ParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {
        Cht080Dao dao = new Cht080Dao(con);
        return dao.recordCount(__createSearchModel(paramMdl, reqMdl));
    }

    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @return 検索条件Model
     */
    private Cht080SearchModel __createSearchModel(Cht080ParamModel paramMdl, RequestModel reqMdl) {
        Cht080SearchModel searchMdl = new Cht080SearchModel();
        searchMdl.setKeyword(paramMdl.getCht080svKeyword());
        searchMdl.setSortKey(paramMdl.getCht080sortKey());
        searchMdl.setOrder(paramMdl.getCht080order());
        searchMdl.setLimit(Cht080Const.LIMIT_DSP_SPACCESS);

        return searchMdl;
    }

    /**
     * <br>[機  能] 特例アクセスの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteAccess(Connection con, Cht080ParamModel paramMdl, int userSid)
    throws SQLException {

        ChtSpaccessDao accessDao = new ChtSpaccessDao(con);
        ChtSpaccessTargetDao accessTargetDao = new ChtSpaccessTargetDao(con);
        ChtSpaccessPermitDao accessPermitDao = new ChtSpaccessPermitDao(con);
        for (String chsSid : paramMdl.getCht080selectSpAccessList()) {
            accessDao.delete(Integer.parseInt(chsSid));
            accessTargetDao.delete(Integer.parseInt(chsSid));
            accessPermitDao.delete(Integer.parseInt(chsSid));
        }
    }

    /**
     * <br>[機  能] 特例アクセス名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 特例アクセスSID
     * @return 特例アクセス名
     * @throws SQLException SQL実行時例外
     */
    public String getAccessName(Connection con, String[] sidList)
    throws SQLException {

        Cht080Dao cht080Dao = new Cht080Dao(con);
        List<String> titleList = cht080Dao.getAccessNameList(sidList);

        String msgTitle = "";
        for (int idx = 0; idx < titleList.size(); idx++) {

            //最初の要素以外は改行を挿入
            if (idx > 0) {
                msgTitle += "<br>";
            }

            msgTitle += "・ " + StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(titleList.get(idx), ""));
        }

        return msgTitle;
    }

}
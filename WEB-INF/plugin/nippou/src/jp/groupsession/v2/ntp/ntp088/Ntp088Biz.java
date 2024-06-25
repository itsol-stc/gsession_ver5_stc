package jp.groupsession.v2.ntp.ntp088;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.dao.NtpSpaccessDao;
import jp.groupsession.v2.ntp.dao.NtpSpaccessPermitDao;
import jp.groupsession.v2.ntp.dao.NtpSpaccessTargetDao;
import jp.groupsession.v2.ntp.model.NtpSpaccessModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 特例アクセス一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp088Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp088Biz.class);
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
    public void setInitData(Connection con, Ntp088ParamModel paramMdl,
                            RequestModel reqMdl) throws SQLException {

        if (paramMdl.getNtp088searchFlg() != 1) {
            //検索条件Model生成
            Ntp088SearchModel searchMdl = __createSearchModel(paramMdl, reqMdl);

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
            int nowPage = paramMdl.getNtp088pageTop();
            if (nowPage == 0) {
                nowPage = 1;
            } else if (nowPage > maxPage) {
                nowPage = maxPage;
            }
            //結果取得開始カーソル位置
            int start = PageUtil.getRowNumber(nowPage, limit);
            searchMdl.setStart(start);

            //ページング
            paramMdl.setNtp088pageTop(nowPage);
            paramMdl.setNtp088pageBottom(nowPage);
            if (maxPage > 1) {
                paramMdl.setPageCombo(PageUtil.createPageOptions(maxCount, limit));
            }

            Ntp088Dao dao = new Ntp088Dao(con);
            List<Ntp088SpAccessModel> spAccessList = dao.getAccessList(searchMdl, reqMdl);
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
    public int getRecordCount(Connection con, Ntp088ParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {
        Ntp088Dao dao = new Ntp088Dao(con);
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
    private Ntp088SearchModel __createSearchModel(Ntp088ParamModel paramMdl, RequestModel reqMdl) {
        Ntp088SearchModel searchMdl = new Ntp088SearchModel();
        searchMdl.setKeyword(paramMdl.getNtp088svKeyword());
        searchMdl.setSortKey(paramMdl.getNtp088sortKey());
        searchMdl.setOrder(paramMdl.getNtp088order());
        searchMdl.setLimit(Ntp088Const.LIMIT_DSP_SPACCESS);

        return searchMdl;
    }

    /**
     * <br>[機  能] 指定したスケジュール 特例アクセスが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param nsaSid スケジュール特例アクセスSID
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existNtpSpAccess(Connection con, int nsaSid) throws SQLException {
        NtpSpaccessDao spAccessDao = new NtpSpaccessDao(con);
        NtpSpaccessModel mode = spAccessDao.select(nsaSid);
        return mode.getNsaSid() == nsaSid;
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
    public void deleteAccess(Connection con, Ntp088ParamModel paramMdl, int userSid)
    throws SQLException {

        NtpSpaccessDao accessDao = new NtpSpaccessDao(con);
        NtpSpaccessTargetDao accessTargetDao = new NtpSpaccessTargetDao(con);
        NtpSpaccessPermitDao accessPermitDao = new NtpSpaccessPermitDao(con);
        for (String nsaSid : paramMdl.getNtp088selectSpAccessList()) {
            accessDao.delete(Integer.parseInt(nsaSid));
            accessTargetDao.delete(Integer.parseInt(nsaSid));
            accessPermitDao.delete(Integer.parseInt(nsaSid));
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

        Ntp088Dao ntp088Dao = new Ntp088Dao(con);
        List<String> titleList = ntp088Dao.getAccessNameList(sidList);

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
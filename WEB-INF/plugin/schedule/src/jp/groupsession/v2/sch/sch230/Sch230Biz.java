package jp.groupsession.v2.sch.sch230;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.dao.SchSpaccessDao;
import jp.groupsession.v2.sch.dao.SchSpaccessPermitDao;
import jp.groupsession.v2.sch.dao.SchSpaccessTargetDao;
import jp.groupsession.v2.sch.model.SchSpaccessModel;

/**
 * <br>[機  能] スケジュール 特例アクセス管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch230Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch230Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Sch230ParamModel paramMdl,
                            RequestModel reqMdl) throws SQLException {

        if (paramMdl.getSch230searchFlg() != 1) {
            //検索条件Model生成
            Sch230SearchModel searchMdl = __createSearchModel(paramMdl, reqMdl);

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
            int nowPage = paramMdl.getSch230pageTop();
            if (nowPage == 0) {
                nowPage = 1;
            } else if (nowPage > maxPage) {
                nowPage = maxPage;
            }
            //結果取得開始カーソル位置
            int start = PageUtil.getRowNumber(nowPage, limit);
            searchMdl.setStart(start);

            //ページング
            paramMdl.setSch230pageTop(nowPage);
            paramMdl.setSch230pageBottom(nowPage);
            if (maxPage > 1) {
                paramMdl.setPageCombo(PageUtil.createPageOptions(maxCount, limit));
            }

            Sch230Dao dao = new Sch230Dao(con);
            List<Sch230SpAccessModel> spAccessList = dao.getAccessList(searchMdl, reqMdl);
            paramMdl.setSpAccessList(spAccessList);
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

        Sch230Dao wml270Dao = new Sch230Dao(con);
        List<String> titleList = wml270Dao.getAccessNameList(sidList);

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
    public int getRecordCount(Connection con, Sch230ParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {
        Sch230Dao dao = new Sch230Dao(con);
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
    private Sch230SearchModel __createSearchModel(Sch230ParamModel paramMdl, RequestModel reqMdl) {
        Sch230SearchModel searchMdl = new Sch230SearchModel();
        searchMdl.setKeyword(paramMdl.getSch230svKeyword());
        searchMdl.setSortKey(paramMdl.getSch230sortKey());
        searchMdl.setOrder(paramMdl.getSch230order());
        searchMdl.setLimit(Sch230Const.LIMIT_DSP_SPACCESS);

        return searchMdl;
    }

    /**
     * <br>[機  能] 指定したスケジュール 特例アクセスが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param ssaSid スケジュール特例アクセスSID
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existSchSpAccess(Connection con, int ssaSid) throws SQLException {
        SchSpaccessDao spAccessDao = new SchSpaccessDao(con);
        return spAccessDao.select(ssaSid) != null;
    }

    /**
     * <br>[機  能] 特例アクセスの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @return 削除対象の特例アクセス設定モデルリスト
     * @throws SQLException SQL実行時例外
     */
    public List<SchSpaccessModel> deleteAccess(
            Connection con, Sch230ParamModel paramMdl, int userSid)
    throws SQLException {

        SchSpaccessDao accessDao = new SchSpaccessDao(con);
        List<Integer> delSpSid = new ArrayList<Integer>();
        for (String ssaSid : paramMdl.getSch230selectSpAccessList()) {
            delSpSid.add(Integer.parseInt(ssaSid));
        }
        List<SchSpaccessModel> delList = accessDao.select(delSpSid);

        SchSpaccessTargetDao accessTargetDao = new SchSpaccessTargetDao(con);
        SchSpaccessPermitDao accessPermitDao = new SchSpaccessPermitDao(con);
        for (String ssaSid : paramMdl.getSch230selectSpAccessList()) {
            accessDao.delete(Integer.parseInt(ssaSid));
            accessTargetDao.delete(Integer.parseInt(ssaSid));
            accessPermitDao.delete(Integer.parseInt(ssaSid));
        }
        return delList;
    }
}

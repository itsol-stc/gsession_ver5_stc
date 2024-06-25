package jp.groupsession.v2.anp.anp170;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 状況内容確認 結果状況ポップアップ画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp170Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp170Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param anp170Model パラメータモデル
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @return true:データあり  false:データなし
     * @throws SQLException SQL実行エラー
     */
    public boolean setInitData(
            Anp170ParamModel anp170Model,
            Connection con,
            int sessionUsrSid) throws SQLException {


        //個人設定情報を取得
        log__.debug("個人設定情報を取得 usrSid = " + sessionUsrSid);
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        AnpPriConfModel priConf = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        //配信データの存在チェック
        Anp170Dao dao = new Anp170Dao(con);
        int hDataCount = dao.getHDataCount(anp170Model.getAnp170AnpSid());
        if (hDataCount == 0) {
            log__.debug("配信履歴データなし");
            return false;
        }

        //結果状況一覧のページ内容をセット
        int limit = priConf.getAppListCount(); //表示行数

        //指定したグループの全件数
        int maxCount =
                dao.getJListCount(anp170Model.getAnp170AnpSid(), anp170Model.getAnp170GrpSid());

        int nowPage = anp170Model.getAnp170NowPage();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPage = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPage, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPage;
            start = maxPageStartRow;
        }

        anp170Model.setAnp170NowPage(nowPage);
        anp170Model.setAnp170DspPage1(nowPage);
        anp170Model.setAnp170DspPage2(nowPage);
        anp170Model.setAnp170PageLabel(PageUtil.createPageOptions(maxCount, limit));

        //グループ情報を取得
        CmnGroupmDao grpmDao = new CmnGroupmDao(con);
        CmnGroupmModel grpmMdl =  grpmDao.select(anp170Model.getAnp170GrpSid());
        anp170Model.setAnp170GrpName(grpmMdl.getGrpName());

        //結果状況リストを取得
        List<Anp170DspModel> jList = dao.getJyokyoList(
                anp170Model.getAnp170AnpSid(),
                anp170Model.getAnp170GrpSid(),
                anp170Model.getAnp170SortKeyIndex(),
                anp170Model.getAnp170OrderKey(),
                start,
                limit);
        anp170Model.setAnp170JyokyoList(jList);

        return true;
    }
}
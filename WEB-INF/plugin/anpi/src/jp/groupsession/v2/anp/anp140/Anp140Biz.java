package jp.groupsession.v2.anp.anp140;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp140.dao.Anp140Dao;
import jp.groupsession.v2.anp.anp140.model.Anp140HaisinDetaModel;
import jp.groupsession.v2.anp.anp140.model.Anp140JyokyoListModel;
import jp.groupsession.v2.anp.dao.AnpDatausedSumDao;
import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.dao.AnpJdataDao;
import jp.groupsession.v2.anp.dao.AnpiCommonDao;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.anp.model.AnpStateModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 管理者設定・配信履歴 状況内容確認画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp140Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp140Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp140Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @return true:データあり  false:データなし
     * @throws Exception 実行例外
     */
    public boolean setInitData(Anp140ParamModel anp140Model,
            RequestModel reqMdl,
                            Connection con)
                            throws Exception {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //個人設定情報を取得
        log__.debug("個人設定情報を取得 usrSid = " + sessionUsrSid);
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        AnpPriConfModel priConf = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        //配信データの存在チェック
        Anp140Dao dao = new Anp140Dao(con);
        int hDataCount = dao.getHDataCount(anp140Model.getAnp130SelectAphSid());
        if (hDataCount == 0) {
            log__.debug("配信履歴データなし");
            return false;
        }

        //結果状況一覧のページ内容をセット
        int limit = priConf.getAppListCount(); //表示行数
        int maxCount = dao.getJListCount(anp140Model.getAnp130SelectAphSid()); //全データ数


        int nowPage = anp140Model.getAnp140NowPage();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPage = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPage, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPage;
            start = maxPageStartRow;
        }

        anp140Model.setAnp140NowPage(nowPage);
        anp140Model.setAnp140DspPage1(nowPage);
        anp140Model.setAnp140DspPage2(nowPage);
        anp140Model.setAnp140PageLabel(PageUtil.createPageOptions(maxCount, limit));

        //配信データ（配信日時・配信者・件名・本分・訓練モードフラグ）を取得
        Anp140HaisinDetaModel hModel = dao.getHaisinData(anp140Model.getAnp130SelectAphSid());

        anp140Model.setAnp140Name(hModel.getName());
        anp140Model.setAnp140usrJkbn(hModel.getUsrJkbn());
        anp140Model.setAnp140usrUkoFlg(hModel.getUsrUkoFlg());
        //件名表示用の設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        String title;
        if (hModel.getKnrenFlg() == GSConstAnpi.KNREN_MODE_ON) {
            title = "【 " + gsMsg.getMessage("anp.knmode") + " 】" + hModel.getSubject();
        } else {
            title = hModel.getSubject();
        }
        anp140Model.setAnp140Subject(title);
        anp140Model.setAnp140Body(anpiBiz.getHaisinMessageBody(
                reqMdl, con, null, null, hModel.getText1(),
                hModel.getText2(), true, hModel.getKnrenFlg()));
        anp140Model.setAnp010KnrenFlg(hModel.getKnrenFlg());

        //現在の状況内容を取得
        AnpiCommonDao cDao = new AnpiCommonDao(con, reqMdl);
        AnpStateModel sModel = cDao.getStateInfo(anp140Model.getAnp130SelectAphSid(), false);

        if (sModel != null) {
            anp140Model.setAnp140HaisinDate(sModel.getHaisinDate());
            anp140Model.setAnp140EndDate(sModel.getLastDate());
            anp140Model.setAnp140ReplyState(sModel.getReplyState());
            anp140Model.setAnp140Buji(sModel.getJokyoGood());
            anp140Model.setAnp140Keisyo(sModel.getJokyoKeisyo());
            anp140Model.setAnp140Jyusyo(sModel.getJokyoJusyo());
            anp140Model.setAnp140SyusyaOk(sModel.getSyusyaOk());
            anp140Model.setAnp140SyusyaNo(sModel.getSyusyaNo());
        }

        //結果状況リストを取得
        List<Anp140JyokyoListModel> jList = dao.getJyokyoList(
                anp140Model.getAnp130SelectAphSid(),
                anp140Model.getAnp140SortKeyIndex(),
                anp140Model.getAnp140OrderKey(),
                start,
                limit);
        anp140Model.setAnp140JyokyoList(jList);

        return true;
    }

    /**
     * <br>[機  能] 配信履歴を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp140Model パラメータモデル
     * @param con  DBコネクション
     * @throws SQLException SQL実行例外
     * @throws IOException IO例外
     * @throws IOToolsException IOTools例外
     */
    public void doDelete(Anp140ParamModel anp140Model, Connection con)
                throws SQLException, IOToolsException, IOException {

        log__.debug("配信履歴削除");
        boolean commitFlg = false;

        try {
            con.setAutoCommit(false);
            AnpHdataDao hDao = new AnpHdataDao(con);
            AnpJdataDao jDao = new AnpJdataDao(con);

            AnpDatausedSumDao adsDao = new AnpDatausedSumDao(con);
            adsDao.insertHdataDelDiff(Arrays.asList(anp140Model.getAnp130SelectAphSid()));
            adsDao.insertJdataDelDiff(anp140Model.getAnp130SelectAphSid());
            
            hDao.delete(anp140Model.getAnp130SelectAphSid());
            jDao.delete(anp140Model.getAnp130SelectAphSid());

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] ログに出力する安否タイトルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param anpSid 安否SID
     * @throws SQLException SQL実行例外
     * @return タイトル
     */
    public String getLogValue(Connection con, RequestModel reqMdl, int anpSid
            ) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);

        String ret = null;
        AnpHdataDao dao = new AnpHdataDao(con);
        AnpHdataModel mdl = dao.select(anpSid);

        if (mdl.getAphKnrenFlg() == GSConstAnpi.KNREN_MODE_ON) {
            ret = "【 " + gsMsg.getMessage("anp.knmode") + " 】" + mdl.getAphSubject();
        } else {
            ret = mdl.getAphSubject();
        }
        return ret;
    }
}
package jp.groupsession.v2.enq.enqmain;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.dao.EnqAdmConfDao;
import jp.groupsession.v2.enq.dao.EnqPriConfDao;
import jp.groupsession.v2.enq.enq010.Enq010Const;
import jp.groupsession.v2.enq.enq010.Enq010Dao;
import jp.groupsession.v2.enq.enq010.Enq010ParamModel;
import jp.groupsession.v2.enq.enq010.Enq010SearchModel;
import jp.groupsession.v2.enq.model.EnqAdmConfModel;
import jp.groupsession.v2.enq.model.EnqPriConfModel;


/**
*
* <br>[機  能] アンケート メイン画面表示のビジネスロジッククラス
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class EnqMainBiz {

    /** logginインスタンス */
    private static Log log__ = LogFactory.getLog(EnqMainBiz.class);


    /**
     *
     * <br>[機  能] 初期表示時に必要な値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行例外
     */
    public void setInitData(
            EnqMainParamModel paramMdl,
            Connection con,
            RequestModel reqMdl
            ) throws SQLException {

        BaseUserModel usrMdl = reqMdl.getSmodel();

        // 表示件数
        int dspCnt = 0;
        // 回答済み表示の有無
        int dspChecked = GSConstEnquete.DSP_CHECKED_NONE;
        // メイン画面表示区分
        int dspKbn = GSConstEnquete.CONF_MAIN_DSP_OFF;


        // アンケート管理者設定を取得
        EnqAdmConfDao admDao = new EnqAdmConfDao(con);
        EnqAdmConfModel admConfMdl = admDao.select();
        // 管理者設定が存在しない場合、デフォルト設定
        if (admConfMdl == null) {
            dspCnt = GSConstEnquete.MAIN_DSPCNT_5;
            dspChecked = GSConstEnquete.DSP_CHECKED_NONE;
            dspKbn = GSConstEnquete.CONF_MAIN_DSP_ON;
        } else if (admConfMdl.getEacMainDspUse() == GSConstEnquete.ENQ_DISP_USER) {
            // メイン画面表示を各ユーザが設定する場合
            //アンケート個人設定を取得
            EnqPriConfDao priConfDao = new EnqPriConfDao(con);
            EnqPriConfModel priConfMdl = priConfDao.select(usrMdl.getUsrsid());
            // 個人設定がない場合、デフォルト設定
            if (priConfMdl == null) {
                dspCnt = GSConstEnquete.MAIN_DSPCNT_5;
                dspChecked = GSConstEnquete.DSP_CHECKED_NONE;
                dspKbn = GSConstEnquete.CONF_MAIN_DSP_ON;
            } else {
                dspCnt = priConfMdl.getEpcDspcntMain();
                dspChecked = priConfMdl.getEpcDspChecked();
                dspKbn = priConfMdl.getEpcMainDsp();
            }
        } else if (admConfMdl.getEacMainDspUse() == GSConstEnquete.ENQ_DISP_ADMIN) {
            // メイン画面表示を管理者が設定する場合
            dspCnt = admConfMdl.getEacDspcntMain();
            dspChecked = admConfMdl.getEacDspChecked();
            dspKbn = admConfMdl.getEacMainDsp();
        }

        // メインに表示する場合
        if (dspKbn == GSConstEnquete.CONF_MAIN_DSP_ON) {
            // 表示するアンケートを取得
            Enq010SearchModel searchMdl = __createSearchModel(con, paramMdl, reqMdl);
            searchMdl.setSessionUserSid(usrMdl.getUsrsid());
            searchMdl.setMaxCount(dspCnt);
            searchMdl.setPage(1);
            if (dspChecked == GSConstEnquete.DSP_CHECKED_NONE) {
                int[] enqStatus = new int[1];
                enqStatus[0] = Enq010Const.STATUS_NOTANS;
                searchMdl.setStatus(enqStatus);
            } else if (dspChecked == GSConstEnquete.DSP_CHECKED_OK) {
                int[] enqStatus = new int[2];
                enqStatus[0] = Enq010Const.STATUS_NOTANS;
                enqStatus[1] = Enq010Const.STATUS_ANS;
            }
            Enq010Dao dao010 = new Enq010Dao(con);
            paramMdl.setEnq010EnqueteList(dao010.getEnqueteList(searchMdl, reqMdl));
        }
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
    private Enq010SearchModel __createSearchModel(Connection con, Enq010ParamModel paramMdl,
            RequestModel reqMdl)
                    throws SQLException {
        Enq010SearchModel searchMdl = new Enq010SearchModel();

        // ページ
        searchMdl.setPage(paramMdl.getEnq010pageTop());
        // フォルダ 受信で固定
        searchMdl.setFolder(Enq010Const.FOLDER_RECEIVE);
        // ログインユーザSID
        searchMdl.setSessionUserSid(reqMdl.getSmodel().getUsrsid());
        // 回答期限を過ぎたアンケートは表示しない
        UDate now = new UDate();
        now.setHour(0);
        now.setMinute(0);
        now.setSecond(0);
        now.setMilliSecond(0);
        searchMdl.setAnsLimitDateFrom(now);
        //ソートキー
        searchMdl.setSortKey(Enq010Const.SORTKEY_ANS_OPEN);
        //並び順
        searchMdl.setOrder(Enq010Const.ORDER_DESC);
        //管理者フラグ
        searchMdl.setEnqAdminFlg(false);

        return searchMdl;
    }

}

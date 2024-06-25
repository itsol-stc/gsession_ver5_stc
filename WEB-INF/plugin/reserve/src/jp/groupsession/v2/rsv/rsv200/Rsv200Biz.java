package jp.groupsession.v2.rsv.rsv200;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.rsv080.Rsv080Model;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 施設予約 施設一括設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv200Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv200Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv200Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 処理権限判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv200ParamModel
     * @return ret true:処理実行可 false:処理実行負荷
     * @throws SQLException SQL実行時例外
     */
    public boolean isPossibleToProcess(Rsv200ParamModel paramMdl)
        throws SQLException {

       /***********************************************
         *
         * 施設の編集が可能か判定する
         *
         * 1:システム管理者である
         * 2:処理対象の施設グループが【権限設定をしない】に
         *   設定されている
         * 3:施設グループの管理者に設定されている
         *
         ***********************************************/
        boolean ret = _isSisetuEditAuthority(reqMdl_, con_, paramMdl.getRsv080EditGrpSid());
        log__.debug("処理権限 = " + ret);

        return ret;

    }

    /**
     * <br>[機  能] 画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv200ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setSisetuData(Rsv200ParamModel paramMdl) throws SQLException {

        int grpSid = paramMdl.getRsv080EditGrpSid();

        //施設グループ情報を取得
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
        Rsv080Model grpMdl = grpDao.getGrpBaseData(grpSid);
        int rskSid = grpMdl.getRskSid();

        //施設区分によって表示する項目を決定する
        __setSisetuItem(paramMdl, rskSid);

        //施設一覧取得
        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        ArrayList<RsvSisDataModel> rsv200SisetuList =
            dataDao.selectGrpSisetuList(grpSid);
        paramMdl.setRsv200SisetuList(rsv200SisetuList);
    }

    /**
     * <br>[機  能] 施設区分に応じて施設の入力項目をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv200ParamModel
     * @param rskSid 施設区分SID
     */
    private void __setSisetuItem(Rsv200ParamModel paramMdl, int rskSid) {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                paramMdl.setRsv200PropHeaderName1(gsMsg.getMessage("reserve.128"));
                paramMdl.setRsv200PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                paramMdl.setRsv200PropHeaderName1(gsMsg.getMessage("reserve.130"));
                paramMdl.setRsv200PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                paramMdl.setRsv200PropHeaderName1(gsMsg.getMessage("reserve.129"));
                paramMdl.setRsv200PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv200PropHeaderName4(gsMsg.getMessage("reserve.134"));
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                paramMdl.setRsv200PropHeaderName1(gsMsg.getMessage("reserve.131"));
                paramMdl.setRsv200PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv200PropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            default:
                break;
        }
    }
}
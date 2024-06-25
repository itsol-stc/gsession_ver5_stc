package jp.groupsession.v2.rsv.rsv240kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisMainDao;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvSisMainModel;

/**
 * <br>[機  能] 施設予約 メイン表示設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv240knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv240knBiz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;
//    /**
//     * <br>[機  能] デフォルトコンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public Rsv240knBiz() {
//        super();
//    }
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Rsv240knBiz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }
    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv240knParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Rsv240knParamModel paramMdl, Connection con) throws SQLException {

        //DBより設定情報を取得。
        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        String[] checkGrp = paramMdl.getRsv240RsgSids();
        List<RsvSisGrpModel> rsgMdlList = dao.select(checkGrp);
        paramMdl.setRsv240knGrpList(rsgMdlList);

        if (rsgMdlList == null || rsgMdlList.size() == 0) {
            paramMdl.setRsv240overTimeDspKbn(GSConstReserve.RSV_OVERTIME_DSP_ON);
        }
    }

    /**
     * <br>[機  能] メイン画面表示情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv240knParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void updateData(Rsv240knParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        RsvSisMainDao dao = new RsvSisMainDao(con);
        RsvSisMainModel model = new RsvSisMainModel();
        UDate now = new UDate();
        int usrSid = umodel.getUsrsid();

        model.setUsrSid(usrSid);
        model.setRsmDspKbn(paramMdl.getRsv240overTimeDspKbn());
        model.setRsmAuid(usrSid);
        model.setRsmAdate(now);
        model.setRsmEuid(usrSid);
        model.setRsmEdate(now);
        boolean commitFlg = false;
        String[] rsgSids = paramMdl.getRsv240RsgSids();

        try {

            dao.delete(usrSid);
            if (rsgSids != null && rsgSids.length > 0) {
                for (String rsgSid : rsgSids) {
                    model.setRsgSid(Integer.parseInt(rsgSid));
                    dao.insert(model);
                }
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
    }
    /**
     * <br>[機  能] ログメッセージ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList SIDリスト
     * @param con コネクション
     * @return メッセージ
     * @throws SQLException SQL実行エラー
     */
    public String getLogMessage(String[] sidList, Connection con) throws SQLException {

        //DBより設定情報を取得。
        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        List<RsvSisGrpModel> rsgMdlList = dao.select(sidList);
        String msg = "";
        for (RsvSisGrpModel mdl : rsgMdlList) {
            if (msg.length() > 0) {
                msg += "\r\n";
            }
            msg += mdl.getRsgName();
        }
        return msg;
    }
}

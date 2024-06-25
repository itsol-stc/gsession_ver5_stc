package jp.groupsession.v2.ntp.ntp250;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.dao.NtpTargetDao;
import jp.groupsession.v2.ntp.model.NtpTargetModel;

/**
 * <br>[機  能] 日報 目標情報ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp250Biz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Ntp250Biz(Connection con, RequestModel reqMdl) {
        reqMdl__ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp250ParamModel
     * @param userMdl セッションユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp250ParamModel paramMdl,
            BaseUserModel userMdl,
            Connection con) throws SQLException {

        //目標データ取得
        NtpTargetDao targetDao = new NtpTargetDao(con);
        NtpTargetModel targetModel = targetDao.select(paramMdl.getNtp250NtgSid());
        paramMdl.setNtp250TargetName(targetModel.getNtgName());
        paramMdl.setNtp250TargetDetail(
                StringUtilHtml.returntoBR(targetModel.getNtgDetail()));
        paramMdl.setNtp250TargetDef(targetModel.getNtgDef());
        paramMdl.setNtp250TargetUnit(targetModel.getNtgUnit());

    }
}
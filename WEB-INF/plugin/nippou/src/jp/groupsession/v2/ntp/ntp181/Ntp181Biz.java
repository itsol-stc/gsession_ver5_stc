package jp.groupsession.v2.ntp.ntp181;

import java.sql.Connection;
import java.sql.SQLException;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpKthouhouDao;
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;

/**
 * <br>[機  能] 日報 活動方法登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp181Biz {

    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <p>デフォルトコンストラクタ
     * @param reqMdl RequestModel
     */
    public Ntp181Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>コンストラクタ
     * @param cntCon MlCountMtController
     * @param reqMdl RequestModel
     */
    public Ntp181Biz(MlCountMtController cntCon,
                     RequestModel reqMdl) {
        cntCon__ = cntCon;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp181ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp181ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getNtp180ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            //変更処理
            NtpKthouhouDao kthouhouDao = new NtpKthouhouDao(con);
            NtpKthouhouModel kthouhouMdl = kthouhouDao.select(paramMdl.getNtp180NkhSid());
            if (kthouhouDao != null) {
                paramMdl.setNtp181KthouhouCode(kthouhouMdl.getNkhCode());
                paramMdl.setNtp181KthouhouName(kthouhouMdl.getNkhName());
            }
        } else if (paramMdl.getNtp180ProcMode().equals(GSConstNippou.CMD_ADD)) {
            //採番SID
            int ngySid = (int) cntCon__.getSaibanNumberNotCommit(con, GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_KTHOUHOU, sessionUserSid);
            paramMdl.setNtp181KthouhouCode(String.valueOf(ngySid));
        }
    }
}

package jp.groupsession.v2.ntp.ntp191;

import java.sql.Connection;
import java.sql.SQLException;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpContactDao;
import jp.groupsession.v2.ntp.model.NtpContactModel;

/**
 * <br>[機  能] 日報 顧客源泉登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp191Biz {

    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param cntCon MlCountMtController
     * @param reqMdl RequestModel
     */
    public Ntp191Biz(MlCountMtController cntCon,
                     RequestModel reqMdl) {
        cntCon__ = cntCon;
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp191ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp191ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getNtp190ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            //変更処理
            NtpContactDao contactDao = new NtpContactDao(con);
            NtpContactModel contactMdl = contactDao.select(paramMdl.getNtp190NcnSid());
            if (contactDao != null) {
                paramMdl.setNtp191ContactCode(contactMdl.getNcnCode());
                paramMdl.setNtp191ContactName(contactMdl.getNcnName());
            }
        } else if (paramMdl.getNtp190ProcMode().equals(GSConstNippou.CMD_ADD)) {
            //登録処理
            //初期値(採番SID)を表示
            int ngySid = (int) cntCon__.getSaibanNumberNotCommit(con, GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_CONTACT, sessionUserSid);
            paramMdl.setNtp191ContactCode(String.valueOf(ngySid));
        }
    }
}

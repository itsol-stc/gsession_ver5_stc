package jp.groupsession.v2.ntp.ntp141;

import java.sql.Connection;
import java.sql.SQLException;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpGyomuDao;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;

/**
 * <br>[機  能] 日報 業種登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp141Biz {

    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <p>デフォルトコンストラクタ
     * @param reqMdl RequestModel
     */
    public Ntp141Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>コンストラクタ
     * @param cntCon MlCountMtController
     * @param reqMdl RequestModel
     */
    public Ntp141Biz(MlCountMtController cntCon,
                     RequestModel reqMdl) {
        cntCon__ = cntCon;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp141ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp141ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getNtp140ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            //変更処理
            NtpGyomuDao gyomuDao = new NtpGyomuDao(con);
            NtpGyomuModel gyomuMdl = gyomuDao.select(paramMdl.getNtp140NgySid());
            if (gyomuMdl != null) {
                paramMdl.setNtp141GyomuCode(gyomuMdl.getNgyCode());
                paramMdl.setNtp141GyomuName(gyomuMdl.getNgyName());
            }
        } else if (paramMdl.getNtp140ProcMode().equals(GSConstNippou.CMD_ADD)) {
            //採番SID
            int ngySid = (int) cntCon__.getSaibanNumberNotCommit(con, GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_GYOMU, sessionUserSid);
            paramMdl.setNtp141GyomuCode(String.valueOf(ngySid));
        }
    }
}

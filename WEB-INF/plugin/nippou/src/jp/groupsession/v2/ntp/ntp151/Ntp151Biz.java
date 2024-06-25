package jp.groupsession.v2.ntp.ntp151;

import java.sql.Connection;
import java.sql.SQLException;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpProcessDao;
import jp.groupsession.v2.ntp.model.NtpProcessModel;

/**
 * <br>[機  能] 日報 プロセス登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp151Biz {

    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <p>デフォルトコンストラクタ
     * @param reqMdl RequestModel
     */
    public Ntp151Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>コンストラクタ
     * @param cntCon MlCountMtController
     * @param reqMdl RequestModel
     */
    public Ntp151Biz(MlCountMtController cntCon,
                     RequestModel reqMdl) {
        cntCon__ = cntCon;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp151ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp151ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getNtp150ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            //変更処理
            NtpProcessDao processDao = new NtpProcessDao(con);
            NtpProcessModel processMdl = processDao.select(paramMdl.getNtp150NgpSid());
            if (processDao != null) {
                paramMdl.setNtp151ProcessCode(processMdl.getNgpCode());
                paramMdl.setNtp151ProcessName(processMdl.getNgpName());
                paramMdl.setNtp151Naiyo(processMdl.getNgpAccount());
            }
        } else {
            if (paramMdl.getNtp150DispNgySid().equals("all")) {
                paramMdl.setNtp150DispNgySid("-1");
            }
            paramMdl.setNtp150NgySid(Integer.valueOf(paramMdl.getNtp150DispNgySid()));
            //採番SID
            int ngySid = (int) cntCon__.getSaibanNumberNotCommit(con, GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_PROCESS, sessionUserSid);
            paramMdl.setNtp151ProcessCode(String.valueOf(ngySid));
        }
        NtpCommonBiz cBiz = new NtpCommonBiz(con, reqMdl__);
        //業務コンボ設定
        paramMdl.setNtp151GyomuList(cBiz.getGyomuList(con, "選択してください"));
    }
    /**
     * <br>[機  能] 画面表示データの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp151ParamModel
     * @param con コネクション

     * @throws SQLException SQL実行エラー
     */
    public void setDspData(Ntp151ParamModel paramMdl,
            Connection con) throws SQLException {
        NtpCommonBiz cBiz = new NtpCommonBiz(con, reqMdl__);
        //業務コンボ設定
        paramMdl.setNtp151GyomuList(cBiz.getGyomuList(con, "選択してください"));
    }
}

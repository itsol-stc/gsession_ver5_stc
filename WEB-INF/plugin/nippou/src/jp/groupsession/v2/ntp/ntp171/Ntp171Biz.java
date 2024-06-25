package jp.groupsession.v2.ntp.ntp171;

import java.sql.Connection;
import java.sql.SQLException;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpKtbunruiDao;
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;

/**
 * <br>[機  能] 日報 活動分類登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp171Biz {

    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <p>デフォルトコンストラクタ
     * @param reqMdl RequestModel
     */
    public Ntp171Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>コンストラクタ
     * @param cntCon MlCountMtController
     * @param reqMdl RequestModel
     */
    public Ntp171Biz(MlCountMtController cntCon,
                     RequestModel reqMdl) {
        cntCon__ = cntCon;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp171ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getNtp170ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            //変更処理
            NtpKtbunruiDao ktbunruiDao = new NtpKtbunruiDao(con);
            NtpKtbunruiModel ktbunruiMdl = ktbunruiDao.select(paramMdl.getNtp170NkbSid());
            if (ktbunruiDao != null) {
                paramMdl.setNtp171KtbunruiCode(ktbunruiMdl.getNkbCode());
                paramMdl.setNtp171KtbunruiName(ktbunruiMdl.getNkbName());
                paramMdl.setNtp171TieupKbn(ktbunruiMdl.getNkbTieupKbn());
            }
        } else if (paramMdl.getNtp170ProcMode().equals(GSConstNippou.CMD_ADD)) {
            //採番SID
            int ngySid = (int) cntCon__.getSaibanNumberNotCommit(con, GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_KTBUNRUI, sessionUserSid);
            paramMdl.setNtp171KtbunruiCode(String.valueOf(ngySid));
        }
    }
}

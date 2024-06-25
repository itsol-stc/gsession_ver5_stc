package jp.groupsession.v2.cir.cir200;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.model.CirAconfModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 回覧板 管理者設定 ショートメール通知設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir200Biz {

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Cir200ParamModel paramMdl, RequestModel reqMdl)
            throws SQLException {
        if (paramMdl.getCir200InitFlg() != 1) {

            CirCommonBiz cmnBiz = new CirCommonBiz();
            CirAconfModel admMdl = cmnBiz.getCirAdminData(con, reqMdl.getSmodel().getUsrsid());

            paramMdl.setCir200SmlSendKbn(admMdl.getCafSmailSendKbn());
            paramMdl.setCir200SmlSend(admMdl.getCafSmailSend());
            paramMdl.setCir200SmlMemo(admMdl.getCafSmailSendMemo());
            paramMdl.setCir200SmlEdt(admMdl.getCafSmailSendEdit());

            paramMdl.setCir200InitFlg(1);
        }
    }
}

package jp.groupsession.v2.cir.cir200kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAconfDao;
import jp.groupsession.v2.cir.model.CirAconfModel;

/**
 * <br>[機  能] 回覧板 管理者設定 ショートメール通知設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir200knBiz {

    /**
     * <br>[機  能] 回覧板 管理者設定のショートメール通知設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateCirSmailSetting(Cir200knParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        CirCommonBiz cirBiz = new CirCommonBiz();
        CirAconfModel cirMdl = cirBiz.getCirAdminData(con, userSid);
        cirMdl.setCafSmailSendKbn(paramMdl.getCir200SmlSendKbn());
        cirMdl.setCafSmailSend(paramMdl.getCir200SmlSend());
        cirMdl.setCafSmailSendMemo(paramMdl.getCir200SmlMemo());
        cirMdl.setCafSmailSendEdit(paramMdl.getCir200SmlEdt());

        if (cirMdl.getCafSmailSendKbn() == GSConstCircular.CAF_SML_NTF_USER) {
            cirMdl.setCafSmailSend(GSConstCircular.CAF_SML_NTF_KBN_YES);
            cirMdl.setCafSmailSendMemo(GSConstCircular.CAF_SML_NTF_KBN_YES);
            cirMdl.setCafSmailSendEdit(GSConstCircular.CAF_SML_NTF_KBN_YES);
        }
        UDate now = new UDate();
        cirMdl.setCafEuid(userSid);
        cirMdl.setCafEdate(now);

        CirAconfDao aconfDao = new CirAconfDao(con);
        if (aconfDao.update(cirMdl) == 0)  {
            cirMdl.setCafAuid(userSid);
            cirMdl.setCafAdate(now);
            aconfDao.insert(cirMdl);
        }
    }
}
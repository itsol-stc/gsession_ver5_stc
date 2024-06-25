package jp.groupsession.v2.fil.fil260kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.model.FileAconfModel;

/**
 * <br>[機  能] 管理者設定 ショートメール通知設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil260knBiz {
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** DBコネクション */
    private Connection con__ = null;


    /**
     * コンストラクタ
     * @param reqMdl
     * @param con
     */
    public Fil260knBiz(RequestModel reqMdl, Connection con) {
        super();
        reqMdl__ = reqMdl;
        con__ = con;
    }


    /**
     * <br>[機  能] ファイル管理 管理者設定のショートメール通知設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void updateFileSmailSetting(Fil260knParamModel paramMdl)
    throws SQLException {

        //ファイル管理管理者情報を取得
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        FileAconfModel admData = filBiz.getFileAdminData();

        admData.setFacEuid(reqMdl__.getSmodel().getUsrsid());
        admData.setFacSmailSendKbn(paramMdl.getFil260smlSendKbn());
        admData.setFacSmailSend(paramMdl.getFil260smlSend());
        if (admData.getFacSmailSendKbn() == GSConstFile.FAC_SMAIL_SEND_KBN_USER) {
            admData.setFacSmailSend(GSConstFile.FAC_SMAIL_SEND_YES);
        }

        FileAconfDao aconfDao = new FileAconfDao(con__);
        if (aconfDao.updateSmailSend(admData) == 0)  {
            admData.setFacAuid(reqMdl__.getSmodel().getUsrsid());
            aconfDao.insert(admData);
        }
    }
}
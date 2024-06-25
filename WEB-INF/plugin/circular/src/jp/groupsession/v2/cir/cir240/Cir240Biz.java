package jp.groupsession.v2.cir.cir240;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirLabelDao;
import jp.groupsession.v2.cir.model.CirLabelModel;

/**
 * <br>[機  能] ラベル登録編集画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir240Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir240Biz.class);
    /** コネクション */
    private Connection con__ = null;
    /**
     * コンストラクタ
     * @param con コネクション
     */
    public Cir240Biz(Connection con) {
        con__ = con;
    }


    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Cir240ParamModel paramMdl) throws SQLException {
        log__.info("init");
        //アカウント名取得
        int accountSid = paramMdl.getCirAccountSid();
        CirAccountDao acDao = new CirAccountDao(con__);
        String accountName =  acDao.getCirAccountName(accountSid);
        paramMdl.setCir230accountName(accountName);
        //初期表示　編集
        if (paramMdl.getCir240initKbn() == GSConstCircular.DSP_FIRST
        && paramMdl.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_EDIT) {
            //ラベル名設定
            int claSid = paramMdl.getCir230EditLabelId();
            CirLabelDao labelDao = new CirLabelDao(con__);
            CirLabelModel labelData = labelDao.select(claSid, accountSid);
            paramMdl.setCir240LabelName(labelData.getClaName());
            //初期表示完了
            paramMdl.setCir240initKbn(GSConstCircular.DSP_ALREADY);
        }
    }

}

package jp.groupsession.v2.cir.cir190kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirInitDao;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板 アカウント基本設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir190knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir190knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Cir190knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void setData(Cir190knParamModel paramMdl) throws Exception {

        boolean commitFlg = false;
        try {

            CirCommonBiz cmnBiz = new CirCommonBiz(con__);

            con__.setAutoCommit(false);
            CirInitDao cacDao = new CirInitDao(con__);
            CirInitModel cacMdl = new CirInitModel();

            cacMdl = cmnBiz.getCirInitConf(reqMdl__.getSmodel().getUsrsid(), con__);

            //アカウント作成区分設定
            cacMdl.setCinAcntMake(paramMdl.getCir190acntMakeKbn());
            //自動削除区分
            cacMdl.setCinAutoDelKbn(paramMdl.getCir190autoDelKbn());
            //アカウント使用者制限
            cacMdl.setCinAcntUser(paramMdl.getCir190acntUser());

            //テーブルにデータが存在するかチェックする
            int count = cacDao.selectCount();

            if (count > 0) {
                //更新処理
                cacDao.updateActSetting(cacMdl);
            } else {
                cacDao.insertAdmInitSet(cacMdl);
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
        throw e;

        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }
}
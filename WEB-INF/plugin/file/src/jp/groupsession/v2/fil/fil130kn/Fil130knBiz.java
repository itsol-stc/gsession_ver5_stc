package jp.groupsession.v2.fil.fil130kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileUconfDao;
import jp.groupsession.v2.fil.model.FileUconfModel;

/**
 * <br>[機  能] 個人設定 ショートメール通知設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil130knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil130knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil130knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil130knParamModel
     * @param buMdl セッションユーザモデル
     * @throws Exception SQL実行例外
     */
    public void registerData(Fil130knParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        log__.debug("fil130knBiz Start");

        FileUconfDao uconfDao = new FileUconfDao(con__);

        //登録モデル取得する。
        FileUconfModel uconfModel = __setData(paramMdl, buMdl.getUsrsid());

        int count = uconfDao.updateSmailConf(uconfModel);
        if (count < 1) {
            uconfDao.insert(uconfModel);
        }

    }

    /**
     * <br>[機  能] 登録モデル取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil130knParamModel
     * @param usrSid セッションユーザSID
     * @return uconfModel セッションユーザSID
     */
    private FileUconfModel __setData(Fil130knParamModel paramMdl, int usrSid) {

        FileUconfModel uconfModel = new FileUconfModel();

        uconfModel.setFucMainOkini(GSConstFile.MAIN_OKINI_DSP_ON);
        uconfModel.setFucMainCall(GSConstFile.MAIN_CALL_DSP_CNT);
        uconfModel.setFucRirekiCnt(GSConstFile.RIREKI_COUNT_DEFAULT);
        uconfModel.setFucSmailSend(
                NullDefault.getInt(paramMdl.getFil130SmailSend(), GSConstFile.SMAIL_SEND_ON));
        uconfModel.setUsrSid(usrSid);

        return uconfModel;
    }
}
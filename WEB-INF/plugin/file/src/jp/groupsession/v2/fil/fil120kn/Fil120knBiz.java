package jp.groupsession.v2.fil.fil120kn;

import java.sql.Connection;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileUconfDao;
import jp.groupsession.v2.fil.model.FileUconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 個人設定 表示設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil120knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil120knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil120knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil120knParamModel
     * @param buMdl セッションユーザモデル
     * @throws Exception SQL実行例外
     */
    public void registerData(Fil120knParamModel paramMdl, BaseUserModel buMdl) throws Exception {

        log__.debug("fil120knBiz Start");

        FileUconfDao uconfDao = new FileUconfDao(con__);

        //登録モデル取得する。
        FileUconfModel uconfModel = __setData(paramMdl, buMdl.getUsrsid());

        int count = uconfDao.updateDspConf(uconfModel);
        if (count < 1) {
            uconfDao.insert(uconfModel);
        }

    }

    /**
     * <br>[機  能] 登録モデル取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil120knParamModel
     * @param usrSid セッションユーザSID
     * @return uconfModel セッションユーザSID
     */
    private FileUconfModel __setData(Fil120knParamModel paramMdl, int usrSid) {

        FileUconfModel uconfModel = new FileUconfModel();

        uconfModel.setFucMainOkini(
                NullDefault.getInt(paramMdl.getFil120MainSort(), GSConstFile.MAIN_OKINI_DSP_ON));
        uconfModel.setFucMainCall(
                NullDefault.getInt(paramMdl.getFil120MainCall(), GSConstFile.MAIN_CALL_DSP_CNT));
        uconfModel.setFucRirekiCnt(
                NullDefault.getInt(paramMdl.getFil120RirekiCnt(),
                        GSConstFile.RIREKI_COUNT_DEFAULT));
        uconfModel.setFucCall(
                NullDefault.getInt(paramMdl.getFil120Call(), GSConstFile.CALL_DSP_CNT));
        uconfModel.setFucSmailSend(GSConstFile.SMAIL_SEND_ON);
        uconfModel.setUsrSid(usrSid);

        return uconfModel;
    }
}
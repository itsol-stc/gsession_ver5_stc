package jp.groupsession.v2.fil.fil130;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileUconfDao;
import jp.groupsession.v2.fil.model.FileUconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 個人設定 ショートメール通知設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil130Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil130Biz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil130Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil130ParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil130ParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        log__.debug("fil130Biz Start");

        //初期表示の場合、DB登録値を表示する。
        if (StringUtil.isNullZeroString(paramMdl.getFil130SmailSend())) {
            __setData(paramMdl, buMdl.getUsrsid());
        }
    }

    /**
     * <br>[機  能] DBから個人設定を取得し、設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil130ParamModel
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __setData(Fil130ParamModel paramMdl, int usrSid) throws SQLException {

        FileUconfDao uconfDao = new FileUconfDao(con__);
        FileUconfModel uconf = uconfDao.select(usrSid);

        if (uconf == null) {
            __setDefault(paramMdl);
            return;
        }

        paramMdl.setFil130SmailSend(String.valueOf(uconf.getFucSmailSend()));
    }

    /**
     * <br>[機  能] 入力項目に初期値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil130ParamModel
     */
    private void __setDefault(Fil130ParamModel paramMdl) {

        paramMdl.setFil130SmailSend(String.valueOf(GSConstFile.SMAIL_SEND_ON));

    }
}

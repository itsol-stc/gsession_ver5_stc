package jp.groupsession.v2.api.smail.mail.delete;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.model.SmlSmeisModel;

/**
 * <br>[機  能] ショートメールを完全削除するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-mail/delete",
plugin = "smail", name = "ショートメール削除",
url = "/api/smail/mail/delete.do", reqtype = "DELETE")
public class ApiSmlMailDeleteForm extends AbstractApiForm {

    /** アカウントSID*/
    @ApiParam(name = "sacSid", viewName = "アカウントSID")
    private int sacSid__  = -1;

    /** メールSID */
    @ApiParam(name = "smlSid", viewName = "ショートメールSID")
    private int smlSid__  = -1;

    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }
    /**
     * <p>sacSid をセットします。
     * @param sacSid アカウントSID
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

    /**
     * <p>smlSid を取得します。
     * @return smlSid
     */
    public int getSmlSid() {
        return smlSid__;
    }
    /**
     * <p>smlSid をセットします。
     * @param smlSid ショートメールSID
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }

    /**
     * <br>[機  能] 送信メールかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @return 送信メールか有無
     * @throws SQLException SQL実行時例外
     */
    public boolean isSendMailCheck(Connection con) throws SQLException {

        if (this.getSmlSid() >= 0) {
            SmlSmeisModel mdl = new SmlSmeisModel();
            mdl.setSmsSid(this.getSmlSid());
            SmlSmeisDao smsDao  = new SmlSmeisDao(con);
            mdl = smsDao.select(mdl);
            if (mdl != null && mdl.getSacSid() == this.getSacSid()) {
                // 自身が送信したメールの場合のみＯＫ
                return true;
            }
        }
        return false;
    }
}

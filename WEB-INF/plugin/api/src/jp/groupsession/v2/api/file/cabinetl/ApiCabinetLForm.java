package jp.groupsession.v2.api.file.cabinetl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/cabinetlのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-cabinetl",
plugin = "file", name = "キャビネット一覧取得",
url = "/api/file/cabinetl.do",
reqtype = "GET")
public class ApiCabinetLForm extends AbstractApiForm {

    /** キャビネット区分 */
    @ApiParam(name = "cabinetKbn", viewName = "キャビネット区分")
    private int cabinetKbn__ = GSConstFile.ERRL_KBN_OFF;
    
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GsMessage
     * @param reqMdl リクエストモデル
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCabinetL(Connection con, GsMessage gsMsg,
                                            RequestModel reqMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;


        if (cabinetKbn__ < GSConstFile.ERRL_KBN_OFF || cabinetKbn__ > GSConstFile.ERRL_KBN_ON) {
            //0,1以外が入力されていないかを確認
            msg = new ActionMessage(
                    "error.input.addhani.text",
                    gsMsg.getMessage("fil.991"),
                    GSConstFile.ERRL_KBN_OFF,
                    GSConstFile.ERRL_KBN_ON);
                StrutsUtil.addMessage(errors, msg, "error.input.addhani.text");
        }
        return errors;
    }

    /**
     * <p>cabinetKbn を取得します。
     * @return cabinetKbn
     * @see jp.groupsession.v2.api.file.cabinetl.ApiCabinetLForm#cabinetKbn__
     */
    public int getCabinetKbn() {
        return cabinetKbn__;
    }

    /**
     * <p>cabinetKbn をセットします。
     * @param cabinetKbn cabinetKbn
     * @see jp.groupsession.v2.api.file.cabinetl.ApiCabinetLForm#cabinetKbn__
     */
    public void setCabinetKbn(int cabinetKbn) {
        cabinetKbn__ = cabinetKbn;
    }
}

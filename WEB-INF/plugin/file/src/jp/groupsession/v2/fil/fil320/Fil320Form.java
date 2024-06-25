package jp.groupsession.v2.fil.fil320;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.fil310.Fil310Form;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] ファイル管理 外貨登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil320Form extends Fil310Form {
    /** 外貨名 */
    private String fil320GaikaName__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws Exception  実行例外
     * @return エラー
     * @throws SQLException 
     */
    public ActionErrors validateCheck(RequestModel reqMdl, Connection con)
            throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String targetName = gsMsg.getMessage("fil.fil310.1");

        //外貨名の入力チェック
        GSValidateFile.validateTextBoxInput(
                errors,
                fil320GaikaName__,
                targetName,
                GSConstFile.MAX_LENGTH_GAIKA,
                true);
        
        ActionMessage msg = null;
        if (ValidateUtil.isTab(fil320GaikaName__)) {
            msg = new ActionMessage("error.input.tab.text", targetName);
            StrutsUtil.addMessage(errors, msg, "error.input.tab.targetName");
        }
        
        if (errors.size() == 0) {
            FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con);
            if (fmmDao.existGaikaName(fil320GaikaName__)) {
                msg = new ActionMessage("error.cant.use.gaika.name");
                StrutsUtil.addMessage(
                        errors, msg, "error.cant.use.gaika.name");
            }
        }

        return errors;
    }

    /**
     * <p>fil320GaikaName を取得します。
     * @return fil320GaikaName
     * @see jp.groupsession.v2.fil.fil320.Fil320Form#fil320GaikaName__
     */
    public String getFil320GaikaName() {
        return fil320GaikaName__;
    }
    /**
     * <p>fil320GaikaName をセットします。
     * @param fil320GaikaName fil320GaikaName
     * @see jp.groupsession.v2.fil.fil320.Fil320Form#fil320GaikaName__
     */
    public void setFil320GaikaName(String fil320GaikaName) {
        fil320GaikaName__ = fil320GaikaName;
    }
}
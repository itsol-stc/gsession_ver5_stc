package jp.groupsession.v2.cir.cir240;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.GSValidateCircular;
import jp.groupsession.v2.cir.cir230.Cir230Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ラベル登録編集画面のアクションフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir240Form extends Cir230Form {

    /** ラベル名 */
    private String cir240LabelName__ = null;
    /** 初期表示区分 */
    private int cir240initKbn__ = 0;


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト
     * @throws Exception  実行例外
     * @return エラー
     */
    public ActionErrors validate240Check(RequestModel reqMdl) throws Exception {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateCircular.validateTextBoxInput(errors, cir240LabelName__,
            "cir240LabelName",
            gsMsg.getMessage("cmn.label.name"),
            GSConstCircular.MAXLEN_LABEL_NAME,
            true);
        return errors;
    }

    /**
     * <p>cir240LabelName を取得します。
     * @return cir240LabelName
     * @see jp.groupsession.v2.cir.cir240.Cir240Form#cir240LabelName__
     */
    public String getCir240LabelName() {
        return cir240LabelName__;
    }
    /**
     * <p>cir240LabelName をセットします。
     * @param cir240LabelName cir240LabelName
     * @see jp.groupsession.v2.cir.cir240.Cir240Form#cir240LabelName__
     */
    public void setCir240LabelName(String cir240LabelName) {
        cir240LabelName__ = cir240LabelName;
    }
    /**
     * <p>cir240initKbn を取得します。
     * @return cir240initKbn
     * @see jp.groupsession.v2.cir.cir240.Cir240Form#cir240initKbn__
     */
    public int getCir240initKbn() {
        return cir240initKbn__;
    }
    /**
     * <p>cir240initKbn をセットします。
     * @param cir240initKbn cir240initKbn
     * @see jp.groupsession.v2.cir.cir240.Cir240Form#cir240initKbn__
     */
    public void setCir240initKbn(int cir240initKbn) {
        cir240initKbn__ = cir240initKbn;
    }

}

package jp.groupsession.v2.cir.cir230;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir150.Cir150Form;
import jp.groupsession.v2.cir.model.LabelDataModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ラベル管理画面のアクションフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir230Form extends Cir150Form {

    /** ラベル編集モード */
    private int cir230LabelCmdMode__ = GSConstCircular.CMDMODE_ADD;
    /** 編集対象ラベルSID */
    private int cir230EditLabelId__ = 0;
    /** ラベルリスト */
    private List<LabelDataModel> cir230LabelList__ = null;
    /** アカウント名 */
    private String cir230accountName__ = null;
    /** 選択ラベル */
    private String cir230SortRadio__ = null;
    /** ページ表示区分 */
    private int cir230DspKbn__ = GSConstCircular.DSPKBN_PREF;


    /**
     * <br>[機  能] ラベルアクセスチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con Connection
     * @throws SQLException  実行例外
     * @return エラー
     */
    public ActionErrors validateLabelAccessCheck(
            RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        CirCommonBiz cirBiz = new CirCommonBiz();
        if (!cirBiz.checkExistLabel(con,
                this.getCir230EditLabelId(),
                this.getCirAccountSid())) {
            String msgKey = "search.data.notfound";
            ActionMessage msg = new ActionMessage(msgKey,
                    gsMsg.getMessage("cmn.label"));
            StrutsUtil.addMessage(errors, msg, msgKey);
        }
        return errors;
    }

    /**
     * <br>[機  能] ラベルアクセスチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con Connection
     * @throws SQLException  実行例外
     * @return エラー
     */
    public ActionErrors validateLabelSortAccessCheck(
            RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        CirCommonBiz cirBiz = new CirCommonBiz();
        int labelSid = NullDefault.getInt(this.getCir230SortRadio(), -1);
        if (!cirBiz.checkExistLabel(con,
                labelSid,
                this.getCirAccountSid())) {
            String msgKey = "search.data.notfound";
            ActionMessage msg = new ActionMessage(msgKey,
                    gsMsg.getMessage("cmn.label"));
            StrutsUtil.addMessage(errors, msg, msgKey);
        }
        return errors;
    }


    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     */
    public void setHiddenParamCir230(Cmn999Form cmn999Form) {
        cmn999Form.addHiddenParam("cir010adminUser", String.valueOf(isCir010adminUser()));
        if (cir230DspKbn__ == GSConstCircular.DSPKBN_ADMIN) {
            cmn999Form.addHiddenParam("cir150keyword", getCir150keyword());
            cmn999Form.addHiddenParam("cir150group", getCir150group());
            cmn999Form.addHiddenParam("cir150user", getCir150user());
            cmn999Form.addHiddenParam("cir150pageTop", getCir150pageTop());
            cmn999Form.addHiddenParam("cir150pageBottom", getCir150pageBottom());
            cmn999Form.addHiddenParam("cir150svKeyword", getCir150svKeyword());
            cmn999Form.addHiddenParam("cir150svGroup", getCir150svGroup());
            cmn999Form.addHiddenParam("cir150svUser", getCir150svUser());
            cmn999Form.addHiddenParam("cir150sortKey", getCir150sortKey());
            cmn999Form.addHiddenParam("cir150order", getCir150order());
            cmn999Form.addHiddenParam("cir150searchFlg", getCir150searchFlg());

        }
        cmn999Form.addHiddenParam("cir230DspKbn", cir230DspKbn__);
        cmn999Form.addHiddenParam("cir230LabelCmdMode", cir230LabelCmdMode__);
        cmn999Form.addHiddenParam("cir230EditLabelId", cir230EditLabelId__);
    }

    /**
     * <p>cir230LabelCmdMode を取得します。
     * @return cir230LabelCmdMode
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230LabelCmdMode__
     */
    public int getCir230LabelCmdMode() {
        return cir230LabelCmdMode__;
    }

    /**
     * <p>cir230LabelCmdMode をセットします。
     * @param cir230LabelCmdMode cir230LabelCmdMode
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230LabelCmdMode__
     */
    public void setCir230LabelCmdMode(int cir230LabelCmdMode) {
        cir230LabelCmdMode__ = cir230LabelCmdMode;
    }

    /**
     * <p>cir230EditLabelId を取得します。
     * @return cir230EditLabelId
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230EditLabelId__
     */
    public int getCir230EditLabelId() {
        return cir230EditLabelId__;
    }

    /**
     * <p>cir230EditLabelId をセットします。
     * @param cir230EditLabelId cir230EditLabelId
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230EditLabelId__
     */
    public void setCir230EditLabelId(int cir230EditLabelId) {
        cir230EditLabelId__ = cir230EditLabelId;
    }

    /**
     * <p>cir230LabelList を取得します。
     * @return cir230LabelList
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230LabelList__
     */
    public List<LabelDataModel> getCir230LabelList() {
        return cir230LabelList__;
    }

    /**
     * <p>cir230LabelList をセットします。
     * @param cir230LabelList cir230LabelList
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230LabelList__
     */
    public void setCir230LabelList(List<LabelDataModel> cir230LabelList) {
        cir230LabelList__ = cir230LabelList;
    }

    /**
     * <p>cir230accountName を取得します。
     * @return cir230accountName
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230accountName__
     */
    public String getCir230accountName() {
        return cir230accountName__;
    }

    /**
     * <p>cir230accountName をセットします。
     * @param cir230accountName cir230accountName
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230accountName__
     */
    public void setCir230accountName(String cir230accountName) {
        cir230accountName__ = cir230accountName;
    }

    /**
     * <p>cir230SortRadio を取得します。
     * @return cir230SortRadio
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230SortRadio__
     */
    public String getCir230SortRadio() {
        return cir230SortRadio__;
    }

    /**
     * <p>cir230SortRadio をセットします。
     * @param cir230SortRadio cir230SortRadio
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230SortRadio__
     */
    public void setCir230SortRadio(String cir230SortRadio) {
        cir230SortRadio__ = cir230SortRadio;
    }

    /**
     * <p>cir230DspKbn を取得します。
     * @return cir230DspKbn
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230DspKbn__
     */
    public int getCir230DspKbn() {
        return cir230DspKbn__;
    }

    /**
     * <p>cir230DspKbn をセットします。
     * @param cir230DspKbn cir230DspKbn
     * @see jp.groupsession.v2.cir.cir230.Cir230Form#cir230DspKbn__
     */
    public void setCir230DspKbn(int cir230DspKbn) {
        cir230DspKbn__ = cir230DspKbn;
    }




}

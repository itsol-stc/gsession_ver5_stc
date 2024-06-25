package jp.groupsession.v2.cir.cir170kn;

import java.util.List;

import jp.groupsession.v2.cir.cir170.Cir170Form;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;

/**
 * <br>[機  能] 回覧板 アカウントインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir170knForm extends Cir170Form {

    /** 取込ファイル名 */
    private String cir170knFileName__ = null;
    /** 使用者リスト */
    private List<Cir170knUseUsrModel> cir170knUseUserList__ = null;

    /**
     * <p>cir170knUseUserList を取得します。
     * @return cir170knUseUserList
     */
    public List<Cir170knUseUsrModel> getCir170knUseUserList() {
        return cir170knUseUserList__;
    }
    /**
     * <p>cir170knUseUserList をセットします。
     * @param cir170knUseUserList cir170knUseUserList
     */
    public void setCir170knUseUserList(List<Cir170knUseUsrModel> cir170knUseUserList) {
        cir170knUseUserList__ = cir170knUseUserList;
    }
    /**
     * <p>cir170knFileName を取得します。
     * @return cir170knFileName
     */
    public String getCir170knFileName() {
        return cir170knFileName__;
    }
    /**
     * <p>cir170knFileName をセットします。
     * @param cir170knFileName cir170knFileName
     */
    public void setCir170knFileName(String cir170knFileName) {
        cir170knFileName__ = cir170knFileName;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("cir150keyword", getCir150keyword());
        msgForm.addHiddenParam("cir150group", getCir150group());
        msgForm.addHiddenParam("cir150user", getCir150user());
        msgForm.addHiddenParam("cir150pageTop", getCir150pageTop());
        msgForm.addHiddenParam("cir150pageBottom", getCir150pageBottom());
        msgForm.addHiddenParam("cir150svKeyword", getCir150svKeyword());
        msgForm.addHiddenParam("cir150svGroup", getCir150svGroup());
        msgForm.addHiddenParam("cir150svUser", getCir150svUser());
        msgForm.addHiddenParam("cir150sortKey", getCir150sortKey());
        msgForm.addHiddenParam("cir150order", getCir150order());
        msgForm.addHiddenParam("cir150searchFlg", getCir150searchFlg());
    }
}

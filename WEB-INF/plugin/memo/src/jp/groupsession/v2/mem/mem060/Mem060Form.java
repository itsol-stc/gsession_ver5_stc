package jp.groupsession.v2.mem.mem060;

import java.sql.Connection;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.GSValidateMemo;
import jp.groupsession.v2.mem.mem050.Mem050Form;


/**
 * <br>[機  能] メモ帳 個人設定 ラベル登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem060Form extends Mem050Form {
    /** ラベル名 */
    private String mem060LabelName__ = null;
    /** 初期表示区分 */
    private int mem060initKbn__ = GSConstMemo.INIT_FLG;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws Exception  実行例外
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl, Connection con) {
        ActionErrors errors = new ActionErrors();
        GSValidateMemo.validateLabel(errors, mem060LabelName__, reqMdl);

        return errors;
    }

    /**
     * <p>mem060LabelName を取得します。
     * @return mem060LabelName
     */
    public String getMem060LabelName() {
        return mem060LabelName__;
    }
    /**
     * <p>mem060LabelName をセットします。
     * @param mem060LabelName mem060LabelName
     */
    public void setMem060LabelName(String mem060LabelName) {
        mem060LabelName__ = mem060LabelName;
    }
    /**
     * <p>mem060initKbn を取得します。
     * @return mem060initKbn
     */
    public int getMem060initKbn() {
        return mem060initKbn__;
    }
    /**
     * <p>mem060initKbn をセットします。
     * @param mem060initKbn mem060initKbn
     */
    public void setMem060initKbn(int mem060initKbn) {
        mem060initKbn__ = mem060initKbn;
    }
}
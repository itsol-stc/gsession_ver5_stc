package jp.groupsession.v2.enq.enq910kn;

import java.util.ArrayList;

import jp.groupsession.v2.enq.enq910.Enq910Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 管理者設定 アンケート発信対象者設定確認画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq910knForm extends Enq910Form {

    /** アンケート作成対象者区分 */
    private int enq910knTaisyoKbn__ = 0;
    /** アンケート発信対象者リスト */
    private ArrayList<UsrLabelValueBean> enq910knAddSenderLabel__ = null;

    /**
     * <p>アンケート作成対象者区分 を取得します。
     * @return アンケート作成対象者区分
     */
    public int getEnq910knTaisyoKbn() {
        return enq910knTaisyoKbn__;
    }
    /**
     * <p>アンケート作成対象者区分 をセットします。
     * @param enq910knTaisyoKbn アンケート作成対象者区分
     */
    public void setEnq910knTaisyoKbn(int enq910knTaisyoKbn) {
        enq910knTaisyoKbn__ = enq910knTaisyoKbn;
    }
    /**
     * <p>アンケート発信対象者リスト を取得します。
     * @return アンケート発信対象者リスト
     */
    public ArrayList<UsrLabelValueBean> getEnq910knAddSenderLabel() {
        return enq910knAddSenderLabel__;
    }
    /**
     * <p>アンケート発信対象者リスト をセットします。
     * @param enq910knAddSenderLabel アンケート発信対象者リスト
     */
    public void setEnq910knAddSenderLabel(ArrayList<UsrLabelValueBean> enq910knAddSenderLabel) {
        enq910knAddSenderLabel__ = enq910knAddSenderLabel;
    }
}

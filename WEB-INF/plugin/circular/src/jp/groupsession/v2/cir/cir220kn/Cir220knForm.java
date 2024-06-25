package jp.groupsession.v2.cir.cir220kn;

import java.util.ArrayList;

import jp.groupsession.v2.cir.cir220.Cir220Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 管理者設定 回覧板登録制限設定確認画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir220knForm extends Cir220Form {

    /** 回覧板作成対象者区分 */
    private int cir220knTaisyoKbn__ = 0;
    /** 回覧板送信対象者リスト */
    private ArrayList<LabelValueBean> cir220knAddSenderLabel__ = null;

    /**
     * <p>回覧板作成対象者区分 を取得します。
     * @return 回覧板作成対象者区分
     */
    public int getCir220knTaisyoKbn() {
        return cir220knTaisyoKbn__;
    }
    /**
     * <p>回覧板作成対象者区分 をセットします。
     * @param cir220knTaisyoKbn 回覧板作成対象者区分
     */
    public void setCir220knTaisyoKbn(int cir220knTaisyoKbn) {
        cir220knTaisyoKbn__ = cir220knTaisyoKbn;
    }
    /**
     * <p>回覧板送信対象者リスト を取得します。
     * @return 回覧板送信対象者リスト
     */
    public ArrayList<LabelValueBean> getCir220knAddSenderLabel() {
        return cir220knAddSenderLabel__;
    }
    /**
     * <p>回覧板送信対象者リスト をセットします。
     * @param cir220knAddSenderLabel 回覧板送信対象者リスト
     */
    public void setCir220knAddSenderLabel(ArrayList<LabelValueBean> cir220knAddSenderLabel) {
        cir220knAddSenderLabel__ = cir220knAddSenderLabel;
    }
}

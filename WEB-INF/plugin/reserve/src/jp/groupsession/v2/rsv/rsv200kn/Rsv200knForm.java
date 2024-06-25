package jp.groupsession.v2.rsv.rsv200kn;

import jp.groupsession.v2.rsv.rsv200.Rsv200Form;

/**
 * <br>[機  能] 施設予約 施設一括設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv200knForm extends Rsv200Form {

    /** 備考(表示用) */
    private String rsv200knBiko__ = null;

    /**
     * <p>rsv200knBiko__ を取得します。
     * @return rsv200knBiko
     */
    public String getRsv200knBiko() {
        return rsv200knBiko__;
    }

    /**
     * <p>rsv200knBiko__ をセットします。
     * @param rsv200knBiko rsv200knBiko__
     */
    public void setRsv200knBiko(String rsv200knBiko) {
        rsv200knBiko__ = rsv200knBiko;
    }
}
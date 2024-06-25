package jp.groupsession.v2.rsv.rsv300;

import jp.groupsession.v2.rsv.rsv140.Rsv140ParamModel;

/**
 * <br>[機  能] 施設予約 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv300ParamModel extends Rsv140ParamModel {

    /** 通知設定 0=通知しない 1=通知する */
    private int rsv300smailKbn__ = -1;

    /**
     * @return rsv300smailKbn
     */
    public int getRsv300smailKbn() {
        return rsv300smailKbn__;
    }

    /**
     * @param rsv300smailKbn セットする rsv300smailKbn
     */
    public void setRsv300smailKbn(int rsv300smailKbn) {
        rsv300smailKbn__ = rsv300smailKbn;
    }

}
package jp.groupsession.v2.ip.ipk080;

import jp.groupsession.v2.ip.ipk010.Ipk010ParamModel;

/**
 * <br>[機  能] IP管理 管理者メニュー画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk080ParamModel extends Ipk010ParamModel {
    /** ページ遷移コマンド */
    private String CMD__;

    /**
     * <p>CMD を取得します。
     * @return CMD
     */
    public String getCMD() {
        return CMD__;
    }

    /**
     * <p>CMD をセットします。
     * @param CMD CMD
     */
    public void setCMD(String CMD) {
        CMD__ = CMD;
    }
}
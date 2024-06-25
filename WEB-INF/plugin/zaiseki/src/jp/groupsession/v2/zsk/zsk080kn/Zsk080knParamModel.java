package jp.groupsession.v2.zsk.zsk080kn;

import jp.groupsession.v2.zsk.zsk080.Zsk080ParamModel;

/**
 * <br>[機  能] 在席管理 個人設定 初期表示設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk080knParamModel extends Zsk080ParamModel {
    /** 初期表示する座席表名称 */
    private String dfZifName__;

    /**
     * <p>dfZifName を取得します。
     * @return dfZifName
     */
    public String getDfZifName() {
        return dfZifName__;
    }

    /**
     * <p>dfZifName をセットします。
     * @param dfZifName dfZifName
     */
    public void setDfZifName(String dfZifName) {
        dfZifName__ = dfZifName;
    }

}

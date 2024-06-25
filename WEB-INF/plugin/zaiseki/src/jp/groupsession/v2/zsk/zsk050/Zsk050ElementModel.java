package jp.groupsession.v2.zsk.zsk050;

import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.zsk.model.WkZaiIndexModel;

/**
 *
 * <br>[機  能] 編集中エレメント用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk050ElementModel extends WkZaiIndexModel {
    /** 関連ユーザモデル*/
    private UsrLabelValueBean user__;


    /**
     * <p>user を取得します。
     * @return user
     */
    public UsrLabelValueBean getUser() {
        return user__;
    }

    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(UsrLabelValueBean user) {
        user__ = user;
    }
    /**
     * <br>[機  能] ログイン停止状態用のCSSクラス名を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return ログイン停止状態用のCSSクラス名
     * @see jp.groupsession.v2.usr.model.UsrLabelValueBean#getCSSClassNameNormal()
     */
    public String getCSSClassNameNormal() {
        if (user__ == null) {
            return "";
        }
        return user__.getCSSClassNameNormal();
    }

    /**
     * <br>[機  能] ログイン停止状態用のCSSクラス名を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return ログイン停止状態用のCSSクラス名
     * @see jp.groupsession.v2.usr.model.UsrLabelValueBean#getCSSClassNameOption()
     */
    public String getCSSClassNameOption() {
        if (user__ == null) {
            return "";
        }
        return user__.getCSSClassNameOption();
    }

}

package jp.groupsession.v2.zsk.zsk011;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.zsk.GSValidateZaiseki;
import jp.groupsession.v2.zsk.zsk010.Zsk010Form;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] 在席管理 在席状況登録ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk011Form extends Zsk010Form {

    /** 更新対象ユーザ名  */
    private String zsk011UpdateUserName__;
    /** 更新対象ユーザログイン停止フラグ  */
    private int zsk011UpdateUsrUkoFlg__;

    /** 在席状況  */
    private String zsk011Status__;
    /** 在席コメント  */
    private String zsk011Comment__;

    //フラグ
    /** 画面closeフラグ true=closeする、false=closeしない */
    private boolean closeFlg__ = false;

    /**
     * <p>closeFlg を取得します。
     * @return closeFlg
     */
    public boolean isCloseFlg() {
        return closeFlg__;
    }

    /**
     * <p>closeFlg をセットします。
     * @param closeFlg closeFlg
     */
    public void setCloseFlg(boolean closeFlg) {
        closeFlg__ = closeFlg;
    }
    /**
     * <p>zsk011Status を取得します。
     * @return zsk011Status
     */
    public String getZsk011Status() {
        return zsk011Status__;
    }

    /**
     * <p>zsk011Status をセットします。
     * @param zsk011Status zsk011Status
     */
    public void setZsk011Status(String zsk011Status) {
        zsk011Status__ = zsk011Status;
    }

    /**
     * <p>zsk011UpdateUserName を取得します。
     * @return zsk011UpdateUserName
     */
    public String getZsk011UpdateUserName() {
        return zsk011UpdateUserName__;
    }

    /**
     * <p>zsk011UpdateUserName をセットします。
     * @param zsk011UpdateUserName zsk011UpdateUserName
     */
    public void setZsk011UpdateUserName(String zsk011UpdateUserName) {
        zsk011UpdateUserName__ = zsk011UpdateUserName;
    }

    /**
     * <p>zsk011Comment を取得します。
     * @return zsk011Comment
     */
    public String getZsk011Comment() {
        return zsk011Comment__;
    }

    /**
     * <p>zsk011Comment をセットします。
     * @param zsk011Comment zsk011Comment
     */
    public void setZsk011Comment(String zsk011Comment) {
        zsk011Comment__ = zsk011Comment;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param req リクエスト
     * @return errors エラー
     */
    public ActionErrors validateComment(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();

        //コメント
        GSValidateZaiseki.validateZskBiko(errors, zsk011Comment__, req);

        return errors;
    }

    /**
     * <p>zsk011UpdateUsrUkoFlg を取得します。
     * @return zsk011UpdateUsrUkoFlg
     */
    public int getZsk011UpdateUsrUkoFlg() {
        return zsk011UpdateUsrUkoFlg__;
    }

    /**
     * <p>zsk011UpdateUsrUkoFlg をセットします。
     * @param zsk011UpdateUsrUkoFlg zsk011UpdateUsrUkoFlg
     */
    public void setZsk011UpdateUsrUkoFlg(int zsk011UpdateUsrUkoFlg) {
        zsk011UpdateUsrUkoFlg__ = zsk011UpdateUsrUkoFlg;
    }
}

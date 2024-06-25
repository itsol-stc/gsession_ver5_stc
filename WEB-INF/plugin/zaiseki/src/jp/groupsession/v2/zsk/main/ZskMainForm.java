package jp.groupsession.v2.zsk.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.GSValidateZaiseki;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理(メイン画面表示用 本人)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskMainForm extends AbstractGsForm {
    //在席管理
    /** 在席ステータス(選択済) */
    private int zskUioStatus__;
    /** 在席ステータスラベル */
    private List<LabelValueBean> zskUioStatusLabel__;
    /** 在席コメント */
    private String zskUioBiko__;
    /** 在席トップ画面URL */
    private String zskTopUrl__;
    /** メイン再表示フラグ */
    private int mainReDspFlg__ = GSConstZaiseki.MAIN_RELOAD_ON;

    /**
     * <p>mainReDspFlg を取得します。
     * @return mainReDspFlg
     */
    public int getMainReDspFlg() {
        return mainReDspFlg__;
    }
    /**
     * <p>mainReDspFlg をセットします。
     * @param mainReDspFlg mainReDspFlg
     */
    public void setMainReDspFlg(int mainReDspFlg) {
        mainReDspFlg__ = mainReDspFlg;
    }
    /**
     * @return zskTopUrl__ を戻します。
     */
    public String getZskTopUrl() {
        return zskTopUrl__;
    }
    /**
     * @param zskTopUrl 設定する zskTopUrl__。
     */
    public void setZskTopUrl(String zskTopUrl) {
        zskTopUrl__ = zskTopUrl;
    }
    /**
     * @return zskUioStatusLabel__ を戻します。
     */
    public List<LabelValueBean> getZskUioStatusLabel() {
        return zskUioStatusLabel__;
    }
    /**
     * @param zskUioStatusLabel 設定する zskUioStatusLabel__。
     */
    public void setZskUioStatusLabel(List<LabelValueBean> zskUioStatusLabel) {
        zskUioStatusLabel__ = zskUioStatusLabel;
    }
    /**
     * @return zskUioBiko__ を戻します。
     */
    public String getZskUioBiko() {
        return zskUioBiko__;
    }
    /**
     * @param zskUioBiko 設定する zskUioBiko__。
     */
    public void setZskUioBiko(String zskUioBiko) {
        zskUioBiko__ = zskUioBiko;
    }
    /**
     * @return zskUioStatus__ を戻します。
     */
    public int getZskUioStatus() {
        return zskUioStatus__;
    }
    /**
     * @param zskUioStatus 設定する zskUioStatus__。
     */
    public void setZskUioStatus(int zskUioStatus) {
        zskUioStatus__ = zskUioStatus;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param req リクエスト
     * @return errors エラー
     */
    public ActionErrors validateChkMan001(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();

        //在席ステーテス
        GSValidateZaiseki.validateZskStatus(errors, zskUioStatus__, req);
        //コメント
        GSValidateZaiseki.validateZskBiko(errors, zskUioBiko__, req);

        return errors;
    }
}

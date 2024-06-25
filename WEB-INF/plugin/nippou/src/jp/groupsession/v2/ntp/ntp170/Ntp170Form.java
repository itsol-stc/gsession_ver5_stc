package jp.groupsession.v2.ntp.ntp170;

import java.util.List;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp120.Ntp120Form;

/**
 * <br>[機  能] 日報 活動分類一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp170Form extends Ntp120Form {
    /** 活動分類一覧 */
    private List<Ntp170KtBunruiDspModel> ntp170KtbunruiList__ = null;
    /** 活動分類SID */
    private int ntp170NkbSid__ = -1;
    /** 処理モード */
    private String ntp170ProcMode__ = GSConstNippou.CMD_ADD;
    /** チェックされている並び順 */
    private String ntp170sortBunrui__ = null;
    /** 並び替え対象のラベル */
    private String[] ntp170sortLabel__ = null;

    /**
     * @return ntp170NkbSid
     */
    public int getNtp170NkbSid() {
        return ntp170NkbSid__;
    }

    /**
     * @param ntp170NkbSid 設定する ntp170NkbSid
     */
    public void setNtp170NkbSid(int ntp170NkbSid) {
        ntp170NkbSid__ = ntp170NkbSid;
    }

    /**
     * @return ntp170ProcMode
     */
    public String getNtp170ProcMode() {
        return ntp170ProcMode__;
    }

    /**
     * @param ntp170ProcMode 設定する ntp170ProcMode
     */
    public void setNtp170ProcMode(String ntp170ProcMode) {
        ntp170ProcMode__ = ntp170ProcMode;
    }

    /**
     * <p>ntp170sortBunrui を取得します。
     * @return ntp170sortBunrui
     */
    public String getNtp170sortBunrui() {
        return ntp170sortBunrui__;
    }

    /**
     * <p>ntp170sortBunrui をセットします。
     * @param ntp170sortBunrui ntp170sortBunrui
     */
    public void setNtp170sortBunrui(String ntp170sortBunrui) {
        ntp170sortBunrui__ = ntp170sortBunrui;
    }

    /**
     * <p>ntp170sortLabel を取得します。
     * @return ntp170sortLabel
     */
    public String[] getNtp170sortLabel() {
        return ntp170sortLabel__;
    }

    /**
     * <p>ntp170sortLabel をセットします。
     * @param ntp170sortLabel ntp170sortLabel
     */
    public void setNtp170sortLabel(String[] ntp170sortLabel) {
        ntp170sortLabel__ = ntp170sortLabel;
    }

    /**
     * <p>ntp170KtbunruiList を取得します。
     * @return ntp170KtbunruiList
     */
    public List<Ntp170KtBunruiDspModel> getNtp170KtbunruiList() {
        return ntp170KtbunruiList__;
    }

    /**
     * <p>ntp170KtbunruiList をセットします。
     * @param ntp170KtbunruiList ntp170KtbunruiList
     */
    public void setNtp170KtbunruiList(
            List<Ntp170KtBunruiDspModel> ntp170KtbunruiList) {
        ntp170KtbunruiList__ = ntp170KtbunruiList;
    }
}
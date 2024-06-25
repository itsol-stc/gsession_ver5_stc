package jp.groupsession.v2.bmk.main010;

import java.util.List;

import jp.groupsession.v2.bmk.bmk010.Bmk010Form;
import jp.groupsession.v2.bmk.model.BmkBookmarkDataModel;


/**
 * <p>メイン画面個人ブックマーク表示用のフォーム
 * @author JTS
 */
public class BmkMain010Form extends Bmk010Form {
    /** 個人設定(メイン表示設定)**/
    private int dspFlg__ = -1;
    /** 個人ブックマーク一覧 */
    private List<BmkBookmarkDataModel> bmkMain010List__ = null;
    /** ブックマークトップ画面URL */
    private String bmkTopUrl__;
    /** メイン画面からの遷移フラグ */
    private int bmkBackMainFlg__ = 0;

    /**
     * @return bmkTopUrl__ を戻します。
     */
    public String getBmkTopUrl() {
        return bmkTopUrl__;
    }
    /**
     * @param bmkTopUrl 設定する bmkTopUrl__。
     */
    public void setBmkTopUrl(String bmkTopUrl) {
        bmkTopUrl__ = bmkTopUrl;
    }
    /**
     * <p>bmkMain010List を取得します。
     * @return bmkMain010List
     */
    public List<BmkBookmarkDataModel> getBmkMain010List() {
        return bmkMain010List__;
    }

    /**
     * <p>bmkMain010List をセットします。
     * @param bmkMain010List bmkMain010List
     */
    public void setBmkMain010List(List<BmkBookmarkDataModel> bmkMain010List) {
        bmkMain010List__ = bmkMain010List;
    }

    /**
     * <p>dspFlg を取得します。
     * @return dspFlg
     */
    public int getDspFlg() {
        return dspFlg__;
    }

    /**
     * <p>dspFlg をセットします。
     * @param dspFlg dspFlg
     */
    public void setDspFlg(int dspFlg) {
        dspFlg__ = dspFlg;
    }
    /**
     * <p>bmkBackMainFlg を取得します。
     * @return bmkBackMainFlg
     * @see jp.groupsession.v2.bmk.main020.BmkMain020ParamModel#bmkBackMainFlg__
     */
    public int getBmkBackMainFlg() {
        return bmkBackMainFlg__;
    }
    /**
     * <p>bmkBackMainFlg をセットします。
     * @param bmkBackMainFlg bmkBackMainFlg
     * @see jp.groupsession.v2.bmk.main020.BmkMain020ParamModel#bmkBackMainFlg__
     */
    public void setBmkBackMainFlg(int bmkBackMainFlg) {
        bmkBackMainFlg__ = bmkBackMainFlg;
    }
}

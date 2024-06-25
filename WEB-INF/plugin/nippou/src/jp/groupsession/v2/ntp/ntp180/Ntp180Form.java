package jp.groupsession.v2.ntp.ntp180;


import java.util.List;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp120.Ntp120Form;

/**
 * <br>[機  能] 日報 活動方法一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp180Form extends Ntp120Form {
    /** 活動方法一覧 */
    private List<Ntp180KtHouhouDspModel> ntp180KthouhouList__ = null;
    /** 活動方法SID */
    private int ntp180NkhSid__ = -1;
    /** 処理モード */
    private String ntp180ProcMode__ = GSConstNippou.CMD_ADD;
    /** チェックされている並び順 */
    private String ntp180sortHouhou__ = null;
    /** 並び替え対象のラベル */
    private String[] ntp180sortLabel__ = null;

    /**
     * @return ntp180NkhSid
     */
    public int getNtp180NkhSid() {
        return ntp180NkhSid__;
    }

    /**
     * @param ntp180NkhSid 設定する ntp180NkhSid
     */
    public void setNtp180NkhSid(int ntp180NkhSid) {
        ntp180NkhSid__ = ntp180NkhSid;
    }

    /**
     * @return ntp180ProcMode
     */
    public String getNtp180ProcMode() {
        return ntp180ProcMode__;
    }

    /**
     * @param ntp180ProcMode 設定する ntp180ProcMode
     */
    public void setNtp180ProcMode(String ntp180ProcMode) {
        ntp180ProcMode__ = ntp180ProcMode;
    }

    /**
     * <p>ntp180KthouhouList を取得します。
     * @return ntp180KthouhouList
     */
    public List<Ntp180KtHouhouDspModel> getNtp180KthouhouList() {
        return ntp180KthouhouList__;
    }

    /**
     * <p>ntp180KthouhouList をセットします。
     * @param ntp180KthouhouList ntp180KthouhouList
     */
    public void setNtp180KthouhouList(
            List<Ntp180KtHouhouDspModel> ntp180KthouhouList) {
        ntp180KthouhouList__ = ntp180KthouhouList;
    }

    /**
     * <p>ntp180sortHouhou を取得します。
     * @return ntp180sortHouhou
     */
    public String getNtp180sortHouhou() {
        return ntp180sortHouhou__;
    }

    /**
     * <p>ntp180sortHouhou をセットします。
     * @param ntp180sortHouhou ntp180sortHouhou
     */
    public void setNtp180sortHouhou(String ntp180sortHouhou) {
        ntp180sortHouhou__ = ntp180sortHouhou;
    }

    /**
     * <p>ntp180sortLabel を取得します。
     * @return ntp180sortLabel
     */
    public String[] getNtp180sortLabel() {
        return ntp180sortLabel__;
    }

    /**
     * <p>ntp180sortLabel をセットします。
     * @param ntp180sortLabel ntp180sortLabel
     */
    public void setNtp180sortLabel(String[] ntp180sortLabel) {
        ntp180sortLabel__ = ntp180sortLabel;
    }


}
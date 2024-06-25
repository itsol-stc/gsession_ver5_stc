package jp.groupsession.v2.ntp.ntp140;

import java.util.List;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp120.Ntp120Form;

/**
 * <br>[機  能] 日報 業種一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp140Form extends Ntp120Form {
    /** 業務一覧 */
    private List<Ntp140GyomuDspModel> ntp140GyomuList__ = null;
    /** 業務内容SID */
    private int ntp140NgySid__ = -1;
    /** 処理モード */
    private String ntp140ProcMode__ = GSConstNippou.CMD_ADD;
    /** チェックされている並び順 */
    private String ntp140sortGyomu__ = null;
    /** 並び替え対象のラベル */
    private String[] ntp140sortLabel__ = null;
    /**
     * <p>ntp140GyomuList を取得します。
     * @return ntp140GyomuList
     */
    public List<Ntp140GyomuDspModel> getNtp140GyomuList() {
        return ntp140GyomuList__;
    }
    /**
     * <p>ntp140GyomuList をセットします。
     * @param ntp140GyomuList ntp140GyomuList
     */
    public void setNtp140GyomuList(List<Ntp140GyomuDspModel> ntp140GyomuList) {
        ntp140GyomuList__ = ntp140GyomuList;
    }
    /**
     * <p>ntp140NgySid を取得します。
     * @return ntp140NgySid
     */
    public int getNtp140NgySid() {
        return ntp140NgySid__;
    }
    /**
     * <p>ntp140NgySid をセットします。
     * @param ntp140NgySid ntp140NgySid
     */
    public void setNtp140NgySid(int ntp140NgySid) {
        ntp140NgySid__ = ntp140NgySid;
    }
    /**
     * <p>ntp140ProcMode を取得します。
     * @return ntp140ProcMode
     */
    public String getNtp140ProcMode() {
        return ntp140ProcMode__;
    }
    /**
     * <p>ntp140ProcMode をセットします。
     * @param ntp140ProcMode ntp140ProcMode
     */
    public void setNtp140ProcMode(String ntp140ProcMode) {
        ntp140ProcMode__ = ntp140ProcMode;
    }
    /**
     * <p>ntp140sortGyomu を取得します。
     * @return ntp140sortGyomu
     */
    public String getNtp140sortGyomu() {
        return ntp140sortGyomu__;
    }
    /**
     * <p>ntp140sortGyomu をセットします。
     * @param ntp140sortGyomu ntp140sortGyomu
     */
    public void setNtp140sortGyomu(String ntp140sortGyomu) {
        ntp140sortGyomu__ = ntp140sortGyomu;
    }
    /**
     * <p>ntp140sortLabel を取得します。
     * @return ntp140sortLabel
     */
    public String[] getNtp140sortLabel() {
        return ntp140sortLabel__;
    }
    /**
     * <p>ntp140sortLabel をセットします。
     * @param ntp140sortLabel ntp140sortLabel
     */
    public void setNtp140sortLabel(String[] ntp140sortLabel) {
        ntp140sortLabel__ = ntp140sortLabel;
    }

}
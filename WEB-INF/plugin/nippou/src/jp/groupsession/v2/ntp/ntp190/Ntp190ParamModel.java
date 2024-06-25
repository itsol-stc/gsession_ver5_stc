package jp.groupsession.v2.ntp.ntp190;

import java.util.List;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp120.Ntp120ParamModel;

/**
 * <br>[機  能] 日報 顧客源泉一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp190ParamModel extends Ntp120ParamModel {
    /** コンタクト一覧 */
    private List<Ntp190ContactDspModel> ntp190ContactList__ = null;
    /** コンタクトSID */
    private int ntp190NcnSid__ = -1;
    /** 処理モード */
    private String ntp190ProcMode__ = GSConstNippou.CMD_ADD;

    /** チェックされている並び順 */
    private String ntp190sortContact__ = null;
    /** 並び替え対象のラベル */
    private String[] ntp190sortLabel__ = null;
    /**
     * <p>ntp190ContactList を取得します。
     * @return ntp190ContactList
     */
    public List<Ntp190ContactDspModel> getNtp190ContactList() {
        return ntp190ContactList__;
    }
    /**
     * <p>ntp190ContactList をセットします。
     * @param ntp190ContactList ntp190ContactList
     */
    public void setNtp190ContactList(List<Ntp190ContactDspModel> ntp190ContactList) {
        ntp190ContactList__ = ntp190ContactList;
    }
    /**
     * <p>ntp190NcnSid を取得します。
     * @return ntp190NcnSid
     */
    public int getNtp190NcnSid() {
        return ntp190NcnSid__;
    }
    /**
     * <p>ntp190NcnSid をセットします。
     * @param ntp190NcnSid ntp190NcnSid
     */
    public void setNtp190NcnSid(int ntp190NcnSid) {
        ntp190NcnSid__ = ntp190NcnSid;
    }
    /**
     * <p>ntp190ProcMode を取得します。
     * @return ntp190ProcMode
     */
    public String getNtp190ProcMode() {
        return ntp190ProcMode__;
    }
    /**
     * <p>ntp190ProcMode をセットします。
     * @param ntp190ProcMode ntp190ProcMode
     */
    public void setNtp190ProcMode(String ntp190ProcMode) {
        ntp190ProcMode__ = ntp190ProcMode;
    }
    /**
     * <p>ntp190sortContact を取得します。
     * @return ntp190sortContact
     */
    public String getNtp190sortContact() {
        return ntp190sortContact__;
    }
    /**
     * <p>ntp190sortContact をセットします。
     * @param ntp190sortContact ntp190sortContact
     */
    public void setNtp190sortContact(String ntp190sortContact) {
        ntp190sortContact__ = ntp190sortContact;
    }
    /**
     * <p>ntp190sortLabel を取得します。
     * @return ntp190sortLabel
     */
    public String[] getNtp190sortLabel() {
        return ntp190sortLabel__;
    }
    /**
     * <p>ntp190sortLabel をセットします。
     * @param ntp190sortLabel ntp190sortLabel
     */
    public void setNtp190sortLabel(String[] ntp190sortLabel) {
        ntp190sortLabel__ = ntp190sortLabel;
    }

}
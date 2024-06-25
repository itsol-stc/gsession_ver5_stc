package jp.groupsession.v2.ntp.ntp230;

import java.util.List;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp120.Ntp120Form;

/**
 * <br>[機  能] 日報 目標一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp230Form extends Ntp120Form {
    /** 目標一覧 */
    private List<Ntp230TargetDspModel> ntp230TargetList__ = null;
    /** 目標SID */
    private int ntp230NtgSid__ = -1;
    /** 処理モード */
    private String ntp230ProcMode__ = GSConstNippou.CMD_ADD;

    /** チェックされている並び順 */
    private String ntp230sortTarget__ = null;
    /** 並び替え対象のラベル */
    private String[] ntp230sortLabel__ = null;
    /**
     * <p>ntp230TargetList を取得します。
     * @return ntp230TargetList
     */
    public List<Ntp230TargetDspModel> getNtp230TargetList() {
        return ntp230TargetList__;
    }
    /**
     * <p>ntp230TargetList をセットします。
     * @param ntp230TargetList ntp230TargetList
     */
    public void setNtp230TargetList(List<Ntp230TargetDspModel> ntp230TargetList) {
        ntp230TargetList__ = ntp230TargetList;
    }
    /**
     * <p>ntp230NtgSid を取得します。
     * @return ntp230NtgSid
     */
    public int getNtp230NtgSid() {
        return ntp230NtgSid__;
    }
    /**
     * <p>ntp230NtgSid をセットします。
     * @param ntp230NtgSid ntp230NtgSid
     */
    public void setNtp230NtgSid(int ntp230NtgSid) {
        ntp230NtgSid__ = ntp230NtgSid;
    }
    /**
     * <p>ntp230ProcMode を取得します。
     * @return ntp230ProcMode
     */
    public String getNtp230ProcMode() {
        return ntp230ProcMode__;
    }
    /**
     * <p>ntp230ProcMode をセットします。
     * @param ntp230ProcMode ntp230ProcMode
     */
    public void setNtp230ProcMode(String ntp230ProcMode) {
        ntp230ProcMode__ = ntp230ProcMode;
    }
    /**
     * <p>ntp230sortTarget を取得します。
     * @return ntp230sortTarget
     */
    public String getNtp230sortTarget() {
        return ntp230sortTarget__;
    }
    /**
     * <p>ntp230sortTarget をセットします。
     * @param ntp230sortTarget ntp230sortTarget
     */
    public void setNtp230sortTarget(String ntp230sortTarget) {
        ntp230sortTarget__ = ntp230sortTarget;
    }
    /**
     * <p>ntp230sortLabel を取得します。
     * @return ntp230sortLabel
     */
    public String[] getNtp230sortLabel() {
        return ntp230sortLabel__;
    }
    /**
     * <p>ntp230sortLabel をセットします。
     * @param ntp230sortLabel ntp230sortLabel
     */
    public void setNtp230sortLabel(String[] ntp230sortLabel) {
        ntp230sortLabel__ = ntp230sortLabel;
    }
}
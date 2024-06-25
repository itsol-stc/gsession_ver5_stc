package jp.groupsession.v2.ntp.ntp150;

import java.util.List;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp120.Ntp120Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 プロセス一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp150Form extends Ntp120Form {
    /** プロセス一覧 */
    private List<Ntp150ProcessModel> ntp150ProcessList__ = null;
    /** プロセスSID */
    private int ntp150NgpSid__ = -1;
    /** 業務内容SID */
    private int ntp150NgySid__ = -1;
    /** 処理モード */
    private String ntp150ProcMode__ = GSConstNippou.CMD_ADD;
    /** 業務内容SID(コンボ表示用) */
    private String ntp150DispNgySid__ = "all";
    /** 業務一覧 */
    private List<LabelValueBean> ntp150GyomuList__ = null;
    /** チェックされている並び順 */
    private String ntp150SortProcess__ = null;
    /** 並び替え対象のラベル */
    private String[] ntp150SortLabel__ = null;

    /**
     * @return ntp150ProcessList
     */
    public List<Ntp150ProcessModel> getNtp150ProcessList() {
        return ntp150ProcessList__;
    }

    /**
     * @param ntp150ProcessList 設定する ntp150ProcessList
     */
    public void setNtp150ProcessList(List<Ntp150ProcessModel> ntp150ProcessList) {
        ntp150ProcessList__ = ntp150ProcessList;
    }

    /**
     * @return ntp150NgpSid
     */
    public int getNtp150NgpSid() {
        return ntp150NgpSid__;
    }

    /**
     * @param ntp150NgpSid 設定する ntp150NgpSid
     */
    public void setNtp150NgpSid(int ntp150NgpSid) {
        ntp150NgpSid__ = ntp150NgpSid;
    }

    /**
     * @return ntp150NgySid
     */
    public int getNtp150NgySid() {
        return ntp150NgySid__;
    }

    /**
     * @param ntp150NgySid 設定する ntp150NgySid
     */
    public void setNtp150NgySid(int ntp150NgySid) {
        ntp150NgySid__ = ntp150NgySid;
    }

    /**
     * @return ntp150ProcMode
     */
    public String getNtp150ProcMode() {
        return ntp150ProcMode__;
    }

    /**
     * @param ntp150ProcMode 設定する ntp150ProcMode
     */
    public void setNtp150ProcMode(String ntp150ProcMode) {
        ntp150ProcMode__ = ntp150ProcMode;
    }

    /**
     * <p>ntp150GyomuList を取得します。
     * @return ntp150GyomuList
     */
    public List<LabelValueBean> getNtp150GyomuList() {
        return ntp150GyomuList__;
    }

    /**
     * <p>ntp150GyomuList をセットします。
     * @param ntp150GyomuList ntp150GyomuList
     */
    public void setNtp150GyomuList(List<LabelValueBean> ntp150GyomuList) {
        ntp150GyomuList__ = ntp150GyomuList;
    }

    /**
     * <p>ntp150DispNgySid を取得します。
     * @return ntp150DispNgySid
     */
    public String getNtp150DispNgySid() {
        return ntp150DispNgySid__;
    }

    /**
     * <p>ntp150DispNgySid をセットします。
     * @param ntp150DispNgySid ntp150DispNgySid
     */
    public void setNtp150DispNgySid(String ntp150DispNgySid) {
        ntp150DispNgySid__ = ntp150DispNgySid;
    }

    /**
     * <p>ntp150SortProcess を取得します。
     * @return ntp150SortProcess
     */
    public String getNtp150SortProcess() {
        return ntp150SortProcess__;
    }

    /**
     * <p>ntp150SortProcess をセットします。
     * @param ntp150SortProcess ntp150SortProcess
     */
    public void setNtp150SortProcess(String ntp150SortProcess) {
        ntp150SortProcess__ = ntp150SortProcess;
    }

    /**
     * <p>ntp150SortLabel を取得します。
     * @return ntp150SortLabel
     */
    public String[] getNtp150SortLabel() {
        return ntp150SortLabel__;
    }

    /**
     * <p>ntp150SortLabel をセットします。
     * @param ntp150SortLabel ntp150SortLabel
     */
    public void setNtp150SortLabel(String[] ntp150SortLabel) {
        ntp150SortLabel__ = ntp150SortLabel;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setNtp150HiddenParam(Cmn999Form msgForm) {

        msgForm.addHiddenParam("ntp150DispNgySid", ntp150DispNgySid__);
    }
}
package jp.groupsession.v2.ntp.ntp089kn;


import java.util.List;

import jp.groupsession.v2.ntp.ntp089.Ntp089Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] 日報 テンプレート登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp089knForm extends Ntp089Form {
    /** 備考(表示用) */
    private String ntp089knBiko__ = null;
    /** 役職名(表示用) */
    private String ntp089knpositionName__ = null;

    /** 制限対象一覧 */
    private List<UsrLabelValueBean> ntp089knSubjectList__  = null;
    /** 許可ユーザ 閲覧 一覧 */
    private List<UsrLabelValueBean> ntp089knAccessList__  = null;

    /**
     * <p>ntp089knBiko を取得します。
     * @return ntp089knBiko
     */
    public String getntp089knBiko() {
        return ntp089knBiko__;
    }
    /**
     * <p>ntp089knBiko をセットします。
     * @param ntp089knBiko ntp089knBiko
     */
    public void setntp089knBiko(String ntp089knBiko) {
        ntp089knBiko__ = ntp089knBiko;
    }
    /**
     * <p>ntp089knpositionName を取得します。
     * @return ntp089knpositionName
     */
    public String getntp089knpositionName() {
        return ntp089knpositionName__;
    }
    /**
     * <p>ntp089knpositionName をセットします。
     * @param ntp089knpositionName ntp089knpositionName
     */
    public void setntp089knpositionName(String ntp089knpositionName) {
        ntp089knpositionName__ = ntp089knpositionName;
    }
    /**
     * <p>ntp089knSubjectList を取得します。
     * @return ntp089knSubjectList
     * @see jp.groupsession.v2.ntp.ntp089kn.Ntp089knForm#ntp089knSubjectList__
     */
    public List<UsrLabelValueBean> getNtp089knSubjectList() {
        return ntp089knSubjectList__;
    }
    /**
     * <p>ntp089knSubjectList をセットします。
     * @param ntp089knSubjectList ntp089knSubjectList
     * @see jp.groupsession.v2.ntp.ntp089kn.Ntp089knForm#ntp089knSubjectList__
     */
    public void setNtp089knSubjectList(
            List<UsrLabelValueBean> ntp089knSubjectList) {
        ntp089knSubjectList__ = ntp089knSubjectList;
    }
    /**
     * <p>ntp089knAccessList を取得します。
     * @return ntp089knAccessList
     * @see jp.groupsession.v2.ntp.ntp089kn.Ntp089knForm#ntp089knAccessList__
     */
    public List<UsrLabelValueBean> getNtp089knAccessList() {
        return ntp089knAccessList__;
    }
    /**
     * <p>ntp089knAccessList をセットします。
     * @param ntp089knAccessList ntp089knAccessList
     * @see jp.groupsession.v2.ntp.ntp089kn.Ntp089knForm#ntp089knAccessList__
     */
    public void setNtp089knAccessList(List<UsrLabelValueBean> ntp089knAccessList) {
        ntp089knAccessList__ = ntp089knAccessList;
    }
}

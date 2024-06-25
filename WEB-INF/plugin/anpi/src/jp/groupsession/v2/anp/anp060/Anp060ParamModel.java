package jp.groupsession.v2.anp.anp060;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp140.Anp140ParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

/**
 * <br>[機  能] 安否確認メッセージ配信画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp060ParamModel extends Anp140ParamModel {

    /** 画面モード */
    private String anp060ProcMode__ = GSConstAnpi.MSG_HAISIN_MODE_NEW;

    /** メイン表示 */
    private int anp060main__ = GSConstAnpi.APH_VIEW_MAIN_ALL;
    /** 件名 */
    private String anp060Subject__;
    /** 本文1 */
    private String anp060Text1__;
    /** 本文2 */
    private String anp060Text2__;
    /** テスト送信アドレス */
    private String anp060TestAdr__;
    /** 配信者 */
    private String anp060RegistName__;

    /** 定型メッセージ選択SID */
    private String anp060SelectMtempSid__;
    /** 送信先グループコンボボックス選択SID */
    private String anp060SelectGroupSid__ = null;
    /** 送信先ユーザSIDリスト */
    private String[] anp060SenderList__ = null;

    /** 定型メッセージコンボボックスリスト */
    private List <LabelValueBean> anp060MtempLabel__ = null;

    /** 送信先ユーザ選択 UI */
    private UserGroupSelector anp060SenderListUI__ = null;

    /** メッセージ本文（固定部） */
    private String anp060MessageBody__;

    /** 表示位置フラグ（1:送信先） */
    private String anp060ScrollFlg__;

    /** 「コピーして新規作成」時の元となる安否SID */
    private String anp060CopyAnpiSid__;

    /**
     * <p>anp060main を取得します。
     * @return anp060main
     */
    public int getAnp060main() {
        return anp060main__;
    }

    /**
     * <p>anp060main をセットします。
     * @param anp060main anp060main
     */
    public void setAnp060main(int anp060main) {
        anp060main__ = anp060main;
    }

    /**
     * <p>anp060ProcMode を取得します。
     * @return anp060ProcMode
     */
    public String getAnp060ProcMode() {
        return anp060ProcMode__;
    }

    /**
     * <p>anp060ProcMode をセットします。
     * @param anp060ProcMode anp060ProcMode
     */
    public void setAnp060ProcMode(String anp060ProcMode) {
        anp060ProcMode__ = anp060ProcMode;
    }

    /**
     * <p>anp060Subject を取得します。
     * @return anp060Subject
     */
    public String getAnp060Subject() {
        return anp060Subject__;
    }

    /**
     * <p>anp060Subject をセットします。
     * @param anp060Subject anp060Subject
     */
    public void setAnp060Subject(String anp060Subject) {
        anp060Subject__ = anp060Subject;
    }

    /**
     * <p>anp060Text1 を取得します。
     * @return anp060Text1
     */
    public String getAnp060Text1() {
        return anp060Text1__;
    }

    /**
     * <p>anp060Text1 をセットします。
     * @param anp060Text1 anp060Text1
     */
    public void setAnp060Text1(String anp060Text1) {
        anp060Text1__ = anp060Text1;
    }

    /**
     * <p>anp060Text2 を取得します。
     * @return anp060Text2
     */
    public String getAnp060Text2() {
        return anp060Text2__;
    }

    /**
     * <p>anp060Text2 をセットします。
     * @param anp060Text2 anp060Text2
     */
    public void setAnp060Text2(String anp060Text2) {
        anp060Text2__ = anp060Text2;
    }

    /**
     * <p>anp060TestAdr を取得します。
     * @return anp060TestAdr
     */
    public String getAnp060TestAdr() {
        return anp060TestAdr__;
    }

    /**
     * <p>anp060TestAdr をセットします。
     * @param anp060TestAdr anp060TestAdr
     */
    public void setAnp060TestAdr(String anp060TestAdr) {
        anp060TestAdr__ = anp060TestAdr;
    }

    /**
     * <p>anp060RegistName を取得します。
     * @return anp060RegistName
     */
    public String getAnp060RegistName() {
        return anp060RegistName__;
    }

    /**
     * <p>anp060RegistName をセットします。
     * @param anp060RegistName anp060RegistName
     */
    public void setAnp060RegistName(String anp060RegistName) {
        anp060RegistName__ = anp060RegistName;
    }

    /**
     * <p>anp060SelectMtempSid を取得します。
     * @return anp060SelectMtempSid
     */
    public String getAnp060SelectMtempSid() {
        return anp060SelectMtempSid__;
    }

    /**
     * <p>anp060SelectMtempSid をセットします。
     * @param anp060SelectMtempSid anp060SelectMtempSid
     */
    public void setAnp060SelectMtempSid(String anp060SelectMtempSid) {
        anp060SelectMtempSid__ = anp060SelectMtempSid;
    }

    /**
     * <p>anp060SelectGroupSid を取得します。
     * @return anp060SelectGroupSid
     */
    public String getAnp060SelectGroupSid() {
        return anp060SelectGroupSid__;
    }

    /**
     * <p>anp060SelectGroupSid をセットします。
     * @param anp060SelectGroupSid anp060SelectGroupSid
     */
    public void setAnp060SelectGroupSid(String anp060SelectGroupSid) {
        anp060SelectGroupSid__ = anp060SelectGroupSid;
    }

    /**
     * <p>anp060SenderList を取得します。
     * @return anp060SenderList
     */
    public String[] getAnp060SenderList() {
        return anp060SenderList__;
    }

    /**
     * <p>anp060SenderList をセットします。
     * @param anp060SenderList anp060SenderList
     */
    public void setAnp060SenderList(String[] anp060SenderList) {
        anp060SenderList__ = anp060SenderList;
    }

    /**
     * <p>anp060MtempLabel を取得します。
     * @return anp060MtempLabel
     */
    public List<LabelValueBean> getAnp060MtempLabel() {
        return anp060MtempLabel__;
    }

    /**
     * <p>anp060MtempLabel をセットします。
     * @param anp060MtempLabel anp060MtempLabel
     */
    public void setAnp060MtempLabel(List<LabelValueBean> anp060MtempLabel) {
        anp060MtempLabel__ = anp060MtempLabel;
    }

    /**
     * <p>anp060SenderListUI を取得します。
     * @return anp060SenderListUI
     */
    public UserGroupSelector getAnp060SenderListUI() {
        return anp060SenderListUI__;
    }

    /**
     * <p>anp060SenderListUI をセットします。
     * @param anp060SenderListUI anp060SenderListUI
     */
    public void setAnp060SenderListUI(UserGroupSelector anp060SenderListUI) {
        anp060SenderListUI__ = anp060SenderListUI;
    }

    /**
     * <p>anp060MessageBody を取得します。
     * @return anp060MessageBody
     */
    public String getAnp060MessageBody() {
        return anp060MessageBody__;
    }

    /**
     * <p>anp060MessageBody をセットします。
     * @param anp060MessageBody anp060MessageBody
     */
    public void setAnp060MessageBody(String anp060MessageBody) {
        anp060MessageBody__ = anp060MessageBody;
    }

    /**
     * <p>anp060ScrollFlg を取得します。
     * @return anp060ScrollFlg
     */
    public String getAnp060ScrollFlg() {
        return anp060ScrollFlg__;
    }

    /**
     * <p>anp060ScrollFlg をセットします。
     * @param anp060ScrollFlg anp060ScrollFlg
     */
    public void setAnp060ScrollFlg(String anp060ScrollFlg) {
        anp060ScrollFlg__ = anp060ScrollFlg;
    }

    /**
     * <p>anp060CopyAnpiSid を取得します。
     * @return anp060CopyAnpiSid
     */
    public String getAnp060CopyAnpiSid() {
        return anp060CopyAnpiSid__;
    }
    /**
     * <p>anp060CopyAnpiSid をセットします。
     * @param anp060CopyAnpiSid anp060CopyAnpiSid
     * @see jp.groupsession.v2.anp.anp060.Anp060ParamModel#anp060CopyAnpiSid__
     */
    public void setAnp060CopyAnpiSid(String anp060CopyAnpiSid) {
        anp060CopyAnpiSid__ = anp060CopyAnpiSid;
    }

}
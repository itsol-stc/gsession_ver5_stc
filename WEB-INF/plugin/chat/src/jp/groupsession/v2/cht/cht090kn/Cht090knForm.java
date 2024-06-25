package jp.groupsession.v2.cht.cht090kn;


import java.util.ArrayList;

import jp.groupsession.v2.cht.cht090.Cht090Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] チャット 特例アクセス登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht090knForm extends Cht090Form {
    /** 備考(表示用) */
    private String cht090knBiko__ = null;
    /** 役職名(表示用) */
    private String cht090knpositionName__ = null;

    /** 制限対象ユーザ・グループ(表示用) */
    ArrayList<UsrLabelValueBean> cht090knsubjectList__ = null;
    /** アクセス許可 ユーザ・グループ(表示用) */
    ArrayList<UsrLabelValueBean> cht090knaccessList__ = null;

    /**
     * <p>cht090knBiko を取得します。
     * @return cht090knBiko
     */
    public String getcht090knBiko() {
        return cht090knBiko__;
    }
    /**
     * <p>cht090knBiko をセットします。
     * @param cht090knBiko cht090knBiko
     */
    public void setcht090knBiko(String cht090knBiko) {
        cht090knBiko__ = cht090knBiko;
    }
    /**
     * <p>cht090knpositionName を取得します。
     * @return cht090knpositionName
     */
    public String getcht090knpositionName() {
        return cht090knpositionName__;
    }
    /**
     * <p>cht090knpositionName をセットします。
     * @param cht090knpositionName cht090knpositionName
     */
    public void setcht090knpositionName(String cht090knpositionName) {
        cht090knpositionName__ = cht090knpositionName;
    }
    /**
     * <p>cht090knsubjectList を取得します。
     * @return cht090knsubjectList
     * @see jp.groupsession.v2.cht.cht090kn.Cht090knForm#cht090knsubjectList__
     */
    public ArrayList<UsrLabelValueBean> getCht090knsubjectList() {
        return cht090knsubjectList__;
    }
    /**
     * <p>cht090knsubjectList をセットします。
     * @param cht090knsubjectList cht090knsubjectList
     * @see jp.groupsession.v2.cht.cht090kn.Cht090knForm#cht090knsubjectList__
     */
    public void setCht090knsubjectList(
            ArrayList<UsrLabelValueBean> cht090knsubjectList) {
        cht090knsubjectList__ = cht090knsubjectList;
    }
    /**
     * <p>cht090knaccessList を取得します。
     * @return cht090knaccessList
     * @see jp.groupsession.v2.cht.cht090kn.Cht090knForm#cht090knaccessList__
     */
    public ArrayList<UsrLabelValueBean> getCht090knaccessList() {
        return cht090knaccessList__;
    }
    /**
     * <p>cht090knaccessList をセットします。
     * @param cht090knaccessList cht090knaccessList
     * @see jp.groupsession.v2.cht.cht090kn.Cht090knForm#cht090knaccessList__
     */
    public void setCht090knaccessList(
            ArrayList<UsrLabelValueBean> cht090knaccessList) {
        cht090knaccessList__ = cht090knaccessList;
    }
}

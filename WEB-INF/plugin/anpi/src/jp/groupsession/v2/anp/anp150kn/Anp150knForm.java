package jp.groupsession.v2.anp.anp150kn;

import java.util.ArrayList;

import jp.groupsession.v2.anp.anp150.Anp150Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 安否確認 管理者設定 緊急連絡先一括設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp150knForm extends Anp150Form {


    /** 表示用 対象一覧 */
    private ArrayList<UsrLabelValueBean> anp150knOkUsrList__ = new ArrayList<UsrLabelValueBean>();
    /** 表示用 対象ユーザ数 */
    private int anp150knOkUsrNum__;
    /** 表示用 対象外一覧 */
    private ArrayList<UsrLabelValueBean> anp150knCutUsrList__ = new ArrayList<UsrLabelValueBean>();
    /** 表示用 対象外ユーザ数 */
    private int anp150knCutUsrNum__;
    /**
     * <p>anp150knOkUsrList を取得します。
     * @return anp150knOkUsrList
     */
    public ArrayList<UsrLabelValueBean> getAnp150knOkUsrList() {
        return anp150knOkUsrList__;
    }
    /**
     * <p>anp150knOkUsrList をセットします。
     * @param anp150knOkUsrList anp150knOkUsrList
     */
    public void setAnp150knOkUsrList(ArrayList<UsrLabelValueBean> anp150knOkUsrList) {
        anp150knOkUsrList__ = anp150knOkUsrList;
    }
    /**
     * <p>anp150knOkUsrNum を取得します。
     * @return anp150knOkUsrNum
     */
    public int getAnp150knOkUsrNum() {
        return anp150knOkUsrNum__;
    }
    /**
     * <p>anp150knOkUsrNum をセットします。
     * @param anp150knOkUsrNum anp150knOkUsrNum
     */
    public void setAnp150knOkUsrNum(int anp150knOkUsrNum) {
        anp150knOkUsrNum__ = anp150knOkUsrNum;
    }
    /**
     * <p>anp150knCutUsrList を取得します。
     * @return anp150knCutUsrList
     */
    public ArrayList<UsrLabelValueBean> getAnp150knCutUsrList() {
        return anp150knCutUsrList__;
    }
    /**
     * <p>anp150knCutUsrList をセットします。
     * @param anp150knCutUsrList anp150knCutUsrList
     */
    public void setAnp150knCutUsrList(ArrayList<UsrLabelValueBean> anp150knCutUsrList) {
        anp150knCutUsrList__ = anp150knCutUsrList;
    }
    /**
     * <p>anp150knCutUsrNum を取得します。
     * @return anp150knCutUsrNum
     */
    public int getAnp150knCutUsrNum() {
        return anp150knCutUsrNum__;
    }
    /**
     * <p>anp150knCutUsrNum をセットします。
     * @param anp150knCutUsrNum anp150knCutUsrNum
     */
    public void setAnp150knCutUsrNum(int anp150knCutUsrNum) {
        anp150knCutUsrNum__ = anp150knCutUsrNum;
    }

}

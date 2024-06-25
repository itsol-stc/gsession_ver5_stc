package jp.groupsession.v2.ntp.ntp020;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp010.Ntp010Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 日報 月間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp020Form extends Ntp010Form {


    //表示内容
    /** ヘッダー表示用年月 */
    private String ntp020StrDspDate__ = null;
    /** ヘッダーユーザ名称 */
    private String ntp020StrUserName__ = null;
    /** 日報リスト */
    @SuppressWarnings("all")
    private ArrayList ntp020NippouList__ = null;

    /** 選択ユーザ */
    private String ntp020SelectUsrSid__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> ntp020UsrLabelList__ = null;
    /** 自動リロード時間 */
    private int ntp020Reload__ = GSConstNippou.AUTO_RELOAD_10MIN;
    /** 一括確認ボタン使用可能区分 */
    private int ntp020IkkatuBttn__ = GSConstNippou.IKKATU_ENABLE_OFF;
    /**
     * <p>ntp020StrDspDate を取得します。
     * @return ntp020StrDspDate
     */
    public String getNtp020StrDspDate() {
        return ntp020StrDspDate__;
    }
    /**
     * <p>ntp020StrDspDate をセットします。
     * @param ntp020StrDspDate ntp020StrDspDate
     */
    public void setNtp020StrDspDate(String ntp020StrDspDate) {
        ntp020StrDspDate__ = ntp020StrDspDate;
    }
    /**
     * <p>ntp020StrUserName を取得します。
     * @return ntp020StrUserName
     */
    public String getNtp020StrUserName() {
        return ntp020StrUserName__;
    }
    /**
     * <p>ntp020StrUserName をセットします。
     * @param ntp020StrUserName ntp020StrUserName
     */
    public void setNtp020StrUserName(String ntp020StrUserName) {
        ntp020StrUserName__ = ntp020StrUserName;
    }
    /**
     * <p>ntp020NippouList を取得します。
     * @return ntp020NippouList
     */
    @SuppressWarnings({"all", "unchecked" })
    public ArrayList getNtp020NippouList() {
        return ntp020NippouList__;
    }
    /**
     * <p>ntp020NippouList をセットします。
     * @param ntp020NippouList ntp020NippouList
     */
    @SuppressWarnings({"all", "unchecked" })
    public void setNtp020NippouList(ArrayList ntp020NippouList) {
        ntp020NippouList__ = ntp020NippouList;
    }
    /**
     * <p>ntp020SelectUsrSid を取得します。
     * @return ntp020SelectUsrSid
     */
    public String getNtp020SelectUsrSid() {
        return ntp020SelectUsrSid__;
    }
    /**
     * <p>ntp020SelectUsrSid をセットします。
     * @param ntp020SelectUsrSid ntp020SelectUsrSid
     */
    public void setNtp020SelectUsrSid(String ntp020SelectUsrSid) {
        ntp020SelectUsrSid__ = ntp020SelectUsrSid;
    }
    /**
     * <p>ntp020UsrLabelList を取得します。
     * @return ntp020UsrLabelList
     */
    public List<UsrLabelValueBean> getNtp020UsrLabelList() {
        return ntp020UsrLabelList__;
    }
    /**
     * <p>ntp020UsrLabelList をセットします。
     * @param ntp020UsrLabelList ntp020UsrLabelList
     */
    public void setNtp020UsrLabelList(List<UsrLabelValueBean> ntp020UsrLabelList) {
        ntp020UsrLabelList__ = ntp020UsrLabelList;
    }
    /**
     * <p>ntp020Reload を取得します。
     * @return ntp020Reload
     */
    public int getNtp020Reload() {
        return ntp020Reload__;
    }
    /**
     * <p>ntp020Reload をセットします。
     * @param ntp020Reload ntp020Reload
     */
    public void setNtp020Reload(int ntp020Reload) {
        ntp020Reload__ = ntp020Reload;
    }
    /**
     * <p>ntp020IkkatuBttn を取得します。
     * @return ntp020IkkatuBttn
     */
    public int getNtp020IkkatuBttn() {
        return ntp020IkkatuBttn__;
    }
    /**
     * <p>ntp020IkkatuBttn をセットします。
     * @param ntp020IkkatuBttn ntp020IkkatuBttn
     */
    public void setNtp020IkkatuBttn(int ntp020IkkatuBttn) {
        ntp020IkkatuBttn__ = ntp020IkkatuBttn;
    }
}

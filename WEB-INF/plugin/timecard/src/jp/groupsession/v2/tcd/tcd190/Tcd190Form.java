package jp.groupsession.v2.tcd.tcd190;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.tcd.tcd030.Tcd030Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] タイムカード管理者設定 有休日数一覧画面のフォームクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd190Form extends Tcd030Form {

    /** 表示年度 */
    private int tcd190nendo__;
    /** 表示グループ */
    private String tcd190group__ = null;
    
    /** ソートキー */
    private int tcd190sortKey__;
    /** 並び順 */
    private int tcd190order__;
    /** 表示ページ */
    private int tcd190page__;
    
    /** モード */
    private int tcd200mode__;
    /** ユーザ */
    private int tcd200Name__;
    /** 年度 */
    private int tcd200Nendo__;
    /** グループ */
    private int tcd200Group__;
    
    /** 表示年度コンボ */
    private List<LabelValueBean> tcdNendoList__ = null;
    
    /** ページングコンボ */
    private List<LabelValueBean> tcd190pageList__ = null;
    
    /** 有休情報一覧 */
    private List<Tcd190YukyuModel> tcd190YukyuModelList__ = null;
    
    /** グループコンボ */
    private List<LabelValueBean> tcdGroupList__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> tcdUserList__ = null;
    
    /**
     * <p>tcd190nendo を取得します。
     * @return tcd190nendo
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190nendo__
     */
    public int getTcd190nendo() {
        return tcd190nendo__;
    }
    /**
     * <p>tcd190nendo をセットします。
     * @param tcd190nendo tcd190nendo
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190nendo__
     */
    public void setTcd190nendo(int tcd190nendo) {
        tcd190nendo__ = tcd190nendo;
    }
    /**
     * <p>tcd190group を取得します。
     * @return tcd190group
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190group__
     */
    public String getTcd190group() {
        return tcd190group__;
    }
    /**
     * <p>tcd190group をセットします。
     * @param tcd190group tcd190group
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190group__
     */
    public void setTcd190group(String tcd190group) {
        tcd190group__ = tcd190group;
    }
    /**
     * <p>tcd190sortKey を取得します。
     * @return tcd190sortKey
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190sortKey__
     */
    public int getTcd190sortKey() {
        return tcd190sortKey__;
    }
    /**
     * <p>tcd190sortKey をセットします。
     * @param tcd190sortKey tcd190sortKey
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190sortKey__
     */
    public void setTcd190sortKey(int tcd190sortKey) {
        tcd190sortKey__ = tcd190sortKey;
    }
    /**
     * <p>tcd190order を取得します。
     * @return tcd190order
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190order__
     */
    public int getTcd190order() {
        return tcd190order__;
    }
    /**
     * <p>tcd190order をセットします。
     * @param tcd190order tcd190order
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190order__
     */
    public void setTcd190order(int tcd190order) {
        tcd190order__ = tcd190order;
    }
    /**
     * <p>tcd190page を取得します。
     * @return tcd190page
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190page__
     */
    public int getTcd190page() {
        return tcd190page__;
    }
    /**
     * <p>tcd190page をセットします。
     * @param tcd190page tcd190page
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190page__
     */
    public void setTcd190page(int tcd190page) {
        tcd190page__ = tcd190page;
    }
    /**
     * <p>tcd200mode を取得します。
     * @return tcd200mode
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd200mode__
     */
    public int getTcd200mode() {
        return tcd200mode__;
    }
    /**
     * <p>tcd200mode をセットします。
     * @param tcd200mode tcd200mode
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd200mode__
     */
    public void setTcd200mode(int tcd200mode) {
        tcd200mode__ = tcd200mode;
    }
    /**
     * <p>tcd200Name を取得します。
     * @return tcd200Name
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd200Name__
     */
    public int getTcd200Name() {
        return tcd200Name__;
    }
    /**
     * <p>tcd200Name をセットします。
     * @param tcd200Name tcd200Name
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd200Name__
     */
    public void setTcd200Name(int tcd200Name) {
        tcd200Name__ = tcd200Name;
    }
    /**
     * <p>tcd200Nendo を取得します。
     * @return tcd200Nendo
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd200Nendo__
     */
    public int getTcd200Nendo() {
        return tcd200Nendo__;
    }
    /**
     * <p>tcd200Nendo をセットします。
     * @param tcd200Nendo tcd200Nendo
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd200Nendo__
     */
    public void setTcd200Nendo(int tcd200Nendo) {
        tcd200Nendo__ = tcd200Nendo;
    }
    /**
     * <p>tcdNendoList を取得します。
     * @return tcdNendoList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcdNendoList__
     */
    public List<LabelValueBean> getTcdNendoList() {
        return tcdNendoList__;
    }
    /**
     * <p>tcdNendoList をセットします。
     * @param tcdNendoList tcdNendoList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcdNendoList__
     */
    public void setTcdNendoList(List<LabelValueBean> tcdNendoList) {
        tcdNendoList__ = tcdNendoList;
    }
    /**
     * <p>tcd190pageList を取得します。
     * @return tcd190pageList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190pageList__
     */
    public List<LabelValueBean> getTcd190pageList() {
        return tcd190pageList__;
    }
    /**
     * <p>tcd190pageList をセットします。
     * @param tcd190pageList tcd190pageList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190pageList__
     */
    public void setTcd190pageList(List<LabelValueBean> tcd190pageList) {
        tcd190pageList__ = tcd190pageList;
    }
    /**
     * <p>tcd190YukyuModelList を取得します。
     * @return tcd190YukyuModelList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190YukyuModelList__
     */
    public List<Tcd190YukyuModel> getTcd190YukyuModelList() {
        return tcd190YukyuModelList__;
    }
    /**
     * <p>tcd190YukyuModelList をセットします。
     * @param tcd190YukyuModelList tcd190YukyuModelList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd190YukyuModelList__
     */
    public void setTcd190YukyuModelList(
            List<Tcd190YukyuModel> tcd190YukyuModelList) {
        tcd190YukyuModelList__ = tcd190YukyuModelList;
    }
    /**
     * <p>tcd200Group を取得します。
     * @return tcd200Group
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd200Group__
     */
    public int getTcd200Group() {
        return tcd200Group__;
    }
    /**
     * <p>tcd200Group をセットします。
     * @param tcd200Group tcd200Group
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcd200Group__
     */
    public void setTcd200Group(int tcd200Group) {
        tcd200Group__ = tcd200Group;
    }
    /**
     * <p>tcdGroupList を取得します。
     * @return tcdGroupList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcdGroupList__
     */
    public List<LabelValueBean> getTcdGroupList() {
        return tcdGroupList__;
    }
    /**
     * <p>tcdGroupList をセットします。
     * @param tcdGroupList tcdGroupList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcdGroupList__
     */
    public void setTcdGroupList(List<LabelValueBean> tcdGroupList) {
        tcdGroupList__ = tcdGroupList;
    }
    /**
     * <p>tcdUserList を取得します。
     * @return tcdUserList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcdUserList__
     */
    public List<UsrLabelValueBean> getTcdUserList() {
        return tcdUserList__;
    }
    /**
     * <p>tcdUserList をセットします。
     * @param tcdUserList tcdUserList
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190Form#tcdUserList__
     */
    public void setTcdUserList(List<UsrLabelValueBean> tcdUserList) {
        tcdUserList__ = tcdUserList;
    }
}

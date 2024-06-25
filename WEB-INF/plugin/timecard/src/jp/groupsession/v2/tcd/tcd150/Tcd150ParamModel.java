package jp.groupsession.v2.tcd.tcd150;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.tcd.tcd010.Tcd010ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] タイムカード CSVインポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150ParamModel extends Tcd010ParamModel {

    /** 登録対象　グループ */
    private String tcd150SltGroup__ = null;
    /** 登録対象 ユーザ */
    private String tcd150SltUser__ = null;
    
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> tcd150FileLabelList__ = null;
    /** 添付ファイル(コンボで選択中) */
    private String[] tcd150SelectFiles__ = null;
    
    /** 取込みファイル件数 */
    private int impDataCnt__;
    
    /** グループコンボ */
    private List<LabelValueBean> tcd150GpLabelList__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> tcd150UsrLabelList__ = null;
    
    /**
     * <p>tcd150SltGroup を取得します。
     * @return tcd150SltGroup
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150SltGroup__
     */
    public String getTcd150SltGroup() {
        return tcd150SltGroup__;
    }
    /**
     * <p>tcd150SltGroup をセットします。
     * @param tcd150SltGroup tcd150SltGroup
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150SltGroup__
     */
    public void setTcd150SltGroup(String tcd150SltGroup) {
        tcd150SltGroup__ = tcd150SltGroup;
    }
    /**
     * <p>tcd150SltUser を取得します。
     * @return tcd150SltUser
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150SltUser__
     */
    public String getTcd150SltUser() {
        return tcd150SltUser__;
    }
    /**
     * <p>tcd150SltUser をセットします。
     * @param tcd150SltUser tcd150SltUser
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150SltUser__
     */
    public void setTcd150SltUser(String tcd150SltUser) {
        tcd150SltUser__ = tcd150SltUser;
    }
    /**
     * <p>tcd150FileLabelList を取得します。
     * @return tcd150FileLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150FileLabelList__
     */
    public ArrayList<LabelValueBean> getTcd150FileLabelList() {
        return tcd150FileLabelList__;
    }
    /**
     * <p>tcd150FileLabelList をセットします。
     * @param tcd150FileLabelList tcd150FileLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150FileLabelList__
     */
    public void setTcd150FileLabelList(
            ArrayList<LabelValueBean> tcd150FileLabelList) {
        tcd150FileLabelList__ = tcd150FileLabelList;
    }
    /**
     * <p>tcd150SelectFiles を取得します。
     * @return tcd150SelectFiles
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150SelectFiles__
     */
    public String[] getTcd150SelectFiles() {
        return tcd150SelectFiles__;
    }
    /**
     * <p>tcd150SelectFiles をセットします。
     * @param tcd150SelectFiles tcd150SelectFiles
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150SelectFiles__
     */
    public void setTcd150SelectFiles(String[] tcd150SelectFiles) {
        tcd150SelectFiles__ = tcd150SelectFiles;
    }
    /**
     * <p>impDataCnt を取得します。
     * @return impDataCnt
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#impDataCnt__
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }
    /**
     * <p>impDataCnt をセットします。
     * @param impDataCnt impDataCnt
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#impDataCnt__
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }
    /**
     * <p>tcd150GpLabelList を取得します。
     * @return tcd150GpLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150GpLabelList__
     */
    public List<LabelValueBean> getTcd150GpLabelList() {
        return tcd150GpLabelList__;
    }
    /**
     * <p>tcd150GpLabelList をセットします。
     * @param tcd150GpLabelList tcd150GpLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150GpLabelList__
     */
    public void setTcd150GpLabelList(List<LabelValueBean> tcd150GpLabelList) {
        tcd150GpLabelList__ = tcd150GpLabelList;
    }
    /**
     * <p>tcd150UsrLabelList を取得します。
     * @return tcd150UsrLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150UsrLabelList__
     */
    public List<UsrLabelValueBean> getTcd150UsrLabelList() {
        return tcd150UsrLabelList__;
    }
    /**
     * <p>tcd150UsrLabelList をセットします。
     * @param tcd150UsrLabelList tcd150UsrLabelList
     * @see jp.groupsession.v2.tcd.tcd150.Tcd150ParamModel#tcd150UsrLabelList__
     */
    public void setTcd150UsrLabelList(List<UsrLabelValueBean> tcd150UsrLabelList) {
        tcd150UsrLabelList__ = tcd150UsrLabelList;
    }
}

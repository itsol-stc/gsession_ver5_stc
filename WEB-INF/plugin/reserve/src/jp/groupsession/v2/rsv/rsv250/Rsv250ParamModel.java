package jp.groupsession.v2.rsv.rsv250;

import java.util.ArrayList;

import jp.groupsession.v2.rsv.rsv040.Rsv040ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv250ParamModel extends Rsv040ParamModel {

    /** 添付ファイル(コンボで選択中) */
    private String[] rsv250SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> rsv250FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;

    /**
     * @return impDataCnt
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }

    /**
     * @param impDataCnt 設定する impDataCnt
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }

    /**
     * @return rsv250FileLabelList
     */
    public ArrayList<LabelValueBean> getRsv250FileLabelList() {
        return rsv250FileLabelList__;
    }

    /**
     * @param rsv250FileLabelList 設定する rsv250FileLabelList
     */
    public void setRsv250FileLabelList(ArrayList<LabelValueBean> rsv250FileLabelList) {
        rsv250FileLabelList__ = rsv250FileLabelList;
    }

    /**
     * @return rsv250SelectFiles
     */
    public String[] getRsv250SelectFiles() {
        return rsv250SelectFiles__;
    }

    /**
     * @param rsv250SelectFiles 設定する rsv250SelectFiles
     */
    public void setRsv250SelectFiles(String[] rsv250SelectFiles) {
        rsv250SelectFiles__ = rsv250SelectFiles;
    }
}
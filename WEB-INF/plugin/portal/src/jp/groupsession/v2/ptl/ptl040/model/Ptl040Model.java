package jp.groupsession.v2.ptl.ptl040.model;

import java.io.Serializable;

/**
 * <br>[機  能] ポータル ポータル詳細画面の表示用情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl040Model implements Serializable {

    /** プラグイン・ポートレット名 */
    private String dspName__;
    /** アイテムID */
    private String ptpItemid__;
    /** 表示区分 */
    private int ptpView__;
    /** ポートレット種別 */
    private int ptpType__;
    /** 番号 */
    private String num__;
    /**
     * <p>dspName を取得します。
     * @return dspName
     */
    public String getDspName() {
        return dspName__;
    }
    /**
     * <p>dspName をセットします。
     * @param dspName dspName
     */
    public void setDspName(String dspName) {
        dspName__ = dspName;
    }
    /**
     * <p>ptpItemid を取得します。
     * @return ptpItemid
     */
    public String getPtpItemid() {
        return ptpItemid__;
    }
    /**
     * <p>ptpItemid をセットします。
     * @param ptpItemid ptpItemid
     */
    public void setPtpItemid(String ptpItemid) {
        ptpItemid__ = ptpItemid;
    }
    /**
     * <p>num を取得します。
     * @return num
     */
    public String getNum() {
        return num__;
    }
    /**
     * <p>num をセットします。
     * @param num num
     */
    public void setNum(String num) {
        num__ = num;
    }
    /**
     * <p>ptpView を取得します。
     * @return ptpView
     */
    public int getPtpView() {
        return ptpView__;
    }
    /**
     * <p>ptpView をセットします。
     * @param ptpView ptpView
     */
    public void setPtpView(int ptpView) {
        ptpView__ = ptpView;
    }
    /**
     * <p>ptpType を取得します。
     * @return ptpType
     */
    public int getPtpType() {
        return ptpType__;
    }
    /**
     * <p>ptpType をセットします。
     * @param ptpType ptpType
     */
    public void setPtpType(int ptpType) {
        ptpType__ = ptpType;
    }

}

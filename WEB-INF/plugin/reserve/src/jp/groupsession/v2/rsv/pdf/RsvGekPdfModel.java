package jp.groupsession.v2.rsv.pdf;

import java.util.ArrayList;

import jp.groupsession.v2.rsv.RsvWeekModel;

/**
 * <br>[機  能] 施設予約[月間]PDF出力用モデルです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvGekPdfModel {

    /** ファイル名 */
    private String rsvGekFileName__ = null;
    /** 保存先ファイル名 */
    private String saveGekFileName__ = null;
    /**年月*/
    private String rsvGekDspDate__ = null;
    /** ヘッダー表示用年月日(曜) */
    private String rsvGekHeadDate__ = null;
    /** 表示グループ */
    private String rsvGekDspGroup__ = null;
    /** 表示施設 */
    private String rsvGekDspSisetsu__ = null;
    /** 施設毎の予約情報リスト */
    private ArrayList<RsvWeekModel> rsvGekMonthList__ = null;
    /**
     * <p>rsvGekFileName を取得します。
     * @return rsvGekFileName
     */
    public String getRsvGekFileName() {
        return rsvGekFileName__;
    }
    /**
     * <p>rsvGekFileName をセットします。
     * @param rsvGekFileName rsvGekFileName
     */
    public void setRsvGekFileName(String rsvGekFileName) {
        rsvGekFileName__ = rsvGekFileName;
    }
    /**
     * <p>saveGekFileName を取得します。
     * @return saveGekFileName
     */
    public String getSaveGekFileName() {
        return saveGekFileName__;
    }
    /**
     * <p>saveGekFileName をセットします。
     * @param saveGekFileName saveGekFileName
     */
    public void setSaveGekFileName(String saveGekFileName) {
        saveGekFileName__ = saveGekFileName;
    }
    /**
     * <p>rsvGekDspDate を取得します。
     * @return rsvGekDspDate
     */
    public String getRsvGekDspDate() {
        return rsvGekDspDate__;
    }
    /**
     * <p>rsvGekDspDate をセットします。
     * @param rsvGekDspDate rsvGekDspDate
     */
    public void setRsvGekDspDate(String rsvGekDspDate) {
        rsvGekDspDate__ = rsvGekDspDate;
    }
    /**
     * <p>rsvGekHeadDate を取得します。
     * @return rsvGekHeadDate
     */
    public String getRsvGekHeadDate() {
        return rsvGekHeadDate__;
    }
    /**
     * <p>rsvGekHeadDate をセットします。
     * @param rsvGekHeadDate rsvGekHeadDate
     */
    public void setRsvGekHeadDate(String rsvGekHeadDate) {
        rsvGekHeadDate__ = rsvGekHeadDate;
    }
    /**
     * <p>rsvGekDspGroup を取得します。
     * @return rsvGekDspGroup
     */
    public String getRsvGekDspGroup() {
        return rsvGekDspGroup__;
    }
    /**
     * <p>rsvGekDspGroup をセットします。
     * @param rsvGekDspGroup rsvGekDspGroup
     */
    public void setRsvGekDspGroup(String rsvGekDspGroup) {
        rsvGekDspGroup__ = rsvGekDspGroup;
    }
    /**
     * <p>rsvGekDspSisetsu を取得します。
     * @return rsvGekDspSisetsu
     */
    public String getRsvGekDspSisetsu() {
        return rsvGekDspSisetsu__;
    }
    /**
     * <p>rsvGekDspSisetsu をセットします。
     * @param rsvGekDspSisetsu rsvGekDspSisetsu
     */
    public void setRsvGekDspSisetsu(String rsvGekDspSisetsu) {
        rsvGekDspSisetsu__ = rsvGekDspSisetsu;
    }
    /**
     * <p>rsvGekMonthList を取得します。
     * @return rsvGekMonthList
     */
    public ArrayList<RsvWeekModel> getRsvGekMonthList() {
        return rsvGekMonthList__;
    }
    /**
     * <p>rsvGekMonthList をセットします。
     * @param rsvGekMonthList rsvGekMonthList
     */
    public void setRsvGekMonthList(ArrayList<RsvWeekModel> rsvGekMonthList) {
        rsvGekMonthList__ = rsvGekMonthList;
    }

}

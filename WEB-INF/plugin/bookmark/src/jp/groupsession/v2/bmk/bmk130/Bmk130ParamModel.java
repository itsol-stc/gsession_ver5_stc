package jp.groupsession.v2.bmk.bmk130;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.bmk.bmk120.Bmk120ParamModel;
import jp.groupsession.v2.bmk.bmk130.ui.Bmk130BookmarkSelector;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 個人設定 表示件数設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk130ParamModel extends Bmk120ParamModel {

    /** 表示件数コンボ */
    private List<LabelValueBean> bmk130DspCountList__ = null;
    /** 表示件数の選択値 */
    private String bmk130DspCount__ = null;

    /** 個人ブックマークメイン表示区分 */
    private int bmk130MyKbn__ = -1;
    /** 新着ブックマークメイン表示区分 */
    private int bmk130NewKbn__ = -1;
    /** 新着ブックマーク表示日数の選択値 */
    public static final String[] NEWCNTVALUE
        = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    /** メイン画面からの遷移フラグ */
    private int bmkBackMainFlg__ = 0;

    //入力項目
    /** 新着ブックマーク表示日数 */
    private int bmk130newCnt__ = 0;

    //表示項目
    /** 表示ブックマーク(パラメータ保持用) */
    private String[] bmk130ViewBmkList__ = null;
    /** 表示ブックマーク UI */
    private Bmk130BookmarkSelector bmk130ViewBmkListUI__ = null;

    /** 新着ブックマーク表示日数 */
    private List < LabelValueBean > bmk130newCntLabel__ = null;


    /**
     * <p>bmk130DspCount を取得します。
     * @return bmk130DspCount
     */
    public String getBmk130DspCount() {
        return bmk130DspCount__;
    }
    /**
     * <p>bmk130DspCount をセットします。
     * @param bmk130DspCount bmk130DspCount
     */
    public void setBmk130DspCount(String bmk130DspCount) {
        bmk130DspCount__ = bmk130DspCount;
    }
    /**
     * <p>bmk130DspCountList を取得します。
     * @return bmk130DspCountList
     */
    public List<LabelValueBean> getBmk130DspCountList() {
        return bmk130DspCountList__;
    }
    /**
     * <p>bmk130DspCountList をセットします。
     * @param bmk130DspCountList bmk130DspCountList
     */
    public void setBmk130DspCountList(List<LabelValueBean> bmk130DspCountList) {
        bmk130DspCountList__ = bmk130DspCountList;
    }

    /**
     * <p>bmk130newCnt を取得します。
     * @return bmk130newCnt
     */
    public int getBmk130newCnt() {
        return bmk130newCnt__;
    }
    /**
     * <p>bmk130newCnt をセットします。
     * @param bmk130newCnt bmk130newCnt
     */
    public void setBmk130newCnt(int bmk130newCnt) {
        bmk130newCnt__ = bmk130newCnt;
    }
    /**
     * <p>bmk130newCntLabel を取得します。
     * @return bmk130newCntLabel
     */
    public List<LabelValueBean> getBmk130newCntLabel() {
        return bmk130newCntLabel__;
    }
    /**
     * <p>bmk130newCntLabel をセットします。
     * @param bmk130newCntLabel bmk130newCntLabel
     */
    public void setBmk130newCntLabel(List<LabelValueBean> bmk130newCntLabel) {
        bmk130newCntLabel__ = bmk130newCntLabel;
    }
    /**
     * <p>bmk130MyKbn を取得します。
     * @return bmk130MyKbn
     */
    public int getBmk130MyKbn() {
        return bmk130MyKbn__;
    }
    /**
     * <p>bmk130MyKbn をセットします。
     * @param bmk130MyKbn bmk130MyKbn
     */
    public void setBmk130MyKbn(int bmk130MyKbn) {
        bmk130MyKbn__ = bmk130MyKbn;
    }
    /**
     * <p>bmk130NewKbn を取得します。
     * @return bmk130NewKbn
     */
    public int getBmk130NewKbn() {
        return bmk130NewKbn__;
    }
    /**
     * <p>bmk130NewKbn をセットします。
     * @param bmk130NewKbn bmk130NewKbn
     */
    public void setBmk130NewKbn(int bmk130NewKbn) {
        bmk130NewKbn__ = bmk130NewKbn;
    }
    /**
     * <p>bmk130ViewBmkList を取得します。
     * @return bmk130ViewBmkList
     */
    public String[] getBmk130ViewBmkList() {
        return bmk130ViewBmkList__;
    }
    /**
     * <p>bmk130ViewBmkList をセットします。
     * @param bmk130ViewBmkList bmk130ViewBmkList
     */
    public void setBmk130ViewBmkList(String[] bmk130ViewBmkList) {
        bmk130ViewBmkList__ = bmk130ViewBmkList;
    }
    /**
     * <p>bmk130ViewBmkListUI を取得します。
     * @return bmk130ViewBmkListUI
     * @see jp.groupsession.v2.bmk.bmk130.Bmk130ParamModel#bmk130ViewBmkListUI__
     */
    public Bmk130BookmarkSelector getBmk130ViewBmkListUI() {
        return bmk130ViewBmkListUI__;
    }
    /**
     * <p>bmk130ViewBmkListUI をセットします。
     * @param bmk130ViewBmkListUI bmk130ViewBmkListUI
     * @see jp.groupsession.v2.bmk.bmk130.Bmk130ParamModel#bmk130ViewBmkListUI__
     */
    public void setBmk130ViewBmkListUI(Bmk130BookmarkSelector bmk130ViewBmkListUI) {
        bmk130ViewBmkListUI__ = bmk130ViewBmkListUI;
    }
    /**
     * <p>bmkBackMainFlg を取得します。
     * @return bmkBackMainFlg
     * @see jp.groupsession.v2.bmk.main020.BmkMain020ParamModel#bmkBackMainFlg__
     */
    public int getBmkBackMainFlg() {
        return bmkBackMainFlg__;
    }
    /**
     * <p>bmkBackMainFlg をセットします。
     * @param bmkBackMainFlg bmkBackMainFlg
     * @see jp.groupsession.v2.bmk.main020.BmkMain020ParamModel#bmkBackMainFlg__
     */
    public void setBmkBackMainFlg(int bmkBackMainFlg) {
        bmkBackMainFlg__ = bmkBackMainFlg;
    }
    /**
     * <br>[機  能] 新着ブックマーク表示日数コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public void setNewCntLabel(RequestModel reqMdl) {
        bmk130newCntLabel__ = new ArrayList < LabelValueBean >();
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (String label : NEWCNTVALUE) {
            bmk130newCntLabel__.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.less.than.day", new String[] {label}),
                                        label));
        }
    }


}
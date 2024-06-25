package jp.groupsession.v2.ntp.ntp094;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.ntp090.Ntp090ParamModel;

/**
 * <br>[機  能] 日報 日報一覧表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp094ParamModel extends Ntp090ParamModel {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp094ParamModel.class);

    /** デフォルト表示件数 */
    private int ntp094DefLine__ = -1;
    /** デフォルト表示件数 ラベル */
    private List<LabelValueBean> ntp094LineLabel__ = null;
    /** 自動リロード時間コンボ */
    private List < LabelValueBean > ntp094TimeLabelList__ = null;
    /** 自動リロード時間の選択値 */
    private String ntp094ReloadTime__ = null;
    /** デフォルト表示件数 */
    private int ntp094Position__ = GSConstNippou.DAY_POSITION_RIGHT;
    /** 初期表示フラグ */
    private int ntp094InitFlg__ = GSConstNippou.INIT_FLG;

    /** ソートキー1 */
    private int ntp094SortKey1__ = -1;
    /** ソートキー1オーダー */
    private int ntp094SortOrder1__ = -1;
    /** ソートキー2 */
    private int ntp094SortKey2__ = -1;
    /** ソートキー2オーダー */
    private int ntp094SortOrder2__ = -1;
    /** ソートキー ラベル */
    private List<LabelValueBean> ntp094SortKeyLabel__ = null;

    /** デフォルト表示グループ */
    private String ntp094DefGroup__ = null;
    /** デフォルト表示グループ ラベル */
    private List<NtpLabelValueModel> ntp094GroupLabel__ = null;

    /** デフォルト表示件数 */
    private int ntp094SchKbn__ = -1;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ntp094ParamModel() {
        //表示ラベル
        ArrayList<LabelValueBean> lineLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstNippou.LIST_LINE_COUNTER.length; i++) {
            String label = String.valueOf(GSConstNippou.LIST_LINE_COUNTER[i]);
            String value = Integer.toString(GSConstNippou.LIST_LINE_COUNTER[i]);
            log__.debug("label==>" + label);
            log__.debug("value==>" + value);
            lineLabel.add(new LabelValueBean(label, value));
        }
        ntp094LineLabel__ = lineLabel;

        //ソートキーラベル
        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstNippou.SORT_KEY_ALL.length; i++) {
            String label = GSConstNippou.SORT_KEY_ALL_TEXT[i];
            String value = Integer.toString(GSConstNippou.SORT_KEY_ALL[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        ntp094SortKeyLabel__ = sortLabel;
    }

    /**
     * <p>ntp094DefLine を取得します。
     * @return ntp094DefLine
     */
    public int getNtp094DefLine() {
        return ntp094DefLine__;
    }

    /**
     * <p>ntp094DefLine をセットします。
     * @param ntp094DefLine ntp094DefLine
     */
    public void setNtp094DefLine(int ntp094DefLine) {
        ntp094DefLine__ = ntp094DefLine;
    }

    /**
     * <p>ntp094LineLabel を取得します。
     * @return ntp094LineLabel
     */
    public List<LabelValueBean> getNtp094LineLabel() {
        return ntp094LineLabel__;
    }

    /**
     * <p>ntp094LineLabel をセットします。
     * @param ntp094LineLabel ntp094LineLabel
     */
    public void setNtp094LineLabel(List<LabelValueBean> ntp094LineLabel) {
        ntp094LineLabel__ = ntp094LineLabel;
    }

    /**
     * <p>ntp094ReloadTime を取得します。
     * @return ntp094ReloadTime
     */
    public String getNtp094ReloadTime() {
        return ntp094ReloadTime__;
    }

    /**
     * <p>ntp094ReloadTime をセットします。
     * @param ntp094ReloadTime ntp094ReloadTime
     */
    public void setNtp094ReloadTime(String ntp094ReloadTime) {
        ntp094ReloadTime__ = ntp094ReloadTime;
    }

    /**
     * <p>ntp094TimeLabelList を取得します。
     * @return ntp094TimeLabelList
     */
    public List<LabelValueBean> getNtp094TimeLabelList() {
        return ntp094TimeLabelList__;
    }

    /**
     * <p>ntp094TimeLabelList をセットします。
     * @param ntp094TimeLabelList ntp094TimeLabelList
     */
    public void setNtp094TimeLabelList(List<LabelValueBean> ntp094TimeLabelList) {
        ntp094TimeLabelList__ = ntp094TimeLabelList;
    }

    /**
     * <p>ntp094Position を取得します。
     * @return ntp094Position
     */
    public int getNtp094Position() {
        return ntp094Position__;
    }

    /**
     * <p>ntp094Position をセットします。
     * @param ntp094Position ntp094Position
     */
    public void setNtp094Position(int ntp094Position) {
        ntp094Position__ = ntp094Position;
    }

    /**
     * <p>ntp094InitFlg を取得します。
     * @return ntp094InitFlg
     */
    public int getNtp094InitFlg() {
        return ntp094InitFlg__;
    }

    /**
     * <p>ntp094InitFlg をセットします。
     * @param ntp094InitFlg ntp094InitFlg
     */
    public void setNtp094InitFlg(int ntp094InitFlg) {
        ntp094InitFlg__ = ntp094InitFlg;
    }


    /**
     * <p>ntp094SortKey1 を取得します。
     * @return ntp094SortKey1
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortKey1__
     */
    public int getNtp094SortKey1() {
        return ntp094SortKey1__;
    }

    /**
     * <p>ntp094SortKey1 をセットします。
     * @param ntp094SortKey1 ntp094SortKey1
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortKey1__
     */
    public void setNtp094SortKey1(int ntp094SortKey1) {
        ntp094SortKey1__ = ntp094SortKey1;
    }

    /**
     * <p>ntp094SortOrder1 を取得します。
     * @return ntp094SortOrder1
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortOrder1__
     */
    public int getNtp094SortOrder1() {
        return ntp094SortOrder1__;
    }

    /**
     * <p>ntp094SortOrder1 をセットします。
     * @param ntp094SortOrder1 ntp094SortOrder1
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortOrder1__
     */
    public void setNtp094SortOrder1(int ntp094SortOrder1) {
        ntp094SortOrder1__ = ntp094SortOrder1;
    }

    /**
     * <p>ntp094SortKey2 を取得します。
     * @return ntp094SortKey2
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortKey2__
     */
    public int getNtp094SortKey2() {
        return ntp094SortKey2__;
    }

    /**
     * <p>ntp094SortKey2 をセットします。
     * @param ntp094SortKey2 ntp094SortKey2
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortKey2__
     */
    public void setNtp094SortKey2(int ntp094SortKey2) {
        ntp094SortKey2__ = ntp094SortKey2;
    }

    /**
     * <p>ntp094SortOrder2 を取得します。
     * @return ntp094SortOrder2
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortOrder2__
     */
    public int getNtp094SortOrder2() {
        return ntp094SortOrder2__;
    }

    /**
     * <p>ntp094SortOrder2 をセットします。
     * @param ntp094SortOrder2 ntp094SortOrder2
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortOrder2__
     */
    public void setNtp094SortOrder2(int ntp094SortOrder2) {
        ntp094SortOrder2__ = ntp094SortOrder2;
    }

    /**
     * <p>ntp094SortKeyLabel を取得します。
     * @return ntp094SortKeyLabel
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortKeyLabel__
     */
    public List<LabelValueBean> getNtp094SortKeyLabel() {
        return ntp094SortKeyLabel__;
    }

    /**
     * <p>ntp094SortKeyLabel をセットします。
     * @param ntp094SortKeyLabel ntp094SortKeyLabel
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SortKeyLabel__
     */
    public void setNtp094SortKeyLabel(List<LabelValueBean> ntp094SortKeyLabel) {
        ntp094SortKeyLabel__ = ntp094SortKeyLabel;
    }

    /**
     * <p>ntp094DefGroup を取得します。
     * @return ntp094DefGroup
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094DefGroup__
     */
    public String getNtp094DefGroup() {
        return ntp094DefGroup__;
    }

    /**
     * <p>ntp094DefGroup をセットします。
     * @param ntp094DefGroup ntp094DefGroup
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094DefGroup__
     */
    public void setNtp094DefGroup(String ntp094DefGroup) {
        ntp094DefGroup__ = ntp094DefGroup;
    }

    /**
     * <p>ntp094GroupLabel を取得します。
     * @return ntp094GroupLabel
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094GroupLabel__
     */
    public List<NtpLabelValueModel> getNtp094GroupLabel() {
        return ntp094GroupLabel__;
    }

    /**
     * <p>ntp094GroupLabel をセットします。
     * @param ntp094GroupLabel ntp094GroupLabel
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094GroupLabel__
     */
    public void setNtp094GroupLabel(List<NtpLabelValueModel> ntp094GroupLabel) {
        ntp094GroupLabel__ = ntp094GroupLabel;
    }

    /**
     * <p>ntp094SchKbn を取得します。
     * @return ntp094SchKbn
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SchKbn__
     */
    public int getNtp094SchKbn() {
        return ntp094SchKbn__;
    }

    /**
     * <p>ntp094SchKbn をセットします。
     * @param ntp094SchKbn ntp094SchKbn
     * @see jp.groupsession.v2.ntp.ntp094.Ntp094Form#ntp094SchKbn__
     */
    public void setNtp094SchKbn(int ntp094SchKbn) {
        ntp094SchKbn__ = ntp094SchKbn;
    }

}

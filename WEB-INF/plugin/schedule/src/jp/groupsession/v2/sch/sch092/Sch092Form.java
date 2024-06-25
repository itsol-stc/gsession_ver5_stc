package jp.groupsession.v2.sch.sch092;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.sch100.Sch100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 日間表示時間帯設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch092Form extends Sch100Form {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Sch092Form() {
        //ソートキーラベル
        ArrayList<LabelValueBean> lineLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstSchedule.LIST_LINE_COUNTER.length; i++) {
            String label = String.valueOf(GSConstSchedule.LIST_LINE_COUNTER[i]);
            String value = Integer.toString(GSConstSchedule.LIST_LINE_COUNTER[i]);
            log__.debug("label==>" + label);
            log__.debug("value==>" + value);
            lineLabel.add(new LabelValueBean(label, value));
        }
        sch092LineLabel__ = lineLabel;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch092Form.class);
    /** ラベル 時 */
    private List < LabelValueBean > sch092HourLabel__ = null;
    /** 開始 時 */
    private int sch092FrH__ = -1;
    /** 終了 時 */
    private int sch092ToH__ = -1;

    /** ソートキー1 */
    private int sch092SortKey1__ = -1;
    /** ソートキー1オーダー */
    private int sch092SortOrder1__ = -1;
    /** ソートキー2 */
    private int sch092SortKey2__ = -1;
    /** ソートキー2オーダー */
    private int sch092SortOrder2__ = -1;
    /** ソートキー ラベル */
    private List<LabelValueBean> sch092SortKeyLabel__ = null;

    /** デフォルト表示グループ */
    private String sch092DefGroup__ = null;
    /** デフォルト表示グループ ラベル */
    private List<SchLabelValueModel> sch092GroupLabel__ = null;
    /** デフォルト表示グループ 個別設定フラグ */
    private int sch092DefGroupFlg__ = 0;

    /** メンバー表示順設定フラグ */
    private int sch092MemDspConfFlg__ = -1;
    /** 表示用ソートキー1 名前 */
    private String sch092DspSortKey1__ = null;
    /** 表示用ソートキー1 オーダー */
    private String sch092DspSortOrder1__ = null;
    /** 表示用ソートキー2 名前 */
    private String sch092DspSortKey2__ = null;
    /** 表示用ソートキー2 オーダー */
    private String sch092DspSortOrder2__ = null;

    /** デフォルト表示画面 */
    private String sch092DefDsp__ = null;

    /** デフォルト表示件数 */
    private int sch092DefLine__ = -1;
    /** デフォルト表示件数 ラベル */
    private List<LabelValueBean> sch092LineLabel__ = null;
    /** 自動リロード時間コンボ */
    private List < LabelValueBean > sch092TimeLabelList__ = null;
    /** 自動リロード時間の選択値 */
    private String sch092ReloadTime__ = null;
    /** 表示開始曜日設定の曜日コンボ */
    private List < LabelValueBean > sch092WeekList__ = null;
    /** 表示開始曜日設定の曜日の選択値 */
    private String sch092SelWeek__ = null;

    /**
     * <p>sch092FrH を取得します。
     * @return sch092FrH
     */
    public int getSch092FrH() {
        return sch092FrH__;
    }

    /**
     * <p>sch092FrH をセットします。
     * @param sch092FrH sch092FrH
     */
    public void setSch092FrH(int sch092FrH) {
        sch092FrH__ = sch092FrH;
    }

    /**
     * <p>sch092HourLabel を取得します。
     * @return sch092HourLabel
     */
    public List<LabelValueBean> getSch092HourLabel() {
        return sch092HourLabel__;
    }

    /**
     * <p>sch092HourLabel をセットします。
     * @param sch092HourLabel sch092HourLabel
     */
    public void setSch092HourLabel(List<LabelValueBean> sch092HourLabel) {
        sch092HourLabel__ = sch092HourLabel;
    }

    /**
     * <p>sch092ToH を取得します。
     * @return sch092ToH
     */
    public int getSch092ToH() {
        return sch092ToH__;
    }

    /**
     * <p>sch092ToH をセットします。
     * @param sch092ToH sch092ToH
     */
    public void setSch092ToH(int sch092ToH) {
        sch092ToH__ = sch092ToH;
    }
    /**
     * <p>log__ を取得します。
     * @return log__
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#log__
     */
    public static Log getLog__() {
        return log__;
    }

    /**
     * <p>log__ をセットします。
     * @param log__ log__
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#log__
     */
    public static void setLog__(Log log__) {
        Sch092Form.log__ = log__;
    }

    /**
     * <p>sch092SortKey1 を取得します。
     * @return sch092SortKey1
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortKey1__
     */
    public int getSch092SortKey1() {
        return sch092SortKey1__;
    }

    /**
     * <p>sch092SortKey1 をセットします。
     * @param sch092SortKey1 sch092SortKey1
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortKey1__
     */
    public void setSch092SortKey1(int sch092SortKey1) {
        sch092SortKey1__ = sch092SortKey1;
    }

    /**
     * <p>sch092SortOrder1 を取得します。
     * @return sch092SortOrder1
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortOrder1__
     */
    public int getSch092SortOrder1() {
        return sch092SortOrder1__;
    }

    /**
     * <p>sch092SortOrder1 をセットします。
     * @param sch092SortOrder1 sch092SortOrder1
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortOrder1__
     */
    public void setSch092SortOrder1(int sch092SortOrder1) {
        sch092SortOrder1__ = sch092SortOrder1;
    }

    /**
     * <p>sch092SortKey2 を取得します。
     * @return sch092SortKey2
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortKey2__
     */
    public int getSch092SortKey2() {
        return sch092SortKey2__;
    }

    /**
     * <p>sch092SortKey2 をセットします。
     * @param sch092SortKey2 sch092SortKey2
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortKey2__
     */
    public void setSch092SortKey2(int sch092SortKey2) {
        sch092SortKey2__ = sch092SortKey2;
    }

    /**
     * <p>sch092SortOrder2 を取得します。
     * @return sch092SortOrder2
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortOrder2__
     */
    public int getSch092SortOrder2() {
        return sch092SortOrder2__;
    }

    /**
     * <p>sch092SortOrder2 をセットします。
     * @param sch092SortOrder2 sch092SortOrder2
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortOrder2__
     */
    public void setSch092SortOrder2(int sch092SortOrder2) {
        sch092SortOrder2__ = sch092SortOrder2;
    }

    /**
     * <p>sch092SortKeyLabel を取得します。
     * @return sch092SortKeyLabel
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortKeyLabel__
     */
    public List<LabelValueBean> getSch092SortKeyLabel() {
        return sch092SortKeyLabel__;
    }

    /**
     * <p>sch092SortKeyLabel をセットします。
     * @param sch092SortKeyLabel sch092SortKeyLabel
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SortKeyLabel__
     */
    public void setSch092SortKeyLabel(List<LabelValueBean> sch092SortKeyLabel) {
        sch092SortKeyLabel__ = sch092SortKeyLabel;
    }

    /**
     * <p>sch092DefGroup を取得します。
     * @return sch092DefGroup
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DefGroup__
     */
    public String getSch092DefGroup() {
        return sch092DefGroup__;
    }

    /**
     * <p>sch092DefGroup をセットします。
     * @param sch092DefGroup sch092DefGroup
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DefGroup__
     */
    public void setSch092DefGroup(String sch092DefGroup) {
        sch092DefGroup__ = sch092DefGroup;
    }

    /**
     * <p>sch092GroupLabel を取得します。
     * @return sch092GroupLabel
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092GroupLabel__
     */
    public List<SchLabelValueModel> getSch092GroupLabel() {
        return sch092GroupLabel__;
    }

    /**
     * <p>sch092GroupLabel をセットします。
     * @param sch092GroupLabel sch092GroupLabel
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092GroupLabel__
     */
    public void setSch092GroupLabel(List<SchLabelValueModel> sch092GroupLabel) {
        sch092GroupLabel__ = sch092GroupLabel;
    }

    /**
     * <p>sch092DefGroupFlg を取得します。
     * @return sch092DefGroupFlg
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DefGroupFlg__
     */
    public int getSch092DefGroupFlg() {
        return sch092DefGroupFlg__;
    }

    /**
     * <p>sch092DefGroupFlg をセットします。
     * @param sch092DefGroupFlg sch092DefGroupFlg
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DefGroupFlg__
     */
    public void setSch092DefGroupFlg(int sch092DefGroupFlg) {
        sch092DefGroupFlg__ = sch092DefGroupFlg;
    }

    /**
     * <p>sch092MemDspConfFlg を取得します。
     * @return sch092MemDspConfFlg
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092MemDspConfFlg__
     */
    public int getSch092MemDspConfFlg() {
        return sch092MemDspConfFlg__;
    }

    /**
     * <p>sch092MemDspConfFlg をセットします。
     * @param sch092MemDspConfFlg sch092MemDspConfFlg
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092MemDspConfFlg__
     */
    public void setSch092MemDspConfFlg(int sch092MemDspConfFlg) {
        sch092MemDspConfFlg__ = sch092MemDspConfFlg;
    }

    /**
     * <p>sch092DspSortKey1 を取得します。
     * @return sch092DspSortKey1
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DspSortKey1__
     */
    public String getSch092DspSortKey1() {
        return sch092DspSortKey1__;
    }

    /**
     * <p>sch092DspSortKey1 をセットします。
     * @param sch092DspSortKey1 sch092DspSortKey1
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DspSortKey1__
     */
    public void setSch092DspSortKey1(String sch092DspSortKey1) {
        sch092DspSortKey1__ = sch092DspSortKey1;
    }

    /**
     * <p>sch092DspSortOrder1 を取得します。
     * @return sch092DspSortOrder1
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DspSortOrder1__
     */
    public String getSch092DspSortOrder1() {
        return sch092DspSortOrder1__;
    }

    /**
     * <p>sch092DspSortOrder1 をセットします。
     * @param sch092DspSortOrder1 sch092DspSortOrder1
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DspSortOrder1__
     */
    public void setSch092DspSortOrder1(String sch092DspSortOrder1) {
        sch092DspSortOrder1__ = sch092DspSortOrder1;
    }

    /**
     * <p>sch092DspSortKey2 を取得します。
     * @return sch092DspSortKey2
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DspSortKey2__
     */
    public String getSch092DspSortKey2() {
        return sch092DspSortKey2__;
    }

    /**
     * <p>sch092DspSortKey2 をセットします。
     * @param sch092DspSortKey2 sch092DspSortKey2
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DspSortKey2__
     */
    public void setSch092DspSortKey2(String sch092DspSortKey2) {
        sch092DspSortKey2__ = sch092DspSortKey2;
    }

    /**
     * <p>sch092DspSortOrder2 を取得します。
     * @return sch092DspSortOrder2
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DspSortOrder2__
     */
    public String getSch092DspSortOrder2() {
        return sch092DspSortOrder2__;
    }

    /**
     * <p>sch092DspSortOrder2 をセットします。
     * @param sch092DspSortOrder2 sch092DspSortOrder2
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DspSortOrder2__
     */
    public void setSch092DspSortOrder2(String sch092DspSortOrder2) {
        sch092DspSortOrder2__ = sch092DspSortOrder2;
    }

    /**
     * <p>sch092DefDsp を取得します。
     * @return sch092DefDsp
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DefDsp__
     */
    public String getSch092DefDsp() {
        return sch092DefDsp__;
    }

    /**
     * <p>sch092DefDsp をセットします。
     * @param sch092DefDsp sch092DefDsp
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DefDsp__
     */
    public void setSch092DefDsp(String sch092DefDsp) {
        sch092DefDsp__ = sch092DefDsp;
    }

    /**
     * <p>sch092DefLine を取得します。
     * @return sch092DefLine
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DefLine__
     */
    public int getSch092DefLine() {
        return sch092DefLine__;
    }

    /**
     * <p>sch092DefLine をセットします。
     * @param sch092DefLine sch092DefLine
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092DefLine__
     */
    public void setSch092DefLine(int sch092DefLine) {
        sch092DefLine__ = sch092DefLine;
    }

    /**
     * <p>sch092LineLabel を取得します。
     * @return sch092LineLabel
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092LineLabel__
     */
    public List<LabelValueBean> getSch092LineLabel() {
        return sch092LineLabel__;
    }

    /**
     * <p>sch092LineLabel をセットします。
     * @param sch092LineLabel sch092LineLabel
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092LineLabel__
     */
    public void setSch092LineLabel(List<LabelValueBean> sch092LineLabel) {
        sch092LineLabel__ = sch092LineLabel;
    }

    /**
     * <p>sch092TimeLabelList を取得します。
     * @return sch092TimeLabelList
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092TimeLabelList__
     */
    public List<LabelValueBean> getSch092TimeLabelList() {
        return sch092TimeLabelList__;
    }

    /**
     * <p>sch092TimeLabelList をセットします。
     * @param sch092TimeLabelList sch092TimeLabelList
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092TimeLabelList__
     */
    public void setSch092TimeLabelList(List<LabelValueBean> sch092TimeLabelList) {
        sch092TimeLabelList__ = sch092TimeLabelList;
    }

    /**
     * <p>sch092ReloadTime を取得します。
     * @return sch092ReloadTime
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092ReloadTime__
     */
    public String getSch092ReloadTime() {
        return sch092ReloadTime__;
    }

    /**
     * <p>sch092ReloadTime をセットします。
     * @param sch092ReloadTime sch092ReloadTime
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092ReloadTime__
     */
    public void setSch092ReloadTime(String sch092ReloadTime) {
        sch092ReloadTime__ = sch092ReloadTime;
    }

    /**
     * <p>sch092WeekList を取得します。
     * @return sch092WeekList
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092WeekList__
     */
    public List<LabelValueBean> getSch092WeekList() {
        return sch092WeekList__;
    }

    /**
     * <p>sch092WeekList をセットします。
     * @param sch092WeekList sch092WeekList
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092WeekList__
     */
    public void setSch092WeekList(List<LabelValueBean> sch092WeekList) {
        sch092WeekList__ = sch092WeekList;
    }

    /**
     * <p>sch092SelWeek を取得します。
     * @return sch092SelWeek
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SelWeek__
     */
    public String getSch092SelWeek() {
        return sch092SelWeek__;
    }

    /**
     * <p>sch092SelWeek をセットします。
     * @param sch092SelWeek sch092SelWeek
     * @see jp.groupsession.v2.sch.sch092.Sch092Form#sch092SelWeek__
     */
    public void setSch092SelWeek(String sch092SelWeek) {
        sch092SelWeek__ = sch092SelWeek;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(ActionMapping map, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        log__.debug("sch092FrH__==>" + sch092FrH__);
        log__.debug("sch092ToH__==>" + sch092ToH__);
        if (sch092FrH__ > sch092ToH__) {
            GsMessage gsMsg = new GsMessage();
            //日間表示時間帯
            String textDayTimeZoon = gsMsg.getMessage(req, "cmn.show.timezone.days");
            //開始時刻 < 終了時刻の場合
            //開始時刻 < 終了時刻
            String textStartLessEnd = gsMsg.getMessage(req, "schedule.src.65");
            msg = new ActionMessage("error.input.comp.text", textDayTimeZoon, textStartLessEnd);
            errors.add("error.input.comp.text", msg);
        }
        return errors;
    }
}

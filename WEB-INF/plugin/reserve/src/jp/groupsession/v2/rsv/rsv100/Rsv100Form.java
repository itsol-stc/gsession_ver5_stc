package jp.groupsession.v2.rsv.rsv100;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.rsv.rsv030.Rsv030Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設利用状況照会画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv100Form extends Rsv030Form {

    /** ユーザSID */
    private int rsv100userSid__;

    /** ページリスト */
    private List<LabelValueBean> rsv100PageList__;

    /** キーリスト */
    private List<LabelValueBean> rsv100KeyList__;

    //コンボ
    /** 表示グループ1コンボ */
    private List<LabelValueBean> rsv100LabelListGrp1__ = null;
    /** 表示グループ2コンボ */
    private List<LabelValueBean> rsv100LabelListGrp2__ = null;

    //検索結果
    /** 検索結果格納リスト */
    private ArrayList<Rsv100SisYrkModel> rsv100resultList__ = null;

    /**
     * <br>[機  能] 選択されたパラメータをセーブ用フィールドへ移す
     * <br>[解  説]
     * <br>[備  考]
     */
    public void setParameter() {

        setRsv100svDateKbn(getRsv100dateKbn());
        if (getRsv100dateKbn() != Rsv100Form.DATEKBN_SELECT) {
            setRsv100svFromYear(getRsv100selectedFromYear());
            setRsv100svFromMonth(getRsv100selectedFromMonth());
            setRsv100svFromDay(getRsv100selectedFromDay());
            setRsv100svToYear(getRsv100selectedToYear());
            setRsv100svToMonth(getRsv100selectedToMonth());
            setRsv100svToDay(getRsv100selectedToDay());
            String newYear = StringUtil.toDecFormat(getRsv100selectedFromYear(), "0000");
            String newMonth = StringUtil.toDecFormat(getRsv100selectedFromMonth(), "00");
            String newDay = StringUtil.toDecFormat(getRsv100selectedFromDay(), "00");
            String newDspDate = newYear + newMonth + newDay;
            setRsvDspFrom(newDspDate);
        } else {
            setRsv100svFromYear(-1);
            setRsv100svFromMonth(-1);
            setRsv100svFromDay(-1);
            setRsv100svToYear(-1);
            setRsv100svToMonth(-1);
            setRsv100svToDay(-1);
            setRsvDspFrom(null);
        }

        setRsv100svGrp1(getRsvSelectedGrpSid());
        setRsv100svGrp2(getRsvSelectedSisetuSid());
        setRsv100svKeyWord(getRsv100KeyWord());
        setRsv100svSearchCondition(getRsv100SearchCondition());
        setRsv100svTargetMok(getRsv100TargetMok());
        setRsv100svTargetNiyo(getRsv100TargetNiyo());
        setRsv100svSelectedKey1(getRsv100SelectedKey1());
        setRsv100svSelectedKey2(getRsv100SelectedKey2());
        setRsv100svSelectedKey1Sort(getRsv100SelectedKey1Sort());
        setRsv100svSelectedKey2Sort(getRsv100SelectedKey2Sort());

        setRsv100svApprStatus(getRsv100apprStatus());
    }

    /**
     * <br>[機  能] 入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors validateCheck(HttpServletRequest req)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        String eprefix = "reserve";
        boolean dateOk = true;
        DateTimePickerBiz picker = new DateTimePickerBiz();

        if (getRsv100dateKbn() != Rsv100Form.DATEKBN_SELECT) {
            String frDateNameJp = gsMsg.getMessage("reserve.157");
            String toDateNameJp = gsMsg.getMessage("reserve.158");

            errors.add(picker.setYmdParam(this, "rsv100selectedFromDate",
                    "rsv100selectedFromYear", "rsv100selectedFromMonth",
                    "rsv100selectedFromDay", frDateNameJp));
            errors.add(picker.setYmdParam(this, "rsv100selectedToDate",
                    "rsv100selectedToYear", "rsv100selectedToMonth",
                    "rsv100selectedToDay", toDateNameJp));
            if (errors.size() > 0) {
                return errors;
            }
            //fromの作成
            String from = getRsv100selectedFromDate().replaceAll("/", "");
            //toの作成
            String to = getRsv100selectedToDate().replaceAll("/", "");

            //存在チェック
            if (!ValidateUtil.isExistDateYyyyMMdd(from)) {
                msg = new ActionMessage(
                        "error.input.notfound.date",
                        getRsv100selectedFromMonth()
                        + gsMsg.getMessage(req, "cmn.month")
                        + getRsv100selectedFromDay()
                        + gsMsg.getMessage(req, "cmn.day"));
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "kikan");
                dateOk = false;
            } else if (!ValidateUtil.isExistDateYyyyMMdd(to)) {
                msg = new ActionMessage(
                        "error.input.notfound.date",
                        getRsv100selectedToMonth()
                        + gsMsg.getMessage(req, "cmn.month")
                        + getRsv100selectedToDay()
                        + gsMsg.getMessage(req, "cmn.day"));
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "kikan");
                dateOk = false;
            }

            if (dateOk) {
                UDate udateFrom = new UDate();
                UDate udateTo = new UDate();
                udateFrom.setDate(from);
                udateTo.setDate(to);

                //from - to の範囲整合性チェック
                if (udateFrom.getTime() > udateTo.getTime()) {
                    msg = new ActionMessage("error.input.comp.text",
                            gsMsg.getMessage(req, "reserve.156"),
                            gsMsg.getMessage(req, "cmn.start.lessthan.end"));
                    StrutsUtil.addMessage(
                            errors, msg, eprefix + "kikan");
                }
            }
        }

        String keyword = getRsv100KeyWord();
        //未入力はOK
        if (!StringUtil.isNullZeroString(keyword)) {
            //桁数チェック
            if (keyword.length() > GSConstReserve.MAX_LENGTH_KEYWORD) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(req, "cmn.keyword"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_KEYWORD));
                StrutsUtil.addMessage(errors, msg, "keyWord");
            //スペースのみチェック
            } else if (ValidateUtil.isSpace(keyword)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(req, "cmn.keyword"));
                StrutsUtil.addMessage(errors, msg, eprefix + "keyWord");
            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(keyword)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(req, "cmn.keyword"));
                StrutsUtil.addMessage(errors, msg, eprefix + "keyWord");
            } else if (!GSValidateUtil.isGsJapaneaseString(keyword)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(keyword);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(req, "cmn.keyword"),
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "keyWord");
            }
        }

        //検索対象
        if (!StringUtil.isNullZeroString(keyword)) {

            int mokVal = getRsv100TargetMok();
            int mokNiyo = getRsv100TargetNiyo();
            int valSum = mokVal + mokNiyo;

            if (valSum == 0) {
                msg = new ActionMessage("error.select.required.text",
                        gsMsg.getMessage(req, "cmn.search2"));
                StrutsUtil.addMessage(errors, msg, eprefix + "target");
            }
        }

        //ソート順
        int sort1Val = getRsv100SelectedKey1();
        int sort2Val = getRsv100SelectedKey2();

        if (sort1Val == sort2Val) {
            msg = new ActionMessage("error.select.dup.list",
                    gsMsg.getMessage(req, "cmn.sort.order"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sort");
        }

        return errors;
    }

    /**
     * <br>[機  能] CSV出力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors
     */
    public ActionErrors validateCsvOutCheck(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        String eprefix = "reserve";

        //CSV出力項目にチェック１つもないとき
        if (getRsv100CsvOutField() == null) {
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(req, "reserve.output.item"));
            StrutsUtil.addMessage(errors, msg, eprefix + "export");
        }

        return errors;
    }

    /**
     * <p>rsv100KeyList__ を取得します。
     * @return rsv100KeyList
     */
    public List<LabelValueBean> getRsv100KeyList() {
        return rsv100KeyList__;
    }

    /**
     * <p>rsv100KeyList__ をセットします。
     * @param rsv100KeyList rsv100KeyList__
     */
    public void setRsv100KeyList(List<LabelValueBean> rsv100KeyList) {
        rsv100KeyList__ = rsv100KeyList;
    }

    /**
     * <p>rsv100LabelListGrp1 を取得します。
     * @return rsv100LabelListGrp1
     */
    public List<LabelValueBean> getRsv100LabelListGrp1() {
        return rsv100LabelListGrp1__;
    }

    /**
     * <p>rsv100LabelListGrp1 をセットします。
     * @param rsv100LabelListGrp1 rsv100LabelListGrp1
     */
    public void setRsv100LabelListGrp1(List<LabelValueBean> rsv100LabelListGrp1) {
        rsv100LabelListGrp1__ = rsv100LabelListGrp1;
    }

    /**
     * <p>rsv100LabelListGrp2 を取得します。
     * @return rsv100LabelListGrp2
     */
    public List<LabelValueBean> getRsv100LabelListGrp2() {
        return rsv100LabelListGrp2__;
    }

    /**
     * <p>rsv100LabelListGrp2 をセットします。
     * @param rsv100LabelListGrp2 rsv100LabelListGrp2
     */
    public void setRsv100LabelListGrp2(List<LabelValueBean> rsv100LabelListGrp2) {
        rsv100LabelListGrp2__ = rsv100LabelListGrp2;
    }

    /**
     * <p>rsv100PageList を取得します。
     * @return rsv100PageList
     */
    public List<LabelValueBean> getRsv100PageList() {
        return rsv100PageList__;
    }

    /**
     * <p>rsv100PageList をセットします。
     * @param rsv100PageList rsv100PageList
     */
    public void setRsv100PageList(List<LabelValueBean> rsv100PageList) {
        rsv100PageList__ = rsv100PageList;
    }
    /**
     * <p>rsv100resultList を取得します。
     * @return rsv100resultList
     */
    public ArrayList<Rsv100SisYrkModel> getRsv100resultList() {
        return rsv100resultList__;
    }

    /**
     * <p>rsv100resultList をセットします。
     * @param rsv100resultList rsv100resultList
     */
    public void setRsv100resultList(ArrayList<Rsv100SisYrkModel> rsv100resultList) {
        rsv100resultList__ = rsv100resultList;
    }

    /**
     * <p>rsv100userSid を取得します。
     * @return rsv100userSid
     */
    public int getRsv100userSid() {
        return rsv100userSid__;
    }

    /**
     * <p>rsv100userSid をセットします。
     * @param rsv100userSid rsv100userSid
     */
    public void setRsv100userSid(int rsv100userSid) {
        rsv100userSid__ = rsv100userSid;
    }
}
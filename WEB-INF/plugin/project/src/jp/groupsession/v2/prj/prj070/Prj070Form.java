package jp.groupsession.v2.prj.prj070;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.GSValidateProject;
import jp.groupsession.v2.prj.prj030.Prj030Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] TODO詳細検索画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj070Form extends Prj030Form {

    //検索条件
    /** カテゴリSID */
    private String prj070scCategorySid__ = GSConstProject.TODO_CATEGORY_ALL;
    /** 担当メンバー */
    private String[] prj070scTantou__;
    /** 重要度 */
    private String[] prj070scJuuyou__;
    /** 状態From */
    private String prj070scStatusFrom__;
    /** 状態To */
    private String prj070scStatusTo__;
    /** 開始予定年 */
    private String prj070scKaisiYoteiYear__;
    /** 開始予定月 */
    private String prj070scKaisiYoteiMonth__;
    /** 開始予定日 */
    private String prj070scKaisiYoteiDay__;
    /** 期限年 */
    private String prj070scKigenYear__;
    /** 期限月 */
    private String prj070scKigenMonth__;
    /** 期限日 */
    private String prj070scKigenDay__;
    /** 開始実績年 */
    private String prj070scKaisiJissekiYear__;
    /** 開始実績月 */
    private String prj070scKaisiJissekiMonth__;
    /** 開始実績日 */
    private String prj070scKaisiJissekiDay__;
    /** 終了実績年 */
    private String prj070scSyuryoJissekiYear__;
    /** 終了実績月 */
    private String prj070scSyuryoJissekiMonth__;
    /** 終了実績日 */
    private String prj070scSyuryoJissekiDay__;
    /** タイトル */
    private String prj070scTitle__;
    /** キーワード検索区分 */
    private String prj070KeyWordkbn__ = String.valueOf(GSConstProject.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] prj070SearchTarget__ = null;
    /** 登録者 */
    private String[] prj070scTourokusya__;
    /** 管理番号 */
    private String prj070scKanriNumber__;
    /** 初期表示フラグ */
    private boolean prj070InitFlg__ = false;
    /** 参加プロジェクトのみ表示フラグ */
    private boolean prj070JoinOnlyFlg__ = false;

    /** 予定開始 年月日 */
    private String prj070strPlanDate__ = null;
    /** 予定終了 年月日 */
    private String prj070endPlanDate__ = null;
    /** 実績開始 年月日 */
    private String prj070strResultDater__ = null;
    /** 実績終了 年月日 */
    private String prj070endResultDate__ = null;

    //save項目
    /** (save)カテゴリSID */
    private String prj070svScCategorySid__;
    /** (save)担当メンバー */
    private String[] prj070svScTantou__;
    /** (save)重要度 */
    private String[] prj070svScJuuyou__;
    /** (save)状態From */
    private String prj070svScStatusFrom__;
    /** (save)状態To */
    private String prj070svScStatusTo__;
    /** (save)開始予定年 */
    private String prj070svScKaisiYoteiYear__;
    /** (save)開始予定月 */
    private String prj070svScKaisiYoteiMonth__;
    /** (save)開始予定日 */
    private String prj070svScKaisiYoteiDay__;
    /** (save)期限年 */
    private String prj070svScKigenYear__;
    /** (save)期限月 */
    private String prj070svScKigenMonth__;
    /** (save)期限日 */
    private String prj070svScKigenDay__;
    /** (save)開始実績年 */
    private String prj070svScKaisiJissekiYear__;
    /** (save)開始実績月 */
    private String prj070svScKaisiJissekiMonth__;
    /** (save)開始実績日 */
    private String prj070svScKaisiJissekiDay__;
    /** (save)終了実績年 */
    private String prj070svScSyuryoJissekiYear__;
    /** (save)終了実績月 */
    private String prj070svScSyuryoJissekiMonth__;
    /** (save)終了実績日 */
    private String prj070svScSyuryoJissekiDay__;
    /** (save)タイトル */
    private String prj070svScTitle__;
    /** (save)キーワード検索区分 */
    private String prj070SvKeyWordkbn__ = String.valueOf(GSConstProject.KEY_WORD_KBN_AND);
    /** (save)検索対象 */
    private String[] prj070SvSearchTarget__ = null;
    /** (save)管理番号 */
    private String prj070SvKanriNumber__;
    /** (save)登録者 */
    private String[] prj070svScTourokusya__;

    /** ページ1 */
    private int prj070page1__;
    /** ページ2 */
    private int prj070page2__;
    /** ソートキー */
    private int prj070sort__ = GSConstProject.SORT_TODO_WEIGHT;
    /** オーダーキー */
    private int prj070order__ = GSConst.ORDER_KEY_DESC;

    //表示項目
    /** プロジェクトラベル */
    private List<LabelValueBean> projectLabel__;
    /** カテゴリラベル */
    private List<LabelValueBean> categoryLabel__;

    /**
     * <br>[機  能] 入力チェックを行う(検索)
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public ActionErrors validate070(HttpServletRequest req)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ActionErrors errors = new ActionErrors();
        int errSize = 0;
        GSValidateProject gsValidatePrj = new GSValidateProject(req);
        errSize = errors.size();
        GsMessage gsMsg = new GsMessage();
        //状態To
        String textStatusTo = gsMsg.getMessage(req, "project.src.45");
        //状態From
        String textStatusFrom = gsMsg.getMessage(req, "project.src.40");
        //状態From
        GSValidateProject.validateTextBoxInputNumLenge(
                errors,
                prj070scStatusFrom__,
                textStatusFrom,
                GSConstProject.RATE_MIN,
                GSConstProject.RATE_MAX,
                false);
        //状態To
        GSValidateProject.validateTextBoxInputNumLenge(
                errors,
                prj070scStatusTo__,
                textStatusTo,
                GSConstProject.RATE_MIN,
                GSConstProject.RATE_MAX,
                false);
        if (errSize == errors.size()
        && !NullDefault.getString(prj070scStatusFrom__, "").equals("")
        && !NullDefault.getString(prj070scStatusTo__, "").equals("")) {
            //大小チェック
            GSValidateProject.validateTextBoxFromTo(
                    errors,
                    prj070scStatusFrom__,
                    prj070scStatusTo__,
                    textStatusFrom,
                    textStatusTo);
        }

        errSize = errors.size();

        DateTimePickerBiz picker = new DateTimePickerBiz();

        //予定開始
        String textStartYotei = gsMsg.getMessage(req, "project.prj070.2");
        //開始予定
        if (!StringUtil.isNullZeroString(prj070strPlanDate__)) {
            errors.add(picker.setYmdParam(this, "prj070strPlanDate",
                    "prj070scKaisiYoteiYear", "prj070scKaisiYoteiMonth",
                    "prj070scKaisiYoteiDay", textStartYotei));
        } else {
            picker.setYmdParam(this, "prj070strPlanDate",
                    "prj070scKaisiYoteiYear", "prj070scKaisiYoteiMonth",
                    "prj070scKaisiYoteiDay", textStartYotei);
        }
        if (errSize == errors.size()) {
            gsValidatePrj.validateYMD(
                    errors,
                    textStartYotei,
                    prj070scKaisiYoteiYear__,
                    prj070scKaisiYoteiMonth__,
                    prj070scKaisiYoteiDay__,
                    false);
        }
        //予定終了
        String textKigen = gsMsg.getMessage(req, "project.prj070.4");
        //期限
        if (!StringUtil.isNullZeroString(prj070endPlanDate__)) {
            errors.add(picker.setYmdParam(this, "prj070endPlanDate",
                    "prj070scKigenYear", "prj070scKigenMonth",
                    "prj070scKigenDay", textKigen));
        } else {
            picker.setYmdParam(this, "prj070endPlanDate",
                    "prj070scKigenYear", "prj070scKigenMonth",
                    "prj070scKigenDay", textKigen);
        }
        if (errSize == errors.size()) {
            gsValidatePrj.validateYMD(
                    errors,
                    textKigen,
                    prj070scKigenYear__,
                    prj070scKigenMonth__,
                    prj070scKigenDay__,
                    false);
        }

        if (errSize == errors.size()
                && !NullDefault.getString(prj070scKaisiYoteiYear__, "").equals("")
                && !NullDefault.getString(prj070scKaisiYoteiMonth__, "").equals("")
                && !NullDefault.getString(prj070scKaisiYoteiDay__, "").equals("")
                && !NullDefault.getString(prj070scKigenYear__, "").equals("")
                && !NullDefault.getString(prj070scKigenMonth__, "").equals("")
                && !NullDefault.getString(prj070scKigenDay__, "").equals("")) {
                    //大小チェック
                    UDate dateStart = new UDate();
                    dateStart.setDate(NullDefault.getInt(prj070scKaisiYoteiYear__, -1),
                                      NullDefault.getInt(prj070scKaisiYoteiMonth__, -1),
                                      NullDefault.getInt(prj070scKaisiYoteiDay__, -1));
                    UDate dateEnd = new UDate();
                    dateEnd.setDate(NullDefault.getInt(prj070scKigenYear__, -1),
                                    NullDefault.getInt(prj070scKigenMonth__, -1),
                                    NullDefault.getInt(prj070scKigenDay__, -1));
                    GSValidateProject.validateDataRange(
                            errors,
                            textStartYotei,
                            textKigen,
                            dateStart,
                            dateEnd);
                }

        errSize = errors.size();
        //実績開始
        String textJisseki = gsMsg.getMessage(req, "project.prj070.5");
        //開始実績
        if (!StringUtil.isNullZeroString(prj070strResultDater__)) {
            errors.add(picker.setYmdParam(this, "prj070strResultDater",
                    "prj070scKaisiJissekiYear", "prj070scKaisiJissekiMonth",
                    "prj070scKaisiJissekiDay", textJisseki));
        } else {
            picker.setYmdParam(this, "prj070strResultDater",
                    "prj070scKaisiJissekiYear", "prj070scKaisiJissekiMonth",
                    "prj070scKaisiJissekiDay", textJisseki);
        }
        if (errSize == errors.size()) {
            gsValidatePrj.validateYMD(
                    errors,
                    textJisseki,
                    prj070scKaisiJissekiYear__,
                    prj070scKaisiJissekiMonth__,
                    prj070scKaisiJissekiDay__,
                    false);
        }
        //実績終了
        String textEndJisseki = gsMsg.getMessage(req, "project.prj070.6");
        //終了実績
        if (!StringUtil.isNullZeroString(prj070endResultDate__)) {
            errors.add(picker.setYmdParam(this, "prj070endResultDate",
                    "prj070scSyuryoJissekiYear", "prj070scSyuryoJissekiMonth",
                    "prj070scSyuryoJissekiDay", textEndJisseki));
        } else {
            picker.setYmdParam(this, "prj070endResultDate",
                    "prj070scSyuryoJissekiYear", "prj070scSyuryoJissekiMonth",
                    "prj070scSyuryoJissekiDay", textEndJisseki);
        }
        if (errSize == errors.size()) {
            gsValidatePrj.validateYMD(
                    errors,
                    textEndJisseki,
                    prj070scSyuryoJissekiYear__,
                    prj070scSyuryoJissekiMonth__,
                    prj070scSyuryoJissekiDay__,
                    false);
        }

        if (errSize == errors.size()
                && !NullDefault.getString(prj070scKaisiJissekiYear__, "").equals("")
                && !NullDefault.getString(prj070scKaisiJissekiMonth__, "").equals("")
                && !NullDefault.getString(prj070scKaisiJissekiDay__, "").equals("")
                && !NullDefault.getString(prj070scSyuryoJissekiYear__, "").equals("")
                && !NullDefault.getString(prj070scSyuryoJissekiMonth__, "").equals("")
                && !NullDefault.getString(prj070scSyuryoJissekiDay__, "").equals("")) {
                    //大小チェック
                    UDate dateStart = new UDate();
                    dateStart.setDate(NullDefault.getInt(prj070scKaisiJissekiYear__, -1),
                                      NullDefault.getInt(prj070scKaisiJissekiMonth__, -1),
                                      NullDefault.getInt(prj070scKaisiJissekiDay__, -1));
                    UDate dateEnd = new UDate();
                    dateEnd.setDate(NullDefault.getInt(prj070scSyuryoJissekiYear__, -1),
                                    NullDefault.getInt(prj070scSyuryoJissekiMonth__, -1),
                                    NullDefault.getInt(prj070scSyuryoJissekiDay__, -1));
                    GSValidateProject.validateDataRange(
                            errors,
                            gsMsg.getMessage(req, "project.prj070.5"),
                            textEndJisseki,
                            dateStart,
                            dateEnd);
                }
        //タイトル
        String textTitle = gsMsg.getMessage(req, "cmn.title");
        //タイトル
        GSValidateProject.validateTextBoxInput(
                errors,
                prj070scTitle__,
                textTitle,
                GSConstProject.MAX_LENGTH_TODO_TITLE,
                false);

        //ＫＥＹワードチェック
        GSValidateProject gsVal = new GSValidateProject(req);
        gsVal.validateSearchTarget(errors, prj070scTitle__, prj070SearchTarget__);

        //管理番号チェック
        String kanriNumber = gsMsg.getMessage(req, "project.prj050.5");
        GSValidateProject.validateTextBoxInputNum(
                errors, prj070scKanriNumber__, kanriNumber, 8,  false);

        return errors;
    }

    /**
     * <br>[機  能] 検索条件パラメータをセーブフィールドへ移行
     * <br>[解  説]
     * <br>[備  考]
     */
    public void saveSearchParm070() {

        //(save)プロジェクトSID
        setPrj070svScPrjSid(getPrj070scPrjSid());
        //(save)カテゴリSID
        setPrj070svScCategorySid(getPrj070scCategorySid());
        //(save)担当メンバー
        prj070svScTantou__ = prj070scTantou__;
        //(save)重要度
        prj070svScJuuyou__ = prj070scJuuyou__;
        //(save)状態From
        prj070svScStatusFrom__ = prj070scStatusFrom__;
        //(save)状態To
        prj070svScStatusTo__ = prj070scStatusTo__;
        //(save)開始予定年
        prj070svScKaisiYoteiYear__ = prj070scKaisiYoteiYear__;
        //(save)開始予定月
        prj070svScKaisiYoteiMonth__ = prj070scKaisiYoteiMonth__;
        //(save)開始予定日
        prj070svScKaisiYoteiDay__ = prj070scKaisiYoteiDay__;
        //(save)期限年
        prj070svScKigenYear__ = prj070scKigenYear__;
        //(save)期限月
        prj070svScKigenMonth__ = prj070scKigenMonth__;
        //(save)期限日
        prj070svScKigenDay__ = prj070scKigenDay__;
        //(save)開始実績年
        prj070svScKaisiJissekiYear__ = prj070scKaisiJissekiYear__;
        //(save)開始実績月
        prj070svScKaisiJissekiMonth__ = prj070scKaisiJissekiMonth__;
        //(save)開始実績日
        prj070svScKaisiJissekiDay__ = prj070scKaisiJissekiDay__;
        //(save)終了実績年
        prj070svScSyuryoJissekiYear__ = prj070scSyuryoJissekiYear__;
        //(save)終了実績月
        prj070svScSyuryoJissekiMonth__ = prj070scSyuryoJissekiMonth__;
        //(save)終了実績日
        prj070svScSyuryoJissekiDay__ = prj070scSyuryoJissekiDay__;
        //(save)タイトル
        prj070svScTitle__ = prj070scTitle__;
        //(save)キーワード検索区分
        prj070SvKeyWordkbn__ = prj070KeyWordkbn__;
        //(save)検索対象
        prj070SvSearchTarget__ = prj070SearchTarget__;
        //(save)管理番号
        prj070SvKanriNumber__ = prj070scKanriNumber__;
        //(save)登録者
        prj070svScTourokusya__ = prj070scTourokusya__;
    }

    /**
     * <br>[機  能] Cmn999Formに画面パラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form Cmn999Form
     */
    public void setcmn999FormParam(Cmn999Form cmn999Form) {

        super.setcmn999FormParam(cmn999Form);

        cmn999Form.addHiddenParam("prj070scrId", getPrj070scrId());
        cmn999Form.addHiddenParam("prj070scPrjSid", getPrj070scPrjSid());
        cmn999Form.addHiddenParam("prj070scCategorySid", getPrj070scCategorySid());
        cmn999Form.addHiddenParam("prj070scTantou", prj070scTantou__);
        cmn999Form.addHiddenParam("prj070scJuuyou", prj070scJuuyou__);
        cmn999Form.addHiddenParam("prj070scStatusFrom", prj070scStatusFrom__);
        cmn999Form.addHiddenParam("prj070scStatusTo", prj070scStatusTo__);
        cmn999Form.addHiddenParam("prj070scKaisiYoteiYear", prj070scKaisiYoteiYear__);
        cmn999Form.addHiddenParam("prj070scKaisiYoteiMonth", prj070scKaisiYoteiMonth__);
        cmn999Form.addHiddenParam("prj070scKaisiYoteiDay", prj070scKaisiYoteiDay__);
        cmn999Form.addHiddenParam("prj070scKigenYear", prj070scKigenYear__);
        cmn999Form.addHiddenParam("prj070scKigenMonth", prj070scKigenMonth__);
        cmn999Form.addHiddenParam("prj070scKigenDay", prj070scKigenDay__);
        cmn999Form.addHiddenParam("prj070scKaisiJissekiYear", prj070scKaisiJissekiYear__);
        cmn999Form.addHiddenParam("prj070scKaisiJissekiMonth", prj070scKaisiJissekiMonth__);
        cmn999Form.addHiddenParam("prj070scKaisiJissekiDay", prj070scKaisiJissekiDay__);
        cmn999Form.addHiddenParam("prj070scSyuryoJissekiYear", prj070scSyuryoJissekiYear__);
        cmn999Form.addHiddenParam("prj070scSyuryoJissekiMonth", prj070scSyuryoJissekiMonth__);
        cmn999Form.addHiddenParam("prj070scSyuryoJissekiDay", prj070scSyuryoJissekiMonth__);
        cmn999Form.addHiddenParam("prj070scTitle", prj070scTitle__);
        cmn999Form.addHiddenParam("prj070scTourokusya", prj070scTourokusya__);
        cmn999Form.addHiddenParam("prj070KeyWordkbn", prj070KeyWordkbn__);
        cmn999Form.addHiddenParam("prj070SearchTarget", prj070SearchTarget__);
        cmn999Form.addHiddenParam("prj070svScPrjSid", getPrj070svScPrjSid());
        cmn999Form.addHiddenParam("prj070svScCategorySid", prj070svScCategorySid__);
        cmn999Form.addHiddenParam("prj070svScTantou", prj070svScTantou__);
        cmn999Form.addHiddenParam("prj070svScJuuyou", prj070svScJuuyou__);
        cmn999Form.addHiddenParam("prj070svScStatusFrom", prj070svScStatusFrom__);
        cmn999Form.addHiddenParam("prj070svScStatusTo", prj070svScStatusTo__);
        cmn999Form.addHiddenParam("prj070svScKaisiYoteiYear", prj070svScKaisiYoteiYear__);
        cmn999Form.addHiddenParam("prj070svScKaisiYoteiMonth", prj070svScKaisiYoteiMonth__);
        cmn999Form.addHiddenParam("prj070svScKaisiYoteiDay", prj070svScKaisiYoteiDay__);
        cmn999Form.addHiddenParam("prj070svScKigenYear", prj070svScKigenYear__);
        cmn999Form.addHiddenParam("prj070svScKigenMonth", prj070svScKigenMonth__);
        cmn999Form.addHiddenParam("prj070svScKigenDay", prj070svScKigenDay__);
        cmn999Form.addHiddenParam("prj070svScKaisiJissekiYear", prj070svScKaisiJissekiYear__);
        cmn999Form.addHiddenParam("prj070svScKaisiJissekiMonth", prj070svScKaisiJissekiMonth__);
        cmn999Form.addHiddenParam("prj070svScKaisiJissekiDay", prj070svScKaisiJissekiDay__);
        cmn999Form.addHiddenParam("prj070svScSyuryoJissekiYear", prj070svScSyuryoJissekiYear__);
        cmn999Form.addHiddenParam("prj070svScSyuryoJissekiMonth", prj070svScSyuryoJissekiMonth__);
        cmn999Form.addHiddenParam("prj070svScSyuryoJissekiDay", prj070svScSyuryoJissekiDay__);
        cmn999Form.addHiddenParam("prj070svScTitle", prj070svScTitle__);
        cmn999Form.addHiddenParam("prj070svScTourokusya", prj070svScTourokusya__);
        cmn999Form.addHiddenParam("prj070SvKeyWordkbn", prj070SvKeyWordkbn__);
        cmn999Form.addHiddenParam("prj070SvSearchTarget", prj070SvSearchTarget__);
        cmn999Form.addHiddenParam("prj070page1", prj070page1__);
        cmn999Form.addHiddenParam("prj070page2", prj070page2__);
        cmn999Form.addHiddenParam("prj070sort", prj070sort__);
        cmn999Form.addHiddenParam("prj070order", prj070order__);
        cmn999Form.addHiddenParam("prj070searchFlg", getPrj070searchFlg());
        cmn999Form.addHiddenParam("prj070InitFlg", String.valueOf(prj070InitFlg__));
        cmn999Form.addHiddenParam("prj070SvKanriNumber", prj070SvKanriNumber__);
    }

    /**
     * <p>prj070scTantou を取得します。
     * @return prj070scTantou
     */
    public String[] getPrj070scTantou() {
        return prj070scTantou__;
    }
    /**
     * <p>prj070scTantou をセットします。
     * @param prj070scTantou prj070scTantou
     */
    public void setPrj070scTantou(String[] prj070scTantou) {
        prj070scTantou__ = prj070scTantou;
    }
    /**
     * <p>prj070scJuuyou を取得します。
     * @return prj070scJuuyou
     */
    public String[] getPrj070scJuuyou() {
        return prj070scJuuyou__;
    }
    /**
     * <p>prj070scJuuyou をセットします。
     * @param prj070scJuuyou prj070scJuuyou
     */
    public void setPrj070scJuuyou(String[] prj070scJuuyou) {
        prj070scJuuyou__ = prj070scJuuyou;
    }
    /**
     * <p>prj070scStatusFrom を取得します。
     * @return prj070scStatusFrom
     */
    public String getPrj070scStatusFrom() {
        return prj070scStatusFrom__;
    }
    /**
     * <p>prj070scStatusFrom をセットします。
     * @param prj070scStatusFrom prj070scStatusFrom
     */
    public void setPrj070scStatusFrom(String prj070scStatusFrom) {
        prj070scStatusFrom__ = prj070scStatusFrom;
    }
    /**
     * <p>prj070scStatusTo を取得します。
     * @return prj070scStatusTo
     */
    public String getPrj070scStatusTo() {
        return prj070scStatusTo__;
    }
    /**
     * <p>prj070scStatusTo をセットします。
     * @param prj070scStatusTo prj070scStatusTo
     */
    public void setPrj070scStatusTo(String prj070scStatusTo) {
        prj070scStatusTo__ = prj070scStatusTo;
    }
    /**
     * <p>prj070scKaisiYoteiYear を取得します。
     * @return prj070scKaisiYoteiYear
     */
    public String getPrj070scKaisiYoteiYear() {
        return prj070scKaisiYoteiYear__;
    }
    /**
     * <p>prj070scKaisiYoteiYear をセットします。
     * @param prj070scKaisiYoteiYear prj070scKaisiYoteiYear
     */
    public void setPrj070scKaisiYoteiYear(String prj070scKaisiYoteiYear) {
        prj070scKaisiYoteiYear__ = prj070scKaisiYoteiYear;
    }
    /**
     * <p>prj070scKaisiYoteiMonth を取得します。
     * @return prj070scKaisiYoteiMonth
     */
    public String getPrj070scKaisiYoteiMonth() {
        return prj070scKaisiYoteiMonth__;
    }
    /**
     * <p>prj070scKaisiYoteiMonth をセットします。
     * @param prj070scKaisiYoteiMonth prj070scKaisiYoteiMonth
     */
    public void setPrj070scKaisiYoteiMonth(String prj070scKaisiYoteiMonth) {
        prj070scKaisiYoteiMonth__ = prj070scKaisiYoteiMonth;
    }
    /**
     * <p>prj070scKaisiYoteiDay を取得します。
     * @return prj070scKaisiYoteiDay
     */
    public String getPrj070scKaisiYoteiDay() {
        return prj070scKaisiYoteiDay__;
    }
    /**
     * <p>prj070scKaisiYoteiDay をセットします。
     * @param prj070scKaisiYoteiDay prj070scKaisiYoteiDay
     */
    public void setPrj070scKaisiYoteiDay(String prj070scKaisiYoteiDay) {
        prj070scKaisiYoteiDay__ = prj070scKaisiYoteiDay;
    }
    /**
     * <p>prj070scKigenYear を取得します。
     * @return prj070scKigenYear
     */
    public String getPrj070scKigenYear() {
        return prj070scKigenYear__;
    }
    /**
     * <p>prj070scKigenYear をセットします。
     * @param prj070scKigenYear prj070scKigenYear
     */
    public void setPrj070scKigenYear(String prj070scKigenYear) {
        prj070scKigenYear__ = prj070scKigenYear;
    }
    /**
     * <p>prj070scKigenMonth を取得します。
     * @return prj070scKigenMonth
     */
    public String getPrj070scKigenMonth() {
        return prj070scKigenMonth__;
    }
    /**
     * <p>prj070scKigenMonth をセットします。
     * @param prj070scKigenMonth prj070scKigenMonth
     */
    public void setPrj070scKigenMonth(String prj070scKigenMonth) {
        prj070scKigenMonth__ = prj070scKigenMonth;
    }
    /**
     * <p>prj070scKigenDay を取得します。
     * @return prj070scKigenDay
     */
    public String getPrj070scKigenDay() {
        return prj070scKigenDay__;
    }
    /**
     * <p>prj070scKigenDay をセットします。
     * @param prj070scKigenDay prj070scKigenDay
     */
    public void setPrj070scKigenDay(String prj070scKigenDay) {
        prj070scKigenDay__ = prj070scKigenDay;
    }
    /**
     * <p>prj070scKaisiJissekiYear を取得します。
     * @return prj070scKaisiJissekiYear
     */
    public String getPrj070scKaisiJissekiYear() {
        return prj070scKaisiJissekiYear__;
    }
    /**
     * <p>prj070scKaisiJissekiYear をセットします。
     * @param prj070scKaisiJissekiYear prj070scKaisiJissekiYear
     */
    public void setPrj070scKaisiJissekiYear(String prj070scKaisiJissekiYear) {
        prj070scKaisiJissekiYear__ = prj070scKaisiJissekiYear;
    }
    /**
     * <p>prj070scKaisiJissekiMonth を取得します。
     * @return prj070scKaisiJissekiMonth
     */
    public String getPrj070scKaisiJissekiMonth() {
        return prj070scKaisiJissekiMonth__;
    }
    /**
     * <p>prj070scKaisiJissekiMonth をセットします。
     * @param prj070scKaisiJissekiMonth prj070scKaisiJissekiMonth
     */
    public void setPrj070scKaisiJissekiMonth(String prj070scKaisiJissekiMonth) {
        prj070scKaisiJissekiMonth__ = prj070scKaisiJissekiMonth;
    }
    /**
     * <p>prj070scKaisiJissekiDay を取得します。
     * @return prj070scKaisiJissekiDay
     */
    public String getPrj070scKaisiJissekiDay() {
        return prj070scKaisiJissekiDay__;
    }
    /**
     * <p>prj070scKaisiJissekiDay をセットします。
     * @param prj070scKaisiJissekiDay prj070scKaisiJissekiDay
     */
    public void setPrj070scKaisiJissekiDay(String prj070scKaisiJissekiDay) {
        prj070scKaisiJissekiDay__ = prj070scKaisiJissekiDay;
    }
    /**
     * <p>prj070scSyuryoJissekiYear を取得します。
     * @return prj070scSyuryoJissekiYear
     */
    public String getPrj070scSyuryoJissekiYear() {
        return prj070scSyuryoJissekiYear__;
    }
    /**
     * <p>prj070scSyuryoJissekiYear をセットします。
     * @param prj070scSyuryoJissekiYear prj070scSyuryoJissekiYear
     */
    public void setPrj070scSyuryoJissekiYear(String prj070scSyuryoJissekiYear) {
        prj070scSyuryoJissekiYear__ = prj070scSyuryoJissekiYear;
    }
    /**
     * <p>prj070scSyuryoJissekiMonth を取得します。
     * @return prj070scSyuryoJissekiMonth
     */
    public String getPrj070scSyuryoJissekiMonth() {
        return prj070scSyuryoJissekiMonth__;
    }
    /**
     * <p>prj070scSyuryoJissekiMonth をセットします。
     * @param prj070scSyuryoJissekiMonth prj070scSyuryoJissekiMonth
     */
    public void setPrj070scSyuryoJissekiMonth(String prj070scSyuryoJissekiMonth) {
        prj070scSyuryoJissekiMonth__ = prj070scSyuryoJissekiMonth;
    }
    /**
     * <p>prj070scSyuryoJissekiDay を取得します。
     * @return prj070scSyuryoJissekiDay
     */
    public String getPrj070scSyuryoJissekiDay() {
        return prj070scSyuryoJissekiDay__;
    }
    /**
     * <p>prj070scSyuryoJissekiDay をセットします。
     * @param prj070scSyuryoJissekiDay prj070scSyuryoJissekiDay
     */
    public void setPrj070scSyuryoJissekiDay(String prj070scSyuryoJissekiDay) {
        prj070scSyuryoJissekiDay__ = prj070scSyuryoJissekiDay;
    }
    /**
     * <p>prj070scTitle を取得します。
     * @return prj070scTitle
     */
    public String getPrj070scTitle() {
        return prj070scTitle__;
    }
    /**
     * <p>prj070scTitle をセットします。
     * @param prj070scTitle prj070scTitle
     */
    public void setPrj070scTitle(String prj070scTitle) {
        prj070scTitle__ = prj070scTitle;
    }
    /**
     * <p>prj070scTourokusya を取得します。
     * @return prj070scTourokusya
     */
    public String[] getPrj070scTourokusya() {
        return prj070scTourokusya__;
    }
    /**
     * <p>prj070scTourokusya をセットします。
     * @param prj070scTourokusya prj070scTourokusya
     */
    public void setPrj070scTourokusya(String[] prj070scTourokusya) {
        prj070scTourokusya__ = prj070scTourokusya;
    }
    /**
     * <p>prj070svScTantou を取得します。
     * @return prj070svScTantou
     */
    public String[] getPrj070svScTantou() {
        return prj070svScTantou__;
    }
    /**
     * <p>prj070svScTantou をセットします。
     * @param prj070svScTantou prj070svScTantou
     */
    public void setPrj070svScTantou(String[] prj070svScTantou) {
        prj070svScTantou__ = prj070svScTantou;
    }
    /**
     * <p>prj070svScStatusFrom を取得します。
     * @return prj070svScStatusFrom
     */
    public String getPrj070svScStatusFrom() {
        return prj070svScStatusFrom__;
    }
    /**
     * <p>prj070svScStatusFrom をセットします。
     * @param prj070svScStatusFrom prj070svScStatusFrom
     */
    public void setPrj070svScStatusFrom(String prj070svScStatusFrom) {
        prj070svScStatusFrom__ = prj070svScStatusFrom;
    }
    /**
     * <p>prj070svScStatusTo を取得します。
     * @return prj070svScStatusTo
     */
    public String getPrj070svScStatusTo() {
        return prj070svScStatusTo__;
    }
    /**
     * <p>prj070svScStatusTo をセットします。
     * @param prj070svScStatusTo prj070svScStatusTo
     */
    public void setPrj070svScStatusTo(String prj070svScStatusTo) {
        prj070svScStatusTo__ = prj070svScStatusTo;
    }
    /**
     * <p>prj070svScKaisiYoteiYear を取得します。
     * @return prj070svScKaisiYoteiYear
     */
    public String getPrj070svScKaisiYoteiYear() {
        return prj070svScKaisiYoteiYear__;
    }
    /**
     * <p>prj070svScKaisiYoteiYear をセットします。
     * @param prj070svScKaisiYoteiYear prj070svScKaisiYoteiYear
     */
    public void setPrj070svScKaisiYoteiYear(String prj070svScKaisiYoteiYear) {
        prj070svScKaisiYoteiYear__ = prj070svScKaisiYoteiYear;
    }
    /**
     * <p>prj070svScKaisiYoteiMonth を取得します。
     * @return prj070svScKaisiYoteiMonth
     */
    public String getPrj070svScKaisiYoteiMonth() {
        return prj070svScKaisiYoteiMonth__;
    }
    /**
     * <p>prj070svScKaisiYoteiMonth をセットします。
     * @param prj070svScKaisiYoteiMonth prj070svScKaisiYoteiMonth
     */
    public void setPrj070svScKaisiYoteiMonth(String prj070svScKaisiYoteiMonth) {
        prj070svScKaisiYoteiMonth__ = prj070svScKaisiYoteiMonth;
    }
    /**
     * <p>prj070svScKaisiYoteiDay を取得します。
     * @return prj070svScKaisiYoteiDay
     */
    public String getPrj070svScKaisiYoteiDay() {
        return prj070svScKaisiYoteiDay__;
    }
    /**
     * <p>prj070svScKaisiYoteiDay をセットします。
     * @param prj070svScKaisiYoteiDay prj070svScKaisiYoteiDay
     */
    public void setPrj070svScKaisiYoteiDay(String prj070svScKaisiYoteiDay) {
        prj070svScKaisiYoteiDay__ = prj070svScKaisiYoteiDay;
    }
    /**
     * <p>prj070svScKigenYear を取得します。
     * @return prj070svScKigenYear
     */
    public String getPrj070svScKigenYear() {
        return prj070svScKigenYear__;
    }
    /**
     * <p>prj070svScKigenYear をセットします。
     * @param prj070svScKigenYear prj070svScKigenYear
     */
    public void setPrj070svScKigenYear(String prj070svScKigenYear) {
        prj070svScKigenYear__ = prj070svScKigenYear;
    }
    /**
     * <p>prj070svScKigenMonth を取得します。
     * @return prj070svScKigenMonth
     */
    public String getPrj070svScKigenMonth() {
        return prj070svScKigenMonth__;
    }
    /**
     * <p>prj070svScKigenMonth をセットします。
     * @param prj070svScKigenMonth prj070svScKigenMonth
     */
    public void setPrj070svScKigenMonth(String prj070svScKigenMonth) {
        prj070svScKigenMonth__ = prj070svScKigenMonth;
    }
    /**
     * <p>prj070svScKigenDay を取得します。
     * @return prj070svScKigenDay
     */
    public String getPrj070svScKigenDay() {
        return prj070svScKigenDay__;
    }
    /**
     * <p>prj070svScKigenDay をセットします。
     * @param prj070svScKigenDay prj070svScKigenDay
     */
    public void setPrj070svScKigenDay(String prj070svScKigenDay) {
        prj070svScKigenDay__ = prj070svScKigenDay;
    }
    /**
     * <p>prj070svScKaisiJissekiYear を取得します。
     * @return prj070svScKaisiJissekiYear
     */
    public String getPrj070svScKaisiJissekiYear() {
        return prj070svScKaisiJissekiYear__;
    }
    /**
     * <p>prj070svScKaisiJissekiYear をセットします。
     * @param prj070svScKaisiJissekiYear prj070svScKaisiJissekiYear
     */
    public void setPrj070svScKaisiJissekiYear(String prj070svScKaisiJissekiYear) {
        prj070svScKaisiJissekiYear__ = prj070svScKaisiJissekiYear;
    }
    /**
     * <p>prj070svScKaisiJissekiMonth を取得します。
     * @return prj070svScKaisiJissekiMonth
     */
    public String getPrj070svScKaisiJissekiMonth() {
        return prj070svScKaisiJissekiMonth__;
    }
    /**
     * <p>prj070svScKaisiJissekiMonth をセットします。
     * @param prj070svScKaisiJissekiMonth prj070svScKaisiJissekiMonth
     */
    public void setPrj070svScKaisiJissekiMonth(String prj070svScKaisiJissekiMonth) {
        prj070svScKaisiJissekiMonth__ = prj070svScKaisiJissekiMonth;
    }
    /**
     * <p>prj070svScKaisiJissekiDay を取得します。
     * @return prj070svScKaisiJissekiDay
     */
    public String getPrj070svScKaisiJissekiDay() {
        return prj070svScKaisiJissekiDay__;
    }
    /**
     * <p>prj070svScKaisiJissekiDay をセットします。
     * @param prj070svScKaisiJissekiDay prj070svScKaisiJissekiDay
     */
    public void setPrj070svScKaisiJissekiDay(String prj070svScKaisiJissekiDay) {
        prj070svScKaisiJissekiDay__ = prj070svScKaisiJissekiDay;
    }
    /**
     * <p>prj070svScSyuryoJissekiYear を取得します。
     * @return prj070svScSyuryoJissekiYear
     */
    public String getPrj070svScSyuryoJissekiYear() {
        return prj070svScSyuryoJissekiYear__;
    }
    /**
     * <p>prj070svScSyuryoJissekiYear をセットします。
     * @param prj070svScSyuryoJissekiYear prj070svScSyuryoJissekiYear
     */
    public void setPrj070svScSyuryoJissekiYear(String prj070svScSyuryoJissekiYear) {
        prj070svScSyuryoJissekiYear__ = prj070svScSyuryoJissekiYear;
    }
    /**
     * <p>prj070svScSyuryoJissekiMonth を取得します。
     * @return prj070svScSyuryoJissekiMonth
     */
    public String getPrj070svScSyuryoJissekiMonth() {
        return prj070svScSyuryoJissekiMonth__;
    }
    /**
     * <p>prj070svScSyuryoJissekiMonth をセットします。
     * @param prj070svScSyuryoJissekiMonth prj070svScSyuryoJissekiMonth
     */
    public void setPrj070svScSyuryoJissekiMonth(String prj070svScSyuryoJissekiMonth) {
        prj070svScSyuryoJissekiMonth__ = prj070svScSyuryoJissekiMonth;
    }
    /**
     * <p>prj070svScSyuryoJissekiDay を取得します。
     * @return prj070svScSyuryoJissekiDay
     */
    public String getPrj070svScSyuryoJissekiDay() {
        return prj070svScSyuryoJissekiDay__;
    }
    /**
     * <p>prj070svScSyuryoJissekiDay をセットします。
     * @param prj070svScSyuryoJissekiDay prj070svScSyuryoJissekiDay
     */
    public void setPrj070svScSyuryoJissekiDay(String prj070svScSyuryoJissekiDay) {
        prj070svScSyuryoJissekiDay__ = prj070svScSyuryoJissekiDay;
    }
    /**
     * <p>prj070svScTitle を取得します。
     * @return prj070svScTitle
     */
    public String getPrj070svScTitle() {
        return prj070svScTitle__;
    }
    /**
     * <p>prj070svScTitle をセットします。
     * @param prj070svScTitle prj070svScTitle
     */
    public void setPrj070svScTitle(String prj070svScTitle) {
        prj070svScTitle__ = prj070svScTitle;
    }
    /**
     * <p>prj070svScTourokusya を取得します。
     * @return prj070svScTourokusya
     */
    public String[] getPrj070svScTourokusya() {
        return prj070svScTourokusya__;
    }
    /**
     * <p>prj070svScTourokusya をセットします。
     * @param prj070svScTourokusya prj070svScTourokusya
     */
    public void setPrj070svScTourokusya(String[] prj070svScTourokusya) {
        prj070svScTourokusya__ = prj070svScTourokusya;
    }
    /**
     * <p>prj070page1 を取得します。
     * @return prj070page1
     */
    public int getPrj070page1() {
        return prj070page1__;
    }
    /**
     * <p>prj070page1 をセットします。
     * @param prj070page1 prj070page1
     */
    public void setPrj070page1(int prj070page1) {
        prj070page1__ = prj070page1;
    }
    /**
     * <p>prj070page2 を取得します。
     * @return prj070page2
     */
    public int getPrj070page2() {
        return prj070page2__;
    }
    /**
     * <p>prj070page2 をセットします。
     * @param prj070page2 prj070page2
     */
    public void setPrj070page2(int prj070page2) {
        prj070page2__ = prj070page2;
    }
    /**
     * <p>prj070sort を取得します。
     * @return prj070sort
     */
    public int getPrj070sort() {
        return prj070sort__;
    }
    /**
     * <p>prj070sort をセットします。
     * @param prj070sort prj070sort
     */
    public void setPrj070sort(int prj070sort) {
        prj070sort__ = prj070sort;
    }
    /**
     * <p>prj070order を取得します。
     * @return prj070order
     */
    public int getPrj070order() {
        return prj070order__;
    }
    /**
     * <p>prj070order をセットします。
     * @param prj070order prj070order
     */
    public void setPrj070order(int prj070order) {
        prj070order__ = prj070order;
    }
    /**
     * <p>prj070svScJuuyou を取得します。
     * @return prj070svScJuuyou
     */
    public String[] getPrj070svScJuuyou() {
        return prj070svScJuuyou__;
    }
    /**
     * <p>prj070svScJuuyou をセットします。
     * @param prj070svScJuuyou prj070svScJuuyou
     */
    public void setPrj070svScJuuyou(String[] prj070svScJuuyou) {
        prj070svScJuuyou__ = prj070svScJuuyou;
    }
    /**
     * <p>projectLabel を取得します。
     * @return projectLabel
     */
    public List<LabelValueBean> getProjectLabel() {
        return projectLabel__;
    }
    /**
     * <p>projectLabel をセットします。
     * @param projectLabel projectLabel
     */
    public void setProjectLabel(List<LabelValueBean> projectLabel) {
        projectLabel__ = projectLabel;
    }

    /**
     * <p>prj070scCategorySid を取得します。
     * @return prj070scCategorySid
     */
    public String getPrj070scCategorySid() {
        return prj070scCategorySid__;
    }

    /**
     * <p>prj070scCategorySid をセットします。
     * @param prj070scCategorySid prj070scCategorySid
     */
    public void setPrj070scCategorySid(String prj070scCategorySid) {
        prj070scCategorySid__ = prj070scCategorySid;
    }

    /**
     * <p>prj070svScCategorySid を取得します。
     * @return prj070svScCategorySid
     */
    public String getPrj070svScCategorySid() {
        return prj070svScCategorySid__;
    }

    /**
     * <p>prj070svScCategorySid をセットします。
     * @param prj070svScCategorySid prj070svScCategorySid
     */
    public void setPrj070svScCategorySid(String prj070svScCategorySid) {
        prj070svScCategorySid__ = prj070svScCategorySid;
    }

    /**
     * <p>prj070SvKeyWordkbn を取得します。
     * @return prj070SvKeyWordkbn
     */
    public String getPrj070SvKeyWordkbn() {
        return prj070SvKeyWordkbn__;
    }

    /**
     * <p>prj070SvKeyWordkbn をセットします。
     * @param prj070SvKeyWordkbn prj070SvKeyWordkbn
     */
    public void setPrj070SvKeyWordkbn(String prj070SvKeyWordkbn) {
        prj070SvKeyWordkbn__ = prj070SvKeyWordkbn;
    }

    /**
     * <p>prj070SvSearchTarget を取得します。
     * @return prj070SvSearchTarget
     */
    public String[] getPrj070SvSearchTarget() {
        return prj070SvSearchTarget__;
    }

    /**
     * <p>prj070SvSearchTarget をセットします。
     * @param prj070SvSearchTarget prj070SvSearchTarget
     */
    public void setPrj070SvSearchTarget(String[] prj070SvSearchTarget) {
        prj070SvSearchTarget__ = prj070SvSearchTarget;
    }

    /**
     * <p>categoryLabel を取得します。
     * @return categoryLabel
     */
    public List<LabelValueBean> getCategoryLabel() {
        return categoryLabel__;
    }

    /**
     * <p>categoryLabel をセットします。
     * @param categoryLabel categoryLabel
     */
    public void setCategoryLabel(List<LabelValueBean> categoryLabel) {
        categoryLabel__ = categoryLabel;
    }

    /**
     * <p>prj070KeyWordkbn を取得します。
     * @return prj070KeyWordkbn
     */
    public String getPrj070KeyWordkbn() {
        return prj070KeyWordkbn__;
    }

    /**
     * <p>prj070KeyWordkbn をセットします。
     * @param prj070KeyWordkbn prj070KeyWordkbn
     */
    public void setPrj070KeyWordkbn(String prj070KeyWordkbn) {
        prj070KeyWordkbn__ = prj070KeyWordkbn;
    }

    /**
     * <p>prj070SearchTarget を取得します。
     * @return prj070SearchTarget
     */
    public String[] getPrj070SearchTarget() {
        return prj070SearchTarget__;
    }

    /**
     * <p>prj070SearchTarget をセットします。
     * @param prj070SearchTarget prj070SearchTarget
     */
    public void setPrj070SearchTarget(String[] prj070SearchTarget) {
        prj070SearchTarget__ = prj070SearchTarget;
    }

    /**
     * <p>prj070InitFlg を取得します。
     * @return prj070InitFlg
     */
    public boolean isPrj070InitFlg() {
        return prj070InitFlg__;
    }

    /**
     * <p>prj070InitFlg をセットします。
     * @param prj070InitFlg prj070InitFlg
     */
    public void setPrj070InitFlg(boolean prj070InitFlg) {
        prj070InitFlg__ = prj070InitFlg;
    }

    /**
     * @return prj070scKanriNumber
     */
    public String getPrj070scKanriNumber() {
        return prj070scKanriNumber__;
    }

    /**
     * @param prj070scKanriNumber セットする prj070scKanriNumber
     */
    public void setPrj070scKanriNumber(String prj070scKanriNumber) {
        prj070scKanriNumber__ = prj070scKanriNumber;
    }

    /**
     * @return prj070SvKanriNumber
     */
    public String getPrj070SvKanriNumber() {
        return prj070SvKanriNumber__;
    }

    /**
     * @param prj070SvKanriNumber セットする prj070SvKanriNumber
     */
    public void setPrj070SvKanriNumber(String prj070SvKanriNumber) {
        prj070SvKanriNumber__ = prj070SvKanriNumber;
    }

    /**
     * <p>prj070JoinOnlyFlg__を取得する。
     * @return prj070JoinOnlyFlg
     */
    public boolean getPrj070JoinOnlyFlg() {
        return prj070JoinOnlyFlg__;
    }

    /**
     * <p>prj070JoinOnlyFlg__をセットする。
     * @param prj070JoinOnlyFlg prj070JoinOnlyFlg
     */
    public void setPrj070JoinOnlyFlg(boolean prj070JoinOnlyFlg) {
        prj070JoinOnlyFlg__ = prj070JoinOnlyFlg;
    }

    /**
     * <p>prj070strPlanDate を取得します。
     * @return prj070strPlanDate
     * @see jp.groupsession.v2.prj.prj070.Prj070Form#prj070strPlanDate__
     */
    public String getPrj070strPlanDate() {
        return prj070strPlanDate__;
    }

    /**
     * <p>prj070strPlanDate をセットします。
     * @param prj070strPlanDate prj070strPlanDate
     * @see jp.groupsession.v2.prj.prj070.Prj070Form#prj070strPlanDate__
     */
    public void setPrj070strPlanDate(String prj070strPlanDate) {
        prj070strPlanDate__ = prj070strPlanDate;
    }

    /**
     * <p>prj070endPlanDate を取得します。
     * @return prj070endPlanDate
     * @see jp.groupsession.v2.prj.prj070.Prj070Form#prj070endPlanDate__
     */
    public String getPrj070endPlanDate() {
        return prj070endPlanDate__;
    }

    /**
     * <p>prj070endPlanDate をセットします。
     * @param prj070endPlanDate prj070endPlanDate
     * @see jp.groupsession.v2.prj.prj070.Prj070Form#prj070endPlanDate__
     */
    public void setPrj070endPlanDate(String prj070endPlanDate) {
        prj070endPlanDate__ = prj070endPlanDate;
    }

    /**
     * <p>prj070strResultDater を取得します。
     * @return prj070strResultDater
     * @see jp.groupsession.v2.prj.prj070.Prj070Form#prj070strResultDater__
     */
    public String getPrj070strResultDater() {
        return prj070strResultDater__;
    }

    /**
     * <p>prj070strResultDater をセットします。
     * @param prj070strResultDater prj070strResultDater
     * @see jp.groupsession.v2.prj.prj070.Prj070Form#prj070strResultDater__
     */
    public void setPrj070strResultDater(String prj070strResultDater) {
        prj070strResultDater__ = prj070strResultDater;
    }

    /**
     * <p>prj070endResultDate を取得します。
     * @return prj070endResultDate
     * @see jp.groupsession.v2.prj.prj070.Prj070Form#prj070endResultDate__
     */
    public String getPrj070endResultDate() {
        return prj070endResultDate__;
    }

    /**
     * <p>prj070endResultDate をセットします。
     * @param prj070endResultDate prj070endResultDate
     * @see jp.groupsession.v2.prj.prj070.Prj070Form#prj070endResultDate__
     */
    public void setPrj070endResultDate(String prj070endResultDate) {
        prj070endResultDate__ = prj070endResultDate;
    }
}

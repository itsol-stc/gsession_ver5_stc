package jp.groupsession.v2.rsv.rsv010;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rsv.AbstractReserveForm;
import jp.groupsession.v2.rsv.RsvCalenderModel;
import jp.groupsession.v2.rsv.RsvSisetuModel;
import jp.groupsession.v2.rsv.model.RsvHidDayModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約一覧 週間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv010Form extends AbstractReserveForm {

    /** プラグインID */
    private String rsv010pluginId__ = GSConstReserve.PLUGIN_ID_RESERVE;
    /** 施設グループコンボリスト */
    private ArrayList<LabelValueBean> rsvGrpLabelList__ = null;


    /** 一覧のヘッダに表示する表示日付 */
    private String rsvDispYm__ = null;
    /** 一覧のヘッダに表示する六曜 */
    private String rsvDispRokuyou__ = null;
    /** 週間カレンダー */
    private ArrayList<RsvCalenderModel> rsv010CalendarList__ = null;
    /** 施設毎の予約情報リスト */
    private ArrayList<RsvSisetuModel> rsv010SisetuList__ = null;
    /** 一括登録用キー */
    private ArrayList<String> rsvSelectedIkkatuTorokuKey__ = null;
    /** 一括登録用キー選択値 */
    private String[] rsvIkkatuTorokuKey__ = null;
    /** 一括登録用キー作成フラグ */
    private boolean rsvIkkatuTorokuFlg__ = true;
    /** 選択施設情報 画面に表示しきれない分 */
    private ArrayList<RsvHidDayModel> rsvIkkatuTorokuHiddenList__ = null;
    /** 取消し対象施設キー */
    private String rsv010ClearTargetKey__ = null;
    /** 自動リロード時間 */
    private int rsv010Reload__ = GSConstReserve.AUTO_RELOAD_10MIN;

    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** 画像ファイルバイナリSID */
    private Long rsv010BinSid__ = new Long(0);



    //**************************施設情報表示****************
    /** 施設ID */
    private String rsv010SisetuId__ = null;
    /** 資産管理番号 */
    private String rsv010SisanKanri__ = null;
    /** 表示項目1項目入力欄 座席数、個数、乗員数、冊数などの数値 */
    private String rsv010Prop1Value__ = null;
    /** 表示項目2項目入力欄 喫煙の可不可 */
    private String rsv010Prop2Value__ = null;
    /** 表示項目3項目入力欄 社外持出しの可不可 */
    private String rsv010Prop3Value__ = null;
    /** 表示項目4項目入力欄 車のナンバー */
    private String rsv010Prop4Value__ = null;
    /** 表示項目5項目入力欄 書籍のISBN */
    private String rsv010Prop5Value__ = null;
    /** 表示項目6項目入力欄 全施設共通 予約可能期限 */
    private String rsv010Prop6Value__ = null;
    /** 表示項目7項目入力欄 全施設共通 重複登録 */
    private String rsv010Prop7Value__ = null;
    /** 備考 */
    private String rsv010Biko__ = null;
    /** デフォルト施設画像 */
    private String rsv010SisetuImgDefo__ = null;
    /** 場所・地図画像コメント1 */
    private String rsv010PlaceImgCom1__ = null;
    /** 場所・地図画像コメント2 */
    private String rsv010PlaceImgCom2__ = null;
    /** 場所・地図画像コメント3 */
    private String rsv010PlaceImgCom3__ = null;
    /** 場所・地図画像コメント4 */
    private String rsv010PlaceImgCom4__ = null;
    /** 場所・地図画像コメント5 */
    private String rsv010PlaceImgCom5__ = null;
    /** 場所・地図画像コメント6 */
    private String rsv010PlaceImgCom6__ = null;
    /** 場所・地図画像コメント7 */
    private String rsv010PlaceImgCom7__ = null;
    /** 場所・地図画像コメント8 */
    private String rsv010PlaceImgCom8__ = null;
    /** 場所・地図画像コメント9 */
    private String rsv010PlaceImgCom9__ = null;
    /** 場所・地図画像コメント10 */
    private String rsv010PlaceImgCom10__ = null;
    /** 表示項目1項目名称 */
    private String rsv010PropHeaderName1__ = null;
    /** 表示項目2項目名称 */
    private String rsv010PropHeaderName2__ = null;
    /** 表示項目3項目名称 */
    private String rsv010PropHeaderName3__ = null;
    /** 表示項目4項目名称 */
    private String rsv010PropHeaderName4__ = null;
    /** 表示項目5項目名称 */
    private String rsv010PropHeaderName5__ = null;
    /** 表示項目6項目名称 */
    private String rsv010PropHeaderName6__ = null;
    /** 表示項目7項目名称 */
    private String rsv010PropHeaderName7__ = null;

    /** 管理者設定画面に戻るフラグ 0:管理者設定画面以外へ 1:管理者設定画面へ戻る */
    private int rsvBackToAdminSetting__ = 0;


    /**絞り込み用コンボ*/
    /** 施設絞り込み用グループコンボリスト*/
    private ArrayList<LabelValueBean> rsvGrpNarrowDownList__ = null;
    /** 施設区分コンボリスト */
    private ArrayList<LabelValueBean> rsv010SisetuKbnList__ = null;

    /**
     * <br>[機  能]絞り込み情報を格納する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void setParameter() {

        /** キーワード*/
        setRsv010svSisetuKeyword(getRsv010sisetuKeyword());
        /** キーワード区分*/
        setRsv010svKeyWordkbn(getRsv010KeyWordkbn());
        /** キーワード対象（資産管理番号)*/
        setRsv010svSisetuKeywordSisan(getRsv010sisetuKeywordSisan());
        /** キーワード対象（施設名)*/
        setRsv010svSisetuKeywordSisetu(getRsv010sisetuKeywordSisetu());
        /** キーワード対象（備考)*/
        setRsv010svSisetuKeywordBiko(getRsv010sisetuKeywordBiko());
        /** キーワード対象（ナンバー)*/
        setRsv010svSisetuKeywordNo(getRsv010sisetuKeywordNo());
        /** キーワード対象（ISBN)*/
        setRsv010svSisetuKeywordIsbn(getRsv010sisetuKeywordIsbn());
        /** 空き状況*/
        setRsv010svSisetuFree(getRsv010sisetuFree());
        /** 空き範囲開始年*/
        setRsv010svSisetuFreeFromY(getRsv010sisetuFreeFromY());
        /** 空き範囲開始月*/
        setRsv010svSisetuFreeFromMo(getRsv010sisetuFreeFromMo());
        /** 空き範囲開始日*/
        setRsv010svSisetuFreeFromD(getRsv010sisetuFreeFromD());
        /** 空き範囲開始時*/
        setRsv010svSisetuFreeFromH(getRsv010sisetuFreeFromH());
        /** 空き範囲開始分*/
        setRsv010svSisetuFreeFromMi(getRsv010sisetuFreeFromMi());
        /** 空き範囲終了年*/
        setRsv010svSisetuFreeToY(getRsv010sisetuFreeToY());
        /** 空き範囲終了月*/
        setRsv010svSisetuFreeToMo(getRsv010sisetuFreeToMo());
        /** 空き範囲終了日*/
        setRsv010svSisetuFreeToD(getRsv010sisetuFreeToD());
        /** 空き範囲終了時*/
        setRsv010svSisetuFreeToH(getRsv010sisetuFreeToH());
        /** 空き範囲終了分*/
        setRsv010svSisetuFreeToMi(getRsv010sisetuFreeToMi());
        /** 施設区分*/
        setRsv010svSisetuKbn(getRsv010sisetuKbn());
        /** グループ*/
        setRsv010svGrpNarrowDown(getRsv010grpNarrowDown());
        /** 喫煙*/
        setRsv010svSisetuSmoky(getRsv010sisetuSmoky());
        /** 座席数*/
        setRsv010svSisetuChere(getRsv010sisetuChere());
        /** 社外持ち出し*/
        setRsv010svSisetuTakeout(getRsv010sisetuTakeout());
    }


    /**
     * <br>[機  能]validateチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return ActionErrors
     * @throws NoSuchMethodException 日時設定時処理例外
     * @throws InvocationTargetException 日時設定時処理例外
     * @throws IllegalAccessException 日時設定時処理例外
     */
    public ActionErrors validateCheckSiborikomi(HttpServletRequest req) throws
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        String eprefix = "reserve";


        //キーワードチェック
        String keyword = getRsv010sisetuKeyword();
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
            int sisan = getRsv010sisetuKeywordSisan();
            int sisetu = getRsv010sisetuKeywordSisetu();
            int biko = getRsv010sisetuKeywordBiko();
            int no = 0;
            int isbn = 0;
            if (getRsv010sisetuKbn() == GSConstReserve.RSK_KBN_CAR) {
                no = getRsv010sisetuKeywordNo();
            } else if (getRsv010sisetuKbn() == GSConstReserve.RSK_KBN_BOOK) {
                isbn = getRsv010sisetuKeywordIsbn();
            }
            int valSum = sisan + sisetu + biko + no + isbn;

            if (valSum == 0) {
                msg = new ActionMessage("error.select.required.text",
                        gsMsg.getMessage(req, "cmn.target"));
                StrutsUtil.addMessage(errors, msg, eprefix + "target");
            }
        }

        //空き状況
        if (getRsv010sisetuFree() != 0) {
            boolean dateOk = true;
            DateTimePickerBiz dateBiz = new DateTimePickerBiz();
            int errorSize = errors.size();
            
            String akiJokyo = gsMsg.getMessage("schedule.17");
            errors.add(dateBiz.setYmdParam(this, "rsv010sisetuFreeFromDate",
                    "rsv010sisetuFreeFromY", "rsv010sisetuFreeFromMo",
                    "rsv010sisetuFreeFromD", akiJokyo + gsMsg.getMessage("cmn.startdate")));
            errors.add(dateBiz.setHmParam(this, "rsv010sisetuFreeFromTime",
                    "rsv010sisetuFreeFromH", "rsv010sisetuFreeFromMi",
                    akiJokyo + gsMsg.getMessage("cmn.starttime")));
            
            errors.add(dateBiz.setYmdParam(this, "rsv010sisetuFreeToDate",
                    "rsv010sisetuFreeToY", "rsv010sisetuFreeToMo",
                    "rsv010sisetuFreeToD", akiJokyo + gsMsg.getMessage("cmn.end.date")));
            errors.add(dateBiz.setHmParam(this, "rsv010sisetuFreeToTime",
                    "rsv010sisetuFreeToH", "rsv010sisetuFreeToMi",
                    akiJokyo + gsMsg.getMessage("cmn.endtime")));
            
            if (errors.size() != errorSize) {
                return errors;
            }
            
            UDate udateFrom = new UDate();
            udateFrom.setTimeStamp(getRsv010sisetuFreeFromY(), getRsv010sisetuFreeFromMo(),
                    getRsv010sisetuFreeFromD(), getRsv010sisetuFreeFromH(),
                    getRsv010sisetuFreeFromMi(), 0);
            
            UDate udateTo = new UDate();
            udateTo.setTimeStamp(getRsv010sisetuFreeToY(), getRsv010sisetuFreeToMo(),
                    getRsv010sisetuFreeToD(), getRsv010sisetuFreeToH(),
                    getRsv010sisetuFreeToMi(), 0);
            

            //存在チェック
            if (!ValidateUtil.isExistDateYyyyMMddHHmmss(udateFrom.getTimeStamp())) {
                msg = new ActionMessage(
                        "error.input.notfound.date",
                        getRsv010sisetuFreeFromMo()
                        + gsMsg.getMessage(req, "cmn.month")
                        + getRsv010sisetuFreeFromD()
                        + gsMsg.getMessage(req, "cmn.day"));
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "kikan");
                dateOk = false;
            } else if (!ValidateUtil.isExistDateYyyyMMddHHmmss(udateTo.getTimeStamp())) {
                msg = new ActionMessage(
                        "error.input.notfound.date",
                        getRsv010sisetuFreeToMo()
                        + gsMsg.getMessage(req, "cmn.month")
                        + getRsv010sisetuFreeToD()
                        + gsMsg.getMessage(req, "cmn.day"));
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "kikan");
                dateOk = false;
            }

            if (dateOk) {

                //from - to の範囲整合性チェック
                if (udateFrom.getTime() > udateTo.getTime()) {
                    msg = new ActionMessage("error.input.comp.text",
                            gsMsg.getMessage(req, "schedule.17"),
                            gsMsg.getMessage(req, "cmn.start.lessthan.end"));
                    StrutsUtil.addMessage(
                            errors, msg, eprefix + "kikan");
                }
            }
        }

        //座席数
        //未入力はOK
        String prop1 = getRsv010sisetuChere();
        if (!StringUtil.isNullZeroString(prop1)
                && (getRsv010sisetuKbn() == GSConstReserve.RSK_KBN_CAR
                || getRsv010sisetuKbn() == GSConstReserve.RSK_KBN_HEYA)) {
            //桁数チェック
            if (prop1.length() > GSConstReserve.MAX_LENGTH_PROP1) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(req, "reserve.128"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_PROP1));
                StrutsUtil.addMessage(errors, msg, "zaseki");
            //スペースのみチェック
            } else if (ValidateUtil.isSpace(prop1)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(req, "reserve.128"));
                StrutsUtil.addMessage(errors, msg, eprefix + "zaseki");
            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(prop1)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(req, "reserve.128"));
                StrutsUtil.addMessage(errors, msg, eprefix + "zaseki");
            //数字チェック
            } else if (!ValidateUtil.isNumber(prop1)) {
                msg = new ActionMessage("error.input.number.text",
                        gsMsg.getMessage(req, "reserve.128"));
                StrutsUtil.addMessage(errors, msg, eprefix + "zaseki");
            }
        }

        //施設区分
        int kbn = getRsv010sisetuKbn();
        if (!(kbn == GSConstReserve.RSK_KBN_ALL
                || kbn == GSConstReserve.RSK_KBN_HEYA
                || kbn == GSConstReserve.RSK_KBN_BUPPIN
                || kbn == GSConstReserve.RSK_KBN_CAR
                || kbn == GSConstReserve.RSK_KBN_BOOK
                || kbn == GSConstReserve.RSK_KBN_OTHER)) {
            msg = new ActionMessage("search.data.notfound",
                    gsMsg.getMessage(req, "reserve.47"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuKbn");

        }
        //キーワード区分
        if (!(getRsv010KeyWordkbn() == 0 || getRsv010KeyWordkbn() == 1)) {
            msg = new ActionMessage("error.input.notvalidate.data",
                    gsMsg.getMessage(req, "cmn.keyword.search.Kbn"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuKbn");
        }
        //喫煙
        if (!(getRsv010sisetuSmoky() == 0
                || getRsv010sisetuSmoky() == 1 || getRsv010sisetuSmoky() == 2)) {
            msg = new ActionMessage("error.input.notvalidate.data",
                    gsMsg.getMessage(req, "reserve.132"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuKbn");
        }
        //社外持ち出し
        if (!(getRsv010sisetuTakeout() == 0
                || getRsv010sisetuTakeout() == 1 || getRsv010sisetuTakeout() == 2)) {
            msg = new ActionMessage("error.input.notvalidate.data",
                    gsMsg.getMessage(req, "reserve.133"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuKbn");
        }


        return errors;
    }


    /**
     * <p>rsv010PlaceImgCom1 を取得します。
     * @return rsv010PlaceImgCom1
     */
    public String getRsv010PlaceImgCom1() {
        return rsv010PlaceImgCom1__;
    }
    /**
     * <p>rsv010PlaceImgCom1 をセットします。
     * @param rsv010PlaceImgCom1 rsv010PlaceImgCom1
     */
    public void setRsv010PlaceImgCom1(String rsv010PlaceImgCom1) {
        rsv010PlaceImgCom1__ = rsv010PlaceImgCom1;
    }
    /**
     * <p>rsv010PlaceImgCom10 を取得します。
     * @return rsv010PlaceImgCom10
     */
    public String getRsv010PlaceImgCom10() {
        return rsv010PlaceImgCom10__;
    }
    /**
     * <p>rsv010PlaceImgCom10 をセットします。
     * @param rsv010PlaceImgCom10 rsv010PlaceImgCom10
     */
    public void setRsv010PlaceImgCom10(String rsv010PlaceImgCom10) {
        rsv010PlaceImgCom10__ = rsv010PlaceImgCom10;
    }
    /**
     * <p>rsv010PlaceImgCom2 を取得します。
     * @return rsv010PlaceImgCom2
     */
    public String getRsv010PlaceImgCom2() {
        return rsv010PlaceImgCom2__;
    }
    /**
     * <p>rsv010PlaceImgCom2 をセットします。
     * @param rsv010PlaceImgCom2 rsv010PlaceImgCom2
     */
    public void setRsv010PlaceImgCom2(String rsv010PlaceImgCom2) {
        rsv010PlaceImgCom2__ = rsv010PlaceImgCom2;
    }
    /**
     * <p>rsv010PlaceImgCom3 を取得します。
     * @return rsv010PlaceImgCom3
     */
    public String getRsv010PlaceImgCom3() {
        return rsv010PlaceImgCom3__;
    }
    /**
     * <p>rsv010PlaceImgCom3 をセットします。
     * @param rsv010PlaceImgCom3 rsv010PlaceImgCom3
     */
    public void setRsv010PlaceImgCom3(String rsv010PlaceImgCom3) {
        rsv010PlaceImgCom3__ = rsv010PlaceImgCom3;
    }
    /**
     * <p>rsv010PlaceImgCom4 を取得します。
     * @return rsv010PlaceImgCom4
     */
    public String getRsv010PlaceImgCom4() {
        return rsv010PlaceImgCom4__;
    }
    /**
     * <p>rsv010PlaceImgCom4 をセットします。
     * @param rsv010PlaceImgCom4 rsv010PlaceImgCom4
     */
    public void setRsv010PlaceImgCom4(String rsv010PlaceImgCom4) {
        rsv010PlaceImgCom4__ = rsv010PlaceImgCom4;
    }
    /**
     * <p>rsv010PlaceImgCom5 を取得します。
     * @return rsv010PlaceImgCom5
     */
    public String getRsv010PlaceImgCom5() {
        return rsv010PlaceImgCom5__;
    }
    /**
     * <p>rsv010PlaceImgCom5 をセットします。
     * @param rsv010PlaceImgCom5 rsv010PlaceImgCom5
     */
    public void setRsv010PlaceImgCom5(String rsv010PlaceImgCom5) {
        rsv010PlaceImgCom5__ = rsv010PlaceImgCom5;
    }
    /**
     * <p>rsv010PlaceImgCom6 を取得します。
     * @return rsv010PlaceImgCom6
     */
    public String getRsv010PlaceImgCom6() {
        return rsv010PlaceImgCom6__;
    }
    /**
     * <p>rsv010PlaceImgCom6 をセットします。
     * @param rsv010PlaceImgCom6 rsv010PlaceImgCom6
     */
    public void setRsv010PlaceImgCom6(String rsv010PlaceImgCom6) {
        rsv010PlaceImgCom6__ = rsv010PlaceImgCom6;
    }
    /**
     * <p>rsv010PlaceImgCom7 を取得します。
     * @return rsv010PlaceImgCom7
     */
    public String getRsv010PlaceImgCom7() {
        return rsv010PlaceImgCom7__;
    }
    /**
     * <p>rsv010PlaceImgCom7 をセットします。
     * @param rsv010PlaceImgCom7 rsv010PlaceImgCom7
     */
    public void setRsv010PlaceImgCom7(String rsv010PlaceImgCom7) {
        rsv010PlaceImgCom7__ = rsv010PlaceImgCom7;
    }
    /**
     * <p>rsv010PlaceImgCom8 を取得します。
     * @return rsv010PlaceImgCom8
     */
    public String getRsv010PlaceImgCom8() {
        return rsv010PlaceImgCom8__;
    }
    /**
     * <p>rsv010PlaceImgCom8 をセットします。
     * @param rsv010PlaceImgCom8 rsv010PlaceImgCom8
     */
    public void setRsv010PlaceImgCom8(String rsv010PlaceImgCom8) {
        rsv010PlaceImgCom8__ = rsv010PlaceImgCom8;
    }
    /**
     * <p>rsv010PlaceImgCom9 を取得します。
     * @return rsv010PlaceImgCom9
     */
    public String getRsv010PlaceImgCom9() {
        return rsv010PlaceImgCom9__;
    }
    /**
     * <p>rsv010PlaceImgCom9 をセットします。
     * @param rsv010PlaceImgCom9 rsv010PlaceImgCom9
     */
    public void setRsv010PlaceImgCom9(String rsv010PlaceImgCom9) {
        rsv010PlaceImgCom9__ = rsv010PlaceImgCom9;
    }
    /**
     * <p>rsv010BinSid を取得します。
     * @return rsv010BinSid
     */
    public Long getRsv010BinSid() {
        return rsv010BinSid__;
    }
    /**
     * <p>rsv010BinSid をセットします。
     * @param rsv010BinSid rsv010BinSid
     */
    public void setRsv010BinSid(Long rsv010BinSid) {
        rsv010BinSid__ = rsv010BinSid;
    }
    /**
     * <p>rsv010ClearTargetKey__ を取得します。
     * @return rsv010ClearTargetKey
     */
    public String getRsv010ClearTargetKey() {
        return rsv010ClearTargetKey__;
    }
    /**
     * <p>rsv010ClearTargetKey__ をセットします。
     * @param rsv010ClearTargetKey rsv010ClearTargetKey__
     */
    public void setRsv010ClearTargetKey(String rsv010ClearTargetKey) {
        rsv010ClearTargetKey__ = rsv010ClearTargetKey;
    }
    /**
     * <p>rsvGrpLabelList__ を取得します。
     * @return rsvGrpLabelList
     */
    public ArrayList<LabelValueBean> getRsvGrpLabelList() {
        return rsvGrpLabelList__;
    }
    /**
     * <p>rsvGrpLabelList__ をセットします。
     * @param rsvGrpLabelList rsvGrpLabelList__
     */
    public void setRsvGrpLabelList(ArrayList<LabelValueBean> rsvGrpLabelList) {
        rsvGrpLabelList__ = rsvGrpLabelList;
    }
    /**
     * <p>rsvDispYm__ を取得します。
     * @return rsvDispYm
     */
    public String getRsvDispYm() {
        return rsvDispYm__;
    }
    /**
     * <p>rsvDispYm__ をセットします。
     * @param rsvDispYm rsvDispYm__
     */
    public void setRsvDispYm(String rsvDispYm) {
        rsvDispYm__ = rsvDispYm;
    }
    /**
     * <p>rsvDispRokuyou を取得します。
     * @return rsvDispRokuyou
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010Form#rsvDispRokuyou__
     */
    public String getRsvDispRokuyou() {
        return rsvDispRokuyou__;
    }
    /**
     * <p>rsvDispRokuyou をセットします。
     * @param rsvDispRokuyou rsvDispRokuyou
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010Form#rsvDispRokuyou__
     */
    public void setRsvDispRokuyou(String rsvDispRokuyou) {
        rsvDispRokuyou__ = rsvDispRokuyou;
    }
    /**
     * <p>rsv010CalendarList__ を取得します。
     * @return rsv010CalendarList
     */
    public ArrayList<RsvCalenderModel> getRsv010CalendarList() {
        return rsv010CalendarList__;
    }
    /**
     * <p>rsv010CalendarList__ をセットします。
     * @param rsv010CalendarList rsv010CalendarList__
     */
    public void setRsv010CalendarList(ArrayList<RsvCalenderModel> rsv010CalendarList) {
        rsv010CalendarList__ = rsv010CalendarList;
    }
    /**
     * <p>rsv010SisetuList__ を取得します。
     * @return rsv010SisetuList
     */
    public ArrayList<RsvSisetuModel> getRsv010SisetuList() {
        return rsv010SisetuList__;
    }
    /**
     * <p>rsv010SisetuList__ をセットします。
     * @param rsv010SisetuList rsv010SisetuList__
     */
    public void setRsv010SisetuList(ArrayList<RsvSisetuModel> rsv010SisetuList) {
        rsv010SisetuList__ = rsv010SisetuList;
    }
    /**
     * <p>rsvIkkatuTorokuHiddenList__ を取得します。
     * @return rsvIkkatuTorokuHiddenList
     */
    public ArrayList<RsvHidDayModel> getRsvIkkatuTorokuHiddenList() {
        return rsvIkkatuTorokuHiddenList__;
    }
    /**
     * <p>rsvIkkatuTorokuHiddenList__ をセットします。
     * @param rsvIkkatuTorokuHiddenList rsvIkkatuTorokuHiddenList__
     */
    public void setRsvIkkatuTorokuHiddenList(
            ArrayList<RsvHidDayModel> rsvIkkatuTorokuHiddenList) {
        rsvIkkatuTorokuHiddenList__ = rsvIkkatuTorokuHiddenList;
    }
    /**
     * <p>rsvIkkatuTorokuKey__ を取得します。
     * @return rsvIkkatuTorokuKey
     */
    public String[] getRsvIkkatuTorokuKey() {
        return rsvIkkatuTorokuKey__;
    }
    /**
     * <p>rsvIkkatuTorokuKey__ をセットします。
     * @param rsvIkkatuTorokuKey rsvIkkatuTorokuKey__
     */
    public void setRsvIkkatuTorokuKey(String[] rsvIkkatuTorokuKey) {
        rsvIkkatuTorokuKey__ = rsvIkkatuTorokuKey;
    }
    /**
     * <p>rsvSelectedIkkatuTorokuKey__ を取得します。
     * @return rsvSelectedIkkatuTorokuKey
     */
    public ArrayList<String> getRsvSelectedIkkatuTorokuKey() {
        return rsvSelectedIkkatuTorokuKey__;
    }
    /**
     * <p>rsvSelectedIkkatuTorokuKey__ をセットします。
     * @param rsvSelectedIkkatuTorokuKey rsvSelectedIkkatuTorokuKey__
     */
    public void setRsvSelectedIkkatuTorokuKey(ArrayList<String> rsvSelectedIkkatuTorokuKey) {
        rsvSelectedIkkatuTorokuKey__ = rsvSelectedIkkatuTorokuKey;
    }
    /**
     * <p>rsvIkkatuTorokuFlg__ を取得します。
     * @return rsvIkkatuTorokuFlg
     */
    public boolean isRsvIkkatuTorokuFlg() {
        return rsvIkkatuTorokuFlg__;
    }
    /**
     * <p>rsvIkkatuTorokuFlg__ をセットします。
     * @param rsvIkkatuTorokuFlg rsvIkkatuTorokuFlg__
     */
    public void setRsvIkkatuTorokuFlg(boolean rsvIkkatuTorokuFlg) {
        rsvIkkatuTorokuFlg__ = rsvIkkatuTorokuFlg;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors エラー
     */
    public ActionErrors validateSelectCheck(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //未選択チェック
        if (rsvIkkatuTorokuKey__ == null) {
            msg = new ActionMessage(
                    "error.select.required.text",
                    gsMsg.getMessage(req, "reserve.142"));
            StrutsUtil.addMessage(errors, msg, "rsvIkkatuTorokuKey");
        } else {
            if (rsvIkkatuTorokuKey__.length < 1) {
                msg = new ActionMessage(
                        "error.select.required.text",
                        gsMsg.getMessage(req, "reserve.142"));
                StrutsUtil.addMessage(errors, msg, "rsvIkkatuTorokuKey");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors エラー
     */
    public ActionErrors validateSearchCheck(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        String keyWord = getRsv100KeyWord();
        GsMessage gsMsg = new GsMessage();

        //未入力はOK
        if (!StringUtil.isNullZeroString(keyWord)) {

            //桁数チェック
            if (keyWord.length() > GSConstReserve.MAX_LENGTH_KEYWORD) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(req, "cmn.keyword"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_KEYWORD));
                StrutsUtil.addMessage(errors, msg, "keyWord");
            //スペースのみチェック
            } else if (ValidateUtil.isSpace(keyWord)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(req, "cmn.keyword"));
                StrutsUtil.addMessage(errors, msg, "keyWord");
            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(keyWord)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(req, "cmn.keyword"));
                StrutsUtil.addMessage(errors, msg, "keyWord");
            } else if (!GSValidateUtil.isGsJapaneaseString(keyWord)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(keyWord);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(req, "cmn.keyword"),
                            nstr);
                StrutsUtil.addMessage(errors, msg, "keyWord");
            }
        }

        return errors;
    }
    /**
     * <p>rsv010Reload を取得します。
     * @return rsv010Reload
     */
    public int getRsv010Reload() {
        return rsv010Reload__;
    }
    /**
     * <p>rsv010Reload をセットします。
     * @param rsv010Reload rsv010Reload
     */
    public void setRsv010Reload(int rsv010Reload) {
        rsv010Reload__ = rsv010Reload;
    }
    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }
    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
    /**
     * <p>rsv010pluginId を取得します。
     * @return rsv010pluginId
     */
    public String getRsv010pluginId() {
        return rsv010pluginId__;
    }
    /**
     * <p>rsv010pluginId をセットします。
     * @param rsv010pluginId rsv010pluginId
     */
    public void setRsv010pluginId(String rsv010pluginId) {
        rsv010pluginId__ = rsv010pluginId;
    }
    /**
     * <p>rsv010Biko を取得します。
     * @return rsv010Biko
     */
    public String getRsv010Biko() {
        return rsv010Biko__;
    }
    /**
     * <p>rsv010Biko をセットします。
     * @param rsv010Biko rsv010Biko
     */
    public void setRsv010Biko(String rsv010Biko) {
        rsv010Biko__ = rsv010Biko;
    }
    /**
     * <p>rsv010Prop1Value を取得します。
     * @return rsv010Prop1Value
     */
    public String getRsv010Prop1Value() {
        return rsv010Prop1Value__;
    }
    /**
     * <p>rsv010Prop1Value をセットします。
     * @param rsv010Prop1Value rsv010Prop1Value
     */
    public void setRsv010Prop1Value(String rsv010Prop1Value) {
        rsv010Prop1Value__ = rsv010Prop1Value;
    }
    /**
     * <p>rsv010Prop2Value を取得します。
     * @return rsv010Prop2Value
     */
    public String getRsv010Prop2Value() {
        return rsv010Prop2Value__;
    }
    /**
     * <p>rsv010Prop2Value をセットします。
     * @param rsv010Prop2Value rsv010Prop2Value
     */
    public void setRsv010Prop2Value(String rsv010Prop2Value) {
        rsv010Prop2Value__ = rsv010Prop2Value;
    }
    /**
     * <p>rsv010Prop3Value を取得します。
     * @return rsv010Prop3Value
     */
    public String getRsv010Prop3Value() {
        return rsv010Prop3Value__;
    }
    /**
     * <p>rsv010Prop3Value をセットします。
     * @param rsv010Prop3Value rsv010Prop3Value
     */
    public void setRsv010Prop3Value(String rsv010Prop3Value) {
        rsv010Prop3Value__ = rsv010Prop3Value;
    }
    /**
     * <p>rsv010Prop4Value を取得します。
     * @return rsv010Prop4Value
     */
    public String getRsv010Prop4Value() {
        return rsv010Prop4Value__;
    }
    /**
     * <p>rsv010Prop4Value をセットします。
     * @param rsv010Prop4Value rsv010Prop4Value
     */
    public void setRsv010Prop4Value(String rsv010Prop4Value) {
        rsv010Prop4Value__ = rsv010Prop4Value;
    }
    /**
     * <p>rsv010Prop5Value を取得します。
     * @return rsv010Prop5Value
     */
    public String getRsv010Prop5Value() {
        return rsv010Prop5Value__;
    }
    /**
     * <p>rsv010Prop5Value をセットします。
     * @param rsv010Prop5Value rsv010Prop5Value
     */
    public void setRsv010Prop5Value(String rsv010Prop5Value) {
        rsv010Prop5Value__ = rsv010Prop5Value;
    }
    /**
     * <p>rsv010Prop6Value を取得します。
     * @return rsv010Prop6Value
     */
    public String getRsv010Prop6Value() {
        return rsv010Prop6Value__;
    }
    /**
     * <p>rsv010Prop6Value をセットします。
     * @param rsv010Prop6Value rsv010Prop6Value
     */
    public void setRsv010Prop6Value(String rsv010Prop6Value) {
        rsv010Prop6Value__ = rsv010Prop6Value;
    }
    /**
     * <p>rsv010Prop7Value を取得します。
     * @return rsv010Prop7Value
     */
    public String getRsv010Prop7Value() {
        return rsv010Prop7Value__;
    }
    /**
     * <p>rsv010Prop7Value をセットします。
     * @param rsv010Prop7Value rsv010Prop7Value
     */
    public void setRsv010Prop7Value(String rsv010Prop7Value) {
        rsv010Prop7Value__ = rsv010Prop7Value;
    }
    /**
     * <p>rsv010SisanKanri を取得します。
     * @return rsv010SisanKanri
     */
    public String getRsv010SisanKanri() {
        return rsv010SisanKanri__;
    }
    /**
     * <p>rsv010SisanKanri をセットします。
     * @param rsv010SisanKanri rsv010SisanKanri
     */
    public void setRsv010SisanKanri(String rsv010SisanKanri) {
        rsv010SisanKanri__ = rsv010SisanKanri;
    }
    /**
     * <p>rsv010SisetuId を取得します。
     * @return rsv010SisetuId
     */
    public String getRsv010SisetuId() {
        return rsv010SisetuId__;
    }
    /**
     * <p>rsv010SisetuId をセットします。
     * @param rsv010SisetuId rsv010SisetuId
     */
    public void setRsv010SisetuId(String rsv010SisetuId) {
        rsv010SisetuId__ = rsv010SisetuId;
    }
    /**
     * <p>rsv010SisetuImgDefo を取得します。
     * @return rsv010SisetuImgDefo
     */
    public String getRsv010SisetuImgDefo() {
        return rsv010SisetuImgDefo__;
    }
    /**
     * <p>rsv010SisetuImgDefo をセットします。
     * @param rsv010SisetuImgDefo rsv010SisetuImgDefo
     */
    public void setRsv010SisetuImgDefo(String rsv010SisetuImgDefo) {
        rsv010SisetuImgDefo__ = rsv010SisetuImgDefo;
    }
    /**
     * <p>rsv010PropHeaderName1 を取得します。
     * @return rsv010PropHeaderName1
     */
    public String getRsv010PropHeaderName1() {
        return rsv010PropHeaderName1__;
    }
    /**
     * <p>rsv010PropHeaderName1 をセットします。
     * @param rsv010PropHeaderName1 rsv010PropHeaderName1
     */
    public void setRsv010PropHeaderName1(String rsv010PropHeaderName1) {
        rsv010PropHeaderName1__ = rsv010PropHeaderName1;
    }
    /**
     * <p>rsv010PropHeaderName2 を取得します。
     * @return rsv010PropHeaderName2
     */
    public String getRsv010PropHeaderName2() {
        return rsv010PropHeaderName2__;
    }
    /**
     * <p>rsv010PropHeaderName2 をセットします。
     * @param rsv010PropHeaderName2 rsv010PropHeaderName2
     */
    public void setRsv010PropHeaderName2(String rsv010PropHeaderName2) {
        rsv010PropHeaderName2__ = rsv010PropHeaderName2;
    }
    /**
     * <p>rsv010PropHeaderName3 を取得します。
     * @return rsv010PropHeaderName3
     */
    public String getRsv010PropHeaderName3() {
        return rsv010PropHeaderName3__;
    }
    /**
     * <p>rsv010PropHeaderName3 をセットします。
     * @param rsv010PropHeaderName3 rsv010PropHeaderName3
     */
    public void setRsv010PropHeaderName3(String rsv010PropHeaderName3) {
        rsv010PropHeaderName3__ = rsv010PropHeaderName3;
    }
    /**
     * <p>rsv010PropHeaderName4 を取得します。
     * @return rsv010PropHeaderName4
     */
    public String getRsv010PropHeaderName4() {
        return rsv010PropHeaderName4__;
    }
    /**
     * <p>rsv010PropHeaderName4 をセットします。
     * @param rsv010PropHeaderName4 rsv010PropHeaderName4
     */
    public void setRsv010PropHeaderName4(String rsv010PropHeaderName4) {
        rsv010PropHeaderName4__ = rsv010PropHeaderName4;
    }
    /**
     * <p>rsv010PropHeaderName5 を取得します。
     * @return rsv010PropHeaderName5
     */
    public String getRsv010PropHeaderName5() {
        return rsv010PropHeaderName5__;
    }
    /**
     * <p>rsv010PropHeaderName5 をセットします。
     * @param rsv010PropHeaderName5 rsv010PropHeaderName5
     */
    public void setRsv010PropHeaderName5(String rsv010PropHeaderName5) {
        rsv010PropHeaderName5__ = rsv010PropHeaderName5;
    }
    /**
     * <p>rsv010PropHeaderName6 を取得します。
     * @return rsv010PropHeaderName6
     */
    public String getRsv010PropHeaderName6() {
        return rsv010PropHeaderName6__;
    }
    /**
     * <p>rsv010PropHeaderName6 をセットします。
     * @param rsv010PropHeaderName6 rsv010PropHeaderName6
     */
    public void setRsv010PropHeaderName6(String rsv010PropHeaderName6) {
        rsv010PropHeaderName6__ = rsv010PropHeaderName6;
    }
    /**
     * <p>rsv010PropHeaderName7 を取得します。
     * @return rsv010PropHeaderName7
     */
    public String getRsv010PropHeaderName7() {
        return rsv010PropHeaderName7__;
    }
    /**
     * <p>rsv010PropHeaderName7 をセットします。
     * @param rsv010PropHeaderName7 rsv010PropHeaderName7
     */
    public void setRsv010PropHeaderName7(String rsv010PropHeaderName7) {
        rsv010PropHeaderName7__ = rsv010PropHeaderName7;
    }
    /**
     * <p>rsvBackToAdminSetting を取得します。
     * @return rsvBackToAdminSetting
     */
    public int getRsvBackToAdminSetting() {
        return rsvBackToAdminSetting__;
    }
    /**
     * <p>rsvBackToAdminSetting をセットします。
     * @param rsvBackToAdminSetting rsvBackToAdminSetting
     */
    public void setRsvBackToAdminSetting(int rsvBackToAdminSetting) {
        rsvBackToAdminSetting__ = rsvBackToAdminSetting;
    }
    /**
     * <p>rsvGrpNarrowDownList を取得します。
     * @return rsvGrpNarrowDownList
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010Form#rsvGrpNarrowDownList__
     */
    public ArrayList<LabelValueBean> getRsvGrpNarrowDownList() {
        return rsvGrpNarrowDownList__;
    }
    /**
     * <p>rsvGrpNarrowDownList をセットします。
     * @param rsvGrpNarrowDownList rsvGrpNarrowDownList
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010Form#rsvGrpNarrowDownList__
     */
    public void setRsvGrpNarrowDownList(
            ArrayList<LabelValueBean> rsvGrpNarrowDownList) {
        rsvGrpNarrowDownList__ = rsvGrpNarrowDownList;
    }
    /**
     * <p>rsv010SisetuKbnList を取得します。
     * @return rsv010SisetuKbnList
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010Form#rsv010SisetuKbnList__
     */
    public ArrayList<LabelValueBean> getRsv010SisetuKbnList() {
        return rsv010SisetuKbnList__;
    }
    /**
     * <p>rsv010SisetuKbnList をセットします。
     * @param rsv010SisetuKbnList rsv010SisetuKbnList
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010Form#rsv010SisetuKbnList__
     */
    public void setRsv010SisetuKbnList(
            ArrayList<LabelValueBean> rsv010SisetuKbnList) {
        rsv010SisetuKbnList__ = rsv010SisetuKbnList;
    }
}
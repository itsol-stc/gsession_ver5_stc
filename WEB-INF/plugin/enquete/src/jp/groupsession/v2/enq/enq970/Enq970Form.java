package jp.groupsession.v2.enq.enq970;

import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.EnqValidateUtil;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq010.Enq010Const;
import jp.groupsession.v2.enq.enq900.Enq900Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import java.lang.reflect.InvocationTargetException;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] 管理者設定 発信アンケート管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq970Form extends Enq900Form {
    /** 初期表示フラグ */
    private int enq970initFlg__ = 0;
    /** 詳細検索フラグ */
    private int enq970searchDetailFlg__ = 0;
    /** 選択したアンケートSID */
    private String[] enq970selectEnqSid__ = null;
    /** 遷移元ページ */
    private int enq970BackPage__ = 0;

    /** ソートキー */
    private int enq970sortKey__ = Enq010Const.SORTKEY_OPEN;
    /** 並び順 */
    private int enq970order__ = Enq010Const.ORDER_DESC;

    /** 種類 */
    private int enq970type__ = 0;
    /** キーワード(簡易検索) */
    private String enq970keywordSimple__ = null;
    /** キーワード */
    private String enq970keyword__ = null;
    /** キーワード 種別 */
    private int enq970keywordType__ = 0;
    /** 発信者 グループ */
    private int enq970sendGroup__ = -1;
    /** 発信者 ユーザ */
    private int enq970sendUser__ = -1;
    /** 発信者 入力 */
    private int enq970sendInput__ = 0;
    /** 発信者 テキスト */
    private String enq970sendInputText__ = null;
    /** 作成日 指定なし */
    private int enq970makeDateKbn__ = 0;
    /** 作成日 開始 年 */
    private int enq970makeDateFromYear__ = 0;
    /** 作成日 開始 月 */
    private int enq970makeDateFromMonth__ = 0;
    /** 作成日 開始 日 */
    private int enq970makeDateFromDay__ = 0;
    /** 作成日 終了 年 */
    private int enq970makeDateToYear__ = 0;
    /** 作成日 終了 月 */
    private int enq970makeDateToMonth__ = 0;
    /** 作成日 終了 日 */
    private int enq970makeDateToDay__ = 0;
    /** 作成日 開始 年月日 */
    private String enq970makeDateFrom__ = null;
    /** 作成日 終了 年月日 */
    private String enq970makeDateTo__ = null;
    /** 公開期間 指定なし */
    private int enq970pubDateKbn__ = 0;
    /** 公開期間 開始 年 */
    private int enq970pubDateFromYear__ = 0;
    /** 公開期間 開始 月 */
    private int enq970pubDateFromMonth__ = 0;
    /** 公開期間 開始 日 */
    private int enq970pubDateFromDay__ = 0;
    /** 公開期間 終了 年 */
    private int enq970pubDateToYear__ = 0;
    /** 公開期間 終了 月 */
    private int enq970pubDateToMonth__ = 0;
    /** 公開期間 終了 日 */
    private int enq970pubDateToDay__ = 0;
    /** 回答期限 指定なし */
    private int enq970ansDateKbn__ = 0;
    /** 回答期限 開始 年 */
    private int enq970ansDateFromYear__ = 0;
    /** 回答期限 開始 月 */
    private int enq970ansDateFromMonth__ = 0;
    /** 回答期限 開始 日 */
    private int enq970ansDateFromDay__ = 0;
    /** 回答期限 終了 年 */
    private int enq970ansDateToYear__ = 0;
    /** 回答期限 終了 月 */
    private int enq970ansDateToMonth__ = 0;
    /** 回答期限 終了 日 */
    private int enq970ansDateToDay__ = 0;
    /** 回答期限 開始 年月日 */
    private String enq970ansDateFrom__ = null;
    /** 回答期限 終了 年月日 */
    private String enq970ansDateTo__ = null;
    /** 結果公開期間 指定なし */
    private int enq970resPubDateKbn__ = 0;
    /** 結果公開期間 開始 年 */
    private int enq970resPubDateFromYear__ = 0;
    /** 結果公開期間 開始 月 */
    private int enq970resPubDateFromMonth__ = 0;
    /** 結果公開期間 開始 日 */
    private int enq970resPubDateFromDay__ = 0;
    /** 結果公開期間 終了 年 */
    private int enq970resPubDateToYear__ = 0;
    /** 結果公開期間 終了 月 */
    private int enq970resPubDateToMonth__ = 0;
    /** 結果公開期間 終了 日 */
    private int enq970resPubDateToDay__ = 0;
    /** 結果公開期間 開始 年月日 */
    private String enq970resPubDateFrom__ = null;
    /** 結果公開期間 終了 年月日 */
    private String enq970resPubDateTo__ = null;

    /** 重要度 */
    private int[] enq970priority__ = null;
    /** 状態 */
    private int[] enq970status__ = null;
    /** 匿名 匿名 */
    private int enq970anony__ = 0;
    /** 種類(検索条件保持) */
    private int enq970svType__ = 0;
    /** キーワード(簡易検索)(検索条件保持) */
    private String enq970svKeywordSimple__ = null;
    /** キーワード(検索条件保持) */
    private String enq970svKeyword__ = null;
    /** キーワード 種別(検索条件保持) */
    private int enq970svKeywordType__ = 0;
    /** 発信者 グループ(検索条件保持) */
    private int enq970svSendGroup__ = 0;
    /** 発信者 ユーザ(検索条件保持) */
    private int enq970svSendUser__ = 0;
    /** 発信者 入力(検索条件保持) */
    private int enq970svSendInput__ = 0;
    /** 発信者 テキスト(検索条件保持) */
    private String enq970svSendInputText__ = null;
    /** 作成日 指定なし(検索条件保持) */
    private int enq970svMakeDateKbn__ = 0;
    /** 作成日 開始 年(検索条件保持) */
    private int enq970svMakeDateFromYear__ = 0;
    /** 作成日 開始 月(検索条件保持) */
    private int enq970svMakeDateFromMonth__ = 0;
    /** 作成日 開始 日(検索条件保持) */
    private int enq970svMakeDateFromDay__ = 0;
    /** 作成日 終了 年(検索条件保持) */
    private int enq970svMakeDateToYear__ = 0;
    /** 作成日 終了 月(検索条件保持) */
    private int enq970svMakeDateToMonth__ = 0;
    /** 作成日 終了 日(検索条件保持) */
    private int enq970svMakeDateToDay__ = 0;
    /** 作成日 開始 年月日(検索条件保持) */
    private String enq970svMakeDateFrom__ = null;
    /** 作成日 終了 年月日(検索条件保持) */
    private String enq970svMakeDateTo__ = null;
    /** 公開期間 指定なし(検索条件保持) */
    private int enq970svPubDateKbn__ = 0;
    /** 公開期間 開始 年(検索条件保持) */
    private int enq970svPubDateFromYear__ = 0;
    /** 公開期間 開始 月(検索条件保持) */
    private int enq970svPubDateFromMonth__ = 0;
    /** 公開期間 開始 日(検索条件保持) */
    private int enq970svPubDateFromDay__ = 0;
    /** 公開期間 終了 年(検索条件保持) */
    private int enq970svPubDateToYear__ = 0;
    /** 公開期間 終了 月(検索条件保持) */
    private int enq970svPubDateToMonth__ = 0;
    /** 公開期間 終了 日(検索条件保持) */
    private int enq970svPubDateToDay__ = 0;
    /** 結果公開期間 指定なし(検索条件保持) */
    private int enq970svResPubDateKbn__ = 0;
    /** 結果公開期間 開始 年(検索条件保持) */
    private int enq970svResPubDateFromYear__ = 0;
    /** 結果公開期間 開始 月(検索条件保持) */
    private int enq970svResPubDateFromMonth__ = 0;
    /** 結果公開期間 開始 日(検索条件保持) */
    private int enq970svResPubDateFromDay__ = 0;
    /** 結果公開期間 終了 年(検索条件保持) */
    private int enq970svResPubDateToYear__ = 0;
    /** 結果公開期間 終了 月(検索条件保持) */
    private int enq970svResPubDateToMonth__ = 0;
    /** 結果公開期間 終了 日(検索条件保持) */
    private int enq970svResPubDateToDay__ = 0;
    /** 結果公開期間 開始 年月日(検索条件保持) */
    private int enq970svResPubDateFrom__ = 0;
    /** 結果公開期間 終了 年月日(検索条件保持) */
    private int enq970svResPubDateTo__ = 0;
    /** 回答期限 指定なし(検索条件保持) */
    private int enq970svAnsDateKbn__ = 0;
    /** 回答期限 開始 年(検索条件保持) */
    private int enq970svAnsDateFromYear__ = 0;
    /** 回答期限 開始 月(検索条件保持) */
    private int enq970svAnsDateFromMonth__ = 0;
    /** 回答期限 開始 日(検索条件保持) */
    private int enq970svAnsDateFromDay__ = 0;
    /** 回答期限 終了 年(検索条件保持) */
    private int enq970svAnsDateToYear__ = 0;
    /** 回答期限 終了 月(検索条件保持) */
    private int enq970svAnsDateToMonth__ = 0;
    /** 回答期限 終了 日(検索条件保持) */
    private int enq970svAnsDateToDay__ = 0;
    /** 回答期限 開始 年月日(検索条件保持) */
    private String enq970svAnsDateFrom__ = null;
    /** 回答期限 終了 年月日(検索条件保持) */
    private String enq970svAnsDateTo__ = null;
    /** 重要度(検索条件保持) */
    private int[] enq970svPriority__ = null;
    /** 状態(検索条件保持) */
    private int[] enq970svStatus__ = null;
    /** 匿名 匿名(検索条件保持) */
    private int enq970svAnony__ = 0;
    /** ページ(上段) */
    private int enq970pageTop__ = 0;
    /** ページ(下段) */
    private int enq970pageBottom__ = 0;
    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);

        msgForm.addHiddenParam("backScreen", getBackScreen());
        msgForm.addHiddenParam("enq970initFlg", enq970initFlg__);
        msgForm.addHiddenParam("enq970searchDetailFlg", enq970searchDetailFlg__);
        msgForm.addHiddenParam("enq970selectEnqSid", enq970selectEnqSid__);
        msgForm.addHiddenParam("enq970type", enq970type__);
        msgForm.addHiddenParam("enq970keywordSimple", enq970keywordSimple__);
        msgForm.addHiddenParam("enq970keyword", enq970keyword__);
        msgForm.addHiddenParam("enq970keywordType", enq970keywordType__);
        msgForm.addHiddenParam("enq970sendGroup", enq970sendGroup__);
        msgForm.addHiddenParam("enq970sendUser", enq970sendUser__);
        msgForm.addHiddenParam("enq970sendInput", enq970sendInput__);
        msgForm.addHiddenParam("enq970sendInputText", enq970sendInputText__);
        msgForm.addHiddenParam("enq970makeDateKbn", enq970makeDateKbn__);
        msgForm.addHiddenParam("enq970makeDateFromYear", enq970makeDateFromYear__);
        msgForm.addHiddenParam("enq970makeDateFromMonth", enq970makeDateFromMonth__);
        msgForm.addHiddenParam("enq970makeDateFromDay", enq970makeDateFromDay__);
        msgForm.addHiddenParam("enq970makeDateToYear", enq970makeDateToYear__);
        msgForm.addHiddenParam("enq970makeDateToMonth", enq970makeDateToMonth__);
        msgForm.addHiddenParam("enq970makeDateToDay", enq970makeDateToDay__);
        msgForm.addHiddenParam("enq970makeDateFrom", enq970makeDateFrom__);
        msgForm.addHiddenParam("enq970makeDateTo", enq970makeDateTo__);
        msgForm.addHiddenParam("enq970pubDateKbn", enq970pubDateKbn__);
        msgForm.addHiddenParam("enq970pubDateFromYear", enq970pubDateFromYear__);
        msgForm.addHiddenParam("enq970pubDateFromMonth", enq970pubDateFromMonth__);
        msgForm.addHiddenParam("enq970pubDateFromDay", enq970pubDateFromDay__);
        msgForm.addHiddenParam("enq970pubDateToYear", enq970pubDateToYear__);
        msgForm.addHiddenParam("enq970pubDateToMonth", enq970pubDateToMonth__);
        msgForm.addHiddenParam("enq970pubDateToDay", enq970pubDateToDay__);
        msgForm.addHiddenParam("enq970resPubDateKbn", enq970resPubDateKbn__);
        msgForm.addHiddenParam("enq970resPubDateFromYear", enq970resPubDateFromYear__);
        msgForm.addHiddenParam("enq970resPubDateFromMonth", enq970resPubDateFromMonth__);
        msgForm.addHiddenParam("enq970resPubDateFromDay", enq970resPubDateFromDay__);
        msgForm.addHiddenParam("enq970resPubDateToYear", enq970resPubDateToYear__);
        msgForm.addHiddenParam("enq970resPubDateToMonth", enq970resPubDateToMonth__);
        msgForm.addHiddenParam("enq970resPubDateToDay", enq970resPubDateToDay__);
        msgForm.addHiddenParam("enq970resPubDateFrom", enq970resPubDateFrom__);
        msgForm.addHiddenParam("enq970resPubDateTo", enq970resPubDateTo__);
        msgForm.addHiddenParam("enq970ansDateKbn", enq970ansDateKbn__);
        msgForm.addHiddenParam("enq970ansDateFromYear", enq970ansDateFromYear__);
        msgForm.addHiddenParam("enq970ansDateFromMonth", enq970ansDateFromMonth__);
        msgForm.addHiddenParam("enq970ansDateFromDay", enq970ansDateFromDay__);
        msgForm.addHiddenParam("enq970ansDateToYear", enq970ansDateToYear__);
        msgForm.addHiddenParam("enq970ansDateToMonth", enq970ansDateToMonth__);
        msgForm.addHiddenParam("enq970ansDateToDay", enq970ansDateToDay__);
        msgForm.addHiddenParam("enq970ansDateFrom", enq970ansDateFrom__);
        msgForm.addHiddenParam("enq970ansDateTo", enq970ansDateTo__);
        msgForm.addHiddenParam("enq970priority", enq970priority__);
        msgForm.addHiddenParam("enq970status", enq970status__);
        msgForm.addHiddenParam("enq970anony", enq970anony__);
        msgForm.addHiddenParam("enq970svType", enq970svType__);
        msgForm.addHiddenParam("enq970svKeywordSimple", enq970svKeywordSimple__);
        msgForm.addHiddenParam("enq970svKeyword", enq970svKeyword__);
        msgForm.addHiddenParam("enq970svKeywordType", enq970svKeywordType__);
        msgForm.addHiddenParam("enq970svSendGroup", enq970svSendGroup__);
        msgForm.addHiddenParam("enq970svSendUser", enq970svSendUser__);
        msgForm.addHiddenParam("enq970svSendInput", enq970svSendInput__);
        msgForm.addHiddenParam("enq970svSendInputText", enq970svSendInputText__);
        msgForm.addHiddenParam("enq970svMakeDateKbn", enq970svMakeDateKbn__);
        msgForm.addHiddenParam("enq970svMakeDateFromYear", enq970svMakeDateFromYear__);
        msgForm.addHiddenParam("enq970svMakeDateFromMonth", enq970svMakeDateFromMonth__);
        msgForm.addHiddenParam("enq970svMakeDateFromDay", enq970svMakeDateFromDay__);
        msgForm.addHiddenParam("enq970svMakeDateToYear", enq970svMakeDateToYear__);
        msgForm.addHiddenParam("enq970svMakeDateToMonth", enq970svMakeDateToMonth__);
        msgForm.addHiddenParam("enq970svMakeDateToDay", enq970svMakeDateToDay__);
        msgForm.addHiddenParam("enq970svMakeDateFrom", enq970svMakeDateFrom__);
        msgForm.addHiddenParam("enq970svMakeDateTo", enq970svMakeDateTo__);
        msgForm.addHiddenParam("enq970svPubDateKbn", enq970svPubDateKbn__);
        msgForm.addHiddenParam("enq970svPubDateFromYear", enq970svPubDateFromYear__);
        msgForm.addHiddenParam("enq970svPubDateFromMonth", enq970svPubDateFromMonth__);
        msgForm.addHiddenParam("enq970svPubDateFromDay", enq970svPubDateFromDay__);
        msgForm.addHiddenParam("enq970svPubDateToYear", enq970svPubDateToYear__);
        msgForm.addHiddenParam("enq970svPubDateToMonth", enq970svPubDateToMonth__);
        msgForm.addHiddenParam("enq970svPubDateToDay", enq970svPubDateToDay__);
        msgForm.addHiddenParam("enq970svResPubDateKbn", enq970svResPubDateKbn__);
        msgForm.addHiddenParam("enq970svResPubDateFromYear", enq970svResPubDateFromYear__);
        msgForm.addHiddenParam("enq970svResPubDateFromMonth", enq970svResPubDateFromMonth__);
        msgForm.addHiddenParam("enq970svResPubDateFromDay", enq970svResPubDateFromDay__);
        msgForm.addHiddenParam("enq970svResPubDateToYear", enq970svResPubDateToYear__);
        msgForm.addHiddenParam("enq970svResPubDateToMonth", enq970svResPubDateToMonth__);
        msgForm.addHiddenParam("enq970svResPubDateToDay", enq970svResPubDateToDay__);
        msgForm.addHiddenParam("enq970svResPubDateFrom", enq970svResPubDateFrom__);
        msgForm.addHiddenParam("enq970svResPubDateTo", enq970svResPubDateTo__);
        msgForm.addHiddenParam("enq970svAnsDateKbn", enq970svAnsDateKbn__);
        msgForm.addHiddenParam("enq970svAnsDateFromYear", enq970svAnsDateFromYear__);
        msgForm.addHiddenParam("enq970svAnsDateFromMonth", enq970svAnsDateFromMonth__);
        msgForm.addHiddenParam("enq970svAnsDateFromDay", enq970svAnsDateFromDay__);
        msgForm.addHiddenParam("enq970svAnsDateToYear", enq970svAnsDateToYear__);
        msgForm.addHiddenParam("enq970svAnsDateToMonth", enq970svAnsDateToMonth__);
        msgForm.addHiddenParam("enq970svAnsDateToDay", enq970svAnsDateToDay__);
        msgForm.addHiddenParam("enq970svAnsDateFrom", enq970svAnsDateFrom__);
        msgForm.addHiddenParam("enq970svAnsDateTo", enq970svAnsDateTo__);
        msgForm.addHiddenParam("enq970svPriority", enq970svPriority__);
        msgForm.addHiddenParam("enq970svStatus", enq970svStatus__);
        msgForm.addHiddenParam("enq970svAnony", enq970svAnony__);
        msgForm.addHiddenParam("enq970pageTop", enq970pageTop__);
        msgForm.addHiddenParam("enq970pageBottom", enq970pageBottom__);
    }

    /**
     * <br>[機  能] 検索条件の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外 
     * @throws IllegalAccessException 日時設定時例外
     */
    public ActionErrors validateSearch(RequestModel reqMdl) throws
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();

        //キーワード
        EnqValidateUtil.validateTextBoxInput(errors, enq970keyword__,
                "enq970keyword",
                gsMsg.getMessage("cmn.keyword"),
                GSConstEnquete.MAX_LEN_EMN_TITLE, false);

        //発信者 入力
        EnqValidateUtil.validateTextBoxInput(errors, enq970sendInputText__,
                "enq970sendInputText",
                gsMsg.getMessage("enq.enq010.01"),
                Enq010Const.MAXLEN_SENDER_INPUT, false);

        //作成日
        if (enq970makeDateKbn__ != Enq010Const.DATE_NON) {
            
            int errorCnt = errors.size();
            String createStart =
                    gsMsg.getMessage("man.creation.date") + " " + gsMsg.getMessage("cmn.start");
            errors.add(dateBiz.setYmdParam(this, "enq970makeDateFrom", "enq970makeDateFromYear",
                    "enq970makeDateFromMonth", "enq970makeDateFromDay", createStart));
            
            String createEnd =
                    gsMsg.getMessage("man.creation.date") + " " + gsMsg.getMessage("cmn.end");
            errors.add(dateBiz.setYmdParam(this, "enq970makeDateTo", "enq970makeDateToYear",
                    "enq970makeDateToMonth", "enq970makeDateToDay", createEnd));
            
            if (errorCnt == errors.size()) {
                EnqValidateUtil.validateDate(reqMdl, errors,
                        "enq970makeDate",
                        gsMsg.getMessage("man.creation.date"),
                        enq970makeDateFromYear__,
                        enq970makeDateFromMonth__,
                        enq970makeDateFromDay__,
                        enq970makeDateToYear__,
                        enq970makeDateToMonth__,
                        enq970makeDateToDay__);
            }
        }

        //公開期間
        if (enq970pubDateKbn__ != Enq010Const.DATE_NON) {

            EnqValidateUtil.validateDate(reqMdl, errors,
                    "enq970pubDate",
                    gsMsg.getMessage("cmn.open.period"),
                    enq970pubDateFromYear__,
                    enq970pubDateFromMonth__,
                    enq970pubDateFromDay__,
                    enq970pubDateToYear__,
                    enq970pubDateToMonth__,
                    enq970pubDateToDay__);
        }

        //回答期限
        if (enq970ansDateKbn__ != Enq010Const.DATE_NON) {
            
            int errorCnt = errors.size();
            
            String ansDateStart =
                    gsMsg.getMessage("enq.19") + " " + gsMsg.getMessage("cmn.start");
            errors.add(dateBiz.setYmdParam(this, "enq970ansDateFrom",
                    "enq970ansDateFromYear", "enq970ansDateFromMonth",
                    "enq970ansDateFromDay", ansDateStart));
            
            String ansDateEnd =
                    gsMsg.getMessage("enq.19") + " " + gsMsg.getMessage("cmn.end");
            errors.add(dateBiz.setYmdParam(this, "enq970ansDateTo",
                    "enq970ansDateToYear", "enq970ansDateToMonth",
                    "enq970ansDateToDay", ansDateEnd));
            
            if (errorCnt == errors.size()) {
                EnqValidateUtil.validateDate(reqMdl, errors,
                        "enq970ansDate",
                        gsMsg.getMessage("enq.19"),
                        enq970ansDateFromYear__,
                        enq970ansDateFromMonth__,
                        enq970ansDateFromDay__,
                        enq970ansDateToYear__,
                        enq970ansDateToMonth__,
                        enq970ansDateToDay__);
            }
        }

        //結果公開期限
        if (enq970resPubDateKbn__ != Enq010Const.DATE_NON) {
            
            int errorCnt = errors.size();

            String resPubStart =
                    gsMsg.getMessage("enq.enq210.11") + " " + gsMsg.getMessage("cmn.start");
            errors.add(dateBiz.setYmdParam(this, "enq970resPubDateFrom",
                    "enq970resPubDateFromYear", "enq970resPubDateFromMonth",
                    "enq970resPubDateFromDay", resPubStart));

            String resPubEnd =
                    gsMsg.getMessage("enq.enq210.11") + " " + gsMsg.getMessage("cmn.end");
            errors.add(dateBiz.setYmdParam(this, "enq970resPubDateTo",
                    "enq970resPubDateToYear", "enq970resPubDateToMonth",
                    "enq970resPubDateToDay", resPubEnd));

            if (errorCnt == errors.size()) {
                EnqValidateUtil.validateDate(reqMdl, errors,
                        "enq970resPubDate",
                        gsMsg.getMessage("enq.enq210.11"),
                        enq970resPubDateFromYear__,
                        enq970resPubDateFromMonth__,
                        enq970resPubDateFromDay__,
                        enq970resPubDateToYear__,
                        enq970resPubDateToMonth__,
                        enq970resPubDateToDay__);
            }
        }
        return errors;
    }

    /**
     * <p>enq970initFlg を取得します。
     * @return enq970initFlg
     */
    public int getEnq970initFlg() {
        return enq970initFlg__;
    }

    /**
     * <p>enq970initFlg をセットします。
     * @param enq970initFlg enq970initFlg
     */
    public void setEnq970initFlg(int enq970initFlg) {
        enq970initFlg__ = enq970initFlg;
    }

    /**
     * <p>enq970searchDetailFlg を取得します。
     * @return enq970searchDetailFlg
     */
    public int getEnq970searchDetailFlg() {
        return enq970searchDetailFlg__;
    }

    /**
     * <p>enq970searchDetailFlg をセットします。
     * @param enq970searchDetailFlg enq970searchDetailFlg
     */
    public void setEnq970searchDetailFlg(int enq970searchDetailFlg) {
        enq970searchDetailFlg__ = enq970searchDetailFlg;
    }

    /**
     * <p>enq970selectEnqSid を取得します。
     * @return enq970selectEnqSid
     */
    public String[] getEnq970selectEnqSid() {
        return enq970selectEnqSid__;
    }

    /**
     * <p>enq970selectEnqSid をセットします。
     * @param enq970selectEnqSid enq970selectEnqSid
     */
    public void setEnq970selectEnqSid(String[] enq970selectEnqSid) {
        enq970selectEnqSid__ = enq970selectEnqSid;
    }

    /**
     * <p>enq970type を取得します。
     * @return enq970type
     */
    public int getEnq970type() {
        return enq970type__;
    }

    /**
     * <p>enq970type をセットします。
     * @param enq970type enq970type
     */
    public void setEnq970type(int enq970type) {
        enq970type__ = enq970type;
    }

    /**
     * <p>enq970keywordSimple を取得します。
     * @return enq970keywordSimple
     */
    public String getEnq970keywordSimple() {
        return enq970keywordSimple__;
    }

    /**
     * <p>enq970keywordSimple をセットします。
     * @param enq970keywordSimple enq970keywordSimple
     */
    public void setEnq970keywordSimple(String enq970keywordSimple) {
        enq970keywordSimple__ = enq970keywordSimple;
    }

    /**
     * <p>enq970keyword を取得します。
     * @return enq970keyword
     */
    public String getEnq970keyword() {
        return enq970keyword__;
    }

    /**
     * <p>enq970keyword をセットします。
     * @param enq970keyword enq970keyword
     */
    public void setEnq970keyword(String enq970keyword) {
        enq970keyword__ = enq970keyword;
    }

    /**
     * <p>enq970keywordType を取得します。
     * @return enq970keywordType
     */
    public int getEnq970keywordType() {
        return enq970keywordType__;
    }

    /**
     * <p>enq970keywordType をセットします。
     * @param enq970keywordType enq970keywordType
     */
    public void setEnq970keywordType(int enq970keywordType) {
        enq970keywordType__ = enq970keywordType;
    }

    /**
     * <p>enq970sendGroup を取得します。
     * @return enq970sendGroup
     */
    public int getEnq970sendGroup() {
        return enq970sendGroup__;
    }

    /**
     * <p>enq970sendGroup をセットします。
     * @param enq970sendGroup enq970sendGroup
     */
    public void setEnq970sendGroup(int enq970sendGroup) {
        enq970sendGroup__ = enq970sendGroup;
    }

    /**
     * <p>enq970sendUser を取得します。
     * @return enq970sendUser
     */
    public int getEnq970sendUser() {
        return enq970sendUser__;
    }

    /**
     * <p>enq970sendUser をセットします。
     * @param enq970sendUser enq970sendUser
     */
    public void setEnq970sendUser(int enq970sendUser) {
        enq970sendUser__ = enq970sendUser;
    }

    /**
     * <p>enq970sendInput を取得します。
     * @return enq970sendInput
     */
    public int getEnq970sendInput() {
        return enq970sendInput__;
    }

    /**
     * <p>enq970sendInput をセットします。
     * @param enq970sendInput enq970sendInput
     */
    public void setEnq970sendInput(int enq970sendInput) {
        enq970sendInput__ = enq970sendInput;
    }

    /**
     * <p>enq970sendInputText を取得します。
     * @return enq970sendInputText
     */
    public String getEnq970sendInputText() {
        return enq970sendInputText__;
    }

    /**
     * <p>enq970sendInputText をセットします。
     * @param enq970sendInputText enq970sendInputText
     */
    public void setEnq970sendInputText(String enq970sendInputText) {
        enq970sendInputText__ = enq970sendInputText;
    }

    /**
     * <p>enq970makeDateKbn を取得します。
     * @return enq970makeDateKbn
     */
    public int getEnq970makeDateKbn() {
        return enq970makeDateKbn__;
    }

    /**
     * <p>enq970makeDateKbn をセットします。
     * @param enq970makeDateKbn enq970makeDateKbn
     */
    public void setEnq970makeDateKbn(int enq970makeDateKbn) {
        enq970makeDateKbn__ = enq970makeDateKbn;
    }

    /**
     * <p>enq970makeDateFromYear を取得します。
     * @return enq970makeDateFromYear
     */
    public int getEnq970makeDateFromYear() {
        return enq970makeDateFromYear__;
    }

    /**
     * <p>enq970makeDateFromYear をセットします。
     * @param enq970makeDateFromYear enq970makeDateFromYear
     */
    public void setEnq970makeDateFromYear(int enq970makeDateFromYear) {
        enq970makeDateFromYear__ = enq970makeDateFromYear;
    }

    /**
     * <p>enq970makeDateFromMonth を取得します。
     * @return enq970makeDateFromMonth
     */
    public int getEnq970makeDateFromMonth() {
        return enq970makeDateFromMonth__;
    }

    /**
     * <p>enq970makeDateFromMonth をセットします。
     * @param enq970makeDateFromMonth enq970makeDateFromMonth
     */
    public void setEnq970makeDateFromMonth(int enq970makeDateFromMonth) {
        enq970makeDateFromMonth__ = enq970makeDateFromMonth;
    }

    /**
     * <p>enq970makeDateFromDay を取得します。
     * @return enq970makeDateFromDay
     */
    public int getEnq970makeDateFromDay() {
        return enq970makeDateFromDay__;
    }

    /**
     * <p>enq970makeDateFromDay をセットします。
     * @param enq970makeDateFromDay enq970makeDateFromDay
     */
    public void setEnq970makeDateFromDay(int enq970makeDateFromDay) {
        enq970makeDateFromDay__ = enq970makeDateFromDay;
    }

    /**
     * <p>enq970makeDateToYear を取得します。
     * @return enq970makeDateToYear
     */
    public int getEnq970makeDateToYear() {
        return enq970makeDateToYear__;
    }

    /**
     * <p>enq970makeDateToYear をセットします。
     * @param enq970makeDateToYear enq970makeDateToYear
     */
    public void setEnq970makeDateToYear(int enq970makeDateToYear) {
        enq970makeDateToYear__ = enq970makeDateToYear;
    }

    /**
     * <p>enq970makeDateToMonth を取得します。
     * @return enq970makeDateToMonth
     */
    public int getEnq970makeDateToMonth() {
        return enq970makeDateToMonth__;
    }

    /**
     * <p>enq970makeDateToMonth をセットします。
     * @param enq970makeDateToMonth enq970makeDateToMonth
     */
    public void setEnq970makeDateToMonth(int enq970makeDateToMonth) {
        enq970makeDateToMonth__ = enq970makeDateToMonth;
    }

    /**
     * <p>enq970makeDateToDay を取得します。
     * @return enq970makeDateToDay
     */
    public int getEnq970makeDateToDay() {
        return enq970makeDateToDay__;
    }

    /**
     * <p>enq970makeDateToDay をセットします。
     * @param enq970makeDateToDay enq970makeDateToDay
     */
    public void setEnq970makeDateToDay(int enq970makeDateToDay) {
        enq970makeDateToDay__ = enq970makeDateToDay;
    }
    
    /**
     * <p>enq970makeDateFrom を取得します。
     * @return enq970makeDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970makeDateFrom__
     */
    public String getEnq970makeDateFrom() {
        return enq970makeDateFrom__;
    }

    /**
     * <p>enq970makeDateFrom をセットします。
     * @param enq970makeDateFrom enq970makeDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970makeDateFrom__
     */
    public void setEnq970makeDateFrom(String enq970makeDateFrom) {
        enq970makeDateFrom__ = enq970makeDateFrom;
    }

    /**
     * <p>enq970makeDateTo を取得します。
     * @return enq970makeDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970makeDateTo__
     */
    public String getEnq970makeDateTo() {
        return enq970makeDateTo__;
    }

    /**
     * <p>enq970makeDateTo をセットします。
     * @param enq970makeDateTo enq970makeDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970makeDateTo__
     */
    public void setEnq970makeDateTo(String enq970makeDateTo) {
        enq970makeDateTo__ = enq970makeDateTo;
    }

    /**
     * <p>enq970pubDateKbn を取得します。
     * @return enq970pubDateKbn
     */
    public int getEnq970pubDateKbn() {
        return enq970pubDateKbn__;
    }

    /**
     * <p>enq970pubDateKbn をセットします。
     * @param enq970pubDateKbn enq970pubDateKbn
     */
    public void setEnq970pubDateKbn(int enq970pubDateKbn) {
        enq970pubDateKbn__ = enq970pubDateKbn;
    }

    /**
     * <p>enq970pubDateFromYear を取得します。
     * @return enq970pubDateFromYear
     */
    public int getEnq970pubDateFromYear() {
        return enq970pubDateFromYear__;
    }

    /**
     * <p>enq970pubDateFromYear をセットします。
     * @param enq970pubDateFromYear enq970pubDateFromYear
     */
    public void setEnq970pubDateFromYear(int enq970pubDateFromYear) {
        enq970pubDateFromYear__ = enq970pubDateFromYear;
    }

    /**
     * <p>enq970pubDateFromMonth を取得します。
     * @return enq970pubDateFromMonth
     */
    public int getEnq970pubDateFromMonth() {
        return enq970pubDateFromMonth__;
    }

    /**
     * <p>enq970pubDateFromMonth をセットします。
     * @param enq970pubDateFromMonth enq970pubDateFromMonth
     */
    public void setEnq970pubDateFromMonth(int enq970pubDateFromMonth) {
        enq970pubDateFromMonth__ = enq970pubDateFromMonth;
    }

    /**
     * <p>enq970pubDateFromDay を取得します。
     * @return enq970pubDateFromDay
     */
    public int getEnq970pubDateFromDay() {
        return enq970pubDateFromDay__;
    }

    /**
     * <p>enq970pubDateFromDay をセットします。
     * @param enq970pubDateFromDay enq970pubDateFromDay
     */
    public void setEnq970pubDateFromDay(int enq970pubDateFromDay) {
        enq970pubDateFromDay__ = enq970pubDateFromDay;
    }

    /**
     * <p>enq970pubDateToYear を取得します。
     * @return enq970pubDateToYear
     */
    public int getEnq970pubDateToYear() {
        return enq970pubDateToYear__;
    }

    /**
     * <p>enq970pubDateToYear をセットします。
     * @param enq970pubDateToYear enq970pubDateToYear
     */
    public void setEnq970pubDateToYear(int enq970pubDateToYear) {
        enq970pubDateToYear__ = enq970pubDateToYear;
    }

    /**
     * <p>enq970pubDateToMonth を取得します。
     * @return enq970pubDateToMonth
     */
    public int getEnq970pubDateToMonth() {
        return enq970pubDateToMonth__;
    }

    /**
     * <p>enq970pubDateToMonth をセットします。
     * @param enq970pubDateToMonth enq970pubDateToMonth
     */
    public void setEnq970pubDateToMonth(int enq970pubDateToMonth) {
        enq970pubDateToMonth__ = enq970pubDateToMonth;
    }

    /**
     * <p>enq970pubDateToDay を取得します。
     * @return enq970pubDateToDay
     */
    public int getEnq970pubDateToDay() {
        return enq970pubDateToDay__;
    }

    /**
     * <p>enq970pubDateToDay をセットします。
     * @param enq970pubDateToDay enq970pubDateToDay
     */
    public void setEnq970pubDateToDay(int enq970pubDateToDay) {
        enq970pubDateToDay__ = enq970pubDateToDay;
    }

    /**
     * <p>enq970ansDateKbn を取得します。
     * @return enq970ansDateKbn
     */
    public int getEnq970ansDateKbn() {
        return enq970ansDateKbn__;
    }

    /**
     * <p>enq970ansDateKbn をセットします。
     * @param enq970ansDateKbn enq970ansDateKbn
     */
    public void setEnq970ansDateKbn(int enq970ansDateKbn) {
        enq970ansDateKbn__ = enq970ansDateKbn;
    }

    /**
     * <p>enq970ansDateFromYear を取得します。
     * @return enq970ansDateFromYear
     */
    public int getEnq970ansDateFromYear() {
        return enq970ansDateFromYear__;
    }

    /**
     * <p>enq970ansDateFromYear をセットします。
     * @param enq970ansDateFromYear enq970ansDateFromYear
     */
    public void setEnq970ansDateFromYear(int enq970ansDateFromYear) {
        enq970ansDateFromYear__ = enq970ansDateFromYear;
    }

    /**
     * <p>enq970ansDateFromMonth を取得します。
     * @return enq970ansDateFromMonth
     */
    public int getEnq970ansDateFromMonth() {
        return enq970ansDateFromMonth__;
    }

    /**
     * <p>enq970ansDateFromMonth をセットします。
     * @param enq970ansDateFromMonth enq970ansDateFromMonth
     */
    public void setEnq970ansDateFromMonth(int enq970ansDateFromMonth) {
        enq970ansDateFromMonth__ = enq970ansDateFromMonth;
    }

    /**
     * <p>enq970ansDateFromDay を取得します。
     * @return enq970ansDateFromDay
     */
    public int getEnq970ansDateFromDay() {
        return enq970ansDateFromDay__;
    }

    /**
     * <p>enq970ansDateFromDay をセットします。
     * @param enq970ansDateFromDay enq970ansDateFromDay
     */
    public void setEnq970ansDateFromDay(int enq970ansDateFromDay) {
        enq970ansDateFromDay__ = enq970ansDateFromDay;
    }

    /**
     * <p>enq970ansDateToYear を取得します。
     * @return enq970ansDateToYear
     */
    public int getEnq970ansDateToYear() {
        return enq970ansDateToYear__;
    }

    /**
     * <p>enq970ansDateToYear をセットします。
     * @param enq970ansDateToYear enq970ansDateToYear
     */
    public void setEnq970ansDateToYear(int enq970ansDateToYear) {
        enq970ansDateToYear__ = enq970ansDateToYear;
    }

    /**
     * <p>enq970ansDateToMonth を取得します。
     * @return enq970ansDateToMonth
     */
    public int getEnq970ansDateToMonth() {
        return enq970ansDateToMonth__;
    }

    /**
     * <p>enq970ansDateToMonth をセットします。
     * @param enq970ansDateToMonth enq970ansDateToMonth
     */
    public void setEnq970ansDateToMonth(int enq970ansDateToMonth) {
        enq970ansDateToMonth__ = enq970ansDateToMonth;
    }

    /**
     * <p>enq970ansDateToDay を取得します。
     * @return enq970ansDateToDay
     */
    public int getEnq970ansDateToDay() {
        return enq970ansDateToDay__;
    }

    /**
     * <p>enq970ansDateToDay をセットします。
     * @param enq970ansDateToDay enq970ansDateToDay
     */
    public void setEnq970ansDateToDay(int enq970ansDateToDay) {
        enq970ansDateToDay__ = enq970ansDateToDay;
    }
    
    /**
     * <p>enq970ansDateFrom を取得します。
     * @return enq970ansDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970ansDateFrom__
     */
    public String getEnq970ansDateFrom() {
        return enq970ansDateFrom__;
    }

    /**
     * <p>enq970ansDateFrom をセットします。
     * @param enq970ansDateFrom enq970ansDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970ansDateFrom__
     */
    public void setEnq970ansDateFrom(String enq970ansDateFrom) {
        enq970ansDateFrom__ = enq970ansDateFrom;
    }

    /**
     * <p>enq970ansDateTo を取得します。
     * @return enq970ansDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970ansDateTo__
     */
    public String getEnq970ansDateTo() {
        return enq970ansDateTo__;
    }

    /**
     * <p>enq970ansDateTo をセットします。
     * @param enq970ansDateTo enq970ansDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970ansDateTo__
     */
    public void setEnq970ansDateTo(String enq970ansDateTo) {
        enq970ansDateTo__ = enq970ansDateTo;
    }

    /**
     * <p>enq970priority を取得します。
     * @return enq970priority
     */
    public int[] getEnq970priority() {
        return enq970priority__;
    }

    /**
     * <p>enq970priority をセットします。
     * @param enq970priority enq970priority
     */
    public void setEnq970priority(int[] enq970priority) {
        enq970priority__ = enq970priority;
    }

    /**
     * <p>enq970status を取得します。
     * @return enq970status
     */
    public int[] getEnq970status() {
        return enq970status__;
    }

    /**
     * <p>enq970status をセットします。
     * @param enq970status enq970status
     */
    public void setEnq970status(int[] enq970status) {
        enq970status__ = enq970status;
    }

    /**
     * <p>enq970anony を取得します。
     * @return enq970anony
     */
    public int getEnq970anony() {
        return enq970anony__;
    }

    /**
     * <p>enq970anony をセットします。
     * @param enq970anony enq970anony
     */
    public void setEnq970anony(int enq970anony) {
        enq970anony__ = enq970anony;
    }

    /**
     * <p>enq970svType を取得します。
     * @return enq970svType
     */
    public int getEnq970svType() {
        return enq970svType__;
    }

    /**
     * <p>enq970svType をセットします。
     * @param enq970svType enq970svType
     */
    public void setEnq970svType(int enq970svType) {
        enq970svType__ = enq970svType;
    }

    /**
     * <p>enq970svKeywordSimple を取得します。
     * @return enq970svKeywordSimple
     */
    public String getEnq970svKeywordSimple() {
        return enq970svKeywordSimple__;
    }

    /**
     * <p>enq970svKeywordSimple をセットします。
     * @param enq970svKeywordSimple enq970svKeywordSimple
     */
    public void setEnq970svKeywordSimple(String enq970svKeywordSimple) {
        enq970svKeywordSimple__ = enq970svKeywordSimple;
    }

    /**
     * <p>enq970svKeyword を取得します。
     * @return enq970svKeyword
     */
    public String getEnq970svKeyword() {
        return enq970svKeyword__;
    }

    /**
     * <p>enq970svKeyword をセットします。
     * @param enq970svKeyword enq970svKeyword
     */
    public void setEnq970svKeyword(String enq970svKeyword) {
        enq970svKeyword__ = enq970svKeyword;
    }

    /**
     * <p>enq970svKeywordType を取得します。
     * @return enq970svKeywordType
     */
    public int getEnq970svKeywordType() {
        return enq970svKeywordType__;
    }

    /**
     * <p>enq970svKeywordType をセットします。
     * @param enq970svKeywordType enq970svKeywordType
     */
    public void setEnq970svKeywordType(int enq970svKeywordType) {
        enq970svKeywordType__ = enq970svKeywordType;
    }

    /**
     * <p>enq970svSendGroup を取得します。
     * @return enq970svSendGroup
     */
    public int getEnq970svSendGroup() {
        return enq970svSendGroup__;
    }

    /**
     * <p>enq970svSendGroup をセットします。
     * @param enq970svSendGroup enq970svSendGroup
     */
    public void setEnq970svSendGroup(int enq970svSendGroup) {
        enq970svSendGroup__ = enq970svSendGroup;
    }

    /**
     * <p>enq970svSendUser を取得します。
     * @return enq970svSendUser
     */
    public int getEnq970svSendUser() {
        return enq970svSendUser__;
    }

    /**
     * <p>enq970svSendUser をセットします。
     * @param enq970svSendUser enq970svSendUser
     */
    public void setEnq970svSendUser(int enq970svSendUser) {
        enq970svSendUser__ = enq970svSendUser;
    }

    /**
     * <p>enq970svSendInput を取得します。
     * @return enq970svSendInput
     */
    public int getEnq970svSendInput() {
        return enq970svSendInput__;
    }

    /**
     * <p>enq970svSendInput をセットします。
     * @param enq970svSendInput enq970svSendInput
     */
    public void setEnq970svSendInput(int enq970svSendInput) {
        enq970svSendInput__ = enq970svSendInput;
    }

    /**
     * <p>enq970svSendInputText を取得します。
     * @return enq970svSendInputText
     */
    public String getEnq970svSendInputText() {
        return enq970svSendInputText__;
    }

    /**
     * <p>enq970svSendInputText をセットします。
     * @param enq970svSendInputText enq970svSendInputText
     */
    public void setEnq970svSendInputText(String enq970svSendInputText) {
        enq970svSendInputText__ = enq970svSendInputText;
    }

    /**
     * <p>enq970svMakeDateKbn を取得します。
     * @return enq970svMakeDateKbn
     */
    public int getEnq970svMakeDateKbn() {
        return enq970svMakeDateKbn__;
    }

    /**
     * <p>enq970svMakeDateKbn をセットします。
     * @param enq970svMakeDateKbn enq970svMakeDateKbn
     */
    public void setEnq970svMakeDateKbn(int enq970svMakeDateKbn) {
        enq970svMakeDateKbn__ = enq970svMakeDateKbn;
    }

    /**
     * <p>enq970svMakeDateFromYear を取得します。
     * @return enq970svMakeDateFromYear
     */
    public int getEnq970svMakeDateFromYear() {
        return enq970svMakeDateFromYear__;
    }

    /**
     * <p>enq970svMakeDateFromYear をセットします。
     * @param enq970svMakeDateFromYear enq970svMakeDateFromYear
     */
    public void setEnq970svMakeDateFromYear(int enq970svMakeDateFromYear) {
        enq970svMakeDateFromYear__ = enq970svMakeDateFromYear;
    }

    /**
     * <p>enq970svMakeDateFromMonth を取得します。
     * @return enq970svMakeDateFromMonth
     */
    public int getEnq970svMakeDateFromMonth() {
        return enq970svMakeDateFromMonth__;
    }

    /**
     * <p>enq970svMakeDateFromMonth をセットします。
     * @param enq970svMakeDateFromMonth enq970svMakeDateFromMonth
     */
    public void setEnq970svMakeDateFromMonth(int enq970svMakeDateFromMonth) {
        enq970svMakeDateFromMonth__ = enq970svMakeDateFromMonth;
    }

    /**
     * <p>enq970svMakeDateFromDay を取得します。
     * @return enq970svMakeDateFromDay
     */
    public int getEnq970svMakeDateFromDay() {
        return enq970svMakeDateFromDay__;
    }

    /**
     * <p>enq970svMakeDateFromDay をセットします。
     * @param enq970svMakeDateFromDay enq970svMakeDateFromDay
     */
    public void setEnq970svMakeDateFromDay(int enq970svMakeDateFromDay) {
        enq970svMakeDateFromDay__ = enq970svMakeDateFromDay;
    }

    /**
     * <p>enq970svMakeDateToYear を取得します。
     * @return enq970svMakeDateToYear
     */
    public int getEnq970svMakeDateToYear() {
        return enq970svMakeDateToYear__;
    }

    /**
     * <p>enq970svMakeDateToYear をセットします。
     * @param enq970svMakeDateToYear enq970svMakeDateToYear
     */
    public void setEnq970svMakeDateToYear(int enq970svMakeDateToYear) {
        enq970svMakeDateToYear__ = enq970svMakeDateToYear;
    }

    /**
     * <p>enq970svMakeDateToMonth を取得します。
     * @return enq970svMakeDateToMonth
     */
    public int getEnq970svMakeDateToMonth() {
        return enq970svMakeDateToMonth__;
    }

    /**
     * <p>enq970svMakeDateToMonth をセットします。
     * @param enq970svMakeDateToMonth enq970svMakeDateToMonth
     */
    public void setEnq970svMakeDateToMonth(int enq970svMakeDateToMonth) {
        enq970svMakeDateToMonth__ = enq970svMakeDateToMonth;
    }

    /**
     * <p>enq970svMakeDateToDay を取得します。
     * @return enq970svMakeDateToDay
     */
    public int getEnq970svMakeDateToDay() {
        return enq970svMakeDateToDay__;
    }

    /**
     * <p>enq970svMakeDateToDay をセットします。
     * @param enq970svMakeDateToDay enq970svMakeDateToDay
     */
    public void setEnq970svMakeDateToDay(int enq970svMakeDateToDay) {
        enq970svMakeDateToDay__ = enq970svMakeDateToDay;
    }
    
    /**
     * <p>enq970svMakeDateFrom を取得します。
     * @return enq970svMakeDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svMakeDateFrom__
     */
    public String getEnq970svMakeDateFrom() {
        return enq970svMakeDateFrom__;
    }

    /**
     * <p>enq970svMakeDateFrom をセットします。
     * @param enq970svMakeDateFrom enq970svMakeDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svMakeDateFrom__
     */
    public void setEnq970svMakeDateFrom(String enq970svMakeDateFrom) {
        enq970svMakeDateFrom__ = enq970svMakeDateFrom;
    }

    /**
     * <p>enq970svMakeDateTo を取得します。
     * @return enq970svMakeDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svMakeDateTo__
     */
    public String getEnq970svMakeDateTo() {
        return enq970svMakeDateTo__;
    }

    /**
     * <p>enq970svMakeDateTo をセットします。
     * @param enq970svMakeDateTo enq970svMakeDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svMakeDateTo__
     */
    public void setEnq970svMakeDateTo(String enq970svMakeDateTo) {
        enq970svMakeDateTo__ = enq970svMakeDateTo;
    }

    /**
     * <p>enq970svPubDateKbn を取得します。
     * @return enq970svPubDateKbn
     */
    public int getEnq970svPubDateKbn() {
        return enq970svPubDateKbn__;
    }

    /**
     * <p>enq970svPubDateKbn をセットします。
     * @param enq970svPubDateKbn enq970svPubDateKbn
     */
    public void setEnq970svPubDateKbn(int enq970svPubDateKbn) {
        enq970svPubDateKbn__ = enq970svPubDateKbn;
    }

    /**
     * <p>enq970svPubDateFromYear を取得します。
     * @return enq970svPubDateFromYear
     */
    public int getEnq970svPubDateFromYear() {
        return enq970svPubDateFromYear__;
    }

    /**
     * <p>enq970svPubDateFromYear をセットします。
     * @param enq970svPubDateFromYear enq970svPubDateFromYear
     */
    public void setEnq970svPubDateFromYear(int enq970svPubDateFromYear) {
        enq970svPubDateFromYear__ = enq970svPubDateFromYear;
    }

    /**
     * <p>enq970svPubDateFromMonth を取得します。
     * @return enq970svPubDateFromMonth
     */
    public int getEnq970svPubDateFromMonth() {
        return enq970svPubDateFromMonth__;
    }

    /**
     * <p>enq970svPubDateFromMonth をセットします。
     * @param enq970svPubDateFromMonth enq970svPubDateFromMonth
     */
    public void setEnq970svPubDateFromMonth(int enq970svPubDateFromMonth) {
        enq970svPubDateFromMonth__ = enq970svPubDateFromMonth;
    }

    /**
     * <p>enq970svPubDateFromDay を取得します。
     * @return enq970svPubDateFromDay
     */
    public int getEnq970svPubDateFromDay() {
        return enq970svPubDateFromDay__;
    }

    /**
     * <p>enq970svPubDateFromDay をセットします。
     * @param enq970svPubDateFromDay enq970svPubDateFromDay
     */
    public void setEnq970svPubDateFromDay(int enq970svPubDateFromDay) {
        enq970svPubDateFromDay__ = enq970svPubDateFromDay;
    }

    /**
     * <p>enq970svPubDateToYear を取得します。
     * @return enq970svPubDateToYear
     */
    public int getEnq970svPubDateToYear() {
        return enq970svPubDateToYear__;
    }

    /**
     * <p>enq970svPubDateToYear をセットします。
     * @param enq970svPubDateToYear enq970svPubDateToYear
     */
    public void setEnq970svPubDateToYear(int enq970svPubDateToYear) {
        enq970svPubDateToYear__ = enq970svPubDateToYear;
    }

    /**
     * <p>enq970svPubDateToMonth を取得します。
     * @return enq970svPubDateToMonth
     */
    public int getEnq970svPubDateToMonth() {
        return enq970svPubDateToMonth__;
    }

    /**
     * <p>enq970svPubDateToMonth をセットします。
     * @param enq970svPubDateToMonth enq970svPubDateToMonth
     */
    public void setEnq970svPubDateToMonth(int enq970svPubDateToMonth) {
        enq970svPubDateToMonth__ = enq970svPubDateToMonth;
    }

    /**
     * <p>enq970svPubDateToDay を取得します。
     * @return enq970svPubDateToDay
     */
    public int getEnq970svPubDateToDay() {
        return enq970svPubDateToDay__;
    }

    /**
     * <p>enq970svPubDateToDay をセットします。
     * @param enq970svPubDateToDay enq970svPubDateToDay
     */
    public void setEnq970svPubDateToDay(int enq970svPubDateToDay) {
        enq970svPubDateToDay__ = enq970svPubDateToDay;
    }

    /**
     * <p>enq970svAnsDateKbn を取得します。
     * @return enq970svAnsDateKbn
     */
    public int getEnq970svAnsDateKbn() {
        return enq970svAnsDateKbn__;
    }

    /**
     * <p>enq970svAnsDateKbn をセットします。
     * @param enq970svAnsDateKbn enq970svAnsDateKbn
     */
    public void setEnq970svAnsDateKbn(int enq970svAnsDateKbn) {
        enq970svAnsDateKbn__ = enq970svAnsDateKbn;
    }

    /**
     * <p>enq970svAnsDateFromYear を取得します。
     * @return enq970svAnsDateFromYear
     */
    public int getEnq970svAnsDateFromYear() {
        return enq970svAnsDateFromYear__;
    }

    /**
     * <p>enq970svAnsDateFromYear をセットします。
     * @param enq970svAnsDateFromYear enq970svAnsDateFromYear
     */
    public void setEnq970svAnsDateFromYear(int enq970svAnsDateFromYear) {
        enq970svAnsDateFromYear__ = enq970svAnsDateFromYear;
    }

    /**
     * <p>enq970svAnsDateFromMonth を取得します。
     * @return enq970svAnsDateFromMonth
     */
    public int getEnq970svAnsDateFromMonth() {
        return enq970svAnsDateFromMonth__;
    }

    /**
     * <p>enq970svAnsDateFromMonth をセットします。
     * @param enq970svAnsDateFromMonth enq970svAnsDateFromMonth
     */
    public void setEnq970svAnsDateFromMonth(int enq970svAnsDateFromMonth) {
        enq970svAnsDateFromMonth__ = enq970svAnsDateFromMonth;
    }

    /**
     * <p>enq970svAnsDateFromDay を取得します。
     * @return enq970svAnsDateFromDay
     */
    public int getEnq970svAnsDateFromDay() {
        return enq970svAnsDateFromDay__;
    }

    /**
     * <p>enq970svAnsDateFromDay をセットします。
     * @param enq970svAnsDateFromDay enq970svAnsDateFromDay
     */
    public void setEnq970svAnsDateFromDay(int enq970svAnsDateFromDay) {
        enq970svAnsDateFromDay__ = enq970svAnsDateFromDay;
    }

    /**
     * <p>enq970svAnsDateToYear を取得します。
     * @return enq970svAnsDateToYear
     */
    public int getEnq970svAnsDateToYear() {
        return enq970svAnsDateToYear__;
    }

    /**
     * <p>enq970svAnsDateToYear をセットします。
     * @param enq970svAnsDateToYear enq970svAnsDateToYear
     */
    public void setEnq970svAnsDateToYear(int enq970svAnsDateToYear) {
        enq970svAnsDateToYear__ = enq970svAnsDateToYear;
    }

    /**
     * <p>enq970svAnsDateToMonth を取得します。
     * @return enq970svAnsDateToMonth
     */
    public int getEnq970svAnsDateToMonth() {
        return enq970svAnsDateToMonth__;
    }

    /**
     * <p>enq970svAnsDateToMonth をセットします。
     * @param enq970svAnsDateToMonth enq970svAnsDateToMonth
     */
    public void setEnq970svAnsDateToMonth(int enq970svAnsDateToMonth) {
        enq970svAnsDateToMonth__ = enq970svAnsDateToMonth;
    }

    /**
     * <p>enq970svAnsDateToDay を取得します。
     * @return enq970svAnsDateToDay
     */
    public int getEnq970svAnsDateToDay() {
        return enq970svAnsDateToDay__;
    }

    /**
     * <p>enq970svAnsDateToDay をセットします。
     * @param enq970svAnsDateToDay enq970svAnsDateToDay
     */
    public void setEnq970svAnsDateToDay(int enq970svAnsDateToDay) {
        enq970svAnsDateToDay__ = enq970svAnsDateToDay;
    }
    
    /**
     * <p>enq970svAnsDateFrom を取得します。
     * @return enq970svAnsDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svAnsDateFrom__
     */
    public String getEnq970svAnsDateFrom() {
        return enq970svAnsDateFrom__;
    }

    /**
     * <p>enq970svAnsDateFrom をセットします。
     * @param enq970svAnsDateFrom enq970svAnsDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svAnsDateFrom__
     */
    public void setEnq970svAnsDateFrom(String enq970svAnsDateFrom) {
        enq970svAnsDateFrom__ = enq970svAnsDateFrom;
    }

    /**
     * <p>enq970svAnsDateTo を取得します。
     * @return enq970svAnsDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svAnsDateTo__
     */
    public String getEnq970svAnsDateTo() {
        return enq970svAnsDateTo__;
    }

    /**
     * <p>enq970svAnsDateTo をセットします。
     * @param enq970svAnsDateTo enq970svAnsDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svAnsDateTo__
     */
    public void setEnq970svAnsDateTo(String enq970svAnsDateTo) {
        enq970svAnsDateTo__ = enq970svAnsDateTo;
    }

    /**
     * <p>enq970svPriority を取得します。
     * @return enq970svPriority
     */
    public int[] getEnq970svPriority() {
        return enq970svPriority__;
    }

    /**
     * <p>enq970svPriority をセットします。
     * @param enq970svPriority enq970svPriority
     */
    public void setEnq970svPriority(int[] enq970svPriority) {
        enq970svPriority__ = enq970svPriority;
    }

    /**
     * <p>enq970svStatus を取得します。
     * @return enq970svStatus
     */
    public int[] getEnq970svStatus() {
        return enq970svStatus__;
    }

    /**
     * <p>enq970svStatus をセットします。
     * @param enq970svStatus enq970svStatus
     */
    public void setEnq970svStatus(int[] enq970svStatus) {
        enq970svStatus__ = enq970svStatus;
    }

    /**
     * <p>enq970svAnony を取得します。
     * @return enq970svAnony
     */
    public int getEnq970svAnony() {
        return enq970svAnony__;
    }

    /**
     * <p>enq970svAnony をセットします。
     * @param enq970svAnony enq970svAnony
     */
    public void setEnq970svAnony(int enq970svAnony) {
        enq970svAnony__ = enq970svAnony;
    }

    /**
     * <p>enq970pageTop を取得します。
     * @return enq970pageTop
     */
    public int getEnq970pageTop() {
        return enq970pageTop__;
    }

    /**
     * <p>enq970pageTop をセットします。
     * @param enq970pageTop enq970pageTop
     */
    public void setEnq970pageTop(int enq970pageTop) {
        enq970pageTop__ = enq970pageTop;
    }

    /**
     * <p>enq970pageBottom を取得します。
     * @return enq970pageBottom
     */
    public int getEnq970pageBottom() {
        return enq970pageBottom__;
    }

    /**
     * <p>enq970pageBottom をセットします。
     * @param enq970pageBottom enq970pageBottom
     */
    public void setEnq970pageBottom(int enq970pageBottom) {
        enq970pageBottom__ = enq970pageBottom;
    }

    /**
     * <p>enq970sortKey を取得します。
     * @return enq970sortKey
     */
    public int getEnq970sortKey() {
        return enq970sortKey__;
    }

    /**
     * <p>enq970sortKey をセットします。
     * @param enq970sortKey enq970sortKey
     */
    public void setEnq970sortKey(int enq970sortKey) {
        enq970sortKey__ = enq970sortKey;
    }

    /**
     * <p>enq970order を取得します。
     * @return enq970order
     */
    public int getEnq970order() {
        return enq970order__;
    }

    /**
     * <p>enq970order をセットします。
     * @param enq970order enq970order
     */
    public void setEnq970order(int enq970order) {
        enq970order__ = enq970order;
    }

    /**
     * <p>enq970BackPage を取得します。
     * @return enq970BackPage
     */
    public int getEnq970BackPage() {
        return enq970BackPage__;
    }

    /**
     * <p>enq970BackPage をセットします。
     * @param enq970BackPage enq970BackPage
     */
    public void setEnq970BackPage(int enq970BackPage) {
        enq970BackPage__ = enq970BackPage;
    }

    /**
     * <p>enq970resPubDateKbn を取得します。
     * @return enq970resPubDateKbn
     */
    public int getEnq970resPubDateKbn() {
        return enq970resPubDateKbn__;
    }

    /**
     * <p>enq970resPubDateKbn をセットします。
     * @param enq970resPubDateKbn enq970resPubDateKbn
     */
    public void setEnq970resPubDateKbn(int enq970resPubDateKbn) {
        enq970resPubDateKbn__ = enq970resPubDateKbn;
    }

    /**
     * <p>enq970resPubDateFromYear を取得します。
     * @return enq970resPubDateFromYear
     */
    public int getEnq970resPubDateFromYear() {
        return enq970resPubDateFromYear__;
    }

    /**
     * <p>enq970resPubDateFromYear をセットします。
     * @param enq970resPubDateFromYear enq970resPubDateFromYear
     */
    public void setEnq970resPubDateFromYear(int enq970resPubDateFromYear) {
        enq970resPubDateFromYear__ = enq970resPubDateFromYear;
    }

    /**
     * <p>enq970resPubDateFromMonth を取得します。
     * @return enq970resPubDateFromMonth
     */
    public int getEnq970resPubDateFromMonth() {
        return enq970resPubDateFromMonth__;
    }

    /**
     * <p>enq970resPubDateFromMonth をセットします。
     * @param enq970resPubDateFromMonth enq970resPubDateFromMonth
     */
    public void setEnq970resPubDateFromMonth(int enq970resPubDateFromMonth) {
        enq970resPubDateFromMonth__ = enq970resPubDateFromMonth;
    }

    /**
     * <p>enq970resPubDateFromDay を取得します。
     * @return enq970resPubDateFromDay
     */
    public int getEnq970resPubDateFromDay() {
        return enq970resPubDateFromDay__;
    }

    /**
     * <p>enq970resPubDateFromDay をセットします。
     * @param enq970resPubDateFromDay enq970resPubDateFromDay
     */
    public void setEnq970resPubDateFromDay(int enq970resPubDateFromDay) {
        enq970resPubDateFromDay__ = enq970resPubDateFromDay;
    }

    /**
     * <p>enq970resPubDateToYear を取得します。
     * @return enq970resPubDateToYear
     */
    public int getEnq970resPubDateToYear() {
        return enq970resPubDateToYear__;
    }

    /**
     * <p>enq970resPubDateToYear をセットします。
     * @param enq970resPubDateToYear enq970resPubDateToYear
     */
    public void setEnq970resPubDateToYear(int enq970resPubDateToYear) {
        enq970resPubDateToYear__ = enq970resPubDateToYear;
    }

    /**
     * <p>enq970resPubDateToMonth を取得します。
     * @return enq970resPubDateToMonth
     */
    public int getEnq970resPubDateToMonth() {
        return enq970resPubDateToMonth__;
    }

    /**
     * <p>enq970resPubDateToMonth をセットします。
     * @param enq970resPubDateToMonth enq970resPubDateToMonth
     */
    public void setEnq970resPubDateToMonth(int enq970resPubDateToMonth) {
        enq970resPubDateToMonth__ = enq970resPubDateToMonth;
    }

    /**
     * <p>enq970resPubDateToDay を取得します。
     * @return enq970resPubDateToDay
     */
    public int getEnq970resPubDateToDay() {
        return enq970resPubDateToDay__;
    }

    /**
     * <p>enq970resPubDateToDay をセットします。
     * @param enq970resPubDateToDay enq970resPubDateToDay
     */
    public void setEnq970resPubDateToDay(int enq970resPubDateToDay) {
        enq970resPubDateToDay__ = enq970resPubDateToDay;
    }
    
    /**
     * <p>enq970resPubDateFrom を取得します。
     * @return enq970resPubDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970resPubDateFrom__
     */
    public String getEnq970resPubDateFrom() {
        return enq970resPubDateFrom__;
    }

    /**
     * <p>enq970resPubDateFrom をセットします。
     * @param enq970resPubDateFrom enq970resPubDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970resPubDateFrom__
     */
    public void setEnq970resPubDateFrom(String enq970resPubDateFrom) {
        enq970resPubDateFrom__ = enq970resPubDateFrom;
    }

    /**
     * <p>enq970resPubDateTo を取得します。
     * @return enq970resPubDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970resPubDateTo__
     */
    public String getEnq970resPubDateTo() {
        return enq970resPubDateTo__;
    }

    /**
     * <p>enq970resPubDateTo をセットします。
     * @param enq970resPubDateTo enq970resPubDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970resPubDateTo__
     */
    public void setEnq970resPubDateTo(String enq970resPubDateTo) {
        enq970resPubDateTo__ = enq970resPubDateTo;
    }

    /**
     * <p>enq970svResPubDateKbn を取得します。
     * @return enq970svResPubDateKbn
     */
    public int getEnq970svResPubDateKbn() {
        return enq970svResPubDateKbn__;
    }

    /**
     * <p>enq970svResPubDateKbn をセットします。
     * @param enq970svResPubDateKbn enq970svResPubDateKbn
     */
    public void setEnq970svResPubDateKbn(int enq970svResPubDateKbn) {
        enq970svResPubDateKbn__ = enq970svResPubDateKbn;
    }

    /**
     * <p>enq970svResPubDateFromYear を取得します。
     * @return enq970svResPubDateFromYear
     */
    public int getEnq970svResPubDateFromYear() {
        return enq970svResPubDateFromYear__;
    }

    /**
     * <p>enq970svResPubDateFromYear をセットします。
     * @param enq970svResPubDateFromYear enq970svResPubDateFromYear
     */
    public void setEnq970svResPubDateFromYear(int enq970svResPubDateFromYear) {
        enq970svResPubDateFromYear__ = enq970svResPubDateFromYear;
    }

    /**
     * <p>enq970svResPubDateFromMonth を取得します。
     * @return enq970svResPubDateFromMonth
     */
    public int getEnq970svResPubDateFromMonth() {
        return enq970svResPubDateFromMonth__;
    }

    /**
     * <p>enq970svResPubDateFromMonth をセットします。
     * @param enq970svResPubDateFromMonth enq970svResPubDateFromMonth
     */
    public void setEnq970svResPubDateFromMonth(int enq970svResPubDateFromMonth) {
        enq970svResPubDateFromMonth__ = enq970svResPubDateFromMonth;
    }

    /**
     * <p>enq970svResPubDateFromDay を取得します。
     * @return enq970svResPubDateFromDay
     */
    public int getEnq970svResPubDateFromDay() {
        return enq970svResPubDateFromDay__;
    }

    /**
     * <p>enq970svResPubDateFromDay をセットします。
     * @param enq970svResPubDateFromDay enq970svResPubDateFromDay
     */
    public void setEnq970svResPubDateFromDay(int enq970svResPubDateFromDay) {
        enq970svResPubDateFromDay__ = enq970svResPubDateFromDay;
    }

    /**
     * <p>enq970svResPubDateToYear を取得します。
     * @return enq970svResPubDateToYear
     */
    public int getEnq970svResPubDateToYear() {
        return enq970svResPubDateToYear__;
    }

    /**
     * <p>enq970svResPubDateToYear をセットします。
     * @param enq970svResPubDateToYear enq970svResPubDateToYear
     */
    public void setEnq970svResPubDateToYear(int enq970svResPubDateToYear) {
        enq970svResPubDateToYear__ = enq970svResPubDateToYear;
    }

    /**
     * <p>enq970svResPubDateToMonth を取得します。
     * @return enq970svResPubDateToMonth
     */
    public int getEnq970svResPubDateToMonth() {
        return enq970svResPubDateToMonth__;
    }

    /**
     * <p>enq970svResPubDateToMonth をセットします。
     * @param enq970svResPubDateToMonth enq970svResPubDateToMonth
     */
    public void setEnq970svResPubDateToMonth(int enq970svResPubDateToMonth) {
        enq970svResPubDateToMonth__ = enq970svResPubDateToMonth;
    }

    /**
     * <p>enq970svResPubDateToDay を取得します。
     * @return enq970svResPubDateToDay
     */
    public int getEnq970svResPubDateToDay() {
        return enq970svResPubDateToDay__;
    }

    /**
     * <p>enq970svResPubDateToDay をセットします。
     * @param enq970svResPubDateToDay enq970svResPubDateToDay
     */
    public void setEnq970svResPubDateToDay(int enq970svResPubDateToDay) {
        enq970svResPubDateToDay__ = enq970svResPubDateToDay;
    }

    /**
     * <p>enq970svResPubDateFrom を取得します。
     * @return enq970svResPubDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svResPubDateFrom__
     */
    public int getEnq970svResPubDateFrom() {
        return enq970svResPubDateFrom__;
    }

    /**
     * <p>enq970svResPubDateFrom をセットします。
     * @param enq970svResPubDateFrom enq970svResPubDateFrom
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svResPubDateFrom__
     */
    public void setEnq970svResPubDateFrom(int enq970svResPubDateFrom) {
        enq970svResPubDateFrom__ = enq970svResPubDateFrom;
    }

    /**
     * <p>enq970svResPubDateTo を取得します。
     * @return enq970svResPubDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svResPubDateTo__
     */
    public int getEnq970svResPubDateTo() {
        return enq970svResPubDateTo__;
    }

    /**
     * <p>enq970svResPubDateTo をセットします。
     * @param enq970svResPubDateTo enq970svResPubDateTo
     * @see jp.groupsession.v2.enq.enq970.Enq970Form#enq970svResPubDateTo__
     */
    public void setEnq970svResPubDateTo(int enq970svResPubDateTo) {
        enq970svResPubDateTo__ = enq970svResPubDateTo;
    }
}

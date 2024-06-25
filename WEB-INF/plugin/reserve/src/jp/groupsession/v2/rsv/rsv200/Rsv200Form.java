package jp.groupsession.v2.rsv.rsv200;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.rsv080.Rsv080Form;
import jp.groupsession.v2.rsv.rsv080.Rsv080Model;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 施設予約 施設一括設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv200Form extends Rsv080Form {

    /** 表示項目1項目チェック */
    private int rsv200Prop1Check__ = GSConstReserve.PROP_CHECK_NO;
    /** 表示項目2項目チェック */
    private int rsv200Prop2Check__ = GSConstReserve.PROP_CHECK_NO;
    /** 表示項目3項目チェック */
    private int rsv200Prop3Check__ = GSConstReserve.PROP_CHECK_NO;
    /** 表示項目4項目チェック */
    private int rsv200Prop4Check__ = GSConstReserve.PROP_CHECK_NO;
    /** 表示項目5項目チェック */
    private int rsv200Prop5Check__ = GSConstReserve.PROP_CHECK_NO;
    /** 表示項目6項目チェック */
    private int rsv200Prop6Check__ = GSConstReserve.PROP_CHECK_NO;
    /** 表示項目7項目チェック */
    private int rsv200Prop7Check__ = GSConstReserve.PROP_CHECK_NO;
    /** 表示項目7項目チェック */
    private int rsv200BikoCheck__ = GSConstReserve.PROP_CHECK_NO;

    /** 表示項目1項目名称 */
    private String rsv200PropHeaderName1__ = null;
    /** 表示項目2項目名称 */
    private String rsv200PropHeaderName2__ = null;
    /** 表示項目3項目名称 */
    private String rsv200PropHeaderName3__ = null;
    /** 表示項目4項目名称 */
    private String rsv200PropHeaderName4__ = null;
    /** 表示項目5項目名称 */
    private String rsv200PropHeaderName5__ = null;
    /** 表示項目6項目名称 */
    private String rsv200PropHeaderName6__ = null;
    /** 表示項目7項目名称 */
    private String rsv200PropHeaderName7__ = null;

    /** 表示項目1項目入力欄 */
    private String rsv200Prop1Value__ = null;
    /** 表示項目2項目入力欄 */
    private String rsv200Prop2Value__ = String.valueOf(GSConstReserve.PROP_KBN_KA);
    /** 表示項目3項目入力欄 */
    private String rsv200Prop3Value__ = String.valueOf(GSConstReserve.PROP_KBN_KA);
    /** 表示項目4項目入力欄 */
    private String rsv200Prop4Value__ = null;
    /** 表示項目5項目入力欄 */
    private String rsv200Prop5Value__ = null;
    /** 表示項目6項目入力欄 */
    private String rsv200Prop6Value__ = null;
    /** 表示項目7項目入力欄 */
    private String rsv200Prop7Value__ = String.valueOf(GSConstReserve.PROP_KBN_KA);
    /** 備考 */
    private String rsv200Biko__ = null;

    /** 施設一覧 */
    private ArrayList<RsvSisDataModel> rsv200SisetuList__ = null;
    /** 対象施設選択 */
    private String[] rsv200TargetSisetu__ = null;

    /**
     * <p>rsv200PropHeaderName1__ を取得します。
     * @return rsv200PropHeaderName1
     */
    public String getRsv200PropHeaderName1() {
        return rsv200PropHeaderName1__;
    }

    /**
     * <p>rsv200PropHeaderName1__ をセットします。
     * @param rsv200PropHeaderName1 rsv200PropHeaderName1__
     */
    public void setRsv200PropHeaderName1(String rsv200PropHeaderName1) {
        rsv200PropHeaderName1__ = rsv200PropHeaderName1;
    }

    /**
     * <p>rsv200PropHeaderName2__ を取得します。
     * @return rsv200PropHeaderName2
     */
    public String getRsv200PropHeaderName2() {
        return rsv200PropHeaderName2__;
    }

    /**
     * <p>rsv200PropHeaderName2__ をセットします。
     * @param rsv200PropHeaderName2 rsv200PropHeaderName2__
     */
    public void setRsv200PropHeaderName2(String rsv200PropHeaderName2) {
        rsv200PropHeaderName2__ = rsv200PropHeaderName2;
    }

    /**
     * <p>rsv200PropHeaderName3__ を取得します。
     * @return rsv200PropHeaderName3
     */
    public String getRsv200PropHeaderName3() {
        return rsv200PropHeaderName3__;
    }

    /**
     * <p>rsv200PropHeaderName3__ をセットします。
     * @param rsv200PropHeaderName3 rsv200PropHeaderName3__
     */
    public void setRsv200PropHeaderName3(String rsv200PropHeaderName3) {
        rsv200PropHeaderName3__ = rsv200PropHeaderName3;
    }

    /**
     * <p>rsv200PropHeaderName4__ を取得します。
     * @return rsv200PropHeaderName4
     */
    public String getRsv200PropHeaderName4() {
        return rsv200PropHeaderName4__;
    }

    /**
     * <p>rsv200PropHeaderName4__ をセットします。
     * @param rsv200PropHeaderName4 rsv200PropHeaderName4__
     */
    public void setRsv200PropHeaderName4(String rsv200PropHeaderName4) {
        rsv200PropHeaderName4__ = rsv200PropHeaderName4;
    }

    /**
     * <p>rsv200PropHeaderName5__ を取得します。
     * @return rsv200PropHeaderName5
     */
    public String getRsv200PropHeaderName5() {
        return rsv200PropHeaderName5__;
    }

    /**
     * <p>rsv200PropHeaderName5__ をセットします。
     * @param rsv200PropHeaderName5 rsv200PropHeaderName5__
     */
    public void setRsv200PropHeaderName5(String rsv200PropHeaderName5) {
        rsv200PropHeaderName5__ = rsv200PropHeaderName5;
    }

    /**
     * <p>rsv200PropHeaderName6__ を取得します。
     * @return rsv200PropHeaderName6
     */
    public String getRsv200PropHeaderName6() {
        return rsv200PropHeaderName6__;
    }

    /**
     * <p>rsv200PropHeaderName6__ をセットします。
     * @param rsv200PropHeaderName6 rsv200PropHeaderName6__
     */
    public void setRsv200PropHeaderName6(String rsv200PropHeaderName6) {
        rsv200PropHeaderName6__ = rsv200PropHeaderName6;
    }

    /**
     * <p>rsv200PropHeaderName7__ を取得します。
     * @return rsv200PropHeaderName7
     */
    public String getRsv200PropHeaderName7() {
        return rsv200PropHeaderName7__;
    }

    /**
     * <p>rsv200PropHeaderName7__ をセットします。
     * @param rsv200PropHeaderName7 rsv200PropHeaderName7__
     */
    public void setRsv200PropHeaderName7(String rsv200PropHeaderName7) {
        rsv200PropHeaderName7__ = rsv200PropHeaderName7;
    }

    /**
     * <p>rsv200Biko を取得します。
     * @return rsv200Biko
     */
    public String getRsv200Biko() {
        return rsv200Biko__;
    }

    /**
     * <p>rsv200Biko をセットします。
     * @param rsv200Biko rsv200Biko
     */
    public void setRsv200Biko(String rsv200Biko) {
        rsv200Biko__ = rsv200Biko;
    }

    /**
     * <p>rsv200Prop1Value__ を取得します。
     * @return rsv200Prop1Value
     */
    public String getRsv200Prop1Value() {
        return rsv200Prop1Value__;
    }

    /**
     * <p>rsv200Prop1Value__ をセットします。
     * @param rsv200Prop1Value rsv200Prop1Value__
     */
    public void setRsv200Prop1Value(String rsv200Prop1Value) {
        rsv200Prop1Value__ = rsv200Prop1Value;
    }

    /**
     * <p>rsv200Prop2Value__ を取得します。
     * @return rsv200Prop2Value
     */
    public String getRsv200Prop2Value() {
        return rsv200Prop2Value__;
    }

    /**
     * <p>rsv200Prop2Value__ をセットします。
     * @param rsv200Prop2Value rsv200Prop2Value__
     */
    public void setRsv200Prop2Value(String rsv200Prop2Value) {
        rsv200Prop2Value__ = rsv200Prop2Value;
    }

    /**
     * <p>rsv200Prop3Value__ を取得します。
     * @return rsv200Prop3Value
     */
    public String getRsv200Prop3Value() {
        return rsv200Prop3Value__;
    }

    /**
     * <p>rsv200Prop3Value__ をセットします。
     * @param rsv200Prop3Value rsv200Prop3Value__
     */
    public void setRsv200Prop3Value(String rsv200Prop3Value) {
        rsv200Prop3Value__ = rsv200Prop3Value;
    }

    /**
     * <p>rsv200Prop4Value__ を取得します。
     * @return rsv200Prop4Value
     */
    public String getRsv200Prop4Value() {
        return rsv200Prop4Value__;
    }

    /**
     * <p>rsv200Prop4Value__ をセットします。
     * @param rsv200Prop4Value rsv200Prop4Value__
     */
    public void setRsv200Prop4Value(String rsv200Prop4Value) {
        rsv200Prop4Value__ = rsv200Prop4Value;
    }

    /**
     * <p>rsv200Prop5Value__ を取得します。
     * @return rsv200Prop5Value
     */
    public String getRsv200Prop5Value() {
        return rsv200Prop5Value__;
    }

    /**
     * <p>rsv200Prop5Value__ をセットします。
     * @param rsv200Prop5Value rsv200Prop5Value__
     */
    public void setRsv200Prop5Value(String rsv200Prop5Value) {
        rsv200Prop5Value__ = rsv200Prop5Value;
    }

    /**
     * <p>rsv200Prop6Value__ を取得します。
     * @return rsv200Prop6Value
     */
    public String getRsv200Prop6Value() {
        return rsv200Prop6Value__;
    }

    /**
     * <p>rsv200Prop6Value__ をセットします。
     * @param rsv200Prop6Value rsv200Prop6Value__
     */
    public void setRsv200Prop6Value(String rsv200Prop6Value) {
        rsv200Prop6Value__ = rsv200Prop6Value;
    }

    /**
     * <p>rsv200Prop7Value__ を取得します。
     * @return rsv200Prop7Value
     */
    public String getRsv200Prop7Value() {
        return rsv200Prop7Value__;
    }

    /**
     * <p>rsv200Prop7Value__ をセットします。
     * @param rsv200Prop7Value rsv200Prop7Value__
     */
    public void setRsv200Prop7Value(String rsv200Prop7Value) {
        rsv200Prop7Value__ = rsv200Prop7Value;
    }
    /**
     * <p>rsv200SisetuList__ を取得します。
     * @return rsv200SisetuList
     */
    public ArrayList<RsvSisDataModel> getRsv200SisetuList() {
        return rsv200SisetuList__;
    }

    /**
     * <p>rsv200SisetuList__ をセットします。
     * @param rsv200SisetuList rsv200SisetuList__
     */
    public void setRsv200SisetuList(ArrayList<RsvSisDataModel> rsv200SisetuList) {
        rsv200SisetuList__ = rsv200SisetuList;
    }

    /**
     * <p>rsv200Prop1Check__ を取得します。
     * @return rsv200Prop1Check
     */
    public int getRsv200Prop1Check() {
        return rsv200Prop1Check__;
    }

    /**
     * <p>rsv200Prop1Check__ をセットします。
     * @param rsv200Prop1Check rsv200Prop1Check__
     */
    public void setRsv200Prop1Check(int rsv200Prop1Check) {
        rsv200Prop1Check__ = rsv200Prop1Check;
    }

    /**
     * <p>rsv200Prop2Check__ を取得します。
     * @return rsv200Prop2Check
     */
    public int getRsv200Prop2Check() {
        return rsv200Prop2Check__;
    }

    /**
     * <p>rsv200Prop2Check__ をセットします。
     * @param rsv200Prop2Check rsv200Prop2Check__
     */
    public void setRsv200Prop2Check(int rsv200Prop2Check) {
        rsv200Prop2Check__ = rsv200Prop2Check;
    }

    /**
     * <p>rsv200Prop3Check__ を取得します。
     * @return rsv200Prop3Check
     */
    public int getRsv200Prop3Check() {
        return rsv200Prop3Check__;
    }

    /**
     * <p>rsv200Prop3Check__ をセットします。
     * @param rsv200Prop3Check rsv200Prop3Check__
     */
    public void setRsv200Prop3Check(int rsv200Prop3Check) {
        rsv200Prop3Check__ = rsv200Prop3Check;
    }

    /**
     * <p>rsv200Prop4Check__ を取得します。
     * @return rsv200Prop4Check
     */
    public int getRsv200Prop4Check() {
        return rsv200Prop4Check__;
    }

    /**
     * <p>rsv200Prop4Check__ をセットします。
     * @param rsv200Prop4Check rsv200Prop4Check__
     */
    public void setRsv200Prop4Check(int rsv200Prop4Check) {
        rsv200Prop4Check__ = rsv200Prop4Check;
    }

    /**
     * <p>rsv200Prop5Check__ を取得します。
     * @return rsv200Prop5Check
     */
    public int getRsv200Prop5Check() {
        return rsv200Prop5Check__;
    }

    /**
     * <p>rsv200Prop5Check__ をセットします。
     * @param rsv200Prop5Check rsv200Prop5Check__
     */
    public void setRsv200Prop5Check(int rsv200Prop5Check) {
        rsv200Prop5Check__ = rsv200Prop5Check;
    }

    /**
     * <p>rsv200Prop6Check__ を取得します。
     * @return rsv200Prop6Check
     */
    public int getRsv200Prop6Check() {
        return rsv200Prop6Check__;
    }

    /**
     * <p>rsv200Prop6Check__ をセットします。
     * @param rsv200Prop6Check rsv200Prop6Check__
     */
    public void setRsv200Prop6Check(int rsv200Prop6Check) {
        rsv200Prop6Check__ = rsv200Prop6Check;
    }

    /**
     * <p>rsv200Prop7Check__ を取得します。
     * @return rsv200Prop7Check
     */
    public int getRsv200Prop7Check() {
        return rsv200Prop7Check__;
    }

    /**
     * <p>rsv200Prop7Check__ をセットします。
     * @param rsv200Prop7Check rsv200Prop7Check__
     */
    public void setRsv200Prop7Check(int rsv200Prop7Check) {
        rsv200Prop7Check__ = rsv200Prop7Check;
    }
    /**
     * <p>rsv200BikoCheck__ を取得します。
     * @return rsv200BikoCheck
     */
    public int getRsv200BikoCheck() {
        return rsv200BikoCheck__;
    }

    /**
     * <p>rsv200BikoCheck__ をセットします。
     * @param rsv200BikoCheck rsv200BikoCheck__
     */
    public void setRsv200BikoCheck(int rsv200BikoCheck) {
        rsv200BikoCheck__ = rsv200BikoCheck;
    }
    /**
     * <p>rsv200TargetSisetu__ を取得します。
     * @return rsv200TargetSisetu
     */
    public String[] getRsv200TargetSisetu() {
        return rsv200TargetSisetu__;
    }

    /**
     * <p>rsv200TargetSisetu__ をセットします。
     * @param rsv200TargetSisetu rsv200TargetSisetu__
     */
    public void setRsv200TargetSisetu(String[] rsv200TargetSisetu) {
        rsv200TargetSisetu__ = rsv200TargetSisetu;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        //適用項目数チェック
        int itemCnt =
            rsv200Prop1Check__
            + rsv200Prop2Check__
            + rsv200Prop3Check__
            + rsv200Prop4Check__
            + rsv200Prop5Check__
            + rsv200Prop6Check__
            + rsv200Prop7Check__
            + rsv200BikoCheck__;

        if (itemCnt == 0) {
            msg =
                new ActionMessage("error.select.required.text",
                        gsMsg.getMessage(req, "reserve.rsv200kn.2"));
            StrutsUtil.addMessage(errors, msg, "item");
        }

        RsvSisGrpDao grpDao = new RsvSisGrpDao(con);
        Rsv080Model grpMdl = grpDao.getGrpBaseData(getRsv080EditGrpSid());
        int rskSid = grpMdl.getRskSid();

        //可変項目のチェック対象を取得
        String prop1Name = null;
        String prop2Name = null;
        String prop3Name = null;
        String prop4Name = null;
        String prop5Name = null;
        String prop6Name = null;
        String prop7Name = null;

        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                prop1Name = gsMsg.getMessage(req, "reserve.128");
                prop2Name = gsMsg.getMessage(req, "reserve.132");
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                prop1Name = gsMsg.getMessage(req, "reserve.130");
                prop3Name = gsMsg.getMessage(req, "reserve.133");
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                prop1Name = gsMsg.getMessage(req, "reserve.129");
                prop2Name = gsMsg.getMessage(req, "reserve.132");
                prop4Name = gsMsg.getMessage(req, "reserve.134");
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                prop1Name = gsMsg.getMessage(req, "reserve.131");
                prop3Name = gsMsg.getMessage(req, "reserve.133");
                prop5Name = GSConstReserve.RSK_TEXT_ISBN;
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            default:
                break;
        }


        //可変項目4
        if (!StringUtil.isNullZeroString(rsv200Prop4Value__)
                && rsv200Prop4Check__ == GSConstReserve.PROP_CHECK_YES) {
            //可変項目4 桁数チェック
            if (rsv200Prop4Value__.length() > GSConstReserve.MAX_LENGTH_PROP4) {
                msg =
                    new ActionMessage("error.input.length.text",
                            prop5Name, String.valueOf(GSConstReserve.MAX_LENGTH_PROP4));
                StrutsUtil.addMessage(errors, msg, "rsv200Prop4Value");
            //可変項目4 スペースのみチェック
            } else if (ValidateUtil.isSpace(rsv200Prop4Value__)) {
                msg = new ActionMessage("error.input.spase.only", prop4Name);
                StrutsUtil.addMessage(errors, msg, "rsv200Prop4Value");
            //可変項目4 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(rsv200Prop4Value__)) {
                msg = new ActionMessage("error.input.spase.start", prop4Name);
                StrutsUtil.addMessage(errors, msg, "rsv200Prop4Value");
            //可変項目4 JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(rsv200Prop4Value__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv200Prop4Value__);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            prop4Name,
                            nstr);
                StrutsUtil.addMessage(errors, msg, "rsv200Prop4Value");
            }
        }

        //可変項目5
        if (!StringUtil.isNullZeroString(rsv200Prop5Value__)
                && rsv200Prop5Check__ == GSConstReserve.PROP_CHECK_YES) {
            //可変項目5 桁数チェック
            if (rsv200Prop5Value__.length() > GSConstReserve.MAX_LENGTH_PROP5) {
                msg =
                    new ActionMessage("error.input.length.text",
                            prop5Name, String.valueOf(GSConstReserve.MAX_LENGTH_PROP5));
                StrutsUtil.addMessage(errors, msg, "rsv200Prop5Value");
            //可変項目5 スペースのみチェック
            } else if (ValidateUtil.isSpace(rsv200Prop5Value__)) {
                msg = new ActionMessage("error.input.spase.only", prop5Name);
                StrutsUtil.addMessage(errors, msg, "rsv200Prop5Value");
            //可変項目5 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(rsv200Prop5Value__)) {
                msg = new ActionMessage("error.input.spase.start", prop5Name);
                StrutsUtil.addMessage(errors, msg, "rsv200Prop5Value");
            //半角英数字とハイフンチェック
            } else if (!ValidateUtil.isAlpOrNumberOrHaifun(rsv200Prop5Value__)) {
                    msg =
                        new ActionMessage("error.format.isbn", prop5Name);
                    StrutsUtil.addMessage(errors, msg, "rsv200Prop5Value");
            //可変項目5 JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(rsv200Prop5Value__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv200Prop5Value__);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            prop5Name,
                            nstr);
                StrutsUtil.addMessage(errors, msg, "rsv200Prop5Value");
            }
        }

        //可変項目1
        if (!StringUtil.isNullZeroString(rsv200Prop1Value__)
                && rsv200Prop1Check__ == GSConstReserve.PROP_CHECK_YES) {
            //可変項目1 文字数
            if (rsv200Prop1Value__.length() > GSConstReserve.MAX_LENGTH_PROP1) {
                msg =
                    new ActionMessage("error.input.length.text",
                            prop1Name,
                                    String.valueOf(GSConstReserve.MAX_LENGTH_PROP1));
                StrutsUtil.addMessage(errors, msg, "rsv200Prop1Value");
            //可変項目1 半角数字
            } else if (!ValidateUtil.isNumber(rsv200Prop1Value__)) {
                msg = new ActionMessage("error.input.length.number2",
                        prop1Name,
                        String.valueOf(GSConstReserve.MAX_LENGTH_PROP1));
                StrutsUtil.addMessage(errors, msg, "rsv200Prop1Value");
            }
        }

        //可変項目2
        if (StringUtil.isNullZeroString(rsv200Prop2Value__)
                && rsv200Prop2Check__ == GSConstReserve.PROP_CHECK_YES) {
            msg =
                new ActionMessage("error.select.required.text", prop2Name);
            StrutsUtil.addMessage(errors, msg, "rsv200Prop2Value");
        }

        //可変項目3
        if (StringUtil.isNullZeroString(rsv200Prop3Value__)
                && rsv200Prop3Check__ == GSConstReserve.PROP_CHECK_YES) {
            msg =
                new ActionMessage("error.select.required.text", prop3Name);
            StrutsUtil.addMessage(errors, msg, "rsv200Prop3Value");
        }

        //可変項目7
        if (StringUtil.isNullZeroString(rsv200Prop7Value__)
                && rsv200Prop7Check__ == GSConstReserve.PROP_CHECK_YES) {
            msg =
                new ActionMessage("error.select.required.text", prop7Name);
            StrutsUtil.addMessage(errors, msg, "rsv200Prop7Value");
        }

        //可変項目6
        if (!StringUtil.isNullZeroString(rsv200Prop6Value__)
                && rsv200Prop6Check__ == GSConstReserve.PROP_CHECK_YES) {
            //可変項目6 文字数
            if (rsv200Prop6Value__.length() > GSConstReserve.MAX_LENGTH_PROP6) {
                msg =
                    new ActionMessage("error.input.length.text",
                            prop6Name,
                                    String.valueOf(GSConstReserve.MAX_LENGTH_PROP6));
                StrutsUtil.addMessage(errors, msg, "rsv200Prop6Value");
            //可変項目6 半角数字
            } else if (!ValidateUtil.isNumber(rsv200Prop6Value__)) {
                msg = new ActionMessage("error.input.length.number2",
                        prop6Name,
                        String.valueOf(GSConstReserve.MAX_LENGTH_PROP6));
                StrutsUtil.addMessage(errors, msg, "rsv200Prop6Value");
            }
        }

        //備考
        if (!StringUtil.isNullZeroString(rsv200Biko__)
                && rsv200BikoCheck__ == GSConstReserve.PROP_CHECK_YES) {
            //備考 桁数チェック
            if (rsv200Biko__.length() > GSConstReserve.MAX_LENGTH_BIKO) {
                msg = new ActionMessage("error.input.length.textarea",
                        gsMsg.getMessage(req, "cmn.memo"),
                            String.valueOf(GSConstReserve.MAX_LENGTH_BIKO));
                StrutsUtil.addMessage(errors, msg, "rsv200Biko");
            }
            //備考 スペース・改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(rsv200Biko__)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                        gsMsg.getMessage(req, "cmn.memo"));
                StrutsUtil.addMessage(errors, msg, "rsv200Biko");
            }
            //備考 JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(rsv200Biko__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(rsv200Biko__);
                msg = new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage(req, "cmn.memo"),
                        nstr);
                StrutsUtil.addMessage(errors, msg, "rsv200Biko__");
            }
        }

        //適用施設数チェック
        if (rsv200TargetSisetu__ == null || rsv200TargetSisetu__.length == 0) {
            msg =
                new ActionMessage("error.select.required.text",
                        gsMsg.getMessage(req, "reserve.rsv200kn.3"));
            StrutsUtil.addMessage(errors, msg, "sisetu");
        }

        return errors;
    }
}
package jp.groupsession.v2.ip;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.ipk050.IpAddressCheck;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.IpkNetModel;
import jp.groupsession.v2.ip.model.ValidateCheckModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] IP管理で行う入力チェック機能(CSV用)を実装したクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkValidateCsv {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkValidateCsv.class);

    /** epefix IP管理*/
    private static final String IPK_E_IPKANRI__ = "ipkanri";

    /**
     * <br>[機  能] ネットワーク名の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param netName ネットワーク名
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvNetName(
        ActionErrors errors,
        RequestModel reqMdl,
        String netName,
        long num) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textNetName = gsMsg.getMessage("ipk.1");
        String textGyo = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        log__.debug("netName のチェックを行います。");
        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = textNetName + ".";
        String strField = "netName";

        if (StringUtil.isNullZeroString(netName)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                textGyo + textNetName);
            StrutsUtil.addMessage(
                errors, msg, eprefix + fieldfix + strField + num + "minyuryoku");
            return errors;
        }

        if (ValidateUtil.isSpace(netName)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", textGyo + textNetName);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "spaceOnly");
        }

        if (ValidateUtil.isSpaceStart(netName)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", textGyo + textNetName);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "sentouSpace");
        }

        if (ValidateUtil.isTab(netName)) {
            //タブ文字が含まれている
            msg = new ActionMessage("error.input.tab.text", textGyo + textNetName);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "tabu");
        }

        if (netName.length() > IpkConst.MAX_LENGTH_NAME) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", textGyo + textNetName,
                    IpkConst.MAX_LENGTH_NAME);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "length");
        }

        if (!GSValidateUtil.isGsJapaneaseString(netName)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(netName);
            msg = new ActionMessage("error.input.njapan.text",
                    textGyo + textNetName, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "jis2");
        }
        return errors;
    }

    /**
     * <br>[機  能] ネットワークアドレスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param netad ネットワークアドレス
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvNetad(
        ActionErrors errors,
        RequestModel reqMdl,
        String netad,
        long num) {

        log__.debug("netName のチェックを行います。");

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textNetAd = gsMsg.getMessage("ipk.2");
        String textGyo = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = textNetAd + ".";
        String strField = "netad";

        if (StringUtil.isNullZeroString(netad)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                textGyo + textNetAd);
            StrutsUtil.addMessage(
                errors, msg, eprefix + fieldfix + strField + num + "minyuryoku");
            return errors;
        }

        if (ValidateUtil.isSpace(netad)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", textGyo + textNetAd);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "spaceOnly");
        }

        if (ValidateUtil.isSpaceStart(netad)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", textGyo + textNetAd);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "sentouSpace");
        }

        //ネットワークアドレスを分割してチェックする。
        String[] netadAry = netad.replaceAll("\\.", ",").split(",");
        if (netadAry.length != 4) {
            //フォーマットチェック
            msg = new ActionMessage("error.input.format.text", textGyo + textNetAd);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "format");
        }

        //範囲チェック
        for (String networkAddress : netadAry) {
            if (NullDefault.getInt(networkAddress, 256) > 255
                    || NullDefault.getInt(networkAddress, -1) < 0) {
                msg = new ActionMessage("error.input.addhani.text",
                        textGyo + textNetAd, 0, 255);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField + num + "hani");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] サブネットマスクの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param subnet サブネットマスク
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvNetMask(
        ActionErrors errors,
        RequestModel reqMdl,
        String subnet,
        long num) {

        GsMessage gsMsg = new GsMessage();
        String textSabnet = gsMsg.getMessage("ipk.3");
        String textGyo = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        log__.debug("netSabnet のチェックを行います。");
        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = textSabnet + ".";
        String strField = "subnet";

        if (StringUtil.isNullZeroString(subnet)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                textGyo + textSabnet);
            StrutsUtil.addMessage(
                errors, msg, eprefix + fieldfix + strField + num + "minyuryoku");
            return errors;
        }

        if (ValidateUtil.isSpace(subnet)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", textGyo + textSabnet);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "spaceOnly");
        }

        if (ValidateUtil.isSpaceStart(subnet)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", textGyo + textSabnet);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "sentouSpace");
        }

        //サブネットマスクを分割してチェックする。
        String[] subnetAry = subnet.replaceAll("\\.", ",").split(",");
        if (subnetAry.length != 4) {
            //フォーマットチェック
            msg = new ActionMessage("error.input.format.text", textGyo + textSabnet);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "format");
            return errors;
        }
        //範囲チェック
        for (String subnetMask : subnetAry) {
            if (NullDefault.getInt(subnetMask, 256) > 255
                    || NullDefault.getInt(subnetMask, -1) < 0) {
                msg = new ActionMessage("error.input.addhani.text",
                        textGyo + textSabnet, 0, 255);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField + num + "hani");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] マシン名の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param iadName マシン名
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvIadName(
        ActionErrors errors,
        RequestModel reqMdl,
        String iadName,
        long num) {

        GsMessage gsMsg = new GsMessage();
        String textMachine = gsMsg.getMessage("ipk.7");
        String textGyo = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        log__.debug("iadNameのチェックを行います。");
        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = textMachine + ".";
        String strField = "iadName";

        if (StringUtil.isNullZeroString(iadName)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                textGyo + textMachine);
            StrutsUtil.addMessage(
                errors, msg, eprefix + fieldfix + strField + num + "minyuryoku");
            return errors;
        }

        if (ValidateUtil.isSpace(iadName)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", textGyo + textMachine);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "spaceOnly");
        }

        if (ValidateUtil.isSpaceStart(iadName)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", textGyo + textMachine);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "sentouSpace");
        }

        if (ValidateUtil.isTab(iadName)) {
            //タブ文字が含まれている
            msg = new ActionMessage("error.input.tab.text", textGyo + textMachine);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "tabu");
        }

        if (iadName.length() > IpkConst.MAX_LENGTH_MACHINE) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", textGyo + textMachine,
                    IpkConst.MAX_LENGTH_MACHINE);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "length");
        }

        if (!GSValidateUtil.isGsJapaneaseString(iadName)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(iadName);
            msg = new ActionMessage("error.input.njapan.text",
                    textGyo + textMachine, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "jis2");
        }
        return errors;
    }

    /**
     * <br>[機  能] IPアドレスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param ipad IPアドレス
     * @param num 行数
     * @param con Connection
     * @param model ValidateCheckModel
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateCsvIpad(
        ActionErrors errors, RequestModel reqMdl, String ipad, long num, Connection con,
        ValidateCheckModel model)
    throws SQLException {

        log__.debug("ipadのチェックを行います。");

        GsMessage gsMsg = new GsMessage();
        String textIpAd = gsMsg.getMessage("ipk.6");
        String textNetAd = gsMsg.getMessage("ipk.2");
        String textGyo = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = textIpAd + ".";
        String strField = "ipad";

        if (StringUtil.isNullZeroString(ipad)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                textGyo + textIpAd);
            StrutsUtil.addMessage(
                errors, msg, eprefix + fieldfix + strField + num + "minyuryoku");
            return errors;
        }

        if (ValidateUtil.isSpace(ipad)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", textGyo + textIpAd);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "spaceOnly");
        }

        if (ValidateUtil.isSpaceStart(ipad)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", textGyo + textIpAd);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "sentouSpace");
        }

        IpkNetDao dao = new IpkNetDao(con);
        String dbNetad = "";
        try {
            ArrayList<IpkNetModel> list = dao.selectNetwork(model.getNetSid());
            for (IpkNetModel mdl : list) {
                dbNetad = mdl.getNetNetad();
            }
        } catch (SQLException e) {
            log__.error(e);
        }
        int textNum = IpAddressCheck.checkSubNet(model.getSubnetMask());
        //ネットワークアドレスを０付きフォーマットに変更する。
        String[] netAry = dbNetad.replaceAll("\\.", ",").split(",");


        //IPアドレスのネットワークアドレス部を変更する。
        String[] ipadAry = ipad.replaceAll("\\.", ",").split(",");
        if (ipadAry.length != 4
        || !ValidateUtil.isNumber(ipadAry[0])
        || !ValidateUtil.isNumber(ipadAry[1])
        || !ValidateUtil.isNumber(ipadAry[2])
        || !ValidateUtil.isNumber(ipadAry[3])) {
            //フォーマットチェック
            msg = new ActionMessage("error.input.format.text", textGyo + textNetAd);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "format");
            return errors;
        }

        String netadCheck = "";
        String ipadCheck = "";
        //ネットワークアドレス部不正チェック
        if (textNum == 2) {
            netadCheck = netAry[0];
            ipadCheck = ipadAry[0];
            if (!netadCheck.equals(ipadCheck)) {
                msg = new ActionMessage(
                        "error.input.format.file",
                        gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)}),
                        textNetAd);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + fieldfix + strField + num + "netAdd");
            }
        } else if (textNum == 3) {
            netadCheck = netAry[0] + netAry[1];
            ipadCheck = ipadAry[0] + ipadAry[1];
            if (!netadCheck.equals(ipadCheck)) {
                msg = new ActionMessage(
                        "error.input.format.file",
                        gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)}),
                        textNetAd);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + fieldfix + strField + num + "netAdd");
            }

        } else if (textNum == 4) {
            netadCheck = netAry[0] + netAry[1] + netAry[2];
            ipadCheck = ipadAry[0] + ipadAry[1] + ipadAry[2];
            if (!netadCheck.equals(ipadCheck)) {
                msg = new ActionMessage(
                        "error.input.format.file",
                        gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)}),
                        textNetAd);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + fieldfix + strField + num + "netAdd");
            }
        }
        String strIpad = "";

        strIpad = StringUtil.toDecFormat(ipadAry[0], IpkConst.IPAD_FORMAT_PART)
            + StringUtil.toDecFormat(ipadAry[1], IpkConst.IPAD_FORMAT_PART)
            + StringUtil.toDecFormat(ipadAry[2], IpkConst.IPAD_FORMAT_PART)
            + StringUtil.toDecFormat(ipadAry[3], IpkConst.IPAD_FORMAT_PART);
        long longIpad = Long.parseLong(strIpad);
        IpkAddDao ipkAddDao = new IpkAddDao(con);
        IpkAddModel ipkAddModel = new IpkAddModel();
        ipkAddModel.setNetSid(model.getNetSid());
        ipkAddModel.setIadIpad(longIpad);
        ipkAddModel.setCmd("import");
        int cnt = ipkAddDao.countIpad(ipkAddModel);

        //追加モード時
        if (model.getMode().equals(IpkConst.IMPORT_MODE_TUIKA)) {
            //範囲チェック
            if (!IpAddressCheck.ipadCheck(
                    model.getIpAddress(), model.getNetworkAddress(), model.getSubnetMask())) {
                msg = new ActionMessage("error.input.notvalidate.data", textGyo + textIpAd);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
                return errors;
            }
            //存在チェック
            if (!IpAddressCheck.ipadExistCheck(cnt)) {
                msg = new ActionMessage("error.input.timecard.exist",
                        textGyo + textIpAd);
                StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "exist");
            }
        }

        //CSVファイル内に同じIPアドレスが無いかをチェックする。
        if (model.getIpadListCsv() != null && model.getIpadListCsv().size() != 0) {
            for (String ipadCsv : model.getIpadListCsv()) {
                if (ipadCsv.equals(ipad)) {
                    msg = new ActionMessage("error.import.exist", textGyo + textIpAd);
                    StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField + num + "existCsv");
                }
            }
        }

        //IPアドレスを4分割してチェックする。
        for (String ipAddress : ipadAry) {
            if (NullDefault.getInt(ipAddress, 256) > 255
                    || NullDefault.getInt(ipAddress, -1) < 0) {
                msg = new ActionMessage("error.input.addhani.text",
                        textGyo + textIpAd, 0, 255);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField + num + "hani");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 使用状況の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param usedKbn 使用状況
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvUsekbn(
        ActionErrors errors,
        RequestModel reqMdl,
        String usedKbn,
        long num) {

        log__.debug("usedKbnのチェックを行います。");

        GsMessage gsMsg = new GsMessage();
        String textUsedKbn = gsMsg.getMessage("ipk.11");
        String textSiyou = gsMsg.getMessage("cmn.use");
        String textMisiyou = gsMsg.getMessage("cmn.unused");
        String textGyo = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = textUsedKbn + ".";
        String strField = "usedKbn";

        if (StringUtil.isNullZeroString(usedKbn)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                 textGyo + textUsedKbn);
            StrutsUtil.addMessage(
                errors, msg, eprefix + fieldfix + strField + num + "minyuryoku");
            return errors;
        }

        if (ValidateUtil.isSpace(usedKbn)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", textGyo + textUsedKbn);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "spaceOnly");
        }

        if (ValidateUtil.isSpaceStart(usedKbn)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", textGyo + textUsedKbn);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "sentouSpace");
        }

        if (!usedKbn.equals(textSiyou) && !usedKbn.equals(textMisiyou)) {
            //'使用','未使用'以外の文字列はエラー
            msg = new ActionMessage("error.input.notvalidate.data",
                    textGyo + textUsedKbn);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "husei");
        }
        return errors;
    }

    /**
     * <br>[機  能] 使用状況の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param iadMsg IPアドレスコメント
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvIadMsg(
        ActionErrors errors,
        RequestModel reqMdl,
        String iadMsg,
        long num) {

        log__.debug("iadMsgのチェックを行います。");

        GsMessage gsMsg = new GsMessage();
        String textMsg = gsMsg.getMessage("cmn.comment");

        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = textMsg + ".";
        String strField = "iadMsg";

        if (iadMsg == null) {
            return errors;
        }

        if (iadMsg.length() > IpkConst.MAX_LENGTH_MSG) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.textarea", textMsg,
                    IpkConst.MAX_LENGTH_MSG);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "length");

        }
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(iadMsg)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(iadMsg);
            msg = new ActionMessage("error.input.njapan.text", textMsg, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "jis2");
        }

        if (!iadMsg.equals("")) {
            //スペース、改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(iadMsg)) {
                msg = new ActionMessage("error.input.spase.cl.only", textMsg);
                StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField + num + "space");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 資産管理番号の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param iadPrtMngNum 資産管理番号
     * @param num 行数
     * @return ActionErrors
     */
    public static ActionErrors validateCsvIadPrtMngNum(
        ActionErrors errors,
        RequestModel reqMdl,
        String iadPrtMngNum,
        long num) {

        log__.debug("iadPrtMngNumのチェックを行います。");

        GsMessage gsMsg = new GsMessage();
        String textSisanKanriBangou = gsMsg.getMessage("cmn.asset.register.num");
        String textGyo = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});


        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = textSisanKanriBangou + ".";
        String strField = "iadPrtMngNum";

        if (iadPrtMngNum.length() > IpkConst.MAX_LENGTH_MACHINE) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    textGyo + textSisanKanriBangou,
                    IpkConst.MAX_LENGTH_PRTMNG_NUM);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "length");
        }

        if (!GSValidateUtil.isGsJapaneaseString(iadPrtMngNum)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(iadPrtMngNum);
            msg = new ActionMessage("error.input.njapan.text",
                    textGyo + textSisanKanriBangou, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField + num + "jis2");
        }
        return errors;
    }
}
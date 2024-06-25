package jp.groupsession.v2.ip.ipk040;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.IpkValidate;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.IpkBinModel;
import jp.groupsession.v2.ip.model.ValidateCheckModel;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] IP管理 IPアドレス一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk040Form extends AbstractGsForm {

    /** IPアドレス一覧リスト */
    private ArrayList<IpkAddModel> ipkAddList__;
    /** ネットワークアドレス */
    private String netNetad__;
    /** サブネットマスク */
    private String netSabnet__;
    /** ネットワーク名 */
    private String netName__;
    /** コメント */
    private String netMsg__;
    /** ページ遷移コマンド */
    private String CMD__;
    /** ネットSID */
    private String netSid__;
    /** IPアドレスSID */
    private String iadSid__;
    /** IPアドレス */
    private String iadAdd__;
    /** マシン名 */
    private String iadMachineName__;
    /** 使用状況 */
    private String iadUse__ = IpkConst.USEDKBN_SIYOU;
    /** コメント */
    private String iadMsg__;
    /** 使用者リスト */
    private ArrayList<String> iadAdmList__;
    /** 削除チェックボックス */
    private String[] deleteCheck__;
    /** IPアドレス登録台数 使用中 */
    private String iadCountUse__;
    /** 全IPアドレス登録台数 */
    private String iadCount__;
    /** IPアドレス登録台数 未使用 */
    private String iadCountNotUse__;
    /** 現在ページ */
    private String iadPageNum__ = IpkConst.IPAD_PAGE_NUM;
    /** ページ数1 */
    private String iadPage1__ = IpkConst.IPAD_PAGE_NUM;
    /** ページ数2 */
    private String iadPage2__ = IpkConst.IPAD_PAGE_NUM;
    /** ページラベル */
    private ArrayList<LabelValueBean> iadPageLabel__;
    /** 1ページ表示最大件数 */
    private String iadLimit__ = IpkConst.IPAD_LIMIT_ALL;
    /** ページコンボの値 */
    private String value__;
    /** ページコンボ最大ページ数 */
    private String maxPageNum__;
    /** 使用状況コンボ */
    private String usedKbn__ = IpkConst.USEDKBN_ALL;
    /** ソートキー */
    private String sortKey__ = "0";
    /** オーダーキー */
    private String orderKey__ = "0";
    /** IPアドレステキスト表示数フラグ */
    private String textNum__ = IpkConst.TEXT_NUMFLG1;
    /** IPアドレステキスト1 */
    private String iadAddText1__;
    /** IPアドレステキスト2 */
    private String iadAddText2__;
    /** IPアドレステキスト3 */
    private String iadAddText3__;
    /** IPアドレステキスト4 */
    private String iadAddText4__;
    /** IPアドレス画面ネットワーク管理者 */
    private boolean iadNetAdmFlg__;
    /** IPアドレス一覧表示件数 */
    private String maxCount__;
    /** 削除チェックボックスの全チェック */
    private String deleteAllCheck__;
    /** 削除チェックボックス表示フラグ */
    private boolean checkBoxDspFlg__;
    /** 変更ボタン表示フラグ */
    private String dspFlg__;
    /** バイナリSID */
    private String binSid__;
    /** ファイルコンボ */
    private List < LabelValueBean > ipk040FileLabelList__ = null;
    /** テンポラリディレクトリパス */
    private String tempDir__;
    /** ネットワークの添付ファイル情報リスト 公開*/
    private ArrayList<IpkBinModel> koukaiBinFileInfList__ = null;
    /** ネットワークの添付ファイル情報リスト 非公開*/
    private ArrayList<IpkBinModel> hikoukaiBinFileInfList__ = null;
    /** IPアドレスの添付ファイル情報リスト 公開*/
    private ArrayList<IpkBinModel> ipadKoukaiBinFileInfList__ = null;
    /** IPアドレスの添付ファイル情報リスト 非公開*/
    private ArrayList<IpkBinModel> ipadHikoukaiBinFileInfList__ = null;
    /** ネットワークの添付ファイル情報 公開ならば1、非公開ならば0 */
    private String tempDsp__ = "0";
    /** ネットワークの添付ファイル情報 存在する：true、存在しない：false */
    private boolean tempExist__ = false;
    /** キーワード */
    private String ipk070KeyWord__ = null;
    /** ネットワーク情報表示フラグ */
    private String netInfDspFlg__ = IpkConst.IPK_NETINF_HIHYOUJI;
    /** 詳細ボタンページ遷移モード IPアドレス一覧画面:"0" 詳細検索画面:"1" */
    private String returnCmd__ = "0";

    /**
     * <p>ipk070KeyWord を取得します。
     * @return ipk070KeyWord
     */
    public String getIpk070KeyWord() {
        return ipk070KeyWord__;
    }

    /**
     * <p>ipk070KeyWord をセットします。
     * @param ipk070KeyWord ipk070KeyWord
     */
    public void setIpk070KeyWord(String ipk070KeyWord) {
        ipk070KeyWord__ = ipk070KeyWord;
    }

    /**
     * <p>tempDsp を取得します。
     * @return tempDsp
     */
    public String getTempDsp() {
        return tempDsp__;
    }

    /**
     * <p>tempDsp をセットします。
     * @param tempDsp tempDsp
     */
    public void setTempDsp(String tempDsp) {
        tempDsp__ = tempDsp;
    }

    /**
     * <p>hikoukaiBinFileInfList を取得します。
     * @return hikoukaiBinFileInfList
     */
    public ArrayList<IpkBinModel> getHikoukaiBinFileInfList() {
        return hikoukaiBinFileInfList__;
    }

    /**
     * <p>hikoukaiBinFileInfList をセットします。
     * @param hikoukaiBinFileInfList hikoukaiBinFileInfList
     */
    public void setHikoukaiBinFileInfList(
            ArrayList<IpkBinModel> hikoukaiBinFileInfList) {
        hikoukaiBinFileInfList__ = hikoukaiBinFileInfList;
    }

    /**
     * <p>koukaiBinFileInfList を取得します。
     * @return koukaiBinFileInfList
     */
    public ArrayList<IpkBinModel> getKoukaiBinFileInfList() {
        return koukaiBinFileInfList__;
    }

    /**
     * <p>koukaiBinFileInfList をセットします。
     * @param koukaiBinFileInfList koukaiBinFileInfList
     */
    public void setKoukaiBinFileInfList(ArrayList<IpkBinModel> koukaiBinFileInfList) {
        koukaiBinFileInfList__ = koukaiBinFileInfList;
    }

    /**
     * <p>deleteAllCheck を取得します。
     * @return deleteAllCheck
     */
    public String getDeleteAllCheck() {
        return deleteAllCheck__;
    }

    /**
     * <p>deleteAllCheck をセットします。
     * @param deleteAllCheck deleteAllCheck
     */
    public void setDeleteAllCheck(String deleteAllCheck) {
        deleteAllCheck__ = deleteAllCheck;
    }

    /**
     * <p>maxCount を取得します。
     * @return maxCount
     */
    public String getMaxCount() {
        return maxCount__;
    }

    /**
     * <p>maxCount をセットします。
     * @param maxCount maxCount
     */
    public void setMaxCount(String maxCount) {
        this.maxCount__ = maxCount;
    }


    /**
     * <p>iadNetAdmFlg を取得します。
     * @return iadNetAdmFlg
     */
    public boolean isIadNetAdmFlg() {
        return iadNetAdmFlg__;
    }

    /**
     * <p>iadNetAdmFlg をセットします。
     * @param iadNetAdmFlg iadNetAdmFlg
     */
    public void setIadNetAdmFlg(boolean iadNetAdmFlg) {
        iadNetAdmFlg__ = iadNetAdmFlg;
    }

    /**
     * <p>iadAddText1 を取得します。
     * @return iadAddText1
     */
    public String getIadAddText1() {
        return iadAddText1__;
    }

    /**
     * <p>iadAddText1 をセットします。
     * @param iadAddText1 iadAddText1
     */
    public void setIadAddText1(String iadAddText1) {
        iadAddText1__ = iadAddText1;
    }

    /**
     * <p>iadAddText2 を取得します。
     * @return iadAddText2
     */
    public String getIadAddText2() {
        return iadAddText2__;
    }

    /**
     * <p>iadAddText2 をセットします。
     * @param iadAddText2 iadAddText2
     */
    public void setIadAddText2(String iadAddText2) {
        iadAddText2__ = iadAddText2;
    }

    /**
     * <p>iadAddText3 を取得します。
     * @return iadAddText3
     */
    public String getIadAddText3() {
        return iadAddText3__;
    }

    /**
     * <p>iadAddText3 をセットします。
     * @param iadAddText3 iadAddText3
     */
    public void setIadAddText3(String iadAddText3) {
        iadAddText3__ = iadAddText3;
    }

    /**
     * <p>iadAddText4 を取得します。
     * @return iadAddText4
     */
    public String getIadAddText4() {
        return iadAddText4__;
    }

    /**
     * <p>iadAddText4 をセットします。
     * @param iadAddText4 iadAddText4
     */
    public void setIadAddText4(String iadAddText4) {
        iadAddText4__ = iadAddText4;
    }

    /**
     * <p>textNum を取得します。
     * @return textNum
     */
    public String getTextNum() {
        return textNum__;
    }

    /**
     * <p>textNum をセットします。
     * @param textNum textNum
     */
    public void setTextNum(String textNum) {
        textNum__ = textNum;
    }

    /**
     * <p>maxPageNum を取得します。
     * @return maxPageNum
     */
    public String getMaxPageNum() {
        return maxPageNum__;
    }

    /**
     * <p>maxPageNum をセットします。
     * @param maxPageNum maxPageNum
     */
    public void setMaxPageNum(String maxPageNum) {
        maxPageNum__ = maxPageNum;
    }

    /**
     * <p>iadPageNum を取得します。
     * @return iadPageNum
     */
    public String getIadPageNum() {
        return iadPageNum__;
    }

    /**
     * <p>iadPageNum をセットします。
     * @param iadPageNum iadPageNum
     */
    public void setIadPageNum(String iadPageNum) {
        iadPageNum__ = iadPageNum;
    }

    /**
     * <p>deleteCheck を取得します。
     * @return deleteCheck
     */
    public String[] getDeleteCheck() {
        return deleteCheck__;
    }

    /**
     * <p>deleteCheck をセットします。
     * @param deleteCheck deleteCheck
     */
    public void setDeleteCheck(String[] deleteCheck) {
        deleteCheck__ = deleteCheck;
    }

    /**
     * <p>iadAdmList を取得します。
     * @return iadAdmList
     */
    public ArrayList<String> getIadAdmList() {
        return iadAdmList__;
    }

    /**
     * <p>iadAdmList をセットします。
     * @param iadAdmList iadAdmList
     */
    public void setIadAdmList(ArrayList<String> iadAdmList) {
        iadAdmList__ = iadAdmList;
    }

    /**
     * <p>ipkAddList を取得します。
     * @return ipkAddList
     */
    public ArrayList<IpkAddModel> getIpkAddList() {
        return ipkAddList__;
    }

    /**
     * <p>ipkAddList をセットします。
     * @param ipkAddList ipkAddList
     */
    public void setIpkAddList(ArrayList<IpkAddModel> ipkAddList) {
        ipkAddList__ = ipkAddList;
    }

    /**
     * <p>netNetad を取得します。
     * @return netNetad
     */
    public String getNetNetad() {
        return netNetad__;
    }

    /**
     * <p>netNetad をセットします。
     * @param netNetad netNetad
     */
    public void setNetNetad(String netNetad) {
        netNetad__ = netNetad;
    }

    /**
     * <p>netSabnet を取得します。
     * @return netSabnet
     */
    public String getNetSabnet() {
        return netSabnet__;
    }

    /**
     * <p>netSabnet をセットします。
     * @param netSabnet netSabnet
     */
    public void setNetSabnet(String netSabnet) {
        netSabnet__ = netSabnet;
    }

    /**
     * <p>netMsg を取得します。
     * @return netMsg
     */
    public String getNetMsg() {
        return netMsg__;
    }

    /**
     * <p>netMsg をセットします。
     * @param netMsg netMsg
     */
    public void setNetMsg(String netMsg) {
        netMsg__ = netMsg;
    }

    /**
     * <p>netName を取得します。
     * @return netName
     */
    public String getNetName() {
        return netName__;
    }

    /**
     * <p>netName をセットします。
     * @param netName netName
     */
    public void setNetName(String netName) {
        netName__ = netName;
    }

    /**
     * <p>netSid を取得します。
     * @return netSid
     */
    public String getNetSid() {
        return netSid__;
    }

    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(String netSid) {
        netSid__ = netSid;
    }

    /**
     * <p>CMD を取得します。
     * @return CMD
     */
    public String getCMD() {
        return CMD__;
    }

    /**
     * <p>CMD をセットします。
     * @param CMD CMD
     */
    public void setCMD(String CMD) {
        CMD__ = CMD;
    }

    /**
     * <p>iadAdd を取得します。
     * @return iadAdd
     */
    public String getIadAdd() {
        return iadAdd__;
    }

    /**
     * <p>iadAdd をセットします。
     * @param iadAdd iadAdd
     */
    public void setIadAdd(String iadAdd) {
        iadAdd__ = iadAdd;
    }

    /**
     * <p>iadMachineName を取得します。
     * @return iadMachineName
     */
    public String getIadMachineName() {
        return iadMachineName__;
    }

    /**
     * <p>iadMachineName をセットします。
     * @param iadMachineName iadMachineName
     */
    public void setIadMachineName(String iadMachineName) {
        iadMachineName__ = iadMachineName;
    }

    /**
     * <p>iadMsg を取得します。
     * @return iadMsg
     */
    public String getIadMsg() {
        return iadMsg__;
    }

    /**
     * <p>iadMsg をセットします。
     * @param iadMsg iadMsg
     */
    public void setIadMsg(String iadMsg) {
        iadMsg__ = iadMsg;
    }

    /**
     * <p>iadUse を取得します。
     * @return iadUse
     */
    public String getIadUse() {
        return iadUse__;
    }

    /**
     * <p>iadUse をセットします。
     * @param iadUse iadUse
     */
    public void setIadUse(String iadUse) {
        iadUse__ = iadUse;
    }

    /**
     * <p>iadSid を取得します。
     * @return iadSid
     */
    public String getIadSid() {
        return iadSid__;
    }

    /**
     * <p>iadSid をセットします。
     * @param iadSid iadSid
     */
    public void setIadSid(String iadSid) {
        iadSid__ = iadSid;
    }

    /**
     * <p>iadCount を取得します。
     * @return iadCount
     */
    public String getIadCount() {
        return iadCount__;
    }

    /**
     * <p>iadCount をセットします。
     * @param iadCount iadCount
     */
    public void setIadCount(String iadCount) {
        iadCount__ = iadCount;
    }

    /**
     * <p>iadCountUse を取得します。
     * @return iadCountUse
     */
    public String getIadCountUse() {
        return iadCountUse__;
    }

    /**
     * <p>iadCountUse をセットします。
     * @param iadCountUse iadCountUse
     */
    public void setIadCountUse(String iadCountUse) {
        iadCountUse__ = iadCountUse;
    }

    /**
     * <p>iadCountNotUse を取得します。
     * @return iadCountNotUse
     */
    public String getIadCountNotUse() {
        return iadCountNotUse__;
    }

    /**
     * <p>iadCountNotUse をセットします。
     * @param iadCountNotUse iadCountNotUse
     */
    public void setIadCountNotUse(String iadCountNotUse) {
        iadCountNotUse__ = iadCountNotUse;
    }

    /**
     * <p>iadLimit を取得します。
     * @return iadLimit
     */
    public String getIadLimit() {
        return iadLimit__;
    }

    /**
     * <p>iadLimit をセットします。
     * @param iadLimit iadLimit
     */
    public void setIadLimit(String iadLimit) {
        iadLimit__ = iadLimit;
    }

    /**
     * <p>iadPage1 を取得します。
     * @return iadPage1
     */
    public String getIadPage1() {
        return iadPage1__;
    }

    /**
     * <p>iadPage1 をセットします。
     * @param iadPage1 iadPage1
     */
    public void setIadPage1(String iadPage1) {
        iadPage1__ = iadPage1;
    }

    /**
     * <p>iadPage2 を取得します。
     * @return iadPage2
     */
    public String getIadPage2() {
        return iadPage2__;
    }

    /**
     * <p>iadPage2 をセットします。
     * @param iadPage2 iadPage2
     */
    public void setIadPage2(String iadPage2) {
        iadPage2__ = iadPage2;
    }

    /**
     * <p>iadPageLabel を取得します。
     * @return iadPageLabel
     */
    public ArrayList<LabelValueBean> getIadPageLabel() {
        return iadPageLabel__;
    }

    /**
     * <p>iadPageLabel をセットします。
     * @param iadPageLabel iadPageLabel
     */
    public void setIadPageLabel(ArrayList<LabelValueBean> iadPageLabel) {
        iadPageLabel__ = iadPageLabel;
    }

    /**
     * <p>value を取得します。
     * @return value
     */
    public String getValue() {
        return value__;
    }

    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setValue(String value) {
        value__ = value;
    }

    /**
     * <p>usedKbn を取得します。
     * @return usedKbn
     */
    public String getUsedKbn() {
        return usedKbn__;
    }

    /**
     * <p>usedKbn をセットします。
     * @param usedKbn usedKbn
     */
    public void setUsedKbn(String usedKbn) {
        usedKbn__ = usedKbn;
    }

    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public String getOrderKey() {
        return orderKey__;
    }

    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(String orderKey) {
        orderKey__ = orderKey;
    }

    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public String getSortKey() {
        return sortKey__;
    }

    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(String sortKey) {
        sortKey__ = sortKey;
    }

    /**
     * <p>checkBoxDspFlg を取得します。
     * @return checkBoxDspFlg
     */
    public boolean getCheckBoxDspFlg() {
        return checkBoxDspFlg__;
    }

    /**
     * <p>checkBoxDspFlg をセットします。
     * @param checkBoxDspFlg checkBoxDspFlg
     */

    public void setCheckBoxDspFlg(boolean checkBoxDspFlg) {
        checkBoxDspFlg__ = checkBoxDspFlg;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textIpad = gsMsg.getMessage("ipk.6");
        String textMachine = gsMsg.getMessage("ipk.7");
        String textMsg = gsMsg.getMessage("cmn.comment");

        //-- IPアドレス1 --
        if (textNum__.equals(IpkConst.TEXT_NUMFLG1)) {
        errors = IpkValidate.validateintAddressFieldText(
                                                errors,
                                                textIpad,
                                                iadAddText1__,
                                                "iadad",
                                                IpkConst.MAX_LENGTH_IPAD,
                                                true);
        }

        //--  IPアドレス2 --
        if (textNum__.equals(IpkConst.TEXT_NUMFLG1) || textNum__.equals(IpkConst.TEXT_NUMFLG2)) {
        errors = IpkValidate.validateintAddressFieldText(
                                                errors,
                                                textIpad,
                                                iadAddText2__,
                                                "iadad",
                                                IpkConst.MAX_LENGTH_IPAD,
                                                true);
        }

        //--  IPアドレス3 --
        if (textNum__.equals(IpkConst.TEXT_NUMFLG1)
                || textNum__.equals(IpkConst.TEXT_NUMFLG2)
                || textNum__.equals(IpkConst.TEXT_NUMFLG3)) {
        errors = IpkValidate.validateintAddressFieldText(
                                                errors,
                                                textIpad,
                                                iadAddText3__,
                                                "iadad",
                                                IpkConst.MAX_LENGTH_IPAD,
                                                true);
        }
        //--  IPアドレス4 --
        errors = IpkValidate.validateintAddressFieldText(
                                                errors,
                                                textIpad,
                                                iadAddText4__,
                                                "iadad",
                                                IpkConst.MAX_LENGTH_IPAD,
                                                true);

        //-- マシン名 --
        errors = IpkValidate.validateCmnFieldText(
                                                errors,
                                                textMachine,
                                                iadMachineName__,
                                                "iadName",
                                                IpkConst.MAX_LENGTH_MACHINE,
                                                true);

        //-- コメント --
        errors = IpkValidate.validateFieldTextArea(
                                                errors,
                                                textMsg,
                                                iadMsg__,
                                                "iadMsg",
                                                IpkConst.MAX_LENGTH_MSG,
                                                true);
        return errors;
    }

    /**
     * <br>[機  能] IPアドレスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model ValidateCheckModel
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(ValidateCheckModel model, RequestModel reqMdl) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textIpad = gsMsg.getMessage("ipk.6");

        //-- IPアドレス全体 --
        errors = IpkValidate.validateIpadAllFieldText(
                                                errors,
                                                textIpad,
                                                model,
                                                "iadAddInput");
        return errors;
    }

    /**
     * <p>ipk040FileLabelList を取得します。
     * @return ipk040FileLabelList
     */
    public List<LabelValueBean> getIpk040FileLabelList() {
        return ipk040FileLabelList__;
    }

    /**
     * <p>ipk040FileLabelList をセットします。
     * @param ipk040FileLabelList ipk040FileLabelList
     */
    public void setIpk040FileLabelList(List<LabelValueBean> ipk040FileLabelList) {
        ipk040FileLabelList__ = ipk040FileLabelList;
    }

    /**
     * <p>tempDir を取得します。
     * @return tempDir
     */
    public String getTempDir() {
        return tempDir__;
    }

    /**
     * <p>tempDir をセットします。
     * @param tempDir tempDir
     */
    public void setTempDir(String tempDir) {
        tempDir__ = tempDir;
    }

    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public String getBinSid() {
        return binSid__;
    }

    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(String binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>tempExist を取得します。
     * @return tempExist
     */
    public boolean isTempExist() {
        return tempExist__;
    }

    /**
     * <p>tempExist をセットします。
     * @param tempExist tempExist
     */
    public void setTempExist(boolean tempExist) {
        tempExist__ = tempExist;
    }

    /**
     * <p>dspFlg を取得します。
     * @return dspFlg
     */
    public String getDspFlg() {
        return dspFlg__;
    }

    /**
     * <p>dspFlg をセットします。
     * @param dspFlg dspFlg
     */
    public void setDspFlg(String dspFlg) {
        dspFlg__ = dspFlg;
    }

    /**
     * <p>ipadHikoukaiBinFileInfList を取得します。
     * @return ipadHikoukaiBinFileInfList
     */
    public ArrayList<IpkBinModel> getIpadHikoukaiBinFileInfList() {
        return ipadHikoukaiBinFileInfList__;
    }

    /**
     * <p>ipadHikoukaiBinFileInfList をセットします。
     * @param ipadHikoukaiBinFileInfList ipadHikoukaiBinFileInfList
     */
    public void setIpadHikoukaiBinFileInfList(
            ArrayList<IpkBinModel> ipadHikoukaiBinFileInfList) {
        ipadHikoukaiBinFileInfList__ = ipadHikoukaiBinFileInfList;
    }

    /**
     * <p>ipadKoukaiBinFileInfList を取得します。
     * @return ipadKoukaiBinFileInfList
     */
    public ArrayList<IpkBinModel> getIpadKoukaiBinFileInfList() {
        return ipadKoukaiBinFileInfList__;
    }

    /**
     * <p>ipadKoukaiBinFileInfList をセットします。
     * @param ipadKoukaiBinFileInfList ipadKoukaiBinFileInfList
     */
    public void setIpadKoukaiBinFileInfList(
            ArrayList<IpkBinModel> ipadKoukaiBinFileInfList) {
        ipadKoukaiBinFileInfList__ = ipadKoukaiBinFileInfList;
    }

    /**
     * <p>netInfDspFlg を取得します。
     * @return netInfDspFlg
     */
    public String getNetInfDspFlg() {
        return netInfDspFlg__;
    }

    /**
     * <p>netInfDspFlg をセットします。
     * @param netInfDspFlg netInfDspFlg
     */
    public void setNetInfDspFlg(String netInfDspFlg) {
        netInfDspFlg__ = netInfDspFlg;
    }

    /**
     * <p>returnCmd を取得します。
     * @return returnCmd
     */
    public String getReturnCmd() {
        return returnCmd__;
    }

    /**
     * <p>returnCmd をセットします。
     * @param returnCmd returnCmd
     */
    public void setReturnCmd(String returnCmd) {
        returnCmd__ = returnCmd;
    }
}
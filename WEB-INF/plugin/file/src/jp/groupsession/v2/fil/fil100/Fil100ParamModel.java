package jp.groupsession.v2.fil.fil100;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil040.Fil040ParamModel;
import jp.groupsession.v2.fil.model.FileDspModel;

/**
 * <br>[機  能] ファイル詳細検索画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil100ParamModel extends Fil040ParamModel {

    /** 抽出対象　キャビネット */
    private String fil100SltCabinetSid__ = null;
    /** 抽出対象　キャビネット */
    private int fil100SltCabinetKbn__ = -1;
    /** 抽出対象　フォルダ */
    private String fil100ChkTrgFolder__ = "0";
    /** 抽出対象　ファイル */
    private String fil100ChkTrgFile__ = "0";
    /** 抽出対象　削除済みファイル */
    private String fil100ChkTrgDeleted__ = "0";
    /** 抽出対象　削除済みフォルダ */
    private String fil100ChkTrgDeletedFolder__ = "0";

    /** キーワード AND OR */
    private String fil100SearchMode__ = String.valueOf(GSConstFile.KEY_WORD_KBN_AND);
    /** キーワード検索対象 名前 */
    private String fil100ChkWdTrgName__ = "0";
    /** キーワード検索対象 備考 */
    private String fil100ChkWdTrgBiko__ = "0";
    /** キーワード検索対象 ファイル内容 */
    private String fil100ChkWdTrgText__ = "0";

    /** 検索 取引先 */
    private String fil100SearchTradeTarget__ = null;
    /** 検索 取引金額 指定有りなし */
    private String fil100SearchTradeMoneyNoset__ = String.valueOf(GSConstFile.SEARCH_USE);
    /** 検索 取引金額 金額無し/有り */
    private String fil100SearchTradeMoneyKbn__ = String.valueOf(Fil100Form.TRADEMONEYKBN_WITHOUT);
    /** 検索 取引金額 */
    private String fil100SearchTradeMoney__ = null;
    /** 検索 取引金額to (判定条件に"から"が設定されている場合の未使用) */
    private String fil100SearchTradeMoneyTo__ = null;
    /** 検索 取引金額 外貨*/
    private String fil100SearchTradeMoneyType__ = null;
    /** 検索 取引金額 判定条件*/
    private String fil100SearchTradeMoneyJudge__ = null;
    /** 検索 取引年月日 from*/
    private String fil100SearchTradeDateFrom__ = null;
    /** 検索 取引年月日 to */
    private String fil100SearchTradeDateTo__ = null;
    /** 検索 取引年月日 */
    private String fil100SearchTradeDateKbn__ = String.valueOf(GSConstFile.SEARCH_USE);

    /** 更新日　日時指定区分 */
    private String fil100ChkOnOff__ = "0";
    /** 検索結果一覧 */
    private List <FileDspModel> resultList__ = null;
    /** キャビネットラベル */
    private List<LabelValueBean> cabinetLabel__;
    /** キャビネットラベル(削除済み) */
    private List<LabelValueBean> delCabinetLabel__;
    /** 外貨ラベル */
    private List<LabelValueBean> moneyTypeLabel__;
    /** 取引金額 条件ラベル */
    private List<LabelValueBean> moneyJudgeLabel__;

    /** 検索実行フラグ */
    private int searchFlg__ = GSConstFile.SEARCH_EXECUTE_TRUE;
    /** ページ1 */
    private int fil100pageNum1__ = 1;
    /** ページ2 */
    private int fil100pageNum2__ = 1;
    /** ページコンボ */
    private List<LabelValueBean> pageList__ = null;
    /** ページ表示フラグ */
    private boolean fil100pageDspFlg__ = false;
    /** 検索警告表示フラグ */
    private int fil100WarnDspFlg__ = 0;
    /** 検索結果件数 */
    private long fil100ResultCount__ = 0;
    /** 検索警告表示OK */
    private int fil100WarnOk__ = 0;

    /** 検索更新開始日付 年 */
    private int fileSearchfromYear__ = 0;
    /** 検索更新開始日付 月 */
    private int fileSearchfromMonth__ = 0;
    /** 検索更新開始日付 日 */
    private int fileSearchfromDay__ = 0;
    /** 検索更新終了日付 年 */
    private int fileSearchtoYear__ = 0;
    /** 検索更新終了日付 月 */
    private int fileSearchtoMonth__ = 0;
    /** 検索更新終了日付 日 */
    private int fileSearchtoDay__ = 0;
    /** 検索更新開始日付 年月日 */
    private String fileSearchfromDate__ = null;
    /** 検索更新終了日付 年月日 */
    private String fileSearchtoDate__ = null;

    /** ソートキー */
    private int fil100sortKey__ = GSConstFile.SORT_NAME;
    /** オーダーキー */
    private int fil100orderKey__ = GSConstFile.ORDER_KEY_ASC;

    /** バイナリSID */
    private String binSid__ = null;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int fil100searchUse__ = GSConst.PLUGIN_USE;

    /** WEB検索ワード */
    private String fil100WebSearchWord__ = "";
    /** HTML表示用検索ワード */
    private String fil100HtmlSearchWord__ = "";

    /** 初期表示フラグ */
    private int fil100InitFlg__ = 0;
    /** 管理者ユーザフラグ */
    private boolean fil100adminUser__ = false;

    /*-- SVパラメータ --*/

    /** キャビネットSID */
    private String fil100SvSltCabinetSid__ = null;
    /** 抽出対象　キャビネット */
    private int fil100SvSltCabinetKbn__ = GSConstFile.CABINET_KBN_PUBLIC;
    /** 抽出対象　フォルダ */
    private String fil100SvChkTrgFolder__ = String.valueOf(GSConstFile.GET_TARGET_FOLDER);
    /** 抽出対象　ファイル */
    private String fil100SvChkTrgFile__ = String.valueOf(GSConstFile.GET_TARGET_FILE);
    /** 抽出対象　削除済みファイル */
    private String fil100SvChkTrgDeleted__ = String.valueOf(GSConstFile.GET_TARGET_FILE);
    /** 抽出対象　削除済みフォルダ */
    private String fil100SvChkTrgDeletedFolder__ = String.valueOf(GSConstFile.GET_TARGET_FOLDER);
    /** キーワード AND OR */
    private String fil100SvSearchMode__ = String.valueOf(GSConstFile.KEY_WORD_KBN_AND);
    /** キーワード検索対象 名前 */
    private String fil100SvChkWdTrgName__ = String.valueOf(GSConstFile.KEYWORD_TARGET_NAME);
    /** キーワード検索対象 備考 */
    private String fil100SvChkWdTrgBiko__ = String.valueOf(GSConstFile.KEYWORD_TARGET_BIKO);
    /** キーワード検索対象 ファイル内容 */
    private String fil100SvChkWdTrgText__ = String.valueOf(GSConstFile.KEYWORD_TARGET_TEXT);
    /** 検索 取引先 */
    private String fil100SvSearchTradeTarget__ = null;
    /** 検索 取引金額 */
    private String fil100SvSearchTradeMoney__ = null;
    /** 検索 取引金額To */
    private String fil100SvSearchTradeMoneyTo__ = null;
    /** 検索 取引金額 外貨*/
    private String fil100SvSearchTradeMoneyType__ = null;
    /** 検索 取引金額 判定条件*/
    private String fil100SvSearchTradeMoneyJudge__ = null;
    /** 検索 取引金額 指定有りなし */
    private String fil100SvSearchTradeMoneyNoset__ = String.valueOf(GSConstFile.SEARCH_NON);
    /** 検索 取引金額 金額無し/有り */
    private String fil100SvSearchTradeMoneyKbn__ = String.valueOf(Fil100Form.TRADEMONEYKBN_WITHOUT);
    /** 検索 取引年月日 from*/
    private String fil100SvSearchTradeDateFrom__ = null;
    /** 検索 取引年月日 to */
    private String fil100SvSearchTradeDateTo__ = null;
    /** 検索 取引年月日 */
    private String fil100SvSearchTradeDateKbn__ = String.valueOf(GSConstFile.SEARCH_NON);
    /** キーワード */
    private String fil100SvChkWdKeyWord__ = null;
    /** from更新日　年 */
    private int fileSvSearchfromYear__ = 0;
    /** from更新日　月 */
    private int fileSvSearchfromMonth__ = 0;
    /** from更新日　日 */
    private int fileSvSearchfromDay__ = 0;
    /** to  更新日　年 */
    private int fileSvSearchtoYear__ = 0;
    /** to  更新日　月 */
    private int fileSvSearchtoMonth__ = 0;
    /** to  更新日　日 */
    private int fileSvSearchtoDay__ = 0;
    /** from更新日　年月日 */
    private String fileSvSearchfromDate__ = null;
    /** to  更新日　日 */
    private String fileSvSearchtoDate__ = null;
    /** 更新日　指定区分 */
    private String fil100SvChkOnOff__ = String.valueOf(GSConstFile.UPDATE_KBN_NO);

    /**
     * 検索条件パラメータをSAVEフィールドへ移行します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void saveSearchParm() {

        fil100SvSltCabinetSid__ = fil100SltCabinetSid__;
        fil100SvChkTrgFolder__ = fil100ChkTrgFolder__;
        fil100SvChkTrgFile__ = fil100ChkTrgFile__;
        fil100SvSearchMode__ = fil100SearchMode__;
        fil100SvChkWdTrgName__ = fil100ChkWdTrgName__;
        fil100SvChkWdTrgBiko__ = fil100ChkWdTrgBiko__;
        fil100SvChkWdTrgText__ = fil100ChkWdTrgText__;
        fil100SvChkWdKeyWord__ = getFilSearchWd();
        fileSvSearchfromYear__ = fileSearchfromYear__;
        fileSvSearchfromMonth__ = fileSearchfromMonth__;
        fileSvSearchfromDay__ = fileSearchfromDay__;
        fileSvSearchtoYear__ = fileSearchtoYear__;
        fileSvSearchtoMonth__ = fileSearchtoMonth__;
        fileSvSearchtoDay__ = fileSearchtoDay__;
        fil100SvChkOnOff__ = NullDefault.getString(fil100ChkOnOff__, "0");
    }

    /**
     * <br>[機  能] 検索条件区分を初期化する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void initSearchKbn() {

        if (fil100SltCabinetSid__ == null) {
            fil100ChkOnOff__ = String.valueOf(GSConstFile.UPDATE_KBN_NO);
            fil100ChkTrgFile__ = String.valueOf(GSConstFile.GET_TARGET_FILE);
            fil100ChkTrgFolder__ = String.valueOf(GSConstFile.GET_TARGET_FOLDER);
            fil100ChkWdTrgName__ = String.valueOf(GSConstFile.KEYWORD_TARGET_NAME);
            fil100ChkWdTrgBiko__ = String.valueOf(GSConstFile.KEYWORD_TARGET_BIKO);
            fil100ChkWdTrgText__ = String.valueOf(GSConstFile.KEYWORD_TARGET_TEXT);
        }
    }

    /**
     * <p>fil100pageDspFlg を取得します。
     * @return fil100pageDspFlg
     */
    public boolean isFil100pageDspFlg() {
        return fil100pageDspFlg__;
    }
    /**
     * <p>fil100pageDspFlg をセットします。
     * @param fil100pageDspFlg fil100pageDspFlg
     */
    public void setFil100pageDspFlg(boolean fil100pageDspFlg) {
        fil100pageDspFlg__ = fil100pageDspFlg;
    }
    /**
     * <p>fil100WarnDspFlg を取得します。
     * @return fil100WarnDspFlg
     */
    public int getFil100WarnDspFlg() {
        return fil100WarnDspFlg__;
    }
    /**
     * <p>fil100WarnDspFlg をセットします。
     * @param fil100WarnDspFlg fil100WarnDspFlg
     */
    public void setFil100WarnDspFlg(int fil100WarnDspFlg) {
        this.fil100WarnDspFlg__ = fil100WarnDspFlg;
    }
    /**
     * <p>fil100ResultCount を取得します。
     * @return fil100ResultCount
     */
    public long getFil100ResultCount() {
        return fil100ResultCount__;
    }
    /**
     * <p>fil100ResultCount をセットします。
     * @param fil100ResultCount fil100ResultCount
     */
    public void setFil100ResultCount(long fil100ResultCount) {
        this.fil100ResultCount__ = fil100ResultCount;
    }
    /**
     * <p>fil100WarnOk を取得します。
     * @return fil100WarnOk
     */
    public int getFil100WarnOk() {
        return fil100WarnOk__;
    }
    /**
     * <p>fil100WarnOk をセットします。
     * @param fil100WarnOk fil100WarnOk
     */
    public void setFil100WarnOk(int fil100WarnOk) {
        this.fil100WarnOk__ = fil100WarnOk;
    }
    /**
     * <p>pageList を取得します。
     * @return pageList
     */
    public List<LabelValueBean> getPageList() {
        return pageList__;
    }
    /**
     * <p>pageList をセットします。
     * @param pageList pageList
     */
    public void setPageList(List<LabelValueBean> pageList) {
        pageList__ = pageList;
    }
    /**
     * <p>fil100pageNum1 を取得します。
     * @return fil100pageNum1
     */
    public int getFil100pageNum1() {
        return fil100pageNum1__;
    }
    /**
     * <p>fil100pageNum1 をセットします。
     * @param fil100pageNum1 fil100pageNum1
     */
    public void setFil100pageNum1(int fil100pageNum1) {
        fil100pageNum1__ = fil100pageNum1;
    }
    /**
     * <p>fil100pageNum2 を取得します。
     * @return fil100pageNum2
     */
    public int getFil100pageNum2() {
        return fil100pageNum2__;
    }
    /**
     * <p>fil100pageNum2 をセットします。
     * @param fil100pageNum2 fil100pageNum2
     */
    public void setFil100pageNum2(int fil100pageNum2) {
        fil100pageNum2__ = fil100pageNum2;
    }
    /**
     * <p>searchFlg を取得します。
     * @return searchFlg
     */
    public int getSearchFlg() {
        return searchFlg__;
    }

    /**
     * <p>searchFlg をセットします。
     * @param searchFlg searchFlg
     */
    public void setSearchFlg(int searchFlg) {
        searchFlg__ = searchFlg;
    }
    /**
     * <p>cabinetLabel を取得します。
     * @return cabinetLabel
     */
    public List<LabelValueBean> getCabinetLabel() {
        return cabinetLabel__;
    }

    /**
     * <p>cabinetLabel をセットします。
     * @param cabinetLabel cabinetLabel
     */
    public void setCabinetLabel(List<LabelValueBean> cabinetLabel) {
        cabinetLabel__ = cabinetLabel;
    }

    /**
     * <p>delCabinetLabel を取得します。
     * @return delCabinetLabel
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#delCabinetLabel__
     */
    public List<LabelValueBean> getDelCabinetLabel() {
        return delCabinetLabel__;
    }

    /**
     * <p>delCabinetLabel をセットします。
     * @param delCabinetLabel delCabinetLabel
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#delCabinetLabel__
     */
    public void setDelCabinetLabel(List<LabelValueBean> delCabinetLabel) {
        delCabinetLabel__ = delCabinetLabel;
    }

    /**
     * <p>moneyTypeLabel を取得します。
     * @return moneyTypeLabel
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#moneyTypeLabel__
     */
    public List<LabelValueBean> getMoneyTypeLabel() {
        return moneyTypeLabel__;
    }

    /**
     * <p>moneyTypeLabel をセットします。
     * @param moneyTypeLabel moneyTypeLabel
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#moneyTypeLabel__
     */
    public void setMoneyTypeLabel(List<LabelValueBean> moneyTypeLabel) {
        moneyTypeLabel__ = moneyTypeLabel;
    }

    /**
     * <p>moneyJudgeLabel を取得します。
     * @return moneyJudgeLabel
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#moneyJudgeLabel__
     */
    public List<LabelValueBean> getMoneyJudgeLabel() {
        return moneyJudgeLabel__;
    }

    /**
     * <p>moneyJudgeLabel をセットします。
     * @param moneyJudgeLabel moneyJudgeLabel
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#moneyJudgeLabel__
     */
    public void setMoneyJudgeLabel(List<LabelValueBean> moneyJudgeLabel) {
        moneyJudgeLabel__ = moneyJudgeLabel;
    }

    /**
     * <p>resultList を取得します。
     * @return resultList
     */
    public List<FileDspModel> getResultList() {
        return resultList__;
    }

    /**
     * <p>resultList をセットします。
     * @param resultList resultList
     */
    public void setResultList(List<FileDspModel> resultList) {
        resultList__ = resultList;
    }

    /**
     * <p>fil100SvChkWdKeyWord を取得します。
     * @return fil100SvChkWdKeyWord
     */
    public String getFil100SvChkWdKeyWord() {
        return fil100SvChkWdKeyWord__;
    }
    /**
     * <p>fil100SvChkWdKeyWord をセットします。
     * @param fil100SvChkWdKeyWord fil100SvChkWdKeyWord
     */
    public void setFil100SvChkWdKeyWord(String fil100SvChkWdKeyWord) {
        fil100SvChkWdKeyWord__ = fil100SvChkWdKeyWord;
    }
    /**
     * <p>fil100ChkOnOff を取得します。
     * @return fil100ChkOnOff
     */
    public String getFil100ChkOnOff() {
        return fil100ChkOnOff__;
    }
    /**
     * <p>fil100ChkOnOff をセットします。
     * @param fil100ChkOnOff fil100ChkOnOff
     */
    public void setFil100ChkOnOff(String fil100ChkOnOff) {
        fil100ChkOnOff__ = fil100ChkOnOff;
    }
    /**
     * <p>fil100ChkTrgDeleted を取得します。
     * @return fil100ChkTrgDeleted
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100ChkTrgDeleted__
     */
    public String getFil100ChkTrgDeleted() {
        return fil100ChkTrgDeleted__;
    }
    /**
     * <p>fil100ChkTrgDeleted をセットします。
     * @param fil100ChkTrgDeleted fil100ChkTrgDeleted
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100ChkTrgDeleted__
     */
    public void setFil100ChkTrgDeleted(String fil100ChkTrgDeleted) {
        fil100ChkTrgDeleted__ = fil100ChkTrgDeleted;
    }
    /**
     * <p>fil100ChkTrgDeletedFolder を取得します。
     * @return fil100ChkTrgDeletedFolder
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100ChkTrgDeletedFolder__
     */
    public String getFil100ChkTrgDeletedFolder() {
        return fil100ChkTrgDeletedFolder__;
    }
    /**
     * <p>fil100ChkTrgDeletedFolder をセットします。
     * @param fil100ChkTrgDeletedFolder fil100ChkTrgDeletedFolder
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100ChkTrgDeletedFolder__
     */
    public void setFil100ChkTrgDeletedFolder(String fil100ChkTrgDeletedFolder) {
        fil100ChkTrgDeletedFolder__ = fil100ChkTrgDeletedFolder;
    }
    /**
     * <p>fil100ChkTrgFile を取得します。
     * @return fil100ChkTrgFile
     */
    public String getFil100ChkTrgFile() {
        return fil100ChkTrgFile__;
    }
    /**
     * <p>fil100ChkTrgFile をセットします。
     * @param fil100ChkTrgFile fil100ChkTrgFile
     */
    public void setFil100ChkTrgFile(String fil100ChkTrgFile) {
        fil100ChkTrgFile__ = fil100ChkTrgFile;
    }
    /**
     * <p>fil100ChkTrgFolder を取得します。
     * @return fil100ChkTrgFolder
     */
    public String getFil100ChkTrgFolder() {
        return fil100ChkTrgFolder__;
    }
    /**
     * <p>fil100ChkTrgFolder をセットします。
     * @param fil100ChkTrgFolder fil100ChkTrgFolder
     */
    public void setFil100ChkTrgFolder(String fil100ChkTrgFolder) {
        fil100ChkTrgFolder__ = fil100ChkTrgFolder;
    }
    /**
     * <p>fil100ChkWdTrgBiko を取得します。
     * @return fil100ChkWdTrgBiko
     */
    public String getFil100ChkWdTrgBiko() {
        return fil100ChkWdTrgBiko__;
    }
    /**
     * <p>fil100ChkWdTrgBiko をセットします。
     * @param fil100ChkWdTrgBiko fil100ChkWdTrgBiko
     */
    public void setFil100ChkWdTrgBiko(String fil100ChkWdTrgBiko) {
        fil100ChkWdTrgBiko__ = fil100ChkWdTrgBiko;
    }
    /**
     * <p>fil100ChkWdTrgName を取得します。
     * @return fil100ChkWdTrgName
     */
    public String getFil100ChkWdTrgName() {
        return fil100ChkWdTrgName__;
    }
    /**
     * <p>fil100ChkWdTrgName をセットします。
     * @param fil100ChkWdTrgName fil100ChkWdTrgName
     */
    public void setFil100ChkWdTrgName(String fil100ChkWdTrgName) {
        fil100ChkWdTrgName__ = fil100ChkWdTrgName;
    }
    /**
     * <p>fil100SearchMode を取得します。
     * @return fil100SearchMode
     */
    public String getFil100SearchMode() {
        return fil100SearchMode__;
    }
    /**
     * <p>fil100SearchMode をセットします。
     * @param fil100SearchMode fil100SearchMode
     */
    public void setFil100SearchMode(String fil100SearchMode) {
        fil100SearchMode__ = fil100SearchMode;
    }

    /**
     * <p>fil100SvChkOnOff を取得します。
     * @return fil100SvChkOnOff
     */
    public String getFil100SvChkOnOff() {
        return fil100SvChkOnOff__;
    }
    /**
     * <p>fil100SvChkOnOff をセットします。
     * @param fil100SvChkOnOff fil100SvChkOnOff
     */
    public void setFil100SvChkOnOff(String fil100SvChkOnOff) {
        fil100SvChkOnOff__ = fil100SvChkOnOff;
    }
    /**
     * <p>fil100SvChkTrgDeleted を取得します。
     * @return fil100SvChkTrgDeleted
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvChkTrgDeleted__
     */
    public String getFil100SvChkTrgDeleted() {
        return fil100SvChkTrgDeleted__;
    }
    /**
     * <p>fil100SvChkTrgDeleted をセットします。
     * @param fil100SvChkTrgDeleted fil100SvChkTrgDeleted
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvChkTrgDeleted__
     */
    public void setFil100SvChkTrgDeleted(String fil100SvChkTrgDeleted) {
        fil100SvChkTrgDeleted__ = fil100SvChkTrgDeleted;
    }
    /**
     * <p>fil100SvChkTrgDeletedFolder を取得します。
     * @return fil100SvChkTrgDeletedFolder
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SvChkTrgDeletedFolder__
     */
    public String getFil100SvChkTrgDeletedFolder() {
        return fil100SvChkTrgDeletedFolder__;
    }
    /**
     * <p>fil100SvChkTrgDeletedFolder をセットします。
     * @param fil100SvChkTrgDeletedFolder fil100SvChkTrgDeletedFolder
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SvChkTrgDeletedFolder__
     */
    public void setFil100SvChkTrgDeletedFolder(String fil100SvChkTrgDeletedFolder) {
        fil100SvChkTrgDeletedFolder__ = fil100SvChkTrgDeletedFolder;
    }
    /**
     * <p>fil100SvChkTrgFile を取得します。
     * @return fil100SvChkTrgFile
     */
    public String getFil100SvChkTrgFile() {
        return fil100SvChkTrgFile__;
    }
    /**
     * <p>fil100SvChkTrgFile をセットします。
     * @param fil100SvChkTrgFile fil100SvChkTrgFile
     */
    public void setFil100SvChkTrgFile(String fil100SvChkTrgFile) {
        fil100SvChkTrgFile__ = fil100SvChkTrgFile;
    }
    /**
     * <p>fil100SvChkTrgFolder を取得します。
     * @return fil100SvChkTrgFolder
     */
    public String getFil100SvChkTrgFolder() {
        return fil100SvChkTrgFolder__;
    }
    /**
     * <p>fil100SvChkTrgFolder をセットします。
     * @param fil100SvChkTrgFolder fil100SvChkTrgFolder
     */
    public void setFil100SvChkTrgFolder(String fil100SvChkTrgFolder) {
        fil100SvChkTrgFolder__ = fil100SvChkTrgFolder;
    }
    /**
     * <p>fil100SvChkWdTrgBiko を取得します。
     * @return fil100SvChkWdTrgBiko
     */
    public String getFil100SvChkWdTrgBiko() {
        return fil100SvChkWdTrgBiko__;
    }
    /**
     * <p>fil100SvChkWdTrgBiko をセットします。
     * @param fil100SvChkWdTrgBiko fil100SvChkWdTrgBiko
     */
    public void setFil100SvChkWdTrgBiko(String fil100SvChkWdTrgBiko) {
        fil100SvChkWdTrgBiko__ = fil100SvChkWdTrgBiko;
    }
    /**
     * <p>fil100SvChkWdTrgName を取得します。
     * @return fil100SvChkWdTrgName
     */
    public String getFil100SvChkWdTrgName() {
        return fil100SvChkWdTrgName__;
    }
    /**
     * <p>fil100SvChkWdTrgName をセットします。
     * @param fil100SvChkWdTrgName fil100SvChkWdTrgName
     */
    public void setFil100SvChkWdTrgName(String fil100SvChkWdTrgName) {
        fil100SvChkWdTrgName__ = fil100SvChkWdTrgName;
    }
    /**
     * <p>fil100SvSearchMode を取得します。
     * @return fil100SvSearchMode
     */
    public String getFil100SvSearchMode() {
        return fil100SvSearchMode__;
    }
    /**
     * <p>fil100SvSearchMode をセットします。
     * @param fil100SvSearchMode fil100SvSearchMode
     */
    public void setFil100SvSearchMode(String fil100SvSearchMode) {
        fil100SvSearchMode__ = fil100SvSearchMode;
    }
    /**
     * <p>fil100SvSltCabinetSid を取得します。
     * @return fil100SvSltCabinetSid
     */
    public String getFil100SvSltCabinetSid() {
        return fil100SvSltCabinetSid__;
    }
    /**
     * <p>fil100SvSltCabinetSid をセットします。
     * @param fil100SvSltCabinetSid fil100SvSltCabinetSid
     */
    public void setFil100SvSltCabinetSid(String fil100SvSltCabinetSid) {
        fil100SvSltCabinetSid__ = fil100SvSltCabinetSid;
    }

    /**
     * <p>fil100SvSltCabinetKbn を取得します。
     * @return fil100SvSltCabinetKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSltCabinetKbn__
     */
    public int getFil100SvSltCabinetKbn() {
        return fil100SvSltCabinetKbn__;
    }

    /**
     * <p>fil100SvSltCabinetKbn をセットします。
     * @param fil100SvSltCabinetKbn fil100SvSltCabinetKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSltCabinetKbn__
     */
    public void setFil100SvSltCabinetKbn(int fil100SvSltCabinetKbn) {
        fil100SvSltCabinetKbn__ = fil100SvSltCabinetKbn;
    }

    /**
     * <p>fileSearchfromDay を取得します。
     * @return fileSearchfromDay
     */
    public int getFileSearchfromDay() {
        return fileSearchfromDay__;
    }

    /**
     * <p>fileSearchfromDay をセットします。
     * @param fileSearchfromDay fileSearchfromDay
     */
    public void setFileSearchfromDay(int fileSearchfromDay) {
        fileSearchfromDay__ = fileSearchfromDay;
    }

    /**
     * <p>fileSearchfromMonth を取得します。
     * @return fileSearchfromMonth
     */
    public int getFileSearchfromMonth() {
        return fileSearchfromMonth__;
    }

    /**
     * <p>fileSearchfromMonth をセットします。
     * @param fileSearchfromMonth fileSearchfromMonth
     */
    public void setFileSearchfromMonth(int fileSearchfromMonth) {
        fileSearchfromMonth__ = fileSearchfromMonth;
    }

    /**
     * <p>fileSearchfromYear を取得します。
     * @return fileSearchfromYear
     */
    public int getFileSearchfromYear() {
        return fileSearchfromYear__;
    }

    /**
     * <p>fileSearchfromYear をセットします。
     * @param fileSearchfromYear fileSearchfromYear
     */
    public void setFileSearchfromYear(int fileSearchfromYear) {
        fileSearchfromYear__ = fileSearchfromYear;
    }

    /**
     * <p>fileSearchtoDay を取得します。
     * @return fileSearchtoDay
     */
    public int getFileSearchtoDay() {
        return fileSearchtoDay__;
    }

    /**
     * <p>fileSearchtoDay をセットします。
     * @param fileSearchtoDay fileSearchtoDay
     */
    public void setFileSearchtoDay(int fileSearchtoDay) {
        fileSearchtoDay__ = fileSearchtoDay;
    }

    /**
     * <p>fileSearchtoMonth を取得します。
     * @return fileSearchtoMonth
     */
    public int getFileSearchtoMonth() {
        return fileSearchtoMonth__;
    }

    /**
     * <p>fileSearchtoMonth をセットします。
     * @param fileSearchtoMonth fileSearchtoMonth
     */
    public void setFileSearchtoMonth(int fileSearchtoMonth) {
        fileSearchtoMonth__ = fileSearchtoMonth;
    }

    /**
     * <p>fileSearchtoYear を取得します。
     * @return fileSearchtoYear
     */
    public int getFileSearchtoYear() {
        return fileSearchtoYear__;
    }

    /**
     * <p>fileSearchtoYear をセットします。
     * @param fileSearchtoYear fileSearchtoYear
     */
    public void setFileSearchtoYear(int fileSearchtoYear) {
        fileSearchtoYear__ = fileSearchtoYear;
    }

    /**
     * <p>fileSearchfromDate を取得します。
     * @return fileSearchfromDate
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fileSearchfromDate__
     */
    public String getFileSearchfromDate() {
        return fileSearchfromDate__;
    }

    /**
     * <p>fileSearchfromDate をセットします。
     * @param fileSearchfromDate fileSearchfromDate
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fileSearchfromDate__
     */
    public void setFileSearchfromDate(String fileSearchfromDate) {
        fileSearchfromDate__ = fileSearchfromDate;
    }

    /**
     * <p>fileSearchtoDate を取得します。
     * @return fileSearchtoDate
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fileSearchtoDate__
     */
    public String getFileSearchtoDate() {
        return fileSearchtoDate__;
    }

    /**
     * <p>fileSearchtoDate をセットします。
     * @param fileSearchtoDate fileSearchtoDate
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fileSearchtoDate__
     */
    public void setFileSearchtoDate(String fileSearchtoDate) {
        fileSearchtoDate__ = fileSearchtoDate;
    }

    /**
     * <p>fil100orderKey を取得します。
     * @return fil100orderKey
     */
    public int getFil100orderKey() {
        return fil100orderKey__;
    }

    /**
     * <p>fil100orderKey をセットします。
     * @param fil100orderKey fil100orderKey
     */
    public void setFil100orderKey(int fil100orderKey) {
        fil100orderKey__ = fil100orderKey;
    }

    /**
     * <p>fil100sortKey を取得します。
     * @return fil100sortKey
     */
    public int getFil100sortKey() {
        return fil100sortKey__;
    }

    /**
     * <p>fil100sortKey をセットします。
     * @param fil100sortKey fil100sortKey
     */
    public void setFil100sortKey(int fil100sortKey) {
        fil100sortKey__ = fil100sortKey;
    }

    /**
     * <p>fileSvSearchfromDay を取得します。
     * @return fileSvSearchfromDay
     */
    public int getFileSvSearchfromDay() {
        return fileSvSearchfromDay__;
    }

    /**
     * <p>fileSvSearchfromDay をセットします。
     * @param fileSvSearchfromDay fileSvSearchfromDay
     */
    public void setFileSvSearchfromDay(int fileSvSearchfromDay) {
        fileSvSearchfromDay__ = fileSvSearchfromDay;
    }

    /**
     * <p>fileSvSearchfromMonth を取得します。
     * @return fileSvSearchfromMonth
     */
    public int getFileSvSearchfromMonth() {
        return fileSvSearchfromMonth__;
    }

    /**
     * <p>fileSvSearchfromMonth をセットします。
     * @param fileSvSearchfromMonth fileSvSearchfromMonth
     */
    public void setFileSvSearchfromMonth(int fileSvSearchfromMonth) {
        fileSvSearchfromMonth__ = fileSvSearchfromMonth;
    }

    /**
     * <p>fileSvSearchfromYear を取得します。
     * @return fileSvSearchfromYear
     */
    public int getFileSvSearchfromYear() {
        return fileSvSearchfromYear__;
    }

    /**
     * <p>fileSvSearchfromYear をセットします。
     * @param fileSvSearchfromYear fileSvSearchfromYear
     */
    public void setFileSvSearchfromYear(int fileSvSearchfromYear) {
        fileSvSearchfromYear__ = fileSvSearchfromYear;
    }

    /**
     * <p>fileSvSearchtoDay を取得します。
     * @return fileSvSearchtoDay
     */
    public int getFileSvSearchtoDay() {
        return fileSvSearchtoDay__;
    }

    /**
     * <p>fileSvSearchtoDay をセットします。
     * @param fileSvSearchtoDay fileSvSearchtoDay
     */
    public void setFileSvSearchtoDay(int fileSvSearchtoDay) {
        fileSvSearchtoDay__ = fileSvSearchtoDay;
    }

    /**
     * <p>fileSvSearchtoMonth を取得します。
     * @return fileSvSearchtoMonth
     */
    public int getFileSvSearchtoMonth() {
        return fileSvSearchtoMonth__;
    }

    /**
     * <p>fileSvSearchtoMonth をセットします。
     * @param fileSvSearchtoMonth fileSvSearchtoMonth
     */
    public void setFileSvSearchtoMonth(int fileSvSearchtoMonth) {
        fileSvSearchtoMonth__ = fileSvSearchtoMonth;
    }

    /**
     * <p>fileSvSearchtoYear を取得します。
     * @return fileSvSearchtoYear
     */
    public int getFileSvSearchtoYear() {
        return fileSvSearchtoYear__;
    }

    /**
     * <p>fileSvSearchtoYear をセットします。
     * @param fileSvSearchtoYear fileSvSearchtoYear
     */
    public void setFileSvSearchtoYear(int fileSvSearchtoYear) {
        fileSvSearchtoYear__ = fileSvSearchtoYear;
    }
    /**
     * <p>fileSvSearchfromDate を取得します。
     * @return fileSvSearchfromDate
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fileSvSearchfromDate__
     */
    public String getFileSvSearchfromDate() {
        return fileSvSearchfromDate__;
    }

    /**
     * <p>fileSvSearchfromDate をセットします。
     * @param fileSvSearchfromDate fileSvSearchfromDate
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fileSvSearchfromDate__
     */
    public void setFileSvSearchfromDate(String fileSvSearchfromDate) {
        fileSvSearchfromDate__ = fileSvSearchfromDate;
    }

    /**
     * <p>fileSvSearchtoDate を取得します。
     * @return fileSvSearchtoDate
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fileSvSearchtoDate__
     */
    public String getFileSvSearchtoDate() {
        return fileSvSearchtoDate__;
    }

    /**
     * <p>fileSvSearchtoDate をセットします。
     * @param fileSvSearchtoDate fileSvSearchtoDate
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fileSvSearchtoDate__
     */
    public void setFileSvSearchtoDate(String fileSvSearchtoDate) {
        fileSvSearchtoDate__ = fileSvSearchtoDate;
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
     * <p>fil100SltCabinetSid を取得します。
     * @return fil100SltCabinetSid
     */
    public String getFil100SltCabinetSid() {
        return fil100SltCabinetSid__;
    }

    /**
     * <p>fil100SltCabinetSid をセットします。
     * @param fil100SltCabinetSid fil100SltCabinetSid
     */
    public void setFil100SltCabinetSid(String fil100SltCabinetSid) {
        fil100SltCabinetSid__ = fil100SltCabinetSid;
    }

    /**
     * <p>fil100SltCabinetKbn を取得します。
     * @return fil100SltCabinetKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SltCabinetKbn__
     */
    public int getFil100SltCabinetKbn() {
        return fil100SltCabinetKbn__;
    }

    /**
     * <p>fil100SltCabinetKbn をセットします。
     * @param fil100SltCabinetKbn fil100SltCabinetKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SltCabinetKbn__
     */
    public void setFil100SltCabinetKbn(int fil100SltCabinetKbn) {
        fil100SltCabinetKbn__ = fil100SltCabinetKbn;
    }

    /**
     * <p>fil100searchUse を取得します。
     * @return fil100searchUse
     */
    public int getFil100searchUse() {
        return fil100searchUse__;
    }

    /**
     * <p>fil100searchUse をセットします。
     * @param fil100searchUse fil100searchUse
     */
    public void setFil100searchUse(int fil100searchUse) {
        fil100searchUse__ = fil100searchUse;
    }

    /**
     * <p>fil100WebSearchWord を取得します。
     * @return fil100WebSearchWord
     */
    public String getFil100WebSearchWord() {
        return fil100WebSearchWord__;
    }

    /**
     * <p>fil100WebSearchWord をセットします。
     * @param fil100WebSearchWord fil100WebSearchWord
     */
    public void setFil100WebSearchWord(String fil100WebSearchWord) {
        fil100WebSearchWord__ = fil100WebSearchWord;
    }

    /**
     * <p>fil100HtmlSearchWord を取得します。
     * @return fil100HtmlSearchWord
     */
    public String getFil100HtmlSearchWord() {
        return fil100HtmlSearchWord__;
    }

    /**
     * <p>fil100HtmlSearchWord をセットします。
     * @param fil100HtmlSearchWord fil100HtmlSearchWord
     */
    public void setFil100HtmlSearchWord(String fil100HtmlSearchWord) {
        fil100HtmlSearchWord__ = fil100HtmlSearchWord;
    }

    /**
     * <p>fil100InitFlg を取得します。
     * @return fil100InitFlg
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100InitFlg__
     */
    public int getFil100InitFlg() {
        return fil100InitFlg__;
    }

    /**
     * <p>fil100InitFlg をセットします。
     * @param fil100InitFlg fil100InitFlg
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100InitFlg__
     */
    public void setFil100InitFlg(int fil100InitFlg) {
        fil100InitFlg__ = fil100InitFlg;
    }

    /**
     * @return fil100ChkWdTrgText
     */
    public String getFil100ChkWdTrgText() {
        return fil100ChkWdTrgText__;
    }

    /**
     * @param fil100ChkWdTrgText セットする fil100ChkWdTrgText
     */
    public void setFil100ChkWdTrgText(String fil100ChkWdTrgText) {
        fil100ChkWdTrgText__ = fil100ChkWdTrgText;
    }

    /**
     * <p>fil100SearchTradeTarget を取得します。
     * @return fil100SearchTradeTarget
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeTarget__
     */
    public String getFil100SearchTradeTarget() {
        return fil100SearchTradeTarget__;
    }
    /**
     * <p>fil100SearchTradeTarget をセットします。
     * @param fil100SearchTradeTarget fil100SearchTradeTarget
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeTarget__
     */
    public void setFil100SearchTradeTarget(String fil100SearchTradeTarget) {
        fil100SearchTradeTarget__ = fil100SearchTradeTarget;
    }
    /**
     * <p>fil100SearchTradeMoney を取得します。
     * @return fil100SearchTradeMoney
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeMoney__
     */
    public String getFil100SearchTradeMoney() {
        return fil100SearchTradeMoney__;
    }
    /**
     * <p>fil100SearchTradeMoney をセットします。
     * @param fil100SearchTradeMoney fil100SearchTradeMoney
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeMoney__
     */
    public void setFil100SearchTradeMoney(String fil100SearchTradeMoney) {
        fil100SearchTradeMoney__ = fil100SearchTradeMoney;
    }
    /**
     * <p>fil100SearchTradeMoneyTo を取得します。
     * @return fil100SearchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SearchTradeMoneyTo__
     */
    public String getFil100SearchTradeMoneyTo() {
        return fil100SearchTradeMoneyTo__;
    }
    /**
     * <p>fil100SearchTradeMoneyTo をセットします。
     * @param fil100SearchTradeMoneyTo fil100SearchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SearchTradeMoneyTo__
     */
    public void setFil100SearchTradeMoneyTo(String fil100SearchTradeMoneyTo) {
        fil100SearchTradeMoneyTo__ = fil100SearchTradeMoneyTo;
    }
    /**
     * <p>fil100SearchTradeMoneyType を取得します。
     * @return fil100SearchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeMoneyType__
     */
    public String getFil100SearchTradeMoneyType() {
        return fil100SearchTradeMoneyType__;
    }
    /**
     * <p>fil100SearchTradeMoneyType をセットします。
     * @param fil100SearchTradeMoneyType fil100SearchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeMoneyType__
     */
    public void setFil100SearchTradeMoneyType(String fil100SearchTradeMoneyType) {
        fil100SearchTradeMoneyType__ = fil100SearchTradeMoneyType;
    }
    /**
     * <p>fil100SearchTradeMoneyJudge を取得します。
     * @return fil100SearchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeMoneyJudge__
     */
    public String getFil100SearchTradeMoneyJudge() {
        return fil100SearchTradeMoneyJudge__;
    }
    /**
     * <p>fil100SearchTradeMoneyJudge をセットします。
     * @param fil100SearchTradeMoneyJudge fil100SearchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeMoneyJudge__
     */
    public void setFil100SearchTradeMoneyJudge(String fil100SearchTradeMoneyJudge) {
        fil100SearchTradeMoneyJudge__ = fil100SearchTradeMoneyJudge;
    }
    /**
     * <p>fil100SearchTradeMoneyKbn を取得します。
     * @return fil100SearchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeMoneyKbn__
     */
    public String getFil100SearchTradeMoneyKbn() {
        return fil100SearchTradeMoneyKbn__;
    }
    /**
     * <p>fil100SearchTradeMoneyKbn をセットします。
     * @param fil100SearchTradeMoneyKbn fil100SearchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeMoneyKbn__
     */
    public void setFil100SearchTradeMoneyKbn(String fil100SearchTradeMoneyKbn) {
        fil100SearchTradeMoneyKbn__ = fil100SearchTradeMoneyKbn;
    }
    /**
     * <p>fil100SearchTradeDateFrom を取得します。
     * @return fil100SearchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeDateFrom__
     */
    public String getFil100SearchTradeDateFrom() {
        return fil100SearchTradeDateFrom__;
    }
    /**
     * <p>fil100SearchTradeDateFrom をセットします。
     * @param fil100SearchTradeDateFrom fil100SearchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeDateFrom__
     */
    public void setFil100SearchTradeDateFrom(String fil100SearchTradeDateFrom) {
        fil100SearchTradeDateFrom__ = fil100SearchTradeDateFrom;
    }
    /**
     * <p>fil100SearchTradeDateTo を取得します。
     * @return fil100SearchTradeDateTo
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeDateTo__
     */
    public String getFil100SearchTradeDateTo() {
        return fil100SearchTradeDateTo__;
    }
    /**
     * <p>fil100SearchTradeDateTo をセットします。
     * @param fil100SearchTradeDateTo fil100SearchTradeDateTo
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeDateTo__
     */
    public void setFil100SearchTradeDateTo(String fil100SearchTradeDateTo) {
        fil100SearchTradeDateTo__ = fil100SearchTradeDateTo;
    }
    /**
     * <p>fil100SearchTradeDateKbn を取得します。
     * @return fil100SearchTradeDateKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeDateKbn__
     */
    public String getFil100SearchTradeDateKbn() {
        return fil100SearchTradeDateKbn__;
    }
    /**
     * <p>fil100SearchTradeDateKbn をセットします。
     * @param fil100SearchTradeDateKbn fil100SearchTradeDateKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SearchTradeDateKbn__
     */
    public void setFil100SearchTradeDateKbn(String fil100SearchTradeDateKbn) {
        fil100SearchTradeDateKbn__ = fil100SearchTradeDateKbn;
    }

    /**
     * @return fil100SvChkWdTrgText
     */
    public String getFil100SvChkWdTrgText() {
        return fil100SvChkWdTrgText__;
    }

    /**
     * @param fil100SvChkWdTrgText セットする fil100SvChkWdTrgText
     */
    public void setFil100SvChkWdTrgText(String fil100SvChkWdTrgText) {
        fil100SvChkWdTrgText__ = fil100SvChkWdTrgText;
    }

    /**
     * <p>fil100SvSearchTradeTarget を取得します。
     * @return fil100SvSearchTradeTarget
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeTarget__
     */
    public String getFil100SvSearchTradeTarget() {
        return fil100SvSearchTradeTarget__;
    }

    /**
     * <p>fil100SvSearchTradeTarget をセットします。
     * @param fil100SvSearchTradeTarget fil100SvSearchTradeTarget
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeTarget__
     */
    public void setFil100SvSearchTradeTarget(String fil100SvSearchTradeTarget) {
        fil100SvSearchTradeTarget__ = fil100SvSearchTradeTarget;
    }

    /**
     * <p>fil100SvSearchTradeMoney を取得します。
     * @return fil100SvSearchTradeMoney
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeMoney__
     */
    public String getFil100SvSearchTradeMoney() {
        return fil100SvSearchTradeMoney__;
    }

    /**
     * <p>fil100SvSearchTradeMoney をセットします。
     * @param fil100SvSearchTradeMoney fil100SvSearchTradeMoney
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeMoney__
     */
    public void setFil100SvSearchTradeMoney(String fil100SvSearchTradeMoney) {
        fil100SvSearchTradeMoney__ = fil100SvSearchTradeMoney;
    }

    /**
     * <p>fil100SvSearchTradeMoneyTo を取得します。
     * @return fil100SvSearchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SvSearchTradeMoneyTo__
     */
    public String getFil100SvSearchTradeMoneyTo() {
        return fil100SvSearchTradeMoneyTo__;
    }

    /**
     * <p>fil100SvSearchTradeMoneyTo をセットします。
     * @param fil100SvSearchTradeMoneyTo fil100SvSearchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SvSearchTradeMoneyTo__
     */
    public void setFil100SvSearchTradeMoneyTo(String fil100SvSearchTradeMoneyTo) {
        fil100SvSearchTradeMoneyTo__ = fil100SvSearchTradeMoneyTo;
    }

    /**
     * <p>fil100SvSearchTradeMoneyType を取得します。
     * @return fil100SvSearchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeMoneyType__
     */
    public String getFil100SvSearchTradeMoneyType() {
        return fil100SvSearchTradeMoneyType__;
    }

    /**
     * <p>fil100SvSearchTradeMoneyType をセットします。
     * @param fil100SvSearchTradeMoneyType fil100SvSearchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeMoneyType__
     */
    public void setFil100SvSearchTradeMoneyType(
            String fil100SvSearchTradeMoneyType) {
        fil100SvSearchTradeMoneyType__ = fil100SvSearchTradeMoneyType;
    }

    /**
     * <p>fil100SvSearchTradeMoneyJudge を取得します。
     * @return fil100SvSearchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeMoneyJudge__
     */
    public String getFil100SvSearchTradeMoneyJudge() {
        return fil100SvSearchTradeMoneyJudge__;
    }

    /**
     * <p>fil100SvSearchTradeMoneyJudge をセットします。
     * @param fil100SvSearchTradeMoneyJudge fil100SvSearchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeMoneyJudge__
     */
    public void setFil100SvSearchTradeMoneyJudge(
            String fil100SvSearchTradeMoneyJudge) {
        fil100SvSearchTradeMoneyJudge__ = fil100SvSearchTradeMoneyJudge;
    }

    /**
     * <p>fil100SvSearchTradeMoneyKbn を取得します。
     * @return fil100SvSearchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeMoneyKbn__
     */
    public String getFil100SvSearchTradeMoneyKbn() {
        return fil100SvSearchTradeMoneyKbn__;
    }

    /**
     * <p>fil100SvSearchTradeMoneyKbn をセットします。
     * @param fil100SvSearchTradeMoneyKbn fil100SvSearchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeMoneyKbn__
     */
    public void setFil100SvSearchTradeMoneyKbn(String fil100SvSearchTradeMoneyKbn) {
        fil100SvSearchTradeMoneyKbn__ = fil100SvSearchTradeMoneyKbn;
    }

    /**
     * <p>fil100SvSearchTradeDateFrom を取得します。
     * @return fil100SvSearchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeDateFrom__
     */
    public String getFil100SvSearchTradeDateFrom() {
        return fil100SvSearchTradeDateFrom__;
    }

    /**
     * <p>fil100SvSearchTradeDateFrom をセットします。
     * @param fil100SvSearchTradeDateFrom fil100SvSearchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeDateFrom__
     */
    public void setFil100SvSearchTradeDateFrom(String fil100SvSearchTradeDateFrom) {
        fil100SvSearchTradeDateFrom__ = fil100SvSearchTradeDateFrom;
    }

    /**
     * <p>fil100SvSearchTradeDateTo を取得します。
     * @return fil100SvSearchTradeDateTo
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeDateTo__
     */
    public String getFil100SvSearchTradeDateTo() {
        return fil100SvSearchTradeDateTo__;
    }

    /**
     * <p>fil100SvSearchTradeDateTo をセットします。
     * @param fil100SvSearchTradeDateTo fil100SvSearchTradeDateTo
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeDateTo__
     */
    public void setFil100SvSearchTradeDateTo(String fil100SvSearchTradeDateTo) {
        fil100SvSearchTradeDateTo__ = fil100SvSearchTradeDateTo;
    }

    /**
     * <p>fil100SvSearchTradeDateKbn を取得します。
     * @return fil100SvSearchTradeDateKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeDateKbn__
     */
    public String getFil100SvSearchTradeDateKbn() {
        return fil100SvSearchTradeDateKbn__;
    }

    /**
     * <p>fil100SvSearchTradeDateKbn をセットします。
     * @param fil100SvSearchTradeDateKbn fil100SvSearchTradeDateKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100Form#fil100SvSearchTradeDateKbn__
     */
    public void setFil100SvSearchTradeDateKbn(String fil100SvSearchTradeDateKbn) {
        fil100SvSearchTradeDateKbn__ = fil100SvSearchTradeDateKbn;
    }

    /**
     * <p>fil100SearchTradeMoneyNoset を取得します。
     * @return fil100SearchTradeMoneyNoset
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SearchTradeMoneyNoset__
     */
    public String getFil100SearchTradeMoneyNoset() {
        return fil100SearchTradeMoneyNoset__;
    }

    /**
     * <p>fil100SearchTradeMoneyNoset をセットします。
     * @param fil100SearchTradeMoneyNoset fil100SearchTradeMoneyNoset
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SearchTradeMoneyNoset__
     */
    public void setFil100SearchTradeMoneyNoset(String fil100SearchTradeMoneyNoset) {
        fil100SearchTradeMoneyNoset__ = fil100SearchTradeMoneyNoset;
    }

    /**
     * <p>fil100SvSearchTradeMoneyNoset を取得します。
     * @return fil100SvSearchTradeMoneyNoset
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SvSearchTradeMoneyNoset__
     */
    public String getFil100SvSearchTradeMoneyNoset() {
        return fil100SvSearchTradeMoneyNoset__;
    }

    /**
     * <p>fil100SvSearchTradeMoneyNoset をセットします。
     * @param fil100SvSearchTradeMoneyNoset fil100SvSearchTradeMoneyNoset
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100SvSearchTradeMoneyNoset__
     */
    public void setFil100SvSearchTradeMoneyNoset(
            String fil100SvSearchTradeMoneyNoset) {
        fil100SvSearchTradeMoneyNoset__ = fil100SvSearchTradeMoneyNoset;
    }

    /**
     * <p>fil100adminUser を取得します。
     * @return fil100adminUser
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100adminUser__
     */
    public boolean isFil100adminUser() {
        return fil100adminUser__;
    }

    /**
     * <p>fil100adminUser をセットします。
     * @param fil100adminUser fil100adminUser
     * @see jp.groupsession.v2.fil.fil100.Fil100ParamModel#fil100adminUser__
     */
    public void setFil100adminUser(boolean fil100adminUser) {
        fil100adminUser__ = fil100adminUser;
    }
}
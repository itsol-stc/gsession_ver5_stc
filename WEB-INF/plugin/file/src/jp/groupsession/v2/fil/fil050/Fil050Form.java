package jp.groupsession.v2.fil.fil050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil040.Fil040Form;
import jp.groupsession.v2.fil.fil050.model.Fil050Model;
import jp.groupsession.v2.fil.model.FileDAccessUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] フォルダ詳細画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil050Form extends Fil040Form {

    /** ソートする項目 */
    private String fil050SortKey__ = "0";
    /** 昇順・降順 */
    private String fil050OrderKey__ = String.valueOf(GSConst.ORDER_KEY_DESC);
    /** GS管理者権限 有無 */
    private String fil050AdminFlg__ = String.valueOf(GSConst.USER_NOT_ADMIN);

    /** 表示切替用 更新履歴・アクセス制御*/
    private String fil050DspMode__ = GSConstFile.DSP_MODE_HIST;
    /** ディレクトリSID */
    private String fil050DirSid__ = null;
    /** 親ディレクトリSID */
    private String fil050ParentDirSid__ = null;
    /** フォルダパス */
    private String fil050FolderPath__ = null;
    /** 備考 */
    private String fil050Biko__ = null;
    /** ショートカット有無 */
    private String fil050ShortcutKbn__ = null;
    /** ショートカットパス */
    private String fil050ShortcutPath__ = null;
    /** 更新通知有無 */
    private String fil050CallKbn__ = null;
    /** 更新通知 一括反映区分 */
    private int fil050CallLevelKbn__ = GSConstFile.CALL_LEVEL_OFF;
    /** ファイルコンボ */
    private List<LabelValueBean> fil050FileLabelList__ = null;
    /** 履歴　選択したディレクトリSID */
    private String fil050SltDirSid__ = null;
    /** 履歴　選択したディレクトリバージョン */
    private String fil050SltDirVer__ = null;
    /** ログインユーザ編集権限区分 */
    private String fil050EditAuthKbn__ = null;
    /** 復旧表示区分 */
    private String fil050RepairDspFlg__ = null;

    /** 更新履歴一覧 */
    private List<Fil050Model> fil050RekiList__ = null;
    /** アクセス制御一覧 */
    private List<FileDAccessUserModel> fil050AccessList__ = null;
    /** ページ1 */
    private int fil050PageNum1__ = 1;
    /** ページ2 */
    private int fil050PageNum2__ = 1;
    /** ページラベル */
    ArrayList < LabelValueBean > fil050PageLabel__;

    /** 履歴　選択したディレクトリバージョン */
    private String fil050FolderUrl__ = null;

    //詳細検索画面のパラメータ

    /** 抽出対象　キャビネット */
    private String fil100SltCabinetSid__ = null;
    /** 抽出対象　フォルダ */
    private String fil100ChkTrgFolder__ = "0";
    /** 抽出対象　ファイル */
    private String fil100ChkTrgFile__ = "0";
    /** キーワード AND OR */
    private String fil100SearchMode__ = String.valueOf(GSConstFile.KEY_WORD_KBN_AND);
    /** キーワード検索対象 名前 */
    private String fil100ChkWdTrgName__ = "0";
    /** キーワード検索対象 備考 */
    private String fil100ChkWdTrgBiko__ = "0";
    /** キーワード検索対象 ファイル内容 */
    private String fil100ChkWdTrgText__ = "0";
    /** 更新日　日時指定区分 */
    private String fil100ChkOnOff__ = String.valueOf(GSConstFile.UPDATE_KBN_OK);
    /** 検索実行フラグ */
    private int searchFlg__ = GSConstFile.SEARCH_EXECUTE_TRUE;
    /** ページ1 */
    private int fil100pageNum1__ = 1;
    /** ページ2 */
    private int fil100pageNum2__ = 1;
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
    /** ソートキー */
    private int fil100sortKey__ = GSConstFile.SORT_NAME;
    /** オーダーキー */
    private int fil100orderKey__ = GSConstFile.ORDER_KEY_ASC;

    /** 抽出対象　キャビネット */
    private int fil100SltCabinetKbn__ = GSConstFile.CABINET_KBN_PUBLIC;
    /** 抽出対象　削除済みファイル */
    private String fil100ChkTrgDeleted__ = "0";
    /** 抽出対象　削除済みフォルダ */
    private String fil100ChkTrgDeletedFolder__ = "0";
    /** 検索 取引先 */
    private String fil100SearchTradeTarget__ = null;
    /** 検索 取引金額 */
    private String fil100SearchTradeMoney__ = null;
    /** 検索 取引金額to (判定条件に"から"が設定されている場合の未使用) */
    private String fil100SearchTradeMoneyTo__ = null;
    /** 検索 取引金額 外貨*/
    private String fil100SearchTradeMoneyType__ = null;
    /** 検索 取引金額 判定条件*/
    private String fil100SearchTradeMoneyJudge__ = null;
    /** 検索 取引金額 指定有りなし */
    private String fil100SearchTradeMoneyNoset__ = String.valueOf(GSConstFile.SEARCH_USE);
    /** 検索 取引金額 金額無し/有り */
    private String fil100SearchTradeMoneyKbn__ = "0";
    /** 検索 取引年月日 from*/
    private String fil100SearchTradeDateFrom__ = null;
    /** 検索 取引年月日 to */
    private String fil100SearchTradeDateTo__ = null;
    /** 検索 取引年月日 指定有りなし*/
    private String fil100SearchTradeDateKbn__ = String.valueOf(GSConstFile.SEARCH_USE);

    /** キャビネットSID */
    private String fil100SvSltCabinetSid__ = null;
    /** 抽出対象　フォルダ */
    private String fil100SvChkTrgFolder__ = String.valueOf(GSConstFile.GET_TARGET_FOLDER);
    /** 抽出対象　ファイル */
    private String fil100SvChkTrgFile__ = String.valueOf(GSConstFile.GET_TARGET_FILE);
    /** 抽出対象　削除済みファイル */
    private String fil100SvChkTrgDeleted__ = String.valueOf(GSConstFile.GET_TARGET_DELETED);
    /** 抽出対象　削除済みフォルダ */
    private String fil100SvChkTrgDeletedFolder__ = String.valueOf(GSConstFile.GET_TARGET_DELETED);
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
    private String fil100SvSearchTradeMoneyKbn__ = "0";
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
    /** 更新日　指定区分 */
    private String fil100SvChkOnOff__ = String.valueOf(GSConstFile.UPDATE_KBN_NO);

    /** ディレクトリ 削除区分 */
    private boolean logicalDelKbn__ = false;

    /**
     * <p>fil050PageLabel を取得します。
     * @return fil050PageLabel
     */
    public ArrayList<LabelValueBean> getFil050PageLabel() {
        return fil050PageLabel__;
    }
    /**
     * <p>fil050PageLabel をセットします。
     * @param fil050PageLabel fil050PageLabel
     */
    public void setFil050PageLabel(ArrayList<LabelValueBean> fil050PageLabel) {
        fil050PageLabel__ = fil050PageLabel;
    }
    /**
     * <p>fil050PageNum1 を取得します。
     * @return fil050PageNum1
     */
    public int getFil050PageNum1() {
        return fil050PageNum1__;
    }
    /**
     * <p>fil050PageNum1 をセットします。
     * @param fil050PageNum1 fil050PageNum1
     */
    public void setFil050PageNum1(int fil050PageNum1) {
        fil050PageNum1__ = fil050PageNum1;
    }
    /**
     * <p>fil050PageNum2 を取得します。
     * @return fil050PageNum2
     */
    public int getFil050PageNum2() {
        return fil050PageNum2__;
    }
    /**
     * <p>fil050PageNum2 をセットします。
     * @param fil050PageNum2 fil050PageNum2
     */
    public void setFil050PageNum2(int fil050PageNum2) {
        fil050PageNum2__ = fil050PageNum2;
    }
    /**
     * <p>fil050Biko を取得します。
     * @return fil050Biko
     */
    public String getFil050Biko() {
        return fil050Biko__;
    }
    /**
     * <p>fil050Biko をセットします。
     * @param fil050Biko fil050Biko
     */
    public void setFil050Biko(String fil050Biko) {
        fil050Biko__ = fil050Biko;
    }
    /**
     * <p>fil050FolderPath を取得します。
     * @return fil050FolderPath
     */
    public String getFil050FolderPath() {
        return fil050FolderPath__;
    }
    /**
     * <p>fil050FolderPath をセットします。
     * @param fil050FolderPath fil050FolderPath
     */
    public void setFil050FolderPath(String fil050FolderPath) {
        fil050FolderPath__ = fil050FolderPath;
    }
    /**
     * <p>fil050AdminFlg を取得します。
     * @return fil050AdminFlg
     */
    public String getFil050AdminFlg() {
        return fil050AdminFlg__;
    }
    /**
     * <p>fil050AdminFlg をセットします。
     * @param fil050AdminFlg fil050AdminFlg
     */
    public void setFil050AdminFlg(String fil050AdminFlg) {
        fil050AdminFlg__ = fil050AdminFlg;
    }
    /**
     * <p>fil050DspMode を取得します。
     * @return fil050DspMode
     */
    public String getFil050DspMode() {
        return fil050DspMode__;
    }
    /**
     * <p>fil050DspMode をセットします。
     * @param fil050DspMode fil050DspMode
     */
    public void setFil050DspMode(String fil050DspMode) {
        this.fil050DspMode__ = fil050DspMode;
    }
    /**
     * <p>fil050OrderKey を取得します。
     * @return fil050OrderKey
     */
    public String getFil050OrderKey() {
        return fil050OrderKey__;
    }
    /**
     * <p>fil050OrderKey をセットします。
     * @param fil050OrderKey fil050OrderKey
     */
    public void setFil050OrderKey(String fil050OrderKey) {
        fil050OrderKey__ = fil050OrderKey;
    }
    /**
     * <p>fil050SortKey を取得します。
     * @return fil050SortKey
     */
    public String getFil050SortKey() {
        return fil050SortKey__;
    }
    /**
     * <p>fil050SortKey をセットします。
     * @param fil050SortKey fil050SortKey
     */
    public void setFil050SortKey(String fil050SortKey) {
        fil050SortKey__ = fil050SortKey;
    }

    /**
     * <p>fil050CallKbn を取得します。
     * @return fil050CallKbn
     */
    public String getFil050CallKbn() {
        return fil050CallKbn__;
    }
    /**
     * <p>fil050CallKbn をセットします。
     * @param fil050CallKbn fil050CallKbn
     */
    public void setFil050CallKbn(String fil050CallKbn) {
        fil050CallKbn__ = fil050CallKbn;
    }
    /**
     * <p>fil050ShortcutPath を取得します。
     * @return fil050ShortcutPath
     */
    public String getFil050ShortcutPath() {
        return fil050ShortcutPath__;
    }
    /**
     * <p>fil050ShortcutPath をセットします。
     * @param fil050ShortcutPath fil050ShortcutPath
     */
    public void setFil050ShortcutPath(String fil050ShortcutPath) {
        fil050ShortcutPath__ = fil050ShortcutPath;
    }
    /**
     * <p>fil050ShortcutKbn を取得します。
     * @return fil050ShortcutKbn
     */
    public String getFil050ShortcutKbn() {
        return fil050ShortcutKbn__;
    }
    /**
     * <p>fil050ShortcutKbn をセットします。
     * @param fil050ShortcutKbn fil050ShortcutKbn
     */
    public void setFil050ShortcutKbn(String fil050ShortcutKbn) {
        fil050ShortcutKbn__ = fil050ShortcutKbn;
    }
    /**
     * <p>fil050RekiList を取得します。
     * @return fil050RekiList
     */
    public List<Fil050Model> getFil050RekiList() {
        return fil050RekiList__;
    }
    /**
     * <p>fil050RekiList をセットします。
     * @param fil050RekiList fil050RekiList
     */
    public void setFil050RekiList(List<Fil050Model> fil050RekiList) {
        fil050RekiList__ = fil050RekiList;
    }
    /**
     * <p>fil050AccessList を取得します。
     * @return fil050AccessList
     */
    public List<FileDAccessUserModel> getFil050AccessList() {
        return fil050AccessList__;
    }
    /**
     * <p>fil050AccessList をセットします。
     * @param fil050AccessList fil050AccessList
     */
    public void setFil050AccessList(List<FileDAccessUserModel> fil050AccessList) {
        this.fil050AccessList__ = fil050AccessList;
    }
    /**
     * <p>fil050SltDirSid を取得します。
     * @return fil050SltDirSid
     */
    public String getFil050SltDirSid() {
        return fil050SltDirSid__;
    }
    /**
     * <p>fil050SltDirSid をセットします。
     * @param fil050SltDirSid fil050SltDirSid
     */
    public void setFil050SltDirSid(String fil050SltDirSid) {
        fil050SltDirSid__ = fil050SltDirSid;
    }
    /**
     * <p>fil050SltDirVer を取得します。
     * @return fil050SltDirVer
     */
    public String getFil050SltDirVer() {
        return fil050SltDirVer__;
    }
    /**
     * <p>fil050SltDirVer をセットします。
     * @param fil050SltDirVer fil050SltDirVer
     */
    public void setFil050SltDirVer(String fil050SltDirVer) {
        fil050SltDirVer__ = fil050SltDirVer;
    }
    /**
     * <p>fil050FileLabelList を取得します。
     * @return fil050FileLabelList
     */
    public List<LabelValueBean> getFil050FileLabelList() {
        return fil050FileLabelList__;
    }
    /**
     * <p>fil050FileLabelList をセットします。
     * @param fil050FileLabelList fil050FileLabelList
     */
    public void setFil050FileLabelList(List<LabelValueBean> fil050FileLabelList) {
        fil050FileLabelList__ = fil050FileLabelList;
    }
    /**
     * <p>fil050DirSid を取得します。
     * @return fil050DirSid
     */
    public String getFil050DirSid() {
        return fil050DirSid__;
    }
    /**
     * <p>fil050DirSid をセットします。
     * @param fil050DirSid fil050DirSid
     */
    public void setFil050DirSid(String fil050DirSid) {
        fil050DirSid__ = fil050DirSid;
    }
    /**
     * <p>fil050FolderUrl を取得します。
     * @return fil050FolderUrl
     */
    public String getFil050FolderUrl() {
        return fil050FolderUrl__;
    }
    /**
     * <p>fil050FolderUrl をセットします。
     * @param fil050FolderUrl fil050FolderUrl
     */
    public void setFil050FolderUrl(String fil050FolderUrl) {
        fil050FolderUrl__ = fil050FolderUrl;
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
     * <p>fil100SvChkTrgDeleted を取得します。
     * @return fil100SvChkTrgDeleted
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvChkTrgDeleted__
     */
    public String getFil100SvChkTrgDeleted() {
        return fil100SvChkTrgDeleted__;
    }
    /**
     * <p>fil100SvChkTrgDeleted をセットします。
     * @param fil100SvChkTrgDeleted fil100SvChkTrgDeleted
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvChkTrgDeleted__
     */
    public void setFil100SvChkTrgDeleted(String fil100SvChkTrgDeleted) {
        fil100SvChkTrgDeleted__ = fil100SvChkTrgDeleted;
    }
    /**
     * <p>fil100SvChkTrgDeletedFolder を取得します。
     * @return fil100SvChkTrgDeletedFolder
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvChkTrgDeletedFolder__
     */
    public String getFil100SvChkTrgDeletedFolder() {
        return fil100SvChkTrgDeletedFolder__;
    }
    /**
     * <p>fil100SvChkTrgDeletedFolder をセットします。
     * @param fil100SvChkTrgDeletedFolder fil100SvChkTrgDeletedFolder
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvChkTrgDeletedFolder__
     */
    public void setFil100SvChkTrgDeletedFolder(String fil100SvChkTrgDeletedFolder) {
        fil100SvChkTrgDeletedFolder__ = fil100SvChkTrgDeletedFolder;
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
     * <p>fil100SvSearchTradeTarget を取得します。
     * @return fil100SvSearchTradeTarget
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeTarget__
     */
    public String getFil100SvSearchTradeTarget() {
        return fil100SvSearchTradeTarget__;
    }
    /**
     * <p>fil100SvSearchTradeTarget をセットします。
     * @param fil100SvSearchTradeTarget fil100SvSearchTradeTarget
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeTarget__
     */
    public void setFil100SvSearchTradeTarget(String fil100SvSearchTradeTarget) {
        fil100SvSearchTradeTarget__ = fil100SvSearchTradeTarget;
    }
    /**
     * <p>fil100SvSearchTradeMoney を取得します。
     * @return fil100SvSearchTradeMoney
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoney__
     */
    public String getFil100SvSearchTradeMoney() {
        return fil100SvSearchTradeMoney__;
    }
    /**
     * <p>fil100SvSearchTradeMoney をセットします。
     * @param fil100SvSearchTradeMoney fil100SvSearchTradeMoney
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoney__
     */
    public void setFil100SvSearchTradeMoney(String fil100SvSearchTradeMoney) {
        fil100SvSearchTradeMoney__ = fil100SvSearchTradeMoney;
    }
    /**
     * <p>fil100SvSearchTradeMoneyTo を取得します。
     * @return fil100SvSearchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyTo__
     */
    public String getFil100SvSearchTradeMoneyTo() {
        return fil100SvSearchTradeMoneyTo__;
    }
    /**
     * <p>fil100SvSearchTradeMoneyTo をセットします。
     * @param fil100SvSearchTradeMoneyTo fil100SvSearchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyTo__
     */
    public void setFil100SvSearchTradeMoneyTo(String fil100SvSearchTradeMoneyTo) {
        fil100SvSearchTradeMoneyTo__ = fil100SvSearchTradeMoneyTo;
    }
    /**
     * <p>fil100SvSearchTradeMoneyType を取得します。
     * @return fil100SvSearchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyType__
     */
    public String getFil100SvSearchTradeMoneyType() {
        return fil100SvSearchTradeMoneyType__;
    }
    /**
     * <p>fil100SvSearchTradeMoneyType をセットします。
     * @param fil100SvSearchTradeMoneyType fil100SvSearchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyType__
     */
    public void setFil100SvSearchTradeMoneyType(
            String fil100SvSearchTradeMoneyType) {
        fil100SvSearchTradeMoneyType__ = fil100SvSearchTradeMoneyType;
    }
    /**
     * <p>fil100SvSearchTradeMoneyJudge を取得します。
     * @return fil100SvSearchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyJudge__
     */
    public String getFil100SvSearchTradeMoneyJudge() {
        return fil100SvSearchTradeMoneyJudge__;
    }
    /**
     * <p>fil100SvSearchTradeMoneyJudge をセットします。
     * @param fil100SvSearchTradeMoneyJudge fil100SvSearchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyJudge__
     */
    public void setFil100SvSearchTradeMoneyJudge(
            String fil100SvSearchTradeMoneyJudge) {
        fil100SvSearchTradeMoneyJudge__ = fil100SvSearchTradeMoneyJudge;
    }
    /**
     * <p>fil100SvSearchTradeMoneyKbn を取得します。
     * @return fil100SvSearchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyKbn__
     */
    public String getFil100SvSearchTradeMoneyKbn() {
        return fil100SvSearchTradeMoneyKbn__;
    }
    /**
     * <p>fil100SvSearchTradeMoneyKbn をセットします。
     * @param fil100SvSearchTradeMoneyKbn fil100SvSearchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyKbn__
     */
    public void setFil100SvSearchTradeMoneyKbn(String fil100SvSearchTradeMoneyKbn) {
        fil100SvSearchTradeMoneyKbn__ = fil100SvSearchTradeMoneyKbn;
    }
    /**
     * <p>fil100SearchTradeMoneyNoset を取得します。
     * @return fil100SearchTradeMoneyNoset
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyNoset__
     */
    public String getFil100SearchTradeMoneyNoset() {
        return fil100SearchTradeMoneyNoset__;
    }
    /**
     * <p>fil100SearchTradeMoneyNoset をセットします。
     * @param fil100SearchTradeMoneyNoset fil100SearchTradeMoneyNoset
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyNoset__
     */
    public void setFil100SearchTradeMoneyNoset(String fil100SearchTradeMoneyNoset) {
        fil100SearchTradeMoneyNoset__ = fil100SearchTradeMoneyNoset;
    }
    /**
     * <p>fil100SvSearchTradeMoneyNoset を取得します。
     * @return fil100SvSearchTradeMoneyNoset
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyNoset__
     */
    public String getFil100SvSearchTradeMoneyNoset() {
        return fil100SvSearchTradeMoneyNoset__;
    }
    /**
     * <p>fil100SvSearchTradeMoneyNoset をセットします。
     * @param fil100SvSearchTradeMoneyNoset fil100SvSearchTradeMoneyNoset
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeMoneyNoset__
     */
    public void setFil100SvSearchTradeMoneyNoset(
            String fil100SvSearchTradeMoneyNoset) {
        fil100SvSearchTradeMoneyNoset__ = fil100SvSearchTradeMoneyNoset;
    }
    /**
     * <p>fil100SvSearchTradeDateFrom を取得します。
     * @return fil100SvSearchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeDateFrom__
     */
    public String getFil100SvSearchTradeDateFrom() {
        return fil100SvSearchTradeDateFrom__;
    }
    /**
     * <p>fil100SvSearchTradeDateFrom をセットします。
     * @param fil100SvSearchTradeDateFrom fil100SvSearchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeDateFrom__
     */
    public void setFil100SvSearchTradeDateFrom(String fil100SvSearchTradeDateFrom) {
        fil100SvSearchTradeDateFrom__ = fil100SvSearchTradeDateFrom;
    }
    /**
     * <p>fil100SvSearchTradeDateTo を取得します。
     * @return fil100SvSearchTradeDateTo
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeDateTo__
     */
    public String getFil100SvSearchTradeDateTo() {
        return fil100SvSearchTradeDateTo__;
    }
    /**
     * <p>fil100SvSearchTradeDateTo をセットします。
     * @param fil100SvSearchTradeDateTo fil100SvSearchTradeDateTo
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeDateTo__
     */
    public void setFil100SvSearchTradeDateTo(String fil100SvSearchTradeDateTo) {
        fil100SvSearchTradeDateTo__ = fil100SvSearchTradeDateTo;
    }
    /**
     * <p>fil100SvSearchTradeDateKbn を取得します。
     * @return fil100SvSearchTradeDateKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeDateKbn__
     */
    public String getFil100SvSearchTradeDateKbn() {
        return fil100SvSearchTradeDateKbn__;
    }
    /**
     * <p>fil100SvSearchTradeDateKbn をセットします。
     * @param fil100SvSearchTradeDateKbn fil100SvSearchTradeDateKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SvSearchTradeDateKbn__
     */
    public void setFil100SvSearchTradeDateKbn(String fil100SvSearchTradeDateKbn) {
        fil100SvSearchTradeDateKbn__ = fil100SvSearchTradeDateKbn;
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
     * <p>fil100SltCabinetKbn を取得します。
     * @return fil100SltCabinetKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SltCabinetKbn__
     */
    public int getFil100SltCabinetKbn() {
        return fil100SltCabinetKbn__;
    }
    /**
     * <p>fil100SltCabinetKbn をセットします。
     * @param fil100SltCabinetKbn fil100SltCabinetKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SltCabinetKbn__
     */
    public void setFil100SltCabinetKbn(int fil100SltCabinetKbn) {
        fil100SltCabinetKbn__ = fil100SltCabinetKbn;
    }
    /**
     * <p>fil100ChkTrgDeleted を取得します。
     * @return fil100ChkTrgDeleted
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100ChkTrgDeleted__
     */
    public String getFil100ChkTrgDeleted() {
        return fil100ChkTrgDeleted__;
    }
    /**
     * <p>fil100ChkTrgDeleted をセットします。
     * @param fil100ChkTrgDeleted fil100ChkTrgDeleted
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100ChkTrgDeleted__
     */
    public void setFil100ChkTrgDeleted(String fil100ChkTrgDeleted) {
        fil100ChkTrgDeleted__ = fil100ChkTrgDeleted;
    }
    /**
     * <p>fil100ChkTrgDeletedFolder を取得します。
     * @return fil100ChkTrgDeletedFolder
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100ChkTrgDeletedFolder__
     */
    public String getFil100ChkTrgDeletedFolder() {
        return fil100ChkTrgDeletedFolder__;
    }
    /**
     * <p>fil100ChkTrgDeletedFolder をセットします。
     * @param fil100ChkTrgDeletedFolder fil100ChkTrgDeletedFolder
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100ChkTrgDeletedFolder__
     */
    public void setFil100ChkTrgDeletedFolder(String fil100ChkTrgDeletedFolder) {
        fil100ChkTrgDeletedFolder__ = fil100ChkTrgDeletedFolder;
    }
    /**
     * <p>fil100SearchTradeTarget を取得します。
     * @return fil100SearchTradeTarget
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeTarget__
     */
    public String getFil100SearchTradeTarget() {
        return fil100SearchTradeTarget__;
    }
    /**
     * <p>fil100SearchTradeTarget をセットします。
     * @param fil100SearchTradeTarget fil100SearchTradeTarget
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeTarget__
     */
    public void setFil100SearchTradeTarget(String fil100SearchTradeTarget) {
        fil100SearchTradeTarget__ = fil100SearchTradeTarget;
    }
    /**
     * <p>fil100SearchTradeMoney を取得します。
     * @return fil100SearchTradeMoney
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoney__
     */
    public String getFil100SearchTradeMoney() {
        return fil100SearchTradeMoney__;
    }
    /**
     * <p>fil100SearchTradeMoney をセットします。
     * @param fil100SearchTradeMoney fil100SearchTradeMoney
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoney__
     */
    public void setFil100SearchTradeMoney(String fil100SearchTradeMoney) {
        fil100SearchTradeMoney__ = fil100SearchTradeMoney;
    }
    /**
     * <p>fil100SearchTradeMoneyTo を取得します。
     * @return fil100SearchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyTo__
     */
    public String getFil100SearchTradeMoneyTo() {
        return fil100SearchTradeMoneyTo__;
    }
    /**
     * <p>fil100SearchTradeMoneyTo をセットします。
     * @param fil100SearchTradeMoneyTo fil100SearchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyTo__
     */
    public void setFil100SearchTradeMoneyTo(String fil100SearchTradeMoneyTo) {
        fil100SearchTradeMoneyTo__ = fil100SearchTradeMoneyTo;
    }
    /**
     * <p>fil100SearchTradeMoneyType を取得します。
     * @return fil100SearchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyType__
     */
    public String getFil100SearchTradeMoneyType() {
        return fil100SearchTradeMoneyType__;
    }
    /**
     * <p>fil100SearchTradeMoneyType をセットします。
     * @param fil100SearchTradeMoneyType fil100SearchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyType__
     */
    public void setFil100SearchTradeMoneyType(String fil100SearchTradeMoneyType) {
        fil100SearchTradeMoneyType__ = fil100SearchTradeMoneyType;
    }
    /**
     * <p>fil100SearchTradeMoneyJudge を取得します。
     * @return fil100SearchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyJudge__
     */
    public String getFil100SearchTradeMoneyJudge() {
        return fil100SearchTradeMoneyJudge__;
    }
    /**
     * <p>fil100SearchTradeMoneyJudge をセットします。
     * @param fil100SearchTradeMoneyJudge fil100SearchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyJudge__
     */
    public void setFil100SearchTradeMoneyJudge(String fil100SearchTradeMoneyJudge) {
        fil100SearchTradeMoneyJudge__ = fil100SearchTradeMoneyJudge;
    }
    /**
     * <p>fil100SearchTradeMoneyKbn を取得します。
     * @return fil100SearchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyKbn__
     */
    public String getFil100SearchTradeMoneyKbn() {
        return fil100SearchTradeMoneyKbn__;
    }
    /**
     * <p>fil100SearchTradeMoneyKbn をセットします。
     * @param fil100SearchTradeMoneyKbn fil100SearchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeMoneyKbn__
     */
    public void setFil100SearchTradeMoneyKbn(String fil100SearchTradeMoneyKbn) {
        fil100SearchTradeMoneyKbn__ = fil100SearchTradeMoneyKbn;
    }
    /**
     * <p>fil100SearchTradeDateFrom を取得します。
     * @return fil100SearchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeDateFrom__
     */
    public String getFil100SearchTradeDateFrom() {
        return fil100SearchTradeDateFrom__;
    }
    /**
     * <p>fil100SearchTradeDateFrom をセットします。
     * @param fil100SearchTradeDateFrom fil100SearchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeDateFrom__
     */
    public void setFil100SearchTradeDateFrom(String fil100SearchTradeDateFrom) {
        fil100SearchTradeDateFrom__ = fil100SearchTradeDateFrom;
    }
    /**
     * <p>fil100SearchTradeDateTo を取得します。
     * @return fil100SearchTradeDateTo
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeDateTo__
     */
    public String getFil100SearchTradeDateTo() {
        return fil100SearchTradeDateTo__;
    }
    /**
     * <p>fil100SearchTradeDateTo をセットします。
     * @param fil100SearchTradeDateTo fil100SearchTradeDateTo
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeDateTo__
     */
    public void setFil100SearchTradeDateTo(String fil100SearchTradeDateTo) {
        fil100SearchTradeDateTo__ = fil100SearchTradeDateTo;
    }
    /**
     * <p>fil100SearchTradeDateKbn を取得します。
     * @return fil100SearchTradeDateKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeDateKbn__
     */
    public String getFil100SearchTradeDateKbn() {
        return fil100SearchTradeDateKbn__;
    }
    /**
     * <p>fil100SearchTradeDateKbn をセットします。
     * @param fil100SearchTradeDateKbn fil100SearchTradeDateKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#fil100SearchTradeDateKbn__
     */
    public void setFil100SearchTradeDateKbn(String fil100SearchTradeDateKbn) {
        fil100SearchTradeDateKbn__ = fil100SearchTradeDateKbn;
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
     * <p>fil050ParentDirSid を取得します。
     * @return fil050ParentDirSid
     */
    public String getFil050ParentDirSid() {
        return fil050ParentDirSid__;
    }
    /**
     * <p>fil050ParentDirSid をセットします。
     * @param fil050ParentDirSid fil050ParentDirSid
     */
    public void setFil050ParentDirSid(String fil050ParentDirSid) {
        fil050ParentDirSid__ = fil050ParentDirSid;
    }
    /**
     * <p>fil050EditAuthKbn を取得します。
     * @return fil050EditAuthKbn
     */
    public String getFil050EditAuthKbn() {
        return fil050EditAuthKbn__;
    }
    /**
     * <p>fil050EditAuthKbn をセットします。
     * @param fil050EditAuthKbn fil050EditAuthKbn
     */
    public void setFil050EditAuthKbn(String fil050EditAuthKbn) {
        fil050EditAuthKbn__ = fil050EditAuthKbn;
    }
    /**
     * <p>fil050RepairDspFlg を取得します。
     * @return fil050RepairDspFlg
     */
    public String getFil050RepairDspFlg() {
        return fil050RepairDspFlg__;
    }
    /**
     * <p>fil050RepairDspFlg をセットします。
     * @param fil050RepairDspFlg fil050RepairDspFlg
     */
    public void setFil050RepairDspFlg(String fil050RepairDspFlg) {
        this.fil050RepairDspFlg__ = fil050RepairDspFlg;
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
     * <p>fil050CallLevelKbn を取得します。
     * @return fil050CallLevelKbn
     */
    public int getFil050CallLevelKbn() {
        return fil050CallLevelKbn__;
    }
    /**
     * <p>fil050CallLevelKbn をセットします。
     * @param fil050CallLevelKbn fil050CallLevelKbn
     */
    public void setFil050CallLevelKbn(int fil050CallLevelKbn) {
        fil050CallLevelKbn__ = fil050CallLevelKbn;
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
     * <p>cabinetDelKbn を取得します。
     * @return cabinetDelKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#logicalDelKbn__
     */
    public boolean isLogicalDelKbn() {
        return logicalDelKbn__;
    }
    /**
     * <p>cabinetDelKbn をセットします。
     * @param cabinetDelKbn cabinetDelKbn
     * @see jp.groupsession.v2.fil.fil050.Fil050Form#logicalDelKbn__
     */
    public void setLogicalDelKbn(boolean cabinetDelKbn) {
        logicalDelKbn__ = cabinetDelKbn;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説] 復旧ボタンクリック時にチェックする
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return errors エラー
     * @throws NumberFormatException 実行例外
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors fil050RepairCheck(Connection con, RequestModel reqMdl)
            throws NumberFormatException, SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        FilCommonBiz biz = new FilCommonBiz(reqMdl, con);

        //権限チェック
        if (!biz.isDirAccessAuthUser(NullDefault.getInt(getFil010SelectCabinet(), -1),
                                     NullDefault.getInt(fil050SltDirSid__, -1),
                                     Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE),
                                     true)) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            msg = new ActionMessage("error.edit.power.user",
                    gsMsg.getMessage("cmn.edit"),
                    gsMsg.getMessage("fil.12"));

            StrutsUtil.addMessage(errors, msg, "fil050SltDirSid");
        }

        return errors;
    }
}
package jp.groupsession.v2.fil.fil100;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileAconfModel;

/**
 * <br>[機  能] ファイル詳細検索パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil100SearchParameterModel {

    /** キャビネット */
    private int cabinet__ = GSConstCommon.NUM_INIT;
    /** キャビネット区分(共有or個人) */
    private int personalFlg__ = GSConstFile.CABINET_KBN_PUBLIC;
    /** 電帳法対応フラグ */
    private int errlFlg__ = GSConstFile.ERRL_KBN_OFF;
    /** キーワード */
    private List<String> keyWord__;
    /** キーワード検索区分(AND OR) */
    private int wordKbn__ = GSConstCommon.NUM_INIT;
    /** 更新日 from */
    private UDate upFrDate__ = null;
    /** 更新日 to */
    private UDate upToDate__ = null;
    /** 更新日区分 */
    private int updateKbn__ = GSConstCommon.NUM_INIT;
    /** 対象(フォルダ) */
    private String targetFolder__ = null;
    /** 対象(ファイル) */
    private String targetFile__ = null;
    /** 対象(削除済みファイル) */
    private String targetDeletedFile__ = null;
    /** 対象(削除済みフォルダ) */
    private String targetDeletedFolder__ = null;

    /** 検索対象(フォルダ・ファイル名) */
    private boolean srchTargetName__ = false;
    /** 検索対象(備考) */
    private boolean srchTargetBiko__ = false;
    /** 検索対象(備考) */
    private boolean srchTargetText__ = false;
    /** 1ページの件数 */
    private int limit__ = 0;
    /** カーソルスタート位置 */
    private int start__ = 0;
    /** ユーザSID */
    private int usrSid__ = 0;
    
    /** 取引先 */
    private String srchTradeTargetName__ = null;
    /** 取引金額 */
    private BigDecimal srchTradeMoney__ = null;
    /** 取引金額 to (判定条件に "から"が選択されている場合のみ使用) */
    private BigDecimal srchTradeMoneyTo__ = null;
    /** 取引金額 金額有りなし区分 */
    private int srchTradeMoneyKbn__ = GSConstFile.MONEY_KBN_NOSEARCH;

    /** 取引金額 外貨 */
    private int srchTradeMoneyType__;
    /** 取引金額 判定条件 */
    private int srchTradeMoneyJudge__ = GSConstFile.MONEY_JUEDGE_EQUAL;
    /** 取引年月日 指定なしフラグ */
    private int srchTradeDateFlg__ = GSConstFile.SEARCH_NON;
    /** 取引年月日 from */
    private UDate srchTradeDateFrom__ = null;
    /** 取引年月日 to */
    private UDate srchTradeDateTo__ = null;

    /** ソートキー */
    private int searchSortKey__ = GSConstFile.SORT_NAME;
    /** オーダーキー */
    private int searchOrderKey__ = GSConstFile.ORDER_KEY_ASC;

    /** 全文検索使用フラグ */
    private boolean searchFlg__ = false;

    /** 設定情報モデル */
    private FileAconfModel aconfMdl__ = null;

    /** 削除キャビネット選択 */
    private boolean delCabinetFlg__ = false;

    /**
     * <br>[機  能] フォルダ・ファイル名キーワードを設定する
     * <br>[解  説] スペース区切りで複数のキーワードを設定する
     * <br>[備  考]
     * @param textWordName フォルダ・ファイル名キーワード
     */
    public void setTextWordName(String textWordName) {

        CommonBiz cBiz = new CommonBiz();
        setKeyWord(cBiz.setKeyword(textWordName));
    }
    /**
     * <br>[機  能] 備考キーワードを設定する
     * <br>[解  説] スペース区切りで複数のキーワードを設定する
     * <br>[備  考]
     * @param textWordBiko 備考キーワード
     */
    public void setTextWordBiko(String textWordBiko) {

        CommonBiz cBiz = new CommonBiz();
        setKeyWord(cBiz.setKeyword(textWordBiko));
    }
    /**
     * <br>[機  能] ファイル内容キーワードを設定する
     * <br>[解  説] スペース区切りで複数のキーワードを設定する
     * <br>[備  考]
     * @param textWordText ファイル内容キーワード
     */
    public void setTextWordText(String textWordText) {

        CommonBiz cBiz = new CommonBiz();
        setKeyWord(cBiz.setKeyword(textWordText));
    }

    /**
     * <p>searchOrderKey を取得します。
     * @return searchOrderKey
     */
    public int getSearchOrderKey() {
        return searchOrderKey__;
    }

    /**
     * <p>searchOrderKey をセットします。
     * @param searchOrderKey searchOrderKey
     */
    public void setSearchOrderKey(int searchOrderKey) {
        searchOrderKey__ = searchOrderKey;
    }

    /**
     * <p>searchSortKey を取得します。
     * @return searchSortKey
     */
    public int getSearchSortKey() {
        return searchSortKey__;
    }

    /**
     * <p>searchSortKey をセットします。
     * @param searchSortKey searchSortKey
     */
    public void setSearchSortKey(int searchSortKey) {
        searchSortKey__ = searchSortKey;
    }
    /**
     * @return cabinet
     */
    public int getCabinet() {
        return cabinet__;
    }

    /**
     * @param cabinet 設定する cabinet
     */
    public void setCabinet(int cabinet) {
        cabinet__ = cabinet;
    }

    /**
     * @return personalFlg
     */
    public int getPersonalFlg() {
        return personalFlg__;
    }

    /**
     * @param personalFlg 設定する personalFlg
     */
    public void setPersonalFlg(int personalFlg) {
        personalFlg__ = personalFlg;
    }
    
    /**
     * <p>errlFlg を取得します。
     * @return errlFlg
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#errlFlg__
     */
    public int getErrlFlg() {
        return errlFlg__;
    }
    /**
     * <p>errlFlg をセットします。
     * @param errlFlg errlFlg
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#errlFlg__
     */
    public void setErrlFlg(int errlFlg) {
        errlFlg__ = errlFlg;
    }

    /**
     * @return keyWord
     */
    public List<String> getKeyWord() {
        return keyWord__;
    }

    /**
     * @param keyWord 設定する keyWord
     */
    public void setKeyWord(List<String> keyWord) {
        keyWord__ = keyWord;
    }

    /**
     * @return wordKbn
     */
    public int getWordKbn() {
        return wordKbn__;
    }

    /**
     * @param wordKbn 設定する wordKbn
     */
    public void setWordKbn(int wordKbn) {
        wordKbn__ = wordKbn;
    }

    /**
     * @return upFrDate
     */
    public UDate getUpFrDate() {
        return upFrDate__;
    }

    /**
     * @param upFrDate 設定する upFrDate
     */
    public void setUpFrDate(UDate upFrDate) {
        upFrDate__ = upFrDate;
    }

    /**
     * @return upToDate
     */
    public UDate getUpToDate() {
        return upToDate__;
    }

    /**
     * @param upToDate 設定する upToDate
     */
    public void setUpToDate(UDate upToDate) {
        upToDate__ = upToDate;
    }

    /**
     * @return updateKbn
     */
    public int getUpdateKbn() {
        return updateKbn__;
    }

    /**
     * @param updateKbn 設定する updateKbn
     */
    public void setUpdateKbn(int updateKbn) {
        updateKbn__ = updateKbn;
    }

    /**
     * @return targetFolder
     */
    public String getTargetFolder() {
        return targetFolder__;
    }

    /**
     * @param targetFolder 設定する targetFolder
     */
    public void setTargetFolder(String targetFolder) {
        targetFolder__ = targetFolder;
    }

    /**
     * @return targetFile
     */
    public String getTargetFile() {
        return targetFile__;
    }

    /**
     * @param targetFile 設定する targetFile
     */
    public void setTargetFile(String targetFile) {
        targetFile__ = targetFile;
    }
    
    /**
     * <p>targetDeletedFile を取得します。
     * @return targetDeletedFile
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#targetDeletedFile__
     */
    public String getTargetDeletedFile() {
        return targetDeletedFile__;
    }
    /**
     * <p>targetDeletedFile をセットします。
     * @param targetDeletedFile targetDeletedFile
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#targetDeletedFile__
     */
    public void setTargetDeletedFile(String targetDeletedFile) {
        targetDeletedFile__ = targetDeletedFile;
    }

    /**
     * <p>targetDeletedFolder を取得します。
     * @return targetDeletedFolder
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#targetDeletedFolder__
     */
    public String getTargetDeletedFolder() {
        return targetDeletedFolder__;
    }
    /**
     * <p>targetDeletedFolder をセットします。
     * @param targetDeletedFolder targetDeletedFolder
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#targetDeletedFolder__
     */
    public void setTargetDeletedFolder(String targetDeletedFolder) {
        targetDeletedFolder__ = targetDeletedFolder;
    }
    /**
     * @return srchTargetName
     */
    public boolean isSrchTargetName() {
        return srchTargetName__;
    }

    /**
     * @param srchTargetName 設定する srchTargetName
     */
    public void setSrchTargetName(boolean srchTargetName) {
        srchTargetName__ = srchTargetName;
    }

    /**
     * @return srchTargetBiko
     */
    public boolean isSrchTargetBiko() {
        return srchTargetBiko__;
    }

    /**
     * @param srchTargetBiko 設定する srchTargetBiko
     */
    public void setSrchTargetBiko(boolean srchTargetBiko) {
        srchTargetBiko__ = srchTargetBiko;
    }
    /**
     * <p>limit を取得します。
     * @return limit
     */
    public int getLimit() {
        return limit__;
    }

    /**
     * <p>limit をセットします。
     * @param limit limit
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }

    /**
     * <p>start を取得します。
     * @return start
     */
    public int getStart() {
        return start__;
    }

    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(int start) {
        start__ = start;
    }

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    
    /**
     * <p>srchTradeTargetName を取得します。
     * @return srchTradeTargetName
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeTargetName__
     */
    public String getSrchTradeTargetName() {
        return srchTradeTargetName__;
    }
    
    /**
     * <p>srchTradeTargetName をセットします。
     * @param srchTradeTargetName srchTradeTargetName
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeTargetName__
     */
    public void setSrchTradeTargetName(String srchTradeTargetName) {
        srchTradeTargetName__ = srchTradeTargetName;
    }
    
    /**
     * <p>srchTradeMoney を取得します。
     * @return srchTradeMoney
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoney__
     */
    public BigDecimal getSrchTradeMoney() {
        return srchTradeMoney__;
    }
    
    /**
     * <p>srchTradeMoney をセットします。
     * @param srchTradeMoney srchTradeMoney
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoney__
     */
    public void setSrchTradeMoney(BigDecimal srchTradeMoney) {
        srchTradeMoney__ = srchTradeMoney;
    }
    
    /**
     * <p>srchTradeMoneyTo を取得します。
     * @return srchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoneyTo__
     */
    public BigDecimal getSrchTradeMoneyTo() {
        return srchTradeMoneyTo__;
    }
    
    /**
     * <p>srchTradeMoneyTo をセットします。
     * @param srchTradeMoneyTo srchTradeMoneyTo
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoneyTo__
     */
    public void setSrchTradeMoneyTo(BigDecimal srchTradeMoneyTo) {
        srchTradeMoneyTo__ = srchTradeMoneyTo;
    }
    
    /**
     * <p>srchTradeMoneyKbn を取得します。
     * @return srchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoneyKbn__
     */
    public int getSrchTradeMoneyKbn() {
        return srchTradeMoneyKbn__;
    }
    
    /**
     * <p>srchTradeMoneyKbn をセットします。
     * @param srchTradeMoneyKbn srchTradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoneyKbn__
     */
    public void setSrchTradeMoneyKbn(int srchTradeMoneyKbn) {
        srchTradeMoneyKbn__ = srchTradeMoneyKbn;
    }
    
    /**
     * <p>srchTradeMoneyType を取得します。
     * @return srchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoneyType__
     */
    public int getSrchTradeMoneyType() {
        return srchTradeMoneyType__;
    }
    
    /**
     * <p>srchTradeMoneyType をセットします。
     * @param srchTradeMoneyType srchTradeMoneyType
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoneyType__
     */
    public void setSrchTradeMoneyType(int srchTradeMoneyType) {
        srchTradeMoneyType__ = srchTradeMoneyType;
    }
    
    /**
     * <p>srchTradeMoneyJudge を取得します。
     * @return srchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoneyJudge__
     */
    public int getSrchTradeMoneyJudge() {
        return srchTradeMoneyJudge__;
    }
    
    /**
     * <p>srchTradeMoneyJudge をセットします。
     * @param srchTradeMoneyJudge srchTradeMoneyJudge
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeMoneyJudge__
     */
    public void setSrchTradeMoneyJudge(int srchTradeMoneyJudge) {
        srchTradeMoneyJudge__ = srchTradeMoneyJudge;
    }
    
    /**
     * <p>srchTradeDateFlg を取得します。
     * @return srchTradeDateFlg
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeDateFlg__
     */
    public int getSrchTradeDateFlg() {
        return srchTradeDateFlg__;
    }
    
    /**
     * <p>srchTradeDateFlg をセットします。
     * @param srchTradeDateFlg srchTradeDateFlg
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeDateFlg__
     */
    public void setSrchTradeDateFlg(int srchTradeDateFlg) {
        srchTradeDateFlg__ = srchTradeDateFlg;
    }
    
    /**
     * <p>srchTradeDateFrom を取得します。
     * @return srchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeDateFrom__
     */
    public UDate getSrchTradeDateFrom() {
        return srchTradeDateFrom__;
    }
    
    /**
     * <p>srchTradeDateFrom をセットします。
     * @param srchTradeDateFrom srchTradeDateFrom
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeDateFrom__
     */
    public void setSrchTradeDateFrom(UDate srchTradeDateFrom) {
        srchTradeDateFrom__ = srchTradeDateFrom;
    }
    
    /**
     * <p>srchTradeDateTo を取得します。
     * @return srchTradeDateTo
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeDateTo__
     */
    public UDate getSrchTradeDateTo() {
        return srchTradeDateTo__;
    }
    
    /**
     * <p>srchTradeDateTo をセットします。
     * @param srchTradeDateTo srchTradeDateTo
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#srchTradeDateTo__
     */
    public void setSrchTradeDateTo(UDate srchTradeDateTo) {
        srchTradeDateTo__ = srchTradeDateTo;
    }
    
    /**
     * @return srchTargetText
     */
    public boolean isSrchTargetText() {
        return srchTargetText__;
    }
    /**
     * @param srchTargetText セットする srchTargetText
     */
    public void setSrchTargetText(boolean srchTargetText) {
        srchTargetText__ = srchTargetText;
    }

    /**
     * <p>searchFlg を取得します。
     * @return searchFlg
     */
    public boolean getSearchFlg() {
        return searchFlg__;
    }

    /**
     * <p>searchFlg をセットします。
     * @param searchFlg searchFlg
     */
    public void setSearchFlg(boolean searchFlg) {
        searchFlg__ = searchFlg;
    }

    /**
     * <p>aconfMdl を取得します。
     * @return aconfMdl
     */
    public FileAconfModel getAconfMdl() {
        return aconfMdl__;
    }

    /**
     * <p>aconfMdl をセットします。
     * @param aconfMdl aconfMdl
     */
    public void setAconfMdl(FileAconfModel aconfMdl) {
        aconfMdl__ = aconfMdl;
    }
    /**
     * <p>delCabinetFlg を取得します。
     * @return delCabinetFlg
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#delCabinetFlg__
     */
    public boolean isDelCabinetFlg() {
        return delCabinetFlg__;
    }
    /**
     * <p>delCabinetFlg をセットします。
     * @param delCabinetFlg delCabinetFlg
     * @see jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel#delCabinetFlg__
     */
    public void setDelCabinetFlg(boolean delCabinetFlg) {
        delCabinetFlg__ = delCabinetFlg;
    }
}

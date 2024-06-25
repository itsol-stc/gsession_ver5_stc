package jp.groupsession.v2.mem.mem010;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.model.MemoLabelModel;

/**
 * <br>[機  能] メモ メモ画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem010ParamModel extends AbstractParamModel {
    
    /** 非同期処理モード テンポラリディレクトリの初期化*/
    public static final int MODE_CLEAR_TEMP = 0;
    /** 非同期処理モード 検索*/
    public static final int MODE_SEARCH = 1;
    /** 非同期処理モード ラベル追加 */
    public static final int MODE_ADD_LABEL = 2;
    /** 非同期処理モード メモ登録 */
    public static final int MODE_INSERT_MEMO = 3;
    /** 非同期処理モード メモ明細表示 */
    public static final int MODE_SHOW_DETAIL = 4;
    /** 非同期処理モード　メモ削除(変更中のメモが含まれていた場合) */
    public static final int MODE_DELETE_HENKOU = 5;
    /** 非同期処理モード メモ削除 */
    public static final int MODE_DELETE = 6;
    /** 非同期処理モード メモ変更 */
    public static final int MODE_UPDATE = 7;
    /** 非同期処理モード ファイル削除 */
    public static final int MODE_DELETE_FILE = 8;
    
    /** 検索条件 内容 */
    private String mem010SearchNaiyo__ = null;
    /** 検索条件 登録日Fr */
    private String mem010SearchDateFr__ = "";
    /** 検索条件 登録日To */
    private String mem010SearchDateTo__ = "";
    /** 検索条件 ラベル */
    private int mem010SearchLabel__ = -1;
    /** 検索条件 添付 */
    private int mem010SearchTenpu__ = -1;
    /** 検索条件 ソート順 */
    private int mem010Sort__ = GSConstMemo.ORDER_DESC;
    
    /** 検索条件(保持用) 内容 */
    private String mem010SvSearchNaiyo__ = null;
    /** 検索条件(保持用) 登録日Fr */
    private String mem010SvSearchDateFr__ = null;
    /** 検索条件(保持用) 登録日To */
    private String mem010SvSearchDateTo__ = null;
    /** 検索条件(保持用) ラベル */
    private int mem010SvSearchLabel__ = -1;
    /** 検索条件(保持用) 添付 */
    private int mem010SvSearchTenpu__ = -1;
    /** 検索条件(保持用) ソート順 */
    private int mem010SvSort__ = GSConstMemo.ORDER_DESC;
    
    /** 削除対象メモSID */
    private long[] mem010DeleteMemoSidArray__ = null;
    /** 明細表示，変更対象メモSID */
    private long mem010TargetMemoSid__ = -1;
    /** メモ一覧表示件数 */
    private int mem010MemoCount__ = 0;
    
    /** メモ登録 内容 */
    private String mem010Naiyo__ = null;
    /** メモ登録 ラベルSID */
    private int[] mem010Label__ = null;
    /** メモ登録 自動削除設定 */
    private int mem010AtdelFlg__ = GSConstMemo.AUTO_DELETE_FLG_OFF;
    /** メモ登録 自動削除日付 */
    private String mem010AtdelDate__ = null;
    /** ラベル名 */
    private String[] mem010LabelName__ = null;
    
    /** メモ一覧表示用リスト */
    private List<Mem010DisplayModel>memList__ = new ArrayList<Mem010DisplayModel>();
    /** ラベル表示用リスト */
    private List<MemoLabelModel>labelList__ = new ArrayList<MemoLabelModel>();
    /** ファイル表示用リスト */
    private List<LabelValueBean> fileList__ = new ArrayList<LabelValueBean>();
    /** 添付ファイル削除,ダウンロード対象 */
    private String[] mem010selectFile__ = null;
    /** ラベル選択追加時のラベルSID */
    private int mem010targetLabelSid__ = -1;
    /** ラベル新規追加時のラベル名 */
    private String mem010addLabelName__ = null;
    /** ラベル追加時のモード(0:選択, 1:新規) */
    private int mem010addLabelMode__ = GSConstMemo.MODE_SELECT;
    /** ラベル追加時のラベルモデル */
    private MemoLabelModel addLabelModel__ = null;

    /**
     * <p>mem010SearchNaiyo を取得します。
     * @return mem010SearchNaiyo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchNaiyo__
     */
    public String getMem010SearchNaiyo() {
        return mem010SearchNaiyo__;
    }

    /**
     * <p>mem010SearchNaiyo をセットします。
     * @param mem010SearchNaiyo mem010SearchNaiyo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchNaiyo__
     */
    public void setMem010SearchNaiyo(String mem010SearchNaiyo) {
        mem010SearchNaiyo__ = mem010SearchNaiyo;
    }

    /**
     * <p>mem010SearchDateFr を取得します。
     * @return mem010SearchDateFr
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchDateFr__
     */
    public String getMem010SearchDateFr() {
        return mem010SearchDateFr__;
    }

    /**
     * <p>mem010SearchDateFr をセットします。
     * @param mem010SearchDateFr mem010SearchDateFr
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchDateFr__
     */
    public void setMem010SearchDateFr(String mem010SearchDateFr) {
        mem010SearchDateFr__ = mem010SearchDateFr;
    }

    /**
     * <p>mem010SearchDateTo を取得します。
     * @return mem010SearchDateTo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchDateTo__
     */
    public String getMem010SearchDateTo() {
        return mem010SearchDateTo__;
    }

    /**
     * <p>mem010SearchDateTo をセットします。
     * @param mem010SearchDateTo mem010SearchDateTo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchDateTo__
     */
    public void setMem010SearchDateTo(String mem010SearchDateTo) {
        mem010SearchDateTo__ = mem010SearchDateTo;
    }

    /**
     * <p>mem010SearchLabel を取得します。
     * @return mem010SearchLabel
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchLabel__
     */
    public int getMem010SearchLabel() {
        return mem010SearchLabel__;
    }

    /**
     * <p>mem010SearchLabel をセットします。
     * @param mem010SearchLabel mem010SearchLabel
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchLabel__
     */
    public void setMem010SearchLabel(int mem010SearchLabel) {
        mem010SearchLabel__ = mem010SearchLabel;
    }

    /**
     * <p>mem010SearchTenpu を取得します。
     * @return mem010SearchTenpu
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchTenpu__
     */
    public int getMem010SearchTenpu() {
        return mem010SearchTenpu__;
    }

    /**
     * <p>mem010SearchTenpu をセットします。
     * @param mem010SearchTenpu mem010SearchTenpu
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SearchTenpu__
     */
    public void setMem010SearchTenpu(int mem010SearchTenpu) {
        mem010SearchTenpu__ = mem010SearchTenpu;
    }

    /**
     * <p>mem010Sort を取得します。
     * @return mem010Sort
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010Sort__
     */
    public int getMem010Sort() {
        return mem010Sort__;
    }

    /**
     * <p>mem010Sort をセットします。
     * @param mem010Sort mem010Sort
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010Sort__
     */
    public void setMem010Sort(int mem010Sort) {
        mem010Sort__ = mem010Sort;
    }

    /**
     * <p>mem010SvSearchNaiyo を取得します。
     * @return mem010SvSearchNaiyo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchNaiyo__
     */
    public String getMem010SvSearchNaiyo() {
        return mem010SvSearchNaiyo__;
    }

    /**
     * <p>mem010SvSearchNaiyo をセットします。
     * @param mem010SvSearchNaiyo mem010SvSearchNaiyo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchNaiyo__
     */
    public void setMem010SvSearchNaiyo(String mem010SvSearchNaiyo) {
        mem010SvSearchNaiyo__ = mem010SvSearchNaiyo;
    }

    /**
     * <p>mem010SvSearchDateFr を取得します。
     * @return mem010SvSearchDateFr
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchDateFr__
     */
    public String getMem010SvSearchDateFr() {
        return mem010SvSearchDateFr__;
    }

    /**
     * <p>mem010SvSearchDateFr をセットします。
     * @param mem010SvSearchDateFr mem010SvSearchDateFr
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchDateFr__
     */
    public void setMem010SvSearchDateFr(String mem010SvSearchDateFr) {
        mem010SvSearchDateFr__ = mem010SvSearchDateFr;
    }

    /**
     * <p>mem010SvSearchDateTo を取得します。
     * @return mem010SvSearchDateTo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchDateTo__
     */
    public String getMem010SvSearchDateTo() {
        return mem010SvSearchDateTo__;
    }

    /**
     * <p>mem010SvSearchDateTo をセットします。
     * @param mem010SvSearchDateTo mem010SvSearchDateTo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchDateTo__
     */
    public void setMem010SvSearchDateTo(String mem010SvSearchDateTo) {
        mem010SvSearchDateTo__ = mem010SvSearchDateTo;
    }

    /**
     * <p>mem010SvSearchLabel を取得します。
     * @return mem010SvSearchLabel
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchLabel__
     */
    public int getMem010SvSearchLabel() {
        return mem010SvSearchLabel__;
    }

    /**
     * <p>mem010SvSearchLabel をセットします。
     * @param mem010SvSearchLabel mem010SvSearchLabel
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchLabel__
     */
    public void setMem010SvSearchLabel(int mem010SvSearchLabel) {
        mem010SvSearchLabel__ = mem010SvSearchLabel;
    }

    /**
     * <p>mem010SvSearchTenpu を取得します。
     * @return mem010SvSearchTenpu
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchTenpu__
     */
    public int getMem010SvSearchTenpu() {
        return mem010SvSearchTenpu__;
    }

    /**
     * <p>mem010SvSearchTenpu をセットします。
     * @param mem010SvSearchTenpu mem010SvSearchTenpu
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSearchTenpu__
     */
    public void setMem010SvSearchTenpu(int mem010SvSearchTenpu) {
        mem010SvSearchTenpu__ = mem010SvSearchTenpu;
    }

    /**
     * <p>mem010SvSort を取得します。
     * @return mem010SvSort
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSort__
     */
    public int getMem010SvSort() {
        return mem010SvSort__;
    }

    /**
     * <p>mem010SvSort をセットします。
     * @param mem010SvSort mem010SvSort
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010SvSort__
     */
    public void setMem010SvSort(int mem010SvSort) {
        mem010SvSort__ = mem010SvSort;
    }

    /**
     * <p>mem010DeleteMemoSidArray を取得します。
     * @return mem010DeleteMemoSidArray
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010DeleteMemoSidArray__
     */
    public long[] getMem010DeleteMemoSidArray() {
        return mem010DeleteMemoSidArray__;
    }

    /**
     * <p>mem010DeleteMemoSidArray をセットします。
     * @param mem010DeleteMemoSidArray mem010DeleteMemoSidArray
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010DeleteMemoSidArray__
     */
    public void setMem010DeleteMemoSidArray(long[] mem010DeleteMemoSidArray) {
        mem010DeleteMemoSidArray__ = mem010DeleteMemoSidArray;
    }

    /**
     * <p>mem010TargetMemoSid を取得します。
     * @return mem010TargetMemoSid
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010TargetMemoSid__
     */
    public long getMem010TargetMemoSid() {
        return mem010TargetMemoSid__;
    }

    /**
     * <p>mem010TargetMemoSid をセットします。
     * @param mem010TargetMemoSid mem010TargetMemoSid
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010TargetMemoSid__
     */
    public void setMem010TargetMemoSid(long mem010TargetMemoSid) {
        mem010TargetMemoSid__ = mem010TargetMemoSid;
    }

    /**
     * <p>mem010MemoCount を取得します。
     * @return mem010MemoCount
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010MemoCount__
     */
    public int getMem010MemoCount() {
        return mem010MemoCount__;
    }

    /**
     * <p>mem010MemoCount をセットします。
     * @param mem010MemoCount mem010MemoCount
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010MemoCount__
     */
    public void setMem010MemoCount(int mem010MemoCount) {
        mem010MemoCount__ = mem010MemoCount;
    }

    /**
     * <p>mem010Naiyo を取得します。
     * @return mem010Naiyo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010Naiyo__
     */
    public String getMem010Naiyo() {
        return mem010Naiyo__;
    }

    /**
     * <p>mem010Naiyo をセットします。
     * @param mem010Naiyo mem010Naiyo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010Naiyo__
     */
    public void setMem010Naiyo(String mem010Naiyo) {
        mem010Naiyo__ = mem010Naiyo;
    }

    /**
     * <p>mem010Label を取得します。
     * @return mem010Label
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010Label__
     */
    public int[] getMem010Label() {
        return mem010Label__;
    }

    /**
     * <p>mem010Label をセットします。
     * @param mem010Label mem010Label
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010Label__
     */
    public void setMem010Label(int[] mem010Label) {
        mem010Label__ = mem010Label;
    }

    /**
     * <p>mem010AtdelFlg を取得します。
     * @return mem010AtdelFlg
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010AtdelFlg__
     */
    public int getMem010AtdelFlg() {
        return mem010AtdelFlg__;
    }

    /**
     * <p>mem010AtdelFlg をセットします。
     * @param mem010AtdelFlg mem010AtdelFlg
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010AtdelFlg__
     */
    public void setMem010AtdelFlg(int mem010AtdelFlg) {
        mem010AtdelFlg__ = mem010AtdelFlg;
    }

    /**
     * <p>mem010AtdelDate を取得します。
     * @return mem010AtdelDate
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010AtdelDate__
     */
    public String getMem010AtdelDate() {
        return mem010AtdelDate__;
    }

    /**
     * <p>mem010AtdelDate をセットします。
     * @param mem010AtdelDate mem010AtdelDate
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010AtdelDate__
     */
    public void setMem010AtdelDate(String mem010AtdelDate) {
        mem010AtdelDate__ = mem010AtdelDate;
    }

    /**
     * <p>mem010LabelName を取得します。
     * @return mem010LabelName
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010LabelName__
     */
    public String[] getMem010LabelName() {
        return mem010LabelName__;
    }

    /**
     * <p>mem010LabelName をセットします。
     * @param mem010LabelName mem010LabelName
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010LabelName__
     */
    public void setMem010LabelName(String[] mem010LabelName) {
        mem010LabelName__ = mem010LabelName;
    }

    /**
     * <p>memList を取得します。
     * @return memList
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#memList__
     */
    public List<Mem010DisplayModel> getMemList() {
        return memList__;
    }

    /**
     * <p>memList をセットします。
     * @param memList memList
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#memList__
     */
    public void setMemList(List<Mem010DisplayModel> memList) {
        memList__ = memList;
    }

    /**
     * <p>labelList を取得します。
     * @return labelList
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#labelList__
     */
    public List<MemoLabelModel> getLabelList() {
        return labelList__;
    }

    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#labelList__
     */
    public void setLabelList(List<MemoLabelModel> labelList) {
        labelList__ = labelList;
    }

    /**
     * <p>fileList を取得します。
     * @return fileList
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#fileList__
     */
    public List<LabelValueBean> getFileList() {
        return fileList__;
    }

    /**
     * <p>fileList をセットします。
     * @param fileList fileList
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#fileList__
     */
    public void setFileList(List<LabelValueBean> fileList) {
        fileList__ = fileList;
    }

    /**
     * <p>mem010selectFile を取得します。
     * @return mem010selectFile
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010selectFile__
     */
    public String[] getMem010selectFile() {
        return mem010selectFile__;
    }

    /**
     * <p>mem010selectFile をセットします。
     * @param mem010selectFile mem010selectFile
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010selectFile__
     */
    public void setMem010selectFile(String[] mem010selectFile) {
        mem010selectFile__ = mem010selectFile;
    }

    /**
     * <p>mem010targetLabelSid を取得します。
     * @return mem010targetLabelSid
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010targetLabelSid__
     */
    public int getMem010targetLabelSid() {
        return mem010targetLabelSid__;
    }

    /**
     * <p>mem010targetLabelSid をセットします。
     * @param mem010targetLabelSid mem010targetLabelSid
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010targetLabelSid__
     */
    public void setMem010targetLabelSid(int mem010targetLabelSid) {
        mem010targetLabelSid__ = mem010targetLabelSid;
    }

    /**
     * <p>mem010addLabelName を取得します。
     * @return mem010addLabelName
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010addLabelName__
     */
    public String getMem010addLabelName() {
        return mem010addLabelName__;
    }

    /**
     * <p>mem010addLabelName をセットします。
     * @param mem010addLabelName mem010addLabelName
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010addLabelName__
     */
    public void setMem010addLabelName(String mem010addLabelName) {
        mem010addLabelName__ = mem010addLabelName;
    }

    /**
     * <p>mem010addLabelMode を取得します。
     * @return mem010addLabelMode
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010addLabelMode__
     */
    public int getMem010addLabelMode() {
        return mem010addLabelMode__;
    }

    /**
     * <p>mem010addLabelMode をセットします。
     * @param mem010addLabelMode mem010addLabelMode
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#mem010addLabelMode__
     */
    public void setMem010addLabelMode(int mem010addLabelMode) {
        mem010addLabelMode__ = mem010addLabelMode;
    }

    /**
     * <p>addLabelModel を取得します。
     * @return addLabelModel
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#addLabelModel__
     */
    public MemoLabelModel getAddLabelModel() {
        return addLabelModel__;
    }

    /**
     * <p>addLabelModel をセットします。
     * @param addLabelModel addLabelModel
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#addLabelModel__
     */
    public void setAddLabelModel(MemoLabelModel addLabelModel) {
        addLabelModel__ = addLabelModel;
    }

    /**
     * <p>modeClearTemp を取得します。
     * @return modeClearTemp
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_CLEAR_TEMP
     */
    public static int getModeClearTemp() {
        return MODE_CLEAR_TEMP;
    }

    /**
     * <p>modeSearch を取得します。
     * @return modeSearch
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_SEARCH
     */
    public static int getModeSearch() {
        return MODE_SEARCH;
    }

    /**
     * <p>modeAddLabel を取得します。
     * @return modeAddLabel
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_ADD_LABEL
     */
    public static int getModeAddLabel() {
        return MODE_ADD_LABEL;
    }

    /**
     * <p>modeInsertMemo を取得します。
     * @return modeInsertMemo
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_INSERT_MEMO
     */
    public static int getModeInsertMemo() {
        return MODE_INSERT_MEMO;
    }

    /**
     * <p>modeShowDetail を取得します。
     * @return modeShowDetail
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_SHOW_DETAIL
     */
    public static int getModeShowDetail() {
        return MODE_SHOW_DETAIL;
    }

    /**
     * <p>modeDeleteHenkou を取得します。
     * @return modeDeleteHenkou
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_DELETE_HENKOU
     */
    public static int getModeDeleteHenkou() {
        return MODE_DELETE_HENKOU;
    }

    /**
     * <p>modeDelete を取得します。
     * @return modeDelete
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_DELETE
     */
    public static int getModeDelete() {
        return MODE_DELETE;
    }

    /**
     * <p>modeUpdate を取得します。
     * @return modeUpdate
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_UPDATE
     */
    public static int getModeUpdate() {
        return MODE_UPDATE;
    }

    /**
     * <p>modeDeleteFile を取得します。
     * @return modeDeleteFile
     * @see jp.groupsession.v2.mem.mem010.Mem010Form#MODE_DELETE_FILE
     */
    public static int getModeDeleteFile() {
        return MODE_DELETE_FILE;
    }
    
}
package jp.groupsession.v2.fil.fil300;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil010.Fil010ParamModel;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil300ParamModel extends Fil010ParamModel {

    /** キャビネットコンボ選択値 */
    private String fil300SelectCabinet__ = null;
    /** キャビネットコンボ */
    private List<LabelValueBean> fil300CabinetList__ = new ArrayList<LabelValueBean>();
    /** 初期表示フラグ */
    private int fil300InitFlg__ = GSConstFile.INIT_FLG_NO;
    /** 遷移元判定フラグ */
    private int fil300BeforeDspFlg__ = GSConstFile.BEFORE_DSP_FIL010;
    /** ファイル登録画面 登録済みファイル */
    private String[] fil300BeforeInsertFile__ = null;

    /**
     * <p>fil300BeforeInsertFile を取得します。
     * @return fil300BeforeInsertFile
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300BeforeInsertFile__
     */
    public String[] getFil300BeforeInsertFile() {
        return fil300BeforeInsertFile__;
    }

    /**
     * <p>fil300BeforeInsertFile をセットします。
     * @param fil300BeforeInsertFile fil300BeforeInsertFile
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300BeforeInsertFile__
     */
    public void setFil300BeforeInsertFile(String[] fil300BeforeInsertFile) {
        fil300BeforeInsertFile__ = fil300BeforeInsertFile;
    }

    /** 選択ファイル */
    private int fil300SelectFile__ = -1;
    /** ファイル名 */
    private String fil300FileName__ = null;
    /** 取引年月 */
    private String fil300TradeDate__ = null;
    /** 取引先 */
    private String fil300TradeTarget__ = null;
    /** 取引金額 区分*/
    private int fil300TradeMoneyKbn__ = GSConstFile.MONEY_KBN_ON;
    /** 取引金額 */
    private String fil300TradeMoney__ = null;
    /** 取引金額 外貨 */
    private int fil300TradeMoneyGaika__ = -1;

    /** 保存先 */
    private String fil300SaveDir1__ = null;
    /** 保存先振り分けフォルダ1 */
    private int fil300SortFolder1__ = 2;
    /** 保存先振り分けフォルダ2 */
    private int fil300SortFolder2__ = 0;

    /** 登録モード */
    private int fil300insertMode__ = GSConstFile.MODE_SINGLE;
    /** 移動先ディレクトリ */
    private String fil300MoveToDir__ = null;
    /** 振り分け機能：有効キャビネット内フォルダ一覧 */
    private List<FileCabinetDspModel> fil300ErrlFolderList__ = null;
    /** 単体登録 外貨コンボ */
    private List<LabelValueBean> fil300GaikaList__ = null;
    /** 取引情報入力 ファイル一覧 */
    private List<FileErrlAdddataModel> fil300AddFileDspList__ = null;
    /** 一括登録 選択キャビネット, 選択フォルダ */
    private int fil300SelectDir__ = -1;
    /** 一括登録 取引情報入力 ページ番号 */
    private int fil300SelectPage__ = 1;
    /** 一括登録 取引情報入力 ページングコンボ */
    private ArrayList<LabelValueBean> fil300PageLabel__ = null;
    /** 一括登録 取引一覧表示 */
    private List<FileErrlAdddataDspModel> fil300ImpDspList__ = null;
    /** 一括登録 取引一覧表示(確認ダイアログ) */
    private ArrayList<LabelValueBean> fil300ImpPageLabel__ = null;
    /** 一括登録 取引一覧表示(確認ダイアログ) */
    private int fil300ImpPage__ = 1;
    /** ツリーフォーム 最下階層 */
    private String[] treeFormLv11__;

    /** ツリーフォーム 保存先root階層 */
    private String[] treeSavePathLv0__;
    /** ツリーフォーム 保存先1階層 */
    private String[] treeSavePathLv1__;
    /** ツリーフォーム 保存先2階層 */
    private String[] treeSavePathLv2__;
    /** ツリーフォーム 保存先3階層 */
    private String[] treeSavePathLv3__;
    /** ツリーフォーム 保存先4階層 */
    private String[] treeSavePathLv4__;
    /** ツリーフォーム 保存先5階層 */
    private String[] treeSavePathLv5__;
    /** ツリーフォーム 保存先6階層 */
    private String[] treeSavePathLv6__;
    /** ツリーフォーム 保存先7階層 */
    private String[] treeSavePathLv7__;
    /** ツリーフォーム 保存先8階層 */
    private String[] treeSavePathLv8__;
    /** ツリーフォーム 保存先9階層 */
    private String[] treeSavePathLv9__;
    /** ツリーフォーム 保存先10階層 */
    private String[] treeSavePathLv10__;

    /**
     * <p>fil300SelectCabinet を取得します。
     * @return fil300SelectCabinet
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SelectCabinet__
     */
    public String getFil300SelectCabinet() {
        return fil300SelectCabinet__;
    }
    /**
     * <p>fil300SelectCabinet をセットします。
     * @param fil300SelectCabinet fil300SelectCabinet
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SelectCabinet__
     */
    public void setFil300SelectCabinet(String fil300SelectCabinet) {
        fil300SelectCabinet__ = fil300SelectCabinet;
    }
    /**
     * <p>fil300CabinetList を取得します。
     * @return fil300CabinetList
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300CabinetList__
     */
    public List<LabelValueBean> getFil300CabinetList() {
        return fil300CabinetList__;
    }
    /**
     * <p>fil300CabinetList をセットします。
     * @param fil300CabinetList fil300CabinetList
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300CabinetList__
     */
    public void setFil300CabinetList(List<LabelValueBean> fil300CabinetList) {
        fil300CabinetList__ = fil300CabinetList;
    }
    /**
     * <p>fil300InitFlg を取得します。
     * @return fil300InitFlg
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300InitFlg__
     */
    public int getFil300InitFlg() {
        return fil300InitFlg__;
    }
    /**
     * <p>fil300InitFlg をセットします。
     * @param fil300InitFlg fil300InitFlg
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300InitFlg__
     */
    public void setFil300InitFlg(int fil300InitFlg) {
        fil300InitFlg__ = fil300InitFlg;
    }
    /**
     * <p>fil300BeforeDspFlg を取得します。
     * @return fil300BeforeDspFlg
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300BeforeDspFlg__
     */
    public int getFil300BeforeDspFlg() {
        return fil300BeforeDspFlg__;
    }
    /**
     * <p>fil300BeforeDspFlg をセットします。
     * @param fil300BeforeDspFlg fil300BeforeDspFlg
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300BeforeDspFlg__
     */
    public void setFil300BeforeDspFlg(int fil300BeforeDspFlg) {
        fil300BeforeDspFlg__ = fil300BeforeDspFlg;
    }
    /**
     * <p>fil300SelectFile を取得します。
     * @return fil300SelectFile
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SelectFile__
     */
    public int getFil300SelectFile() {
        return fil300SelectFile__;
    }
    /**
     * <p>fil300SelectFile をセットします。
     * @param fil300SelectFile fil300SelectFile
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SelectFile__
     */
    public void setFil300SelectFile(int fil300SelectFile) {
        fil300SelectFile__ = fil300SelectFile;
    }
    /**
     * <p>fil300FileName を取得します。
     * @return fil300FileName
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300FileName__
     */
    public String getFil300FileName() {
        return fil300FileName__;
    }
    /**
     * <p>fil300FileName をセットします。
     * @param fil300FileName fil300FileName
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300FileName__
     */
    public void setFil300FileName(String fil300FileName) {
        fil300FileName__ = fil300FileName;
    }
    /**
     * <p>fil300TradeDate を取得します。
     * @return fil300TradeDate
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300TradeDate__
     */
    public String getFil300TradeDate() {
        return fil300TradeDate__;
    }
    /**
     * <p>fil300TradeDate をセットします。
     * @param fil300TradeDate fil300TradeDate
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300TradeDate__
     */
    public void setFil300TradeDate(String fil300TradeDate) {
        fil300TradeDate__ = fil300TradeDate;
    }
    /**
     * <p>fil300TradeTarget を取得します。
     * @return fil300TradeTarget
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300TradeTarget__
     */
    public String getFil300TradeTarget() {
        return fil300TradeTarget__;
    }
    /**
     * <p>fil300TradeTarget をセットします。
     * @param fil300TradeTarget fil300TradeTarget
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300TradeTarget__
     */
    public void setFil300TradeTarget(String fil300TradeTarget) {
        fil300TradeTarget__ = fil300TradeTarget;
    }
    /**
     * <p>fil300TradeMoney を取得します。
     * @return fil300TradeMoney
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300TradeMoney__
     */
    public String getFil300TradeMoney() {
        return fil300TradeMoney__;
    }
    /**
     * <p>fil300TradeMoney をセットします。
     * @param fil300TradeMoney fil300TradeMoney
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300TradeMoney__
     */
    public void setFil300TradeMoney(String fil300TradeMoney) {
        fil300TradeMoney__ = fil300TradeMoney;
    }
    /**
     * <p>fil300TradeMoneyKbn を取得します。
     * @return fil300TradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300TradeMoneyKbn__
     */
    public int getFil300TradeMoneyKbn() {
        return fil300TradeMoneyKbn__;
    }
    /**
     * <p>fil300TradeMoneyKbn をセットします。
     * @param fil300TradeMoneyKbn fil300TradeMoneyKbn
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300TradeMoneyKbn__
     */
    public void setFil300TradeMoneyKbn(int fil300TradeMoneyKbn) {
        fil300TradeMoneyKbn__ = fil300TradeMoneyKbn;
    }
    /**
     * <p>fil300TradeMoneyGaika を取得します。
     * @return fil300TradeMoneyGaika
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300TradeMoneyGaika__
     */
    public int getFil300TradeMoneyGaika() {
        return fil300TradeMoneyGaika__;
    }
    /**
     * <p>fil300TradeMoneyGaika をセットします。
     * @param fil300TradeMoneyGaika fil300TradeMoneyGaika
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300TradeMoneyGaika__
     */
    public void setFil300TradeMoneyGaika(int fil300TradeMoneyGaika) {
        fil300TradeMoneyGaika__ = fil300TradeMoneyGaika;
    }
    /**
     * <p>fil300SaveDir1 を取得します。
     * @return fil300SaveDir1
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SaveDir1__
     */
    public String getFil300SaveDir1() {
        return fil300SaveDir1__;
    }
    /**
     * <p>fil300SaveDir1 をセットします。
     * @param fil300SaveDir1 fil300SaveDir1
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SaveDir1__
     */
    public void setFil300SaveDir1(String fil300SaveDir1) {
        fil300SaveDir1__ = fil300SaveDir1;
    }
    /**
     * <p>fil300SortFolder1 を取得します。
     * @return fil300SortFolder1
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SortFolder1__
     */
    public int getFil300SortFolder1() {
        return fil300SortFolder1__;
    }
    /**
     * <p>fil300SortFolder1 をセットします。
     * @param fil300SortFolder1 fil300SortFolder1
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SortFolder1__
     */
    public void setFil300SortFolder1(int fil300SortFolder1) {
        fil300SortFolder1__ = fil300SortFolder1;
    }
    /**
     * <p>fil300SortFolder2 を取得します。
     * @return fil300SortFolder2
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SortFolder2__
     */
    public int getFil300SortFolder2() {
        return fil300SortFolder2__;
    }
    /**
     * <p>fil300SortFolder2 をセットします。
     * @param fil300SortFolder2 fil300SortFolder2
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SortFolder2__
     */
    public void setFil300SortFolder2(int fil300SortFolder2) {
        fil300SortFolder2__ = fil300SortFolder2;
    }
    /**
     * <p>fil300insertMode を取得します。
     * @return fil300insertMode
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300insertMode__
     */
    public int getFil300insertMode() {
        return fil300insertMode__;
    }
    /**
     * <p>fil300insertMode をセットします。
     * @param fil300insertMode fil300insertMode
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300insertMode__
     */
    public void setFil300insertMode(int fil300insertMode) {
        fil300insertMode__ = fil300insertMode;
    }
    /**
     * <p>fil300MoveToDir を取得します。
     * @return fil300MoveToDir
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300MoveToDir__
     */
    public String getFil300MoveToDir() {
        return fil300MoveToDir__;
    }
    /**
     * <p>fil300MoveToDir をセットします。
     * @param fil300MoveToDir fil300MoveToDir
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300MoveToDir__
     */
    public void setFil300MoveToDir(String fil300MoveToDir) {
        fil300MoveToDir__ = fil300MoveToDir;
    }
    /**
     * <p>fil300ErrlFolderList を取得します。
     * @return fil300ErrlFolderList
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300ErrlFolderList__
     */
    public List<FileCabinetDspModel> getFil300ErrlFolderList() {
        return fil300ErrlFolderList__;
    }
    /**
     * <p>fil300ErrlFolderList をセットします。
     * @param fil300ErrlFolderList fil300ErrlFolderList
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300ErrlFolderList__
     */
    public void setFil300ErrlFolderList(List<FileCabinetDspModel> fil300ErrlFolderList) {
        fil300ErrlFolderList__ = fil300ErrlFolderList;
    }
    /**
     * <p>fil300GaikaList を取得します。
     * @return fil300GaikaList
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300GaikaList__
     */
    public List<LabelValueBean> getFil300GaikaList() {
        return fil300GaikaList__;
    }
    /**
     * <p>fil300GaikaList をセットします。
     * @param fil300GaikaList fil300GaikaList
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300GaikaList__
     */
    public void setFil300GaikaList(List<LabelValueBean> fil300GaikaList) {
        fil300GaikaList__ = fil300GaikaList;
    }
    /**
     * <p>fil300AddFileDspList を取得します。
     * @return fil300AddFileDspList
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300AddFileDspList__
     */
    public List<FileErrlAdddataModel> getFil300AddFileDspList() {
        return fil300AddFileDspList__;
    }
    /**
     * <p>fil300AddFileDspList をセットします。
     * @param fil300AddFileDspList fil300AddFileDspList
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300AddFileDspList__
     */
    public void setFil300AddFileDspList(List<FileErrlAdddataModel> fil300AddFileDspList) {
        fil300AddFileDspList__ = fil300AddFileDspList;
    }
    /**
     * <p>fil300SelectDir を取得します。
     * @return fil300SelectDir
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SelectDir__
     */
    public int getFil300SelectDir() {
        return fil300SelectDir__;
    }
    /**
     * <p>fil300SelectDir をセットします。
     * @param fil300SelectDir fil300SelectDir
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SelectDir__
     */
    public void setFil300SelectDir(int fil300SelectDir) {
        fil300SelectDir__ = fil300SelectDir;
    }
    /**
     * <p>fil300SelectPage を取得します。
     * @return fil300SelectPage
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SelectPage__
     */
    public int getFil300SelectPage() {
        return fil300SelectPage__;
    }
    /**
     * <p>fil300SelectPage をセットします。
     * @param fil300SelectPage fil300SelectPage
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#fil300SelectPage__
     */
    public void setFil300SelectPage(int fil300SelectPage) {
        fil300SelectPage__ = fil300SelectPage;
    }
    /**
     * <p>fil300PageLabel を取得します。
     * @return fil300PageLabel
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300PageLabel__
     */
    public ArrayList<LabelValueBean> getFil300PageLabel() {
        return fil300PageLabel__;
    }
    /**
     * <p>fil300PageLabel をセットします。
     * @param fil300PageLabel fil300PageLabel
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300PageLabel__
     */
    public void setFil300PageLabel(ArrayList<LabelValueBean> fil300PageLabel) {
        fil300PageLabel__ = fil300PageLabel;
    }
    /**
     * <p>fil300ImpDspList を取得します。
     * @return fil300ImpDspList
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300ImpDspList__
     */
    public List<FileErrlAdddataDspModel> getFil300ImpDspList() {
        return fil300ImpDspList__;
    }
    /**
     * <p>fil300ImpDspList をセットします。
     * @param fil300ImpDspList fil300ImpDspList
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300ImpDspList__
     */
    public void setFil300ImpDspList(
            List<FileErrlAdddataDspModel> fil300ImpDspList) {
        fil300ImpDspList__ = fil300ImpDspList;
    }
    /**
     * <p>fil300ImpPageLabel を取得します。
     * @return fil300ImpPageLabel
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300ImpPageLabel__
     */
    public ArrayList<LabelValueBean> getFil300ImpPageLabel() {
        return fil300ImpPageLabel__;
    }
    /**
     * <p>fil300ImpPageLabel をセットします。
     * @param fil300ImpPageLabel fil300ImpPageLabel
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300ImpPageLabel__
     */
    public void setFil300ImpPageLabel(
            ArrayList<LabelValueBean> fil300ImpPageLabel) {
        fil300ImpPageLabel__ = fil300ImpPageLabel;
    }
    /**
     * <p>fil300ImpPage を取得します。
     * @return fil300ImpPage
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300ImpPage__
     */
    public int getFil300ImpPage() {
        return fil300ImpPage__;
    }
    /**
     * <p>fil300ImpPage をセットします。
     * @param fil300ImpPage fil300ImpPage
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#fil300ImpPage__
     */
    public void setFil300ImpPage(int fil300ImpPage) {
        fil300ImpPage__ = fil300ImpPage;
    }
    /**
     * <p>treeFormLv11 を取得します。
     * @return treeFormLv11
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#treeFormLv11__
     */
    public String[] getTreeFormLv11() {
        return treeFormLv11__;
    }
    /**
     * <p>treeFormLv11 をセットします。
     * @param treeFormLv11 treeFormLv11
     * @see jp.groupsession.v2.fil.fil300.Fil300ParamModel#treeFormLv11__
     */
    public void setTreeFormLv11(String[] treeFormLv11) {
        treeFormLv11__ = treeFormLv11;
    }    /**
     * <p>treeSavePathLv0 を取得します。
     * @return treeSavePathLv0
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv0__
     */
    public String[] getTreeSavePathLv0() {
        return treeSavePathLv0__;
    }
    /**
     * <p>treeSavePathLv0 をセットします。
     * @param treeSavePathLv0 treeSavePathLv0
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv0__
     */
    public void setTreeSavePathLv0(String[] treeSavePathLv0) {
        treeSavePathLv0__ = treeSavePathLv0;
    }
    /**
     * <p>treeSavePathLv1 を取得します。
     * @return treeSavePathLv1
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv1__
     */
    public String[] getTreeSavePathLv1() {
        return treeSavePathLv1__;
    }
    /**
     * <p>treeSavePathLv1 をセットします。
     * @param treeSavePathLv1 treeSavePathLv1
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv1__
     */
    public void setTreeSavePathLv1(String[] treeSavePathLv1) {
        treeSavePathLv1__ = treeSavePathLv1;
    }
    /**
     * <p>treeSavePathLv2 を取得します。
     * @return treeSavePathLv2
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv2__
     */
    public String[] getTreeSavePathLv2() {
        return treeSavePathLv2__;
    }
    /**
     * <p>treeSavePathLv2 をセットします。
     * @param treeSavePathLv2 treeSavePathLv2
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv2__
     */
    public void setTreeSavePathLv2(String[] treeSavePathLv2) {
        treeSavePathLv2__ = treeSavePathLv2;
    }
    /**
     * <p>treeSavePathLv3 を取得します。
     * @return treeSavePathLv3
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv3__
     */
    public String[] getTreeSavePathLv3() {
        return treeSavePathLv3__;
    }
    /**
     * <p>treeSavePathLv3 をセットします。
     * @param treeSavePathLv3 treeSavePathLv3
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv3__
     */
    public void setTreeSavePathLv3(String[] treeSavePathLv3) {
        treeSavePathLv3__ = treeSavePathLv3;
    }
    /**
     * <p>treeSavePathLv4 を取得します。
     * @return treeSavePathLv4
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv4__
     */
    public String[] getTreeSavePathLv4() {
        return treeSavePathLv4__;
    }
    /**
     * <p>treeSavePathLv4 をセットします。
     * @param treeSavePathLv4 treeSavePathLv4
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv4__
     */
    public void setTreeSavePathLv4(String[] treeSavePathLv4) {
        treeSavePathLv4__ = treeSavePathLv4;
    }
    /**
     * <p>treeSavePathLv5 を取得します。
     * @return treeSavePathLv5
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv5__
     */
    public String[] getTreeSavePathLv5() {
        return treeSavePathLv5__;
    }
    /**
     * <p>treeSavePathLv5 をセットします。
     * @param treeSavePathLv5 treeSavePathLv5
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv5__
     */
    public void setTreeSavePathLv5(String[] treeSavePathLv5) {
        treeSavePathLv5__ = treeSavePathLv5;
    }
    /**
     * <p>treeSavePathLv6 を取得します。
     * @return treeSavePathLv6
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv6__
     */
    public String[] getTreeSavePathLv6() {
        return treeSavePathLv6__;
    }
    /**
     * <p>treeSavePathLv6 をセットします。
     * @param treeSavePathLv6 treeSavePathLv6
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv6__
     */
    public void setTreeSavePathLv6(String[] treeSavePathLv6) {
        treeSavePathLv6__ = treeSavePathLv6;
    }
    /**
     * <p>treeSavePathLv7 を取得します。
     * @return treeSavePathLv7
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv7__
     */
    public String[] getTreeSavePathLv7() {
        return treeSavePathLv7__;
    }
    /**
     * <p>treeSavePathLv7 をセットします。
     * @param treeSavePathLv7 treeSavePathLv7
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv7__
     */
    public void setTreeSavePathLv7(String[] treeSavePathLv7) {
        treeSavePathLv7__ = treeSavePathLv7;
    }
    /**
     * <p>treeSavePathLv8 を取得します。
     * @return treeSavePathLv8
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv8__
     */
    public String[] getTreeSavePathLv8() {
        return treeSavePathLv8__;
    }
    /**
     * <p>treeSavePathLv8 をセットします。
     * @param treeSavePathLv8 treeSavePathLv8
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv8__
     */
    public void setTreeSavePathLv8(String[] treeSavePathLv8) {
        treeSavePathLv8__ = treeSavePathLv8;
    }
    /**
     * <p>treeSavePathLv9 を取得します。
     * @return treeSavePathLv9
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv9__
     */
    public String[] getTreeSavePathLv9() {
        return treeSavePathLv9__;
    }
    /**
     * <p>treeSavePathLv9 をセットします。
     * @param treeSavePathLv9 treeSavePathLv9
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv9__
     */
    public void setTreeSavePathLv9(String[] treeSavePathLv9) {
        treeSavePathLv9__ = treeSavePathLv9;
    }
    /**
     * <p>treeSavePathLv10 を取得します。
     * @return treeSavePathLv10
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv10__
     */
    public String[] getTreeSavePathLv10() {
        return treeSavePathLv10__;
    }
    /**
     * <p>treeSavePathLv10 をセットします。
     * @param treeSavePathLv10 treeSavePathLv10
     * @see jp.groupsession.v2.fil.fil300.Fil300Form#treeSavePathLv10__
     */
    public void setTreeSavePathLv10(String[] treeSavePathLv10) {
        treeSavePathLv10__ = treeSavePathLv10;
    }
}